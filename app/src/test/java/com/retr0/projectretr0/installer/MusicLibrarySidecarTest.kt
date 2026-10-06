package com.retr0.projectretr0.installer

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class MusicLibrarySidecarTest {
    @Test fun sidecarRoundTripsDeterministically() {
        val metadata = MusicLibrarySidecar("Manual Title", "Manual Artist")

        assertEquals(metadata, MusicLibrarySidecarCodec.parse(MusicLibrarySidecarCodec.serialize(metadata)))
    }

    @Test fun malformedOrIncompleteSidecarsAreIgnored() {
        assertNull(MusicLibrarySidecarCodec.parse("not json".toByteArray()))
        assertNull(MusicLibrarySidecarCodec.parse("{\"trackName\":\"Title\"}".toByteArray()))
        assertNull(MusicLibrarySidecarCodec.parse(null))
    }

    @Test fun baseNamesCannotTraverseDirectories() {
        assertEquals("evil_track", MusicLibrarySidecarCodec.sanitizeBaseName("../../evil/track.mp3"))
        assertEquals("track", MusicLibrarySidecarCodec.sanitizeBaseName(".."))
        assertEquals("My_Song", MusicLibrarySidecarCodec.sanitizeBaseName(" My Song.mp3 "))
    }

    @Test fun stagingNamesPreserveAllowedSemanticExtensions() {
        assertEquals("Song.123.tmp.mp3", MusicLibrarySidecarCodec.stagingFileName("Song.mp3", "123"))
        assertEquals(
            "Song.123.tmp.metadata.json",
            MusicLibrarySidecarCodec.stagingFileName("Song.metadata.json", "123"),
        )
        assertEquals(
            "Song.123.tmp.artwork.jpg",
            MusicLibrarySidecarCodec.stagingFileName("Song.artwork.jpg", "123"),
        )
    }

    @Test fun stagingTokensAreSanitizedDeterministically() {
        assertEquals(
            "Song.123abc.tmp.mp3",
            MusicLibrarySidecarCodec.stagingFileName("Song.mp3", "../12-3 abc"),
        )
        assertEquals("Song.stage.tmp.mp3", MusicLibrarySidecarCodec.stagingFileName("Song.mp3", "../"))
    }

    @Test fun validSidecarWinsAndFallbackOnlyFillsLegacyTracks() {
        val local = MusicLibrarySidecar("Local Title", "Local Artist")
        assertEquals(local, MusicLibrarySidecarCodec.resolve(local, "Remote Title", "Remote Artist"))
        assertEquals(
            MusicLibrarySidecar("Remote Title", "Remote Artist"),
            MusicLibrarySidecarCodec.resolve(null, "Remote Title", "Remote Artist"),
        )
        assertNull(MusicLibrarySidecarCodec.resolve(null, null, null))
    }
}
