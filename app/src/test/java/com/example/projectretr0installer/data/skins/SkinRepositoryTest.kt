package com.example.projectretr0installer.data.skins

import com.example.projectretr0installer.ui.skins.Compatibility
import org.junit.Assert.assertEquals
import org.junit.Test

class SkinRepositoryTest {
    private val repository = SkinRepository(object : SkinDataSource {
        override suspend fun list(query: String, sort: String, limit: Int, offset: Int) = SkinListResponse(emptyList(), limit, offset)
        override suspend fun details(id: String) = error("unused")
    })

    @Test fun mapsExactTargetAndHumanReadableValues() {
        val mapped = repository.map(api(slotKey = "rear_custom_slot"))
        assertEquals("rear_custom_slot", mapped.slotKey)
        assertEquals("Rear Custom Slot", mapped.slotLabel)
        assertEquals("Audi R18 Etronquattro", mapped.vehicle)
        assertEquals(Compatibility.GAME_NOT_DETECTED, mapped.compatibility)
        assertEquals(1.0, mapped.sizeMb, 0.0001)
        assertEquals("2026-09-06", mapped.uploadedLabel)
    }

    private fun api(slotKey: String) = SkinApiSkin(
        id = "1", title = "Title", creatorName = "Creator", description = null,
        carDirectory = "2014_audi_r18_etronquattro", slotKey = slotKey, textureFormat = "etc",
        sourceFilename = null, binarySize = 1024L * 1024L, binarySha256 = null,
        screenshotCount = 0, gameVersions = null, status = "approved", createdAt = "2026-09-06 10:00:00",
        updatedAt = null, likes = 0, downloads = 0, coverUrl = null, binaryUrl = null, detailsUrl = null,
    )
}
