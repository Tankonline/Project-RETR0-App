package com.retr0.projectretr0.installer

/**
 * Pure decisions behind MainActivity's Shizuku user-service lifecycle, kept free of Android types so they
 * can be unit-tested. MainActivity owns the binder; this only answers "should we act now?".
 */
object ShizukuConnectionPolicy {
    /** Snapshot of everything the reconnect decision depends on. */
    data class State(
        val shizukuBinderAlive: Boolean,
        val permissionGranted: Boolean,
        val shizukuPreV11: Boolean,
        val serviceBound: Boolean,
        val bindInProgress: Boolean,
        val retryScheduled: Boolean,
    )

    /**
     * The status monitor reconnects only when Shizuku is up and authorized, nothing is bound or binding,
     * no retry is already pending, and the last automatic attempt is at least [intervalMs] old.
     */
    fun shouldAutoReconnect(state: State, nowMs: Long, lastAttemptMs: Long, intervalMs: Long): Boolean =
        state.shizukuBinderAlive && state.permissionGranted && !state.shizukuPreV11 &&
            !state.serviceBound && !state.bindInProgress && !state.retryScheduled &&
            nowMs - lastAttemptMs >= intervalMs

    /**
     * A death notification matters only for the binder currently in use. Helpers replaced on purpose die
     * after their successor connected; reacting to those would tear down a healthy connection.
     */
    fun isCurrentBinder(current: Any?, died: Any): Boolean = current != null && current === died
}
