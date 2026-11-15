package com.retr0.projectretr0.data.carModels.local

import com.retr0.projectretr0.data.carModels.CarModelErrorCode
import com.retr0.projectretr0.data.carModels.CarModelException
import com.retr0.projectretr0.data.carModels.manifest.CarModelDestinationRoot
import com.retr0.projectretr0.data.carModels.manifest.CarModelDestinationRoot.DEPOT_DATA
import com.retr0.projectretr0.data.carModels.manifest.CarModelDestinationRoot.DEPOT_ROOT
import com.retr0.projectretr0.data.carModels.manifest.CarModelDestinationRoot.VEHICLE
import com.retr0.projectretr0.data.carModels.manifest.CarModelDestinationRoot.VEHICLES_DATA
import com.retr0.projectretr0.data.carModels.manifest.CarModelFile
import com.retr0.projectretr0.data.carModels.manifest.CarModelManifest
import com.retr0.projectretr0.data.carModels.manifest.InstallationPolicy
import com.retr0.projectretr0.data.carModels.manifest.PackageMetadata
import com.retr0.projectretr0.data.carModels.manifest.TargetVehicle
import com.retr0.projectretr0.ui.carmodels.CarModelsViewModel
import com.retr0.projectretr0.ui.carmodels.applyPreview
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.File
import java.nio.file.Files

/**
 * ADDON apply for a Community Hub car whose vehicle folder does not exist in the base game, shaped like
 * the real 2004_bmw_m3_gtr package: vehicle files plus shared DEPOT_ROOT / DEPOT_DATA / VEHICLES_DATA files.
 */
class CarModelAddonApplyTest {
    private val pkg = "com.ea.games.r3_row"
    private val car = "2004_bmw_m3_gtr"
    private lateinit var temp: File
    private lateinit var store: CarModelStore
    private lateinit var paths: CarModelPaths
    private lateinit var engine: CarModelEngine
    private lateinit var depot: File
    private lateinit var ops: DiskOps

    /** Package content: (source path, destination path, root) to bytes. */
    private val packageFiles = listOf(
        Triple("vehicles/$car/$car.points", "$car/$car.points", VEHICLE) to "points",
        Triple("vehicles/$car/${car}_a.m3g", "$car/${car}_a.m3g", VEHICLE) to "mesh-a",
        Triple("gametext.txt", "gametext.txt", DEPOT_ROOT) to "modded-gametext",
        Triple("data/events.dat.nct", "data/events.dat.nct", DEPOT_DATA) to "modded-events",
        Triple("vehicles/data/car.brakes.nct", "vehicles/data/car.brakes.nct", VEHICLES_DATA) to "modded-brakes",
        Triple("vehicles/data/611_$car.bin.nct", "vehicles/data/611_$car.bin.nct", VEHICLES_DATA) to "new-bin",
    )

    @Before fun setUp() {
        temp = Files.createTempDirectory("car-addon").toFile()
        val gameRoot = File(temp, "game")
        depot = File(gameRoot, "files/.depot")
        // Base game: other cars and the shared files, but no 2004_bmw_m3_gtr folder.
        File(depot, "vehicles/2001_bmw_m3_gtr_street").mkdirs()
        File(depot, "vehicles/2001_bmw_m3_gtr_street/body.m3g").writeText("street")
        File(depot, "gametext.txt").writeText("stock-gametext")
        File(depot, "data").mkdirs(); File(depot, "data/events.dat.nct").writeText("stock-events")
        File(depot, "vehicles/data").mkdirs(); File(depot, "vehicles/data/car.brakes.nct").writeText("stock-brakes")
        store = CarModelStore(File(temp, "store"))
        paths = CarModelPaths(File(temp, "backups").path.replace('\\', '/'), mapOf(pkg to gameRoot.path.replace('\\', '/')))
        engine = CarModelEngine(store, paths) { 1_000L }
        ops = DiskOps()
    }

    @After fun tearDown() { temp.deleteRecursively() }

