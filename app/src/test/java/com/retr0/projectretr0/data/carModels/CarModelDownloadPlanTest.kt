package com.retr0.projectretr0.data.carModels

import org.json.JSONArray
import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test

class CarModelDownloadPlanTest {
    private val id = "8c89ed68-1327-4e1c-a7b9-aedc422b0a22"
    private val sha = "a".repeat(64)
    private val model = CommunityCarModel(id, "M3", "c", "", "2004_bmw_m3_gtr", emptyList(), "", 1, 10, 0, 0, true, "",
        coverUrl = "https://hub.test/v1/car-models/$id/cover", screenshotUrls = listOf("https://hub.test/s/1", "https://hub.test/s/2"))

    private fun json(vararg files: JSONObject) = JSONObject().put("manifestUrl", "/v1/car-models/$id/manifest").put("files", JSONArray(files.toList()))
    private fun file(path: String, size: Long = 10, hash: String = sha) = JSONObject().put("path", path).put("size", size).put("sha256", hash).put("url", "/f/$path")

    @Test fun parsesWorkerMediaFields() {
        val body = json(file("car/a.z"))
            .put("manifestSha256", "B".repeat(64))
            .put("cover", JSONObject().put("url", "/v1/car-models/$id/cover").put("sha256", "C".repeat(64)))
            .put("screenshots", JSONArray().put(JSONObject().put("position", 2).put("url", "/s/2")).put(JSONObject().put("position", 1).put("url", "/s/1").put("sha256", "d".repeat(64))))
        val plan = CarModelDownloadPlan.parse(body, model)
        assertEquals("b".repeat(64), plan.manifestSha256)
        assertEquals("/v1/car-models/$id/cover", plan.cover!!.url)
        assertEquals("c".repeat(64), plan.cover!!.sha256)
        assertEquals(listOf(1, 2), plan.screenshots.map { it.position })
        assertEquals("d".repeat(64), plan.screenshots.first().sha256)
        assertNull(plan.screenshots.last().sha256)
        assertEquals(10L, plan.modelBytes)
    }

    @Test fun legacyCoverUrlFieldIsAccepted() {
        val plan = CarModelDownloadPlan.parse(json(file("car/a.z")).put("coverUrl", "/legacy/cover"), model)
        assertEquals("/legacy/cover", plan.cover!!.url)
    }

    @Test fun fallsBackToCatalogMediaWhenWorkerOmitsIt() {
        val plan = CarModelDownloadPlan.parse(json(file("car/a.z")), model)
        assertEquals(model.coverUrl, plan.cover!!.url)
        assertNull(plan.cover!!.sha256)
        assertEquals(model.screenshotUrls, plan.screenshots.map { it.url })
    }

    @Test fun noMediaAnywhereIsAllowed() {
        val plan = CarModelDownloadPlan.parse(json(file("car/a.z")), model.copy(coverUrl = null, screenshotUrls = emptyList()))
        assertNull(plan.cover)
        assertTrue(plan.screenshots.isEmpty())
    }

    @Test fun unsafePathsAreRejected() {
        listOf("../escape.z", "/abs.z", "car/../../x.z").forEach { bad ->
            assertThrows(CarModelException::class.java) { CarModelDownloadPlan.parse(json(file(bad)), model) }
        }
    }

    @Test fun invalidMetadataIsRejected() {
        assertThrows(IllegalArgumentException::class.java) { CarModelDownloadPlan.parse(json(file("car/a.z", size = 0)), model) }
        assertThrows(IllegalArgumentException::class.java) { CarModelDownloadPlan.parse(json(file("car/a.z", hash = "nothex")), model) }
        assertThrows(IllegalArgumentException::class.java) { CarModelDownloadPlan.parse(json(file("car/a.z"), file("CAR/A.z")), model) }
        assertThrows(IllegalArgumentException::class.java) { CarModelDownloadPlan.parse(json(), model) }
        assertThrows(IllegalArgumentException::class.java) { CarModelDownloadPlan.parse(JSONObject().put("files", JSONArray()), model) }
    }

    @Test fun downloadUrlsAreRestrictedToTheWorkerHost() {
        val client = CarModelApiClient(baseUrl = "https://api.retr0.test")
        assertEquals("https://api.retr0.test/v1/car-models/$id/files/a%20b.z", client.resolveDownloadUrl("/v1/car-models/$id/files/a%20b.z").toString())
        assertEquals("api.retr0.test", client.resolveDownloadUrl("https://api.retr0.test/v1/x").host)
        assertThrows(IllegalArgumentException::class.java) { client.resolveDownloadUrl("https://evil.test/v1/x") }
        assertThrows(IllegalArgumentException::class.java) { client.resolveDownloadUrl("http://api.retr0.test/v1/x") }
    }

    @Test fun retryableHttpStatuses() {
        assertTrue(CarModelHttpException(503, "").retryable)
        assertTrue(CarModelHttpException(429, "").retryable)
        assertTrue(!CarModelHttpException(404, "").retryable)
    }
}
