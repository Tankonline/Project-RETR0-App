package com.retr0.projectretr0.ui.carmodels

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ListAlt
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.retr0.projectretr0.IRetr0UserService
import com.retr0.projectretr0.data.carModels.CarModelErrorCode
import com.retr0.projectretr0.data.carModels.CommunityCarModel
import com.retr0.projectretr0.data.carModels.local.BackupStatus
import com.retr0.projectretr0.data.carModels.local.CarModelActivity
import com.retr0.projectretr0.data.carModels.local.CarModelBackup
import com.retr0.projectretr0.data.carModels.local.CarModelPaths
import com.retr0.projectretr0.data.carModels.local.CarModelState
import com.retr0.projectretr0.data.carModels.local.InstalledCarModel
import com.retr0.projectretr0.ui.components.*
import com.retr0.projectretr0.ui.skins.DestructiveConfirmationDialog
import com.retr0.projectretr0.ui.skins.EmptyState
import com.retr0.projectretr0.ui.skins.GamePackage
import com.retr0.projectretr0.ui.skins.SkinModsScreen
import com.retr0.projectretr0.ui.skins.StatusLine
import com.retr0.projectretr0.ui.skins.WarningBanner
import com.retr0.projectretr0.ui.theme.*
import kotlinx.coroutines.launch
import java.io.File

enum class CarModelsSection(val label: String) { OVERVIEW("Overview"), LIBRARY("My Models"), APPLIED("Applied"), BACKUPS("Backups"), ACTIVITY("Activity") }

/** Mods home: one top segmented control switching between the untouched Skin Mods flow and Car Models. */
@Composable
fun ModsHomeScreen(
    kind: ModsKind,
    onKind: (ModsKind) -> Unit,
    isServiceConnected: Boolean,
    gamePackage: GamePackage,
    storageText: String,
    service: IRetr0UserService?,
    onBrowseCommunity: () -> Unit,
    modifier: Modifier = Modifier,
    isGameInstalled: Boolean = true,
    onFullScreenChange: (Boolean) -> Unit = {},
) {
    val header: @Composable () -> Unit = { ModsHeader(kind, onKind) }
    when (kind) {
        ModsKind.SKINS -> SkinModsScreen(isServiceConnected, isGameInstalled, gamePackage, storageText, service, onBrowseCommunity, modifier, onFullScreenChange = onFullScreenChange, header = header)
        ModsKind.CARS -> CarModelsScreen(isServiceConnected, gamePackage, service, header, modifier, isGameInstalled, onFullScreenChange = onFullScreenChange)
    }
}

