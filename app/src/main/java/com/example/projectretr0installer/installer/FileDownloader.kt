package com.example.projectretr0installer.installer

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL
import kotlin.math.pow

/**
 * Robust downloader with Resume support, Content-Range parsing, and Exponential Backoff.
 */
class FileDownloader {

    private companion object {
        private const val TAG = "RETR0_DOWNLOADER"
        private const val BUFFER_SIZE = 64 * 1024
    }

    var lastError: String = "none"
        private set

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
                    Log.i(TAG, "File already complete: ${destination.name}")
                    onProgress(manifestSize, manifestSize, 0.0)
                    return@withContext true
                }

                val url = URL(urlString)
                connection = url.openConnection() as HttpURLConnection
                connection.instanceFollowRedirects = true
                connection.connectTimeout = 60000 // Increased to 60s
                connection.readTimeout = 60000    // Increased to 60s
                connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Android)")
                connection.setRequestProperty("Accept", "*/*")
                connection.setRequestProperty("Connection", "Keep-Alive")
                
                if (existingSize > 0) {
                    connection.setRequestProperty("Range", "bytes=$existingSize-")
                    Log.i(TAG, "Requesting Range: bytes=$existingSize-")
                }

                connection.connect()
                val responseCode = connection.responseCode
                Log.i(TAG, "Server response: $responseCode")

                if ((responseCode != HttpURLConnection.HTTP_OK) && (responseCode != HttpURLConnection.HTTP_PARTIAL)) {
                    lastError = "HTTP $responseCode"
                    if (responseCode in (400..499)) {
                        Log.e(TAG, "Permanent Error: HTTP $responseCode")
                        return@withContext false 
                    }
                    throw Exception("HTTP $responseCode")
                }

                val isResuming = (responseCode == HttpURLConnection.HTTP_PARTIAL)
                
                // CRITICAL MATH FIX: Use Content-Range to get the TRUE total size of the 10GB file
                val contentRange = connection.getHeaderField("Content-Range")
                val totalFileLength: Long = if (contentRange != null) {
                    contentRange.substringAfterLast('/').toLongOrNull() ?: manifestSize
                } else {
                    val len = connection.contentLength.toLong()
                    if ((isResuming) && (len > 0)) len + existingSize else len
                }

                Log.i(TAG, "True Total Size: $totalFileLength bytes")
                
                destination.parentFile?.mkdirs()

                connection.inputStream.use { input ->
                    FileOutputStream(destination, isResuming).use { output ->
                        val data = ByteArray(BUFFER_SIZE)
                        var bytesDownloaded: Long = if (isResuming) existingSize else 0L
                        val startTime = System.currentTimeMillis()
                        var lastReportTime = 0L
                        
                        // ROBUST SPEED TRACKING
                        var lastBytesForSpeed = bytesDownloaded
                        var lastTimeForSpeed = startTime
                        var count: Int

                        while (input.read(data).also { count = it } != -1) {
                            bytesDownloaded += count
                            output.write(data, 0, count)
                            
                            val currentTime = System.currentTimeMillis()
                            if ((currentTime - lastReportTime) > 500) { 
                                if (totalFileLength > 0) {
                                    // Calculate speed over the LAST 500ms window (more reactive)
                                    val timeDelta = (currentTime - lastTimeForSpeed) / 1000.0
                                    val byteDelta = bytesDownloaded - lastBytesForSpeed
                                    val currentSpeed = if (timeDelta > 0) byteDelta / timeDelta else 0.0
                                    
                                    onProgress(bytesDownloaded, totalFileLength, currentSpeed)
                                    
                                    lastBytesForSpeed = bytesDownloaded
                                    lastTimeForSpeed = currentTime
                                }
                                lastReportTime = currentTime
                            }
                        }
                    }
                }
                
                onProgress(totalFileLength, totalFileLength, 0.0)
                Log.i(TAG, "Download Success: ${destination.name}")
                return@withContext true

            } catch (e: Exception) {
                Log.w(TAG, "Attempt $attempts failed: ${e.message}")
                lastError = e.message ?: "Unknown Error"
                if (attempts >= maxAttempts) return@withContext false
                
                // Exponential backoff with a cap: 2s, 4s, 8s... up to 30s
                val backoff = (2.0.pow(attempts.toDouble()) * 1000).toLong().coerceAtMost(30000)
                kotlinx.coroutines.delay(kotlin.time.Duration.parse("${backoff}ms"))
            } finally {
                connection?.disconnect()
            }
        }
        false
    }
}
