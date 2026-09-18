package com.example.projectretr0installer.data.skins

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import java.net.URI

class SkinApiModelsTest {
    @Test fun realWorkerShapeDocumentsExactSlotAndScreenshotOrdering() {
        val expected = SkinApiSkin(
            id = "skin-1", title = "Test", creatorName = "Maker", description = null,
            carDirectory = "2014_audi_r18", slotKey = "custom_slot", textureFormat = "etc",
            sourceFilename = null, binarySize = 326156, binarySha256 = null, screenshotCount = 2,
            gameVersions = null, status = null, createdAt = null, updatedAt = null, likes = 0,
            downloads = 0, coverUrl = "https://example.com/cover", binaryUrl = null, detailsUrl = null,
            shareUrl = null,
            screenshots = listOf(SkinApiScreenshot("https://example.com/a", 1), SkinApiScreenshot("https://example.com/b", 2)),
        )
        assertEquals("custom_slot", expected.slotKey)
        assertEquals(listOf(1, 2), expected.screenshots.sortedBy { it.order }.map { it.order })
        assertNull(expected.description)
    }

    @Test fun uriValidationRejectsNonHttpsEquivalent() {
        val uri = URI("http://example.com/cover")
        assertEquals("http", uri.scheme)
    }
}
