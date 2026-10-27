package com.retr0.projectretr0.data.skins

import android.content.Context
import android.util.Log
import com.retr0.projectretr0.BuildConfig
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

interface SkinDataSource {
    suspend fun list(query: String = "", sort: String = "newest", limit: Int = 20, offset: Int = 0): SkinListResponse
    suspend fun details(id: String): SkinApiSkin
    suspend fun registerInstallation(): Unit = unsupported()
    suspend fun createDraft(draft: SkinDraft, idempotencyKey: String): SkinDraftResponse = unsupported()
    suspend fun uploadBinary(id: String, file: File): SkinMutationResponse = unsupported()
    suspend fun uploadCover(id: String, file: File, contentType: String): SkinMutationResponse = unsupported()
    suspend fun uploadManifest(id: String, manifest: String): SkinMutationResponse = unsupported()
    suspend fun uploadScreenshot(id: String, position: Int, file: File, contentType: String, caption: String = ""): SkinMutationResponse = unsupported()
    suspend fun publish(id: String): SkinMutationResponse = unsupported()
    suspend fun setLike(id: String, liked: Boolean): SkinMutationResponse = unsupported()
    suspend fun report(id: String, reason: SkinReportReason, details: String = ""): SkinMutationResponse = unsupported()
    suspend fun deleteOwned(id: String): SkinMutationResponse = unsupported()

    private fun unsupported(): Nothing = throw UnsupportedOperationException("Authenticated skin writes are unavailable")
}

class SkinApiException(
    message: String,
    val statusCode: Int? = null,
    val errorCode: String? = null,
    val requestId: String? = null,
    val duplicateId: String? = null,
    cause: Throwable? = null,
) : IOException(message, cause)

