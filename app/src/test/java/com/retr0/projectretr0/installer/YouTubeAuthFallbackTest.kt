package com.retr0.projectretr0.installer

import org.junit.Assert.*
import org.junit.Test

class YouTubeAuthFallbackTest {
    @Test fun classifiesExplicitAuthFailuresAtEitherStage() {
        assertEquals(
            YouTubeAuthFallback.Classification.EXPLICIT_AUTH,
            YouTubeAuthFallback.classify(
                YouTubeAuthFallback.Stage.METADATA,
                "Sign in to confirm you're not a bot",
            ),
        )
        assertTrue(YouTubeAuthFallback.requiresAuthentication(IllegalStateException("verification required")))
        assertTrue(YouTubeAuthFallback.requiresAuthentication("This video is age-restricted"))
        assertFalse(YouTubeAuthFallback.requiresAuthentication("HTTP Error 503: Service Unavailable"))
        assertFalse(YouTubeAuthFallback.requiresAuthentication("network timeout"))
    }

    @Test fun generic403IsEligibleOnlyAtDownloadStage() {
        val failure = IllegalStateException(
            "wrapper",
            IllegalStateException("unable to download video data: HTTP Error 403: Forbidden"),
        )
        assertEquals(
            YouTubeAuthFallback.Classification.NOT_AUTH_ELIGIBLE,
            YouTubeAuthFallback.classify(YouTubeAuthFallback.Stage.METADATA, failure),
        )
        assertEquals(
            YouTubeAuthFallback.Classification.MEDIA_HTTP_403,
            YouTubeAuthFallback.classify(YouTubeAuthFallback.Stage.DOWNLOAD, failure),
        )
    }

    @Test fun download403RetryIsExactlyOnceAndRequiresCookies() {
        val state = YouTubeAuthFallback.RetryState(YouTubeAuthFallback.Stage.DOWNLOAD)
        val forbidden = IllegalStateException("HTTP Error 403: Forbidden")
        assertNull(state.nextAttempt(false, forbidden))
        assertEquals(YouTubeAuthFallback.Attempt.AUTHENTICATED, state.nextAttempt(true, forbidden))
        assertNull(state.nextAttempt(true, forbidden))
    }

    @Test fun metadataGeneric403NeverRetries() {
        val state = YouTubeAuthFallback.RetryState(YouTubeAuthFallback.Stage.METADATA)
        assertNull(state.nextAttempt(true, IllegalStateException("HTTP Error 403: Forbidden")))
    }
}

class NetscapeCookieValidatorTest {
    @Test fun acceptsValidNetscapeCookieRows() {
        val valid = "# Netscape HTTP Cookie File\n.youtube.com\tTRUE\t/\tTRUE\t1893456000\tSID\tvalue"
        assertTrue(NetscapeCookieValidator.isValid(valid))
    }

    @Test fun rejectsMalformedCookieRows() {
        assertFalse(NetscapeCookieValidator.isValid("youtube.com TRUE / TRUE not-a-date SID"))
        assertFalse(NetscapeCookieValidator.isValid(""))
    }
}
