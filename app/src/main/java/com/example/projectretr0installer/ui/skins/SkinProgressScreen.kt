package com.example.projectretr0installer.ui.skins

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import com.example.projectretr0installer.ui.components.*
import com.example.projectretr0installer.ui.theme.*
import kotlinx.coroutines.delay

@Composable fun SkinProgressScreen(op: OpKind, onDone: () -> Unit, onCancel: () -> Unit, forceFailure: Boolean = false) {
    val labels = if (op == OpKind.APPLY) listOf("Validating", "Backing up original", "Copying skin", "Verifying hash") else listOf("Validating", "Restoring original", "Verifying")
    var phase by remember(forceFailure) { mutableIntStateOf(0) }; var failed by remember(forceFailure) { mutableStateOf(false) }
    LaunchedEffect(forceFailure) { while (phase < labels.size && !failed) { delay(700); if (forceFailure && phase == 1) failed = true else phase++ } }
    Column(Modifier.fillMaxSize().padding(18.dp), verticalArrangement = Arrangement.spacedBy(18.dp)) {
        ScreenTopBar(if(op == OpKind.APPLY) "Applying skin" else "Restoring original", "Simulated operation", onCancel)
        if (failed) { EmptyState(Icons.Filled.ErrorOutline, "Operation failed", "The simulated verification did not complete."); PrimaryButton("Retry") { failed = false; phase = 0 }; SecondaryButton("Restore original", Modifier.fillMaxWidth(), Icons.Filled.Restore, onCancel); OutlineButton("View details") {} }
        else { labels.forEachIndexed { i, text -> Row(Modifier.fillMaxWidth().heightIn(min = 48.dp), verticalAlignment = Alignment.CenterVertically) { when { i < phase -> Icon(Icons.Filled.CheckCircle, "Completed", tint = RetroSuccess); i == phase && phase < labels.size -> CircularProgressIndicator(Modifier.size(22.dp), strokeWidth = 2.dp); else -> Icon(Icons.Filled.RadioButtonUnchecked, "Waiting", tint = RetroTextMuted) }; Spacer(Modifier.width(12.dp)); Text(text, color = if(i <= phase) RetroText else RetroTextMuted) } }; if (phase >= labels.size) { EmptyState(Icons.Filled.CheckCircle, "Success", if(op == OpKind.APPLY) "The skin is ready." else "Original graphics restored."); if(op == OpKind.APPLY) PrimaryButton("Launch Real Racing 3", icon = Icons.Filled.SportsEsports, onClick = onDone) else PrimaryButton("Done", onClick = onDone) } else if (phase < 2) OutlineButton(text = "Cancel", onClick = onCancel) }
    }
}
