package com.retr0.projectretr0.ui.carmodels

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.retr0.projectretr0.data.carModels.CommunityCarModel
import com.retr0.projectretr0.data.carModels.UnavailableCarModelHubRepository
import com.retr0.projectretr0.data.carModels.local.AddonInspection
import com.retr0.projectretr0.data.carModels.local.ApplyOutcome
import com.retr0.projectretr0.data.carModels.local.InstallMode
import com.retr0.projectretr0.data.carModels.local.BackupFile
import com.retr0.projectretr0.data.carModels.local.BackupStatus
import com.retr0.projectretr0.data.carModels.local.CarModelActivity
import com.retr0.projectretr0.data.carModels.local.CarModelActivityKind
import com.retr0.projectretr0.data.carModels.local.CarModelBackup
import com.retr0.projectretr0.data.carModels.local.CarModelState
import com.retr0.projectretr0.data.carModels.local.InstalledCarModel
import com.retr0.projectretr0.data.carModels.local.LocalCarModel
import com.retr0.projectretr0.data.carModels.local.StagedCarModel
import com.retr0.projectretr0.data.carModels.local.TargetInspection
import com.retr0.projectretr0.data.carModels.manifest.CarModelFile
import com.retr0.projectretr0.data.carModels.manifest.CarModelManifest
import com.retr0.projectretr0.data.carModels.manifest.PackageMetadata
import com.retr0.projectretr0.data.carModels.manifest.TargetVehicle
import com.retr0.projectretr0.ui.skins.GamePackage
import com.retr0.projectretr0.ui.theme.*
import java.io.File

/** Example data for @Previews only (the mockup's BMW M3 GTR Street). Never shown in the running app. */
private object PreviewCarModels {
    const val VEHICLE = "2001_bmw_m3_gtr_street"
    const val ROW = "com.ea.games.r3_row"
    const val NA = "com.ea.games.r3_na"
    private const val HASH = "94b6d3f26d988b742da9bd2c77833e7fac4e0cbb58e07bb7a79cb25b77a023bd"
    private const val DAY = 86_400_000L
    private const val NOW = 1_776_000_000_000L

