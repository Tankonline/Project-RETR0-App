package com.retr0.projectretr0.data.skins

import com.retr0.projectretr0.data.skins.manifest.ManifestFile
import com.retr0.projectretr0.data.skins.manifest.ManifestScreenshot
import com.retr0.projectretr0.data.skins.manifest.SkinManifest
import com.retr0.projectretr0.data.skins.manifest.SkinManifestValidator
import java.io.File
import org.junit.Assert.assertEquals
import org.junit.Test

class SkinManifestTest {
    @Test fun roundTripsManifestJson() {
        val root = createTempDir()
        val binary = root.resolve("skin.dds.z").apply { writeBytes(byteArrayOf(1)) }
        val manifest = SkinManifest(
            skinId = "id", title = "Test", description = "Description", creator = "Retr0", vehicle = "Audi",
            carDirectory = "2014_audi_r18etronquattro", slotKey = "ext_one", textureFormat = "etc",
            gamePackage = "com.ea.games.r3_row", sourceFilename = "target.etc.dds.z", targetRelativePath = "files/.depot/vehicles/x",
            binary = ManifestFile("skin.dds.z", "application/octet-stream", binary.length(), "hash"),
            cover = null, screenshots = listOf(ManifestScreenshot(1, "screenshots/01.webp", "image/webp", 1, "hash")),
        )
        SkinManifestValidator.validate(manifest, root)
        assertEquals(manifest.skinId, SkinManifest.fromJson(manifest.toJson()).skinId)
        root.deleteRecursively()
    }

    @Test(expected = IllegalArgumentException::class)
    fun rejectsTraversalPath() {
        val root = createTempDir()
        val manifest = SkinManifest(
            skinId = "id", title = "Test", description = "", creator = "Retr0", vehicle = "Audi",
            carDirectory = "car", slotKey = "ext_one", textureFormat = "etc", gamePackage = "com.ea.games.r3_row",
            sourceFilename = "target.etc.dds.z", targetRelativePath = null,
            binary = ManifestFile("../outside.dds.z", "application/octet-stream", 1, "hash"), cover = null, screenshots = emptyList(),
        )
        SkinManifestValidator.validate(manifest, root)
    }
}
