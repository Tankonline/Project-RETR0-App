package com.example.projectretr0installer.ui.skins

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.projectretr0installer.ui.components.PrimaryButton
import com.example.projectretr0installer.ui.theme.RetroDanger

@OptIn(ExperimentalMaterial3Api::class)
@Composable fun SkinTargetBrowserScreen(packageRoot: String, importedFileName: String, source: SkinTargetBrowserSource, onClose: () -> Unit, onConfirm: (SelectedSkinTarget) -> Unit, modifier: Modifier = Modifier, previewEntries: List<SkinBrowserEntry>? = null) {
    var path by remember(packageRoot) { mutableStateOf(packageRoot) }
    var result by remember { mutableStateOf<BrowserResult>(BrowserResult.Success(emptyList())) }
    var selected by remember { mutableStateOf<SkinBrowserEntry?>(null) }
    var retry by remember { mutableIntStateOf(0) }
    LaunchedEffect(path, retry, previewEntries) { result = if (previewEntries != null) BrowserResult.Success(previewEntries) else source.list(packageRoot, path) }
    val validated = selected?.let { SkinTargetValidator.validate(packageRoot, importedFileName, it).getOrNull() }
    fun up() { if (path != packageRoot) { path = path.substringBeforeLast('/').coerceAtLeastRoot(packageRoot); selected = null } else onClose() }
    BackHandler { up() }
    Scaffold(modifier, topBar = { TopAppBar(title = { Text("Choose RR3 target") }, navigationIcon = { IconButton(onClick = ::up) { Icon(Icons.Filled.ArrowBack, "Back") } }, actions = { IconButton(onClick = onClose) { Icon(Icons.Filled.Close, "Close") } }) }, bottomBar = { Surface { Column(Modifier.fillMaxWidth().navigationBarsPadding().padding(16.dp)) { selected?.let { Text(it.name, maxLines = 1, overflow = TextOverflow.Ellipsis); SkinTargetValidator.validate(packageRoot, importedFileName, it).exceptionOrNull()?.message?.let { message -> Text(message, color = RetroDanger, style = MaterialTheme.typography.bodySmall) } }; PrimaryButton("Confirm target", Modifier.fillMaxWidth(), onClick = { validated?.let(onConfirm) }) } } }) { padding ->
        Column(Modifier.padding(padding).fillMaxSize().padding(horizontal = 16.dp)) {
            AssistChip(onClick = {}, label = { Text("RR3 package: ${packageRoot.substringAfterLast('/')}") }, leadingIcon = { Icon(Icons.Filled.FolderOpen, null) })
            Text(path.removePrefix(packageRoot).ifEmpty { "/" }, style = MaterialTheme.typography.bodySmall, maxLines = 2, overflow = TextOverflow.Ellipsis)
            when (val state = result) {
                is BrowserResult.Error -> Box(Modifier.fillMaxSize(), Alignment.Center) { Column(horizontalAlignment = Alignment.CenterHorizontally) { Text(state.message); TextButton(onClick = { retry++ }) { Text("Retry") } } }
                is BrowserResult.Success -> if (state.entries.isEmpty()) Box(Modifier.fillMaxSize(), Alignment.Center) { Text("This folder is empty") } else LazyColumn { items(state.entries, key = { it.path }) { entry ->
                    val selectable = !entry.isDirectory && entry.name.endsWith(".dds.z")
                    ListItem(headlineContent = { Text(entry.name, maxLines = 1, overflow = TextOverflow.Ellipsis) }, leadingContent = { Icon(if (entry.isDirectory) Icons.Filled.Folder else Icons.Filled.InsertDriveFile, null) }, supportingContent = { if (entry.isSymbolicLink) Text("Symbolic links are blocked") }, modifier = Modifier.clickable(enabled = entry.isDirectory || selectable) { if (entry.isDirectory) { path = entry.path; selected = null } else selected = entry })
                    HorizontalDivider()
                } }
            }
        }
    }
}

private fun String.coerceAtLeastRoot(root: String) = if (length < root.length || !startsWith(root)) root else this
