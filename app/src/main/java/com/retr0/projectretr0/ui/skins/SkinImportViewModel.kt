package com.retr0.projectretr0.ui.skins

import android.util.Log
import com.retr0.projectretr0.data.skins.SkinApiException
import com.retr0.projectretr0.data.skins.SkinDraft
import com.retr0.projectretr0.data.skins.SkinRepository
import com.retr0.projectretr0.data.skins.SkinUploadImage
import com.retr0.projectretr0.data.skins.local.LocalSkinImport
import com.retr0.projectretr0.data.skins.local.LocalSkinRepository
import com.retr0.projectretr0.data.skins.manifest.SkinManifestStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.File
import java.util.Locale
import java.util.UUID

class SkinImportViewModel(
    private val local: LocalSkinRepository,
    private val community: SkinRepository,
) {
    private companion object {
        const val TAG = "SKIN_IMPORT"
        const val CODE_FINISH_START = "SKIN_IMPORT_E001"
        const val CODE_LOCAL_SAVE_FAILED = "SKIN_IMPORT_E002"
        const val CODE_COVER_STATE_MISSING = "SKIN_IMPORT_E003"
        const val CODE_SAVED_DIRECTORY_MISSING = "SKIN_IMPORT_E004"
        const val CODE_COVER_FILE_MISSING = "SKIN_IMPORT_E005"
        const val CODE_SCREENSHOT_FILE_MISSING = "SKIN_IMPORT_E006"
        const val CODE_COMMUNITY_UPLOAD_FAILED = "SKIN_IMPORT_E007"
        const val CODE_FINISH_FAILED = "SKIN_IMPORT_E008"
    }

    private val mutableProgress = MutableStateFlow<ImportProgress>(ImportProgress.Idle)
    val progress: StateFlow<ImportProgress> = mutableProgress.asStateFlow()

    suspend fun finish(state: ImportState, title: String, creator: String, description: String): Result<String> = runCatching {
        Log.i(TAG, "code=$CODE_FINISH_START stage=finish_start upload=${state.upload} imageCount=${state.images.size}")
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
        val saved = try {
            local.saveImport(request) { percent, message -> mutableProgress.value = ImportProgress.Running(percent * if (state.upload) 60 / 100 else percent, message) }
        } catch (error: Throwable) {
            Log.e(TAG, "code=$CODE_LOCAL_SAVE_FAILED stage=local_save_failed type=${error::class.java.simpleName} message=${error.message}", error)
            throw error
        }
        if (state.upload) {
            val screenshotCount = state.images.filter { it.role == SkinImageRole.SCREENSHOT }.distinctBy { it.id }.size
            Log.i("SKIN_IMPORT_UPLOAD", "localId=${saved.id} imageCount=${state.images.size} screenshotCount=$screenshotCount")
            val cover = state.images.firstOrNull { it.role == SkinImageRole.COVER }
                ?: error("$CODE_COVER_STATE_MISSING: A cover is required")
            val savedSkinDirectory = File(saved.binaryPath).parentFile
                ?: error("$CODE_SAVED_DIRECTORY_MISSING: The saved skin directory is unavailable.")
            val savedImagesDirectory = savedSkinDirectory.resolve("images")
            val savedScreenshotsDirectory = savedSkinDirectory.resolve("screenshots")
            val savedCover = savedImagesDirectory.resolve("cover.webp").also {
                if (!it.isFile) {
                    Log.e(TAG, "code=$CODE_COVER_FILE_MISSING stage=cover_validation path=${it.absolutePath} exists=${it.exists()} readable=${it.canRead()} length=${it.length()} savedBinary=${saved.binaryPath}")
                    error("$CODE_COVER_FILE_MISSING: The saved cover image is missing. Add it again.")
                }
                Log.i(TAG, "code=SKIN_IMPORT_I001 stage=cover_validation path=${it.absolutePath} bytes=${it.length()} sourceImage=${cover.file.absolutePath}")
            }
            mutableProgress.value = ImportProgress.Running(65, "Creating community draft")
            try {
                community.createUploadAndPublish(
                    SkinDraft(title, creator, description, saved.carDirectory, saved.slotKey, targetMetadata.textureFormat, target.fileName, saved.binarySize, saved.binarySha256),
                "import-${UUID.randomUUID()}", File(saved.binaryPath),
                savedCover,
                cover.mimeType,
                state.images.filter { it.role == SkinImageRole.SCREENSHOT }.distinctBy { it.id }.mapIndexed { index, image ->
                    Log.i("SKIN_IMPORT_UPLOAD_SCREENSHOT", "localId=${saved.id} position=${index + 1} bytes=${image.sizeBytes}")
                    SkinUploadImage(
                        index + 1,
                        savedScreenshotsDirectory.resolve("${String.format(Locale.ROOT, "%02d", index + 1)}.webp").also {
                            if (!it.isFile) {
                            Log.e(TAG, "code=$CODE_SCREENSHOT_FILE_MISSING stage=screenshot_validation position=${index + 1} path=${it.absolutePath} exists=${it.exists()} readable=${it.canRead()} length=${it.length()}")
                            error("$CODE_SCREENSHOT_FILE_MISSING: Saved screenshot ${index + 1} is missing. Add it again.")
                        }
                        },
                        image.mimeType,
                        "Screenshot ${index + 1}",
                    )
                },
                    manifest = SkinManifestStore.read(savedSkinDirectory)?.toJson()?.toString(),
                )
            } catch (error: Throwable) {
                Log.e(TAG, "code=$CODE_COMMUNITY_UPLOAD_FAILED stage=community_upload_failed type=${error::class.java.simpleName} message=${error.message}", error)
                throw error
            }
        }
        mutableProgress.value = ImportProgress.Complete(saved.id, state.apply)
        saved.id
    }.onFailure { error ->
        val code = error.message?.substringBefore(":")?.takeIf { it.startsWith("SKIN_IMPORT_") } ?: CODE_FINISH_FAILED
        Log.e(
            TAG,
            "code=$code stage=finish_failed type=${error::class.java.simpleName}; " +
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
