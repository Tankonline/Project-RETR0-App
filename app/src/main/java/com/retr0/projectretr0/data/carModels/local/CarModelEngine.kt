package com.retr0.projectretr0.data.carModels.local

import com.retr0.projectretr0.data.carModels.CarModelErrorCode
import com.retr0.projectretr0.data.carModels.CarModelException
import com.retr0.projectretr0.data.carModels.CarModelLog
import com.retr0.projectretr0.data.carModels.asCarModelException
import com.retr0.projectretr0.data.carModels.manifest.CarModelManifest
import com.retr0.projectretr0.data.carModels.manifest.CarModelManifestValidator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.security.MessageDigest

/** What the target vehicle directory looks like for a given model, before anything is changed. */
data class TargetInspection(val targetPath: String, val exists: Boolean, val stockFileCount: Int, val matchingFiles: Int) {
    val status: TargetStatus get() = when {
        !exists || stockFileCount == 0 -> TargetStatus.MISSING
        matchingFiles == 0 -> TargetStatus.INCOMPATIBLE
        else -> TargetStatus.FOUND
    }
}
enum class TargetStatus { FOUND, MISSING, INCOMPATIBLE }

/** What an add-on would do to its destination folder: which files are new and which would be overwritten. */
data class AddonInspection(val targetPath: String, val exists: Boolean, val newFiles: List<String>, val overwrites: List<String>, val occupiedBy: InstalledCarModel?) {
    val nothingOverwritten: Boolean get() = overwrites.isEmpty()
}

sealed interface ApplyOutcome {
    data class Success(val filesCopied: Int, val appliedAt: Long) : ApplyOutcome
    /** [originalRestored] is true when every stock file was put back and re-verified after the failure. */
    data class Failed(val reason: String, val gameFilesTouched: Boolean, val originalRestored: Boolean, val code: String) : ApplyOutcome
}

/**
 * Backup-first car-model pipeline: create + verify + restore originals, and apply a model on top.
 * [apply] re-verifies the backup itself, so the gate holds even if a caller skips the UI check.
 * Every failure is a [CarModelException] with a stable code and is logged under [CarModelLog.TAG].
 */
