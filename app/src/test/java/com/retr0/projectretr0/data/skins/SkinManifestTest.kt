package com.retr0.projectretr0.data.skins

import com.retr0.projectretr0.data.skins.manifest.ManifestFile
import com.retr0.projectretr0.data.skins.manifest.ManifestScreenshot
import com.retr0.projectretr0.data.skins.manifest.SkinManifest
import com.retr0.projectretr0.data.skins.manifest.SkinManifestValidator
import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

class SkinManifestTest {
    @Test fun serializesWorkerCanonicalRootAssetShape() {
        val manifest = sampleManifest()
        val json = manifest.toJson()
        assertTrue(json.has("binary"))
        assertTrue(json.has("cover"))
        assertTrue(json.has("screenshots"))
        assertFalse(json.has("files"))
        assertEquals("id", json.getString("skinId"))
    }

    @Test fun roundTripsCanonicalManifestJson() {
        val root = createTempDir()
        val binary = root.resolve("skin.dds.z").apply { writeBytes(byteArrayOf(1)) }
        val manifest = sampleManifest().copy(binary = ManifestFile("skin.dds.z", "application/octet-stream", binary.length(), "hash"))
        SkinManifestValidator.validate(manifest, root)
        assertEquals(manifest.skinId, SkinManifest.fromJson(manifest.toJson()).skinId)
        root.deleteRecursively()
    }

    @Test fun readsLegacyFilesWrappedManifest() {
        val legacy = sampleManifest().toJson().let { root ->
            JSONObject(root.toString()).apply {
                val binary = getJSONObject("binary")
                val cover = getJSONObject("cover")
                val screenshots = getJSONArray("screenshots")
                remove("binary"); remove("cover"); remove("screenshots")
                put("files", JSONObject().put("binary", binary).put("cover", cover).put("screenshots", screenshots))
            }
        }
        assertEquals("id", SkinManifest.fromJson(legacy).skinId)
    }

    private fun sampleManifest() = SkinManifest(
        skinId = "id", title = "Test", description = "Description", creator = "Retr0", vehicle = "Audi",
        carDirectory = "2014_audi_r18etronquattro", slotKey = "ext_one", textureFormat = "etc",
        gamePackage = "com.ea.games.r3_row", sourceFilename = "target.etc.dds.z", targetRelativePath = "files/.depot/vehicles/x",
        binary = ManifestFile("skin.dds.z", "application/octet-stream", 1, "${"a".repeat(64)}"),
        cover = ManifestFile("cover.webp", "image/webp", 1, "${"b".repeat(64)}"),
        screenshots = listOf(ManifestScreenshot(1, "screenshots/01.webp", "image/webp", 1, "${"c".repeat(64)}")),
    )

    @Test(expected = IllegalArgumentException::class)
    fun rejectsTraversalPath() {
        val root = createTempDir()
        SkinManifestValidator.validate(sampleManifest().copy(binary = ManifestFile("../outside.dds.z", "application/octet-stream", 1, "hash")), root)
    }
}
