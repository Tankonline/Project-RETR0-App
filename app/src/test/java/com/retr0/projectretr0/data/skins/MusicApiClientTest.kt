package com.retr0.projectretr0.data.skins

import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Test

class MusicApiClientTest {
    @Test
    fun musicCoverUrlPrefersCamelCaseField() {
        val item = JSONObject()
            .put("coverUrl", "https://example.com/camel.jpg")
            .put("cover_url", "https://example.com/snake.jpg")

        assertEquals("https://example.com/camel.jpg", MusicApiClient.musicCoverUrl(item))
    }

    @Test
    fun musicCoverUrlFallsBackToSnakeCaseField() {
        val item = JSONObject().put("cover_url", "https://example.com/snake.jpg")

        assertEquals("https://example.com/snake.jpg", MusicApiClient.musicCoverUrl(item))
    }
}
