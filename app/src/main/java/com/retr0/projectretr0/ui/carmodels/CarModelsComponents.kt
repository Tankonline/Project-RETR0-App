package com.retr0.projectretr0.ui.carmodels

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.retr0.projectretr0.data.carModels.CarModelErrorCode
import com.retr0.projectretr0.data.carModels.asCarModelException
import com.retr0.projectretr0.data.carModels.local.BackupStatus
import com.retr0.projectretr0.data.carModels.local.TargetStatus
import com.retr0.projectretr0.ui.components.*
import com.retr0.projectretr0.ui.skins.GamePackage
import com.retr0.projectretr0.ui.theme.*
import java.text.DateFormat
import java.util.Date
import java.util.Locale

enum class ModsKind(val label: String) { SKINS("Skin Mods"), CARS("Car Models") }
enum class CompatLevel(val label: String) { COMPATIBLE("Compatible"), DIFFERENT_BUILD("Different build"), INCOMPATIBLE("Incompatible") }
enum class ContentType(val label: String) { CAR_MODEL("Car Model"), SKIN("Skin") }
enum class CarModelAction(val label: String) { DOWNLOAD("Download"), APPLY("Apply"), UPDATE("Update"), INSTALLED("Installed"), DETAILS("Details"), UPLOAD("Publish to Community Hub"), PUBLISH("Publish") }

/** What a gallery / library card shows. Built from a hub model or a local library model. */
data class CarModelCardUi(
    val id: String,
    val name: String,
    val vehicleDirectory: String,
    val creator: String,
    val supportedPackages: List<String>,
    val fileCount: Int,
    val totalBytes: Long,
    val compat: CompatLevel,
    val verified: Boolean,
    val likes: Int? = null,
    val downloads: Int? = null,
    val cover: Any? = null,
)

sealed interface BackupCardState {
    data class Creating(val done: Int, val total: Int) : BackupCardState
    data class Verified(val fileCount: Int, val totalBytes: Long) : BackupCardState
    data class Failed(val reason: String, val code: String? = null) : BackupCardState
}

/** An error as shown to the user: the friendly text plus its stable `CM-…` code. */
data class UiError(val message: String, val code: String?)
fun Throwable.toUiError(message: String? = null): UiError = asCarModelException().let { UiError(message ?: it.message ?: it.error.userMessage, it.error.code) }
fun CarModelErrorCode.toUiError(message: String = userMessage) = UiError(message, code)

fun formatMb(bytes: Long): String = String.format(Locale.US, "%.1f MB", bytes / 1024.0 / 1024.0)
fun formatDate(millis: Long): String = DateFormat.getDateInstance(DateFormat.MEDIUM).format(Date(millis))
fun packageLabel(id: String): String = when (id) { GamePackage.ROW.id -> "Global"; GamePackage.NA.id -> "N. America"; else -> id }
fun packageShort(id: String): String = GamePackage.entries.firstOrNull { it.id == id }?.short ?: id
fun compatFor(supportedPackages: List<String>, gamePackage: GamePackage): CompatLevel = when {
    supportedPackages.isEmpty() -> CompatLevel.DIFFERENT_BUILD
    gamePackage.id in supportedPackages -> CompatLevel.COMPATIBLE
    else -> CompatLevel.INCOMPATIBLE
}

@Composable
fun ModsSegmentedControl(selected: ModsKind, onSelect: (ModsKind) -> Unit, modifier: Modifier = Modifier) =
    SegmentedTabs(ModsKind.entries.map { it.label }, selected.ordinal, modifier) { onSelect(ModsKind.entries[it]) }

@Composable
fun CompatBadge(level: CompatLevel, modifier: Modifier = Modifier) = when (level) {
    CompatLevel.COMPATIBLE -> BadgePill(level.label, Icons.Filled.CheckCircle, RetroSuccess, modifier)
    CompatLevel.DIFFERENT_BUILD -> BadgePill(level.label, Icons.Filled.WarningAmber, RetroGold, modifier)
    CompatLevel.INCOMPATIBLE -> BadgePill(level.label, Icons.Filled.Block, RetroDanger, modifier)
}

@Composable
fun ContentTypeBadge(type: ContentType, modifier: Modifier = Modifier) {
    val color = if (type == ContentType.CAR_MODEL) RetroAccent else RetroModelPurple
    // Solid backing keeps the badge legible on top of cover art.
    BadgePill(type.label, if (type == ContentType.CAR_MODEL) Icons.Filled.DirectionsCar else Icons.Filled.Brush, color, modifier.background(RetroBg.copy(alpha = .85f), RoundedCornerShape(50)))
}

