package com.example.projectretr0installer.data.skins

import android.content.Context
import android.util.Log
import com.example.projectretr0installer.BuildConfig
import com.example.projectretr0installer.installer.CommunitySave
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
import java.io.IOException
import java.security.MessageDigest
import java.util.concurrent.TimeUnit

class SaveApiClient(
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
        saveName: String,
        uploaderName: String,
        level: Int,
        gold: Long,
        rs: Long,
        ms: Long,
    ): SaveUploadResponse = withContext(Dispatchers.IO) {
        require(file.isFile && file.length() > 0) { "Save file is empty or missing" }
        require(file.length() <= MAX_SAVE_BYTES) { "Save file exceeds 25 MiB" }
        val url = savesCollectionUrl().newBuilder()
            .addQueryParameter("name", saveName.trim())
            .addQueryParameter("user", uploaderName.trim())
            .addQueryParameter("level", level.coerceAtLeast(0).toString())
            .addQueryParameter("gold", gold.coerceAtLeast(0).toString())
            .addQueryParameter("rs", rs.coerceAtLeast(0).toString())
            .addQueryParameter("ms", ms.coerceAtLeast(0).toString())
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

    suspend fun listSaves(sort: String = "newest"): List<CommunitySave> = withContext(Dispatchers.IO) {
        val url = savesCollectionUrl().newBuilder().addQueryParameter("sort", sort).build()
        val response = try {
            executeSigned("GET", url, EMPTY_BODY, SkinRequestCanonicalizer.sha256(ByteArray(0)))
        } catch (error: SkinApiException) {
            if (error.errorCode != "unknown_installation") throw error
            registerInstallation()
            executeSigned("GET", url, EMPTY_BODY, SkinRequestCanonicalizer.sha256(ByteArray(0)))
        }
        val root = JSONObject(response)
        val items = root.optJSONArray("items")
            ?: if (root.has("error")) throw SkinApiException("Save list response contained an error") else return@withContext emptyList()
        buildList(items.length()) {
            for (index in 0 until items.length()) {
                val item = items.getJSONObject(index)
                add(
                    CommunitySave(
                        id = item.getString("id"),
                        customName = item.getString("custom_name"),
                        uploaderName = item.getString("uploader_name"),
                        fileSize = item.getLong("file_size"),
                        createdAt = item.getString("created_at"),
                        playerLevel = item.optInt("player_level", 0),
                        playerGold = item.optLong("player_gold", 0),
                        playerRs = item.optLong("player_rs", 0),
                        playerMs = item.optLong("player_ms", 0),
                        likes = item.optInt("likes", 0),
                        downloads = item.optInt("downloads", 0),
                        isOwner = item.optBoolean("is_owner", false),
                        likedByMe = item.optBoolean("liked_by_me", false),
                        shareUrl = item.optString("shareUrl").takeIf { it.isNotBlank() },
                        uploaderAvatarUrl = item.optString("uploader_avatar_url")
                            .takeIf(::isSafeRemoteAvatarUrl),
                    )
                )
            }
        }
    }

    internal fun registerInstallationIfNeeded() {
        val snapshot = identity.snapshot()
        val bytes = JSONObject()
            .put("installationId", snapshot.installationId)
            .put("publicKey", snapshot.publicKey.toJson())
            .toString()
            .toByteArray()
        executeSigned("POST", v1("installations"), bytes.toRequestBody(JSON), SkinRequestCanonicalizer.sha256(bytes))
    }

    suspend fun deleteInstallation(): Boolean = withContext(Dispatchers.IO) {
        try {
            executeSigned("DELETE", v1("installations"), EMPTY_BODY, SkinRequestCanonicalizer.sha256(ByteArray(0)))
            true
        } catch (e: Exception) {
            Log.e("SaveApiClient", "Failed to delete remote installation", e)
            false
        }
    }

    suspend fun deleteSave(id: String): Boolean = withContext(Dispatchers.IO) {
        try {
            val url = baseUrl.toHttpUrl().newBuilder().addPathSegments("v1/saves").addPathSegment(id).build()
            executeSigned("DELETE", url, EMPTY_BODY, SkinRequestCanonicalizer.sha256(ByteArray(0)))
            true
        } catch (e: Exception) {
            Log.e("SaveApiClient", "Failed to delete save $id", e)
            false
        }
    }

    suspend fun setLike(id: String, liked: Boolean): Boolean = withContext(Dispatchers.IO) {
        try {
            val url = baseUrl.toHttpUrl().newBuilder().addPathSegments("v1/saves").addPathSegment(id).addPathSegment("like").build()
            executeSigned(if (liked) "POST" else "DELETE", url, if (liked) EMPTY_BODY else ByteArray(0).toRequestBody(null), SkinRequestCanonicalizer.sha256(ByteArray(0)))
            true
        } catch (e: Exception) {
            Log.e("SaveApiClient", "Failed to set like for save $id", e)
            false
        }
    }

    suspend fun uploadAvatar(file: File): Boolean = withContext(Dispatchers.IO) {
        require(file.isFile && file.length() > 0) { "Avatar file is empty or missing" }
        require(file.length() <= MAX_AVATAR_BYTES) { "Avatar image exceeds 200 KiB" }
        val url = v1("installations", "avatar")
        val hash = fileSha256(file)
        val contentType = when (file.extension.lowercase()) {
            "png" -> "image/png"
            "jpg", "jpeg" -> "image/jpeg"
            "webp" -> "image/webp"
            else -> return@withContext false
        }
        try {
            JSONObject(executeSigned("PUT", url, ExactSaveFileRequestBody(file, contentType.toMediaType()), hash))
                .optBoolean("success", false)
        } catch (error: SkinApiException) {
            if (error.errorCode != "unknown_installation") {
                Log.e("SaveApiClient", "Avatar upload failed", error)
                false
            } else {
                runCatching {
                    registerInstallation()
                    JSONObject(executeSigned("PUT", url, ExactSaveFileRequestBody(file, contentType.toMediaType()), hash))
                        .optBoolean("success", false)
                }.onFailure { Log.e("SaveApiClient", "Avatar upload retry failed", it) }.getOrDefault(false)
            }
        } catch (error: Exception) {
            Log.e("SaveApiClient", "Avatar upload failed", error)
            false
        }
    }

    suspend fun deleteAvatar(): Boolean = withContext(Dispatchers.IO) {
        try {
            JSONObject(executeSigned("DELETE", v1("installations", "avatar"), EMPTY_BODY, SkinRequestCanonicalizer.sha256(ByteArray(0))))
                .optBoolean("success", false)
        } catch (e: Exception) {
            Log.e("SaveApiClient", "Avatar deletion failed", e)
            false
        }
    }

    private fun registerInstallation() = registerInstallationIfNeeded()

    private fun executeUpload(url: HttpUrl, file: File, hash: String): SaveUploadResponse {
        val response = executeSigned("POST", url, ExactSaveFileRequestBody(file), hash)
        return runCatching {
            val json = JSONObject(response)
            SaveUploadResponse(json.getString("id"), json.getLong("size"), json.getString("sha256"), json.optString("requestId"))
        }.getOrElse { throw SkinApiException("Invalid save service response", cause = it) }
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
            val startedAt = System.currentTimeMillis()
            client.newCall(request).execute().use { response ->
                Log.i(
                    AUTH_DIAGNOSTIC_TAG,
                    "SIGNED_REQUEST method=$method path=${url.encodedPath} status=${response.code} " +
                        "requestId=${response.header("X-Request-ID") ?: "ABSENT"} " +
                        "contentType=${response.header("Content-Type") ?: "ABSENT"} " +
                        "elapsedMs=${System.currentTimeMillis() - startedAt} " +
                        "installationIdPresent=${request.header("X-Installation-ID") != null} " +
                        "timestampPresent=${request.header("X-Timestamp") != null} " +
                        "noncePresent=${request.header("X-Nonce") != null} " +
                        "signaturePresent=${request.header("X-Signature") != null}",
                )
                val text = response.body?.string().orEmpty()
                if (!response.isSuccessful) {
                    val error = SkinApiParser.parseError(text)
                    throw SkinApiException(error?.message ?: "Save service returned HTTP ${response.code}", response.code, error?.code, error?.requestId ?: response.header("X-Request-ID"))
                }
                return text
            }
        } catch (error: SkinApiException) {
            throw error
        } catch (error: IOException) {
            throw SkinApiException("Unable to reach the Community Hub", cause = error)
        }
    }

    private fun savesCollectionUrl(): HttpUrl = baseUrl.toHttpUrl().newBuilder().addPathSegments("v1/saves").build()
    private fun v1(vararg segments: String): HttpUrl = baseUrl.toHttpUrl().newBuilder().addPathSegment("v1").apply { segments.forEach(::addPathSegment) }.build()

    companion object {
        private const val AUTH_DIAGNOSTIC_TAG = "RETR0_AUTH_DIAG"

        fun isSafeRemoteAvatarUrl(value: String): Boolean = runCatching {
            val remote = value.toHttpUrl()
            val community = BuildConfig.COMMUNITY_API_BASE_URL.toHttpUrl()
            remote.scheme == "https" && remote.host == community.host && remote.port == community.port
        }.getOrDefault(false)

        const val MAX_SAVE_BYTES = 25L * 1024 * 1024
        const val MAX_AVATAR_BYTES = 200L * 1024
        val JSON = "application/json; charset=utf-8".toMediaType()
        val EMPTY_BODY = ByteArray(0).toRequestBody(null)
    }
}

data class SaveUploadResponse(val id: String, val size: Long, val sha256: String, val requestId: String?)

private class ExactSaveFileRequestBody(private val file: File, private val mediaType: MediaType = "application/octet-stream".toMediaType()) : RequestBody() {
    override fun contentType(): MediaType = mediaType
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
