package com.retr0.projectretr0.installer

import java.net.URI
import org.junit.Assert.assertEquals
import org.junit.Test

class LibraryArtworkSourcePolicyTest {
    @Test fun fileSourcePrecedesValidHttpsFallback() {
        assertEquals(
            listOf(LibraryArtworkSource.FILE, LibraryArtworkSource.REMOTE_HTTPS),
            LibraryArtworkSourcePolicy.candidates(
                URI("file:///data/user/0/app/cache/cover.jpg"),
                "https://example.com/cover.jpg",
            ),
        )
    }

    @Test fun contentSourcePrecedesValidHttpsFallback() {
        assertEquals(
            listOf(LibraryArtworkSource.CONTENT, LibraryArtworkSource.REMOTE_HTTPS),
            LibraryArtworkSourcePolicy.candidates(
                URI("content://media/external/images/1"),
                "https://example.com/cover.jpg",
            ),
        )
    }

    @Test fun unsupportedLocalSchemeIsRejectedWhileHttpsFallbackRemains() {
        assertEquals(
            listOf(LibraryArtworkSource.REMOTE_HTTPS),
            LibraryArtworkSourcePolicy.candidates(
                URI("ftp://example.com/cover.jpg"),
                "https://example.com/fallback.jpg",
            ),
        )
    }

    @Test fun nonHttpsRemoteFallbackIsRejected() {
        assertEquals(
            listOf(LibraryArtworkSource.FILE),
            LibraryArtworkSourcePolicy.candidates(
                URI("file:///data/user/0/app/cache/cover.jpg"),
                "http://example.com/cover.jpg",
            ),
        )
    }
}
