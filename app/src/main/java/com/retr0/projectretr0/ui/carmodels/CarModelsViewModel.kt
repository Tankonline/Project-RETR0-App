package com.retr0.projectretr0.ui.carmodels

import android.content.Context
import com.retr0.projectretr0.IRetr0UserService
import com.retr0.projectretr0.data.carModels.CarModelHubRepository
import com.retr0.projectretr0.data.carModels.CarModelApiClient
import com.retr0.projectretr0.data.carModels.CarModelUploadManager
import com.retr0.projectretr0.data.carModels.CarModelLog
import com.retr0.projectretr0.data.carModels.CarModelErrorCode
import com.retr0.projectretr0.data.skins.SkinApiException
import com.retr0.projectretr0.data.carModels.CloudflareCarModelHubRepository
import com.retr0.projectretr0.data.carModels.manifest.TargetVehicle
import java.io.File
import com.retr0.projectretr0.data.carModels.local.AddonInspection
import com.retr0.projectretr0.data.carModels.local.PackageAddonInspection
import com.retr0.projectretr0.data.carModels.local.ApplyOutcome
import com.retr0.projectretr0.data.carModels.local.InstallMode
import com.retr0.projectretr0.data.carModels.local.CarModelActivityKind
import com.retr0.projectretr0.data.carModels.local.CarModelBackup
import com.retr0.projectretr0.data.carModels.local.CarModelEngine
import com.retr0.projectretr0.data.carModels.local.CarModelImporter
import com.retr0.projectretr0.data.carModels.local.CarModelState
import com.retr0.projectretr0.data.carModels.local.CarModelStore
import com.retr0.projectretr0.data.carModels.local.ShizukuCarModelFileOps
import com.retr0.projectretr0.data.carModels.local.TargetInspection
import com.retr0.projectretr0.data.carModels.manifest.CarModelManifest
import kotlinx.coroutines.flow.StateFlow

/** Car Models state holder, parallel to SkinModsViewModel. Callbacks keep the skin names: apply, revert, restoreAll. */
class CarModelsViewModel private constructor(context: Context) {
    private val store = CarModelStore.get(context)
    private val engine = CarModelEngine(store)
    val importer = CarModelImporter(context.applicationContext, store)
    private val api = CarModelApiClient(context)
    val hub: CarModelHubRepository = CloudflareCarModelHubRepository(api)
    private val communityInstaller = com.retr0.projectretr0.data.carModels.local.CommunityCarModelInstaller(
        store = store,
        downloadsRoot = File(context.applicationContext.filesDir, "car-model-downloads"),
        source = hub,
    )
    val state: StateFlow<CarModelState> = store.state

    /**
     * The one Community Hub download operation: download to a unique temp dir, verify every file, move into the
     * library, persist with remoteId and update [state]. Returns the local model id only after persistence, so
     * callers may navigate on success. Failures come back as a coded Result; nothing is thrown or lost.
     */
    suspend fun downloadAndImportCommunityModel(
        model: com.retr0.projectretr0.data.carModels.CommunityCarModel,
        onProgress: (completedBytes: Long, totalBytes: Long) -> Unit = { _, _ -> },
    ): Result<com.retr0.projectretr0.data.carModels.local.LocalCarModel> = communityInstaller.downloadAndImport(model, onProgress)

    /** Fetches the Hub cover/screenshots for a downloaded model that has none locally (e.g. downloaded before media support). */
    suspend fun ensureCommunityMedia(localId: String): Result<com.retr0.projectretr0.data.carModels.local.LocalCarModel> =
        communityInstaller.refreshMedia(localId) { remoteId -> hub.details(remoteId) }

