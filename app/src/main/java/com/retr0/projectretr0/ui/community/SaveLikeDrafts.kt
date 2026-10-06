package com.retr0.projectretr0.ui.community

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.retr0.projectretr0.installer.CommunitySave
import com.retr0.projectretr0.installer.StorageUtils
import com.retr0.projectretr0.ui.components.RetroCard
import com.retr0.projectretr0.ui.theme.ProjectRETR0InstallerTheme
import com.retr0.projectretr0.ui.theme.RetroAccent
import com.retr0.projectretr0.ui.theme.RetroAccentTint
import com.retr0.projectretr0.ui.theme.RetroBg
import com.retr0.projectretr0.ui.theme.RetroDanger
import com.retr0.projectretr0.ui.theme.RetroText
import com.retr0.projectretr0.ui.theme.RetroTextDim
import com.retr0.projectretr0.ui.theme.RetroTextMuted

private fun formatLikeCount(value: Int): String = when {
    value >= 1000 -> "%.1fk".format(value / 1000f).removeSuffix(".0k")
    else -> value.toString()
}

private fun likeDescription(save: CommunitySave): String =
    "${if (save.likedByMe) "Liked" else "Like"}, ${save.likes} likes"

@Composable
private fun DraftHeader(save: CommunitySave, onDelete: (() -> Unit)?) {
    Row(verticalAlignment = Alignment.Top, modifier = Modifier.fillMaxWidth()) {
        Box(Modifier.size(38.dp).background(RetroAccentTint, RoundedCornerShape(11.dp)), Alignment.Center) {
            Icon(Icons.Filled.Person, contentDescription = null, tint = RetroAccent)
        }
        Spacer(Modifier.width(11.dp))
        Column(Modifier.weight(1f)) {
            Text(save.customName, color = RetroText, style = MaterialTheme.typography.titleMedium, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text("by ${save.uploaderName} · ${StorageUtils.formatSize(save.fileSize)}", color = RetroTextMuted, style = MaterialTheme.typography.labelSmall)
        }
        if (save.isOwner && onDelete != null) {
            IconButton(onClick = onDelete) { Icon(Icons.Filled.Delete, contentDescription = "Delete save", tint = RetroDanger) }
        }
    }
}

@Composable
private fun DraftFooter(save: CommunitySave, likeControl: @Composable () -> Unit, onGet: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        Text("${save.downloads} downloads", color = RetroTextDim, style = MaterialTheme.typography.labelSmall, modifier = Modifier.weight(1f))
        likeControl()
        Spacer(Modifier.width(8.dp))
        Box(Modifier.background(RetroAccent, RoundedCornerShape(20.dp)).clickable(onClick = onGet).padding(horizontal = 22.dp, vertical = 9.dp)) {
            Text("Get", color = Color.White, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun SaveLikeCompactDraft(save: CommunitySave, onGet: () -> Unit, onFav: () -> Unit, onDelete: (() -> Unit)? = null) {
    RetroCard {
        DraftHeader(save, onDelete)
        Spacer(Modifier.height(14.dp))
        DraftFooter(save, {
            Row(Modifier.size(48.dp).clickable(onClick = onFav).semantics { contentDescription = likeDescription(save) }, verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                Icon(if (save.likedByMe) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder, contentDescription = null, tint = if (save.likedByMe) RetroDanger else RetroTextMuted, modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(4.dp))
                Text(formatLikeCount(save.likes), color = RetroTextDim, style = MaterialTheme.typography.labelSmall)
            }
        }, onGet)
    }
}

@Composable
fun SaveLikeLabeledDraft(save: CommunitySave, onGet: () -> Unit, onFav: () -> Unit, onDelete: (() -> Unit)? = null) {
    RetroCard {
        DraftHeader(save, onDelete)
        Spacer(Modifier.height(14.dp))
        DraftFooter(save, {
            Row(Modifier.background(if (save.likedByMe) RetroDanger.copy(alpha = .16f) else RetroAccentTint, RoundedCornerShape(20.dp)).clickable(onClick = onFav).padding(horizontal = 12.dp, vertical = 9.dp).semantics { contentDescription = likeDescription(save) }, verticalAlignment = Alignment.CenterVertically) {
                Icon(if (save.likedByMe) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder, contentDescription = null, tint = if (save.likedByMe) RetroDanger else RetroTextDim, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(6.dp))
                Text(if (save.likedByMe) "Liked" else "Like", color = RetroText, style = MaterialTheme.typography.labelSmall)
                Spacer(Modifier.width(5.dp))
                Text(formatLikeCount(save.likes), color = RetroTextDim, style = MaterialTheme.typography.labelSmall)
            }
        }, onGet)
    }
}

@Composable
fun SaveLikeExpandedDraft(save: CommunitySave, onGet: () -> Unit, onFav: () -> Unit, onDelete: (() -> Unit)? = null) {
    RetroCard {
        DraftHeader(save, onDelete)
        Spacer(Modifier.height(14.dp))
        DraftFooter(save, {
            Row(Modifier.size(48.dp).clickable(onClick = onFav).semantics { contentDescription = likeDescription(save) }, verticalAlignment = Alignment.CenterVertically) {
                Icon(if (save.likedByMe) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder, contentDescription = null, tint = if (save.likedByMe) RetroDanger else RetroTextMuted, modifier = Modifier.size(19.dp))
                Spacer(Modifier.width(5.dp))
                Text(if (save.likedByMe) "Liked" else "Like", color = if (save.likedByMe) RetroDanger else RetroTextDim, style = MaterialTheme.typography.labelSmall)
                Spacer(Modifier.width(4.dp))
                Text(formatLikeCount(save.likes), color = RetroTextDim, style = MaterialTheme.typography.labelSmall)
            }
        }, onGet)
    }
}

private fun sampleCommunitySave(liked: Boolean = false, likes: Int = 12, owner: Boolean = false, name: String = "Midnight career save") = CommunitySave("preview-save", name, if (owner) "You" else "RetroPilot", 4_580_000, "2026-09-19", playerLevel = 42, likes = likes, downloads = 238, isOwner = owner, likedByMe = liked)

private val noOp: () -> Unit = {}

@Preview(name = "Compact - unliked", showBackground = true, widthDp = 412)
@Composable fun SaveLikeCompactDraftPreview() = ProjectRETR0InstallerTheme { Box(Modifier.background(RetroBg).padding(16.dp)) { SaveLikeCompactDraft(sampleCommunitySave(), noOp, noOp) } }

@Preview(name = "Labeled - liked", showBackground = true, widthDp = 412)
@Composable fun SaveLikeLabeledDraftPreview() = ProjectRETR0InstallerTheme { Box(Modifier.background(RetroBg).padding(16.dp)) { SaveLikeLabeledDraft(sampleCommunitySave(true, 13), noOp, noOp) } }

@Preview(name = "Expanded - owner", showBackground = true, widthDp = 412)
@Composable fun SaveLikeExpandedDraftPreview() = ProjectRETR0InstallerTheme { Box(Modifier.background(RetroBg).padding(16.dp)) { SaveLikeExpandedDraft(sampleCommunitySave(true, 12345, true, "A very long save name that truncates safely"), noOp, noOp, noOp) } }

@Preview(name = "All like drafts", showBackground = true, widthDp = 412, heightDp = 900)
@Composable fun SaveLikeDraftsCombinedPreview() = ProjectRETR0InstallerTheme { Column(Modifier.background(RetroBg).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
    SaveLikeCompactDraft(sampleCommunitySave(false, 0), noOp, noOp)
    SaveLikeLabeledDraft(sampleCommunitySave(true, 13), noOp, noOp)
    SaveLikeExpandedDraft(sampleCommunitySave(false, 12345, true, "A very long save name that truncates safely"), noOp, noOp, noOp)
} }
