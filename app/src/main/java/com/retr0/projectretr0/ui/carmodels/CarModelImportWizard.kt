package com.retr0.projectretr0.ui.carmodels

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.retr0.projectretr0.IRetr0UserService
import com.retr0.projectretr0.data.carModels.CarModelErrorCode
import com.retr0.projectretr0.data.carModels.local.AddonInspection
import com.retr0.projectretr0.data.carModels.local.ApplyOutcome
import com.retr0.projectretr0.data.carModels.local.InstallMode
import androidx.compose.foundation.selection.selectable
import com.retr0.projectretr0.data.carModels.local.CarModelDetailsInput
import com.retr0.projectretr0.data.carModels.local.CarModelMediaAsset
import com.retr0.projectretr0.ui.skins.CropDecision
import com.retr0.projectretr0.ui.skins.CropRect
import com.retr0.projectretr0.ui.skins.ImageCropRequest
import com.retr0.projectretr0.ui.skins.SkinImageProcessor
import com.retr0.projectretr0.ui.skins.SkinImageRole
import com.retr0.projectretr0.ui.skins.SkinImportCache
import com.retr0.projectretr0.data.carModels.local.CarModelFileGroup
import com.retr0.projectretr0.data.carModels.local.CarModelFileGroups
import com.retr0.projectretr0.data.carModels.local.CarModelPaths
import com.retr0.projectretr0.data.carModels.local.StagedCarModel
import com.retr0.projectretr0.data.carModels.local.TargetInspection
import com.retr0.projectretr0.data.carModels.local.TargetStatus
import com.retr0.projectretr0.data.carModels.manifest.CarModelFolderScanner
import com.retr0.projectretr0.data.carModels.manifest.CarModelManifest
import com.retr0.projectretr0.ui.components.*
import com.retr0.projectretr0.ui.skins.GamePackage
import com.retr0.projectretr0.ui.skins.WarningBanner
import com.retr0.projectretr0.ui.theme.*
import kotlinx.coroutines.launch

private const val STEPS = 7

/** Wizard step numbers and the gates between them; the install type is chosen before any file is picked. */
internal object WizardSteps {
    const val MODE = 1
    const val FOLDER = 2
    const val VALIDATE = 3
    const val TARGET = 4
    const val DETAILS = 5
    const val REVIEW = 6
    const val RESULT = 7

    /** Null while no install type is chosen: nothing can be imported or applied without one. */
    fun afterMode(mode: InstallMode?, applyingExistingModel: Boolean): Int? = when {
        mode == null -> null
        applyingExistingModel -> TARGET
        else -> FOLDER
    }

    /** REPLACE keeps the verified whole-folder backup gate; ADDON only needs a free destination folder. */
    fun canApply(mode: InstallMode?, backupVerified: Boolean, addon: AddonInspection?): Boolean = when (mode) {
        null -> false
        InstallMode.REPLACE -> backupVerified
        InstallMode.ADDON -> addon != null && addon.occupiedBy == null
    }

    /** Turns a picked folder name into a safe vehicle directory name for a new add-on folder. */
    fun suggestAddonFolder(name: String?): String = name.orEmpty().trim().replace(Regex("[^A-Za-z0-9_-]+"), "_").trim('_').take(120)
}

data class CarModelDetailsForm(
    val name: String = "",
    val creator: String = "",
    val description: String = "",
    val supportsRow: Boolean = true,
    val supportsNa: Boolean = false,
    val gameVersion: String = "",
    val license: String = "",
    val cover: CarModelMediaAsset? = null,
    val screenshots: List<CarModelMediaAsset> = emptyList(),
    val uploadToCommunityHub: Boolean = false,
) {
    val supportedPackages: List<String> get() = listOfNotNull(GamePackage.ROW.id.takeIf { supportsRow }, GamePackage.NA.id.takeIf { supportsNa })
    val valid: Boolean get() = name.isNotBlank() && supportedPackages.isNotEmpty() && screenshots.size <= 8
    val publishReady: Boolean get() = cover != null && cover.mimeType == "image/webp" && cover.sizeBytes <= 500L * 1024 && screenshots.all { it.mimeType == "image/webp" && it.sizeBytes <= 1024L * 1024 }
}

/**
 * Seven-step import + apply flow that starts with the install type (Replace or Add-on). Pass [existingModelId]
 * to apply a model that is already in the library: after the install type it jumps to the target and skips
 * the details form.
 */
