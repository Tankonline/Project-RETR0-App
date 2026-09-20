package com.example.projectretr0installer.ui.skins

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ListAlt
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.projectretr0installer.IRetr0UserService
import com.example.projectretr0installer.data.skins.local.LocalSkinRepository
import com.example.projectretr0installer.data.skins.local.SkinOperationEntity
import com.example.projectretr0installer.domain.skins.SkinTransactionCoordinator
import com.example.projectretr0installer.ui.components.*
import com.example.projectretr0installer.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.text.DateFormat
import java.util.Date
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

private sealed interface ModsRoute { data object Root : ModsRoute; data object Import : ModsRoute; data class Details(val id: String) : ModsRoute; data class Progress(val op: OpType, val title: String) : ModsRoute }

@Composable fun SkinModsScreen(isServiceConnected: Boolean, isGameInstalled: Boolean = true, gamePackage: GamePackage = GamePackage.ROW, storageText: String, service: IRetr0UserService?, onBrowseCommunity: () -> Unit, modifier: Modifier = Modifier, initialSection: SkinModsSection = SkinModsSection.OVERVIEW, previewState: SkinModsPreviewState = SkinModsPreviewState.CONTENT, onFullScreenChange: (Boolean) -> Unit = {}) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val localRepository = remember(context) { LocalSkinRepository(context) }
    val modsViewModel = remember(context, localRepository) {
        SkinModsViewModel(
            localRepository,
            SkinTransactionCoordinator(localRepository, File("/storage/emulated/0/Documents/ProjectRETR0/SkinBackups")),
        )
    }
    val storedOperations by modsViewModel.operations.collectAsState(initial = emptyList())
    val storedSkins by modsViewModel.skins.collectAsState(initial = emptyList())
    val storedApplied by modsViewModel.applied.collectAsState(initial = emptyList())
    val storedBackups by modsViewModel.backups.collectAsState(initial = emptyList())
    var section by remember { mutableStateOf(initialSection) }
    val routes = remember { mutableStateListOf<ModsRoute>(ModsRoute.Root) }
    val operations = remember { mutableStateListOf<ActivityOp>() }
    LaunchedEffect(storedOperations) {
        operations.clear()
        operations.addAll(storedOperations.map { it.toActivityOp() })
    }
    LaunchedEffect(service, isServiceConnected) { if (isServiceConnected && service != null) modsViewModel.recover(service) }
    LaunchedEffect(Unit) {
        while (true) {
            delay(2.seconds)
            if (isServiceConnected && service != null) modsViewModel.recover(service)
        }
    }
    var sheet by remember { mutableStateOf<SkinOperationRequest?>(null) }
    fun push(route: ModsRoute) { routes.add(route) }
    fun back() { if (routes.size > 1) routes.removeAt(routes.lastIndex) }
    fun start(type: OpType, title: String) { val id = "${type.name}-${System.currentTimeMillis()}"; operations.add(0, ActivityOp(id, type, title, "Preparing", 0, "0 B", "Just now", OpResult.ACTIVE)); push(ModsRoute.Progress(type, title)) }
    BackHandler(routes.size > 1) { back() }
    val currentRoute = routes.last()
    LaunchedEffect(currentRoute) { onFullScreenChange(currentRoute is ModsRoute.Import) }
    DisposableEffect(Unit) { onDispose { onFullScreenChange(false) } }
    when (currentRoute) {
        ModsRoute.Import -> SkinImportWizard(
            ::back,
            ::back,
            browserSource = ShizukuSkinTargetBrowserSource(if (isServiceConnected) service else null),
            onTargetBrowserVisibilityChange = { visible -> onFullScreenChange(visible || currentRoute is ModsRoute.Import) },
        )
        is ModsRoute.Details -> LocalSkinDetails(currentRoute.id, ::back, { sheet = applyRequest(currentRoute.id) }, { sheet = revertRequest(currentRoute.id) })
        is ModsRoute.Progress -> SimulatedProgress(currentRoute.op, currentRoute.title, operations, ::back)
        ModsRoute.Root -> SkinModsRoot(section, { section = it }, isServiceConnected, isGameInstalled, gamePackage, storageText, operations, previewState, storedSkins.size, storedApplied.size, storedBackups.size, storedOperations.count { it.state == "FAILED" || it.state == "FAILED_SAFE" }, { push(ModsRoute.Import) }, onBrowseCommunity, { push(ModsRoute.Details(it)) }, { sheet = it }, ::start, modifier)
    }
    sheet?.let { request -> SkinOperationSheet(request, { sheet = null }) {
        sheet = null
        val skinId = request.skinId
        if (service == null || skinId == null) return@SkinOperationSheet
        scope.launch {
            if (request.type == OpType.APPLY) modsViewModel.apply(skinId, service)
            else modsViewModel.revert(skinId, service)
        }
    } }
}

