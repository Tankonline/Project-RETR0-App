package com.retr0.projectretr0.ui.storage

import com.retr0.projectretr0.IRetr0UserService
import com.retr0.projectretr0.data.storage.StorageRepository
import com.retr0.projectretr0.data.storage.StorageSnapshot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface StorageUiState {
    data object Loading : StorageUiState
    /** [refreshing] keeps the last numbers on screen while a new scan runs. */
    data class Ready(val snapshot: StorageSnapshot, val refreshing: Boolean = false) : StorageUiState
}

/** Result banner after a clear action. */
data class StorageNotice(val text: String, val good: Boolean, val code: String? = null)

/**
 * Holds the Storage screen state. Scans run on IO inside [scope] (the screen's composition scope),
 * so leaving the screen cancels any scan in flight.
 */
class StorageViewModel(private val repository: StorageRepository, private val scope: CoroutineScope) {
    private val _state = MutableStateFlow<StorageUiState>(StorageUiState.Loading)
    val state: StateFlow<StorageUiState> = _state
    private val _busy = MutableStateFlow<String?>(null)
    val busy: StateFlow<String?> = _busy
    private var scan: Job? = null
    private var lastKey: String? = null
    private var invalidated = true

    fun refresh(service: IRetr0UserService?, gamePackage: String) {
        val key = "${System.identityHashCode(service)}:$gamePackage"
        if (!invalidated && lastKey == key && scan?.isActive != true && _state.value is StorageUiState.Ready) return
        if (scan?.isActive == true && lastKey == key) return // latest request is already authoritative
        scan?.cancel()
        lastKey = key
        invalidated = false
        (_state.value as? StorageUiState.Ready)?.let { _state.value = it.copy(refreshing = true) }
        scan = scope.launch {
            _state.value = StorageUiState.Ready(repository.snapshot(service, gamePackage))
        }
    }

    fun invalidate() {
        invalidated = true
        repository.invalidate()
    }

    fun clearCache(service: IRetr0UserService?, gamePackage: String, onDone: (StorageNotice) -> Unit) =
        clear("Clearing cache…", "cache", { repository.clearCache() }, service, gamePackage, onDone)

    fun clearDownloads(service: IRetr0UserService?, gamePackage: String, onDone: (StorageNotice) -> Unit) =
        clear("Clearing downloads…", "downloads", { repository.clearDownloads() }, service, gamePackage, onDone)

    private fun clear(label: String, what: String, action: suspend () -> Result<Long>, service: IRetr0UserService?, gamePackage: String, onDone: (StorageNotice) -> Unit) {
        if (_busy.value != null) return
        scope.launch {
            _busy.value = label
            try {
                val result = action()
                onDone(result.fold(
                    { StorageNotice("Cleared $what · ${com.retr0.projectretr0.ui.carmodels.formatBytes(it)} freed.", true) },
                    { StorageNotice(it.message ?: "Some files could not be cleared.", false, com.retr0.projectretr0.data.carModels.CarModelErrorCode.STORAGE_CLEAR_FAILED.code) },
                ))
            } finally {
                _busy.value = null
                invalidate()
                refresh(service, gamePackage)
            }
        }
    }
}