@Composable
fun VerifiedBadge(modifier: Modifier = Modifier, text: String = "Verified") =
    BadgePill(text, Icons.Filled.Verified, RetroSuccess, modifier.background(RetroBg.copy(alpha = .85f), RoundedCornerShape(50)))

@Composable
fun BackupStatusBadge(status: BackupStatus?, modifier: Modifier = Modifier) = when (status) {
    BackupStatus.VERIFIED -> BadgePill("Verified", Icons.Filled.Verified, RetroSuccess, modifier)
    BackupStatus.CORRUPT -> BadgePill("Corrupt", Icons.Filled.ErrorOutline, RetroDanger, modifier)
    BackupStatus.MISSING -> BadgePill("Missing", Icons.Filled.HelpOutline, RetroTextMuted, modifier)
    null -> BadgePill("No backup", Icons.Filled.HelpOutline, RetroTextMuted, modifier)
}

@Composable
fun MetaPill(text: String, modifier: Modifier = Modifier, color: Color = RetroTextDim) {
    Text(text, color = color, style = MaterialTheme.typography.labelMedium, maxLines = 1, modifier = modifier.background(RetroStatBg, RoundedCornerShape(50)).padding(horizontal = 9.dp, vertical = 4.dp))
}

@Composable
fun MonoText(text: String, modifier: Modifier = Modifier, color: Color = RetroTextDim, maxLines: Int = 3) {
    Text(text, color = color, fontFamily = FontFamily.Monospace, style = MaterialTheme.typography.labelMedium, maxLines = maxLines, overflow = TextOverflow.Ellipsis, modifier = modifier)
}

/** Small muted monospace error code, so a screenshot names the same code as logcat. */
@Composable
fun ErrorCodeText(code: String?, modifier: Modifier = Modifier) {
    if (!code.isNullOrBlank()) Text(code, color = RetroTextMuted, fontFamily = FontFamily.Monospace, style = MaterialTheme.typography.labelMedium, maxLines = 1, modifier = modifier)
}

@Composable
fun InfoNote(icon: ImageVector, text: String, color: Color, modifier: Modifier = Modifier, code: String? = null) {
    Row(modifier.fillMaxWidth().background(color.copy(.1f), RoundedCornerShape(12.dp)).border(1.dp, color.copy(.3f), RoundedCornerShape(12.dp)).padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, null, tint = color, modifier = Modifier.size(18.dp))
        Spacer(Modifier.width(9.dp))
        Column(Modifier.weight(1f)) {
            Text(text, color = RetroTextSoft, style = MaterialTheme.typography.bodyMedium)
            ErrorCodeText(code)
        }
    }
}

@Composable
fun InfoRow(label: String, value: String, mono: Boolean = false, valueColor: Color = RetroText) {
    Row(Modifier.fillMaxWidth().padding(vertical = 6.dp), verticalAlignment = Alignment.Top) {
        Text(label, color = RetroTextMuted, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.width(128.dp))
        if (mono) MonoText(value, Modifier.weight(1f), valueColor) else Text(value, color = valueColor, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.weight(1f))
    }
}

/** Compact pill action that, unlike the shared buttons, has a disabled state (Restore on a corrupt backup). */
@Composable
fun SmallActionButton(text: String, modifier: Modifier = Modifier, icon: ImageVector? = null, color: Color = RetroAccent, filled: Boolean = false, enabled: Boolean = true, onClick: () -> Unit) {
    val content = when { !enabled -> RetroTextMuted; filled -> Color.White; else -> color }
    Row(
        modifier.heightIn(min = 44.dp).clip(RoundedCornerShape(12.dp))
            .background(when { !enabled -> RetroStatBg; filled -> color; else -> color.copy(alpha = .1f) })
            .border(1.dp, if (enabled) color.copy(alpha = .4f) else RetroBorder, RoundedCornerShape(12.dp))
            .clickable(enabled = enabled, onClick = onClick).padding(horizontal = 14.dp),
        horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically,
    ) {
        if (icon != null) { Icon(icon, null, tint = content, modifier = Modifier.size(16.dp)); Spacer(Modifier.width(6.dp)) }
        Text(text, color = content, style = MaterialTheme.typography.labelLarge, maxLines = 1)
    }
}

