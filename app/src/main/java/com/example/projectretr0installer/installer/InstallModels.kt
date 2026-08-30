package com.example.projectretr0installer.installer

/**
 * Represents the current state of the installation process.
 */
enum class InstallState {
    IDLE,
    PREPARING,
    FETCHING_MANIFEST,
    DOWNLOADING,
    VERIFYING,
    EXTRACTING,
    INSTALLING,
    UPDATE,
    SUCCESS,
    FAILED,
    CANCELLED,
}

/**
 * Data model for a single file in the installation manifest.
 */
data class InstallFile(
    val id: String,
    val name: String,
    val url: String,
    val size: Long,
    val uncompressedSize: Long = 0,
    val sha256: String,
    val destination: String,
    val type: String = "file", // "file", "zip", etc.
    val required: Boolean = true,
)

/**
 * Data model for the entire installation manifest.
 */
data class InstallManifest(
    val project: String,
    val version: String,
    val release: String,
    val files: List<InstallFile>,
)

/**
 * Represents the progress of the current installation.
 */
data class InstallProgress(
    val state: InstallState = InstallState.IDLE,
    val currentStep: String = "",
    val currentFile: String = "",
    val fileProgress: Float = 0f,
    val overallProgress: Float = 0f,
    val completedFiles: Int = 0,
    val totalFiles: Int = 0,
    val eta: String = "",
    val errorMessage: String? = null,
)

/**
 * Represents a save file shared by the community.
 */
data class CommunitySave(
    val id: String,
    val customName: String,
    val uploaderName: String,
    val fileSize: Long,
    val createdAt: String,
    val playerLevel: Int = 0,
    val playerGold: Long = 0,
    val playerRs: Long = 0,
    val playerMs: Long = 0,
    val likes: Int = 0,
    val downloads: Int = 0,
) {
    val formattedSize: String get() = StorageUtils.formatSize(fileSize)
}

/**
 * Represents a music file shared by the community.
 */
data class CommunityMusic(
    val id: String,
    val customName: String,
    val uploaderName: String,
    val fileSize: Long,
    val createdAt: String,
    val targetTrack: String, // e.g., "Garage_Music.mp3"
    val likes: Int = 0,
    val downloads: Int = 0,
) {
    val formattedSize: String get() = StorageUtils.formatSize(fileSize)
}

/**
 * Represents a piece of game music that can be modded.
 */
data class MusicTrack(
    val fileName: String,
    val displayName: String,
    val isModded: Boolean = false,
)

/**
 * Represents a music file stored in the local persistent library.
 */
data class LibraryMusic(
    val fileName: String,
    val fileSize: Long,
    val absolutePath: String,
    val trackName: String? = null,
    val artistName: String? = null,
    val artworkUrl: String? = null
) {
    val formattedSize: String get() = StorageUtils.formatSize(fileSize)
}

/**
 * Metadata for a YouTube video.
 */
data class YouTubeInfo(
    val title: String,
    val thumbnailUrl: String?,
    val url: String
)

/**
 * Result of a YouTube audio download.
 */
data class YouTubeDownloadResult(
    val file: java.io.File,
    val info: YouTubeInfo
)

/**
 * Detailed music metadata from external source.
 */
data class MusicMetadata(
    val trackName: String,
    val artistName: String,
    val artworkUrl: String?,
    val previewUrl: String? = null,
    val collectionName: String? = null
)
