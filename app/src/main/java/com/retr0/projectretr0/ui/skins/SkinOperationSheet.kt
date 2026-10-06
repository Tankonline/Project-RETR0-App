package com.retr0.projectretr0.ui.skins

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.retr0.projectretr0.ui.theme.*

data class SkinOperationRequest(
    val type: OpType,
    val title: String,
    val vehicle: String,
    val target: String,
    val backup: OriginalBackup? = null,
    val skinId: String? = null,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable fun SkinOperationSheet(request: SkinOperationRequest, onDismiss: () -> Unit, onConfirm: () -> Unit) {
    val restoreAll = request.type == OpType.RESTORE_ALL
    ModalBottomSheet(onDismissRequest = onDismiss, containerColor = RetroSurface) {
        Column(Modifier.fillMaxWidth().navigationBarsPadding().padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Icon(if (restoreAll) Icons.Filled.WarningAmber else Icons.Filled.Security, null, tint = if (restoreAll) RetroGold else RetroAccent)
            Text(request.title, style = MaterialTheme.typography.headlineSmall, color = RetroText)
            if (request.vehicle.isNotBlank()) Text(request.vehicle, color = RetroTextSoft)
            if (request.target.isNotBlank()) Text(request.target, fontFamily = FontFamily.Monospace, color = RetroText, modifier = Modifier.fillMaxWidth().background(RetroBg).padding(10.dp))
            if (request.type == OpType.APPLY) {
                SheetLine("Source and target names match", Icons.Filled.CompareArrows)
                SheetLine("Original backup will be verified first", Icons.Filled.Backup)
                SheetLine("RR3 will be force-stopped before the simulated operation", Icons.Filled.StopCircle)
                SheetLine("SHA-256 verification and rollback available", Icons.Filled.VerifiedUser)
            } else request.backup?.let { Text("Immutable backup ${it.hash}\nCreated ${it.createdAt}", fontFamily = FontFamily.Monospace, color = RetroTextDim) }
            if (restoreAll) Text("Every valid immutable original will be restored. Failed targets remain available for retry.", color = RetroGold)
            Button(onClick = onConfirm, modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp), colors = ButtonDefaults.buttonColors(containerColor = if (restoreAll) RetroGold else RetroAccent, contentColor = if (restoreAll) Color.Black else Color.White)) { Text(if (restoreAll) "Restore all originals" else if (request.type == OpType.APPLY) "Apply skin" else "Restore original") }
            TextButton(onClick = onDismiss, modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)) { Text("Cancel") }
        }
    }
}

@Composable private fun SheetLine(text: String, icon: ImageVector) { Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { Icon(icon, null, tint = RetroSuccess); Text(text, color = RetroTextSoft) } }