class CarModelEngine(
    private val store: CarModelStore,
    private val paths: CarModelPaths = CarModelPaths(),
    private val clock: () -> Long = System::currentTimeMillis,
) {
    /** Locks are per package/vehicle so unrelated RR3 targets can progress independently. */
    private val locks = java.util.concurrent.ConcurrentHashMap<String, Mutex>()
    private fun lockFor(gamePackage: String, vehicleDirectory: String) = locks.computeIfAbsent(vehicleKey(gamePackage, vehicleDirectory)) { Mutex() }

    suspend fun listVehicles(ops: CarModelFileOps, gamePackage: String): Result<List<String>> = io(CarModelErrorCode.VEHICLES_UNREADABLE) {
        ops.listDirectories(paths.packageRoot(gamePackage), paths.vehiclesRoot(gamePackage))
            .filter { CarModelPaths.SAFE_VEHICLE_DIR.matches(it) }.sortedBy { it.lowercase() }
    }.logged("TARGET", "list vehicles", gamePackage, null) { "${it.size} vehicles" }

    suspend fun inspectTarget(ops: CarModelFileOps, manifest: CarModelManifest, gamePackage: String, vehicleDirectory: String): Result<TargetInspection> = io {
        val target = paths.vehicleDir(gamePackage, vehicleDirectory)
        if (!ops.isDirectory(target)) return@io TargetInspection(target, false, 0, 0)
        val stock = ops.listTree(paths.packageRoot(gamePackage), target).map { it.relativePath.lowercase() }.toSet()
        TargetInspection(target, true, stock.size, manifest.files.count { it.sourcePath.lowercase() in stock })
    }.logged("TARGET", "inspect target", gamePackage, vehicleDirectory) { "status=${it.status} stock=${it.stockFileCount} matching=${it.matchingFiles}" }

    /** Returns the verified original backup for this vehicle, creating it from the current game files if none exists. */
    suspend fun ensureBackup(ops: CarModelFileOps, gamePackage: String, vehicleDirectory: String, onProgress: (Int, Int) -> Unit = { _, _ -> }): Result<CarModelBackup> = locked(gamePackage, vehicleDirectory) {
        CarModelLog.info("CM-BACKUP-START", "ensure backup ${CarModelLog.where(gamePackage, vehicleDirectory)}")
        val target = paths.vehicleDir(gamePackage, vehicleDirectory)
        val backupDir = paths.backupDir(gamePackage, vehicleDirectory)
        val known = store.current.backup(gamePackage, vehicleDirectory) ?: adoptExisting(ops, gamePackage, vehicleDirectory, backupDir)
        if (known != null) {
            // An existing backup holds the real originals; it is only ever re-verified, never overwritten.
            val checked = verify(ops, known)
            store.saveBackup(checked)
            if (checked.status == BackupStatus.MISSING) fail(CarModelErrorCode.BACKUP_MISSING)
            if (!checked.verified) fail(CarModelErrorCode.BACKUP_CORRUPT, "The original backup for this vehicle is corrupt (${checked.problemCount} files).")
            return@locked checked
        }
        ensure(store.current.activeInstall(gamePackage, vehicleDirectory) == null, CarModelErrorCode.BACKUP_GONE_WHILE_APPLIED)
        ensure(ops.isDirectory(target), CarModelErrorCode.TARGET_NOT_FOUND)
        val stock = ops.listTree(paths.packageRoot(gamePackage), target)
        ensure(stock.isNotEmpty(), CarModelErrorCode.BACKUP_TARGET_EMPTY)
        ops.delete(backupDir) // leftovers of an attempt that never completed (no manifest was written)
        val files = runCatching {
            stock.mapIndexed { index, file ->
                val relative = safePath(file.relativePath)
                val hash = ops.sha256("$target/$relative")
                ensure(hash.matches(SHA256), CarModelErrorCode.BACKUP_READ_FAILED) { "Unable to read $relative" }
                ensure(ops.copy("$target/$relative", "$backupDir/files/$relative"), CarModelErrorCode.BACKUP_COPY_FAILED) { "Unable to copy $relative" }
                ensure(ops.sha256("$backupDir/files/$relative") == hash, CarModelErrorCode.BACKUP_COPY_MISMATCH) { "Backup copy of $relative does not match the original" }
                onProgress(index + 1, stock.size)
                CarModelLog.step("backup copied ${index + 1}/${stock.size} $relative")
                BackupFile(relative, file.sizeBytes, hash)
            }
        }.getOrElse { error ->
            ops.delete(backupDir)
            store.log(CarModelActivityKind.BACKUP, vehicleDirectory, coded(error, error.message ?: "Backup failed"), false, clock())
            throw error
        }
        val backup = CarModelBackup(gamePackage, vehicleDirectory, backupDir, files, clock(), BackupStatus.VERIFIED)
        ensure(ops.writeText("$backupDir/$BACKUP_MANIFEST", backup.toJson().toString()), CarModelErrorCode.BACKUP_MANIFEST_WRITE_FAILED)
        store.saveBackup(backup)
        store.log(CarModelActivityKind.BACKUP, vehicleDirectory, "${files.size} original files backed up and verified", true, clock())
        backup
    }.logged("BACKUP", "ensure backup", gamePackage, vehicleDirectory) { "status=${it.status} files=${it.files.size} dest=${it.root}" }

    /** Re-hashes the stored backup files against the recorded manifest and saves the result. */
    suspend fun verifyBackup(ops: CarModelFileOps, gamePackage: String, vehicleDirectory: String): Result<CarModelBackup> = locked(gamePackage, vehicleDirectory) {
        val backup = store.current.backup(gamePackage, vehicleDirectory) ?: fail(CarModelErrorCode.NO_BACKUP)
        verify(ops, backup).also { store.saveBackup(it) }
    }.logged("BACKUP", "verify backup", gamePackage, vehicleDirectory) { "status=${it.status} files=${it.files.size} problems=${it.problemCount}" }

    suspend fun apply(ops: CarModelFileOps, modelId: String, gamePackage: String, vehicleDirectory: String, onProgress: (Int, Int) -> Unit = { _, _ -> }): ApplyOutcome {
        var touched = false
        var verifiedBackup: CarModelBackup? = null
        var modelName = modelId
        val where = CarModelLog.where(gamePackage, vehicleDirectory)
        CarModelLog.info("CM-APPLY-START", "apply model=$modelId $where")
        val result = locked(gamePackage, vehicleDirectory) {
            val model = store.current.model(modelId) ?: fail(CarModelErrorCode.MODEL_NOT_FOUND)
            modelName = model.name
            val target = paths.vehicleDir(gamePackage, vehicleDirectory)
            val recorded = store.current.backup(gamePackage, vehicleDirectory) ?: fail(CarModelErrorCode.BACKUP_REQUIRED)
            val backup = verify(ops, recorded).also { store.saveBackup(it) }
            ensure(backup.verified, CarModelErrorCode.BACKUP_NOT_VERIFIED)
            verifiedBackup = backup
            ensure(model.manifest.files.isNotEmpty(), CarModelErrorCode.MODEL_EMPTY)
            val sources = model.manifest.files.map { entry ->
                val sourceRelative = safePath(entry.sourcePath)
                val destinationRelative = safePath(entry.destinationPath)
                val local = File(model.filesRoot, sourceRelative).canonicalFile
                val libraryRoot = File(model.filesRoot).canonicalFile.toPath()
                ensure(local.toPath().startsWith(libraryRoot), CarModelErrorCode.UNSAFE_PATH)
                ensure(local.isFile && local.length() == entry.sizeBytes && sha256(local).equals(entry.sha256, true), CarModelErrorCode.LIBRARY_FILE_CHANGED) { "Library file changed or missing: $sourceRelative" }
                Triple(destinationRelative, local, entry.sha256.lowercase())
            }
            ensure(ops.isDirectory(target), CarModelErrorCode.TARGET_NOT_FOUND)
            ensure(ops.ensureGameStopped(gamePackage), CarModelErrorCode.GAME_RUNNING)
            touched = true
            // Start from stock so files of a previously applied model never mix with this one.
            if (store.current.activeInstall(gamePackage, vehicleDirectory) != null) {
                CarModelLog.info("CM-APPLY-START", "restoring originals before re-apply $where")
                restore(ops, backup, target, backup.addedFiles)
            }
            val added = sources.map { it.first }.filter { path -> backup.files.none { it.path.equals(path, true) } }
            store.saveBackup(backup.copy(addedFiles = (backup.addedFiles + added).distinct()))
            sources.forEachIndexed { index, (relative, local, hash) ->
                ensure(ops.importLocal(local, "$target/$relative"), CarModelErrorCode.APPLY_COPY_FAILED) { "Unable to copy $relative" }
                ensure(ops.sha256("$target/$relative") == hash, CarModelErrorCode.APPLY_HASH_MISMATCH) { "Hash mismatch after copying $relative" }
                onProgress(index + 1, sources.size)
                CarModelLog.step("apply copied ${index + 1}/${sources.size} $relative")
            }
            // FULL_DIRECTORY_REPLACEMENT: remove stock files not present in this model only after backup verification and staged copies.
            val destinationPaths = sources.map { it.first }.toSet()
            backup.files.filter { it.path !in destinationPaths }.forEach { original ->
                ensure(ops.delete("$target/${safePath(original.path)}"), CarModelErrorCode.APPLY_COPY_FAILED) { "Unable to remove stale ${original.path}" }
            }
            val now = clock()
            store.update { state ->
                state.copy(installed = state.installed.filterNot { it.key == vehicleKey(gamePackage, vehicleDirectory) } +
                    InstalledCarModel(model.id, model.name, gamePackage, vehicleDirectory, now, true, model.fileCount, model.totalBytes))
            }
            store.log(CarModelActivityKind.APPLY, model.name, "${sources.size} files applied to $vehicleDirectory and verified", true, now)
            ApplyOutcome.Success(sources.size, now)
        }
        return result.fold(
            onSuccess = { outcome ->
                CarModelLog.info("CM-APPLY-OK", "apply ok: ${outcome.filesCopied} files applied and verified, ${added(gamePackage, vehicleDirectory)} added $where")
                outcome
            },
            onFailure = { error ->
                val failure = error.asCarModelException()
                val reason = failure.message ?: "Install failed"
                CarModelLog.fail(failure.error, "apply failed: $reason (gameFilesTouched=$touched) $where")
                val backup = verifiedBackup
                val restored = touched && backup != null && locked(gamePackage, vehicleDirectory) {
                    val latest = store.current.backup(gamePackage, vehicleDirectory) ?: backup
                    restore(ops, backup, paths.vehicleDir(gamePackage, vehicleDirectory), latest.addedFiles)
                    markReverted(gamePackage, vehicleDirectory)
                }.onFailure { rollback ->
                    val coded = rollback.asCarModelException()
                    CarModelLog.fail(coded.error, "apply rollback failed: ${coded.message} $where")
                }.isSuccess
                if (restored) CarModelLog.info("CM-REVERT-OK", "apply rollback ok: originals restored and verified $where")
                store.log(CarModelActivityKind.APPLY, modelName, "[${failure.error.code}] " + if (restored) "$reason — original files restored" else reason, false, clock())
                ApplyOutcome.Failed(reason, touched, restored, failure.error.code)
            },
        )
    }

    /** Routes the wizard's choice. REPLACE goes to [apply] unchanged; it is only refused while an add-on owns the folder. */
    suspend fun install(ops: CarModelFileOps, mode: InstallMode, modelId: String, gamePackage: String, vehicleDirectory: String, onProgress: (Int, Int) -> Unit = { _, _ -> }): ApplyOutcome = when (mode) {
        InstallMode.ADDON -> applyAddon(ops, modelId, gamePackage, vehicleDirectory, onProgress)
        InstallMode.REPLACE -> {
            val where = CarModelLog.where(gamePackage, vehicleDirectory)
            if (store.current.activeInstall(gamePackage, vehicleDirectory)?.isAddon == true) {
                val code = CarModelErrorCode.REPLACE_ON_ADDON
                CarModelLog.fail(code, "apply refused mode=replace: add-on active $where")
                ApplyOutcome.Failed(code.userMessage, false, false, code.code)
            } else {
                CarModelLog.info("CM-APPLY-START", "apply mode=replace model=$modelId $where")
                apply(ops, modelId, gamePackage, vehicleDirectory, onProgress)
            }
        }
    }

    suspend fun inspectAddonTarget(ops: CarModelFileOps, manifest: CarModelManifest, gamePackage: String, vehicleDirectory: String): Result<AddonInspection> = io {
        val target = paths.vehicleDir(gamePackage, vehicleDirectory)
        val occupied = store.current.activeInstall(gamePackage, vehicleDirectory)
        val exists = ops.isDirectory(target)
        val existing = if (exists) ops.listTree(paths.packageRoot(gamePackage), target).map { it.relativePath.lowercase() }.toSet() else emptySet()
        val (overwrites, fresh) = manifest.files.map { it.destinationPath }.partition { it.lowercase() in existing }
        AddonInspection(target, exists, fresh, overwrites, occupied)
    }.logged("TARGET", "inspect addon target", gamePackage, vehicleDirectory) { "exists=${it.exists} new=${it.newFiles.size} overwrites=${it.overwrites.size} occupied=${it.occupiedBy != null}" }

    /**
     * Installs a model as an additional car: no whole-folder backup is required because nothing existing is
     * replaced. Any pre-existing file the add-on would overwrite is backed up and verified on its own first.
     * The install is recorded before the first copy, so a partial install can always be cleaned up.
     */
    suspend fun applyAddon(ops: CarModelFileOps, modelId: String, gamePackage: String, vehicleDirectory: String, onProgress: (Int, Int) -> Unit = { _, _ -> }): ApplyOutcome {
        var touched = false
        var modelName = modelId
        val where = CarModelLog.where(gamePackage, vehicleDirectory)
        CarModelLog.info("CM-APPLY-START", "apply mode=addon model=$modelId $where")
        val result = locked(gamePackage, vehicleDirectory) {
            val model = store.current.model(modelId) ?: fail(CarModelErrorCode.MODEL_NOT_FOUND)
            modelName = model.name
            ensure(store.current.activeInstall(gamePackage, vehicleDirectory) == null, CarModelErrorCode.ADDON_TARGET_OCCUPIED)
            ensure(model.manifest.files.isNotEmpty(), CarModelErrorCode.MODEL_EMPTY)
            val target = paths.vehicleDir(gamePackage, vehicleDirectory)
            val backupDir = paths.addonBackupDir(gamePackage, vehicleDirectory)
            val existed = ops.isDirectory(target)
            ensure(ops.createDirectory(target), CarModelErrorCode.ADDON_COPY_FAILED) { "Unable to create add-on destination" }
            val sources = verifiedSources(model)
            ensure(ops.ensureGameStopped(gamePackage), CarModelErrorCode.GAME_RUNNING)
            val existing = existingAddonFiles(ops, gamePackage, vehicleDirectory, target, existed)
            val (overwriting, fresh) = sources.map { it.relative }.partition { relative ->
                val source = sources.first { it.relative == relative }
                addonKey(source.entry.destinationRoot, relative) in existing || (source.entry.destinationRoot == com.retr0.projectretr0.data.carModels.manifest.CarModelDestinationRoot.VEHICLE && relative.lowercase() in existing)
            }
            // One transaction folder and one manifest cover every destination root.
            ops.delete(backupDir)
            val overwritten = sources.filter { source -> addonKey(source.entry.destinationRoot, source.relative).lowercase() in existing }.map { source ->
                val root = source.entry.destinationRoot.name
                val relative = source.relative
                val key = addonKey(source.entry.destinationRoot, relative).lowercase()
                val original = existing[key] ?: existing.getValue(relative.lowercase())
                val destination = paths.destination(gamePackage, vehicleDirectory, source.entry)
                val hash = ops.sha256(destination)
                ensure(hash.matches(SHA256), CarModelErrorCode.ADDON_BACKUP_FAILED) { "Unable to read $key before overwriting it" }
                val backupPath = addonBackupFile(backupDir, root, relative)
                ensure(ops.copy(destination, backupPath) && ops.sha256(backupPath).equals(hash, true), CarModelErrorCode.ADDON_BACKUP_FAILED)
                if (root == "VEHICLE") ensure(ops.copy(destination, "$backupDir/files/${safePath(relative)}"), CarModelErrorCode.ADDON_BACKUP_FAILED) { "Unable to back up $key before overwriting it" }
                BackupFile(relative, original.sizeBytes, hash, root)
            }
            val addedEntries = sources.filter { addonKey(it.entry.destinationRoot, it.relative).lowercase() !in existing }
                .map { AddonFileRecord(it.relative, it.entry.destinationRoot.name, it.hash) }
            val now = clock()
            val record = InstalledCarModel(model.id, model.name, gamePackage, vehicleDirectory, now, true, model.fileCount, model.totalBytes,
                InstallMode.ADDON, fresh, overwritten, backupDir, addedEntries, createdDirectory = !existed)
            ensure(ops.writeText("$backupDir/$BACKUP_MANIFEST", JSONObject().put("type", "addon-transaction-v1").put("gamePackage", gamePackage).put("vehicleDirectory", vehicleDirectory).put("files", JSONArray((overwritten.map { it.toJson() } + addedEntries.map { it.toJson() }))).toString()), CarModelErrorCode.ADDON_BACKUP_FAILED)
            store.update { state -> state.copy(installed = state.installed.filterNot { it.key == record.key } + record) }
            touched = true
            sources.forEachIndexed { index, source ->
                val destination = paths.destination(gamePackage, vehicleDirectory, source.entry)
                ensure(ops.createDirectory(File(destination).parent ?: target), CarModelErrorCode.ADDON_COPY_FAILED)
                ensure(ops.importLocal(source.file, destination), CarModelErrorCode.ADDON_COPY_FAILED) { "Unable to copy ${source.relative}" }
                ensure(ops.sha256(destination) == source.hash, CarModelErrorCode.ADDON_HASH_MISMATCH) { "Hash mismatch after copying ${source.relative}" }
                onProgress(index + 1, sources.size)
                CarModelLog.step("addon copied ${index + 1}/${sources.size} ${source.relative}")
            }
            val detail = if (overwritten.isEmpty()) ", nothing overwritten" else ", ${overwritten.size} overwritten (backed up)"
            store.log(CarModelActivityKind.APPLY, model.name, "Add-on: ${fresh.size} files added to $vehicleDirectory$detail", true, now)
            ApplyOutcome.Success(sources.size, now)
        }
        return result.fold(
            onSuccess = { outcome ->
                CarModelLog.info("CM-APPLY-OK", "apply ok mode=addon: ${outcome.filesCopied} files installed and verified $where")
                outcome
            },
            onFailure = { error ->
                val failure = error.asCarModelException()
                val reason = failure.message ?: "Install failed"
                CarModelLog.fail(failure.error, "apply failed mode=addon: $reason (gameFilesTouched=$touched) $where")
                val restored = touched && locked(gamePackage, vehicleDirectory) { uninstallAddon(ops, gamePackage, vehicleDirectory, allowPartial = true) }
                    .onFailure { rollback -> val coded = rollback.asCarModelException(); CarModelLog.fail(coded.error, "addon rollback failed: ${coded.message} $where") }
                    .isSuccess
                if (restored) CarModelLog.info("CM-ADDON-OK", "addon rollback ok: added files removed $where")
                store.log(CarModelActivityKind.APPLY, modelName, "[${failure.error.code}] " + if (restored) "$reason — add-on files removed" else reason, false, clock())
                ApplyOutcome.Failed(reason, touched, restored, failure.error.code)
            },
        )
    }

    /** Uninstalls an add-on: deletes exactly the files it added and puts back any file it overwrote. */
    suspend fun removeAddon(ops: CarModelFileOps, gamePackage: String, vehicleDirectory: String, onProgress: (Int, Int) -> Unit = { _, _ -> }): Result<Int> = locked(gamePackage, vehicleDirectory) {
        CarModelLog.info("CM-ADDON-START", "remove addon ${CarModelLog.where(gamePackage, vehicleDirectory)}")
        val install = store.current.activeInstall(gamePackage, vehicleDirectory)?.takeIf { it.isAddon } ?: fail(CarModelErrorCode.ADDON_NOT_INSTALLED)
        ensure(ops.ensureGameStopped(gamePackage), CarModelErrorCode.GAME_RUNNING)
        val count = runCatching { uninstallAddon(ops, gamePackage, vehicleDirectory, onProgress) }.getOrElse { error ->
            store.log(CarModelActivityKind.UNINSTALL, install.modelName, coded(error, error.message ?: "Remove failed"), false, clock())
            throw error
        }
        val restoredDetail = if (install.overwritten.isEmpty()) "" else ", ${install.overwritten.size} overwritten files restored"
        store.log(CarModelActivityKind.UNINSTALL, install.modelName, "Add-on removed from $vehicleDirectory: ${install.addedFiles.size} files deleted$restoredDetail", true, clock())
        count
    }.logged("ADDON", "remove addon", gamePackage, vehicleDirectory) { "$it files removed or restored" }

    private fun uninstallAddon(ops: CarModelFileOps, gamePackage: String, vehicleDirectory: String, onProgress: (Int, Int) -> Unit = { _, _ -> }, allowPartial: Boolean = false): Int {
        val install = store.current.activeInstall(gamePackage, vehicleDirectory)?.takeIf { it.isAddon } ?: fail(CarModelErrorCode.ADDON_NOT_INSTALLED)
        val target = paths.vehicleDir(gamePackage, vehicleDirectory)
        val root = install.backupRoot
        val entries = if (install.addedEntries.isNotEmpty()) install.addedEntries else install.addedFiles.map { AddonFileRecord(it) }
        fun destination(entry: AddonFileRecord): String = paths.destination(gamePackage, vehicleDirectory, com.retr0.projectretr0.data.carModels.manifest.CarModelFile("", entry.path, "", "", sizeBytes = 1, sha256 = "0".repeat(64), destinationRoot = runCatching { com.retr0.projectretr0.data.carModels.manifest.CarModelDestinationRoot.valueOf(entry.root) }.getOrDefault(com.retr0.projectretr0.data.carModels.manifest.CarModelDestinationRoot.VEHICLE)))
        // Refuse removal if any installed file was modified after installation.
        if (!allowPartial) entries.forEach { entry ->
            if (entry.sha256.isNotBlank()) ensure(ops.sha256(destination(entry)).equals(entry.sha256, true), CarModelErrorCode.ADDON_REMOVE_FAILED) { "Installed file was modified: ${entry.root}/${entry.path}" }
        }
        install.overwritten.forEach { file ->
            val backupPath = addonBackupFile(root ?: "", file.root, file.path)
            ensure(root != null && ops.sha256(backupPath).equals(file.sha256, true), CarModelErrorCode.ADDON_RESTORE_FAILED) { "Backup of ${file.root}/${file.path} is missing or corrupt" }
        }
        val total = entries.size + install.overwritten.size
        entries.forEachIndexed { index, entry ->
            val relative = safePath(entry.path)
            ensure(ops.delete(destination(entry)), CarModelErrorCode.ADDON_REMOVE_FAILED) { "Unable to delete ${entry.root}/$relative" }
            onProgress(index + 1, total)
            CarModelLog.step("addon removed ${entry.root}/$relative")
        }
        install.overwritten.forEachIndexed { index, file ->
            val backupPath = addonBackupFile(root ?: "", file.root, file.path)
            val destinationPath = paths.destination(gamePackage, vehicleDirectory, com.retr0.projectretr0.data.carModels.manifest.CarModelFile("", file.path, "", "", sizeBytes = 1, sha256 = "0".repeat(64), destinationRoot = runCatching { com.retr0.projectretr0.data.carModels.manifest.CarModelDestinationRoot.valueOf(file.root) }.getOrDefault(com.retr0.projectretr0.data.carModels.manifest.CarModelDestinationRoot.VEHICLE)))
            ensure(ops.copy(backupPath, destinationPath) && ops.sha256(destinationPath).equals(file.sha256, true), CarModelErrorCode.ADDON_RESTORE_FAILED) { "Unable to restore ${file.root}/${file.path}" }
            onProgress(entries.size + index + 1, total)
            CarModelLog.step("addon restored overwritten ${file.root}/${file.path}")
        }
        // A folder the add-on created is removed once nothing else lives in it.
        if (install.createdDirectory && ops.isDirectory(target) && ops.listTree(paths.packageRoot(gamePackage), target).isEmpty()) ops.delete(target)
        if (root != null) ops.delete(root)
        markReverted(gamePackage, vehicleDirectory)
        return total
    }

    private fun addonKey(root: com.retr0.projectretr0.data.carModels.manifest.CarModelDestinationRoot, relative: String) = "${root.name}/$relative".lowercase()
    private fun addonBackupFile(backupDir: String, root: String, relative: String) = "$backupDir/files/$root/${safePath(relative)}"
    private fun existingAddonFiles(ops: CarModelFileOps, gamePackage: String, vehicleDirectory: String, target: String, existed: Boolean): Map<String, RemoteFile> {
        val result = linkedMapOf<String, RemoteFile>()
        if (existed) ops.listTree(paths.packageRoot(gamePackage), target).forEach {
            result[addonKey(com.retr0.projectretr0.data.carModels.manifest.CarModelDestinationRoot.VEHICLE, it.relativePath)] = it
            result[it.relativePath.lowercase()] = it
        }
        fun add(root: com.retr0.projectretr0.data.carModels.manifest.CarModelDestinationRoot, dir: String) { if (ops.isDirectory(dir)) ops.listTree(paths.packageRoot(gamePackage), dir).forEach { result[addonKey(root, it.relativePath)] = it } }
        add(com.retr0.projectretr0.data.carModels.manifest.CarModelDestinationRoot.VEHICLES_DATA, paths.vehiclesDataRoot(gamePackage))
        add(com.retr0.projectretr0.data.carModels.manifest.CarModelDestinationRoot.DEPOT_DATA, paths.depotDataRoot(gamePackage))
        add(com.retr0.projectretr0.data.carModels.manifest.CarModelDestinationRoot.DEPOT_ROOT, paths.depotRoot(gamePackage))
        return result
    }

    private data class VerifiedSource(val relative: String, val file: File, val hash: String, val entry: com.retr0.projectretr0.data.carModels.manifest.CarModelFile)
    private fun verifiedSources(model: LocalCarModel): List<VerifiedSource> {
        val libraryRoot = File(model.filesRoot).canonicalFile.toPath()
        return model.manifest.files.map { entry ->
            val sourceRelative = safePath(entry.sourcePath)
            val local = File(model.filesRoot, sourceRelative).canonicalFile
            ensure(local.toPath().startsWith(libraryRoot), CarModelErrorCode.UNSAFE_PATH)
            ensure(local.isFile && local.length() == entry.sizeBytes && sha256(local).equals(entry.sha256, true), CarModelErrorCode.LIBRARY_FILE_CHANGED) { "Library file changed or missing: $sourceRelative" }
            VerifiedSource(safePath(entry.destinationPath), local, entry.sha256.lowercase(), entry)
        }
    }

    /** Restores the stock files from the verified backup. Refuses when the backup is corrupt or missing. */
    suspend fun revert(ops: CarModelFileOps, gamePackage: String, vehicleDirectory: String, onProgress: (Int, Int) -> Unit = { _, _ -> }): Result<Int> = locked(gamePackage, vehicleDirectory) {
        CarModelLog.info("CM-REVERT-START", "revert ${CarModelLog.where(gamePackage, vehicleDirectory)}")
        val recorded = store.current.backup(gamePackage, vehicleDirectory) ?: fail(CarModelErrorCode.NO_BACKUP)
        val backup = verify(ops, recorded).also { store.saveBackup(it) }
        if (backup.status == BackupStatus.MISSING) fail(CarModelErrorCode.REVERT_BACKUP_MISSING)
        if (!backup.verified) fail(CarModelErrorCode.REVERT_BACKUP_CORRUPT, "Hash mismatch on ${backup.problemCount} files. Restore is unavailable.")
        ensure(ops.ensureGameStopped(gamePackage), CarModelErrorCode.REVERT_GAME_RUNNING)
        val count = runCatching { restore(ops, backup, paths.vehicleDir(gamePackage, vehicleDirectory), backup.addedFiles, onProgress) }.getOrElse { error ->
            store.log(CarModelActivityKind.REVERT, vehicleDirectory, coded(error, error.message ?: "Restore failed"), false, clock())
            throw error
        }
        markReverted(gamePackage, vehicleDirectory)
        store.log(CarModelActivityKind.REVERT, vehicleDirectory, "$count original files restored and verified", true, clock())
        count
    }.logged("REVERT", "revert", gamePackage, vehicleDirectory) { "$it original files restored and verified" }

    suspend fun deleteBackup(ops: CarModelFileOps, gamePackage: String, vehicleDirectory: String): Result<Unit> = locked(gamePackage, vehicleDirectory) {
        val backup = store.current.backup(gamePackage, vehicleDirectory) ?: fail(CarModelErrorCode.NO_BACKUP)
        ensure(backup.root == paths.backupDir(gamePackage, vehicleDirectory), CarModelErrorCode.BACKUP_LOCATION_UNRECOGNISED)
        ensure(ops.delete(backup.root), CarModelErrorCode.BACKUP_DELETE_FAILED)
        store.update { state -> state.copy(backups = state.backups.filterNot { it.key == backup.key }) }
        store.log(CarModelActivityKind.DELETE_BACKUP, vehicleDirectory, "Original backup deleted", true, clock())
        Unit
    }.logged("BACKUP", "delete backup", gamePackage, vehicleDirectory) { "backup deleted" }

    /** Removes a model from the local library. Game files and backups are not touched. */
    suspend fun removeModel(modelId: String): Result<Unit> = locked {
        val model = store.current.model(modelId) ?: fail(CarModelErrorCode.MODEL_NOT_FOUND)
        ensure(store.current.installed.none { it.active && it.modelId == modelId }, CarModelErrorCode.MODEL_STILL_APPLIED)
        store.libraryDir(modelId).deleteRecursively()
        store.update { state -> state.copy(models = state.models.filterNot { it.id == modelId }) }
        store.log(CarModelActivityKind.REMOVE, model.name, "Removed from library", true, clock())
        Unit
    }.logged("IMPORT", "remove model $modelId", null, null) { "removed from library" }

    private fun adoptExisting(ops: CarModelFileOps, gamePackage: String, vehicleDirectory: String, backupDir: String): CarModelBackup? {
        // Survives cleared app data: the manifest next to the backup files is the source of truth.
        val text = ops.readText("$backupDir/$BACKUP_MANIFEST") ?: return null
        val parsed = runCatching { CarModelBackup.fromJson(JSONObject(text)) }.getOrNull() ?: return null
        if (parsed.gamePackage != gamePackage || parsed.vehicleDirectory != vehicleDirectory || parsed.files.isEmpty()) return null
        CarModelLog.info("CM-BACKUP-START", "adopting on-disk backup manifest ${CarModelLog.where(gamePackage, vehicleDirectory)}")
        return parsed.copy(root = backupDir, addedFiles = emptyList())
    }

    private fun verify(ops: CarModelFileOps, backup: CarModelBackup): CarModelBackup {
        var missing = 0
        var mismatched = 0
        backup.files.forEach { file ->
            val hash = runCatching { ops.sha256("${backup.root}/files/${CarModelManifestValidator.validateRelativePath(file.path)}") }.getOrDefault("")
            when {
                hash.isBlank() -> { missing++; CarModelLog.step("verify: unreadable ${file.path}") }
                !hash.equals(file.sha256, true) -> { mismatched++; CarModelLog.step("verify: hash mismatch ${file.path}") }
            }
        }
        val status = when {
            backup.files.isEmpty() || missing == backup.files.size -> BackupStatus.MISSING
            missing + mismatched > 0 -> BackupStatus.CORRUPT
            else -> BackupStatus.VERIFIED
        }
        return backup.copy(status = status, problemCount = missing + mismatched, verifiedAt = clock())
    }

    private fun restore(ops: CarModelFileOps, backup: CarModelBackup, target: String, addedFiles: List<String>, onProgress: (Int, Int) -> Unit = { _, _ -> }): Int {
        backup.files.forEachIndexed { index, file ->
            val relative = safePath(file.path)
            ensure(ops.copy("${backup.root}/files/$relative", "$target/$relative"), CarModelErrorCode.RESTORE_COPY_FAILED) { "Unable to restore $relative" }
            ensure(ops.sha256("$target/$relative").equals(file.sha256, true), CarModelErrorCode.RESTORE_HASH_MISMATCH) { "Restored file failed verification: $relative" }
            onProgress(index + 1, backup.files.size)
            CarModelLog.step("restore copied ${index + 1}/${backup.files.size} $relative")
        }
        addedFiles.forEach { added ->
            val relative = safePath(added)
            if (backup.files.none { it.path.equals(relative, true) }) {
                ensure(ops.delete("$target/$relative"), CarModelErrorCode.RESTORE_REMOVE_FAILED) { "Unable to remove $relative" }
                CarModelLog.step("restore removed added file $relative")
            }
        }
        store.saveBackup((store.current.backup(backup.gamePackage, backup.vehicleDirectory) ?: backup).copy(addedFiles = emptyList()))
        return backup.files.size
    }

    private fun markReverted(gamePackage: String, vehicleDirectory: String) {
        store.update { state -> state.copy(installed = state.installed.map { if (it.key == vehicleKey(gamePackage, vehicleDirectory)) it.copy(active = false) else it }) }
    }

    private fun added(gamePackage: String, vehicleDirectory: String) = store.current.backup(gamePackage, vehicleDirectory)?.addedFiles?.size ?: 0
    private fun coded(error: Throwable, text: String) = "[${error.asCarModelException().error.code}] $text"

    private fun fail(code: CarModelErrorCode, message: String = code.userMessage): Nothing = throw CarModelException(code, message)
    private inline fun ensure(condition: Boolean, code: CarModelErrorCode, message: () -> String = { code.userMessage }) { if (!condition) fail(code, message()) }
    private fun safePath(path: String): String = try {
        CarModelManifestValidator.validateRelativePath(path)
    } catch (error: IllegalArgumentException) {
        throw CarModelException(CarModelErrorCode.UNSAFE_PATH, error.message ?: CarModelErrorCode.UNSAFE_PATH.userMessage, error)
    }

    /** Logs the outcome of an engine boundary under one tag; the result itself is passed through untouched. */
    private fun <T> Result<T>.logged(category: String, operation: String, gamePackage: String?, vehicleDirectory: String?, ok: (T) -> String): Result<T> {
        val where = CarModelLog.where(gamePackage, vehicleDirectory)
        onSuccess { CarModelLog.info("CM-$category-OK", "$operation ok: ${ok(it)} $where") }
        onFailure { val coded = it.asCarModelException(); CarModelLog.fail(coded.error, "$operation failed: ${coded.message} $where") }
        return this
    }

    private suspend fun <T> io(fallback: CarModelErrorCode = CarModelErrorCode.UNEXPECTED, block: () -> T): Result<T> =
        withContext(Dispatchers.IO) { runCatching(block).recoverCatching { throw it.asCarModelException(fallback) } }
    private suspend fun <T> locked(gamePackage: String, vehicleDirectory: String, block: () -> T): Result<T> = lockFor(gamePackage, vehicleDirectory).withLock { io(block = block) }
    private suspend fun <T> locked(block: () -> T): Result<T> = io(block = block)

    companion object {
        const val BACKUP_MANIFEST = "manifest.json"
        private val SHA256 = Regex("[0-9a-f]{64}")
        fun sha256(file: File): String {
            val digest = MessageDigest.getInstance("SHA-256")
            file.inputStream().use { input ->
                val buffer = ByteArray(32 * 1024)
                while (true) { val count = input.read(buffer); if (count < 0) break; digest.update(buffer, 0, count) }
            }
            return digest.digest().joinToString("") { "%02x".format(it) }
        }
    }
}
