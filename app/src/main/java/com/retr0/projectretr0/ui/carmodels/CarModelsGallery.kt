package com.retr0.projectretr0.ui.carmodels

import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.retr0.projectretr0.data.carModels.CarModelErrorCode
import com.retr0.projectretr0.data.carModels.CarModelHubRepository
import com.retr0.projectretr0.data.carModels.CarModelLog
import com.retr0.projectretr0.data.carModels.asCarModelException
import com.retr0.projectretr0.data.carModels.CarModelHubUnavailableException
import com.retr0.projectretr0.data.carModels.CommunityCarModel
import com.retr0.projectretr0.data.carModels.local.CarModelFileGroup
import com.retr0.projectretr0.data.carModels.local.CarModelFileGroups
import com.retr0.projectretr0.data.carModels.local.CarModelState
import com.retr0.projectretr0.data.carModels.local.LocalCarModel
import com.retr0.projectretr0.ui.components.*
import com.retr0.projectretr0.ui.skins.EmptyState
import com.retr0.projectretr0.ui.skins.GamePackage
import com.retr0.projectretr0.ui.skins.LoadingSkinCard
import com.retr0.projectretr0.ui.skins.SkinFilterRow
import com.retr0.projectretr0.ui.theme.*
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException

enum class CarGalleryState { LOADING, CONTENT, EMPTY, OFFLINE, ERROR, UNAVAILABLE }

data class CarModelDetailsUi(
    val name: String,
    val creator: String,
    val updatedLabel: String,
    val description: String,
    val vehicleDirectory: String,
    val supportedPackages: List<String>,
    val fileCount: Int,
    val totalBytes: Long,
    val manifestVerified: Boolean,
    val gameBuild: String,
    val compat: CompatLevel,
    val fileSummary: String,
    val cover: Any? = null,
    val screenshots: List<Any> = emptyList(),
)

fun CommunityCarModel.toCardUi(gamePackage: GamePackage) = CarModelCardUi(id, name, vehicleDirectory, creator, supportedPackages, fileCount, totalBytes, compatFor(supportedPackages, gamePackage), verifiedManifest, likes, downloads, coverUrl)
fun LocalCarModel.toCardUi(gamePackage: GamePackage) = CarModelCardUi(id, name, vehicleDirectory, creator, supportedPackages, fileCount, totalBytes, compatFor(supportedPackages, gamePackage), verified = true, cover = coverPath?.let(::File))

fun CommunityCarModel.toDetailsUi(gamePackage: GamePackage) = CarModelDetailsUi(
    name, creator, "updated $updatedAt", description, vehicleDirectory, supportedPackages, fileCount, totalBytes, verifiedManifest, gameBuild,
    compatFor(supportedPackages, gamePackage), "$fileCount files", coverUrl, screenshotUrls,
)

fun LocalCarModel.toDetailsUi(gamePackage: GamePackage): CarModelDetailsUi {
    val groups = CarModelFileGroups.summarize(manifest.files.map { it.sourcePath })
    val parts = listOfNotNull(
        groups[CarModelFileGroup.MESHES]?.let { "meshes (.m3g)" }, groups[CarModelFileGroup.POINTS]?.let { "points" },
        groups[CarModelFileGroup.LIVERIES]?.let { "liveries (.dds.z)" }, groups[CarModelFileGroup.BINARIES]?.let { "binaries (.bin)" },
        groups[CarModelFileGroup.OTHER]?.let { "other files" },
    )
    return CarModelDetailsUi(
        name, creator, "imported ${formatDate(importedAt)}", description, vehicleDirectory, supportedPackages, fileCount, totalBytes, manifestVerified = true,
        gameBuild = gameVersion, compat = compatFor(supportedPackages, gamePackage),
        fileSummary = "$fileCount files across ${parts.joinToString(", ").ifBlank { "the vehicle folder" }}",
        cover = coverPath?.let(::File), screenshots = screenshotPaths.map(::File),
    )
}

/** Hub model → what its card button should do, based on what is already in the local library. */
fun actionFor(model: CommunityCarModel, local: CarModelState): CarModelAction {
    val downloaded = local.models.firstOrNull { it.remoteId == model.id } ?: return CarModelAction.DOWNLOAD
    return if (local.installed.any { it.active && it.modelId == downloaded.id }) CarModelAction.INSTALLED else CarModelAction.APPLY
}

