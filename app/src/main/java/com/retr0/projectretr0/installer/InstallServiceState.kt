package com.retr0.projectretr0.installer

/** Process-wide flag: true while the background install service is running a task. */
object InstallServiceState {
    @Volatile
    var busy: Boolean = false
}
