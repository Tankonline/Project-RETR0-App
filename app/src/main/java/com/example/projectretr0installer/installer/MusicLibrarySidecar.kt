package com.example.projectretr0installer.installer

import org.json.JSONObject

internal data class MusicLibrarySidecar(
    val trackName: String,
    val artistName: String,
)

internal object MusicLibrarySidecarCodec {
    const val SIDECAR_SUFFIX = ".metadata.json"
    const val ARTWORK_SUFFIX = ".artwork.jpg"

    fun sanitizeBaseName(name: String): String {
        val withoutExtension = name.trim().removeSuffix(".mp3").removeSuffix(".MP3")
        val sanitized = withoutExtension
            .replace(Regex("[^a-zA-Z0-9._-]"), "_")
            .trim('.', '_', '-')
            .take(120)
        return sanitized.ifBlank { "track" }
    }

    fun sidecarFileName(baseName: String): String = "${sanitizeBaseName(baseName)}$SIDECAR_SUFFIX"

    fun artworkFileName(baseName: String): String = "${sanitizeBaseName(baseName)}$ARTWORK_SUFFIX"

    fun stagingFileName(finalFileName: String, token: String): String {
        val sanitizedToken = token.filter(Char::isLetterOrDigit).ifBlank { "stage" }
        val semanticSuffix = when {
            finalFileName.endsWith(SIDECAR_SUFFIX, ignoreCase = true) -> SIDECAR_SUFFIX
            finalFileName.endsWith(ARTWORK_SUFFIX, ignoreCase = true) -> ARTWORK_SUFFIX
            finalFileName.endsWith(".mp3", ignoreCase = true) -> ".mp3"
            else -> throw IllegalArgumentException("Unsupported music library file name")
        }
        val stem = finalFileName.dropLast(semanticSuffix.length)
        return "$stem.$sanitizedToken.tmp$semanticSuffix"
    }

    fun serialize(sidecar: MusicLibrarySidecar): ByteArray = JSONObject()
        .put("trackName", sidecar.trackName.trim())
        .put("artistName", sidecar.artistName.trim())
        .toString()
        .toByteArray(Charsets.UTF_8)

    fun parse(bytes: ByteArray?): MusicLibrarySidecar? {
        if (bytes == null || bytes.isEmpty()) return null
        return runCatching {
            val json = JSONObject(bytes.toString(Charsets.UTF_8))
            val trackName = json.optString("trackName").trim()
            val artistName = json.optString("artistName").trim()
            if (trackName.isBlank() || artistName.isBlank()) null
            else MusicLibrarySidecar(trackName, artistName)
        }.getOrNull()
    }

    fun resolve(
        sidecar: MusicLibrarySidecar?,
        fallbackTrackName: String?,
        fallbackArtistName: String?,
    ): MusicLibrarySidecar? = sidecar ?: fallbackTrackName?.takeIf { it.isNotBlank() }?.let {
        MusicLibrarySidecar(it, fallbackArtistName.orEmpty())
    }
}
