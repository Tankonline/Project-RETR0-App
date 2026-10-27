package com.retr0.projectretr0.data.skins.local

import android.content.ContentResolver
import android.content.Context
import android.util.Log
import com.retr0.projectretr0.ui.skins.ProcessedSkinImage
import com.retr0.projectretr0.ui.skins.SelectedSkinSource
import com.retr0.projectretr0.ui.skins.SelectedSkinTarget
import com.retr0.projectretr0.ui.skins.SkinImageRole
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.util.Locale
import com.retr0.projectretr0.data.skins.manifest.ManifestFile
import com.retr0.projectretr0.data.skins.manifest.ManifestScreenshot
import com.retr0.projectretr0.data.skins.manifest.SkinManifest
import com.retr0.projectretr0.data.skins.manifest.SkinManifestStore
import java.io.FileInputStream
import java.security.MessageDigest
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
                    SkinImageRole.SCREENSHOT -> "${String.format(Locale.ROOT, "%02d", position)}.webp"
                    SkinImageRole.PFP -> error("PFP images are not part of the skin import flow.")
                }
                val imageFile = File(skinDir, if (image.role == SkinImageRole.SCREENSHOT) "screenshots/$filename" else "images/$filename")
                imageFile.parentFile?.mkdirs()
                val promoted = try {
                    VerifiedFilePromoter.promote(image.file.inputStream(), imageFile, image.sizeBytes, maxBytes = image.role.maxBytes)
                } catch (error: Throwable) {
                    Log.e(
                        "SKIN_IMPORT",
                        "code=SKIN_IMPORT_E009 stage=image_save_failed role=${image.role} source=${image.file.absolutePath} destination=${imageFile.absolutePath} sourceExists=${image.file.exists()} sourceBytes=${image.file.length()} error=${error.message}",
                        error,
                    )
                    throw error
                }
                Log.i(
                    "SKIN_IMPORT",
                    "code=SKIN_IMPORT_I002 stage=image_saved role=${image.role} path=${promoted.file.absolutePath} exists=${promoted.file.isFile} bytes=${promoted.size}",
                )
                SkinImageEntity(UUID.randomUUID().toString(), id, image.role.name, position, request.captions[image.id].orEmpty(), promoted.file.absolutePath, image.mimeType, image.width, image.height, promoted.size, promoted.sha256)
            }
            val entity = LocalSkinEntity(id, request.communityId, request.title.trim(), request.creator.trim(), request.vehicle, request.carDirectory, request.slotKey, request.source.format, canonicalFilename, verified.file.absolutePath, verified.size, verified.sha256, request.target?.absolutePath, request.target?.relativePath, request.gamePackage, System.currentTimeMillis())
            onProgress(75, "Saving library record")
            dao.insertImported(entity, imageEntities)
            writeManifest(skinDir, entity, imageEntities)
            onProgress(100, "Saved to library")
            entity
        } catch (error: Throwable) {
            skinDir.deleteRecursively()
            throw error
        }
    }

    private fun writeManifest(directory: File, entity: LocalSkinEntity, images: List<SkinImageEntity>) {
        fun hash(file: File): String = FileInputStream(file).use { input -> MessageDigest.getInstance("SHA-256").digest(input.readBytes()).joinToString("") { b -> "%02x".format(b) } }
        val manifest = SkinManifest(
            skinId = entity.id, title = entity.title, description = "", creator = entity.creator, vehicle = entity.vehicle,
            carDirectory = entity.carDirectory, slotKey = entity.slotKey, textureFormat = entity.textureFormat,
            gamePackage = entity.gamePackage, sourceFilename = entity.sourceFilename, targetRelativePath = entity.targetRelativePath,
            binary = ManifestFile(File(entity.binaryPath).name, "application/octet-stream", entity.binarySize, entity.binarySha256),
            cover = images.firstOrNull { it.role == "COVER" }?.let { ManifestFile("cover.webp", it.mimeType, it.sizeBytes, hash(File(it.path))) },
            screenshots = images.filter { it.role == "SCREENSHOT" }.map { ManifestScreenshot(it.position, "screenshots/${File(it.path).name}", it.mimeType, it.sizeBytes, hash(File(it.path)), it.caption) },
            communityId = entity.communityId,
        )
        SkinManifestStore.write(directory, manifest)
    }

    suspend fun saveDownloaded(skin: LocalSkinEntity, images: List<SkinImageEntity> = emptyList()) {
        val existing = skin.communityId?.let { dao.communitySkin(it) }
        require(existing?.targetPath == null) { "Revert this skin before refreshing it" }
        dao.replaceDownloaded(existing?.id, skin, images)
        if (existing != null && existing.id != skin.id) File(filesRoot, existing.id).deleteRecursively()
    }

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
    suspend fun updateMetadata(id: String, title: String, creator: String, vehicle: String, carDirectory: String, slotKey: String, textureFormat: String) =
        dao.updateMetadata(id, title.trim(), creator.trim(), vehicle.trim(), carDirectory.trim(), slotKey.trim(), textureFormat.trim())
    suspend fun updateTarget(id: String, targetPath: String, packageRoot: String, gamePackage: String) {
        val relative = targetPath.substringAfter("$packageRoot/", targetPath).replace('\\', '/')
        dao.updateTarget(id, targetPath, relative, gamePackage)
    }
    suspend fun images(id: String) = dao.images(id)
    fun observeSkins() = dao.observeSkins()
    fun observeImages(skinId: String) = dao.observeImages(skinId)
    fun observeOperations() = dao.observeOperations()
    fun observeBackups() = dao.observeBackups()
    fun observeApplied() = dao.observeApplied()
    suspend fun operation(operation: SkinOperationEntity) = dao.upsertOperation(operation)
    suspend fun backupForTarget(targetPath: String) = dao.backupForTarget(targetPath)
    suspend fun backups() = dao.backups()
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
