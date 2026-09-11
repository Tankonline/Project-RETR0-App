package com.example.projectretr0installer.data.skins

import android.content.Context
import com.example.projectretr0installer.ui.skins.CommunitySkin
import com.example.projectretr0installer.ui.skins.Compatibility
import java.io.File
import java.util.Locale

open class SkinRepository(private val source: SkinDataSource = SkinApiClient()) {
    constructor(context: Context) : this(SkinApiClient(context))
    open suspend fun list(query: String = "", sort: String = "newest"): List<CommunitySkin> = source.list(query, sort).items.map(::map)
    open suspend fun listPage(query: String = "", sort: String = "newest", limit: Int = 20, offset: Int = 0): SkinListResponse = source.list(query, sort, limit, offset)
    open suspend fun details(id: String): CommunitySkin = map(source.details(id))

    open suspend fun createDraft(draft: SkinDraft, idempotencyKey: String): SkinDraftResponse = authenticated { source.createDraft(draft, idempotencyKey) }
    open suspend fun uploadBinary(id: String, file: File) = authenticated { source.uploadBinary(id, file) }
    open suspend fun uploadCover(id: String, file: File, contentType: String) = authenticated { source.uploadCover(id, file, contentType) }
    open suspend fun uploadScreenshot(id: String, position: Int, file: File, contentType: String, caption: String = "") = authenticated { source.uploadScreenshot(id, position, file, contentType, caption) }
    open suspend fun publish(id: String) = authenticated { source.publish(id) }
    open suspend fun setLike(id: String, liked: Boolean) = authenticated { source.setLike(id, liked) }
    open suspend fun report(id: String, reason: SkinReportReason, details: String = "") = authenticated { source.report(id, reason, details) }
    open suspend fun deleteOwned(id: String) = authenticated { source.deleteOwned(id) }

    open suspend fun createUploadAndPublish(
        draft: SkinDraft,
        idempotencyKey: String,
        binary: File,
        cover: File,
        coverContentType: String,
        screenshots: List<SkinUploadImage> = emptyList(),
    ): SkinDraftResponse {
        require(binary.length() == draft.binarySize) { "Binary length does not match draft metadata" }
        val created = createDraft(draft, idempotencyKey)
        uploadBinary(created.id, binary)
        uploadCover(created.id, cover, coverContentType)
        screenshots.sortedBy { it.position }.forEach { uploadScreenshot(created.id, it.position, it.file, it.contentType, it.caption) }
        publish(created.id)
        return created
    }

    private suspend fun <T> authenticated(operation: suspend () -> T): T {
        source.registerInstallation()
        return operation()
    }

    internal fun map(api: SkinApiSkin): CommunitySkin = CommunitySkin(
        id = api.id, title = api.title, vehicle = vehicleName(api.carDirectory), carId = api.carDirectory,
        creator = api.creatorName, slotKey = api.slotKey, slotLabel = slotName(api.slotKey), format = api.textureFormat,
        gameVersions = api.gameVersions.orEmpty(), binarySizeBytes = api.binarySize, likes = api.likes, downloads = api.downloads,
        createdAt = api.createdAt, updatedAt = api.updatedAt, verified = false, featured = false,
        compatibility = Compatibility.GAME_NOT_DETECTED, description = api.description.orEmpty(), coverUrl = api.coverUrl,
        screenshotUrls = api.screenshots.map { it.url }, sourceFilename = api.sourceFilename, binaryUrl = api.binaryUrl,
        binarySha256 = api.binarySha256, status = api.status,
    )

    private fun slotName(value: String): String = when (value.lowercase(Locale.ROOT)) {
        "ext_one", "exterior_1" -> "Exterior - Slot 1"
        "ext_two", "exterior_2" -> "Exterior - Slot 2"
        "interior" -> "Interior"
        else -> value.replace('_', ' ').trim().split(Regex("\\s+")).joinToString(" ") { word -> word.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() } }
    }

    private fun vehicleName(directory: String): String = directory.substringAfter('_', directory).replace('_', ' ').trim().split(Regex("\\s+"))
        .joinToString(" ") { word -> word.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() } }.ifBlank { directory }
}

data class SkinUploadImage(val position: Int, val file: File, val contentType: String, val caption: String = "")
