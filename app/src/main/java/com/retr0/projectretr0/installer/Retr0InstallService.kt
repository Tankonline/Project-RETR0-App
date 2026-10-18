package com.retr0.projectretr0.installer

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.net.Uri
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import android.os.SystemClock
import android.util.Log
import androidx.core.app.NotificationCompat
import com.retr0.projectretr0.IRetr0UserService
import com.retr0.projectretr0.MainActivity
import com.retr0.projectretr0.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

/**
 * A Foreground Service that handles the installation and save management process in the background.
 */
class Retr0InstallService : Service() {

    private companion object {
        private const val TAG = "RETR0_INSTALL_SVC"
        private const val MUSIC_LIBRARY_TAG = "RETR0_MUSIC_LIBRARY"
        private const val NOTIFICATION_ID = 1337
        private const val CHANNEL_ID = "retr0_install_channel"
        private const val WAKE_LOCK_TAG = "ProjectRETR0:InstallWakeLock"
        private const val NOTIFICATION_UPDATE_INTERVAL_MS = 500L
    }

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private var installer: Retr0Installer? = null
    
    private var wakeLock: PowerManager.WakeLock? = null
    private var isTaskRunning = false
    private var lastNotificationAt = 0L
    private var lastNotificationState = InstallState.IDLE
    private var pendingNotificationJob: Job? = null
    private var pendingNotificationProgress: InstallProgress? = null

    // Stable flow that MainActivity can always bind to
    private val _serviceProgress = MutableStateFlow(InstallProgress())
    val serviceProgress = _serviceProgress.asStateFlow()

    private val binder = LocalBinder()

    inner class LocalBinder : Binder() {
        fun getProgressFlow(): StateFlow<InstallProgress> = serviceProgress
    }

