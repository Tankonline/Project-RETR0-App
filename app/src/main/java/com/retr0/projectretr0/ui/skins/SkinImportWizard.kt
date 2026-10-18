package com.retr0.projectretr0.ui.skins

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.InsertDriveFile
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.retr0.projectretr0.data.skins.SkinRepository
import com.retr0.projectretr0.data.skins.local.LocalSkinRepository
import com.retr0.projectretr0.ui.components.*
import com.retr0.projectretr0.ui.theme.*
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.math.roundToInt

data class ImportState(val step: Int = 1, val source: SelectedSkinSource? = null, val vehicle: String = "Audi R18 e-tron", val gamePackage: GamePackage = GamePackage.ROW, val target: SelectedSkinTarget? = null, val images: List<ProcessedSkinImage> = emptyList(), val apply: Boolean = false, val upload: Boolean = false, val guidelines: Boolean = false)

private const val CROP_WIZARD_LOG_TAG = "SkinCropWizard"

@Composable
fun SkinImportWizard(onBack: () -> Unit, onDone: () -> Unit, initialStep: Int = 1, modifier: Modifier = Modifier, browserSource: SkinTargetBrowserSource = SkinTargetBrowserSource { _, _ -> BrowserResult.Error("Privileged service is disconnected.") }, onTargetBrowserVisibilityChange: (Boolean) -> Unit = {}) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var state by remember { mutableStateOf(ImportState(step = initialStep)) }
    var browsing by remember { mutableStateOf(false) }
    var sourceLoading by remember { mutableStateOf(false) }
    var sourceError by remember { mutableStateOf<String?>(null) }
    var imageError by remember { mutableStateOf<String?>(null) }
    var imageLoading by remember { mutableStateOf(false) }
    var pendingUris by remember { mutableStateOf<List<Uri>>(emptyList()) }
    var pendingRole by remember { mutableStateOf(SkinImageRole.COVER) }
    var cropRequest by remember { mutableStateOf<ImageCropRequest?>(null) }
    var manualImageCropRequest by remember { mutableStateOf<ImageCropRequest?>(null) }
    val imageCache = remember(context) { SkinImportCache(context) }
    val imageProcessor = remember(context, imageCache) { SkinImageProcessor(context.contentResolver, imageCache) }
    val viewModel = remember(context) { SkinImportViewModel(LocalSkinRepository(context), SkinRepository(context)) }
    val importProgress by viewModel.progress.collectAsState()
    var finishing by remember { mutableStateOf(false) }
    DisposableEffect(imageCache) { onDispose { } }
    LaunchedEffect(browsing) { onTargetBrowserVisibilityChange(browsing) }
    DisposableEffect(Unit) { onDispose { onTargetBrowserVisibilityChange(false) } }
    var name by remember { mutableStateOf("Aurora Drift") }; var creator by remember { mutableStateOf("Retr0") }; var description by remember { mutableStateOf("") }

    fun processNext(decision: CropDecision = CropDecision.AUTO_CROP, rect: CropRect? = null) {
        val uri = pendingUris.firstOrNull() ?: run { imageLoading = false; return }
        val role = pendingRole
        scope.launch {
            imageLoading = true
            Log.i(CROP_WIZARD_LOG_TAG, "process_start decision=$decision role=$role remaining=${pendingUris.size}")
            imageProcessor.process(uri, role, decision, rect).onSuccess { image ->
                if (state.images.any { it.sourceHash == image.sourceHash }) {
                    imageCache.delete(image.file)
                    imageError = "Duplicate image skipped."
                    Log.w(CROP_WIZARD_LOG_TAG, "duplicate_skipped hash=${image.sourceHash.take(12)}")
                } else {
                    state = state.copy(images = state.images + image)
                }
            }.onFailure { error ->
                imageError = error.message ?: "Unable to process the selected image."
                Log.e(CROP_WIZARD_LOG_TAG, "process_failed", error)
            }
            pendingUris = pendingUris.drop(1)
            if (pendingUris.isEmpty()) {
                imageLoading = false
                Log.i(CROP_WIZARD_LOG_TAG, "queue_complete")
            } else {
                val nextUri = pendingUris.first()
                val inspection = imageProcessor.inspect(nextUri).getOrElse {
                    imageError = it.message ?: "Unable to inspect the selected image."
                    processNext()
                    return@launch
                }
                pendingRole = if (state.images.none { it.role == SkinImageRole.COVER }) SkinImageRole.COVER else SkinImageRole.SCREENSHOT
                if (inspection.needsCropChoice) {
                    cropRequest = ImageCropRequest(nextUri, inspection, pendingRole)
                    imageLoading = false
                    Log.i(CROP_WIZARD_LOG_TAG, "crop_choice_required dimensions=${inspection.width}x${inspection.height} role=$pendingRole")
                } else {
                    Log.i(CROP_WIZARD_LOG_TAG, "AUTO_CROP_BYPASSED_WITHIN_TOLERANCE role=$pendingRole dimensions=${inspection.width}x${inspection.height}")
                    processNext()
                }
            }
        }
    }

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
        val selected = uris.take((5 - state.images.size).coerceAtLeast(0))
        if (selected.isNotEmpty()) {
            imageError = null
            pendingUris = selected
            scope.launch {
                val inspection = imageProcessor.inspect(selected.first()).getOrElse {
                    imageError = it.message ?: "Unable to inspect the selected image."
                    pendingUris = emptyList()
                    return@launch
                }
                pendingRole = if (state.images.none { it.role == SkinImageRole.COVER }) SkinImageRole.COVER else SkinImageRole.SCREENSHOT
                if (inspection.needsCropChoice) {
                    cropRequest = ImageCropRequest(selected.first(), inspection, pendingRole)
                    Log.i(CROP_WIZARD_LOG_TAG, "crop_choice_required dimensions=${inspection.width}x${inspection.height} role=$pendingRole")
                } else {
                    Log.i(CROP_WIZARD_LOG_TAG, "AUTO_CROP_BYPASSED_WITHIN_TOLERANCE role=$pendingRole dimensions=${inspection.width}x${inspection.height}")
                    processNext()
                }
            }
        }
    }

    suspend fun promote(image: ProcessedSkinImage) {
        if (image.role == SkinImageRole.COVER) return
        imageLoading = true; imageError = null
        val oldCover = state.images.firstOrNull { it.role == SkinImageRole.COVER }
        val newCover = imageProcessor.changeRole(image, SkinImageRole.COVER).getOrElse { imageError = it.message; imageLoading = false; return }
        val oldScreenshot = oldCover?.let { imageProcessor.changeRole(it, SkinImageRole.SCREENSHOT).getOrElse { error -> imageCache.delete(newCover.file); imageError = error.message; imageLoading = false; return } }
        val replacements = state.images.map { when (it.id) { image.id -> newCover; oldCover?.id -> oldScreenshot ?: it; else -> it } }
        oldCover?.let { imageCache.delete(it.file) }
        state = state.copy(images = replacements); imageLoading = false
    }

    cropRequest?.let { request ->
        CropChoiceDialog(
            request = request,
            onAutoCrop = {
                Log.i(CROP_WIZARD_LOG_TAG, "AUTO_CROP_CLASSIFIED role=${request.role} decision=user_selected dimensions=${request.inspection.width}x${request.inspection.height}")
                cropRequest = null
                processNext()
            },
            onManualCrop = { cropRequest = null; manualImageCropRequest = request },
        )
    }
    manualImageCropRequest?.let { request ->
        ManualCropDialog(
            request = request,
            imageProcessor = imageProcessor,
            onConfirm = { rect -> manualImageCropRequest = null; processNext(CropDecision.MANUAL_CROP, rect) },
            onDismiss = { manualImageCropRequest = null; processNext() },
        )
    }

    val importedFileName = state.source?.fileName.orEmpty()
    if (browsing) { SkinTargetBrowserScreen(SkinTargetValidator.root(state.gamePackage), importedFileName, browserSource, { browsing = false }, { state = state.copy(target = it); browsing = false }, modifier); return }
    val labels = listOf("Choose file", "Match vehicle", "Details", "Review")
    val valid = when (state.step) { 1 -> state.source != null && !sourceLoading; 2 -> state.target != null; 3 -> !imageLoading && cropRequest == null && (!state.upload || state.images.any { it.role == SkinImageRole.COVER }); 4 -> !state.upload || state.guidelines; else -> true }
    Column(modifier.fillMaxSize().imePadding()) {
        Column(Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(horizontal = 18.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            ScreenTopBar("Import skin", labels[state.step - 1], onBack); WizardStepIndicator(state.step, label = labels[state.step - 1])
            when (state.step) {
                1 -> { SectionHeader("Car skin file", Icons.AutoMirrored.Filled.InsertDriveFile); SecondaryButton(if (state.source == null) "Choose .dds.z file" else "Choose another file", Modifier.fillMaxWidth(), icon = Icons.Filled.FolderOpen) { sourcePicker.launch(arrayOf("application/octet-stream", "application/zip", "*/*")) }; if (sourceLoading) { LinearProgressIndicator(Modifier.fillMaxWidth()); Text("Reading and verifying the file...", color = RetroTextMuted) }; sourceError?.let { Text(it, color = RetroDanger) }; state.source?.let { source -> RetroCard { DataRow("Name", source.fileName); DataRow("Size", formatSourceSize(source.size)); DataRow("Type", "DDS.Z"); DataRow("Format", source.format); DataRow("SHA-256", source.sha256.chunked(16).joinToString("\n")) }; SectionHeader("Detected metadata", Icons.Filled.AutoAwesome); RetroCard { CompatibilityBadge(Compatibility.GAME_NOT_DETECTED); DataRow("Target", "Exterior livery"); DataRow("Next", "Choose the exact RR3 target file") } } ?: Text("Select the custom skin file you want to import.", color = RetroTextMuted) }
                2 -> { SectionHeader("Target", Icons.Filled.DirectionsCar); VehicleSelector(state.vehicle) { state = state.copy(vehicle = it, target = null) }; GamePackageSelector(state.gamePackage) { state = state.copy(gamePackage = it, target = null) }; SecondaryButton("Browse RR3 Files", Modifier.fillMaxWidth(), icon = Icons.Filled.FolderOpen) { browsing = true }; RetroCard { DataRow("Package", state.gamePackage.short); DataRow("Selected target", state.target?.relativePath ?: "No target selected"); DataRow("Exact filename", if (state.target != null) "Match" else "Required") }; if (!valid) Text("Choose an existing exact-name exterior livery target before continuing.", color = RetroDanger) }
                3 -> { SectionHeader("Skin details", Icons.Filled.Edit); OutlinedTextField(name, { name = it }, label = { Text("Name") }, modifier = Modifier.fillMaxWidth()); OutlinedTextField(creator, { creator = it }, label = { Text("Creator") }, modifier = Modifier.fillMaxWidth()); OutlinedTextField(description, { if (it.length <= 280) description = it }, label = { Text("Description") }, supportingText = { Text("${description.length} / 280") }, minLines = 3, modifier = Modifier.fillMaxWidth()); ScreenshotPicker(state.images, onAdd = { imagePicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }, onRemove = { removed -> imageCache.delete(removed.file); state = state.copy(images = state.images - removed) }, onSetCover = { scope.launch { promote(it) } }); if (imageLoading) LinearProgressIndicator(Modifier.fillMaxWidth()); imageError?.let { Text(it, color = RetroDanger) }; if (state.upload && state.images.none { it.role == SkinImageRole.COVER }) Text("A cover image is required for community upload.", color = RetroDanger) }
                4 -> { SectionHeader("Options", Icons.Filled.Tune); UploadOptionsCard(state.apply, state.upload, { state = state.copy(apply = it) }, { state = state.copy(upload = it) }); if (state.upload) CommunityGuidelinesCard(state.guidelines) { state = state.copy(guidelines = it) }; SectionHeader("Summary", Icons.Filled.Summarize); RetroCard { DataRow("Skin", name); DataRow("Source", state.source?.fileName ?: "Not selected"); DataRow("Vehicle", "${state.vehicle} · Exterior livery"); DataRow("Package", state.gamePackage.short); DataRow("Target", state.target?.relativePath ?: "Not selected"); DataRow("Cover", if (state.images.any { it.role == SkinImageRole.COVER }) "Selected" else "Not selected"); DataRow("Screenshots", state.images.count { it.role == SkinImageRole.SCREENSHOT }.toString()) }; when (val progress = importProgress) { is ImportProgress.Running -> { LinearProgressIndicator({ progress.percent / 100f }, Modifier.fillMaxWidth()); Text(progress.message, color = RetroTextMuted) }; is ImportProgress.Failed -> Text(progress.message, color = RetroDanger); else -> Unit } }
            }; Spacer(Modifier.height(12.dp))
        }
        Row(Modifier.fillMaxWidth().padding(18.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) { SecondaryButton(if (state.step == 1) "Cancel" else "Back", Modifier.weight(1f)) { if (!finishing) { if (state.step == 1) { imageCache.cleanup(); onBack() } else state = state.copy(step = state.step - 1) } }; Box(Modifier.weight(1f)) { PrimaryButton(if (state.step < 4) "Continue" else if (finishing) "Saving..." else "Finish", Modifier.fillMaxWidth()) { if (valid && !finishing) { if (state.step < 4) state = state.copy(step = state.step + 1) else scope.launch { finishing = true; viewModel.finish(state, name, creator, description).onSuccess { imageCache.cleanup(); onDone() }; finishing = false } } } } }
    }
}

data class ImageCropRequest(val uri: Uri, val inspection: ImageInspection, val role: SkinImageRole)

@Composable
fun CropChoiceDialog(request: ImageCropRequest, onAutoCrop: () -> Unit, onManualCrop: () -> Unit) {
    AlertDialog(
        onDismissRequest = onAutoCrop,
        title = { Text("Crop image to 16:9?") },
        text = { Text("This ${request.inspection.width} × ${request.inspection.height} image differs from 16:9. Auto crop uses the centered 16:9 area, or choose the area manually.") },
        confirmButton = { TextButton(onClick = onAutoCrop) { Text("Auto crop") } },
        dismissButton = { TextButton(onClick = onManualCrop) { Text("Crop manually") } },
    )
}

@Composable
fun ManualCropDialog(request: ImageCropRequest, imageProcessor: SkinImageProcessor, onConfirm: (CropRect) -> Unit, onDismiss: () -> Unit) {
    var oriented by remember(request.uri) { mutableStateOf<Bitmap?>(null) }
    var decodeFailed by remember(request.uri) { mutableStateOf(false) }
    var viewport by remember { mutableStateOf(IntSize.Zero) }
    var geo by remember(request.uri) { mutableStateOf<CropGeometry?>(null) }
    val logTag = "SkinCropEditor"

    // Decode through the SAME oriented+sampled path SkinImageProcessor.process() uses for the
    // final crop, so the geometry the user sees maps 1:1 onto the pixels that get exported
    // (CROP_EDITOR guardrail: editor and processor must operate on the same oriented bitmap).
    LaunchedEffect(request.uri) {
        Log.i(logTag, "EDITOR_OPENED role=${request.role} dimensions=${request.inspection.width}x${request.inspection.height}")
        imageProcessor.decodeForManualCrop(request.uri, request.role)
            .onSuccess { decoded ->
                oriented = decoded
                Log.i(logTag, "EDITOR_IMAGE_READY srcW=${decoded.width} srcH=${decoded.height}")
            }
            .onFailure { error ->
                decodeFailed = true
                Log.e(logTag, "CROP_EDITOR_E001 decode_failed", error)
            }
    }

    LaunchedEffect(viewport, oriented) {
        val image = oriented
        if (image != null && viewport.width > 0 && viewport.height > 0) {
            val next = CropGeometry(image.width, image.height, viewport.width.toFloat(), viewport.height.toFloat())
            if (next.valid) {
                geo = next.reset()
                Log.i(logTag, "EDITOR_VIEWPORT_READY vpW=${viewport.width} vpH=${viewport.height} srcW=${image.width} srcH=${image.height} baseScale=${next.baseScale}")
            } else {
                geo = null
                Log.e(logTag, "CROP_EDITOR_E002 invalid_viewport_or_source vpW=${viewport.width} vpH=${viewport.height} srcW=${image.width} srcH=${image.height}")
            }
        }
    }

    AlertDialog(
        onDismissRequest = {
            Log.i(logTag, "EDITOR_CROP_CANCELLED")
            onDismiss()
        },
        title = { Text("Manual 16:9 crop") },
        text = {
            val image = oriented
            if (image == null) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    LinearProgressIndicator(Modifier.fillMaxWidth())
                    if (decodeFailed) Text("Unable to decode the selected image.", color = RetroDanger)
                }
            } else {
                val imageBitmap = remember(image) { image.asImageBitmap() }
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .clipToBounds()
                            .background(RetroCover)
                            .onSizeChanged { viewport = it }
                            .pointerInput(geo != null) {
                                detectTransformGestures { _, pan, zoom, _ ->
                                    val current = geo ?: return@detectTransformGestures
                                    val updated = current.withTransform(
                                        newUserScale = current.userScale * zoom,
                                        newPanX = current.panX + pan.x,
                                        newPanY = current.panY + pan.y,
                                    )
                                    geo = updated
                                    Log.d(logTag, "EDITOR_TRANSFORM_CHANGED scale=${updated.userScale} pan=${updated.panX},${updated.panY}")
                                }
                            }
                            .semantics {
                                contentDescription = "Crop viewport. Pinch to zoom and drag to position the image inside the 16:9 frame."
                            },
                    ) {
                        val g = geo
                        if (g != null) {
                            Canvas(Modifier.matchParentSize()) {
                                // Single explicit destination rect, NO ContentScale: this is the exact
                                // rect toSourceCropRect() maps back to source px, so display == export.
                                val dst = g.imageDestRect()
                                drawImage(
                                    image = imageBitmap,
                                    srcOffset = IntOffset.Zero,
                                    srcSize = IntSize(g.sourceW, g.sourceH),
                                    dstOffset = IntOffset(dst[0].roundToInt(), dst[1].roundToInt()),
                                    dstSize = IntSize(
                                        (dst[2] - dst[0]).roundToInt().coerceAtLeast(1),
                                        (dst[3] - dst[1]).roundToInt().coerceAtLeast(1),
                                    ),
                                )
                                val f = g.frameRect()
                                val dim = Color.Black.copy(alpha = 0.45f)
                                if (f[1] > 0f) drawRect(dim, topLeft = Offset(0f, 0f), size = Size(size.width, f[1]))
                                if (f[3] < size.height) drawRect(dim, topLeft = Offset(0f, f[3]), size = Size(size.width, size.height - f[3]))
                                if (f[0] > 0f) drawRect(dim, topLeft = Offset(0f, f[1]), size = Size(f[0], f[3] - f[1]))
                                if (f[2] < size.width) drawRect(dim, topLeft = Offset(f[2], f[1]), size = Size(size.width - f[2], f[3] - f[1]))
                                drawRect(RetroAccent, topLeft = Offset(f[0], f[1]), size = Size(f[2] - f[0], f[3] - f[1]), style = Stroke(2.dp.toPx()))
                            }
                        }
                    }
                    Text("Pinch to zoom, then drag the image to choose the area inside the 16:9 frame.", color = RetroTextMuted, style = MaterialTheme.typography.bodySmall)
                }
            }
        },
        confirmButton = {
            TextButton(
                enabled = geo != null,
                onClick = {
                    val g = geo
                    if (g == null) {
                        Log.e(logTag, "CROP_EDITOR_E003 no_geometry")
                        return@TextButton
                    }
                    val rect = g.toSourceCropRect()
                    if (rect == null) {
                        Log.e(logTag, "CROP_EDITOR_E005 crop_outside_source")
                        return@TextButton
                    }
                    Log.i(
                        logTag,
                        "EDITOR_CROP_CONFIRMED srcW=${g.sourceW} srcH=${g.sourceH} vpW=${g.viewportW} vpH=${g.viewportH} " +
                            "baseScale=${g.baseScale} userScale=${g.userScale} pan=${g.panX},${g.panY} " +
                            "rect=${rect.left},${rect.top},${rect.width}x${rect.height} ratio=${rect.width.toFloat() / rect.height}",
                    )
                    onConfirm(rect)
                },
            ) { Text("Use crop") }
        },
        dismissButton = {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TextButton(onClick = {
                    geo = geo?.reset()
                    Log.i(logTag, "EDITOR_RESET")
                }) { Text("Reset") }
                TextButton(onClick = {
                    Log.i(logTag, "EDITOR_CROP_CANCELLED")
                    onDismiss()
                }) { Text("Cancel") }
            }
        },
    )
}

private fun formatSourceSize(bytes: Long): String = String.format(Locale.US, "%.2f MiB", bytes / 1024.0 / 1024.0)
