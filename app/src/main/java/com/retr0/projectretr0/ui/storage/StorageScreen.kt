package com.retr0.projectretr0.ui.storage

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.retr0.projectretr0.IRetr0UserService
import com.retr0.projectretr0.data.storage.StorageCategory
import com.retr0.projectretr0.data.storage.StorageCategoryId
import com.retr0.projectretr0.data.storage.StorageRepository
import com.retr0.projectretr0.data.storage.StorageSnapshot
import com.retr0.projectretr0.ui.carmodels.BackHeader
import com.retr0.projectretr0.ui.carmodels.InfoNote
import com.retr0.projectretr0.ui.carmodels.SmallActionButton
import com.retr0.projectretr0.ui.carmodels.formatBytes
import com.retr0.projectretr0.ui.components.RetroCard
import com.retr0.projectretr0.ui.components.SectionHeader
import com.retr0.projectretr0.ui.shimmer
import com.retr0.projectretr0.ui.skins.DestructiveConfirmationDialog
import com.retr0.projectretr0.ui.skins.GamePackage
import com.retr0.projectretr0.ui.theme.*

/** Storage: what Project RETR0 uses on the device, with safe ways to reclaim space. */
@Composable
fun StorageScreen(
    service: IRetr0UserService?,
    isServiceConnected: Boolean,
    gamePackage: GamePackage,
    operationsIdle: Boolean,
    onBack: () -> Unit,
    onReviewBackups: () -> Unit,
    onStorageChanged: () -> Unit,
    modifier: Modifier = Modifier,
    onConnect: (() -> Unit)? = null,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val viewModel = remember(context, scope) { StorageViewModel(StorageRepository(context), scope) }
    val state by viewModel.state.collectAsState()
    val busy by viewModel.busy.collectAsState()
    val liveService = service.takeIf { isServiceConnected }
    var notice by remember { mutableStateOf<StorageNotice?>(null) }
    var confirm by remember { mutableStateOf<StorageCategoryId?>(null) }

    BackHandler(onBack = onBack)
    // Rescan when Shizuku connects or drops, since backups, music and the game folder are read through it.
    LaunchedEffect(liveService) { viewModel.refresh(liveService, gamePackage.id) }

    val onCleared: (StorageNotice) -> Unit = { notice = it; onStorageChanged() }
    StorageContent(
        state = state, busy = busy, notice = notice, connected = liveService != null, operationsIdle = operationsIdle, gamePackage = gamePackage,
        onBack = onBack, onRefresh = { viewModel.refresh(liveService, gamePackage.id) }, onDismissNotice = { notice = null },
        onClear = { confirm = it }, onReviewBackups = onReviewBackups, onConnect = onConnect, modifier = modifier,
    )

    confirm?.let { id ->
        val size = (state as? StorageUiState.Ready)?.snapshot?.bytes(id)?.let(::formatBytes) ?: "—"
        val cache = id == StorageCategoryId.CACHE
        DestructiveConfirmationDialog(
            title = if (cache) "Clear cache?" else "Clear downloads?",
            body = if (cache) "Deletes $size of temporary files, half-finished imports and cached images. They are re-created when needed. Your library, music and backups are not touched."
            else "Deletes $size of game-install downloads. They are downloaded again the next time you install. Your library, music and backups are not touched.",
            confirmText = if (cache) "Clear cache" else "Clear downloads",
            onConfirm = {
                confirm = null
                if (cache) viewModel.clearCache(liveService, gamePackage.id, onCleared) else viewModel.clearDownloads(liveService, gamePackage.id, onCleared)
            },
            onDismiss = { confirm = null },
        )
    }
}

private val GB = 1024L * 1024 * 1024
private val RR3Color = Color(0xFFF87171)

private fun categoryColor(id: StorageCategoryId): Color = when (id) {
    StorageCategoryId.BACKUPS -> RetroGold           // #F5C542
    StorageCategoryId.LIBRARY -> RetroAccent         // #3B82F6
    StorageCategoryId.DOWNLOADS -> RetroModelPurple  // #A78BFA
    StorageCategoryId.MUSIC -> RetroDiscord          // #5865F2
    StorageCategoryId.MEDIA -> RetroSuccess          // #34D399
    StorageCategoryId.CACHE -> RetroTextMuted        // #6B7280
}

