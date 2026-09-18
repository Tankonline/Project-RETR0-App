package com.example.projectretr0installer.ui.skins

import android.util.Log
import com.example.projectretr0installer.data.skins.SkinApiException
import com.example.projectretr0installer.data.skins.SkinDraft
import com.example.projectretr0installer.data.skins.SkinRepository
import com.example.projectretr0installer.data.skins.SkinUploadImage
import com.example.projectretr0installer.data.skins.local.LocalSkinImport
import com.example.projectretr0installer.data.skins.local.LocalSkinRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.File
import java.util.UUID

class SkinImportViewModel(
    private val local: LocalSkinRepository,
    private val community: SkinRepository,
) {
    private val mutableProgress = MutableStateFlow<ImportProgress>(ImportProgress.Idle)
    val progress: StateFlow<ImportProgress> = mutableProgress.asStateFlow()

    suspend fun finish(state: ImportState, title: String, creator: String, description: String): Result<String> = runCatching {
        val source = requireNotNull(state.source) { "Choose a skin file" }
        requireNotNull(state.target) { "Choose a target" }
        require(title.isNotBlank() && creator.isNotBlank()) { "Name and creator are required" }
        val target = requireNotNull(state.target) { "Choose a target" }
        // The target browser already validates an exact source/target filename match.
        // Parse the format from that validated filename, rather than the earlier detected
        // value, which can include the `.dds.z` extension for unrecognised formats.
        val targetMetadata = parseTargetMetadata(target.fileName)
        val request = LocalSkinImport(
            source = source, title = title, creator = creator, vehicle = state.vehicle,
            // The selected RR3 target supplies the canonical filename and target
            // metadata. This intentionally decouples a user-provided source name
            // from the game destination it will replace.
            carDirectory = carDirectoryFromTargetName(target.fileName),
            slotKey = targetMetadata.slotKey, target = target, gamePackage = state.gamePackage.id, images = state.images,
        )
        mutableProgress.value = ImportProgress.Running(0, "Preparing import")
        val saved = local.saveImport(request) { percent, message -> mutableProgress.value = ImportProgress.Running(percent * if (state.upload) 60 / 100 else percent, message) }
        if (state.upload) {
            val screenshotCount = state.images.count { it.role == SkinImageRole.SCREENSHOT }
            Log.i("SKIN_IMPORT_UPLOAD", "localId=${saved.id} imageCount=${state.images.size} screenshotCount=$screenshotCount")
            val cover = requireNotNull(state.images.firstOrNull { it.role == SkinImageRole.COVER }) { "A cover is required" }
            val savedSkinDirectory = requireNotNull(File(saved.binaryPath).parentFile) { "The saved skin directory is unavailable." }
            val savedImagesDirectory = savedSkinDirectory.resolve("images")
            val savedCover = savedImagesDirectory.resolve("cover.webp").also {
                require(it.isFile) { "The saved cover image is missing. Add it again." }
            }
            mutableProgress.value = ImportProgress.Running(65, "Creating community draft")
            community.createUploadAndPublish(
                SkinDraft(title, creator, description, saved.carDirectory, saved.slotKey, targetMetadata.textureFormat, target.fileName, saved.binarySize, saved.binarySha256),
                "import-${UUID.randomUUID()}", File(saved.binaryPath),
                savedCover,
                cover.mimeType,
                state.images.filter { it.role == SkinImageRole.SCREENSHOT }.mapIndexed { index, image ->
                    Log.i("SKIN_IMPORT_UPLOAD_SCREENSHOT", "localId=${saved.id} position=${index + 1} bytes=${image.sizeBytes}")
                    SkinUploadImage(
                        index + 1,
                        savedImagesDirectory.resolve("screenshot-${index + 1}.webp").also {
                            require(it.isFile) { "Saved screenshot ${index + 1} is missing. Add it again." }
                        },
                        image.mimeType,
                        "Screenshot ${index + 1}",
                    )
                },
            )
        }
        mutableProgress.value = ImportProgress.Complete(saved.id, state.apply)
        saved.id
    }.onFailure { error ->
        Log.e(
            "SKIN_IMPORT",
            "Import failed: type=${error::class.java.simpleName}; " +
                "apiStatus=${(error as? SkinApiException)?.statusCode}; " +
                "apiCode=${(error as? SkinApiException)?.errorCode}; " +
                "requestId=${(error as? SkinApiException)?.requestId}",
            error,
        )
        mutableProgress.value = ImportProgress.Failed(error.toImportDiagnostic())
    }
}

private fun Throwable.toImportDiagnostic(): String = when (this) {
    is SkinApiException -> buildString {
        append(errorCode ?: "community_request_failed")
        statusCode?.let { append(" (HTTP $it)") }
        append(": ")
        append(message ?: "The community service rejected the request.")
        requestId?.let { append(" [requestId: $it]") }
    }
    else -> "${this::class.java.simpleName}: ${message ?: "Import failed"}"
}

internal data class SkinTargetMetadata(val slotKey: String, val textureFormat: String)

private val supportedSlotSuffix = Regex("_(?:ext|int)(?:_[a-z0-9_]+|[0-9]+)$", RegexOption.IGNORE_CASE)

internal fun carDirectoryFromTargetName(fileName: String): String {
    val stem = fileName.dropLast(".dds.z".length).substringBeforeLast('.')
    val slotIndex = supportedSlotSuffix.find(stem)?.range?.first
        ?: error("The filename doesn't match a supported RR3 slot")
    return stem.substring(0, slotIndex).lowercase()
}

internal fun parseTargetMetadata(fileName: String): SkinTargetMetadata {
    require(fileName.endsWith(".dds.z", ignoreCase = true)) { "The filename must end in .dds.z" }
    val stem = fileName.dropLast(".dds.z".length)

    // RR3 uses a dot-separated compression suffix: `<slot>.<format>.dds.z`,
    // for example `2014_audi_r18etronquattro_ext_one.etc.dds.z`.
    val formatSeparator = stem.lastIndexOf('.')
    require(formatSeparator > 0 && formatSeparator < stem.lastIndex) {
        "The filename doesn't match the RR3 <slot>.<format>.dds.z convention"
    }
    val slotAndPrefix = stem.substring(0, formatSeparator)
    val textureFormat = stem.substring(formatSeparator + 1)
    val slotKey = supportedSlotSuffix.find(slotAndPrefix)?.value?.removePrefix("_")
        ?: error("The filename doesn't match a supported RR3 slot")
    return SkinTargetMetadata(slotKey.lowercase(), textureFormat.lowercase())
}

sealed interface ImportProgress {
    data object Idle : ImportProgress
    data class Running(val percent: Int, val message: String) : ImportProgress
    data class Complete(val localId: String, val enqueueApply: Boolean) : ImportProgress
    data class Failed(val message: String) : ImportProgress
}
