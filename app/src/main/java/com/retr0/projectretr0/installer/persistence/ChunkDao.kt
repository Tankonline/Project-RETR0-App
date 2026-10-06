package com.retr0.projectretr0.installer.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ChunkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun markChunkDownloaded(chunk: ChunkEntity)

    @Query("SELECT chunkId FROM downloaded_chunks WHERE modVersion = :version")
    suspend fun getDownloadedChunksForVersion(version: String): List<String>

    @Query("DELETE FROM downloaded_chunks WHERE modVersion = :version")
    suspend fun clearChunksForVersion(version: String)

    @Query("DELETE FROM downloaded_chunks")
    suspend fun clearAllChunks()

    @Query("SELECT EXISTS(SELECT 1 FROM downloaded_chunks WHERE chunkId = :chunkId AND modVersion = :version AND archiveId = :archiveId LIMIT 1)")
    suspend fun isChunkDownloaded(chunkId: String, version: String, archiveId: String): Boolean

    @Query("DELETE FROM downloaded_chunks WHERE modVersion = :version AND archiveId = :archiveId")
    suspend fun clearChunksForArchive(version: String, archiveId: String)
}