    private fun addModel(id: String = "hub", target: String = car, files: List<Pair<Triple<String, String, CarModelDestinationRoot>, String>> = packageFiles, mode: String = "ADDON"): LocalCarModel {
        val root = File(store.libraryDir(id), "files")
        val entries = files.map { (spec, content) ->
            val (source, destination, destRoot) = spec
            File(root, source).apply { parentFile?.mkdirs(); writeText(content) }
            CarModelFile(source, destination, "asset", "nct", sizeBytes = content.length.toLong(), sha256 = CarModelEngine.sha256(File(root, source)), destinationRoot = destRoot)
        }
        val manifest = CarModelManifest(
            metadata = PackageMetadata("2004 BMW M3 GTR"), packageId = pkg, supportedGamePackages = listOf(pkg),
            target = TargetVehicle(target, "files/.depot"), installationPolicy = InstallationPolicy.FULL_DIRECTORY_REPLACEMENT,
            installMode = mode, files = entries,
        )
        val model = LocalCarModel(id, "Model $id", "hub", "", "", "", listOf(pkg), manifest, root.absolutePath, null, emptyList(), emptyList(), 0L, remoteId = "remote-$id")
        store.update { it.copy(models = it.models.filterNot { m -> m.id == id } + model) }
        return model
    }

    private fun vehicleDir() = File(depot, "vehicles/$car")
    private suspend fun install(id: String = "hub", target: String = car, using: CarModelFileOps = ops) = engine.install(using, InstallMode.ADDON, id, pkg, target)
    private fun failed(outcome: ApplyOutcome) = outcome as ApplyOutcome.Failed

    // ---- install -------------------------------------------------------------------------------

    @Test fun addonForAVehicleAbsentFromTheBaseGameInstallsWithoutAReplacementBackup() = runBlocking {
        addModel()
        assertFalse(vehicleDir().exists())
        val outcome = install()
        assertEquals(ApplyOutcome.Success(packageFiles.size, 1_000L), outcome)
        // Destination roots are preserved, not flattened.
        assertEquals("points", File(vehicleDir(), "$car.points").readText())
        assertEquals("mesh-a", File(vehicleDir(), "${car}_a.m3g").readText())
        assertEquals("modded-gametext", File(depot, "gametext.txt").readText())
        assertEquals("modded-events", File(depot, "data/events.dat.nct").readText())
        assertEquals("modded-brakes", File(depot, "vehicles/data/car.brakes.nct").readText())
        assertEquals("new-bin", File(depot, "vehicles/data/611_$car.bin.nct").readText())
        // No whole-vehicle REPLACE backup was needed or created.
        assertNull(store.current.backup(pkg, car))
        val record = store.current.activeInstall(pkg, car)!!
        assertTrue(record.isAddon && record.complete && record.createdDirectory)
        // Every pre-existing shared file was backed up (and verified) before being overwritten.
        assertEquals(setOf("DEPOT_ROOT", "DEPOT_DATA", "VEHICLES_DATA"), record.overwritten.map { it.root }.toSet())
        assertEquals(3, record.overwritten.size)
        assertTrue(File(paths.addonBackupDir(pkg, car), CarModelEngine.BACKUP_MANIFEST).isFile)
    }

    @Test fun removeRestoresSharedOriginalsAndDeletesTheCreatedVehicleFolder() = runBlocking {
        addModel(); install()
        engine.removeAddon(ops, pkg, car).getOrThrow()
        assertEquals("stock-gametext", File(depot, "gametext.txt").readText())
        assertEquals("stock-events", File(depot, "data/events.dat.nct").readText())
        assertEquals("stock-brakes", File(depot, "vehicles/data/car.brakes.nct").readText())
        assertFalse(File(depot, "vehicles/data/611_$car.bin.nct").exists())
        assertFalse(vehicleDir().exists())
        assertNull(store.current.activeInstall(pkg, car))
        assertEquals("street", File(depot, "vehicles/2001_bmw_m3_gtr_street/body.m3g").readText())
    }

    @Test fun addonOnAnExistingFolderKeepsTheFolderOnRemove() = runBlocking {
        vehicleDir().mkdirs(); File(vehicleDir(), "unrelated.txt").writeText("keep")
        addModel(); install()
        assertFalse(store.current.activeInstall(pkg, car)!!.createdDirectory)
        engine.removeAddon(ops, pkg, car).getOrThrow()
        assertEquals("keep", File(vehicleDir(), "unrelated.txt").readText())
    }