@Composable
fun BackHeader(title: String, subtitle: String, onBack: () -> Unit, modifier: Modifier = Modifier) {
    Row(modifier.fillMaxWidth().heightIn(min = 64.dp).padding(horizontal = 6.dp), verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = RetroTextDim) }
        Column(Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.titleLarge, color = RetroText, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(subtitle, color = RetroTextMuted, style = MaterialTheme.typography.labelMedium, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}

@Composable
fun ModelCover(model: Any?, modifier: Modifier = Modifier) {
    val placeholder: @Composable () -> Unit = { Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Icon(Icons.Filled.DirectionsCar, "Car model artwork placeholder", tint = Color.White.copy(.10f), modifier = Modifier.size(44.dp)) } }
    Box(modifier.clip(RoundedCornerShape(12.dp)).background(RetroCover), contentAlignment = Alignment.Center) {
        if (model == null) placeholder()
        else SubcomposeAsyncImage(model = model, contentDescription = "Car model artwork", contentScale = ContentScale.Crop, loading = { placeholder() }, error = { placeholder() }, modifier = Modifier.fillMaxSize())
    }
}

@Composable
fun CarModelCard(model: CarModelCardUi, action: CarModelAction, onClick: () -> Unit, onAction: () -> Unit, modifier: Modifier = Modifier) {
    RetroCard(modifier.clip(RoundedCornerShape(16.dp)).clickable(onClick = onClick)) {
        Box(Modifier.fillMaxWidth().aspectRatio(2.1f)) {
            ModelCover(model.cover, Modifier.fillMaxSize())
            ContentTypeBadge(ContentType.CAR_MODEL, Modifier.align(Alignment.TopStart).padding(8.dp))
            if (model.verified) VerifiedBadge(Modifier.align(Alignment.TopEnd).padding(8.dp))
        }
        Spacer(Modifier.height(10.dp))
        Text(model.name, color = RetroText, style = MaterialTheme.typography.titleMedium, maxLines = 2, overflow = TextOverflow.Ellipsis)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Replaces ", color = RetroTextMuted, style = MaterialTheme.typography.labelMedium)
            MonoText(model.vehicleDirectory, maxLines = 1)
        }
        Text(listOf(model.creator.ifBlank { "Unknown creator" }, model.supportedPackages.joinToString(" + ") { packageLabel(it) }.ifBlank { "Package not set" }).joinToString(" · "), color = RetroTextDim, style = MaterialTheme.typography.bodyMedium, maxLines = 1, overflow = TextOverflow.Ellipsis)
        Spacer(Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            model.likes?.let { Metric(Icons.Filled.Favorite, it) }
            model.downloads?.let { Metric(Icons.Filled.Download, it) }
            CompatBadge(model.compat)
        }
        Spacer(Modifier.height(8.dp))
        HorizontalDivider(color = RetroBorder)
        Spacer(Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("${model.fileCount} files · ${formatMb(model.totalBytes)}", color = RetroTextMuted, style = MaterialTheme.typography.labelMedium, modifier = Modifier.weight(1f))
            when (action) {
                CarModelAction.DOWNLOAD -> SmallActionButton(action.label, icon = Icons.Filled.Download, filled = true, onClick = onAction)
                CarModelAction.APPLY -> SmallActionButton(action.label, icon = Icons.Filled.Bolt, filled = true, onClick = onAction)
                CarModelAction.UPDATE -> SmallActionButton(action.label, icon = Icons.Filled.Update, color = RetroGold, onClick = onAction)
                CarModelAction.INSTALLED -> SmallActionButton(action.label, icon = Icons.Filled.CheckCircle, color = RetroSuccess, enabled = false, onClick = onAction)
                CarModelAction.DETAILS -> SmallActionButton(action.label, color = RetroTextDim, onClick = onAction)
                CarModelAction.UPLOAD -> SmallActionButton(action.label, icon = Icons.Filled.Upload, filled = true, onClick = onAction)
                CarModelAction.PUBLISH -> SmallActionButton(action.label, icon = Icons.Filled.Public, color = RetroGold, onClick = onAction)
            }
        }
    }
}