@Composable
fun CarModelImportWizard(
    viewModel: CarModelsViewModel,
    service: IRetr0UserService?,
    isServiceConnected: Boolean,
    initialPackage: GamePackage,
    existingModelId: String?,
    onExit: () -> Unit,
    onViewBackups: () -> Unit,
    onRevertRequested: (gamePackage: String, vehicleDirectory: String) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val library by viewModel.state.collectAsState()
    val liveService = service.takeIf { isServiceConnected }
    var step by remember { mutableIntStateOf(WizardSteps.MODE) }
    var mode by remember { mutableStateOf<InstallMode?>(null) }
    var modelId by remember { mutableStateOf(existingModelId) }
    val savedModel = modelId?.let(library::model)

    var staged by remember { mutableStateOf<StagedCarModel?>(null) }
    var stageProgress by remember { mutableStateOf<Pair<Int, Int>?>(null) }
    var stageError by remember { mutableStateOf<UiError?>(null) }
    var scan by remember { mutableStateOf<CarModelFolderScanner.ScanResult?>(null) }
    var hashProgress by remember { mutableStateOf<Pair<Int, Int>?>(null) }

    var gamePackage by remember { mutableStateOf(initialPackage) }
    var vehicles by remember { mutableStateOf<List<String>?>(null) }
    var vehiclesError by remember { mutableStateOf<UiError?>(null) }
    var vehicleQuery by remember { mutableStateOf("") }
    var vehicle by remember { mutableStateOf<String?>(null) }
    var inspection by remember { mutableStateOf<TargetInspection?>(null) }
    var zeroMatchAccepted by remember { mutableStateOf(false) }
    var addonFolder by remember { mutableStateOf("") }
    var addonInspection by remember { mutableStateOf<AddonInspection?>(null) }
    var addonError by remember { mutableStateOf<UiError?>(null) }

    val context = LocalContext.current
    val mediaCache = remember(context) { SkinImportCache(context, "car-model-media") }
    val mediaProcessor = remember(context, mediaCache) { SkinImageProcessor(context.contentResolver, mediaCache) }
    var pendingMedia by remember { mutableStateOf<Pair<Uri, SkinImageRole>?>(null) }
    var cropRequest by remember { mutableStateOf<ImageCropRequest?>(null) }
    var manualCropRequest by remember { mutableStateOf<ImageCropRequest?>(null) }
    var mediaError by remember { mutableStateOf<String?>(null) }
    var form by remember { mutableStateOf(CarModelDetailsForm()) }
    var saveError by remember { mutableStateOf<UiError?>(null) }
    var uploadError by remember { mutableStateOf<UiError?>(null) }
    var uploadProgress by remember { mutableStateOf<String?>(null) }
    var saving by remember { mutableStateOf(false) }
    var uploading by remember { mutableStateOf(false) }
    var publishing by remember { mutableStateOf(false) }

    var backupState by remember { mutableStateOf<BackupCardState?>(null) }
    var backupAttempt by remember { mutableIntStateOf(0) }
    var confirmApply by remember { mutableStateOf(false) }
    var applyProgress by remember { mutableStateOf<Pair<Int, Int>?>(null) }
    var outcome by remember { mutableStateOf<ApplyOutcome?>(null) }

    val manifest: CarModelManifest? = savedModel?.manifest ?: scan?.manifest
    val applying = step == WizardSteps.RESULT && outcome == null
    val addon = mode == InstallMode.ADDON
    // Where the model lands: an existing vehicle for REPLACE, the chosen (usually new) folder for ADDON.
    val destination: String? = if (addon) addonFolder.takeIf { CarModelPaths.SAFE_VEHICLE_DIR.matches(it) } else vehicle

    fun exit() { if (modelId == null) viewModel.importer.discardStaging(); onExit() }
    // The install type can be changed by going Back until the review/apply step; it is fixed from there on.
    fun back() {
        when {
            applying || saving || uploading -> Unit
            step == WizardSteps.RESULT -> exit()
            step == WizardSteps.REVIEW -> step = WizardSteps.TARGET
            step == WizardSteps.TARGET && existingModelId != null -> step = WizardSteps.MODE
            step == WizardSteps.DETAILS && modelId != null -> step = WizardSteps.TARGET
            step > WizardSteps.MODE -> step--
            else -> exit()
        }
    }
    BackHandler(onBack = ::back)

    val folderPicker = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocumentTree()) { uri ->
        // Keep the mode gate defensive as well as visual: a stale picker result must never stage a folder.
        if (mode == null || uri == null) return@rememberLauncherForActivityResult
        stageError = null; staged = null; scan = null; stageProgress = 0 to 0
        scope.launch {
            viewModel.importer.stage(uri) { done, total -> stageProgress = done to total }
                .onSuccess { staged = it; form = form.copy(name = it.folderName) }
                .onFailure { stageError = it.toUiError() }
            stageProgress = null
        }
    }
    fun processMedia(decision: CropDecision, rect: CropRect? = null) { val pending = pendingMedia ?: return; scope.launch { mediaProcessor.process(pending.first, pending.second, decision, rect).onSuccess { image -> val asset = CarModelMediaAsset(image.file, image.mimeType, image.sizeBytes, image.sourceHash, image.width, image.height); if (pending.second == SkinImageRole.COVER) form = form.copy(cover = asset) else form = form.copy(screenshots = (form.screenshots + asset).take(8)); pendingMedia = null }.onFailure { mediaError = it.message } } }
    fun inspectMedia(uri: Uri, role: SkinImageRole) { scope.launch { mediaProcessor.inspect(uri, role.aspectRatio, role.cropTolerance).onSuccess { inspection -> pendingMedia = uri to role; if (inspection.needsCropChoice) cropRequest = ImageCropRequest(uri, inspection, role) else processMedia(CropDecision.AUTO_CROP) }.onFailure { mediaError = it.message } } }
    val coverPicker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri -> if (uri != null) inspectMedia(uri, SkinImageRole.COVER) }
    val screenshotPicker = rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris -> uris.take((8 - form.screenshots.size).coerceAtLeast(0)).forEach { inspectMedia(it, SkinImageRole.SCREENSHOT) } }

    // Validate step: hash every staged file into the manifest.
    LaunchedEffect(step, staged) {
        val current = staged
        if (step == WizardSteps.VALIDATE && current != null && scan == null) {
            hashProgress = 0 to current.fileCount
            scan = viewModel.importer.scan(current) { done, total -> hashProgress = done to total }
            hashProgress = null
        }
    }
    // Target step (REPLACE): list the vehicles of the selected package, then inspect the chosen one.
    LaunchedEffect(step, mode, gamePackage, liveService) {
        if (step != WizardSteps.TARGET || mode != InstallMode.REPLACE) return@LaunchedEffect
        vehicles = null; vehiclesError = null
        if (liveService == null) { vehiclesError = CarModelErrorCode.SERVICE_UNAVAILABLE.toUiError("Connect Shizuku to browse RR3 vehicles."); return@LaunchedEffect }
        viewModel.listVehicles(liveService, gamePackage.id)
            .onSuccess { list ->
                vehicles = list
                val guess = vehicle ?: savedModel?.vehicleDirectory ?: staged?.folderName
                vehicle = list.firstOrNull { it.equals(guess, ignoreCase = true) }
            }
            .onFailure { vehiclesError = it.toUiError("RR3 ${gamePackage.short} vehicles could not be read. Is the game installed?") }
    }
    LaunchedEffect(step, mode, gamePackage, vehicle, manifest, liveService) {
        inspection = null; zeroMatchAccepted = false
        val selected = vehicle
        if (step == WizardSteps.TARGET && mode == InstallMode.REPLACE && selected != null && manifest != null && liveService != null) inspection = viewModel.inspectTarget(liveService, manifest, gamePackage.id, selected).getOrNull()
    }
    // Target step (ADDON): suggest a new folder named after the model, then check what adding it would touch.
    LaunchedEffect(step, mode) {
        if (step == WizardSteps.TARGET && addon && addonFolder.isBlank()) addonFolder = WizardSteps.suggestAddonFolder(savedModel?.vehicleDirectory ?: staged?.folderName)
    }
    LaunchedEffect(step, mode, gamePackage, addonFolder, manifest, liveService) {
        addonInspection = null; addonError = null
        if (!addon || (step != WizardSteps.TARGET && step != WizardSteps.REVIEW)) return@LaunchedEffect
        val folder = destination ?: return@LaunchedEffect
        if (liveService == null) { addonError = CarModelErrorCode.SERVICE_UNAVAILABLE.toUiError("Connect Shizuku to check the destination folder."); return@LaunchedEffect }
        if (manifest == null) return@LaunchedEffect
        viewModel.inspectAddonTarget(liveService, manifest, gamePackage.id, folder)
            .onSuccess { addonInspection = it }
            .onFailure { addonError = it.toUiError("RR3 ${gamePackage.short} vehicles could not be read. Is the game installed?") }
    }
    // Review step (REPLACE only): the gate. Creating (or re-verifying) the original backup is the only way to unlock Apply.
    LaunchedEffect(step, backupAttempt) {
        val selected = vehicle
        if (step != WizardSteps.REVIEW || mode != InstallMode.REPLACE || selected == null) return@LaunchedEffect
        if (liveService == null) { backupState = BackupCardState.Failed(CarModelErrorCode.SERVICE_UNAVAILABLE.userMessage, CarModelErrorCode.SERVICE_UNAVAILABLE.code); return@LaunchedEffect }
        backupState = BackupCardState.Creating(0, 0)
        backupState = viewModel.ensureBackup(liveService, gamePackage.id, selected) { done, total -> backupState = BackupCardState.Creating(done, total) }
            .fold({ BackupCardState.Verified(it.files.size, it.totalBytes) }, { error -> error.toUiError().let { BackupCardState.Failed(it.message, it.code) } })
    }

    val canApply = liveService != null && WizardSteps.canApply(mode, backupState is BackupCardState.Verified, addonInspection)
    fun startApply() {
        val id = modelId; val selected = destination; val chosen = mode
        if (id == null || selected == null || chosen == null || liveService == null || !canApply) return
        confirmApply = false; outcome = null; applyProgress = 0 to (manifest?.files?.size ?: 0); step = WizardSteps.RESULT
        scope.launch { outcome = viewModel.install(liveService, chosen, id, gamePackage.id, selected) { done, total -> applyProgress = done to total } }
    }

    fun retryCommunityPublish() {
        val id = modelId ?: return
        uploadError = null
        uploading = true
        uploadProgress = "Retrying upload and publish…"
        scope.launch {
            val uploadResult = if (library.model(id)?.remoteDraftId != null) Result.success(Unit)
            else viewModel.uploadDraft(context, id) { progress ->
                val event = progress as? com.retr0.projectretr0.data.carModels.CarModelUploadManager.UploadProgress.Event
                if (event != null) uploadProgress = "Uploading ${event.name} · ${event.completed}/${event.total}"
            }.map { Unit }
            uploading = false
            if (uploadResult.isFailure) {
                uploadError = uploadResult.exceptionOrNull()?.toUiError("Upload failed. The model is still saved locally; retry when the Worker is reachable.")
            } else {
                publishing = true
                uploadProgress = "Publishing asset…"
                viewModel.publishDraft(context, id).onFailure { error ->
                    uploadError = UiError("Upload succeeded, but publishing failed. The remote draft was preserved; retry from this wizard or the library.", error.toUiError().code)
                }
                publishing = false
            }
            uploadProgress = null
        }
    }

    cropRequest?.let { request ->
        com.retr0.projectretr0.ui.skins.CropChoiceDialog(request, onAutoCrop = { cropRequest = null; processMedia(CropDecision.AUTO_CROP) }, onManualCrop = { cropRequest = null; manualCropRequest = request })
    }
    manualCropRequest?.let { request ->
        com.retr0.projectretr0.ui.skins.ManualCropDialog(request, mediaProcessor, onConfirm = { rect -> manualCropRequest = null; processMedia(CropDecision.MANUAL_CROP, rect) }, onDismiss = { manualCropRequest = null })
    }
    val paths = remember { CarModelPaths() }
    val targetPath = vehicle?.let { runCatching { paths.vehicleDir(gamePackage.id, it) }.getOrNull() }
    val activeOnVehicle = vehicle?.let { library.activeInstall(gamePackage.id, it) }
    val alreadyInstalled = activeOnVehicle != null
    val addonOnVehicle = activeOnVehicle?.isAddon == true
    val relativeFiles = manifest?.files?.map { it.sourcePath }.orEmpty()
    val targetReady = if (addon) addonInspection != null && addonInspection?.occupiedBy == null
        else !addonOnVehicle && (inspection?.status == TargetStatus.FOUND || (inspection?.status == TargetStatus.INCOMPATIBLE && zeroMatchAccepted))
    val reviewLabel = if (addon) "Next · Review" else "Next · Backup"

    WizardScaffold(
        step = step, onBack = ::back,
        footer = {
            when (step) {
                WizardSteps.MODE -> {
                    SecondaryButton("Cancel", Modifier.weight(1f), onClick = ::exit)
                    PrimaryButton("Continue", Modifier.weight(1f), enabled = mode != null) { WizardSteps.afterMode(mode, existingModelId != null)?.let { step = it } }
                }
                WizardSteps.FOLDER -> { SecondaryButton("Back", Modifier.weight(1f), onClick = ::back); PrimaryButton("Next · Validate", Modifier.weight(1f), enabled = staged != null && stageProgress == null) { step = WizardSteps.VALIDATE } }
                WizardSteps.VALIDATE -> { SecondaryButton("Back", Modifier.weight(1f), onClick = ::back); PrimaryButton("Next · Target", Modifier.weight(1f), enabled = scan?.report?.isValid == true && hashProgress == null) { step = WizardSteps.TARGET } }
                WizardSteps.TARGET -> { SecondaryButton("Back", Modifier.weight(1f), onClick = ::back); PrimaryButton(if (modelId == null) "Next · Details" else reviewLabel, Modifier.weight(1f), enabled = targetReady) { backupState = null; backupAttempt++; step = if (modelId == null) WizardSteps.DETAILS else WizardSteps.REVIEW } }
                WizardSteps.DETAILS -> {
                    SecondaryButton("Back", Modifier.weight(1f), onClick = ::back)
                    PrimaryButton(
                        when {
                            saving -> "Saving…"
                            uploading -> "Uploading…"
                            publishing -> "Publishing asset…"
                            else -> reviewLabel
                        },
                        Modifier.weight(1f),
                        enabled = form.valid && !saving && !uploading && !publishing,
                    ) {
                        val current = staged; val scanned = scan?.manifest; val selected = destination
                        if (current == null || scanned == null || selected == null) return@PrimaryButton
                        saving = true; saveError = null; uploadError = null; uploadProgress = "Saving locally…"
                        scope.launch {
                            viewModel.importer.saveToLibrary(current, scanned, CarModelDetailsInput(form.name, form.creator, form.description, form.gameVersion, form.license, form.supportedPackages, selected, form.cover, form.screenshots))
                                .onSuccess { saved ->
                                    modelId = saved.id
                                    backupState = null
                                    backupAttempt++
                                    if (form.uploadToCommunityHub && saved.remotePublicId == null) {
                                        val uploadResult = if (saved.remoteDraftId != null) {
                                            Result.success(Unit)
                                        } else {
                                            uploading = true
                                            uploadProgress = "Uploading package and media…"
                                            viewModel.uploadDraft(context, saved.id) { progress ->
                                                val event = progress as? com.retr0.projectretr0.data.carModels.CarModelUploadManager.UploadProgress.Event
                                                if (event != null) uploadProgress = "Uploading ${event.name} · ${event.completed}/${event.total}"
                                            }.map { Unit }
                                        }
                                        uploading = false
                                        if (uploadResult.isFailure) {
                                            uploadError = uploadResult.exceptionOrNull()?.toUiError("Upload failed. The model is still saved in your local library.")
                                        } else {
                                            publishing = true
                                            uploadProgress = "Publishing asset…"
                                            viewModel.publishDraft(context, saved.id)
                                                .onFailure { error -> uploadError = UiError("Upload succeeded, but publishing failed. The remote draft was preserved; retry publishing from the library.", error.toUiError().code) }
                                            publishing = false
                                        }
                                    }
                                    uploadProgress = null
                                    // A checked publish request is incomplete until both upload and publish succeed.
                                    // Keep the saved local model and remain on a retryable details step on failure.
                                    if (uploadError == null) step = WizardSteps.REVIEW
                                }
                                .onFailure { saveError = it.toUiError() }
                            saving = false
                        }
                    }
                }
                WizardSteps.REVIEW -> { SecondaryButton("Back", Modifier.weight(1f), onClick = ::back); PrimaryButton(if (addon) "Add car model" else "Apply car model", Modifier.weight(1f), icon = if (addon) Icons.Filled.Add else Icons.Filled.Bolt, enabled = canApply) { confirmApply = true } }
                else -> when (outcome) {
                    null -> PrimaryButton(if (addon) "Adding…" else "Applying…", Modifier.weight(1f), enabled = false) {}
                    is ApplyOutcome.Success -> PrimaryButton("Done", Modifier.weight(1f), onClick = ::exit)
                    is ApplyOutcome.Failed -> {
                        SecondaryButton("Close", Modifier.weight(1f), onClick = ::exit)
                        PrimaryButton("Retry", Modifier.weight(1f), icon = Icons.Filled.Refresh, enabled = liveService != null) { outcome = null; backupState = null; backupAttempt++; step = WizardSteps.REVIEW }
                    }
                }
            }
        },
    ) {
        when (step) {
            WizardSteps.MODE -> StepInstallMode(mode) { mode = it }
            WizardSteps.FOLDER -> StepSelectFolder(staged, stageProgress, stageError) { folderPicker.launch(null) }
            WizardSteps.VALIDATE -> StepValidate(hashProgress, relativeFiles, staged?.ignoredFiles.orEmpty(), scan?.report?.errors.orEmpty())
            WizardSteps.TARGET -> if (addon) StepAddonTarget(gamePackage, { gamePackage = it }, addonFolder, { addonFolder = it.take(120) }, addonInspection, addonError)
                else StepTarget(gamePackage, { gamePackage = it; vehicle = null }, vehicles, vehiclesError, vehicleQuery, { vehicleQuery = it }, vehicle, { vehicle = it }, staged?.displayPath ?: "Library · ${savedModel?.name.orEmpty()}", targetPath, inspection, alreadyInstalled, zeroMatchAccepted, { zeroMatchAccepted = it }, addonOnVehicle)
            WizardSteps.DETAILS -> StepDetails(form, { form = it }, destination.orEmpty(), saveError, uploadError, uploadProgress, mediaError, { coverPicker.launch("image/*") }, { screenshotPicker.launch("image/*") }, if (uploadError != null) ::retryCommunityPublish else null, targetLabel = if (addon) "New car folder" else "Replaced vehicle")
            WizardSteps.REVIEW -> if (addon) StepAddonReview(addonInspection, addonError, destination.orEmpty(), gamePackage, manifest, uploadError, uploadProgress)
                else StepBackup(backupState, vehicle?.let { runCatching { paths.backupDir(gamePackage.id, it) }.getOrNull() }.orEmpty(), relativeFiles, alreadyInstalled, uploadError, uploadProgress) { backupAttempt++ }
            else -> StepResult(outcome, applyProgress, destination.orEmpty(), gamePackage, relativeFiles.size, onViewBackups, onRevert = { destination?.let { onRevertRequested(gamePackage.id, it) } }, mode = mode ?: InstallMode.REPLACE)
        }
    }

    if (confirmApply) AlertDialog(
        onDismissRequest = { confirmApply = false },
        containerColor = RetroSurface,
        title = { Text(if (addon) "Add car model?" else "Apply car model?", color = RetroText) },
        text = {
            val overwrites = addonInspection?.overwrites?.size ?: 0
            Text(
                if (addon) "“${savedModel?.name.orEmpty()}” will be added as ${relativeFiles.size} files in ${destination.orEmpty()} (RR3 ${gamePackage.short}). RR3 will be force-stopped. " +
                    (if (overwrites == 0) "No existing file is changed." else "$overwrites existing files will be backed up and verified first, then restored when you remove the add-on.")
                else "${relativeFiles.size} files in ${vehicle.orEmpty()} (RR3 ${gamePackage.short}) will be replaced by “${savedModel?.name.orEmpty()}”. RR3 will be force-stopped. Your verified original backup stays on this device so you can revert.",
                color = RetroTextSoft,
            )
        },
        confirmButton = { TextButton(onClick = ::startApply) { Text(if (addon) "Add car model" else "Apply car model", color = RetroAccent) } },
        dismissButton = { TextButton(onClick = { confirmApply = false }) { Text("Cancel", color = RetroTextDim) } },
    )
}