class SkinApiClient(
    context: Context? = null,
    private val baseUrl: String = BuildConfig.COMMUNITY_API_BASE_URL,
    private val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS).readTimeout(70, TimeUnit.SECONDS).writeTimeout(70, TimeUnit.SECONDS).callTimeout(90, TimeUnit.SECONDS).build(),
) : SkinDataSource {
    private val identity = context?.applicationContext?.let(::SkinInstallationIdentity)

    override suspend fun list(query: String, sort: String, limit: Int, offset: Int): SkinListResponse = io {
        val url = v1("skins").newBuilder().addQueryParameter("sort", sort).addQueryParameter("limit", limit.coerceIn(1, 50).toString())
            .addQueryParameter("offset", offset.coerceIn(0, 10_000).toString()).apply { if (query.isNotBlank()) addQueryParameter("query", query.trim()) }.build()
        execute(Request.Builder().url(url).get().build(), SkinApiParser::parseList)
    }

    override suspend fun details(id: String): SkinApiSkin = io {
        execute(Request.Builder().url(skinUrl(id)).get().build(), SkinApiParser::parseDetails)
    }

    override suspend fun registerInstallation(): Unit = io {
        val auth = requireIdentity()
        val body = JSONObject().put("installationId", auth.installationId).put("publicKey", auth.publicJwk().toJson()).toString().toByteArray()
        executeSigned("POST", v1("installations"), body.toRequestBody(JSON), SkinRequestCanonicalizer.sha256(body)) { Unit }
    }

    override suspend fun createDraft(draft: SkinDraft, idempotencyKey: String): SkinDraftResponse = io {
        require(idempotencyKey.matches(Regex("[A-Za-z0-9._:-]{8,128}"))) { "Invalid idempotency key" }
        val bytes = draft.toJson().toString().toByteArray()
        executeSigned("POST", v1("skins"), bytes.toRequestBody(JSON), SkinRequestCanonicalizer.sha256(bytes), mapOf("Idempotency-Key" to idempotencyKey), SkinApiParser::parseDraft)
    }

    override suspend fun uploadBinary(id: String, file: File): SkinMutationResponse = uploadFile(skinUrl(id, "binary"), file, "application/octet-stream")
    override suspend fun uploadCover(id: String, file: File, contentType: String): SkinMutationResponse = uploadFile(skinUrl(id, "cover"), file, contentType)
    override suspend fun uploadManifest(id: String, manifest: String): SkinMutationResponse = io {
        val bytes = manifest.toByteArray(Charsets.UTF_8)
        Log.i("SKIN_API", "manifest_upload_start method=PUT path=/v1/skins/$id/manifest bytes=${bytes.size}")
        executeSigned("PUT", manifestUrl(id), bytes.toRequestBody(JSON), SkinRequestCanonicalizer.sha256(bytes), parser = SkinApiParser::parseMutation)
    }
    override suspend fun uploadScreenshot(id: String, position: Int, file: File, contentType: String, caption: String): SkinMutationResponse {
        require(position in 1..4) { "Screenshot position must be 1-4" }
        val url = skinUrl(id, "screenshots", position.toString()).newBuilder().apply { if (caption.isNotBlank()) addQueryParameter("caption", caption) }.build()
        return uploadFile(url, file, contentType)
    }

    override suspend fun publish(id: String) = emptyMutation("POST", skinUrl(id, "publish"))
    override suspend fun setLike(id: String, liked: Boolean) = emptyMutation(if (liked) "POST" else "DELETE", skinUrl(id, "like"))
    override suspend fun report(id: String, reason: SkinReportReason, details: String): SkinMutationResponse = io {
        val bytes = JSONObject().put("reason", reason.wireValue).put("details", details).toString().toByteArray()
        executeSigned("POST", skinUrl(id, "report"), bytes.toRequestBody(JSON), SkinRequestCanonicalizer.sha256(bytes), parser = SkinApiParser::parseMutation)
    }
    override suspend fun deleteOwned(id: String) = emptyMutation("DELETE", skinUrl(id))

    private suspend fun uploadFile(url: HttpUrl, file: File, contentType: String): SkinMutationResponse = io {
        require(file.isFile && file.length() > 0) { "Upload file is empty or missing" }
        val hash = fileSha256(file)
        executeSigned("PUT", url, ExactFileRequestBody(file, contentType.toMediaType()), hash, parser = SkinApiParser::parseMutation)
    }

    private suspend fun emptyMutation(method: String, url: HttpUrl): SkinMutationResponse = io {
        val body = if (method == "POST") ByteArray(0).toRequestBody(null) else null
        executeSigned(method, url, body, SkinRequestCanonicalizer.sha256(ByteArray(0)), parser = SkinApiParser::parseMutation)
    }

    private fun <T> executeSigned(method: String, url: HttpUrl, body: RequestBody?, hash: String, headers: Map<String, String> = emptyMap(), parser: (String) -> T): T {
        val signed = requireIdentity().sign(method, encodedPathAndQuery(url), hash)
        val request = Request.Builder().url(url).method(method, body)
            .header("X-Installation-ID", signed.installationId).header("X-Timestamp", signed.timestamp)
            .header("X-Nonce", signed.nonce).header("X-Signature", signed.signature)
            .apply { headers.forEach { (name, value) -> header(name, value) } }.build()
        return execute(request, parser)
    }

    private fun <T> execute(request: Request, parser: (String) -> T): T = try {
        client.newCall(request).execute().use { response ->
            val body = response.body?.string().orEmpty()
            if (!response.isSuccessful) {
                val error = SkinApiParser.parseError(body)
                val requestId = error?.requestId ?: response.header("X-Request-ID")
                Log.e(
                    "SKIN_API",
                    "request_failed method=${request.method} path=${request.url.encodedPath} status=${response.code} " +
                        "errorCode=${error?.code ?: "unknown"} requestId=${requestId ?: "unknown"}",
                )
                throw SkinApiException(error?.message ?: "Skin service returned ${response.code}", response.code, error?.code, requestId, error?.duplicateId)
            }
            runCatching { parser(body) }.getOrElse { throw SkinApiException("Invalid skin service response", response.code, requestId = response.header("X-Request-ID"), cause = it) }
        }
    } catch (error: SkinApiException) { throw error }
      catch (error: IOException) { throw SkinApiException("Unable to reach the skin service", cause = error) }

    private fun requireIdentity() = identity ?: throw SkinApiException("Authenticated skin writes require an Android application context")
    private fun v1(vararg segments: String): HttpUrl = baseUrl.toHttpUrl().newBuilder().addPathSegment("v1").apply { segments.forEach(::addPathSegment) }.build()
    private fun skinUrl(id: String, vararg segments: String): HttpUrl {
        require(UUID.matches(id)) { "Skin ID must be a UUID" }
        return v1("skins", id.lowercase(), *segments)
    }

    private fun manifestUrl(id: String): HttpUrl {
        require(UUID.matches(id)) { "Skin ID must be a UUID" }
        return v1("skins", id.lowercase(), "manifest")
    }
    private fun encodedPathAndQuery(url: HttpUrl) = url.encodedPath + (url.encodedQuery?.let { "?$it" } ?: "")
    private suspend fun <T> io(block: () -> T): T = withContext(Dispatchers.IO) { block() }

    private companion object {
        val JSON = "application/json; charset=utf-8".toMediaType()
        val UUID = Regex("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}")
    }
}

private class ExactFileRequestBody(private val file: File, private val type: MediaType) : RequestBody() {
    override fun contentType() = type
    override fun contentLength() = file.length()
    override fun writeTo(sink: BufferedSink) = FileInputStream(file).use { input ->
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        while (true) { val count = input.read(buffer); if (count < 0) break; sink.write(buffer, 0, count) }
    }
}

private fun fileSha256(file: File): String {
    val digest = MessageDigest.getInstance("SHA-256")
    FileInputStream(file).use { input ->
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        while (true) { val count = input.read(buffer); if (count < 0) break; digest.update(buffer, 0, count) }
    }
    return digest.digest().joinToString("") { "%02x".format(it) }
}
