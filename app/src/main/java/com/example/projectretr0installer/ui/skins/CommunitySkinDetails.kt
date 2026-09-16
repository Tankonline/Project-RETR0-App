package com.example.projectretr0installer.ui.skins

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.LocalContext
import android.content.Intent
import android.util.Log
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.projectretr0installer.data.skins.SkinApiException
import com.example.projectretr0installer.data.skins.SkinDownloadManager
import com.example.projectretr0installer.data.skins.SkinReportReason
import com.example.projectretr0installer.data.skins.SkinRepository
import kotlinx.coroutines.launch
import com.example.projectretr0installer.ui.components.*
import com.example.projectretr0installer.ui.theme.*
import java.util.Locale

enum class DetailsState { LOADING, CONTENT, ERROR, NOT_FOUND }

@Composable fun CommunitySkinDetails(
    id: String,
    onBack: () -> Unit,
    onApply: () -> Unit,
    repository: SkinRepository? = null,
    galleryItems: List<CommunitySkin> = emptyList(),
    previewSkin: CommunitySkin? = null,
    previewState: DetailsState? = null,
    previewTechnicalExpanded: Boolean = false,
    communityViewModel: SkinCommunityViewModel? = null,
) {
    val context = LocalContext.current
    val runtimeRepository = repository ?: remember(context) { SkinRepository(context) }
    val runtimeViewModel = communityViewModel ?: remember(context, runtimeRepository) {
        SkinCommunityViewModel(runtimeRepository, SkinDownloadManager(context))
    }
    val scope = rememberCoroutineScope()
    var actionMessage by remember { mutableStateOf<String?>(null) }
    var reportDialog by remember { mutableStateOf(false) }
    var deleteDialog by remember { mutableStateOf(false) }
    var state by remember(previewState) { mutableStateOf(previewState ?: DetailsState.LOADING) }
    var skin by remember(previewSkin) { mutableStateOf(previewSkin) }
    var loadError by remember(id) { mutableStateOf<String?>(null) }
    var retry by remember { mutableIntStateOf(0) }
    LaunchedEffect(id, retry, previewState) {
        if (previewState != null || previewSkin != null) return@LaunchedEffect
        state = DetailsState.LOADING
        loadError = null
        runCatching { runtimeRepository.details(id) }
            .onSuccess { skin = it; state = DetailsState.CONTENT }
            .onFailure { error ->
                loadError = error.toDetailDiagnostic(id)
                state = if (error is SkinApiException && error.statusCode == 404) DetailsState.NOT_FOUND else DetailsState.ERROR
            }
    }
    DetailsShell(
        onBack = onBack,
        subtitle = skin?.vehicle ?: state.label,
        onShare = {
            val current = skin ?: return@DetailsShell
            val shareText = current.shareUrl ?: current.coverUrl ?: ""
            Log.i("COMMUNITY_SKIN", "Sharing skin link: $shareText")
            if (shareText.isNotEmpty()) {
                context.startActivity(Intent.createChooser(Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, shareText)
                }, "Share skin link"))
            }
        },
        onReport = { reportDialog = true },
        onDelete = { deleteDialog = true },
    ) {
        when (state) {
            DetailsState.LOADING -> item { LoadingSkinCard() }
            DetailsState.ERROR -> item {
                EmptyState(
                    Icons.Filled.ErrorOutline,
                    "Couldn't load skin",
                    loadError ?: "Check your connection and try again.",
                    "Retry",
                ) { retry++ }
            }
            DetailsState.NOT_FOUND -> item {
                EmptyState(
                    Icons.Filled.ErrorOutline,
                    "Skin not found",
                    loadError ?: "This community skin may have been removed.",
                )
            }
            DetailsState.CONTENT -> skin?.let { current ->
                SkinDetailsItems(
                    current,
                    galleryItems = galleryItems,
                    initiallyExpanded = previewTechnicalExpanded,
                    onLike = { liked ->
                        scope.launch {
                            runtimeViewModel.setLike(current.id, liked)
                                .onSuccess { likes -> skin = skin?.copy(likes = likes ?: skin!!.likes) }
                                .onFailure { actionMessage = it.message ?: "Unable to update like" }
                        }
                    },
                    onDownload = { applyAfterDownload ->
                        scope.launch {
                            runtimeViewModel.download(current)
                                .onSuccess { actionMessage = "Saved to My skins"; if (applyAfterDownload) onApply() }
                                .onFailure { actionMessage = it.message ?: "Unable to download skin" }
                        }
                    },
                    onReport = { reportDialog = true },
                    onDelete = { deleteDialog = true },
                )
            } ?: item { EmptyState(Icons.Filled.ErrorOutline, "Skin not found", "No details are available.") }
        }
    }
    actionMessage?.let { message ->
        AlertDialog(onDismissRequest = { actionMessage = null }, confirmButton = { TextButton({ actionMessage = null }) { Text("OK") } }, title = { Text("Skin action") }, text = { Text(message) })
    }
    if (deleteDialog) {
        AlertDialog(
            onDismissRequest = { deleteDialog = false },
            title = { Text("Delete community skin?") },
            text = { Text("This permanently removes your community upload and its media. This cannot be undone.") },
            confirmButton = {
                TextButton(onClick = {
                    deleteDialog = false
                    scope.launch {
                        runtimeViewModel.deleteOwned(id)
                            .onSuccess { onBack() }
                            .onFailure { error -> actionMessage = error.toDetailDiagnostic(id) }
                    }
                }) { Text("Delete", color = RetroDanger) }
            },
            dismissButton = { TextButton(onClick = { deleteDialog = false }) { Text("Cancel") } },
        )
    }
    if (reportDialog) {
        AlertDialog(
            onDismissRequest = { reportDialog = false },
            confirmButton = { TextButton({ reportDialog = false; scope.launch { runtimeViewModel.report(id, SkinReportReason.OTHER, "Reported from Android app").onFailure { actionMessage = it.message } } }) { Text("Report") } },
            dismissButton = { TextButton({ reportDialog = false }) { Text("Cancel") } },
            title = { Text("Report skin") },
            text = { Text("Report this skin for review?") },
        )
    }
}

