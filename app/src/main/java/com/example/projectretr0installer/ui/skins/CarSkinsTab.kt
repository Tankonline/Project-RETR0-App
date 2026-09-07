package com.example.projectretr0installer.ui.skins

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import com.example.projectretr0installer.IRetr0UserService
import com.example.projectretr0installer.data.skins.SkinRepository
import com.example.projectretr0installer.ui.components.*

enum class GalleryState { CONTENT, LOADING, EMPTY, ERROR }

@Composable fun CarSkinsTab(service: IRetr0UserService? = null, isServiceConnected: Boolean = false) {
    val repository = remember { SkinRepository() }
    val stack = remember { mutableStateListOf<SkinsRoute>(SkinsRoute.Gallery) }
    var galleryCache by remember { mutableStateOf(emptyList<CommunitySkin>()) }
    fun push(route: SkinsRoute) { stack.add(route) }
    fun back() { if (stack.size > 1) stack.removeAt(stack.lastIndex) }
    BackHandler(stack.size > 1) { back() }
    Box(Modifier.fillMaxSize()) {
    when (val route = stack.last()) {
        SkinsRoute.Gallery -> CommunityGalleryContent(
            onOpen = { push(SkinsRoute.CommunityDetails(it)) },
            onMySkins = { push(SkinsRoute.Library) },
            onImport = { push(SkinsRoute.Import) },
            repository = repository,
            onItemsLoaded = { galleryCache = it },
        )
        SkinsRoute.Library -> SkinLibraryScreen(onBack = ::back, onOpen = { push(SkinsRoute.LocalDetails(it)) }, onImport = { push(SkinsRoute.Import) })
        SkinsRoute.Import -> SkinImportWizard(
            onBack = ::back,
            onDone = ::back,
            modifier = Modifier.fillMaxSize(),
            browserSource = ShizukuSkinTargetBrowserSource(if (isServiceConnected) service else null),
        )
        is SkinsRoute.CommunityDetails -> CommunitySkinDetails(route.id, ::back, { push(SkinsRoute.Progress(OpKind.APPLY, route.id)) }, repository, galleryCache)
        is SkinsRoute.LocalDetails -> LocalSkinDetails(route.id, ::back, { push(SkinsRoute.Progress(OpKind.APPLY, route.id)) }, { push(SkinsRoute.Progress(OpKind.REVERT, route.id)) })
        is SkinsRoute.Progress -> SkinProgressScreen(route.op, ::back, ::back)
    }
    }
}

@Composable fun CommunityGalleryContent(
    onOpen: (String) -> Unit,
    onMySkins: () -> Unit,
    onImport: () -> Unit,
    previewState: GalleryState? = null,
    previewItems: List<CommunitySkin> = FakeSkins.community,
    repository: SkinRepository? = null,
    onItemsLoaded: (List<CommunitySkin>) -> Unit = {},
) {
    val runtimeRepository = repository ?: remember { SkinRepository() }
    var query by remember { mutableStateOf("") }
    var filter by remember { mutableIntStateOf(0) }
    var retry by remember { mutableIntStateOf(0) }
    var state by remember(previewState) { mutableStateOf(previewState ?: GalleryState.LOADING) }
    var skins by remember(previewState) { mutableStateOf(if (previewState != null) previewItems else emptyList()) }
    val sort = listOf("newest", "newest", "downloads", "likes")[filter]
    LaunchedEffect(query, sort, retry, previewState) {
        if (previewState != null) return@LaunchedEffect
        state = GalleryState.LOADING
        runCatching { runtimeRepository.list(query, sort) }
            .onSuccess { items -> skins = items; onItemsLoaded(items); state = if (items.isEmpty()) GalleryState.EMPTY else GalleryState.CONTENT }
            .onFailure { state = GalleryState.ERROR }
    }
    Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextButton(onClick = onMySkins, modifier = Modifier.heightIn(min = 48.dp)) { Icon(Icons.Filled.Garage, null); Text(" My skins - ${FakeSkins.library.size}") }
            Spacer(Modifier.weight(1f))
            TextButton(onClick = onImport, modifier = Modifier.heightIn(min = 48.dp)) { Icon(Icons.Filled.Upload, null); Text(" Import") }
        }
        RetroSearchField(query, { query = it }, "Search car skins...", Icons.Filled.Search)
        SkinFilterRow(listOf("Compatible", "Newest", "Most downloaded", "Most liked"), filter) { filter = it }
        when (state) {
            GalleryState.LOADING -> repeat(2) { LoadingSkinCard() }
            GalleryState.ERROR -> ErrorState { retry++ }
            GalleryState.EMPTY -> EmptyState(Icons.Filled.DirectionsCar, "No skins found", "Try another search or import your own.", "Import skin", onImport)
            GalleryState.CONTENT -> {
                skins.firstOrNull { it.featured }?.let { featured ->
                    SectionHeader("Featured", Icons.Filled.Star)
                    FeaturedSkinCard(featured, { onOpen(featured.id) }, { onOpen(featured.id) })
                }
                SectionHeader("All skins", Icons.Filled.GridView, skins.size)
                AdaptiveSkinGrid(skins) { item -> GalleryCard(item, onClick = { onOpen(item.id) }) }
            }
        }
    }
}

@Composable private fun AdaptiveSkinGrid(items: List<CommunitySkin>, content: @Composable (CommunitySkin) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        items.chunked(2).forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                row.forEach { item -> Box(Modifier.weight(1f)) { content(item) } }
                repeat(2 - row.size) { Spacer(Modifier.weight(1f)) }
            }
        }
    }
}