    // ---- ownership ------------------------------------------------------------------------------

    @Test fun targetOwnedByAnotherModelIsRefusedWithoutTouchingFiles() = runBlocking {
        addModel("first"); install("first")
        addModel("second")
        val outcome = failed(install("second"))
        assertEquals(CarModelErrorCode.ADDON_TARGET_OCCUPIED.code, outcome.code)
        assertFalse(outcome.gameFilesTouched)
        assertEquals("first", store.current.activeInstall(pkg, car)!!.modelId)
    }

    @Test fun addonOverAReplaceInstallIsRefusedSoItsRecordIsNotLost() = runBlocking {
        addModel()
        store.update { it.copy(installed = it.installed + InstalledCarModel("other", "Other", pkg, car, 1L, true, 1, 1L, InstallMode.REPLACE)) }
        assertEquals(CarModelErrorCode.ADDON_TARGET_OCCUPIED.code, failed(install()).code)
        assertEquals(InstallMode.REPLACE, store.current.activeInstall(pkg, car)!!.mode)
    }

    @Test fun sharedFileConflictWithAnotherInstalledAddonIsRefused() = runBlocking {
        addModel("first"); install("first")
        val otherCar = "2010_new_car"
        addModel("second", target = otherCar, files = listOf(
            Triple("vehicles/$otherCar/x.points", "$otherCar/x.points", VEHICLE) to "x",
            Triple("gametext.txt", "gametext.txt", DEPOT_ROOT) to "second-gametext",
        ))
        val outcome = failed(install("second", otherCar))
        assertEquals(CarModelErrorCode.ADDON_SHARED_CONFLICT.code, outcome.code)
        assertTrue(outcome.reason.contains("gametext.txt"))
        assertFalse(outcome.gameFilesTouched)
        assertEquals("modded-gametext", File(depot, "gametext.txt").readText())
        assertFalse(File(depot, "vehicles/$otherCar").exists())
    }

    @Test fun gameRunningBlocksTheInstall() = runBlocking {
        addModel()
        ops.gameRunning = true
        val outcome = failed(install())
        assertEquals(CarModelErrorCode.GAME_RUNNING.code, outcome.code)
        assertFalse(outcome.gameFilesTouched)
        assertEquals("stock-gametext", File(depot, "gametext.txt").readText())
    }

    // ---- failure, rollback, recovery -----------------------------------------------------------

    @Test fun partialCopyFailureRollsBackEveryWrite() = runBlocking {
        addModel()
        ops.failImportOf = "vehicles/data/car.brakes.nct"
        val outcome = failed(install())
        assertTrue(outcome.gameFilesTouched)
        assertTrue(outcome.originalRestored)
        assertEquals("stock-gametext", File(depot, "gametext.txt").readText())
        assertEquals("stock-events", File(depot, "data/events.dat.nct").readText())
        assertEquals("stock-brakes", File(depot, "vehicles/data/car.brakes.nct").readText())
        assertFalse(vehicleDir().exists())
        assertNull(store.current.activeInstall(pkg, car))
    }

    @Test fun hashMismatchAfterCopyIsRejectedAndRolledBack() = runBlocking {
        addModel()
        ops.corruptImportOf = "gametext.txt"
        val outcome = failed(install())
        assertEquals(CarModelErrorCode.ADDON_HASH_MISMATCH.code, outcome.code)
        assertTrue(outcome.originalRestored)
        assertEquals("stock-gametext", File(depot, "gametext.txt").readText())
    }

