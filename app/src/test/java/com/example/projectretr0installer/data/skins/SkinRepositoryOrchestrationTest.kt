package com.example.projectretr0installer.data.skins

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File
import java.security.MessageDigest

class SkinRepositoryOrchestrationTest {
    @Test fun uploadFlowRegistersThenSequencesDraftBinaryCoverScreenshotsAndPublish() = runBlocking {
        val calls = mutableListOf<String>()
        val source = object : SkinDataSource {
            override suspend fun list(query: String, sort: String, limit: Int, offset: Int) = SkinListResponse(emptyList(), limit, offset)
            override suspend fun details(id: String) = error("unused")
            override suspend fun registerInstallation() { calls += "register" }
            override suspend fun createDraft(draft: SkinDraft, idempotencyKey: String): SkinDraftResponse { calls += "draft"; return SkinDraftResponse(ID, "draft", URL, URL, URL, null) }
            override suspend fun uploadBinary(id: String, file: File) = ok("binary", calls)
            override suspend fun uploadCover(id: String, file: File, contentType: String) = ok("cover", calls)
            override suspend fun uploadScreenshot(id: String, position: Int, file: File, contentType: String, caption: String) = ok("screenshot-$position", calls)
            override suspend fun publish(id: String) = ok("publish", calls)
        }
        val binary = temp("binary")
        val cover = temp("cover")
        val draft = SkinDraft("Title", "Creator", carDirectory = "car", slotKey = "ext", textureFormat = "etc", sourceFilename = "car_ext.etc.dds.z", binarySize = binary.length(), binarySha256 = sha256(binary))

        SkinRepository(source).createUploadAndPublish(draft, "upload-key-1", binary, cover, "image/png", listOf(SkinUploadImage(2, cover, "image/png"), SkinUploadImage(1, cover, "image/png")))

        assertEquals(listOf("register", "draft", "register", "binary", "register", "cover", "register", "screenshot-1", "register", "screenshot-2", "register", "publish"), calls)
    }

    private fun ok(name: String, calls: MutableList<String>): SkinMutationResponse { calls += name; return SkinMutationResponse(true) }
    private fun temp(value: String) = File.createTempFile("skin", ".tmp").apply { writeText(value); deleteOnExit() }
    private fun sha256(file: File) = MessageDigest.getInstance("SHA-256").digest(file.readBytes()).joinToString("") { "%02x".format(it) }
    private companion object { const val ID = "11111111-1111-4111-8111-111111111111"; const val URL = "https://example.com" }
}
