package com.retr0.projectretr0.installer

import android.os.StatFs
import java.io.File
import java.util.Locale

/**
 * Utility for checking storage availability and formatting sizes.
 */
object StorageUtils {

    /**
     * Returns the available bytes on the partition containing the given path.
     */
    fun getAvailableSpace(dir: File): Long {
        return try {
            val stat = StatFs(dir.absolutePath)
            stat.availableBlocksLong * stat.blockSizeLong
        } catch (_: Exception) {
            -1L
        }
    }

    /**
     * Formats bytes into a human-readable string (e.g., 1.23 GB).
     */
    fun formatSize(bytes: Long): String {
        if (bytes < 0) return "Unknown"
        val kb = 1024.0
        val mb = kb * 1024
        val gb = mb * 1024

        return when {
            bytes >= gb -> String.format(Locale.ENGLISH, "%.2f GB", bytes / gb)
            bytes >= mb -> String.format(Locale.ENGLISH, "%.2f MB", bytes / mb)
            bytes >= kb -> String.format(Locale.ENGLISH, "%.2f KB", bytes / kb)
            else -> "$bytes Bytes"
        }
    }
}
