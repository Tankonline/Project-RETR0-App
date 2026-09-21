package com.example.projectretr0installer.ui.skins

import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import coil.compose.SubcomposeAsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.projectretr0installer.ui.components.*
import com.example.projectretr0installer.ui.theme.*
import java.io.File
import java.util.Locale

@Composable fun SkinCover(modifier: Modifier = Modifier, @DrawableRes art: Int? = null, imageUrl: String? = null, imageUrls: List<String> = emptyList()) {
    val placeholder: @Composable () -> Unit = { Icon(Icons.Filled.DirectionsCar, "Car artwork placeholder", tint = Color.White.copy(.10f), modifier = Modifier.size(48.dp)) }
    val resolvedImageUrl = imageUrl ?: imageUrls.firstOrNull()
    Box(modifier.clip(RoundedCornerShape(12.dp)).background(RetroCover), contentAlignment = Alignment.Center) {
        when {
            resolvedImageUrl != null -> SubcomposeAsyncImage(
                model = resolvedImageUrl,
                contentDescription = "Skin artwork",
                contentScale = ContentScale.Crop,
                loading = {
                    Log.d("SKIN_IMAGE_LOADING", "url=$resolvedImageUrl")
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { placeholder() }
                },
                error = {
                    Log.e("SKIN_IMAGE_DECODE_FAILED", "url=$resolvedImageUrl")
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { placeholder() }
                },
                modifier = Modifier.fillMaxSize(),
            )
            art != null -> Image(painterResource(art), "Skin artwork", contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
            else -> placeholder()
        }
    }
}
@Composable fun SkinAvatar(modifier: Modifier = Modifier, avatarUrl: String?, name: String, size: Dp = 24.dp, localFile: File? = null, localAvatarRevision: String? = null) {
    val fallback: @Composable () -> Unit = {
        Box(modifier.size(size).background(RetroAccentTint, CircleShape), Alignment.Center) {
            Text(name.take(1).uppercase(), color = RetroAccent, style = MaterialTheme.typography.labelSmall)
        }
    }
    when {
        localFile != null && localFile.isFile -> {
            val context = LocalContext.current
            // A content-derived revision changes for every persisted image replacement, even
            // when the installation-keyed filename and filesystem timestamp are unchanged.
            val revision = localAvatarRevision ?: "${localFile.length()}:${localFile.lastModified()}"
            val request = remember(localFile, revision) {
                ImageRequest.Builder(context)
                    .data(localFile)
                    .memoryCacheKey("${localFile.absolutePath}:$revision")
                    .diskCachePolicy(CachePolicy.DISABLED)
                    .build()
            }
            SubcomposeAsyncImage(
                model = request,
                contentDescription = "Avatar for $name",
                modifier = modifier.size(size).clip(CircleShape).background(RetroSurfaceAlt),
                contentScale = ContentScale.Crop,
                error = { fallback() },
                loading = { fallback() },
            )
        }
        avatarUrl != null && avatarUrl.startsWith("https://") -> SubcomposeAsyncImage(
            model = avatarUrl,
            contentDescription = "Avatar for $name",
            modifier = modifier.size(size).clip(CircleShape).background(RetroSurfaceAlt),
            contentScale = ContentScale.Crop,
            error = { fallback() },
            loading = { fallback() }
        )
        else -> fallback()
    }
}
@Composable fun CompatibilityBadge(c: Compatibility, modifier: Modifier = Modifier) {
    val pair = when (c) { Compatibility.COMPATIBLE -> Icons.Filled.Check to RetroSuccess; Compatibility.WRONG_FORMAT -> Icons.Filled.WarningAmber to RetroGold; Compatibility.VERIFICATION_UNAVAILABLE -> Icons.Filled.CloudOff to RetroGold; else -> Icons.Filled.HelpOutline to RetroTextMuted }
    BadgePill(c.label, pair.first, pair.second, modifier)
}
@Composable fun StatusChip(s: SkinStatus, modifier: Modifier = Modifier) { if (s != SkinStatus.LOCAL) BadgePill(if (s == SkinStatus.ACTIVE) "Active" else "Downloaded", if (s == SkinStatus.ACTIVE) Icons.Filled.CheckCircle else Icons.Filled.Download, if (s == SkinStatus.ACTIVE) RetroSuccess else RetroAccent, modifier) }
@Composable fun GalleryCard(skin: CommunitySkin, onClick: () -> Unit, onFav: (() -> Unit)? = null, onDelete: (() -> Unit)? = null, modifier: Modifier = Modifier) {
    RetroCard(modifier.clickable(onClick = onClick)) {
        Box {
            SkinCover(Modifier.fillMaxWidth().aspectRatio(1.35f), imageUrl = skin.coverUrl, imageUrls = skin.screenshotUrls)
            Row(Modifier.align(Alignment.TopEnd).padding(7.dp), horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
                if (skin.isOwner) BadgePill("Mine", Icons.Filled.Person, RetroAccent)
                CompatibilityBadge(skin.compatibility)
            }
            if (skin.isOwner && onDelete != null) {
                Box(Modifier.align(Alignment.BottomEnd).padding(8.dp).size(32.dp).background(RetroDanger.copy(.8f), CircleShape).clickable(onClick = onDelete), Alignment.Center) {
                    Icon(Icons.Filled.Delete, null, tint = Color.White, modifier = Modifier.size(16.dp))
                }
            }
        }
        Spacer(Modifier.height(9.dp))
        CardTitle(skin.title, skin.vehicle)
        Creator(skin.creator, skin.creatorId)
        Spacer(Modifier.height(8.dp))
        Metrics(skin.likes, skin.downloads, skin.likedByMe, onFav)
    }
}
@Composable
fun LibrarySkinCard(
    skin: CarSkin,
    onClick: () -> Unit,
    onOverflow: () -> Unit,
    modifier: Modifier = Modifier,
    compact: Boolean = false,
) {
    val shape = RoundedCornerShape(12.dp)
    val contentPadding = if (compact) 9.dp else 14.dp
    Column(
        modifier = modifier
            .clip(shape)
            .background(RetroSurface)
            .border(1.dp, RetroBorder, shape)
            .clickable(onClick = onClick)
            .padding(contentPadding),
        verticalArrangement = Arrangement.spacedBy(if (compact) 6.dp else 9.dp),
    ) {
        Box {
            SkinCover(Modifier.fillMaxWidth().aspectRatio(if (compact) 1.55f else 1.35f), imageUrl = skin.coverUrl)
            StatusChip(skin.status, Modifier.align(Alignment.TopStart).padding(6.dp).padding(end = 44.dp))
            IconButton(
                onClick = onOverflow,
                modifier = Modifier.align(Alignment.TopEnd).size(48.dp),
            ) { Icon(Icons.Filled.MoreVert, "Skin options", tint = RetroTextSoft) }
        }
        Text(skin.title, color = RetroText, style = MaterialTheme.typography.titleSmall, maxLines = 1, overflow = TextOverflow.Ellipsis)
        Text(skin.vehicle, color = RetroTextMuted, style = MaterialTheme.typography.bodySmall, maxLines = 1, overflow = TextOverflow.Ellipsis)
        Text("by ${skin.creator}", color = RetroTextDim, style = MaterialTheme.typography.bodySmall, maxLines = 1, overflow = TextOverflow.Ellipsis)
        if (compact) {
            Text("${skin.sizeMb} MB", color = RetroTextMuted, style = MaterialTheme.typography.labelSmall)
            CompatibilityBadge(skin.compatibility)
        } else {
            Creator(skin.creator)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("${skin.sizeMb} MB", color = RetroTextMuted, style = MaterialTheme.typography.labelSmall, modifier = Modifier.weight(1f))
                CompatibilityBadge(skin.compatibility)
            }
        }
    }
}
@Composable fun FeaturedSkinCard(skin: CommunitySkin, onGet: () -> Unit, onClick: () -> Unit, onFav: (() -> Unit)? = null, onDelete: (() -> Unit)? = null) {
    RetroCard(Modifier.clickable(onClick = onClick)) {
        Box {
            SkinCover(Modifier.fillMaxWidth().aspectRatio(1.7f), imageUrl = skin.coverUrl, imageUrls = skin.screenshotUrls)
            Row(Modifier.align(Alignment.TopStart).padding(8.dp), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                BadgePill("Featured", Icons.Filled.Star, RetroGold)
                if (skin.isOwner) BadgePill("Mine", Icons.Filled.Person, RetroAccent)
            }
            if (skin.isOwner && onDelete != null) {
                Box(Modifier.align(Alignment.TopEnd).padding(8.dp).size(36.dp).background(RetroDanger.copy(.8f), CircleShape).clickable(onClick = onDelete), Alignment.Center) {
                    Icon(Icons.Filled.Delete, null, tint = Color.White, modifier = Modifier.size(18.dp))
                }
            }
            Column(Modifier.align(Alignment.BottomStart).fillMaxWidth().background(RetroSurfaceAlt.copy(.94f)).padding(10.dp)) {
                CardTitle(skin.title, "by ${skin.creator}")
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (skin.verified) { BadgePill("Verified", Icons.Filled.Verified, RetroGold); Spacer(Modifier.width(8.dp)) }
                    SkinAvatar(avatarUrl = skin.creatorAvatarUrl, name = skin.creator, size = 18.dp)
                }
            }
        }
        Spacer(Modifier.height(9.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.weight(1f)) { Metrics(skin.likes, skin.downloads, skin.likedByMe, onFav) }
            SecondaryButton(text = "Get", modifier = Modifier.width(84.dp), icon = Icons.Filled.Download, onClick = onGet)
        }
    }
}
@Composable fun ActiveSkinCard(skin: CarSkin, onRevert: () -> Unit) { RetroCard { Row(verticalAlignment = Alignment.CenterVertically) { SkinCover(Modifier.size(76.dp)); Spacer(Modifier.width(10.dp)); Column(Modifier.weight(1f)) { CardTitle(skin.title, "${skin.vehicle} · ${skin.slot.label}"); StatusChip(SkinStatus.ACTIVE) }; SecondaryButton(text = "Revert", modifier = Modifier.width(90.dp), onClick = onRevert) } } }
@Composable private fun CardTitle(title: String, subtitle: String) { Text(title, color = RetroText, style = MaterialTheme.typography.titleMedium, maxLines = 2, overflow = TextOverflow.Ellipsis); Text(subtitle, color = RetroTextMuted, style = MaterialTheme.typography.bodySmall, maxLines = 2, overflow = TextOverflow.Ellipsis) }
@Composable private fun Creator(name: String, avatarUrl: String? = null) { Row(verticalAlignment = Alignment.CenterVertically) { SkinAvatar(avatarUrl = avatarUrl, name = name); Spacer(Modifier.width(6.dp)); Text(name, color = RetroTextDim, style = MaterialTheme.typography.bodySmall, maxLines = 1, overflow = TextOverflow.Ellipsis) } }
@Composable private fun Metrics(likes: Int, downloads: Int, likedByMe: Boolean = false, onFav: (() -> Unit)? = null) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            if (likedByMe) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
            null,
            tint = if (likedByMe) RetroDanger else RetroTextDim,
            modifier = Modifier.size(14.dp).let { if (onFav != null) it.clickable(onClick = onFav) else it }
        )
        Text(" $likes", color = RetroTextDim, style = MaterialTheme.typography.labelSmall)
        Spacer(Modifier.width(10.dp))
        Icon(Icons.Filled.Download, null, tint = RetroAccent, modifier = Modifier.size(14.dp))
        Text(" $downloads", color = RetroTextDim, style = MaterialTheme.typography.labelSmall)
    }
}
@Composable fun SkinFilterRow(options: List<String>, selectedIndex: Int, onSelect: (Int) -> Unit) { Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) { options.forEachIndexed { i, text -> RetroChip(text = text, selected = i == selectedIndex, onClick = { onSelect(i) }) } } }
@Composable fun WizardStepIndicator(step: Int, total: Int = 4, label: String) { Column { Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) { repeat(total) { Box(Modifier.weight(1f).height(5.dp).background(if (it < step) RetroAccent else RetroSurfaceAlt, RoundedCornerShape(3.dp))) } }; Spacer(Modifier.height(7.dp)); Text("Step $step of $total · $label", color = RetroTextDim, style = MaterialTheme.typography.labelSmall) } }
@Composable fun SkinSlotSelector(selected: SkinSlot, onSelect: (SkinSlot) -> Unit) { SkinFilterRow(SkinSlot.entries.map { it.label }, SkinSlot.entries.indexOf(selected)) { onSelect(SkinSlot.entries[it]) } }
@Composable fun GamePackageSelector(selected: GamePackage, onSelect: (GamePackage) -> Unit) { SegmentedTabs(options = GamePackage.entries.map { it.short }, selectedIndex = GamePackage.entries.indexOf(selected), onSelect = { onSelect(GamePackage.entries[it]) }) }
@Composable fun VehicleSelector(selected: String, onSelect: (String) -> Unit) { var expanded = false; RetroCard(Modifier.clickable { expanded = !expanded }) { Row(verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Filled.DirectionsCar, null, tint = RetroAccent); Spacer(Modifier.width(8.dp)); Text(selected, color = RetroText, modifier = Modifier.weight(1f)); Icon(Icons.Filled.ExpandMore, "Choose vehicle", tint = RetroTextMuted) }; if (expanded) listOf("Audi R18 e-tron", "Porsche 911 RSR", "Ferrari 488 GTE").forEach { Text(it, color = RetroTextSoft, modifier = Modifier.fillMaxWidth().clickable { onSelect(it); expanded = false }.padding(12.dp)) } } }
@Composable fun ScreenshotPicker(images: List<ProcessedSkinImage>, max: Int = 5, onAdd: () -> Unit, onRemove: (ProcessedSkinImage) -> Unit, onSetCover: (ProcessedSkinImage) -> Unit) {
    Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        images.forEach { image ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(Modifier.size(96.dp).clip(RoundedCornerShape(10.dp)).background(RetroCover)) {
                    SubcomposeAsyncImage(model = image.file, contentDescription = if (image.role == SkinImageRole.COVER) "Selected cover image" else "Selected screenshot", contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
                    if (image.role == SkinImageRole.COVER) Text("Cover", color = RetroGold, style = MaterialTheme.typography.labelSmall, modifier = Modifier.align(Alignment.BottomStart).background(RetroSurfaceAlt.copy(.85f)).padding(4.dp))
                    IconButton(onClick = { onRemove(image) }, modifier = Modifier.align(Alignment.TopEnd).size(48.dp)) { Icon(Icons.Filled.Close, "Remove image", tint = Color.White) }
                }
                Text("${image.sizeBytes / 1024} KiB", color = RetroTextMuted, style = MaterialTheme.typography.labelSmall)
                if (image.role != SkinImageRole.COVER) TextButton(onClick = { onSetCover(image) }) { Text("Set cover") }
            }
        }
        if (images.size < max) Box(Modifier.size(96.dp).border(1.dp, RetroTextMuted, RoundedCornerShape(10.dp)).clickable(onClick = onAdd), Alignment.Center) { Icon(Icons.Filled.Add, "Add images", tint = RetroTextMuted) }
    }
}
@Composable fun ScreenshotCarousel(urls: List<String>, modifier: Modifier = Modifier, aspectRatio: Float = 16f / 9f) {
    var selectedIndex by remember(urls) { mutableIntStateOf(0) }
    LaunchedEffect(urls) {
        Log.i(
            "SCREENSHOT_CAROUSEL_STATE",
            "count=${urls.size} selectedIndex=$selectedIndex sources=${urls.joinToString(limit = 4)}",
        )
        if (selectedIndex !in urls.indices && urls.isNotEmpty()) {
            Log.w("SCREENSHOT_CAROUSEL_INDEX_RESET", "count=${urls.size} invalidIndex=$selectedIndex")
            selectedIndex = 0
        }
        if (urls.size < 2) {
            Log.w("SCREENSHOT_CAROUSEL_NAV_UNAVAILABLE", "count=${urls.size} reason=requires_at_least_two_images")
        }
    }
    val selectedUrl = urls.getOrNull(selectedIndex)
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        SkinCover(Modifier.fillMaxWidth().aspectRatio(aspectRatio), imageUrl = selectedUrl)
        when {
            urls.size > 1 -> {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(
                        onClick = {
                            val previous = selectedIndex
                            selectedIndex = (selectedIndex - 1 + urls.size) % urls.size
                            Log.i("SCREENSHOT_CAROUSEL_NAVIGATED", "direction=previous count=${urls.size} from=$previous to=$selectedIndex url=${urls[selectedIndex]}")
                        },
                        modifier = Modifier.size(48.dp),
                    ) { Icon(Icons.Filled.ChevronLeft, "Previous screenshot", tint = RetroTextSoft) }
                    Text("${selectedIndex + 1} / ${urls.size}", color = RetroTextMuted, style = MaterialTheme.typography.labelSmall)
                    IconButton(
                        onClick = {
                            val previous = selectedIndex
                            selectedIndex = (selectedIndex + 1) % urls.size
                            Log.i("SCREENSHOT_CAROUSEL_NAVIGATED", "direction=next count=${urls.size} from=$previous to=$selectedIndex url=${urls[selectedIndex]}")
                        },
                        modifier = Modifier.size(48.dp),
                    ) { Icon(Icons.Filled.ChevronRight, "Next screenshot", tint = RetroTextSoft) }
                }
            }
            urls.isEmpty() -> {
                Log.w("SCREENSHOT_CAROUSEL_EMPTY", "reason=no_image_urls_supplied")
                Spacer(Modifier.height(6.dp))
                Text("No screenshots", color = RetroTextMuted, style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}
@Composable fun SafePathNotice() { Notice(Icons.Filled.Lock, "The app picks the game path; custom paths aren't allowed.", RetroAccent) }
@Composable private fun Notice(icon: ImageVector, text: String, color: Color) { Row(Modifier.fillMaxWidth().background(color.copy(.1f), RoundedCornerShape(12.dp)).padding(12.dp), verticalAlignment = Alignment.CenterVertically) { Icon(icon, null, tint = color); Spacer(Modifier.width(9.dp)); Text(text, color = RetroTextSoft, style = MaterialTheme.typography.bodySmall, modifier = Modifier.weight(1f)) } }
@Composable fun OriginalBackupCard(hasBackup: Boolean, hash: String, dateLabel: String) { RetroCard { Text(if (hasBackup) "Original backup available" else "No original backup", color = if (hasBackup) RetroSuccess else RetroGold, style = MaterialTheme.typography.titleSmall); Text(if (hasBackup) "$dateLabel · $hash" else "Revert needs a backup created before applying a skin.", color = RetroTextMuted, style = MaterialTheme.typography.bodySmall) } }
@Composable fun UploadOptionsCard(apply: Boolean, upload: Boolean, onApply: (Boolean) -> Unit, onUpload: (Boolean) -> Unit) { RetroCard { ToggleRow("Save to library", true, {}, false); ToggleRow("Apply after import", apply, onApply); ToggleRow("Upload to community", upload, onUpload) } }
@Composable private fun ToggleRow(text: String, checked: Boolean, onChange: (Boolean) -> Unit, enabled: Boolean = true) { Row(Modifier.fillMaxWidth().defaultMinSize(minHeight = 48.dp), verticalAlignment = Alignment.CenterVertically) { Text(text, color = RetroText, modifier = Modifier.weight(1f)); Switch(checked = checked, onCheckedChange = onChange, enabled = enabled) } }
@Composable fun CommunityGuidelinesCard(checked: Boolean, onCheck: (Boolean) -> Unit) { RetroCard { Row(verticalAlignment = Alignment.CenterVertically) { Checkbox(checked = checked, onCheckedChange = onCheck); Text("I own this work and accept the community guidelines.", color = RetroTextSoft, style = MaterialTheme.typography.bodySmall, modifier = Modifier.weight(1f)) } } }
@Composable fun EmptyState(icon: ImageVector, title: String, body: String, ctaText: String? = null, onCta: (() -> Unit)? = null) { Column(Modifier.fillMaxWidth().padding(28.dp), horizontalAlignment = Alignment.CenterHorizontally) { Icon(icon, null, tint = RetroTextMuted, modifier = Modifier.size(42.dp)); Text(title, color = RetroText, style = MaterialTheme.typography.titleMedium); Text(body, color = RetroTextMuted, style = MaterialTheme.typography.bodySmall); if (ctaText != null && onCta != null) { Spacer(Modifier.height(12.dp)); PrimaryButton(text = ctaText, onClick = onCta) } } }
@Composable fun ErrorState(onRetry: () -> Unit) { EmptyState(Icons.Filled.ErrorOutline, "Couldn't load skins", "Check your connection and try again.", "Retry", onRetry) }
@Composable fun LoadingSkinCard() { RetroCard { Box(Modifier.fillMaxWidth().aspectRatio(1.35f).background(RetroCover, RoundedCornerShape(12.dp))); Spacer(Modifier.height(10.dp)); Box(Modifier.fillMaxWidth(.7f).height(16.dp).background(RetroSurfaceAlt)); Spacer(Modifier.height(7.dp)); Box(Modifier.fillMaxWidth(.45f).height(12.dp).background(RetroSurfaceAlt)) } }
@Composable fun DestructiveConfirmationDialog(title: String, body: String, confirmText: String, onConfirm: () -> Unit, onDismiss: () -> Unit) { AlertDialog(onDismissRequest = onDismiss, title = { Text(title) }, text = { Text(body) }, confirmButton = { TextButton(onClick = onConfirm) { Text(confirmText, color = RetroDanger) } }, dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } }) }
@OptIn(ExperimentalMaterial3Api::class) @Composable fun DownloadApplySheet(skin: CommunitySkin, onCancel: () -> Unit, onConfirm: () -> Unit) { ModalBottomSheet(onDismissRequest = onCancel, containerColor = RetroSurface) { Column(Modifier.padding(18.dp).navigationBarsPadding(), verticalArrangement = Arrangement.spacedBy(12.dp)) { Text("Download & apply", color = RetroText, style = MaterialTheme.typography.titleLarge); Text("${skin.vehicle} - ${skin.slotLabel} - ${String.format(
    Locale.US, "%.2f MiB", skin.sizeMb)}", color = RetroTextMuted); Notice(Icons.Filled.FolderOpen, "Downloaded to your library first", RetroAccent); Notice(Icons.Filled.Shield, "Original graphics will be backed up", RetroSuccess); Notice(Icons.Filled.Power, "Applying later requires Shizuku", RetroGold); Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { SecondaryButton(text = "Cancel", modifier = Modifier.weight(1f), onClick = onCancel); PrimaryButton(text = "Download & apply", modifier = Modifier.weight(1f), onClick = onConfirm) } } } }