@Composable
fun CarModelsGallery(
    repository: CarModelHubRepository,
    gamePackage: GamePackage,
    local: CarModelState,
    onOpen: (CommunityCarModel) -> Unit,
    onAction: (CommunityCarModel, CarModelAction) -> Unit,
    onMyModels: () -> Unit,
    modifier: Modifier = Modifier,
    onBack: (() -> Unit)? = null,
    previewState: CarGalleryState? = null,
    previewItems: List<CommunityCarModel> = emptyList(),
    refreshToken: Int = 0,
    sortOrder: String = "newest",
    onRefresh: () -> Unit = {},
) {
    var query by remember { mutableStateOf("") }
    var filter by remember { mutableIntStateOf(0) }
    var retry by remember { mutableIntStateOf(0) }
    var state by remember(previewState) { mutableStateOf(previewState ?: CarGalleryState.LOADING) }
    var models by remember(previewState) { mutableStateOf(if (previewState != null) previewItems else emptyList()) }
    LaunchedEffect(query, retry, refreshToken, sortOrder, previewState) {
        if (previewState != null) return@LaunchedEffect
        kotlinx.coroutines.delay(250)

        // A refresh is authoritative: never keep rendering the previous catalog while the
        // Worker is unavailable or has removed an asset-backed row.
        models = emptyList()
        state = CarGalleryState.LOADING
        val result = runCatching { repository.list(query, sortOrder) }
        result.onSuccess { items ->
            models = refreshedCatalogItems(result)
            state = if (items.isEmpty()) CarGalleryState.EMPTY else CarGalleryState.CONTENT
        }.onFailure { error ->
            // Do not leave old downloadable rows visible on transport, 404, or parse errors.
            models = emptyList()
            error.asCarModelException().let { CarModelLog.fail(it.error, "hub list failed: ${it.message}") }
            state = when (error) {
                is CarModelHubUnavailableException -> CarGalleryState.UNAVAILABLE
                is IOException -> CarGalleryState.OFFLINE
                else -> if (error.cause is IOException) CarGalleryState.OFFLINE else CarGalleryState.ERROR
            }
        }
    }
    val visible = remember(models, filter, sortOrder) {
        val ordered = when (sortOrder) {
            "downloads" -> models.sortedByDescending { it.downloads }
            "likes" -> models.sortedByDescending { it.likes }
            else -> models.sortedByDescending { it.updatedAt }
        }
        when (filter) {
            1 -> ordered.filter { it.verifiedManifest }
            2 -> ordered.filter { GamePackage.ROW.id in it.supportedPackages }
            3 -> ordered.filter { GamePackage.NA.id in it.supportedPackages }
            4 -> ordered.sortedByDescending { it.updatedAt }
            else -> ordered
        }
    }
    Column(modifier.fillMaxSize().background(RetroBg)) {
        if (onBack != null) BackHeader("Car Models", "Community", onBack)
        LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(horizontal = if (onBack != null) 18.dp else 0.dp, vertical = 10.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(Modifier.weight(1f)) {
                        RetroSearchField(query, { query = it }, "Search car models…", Icons.Filled.Search)
                    }
                    IconButton(onClick = onRefresh) {
                        Icon(Icons.Filled.Refresh, contentDescription = "Refresh car models", tint = RetroAccent)
                    }
                }
            }
            item { SkinFilterRow(listOf("All", "Verified", "Global", "N. America", "Newest ▾"), filter) { filter = it } }
            when {
                state == CarGalleryState.LOADING -> items(2) { LoadingSkinCard() }
                state == CarGalleryState.UNAVAILABLE -> item { EmptyState(Icons.Filled.CloudOff, "Community Hub unavailable", "We couldn't load published car models right now. Models you import stay in My Models. (${CarModelErrorCode.HUB_UNAVAILABLE.code})", "Open My Models", onMyModels) }
                state == CarGalleryState.OFFLINE -> item { EmptyState(Icons.Filled.WifiOff, "You're offline", "Reconnect to browse community car models. Models you've already downloaded stay available in My Models.", "Retry") { retry++ } }
                state == CarGalleryState.ERROR -> item { EmptyState(Icons.Filled.ErrorOutline, "Couldn't load models", "Something went wrong on our side. Your downloads and backups are unaffected.", "Try again") { retry++ } }
                state == CarGalleryState.EMPTY || visible.isEmpty() -> item { EmptyState(Icons.Filled.DirectionsCar, "No car models found", "Try another search or clear the filters.", "Clear filters") { query = ""; filter = 0 } }
                else -> items(visible, key = { it.id }) { model ->
                    val action = actionFor(model, local)
                    CarModelCard(model.toCardUi(gamePackage), action, { onOpen(model) }, { onAction(model, action) })
                }
            }
        }
    }
}