@Composable
fun WizardScaffold(step: Int, onBack: () -> Unit, footer: @Composable RowScope.() -> Unit, content: @Composable ColumnScope.() -> Unit) {
    Column(Modifier.fillMaxSize().background(RetroBg)) {
        BackHeader("Import car model", "Step $step of $STEPS", onBack)
        WizardStepper(STEPS, step, Modifier.padding(horizontal = 18.dp, vertical = 6.dp))
        Column(Modifier.weight(1f).fillMaxWidth().verticalScroll(rememberScrollState()).padding(horizontal = 18.dp, vertical = 12.dp), verticalArrangement = Arrangement.spacedBy(12.dp), content = content)
        Row(Modifier.fillMaxWidth().background(RetroSurfaceAlt).padding(horizontal = 18.dp, vertical = 12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically, content = footer)
    }
}

@Composable
fun StepInstallMode(selected: InstallMode?, onSelect: (InstallMode) -> Unit) {
    Text("Choose install type", color = RetroText, style = MaterialTheme.typography.titleLarge)
    InstallModeCard(Icons.Filled.SwapHoriz, "Replace an existing car", "Overwrites a vehicle's files with this model. The original is backed up first and can be restored anytime.", selected == InstallMode.REPLACE) { onSelect(InstallMode.REPLACE) }
    InstallModeCard(Icons.Filled.Layers, "Add as a new car", "Installs this model as an extra car without changing any existing vehicle. Remove it anytime to uninstall.", selected == InstallMode.ADDON) { onSelect(InstallMode.ADDON) }
    InfoNote(Icons.Filled.Info, "This affects how the model is installed and removed — you choose files next.", RetroTextMuted)
}

@Composable
private fun InstallModeCard(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String, body: String, selected: Boolean, onClick: () -> Unit) {
    val shape = RoundedCornerShape(16.dp)
    Row(
        Modifier.fillMaxWidth().clip(shape).background(if (selected) RetroAccent.copy(alpha = .10f) else RetroSurface)
            .border(if (selected) 1.5.dp else 1.dp, if (selected) RetroAccent else RetroBorder, shape)
            .selectable(selected = selected, role = androidx.compose.ui.semantics.Role.RadioButton, onClick = onClick).padding(14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(Modifier.size(42.dp).background(RetroAccent.copy(alpha = .14f), CircleShape), contentAlignment = Alignment.Center) { Icon(icon, null, tint = RetroAccent) }
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(title, color = RetroText, style = MaterialTheme.typography.titleMedium)
            Text(body, color = RetroTextMuted, style = MaterialTheme.typography.bodyMedium)
        }
        RadioButton(selected = selected, onClick = null, colors = RadioButtonDefaults.colors(selectedColor = RetroAccent, unselectedColor = RetroTextMuted))
    }
}

@Composable
fun StepSelectFolder(staged: StagedCarModel?, progress: Pair<Int, Int>?, error: UiError?, onPick: () -> Unit) {
    Text("Select folder", color = RetroText, style = MaterialTheme.typography.titleLarge)
    Column(
        Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).clickable(enabled = progress == null, onClick = onPick)
            .drawBehind { drawRoundRect(RetroAccent.copy(alpha = .6f), cornerRadius = CornerRadius(16.dp.toPx()), style = Stroke(1.5.dp.toPx(), pathEffect = PathEffect.dashPathEffect(floatArrayOf(14f, 10f)))) }
            .padding(vertical = 26.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(Icons.Filled.FolderOpen, null, tint = RetroAccent, modifier = Modifier.size(34.dp))
        Spacer(Modifier.height(8.dp))
        Text("Select car-model folder", color = RetroText, style = MaterialTheme.typography.titleMedium)
        Text("Choose the whole vehicle folder, not a single file.", color = RetroTextMuted, style = MaterialTheme.typography.bodyMedium)
    }
    progress?.let { (done, total) ->
        RetroCard {
            Text(if (total > 0) "Reading files… $done/$total" else "Reading folder…", color = RetroTextSoft)
            Spacer(Modifier.height(8.dp))
            if (total > 0) LinearProgressIndicator({ done / total.toFloat() }, Modifier.fillMaxWidth(), color = RetroAccent, trackColor = RetroStatBg) else LinearProgressIndicator(Modifier.fillMaxWidth(), color = RetroAccent, trackColor = RetroStatBg)
        }
    }
    error?.let { InfoNote(Icons.Filled.ErrorOutline, it.message, RetroDanger, code = it.code) }
    staged?.let { folder ->
        RetroCard {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.Folder, null, tint = RetroAccent)
                Spacer(Modifier.width(8.dp))
                Text(folder.folderName, color = RetroText, style = MaterialTheme.typography.titleMedium)
            }
            MonoText(folder.displayPath, Modifier.padding(vertical = 6.dp))
            InfoRow("Files found", folder.fileCount.toString())
            InfoRow("Total size", formatMb(folder.totalBytes))
            InfoRow("Detected prefix", folder.folderName, mono = true)
            Spacer(Modifier.height(6.dp))
            val complete = folder.fileCount > 1
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(if (complete) Icons.Filled.CheckCircle else Icons.Filled.WarningAmber, null, tint = if (complete) RetroSuccess else RetroGold, modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(6.dp))
                Text(if (complete) "Looks like a complete car model" else "Only one file — a car model is normally many files", color = if (complete) RetroSuccess else RetroGold, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
fun StepValidate(hashing: Pair<Int, Int>?, relativeFiles: List<String>, ignored: List<String>, errors: List<String>) {
    Text("Validate contents", color = RetroText, style = MaterialTheme.typography.titleLarge)
    hashing?.let { (done, total) ->
        RetroCard {
            Text("Hashing files… $done/$total", color = RetroTextSoft)
            Spacer(Modifier.height(8.dp))
            LinearProgressIndicator({ if (total > 0) done / total.toFloat() else 0f }, Modifier.fillMaxWidth(), color = RetroAccent, trackColor = RetroStatBg)
        }
    }
    if (hashing == null && relativeFiles.isNotEmpty()) {
        val groups = CarModelFileGroups.summarize(relativeFiles)
        RetroCard {
            Text("${relativeFiles.size} files hashed · SHA-256", color = RetroTextMuted, style = MaterialTheme.typography.labelMedium)
            groups.forEach { (group, count) -> FileGroupRow(groupIcon(group), group.label, group.extensionLabel, count, ok = group != CarModelFileGroup.OTHER) }
        }
        if (groups.containsKey(CarModelFileGroup.OTHER)) WarningBanner("Unrecognised files included", "${groups.getValue(CarModelFileGroup.OTHER)} files aren't a known RR3 type. They will be installed as-is — go back if they don't belong to this vehicle.")
    }
    if (ignored.isNotEmpty()) WarningBanner(
        "${ignored.size} unexpected ${if (ignored.size == 1) "file" else "files"} ignored",
        "${ignored.take(3).joinToString(", ") { it.substringAfterLast('/') }}${if (ignored.size > 3) " and ${ignored.size - 3} more" else ""} won't be installed.",
    )
    errors.forEach { InfoNote(Icons.Filled.ErrorOutline, it, RetroDanger, code = CarModelErrorCode.IMPORT_VALIDATION_FAILED.code) }
}

private fun groupIcon(group: CarModelFileGroup) = when (group) {
    CarModelFileGroup.MESHES -> Icons.Filled.ViewInAr
    CarModelFileGroup.POINTS -> Icons.Filled.Place
    CarModelFileGroup.LIVERIES -> Icons.Filled.Texture
    CarModelFileGroup.BINARIES -> Icons.Filled.Memory
    CarModelFileGroup.OTHER -> Icons.Filled.HelpOutline
}

@Composable
fun StepTarget(gamePackage: GamePackage, onPackage: (GamePackage) -> Unit, vehicles: List<String>?, vehiclesError: UiError?, query: String, onQuery: (String) -> Unit, selected: String?, onSelect: (String) -> Unit, sourcePath: String, targetPath: String?, inspection: TargetInspection?, alreadyInstalled: Boolean, zeroMatchAccepted: Boolean = false, onZeroMatchAccepted: (Boolean) -> Unit = {}, addonOnVehicle: Boolean = false) {
    Text("Select target vehicle", color = RetroText, style = MaterialTheme.typography.titleLarge)
    SegmentedTabs(listOf("Global (r3_row)", "N. America (r3_na)"), GamePackage.entries.indexOf(gamePackage)) { onPackage(GamePackage.entries[it]) }
    RetroSearchField(query, onQuery, "Search vehicles…", Icons.Filled.Search)
    when {
        vehiclesError != null -> { WarningBanner("Vehicles unavailable", vehiclesError.message); ErrorCodeText(vehiclesError.code) }
        vehicles == null -> RetroCard { Text("Reading RR3 vehicles…", color = RetroTextSoft); Spacer(Modifier.height(8.dp)); LinearProgressIndicator(Modifier.fillMaxWidth(), color = RetroAccent, trackColor = RetroStatBg) }
        else -> {
            val filtered = remember(vehicles, query) { vehicles.filter { it.contains(query.trim(), ignoreCase = true) } }
            if (filtered.isEmpty()) Text("No vehicles match “$query”.", color = RetroTextMuted)
            else LazyColumn(Modifier.fillMaxWidth().heightIn(max = 240.dp).background(RetroSurface, RoundedCornerShape(16.dp)).border(1.dp, RetroBorder, RoundedCornerShape(16.dp))) {
                items(filtered, key = { it }) { name ->
                    Row(Modifier.fillMaxWidth().heightIn(min = 48.dp).clickable { onSelect(name) }.padding(horizontal = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = name == selected, onClick = { onSelect(name) }, colors = RadioButtonDefaults.colors(selectedColor = RetroAccent, unselectedColor = RetroTextMuted))
                        MonoText(name, color = if (name == selected) RetroText else RetroTextSoft, maxLines = 1)
                    }
                }
            }
        }
    }
    if (selected != null && targetPath != null) PathDiffCard(
        sourcePath, targetPath, inspection?.status,
        when (inspection?.status) {
            TargetStatus.FOUND -> "Target found · stock files present (${inspection?.matchingFiles ?: 0} of your files match)"
            TargetStatus.MISSING -> "Target folder not found in this RR3 install"
            TargetStatus.INCOMPATIBLE -> "No stock files match this vehicle"
            null -> "Checking target…"
        },
    )
    if (inspection?.status == TargetStatus.INCOMPATIBLE) {
        WarningBanner("No stock files match this vehicle — is this the right target?", "None of this model's files share a name with the ${inspection.stockFileCount} files in this vehicle. Models that rename files can still be installed: the original folder is backed up and verified first, so it stays reversible.")
        CheckRow("Yes, install to this vehicle anyway", zeroMatchAccepted, onZeroMatchAccepted)
    }
    when {
        addonOnVehicle -> { WarningBanner("Add-on installed here", "An add-on car model lives in this folder. Remove it from Applied before replacing this vehicle."); ErrorCodeText(CarModelErrorCode.REPLACE_ON_ADDON.code) }
        alreadyInstalled -> WarningBanner("Vehicle already modded", "This vehicle already has a car model installed. The original RR3 files are safely backed up.")
    }
}

@Composable
fun StepAddonTarget(gamePackage: GamePackage, onPackage: (GamePackage) -> Unit, folder: String, onFolder: (String) -> Unit, inspection: AddonInspection?, error: UiError?) {
    Text("Choose where to add it", color = RetroText, style = MaterialTheme.typography.titleLarge)
    SegmentedTabs(listOf("Global (r3_row)", "N. America (r3_na)"), GamePackage.entries.indexOf(gamePackage)) { onPackage(GamePackage.entries[it]) }
    RetroField("New car folder", folder, onFolder, placeholder = "e.g. 2001_bmw_m3_gtr_street_addon", mono = true)
    Text("The model is installed into this folder under RR3's vehicles. Letters, numbers, _ and - only.", color = RetroTextMuted, style = MaterialTheme.typography.bodyMedium)
    val valid = CarModelPaths.SAFE_VEHICLE_DIR.matches(folder)
    when {
        !valid -> InfoNote(Icons.Filled.ErrorOutline, if (folder.isBlank()) "Enter a folder name for the new car." else "Use only letters, numbers, _ and - (max 120).", RetroDanger, code = CarModelErrorCode.UNSAFE_VEHICLE_DIRECTORY.code.takeIf { folder.isNotBlank() })
        error != null -> { WarningBanner("Destination unavailable", error.message); ErrorCodeText(error.code) }
        inspection == null -> RetroCard { Text("Checking destination…", color = RetroTextSoft); Spacer(Modifier.height(8.dp)); LinearProgressIndicator(Modifier.fillMaxWidth(), color = RetroAccent, trackColor = RetroStatBg) }
        else -> {
            RetroCard {
                MonoText(inspection.targetPath, Modifier.padding(bottom = 6.dp))
                InfoRow("Folder", if (inspection.exists) "Already exists" else "New — will be created")
                InfoRow("Files added", inspection.newFiles.size.toString(), valueColor = RetroSuccess)
                InfoRow("Existing files overwritten", inspection.overwrites.size.toString(), valueColor = if (inspection.nothingOverwritten) RetroSuccess else RetroGold)
            }
            when {
                inspection.occupiedBy != null -> { WarningBanner("Folder in use", "“${inspection.occupiedBy.modelName}” is installed in this folder. Pick another name, or remove/revert that model first."); ErrorCodeText(CarModelErrorCode.ADDON_TARGET_OCCUPIED.code) }
                !inspection.nothingOverwritten -> WarningBanner("This folder already has some of these files", "${inspection.overwrites.size} existing files would be overwritten. Only those files are backed up and verified first, and they are put back when you remove the add-on. Choose a new folder name to change nothing.")
                else -> InfoNote(Icons.Filled.CheckCircle, "Nothing existing is overwritten — no backup is needed.", RetroSuccess)
            }
        }
    }
}

@Composable
fun StepDetails(form: CarModelDetailsForm, onChange: (CarModelDetailsForm) -> Unit, vehicleDirectory: String, error: UiError?, uploadError: UiError?, uploadProgress: String?, mediaError: String?, onPickCover: () -> Unit, onPickScreenshots: () -> Unit, onRetryPublish: (() -> Unit)? = null, targetLabel: String = "Replaced vehicle") {
    Text("Model details", color = RetroText, style = MaterialTheme.typography.titleLarge)
    RetroField("Model name *", form.name, { onChange(form.copy(name = it.take(80))) }, placeholder = "e.g. BMW M3 GTR Street")
    RetroField("Creator", form.creator, { onChange(form.copy(creator = it.take(60))) })
    RetroField("Description", form.description, { onChange(form.copy(description = it.take(600))) }, singleLine = false)
    RetroField(targetLabel, vehicleDirectory, {}, readOnly = true, mono = true)
    Column {
        Text("Supported packages", color = RetroTextDim, style = MaterialTheme.typography.labelMedium)
        CheckRow("Global (r3_row)", form.supportsRow) { onChange(form.copy(supportsRow = it)) }
        CheckRow("N. America (r3_na)", form.supportsNa) { onChange(form.copy(supportsNa = it)) }
    }
    RetroField("Game version (optional)", form.gameVersion, { onChange(form.copy(gameVersion = it.take(24))) })
    RetroField("License / permission (optional)", form.license, { onChange(form.copy(license = it.take(120))) })
    CheckRow("Publish this model to the Community Hub", form.uploadToCommunityHub) { onChange(form.copy(uploadToCommunityHub = it)) }
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        SecondaryButton(if (form.cover == null) "Add cover" else "Cover added", Modifier.weight(1f), Icons.Filled.Image, onPickCover)
        SecondaryButton(if (form.screenshots.isEmpty()) "Add screenshots" else "${form.screenshots.size} screenshots", Modifier.weight(1f), Icons.Filled.AddPhotoAlternate, onPickScreenshots)
    }
    mediaError?.let { InfoNote(Icons.Filled.ErrorOutline, it, RetroDanger) }
    uploadProgress?.let { InfoNote(Icons.Filled.CloudUpload, it, RetroAccent) }
    InfoNote(Icons.Filled.Lock, if (form.uploadToCommunityHub) "Files and processed media are uploaded, integrity is checked, then the model becomes public. Original local backups and game files are never uploaded." else "This model stays local and is not uploaded to the Community Hub.", RetroAccent)
    error?.let { InfoNote(Icons.Filled.ErrorOutline, it.message, RetroDanger, code = it.code) }
    uploadError?.let {
        InfoNote(Icons.Filled.ErrorOutline, it.message, RetroDanger, code = it.code)
        onRetryPublish?.let { retry -> SecondaryButton("Retry upload and publish", Modifier.fillMaxWidth(), Icons.Filled.Refresh, retry) }
    }
}

@Composable
private fun CheckRow(text: String, checked: Boolean, onChange: (Boolean) -> Unit) {
    Row(Modifier.fillMaxWidth().heightIn(min = 48.dp).clickable { onChange(!checked) }, verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = checked, onCheckedChange = onChange, colors = CheckboxDefaults.colors(checkedColor = RetroAccent, uncheckedColor = RetroTextMuted))
        Text(text, color = RetroTextSoft)
    }
}

@Composable
fun StepBackup(state: BackupCardState?, destination: String, relativeFiles: List<String>, alreadyInstalled: Boolean, uploadError: UiError? = null, uploadProgress: String? = null, onRetry: () -> Unit) {
    Text("Backup & review", color = RetroText, style = MaterialTheme.typography.titleLarge)
    InfoNote(Icons.Filled.Shield, "A verified local backup is required. The original vehicle files are copied and SHA-256 checked before anything is replaced.", RetroAccent)
    uploadProgress?.let { InfoNote(Icons.Filled.CloudUpload, it, RetroAccent) }
    uploadError?.let { InfoNote(Icons.Filled.ErrorOutline, it.message + " The model remains available in your library for retry.", RetroDanger, code = it.code) }
    BackupStatusCard(state ?: BackupCardState.Creating(0, 0), destination, onRetry)
    if (alreadyInstalled) WarningBanner("Vehicle already modded", "This vehicle already has a car model installed. The original RR3 files are safely backed up and will be restored before the new model is applied.")
    SectionHeader("Will be replaced", Icons.Filled.SwapHoriz, trailingCount = relativeFiles.size)
    RetroCard {
        relativeFiles.take(8).forEach { MonoText(it, Modifier.padding(vertical = 3.dp), RetroTextSoft, maxLines = 1) }
        if (relativeFiles.size > 8) Text("+ ${relativeFiles.size - 8} more files", color = RetroTextMuted, style = MaterialTheme.typography.labelMedium)
    }
    val verified = state is BackupCardState.Verified
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(if (verified) Icons.Filled.LockOpen else Icons.Filled.Lock, null, tint = if (verified) RetroSuccess else RetroGold, modifier = Modifier.size(16.dp))
        Spacer(Modifier.width(6.dp))
        Text(if (verified) "Backup verified — apply is now enabled" else "Apply stays locked until the backup is verified", color = if (verified) RetroSuccess else RetroGold, style = MaterialTheme.typography.bodyMedium)
    }
}

