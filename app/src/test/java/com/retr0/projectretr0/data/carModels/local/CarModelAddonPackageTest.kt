package com.retr0.projectretr0.data.carModels.local

import com.retr0.projectretr0.data.carModels.CarModelErrorCode
import com.retr0.projectretr0.data.carModels.manifest.CarModelDestinationRoot
import com.retr0.projectretr0.data.carModels.manifest.CarModelDestinationRoot.DEPOT_ROOT
import com.retr0.projectretr0.data.carModels.manifest.CarModelDestinationRoot.VEHICLE
import com.retr0.projectretr0.data.carModels.manifest.CarModelDestinationRoot.VEHICLES_DATA
import com.retr0.projectretr0.data.carModels.manifest.CarModelFile
import com.retr0.projectretr0.data.carModels.manifest.CarModelManifest
import com.retr0.projectretr0.data.carModels.manifest.InstallationPolicy
import com.retr0.projectretr0.data.carModels.manifest.PackageMetadata
import com.retr0.projectretr0.data.carModels.manifest.TargetVehicle
import com.retr0.projectretr0.ui.carmodels.WizardSteps
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.json.JSONObject
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.File
import java.nio.file.Files
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * Multi-vehicle ADDON packages, shaped like the "[S2RR3] v0.4 early access" folder import: several new cars
 * (paths `vehicles/<car>/...`) plus shared depot / vehicles/data files. Covers the Step 4 hang.
 */
class CarModelAddonPackageTest {
    private val pkg = "com.ea.games.r3_row"
    private val carA = "1990_bmw_m3_e36"
    private val carB = "1998_toyota_supra_mk4"
    private lateinit var temp: File
    private lateinit var store: CarModelStore
    private lateinit var paths: CarModelPaths
    private lateinit var engine: CarModelEngine
    private lateinit var depot: File
    private val ops = DiskOps()

    private val files = listOf(
        Triple("vehicles/$carA/$carA.points", VEHICLE, "a-points"),
        Triple("vehicles/$carA/livery/${carA}_ext.atc.dds.z", VEHICLE, "a-livery"),
        Triple("vehicles/$carB/$carB.points", VEHICLE, "b-points"),
        Triple("gametext.txt", DEPOT_ROOT, "pkg-gametext"),
        Triple("vehicles/data/car.brakes.nct", VEHICLES_DATA, "pkg-brakes"),
        Triple("vehicles/data/612_$carB.bin.nct", VEHICLES_DATA, "b-bin"),
    )

    @Before fun setUp() {
        temp = Files.createTempDirectory("car-package").toFile()
        val gameRoot = File(temp, "game")
        depot = File(gameRoot, "files/.depot")
        File(depot, "vehicles/data").mkdirs()
        File(depot, "gametext.txt").writeText("stock-gametext")
        File(depot, "vehicles/data/car.brakes.nct").writeText("stock-brakes")
        store = CarModelStore(File(temp, "store"))
        paths = CarModelPaths(File(temp, "backups").path.replace('\\', '/'), mapOf(pkg to gameRoot.path.replace('\\', '/')))
        engine = CarModelEngine(store, paths) { 1_000L }
    }

    @After fun tearDown() { temp.deleteRecursively() }

    private fun manifest(): CarModelManifest {
        val root = File(store.libraryDir("pack"), "files")
        val entries = files.map { (path, destRoot, content) ->
            File(root, path).apply { parentFile?.mkdirs(); writeText(content) }
            CarModelFile(path, path, "asset", "nct", sizeBytes = content.length.toLong(), sha256 = CarModelEngine.sha256(File(root, path)), destinationRoot = destRoot)
        }
        return CarModelManifest(
            vehicleDirectories = listOf(carA, carB), metadata = PackageMetadata("S2RR3 pack"), packageId = pkg, supportedGamePackages = listOf(pkg),
            target = TargetVehicle(carA, "files/.depot"), installationPolicy = InstallationPolicy.ADDON, installMode = "ADDON", files = entries,
        )
    }

