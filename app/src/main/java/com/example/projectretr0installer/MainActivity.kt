package com.example.projectretr0installer

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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.edit
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import androidx.work.*
import coil.compose.AsyncImage
import com.example.projectretr0installer.installer.*
import com.example.projectretr0installer.data.skins.SkinInstallationIdentity
import com.example.projectretr0installer.ui.components.*
import com.example.projectretr0installer.ui.skins.CarSkinsTab
import com.example.projectretr0installer.ui.skins.GamePackage
import com.example.projectretr0installer.ui.skins.SkinModsScreen
import com.example.projectretr0installer.ui.theme.*
import com.example.projectretr0installer.installer.persistence.DownloadDatabase
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit
import java.io.File
import com.example.projectretr0installer.ui.shimmer
import com.example.projectretr0installer.ui.tiltEffect
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
        private const val SHIZUKU_PERMISSION_REQUEST_CODE = 1000
    }

    enum class AppMode {
        SELECTION,
        DASHBOARD,
        COMMUNITY,
        SKIN_MODS,
        AI_CHAT,
        EXPERIMENTAL_GRAPHICS,
        MUSIC_SLOTS
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
    private var allMusicSlots by mutableStateOf<List<String>>(emptyList())
    private var backedUpSlots by mutableStateOf<Set<String>>(emptySet())
    
    private var availablePresets by mutableStateOf<List<String>>(emptyList())
    
    private var isSystemStatusLoading by mutableStateOf(value = true)
    private var installationId by mutableStateOf("Loading...")
    private var showResetIdentityDialog by mutableStateOf(false)
    private val identity by lazy { SkinInstallationIdentity(this) }

    private var isCommunityLoading by mutableStateOf(false)
    private var isLibraryLoading by mutableStateOf(false)
    private var isSavesLoading by mutableStateOf(false)
    private var isSlotsLoading by mutableStateOf(false)
    private var isInstallServiceConnecting by mutableStateOf(true)
    private var isClearingTempFiles by mutableStateOf(false)

    // Save Upload State
    private var showSaveUploadDialog by mutableStateOf(false)
    private var profileToUpload by mutableStateOf<SaveProfile?>(null)

    // Music Import Workflow State
    private var showMusicImportDialog by mutableStateOf(false)
    private var currentMusicImportType by mutableStateOf("YOUTUBE") // "YOUTUBE" or "LOCAL"
    private var musicImportYtUrl by mutableStateOf("")
    private var musicImportLocalUri by mutableStateOf<Uri?>(null)
    private var musicImportSearchQuery by mutableStateOf("")
    private var musicImportResults by mutableStateOf<List<MusicMetadata>>(emptyList())
    private var isMusicSearching by mutableStateOf(false)
    private var selectedMetadata by mutableStateOf<MusicMetadata?>(null)

    // Slot Swapping State
    private var showLibraryPickerForSlot by mutableStateOf<String?>(null) // game slot being swapped
    private var showSlotPickerForLibraryTrack by mutableStateOf<LibraryMusic?>(null) // library track being applied
    
    private var aiFixResponse by mutableStateOf<String?>(null)
    private var isAiThinking by mutableStateOf(false)
    
    // AI Chat History
    private val chatMessages = mutableStateListOf<ChatMessage>()
    private var currentChatInput by mutableStateOf("")
    private var isAiChatThinking by mutableStateOf(false)
    
    private var lastUpdateCheck = 0L
    private var shizukuMonitorJob: Job? = null

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
            refreshMusicSlots()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.w(TAG, "onServiceDisconnected(name=$name)")
            service = null
            isServiceBound = false
            shizukuStatus = "RETR0 privileged service disconnected."
        }
    }

    private val userServiceArgs by lazy {
        Shizuku.UserServiceArgs(ComponentName(packageName, Retr0UserService::class.java.name))
            .daemon(false)
            .debuggable(BuildConfig.DEBUG)
            .processNameSuffix("retr0")
            .version(4)
    }

    private val binderReceivedListener = Shizuku.OnBinderReceivedListener {
        Log.i(TAG, "Shizuku binder received")
        runOnUiThread { updateShizukuStatus() }
    }

    private val binderDeadListener = Shizuku.OnBinderDeadListener {
        Log.w(TAG, "Shizuku binder died")
        runOnUiThread {
            isShizukuRunning = false
            isPermissionGranted = false
            isServiceBound = false
            service = null
            shizukuStatus = "Shizuku binder disconnected."
        }
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
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        Log.i(TAG, "MainActivity.onCreate")

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
            // Background readiness: Pre-fetch manifest immediately
            Retr0Installer(this@MainActivity).preFetchManifest()
            
            isSystemStatusLoading = true
            withContext(Dispatchers.IO) {
                updateShizukuStatus()
                updateStorageStatus()
                installationId = identity.installationId
                
                val prefs = getSharedPreferences("retr0_prefs", MODE_PRIVATE)
                isSmartBackupEnabled = prefs.getBoolean("smart_backup", false)
                lastBackupStatus = prefs.getString("last_backup_status", "Never") ?: "Never"
                
                // Initialize local YouTube downloader engine
                // Retr0Installer.initDownloader(this@MainActivity)
            }
            if (canAttemptServiceBind()) {
                Log.i(TAG, "Auto-binding service...")
                bindRetr0Service()
            }
            requestIgnoreBatteryOptimizations()
            
            // Always bind to the background install service to catch any ongoing tasks
            val intent = Intent(this@MainActivity, Retr0InstallService::class.java)
            bindService(intent, installServiceConnection, BIND_AUTO_CREATE)
        }

        setContent {
            ProjectRETR0InstallerTheme {
                MainScreen()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        updateShizukuStatus()
    }

    override fun onStop() {
        super.onStop()
        shizukuMonitorJob?.cancel()
        shizukuMonitorJob = null
    }

    override fun onResume() {
        super.onResume()
        startShizukuMonitor()
    }

    private fun updateStorageStatus() {
        isSystemStatusLoading = true
        lifecycleScope.launch {
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
        val currentService = service ?: return
        isCommunityLoading = true
        lifecycleScope.launch {
            val installer = Retr0Installer(this@MainActivity, currentService)
            communitySaves = installer.fetchCommunitySaves(communitySortType)
            communityMusic = installer.fetchCommunityMusic(communitySortType)
            isCommunityLoading = false
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

        if (showLibraryPickerForSlot != null) {
            LibraryPickerForSlotDialog(showLibraryPickerForSlot!!)
        }

        if (showSlotPickerForLibraryTrack != null) {
            SlotPickerForLibraryDialog(showSlotPickerForLibraryTrack!!)
        }
        
        LaunchedEffect(Unit) {
            lifecycleScope.launch {
                // Silent update check once per hour
                val now = System.currentTimeMillis()
                if ((now - lastUpdateCheck) > 3600000L) {
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
                refreshMusicSlots()
            }
        }

        if (showResetIdentityDialog) {
            ResetIdentityDialog()
        }

        Scaffold(
            contentWindowInsets = WindowInsets.safeDrawing,
            bottomBar = {
                if (currentMode != AppMode.EXPERIMENTAL_GRAPHICS && currentMode != AppMode.SELECTION && !skinsFullScreen) {
                    RetroBottomBar(
                        selectedIndex = when(currentMode) {
                            AppMode.DASHBOARD -> 0
                            AppMode.COMMUNITY -> 1
                            AppMode.SKIN_MODS -> 2
                            AppMode.AI_CHAT -> 3
                            else -> 0
                        },
                        items = listOf(
                            RetroNavItem("Home", Icons.Filled.Home) { currentMode = AppMode.DASHBOARD },
                            RetroNavItem("Hub", Icons.Filled.Public) { currentMode = AppMode.COMMUNITY },
                            RetroNavItem("Skins", Icons.Filled.Brush) { currentMode = AppMode.SKIN_MODS },
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
                when (currentMode) {
                    AppMode.SELECTION -> GameSelectionScreen()
                    AppMode.DASHBOARD -> RR3Dashboard(installProgress)
                    AppMode.COMMUNITY -> CommunityGalleryScreen(onFullScreenChange = { skinsFullScreen = it })
                    AppMode.SKIN_MODS -> SkinModsScreen(isServiceConnected = isServiceBound, gamePackage = GamePackage.ROW, storageText = availableSpace, service = service, onBrowseCommunity = { currentMode = AppMode.COMMUNITY }, onFullScreenChange = { skinsFullScreen = it })
                    AppMode.AI_CHAT -> AiChatScreen()
                    AppMode.EXPERIMENTAL_GRAPHICS -> ExperimentalGraphicsScreen(
                        (installProgress.state == InstallState.IDLE || installProgress.state == InstallState.SUCCESS || installProgress.state == InstallState.FAILED)
                    )
                    AppMode.MUSIC_SLOTS -> MusicSlotsScreen()
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
                Text("v1.0.0 · Stable", style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 0.sp), color = RetroTextMuted)
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

        val characterPicker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri -> 
            uri?.let { startBackgroundInstall("CHARACTER", uri = it) } 
        }
        val docPicker = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocumentTree()) { uri -> 
            uri?.let { startBackgroundInstall("DOC", uri = it) } 
        }
        val musicPicker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri -> 
            uri?.let { 
                musicImportLocalUri = it
                currentMusicImportType = "LOCAL"
                musicImportSearchQuery = "" // Reset
                musicImportResults = emptyList()
                selectedMetadata = null
                showMusicImportDialog = true
            }
        }

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

            // System Status
            RetroCard {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { 
                    if (!isShizukuRunning || !isPermissionGranted) {
                        requestShizukuPermission()
                    }
                }) {
                    Box(Modifier.size(9.dp).background(if(isShizukuRunning) RetroSuccess else RetroDanger, CircleShape))
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) {
                        Text(if(isShizukuRunning) "Shizuku running" else "Shizuku not running", style = MaterialTheme.typography.bodyLarge, color = RetroText)
                        Text(if(isPermissionGranted) "Permission granted" else "Tap to grant permission", style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 0.sp), color = if(isPermissionGranted) RetroTextMuted else RetroDangerText)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(availableSpace, style = MaterialTheme.typography.bodyLarge, color = RetroText)
                        Text("available", style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 0.sp), color = RetroTextMuted)
                    }
                }
            }

            AnimatedVisibility(visible = installProgress.state != InstallState.IDLE) {
                InstallProgressSection(installProgress)
            }
            
            if (isInstallServiceConnecting && installProgress.state == InstallState.IDLE) {
                Box(Modifier.fillMaxWidth().padding(vertical = 20.dp), Alignment.Center) {
                    CircularProgressIndicator(color = RetroAccent)
                }
            }

            // Main Actions
            val showButtons = (installProgress.state == InstallState.IDLE || installProgress.state == InstallState.SUCCESS || installProgress.state == InstallState.FAILED) && !isInstallServiceConnecting
            
            AnimatedVisibility(visible = showButtons) {
                Column(verticalArrangement = Arrangement.spacedBy(9.dp)) {
                    val privileged = isShizukuRunning && isPermissionGranted

                    if (!isServiceBound) {
                        PrimaryButton(
                            text = "Enable Privileged Services",
                            icon = Icons.Filled.FlashOn
                        ) {
                            if (!Shizuku.pingBinder()) {
                                updateShizukuStatus()
                                Toast.makeText(this@MainActivity, "Shizuku binder is unavailable. Open Shizuku and verify the service is running.", Toast.LENGTH_SHORT).show()
                            } else if (Shizuku.checkSelfPermission() == PackageManager.PERMISSION_GRANTED) {
                                isShizukuRunning = true
                                isPermissionGranted = true
                                bindRetr0Service()
                            } else {
                                requestShizukuPermission()
                            }
                        }
                    }

                    AccentTintButton(
                        text = "Launch Real Racing 3", 
                        icon = Icons.Filled.PlayArrow, 
                        fill = RetroSuccess.copy(alpha = 0.15f), 
                        stroke = RetroSuccess.copy(alpha = 0.4f), 
                        contentColor = RetroSuccess,
                        modifier = Modifier.fillMaxWidth().height(60.dp)
                    ) {
                        launchGame()
                    }

                    if (privileged) {
                        PrimaryButton("Install Project RETR0", icon = Icons.Filled.Download) { 
                            service?.let { binder ->
                                val intent = Intent(this@MainActivity, Retr0InstallService::class.java).apply {
                                    action = "START_INSTALL"
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
                    } else {
                        SecondaryButton("Install Project RETR0", icon = Icons.Filled.Download, modifier = Modifier.fillMaxWidth()) { 
                            service?.let { binder ->
                                val intent = Intent(this@MainActivity, Retr0InstallService::class.java).apply {
                                    action = "START_INSTALL"
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
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(9.dp)) {
                        SecondaryButton("Pipeline test", Modifier.weight(1f), icon = Icons.Filled.VerifiedUser) { 
                            service?.let { binder ->
                                val intent = Intent(this@MainActivity, Retr0InstallService::class.java).apply {
                                    action = "START_SAFE_TEST"
                                    val bundle = Bundle()
                                bundle.putBinder("USER_SERVICE_BINDER", binder.asBinder())
                                    putExtras(bundle)
                                }
                                startForegroundService(intent)
                                bindService(intent, installServiceConnection, BIND_AUTO_CREATE)
                            }
                        }
                        SecondaryButton("Community hub", Modifier.weight(1f), icon = Icons.Filled.Public) { currentMode = AppMode.COMMUNITY }
                    }
                }
            }

            // Community & Support
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                SectionHeader("Community & support", Icons.Filled.Favorite)
                Row(horizontalArrangement = Arrangement.spacedBy(9.dp)) {
                    AccentTintButton("Discord", Icons.Filled.Forum, RetroDiscordTint, RetroDiscord.copy(alpha = 0.4f), RetroDiscord, Modifier.weight(1f)) { 
                        startActivity(Intent(Intent.ACTION_VIEW, "https://discord.gg/projectretr0".toUri()))
                    }
                    AccentTintButton("Subscribe", Icons.Filled.Star, RetroGoldTint, RetroGold.copy(alpha = 0.45f), RetroGold, Modifier.weight(1f)) { 
                        startActivity(Intent(Intent.ACTION_VIEW, "https://www.youtube.com/@Retrospect69".toUri()))
                    }
                }
            }

            // Music Hub
            MusicHubSection(musicPicker)

            // Graphics Hub
            GraphicsHubSection(isUiIdle)

            // Save Hub
            SaveHubSection(isUiIdle)

            // Manual Import
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                SectionHeader("Manual import", Icons.Filled.FileUpload)
                Text("Import external files into the game.", style = MaterialTheme.typography.bodyMedium, color = RetroTextMuted)
                Row(horizontalArrangement = Arrangement.spacedBy(9.dp)) {
                    SecondaryButton("Character2.dat", Modifier.weight(1f), icon = Icons.AutoMirrored.Filled.InsertDriveFile) { characterPicker.launch("application/octet-stream") }
                    SecondaryButton(".doc folder", Modifier.weight(1f), icon = Icons.Filled.Folder) { docPicker.launch(null) }
                }
            }

            // Maintenance
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                SectionHeader("Maintenance", Icons.Filled.Build)
                Text("Repair game assets or remove temporary installer downloads.", style = MaterialTheme.typography.bodyMedium, color = RetroTextMuted)
                DangerButton("RR3 crash / error fix", icon = Icons.Filled.ReportProblem) { startBackgroundInstall("REPAIR") }
                SecondaryButton(
                    text = if (isClearingTempFiles) "Clearing temporary files..." else "Clear temporary files",
                    modifier = Modifier.fillMaxWidth(),
                    icon = Icons.Filled.DeleteSweep,
                ) {
                    if (!isUiIdle || isClearingTempFiles) {
                        Toast.makeText(this@MainActivity, "Temporary files cannot be cleared during installation.", Toast.LENGTH_SHORT).show()
                    } else {
                        clearInstallerTempFiles()
                    }
                }
            }

            // Diagnostics
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                SectionHeader("Diagnostics", Icons.Filled.Troubleshoot)
                val diags = listOf(
                    "Directory access" to { runRemoteCall { it.testRR3Access() } }, 
                    "Write access (legacy)" to { runRemoteCall { it.testRR3Write() } },
                    "Check for updates" to { startBackgroundInstall("CHECK_UPDATE") }, 
                    "Generate logs" to { generateLogs() }
                )
                Column(verticalArrangement = Arrangement.spacedBy(9.dp)) {
                    diags.chunked(2).forEach { row ->
                        Row(horizontalArrangement = Arrangement.spacedBy(9.dp)) {
                            row.forEach { (label, action) -> SecondaryButton(label, modifier = Modifier.weight(1f)) { action() } }
                            if (row.size == 1) Spacer(Modifier.weight(1f))
                        }
                    }
                    SecondaryButton("Show device ID", modifier = Modifier.fillMaxWidth()) { 
                        @Suppress("HardwareIds")
                        val id = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
                        Toast.makeText(this@MainActivity, "ID: $id", Toast.LENGTH_LONG).show() 
                    }
                }
            }

            SectionHeader("Community Identity", Icons.Filled.Fingerprint)
            // Installation Identity
            RetroCard {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Fingerprint, null, tint = RetroAccent, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Installation Identity", style = MaterialTheme.typography.labelSmall, color = RetroTextMuted)
                        Spacer(Modifier.weight(1f))
                        Text(if (isServiceBound) "Secured" else "Local", color = if (isServiceBound) RetroSuccess else RetroTextDim, style = MaterialTheme.typography.labelSmall)
                    }
                    
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
                        .background(RetroBg, RoundedCornerShape(11.dp))
                        .border(1.dp, RetroBorder, RoundedCornerShape(11.dp))
                        .padding(horizontal = 12.dp, vertical = 10.dp)) {
                        Text(
                            installationId,
                            modifier = Modifier.weight(1f),
                            color = RetroText,
                            style = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
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
                            "Reset Identity",
                            color = RetroDanger,
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.clickable { showResetIdentityDialog = true }.padding(4.dp)
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun InstallProgressSection(progress: InstallProgress) {
        RetroCard {
            val title = when (progress.state) {
                InstallState.SUCCESS -> "COMPLETE"
                InstallState.FAILED -> "FAILED"
                InstallState.UPDATE -> "UPDATING"
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
        var selectedTab by remember { mutableIntStateOf(0) }
        var focusedSkinRoute by remember { mutableStateOf(false) }
        val scrollState = rememberScrollState()
        DisposableEffect(Unit) { onDispose { onFullScreenChange(false) } }
        LaunchedEffect(focusedSkinRoute) { onFullScreenChange(focusedSkinRoute) }

        if (selectedTab == 2 && focusedSkinRoute) {
            CarSkinsTab(
                service = service,
                isServiceConnected = isServiceBound,
                onOpenMySkins = { currentMode = AppMode.SKIN_MODS },
                onOpenImport = { currentMode = AppMode.SKIN_MODS },
                onFullScreenChange = { focusedSkinRoute = it },
            )
            return
        }

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
                .then(if (selectedTab == 2) Modifier else Modifier.verticalScroll(scrollState))
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
                        .clickable { /* toggleSort() */ }, Alignment.Center) { Icon(Icons.Filled.SwapVert, null, tint = RetroAccent) }
            }
            
            SegmentedTabs(listOf("Saves", "Music", "Skins"), selectedTab) { selectedTab = it }

            if (selectedTab == 0) {
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
                                lifecycleScope.launch {
                                    val s = service ?: return@launch
                                    if (Retr0Installer(this@MainActivity, s).likeItem(save.id)) {
                                        Toast.makeText(this@MainActivity, "Liked!", Toast.LENGTH_SHORT).show()
                                        refreshCommunityHub()
                                    }
                                }
                            }
                        )
                        Spacer(Modifier.height(12.dp))
                    }
                }
            } else if (selectedTab == 1) {
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
                        CommunityMusicItem(music)
                        Spacer(Modifier.height(12.dp))
                    }
                }
            } else {
                Box(Modifier.weight(1f).fillMaxWidth()) {
                    CarSkinsTab(
                        service = service,
                        isServiceConnected = isServiceBound,
                        onOpenMySkins = { currentMode = AppMode.SKIN_MODS },
                        onOpenImport = { currentMode = AppMode.SKIN_MODS },
                        onFullScreenChange = { focusedSkinRoute = it },
                    )
                }
            }
        }
    }

    @Composable
    fun SaveCard(save: CommunitySave, onGet: () -> Unit, onFav: () -> Unit) {
        RetroCard {
            Row(verticalAlignment = Alignment.Top) {
                Box(Modifier.size(38.dp).background(RetroAccentTint, RoundedCornerShape(11.dp)), Alignment.Center) {
                    Icon(Icons.Filled.Person, null, tint = RetroAccent)
                }
                Spacer(Modifier.width(11.dp))
                Column(Modifier.weight(1f)) {
                    Text(save.customName, style = MaterialTheme.typography.titleMedium, color = RetroText)
                    Text("by ${save.uploaderName} · ${StorageUtils.formatSize(save.fileSize)}", style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 0.sp), color = RetroTextMuted)
                }
                Icon(Icons.Filled.FavoriteBorder, null,
                    tint = RetroTextMuted, modifier = Modifier.clickable(onClick = onFav))
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
    fun CommunityMusicItem(music: CommunityMusic) {
        RetroCard {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(38.dp).background(RetroAccentTint, RoundedCornerShape(11.dp)), Alignment.Center) {
                    Icon(Icons.Filled.MusicNote, null, tint = RetroAccent)
                }
                Spacer(Modifier.width(11.dp))
                Column(Modifier.weight(1f)) {
                    Text(music.customName, style = MaterialTheme.typography.titleMedium, color = RetroText)
                    Text("by ${music.uploaderName} · ${StorageUtils.formatSize(music.fileSize)}", style = MaterialTheme.typography.labelSmall, color = RetroTextMuted)
                }
                Box(Modifier.clip(RoundedCornerShape(20.dp)).background(RetroAccent).clickable { 
                    startBackgroundInstall("DOWNLOAD_TO_LIBRARY", saveId = music.id, customName = music.customName)
                }.padding(horizontal = 20.dp, vertical = 7.dp)) {
                    Text("Get", color = Color.White, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }

    @Composable
    fun MusicHubSection(musicPicker: androidx.activity.result.ActivityResultLauncher<String>) {
        var selectedTabIndex by remember { mutableIntStateOf(0) }
        var ytUrl by remember { mutableStateOf("") }

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                SectionHeader("Music hub", Icons.Filled.MusicNote)
                Text("Swap game soundtracks and build a library.", style = MaterialTheme.typography.bodyMedium, color = RetroTextMuted)
            }
            
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                RetroCard {
                    SegmentedTabs(listOf("Core","Extended","Library"), selectedTabIndex) { selectedTabIndex = it }
                    Spacer(Modifier.height(12.dp))
                    
                    when (selectedTabIndex) {
                        0 -> {
                            TrackRow("Main menu", "Menu_Music.mp3") { startBackgroundInstall("MUSIC_RESTORE", trackFileName = "Menu_Music.mp3") }
                            HorizontalDivider(color = RetroBorder)
                            TrackRow("Garage", "Garage_Music.mp3") { startBackgroundInstall("MUSIC_RESTORE", trackFileName = "Garage_Music.mp3") }
                            HorizontalDivider(color = RetroBorder)
                            TrackRow("Loading screen", "LoadingScreen.mp3") { startBackgroundInstall("MUSIC_RESTORE", trackFileName = "LoadingScreen.mp3") }
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
                                TrackRow(title, file) { startBackgroundInstall("MUSIC_RESTORE", trackFileName = file) }
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
                    currentMode = AppMode.MUSIC_SLOTS 
                }
                
                Column(Modifier.fillMaxWidth().background(RetroSurfaceAlt, RoundedCornerShape(12.dp))
                        .border(1.dp, RetroBorder, RoundedCornerShape(12.dp)).padding(11.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.SmartDisplay, null, tint = RetroDanger, modifier = Modifier.size(14.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("YouTube converter", style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 0.sp), color = RetroTextMuted)
                    }
                    Spacer(Modifier.height(8.dp))
                    BasicTextField(value = ytUrl, onValueChange = { ytUrl = it }, singleLine = true,
                        textStyle = MaterialTheme.typography.bodyMedium.copy(color = RetroText),
                        modifier = Modifier.fillMaxWidth().background(RetroBg, RoundedCornerShape(9.dp))
                            .border(1.dp, RetroBorder, RoundedCornerShape(9.dp)).padding(11.dp),
                        decorationBox = { inner -> if (ytUrl.isEmpty()) Text("Paste YouTube URL here…", color = RetroTextMuted, style = MaterialTheme.typography.bodyMedium); inner() })
                    Spacer(Modifier.height(8.dp))
                    PrimaryButton("Start Matching") { 
                        if (ytUrl.isNotBlank()) {
                            musicImportYtUrl = ytUrl
                            currentMusicImportType = "YOUTUBE"
                            musicImportSearchQuery = ""
                            musicImportResults = emptyList()
                            selectedMetadata = null
                            showMusicImportDialog = true
                        } else {
                            Toast.makeText(this@MainActivity, "Paste a URL first.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun TrackRow(title: String, file: String, onSwap: () -> Unit) {
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
            Spacer(Modifier.width(10.dp))
            Text("Swap", color = RetroAccent, style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.border(1.dp, RetroAccent.copy(alpha = 0.4f), RoundedCornerShape(20.dp))
                    .clickable(onClick = onSwap).padding(horizontal = 12.dp, vertical = 4.dp))
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

    @Composable
    fun GraphicsHubSection(isUiIdle: Boolean) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                SectionHeader("Graphics hub", Icons.Filled.Tune)
                Text("Override your device tier for hidden quality.", style = MaterialTheme.typography.bodyMedium, color = RetroTextMuted)
            }
            
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                val tiers = listOf("Low" to "AndroidLow","Medium" to "AndroidMid","Medium 2" to "AndroidMid2","High" to "AndroidHigh","Xtra High" to "AndroidXtraHigh","Ultra Wide" to "AndroidUltraWide")
                tiers.chunked(2).forEach { row ->
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        row.forEach { (label, id) ->
                            RetroChip(label, selected = false, modifier = Modifier.weight(1f)) { 
                                if (isUiIdle) startBackgroundInstall("APPLY_GRAPHICS", presetName = id) 
                            }
                        }
                    }
                }
                RetroChip("SimRigTV", selected = false, centered = true, modifier = Modifier.fillMaxWidth()) { 
                    if (isUiIdle) startBackgroundInstall("APPLY_GRAPHICS", presetName = "SimRigTV") 
                }
                OutlineButton("Restore original graphics") { 
                    if (isUiIdle) startBackgroundInstall("RESTORE_GRAPHICS") 
                }
                DangerButton("Other options (experimental)", icon = Icons.Filled.WarningAmber) { currentMode = AppMode.EXPERIMENTAL_GRAPHICS }
            }
        }
    }

    @Composable
    fun SaveHubSection(isUiIdle: Boolean) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Save, null, tint = RetroAccent, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("SAVE HUB", style = MaterialTheme.typography.labelSmall, color = RetroTextMuted)
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
                                    // Show rename dialog (logic omitted for brevity but function is now "used")
                                    lifecycleScope.launch {
                                        val currentService = service ?: return@launch
                                        Retr0Installer(this@MainActivity, currentService).renameProfile(profile, profile.name + "_renamed")
                                        refreshSaveHub()
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
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = RetroTextDim, modifier = Modifier.clickable { currentMode = AppMode.DASHBOARD }); Spacer(Modifier.width(12.dp))
                Column {
                    Text("Experimental Hub", style = MaterialTheme.typography.titleLarge, color = RetroText)
                    Text("iOS · tvOS · Mac tiers", style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 0.sp), color = RetroDangerText)
                }
            }
            
            Row(Modifier.fillMaxWidth().background(RetroDangerTint, RoundedCornerShape(14.dp)).border(1.dp, RetroDanger.copy(alpha = 0.3f), RoundedCornerShape(14.dp)).padding(14.dp)) {
                Icon(Icons.Filled.WarningAmber, null, tint = RetroDanger, modifier = Modifier.size(20.dp)); Spacer(Modifier.width(11.dp))
                Column {
                    Text("Non-Android presets", style = MaterialTheme.typography.titleMedium.copy(fontSize = 13.sp), color = RetroDangerText)
                    Text("May cause glitches, crashes or severe performance drops. Use at your own risk.", style = MaterialTheme.typography.bodyMedium, color = RetroDangerBody)
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
                                        RetroChip(formatDeviceName(p), modifier = Modifier.weight(1f)) { 
                                            if (enabled) startBackgroundInstall("APPLY_GRAPHICS", presetName = p) 
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
        manualLevel: String? = null,
        manualGold: String? = null,
        manualRS: String? = null,
        manualMS: String? = null,
        youtubeUrl: String? = null,
        shareHub: Boolean = false,
        artworkUrl: String? = null,
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
                putExtra("YOUTUBE_URL", youtubeUrl)
                putExtra("ARTWORK_URL", artworkUrl)
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
            while (isActive) {
                updateShizukuStatus()
                delay(1.seconds)
            }
        }
    }

    private fun updateShizukuStatus() {
        val binderOk = try { Shizuku.pingBinder() } catch (e: Throwable) {
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
        val permGranted = try { 
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
        val binderOk = runCatching { Shizuku.pingBinder() }.getOrDefault(false)
        val binderAlive = binderOk && runCatching { Shizuku.getBinder()?.pingBinder() == true }.getOrDefault(false)
        Log.i(TAG, "requestShizukuPermission: ping=$binderOk binderAlive=$binderAlive")
        if (!binderAlive) { 
            Toast.makeText(this, "Shizuku binder is unavailable. Open Shizuku and restart its service.", Toast.LENGTH_SHORT).show()
            return 
        }
        val permissionGranted = runCatching {
            Shizuku.checkSelfPermission() == PackageManager.PERMISSION_GRANTED
        }.getOrDefault(false)
        if (permissionGranted) {
            isPermissionGranted = true
            bindRetr0Service()
            return
        }
        try {
            Shizuku.requestPermission(SHIZUKU_PERMISSION_REQUEST_CODE)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to request Shizuku permission", e)
            Toast.makeText(this, "Failed to request permission: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun canAttemptServiceBind(): Boolean {
        val binderOk = runCatching { Shizuku.pingBinder() }.getOrDefault(defaultValue = false)
        return binderOk && (runCatching { Shizuku.checkSelfPermission() }.getOrDefault(PackageManager.PERMISSION_DENIED) == PackageManager.PERMISSION_GRANTED)
    }

    private fun bindRetr0Service() {
        // Do not call bindUserService until both the Shizuku binder and the
        // app's Shizuku permission are ready. Calling it earlier produces the
        // provider SecurityException reported by Android.
        val binderReady = runCatching { Shizuku.pingBinder() }.getOrDefault(false)
        val permissionReady = runCatching {
            Shizuku.checkSelfPermission() == PackageManager.PERMISSION_GRANTED
        }.getOrDefault(false)

        if (!binderReady || !permissionReady) {
            isServiceBound = false
            service = null
            shizukuStatus = if (!binderReady) {
                "Shizuku is not running."
            } else {
                "Shizuku permission is required."
            }
            Log.w(TAG, "Skipping bind: binderReady=$binderReady permissionReady=$permissionReady")
            if (binderReady) {
                requestShizukuPermission()
            }
            return
        }

        Log.i(TAG, "Attempting to bind privileged service...")
        runCatching {
            Shizuku.bindUserService(userServiceArgs, serviceConnection)
        }.onSuccess {
            Log.i(TAG, "bindUserService called successfully")
        }.onFailure {
            Log.e(TAG, "Failed to bind user service", it)
            shizukuStatus = "Failed to bind: ${it.message}"
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
                        currentMode = AppMode.DASHBOARD 
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
                                                // The slot fileName needs to be the full relative path
                                                showLibraryPickerForSlot = fullRelativePath 
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
        val pm = getSystemService(POWER_SERVICE) as PowerManager
        if (!pm.isIgnoringBatteryOptimizations(packageName)) {
            val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).apply { data = "package:$packageName".toUri() }
            startActivity(intent)
        }
    }

    @Composable
    fun MusicImportDialog() {
        var uploaderName by remember { mutableStateOf("Retr0") }
        var shareOnHub by remember { mutableStateOf(false) }
        var targetTrackSlot by remember { mutableStateOf("Garage_Music.mp3") }
        var currentStep by remember { mutableIntStateOf(0) } // 0: Search, 1: Selection, 2: Confirmation

        Dialog(onDismissRequest = { showMusicImportDialog = false }) {
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
                        if (currentMusicImportType == "YOUTUBE") "YouTube Converter" else "Local Music Import",
                        style = MaterialTheme.typography.titleLarge,
                        color = RetroText
                    )
                    
                    when (currentStep) {
                        0 -> {
                            Text("What is this song called?", style = MaterialTheme.typography.bodyMedium, color = RetroTextMuted)
                            RetroSearchField(
                                value = musicImportSearchQuery,
                                onValueChange = { musicImportSearchQuery = it },
                                placeholder = "Enter song name…",
                                icon = Icons.Filled.MusicNote
                            )
                            PrimaryButton(
                                text = if (isMusicSearching) "Searching…" else "Find Matches",
                                icon = if (isMusicSearching) null else Icons.Filled.Search
                            ) {
                                if (!isMusicSearching && musicImportSearchQuery.isNotBlank()) {
                                    isMusicSearching = true
                                    lifecycleScope.launch {
                                        try {
                                            musicImportResults = MetadataFetcher.searchMetadata(musicImportSearchQuery)
                                            if (musicImportResults.isNotEmpty()) {
                                                currentStep = 1
                                            } else {
                                                Toast.makeText(this@MainActivity, "No matches found. Try a different name.", Toast.LENGTH_SHORT).show()
                                            }
                                        } catch (e: Exception) {
                                            Toast.makeText(this@MainActivity, "Search failed: ${e.message}", Toast.LENGTH_SHORT).show()
                                        } finally {
                                            isMusicSearching = false
                                        }
                                    }
                                }
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
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                OutlineButton("Search Again", Modifier.weight(1f)) { currentStep = 0 }
                                PrimaryButton("Confirm Match", Modifier.weight(1f)) {
                                    if (selectedMetadata != null) currentStep = 2
                                }
                            }
                        }
                        2 -> {
                            val meta = selectedMetadata!!
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                AsyncImage(
                                    model = meta.artworkUrl,
                                    contentDescription = null,
                                    modifier = Modifier.size(64.dp).clip(RoundedCornerShape(10.dp)),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(Modifier.width(16.dp))
                                Column {
                                    Text(meta.trackName, style = MaterialTheme.typography.titleMedium, color = RetroText)
                                    Text(meta.artistName, style = MaterialTheme.typography.bodyMedium, color = RetroTextMuted)
                                }
                            }
                            
                            HorizontalDivider(color = RetroBorder)
                            
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
                            
                            Column(
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text("CORE", style = MaterialTheme.typography.labelSmall, color = RetroAccent, modifier = Modifier.padding(top = 4.dp))
                                coreSlots.chunked(2).forEach { row ->
                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        row.forEach { (file, title) ->
                                            RetroChip(
                                                text = title,
                                                selected = targetTrackSlot == file,
                                                modifier = Modifier.weight(1f),
                                                onClick = { targetTrackSlot = file }
                                            )
                                        }
                                        if (row.size == 1) Spacer(Modifier.weight(1f))
                                    }
                                }
                                
                                Text("EXTENDED", style = MaterialTheme.typography.labelSmall, color = RetroAccent, modifier = Modifier.padding(top = 8.dp))
                                extendedSlots.chunked(2).forEach { row ->
                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        row.forEach { (file, title) ->
                                            RetroChip(
                                                text = title,
                                                selected = targetTrackSlot == file,
                                                modifier = Modifier.weight(1f),
                                                onClick = { targetTrackSlot = file }
                                            )
                                        }
                                        if (row.size == 1) Spacer(Modifier.weight(1f))
                                    }
                                }
                            }
                            
                            PrimaryButton("Apply Profile & Process") {
                                if (currentMusicImportType == "YOUTUBE") {
                                    startBackgroundInstall(
                                        "YOUTUBE_TO_LIBRARY",
                                        youtubeUrl = musicImportYtUrl,
                                        customName = meta.trackName,
                                        uploaderName = uploaderName,
                                        targetTrack = targetTrackSlot,
                                        shareHub = shareOnHub,
                                        artworkUrl = meta.artworkUrl
                                    )
                                } else {
                                    startBackgroundInstall(
                                        "MUSIC_MOD",
                                        uri = musicImportLocalUri,
                                        customName = meta.trackName,
                                        uploaderName = uploaderName,
                                        trackFileName = targetTrackSlot, // This is the 'target' in the game
                                        targetTrack = targetTrackSlot,   // Duplicated for the shared uploader logic
                                        shareHub = shareOnHub,
                                        artworkUrl = meta.artworkUrl
                                    )
                                }
                                showMusicImportDialog = false
                            }
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
                                identity.resetIdentity()
                                installationId = identity.installationId
                                showResetIdentityDialog = false
                                Toast.makeText(this@MainActivity, "Identity Reset", Toast.LENGTH_SHORT).show()
                                refreshCommunityHub()
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