    val paths: List<String> = List(11) { "${VEHICLE}_lod$it.m3g" } + listOf("$VEHICLE.points", "${VEHICLE}_wheels.points") +
        List(6) { "livery/${VEHICLE}_livery_0${it + 1}.atc.dds.z" } + List(4) { "${VEHICLE}_data$it.bin" }
    private val manifest = CarModelManifest(
        metadata = PackageMetadata("BMW M3 GTR Street", creator = "NovaMods"), supportedGamePackages = listOf(ROW), target = TargetVehicle(VEHICLE, VEHICLE),
        files = paths.map { CarModelFile(it, it, "texture", "unknown", sizeBytes = 2_197_000, sha256 = HASH) },
    )
    val local = LocalCarModel("m1", "BMW M3 GTR Street", "NovaMods", "Street-spec conversion of the GTR with a full interior, new wheels and six liveries.", "13.x", "", listOf(ROW), manifest, "/data/library/m1/files", null, emptyList(), listOf("readme.txt"), NOW - 3 * DAY)
    private fun backup(dir: String, status: BackupStatus, problems: Int = 0) = CarModelBackup(ROW, dir, "/storage/emulated/0/Documents/ProjectRETR0/CarModelBackups/$ROW/$dir", paths.map { BackupFile(it, 2_180_000, HASH) }, NOW - 2 * DAY, status, problems)
    val state = CarModelState(
        models = listOf(local, local.copy(id = "m2", name = "Porsche 911 RSR Widebody", creator = "ApexTuner", supportedPackages = listOf(ROW, NA), manifest = manifest.copy(target = TargetVehicle("2016_porsche_911rsr", "2016_porsche_911rsr")))),
        installed = listOf(InstalledCarModel("m1", "BMW M3 GTR Street", ROW, VEHICLE, NOW - DAY, true, 23, 50_531_000), InstalledCarModel("m2", "Porsche 911 RSR Widebody", ROW, "2016_porsche_911rsr", NOW - 5 * DAY, false, 19, 41_000_000)),
        backups = listOf(backup(VEHICLE, BackupStatus.VERIFIED), backup("2016_porsche_911rsr", BackupStatus.CORRUPT, 2), backup("2014_audi_r18", BackupStatus.MISSING, 23)),
        activity = listOf(
            CarModelActivity("a1", CarModelActivityKind.APPLY, "BMW M3 GTR Street", "23 files applied to $VEHICLE and verified", NOW - DAY, true),
            CarModelActivity("a2", CarModelActivityKind.BACKUP, VEHICLE, "23 original files backed up and verified", NOW - DAY, true),
            CarModelActivity("a3", CarModelActivityKind.APPLY, "Porsche 911 RSR Widebody", "[CM-APPLY-007] Hash mismatch after copying body.m3g — original files restored", NOW - 5 * DAY, false),
        ),
    )
    val hub = listOf(
        CommunityCarModel("h1", "BMW M3 GTR Street", "NovaMods", "Street-spec conversion of the GTR with a full interior, new wheels and six liveries.", VEHICLE, listOf(ROW), "13.x", 23, 50_531_000, 1240, 4810, true, "2026-04-12", screenshotUrls = listOf("preview-1", "preview-2")),
        CommunityCarModel("h2", "Porsche 911 RSR Widebody", "ApexTuner", "Widebody kit with endurance aero.", "2016_porsche_911rsr", listOf(ROW, NA), "13.x", 19, 41_000_000, 842, 2100, false, "2026-04-09"),
        CommunityCarModel("h3", "Audi R18 Le Mans", "Retr0", "NA-only build.", "2014_audi_r18", listOf(NA), "12.x", 27, 61_000_000, 310, 990, true, "2026-03-30"),
    )
    val staged = StagedCarModel(File("/staging/$VEHICLE"), VEHICLE, "Download/$VEHICLE", 23, 50_531_000, listOf("readme.txt"))
    const val TARGET = "/storage/emulated/0/Android/data/$ROW/files/.depot/vehicles/$VEHICLE"
    const val BACKUP_DIR = "/storage/emulated/0/Documents/ProjectRETR0/CarModelBackups/$ROW/$VEHICLE"
}

private val P = PreviewCarModels

@Composable private fun Frame(content: @Composable BoxScope.() -> Unit) { ProjectRETR0InstallerTheme { Box(Modifier.fillMaxSize().background(RetroBg)) { content() } } }
@Composable private fun RootPreview(section: CarModelsSection, state: CarModelState = P.state, connected: Boolean = true) = Frame {
    CarModelsRoot({ ModsHeader(ModsKind.CARS) {} }, section, {}, state, connected, true, GamePackage.ROW, null, null, {}, CarModelsActions())
}
@Composable private fun WizardPreview(step: Int, primary: String, enabled: Boolean = true, content: @Composable ColumnScope.() -> Unit) = Frame {
    WizardScaffold(step, {}, footer = { com.retr0.projectretr0.ui.components.SecondaryButton("Back", Modifier.weight(1f)) {}; com.retr0.projectretr0.ui.components.PrimaryButton(primary, Modifier.weight(1f), enabled = enabled) {} }, content = content)
}

// 1 · Landing
@Preview(name = "1 Landing - Car Models", widthDp = 412, heightDp = 900) @Composable fun CarModelsLandingPreview() = RootPreview(CarModelsSection.OVERVIEW)
@Preview(name = "1 Landing - disconnected", widthDp = 412, heightDp = 900) @Composable fun CarModelsLandingDisconnectedPreview() = RootPreview(CarModelsSection.OVERVIEW, CarModelState(), connected = false)
@Preview(name = "1 Landing - large font", widthDp = 412, heightDp = 900, fontScale = 1.5f) @Composable fun CarModelsLandingLargeFontPreview() = RootPreview(CarModelsSection.OVERVIEW)
@Preview(name = "My Models", widthDp = 412, heightDp = 900) @Composable fun CarModelsLibraryPreview() = RootPreview(CarModelsSection.LIBRARY)
@Preview(name = "My Models - empty", widthDp = 412, heightDp = 900) @Composable fun CarModelsLibraryEmptyPreview() = RootPreview(CarModelsSection.LIBRARY, CarModelState())

