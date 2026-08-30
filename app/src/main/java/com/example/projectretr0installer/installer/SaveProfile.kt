package com.example.projectretr0installer.installer

import java.io.File

/**
 * Represents a saved game state snapshot in the Save Hub.
 */
data class SaveProfile(
    val name: String,
    val timestamp: Long,
    val fileCount: Int,
    val totalSize: Long,
    val path: File
) {
    val formattedSize: String get() = StorageUtils.formatSize(totalSize)
    val formattedDate: String get() = java.text.DateFormat.getDateTimeInstance().format(java.util.Date(timestamp))
}
