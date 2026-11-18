package com.retr0.projectretr0.data.skins

import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer
import org.junit.After
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import java.io.File
import java.nio.file.Files
import java.security.KeyPairGenerator
import java.security.SecureRandom
import java.security.Signature
import java.security.spec.ECGenParameterSpec
import java.util.Base64
import java.util.UUID

/**
 * Exercises the signed Community save download against an in-process fake Hub that verifies
 * requests exactly as the Worker does (`verifySignedRequest` in save-files-worker.js).
 */
class SaveApiClientDownloadTest {
    private lateinit var workDir: File
    private lateinit var destination: File
    private val signer = SoftwareSigner()
    private val requests = mutableListOf<Request>()

    @Before fun setUp() {
        workDir = Files.createTempDirectory("save-download-test").toFile()
        destination = File(workDir, "download.tmp")
    }

    @After fun tearDown() {
        workDir.deleteRecursively()
    }

    @Test fun signedDownloadMatchesWorkerCanonicalContract() = runBlocking {
        val payload = ByteArray(4096) { (it % 251).toByte() }
        val client = client { request ->
            assertEquals("GET", request.method)
            assertEquals("/v1/saves/$SAVE_ID", request.url.encodedPath)
            assertTrue("signature must verify like the Worker", signer.verifies(request, ByteArray(0)))
            ok(request, payload, contentLength = payload.size.toLong())
        }

        val result = client.download(SAVE_ID, destination)

        assertEquals(payload.size.toLong(), result.size)
        assertEquals("req-1", result.requestId)
        assertArrayEquals(payload, destination.readBytes())
        assertEquals(1, requests.size)
        assertNoTempFiles()
    }

    @Test fun uppercaseIdIsNormalisedBeforeSigning() = runBlocking {
        val client = client { request ->
            assertEquals("/v1/saves/$SAVE_ID", request.url.encodedPath)
            assertTrue(signer.verifies(request, ByteArray(0)))
            ok(request, byteArrayOf(1, 2, 3), contentLength = 3)
        }
        client.download(SAVE_ID.uppercase(), destination)
        assertEquals(3L, destination.length())
    }

    @Test fun unknownInstallationRegistersOnceAndRetriesWithFreshSignature() = runBlocking {
        var registered = false
        val client = client { request ->
            when {
                request.method == "POST" && request.url.encodedPath == "/v1/installations" -> {
                    val body = request.bodyBytes()
                    assertTrue("registration must be self-signed over its exact body", signer.verifies(request, body))
                    registered = true
                    json(request, 201, """{"installationId":"${signer.installationId}","status":"active"}""")
                }
                !registered -> error(request, 401, "unknown_installation")
                else -> ok(request, byteArrayOf(9, 9), contentLength = 2)
            }
        }

        client.download(SAVE_ID, destination)

        assertEquals(listOf("GET", "POST", "GET"), requests.map { it.method })
        val nonces = requests.map { it.header("X-Nonce") }
        assertEquals("every attempt must use a fresh nonce", nonces.size, nonces.toSet().size)
        assertNotEquals(requests[0].header("X-Signature"), requests[2].header("X-Signature"))
        assertEquals(2L, destination.length())
    }

    @Test fun persistentUnknownInstallationRetriesExactlyOnce() = runBlocking {
        val client = client { request ->
            if (request.method == "POST") json(request, 200, """{"status":"active"}""")
            else error(request, 401, "unknown_installation")
        }

        val error = expectApiError { client.download(SAVE_ID, destination) }

        assertEquals("unknown_installation", error.errorCode)
        assertEquals(401, error.statusCode)
        assertEquals(listOf("GET", "POST", "GET"), requests.map { it.method })
        assertFalse(destination.exists())
    }

    @Test fun registrationConflictSurfacesItsCode() = runBlocking {
        val client = client { request ->
            if (request.method == "POST") error(request, 409, "installation_exists")
            else error(request, 401, "unknown_installation")
        }
        val error = expectApiError { client.download(SAVE_ID, destination) }
        assertEquals("installation_exists", error.errorCode)
        assertEquals(2, requests.size)
    }

    @Test fun invalidSignatureIsNotRetried() = runBlocking {
        val client = client { request -> error(request, 401, "invalid_signature") }
        val error = expectApiError { client.download(SAVE_ID, destination) }
        assertEquals("invalid_signature", error.errorCode)
        assertEquals(1, requests.size)
    }