/** Add-on counterpart of [StepBackup]: nothing existing is replaced, so there is no whole-folder backup gate. */
@Composable
fun StepAddonReview(inspection: AddonInspection?, error: UiError?, folder: String, gamePackage: GamePackage, manifest: CarModelManifest? = null, uploadError: UiError? = null, uploadProgress: String? = null) {
    Text("Review complete add-on scan", color = RetroText, style = MaterialTheme.typography.titleLarge)
    uploadProgress?.let { InfoNote(Icons.Filled.CloudUpload, it, RetroAccent) }
    uploadError?.let { InfoNote(Icons.Filled.ErrorOutline, it.message + " The model remains available in your library for retry.", RetroDanger, code = it.code) }
    error?.let { InfoNote(Icons.Filled.ErrorOutline, it.message, RetroDanger, code = it.code) }
    if (inspection == null) {
        if (error == null) RetroCard { Text("Checking destination…", color = RetroTextSoft); Spacer(Modifier.height(8.dp)); LinearProgressIndicator(Modifier.fillMaxWidth(), color = RetroAccent, trackColor = RetroStatBg) }
        return
    }
    val files = manifest?.files.orEmpty()
    val byRoot = files.groupingBy { it.destinationRoot }.eachCount()
    val eventWarning = files.any { it.sourcePath.equals("events.dat.nct", true) || it.destinationPath.equals("events.dat.nct", true) }
    val localization = files.filter { it.destinationRoot.name == "DEPOT_ROOT" && (it.destinationPath.substringAfterLast('/').equals("gametext.txt", true) || it.destinationPath.substringAfterLast('/').startsWith("text_", true) || it.destinationPath.substringAfterLast('/').equals("asset_list_base.txtCache.txt", true)) }
    val createdDirectories = buildList {
        if (!inspection.exists) add(folder)
        byRoot.keys.filterNot { it.name == "VEHICLE" }.forEach { add(it.name.lowercase().replace('_', '/')) }
    }.distinct()
    val summary = if (inspection.nothingOverwritten) "Adds ${files.size} files · nothing overwritten · removable later" else "Adds ${files.size} files · overwrites ${inspection.overwrites.size} · one verified transaction backup"
    InfoNote(Icons.Filled.Layers, summary, if (inspection.nothingOverwritten) RetroSuccess else RetroGold)
    if (eventWarning) WarningBanner("events.dat.nct warning", "This file can affect depot event data globally. Review the complete scan before continuing.")
    RetroCard {
        InfoRow("Folder", folder, mono = true)
        InfoRow("Package", "${packageLabel(gamePackage.id)} (${gamePackage.short})")
        InfoRow("Folder status", if (inspection.exists) "Exists" else "New")
        InfoRow("Vehicle files", byRoot[com.retr0.projectretr0.data.carModels.manifest.CarModelDestinationRoot.VEHICLE]?.toString() ?: "0")
        InfoRow("Vehicles/data files", byRoot[com.retr0.projectretr0.data.carModels.manifest.CarModelDestinationRoot.VEHICLES_DATA]?.toString() ?: "0")
        InfoRow("Depot data/global files", ((byRoot[com.retr0.projectretr0.data.carModels.manifest.CarModelDestinationRoot.DEPOT_DATA] ?: 0) + (byRoot[com.retr0.projectretr0.data.carModels.manifest.CarModelDestinationRoot.DEPOT_ROOT] ?: 0)).toString())
        InfoRow("Depot-root localization/cache", localization.size.toString())
        InfoRow("Existing files replaced", inspection.overwrites.size.toString(), valueColor = if (inspection.overwrites.isEmpty()) RetroSuccess else RetroGold)
        InfoRow("New files", inspection.newFiles.size.toString(), valueColor = RetroSuccess)
        InfoRow("New directories", createdDirectories.size.toString(), valueColor = RetroSuccess)
        InfoRow("Local transaction backup", "1 folder${if (inspection.overwrites.isEmpty()) " (manifest only)" else " · overwritten files verified"}", valueColor = RetroAccent)
    }
    if (inspection.overwrites.isNotEmpty()) {
        SectionHeader("Destinations that exist and will be replaced", Icons.Filled.Shield, trailingCount = inspection.overwrites.size)
        RetroCard {
            inspection.overwrites.take(12).forEach { MonoText(it, Modifier.padding(vertical = 3.dp), RetroTextSoft, maxLines = 1) }
            if (inspection.overwrites.size > 12) Text("+ ${inspection.overwrites.size - 12} more files", color = RetroTextMuted, style = MaterialTheme.typography.labelMedium)
        }
        inspection.collisions.groupingBy { it.classification }.eachCount().forEach { (classification, count) ->
            InfoRow(classification.name.lowercase().replaceFirstChar { it.uppercase() }, count.toString(), valueColor = if (classification.name == "DIFFERENT") RetroGold else RetroTextSoft)
        }
    }
    SectionHeader("Files and directories newly created", Icons.Filled.Add, trailingCount = inspection.newFiles.size + createdDirectories.size)
    RetroCard {
        createdDirectories.forEach { MonoText("$it/", Modifier.padding(vertical = 2.dp), RetroAccent, maxLines = 1) }
        inspection.newFiles.take(12).forEach { MonoText(it, Modifier.padding(vertical = 2.dp), RetroTextSoft, maxLines = 1) }
        if (inspection.newFiles.size > 12) Text("+ ${inspection.newFiles.size - 12} more files", color = RetroTextMuted, style = MaterialTheme.typography.labelMedium)
    }
    inspection.occupiedBy?.let { InfoNote(Icons.Filled.ErrorOutline, CarModelErrorCode.ADDON_TARGET_OCCUPIED.userMessage, RetroDanger, code = CarModelErrorCode.ADDON_TARGET_OCCUPIED.code) }
}

