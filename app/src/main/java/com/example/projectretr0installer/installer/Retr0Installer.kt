package com.example.projectretr0installer.installer

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.documentfile.provider.DocumentFile
import com.example.projectretr0installer.IRetr0UserService
import com.example.projectretr0installer.data.skins.SaveApiClient
import com.example.projectretr0installer.data.skins.SkinApiException
import com.example.projectretr0installer.installer.persistence.DownloadDatabase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL
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
        private const val MANIFEST_URL = "https://pub-f87b4daf1b344ca39c9ba6de6812a777.r2.dev"
        
        private const val RR3_ROW = "/storage/emulated/0/Android/data/com.ea.games.r3_row"
        private const val RR3_NA = "/storage/emulated/0/Android/data/com.ea.games.r3_na"
        
        // PERSISTENT HUB: Stored in public Documents to survive uninstalls/updates
        private const val SAVE_HUB_PATH = "/storage/emulated/0/Documents/ProjectRETR0/Backups"
        private const val MUSIC_HUB_PATH = "/storage/emulated/0/Documents/ProjectRETR0/MusicBackups"
        private const val MUSIC_LIBRARY_PATH = "/storage/emulated/0/Documents/ProjectRETR0/MusicLibrary"
        
        // COMMUNITY API URL
        private const val COMMUNITY_API_URL = "https://save-files.armaandeep08-ap.workers.dev"
        
        // Workers.dev proxy for globally cached release downloads.
        private const val BOOSTER_URL = "https://save-files.armaandeep08-ap.workers.dev"

        private var isDownloaderInit = false
        
        // Static cache to ensure pre-fetched manifest is available to all instances
        @Volatile
        private var globalCachedManifest: InstallManifest? = null

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
                
                // Attempt to update yt-dlp to the latest version
                try {
                    Log.i(TAG, "Updating yt-dlp...")
                    com.yausername.youtubedl_android.YoutubeDL.getInstance().updateYoutubeDL(context)
                    Log.i(TAG, "yt-dlp updated successfully")
                } catch (updateEx: Exception) {
                    Log.w(TAG, "Failed to update yt-dlp, using bundled version", updateEx)
                }
                
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
    private val sonicInstaller by lazy { SonicInstaller(context, service!!) }
    private val downloadDb by lazy { DownloadDatabase.getDatabase(context) }
    
    private val _progress = MutableStateFlow(InstallProgress())
    val progress: StateFlow<InstallProgress> = _progress.asStateFlow()

    /**
     * Pre-fetches the manifest in the background to ensure 'Checking for updates' is instant.
     */
    suspend fun preFetchManifest() {
        try {
            globalCachedManifest = manifestManager.fetchManifest()
            Log.i(TAG, "Manifest pre-fetched successfully (Version: ${globalCachedManifest?.version})")
        } catch (e: Exception) {
            Log.e(TAG, "Manifest pre-fetch failed", e)
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
        service?.move(profile.path.absolutePath, "${profile.path.parent}/$newName.dat") ?: false
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
            val url = URL("$COMMUNITY_API_URL/list?sort=$sort")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 10000
                
            if (connection.responseCode == 200) {
                val responseText = connection.inputStream.bufferedReader().readText()
                Log.i(TAG, "Community API Response: $responseText")
                
                // Safety check: Is this my JSON or the default 'Hello World'?
                if (!responseText.trim().startsWith("[")) {
                    Log.e(TAG, "Invalid server response. Worker is likely not deployed correctly.")
                    return@withContext emptyList()
                }

                val array = org.json.JSONArray(responseText)
                val list = mutableListOf<CommunitySave>()
                for (i in 0 until array.length()) {
                    val obj = array.getJSONObject(i)
                    list.add(
                        CommunitySave(
                            id = obj.getString("id"),
                            customName = obj.getString("custom_name"),
                            uploaderName = obj.getString("uploader_name"),
                            fileSize = obj.getLong("file_size"),
                            createdAt = obj.getString("created_at"),
                            playerLevel = obj.optInt("player_level", 0),
                            playerGold = obj.optLong("player_gold", 0),
                            playerRs = obj.optLong("player_rs", 0),
                            playerMs = obj.optLong("player_ms", 0),
                            likes = obj.optInt("likes", 0),
                            downloads = obj.optInt("downloads", 0),
                        ),
                    )
                }
                list
            } else {
                Log.e(TAG, "Community API error: ${connection.responseCode}")
                emptyList()
            }
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
            val url = URL("$COMMUNITY_API_URL/music/list?sort=$sort")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 10000
                
            if (connection.responseCode == 200) {
                val responseText = connection.inputStream.bufferedReader().readText()
                Log.i(TAG, "Community Music Response: $responseText")
                
                val array = org.json.JSONArray(responseText)
                val list = mutableListOf<CommunityMusic>()
                for (i in 0 until array.length()) {
                    val obj = array.getJSONObject(i)
                    list.add(
                        CommunityMusic(
                            id = obj.getString("id"),
                            customName = obj.optString("custom_name", "Untitled"),
                            uploaderName = obj.optString("uploader_name", "Anonymous"),
                            fileSize = obj.optLong("file_size", 0L),
                            createdAt = obj.optString("created_at", ""),
                            targetTrack = obj.optString("target_track", "Garage_Music.mp3"),
                            likes = obj.optInt("likes", 0),
                            downloads = obj.optInt("downloads", 0),
                        ),
                    )
                }
                list
            } else {
                Log.e(TAG, "Music API error: ${connection.responseCode}")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to fetch community music", e)
            emptyList()
        }
    }

    /**
     * Sends a 'Like' signal to the community hub.
     */
    suspend fun likeItem(id: String): Boolean = withContext(Dispatchers.IO) {
        try {
            val encodedId = java.net.URLEncoder.encode(id, "UTF-8")
            val url = URL("$COMMUNITY_API_URL/like?id=$encodedId")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.connectTimeout = 10000
                return@withContext connection.responseCode == 200
        } catch (_: Exception) { false }
    }

    /**
     * Uses Cloudflare AI to analyze an error and provide a fix.
     */
    suspend fun troubleshootError(errorMessage: String): String? = withContext(Dispatchers.IO) {
        try {
            val url = URL("$COMMUNITY_API_URL/troubleshoot")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.doOutput = true
            connection.connectTimeout = 15000
            connection.readTimeout = 15000
                connection.setRequestProperty("Content-Type", "application/json; charset=utf-8")
            connection.setRequestProperty("Accept", "application/json")
            connection.setRequestProperty("User-Agent", "RETR0-Installer/1.0")

            val deviceModel = "${android.os.Build.MANUFACTURER} ${android.os.Build.MODEL} (Android ${android.os.Build.VERSION.RELEASE})"
            val jsonReq = org.json.JSONObject().apply {
                put("error", errorMessage)
                put("device", deviceModel)
                put("type", "troubleshoot")
            }

            connection.outputStream.use { it.write(jsonReq.toString().toByteArray(Charsets.UTF_8)) }
            
            val respCode = connection.responseCode
            Log.d(TAG, "Troubleshoot response code: $respCode")

            if (respCode == 200) {
                val resp = connection.inputStream.bufferedReader().use { it.readText() }
                Log.d(TAG, "Troubleshoot response: $resp")
                val jsonResp = org.json.JSONObject(resp)
                return@withContext jsonResp.optString("response", "No response from AI")
            } else {
                val err = connection.errorStream?.bufferedReader()?.use { it.readText() } ?: "No error body"
                Log.e(TAG, "Troubleshoot error ($respCode): $err")
            }
        } catch (e: Exception) {
            Log.e(TAG, "AI Troubleshoot failed", e)
        }
        null
    }

    /**
     * Sends a conversation history to the Cloudflare AI Modding Assistant.
     */
    suspend fun chatWithAi(history: List<ChatMessage>): String? = withContext(Dispatchers.IO) {
        try {
            val url = URL("$COMMUNITY_API_URL/troubleshoot")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.doOutput = true
            connection.connectTimeout = 20000
            connection.readTimeout = 20000
                connection.setRequestProperty("Content-Type", "application/json; charset=utf-8")
            connection.setRequestProperty("Accept", "application/json")
            connection.setRequestProperty("User-Agent", "RETR0-Installer/1.0")

            val deviceModel = "${android.os.Build.MANUFACTURER} ${android.os.Build.MODEL} (Android ${android.os.Build.VERSION.RELEASE})"
            
            // The Worker expects "error", "device", and "type"
            // It does not support conversation history yet, so we send the latest user message.
            val lastUserMsg = history.lastOrNull { it.isUser }?.content ?: ""

            val jsonReq = org.json.JSONObject().apply {
                put("error", lastUserMsg) 
                put("device", deviceModel)
                put("type", "general") 
            }

            Log.d(TAG, "Sending AI Chat request to: $url")
            connection.outputStream.use { it.write(jsonReq.toString().toByteArray(Charsets.UTF_8)) }
            
            val respCode = connection.responseCode
            Log.d(TAG, "AI Chat response code: $respCode")

            if (respCode == 200) {
                val resp = connection.inputStream.bufferedReader().use { it.readText() }
                Log.d(TAG, "AI Chat response: $resp")
                val jsonResp = org.json.JSONObject(resp)
                return@withContext jsonResp.optString("response", "AI is silent.")
            } else {
                val errBody = connection.errorStream?.bufferedReader()?.use { it.readText() } ?: "No error body"
                Log.e(TAG, "AI Chat error ($respCode): $errBody")
            }
        } catch (e: Exception) {
            Log.e(TAG, "AI Chat exception: ${e.message}", e)
        }
        null
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
            val url = URL("$BOOSTER_URL/download?id=$encodedId")
            val connection = url.openConnection() as HttpURLConnection
                
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
    ): Boolean = withContext(Dispatchers.IO) {
        // 1. Apply Locally (saving metadata)
        val applied = applyMusicMod(uri, trackFileName, metadata)
        if (!applied) return@withContext false
        
        // 2. Upload to Community
        uploadMusic(uri, uploaderName, customName, trackFileName)
    }

    /**
     * Uploads a music file to the community hub using a true streaming pipeline.
     */
    suspend fun uploadMusic(
        uri: Uri,
        uploaderName: String,
        customName: String,
        targetTrack: String,
    ): Boolean = withContext(Dispatchers.IO) {
        val s = service ?: run { fail("Not connected."); return@withContext false }
        try {
            updateProgress(InstallState.PREPARING, "Uploading to Hub...")

            val encodedName = java.net.URLEncoder.encode(customName, "UTF-8")
            val encodedUser = java.net.URLEncoder.encode(uploaderName, "UTF-8")
            val encodedTrack = java.net.URLEncoder.encode(targetTrack.trim(), "UTF-8")
            
            val url = URL("$COMMUNITY_API_URL/music/upload?name=$encodedName&user=$encodedUser&track=$encodedTrack")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.doOutput = true
            connection.connectTimeout = 60000 // 60 seconds
            connection.readTimeout = 60000    // 60 seconds
                connection.setRequestProperty("Content-Type", "application/octet-stream")
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Android)")
            connection.setRequestProperty("Accept", "*/*")
            connection.setRequestProperty("Accept-Encoding", "identity")
            connection.instanceFollowRedirects = true

            // Use Chunked Streaming for better compatibility with Cloudflare Workers
            connection.setChunkedStreamingMode(16384) // 16KB chunks
            
            // Stream from URI directly to Network Socket
            // Fix: If it's a file URI from the library, it might need privileged access
            val inputStream = try {
                context.contentResolver.openInputStream(uri)
            } catch (e: Exception) {
                if (uri.scheme == "file") {
                    // Fallback: Pull from privileged storage to local cache if standard read fails
                    val tempFile = File(context.cacheDir, "music_upload_tmp.mp3")
                    val pfd = android.os.ParcelFileDescriptor.open(tempFile, android.os.ParcelFileDescriptor.MODE_READ_WRITE or android.os.ParcelFileDescriptor.MODE_CREATE)
                    val pulled = pfd.use { s.exportFile(uri.path ?: "", it) }
                    if (pulled) tempFile.inputStream() else throw e
                } else throw e
            } ?: throw java.io.FileNotFoundException("Could not open stream for URI: $uri")
                
            inputStream.buffered().use { input ->
                connection.outputStream.use { output ->
                    val buffer = ByteArray(8192) // 8KB chunks
                    var bytesRead: Int
                    var totalWritten = 0L
                    while (input.read(buffer).also { bytesRead = it } != -1) {
                        output.write(buffer, 0, bytesRead)
                        totalWritten += bytesRead
                    }
                    output.flush()
                    Log.d("RETR0_UPLOAD", "Successfully wrote $totalWritten bytes to socket")
                }
            }

            if (connection.responseCode == 200) {
                updateProgress(InstallState.SUCCESS, "APPLIED & SHARED SUCCESSFULLY")
                true
            } else {
                val responseCode = connection.responseCode
                val errorMsg = try {
                    connection.errorStream?.bufferedReader()?.readText() ?: ""
                } catch (_: Exception) {
                    "Could not read error stream"
                }
                Log.e("RETR0_UPLOAD", "Server rejected upload: $responseCode - $errorMsg")
                val fixHint = if (responseCode == 500) "FIX: Check your Cloudflare Worker's R2 bucket binding." else "FIX: Try again later or check your internet."
                fail("Upload failed: HTTP $responseCode. $fixHint")
                false
            }
        } catch (e: Exception) {
            Log.e("RETR0_UPLOAD", "Fatal upload error", e)
            val msg = e.message ?: ""
            val fixHint = if (msg.contains("reset")) "FIX: Internet unstable or file too large for Worker limits." 
                         else if (msg.contains("EACCES") || msg.contains("Permission denied")) "FIX: Storage permission error. Try restarting the app."
                         else "FIX: Ensure you have internet and try again."
            fail("Upload error: ${e.message}. $fixHint")
            false
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
            val url = URL("$BOOSTER_URL/music/download?id=$encodedId")
            val connection = url.openConnection() as HttpURLConnection
                
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
     * Fetches metadata for a YouTube video.
     */
    suspend fun fetchYouTubeInfo(youtubeUrl: String): YouTubeInfo? = withContext(Dispatchers.IO) {
        try {
            if (!isDownloaderInit) {
                if (!initDownloader(context)) return@withContext null
            }
            val request = com.yausername.youtubedl_android.YoutubeDLRequest(youtubeUrl)
            val info = com.yausername.youtubedl_android.YoutubeDL.getInstance().getInfo(request)
            return@withContext YouTubeInfo(
                title = info.title ?: "YouTube Audio",
                thumbnailUrl = info.thumbnail,
                url = youtubeUrl,
            )
        } catch (e: Exception) {
            Log.e(TAG, "Failed to fetch YouTube info", e)
            null
        }
    }

    /**
     * Downloads audio from a YouTube URL locally using yt-dlp.
     * Returns the local File and info if successful, null otherwise.
     */
    suspend fun downloadYouTubeAudio(youtubeUrl: String): YouTubeDownloadResult? = withContext(Dispatchers.IO) {
        try {
            if (!isDownloaderInit) {
                if (!initDownloader(context)) return@withContext null
            }

            updateProgress(InstallState.PREPARING, "Processing YouTube Link locally...")
            
            val info = fetchYouTubeInfo(youtubeUrl) ?: return@withContext null

            val tempDir = File(context.externalCacheDir ?: context.cacheDir, "youtube_work")
            if (tempDir.exists()) tempDir.deleteRecursively()
            tempDir.mkdirs()

            val request = com.yausername.youtubedl_android.YoutubeDLRequest(youtubeUrl)
            request.addOption("--extract-audio")
            request.addOption("--audio-format", "mp3")
            request.addOption("--audio-quality", "0") // Best quality (320kbps via FFmpeg)
            request.addOption("-o", "${tempDir.absolutePath}/%(title)s.%(ext)s")
            
            // Add options for speed and reliability
            request.addOption("--no-playlist")
            request.addOption("--no-mtime")
            
            Log.i(TAG, "Starting local yt-dlp execution for: $youtubeUrl")
            
            com.yausername.youtubedl_android.YoutubeDL.getInstance().execute(request) { progress, eta, line ->
                updateProgress(
                    state = InstallState.DOWNLOADING,
                    step = "Local Conversion: $progress%",
                    fileProgress = progress / 100f,
                    overallProgress = progress / 100f,
                    eta = if (eta > 0) "${eta}s" else "Converting...",
                )
                Log.v(TAG, "yt-dlp: $line")
            }
            
            val downloadedFile = tempDir.listFiles()?.find { it.extension == "mp3" }
            if ((downloadedFile != null) && downloadedFile.exists()) {
                // Move to a stable location
                val finalFile = File(context.externalCacheDir ?: context.cacheDir, "youtube_download.mp3")
                if (finalFile.exists()) finalFile.delete()
                downloadedFile.renameTo(finalFile)
                
                tempDir.deleteRecursively()
                return@withContext YouTubeDownloadResult(finalFile, info)
            } else {
                fail("Local conversion failed. No MP3 file generated.")
                null
            }
        } catch (e: Exception) {
            Log.e(TAG, "Local YouTube conversion failed", e)
            fail("YouTube Error (Local): ${e.message}")
            null
        }
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
            val libraryFiles = s.listFiles(MUSIC_LIBRARY_PATH) ?: emptyArray()
            
            val mp3Files = libraryFiles.filter { it.endsWith(".mp3", ignoreCase = true) }

            // Fetch metadata in parallel
            coroutineScope {
                mp3Files.map { fileName ->
                    async {
                        val absolutePath = "$MUSIC_LIBRARY_PATH/$fileName"
                        val baseName = fileName.removeSuffix(".mp3")
                        val artworkPath = "$MUSIC_LIBRARY_PATH/$baseName.jpg"
                        
                        var artworkUrl: String? = if (s.exists(artworkPath)) "file://$artworkPath" else null
                        
                        // Always try to fetch professional metadata for the names
                        val meta = try {
                            MetadataFetcher.fetchMetadata(baseName)
                        } catch (_: Exception) { null }
                        
                        val trackName = meta?.get("trackName")
                        val artistName = meta?.get("artistName")
                        
                        // Use iTunes art as primary if found, otherwise fallback to local .jpg
                        if (meta?.get("artworkUrl") != null) {
                            artworkUrl = meta["artworkUrl"]
                        }

                        LibraryMusic(
                            fileName = fileName,
                            fileSize = s.getFileSize(absolutePath),
                            absolutePath = absolutePath,
                            trackName = trackName,
                            artistName = artistName,
                            artworkUrl = artworkUrl
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
     * Saves a music file to the permanent local library, including optional metadata/artwork.
     */
    suspend fun saveToLibrary(
        uri: Uri, 
        customName: String, 
        thumbUrl: String? = null,
        metadata: MusicMetadata? = null,
    ): Boolean = withContext(Dispatchers.IO) {
        val s = service ?: return@withContext false
        try {
            updateProgress(InstallState.PREPARING, "Saving to Library...")
            s.createDirectory(MUSIC_LIBRARY_PATH)
            
            val sanitizedName = customName.replace(Regex("[^a-zA-Z0-9._-]"), "_")
            val baseFileName = sanitizedName.removeSuffix(".mp3")
            val fileName = "$baseFileName.mp3"
            val destPath = "$MUSIC_LIBRARY_PATH/$fileName"

            val pfd = context.contentResolver.openFileDescriptor(uri, "r") ?: return@withContext false
            if (s.importFile(pfd, destPath)) {
                // Determine the best Artwork source
                val finalArtUrl = metadata?.artworkUrl ?: thumbUrl
                
                if (finalArtUrl != null) {
                    try {
                        val artFile = File(context.cacheDir, "art_buffer.jpg")
                        val artUrl = URL(finalArtUrl)
                        artUrl.openStream().use { input ->
                            artFile.outputStream().use { output -> input.copyTo(output) }
                        }
                        s.copyFile(artFile.absolutePath, "$MUSIC_LIBRARY_PATH/$baseFileName.jpg")
                        artFile.delete()
                    } catch (e: Exception) {
                        Log.w(TAG, "Failed to save artwork for $baseFileName", e)
                    }
                }

                updateProgress(InstallState.SUCCESS, "SAVED TO LIBRARY", fileName)
                return@withContext true
            }
            false
        } catch (e: Exception) {
            fail("Library Save Error: ${e.message}")
            false
        }
    }

    /**
     * Deletes a track from the local music library.
     */
    suspend fun deleteLibraryMusic(fileName: String): Boolean = withContext(Dispatchers.IO) {
        val s = service ?: return@withContext false
        try {
            val path = "$MUSIC_LIBRARY_PATH/$fileName"
            if (s.delete(path)) {
                Log.i(TAG, "Deleted from library: $fileName")
                true
            } else {
                false
            }
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

    /**
     * Checks for app updates and installs them.
     */
    suspend fun autoUpdate(isManual: Boolean = false): Boolean {
        val s = service ?: return false
        try {
            if (isManual) updateProgress(InstallState.FETCHING_MANIFEST, "Checking for app updates...")
            val manifest = manifestManager.fetchManifest() ?: return false
            
            val currentVersion = context.packageManager.getPackageInfo(context.packageName, 0).versionName
            
            // Simple version comparison: trigger update if manifest version differs from local
            if (manifest.version != currentVersion) {
                Log.i(TAG, "Update found! Current: $currentVersion, New: ${manifest.version}")
                
                val apkFile = manifest.files.find { it.type == "apk" } ?: run {
                    Log.e(TAG, "No APK file found in manifest for update")
                    return false
                }

                updateProgress(InstallState.DOWNLOADING, "New update found: V${manifest.version}", apkFile.name)
                
                val tempApk = File(context.externalCacheDir, "update.apk")
                if (tempApk.exists()) tempApk.delete()

                Log.i(TAG, "Downloading update APK from: ${apkFile.url}")
                
                if (downloader.download(apkFile.url.replace(MANIFEST_URL, BOOSTER_URL), tempApk, apkFile.size) { current, total, _ ->
                    updateProgress(
                        state = InstallState.DOWNLOADING, 
                        step = "Downloading Update V${manifest.version}...", 
                        currentFile = apkFile.name, 
                        fileProgress = current.toFloat() / total,
                        overallProgress = current.toFloat() / total,
                        eta = "Processing",
                    )
                }) {
                    updateProgress(InstallState.UPDATE, "Installing update and restarting...", apkFile.name)
                    Log.i(TAG, "Download complete. Passing to privileged service for installation...")
                    
                    if (s.installPackage(tempApk.absolutePath)) {
                        updateProgress(InstallState.SUCCESS, "UPDATE INSTALLED")
                        return true
                    } else {
                        fail("Installation failed. Check Shizuku permissions.")
                    }
                } else {
                    val error = downloader.lastError
                    fail("Update download failed: $error")
                }
            } else {
                if (isManual) updateProgress(InstallState.SUCCESS, "APP IS UP TO DATE")
                Log.i(TAG, "App is already at the latest version (V$currentVersion)")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Auto-update failed", e)
        }
        return false
    }
}
