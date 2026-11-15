package com.retr0.projectretr0.data.carModels.local

import com.retr0.projectretr0.data.carModels.CarModelDownloadPlan
import com.retr0.projectretr0.data.carModels.CarModelDownloadSource
import com.retr0.projectretr0.data.carModels.CarModelErrorCode
import com.retr0.projectretr0.data.carModels.CarModelException
import com.retr0.projectretr0.data.carModels.CarModelHttpException
import com.retr0.projectretr0.data.carModels.CarModelLog
import com.retr0.projectretr0.data.carModels.CommunityCarModel
import com.retr0.projectretr0.data.carModels.asCarModelException
import com.retr0.projectretr0.data.carModels.manifest.CarModelLimits
import com.retr0.projectretr0.data.carModels.manifest.CarModelManifest
import com.retr0.projectretr0.data.carModels.manifest.CarModelManifestValidator
import com.retr0.projectretr0.data.carModels.manifest.PackageMetadata
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.security.MessageDigest
import java.util.UUID

/**
 * The single owner of the Community Hub "download → library" transaction.
 *
 * The repository ([CarModelDownloadSource]) only moves bytes. This class, in one awaited call:
 *  1. fetches the download plan (file list with sizes/SHA-256, cover and screenshot routes);
 *  2. checks free space, then downloads manifest, model files and media into `<remoteId>.partial/`;
 *     each file is written to `.part`, size- and SHA-256-verified, then renamed — so a verified file
 *     left by an interrupted attempt (network loss, cancellation, process death) is reused, not re-fetched;
 *  3. validates the manifest against every file;
 *  4. moves `files/` and `media/` into `library/<localId>/` (rename, or verified copy), swapping out any
 *     previous copy only after the new one is complete;
 *  5. persists the [LocalCarModel] (remoteId, ADDON/REPLACE from the remote manifest, absolute media paths)
 *     and updates the StateFlow — and only then returns success, so the UI may navigate.
 *
 * Network failures keep the partial directory for resume; corrupt downloads delete it. Model files are
 * mandatory, media is best-effort (a missing image never blocks the model, and is retried later by
 * [refreshMedia]). The same remoteId always maps to the same local id.
 */