    internal suspend fun uploadDraft(context: Context, modelId: String, onProgress: (CarModelUploadManager.UploadProgress) -> Unit = {}): Result<CarModelUploadManager.UploadResult> = runCatching {
        CarModelLog.info("upload_requested", "model=$modelId")
        val model = state.value.model(modelId) ?: error("Car model is no longer in the library")
        val manager = CarModelUploadManager(api)
        val result = manager.upload(CarModelUploadManager.UploadRequest(
            root = File(model.filesRoot),
            metadata = model.manifest.metadata,
            packageId = model.manifest.packageId,
            supportedVersions = model.manifest.supportedVersions,
            supportedGamePackages = model.manifest.supportedGamePackages,
            target = model.manifest.target,
            installMode = "ADDON",
            cover = model.coverPath?.let { File(it).takeIf(File::isFile)?.let { file -> CarModelUploadManager.MediaFile(file, "image/webp") } },
            screenshots = model.screenshotPaths.mapNotNull { path -> File(path).takeIf(File::isFile)?.let { file -> CarModelUploadManager.MediaFile(file, "image/webp") } },
        )) { progress ->
            val event = progress as? CarModelUploadManager.UploadProgress.Event
            if (event != null) {
                CarModelLog.info("upload_progress", "model=$modelId stage=${event.name} ${event.completed}/${event.total}")
            }
            onProgress(progress)
        }
        store.update { current -> current.copy(models = current.models.map { if (it.id == modelId) it.copy(remoteDraftId = result.draftId) else it }) }
        store.log(CarModelActivityKind.IMPORT, model.name, "Draft uploaded (${result.uploadedFiles} files, ${result.uploadedMedia} media)", true)
        result
    }.onFailure { error ->
        when (error) {
            is SkinApiException -> CarModelLog.fail(
                CarModelErrorCode.UNEXPECTED,
                "upload_failed model=$modelId status=${error.statusCode ?: "none"} " +
                    "code=${error.errorCode ?: "none"} requestId=${error.requestId ?: "none"} " +
                    "message=${error.message ?: "none"}"
            )
            else -> CarModelLog.fail(
                CarModelErrorCode.UNEXPECTED,
                "upload_failed model=$modelId cause=${error.javaClass.simpleName} " +
                    "message=${error.message ?: "none"}"
            )
        }
    }

    suspend fun deleteRemote(context: Context, modelId: String): Result<Unit> = runCatching {
        val model = state.value.model(modelId) ?: error("Car model is no longer in the library")
        val remoteId = model.remotePublicId ?: model.remoteDraftId ?: error("This model has no owned Community Hub package")
        CarModelLog.info("remote_delete_requested", "model=$modelId")
        val response = api.deleteOwned(remoteId)
        if (!response.deleted) error("Community Hub did not accept deletion")
        store.update { current -> current.copy(models = current.models.map { if (it.id == modelId) it.copy(remotePublicId = null, remoteDraftId = null) else it }) }
        store.log(CarModelActivityKind.IMPORT, model.name, "Remote Community Hub package deleted; local files preserved", true)
        Unit
    }.onFailure { error -> CarModelLog.fail(CarModelErrorCode.UNEXPECTED, "remote_delete_failed model=$modelId cause=${error.javaClass.simpleName}") }

    suspend fun publishDraft(context: Context, modelId: String): Result<Unit> = runCatching {
        CarModelLog.info("publish_requested", "model=$modelId")
        val model = state.value.model(modelId) ?: error("Car model is no longer in the library")
        val draftId = model.remoteDraftId ?: error("Upload a draft before publishing")
        api.publish(draftId)
        store.update { current -> current.copy(models = current.models.map { if (it.id == modelId) it.copy(remotePublicId = draftId) else it }) }
        store.log(CarModelActivityKind.IMPORT, model.name, "Car model published", true)
        Unit
    }.onFailure { error ->
        CarModelLog.fail(CarModelErrorCode.UNEXPECTED, "publish_failed model=$modelId cause=${error.javaClass.simpleName}")
    }

    /** Uploads a local package when needed, then publishes it. A failed publish leaves remoteDraftId intact. */
    internal suspend fun uploadAndPublish(
        context: Context,
        modelId: String,
        onProgress: (CarModelUploadManager.UploadProgress) -> Unit = {},
        onStage: (String) -> Unit = {},
    ): Result<Unit> = runCatching {
        val model = state.value.model(modelId) ?: error("Car model is no longer in the library")
        if (model.remoteDraftId == null) {
            onStage("Uploading…")
            uploadDraft(context, modelId, onProgress).getOrThrow()
        }
        onStage("Publishing asset…")
        publishDraft(context, modelId).getOrThrow()
    }

    suspend fun listVehicles(service: IRetr0UserService, gamePackage: String): Result<List<String>> =
        engine.listVehicles(ShizukuCarModelFileOps(service), gamePackage)

    suspend fun inspectTarget(service: IRetr0UserService, manifest: CarModelManifest, gamePackage: String, vehicleDirectory: String): Result<TargetInspection> =
        engine.inspectTarget(ShizukuCarModelFileOps(service), manifest, gamePackage, vehicleDirectory)