// 2 · Gallery and 2b · states
@Composable private fun GalleryPreview(state: CarGalleryState) = Frame { CarModelsGallery(UnavailableCarModelHubRepository, GamePackage.ROW, CarModelState(), {}, { _, _ -> }, {}, onBack = {}, previewState = state, previewItems = if (state == CarGalleryState.CONTENT) P.hub else emptyList()) }
@Preview(name = "2 Gallery", widthDp = 412, heightDp = 900) @Composable fun CarModelsGalleryPreview() = GalleryPreview(CarGalleryState.CONTENT)
@Preview(name = "2b Gallery - loading", widthDp = 412, heightDp = 900) @Composable fun CarModelsGalleryLoadingPreview() = GalleryPreview(CarGalleryState.LOADING)
@Preview(name = "2b Gallery - empty", widthDp = 412, heightDp = 900) @Composable fun CarModelsGalleryEmptyPreview() = GalleryPreview(CarGalleryState.EMPTY)
@Preview(name = "2b Gallery - offline", widthDp = 412, heightDp = 900) @Composable fun CarModelsGalleryOfflinePreview() = GalleryPreview(CarGalleryState.OFFLINE)
@Preview(name = "2b Gallery - error", widthDp = 412, heightDp = 900) @Composable fun CarModelsGalleryErrorPreview() = GalleryPreview(CarGalleryState.ERROR)
@Preview(name = "2b Gallery - hub not available (live build)", widthDp = 412, heightDp = 900) @Composable fun CarModelsGalleryUnavailablePreview() = GalleryPreview(CarGalleryState.UNAVAILABLE)

// 3 · Details
@Preview(name = "3 Details - hub model", widthDp = 412, heightDp = 1100) @Composable fun CarModelDetailsPreview() = Frame {
    CarModelDetailsScreen(P.hub.first().toDetailsUi(GamePackage.ROW).copy(fileSummary = "23 files across meshes (.m3g), points, and liveries (.atc.dds.z)"), GamePackage.ROW, {}, "Download model · 48.2 MB", Icons.Filled.Download, {}, onShare = {}, onReport = {})
}
@Preview(name = "3 Details - library model", widthDp = 412, heightDp = 1100) @Composable fun CarModelLocalDetailsPreview() = Frame {
    CarModelDetailsScreen(P.local.toDetailsUi(GamePackage.ROW), GamePackage.ROW, {}, "Apply car model", Icons.Filled.Bolt, {}, onRemove = {})
}

