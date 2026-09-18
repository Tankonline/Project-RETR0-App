package com.example.projectretr0installer.installer

import android.content.Context
import android.util.Log
import org.chromium.net.UrlRequest
import org.chromium.net.UrlResponseInfo
import org.chromium.net.CronetException
import java.io.IOException
import java.io.InputStream
import java.io.PipedInputStream
import java.io.PipedOutputStream
import java.nio.ByteBuffer
import java.util.concurrent.Executors

/**
 * A streaming downloader that uses Cronet to provide a real-time InputStream.
 * This avoids writing the entire file to disk before processing.
 */
class StreamDownloader(@Suppress("UNUSED_PARAMETER") private val context: Context) {
    private val engine = NetworkStack.getEngine(context)
    private val executor = Executors.newSingleThreadExecutor()

    fun getStream(url: String): InputStream {
        require(url.startsWith("https://", ignoreCase = true)) { "Only HTTPS downloads are supported" }
        Log.i("STREAM_DOWNLOADER", "Opening stream")
        val pipedOut = PipedOutputStream()
        val pipedIn = object : PipedInputStream(pipedOut, 2 * 1024 * 1024) {
            override fun close() {
                try { super.close() } finally { try { pipedOut.close() } catch (_: IOException) {} }
            }
        }

        val callback = object : UrlRequest.Callback() {
            override fun onRedirectReceived(request: UrlRequest, info: UrlResponseInfo, newLocation: String) {
                if (!newLocation.startsWith("https://", ignoreCase = true)) {
                    Log.e("STREAM_DOWNLOADER", "Blocked non-HTTPS redirect")
                    request.cancel()
                } else {
                    request.followRedirect()
                }
            }

            override fun onResponseStarted(request: UrlRequest, info: UrlResponseInfo) {
                Log.i("STREAM_DOWNLOADER", "Response started. Code: ${info.httpStatusCode}")
                val contentType = info.allHeaders["Content-Type"]?.firstOrNull()?.lowercase()
                if (info.httpStatusCode !in 200..299 || (contentType != null && !(contentType.startsWith("application/") || contentType.startsWith("application/octet-stream") || contentType.startsWith("binary/")))) {
                    Log.e("STREAM_DOWNLOADER", "Request failed with code: ${info.httpStatusCode}")
                    request.cancel()
                    try { pipedOut.close() } catch (_: Exception) {}
                } else {
                    request.read(ByteBuffer.allocateDirect(128 * 1024))
                }
            }

            override fun onReadCompleted(request: UrlRequest, info: UrlResponseInfo, byteBuffer: ByteBuffer) {
                byteBuffer.flip()
                val bytes = ByteArray(byteBuffer.remaining())
                byteBuffer[bytes]
                
                try {
                    pipedOut.write(bytes)
                    byteBuffer.clear()
                    request.read(byteBuffer)
                } catch (e: Exception) {
                    Log.e("STREAM_DOWNLOADER", "Pipe write failed (maybe closed?)", e)
                    request.cancel()
                    try { pipedOut.close() } catch (_: Exception) {}
                }
            }

            override fun onSucceeded(request: UrlRequest, info: UrlResponseInfo) {
                Log.i("STREAM_DOWNLOADER", "Request succeeded.")
                try { pipedOut.close() } catch (_: Exception) {}
            }

            override fun onFailed(request: UrlRequest, info: UrlResponseInfo, error: CronetException) {
                Log.e("STREAM_DOWNLOADER", "Cronet request failed", error)
                try { pipedOut.close() } catch (_: Exception) {}
            }

            override fun onCanceled(request: UrlRequest, info: UrlResponseInfo) {
                Log.i("STREAM_DOWNLOADER", "Request canceled.")
                try { pipedOut.close() } catch (_: Exception) {}
            }
        }

        val request = engine.newUrlRequestBuilder(url, callback, executor)
            .setPriority(UrlRequest.Builder.REQUEST_PRIORITY_HIGHEST)
            .build()
        
        request.start()
        return pipedIn
    }
}