private fun Throwable.toDetailDiagnostic(skinId: String): String = when (this) {
    is SkinApiException -> buildString {
        append(errorCode ?: "skin_details_failed")
        statusCode?.let { append(" (HTTP $it)") }
        append(": ")
        append(message ?: "The server rejected the details request.")
        requestId?.let { append(" [requestId: $it]") }
        append(" [skinId: $skinId]")
    }
    else -> "${this::class.java.simpleName}: ${message ?: "Unable to load skin details"} [skinId: $skinId]"
}

private val DetailsState.label get() = when (this) {
    DetailsState.LOADING -> "Loading"
    DetailsState.CONTENT -> "Community skin"
    DetailsState.ERROR -> "Unavailable"
    DetailsState.NOT_FOUND -> "Unavailable"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable private fun DetailsShell(
    onBack: () -> Unit,
    subtitle: String,
    onShare: () -> Unit,
    onReport: () -> Unit,
    onDelete: () -> Unit,
    content: LazyListScope.() -> Unit,
) {
    var overflow by remember { mutableStateOf(false) }
    Scaffold(
        containerColor = RetroBg,
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = {
            TopAppBar(
                title = { Column { Text("Skin details", maxLines = 1); Text(subtitle, color = RetroTextMuted, style = MaterialTheme.typography.labelSmall, maxLines = 1, overflow = TextOverflow.Ellipsis) } },
                navigationIcon = { IconButton(onBack) { Icon(Icons.Filled.ArrowBack, "Back") } },
                actions = {
                    Box {
                        IconButton({ overflow = true }) { Icon(Icons.Filled.MoreVert, "More actions") }
                        DropdownMenu(overflow, { overflow = false }) {
                            DropdownMenuItem(text = { Text("Share") }, leadingIcon = { Icon(Icons.Filled.Share, null) }, onClick = { overflow = false; onShare() })
                            DropdownMenuItem(text = { Text("Report") }, leadingIcon = { Icon(Icons.Filled.Flag, null) }, onClick = { overflow = false; onReport() })
                            DropdownMenuItem(text = { Text("Delete from Community Hub") }, leadingIcon = { Icon(Icons.Filled.Delete, null, tint = RetroDanger) }, onClick = { overflow = false; onDelete() })
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = RetroBg, titleContentColor = RetroText, navigationIconContentColor = RetroTextSoft, actionIconContentColor = RetroTextSoft),
            )
        },
    ) { padding ->
        LazyColumn(
            Modifier.fillMaxSize().padding(padding).consumeWindowInsets(padding),
            contentPadding = PaddingValues(horizontal = 18.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            content = content,
        )
    }
}

private fun LazyListScope.SkinDetailsItems(
    skin: CommunitySkin,
    galleryItems: List<CommunitySkin>,
    initiallyExpanded: Boolean,
    onLike: (Boolean) -> Unit,
    onDownload: (Boolean) -> Unit,
    onReport: () -> Unit,
    onDelete: () -> Unit,
) {
    item { ResponsiveHero(skin) }
    item { SkinIdentity(skin, onLike) }
    item { CompatibilitySummary(skin.compatibility) }
    item { DownloadActions(skin, onDownload) }
    item { OutlineButton("Report skin", onClick = onReport) }
    if (skin.isOwner) item { DangerButton("Delete from Community Hub", icon = Icons.Filled.Delete, onClick = onDelete) }
    item { ResponsiveStats(skin) }
    if (skin.description.isNotBlank()) item { Text(skin.description, color = RetroTextSoft) }
    item { TechnicalDetails(skin, initiallyExpanded) }
    val related = galleryItems.filter { it.id != skin.id && (it.creator == skin.creator || it.carId == skin.carId) }.take(2)
    if (related.isNotEmpty()) {
        item { SectionHeader("Related skins", Icons.Filled.DirectionsCar) }
        item { MiniSkinRow(related) }
    }
}

@Composable private fun ResponsiveHero(skin: CommunitySkin) {
    val configuration = LocalConfiguration.current
    val ratio = if (configuration.screenHeightDp < 520) 1.95f else 1.72f
    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        ScreenshotCarousel(
            urls = skin.screenshotUrls.ifEmpty { listOfNotNull(skin.coverUrl) },
            modifier = Modifier.widthIn(max = 760.dp).fillMaxWidth(),
            aspectRatio = ratio,
        )
    }
}

@Composable private fun SkinIdentity(skin: CommunitySkin, onLike: (Boolean) -> Unit) {
    var liked by remember(skin.id, skin.likedByMe) { mutableStateOf(skin.likedByMe) }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(Modifier.weight(1f)) {
            Text(skin.title, color = RetroText, style = MaterialTheme.typography.headlineSmall)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("by ${skin.creator}", color = RetroTextDim)
                if (skin.verified) { Spacer(Modifier.width(8.dp)); Icon(Icons.Filled.Verified, "Verified creator", tint = RetroGold, modifier = Modifier.size(18.dp)) }
            }
        }
        IconButton({ liked = !liked; onLike(liked) }) { Icon(if (liked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder, if (liked) "Unlike" else "Like", tint = RetroDanger) }
        Text(skin.likes.toString(), color = RetroTextDim)
    }
}

@Composable private fun CompatibilitySummary(c: Compatibility) {
    RetroCard { Row(verticalAlignment = Alignment.CenterVertically) { CompatibilityBadge(c); Spacer(Modifier.width(10.dp)); Text(compatibilityText(c), color = RetroTextSoft, style = MaterialTheme.typography.bodySmall, modifier = Modifier.weight(1f)) } }
}

@Composable private fun DownloadActions(skin: CommunitySkin, onDownload: (Boolean) -> Unit) {
    var sheet by remember { mutableStateOf(false) }
    val configuration = LocalConfiguration.current
    Box {
        if (configuration.screenWidthDp >= 600) Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            PrimaryButton("Download & apply", Modifier.weight(1f), Icons.Filled.Download) { sheet = true }
            OutlineButton("Download to library", Modifier.weight(1f)) { onDownload(false) }
        } else Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            PrimaryButton("Download & apply", icon = Icons.Filled.Download) { sheet = true }
            OutlineButton("Download to library") { onDownload(false) }
        }
    }
    if (sheet) DownloadApplySheet(skin, { sheet = false }, { sheet = false; onDownload(true) })
}

