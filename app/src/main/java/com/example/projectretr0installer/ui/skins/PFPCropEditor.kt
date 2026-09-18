package com.example.projectretr0installer.ui.skins

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.projectretr0installer.data.skins.SkinInstallationIdentity
import com.example.projectretr0installer.data.skins.SkinPFPStore
import com.example.projectretr0installer.ui.theme.RetroAccent
import com.example.projectretr0installer.ui.theme.RetroCover
import com.example.projectretr0installer.ui.theme.RetroDanger
import com.example.projectretr0installer.ui.theme.RetroTextMuted
import kotlinx.coroutines.launch
import java.io.File
import kotlin.math.roundToInt

private const val PFP_EDITOR_LOG_TAG = "SkinPFPEditor"
private const val PFP_CROP_LOG_TAG = "SkinImageCrop"

/**
 * Owns the whole "set profile picture" flow: pick an image from the photo picker, decide
 * auto-vs-manual 1:1 crop (bypassing the choice entirely when the source is already square within
 * [SkinImageCropMath.SQUARE_ASPECT_TOLERANCE]), encode through [SkinImageProcessor] with
 * [SkinImageRole.PFP], and adopt the result into [SkinPFPStore] keyed by this device's
 * [SkinInstallationIdentity]. Composed only while the dashboard's Community Identity card has the
 * editor open -- it tears itself down via [onDismiss] or [onSaved] once the flow completes.
 */
@Composable
fun PFPEditorFlow(onDismiss: () -> Unit, onSaved: (File) -> Unit) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val cache = remember(context) { SkinImportCache(context, sessionId = "pfp-edit") }
    val processor = remember(context, cache) { SkinImageProcessor(context.contentResolver, cache) }
    val installationId = remember(context) { SkinInstallationIdentity(context).installationId }

    var pendingUri by remember { mutableStateOf<Uri?>(null) }
    var inspection by remember { mutableStateOf<ImageInspection?>(null) }
    var showCropChoice by remember { mutableStateOf(false) }
    var showManualCrop by remember { mutableStateOf(false) }
    var busy by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    var launched by remember { mutableStateOf(false) }

    fun finish(uri: Uri, decision: CropDecision, rect: CropRect? = null) {
        scope.launch {
            busy = true
            processor.process(uri, SkinImageRole.PFP, decision, rect)
                .onSuccess { image ->
                    val saved = runCatching { SkinPFPStore.adopt(context, installationId, image.file) }
                        .onFailure {
                            cache.delete(image.file)
                            error = it.message ?: "Unable to save the profile picture."
                            Log.e(PFP_CROP_LOG_TAG, "PFP_CROP_E004 adopt_failed error=${it.message}")
                        }.getOrNull()
                    busy = false
                    if (saved != null) {
                        Log.i(PFP_EDITOR_LOG_TAG, "PFP_CROP_CONFIRMED decision=$decision dimensions=${image.width}x${image.height} bytes=${image.sizeBytes}")
                        onSaved(saved)
                    }
                }
                .onFailure {
                    busy = false
                    error = it.message ?: "Unable to process the selected image."
                    Log.e(PFP_CROP_LOG_TAG, "PFP_CROP_E004 process_failed error=${it.message}")
                }
        }
    }

    val picker = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri == null) {
            onDismiss()
            return@rememberLauncherForActivityResult
        }
        pendingUri = uri
        scope.launch {
            busy = true
            processor.inspect(uri, aspectRatio = SkinImageCropMath.SQUARE_ASPECT_RATIO, tolerance = SkinImageCropMath.SQUARE_ASPECT_TOLERANCE)
                .onSuccess { insp ->
                    busy = false
                    inspection = insp
                    if (insp.needsCropChoice) {
                        Log.i(PFP_EDITOR_LOG_TAG, "EDITOR_OPENED dimensions=${insp.width}x${insp.height} needsCropChoice=true")
                        showCropChoice = true
                    } else {
                        Log.i(PFP_CROP_LOG_TAG, "PFP_AUTO_CROP_BYPASSED_WITHIN_TOLERANCE dimensions=${insp.width}x${insp.height}")
                        finish(uri, CropDecision.AUTO_CROP)
                    }
                }
                .onFailure {
                    busy = false
                    error = it.message ?: "Unable to read the selected image."
                    Log.e(PFP_CROP_LOG_TAG, "PFP_CROP_E001 inspect_failed error=${it.message}")
                    onDismiss()
                }
        }
    }

    LaunchedEffect(Unit) {
        if (!launched) {
            launched = true
            picker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }

    if (showCropChoice) {
        val insp = inspection
        AlertDialog(
            onDismissRequest = { showCropChoice = false; onDismiss() },
            title = { Text("Crop profile picture") },
            text = { Text("This ${insp?.width} × ${insp?.height} image isn't square. Auto crop uses the centered square area, or choose it manually.") },
            confirmButton = {
                TextButton(onClick = {
                    showCropChoice = false
                    pendingUri?.let { finish(it, CropDecision.AUTO_CROP) }
                }) { Text("Auto crop") }
            },
            dismissButton = {
                TextButton(onClick = { showCropChoice = false; showManualCrop = true }) { Text("Crop manually") }
            },
        )
    }

    if (showManualCrop) {
        pendingUri?.let { uri ->
            PFPManualCropDialog(
                uri = uri,
                processor = processor,
                onConfirm = { rect -> showManualCrop = false; finish(uri, CropDecision.MANUAL_CROP, rect) },
                onDismiss = { showManualCrop = false; onDismiss() },
            )
        }
    }

    if (busy && !showCropChoice && !showManualCrop) {
        AlertDialog(
            onDismissRequest = {},
            confirmButton = {},
            text = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CircularProgressIndicator(Modifier.size(20.dp))
                    Spacer(Modifier.width(12.dp))
                    Text("Processing profile picture...")
                }
            },
        )
    }

    error?.let { message ->
        AlertDialog(
            onDismissRequest = { error = null; onDismiss() },
            title = { Text("Couldn't update profile picture") },
            text = { Text(message) },
            confirmButton = { TextButton(onClick = { error = null; onDismiss() }) { Text("OK") } },
        )
    }
}