    @Test fun missingSaveReportsStatusCodeAndRequestId() = runBlocking {
        val client = client { request -> error(request, 404, "save_not_found") }
        val error = expectApiError { client.download(SAVE_ID, destination) }
        assertEquals(404, error.statusCode)
        assertEquals("save_not_found", error.errorCode)
        assertEquals("req-err", error.requestId)
        assertFalse(destination.exists())
        assertNoTempFiles()
    }

    @Test fun missingR2ObjectReportsAssetCode() = runBlocking {
        val client = client { request -> error(request, 404, "save_asset_not_found") }
        assertEquals("save_asset_not_found", expectApiError { client.download(SAVE_ID, destination) }.errorCode)
    }

    @Test fun malformedErrorBodyFallsBackToStatus() = runBlocking {
        val client = client { request ->
            Response.Builder().request(request).protocol(Protocol.HTTP_1_1).code(502).message("Bad Gateway")
                .body("<html>gateway</html>".toResponseBody("text/html".toMediaType())).build()
        }
        val error = expectApiError { client.download(SAVE_ID, destination) }
        assertEquals(502, error.statusCode)
        assertEquals(null, error.errorCode)
    }

    @Test fun contentLengthMismatchRejectsAndLeavesNothingBehind() = runBlocking {
        val client = client { request -> ok(request, ByteArray(50), contentLength = 100) }
        val error = expectApiError { client.download(SAVE_ID, destination) }
        assertEquals(SaveApiClient.DOWNLOAD_INCOMPLETE, error.errorCode)
        assertFalse(destination.exists())
        assertNoTempFiles()
    }

    @Test fun mismatchDoesNotReplaceAnExistingDestination() = runBlocking {
        destination.writeBytes(byteArrayOf(7, 7, 7))
        val client = client { request -> ok(request, ByteArray(50), contentLength = 100) }
        expectApiError { client.download(SAVE_ID, destination) }
        assertArrayEquals(byteArrayOf(7, 7, 7), destination.readBytes())
    }

    @Test fun chunkedResponseWithoutContentLengthSucceeds() = runBlocking {
        val payload = ByteArray(1500) { 3 }
        val client = client { request -> ok(request, payload, contentLength = null) }
        client.download(SAVE_ID, destination)
        assertArrayEquals(payload, destination.readBytes())
    }

    @Test fun declaredOversizeIsRejectedBeforeStreaming() = runBlocking {
        val client = client(maxBytes = 1024) { request -> ok(request, ByteArray(10), contentLength = 4096) }
        assertEquals(SaveApiClient.DOWNLOAD_TOO_LARGE, expectApiError { client.download(SAVE_ID, destination) }.errorCode)
        assertNoTempFiles()
    }

    @Test fun undeclaredOversizeIsCutOffWhileStreaming() = runBlocking {
        val client = client(maxBytes = 1024) { request -> ok(request, ByteArray(2048), contentLength = null) }
        assertEquals(SaveApiClient.DOWNLOAD_TOO_LARGE, expectApiError { client.download(SAVE_ID, destination) }.errorCode)
        assertFalse(destination.exists())
        assertNoTempFiles()
    }

    @Test fun emptyBodyIsRejected() = runBlocking {
        val client = client { request -> ok(request, ByteArray(0), contentLength = 0) }
        assertEquals(SaveApiClient.DOWNLOAD_INCOMPLETE, expectApiError { client.download(SAVE_ID, destination) }.errorCode)
    }

    @Test fun networkFailureIsReportedAsNetworkError() = runBlocking {
        val client = client { throw java.io.IOException("connection reset") }
        val error = expectApiError { client.download(SAVE_ID, destination) }
        assertEquals(SaveApiClient.DOWNLOAD_NETWORK_ERROR, error.errorCode)
        assertEquals(null, error.statusCode)
    }

    @Test fun nonUuidIdsAreRejectedLocally() = runBlocking {
        val client = client { request -> ok(request, byteArrayOf(1), contentLength = 1) }
        for (id in listOf("", "../v1/installations", "saves/$SAVE_ID.dat")) {
            try {
                client.download(id, destination)
                fail("expected rejection for '$id'")
            } catch (_: IllegalArgumentException) {
            }
        }
        assertTrue(requests.isEmpty())
    }

