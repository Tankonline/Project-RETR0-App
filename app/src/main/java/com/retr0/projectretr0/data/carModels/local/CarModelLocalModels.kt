package com.retr0.projectretr0.data.carModels.local

import com.retr0.projectretr0.data.carModels.manifest.CarModelManifest
import org.json.JSONArray
import org.json.JSONObject

/** Persisted media metadata; media files remain app-private and are never uploaded by local persistence. */
data class CarModelMediaRecord(
    val role: String,
    val path: String,
    val mimeType: String,
    val sizeBytes: Long,
    val sha256: String,
    val width: Int = 0,
    val height: Int = 0,
) {
    fun toJson() = JSONObject().put("role", role).put("path", path).put("mimeType", mimeType)
        .put("sizeBytes", sizeBytes).put("sha256", sha256).put("width", width).put("height", height)
    companion object {
        fun fromJson(j: JSONObject) = CarModelMediaRecord(j.optString("role"), j.getString("path"), j.optString("mimeType"), j.optLong("sizeBytes"), j.optString("sha256"), j.optInt("width"), j.optInt("height"))
    }
}

/** A complete car model kept in the app's local library (folder + manifest, never a single texture). */
data class LocalCarModel(
    val id: String,
    val name: String,
    val creator: String,
    val description: String,
    val gameVersion: String,
    val license: String,
    val supportedPackages: List<String>,
    val manifest: CarModelManifest,
    val filesRoot: String,
    val coverPath: String?,
    val screenshotPaths: List<String>,
    val ignoredFiles: List<String>,
    val importedAt: Long,
    /** Hub id this model was downloaded from; null for folder imports. */
    val remoteId: String? = null,
    /** Draft/public identifiers are retained locally without coupling local install to upload code. */
    val remoteDraftId: String? = null,
    val remotePublicId: String? = null,
    /** Variant selections made for this install, keyed by manifest file path. */
    val selectedVariants: Map<String, String> = emptyMap(),
    /** Hashed media records retained alongside the convenient legacy paths. */
    val media: List<CarModelMediaRecord> = emptyList(),
) {
    val vehicleDirectory: String get() = manifest.target.vehicleDirectory
    val fileCount: Int get() = manifest.files.size
    val totalBytes: Long get() = manifest.files.sumOf { it.sizeBytes }

    fun toJson(): JSONObject = JSONObject()
        .put("id", id).put("name", name).put("creator", creator).put("description", description)
        .put("gameVersion", gameVersion).put("license", license)
        .put("supportedPackages", JSONArray(supportedPackages))
        .put("manifest", manifest.toJson()).put("filesRoot", filesRoot)
        .put("coverPath", coverPath ?: JSONObject.NULL)
        .put("screenshotPaths", JSONArray(screenshotPaths))
        .put("ignoredFiles", JSONArray(ignoredFiles))
        .put("importedAt", importedAt)
        .put("remoteId", remoteId ?: JSONObject.NULL)
        .put("remoteDraftId", remoteDraftId ?: JSONObject.NULL)
        .put("remotePublicId", remotePublicId ?: JSONObject.NULL)
        .put("selectedVariants", JSONObject(selectedVariants))
        .put("media", JSONArray(media.map { it.toJson() }))

    companion object {
        fun fromJson(j: JSONObject) = LocalCarModel(
            id = j.getString("id"),
            name = j.optString("name"),
            creator = j.optString("creator"),
            description = j.optString("description"),
            gameVersion = j.optString("gameVersion"),
            license = j.optString("license"),
            supportedPackages = j.optJSONArray("supportedPackages").strings(),
            manifest = CarModelManifest.fromJson(j.getJSONObject("manifest")),
            filesRoot = j.getString("filesRoot"),
            coverPath = j.optString("coverPath").takeIf { it.isNotBlank() && it != "null" },
            screenshotPaths = j.optJSONArray("screenshotPaths").strings(),
            ignoredFiles = j.optJSONArray("ignoredFiles").strings(),
            importedAt = j.optLong("importedAt"),
            remoteId = j.optString("remoteId").takeIf { it.isNotBlank() && it != "null" },
            remoteDraftId = j.optString("remoteDraftId").takeIf { it.isNotBlank() && it != "null" },
            remotePublicId = j.optString("remotePublicId").takeIf { it.isNotBlank() && it != "null" },
            selectedVariants = j.optJSONObject("selectedVariants")?.let { obj -> obj.keys().asSequence().associateWith { obj.optString(it) } } ?: emptyMap(),
            media = j.optJSONArray("media").objects { runCatching { CarModelMediaRecord.fromJson(it) }.getOrNull() },
        )
    }
}

enum class BackupStatus { VERIFIED, CORRUPT, MISSING }

data class BackupFile(
    val path: String,
    val sizeBytes: Long,
    val sha256: String,
    /** Root containing [path]. Defaults to VEHICLE for legacy JSON. */
    val root: String = "VEHICLE",
) {
    fun toJson(): JSONObject = JSONObject().put("path", path).put("sizeBytes", sizeBytes).put("sha256", sha256).put("root", root)
    companion object { fun fromJson(j: JSONObject) = BackupFile(j.getString("path"), j.getLong("sizeBytes"), j.getString("sha256"), j.optString("root", "VEHICLE")) }
}