@Composable
fun CarModelDetailsScreen(
    ui: CarModelDetailsUi,
    gamePackage: GamePackage,
    onBack: () -> Unit,
    primaryLabel: String,
    primaryIcon: androidx.compose.ui.graphics.vector.ImageVector,
    onPrimary: () -> Unit,
    modifier: Modifier = Modifier,
    primaryEnabled: Boolean = true,
    message: String? = null,
    messageCode: String? = null,
    onShare: (() -> Unit)? = null,
    onReport: (() -> Unit)? = null,
    onRemove: (() -> Unit)? = null,
    onRemoteDelete: (() -> Unit)? = null,
    secondaryLabel: String? = null,
    secondaryIcon: androidx.compose.ui.graphics.vector.ImageVector? = null,
    onSecondary: (() -> Unit)? = null,
) {
    Column(modifier.fillMaxSize().background(RetroBg)) {
        BackHeader(ui.name, "Car model", onBack)
        LazyColumn(Modifier.weight(1f), contentPadding = PaddingValues(horizontal = 18.dp, vertical = 8.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            item {
                Box(Modifier.fillMaxWidth().aspectRatio(16f / 9f)) {
                    ModelCover(ui.cover, Modifier.fillMaxSize())
                    Row(Modifier.align(Alignment.BottomStart).padding(8.dp), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        BadgePill("Complete car model", Icons.Filled.DirectionsCar, RetroAccent, Modifier.background(RetroBg.copy(alpha = .85f), RoundedCornerShape(50)))
                        if (ui.manifestVerified) VerifiedBadge(text = "Verified manifest")
                    }
                }
            }
            item {
                Text(ui.name, color = RetroText, style = MaterialTheme.typography.headlineMedium)
                Text("by ${ui.creator.ifBlank { "Unknown creator" }} · ${ui.updatedLabel}", color = RetroTextMuted, style = MaterialTheme.typography.bodyMedium)
            }
            item {
                when (ui.compat) {
                    CompatLevel.COMPATIBLE -> InfoNote(Icons.Filled.CheckCircle, "Compatible with your install · RR3 ${gamePackage.short}", RetroSuccess)
                    CompatLevel.DIFFERENT_BUILD -> InfoNote(Icons.Filled.WarningAmber, "Supported packages aren't declared — check it matches RR3 ${gamePackage.short}", RetroGold)
                    CompatLevel.INCOMPATIBLE -> InfoNote(Icons.Filled.Block, "Not made for your install · RR3 ${gamePackage.short}", RetroDanger)
                }
            }
            if (ui.description.isNotBlank()) item { Text(ui.description, color = RetroTextSoft, style = MaterialTheme.typography.bodyLarge) }
            if (ui.screenshots.isNotEmpty()) item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) { items(ui.screenshots) { shot -> ModelCover(shot, Modifier.width(200.dp).aspectRatio(16f / 9f)) } }
            }
            item {
                SectionHeader("Model info", Icons.Filled.Info)
                RetroCard {
                    InfoRow("Target vehicle", ui.vehicleDirectory, mono = true)
                    InfoRow("Supported packages", ui.supportedPackages.joinToString(", ") { packageLabel(it) }.ifBlank { "Not declared" })
                    InfoRow("Files", ui.fileCount.toString())
                    InfoRow("Total size", formatMb(ui.totalBytes))
                    InfoRow("Manifest", if (ui.manifestVerified) "Verified · SHA-256" else "Unverified", valueColor = if (ui.manifestVerified) RetroSuccess else RetroGold)
                    InfoRow("Game build", ui.gameBuild.ifBlank { "Not specified" })
                }
            }
            item { InfoNote(Icons.Filled.Info, "This is a complete car model — ${ui.fileSummary}. Not a single skin texture.", RetroAccent) }
            if (onRemoteDelete != null) item { SmallActionButton("Delete Community Hub package", Modifier.fillMaxWidth(), Icons.Filled.DeleteForever, color = RetroDanger, onClick = onRemoteDelete) }
            if (onRemove != null) item { SmallActionButton("Remove from library", Modifier.fillMaxWidth(), Icons.Filled.Delete, color = RetroDanger, onClick = onRemove) }
        }
        Column(Modifier.fillMaxWidth().background(RetroSurfaceAlt).padding(horizontal = 18.dp, vertical = 12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            message?.let { Text(it, color = RetroGold, style = MaterialTheme.typography.bodyMedium); ErrorCodeText(messageCode) }
            PrimaryButton(primaryLabel, icon = primaryIcon, enabled = primaryEnabled, onClick = onPrimary)
            if (secondaryLabel != null && onSecondary != null) {
                SecondaryButton(secondaryLabel, Modifier.fillMaxWidth(), secondaryIcon, onClick = onSecondary)
            }
            if (onShare != null || onReport != null) Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                onShare?.let { SecondaryButton("Share", Modifier.weight(1f), Icons.Filled.Share, it) }
                onReport?.let { SecondaryButton("Report", Modifier.weight(1f), Icons.Filled.Flag, it) }
            }
        }
    }
}

