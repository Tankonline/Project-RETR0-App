package com.example.projectretr0installer.installer

import android.content.Context
import android.os.ParcelFileDescriptor
import android.util.Log
import com.example.projectretr0installer.IRetr0UserService
import kotlinx.coroutines.*
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.util.concurrent.atomic.AtomicReference

/**
 * SonicInstaller: A high-speed installation engine that pipes bytes directly 
 * from the network into the game directory via ZipInputStream and Shizuku.
 */
class SonicInstaller(
    @Suppress("unused")
    private val context: Context,
    private val service: IRetr0UserService,
) {
    private val downloader = StreamDownloader(context)

    suspend fun streamInstall(
        url: String,
        privilegedDest: String,
        @Suppress("UNUSED_PARAMETER") onFileProgress: (String) -> Unit
    ): Int = installStream(downloader.getStream(url), privilegedDest)

    suspend fun installSplitArchive(
        partFiles: List<File>,
        privilegedDest: String,
    ): Int = installStream(SplitZipSupport.open(partFiles), privilegedDest)

    suspend fun installArchive(
        archiveFile: File,
        privilegedDest: String,
    ): Int = installStream(archiveFile.inputStream(), privilegedDest)

    private suspend fun installStream(
        inputStream: InputStream,
        privilegedDest: String,
    ): Int = withContext(Dispatchers.IO) {
        val pipe = ParcelFileDescriptor.createPipe()
        val readSide = pipe[0]
        val writeSide = pipe[1]

        var installCount = -1
        val feederFailure = AtomicReference<Throwable?>(null)

        supervisorScope {
            // Keep feeder failures from cancelling the caller before native extraction can report its result.
            val feederJob = launch(Dispatchers.IO) {
                try {
                    inputStream.use { input ->
                        ParcelFileDescriptor.AutoCloseOutputStream(writeSide).use { output ->
                            val buffer = ByteArray(256 * 1024)
                            var len: Int
                            while (input.read(buffer).also { len = it } > 0) {
                                output.write(buffer, 0, len)
                            }
                            output.flush()
                        }
                    }
                } catch (error: Throwable) {
                    feederFailure.set(error)
                }
            }

            try {
                installCount = service.nativeExtract(readSide, privilegedDest)
                Log.i("SONIC_INSTALLER", "Native extraction returned $installCount")
            } catch (error: CancellationException) {
                feederJob.cancel()
                throw error
            } catch (error: Exception) {
                Log.e("SONIC_INSTALLER", "Remote native extraction failed", error)
                installCount = -1
            } finally {
                runCatching { readSide.close() }
                if (installCount < 0 && !feederJob.isCompleted) {
                    feederJob.cancel()
                    runCatching { writeSide.close() }
                    runCatching { inputStream.close() }
                }
                feederJob.join()
            }
        }

        feederFailure.get()?.let { error ->
            val pipeClosedAfterExtraction = installCount > 0 &&
                error is IOException &&
                (error.message?.contains("EPIPE", ignoreCase = true) == true ||
                    error.message?.contains("Broken pipe", ignoreCase = true) == true)
            val pipeClosedAfterFailure = installCount < 0 &&
                (error is IOException || error is CancellationException)

            when {
                pipeClosedAfterExtraction -> Log.i(
                    "SONIC_INSTALLER",
                    "Archive entries extracted successfully; ignored expected pipe closure after central directory",
                )
                pipeClosedAfterFailure -> Log.w(
                    "SONIC_INSTALLER",
                    "Pipe feeder stopped after native extraction failure: ${error.message}",
                )
                else -> {
                    Log.e("SONIC_INSTALLER", "Archive pipe feeder failed", error)
                    installCount = -1
                }
            }
        }

        installCount
    }

    @Suppress("unused")
    private fun resolveFinalPath(entryName: String, privilegedDest: String): String {
        val packageNames = listOf("com.ea.games.r3_row", "com.ea.games.r3_na")
        for (pkg in packageNames) {
            if (entryName.startsWith("$pkg/")) {
                return File(privilegedDest, entryName.substringAfter("$pkg/")).absolutePath
            }
        }
        return File(privilegedDest, entryName).absolutePath
    }
}