@Composable
fun ModsHeader(kind: ModsKind, onKind: (ModsKind) -> Unit) {
    Column(Modifier.fillMaxWidth().padding(horizontal = 18.dp, vertical = 10.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Column {
            Text("Mods", style = MaterialTheme.typography.titleLarge, color = RetroText)
            Text("Skins & complete car models for RR3", color = RetroTextMuted, style = MaterialTheme.typography.labelMedium)
        }
        ModsSegmentedControl(kind, onKind)
    }
}

private sealed interface CarRoute {
    data object Root : CarRoute
    data object Gallery : CarRoute
    data class HubDetails(val model: CommunityCarModel) : CarRoute
    data class LocalDetails(val id: String) : CarRoute
    data class Import(val modelId: String?) : CarRoute
}

/** One-line result banner on the Car Models root; failures carry their `CM-…` code. */
data class CarNotice(val text: String, val good: Boolean, val code: String? = null)
private fun Throwable.toNotice() = toUiError().let { CarNotice(it.message, false, it.code) }

data class CarModelsActions(
    val onBrowse: () -> Unit = {},
    val onImport: () -> Unit = {},
    val onBrowseLibrary: () -> Unit = {},
    val onScan: () -> Unit = {},
    val onRestoreAll: () -> Unit = {},
    val onOpenModel: (String) -> Unit = {},
    val onApplyModel: (String) -> Unit = {},
    val onRevert: (gamePackage: String, vehicleDirectory: String) -> Unit = { _, _ -> },
    val onDeleteBackup: (CarModelBackup) -> Unit = {},
    val onVerifyBackups: () -> Unit = {},
)

@Composable
fun CarModelsScreen(
    isServiceConnected: Boolean,
    gamePackage: GamePackage,
    service: IRetr0UserService?,
    header: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    isGameInstalled: Boolean = true,
    initialSection: CarModelsSection = CarModelsSection.OVERVIEW,
    onFullScreenChange: (Boolean) -> Unit = {},
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val viewModel = remember(context) { CarModelsViewModel.get(context) }
    val state by viewModel.state.collectAsState()
    val liveService = service.takeIf { isServiceConnected }
    var section by rememberSaveable { mutableStateOf(initialSection) }
    val routes = remember { mutableStateListOf<CarRoute>(CarRoute.Root) }
    var busy by remember { mutableStateOf<String?>(null) }
    var notice by remember { mutableStateOf<CarNotice?>(null) }
    var revertTarget by remember { mutableStateOf<Pair<String, String>?>(null) }
    var revertChecking by remember { mutableStateOf(false) }
    var removeAddonTarget by remember { mutableStateOf<InstalledCarModel?>(null) }
    var deleteTarget by remember { mutableStateOf<CarModelBackup?>(null) }
    var removeTarget by remember { mutableStateOf<String?>(null) }
    var remoteDeleteTarget by remember { mutableStateOf<String?>(null) }
    var confirmRestoreAll by remember { mutableStateOf(false) }
    var confirmUploadModel by remember { mutableStateOf<String?>(null) }
    var confirmPublishModel by remember { mutableStateOf<String?>(null) }
    var uploadBusy by remember { mutableStateOf<String?>(null) }
    var uploadProgress by remember { mutableStateOf<String?>(null) }
    val paths = remember { CarModelPaths() }

    fun push(route: CarRoute) { routes.add(route) }
    fun back() { if (routes.size > 1) routes.removeAt(routes.lastIndex) }
    fun toRoot(target: CarModelsSection) { while (routes.size > 1) routes.removeAt(routes.lastIndex); section = target }
    fun requireService(): IRetr0UserService? = liveService.also { if (it == null) notice = CarNotice("Connect Shizuku to change game files.", false, CarModelErrorCode.SERVICE_UNAVAILABLE.code) }
    fun run(label: String, block: suspend () -> Unit) { if (busy == null) scope.launch { busy = label; try { block() } finally { busy = null } } }
    // Replace installs are reverted from their verified backup; add-ons are removed by deleting what they added.
    fun undoInstall(pkg: String, dir: String) {
        val addon = state.activeInstall(pkg, dir)?.takeIf { it.isAddon }
        if (addon != null) removeAddonTarget = addon else revertTarget = pkg to dir
    }

    val currentRoute = routes.last()
    BackHandler(routes.size > 1 && currentRoute !is CarRoute.Import) { back() }
    LaunchedEffect(currentRoute) { onFullScreenChange(currentRoute != CarRoute.Root) }
    DisposableEffect(Unit) { onDispose { onFullScreenChange(false) } }
    // The revert dialog always shows a fresh verification before Restore original can be pressed.
    LaunchedEffect(revertTarget, liveService) {
        val target = revertTarget ?: return@LaunchedEffect
        if (liveService == null || state.backup(target.first, target.second) == null) return@LaunchedEffect
        revertChecking = true
        viewModel.verifyBackup(liveService, target.first, target.second)
        revertChecking = false
    }

    val actions = CarModelsActions(
        onBrowse = { push(CarRoute.Gallery) },
        onImport = { push(CarRoute.Import(null)) },
        onBrowseLibrary = { section = CarModelsSection.LIBRARY },
        onScan = { requireService()?.let { svc -> run("Verifying backups…") { val problems = viewModel.verifyAllBackups(svc); notice = CarNotice(if (problems == 0) "All original backups verified." else "$problems backups need attention.", problems == 0) } } },
        onRestoreAll = { if (state.installed.any { it.active }) confirmRestoreAll = true else notice = CarNotice("No car models are applied.", true) },
        onOpenModel = { push(CarRoute.LocalDetails(it)) },
        onApplyModel = { push(CarRoute.Import(it)) },
        onRevert = ::undoInstall,
        onDeleteBackup = { deleteTarget = it },
        onVerifyBackups = { requireService()?.let { svc -> run("Verifying backups…") { viewModel.verifyAllBackups(svc) } } },
    )

    when (currentRoute) {
        CarRoute.Root -> CarModelsRoot(header, section, { section = it }, state, isServiceConnected, isGameInstalled, gamePackage, busy, notice, { notice = null }, actions, modifier)
        CarRoute.Gallery -> CarModelsGallery(
            viewModel.hub, gamePackage, state, modifier = modifier, onBack = ::back,
            onOpen = { push(CarRoute.HubDetails(it)) },
            onAction = { model, action -> if (action == CarModelAction.DOWNLOAD) push(CarRoute.HubDetails(model)) else toRoot(CarModelsSection.LIBRARY) },
            onMyModels = { toRoot(CarModelsSection.LIBRARY) },
        )
        is CarRoute.HubDetails -> HubCarModelDetails(currentRoute.model, viewModel.hub, gamePackage, ::back) { toRoot(CarModelsSection.LIBRARY) }
        is CarRoute.LocalDetails -> {
            val model = state.model(currentRoute.id)
            if (model == null) EmptyState(Icons.Filled.ErrorOutline, "Model not found", "This car model is no longer in your library.", "Back", ::back)
            else {
                val active = state.installed.any { it.active && it.modelId == model.id }
                CarModelDetailsScreen(
                    model.toDetailsUi(gamePackage), gamePackage, ::back,
                    primaryLabel = if (active) "Installed" else "Apply car model", primaryIcon = if (active) Icons.Filled.CheckCircle else Icons.Filled.Bolt,
                    primaryEnabled = !active, onPrimary = { push(CarRoute.Import(model.id)) }, modifier = modifier,
                    message = uploadProgress?.takeIf { uploadBusy == model.id }
                        ?: if (active) "Applied to ${state.installed.first { it.active && it.modelId == model.id }.vehicleDirectory}. Remote upload and publish do not change installed game files. Revert it from Applied." else notice?.text,
                    messageCode = notice?.code,
                    onRemove = if (active) null else ({ removeTarget = model.id }),
                    onRemoteDelete = if ((model.remotePublicId != null || model.remoteDraftId != null) && uploadBusy != model.id) ({ remoteDeleteTarget = model.id }) else null,
                    secondaryLabel = when {
                        model.remotePublicId != null -> "Published"
                        uploadBusy == model.id -> if (model.remoteDraftId != null) "Publishing asset…" else "Uploading…"
                        model.remoteDraftId != null -> "Publish asset to Community Hub"
                        else -> "Publish asset to Community Hub"
                    },
                    secondaryIcon = when {
                        model.remotePublicId != null -> Icons.Filled.Public
                        model.remoteDraftId != null -> Icons.Filled.Public
                        else -> Icons.Filled.Upload
                    },
                    // Remote lifecycle is independent of local installation: an active local model
                    // can still upload its unchanged imported files or publish an existing draft.
                    onSecondary = if (model.remotePublicId != null || uploadBusy == model.id) null else ({
                        if (model.remoteDraftId != null) confirmPublishModel = model.id else confirmUploadModel = model.id
                    }),
                )
            }
        }
        is CarRoute.Import -> CarModelImportWizard(
            viewModel, service, isServiceConnected, gamePackage, currentRoute.modelId,
            onExit = { toRoot(if (state.installed.any { it.active }) CarModelsSection.APPLIED else CarModelsSection.LIBRARY) },
            onViewBackups = { toRoot(CarModelsSection.BACKUPS) },
            onRevertRequested = { pkg, dir -> toRoot(CarModelsSection.APPLIED); undoInstall(pkg, dir) },
        )
    }

    revertTarget?.let { (pkg, dir) ->
        val backup = state.backup(pkg, dir)
        val installed = state.activeInstall(pkg, dir)
        RevertSheet(
            modelName = installed?.modelName.orEmpty(),
            targetPath = runCatching { paths.vehicleDir(pkg, dir) }.getOrDefault(dir),
            backupStatus = backup?.status, checking = revertChecking,
            fileCount = backup?.files?.size ?: 0, totalBytes = backup?.totalBytes ?: 0L,
            restoreEnabled = liveService != null && busy == null,
            onCancel = { revertTarget = null },
            onConfirm = {
                revertTarget = null
                requireService()?.let { svc -> run("Restoring original files…") {
                    viewModel.revert(svc, pkg, dir)
                        .onSuccess { notice = CarNotice("$it original files restored and verified.", true) }
                        .onFailure { notice = it.toNotice() }
                } }
            },
        )
    }
    removeAddonTarget?.let { install ->
        DestructiveConfirmationDialog(
            title = "Remove add-on?",
            body = "“${install.modelName}” will be uninstalled from ${install.vehicleDirectory}: the ${install.addedFiles.size} files it added are deleted" +
                (if (install.overwritten.isEmpty()) "." else " and the ${install.overwritten.size} files it overwrote are restored from their backup.") +
                " RR3 will be force-stopped. The model stays in your library.",
            confirmText = "Remove add-on",
            onConfirm = {
                removeAddonTarget = null
                requireService()?.let { svc -> run("Removing add-on…") {
                    viewModel.removeAddon(svc, install.gamePackage, install.vehicleDirectory)
                        .onSuccess { notice = CarNotice("Add-on removed from ${install.vehicleDirectory}.", true) }
                        .onFailure {
                            val error = it.toUiError()
                            notice = if (error.code == CarModelErrorCode.ADDON_REMOVE_FAILED.code || error.code == CarModelErrorCode.ADDON_RESTORE_FAILED.code) {
                                CarNotice("Add-on removal refused: one or more installed files were modified after installation or its transaction backup is unavailable. No files were removed.", false, error.code)
                            } else {
                                CarNotice(error.message, false, error.code)
                            }
                        }
                } }
            },
            onDismiss = { removeAddonTarget = null },
        )
    }
    deleteTarget?.let { backup ->
        val active = state.activeInstall(backup.gamePackage, backup.vehicleDirectory) != null
        DestructiveConfirmationDialog(
            title = "Delete this backup?",
            body = "The original files for ${backup.vehicleDirectory} (${backup.files.size} files, ${formatMb(backup.totalBytes)}) will be permanently deleted. Reverting this vehicle will be impossible." +
                if (active) "\n\nA car model is currently applied to this vehicle — its stock files will be unrecoverable from this app." else "",
            confirmText = "Delete backup",
            onConfirm = {
                deleteTarget = null
                requireService()?.let { svc -> run("Deleting backup…") {
                    viewModel.deleteBackup(svc, backup.gamePackage, backup.vehicleDirectory)
                        .onSuccess { notice = CarNotice("Backup deleted.", true) }.onFailure { notice = it.toNotice() }
                } }
            },
            onDismiss = { deleteTarget = null },
        )
    }
    remoteDeleteTarget?.let { id ->
        val model = state.model(id)
        DestructiveConfirmationDialog(
            title = "Delete Community Hub package?",
            body = "This permanently hides “${model?.name.orEmpty()}” from the Community Hub and schedules its package and media for deletion. Your local model files, installed game files, and original backups will be preserved.",
            confirmText = "Delete remote package",
            onConfirm = {
                remoteDeleteTarget = null
                scope.launch {
                    uploadBusy = id
                    uploadProgress = "Deleting remote package…"
                    viewModel.deleteRemote(context, id).onSuccess { notice = CarNotice("Remote package deleted. Local model and backups were preserved.", true) }.onFailure { notice = it.toNotice() }
                    uploadBusy = null
                    uploadProgress = null
                }
            },
            onDismiss = { remoteDeleteTarget = null },
        )
    }
    removeTarget?.let { id ->
        DestructiveConfirmationDialog(
            title = "Remove from library?",
            body = "“${state.model(id)?.name.orEmpty()}” and its imported files will be deleted from this app. Game files and backups are not touched.",
            confirmText = "Remove",
            onConfirm = { removeTarget = null; run("Removing…") { viewModel.removeModel(id).onSuccess { toRoot(CarModelsSection.LIBRARY) }.onFailure { notice = it.toNotice() } } },
            onDismiss = { removeTarget = null },
        )
    }
    if (confirmRestoreAll) {
        val active = state.installed.filter { it.active }
        DestructiveConfirmationDialog(
            title = "Restore all originals?",
            body = "${active.size} applied car ${if (active.size == 1) "model" else "models"} will be undone: ${active.joinToString(", ") { it.vehicleDirectory }}. Replaced vehicles get their stock RR3 files back from their verified backups (skipped if the backup is corrupt or missing); add-ons are removed.",
            confirmText = "Restore all",
            onConfirm = {
                confirmRestoreAll = false
                requireService()?.let { svc -> run("Restoring originals…") {
                    val (restored, failed) = viewModel.restoreAll(svc)
                    notice = CarNotice(if (failed == 0) "$restored vehicles restored." else "$restored restored, $failed could not be restored. See Activity for the error codes.", failed == 0)
                } }
            },
            onDismiss = { confirmRestoreAll = false },
        )
    }
    confirmUploadModel?.let { id ->
        val model = state.model(id)
        DestructiveConfirmationDialog(
            title = "Publish asset to Community Hub?",
            body = "Upload “${model?.name.orEmpty()}” and its package and processed media, validate its integrity, then publish it publicly to the Community Hub. This does not install, revert, or modify game files.",
            confirmText = "Publish",
            onConfirm = {
                confirmUploadModel = null
                uploadBusy = id
                uploadProgress = "Uploading…"
                scope.launch {
                    viewModel.uploadAndPublish(context, id, onProgress = { progress ->
                        val event = progress as? com.retr0.projectretr0.data.carModels.CarModelUploadManager.UploadProgress.Event
                        if (event != null) uploadProgress = "Uploading ${event.name} · ${event.completed}/${event.total}"
                    }, onStage = { stage -> uploadProgress = stage })
                        .onSuccess { notice = CarNotice("Car model published to the Community Hub.", true) }
                        .onFailure { notice = it.toNotice() }
                    uploadBusy = null
                    uploadProgress = null
                }
            },
            onDismiss = { confirmUploadModel = null },
        )
    }
    confirmPublishModel?.let { id ->
        val model = state.model(id)
        DestructiveConfirmationDialog(
            title = "Publish asset to Community Hub?",
            body = "Publish the already-uploaded package and media for “${model?.name.orEmpty()}” publicly to the Community Hub after integrity validation. This does not install, revert, or modify game files.",
            confirmText = "Publish",
            onConfirm = {
                confirmPublishModel = null
                uploadBusy = id
                uploadProgress = "Publishing asset…"
                scope.launch {
                    viewModel.publishDraft(context, id).onSuccess { notice = CarNotice("Car model published.", true) }.onFailure { notice = it.toNotice() }
                    uploadBusy = null
                    uploadProgress = null
                }
            },
            onDismiss = { confirmPublishModel = null },
        )
    }
}

@Composable
fun CarModelsRoot(
    header: @Composable () -> Unit,
    section: CarModelsSection,
    onSection: (CarModelsSection) -> Unit,
    state: CarModelState,
    connected: Boolean,
    gameInstalled: Boolean,
    gamePackage: GamePackage,
    busy: String?,
    notice: CarNotice?,
    onDismissNotice: () -> Unit,
    actions: CarModelsActions,
    modifier: Modifier = Modifier,
) {
    Column(modifier.fillMaxSize().background(RetroBg)) {
        header()
        SectionTabs(section, onSection)
        if (busy != null) Column(Modifier.fillMaxWidth().padding(horizontal = 18.dp, vertical = 8.dp)) {
            Text(busy, color = RetroTextSoft, style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(6.dp))
            LinearProgressIndicator(Modifier.fillMaxWidth(), color = RetroAccent, trackColor = RetroStatBg)
        }
        notice?.let { (text, good, code) ->
            Row(Modifier.fillMaxWidth().padding(horizontal = 18.dp, vertical = 6.dp).clickable(onClick = onDismissNotice)) { InfoNote(if (good) Icons.Filled.CheckCircle else Icons.Filled.ErrorOutline, text, if (good) RetroSuccess else RetroDanger, code = code) }
        }
        Box(Modifier.weight(1f).fillMaxWidth().wrapContentWidth(Alignment.CenterHorizontally).widthIn(max = 720.dp).padding(horizontal = 18.dp)) {
            when (section) {
                CarModelsSection.OVERVIEW -> OverviewSection(state, connected, gameInstalled, gamePackage, actions)
                CarModelsSection.LIBRARY -> LibrarySection(state, gamePackage, actions)
                CarModelsSection.APPLIED -> AppliedSection(state, connected, actions)
                CarModelsSection.BACKUPS -> BackupsSection(state, connected, actions)
                CarModelsSection.ACTIVITY -> ActivitySection(state.activity)
            }
        }
    }
}

@Composable
private fun SectionTabs(selected: CarModelsSection, onSection: (CarModelsSection) -> Unit) {
    Row(Modifier.fillMaxWidth().background(RetroSurfaceAlt).horizontalScroll(rememberScrollState()).padding(horizontal = 10.dp), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        CarModelsSection.entries.forEach { item ->
            val isSelected = item == selected
            Column(Modifier.heightIn(min = 48.dp).clickable { onSection(item) }.padding(horizontal = 10.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Text(item.label, maxLines = 1, color = if (isSelected) RetroAccent else RetroTextMuted, style = MaterialTheme.typography.labelLarge)
                Spacer(Modifier.height(5.dp))
                Box(Modifier.height(2.dp).width(if (isSelected) 24.dp else 0.dp).background(if (isSelected) RetroAccent else Color.Transparent, RoundedCornerShape(1.dp)))
            }
        }
    }
}

private val sectionPadding = PaddingValues(vertical = 14.dp)
private val ACTIVITY_CODE = Regex("""^\[(CM-[A-Z]+-\d{3})] """)

@Composable
private fun OverviewSection(state: CarModelState, connected: Boolean, installed: Boolean, pkg: GamePackage, actions: CarModelsActions) = LazyColumn(Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(12.dp), contentPadding = sectionPadding) {
    val unhealthy = state.backups.count { !it.verified }
    item {
        SectionHeader("System status", Icons.Filled.MonitorHeart)
        RetroCard {
            StatusLine(if (connected) "Shizuku service connected" else "Shizuku disconnected", connected, "Connect")
            StatusLine("RR3 ${pkg.short}${if (installed) " installed" else " missing"}", installed, "Install")
            StatusLine(when { state.backups.isEmpty() -> "No original backups yet"; unhealthy == 0 -> "Original backups verified"; else -> "$unhealthy original backups need attention" }, unhealthy == 0, "View")
        }
    }
    item {
        val stats = listOf("Models" to state.models.size, "Applied" to state.installed.count { it.active }, "Backups" to state.backups.size, "Issues" to unhealthy)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { stats.forEach { (label, value) -> RetroCard(Modifier.weight(1f)) { Text(value.toString(), color = RetroText, style = MaterialTheme.typography.titleLarge); Text(label, color = RetroTextMuted, maxLines = 1) } } }
    }
    item { WarningBanner("Backup required before replacing", "Replacing a vehicle overwrites many game files, so Apply stays locked until a verified local backup of the original exists. Add-ons change no existing files.") }
    if (!connected) item { WarningBanner("Privileged service unavailable", "Connect Shizuku to scan vehicles, back up, apply, or restore.") }
    item {
        SectionHeader("Quick actions", Icons.Filled.Bolt)
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { SecondaryButton("Browse models", Modifier.weight(1f), Icons.Filled.Public, actions.onBrowse); SecondaryButton("Import model", Modifier.weight(1f), Icons.Filled.Upload, actions.onImport) }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { SecondaryButton("Scan vehicles", Modifier.weight(1f), Icons.Filled.Search, actions.onScan); SecondaryButton("Restore all", Modifier.weight(1f), Icons.Filled.Restore, actions.onRestoreAll) }
        }
    }
}

@Composable
private fun LibrarySection(state: CarModelState, gamePackage: GamePackage, actions: CarModelsActions) = LazyColumn(Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(12.dp), contentPadding = sectionPadding) {
    item { SectionHeader("My models", Icons.Filled.Garage, trailingCount = state.models.size) }
    if (state.models.isEmpty()) item { EmptyState(Icons.Filled.DirectionsCar, "No car models yet", "Import a complete car-model folder to add it to your library.", "Import model", actions.onImport) }
    else {
        items(state.models, key = { it.id }) { model ->
            val active = state.installed.any { it.active && it.modelId == model.id }
            CarModelCard(model.toCardUi(gamePackage), if (active) CarModelAction.INSTALLED else CarModelAction.APPLY, { actions.onOpenModel(model.id) }, { actions.onApplyModel(model.id) })
        }
        item { SecondaryButton("Import another model", Modifier.fillMaxWidth(), Icons.Filled.Upload, actions.onImport) }
    }
}

@Composable
private fun AppliedSection(state: CarModelState, connected: Boolean, actions: CarModelsActions) = LazyColumn(Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(10.dp), contentPadding = sectionPadding) {
    val applied = state.installed.sortedWith(compareByDescending<InstalledCarModel> { it.active }.thenByDescending { it.appliedAt })
    item { SectionHeader("Installed models · ${applied.count { it.active }} applied", Icons.Filled.CheckCircle) }
    if (applied.isEmpty()) item { EmptyState(Icons.Filled.LayersClear, "No installed car models", "Models you apply will appear here with their backup status.") }
    else items(applied, key = { it.key }) { item ->
        InstalledModelCard(item, state.backup(item.gamePackage, item.vehicleDirectory)?.status, state.model(item.modelId)?.coverPath?.let(::File), connected, { actions.onOpenModel(item.modelId) }, { actions.onRevert(item.gamePackage, item.vehicleDirectory) }, state.model(item.modelId) != null)
    }
    item { SecondaryButton("Apply another model", Modifier.fillMaxWidth(), Icons.Filled.Add) { if (state.models.isEmpty()) actions.onImport() else actions.onBrowseLibrary() } }
}

@Composable
fun InstalledModelCard(item: InstalledCarModel, backupStatus: BackupStatus?, cover: Any?, connected: Boolean, onDetails: () -> Unit, onRevert: () -> Unit, detailsEnabled: Boolean = true) {
    RetroCard {
        Row(verticalAlignment = Alignment.CenterVertically) {
            ModelCover(cover, Modifier.size(64.dp))
            Spacer(Modifier.width(10.dp))
            Column(Modifier.weight(1f)) {
                Text(item.modelName, color = RetroText, style = MaterialTheme.typography.titleMedium, maxLines = 2, overflow = TextOverflow.Ellipsis)
                MonoText(item.vehicleDirectory, maxLines = 1)
            }
            Spacer(Modifier.width(8.dp))
            if (item.active) BadgePill("Active", Icons.Filled.CheckCircle, RetroSuccess) else BadgePill(if (item.isAddon) "Removed" else "Reverted", Icons.Filled.Restore, RetroTextMuted)
        }
        Spacer(Modifier.height(8.dp))
        Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            MetaPill(if (item.isAddon) "Add-on" else "Replace", color = RetroAccent)
            MetaPill(packageLabel(item.gamePackage)); MetaPill("Applied ${formatDate(item.appliedAt)}")
            when {
                item.isAddon && item.overwritten.isEmpty() -> MetaPill("Nothing overwritten", color = RetroSuccess)
                item.isAddon -> MetaPill("${item.overwritten.size} overwritten files backed up", color = RetroSuccess)
                backupStatus == BackupStatus.VERIFIED -> MetaPill("Backup verified", color = RetroSuccess)
                backupStatus == BackupStatus.CORRUPT -> MetaPill("Backup corrupt", color = RetroDanger)
                else -> MetaPill("Backup missing", color = RetroDanger)
            }
        }
        Spacer(Modifier.height(10.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            SmallActionButton("Details", Modifier.weight(1f), color = RetroTextDim, enabled = detailsEnabled, onClick = onDetails)
            if (item.isAddon) SmallActionButton("Remove", Modifier.weight(1f), Icons.Filled.Delete, color = RetroDanger, enabled = item.active && connected, onClick = onRevert)
            else SmallActionButton("Revert", Modifier.weight(1f), Icons.Filled.Restore, color = RetroDanger, enabled = item.active && connected && backupStatus == BackupStatus.VERIFIED, onClick = onRevert)
        }
    }
}

@Composable
private fun BackupsSection(state: CarModelState, connected: Boolean, actions: CarModelsActions) = LazyColumn(Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(10.dp), contentPadding = sectionPadding) {
    item {
        SectionHeader("Backups · Car-model originals", Icons.Filled.Backup, trailingCount = state.backups.size, trailing = {
            TextButton(onClick = actions.onVerifyBackups, enabled = connected && state.backups.isNotEmpty(), modifier = Modifier.heightIn(min = 48.dp)) { Text("Verify", color = if (connected && state.backups.isNotEmpty()) RetroAccent else RetroTextMuted) }
        })
    }
    item { WarningBanner("Keep your backups", "Deleting a backup makes reverting that vehicle impossible.") }
    // Add-ons appear here only when they actually backed up files they overwrote.
    val addonBackups = state.installed.filter { it.active && it.isAddon && it.overwritten.isNotEmpty() }
    if (state.backups.isEmpty() && addonBackups.isEmpty()) item { EmptyState(Icons.Filled.Backup, "No backups", "A verified backup of the original vehicle files is created before a vehicle is replaced. Add-ons only back up files they overwrite.") }
    else {
        items(state.backups.sortedByDescending { it.createdAt }, key = { it.key }) { backup ->
            BackupListRow(backup.vehicleDirectory, backup.gamePackage, backup.status, backup.problemCount, backup.files.size, backup.totalBytes, backup.createdAt, connected, { actions.onRevert(backup.gamePackage, backup.vehicleDirectory) }, { actions.onDeleteBackup(backup) }, tag = "Replace")
        }
        items(addonBackups, key = { "addon:" + it.key }) { install -> AddonBackupRow(install, connected) { actions.onRevert(install.gamePackage, install.vehicleDirectory) } }
    }
}

@Composable
private fun AddonBackupRow(install: InstalledCarModel, connected: Boolean, onRemove: () -> Unit) {
    RetroCard {
        Text(install.vehicleDirectory, color = RetroText, style = MaterialTheme.typography.titleMedium, maxLines = 1, overflow = TextOverflow.Ellipsis)
        MonoText("Overwritten by “${install.modelName}”", maxLines = 1)
        Spacer(Modifier.height(8.dp))
        Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            MetaPill("Add-on", color = RetroAccent); MetaPill(packageLabel(install.gamePackage)); MetaPill("${install.overwritten.size} files"); MetaPill(formatMb(install.overwritten.sumOf { it.sizeBytes }))
        }
        Spacer(Modifier.height(10.dp))
        Text("Restored automatically when the add-on is removed.", color = RetroTextMuted, style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.height(8.dp))
        SmallActionButton("Remove add-on", Modifier.fillMaxWidth(), Icons.Filled.Delete, color = RetroDanger, enabled = connected, onClick = onRemove)
    }
}