@Composable private fun ResponsiveStats(skin: CommunitySkin) {
    val configuration = LocalConfiguration.current
    Box {
        if (configuration.screenWidthDp < 350) Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(7.dp)) {
                RetroStatCell("Downloads", skin.downloads.toString(), Modifier.weight(1f))
                RetroStatCell("Size", formatSize(skin.binarySizeBytes), Modifier.weight(1f))
            }
            RetroStatCell("Uploaded", skin.uploadedLabel, Modifier.fillMaxWidth())
        } else Row(horizontalArrangement = Arrangement.spacedBy(7.dp)) {
            RetroStatCell("Downloads", skin.downloads.toString(), Modifier.weight(1f))
            RetroStatCell("Size", formatSize(skin.binarySizeBytes), Modifier.weight(1f))
            RetroStatCell("Uploaded", skin.uploadedLabel, Modifier.weight(1f))
        }
    }
}

@Composable private fun TechnicalDetails(skin: CommunitySkin, initiallyExpanded: Boolean) {
    var expanded by remember(initiallyExpanded) { mutableStateOf(initiallyExpanded) }
    RetroCard {
        TextButton(onClick = { expanded = !expanded }, modifier = Modifier.fillMaxWidth()) {
            Text("Technical details", color = RetroText, modifier = Modifier.weight(1f))
            Icon(if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore, if (expanded) "Collapse technical details" else "Expand technical details", tint = RetroTextMuted)
        }
        if (expanded) {
            DetailsMetadataRow("Vehicle", skin.vehicle)
            DetailsMetadataRow("Car ID", skin.carId, technical = true)
            DetailsMetadataRow("Slot", "${skin.slotLabel} (${skin.slotKey})")
            DetailsMetadataRow("Format", skin.format)
            skin.gameVersions?.takeIf { it.isNotBlank() }?.let { DetailsMetadataRow("Game versions", it) }
            skin.sourceFilename?.let { DetailsMetadataRow("Source file", it, longValue = true, technical = true) }
            skin.binarySha256?.let { DetailsMetadataRow("SHA-256", it, longValue = true, technical = true) }
            skin.updatedAt?.let { DetailsMetadataRow("Updated", it, longValue = true) }
        }
    }
}

