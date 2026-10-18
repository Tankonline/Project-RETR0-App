package com.retr0.projectretr0.data.carModels.local

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.json.JSONObject
import java.io.File
import java.util.UUID

/** App-private persistence for the car-model library, installed list, backup records and activity. */
class CarModelStore(val root: File) {
    private val stateFile = File(root, "state.json")
    private val _state = MutableStateFlow(load())
    val state: StateFlow<CarModelState> = _state

    val current: CarModelState get() = _state.value
    val stagingDir: File get() = File(root, "staging")
    fun libraryDir(id: String) = File(root, "library/$id")

    @Synchronized
    fun update(transform: (CarModelState) -> CarModelState): CarModelState {
        val next = transform(_state.value)
        root.mkdirs()
        val temp = File(root, "state.json.tmp")
        temp.writeText(next.toJson().toString())
        if (!temp.renameTo(stateFile)) {
            stateFile.delete()
            check(temp.renameTo(stateFile)) { "Unable to save car-model state" }
        }
        _state.value = next
        return next
    }

    fun saveBackup(backup: CarModelBackup) = update { state -> state.copy(backups = state.backups.filterNot { it.key == backup.key } + backup) }

    fun log(kind: CarModelActivityKind, title: String, detail: String, success: Boolean, now: Long = System.currentTimeMillis()) =
        update { state -> state.copy(activity = (listOf(CarModelActivity(UUID.randomUUID().toString(), kind, title, detail, now, success)) + state.activity).take(MAX_ACTIVITY)) }

    private fun load(): CarModelState = runCatching {
        val loaded = if (stateFile.isFile) CarModelState.fromJson(JSONObject(stateFile.readText())) else CarModelState()
        // A process death can leave a transaction in-flight. Keep the record visible and mark it for recovery;
        // the engine will require a verified backup before any subsequent install/revert.
        loaded.copy(operations = loaded.operations.map { operation ->
            if (operation.state in setOf(CarModelOperationState.PREPARING, CarModelOperationState.BACKING_UP, CarModelOperationState.INSTALLING, CarModelOperationState.REVERTING)) {
                operation.copy(state = CarModelOperationState.RECOVERING, updatedAt = System.currentTimeMillis())
            } else operation
        })
    }.getOrDefault(CarModelState())

    companion object {
        private const val MAX_ACTIVITY = 100
        @Volatile private var instance: CarModelStore? = null
        fun get(context: Context): CarModelStore = instance ?: synchronized(this) {
            instance ?: CarModelStore(File(context.applicationContext.filesDir, "car_models")).also { instance = it }
        }
    }
}
