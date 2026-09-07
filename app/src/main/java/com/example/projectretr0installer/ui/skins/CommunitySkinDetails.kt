package com.example.projectretr0installer.ui.skins

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import com.example.projectretr0installer.data.skins.SkinApiException
import com.example.projectretr0installer.data.skins.SkinRepository
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
) {
    val runtimeRepository = repository ?: remember { SkinRepository() }
    var state by remember(previewState) { mutableStateOf(previewState ?: DetailsState.LOADING) }
    var skin by remember(previewSkin) { mutableStateOf(previewSkin) }
    var retry by remember { mutableIntStateOf(0) }
    LaunchedEffect(id, retry, previewState) {
        if (previewState != null || previewSkin != null) return@LaunchedEffect
        state = DetailsState.LOADING
        runCatching { runtimeRepository.details(id) }
            .onSuccess { skin = it; state = DetailsState.CONTENT }
            .onFailure { state = if (it is SkinApiException && it.statusCode == 404) DetailsState.NOT_FOUND else DetailsState.ERROR }
    }
    when (state) {
        DetailsState.LOADING -> Column(Modifier.fillMaxSize().padding(18.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) { ScreenTopBar("Skin details", "Loading", onBack); LoadingSkinCard() }
        DetailsState.ERROR -> DetailsMessage(onBack, "Couldn't load skin", "Check your connection and try again.", "Retry") { retry++ }
        DetailsState.NOT_FOUND -> DetailsMessage(onBack, "Skin not found", "This community skin may have been removed.")
        DetailsState.CONTENT -> skin?.let { SkinDetailsContent(it, onBack, onApply, galleryItems) }
            ?: DetailsMessage(onBack, "Skin not found", "No details are available.")
    }
}

@Composable private fun DetailsMessage(onBack: () -> Unit, title: String, body: String, action: String? = null, onAction: () -> Unit = {}) {
    Column(Modifier.fillMaxSize().padding(horizontal = 18.dp)) {
        ScreenTopBar("Skin details", title, onBack)
        EmptyState(Icons.Filled.ErrorOutline, title, body, action, if (action == null) null else onAction)
    }
}

@Composable private fun SkinDetailsContent(skin: CommunitySkin, onBack: () -> Unit, onApply: () -> Unit, galleryItems: List<CommunitySkin>) {
    var sheet by remember { mutableStateOf(false) }
    var liked by remember { mutableStateOf(false) }
    Column(Modifier.fillMaxSize()) {
        Column(
            Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 18.dp)
                .padding(bottom = 96.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            ScreenTopBar("Skin details", skin.vehicle, onBack) {
                IconButton({}) { Icon(Icons.Filled.Share, "Share (simulated)", tint = RetroTextSoft) }
                IconButton({}) { Icon(Icons.Filled.Flag, "Report (simulated)", tint = RetroTextSoft) }
            }
            ScreenshotCarousel(skin.screenshotUrls.ifEmpty { listOfNotNull(skin.coverUrl) })
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) { Text(skin.title, color = RetroText, style = MaterialTheme.typography.headlineSmall); Text("by ${skin.creator}", color = RetroTextDim) }
                IconButton({ liked = !liked }) { Icon(if (liked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder, "Like (simulated)", tint = RetroDanger) }
                Text((skin.likes + if (liked) 1 else 0).toString(), color = RetroTextDim)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(7.dp)) {
                RetroStatCell("Downloads", skin.downloads.toString(), Modifier.weight(1f))
                RetroStatCell("Size", formatSize(skin.binarySizeBytes), Modifier.weight(1f))
                RetroStatCell("Uploaded", skin.uploadedLabel, Modifier.weight(1f))
            }
            RetroCard { CompatibilityBadge(skin.compatibility); Spacer(Modifier.height(6.dp)); Text(compatibilityText(skin.compatibility), color = RetroTextSoft, style = MaterialTheme.typography.bodySmall) }
            MetadataCard(skin.vehicle, skin.carId, skin.slotLabel, skin.slotKey, skin.format, skin.gameVersions)
            if (skin.description.isNotBlank()) Text(skin.description, color = RetroTextSoft)
            skin.sourceFilename?.let { DataRow("Source file", it) }
            skin.binarySha256?.let { DataRow("SHA-256", it) }
            skin.updatedAt?.let { DataRow("Updated", it) }
            PrimaryButton("Download & apply (simulated)", icon = Icons.Filled.Download, onClick = { sheet = true })
            OutlineButton("Download to library (simulated)") {}
            val related = galleryItems.filter { it.id != skin.id && (it.creator == skin.creator || it.carId == skin.carId) }.take(2)
            if (related.isNotEmpty()) { SectionHeader("Related skins", Icons.Filled.DirectionsCar); MiniSkinRow(related) }
            Spacer(Modifier.height(12.dp))
        }
    }
    if (sheet) DownloadApplySheet(skin, { sheet = false }, { sheet = false; onApply() })
}

private fun compatibilityText(c: Compatibility) = when (c) {
    Compatibility.COMPATIBLE -> "Compatible with the detected game package and texture slot."
    Compatibility.WRONG_FORMAT -> "This format may need conversion before it can be applied."
    Compatibility.TARGET_MISSING -> "The target vehicle files were not detected."
    Compatibility.GAME_NOT_DETECTED -> "Compatibility will be checked when local target scanning is implemented."
}
private fun formatSize(bytes: Long) = String.format(Locale.US, "%.2f MiB", bytes / 1024.0 / 1024.0)
@Composable fun MetadataCard(vehicle: String, carId: String, slotLabel: String, slotKey: String, format: String, versions: String? = null) { RetroCard { DataRow("Vehicle", vehicle); DataRow("Car ID", carId); DataRow("Slot", "$slotLabel ($slotKey)"); DataRow("Format", format); versions?.takeIf { it.isNotBlank() }?.let { DataRow("Game versions", it) } } }
@Composable fun DataRow(label: String, value: String) { Row(Modifier.fillMaxWidth().padding(vertical = 5.dp)) { Text(label, color = RetroTextMuted, modifier = Modifier.weight(1f)); Text(value, color = RetroTextSoft, style = MaterialTheme.typography.bodySmall, modifier = Modifier.weight(1f)) } }
@Composable private fun MiniSkinRow(items: List<CommunitySkin>) { Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { items.forEach { Column(Modifier.weight(1f)) { SkinCover(Modifier.fillMaxWidth().aspectRatio(1.5f), imageUrl = it.coverUrl); Text(it.title, color = RetroText, maxLines = 1) } } } }
