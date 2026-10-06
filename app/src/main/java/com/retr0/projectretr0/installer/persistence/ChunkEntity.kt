package com.retr0.projectretr0.installer.persistence

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "downloaded_chunks")
data class ChunkEntity(
    @PrimaryKey val chunkId: String,
    val modVersion: String,
    val archiveId: String = "legacy",
    val timestamp: Long = System.currentTimeMillis(),
)
