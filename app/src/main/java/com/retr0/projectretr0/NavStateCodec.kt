package com.retr0.projectretr0

/**
 * Saves which screen is open (and where each screen's Back goes) across Android closing the app in the
 * background or rebuilding the Activity (dark mode, language, font size). Stored as plain strings in the
 * saved-instance Bundle; unknown or damaged values are dropped, so a bad restore falls back to the game picker.
 */
internal object NavStateCodec {
    const val KEY_MODE = "retr0_nav_mode"
    const val KEY_RETURN = "retr0_nav_return"

    /** "TARGET=FROM,TARGET=FROM" */
    fun encodeReturns(returns: Map<MainActivity.AppMode, MainActivity.AppMode>): String =
        returns.entries.joinToString(",") { "${it.key.name}=${it.value.name}" }

    fun decodeMode(value: String?): MainActivity.AppMode? = value?.let { v -> MainActivity.AppMode.entries.firstOrNull { it.name == v } }

    fun decodeReturns(value: String?): Map<MainActivity.AppMode, MainActivity.AppMode> {
        if (value.isNullOrBlank()) return emptyMap()
        return value.split(",").mapNotNull { pair ->
            val parts = pair.split("=")
            if (parts.size != 2) return@mapNotNull null
            val target = decodeMode(parts[0]) ?: return@mapNotNull null
            val from = decodeMode(parts[1]) ?: return@mapNotNull null
            target to from
        }.toMap()
    }
}