    private fun addPackage(): LocalCarModel {
        val model = LocalCarModel("pack", "S2RR3 pack", "", "", "", "", listOf(pkg), manifest(), File(store.libraryDir("pack"), "files").absolutePath, null, emptyList(), emptyList(), 0L)
        store.update { it.copy(models = it.models + model) }
        return model
    }

    // ---- Step 4: inspection -------------------------------------------------------------------

    @Test fun packageInspectionCompletesWithOneTargetPerVehicleAndSharedFilesCountedOnce() = runBlocking {
        val inspection = engine.inspectAddonPackage(ops, addPackage().manifest, pkg).getOrThrow()
        assertEquals(listOf(carA, carB), inspection.vehicleDirectories)
        assertEquals(2, inspection.targets.size)
        assertTrue(inspection.targets.none { it.exists })
        // gametext + brakes exist once, not once per vehicle.
        assertEquals(2, inspection.overwrites.size)
        assertEquals(4, inspection.newFiles.size)
        // Each target lists only its own car's files (plus shared ones on the first).
        assertEquals(5, inspection.targets[0].collisions.size)
        assertEquals(1, inspection.targets[1].collisions.size)
    }

    @Test fun aCancelledFirstCallerNoLongerStrandsTheSecondWaiter() = runBlocking {
        val model = addPackage()
        val entered = CountDownLatch(1)
        val gate = CountDownLatch(1)
        ops.onExists = { entered.countDown(); gate.await(5, TimeUnit.SECONDS) }
        // Caller 1 starts the shared inspection (the first Step 4 LaunchedEffect run)…
        val first = launch(Dispatchers.Default) { engine.inspectAddonPackage(ops, model.manifest, pkg) }
        assertTrue(entered.await(5, TimeUnit.SECONDS))
        // …caller 2 joins it (the restarted effect: ENGINE_PACKAGE_DEDUP state=reuse)…
        val second = async(Dispatchers.Default) { engine.inspectAddonPackage(ops, model.manifest, pkg) }
        delay(100)
        // …and caller 1 is cancelled mid-inspection. Before the fix caller 2 waited forever.
        first.cancel()
        gate.countDown()
        val result = withTimeout(5_000) { second.await() }
        assertEquals(2, result.getOrThrow().targets.size)
    }

    // ---- Community Hub round trip ----------------------------------------------------------------