@Composable
private fun ActivitySection(activity: List<CarModelActivity>) = LazyColumn(Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(10.dp), contentPadding = sectionPadding) {
    item { SectionHeader("Operations", Icons.AutoMirrored.Filled.ListAlt, trailingCount = activity.size) }
    if (activity.isEmpty()) item { EmptyState(Icons.Filled.History, "No activity", "Car-model imports, backups, applies and restores are recorded here.") }
    else items(activity, key = { it.id }) { op ->
        RetroCard {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text("${op.kind.name.replace('_', ' ')} • ${op.title}", color = RetroText, maxLines = 2, overflow = TextOverflow.Ellipsis)
                    Text(formatDate(op.timestamp), color = RetroTextMuted, style = MaterialTheme.typography.labelMedium)
                }
                Text(if (op.success) "SUCCESS" else "FAILED", color = if (op.success) RetroSuccess else RetroDanger, style = MaterialTheme.typography.labelMedium)
            }
            val code = ACTIVITY_CODE.find(op.detail)?.groupValues?.get(1)
            Text(if (code == null) op.detail else op.detail.removePrefix("[$code] "), color = if (op.success) RetroTextDim else RetroDangerText, style = MaterialTheme.typography.bodyMedium)
            ErrorCodeText(code)
        }
    }
}