@Composable private fun DetailsMetadataRow(label: String, value: String, longValue: Boolean = false, technical: Boolean = false) {
    val configuration = LocalConfiguration.current
    Box(Modifier.fillMaxWidth().padding(vertical = 5.dp)) {
        val stacked = longValue || configuration.screenWidthDp < 360
        val valueText: @Composable (Modifier) -> Unit = { modifier ->
            SelectionContainer(modifier) { Text(value, color = RetroTextSoft, style = MaterialTheme.typography.bodySmall.copy(fontFamily = if (technical) FontFamily.Monospace else FontFamily.Default)) }
        }
        if (stacked) Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(label, color = RetroTextMuted, style = MaterialTheme.typography.labelSmall)
            valueText(Modifier.fillMaxWidth())
        } else Row {
            Text(label, color = RetroTextMuted, modifier = Modifier.weight(.35f))
            valueText(Modifier.weight(.65f))
        }
    }
}

private fun compatibilityText(c: Compatibility) = when (c) {
    Compatibility.COMPATIBLE -> "Ready for the detected game package and texture slot."
    Compatibility.WRONG_FORMAT -> "This format may need conversion before it can be applied."
    Compatibility.TARGET_MISSING -> "The target vehicle files were not detected."
    Compatibility.GAME_NOT_DETECTED -> "Compatibility will be checked when a target is available."
}
private fun formatSize(bytes: Long) = String.format(Locale.US, "%.2f MiB", bytes / 1024.0 / 1024.0)
@Composable fun MetadataCard(vehicle: String, carId: String, slotLabel: String, slotKey: String, format: String, versions: String? = null) { RetroCard { DataRow("Vehicle", vehicle); DataRow("Car ID", carId); DataRow("Slot", "$slotLabel ($slotKey)"); DataRow("Format", format); versions?.takeIf { it.isNotBlank() }?.let { DataRow("Game versions", it) } } }
@Composable fun DataRow(label: String, value: String) { Row(Modifier.fillMaxWidth().padding(vertical = 5.dp)) { Text(label, color = RetroTextMuted, modifier = Modifier.weight(1f)); Text(value, color = RetroTextSoft, style = MaterialTheme.typography.bodySmall, modifier = Modifier.weight(1f)) } }
@Composable private fun MiniSkinRow(items: List<CommunitySkin>) { Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { items.forEach { Column(Modifier.weight(1f)) { SkinCover(Modifier.fillMaxWidth().aspectRatio(1.5f), imageUrl = it.coverUrl); Text(it.title, color = RetroText, maxLines = 1) } } } }
