package com.example.projectretr0installer

import android.Manifest
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import android.os.PowerManager
import android.provider.Settings
import android.widget.Toast
import java.io.File
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.ScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import androidx.work.*
import coil.compose.AsyncImage
import com.example.projectretr0installer.installer.*
import com.example.projectretr0installer.ui.theme.ProjectRETR0InstallerTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import rikka.shizuku.Shizuku
import rikka.shizuku.Shizuku.UserServiceArgs

class MainActivity : ComponentActivity() {

    private companion object {
        private const val TAG = "RETR0_INSTALLER"
        private const val SHIZUKU_PERMISSION_REQUEST_CODE = 1000
    }

    enum class AppMode {
        SELECTION,
        RR3_DASHBOARD,
        EXPERIMENTAL_GRAPHICS
    }

    private var currentMode by mutableStateOf(AppMode.SELECTION)
    private var service: IRetr0UserService? = null
    private var isServiceBound by mutableStateOf(value = false)
    private var isShizukuRunning by mutableStateOf(value = false)
    private var isPermissionGranted by mutableStateOf(value = false)
    private var shizukuStatus by mutableStateOf("Checking Shizuku...")
    private var availableSpace by mutableStateOf("Checking...")

    private var isSmartBackupEnabled by mutableStateOf(value = false)
    private var lastBackupStatus by mutableStateOf("Never")
    
    private var installService: Retr0InstallService.LocalBinder? by mutableStateOf(null)
    private var isInstallServiceBound by mutableStateOf(value = false)
    
    private var saveProfiles by mutableStateOf<List<SaveProfile>>(emptyList())
    private var communitySaves by mutableStateOf<List<CommunitySave>>(emptyList())
    private var communityMusic by mutableStateOf<List<CommunityMusic>>(emptyList())
    private var communitySortType by mutableStateOf("newest")
    private var libraryMusic by mutableStateOf<List<LibraryMusic>>(emptyList())
    private var isViewingCommunity by mutableStateOf(value = false)
    private var lastUpdateCheck = 0L