    override fun onBind(intent: Intent?): IBinder = binder

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        
        // Prepare WakeLock to prevent CPU sleep during long operations
        val powerManager = getSystemService(POWER_SERVICE) as PowerManager
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, WAKE_LOCK_TAG)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent?.action
        
        if (action?.startsWith("START_") == true) {
            if (isTaskRunning) {
                Log.w(TAG, "Task already running, ignoring: $action")
                return START_STICKY
            }
            
            val profileName = intent.getStringExtra("PROFILE_NAME")
            val uploaderName = intent.getStringExtra("UPLOADER_NAME")
            val saveId = intent.getStringExtra("SAVE_ID")
            val customName = intent.getStringExtra("CUSTOM_NAME")
            val trackFileName = intent.getStringExtra("TRACK_FILENAME")
            val targetTrack = intent.getStringExtra("TARGET_TRACK")
            val presetName = intent.getStringExtra("PRESET_NAME")
            val youtubeUrl = intent.getStringExtra("YOUTUBE_URL")
            val artworkUrl = intent.getStringExtra("ARTWORK_URL")
            val artworkUri = intent.getStringExtra("ARTWORK_URI")?.let(Uri::parse)
            val artistName = intent.getStringExtra("ARTIST_NAME")
            val shareHub = intent.getBooleanExtra("SHARE_HUB", false)
            
            val mLevel = intent.getIntExtra("MANUAL_LEVEL", -1)
            val mGold = intent.getLongExtra("MANUAL_GOLD", -1L)
            val mRS = intent.getLongExtra("MANUAL_RS", -1L)
            val mMS = intent.getLongExtra("MANUAL_MS", -1L)

            val binderProxy = intent.extras?.getBinder("USER_SERVICE_BINDER") 
            if (binderProxy != null) {
                val service = IRetr0UserService.Stub.asInterface(binderProxy)
                startTask(
                    service, 
                    taskType = action.substringAfter("START_"), 
                    uri = intent.data, 
                    profileName = profileName,
                    uploaderName = uploaderName,
                    saveId = saveId,
                    customName = customName,
                    trackFileName = trackFileName,
                    targetTrack = targetTrack,
                    presetName = presetName,
                    manualLevel = mLevel,
                    manualGold = mGold,
                    manualRS = mRS,
                    manualMS = mMS,
                    youtubeUrl = youtubeUrl,
                    shareHub = shareHub,
                    artworkUrl = artworkUrl,
                    artworkUri = artworkUri,
                    artistName = artistName,
                )
            } else {
                Log.e(TAG, "No User Service Binder provided")
                if (!isTaskRunning) stopSelf()
            }
        } else if (action == "RESTORE_NOTIFICATION") {
            // Re-issue the current notification if task is active
            val currentProgress = _serviceProgress.value
            if ((currentProgress.state != InstallState.IDLE) &&
            (currentProgress.state != InstallState.SUCCESS) &&
            (currentProgress.state != InstallState.FAILED)
        ) {
                updateNotification(currentProgress)
            }
        } else if (action == "STOP_SERVICE") {
            releaseWakeLock()
            stopForeground(STOP_FOREGROUND_REMOVE)
            stopSelf()
        } else if (action == "RESET_PROGRESS") {
            _serviceProgress.value = InstallProgress()
            installer?.reset()
        }
        
        return START_STICKY
    }

    private fun startTask(
        service: IRetr0UserService, 
        taskType: String, 
        uri: Uri? = null, 
        profileName: String? = null,
        uploaderName: String? = null,
        saveId: String? = null,
        customName: String? = null,
        trackFileName: String? = null,
        targetTrack: String? = null,
        presetName: String? = null,
        manualLevel: Int = -1,
        manualGold: Long = -1,
        manualRS: Long = -1,
        manualMS: Long = -1,
        youtubeUrl: String? = null,
        shareHub: Boolean = false,
        artworkUrl: String? = null,
        artworkUri: Uri? = null,
        artistName: String? = null,
    ) {
        isTaskRunning = true
        installer = Retr0Installer(applicationContext, service)
        
        // HEARTBEAT: Keep Shizuku Binder alive during heavy extraction
        val heartbeatJob = serviceScope.launch {
            while (true) {
                try {
                    service.ping()
                } catch (_: Exception) {
                    Log.w(TAG, "Shizuku heartbeat failed - connection may be unstable")
                }
                delay(5.seconds) // Ping every 5 seconds
            }
        }
        
        // Acquire WakeLock before starting heavy work
        wakeLock?.acquire(3 * 60 * 60 * 1000L) // 3 hour timeout safety
        
        // Initial Notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(
                NOTIFICATION_ID, 
                createNotification("Preparing...", 0),
                ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC,
            )
        } else {
            startForeground(NOTIFICATION_ID, createNotification("Preparing...", 0))
        }

        serviceScope.launch {
            // Forward installer progress to our stable service flow
            launch {
                installer?.progress?.collect { progress ->
                    _serviceProgress.value = progress
                    scheduleNotificationUpdate(progress)
                    
                    if ((progress.state == InstallState.SUCCESS) || (progress.state == InstallState.FAILED) || (progress.state == InstallState.CANCELLED)) {
                        releaseWakeLock()
                        isTaskRunning = false
                        heartbeatJob.cancel()
                        if (progress.state == InstallState.SUCCESS) {
                            val finalNotif = createNotification("Process Complete!", 100, ongoing = false)
                            val manager = getSystemService(NotificationManager::class.java)
                            manager.notify(NOTIFICATION_ID, finalNotif)
                        }
                    }
                }
            }

            // Run the requested task
            try {
                when (taskType) {
                    "INSTALL" -> installer?.installMod()
                    "SAFE_TEST" -> installer?.runSafeTest()
                    "REPAIR" -> installer?.runAssetRepair()
                    "CHARACTER" -> uri?.let { installer?.importCharacterData(it) }
                    "DOC" -> uri?.let { installer?.importSaveDirectory(it) }
                    "BACKUP" -> profileName?.let { installer?.createSnapshot(it) }
                    "RESTORE" -> profileName?.let { name ->
                        val hubPath = "/storage/emulated/0/Documents/ProjectRETR0/Backups"
                        val profilePath = "$hubPath/$name.dat"
                        
                        if (service.exists(profilePath)) {
                            val size = service.getFileSize(profilePath)
                            val time = service.getLastModified(profilePath)
                            val profile = SaveProfile(name, time, 1, size, File(profilePath))
                            
                            if (installer?.applyProfile(profile) == true) {
                                // Fix permissions dynamically
                                try {
                                    val rowPath = "/storage/emulated/0/Android/data/com.ea.games.r3_row/files/doc/character.2.dat"
                                    val naPath = "/storage/emulated/0/Android/data/com.ea.games.r3_na/files/doc/character.2.dat"
                                    
                                    val finalPath = if (service.exists(rowPath)) rowPath else naPath
                                    Runtime.getRuntime().exec(arrayOf("chmod", "666", finalPath)).waitFor()
                                } catch (_: Exception) {}
                            }
                        }
                    }
                    "UPLOAD" -> {
                        if ((profileName != null) && (uploaderName != null)) {
                            val hubPath = "/storage/emulated/0/Documents/ProjectRETR0/Backups"
                            val profilePath = File(hubPath, "$profileName.dat")
                            val profile = SaveProfile(profileName, service.getLastModified(profilePath.absolutePath), 1, service.getFileSize(profilePath.absolutePath), profilePath)
                            
                            installer?.uploadToCommunity(
                                profile = profile, 
                                uploaderName = uploaderName,
                                manualLevel = manualLevel,
                                manualGold = manualGold,
                                manualRS = manualRS,
                                manualMS = manualMS,
                            )
                        }
                    }
                    "DOWNLOAD_COMMUNITY" -> {
                        if ((saveId != null) && (customName != null)) {
                            val save = CommunitySave(saveId, customName, "", 0, "")
                            installer?.downloadCommunitySave(save)
                        }
                    }
                    "MUSIC_MOD" -> {
                        if ((uri != null) && (customName != null) && (targetTrack != null)) {
                            val meta = MusicMetadata(
                                customName,
                                artistName ?: uploaderName.orEmpty(),
                                artworkUrl,
                            )

                            // Save to library as well for consistency. Applying/uploading remains independent.
                            val librarySaved = installer?.saveToLibrary(
                                uri,
                                customName,
                                metadata = meta,
                                artworkUri = artworkUri,
                                artworkUrl = artworkUrl,
                            ) == true
                            Log.i(MUSIC_LIBRARY_TAG, "code=ML_SVC001 stage=music_mod_local_save result=$librarySaved task=MUSIC_MOD uploadRequested=$shareHub")
                            if (shareHub && uploaderName != null && !librarySaved) {
                                Log.w(MUSIC_LIBRARY_TAG, "code=ML_SVC002 stage=local_upload_divergence result=skipped task=MUSIC_MOD localSaved=false uploadSkipped=true")
                            }

                            val applySucceeded = installer?.applyMusicMod(uri, targetTrack, metadata = meta) == true
                            if (shareHub && uploaderName != null && librarySaved) {
                                installer?.uploadMusic(uri, uploaderName, customName, targetTrack, artworkUri, artworkUrl)
                            }
                            Log.i(MUSIC_LIBRARY_TAG, "code=ML_SVC003 stage=music_mod_outcome result=reported localSaved=$librarySaved gameApplied=$applySucceeded uploadAttempted=${shareHub && uploaderName != null && librarySaved}")
                        }
                    }
                    "MUSIC_RESTORE" -> {
                        trackFileName?.let { installer?.restoreMusic(it) }
                    }
                    "SAVE_TO_LIBRARY" -> {
                        if ((uri != null) && (customName != null)) {
                            installer?.saveToLibrary(
                                uri = uri,
                                customName = customName,
                                metadata = MusicMetadata(customName, artistName.orEmpty(), artworkUrl),
                                artworkUri = artworkUri,
                                artworkUrl = artworkUrl,
                            )
                        }
                    }
                    "APPLY_FROM_LIBRARY" -> {
                        if ((trackFileName != null) && (targetTrack != null)) {
                            installer?.applyFromLibrary(trackFileName, targetTrack)
                        }
                    }
                    "APPLY_FROM_FILE" -> {
                        if (uri != null && targetTrack != null) {
                            installer?.applyMusicMod(uri, targetTrack)
                        }
                    }
                    "DOWNLOAD_TO_LIBRARY" -> {
                        if (saveId != null && customName != null) {
                            installer?.downloadCommunityMusic(CommunityMusic(saveId, customName, "", 0, "", ""), toLibrary = true)
                        }
                    }
                    "UPLOAD_MUSIC" -> {
                        if ((uri != null) && (uploaderName != null) && (customName != null) && (targetTrack != null)) {
                            installer?.uploadMusic(uri, uploaderName, customName, targetTrack)
                        }
                    }
                    "UPLOAD_LIBRARY_MUSIC" -> {
                        if ((trackFileName != null) && (uploaderName != null) && (customName != null)) {
                            installer?.uploadMusicFromLibrary(trackFileName, uploaderName, customName)
                        }
                    }
                    "APPLY_AND_UPLOAD_MUSIC" -> {
                        if ((uri != null) && (uploaderName != null) && (customName != null) && (targetTrack != null)) {
                            // Metadata is independent from artwork availability.
                            val meta = MusicMetadata(customName, artistName ?: uploaderName, artworkUrl)
                            installer?.applyAndUploadMusic(
                                uri,
                                targetTrack,
                                uploaderName,
                                customName,
                                metadata = meta,
                                artworkUri = artworkUri,
                                artworkUrl = artworkUrl,
                            )
                        }
                    }
                    "DOWNLOAD_COMMUNITY_MUSIC" -> {
                        if ((saveId != null) && (targetTrack != null) && (customName != null)) {
                            val music = CommunityMusic(saveId, customName, "", 0, "", targetTrack)
                            installer?.downloadCommunityMusic(music)
                        }
                    }
                    "YOUTUBE_DOWNLOAD", "YOUTUBE_TO_LIBRARY" -> {
                        if (youtubeUrl != null) {
                            // Ensure engine is ready
                            Retr0Installer.initDownloader(applicationContext)
                            
                            val result = installer?.downloadYouTubeAudio(youtubeUrl)
                            if (result == null || !result.file.isFile || result.file.length() <= 0) {
                                val current = installer?.progress?.value
                                if (current?.state != InstallState.FAILED) {
                                    _serviceProgress.value = _serviceProgress.value.copy(
                                        state = InstallState.FAILED,
                                        errorMessage = "YT_E006 YouTube processing failed before an audio file was created.",
                                    )
                                }
                                Log.e(TAG, "RETR0_YT_SERVICE_FAILED task=$taskType result=${if (result == null) "null" else "invalid_file"} installerState=${current?.state}")
                            } else {
                                val mp3Uri = Uri.fromFile(result.file)
                                val finalTitle = customName ?: result.info.title
                                val finalArtworkUrl = artworkUrl ?: result.info.thumbnailUrl
                                val meta = MusicMetadata(
                                    finalTitle,
                                    artistName ?: uploaderName.orEmpty(),
                                    finalArtworkUrl,
                                )

                                // 1. Save to permanent library first. Applying/uploading remains independent.
                                val librarySaved = installer?.saveToLibrary(
                                    mp3Uri,
                                    finalTitle,
                                    metadata = meta,
                                    artworkUri = artworkUri,
                                    artworkUrl = finalArtworkUrl,
                                ) == true
                                Log.i(MUSIC_LIBRARY_TAG, "code=ML_SVC001 stage=youtube_local_save result=$librarySaved task=$taskType uploadRequested=$shareHub")
                                if (!librarySaved && shareHub && uploaderName != null && targetTrack != null) {
                                    Log.w(MUSIC_LIBRARY_TAG, "code=ML_SVC002 stage=local_upload_divergence result=skipped task=$taskType localSaved=false uploadSkipped=true")
                                }
                                
                                // 2. Apply to game slot independently, then report the local/upload outcomes.
                                var gameApplied = false
                                if (targetTrack != null) {
                                    gameApplied = installer?.applyMusicMod(mp3Uri, targetTrack, metadata = meta) == true
                                    if (shareHub && uploaderName != null && librarySaved) {
                                        installer?.uploadMusic(
                                            mp3Uri,
                                            uploaderName,
                                            finalTitle,
                                            targetTrack,
                                            artworkUri = artworkUri,
                                            artworkUrl = artworkUrl ?: result.info.thumbnailUrl,
                                        )
                                    }
                                }
                                Log.i(MUSIC_LIBRARY_TAG, "code=ML_SVC003 stage=youtube_outcome result=reported localSaved=$librarySaved gameApplied=$gameApplied uploadAttempted=${shareHub && uploaderName != null && targetTrack != null && librarySaved}")
                                result.file.delete()
                                Log.i(TAG, "RETR0_YT_SERVICE_SUCCEEDED task=$taskType bytes=${result.file.length()}")
                            }
                        }
                    }
                    "APPLY_GRAPHICS" -> {
                        presetName?.let { installer?.applyGraphicsPreset(it) }
                    }
                    "RESTORE_GRAPHICS" -> {
                        installer?.restoreOriginalGraphics()
                    }
                "REVERT_AUDIO" -> {
                    if (targetTrack != null) {
                        Log.i(TAG, "Starting revert task for: $targetTrack")
                        installer?.revertAudioMod(targetTrack)
                    }
                }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Task failed with exception", e)
                _serviceProgress.value = _serviceProgress.value.copy(state = InstallState.FAILED, errorMessage = e.message)
            } finally {
                isTaskRunning = false
                heartbeatJob.cancel()
                releaseWakeLock()
            }
        }
    }

    private fun releaseWakeLock() {
        if (wakeLock?.isHeld == true) {
            wakeLock?.release()
        }
    }

    private fun scheduleNotificationUpdate(progress: InstallProgress) {
        val now = SystemClock.elapsedRealtime()
        val stateChanged = progress.state != lastNotificationState
        val terminal = progress.state == InstallState.SUCCESS ||
            progress.state == InstallState.FAILED ||
            progress.state == InstallState.CANCELLED
        val elapsed = now - lastNotificationAt

        if (stateChanged || terminal || elapsed >= NOTIFICATION_UPDATE_INTERVAL_MS) {
            pendingNotificationJob?.cancel()
            pendingNotificationJob = null
            pendingNotificationProgress = null
            updateNotification(progress)
            return
        }

        pendingNotificationProgress = progress
        if (pendingNotificationJob?.isActive == true) return
        pendingNotificationJob = serviceScope.launch {
            delay((NOTIFICATION_UPDATE_INTERVAL_MS - elapsed).coerceAtLeast(1L).milliseconds)
            pendingNotificationProgress?.let(::updateNotification)
            pendingNotificationProgress = null
            pendingNotificationJob = null
        }
    }

    private fun updateNotification(progress: InstallProgress) {
        lastNotificationAt = SystemClock.elapsedRealtime()
        lastNotificationState = progress.state
        val percentageStr = String.format(java.util.Locale.ENGLISH, "%.2f%%", progress.fileProgress * 100)
        val displayedPart = (progress.completedFiles + 1).coerceAtMost(progress.totalFiles.coerceAtLeast(1))
        val partInfo = if (progress.totalFiles > 1) " (Part $displayedPart of ${progress.totalFiles})" else ""
        val etaInfo = if ((progress.eta.isNotBlank()) && (progress.state == InstallState.DOWNLOADING)) " - ${progress.eta} remaining" else ""
        
        val content = when (progress.state) {
            InstallState.DOWNLOADING -> "Downloading$partInfo: $percentageStr$etaInfo"
            InstallState.INSTALLING -> "Installing$partInfo: ${progress.currentFile.ifEmpty { "Files" }}"
            InstallState.EXTRACTING -> "Extracting$partInfo..."
            InstallState.SUCCESS -> "Process Complete!"
            InstallState.FAILED -> "Process Failed!"
            else -> progress.currentStep
        }
        
        // Use a high resolution for the progress bar (0-1000) for smoother animation
        val progressInt = (progress.fileProgress * 1000).toInt()
        val isIndeterminate = (progress.fileProgress <= 0f) && (progress.state != InstallState.IDLE)
        
        val notification = createNotification(content, progressInt, 1000, isIndeterminate)
        val manager = getSystemService(NotificationManager::class.java)
        manager.notify(NOTIFICATION_ID, notification)
    }

    private fun createNotification(
        content: String, 
        progress: Int, 
        maxProgress: Int = 100, 
        indeterminate: Boolean = false,
        ongoing: Boolean = true,
    ): Notification {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, 
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
        )
        
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Project RETR0 Installer")
            .setContentText(content)
            .setSmallIcon(R.drawable.ic_notification)
            .setOngoing(ongoing)
            .setOnlyAlertOnce(true)
            .setProgress(maxProgress, progress, indeterminate)
            .setContentIntent(pendingIntent)
            .setSilent(true)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_PROGRESS)
            .setForegroundServiceBehavior(NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE)
            
        if (ongoing) {
            val deleteIntent = Intent(this, Retr0InstallService::class.java).apply { action = "RESTORE_NOTIFICATION" }
            val deletePendingIntent = PendingIntent.getService(
                this, 1, deleteIntent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
            )
            builder.setDeleteIntent(deletePendingIntent)
        }
        
        return builder.build()
    }

    private fun createNotificationChannel() {
        val name = "Installer Channel"
        val descriptionText = "Shows progress for Project RETR0 installation"
        val importance = NotificationManager.IMPORTANCE_LOW
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
            setShowBadge(false)
            enableVibration(false)
            setSound(null, null)
        }
        val notificationManager: NotificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }

    override fun onTimeout(startId: Int, fgsType: Int) {
        Log.e(TAG, "Foreground data-sync time limit reached; stopping service")
        pendingNotificationJob?.cancel()
        releaseWakeLock()
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf(startId)
    }

    override fun onDestroy() {
        pendingNotificationJob?.cancel()
        serviceScope.cancel()
        releaseWakeLock()
        super.onDestroy()
    }
}