@Composable
private fun PFPManualCropDialog(uri: Uri, processor: SkinImageProcessor, onConfirm: (CropRect) -> Unit, onDismiss: () -> Unit) {
    var oriented by remember(uri) { mutableStateOf<Bitmap?>(null) }
    var decodeFailed by remember(uri) { mutableStateOf(false) }
    var viewport by remember { mutableStateOf(IntSize.Zero) }
    var geo by remember(uri) { mutableStateOf<CropGeometry?>(null) }
    val haptics = LocalHapticFeedback.current
    var lastHapticAtMs by remember { mutableStateOf(0L) }

    // Decode through the SAME oriented+sampled path SkinImageProcessor.process() uses for the
    // final crop, so the geometry the user sees maps 1:1 onto the pixels that get exported.
    LaunchedEffect(uri) {
        Log.i(PFP_EDITOR_LOG_TAG, "EDITOR_OPENED role=PFP")
        processor.decodeForManualCrop(uri, SkinImageRole.PFP)
            .onSuccess { decoded ->
                oriented = decoded
                Log.i(PFP_EDITOR_LOG_TAG, "EDITOR_IMAGE_READY srcW=${decoded.width} srcH=${decoded.height}")
            }
            .onFailure { error ->
                decodeFailed = true
                Log.e(PFP_CROP_LOG_TAG, "PFP_CROP_E001 decode_failed error=${error.message}")
            }
    }

    LaunchedEffect(viewport, oriented) {
        val image = oriented
        if (image != null && viewport.width > 0 && viewport.height > 0) {
            val next = CropGeometry(image.width, image.height, viewport.width.toFloat(), viewport.height.toFloat(), targetRatio = CropGeometry.SQUARE_RATIO)
            if (next.valid) {
                geo = next.reset()
                Log.i(PFP_EDITOR_LOG_TAG, "EDITOR_VIEWPORT_READY vpW=${viewport.width} vpH=${viewport.height} srcW=${image.width} srcH=${image.height} baseScale=${next.baseScale}")
            } else {
                geo = null
                Log.e(PFP_CROP_LOG_TAG, "PFP_CROP_E002 viewport_size_mismatch vpW=${viewport.width} vpH=${viewport.height} srcW=${image.width} srcH=${image.height}")
            }
        }
    }

    AlertDialog(
        onDismissRequest = {
            Log.i(PFP_EDITOR_LOG_TAG, "EDITOR_CROP_CANCELLED")
            onDismiss()
        },
        title = { Text("Crop profile picture") },
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
                                    val requestedScale = current.userScale * zoom
                                    val requestedPanX = current.panX + pan.x
                                    val requestedPanY = current.panY + pan.y
                                    val updated = current.withTransform(requestedScale, requestedPanX, requestedPanY)
                                    // Bounds are enforced entirely inside CropGeometry.withTransform;
                                    // a mismatch between requested and clamped values means the user
                                    // pushed past a limit (zoom cap or pan edge) this frame.
                                    val clamped = updated.userScale != requestedScale || updated.panX != requestedPanX || updated.panY != requestedPanY
                                    if (clamped) {
                                        val now = System.currentTimeMillis()
                                        if (now - lastHapticAtMs > 150) {
                                            haptics.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                            lastHapticAtMs = now
                                        }
                                    }
                                    geo = updated
                                    Log.d(PFP_EDITOR_LOG_TAG, "EDITOR_GESTURE_TRANSFORM scale=${updated.userScale} pan=${updated.panX},${updated.panY} clamped=$clamped")
                                }
                            }
                            .semantics {
                                contentDescription = "Crop viewport. Pinch to zoom and drag to position the image inside the square frame."
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
                                val dim = Color.Black.copy(alpha = 0.5f)
                                if (f[1] > 0f) drawRect(dim, topLeft = Offset(0f, 0f), size = Size(size.width, f[1]))
                                if (f[3] < size.height) drawRect(dim, topLeft = Offset(0f, f[3]), size = Size(size.width, size.height - f[3]))
                                if (f[0] > 0f) drawRect(dim, topLeft = Offset(0f, f[1]), size = Size(f[0], f[3] - f[1]))
                                if (f[2] < size.width) drawRect(dim, topLeft = Offset(f[2], f[1]), size = Size(size.width - f[2], f[3] - f[1]))
                                drawRect(RetroAccent, topLeft = Offset(f[0], f[1]), size = Size(f[2] - f[0], f[3] - f[1]), style = Stroke(2.dp.toPx()))
                                // Circular guide only: the stored asset stays a square, but most
                                // surfaces render avatars inside a circular mask, so preview that too.
                                drawCircle(
                                    color = RetroAccent.copy(alpha = 0.55f),
                                    radius = (f[2] - f[0]) / 2f,
                                    center = Offset((f[0] + f[2]) / 2f, (f[1] + f[3]) / 2f),
                                    style = Stroke(1.dp.toPx()),
                                )
                            }
                        }
                    }
                    Text("Pinch to zoom, then drag the image to choose the area inside the frame.", color = RetroTextMuted, style = MaterialTheme.typography.bodySmall)
                }
            }
        },
        confirmButton = {
            TextButton(
                enabled = geo != null,
                onClick = {
                    val g = geo
                    if (g == null) {
                        Log.e(PFP_CROP_LOG_TAG, "PFP_CROP_E002 no_geometry")
                        return@TextButton
                    }
                    val rect = g.toSourceCropRect()
                    if (rect == null) {
                        Log.e(PFP_CROP_LOG_TAG, "PFP_CROP_E003 crop_outside_source")
                        return@TextButton
                    }
                    Log.i(
                        PFP_EDITOR_LOG_TAG,
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
                    Log.i(PFP_EDITOR_LOG_TAG, "EDITOR_RESET")
                }) { Text("Reset") }
                TextButton(onClick = {
                    Log.i(PFP_EDITOR_LOG_TAG, "EDITOR_CROP_CANCELLED")
                    onDismiss()
                }) { Text("Cancel") }
            }
        },
    )
}
