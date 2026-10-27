package com.retr0.projectretr0

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.os.PowerManager
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.edit
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.work.*
import coil.compose.AsyncImage
import com.retr0.projectretr0.installer.*
import com.retr0.projectretr0.data.skins.MusicApiClient
import com.retr0.projectretr0.data.skins.SaveApiClient
import com.retr0.projectretr0.data.skins.SkinInstallationIdentity
import com.retr0.projectretr0.data.skins.SkinPFPStore
import com.retr0.projectretr0.ui.skins.PFPEditorFlow
import com.retr0.projectretr0.ui.skins.SkinAvatar
import com.retr0.projectretr0.ui.components.*
import com.retr0.projectretr0.ui.carmodels.CarModelsHubTab
import com.retr0.projectretr0.ui.carmodels.ModsHomeScreen
import com.retr0.projectretr0.ui.carmodels.CarModelsSection
import com.retr0.projectretr0.ui.skins.SkinModsSection
import com.retr0.projectretr0.ui.storage.StorageScreen
import com.retr0.projectretr0.ui.carmodels.ModsKind
import com.retr0.projectretr0.ui.skins.CarSkinsTab
import com.retr0.projectretr0.ui.skins.GamePackage
import com.retr0.projectretr0.ui.theme.*
import com.retr0.projectretr0.installer.persistence.DownloadDatabase
import com.retr0.projectretr0.installer.graphics.GraphicsPathValidator
import com.retr0.projectretr0.installer.graphics.HdrLevel
import com.retr0.projectretr0.installer.graphics.HdrScan
import com.retr0.projectretr0.installer.graphics.HdrState
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit
import java.io.File
import com.retr0.projectretr0.ui.shimmer
import com.retr0.projectretr0.ui.tiltEffect
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import rikka.shizuku.Shizuku
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {

    private companion object {
        private const val TAG = "RETR0_INSTALLER"
        private const val PACKAGE_LOG_TAG = "RETR0_PACKAGE_MIGRATION"
        private const val EXPECTED_PACKAGE = "com.retr0.projectretr0"
        private const val CODE_PACKAGE_VERIFIED = "PKG_MIGRATION_I002"
        private const val CODE_PACKAGE_MISMATCH = "PKG_MIGRATION_E001"
        private const val SHIZUKU_PERMISSION_REQUEST_CODE = 1000

        /** Shizuku key for the app's own helper. The scheduled backup uses its own tag, so neither can kill the other. */
        const val MAIN_HELPER_TAG = "retr0-main"

        /**
         * Process-wide: true once this app process has replaced any existing helper with a fresh one.
         * Activity recreation (rotation, reopening while a job runs) must not kill a helper in use.
         */
        @Volatile var freshHelperRequested = false

        /** Screens whose back arrow system Back follows (Music slots handles its own back inside folders). */
        val BACKABLE_MODES = setOf(AppMode.MUSIC, AppMode.GRAPHICS, AppMode.SAVES, AppMode.DIAGNOSTICS, AppMode.EXPERIMENTAL_GRAPHICS, AppMode.STORAGE)

        /** The Android quality presets shown in the Graphics screen (label to preset id). */
        val ANDROID_PRESETS = listOf("Low" to "AndroidLow", "Medium" to "AndroidMid", "Medium 2" to "AndroidMid2", "High" to "AndroidHigh", "Xtra High" to "AndroidXtraHigh", "Ultra Wide" to "AndroidUltraWide")
    }

    enum class AppMode {
        SELECTION,
        DASHBOARD,
        COMMUNITY,
        SKIN_MODS,
        AI_CHAT,
        EXPERIMENTAL_GRAPHICS,
        MUSIC_SLOTS,
        STORAGE,
        // Dashboard hubs promoted to their own screens (opened from the Home cards / System list).
        MUSIC,
        GRAPHICS,
        SAVES,
        DIAGNOSTICS,
    }

    private var currentMode by mutableStateOf(AppMode.SELECTION)

    /** Where each screen's back arrow returns to, recorded when the screen is opened with [openMode]. */
    private val modeReturn = mutableStateMapOf<AppMode, AppMode>()

    /** Opens [target] and remembers the current screen so Back returns to it. */
    private fun openMode(target: AppMode) {
        if (target == currentMode) return
        modeReturn[target] = currentMode
        Log.i(TAG, "code=NAV_I001 stage=open from=${currentMode.name} to=${target.name}")
        currentMode = target
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(NavStateCodec.KEY_MODE, currentMode.name)
        outState.putString(NavStateCodec.KEY_RETURN, NavStateCodec.encodeReturns(modeReturn))
        Log.i(TAG, "code=NAV_I003 stage=save mode=${currentMode.name} returns=${modeReturn.size}")
    }

    /** Reopens the screen that was showing when Android closed or rebuilt the app. A deep link handled later still wins. */
    private fun restoreNavState(saved: Bundle?) {
        if (saved == null) return
        val rawMode = saved.getString(NavStateCodec.KEY_MODE)
        val mode = NavStateCodec.decodeMode(rawMode)
        if (mode == null) {
            Log.w(TAG, "code=NAV_W001 stage=restore result=fallback saved=${rawMode ?: "none"} mode=${currentMode.name}")
            return
        }
        modeReturn.clear()
        modeReturn.putAll(NavStateCodec.decodeReturns(saved.getString(NavStateCodec.KEY_RETURN)))
        currentMode = mode
        Log.i(TAG, "code=NAV_I004 stage=restore mode=${mode.name} returns=${modeReturn.size}")
    }

    /** Back from the current screen: to the screen that opened it, else [fallback]. */
    private fun goBack(fallback: AppMode = AppMode.DASHBOARD) {
        val to = modeReturn.remove(currentMode) ?: fallback
        Log.i(TAG, "code=NAV_I002 stage=back from=${currentMode.name} to=${to.name}")
        currentMode = to
    }
    private var service: IRetr0UserService? = null
    private var isServiceBound by mutableStateOf(value = false)
    private var isShizukuRunning by mutableStateOf(value = false)
    private var isPermissionGranted by mutableStateOf(value = false)
    private var shizukuStatus by mutableStateOf("Checking Shizuku...")
    private var availableSpace by mutableStateOf("Checking...")

    private var isSmartBackupEnabled by mutableStateOf(value = false)
    private var lastBackupStatus by mutableStateOf("Never")
    
    private var pendingDeepLinkSkinId by mutableStateOf<String?>(null)
    private var pendingDeepLinkSaveId by mutableStateOf<String?>(null)
    private var pendingDeepLinkMusicId by mutableStateOf<String?>(null)
    private var communityTab by mutableIntStateOf(0)
    private var modsKind by mutableStateOf(ModsKind.SKINS)
    // One-shot: which section Mods opens on next time (Storage → "Review backups"). Reset once consumed.
    private var modsOpenOnBackups by mutableStateOf(false)
    
    private var installService: Retr0InstallService.LocalBinder? by mutableStateOf(null)
    private var isInstallServiceBound by mutableStateOf(value = false)
    
    private var saveProfiles by mutableStateOf<List<SaveProfile>>(emptyList())
    private var communitySaves by mutableStateOf<List<CommunitySave>>(emptyList())
    private var communityMusic by mutableStateOf<List<CommunityMusic>>(emptyList())
    private var communitySortType by mutableStateOf("newest")
    private var communityRefreshToken by mutableIntStateOf(0)
    // Car-model browsing is network-backed and must refresh independently of the
    // Shizuku-dependent saves/music flows.
    private var carModelsRefreshToken by mutableIntStateOf(0)
    private var libraryMusic by mutableStateOf<List<LibraryMusic>>(emptyList())
    private var allMusicSlots by mutableStateOf<List<String>>(emptyList())
    private var backedUpSlots by mutableStateOf<Set<String>>(emptySet())
    
    private var availablePresets by mutableStateOf<List<String>>(emptyList())
    /** Graphics Hub "Full HDR" switch: off = HDR fix level B, on = level C. Saved in retr0_prefs. */
    /** HDR level used for applies and restores (the Hub's A/B/C switches). Synced to what the scan finds. */
    private var graphicsHdrLevel by mutableStateOf(HdrLevel.B)
    /** Last scan of the game files; null = not scanned yet or unavailable. */
    private var graphicsHdrScan by mutableStateOf<HdrScan?>(null)
    private var graphicsHdrScanning by mutableStateOf(false)
    
    private var isSystemStatusLoading by mutableStateOf(value = true)
    private var installationId by mutableStateOf("Loading...")
    private var showResetIdentityDialog by mutableStateOf(false)
    private val identity by lazy { SkinInstallationIdentity(this) }
    private var customPfp by mutableStateOf<SkinPFPStore.LocalAvatar?>(null)
    private var showPfpEditor by mutableStateOf(false)

    private var isCommunityLoading by mutableStateOf(false)
    private var isLibraryLoading by mutableStateOf(false)
    private var isSavesLoading by mutableStateOf(false)
    private var isSlotsLoading by mutableStateOf(false)
    private var isInstallServiceConnecting by mutableStateOf(true)
    private var isClearingTempFiles by mutableStateOf(false)

    // Save Upload State
    private var showSaveUploadDialog by mutableStateOf(false)
    private var profileToUpload by mutableStateOf<SaveProfile?>(null)

    // Library Music Upload State
    private var showLibraryMusicUploadDialog by mutableStateOf(false)
    private var libraryMusicToUpload by mutableStateOf<LibraryMusic?>(null)
    private var profileToRename by mutableStateOf<SaveProfile?>(null)
    private var renameProfileName by mutableStateOf("")
    private var renameProfileError by mutableStateOf<String?>(null)

    // Local Music Import Workflow State
    private var showMusicImportDialog by mutableStateOf(false)
    private var musicImportLocalUri by mutableStateOf<Uri?>(null)
    private var musicImportArtworkUri by mutableStateOf<Uri?>(null)
    private var musicImportSearchQuery by mutableStateOf("")
    private var musicImportResults by mutableStateOf<List<MusicMetadata>>(emptyList())
    private var isMusicSearching by mutableStateOf(false)
    private var selectedMetadata by mutableStateOf<MusicMetadata?>(null)

    // Slot Swapping State
    private var showLibraryPickerForSlot by mutableStateOf<String?>(null) // legacy library picker state
    private var showSlotPickerForLibraryTrack by mutableStateOf<LibraryMusic?>(null) // legacy library picker state
    private var pendingAudioFileTarget by mutableStateOf<String?>(null)
    
    private var aiFixResponse by mutableStateOf<String?>(null)
    private var isAiThinking by mutableStateOf(false)
    
    // AI Chat History
    private val chatMessages = mutableStateListOf<ChatMessage>()
    private var currentChatInput by mutableStateOf("")
    private var isAiChatThinking by mutableStateOf(false)
    
    private var shizukuMonitorJob: Job? = null
    private var storageStatusJob: Job? = null
    private var userServiceBindJob: Job? = null
    private var userServiceBinder: IBinder? = null
    private var bindInProgress = false
    private var activityStarted = false
    /** How many times an outdated Shizuku helper was killed and re-bound in this activity. */
    private var staleHelperRestarts = 0

    private val installServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            installService = binder as? Retr0InstallService.LocalBinder
            isInstallServiceBound = true
            isInstallServiceConnecting = false
            Log.i(TAG, "Bound to background install service")
            refreshSaveHub()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            installService = null
            isInstallServiceBound = false
            isInstallServiceConnecting = true
        }
    }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            Log.i(TAG, "code=SHIZUKU_USVC_CONNECTED stage=callback name=$name binder=${binder != null}")
            // The callback can arrive during the launch/splash transition, before
            // onStart() has marked the activity visible. It is still a valid bind;
            // rejecting it here leaves Shizuku connected but the app unbound until
            // the five-second timeout expires.
            val activityDestroyed = isDestroyed
            if (isFinishing || activityDestroyed) {
                Log.w(TAG, "code=SHIZUKU_USVC_STALE_CALLBACK stage=callback_ignored finishing=$isFinishing destroyed=$activityDestroyed")
                return
            }
            if (binder == null || !binder.isBinderAlive) {
                handleUserServiceLost("Shizuku returned an invalid RETR0 binder")
                scheduleUserServiceBindRetry()
                return
            }
            // Shizuku can keep an older helper process alive after an update (e.g. Android Studio
            // "Apply Changes" or a cached service record). Ask the helper its API version and, if it
            // is outdated, kill it and bind a fresh one built from the installed APK.
            val helperApi = runCatching { IRetr0UserService.Stub.asInterface(binder).getGraphicsApiVersion() }.getOrDefault(0)
            if (helperApi < GraphicsPathValidator.SERVICE_API) {
                if (staleHelperRestarts < 1) {
                    staleHelperRestarts++
                    Log.w(GraphicsPathValidator.LOG_TAG, "code=GFX_W100 stage=helper_version_check helperApi=$helperApi required=${GraphicsPathValidator.SERVICE_API} action=restart_helper")
                    bindInProgress = false
                    userServiceBindJob?.cancel()
                    val connection: ServiceConnection = this
                    runCatching { Shizuku.unbindUserService(userServiceArgs, connection, true) }
                        .onFailure { Log.w(TAG, "Stale RETR0 helper unbind failed", it) }
                    lifecycleScope.launch {
                        delay(1_000)
                        bindRetr0Service()
                    }
                    return
                }
                // A fresh helper process still reports old code: the installed APK's compiled code is
                // stale. Android Studio's optimized deploy does this; Shizuku documents that "Always
                // install with package manager" must be enabled for user services to get new code.
                Log.e(GraphicsPathValidator.LOG_TAG, "code=GFX_E100 stage=helper_version_check helperApi=$helperApi required=${GraphicsPathValidator.SERVICE_API} action=restart_failed cause=stale_installed_code hint=android_studio_always_install_with_package_manager")
                Toast.makeText(this@MainActivity, "The RETR0 Shizuku helper is still old code. In Android Studio enable \"Always install with package manager\" (Run > Edit Configurations > app) and run again.", Toast.LENGTH_LONG).show()
            } else {
                Log.i(GraphicsPathValidator.LOG_TAG, "code=GFX_I100 stage=helper_version_check helperApi=$helperApi required=${GraphicsPathValidator.SERVICE_API} restarts=$staleHelperRestarts result=ok")
            }
            userServiceBinder = binder
            runCatching {
                binder.linkToDeath({ runOnUiThread { handleUserServiceLost("RETR0 user service binder died") } }, 0)
            }.onFailure { Log.w(TAG, "Could not register RETR0 binder death recipient", it) }
            service = IRetr0UserService.Stub.asInterface(binder)
            isServiceBound = true
            bindInProgress = false
            userServiceBindJob?.cancel()
            shizukuStatus = "RETR0 privileged service connected."

            val intent = Intent(this@MainActivity, Retr0InstallService::class.java)
            bindService(intent, installServiceConnection, BIND_AUTO_CREATE)
            refreshSaveHub()
            refreshCommunityHub()
            refreshMusicLibrary()
            refreshMusicSlots()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.w(TAG, "RETR0 user service disconnected name=$name")
            handleUserServiceLost("RETR0 privileged service disconnected")
        }

        override fun onBindingDied(name: ComponentName?) {
            Log.w(TAG, "RETR0 user service binding died name=$name")
            handleUserServiceLost("RETR0 privileged service binding died")
            scheduleUserServiceBindRetry()
        }
    }

    private val userServiceArgs by lazy {
        // This component is the UserService entry point, not an Android service
        // declaration. Shizuku starts it in its own authorized process.
        Shizuku.UserServiceArgs(ComponentName(packageName, Retr0UserService::class.java.name))
            .daemon(false)
            .debuggable(BuildConfig.DEBUG)
            .processNameSuffix("retr0")
            .tag(MAIN_HELPER_TAG)
            // Increment when the UserService implementation or its Application setup changes.
            // Shizuku uses this to invalidate a previously cached service process.
            .version(7)
    }

    /** Untagged args used by builds before the helper had a tag; only used to remove that old helper. */
    private val legacyUserServiceArgs by lazy {
        Shizuku.UserServiceArgs(ComponentName(packageName, Retr0UserService::class.java.name))
            .processNameSuffix("retr0")
    }

    /** "Refresh privileged services": kill the current helper and bind a new one. */
    private fun restartPrivilegedHelper() {
        if (InstallServiceState.busy) {
            Toast.makeText(this, "Wait for the current task to finish first.", Toast.LENGTH_SHORT).show()
            return
        }
        Log.i(GraphicsPathValidator.LOG_TAG, "code=GFX_I102 stage=manual_helper_restart")
        bindInProgress = false
        userServiceBindJob?.cancel()
        runCatching { Shizuku.unbindUserService(userServiceArgs, serviceConnection, true) }
        runCatching { Shizuku.unbindUserService(legacyUserServiceArgs, null, true) }
        handleUserServiceLost("Restarting RETR0 privileged service...")
        staleHelperRestarts = 0
        freshHelperRequested = true
        lifecycleScope.launch {
            delay(1_000)
            bindRetr0Service()
        }
    }

    /**
     * Once per launch: kill any helper left over from an earlier run (including the old
     * untagged one) so this session always talks to a freshly started helper.
     */
    private fun replaceHelperOncePerProcess() {
        if (freshHelperRequested) return
        freshHelperRequested = true
        val legacy = runCatching { Shizuku.unbindUserService(legacyUserServiceArgs, null, true) }
        val main = runCatching { Shizuku.unbindUserService(userServiceArgs, null, true) }
        Log.i(
            GraphicsPathValidator.LOG_TAG,
            "code=GFX_I101 stage=fresh_helper action=remove_existing legacyRemoved=${legacy.isSuccess} mainRemoved=${main.isSuccess}" +
                (legacy.exceptionOrNull()?.let { " legacyError=${it.javaClass.simpleName}" } ?: "") +
                (main.exceptionOrNull()?.let { " mainError=${it.javaClass.simpleName}" } ?: ""),
        )
    }

    private val binderReceivedListener = Shizuku.OnBinderReceivedListener {
        Log.i(TAG, "Shizuku binder received")
        runOnUiThread {
            updateShizukuStatus()
            if (canAttemptServiceBind() && !isServiceBound) bindRetr0Service()
        }
    }

    private val binderDeadListener = Shizuku.OnBinderDeadListener {
        Log.w(TAG, "Shizuku binder died")
        runOnUiThread {
            isShizukuRunning = false
            isPermissionGranted = false
            handleUserServiceLost("Shizuku binder disconnected")
        }
    }

    private val permissionResultListener = Shizuku.OnRequestPermissionResultListener { requestCode, grantResult ->
        if (requestCode == SHIZUKU_PERMISSION_REQUEST_CODE) {
            Log.i(TAG, "code=SHIZUKU_PERMISSION_RESULT requestCode=$requestCode grantResult=$grantResult")
            updateShizukuStatus()
            if (grantResult == PackageManager.PERMISSION_GRANTED) {
                isPermissionGranted = true
                Toast.makeText(this, "Permission granted. Connecting to RETR0...", Toast.LENGTH_SHORT).show()
                bindRetr0Service(forceRebind = true)
            } else {
                isPermissionGranted = false
                Toast.makeText(this, "Shizuku permission denied. Open Shizuku and authorize Project RETR0.", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        // A real launch (not a rotation/recreate) gets a fresh Shizuku helper, even when the app
        // process survived because the background service kept it alive. Never mid-task.
        if (savedInstanceState == null && !InstallServiceState.busy) freshHelperRequested = false
        Log.i(GraphicsPathValidator.LOG_TAG, "code=GFX_I103 stage=activity_create freshLaunch=${savedInstanceState == null} taskRunning=${InstallServiceState.busy} willReplaceHelper=${!freshHelperRequested}")
        val runtimePackage = packageName
        if (runtimePackage == EXPECTED_PACKAGE) {
            Log.i(PACKAGE_LOG_TAG, "code=$CODE_PACKAGE_VERIFIED stage=package_verified package=$runtimePackage")
        } else {
            Log.e(PACKAGE_LOG_TAG, "code=$CODE_PACKAGE_MISMATCH stage=package_mismatch expected=$EXPECTED_PACKAGE actual=$runtimePackage")
        }
        Log.i(TAG, "MainActivity.onCreate")
        restoreNavState(savedInstanceState)

        Shizuku.addBinderReceivedListenerSticky(binderReceivedListener)
        Shizuku.addBinderDeadListener(binderDeadListener)
        Shizuku.addRequestPermissionResultListener(permissionResultListener)
        startShizukuMonitor()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
        ) {
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
                if (!granted) {
                    Toast.makeText(this, "Enable notifications to see live installation progress.", Toast.LENGTH_LONG).show()
                }
            }.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        lifecycleScope.launch {
            // Pre-fetch the shared RR3 installation manifest for faster content installation.
            Retr0Installer(this@MainActivity).preFetchManifest()
            isSystemStatusLoading = true
            updateShizukuStatus()
            updateStorageStatus()
            withContext(Dispatchers.IO) {
                runCatching {
                    installationId = identity.installationId
                    customPfp = SkinPFPStore.current(this@MainActivity, installationId)
                }.onFailure { error ->
                    Log.w(TAG, "Community identity initialization failed", error)
                }

                val prefs = getSharedPreferences("retr0_prefs", MODE_PRIVATE)
                isSmartBackupEnabled = prefs.getBoolean("smart_backup", false)
                // Older builds stored a "Full HDR" on/off switch: on = level C, off = level B.
                graphicsHdrLevel = prefs.getString("graphics_hdr_level", null)?.let { v -> HdrLevel.entries.firstOrNull { it.name == v } }
                    ?: if (prefs.getBoolean("graphics_full_hdr", false)) HdrLevel.C else HdrLevel.B
                lastBackupStatus = prefs.getString("last_backup_status", "Never") ?: "Never"
                
                          }
            if (canAttemptServiceBind()) {
                Log.i(TAG, "Auto-binding service...")
                bindRetr0Service()
            }
            // Battery optimization exemption is optional and must never block startup.
            // Battery optimization exemption is optional and must never block startup.
            runCatching { requestIgnoreBatteryOptimizations() }
                .onFailure { error -> Log.w(TAG, "Battery optimization request skipped", error) }
            
            // Always bind to the background install service to catch any ongoing tasks.
            val serviceIntent = Intent(this@MainActivity, Retr0InstallService::class.java)
            runCatching {
                bindService(serviceIntent, installServiceConnection, BIND_AUTO_CREATE)
            }.onFailure { error ->
                Log.w(TAG, "Background install service is unavailable", error)
            }
        }

        handleDeepLink(intent)

        setContent {
            ProjectRETR0InstallerTheme {
                MainScreen()
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleDeepLink(intent)
    }

    private fun handleDeepLink(intent: Intent?) {
        val data = intent?.data ?: return
        Log.i(TAG, "Handling deep link: $data")
        if (data.scheme == "https" && data.host == "save-files.armaandeep08-ap.workers.dev") {
            val segments = data.pathSegments
            if (segments.size >= 3 && segments[0] == "v1") {
                val type = segments[1]
                val id = segments[2]
                Log.i(TAG, "Extracted deep link: type=$type, id=$id")
                when (type) {
                    "skins" -> { pendingDeepLinkSkinId = id; communityTab = 2 }
                    "saves" -> { pendingDeepLinkSaveId = id; communityTab = 0 }
                    "music" -> { pendingDeepLinkMusicId = id; communityTab = 1 }
                }
                currentMode = AppMode.COMMUNITY
            }
        }
    }

    override fun onStart() {
        super.onStart()
        activityStarted = true
        updateShizukuStatus()
    }

    override fun onStop() {
        activityStarted = false
        super.onStop()
        shizukuMonitorJob?.cancel()
        shizukuMonitorJob = null
    }

    override fun onDestroy() {
        activityStarted = false
        shizukuMonitorJob?.cancel()
        userServiceBindJob?.cancel()
        storageStatusJob?.cancel()
        runCatching { Shizuku.removeBinderReceivedListener(binderReceivedListener) }
        runCatching { Shizuku.removeBinderDeadListener(binderDeadListener) }
        runCatching { Shizuku.removeRequestPermissionResultListener(permissionResultListener) }
        if (isServiceBound || bindInProgress) {
            runCatching { Shizuku.unbindUserService(userServiceArgs, serviceConnection, false) }
        }
        if (isInstallServiceBound) {
            runCatching { unbindService(installServiceConnection) }
        }
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        startShizukuMonitor()
    }

    private fun updateStorageStatus() {
        storageStatusJob?.cancel()
        storageStatusJob = lifecycleScope.launch {
            isSystemStatusLoading = true
            val bytes = withContext(Dispatchers.IO) {
                StorageUtils.getAvailableSpace(externalCacheDir ?: cacheDir)
            }
            availableSpace = StorageUtils.formatSize(bytes)
            isSystemStatusLoading = false
        }
    }
    
    private fun refreshSaveHub() {
        val currentService = service ?: return
        isSavesLoading = true
        lifecycleScope.launch {
            val installer = Retr0Installer(this@MainActivity, currentService)
            saveProfiles = installer.getSaveProfiles()
            isSavesLoading = false
        }
    }

    private fun toggleSmartBackup(enabled: Boolean) {
        isSmartBackupEnabled = enabled
        val prefs = getSharedPreferences("retr0_prefs", MODE_PRIVATE)
        prefs.edit { putBoolean("smart_backup", enabled) }

        val workManager = WorkManager.getInstance(this)
        if (enabled) {
            val request = PeriodicWorkRequestBuilder<BackupWorker>(4, TimeUnit.HOURS)
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                        .setRequiresBatteryNotLow(true)
                        .build(),
                )
                .addTag("smart_backup")
                .build()
            
            workManager.enqueueUniquePeriodicWork(
                "smart_backup",
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )
            Toast.makeText(this, "Smart Backup scheduled (every 4h)", Toast.LENGTH_SHORT).show()
        } else {
            workManager.cancelUniqueWork("smart_backup")
            Toast.makeText(this, "Smart Backup disabled", Toast.LENGTH_SHORT).show()
        }
    }

    private fun refreshCommunityHub() {
        communityRefreshToken++
        // Car models are fetched directly from the Community Hub and do not require
        // the Shizuku installer service or the saves/music loading gate.
        if (communityTab == 3) {
            carModelsRefreshToken++
            return
        }
        if (communityTab >= 2) return
        val currentService = service ?: return
        if (isCommunityLoading) return
        isCommunityLoading = true
        lifecycleScope.launch {
            try {
                val installer = Retr0Installer(this@MainActivity, currentService)
                if (communityTab == 0) communitySaves = installer.fetchCommunitySaves(communitySortType)
                else communityMusic = installer.fetchCommunityMusic(communitySortType)
            } finally {
                isCommunityLoading = false
            }
        }
    }

    private fun refreshMusicLibrary() {
        val currentService = service ?: return
        isLibraryLoading = true
        lifecycleScope.launch {
            val installer = Retr0Installer(this@MainActivity, currentService)
            libraryMusic = installer.getLibraryMusic()
            availablePresets = installer.getAvailablePresets()
            isLibraryLoading = false
        }
    }

    private fun refreshMusicSlots() {
        val currentService = service ?: return
        isSlotsLoading = true
        lifecycleScope.launch {
            val installer = Retr0Installer(this@MainActivity, currentService)
            allMusicSlots = installer.getAllMusicSlots()
            backedUpSlots = installer.getBackedUpSlots()
            isSlotsLoading = false
        }
    }

    @Composable
    fun MainScreen() {
        // Collect from the service if bound, otherwise fallback to a remembered state to prevent flickering
        val installProgress by (installService?.getProgressFlow() ?: remember { MutableStateFlow(InstallProgress()) }).collectAsState()
        var skinsFullScreen by remember { mutableStateOf(false) }

        if (showMusicImportDialog) {
            MusicImportDialog()
        }

        if (showSaveUploadDialog) {
            SaveUploadDialog()
        }

        if (showLibraryMusicUploadDialog) {
            LibraryMusicUploadDialog()
        }

        if (profileToRename != null) {
            RenameSaveDialog()
        }

        if (showLibraryPickerForSlot != null) {
            LibraryPickerForSlotDialog(showLibraryPickerForSlot!!)
        }

        if (showSlotPickerForLibraryTrack != null) {
            SlotPickerForLibraryDialog(showSlotPickerForLibraryTrack!!)
        }
        
        LaunchedEffect(installProgress.state) {
            if (installProgress.state == InstallState.SUCCESS) {
                refreshSaveHub()
                refreshCommunityHub()
                refreshMusicLibrary()
                refreshMusicSlots()
            }
        }

        if (showResetIdentityDialog) {
            ResetIdentityDialog()
        }

        if (showPfpEditor) {
            PFPEditorFlow(
                onDismiss = { showPfpEditor = false },
                onSaved = { localAvatar ->
                    customPfp = localAvatar
                    showPfpEditor = false
                    Toast.makeText(this@MainActivity, "Profile picture updated", Toast.LENGTH_SHORT).show()
                    lifecycleScope.launch {
                        val uploaded = withContext(Dispatchers.IO) {
                            SaveApiClient(this@MainActivity).uploadAvatar(localAvatar.file)
                        }
                        if (uploaded) {
                            refreshCommunityHub()
                        } else {
                            Toast.makeText(this@MainActivity, "Saved locally; Community Hub sync failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            )
        }

        Scaffold(
            contentWindowInsets = WindowInsets.safeDrawing,
            bottomBar = {
                if (currentMode != AppMode.EXPERIMENTAL_GRAPHICS && currentMode != AppMode.SELECTION && !skinsFullScreen) {
                    RetroBottomBar(
                        selectedIndex = when (currentMode) {
                            // Home and the screens opened from it.
                            AppMode.DASHBOARD, AppMode.MUSIC, AppMode.MUSIC_SLOTS, AppMode.GRAPHICS, AppMode.SAVES, AppMode.DIAGNOSTICS -> 0
                            AppMode.COMMUNITY -> 1
                            AppMode.SKIN_MODS -> 2
                            // Storage opens from Home (status strip) or from Mods; highlight whichever opened it.
                            AppMode.STORAGE -> if (modeReturn[AppMode.STORAGE] == AppMode.SKIN_MODS) 2 else 0
                            AppMode.AI_CHAT -> 3
                            // The bar is hidden on these.
                            AppMode.SELECTION, AppMode.EXPERIMENTAL_GRAPHICS -> 0
                        },
                        items = listOf(
                            RetroNavItem("Home", Icons.Filled.Home) { currentMode = AppMode.DASHBOARD },
                            RetroNavItem("Hub", Icons.Filled.Public) { currentMode = AppMode.COMMUNITY },
                            RetroNavItem("Mods", Icons.Filled.Tune) { currentMode = AppMode.SKIN_MODS },
                            RetroNavItem("AI", Icons.Filled.SmartToy) { currentMode = AppMode.AI_CHAT },
                        )
                    )
                }
            }
        ) { innerPadding ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .consumeWindowInsets(innerPadding),
                color = MaterialTheme.colorScheme.background
            ) {
                val tasksIdle = installProgress.state == InstallState.IDLE || installProgress.state == InstallState.SUCCESS || installProgress.state == InstallState.FAILED
                // System back on screens opened from Home goes back instead of closing the app.
                BackHandler(enabled = currentMode in BACKABLE_MODES) {
                    goBack(if (currentMode == AppMode.STORAGE) AppMode.SKIN_MODS else AppMode.DASHBOARD)
                }
                when (currentMode) {
                    AppMode.SELECTION -> GameSelectionScreen()
                    AppMode.DASHBOARD -> RR3Dashboard(installProgress)
                    AppMode.COMMUNITY -> CommunityGalleryScreen(onFullScreenChange = { skinsFullScreen = it })
                    AppMode.SKIN_MODS -> {
                        val openBackups = modsOpenOnBackups
                        // The sections are read once when the screen is first composed, so the flag can be reset right away.
                        LaunchedEffect(Unit) { modsOpenOnBackups = false }
                        ModsHomeScreen(
                            kind = modsKind, onKind = { modsKind = it }, isServiceConnected = isServiceBound, gamePackage = GamePackage.ROW, storageText = availableSpace, service = service,
                            onBrowseCommunity = { currentMode = AppMode.COMMUNITY }, onStorage = { openMode(AppMode.STORAGE) }, onConnect = ::requestShizukuPermission, onFullScreenChange = { skinsFullScreen = it },
                            initialSkinSection = if (openBackups) SkinModsSection.BACKUPS else SkinModsSection.OVERVIEW,
                            initialCarSection = if (openBackups) CarModelsSection.BACKUPS else CarModelsSection.OVERVIEW,
                        )
                    }
                    AppMode.STORAGE -> StorageScreen(
                        service = service, isServiceConnected = isServiceBound, gamePackage = GamePackage.ROW,
                        operationsIdle = !isClearingTempFiles && (installProgress.state == InstallState.IDLE || installProgress.state == InstallState.SUCCESS || installProgress.state == InstallState.FAILED),
                        onBack = { goBack(AppMode.SKIN_MODS) },
                        onReviewBackups = { modsOpenOnBackups = true; currentMode = AppMode.SKIN_MODS },
                        onStorageChanged = ::updateStorageStatus,
                        onConnect = ::requestShizukuPermission,
                    )
                    AppMode.AI_CHAT -> AiChatScreen()
                    AppMode.EXPERIMENTAL_GRAPHICS -> ExperimentalGraphicsScreen(
                        (installProgress.state == InstallState.IDLE || installProgress.state == InstallState.SUCCESS || installProgress.state == InstallState.FAILED)
                    )
                    AppMode.MUSIC_SLOTS -> MusicSlotsScreen()
                    AppMode.MUSIC -> MusicHubScreen(installProgress)
                    AppMode.GRAPHICS -> GraphicsHubScreen(installProgress, tasksIdle)
                    AppMode.SAVES -> SaveHubScreen(installProgress, tasksIdle)
                    AppMode.DIAGNOSTICS -> DiagnosticsScreen(installProgress, tasksIdle && !isInstallServiceConnecting)
                }
            }
        }
    }

    @Composable
    fun GameSelectionScreen() {
        Column(
            Modifier.fillMaxSize().background(RetroBg).padding(horizontal = 18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(42.dp))
            // --- logo lockup ---
            Image(
                painter = painterResource(R.drawable.project_retr0_game_art),
                contentDescription = "Project RETR0 logo",
                modifier = Modifier
                    .size(88.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .border(1.dp, RetroBorder, RoundedCornerShape(18.dp)),
                contentScale = ContentScale.Crop,
            )
            Spacer(modifier = Modifier.height(14.dp))
            Text("PROJECT RETR0", style = MaterialTheme.typography.headlineMedium, color = RetroText)
            Spacer(modifier = Modifier.height(4.dp))
            Text("Select a game to start modifying", style = MaterialTheme.typography.bodyMedium, color = RetroTextMuted)

            Spacer(modifier = Modifier.height(28.dp))
            GameCard(
                title = "Real Racing 3",
                subtitle = "Ultimate Modding Suite",
                installed = true,
                banner = painterResource(R.drawable.rr3_banner), // keep the existing asset
                onOpen = { currentMode = AppMode.DASHBOARD },
            )
            Spacer(modifier = Modifier.height(12.dp))
            ComingSoonCard()

            Spacer(modifier = Modifier.weight(1f))
            // --- version status pill ---
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.background(RetroSurface, RoundedCornerShape(20.dp))
                    .border(1.dp, RetroBorder, RoundedCornerShape(20.dp)).padding(horizontal = 12.dp, vertical = 6.dp)) {
                Box(Modifier.size(7.dp).background(RetroSuccess, CircleShape))
                Spacer(Modifier.width(7.dp))
                Text("v1.0.1 · Stable", style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 0.sp), color = RetroTextMuted)
            }
            Spacer(modifier = Modifier.height(26.dp).navigationBarsPadding())
        }
    }

    @Composable
    fun GameCard(title: String, subtitle: String, installed: Boolean, banner: Painter, onOpen: () -> Unit) {
        Column(
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(18.dp))
                .background(RetroSurface)
                .border(1.dp, RetroBorder, RoundedCornerShape(18.dp))
                .tiltEffect()
                .clickable(onClick = onOpen)
        ) {
            Box(Modifier.fillMaxWidth().height(140.dp)) {
                Image(banner, null, modifier = Modifier.matchParentSize(), contentScale = ContentScale.Crop)
                // scrim so the title stays legible over any art
                Box(modifier = Modifier.matchParentSize().background(
                    Brush.verticalGradient(listOf(Color.Transparent, RetroBg.copy(alpha = 0.9f)))))
                if (installed) {
                    Row(modifier = Modifier.align(Alignment.TopEnd).padding(10.dp)
                            .background(RetroSuccess.copy(alpha = 0.16f), RoundedCornerShape(20.dp))
                            .padding(horizontal = 10.dp, vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(Modifier.size(7.dp).background(RetroSuccess, CircleShape)); Spacer(Modifier.width(6.dp))
                        Text("Installed", color = RetroSuccess, style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 0.sp))
                    }
                }
                Column(modifier = Modifier.align(Alignment.BottomStart).padding(14.dp)) {
                    Text(title, style = MaterialTheme.typography.titleLarge, color = Color.White)
                    Text(subtitle, style = MaterialTheme.typography.bodyMedium, color = RetroTextSoft)
                }
            }
            Row(Modifier.fillMaxWidth().padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                Text("Open modding dashboard", Modifier.weight(1f), color = RetroTextDim, style = MaterialTheme.typography.bodyMedium)
                Row(Modifier.clip(RoundedCornerShape(20.dp)).background(RetroAccent).padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    Text("Open", color = Color.White, style = MaterialTheme.typography.bodyMedium)
                    Spacer(Modifier.width(4.dp)); Icon(Icons.AutoMirrored.Filled.ArrowForward, null, tint = Color.White, modifier = Modifier.size(16.dp))
                }
            }
        }
    }

    @Composable
    fun ComingSoonCard() {
        Column(
            Modifier.fillMaxWidth().clip(RoundedCornerShape(18.dp))
                .border(1.dp, RetroBorder, RoundedCornerShape(18.dp)).padding(vertical = 26.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(Icons.Filled.Add, null, tint = RetroTextMuted, modifier = Modifier.size(22.dp))
            Spacer(Modifier.height(6.dp))
            Text("More games coming soon", color = RetroTextMuted, style = MaterialTheme.typography.bodyMedium)
        }
    }

    @Composable
    fun AiChatScreen() {
        val chatScrollState = rememberScrollState()

        // Auto-scroll to bottom when a new message is added
        LaunchedEffect(chatMessages.size) {
            if (chatMessages.isNotEmpty()) {
                try {
                    yield() // Ensure layout has processed the new item
                    chatScrollState.animateScrollTo(chatScrollState.maxValue)
                } catch (e: Exception) {
                    Log.e(TAG, "Chat scroll failed", e)
                }
            }
        }
        
        Column(
            Modifier.fillMaxSize().background(RetroBg).padding(horizontal = 18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text("AI Assistant", style = MaterialTheme.typography.titleLarge, color = RetroText)
                    Text("RR3 modding helper", style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 0.sp), color = RetroTextMuted)
                }
                RetroIconBox(Icons.Filled.History) { /* openHistory() */ }
                Spacer(Modifier.width(8.dp))
                RetroIconBox(Icons.Filled.Add) { chatMessages.clear() }
            }
            
            // Chat messages or Suggested prompts
            Column(
                modifier = Modifier.weight(1f).verticalScroll(chatScrollState).padding(bottom = 16.dp)
            ) {
                if (chatMessages.isEmpty()) {
                    Column(Modifier.fillMaxWidth().padding(vertical = 30.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(Modifier.size(64.dp).background(RetroAccentTint, RoundedCornerShape(20.dp)).border(1.dp, RetroAccent.copy(alpha = 0.3f), RoundedCornerShape(20.dp)), Alignment.Center) {
                            Icon(Icons.Filled.SmartToy, null, tint = RetroAccent, modifier = Modifier.size(34.dp))
                        }
                        Spacer(Modifier.height(16.dp))
                        Text("Ask anything about RR3 modding", style = MaterialTheme.typography.titleMedium, color = RetroText)
                        Text("Swaps, graphics tiers, crash fixes, saves.", style = MaterialTheme.typography.bodyMedium, color = RetroTextMuted)
                    }
                    SectionHeader("Suggested", Icons.Filled.AutoAwesome)
                    val suggestions = listOf(
                        Icons.Filled.MusicNote to "How do I swap the garage music?",
                        Icons.Filled.Warning   to "RR3 crashes on launch — how do I fix it?",
                        Icons.Filled.Tune      to "What does the Xtra High tier change?",
                        Icons.Filled.Save      to "How do I import a modded save safely?")
                    suggestions.forEach { (icon, q) ->
                        Row(Modifier.fillMaxWidth().clip(RoundedCornerShape(13.dp)).background(RetroSurface).border(1.dp, RetroBorder, RoundedCornerShape(13.dp))
                                .clickable { 
                                    currentChatInput = q
                                    sendChatMessage()
                                }.padding(13.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(icon, null, tint = RetroAccent); Spacer(Modifier.width(12.dp))
                            Text(q, Modifier.weight(1f), color = RetroChipText, style = MaterialTheme.typography.bodyMedium)
                            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null, tint = RetroTextMuted)
                        }
                        Spacer(Modifier.height(9.dp))
                    }
                } else {
                    chatMessages.forEach { msg ->
                        val user = msg.isUser
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = if (user) Arrangement.End else Arrangement.Start) {
                            Text(msg.content, color = if (user) Color.White else RetroText, style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.widthIn(max = 280.dp)
                                    .background(if (user) RetroAccent else RetroSurface, RoundedCornerShape(14.dp))
                                    .then(if (!user) Modifier.border(1.dp, RetroBorder, RoundedCornerShape(14.dp)) else Modifier)
                                    .padding(horizontal = 14.dp, vertical = 10.dp))
                        }
                        Spacer(Modifier.height(8.dp))
                    }
                    
                    if (isAiChatThinking) {
                        Box(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp).align(Alignment.CenterStart), color = RetroAccent)
                        }
                    }
                }
            }
            
            // Composer
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 16.dp)) {
                Row(Modifier.weight(1f).background(RetroSurface, RoundedCornerShape(24.dp)).border(1.dp, RetroBorder, RoundedCornerShape(24.dp)).padding(horizontal = 16.dp, vertical = 12.dp)) {
                    BasicTextField(value = currentChatInput, onValueChange = { currentChatInput = it }, singleLine = true,
                        textStyle = MaterialTheme.typography.bodyMedium.copy(color = RetroText), modifier = Modifier.weight(1f),
                        decorationBox = { inner -> if (currentChatInput.isEmpty()) Text("Ask a question…", color = RetroTextMuted, style = MaterialTheme.typography.bodyMedium); inner() })
                }
                Spacer(Modifier.width(9.dp))
                Box(Modifier.size(46.dp).background(RetroAccent, CircleShape).clickable { sendChatMessage() }, Alignment.Center) {
                    Icon(Icons.Filled.ArrowUpward, null, tint = Color.White, modifier = Modifier.size(20.dp))
                }
            }
        }
    }

    private fun sendChatMessage() {
        if (currentChatInput.isNotBlank() && !isAiChatThinking) {
            val userMsg = ChatMessage(currentChatInput, true)
            chatMessages.add(userMsg)
            currentChatInput = ""
            
            lifecycleScope.launch {
                isAiChatThinking = true
                val response = Retr0Installer(this@MainActivity, service).chatWithAi(chatMessages.toList())
                chatMessages.add(ChatMessage(response ?: "Connection error.", false))
                isAiChatThinking = false
            }
        }
    }

    @Composable
    fun RR3Dashboard(installProgress: InstallProgress) {
        val isUiIdle = installProgress.state == InstallState.IDLE || installProgress.state == InstallState.SUCCESS || installProgress.state == InstallState.FAILED
        // The Graphics card shows the applied preset and HDR level, so scan when Home opens and after each task.
        LaunchedEffect(isUiIdle, service) { if (isUiIdle) refreshGraphicsHdrScan() }

        Column(
            Modifier.fillMaxSize().background(RetroBg)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { currentMode = AppMode.SELECTION }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = RetroAccent)
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text("Dashboard", style = MaterialTheme.typography.headlineMedium, color = RetroText)
                    Text("Real Racing 3 modding suite", style = MaterialTheme.typography.labelSmall, color = RetroTextMuted)
                }
            }

            // Main actions (and the privileged-service controls) are hidden while a task runs.
            val showButtons = isUiIdle && !isInstallServiceConnecting

            DashboardStatusStrip(showButtons)

            AnimatedVisibility(visible = installProgress.state != InstallState.IDLE) {
                InstallProgressSection(installProgress)
            }

            if (isInstallServiceConnecting && installProgress.state == InstallState.IDLE) {
                Box(Modifier.fillMaxWidth().padding(vertical = 20.dp), Alignment.Center) {
                    CircularProgressIndicator(color = RetroAccent)
                }
            }

            AnimatedVisibility(visible = showButtons) {
                Column(verticalArrangement = Arrangement.spacedBy(9.dp)) {
                    PrimaryButton("Launch Real Racing 3", icon = Icons.Filled.PlayArrow) { launchGame() }
                    SecondaryButton("Update Project RETR0", Modifier.fillMaxWidth(), icon = Icons.Filled.Download) {
                        startInstallServiceAction("START_INSTALL")
                    }
                }
            }

            // Feature hubs
            Column {
                SectionHeader("Hubs", Icons.Filled.Dashboard)
                Column(verticalArrangement = Arrangement.spacedBy(9.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(9.dp), modifier = Modifier.height(IntrinsicSize.Max)) {
                        // Mods has no card: it's already in the bottom bar.
                        RetroHubCard(
                            title = "Diagnostics", icon = Icons.Filled.Troubleshoot,
                            status = "Checks, logs and game repair",
                            modifier = Modifier.weight(1f).fillMaxHeight(),
                        ) { openMode(AppMode.DIAGNOSTICS) }
                        RetroHubCard(
                            title = "Music", icon = Icons.Filled.MusicNote,
                            status = when (val n = libraryMusic.size) { 0 -> "Swap soundtracks"; 1 -> "1 track in library"; else -> "$n tracks in library" },
                            modifier = Modifier.weight(1f).fillMaxHeight(),
                        ) { openMode(AppMode.MUSIC) }
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(9.dp), modifier = Modifier.height(IntrinsicSize.Max)) {
                        val (gfxStatus, gfxColor) = graphicsCardStatus()
                        RetroHubCard(
                            title = "Graphics", icon = Icons.Filled.Tune, status = gfxStatus, statusColor = gfxColor,
                            modifier = Modifier.weight(1f).fillMaxHeight(),
                        ) { openMode(AppMode.GRAPHICS) }
                        RetroHubCard(
                            title = "Saves", icon = Icons.Filled.Save,
                            status = "${saveProfiles.size} snapshot${if (saveProfiles.size == 1) "" else "s"} · Auto ${if (isSmartBackupEnabled) "on" else "off"}",
                            statusColor = if (isSmartBackupEnabled) RetroSuccess else RetroTextMuted,
                            modifier = Modifier.weight(1f).fillMaxHeight(),
                        ) { openMode(AppMode.SAVES) }
                    }
                }
            }

            // Community & Support
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                SectionHeader("Community & support", Icons.Filled.Favorite)
                Row(horizontalArrangement = Arrangement.spacedBy(9.dp)) {
                    AccentTintButton("Discord", Icons.Filled.Forum, RetroDiscordTint, RetroDiscord.copy(alpha = 0.4f), RetroDiscord, Modifier.weight(1f)) {
                        startActivity(Intent(Intent.ACTION_VIEW, getString(R.string.discord_invite_url).toUri()))
                    }
                    AccentTintButton("Subscribe", Icons.Filled.Star, RetroGoldTint, RetroGold.copy(alpha = 0.45f), RetroGold, Modifier.weight(1f)) {
                        startActivity(Intent(Intent.ACTION_VIEW, "https://www.youtube.com/@Retrospect69".toUri()))
                    }
                }
            }

            IdentityFooter()
            Spacer(Modifier.height(12.dp))
        }
    }

    /** Home status strip: Shizuku state, helper/HDR chips, tappable free-space figure (opens Storage) and the helper controls. */
    @Composable
    private fun DashboardStatusStrip(showControls: Boolean) {
        RetroCard {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Row(
                    Modifier.weight(1f).clickable { if (!isShizukuRunning || !isPermissionGranted) requestShizukuPermission() },
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(Modifier.size(9.dp).background(if (isShizukuRunning) RetroSuccess else RetroDanger, CircleShape))
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(if (isShizukuRunning) "Shizuku running" else "Shizuku not running", style = MaterialTheme.typography.bodyLarge, color = RetroText)
                        Text(
                            when {
                                !isShizukuRunning -> "Shizuku unavailable"
                                !isPermissionGranted -> "Tap to grant permission"
                                else -> "Permission granted"
                            },
                            style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 0.sp),
                            color = RetroTextMuted,
                        )
                    }
                }
                Column(
                    Modifier.clip(RoundedCornerShape(10.dp)).clickable { openMode(AppMode.STORAGE) }.padding(horizontal = 6.dp, vertical = 2.dp),
                    horizontalAlignment = Alignment.End,
                ) {
                    Text(availableSpace, style = MaterialTheme.typography.bodyLarge, color = RetroText)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("free · Storage", style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 0.sp), color = RetroAccent)
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, "Open Storage", tint = RetroAccent, modifier = Modifier.size(14.dp))
                    }
                }
            }
            Spacer(Modifier.height(10.dp))
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                StatusChip(if (isServiceBound) "RETR0 connected" else "RETR0 not connected", if (isServiceBound) RetroSuccess else RetroTextMuted)
                val scan = graphicsHdrScan
                when (val level = scan?.state?.level) {
                    null -> if (scan != null) StatusChip("No HDR fix", RetroWarning)
                    else -> StatusChip("HDR ${level.name}", if (scan.mixed) RetroWarning else RetroSuccess)
                }
                Spacer(Modifier.weight(1f))
                if (showControls && isServiceBound) {
                    Row(
                        Modifier.clip(RoundedCornerShape(20.dp)).clickable { restartPrivilegedHelper() }.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(Icons.Filled.Refresh, null, tint = RetroAccent, modifier = Modifier.size(14.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("Refresh", style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 0.sp), color = RetroAccent)
                    }
                }
            }
            if (showControls && !isServiceBound) {
                Spacer(Modifier.height(10.dp))
                AccentTintButton("Enable privileged services", Icons.Filled.FlashOn, RetroAccentTint, RetroAccent.copy(alpha = 0.4f), RetroAccent, Modifier.fillMaxWidth()) {
                    val binderAvailable = runCatching { Shizuku.pingBinder() }.getOrDefault(false)
                    val permissionGranted = runCatching {
                        Shizuku.checkSelfPermission() == PackageManager.PERMISSION_GRANTED
                    }.getOrDefault(false)
                    Log.i(TAG, "code=SHIZUKU_ENABLE_CLICK stage=button binderAvailable=$binderAvailable permissionGranted=$permissionGranted serviceBound=$isServiceBound bindInProgress=$bindInProgress")
                    when {
                        !binderAvailable -> {
                            updateShizukuStatus()
                            Toast.makeText(this@MainActivity, "Shizuku binder is unavailable. Open Shizuku and start its service.", Toast.LENGTH_LONG).show()
                        }
                        !permissionGranted -> requestShizukuPermission()
                        else -> {
                            isShizukuRunning = true
                            isPermissionGranted = true
                            bindRetr0Service(forceRebind = true)
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun StatusChip(text: String, color: Color) {
        Row(
            Modifier.background(color.copy(alpha = 0.12f), RoundedCornerShape(20.dp)).padding(horizontal = 8.dp, vertical = 3.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(Modifier.size(6.dp).background(color, CircleShape))
            Spacer(Modifier.width(5.dp))
            Text(text, style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 0.sp), color = color, maxLines = 1)
        }
    }

    /** Status line + colour for the Home Graphics card, from the last scan of the game files. */
    private fun graphicsCardStatus(): Pair<String, Color> {
        val scan = graphicsHdrScan ?: return (if (graphicsHdrScanning) "Checking..." else "Connect to read") to RetroTextMuted
        val preset = scan.activePreset?.let { formatDeviceName(it) } ?: "Device default"
        return when {
            scan.state.level == null -> "$preset · no HDR fix" to RetroWarning
            scan.mixed -> "$preset · mixed HDR" to RetroWarning
            else -> "$preset · HDR ${scan.state.name}" to RetroSuccess
        }
    }

    /** Starts the install service with [action] ("START_INSTALL", "START_SAFE_TEST"), as the Dashboard buttons always have. */
    private fun startInstallServiceAction(action: String) {
        service?.let { binder ->
            val intent = Intent(this@MainActivity, Retr0InstallService::class.java).apply {
                this.action = action
                val bundle = Bundle()
                bundle.putBinder("USER_SERVICE_BINDER", binder.asBinder())
                putExtras(bundle)
            }
            startForegroundService(intent)
            bindService(intent, installServiceConnection, BIND_AUTO_CREATE)
        } ?: run {
            Toast.makeText(this@MainActivity, "Connect Privileged Services first.", Toast.LENGTH_SHORT).show()
        }
    }

    /** Home footer: installation identity with copy and Reset. */
    @Composable
    private fun IdentityFooter() {
        RetroCard {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box {
                    SkinAvatar(avatarUrl = null, name = "Me", size = 32.dp, localFile = customPfp?.file, localAvatarRevision = customPfp?.revision)
                    Box(
                        Modifier
                            .align(Alignment.BottomEnd)
                            .size(16.dp)
                            .background(RetroAccent, CircleShape)
                            .clickable { showPfpEditor = true },
                        Alignment.Center,
                    ) { Icon(Icons.Filled.Edit, "Change profile picture", tint = Color.White, modifier = Modifier.size(10.dp)) }
                }
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Installation identity", style = MaterialTheme.typography.labelSmall, color = RetroTextMuted)
                        Spacer(Modifier.width(6.dp))
                        Text(if (isServiceBound) "Secured" else "Local", color = if (isServiceBound) RetroSuccess else RetroTextDim, style = MaterialTheme.typography.labelSmall)
                    }
                    Text(
                        installationId,
                        color = RetroText,
                        style = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(Modifier.width(8.dp))
                Icon(
                    Icons.Filled.ContentCopy,
                    contentDescription = "Copy ID",
                    tint = RetroAccent,
                    modifier = Modifier.size(18.dp).clickable {
                        val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = ClipData.newPlainText("Installation ID", installationId)
                        clipboard.setPrimaryClip(clip)
                        Toast.makeText(this@MainActivity, "ID copied to clipboard", Toast.LENGTH_SHORT).show()
                    }
                )
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Text(
                    "Reset identity",
                    color = RetroDanger,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.clickable { showResetIdentityDialog = true }.padding(4.dp)
                )
            }
        }
    }

    /** Shared scrolling layout for the screens opened from Home: header, task progress, content. */
    @Composable
    private fun HubScreenScaffold(
        title: String,
        subtitle: String,
        installProgress: InstallProgress,
        subtitleColor: Color = RetroTextMuted,
        content: @Composable ColumnScope.() -> Unit,
    ) {
        Column(
            Modifier.fillMaxSize().background(RetroBg)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            RetroScreenHeader(title, onBack = { goBack() }, subtitle = subtitle, subtitleColor = subtitleColor)
            // Tasks started here report progress here too (they used to run on Home, under its progress card).
            AnimatedVisibility(visible = installProgress.state != InstallState.IDLE) {
                InstallProgressSection(installProgress)
            }
            content()
            Spacer(Modifier.height(24.dp))
        }
    }

    @Composable
    fun MusicHubScreen(installProgress: InstallProgress) {
        val musicPicker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                musicImportLocalUri = it
                musicImportSearchQuery = "" // Reset
                musicImportResults = emptyList()
                selectedMetadata = null
                showMusicImportDialog = true
            }
        }
        HubScreenScaffold("Music", "Swap game soundtracks and build a library", installProgress) {
            MusicHubSection(musicPicker)
        }
    }

    @Composable
    fun GraphicsHubScreen(installProgress: InstallProgress, isUiIdle: Boolean) {
        HubScreenScaffold("Graphics", "Override your device tier for hidden quality", installProgress) {
            GraphicsHubSection(isUiIdle)
        }
    }

    @Composable
    fun SaveHubScreen(installProgress: InstallProgress, isUiIdle: Boolean) {
        val characterPicker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let { startBackgroundInstall("CHARACTER", uri = it) }
        }
        val docPicker = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocumentTree()) { uri ->
            uri?.let { startBackgroundInstall("DOC", uri = it) }
        }
        HubScreenScaffold("Saves", "Snapshots, backups and save import", installProgress) {
            SaveHubSection(isUiIdle)

            // Import (was "Manual import" on Home)
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                SectionHeader("Import save", Icons.Filled.FileUpload)
                Text("Import external save files into the game.", style = MaterialTheme.typography.bodyMedium, color = RetroTextMuted)
                Row(horizontalArrangement = Arrangement.spacedBy(9.dp)) {
                    SecondaryButton("Character2.dat", Modifier.weight(1f), icon = Icons.AutoMirrored.Filled.InsertDriveFile) { characterPicker.launch("application/octet-stream") }
                    SecondaryButton(".doc folder", Modifier.weight(1f), icon = Icons.Filled.Folder) { docPicker.launch(null) }
                }
            }
        }
    }

    /** Checks, logs, the pipeline test and the game-asset repair (was "RR3 crash / error fix" on Home). */
    @Composable
    fun DiagnosticsScreen(installProgress: InstallProgress, actionsReady: Boolean) {
        HubScreenScaffold("Diagnostics", "Checks, logs and game repair", installProgress) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                SectionHeader("Checks", Icons.Filled.Troubleshoot)
                val diags = listOf(
                    "Directory access" to { runRemoteCall { it.testRR3Access() } },
                    "Write access (legacy)" to { runRemoteCall { it.testRR3Write() } },
                )
                Column(verticalArrangement = Arrangement.spacedBy(9.dp)) {
                    diags.chunked(2).forEach { row ->
                        Row(horizontalArrangement = Arrangement.spacedBy(9.dp)) {
                            row.forEach { (label, action) -> SecondaryButton(label, modifier = Modifier.weight(1f)) { action() } }
                        }
                    }
                    SecondaryButton("Generate logs", modifier = Modifier.fillMaxWidth()) { generateLogs() }
                    SecondaryButton("Show device ID", modifier = Modifier.fillMaxWidth()) {
                        @Suppress("HardwareIds")
                        val id = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
                        Toast.makeText(this@MainActivity, "ID: $id", Toast.LENGTH_LONG).show()
                    }
                }
            }

            // Hidden while a task runs, as it was on Home.
            if (actionsReady) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    SectionHeader("Pipeline", Icons.Filled.VerifiedUser)
                    Text("Dry-run the install pipeline without changing the game.", style = MaterialTheme.typography.bodyMedium, color = RetroTextMuted)
                    SecondaryButton("Pipeline test", Modifier.fillMaxWidth(), icon = Icons.Filled.VerifiedUser) {
                        // Silently does nothing without the helper, as before.
                        if (service != null) startInstallServiceAction("START_SAFE_TEST")
                    }
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                SectionHeader("Repair", Icons.Filled.Build)
                Text("Fixes crashes and errors by restoring the game's graphics assets (keeps your HDR level).", style = MaterialTheme.typography.bodyMedium, color = RetroTextMuted)
                WarningButton("Repair game assets", icon = Icons.Filled.Build) { startBackgroundInstall("REPAIR", hdrLevel = graphicsHdrLevel) }
            }
        }
    }

    @Composable
    fun InstallProgressSection(progress: InstallProgress) {
        RetroCard {
            val title = when (progress.state) {
                InstallState.SUCCESS -> "COMPLETE"
                InstallState.FAILED -> "FAILED"
                else -> if (progress.totalFiles > 1) "PART ${progress.completedFiles + 1} OF ${progress.totalFiles}" else "INSTALLING"
            }
            Text(title, style = MaterialTheme.typography.titleLarge, color = if (progress.state == InstallState.SUCCESS) RetroSuccess else if (progress.state == InstallState.FAILED) RetroDanger else RetroAccent)

            Spacer(modifier = Modifier.height(8.dp))
            Text(progress.currentStep, style = MaterialTheme.typography.bodyMedium, color = RetroText)
            if (progress.currentFile.isNotBlank()) {
                Text(progress.currentFile, style = MaterialTheme.typography.labelSmall, color = RetroTextMuted)
            }

            if (progress.state != InstallState.SUCCESS && progress.state != InstallState.FAILED) {
                Spacer(modifier = Modifier.height(12.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("${(progress.overallProgress * 100).toInt()}%", style = MaterialTheme.typography.labelSmall)
                    if (progress.eta.isNotBlank()) {
                        Text("ETA: ${progress.eta}", style = MaterialTheme.typography.labelSmall, color = RetroAccent)
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                LinearProgressIndicator(progress = { progress.overallProgress }, modifier = Modifier.fillMaxWidth(), color = RetroAccent, trackColor = RetroBorder)
            }

            if (progress.errorMessage != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(progress.errorMessage, style = MaterialTheme.typography.bodySmall, color = RetroDangerText)
                
                if (progress.state == InstallState.FAILED) {
                    Spacer(modifier = Modifier.height(8.dp))
                    PrimaryButton("ASK AI FOR HELP", icon = Icons.AutoMirrored.Filled.Chat) {
                        lifecycleScope.launch {
                            isAiThinking = true
                            val currentService = service ?: return@launch
                            val response = Retr0Installer(this@MainActivity, currentService).troubleshootError(progress.errorMessage)
                            aiFixResponse = response ?: "Could not connect to AI."
                            isAiThinking = false
                        }
                    }
                    
                    if (aiFixResponse != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(modifier = Modifier.fillMaxWidth().background(RetroAccentTint, RoundedCornerShape(12.dp)).padding(12.dp)) {
                            Text("🤖 AI FIX: $aiFixResponse", style = MaterialTheme.typography.bodySmall, color = RetroText)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun CommunityGalleryScreen(onFullScreenChange: (Boolean) -> Unit = {}) {
        var searchQuery by remember { mutableStateOf("") }
        var focusedSkinRoute by rememberSaveable { mutableStateOf(false) }
        val scrollState = rememberScrollState()

        // Handle deep link navigation
        LaunchedEffect(pendingDeepLinkSkinId, pendingDeepLinkSaveId, pendingDeepLinkMusicId) {
            pendingDeepLinkSkinId?.let { communityTab = 2 }
            pendingDeepLinkSaveId?.let { communityTab = 0 }
            pendingDeepLinkMusicId?.let { communityTab = 1 }
        }

        val linkedSave = remember(communitySaves, pendingDeepLinkSaveId) {
            pendingDeepLinkSaveId?.let { id -> communitySaves.find { it.id == id } }
        }
        val linkedMusic = remember(communityMusic, pendingDeepLinkMusicId) {
            pendingDeepLinkMusicId?.let { id -> communityMusic.find { it.id == id } }
        }

        linkedSave?.let { save ->
            AlertDialog(
                onDismissRequest = { pendingDeepLinkSaveId = null },
                title = { Text("Shared Save Found") },
                text = { Text("A community member shared a save: ${save.customName} by ${save.uploaderName}. Would you like to get it?") },
                confirmButton = {
                    TextButton(onClick = { 
                        pendingDeepLinkSaveId = null
                        startBackgroundInstall("DOWNLOAD_COMMUNITY", saveId = save.id, customName = save.customName)
                    }) { Text("Get Save") }
                },
                dismissButton = {
                    TextButton(onClick = { pendingDeepLinkSaveId = null }) { Text("Close") }
                }
            )
        }

        linkedMusic?.let { music ->
            AlertDialog(
                onDismissRequest = { pendingDeepLinkMusicId = null },
                title = { Text("Shared Music Found") },
                text = { Text("A community member shared a track: ${music.customName} by ${music.uploaderName}. Would you like to get it?") },
                confirmButton = {
                    TextButton(onClick = { 
                        pendingDeepLinkMusicId = null
                        startBackgroundInstall("DOWNLOAD_TO_LIBRARY", saveId = music.id, customName = music.customName)
                    }) { Text("Get Music") }
                },
                dismissButton = {
                    TextButton(onClick = { pendingDeepLinkMusicId = null }) { Text("Close") }
                }
            )
        }

        DisposableEffect(Unit) { onDispose { onFullScreenChange(false) } }
        LaunchedEffect(focusedSkinRoute) { onFullScreenChange(focusedSkinRoute) }

        val filteredSaves = remember(communitySaves, searchQuery) {
            communitySaves.filter { 
                it.customName.contains(searchQuery, ignoreCase = true) || 
                it.uploaderName.contains(searchQuery, ignoreCase = true) ||
                it.id.contains(searchQuery, ignoreCase = true)
            }
        }

        val filteredMusic = remember(communityMusic, searchQuery) {
            communityMusic.filter { 
                it.customName.contains(searchQuery, ignoreCase = true) || 
                it.uploaderName.contains(searchQuery, ignoreCase = true) ||
                it.id.contains(searchQuery, ignoreCase = true)
            }
        }

        Column(
            Modifier.fillMaxSize().background(RetroBg)
                .then(if (communityTab >= 2) Modifier else Modifier.verticalScroll(scrollState))
                .padding(horizontal = 18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text("Community Hub", style = MaterialTheme.typography.titleLarge, color = RetroText)
                    Text("Browse and share RR3 saves", style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 0.sp), color = RetroTextMuted)
                }
                Box(Modifier.size(36.dp).background(RetroSurface, RoundedCornerShape(10.dp)).border(1.dp, RetroBorder, RoundedCornerShape(10.dp))
                        .clickable { refreshCommunityHub() }, Alignment.Center) { Icon(Icons.Filled.Refresh, null, tint = RetroAccent) }
            }
            
            Row(horizontalArrangement = Arrangement.spacedBy(9.dp)) {
                Box(Modifier.weight(1f)) { 
                    RetroSearchField(value = searchQuery, onValueChange = { searchQuery = it }, placeholder = "Search name, author, file ID…", icon = Icons.Filled.Search)
                }
                Box(Modifier.size(44.dp).background(RetroSurface, RoundedCornerShape(12.dp)).border(1.dp, RetroBorder, RoundedCornerShape(12.dp))
                        .clickable {
                            communitySortType = when (communitySortType) {
                                "newest" -> "downloads"
                                "downloads" -> "likes"
                                else -> "newest"
                            }
                            refreshCommunityHub()
                        }, Alignment.Center) {
                    Icon(Icons.Filled.SwapVert, contentDescription = "Change sort order", tint = RetroAccent)
                }
            }
            
            SegmentedTabs(listOf("Saves", "Music", "Skins", "Car Models"), communityTab) { communityTab = it }

            if (communityTab == 0) {
                if (isCommunityLoading) {
                    repeat(3) {
                        Box(modifier = Modifier.fillMaxWidth().height(150.dp).clip(RoundedCornerShape(16.dp)).shimmer())
                        Spacer(Modifier.height(12.dp))
                    }
                } else if (filteredSaves.isEmpty()) {
                    Column(Modifier.fillMaxWidth().padding(top = 60.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Filled.SearchOff, null, tint = RetroTextMuted, modifier = Modifier.size(34.dp))
                        Spacer(Modifier.height(10.dp))
                        Text("No saves found", color = RetroTextDim, style = MaterialTheme.typography.bodyLarge)
                    }
                } else {
                    filteredSaves.forEach { save ->
                        SaveCard(
                            save = save,
                            onGet = { startBackgroundInstall("DOWNLOAD_COMMUNITY", saveId = save.id, customName = save.customName) },
                            onFav = {
                                val liked = !save.likedByMe
                                communitySaves = communitySaves.map {
                                    if (it.id == save.id) it.copy(likedByMe = liked, likes = (it.likes + if (liked) 1 else -1).coerceAtLeast(0)) else it
                                }
                                lifecycleScope.launch {
                                    val success = withContext(Dispatchers.IO) {
                                        SaveApiClient(this@MainActivity).setLike(save.id, liked)
                                    }
                                    if (!success) {
                                        communitySaves = communitySaves.map {
                                            if (it.id == save.id) it.copy(likedByMe = !liked, likes = (it.likes + if (liked) -1 else 1).coerceAtLeast(0)) else it
                                        }
                                        Toast.makeText(this@MainActivity, "Unable to update like", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            },
                            onDelete = if (save.isOwner) { {
                                lifecycleScope.launch(Dispatchers.IO) {
                                    if (SaveApiClient(this@MainActivity).deleteSave(save.id)) {
                                        withContext(Dispatchers.Main) {
                                            Toast.makeText(this@MainActivity, "Save deleted", Toast.LENGTH_SHORT).show()
                                            refreshCommunityHub()
                                        }
                                    }
                                }
                            } } else null,
                            onShare = { 
                                val url = save.shareUrl ?: return@SaveCard
                                val intent = Intent(Intent.ACTION_SEND).apply { 
                                    type = "text/plain"
                                    putExtra(Intent.EXTRA_TEXT, url)
                                }
                                startActivity(Intent.createChooser(intent, "Share save"))
                            }
                        )
                        Spacer(Modifier.height(12.dp))
                    }
                }
            } else if (communityTab == 1) {
                if (isCommunityLoading) {
                    repeat(3) {
                        Box(modifier = Modifier.fillMaxWidth().height(100.dp).clip(RoundedCornerShape(16.dp)).shimmer())
                        Spacer(Modifier.height(12.dp))
                    }
                } else if (filteredMusic.isEmpty()) {
                    Column(Modifier.fillMaxWidth().padding(top = 60.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Filled.MusicOff, null, tint = RetroTextMuted, modifier = Modifier.size(34.dp))
                        Spacer(Modifier.height(10.dp))
                        Text("No music found", color = RetroTextDim, style = MaterialTheme.typography.bodyLarge)
                    }
                } else {
                    filteredMusic.forEach { music ->
                        CommunityMusicItem(
                            music = music,
                            onFav = {
                                lifecycleScope.launch(Dispatchers.IO) {
                                    if (MusicApiClient(this@MainActivity).setLike(music.id, !music.likedByMe)) {
                                        withContext(Dispatchers.Main) { refreshCommunityHub() }
                                    }
                                }
                            },
                            onDelete = if (music.isOwner) { {
                                lifecycleScope.launch(Dispatchers.IO) {
                                    if (MusicApiClient(this@MainActivity).deleteMusic(music.id)) {
                                        withContext(Dispatchers.Main) {
                                            Toast.makeText(this@MainActivity, "Music deleted", Toast.LENGTH_SHORT).show()
                                            refreshCommunityHub()
                                        }
                                    }
                                }
                            } } else null,
                            onShare = {
                                val url = music.shareUrl ?: return@CommunityMusicItem
                                val intent = Intent(Intent.ACTION_SEND).apply { 
                                    type = "text/plain"
                                    putExtra(Intent.EXTRA_TEXT, url)
                                }
                                startActivity(Intent.createChooser(intent, "Share music"))
                            }
                        )
                        Spacer(Modifier.height(12.dp))
                    }
                }
            } else if (communityTab == 3) {
                CarModelsHubTab(
                    gamePackage = GamePackage.ROW,
                    onOpenMyModels = { modsKind = ModsKind.CARS; currentMode = AppMode.SKIN_MODS },
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    onFullScreenChange = { focusedSkinRoute = it },
                    refreshToken = carModelsRefreshToken,
                    onRefresh = { refreshCommunityHub() },
                )
            } else {
                // Keep this instance composed while it switches between the gallery and
                // a skin detail. Replacing it with a full-screen instance reset its
                // local selected-skin state and immediately returned users to the gallery.
                CarSkinsTab(
                    service = service,
                    isServiceConnected = isServiceBound,
                    onOpenMySkins = { modsKind = ModsKind.SKINS; currentMode = AppMode.SKIN_MODS },
                    onOpenImport = { modsKind = ModsKind.SKINS; currentMode = AppMode.SKIN_MODS },
                    onFullScreenChange = { focusedSkinRoute = it },
                    initialSkinId = pendingDeepLinkSkinId,
                    onInitialSkinIdHandled = { pendingDeepLinkSkinId = null },
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    refreshToken = communityRefreshToken,
                    sortOrder = communitySortType,
                )
            }
        }
    }

    @Composable
    fun SaveCard(save: CommunitySave, onGet: () -> Unit, onFav: () -> Unit, onDelete: (() -> Unit)? = null, onShare: () -> Unit = {}) {
        RetroCard {
            Row(verticalAlignment = Alignment.Top) {
                SkinAvatar(
                    avatarUrl = save.uploaderAvatarUrl,
                    name = save.uploaderName,
                    size = 38.dp,
                )
                Spacer(Modifier.width(11.dp))
                Column(Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(save.customName, style = MaterialTheme.typography.titleMedium, color = RetroText, modifier = Modifier.weight(1f, fill = false))
                        if (save.isOwner) {
                            Spacer(Modifier.width(6.dp))
                            BadgePill("Mine", Icons.Filled.Person, RetroAccent)
                        }
                    }
                    Text("by ${save.uploaderName} · ${StorageUtils.formatSize(save.fileSize)}", style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 0.sp), color = RetroTextMuted)
                }
                Row(
                    modifier = Modifier.wrapContentWidth(),
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(
                        onClick = onShare,
                        modifier = Modifier.size(48.dp),
                    ) {
                        Icon(Icons.Filled.Share, contentDescription = "Share save", tint = RetroAccent)
                    }
                    if (save.isOwner && onDelete != null) {
                        IconButton(
                            onClick = onDelete,
                            modifier = Modifier.size(48.dp),
                        ) {
                            Icon(Icons.Filled.Delete, contentDescription = "Delete save", tint = RetroDanger)
                        }
                    }
                    IconButton(
                        onClick = onFav,
                        modifier = Modifier.size(48.dp),
                    ) {
                        Icon(
                            if (save.likedByMe) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = if (save.likedByMe) "Unlike save" else "Like save",
                            tint = if (save.likedByMe) RetroDanger else RetroTextMuted,
                        )
                    }
                }
            }
            Spacer(Modifier.height(12.dp))
            Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(7.dp)) {
                    RetroStatCell(label = "Level", value = save.playerLevel.toString(), modifier = Modifier.weight(1f))
                    RetroStatCell(label = "Gold", value = save.playerGold.toString(), modifier = Modifier.weight(1f))
                }
                Row(horizontalArrangement = Arrangement.spacedBy(7.dp)) {
                    RetroStatCell(label = "R$", value = save.playerRs.toString(), modifier = Modifier.weight(1f))
                    RetroStatCell(label = "M$", value = save.playerMs.toString(), modifier = Modifier.weight(1f))
                }
            }
            Spacer(Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.Download, null, tint = RetroTextDim, modifier = Modifier.size(14.dp))
                Spacer(Modifier.width(3.dp))
                Text(save.downloads.toString(), color = RetroTextDim, style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.width(14.dp))
                Icon(Icons.Filled.Favorite, null, tint = RetroDanger, modifier = Modifier.size(14.dp))
                Spacer(Modifier.width(3.dp))
                Text(save.likes.toString(), color = RetroTextDim, style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.weight(1f))
                Box(Modifier.clip(RoundedCornerShape(20.dp)).background(RetroAccent).clickable(onClick = onGet).padding(horizontal = 26.dp, vertical = 9.dp)) {
                    Text("Get", color = Color.White, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }

    @Composable
    fun CommunityMusicItem(music: CommunityMusic, onFav: () -> Unit, onDelete: (() -> Unit)? = null, onShare: () -> Unit = {}) {
        RetroCard {
            Row(verticalAlignment = Alignment.Top) {
                Box(
                    Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(RetroAccentTint),
                    Alignment.Center,
                ) {
                    if (music.coverUrl != null) {
                        AsyncImage(
                            model = music.coverUrl,
                            contentDescription = "Artwork for ${music.customName}",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,
                        )
                    } else {
                        Icon(Icons.Filled.MusicNote, null, tint = RetroAccent, modifier = Modifier.size(30.dp))
                    }
                }
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            music.customName,
                            style = MaterialTheme.typography.titleMedium,
                            color = RetroText,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f),
                        )
                        if (music.isOwner) {
                            Spacer(Modifier.width(6.dp))
                            BadgePill("Mine", Icons.Filled.Person, RetroAccent)
                        }
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        SkinAvatar(avatarUrl = music.uploaderAvatarUrl, name = music.uploaderName, size = 20.dp)
                        Spacer(Modifier.width(6.dp))
                        Text(
                            music.uploaderName,
                            style = MaterialTheme.typography.bodyMedium,
                            color = RetroTextMuted,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                    Text(
                        "Replaces: ${music.targetTrack}",
                        style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 0.sp),
                        color = RetroAccent,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        StorageUtils.formatSize(music.fileSize),
                        style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 0.sp),
                        color = RetroTextMuted,
                    )
                }
            }
            Spacer(Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onShare) { Icon(Icons.Filled.Share, null, tint = RetroAccent) }
                Icon(
                    if (music.likedByMe) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    null,
                    tint = if (music.likedByMe) RetroDanger else RetroTextMuted,
                    modifier = Modifier.clickable(onClick = onFav),
                )
                Spacer(Modifier.width(5.dp))
                Text(music.likes.toString(), color = RetroTextDim, style = MaterialTheme.typography.labelSmall)
                Spacer(Modifier.width(14.dp))
                Icon(Icons.Filled.Download, null, tint = RetroTextMuted, modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(5.dp))
                Text(music.downloads.toString(), color = RetroTextDim, style = MaterialTheme.typography.labelSmall)
                Spacer(Modifier.weight(1f))
                if (music.isOwner && onDelete != null) {
                    IconButton(onClick = onDelete) { Icon(Icons.Filled.Delete, null, tint = RetroDanger) }
                }
                Box(
                    Modifier.clip(RoundedCornerShape(20.dp)).background(RetroAccent).clickable {
                        startBackgroundInstall("DOWNLOAD_TO_LIBRARY", saveId = music.id, customName = music.customName)
                    }.padding(horizontal = 22.dp, vertical = 9.dp),
                ) {
                    Text("Get", color = Color.White, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }

    @Composable
    fun MusicHubSection(musicPicker: androidx.activity.result.ActivityResultLauncher<String>) {
        var communityTabIndex by remember { mutableIntStateOf(0) }

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            SectionHeader("Tracks", Icons.Filled.MusicNote)

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                RetroCard {
                    SegmentedTabs(listOf("Core","Extended","Library"), communityTabIndex) { communityTabIndex = it }
                    Spacer(Modifier.height(12.dp))
                    
                    when (communityTabIndex) {
                        0 -> {
                            TrackRow("Main menu", "Menu_Music.mp3")
                            HorizontalDivider(color = RetroBorder)
                            TrackRow("Garage", "Garage_Music.mp3")
                            HorizontalDivider(color = RetroBorder)
                            TrackRow("Loading screen", "LoadingScreen.mp3")
                        }
                        1 -> {
                            val extended = listOf(
                                "IntroLogos.mp3" to "Intro Logos",
                                "IntroCinematic.mp3" to "Intro Cinematic",
                                "Ryan_Kurt_Time.mp3" to "Time",
                                "Ryan_Kurt_Strike.mp3" to "Strike",
                                "Ryan_Kurt_Further.mp3" to "Further",
                                "Ryan_Kurt_Taplein.mp3" to "Taplein",
                                "Ryan_Kurt_Connected.mp3" to "Connected",
                                "Ryan_Kurt_Red_Green.mp3" to "Red Green"
                            )
                            extended.forEachIndexed { i, (file, title) ->
                                TrackRow(title, file)
                                if (i < extended.size - 1) HorizontalDivider(color = RetroBorder)
                            }
                        }
                        2 -> {
                            if (libraryMusic.isEmpty()) {
                                Text("No tracks in library.", style = MaterialTheme.typography.bodyMedium, color = RetroTextDim, modifier = Modifier.padding(vertical = 12.dp))
                            } else {
                                libraryMusic.forEachIndexed { i, music ->
                                    LibraryTrackRow(music)
                                    if (i < libraryMusic.size - 1) HorizontalDivider(color = RetroBorder)
                                }
                            }
                        }
                    }
                }
                
                SecondaryButton("Import from device", Modifier.fillMaxWidth(), icon = Icons.Filled.FileUpload) { 
                    musicPicker.launch("audio/mpeg") 
                }
                
                SecondaryButton("View all game track slots", Modifier.fillMaxWidth(), icon = Icons.AutoMirrored.Filled.List) { 
                    refreshMusicSlots()
                    openMode(AppMode.MUSIC_SLOTS)
                }
                
            }
        }
    }

    @Composable
    fun TrackRow(title: String, file: String) {
        // The slot explorer uses full paths (music/file.mp3), 
        // while the dashboard often uses just the filename. 
        // We check both for the revert button logic to be foolproof.
        val hasBackup = backedUpSlots.contains("music/$file") || backedUpSlots.contains(file)
        
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 8.dp)) {
            Icon(Icons.Filled.PlayArrow, null, tint = RetroTextDim, modifier = Modifier.size(15.dp))
            Spacer(Modifier.width(9.dp))
            Column(Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.bodyLarge, color = RetroText)
                Text(file, style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 0.sp), color = RetroTextMuted)
            }
            Icon(
                imageVector = Icons.Filled.Refresh,
                contentDescription = if (hasBackup) "Revert to original" else null,
                tint = if (hasBackup) RetroDanger else RetroTextMuted,
                modifier = Modifier
                    .size(if (hasBackup) 22.dp else 15.dp)
                    .then(if (hasBackup) Modifier.clickable { startBackgroundInstall("REVERT_AUDIO", targetTrack = "music/$file") } else Modifier)
            )
        }
    }

    @Composable
    fun LibraryTrackRow(music: LibraryMusic) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 8.dp)) {
            AsyncImage(
                model = music.artworkUrl ?: R.drawable.ic_app_icon,
                contentDescription = null,
                modifier = Modifier.size(32.dp).clip(RoundedCornerShape(6.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(music.trackName ?: music.fileName, style = MaterialTheme.typography.bodyLarge, color = RetroText)
                Text(music.artistName ?: "Unknown Artist", style = MaterialTheme.typography.labelSmall, color = RetroTextMuted)
            }
            Icon(
                Icons.Filled.CloudUpload,
                contentDescription = "Upload ${music.trackName ?: music.fileName} to Community",
                tint = RetroAccent,
                modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        libraryMusicToUpload = music
                        showLibraryMusicUploadDialog = true
                    }
            )
            Spacer(Modifier.width(12.dp))
            Icon(
                Icons.Filled.Delete,
                null,
                tint = RetroDanger,
                modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        lifecycleScope.launch {
                            val currentService = service ?: return@launch
                            Retr0Installer(this@MainActivity, currentService).deleteLibraryMusic(music.fileName)
                            refreshMusicLibrary()
                        }
                    }
            )
            Spacer(Modifier.width(12.dp))
            Text("Apply", color = RetroAccent, style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.border(1.dp, RetroAccent.copy(alpha = 0.4f), RoundedCornerShape(20.dp))
                    .clickable { showSlotPickerForLibraryTrack = music }.padding(horizontal = 12.dp, vertical = 4.dp))
        }
    }

    /** Reads the HDR level in the game files so the A/B/C switches show what is really applied. */
    private fun refreshGraphicsHdrScan() {
        val s = service ?: return
        if (graphicsHdrScanning) return
        graphicsHdrScanning = true
        lifecycleScope.launch {
            val scan = runCatching { Retr0Installer(this@MainActivity, s).scanGraphicsHdr() }.getOrNull()
            graphicsHdrScan = scan
            scan?.state?.level?.let { found ->
                if (found != graphicsHdrLevel) {
                    graphicsHdrLevel = found
                    getSharedPreferences("retr0_prefs", MODE_PRIVATE).edit().putString("graphics_hdr_level", found.name).apply()
                }
            }
            Log.i(GraphicsPathValidator.LOG_TAG, "code=GFX_I018 stage=hdr_switches scan=${scan?.state ?: "unavailable"} mixed=${scan?.mixed} selected=${graphicsHdrLevel.name}")
            graphicsHdrScanning = false
        }
    }

    /**
     * HDR fix level as a single-choice radio group (A, B, C). Picking a level applies it straight away; it
     * can't be cleared. The selected row comes from scanning the game files, not from the last tap.
     */
    @Composable
    fun HdrLevelSwitches(isUiIdle: Boolean) {
        // Rescan when the screen opens and whenever a task finishes (any apply/restore may change the level).
        LaunchedEffect(isUiIdle, service) { if (isUiIdle) refreshGraphicsHdrScan() }
        val scan = graphicsHdrScan
        val applied = scan?.state?.level
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text("HDR black-screen fix", style = MaterialTheme.typography.titleMedium.copy(fontSize = 14.sp), color = RetroText)
            val (status, warn) = when {
                graphicsHdrScanning && scan == null -> "Checking the game files..." to false
                scan == null -> "Couldn't read the game files. Connect Shizuku to see the current level." to true
                scan.state == HdrState.NONE -> "No HDR fix applied. Most presets show a black screen on Adreno 840 phones. Pick a level." to true
                scan.mixed -> "Presets have different HDR settings. Pick a level to set them all the same." to true
                scan.state == HdrState.CUSTOM -> "Custom HDR settings found. Pick a level to replace them." to true
                scan.state == HdrState.UNKNOWN -> "Couldn't decode the graphics files (different RR3 version?)." to true
                else -> "Level ${scan.state.name} is applied (${scan.tiers.size} presets checked, ${scan.featsReadable}/${scan.featsChecked} feature files readable)." to false
            }
            Text(status, style = MaterialTheme.typography.bodySmall, color = if (warn) RetroWarningText else RetroTextMuted)
            val rows = listOf(
                HdrLevel.A to "HDR post-process only. May still be black on Adreno 840.",
                HdrLevel.B to "Recommended. Adds tonemap and gamma; fixes the black screen.",
                HdrLevel.C to "All HDR effects: adds HDR reflections and auto exposure.",
            )
            Column(Modifier.selectableGroup(), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                rows.forEach { (level, help) ->
                    RetroRadioRow(
                        title = "Level ${level.name}",
                        description = help,
                        selected = applied == level,
                        enabled = isUiIdle && service != null,
                        badge = if (applied == level) "Applied" else null,
                    ) {
                        // Selecting the applied level does nothing; picking another applies it (the others go off).
                        if (applied == level) return@RetroRadioRow
                        graphicsHdrLevel = level
                        getSharedPreferences("retr0_prefs", MODE_PRIVATE).edit().putString("graphics_hdr_level", level.name).apply()
                        Log.i(GraphicsPathValidator.LOG_TAG, "code=GFX_I014 stage=hdr_level_select level=${level.name} was=${scan?.state ?: "unknown"}")
                        startBackgroundInstall("SET_GRAPHICS_HDR", hdrLevel = level)
                    }
                }
            }
        }
    }

    @Composable
    fun GraphicsHubSection(isUiIdle: Boolean) {
        val active = graphicsHdrScan?.activePreset
        RetroCard { HdrLevelSwitches(isUiIdle) }

        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            SectionHeader("Quality preset", Icons.Filled.Tune)
            Text(
                when {
                    graphicsHdrScan == null -> "Pick a preset to apply it to every Android tier."
                    active == null -> "Active: device default (no preset applied to every tier)."
                    else -> "Active: ${formatDeviceName(active)}"
                },
                style = MaterialTheme.typography.bodyMedium,
                color = if (active != null) RetroSuccess else RetroTextMuted,
            )
            ANDROID_PRESETS.chunked(2).forEach { row ->
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    row.forEach { (label, id) ->
                        RetroChip(label, selected = id == active, subtitle = if (id == active) "Active" else null, modifier = Modifier.weight(1f)) {
                            if (isUiIdle) startBackgroundInstall("APPLY_GRAPHICS", presetName = id, hdrLevel = graphicsHdrLevel)
                        }
                    }
                }
            }
            RetroChip("SimRigTV", selected = active == "SimRigTV", subtitle = if (active == "SimRigTV") "Active" else null, centered = true, modifier = Modifier.fillMaxWidth()) {
                if (isUiIdle) startBackgroundInstall("APPLY_GRAPHICS", presetName = "SimRigTV", hdrLevel = graphicsHdrLevel)
            }
        }

        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            SectionHeader("Restore", Icons.Filled.Restore)
            OutlineButton("Restore original graphics") {
                if (isUiIdle) startBackgroundInstall("RESTORE_GRAPHICS", hdrLevel = graphicsHdrLevel)
            }
            OutlineButton("Restore previous graphics") {
                if (isUiIdle) startBackgroundInstall("RESTORE_GRAPHICS_BACKUP", backupId = "latest", hdrLevel = graphicsHdrLevel)
            }
        }

        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            SectionHeader("Experimental", Icons.Filled.Science)
            WarningButton("Other options (experimental)", icon = Icons.Filled.WarningAmber) { openMode(AppMode.EXPERIMENTAL_GRAPHICS) }
        }
    }

    @Composable
    fun SaveHubSection(isUiIdle: Boolean) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Save, null, tint = RetroAccent, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("SNAPSHOTS", style = MaterialTheme.typography.labelSmall, color = RetroTextMuted)
                    Spacer(Modifier.weight(1f))
                    Text("Auto", style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 0.sp), color = RetroTextMuted)
                    Spacer(Modifier.width(6.dp))
                    Switch(
                        checked = isSmartBackupEnabled,
                        onCheckedChange = { toggleSmartBackup(it) },
                        colors = SwitchDefaults.colors(checkedTrackColor = RetroAccent, checkedThumbColor = Color.White),
                        modifier = Modifier.scale(0.8f)
                    )
                }
                Text("Last run: $lastBackupStatus", style = MaterialTheme.typography.bodyMedium, color = RetroTextMuted)
            }
            
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                RetroCard {
                    if (saveProfiles.isEmpty()) {
                        Text("No local snapshots.", style = MaterialTheme.typography.bodyMedium, color = RetroTextDim)
                    } else {
                        saveProfiles.forEachIndexed { i, profile ->
                            SaveRow(
                                profile.name,
                                "${StorageUtils.formatSize(profile.totalSize)} · active",
                                primary = i == 0, 
                                onApply = { if (isUiIdle) startBackgroundInstall("RESTORE", profileName = profile.name) },
                                onExport = {
                                    if (isUiIdle) {
                                        profileToUpload = profile
                                        showSaveUploadDialog = true
                                    }
                                },
                                onRename = {
                                    if (isUiIdle) {
                                        profileToRename = profile
                                        renameProfileName = profile.name
                                        renameProfileError = null
                                    }
                                }, 
                                onDelete = { if (isUiIdle) lifecycleScope.launch { val currentService = service ?: return@launch; Retr0Installer(this@MainActivity, currentService).deleteProfile(profile); refreshSaveHub() } }
                            )
                            if (i < saveProfiles.size - 1) HorizontalDivider(color = RetroBorder)
                        }
                    }
                }
                
                Row(horizontalArrangement = Arrangement.spacedBy(9.dp)) {
                    SecondaryButton("Backup data", modifier = Modifier.weight(1f)) { 
                        if (isUiIdle) startBackgroundInstall("BACKUP", profileName = "Snapshot ${saveProfiles.size + 1}") 
                    }
                    SecondaryButton("Update snapshot", modifier = Modifier.weight(1f)) { /* update */ }
                }
            }
        }
    }

    @Composable
    private fun RenameSaveDialog() {
        val profile = profileToRename ?: return
        Dialog(onDismissRequest = {
            profileToRename = null
            renameProfileError = null
        }) {
            RetroCard(Modifier.fillMaxWidth().wrapContentHeight()) {
                Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(stringResource(R.string.save_rename_title), style = MaterialTheme.typography.titleMedium, color = RetroText)
                    Text(stringResource(R.string.save_rename_description), color = RetroTextMuted)
                    OutlinedTextField(
                        value = renameProfileName,
                        onValueChange = {
                            renameProfileName = it
                            renameProfileError = null
                        },
                        label = { Text(stringResource(R.string.save_rename_name)) },
                        singleLine = true,
                        isError = renameProfileError != null,
                        supportingText = renameProfileError?.let { error -> { Text(error) } },
                        modifier = Modifier.fillMaxWidth(),
                    )
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        TextButton(onClick = {
                            profileToRename = null
                            renameProfileError = null
                        }) {
                            Text(stringResource(R.string.save_rename_cancel), color = RetroTextMuted)
                        }
                        TextButton(onClick = {
                            val requestedName = renameProfileName.trim().removeSuffix(".dat").trim()
                            if (requestedName.isBlank() || requestedName == "." || requestedName == "..") {
                                renameProfileError = getString(R.string.save_rename_blank)
                            } else if (requestedName.any { it.isISOControl() || it in "<>:\"/\\|?*" } || requestedName.contains("..") || requestedName.endsWith('.') || requestedName.endsWith(' ')) {
                                renameProfileError = getString(R.string.save_rename_invalid)
                            } else {
                                lifecycleScope.launch {
                                    val currentService = service
                                    if (currentService == null) {
                                        renameProfileError = getString(R.string.save_rename_disconnected)
                                        return@launch
                                    }
                                    val renamed = Retr0Installer(this@MainActivity, currentService).renameProfile(profile, requestedName)
                                    if (renamed) {
                                        profileToRename = null
                                        renameProfileError = null
                                        refreshSaveHub()
                                    } else {
                                        renameProfileError = getString(R.string.save_rename_unavailable)
                                    }
                                }
                            }
                        }) {
                            Text(stringResource(R.string.save_rename_action), color = RetroAccent)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun SaveRow(
        name: String,
        meta: String,
        @Suppress("UNUSED_PARAMETER") primary: Boolean,
        onApply: () -> Unit,
        onExport: () -> Unit,
        onRename: () -> Unit,
        onDelete: () -> Unit,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 10.dp)) {
            Column(Modifier.weight(1f)) {
                Text(name, style = MaterialTheme.typography.bodyLarge, color = RetroText)
                Text(meta, style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 0.sp), color = RetroTextMuted)
            }
            Text(
                "Apply",
                Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(RetroAccent)
                    .clickable(onClick = onApply)
                    .padding(horizontal = 12.dp, vertical = 5.dp),
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.width(8.dp))
            Icon(Icons.Filled.Upload, null, tint = RetroSuccess, modifier = Modifier.size(15.dp).clickable(onClick = onExport)); Spacer(Modifier.width(8.dp))
            Icon(Icons.Filled.Edit, null, tint = RetroTextDim, modifier = Modifier.size(15.dp).clickable(onClick = onRename)); Spacer(Modifier.width(8.dp))
            Icon(Icons.Filled.Delete, null, tint = RetroDanger, modifier = Modifier.size(15.dp).clickable(onClick = onDelete))
        }
    }

    @Composable
    fun ExperimentalGraphicsScreen(enabled: Boolean) {
        var query by remember { mutableStateOf("") }
        
        Column(
            Modifier.fillMaxSize().background(RetroBg)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            RetroScreenHeader("Experimental", onBack = { goBack() }, subtitle = "Other engine options · iOS · tvOS · Mac tiers", subtitleColor = RetroWarningText)

            Row(Modifier.fillMaxWidth().background(RetroWarningTint, RoundedCornerShape(14.dp)).border(1.dp, RetroWarning.copy(alpha = 0.3f), RoundedCornerShape(14.dp)).padding(14.dp)) {
                Icon(Icons.Filled.WarningAmber, null, tint = RetroWarning, modifier = Modifier.size(20.dp)); Spacer(Modifier.width(11.dp))
                Column {
                    Text("Non-Android presets", style = MaterialTheme.typography.titleMedium.copy(fontSize = 13.sp), color = RetroWarningText)
                    Text("May cause glitches, crashes or severe performance drops. Use at your own risk.", style = MaterialTheme.typography.bodyMedium, color = RetroWarningBody)
                }
            }

            RetroSearchField(value = query, onValueChange = { query = it }, placeholder = "Search presets…", icon = Icons.Filled.Search)
            
            val familyIcon = mapOf(
                "APPLE TV" to Icons.Filled.Tv, 
                "iPAD" to Icons.Filled.TabletMac,
                "iPHONE" to Icons.Filled.Smartphone, 
                "iPOD TOUCH" to Icons.Filled.Headphones, 
                "MAC" to Icons.Filled.DesktopMac
            )

            val presetFamilies = listOf(
                "APPLE TV" to availablePresets.filter { it.startsWith("AppleTV") },
                "iPAD" to availablePresets.filter { it.startsWith("iPad") },
                "iPHONE" to availablePresets.filter { it.startsWith("iPhone") },
                "iPOD TOUCH" to availablePresets.filter { it.startsWith("iPod") },
                "MAC" to availablePresets.filter { it == "mac" }
            )

            presetFamilies.forEach { (family, items) ->
                val filteredItems = items.filter { query.isBlank() || formatDeviceName(it).contains(query, true) }
                if (filteredItems.isNotEmpty()) {
                    Column {
                        SectionHeader(family, familyIcon[family] ?: Icons.Filled.Devices, trailingCount = filteredItems.size)
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            filteredItems.chunked(2).forEach { row ->
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    row.forEach { p -> 
                                        val isActive = p == graphicsHdrScan?.activePreset
                                        RetroChip(formatDeviceName(p), selected = isActive, subtitle = if (isActive) "Active" else null, modifier = Modifier.weight(1f)) { 
                                            if (enabled) startBackgroundInstall("APPLY_GRAPHICS", presetName = p, hdrLevel = graphicsHdrLevel) 
                                        } 
                                    }
                                    if (row.size == 1) Spacer(Modifier.weight(1f))
                                }
                            }
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(100.dp))
        }
    }

    private fun formatDeviceName(id: String): String {
        val mapping = mapOf(
            // Android Presets
            "AndroidLow" to "Low",
            "AndroidMid" to "Medium",
            "AndroidMid2" to "Medium 2",
            "AndroidHigh" to "High",
            "AndroidXtraHigh" to "Xtra High",
            "AndroidUltraWide" to "Ultra Wide",

            // Apple TV
            "AppleTV" to "Apple TV — Generic",
            "AppleTV5,3" to "Apple TV HD (4th generation)",
            "AppleTV6,2" to "Apple TV 4K (1st generation)",
            "AppleTV11,1" to "Apple TV 4K (2nd generation)",

            // iPad
            "iPad" to "iPad — Generic",
            "iPad1,1" to "iPad (1st generation)",
            "iPad2,1" to "iPad 2 — Wi-Fi",
            "iPad2,2" to "iPad 2 — GSM",
            "iPad2,3" to "iPad 2 — CDMA",
            "iPad2,4" to "iPad 2 — Wi-Fi (Rev. A)",
            "iPad2,5" to "iPad mini — Wi-Fi",
            "iPad2,6" to "iPad mini — GSM",
            "iPad2,7" to "iPad mini — GSM + CDMA",
            "iPad3,1" to "iPad (3rd generation) — Wi-Fi",
            "iPad3,2" to "iPad (3rd generation) — Wi-Fi + Cellular (VZ)",
            "iPad3,3" to "iPad (3rd generation) — Wi-Fi + Cellular",
            "iPad3,4" to "iPad (4th generation) — Wi-Fi",
            "iPad3,5" to "iPad (4th generation) — Wi-Fi + Cellular",
            "iPad3,6" to "iPad (4th generation) — Wi-Fi + Cellular (MM)",
            "iPad4,1" to "iPad Air — Wi-Fi",
            "iPad4,2" to "iPad Air — Wi-Fi + Cellular",
            "iPad4,3" to "iPad Air — Wi-Fi + Cellular (China)",
            "iPad4,4" to "iPad mini 2 — Wi-Fi",
            "iPad4,5" to "iPad mini 2 — Wi-Fi + Cellular",
            "iPad4,7" to "iPad mini 3 — Wi-Fi",
            "iPad5,1" to "iPad mini 4 — Wi-Fi",
            "iPad5,2" to "iPad mini 4 — Wi-Fi + Cellular",
            "iPad5,3" to "iPad Air 2 — Wi-Fi",
            "iPad5,4" to "iPad Air 2 — Wi-Fi + Cellular",
            "iPad6,3" to "iPad Pro (9.7-inch) — Wi-Fi",
            "iPad6,4" to "iPad Pro (9.7-inch) — Wi-Fi + Cellular",
            "iPad6,7" to "iPad Pro (12.9-inch) — Wi-Fi",
            "iPad6,8" to "iPad Pro (12.9-inch) — Wi-Fi + Cellular",
            "iPad6,11" to "iPad (5th generation) — Wi-Fi",
            "iPad6,12" to "iPad (5th generation) — Wi-Fi + Cellular",
            "iPad7,1" to "iPad Pro (12.9-inch) (2nd generation) — Wi-Fi",
            "iPad7,2" to "iPad Pro (12.9-inch) (2nd generation) — Wi-Fi + Cellular",
            "iPad7,3" to "iPad Pro (10.5-inch) — Wi-Fi",
            "iPad7,4" to "iPad Pro (10.5-inch) — Wi-Fi + Cellular",

            // iPhone
            "iPhone" to "iPhone — Generic",
            "iPhone2,1" to "iPhone 3GS",
            "iPhone3,1" to "iPhone 4 — GSM",
            "iPhone3,2" to "iPhone 4 — GSM (Rev. A)",
            "iPhone3,3" to "iPhone 4 — CDMA",
            "iPhone4,1" to "iPhone 4s",
            "iPhone5,1" to "iPhone 5 — GSM",
            "iPhone5,2" to "iPhone 5 — Global",
            "iPhone5,3" to "iPhone 5c — GSM",
            "iPhone5,4" to "iPhone 5c — Global",
            "iPhone6,1" to "iPhone 5s — GSM",
            "iPhone6,2" to "iPhone 5s — Global",
            "iPhone7,1" to "iPhone 6 Plus",
            "iPhone7,2" to "iPhone 6",
            "iPhone8,1" to "iPhone 6s",
            "iPhone8,2" to "iPhone 6s Plus",
            "iPhone8,4" to "iPhone SE (1st generation)",
            "iPhone9,1" to "iPhone 7 — Global",
            "iPhone9,2" to "iPhone 7 Plus — Global",
            "iPhone9,3" to "iPhone 7 — GSM",
            "iPhone9,4" to "iPhone 7 Plus — GSM",
            "iPhone10,1" to "iPhone 8 — Global",
            "iPhone10,2" to "iPhone 8 Plus — Global",
            "iPhone10,3" to "iPhone X — Global",
            "iPhone10,4" to "iPhone 8 — GSM",
            "iPhone10,5" to "iPhone 8 Plus — GSM",
            "iPhone10,6" to "iPhone X — GSM",

            // iPod touch
            "iPod" to "iPod touch — Generic",
            "iPod3,1" to "iPod touch (3rd generation)",
            "iPod4,1" to "iPod touch (4th generation)",
            "iPod5,1" to "iPod touch (5th generation)",
            "iPod7,1" to "iPod touch (6th generation)",

            // Other
            "mac" to "Mac",
            "SimRigTV" to "SimRigTV"
        )
        
        return mapping[id] ?: id
    }

    private fun clearInstallerTempFiles() {
        if (isClearingTempFiles) return
        isClearingTempFiles = true
        lifecycleScope.launch {
            val result = withContext(Dispatchers.IO) {
                runCatching {
                    val tempDir = File(externalCacheDir ?: cacheDir, "retr0_install")
                    val bytes = if (tempDir.exists()) {
                        tempDir.walkTopDown().filter { it.isFile }.sumOf { it.length() }
                    } else 0L
                    if (tempDir.exists() && !tempDir.deleteRecursively()) {
                        error("Some temporary files could not be deleted")
                    }
                    tempDir.mkdirs()
                    DownloadDatabase.getDatabase(applicationContext).chunkDao().clearAllChunks()
                    bytes
                }
            }
            isClearingTempFiles = false
            result.onSuccess { bytes ->
                updateStorageStatus()
                Toast.makeText(this@MainActivity, "Freed ${StorageUtils.formatSize(bytes)} of temporary files.", Toast.LENGTH_LONG).show()
            }.onFailure { error ->
                Toast.makeText(this@MainActivity, "Could not clear temporary files: ${error.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun startBackgroundInstall(
        actionType: String,
        uri: Uri? = null,
        profileName: String? = null,
        uploaderName: String? = null,
        saveId: String? = null,
        customName: String? = null,
        trackFileName: String? = null,
        targetTrack: String? = null,
        presetName: String? = null,
        hdrLevel: HdrLevel? = null,
        backupId: String? = null,
        manualLevel: String? = null,
        manualGold: String? = null,
        manualRS: String? = null,
        manualMS: String? = null,
        shareHub: Boolean = false,
        artworkUrl: String? = null,
        artworkUri: Uri? = null,
        artistName: String? = null,
    ) {
        if (actionType == "RESET_PROGRESS") {
            val intent = Intent(this, Retr0InstallService::class.java).apply { action = "RESET_PROGRESS" }
            startService(intent)
            return
        }

        service?.let { binder ->
            val intent = Intent(this, Retr0InstallService::class.java).apply {
                action = "START_$actionType"
                uri?.let { data = it }
                val bundle = Bundle()
                bundle.putBinder("USER_SERVICE_BINDER", binder.asBinder())
                putExtras(bundle)
                
                putExtra("PROFILE_NAME", profileName)
                putExtra("UPLOADER_NAME", uploaderName)
                putExtra("SAVE_ID", saveId)
                putExtra("CUSTOM_NAME", customName)
                putExtra("TRACK_FILENAME", trackFileName)
                putExtra("TARGET_TRACK", targetTrack)
                putExtra("PRESET_NAME", presetName)
                putExtra("HDR_LEVEL", hdrLevel?.name)
                putExtra("BACKUP_ID", backupId)
                putExtra("ARTWORK_URL", artworkUrl)
                putExtra("ARTWORK_URI", artworkUri?.toString())
                putExtra("ARTIST_NAME", artistName)
                putExtra("SHARE_HUB", shareHub)
                
                manualLevel?.toIntOrNull()?.let { putExtra("MANUAL_LEVEL", it) }
                manualGold?.toLongOrNull()?.let { putExtra("MANUAL_GOLD", it) }
                manualRS?.toLongOrNull()?.let { putExtra("MANUAL_RS", it) }
                manualMS?.toLongOrNull()?.let { putExtra("MANUAL_MS", it) }
            }
            startForegroundService(intent)
            bindService(intent, installServiceConnection, BIND_AUTO_CREATE)
        }
    }

    private fun startShizukuMonitor() {
        shizukuMonitorJob?.cancel()
        shizukuMonitorJob = lifecycleScope.launch {
            var lastBinder: Boolean? = null
            var lastPermission: Boolean? = null
            while (isActive) {
                val binderAvailable = runCatching { Shizuku.pingBinder() }.getOrDefault(false)
                val permissionGranted = binderAvailable && runCatching {
                    Shizuku.checkSelfPermission() == PackageManager.PERMISSION_GRANTED
                }.getOrDefault(false)
                if (binderAvailable != lastBinder || permissionGranted != lastPermission) {
                    Log.i(TAG, "Shizuku state changed: binderAvailable=$binderAvailable permissionGranted=$permissionGranted serviceBound=$isServiceBound")
                    lastBinder = binderAvailable
                    lastPermission = permissionGranted
                }
                updateShizukuStatus(binderAvailable, permissionGranted)
                delay(2.seconds)
            }
        }
    }

    private fun updateShizukuStatus(
        binderOverride: Boolean? = null,
        permissionOverride: Boolean? = null,
    ) {
        val binderOk = binderOverride ?: try { Shizuku.pingBinder() } catch (e: Throwable) {
            Log.w(TAG, "Shizuku binder ping failed", e)
            false
        }
        // Shizuku.pingBinder() is the SDK's authoritative availability check.
        // Do not invoke Binder.pingBinder() on the SDK-managed binder here: it can be
        // unavailable during hand-off even while the Shizuku service is operational.
        val binderAlive = binderOk
        Log.i(TAG, "updateShizukuStatus: binderAvailable=$binderAlive")
        isShizukuRunning = binderAlive
        if (!binderAlive) { 
            shizukuStatus = "Shizuku binder unavailable."
            isPermissionGranted = false
            isSystemStatusLoading = false
            return 
        }
        val permGranted = permissionOverride ?: try {
            Shizuku.checkSelfPermission() == PackageManager.PERMISSION_GRANTED
        } catch (_: Exception) {
            false
        }
        Log.i(TAG, "updateShizukuStatus: permGranted=$permGranted")
        isPermissionGranted = permGranted
        shizukuStatus = if (permGranted) "Shizuku is running (permission granted)." else "Shizuku is running (permission required)."
        isSystemStatusLoading = false
    }

    private fun requestShizukuPermission() {
        val binderAvailable = runCatching { Shizuku.pingBinder() }.getOrDefault(false)
        Log.i(TAG, "code=SHIZUKU_PERMISSION_REQUEST stage=preflight binderAvailable=$binderAvailable")
        if (!binderAvailable) {
            Toast.makeText(this, "Shizuku binder is unavailable. Open Shizuku and restart its service.", Toast.LENGTH_SHORT).show()
            return 
        }
        val permissionGranted = runCatching {
            Shizuku.checkSelfPermission() == PackageManager.PERMISSION_GRANTED
        }.getOrDefault(false)
        if (permissionGranted) {
            isPermissionGranted = true
            bindRetr0Service(forceRebind = true)
            return
        }
        Log.i(TAG, "code=SHIZUKU_PERMISSION_REQUEST stage=requesting requestCode=$SHIZUKU_PERMISSION_REQUEST_CODE")
        try {
            Shizuku.requestPermission(SHIZUKU_PERMISSION_REQUEST_CODE)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to request Shizuku permission", e)
            Toast.makeText(this, "Failed to request permission: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun canAttemptServiceBind(): Boolean {
        return runCatching { Shizuku.pingBinder() }.getOrDefault(false) &&
            runCatching { Shizuku.checkSelfPermission() == PackageManager.PERMISSION_GRANTED }.getOrDefault(false)
    }

    private fun handleUserServiceLost(reason: String) {
        userServiceBinder = null
        service = null
        isServiceBound = false
        bindInProgress = false
        shizukuStatus = reason
        Log.w(TAG, reason)
    }

    private fun scheduleUserServiceBindRetry() {
        if (!activityStarted || userServiceBindJob?.isActive == true) return
        userServiceBindJob = lifecycleScope.launch {
            repeat(3) { attempt ->
                delay(750L * (attempt + 1))
                if (!isServiceBound && canAttemptServiceBind()) {
                    bindRetr0Service()
                    delay(2_000)
                    if (isServiceBound) return@launch
                }
            }
            if (!isServiceBound && canAttemptServiceBind()) {
                shizukuStatus = "RETR0 service did not connect. Tap Enable Privileged Services to retry."
            }
        }
    }

    private fun bindRetr0Service(forceRebind: Boolean = false) {
        if (isServiceBound) return
        if (bindInProgress && !forceRebind) return
        if (forceRebind) {
            bindInProgress = false
            userServiceBindJob?.cancel()
            runCatching { Shizuku.unbindUserService(userServiceArgs, serviceConnection, true) }
                .onFailure { Log.w(TAG, "Existing RETR0 user-service unbind failed during retry", it) }
        }
        val binderReady = runCatching { Shizuku.pingBinder() }.getOrDefault(false)
        val permissionReady = runCatching {
            Shizuku.checkSelfPermission() == PackageManager.PERMISSION_GRANTED
        }.getOrDefault(false)
        if (!binderReady || !permissionReady) {
            handleUserServiceLost(if (!binderReady) "Shizuku is not running. Open Shizuku and start its service." else "Shizuku permission is required.")
            if (binderReady) requestShizukuPermission()
            return
        }
        bindInProgress = true
        shizukuStatus = "Connecting to RETR0 privileged service..."
        Log.i(TAG, "Binding RETR0 user service package=$packageName component=${Retr0UserService::class.java.name}")
        replaceHelperOncePerProcess()
        runCatching { Shizuku.bindUserService(userServiceArgs, serviceConnection) }
            .onSuccess {
                // Some OEMs accept the request but delay (or drop) the callback.
                // Do not report success until onServiceConnected has supplied a live binder.
                userServiceBindJob?.cancel()
                userServiceBindJob = lifecycleScope.launch {
                            delay(5_000)
                    if (!activityStarted) return@launch
                    if (!isServiceBound && bindInProgress) {
                        bindInProgress = false
                        shizukuStatus = "RETR0 service did not connect. Keep Shizuku running and tap Enable Privileged Services again."
                        Log.w(TAG, "code=SHIZUKU_USVC_TIMEOUT stage=bind_timeout binderAvailable=${runCatching { Shizuku.pingBinder() }.getOrDefault(false)} permissionGranted=${runCatching { Shizuku.checkSelfPermission() == PackageManager.PERMISSION_GRANTED }.getOrDefault(false)}")
                        scheduleUserServiceBindRetry()
                    }
                }
            }
            .onFailure {
                bindInProgress = false
                handleUserServiceLost("Could not start RETR0 service: ${it.message ?: "Shizuku rejected the bind"}")
                scheduleUserServiceBindRetry()
            }
    }

    private fun runRemoteCall(block: (IRetr0UserService) -> String) {
        val currentService = service ?: return
        lifecycleScope.launch {
            val result = withContext(Dispatchers.IO) { block(currentService) }
            Toast.makeText(this@MainActivity, result, Toast.LENGTH_LONG).show()
        }
    }

    private fun generateLogs() {
        val currentService = service ?: run {
            Toast.makeText(this, "Connect Privileged Services first.", Toast.LENGTH_SHORT).show()
            return
        }
        
        lifecycleScope.launch {
            val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(Date())
            val fileName = "RETR0_Logs_$timestamp.txt"
            val destPath = "/storage/emulated/0/Download/$fileName"
            
            val success = withContext(Dispatchers.IO) {
                try {
                    currentService.dumpLogcat(destPath)
                } catch (e: Exception) {
                    Log.e(TAG, "Log dump failed", e)
                    false
                }
            }
            
            if (success) {
                Toast.makeText(this@MainActivity, "Logs saved to Downloads/$fileName", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this@MainActivity, "Failed to generate logs.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun launchGame() {
        val currentService = service ?: run {
            Toast.makeText(this, "RETR0 service not connected. Please start Shizuku.", Toast.LENGTH_LONG).show()
            return
        }

        lifecycleScope.launch {
            val rowPkg = "com.ea.games.r3_row"
            val naPkg = "com.ea.games.r3_na"
            
            val pm = packageManager
            val launchIntent = pm.getLaunchIntentForPackage(rowPkg) ?: pm.getLaunchIntentForPackage(naPkg)
            val pkgName = if (pm.getLaunchIntentForPackage(rowPkg) != null) rowPkg else naPkg

            if (launchIntent != null) {
                withContext(Dispatchers.IO) {
                    try {
                        if (currentService.isPackageRunning(pkgName)) {
                            Log.i(TAG, "Restarting $pkgName...")
                            currentService.forceStopPackage(pkgName)
                            // Small delay to ensure the OS has cleaned up the process
                            delay(800.milliseconds)
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "Failed to check/stop game process", e)
                    }
                }
                startActivity(launchIntent)
            } else {
                Toast.makeText(this@MainActivity, "Real Racing 3 not found on this device.", Toast.LENGTH_LONG).show()
            }
        }
    }

    @Composable
    fun MusicSlotsScreen() {
        var query by remember { mutableStateOf("") }
        var explorerPath by remember { mutableStateOf<List<String>>(emptyList()) }
        val audioFilePicker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            val target = pendingAudioFileTarget
            pendingAudioFileTarget = null
            if (uri != null && target != null) {
                startBackgroundInstall(
                    actionType = "APPLY_FROM_FILE",
                    uri = uri,
                    targetTrack = target,
                )
            }
        }
        
        // Filter by search query first if it's not empty
        val slotsToDisplay = if (query.isNotBlank()) {
            allMusicSlots.filter { it.contains(query, true) }
        } else {
            allMusicSlots
        }

        // Determine current directory contents (Folders and Files)
        val currentDirPrefix = if (explorerPath.isEmpty()) "" else explorerPath.joinToString("/") + "/"
        
        val itemsInCurrentDir = remember(slotsToDisplay, explorerPath, currentDirPrefix) {
            val folders = mutableSetOf<String>()
            val files = mutableListOf<String>()
            
            slotsToDisplay.forEach { slot ->
                if (query.isNotBlank()) {
                    files.add(slot) // Show flat list during search
                } else if (slot.startsWith(currentDirPrefix)) {
                    val remaining = slot.substring(currentDirPrefix.length)
                    if (remaining.contains("/")) {
                        folders.add(remaining.substringBefore("/"))
                    } else {
                        files.add(remaining)
                    }
                }
            }
            (folders.asSequence().sorted().map { it to true }.toList() + 
             files.asSequence().sorted().map { it to false }.toList())
        }

        Column(
            Modifier.fillMaxSize().background(RetroBg)
                .padding(horizontal = 18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Spacer(Modifier.height(14.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { 
                    if (explorerPath.isNotEmpty() && query.isBlank()) {
                        explorerPath = explorerPath.dropLast(1)
                    } else {
                        goBack(AppMode.MUSIC)
                    }
                }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = RetroAccent)
                }
                Column(Modifier.weight(1f)) {
                    Text("Game Audio Files", style = MaterialTheme.typography.headlineMedium, color = RetroText)
                    Text("Browse engine audio assets", style = MaterialTheme.typography.labelSmall, color = RetroTextMuted)
                }
                Box(Modifier.size(36.dp).background(RetroSurface, RoundedCornerShape(10.dp)).border(1.dp, RetroBorder, RoundedCornerShape(10.dp))
                    .clickable { refreshMusicSlots() }, Alignment.Center) { Icon(Icons.Filled.Refresh, null, tint = RetroAccent) }
            }

            RetroSearchField(value = query, onValueChange = { query = it }, placeholder = "Search all audio files…", icon = Icons.Filled.Search)

            // Breadcrumbs / Folder Indicator
            if (query.isBlank()) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Icon(Icons.Filled.FolderOpen, null, tint = RetroAccent, modifier = Modifier.size(14.dp))
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = if (explorerPath.isEmpty()) "root/" else "root / " + explorerPath.joinToString(" / "),
                        style = MaterialTheme.typography.labelSmall,
                        color = RetroText
                    )
                }
            }

            SectionHeader(if (query.isNotBlank()) "Search Results" else "FileSystem", Icons.Filled.Storage, trailingCount = itemsInCurrentDir.size)

            Column(
                Modifier.weight(1f).verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (isSlotsLoading) {
                    repeat(6) {
                        Box(Modifier.fillMaxWidth().height(48.dp).shimmer())
                    }
                } else if (itemsInCurrentDir.isEmpty()) {
                    Box(Modifier.fillMaxSize(), Alignment.Center) {
                        Text("No audio files found in this directory.", color = RetroTextDim, style = MaterialTheme.typography.bodyMedium)
                    }
                } else {
                    itemsInCurrentDir.forEach { (name, isFolder) ->
                        Row(
                            Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(RetroSurface)
                                .border(1.dp, RetroBorder, RoundedCornerShape(12.dp))
                                .clickable { 
                                    if (isFolder) {
                                        explorerPath += name
                                        query = "" // Clear search when navigating into folder
                                    }
                                }.padding(horizontal = 14.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = if (isFolder) Icons.Filled.Folder else Icons.Filled.Description,
                                contentDescription = null,
                                tint = if (isFolder) RetroAccent else RetroTextDim,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(Modifier.width(12.dp))
                            Text(
                                text = name,
                                Modifier.weight(1f),
                                color = if (isFolder) Color.White else RetroText,
                                style = MaterialTheme.typography.bodyMedium,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            if (!isFolder) {
                                val fullRelativePath = if (query.isNotBlank()) name else currentDirPrefix + name
                                val hasBackup = backedUpSlots.contains(fullRelativePath)
                                
                                Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                                    if (hasBackup) {
                                        Icon(
                                            imageVector = Icons.Filled.Refresh,
                                            contentDescription = "Revert",
                                            tint = RetroDanger,
                                            modifier = Modifier
                                                .size(22.dp)
                                                .clickable { 
                                                    startBackgroundInstall("REVERT_AUDIO", targetTrack = fullRelativePath)
                                                }
                                        )
                                    }
                                    
                                    Text(
                                        "Swap",
                                        color = RetroAccent,
                                        style = MaterialTheme.typography.labelSmall,
                                        modifier = Modifier
                                            .border(1.dp, RetroAccent.copy(alpha = 0.4f), RoundedCornerShape(20.dp))
                                            .clickable {
                                                pendingAudioFileTarget = fullRelativePath
                                                audioFilePicker.launch("audio/*")
                                            }
                                            .padding(horizontal = 10.dp, vertical = 4.dp)
                                    )
                                }
                            } else {
                                Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null, tint = RetroTextMuted, modifier = Modifier.size(14.dp))
                            }
                        }
                    }
                }
                Spacer(Modifier.height(80.dp))
            }
        }
    }

    @Composable
    fun LibraryPickerForSlotDialog(gameSlotFileName: String) {
        var searchQuery by remember { mutableStateOf("") }
        val filteredLibrary = libraryMusic.filter { 
            searchQuery.isBlank() || 
            (it.trackName?.contains(searchQuery, true) == true) || 
            (it.artistName?.contains(searchQuery, true) == true) ||
            it.fileName.contains(searchQuery, true)
        }

        Dialog(onDismissRequest = { showLibraryPickerForSlot = null }) {
            RetroCard(Modifier.fillMaxWidth().heightIn(max = 600.dp)) {
                Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    Text("Pick from Library", style = MaterialTheme.typography.titleLarge, color = RetroText)
                    Text("Inject into: $gameSlotFileName", style = MaterialTheme.typography.bodySmall, color = RetroTextMuted)
                    
                    RetroSearchField(value = searchQuery, onValueChange = { searchQuery = it }, placeholder = "Filter library…", icon = Icons.Filled.Search)

                    Column(
                        Modifier.weight(1f, fill = false).verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        if (filteredLibrary.isEmpty()) {
                            Text("Your library is empty or no matches.", color = RetroTextDim, modifier = Modifier.padding(20.dp), textAlign = TextAlign.Center)
                        } else {
                            filteredLibrary.forEach { music ->
                                Row(
                                    Modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp)).background(RetroSurface)
                                        .border(1.dp, RetroBorder, RoundedCornerShape(10.dp))
                                        .clickable { 
                                            startBackgroundInstall("APPLY_FROM_LIBRARY", trackFileName = music.fileName, targetTrack = gameSlotFileName)
                                            showLibraryPickerForSlot = null
                                        }.padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    AsyncImage(
                                        model = music.artworkUrl ?: R.drawable.ic_app_icon,
                                        contentDescription = null,
                                        modifier = Modifier.size(36.dp).clip(RoundedCornerShape(6.dp)),
                                        contentScale = ContentScale.Crop
                                    )
                                    Spacer(Modifier.width(12.dp))
                                    Column(Modifier.weight(1f)) {
                                        Text(music.trackName ?: music.fileName, color = RetroText, style = MaterialTheme.typography.bodyLarge, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                        Text(music.artistName ?: "Unknown Artist", color = RetroTextMuted, style = MaterialTheme.typography.labelSmall)
                                    }
                                    Icon(Icons.AutoMirrored.Filled.ArrowForward, null, tint = RetroAccent, modifier = Modifier.size(16.dp))
                                }
                            }
                        }
                    }
                    
                    SecondaryButton("Cancel", Modifier.fillMaxWidth()) { showLibraryPickerForSlot = null }
                }
            }
        }
    }

    @Composable
    fun SlotPickerForLibraryDialog(libraryMusic: LibraryMusic) {
        val coreSlots = listOf(
            "Menu_Music.mp3" to "Main Menu",
            "Garage_Music.mp3" to "Garage",
            "LoadingScreen.mp3" to "Loading Screen"
        )
        val extendedSlots = listOf(
            "IntroLogos.mp3" to "Intro Logos",
            "IntroCinematic.mp3" to "Intro Cinematic",
            "Ryan_Kurt_Time.mp3" to "Time",
            "Ryan_Kurt_Strike.mp3" to "Strike",
            "Ryan_Kurt_Further.mp3" to "Further",
            "Ryan_Kurt_Taplein.mp3" to "Taplein",
            "Ryan_Kurt_Connected.mp3" to "Connected",
            "Ryan_Kurt_Red_Green.mp3" to "Red Green"
        )

        Dialog(onDismissRequest = { showSlotPickerForLibraryTrack = null }) {
            RetroCard(Modifier.fillMaxWidth().heightIn(max = 600.dp)) {
                Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    Text("Apply Track to Game", style = MaterialTheme.typography.titleLarge, color = RetroText)
                    
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        AsyncImage(
                            model = libraryMusic.artworkUrl ?: R.drawable.ic_app_icon,
                            contentDescription = null,
                            modifier = Modifier.size(44.dp).clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(Modifier.width(12.dp))
                        Column {
                            Text(libraryMusic.trackName ?: libraryMusic.fileName, color = RetroText, style = MaterialTheme.typography.bodyLarge)
                            Text(libraryMusic.artistName ?: "Library Track", color = RetroTextMuted, style = MaterialTheme.typography.labelSmall)
                        }
                    }

                    Column(
                        Modifier.weight(1f, fill = false).verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        SectionHeader("Core Slots", Icons.Filled.Star)
                        coreSlots.forEach { (file, title) ->
                            SlotSelectionRow(title, file) {
                                startBackgroundInstall("APPLY_FROM_LIBRARY", trackFileName = libraryMusic.fileName, targetTrack = file)
                                showSlotPickerForLibraryTrack = null
                            }
                        }

                        SectionHeader("Extended Slots", Icons.Filled.Add)
                        extendedSlots.forEach { (file, title) ->
                            SlotSelectionRow(title, file) {
                                startBackgroundInstall("APPLY_FROM_LIBRARY", trackFileName = libraryMusic.fileName, targetTrack = file)
                                showSlotPickerForLibraryTrack = null
                            }
                        }
                    }
                    
                    SecondaryButton("Cancel", Modifier.fillMaxWidth()) { showSlotPickerForLibraryTrack = null }
                }
            }
        }
    }

    @Composable
    private fun SlotSelectionRow(title: String, fileName: String, onSelect: () -> Unit) {
        val hasBackup = backedUpSlots.contains("music/$fileName") || backedUpSlots.contains(fileName)
        Row(
            Modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp)).background(RetroSurface)
                .border(1.dp, RetroBorder, RoundedCornerShape(10.dp))
                .clickable(onClick = onSelect).padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text(title, color = RetroText, style = MaterialTheme.typography.bodyMedium)
                Text(fileName, color = RetroTextMuted, style = MaterialTheme.typography.labelSmall)
            }
            if (hasBackup) {
                Box(Modifier.background(RetroSuccess.copy(alpha = 0.1f), CircleShape).padding(horizontal = 8.dp, vertical = 2.dp)) {
                    Text("PROTECTED", color = RetroSuccess, style = MaterialTheme.typography.labelSmall, fontSize = 9.sp)
                }
            }
            Spacer(Modifier.width(8.dp))
            Icon(Icons.AutoMirrored.Filled.ArrowForward, null, tint = RetroAccent, modifier = Modifier.size(16.dp))
        }
    }

    private fun requestIgnoreBatteryOptimizations() {
        // This is an optional optimization. Some OEMs do not expose the settings
        // activity, and Android rejects the request when the manifest permission is
        // missing. Neither case should prevent the app from starting.
        val powerManager = getSystemService(POWER_SERVICE) as? PowerManager ?: return
        if (powerManager.isIgnoringBatteryOptimizations(packageName)) return

        val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).apply {
            data = "package:$packageName".toUri()
        }
        if (intent.resolveActivity(packageManager) == null) {
            Log.i(TAG, "Battery optimization settings are unavailable on this device")
            return
        }
        runCatching { startActivity(intent) }
            .onFailure { error ->
                // Battery optimization exemption is not required for normal app use.
                Log.w(TAG, "Unable to request battery optimization exemption", error)
            }
    }

    @Composable
    fun MusicImportDialog() {
        var uploaderName by remember { mutableStateOf("Retr0") }
        var shareOnHub by remember { mutableStateOf(false) }
        var targetTrackSlot by remember { mutableStateOf("Garage_Music.mp3") }
        var currentStep by remember { mutableIntStateOf(0) } // 0: Search, 1: Selection, 2: Confirmation, 3: Manual metadata
        var manualTrackName by remember { mutableStateOf("") }
        var manualArtistName by remember { mutableStateOf("") }
        var noMatchesFound by remember { mutableStateOf(false) }
        var isManualMetadata by remember { mutableStateOf(false) }

        fun enterManualMetadata() {
            selectedMetadata = null
            musicImportArtworkUri = null
            manualTrackName = musicImportSearchQuery.trim()
            manualArtistName = ""
            noMatchesFound = false
            isManualMetadata = true
            currentStep = 3
        }

        fun closeDialog() {
            musicImportArtworkUri = null
            musicImportSearchQuery = ""
            musicImportResults = emptyList()
            selectedMetadata = null
            isMusicSearching = false
            showMusicImportDialog = false
        }

        val artworkPicker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            musicImportArtworkUri = uri
        }
        Dialog(onDismissRequest = { closeDialog() }) {
            RetroCard(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f)
                    .imePadding()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                ) {
                    Text(
                        "Local Music Import",
                        style = MaterialTheme.typography.titleLarge,
                        color = RetroText
                    )

                    when (currentStep) {
                        0 -> {
                            Text("What is this song called?", style = MaterialTheme.typography.bodyMedium, color = RetroTextMuted)
                            RetroSearchField(
                                value = musicImportSearchQuery,
                                onValueChange = {
                                    musicImportSearchQuery = it
                                    noMatchesFound = false
                                },
                                placeholder = "Enter song name…",
                                icon = Icons.Filled.MusicNote
                            )
                            PrimaryButton(
                                text = if (isMusicSearching) "Searching…" else "Find Matches",
                                icon = if (isMusicSearching) null else Icons.Filled.Search
                            ) {
                                if (!isMusicSearching && musicImportSearchQuery.isNotBlank()) {
                                    isMusicSearching = true
                                    noMatchesFound = false
                                    lifecycleScope.launch {
                                        try {
                                            musicImportResults = MetadataFetcher.searchMetadata(musicImportSearchQuery)
                                            if (musicImportResults.isNotEmpty()) {
                                                selectedMetadata = null
                                                currentStep = 1
                                            } else {
                                                noMatchesFound = true
                                            }
                                        } catch (e: Exception) {
                                            Toast.makeText(this@MainActivity, "Search failed: ${e.message}", Toast.LENGTH_SHORT).show()
                                        } finally {
                                            isMusicSearching = false
                                        }
                                    }
                                }
                            }
                            if (noMatchesFound) {
                                Text("No matches found. Try another name or enter the details manually.", style = MaterialTheme.typography.labelSmall, color = RetroTextMuted)
                            }
                            TextButton(onClick = { enterManualMetadata() }) {
                                Text(if (noMatchesFound) "Enter details manually" else "Can’t find your song? Enter details manually")
                            }
                        }
                        1 -> {
                            Text("Select the correct match:", style = MaterialTheme.typography.bodyMedium, color = RetroTextMuted)
                            Column(
                                Modifier.weight(1f, fill = false).verticalScroll(rememberScrollState()),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                musicImportResults.forEach { meta ->
                                    Row(
                                        Modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp))
                                            .background(if (selectedMetadata == meta) RetroAccentTint else RetroSurface)
                                            .border(1.dp, if (selectedMetadata == meta) RetroAccent else RetroBorder, RoundedCornerShape(10.dp))
                                            .clickable { selectedMetadata = meta }
                                            .padding(10.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        AsyncImage(
                                            model = meta.artworkUrl,
                                            contentDescription = null,
                                            modifier = Modifier.size(40.dp).clip(RoundedCornerShape(6.dp)),
                                            contentScale = ContentScale.Crop
                                        )
                                        Spacer(Modifier.width(12.dp))
                                        Column(Modifier.weight(1f)) {
                                            Text(meta.trackName, color = RetroText, style = MaterialTheme.typography.bodyLarge)
                                            Text(meta.artistName, color = RetroTextMuted, style = MaterialTheme.typography.labelSmall)
                                        }
                                        if (selectedMetadata == meta) {
                                            Icon(Icons.Filled.Check, null, tint = RetroAccent)
                                        }
                                    }
                                }
                            }
                            TextButton(onClick = { enterManualMetadata() }) { Text("None of these? Enter details manually") }
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                OutlineButton("Search Again", Modifier.weight(1f)) {
                                    selectedMetadata = null
                                    currentStep = 0
                                }
                                PrimaryButton("Confirm Match", Modifier.weight(1f)) {
                                    if (selectedMetadata != null) {
                                        isManualMetadata = false
                                        currentStep = 2
                                    }
                                }
                            }
                        }
                        3 -> {
                            Text("Enter song details", style = MaterialTheme.typography.bodyMedium, color = RetroTextMuted)
                            RetroSearchField(value = manualTrackName, onValueChange = { manualTrackName = it }, placeholder = "Track name (required)…", icon = Icons.Filled.MusicNote)
                            if (manualTrackName.isBlank()) {
                                Text("Track name is required.", style = MaterialTheme.typography.labelSmall, color = RetroTextMuted)
                            }
                            RetroSearchField(value = manualArtistName, onValueChange = { manualArtistName = it }, placeholder = "Artist (required)…", icon = Icons.Filled.Person)
                            if (manualArtistName.isBlank()) {
                                Text("Artist is required.", style = MaterialTheme.typography.labelSmall, color = RetroTextMuted)
                            }
                            Text("Custom artwork (required)", style = MaterialTheme.typography.labelSmall, color = RetroTextMuted)
                            OutlineButton(if (musicImportArtworkUri == null) "Choose image" else "Replace image") { artworkPicker.launch("image/*") }
                            Text(
                                if (musicImportArtworkUri == null) "Select local artwork to continue." else "Using selected local artwork.",
                                style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 0.sp),
                                color = RetroTextMuted,
                            )
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                OutlineButton("Back to Search", Modifier.weight(1f)) {
                                    musicImportArtworkUri = null
                                    isManualMetadata = false
                                    currentStep = 0
                                }
                                PrimaryButton("Continue", Modifier.weight(1f)) {
                                    if (manualTrackName.isNotBlank() && manualArtistName.isNotBlank() && musicImportArtworkUri != null) {
                                        selectedMetadata = MusicMetadata(
                                            trackName = manualTrackName.trim(),
                                            artistName = manualArtistName.trim(),
                                            artworkUrl = null,
                                        )
                                        currentStep = 2
                                    }
                                }
                            }
                        }
                        2 -> {
                            val meta = selectedMetadata ?: return@Column
                            val selectedArtwork = musicImportArtworkUri ?: meta.artworkUrl
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                AsyncImage(
                                    model = selectedArtwork,
                                    contentDescription = "Selected artwork",
                                    modifier = Modifier.size(64.dp).clip(RoundedCornerShape(10.dp)),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(Modifier.width(16.dp))
                                Column(Modifier.weight(1f)) {
                                    Text(meta.trackName, style = MaterialTheme.typography.titleMedium, color = RetroText, maxLines = 2, overflow = TextOverflow.Ellipsis)
                                    Text(meta.artistName, style = MaterialTheme.typography.bodyMedium, color = RetroTextMuted, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                }
                            }

                            HorizontalDivider(color = RetroBorder)

                            Text("Artwork", style = MaterialTheme.typography.labelSmall, color = RetroTextMuted)
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                OutlineButton("Choose image", Modifier.weight(1f)) { artworkPicker.launch("image/*") }
                                if (!isManualMetadata && musicImportArtworkUri != null) {
                                    TextButton(onClick = { musicImportArtworkUri = null }) { Text("Use matched") }
                                }
                            }
                            Text(
                                when {
                                    musicImportArtworkUri != null -> "Using selected local artwork."
                                    isManualMetadata -> "Custom artwork is required."
                                    else -> "Using the matched artwork when available."
                                },
                                style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 0.sp),
                                color = RetroTextMuted,
                            )

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("Upload to Community Hub?", Modifier.weight(1f), color = RetroText, style = MaterialTheme.typography.bodyMedium)
                                Switch(checked = shareOnHub, onCheckedChange = { shareOnHub = it }, colors = SwitchDefaults.colors(checkedTrackColor = RetroAccent))
                            }

                            if (shareOnHub) {
                                RetroSearchField(value = uploaderName, onValueChange = { uploaderName = it }, placeholder = "Your name…", icon = Icons.Filled.Person)
                            }

                            Text("Target game track slot:", style = MaterialTheme.typography.labelSmall, color = RetroTextMuted)
                            val coreSlots = listOf(
                                "Menu_Music.mp3" to "Main Menu",
                                "Garage_Music.mp3" to "Garage",
                                "LoadingScreen.mp3" to "Loading Screen"
                            )
                            val extendedSlots = listOf(
                                "IntroLogos.mp3" to "Intro Logos",
                                "IntroCinematic.mp3" to "Intro Cinematic",
                                "Ryan_Kurt_Time.mp3" to "Time",
                                "Ryan_Kurt_Strike.mp3" to "Strike",
                                "Ryan_Kurt_Further.mp3" to "Further",
                                "Ryan_Kurt_Taplein.mp3" to "Taplein",
                                "Ryan_Kurt_Connected.mp3" to "Connected",
                                "Ryan_Kurt_Red_Green.mp3" to "Red Green"
                            )

                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text("CORE", style = MaterialTheme.typography.labelSmall, color = RetroAccent, modifier = Modifier.padding(top = 4.dp))
                                coreSlots.chunked(2).forEach { row ->
                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        row.forEach { (file, title) ->
                                            RetroChip(text = title, selected = targetTrackSlot == file, modifier = Modifier.weight(1f), onClick = { targetTrackSlot = file })
                                        }
                                        if (row.size == 1) Spacer(Modifier.weight(1f))
                                    }
                                }

                                Text("EXTENDED", style = MaterialTheme.typography.labelSmall, color = RetroAccent, modifier = Modifier.padding(top = 8.dp))
                                extendedSlots.chunked(2).forEach { row ->
                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        row.forEach { (file, title) ->
                                            RetroChip(text = title, selected = targetTrackSlot == file, modifier = Modifier.weight(1f), onClick = { targetTrackSlot = file })
                                        }
                                        if (row.size == 1) Spacer(Modifier.weight(1f))
                                    }
                                }
                            }

                            PrimaryButton("Apply Profile & Process") {
                                if (isManualMetadata && musicImportArtworkUri == null) return@PrimaryButton
                                startBackgroundInstall(
                                    "MUSIC_MOD",
                                    uri = musicImportLocalUri,
                                    customName = meta.trackName,
                                    uploaderName = uploaderName,
                                    trackFileName = targetTrackSlot,
                                    targetTrack = targetTrackSlot,
                                    shareHub = shareOnHub,
                                    artworkUrl = meta.artworkUrl,
                                    artworkUri = musicImportArtworkUri,
                                    artistName = meta.artistName,
                                )
                                closeDialog()
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun LibraryMusicUploadDialog() {
        val music = libraryMusicToUpload ?: return
        var uploaderName by remember { mutableStateOf("Retr0") }
        val displayName = music.trackName ?: music.fileName.removeSuffix(".mp3")

        Dialog(onDismissRequest = { showLibraryMusicUploadDialog = false }) {
            RetroCard(Modifier.fillMaxWidth().wrapContentHeight().imePadding()) {
                Column(
                    modifier = Modifier.padding(18.dp).verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(Modifier.size(42.dp).background(RetroAccentTint, RoundedCornerShape(12.dp)), Alignment.Center) {
                            Icon(Icons.Filled.CloudUpload, null, tint = RetroAccent)
                        }
                        Spacer(Modifier.width(12.dp))
                        Column {
                            Text("Share Music", style = MaterialTheme.typography.titleLarge, color = RetroText)
                            Text(displayName, style = MaterialTheme.typography.labelSmall, color = RetroTextMuted)
                        }
                    }
                    HorizontalDivider(color = RetroBorder)
                    Text("Artist: ${music.artistName ?: "Unknown Artist"}", color = RetroTextSoft, style = MaterialTheme.typography.bodyMedium)
                    Text("File: ${music.fileName}", color = RetroTextMuted, style = MaterialTheme.typography.labelSmall)
                    Text("Artwork: ${if (music.artworkUrl != null) "Available" else "None"}", color = RetroTextMuted, style = MaterialTheme.typography.labelSmall)
                    OutlinedTextField(
                        value = uploaderName,
                        onValueChange = { uploaderName = it },
                        label = { Text("Display Name") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        leadingIcon = { Icon(Icons.Filled.Person, null, tint = RetroAccent) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = RetroText,
                            unfocusedTextColor = RetroText,
                            focusedBorderColor = RetroAccent,
                            unfocusedBorderColor = RetroBorder,
                            focusedLabelColor = RetroAccent,
                            unfocusedLabelColor = RetroTextMuted,
                        ),
                        shape = RoundedCornerShape(10.dp),
                    )
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        SecondaryButton("Cancel", Modifier.weight(1f)) { showLibraryMusicUploadDialog = false }
                        PrimaryButton("Upload Now", Modifier.weight(1f), enabled = uploaderName.isNotBlank()) {
                            startBackgroundInstall(
                                actionType = "UPLOAD_LIBRARY_MUSIC",
                                trackFileName = music.fileName,
                                customName = displayName,
                                uploaderName = uploaderName,
                            )
                            showLibraryMusicUploadDialog = false
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun SaveUploadDialog() {
        val profile = profileToUpload ?: return
        var uploaderName by remember { mutableStateOf("Retr0") }
        var manualLevel by remember { mutableStateOf("") }
        var manualGold by remember { mutableStateOf("") }
        var manualRS by remember { mutableStateOf("") }
        var manualMS by remember { mutableStateOf("") }

        val canUpload = uploaderName.isNotBlank() && manualLevel.isNotBlank() && manualGold.isNotBlank() && manualRS.isNotBlank() && manualMS.isNotBlank()

        Dialog(onDismissRequest = { showSaveUploadDialog = false }) {
            RetroCard(
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .imePadding()
            ) {
                Column(
                    modifier = Modifier
                        .padding(18.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(Modifier.size(42.dp).background(RetroAccentTint, RoundedCornerShape(12.dp)), Alignment.Center) {
                            Icon(Icons.Filled.CloudUpload, null, tint = RetroAccent)
                        }
                        Spacer(Modifier.width(12.dp))
                        Column {
                            Text("Share to Community", style = MaterialTheme.typography.titleLarge, color = RetroText)
                            Text(profile.name, style = MaterialTheme.typography.labelSmall, color = RetroTextMuted)
                        }
                    }

                    HorizontalDivider(color = RetroBorder)

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Creator identity", style = MaterialTheme.typography.labelSmall, color = RetroTextMuted)
                        OutlinedTextField(
                            value = uploaderName,
                            onValueChange = { uploaderName = it },
                            label = { Text("Display Name") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            leadingIcon = { Icon(Icons.Filled.Person, null, tint = RetroAccent) },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = RetroText,
                                unfocusedTextColor = RetroText,
                                focusedBorderColor = RetroAccent,
                                unfocusedBorderColor = RetroBorder,
                                focusedLabelColor = RetroAccent,
                                unfocusedLabelColor = RetroTextMuted
                            ),
                            shape = RoundedCornerShape(10.dp)
                        )
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Save statistics (mandatory)", style = MaterialTheme.typography.labelSmall, color = RetroTextMuted)
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Box(Modifier.weight(1f)) {
                                CurrencyField(value = manualLevel, onValueChange = { if (it.all { c -> c.isDigit() }) manualLevel = it }, label = "Level")
                            }
                            Box(Modifier.weight(1f)) {
                                CurrencyField(value = manualGold, onValueChange = { if (it.all { c -> c.isDigit() }) manualGold = it }, label = "Gold")
                            }
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Box(Modifier.weight(1f)) {
                                CurrencyField(value = manualRS, onValueChange = { if (it.all { c -> c.isDigit() }) manualRS = it }, label = "R$")
                            }
                            Box(Modifier.weight(1f)) {
                                CurrencyField(value = manualMS, onValueChange = { if (it.all { c -> c.isDigit() }) manualMS = it }, label = "M$")
                            }
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        SecondaryButton("Cancel", Modifier.weight(1f)) {
                            showSaveUploadDialog = false
                        }
                        PrimaryButton("Upload Now", Modifier.weight(1f), enabled = canUpload) {
                            startBackgroundInstall(
                                actionType = "UPLOAD",
                                profileName = profile.name,
                                uploaderName = uploaderName,
                                manualLevel = manualLevel,
                                manualGold = manualGold,
                                manualRS = manualRS,
                                manualMS = manualMS
                            )
                            showSaveUploadDialog = false
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun CurrencyField(value: String, onValueChange: (String) -> Unit, label: String) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label, style = MaterialTheme.typography.labelSmall) },
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.bodyMedium,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = RetroText,
                unfocusedTextColor = RetroText,
                focusedBorderColor = RetroAccent,
                unfocusedBorderColor = RetroBorder,
                focusedLabelColor = RetroAccent,
                unfocusedLabelColor = RetroTextMuted
            ),
            shape = RoundedCornerShape(8.dp)
        )
    }

    @Composable
    fun ResetIdentityDialog() {
        Dialog(onDismissRequest = { showResetIdentityDialog = false }) {
            RetroCard(Modifier.fillMaxWidth().wrapContentHeight()) {
                Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(Modifier.size(42.dp).background(RetroDangerTint, RoundedCornerShape(12.dp)), Alignment.Center) {
                            Icon(Icons.Filled.Warning, null, tint = RetroDanger)
                        }
                        Spacer(Modifier.width(12.dp))
                        Text("Reset Identity?", style = MaterialTheme.typography.titleLarge, color = RetroText)
                    }

                    Text(
                        "This will permanently delete your current community profile and generate a fresh one. You will lose access to your previous likes and uploads on the server.",
                        color = RetroTextSoft,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {
                        TextButton(onClick = { showResetIdentityDialog = false }) {
                            Text("Cancel", color = RetroTextMuted)
                        }
                        Spacer(Modifier.width(8.dp))
                        Button(
                            onClick = {
                                lifecycleScope.launch(Dispatchers.IO) {
                                    val client = SaveApiClient(this@MainActivity)
                                    // 1. Tell server to wipe old identity data
                                    runCatching { client.deleteInstallation() }
                                        .onFailure { Log.w(TAG, "Failed to wipe remote data during reset", it) }
                                    
                                    withContext(Dispatchers.Main) {
                                        // 2. Wipe local identity
                                        val oldInstallationId = installationId
                                        identity.resetIdentity()
                                        installationId = identity.installationId
                                        SkinPFPStore.clear(this@MainActivity, oldInstallationId)
                                        customPfp = null
                                        showResetIdentityDialog = false
                                        Toast.makeText(this@MainActivity, "Identity Reset", Toast.LENGTH_SHORT).show()
                                        refreshCommunityHub()
                                        
                                        // 3. Register new identity
                                        lifecycleScope.launch(Dispatchers.IO) {
                                            runCatching { SaveApiClient(this@MainActivity).registerInstallationIfNeeded() }
                                                .onFailure { Log.w(TAG, "New installation registration will be retried when needed", it) }
                                        }
                                    }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = RetroDanger),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Reset", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}
