package com.example.projectretr0installer.data.skins.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "local_skins", indices = [Index("communityId", unique = true)])
data class LocalSkinEntity(
    @PrimaryKey val id: String,
    val communityId: String? = null,
    val title: String,
    val creator: String,
    val vehicle: String,
    val carDirectory: String,
    val slotKey: String,
    val textureFormat: String,
    val sourceFilename: String,
    val binaryPath: String,
    val binarySize: Long,
    val binarySha256: String,
    val targetPath: String?,
    val targetRelativePath: String? = null,
    val gamePackage: String,
    val importedAt: Long,
)

@Entity(
    tableName = "skin_images",
    foreignKeys = [ForeignKey(entity = LocalSkinEntity::class, parentColumns = ["id"], childColumns = ["skinId"], onDelete = ForeignKey.CASCADE)],
    indices = [Index("skinId"), Index(value = ["skinId", "position"], unique = true)],
)
data class SkinImageEntity(
    @PrimaryKey val id: String,
    val skinId: String,
    val role: String,
    val position: Int,
    val caption: String,
    val path: String,
    val mimeType: String,
    val width: Int,
    val height: Int,
    val sizeBytes: Long,
    val sha256: String,
)

@Entity(tableName = "skin_backups", indices = [Index("skinId"), Index("targetPath", unique = true)])
data class SkinBackupEntity(@PrimaryKey val id: String, val skinId: String, val targetPath: String, val backupPath: String, val sha256: String, val createdAt: Long)

@Entity(tableName = "applied_skins", indices = [Index("skinId"), Index("targetPath", unique = true)])
data class AppliedSkinEntity(@PrimaryKey val id: String, val skinId: String, val targetPath: String, val backupId: String?, val appliedAt: Long)

@Entity(tableName = "skin_operations", indices = [Index("skinId"), Index("createdAt")])
data class SkinOperationEntity(
    @PrimaryKey val id: String,
    val skinId: String?,
    val kind: String,
    val state: String,
    val progress: Int,
    val message: String?,
    val createdAt: Long,
    val updatedAt: Long,
)
