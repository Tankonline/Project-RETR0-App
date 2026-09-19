package com.example.projectretr0installer.installer

import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.ParcelFileDescriptor
import android.os.SystemClock
import android.util.Log
import androidx.documentfile.provider.DocumentFile
import com.example.projectretr0installer.BuildConfig
import com.example.projectretr0installer.IRetr0UserService
import com.example.projectretr0installer.data.skins.MusicApiClient
import com.example.projectretr0installer.data.skins.SaveApiClient
import com.example.projectretr0installer.data.skins.SkinInstallationIdentity
import com.example.projectretr0installer.data.skins.SkinRequestCanonicalizer
import com.example.projectretr0installer.data.skins.SkinApiException
import com.example.projectretr0installer.installer.persistence.DownloadDatabase
import com.yausername.youtubedl_android.YoutubeDL
import com.yausername.youtubedl_android.YoutubeDLRequest
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.URI
import java.net.URL
import java.security.MessageDigest
import java.util.Locale

/**
 * Orchestrates the Project RETR0 installation and maintenance process.
 */
class Retr0Installer(
    private val context: Context,
    private val service: IRetr0UserService? = null,
) {

    companion object {
        private const val TAG = "RETR0_INSTALLER_LOG"
        private const val AI_TAG = "RETR0_AI"
        private const val MUSIC_LIBRARY_TAG = "RETR0_MUSIC_LIBRARY"
        private const val MANIFEST_URL = "https://pub-f87b4daf1b344ca39c9ba6de6812a777.r2.dev"
        private const val RR3_ROW = "/storage/emulated/0/Android/data/com.ea.games.r3_row"
        private const val RR3_NA = "/storage/emulated/0/Android/data/com.ea.games.r3_na"
        
        // PERSISTENT HUB: Stored in public Documents to survive uninstalls/updates
        private const val SAVE_HUB_PATH = "/storage/emulated/0/Documents/ProjectRETR0/Backups"
        private const val MUSIC_HUB_PATH = "/storage/emulated/0/Documents/ProjectRETR0/MusicBackups"
        private const val MUSIC_LIBRARY_PATH = "/storage/emulated/0/Documents/ProjectRETR0/MusicLibrary"
        
        // COMMUNITY API URL
        private const val COMMUNITY_API_URL = BuildConfig.COMMUNITY_API_BASE_URL
        
        // Workers.dev proxy for globally cached release downloads.
        private const val BOOSTER_URL = COMMUNITY_API_URL

        private var isDownloaderInit = false
        private const val YOUTUBE_METADATA_TIMEOUT_MS = 45_000L
        private const val YOUTUBE_DOWNLOAD_TIMEOUT_MS = 8 * 60_000L
        private const val YOUTUBE_SOCKET_TIMEOUT_SECONDS = 20
        private const val YOUTUBE_RETRIES = 2
        private const val MAX_MUSIC_ARTWORK_DOWNLOAD_BYTES = 25L * 1024 * 1024
        private val SUPPORTED_MUSIC_ARTWORK_TYPES = setOf("image/jpeg", "image/png", "image/webp")

        internal enum class YouTubeErrorCode(val userMessage: String) {
            INVALID_URL("YT_E001 Invalid YouTube URL."),
            INITIALIZATION_FAILED("YT_E002 Local downloader could not start."),
            METADATA_TIMEOUT("YT_E003 Timed out while reading video details."),
            METADATA_FAILED("YT_E004 Could not read video details."),
            DOWNLOAD_TIMEOUT("YT_E005 Timed out while downloading or converting audio."),
            DOWNLOAD_FAILED("YT_E006 Audio download or conversion failed."),
            OUTPUT_MISSING("YT_E007 Audio conversion completed without an MP3 output."),
            CANCELLED("YT_E008 YouTube processing was cancelled."),
        }

        /**
         * Initializes the YouTubeDL and FFmpeg engines locally on the device.
         */
        suspend fun initDownloader(context: Context): Boolean = withContext(Dispatchers.IO) {
            if (isDownloaderInit) return@withContext true
            try {
                com.yausername.youtubedl_android.YoutubeDL.getInstance().init(context)
                com.yausername.ffmpeg.FFmpeg.getInstance().init(context)
                isDownloaderInit = true
                Log.i(TAG, "YoutubeDL & FFmpeg initialized locally")
                
                // The bundled yt-dlp version is updated with normal signed app releases.
                // Runtime executable updates are disabled so this APK stays reproducible.
                Log.i(TAG, "Using bundled yt-dlp version")
                
                true
            } catch (e: Exception) {
                Log.e(TAG, "Failed to initialize YoutubeDL engine", e)
                false
            }
        }
    }

    private val downloader = FileDownloader()
    private val verifier = HashVerifier()
    private val manifestManager = ManifestManager(COMMUNITY_API_URL)
    private val youtubeCookieStore by lazy { YouTubeCookieStore(context) }

    @Volatile
    private var globalCachedManifest: InstallManifest? = null
    private val sonicInstaller by lazy { SonicInstaller(context, service!!) }
    private val downloadDb by lazy { DownloadDatabase.getDatabase(context) }
    
    private val _progress = MutableStateFlow(InstallProgress())
    val progress: StateFlow<InstallProgress> = _progress.asStateFlow()

    /** Pre-fetches the shared RR3 installation manifest for faster content installation. */
    suspend fun preFetchManifest() {
        try {
            globalCachedManifest = manifestManager.fetchManifest()
            Log.i(TAG, "RR3 installation manifest pre-fetched")
        } catch (e: Exception) {
            Log.e(TAG, "RR3 installation manifest pre-fetch failed", e)
        }
    }

    private var activeRr3Path: String? = null

    private fun getRr3Path(): String {
        activeRr3Path?.let { return it }
        val s = service
        val foundPath = if (s?.exists(RR3_ROW) == true) RR3_ROW else if (s?.exists(RR3_NA) == true) RR3_NA else RR3_ROW
        activeRr3Path = foundPath
        return foundPath
    }

    private suspend fun installStreamingZip(
        file: InstallFile,
        fileNum: String,
        rr3Path: String,
        index: Int,
        totalFiles: Int,
        downloadedBytes: Long,
        totalCompressed: Long,
    ): Int {
        val candidateUrls = listOf(file.url.replace(MANIFEST_URL, BOOSTER_URL), file.url).distinct()
        var lastError: Throwable? = null
        for ((attempt, candidateUrl) in candidateUrls.withIndex()) {
            try {
                val count = sonicInstaller.streamInstall(candidateUrl, rr3Path) { entryName ->
                    updateProgress(
                        state = InstallState.EXTRACTING,
                        step = "$fileNum: Piped Extraction (${if (attempt == 0) "Booster" else "Fallback"})...",
                        currentFile = entryName,
                        overallProgress = (downloadedBytes.toFloat() + (file.size / 2f)) / totalCompressed,
                        completedFiles = index,
                        totalFiles = totalFiles,
                        eta = "Piping...",
                    )
                }
                if (count > 0) return count
            } catch (error: Throwable) {
                lastError = error
                Log.w(TAG, "Sonic URL attempt ${attempt + 1} failed for ${file.name}: ${error.message}")
            }
        }
        throw Exception("Archive extraction failed${lastError?.message?.let { ": $it" } ?: ""}", lastError)
    }

    suspend fun installMod(): Boolean = withContext(Dispatchers.IO) {
        try {
            val s = service ?: run { fail("Service not connected."); return@withContext false }
            val rr3Path = getRr3Path()
            updateProgress(InstallState.FETCHING_MANIFEST, "Connecting to Cloudflare...")

            val manifest = globalCachedManifest ?: manifestManager.fetchManifest() ?: run {
                fail("Connection failed. Manifest not found.")
                return@withContext false
            }
            val requiredFiles = manifest.files.filter { it.required && !it.type.equals("apk", ignoreCase = true) }
            if (requiredFiles.isEmpty()) {
                fail("Manifest contains no required install files.")
                return@withContext false
            }
            requiredFiles.filter { it.type.equals("split_zip", ignoreCase = true) }
                .forEach(SplitZipSupport::validateChunkedArchive)

            val totalCompressed = requiredFiles.sumOf { it.size }.coerceAtLeast(1L)
            val totalUncompressed = requiredFiles.sumOf { it.uncompressedSize }
            val largestCompressedArchive = requiredFiles
                .asSequence()
                .filter {
                    it.type.equals("zip", ignoreCase = true) ||
                        it.type.equals("split_zip", ignoreCase = true)
                }
                .maxOfOrNull { it.size } ?: 0L
            val requiredBytes = totalUncompressed + largestCompressedArchive + (512 * 1024 * 1024L)
            var downloadedBytesAccumulated = 0L

            updateProgress(InstallState.PREPARING, "Checking storage...")
            val available = StorageUtils.getAvailableSpace(context.externalCacheDir ?: context.cacheDir)
            if (available in (1 until requiredBytes)) {
                fail("Insufficient space. You need ${StorageUtils.formatSize(requiredBytes)} free.")
                return@withContext false
            }
            if (!s.ping()) { fail("Shizuku service not connected."); return@withContext false }

            val tempDir = File(context.externalCacheDir ?: context.cacheDir, "retr0_install").apply { mkdirs() }
            tempDir.listFiles()?.filter { it.name.endsWith(".merged") }?.forEach(File::delete)

            updateProgress(InstallState.PREPARING, "Wiping existing data...")
            val statsBefore = s.getDirectoryStats("$rr3Path/files")
            Log.i(TAG, "Cleanup - Before: ${statsBefore[1]} files")
            try {
                s.delete("$rr3Path/files")
                s.delete("$rr3Path/cache")
            } catch (_: Exception) {}
            s.createDirectory("$rr3Path/files")
            s.createDirectory("$rr3Path/cache")

            Log.i(TAG, "Starting install sequence targeting: $rr3Path")
            requiredFiles.forEachIndexed { index, file ->
                val fileNum = "Part ${index + 1} of ${requiredFiles.size}"
                val downloadFile = File(tempDir, file.name)

                if (file.type == "zip") {
                    updateProgress(
                        state = InstallState.DOWNLOADING,
                        step = "$fileNum: ${if (file.parts.isEmpty()) "Sonic Piped Installation" else "Downloading split ZIP"}...",
                        currentFile = file.name,
                        overallProgress = downloadedBytesAccumulated.toFloat() / totalCompressed,
                        completedFiles = index,
                        totalFiles = requiredFiles.size,
                        eta = "Streaming...",
                    )

                    try {
                        val count = if (file.parts.isEmpty()) {
                            installStreamingZip(file, fileNum, rr3Path, index, requiredFiles.size, downloadedBytesAccumulated, totalCompressed)
                        } else {
                            val parts = SplitZipSupport.validate(file)
                            val localParts = mutableListOf<File>()
                            var splitBytes = 0L
                            try {
                                for (part in parts) {
                                    val localPart = File(tempDir, part.name)
                                    val candidateUrl = part.url.replace(MANIFEST_URL, BOOSTER_URL)
                                    if (!downloader.download(candidateUrl, localPart, part.size) { current, _, speed ->
                                            val overall = downloadedBytesAccumulated + splitBytes + current
                                            val remaining = (totalCompressed - overall).coerceAtLeast(0)
                                            updateProgress(
                                                state = InstallState.DOWNLOADING,
                                                step = "$fileNum: Downloading ${part.name}...",
                                                currentFile = part.name,
                                                fileProgress = current.toFloat() / part.size,
                                                overallProgress = overall.toFloat() / totalCompressed,
                                                completedFiles = index,
                                                totalFiles = requiredFiles.size,
                                                eta = if (speed > 0) "${(remaining / speed).toLong()}s" else "Calculating...",
                                            )
                                        }) throw Exception("Download failed for ${part.name}: ${downloader.lastError}")
                                    if (!verifier.verifySha256(localPart, part.sha256)) throw Exception("Corrupted split ZIP part: ${part.name}")
                                    localParts += localPart
                                    splitBytes += part.size
                                }
                                updateProgress(InstallState.EXTRACTING, "$fileNum: Extracting split ZIP...", file.name)
                                sonicInstaller.installSplitArchive(localParts, rr3Path)
                            } finally {
                                localParts.forEach { it.delete() }
                            }
                        }
                        if (count <= 0) throw Exception("Archive extraction failed")
                        downloadedBytesAccumulated += file.size
                    } catch (e: Exception) {
                        Log.e(TAG, "Sonic install failed", e)
                        fail("Sonic Install failed for ${file.name}: ${e.message}")
                        return@withContext false
                    }
                } else {
                    // LEGACY/SINGLE FILE HANDLER
                    if (file.chunks.isNotEmpty()) {
                        // --- PARALLEL CHUNK DOWNLOADER ---
                        updateProgress(
                            state = InstallState.DOWNLOADING,
                            step = "$fileNum: Fast Downloading Chunks...",
                            currentFile = file.name,
                            overallProgress = downloadedBytesAccumulated.toFloat() / totalCompressed,
                            completedFiles = index,
                            totalFiles = requiredFiles.size,
                            eta = "Streaming...",
                        )

                        // Download chunks from the BOOSTER proxy
                        val chunkBaseUrl = "$BOOSTER_URL/"
                        if (!downloader.downloadChunks(
                                baseUrl = chunkBaseUrl, 
                                chunkNames = file.chunks, 
                                destDir = tempDir, 
                                totalSize = file.size,
                                modVersion = manifest.version,
                                archiveId = file.id,
                                chunkDao = downloadDb.chunkDao()
                            ) { bytes, _, speed ->
                            val overall = downloadedBytesAccumulated + bytes
                            val remaining = totalCompressed - overall
                            val etaStr = if (speed > 0) String.format(Locale.ENGLISH, "%02d:%02d", (remaining / speed).toLong() / 60, (remaining / speed).toLong() % 60) else "Calculating..."
                            
                            updateProgress(
                            state = InstallState.DOWNLOADING,
                            step = "$fileNum: Fast Downloading Chunks...",
                            currentFile = file.name,
                            fileProgress = bytes.toFloat() / file.size,
                            overallProgress = overall.toFloat() / totalCompressed,
                            completedFiles = index,
                            totalFiles = requiredFiles.size,
                            eta = etaStr,
                        )
                        }) {
                            fail("Fast Download failed for ${file.name}: ${downloader.lastError}")
                            return@withContext false
                        }

                        updateProgress(InstallState.PREPARING, "$fileNum: Stitching chunks...", file.name, 1.0f)
                        val mergedFile = File(tempDir, "${file.name}.merged")
                        try {
                            if (mergedFile.exists()) mergedFile.delete()
                            FileOutputStream(mergedFile).use { output ->
                                file.chunks.forEach { chunkName ->
                                    val chunkFileName = FileDownloader.localChunkFileName(chunkName)
                                    val chunkFile = File(tempDir, chunkFileName)
                                    chunkFile.inputStream().use { it.copyTo(output) }
                                    chunkFile.delete()
                                }
                            }
                            if (downloadFile.exists()) downloadFile.delete()
                            mergedFile.renameTo(downloadFile)
                        } catch (e: Exception) {
                            fail("Failed to stitch file parts: ${e.message}")
                            return@withContext false
                        }
                    } else {
                        // --- SINGLE FILE DOWNLOADER ---
                        updateProgress(
                            state = InstallState.DOWNLOADING,
                            step = "$fileNum: Connecting...",
                            currentFile = file.name,
                            overallProgress = downloadedBytesAccumulated.toFloat() / totalCompressed,
                            completedFiles = index,
                            totalFiles = requiredFiles.size,
                        )

                        if (!downloader.download(file.url.replace(MANIFEST_URL, BOOSTER_URL), downloadFile, file.size) { currentBytes, totalBytes, speed ->
                            val overallDownloaded = downloadedBytesAccumulated + currentBytes
                            val overallProgress = overallDownloaded.toFloat() / totalCompressed
                            val remainingBytes = totalCompressed - overallDownloaded
                            val etaSeconds = if (speed > 0) (remainingBytes / speed).toLong() else -1L
                            val etaStr = if (etaSeconds >= 0) String.format(Locale.ENGLISH, "%02d:%02d", etaSeconds / 60, etaSeconds % 60) else "Calculating..."

                            updateProgress(
                                state = InstallState.DOWNLOADING, 
                                step = "$fileNum: Downloading...", 
                                currentFile = file.name, 
                                fileProgress = currentBytes.toFloat() / totalBytes,
                                overallProgress = overallProgress,
                                completedFiles = index,
                                totalFiles = requiredFiles.size,
                                eta = etaStr,
                            )
                        }) {
                            fail("Download failed: ${file.name} (${downloader.lastError})")
                            return@withContext false
                        }
                    }

                    downloadedBytesAccumulated += file.size
                    
                    // Verify hash for single files/merged files
                    if (file.sha256 != "SKIP_VERIFICATION") {
                        if (!verifier.verifySha256(downloadFile, file.sha256)) {
                            fail("Corrupted file: ${file.name}")
                            return@withContext false
                        }
                    }

                    if (file.type == "split_zip") {
                        updateProgress(InstallState.EXTRACTING, "$fileNum: Extracting game data...", file.name)
                        Log.i(TAG, "Extracting stitched archive ${downloadFile.absolutePath} to $rr3Path")
                        val extractedCount = sonicInstaller.installArchive(downloadFile, rr3Path)
                        if (extractedCount <= 0) {
                            fail("Archive extraction produced no files: ${file.name}")
                            return@withContext false
                        }
                        val statsAfterExtraction = s.getDirectoryStats(rr3Path)
                        Log.i(TAG, "Extraction complete for ${file.name}: $extractedCount entries; target now has ${statsAfterExtraction[1]} files")
                        if (statsAfterExtraction[1] <= 0L) {
                            fail("Extraction verification failed for ${file.name}: target is empty")
                            return@withContext false
                        }
                        downloadDb.chunkDao().clearChunksForArchive(manifest.version, file.id)
                    } else {
                        val relativePath = file.destination.substringAfter("com.ea.games.r3_row/")
                            .substringAfter("com.ea.games.r3_na/")
                        val finalDest = "$rr3Path/$relativePath"

                        if (file.name.endsWith(".patch")) {
                            updateProgress(InstallState.INSTALLING, "$fileNum: Applying Binary Patch...", file.name)
                            val targetFile = finalDest.removeSuffix(".patch")
                            if (!s.applyPatch(targetFile, downloadFile.absolutePath)) {
                                fail("Failed to apply patch: ${file.name}")
                                return@withContext false
                            }
                        } else if (!s.copyFile(downloadFile.absolutePath, finalDest)) {
                            fail("Failed to copy ${file.name}")
                            return@withContext false
                        }
                    }
                    downloadFile.delete()
                }

                updateProgress(
                    state = InstallState.INSTALLING, 
                    step = "$fileNum: Completed", 
                    currentFile = file.name, 
                    fileProgress = 1.0f, 
                    overallProgress = (downloadedBytesAccumulated.toFloat() / totalCompressed),
                    completedFiles = index + 1, 
                    totalFiles = requiredFiles.size,
                )
            }

            updateProgress(InstallState.SUCCESS, "MOD SUCCESSFULLY INSTALLED", "", 1.0f)
            tempDir.deleteRecursively()
            return@withContext true

        } catch (e: Exception) {
            fail("Fatal Error: ${e.message}")
            return@withContext false
        }
    }

    fun runSafeTest(): Boolean {
        val s = service ?: run { fail("Not connected."); return false }
        return try {
            val rr3Path = getRr3Path()
            if (!s.ping()) { fail("Not connected."); return false }
            val testPath = "$rr3Path/files/retr0_test.tmp"
            if (s.writeFile(testPath, "TEST".toByteArray(Charsets.UTF_8))) {
                s.delete(testPath)
                updateProgress(InstallState.SUCCESS, "FILESYSTEM TEST PASSED", "", 1.0f)
                true
            } else {
                fail("Write denied."); false
            }
        } catch (e: Exception) {
            fail("Test error: ${e.message}"); false
        }
    }

    fun runAssetRepair(): Boolean {
        val s = service ?: run { fail("Not connected."); return false }
        return try {
            val rr3Path = getRr3Path()
            if (!s.ping()) { fail("Not connected."); return false }
            val depotPath = "$rr3Path/files/.depot"
            val resPath = "$rr3Path/files/apk/res"
            updateProgress(InstallState.PREPARING, "Cleaning assets...")
            s.delete("$depotPath/eds")
            s.delete("$depotPath/feats")
            updateProgress(InstallState.INSTALLING, "Restoring assets...")
            if (s.exists("$resPath/eds")) s.copyDirectory("$resPath/eds", "$depotPath/eds")
            if (s.exists("$resPath/feats")) s.copyDirectory("$resPath/feats", "$depotPath/feats")
            updateProgress(InstallState.SUCCESS, "ASSET REPAIR COMPLETE")
            true
        } catch (e: Exception) {
            fail("Repair error: ${e.message}"); false
        }
    }

    suspend fun importCharacterData(uri: Uri): Boolean = withContext(Dispatchers.IO) {
        val s = service ?: run { fail("Not connected."); return@withContext false }
        try {
            val rr3Path = getRr3Path()
            if (!s.ping()) { fail("Not connected."); return@withContext false }
            val destPath = "$rr3Path/files/doc/character.2.dat"
            val pfd = context.contentResolver.openFileDescriptor(uri, "r") ?: run {
                fail("Could not open file."); return@withContext false
            }
            updateProgress(InstallState.INSTALLING, "Importing character.2.dat...")
            s.delete(destPath)
            if (s.importFile(pfd, destPath)) {
                updateProgress(InstallState.SUCCESS, "CHARACTER DATA IMPORTED")
                return@withContext true
            }
            fail("Failed to write to game folder."); false
        } catch (e: Exception) {
            fail("Import error: ${e.message}"); false
        }
    }

    suspend fun importSaveDirectory(treeUri: Uri): Boolean = withContext(Dispatchers.IO) {
        val s = service ?: run { fail("Not connected."); return@withContext false }
        try {
            val rr3Path = getRr3Path()
            if (!s.ping()) { fail("Not connected."); return@withContext false }
            val destPath = "$rr3Path/files/doc"
            val tempDir = File(context.externalCacheDir ?: context.cacheDir, "save_import").apply { deleteRecursively(); mkdirs() }
            val directory = DocumentFile.fromTreeUri(context, treeUri) ?: run {
                fail("Invalid folder."); return@withContext false
            }
            
            updateProgress(InstallState.PREPARING, "Collecting files...")
            pullRecursiveFromDocumentFile(directory, tempDir)

            updateProgress(InstallState.INSTALLING, "Replacing .doc folder...")
            s.delete(destPath)
            s.createDirectory(destPath)
            copyRecursiveToPrivileged(tempDir, destPath)
            tempDir.deleteRecursively()
            updateProgress(InstallState.SUCCESS, "SAVE FOLDER IMPORTED")
            return@withContext true
        } catch (e: Exception) {
            fail("Import error: ${e.message}"); false
        }
    }

    suspend fun getSaveProfiles(): List<SaveProfile> = withContext(Dispatchers.IO) {
        val s = service ?: return@withContext emptyList()
        try {
            if (!s.ping()) return@withContext emptyList()
            s.createDirectory(SAVE_HUB_PATH)
            val files = s.listFiles(SAVE_HUB_PATH) ?: return@withContext emptyList()
            
            Log.i(TAG, "Listing Hub: Found ${files.size} entries in $SAVE_HUB_PATH")
            
            files.asSequence().filter { it.endsWith(".dat") }.map { fileName ->
                val profileName = fileName.removeSuffix(".dat")
                val path = "$SAVE_HUB_PATH/$fileName"
                val file = File(path)
                
                SaveProfile(
                    name = profileName,
                    timestamp = file.lastModified(),
                    fileCount = 1,
                    totalSize = s.getFileSize(path),
                    path = file,
                )
            }.sortedByDescending { it.timestamp }.toList()
        } catch (_: Exception) { emptyList() }
    }

    /**
     * Backs up only the character.2.dat file to the persistent hub.
     */
    suspend fun createSnapshot(profileName: String): Boolean = withContext(Dispatchers.IO) {
        val s = service ?: run { fail("Not connected."); return@withContext false }
        Log.i(TAG, "Creating snapshot (character only): $profileName")
        try {
            val rr3Path = getRr3Path()
            if (!s.ping()) { fail("Shizuku disconnected."); return@withContext false }
            
            val sourceFile = "$rr3Path/files/doc/character.2.dat"
            val destFile = "$SAVE_HUB_PATH/$profileName.dat"
            
            updateProgress(InstallState.PREPARING, "Checking game save...")
            if (!s.exists(sourceFile)) {
                fail("Source character.2.dat not found in game folder.")
                return@withContext false
            }

            s.createDirectory(SAVE_HUB_PATH)

            updateProgress(InstallState.INSTALLING, "Copying to Hub...")
            
            // DIRECT COPY: The service can see both paths!
            if (s.copyFile(sourceFile, destFile)) {
                updateProgress(InstallState.SUCCESS, "SNAPSHOT CREATED", "Saved to Documents")
                return@withContext true
            }
            
            fail("Copy failed. Shizuku might lack permission to write to Documents."); false
        } catch (e: Exception) {
            Log.e(TAG, "Backup exception", e)
            fail("Backup error: ${e.message}"); false
        }
    }

    suspend fun applyProfile(profile: SaveProfile): Boolean = withContext(Dispatchers.IO) {
        val s = service ?: run { fail("Not connected."); return@withContext false }
        try {
            val rr3Path = getRr3Path()
            if (!s.ping()) { fail("Not connected."); return@withContext false }
            val destFile = "$rr3Path/files/doc/character.2.dat"
            updateProgress(InstallState.INSTALLING, "Switching save file...")
            if (s.copyFile(profile.path.absolutePath, destFile)) {
                updateProgress(InstallState.SUCCESS, "PROFILE APPLIED")
                return@withContext true
            }
            fail("Restore failed."); false
        } catch (e: Exception) {
            fail("Restore error: ${e.message}"); false
        }
    }

    suspend fun deleteProfile(profile: SaveProfile) = withContext(Dispatchers.IO) {
        service?.delete(profile.path.absolutePath)
    }

    suspend fun renameProfile(profile: SaveProfile, newName: String): Boolean = withContext(Dispatchers.IO) {
        val s = service ?: return@withContext false
        if (!s.ping()) return@withContext false

        val normalizedName = newName.trim().removeSuffix(".dat").trim()
        if (!isValidSaveProfileName(normalizedName) || normalizedName == profile.name) return@withContext false

        val destinationPath = "$SAVE_HUB_PATH/$normalizedName.dat"
        if (s.exists(destinationPath)) return@withContext false

        s.move(profile.path.absolutePath, destinationPath)
    }

    private fun isValidSaveProfileName(name: String): Boolean {
        if (name.isBlank() || name == "." || name == ".." || name.length > 120) return false
        if (name.endsWith('.') || name.endsWith(' ')) return false
        if (name.any { it.isISOControl() || it in "<>:\"/\\|?*" }) return false
        return !name.contains("..")
    }

    /**
     * Removes old auto-snapshots to keep only the most recent ones.
     */
    suspend fun pruneAutoSnapshots(maxCount: Int = 5) = withContext(Dispatchers.IO) {
        try {
            val autoBackups = getSaveProfiles()
                .asSequence()
                .filter { it.name.startsWith("AutoBackup_") }
                .sortedByDescending { it.timestamp } // Newest first
                .toList()

            if (autoBackups.size > maxCount) {
                val toDelete = autoBackups.drop(maxCount)
                Log.i(TAG, "Pruning ${toDelete.size} old auto-snapshots")
                toDelete.forEach { deleteProfile(it) }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to prune auto-snapshots", e)
        }
    }

    /**
     * Fetches the list of community saves from Cloudflare.
     */
    suspend fun fetchCommunitySaves(sort: String = "newest"): List<CommunitySave> = withContext(Dispatchers.IO) {
        try {
            SaveApiClient(context).listSaves(sort)
        } catch (e: SkinApiException) {
            Log.e(TAG, "Community saves API error: status=${e.statusCode}, code=${e.errorCode}, requestId=${e.requestId}", e)
            emptyList()
        } catch (e: Exception) {
            Log.e(TAG, "Failed to fetch community saves", e)
            emptyList()
        }
    }

    /**
     * Fetches the list of community music from Cloudflare.
     */
    suspend fun fetchCommunityMusic(sort: String = "newest"): List<CommunityMusic> = withContext(Dispatchers.IO) {
        try {
            MusicApiClient(context).listMusic(sort)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to fetch community music", e)
            emptyList()
        }
    }

    /**
     * Sends a 'Like' signal to the community hub.
     */


    /**
     * Uses the AI service to analyze an error and provide a fix.
     *
     * Temporary diagnostics log authentication metadata only. Prompts, signatures, responses, and
     * unrestricted server bodies are never logged.
     */
    suspend fun troubleshootError(errorMessage: String): String? = withContext(Dispatchers.IO) {
        executeAiRequest(
            mode = "troubleshoot",
            message = errorMessage,
            connectTimeoutMs = 15_000,
            readTimeoutMs = 15_000,
        )
    }

    /**
     * Sends the latest user message to the AI Modding Assistant.
     */
    suspend fun chatWithAi(history: List<ChatMessage>): String? = withContext(Dispatchers.IO) {
        val startedAt = System.currentTimeMillis()
        val lastUserMessage = history.lastOrNull { it.isUser }?.content
        if (lastUserMessage.isNullOrBlank()) {
            logAiFailure(AiErrorCode.INVALID_REQUEST, "chat", startedAt)
            return@withContext null
        }
        executeAiRequest(
            mode = "chat",
            message = lastUserMessage,
            connectTimeoutMs = 20_000,
            readTimeoutMs = 20_000,
            historyCount = history.size,
        )
    }

    private fun executeAiRequest(
        mode: String,
        message: String,
        connectTimeoutMs: Int,
        readTimeoutMs: Int,
        historyCount: Int? = null,
    ): String? {
        val startedAt = System.currentTimeMillis()
        val method = "POST"
        val path = "/troubleshoot"
        val historyMetadata = historyCount?.let { " historyCount=$it" }.orEmpty()
        Log.i(AI_TAG, "RETR0_AI_REQUEST_STARTED mode=$mode method=$method path=$path elapsedMs=0$historyMetadata")

        var connection: HttpURLConnection? = null
        var diagnostic = AiRequestDiagnostic()
        try {
            val deviceModel = "${Build.MANUFACTURER} ${Build.MODEL} (Android ${Build.VERSION.RELEASE})"
            val jsonRequest = JSONObject().apply {
                put("error", message)
                put("device", deviceModel)
                put("type", if (mode == "chat") "general" else "troubleshoot")
            }
            val bodyBytes = jsonRequest.toString().toByteArray(Charsets.UTF_8)
            val bodySha256 = SkinRequestCanonicalizer.sha256(bodyBytes)
            diagnostic = diagnostic.copy(bodyBytes = bodyBytes.size, bodySha256 = bodySha256)
            Log.i(AI_TAG, "RETR0_AI_BODY_METADATA mode=$mode bodyBytes=${bodyBytes.size} bodySha256=$bodySha256")

            val identity = try {
                SkinInstallationIdentity(context).also {
                    Log.i(AI_TAG, "RETR0_AI_IDENTITY_CREATED mode=$mode installationId=${it.installationId}")
                }
            } catch (error: Exception) {
                Log.e(AI_TAG, "RETR0_AI_IDENTITY_FAILED mode=$mode errorType=${error.javaClass.simpleName} message=${safeAiExceptionMessage(error)}", error)
                throw error
            }
            val signed = try {
                identity.sign(method, path, bodySha256).also {
                    diagnostic = diagnostic.copy(
                        installationIdPresent = it.installationId.isNotBlank(),
                        timestampPresent = it.timestamp.isNotBlank(),
                        noncePresent = it.nonce.isNotBlank(),
                        signaturePresent = it.signature.isNotBlank(),
                    )
                    Log.i(
                        AI_TAG,
                        "RETR0_AI_SIGNING_SUCCEEDED mode=$mode method=$method path=$path " +
                            "installationId=${it.installationId} timestamp=${it.timestamp} " +
                            "nonceLength=${it.nonce.length} signatureLength=${it.signature.length} " +
                            "signatureNonEmpty=${it.signature.isNotEmpty()} bodySha256=$bodySha256",
                    )
                }
            } catch (error: Exception) {
                Log.e(AI_TAG, "RETR0_AI_SIGNING_FAILED mode=$mode method=$method path=$path errorType=${error.javaClass.simpleName} message=${safeAiExceptionMessage(error)}", error)
                throw error
            }

            connection = (URL("$COMMUNITY_API_URL$path").openConnection() as HttpURLConnection).apply {
                requestMethod = method
                doOutput = true
                connectTimeout = connectTimeoutMs
                readTimeout = readTimeoutMs
                setRequestProperty("Content-Type", "application/json; charset=utf-8")
                setRequestProperty("Accept", "application/json")
                setRequestProperty("User-Agent", "RETR0-Installer/1.0")
                setRequestProperty("X-Installation-ID", signed.installationId)
                setRequestProperty("X-Timestamp", signed.timestamp)
                setRequestProperty("X-Nonce", signed.nonce)
                setRequestProperty("X-Signature", signed.signature)
            }
            Log.i(
                AI_TAG,
                "RETR0_AI_AUTH_HEADERS mode=$mode " +
                    "X-Installation-ID=${presence(signed.installationId)} " +
                    "X-Timestamp=${presence(signed.timestamp)} X-Nonce=${presence(signed.nonce)} " +
                    "X-Signature=${presence(signed.signature)}",
            )
            Log.i(
                AI_TAG,
                "RETR0_AI_CANONICAL_METADATA mode=$mode method=$method path=$path " +
                    "installationId=${signed.installationId} timestamp=${signed.timestamp} " +
                    "nonceLength=${signed.nonce.length} bodySha256=$bodySha256",
            )
            connection.outputStream.use { it.write(bodyBytes) }

            val responseCode = connection.responseCode
            if (responseCode !in 200..299) {
                val requestId = connection.getHeaderField("X-Request-ID")
                val traceId = connection.getHeaderField("X-Trace-ID")
                val workerVersion = connection.getHeaderField("X-Worker-Version")
                val workerRevision = connection.getHeaderField("X-Worker-Revision")
                val contentType = connection.contentType
                val contentLength = connection.contentLengthLong.takeIf { it >= 0 }
                diagnostic = diagnostic.copy(
                    requestId = requestId,
                    responseContentType = contentType,
                )
                val responseBody = connection.errorStream?.bufferedReader()?.use { it.readText() }.orEmpty()
                val safeResponseBody = safeAiDiagnosticResponseBody(responseBody)
                Log.e(
                    AI_TAG,
                    "RETR0_AI_AUTH_RESPONSE mode=$mode status=$responseCode " +
                        "elapsedMs=${System.currentTimeMillis() - startedAt} requestId=${safeLogValue(requestId)} " +
                        "traceId=${safeLogValue(traceId)} workerVersion=${safeLogValue(workerVersion)} " +
                        "workerRevision=${safeLogValue(workerRevision)} contentType=${safeLogValue(contentType)} " +
                        "contentLength=${contentLength ?: "ABSENT"} responseBody=${safeResponseBody ?: "REDACTED_OR_EMPTY"}",
                )
                logAiFailure(AiErrorCode.HTTP_ERROR, mode, startedAt, httpStatus = responseCode, diagnostic = diagnostic)
                return null
            }

            val responseBody = connection.inputStream.bufferedReader().use { it.readText() }
            val response = runCatching {
                JSONObject(responseBody).optString("response").takeIf { it.isNotBlank() }
            }.getOrElse {
                logAiFailure(AiErrorCode.INVALID_RESPONSE, mode, startedAt, it, diagnostic = diagnostic)
                return null
            }
            if (response == null) {
                logAiFailure(AiErrorCode.EMPTY_RESPONSE, mode, startedAt, diagnostic = diagnostic)
                return null
            }
            Log.i(AI_TAG, "RETR0_AI_REQUEST_SUCCEEDED mode=$mode elapsedMs=${System.currentTimeMillis() - startedAt}")
            return response
        } catch (error: SocketTimeoutException) {
            logAiFailure(AiErrorCode.TIMEOUT, mode, startedAt, error, diagnostic = diagnostic)
        } catch (error: IOException) {
            logAiFailure(AiErrorCode.NETWORK, mode, startedAt, error, diagnostic = diagnostic)
        } catch (error: Exception) {
            logAiFailure(AiErrorCode.UNKNOWN, mode, startedAt, error, diagnostic = diagnostic)
        } finally {
            connection?.disconnect()
        }
        return null
    }

    private enum class AiErrorCode {
        INVALID_REQUEST,
        NETWORK,
        TIMEOUT,
        HTTP_ERROR,
        INVALID_RESPONSE,
        EMPTY_RESPONSE,
        UNKNOWN,
    }

    private data class AiRequestDiagnostic(
        val installationIdPresent: Boolean = false,
        val timestampPresent: Boolean = false,
        val noncePresent: Boolean = false,
        val signaturePresent: Boolean = false,
        val bodyBytes: Int? = null,
        val bodySha256: String? = null,
        val requestId: String? = null,
        val responseContentType: String? = null,
    )

    private fun presence(value: String?): String = if (value.isNullOrBlank()) "ABSENT" else "PRESENT"

    private fun safeLogValue(value: String?): String = value
        ?.takeIf { it.isNotBlank() && it.length <= 160 && it.all { char -> char.code in 32..126 } }
        ?: "ABSENT"

    private fun safeAiExceptionMessage(error: Throwable): String = error.message
        ?.takeIf { message ->
            message.length <= 160 &&
                !message.contains("signature", ignoreCase = true) &&
                !message.contains("key", ignoreCase = true) &&
                !message.contains("token", ignoreCase = true)
        }
        ?: "REDACTED_OR_EMPTY"

    private fun safeAiDiagnosticResponseBody(body: String): String? {
        val normalized = body.trim()
        if (normalized.isEmpty() || normalized.length > 160) return null
        val lower = normalized.lowercase()
        val forbidden = listOf("signature", "token", "authorization", "cookie", "prompt", "response", "api_key", "auth_key")
        if (forbidden.any(lower::contains)) return null
        return normalized.takeIf { it.all { char -> char == '\n' || char == '\r' || char == '\t' || char.code in 32..126 } }
    }

    private fun logAiFailure(
        code: AiErrorCode,
        mode: String,
        startedAt: Long,
        error: Throwable? = null,
        httpStatus: Int? = null,
        diagnostic: AiRequestDiagnostic = AiRequestDiagnostic(),
    ) {
        val status = httpStatus?.let { " httpStatus=$it" }.orEmpty()
        val errorType = error?.javaClass?.simpleName?.let { " errorType=$it" }.orEmpty()
        Log.e(
            AI_TAG,
            "RETR0_AI_REQUEST_FAILED code=${code.name} mode=$mode elapsedMs=${System.currentTimeMillis() - startedAt}$status$errorType " +
                "installationIdPresent=${diagnostic.installationIdPresent} " +
                "timestampPresent=${diagnostic.timestampPresent} noncePresent=${diagnostic.noncePresent} " +
                "signaturePresent=${diagnostic.signaturePresent} bodyBytes=${diagnostic.bodyBytes ?: "UNKNOWN"} " +
                "bodySha256=${diagnostic.bodySha256 ?: "UNKNOWN"} requestId=${safeLogValue(diagnostic.requestId)} " +
                "responseContentType=${safeLogValue(diagnostic.responseContentType)}",
        )
    }

    /**
     * Uploads a local save profile to the community hub.
     */
    suspend fun uploadToCommunity(
        profile: SaveProfile, 
        uploaderName: String,
        manualLevel: Int = -1,
        manualGold: Long = -1,
        manualRS: Long = -1,
        manualMS: Long = -1,
    ): Boolean = withContext(Dispatchers.IO) {
        val s = service ?: run { fail("Not connected."); return@withContext false }
        try {
            updateProgress(InstallState.PREPARING, "Scanning and Uploading...")
            
            // 1. Fetch file data via privileged service
            val tempFile = File(context.cacheDir, "upload_buffer.dat") // Use internal cache for better ownership
            if (tempFile.exists()) tempFile.delete()
            
            // USE PFD EXPORT: ensures the app process OWNS the temp file
            val pfd = android.os.ParcelFileDescriptor.open(tempFile, android.os.ParcelFileDescriptor.MODE_READ_WRITE or android.os.ParcelFileDescriptor.MODE_CREATE)
            val exportSuccess = pfd.use { s.exportFile(profile.path.absolutePath, it) }
            
            if (!exportSuccess) {
                fail("Could not read backup file.")
                return@withContext false
            }

            // 2. Determine Stats (Manual entries take priority)
            val stats = SaveScanner.scan(tempFile)
            val finalLevel = if (manualLevel >= 0) manualLevel else stats.level
            val finalGold = if (manualGold >= 0) manualGold else stats.gold
            val finalRS = if (manualRS >= 0) manualRS else stats.rs
            val finalMS = if (manualMS >= 0) manualMS else 0L // M$ is now strictly manual

            // 3. Upload through the signed accountless v1 API. The request body reports the
            // exact file length, so OkHttp sends Content-Length instead of chunked encoding.
            val result = SaveApiClient(context).upload(
                file = tempFile,
                saveName = profile.name,
                uploaderName = uploaderName,
                level = finalLevel,
                gold = finalGold,
                rs = finalRS,
                ms = finalMS,
            )
            Log.i("RETR0_UPLOAD", "Uploaded ${result.size} bytes as ${result.id}; requestId=${result.requestId}")
            updateProgress(InstallState.SUCCESS, "SHARED SUCCESSFULLY")
            true
        } catch (e: SkinApiException) {
            Log.e("RETR0_UPLOAD", "Signed save upload failed: status=${e.statusCode}, code=${e.errorCode}, requestId=${e.requestId}", e)
            val requestSuffix = e.requestId?.let { " Request ID: $it." }.orEmpty()
            fail("Upload failed${e.statusCode?.let { " (HTTP $it)" }.orEmpty()}: ${e.message}.$requestSuffix")
            false
        } catch (e: Exception) {
            val msg = e.message ?: "Unknown error"
            val fixHint = if (msg.contains("EACCES") || msg.contains("Permission denied")) "Storage permission error. Try restarting the app."
                else "Ensure you have internet and try again."
            fail("Upload error: $msg. $fixHint")
            false
        } finally {
            File(context.cacheDir, "upload_buffer.dat").delete()
        }
    }

    /**
     * Downloads a community save and adds it to the local hub.
     */
    suspend fun downloadCommunitySave(save: CommunitySave): Boolean = withContext(Dispatchers.IO) {
        val s = service ?: run { fail("Not connected."); return@withContext false }
        try {
            updateProgress(InstallState.DOWNLOADING, "Downloading ${save.customName}...")
            val destFile = File(SAVE_HUB_PATH, "${save.customName}.dat")
            s.createDirectory(SAVE_HUB_PATH)

            val encodedId = java.net.URLEncoder.encode(save.id, "UTF-8")
            val url = URL("$COMMUNITY_API_URL/v1/saves/$encodedId")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 10000
            connection.readTimeout = 70000
            connection.setRequestProperty("Accept", "application/octet-stream")
            if (BuildConfig.COMMUNITY_API_KEY.isNotBlank()) {
                connection.setRequestProperty("X-RETR0-KEY", BuildConfig.COMMUNITY_API_KEY)
            }
            if (connection.responseCode == 200) {
                val tempFile = File(context.externalCacheDir, "community_download.tmp")
                connection.inputStream.use { input ->
                    tempFile.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }
                
                if (s.copyFile(tempFile.absolutePath, destFile.absolutePath)) {
                    tempFile.delete()
                    updateProgress(InstallState.SUCCESS, "DOWNLOAD COMPLETE", "Saved to your Hub.")
                    true
                } else {
                    tempFile.delete()
                    fail("Failed to save file. FIX: Ensure you have enough storage space.")
                    false
                }
            } else {
                val fixHint = if (connection.responseCode == 404) "FIX: This file may have been deleted from the Hub." else "FIX: Server error or internet issue."
                fail("Download failed: HTTP ${connection.responseCode}. $fixHint")
                false
            }
        } catch (e: Exception) {
            val fixHint = if (e.message?.contains("No route to host") == true) "FIX: Check your internet connection." else "FIX: Server might be down."
            fail("Download error: ${e.message}. $fixHint")
            false
        }
    }

    /**
     * Replaces a game music track AND uploads it to the community in one task.
     */
    suspend fun applyAndUploadMusic(
        uri: Uri,
        trackFileName: String,
        uploaderName: String,
        customName: String,
        metadata: MusicMetadata? = null,
        artworkUri: Uri? = null,
        artworkUrl: String? = metadata?.artworkUrl,
    ): Boolean = withContext(Dispatchers.IO) {
        // 1. Apply locally while preserving the selected metadata.
        val applied = applyMusicMod(uri, trackFileName, metadata)
        if (!applied) return@withContext false

        // 2. Upload to Community.
        uploadMusic(uri, uploaderName, customName, trackFileName, artworkUri, artworkUrl)
    }

    /**
     * Uploads a music file to the community hub using a true streaming pipeline.
     */
    suspend fun uploadMusic(
        uri: Uri,
        uploaderName: String,
        customName: String,
        targetTrack: String,
        artworkUri: Uri? = null,
        artworkUrl: String? = null,
    ): Boolean = withContext(Dispatchers.IO) {
        val s = service ?: run { fail("Not connected."); return@withContext false }
        try {
            updateProgress(InstallState.PREPARING, "Uploading to Hub...")

            // Copy to temp file because the new signed API uses file hashing and size checks
            val tempFile = File(context.cacheDir, "music_upload_tmp.mp3")
            if (tempFile.exists()) tempFile.delete()

            val exportSuccess = when (uri.scheme) {
                "file" -> {
                    val sourcePath = uri.path
                    if (sourcePath.isNullOrBlank()) {
                        false
                    } else {
                        val sourceFile = File(sourcePath)
                        if (sourceFile.isFile && sourceFile.canRead()) {
                            sourceFile.inputStream().use { input ->
                                tempFile.outputStream().use { output -> input.copyTo(output) }
                            }
                            true
                        } else {
                            ParcelFileDescriptor.open(
                                tempFile,
                                ParcelFileDescriptor.MODE_READ_WRITE or ParcelFileDescriptor.MODE_CREATE or ParcelFileDescriptor.MODE_TRUNCATE,
                            ).use { destination ->
                                s.exportFile(sourcePath, destination)
                            }
                        }
                    }
                }
                else -> context.contentResolver.openInputStream(uri)?.use { input ->
                    tempFile.outputStream().use { output -> input.copyTo(output) }
                    true
                } ?: false
            }

            if (!exportSuccess || !tempFile.isFile || tempFile.length() == 0L) {
                Log.e("RETR0_UPLOAD", "Could not prepare music upload file: uri=$uri, copiedBytes=${tempFile.length()}, exportSuccess=$exportSuccess")
                fail("Could not prepare music file for upload.")
                return@withContext false
            }

            val result = MusicApiClient(context).upload(
                file = tempFile,
                musicName = customName,
                uploaderName = uploaderName,
                targetTrack = targetTrack,
            )

            if (artworkUri != null || !artworkUrl.isNullOrBlank()) {
                uploadMusicArtwork(result.id, artworkUri, artworkUrl)
            }

            Log.i("RETR0_UPLOAD", "Uploaded music ${result.size} bytes as ${result.id}; requestId=${result.requestId}")
            updateProgress(InstallState.SUCCESS, "APPLIED & SHARED SUCCESSFULLY")
            true
        } catch (e: SkinApiException) {
            Log.e("RETR0_UPLOAD", "Signed music upload failed: status=${e.statusCode}, code=${e.errorCode}, requestId=${e.requestId}", e)
            val requestSuffix = e.requestId?.let { " Request ID: $it." }.orEmpty()
            fail("Upload failed${e.statusCode?.let { " (HTTP $it)" }.orEmpty()}: ${e.message}.$requestSuffix")
            false
        } catch (e: Exception) {
            Log.e("RETR0_UPLOAD", "Fatal upload error", e)
            fail("Upload error: ${e.message}")
            false
        } finally {
            File(context.cacheDir, "music_upload_tmp.mp3").delete()
        }
    }

    private suspend fun uploadMusicArtwork(musicId: String, artworkUri: Uri?, artworkUrl: String?) {
        val client = MusicApiClient(context)
        val source = File.createTempFile("music_cover_source_", ".tmp", context.cacheDir)
        var encoded: File? = null
        var connection: HttpURLConnection? = null
        try {
            if (artworkUri != null) {
                val contentType = context.contentResolver.getType(artworkUri)
                    ?.substringBefore(';')
                    ?.trim()
                    ?.lowercase(Locale.US)
                require(contentType == null || contentType in SUPPORTED_MUSIC_ARTWORK_TYPES) { "Unsupported artwork content type" }
                val input = context.contentResolver.openInputStream(artworkUri)
                    ?: throw IOException("Selected artwork could not be opened")
                input.use { source.outputStream().use(it::copyTo) }
            } else {
                val remoteUrl = artworkUrl?.takeIf { it.isNotBlank() } ?: return
                val url = URL(remoteUrl)
                require(url.protocol.equals("https", true) && url.host.isNotBlank()) { "Artwork URL must use HTTPS" }
                connection = (url.openConnection() as HttpURLConnection).apply {
                    instanceFollowRedirects = false
                    connectTimeout = 10_000
                    readTimeout = 20_000
                    setRequestProperty("Accept", "image/jpeg,image/png,image/webp")
                }
                val status = connection.responseCode
                require(status in 200..299) { "Artwork download returned HTTP $status" }
                val contentType = connection.contentType
                    ?.substringBefore(';')
                    ?.trim()
                    ?.lowercase(Locale.US)
                require(contentType == null || contentType in SUPPORTED_MUSIC_ARTWORK_TYPES) { "Unsupported artwork content type" }
                val declaredLength = connection.contentLengthLong
                require(declaredLength < 0 || declaredLength <= MAX_MUSIC_ARTWORK_DOWNLOAD_BYTES) { "Artwork download is too large" }
                connection.inputStream.use { input ->
                    source.outputStream().use { output ->
                        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
                        var total = 0L
                        while (true) {
                            val count = input.read(buffer)
                            if (count < 0) break
                            total += count
                            require(total <= MAX_MUSIC_ARTWORK_DOWNLOAD_BYTES) { "Artwork download is too large" }
                            output.write(buffer, 0, count)
                        }
                    }
                }
            }

            val processed = MusicArtworkProcessor.process(source, context.cacheDir)
            encoded = processed.file
            encoded.inputStream().use { client.uploadCover(musicId, it, processed.contentType) }
        } finally {
            connection?.disconnect()
            source.delete()
            encoded?.delete()
        }
    }

    /**
     * Downloads a community music file and applies it to the game or saves to library.
     */
    suspend fun downloadCommunityMusic(music: CommunityMusic, toLibrary: Boolean = false): Boolean = withContext(Dispatchers.IO) {
        val s = service ?: run { fail("Not connected."); return@withContext false }
        try {
            val rr3Path = getRr3Path()
            updateProgress(InstallState.DOWNLOADING, "Downloading ${music.customName}...")
            
            // Ensure necessary directories exist
            if (toLibrary) s.createDirectory(MUSIC_LIBRARY_PATH)
            else backupOriginalMusic()

            val encodedId = java.net.URLEncoder.encode(music.id, "UTF-8")
            val url = URL("$COMMUNITY_API_URL/v1/music/$encodedId")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 10000
            connection.readTimeout = 70000
            connection.setRequestProperty("Accept", "application/octet-stream")
            
            // Signs the download request for the new authenticated /v1/music endpoint
            val identity = SkinInstallationIdentity(context)
            val signed = identity.sign("GET", "/v1/music/$encodedId", SkinRequestCanonicalizer.sha256(ByteArray(0)))

            connection.setRequestProperty("X-Installation-ID", signed.installationId)
            connection.setRequestProperty("X-Timestamp", signed.timestamp)
            connection.setRequestProperty("X-Nonce", signed.nonce)
            connection.setRequestProperty("X-Signature", signed.signature)
                
            if (connection.responseCode == 200) {
                val tempFile = File(context.externalCacheDir, "music_download.tmp")
                connection.inputStream.use { input ->
                    tempFile.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }
                
                val destPath = if (toLibrary) {
                    val sanitizedName = music.customName.replace(Regex("[^a-zA-Z0-9._-]"), "_")
                    val fileName = if (sanitizedName.endsWith(".mp3")) sanitizedName else "$sanitizedName.mp3"
                    "$MUSIC_LIBRARY_PATH/$fileName"
                } else {
                    "$rr3Path/files/.depot/audio/music/${music.targetTrack}"
                }

                if (s.copyFile(tempFile.absolutePath, destPath)) {
                    tempFile.delete()
                    if (toLibrary) {
                        updateProgress(InstallState.SUCCESS, "SAVED TO LIBRARY", music.customName)
                    } else {
                        updateProgress(InstallState.SUCCESS, "MUSIC MOD APPLIED", "Track: ${music.targetTrack}")
                    }
                    true
                } else {
                    tempFile.delete()
                    fail("Failed to apply music. FIX: Ensure Shizuku is running and storage is not full.")
                    false
                }
            } else {
                val fixHint = if (connection.responseCode == 404) "FIX: This track may have been removed from the Hub." else "FIX: Server error or internet issue."
                fail("Download failed: HTTP ${connection.responseCode}. $fixHint")
                false
            }
        } catch (e: Exception) {
            val fixHint = if (e.message?.contains("reset") == true) "FIX: Internet unstable or file too large." else "FIX: Check your internet connection."
            fail("Download error: ${e.message}. $fixHint")
            false
        }
    }

    /**
     * Fetches metadata for a YouTube video with finite extractor/network waits.
     */
    suspend fun fetchYouTubeInfo(youtubeUrl: String): YouTubeInfo? = withContext(Dispatchers.IO) {
        if (!isValidYouTubeUrl(youtubeUrl)) {
            failYouTube(YouTubeErrorCode.INVALID_URL, "metadata", youtubeUrl)
            return@withContext null
        }
        if (!isDownloaderInit && !initDownloader(context)) {
            failYouTube(YouTubeErrorCode.INITIALIZATION_FAILED, "initialization", youtubeUrl)
            return@withContext null
        }
        val startedAt = System.currentTimeMillis()
        updateProgress(InstallState.PREPARING, "Reading YouTube video details...", eta = "Up to 45s")
        logYouTube("METADATA_STARTED", "stage=metadata host=${youtubeHost(youtubeUrl)}")
        return@withContext try {
            val info = executeYouTubeWithAuthFallback(
                youtubeUrl = youtubeUrl,
                timeoutMs = YOUTUBE_METADATA_TIMEOUT_MS,
                stage = YouTubeAuthFallback.Stage.METADATA,
            ) { cookiesFile ->
                YoutubeDLRequest(youtubeUrl).apply {
                    addYouTubeNetworkOptions(this)
                    addYouTubeExtractorOptions(this)
                    addOption("--no-playlist")
                    cookiesFile?.let { addOption("--cookies", it.absolutePath) }
                }.let { request ->
                    withTimeout(YOUTUBE_METADATA_TIMEOUT_MS) { YoutubeDL.getInstance().getInfo(request) }
                }
            }
            logYouTube("METADATA_SUCCEEDED", "stage=metadata elapsedMs=${System.currentTimeMillis() - startedAt}")
            YouTubeInfo(
                title = info.title ?: "YouTube Audio",
                thumbnailUrl = info.thumbnail,
                url = youtubeUrl,
            )
        } catch (error: TimeoutCancellationException) {
            failYouTube(YouTubeErrorCode.METADATA_TIMEOUT, "metadata", youtubeUrl, startedAt, error)
            null
        } catch (error: CancellationException) {
            failYouTube(YouTubeErrorCode.CANCELLED, "metadata", youtubeUrl, startedAt, error)
            throw error
        } catch (error: Exception) {
            failYouTube(YouTubeErrorCode.METADATA_FAILED, "metadata", youtubeUrl, startedAt, error)
            null
        }
    }

    /**
     * Downloads audio from a YouTube URL locally using yt-dlp.
     * Returns the local File and info if successful, null otherwise.
     */
    suspend fun downloadYouTubeAudio(youtubeUrl: String): YouTubeDownloadResult? = withContext(Dispatchers.IO) {
        val tempDir = File(context.externalCacheDir ?: context.cacheDir, "youtube_work")
        try {
            if (!isValidYouTubeUrl(youtubeUrl)) {
                failYouTube(YouTubeErrorCode.INVALID_URL, "validation", youtubeUrl)
                return@withContext null
            }
            updateProgress(InstallState.PREPARING, "Initializing local YouTube downloader...", eta = "Starting")
            if (!isDownloaderInit && !initDownloader(context)) {
                failYouTube(YouTubeErrorCode.INITIALIZATION_FAILED, "initialization", youtubeUrl)
                return@withContext null
            }

            val info = fetchYouTubeInfo(youtubeUrl) ?: return@withContext null
            if (progress.value.state == InstallState.FAILED) return@withContext null

            if (tempDir.exists()) tempDir.deleteRecursively()
            if (!tempDir.mkdirs() && !tempDir.isDirectory) {
                failYouTube(YouTubeErrorCode.DOWNLOAD_FAILED, "workspace", youtubeUrl)
                return@withContext null
            }

            val startedAt = System.currentTimeMillis()
            updateProgress(InstallState.DOWNLOADING, "Downloading YouTube audio locally...", eta = "Up to 8 min")
            logYouTube("DOWNLOAD_STARTED", "stage=download host=${youtubeHost(youtubeUrl)}")
            executeYouTubeWithAuthFallback(
                youtubeUrl = youtubeUrl,
                timeoutMs = YOUTUBE_DOWNLOAD_TIMEOUT_MS,
                stage = YouTubeAuthFallback.Stage.DOWNLOAD,
            ) { cookiesFile ->
                val request = YoutubeDLRequest(youtubeUrl).apply {
                    addOption("--format", "bestaudio[ext=m4a]/bestaudio[ext=webm]/bestaudio")
                    addOption("--extract-audio")
                    addOption("--audio-format", "mp3")
                    addOption("--audio-quality", "0")
                    addOption("-o", "${tempDir.absolutePath}/%(title)s.%(ext)s")
                    addOption("--no-playlist")
                    addOption("--no-mtime")
                    addYouTubeNetworkOptions(this)
                    addYouTubeExtractorOptions(this)
                    cookiesFile?.let { addOption("--cookies", it.absolutePath) }
                }
                withTimeout(YOUTUBE_DOWNLOAD_TIMEOUT_MS) {
                    YoutubeDL.getInstance().execute(request) { progress, eta, line ->
                        updateProgress(
                            state = InstallState.DOWNLOADING,
                            step = if (line.contains("ffmpeg", ignoreCase = true)) "Converting YouTube audio to MP3..." else "Downloading YouTube audio: $progress%",
                            fileProgress = progress / 100f,
                            overallProgress = progress / 100f,
                            eta = if (eta > 0) "${eta}s" else "Working...",
                        )
                    }
                }
            }

            val downloadedFile = tempDir.listFiles()?.find { it.extension.equals("mp3", ignoreCase = true) && it.length() > 0 }
            if (downloadedFile == null) {
                failYouTube(YouTubeErrorCode.OUTPUT_MISSING, "output", youtubeUrl, startedAt)
                return@withContext null
            }
            val finalFile = File(context.externalCacheDir ?: context.cacheDir, "youtube_download.mp3")
            if (finalFile.exists() && !finalFile.delete()) {
                failYouTube(YouTubeErrorCode.DOWNLOAD_FAILED, "output_replace", youtubeUrl, startedAt)
                return@withContext null
            }
            if (!downloadedFile.renameTo(finalFile) || !finalFile.isFile || finalFile.length() <= 0) {
                failYouTube(YouTubeErrorCode.OUTPUT_MISSING, "output_move", youtubeUrl, startedAt)
                return@withContext null
            }
            tempDir.deleteRecursively()
            logYouTube("DOWNLOAD_SUCCEEDED", "stage=complete elapsedMs=${System.currentTimeMillis() - startedAt} bytes=${finalFile.length()}")
            YouTubeDownloadResult(finalFile, info)
        } catch (error: TimeoutCancellationException) {
            failYouTube(YouTubeErrorCode.DOWNLOAD_TIMEOUT, "download", youtubeUrl, error = error)
            null
        } catch (error: CancellationException) {
            failYouTube(YouTubeErrorCode.CANCELLED, "download", youtubeUrl, error = error)
            throw error
        } catch (error: Exception) {
            failYouTube(YouTubeErrorCode.DOWNLOAD_FAILED, "download", youtubeUrl, error = error)
            null
        } finally {
            if (progress.value.state == InstallState.FAILED) tempDir.deleteRecursively()
        }
    }

    private suspend fun <T> executeYouTubeWithAuthFallback(
        youtubeUrl: String,
        timeoutMs: Long,
        stage: YouTubeAuthFallback.Stage,
        operation: suspend (File?) -> T,
    ): T {
        require(youtubeUrl.isNotBlank() && timeoutMs > 0)
        val retryState = YouTubeAuthFallback.RetryState(stage)
        var temporaryCookies: File? = null
        try {
            return try {
                logYouTube("ATTEMPT", "stage=${stage.name.lowercase()} auth=anonymous")
                operation(null)
            } catch (error: CancellationException) {
                throw error
            } catch (error: Exception) {
                val hasCookies = youtubeCookieStore.hasCookies()
                val classification = YouTubeAuthFallback.classify(stage, error)
                val attempt = retryState.nextAttempt(hasCookies, classification)
                logYouTube(
                    "FALLBACK_CLASSIFIED",
                    "stage=${stage.name.lowercase()} classification=${classification.name} cookiesConfigured=$hasCookies retryAuthenticated=${attempt == YouTubeAuthFallback.Attempt.AUTHENTICATED}",
                )
                if (attempt != YouTubeAuthFallback.Attempt.AUTHENTICATED) throw error
                val cookies = youtubeCookieStore.readNetscapeCookies() ?: throw error
                temporaryCookies = withContext(Dispatchers.IO) {
                    File.createTempFile("retr0-youtube-", ".cookies.txt", context.cacheDir)
                }
                temporaryCookies!!.writeText(cookies)
                logYouTube("ATTEMPT", "stage=${stage.name.lowercase()} auth=authenticated")
                operation(temporaryCookies)
            }
        } finally {
            temporaryCookies?.let { cookieFile ->
                val deleted = runCatching { !cookieFile.exists() || cookieFile.delete() }.getOrDefault(false)
                logYouTube("COOKIE_TEMP_CLEANUP", "stage=${stage.name.lowercase()} deleted=$deleted")
            }
        }
    }

    private fun addYouTubeNetworkOptions(request: YoutubeDLRequest) {
        request.addOption("--socket-timeout", YOUTUBE_SOCKET_TIMEOUT_SECONDS.toString())
        request.addOption("--retries", YOUTUBE_RETRIES.toString())
        request.addOption("--fragment-retries", YOUTUBE_RETRIES.toString())
    }

    private fun addYouTubeExtractorOptions(request: YoutubeDLRequest) {
        // Avoid clients whose media URLs require a PO token that this local Android app cannot supply.
        request.addOption("--extractor-args", "youtube:player_client=android_vr,web_safari")
    }

    private fun isValidYouTubeUrl(rawUrl: String): Boolean = runCatching {
        val url = URL(rawUrl.trim())
        url.protocol == "https" && (url.host.equals("youtube.com", true) || url.host.endsWith(".youtube.com", true) || url.host.equals("youtu.be", true))
    }.getOrDefault(false)

    private fun youtubeHost(rawUrl: String): String = runCatching { URL(rawUrl).host }.getOrDefault("invalid")

    private fun logYouTube(event: String, details: String) {
        Log.i(TAG, "RETR0_YT_$event $details")
    }

    private fun failYouTube(
        code: YouTubeErrorCode,
        stage: String,
        rawUrl: String,
        startedAt: Long? = null,
        error: Throwable? = null,
    ) {
        val elapsed = startedAt?.let { " elapsedMs=${System.currentTimeMillis() - it}" }.orEmpty()
        val errorType = error?.javaClass?.simpleName?.let { " errorType=$it" }.orEmpty()
        Log.e(TAG, "RETR0_YT_FAILED code=${code.name} stage=$stage host=${youtubeHost(rawUrl)}$elapsed$errorType", error)
        fail("${code.userMessage} Check your connection or try another video.")
    }

    private fun copyRecursiveToPrivileged(localDir: File, privilegedDest: String): Int {
        val s = service ?: return 0
        var count = 0
        localDir.listFiles()?.forEach { file ->
            val destPath = "$privilegedDest/${file.name}"
            if (file.isDirectory) {
                s.createDirectory(destPath)
                count += copyRecursiveToPrivileged(file, destPath)
            } else {
                if (s.copyFile(file.absolutePath, destPath)) count++
            }
        }
        return count
    }

    private fun pullRecursiveFromDocumentFile(source: DocumentFile, destDir: File) {
        source.listFiles().forEach { docFile ->
            val name = docFile.name ?: return@forEach
            if (docFile.isDirectory) {
                val subDir = File(destDir, name).apply { mkdirs() }
                pullRecursiveFromDocumentFile(docFile, subDir)
            } else {
                val targetFile = File(destDir, name)
                context.contentResolver.openInputStream(docFile.uri)?.use { input ->
                    targetFile.outputStream().use { output -> input.copyTo(output) }
                }
            }
        }
    }

    private fun updateProgress(
        state: InstallState, 
        step: String, 
        currentFile: String = "", 
        fileProgress: Float = 0f,
        overallProgress: Float = 0f,
        completedFiles: Int = 0,
        totalFiles: Int = 0,
        eta: String = "",
    ) {
        _progress.update { current ->
            // ENSURE PROGRESS ONLY GOES FORWARD
            val finalOverall = if (overallProgress > current.overallProgress) overallProgress else current.overallProgress

            current.copy(
                state = state, 
                currentStep = step, 
                currentFile = currentFile, 
                fileProgress = fileProgress.coerceIn(0f, 1f),
                overallProgress = finalOverall.coerceIn(0f, 1f),
                completedFiles = completedFiles,
                totalFiles = totalFiles,
                // KEEP PREVIOUS ETA IF THE NEW ONE IS BLANK AND WE ARE STILL ACTIVE
                eta = if (eta.isBlank() && ((state != InstallState.SUCCESS) && (state != InstallState.FAILED) && (state != InstallState.IDLE))) {
                    current.eta.ifBlank { "Calculating..." }
                } else {
                    eta
                },
            )
        }
    }

    private fun fail(message: String) {
        _progress.update { it.copy(state = InstallState.FAILED, errorMessage = message) }
    }

    fun reset() {
        _progress.value = InstallProgress()
    }

    /**
     * Backs up original game music to persistent storage if not already done.
     */
    suspend fun backupOriginalMusic(): Boolean = withContext(Dispatchers.IO) {
        val s = service ?: return@withContext false
        try {
            val rr3Path = getRr3Path()
            val musicDir = "$rr3Path/files/.depot/audio/music"
            val tracks = listOf(
                "IntroLogos.mp3",
                "IntroCinematic.mp3",
                "Menu_Music.mp3",
                "Garage_Music.mp3",
                "LoadingScreen.mp3",
                "Ryan_Kurt_Time.mp3",
                "Ryan_Kurt_Strike.mp3",
                "Ryan_Kurt_Further.mp3",
                "Ryan_Kurt_Taplein.mp3",
                "Ryan_Kurt_Connected.mp3",
                "Ryan_Kurt_Red_Green.mp3",
                "Formula_E_EJ_Ignition.mp3",
                "Formula_E_EJ_Buenos_Aires.mp3",
                "Formula_E_EJ_Clear_Blue_Sky.mp3",
                "Ryan_Kurt_Time_RICCI_Remix.mp3",
                "Ryan_Kurt_Taplein_SIPPY_Remix.mp3",
                "Ryan_Kurt_Further_Corrupt_Remix.mp3",
            )

            s.createDirectory(MUSIC_HUB_PATH)
            var backedUpCount = 0

            tracks.forEach { fileName ->
                val source = "$musicDir/$fileName"
                val dest = "$MUSIC_HUB_PATH/$fileName"
                
                // Only backup if the backup doesn't already exist (to avoid backing up a modded file)
                if (s.exists(source) && !s.exists(dest)) {
                    Log.i(TAG, "Backing up original music: $fileName")
                    if (s.copyFile(source, dest)) backedUpCount++
                }
            }
            return@withContext true
        } catch (e: Exception) {
            Log.e(TAG, "Music backup failed", e)
            false
        }
    }

    /**
     * Scans the game's audio directory recursively, excluding the 'music' folder.
     * Detects all common audio/video container formats used by the game engine.
     */
    suspend fun getAllMusicSlots(): List<String> = withContext(Dispatchers.IO) {
        val s = service ?: return@withContext emptyList()
        try {
            val rr3Path = getRr3Path()
            val audioDir = "$rr3Path/files/.depot/audio"
            
            val slots = mutableListOf<String>()
            val extensions = listOf(".mp3", ".mp4", ".wav", ".ogg", ".aac", ".m4a", ".flac", ".3gp")
            
            fun scanRecursive(currentPath: String, relativePrefix: String) {
                s.listFiles(currentPath)?.forEach { name ->
                    val fullPath = "$currentPath/$name"
                    val relativePath = if (relativePrefix.isEmpty()) name else "$relativePrefix/$name"
                    
                    if (s.isDirectory(fullPath)) {
                        scanRecursive(fullPath, relativePath)
                    } else {
                        val lower = name.lowercase()
                        if (extensions.any { lower.endsWith(it) }) {
                            slots.add(relativePath)
                        }
                    }
                }
            }

            // Scan the entire audio folder
            s.listFiles(audioDir)?.forEach { name ->
                if (name != "music") { // Explicitly exclude the 'music' folder
                    val fullPath = "$audioDir/$name"
                    if (s.isDirectory(fullPath)) {
                        scanRecursive(fullPath, name)
                    } else {
                        val lower = name.lowercase()
                        if (extensions.any { lower.endsWith(it) }) slots.add(name)
                    }
                }
            }
            
            slots.sorted().toList()
        } catch (e: Exception) {
            Log.e(TAG, "Failed to list audio slots", e)
            emptyList()
        }
    }

    /**
     * Scans the factory original res folder for all available graphics profiles.
     */
    suspend fun getAvailablePresets(): List<String> = withContext(Dispatchers.IO) {
        val s = service ?: return@withContext emptyList()
        try {
            val rr3Path = getRr3Path()
            val resEds = "$rr3Path/files/apk/res/eds"
            val resFeats = "$rr3Path/files/apk/res/feats"
            
            val presets = mutableSetOf<String>()
            
            s.listFiles(resEds)?.forEach { 
                if (it.endsWith(".plist")) presets.add(it.removeSuffix(".plist"))
            }
            
            s.listFiles(resFeats)?.forEach { 
                if (it.endsWith(".feats")) presets.add(it.removeSuffix(".feats"))
            }
            
            presets.asSequence().sorted().toList()
        } catch (_: Exception) { emptyList() }
    }

    /**
     * Overwrites all standard quality tiers with the selected preset.
     * Source: apk/res (Factory Original)
     */
    suspend fun applyGraphicsPreset(presetName: String): Boolean = withContext(Dispatchers.IO) {
        val s = service ?: run { fail("Not connected."); return@withContext false }
        try {
            updateProgress(InstallState.PREPARING, "Preparing graphics swap...")
            
            val rr3Path = getRr3Path()
            val resEds = "$rr3Path/files/apk/res/eds"
            val resFeats = "$rr3Path/files/apk/res/feats"
            
            val depotEds = "$rr3Path/files/.depot/eds"
            val depotFeats = "$rr3Path/files/.depot/feats"

            val sourcePlist = "$resEds/$presetName.plist"
            val sourceFeats = "$resFeats/$presetName.feats"

            val hasPlist = s.exists(sourcePlist)
            val hasFeats = s.exists(sourceFeats)

            if (!hasPlist && !hasFeats) {
                fail("Preset source not found: $presetName (.plist or .feats)")
                return@withContext false
            }

            val tiers = listOf(
                "AndroidLow", "AndroidMid", "AndroidMid2", 
                "AndroidHigh", "AndroidXtraHigh", "AndroidUltraWide", "SimRigTV"
            )

            updateProgress(InstallState.INSTALLING, "Injecting $presetName configuration...")
            
            var successCount = 0
            tiers.forEach { tier ->
                // Apply Plist (if exists)
                if (hasPlist) {
                    val destPlist = "$depotEds/$tier.plist"
                    if (s.copyFile(sourcePlist, destPlist)) {
                        successCount++
                    }
                }
                
                // Apply Feats (if exists)
                if (hasFeats) {
                    val destFeats = "$depotFeats/$tier.feats"
                    if (s.copyFile(sourceFeats, destFeats)) {
                        if (!hasPlist) successCount++ // Count as success if feats-only
                    }
                }
            }

            if (successCount > 0) {
                updateProgress(InstallState.SUCCESS, "GRAPHICS APPLIED", "$presetName active across all tiers")
                return@withContext true
            }
            
            fail("Failed to apply graphics preset.")
            false
        } catch (e: Exception) {
            fail("Graphics Error: ${e.message}")
            false
        }
    }

    /**
     * Restores the original graphics configuration from the factory apk/res folder.
     */
    suspend fun restoreOriginalGraphics(): Boolean = withContext(Dispatchers.IO) {
        val s = service ?: run { fail("Not connected."); return@withContext false }
        try {
            val rr3Path = getRr3Path()
            val resPath = "$rr3Path/files/apk/res"
            val depotPath = "$rr3Path/files/.depot"

            if (!s.exists(resPath)) {
                fail("Original graphics source (apk/res) not found.")
                return@withContext false
            }

            updateProgress(InstallState.INSTALLING, "Restoring original graphics...")
            
            // Restore EDS
            if (s.exists("$resPath/eds")) {
                s.delete("$depotPath/eds")
                s.createDirectory("$depotPath/eds")
                s.copyDirectory("$resPath/eds", "$depotPath/eds")
            }

            // Restore FEATS
            if (s.exists("$resPath/feats")) {
                s.delete("$depotPath/feats")
                s.createDirectory("$depotPath/feats")
                s.copyDirectory("$resPath/feats", "$depotPath/feats")
            }

            updateProgress(InstallState.SUCCESS, "ORIGINAL GRAPHICS RESTORED")
            true
        } catch (e: Exception) {
            fail("Restore Error: ${e.message}")
            false
        }
    }

    /**
     * Replaces a game music track with a user-provided file.
     */
    /**
     * Internal: Ensures a permanent backup of the original game file exists 
     * before we apply any modifications.
     */
    private fun ensureBackup(relativePath: String): Boolean {
        val s = service ?: return false
        val rr3Path = getRr3Path()
        
        // Normalize the relative path to be used for the backup
        val normalizedPath = if (relativePath.contains("/")) relativePath else "music/$relativePath"
        val backupPath = "$MUSIC_HUB_PATH/$normalizedPath"
        val gamePath = "$rr3Path/files/.depot/audio/$normalizedPath"
        
        if (s.exists(backupPath)) return true // Already backed up
        
        if (!s.exists(gamePath)) {
            Log.e(TAG, "Original file not found to backup: $gamePath")
            return false
        }
        
        s.createDirectory(File(backupPath).parent ?: MUSIC_HUB_PATH)
        Log.i(TAG, "Creating original backup: $normalizedPath")
        return s.copyFile(gamePath, backupPath)
    }

    /**
     * Replaces a game music track with a user-provided file.
     */
    suspend fun applyMusicMod(
        uri: Uri, 
        trackFileName: String, 
        metadata: MusicMetadata? = null,
    ): Boolean = withContext(Dispatchers.IO) {
        val s = service ?: return@withContext false
        try {
            val rr3Path = getRr3Path()
            // Support both direct filenames (legacy) and relative paths (sfx/file.mp3)
            val relativePath = if (trackFileName.contains("/")) trackFileName else "music/$trackFileName"
            val destPath = "$rr3Path/files/.depot/audio/$relativePath"
            
            // 1. Ensure permanent backup exists before we overwrite
            ensureBackup(relativePath)

            // 2. Import the new music
            val pfd = context.contentResolver.openFileDescriptor(uri, "r") ?: return@withContext false
            updateProgress(InstallState.INSTALLING, "Applying Audio Mod: ${trackFileName.substringAfterLast("/")}")
            
            if (s.importFile(pfd, destPath)) {
                // Save Artwork for the core slot too if provided
                if (metadata?.artworkUrl != null) {
                    try {
                        val baseName = trackFileName.substringAfterLast("/").removeSuffix(".mp3")
                        val artFile = File(context.cacheDir, "art_buffer_core.jpg")
                        URL(metadata.artworkUrl).openStream().use { input ->
                            artFile.outputStream().use { output -> input.copyTo(output) }
                        }
                        s.copyFile(artFile.absolutePath, "$MUSIC_LIBRARY_PATH/$baseName.jpg")
                    } catch (_: Exception) {}
                }

                updateProgress(InstallState.SUCCESS, "AUDIO MOD APPLIED")
                return@withContext true
            }
            false
        } catch (e: Exception) {
            fail("Music Mod Error: ${e.message}")
            false
        }
    }

    /**
     * Restores an audio file to its factory original state from the Hub.
     */
    suspend fun revertAudioMod(relativePath: String): Boolean = withContext(Dispatchers.IO) {
        val s = service ?: return@withContext false
        try {
            val rr3Path = getRr3Path()
            
            // Normalize path for lookup
            val normalizedPath = if (relativePath.contains("/")) relativePath else "music/$relativePath"
            
            // Foolproof path checking: Try relative path first, then check if it's a flat filename
            val backupPath = if (s.exists("$MUSIC_HUB_PATH/$normalizedPath")) {
                "$MUSIC_HUB_PATH/$normalizedPath"
            } else if (s.exists("$MUSIC_HUB_PATH/${relativePath.substringAfterLast("/")}")) {
                "$MUSIC_HUB_PATH/${relativePath.substringAfterLast("/")}"
            } else {
                null
            }
            
            if (backupPath == null) {
                fail("No original backup found for: $relativePath")
                return@withContext false
            }
            
            val gamePath = "$rr3Path/files/.depot/audio/$normalizedPath"

            updateProgress(InstallState.INSTALLING, "Reverting to original: ${relativePath.substringAfterLast("/")}")
            if (s.copyFile(backupPath, gamePath)) {
                updateProgress(InstallState.SUCCESS, "AUDIO REVERTED")
                return@withContext true
            }
            false
        } catch (e: Exception) {
            fail("Revert Error: ${e.message}")
            false
        }
    }

    /**
     * Returns a list of all relative paths that currently have an original backup.
     */
    suspend fun getBackedUpSlots(): Set<String> = withContext(Dispatchers.IO) {
        val s = service ?: return@withContext emptySet()
        val slots = mutableSetOf<String>()
        try {
            fun scanBackupRecursive(currentPath: String, relativePrefix: String) {
                s.listFiles(currentPath)?.forEach { name ->
                    val fullPath = "$currentPath/$name"
                    val relativePath = if (relativePrefix.isEmpty()) name else "$relativePrefix/$name"
                    if (s.isDirectory(fullPath)) {
                        scanBackupRecursive(fullPath, relativePath)
                    } else {
                        slots.add(relativePath)
                    }
                }
            }
            scanBackupRecursive(MUSIC_HUB_PATH, "")
        } catch (_: Exception) {}
        slots
    }

    /**
     * Restores a specific original music track from the Hub.
     */
    suspend fun restoreMusic(trackFileName: String): Boolean = withContext(Dispatchers.IO) {
        val s = service ?: return@withContext false
        try {
            val rr3Path = getRr3Path()
            // Support both direct filenames (legacy) and relative paths (sfx/file.mp3)
            val relativePath = if (trackFileName.contains("/")) trackFileName else "music/$trackFileName"
            
            // Original music backups are flattened in the hub for now, but we check audio/ first
            val source = "$MUSIC_HUB_PATH/${trackFileName.substringAfterLast("/")}"
            val dest = "$rr3Path/files/.depot/audio/$relativePath"

            if (!s.exists(source)) {
                // Try full relative path too for new dynamic backups
                if (revertAudioMod(relativePath)) return@withContext true
                
                fail("Original backup for $trackFileName not found.")
                return@withContext false
            }

            updateProgress(InstallState.INSTALLING, "Restoring original: ${trackFileName.substringAfterLast("/")}")
            if (s.copyFile(source, dest)) {
                updateProgress(InstallState.SUCCESS, "AUDIO RESTORED")
                return@withContext true
            }
            false
        } catch (e: Exception) {
            fail("Restore Error: ${e.message}")
            false
        }
    }

    /**
     * Scans the local persistent music library and returns available tracks.
     */
    suspend fun getLibraryMusic(): List<LibraryMusic> = withContext(Dispatchers.IO) {
        val s = service ?: return@withContext emptyList()
        try {
            s.createDirectory(MUSIC_LIBRARY_PATH)
            val mp3Files = (s.listFiles(MUSIC_LIBRARY_PATH) ?: emptyArray())
                .filter { it.endsWith(".mp3", ignoreCase = true) }
                .filter { MusicLibrarySidecarCodec.sanitizeBaseName(it) + ".mp3" == it }

            coroutineScope {
                mp3Files.map { fileName ->
                    async {
                        val absolutePath = "$MUSIC_LIBRARY_PATH/$fileName"
                        val baseName = fileName.removeSuffix(".mp3")
                        val sidecarPath = "$MUSIC_LIBRARY_PATH/${MusicLibrarySidecarCodec.sidecarFileName(baseName)}"
                        val artworkPath = "$MUSIC_LIBRARY_PATH/${MusicLibrarySidecarCodec.artworkFileName(baseName)}"
                        val legacyArtworkPath = "$MUSIC_LIBRARY_PATH/$baseName.jpg"
                        val sidecar = MusicLibrarySidecarCodec.parse(s.readFile(sidecarPath))

                        val fallbackMeta = if (sidecar == null) {
                            runCatching { MetadataFetcher.fetchMetadata(baseName) }.getOrNull()
                        } else null
                        val resolved = MusicLibrarySidecarCodec.resolve(
                            sidecar,
                            fallbackMeta?.get("trackName"),
                            fallbackMeta?.get("artistName"),
                        )
                        val localArtwork = when {
                            s.exists(artworkPath) -> "file://$artworkPath"
                            s.exists(legacyArtworkPath) -> "file://$legacyArtworkPath"
                            else -> null
                        }

                        LibraryMusic(
                            fileName = fileName,
                            fileSize = s.getFileSize(absolutePath),
                            absolutePath = absolutePath,
                            trackName = resolved?.trackName,
                            artistName = resolved?.artistName,
                            artworkUrl = localArtwork ?: fallbackMeta?.get("artworkUrl"),
                        )
                    }
                }.awaitAll()
            }.sortedBy { it.trackName ?: it.fileName }
        } catch (e: Exception) {
            Log.e(TAG, "Critical failure fetching music library", e)
            emptyList()
        }
    }

    /**
     * Saves a music file and its sidecars with temporary names before publishing them.
     */
    suspend fun saveToLibrary(
        uri: Uri,
        customName: String,
        thumbUrl: String? = null,
        metadata: MusicMetadata? = null,
        artworkUri: Uri? = null,
        artworkUrl: String? = metadata?.artworkUrl ?: thumbUrl,
    ): Boolean = withContext(Dispatchers.IO) {
        val startedAt = SystemClock.elapsedRealtime()
        val baseName = MusicLibrarySidecarCodec.sanitizeBaseName(customName)
        val nameKey = libraryNameKey(baseName)
        val uriScheme = uri.scheme?.lowercase(Locale.ROOT) ?: "none"
        val hasMetadata = metadata != null
        val hasArtworkRequest = artworkUri != null || !artworkUrl.isNullOrBlank()
        var stage = "service"
        var mp3Published = false
        var sidecarPublished = false
        var artworkPublished = false
        var artworkCache: File? = null

        fun event(code: String, result: Boolean, error: Throwable? = null, detail: String? = null) {
            val message = buildString {
                append("code=$code stage=$stage result=$result elapsedMs=${SystemClock.elapsedRealtime() - startedAt}")
                append(" uriScheme=$uriScheme nameKey=$nameKey nameLength=${baseName.length}")
                append(" sourceCategory=music sourceExt=mp3 targetCategory=music_library targetExt=${detail ?: "none"}")
                append(" hasMetadata=$hasMetadata hasArtworkRequest=$hasArtworkRequest")
                append(" mp3Published=$mp3Published sidecarPublished=$sidecarPublished artworkPublished=$artworkPublished")
                if (error != null) append(" exception=${error.javaClass.simpleName} message=${safeLibraryExceptionMessage(error)}")
            }
            if (result) Log.i(MUSIC_LIBRARY_TAG, message) else Log.e(MUSIC_LIBRARY_TAG, message)
        }

        fun failure(code: String, detail: String, error: Throwable? = null): Boolean {
            event(code, false, error, detail)
            fail("$code Local Music Library save failed at $stage.")
            return false
        }

        val s = service ?: return@withContext failure("ML_E001", "service")
        val mp3Path = "$MUSIC_LIBRARY_PATH/$baseName.mp3"
        val sidecarPath = "$MUSIC_LIBRARY_PATH/${MusicLibrarySidecarCodec.sidecarFileName(baseName)}"
        val artworkPath = "$MUSIC_LIBRARY_PATH/${MusicLibrarySidecarCodec.artworkFileName(baseName)}"
        val token = System.nanoTime().toString()
        val mp3Temp = "$MUSIC_LIBRARY_PATH/${MusicLibrarySidecarCodec.stagingFileName("$baseName.mp3", token)}"
        val sidecarTemp = "$MUSIC_LIBRARY_PATH/${MusicLibrarySidecarCodec.stagingFileName(MusicLibrarySidecarCodec.sidecarFileName(baseName), token)}"
        val artworkTemp = "$MUSIC_LIBRARY_PATH/${MusicLibrarySidecarCodec.stagingFileName(MusicLibrarySidecarCodec.artworkFileName(baseName), token)}"
        try {
            updateProgress(InstallState.PREPARING, "Saving to Library...")
            stage = "mkdir"
            if (!s.createDirectory(MUSIC_LIBRARY_PATH)) return@withContext failure("ML_E002", "directory")

            stage = "source_open"
            val pfd = try {
                context.contentResolver.openFileDescriptor(uri, "r")
            } catch (error: Exception) {
                return@withContext failure("ML_E003", "mp3", error)
            } ?: return@withContext failure("ML_E003", "mp3")

            stage = "mp3_stage"
            if (!s.importFile(pfd, mp3Temp)) return@withContext failure("ML_E004", "mp3")

            if (metadata != null) {
                stage = "sidecar_serialize"
                val bytes = try {
                    MusicLibrarySidecarCodec.serialize(
                        MusicLibrarySidecar(metadata.trackName, metadata.artistName),
                    )
                } catch (error: Exception) {
                    return@withContext failure("ML_E005", "json", error)
                }
                stage = "sidecar_write"
                if (!s.writeFile(sidecarTemp, bytes)) return@withContext failure("ML_E005", "json")
            }

            if (hasArtworkRequest) {
                stage = "artwork_acquire"
                artworkCache = try {
                    acquireLibraryArtwork(artworkUri, artworkUrl)
                } catch (error: Exception) {
                    return@withContext failure("ML_E006", "jpg", error)
                }
                if (artworkCache == null) return@withContext failure("ML_E006", "jpg")
                stage = "artwork_stage"
                if (!s.copyFile(artworkCache.absolutePath, artworkTemp)) return@withContext failure("ML_E006", "jpg")
            }

            stage = "mp3_publish"
            if (!s.move(mp3Temp, mp3Path)) return@withContext failure("ML_E007", "mp3")
            mp3Published = true
            if (metadata != null) {
                stage = "sidecar_publish"
                if (!s.move(sidecarTemp, sidecarPath)) {
                    s.delete(mp3Path)
                    mp3Published = false
                    return@withContext failure("ML_E008", "json")
                }
                sidecarPublished = true
            }
            if (artworkCache != null) {
                stage = "artwork_publish"
                if (!s.move(artworkTemp, artworkPath)) {
                    s.delete(mp3Path)
                    s.delete(sidecarPath)
                    mp3Published = false
                    sidecarPublished = false
                    return@withContext failure("ML_E009", "jpg")
                }
                artworkPublished = true
            } else {
                stage = "stale_artwork_cleanup"
                if (!s.delete(artworkPath)) event("ML_E010", false, detail = "jpg")
            }
            stage = "complete"
            event("ML_OK", true, detail = "mp3")
            updateProgress(InstallState.SUCCESS, "SAVED TO LIBRARY", "$baseName.mp3")
            true
        } catch (error: Exception) {
            failure("ML_E011", "unknown", error)
        } finally {
            stage = "cleanup"
            listOf(mp3Temp to "mp3", sidecarTemp to "json", artworkTemp to "jpg").forEach { (path, extension) ->
                try {
                    if (!s.delete(path)) event("ML_E010", false, detail = extension)
                } catch (error: Exception) {
                    event("ML_E010", false, error, extension)
                }
            }
            artworkCache?.let { cache ->
                if (cache.exists() && !cache.delete()) event("ML_E010", false, detail = "jpg_cache")
            }
        }
    }

    private fun libraryNameKey(baseName: String): String {
        val digest = MessageDigest.getInstance("SHA-256").digest(baseName.toByteArray(Charsets.UTF_8))
        return digest.take(6).joinToString("") { "%02x".format(it) }
    }

    private fun safeLibraryExceptionMessage(error: Throwable): String = error.message
        ?.takeIf { message ->
            message.length <= 120 &&
                !message.contains('/') &&
                !message.contains('\\') &&
                !message.contains("content:", ignoreCase = true) &&
                message.all { character -> character.code in 32..126 }
        }
        ?: "REDACTED_OR_EMPTY"

    private fun acquireLibraryArtwork(artworkUri: Uri?, artworkUrl: String?): File? {
        if (artworkUri == null && artworkUrl.isNullOrBlank()) return null
        val output = File.createTempFile("library_artwork_", ".jpg", context.cacheDir)
        return try {
            val localError = artworkUri?.let { uri ->
                try {
                    openLibraryArtworkUri(uri).use { source ->
                        output.outputStream().use(source::copyTo)
                    }
                    null
                } catch (error: Exception) {
                    error
                }
            }

            if (artworkUri == null || localError != null) {
                val remoteUrl = artworkUrl?.takeIf { it.isNotBlank() }
                    ?: throw localError ?: IOException("Artwork source could not be opened")
                try {
                    openRemoteLibraryArtwork(remoteUrl).use { source ->
                        output.outputStream().use(source::copyTo)
                    }
                } catch (remoteError: Exception) {
                    localError?.let(remoteError::addSuppressed)
                    throw remoteError
                }
            }

            output.takeIf { it.length() > 0L } ?: error("Artwork was empty")
        } catch (error: Exception) {
            output.delete()
            throw error
        }
    }

    private fun openLibraryArtworkUri(uri: Uri) = when (
        LibraryArtworkSourcePolicy.candidates(URI(uri.toString()), null).firstOrNull()
    ) {
        LibraryArtworkSource.CONTENT -> context.contentResolver.openInputStream(uri)
            ?: throw IOException("Selected artwork could not be opened")
        LibraryArtworkSource.FILE -> {
            val path = uri.path ?: throw IOException("Selected artwork path was invalid")
            val file = try {
                File(path).canonicalFile
            } catch (_: IOException) {
                throw IOException("Selected artwork path was invalid")
            }
            require(file.isAbsolute && file.exists() && file.isFile && file.canRead()) {
                "Selected artwork file was not readable"
            }
            file.inputStream()
        }
        else -> throw IOException("Unsupported artwork URI scheme")
    }

    private fun openRemoteLibraryArtwork(artworkUrl: String) = URL(artworkUrl).let { url ->
        require(
            LibraryArtworkSourcePolicy.candidates(null, artworkUrl) ==
                listOf(LibraryArtworkSource.REMOTE_HTTPS)
        ) { "Artwork URL must use HTTPS" }
        url.openConnection().apply {
            connectTimeout = 10_000
            readTimeout = 20_000
        }.getInputStream()
    }

    /**
     * Deletes a track and all local sidecars from the music library.
     */
    suspend fun deleteLibraryMusic(fileName: String): Boolean = withContext(Dispatchers.IO) {
        val s = service ?: return@withContext false
        val safeBase = MusicLibrarySidecarCodec.sanitizeBaseName(fileName)
        if ("$safeBase.mp3" != fileName) return@withContext false
        try {
            val deleted = s.delete("$MUSIC_LIBRARY_PATH/$fileName")
            s.delete("$MUSIC_LIBRARY_PATH/${MusicLibrarySidecarCodec.sidecarFileName(safeBase)}")
            s.delete("$MUSIC_LIBRARY_PATH/${MusicLibrarySidecarCodec.artworkFileName(safeBase)}")
            s.delete("$MUSIC_LIBRARY_PATH/$safeBase.jpg")
            deleted
        } catch (e: Exception) {
            Log.e(TAG, "Delete from library failed", e)
            false
        }
    }

    /**
     * Applies a track from the library to a game soundtrack slot.
     */
    suspend fun applyFromLibrary(libraryFileName: String, targetTrackFileName: String): Boolean = withContext(Dispatchers.IO) {
        val s = service ?: return@withContext false
        try {
            val rr3Path = getRr3Path()
            val source = "$MUSIC_LIBRARY_PATH/$libraryFileName"
            
            // Support both direct filenames (legacy) and relative paths (sfx/file.mp3)
            val relativePath = if (targetTrackFileName.contains("/")) targetTrackFileName else "music/$targetTrackFileName"
            val dest = "$rr3Path/files/.depot/audio/$relativePath"

            if (!s.exists(source)) {
                fail("Library file not found.")
                return@withContext false
            }

            // 1. Ensure permanent backup exists before we overwrite
            ensureBackup(relativePath)

            updateProgress(InstallState.INSTALLING, "Applying: $libraryFileName -> ${targetTrackFileName.substringAfterLast("/")}")
            if (s.copyFile(source, dest)) {
                updateProgress(InstallState.SUCCESS, "AUDIO APPLIED FROM LIBRARY")
                return@withContext true
            }
            false
        } catch (e: Exception) {
            fail("Apply from library failed: ${e.message}")
            false
        }
    }

}
