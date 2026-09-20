package com.example.projectretr0installer.installer

/** Stage-aware, conservative authentication fallback policy for yt-dlp failures. */
object YouTubeAuthFallback {
    enum class Stage { METADATA, DOWNLOAD }
    enum class Classification { EXPLICIT_AUTH, MEDIA_HTTP_403, NOT_AUTH_ELIGIBLE }
    enum class Attempt { AUTHENTICATED }

    private val authMarkers = listOf(
        "sign in to confirm",
        "confirm you’re not a bot",
        "confirm you're not a bot",
        "age-restricted",
        "age restricted",
        "login required",
        "authentication required",
        "verification required",
        "this video may be inappropriate",
        "use --cookies",
        "cookies are required",
    )
    private val http403Markers = listOf("http error 403", "403 forbidden", "status code 403")

    fun classify(stage: Stage, message: String?): Classification {
        val normalized = message.orEmpty().lowercase()
        if (normalized.isBlank()) return Classification.NOT_AUTH_ELIGIBLE
        if (authMarkers.any(normalized::contains)) return Classification.EXPLICIT_AUTH
        if ((stage == Stage.DOWNLOAD) && http403Markers.any(normalized::contains)) {
            return Classification.MEDIA_HTTP_403
        }
        return Classification.NOT_AUTH_ELIGIBLE
    }

    fun classify(stage: Stage, failure: Throwable): Classification {
        var current: Throwable? = failure
        while (current != null) {
            val classification = classify(stage, current.message)
            if (classification != Classification.NOT_AUTH_ELIGIBLE) return classification
            current = current.cause
        }
        return Classification.NOT_AUTH_ELIGIBLE
    }

    fun requiresAuthentication(message: String?): Boolean =
        classify(Stage.METADATA, message) == Classification.EXPLICIT_AUTH

    fun requiresAuthentication(failure: Throwable): Boolean =
        classify(Stage.METADATA, failure) == Classification.EXPLICIT_AUTH

    class RetryState(private val stage: Stage = Stage.METADATA) {
        private var authenticatedAttempted = false

        fun nextAttempt(hasCookies: Boolean, failure: Throwable?): Attempt? {
            val classification = failure?.let { classify(stage, it) } ?: Classification.NOT_AUTH_ELIGIBLE
            return nextAttempt(hasCookies, classification)
        }

        fun nextAttempt(hasCookies: Boolean, classification: Classification): Attempt? {
            if ((!hasCookies) || authenticatedAttempted || (classification == Classification.NOT_AUTH_ELIGIBLE)) return null
            authenticatedAttempted = true
            return Attempt.AUTHENTICATED
        }
    }
}
