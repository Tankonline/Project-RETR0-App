package com.retr0.projectretr0.ui.skins

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import android.util.Log
import android.content.Intent
import androidx.core.net.toUri
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.retr0.projectretr0.data.skins.local.LocalSkinEntity
import com.retr0.projectretr0.data.skins.local.LocalSkinRepository
import com.retr0.projectretr0.data.skins.manifest.SkinManifestStore
import com.retr0.projectretr0.data.skins.local.SkinBackupEntity
import com.retr0.projectretr0.data.skins.manifest.SkinManifest
import com.retr0.projectretr0.ui.components.*
import com.retr0.projectretr0.ui.theme.*
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
    var editing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var manifest by remember(id) { mutableStateOf<SkinManifest?>(null) }
    LaunchedEffect(id) {
        val loadedSkin = repository.skin(id)
        skin = loadedSkin
        val packageRoot = loadedSkin?.let { File(it.binaryPath).parentFile }
        manifest = packageRoot?.let { SkinManifestStore.read(it) }
        Log.i("LOCAL_SCREENSHOT_LOAD_START", "skinId=$id")
        val images = repository.images(id)
        val screenshotEntities = images
            .filter { it.role == SkinImageRole.SCREENSHOT.name }
            .sortedBy { it.position }
        val coverEntity = images.firstOrNull { it.role == SkinImageRole.COVER.name }
        
        val carouselUris = buildList {
            manifest?.let { m ->
                val root = packageRoot!!
                m.cover?.let { add("file://${root.resolve(it.path).absolutePath}") }
                m.screenshots.forEach { add("file://${root.resolve(it.path).absolutePath}") }
            } ?: run {
                coverEntity?.let { add("file://${File(it.path).absolutePath}") }
                screenshotEntities.forEach { add("file://${File(it.path).absolutePath}") }
            }
        }
        
        Log.i(
            "LOCAL_SCREENSHOT_DB_RESULT",
            "skinId=$id images=${images.size} screenshots=${screenshotEntities.size} cover=${coverEntity != null} manifest=${manifest != null}",
        )
        coverPath = manifest?.cover?.let { packageRoot?.resolve(it.path)?.absolutePath } ?: coverEntity?.path
        screenshots = carouselUris.filter { uri ->
            val file = File(uri.substringAfter("file://"))
            if (!file.isFile || !file.canRead() || file.length() == 0L) {
                Log.e("LOCAL_SCREENSHOT_FILE_MISSING", "skinId=$id path=${file.absolutePath}")
                false
            } else true
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
        SecondaryButton("Export", Modifier.fillMaxWidth(), Icons.Filled.IosShare) {
            context.startActivity(Intent.createChooser(Intent(Intent.ACTION_SEND).apply {
                type = "application/octet-stream"
                putExtra(Intent.EXTRA_STREAM, current.binaryPath.toUri())
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }, "Export skin mod"))
        }
        SecondaryButton("Edit metadata", Modifier.fillMaxWidth(), Icons.Filled.Edit) { editing = true }
        if (editing) MetadataEditor(current, onCancel = { editing = false }) { updated ->
            scope.launch {
                repository.updateMetadata(id, updated.title, updated.creator, updated.vehicle, updated.carDirectory, updated.slotKey, updated.textureFormat)
                skin = repository.skin(id)
                editing = false
            }
        }
        DangerButton("Delete skin", icon = Icons.Filled.Delete) { dialog = "delete" }
        Spacer(Modifier.height(12.dp))
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

data class EditableSkinMetadata(
    val title: String,
    val creator: String,
    val vehicle: String,
    val carDirectory: String,
    val slotKey: String,
    val textureFormat: String,
)

@Composable
private fun MetadataEditor(skin: LocalSkinEntity, onCancel: () -> Unit, onSave: (EditableSkinMetadata) -> Unit) {
    var title by remember(skin.id) { mutableStateOf(skin.title) }
    var creator by remember(skin.id) { mutableStateOf(skin.creator) }
    var vehicle by remember(skin.id) { mutableStateOf(skin.vehicle) }
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedTextField(title, { title = it }, label = { Text("Title") })
        OutlinedTextField(creator, { creator = it }, label = { Text("Creator") })
        OutlinedTextField(vehicle, { vehicle = it }, label = { Text("Vehicle") })
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TextButton(onClick = onCancel) { Text("Cancel") }
            Button(onClick = { onSave(EditableSkinMetadata(title, creator, vehicle, skin.carDirectory, skin.slotKey, skin.textureFormat)) }) { Text("Save") }
        }
    }
}
