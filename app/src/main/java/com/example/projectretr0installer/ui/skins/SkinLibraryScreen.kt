package com.example.projectretr0installer.ui.skins

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import com.example.projectretr0installer.ui.components.*
import com.example.projectretr0installer.ui.theme.*
import kotlin.math.max

@Composable
fun SkinLibraryScreen(
    onBack: () -> Unit,
    onOpen: (String) -> Unit,
    onImport: () -> Unit,
    onCommunity: () -> Unit = onBack,
    embedded: Boolean = false,
    onApply: (String) -> Unit = {},
    onRevert: (String) -> Unit = {},
    previewQuery: String = "",
    previewSkins: List<CarSkin> = FakeSkins.library,
) {
    var query by rememberSaveable { mutableStateOf(previewQuery) }
    var filter by rememberSaveable { mutableIntStateOf(0) }
    val options = listOf("All", "Active", "Audi", "Porsche", "Other")
    val skins = previewSkins.filter {
        (it.title.contains(query, true) || it.vehicle.contains(query, true)) && when (filter) {
            1 -> it.status == SkinStatus.ACTIVE
            2 -> "Audi" in it.vehicle
            3 -> "Porsche" in it.vehicle
            4 -> "Audi" !in it.vehicle && "Porsche" !in it.vehicle
            else -> true
        }
    }

    BoxWithConstraints(Modifier.fillMaxSize()) {
        val columnCount = when {
            this@BoxWithConstraints.maxWidth < 360.dp -> 1
            this@BoxWithConstraints.maxWidth >= 840.dp -> 3
            else -> 2
        }
        val horizontalPadding = if (embedded) 16.dp else 18.dp
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = horizontalPadding,
                top = if (embedded) 12.dp else 8.dp,
                end = horizontalPadding,
                bottom = if (embedded) 96.dp else 24.dp,
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            if (!embedded) item(key = "top-bar") { ScreenTopBar("Car Skins", "Your local skin library", onBack) }
            item(key = "search") { RetroSearchField(query, { query = it }, "Search your skins…", Icons.Filled.Search) }
            if (!embedded) {
                previewSkins.firstOrNull { it.status == SkinStatus.ACTIVE }?.let { active ->
                    item(key = "active-heading") { SectionHeader("Active skin", Icons.Filled.CheckCircle) }
                    item(key = "active-${active.id}") { ActiveSkinCard(active) { onRevert(active.id) } }
                }
                item(key = "standalone-actions") {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        PrimaryButton("Import skin", Modifier.weight(1f), Icons.Filled.Upload, onImport)
                        SecondaryButton("Community", Modifier.weight(1f), Icons.Filled.Public, onCommunity)
                    }
                }
            } else {
                item(key = "import") { PrimaryButton("Import skin", Modifier.fillMaxWidth(), Icons.Filled.Upload, onImport) }
            }
            item(key = "filters") { SkinFilterRow(options, filter) { filter = it } }
            item(key = "library-heading") { SectionHeader("Library", Icons.Filled.Garage, trailingCount = skins.size) }
            if (skins.isEmpty()) {
                item(key = "empty") { EmptyState(Icons.Filled.Garage, "No skins found", "Try another search or import a skin.", "Import", onImport) }
            } else {
                items(
                    items = skins.chunked(columnCount),
                    key = { row -> row.joinToString(prefix = "row-", separator = "-") { it.id } },
                ) { row ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.Top,
                    ) {
                        row.forEach { skin ->
                            LibrarySkinCard(
                                skin = skin,
                                onClick = { onOpen(skin.id) },
                                onOverflow = { onApply(skin.id) },
                                modifier = Modifier.weight(1f),
                                compact = embedded,
                            )
                        }
                        repeat(max(0, columnCount - row.size)) { Spacer(Modifier.weight(1f)) }
                    }
                }
            }
        }
    }
}

@Composable
fun ScreenTopBar(title: String, subtitle: String, onBack: () -> Unit, actions: @Composable RowScope.() -> Unit = {}) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        RetroIconBox(Icons.Filled.ArrowBack, onClick = onBack)
        Spacer(Modifier.width(10.dp))
        Column(Modifier.weight(1f)) {
            Text(title, color = RetroText, style = MaterialTheme.typography.titleLarge)
            Text(subtitle, color = RetroTextMuted, style = MaterialTheme.typography.labelSmall)
        }
        actions()
    }
}
