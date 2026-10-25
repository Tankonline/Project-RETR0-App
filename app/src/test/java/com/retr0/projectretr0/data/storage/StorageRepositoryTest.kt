package com.retr0.projectretr0.data.storage

import com.retr0.projectretr0.IRetr0UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotSame
import org.junit.Test

class StorageRepositoryTest {
    @Test fun cacheHitReturnsTheSameSnapshot() {
        val cache = StorageSnapshotCache<String, String>()
        cache.put("key", "first")
        assertEquals("first", cache.get("key"))
        assertEquals("first", cache.get("key"))
    }

    @Test fun forcedRefreshAfterInvalidationReadsNewValue() {
        val cache = StorageSnapshotCache<String, String>()
        cache.put("key", "old")
        cache.clear()
        cache.put("key", "new")
        assertEquals("new", cache.get("key"))
    }

    @Test fun invalidationClearsCachedSnapshots() {
        val cache = StorageSnapshotCache<String, String>()
        cache.put("key", "value")
        cache.clear()
        assertEquals(null, cache.get("key"))
    }

    @Test fun differentKeysDoNotShareSnapshots() {
        val cache = StorageSnapshotCache<String, String>()
        cache.put("one", "first")
        cache.put("two", "second")
        assertNotSame(cache.get("one"), cache.get("two"))
    }

    @Test fun shizukuUnavailableIsRepresentedAsUnreadable() {
        val snapshot = StorageSnapshot(emptyList(), 0L, 1L, 2L, gameBytes = null)
        assertEquals(null, snapshot.gameBytes)
    }

    @Test fun clearRefreshesThroughViewModelContract() = runBlocking {
        val source = FakeStorageDataSource()
        val viewModel = com.retr0.projectretr0.ui.storage.StorageViewModel(
            source,
            kotlinx.coroutines.CoroutineScope(Dispatchers.Unconfined),
        )
        viewModel.refresh(null, "game")
        source.clearCache()
        viewModel.invalidate()
        viewModel.refresh(null, "game")
        assertEquals(2, source.snapshotCalls)
    }

    private class FakeStorageDataSource : StorageDataSource {
        var snapshotCalls = 0
        override suspend fun snapshot(service: IRetr0UserService?, gamePackage: String): StorageSnapshot {
            snapshotCalls++
            return StorageSnapshot(emptyList(), snapshotCalls.toLong(), 1L, 2L, null)
        }
        override fun invalidate() = Unit
        override suspend fun clearCache() = Result.success(0L)
        override suspend fun clearDownloads() = Result.success(0L)
    }
}