private fun categorySubtitle(id: StorageCategoryId): String = when (id) {
    StorageCategoryId.BACKUPS -> "Car, skin, save & music originals"
    StorageCategoryId.LIBRARY -> "Imported car models & skins"
    StorageCategoryId.DOWNLOADS -> "Game-install downloads · re-downloadable"
    StorageCategoryId.MUSIC -> "Your music library"
    StorageCategoryId.MEDIA -> "Covers, screenshots & profile picture"
    StorageCategoryId.CACHE -> "Temporary files & image cache"
}

private fun sizeText(bytes: Long?) = bytes?.let(::formatBytes) ?: "—"

@Composable
fun StorageContent(
    state: StorageUiState,
    busy: String?,
    notice: StorageNotice?,
    connected: Boolean,
    operationsIdle: Boolean,
    gamePackage: GamePackage,
    onBack: () -> Unit,
    onRefresh: () -> Unit,
    onDismissNotice: () -> Unit,
    onClear: (StorageCategoryId) -> Unit,
    onReviewBackups: () -> Unit,
    modifier: Modifier = Modifier,
    onConnect: (() -> Unit)? = null,
) {
    val ready = state as? StorageUiState.Ready
    val snapshot = ready?.snapshot
    val canClear = operationsIdle && busy == null && snapshot != null
    Column(modifier.fillMaxSize().background(RetroBg)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            BackHeader("Storage", "What Project RETR0 is using", onBack, Modifier.weight(1f))
            IconButton(onClick = onRefresh, enabled = busy == null && ready?.refreshing != true, modifier = Modifier.padding(end = 6.dp)) {
                Icon(Icons.Filled.Refresh, "Refresh", tint = RetroTextDim)
            }
        }
        if (busy != null || ready?.refreshing == true) LinearProgressIndicator(Modifier.fillMaxWidth(), color = RetroAccent, trackColor = RetroStatBg)
        LazyColumn(
            Modifier.weight(1f).fillMaxWidth().wrapContentWidth(Alignment.CenterHorizontally).widthIn(max = 720.dp).padding(horizontal = 18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp), contentPadding = PaddingValues(vertical = 14.dp),
        ) {
            notice?.let { n -> item { Box(Modifier.clickable(onClick = onDismissNotice)) { InfoNote(if (n.good) Icons.Filled.CheckCircle else Icons.Filled.ErrorOutline, n.text, if (n.good) RetroSuccess else RetroDanger, code = n.code) } } }
            if (snapshot == null) { item { LoadingSkeleton() }; return@LazyColumn }

            // 1 · Hero
            item {
                RetroCard {
                    Text("Used by RETR0", color = RetroTextMuted, style = MaterialTheme.typography.labelMedium)
                    Text(formatBytes(snapshot.appTotalBytes), color = RetroText, style = MaterialTheme.typography.headlineSmall)
                    Text("of ${formatBytes(snapshot.deviceFreeBytes)} free", color = RetroTextDim, style = MaterialTheme.typography.bodyMedium)
                    Spacer(Modifier.height(12.dp))
                    StackedBar(snapshot.categories.map { (it.bytes ?: 0L) to categoryColor(it.id) }, fillTo = snapshot.appTotalBytes)
                }
            }
            if (!connected) item {
                Box(Modifier.then(if (onConnect != null) Modifier.clickable(onClick = onConnect) else Modifier)) {
                    InfoNote(Icons.Filled.LinkOff, "Connect Shizuku to measure backups, music and the RR3 install. Those show “—” until then." + if (onConnect != null) " Tap to connect." else "", RetroGold)
                }
            }

            // 2 · RETR0 usage list
            item {
                SectionHeader("RETR0 usage", Icons.Filled.DataUsage)
                RetroCard {
                    snapshot.categories.forEachIndexed { i, category ->
                        if (i > 0) HorizontalDivider(color = RetroBorder)
                        CategoryRow(category, canClear, onClear, onReviewBackups)
                    }
                }
            }

            // 3 · Reclaim space
            item {
                SectionHeader("Reclaim space", Icons.Filled.CleaningServices)
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (!operationsIdle) InfoNote(Icons.Filled.HourglassTop, "An install or download is running. Clearing is paused until it finishes.", RetroAccent)
                    SmallActionButton("Clear cache · ${sizeText(snapshot.bytes(StorageCategoryId.CACHE))}", Modifier.fillMaxWidth(), Icons.Filled.DeleteSweep, color = RetroDanger, enabled = canClear) { onClear(StorageCategoryId.CACHE) }
                    SmallActionButton("Clear downloads · ${sizeText(snapshot.bytes(StorageCategoryId.DOWNLOADS))}", Modifier.fillMaxWidth(), Icons.Filled.Download, color = RetroDanger, enabled = canClear) { onClear(StorageCategoryId.DOWNLOADS) }
                    SmallActionButton("Review backups · ${sizeText(snapshot.bytes(StorageCategoryId.BACKUPS))}", Modifier.fillMaxWidth(), Icons.Filled.Backup, color = RetroGold, onClick = onReviewBackups)
                }
            }

            // 4 · Backup safety note
            item { InfoNote(Icons.Filled.Shield, "Clearing cache or downloads is always safe. Deleting a backup frees space but makes reverting that vehicle impossible — backups can only be deleted from the Backups screen.", RetroGold) }

            // 5 · Device
            item {
                val game = snapshot.gameBytes
                val known = snapshot.deviceTotalBytes > 0 && snapshot.deviceFreeBytes >= 0
                val other = if (known) (snapshot.deviceTotalBytes - snapshot.deviceFreeBytes - (game ?: 0L) - snapshot.appTotalBytes).coerceAtLeast(0L) else -1L
                SectionHeader("Device", Icons.Filled.PhoneAndroid)
                RetroCard {
                    if (known) {
                        StackedBar(listOf((game ?: 0L) to RR3Color, snapshot.appTotalBytes to RetroGold, other to RetroTextMuted), fillTo = snapshot.deviceTotalBytes)
                        Spacer(Modifier.height(8.dp))
                    }
                    LegendRow(RR3Color, "RR3 (${gamePackage.id.substringAfterLast('.')})", sizeText(game))
                    LegendRow(RetroGold, "Project RETR0", formatBytes(snapshot.appTotalBytes))
                    LegendRow(RetroTextMuted, "Other", formatBytes(other))
                    LegendRow(RetroStatBg, "Free", if (known) "${formatBytes(snapshot.deviceFreeBytes)} of ${formatBytes(snapshot.deviceTotalBytes)}" else "—")
                }
            }
        }
    }
}