@Composable private fun SkinModsRoot(section: SkinModsSection, onSection: (SkinModsSection) -> Unit, connected: Boolean, gameInstalled: Boolean, gamePackage: GamePackage, storage: String, operations: List<ActivityOp>, preview: SkinModsPreviewState, localCount: Int, appliedCount: Int, backupCount: Int, issueCount: Int, onImport: () -> Unit, onHub: () -> Unit, onDetails: (String) -> Unit, onSheet: (SkinOperationRequest) -> Unit, onStart: (OpType, String) -> Unit, modifier: Modifier) {
    Column(modifier.fillMaxSize().background(RetroBg)) {
        Row(Modifier.fillMaxWidth().heightIn(min = 72.dp).padding(horizontal = 18.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) { Column(Modifier.weight(1f)) { Text("Skin Mods", style = MaterialTheme.typography.titleLarge, color = RetroText); Text("Manage RR3 exterior liveries", color = RetroTextMuted, style = MaterialTheme.typography.labelSmall) }; IconButton(onClick = {}) { Icon(Icons.Filled.MoreVert, "Skin Mods options", tint = RetroTextDim) } }
        BoxWithConstraints(Modifier.fillMaxSize()) {
            val expanded = this.maxWidth >= 600.dp
            Row(Modifier.fillMaxSize()) {
                if (expanded) NavigationRail(containerColor = RetroSurfaceAlt) { SkinModsSection.entries.forEach { item -> NavigationRailItem(selected = section == item, onClick = { onSection(item) }, icon = { Icon(sectionIcon(item), null) }, label = { Text(item.label) }) } }
                Column(Modifier.weight(1f).fillMaxHeight()) {
                    if (!expanded) CompactSectionNavigation(section, onSection)
                    Box(Modifier.weight(1f).fillMaxWidth()) { when(section) {
                        SkinModsSection.OVERVIEW -> Box(Modifier.fillMaxSize().padding(horizontal = 18.dp)) { OverviewSection(connected, gameInstalled, gamePackage, storage, localCount, appliedCount, backupCount, issueCount, onImport, onHub, onSheet, onStart) }
                        SkinModsSection.LIBRARY -> SkinLibraryScreen({}, onDetails, onImport, onHub, embedded = true, onApply = { onSheet(applyRequest(it)) }, onRevert = { onSheet(revertRequest(it)) })
                        SkinModsSection.APPLIED -> Box(Modifier.fillMaxSize().padding(horizontal = 18.dp)) { AppliedSection(onSheet) }
                        SkinModsSection.BACKUPS -> Box(Modifier.fillMaxSize().padding(horizontal = 18.dp)) { BackupsSection() }
                        SkinModsSection.ACTIVITY -> Box(Modifier.fillMaxSize().padding(horizontal = 18.dp)) { ActivitySection(operations, preview, onStart) }
                    } }
                }
            }
        }
    }
}

@Composable private fun OverviewSection(connected: Boolean, installed: Boolean, pkg: GamePackage, storage: String, localCount: Int, appliedCount: Int, backupCount: Int, issueCount: Int, onImport: () -> Unit, onHub: () -> Unit, onSheet: (SkinOperationRequest) -> Unit, onStart: (OpType, String) -> Unit) = LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp), contentPadding = PaddingValues(vertical = 14.dp)) {
    item { SectionHeader("System status", Icons.Filled.MonitorHeart); RetroCard { StatusLine(if (connected) "Shizuku service connected" else "Shizuku disconnected", connected, "Connect"); StatusLine("RR3 ${pkg.short}${if (installed) " installed" else " missing"}", installed, "Install"); StatusLine("Original backups healthy", true, "View"); StatusLine(storage, true, "Storage") } }
    if (!connected) item { WarningBanner("Privileged service unavailable", "Connect Shizuku to browse, apply, verify, or restore targets.") }
    item { val stats = listOf("Local" to localCount, "Applied" to appliedCount, "Backups" to backupCount, "Issues" to issueCount); Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { stats.forEach { (label, value) -> RetroCard(Modifier.weight(1f)) { Text(value.toString(), color = RetroText, style = MaterialTheme.typography.titleLarge); Text(label, color = RetroTextMuted, maxLines = 1) } } } }
    item { SectionHeader("Quick actions", Icons.Filled.Bolt); Column(verticalArrangement = Arrangement.spacedBy(8.dp)) { Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { SecondaryButton("Import skin", Modifier.weight(1f), Icons.Filled.Upload, onImport); SecondaryButton("Browse Hub", Modifier.weight(1f), Icons.Filled.Public, onHub) }; Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { SecondaryButton("Scan liveries", Modifier.weight(1f), Icons.Filled.Search) { onStart(OpType.SCAN, "Scan liveries") }; SecondaryButton("Restore all", Modifier.weight(1f), Icons.Filled.Restore) { onSheet(restoreAllRequest()) } } } }
    item { SectionHeader("Recently used", Icons.Filled.History) }
    item { EmptyState(Icons.Filled.History, "No recently used skins", "Applied or imported skins will appear here.") }
}

