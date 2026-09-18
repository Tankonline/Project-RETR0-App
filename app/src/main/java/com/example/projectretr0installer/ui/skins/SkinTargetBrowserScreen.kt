package com.example.projectretr0installer.ui.skins

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.InsertDriveFile
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.projectretr0installer.ui.components.*
import com.example.projectretr0installer.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable fun SkinTargetBrowserScreen(packageRoot: String, importedFileName: String, source: SkinTargetBrowserSource, onClose: () -> Unit, onConfirm: (SelectedSkinTarget) -> Unit, modifier: Modifier = Modifier, previewEntries: List<SkinBrowserEntry>? = null, previewSelectedEntry: SkinBrowserEntry? = null, previewMode: Int = 0, previewError: String? = null) {
    var mode by remember { mutableIntStateOf(previewMode) }; var path by remember(packageRoot) { mutableStateOf(packageRoot) }; var result by remember { mutableStateOf<BrowserResult>(BrowserResult.Success(emptyList())) }; var selected by remember { mutableStateOf(previewSelectedEntry) }; var scan by remember { mutableStateOf<SmartScanResult?>(null) }; var scanning by remember { mutableStateOf(false) }; var query by remember { mutableStateOf("") }; var retry by remember { mutableIntStateOf(0) }
    LaunchedEffect(path, retry, previewEntries, mode, previewError) { if (mode == 1) result = previewError?.let(BrowserResult::Error) ?: if (previewEntries != null) BrowserResult.Success(previewEntries) else source.list(packageRoot, path) }
    if (previewEntries != null && mode == 1 && previewError == null) result = BrowserResult.Success(previewEntries)
    if (previewError != null && mode == 1) result = BrowserResult.Error(previewError)
    LaunchedEffect(mode, retry, previewEntries, previewSelectedEntry) {
        if (mode == 0) {
            scanning = true
            scan = if (previewEntries != null) {
                previewEntries
                    .firstOrNull { entry ->
                        entry.name.equals(importedFileName, ignoreCase = true) &&
                            SkinTargetValidator.validate(packageRoot, entry.name, entry).isSuccess
                    }
                    ?.let(SmartScanResult::Found)
                    ?: SmartScanResult.NotFound
            } else {
                smartMatch(source, packageRoot, importedFileName)
            }
            scanning = false
            (scan as? SmartScanResult.Found)?.entry?.let {
                selected = it
                path = it.path.substringBeforeLast('/')
            }
            if (scan !is SmartScanResult.Found) selected = previewSelectedEntry
        }
    }
    val suggestedTargets = (scan as? SmartScanResult.Candidates)?.entries.orEmpty()
    val validation = selected?.let { SkinTargetValidator.validate(packageRoot, importedFileName, it) }; val target = validation?.getOrNull(); val canConfirm = validation?.isSuccess == true; val presentation = if (selected == null) ValidationPresentation("Select the matching livery to continue", ValidationSeverity.INFO) else validationPresentation(validation?.exceptionOrNull(), importedFileName)
    fun up() { if (path != packageRoot) { path = path.substringBeforeLast('/').coerceAtLeastRoot(packageRoot); selected = null } else onClose() }
    BackHandler { up() }
    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = { CompactBrowserTopBar(mode, ::up, onClose) },
        bottomBar = {
            Surface(color = RetroSurface, tonalElevation = 3.dp) {
                Column(
                    Modifier.fillMaxWidth().imePadding().padding(horizontal = 14.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    selected?.let { Text("${importedFileName}  →  ${it.name}", fontFamily = FontFamily.Monospace, maxLines = 1, overflow = TextOverflow.Ellipsis, style = MaterialTheme.typography.labelSmall) }
                    Text(presentation.message, color = severityColor(presentation.severity), style = MaterialTheme.typography.bodySmall, maxLines = 2, overflow = TextOverflow.Ellipsis)
                    Button(onClick = { if (canConfirm) target?.let(onConfirm) }, enabled = canConfirm, modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)) { Text("Confirm target") }
                }
            }
        },
    ) { padding ->
        BoxWithConstraints(Modifier.padding(padding).fillMaxSize()) {
            val compactHeight = this@BoxWithConstraints.maxHeight < 600.dp
            Column(Modifier.fillMaxSize().padding(horizontal = 14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                SegmentedTabs(listOf("Smart Match", "Browse All"), mode) { mode = it; selected = null }
                Surface(Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp), color = RetroSurface) { Row(Modifier.padding(horizontal = 10.dp, vertical = 7.dp), verticalAlignment = Alignment.CenterVertically) { Icon(Icons.AutoMirrored.Filled.InsertDriveFile, null, tint = RetroAccent, modifier = Modifier.size(20.dp)); Spacer(Modifier.width(8.dp)); Column(Modifier.weight(1f)) { if (!compactHeight) Text("Source", color = RetroTextMuted, style = MaterialTheme.typography.labelSmall); Text(importedFileName, fontFamily = FontFamily.Monospace, color = RetroText, maxLines = 1, overflow = TextOverflow.Ellipsis) }; Text(packageRoot.substringAfterLast('/'), color = RetroTextMuted, style = MaterialTheme.typography.labelSmall) } }
                if (mode == 0) {
                    Box(Modifier.weight(1f).fillMaxWidth()) {
                        SmartMatchContent(
                            scanning = scanning,
                            result = scan,
                            candidates = suggestedTargets,
                            onCandidateSelected = { selected = it },
                            browse = { mode = 1 },
                            retry = { retry++ },
                        )
                    }
                } else {
                    Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(2.dp), verticalAlignment = Alignment.CenterVertically) { packageBreadcrumbs(packageRoot, path).forEachIndexed { index, crumb -> if (index > 0) Text("/", color = RetroTextMuted); TextButton(onClick = { path = crumb.second; selected = null }, modifier = Modifier.heightIn(min = 48.dp), contentPadding = PaddingValues(horizontal = 6.dp)) { Text(crumb.first, maxLines = 1) } } }
                    OutlinedTextField(query, { query = it }, modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp), placeholder = { Text("Filter this folder") }, leadingIcon = { Icon(Icons.Filled.FilterList, null) }, singleLine = true)
                    Box(Modifier.weight(1f).fillMaxWidth()) { when (val state = result) { is BrowserResult.Error -> EmptyState(Icons.Filled.ErrorOutline, "Can't read this folder", state.message, "Retry") { retry++ }; is BrowserResult.Success -> { val shown = state.entries.filter { it.name.contains(query, true) }; if (shown.isEmpty()) EmptyState(Icons.Filled.FolderOff, "This folder is empty", "Try another breadcrumb or clear the filter.") else LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(bottom = 16.dp)) { items(shown, key = { it.path }) { entry -> BrowserEntryRow(entry, selected == entry) { if (entry.isDirectory) { path = entry.path; selected = null } else selected = entry } } } } } }
                }
            }
        }
    }
}

