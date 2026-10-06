package com.retr0.projectretr0.installer

import android.util.Log
import com.retr0.projectretr0.installer.persistence.ChunkDao
import com.retr0.projectretr0.installer.persistence.ChunkEntity
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URI
import java.net.URL
import java.util.concurrent.atomic.AtomicLong
import kotlin.math.pow
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds

/**
 * Robust downloader with Resume support, Parallel Chunking, and Exponential Backoff.
 */
class FileDownloader {

    companion object {
        private const val TAG = "RETR0_DOWNLOADER"
        private const val BUFFER_SIZE = 64 * 1024
        private const val INITIAL_CONCURRENCY = 4

        fun localChunkFileName(chunk: String): String {
            val uri = runCatching { URI(chunk) }.getOrNull()
            val name = if (uri?.isAbsolute == true) uri.rawPath?.substringAfterLast('/') else chunk.substringAfterLast('/')
            require(!name.isNullOrBlank() && (name != ".") && (name != "..")) { "Invalid chunk URL or name: $chunk" }
            return name
        }
    }

    var lastError: String = "none"
        private set
        
    private var currentConcurrency = INITIAL_CONCURRENCY

    /**
     * Downloads a list of chunks in parallel and reports combined progress.
     */
    suspend fun downloadChunks(
        baseUrl: String,
        chunkNames: List<String>,
        destDir: File,
        totalSize: Long,
        modVersion: String,
        archiveId: String = "legacy",
        chunkDao: ChunkDao? = null,
        onProgress: (Long, Long, Double) -> Unit,
    ): Boolean = withContext(Dispatchers.IO) {
        require(chunkNames.isNotEmpty()) { "No chunks were provided" }
        require(totalSize > 0L) { "Chunked asset size must be positive" }
        Log.i(TAG, "code=ASSET_INSTALL_I007 stage=chunks_start count=${chunkNames.size} totalBytes=$totalSize concurrency=$currentConcurrency")
        val semaphore = Semaphore(currentConcurrency)
        val downloadedBytes = AtomicLong(0)
        val startTime = System.currentTimeMillis()
        
        // Speed measurement tracking
        var chunksFinished = 0
        
        // --- SMART RESUME: Pre-calculate already downloaded bytes ---
        var initialTotal = 0L
        val chunkCompletionMap = mutableMapOf<String, Boolean>()
        
        chunkNames.forEach { name ->
            val identity = chunkIdentity(name)
            val isDownloaded = chunkDao?.isChunkDownloaded(identity, modVersion, archiveId) ?: false
            if (isDownloaded) {
                val file = File(destDir, localChunkFileName(name))
                if (file.exists() && file.length() > 0L) {
                    initialTotal += file.length()
                    chunkCompletionMap[name] = true
                    chunksFinished++
                    Log.i(TAG, "code=ASSET_INSTALL_I008 stage=chunk_resume chunk=$name bytes=${file.length()}")
                }
            }
        }
        downloadedBytes.set(initialTotal)

        try {
            coroutineScope {
                val jobs = chunkNames.map { chunkName ->
                    async {
                        // Skip if already finished according to DB
                        if (chunkCompletionMap[chunkName] == true) return@async
                        
                        semaphore.withPermit {
                            val chunkUri = runCatching { URI(chunkName) }.getOrNull()
                            val chunkIsAbsolute = chunkUri?.isAbsolute == true
                            val chunkFileName = localChunkFileName(chunkName)
                            val chunkFile = File(destDir, chunkFileName)
                            val chunkUrl = if (chunkIsAbsolute) {
                                chunkName
                            } else if (baseUrl.endsWith("/")) {
                                "$baseUrl$chunkName"
                            } else {
                                "$baseUrl/$chunkName"
                            }
                            
                            Log.i(TAG, "code=ASSET_INSTALL_I009 stage=chunk_connect chunk=$chunkName url=$chunkUrl")
                            var success = false
                            var attempt = 1
                            while (!success && (attempt <= 8)) {
                                val sizeBeforeAttempt = chunkFile.takeIf { it.exists() }?.length() ?: 0L
                                success = downloadChunkSingle(chunkUrl, chunkFile) { added ->
                                    val currentTotal = downloadedBytes.addAndGet(added.toLong())
                                    val elapsed = (System.currentTimeMillis() - startTime) / 1000.0
                                    val speed = if (elapsed > 0) (currentTotal - initialTotal) / elapsed else 0.0
                                    onProgress(currentTotal, totalSize, speed)
                                }
                                if (!success) {
                                    val sizeAfterAttempt = chunkFile.takeIf { it.exists() }?.length() ?: 0L
                                    if (sizeAfterAttempt < sizeBeforeAttempt) {
                                        downloadedBytes.addAndGet(sizeAfterAttempt - sizeBeforeAttempt)
                                    }
                                    val backoff = (1_000L shl (attempt - 1).coerceAtMost(5)) + Random.nextLong(250L, 1_000L)
                                    Log.w(TAG, "Chunk attempt $attempt failed: $chunkUrl ($lastError)")
                                    attempt++
                                    delay(backoff.milliseconds)
                                }
                            }
                            
                            if (success) {
                                Log.i(TAG, "code=ASSET_INSTALL_I010 stage=chunk_complete chunk=$chunkName bytes=${chunkFile.length()} attempt=$attempt")
                                // --- SMART RESUME: Record completion in DB ---
                                chunkDao?.markChunkDownloaded(
                                    ChunkEntity(chunkIdentity(chunkName), modVersion, archiveId),
                                )
                                
                                chunksFinished++
                                // ADAPTIVE SCALING: Adjust threads after first 4 chunks
                                if (chunksFinished == 4) {
                                    val elapsed = (System.currentTimeMillis() - startTime) / 1000.0
                                    val totalDownloadedSinceStart = downloadedBytes.get() - initialTotal
                                    val avgSpeed = if (elapsed > 0) totalDownloadedSinceStart / elapsed else 0.0
                                    
                                    val newThreads = when {
                                        (avgSpeed > (5 * 1024 * 1024)) -> 12
                                        (avgSpeed > (2 * 1024 * 1024)) -> 8
                                        (avgSpeed < (500 * 1024)) -> 2
                                        else -> 4
                                    }
                                    
                                    if (newThreads != currentConcurrency) {
                                        Log.i(TAG, "Adaptive Scaling recommendation: ${avgSpeed / 1024 / 1024} MB/s -> $newThreads threads (applies next batch)")
                                        currentConcurrency = newThreads
                                    }
                                }
                            } else {
                                throw Exception("Failed to download chunk: $chunkName")
                            }
                        }
                    }
                }
                jobs.awaitAll()
            }
            Log.i(TAG, "code=ASSET_INSTALL_I011 stage=chunks_complete downloadedBytes=${downloadedBytes.get()} totalBytes=$totalSize")
            true
        } catch (e: Exception) {
            Log.e(TAG, "code=ASSET_INSTALL_E006 stage=chunks_failed downloadedBytes=${downloadedBytes.get()} totalBytes=$totalSize", e)
            lastError = e.message ?: "Chunk failure"
            false
        }
    }

