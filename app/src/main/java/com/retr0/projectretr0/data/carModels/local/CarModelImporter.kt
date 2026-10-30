package com.retr0.projectretr0.data.carModels.local

import android.content.Context
import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import com.retr0.projectretr0.data.carModels.CarModelErrorCode
import com.retr0.projectretr0.data.carModels.CarModelException
import com.retr0.projectretr0.data.carModels.CarModelLog
import com.retr0.projectretr0.data.carModels.asCarModelException
import com.retr0.projectretr0.data.carModels.manifest.CarModelFolderScanner
import com.retr0.projectretr0.data.carModels.manifest.CarModelManifest
import com.retr0.projectretr0.data.carModels.manifest.CarModelManifestValidator
import com.retr0.projectretr0.data.carModels.manifest.CarModelLimits
import com.retr0.projectretr0.data.carModels.manifest.PackageMetadata
import com.retr0.projectretr0.data.carModels.manifest.TargetVehicle
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import java.io.File
import java.security.MessageDigest
import java.util.UUID
import kotlin.coroutines.coroutineContext

/** A user-picked car-model folder copied into app-private staging so it can be hashed and later installed. */
data class StagedCarModel(
    val root: File,
    val folderName: String,
    val displayPath: String,
    val fileCount: Int,
    val totalBytes: Long,
    val ignoredFiles: List<String>,
    val effectiveRoot: String = "",
)

data class CarModelMediaAsset(
    val file: File,
    val mimeType: String,
    val sizeBytes: Long,
    val sha256: String,
    val width: Int,
    val height: Int,
)

data class CarModelDetailsInput(
    val name: String,
    val creator: String,
    val description: String,
    val gameVersion: String,
    val license: String,
    val supportedPackages: List<String>,
    val vehicleDirectory: String,
    val cover: CarModelMediaAsset?,
    val screenshots: List<CarModelMediaAsset>,
) {
    val mediaValid: Boolean get() = screenshots.size <= CarModelLimits.MAX_SCREENSHOTS &&
        (cover == null || (cover.mimeType == "image/webp" && cover.sizeBytes <= CarModelLimits.MAX_COVER_BYTES)) &&
        screenshots.all { it.mimeType == "image/webp" && it.sizeBytes <= CarModelLimits.MAX_SCREENSHOT_BYTES }
    val publishReady: Boolean get() = cover != null && mediaValid
}

/** Folder pick → staging copy → manifest hashing → local library. Never touches game files. */
class CarModelImporter(private val context: Context, private val store: CarModelStore) {

    suspend fun stage(treeUri: Uri, onProgress: (Int, Int) -> Unit): Result<StagedCarModel> = withContext(Dispatchers.IO) {
        CarModelLog.info("CM-IMPORT-START", "staging picked folder")
        coded(CarModelErrorCode.IMPORT_READ_FAILED) {
            store.stagingDir.deleteRecursively()
            val tree = DocumentFile.fromTreeUri(context, treeUri) ?: fail(CarModelErrorCode.IMPORT_FOLDER_UNOPENABLE)
            ensure(tree.isDirectory, CarModelErrorCode.IMPORT_NOT_A_FOLDER)
            val folderName = tree.name?.trim().orEmpty().replace('/', '_').replace('\\', '_').ifBlank { "car_model" }
            val sources = mutableListOf<Pair<String, DocumentFile>>()
            val ignored = mutableListOf<String>()
            collect(tree, "", 0, sources, ignored)
            ensure(sources.isNotEmpty(), CarModelErrorCode.IMPORT_NO_FILES)
            val stagedRoot = File(store.stagingDir, "${UUID.randomUUID()}/$folderName")
            var total = 0L
            sources.forEachIndexed { index, (relative, document) ->
                coroutineContext.ensureActive()
                ensure(document.length() > 0, CarModelErrorCode.IMPORT_READ_FAILED) { "Car-model files must not be empty: $relative" }
                ensure(document.length() <= CarModelLimits.MAX_FILE_BYTES, CarModelErrorCode.IMPORT_TOO_LARGE) { "Car-model files must be ${CarModelLimits.MAX_FILE_BYTES / 1024 / 1024} MiB or smaller." }
                val destination = File(stagedRoot, relative)
                destination.parentFile?.mkdirs()
                val input = context.contentResolver.openInputStream(document.uri) ?: fail(CarModelErrorCode.IMPORT_READ_FAILED, "Unable to read $relative")
                input.use { stream -> destination.outputStream().use { output -> total += stream.copyTo(output) } }
                ensure(total <= CarModelLimits.MAX_TOTAL_BYTES, CarModelErrorCode.IMPORT_TOO_LARGE) { "Car models must be ${CarModelLimits.MAX_TOTAL_BYTES / 1024 / 1024} MiB or smaller." }
                onProgress(index + 1, sources.size)
                CarModelLog.step("staged ${index + 1}/${sources.size} $relative")
            }
            StagedCarModel(stagedRoot, folderName, treeUri.lastPathSegment?.substringAfter(':')?.ifBlank { null } ?: folderName, sources.size, total, ignored)
        }.onSuccess { CarModelLog.info("CM-IMPORT-OK", "staged ${it.fileCount} files, ${it.totalBytes} bytes, ${it.ignoredFiles.size} ignored (folder=${it.folderName})") }
            .onFailure { error -> store.stagingDir.deleteRecursively(); logFailure("staging failed", error) }
    }

