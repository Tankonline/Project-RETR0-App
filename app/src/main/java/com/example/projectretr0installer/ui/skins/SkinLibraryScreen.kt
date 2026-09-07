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
import com.example.projectretr0installer.ui.components.*
import com.example.projectretr0installer.ui.theme.*

@Composable fun SkinLibraryScreen(onBack: () -> Unit, onOpen: (String) -> Unit, onImport: () -> Unit, onCommunity: () -> Unit = onBack) {
    var query by remember { mutableStateOf("") }; var filter by remember { mutableIntStateOf(0) }; var confirm by remember { mutableStateOf(false) }
    val options = listOf("All", "Active", "Audi", "Porsche", "Other")
    val skins = FakeSkins.library.filter { (it.title.contains(query, true) || it.vehicle.contains(query, true)) && when(filter) { 1 -> it.status == SkinStatus.ACTIVE; 2 -> "Audi" in it.vehicle; 3 -> "Porsche" in it.vehicle; 4 -> "Audi" !in it.vehicle && "Porsche" !in it.vehicle; else -> true } }
    Column(Modifier.fillMaxSize()) {
        Column(Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(horizontal = 18.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            ScreenTopBar("Car Skins", "Your local skin library", onBack)
            RetroSearchField(query, { query = it }, "Search your skins…", Icons.Filled.Search)
            FakeSkins.library.firstOrNull { it.status == SkinStatus.ACTIVE }?.let { SectionHeader("Active skin", Icons.Filled.CheckCircle); ActiveSkinCard(it) { confirm = true } }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { PrimaryButton("Import skin", Modifier.weight(1f), Icons.Filled.Upload, onImport); SecondaryButton("Community", Modifier.weight(1f), Icons.Filled.Public, onCommunity) }
            SkinFilterRow(options, filter) { filter = it }; SectionHeader("Library", Icons.Filled.Garage, skins.size)
            if (skins.isEmpty()) EmptyState(Icons.Filled.Garage, "No skins found", "Try another search or import a skin.", "Import", onImport) else BoxWithConstraints(modifier = Modifier.fillMaxWidth()) { val columns = if (this.maxWidth < 360.dp) 1 else 2; Column(verticalArrangement = Arrangement.spacedBy(10.dp)) { skins.chunked(columns).forEach { row -> Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) { row.forEach { Box(Modifier.weight(1f)) { LibrarySkinCard(it, { onOpen(it.id) }, {}) } }; repeat(columns-row.size) { Spacer(Modifier.weight(1f)) } } } } }
            Spacer(Modifier.height(12.dp))
        }
    }
    if (confirm) DestructiveConfirmationDialog("Revert active skin?", "The saved original graphics will be restored.", "Revert", { confirm = false }, { confirm = false })
}

@Composable fun ScreenTopBar(title: String, subtitle: String, onBack: () -> Unit, actions: @Composable RowScope.() -> Unit = {}) { Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) { RetroIconBox(Icons.Filled.ArrowBack, onClick = onBack); Spacer(Modifier.width(10.dp)); Column(Modifier.weight(1f)) { Text(title, color = RetroText, style = MaterialTheme.typography.titleLarge); Text(subtitle, color = RetroTextMuted, style = MaterialTheme.typography.labelSmall) }; actions() } }
