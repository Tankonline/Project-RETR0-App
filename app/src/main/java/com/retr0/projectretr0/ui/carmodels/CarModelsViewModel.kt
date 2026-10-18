package com.retr0.projectretr0.ui.carmodels

import android.content.Context
import com.retr0.projectretr0.IRetr0UserService
import com.retr0.projectretr0.data.carModels.CarModelHubRepository
import com.retr0.projectretr0.data.carModels.CarModelApiClient
import com.retr0.projectretr0.data.carModels.CarModelUploadManager
import com.retr0.projectretr0.data.carModels.CarModelLog
import com.retr0.projectretr0.data.carModels.CarModelErrorCode
import com.retr0.projectretr0.data.carModels.CloudflareCarModelHubRepository
import com.retr0.projectretr0.data.carModels.manifest.TargetVehicle
import java.io.File
import com.retr0.projectretr0.data.carModels.local.AddonInspection
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
    val hub: CarModelHubRepository = CloudflareCarModelHubRepository(CarModelApiClient(context))
    val state: StateFlow<CarModelState> = store.state

    internal suspend fun uploadDraft(context: Context, modelId: String, onProgress: (CarModelUploadManager.UploadProgress) -> Unit = {}): Result<CarModelUploadManager.UploadResult> = runCatching {
        CarModelLog.info("upload_requested", "model=$modelId")
        val model = state.value.model(modelId) ?: error("Car model is no longer in the library")
        val api = CarModelApiClient(context)
        val manager = CarModelUploadManager(api)
        val result = manager.upload(CarModelUploadManager.UploadRequest(
            root = File(model.filesRoot),
            metadata = model.manifest.metadata,
            packageId = model.manifest.packageId,
            supportedVersions = model.manifest.supportedVersions,
            supportedGamePackages = model.manifest.supportedGamePackages,
            target = model.manifest.target,
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
        CarModelLog.fail(CarModelErrorCode.UNEXPECTED, "upload_failed model=$modelId cause=${error.javaClass.simpleName}")
    }

    suspend fun deleteRemote(context: Context, modelId: String): Result<Unit> = runCatching {
        val model = state.value.model(modelId) ?: error("Car model is no longer in the library")
        val remoteId = model.remotePublicId ?: model.remoteDraftId ?: error("This model has no owned Community Hub package")
        CarModelLog.info("remote_delete_requested", "model=$modelId")
        val response = CarModelApiClient(context).deleteOwned(remoteId)
        if (!response.deleted) error("Community Hub did not accept deletion")
        store.update { current -> current.copy(models = current.models.map { if (it.id == modelId) it.copy(remotePublicId = null, remoteDraftId = null) else it }) }
        store.log(CarModelActivityKind.IMPORT, model.name, "Remote Community Hub package deleted; local files preserved", true)
        Unit
    }.onFailure { error -> CarModelLog.fail(CarModelErrorCode.UNEXPECTED, "remote_delete_failed model=$modelId cause=${error.javaClass.simpleName}") }

    suspend fun publishDraft(context: Context, modelId: String): Result<Unit> = runCatching {
        CarModelLog.info("publish_requested", "model=$modelId")
        val model = state.value.model(modelId) ?: error("Car model is no longer in the library")
        val draftId = model.remoteDraftId ?: error("Upload a draft before publishing")
        CarModelApiClient(context).publish(draftId)
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

    suspend fun inspectAddonTarget(service: IRetr0UserService, manifest: CarModelManifest, gamePackage: String, vehicleDirectory: String): Result<AddonInspection> =
        engine.inspectAddonTarget(ShizukuCarModelFileOps(service), manifest, gamePackage, vehicleDirectory)

    suspend fun install(service: IRetr0UserService, mode: InstallMode, modelId: String, gamePackage: String, vehicleDirectory: String, onProgress: (Int, Int) -> Unit): ApplyOutcome =
        engine.install(ShizukuCarModelFileOps(service), mode, modelId, gamePackage, vehicleDirectory, onProgress)

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
        @Volatile private var instance: CarModelsViewModel? = null
        fun get(context: Context): CarModelsViewModel = instance ?: synchronized(this) {
            instance ?: CarModelsViewModel(context.applicationContext).also { instance = it }
        }
    }
}