    suspend fun scan(staged: StagedCarModel, onProgress: (Int, Int) -> Unit): CarModelFolderScanner.ScanResult = withContext(Dispatchers.IO) {
        CarModelFolderScanner.scan(staged.root, onFileHashed = onProgress).also { result ->
            if (result.report.isValid) CarModelLog.info("CM-IMPORT-OK", "validated ${result.manifest.files.size} files, SHA-256 hashed (folder=${staged.folderName})")
            else CarModelLog.fail(CarModelErrorCode.IMPORT_VALIDATION_FAILED, "validation failed: ${result.report.errors.joinToString("; ")} (folder=${staged.folderName})")
        }
    }

    fun discardStaging() { store.stagingDir.deleteRecursively() }

    suspend fun saveToLibrary(staged: StagedCarModel, scanned: CarModelManifest, details: CarModelDetailsInput): Result<LocalCarModel> = withContext(Dispatchers.IO) {
        coded(CarModelErrorCode.IMPORT_SAVE_FAILED) {
            ensure(details.name.isNotBlank(), CarModelErrorCode.IMPORT_NAME_REQUIRED)
            ensure(CarModelPaths.SAFE_VEHICLE_DIR.matches(details.vehicleDirectory), CarModelErrorCode.IMPORT_TARGET_REQUIRED)
            val id = UUID.randomUUID().toString()
            val home = store.libraryDir(id)
            val files = File(home, "files")
            home.mkdirs()
            ensure(staged.root.renameTo(files) || (staged.root.copyRecursively(files, overwrite = true) && staged.root.deleteRecursively()), CarModelErrorCode.IMPORT_SAVE_FAILED)
            val manifest = scanned.copy(
                metadata = PackageMetadata(details.name.trim(), description = details.description.trim(), creator = details.creator.trim()),
                packageId = details.supportedPackages.firstOrNull().orEmpty(),
                supportedVersions = listOfNotNull(details.gameVersion.trim().takeIf { it.isNotEmpty() }),
                supportedGamePackages = details.supportedPackages,
                target = TargetVehicle(details.vehicleDirectory, details.vehicleDirectory),
            )
            try {
                CarModelManifestValidator.validate(manifest)
            } catch (error: IllegalArgumentException) {
                throw CarModelException(CarModelErrorCode.IMPORT_INVALID_MANIFEST, error.message ?: CarModelErrorCode.IMPORT_INVALID_MANIFEST.userMessage, error)
            }
            ensure(details.mediaValid, CarModelErrorCode.IMPORT_SAVE_FAILED) { "Cover is required and media must be processed WebP within Community Hub limits." }
            val model = LocalCarModel(
                id = id,
                name = details.name.trim(),
                creator = details.creator.trim(),
                description = details.description.trim(),
                gameVersion = details.gameVersion.trim(),
                license = details.license.trim(),
                supportedPackages = details.supportedPackages,
                manifest = manifest,
                filesRoot = files.absolutePath,
                coverPath = details.cover?.let { copyMedia(it.file, File(home, "media/cover.webp")) },
                screenshotPaths = details.screenshots.take(CarModelLimits.MAX_SCREENSHOTS).mapIndexedNotNull { index, asset -> copyMedia(asset.file, File(home, "media/screenshot_${index + 1}.webp")) },
                ignoredFiles = staged.ignoredFiles,
                media = listOfNotNull(details.cover?.let { CarModelMediaRecord("cover", "media/cover.webp", it.mimeType, it.sizeBytes, sha256(File(home, "media/cover.webp"))) }) + details.screenshots.take(CarModelLimits.MAX_SCREENSHOTS).mapIndexed { index, asset -> CarModelMediaRecord("screenshot", "media/screenshot_${index + 1}.webp", asset.mimeType, asset.sizeBytes, sha256(File(home, "media/screenshot_${index + 1}.webp"))) },
                importedAt = System.currentTimeMillis(),
            )
            store.update { state -> state.copy(models = state.models + model) }
            store.log(CarModelActivityKind.IMPORT, model.name, "${model.fileCount} files imported to your library", true)
            discardStaging()
            model
        }.onSuccess { CarModelLog.info("CM-IMPORT-OK", "saved to library: ${it.fileCount} files ${CarModelLog.where(null, it.vehicleDirectory)}") }
            .onFailure { error ->
                logFailure("save to library failed", error)
                store.log(CarModelActivityKind.IMPORT, details.name.ifBlank { staged.folderName }, "[${error.asCarModelException().error.code}] ${error.message}", false)
            }
    }

