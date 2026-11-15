package com.retr0.projectretr0.installer

import android.content.Context
import android.os.ParcelFileDescriptor
import android.util.Log
import com.retr0.projectretr0.IRetr0UserService
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
                Log.i("SONIC_INSTALLER", "code=ASSET_INSTALL_I014 stage=native_extract_start destination=$privilegedDest")
                installCount = service.nativeExtract(readSide, privilegedDest)
                Log.i("SONIC_INSTALLER", "code=ASSET_INSTALL_I015 stage=native_extract_result result=$installCount destination=$privilegedDest")
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

        if (installCount > 0) repairGamePermissions(privilegedDest)
        installCount
    }

    /**
     * Data extracted by older helpers was created 0640/0750 by the shell user, so RR3 could read but never save.
     * Re-applies group write to the RR3 roots this install touched; a failure is logged, not fatal.
     */
    private fun repairGamePermissions(privilegedDest: String) {
        listOf("/storage/emulated/0/Android/data/com.ea.games.r3_row", "/storage/emulated/0/Android/data/com.ea.games.r3_na")
            .filter { root -> root.startsWith(privilegedDest.trimEnd('/')) || privilegedDest.startsWith(root) }
            .forEach { root ->
                runCatching { service.repairGameDataPermissions(root) }
                    .onSuccess { Log.i("SONIC_INSTALLER", "code=ASSET_INSTALL_I016 stage=permission_repair root=${root.substringAfterLast('/')} $it") }
                    .onFailure { Log.e("SONIC_INSTALLER", "code=ASSET_INSTALL_E016 stage=permission_repair root=${root.substringAfterLast('/')} error=${it.javaClass.simpleName}", it) }
            }
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