class CommunityCarModelInstaller(
    private val store: CarModelStore,
    private val downloadsRoot: File,
    private val source: CarModelDownloadSource,
    private val moveDirectory: (from: File, to: File) -> Boolean = { from, to -> from.renameTo(to) },
    private val clock: () -> Long = System::currentTimeMillis,
    private val maxAttempts: Int = 3,
    private val retryDelayMs: (attempt: Int) -> Long = { attempt -> 750L * attempt },
    private val freeSpace: (File) -> Long = { it.usableSpace },
) {
    private val mutex = Mutex()
    private val mediaAttempted = mutableSetOf<String>()

    /** A verified image ready to be moved into `library/<id>/media/`. */
    internal data class DownloadedMedia(val role: String, val position: Int, val file: File, val mimeType: String, val sha256: String)

    suspend fun downloadAndImport(model: CommunityCarModel, onProgress: (completedBytes: Long, totalBytes: Long) -> Unit = { _, _ -> }): Result<LocalCarModel> = mutex.withLock {
        val partial = File(downloadsRoot, "${safeName(model.id)}.partial")
        var keepPartial = true
        CarModelLog.info("CM-IMPORT-DOWNLOAD-START", "model=${model.id} partial=${partial.absolutePath} resume=${partial.isDirectory}")
        try {
            withContext(Dispatchers.IO) { cleanupDownloads(keep = partial) }
            val plan = retrying("plan model=${model.id}") { source.downloadPlan(model) }
            CarModelLog.info("CM-DOWNLOAD-START", "model=${model.id} files=${plan.files.size} bytes=${plan.modelBytes} cover=${plan.cover != null} screenshots=${plan.screenshots.size}")
            val filesDir = File(partial, "files")
            val mediaDir = File(partial, "media")
            val verified = HashMap<String, String>()
            val progress = MonotonicProgress(plan.modelBytes, onProgress)

            withContext(Dispatchers.IO) {
                filesDir.mkdirs()
                val missing = plan.files.filterNot { entry -> alreadyVerified(filesDir, entry)?.also { verified[entry.path] = it } != null }
                val needed = missing.sumOf { it.sizeBytes } + SPACE_MARGIN_BYTES
                val available = freeSpace(downloadsRoot)
                if (available < needed) throw CarModelException(CarModelErrorCode.HUB_DOWNLOAD_NO_SPACE, "Not enough free storage: ${needed / MB} MB needed, ${available / MB} MB available.")
                if (verified.isNotEmpty()) CarModelLog.info("CM-DOWNLOAD-RESUME", "model=${model.id} reused=${verified.size}/${plan.files.size}")
            }
            progress.base(plan.files.filter { it.path in verified }.sumOf { it.sizeBytes })

            // Manifest (small, always re-fetched so it cannot be stale).
            val manifestFile = File(filesDir, "manifest.json")
            downloadVerified(plan.manifestUrl, manifestFile, null, plan.manifestSha256) { }
            CarModelLog.info("CM-DOWNLOAD-MANIFEST", "model=${model.id} bytes=${manifestFile.length()} shaChecked=${plan.manifestSha256 != null}")

            plan.files.forEachIndexed { index, entry ->
                if (entry.path in verified) return@forEachIndexed
                val target = contained(filesDir, entry.path)
                verified[entry.path] = downloadVerified(entry.url, target, entry.sizeBytes, entry.sha256) { bytes -> progress.current(bytes) }
                progress.commit(entry.sizeBytes)
                CarModelLog.info("CM-DOWNLOAD-FILE", "model=${model.id} ${index + 1}/${plan.files.size} path=${entry.path} bytes=${entry.sizeBytes}")
            }

            val manifest = withContext(Dispatchers.IO) { verifyDownload(filesDir, verified) }
            CarModelLog.info("CM-DOWNLOAD-VERIFY", "model=${model.id} files=${manifest.files.size} mode=${manifest.installMode} target=${manifest.target.vehicleDirectory}")
            val media = downloadMedia(plan, mediaDir)
            CarModelLog.info("CM-DOWNLOAD-COMPLETE", "model=${model.id} files=${plan.files.size} media=${media.size}")

            val local = withContext(Dispatchers.IO) { importVerified(model, filesDir, manifest, media) }
            keepPartial = false
            CarModelLog.info("CM-IMPORT-DOWNLOAD-COMPLETE", "model=${model.id} localId=${local.id} remoteId=${local.remoteId} library=${local.filesRoot} mode=${local.manifest.installMode} cover=${local.coverPath != null} screenshots=${local.screenshotPaths.size}")
            Result.success(local)
        } catch (cancelled: CancellationException) {
            CarModelLog.info("CM-DOWNLOAD-CANCELLED", "model=${model.id} partialKept=true")
            throw cancelled
        } catch (error: Throwable) {
            val coded = error.asCarModelException(if (error is IOException) CarModelErrorCode.HUB_DOWNLOAD_FAILED else CarModelErrorCode.IMPORT_SAVE_FAILED)
            // A corrupt or unsafe package must not be resumed; a network failure should be.
            if (coded.error in CORRUPT_CODES) keepPartial = false
            CarModelLog.fail(coded.error, "CM-DOWNLOAD-FAIL model=${model.id} partialKept=$keepPartial cause=${error.javaClass.simpleName}: ${error.message}")
            runCatching { store.log(CarModelActivityKind.IMPORT, model.name, "[${coded.error.code}] Download failed: ${coded.message}", false) }
            Result.failure(coded)
        } finally {
            if (!keepPartial) withContext(kotlinx.coroutines.NonCancellable + Dispatchers.IO) { partial.deleteRecursively() }
        }
    }

    /**
     * Downloads cover/screenshots for a library model that came from the Hub without them (models
     * downloaded before media support). Attempted at most once per model per process.
     */
    suspend fun refreshMedia(localId: String, details: suspend (remoteId: String) -> CommunityCarModel): Result<LocalCarModel> = mutex.withLock {
        val local = store.current.model(localId) ?: return@withLock Result.failure(CarModelException(CarModelErrorCode.IMPORT_SAVE_FAILED, "Car model is no longer in the library"))
        val remoteId = local.remoteId ?: return@withLock Result.success(local)
        if (local.coverPath?.let { File(it).isFile } == true || !mediaAttempted.add(localId)) return@withLock Result.success(local)
        val temp = File(downloadsRoot, "${safeName(remoteId)}.media-${UUID.randomUUID()}")
        try {
            CarModelLog.info("CM-IMPORT-MEDIA", "backfill start localId=$localId remoteId=$remoteId")
            val remote = retrying("details model=$remoteId") { details(remoteId) }
            val plan = retrying("plan model=$remoteId") { source.downloadPlan(remote) }
            val media = downloadMedia(plan, temp)
            if (media.isEmpty()) return@withLock Result.success(local)
            val updated = withContext(Dispatchers.IO) {
                val home = store.libraryDir(localId)
                val finalMedia = swapIn(temp, File(home, "media"))
                val withMedia = local.withMedia(finalMedia, media)
                store.update { state -> state.copy(models = state.models.map { if (it.id == localId) withMedia else it }) }
                withMedia
            }
            CarModelLog.info("CM-IMPORT-MEDIA", "backfill done localId=$localId cover=${updated.coverPath != null} screenshots=${updated.screenshotPaths.size}")
            Result.success(updated)
        } catch (cancelled: CancellationException) {
            mediaAttempted.remove(localId)
            throw cancelled
        } catch (error: Throwable) {
            CarModelLog.fail(CarModelErrorCode.HUB_DOWNLOAD_FAILED, "CM-IMPORT-MEDIA backfill failed localId=$localId cause=${error.javaClass.simpleName}: ${error.message}")
            Result.failure(error.asCarModelException(CarModelErrorCode.HUB_DOWNLOAD_FAILED))
        } finally {
            withContext(kotlinx.coroutines.NonCancellable + Dispatchers.IO) { temp.deleteRecursively() }
        }
    }

    /** Validates nothing itself: [filesDir] and [media] were verified by the caller. Never leaves partial library state. */
    internal fun importVerified(remote: CommunityCarModel, filesDir: File, manifest: CarModelManifest, media: List<DownloadedMedia>): LocalCarModel {
        val existing = store.current.models.firstOrNull { it.remoteId == remote.id }
        val id = existing?.id ?: UUID.randomUUID().toString()
        val home = store.libraryDir(id).apply { mkdirs() }
        val files = File(home, "files")
        val mediaHome = File(home, "media")
        val incoming = File(home, "files.incoming-${UUID.randomUUID()}")
        val mediaIncoming = File(home, "media.incoming-${UUID.randomUUID()}")
        val stamp = UUID.randomUUID()
        val previousFiles = File(home, "files.previous-$stamp")
        val previousMedia = File(home, "media.previous-$stamp")
        try {
            place(filesDir, incoming) { copied -> manifest.files.all { File(copied, it.sourcePath).let { f -> f.isFile && f.length() == it.sizeBytes } } && File(copied, "manifest.json").isFile }
            val mediaSizes = media.associate { it.file.name to it.file.length() }
            if (media.isNotEmpty()) place(media.first().file.parentFile!!, mediaIncoming) { copied -> mediaSizes.all { (name, size) -> File(copied, name).length() == size } }
            if (files.exists()) fail(files.renameTo(previousFiles), "Unable to set aside the previous copy of this model")
            fail(incoming.renameTo(files), "Unable to move the downloaded model into the library")
            if (media.isNotEmpty()) {
                if (mediaHome.exists()) fail(mediaHome.renameTo(previousMedia), "Unable to set aside previous images")
                fail(mediaIncoming.renameTo(mediaHome), "Unable to move images into the library")
            }
            val displayName = remote.name.ifBlank { manifest.metadata.name }.trim()
            val base = LocalCarModel(
                id = id,
                name = displayName,
                creator = remote.creator.ifBlank { manifest.metadata.creator }.trim(),
                description = remote.description.ifBlank { manifest.metadata.description }.trim(),
                gameVersion = remote.gameBuild.trim(),
                license = existing?.license.orEmpty(),
                supportedPackages = remote.supportedPackages.ifEmpty { manifest.supportedGamePackages },
                // installMode, installationPolicy, target and files come from the remote manifest unchanged.
                manifest = manifest.copy(metadata = PackageMetadata(displayName, manifest.metadata.version, remote.description.ifBlank { manifest.metadata.description }.trim(), remote.creator.ifBlank { manifest.metadata.creator }.trim())),
                filesRoot = files.absolutePath,
                coverPath = existing?.coverPath?.takeIf { media.isEmpty() && File(it).isFile },
                screenshotPaths = if (media.isEmpty()) existing?.screenshotPaths.orEmpty().filter { File(it).isFile } else emptyList(),
                ignoredFiles = emptyList(),
                importedAt = clock(),
                remoteId = remote.id,
                remoteDraftId = existing?.remoteDraftId,
                remotePublicId = existing?.remotePublicId,
                selectedVariants = existing?.selectedVariants.orEmpty(),
                media = if (media.isEmpty()) existing?.media.orEmpty() else emptyList(),
            )
            val local = if (media.isEmpty()) base else base.withMedia(mediaHome, media)
            val persisted = store.update { state -> state.copy(models = state.models.filterNot { it.id == id || it.remoteId == remote.id } + local) }
            check(persisted.models.any { it.id == id && it.remoteId == remote.id }) { "Library state did not record the downloaded model" }
            CarModelLog.info("CM-LIBRARY-PERSIST", "models=${persisted.models.size} localId=$id remoteId=${remote.id} replaced=${existing != null} cover=${local.coverPath} stateFile=${store.root.absolutePath}")
            store.log(CarModelActivityKind.IMPORT, local.name, "Downloaded from Community Hub: ${local.fileCount} files", true)
            CarModelLog.info("CM-IMPORT-OK", "saved download to library: ${local.fileCount} files ${CarModelLog.where(null, local.vehicleDirectory)}")
            previousFiles.deleteRecursively(); previousMedia.deleteRecursively()
            return local
        } catch (error: Throwable) {
            // Roll back to exactly what was there before.
            incoming.deleteRecursively(); mediaIncoming.deleteRecursively()
            if (previousFiles.exists()) { files.deleteRecursively(); previousFiles.renameTo(files) }
            if (previousMedia.exists()) { mediaHome.deleteRecursively(); previousMedia.renameTo(mediaHome) }
            if (existing == null && store.current.models.none { it.id == id }) home.deleteRecursively()
            throw error
        }
    }

    /** Throws a coded exception unless manifest.json is valid and every listed file exists with the expected size and hash. */
    internal fun verifyDownload(root: File, knownHashes: Map<String, String> = emptyMap()): CarModelManifest {
        val manifestFile = File(root, "manifest.json")
        if (!manifestFile.isFile) throw CarModelException(CarModelErrorCode.IMPORT_INVALID_MANIFEST, "Downloaded model has no manifest.json")
        val manifest = try {
            CarModelManifest.fromJson(JSONObject(manifestFile.readText())).also(CarModelManifestValidator::validate)
        } catch (error: Exception) {
            throw CarModelException(CarModelErrorCode.IMPORT_INVALID_MANIFEST, "Downloaded manifest is invalid: ${error.message}", error)
        }
        manifest.files.forEach { entry ->
            val file = contained(root, entry.sourcePath)
            if (!file.isFile) throw CarModelException(CarModelErrorCode.IMPORT_VALIDATION_FAILED, "Downloaded model is missing ${entry.sourcePath}")
            if (file.length() != entry.sizeBytes) throw CarModelException(CarModelErrorCode.IMPORT_VALIDATION_FAILED, "Size mismatch for ${entry.sourcePath}: expected ${entry.sizeBytes}, got ${file.length()}")
            val actual = knownHashes[entry.sourcePath] ?: sha256(file)
            if (!actual.equals(entry.sha256, ignoreCase = true)) throw CarModelException(CarModelErrorCode.IMPORT_VALIDATION_FAILED, "Checksum mismatch for ${entry.sourcePath}")
        }
        return manifest
    }

    /** Best-effort: each image is retried, verified (size, type, SHA-256 when known) and dropped on failure. */
    private suspend fun downloadMedia(plan: CarModelDownloadPlan, mediaDir: File): List<DownloadedMedia> {
        withContext(Dispatchers.IO) { mediaDir.deleteRecursively(); mediaDir.mkdirs() }
        val entries = listOfNotNull(plan.cover) + plan.screenshots
        return entries.mapNotNull { entry ->
            val limit = if (entry.role == "cover") CarModelLimits.MAX_COVER_BYTES else CarModelLimits.MAX_SCREENSHOT_BYTES
            val raw = File(mediaDir, "${entry.role}-${entry.position}.download")
            val event = if (entry.role == "cover") "CM-DOWNLOAD-COVER" else "CM-DOWNLOAD-SCREENSHOT"
            try {
                val sha = downloadVerified(entry.url, raw, entry.sizeBytes, entry.sha256, maxBytes = limit) { }
                val mime = withContext(Dispatchers.IO) { imageType(raw) } ?: throw CarModelException(CarModelErrorCode.IMPORT_VALIDATION_FAILED, "${entry.role} is not a supported image")
                val name = if (entry.role == "cover") "cover.${extension(mime)}" else "screenshot_%02d.%s".format(entry.position, extension(mime))
                val final = File(mediaDir, name)
                withContext(Dispatchers.IO) { fail(raw.renameTo(final), "Unable to store ${entry.role}") }
                CarModelLog.info(event, "model=${plan.modelId} position=${entry.position} bytes=${final.length()} type=$mime shaChecked=${entry.sha256 != null}")
                DownloadedMedia(entry.role, entry.position, final, mime, sha)
            } catch (cancelled: CancellationException) {
                throw cancelled
            } catch (error: Throwable) {
                withContext(Dispatchers.IO) { raw.delete() }
                CarModelLog.fail(CarModelErrorCode.HUB_DOWNLOAD_FAILED, "$event failed model=${plan.modelId} position=${entry.position} cause=${error.javaClass.simpleName}: ${error.message}")
                null
            }
        }
    }

    /** Downloads to `<target>.part` with retries, verifies size/SHA-256, then renames into place. Returns the SHA-256. */
    private suspend fun downloadVerified(url: String, target: File, expectedSize: Long?, expectedSha: String?, maxBytes: Long = CarModelLimits.MAX_FILE_BYTES, onBytes: (Long) -> Unit): String =
        retrying("fetch ${target.name}") {
            val part = File(target.parentFile, "${target.name}.part")
            withContext(Dispatchers.IO) { target.parentFile?.mkdirs(); part.delete() }
            source.fetch(url, part, onBytes)
            withContext(Dispatchers.IO) {
                val size = part.length()
                if (size <= 0 || size > maxBytes || (expectedSize != null && size != expectedSize)) {
                    part.delete(); throw TransferCorrupt("Size mismatch for ${target.name}: expected ${expectedSize ?: "≤$maxBytes"}, got $size")
                }
                val sha = sha256(part)
                if (expectedSha != null && !sha.equals(expectedSha, ignoreCase = true)) { part.delete(); throw TransferCorrupt("Checksum mismatch for ${target.name}") }
                target.delete()
                fail(part.renameTo(target), "Unable to finalize ${target.name}")
                sha
            }
        }

    /** Retries network failures and truncated transfers with backoff; everything else fails immediately. */
    private suspend fun <T> retrying(what: String, block: suspend () -> T): T {
        var attempt = 1
        while (true) {
            try {
                return block()
            } catch (cancelled: CancellationException) {
                throw cancelled
            } catch (error: Throwable) {
                val retryable = when (error) {
                    is CarModelHttpException -> error.retryable
                    is TransferCorrupt, is IOException -> true
                    else -> false
                }
                if (!retryable || attempt >= maxAttempts) {
                    throw if (error is TransferCorrupt) CarModelException(CarModelErrorCode.IMPORT_VALIDATION_FAILED, error.message ?: "Downloaded file is corrupt", error) else error
                }
                CarModelLog.info("CM-DOWNLOAD-RETRY", "$what attempt=$attempt/$maxAttempts cause=${error.javaClass.simpleName}: ${error.message}")
                delay(retryDelayMs(attempt))
                attempt++
            }
        }
    }

    /** SHA-256 of a file a previous attempt already finalized with the expected size and hash, else null. */
    private fun alreadyVerified(filesDir: File, entry: CarModelDownloadPlan.FileEntry): String? {
        val file = contained(filesDir, entry.path)
        if (!file.isFile || file.length() != entry.sizeBytes) return null
        return sha256(file).takeIf { it.equals(entry.sha256, ignoreCase = true) }
    }

    /** Removes legacy download folders and partials abandoned for more than a week; never the one in use. */
    private fun cleanupDownloads(keep: File) {
        downloadsRoot.mkdirs()
        val cutoff = clock() - STALE_PARTIAL_MS
        downloadsRoot.listFiles().orEmpty().forEach { entry ->
            if (entry == keep) return@forEach
            val stalePartial = entry.name.endsWith(".partial") && entry.lastModified() < cutoff
            if (!entry.name.endsWith(".partial") || stalePartial) {
                CarModelLog.info("CM-DOWNLOAD-CLEANUP", "removing ${entry.name}")
                entry.deleteRecursively()
            }
        }
    }

    /** Rename first (cheap, same filesystem); fall back to a copy that [complete] must accept. */
    private fun place(from: File, to: File, complete: (File) -> Boolean) {
        if (moveDirectory(from, to)) {
            fail(complete(to), "Moved model is incomplete")
            return
        }
        CarModelLog.info("CM-IMPORT-COPY-FALLBACK", "rename refused from=${from.absolutePath} to=${to.absolutePath}")
        to.deleteRecursively()
        from.copyRecursively(to, overwrite = true)
        fail(complete(to), "Copy into the library was incomplete")
    }

    /** Moves [from] to [to], keeping the old [to] until the new one is in place. Returns [to]. */
    private fun swapIn(from: File, to: File): File {
        val incoming = File(to.parentFile, "${to.name}.incoming-${UUID.randomUUID()}")
        val previous = File(to.parentFile, "${to.name}.previous-${UUID.randomUUID()}")
        to.parentFile?.mkdirs()
        place(from, incoming) { it.isDirectory && (it.list()?.isNotEmpty() == true) }
        if (to.exists()) fail(to.renameTo(previous), "Unable to set aside previous images")
        if (!incoming.renameTo(to)) { if (previous.exists()) previous.renameTo(to); incoming.deleteRecursively(); fail(false, "Unable to move images into the library") }
        previous.deleteRecursively()
        return to
    }

    private fun LocalCarModel.withMedia(mediaHome: File, media: List<DownloadedMedia>): LocalCarModel {
        val cover = media.firstOrNull { it.role == "cover" }
        val shots = media.filter { it.role == "screenshot" }.sortedBy { it.position }
        return copy(
            coverPath = cover?.let { File(mediaHome, it.file.name).absolutePath },
            screenshotPaths = shots.map { File(mediaHome, it.file.name).absolutePath },
            media = media.map { CarModelMediaRecord(it.role, "media/${it.file.name}", it.mimeType, File(mediaHome, it.file.name).length(), it.sha256) },
        )
    }

    private fun contained(root: File, relative: String): File {
        val canonicalRoot = root.canonicalFile
        val file = File(root, relative).canonicalFile
        if (!file.toPath().startsWith(canonicalRoot.toPath())) throw CarModelException(CarModelErrorCode.UNSAFE_PATH, "Unsafe path in downloaded manifest: $relative")
        return file
    }

    private fun fail(condition: Boolean, message: String) {
        if (!condition) throw CarModelException(CarModelErrorCode.IMPORT_SAVE_FAILED, message)
    }

    private class TransferCorrupt(message: String) : Exception(message)

    /** Reports `completed` bytes that never go backwards, even when a file is retried from zero. */
    private class MonotonicProgress(private val total: Long, private val sink: (Long, Long) -> Unit) {
        private var committed = 0L
        private var reported = 0L
        fun base(bytes: Long) { committed = bytes; emit(committed) }
        fun current(fileBytes: Long) = emit(committed + fileBytes)
        fun commit(fileBytes: Long) { committed += fileBytes; emit(committed) }
        @Synchronized private fun emit(value: Long) {
            val next = value.coerceIn(reported, total.coerceAtLeast(0))
            if (next != reported || next == 0L) { reported = next; sink(next, total) }
        }
    }

    companion object {
        private const val MB = 1024L * 1024
        private const val SPACE_MARGIN_BYTES = 16 * MB
        private const val STALE_PARTIAL_MS = 7L * 24 * 60 * 60 * 1000
        private val CORRUPT_CODES = setOf(CarModelErrorCode.IMPORT_VALIDATION_FAILED, CarModelErrorCode.IMPORT_INVALID_MANIFEST, CarModelErrorCode.UNSAFE_PATH)

        private fun safeName(id: String) = id.replace(Regex("[^A-Za-z0-9_-]"), "_")

        internal fun imageType(file: File): String? {
            val head = ByteArray(12)
            val read = file.inputStream().use { it.read(head) }
            if (read < 12) return null
            return when {
                head[0] == 'R'.code.toByte() && head[1] == 'I'.code.toByte() && head[2] == 'F'.code.toByte() && head[3] == 'F'.code.toByte() &&
                    head[8] == 'W'.code.toByte() && head[9] == 'E'.code.toByte() && head[10] == 'B'.code.toByte() && head[11] == 'P'.code.toByte() -> "image/webp"
                head[0] == 0x89.toByte() && head[1] == 'P'.code.toByte() && head[2] == 'N'.code.toByte() && head[3] == 'G'.code.toByte() -> "image/png"
                head[0] == 0xFF.toByte() && head[1] == 0xD8.toByte() -> "image/jpeg"
                else -> null
            }
        }

        private fun extension(mime: String) = when (mime) { "image/png" -> "png"; "image/jpeg" -> "jpg"; else -> "webp" }

        internal fun sha256(file: File): String {
            val digest = MessageDigest.getInstance("SHA-256")
            file.inputStream().use { input ->
                val buffer = ByteArray(64 * 1024)
                while (true) { val read = input.read(buffer); if (read < 0) break; digest.update(buffer, 0, read) }
            }
            return digest.digest().joinToString("") { "%02x".format(it) }
        }
    }
}

/** The models shown in Mods → Car Models → Downloaded Mods. */
fun CarModelState.downloadedModels(): List<LocalCarModel> = models.filter { it.remoteId != null }