    private fun collect(directory: DocumentFile, prefix: String, depth: Int, sources: MutableList<Pair<String, DocumentFile>>, ignored: MutableList<String>) {
        ensure(depth <= MAX_DEPTH, CarModelErrorCode.IMPORT_TOO_DEEP)
        directory.listFiles().forEach { child ->
            val name = child.name ?: return@forEach
            when {
                child.isDirectory -> if (!name.startsWith(".")) collect(child, "$prefix$name/", depth + 1, sources, ignored)
                !child.isFile -> Unit
                CarModelFileGroups.isIgnored(name) -> ignored += "$prefix$name"
                else -> {
                    val relative = try {
                        CarModelManifestValidator.validateRelativePath("$prefix$name")
                    } catch (error: IllegalArgumentException) {
                        throw CarModelException(CarModelErrorCode.UNSAFE_PATH, error.message ?: CarModelErrorCode.UNSAFE_PATH.userMessage, error)
                    }
                    sources += relative to child
                    ensure(sources.size <= CarModelLimits.MAX_FILES, CarModelErrorCode.IMPORT_TOO_MANY_FILES) { "Car models can contain at most ${CarModelLimits.MAX_FILES} files." }
                }
            }
        }
    }

    private fun sha256(file: File): String = MessageDigest.getInstance("SHA-256").digest(file.readBytes()).joinToString("") { "%02x".format(it) }

    private fun copyMedia(uri: Uri, destination: File): String? = runCatching {
        destination.parentFile?.mkdirs()
        val input = requireNotNull(context.contentResolver.openInputStream(uri))
        input.use { stream -> destination.outputStream().use { output -> stream.copyTo(output) } }
        destination.absolutePath
    }.getOrNull()

    private fun copyMedia(source: File, destination: File): String? = runCatching {
        destination.parentFile?.mkdirs()
        source.inputStream().use { input -> destination.outputStream().use { output -> input.copyTo(output) } }
        destination.absolutePath
    }.getOrNull()

    private fun fail(code: CarModelErrorCode, message: String = code.userMessage): Nothing = throw CarModelException(code, message)
    private inline fun ensure(condition: Boolean, code: CarModelErrorCode, message: () -> String = { code.userMessage }) { if (!condition) fail(code, message()) }
    private fun logFailure(what: String, error: Throwable) { val coded = error.asCarModelException(); CarModelLog.fail(coded.error, "$what: ${coded.message}") }
    /** Like runCatching, but cancellation propagates and every failure carries a code ([fallback] if it had none). */
    private inline fun <T> coded(fallback: CarModelErrorCode, block: () -> T): Result<T> = try {
        Result.success(block())
    } catch (cancelled: CancellationException) {
        throw cancelled
    } catch (error: Throwable) {
        Result.failure(error.asCarModelException(fallback))
    }

    private companion object {
        const val MAX_DEPTH = 6
    }
}