@Composable
private fun Metric(icon: ImageVector, value: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, null, tint = RetroTextMuted, modifier = Modifier.size(14.dp))
        Spacer(Modifier.width(4.dp))
        Text(if (value >= 1000) String.format(Locale.US, "%.1fk", value / 1000.0) else value.toString(), color = RetroTextDim, style = MaterialTheme.typography.labelMedium)
    }
}

@Composable
fun WizardStepper(total: Int, current: Int, modifier: Modifier = Modifier) {
    Row(modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        repeat(total) { index ->
            val step = index + 1
            val color = when { step < current -> RetroSuccess; step == current -> RetroAccent; else -> RetroTextMuted }
            Box(Modifier.size(26.dp).background(if (step > current) RetroSurface else color.copy(alpha = .16f), CircleShape).border(1.5.dp, if (step > current) RetroBorder else color, CircleShape), contentAlignment = Alignment.Center) {
                if (step < current) Icon(Icons.Filled.Check, "Step $step done", tint = color, modifier = Modifier.size(14.dp))
                else Text(step.toString(), color = color, style = MaterialTheme.typography.labelMedium)
            }
            if (step < total) Box(Modifier.weight(1f).padding(horizontal = 4.dp).height(2.dp).background(if (step < current) RetroSuccess.copy(alpha = .6f) else RetroBorder, RoundedCornerShape(1.dp)))
        }
    }
}

@Composable
fun FileGroupRow(icon: ImageVector, label: String, ext: String, count: Int, ok: Boolean = true) {
    Row(Modifier.fillMaxWidth().heightIn(min = 48.dp), verticalAlignment = Alignment.CenterVertically) {
        Box(Modifier.size(34.dp).background(RetroStatBg, RoundedCornerShape(9.dp)), contentAlignment = Alignment.Center) { Icon(icon, null, tint = RetroAccent, modifier = Modifier.size(18.dp)) }
        Spacer(Modifier.width(10.dp))
        Column(Modifier.weight(1f)) {
            Text(label, color = RetroText, style = MaterialTheme.typography.bodyLarge)
            MonoText(ext, maxLines = 1, color = RetroTextMuted)
        }
        Text(count.toString(), color = RetroTextSoft, style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.width(10.dp))
        Icon(if (ok) Icons.Filled.CheckCircle else Icons.Filled.WarningAmber, if (ok) "OK" else "Needs review", tint = if (ok) RetroSuccess else RetroGold, modifier = Modifier.size(18.dp))
    }
}