    private suspend fun downloadChunkSingle(
        urlStr: String,
        dest: File,
        onByteAdded: (Int) -> Unit,
    ): Boolean = withContext(Dispatchers.IO) {
        var connection: HttpURLConnection? = null
        try {
            val existingSize = if (dest.exists()) dest.length() else 0L
            val url = URL(urlStr)
            connection = url.openConnection() as HttpURLConnection
            connection.instanceFollowRedirects = true
            connection.connectTimeout = 60000
            connection.readTimeout = 120000
            connection.setRequestProperty("User-Agent", "RETR0-Installer/1.1 (Android)")
            connection.setRequestProperty("Accept", "application/octet-stream, */*")
            connection.setRequestProperty("Accept-Encoding", "identity")
            
            if (existingSize > 0) {
                connection.setRequestProperty("Range", "bytes=$existingSize-")
            }

            Log.i(TAG, "code=ASSET_INSTALL_I012 stage=http_connect url=$urlStr existingBytes=$existingSize")
            connection.connect()
            when (val code = connection.responseCode) {
                HttpURLConnection.HTTP_OK, HttpURLConnection.HTTP_PARTIAL -> {
                    Log.i(TAG, "code=ASSET_INSTALL_I013 stage=http_response url=$urlStr status=$code contentLength=${connection.contentLengthLong} contentRange=${connection.getHeaderField("Content-Range")}")
                    val isResuming = (code == HttpURLConnection.HTTP_PARTIAL)
                    if (existingSize > 0L && code == HttpURLConnection.HTTP_OK) dest.delete()
                    if (existingSize > 0L && code == HttpURLConnection.HTTP_PARTIAL &&
                        !validContentRange(connection.getHeaderField("Content-Range"), existingSize)
                    ) return@withContext false
                    val expectedLength = connection.contentLengthLong
                    var received = 0L
                    connection.inputStream.use { input ->
                        FileOutputStream(dest, isResuming).use { output ->
                            val buffer = ByteArray(BUFFER_SIZE)
                            var bytes: Int
                            while (input.read(buffer).also { bytes = it } != -1) {
                                if (bytes == 0) continue
                                output.write(buffer, 0, bytes)
                                received += bytes
                                onByteAdded(bytes)
                            }
                        }
                    }
                    if (expectedLength >= 0L && received != expectedLength) {
                        lastError = "Incomplete response for $urlStr: expected $expectedLength bytes, received $received"
                        return@withContext false
                    }
                    true
                }
                416 -> {
                    lastError = "HTTP 416 for $urlStr"
                    false
                }
                else -> {
                    lastError = "HTTP $code for $urlStr"
                    false
                }
            }
        } catch (e: Exception) {
            lastError = "${e.javaClass.simpleName}: ${e.message ?: "network failure"} for $urlStr"
            Log.w(TAG, lastError, e)
            false
        } finally {
            connection?.disconnect()
        }
    }