    @Test fun processDeathMidInstallLeavesAnInterruptedRecordThatRemovesCleanly() = runBlocking {
        addModel()
        // Snapshot the disk and state exactly as they are when the 4th file is about to be copied, as if the
        // process died there; then let this run continue (its own rollback is irrelevant to the snapshot).
        val gameSnap = File(temp, "game-snap"); val storeSnap = File(temp, "store-snap")
        ops.beforeImport = { count ->
            if (count == 4) { File(temp, "game").copyRecursively(gameSnap, true); File(temp, "store").copyRecursively(storeSnap, true) }
        }
        install()
        // "Restart": the disk and state.json from the moment of death.
        File(temp, "game").deleteRecursively(); gameSnap.copyRecursively(File(temp, "game"), true)
        val restarted = CarModelStore(storeSnap)
        val record = restarted.current.activeInstall(pkg, car)!!
        assertFalse("an interrupted add-on must not look installed", record.complete)
        assertTrue(File(depot, "gametext.txt").readText().startsWith("modded"))

        val engine2 = CarModelEngine(restarted, paths) { 2_000L }
        // Re-applying is refused with a clear "interrupted" message until it is cleaned up.
        val retry = engine2.install(ops, InstallMode.ADDON, "hub", pkg, car) as ApplyOutcome.Failed
        assertEquals(CarModelErrorCode.ADDON_TARGET_OCCUPIED.code, retry.code)
        assertTrue(retry.reason.contains("interrupted"))
        // Removal skips the "modified since install" check for never-copied files and restores originals.
        engine2.removeAddon(ops, pkg, car).getOrThrow()
        assertEquals("stock-gametext", File(depot, "gametext.txt").readText())
        assertEquals("stock-events", File(depot, "data/events.dat.nct").readText())
        assertEquals("stock-brakes", File(depot, "vehicles/data/car.brakes.nct").readText())
        assertFalse(vehicleDir().exists())
        assertNull(restarted.current.activeInstall(pkg, car))
        // And a fresh apply now succeeds.
        assertTrue(engine2.install(ops, InstallMode.ADDON, "hub", pkg, car) is ApplyOutcome.Success)
    }

    // ---- path safety ---------------------------------------------------------------------------

    @Test fun traversalInADestinationPathIsRefusedBeforeAnyWrite() = runBlocking {
        addModel(files = packageFiles + (Triple("evil.txt", "../../../outside.txt", DEPOT_ROOT) to "evil"))
        val outcome = failed(install())
        assertEquals(CarModelErrorCode.UNSAFE_PATH.code, outcome.code)
        assertFalse(File(temp, "outside.txt").exists())
        assertEquals("stock-gametext", File(depot, "gametext.txt").readText())
    }

    @Test fun absoluteDestinationPathIsRefused() = runBlocking {
        addModel(files = packageFiles + (Triple("abs.txt", "/etc/abs.txt", DEPOT_ROOT) to "abs"))
        assertEquals(CarModelErrorCode.UNSAFE_PATH.code, failed(install()).code)
    }

    @Test fun unsafeVehicleDirectoryCannotEscapeTheVehiclesFolder() = runBlocking {
        addModel()
        val error = runCatching { paths.vehicleDir(pkg, "../../escape") }.exceptionOrNull() as CarModelException
        assertEquals(CarModelErrorCode.UNSAFE_VEHICLE_DIRECTORY, error.error)
        assertTrue(install(target = "../escape") is ApplyOutcome.Failed)
    }

    @Test fun mappingForEveryDestinationRootMatchesTheRr3DepotLayout() {
        fun dest(path: String, root: CarModelDestinationRoot) = paths.destination(pkg, car, CarModelFile("", path, "", "", sizeBytes = 1, sha256 = "0".repeat(64), destinationRoot = root))
        val depotPath = depot.path.replace('\\', '/')
        assertEquals("$depotPath/vehicles/$car/$car.points", dest("$car/$car.points", VEHICLE))
        assertEquals("$depotPath/vehicles/$car/$car.points", dest("vehicles/$car/$car.points", VEHICLE))
        assertEquals("$depotPath/vehicles/data/car.brakes.nct", dest("vehicles/data/car.brakes.nct", VEHICLES_DATA))
        assertEquals("$depotPath/data/events.dat.nct", dest("data/events.dat.nct", DEPOT_DATA))
        assertEquals("$depotPath/gametext.txt", dest("gametext.txt", DEPOT_ROOT))
    }

    // ---- state + UI helpers --------------------------------------------------------------------

