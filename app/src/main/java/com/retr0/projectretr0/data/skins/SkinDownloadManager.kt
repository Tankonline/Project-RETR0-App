package com.retr0.projectretr0.data.skins

import android.content.Context
import com.retr0.projectretr0.data.skins.local.LocalSkinEntity
import com.retr0.projectretr0.data.skins.local.LocalSkinRepository
import com.retr0.projectretr0.data.skins.local.SkinImageEntity
import com.retr0.projectretr0.data.skins.local.VerifiedFilePromoter
import com.retr0.projectretr0.data.skins.manifest.ManifestFile
import com.retr0.projectretr0.data.skins.manifest.ManifestScreenshot
import com.retr0.projectretr0.data.skins.manifest.SkinManifest
import com.retr0.projectretr0.data.skins.manifest.SkinManifestValidator
import java.security.MessageDigest
import java.util.Date
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.FileInputStream
import java.util.UUID
import java.util.Locale

class SkinDownloadManager(
    private val filesRoot: File,
    private val localRepository: LocalSkinRepository,
    private val client: OkHttpClient = OkHttpClient(),
) {
    constructor(context: Context) : this(File(context.filesDir, "skins"), LocalSkinRepository(context))

    suspend fun download(skin: CommunityDownload, onProgress: (Int) -> Unit = {}): LocalSkinEntity = withContext(Dispatchers.IO) {
        require(skin.url.startsWith("https://")) { "Only HTTPS downloads are allowed" }
        require(skin.size in (1..VerifiedFilePromoter.MAX_BINARY_BYTES)) { "Invalid skin download size" }
        require(skin.sha256.matches(Regex("[0-9a-fA-F]{64}"))) { "Invalid skin SHA-256" }
        val id = UUID.randomUUID().toString()
        val directory = File(filesRoot, id).apply { mkdirs() }
        val safeFilename = skin.sourceFilename.substringAfterLast('/').substringAfterLast('\\')
        require(safeFilename == skin.sourceFilename && safeFilename.isNotBlank() && safeFilename != "." && safeFilename != "..") { "Invalid skin filename" }
        val destination = File(directory, "skin.dds.z")
        try {
            client.newCall(Request.Builder().url(skin.url).get().build()).execute().use { response ->
                require(response.isSuccessful) { "Skin download failed with HTTP ${response.code}" }
                val body = response.body ?: error("Skin download had no body")
                val headerLength = body.contentLength()
                require(headerLength < 0 || headerLength == skin.size) { "Download Content-Length does not match metadata" }
                val verified = VerifiedFilePromoter.promote(body.byteStream(), destination, skin.size, skin.sha256) { bytes -> onProgress(((bytes * 70) / skin.size).toInt()) }
                val coverEntity = skin.coverUrl?.let { downloadCover(it, directory, id) }
                val imageEntities = buildList {
                    coverEntity?.let(::add)
                    addAll(downloadScreenshots(skin.screenshotUrls, directory, id) { completed -> onProgress(70 + completed * 25 / skin.screenshotUrls.size.coerceAtLeast(1)) })
                }
                val entity = LocalSkinEntity(id, skin.communityId, skin.title, skin.creator, skin.vehicle, skin.carDirectory, skin.slotKey, skin.textureFormat, "skin.dds.z", verified.file.absolutePath, verified.size, verified.sha256, null, null, skin.gamePackage, System.currentTimeMillis())
                localRepository.saveDownloaded(entity, imageEntities)
                writeManifest(skin, directory, id, entity, coverEntity, imageEntities.filter { it.role == "SCREENSHOT" })
                onProgress(100)
                entity
            }
        } catch (error: Throwable) {
            directory.deleteRecursively()
            throw error
        }
    }

    private fun writeManifest(skin: CommunityDownload, directory: File, skinId: String, entity: LocalSkinEntity, cover: SkinImageEntity?, screenshots: List<SkinImageEntity>) {
        fun hash(file: File): String = FileInputStream(file).use { input -> MessageDigest.getInstance("SHA-256").digest(input.readBytes()).joinToString("") { b -> "%02x".format(b) } }
        val manifest = SkinManifest(
            skinId = skinId, title = skin.title, description = "", creator = skin.creator, vehicle = skin.vehicle,
            carDirectory = skin.carDirectory, slotKey = skin.slotKey, textureFormat = skin.textureFormat,
            gamePackage = skin.gamePackage, sourceFilename = skin.sourceFilename, targetRelativePath = null,
            binary = ManifestFile("skin.dds.z", "application/octet-stream", entity.binarySize, entity.binarySha256),
            cover = cover?.let { ManifestFile("cover.webp", it.mimeType, it.sizeBytes, hash(File(it.path))) },
            screenshots = screenshots.map { ManifestScreenshot(it.position, "screenshots/${File(it.path).name}", it.mimeType, it.sizeBytes, hash(File(it.path)), it.caption) },
            communityId = skin.communityId,
        )
        SkinManifestValidator.validate(manifest, directory)
        val temporary = File(directory, "skin-manifest.json.tmp")
        temporary.writeText(manifest.toJson().toString(2))
        temporary.renameTo(File(directory, "skin-manifest.json"))
    }

    private fun downloadCover(url: String, directory: File, skinId: String): SkinImageEntity? {
        if (!url.startsWith("https://")) return null
        val destination = File(directory, "cover.webp")
        return runCatching {
            client.newCall(Request.Builder().url(url).get().build()).execute().use { response ->
                require(response.isSuccessful) { "Cover download failed with HTTP ${response.code}" }
                val body = response.body ?: error("Cover download had no body")
                body.byteStream().use { input -> destination.outputStream().use { output -> input.copyTo(output) } }
            }
            require(destination.isFile && destination.length() > 0L) { "Downloaded cover is empty" }
            SkinImageEntity("$skinId-cover", skinId, "COVER", 0, "", destination.absolutePath, "image/webp", 0, 0, destination.length(), "")
        }.getOrNull()
    }

    private fun downloadScreenshots(urls: List<String>, directory: File, skinId: String, onComplete: (Int) -> Unit): List<SkinImageEntity> {
        if (urls.isEmpty()) return emptyList()
        val imageDirectory = File(directory, "screenshots").apply { mkdirs() }
        return urls.distinct().mapIndexedNotNull { index, url ->
            require(url.startsWith("https://")) { "Only HTTPS screenshot downloads are allowed" }
            val extension = url.substringBefore('?').substringAfterLast('.').lowercase().takeIf { it in setOf("jpg", "jpeg", "png", "webp") } ?: "img"
            val destination = File(imageDirectory, "${String.format(Locale.ROOT, "%02d", index + 1)}.$extension")
            runCatching {
                client.newCall(Request.Builder().url(url).get().build()).execute().use { response ->
                    require(response.isSuccessful) { "Screenshot download failed with HTTP ${response.code}" }
                    val body = response.body ?: error("Screenshot download had no body")
                    body.byteStream().use { input -> destination.outputStream().use { output -> input.copyTo(output) } }
                }
            }.getOrElse { return@mapIndexedNotNull null }
            if (!destination.isFile || destination.length() == 0L) return@mapIndexedNotNull null
            onComplete(index + 1)
            SkinImageEntity("${skinId}-screenshot-${index + 1}", skinId, "SCREENSHOT", index + 1, "", destination.absolutePath, "image/*", 0, 0, destination.length(), "")
        }
    }
}

data class CommunityDownload(
    val communityId: String,
    val title: String,
    val creator: String,
    val vehicle: String,
    val carDirectory: String,
    val slotKey: String,
    val textureFormat: String,
    val sourceFilename: String,
    val gamePackage: String,
    val url: String,
    val size: Long,
    val sha256: String,
    val coverUrl: String? = null,
    val screenshotUrls: List<String> = emptyList(),
    val companionUrl: String? = null,
    val companionFilename: String? = null,
)