@Composable
fun PathDiffCard(sourcePath: String, targetPath: String, targetStatus: TargetStatus?, statusText: String) {
    RetroCard {
        Text("SOURCE", color = RetroTextMuted, style = MaterialTheme.typography.labelSmall)
        MonoText(sourcePath, Modifier.fillMaxWidth().padding(top = 4.dp).background(RetroStatBg, RoundedCornerShape(9.dp)).padding(10.dp), RetroTextSoft)
        Icon(Icons.Filled.ArrowDownward, null, tint = RetroAccent, modifier = Modifier.align(Alignment.CenterHorizontally).padding(vertical = 6.dp).size(18.dp))
        Text("TARGET", color = RetroTextMuted, style = MaterialTheme.typography.labelSmall)
        MonoText(targetPath, Modifier.fillMaxWidth().padding(top = 4.dp).background(RetroStatBg, RoundedCornerShape(9.dp)).padding(10.dp), RetroTextSoft, maxLines = 4)
        Spacer(Modifier.height(10.dp))
        val color = when (targetStatus) { TargetStatus.FOUND -> RetroSuccess; TargetStatus.MISSING -> RetroDanger; TargetStatus.INCOMPATIBLE -> RetroGold; null -> RetroTextMuted }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(when (targetStatus) { TargetStatus.FOUND -> Icons.Filled.CheckCircle; TargetStatus.MISSING -> Icons.Filled.ErrorOutline; TargetStatus.INCOMPATIBLE -> Icons.Filled.WarningAmber; null -> Icons.Filled.HourglassEmpty }, null, tint = color, modifier = Modifier.size(16.dp))
            Spacer(Modifier.width(6.dp))
            Text(statusText, color = color, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun BackupStatusCard(state: BackupCardState, destination: String, onRetry: (() -> Unit)? = null) {
    val color = when (state) { is BackupCardState.Creating -> RetroAccent; is BackupCardState.Verified -> RetroSuccess; is BackupCardState.Failed -> RetroDanger }
    Column(Modifier.fillMaxWidth().background(RetroSurface, RoundedCornerShape(16.dp)).border(1.dp, color.copy(alpha = .45f), RoundedCornerShape(16.dp)).padding(14.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(when (state) { is BackupCardState.Creating -> Icons.Filled.Backup; is BackupCardState.Verified -> Icons.Filled.VerifiedUser; is BackupCardState.Failed -> Icons.Filled.ErrorOutline }, null, tint = color)
            Spacer(Modifier.width(8.dp))
            Text(when (state) { is BackupCardState.Creating -> "Creating backup…"; is BackupCardState.Verified -> "Verified · SHA-256 matched"; is BackupCardState.Failed -> "Backup failed" }, color = color, style = MaterialTheme.typography.titleMedium)
        }
        Spacer(Modifier.height(8.dp))
        when (state) {
            is BackupCardState.Creating -> {
                if (state.total > 0) LinearProgressIndicator({ state.done / state.total.toFloat() }, Modifier.fillMaxWidth(), color = RetroAccent, trackColor = RetroStatBg)
                else LinearProgressIndicator(Modifier.fillMaxWidth(), color = RetroAccent, trackColor = RetroStatBg)
                Spacer(Modifier.height(6.dp))
                InfoRow("Destination", destination, mono = true)
                InfoRow("Files", if (state.total > 0) "${state.done}/${state.total}" else "Scanning…")
            }
            is BackupCardState.Verified -> {
                InfoRow("Destination", destination, mono = true)
                InfoRow("Files", "${state.fileCount}/${state.fileCount}")
                InfoRow("Size", formatMb(state.totalBytes))
            }
            is BackupCardState.Failed -> {
                Text(state.reason, color = RetroDangerText, style = MaterialTheme.typography.bodyMedium)
                ErrorCodeText(state.code)
                Text("Nothing in the game was changed.", color = RetroTextMuted, style = MaterialTheme.typography.bodyMedium)
                if (onRetry != null) { Spacer(Modifier.height(10.dp)); SmallActionButton("Retry backup", icon = Icons.Filled.Refresh, onClick = onRetry) }
            }
        }
    }
}

@Composable
fun BackupListRow(vehicleDirectory: String, gamePackage: String, status: BackupStatus, problemCount: Int, fileCount: Int, totalBytes: Long, createdAt: Long, restoreEnabled: Boolean, onRestore: () -> Unit, onDelete: () -> Unit, tag: String? = null) {
    RetroCard {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text(vehicleDirectory, color = RetroText, style = MaterialTheme.typography.titleMedium, maxLines = 1, overflow = TextOverflow.Ellipsis)
                MonoText("${packageShort(gamePackage)}/$vehicleDirectory", maxLines = 1)
            }
            Spacer(Modifier.width(8.dp))
            BackupStatusBadge(status)
        }
        Spacer(Modifier.height(8.dp))
        Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            tag?.let { MetaPill(it, color = RetroAccent) }
            MetaPill(packageLabel(gamePackage)); MetaPill("$fileCount files"); MetaPill(formatMb(totalBytes)); MetaPill(formatDate(createdAt))
        }
        when (status) {
            BackupStatus.CORRUPT -> { Spacer(Modifier.height(8.dp)); Text("Hash mismatch on $problemCount ${if (problemCount == 1) "file" else "files"}", color = RetroDanger, style = MaterialTheme.typography.bodyMedium); ErrorCodeText(CarModelErrorCode.BACKUP_CORRUPT.code) }
            BackupStatus.MISSING -> { Spacer(Modifier.height(8.dp)); Text("Backup files not found", color = RetroTextMuted, style = MaterialTheme.typography.bodyMedium); ErrorCodeText(CarModelErrorCode.BACKUP_MISSING.code) }
            BackupStatus.VERIFIED -> Unit
        }
        Spacer(Modifier.height(10.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            SmallActionButton("Restore", Modifier.weight(1f), Icons.Filled.Restore, enabled = restoreEnabled && status == BackupStatus.VERIFIED, onClick = onRestore)
            SmallActionButton("Delete", Modifier.weight(1f), Icons.Filled.Delete, color = RetroDanger, onClick = onDelete)
        }
    }
}

/** Body of the revert confirmation. [backupStatus] null means the backup is still being re-verified. */
@Composable
fun RevertSheetContent(modelName: String, targetPath: String, backupStatus: BackupStatus?, checking: Boolean, fileCount: Int, totalBytes: Long, restoreEnabled: Boolean, onCancel: () -> Unit, onConfirm: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier.fillMaxWidth().padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Box(Modifier.size(44.dp).background(RetroDangerTint, CircleShape).border(1.dp, RetroDanger.copy(alpha = .4f), CircleShape), contentAlignment = Alignment.Center) { Icon(Icons.Filled.Restore, null, tint = RetroDanger) }
        Text("Revert to original?", style = MaterialTheme.typography.titleLarge, color = RetroText)
        Text("This restores the stock RR3 files for this vehicle from your verified backup.", color = RetroTextSoft, style = MaterialTheme.typography.bodyLarge)
        Column(Modifier.fillMaxWidth().background(RetroStatBg, RoundedCornerShape(12.dp)).padding(horizontal = 12.dp, vertical = 6.dp)) {
            InfoRow("Installed model", modelName.ifBlank { "None recorded" })
            InfoRow("Target vehicle", targetPath, mono = true)
            InfoRow("Backup", when { checking -> "Verifying…"; backupStatus == BackupStatus.VERIFIED -> "Verified"; backupStatus == BackupStatus.CORRUPT -> "Corrupt — restore unavailable"; backupStatus == BackupStatus.MISSING -> "Missing — restore unavailable"; else -> "No backup" },
                valueColor = when { checking -> RetroTextDim; backupStatus == BackupStatus.VERIFIED -> RetroSuccess; else -> RetroDanger })
            InfoRow("Files restored", "$fileCount · ${formatMb(totalBytes)}")
            if (!checking) ErrorCodeText(when (backupStatus) { BackupStatus.VERIFIED -> null; BackupStatus.CORRUPT -> CarModelErrorCode.REVERT_BACKUP_CORRUPT.code; BackupStatus.MISSING -> CarModelErrorCode.REVERT_BACKUP_MISSING.code; null -> CarModelErrorCode.NO_BACKUP.code }, Modifier.padding(bottom = 6.dp))
        }
        Text("The current modded files will be replaced. This can't be undone without re-applying the model.", color = RetroDanger, style = MaterialTheme.typography.bodyMedium)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SecondaryButton(
                "Cancel",
                Modifier.weight(1f).height(52.dp),
                onClick = onCancel,
            )
            SmallActionButton(
                "Restore original",
                Modifier.weight(1f).height(52.dp),
                Icons.Filled.Restore,
                color = RetroDanger,
                enabled = restoreEnabled && !checking && backupStatus == BackupStatus.VERIFIED,
                onClick = onConfirm,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RevertSheet(modelName: String, targetPath: String, backupStatus: BackupStatus?, checking: Boolean, fileCount: Int, totalBytes: Long, restoreEnabled: Boolean, onCancel: () -> Unit, onConfirm: () -> Unit) {
    ModalBottomSheet(onDismissRequest = onCancel, containerColor = RetroSurface) {
        RevertSheetContent(modelName, targetPath, backupStatus, checking, fileCount, totalBytes, restoreEnabled, onCancel, onConfirm, Modifier.navigationBarsPadding())
    }
}

@Composable
fun RetroField(label: String, value: String, onValueChange: (String) -> Unit, modifier: Modifier = Modifier, placeholder: String = "", singleLine: Boolean = true, readOnly: Boolean = false, mono: Boolean = false) {
    Column(modifier.fillMaxWidth()) {
        Text(label, color = RetroTextDim, style = MaterialTheme.typography.labelMedium)
        Spacer(Modifier.height(5.dp))
        BasicTextField(
            value = value, onValueChange = onValueChange, singleLine = singleLine, readOnly = readOnly,
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = if (readOnly) RetroTextDim else RetroText, fontFamily = if (mono) FontFamily.Monospace else UiSans),
            cursorBrush = androidx.compose.ui.graphics.SolidColor(RetroAccent),
            modifier = Modifier.fillMaxWidth().background(RetroSurfaceAlt, RoundedCornerShape(12.dp)).border(1.dp, RetroBorder, RoundedCornerShape(12.dp)).heightIn(min = if (singleLine) 46.dp else 88.dp).padding(horizontal = 13.dp, vertical = 12.dp),
            decorationBox = { inner -> if (value.isEmpty()) Text(placeholder, color = RetroTextMuted, style = MaterialTheme.typography.bodyLarge); inner() },
        )
    }
}