    // --- helpers -------------------------------------------------------------------------

    private fun client(maxBytes: Long = SaveApiClient.MAX_SAVE_BYTES, handler: (Request) -> Response): SaveApiClient {
        val http = OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
            val request = chain.request()
            requests += request
            handler(request)
        }).build()
        return SaveApiClient(signer, BASE_URL, http, http, maxBytes)
    }

    private fun ok(request: Request, bytes: ByteArray, contentLength: Long?): Response = Response.Builder()
        .request(request).protocol(Protocol.HTTP_1_1).code(200).message("OK")
        .header("X-Request-ID", "req-1")
        .apply { if (contentLength != null) header("Content-Length", contentLength.toString()) }
        .body(bytes.toResponseBody("application/octet-stream".toMediaType()))
        .build()

    private fun json(request: Request, code: Int, body: String): Response = Response.Builder()
        .request(request).protocol(Protocol.HTTP_1_1).code(code).message("HTTP $code")
        .body(body.toResponseBody("application/json".toMediaType()))
        .build()

    private fun error(request: Request, code: Int, errorCode: String): Response = json(
        request, code, """{"error":{"code":"$errorCode","message":"Rejected: $errorCode"},"requestId":"req-err"}""",
    )

    private suspend fun expectApiError(block: suspend () -> Unit): SkinApiException {
        try {
            block()
        } catch (error: SkinApiException) {
            return error
        }
        throw AssertionError("expected SkinApiException")
    }

    private fun assertNoTempFiles() {
        val leftovers = workDir.listFiles().orEmpty().filter { it.name.startsWith(".") }
        assertTrue("temp files left behind: $leftovers", leftovers.isEmpty())
    }

    private fun Request.bodyBytes(): ByteArray = Buffer().also { body!!.writeTo(it) }.readByteArray()

    /** Software P-256 signer producing the same DER SHA256withECDSA signatures as the Keystore. */
    private class SoftwareSigner : InstallationSigner {
        val installationId: String = UUID.randomUUID().toString()
        private val keyPair = KeyPairGenerator.getInstance("EC").apply { initialize(ECGenParameterSpec("secp256r1")) }.generateKeyPair()
        private val random = SecureRandom()

        override fun sign(method: String, encodedPathAndQuery: String, bodySha256: String): SignedHeaders {
            val timestamp = System.currentTimeMillis().toString()
            val nonce = b64(ByteArray(24).also(random::nextBytes))
            val canonical = SkinRequestCanonicalizer.canonical(method, encodedPathAndQuery, installationId, timestamp, nonce, bodySha256)
            val signature = Signature.getInstance("SHA256withECDSA").run {
                initSign(keyPair.private)
                update(canonical.toByteArray(Charsets.UTF_8))
                sign()
            }
            return SignedHeaders(installationId, timestamp, nonce, b64(signature))
        }

        override fun snapshot(): IdentitySnapshot = IdentitySnapshot(installationId, SkinPublicJwk("x", "y"))

        /** Mirrors the Worker: METHOD \n pathname+search \n id \n timestamp \n nonce \n sha256(body). */
        fun verifies(request: Request, body: ByteArray): Boolean {
            val timestamp = request.header("X-Timestamp") ?: return false
            val nonce = request.header("X-Nonce") ?: return false
            if (!Regex("^\\d{13}$").matches(timestamp)) return false
            if (!Regex("^[A-Za-z0-9_-]{16,128}$").matches(nonce)) return false
            if (request.header("X-Installation-ID") != installationId) return false
            val pathAndSearch = request.url.encodedPath + (request.url.encodedQuery?.let { "?$it" } ?: "")
            val canonical = "${request.method}\n$pathAndSearch\n$installationId\n$timestamp\n$nonce\n${SkinRequestCanonicalizer.sha256(body)}"
            val signature = Base64.getUrlDecoder().decode(request.header("X-Signature") ?: return false)
            return Signature.getInstance("SHA256withECDSA").run {
                initVerify(keyPair.public)
                update(canonical.toByteArray(Charsets.UTF_8))
                verify(signature)
            }
        }

        private fun b64(bytes: ByteArray) = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes)
    }

    private companion object {
        const val BASE_URL = "https://hub.example.test"
        const val SAVE_ID = "3f2a9c4e-1b7d-4e8a-9c3f-5d6e7f8a9b0c"
    }
}
