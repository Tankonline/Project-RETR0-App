package com.retr0.projectretr0.data.carModels

import com.retr0.projectretr0.data.carModels.manifest.PackageMetadata
import com.retr0.projectretr0.data.carModels.manifest.TargetVehicle
import com.retr0.projectretr0.data.skins.SkinMutationResponse
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test
import java.io.File

class CarModelUploadManagerTest {
    @Test fun uploadsInOrderAndNeverPublishes() { runBlocking {
        val root = createTempDir().apply { resolve("vehicle/livery_01.dds").apply { parentFile!!.mkdirs(); writeText("data") } }
        val api = FakeApi(); val result = CarModelUploadManager(api).upload(request(root))
        assertEquals(listOf("draft", "manifest", "file:vehicle/livery_01.dds"), api.calls)
        assertEquals(1, result.uploadedFiles); assertFalse(api.published)
        root.deleteRecursively()
    } }

    @Test fun failureStopsLaterUploadsAndRejectsUnsafePaths() { runBlocking {
        val root = createTempDir().apply { resolve("car.dds").writeText("data") }
        val api = FakeApi(failManifest = true)
        kotlin.runCatching { CarModelUploadManager(api).upload(request(root)) }.onSuccess { error("expected failure") }
        assertEquals(listOf("draft", "manifest"), api.calls)
        root.deleteRecursively()
    } }

    private fun request(root: File) = CarModelUploadManager.UploadRequest(root, PackageMetadata("generic", description = "no local paths"), target = TargetVehicle("generic", "vehicle"))

    private class FakeApi(private val failManifest: Boolean = false) : CarModelUploadApi {
        val calls = mutableListOf<String>(); var published = false
        override suspend fun createDraft(metadata: JSONObject, idempotencyKey: String) = com.retr0.projectretr0.data.carModels.CarModelApiClient.CarModelDraftResponse("draft-1", "draft", null).also { calls += "draft" }
        override suspend fun getDraft(modelId: String) = com.retr0.projectretr0.data.carModels.CarModelApiClient.CarModelDetailsResponse(modelId, "draft", null, emptyList(), false, emptySet())
        override suspend fun uploadManifest(modelId: String, manifest: String): SkinMutationResponse { calls += "manifest"; if (failManifest) error("manifest failure"); return mutation() }
        override suspend fun uploadFile(modelId: String, relativePath: String, file: File, contentType: String) = mutation().also { calls += "file:$relativePath" }
        override suspend fun uploadCover(modelId: String, file: File, contentType: String) = mutation()
        override suspend fun uploadScreenshot(modelId: String, position: Int, file: File, contentType: String) = mutation()
        override suspend fun deleteOwned(modelId: String) = CarModelApiClient.CarModelDeleteResponse(deleted = true, requestId = null)
        private fun mutation() = SkinMutationResponse(success = true)
    }
}