// 4 · Import wizard
@Preview(name = "4.0 Wizard - choose install type", widthDp = 412, heightDp = 900) @Composable fun CarWizardInstallModePreview() = WizardPreview(1, "Continue") { StepInstallMode(InstallMode.ADDON) {} }
@Preview(name = "4.0 Wizard - choose install type, none selected", widthDp = 412, heightDp = 900) @Composable fun CarWizardInstallModeEmptyPreview() = WizardPreview(1, "Continue", enabled = false) { StepInstallMode(null) {} }
@Preview(name = "4.3 Wizard - add-on destination (new folder)", widthDp = 412, heightDp = 900) @Composable fun CarWizardAddonTargetPreview() = WizardPreview(4, "Next · Details") {
    StepAddonTarget(GamePackage.ROW, {}, "${P.VEHICLE}_addon", {}, AddonInspection(P.TARGET + "_addon", false, P.paths, emptyList(), null), null)
}
@Preview(name = "4.3 Wizard - add-on destination (overwrites)", widthDp = 412, heightDp = 1000) @Composable fun CarWizardAddonOverwritePreview() = WizardPreview(4, "Next · Details") {
    StepAddonTarget(GamePackage.ROW, {}, P.VEHICLE, {}, AddonInspection(P.TARGET, true, P.paths.drop(2), P.paths.take(2), null), null)
}
@Preview(name = "4.5 Wizard - add-on review", widthDp = 412, heightDp = 1000) @Composable fun CarWizardAddonReviewPreview() = WizardPreview(6, "Add car model") {
    StepAddonReview(AddonInspection(P.TARGET + "_addon", false, P.paths, emptyList(), null), null, "${P.VEHICLE}_addon", GamePackage.ROW)
}
@Preview(name = "4.6 Wizard - add-on result success", widthDp = 412, heightDp = 900) @Composable fun CarWizardAddonResultPreview() = WizardPreview(7, "Done") { StepResult(ApplyOutcome.Success(23, 1_776_000_000_000L), null, "${P.VEHICLE}_addon", GamePackage.ROW, 23, {}, {}, InstallMode.ADDON) }
@Preview(name = "4.1 Wizard - select folder", widthDp = 412, heightDp = 900) @Composable fun CarWizardSelectFolderPreview() = WizardPreview(2, "Next · Validate") { StepSelectFolder(P.staged, null, null) {} }
@Preview(name = "4.2 Wizard - hashing", widthDp = 412, heightDp = 900) @Composable fun CarWizardHashingPreview() = WizardPreview(3, "Next · Target", enabled = false) { StepValidate(18 to 23, emptyList(), emptyList(), emptyList()) }
@Preview(name = "4.2 Wizard - validated", widthDp = 412, heightDp = 900) @Composable fun CarWizardValidatePreview() = WizardPreview(3, "Next · Target") { StepValidate(null, P.paths, listOf("readme.txt"), emptyList()) }
@Preview(name = "4.3 Wizard - target", widthDp = 412, heightDp = 1000) @Composable fun CarWizardTargetPreview() = WizardPreview(4, "Next · Details") {
    StepTarget(GamePackage.ROW, {}, listOf("1999_bmw_v12_lmr", P.VEHICLE, "2014_audi_r18", "2016_porsche_911rsr"), null, "", {}, P.VEHICLE, {}, "Download/${P.VEHICLE}", P.TARGET, TargetInspection(P.TARGET, true, 23, 23), false)
}
@Preview(name = "4.3 Wizard - target, zero matches (warn + override)", widthDp = 412, heightDp = 1100) @Composable fun CarWizardTargetIncompatiblePreview() = WizardPreview(4, "Next · Details", enabled = false) {
    StepTarget(GamePackage.ROW, {}, listOf(P.VEHICLE, "2014_audi_r18"), null, "", {}, "2014_audi_r18", {}, "Download/${P.VEHICLE}", P.TARGET.replace(P.VEHICLE, "2014_audi_r18"), TargetInspection(P.TARGET, true, 27, 0), true)
}
@Preview(name = "4.4 Wizard - details", widthDp = 412, heightDp = 1200) @Composable fun CarWizardDetailsPreview() = WizardPreview(5, "Next · Backup") { StepDetails(CarModelDetailsForm("BMW M3 GTR Street", "NovaMods", "Street-spec conversion."), {}, P.VEHICLE, null, null, null, null, {}, {}) }
@Preview(name = "4.5 Wizard - backup gate, creating (Apply locked)", widthDp = 412, heightDp = 900) @Composable fun CarWizardBackupCreatingPreview() = WizardPreview(6, "Apply car model", enabled = false) { StepBackup(BackupCardState.Creating(9, 23), P.BACKUP_DIR, P.paths, false) {} }
@Preview(name = "4.5 Wizard - backup gate, verified (Apply enabled)", widthDp = 412, heightDp = 900) @Composable fun CarWizardBackupVerifiedPreview() = WizardPreview(6, "Apply car model") { StepBackup(BackupCardState.Verified(23, 50_200_000), P.BACKUP_DIR, P.paths, false) {} }
@Preview(name = "4.5 Wizard - backup gate, failed (Apply locked)", widthDp = 412, heightDp = 900) @Composable fun CarWizardBackupFailedPreview() = WizardPreview(6, "Apply car model", enabled = false) { StepBackup(BackupCardState.Failed("Backup copy of ${P.VEHICLE}_lod3.m3g does not match the original", "CM-BACKUP-010"), P.BACKUP_DIR, P.paths, false) {} }
@Preview(name = "4.6 Wizard - result success", widthDp = 412, heightDp = 900) @Composable fun CarWizardResultPreview() = WizardPreview(7, "Done") { StepResult(ApplyOutcome.Success(23, 1_776_000_000_000L), null, P.VEHICLE, GamePackage.ROW, 23, {}, {}) }
@Preview(name = "4.6 Wizard - result failed, originals restored", widthDp = 412, heightDp = 900) @Composable fun CarWizardResultFailedPreview() = WizardPreview(7, "Retry") { StepResult(ApplyOutcome.Failed("Hash mismatch after copying ${P.VEHICLE}_lod3.m3g", true, true, "CM-APPLY-007"), null, P.VEHICLE, GamePackage.ROW, 23, {}, {}) }
@Preview(name = "4.6 Wizard - result failed, restore needed", widthDp = 412, heightDp = 900) @Composable fun CarWizardResultFailedUnrestoredPreview() = WizardPreview(7, "Retry") { StepResult(ApplyOutcome.Failed("Unable to copy ${P.VEHICLE}_lod3.m3g", true, false, "CM-APPLY-006"), null, P.VEHICLE, GamePackage.ROW, 23, {}, {}) }

