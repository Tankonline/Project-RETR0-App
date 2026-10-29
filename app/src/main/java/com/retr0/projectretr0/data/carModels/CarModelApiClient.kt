package com.retr0.projectretr0.data.carModels

import android.content.Context
import com.retr0.projectretr0.BuildConfig
import com.retr0.projectretr0.data.skins.SkinApiException
import com.retr0.projectretr0.data.skins.SkinApiParser
import com.retr0.projectretr0.data.skins.SkinInstallationIdentity
import com.retr0.projectretr0.data.skins.SkinRequestCanonicalizer
import com.retr0.projectretr0.data.skins.SkinMutationResponse
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

/** Signed, transport-only client for generic car-model packages. It deliberately has no UI or upload orchestration. */
internal interface CarModelUploadApi {
    suspend fun createDraft(metadata: JSONObject, idempotencyKey: String): CarModelApiClient.CarModelDraftResponse
    suspend fun getDraft(modelId: String): CarModelApiClient.CarModelDetailsResponse
    suspend fun uploadManifest(modelId: String, manifest: String): SkinMutationResponse
    suspend fun uploadFile(modelId: String, relativePath: String, file: File, contentType: String): SkinMutationResponse
    suspend fun uploadCover(modelId: String, file: File, contentType: String): SkinMutationResponse
    suspend fun uploadScreenshot(modelId: String, position: Int, file: File, contentType: String): SkinMutationResponse
    suspend fun deleteOwned(modelId: String): CarModelApiClient.CarModelDeleteResponse
}

