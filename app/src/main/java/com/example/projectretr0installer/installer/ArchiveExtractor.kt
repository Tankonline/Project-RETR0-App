package com.example.projectretr0installer.installer

import android.os.ParcelFileDescriptor
import android.util.Log
import com.example.projectretr0installer.IRetr0UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

/**
 * Handles extracting and installing files from ZIP archives.
 * Uses a Zero-Disk streaming pipeline to maximize speed and minimize storage usage.
 */
@Suppress("unused")
class ArchiveExtractor {

    private companion object {
        private const val TAG = "RETR0_EXTRACTOR"
        private const val BUFFER_SIZE = 128 * 1024 // 128KB buffer
    }

    suspend fun extractAndInstall(
        zipFile: File,
        privilegedDest: String,
        service: IRetr0UserService,
        onFileProgress: (String) -> Unit,
    ): Int = withContext(Dispatchers.IO) {
        var installCount = 0
        var lastUpdate = 0L
        val createdDirs = mutableSetOf<String>()
        
        val zis = ZipInputStream(FileInputStream(zipFile))
        try {
            Log.i(TAG, "Starting Zero-Disk extraction: ${zipFile.name}")
            
            var entry: ZipEntry? = zis.nextEntry
            while (entry != null) {
                val entryName = entry.name.trimStart('/')
                if (entryName.isEmpty() || entryName.contains("__MACOSX")) {
                    zis.closeEntry()
                    entry = zis.nextEntry
                    continue
                }

                val finalDestPath = resolveFinalPath(entryName, privilegedDest)

                if (!isPathWithinDestination(finalDestPath, privilegedDest)) {
                    Log.w(TAG, "Skipping unsafe ZIP entry: $entryName")
                    zis.closeEntry()
                    entry = zis.nextEntry
                    continue
                }

                if (!entry.isDirectory) {
                    val now = System.currentTimeMillis()
                    if ((now - lastUpdate) > 300) {
                        onFileProgress(entryName)
                        lastUpdate = now
                        // Keep-Alive: Ping service during long extractions
                        try { service.ping() } catch (_: Exception) {}
                    }
                    
                    val parentDir = File(finalDestPath).parent ?: ""
                    if (createdDirs.add(parentDir)) {
                        service.createDirectory(parentDir)
                    }

                    // Use a pipe to stream directly into the privileged service
                    val pipe = ParcelFileDescriptor.createPipe()
                    val readSide = pipe[0]
                    val writeSide = pipe[1]

                    coroutineScope {
                        // Launch the consumer (AIDL call)
                        val importJob = launch(Dispatchers.IO) {
                            try {
                                if (service.importFile(readSide, finalDestPath)) {
                                    installCount++
                                }
                            } catch (e: Exception) {
                                Log.e(TAG, "Privileged import failed for $entryName", e)
                            } finally {
                                try { readSide.close() } catch (_: Exception) {}
                            }
                        }

                        // Producer: Write ZIP content to the pipe
                        try {
                            ParcelFileDescriptor.AutoCloseOutputStream(writeSide).use { output ->
                                val buffer = ByteArray(BUFFER_SIZE)
                                var len: Int
                                while (zis.read(buffer).also { len = it } > 0) {
                                    output.write(buffer, 0, len)
                                }
                                output.flush()
                            }
                        } finally {
                            importJob.join()
                        }
                    }
                } else {
                    if (createdDirs.add(finalDestPath)) {
                        service.createDirectory(finalDestPath)
                    }
                }
                zis.closeEntry()
                entry = zis.nextEntry
            }
        } catch (e: Exception) {
            Log.e(TAG, "Extraction aborted: ${e.message}")
            throw e 
        } finally {
            try { zis.close() } catch (_: Exception) {}
        }
        installCount
    }

    private fun resolveFinalPath(entryName: String, privilegedDest: String): String {
        val packageNames = listOf("com.ea.games.r3_row", "com.ea.games.r3_na")
        for (pkg in packageNames) {
            if (entryName.startsWith("$pkg/")) {
                return File(privilegedDest, entryName.substringAfter("$pkg/")).canonicalPath
            }
        }
        return File(privilegedDest, entryName).canonicalPath
    }

    private fun isPathWithinDestination(path: String, destination: String): Boolean {
        val root = File(destination).canonicalFile.toPath()
        val candidate = File(path).canonicalFile.toPath()
        return candidate == root || candidate.startsWith(root)
    }
}
