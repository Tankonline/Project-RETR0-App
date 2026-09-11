package com.example.projectretr0installer.ui.skins

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.projectretr0installer.IRetr0UserService
import com.example.projectretr0installer.data.skins.SkinDownloadManager
import com.example.projectretr0installer.data.skins.SkinRepository
import com.example.projectretr0installer.ui.components.*

enum class GalleryState { CONTENT, LOADING, EMPTY, ERROR }

@Composable fun CarSkinsTab(service: IRetr0UserService? = null, isServiceConnected: Boolean = false, onOpenMySkins: () -> Unit = {}, onOpenImport: () -> Unit = onOpenMySkins, onFullScreenChange: (Boolean) -> Unit = {}) {
    val context = LocalContext.current
    val repository = remember(context) { SkinRepository(context) }
    val communityViewModel = remember(context, repository) {
        SkinCommunityViewModel(repository, SkinDownloadManager(context))
    }
    val stack = remember { mutableStateListOf<SkinsRoute>(SkinsRoute.Gallery) }
    var galleryCache by remember { mutableStateOf(emptyList<CommunitySkin>()) }
    fun push(route: SkinsRoute) { stack.add(route) }
    fun back() { if (stack.size > 1) stack.removeAt(stack.lastIndex) }
    BackHandler(stack.size > 1) { back() }
    val focusedRoute = stack.last() is SkinsRoute.CommunityDetails || stack.last() is SkinsRoute.Progress
    LaunchedEffect(focusedRoute) { onFullScreenChange(focusedRoute) }
    DisposableEffect(Unit) { onDispose { onFullScreenChange(false) } }
    Box(Modifier.fillMaxSize()) {
    when (val route = stack.last()) {
        SkinsRoute.Gallery -> CommunityGalleryContent(
            onOpen = { push(SkinsRoute.CommunityDetails(it)) },
            onMySkins = onOpenMySkins,
            onImport = onOpenImport,
            repository = repository,
            onItemsLoaded = { galleryCache = it },
        )
        SkinsRoute.Library, SkinsRoute.Import -> LaunchedEffect(route) { onOpenMySkins(); back() }
        is SkinsRoute.CommunityDetails -> CommunitySkinDetails(
            route.id,
            ::back,
            { push(SkinsRoute.Progress(OpKind.APPLY, route.id)) },
            repository,
            galleryCache,
            communityViewModel = communityViewModel,
        )
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
    previewItems: List<CommunitySkin> = emptyList(),
    repository: SkinRepository? = null,
    onItemsLoaded: (List<CommunitySkin>) -> Unit = {},
) {
    val context = LocalContext.current
    val runtimeRepository = repository ?: remember(context) { SkinRepository(context) }
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
                SectionHeader("All skins", Icons.Filled.GridView, trailingCount = skins.size)
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