    suspend fun ensureBackup(service: IRetr0UserService, gamePackage: String, vehicleDirectory: String, onProgress: (Int, Int) -> Unit): Result<CarModelBackup> =
        engine.ensureBackup(ShizukuCarModelFileOps(service), gamePackage, vehicleDirectory, onProgress)

    suspend fun verifyBackup(service: IRetr0UserService, gamePackage: String, vehicleDirectory: String): Result<CarModelBackup> =
        engine.verifyBackup(ShizukuCarModelFileOps(service), gamePackage, vehicleDirectory)

    /** Re-verifies every backup; returns how many are not verified. */
    suspend fun verifyAllBackups(service: IRetr0UserService): Int {
        val ops = ShizukuCarModelFileOps(service)
        val problems = state.value.backups.count { backup -> engine.verifyBackup(ops, backup.gamePackage, backup.vehicleDirectory).getOrNull()?.verified != true }
        store.log(CarModelActivityKind.VERIFY, "Backups verified", if (problems == 0) "${state.value.backups.size} backups healthy" else "$problems backups need attention", problems == 0)
        return problems
    }

    suspend fun inspectAddonPackage(service: IRetr0UserService, manifest: CarModelManifest, gamePackage: String): Result<PackageAddonInspection> =
        engine.inspectAddonPackage(ShizukuCarModelFileOps(service), manifest, gamePackage)

    suspend fun inspectAddonTarget(service: IRetr0UserService, manifest: CarModelManifest, gamePackage: String, vehicleDirectory: String): Result<AddonInspection> =
        engine.inspectAddonTarget(ShizukuCarModelFileOps(service), manifest, gamePackage, vehicleDirectory)

    suspend fun install(service: IRetr0UserService, mode: InstallMode, modelId: String, gamePackage: String, vehicleDirectory: String, onProgress: (Int, Int) -> Unit): ApplyOutcome =
        engine.install(ShizukuCarModelFileOps(service), mode, modelId, gamePackage, vehicleDirectory, onProgress)

    /**
     * The details-screen Apply. The manifest's install mode decides the path:
     *  - ADDON: the target may be absent from the base game (it is a new car). No vehicle-list lookup and no
     *    whole-folder backup; the engine creates the folder, backs up every shared file it overwrites, journals
     *    the install and rolls back on failure.
     *  - REPLACE: the target must already exist (exact name match only) and a verified original backup is
     *    required before anything is written.
     */
    suspend fun applyModel(service: IRetr0UserService, modelId: String, gamePackage: String, vehicleDirectory: String): Result<ApplyOutcome> = runCatching {
        val ops = ShizukuCarModelFileOps(service)
        val model = state.value.model(modelId) ?: throw com.retr0.projectretr0.data.carModels.CarModelException(CarModelErrorCode.MODEL_NOT_FOUND)
        val requestedTarget = model.manifest.target.vehicleDirectory.takeIf { it.isNotBlank() } ?: vehicleDirectory
        val mode = installModeOf(model.manifest)
        CarModelLog.apply("RESOLVE_START", "model=$modelId requested=$requestedTarget package=$gamePackage mode=$mode")
        val outcome = when (mode) {
            InstallMode.ADDON -> {
                // A multi-vehicle package is one install keyed by its first vehicle; it owns all of its folders.
                val addonTarget = model.manifest.vehicleDirectories.firstOrNull() ?: requestedTarget
                // Validates the name (UNSAFE_VEHICLE_DIRECTORY) before any privileged call.
                com.retr0.projectretr0.data.carModels.local.CarModelPaths().vehicleDir(gamePackage, addonTarget)
                CarModelLog.apply("ENGINE_START", "model=$modelId target=$addonTarget package=$gamePackage mode=ADDON vehicles=${model.manifest.vehicleDirectories.size}")
                engine.install(ops, InstallMode.ADDON, modelId, gamePackage, addonTarget)
            }
            InstallMode.REPLACE -> {
                val availableVehicles = listVehicles(service, gamePackage).getOrThrow()
                CarModelLog.apply("VEHICLES", "model=$modelId package=$gamePackage count=${availableVehicles.size} requested=$requestedTarget")
                val resolvedTarget = resolveReplaceTarget(requestedTarget, availableVehicles)
                    ?: throw com.retr0.projectretr0.data.carModels.CarModelException(CarModelErrorCode.TARGET_NOT_FOUND, "Target vehicle '$requestedTarget' was not found in ${gamePackage.substringAfterLast('.')}. A replacement needs the original car installed.")
                CarModelLog.apply("RESOLVE_SUCCESS", "model=$modelId requested=$requestedTarget resolved=$resolvedTarget package=$gamePackage")
                val existingBackup = state.value.backup(gamePackage, resolvedTarget)
                CarModelLog.apply("BACKUP_LOOKUP", "model=$modelId target=$resolvedTarget existing=${existingBackup != null} verified=${existingBackup?.verified == true}")
                val backup = if (existingBackup?.verified == true) engine.verifyBackup(ops, gamePackage, resolvedTarget).getOrThrow()
                else engine.ensureBackup(ops, gamePackage, resolvedTarget).getOrThrow()
                CarModelLog.apply("BACKUP_READY", "model=$modelId target=$resolvedTarget verified=${backup.verified} files=${backup.files.size}")
                if (!backup.verified) throw com.retr0.projectretr0.data.carModels.CarModelException(CarModelErrorCode.BACKUP_NOT_VERIFIED)
                CarModelLog.apply("ENGINE_START", "model=$modelId target=$resolvedTarget package=$gamePackage mode=REPLACE")
                engine.install(ops, InstallMode.REPLACE, modelId, gamePackage, resolvedTarget)
            }
        }
        // Keep the engine's stable code (e.g. CM-ADDON-009, CM-FILEOP-003) instead of a bare message.
        if (outcome is ApplyOutcome.Failed) throw com.retr0.projectretr0.data.carModels.CarModelException(
            CarModelErrorCode.entries.firstOrNull { it.code == outcome.code } ?: CarModelErrorCode.UNEXPECTED,
            outcome.reason + if (outcome.gameFilesTouched && outcome.originalRestored) " Changes were rolled back." else "",
        )
        outcome
    }

