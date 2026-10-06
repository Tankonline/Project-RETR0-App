package com.retr0.projectretr0.ui.skins

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.retr0.projectretr0.ui.theme.*

private val oddPreviewSkins = FakeSkins.library.take(3)
private val detailsPreviewSkin = FakeSkins.community.first()
private val detailsLongMetadataSkin = detailsPreviewSkin.copy(
    sourceFilename = "audi_r18_e_tron_quattro_2015_endurance_championship_final_release_texture.dds.z",
    binarySha256 = "0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef",
)

@Composable private fun PreviewFrame(content: @Composable () -> Unit) { ProjectRETR0InstallerTheme { Box(Modifier.fillMaxSize().background(RetroBg).padding(12.dp)) { content() } } }
@Composable private fun DestinationPreviewFrame(content: @Composable () -> Unit) { ProjectRETR0InstallerTheme { Box(Modifier.fillMaxSize().background(RetroBg)) { content() } } }
private val previewRoot = SkinTargetValidator.ROW_ROOT
private val previewFile = FakeSkinMods.AUDI_TARGET
private val valid = SkinBrowserEntry(previewFile, "$previewRoot/files/.depot/vehicles/audi/livery/$previewFile", false, true, false, 4000)
private val previewVehicle = SkinBrowserEntry("audi", "$previewRoot/files/.depot/vehicles/audi", true, false, false, 3452)
private val mismatch = valid.copy(name = "wrong.dds.z", path = "$previewRoot/files/.depot/vehicles/audi/livery/wrong.dds.z")
private val manyEntries = listOf(previewVehicle, SkinBrowserEntry("bmw", "$previewRoot/files/.depot/vehicles/bmw", true, false, false, 8000), SkinBrowserEntry("ferrari", "$previewRoot/files/.depot/vehicles/ferrari", true, false, false, 100), SkinBrowserEntry("ford", "$previewRoot/files/.depot/vehicles/ford", true, false, false, 42), SkinBrowserEntry("lotus", "$previewRoot/files/.depot/vehicles/lotus", true, false, false, 2048), SkinBrowserEntry("porsche", "$previewRoot/files/.depot/vehicles/porsche", true, false, false, 900), valid, mismatch)
private val smartPreviewBrowser = SkinTargetBrowserSource { _, path -> when(path) { "$previewRoot/files/.depot/vehicles" -> BrowserResult.Success(listOf(previewVehicle)); "$previewRoot/files/.depot/vehicles/audi" -> BrowserResult.Success(listOf(SkinBrowserEntry("livery", "$previewRoot/files/.depot/vehicles/audi/livery", true, false, false, 0))); "$previewRoot/files/.depot/vehicles/audi/livery" -> BrowserResult.Success(listOf(valid)); else -> BrowserResult.Success(emptyList()) } }
private val previewBrowser = SkinTargetBrowserSource { _, path -> BrowserResult.Success(listOf(SkinBrowserEntry("files", "$path/files", true, false, false, 0))) }
@Preview(widthDp=412,heightDp=900) @Composable fun SkinModsOverviewPreview()=PreviewFrame{ SkinModsScreen(true,storageText="18 GB available",service=null,onBrowseCommunity={}) }
@Preview(name="My Skins - standard phone",widthDp=412,heightDp=900) @Composable fun SkinModsLibraryPreview()=PreviewFrame{ SkinModsScreen(true,storageText="18 GB",service=null,onBrowseCommunity={},initialSection=SkinModsSection.LIBRARY) }
@Preview(name="My Skins - narrow phone",widthDp=340,heightDp=760) @Composable fun SkinModsLibraryNarrowPreview()=PreviewFrame{ SkinModsScreen(true,storageText="18 GB",service=null,onBrowseCommunity={},initialSection=SkinModsSection.LIBRARY) }
@Preview(name="My Skins - tablet rail",widthDp=900,heightDp=700) @Composable fun SkinModsLibraryTabletPreview()=PreviewFrame{ SkinModsScreen(true,storageText="18 GB",service=null,onBrowseCommunity={},initialSection=SkinModsSection.LIBRARY) }
@Preview(name="My Skins - large font",widthDp=412,heightDp=900,fontScale=1.5f) @Composable fun SkinModsLibraryLargeFontPreview()=PreviewFrame{ SkinModsScreen(true,storageText="18 GB",service=null,onBrowseCommunity={},initialSection=SkinModsSection.LIBRARY) }
@Preview(name="Library - empty search",widthDp=412,heightDp=900) @Composable fun SkinLibraryEmptySearchPreview()=PreviewFrame{ SkinLibraryScreen({},{},{},embedded=true,previewQuery="No matching skin") }
@Preview(name="Library - odd final card",widthDp=412,heightDp=900) @Composable fun SkinLibraryOddRowPreview()=PreviewFrame{ SkinLibraryScreen({},{},{},embedded=true,previewSkins=oddPreviewSkins) }
@Preview(name="Compact library card",widthDp=196,heightDp=310) @Composable fun CompactLibrarySkinCardPreview()=PreviewFrame{ LibrarySkinCard(FakeSkins.library.first(),{},{},Modifier.fillMaxWidth(),compact=true) }
@Preview(name="Applied - sparse one override",widthDp=412,heightDp=900) @Composable fun SkinModsAppliedPreview()=DestinationPreviewFrame{ SkinModsScreen(true,storageText="18 GB",service=null,onBrowseCommunity={},initialSection=SkinModsSection.APPLIED) }
@Preview(name="Applied - multiple visible cards",widthDp=412,heightDp=520) @Composable fun SkinModsAppliedMultiplePreview()=DestinationPreviewFrame{ SkinModsScreen(true,storageText="18 GB",service=null,onBrowseCommunity={},initialSection=SkinModsSection.APPLIED) }
@Preview(name="Applied - empty",widthDp=412,heightDp=900) @Composable fun SkinModsAppliedEmptyPreview()=DestinationPreviewFrame{ SkinModsScreen(true,storageText="18 GB",service=null,onBrowseCommunity={},initialSection=SkinModsSection.APPLIED,previewState=SkinModsPreviewState.EMPTY) }
@Preview(name="Backups - sparse top packed",widthDp=412,heightDp=900) @Composable fun SkinModsBackupsPreview()=DestinationPreviewFrame{ SkinModsScreen(true,storageText="18 GB",service=null,onBrowseCommunity={},initialSection=SkinModsSection.BACKUPS) }
@Preview(name="Activity - sparse top packed",widthDp=412,heightDp=900) @Composable fun SkinModsActivityPreview()=DestinationPreviewFrame{ SkinModsScreen(true,storageText="18 GB",service=null,onBrowseCommunity={},initialSection=SkinModsSection.ACTIVITY) }
@Preview(name="Navigation - narrow selected middle",widthDp=320,heightDp=700) @Composable fun SkinModsNavigationNarrowPreview()=DestinationPreviewFrame{ SkinModsScreen(true,storageText="18 GB",service=null,onBrowseCommunity={},initialSection=SkinModsSection.APPLIED) }
@Preview(name="Navigation - first selected",widthDp=340,heightDp=700) @Composable fun SkinModsFirstSectionPreview()=DestinationPreviewFrame{ SkinModsScreen(true,storageText="18 GB",service=null,onBrowseCommunity={},initialSection=SkinModsSection.OVERVIEW) }
@Preview(name="Navigation - last selected",widthDp=340,heightDp=700) @Composable fun SkinModsLastSectionPreview()=DestinationPreviewFrame{ SkinModsScreen(true,storageText="18 GB",service=null,onBrowseCommunity={},initialSection=SkinModsSection.ACTIVITY) }
@Preview(name="Applied - large font responsive header",widthDp=412,heightDp=900,fontScale=1.5f) @Composable fun SkinModsAppliedLargeFontPreview()=DestinationPreviewFrame{ SkinModsScreen(true,storageText="18 GB",service=null,onBrowseCommunity={},initialSection=SkinModsSection.APPLIED) }
@Preview(widthDp=900,heightDp=700) @Composable fun SkinModsTabletPreview()=PreviewFrame{ SkinModsScreen(true,storageText="18 GB",service=null,onBrowseCommunity={}) }
@Preview(widthDp=412,heightDp=900) @Composable fun SkinModsLoadingPreview()=PreviewFrame{ SkinModsScreen(false,storageText="Checking…",service=null,onBrowseCommunity={},previewState=SkinModsPreviewState.LOADING) }
@Preview(widthDp=412,heightDp=900) @Composable fun SkinModsEmptyPreview()=DestinationPreviewFrame{ SkinModsScreen(true,storageText="18 GB",service=null,onBrowseCommunity={},initialSection=SkinModsSection.APPLIED,previewState=SkinModsPreviewState.EMPTY) }
@Preview(widthDp=412,heightDp=900) @Composable fun SkinModsPartialPreview()=PreviewFrame{ SkinModsScreen(true,storageText="18 GB",service=null,onBrowseCommunity={},previewState=SkinModsPreviewState.PARTIAL_FAILURE) }
@Preview(widthDp=412,heightDp=900) @Composable fun TargetBrowserStandardPreview()=PreviewFrame{ SkinTargetBrowserScreen(previewRoot,previewFile,previewBrowser,{},{},previewEntries=manyEntries,previewMode=1) }
@Preview(widthDp=740,heightDp=420) @Composable fun TargetBrowserCompactHeightPreview()=PreviewFrame{ SkinTargetBrowserScreen(previewRoot,previewFile,previewBrowser,{},{},previewEntries=manyEntries,previewMode=1) }
@Preview(widthDp=412,heightDp=900,fontScale=1.5f) @Composable fun TargetBrowserLargeFontPreview()=PreviewFrame{ SkinTargetBrowserScreen(previewRoot,previewFile,previewBrowser,{},{},previewEntries=manyEntries,previewMode=1) }
@Preview(widthDp=412,heightDp=900) @Composable fun SmartMatchValidPreview()=PreviewFrame{ SkinTargetBrowserScreen(previewRoot,previewFile,smartPreviewBrowser,{},{},previewEntries=listOf(valid),previewSelectedEntry=valid) }
@Preview(widthDp=412,heightDp=900) @Composable fun TargetMismatchPreview()=PreviewFrame{ SkinTargetBrowserScreen(previewRoot,previewFile,previewBrowser,{},{},previewEntries=listOf(mismatch),previewSelectedEntry=mismatch,previewMode=1) }
@Preview(widthDp=412,heightDp=900) @Composable fun TargetNothingSelectedPreview()=PreviewFrame{ SkinTargetBrowserScreen(previewRoot,previewFile,previewBrowser,{},{},previewEntries=listOf(valid),previewMode=1) }
@Preview(widthDp=412,heightDp=900) @Composable fun TargetWrongFolderPreview()=PreviewFrame{ SkinTargetBrowserScreen(previewRoot,previewFile,previewBrowser,{},{},previewEntries=listOf(valid.copy(path="$previewRoot/files/$previewFile"))) }
@Preview(widthDp=412,heightDp=900) @Composable fun TargetEmptyPreview()=PreviewFrame{ SkinTargetBrowserScreen(previewRoot,previewFile,previewBrowser,{},{},previewEntries=emptyList(),previewMode=1) }
@Preview(widthDp=412,heightDp=900) @Composable fun TargetErrorPreview()=PreviewFrame{ SkinTargetBrowserScreen(previewRoot,previewFile,previewBrowser,{},{},previewMode=1,previewError="Privileged service could not read this folder.") }
@Preview(widthDp=412,heightDp=900) @Composable fun TargetSourceErrorPreview()=PreviewFrame{ SkinTargetBrowserScreen(previewRoot,"bad.png",previewBrowser,{},{},previewEntries=listOf(valid)) }
@Preview(widthDp=412,heightDp=900) @Composable fun GalleryPreview()=PreviewFrame{ CommunityGalleryContent({},{},{},previewState=GalleryState.CONTENT) }
@Preview(name="Details - focused full screen, one image",widthDp=412,heightDp=900) @Composable fun CommunityDetailsPreview()=DestinationPreviewFrame{ CommunitySkinDetails(detailsPreviewSkin.id,{}, {},previewSkin=detailsPreviewSkin,previewState=DetailsState.CONTENT) }
@Preview(name="Details - multiple image pagination",widthDp=412,heightDp=900) @Composable fun CommunityDetailsCarouselPreview()=DestinationPreviewFrame{ CommunitySkinDetails(detailsPreviewSkin.id,{}, {},previewSkin=detailsPreviewSkin.copy(screenshotUrls=listOf("one","two","three")),previewState=DetailsState.CONTENT) }
@Preview(name="Details - narrow phone",widthDp=320,heightDp=700) @Composable fun CommunityDetailsNarrowPreview()=DestinationPreviewFrame{ CommunitySkinDetails(detailsPreviewSkin.id,{}, {},previewSkin=detailsLongMetadataSkin,previewState=DetailsState.CONTENT) }
@Preview(name="Details - compact landscape",widthDp=740,heightDp=420) @Composable fun CommunityDetailsLandscapePreview()=DestinationPreviewFrame{ CommunitySkinDetails(detailsPreviewSkin.id,{}, {},previewSkin=detailsPreviewSkin,previewState=DetailsState.CONTENT) }
@Preview(name="Details - large font",widthDp=412,heightDp=900,fontScale=1.5f) @Composable fun CommunityDetailsLargeFontPreview()=DestinationPreviewFrame{ CommunitySkinDetails(detailsPreviewSkin.id,{}, {},previewSkin=detailsPreviewSkin,previewState=DetailsState.CONTENT) }
@Preview(name="Details - expanded long metadata",widthDp=412,heightDp=900) @Composable fun CommunityDetailsExpandedPreview()=DestinationPreviewFrame{ CommunitySkinDetails(detailsLongMetadataSkin.id,{}, {},previewSkin=detailsLongMetadataSkin,previewState=DetailsState.CONTENT,previewTechnicalExpanded=true) }
@Preview(name="Details - loading shell",widthDp=412,heightDp=900) @Composable fun CommunityDetailsLoadingPreview()=DestinationPreviewFrame{ CommunitySkinDetails("preview",{}, {},previewState=DetailsState.LOADING) }
@Preview(name="Details - error shell",widthDp=412,heightDp=900) @Composable fun CommunityDetailsErrorPreview()=DestinationPreviewFrame{ CommunitySkinDetails("preview",{}, {},previewState=DetailsState.ERROR) }
@Preview(name="Details - not found shell",widthDp=412,heightDp=900) @Composable fun CommunityDetailsNotFoundPreview()=DestinationPreviewFrame{ CommunitySkinDetails("preview",{}, {},previewState=DetailsState.NOT_FOUND) }
@Preview(name="Route chrome proof - gallery",widthDp=412,heightDp=900) @Composable fun CommunityGalleryChromeProofPreview()=PreviewFrame{ Column { Text("Community Hub",color=RetroText); Spacer(Modifier.height(12.dp)); CommunityGalleryContent({},{},{},previewState=GalleryState.CONTENT) } }
@Preview(name="Route chrome proof - details",widthDp=412,heightDp=900) @Composable fun CommunityDetailsChromeProofPreview()=DestinationPreviewFrame{ CommunitySkinDetails(detailsPreviewSkin.id,{}, {},previewSkin=detailsPreviewSkin,previewState=DetailsState.CONTENT) }
@Preview(widthDp=412,heightDp=900) @Composable fun ImportWizardPreview()=PreviewFrame{ SkinImportWizard({},{},2,browserSource=previewBrowser) }
