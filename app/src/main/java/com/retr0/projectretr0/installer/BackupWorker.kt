package com.retr0.projectretr0.installer

import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import androidx.core.content.edit
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.retr0.projectretr0.IRetr0UserService
import com.retr0.projectretr0.Retr0UserService
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.withTimeoutOrNull
import rikka.shizuku.Shizuku
import kotlin.time.Duration.Companion.seconds

/**
 * Background worker that performs automated snapshots of RR3 character data.
 */
class BackupWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    private companion object {
        private const val TAG = "RETR0_BACKUP_WORKER"
    }

    override suspend fun doWork(): Result {
        Log.i(TAG, "Starting scheduled backup task...")

        val prefs = applicationContext.getSharedPreferences("retr0_prefs", Context.MODE_PRIVATE)

        if (!Shizuku.pingBinder()) {
            Log.w(TAG, "Shizuku not running, skipping backup.")
            saveStatus(prefs, "Failed (Shizuku off)")
            return Result.retry()
        }

        val deferredService = CompletableDeferred<IRetr0UserService>()
        
        val connection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
                val service = IRetr0UserService.Stub.asInterface(binder)
                deferredService.complete(service)
            }
            override fun onServiceDisconnected(name: ComponentName?) {}
        }

        val args = Shizuku.UserServiceArgs(ComponentName(applicationContext.packageName, Retr0UserService::class.java.name))
            .daemon(false)
            .processNameSuffix("backup")
            // Own Shizuku key, so the app replacing its helper at launch never kills a running backup.
            .tag("retr0-backup")
            .debuggable(false)
            // Keep this independent from the foreground binding cache.
            .version(6)

        try {
            Shizuku.bindUserService(args, connection)
            
            // Wait up to 10 seconds for the privileged service to connect
            val service = withTimeoutOrNull(10.seconds) {
                deferredService.await()
            }

            if (service == null) {
                Log.e(TAG, "Failed to bind to privileged service within timeout")
                saveStatus(prefs, "Failed (Timeout)")
                return Result.retry()
            }

            // 1. Process Detection: Check if RR3 is running
            val isRowRunning = service.isPackageRunning("com.ea.games.r3_row")
            val isNaRunning = service.isPackageRunning("com.ea.games.r3_na")
            
            if (isRowRunning || isNaRunning) {
                Log.w(TAG, "Game is currently running. Postponing backup to avoid corruption.")
                saveStatus(prefs, "Postponed (Game running)")
                // Re-attempt in 15 minutes
                return Result.retry()
            }

            val installer = Retr0Installer(applicationContext, service)
            
            // Generate a timestamped name for the automatic backup
            val timestamp = java.text.SimpleDateFormat("yyyyMMdd_HHmm", java.util.Locale.ENGLISH).format(java.util.Date())
            val backupName = "AutoBackup_$timestamp"

            Log.i(TAG, "Creating scheduled snapshot: $backupName")
            val success = installer.createSnapshot(backupName)

            if (success) {
                // 2. Rotation Policy: Prune old auto-backups
                installer.pruneAutoSnapshots(maxCount = 5)
                
                val now = java.text.SimpleDateFormat("HH:mm", java.util.Locale.ENGLISH).format(java.util.Date())
                saveStatus(prefs, "Success ($now)")
            } else {
                saveStatus(prefs, "Failed (Copy error)")
            }

            // Cleanup: Unbind
            Shizuku.unbindUserService(args, connection, false)

            return if (success) {
                Log.i(TAG, "Scheduled backup successful.")
                Result.success()
            } else {
                Log.e(TAG, "Scheduled backup failed.")
                Result.failure()
            }

        } catch (e: Exception) {
            Log.e(TAG, "Error during scheduled backup", e)
            saveStatus(prefs, "Error")
            return Result.failure()
        }
    }

    private fun saveStatus(prefs: android.content.SharedPreferences, status: String) {
        prefs.edit { putString("last_backup_status", status) }
    }
}
