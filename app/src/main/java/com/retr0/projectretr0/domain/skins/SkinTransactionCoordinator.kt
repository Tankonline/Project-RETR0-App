package com.retr0.projectretr0.domain.skins

import android.os.ParcelFileDescriptor
import com.retr0.projectretr0.IRetr0UserService
import com.retr0.projectretr0.data.skins.local.AppliedSkinEntity
import com.retr0.projectretr0.data.skins.local.LocalSkinEntity
import com.retr0.projectretr0.data.skins.local.LocalSkinRepository
import com.retr0.projectretr0.data.skins.local.SkinBackupEntity
import com.retr0.projectretr0.data.skins.local.SkinOperationEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.io.File
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

class SkinTransactionCoordinator(
    private val local: LocalSkinRepository,
    private val backupRoot: File,
) {
    private val locks = ConcurrentHashMap<String, Mutex>()

    suspend fun apply(localSkinId: String, service: IRetr0UserService): Result<String> = runCatching {
        val skin = requireNotNull(local.skin(localSkinId)) { "Local skin not found" }
        val initialTarget = requireNotNull(skin.targetPath) { "Choose an RR3 target before applying" }
        val target = resolveTarget(service, skin, initialTarget)
        locks.getOrPut(target) { Mutex() }.withLock {
            val operationId = UUID.randomUUID().toString()
            update(operationId, skin.id, "APPLY", "CREATED", 0, "Preparing")
            withContext(Dispatchers.IO) {
                require(service.exists(target)) { "RR3 target no longer exists" }
                require(!service.isPackageRunning(skin.gamePackage) || run { service.forceStopPackage(skin.gamePackage); !service.isPackageRunning(skin.gamePackage) }) {
                    "RR3 is still running"
                }
                update(operationId, skin.id, "APPLY", "GAME_STOPPED", 10, "RR3 stopped")
                val originalHash = service.sha256File(target)
                require(originalHash.matches(SHA256)) { "Unable to verify original target" }
                val existingBackup = local.backupForTarget(target)
                val backup = existingBackup ?: createBackup(service, skin.id, target, originalHash)
                require(service.sha256File(backup.backupPath).equals(other = backup.sha256, ignoreCase = true)) { "Original backup is missing or corrupt" }
                update(operationId, skin.id, "APPLY", "BACKUP_VERIFIED", 45, "Original backup verified")
                update(operationId, skin.id, "APPLY", "SWAPPING", 70, "Replacing target")
                val input = ParcelFileDescriptor.open(File(skin.binaryPath), ParcelFileDescriptor.MODE_READ_ONLY)
                require(service.atomicImportSkin(input, target, skin.binarySha256, skin.binarySize)) { "Unable to atomically replace RR3 skin" }
                require(service.sha256File(target).equals(skin.binarySha256, true)) { "Applied skin verification failed" }
                local.saveApplied(AppliedSkinEntity(UUID.randomUUID().toString(), skin.id, target, backup.id, System.currentTimeMillis()))
                update(operationId, skin.id, "APPLY", "SUCCESS", 100, "Applied and verified")
                operationId
            }
        }
    }

    suspend fun restoreAll(service: IRetr0UserService): Result<Int> = runCatching {
        val backups = local.backups()
        var restored = 0
        backups.forEach { backup ->
            val target = backup.targetPath
            if (!service.exists(target)) return@forEach
            if (service.restoreSkinBackup(backup.backupPath, target, backup.sha256) && service.sha256File(target).equals(backup.sha256, true)) {
                local.clearApplied(target)
                restored++
            }
        }
        restored
    }

    suspend fun revert(localSkinId: String, service: IRetr0UserService): Result<String> = runCatching {
        val skin = requireNotNull(local.skin(localSkinId)) { "Local skin not found" }
        val target = requireNotNull(skin.targetPath) { "Skin has no target" }
        locks.getOrPut(target) { Mutex() }.withLock {
            val operationId = UUID.randomUUID().toString()
            update(operationId, skin.id, "REVERT", "CREATED", 0, "Preparing restore")
            withContext(Dispatchers.IO) {
                if (service.isPackageRunning(skin.gamePackage)) service.forceStopPackage(skin.gamePackage)
                require(!service.isPackageRunning(skin.gamePackage)) { "RR3 is still running" }
                val backup = requireNotNull(local.backupForTarget(target)) { "No original backup exists" }
                require(service.restoreSkinBackup(backup.backupPath, target, backup.sha256)) { "Unable to restore original skin" }
                require(service.sha256File(target).equals(backup.sha256, true)) { "Restored target verification failed" }
                local.clearApplied(target)
                update(operationId, skin.id, "REVERT", "SUCCESS", 100, "Original restored and verified")
                operationId
            }
        }
    }

    suspend fun recover(service: IRetr0UserService) {
        local.pendingOperations().forEach { operation ->
            val skinId = operation.skinId ?: return@forEach
            val skin = local.skin(skinId) ?: return@forEach
            val target = skin.targetPath ?: return@forEach
            val targetHash = runCatching { service.sha256File(target) }.getOrDefault("")
            when {
                targetHash.equals(skin.binarySha256, true) -> {
                    val backup = local.backupForTarget(target)
                    local.saveApplied(AppliedSkinEntity(UUID.randomUUID().toString(), skin.id, target, backup?.id, System.currentTimeMillis()))
                    update(operation.id, skin.id, operation.kind, "SUCCESS", 100, "Recovered applied state")
                }
                local.backupForTarget(target)?.sha256?.equals(targetHash, true) == true -> {
                    local.clearApplied(target)
                    update(operation.id, skin.id, operation.kind, "ROLLED_BACK", 100, "Recovered original state")
                }
                else -> update(operation.id, skin.id, operation.kind, "ROLLBACK_FAILED", operation.progress, "Manual recovery required")
            }
        }
    }

    private fun resolveTarget(service: IRetr0UserService, skin: LocalSkinEntity, target: String): String {
        if (service.exists(target)) return target
        val packageRoot = "/storage/emulated/0/Android/data/${skin.gamePackage}"
        val vehiclesRoot = "$packageRoot/files/.depot/vehicles"
        val candidates = service.scanSkinTargets(packageRoot, vehiclesRoot, skin.sourceFilename, 250, 2_000)
            .mapNotNull { raw -> raw.split('\t', limit = 3).takeIf { it.size == 3 }?.getOrNull(2) }
            .filter { it.contains("/vehicles/${skin.carDirectory}/", ignoreCase = true) }
        require(candidates.size == 1) { if (candidates.isEmpty()) "RR3 target no longer exists and could not be rediscovered" else "Multiple RR3 targets match this skin" }
        return candidates.single()
    }

    private suspend fun createBackup(service: IRetr0UserService, skinId: String, target: String, originalHash: String): SkinBackupEntity {
        val backupFile = File(backupRoot, "${originalHash.lowercase()}.dds.z")
        require(service.createImmutableSkinBackup(target, backupFile.absolutePath, originalHash)) { "Unable to create immutable original backup" }
        val backup = SkinBackupEntity(UUID.randomUUID().toString(), skinId, target, backupFile.absolutePath, originalHash.lowercase(), System.currentTimeMillis())
        local.saveBackup(backup)
        return backup
    }

    private suspend fun update(id: String, skinId: String?, kind: String, state: String, progress: Int, message: String) {
        val now = System.currentTimeMillis()
        local.operation(SkinOperationEntity(id, skinId, kind, state, progress, message, now, now))
    }

    private companion object { val SHA256 = Regex("[0-9a-fA-F]{64}") }
}