    suspend fun removeAddon(service: IRetr0UserService, gamePackage: String, vehicleDirectory: String): Result<Int> =
        engine.removeAddon(ShizukuCarModelFileOps(service), gamePackage, vehicleDirectory)

    suspend fun revert(service: IRetr0UserService, gamePackage: String, vehicleDirectory: String): Result<Int> =
        engine.revert(ShizukuCarModelFileOps(service), gamePackage, vehicleDirectory)

    /** Reverts every replacement and removes every add-on. Returns restored vehicles to failures. */
    suspend fun restoreAll(service: IRetr0UserService): Pair<Int, Int> {
        val ops = ShizukuCarModelFileOps(service)
        val results = state.value.installed.filter { it.active }.map {
            if (it.isAddon) engine.removeAddon(ops, it.gamePackage, it.vehicleDirectory) else engine.revert(ops, it.gamePackage, it.vehicleDirectory)
        }
        return results.count { it.isSuccess } to results.count { it.isFailure }
    }

    suspend fun deleteBackup(service: IRetr0UserService, gamePackage: String, vehicleDirectory: String): Result<Unit> =
        engine.deleteBackup(ShizukuCarModelFileOps(service), gamePackage, vehicleDirectory)

    suspend fun removeModel(modelId: String): Result<Unit> = engine.removeModel(modelId)

    companion object {
        /** Missing/unknown modes are treated as REPLACE, the conservative path that needs a verified backup. */
        internal fun installModeOf(manifest: CarModelManifest): InstallMode =
            if (manifest.installMode.equals("ADDON", ignoreCase = true)) InstallMode.ADDON else InstallMode.REPLACE

        /**
         * Exact (case-insensitive) or punctuation-insensitive equal names only. The old substring match could
         * resolve "m3" inside another car's name and overwrite the wrong vehicle.
         */
        internal fun resolveReplaceTarget(requested: String, available: List<String>): String? {
            fun norm(value: String) = value.lowercase().replace(Regex("[^a-z0-9]+"), "")
            return available.firstOrNull { it.equals(requested, ignoreCase = true) }
                ?: available.filter { norm(it) == norm(requested) }.singleOrNull()
        }

        @Volatile private var instance: CarModelsViewModel? = null
        fun get(context: Context): CarModelsViewModel = instance ?: synchronized(this) {
            instance ?: CarModelsViewModel(context.applicationContext).also { instance = it }
        }
    }
}
