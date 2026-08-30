package com.example.projectretr0installer.installer

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.documentfile.provider.DocumentFile
import com.example.projectretr0installer.IRetr0UserService
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.File
import java.net.HttpURLConnection
import java.net.URL

/**
 * Orchestrates the Project RETR0 installation and maintenance process.
 */
class Retr0Installer(
    private val context: Context,
    private val service: IRetr0UserService,
    private val androidId: String? = null,
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
        private val COMMUNITY_API_KEY = com.example.projectretr0installer.BuildConfig.COMMUNITY_API_KEY

        private var isDownloaderInit = false

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
    private val extractor = ArchiveExtractor()
    private val verifier = HashVerifier()
    private val manifestManager = ManifestManager(MANIFEST_URL)

    private val _progress = MutableStateFlow(InstallProgress())
    val progress: StateFlow<InstallProgress> = _progress.asStateFlow()
    
    private var activeRr3Path: String? = null

    private fun getRr3Path(): String {
        activeRr3Path?.let { return it }
        val foundPath = if (service.exists(RR3_ROW)) RR3_ROW else if (service.exists(RR3_NA)) RR3_NA else RR3_ROW
        activeRr3Path = foundPath
        return foundPath
    }

    suspend fun installMod(): Boolean = withContext(Dispatchers.IO) {
        try {
            val rr3Path = getRr3Path()
            updateProgress(InstallState.FETCHING_MANIFEST, "Connecting to Cloudflare...")
            val manifest = manifestManager.fetchManifest() ?: run {
                fail("Connection failed. Manifest not found.")
                return@withContext false
            }

            // 1. Storage Check
            val totalCompressed = manifest.files.sumOf { it.size }
            val totalUncompressed = manifest.files.sumOf { it.uncompressedSize }
            val requiredBytes = totalCompressed + totalUncompressed + (512 * 1024 * 1024L) // + 512MB Buffer

            var downloadedBytesAccumulated = 0L
            
            updateProgress(InstallState.PREPARING, "Checking storage...")
            val available = StorageUtils.getAvailableSpace(context.externalCacheDir ?: context.cacheDir)
            if (available in (1 until requiredBytes)) {
                val neededStr = StorageUtils.formatSize(requiredBytes)
                fail("Insufficient space. You need $neededStr free.")
                return@withContext false
            }

            if (!service.ping()) { fail("Shizuku service not connected."); return@withContext false }

            updateProgress(InstallState.PREPARING, "Analyzing existing game data...")
            val statsBefore = service.getDirectoryStats("$rr3Path/files")
            Log.i(TAG, "Cleanup - Before: ${statsBefore[1]} files")

            updateProgress(InstallState.PREPARING, "Wiping existing data...")
            try {
                service.delete("$rr3Path/files")
                service.delete("$rr3Path/cache")
            } catch (_: Exception) {}
            
            // CRITICAL: Re-create the base directories after wiping them
            service.createDirectory("$rr3Path/files")
            service.createDirectory("$rr3Path/cache")
            
            updateProgress(InstallState.PREPARING, "Cleaning temporary files...")
            val tempDir = File(context.externalCacheDir ?: context.cacheDir, "retr0_install")
            if (tempDir.exists()) tempDir.deleteRecursively()
            tempDir.mkdirs()

            Log.i(TAG, "Starting install sequence targeting: $rr3Path")
            updateProgress(InstallState.PREPARING, "Targeting: ${rr3Path.substringAfterLast("/")}")

            manifest.files.asSequence().filter { it.required }.forEachIndexed { index, file ->
                val fileNum = "Part ${index + 1} of ${manifest.files.size}"
                val downloadFile = File(tempDir, file.name)

                // IMMEDIATE FEEDBACK: Show connecting status
                updateProgress(
                    state = InstallState.DOWNLOADING,
                    step = "$fileNum: Connecting...",
                    currentFile = file.name,
                    overallProgress = downloadedBytesAccumulated.toFloat() / totalCompressed,
                    completedFiles = index,
                    totalFiles = manifest.files.size,
                    eta = "..."
                )

                if (!downloader.download(file.url, downloadFile, file.size) { currentBytes, totalBytes, speed ->
                    val overallDownloaded = downloadedBytesAccumulated + currentBytes
                    val overallProgress = overallDownloaded.toFloat() / totalCompressed
                    
                    val remainingBytes = totalCompressed - overallDownloaded
                    val etaSeconds = if (speed > 0) (remainingBytes / speed).toLong() else -1L
                    val etaStr = if (etaSeconds >= 0) {
                        val mins = etaSeconds / 60
                        val secs = etaSeconds % 60
                        String.format(java.util.Locale.ENGLISH, "%02d:%02d", mins, secs)
                    } else "Calculating..."

                    updateProgress(
                        state = InstallState.DOWNLOADING, 
                        step = "$fileNum: Downloading...", 
                        currentFile = file.name, 
                        fileProgress = currentBytes.toFloat() / totalBytes,
                        overallProgress = overallProgress,
                        completedFiles = index,
                        totalFiles = manifest.files.size,
                        eta = etaStr,
                    )
                }) {
                    val errorCode = downloader.lastError
                    val fixHint = if (errorCode.contains("404")) {
                        "FIX: The mod files may have been removed from the server."
                    } else if (errorCode.contains("timeout")) {
                        "FIX: Server timed out. The mod part is large; we have increased wait time to 60s. Try again."
                    } else if (errorCode.contains("reset")) {
                        "FIX: Connection reset. Your ISP or the server dropped the link."
                    } else {
                        "FIX: Ensure you have internet and the server is online."
                    }
                    fail("Download failed: ${file.name} (Error: $errorCode). $fixHint")
                    return@withContext false
                }

                // Update accumulated counter after successful download
                downloadedBytesAccumulated += file.size
                
                if (file.sha256 != "SKIP_VERIFICATION") {
                    updateProgress(
                        state = InstallState.VERIFYING, 
                        step = "$fileNum: Verifying...", 
                        currentFile = file.name, 
                        fileProgress = 1.0f, 
                        overallProgress = downloadedBytesAccumulated.toFloat() / totalCompressed,
                        completedFiles = index, 
                        totalFiles = manifest.files.size, 
                        eta = "Checking...",
                    )
                    if (!verifier.verifySha256(downloadFile, file.sha256)) {
                        fail("Corrupted file detected: ${file.name}")
                        return@withContext false
                    }
                }

                if (file.type == "zip") {
                    try {
                        val count = extractor.extractAndInstall(
                            zipFile = downloadFile,
                            privilegedDest = rr3Path,
                            service = service,
                        ) { entryName ->
                            updateProgress(
                                state = InstallState.EXTRACTING, 
                                step = "$fileNum: Extracting...", 
                                currentFile = entryName, 
                                fileProgress = 0.5f, 
                                overallProgress = downloadedBytesAccumulated.toFloat() / totalCompressed,
                                completedFiles = index,
                                totalFiles = manifest.files.size,
                            )
                        }
                        if (count == 0) throw Exception("Zero files extracted")
                    } catch (e: android.os.RemoteException) {
                        Log.e(TAG, "Shizuku died during extraction", e)
                        fail("Extraction failed. Shizuku service disconnected. FIX: Ensure Shizuku is running and the RETR0 service is connected.")
                        return@withContext false
                    } catch (e: Exception) {
                        Log.e(TAG, "Extraction failure: ${e.message}")
                        val fixHint = if (e.message?.contains("No space") == true) "FIX: You have run out of storage space on your device." else "FIX: Ensure Shizuku is running and you have free space."
                        fail("Extraction failed for ${file.name}. $fixHint (Error: ${e.message})")
                        return@withContext false 
                    }
                } else {
                    val relativePath = file.destination.substringAfter("com.ea.games.r3_row/")
                        .substringAfter("com.ea.games.r3_na/")
                    val finalDest = "$rr3Path/$relativePath"
                    
                    if (!service.copyFile(downloadFile.absolutePath, finalDest)) {
                        fail("Failed to copy ${file.name}"); return@withContext false
                    }
                }

                // DELETE ZIP IMMEDIATELY TO FREE UP SPACE
                downloadFile.delete()
                updateProgress(
                    state = InstallState.INSTALLING, 
                    step = "$fileNum: Completed", 
                    currentFile = file.name, 
                    fileProgress = 1.0f, 
                    overallProgress = (downloadedBytesAccumulated.toFloat() / totalCompressed),
                    completedFiles = index + 1, 
                    totalFiles = manifest.files.size,
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
        return try {
            val rr3Path = getRr3Path()
            if (!service.ping()) { fail("Not connected."); return false }
            val testPath = "$rr3Path/files/retr0_test.tmp"
            if (service.writeFile(testPath, "TEST".toByteArray())) {
                service.delete(testPath)
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
        return try {
            val rr3Path = getRr3Path()
            if (!service.ping()) { fail("Not connected."); return false }
            val depotPath = "$rr3Path/files/.depot"
            val resPath = "$rr3Path/files/apk/res"
            updateProgress(InstallState.PREPARING, "Cleaning assets...")
            service.delete("$depotPath/eds")
            service.delete("$depotPath/feats")
            updateProgress(InstallState.INSTALLING, "Restoring assets...")
            if (service.exists("$resPath/eds")) service.copyDirectory("$resPath/eds", "$depotPath/eds")
            if (service.exists("$resPath/feats")) service.copyDirectory("$resPath/feats", "$depotPath/feats")
            updateProgress(InstallState.SUCCESS, "ASSET REPAIR COMPLETE")
            true
        } catch (e: Exception) {
            fail("Repair error: ${e.message}"); false
        }
    }

    suspend fun importCharacterData(uri: Uri): Boolean = withContext(Dispatchers.IO) {
        try {
            val rr3Path = getRr3Path()
            if (!service.ping()) { fail("Not connected."); return@withContext false }
            val destPath = "$rr3Path/files/doc/character.2.dat"
            val pfd = context.contentResolver.openFileDescriptor(uri, "r") ?: run {
                fail("Could not open file."); return@withContext false
            }
            updateProgress(InstallState.INSTALLING, "Importing character.2.dat...")
            service.delete(destPath)
            if (service.importFile(pfd, destPath)) {
                updateProgress(InstallState.SUCCESS, "CHARACTER DATA IMPORTED")
                return@withContext true
            }
            fail("Failed to write to game folder."); false
        } catch (e: Exception) {
            fail("Import error: ${e.message}"); false
        }
    }

    suspend fun importSaveDirectory(treeUri: Uri): Boolean = withContext(Dispatchers.IO) {
        try {
            val rr3Path = getRr3Path()
            if (!service.ping()) { fail("Not connected."); return@withContext false }
            val destPath = "$rr3Path/files/doc"
            val tempDir = File(context.externalCacheDir ?: context.cacheDir, "save_import").apply { deleteRecursively(); mkdirs() }
            val directory = DocumentFile.fromTreeUri(context, treeUri) ?: run {
                fail("Invalid folder."); return@withContext false
            }
            
            updateProgress(InstallState.PREPARING, "Collecting files...")
            pullRecursiveFromDocumentFile(directory, tempDir)

            updateProgress(InstallState.INSTALLING, "Replacing .doc folder...")
            service.delete(destPath)
            service.createDirectory(destPath)
            copyRecursiveToPrivileged(tempDir, destPath)
            tempDir.deleteRecursively()
            updateProgress(InstallState.SUCCESS, "SAVE FOLDER IMPORTED")
            return@withContext true
        } catch (e: Exception) {
            fail("Import error: ${e.message}"); false
        }
    }

    suspend fun getSaveProfiles(): List<SaveProfile> = withContext(Dispatchers.IO) {
        try {
            if (!service.ping()) return@withContext emptyList()
            service.createDirectory(SAVE_HUB_PATH)
            val files = service.listFiles(SAVE_HUB_PATH) ?: return@withContext emptyList()
            
            Log.i(TAG, "Listing Hub: Found ${files.size} entries in $SAVE_HUB_PATH")
            
            files.asSequence().filter { it.endsWith(".dat") }.map { fileName ->
                val profileName = fileName.removeSuffix(".dat")
                val path = "$SAVE_HUB_PATH/$fileName"
                val file = File(path)
                
                SaveProfile(
                    name = profileName,
                    timestamp = file.lastModified(),
                    fileCount = 1,
                    totalSize = service.getFileSize(path),
                    path = file,
                )
            }.sortedByDescending { it.timestamp }.toList()
        } catch (_: Exception) { emptyList() }
    }

    /**
     * Backs up only the character.2.dat file to the persistent hub.
     */
    suspend fun createSnapshot(profileName: String): Boolean = withContext(Dispatchers.IO) {
        Log.i(TAG, "Creating snapshot (character only): $profileName")
        try {
            val rr3Path = getRr3Path()
            if (!service.ping()) { fail("Shizuku disconnected."); return@withContext false }
            
            val sourceFile = "$rr3Path/files/doc/character.2.dat"
            val destFile = "$SAVE_HUB_PATH/$profileName.dat"
            
            updateProgress(InstallState.PREPARING, "Checking game save...")
            if (!service.exists(sourceFile)) {
                fail("Source character.2.dat not found in game folder.")
                return@withContext false
            }

            service.createDirectory(SAVE_HUB_PATH)

            updateProgress(InstallState.INSTALLING, "Copying to Hub...")
            
            // DIRECT COPY: The service can see both paths!
            if (service.copyFile(sourceFile, destFile)) {
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
        try {
            val rr3Path = getRr3Path()
            if (!service.ping()) { fail("Not connected."); return@withContext false }
            val destFile = "$rr3Path/files/doc/character.2.dat"
            updateProgress(InstallState.INSTALLING, "Switching save file...")
            if (service.copyFile(profile.path.absolutePath, destFile)) {
                updateProgress(InstallState.SUCCESS, "PROFILE APPLIED")
                return@withContext true
            }
            fail("Restore failed."); false
        } catch (e: Exception) {
            fail("Restore error: ${e.message}"); false
        }
    }

    suspend fun deleteProfile(profile: SaveProfile) = withContext(Dispatchers.IO) {
        service.delete(profile.path.absolutePath)
    }

    suspend fun renameProfile(profile: SaveProfile, newName: String): Boolean = withContext(Dispatchers.IO) {
        service.move(profile.path.absolutePath, "${profile.path.parent}/$newName.dat")
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
            connection.setRequestProperty("X-RETR0-KEY", COMMUNITY_API_KEY)
            
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
                            downloads = obj.optInt("downloads", 0)
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
            connection.setRequestProperty("X-RETR0-KEY", COMMUNITY_API_KEY)
            
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
                            downloads = obj.optInt("downloads", 0)
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
            connection.setRequestProperty("X-RETR0-KEY", COMMUNITY_API_KEY)
            return@withContext connection.responseCode == 200
        } catch (_: Exception) { false }
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
        try {
            updateProgress(InstallState.PREPARING, "Scanning and Uploading...")
            
            // 1. Fetch file data via privileged service
            val tempFile = File(context.cacheDir, "upload_buffer.dat") // Use internal cache for better ownership
            if (tempFile.exists()) tempFile.delete()
            
            // USE PFD EXPORT: ensures the app process OWNS the temp file
            val pfd = android.os.ParcelFileDescriptor.open(tempFile, android.os.ParcelFileDescriptor.MODE_READ_WRITE or android.os.ParcelFileDescriptor.MODE_CREATE)
            val exportSuccess = pfd.use { service.exportFile(profile.path.absolutePath, it) }
            
            if (!exportSuccess) {
                fail("Could not read backup file.")
                return@withContext false
            }

            // 2. Determine Stats (Manual entries take priority)
            val stats = SaveScanner.scan(tempFile, androidId)
            val finalLevel = if (manualLevel >= 0) manualLevel else stats.level
            val finalGold = if (manualGold >= 0) manualGold else stats.gold
            val finalRS = if (manualRS >= 0) manualRS else stats.rs
            val finalMS = if (manualMS >= 0) manualMS else 0L // M$ is now strictly manual

            // 3. Upload with Stat Parameters (Preflight-Proof)
            val encodedName = java.net.URLEncoder.encode(profile.name, "UTF-8")
            val encodedUser = java.net.URLEncoder.encode(uploaderName, "UTF-8")
            
            val url = URL("$COMMUNITY_API_URL/upload?name=$encodedName&user=$encodedUser&level=$finalLevel&gold=$finalGold&rs=$finalRS&ms=$finalMS")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.doOutput = true
            connection.connectTimeout = 60000
            connection.readTimeout = 60000
            connection.setRequestProperty("X-RETR0-KEY", COMMUNITY_API_KEY)
            connection.setRequestProperty("Content-Type", "application/octet-stream")
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Android)")
            connection.setRequestProperty("Accept", "*/*")
            connection.setRequestProperty("Accept-Encoding", "identity")
            connection.instanceFollowRedirects = true

            // Use Chunked Streaming for better compatibility with Cloudflare Workers
            connection.setChunkedStreamingMode(16384) // 16KB chunks

            // Stream from file to network
            tempFile.inputStream().buffered().use { input ->
                connection.outputStream.use { output ->
                    val buffer = ByteArray(8192)
                    var bytesRead: Int
                    var totalWritten = 0L
                    while (input.read(buffer).also { bytesRead = it } != -1) {
                        output.write(buffer, 0, bytesRead)
                        totalWritten += bytesRead
                    }
                    output.flush()
                    Log.d("RETR0_UPLOAD", "Successfully wrote $totalWritten bytes (save) to socket")
                }
            }

            tempFile.delete()

            if (connection.responseCode == 200) {
                updateProgress(InstallState.SUCCESS, "SHARED SUCCESSFULLY")
                true
            } else {
                val errorMsg = try {
                    connection.errorStream?.bufferedReader()?.readText() ?: ""
                } catch (_: Exception) {
                    "Could not read error stream"
                }
                Log.e("RETR0_UPLOAD", "Server rejected save upload: ${connection.responseCode} - $errorMsg")
                val fixHint = if (connection.responseCode == 500) "FIX: Check your Cloudflare Worker's R2 bucket binding." else "FIX: Try again later or check your internet."
                fail("Upload failed: HTTP ${connection.responseCode}. $fixHint")
                false
            }
        } catch (e: Exception) {
            val msg = e.message ?: ""
            val fixHint = if (msg.contains("reset")) "FIX: Internet unstable or file too large for Worker limits." 
                         else if (msg.contains("EACCES") || msg.contains("Permission denied")) "FIX: Storage permission error. Try restarting the app."
                         else "FIX: Ensure you have internet and try again."
            fail("Upload error: ${e.message}. $fixHint")
            false
        }
    }

    /**
     * Downloads a community save and adds it to the local hub.
     */
    suspend fun downloadCommunitySave(save: CommunitySave): Boolean = withContext(Dispatchers.IO) {
        try {
            updateProgress(InstallState.DOWNLOADING, "Downloading ${save.customName}...")
            val destFile = File(SAVE_HUB_PATH, "${save.customName}.dat")
            service.createDirectory(SAVE_HUB_PATH)

            val encodedId = java.net.URLEncoder.encode(save.id, "UTF-8")
            val url = URL("$COMMUNITY_API_URL/download?id=$encodedId")
            val connection = url.openConnection() as HttpURLConnection
            connection.setRequestProperty("X-RETR0-KEY", COMMUNITY_API_KEY)
            
            if (connection.responseCode == 200) {
                val tempFile = File(context.externalCacheDir, "community_download.tmp")
                connection.inputStream.use { input ->
                    tempFile.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }
                
                if (service.copyFile(tempFile.absolutePath, destFile.absolutePath)) {
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
        metadata: MusicMetadata? = null
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
            connection.setRequestProperty("X-RETR0-KEY", COMMUNITY_API_KEY)
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
                    val pulled = pfd.use { service.exportFile(uri.path ?: "", it) }
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
        try {
            val rr3Path = getRr3Path()
            updateProgress(InstallState.DOWNLOADING, "Downloading ${music.customName}...")
            
            // Ensure necessary directories exist
            if (toLibrary) service.createDirectory(MUSIC_LIBRARY_PATH)
            else backupOriginalMusic()

            val encodedId = java.net.URLEncoder.encode(music.id, "UTF-8")
            val url = URL("$COMMUNITY_API_URL/music/download?id=$encodedId")
            val connection = url.openConnection() as HttpURLConnection
            connection.setRequestProperty("X-RETR0-KEY", COMMUNITY_API_KEY)
            
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

                if (service.copyFile(tempFile.absolutePath, destPath)) {
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
                url = youtubeUrl
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
                    eta = if (eta > 0) "${eta}s" else "Converting..."
                )
                Log.v(TAG, "yt-dlp: $line")
            }
            
            val downloadedFile = tempDir.listFiles()?.find { it.extension == "mp3" }
            if (downloadedFile != null && downloadedFile.exists()) {
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
        var count = 0
        localDir.listFiles()?.forEach { file ->
            val destPath = "$privilegedDest/${file.name}"
            if (file.isDirectory) {
                service.createDirectory(destPath)
                count += copyRecursiveToPrivileged(file, destPath)
            } else {
                if (service.copyFile(file.absolutePath, destPath)) count++
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
                "Ryan_Kurt_Further_Corrupt_Remix.mp3"
            )

            service.createDirectory(MUSIC_HUB_PATH)
            var backedUpCount = 0

            tracks.forEach { fileName ->
                val source = "$musicDir/$fileName"
                val dest = "$MUSIC_HUB_PATH/$fileName"
                
                // Only backup if the backup doesn't already exist (to avoid backing up a modded file)
                if (service.exists(source) && !service.exists(dest)) {
                    Log.i(TAG, "Backing up original music: $fileName")
                    if (service.copyFile(source, dest)) backedUpCount++
                }
            }
            return@withContext true
        } catch (e: Exception) {
            Log.e(TAG, "Music backup failed", e)
            false
        }
    }

    /**
     * Overwrites all standard quality tiers with the selected preset.
     * Source: apk/res (Factory Original)
     */
    suspend fun applyGraphicsPreset(presetName: String): Boolean = withContext(Dispatchers.IO) {
        try {
            updateProgress(InstallState.PREPARING, "Preparing graphics swap...")
            
            val rr3Path = getRr3Path()
            val resEds = "$rr3Path/files/apk/res/eds"
            val resFeats = "$rr3Path/files/apk/res/feats"
            
            val depotEds = "$rr3Path/files/.depot/eds"
            val depotFeats = "$rr3Path/files/.depot/feats"

            val sourcePlist = "$resEds/$presetName.plist"
            val sourceFeats = "$resFeats/$presetName.feats"

            val hasPlist = service.exists(sourcePlist)
            val hasFeats = service.exists(sourceFeats)

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
                    if (service.copyFile(sourcePlist, destPlist)) {
                        successCount++
                    }
                }
                
                // Apply Feats (if exists)
                if (hasFeats) {
                    val destFeats = "$depotFeats/$tier.feats"
                    if (service.copyFile(sourceFeats, destFeats)) {
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
        try {
            val rr3Path = getRr3Path()
            val resPath = "$rr3Path/files/apk/res"
            val depotPath = "$rr3Path/files/.depot"

            if (!service.exists(resPath)) {
                fail("Original graphics source (apk/res) not found.")
                return@withContext false
            }

            updateProgress(InstallState.INSTALLING, "Restoring original graphics...")
            
            // Restore EDS
            if (service.exists("$resPath/eds")) {
                service.delete("$depotPath/eds")
                service.createDirectory("$depotPath/eds")
                service.copyDirectory("$resPath/eds", "$depotPath/eds")
            }

            // Restore FEATS
            if (service.exists("$resPath/feats")) {
                service.delete("$depotPath/feats")
                service.createDirectory("$depotPath/feats")
                service.copyDirectory("$resPath/feats", "$depotPath/feats")
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
    suspend fun applyMusicMod(
        uri: Uri, 
        trackFileName: String, 
        metadata: MusicMetadata? = null
    ): Boolean = withContext(Dispatchers.IO) {
        try {
            val rr3Path = getRr3Path()
            val destPath = "$rr3Path/files/.depot/audio/music/$trackFileName"
            
            // 1. Ensure backup exists before we overwrite
            backupOriginalMusic()

            // 2. Import the new music
            val pfd = context.contentResolver.openFileDescriptor(uri, "r") ?: return@withContext false
            updateProgress(InstallState.INSTALLING, "Applying Music Mod: $trackFileName")
            
            if (service.importFile(pfd, destPath)) {
                // Save Artwork for the core slot too if provided
                if (metadata?.artworkUrl != null) {
                    try {
                        val baseName = trackFileName.removeSuffix(".mp3")
                        val artFile = File(context.cacheDir, "art_buffer_core.jpg")
                        URL(metadata.artworkUrl).openStream().use { input ->
                            artFile.outputStream().use { output -> input.copyTo(output) }
                        }
                        service.copyFile(artFile.absolutePath, "$MUSIC_LIBRARY_PATH/$baseName.jpg")
                    } catch (_: Exception) {}
                }

                updateProgress(InstallState.SUCCESS, "MUSIC MOD APPLIED")
                return@withContext true
            }
            false
        } catch (e: Exception) {
            fail("Music Mod Error: ${e.message}")
            false
        }
    }

    /**
     * Restores a specific original music track from the Hub.
     */
    suspend fun restoreMusic(trackFileName: String): Boolean = withContext(Dispatchers.IO) {
        try {
            val rr3Path = getRr3Path()
            val source = "$MUSIC_HUB_PATH/$trackFileName"
            val dest = "$rr3Path/files/.depot/audio/music/$trackFileName"

            if (!service.exists(source)) {
                fail("Original backup for $trackFileName not found.")
                return@withContext false
            }

            updateProgress(InstallState.INSTALLING, "Restoring original: $trackFileName")
            if (service.copyFile(source, dest)) {
                updateProgress(InstallState.SUCCESS, "MUSIC RESTORED")
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
        try {
            service.createDirectory(MUSIC_LIBRARY_PATH)
            val libraryFiles = service.listFiles(MUSIC_LIBRARY_PATH) ?: emptyArray()
            
            val mp3Files = libraryFiles.filter { it.endsWith(".mp3", ignoreCase = true) }

            // Fetch metadata in parallel
            coroutineScope {
                mp3Files.map { fileName ->
                    async {
                        val absolutePath = "$MUSIC_LIBRARY_PATH/$fileName"
                        val baseName = fileName.removeSuffix(".mp3")
                        val artworkPath = "$MUSIC_LIBRARY_PATH/$baseName.jpg"
                        
                        var artworkUrl: String? = if (service.exists(artworkPath)) "file://$artworkPath" else null
                        
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
                            fileSize = service.getFileSize(absolutePath),
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
        metadata: MusicMetadata? = null
    ): Boolean = withContext(Dispatchers.IO) {
        try {
            updateProgress(InstallState.PREPARING, "Saving to Library...")
            service.createDirectory(MUSIC_LIBRARY_PATH)
            
            val sanitizedName = customName.replace(Regex("[^a-zA-Z0-9._-]"), "_")
            val baseFileName = sanitizedName.removeSuffix(".mp3")
            val fileName = "$baseFileName.mp3"
            val destPath = "$MUSIC_LIBRARY_PATH/$fileName"

            val pfd = context.contentResolver.openFileDescriptor(uri, "r") ?: return@withContext false
            if (service.importFile(pfd, destPath)) {
                // Determine the best Artwork source
                val finalArtUrl = metadata?.artworkUrl ?: thumbUrl
                
                if (finalArtUrl != null) {
                    try {
                        val artFile = File(context.cacheDir, "art_buffer.jpg")
                        val artUrl = URL(finalArtUrl)
                        artUrl.openStream().use { input ->
                            artFile.outputStream().use { output -> input.copyTo(output) }
                        }
                        service.copyFile(artFile.absolutePath, "$MUSIC_LIBRARY_PATH/$baseFileName.jpg")
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
        try {
            val path = "$MUSIC_LIBRARY_PATH/$fileName"
            if (service.delete(path)) {
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
        try {
            val rr3Path = getRr3Path()
            val source = "$MUSIC_LIBRARY_PATH/$libraryFileName"
            val dest = "$rr3Path/files/.depot/audio/music/$targetTrackFileName"

            if (!service.exists(source)) {
                fail("Library file not found.")
                return@withContext false
            }

            // 1. Ensure backup exists
            backupOriginalMusic()

            updateProgress(InstallState.INSTALLING, "Applying: $libraryFileName -> $targetTrackFileName")
            if (service.copyFile(source, dest)) {
                updateProgress(InstallState.SUCCESS, "MUSIC APPLIED FROM LIBRARY")
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
        try {
            if (isManual) updateProgress(InstallState.FETCHING_MANIFEST, "Checking for app updates...")
            val manifest = manifestManager.fetchManifest() ?: return false
            
            val currentVersion = context.packageManager.getPackageInfo(context.packageName, 0).versionName
            
            if (manifest.version != currentVersion) {
                Log.i(TAG, "Update found! Current: $currentVersion, New: ${manifest.version}")
                
                val apkFile = manifest.files.find { it.type == "apk" } ?: return false

                if (isManual) updateProgress(InstallState.DOWNLOADING, "Downloading Update ${manifest.version}...", apkFile.name)
                
                val tempApk = File(context.externalCacheDir, "update.apk")
                if (tempApk.exists()) tempApk.delete()

                if (downloader.download(apkFile.url, tempApk, apkFile.size) { current, total, _ ->
                    if (isManual) {
                        updateProgress(
                            state = InstallState.DOWNLOADING, 
                            step = "Downloading Update...", 
                            currentFile = apkFile.name, 
                            fileProgress = current.toFloat() / total,
                            overallProgress = current.toFloat() / total,
                            eta = "Soon",
                        )
                    }
                }) {
                    if (isManual) updateProgress(InstallState.UPDATE, "Installing Update...", apkFile.name)
                    if (service.installPackage(tempApk.absolutePath)) {
                        if (isManual) updateProgress(InstallState.SUCCESS, "UPDATE INSTALLED")
                        return true
                    }
                } else {
                    val error = downloader.lastError
                    if (isManual) fail("Update download failed: $error")
                }
            } else {
                if (isManual) updateProgress(InstallState.SUCCESS, "APP IS UP TO DATE")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Auto-update failed", e)
        }
        return false
    }
}