    /**
     * Upload (real wire rewrite) → Worker-shaped download plan → CommunityCarModelInstaller → Apply → Remove.
     * Before the wire fix, car B's files came back as `<carA>/<carB>/...` and installed nested inside car A.
     */
    @Test fun hubDownloadedPackageInstallsEachCarIntoItsOwnFolderAndRemovesCleanly() = runBlocking {
        val uploaded = com.retr0.projectretr0.data.carModels.toWireManifest(manifest().copy(installMode = "ADDON"))
        assertEquals("$carB/$carB.points", uploaded.files.first { it.sourcePath.endsWith("$carB.points") }.destinationPath)
        val libraryRoot = File(store.libraryDir("pack"), "files")
        val served = uploaded.files.associate { it.sourcePath to File(libraryRoot, it.sourcePath).readBytes() }
        val manifestBytes = uploaded.toJson().toString().toByteArray()
        val cover = "RIFF".toByteArray() + byteArrayOf(20, 0, 0, 0) + "WEBPVP8 cover".toByteArray()
        fun sha(bytes: ByteArray) = java.security.MessageDigest.getInstance("SHA-256").digest(bytes).joinToString("") { "%02x".format(it) }
        val hub = object : com.retr0.projectretr0.data.carModels.CarModelDownloadSource {
            override suspend fun downloadPlan(model: com.retr0.projectretr0.data.carModels.CommunityCarModel) = com.retr0.projectretr0.data.carModels.CarModelDownloadPlan.parse(
                JSONObject().put("manifestUrl", "/manifest").put("manifestSha256", sha(manifestBytes))
                    .put("files", org.json.JSONArray(served.map { (path, bytes) -> JSONObject().put("path", path).put("size", bytes.size).put("sha256", sha(bytes)).put("url", "/files/$path") }))
                    .put("cover", JSONObject().put("url", "/cover").put("sha256", sha(cover))), model)
            override suspend fun fetch(url: String, output: File, onBytes: (Long) -> Unit) {
                val bytes = when {
                    url == "/manifest" -> manifestBytes
                    url == "/cover" -> cover
                    else -> served.getValue(url.removePrefix("/files/"))
                }
                output.parentFile?.mkdirs(); output.writeBytes(bytes); onBytes(bytes.size.toLong())
            }
        }
        val listing = com.retr0.projectretr0.data.carModels.CommunityCarModel(
            id = "11111111-2222-4333-8444-555555555555", name = "S2RR3 pack", creator = "c", description = "", vehicleDirectory = carA,
            supportedPackages = listOf(pkg), gameBuild = "", fileCount = served.size, totalBytes = 0, likes = 0, downloads = 0, verifiedManifest = true, updatedAt = "",
        )
        val installer = CommunityCarModelInstaller(store, File(temp, "downloads"), hub, maxAttempts = 1, retryDelayMs = { 0L }, freeSpace = { Long.MAX_VALUE })
        val downloaded = installer.downloadAndImport(listing).getOrThrow()
        assertEquals(listOf(carA, carB), downloaded.manifest.vehicleDirectories)
        assertEquals("ADDON", downloaded.manifest.installMode)

        // Same target the details-screen Apply picks for a package: its first vehicle.
        val target = downloaded.manifest.vehicleDirectories.first()
        assertEquals(ApplyOutcome.Success(files.size, 1_000L), engine.install(ops, InstallMode.ADDON, downloaded.id, pkg, target))
        assertEquals("a-points", File(depot, "vehicles/$carA/$carA.points").readText())
        assertEquals("a-livery", File(depot, "vehicles/$carA/livery/${carA}_ext.atc.dds.z").readText())
        assertEquals("b-points", File(depot, "vehicles/$carB/$carB.points").readText())
        assertFalse("car B must not nest inside car A", File(depot, "vehicles/$carA/$carB").exists())
        assertEquals("pkg-gametext", File(depot, "gametext.txt").readText())
        assertEquals("pkg-brakes", File(depot, "vehicles/data/car.brakes.nct").readText())
        assertEquals("b-bin", File(depot, "vehicles/data/612_$carB.bin.nct").readText())

        engine.removeAddon(ops, pkg, carA).getOrThrow()
        assertEquals("stock-gametext", File(depot, "gametext.txt").readText())
        assertEquals("stock-brakes", File(depot, "vehicles/data/car.brakes.nct").readText())
        assertFalse(File(depot, "vehicles/data/612_$carB.bin.nct").exists())
        assertFalse(File(depot, "vehicles/$carA").exists())
        assertFalse(File(depot, "vehicles/$carB").exists())
        assertNull(store.current.occupant(pkg, carB))
    }

    // ---- install / remove ---------------------------------------------------------------------

    @Test fun packageInstallsEachCarIntoItsOwnFolderNeverNested() = runBlocking {
        addPackage()
        val outcome = engine.install(ops, InstallMode.ADDON, "pack", pkg, carA)
        assertEquals(ApplyOutcome.Success(files.size, 1_000L), outcome)
        assertEquals("a-points", File(depot, "vehicles/$carA/$carA.points").readText())
        assertEquals("a-livery", File(depot, "vehicles/$carA/livery/${carA}_ext.atc.dds.z").readText())
        assertEquals("b-points", File(depot, "vehicles/$carB/$carB.points").readText())
        assertFalse("no vehicles/<first>/<second>/ nesting", File(depot, "vehicles/$carA/$carB").exists())
        assertEquals("pkg-gametext", File(depot, "gametext.txt").readText())
        val record = store.current.activeInstall(pkg, carA)!!
        assertEquals(listOf(carA, carB), record.packageVehicles)
        assertEquals(listOf(carA, carB), record.createdVehicleDirectories)
        assertEquals(record, store.current.occupant(pkg, carB))
    }

