package com.retr0.projectretr0.data.skins

import android.content.Context
import android.util.Log
import com.retr0.projectretr0.BuildConfig
import com.retr0.projectretr0.installer.CommunityMusic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okio.BufferedSink
import org.json.JSONObject
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.io.IOException
import java.security.MessageDigest
import java.util.concurrent.TimeUnit

class MusicApiClient(
    context: Context,
    private val baseUrl: String = BuildConfig.COMMUNITY_API_BASE_URL,
    private val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(90, TimeUnit.SECONDS)
        .readTimeout(70, TimeUnit.SECONDS)
        .callTimeout(120, TimeUnit.SECONDS)
        .build(),
) {
    private val identity = SkinInstallationIdentity(context.applicationContext)

    suspend fun upload(
        file: File,
        musicName: String,
        uploaderName: String,
        targetTrack: String,
    ): MusicUploadResponse = withContext(Dispatchers.IO) {
        require(file.isFile && file.length() > 0) { "Music file is empty or missing" }
        require(file.length() <= MAX_MUSIC_BYTES) { "Music file exceeds 25 MiB" }
        val url = musicCollectionUrl().newBuilder()
            .addQueryParameter("name", musicName.trim())
            .addQueryParameter("user", uploaderName.trim())
            .addQueryParameter("track", targetTrack.trim())
            .build()
        val hash = fileSha256(file)
        try {
            executeUpload(url, file, hash)
        } catch (error: SkinApiException) {
            if (error.errorCode != "unknown_installation") throw error
            registerInstallation()
            executeUpload(url, file, hash)
        }
    }

    suspend fun uploadCover(musicId: String, input: InputStream, contentType: String): String? = withContext(Dispatchers.IO) {
        val normalizedContentType = contentType.substringBefore(';').trim().lowercase()
        require(normalizedContentType in SUPPORTED_COVER_TYPES) { "Unsupported artwork content type" }
        val bytes = input.use { it.readBytes() }
        require(bytes.isNotEmpty()) { "Artwork file is empty" }
        require(bytes.size <= MAX_COVER_BYTES) { "Artwork exceeds 500 KiB" }
        val url = musicCollectionUrl().newBuilder().addPathSegment(musicId).addPathSegment("cover").build()
        val body = bytes.toRequestBody(normalizedContentType.toMediaType())
        val response = try {
            executeSigned("PUT", url, body, SkinRequestCanonicalizer.sha256(bytes))
        } catch (error: SkinApiException) {
            if (error.errorCode != "unknown_installation") throw error
            registerInstallation()
            executeSigned("PUT", url, body, SkinRequestCanonicalizer.sha256(bytes))
        }
        JSONObject(response).optString("coverUrl").takeIf { it.isNotBlank() }
    }

    suspend fun listMusic(sort: String = "newest"): List<CommunityMusic> = withContext(Dispatchers.IO) {
        val url = musicCollectionUrl().newBuilder().addQueryParameter("sort", sort).build()
        val response = try {
            executeSigned("GET", url, EMPTY_BODY, SkinRequestCanonicalizer.sha256(ByteArray(0)))
        } catch (error: SkinApiException) {
            if (error.errorCode != "unknown_installation") throw error
            registerInstallation()
            executeSigned("GET", url, EMPTY_BODY, SkinRequestCanonicalizer.sha256(ByteArray(0)))
        }
        val root = JSONObject(response)
        val items = root.optJSONArray("items")
            ?: if (root.has("error")) throw SkinApiException("Music list response contained an error") else return@withContext emptyList()
        buildList(items.length()) {
            for (index in 0 until items.length()) {
                val item = items.getJSONObject(index)
                add(
                    CommunityMusic(
                        id = item.getString("id"),
                        customName = item.getString("custom_name"),
                        uploaderName = item.getString("uploader_name"),
                        fileSize = item.getLong("file_size"),
                        createdAt = item.getString("created_at"),
                        targetTrack = item.getString("target_track"),
                        coverUrl = coverUrl(item),
                        uploaderAvatarUrl = item.optString("uploader_avatar_url")
                            .takeIf(SaveApiClient::isSafeRemoteAvatarUrl),
                        likes = item.optInt("likes", 0),
                        downloads = item.optInt("downloads", 0),
                        isOwner = item.optBoolean("is_owner", false),
                        likedByMe = item.optBoolean("liked_by_me", false),
                        shareUrl = item.optString("shareUrl").takeIf { it.isNotBlank() },
                    )
                )
            }
        }
    }

    suspend fun deleteMusic(id: String): Boolean = withContext(Dispatchers.IO) {
        try {
            val url = baseUrl.toHttpUrl().newBuilder().addPathSegments("v1/music").addPathSegment(id).build()
            executeSigned("DELETE", url, EMPTY_BODY, SkinRequestCanonicalizer.sha256(ByteArray(0)))
            true
        } catch (e: Exception) {
            Log.e("MusicApiClient", "Failed to delete music $id", e)
            false
        }
    }

    suspend fun setLike(id: String, liked: Boolean): Boolean = withContext(Dispatchers.IO) {
        try {
            val url = baseUrl.toHttpUrl().newBuilder().addPathSegments("v1/music").addPathSegment(id).addPathSegment("like").build()
            executeSigned(if (liked) "POST" else "DELETE", url, if (liked) EMPTY_BODY else ByteArray(0).toRequestBody(null), SkinRequestCanonicalizer.sha256(ByteArray(0)))
            true
        } catch (e: Exception) {
            Log.e("MusicApiClient", "Failed to set like for music $id", e)
            false
        }
    }

    private fun registerInstallation() {
        val snapshot = identity.snapshot()
        val bytes = JSONObject()
            .put("installationId", snapshot.installationId)
            .put("publicKey", snapshot.publicKey.toJson())
            .toString()
            .toByteArray()
        executeSigned("POST", v1("installations"), bytes.toRequestBody(JSON), SkinRequestCanonicalizer.sha256(bytes))
    }

    private fun executeUpload(url: HttpUrl, file: File, hash: String): MusicUploadResponse {
        val response = executeSigned("POST", url, ExactMusicFileRequestBody(file), hash)
        return runCatching {
            val json = JSONObject(response)
            MusicUploadResponse(json.getString("id"), json.getLong("size"), json.getString("sha256"), json.optString("requestId"))
        }.getOrElse { throw SkinApiException("Invalid music service response", cause = it) }
    }

    private fun executeSigned(method: String, url: HttpUrl, body: RequestBody, hash: String): String {
        val pathAndQuery = url.encodedPath + (url.encodedQuery?.let { "?$it" } ?: "")
        val signed = identity.sign(method, pathAndQuery, hash)
        val request = Request.Builder().url(url)
            .method(method, if (method == "GET" || method == "HEAD") null else body)
            .header("X-Installation-ID", signed.installationId)
            .header("Accept", "application/json")
            .header("X-Timestamp", signed.timestamp)
            .header("X-Nonce", signed.nonce)
            .header("X-Signature", signed.signature)
            .build()
        try {
            client.newCall(request).execute().use { response ->
                val text = response.body?.string().orEmpty()
                if (!response.isSuccessful) {
                    val error = SkinApiParser.parseError(text)
                    throw SkinApiException(error?.message ?: "Music service returned HTTP ${response.code}", response.code, error?.code, error?.requestId ?: response.header("X-Request-ID"))
                }
                return text
            }
        } catch (error: SkinApiException) {
            throw error
        } catch (error: IOException) {
            throw SkinApiException("Unable to reach the Community Hub", cause = error)
        }
    }

    private fun musicCollectionUrl(): HttpUrl = baseUrl.toHttpUrl().newBuilder().addPathSegments("v1/music").build()
    private fun v1(vararg segments: String): HttpUrl = baseUrl.toHttpUrl().newBuilder().addPathSegment("v1").apply { segments.forEach(::addPathSegment) }.build()

    private fun coverUrl(item: JSONObject): String? = musicCoverUrl(item)

    internal companion object {
        fun musicCoverUrl(item: JSONObject): String? =
            item.optString("coverUrl").takeIf { it.isNotBlank() }
                ?: item.optString("cover_url").takeIf { it.isNotBlank() }

        private const val MAX_MUSIC_BYTES = 25L * 1024 * 1024
        private const val MAX_COVER_BYTES = 500 * 1024
        private val SUPPORTED_COVER_TYPES = setOf("image/jpeg", "image/png", "image/webp")
        private val JSON = "application/json; charset=utf-8".toMediaType()
        private val EMPTY_BODY = ByteArray(0).toRequestBody(null)
    }
}

data class MusicUploadResponse(val id: String, val size: Long, val sha256: String, val requestId: String?)

private class ExactMusicFileRequestBody(private val file: File) : RequestBody() {
    override fun contentType(): MediaType = "audio/mpeg".toMediaType()
    override fun contentLength(): Long = file.length()
    override fun writeTo(sink: BufferedSink) = FileInputStream(file).use { input ->
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        while (true) {
            val count = input.read(buffer)
            if (count < 0) break
            sink.write(buffer, 0, count)
        }
    }
}

private fun fileSha256(file: File): String {
    val digest = MessageDigest.getInstance("SHA-256")
    FileInputStream(file).use { input ->
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        while (true) {
            val count = input.read(buffer)
            if (count < 0) break
            digest.update(buffer, 0, count)
        }
    }
    return digest.digest().joinToString("") { "%02x".format(it) }
}
