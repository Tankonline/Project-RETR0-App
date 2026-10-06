package com.retr0.projectretr0.ui.skins

import com.retr0.projectretr0.data.skins.CommunityDownload
import com.retr0.projectretr0.data.skins.SkinDownloadManager
import com.retr0.projectretr0.data.skins.SkinReportReason
import com.retr0.projectretr0.data.skins.SkinRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SkinCommunityViewModel(
    private val repository: SkinRepository,
    private val downloads: SkinDownloadManager,
) {
    private val mutableState = MutableStateFlow(CommunityUiState())
    val state: StateFlow<CommunityUiState> = mutableState.asStateFlow()

    suspend fun load(query: String = "", sort: String = "newest", reset: Boolean = true) {
        if (mutableState.value.loading) return
        val offset = if (reset) 0 else mutableState.value.items.size
        mutableState.value = mutableState.value.copy(loading = true, error = null)
        runCatching { repository.listPage(query, sort, PAGE_SIZE, offset) }
            .onSuccess { page ->
                val mapped = page.items.map(repository::map)
                val items = if (reset) mapped else (mutableState.value.items + mapped).distinctBy { it.id }
                mutableState.value = mutableState.value.copy(items = items, loading = false, canLoadMore = mapped.size == PAGE_SIZE)
            }
            .onFailure { mutableState.value = mutableState.value.copy(loading = false, error = it.message ?: "Unable to load skins") }
    }

    suspend fun details(id: String): Result<CommunitySkin> = runCatching { repository.details(id) }

    suspend fun setLike(id: String, liked: Boolean): Result<Int?> = runCatching {
        val result = repository.setLike(id, liked)
        mutableState.value = mutableState.value.copy(items = mutableState.value.items.map {
            if (it.id == id) it.copy(likes = result.likes ?: (it.likes + if (liked) 1 else -1).coerceAtLeast(0)) else it
        })
        result.likes
    }

    suspend fun report(id: String, reason: SkinReportReason, details: String = ""): Result<Unit> = runCatching { repository.report(id, reason, details); Unit }

    /** Deletes only the current installation's community upload. */
    suspend fun deleteOwned(id: String): Result<Unit> = runCatching {
        repository.deleteOwned(id)
        mutableState.value = mutableState.value.copy(items = mutableState.value.items.filterNot { it.id == id })
    }

    suspend fun download(skin: CommunitySkin, onProgress: (Int) -> Unit = {}): Result<String> = runCatching {
        downloads.download(
            CommunityDownload(skin.id, skin.title, skin.creator, skin.vehicle, skin.carId, skin.slotKey, skin.format,
                requireNotNull(skin.sourceFilename) { "Missing source filename" }, "unknown",
                requireNotNull(skin.binaryUrl) { "Missing binary URL" }, skin.binarySizeBytes,
                requireNotNull(skin.binarySha256) { "Missing binary hash" }, skin.coverUrl, skin.screenshotUrls), onProgress,
        ).id
    }

    private companion object { const val PAGE_SIZE = 20 }
}

data class CommunityUiState(val items: List<CommunitySkin> = emptyList(), val loading: Boolean = false, val canLoadMore: Boolean = true, val error: String? = null)
