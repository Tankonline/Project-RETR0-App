package com.retr0.projectretr0.ui.skins

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import android.util.Log
import com.retr0.projectretr0.IRetr0UserService
import com.retr0.projectretr0.data.skins.SkinDownloadManager
import com.retr0.projectretr0.data.skins.SkinRepository
import com.retr0.projectretr0.ui.components.*
import kotlinx.coroutines.launch

enum class GalleryState { CONTENT, LOADING, EMPTY, ERROR }

@Composable fun CarSkinsTab(service: IRetr0UserService? = null, isServiceConnected: Boolean = false, onOpenMySkins: () -> Unit = {}, onOpenImport: () -> Unit = onOpenMySkins, onFullScreenChange: (Boolean) -> Unit = {}, modifier: Modifier = Modifier, initialSkinId: String? = null, onInitialSkinIdHandled: () -> Unit = {}) {
    val context = LocalContext.current
    val repository = remember(context) { SkinRepository(context) }
    val communityViewModel = remember(context, repository) {
        SkinCommunityViewModel(repository, SkinDownloadManager(context))
    }
    // Persist the selected skin across parent recompositions and configuration changes.
    // A transient mutable route stack could otherwise reset to Gallery while details loads.
    var selectedCommunitySkinId by rememberSaveable { mutableStateOf<String?>(null) }
    var galleryCache by remember { mutableStateOf(emptyList<CommunitySkin>()) }
    var navigationError by rememberSaveable { mutableStateOf<String?>(null) }
    val focusedRoute = selectedCommunitySkinId != null
    BackHandler(focusedRoute) {
        Log.w("COMMUNITY_SKIN", "Back navigation cleared selected skin: $selectedCommunitySkinId")
        selectedCommunitySkinId = null
    }
    LaunchedEffect(focusedRoute) {
        Log.d("COMMUNITY_SKIN", "Route changed: focused=$focusedRoute, skinId=$selectedCommunitySkinId")
        onFullScreenChange(focusedRoute)
    }

    LaunchedEffect(initialSkinId) {
        initialSkinId?.let {
            Log.i("COMMUNITY_SKIN", "Handling initial skin ID: $it")
            selectedCommunitySkinId = it
            onInitialSkinIdHandled()
        }
    }

    DisposableEffect(Unit) { onDispose { onFullScreenChange(false) } }

    Box(modifier.fillMaxSize()) {
        val selectedId = selectedCommunitySkinId
        if (navigationError != null) {
            EmptyState(
                Icons.Filled.ErrorOutline,
                "Couldn’t open community skin",
                navigationError.orEmpty(),
                "Back to Community Hub",
            ) { navigationError = null }
        } else if (selectedId == null) {
            CommunityGalleryContent(
                onOpen = { skinId ->
                    Log.i("COMMUNITY_SKIN", "Opening community skin: $skinId")
                    navigationError = null
                    selectedCommunitySkinId = skinId
                },
                onMySkins = onOpenMySkins,
                onImport = onOpenImport,
                repository = repository,
                onItemsLoaded = { galleryCache = it },
            )
        } else {
            CommunitySkinDetails(
                selectedId,
                {
                    Log.w("COMMUNITY_SKIN", "Detail screen requested back: $selectedId")
                    selectedCommunitySkinId = null
                },
                { /* Applying from the Community Hub is handled by the download flow. */ },
                repository,
                galleryCache,
                communityViewModel = communityViewModel,
                service = service,
                isServiceConnected = isServiceConnected,
            )
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
                val scope = rememberCoroutineScope()
                skins.firstOrNull { it.featured }?.let { featured ->
                    SectionHeader("Featured", Icons.Filled.Star)
                    FeaturedSkinCard(
                        skin = featured, 
                        onGet = { onOpen(featured.id) }, 
                        onClick = { onOpen(featured.id) },
                        onFav = {
                            scope.launch {
                                if (runtimeRepository.setLike(featured.id, !featured.likedByMe).success) {
                                    retry++
                                }
                            }
                        },
                        onDelete = if (featured.isOwner) { {
                            scope.launch {
                                if (runtimeRepository.deleteOwned(featured.id).success) {
                                    retry++
                                }
                            }
                        } } else null
                    )
                }
                SectionHeader("All skins", Icons.Filled.GridView, trailingCount = skins.size)
                AdaptiveSkinGrid(skins) { item -> 
                    GalleryCard(
                        skin = item, 
                        onClick = { onOpen(item.id) },
                        onFav = {
                            scope.launch {
                                if (runtimeRepository.setLike(item.id, !item.likedByMe).success) {
                                    retry++
                                }
                            }
                        },
                        onDelete = if (item.isOwner) { {
                            scope.launch {
                                if (runtimeRepository.deleteOwned(item.id).success) {
                                    retry++ // Trigger reload
                                }
                            }
                        } } else null
                    ) 
                }
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