@Composable
private fun CompactSectionNavigation(selected: SkinModsSection, onSection: (SkinModsSection) -> Unit) {
    val state = rememberLazyListState()
    LaunchedEffect(selected) { state.animateScrollToItem(selected.ordinal) }
    LazyRow(
        state = state,
        modifier = Modifier.fillMaxWidth().background(RetroSurfaceAlt),
        contentPadding = PaddingValues(horizontal = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        itemsIndexed(SkinModsSection.entries, key = { _, item -> item }) { _, item ->
            val isSelected = item == selected
            Column(
                Modifier
                    .heightIn(min = 48.dp)
                    .clickable { onSection(item) }
                    .padding(horizontal = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(item.label, maxLines = 1, color = if (isSelected) RetroAccent else RetroTextMuted, style = MaterialTheme.typography.labelLarge)
                Spacer(Modifier.height(5.dp))
                Box(Modifier.height(2.dp).width(if (isSelected) 24.dp else 0.dp).background(if (isSelected) RetroAccent else Color.Transparent, RoundedCornerShape(1.dp)))
            }
        }
    }
}

@Composable private fun AppliedSection(onSheet: (SkinOperationRequest) -> Unit) = LazyColumn(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(10.dp), contentPadding = PaddingValues(vertical = 14.dp)) {
    item {
        SectionHeader(
            "Active overrides",
            Icons.Filled.CheckCircle,
            trailingCount = 0,
            trailing = {
                TextButton(onClick = { onSheet(restoreAllRequest()) }, modifier = Modifier.heightIn(min = 48.dp)) {
                    Icon(Icons.Filled.Restore, null, tint = RetroGold, modifier = Modifier.size(17.dp))
                    Spacer(Modifier.width(5.dp))
                    Text("Restore all", color = RetroGold, maxLines = 2)
                }
            },
        )
    }
    item { EmptyState(Icons.Filled.LayersClear, "No active overrides", "Applied skins will appear here.") }
}


/* Removed unused AppliedOverrideCard implementation.
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(Icons.Filled.DirectionsCar, null, tint = RetroAccent, modifier = Modifier.size(34.dp))
        Spacer(Modifier.width(10.dp))
        Column(Modifier.weight(1f)) {
            Text(item.vehicle, color = RetroText, style = MaterialTheme.typography.titleMedium)
            Text("Active skin override", color = RetroTextMuted, style = MaterialTheme.typography.labelSmall)
        }
    }
    Spacer(Modifier.height(8.dp))
    Text(item.targetName, color = RetroTextDim, fontFamily = FontFamily.Monospace, maxLines = 2, overflow = TextOverflow.Ellipsis)
    Text("Applied ${item.appliedAt}", color = RetroTextMuted, style = MaterialTheme.typography.bodySmall)
    Spacer(Modifier.height(8.dp))
    Row(verticalAlignment = Alignment.CenterVertically) {
        HealthBadge(Icons.Filled.Backup, "Backup ${item.backupHealth.name.lowercase()}", item.backupHealth == Health.GOOD)
        Spacer(Modifier.width(6.dp))
        HealthBadge(if (item.verified) Icons.Filled.Verified else Icons.Filled.ErrorOutline, if (item.verified) "Verified" else "Unverified", item.verified)
        Spacer(Modifier.weight(1f))
        TextButton(onClick = onRevert, modifier = Modifier.heightIn(min = 48.dp)) { Text("Revert") }
    }
}
*/

/*
@Composable
private fun HealthBadge(icon: ImageVector, text: String, healthy: Boolean) {
    val color = if (healthy) RetroSuccess else RetroGold
    Row(Modifier.background(color.copy(alpha = .12f), RoundedCornerShape(10.dp)).padding(horizontal = 7.dp, vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, null, tint = color, modifier = Modifier.size(14.dp))
        Spacer(Modifier.width(4.dp))
        Text(text, color = color, style = MaterialTheme.typography.labelSmall, maxLines = 1)
    }
}
*/

@Composable private fun BackupsSection() = LazyColumn(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(10.dp), contentPadding = PaddingValues(vertical = 14.dp)) {
    item { WarningBanner("Immutable originals", "Backups are kept unchanged so every livery can be safely restored.") }
    item { EmptyState(Icons.Filled.Backup, "No backups", "A backup is created before the first apply.") }
}

@Composable private fun ActivitySection(operations: List<ActivityOp>, preview: SkinModsPreviewState, onStart: (OpType, String) -> Unit) = LazyColumn(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(10.dp), contentPadding = PaddingValues(vertical = 14.dp)) { item { SectionHeader("Operations", Icons.AutoMirrored.Filled.ListAlt, trailingCount = operations.size) }; if ((preview == SkinModsPreviewState.EMPTY) && operations.isEmpty()) item { EmptyState(Icons.Filled.History, "No activity", "Skin operations will be recorded here.") } else items(operations, key = { it.id }) { op -> var expanded by remember { mutableStateOf(false) }; RetroCard { Row { Column(Modifier.weight(1f)) { Text("${op.type.name.replace('_',' ')} • ${op.title}", color = RetroText); Text("${op.stage} • ${op.timestamp}", color = RetroTextMuted) }; Text(op.result.name, color = resultColor(op.result)) }; if (op.result == OpResult.ACTIVE) LinearProgressIndicator({ op.progress / 100f }, Modifier.fillMaxWidth()); Text("${op.progress}% • ${op.bytes}", color = RetroTextDim); op.error?.let { Text(if (expanded) it else "Operation failed. Tap for details.", color = RetroDanger, modifier = Modifier.clickable { expanded = !expanded }) }; if (op.result == OpResult.FAILED || op.result == OpResult.PARTIAL) TextButton(onClick = { onStart(op.type, op.title) }) { Text("Retry") } } } }

@Composable private fun SimulatedProgress(type: OpType, title: String, operations: MutableList<ActivityOp>, onBack: () -> Unit) { val op = operations.firstOrNull { it.type == type && it.title == title && it.result == OpResult.ACTIVE }; LaunchedEffect(op?.id) { if (op == null) return@LaunchedEffect; for (p in 10..100 step 10) { delay(120.milliseconds); val i = operations.indexOfFirst { it.id == op.id }; if (i >= 0) operations[i] = op.copy(stage = if (p < 100) "Processing" else "Verified", progress = p, bytes = "${p * 42} KiB", result = if (p == 100) OpResult.SUCCESS else OpResult.ACTIVE) } }; SkinProgressScreen(if (type == OpType.REVERT || type == OpType.RESTORE_ALL) OpKind.REVERT else OpKind.APPLY, onBack, onBack) }

@Composable private fun StatusLine(text: String, good: Boolean, action: String) { Row(Modifier.fillMaxWidth().heightIn(min = 48.dp), verticalAlignment = Alignment.CenterVertically) { Icon(if(good) Icons.Filled.CheckCircle else Icons.Filled.ErrorOutline, null, tint = if(good) RetroSuccess else RetroDanger); Spacer(Modifier.width(8.dp)); Text(text, color = RetroTextSoft, modifier = Modifier.weight(1f)); Text(action, color = RetroAccent) } }
@Composable private fun WarningBanner(title: String, body: String) { Row(Modifier.fillMaxWidth().background(RetroGold.copy(alpha=.12f), RoundedCornerShape(12.dp)).border(1.dp, RetroGold.copy(alpha=.4f), RoundedCornerShape(12.dp)).padding(12.dp)) { Icon(Icons.Filled.WarningAmber, null, tint = RetroGold); Spacer(Modifier.width(8.dp)); Column { Text(title, color = RetroGold); Text(body, color = RetroTextSoft) } } }
private fun sectionIcon(s: SkinModsSection) = when(s) { SkinModsSection.OVERVIEW -> Icons.Filled.Dashboard; SkinModsSection.LIBRARY -> Icons.Filled.Garage; SkinModsSection.APPLIED -> Icons.Filled.CheckCircle; SkinModsSection.BACKUPS -> Icons.Filled.Backup; SkinModsSection.ACTIVITY -> Icons.Filled.History }
private fun applyRequest(id: String): SkinOperationRequest = SkinOperationRequest(OpType.APPLY, "Apply this skin?", "", "The selected RR3 target", skinId = id)
private fun revertRequest(id: String): SkinOperationRequest = SkinOperationRequest(OpType.REVERT, "Restore original livery?", "", "The selected RR3 target", skinId = id)
private fun restoreAllRequest() = SkinOperationRequest(OpType.RESTORE_ALL, "Restore all originals?", "", "")
private fun resultColor(result: OpResult) = when(result) { OpResult.SUCCESS -> RetroSuccess; OpResult.FAILED -> RetroDanger; OpResult.PARTIAL -> RetroGold; else -> RetroTextDim }

private fun SkinOperationEntity.toActivityOp() = ActivityOp(
    id = id,
    type = runCatching { OpType.valueOf(kind) }.getOrDefault(OpType.VERIFY),
    title = message ?: kind,
    stage = state,
    progress = progress,
    bytes = "",
    timestamp = DateFormat.getDateTimeInstance().format(Date(updatedAt)),
    result = when (state) {
        "SUCCESS", "ROLLED_BACK" -> OpResult.SUCCESS
        "FAILED_SAFE" -> OpResult.FAILED
        "ROLLBACK_FAILED" -> OpResult.PARTIAL
        else -> OpResult.ACTIVE
    },
    error = message.takeIf { state == "FAILED_SAFE" || state == "ROLLBACK_FAILED" },
)
