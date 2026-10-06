package com.retr0.projectretr0.installer

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class MusicArtworkProcessorTest {
    @Test fun qualityRetriesReachMinimumBeforeDimensionsShrink() {
        var current = MusicArtworkCompressionPolicy.first()
        val qualities = mutableListOf(current.quality)
        while (current.maxEdge == MusicArtworkCompressionPolicy.INITIAL_MAX_EDGE) {
            val next = MusicArtworkCompressionPolicy.next(current) ?: break
            if (next.maxEdge == current.maxEdge) qualities += next.quality
            current = next
        }

        assertEquals(listOf(90, 80, 70, 60, 50, 40), qualities)
        assertTrue(current.maxEdge < MusicArtworkCompressionPolicy.INITIAL_MAX_EDGE)
        assertEquals(80, current.quality)
    }

    @Test fun retryPolicyTerminatesAtMinimums() {
        var current = MusicArtworkCompressionPolicy.first()
        var attempts = 1
        while (true) {
            val next = MusicArtworkCompressionPolicy.next(current) ?: break
            current = next
            assertTrue(++attempts < 100)
        }

        assertEquals(MusicArtworkCompressionPolicy.MIN_MAX_EDGE, current.maxEdge)
        assertEquals(MusicArtworkCompressionPolicy.MIN_QUALITY, current.quality)
        assertNull(MusicArtworkCompressionPolicy.next(current))
    }

    @Test fun dimensionsPreserveAspectRatioWithoutUpscaling() {
        assertEquals(800 to 400, MusicArtworkCompressionPolicy.dimensions(4000, 2000, 800))
        assertEquals(400 to 800, MusicArtworkCompressionPolicy.dimensions(2000, 4000, 800))
        assertEquals(640 to 480, MusicArtworkCompressionPolicy.dimensions(640, 480, 1600))
    }
}