data class AddonFileRecord(val path: String, val root: String = "VEHICLE", val sha256: String = "") {
    fun toJson() = JSONObject().put("path", path).put("root", root).put("sha256", sha256)
    companion object { fun fromJson(j: JSONObject) = AddonFileRecord(j.getString("path"), j.optString("root", "VEHICLE"), j.optString("sha256")) }
}

/**
 * The original (stock) files of one vehicle directory, keyed by package + vehicleDir.
 * [status] is the result of the last re-hash of the stored files against [files].
 */
data class CarModelBackup(
    val gamePackage: String,
    val vehicleDirectory: String,
    val root: String,
    val files: List<BackupFile>,
    val createdAt: Long,
    val status: BackupStatus,
    val problemCount: Int = 0,
    val verifiedAt: Long = createdAt,
    /** Files a car model added to the vehicle directory that are not part of the original; removed on revert. */
    val addedFiles: List<String> = emptyList(),
) {
    val key: String get() = vehicleKey(gamePackage, vehicleDirectory)
    val verified: Boolean get() = status == BackupStatus.VERIFIED
    val totalBytes: Long get() = files.sumOf { it.sizeBytes }

    fun toJson(): JSONObject = JSONObject()
        .put("gamePackage", gamePackage).put("vehicleDirectory", vehicleDirectory).put("root", root)
        .put("files", JSONArray(files.map { it.toJson() })).put("createdAt", createdAt)
        .put("status", status.name).put("problemCount", problemCount).put("verifiedAt", verifiedAt)
        .put("addedFiles", JSONArray(addedFiles))

    companion object {
        fun fromJson(j: JSONObject): CarModelBackup {
            val files = j.getJSONArray("files")
            return CarModelBackup(
                gamePackage = j.getString("gamePackage"),
                vehicleDirectory = j.getString("vehicleDirectory"),
                root = j.getString("root"),
                files = (0 until files.length()).map { BackupFile.fromJson(files.getJSONObject(it)) },
                createdAt = j.optLong("createdAt"),
                status = runCatching { BackupStatus.valueOf(j.optString("status")) }.getOrDefault(BackupStatus.CORRUPT),
                problemCount = j.optInt("problemCount"),
                verifiedAt = j.optLong("verifiedAt"),
                addedFiles = j.optJSONArray("addedFiles").strings(),
            )
        }
    }
}

/**
 * How a model is installed. [REPLACE] overlays an existing vehicle behind a verified whole-folder backup;
 * [ADDON] only adds files to a (usually new) vehicle folder and is uninstalled by deleting exactly those files.
 */
enum class InstallMode { REPLACE, ADDON }

data class InstalledCarModel(
    val modelId: String,
    val modelName: String,
    val gamePackage: String,
    val vehicleDirectory: String,
    val appliedAt: Long,
    val active: Boolean,
    val fileCount: Int,
    val totalBytes: Long,
    val mode: InstallMode = InstallMode.REPLACE,
    /** ADDON only: files that did not exist before the add-on; Remove deletes exactly these. */
    val addedFiles: List<String> = emptyList(),
    /** ADDON only: pre-existing files the add-on overwrote, backed up under [backupRoot] and restored on Remove. */
    val overwritten: List<BackupFile> = emptyList(),
    val backupRoot: String? = null,
    /** Root-aware add-on paths. Empty for legacy records; [addedFiles]/[overwritten] remain compatible. */
    val addedEntries: List<AddonFileRecord> = emptyList(),
    /** ADDON only: the vehicle folder did not exist before; Remove deletes it once it is empty again. */
    val createdDirectory: Boolean = false,
) {
    val key: String get() = vehicleKey(gamePackage, vehicleDirectory)
    val isAddon: Boolean get() = mode == InstallMode.ADDON

    fun toJson(): JSONObject = JSONObject()
        .put("modelId", modelId).put("modelName", modelName).put("gamePackage", gamePackage)
        .put("vehicleDirectory", vehicleDirectory).put("appliedAt", appliedAt).put("active", active)
        .put("fileCount", fileCount).put("totalBytes", totalBytes)
        .put("mode", mode.name).put("addedFiles", JSONArray(addedFiles))
        .put("overwritten", JSONArray(overwritten.map { it.toJson() }))
        .put("backupRoot", backupRoot ?: JSONObject.NULL)
        .put("addedEntries", JSONArray(addedEntries.map { it.toJson() }))
        .put("createdDirectory", createdDirectory)

    companion object {
        fun fromJson(j: JSONObject) = InstalledCarModel(
            j.getString("modelId"), j.optString("modelName"), j.getString("gamePackage"), j.getString("vehicleDirectory"),
            j.optLong("appliedAt"), j.optBoolean("active"), j.optInt("fileCount"), j.optLong("totalBytes"),
            mode = runCatching { InstallMode.valueOf(j.optString("mode")) }.getOrDefault(InstallMode.REPLACE),
            addedFiles = j.optJSONArray("addedFiles").strings(),
            overwritten = j.optJSONArray("overwritten").objects { runCatching { BackupFile.fromJson(it) }.getOrNull() },
            backupRoot = j.optString("backupRoot").takeIf { it.isNotBlank() && it != "null" },
            addedEntries = j.optJSONArray("addedEntries").objects { runCatching { AddonFileRecord.fromJson(it) }.getOrNull() },
            createdDirectory = j.optBoolean("createdDirectory"),
        )
    }
}