class CarModelApiClient(
    context: Context? = null,
    baseUrl: String = BuildConfig.COMMUNITY_API_BASE_URL,
    private val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS).readTimeout(70, TimeUnit.SECONDS)
        .writeTimeout(70, TimeUnit.SECONDS).callTimeout(90, TimeUnit.SECONDS).build(),
) : CarModelUploadApi {
    private val base = requireHttpsBase(baseUrl)
    private val identity = context?.applicationContext?.let(::SkinInstallationIdentity)


    init {
        // Keep startup diagnostics limited to routing metadata; never log credentials or signed paths.
        CarModelLog.info("client_initialized", "host=${base.host} buildType=${if (BuildConfig.DEBUG) "debug" else "release"}")
    }

    /** Public, read-only catalog routes. They intentionally carry no installation identity or write signature. */
    suspend fun listPublished(query: String = "", sort: String = "newest"): List<CommunityCarModel> = io {
        val url = paths.collection().newBuilder().addQueryParameter("limit", "50").addQueryParameter("sort", sort).build()
        val response = executePublic("GET", url)
        parseCatalog(response).filter { query.isBlank() || it.matches(query) }
    }

    suspend fun getPublishedDetails(modelId: String): CommunityCarModel = io {
        parseModel(executePublic("GET", paths.model(modelId)))
    }

    internal fun parseCatalogForTest(body: String): List<CommunityCarModel> = parseCatalog(body)
    internal fun parseModelForTest(body: String): CommunityCarModel = parseModel(body)

    override suspend fun getDraft(modelId: String): CarModelDetailsResponse = io {
        executeSigned("GET", paths.model(modelId), null, sha256(ByteArray(0))) { body ->
            val json = JSONObject(body)
            val files = json.optJSONArray("files")
            CarModelDetailsResponse(
                modelId = json.optString("id", modelId),
                status = json.optString("status", "draft"),
                manifestSha256 = json.optString("manifestSha256").takeIf { it.isNotBlank() },
                files = if (files == null) emptyList() else (0 until files.length()).map { index ->
                    val item = files.getJSONObject(index)
                    RemoteFile(item.optString("path", item.optString("sourcePath")), item.optLong("size", item.optLong("sizeBytes", -1L)), item.optString("sha256"))
                },
                coverUploaded = json.optBoolean("coverUploaded", json.optString("coverUrl").isNotBlank()),
                screenshotPositions = json.optJSONArray("screenshotPositions")?.let { positions -> (0 until positions.length()).map { positions.getInt(it) }.toSet() } ?: emptySet(),
            )
        }
    }

    override suspend fun createDraft(metadata: JSONObject, idempotencyKey: String): CarModelDraftResponse = io {
        require(idempotencyKey.matches(IDEMPOTENCY_KEY)) { "Invalid idempotency key" }
        val bytes = metadata.toString().toByteArray(Charsets.UTF_8)
        require(bytes.size <= MAX_METADATA_BYTES) { "Draft metadata exceeds $MAX_METADATA_BYTES bytes" }
        executeSigned("POST", paths.collection(), bytes.toRequestBody(JSON), sha256(bytes), mapOf("Idempotency-Key" to idempotencyKey)) { body ->
            val json = JSONObject(body)
            CarModelDraftResponse(
                modelId = json.optString("modelId").ifBlank { json.optString("id") }.also { require(it.isNotBlank()) { "modelId is required" } },
                status = json.optString("status").ifBlank { "draft" },
                requestId = json.optString("requestId").takeIf { it.isNotBlank() },
            )
        }
    }

    override suspend fun uploadManifest(modelId: String, manifest: String): SkinMutationResponse = io {
        val bytes = manifest.toByteArray(Charsets.UTF_8)
        require(bytes.isNotEmpty() && bytes.size <= MAX_MANIFEST_BYTES) { "Manifest is empty or exceeds $MAX_MANIFEST_BYTES bytes" }
        validateManifest(bytes)
        executeSigned("PUT", paths.model(modelId, "manifest"), bytes.toRequestBody(JSON), sha256(bytes), parser = SkinApiParser::parseMutation)
    }

    override suspend fun uploadFile(modelId: String, relativePath: String, file: File, contentType: String): SkinMutationResponse =
        uploadFile(paths.file(modelId, relativePath), file, contentType)

    override suspend fun uploadCover(modelId: String, file: File, contentType: String): SkinMutationResponse =
        uploadFile(paths.model(modelId, "cover"), file, contentType)

    override suspend fun uploadScreenshot(modelId: String, position: Int, file: File, contentType: String): SkinMutationResponse {
        require(position in 1..8) { "Screenshot position must be 1-8" }
        return uploadFile(paths.model(modelId, "screenshots", position.toString()), file, contentType)
    }

    override suspend fun deleteOwned(modelId: String): CarModelDeleteResponse = io {
        executeSigned("DELETE", paths.model(modelId), null, sha256(ByteArray(0))) { body ->
            val json = JSONObject(body)
            CarModelDeleteResponse(
                deleted = json.optBoolean("deleted", false),
                requestId = json.optString("requestId").takeIf { it.isNotBlank() },
            )
        }
    }

    suspend fun publish(modelId: String): SkinMutationResponse = io {
        executeSigned("POST", paths.model(modelId, "publish"), ByteArray(0).toRequestBody(null), sha256(ByteArray(0)), parser = SkinApiParser::parseMutation)
    }

    private suspend fun uploadFile(url: HttpUrl, file: File, contentType: String): SkinMutationResponse = io {
        require(file.isFile && file.length() > 0) { "Upload file is empty or missing" }
        require(file.length() <= MAX_FILE_BYTES) { "Upload file exceeds $MAX_FILE_BYTES bytes" }
        val mediaType = contentType.trim().takeIf { it.isNotEmpty() }?.toMediaType() ?: error("Content type is required")
        executeSigned("PUT", url, StreamingRequestBody(file, mediaType), fileSha256(file), parser = SkinApiParser::parseMutation)
    }

    private fun <T> executeSigned(
        method: String,
        url: HttpUrl,
        body: RequestBody?,
        hash: String,
        headers: Map<String, String> = emptyMap(),
        allowRegistrationRetry: Boolean = true,
        parser: (String) -> T,
    ): T {
        CarModelLog.step("request_start method=$method host=${url.host} path=${url.encodedPath} stage=${if (allowRegistrationRetry) "operation" else "registration"}")
        val signed = requireIdentity().sign(method, encodedPathAndQuery(url), hash)
        val request = Request.Builder().url(url).method(method, body)
            .header("X-Installation-ID", signed.installationId).header("X-Timestamp", signed.timestamp)
            .header("X-Nonce", signed.nonce).header("X-Signature", signed.signature)
            .apply { headers.forEach { (name, value) -> header(name, value) } }.build()
        try {
            return client.newCall(request).execute().use { response ->
                val responseBody = response.body?.string().orEmpty()
                val requestId = response.header("X-Request-ID")
                CarModelLog.step("response method=$method path=${url.encodedPath} status=${response.code} requestId=${requestId ?: "none"}")
                if (!response.isSuccessful) {
                    val error = SkinApiParser.parseError(responseBody)
                    val resolvedCode = error?.code ?: "http_${response.code}"
                    val resolvedRequestId = error?.requestId ?: requestId
                    val safeBody = responseBody
                        .replace(Regex("(?i)(x-signature|authorization|auth_key|private_key|secret)\\s*[:=]\\s*[^,}\\s]+"), "$1=<redacted>")
                        .take(2000)
                    CarModelLog.fail(
                        CarModelErrorCode.UNEXPECTED,
                        "api_rejected method=$method path=${url.encodedPath} status=${response.code} " +
                            "code=$resolvedCode requestId=${resolvedRequestId ?: "none"} " +
                            "body=${safeBody.ifBlank { "<empty>" }}"
                    )
                    CarModelLog.step("response_error path=${url.encodedPath} code=$resolvedCode requestId=${resolvedRequestId ?: "none"}")
                    throw SkinApiException(error?.message ?: "Car-model service returned ${response.code}", response.code, error?.code, resolvedRequestId, error?.duplicateId)
                }
                runCatching { parser(responseBody) }.getOrElse { throw SkinApiException("Invalid car-model service response", response.code, requestId = requestId, cause = it) }
            }
        } catch (error: SkinApiException) {
            if (allowRegistrationRetry && error.errorCode == "unknown_installation") {
                CarModelLog.info("registration_retry", "unknown_installation path=${url.encodedPath}")
                registerInstallation()
                return executeSigned(method, url, body, hash, headers, allowRegistrationRetry = false, parser = parser)
            }
            CarModelLog.fail(CarModelErrorCode.HUB_UNAVAILABLE, "request_failed path=${url.encodedPath} status=${error.statusCode ?: "none"} code=${error.errorCode ?: "transport"} requestId=${error.requestId ?: "none"}")
            throw error
        } catch (error: IOException) {
            CarModelLog.fail(CarModelErrorCode.HUB_UNAVAILABLE, "request_failed path=${url.encodedPath} cause=${error.javaClass.simpleName}")
            throw SkinApiException("Unable to reach the car-model service", cause = error)
        }
    }

    private fun executePublic(method: String, url: HttpUrl): String {
        CarModelLog.step("public_request method=$method host=${url.host} path=${url.encodedPath}")
        try {
            return client.newCall(Request.Builder().url(url).get().build()).execute().use { response ->
                val body = response.body?.string().orEmpty()
                CarModelLog.step("public_response path=${url.encodedPath} status=${response.code}")
                if (!response.isSuccessful) throw SkinApiException("Car-model catalog returned ${response.code}", response.code)
                body
            }
        } catch (error: IOException) {
            CarModelLog.fail(CarModelErrorCode.HUB_UNAVAILABLE, "public_request_failed path=${url.encodedPath} cause=${error.javaClass.simpleName}")
            throw SkinApiException("Unable to reach the car-model catalog", cause = error)
        }
    }

    private fun parseCatalog(body: String): List<CommunityCarModel> {
        val items = JSONObject(body).optJSONArray("items") ?: return emptyList()
        return (0 until items.length()).mapNotNull { index ->
            val model = runCatching { parseModel(items.getJSONObject(index)) }.getOrNull()
            // A catalog row is downloadable only when the Worker supplied the identity and
            // asset metadata produced by its R2 integrity check. Never render D1-only ghosts.
            model?.takeIf {
                it.id.isNotBlank() && it.name.isNotBlank() && it.fileCount > 0 &&
                    it.totalBytes > 0L && it.coverUrl?.isNotBlank() == true
            }
        }
    }

    private fun parseModel(body: String): CommunityCarModel = parseModel(JSONObject(body))
    private fun parseModel(json: JSONObject): CommunityCarModel {
        val screenshots = json.optJSONArray("screenshotPositions")
        val base = baseUrlForAsset(json.optString("id"))
        return CommunityCarModel(
            id = json.optString("id"), name = json.optString("title"), creator = json.optString("creatorName"),
            description = json.optString("description"), vehicleDirectory = json.optString("targetVehicleDirectory"),
            supportedPackages = listOfNotNull(json.optString("packageId").takeIf { it.isNotBlank() }),
            gameBuild = json.optString("supportedVersions"), fileCount = json.optInt("fileCount"),
            totalBytes = json.optLong("totalSize"), likes = json.optInt("likes"), downloads = 0,
            verifiedManifest = json.optString("manifestSha256").matches(Regex("[0-9a-fA-F]{64}")), updatedAt = "",
            coverUrl = json.optString("coverUrl").takeIf { it.isNotBlank() },
            screenshotUrls = if (screenshots == null) emptyList() else (0 until screenshots.length()).map { "$base/screenshots/${screenshots.getInt(it)}" },
            shareUrl = null,
        )
    }
    private fun baseUrlForAsset(id: String) = paths.model(id).toString()
    private fun CommunityCarModel.matches(query: String): Boolean = listOf(name, creator, description, vehicleDirectory).any { it.contains(query, true) }

    /** Registration deliberately disables the fallback to avoid recursive registration on a bad identity/route. */
    private fun registerInstallation() {
        val auth = requireIdentity()
        val snapshot = auth.snapshot()
        val bytes = JSONObject().put("installationId", snapshot.installationId).put("publicKey", snapshot.publicKey.toJson()).toString().toByteArray(Charsets.UTF_8)
        CarModelLog.info("registration_start", "host=${base.host} path=/v1/installations")
        executeSigned("POST", base.newBuilder().addPathSegments("v1/installations").build(), bytes.toRequestBody(JSON), sha256(bytes), allowRegistrationRetry = false) { }
    }

    private fun requireIdentity() = identity ?: throw SkinApiException("Authenticated car-model writes require an Android application context")
    private fun encodedPathAndQuery(url: HttpUrl) = url.encodedPath + (url.encodedQuery?.let { "?$it" } ?: "")
    private suspend fun <T> io(block: () -> T): T = withContext(Dispatchers.IO) { block() }

    private fun validateManifest(bytes: ByteArray) {
        val root = runCatching { JSONObject(String(bytes, Charsets.UTF_8)) }.getOrElse { throw IllegalArgumentException("Manifest must be a JSON object", it) }
        root.optJSONObject("metadata")?.let { requireNoAbsolutePaths(it, "metadata") }
        root.optJSONObject("target")?.let { target ->
            listOf("relativeRoot", "vehicleDirectory").forEach { key -> target.optString(key).takeIf(String::isNotBlank)?.let { safeRelativePath(it) } }
        }
        root.optJSONArray("files")?.let { files -> (0 until files.length()).forEach { index ->
            val item = files.getJSONObject(index)
            listOf("sourcePath", "destinationPath").forEach { key -> item.optString(key).takeIf(String::isNotBlank)?.let(::safeRelativePath) }
            val destination = item.optString("destinationPath")
            require(!destination.contains("paste to depot", ignoreCase = true)) {
                "Display label found in manifest destination at files[$index]"
            }
        } }
        requireNoAbsolutePaths(root, "manifest")
    }

    private fun requireNoAbsolutePaths(value: Any, location: String) {
        when (value) {
            is JSONObject -> value.keys().forEach { key -> if (!value.isNull(key)) requireNoAbsolutePaths(value.get(key), "$location.$key") }
            is org.json.JSONArray -> (0 until value.length()).forEach { requireNoAbsolutePaths(value.get(it), "$location[$it]") }
            is String -> require(!ABSOLUTE_PATH.containsMatchIn(value)) { "Absolute device paths are not allowed in $location" }
        }
    }

    companion object {
        const val MAX_FILE_BYTES = 100L * 1024 * 1024
        const val MAX_MANIFEST_BYTES = 10L * 1024 * 1024
        const val MAX_METADATA_BYTES = 256L * 1024
        private val IDEMPOTENCY_KEY = Regex("[A-Za-z0-9._:-]{8,128}")
        private val ABSOLUTE_PATH = Regex("^(?:/|[A-Za-z]:[\\\\/])")
        internal fun safeRelativePath(path: String): String = path.replace('\\', '/').also {
            require(it.isNotBlank() && !it.startsWith('/') && !Regex("^[A-Za-z]:").containsMatchIn(it)) { "Path must be relative: $path" }
            require(it.split('/').none { part -> part.isEmpty() || part == "." || part == ".." }) { "Unsafe relative path: $path" }
        }
        internal fun requireHttpsBase(value: String): HttpUrl {
            require(value.isNotBlank()) { "Car-model API base URL must not be blank" }
            val url = runCatching { value.toHttpUrl() }.getOrElse { throw IllegalArgumentException("Invalid car-model API base URL", it) }
            require(url.scheme == "https" && url.host.isNotBlank()) { "Car-model API requires HTTPS" }
            return url
        }
        internal fun validateManifestForTest(manifest: String) {
            val root = runCatching { JSONObject(manifest) }.getOrElse { throw IllegalArgumentException("Manifest must be a JSON object", it) }
            requireNoAbsolutePathsForTest(root, "manifest")
        }
        private fun requireNoAbsolutePathsForTest(value: Any, location: String) {
            when (value) {
                is JSONObject -> value.keys().forEach { key -> if (!value.isNull(key)) requireNoAbsolutePathsForTest(value.get(key), "$location.$key") }
                is org.json.JSONArray -> (0 until value.length()).forEach { requireNoAbsolutePathsForTest(value.get(it), "$location[$it]") }
                is String -> require(!ABSOLUTE_PATH.containsMatchIn(value)) { "Absolute device paths are not allowed in $location" }
            }
        }
        private fun sha256(bytes: ByteArray) = SkinRequestCanonicalizer.sha256(bytes)
        private fun fileSha256(file: File): String { val digest = MessageDigest.getInstance("SHA-256"); FileInputStream(file).use { input -> val buffer = ByteArray(8192); while (true) { val count = input.read(buffer); if (count < 0) break; digest.update(buffer, 0, count) } }; return digest.digest().joinToString("") { "%02x".format(it) } }
        private const val JSON_VALUE = "application/json; charset=utf-8"
        private val JSON = JSON_VALUE.toMediaType()
    }

    data class RemoteFile(val path: String, val size: Long, val sha256: String)
    data class CarModelDetailsResponse(
        val modelId: String,
        val status: String,
        val manifestSha256: String?,
        val files: List<RemoteFile>,
        val coverUploaded: Boolean,
        val screenshotPositions: Set<Int>,
    )

    data class CarModelDraftResponse(val modelId: String, val status: String, val requestId: String?)
    data class CarModelDeleteResponse(val deleted: Boolean, val requestId: String?)

    private class Paths(private val base: HttpUrl) {
        fun collection() = base.newBuilder().addPathSegments("v1/car-models").build()
        fun model(id: String, vararg segments: String): HttpUrl { requireId(id); return base.newBuilder().addPathSegments("v1/car-models").addPathSegment(id).apply { segments.forEach(::addPathSegment) }.build() }
        fun file(id: String, relative: String): HttpUrl { val safe = safeRelativePath(relative); return base.newBuilder().addPathSegments("v1/car-models").addPathSegment(id).addPathSegments("files").apply { safe.split('/').forEach(::addPathSegment) }.build() }
        private fun requireId(id: String) { require(id.isNotBlank() && !id.contains('/') && !id.contains('\\') && id != "." && id != "..") { "Invalid model id" } }
    }
    private val paths = Paths(base)
}

private class StreamingRequestBody(private val file: File, private val type: MediaType) : RequestBody() {
    override fun contentType() = type
    override fun contentLength() = file.length()
    override fun writeTo(sink: BufferedSink) = FileInputStream(file).use { input -> val buffer = ByteArray(8192); while (true) { val count = input.read(buffer); if (count < 0) break; sink.write(buffer, 0, count) } }
}
