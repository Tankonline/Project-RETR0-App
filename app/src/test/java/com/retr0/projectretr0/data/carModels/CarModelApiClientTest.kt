package com.retr0.projectretr0.data.carModels

import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class CarModelApiClientTest {
    @Test fun relativePathsRejectTraversalAndPreserveNestedEncoding() {
        assertEquals("textures/car body.dds", CarModelApiClient.safeRelativePath("textures/car body.dds"))
        assertThrows(IllegalArgumentException::class.java) { CarModelApiClient.safeRelativePath("../outside.dds") }
        assertThrows(IllegalArgumentException::class.java) { CarModelApiClient.safeRelativePath("/storage/emulated/0/file.dds") }
        assertThrows(IllegalArgumentException::class.java) { CarModelApiClient.safeRelativePath("C:\\device\\file.dds") }
    }

    @Test fun manifestSerializationIsUtf8AndRejectsAbsoluteDevicePaths() {
        val json = JSONObject().put("metadata", JSONObject().put("name", "BMW  M3"))
        assertEquals("{\"metadata\":{\"name\":\"BMW  M3\"}}", json.toString())
        assertThrows(IllegalArgumentException::class.java) {
            // Validation is exercised through the same public upload preflight without a network call.
            CarModelApiClient.validateManifestForTest(JSONObject().put("path", "/storage/emulated/0/car.dds").toString())
        }
    }

    @Test fun baseUrlMustBeNonBlankHttps() {
        assertThrows(IllegalArgumentException::class.java) { CarModelApiClient.requireHttpsBase("") }
        assertThrows(IllegalArgumentException::class.java) { CarModelApiClient.requireHttpsBase("http://example.test") }
        assertEquals("example.test", CarModelApiClient.requireHttpsBase("https://example.test").host)
    }

    @Test fun workerBaseUrlIsHttpsAndUsesExpectedHost() {
        val url = CarModelApiClient.requireHttpsBase("https://save-files.armaandeep08-ap.workers.dev")
        assertEquals("https", url.scheme)
        assertEquals("save-files.armaandeep08-ap.workers.dev", url.host)
    }

    @Test fun publicCatalogAndDetailsMapWorkerFields() {
        val client = CarModelApiClient()
        val catalog = client.parseCatalogForTest("""{"items":[{"id":"m1","title":"Roadster","creatorName":"A","targetVehicleDirectory":"car_01","packageId":"r3_row","fileCount":2,"totalSize":42,"likes":3,"coverUrl":"https://cdn/cover"}]}""")
        assertEquals(1, catalog.size)
        assertEquals("Roadster", catalog.single().name)
        assertEquals("r3_row", catalog.single().supportedPackages.single())
        val detail = client.parseModelForTest("""{"id":"m1","title":"Roadster","creatorName":"A","description":"desc","targetVehicleDirectory":"car_01","packageId":"r3_row","supportedVersions":"13","status":"published","fileCount":2,"totalSize":42,"likes":3,"manifestSha256":"0123456789012345678901234567890123456789012345678901234567890123","screenshotPositions":[1]}""")
        assertEquals("desc", detail.description)
        assertEquals(true, detail.verifiedManifest)
        assertEquals(1, detail.screenshotUrls.size)
    }

    @Test fun emptyPublicCatalogMapsToEmptyList() {
        assertEquals(0, CarModelApiClient().parseCatalogForTest("{\"items\":[]}").size)
        assertEquals(0, CarModelApiClient().parseCatalogForTest("{}").size)
    }

    @Test fun catalogDropsRowsThatCannotBeDownloaded() {
        val body = """{"items":[
            {"id":"missing-assets","title":"Ghost","creatorName":"A","fileCount":0,"totalSize":0},
            {"id":"valid","title":"Roadster","creatorName":"A","fileCount":1,"totalSize":42,"coverUrl":"https://worker/v1/car-models/valid/cover"}
        ]}"""
        val items = CarModelApiClient().parseCatalogForTest(body)
        assertEquals(listOf("valid"), items.map { it.id })
    }

    @Test fun limitsAreExplicitAndStable() {
        assertEquals(100L * 1024 * 1024, CarModelApiClient.MAX_FILE_BYTES)
        assertEquals(10L * 1024 * 1024, CarModelApiClient.MAX_MANIFEST_BYTES)
        assertEquals(256L * 1024, CarModelApiClient.MAX_METADATA_BYTES)
    }
}