enum class CarModelActivityKind { IMPORT, BACKUP, VERIFY, APPLY, REVERT, DELETE_BACKUP, REMOVE, UNINSTALL }

enum class CarModelOperationState { PREPARING, BACKING_UP, INSTALLING, REVERTING, SUCCEEDED, FAILED, INTERRUPTED, RECOVERING }

data class CarModelOperation(
    val id: String,
    val transactionId: String,
    val kind: CarModelActivityKind,
    val gamePackage: String,
    val vehicleDirectory: String,
    val state: CarModelOperationState,
    val completed: Int,
    val total: Int,
    val message: String? = null,
    val createdAt: Long,
    val updatedAt: Long,
) {
    fun toJson() = JSONObject().put("id", id).put("transactionId", transactionId).put("kind", kind.name)
        .put("gamePackage", gamePackage).put("vehicleDirectory", vehicleDirectory).put("state", state.name)
        .put("completed", completed).put("total", total).put("message", message ?: JSONObject.NULL)
        .put("createdAt", createdAt).put("updatedAt", updatedAt)
    companion object {
        fun fromJson(j: JSONObject) = CarModelOperation(j.getString("id"), j.optString("transactionId", j.getString("id")),
            runCatching { CarModelActivityKind.valueOf(j.optString("kind")) }.getOrDefault(CarModelActivityKind.APPLY), j.getString("gamePackage"), j.getString("vehicleDirectory"),
            runCatching { CarModelOperationState.valueOf(j.optString("state")) }.getOrDefault(CarModelOperationState.FAILED), j.optInt("completed"), j.optInt("total"), j.optString("message").takeIf { it.isNotBlank() && it != "null" }, j.optLong("createdAt"), j.optLong("updatedAt"))
    }
}

data class CarModelActivity(val id: String, val kind: CarModelActivityKind, val title: String, val detail: String, val timestamp: Long, val success: Boolean) {
    fun toJson(): JSONObject = JSONObject().put("id", id).put("kind", kind.name).put("title", title).put("detail", detail).put("timestamp", timestamp).put("success", success)
    companion object {
        fun fromJson(j: JSONObject) = CarModelActivity(
            j.getString("id"), runCatching { CarModelActivityKind.valueOf(j.optString("kind")) }.getOrDefault(CarModelActivityKind.VERIFY),
            j.optString("title"), j.optString("detail"), j.optLong("timestamp"), j.optBoolean("success"),
        )
    }
}

data class CarModelState(
    val models: List<LocalCarModel> = emptyList(),
    val installed: List<InstalledCarModel> = emptyList(),
    val backups: List<CarModelBackup> = emptyList(),
    val activity: List<CarModelActivity> = emptyList(),
    val operations: List<CarModelOperation> = emptyList(),
) {
    fun model(id: String) = models.firstOrNull { it.id == id }
    fun backup(gamePackage: String, vehicleDirectory: String) = backups.firstOrNull { it.key == vehicleKey(gamePackage, vehicleDirectory) }
    fun activeInstall(gamePackage: String, vehicleDirectory: String) = installed.firstOrNull { it.active && it.key == vehicleKey(gamePackage, vehicleDirectory) }

    fun toJson(): JSONObject = JSONObject()
        .put("models", JSONArray(models.map { it.toJson() }))
        .put("installed", JSONArray(installed.map { it.toJson() }))
        .put("backups", JSONArray(backups.map { it.toJson() }))
        .put("activity", JSONArray(activity.map { it.toJson() }))
        .put("operations", JSONArray(operations.map { it.toJson() }))

    companion object {
        fun fromJson(j: JSONObject) = CarModelState(
            models = j.optJSONArray("models").objects { runCatching { LocalCarModel.fromJson(it) }.getOrNull() },
            installed = j.optJSONArray("installed").objects { runCatching { InstalledCarModel.fromJson(it) }.getOrNull() },
            backups = j.optJSONArray("backups").objects { runCatching { CarModelBackup.fromJson(it) }.getOrNull() },
            activity = j.optJSONArray("activity").objects { runCatching { CarModelActivity.fromJson(it) }.getOrNull() },
            operations = j.optJSONArray("operations").objects { runCatching { CarModelOperation.fromJson(it) }.getOrNull() },
        )
    }
}

fun vehicleKey(gamePackage: String, vehicleDirectory: String) = "$gamePackage/$vehicleDirectory"

private fun JSONArray?.strings(): List<String> = if (this == null) emptyList() else (0 until length()).map { getString(it) }
private fun <T : Any> JSONArray?.objects(parse: (JSONObject) -> T?): List<T> = if (this == null) emptyList() else (0 until length()).mapNotNull { parse(getJSONObject(it)) }