/** Hub model details wired to the (currently stubbed) repository. Failures are shown, never simulated away. */
@Composable
fun HubCarModelDetails(model: CommunityCarModel, repository: CarModelHubRepository, gamePackage: GamePackage, onBack: () -> Unit, onDownloaded: (String) -> Unit) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var currentModel by remember(model.id) { mutableStateOf(model) }
    var message by remember { mutableStateOf<String?>(null) }
    var messageCode by remember { mutableStateOf<String?>(null) }
    fun failed(what: String, error: Throwable) { val coded = error.asCarModelException(); CarModelLog.fail(coded.error, "hub $what failed: ${coded.message} (model=${model.id})"); message = coded.message; messageCode = coded.error.code }
    LaunchedEffect(model.id) {
        runCatching { repository.details(model.id) }
            .onSuccess { currentModel = it }
            .onFailure { error ->
                failed("details", error)
                if ((error as? com.retr0.projectretr0.data.skins.SkinApiException)?.statusCode == 404) onBack()
            }
    }
    var downloading by remember { mutableStateOf(false) }
    CarModelDetailsScreen(
        ui = currentModel.toDetailsUi(gamePackage), gamePackage = gamePackage, onBack = onBack,
        primaryLabel = if (downloading) "Downloading…" else "Download model · ${formatMb(model.totalBytes)}", primaryIcon = Icons.Filled.Download, primaryEnabled = !downloading, message = message, messageCode = messageCode,
        onPrimary = {
            downloading = true
            scope.launch {
                runCatching { repository.download(model) { _, _ -> } }.onSuccess(onDownloaded).onFailure { failed("download", it) }
                downloading = false
            }
        },
        onShare = {
            val url = model.shareUrl
            if (url == null) { message = "This model has no share link yet."; messageCode = null }
            else context.startActivity(Intent.createChooser(Intent(Intent.ACTION_SEND).apply { type = "text/plain"; putExtra(Intent.EXTRA_TEXT, url) }, "Share car model"))
        },
        onReport = { scope.launch { runCatching { repository.report(model) }.onSuccess { message = "Report sent. Thank you."; messageCode = null }.onFailure { failed("report", it) } } },
    )
}

/** Car Models tab for the Community Hub: gallery plus a full-screen details drill-in. */
@Composable
fun CarModelsHubTab(gamePackage: GamePackage, onOpenMyModels: () -> Unit, modifier: Modifier = Modifier, onFullScreenChange: (Boolean) -> Unit = {}, refreshToken: Int = 0, sortOrder: String = "newest", onRefresh: () -> Unit = {}) {
    val context = LocalContext.current
    val viewModel = remember(context) { CarModelsViewModel.get(context) }
    val local by viewModel.state.collectAsState()
    var selectedId by rememberSaveable { mutableStateOf<String?>(null) }
    var cache by remember { mutableStateOf(emptyMap<String, CommunityCarModel>()) }
    val selected = selectedId?.let(cache::get)
    LaunchedEffect(refreshToken) {
        // A refresh invalidates both the gallery and any detail snapshot. Otherwise
        // switching tabs can keep rendering a model that the new catalog removed.
        selectedId = null
        cache = emptyMap()
    }
    BackHandler(selected != null) { selectedId = null }
    LaunchedEffect(selected != null) { onFullScreenChange(selected != null) }
    DisposableEffect(Unit) { onDispose { onFullScreenChange(false) } }
    Box(modifier.fillMaxSize()) {
        if (selected != null) HubCarModelDetails(selected, viewModel.hub, gamePackage, { selectedId = null }) { onOpenMyModels() }
        else CarModelsGallery(
            viewModel.hub, gamePackage, local,
            onOpen = { cache = cache + (it.id to it); selectedId = it.id },
            onAction = { model, action -> if (action == CarModelAction.DOWNLOAD) { cache = cache + (model.id to model); selectedId = model.id } else onOpenMyModels() },
            onMyModels = onOpenMyModels,
            refreshToken = refreshToken,
            sortOrder = sortOrder,
            onRefresh = onRefresh,
        )
    }
}