    private fun chunkIdentity(chunk: String): String = chunk

    private fun validContentRange(value: String?, offset: Long): Boolean {
        val start = value?.substringAfter("bytes ", "")?.substringBefore("-")?.toLongOrNull()
        return start == offset
    }

    /**
     * Legacy single-file downloader
     */
    suspend fun download(
        urlString: String,
        destination: File,
        manifestSize: Long = -1L,
        onProgress: (Long, Long, Double) -> Unit = { _, _, _ -> },
    ): Boolean = withContext(Dispatchers.IO) {
        val maxAttempts = 10 
        
        for (attempts in 1..maxAttempts) {
            var connection: HttpURLConnection? = null
            try {
                val existingSize = if (destination.exists()) destination.length() else 0L
                
                if ((manifestSize > 0) && (existingSize >= manifestSize)) {
                    onProgress(manifestSize, manifestSize, 0.0)
                    return@withContext true
                }

                val url = URL(urlString)
                connection = url.openConnection() as HttpURLConnection
                connection.instanceFollowRedirects = true
                connection.connectTimeout = 60000 
                connection.readTimeout = 60000    
                connection.setRequestProperty("User-Agent", "RETR0-Installer/1.0 (Android)")
                connection.setRequestProperty("Accept", "*/*")
                connection.setRequestProperty("Connection", "close") 
                
                if (existingSize > 0) {
                    connection.setRequestProperty("Range", "bytes=$existingSize-")
                }

                connection.connect()
                val responseCode = connection.responseCode

                if ((responseCode != HttpURLConnection.HTTP_OK) && (responseCode != HttpURLConnection.HTTP_PARTIAL)) {
                    lastError = "HTTP $responseCode"
                    if (responseCode in (400..499)) return@withContext false 
                    throw Exception("HTTP $responseCode")
                }

                val isResuming = (responseCode == HttpURLConnection.HTTP_PARTIAL)
                val contentRange = connection.getHeaderField("Content-Range")
                val len = connection.contentLength.toLong()
                val totalFileLength: Long = if (contentRange != null) {
                    contentRange.substringAfterLast('/').toLongOrNull() ?: manifestSize
                } else {
                    if (isResuming && (len > 0)) len + existingSize else len
                }

                val finalTotalLength = if (totalFileLength > 0) totalFileLength else manifestSize
                destination.parentFile?.mkdirs()

                connection.inputStream.use { input ->
                    FileOutputStream(destination, isResuming).use { output ->
                        val data = ByteArray(BUFFER_SIZE)
                        var bytesDownloaded: Long = if (isResuming) existingSize else 0L
                        val startTime = System.currentTimeMillis()
                        var lastReportTime = 0L
                        var lastBytesForSpeed = bytesDownloaded
                        var lastTimeForSpeed = startTime

                        while (true) {
                            val count = try {
                                input.read(data)
                            } catch (e: IOException) {
                                if (e.message?.contains("reset", ignoreCase = true) == true) throw e 
                                else throw e
                            }
                            
                            if (count == -1) break
                            bytesDownloaded += count
                            output.write(data, 0, count)
                            
                            val currentTime = System.currentTimeMillis()
                            if ((currentTime - lastReportTime) > 500) { 
                                val timeDelta = (currentTime - lastTimeForSpeed) / 1000.0
                                val byteDelta = bytesDownloaded - lastBytesForSpeed
                                val currentSpeed = if (timeDelta > 0) byteDelta / timeDelta else 0.0
                                onProgress(bytesDownloaded, if (finalTotalLength > 0) finalTotalLength else bytesDownloaded, currentSpeed)
                                lastBytesForSpeed = bytesDownloaded
                                lastTimeForSpeed = currentTime
                                lastReportTime = currentTime
                            }
                        }
                    }
                }
                onProgress(finalTotalLength, finalTotalLength, 0.0)
                return@withContext true

            } catch (e: Exception) {
                lastError = e.message ?: "Unknown Error"
                if (attempts >= maxAttempts) return@withContext false
                val backoff = (2.0.pow(attempts.toDouble()) * 1000).toLong().coerceAtMost(30000)
                delay(backoff.milliseconds)
            } finally {
                connection?.disconnect()
            }
        }
        false
    }
}
