package com.retr0.projectretr0.installer

import java.net.URI

internal enum class LibraryArtworkSource {
    FILE,
    CONTENT,
    REMOTE_HTTPS,
}

internal object LibraryArtworkSourcePolicy {
    fun candidates(localUri: URI?, artworkUrl: String?): List<LibraryArtworkSource> {
        val localSource = when (localUri?.scheme?.lowercase()) {
            null -> null
            "file" -> LibraryArtworkSource.FILE
            "content" -> LibraryArtworkSource.CONTENT
            else -> null
        }
        val remoteUri = artworkUrl
            ?.takeIf { it.isNotBlank() }
            ?.let { runCatching { URI(it) }.getOrNull() }
        val remoteSource = remoteUri
            ?.takeIf { it.scheme.equals("https", ignoreCase = true) && !it.host.isNullOrBlank() }
            ?.let { LibraryArtworkSource.REMOTE_HTTPS }
        return listOfNotNull(localSource, remoteSource)
    }
}
