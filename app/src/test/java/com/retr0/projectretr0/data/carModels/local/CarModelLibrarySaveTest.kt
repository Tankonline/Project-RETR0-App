package com.retr0.projectretr0.data.carModels.local

import com.retr0.projectretr0.data.carModels.manifest.CarModelFolderScanner
import com.retr0.projectretr0.data.carModels.manifest.CarModelDestinationRoot
import com.retr0.projectretr0.data.carModels.wireVehicleDestination
import com.retr0.projectretr0.data.carModels.manifest.CarModelManifest
import com.retr0.projectretr0.data.carModels.manifest.CarModelManifestValidator
import com.retr0.projectretr0.data.carModels.manifest.TargetVehicle
import com.retr0.projectretr0.data.carModels.manifest.PackageMetadata
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.File
import java.nio.file.Files

/**
 * Regression for CM-IMPORT-010: saving a picked folder to the library required a manifest.json that folder
 * imports never have (the scanner builds the manifest in memory), so every local import failed.
 */
class CarModelLibrarySaveTest {
    private lateinit var temp: File

    @Before fun setUp() { temp = Files.createTempDirectory("car-library-save").toFile() }
    @After fun tearDown() { temp.deleteRecursively() }

    private fun folder(name: String, files: Map<String, String>): File = File(temp, name).apply {
        files.forEach { (path, content) -> File(this, path).apply { parentFile?.mkdirs(); writeText(content) } }
    }

    /** Same manifest shaping as CarModelImporter.saveToLibrary for a local import. */
    private fun saved(scanned: CarModelManifest, vehicle: String) = scanned.copy(target = TargetVehicle(vehicle, vehicle))

    @Test fun singleCarFolderWithoutManifestJsonPassesTheLibraryCheck() {
        val root = folder("my_car", mapOf(
            "vehicles/2004_bmw_m3_gtr/2004_bmw_m3_gtr.points" to "points",
            "vehicles/2004_bmw_m3_gtr/livery/ext.atc.dds.z" to "livery",
            "vehicles/data/car.brakes.nct" to "brakes",
        ))
        val scan = CarModelFolderScanner.scan(root)
        assertTrue(scan.report.errors.joinToString(), scan.report.isValid)
        assertFalse(File(root, "manifest.json").exists())
        assertEquals(emptyList<Any>(), missingLibraryFiles(root, scan.manifest))
        CarModelManifestValidator.validate(saved(scan.manifest, "2004_bmw_m3_gtr"))
    }

    @Test fun multiCarWrapperFolderPassesTheLibraryCheckAndValidates() {
        val root = folder("[S2RR3] v0.4 early access", mapOf(
            "vehicles/1990_bmw_m3_e36/1990_bmw_m3_e36.points" to "a-points",
            "vehicles/1990_bmw_m3_e36/livery/ext.atc.dds.z" to "a-livery",
            "vehicles/1998_toyota_supra_mk4/1998_toyota_supra_mk4.points" to "b-points",
            "vehicles/data/car.brakes.nct" to "brakes",
            "gametext.txt" to "text",
        ))
        val scan = CarModelFolderScanner.scan(root)
        assertTrue(scan.report.errors.joinToString(), scan.report.isValid)
        assertTrue(scan.manifest.vehicleDirectories.containsAll(listOf("1990_bmw_m3_e36", "1998_toyota_supra_mk4")))
        assertEquals(emptyList<Any>(), missingLibraryFiles(root, scan.manifest))
        val manifest = saved(scan.manifest, scan.manifest.vehicleDirectories.first())
        CarModelManifestValidator.validate(manifest)
        System.getenv("RETR0_DUMP_MANIFEST")?.let { File(it).writeText(manifest.toJson().toString(2)) }
    }

    @Test fun aMissingOrTruncatedFileIsReported() {
        val root = folder("pack", mapOf("vehicles/car_a/a.points" to "points", "vehicles/car_a/b.points" to "more"))
        val scan = CarModelFolderScanner.scan(root)
        File(root, "vehicles/car_a/a.points").delete()
        File(root, "vehicles/car_a/b.points").writeText("m")
        assertEquals(2, missingLibraryFiles(root, scan.manifest).size)
    }

    @Test fun uploadKeepsEachPackageCarAsItsOwnFolderAndDownloadMapsItBack() {
        val manifest = CarModelManifest(vehicleDirectories = listOf("car_a", "car_b"), metadata = PackageMetadata("p"), supportedGamePackages = listOf("pkg"), target = TargetVehicle("car_a", "car_a"), files = emptyList())
        assertEquals("car_a/livery/x.dds", wireVehicleDestination(manifest, "vehicles/car_a/livery/x.dds"))
        assertEquals("car_b/b.points", wireVehicleDestination(manifest, "vehicles/car_b/b.points"))
        // Round trip: the Hub copy of car_b's file must install into car_b, not car_a/car_b.
        val paths = CarModelPaths("/b", mapOf("pkg" to "/game"))
        val entry = com.retr0.projectretr0.data.carModels.manifest.CarModelFile("vehicles/car_b/b.points", wireVehicleDestination(manifest, "vehicles/car_b/b.points"), "points", "points", sizeBytes = 1, sha256 = "0".repeat(64), destinationRoot = CarModelDestinationRoot.VEHICLE)
        assertEquals("/game/files/.depot/vehicles/car_b/b.points", paths.destination("pkg", "car_a", entry, manifest.vehicleDirectories))
    }

    @Test fun singleCarWireDestinationIsUnchanged() {
        val manifest = CarModelManifest(metadata = PackageMetadata("p"), supportedGamePackages = listOf("pkg"), target = TargetVehicle("car_a", "car_a"), files = emptyList())
        assertEquals("car_a/body.m3g", wireVehicleDestination(manifest, "vehicles/car_a/body.m3g"))
        assertEquals("car_a/body.m3g", wireVehicleDestination(manifest, "body.m3g"))
    }
}
