package com.retr0.projectretr0.installer

import android.content.Context
import android.util.Log
import org.chromium.net.CronetEngine
import java.io.File

/**
 * High-performance network stack using Google Cronet (Chromium's Network Stack).
 * Configured for HTTP/3, QUIC, and 0-RTT support.
 */
object NetworkStack {
    private const val TAG = "RETR0_NETWORK"
    private var cronetEngine: CronetEngine? = null

    fun getEngine(context: Context): CronetEngine {
        return cronetEngine ?: synchronized(this) {
            cronetEngine ?: buildEngine(context).also { cronetEngine = it }
        }
    }

    private fun buildEngine(context: Context): CronetEngine {
        Log.i(TAG, "Initializing Cronet Engine with HTTP/3 and 0-RTT...")
        
        val cacheDir = File(context.cacheDir, "cronet_cache").apply { mkdirs() }
        
        val builder = CronetEngine.Builder(context)
            .enableHttp2(true)
            .enableQuic(true)
            .enableBrotli(true)
            .setStoragePath(cacheDir.absolutePath)
            .enableHttpCache(CronetEngine.Builder.HTTP_CACHE_DISK, 50 * 1024 * 1024) // 50MB Cache

        return builder.build()
    }
}