@Composable
private fun CategoryRow(category: StorageCategory, canClear: Boolean, onClear: (StorageCategoryId) -> Unit, onReviewBackups: () -> Unit) {
    Row(Modifier.fillMaxWidth().heightIn(min = 56.dp).padding(vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
        Dot(categoryColor(category.id))
        Spacer(Modifier.width(10.dp))
        Column(Modifier.weight(1f)) {
            Text(category.label, color = RetroText, style = MaterialTheme.typography.bodyLarge, maxLines = 1)
            Text(categorySubtitle(category.id), color = RetroTextMuted, style = MaterialTheme.typography.labelMedium, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
        Spacer(Modifier.width(8.dp))
        Text(sizeText(category.bytes), color = if (category.bytes == null) RetroTextMuted else RetroTextSoft, style = MaterialTheme.typography.bodyMedium, maxLines = 1)
        when (category.id) {
            StorageCategoryId.BACKUPS -> RowAction("Manage", RetroGold, true, onReviewBackups)
            StorageCategoryId.DOWNLOADS, StorageCategoryId.CACHE -> RowAction("Clear", RetroAccent, canClear && (category.bytes ?: 0L) > 0L) { onClear(category.id) }
            else -> Unit
        }
    }
}

@Composable
private fun RowAction(text: String, color: Color, enabled: Boolean, onClick: () -> Unit) {
    TextButton(onClick = onClick, enabled = enabled, modifier = Modifier.heightIn(min = 48.dp)) {
        Text(text, color = if (enabled) color else RetroTextMuted, style = MaterialTheme.typography.labelLarge)
    }
}

@Composable
private fun LegendRow(color: Color, label: String, value: String) {
    Row(Modifier.fillMaxWidth().padding(vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
        Dot(color)
        Spacer(Modifier.width(10.dp))
        Text(label, color = RetroTextSoft, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.weight(1f))
        Text(value, color = RetroText, style = MaterialTheme.typography.bodyMedium, maxLines = 1)
    }
}

@Composable
private fun Dot(color: Color) = Box(Modifier.size(10.dp).clip(CircleShape).background(color))

/** Horizontal bar with one segment per entry, widths proportional to bytes against [fillTo]. */
@Composable
private fun StackedBar(segments: List<Pair<Long, Color>>, fillTo: Long) {
    val total = fillTo.coerceAtLeast(segments.sumOf { it.first.coerceAtLeast(0L) }).coerceAtLeast(1L)
    // Tiny non-zero segments still get a sliver so every category is visible.
    val minWeight = total * 0.012f
    Row(Modifier.fillMaxWidth().height(12.dp).clip(RoundedCornerShape(6.dp)).background(RetroStatBg)) {
        var used = 0f
        segments.forEach { (bytes, color) ->
            if (bytes > 0) {
                val weight = maxOf(bytes.toFloat(), minWeight)
                used += weight
                Box(Modifier.weight(weight).fillMaxHeight().background(color))
            }
        }
        val rest = total - used
        if (rest > 0f) Spacer(Modifier.weight(rest))
    }
}

@Composable
private fun LoadingSkeleton() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        RetroCard {
            Box(Modifier.width(110.dp).height(12.dp).clip(RoundedCornerShape(4.dp)).shimmer())
            Spacer(Modifier.height(8.dp))
            Box(Modifier.width(160.dp).height(24.dp).clip(RoundedCornerShape(4.dp)).shimmer())
            Spacer(Modifier.height(14.dp))
            Box(Modifier.fillMaxWidth().height(12.dp).clip(RoundedCornerShape(6.dp)).shimmer())
        }
        RetroCard {
            repeat(6) {
                Row(Modifier.fillMaxWidth().padding(vertical = 10.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(Modifier.size(10.dp).clip(CircleShape).shimmer())
                    Spacer(Modifier.width(10.dp))
                    Box(Modifier.weight(1f).height(14.dp).clip(RoundedCornerShape(4.dp)).shimmer())
                    Spacer(Modifier.width(24.dp))
                    Box(Modifier.width(56.dp).height(14.dp).clip(RoundedCornerShape(4.dp)).shimmer())
                }
            }
        }
        Text("Measuring folders…", color = RetroTextMuted, style = MaterialTheme.typography.labelMedium)
    }
}

private fun previewSnapshot() = StorageSnapshot(
    categories = listOf(
        StorageCategory(StorageCategoryId.BACKUPS, "Backups", 812L * 1024 * 1024, emptyList()),
        StorageCategory(StorageCategoryId.LIBRARY, "Library", 1_430L * 1024 * 1024, emptyList()),
        StorageCategory(StorageCategoryId.DOWNLOADS, "Downloads", 2L * GB, emptyList()),
        StorageCategory(StorageCategoryId.MUSIC, "Music", 214L * 1024 * 1024, emptyList()),
        StorageCategory(StorageCategoryId.MEDIA, "Media", 38L * 1024 * 1024, emptyList()),
        StorageCategory(StorageCategoryId.CACHE, "Cache", 96L * 1024 * 1024, emptyList()),
    ),
    appTotalBytes = 4_638L * 1024 * 1024, deviceFreeBytes = 672L * GB, deviceTotalBytes = 1_000L * GB, gameBytes = 3L * GB,
)

@Preview(widthDp = 412, heightDp = 1400)
@Composable
private fun StorageReadyPreview() = StorageContent(StorageUiState.Ready(previewSnapshot()), null, null, true, true, GamePackage.ROW, {}, {}, {}, {}, {})

@Preview(widthDp = 412, heightDp = 900)
@Composable
private fun StorageLoadingPreview() = StorageContent(StorageUiState.Loading, null, null, false, true, GamePackage.ROW, {}, {}, {}, {}, {})
