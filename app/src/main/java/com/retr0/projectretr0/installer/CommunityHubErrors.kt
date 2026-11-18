package com.retr0.projectretr0.installer

import com.retr0.projectretr0.data.skins.SaveApiClient

/**
 * Pure helpers for Community Hub save downloads and AI troubleshooting: local file naming,
 * user-facing fix hints, and the bounded registration-retry rule. Kept free of Android types so
 * they are covered by JVM unit tests.
 */
internal object CommunityHubErrors {
    private const val RESET_IDENTITY_HINT =
        "FIX: Tap \"Reset identity\" (next to your Installation ID in the Community Hub), then try again."
    private const val CLOCK_HINT =
        "FIX: Your device date/time is wrong. Turn on automatic date & time, then try again."

    /** Codes the Worker returns when this installation's identity or key is not usable. */
    private val IDENTITY_CODES = setOf(
        "authentication_required", "unknown_installation", "invalid_installation",
        "invalid_signature", "invalid_nonce", "installation_exists",
    )

    /**
     * The AI request registers and retries only once: the retry passes `allowRetry = false`, so a
     * server that keeps answering `unknown_installation` (for example a revoked installation)
     * cannot cause unbounded recursion.
     */
    fun shouldRegisterAndRetryAi(httpStatus: Int, serverCode: String?, allowRetry: Boolean): Boolean =
        allowRetry && httpStatus == 401 && serverCode == "unknown_installation"

    fun saveDownloadHint(statusCode: Int?, errorCode: String?): String = when (errorCode) {
        "stale_request" -> CLOCK_HINT
        in IDENTITY_CODES -> RESET_IDENTITY_HINT
        "replayed_request" -> "FIX: Tap Get again."
        "save_not_found", "save_asset_not_found" -> "FIX: This file may have been deleted from the Hub."
        SaveApiClient.DOWNLOAD_NETWORK_ERROR -> "FIX: Check your internet connection, then try again."
        SaveApiClient.DOWNLOAD_INCOMPLETE -> "FIX: The download did not finish. Check your connection and try again."
        SaveApiClient.DOWNLOAD_TOO_LARGE -> "FIX: This save is larger than the 25 MiB Community Hub limit and was not installed."
        SaveApiClient.LOCAL_STORAGE_ERROR -> "FIX: Free up storage space, then try again."
        "rate_limited" -> "FIX: Too many requests. Wait a minute, then try again."
        else -> when (statusCode) {
            null -> "FIX: Check your internet connection, then try again."
            401 -> RESET_IDENTITY_HINT
            404 -> "FIX: This file may have been deleted from the Hub."
            429 -> "FIX: Too many requests. Wait a minute, then try again."
            in 500..599 -> "FIX: The Community Hub is having problems. Try again later."
            else -> "FIX: Server error or internet issue."
        }
    }

    /** User-facing text for a failed AI request. [failureKind] is the installer's AI error enum name. */
    fun aiFailureMessage(failureKind: String, httpStatus: Int?, serverCode: String?): String = when (failureKind) {
        "TIMEOUT" -> "The AI took too long to answer. Please try again."
        "NETWORK" -> "Could not connect to AI. Check your internet connection and try again."
        "INVALID_RESPONSE", "EMPTY_RESPONSE" -> "The AI returned an unreadable answer. Please try again."
        "INVALID_REQUEST" -> "There is no question to send to the AI."
        "HTTP_ERROR" -> when {
            serverCode == "stale_request" -> "AI request rejected: device clock is wrong. ${CLOCK_HINT.removePrefix("FIX: ")}"
            serverCode in IDENTITY_CODES -> "AI request rejected: Community identity is not valid. ${RESET_IDENTITY_HINT.removePrefix("FIX: ")}"
            serverCode == "replayed_request" -> "AI request was rejected as a duplicate. Please try again."
            serverCode == "service_unavailable" || httpStatus == 503 -> "The AI service is temporarily unavailable. Try again later."
            httpStatus == 429 -> "The AI service is busy. Wait a minute, then try again."
            httpStatus == 401 -> "AI request rejected (HTTP 401). ${RESET_IDENTITY_HINT.removePrefix("FIX: ")}"
            else -> "The AI service returned an error (HTTP ${httpStatus ?: "unknown"}). Try again later."
        }
        else -> "Could not connect to AI."
    }
}

/** Safe local file names for downloaded Community saves. Server-supplied names are untrusted. */
internal object CommunitySaveFileNames {
    private const val MAX_BASE_LENGTH = 80
    private const val MAX_SUFFIX_ATTEMPTS = 99
    private val UNSAFE_CHARS = Regex("[^\\p{L}\\p{N}._ ()-]")
    private val DOT_RUNS = Regex("\\.{2,}")

    /**
     * Turns an uploader-controlled name into a single path segment: no separators, no `..`, no
     * leading dots (hidden files), no control characters. Falls back to the save ID when nothing
     * usable remains.
     */
    fun baseName(customName: String, fallbackId: String): String {
        val cleaned = customName
            .replace(UNSAFE_CHARS, "_")
            .replace(DOT_RUNS, "_")
            .trim(' ', '.')
            .let { if (it.endsWith(".dat", ignoreCase = true)) it.dropLast(4).trimEnd(' ', '.') else it }
            .take(MAX_BASE_LENGTH)
            .trimEnd(' ', '.')
        if (cleaned.isNotBlank() && cleaned.any { it.isLetterOrDigit() }) return cleaned
        val safeId = fallbackId.filter { it.isLetterOrDigit() || it == '-' }.take(36)
        return "community_save_${safeId.ifBlank { "download" }}"
    }

    /** First `<base>.dat`, `<base> (2).dat`, ... that does not exist yet, so existing backups are never overwritten. */
    fun uniqueFileName(base: String, exists: (String) -> Boolean): String {
        val first = "$base.dat"
        if (!exists(first)) return first
        for (index in 2..MAX_SUFFIX_ATTEMPTS) {
            val candidate = "$base ($index).dat"
            if (!exists(candidate)) return candidate
        }
        return "$base (${System.currentTimeMillis()}).dat"
    }
}
