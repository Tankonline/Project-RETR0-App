package com.retr0.projectretr0.data.carModels.manifest

import java.io.File
import java.security.MessageDigest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CarModelManifestTest {
    @Test fun pathValidationRejectsTraversalAndAbsolutePaths() {
        listOf("../outside", "/absolute", "C:/absolute", "a/../b", "a\\..\\b").forEach { path ->
            runCatching { CarModelManifestValidator.validateRelativePath(path) }.onSuccess { error("accepted $path") }
        }
        assertEquals("textures/a.dds", CarModelManifestValidator.validateRelativePath("textures/a.dds"))
    }

    @Test fun scannerProducesGenericBmwLikeShapeHashesAndSizes() {
        val root = createTempDir(prefix = "rr3-car-")
        repeat(27) { index ->
            val directory = root.resolve(if (index % 3 == 0) "textures" else "vehicle").apply { mkdirs() }
            val file = directory.resolve(if (index == 0) "livery_01.dds.z" else "part_$index.etc")
            file.writeBytes(ByteArray(index + 1) { index.toByte() })
        }
        root.resolve("notes.xyz").writeText("review")
        val result = CarModelFolderScanner.scan(root, target = TargetVehicle("generic_vehicle", "files/.depot/vehicles/generic_vehicle"))
        assertTrue(result.report.isValid)
        assertEquals(28, result.manifest.files.size)
        assertTrue(result.manifest.files.any { it.role == "livery" && it.variant == "livery_01" })
        assertTrue(result.manifest.files.any { it.detectedFormat == "unknown" && !it.required })
        val first = result.manifest.files.first { it.sourcePath.endsWith("part_1.etc") }
        assertEquals(2L, first.sizeBytes)
        assertEquals(sha256(root.resolve(first.sourcePath)), first.sha256)
        assertEquals(first, CarModelManifest.fromJson(result.manifest.toJson()).files.first { it.sourcePath == first.sourcePath })
        root.deleteRecursively()
    }

    @Test fun scannerNormalizesLegacyStructuredRootsAndDestinations() {
        val root = createTempDir(prefix = "rr3-legacy-")
        root.resolve("vehicles/2001_bmw_m3_gtr_street/car.m3g").apply { parentFile!!.mkdirs(); writeText("mesh") }
        root.resolve("vehicles/data/events.dat.nct").apply { parentFile!!.mkdirs(); writeText("events") }
        val result = CarModelFolderScanner.scan(root, target = TargetVehicle("ignored", "files/.depot"))
        assertTrue(result.report.isValid)
        assertEquals("car.m3g", result.manifest.files.first { it.sourcePath.startsWith("vehicles/") && !it.sourcePath.contains("data") }.destinationPath)
        assertEquals(CarModelDestinationRoot.VEHICLES_DATA, result.manifest.files.first { it.sourcePath.contains("events") }.destinationRoot)
        root.deleteRecursively()
    }

    @Test fun scannerNormalizesUpdatedDepotRootFilesAndRejectsUnknownTopLevelFile() {
        val root = createTempDir(prefix = "rr3-updated-")
        root.resolve("vehicles/2001_bmw_m3_gtr_street/car.m3g").apply { parentFile!!.mkdirs(); writeText("mesh") }
        root.resolve("gametext/text_en.txt").apply { parentFile!!.mkdirs(); writeText("text") }
        root.resolve("asset_list_base.txtCache.txt").writeText("cache")
        root.resolve("unexpected.bin").writeText("invalid")
        val result = CarModelFolderScanner.scan(root, target = TargetVehicle("ignored", "files/.depot"))
        assertTrue(result.report.isValid)
        assertEquals(3, result.manifest.files.size)
        assertTrue(result.report.warnings.any { it.contains("unexpected.bin") })
        assertTrue(result.manifest.files.all { it.destinationRoot == CarModelDestinationRoot.VEHICLE || it.destinationRoot == CarModelDestinationRoot.DEPOT_ROOT })
        assertTrue(result.manifest.files.any { it.destinationPath == "gametext/text_en.txt" })
        assertTrue(result.manifest.files.any { it.destinationPath == "asset_list_base.txtCache.txt" })
        root.deleteRecursively()
    }

    private fun sha256(file: File): String = MessageDigest.getInstance("SHA-256").digest(file.readBytes()).joinToString("") { "%02x".format(it) }
}
