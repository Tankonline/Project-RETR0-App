package com.retr0.projectretr0.data.carModels

import com.retr0.projectretr0.data.carModels.manifest.CarModelFolderScanner
import com.retr0.projectretr0.data.carModels.manifest.CarModelManifest
import com.retr0.projectretr0.data.carModels.manifest.CarModelLimits
import com.retr0.projectretr0.data.carModels.manifest.PackageMetadata
import com.retr0.projectretr0.data.carModels.manifest.TargetVehicle
import kotlinx.coroutines.ensureActive
import org.json.JSONObject
import java.io.File
import java.security.MessageDigest
import kotlin.coroutines.coroutineContext

/** Orchestrates a draft upload only. It never publishes or mutates local files. */
internal class CarModelUploadManager(private val api: CarModelUploadApi) {
    data class UploadRequest(
        val root: File,
        val metadata: PackageMetadata,
        val packageId: String = "com.ea.games.r3_row",
        val supportedVersions: List<String> = emptyList(),
        val supportedGamePackages: List<String> = listOf(packageId),
        val target: TargetVehicle,
        val installMode: String = "REPLACE",
        val cover: MediaFile? = null,
        val screenshots: List<MediaFile> = emptyList(),
    )
    data class MediaFile(val file: File, val contentType: String)
    sealed class UploadProgress {
        data class Event(val name: String, val completed: Int, val total: Int) : UploadProgress()
    }
    data class UploadResult(val draftId: String, val status: String, val uploadedFiles: Int, val uploadedMedia: Int, val manifest: CarModelManifest)

    suspend fun upload(request: UploadRequest, onProgress: (UploadProgress) -> Unit = {}): UploadResult {
        coroutineContext.ensureActive()
        CarModelLog.info("upload_start", "files=${request.root.name} package=${request.packageId.substringAfterLast('.')} host=community")
        val root = request.root.canonicalFile
        require(root.isDirectory && root.canRead()) { "Selected folder is not readable" }
        val scan = CarModelFolderScanner.scan(root, request.metadata, request.target)
        require(scan.report.isValid) { scan.report.errors.joinToString("; ") }
        // Local collision journals and absolute backup paths are intentionally excluded from the wire manifest.
        val manifest = scan.manifest.copy(packageId = request.packageId, supportedVersions = request.supportedVersions, supportedGamePackages = request.supportedGamePackages, installMode = request.installMode.uppercase(), backupRequired = request.installMode.uppercase() != "ADDON", transactionId = null, localInstallJournal = null)
        // The local manifest keeps each destination relative to its declared depot root.
        // The Worker validates the complete wire destination, including non-vehicle roots.
        val wireManifest = manifest.copy(files = manifest.files.map { file ->
            val wireDestination = when (file.destinationRoot) {
                com.retr0.projectretr0.data.carModels.manifest.CarModelDestinationRoot.VEHICLE ->
                    "${manifest.target.vehicleDirectory}/${file.destinationPath}"
                com.retr0.projectretr0.data.carModels.manifest.CarModelDestinationRoot.VEHICLES_DATA ->
                    "vehicles/data/${file.destinationPath}"
                com.retr0.projectretr0.data.carModels.manifest.CarModelDestinationRoot.DEPOT_DATA ->
                    "data/${file.destinationPath}"
                com.retr0.projectretr0.data.carModels.manifest.CarModelDestinationRoot.DEPOT_ROOT ->
                    file.destinationPath
            }
            file.copy(destinationPath = wireDestination)
        })
        val manifestJson = wireManifest.toJson().toString()
        require(manifestJson.toByteArray(Charsets.UTF_8).size <= CarModelLimits.MAX_MANIFEST_BYTES) { "Car-model manifest exceeds 256 KiB." }
        val idempotency = "car-model-${manifest.packageId}-${sha256(manifestJson.toByteArray()).take(32)}"
        onProgress(UploadProgress.Event("draft", 0, manifest.files.size + 1))
        CarModelLog.step("upload_stage=draft files=${manifest.files.size}")
        val draft = api.createDraft(JSONObject()
            .put("title", manifest.metadata.name)
            .put("creatorName", manifest.metadata.creator.ifBlank { manifest.metadata.name })
            .put("description", manifest.metadata.description)
            .put("packageId", manifest.packageId)
            .put("packageName", manifest.metadata.name)
            .put("targetVehicleDirectory", manifest.target.vehicleDirectory)
            .put("metadata", manifest.metadata.toJson())
            .put("target", manifest.target.toJson())
            .put("installMode", manifest.installMode)
            .put("root", manifest.target.relativeRoot)
            .put("supportedVersions", manifest.supportedVersions)
            .put("supportedGamePackages", manifest.supportedGamePackages), idempotency)
        coroutineContext.ensureActive()
        val remote = api.getDraft(draft.modelId)
        val remoteFilesByPath = remote.files.associateBy { it.path }
        if (remote.manifestSha256 != sha256(manifestJson.toByteArray())) {
            CarModelLog.step("upload_stage=manifest modelId=${draft.modelId}")
            api.uploadManifest(draft.modelId, manifestJson)
        }
        onProgress(UploadProgress.Event("manifest", 1, manifest.files.size + 1))
        manifest.files.forEachIndexed { index, entry ->
            coroutineContext.ensureActive()
            val local = File(root, entry.sourcePath).canonicalFile
            require(local.toPath().startsWith(root.toPath())) { "File escapes selected folder" }
            require(local.isFile && local.length() == entry.sizeBytes && sha256(local) == entry.sha256) { "File changed while uploading: ${entry.sourcePath}" }
            val existing = remoteFilesByPath[entry.sourcePath]
            if (existing == null || existing.size != entry.sizeBytes || !existing.sha256.equals(entry.sha256, ignoreCase = true)) {
                // The Worker addresses uploaded objects by their declared source path;
                // destinationPath is the in-game target and is validated from the manifest.
                CarModelLog.step("upload_stage=file index=${index + 1} size=${entry.sizeBytes}")
                api.uploadFile(draft.modelId, entry.sourcePath, local, entry.contentType)
            }
            onProgress(UploadProgress.Event("file", index + 2, manifest.files.size + 1))
        }
        var mediaCount = 0
        request.cover?.let { media ->
            validateMedia(media)
            if (!remote.coverUploaded) api.uploadCover(draft.modelId, media.file.canonicalFile, media.contentType)
            mediaCount++; onProgress(UploadProgress.Event("cover", mediaCount, request.screenshots.size + 1))
        }
        request.screenshots.forEachIndexed { index, media ->
            validateMedia(media)
            if (index + 1 !in remote.screenshotPositions) api.uploadScreenshot(draft.modelId, index + 1, media.file.canonicalFile, media.contentType)
            mediaCount++; onProgress(UploadProgress.Event("screenshot", mediaCount, request.screenshots.size + 1))
        }
        return UploadResult(draft.modelId, draft.status, manifest.files.size, mediaCount, manifest)
    }

    private fun validateMedia(media: MediaFile) {
        val file = media.file.canonicalFile
        require(file.isFile && file.canRead()) { "Media must be a readable file" }
        require(media.contentType.isNotBlank()) { "Media content type is required" }
    }
    private fun sha256(bytes: ByteArray) = MessageDigest.getInstance("SHA-256").digest(bytes).joinToString("") { "%02x".format(it) }
    private fun sha256(file: File): String = sha256(file.readBytes())
}
