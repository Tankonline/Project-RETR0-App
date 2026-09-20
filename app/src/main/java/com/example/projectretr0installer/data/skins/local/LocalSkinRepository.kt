package com.example.projectretr0installer.data.skins.local

import android.content.ContentResolver
import android.content.Context
import com.example.projectretr0installer.ui.skins.ProcessedSkinImage
import com.example.projectretr0installer.ui.skins.SelectedSkinSource
import com.example.projectretr0installer.ui.skins.SelectedSkinTarget
import com.example.projectretr0installer.ui.skins.SkinImageRole
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.util.UUID

class LocalSkinRepository private constructor(
    private val filesRoot: File,
    private val resolver: ContentResolver?,
    private val dao: SkinDao,
) {
    constructor(context: Context) : this(File(context.filesDir, "skins"), context.contentResolver, SkinDatabase.get(context).skinDao())

    suspend fun saveImport(request: LocalSkinImport, onProgress: (Int, String) -> Unit = { _, _ -> }): LocalSkinEntity = withContext(Dispatchers.IO) {
        val id = request.id.ifBlank { UUID.randomUUID().toString() }
        val skinDir = File(filesRoot, id)
        require(!skinDir.exists()) { "Skin already exists" }
        skinDir.mkdirs()
        try {
            onProgress(10, "Copying skin file")
            val canonicalFilename = request.target?.fileName ?: request.source.fileName
            val stream = requireNotNull(resolver).openInputStream(request.source.uri) ?: error("Unable to reopen selected skin")
            val verified = VerifiedFilePromoter.promote(stream, File(skinDir, canonicalFilename), request.source.size, request.source.sha256)
            var screenshotPosition = 0
            val imageEntities = request.images.mapIndexed { index, image ->
                onProgress(25 + (((index + 1) * 35) / request.images.size.coerceAtLeast(1)), "Saving images")
                val position = when (image.role) {
                    SkinImageRole.COVER -> 0
                    SkinImageRole.SCREENSHOT -> ++screenshotPosition
                    SkinImageRole.PFP -> error("PFP images are not part of the skin import flow.")
                }
                val filename = when (image.role) {
                    SkinImageRole.COVER -> "cover.webp"
                    SkinImageRole.SCREENSHOT -> "screenshot-$position.webp"
                    SkinImageRole.PFP -> error("PFP images are not part of the skin import flow.")
                }
                val imageFile = File(skinDir, "images/$filename")
                val promoted = VerifiedFilePromoter.promote(image.file.inputStream(), imageFile, image.sizeBytes, maxBytes = image.role.maxBytes)
                SkinImageEntity(image.id, id, image.role.name, position, request.captions[image.id].orEmpty(), promoted.file.absolutePath, image.mimeType, image.width, image.height, promoted.size, promoted.sha256)
            }
            val entity = LocalSkinEntity(id, request.communityId, request.title.trim(), request.creator.trim(), request.vehicle, request.carDirectory, request.slotKey, request.source.format, canonicalFilename, verified.file.absolutePath, verified.size, verified.sha256, request.target?.relativePath, request.gamePackage, System.currentTimeMillis())
            onProgress(75, "Saving library record")
            dao.insertImported(entity, imageEntities)
            onProgress(100, "Saved to library")
            entity
        } catch (error: Throwable) {
            skinDir.deleteRecursively()
            throw error
        }
    }

    suspend fun saveDownloaded(skin: LocalSkinEntity) = dao.insertImported(skin, emptyList())

    /** Removes the local library record and all private app files for this skin. */
    suspend fun deleteSkin(id: String): Boolean = withContext(Dispatchers.IO) {
        val skin = dao.skin(id) ?: return@withContext false
        // Do not remove a skin that is currently applied. The user must revert it first,
        // which preserves the backup and prevents a dangling applied-skin reference.
        val isApplied = skin.targetPath?.let { targetPath -> dao.appliedTarget(targetPath) } != null
        if (isApplied) {
            throw IllegalStateException("Revert this skin before deleting it")
        }
        dao.deleteSkin(id)
        File(filesRoot, id).deleteRecursively()
        true
    }

    suspend fun skins() = dao.skins()
    suspend fun skin(id: String) = dao.skin(id)
    suspend fun images(id: String) = dao.images(id)
    fun observeSkins() = dao.observeSkins()
    fun observeImages(skinId: String) = dao.observeImages(skinId)
    fun observeOperations() = dao.observeOperations()
    fun observeBackups() = dao.observeBackups()
    fun observeApplied() = dao.observeApplied()
    suspend fun operation(operation: SkinOperationEntity) = dao.upsertOperation(operation)
    suspend fun backupForTarget(targetPath: String) = dao.backupForTarget(targetPath)
    suspend fun saveBackup(backup: SkinBackupEntity) = dao.upsertBackup(backup)
    suspend fun saveApplied(applied: AppliedSkinEntity) = dao.upsertApplied(applied)
    suspend fun appliedTarget(targetPath: String) = dao.appliedTarget(targetPath)
    suspend fun clearApplied(targetPath: String) = dao.deleteAppliedTarget(targetPath)
    suspend fun pendingOperations() = dao.pendingOperations()
}

data class LocalSkinImport(
    val id: String = "",
    val communityId: String? = null,
    val source: SelectedSkinSource,
    val title: String,
    val creator: String,
    val vehicle: String,
    val carDirectory: String,
    val slotKey: String,
    val target: SelectedSkinTarget?,
    val gamePackage: String,
    val images: List<ProcessedSkinImage>,
    val captions: Map<String, String> = emptyMap(),
)
