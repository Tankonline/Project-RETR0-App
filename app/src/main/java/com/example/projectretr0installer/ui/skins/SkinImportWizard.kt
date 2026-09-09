package com.example.projectretr0installer.ui.skins

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.InsertDriveFile
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import java.util.Locale
import com.example.projectretr0installer.ui.components.*
import com.example.projectretr0installer.ui.theme.*

data class ImportState(val step: Int = 1, val source: SelectedSkinSource? = null, val vehicle: String = "Audi R18 e-tron", val gamePackage: GamePackage = GamePackage.ROW, val target: SelectedSkinTarget? = null, val images: List<ProcessedSkinImage> = emptyList(), val apply: Boolean = false, val upload: Boolean = false, val guidelines: Boolean = false)

@Composable fun SkinImportWizard(onBack: () -> Unit, onDone: () -> Unit, initialStep: Int = 1, modifier: Modifier = Modifier, browserSource: SkinTargetBrowserSource = SkinTargetBrowserSource { _, _ -> BrowserResult.Error("Privileged service is disconnected.") }, onTargetBrowserVisibilityChange: (Boolean) -> Unit = {}) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var state by remember { mutableStateOf(ImportState(step = initialStep)) }
    var browsing by remember { mutableStateOf(false) }
    var sourceLoading by remember { mutableStateOf(false) }
    var sourceError by remember { mutableStateOf<String?>(null) }
    var imageError by remember { mutableStateOf<String?>(null) }
    var imageLoading by remember { mutableStateOf(false) }
    val imageCache = remember(context) { SkinImportCache(context) }
    val imageProcessor = remember(context, imageCache) { SkinImageProcessor(context.contentResolver, imageCache) }
    DisposableEffect(imageCache) { onDispose { imageCache.cleanup() } }
    LaunchedEffect(browsing) { onTargetBrowserVisibilityChange(browsing) }
    DisposableEffect(Unit) { onDispose { onTargetBrowserVisibilityChange(false) } }
    var name by remember { mutableStateOf("Aurora Drift") }; var creator by remember { mutableStateOf("Retr0") }; var description by remember { mutableStateOf("") }
    val sourcePicker = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        if (uri != null) {
            sourceLoading = true
            sourceError = null
            runCatching { context.contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION) }
            scope.launch {
                inspectSkinSource(context.contentResolver, uri)
                    .onSuccess { selected -> state = state.copy(source = selected, target = null); if (name == "Aurora Drift") name = selected.fileName.removeSuffix(".dds.z") }
                    .onFailure { sourceError = it.message ?: "Unable to read the selected skin file." }
                sourceLoading = false
            }
        }
    }
    val imagePicker = rememberLauncherForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(5)) { uris ->
        if (uris.isNotEmpty()) scope.launch {
            imageLoading = true; imageError = null
            val remaining = (5 - state.images.size).coerceAtLeast(0)
            for (uri in uris.take(remaining)) {
                val role = if (state.images.isEmpty()) SkinImageRole.COVER else SkinImageRole.SCREENSHOT
                imageProcessor.process(uri, role).onSuccess { image ->
                    if (state.images.any { it.sourceHash == image.sourceHash }) { imageCache.delete(image.file); imageError = "Duplicate image skipped." }
                    else state = state.copy(images = state.images + image)
                }.onFailure { imageError = it.message ?: "Unable to process the selected image." }
            }
            imageLoading = false
        }
    }
    suspend fun promote(image: ProcessedSkinImage) {
        if (image.role == SkinImageRole.COVER) return
        imageLoading = true; imageError = null
        val oldCover = state.images.firstOrNull { it.role == SkinImageRole.COVER }
        val newCover = imageProcessor.changeRole(image, SkinImageRole.COVER).getOrElse { imageError = it.message; imageLoading = false; return }
        val oldScreenshot = oldCover?.let { imageProcessor.changeRole(it, SkinImageRole.SCREENSHOT).getOrElse { error -> imageCache.delete(newCover.file); imageError = error.message; imageLoading = false; return } }
        val replacements = state.images.map { when (it.id) { image.id -> newCover; oldCover?.id -> oldScreenshot ?: it; else -> it } }
        imageCache.delete(image.file); oldCover?.let { imageCache.delete(it.file) }
        state = state.copy(images = replacements); imageLoading = false
    }
    val importedFileName = state.source?.fileName.orEmpty()
    if (browsing) { SkinTargetBrowserScreen(SkinTargetValidator.root(state.gamePackage), importedFileName, browserSource, { browsing = false }, { state = state.copy(target = it); browsing = false }, modifier); return }
    val labels = listOf("Choose file", "Match vehicle", "Details", "Review")
    val valid = when (state.step) { 1 -> state.source != null && !sourceLoading; 2 -> state.target != null; 3 -> !imageLoading && (!state.upload || state.images.any { it.role == SkinImageRole.COVER }); 4 -> !state.upload || state.guidelines; else -> true }
    Column(modifier.fillMaxSize().imePadding()) {
        Column(Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(horizontal = 18.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            ScreenTopBar("Import skin", labels[state.step - 1], onBack); WizardStepIndicator(state.step, label = labels[state.step - 1])
            when (state.step) {
                1 -> {
                    SectionHeader("Car skin file", Icons.AutoMirrored.Filled.InsertDriveFile)
                    SecondaryButton(if (state.source == null) "Choose .dds.z file" else "Choose another file", Modifier.fillMaxWidth(), icon = Icons.Filled.FolderOpen) { sourcePicker.launch(arrayOf("application/octet-stream", "application/zip", "*/*")) }
                    if (sourceLoading) { LinearProgressIndicator(Modifier.fillMaxWidth()); Text("Reading and verifying the file...", color = RetroTextMuted) }
                    sourceError?.let { Text(it, color = RetroDanger) }
                    state.source?.let { source ->
                        RetroCard { DataRow("Name", source.fileName); DataRow("Size", formatSourceSize(source.size)); DataRow("Type", "DDS.Z"); DataRow("Format", source.format); DataRow("SHA-256", source.sha256.chunked(16).joinToString("\n")) }
                        SectionHeader("Detected metadata", Icons.Filled.AutoAwesome)
                        RetroCard { CompatibilityBadge(Compatibility.GAME_NOT_DETECTED); DataRow("Target", "Exterior livery"); DataRow("Next", "Choose the exact RR3 target file") }
                    } ?: Text("Select the custom skin file you want to import.", color = RetroTextMuted)
                }
                2 -> { SectionHeader("Target", Icons.Filled.DirectionsCar); VehicleSelector(state.vehicle) { state = state.copy(vehicle = it, target = null) }; GamePackageSelector(state.gamePackage) { state = state.copy(gamePackage = it, target = null) }; SecondaryButton("Browse RR3 Files", Modifier.fillMaxWidth(), icon = Icons.Filled.FolderOpen) { browsing = true }; RetroCard { DataRow("Package", state.gamePackage.short); DataRow("Selected target", state.target?.relativePath ?: "No target selected"); DataRow("Exact filename", if (state.target != null) "Match" else "Required") }; if (!valid) Text("Choose an existing exact-name exterior livery target before continuing.", color = RetroDanger) }
                3 -> { SectionHeader("Skin details", Icons.Filled.Edit); OutlinedTextField(name, { name = it }, label = { Text("Name") }, modifier = Modifier.fillMaxWidth()); OutlinedTextField(creator, { creator = it }, label = { Text("Creator") }, modifier = Modifier.fillMaxWidth()); OutlinedTextField(description, { if (it.length <= 280) description = it }, label = { Text("Description") }, supportingText = { Text("${description.length} / 280") }, minLines = 3, modifier = Modifier.fillMaxWidth()); ScreenshotPicker(state.images, onAdd = { imagePicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }, onRemove = { removed -> imageCache.delete(removed.file); val rest = state.images - removed; if (removed.role == SkinImageRole.COVER && rest.isNotEmpty()) scope.launch { state = state.copy(images = rest); promote(rest.first()) } else state = state.copy(images = rest) }, onSetCover = { scope.launch { promote(it) } }); if (imageLoading) LinearProgressIndicator(Modifier.fillMaxWidth()); imageError?.let { Text(it, color = RetroDanger) }; if (state.upload && state.images.none { it.role == SkinImageRole.COVER }) Text("A cover image is required for community upload.", color = RetroDanger) }
                4 -> { SectionHeader("Options", Icons.Filled.Tune); UploadOptionsCard(state.apply, state.upload, { state = state.copy(apply = it) }, { state = state.copy(upload = it) }); if (state.upload) CommunityGuidelinesCard(state.guidelines) { state = state.copy(guidelines = it) }; SectionHeader("Summary", Icons.Filled.Summarize); RetroCard { DataRow("Skin", name); DataRow("Source", state.source?.fileName ?: "Not selected"); DataRow("Vehicle", "${state.vehicle} · Exterior livery"); DataRow("Package", state.gamePackage.short); DataRow("Target", state.target?.relativePath ?: "Not selected"); DataRow("Cover", if (state.images.any { it.role == SkinImageRole.COVER }) "Selected" else "Not selected"); DataRow("Screenshots", state.images.count { it.role == SkinImageRole.SCREENSHOT }.toString()) } }
            }; Spacer(Modifier.height(12.dp))
        }
        Row(Modifier.fillMaxWidth().padding(18.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) { SecondaryButton(if (state.step == 1) "Cancel" else "Back", Modifier.weight(1f)) { if (state.step == 1) { imageCache.cleanup(); onBack() } else state = state.copy(step = state.step - 1) }; Box(Modifier.weight(1f)) { PrimaryButton(if (state.step < 4) "Continue" else "Finish", Modifier.fillMaxWidth()) { if (valid) { if (state.step < 4) state = state.copy(step = state.step + 1) else { imageCache.cleanup(); onDone() } } } } }
    }
}

private fun formatSourceSize(bytes: Long): String = String.format(Locale.US, "%.2f MiB", bytes / 1024.0 / 1024.0)
