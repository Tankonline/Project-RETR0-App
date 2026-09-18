package com.example.projectretr0installer.ui.skins

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import android.util.Log
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.projectretr0installer.data.skins.local.LocalSkinEntity
import com.example.projectretr0installer.data.skins.local.LocalSkinRepository
import com.example.projectretr0installer.data.skins.local.SkinBackupEntity
import com.example.projectretr0installer.ui.components.*
import com.example.projectretr0installer.ui.theme.*
import kotlinx.coroutines.launch
import java.io.File
import java.text.DateFormat
import java.util.Date

@Composable fun LocalSkinDetails(id: String, onBack: () -> Unit, onApply: () -> Unit, onRevert: () -> Unit) {
    val context = LocalContext.current
    val repository = remember(context) { LocalSkinRepository(context) }
    var skin by remember(id) { mutableStateOf<LocalSkinEntity?>(null) }
    var screenshots by remember(id) { mutableStateOf(emptyList<String>()) }
    var coverPath by remember(id) { mutableStateOf<String?>(null) }
    var backups by remember(id) { mutableStateOf(emptyList<SkinBackupEntity>()) }
    var dialog by remember { mutableStateOf<String?>(null) }
    var actionError by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()
    LaunchedEffect(id) {
        skin = repository.skin(id)
        Log.i("LOCAL_SCREENSHOT_LOAD_START", "skinId=$id")
        val images = repository.images(id)
        val screenshotEntities = images
            .filter { it.role == SkinImageRole.SCREENSHOT.name }
            .sortedBy { it.position }
        val coverEntity = images.firstOrNull { it.role == SkinImageRole.COVER.name }
        val carouselImages = buildList {
            coverEntity?.let(::add)
            addAll(screenshotEntities)
        }
        Log.i(
            "LOCAL_SCREENSHOT_DB_RESULT",
            "skinId=$id images=${images.size} screenshots=${screenshotEntities.size} cover=${coverEntity != null}",
        )
        coverPath = coverEntity?.path
        screenshots = carouselImages.mapNotNull { image ->
            val file = File(image.path)
            if (!file.isFile || !file.canRead() || file.length() == 0L) {
                Log.e(
                    "LOCAL_SCREENSHOT_FILE_MISSING",
                    "skinId=$id position=${image.position} path=${image.path} exists=${file.exists()} readable=${file.canRead()} bytes=${file.length()}",
                )
                null
            } else {
                val uri = "file://${file.absolutePath}"
                Log.i(
                    "LOCAL_SCREENSHOT_LOAD_SUCCESS",
                    "skinId=$id position=${image.position} path=${file.absolutePath} bytes=${file.length()} uri=$uri",
                )
                uri
            }
        }
        coverPath?.let { path ->
            val file = File(path)
            if (!file.isFile || !file.canRead() || file.length() == 0L) {
                Log.e("LOCAL_COVER_FILE_MISSING", "skinId=$id path=$path exists=${file.exists()} readable=${file.canRead()} bytes=${file.length()}")
                coverPath = null
            }
        }
        if (screenshots.isEmpty()) {
            Log.w("LOCAL_SCREENSHOT_RENDER_EMPTY", "skinId=$id savedScreenshotCount=${screenshotEntities.size} coverAvailable=${coverPath != null}")
        }
        backups = repository.backupForTarget(skin?.targetPath.orEmpty())?.let(::listOf).orEmpty()
    }
    val current = skin
    if (current == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
        return
    }
    val backup = backups.firstOrNull()
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 18.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        ScreenTopBar("Local skin", current.vehicle, onBack)
        ScreenshotCarousel(
            urls = screenshots,
        )
        Text(current.title, color = RetroText, style = MaterialTheme.typography.headlineSmall)
        StatusChip(SkinStatus.LOCAL)
        MetadataCard(current.vehicle, current.carDirectory, current.slotKey, current.slotKey, current.textureFormat)
        OriginalBackupCard(backup != null, backup?.sha256 ?: current.binarySha256, DateFormat.getDateInstance().format(
            Date(current.importedAt)
        ))
        PrimaryButton("Apply", icon = Icons.Filled.CheckCircle) { dialog = "apply" }
        if (backup != null) OutlineButton("Revert to original") { dialog = "revert" } else RetroCard { Text("Revert unavailable", color = RetroGold); Text("A verified immutable backup will be created before the first apply.", color = RetroTextMuted, style = MaterialTheme.typography.bodySmall) }
        SecondaryButton("Export", Modifier.fillMaxWidth(), Icons.Filled.IosShare) {}; SecondaryButton("Edit metadata", Modifier.fillMaxWidth(), Icons.Filled.Edit) {}; DangerButton("Delete skin", icon = Icons.Filled.Delete) { dialog = "delete" }; Spacer(Modifier.height(12.dp))
    }
    dialog?.let { action ->
        DestructiveConfirmationDialog(
            if (action == "apply") "Apply this skin?" else if (action == "revert") "Restore original?" else "Delete this skin?",
            if (action == "apply") "RR3 will be stopped and the original file will be backed up before replacement." else if (action == "delete") "This permanently removes the skin file, preview images, and local library entry from this device." else "This operation uses the verified local library record.",
            action.replaceFirstChar { it.uppercase() },
            {
                dialog = null
                when (action) {
                    "apply" -> onApply()
                    "revert" -> onRevert()
                    "delete" -> scope.launch {
                        runCatching { repository.deleteSkin(id) }
                            .onSuccess { onBack() }
                            .onFailure { actionError = it.message ?: "Unable to delete skin" }
                    }
                }
            },
            { dialog = null },
        )
    }
    actionError?.let { message ->
        AlertDialog(
            onDismissRequest = { actionError = null },
            confirmButton = { TextButton(onClick = { actionError = null }) { Text("OK") } },
            title = { Text("Delete skin") },
            text = { Text(message) },
        )
    }
}
