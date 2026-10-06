package com.retr0.projectretr0.data.skins.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
abstract class SkinDao {
    @Insert(onConflict = OnConflictStrategy.ABORT) abstract suspend fun insertSkin(skin: LocalSkinEntity)
    @Insert(onConflict = OnConflictStrategy.ABORT) abstract suspend fun insertImages(images: List<SkinImageEntity>)
    @Insert(onConflict = OnConflictStrategy.REPLACE) abstract suspend fun upsertBackup(backup: SkinBackupEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE) abstract suspend fun upsertApplied(applied: AppliedSkinEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE) abstract suspend fun upsertOperation(operation: SkinOperationEntity)

    @Transaction
    open suspend fun insertImported(skin: LocalSkinEntity, images: List<SkinImageEntity>) {
        insertSkin(skin)
        if (images.isNotEmpty()) insertImages(images)
    }

    @Query("SELECT * FROM local_skins ORDER BY importedAt DESC") abstract fun observeSkins(): Flow<List<LocalSkinEntity>>
    @Query("SELECT * FROM local_skins ORDER BY importedAt DESC") abstract suspend fun skins(): List<LocalSkinEntity>
    @Query("SELECT * FROM local_skins WHERE id = :id") abstract suspend fun skin(id: String): LocalSkinEntity?
    @Query("UPDATE local_skins SET title = :title, creator = :creator, vehicle = :vehicle, carDirectory = :carDirectory, slotKey = :slotKey, textureFormat = :textureFormat WHERE id = :id") abstract suspend fun updateMetadata(id: String, title: String, creator: String, vehicle: String, carDirectory: String, slotKey: String, textureFormat: String)
    @Query("UPDATE local_skins SET targetPath = :targetPath, targetRelativePath = :targetRelativePath, gamePackage = :gamePackage WHERE id = :id") abstract suspend fun updateTarget(id: String, targetPath: String, targetRelativePath: String, gamePackage: String)
    @Query("SELECT * FROM local_skins WHERE communityId = :communityId") abstract suspend fun communitySkin(communityId: String): LocalSkinEntity?
    @Query("DELETE FROM skin_images WHERE skinId = :skinId") abstract suspend fun deleteImages(skinId: String)
    @Query("DELETE FROM local_skins WHERE id = :skinId") abstract suspend fun deleteSkinRecord(skinId: String)

    @Transaction
    open suspend fun replaceDownloaded(existingId: String?, skin: LocalSkinEntity, images: List<SkinImageEntity>) {
        existingId?.let {
            deleteImages(it)
            deleteSkinRecord(it)
        }
        insertImported(skin, images)
    }
    @Query("SELECT * FROM skin_images WHERE skinId = :skinId ORDER BY position") abstract suspend fun images(skinId: String): List<SkinImageEntity>
    @Query("SELECT * FROM skin_images WHERE skinId = :skinId ORDER BY position") abstract fun observeImages(skinId: String): Flow<List<SkinImageEntity>>
    @Query("SELECT * FROM skin_backups WHERE skinId = :skinId") abstract suspend fun backups(skinId: String): List<SkinBackupEntity>
    @Query("SELECT * FROM applied_skins WHERE skinId = :skinId") abstract suspend fun applied(skinId: String): List<AppliedSkinEntity>
    @Query("SELECT * FROM applied_skins WHERE targetPath = :targetPath LIMIT 1") abstract suspend fun appliedTarget(targetPath: String): AppliedSkinEntity?
    @Query("SELECT * FROM skin_backups WHERE targetPath = :targetPath LIMIT 1") abstract suspend fun backupForTarget(targetPath: String): SkinBackupEntity?
    @Query("SELECT * FROM skin_backups ORDER BY createdAt DESC") abstract fun observeBackups(): Flow<List<SkinBackupEntity>>
    @Query("SELECT * FROM skin_backups ORDER BY createdAt DESC") abstract suspend fun backups(): List<SkinBackupEntity>
    @Query("SELECT * FROM applied_skins ORDER BY appliedAt DESC") abstract fun observeApplied(): Flow<List<AppliedSkinEntity>>
    @Query("DELETE FROM applied_skins WHERE targetPath = :targetPath") abstract suspend fun deleteAppliedTarget(targetPath: String)
    @Query("SELECT * FROM skin_operations WHERE state NOT IN ('SUCCESS', 'ROLLED_BACK', 'FAILED_SAFE') ORDER BY createdAt") abstract suspend fun pendingOperations(): List<SkinOperationEntity>
    @Query("SELECT * FROM skin_operations ORDER BY createdAt DESC") abstract fun observeOperations(): Flow<List<SkinOperationEntity>>
    @Query("DELETE FROM local_skins WHERE id = :id") abstract suspend fun deleteSkin(id: String)
}