@Composable
fun StepResult(outcome: ApplyOutcome?, progress: Pair<Int, Int>?, vehicleDirectory: String, gamePackage: GamePackage, fileCount: Int, onViewBackups: () -> Unit, onRevert: () -> Unit, mode: InstallMode = InstallMode.REPLACE) {
    val addon = mode == InstallMode.ADDON
    when (outcome) {
        null -> {
            Text(if (addon) "Adding model" else "Applying model", color = RetroText, style = MaterialTheme.typography.titleLarge)
            RetroCard {
                val (done, total) = progress ?: (0 to fileCount)
                Text("Copying files… $done/$total", color = RetroTextSoft)
                Spacer(Modifier.height(8.dp))
                LinearProgressIndicator({ if (total > 0) done / total.toFloat() else 0f }, Modifier.fillMaxWidth(), color = RetroAccent, trackColor = RetroStatBg)
                Spacer(Modifier.height(8.dp))
                Text("Don't close the app. Each file is hash-checked after it is copied.", color = RetroTextMuted, style = MaterialTheme.typography.bodyMedium)
            }
        }
        is ApplyOutcome.Success -> {
            ResultHeader(Icons.Filled.Check, RetroSuccess, if (addon) "Model added" else "Model applied")
            RetroCard {
                InfoRow("Files copied", "${outcome.filesCopied}/${outcome.filesCopied}", valueColor = RetroSuccess)
                InfoRow("Hash verification", "Matched", valueColor = RetroSuccess)
                if (addon) InfoRow("Install type", "Add-on") else InfoRow("Original backup", "Kept", valueColor = RetroSuccess)
            }
            RetroCard {
                InfoRow(if (addon) "Folder" else "Vehicle", vehicleDirectory, mono = true)
                InfoRow("Package", "${packageLabel(gamePackage.id)} (${gamePackage.short})")
                InfoRow(if (addon) "Added" else "Applied", formatDate(outcome.appliedAt))
            }
            if (addon) SecondaryButton("Revert add-on", Modifier.fillMaxWidth(), Icons.Filled.Delete, onRevert)
            else Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                SecondaryButton("View backup", Modifier.weight(1f), Icons.Filled.Backup, onViewBackups)
                SecondaryButton("Revert", Modifier.weight(1f), Icons.Filled.Restore, onRevert)
            }
        }
        is ApplyOutcome.Failed -> {
            ResultHeader(Icons.Filled.Close, RetroDanger, "Install failed")
            InfoNote(Icons.Filled.ErrorOutline, outcome.reason, RetroDanger, code = outcome.code)
            when {
                !outcome.gameFilesTouched -> InfoNote(Icons.Filled.Shield, "Nothing in the game was changed. Your original files are safe.", RetroSuccess)
                outcome.originalRestored && addon -> InfoNote(Icons.Filled.Shield, "The files this add-on copied were removed and any overwritten file was put back and re-checked.", RetroSuccess)
                outcome.originalRestored -> InfoNote(Icons.Filled.Shield, "Your original files are safe — they were restored from the verified backup and re-checked.", RetroSuccess)
                addon -> {
                    InfoNote(Icons.Filled.WarningAmber, "Some add-on files were copied and the automatic clean-up did not finish. Remove the add-on to delete them.", RetroGold)
                    SmallActionButton("Remove add-on", Modifier.fillMaxWidth(), Icons.Filled.Delete, color = RetroDanger, onClick = onRevert)
                }
                else -> {
                    InfoNote(Icons.Filled.WarningAmber, "Some game files were changed and the automatic restore did not finish. Your verified backup is intact — restore the original now.", RetroGold)
                    SmallActionButton("Restore original", Modifier.fillMaxWidth(), Icons.Filled.Restore, color = RetroDanger, onClick = onRevert)
                }
            }
        }
    }
}

@Composable
private fun ResultHeader(icon: androidx.compose.ui.graphics.vector.ImageVector, color: androidx.compose.ui.graphics.Color, title: String) {
    Column(Modifier.fillMaxWidth().padding(vertical = 10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Box(Modifier.size(64.dp).background(color.copy(alpha = .14f), CircleShape).border(1.5.dp, color, CircleShape), contentAlignment = Alignment.Center) { Icon(icon, null, tint = color, modifier = Modifier.size(32.dp)) }
        Spacer(Modifier.height(10.dp))
        Text(title, color = color, style = MaterialTheme.typography.headlineMedium)
    }
}
