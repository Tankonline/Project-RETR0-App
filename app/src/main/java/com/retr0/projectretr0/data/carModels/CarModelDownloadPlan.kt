package com.retr0.projectretr0.data.carModels

import com.retr0.projectretr0.data.carModels.manifest.CarModelLimits
import com.retr0.projectretr0.data.carModels.manifest.CarModelManifestValidator
import org.json.JSONObject
import java.io.File
import java.io.IOException

/**
 * Everything one Community Hub car-model download needs, parsed from `GET /v1/car-models/{id}/download`.
 * Media is optional in the response (older Workers omit it), so it falls back to the catalog/details URLs
 * already on [CommunityCarModel]. Paths are validated here, before anything touches the disk.
 */
data class CarModelDownloadPlan(
    val modelId: String,
    val manifestUrl: String,
    val manifestSha256: String?,
    val files: List<FileEntry>,
    val cover: MediaEntry?,
    val screenshots: List<MediaEntry>,
) {
    data class FileEntry(val path: String, val url: String, val sizeBytes: Long, val sha256: String)
    /** [sha256] / [sizeBytes] are null when the Worker did not supply them (fallback URLs). */
    data class MediaEntry(val role: String, val position: Int, val url: String, val sha256: String?, val sizeBytes: Long?)

    val modelBytes: Long get() = files.sumOf { it.sizeBytes }

    companion object {
        private val SHA256 = Regex("^[0-9a-fA-F]{64}$")

        fun parse(json: JSONObject, model: CommunityCarModel): CarModelDownloadPlan {
            val manifestUrl = json.optString("manifestUrl").takeIf(String::isNotBlank) ?: throw IllegalArgumentException("Download response is missing manifestUrl")
            val array = json.optJSONArray("files") ?: throw IllegalArgumentException("Download response is missing files")
            val files = (0 until array.length()).map { index ->
                val item = array.getJSONObject(index)
                val path = item.optString("path")
                try { CarModelManifestValidator.validateRelativePath(path) } catch (error: IllegalArgumentException) {
                    throw CarModelException(CarModelErrorCode.UNSAFE_PATH, "Unsafe path in download response: $path", error)
                }
                val url = item.optString("url").takeIf(String::isNotBlank) ?: throw IllegalArgumentException("Download URL is missing for $path")
                val size = item.optLong("size", item.optLong("sizeBytes", -1))
                val sha = item.optString("sha256")
                require(size > 0 && size <= CarModelLimits.MAX_FILE_BYTES) { "Invalid size for $path" }
                require(SHA256.matches(sha)) { "Invalid checksum for $path" }
                FileEntry(path, url, size, sha.lowercase())
            }
            require(files.isNotEmpty()) { "Download response lists no files" }
            require(files.map { it.path.lowercase() }.distinct().size == files.size) { "Download response lists a file twice" }
            require(files.sumOf { it.sizeBytes } <= CarModelLimits.MAX_TOTAL_BYTES) { "Car model package is too large" }

            val cover = json.optJSONObject("cover")?.let { media(it, "cover", 0) }
                ?: json.optString("coverUrl").takeIf(String::isNotBlank)?.let { MediaEntry("cover", 0, it, json.optString("coverSha256").takeIf(SHA256::matches)?.lowercase(), null) }
                ?: model.coverUrl?.let { MediaEntry("cover", 0, it, null, null) }
            val screenshotArray = json.optJSONArray("screenshots")
            val screenshots = if (screenshotArray != null) {
                (0 until screenshotArray.length()).mapNotNull { index -> screenshotArray.optJSONObject(index)?.let { media(it, "screenshot", it.optInt("position", index + 1)) } }
            } else {
                model.screenshotUrls.mapIndexed { index, url -> MediaEntry("screenshot", index + 1, url, null, null) }
            }
            return CarModelDownloadPlan(
                modelId = model.id,
                manifestUrl = manifestUrl,
                manifestSha256 = json.optString("manifestSha256").takeIf(SHA256::matches)?.lowercase(),
                files = files,
                cover = cover,
                screenshots = screenshots.filter { it.position in 1..CarModelLimits.MAX_SCREENSHOTS }.distinctBy { it.position }.sortedBy { it.position },
            )
        }

        private fun media(json: JSONObject, role: String, position: Int): MediaEntry? {
            val url = json.optString("url").takeIf(String::isNotBlank) ?: return null
            return MediaEntry(role, position, url, json.optString("sha256").takeIf(SHA256::matches)?.lowercase(), json.optLong("size", -1).takeIf { it > 0 })
        }
    }
}

/** Non-2xx HTTP status for a Community Hub asset. 5xx and 429 are worth retrying; other 4xx are not. */
class CarModelHttpException(val statusCode: Int, message: String) : IOException(message) {
    val retryable: Boolean get() = statusCode >= 500 || statusCode == 429 || statusCode == 408
}

/** Transport for the download transaction; implemented by the repository, faked in tests. */
interface CarModelDownloadSource {
    suspend fun downloadPlan(model: CommunityCarModel): CarModelDownloadPlan
    /** Streams [url] into [output] (overwriting it), calling [onBytes] with bytes written so far for this file. */
    suspend fun fetch(url: String, output: File, onBytes: (Long) -> Unit)
}