    @Test fun completionFlagSurvivesJsonAndLegacyRecordsDefaultToComplete() {
        val record = InstalledCarModel("m", "M", pkg, car, 1L, true, 1, 1L, InstallMode.ADDON, complete = false)
        assertFalse(InstalledCarModel.fromJson(JSONObject(record.toJson().toString())).complete)
        val legacy = record.toJson().apply { remove("complete") }
        assertTrue(InstalledCarModel.fromJson(legacy).complete)
    }

    @Test fun installModeComesFromTheManifestAndUnknownModesAreTreatedAsReplace() {
        assertEquals(InstallMode.ADDON, CarModelsViewModel.installModeOf(addModel().manifest))
        assertEquals(InstallMode.REPLACE, CarModelsViewModel.installModeOf(addModel("r", mode = "REPLACE").manifest))
        assertEquals(InstallMode.REPLACE, CarModelsViewModel.installModeOf(addModel("u", mode = "weird").manifest))
    }

    @Test fun replaceTargetResolutionIsExactNeverASubstringOfAnotherCar() {
        val vehicles = listOf("2001_bmw_m3_gtr_street", "2010_bmw_m3", "2010_BMW_M3_GT2")
        assertNull("absent car is not silently mapped to a different one", CarModelsViewModel.resolveReplaceTarget(car, vehicles))
        assertNull(CarModelsViewModel.resolveReplaceTarget("bmw_m3", vehicles))
        assertEquals("2010_BMW_M3_GT2", CarModelsViewModel.resolveReplaceTarget("2010_bmw_m3_gt2", vehicles))
        assertEquals("2010_bmw_m3", CarModelsViewModel.resolveReplaceTarget("2010-BMW-M3", vehicles))
    }

    @Test fun detailsPreviewExplainsAnAddon() {
        val text = applyPreview(addModel())
        assertNotNull(text)
        assertTrue(text!!.contains(car) && text.contains("4 shared game files"))
        assertNull(applyPreview(addModel("r", mode = "REPLACE")))
    }

    /** Local-disk privileged ops with failure injection. */
    private class DiskOps : CarModelFileOps {
        var failImportOf: String? = null
        var corruptImportOf: String? = null
        var gameRunning = false
        var beforeImport: (Int) -> Unit = {}
        private var imports = 0
        override fun exists(path: String) = File(path).exists()
        override fun fileSize(path: String): Long? = File(path).takeIf { it.isFile }?.length()
        override fun isDirectory(path: String) = File(path).isDirectory
        override fun listDirectories(packageRoot: String, directory: String) = File(directory).listFiles().orEmpty().filter { it.isDirectory }.map { it.name }
        override fun listTree(packageRoot: String, directory: String): List<RemoteFile> {
            val root = File(directory)
            return root.walkTopDown().filter { it.isFile }.map { RemoteFile(it.relativeTo(root).path.replace(File.separatorChar, '/'), it.length()) }.toList()
        }
        override fun sha256(path: String) = File(path).takeIf { it.isFile }?.let(CarModelEngine::sha256).orEmpty()
        override fun copy(source: String, destination: String): Boolean {
            if (!File(source).isFile) return false
            File(source).copyTo(File(destination).apply { parentFile?.mkdirs() }, overwrite = true); return true
        }
        override fun importLocal(source: File, destination: String): Boolean {
            beforeImport(++imports)
            if (failImportOf?.let { destination.replace('\\', '/').endsWith(it) } == true) return false
            source.copyTo(File(destination), overwrite = true)
            if (corruptImportOf?.let { destination.replace('\\', '/').endsWith(it) } == true) File(destination).appendText("!")
            return true
        }
        override fun createDirectory(path: String) = File(path).isDirectory || File(path).mkdirs()
        override fun delete(path: String) = !File(path).exists() || File(path).deleteRecursively()
        override fun writeText(path: String, text: String): Boolean { File(path).apply { parentFile?.mkdirs() }.writeText(text); return true }
        override fun readText(path: String) = File(path).takeIf { it.isFile }?.readText()
        override fun ensureGameStopped(gamePackage: String) = !gameRunning
    }
}
