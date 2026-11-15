package com.retr0.projectretr0.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.retr0.projectretr0.data.carModels.local.LocalCarModel
import com.retr0.projectretr0.data.skins.local.LocalSkinEntity
import com.retr0.projectretr0.ui.theme.RetroText
import com.retr0.projectretr0.ui.theme.RetroTextMuted
import com.retr0.projectretr0.ui.skins.EmptyState

@Composable
fun DownloadedModsSection(
    skins: List<LocalSkinEntity>,
    carModels: List<LocalCarModel>,
    onSkin: (String) -> Unit,
    onCarModel: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (skins.isEmpty() && carModels.isEmpty()) {
        EmptyState(Icons.Filled.Download, "No downloaded mods", "Mods downloaded from the Community Hub will appear here.")
        return
    }
    LazyColumn(modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        item {
            Text("Downloaded Mods", color = RetroText, style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(top = 16.dp))
            Text("Community Hub downloads ready to apply", color = RetroTextMuted)
        }
        items(carModels, key = { "car:${it.id}" }) { model ->
            DownloadedModRow(model.name, "Car model · ${model.vehicleDirectory}", Icons.Filled.DirectionsCar) { onCarModel(model.id) }
        }
        items(skins, key = { "skin:${it.id}" }) { skin ->
            DownloadedModRow(skin.title, "Car skin · ${skin.vehicle}", Icons.Filled.Image) { onSkin(skin.id) }
        }
    }
}

@Composable
private fun DownloadedModRow(title: String, subtitle: String, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    RetroCard(Modifier.clickable(onClick = onClick).fillMaxWidth()) {
        androidx.compose.foundation.layout.Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
            androidx.compose.material3.Icon(icon, contentDescription = null, tint = com.retr0.projectretr0.ui.theme.RetroAccent)
            androidx.compose.foundation.layout.Spacer(Modifier.width(12.dp))
            androidx.compose.foundation.layout.Column {
                Text(title, color = RetroText, style = MaterialTheme.typography.titleMedium)
                Text(subtitle, color = RetroTextMuted, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
