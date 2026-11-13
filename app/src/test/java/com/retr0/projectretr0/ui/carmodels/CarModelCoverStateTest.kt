package com.retr0.projectretr0.ui.carmodels

import com.retr0.projectretr0.data.carModels.CommunityCarModel
import org.junit.Assert.assertEquals
import org.junit.Test

class CarModelCoverStateTest {
    private fun model(cover: String?, screenshots: List<String> = emptyList()) = CommunityCarModel(
        id = "m1", name = "Roadster", creator = "A", description = "description",
        vehicleDirectory = "car_01", supportedPackages = listOf("r3_row"), gameBuild = "",
        fileCount = 1, totalBytes = 1, likes = 0, downloads = 0, verifiedManifest = true,
        updatedAt = "", coverUrl = cover, screenshotUrls = screenshots, shareUrl = null,
    )

    @Test
    fun partialDetailsDoesNotEraseCatalogArtwork() {
        val previous = model("https://worker/v1/car-models/m1/cover", listOf("shot"))
        val partial = model(null)
        val merged = partial.copy(
            coverUrl = partial.coverUrl ?: previous.coverUrl,
            screenshotUrls = partial.screenshotUrls.ifEmpty { previous.screenshotUrls },
        )
        assertEquals(previous.coverUrl, merged.coverUrl)
        assertEquals(previous.screenshotUrls, merged.screenshotUrls)
    }

    @Test
    fun freshCoverReplacesStaleCover() {
        val previous = model("https://worker/old")
        val refreshed = model("https://worker/new")
        assertEquals(refreshed.coverUrl, refreshed.coverUrl ?: previous.coverUrl)
    }
}
