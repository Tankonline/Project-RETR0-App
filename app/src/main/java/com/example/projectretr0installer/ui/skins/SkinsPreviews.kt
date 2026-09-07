package com.example.projectretr0installer.ui.skins

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.projectretr0installer.ui.theme.*

@Composable private fun PreviewFrame(content: @Composable () -> Unit) { ProjectRETR0InstallerTheme { Box(Modifier.fillMaxSize().background(RetroBg).padding(12.dp)) { content() } } }
private val previewRoot = SkinTargetValidator.ROW_ROOT
private val previewFile = "2014_audi_r18_livery_etc(1).dds.z"
private val previewBrowser = SkinTargetBrowserSource { _, path -> BrowserResult.Success(listOf(SkinBrowserEntry("files", "$path/files", true, false, false, 0))) }
@Preview(showBackground = true, widthDp = 412, heightDp = 1000) @Composable fun GalleryPreview() = PreviewFrame { CommunityGalleryContent({}, {}, {}, previewState = GalleryState.CONTENT, previewItems = FakeSkins.community) }
@Preview(showBackground = true, widthDp = 340, heightDp = 1000) @Composable fun NarrowGalleryPreview() = PreviewFrame { CommunityGalleryContent({}, {}, {}, previewState = GalleryState.CONTENT, previewItems = FakeSkins.community) }
@Preview(showBackground = true, widthDp = 412, heightDp = 1000) @Composable fun LibraryPreview() = PreviewFrame { SkinLibraryScreen({}, {}, {}) }
@Preview(showBackground = true, widthDp = 412, heightDp = 1000) @Composable fun ImportWizardPreview() = PreviewFrame { SkinImportWizard({}, {}, 2, browserSource = previewBrowser) }
@Preview(showBackground = true, widthDp = 412, heightDp = 900) @Composable fun TargetBrowserPreview() = PreviewFrame { SkinTargetBrowserScreen(previewRoot, previewFile, previewBrowser, {}, {}, previewEntries = listOf(SkinBrowserEntry("another_car", "$previewRoot/files/.depot/vehicles/another_car", true, false, false, 0), SkinBrowserEntry(previewFile, "$previewRoot/files/.depot/vehicles/audi/livery/$previewFile", false, true, false, 4000), SkinBrowserEntry("wrong.dds.z", "$previewRoot/files/.depot/vehicles/audi/livery/wrong.dds.z", false, true, false, 4000))) }
@Preview(showBackground = true, widthDp = 412, heightDp = 1000) @Composable fun CommunityDetailsPreview() = PreviewFrame { CommunitySkinDetails("c1", {}, {}, previewSkin = FakeSkins.community.first(), previewState = DetailsState.CONTENT) }
@Preview(showBackground = true, widthDp = 412, heightDp = 1000) @Composable fun LocalDetailsPreview() = PreviewFrame { LocalSkinDetails("l1", {}, {}, {}) }
@Preview(showBackground = true, widthDp = 412, heightDp = 800) @Composable fun ProgressPreview() = PreviewFrame { SkinProgressScreen(OpKind.APPLY, {}, {}) }
@Preview(showBackground = true, widthDp = 412, heightDp = 800) @Composable fun FailedProgressPreview() = PreviewFrame { SkinProgressScreen(OpKind.REVERT, {}, {}, true) }
@Preview(showBackground = true, widthDp = 250, heightDp = 320) @Composable fun GalleryCardPreview() = PreviewFrame { GalleryCard(FakeSkins.community.first(), {}) }
@Preview(showBackground = true, widthDp = 250, heightDp = 340) @Composable fun LibraryCardPreview() = PreviewFrame { LibrarySkinCard(FakeSkins.library.first(), {}, {}) }
