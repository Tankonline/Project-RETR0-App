package com.retr0.projectretr0.data.carModels.manifest

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.io.path.createTempDirectory

class CarModelMultiVehicleTest {
    @Test fun detectsWrapperWithFourVehiclesAndSharedRootsWithoutUsingWrapperName() {
        val root = createTempDirectory("wrapper-").toFile()
        listOf("alpha", "beta", "gamma", "delta").forEach { vehicle ->
            root.resolve("vehicles/$vehicle/model.m3g").apply { parentFile!!.mkdirs(); writeText(vehicle) }
        }
        root.resolve("vehicles/data/events.dat.nct").apply { parentFile!!.mkdirs(); writeText("events") }
        root.resolve("data/shared.bin").apply { parentFile!!.mkdirs(); writeText("shared") }
        root.resolve("gametext/text_en.txt").apply { parentFile!!.mkdirs(); writeText("text") }
        val result = CarModelFolderScanner.scan(root)
        assertTrue(result.report.isValid)
        assertEquals(listOf("alpha", "beta", "delta", "gamma"), result.manifest.vehicleDirectories.sorted())
        assertTrue(result.manifest.files.any { it.destinationPath == "vehicles/data/events.dat.nct" && it.destinationRoot == CarModelDestinationRoot.VEHICLES_DATA })
        assertTrue(result.manifest.files.any { it.destinationPath == "data/shared.bin" && it.destinationRoot == CarModelDestinationRoot.DEPOT_DATA })
        assertTrue(result.manifest.files.none { it.sourcePath.startsWith("wrapper") })
        root.deleteRecursively()
    }
}