    private val installServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            installService = binder as? Retr0InstallService.LocalBinder
            isInstallServiceBound = true
            Log.i(TAG, "Bound to background install service")
            refreshSaveHub()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            installService = null
            isInstallServiceBound = false
        }
    }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            Log.i(TAG, "onServiceConnected(name=$name, binder=${binder != null})")
            val boundService = IRetr0UserService.Stub.asInterface(binder)
            service = boundService
            isServiceBound = true
            shizukuStatus = "RETR0 privileged service connected."

            val intent = Intent(this@MainActivity, Retr0InstallService::class.java)
            bindService(intent, installServiceConnection, 0)
            
            refreshSaveHub()
            refreshCommunityHub()
            refreshMusicLibrary()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.w(TAG, "onServiceDisconnected(name=$name)")
            service = null
            isServiceBound = false
            shizukuStatus = "RETR0 privileged service disconnected."
        }
    }

    private val userServiceArgs by lazy {
        UserServiceArgs(ComponentName(packageName, Retr0UserService::class.java.name))
            .daemon(false)
            .debuggable(BuildConfig.DEBUG)
            .processNameSuffix("retr0")
            .version(3)
    }

    private val binderReceivedListener = Shizuku.OnBinderReceivedListener {
        Log.i(TAG, "Shizuku binder received")
        updateShizukuStatus()
    }

    private val permissionResultListener = Shizuku.OnRequestPermissionResultListener { requestCode, grantResult ->
        if (requestCode == SHIZUKU_PERMISSION_REQUEST_CODE) {
            updateShizukuStatus()
            if (grantResult == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted. Connecting...", Toast.LENGTH_SHORT).show()
                bindRetr0Service()
                refreshSaveHub()
            } else {
                Toast.makeText(this, "Shizuku permission denied.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "MainActivity.onCreate")

        Shizuku.addBinderReceivedListenerSticky(binderReceivedListener)
        Shizuku.addRequestPermissionResultListener(permissionResultListener)

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                updateShizukuStatus()
                updateStorageStatus()
                
                val prefs = getSharedPreferences("retr0_prefs", MODE_PRIVATE)
                isSmartBackupEnabled = prefs.getBoolean("smart_backup", false)
                lastBackupStatus = prefs.getString("last_backup_status", "Never") ?: "Never"
                
                // Initialize local YouTube downloader engine
                Retr0Installer.initDownloader(this@MainActivity)
            }
            if (canAttemptServiceBind()) {
                Log.i(TAG, "Auto-binding service...")
                bindRetr0Service()
            }
            requestIgnoreBatteryOptimizations()
        }

        setContent {
            ProjectRETR0InstallerTheme(dynamicColor = false) {
                MainScreen()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        updateShizukuStatus()
    }

    private fun updateStorageStatus() {
        lifecycleScope.launch {
            val bytes = withContext(Dispatchers.IO) {
                StorageUtils.getAvailableSpace(externalCacheDir ?: cacheDir)
            }
            availableSpace = StorageUtils.formatSize(bytes)
        }
    }
    
    private fun refreshSaveHub() {
        val currentService = service ?: return
        lifecycleScope.launch {
            val androidId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
            val installer = Retr0Installer(this@MainActivity, currentService, androidId)
            saveProfiles = installer.getSaveProfiles()
        }
    }

    private fun toggleSmartBackup(enabled: Boolean) {
        isSmartBackupEnabled = enabled
        val prefs = getSharedPreferences("retr0_prefs", MODE_PRIVATE)
        prefs.edit { putBoolean("smart_backup", enabled) }

        val workManager = WorkManager.getInstance(this)
        if (enabled) {
            val request = PeriodicWorkRequestBuilder<BackupWorker>(4, java.util.concurrent.TimeUnit.HOURS)
                .setConstraints(Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                    .setRequiresBatteryNotLow(true)
                    .build())
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
        val currentService = service ?: return
        lifecycleScope.launch {
            val androidId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
            val installer = Retr0Installer(this@MainActivity, currentService, androidId)
            communitySaves = installer.fetchCommunitySaves(communitySortType)
            communityMusic = installer.fetchCommunityMusic(communitySortType)
        }
    }

    private fun refreshMusicLibrary() {
        val currentService = service ?: return
        lifecycleScope.launch {
            val installer = Retr0Installer(this@MainActivity, currentService)
            libraryMusic = installer.getLibraryMusic()
        }
    }

    @Composable
    fun MainScreen() {
        val progressFlow = installService?.getProgressFlow()
        val installProgress by (progressFlow ?: remember { MutableStateFlow(InstallProgress()) }).collectAsState()
        val scrollState = rememberScrollState()

        val isServiceBusy = ((installProgress.state != InstallState.IDLE) && 
                           (installProgress.state != InstallState.SUCCESS) && 
                           (installProgress.state != InstallState.FAILED))
        
        val isUiIdle = !isServiceBusy

        val blueButtonColors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF2196F3),
            contentColor = Color.White,
            disabledContainerColor = Color(0xFF37474F),
            disabledContentColor = Color.White.copy(alpha = 0.38f),
        )

        val permissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
        ) { isGranted ->
            if (!isGranted) {
                Toast.makeText(this, "Notifications recommended.", Toast.LENGTH_LONG).show()
            }
        }

        LaunchedEffect(Unit) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(this@MainActivity, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }

        LaunchedEffect(isServiceBound) {
            if (isServiceBound) {
                refreshSaveHub()
                refreshCommunityHub()
                refreshMusicLibrary()
                
                // Silent update check once per hour
                val now = System.currentTimeMillis()
                if (now - lastUpdateCheck > 3600000L) {
                    startBackgroundInstall("UPDATE")
                    lastUpdateCheck = now
                }
            }
        }
        
        LaunchedEffect(installProgress.state) {
            if (installProgress.state == InstallState.SUCCESS) {
                refreshSaveHub()
                refreshCommunityHub()
                refreshMusicLibrary()
            }
        }

        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            if (isViewingCommunity) {
                CommunityGalleryScreen(blueButtonColors)
            } else {
                when (currentMode) {
                    AppMode.SELECTION -> GameSelectionScreen()
                    AppMode.RR3_DASHBOARD -> RR3Dashboard(installProgress, isUiIdle, blueButtonColors, scrollState)
                    AppMode.EXPERIMENTAL_GRAPHICS -> ExperimentalGraphicsScreen(isUiIdle)
                }
            }
        }
    }

    @Composable
    fun GameSelectionScreen() {
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(80.dp))
            
            Text(stringResource(R.string.title_selection), style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
            Text(stringResource(R.string.subtitle_selection), style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.secondary, modifier = Modifier.padding(top = 8.dp, bottom = 32.dp))

            Card(
                onClick = { currentMode = AppMode.RR3_DASHBOARD },
                modifier = Modifier.fillMaxWidth().height(120.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Black)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = R.drawable.rr3_banner),
                        contentDescription = "Real Racing 3",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.5f))
                    )
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            stringResource(R.string.title_dashboard), 
                            style = MaterialTheme.typography.titleLarge, 
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            stringResource(R.string.active_project), 
                            style = MaterialTheme.typography.labelSmall, 
                            color = Color(0xFF4CAF50),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun RR3Dashboard(
        installProgress: InstallProgress,
        isUiIdle: Boolean,
        blueButtonColors: ButtonColors,
        scrollState: ScrollState
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp).verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { currentMode = AppMode.SELECTION }) {
                    Text("←", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(stringResource(R.string.title_dashboard), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Text(stringResource(R.string.subtitle_dashboard), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
            StatusSection(shizukuStatus, availableSpace)
            Spacer(modifier = Modifier.height(48.dp))

            AnimatedVisibility(visible = installProgress.state != InstallState.IDLE) {
                InstallProgressSection(installProgress)
            }

            if (installProgress.state != InstallState.IDLE) Spacer(modifier = Modifier.height(48.dp))

            ActionButtons(installProgress, isUiIdle, blueButtonColors)
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Button(
                onClick = { 
                    isViewingCommunity = true 
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = isServiceBound && isUiIdle,
                colors = blueButtonColors,
            ) {
                Text(stringResource(R.string.btn_open_community), fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(48.dp))
            DiscordSection(blueButtonColors)
            Spacer(modifier = Modifier.height(64.dp))
            MusicHubSection(isUiIdle, blueButtonColors)
            Spacer(modifier = Modifier.height(64.dp))
            GraphicsHubSection(isUiIdle, blueButtonColors)
            Spacer(modifier = Modifier.height(64.dp))
            SaveHubSection(isUiIdle, blueButtonColors)
            Spacer(modifier = Modifier.height(64.dp))
            SaveManagementSection(isUiIdle, blueButtonColors)
            Spacer(modifier = Modifier.height(64.dp))
            MaintenanceSection(isUiIdle, blueButtonColors)
            Spacer(modifier = Modifier.height(64.dp))
            DiagnosticSection(blueButtonColors)
            Spacer(modifier = Modifier.height(80.dp).navigationBarsPadding())
        }
    }

    @Composable
    fun StatusSection(shizukuStatus: String, space: String) {
        Box(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.shapes.medium).padding(16.dp)) {
            Column {
                Text(stringResource(R.string.section_system_status), style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(stringResource(R.string.status_shizuku, shizukuStatus), style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.height(4.dp))
                Text(stringResource(R.string.status_space, space), style = MaterialTheme.typography.bodySmall)
            }
        }
    }

    @Composable
    fun InstallProgressSection(progress: InstallProgress) {
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
            val title = when (progress.state) {
                InstallState.SUCCESS -> stringResource(R.string.progress_complete)
                InstallState.FAILED -> stringResource(R.string.progress_failed)
                InstallState.UPDATE -> stringResource(R.string.progress_updating)
                else -> {
                    if (progress.totalFiles > 1) stringResource(R.string.progress_active_parts, progress.completedFiles + 1, progress.totalFiles)
                    else stringResource(R.string.progress_active)
                }
            }
            Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = if (progress.state == InstallState.SUCCESS) Color.Green else if (progress.state == InstallState.FAILED) Color.Red else MaterialTheme.colorScheme.primary)

            Spacer(modifier = Modifier.height(8.dp))
            Text(progress.currentStep, style = MaterialTheme.typography.bodyMedium)
            if (progress.currentFile.isNotBlank()) {
                Text(progress.currentFile, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
            }

            if (progress.state != InstallState.SUCCESS && progress.state != InstallState.FAILED) {
                Spacer(modifier = Modifier.height(12.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    if (progress.overallProgress > 0f) {
                        Text(stringResource(R.string.progress_total, String.format(java.util.Locale.ENGLISH, "%.1f%%", progress.overallProgress * 100)), style = MaterialTheme.typography.labelSmall)
                    }
                    if (progress.eta.isNotBlank()) {
                        Text(stringResource(R.string.progress_eta, progress.eta), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                LinearProgressIndicator(progress = { progress.overallProgress }, modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.primary)
                if (progress.fileProgress > 0f && progress.state == InstallState.DOWNLOADING) {
                    Text(stringResource(R.string.progress_current_part, String.format(java.util.Locale.ENGLISH, "%.0f%%", progress.fileProgress * 100)), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary, modifier = Modifier.padding(top = 4.dp))
                }
            }

            if (progress.errorMessage != null) {
                Spacer(modifier = Modifier.height(12.dp))
                val parts = progress.errorMessage.split("FIX:")
                Text(parts[0].trim(), style = MaterialTheme.typography.bodySmall, color = if (progress.state == InstallState.FAILED) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant)
                if (parts.size > 1) {
                    Text(stringResource(R.string.how_to_fix, parts[1].trim()), style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = Color.Green, modifier = Modifier.padding(top = 4.dp))
                }
            }
        }
    }

    @Composable
    fun ExperimentalGraphicsScreen(isUiIdle: Boolean) {
        val scrollState = rememberScrollState()
        val experimentalPresets = listOf(
            "iPod3,1" to "iPod 3G",
            "iPod4,1" to "iPod 4G",
            "iPod5,1" to "iPod 5G",
            "iPod7,1" to "iPod 6G",
            "mac" to "Macintosh",
            "AppleTV" to "Apple TV",
            "AppleTV5,3" to "Apple TV 4K",
            "iPad" to "iPad Generic",
            "iPad1,1" to "iPad 1",
            "iPad2,1" to "iPad 2 (Wifi)",
            "iPad2,2" to "iPad 2 (GSM)",
            "iPad2,3" to "iPad 2 (CDMA)",
            "iPad2,5" to "iPad Mini 1",
            "iPad3,1" to "iPad 3 (Wifi)",
            "iPad3,2" to "iPad 3 (CDMA)",
            "iPad3,3" to "iPad 3 (GSM)",
            "iPad3,4" to "iPad 4",
            "iPad4,1" to "iPad Air 1",
            "iPad5,1" to "iPad Mini 4",
            "iPad6,3" to "iPad Pro 9.7 (Wifi)",
            "iPad6,4" to "iPad Pro 9.7 (LTE)",
            "iPad6,7" to "iPad Pro 12.9 (Wifi)",
            "iPad6,8" to "iPad Pro 12.9 (LTE)",
            "iPhone" to "iPhone Generic",
            "iPhone2,1" to "iPhone 3GS",
            "iPhone3,1" to "iPhone 4",
            "iPhone3,3" to "iPhone 4 (CDMA)",
            "iPhone4,1" to "iPhone 4S",
            "iPhone5,1" to "iPhone 5",
            "iPhone6,2" to "iPhone 5S",
            "iPhone7,1" to "iPhone 6 Plus",
            "iPhone8,2" to "iPhone 6S Plus",
            "iPhone9,2" to "iPhone 7 Plus",
            "iPhone10,3" to "iPhone X",
            "iPod" to "iPod Generic"
        )

        Column(modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp)) {
            Spacer(modifier = Modifier.height(64.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { currentMode = AppMode.RR3_DASHBOARD }) {
                    Text("←", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text("EXPERIMENTAL HUB", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Text("iOS / tvOS / Mac Tiers", style = MaterialTheme.typography.labelSmall, color = Color(0xFFD32F2F), fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Box(modifier = Modifier.fillMaxWidth().background(Color(0xFFD32F2F).copy(alpha = 0.1f), MaterialTheme.shapes.medium).padding(12.dp)) {
                Text(
                    "WARNING: These presets were designed for non-Android platforms. " +
                    "They may cause visual glitches, crashes, or severe performance drops. Use at your own risk.",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFFD32F2F),
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
            Column(modifier = Modifier.weight(1f).verticalScroll(scrollState)) {
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    maxItemsInEachRow = 2
                ) {
                    experimentalPresets.forEach { (id, label) ->
                        Button(
                            onClick = { startBackgroundInstall("APPLY_GRAPHICS", presetName = id) },
                            enabled = isUiIdle && isServiceBound,
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant, contentColor = MaterialTheme.colorScheme.onSurfaceVariant),
                            modifier = Modifier.weight(1f).height(56.dp),
                            contentPadding = PaddingValues(horizontal = 8.dp),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Text(label, fontSize = 11.sp, fontWeight = FontWeight.Bold, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }

    @Composable
    fun CommunityGalleryScreen(blueButtonColors: ButtonColors) {
        val scrollState = rememberScrollState()
        var selectedTab by remember { mutableIntStateOf(0) } // 0: Saves, 1: Music
        var searchQuery by remember { mutableStateOf("") }

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

        Column(modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp)) {
            Spacer(modifier = Modifier.height(64.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { isViewingCommunity = false }) {
                    Text("←", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                }
                Text(stringResource(R.string.title_community), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                IconButton(onClick = { refreshCommunityHub() }) {
                    Text("↻", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                }
            }

            // Status Indicator for Community Hub
            val progressFlow = installService?.getProgressFlow()
            val installProgress by (progressFlow ?: remember { MutableStateFlow(InstallProgress()) }).collectAsState()
            
            // AUTO-REFRESH HUB ON SUCCESS
            LaunchedEffect(installProgress.state) {
                if (installProgress.state == InstallState.SUCCESS) {
                    refreshCommunityHub()
                }
            }

            AnimatedVisibility(visible = installProgress.state != InstallState.IDLE) {
                Column {
                    Spacer(modifier = Modifier.height(16.dp))
                    InstallProgressSection(installProgress)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text(stringResource(R.string.label_search_placeholder), style = MaterialTheme.typography.bodySmall) },
                    leadingIcon = { Text("🔍", fontSize = 16.sp) },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Text("✕", fontWeight = FontWeight.Bold)
                            }
                        }
                    },
                    singleLine = true,
                    shape = MaterialTheme.shapes.medium,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                    )
                )

                Spacer(modifier = Modifier.width(8.dp))

                var showSortMenu by remember { mutableStateOf(false) }
                Box {
                    IconButton(onClick = { showSortMenu = true }) {
                        Text("⇵", fontSize = 20.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                    }
                    DropdownMenu(expanded = showSortMenu, onDismissRequest = { showSortMenu = false }) {
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.sort_newest)) },
                            onClick = { communitySortType = "newest"; showSortMenu = false; refreshCommunityHub() },
                            leadingIcon = { if (communitySortType == "newest") Text("✓") }
                        )
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.sort_popular)) },
                            onClick = { communitySortType = "popular"; showSortMenu = false; refreshCommunityHub() },
                            leadingIcon = { if (communitySortType == "popular") Text("✓") }
                        )
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.sort_likes)) },
                            onClick = { communitySortType = "likes"; showSortMenu = false; refreshCommunityHub() },
                            leadingIcon = { if (communitySortType == "likes") Text("✓") }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            
            SecondaryTabRow(selectedTabIndex = selectedTab, containerColor = Color.Transparent) {
                Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }) {
                    Text(stringResource(R.string.tab_saves), modifier = Modifier.padding(16.dp), fontWeight = FontWeight.Bold)
                }
                Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }) {
                    Text(stringResource(R.string.tab_music), modifier = Modifier.padding(16.dp), fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Column(modifier = Modifier.weight(1f).verticalScroll(scrollState)) {
                if (selectedTab == 0) {
                    if (filteredSaves.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize().padding(top = 48.dp), contentAlignment = Alignment.Center) {
                            Text(
                                if (searchQuery.isEmpty()) stringResource(R.string.label_no_saves) else stringResource(R.string.label_no_saves_match, searchQuery), 
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    } else {
                        filteredSaves.forEach { save ->
                            CommunitySaveItem(save, enabled = true, blueButtonColors)
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                } else {
                    if (filteredMusic.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize().padding(top = 48.dp), contentAlignment = Alignment.Center) {
                            Text(
                                if (searchQuery.isEmpty()) stringResource(R.string.label_no_music) else stringResource(R.string.label_no_music_match, searchQuery), 
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    } else {
                        filteredMusic.forEach { music ->
                            CommunityMusicItem(music, enabled = true, blueButtonColors)
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }
                
                // Safe zone for bottom navigation bar
                Spacer(modifier = Modifier.height(100.dp).navigationBarsPadding())
            }
        }
    }

    @Composable
    fun CommunityMusicItem(music: CommunityMusic, enabled: Boolean, blueButtonColors: ButtonColors) {
        val displayTrack = when(music.targetTrack) {
            "IntroLogos.mp3" -> "Intro Logos"
            "IntroCinematic.mp3" -> "Intro Cinematic"
            "Garage_Music.mp3" -> "Garage"
            "LoadingScreen.mp3" -> "Loading"
            "Menu_Music.mp3" -> "Main Menu"
            "Ryan_Kurt_Time.mp3" -> "Time"
            "Ryan_Kurt_Strike.mp3" -> "Strike"
            "Ryan_Kurt_Further.mp3" -> "Further"
            "Ryan_Kurt_Taplein.mp3" -> "Taplein"
            "Ryan_Kurt_Connected.mp3" -> "Connected"
            "Ryan_Kurt_Red_Green.mp3" -> "Red Green"
            "Formula_E_EJ_Ignition.mp3" -> "Ignition"
            "Formula_E_EJ_Buenos_Aires.mp3" -> "Buenos Aires"
            "Formula_E_EJ_Clear_Blue_Sky.mp3" -> "Clear Blue Sky"
            "Ryan_Kurt_Time_RICCI_Remix.mp3" -> "Time (RICCI)"
            "Ryan_Kurt_Taplein_SIPPY_Remix.mp3" -> "Taplein (SIPPY)"
            "Ryan_Kurt_Further_Corrupt_Remix.mp3" -> "Further (Corrupt)"
            else -> music.targetTrack
        }

        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
            Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(music.customName, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
                    Text("by ${music.uploaderName} • ${music.formattedSize}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
                    Text("⬇ ${music.downloads} • ❤️ ${music.likes}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                    Text(stringResource(R.string.label_recommended, displayTrack), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                }
                
                IconButton(
                    onClick = { 
                        lifecycleScope.launch { 
                            val s = service ?: return@launch
                            val installer = Retr0Installer(this@MainActivity, s)
                            if (installer.likeItem(music.id)) {
                                Toast.makeText(this@MainActivity, "Liked!", Toast.LENGTH_SHORT).show()
                                refreshCommunityHub()
                            }
                        }
                    },
                    modifier = Modifier.size(32.dp)
                ) {
                    Text("❤️", fontSize = 16.sp)
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = { startBackgroundInstall("DOWNLOAD_TO_LIBRARY", saveId = music.id, customName = music.customName) }, 
                    enabled = enabled && isServiceBound, 
                    colors = blueButtonColors, 
                    modifier = Modifier.height(32.dp)
                ) {
                    Text(stringResource(R.string.btn_get), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }

    @Composable
    fun CommunitySaveItem(save: CommunitySave, enabled: Boolean, blueButtonColors: ButtonColors) {
        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
            Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(save.customName, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
                    Text("by ${save.uploaderName} • ${save.formattedSize}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
                    Text("⬇ ${save.downloads} • ❤️ ${save.likes}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                    if (save.playerLevel > 0) {
                        Text("Lvl ${save.playerLevel} • G ${save.playerGold} • R$ ${save.playerRs} • M$ ${save.playerMs}", style = MaterialTheme.typography.labelSmall, color = Color(0xFF4CAF50), fontWeight = FontWeight.Bold)
                    }
                }

                IconButton(
                    onClick = { 
                        lifecycleScope.launch { 
                            val s = service ?: return@launch
                            val installer = Retr0Installer(this@MainActivity, s)
                            if (installer.likeItem(save.id)) {
                                Toast.makeText(this@MainActivity, "Liked!", Toast.LENGTH_SHORT).show()
                                refreshCommunityHub()
                            }
                        }
                    },
                    modifier = Modifier.size(32.dp)
                ) {
                    Text("❤️", fontSize = 16.sp)
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(onClick = { startBackgroundInstall("DOWNLOAD_COMMUNITY", saveId = save.id, customName = save.customName) }, enabled = enabled && isServiceBound, colors = blueButtonColors, modifier = Modifier.height(32.dp)) {
                    Text(stringResource(R.string.btn_get), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }

    @Composable
    fun MusicHubSection(enabled: Boolean, blueButtonColors: ButtonColors) {
        var selectedTabIndex by remember { mutableIntStateOf(0) }

        val coreTracks = listOf(
            MusicTrack("Menu_Music.mp3", "Main Menu"),
            MusicTrack("Garage_Music.mp3", "Garage Music"),
            MusicTrack("LoadingScreen.mp3", "Loading Screen")
        )

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(R.string.section_music_hub), style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
            Text(stringResource(R.string.hint_music_hub), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary, modifier = Modifier.padding(bottom = 12.dp))
            
            SecondaryTabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.primary,
                divider = {}
            ) {
                Tab(
                    selected = selectedTabIndex == 0,
                    onClick = { selectedTabIndex = 0 },
                    text = { Text(stringResource(R.string.tab_core), fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelSmall) }
                )
                Tab(
                    selected = selectedTabIndex == 1,
                    onClick = { selectedTabIndex = 1 },
                    text = { Text(stringResource(R.string.tab_extended), fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelSmall) }
                )
                Tab(
                    selected = selectedTabIndex == 2,
                    onClick = { selectedTabIndex = 2 },
                    text = { Text(stringResource(R.string.tab_library), fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelSmall) }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            when (selectedTabIndex) {
                0 -> {
                    coreTracks.forEach { track ->
                        MusicTrackItem(track, enabled, blueButtonColors)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { selectedTabIndex = 1 },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer, contentColor = MaterialTheme.colorScheme.onSecondaryContainer),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text(stringResource(R.string.btn_view_all_slots), fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelSmall)
                    }
                }
                1 -> {
                    ExtendedSoundtrackList(enabled, blueButtonColors)
                }
                2 -> {
                    MusicLibraryTab(enabled, blueButtonColors)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            YouTubeConverterSection(enabled, blueButtonColors)
        }
    }

    @Composable
    fun MusicLibraryTab(enabled: Boolean, blueButtonColors: ButtonColors) {
        var customMusicName by remember { mutableStateOf("") }
        var selectedUri by remember { mutableStateOf<Uri?>(null) }
        var showSaveDialog by remember { mutableStateOf(false) }
        var showMetadataSelector by remember { mutableStateOf(false) }
        var selectedMetadata by remember { mutableStateOf<MusicMetadata?>(null) }

        if (showMetadataSelector) {
            MetadataSelectionDialog(
                initialQuery = customMusicName,
                onMetadataSelected = { meta ->
                    selectedMetadata = meta
                    customMusicName = meta.trackName
                    showMetadataSelector = false
                },
                onDismiss = { showMetadataSelector = false }
            )
        }

        val picker = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
            uri?.let { 
                selectedUri = it
                customMusicName = it.lastPathSegment?.substringAfterLast("/")?.substringBeforeLast(".") ?: "Imported Track"
                showSaveDialog = true 
            }
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            if (libraryMusic.isEmpty()) {
                Box(modifier = Modifier.fillMaxWidth().height(100.dp), contentAlignment = Alignment.Center) {
                    Text(stringResource(R.string.label_library_empty), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary)
                }
            } else {
                // Use a non-scrollable list approach here since it's nested in a scrollable dashboard
                libraryMusic.forEach { music ->
                    LibraryMusicItem(music, enabled, blueButtonColors)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { picker.launch(arrayOf("audio/mpeg")) },
                modifier = Modifier.fillMaxWidth(),
                enabled = enabled && isServiceBound,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(stringResource(R.string.btn_import_mp3), fontWeight = FontWeight.Bold)
            }
        }

        if (showSaveDialog && selectedUri != null) {
            AlertDialog(
                onDismissRequest = { showSaveDialog = false },
                title = { Text(stringResource(R.string.dialog_import_title), fontWeight = FontWeight.Bold) },
                text = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        // PREVIEW BOX
                        Box(modifier = Modifier.size(100.dp).clip(RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant)) {
                            AsyncImage(
                                model = selectedMetadata?.artworkUrl ?: R.drawable.ic_app_icon,
                                contentDescription = "Preview",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = customMusicName, 
                            onValueChange = { customMusicName = it }, 
                            label = { Text(stringResource(R.string.label_music_name)) }, 
                            singleLine = true, 
                            modifier = Modifier.fillMaxWidth(),
                            trailingIcon = {
                                IconButton(onClick = { showMetadataSelector = true }) {
                                    Text("🔎", fontSize = 16.sp)
                                }
                            }
                        )
                    }
                },
                confirmButton = {
                    TextButton(onClick = {
                        selectedUri?.let { uri ->
                            startBackgroundInstall(
                                actionType = "SAVE_TO_LIBRARY", 
                                uri = uri, 
                                customName = customMusicName,
                                artworkUrl = selectedMetadata?.artworkUrl
                            )
                            refreshMusicLibrary()
                        }
                        showSaveDialog = false
                    }) { Text(stringResource(R.string.btn_save), fontWeight = FontWeight.Bold) }
                },
                dismissButton = {
                    TextButton(onClick = { showSaveDialog = false }) { Text(stringResource(R.string.btn_cancel)) }
                }
            )
        }
    }

    @Composable
    fun LibraryMusicItem(music: LibraryMusic, enabled: Boolean, blueButtonColors: ButtonColors) {
        var showApplyDialog by remember { mutableStateOf(false) }
        var showUploadDialog by remember { mutableStateOf(false) }

        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
            Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                // ALBUM ART (Coil)
                AsyncImage(
                    model = music.artworkUrl ?: R.drawable.ic_app_icon,
                    contentDescription = "Album Art",
                    modifier = Modifier.size(50.dp).clip(RoundedCornerShape(8.dp)).background(Color.Black.copy(alpha = 0.1f)),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(R.drawable.ic_app_icon)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(music.trackName ?: music.fileName, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
                    Text(music.artistName ?: "Unknown Artist", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
                    Text(music.formattedSize, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.outline)
                }
                
                Button(onClick = { showApplyDialog = true }, enabled = enabled && isServiceBound, colors = blueButtonColors, modifier = Modifier.height(32.dp)) {
                    Text(stringResource(R.string.btn_use), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = { showUploadDialog = true }, enabled = enabled && isServiceBound, modifier = Modifier.size(32.dp)) {
                    Text("↑", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
                Spacer(modifier = Modifier.width(4.dp))
                IconButton(onClick = { 
                    lifecycleScope.launch { 
                        val currentService = service ?: return@launch
                        if (Retr0Installer(this@MainActivity, currentService).deleteLibraryMusic(music.fileName)) {
                            refreshMusicLibrary()
                        }
                    }
                }, enabled = enabled && isServiceBound, modifier = Modifier.size(32.dp)) {
                    Text("✕", color = Color.Red, fontWeight = FontWeight.Bold)
                }
            }
        }

        if (showApplyDialog) {
            ApplyFromLibraryDialog(
                libraryMusic = music,
                onDismiss = { showApplyDialog = false }
            )
        }

        if (showUploadDialog) {
            UploadFromLibraryDialog(
                libraryMusic = music,
                onDismiss = { showUploadDialog = false }
            )
        }
    }

    @Composable
    fun UploadFromLibraryDialog(libraryMusic: LibraryMusic, onDismiss: () -> Unit) {
        var uploaderName by remember { mutableStateOf("") }
        var customName by remember { mutableStateOf(libraryMusic.fileName.replace(".mp3", "")) }
        var selectedTrack by remember { mutableStateOf("Garage_Music.mp3") }

        val tracks = listOf(
            "Menu_Music.mp3" to "Main Menu",
            "Garage_Music.mp3" to "Garage Music",
            "LoadingScreen.mp3" to "Loading Screen",
            "Ryan_Kurt_Time.mp3" to "Ryan Kurt - Time",
            "Ryan_Kurt_Strike.mp3" to "Ryan Kurt - Strike",
            "Ryan_Kurt_Further.mp3" to "Ryan Kurt - Further",
            "Ryan_Kurt_Taplein.mp3" to "Ryan Kurt - Taplein",
            "Ryan_Kurt_Connected.mp3" to "Ryan Kurt - Connected",
            "Ryan_Kurt_Red_Green.mp3" to "Ryan Kurt - Red Green",
            "Formula_E_EJ_Ignition.mp3" to "Formula E - Ignition",
            "Formula_E_EJ_Buenos_Aires.mp3" to "Formula E - Buenos Aires",
            "Formula_E_EJ_Clear_Blue_Sky.mp3" to "Formula E - Clear Blue Sky",
            "Ryan_Kurt_Time_RICCI_Remix.mp3" to "Time (RICCI Remix)",
            "Ryan_Kurt_Taplein_SIPPY_Remix.mp3" to "Taplein (SIPPY Remix)",
            "Ryan_Kurt_Further_Corrupt_Remix.mp3" to "Further (Corrupt Remix)"
        )

        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(stringResource(R.string.dialog_share_title), fontWeight = FontWeight.Bold) },
            text = {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    Text(stringResource(R.string.dialog_share_desc), style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(value = customName, onValueChange = { customName = it }, label = { Text(stringResource(R.string.label_music_name)) }, singleLine = true, modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(value = uploaderName, onValueChange = { uploaderName = it }, label = { Text(stringResource(R.string.label_uploader_name)) }, singleLine = true, modifier = Modifier.fillMaxWidth())
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(stringResource(R.string.label_target_slot), style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                    tracks.forEach { (file, label) ->
                        Row(
                            modifier = Modifier.fillMaxWidth().clickable { selectedTrack = file }.padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(selected = selectedTrack == file, onClick = { selectedTrack = file })
                            Text(label, modifier = Modifier.padding(start = 8.dp), style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    startBackgroundInstall(
                        actionType = "UPLOAD_MUSIC",
                        uri = Uri.fromFile(File(libraryMusic.absolutePath)),
                        uploaderName = uploaderName,
                        customName = customName,
                        targetTrack = selectedTrack
                    )
                    onDismiss()
                }, enabled = uploaderName.isNotBlank() && customName.isNotBlank()) { 
                    Text(stringResource(R.string.btn_upload), fontWeight = FontWeight.Bold) 
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) { Text(stringResource(R.string.btn_cancel)) }
            }
        )
    }

    @Composable
    fun ApplyFromLibraryDialog(libraryMusic: LibraryMusic, onDismiss: () -> Unit) {
        var selectedTrack by remember { mutableStateOf("Garage_Music.mp3") }
        
        val tracks = listOf(
            "Menu_Music.mp3" to "Main Menu",
            "Garage_Music.mp3" to "Garage Music",
            "LoadingScreen.mp3" to "Loading Screen",
            "Ryan_Kurt_Time.mp3" to "Ryan Kurt - Time",
            "Ryan_Kurt_Strike.mp3" to "Ryan Kurt - Strike",
            "Ryan_Kurt_Further.mp3" to "Ryan Kurt - Further",
            "Ryan_Kurt_Taplein.mp3" to "Ryan Kurt - Taplein",
            "Ryan_Kurt_Connected.mp3" to "Ryan Kurt - Connected",
            "Ryan_Kurt_Red_Green.mp3" to "Ryan Kurt - Red Green",
            "Formula_E_EJ_Ignition.mp3" to "Formula E - Ignition",
            "Formula_E_EJ_Buenos_Aires.mp3" to "Formula E - Buenos Aires",
            "Formula_E_EJ_Clear_Blue_Sky.mp3" to "Formula E - Clear Blue Sky",
            "Ryan_Kurt_Time_RICCI_Remix.mp3" to "Time (RICCI Remix)",
            "Ryan_Kurt_Taplein_SIPPY_Remix.mp3" to "Taplein (SIPPY Remix)",
            "Ryan_Kurt_Further_Corrupt_Remix.mp3" to "Further (Corrupt Remix)"
        )

        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(stringResource(R.string.dialog_replace_title), fontWeight = FontWeight.Bold) },
            text = {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    Text(stringResource(R.string.dialog_replace_desc, libraryMusic.fileName), style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(12.dp))
                    tracks.forEach { (file, label) ->
                        Row(
                            modifier = Modifier.fillMaxWidth().clickable { selectedTrack = file }.padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(selected = selectedTrack == file, onClick = { selectedTrack = file })
                            Text(label, modifier = Modifier.padding(start = 8.dp), style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    startBackgroundInstall(
                        actionType = "APPLY_FROM_LIBRARY",
                        trackFileName = libraryMusic.fileName,
                        targetTrack = selectedTrack
                    )
                    onDismiss()
                }) { Text(stringResource(R.string.btn_apply), fontWeight = FontWeight.Bold) }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) { Text(stringResource(R.string.btn_cancel)) }
            }
        )
    }

    @Composable
    fun ExtendedSoundtrackList(enabled: Boolean, blueButtonColors: ButtonColors) {
        val introTracks = listOf(
            MusicTrack("IntroLogos.mp3", "Intro Logos"),
            MusicTrack("IntroCinematic.mp3", "Intro Cinematic")
        )
        
        val raceTracks = listOf(
            MusicTrack("Ryan_Kurt_Time.mp3", "Ryan Kurt - Time"),
            MusicTrack("Ryan_Kurt_Strike.mp3", "Ryan Kurt - Strike"),
            MusicTrack("Ryan_Kurt_Further.mp3", "Ryan Kurt - Further"),
            MusicTrack("Ryan_Kurt_Taplein.mp3", "Ryan Kurt - Taplein"),
            MusicTrack("Ryan_Kurt_Connected.mp3", "Ryan Kurt - Connected"),
            MusicTrack("Ryan_Kurt_Red_Green.mp3", "Ryan Kurt - Red Green")
        )
        
        val motorsportTracks = listOf(
            MusicTrack("Formula_E_EJ_Ignition.mp3", "Formula E - Ignition"),
            MusicTrack("Formula_E_EJ_Buenos_Aires.mp3", "Formula E - Buenos Aires"),
            MusicTrack("Formula_E_EJ_Clear_Blue_Sky.mp3", "Formula E - Clear Blue Sky")
        )
        
        val remixTracks = listOf(
            MusicTrack("Ryan_Kurt_Time_RICCI_Remix.mp3", "Time (RICCI Remix)"),
            MusicTrack("Ryan_Kurt_Taplein_SIPPY_Remix.mp3", "Taplein (SIPPY Remix)"),
            MusicTrack("Ryan_Kurt_Further_Corrupt_Remix.mp3", "Further (Corrupt Remix)")
        )

        Column {
            Text(stringResource(R.string.tab_extended), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(vertical = 8.dp))
            introTracks.forEach { track ->
                MusicTrackItem(track, enabled, blueButtonColors)
                Spacer(modifier = Modifier.height(8.dp))
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            Text(stringResource(R.string.tab_music), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(vertical = 8.dp))
            raceTracks.forEach { track ->
                MusicTrackItem(track, enabled, blueButtonColors)
                Spacer(modifier = Modifier.height(8.dp))
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            Text(stringResource(R.string.section_graphics_hub), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(vertical = 8.dp))
            motorsportTracks.forEach { track ->
                MusicTrackItem(track, enabled, blueButtonColors)
                Spacer(modifier = Modifier.height(8.dp))
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            Text(stringResource(R.string.tab_extended), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(vertical = 8.dp))
            remixTracks.forEach { track ->
                MusicTrackItem(track, enabled, blueButtonColors)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }

    @Composable
    fun YouTubeConverterSection(enabled: Boolean, blueButtonColors: ButtonColors) {
        var youtubeUrl by remember { mutableStateOf("") }
        var showReplaceDialog by remember { mutableStateOf(false) }
        var fetchedInfo by remember { mutableStateOf<YouTubeInfo?>(null) }
        var isFetching by remember { mutableStateOf(false) }

        // Fetch metadata automatically when URL is pasted
        LaunchedEffect(youtubeUrl) {
            if (youtubeUrl.length > 10 && (youtubeUrl.contains("youtube.com") || youtubeUrl.contains("youtu.be"))) {
                isFetching = true
                val s = service ?: return@LaunchedEffect
                fetchedInfo = Retr0Installer(this@MainActivity, s).fetchYouTubeInfo(youtubeUrl)
                isFetching = false
            } else {
                fetchedInfo = null
            }
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(R.string.section_youtube_converter), style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
            Text(stringResource(R.string.hint_youtube_converter), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary, modifier = Modifier.padding(bottom = 12.dp))

            OutlinedTextField(
                value = youtubeUrl,
                onValueChange = { youtubeUrl = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(stringResource(R.string.label_youtube_placeholder), style = MaterialTheme.typography.bodySmall) },
                singleLine = true,
                shape = MaterialTheme.shapes.medium,
                trailingIcon = { if (isFetching) CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { showReplaceDialog = true },
                modifier = Modifier.fillMaxWidth(),
                enabled = enabled && isServiceBound && youtubeUrl.isNotBlank() && !isFetching,
                colors = blueButtonColors
            ) {
                Text(stringResource(R.string.btn_convert_to_library), fontWeight = FontWeight.Bold)
            }
        }

        if (showReplaceDialog) {
            YouTubeReplaceDialog(
                youtubeUrl = youtubeUrl,
                initialInfo = fetchedInfo,
                onDismiss = { showReplaceDialog = false },
                onConfirm = { youtubeUrl = "" }
            )
        }
    }

    @Composable
    fun YouTubeReplaceDialog(
        youtubeUrl: String,
        initialInfo: YouTubeInfo?,
        onDismiss: () -> Unit,
        onConfirm: () -> Unit
    ) {
        var musicName by remember { mutableStateOf(initialInfo?.title ?: "") }
        var showMetadataSelector by remember { mutableStateOf(false) }
        var selectedMetadata by remember { mutableStateOf<MusicMetadata?>(null) }

        if (showMetadataSelector) {
            MetadataSelectionDialog(
                initialQuery = musicName,
                onMetadataSelected = { meta ->
                    selectedMetadata = meta
                    musicName = meta.trackName
                    showMetadataSelector = false
                },
                onDismiss = { showMetadataSelector = false }
            )
        }

        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(stringResource(R.string.dialog_youtube_title), fontWeight = FontWeight.Bold) },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    // PREVIEW BOX
                    Box(modifier = Modifier.size(120.dp).clip(RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant)) {
                        AsyncImage(
                            model = selectedMetadata?.artworkUrl ?: initialInfo?.thumbnailUrl ?: R.drawable.ic_app_icon,
                            contentDescription = "Preview",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(stringResource(R.string.dialog_youtube_desc), style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = musicName, 
                        onValueChange = { musicName = it }, 
                        label = { Text(stringResource(R.string.label_music_name)) }, 
                        singleLine = true, 
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = {
                            IconButton(onClick = { showMetadataSelector = true }) {
                                Text("🔎", fontSize = 16.sp)
                            }
                        }
                    )
                    if (selectedMetadata != null) {
                        Text("Matched: ${selectedMetadata?.trackName} by ${selectedMetadata?.artistName}", style = MaterialTheme.typography.labelSmall, color = Color(0xFF4CAF50), modifier = Modifier.padding(top = 4.dp))
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        startBackgroundInstall(
                            actionType = "YOUTUBE_TO_LIBRARY",
                            youtubeUrl = youtubeUrl,
                            customName = musicName.ifBlank { "YouTube Music" },
                            artworkUrl = selectedMetadata?.artworkUrl // Pass the high-res art
                        )
                        onConfirm()
                        onDismiss()
                    },
                    enabled = musicName.isNotBlank()
                ) { 
                    Text(stringResource(R.string.btn_save), fontWeight = FontWeight.Bold) 
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) { Text(stringResource(R.string.btn_cancel)) }
            }
        )
    }

    @Composable
    fun MetadataSelectionDialog(
        initialQuery: String,
        onMetadataSelected: (MusicMetadata) -> Unit,
        onDismiss: () -> Unit
    ) {
        var searchQuery by remember { mutableStateOf(initialQuery) }
        var results by remember { mutableStateOf<List<MusicMetadata>>(emptyList()) }
        var isSearching by remember { mutableStateOf(false) }

        // Initial search
        LaunchedEffect(Unit) {
            isSearching = true
            results = MetadataFetcher.searchMetadata(searchQuery)
            isSearching = false
        }

        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Match Music Profile", fontWeight = FontWeight.Bold) },
            text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        label = { Text("Search for official match") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        trailingIcon = {
                            if (isSearching) CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp)
                            else IconButton(onClick = { 
                                lifecycleScope.launch {
                                    isSearching = true
                                    results = MetadataFetcher.searchMetadata(searchQuery)
                                    isSearching = false
                                }
                            }) { Text("➜") }
                        }
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    if (results.isEmpty() && !isSearching) {
                        Text("No results found. Try a different name.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary)
                    }

                    Column(modifier = Modifier.heightIn(max = 300.dp).verticalScroll(rememberScrollState())) {
                        results.forEach { meta ->
                            Row(
                                modifier = Modifier.fillMaxWidth().clickable { onMetadataSelected(meta) }.padding(vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AsyncImage(
                                    model = meta.artworkUrl,
                                    contentDescription = null,
                                    modifier = Modifier.size(50.dp).clip(RoundedCornerShape(8.dp)),
                                    contentScale = ContentScale.Crop,
                                    placeholder = painterResource(R.drawable.ic_app_icon)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(meta.trackName, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodySmall)
                                    Text(meta.artistName, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
                                    if (meta.collectionName != null) {
                                        Text(meta.collectionName, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.outline)
                                    }
                                }
                            }
                            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = onDismiss) { Text("CLOSE") }
            }
        )
    }

    @Composable
    fun MusicTrackItem(track: MusicTrack, enabled: Boolean, blueButtonColors: ButtonColors) {
        var showMusicShareDialog by remember { mutableStateOf(false) }
        var uploaderName by remember { mutableStateOf("") }
        var customMusicName by remember { mutableStateOf("") }
        var selectedUri by remember { mutableStateOf<Uri?>(null) }
        var showMetadataSelector by remember { mutableStateOf(false) }
        var selectedMetadata by remember { mutableStateOf<MusicMetadata?>(null) }

        if (showMetadataSelector) {
            MetadataSelectionDialog(
                initialQuery = customMusicName,
                onMetadataSelected = { meta ->
                    selectedMetadata = meta
                    customMusicName = meta.trackName
                    showMetadataSelector = false
                },
                onDismiss = { showMetadataSelector = false }
            )
        }

        val picker = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
            uri?.let { 
                // Take persistable URI permission for background tasks
                try {
                    contentResolver.takePersistableUriPermission(it, Intent.FLAG_GRANT_READ_URI_PERMISSION)
                } catch (e: Exception) {
                    Log.e("RETR0_UI", "Failed to take persistable URI permission", e)
                }

                selectedUri = it
                customMusicName = it.lastPathSegment?.substringAfterLast("/")?.substringBeforeLast(".") ?: "Imported Track"
                showMusicShareDialog = true 
            }
        }

        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
            Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(track.displayName, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
                    Text(track.fileName, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
                }
                
                Button(onClick = { picker.launch(arrayOf("audio/mpeg")) }, enabled = enabled && isServiceBound, colors = blueButtonColors, modifier = Modifier.height(32.dp)) {
                    Text(stringResource(R.string.btn_swap), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = { startBackgroundInstall("MUSIC_RESTORE", trackFileName = track.fileName) }, enabled = enabled && isServiceBound, modifier = Modifier.size(32.dp)) {
                    Text("↺", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }

        if (showMusicShareDialog && selectedUri != null) {
            AlertDialog(
                onDismissRequest = { showMusicShareDialog = false },
                title = { Text(stringResource(R.string.dialog_apply_share_title), fontWeight = FontWeight.Bold) },
                text = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        // PREVIEW BOX
                        Box(modifier = Modifier.size(100.dp).clip(RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant)) {
                            AsyncImage(
                                model = selectedMetadata?.artworkUrl ?: R.drawable.ic_app_icon,
                                contentDescription = "Preview",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = customMusicName, 
                            onValueChange = { customMusicName = it }, 
                            label = { Text(stringResource(R.string.label_music_name)) }, 
                            singleLine = true, 
                            modifier = Modifier.fillMaxWidth(),
                            trailingIcon = {
                                IconButton(onClick = { showMetadataSelector = true }) {
                                    Text("🔎", fontSize = 16.sp)
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(value = uploaderName, onValueChange = { uploaderName = it }, label = { Text(stringResource(R.string.label_your_name_optional)) }, singleLine = true, modifier = Modifier.fillMaxWidth())
                    }
                },
                confirmButton = {
                    Column {
                        Button(onClick = {
                            selectedUri?.let { uri ->
                                startBackgroundInstall(
                                    actionType = "SAVE_TO_LIBRARY",
                                    uri = uri,
                                    customName = customMusicName,
                                    artworkUrl = selectedMetadata?.artworkUrl
                                )
                                refreshMusicLibrary()
                            }
                            showMusicShareDialog = false
                        }, modifier = Modifier.fillMaxWidth()) { Text(stringResource(R.string.btn_save), fontWeight = FontWeight.Bold) }

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(onClick = {
                            selectedUri?.let { uri ->
                                startBackgroundInstall(
                                    actionType = "APPLY_AND_UPLOAD_MUSIC",
                                    uri = uri,
                                    targetTrack = track.fileName,
                                    uploaderName = uploaderName.ifBlank { "Anonymous" },
                                    customName = customMusicName,
                                    shareHub = uploaderName.isNotBlank(),
                                    artworkUrl = selectedMetadata?.artworkUrl
                                )
                                showMusicShareDialog = false
                            }
                        }, modifier = Modifier.fillMaxWidth()) { Text(stringResource(R.string.btn_apply), fontWeight = FontWeight.Bold) }
                    }
                },
                dismissButton = {
                    TextButton(onClick = { 
                        showMusicShareDialog = false 
                    }) { Text(stringResource(R.string.btn_cancel)) }
                }
            )
        }
    }

    @Composable
    fun GraphicsHubSection(enabled: Boolean, blueButtonColors: ButtonColors) {
        val presets = listOf(
            "AndroidLow" to stringResource(R.string.tier_low),
            "AndroidMid" to stringResource(R.string.tier_medium),
            "AndroidMid2" to stringResource(R.string.tier_medium2),
            "AndroidHigh" to stringResource(R.string.tier_high),
            "AndroidXtraHigh" to stringResource(R.string.tier_xtra_high),
            "AndroidUltraWide" to stringResource(R.string.tier_ultra_wide),
            "SimRigTV" to stringResource(R.string.tier_simrig)
        )

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(R.string.section_graphics_hub), style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
            Text(stringResource(R.string.hint_graphics_hub), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary, modifier = Modifier.padding(bottom = 12.dp))
            
            // Grid of buttons
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                maxItemsInEachRow = 2
            ) {
                presets.forEach { (id, label) ->
                    Button(
                        onClick = { startBackgroundInstall("APPLY_GRAPHICS", presetName = id) },
                        enabled = enabled && isServiceBound,
                        colors = blueButtonColors,
                        modifier = Modifier.weight(1f).height(56.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text(label, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedButton(
                onClick = { startBackgroundInstall("RESTORE_GRAPHICS") },
                modifier = Modifier.fillMaxWidth().height(48.dp),
                enabled = enabled && isServiceBound,
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(stringResource(R.string.btn_restore_graphics), fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { currentMode = AppMode.EXPERIMENTAL_GRAPHICS },
                modifier = Modifier.fillMaxWidth().height(48.dp),
                enabled = enabled && isServiceBound,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(stringResource(R.string.btn_experimental_graphics), fontWeight = FontWeight.Bold)
            }
        }
    }

    @Composable
    fun SaveHubSection(enabled: Boolean, blueButtonColors: ButtonColors) {
        var showBackupDialog by remember { mutableStateOf(value = false) }
        var showUpdateDialog by remember { mutableStateOf(value = false) }
        var backupName by remember { mutableStateOf("") }

        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(stringResource(R.string.section_save_hub), style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(stringResource(R.string.label_auto_snapshot), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
                    Spacer(modifier = Modifier.width(8.dp))
                    Switch(
                        checked = isSmartBackupEnabled,
                        onCheckedChange = { toggleSmartBackup(it) },
                        modifier = Modifier.scale(0.7f),
                        enabled = enabled
                    )
                }
            }
            Text(stringResource(R.string.label_last_run, lastBackupStatus), style = MaterialTheme.typography.labelSmall, color = if (lastBackupStatus.startsWith("Success")) Color(0xFF4CAF50) else MaterialTheme.colorScheme.secondary)
            Spacer(modifier = Modifier.height(4.dp))
            Text(stringResource(R.string.hint_save_hub), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary, modifier = Modifier.padding(bottom = 8.dp))
            if (saveProfiles.isEmpty()) {
                Text(stringResource(R.string.label_no_snapshots), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.outline, modifier = Modifier.padding(vertical = 8.dp))
            } else {
                saveProfiles.forEach { profile ->
                    SaveProfileItem(profile, enabled, blueButtonColors)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { backupName = "Snapshot ${saveProfiles.size + 1}"; showBackupDialog = true }, modifier = Modifier.fillMaxWidth(), enabled = enabled && isServiceBound, colors = blueButtonColors) {
                Text(stringResource(R.string.btn_backup), fontWeight = FontWeight.Bold)
            }
            if (saveProfiles.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedButton(
                    onClick = { showUpdateDialog = true },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = enabled && isServiceBound,
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
                ) {
                    Text(stringResource(R.string.btn_update_snapshot), fontWeight = FontWeight.Bold)
                }
            }
        }

        if (showBackupDialog) {
            AlertDialog(
                onDismissRequest = { showBackupDialog = false }, 
                title = { Text(stringResource(R.string.dialog_backup_title)) }, 
                text = {
                    Column {
                        Text(stringResource(R.string.dialog_backup_desc))
                        Spacer(modifier = Modifier.height(12.dp))
                        OutlinedTextField(value = backupName, onValueChange = { backupName = it }, label = { Text(stringResource(R.string.dialog_rename_title)) }, singleLine = true, modifier = Modifier.fillMaxWidth())
                    }
                }, 
                confirmButton = {
                    TextButton(onClick = { if (backupName.isNotBlank()) { startBackgroundInstall("BACKUP", profileName = backupName); showBackupDialog = false; backupName = "" } }) { Text(stringResource(R.string.btn_backup), fontWeight = FontWeight.Bold) }
                }, 
                dismissButton = { 
                    TextButton(onClick = { showBackupDialog = false }) { 
                        Text(stringResource(R.string.btn_cancel)) 
                    } 
                },
            )
        }

        if (showUpdateDialog) {
            AlertDialog(
                onDismissRequest = { showUpdateDialog = false },
                title = { Text(stringResource(R.string.dialog_update_snapshot_title)) },
                text = {
                    Column(modifier = Modifier.heightIn(max = 300.dp).verticalScroll(rememberScrollState())) {
                        Text(stringResource(R.string.dialog_update_snapshot_desc))
                        Spacer(modifier = Modifier.height(16.dp))
                        saveProfiles.forEach { profile ->
                            Card(
                                onClick = { 
                                    startBackgroundInstall("BACKUP", profileName = profile.name)
                                    showUpdateDialog = false
                                },
                                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                            ) {
                                Text(profile.name, modifier = Modifier.padding(16.dp), fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                },
                confirmButton = {},
                dismissButton = {
                    TextButton(onClick = { showUpdateDialog = false }) { Text(stringResource(R.string.btn_cancel)) }
                }
            )
        }
    }

    @Composable
    fun SaveProfileItem(profile: SaveProfile, enabled: Boolean, blueButtonColors: ButtonColors) {
        var showRenameDialog by remember { mutableStateOf(value = false) }
        var showShareDialog by remember { mutableStateOf(value = false) }
        var newName by remember { mutableStateOf(profile.name) }
        var uploaderName by remember { mutableStateOf("") }
        var manualLevel by remember { mutableStateOf("") }
        var manualGold by remember { mutableStateOf("") }
        var manualRS by remember { mutableStateOf("") }
        var manualMS by remember { mutableStateOf("") }

        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
            Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(profile.name, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
                    Text("${stringResource(R.string.label_snapshot)} • ${profile.formattedSize}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
                }
                Button(onClick = { startBackgroundInstall("RESTORE", profileName = profile.name) }, enabled = enabled && isServiceBound, colors = blueButtonColors, modifier = Modifier.height(32.dp)) {
                    Text(stringResource(R.string.btn_use), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.width(4.dp))
                IconButton(onClick = { showShareDialog = true }, enabled = enabled, modifier = Modifier.size(32.dp)) { Text("↑", color = Color(0xFF4CAF50), fontWeight = FontWeight.Bold) }
                IconButton(onClick = { showRenameDialog = true }, enabled = enabled, modifier = Modifier.size(32.dp)) { Text("✎", color = Color.White, fontWeight = FontWeight.Bold) }
                IconButton(onClick = { lifecycleScope.launch { val currentService = service ?: return@launch; Retr0Installer(this@MainActivity, currentService).deleteProfile(profile); refreshSaveHub() } }, enabled = enabled, modifier = Modifier.size(32.dp)) { Text("✕", color = Color.Red, fontWeight = FontWeight.Bold) }
            }
        }

        if (showShareDialog) {
            AlertDialog(
                onDismissRequest = { showShareDialog = false }, 
                title = { Text(stringResource(R.string.dialog_share_title)) }, 
                text = {
                    Column {
                        Text(stringResource(R.string.dialog_share_desc))
                        Spacer(modifier = Modifier.height(12.dp))
                        OutlinedTextField(value = uploaderName, onValueChange = { uploaderName = it }, label = { Text(stringResource(R.string.label_uploader_name)) }, singleLine = true, modifier = Modifier.fillMaxWidth())
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(modifier = Modifier.fillMaxWidth()) {
                            OutlinedTextField(
                                value = manualLevel, 
                                onValueChange = { if (it.all { char -> char.isDigit() }) manualLevel = it }, 
                                label = { Text(stringResource(R.string.label_level)) }, 
                                singleLine = true, 
                                modifier = Modifier.weight(1f),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            OutlinedTextField(
                                value = manualGold, 
                                onValueChange = { if (it.all { char -> char.isDigit() }) manualGold = it }, 
                                label = { Text(stringResource(R.string.label_gold)) }, 
                                singleLine = true, 
                                modifier = Modifier.weight(1f),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(modifier = Modifier.fillMaxWidth()) {
                            OutlinedTextField(
                                value = manualRS, 
                                onValueChange = { if (it.all { char -> char.isDigit() }) manualRS = it }, 
                                label = { Text(stringResource(R.string.label_rs)) }, 
                                singleLine = true, 
                                modifier = Modifier.weight(1f),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            OutlinedTextField(
                                value = manualMS, 
                                onValueChange = { if (it.all { char -> char.isDigit() }) manualMS = it }, 
                                label = { Text(stringResource(R.string.label_ms)) }, 
                                singleLine = true, 
                                modifier = Modifier.weight(1f),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                        }
                    }
                }, 
                confirmButton = {
                    TextButton(
                        onClick = {
                            val name = uploaderName.ifBlank { "Anonymous" }
                            startBackgroundInstall("UPLOAD", profileName = profile.name, uploaderName = name, manualLevel = manualLevel, manualGold = manualGold, manualRS = manualRS, manualMS = manualMS)
                            showShareDialog = false
                        },
                    ) { Text(stringResource(R.string.btn_share), fontWeight = FontWeight.Bold) }
                }, 
                dismissButton = { 
                    TextButton(onClick = { showShareDialog = false }) { 
                        Text(stringResource(R.string.btn_cancel)) 
                    } 
                },
            )
        }

        if (showRenameDialog) {
            AlertDialog(
                onDismissRequest = { showRenameDialog = false }, 
                title = { Text(stringResource(R.string.dialog_rename_title)) }, 
                text = {
                    OutlinedTextField(value = newName, onValueChange = { newName = it }, singleLine = true, modifier = Modifier.fillMaxWidth(), label = { Text(stringResource(R.string.label_rename)) })
                }, 
                confirmButton = {
                    TextButton(
                        onClick = { 
                            if ((newName.isNotBlank()) && (newName != profile.name)) { 
                                lifecycleScope.launch { 
                                    val currentService = service ?: return@launch
                                    if (Retr0Installer(this@MainActivity, currentService).renameProfile(profile, newName)) { 
                                        refreshSaveHub()
                                        showRenameDialog = false 
                                    } 
                                } 
                            } 
                        },
                    ) { Text(stringResource(R.string.btn_rename), fontWeight = FontWeight.Bold) }
                }, 
                dismissButton = { 
                    TextButton(onClick = { showRenameDialog = false }) { 
                        Text(stringResource(R.string.btn_cancel)) 
                    } 
                },
            )
        }
    }

    @Composable
    fun SaveManagementSection(enabled: Boolean, blueButtonColors: ButtonColors) {
        val characterPicker = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
            uri?.let { 
                val fileName = contentResolver.query(it, null, null, null, null)?.use { cursor ->
                    val nameIndex = cursor.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME)
                    if (cursor.moveToFirst()) cursor.getString(nameIndex) else null
                }
                if (fileName?.endsWith(".dat", ignoreCase = true) == true) startBackgroundInstall("CHARACTER", it)
                else Toast.makeText(this@MainActivity, "Select a .dat file.", Toast.LENGTH_LONG).show()
            }
        }
        val docPicker = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocumentTree()) { uri ->
            uri?.let { startBackgroundInstall("DOC", it) }
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(R.string.section_manual_import), style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
            Text(stringResource(R.string.hint_manual_import), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary, modifier = Modifier.padding(bottom = 8.dp))
            Button(onClick = { characterPicker.launch(arrayOf("application/octet-stream")) }, modifier = Modifier.fillMaxWidth(), enabled = enabled && isServiceBound, colors = blueButtonColors) {
                Text(stringResource(R.string.btn_import_character), fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = { docPicker.launch(null) }, modifier = Modifier.fillMaxWidth(), enabled = enabled && isServiceBound, colors = blueButtonColors) {
                Text(stringResource(R.string.btn_import_doc), fontWeight = FontWeight.Bold)
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
        manualLevel: String? = null,
        manualGold: String? = null,
        manualRS: String? = null,
        manualMS: String? = null,
        youtubeUrl: String? = null,
        shareHub: Boolean = false,
        artworkUrl: String? = null
    ) {
        service?.let { binder ->
            val intent = Intent(this, Retr0InstallService::class.java).apply {
                action = "START_$actionType"
                putExtra("PROFILE_NAME", profileName)
                putExtra("UPLOADER_NAME", uploaderName)
                putExtra("SAVE_ID", saveId)
                putExtra("CUSTOM_NAME", customName)
                putExtra("TRACK_FILENAME", trackFileName)
                putExtra("TARGET_TRACK", targetTrack)
                putExtra("PRESET_NAME", presetName)
                putExtra("MANUAL_LEVEL", manualLevel?.toIntOrNull() ?: 0)
                putExtra("MANUAL_GOLD", manualGold?.toLongOrNull() ?: 0L)
                putExtra("MANUAL_RS", manualRS?.toLongOrNull() ?: 0L)
                putExtra("MANUAL_MS", manualMS?.toLongOrNull() ?: 0L)
                putExtra("YOUTUBE_URL", youtubeUrl)
                putExtra("SHARE_HUB", shareHub)
                putExtra("ARTWORK_URL", artworkUrl)
                
                val bundle = Bundle()
                androidx.core.app.BundleCompat.putBinder(bundle, "USER_SERVICE_BINDER", binder.asBinder())
                putExtras(bundle)
                uri?.let { data = it; addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) }
            }
            startForegroundService(intent)
            bindService(intent, installServiceConnection, BIND_AUTO_CREATE)
        }
    }

    @Composable
    fun ActionButtons(progress: InstallProgress, isUiIdle: Boolean, blueButtonColors: ButtonColors) {
        Column(modifier = Modifier.fillMaxWidth()) {
            if (!isShizukuRunning || !isPermissionGranted) {
                Button(onClick = { requestShizukuPermission() }, modifier = Modifier.fillMaxWidth(), colors = blueButtonColors) {
                    Text(if (!isShizukuRunning) stringResource(R.string.btn_start_shizuku) else stringResource(R.string.btn_request_shizuku), fontWeight = FontWeight.Bold)
                }
            } else if (!isServiceBound) {
                Button(onClick = { bindRetr0Service() }, modifier = Modifier.fillMaxWidth(), colors = blueButtonColors) {
                    Text(stringResource(R.string.btn_connect_service), fontWeight = FontWeight.Bold)
                }
            } else {
                Button(
                    onClick = { 
                        service?.let { binder ->
                            val intent = Intent(this@MainActivity, Retr0InstallService::class.java).apply {
                                action = "START_INSTALL"
                                val bundle = Bundle()
                                androidx.core.app.BundleCompat.putBinder(bundle, "USER_SERVICE_BINDER", binder.asBinder())
                                putExtras(bundle)
                            }
                            startForegroundService(intent)
                            bindService(intent, installServiceConnection, BIND_AUTO_CREATE)
                        }
                    }, 
                    modifier = Modifier.fillMaxWidth(), 
                    enabled = isUiIdle && isServiceBound, 
                    colors = blueButtonColors,
                ) {
                    Text(stringResource(R.string.btn_install), fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = { 
                        service?.let { binder ->
                            val intent = Intent(this@MainActivity, Retr0InstallService::class.java).apply {
                                action = "START_SAFE_TEST"
                                val bundle = Bundle()
                                androidx.core.app.BundleCompat.putBinder(bundle, "USER_SERVICE_BINDER", binder.asBinder())
                                putExtras(bundle)
                            }
                            startForegroundService(intent)
                            bindService(intent, installServiceConnection, BIND_AUTO_CREATE)
                        }
                    }, 
                    modifier = Modifier.fillMaxWidth(), 
                    enabled = isUiIdle && isServiceBound, 
                    colors = blueButtonColors,
                ) {
                    Text(stringResource(R.string.btn_safe_test), fontWeight = FontWeight.Bold)
                }
                if (!isUiIdle) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(stringResource(R.string.install_running_bg), style = MaterialTheme.typography.labelSmall, modifier = Modifier.align(Alignment.CenterHorizontally))
                }
                if ((progress.state == InstallState.SUCCESS) || (progress.state == InstallState.FAILED)) {
                    Spacer(modifier = Modifier.height(8.dp))
                    if (progress.state == InstallState.SUCCESS) {
                        Button(
                            onClick = { 
                                val rowPackage = "com.ea.games.r3_row"
                                val naPackage = "com.ea.games.r3_na"
                                
                                lifecycleScope.launch {
                                    val currentService = service ?: return@launch
                                    
                                    // 1. Force stop the game if it's running (tried for both ROW and NA)
                                    try {
                                        currentService.forceStopPackage(rowPackage)
                                        currentService.forceStopPackage(naPackage)
                                    } catch (_: Exception) {}

                                    // 2. Delay to ensure process is fully killed by the OS
                                    kotlinx.coroutines.delay(kotlin.time.Duration.parse("1000ms"))

                                    // 3. Launch the game
                                    val launchIntent = packageManager.getLaunchIntentForPackage(rowPackage) ?: packageManager.getLaunchIntentForPackage(naPackage)
                                    if (launchIntent != null) { 
                                        launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                        startActivity(launchIntent) 
                                    } else {
                                        Toast.makeText(this@MainActivity, "Game not found.", Toast.LENGTH_LONG).show()
                                    }
                                }
                            }, 
                            modifier = Modifier.fillMaxWidth(), 
                            colors = blueButtonColors,
                        ) {
                            Text(stringResource(R.string.btn_launch_game), fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                    Button(
                        onClick = { 
                            val intent = Intent(this@MainActivity, Retr0InstallService::class.java).apply { action = "RESET_PROGRESS" }
                            startService(intent)
                            updateStorageStatus()
                        }, 
                        modifier = Modifier.fillMaxWidth(), 
                        colors = blueButtonColors,
                    ) {
                        Text(stringResource(R.string.btn_dismiss), fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }

    @Composable
    fun DiscordSection(blueButtonColors: ButtonColors) {
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(stringResource(R.string.section_discord), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text(stringResource(R.string.hint_discord), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary, modifier = Modifier.padding(top = 4.dp, bottom = 12.dp))
            Button(
                onClick = { 
                    startActivity(Intent(Intent.ACTION_VIEW, "https://discord.gg/DyBk4JeSHz".toUri())) 
                }, 
                modifier = Modifier.fillMaxWidth(), 
                colors = blueButtonColors,
            ) {
                Text(stringResource(R.string.btn_discord), fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = { 
                    startActivity(Intent(Intent.ACTION_VIEW, "https://www.youtube.com/@Retrospect69".toUri())) 
                }, 
                modifier = Modifier.fillMaxWidth(), 
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF0000), contentColor = Color.White),
            ) {
                Text(stringResource(R.string.btn_youtube), fontWeight = FontWeight.Bold)
            }
        }
    }

    @Composable
    fun MaintenanceSection(enabled: Boolean, blueButtonColors: ButtonColors) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(R.string.section_maintenance), style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
            Text(stringResource(R.string.hint_maintenance), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary, modifier = Modifier.padding(bottom = 8.dp))
            Button(
                onClick = { 
                    service?.let { binder ->
                        val intent = Intent(this@MainActivity, Retr0InstallService::class.java).apply {
                            action = "START_REPAIR"
                            val bundle = Bundle()
                            androidx.core.app.BundleCompat.putBinder(bundle, "USER_SERVICE_BINDER", binder.asBinder())
                            putExtras(bundle)
                        }
                        startForegroundService(intent)
                        bindService(intent, installServiceConnection, BIND_AUTO_CREATE)
                    }
                }, 
                modifier = Modifier.fillMaxWidth(), 
                enabled = enabled && isServiceBound, 
                colors = blueButtonColors,
            ) {
                Text(stringResource(R.string.btn_repair), fontWeight = FontWeight.Bold)
            }
        }
    }

    @Composable
    fun DiagnosticSection(blueButtonColors: ButtonColors) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(R.string.section_diagnostics), style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { runRemoteCall { it.testRR3Access() } }, modifier = Modifier.fillMaxWidth(), enabled = isServiceBound, colors = blueButtonColors) {
                Text(stringResource(R.string.btn_test_access), fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { runRemoteCall { it.testRR3Write() } }, modifier = Modifier.fillMaxWidth(), enabled = isServiceBound, colors = blueButtonColors) {
                Text(stringResource(R.string.btn_test_write), fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = { startBackgroundInstall("CHECK_UPDATE") }, modifier = Modifier.fillMaxWidth(), colors = blueButtonColors) {
                Text(stringResource(R.string.btn_check_updates), fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(12.dp))
            val deviceIdLabel = stringResource(R.string.btn_show_id)
            Button(onClick = { val id = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID); Toast.makeText(this@MainActivity, "$deviceIdLabel: $id", Toast.LENGTH_LONG).show() }, modifier = Modifier.fillMaxWidth(), colors = blueButtonColors) {
                Text(deviceIdLabel, fontWeight = FontWeight.Bold)
            }
        }
    }

    private fun requestIgnoreBatteryOptimizations() {
        val pm = getSystemService(POWER_SERVICE) as PowerManager
        if (!pm.isIgnoringBatteryOptimizations(packageName)) {
            val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).apply {
                data = "package:$packageName".toUri()
            }
            try {
                startActivity(intent)
            } catch (_: Exception) {
                Log.e(TAG, "Could not request battery optimization exemption")
            }
        }
    }

    private fun updateShizukuStatus() {
        val binderOk = runCatching { Shizuku.pingBinder() }.getOrDefault(defaultValue = false)
        isShizukuRunning = binderOk
        if (!binderOk) { shizukuStatus = "Shizuku is not running."; isPermissionGranted = false; return }
        val permGranted = runCatching { Shizuku.checkSelfPermission() }.getOrElse { PackageManager.PERMISSION_DENIED } == PackageManager.PERMISSION_GRANTED
        isPermissionGranted = permGranted
        shizukuStatus = if (permGranted) "Shizuku is running (permission granted)." else "Shizuku is running (permission required)."
    }

    private fun requestShizukuPermission() {
        if (!runCatching { Shizuku.pingBinder() }.getOrDefault(defaultValue = false)) { Toast.makeText(this, "Start Shizuku first.", Toast.LENGTH_SHORT).show(); return }
        Shizuku.requestPermission(SHIZUKU_PERMISSION_REQUEST_CODE)
    }

    private fun canAttemptServiceBind(): Boolean {
        val binderOk = runCatching { Shizuku.pingBinder() }.getOrDefault(defaultValue = false)
        return binderOk && (runCatching { Shizuku.checkSelfPermission() }.getOrDefault(PackageManager.PERMISSION_DENIED) == PackageManager.PERMISSION_GRANTED)
    }

    private fun bindRetr0Service() {
        runCatching { Shizuku.bindUserService(userServiceArgs, serviceConnection) }.onFailure { shizukuStatus = "Failed: ${it.message}" }
    }

    private fun runRemoteCall(block: (IRetr0UserService) -> String) {
        val currentService = service ?: return
        lifecycleScope.launch {
            val result = withContext(Dispatchers.IO) {
                try { block(currentService) }
                catch (e: RemoteException) { "REMOTE ERROR\n\nRemoteException\n\n${e.message}" }
                catch (t: Throwable) { "ERROR\n\n${t.javaClass.simpleName}\n\n${t.message}" }
            }
            Toast.makeText(this@MainActivity, result, Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroy() {
        runCatching { Shizuku.removeBinderReceivedListener(binderReceivedListener) }
        runCatching { Shizuku.removeRequestPermissionResultListener(permissionResultListener) }
        if (isInstallServiceBound) runCatching { unbindService(installServiceConnection) }
        if (isServiceBound) runCatching { Shizuku.unbindUserService(userServiceArgs, serviceConnection, true) }
        super.onDestroy()
    }
}