    @Test fun removingThePackageRestoresSharedFilesAndDeletesEveryCreatedCarFolder() = runBlocking {
        addPackage(); engine.install(ops, InstallMode.ADDON, "pack", pkg, carA)
        engine.removeAddon(ops, pkg, carA).getOrThrow()
        assertFalse(File(depot, "vehicles/$carA").exists())
        assertFalse(File(depot, "vehicles/$carB").exists())
        assertFalse(File(depot, "vehicles/data/612_$carB.bin.nct").exists())
        assertEquals("stock-gametext", File(depot, "gametext.txt").readText())
        assertEquals("stock-brakes", File(depot, "vehicles/data/car.brakes.nct").readText())
        assertNull(store.current.occupant(pkg, carB))
    }

    @Test fun aFolderOwnedByThePackageCannotBeTakenByAnotherInstall() = runBlocking {
        addPackage(); engine.install(ops, InstallMode.ADDON, "pack", pkg, carA)
        // REPLACE on the package's second car is refused like any add-on-occupied vehicle.
        val replace = engine.install(ops, InstallMode.REPLACE, "pack", pkg, carB) as ApplyOutcome.Failed
        assertEquals(CarModelErrorCode.REPLACE_ON_ADDON.code, replace.code)
        assertEquals("b-points", File(depot, "vehicles/$carB/$carB.points").readText())
    }

    @Test fun packageOwnershipSurvivesJson() = runBlocking {
        addPackage(); engine.install(ops, InstallMode.ADDON, "pack", pkg, carA)
        val record = store.current.activeInstall(pkg, carA)!!
        assertEquals(record, InstalledCarModel.fromJson(JSONObject(record.toJson().toString())))
        assertEquals(record, CarModelStore(File(temp, "store")).current.occupant(pkg, carB))
    }

    @Test fun singleVehicleMappingIsUnchanged() {
        fun dest(path: String, vehicles: List<String>) = paths.destination(pkg, "my_folder", CarModelFile("", path, "", "", sizeBytes = 1, sha256 = "0".repeat(64), destinationRoot = CarModelDestinationRoot.VEHICLE), vehicles)
        val vehiclesRoot = File(depot, "vehicles").path.replace('\\', '/')
        assertEquals("$vehiclesRoot/my_folder/livery/x.z", dest("livery/x.z", emptyList()))
        assertEquals("$vehiclesRoot/my_folder/x.z", dest("vehicles/my_folder/x.z", listOf("my_folder")))
        assertEquals("$vehiclesRoot/$carB/x.z", dest("vehicles/$carB/x.z", listOf(carA, carB)))
    }

    // ---- wizard destination rule ----------------------------------------------------------------

    @Test fun wizardInstallsAPackageIntoItsFirstVehicleNotTheWrapperFolderName() {
        assertEquals(carA, WizardSteps.addonDestination(addon = true, isPackage = true, packageVehicles = listOf(carA, carB), typedFolder = "S2RR3_v0_4_early_access", replaceVehicle = null))
        assertEquals("typed_folder", WizardSteps.addonDestination(true, false, listOf("x"), "typed_folder", null))
        assertNull(WizardSteps.addonDestination(true, false, emptyList(), "bad name!", null))
        assertEquals("stock_car", WizardSteps.addonDestination(false, false, emptyList(), "ignored", "stock_car"))
        assertTrue(WizardSteps.INSPECT_TIMEOUT_MS in 10_000..120_000)
    }

    private class DiskOps : CarModelFileOps {
        @Volatile var onExists: () -> Unit = {}
        override fun exists(path: String): Boolean { onExists(); return File(path).exists() }
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
        override fun importLocal(source: File, destination: String): Boolean { source.copyTo(File(destination), overwrite = true); return true }
        override fun createDirectory(path: String) = File(path).isDirectory || File(path).mkdirs()
        override fun delete(path: String) = !File(path).exists() || File(path).deleteRecursively()
        override fun writeText(path: String, text: String): Boolean { File(path).apply { parentFile?.mkdirs() }.writeText(text); return true }
        override fun readText(path: String) = File(path).takeIf { it.isFile }?.readText()
        override fun ensureGameStopped(gamePackage: String) = true
    }
}