// 5 · Installed, 6 · Backups, Activity
@Preview(name = "5 Installed models", widthDp = 412, heightDp = 900) @Composable fun CarModelsInstalledPreview() = RootPreview(CarModelsSection.APPLIED)
@Preview(name = "6 Backups - verified / corrupt / missing", widthDp = 412, heightDp = 1100) @Composable fun CarModelsBackupsPreview() = RootPreview(CarModelsSection.BACKUPS)
@Preview(name = "Activity", widthDp = 412, heightDp = 900) @Composable fun CarModelsActivityPreview() = RootPreview(CarModelsSection.ACTIVITY)

// 7 · Revert confirmation (sheet body over the dimmed Installed screen)
@Composable private fun RevertPreview(status: BackupStatus) = Frame {
    CarModelsRoot({ ModsHeader(ModsKind.CARS) {} }, CarModelsSection.APPLIED, {}, P.state, true, true, GamePackage.ROW, null, null, {}, CarModelsActions())
    Box(Modifier.fillMaxSize().background(Color.Black.copy(alpha = .6f)))
    RevertSheetContent("BMW M3 GTR Street", P.TARGET, status, false, 23, 50_200_000, true, {}, {}, Modifier.align(Alignment.BottomCenter).background(RetroSurface))
}
@Preview(name = "7 Revert confirmation", widthDp = 412, heightDp = 900) @Composable fun CarModelsRevertPreview() = RevertPreview(BackupStatus.VERIFIED)
@Preview(name = "7 Revert - corrupt backup (Restore disabled)", widthDp = 412, heightDp = 900) @Composable fun CarModelsRevertCorruptPreview() = RevertPreview(BackupStatus.CORRUPT)

// 8 · Community Hub tab and badges
@Preview(name = "8 Hub - Car Models tab content", widthDp = 412, heightDp = 900) @Composable fun CarModelsHubTabPreview() = Frame {
    Column(Modifier.padding(horizontal = 18.dp)) {
        Text("Community Hub", color = RetroText)
        Spacer(Modifier.height(12.dp))
        CarModelsGallery(UnavailableCarModelHubRepository, GamePackage.ROW, P.state.copy(models = listOf(P.local.copy(remoteId = "h1"))), {}, { _, _ -> }, {}, previewState = CarGalleryState.CONTENT, previewItems = P.hub)
    }
}
@Preview(name = "Badges", widthDp = 412, heightDp = 220) @Composable fun CarModelBadgesPreview() = Frame {
    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { ContentTypeBadge(ContentType.CAR_MODEL); ContentTypeBadge(ContentType.SKIN); VerifiedBadge() }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { CompatLevel.entries.forEach { CompatBadge(it) } }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { BackupStatus.entries.forEach { BackupStatusBadge(it) } }
        WizardStepper(6, 3)
    }
}