@Composable private fun CompactBrowserTopBar(mode: Int, onBack: () -> Unit, onClose: () -> Unit) { Surface(color = RetroBg) { Row(Modifier.fillMaxWidth().heightIn(min = 56.dp).padding(horizontal = 4.dp), verticalAlignment = Alignment.CenterVertically) { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back") }; Column(Modifier.weight(1f)) { Text("Choose RR3 target", style = MaterialTheme.typography.titleMedium, maxLines = 1); Text(if (mode == 0) "Smart Match" else "Browse package files", color = RetroTextMuted, style = MaterialTheme.typography.labelSmall, maxLines = 1) }; IconButton(onClick = onClose) { Icon(Icons.Filled.Close, "Close") } } } }

@Composable private fun BrowserEntryRow(entry: SkinBrowserEntry, selected: Boolean, onClick: () -> Unit) { val selectable = entry.isRegularFile && !entry.isSymbolicLink && entry.name.endsWith(".dds.z"); val enabled = entry.isDirectory || selectable; Row(Modifier.fillMaxWidth().heightIn(min = 56.dp).clickable(enabled = enabled, onClick = onClick).padding(horizontal = 8.dp, vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) { Icon(if (entry.isDirectory) Icons.Filled.Folder else Icons.AutoMirrored.Filled.InsertDriveFile, null, tint = if (enabled) RetroAccent else RetroTextMuted); Spacer(Modifier.width(10.dp)); Column(Modifier.weight(1f)) { Text(entry.name, color = if (enabled) RetroText else RetroTextMuted, maxLines = 1, overflow = TextOverflow.Ellipsis); Text(if (entry.isDirectory) "Folder" else if (entry.isSymbolicLink) "Symbolic link blocked" else if (!selectable) "Not a selectable livery" else if (selected) "Selected" else "${entry.size} bytes", color = if (selected) RetroSuccess else RetroTextMuted, style = MaterialTheme.typography.labelSmall, maxLines = 1) }; if (selected) Icon(Icons.Filled.CheckCircle, "Selected", tint = RetroSuccess) }; HorizontalDivider() }

@Composable
private fun SmartMatchContent(
    scanning: Boolean,
    result: SmartScanResult?,
    candidates: List<SkinBrowserEntry>,
    onCandidateSelected: (SkinBrowserEntry) -> Unit,
    browse: () -> Unit,
    retry: () -> Unit,
) {
    Box(Modifier.fillMaxSize()) {
        Column(
            Modifier.fillMaxSize().padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            when {
                scanning -> {
                    Spacer(Modifier.weight(1f))
                    CircularProgressIndicator()
                    Text("Scanning vehicle livery folders…")
                    Spacer(Modifier.weight(1f))
                }
                result is SmartScanResult.Found -> {
                    Spacer(Modifier.weight(1f))
                    Icon(Icons.Filled.CheckCircle, null, tint = RetroSuccess, modifier = Modifier.size(38.dp))
                    Text("Suggested target found", color = RetroText)
                    Text(result.entry.path, fontFamily = FontFamily.Monospace, color = RetroTextDim, maxLines = 3, overflow = TextOverflow.Ellipsis)
                    Spacer(Modifier.weight(1f))
                }
                result is SmartScanResult.Candidates -> {
                    LazyColumn(Modifier.fillMaxWidth().weight(1f)) {
                        items(candidates, key = { it.path }) { entry ->
                            BrowserEntryRow(entry, false) { onCandidateSelected(entry) }
                        }
                    }
                }
                result is SmartScanResult.Error -> {
                    Spacer(Modifier.weight(1f))
                    Icon(Icons.Filled.ErrorOutline, null, tint = RetroDanger)
                    Text(result.message, color = RetroDanger)
                    TextButton(onClick = retry) { Text("Retry") }
                    Spacer(Modifier.weight(1f))
                }
                else -> {
                    Spacer(Modifier.weight(1f))
                    Icon(Icons.Filled.SearchOff, null, tint = RetroTextMuted)
                    Text("No compatible target found", color = RetroText)
                    Text("Browse all RR3 files to choose a target manually.", color = RetroTextDim)
                    TextButton(onClick = browse) { Text("Browse all") }
                    Spacer(Modifier.weight(1f))
                }
            }
        }
    }
}
private fun String.coerceAtLeastRoot(root: String) = if (length < root.length || !startsWith(root)) root else this
private fun severityColor(severity: ValidationSeverity) = when(severity) { ValidationSeverity.SUCCESS -> RetroSuccess; ValidationSeverity.WARNING -> RetroGold; ValidationSeverity.ERROR -> RetroDanger; ValidationSeverity.INFO -> RetroTextDim }
