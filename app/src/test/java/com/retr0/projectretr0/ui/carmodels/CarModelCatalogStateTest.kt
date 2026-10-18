package com.retr0.projectretr0.ui.carmodels

import com.retr0.projectretr0.data.carModels.CommunityCarModel
import org.junit.Assert.assertEquals
import org.junit.Test

class CarModelCatalogStateTest {
    private val model = CommunityCarModel(
        id = "m1", name = "Roadster", creator = "A", description = "",
        vehicleDirectory = "car_01", supportedPackages = listOf("r3_row"), gameBuild = "",
        fileCount = 1, totalBytes = 1, likes = 0, downloads = 0, verifiedManifest = true,
        updatedAt = "", coverUrl = "https://worker/cover", screenshotUrls = emptyList(), shareUrl = null,
    )

    @Test fun failedRefreshClearsPreviousCatalog() {
        assertEquals(emptyList<CommunityCarModel>(), refreshedCatalogItems(Result.failure(IllegalStateException())))
    }

    @Test fun successfulRefreshReplacesPreviousCatalog() {
        assertEquals(listOf(model), refreshedCatalogItems(Result.success(listOf(model))))
    }
}
