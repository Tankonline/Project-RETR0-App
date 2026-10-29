package com.retr0.projectretr0.data.carModels.local

import com.retr0.projectretr0.data.carModels.CarModelErrorCode
import com.retr0.projectretr0.data.carModels.CarModelException
import com.retr0.projectretr0.data.carModels.manifest.CarModelFolderScanner
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.File
import java.nio.file.Files

class CarModelEngineTest {
    private val pkg = "com.ea.games.r3_row"
    private val vehicle = "2001_bmw_m3_gtr_street"
    private lateinit var temp: File
    private lateinit var store: CarModelStore
    private lateinit var ops: DiskOps
    private lateinit var engine: CarModelEngine
    private lateinit var target: File
    private lateinit var backupDir: File

    @Before fun setUp() {
        temp = Files.createTempDirectory("car-engine").toFile()
        val gameRoot = File(temp, "game")
        target = File(gameRoot, "files/.depot/vehicles/$vehicle")
        File(target, "livery").mkdirs()
        File(target, "body.m3g").writeText("stock-body")
        File(target, "car.points").writeText("stock-points")
        File(target, "livery/skin_01.atc.dds.z").writeText("stock-livery")
        store = CarModelStore(File(temp, "store"))
        val paths = CarModelPaths(File(temp, "backups").path.replace('\\', '/'), mapOf(pkg to gameRoot.path.replace('\\', '/')))
        backupDir = File(paths.backupDir(pkg, vehicle))
        ops = DiskOps()
        engine = CarModelEngine(store, paths) { 1_000L }
    }

    @After fun tearDown() { temp.deleteRecursively() }

    private fun importModel(id: String = "model", extraFile: Boolean = true): LocalCarModel {
        val files = File(store.libraryDir(id), "files")
        File(files, "livery").mkdirs()
        File(files, "body.m3g").writeText("modded-body-$id")
        File(files, "livery/skin_01.atc.dds.z").writeText("modded-livery-$id")
        if (extraFile) File(files, "extra.bin").writeText("new-file-$id")
        val manifest = CarModelFolderScanner.scan(files).manifest
        val model = LocalCarModel(id, "Model $id", "tester", "", "", "", listOf(pkg), manifest, files.absolutePath, null, emptyList(), emptyList(), 0L)
        store.update { it.copy(models = it.models + model) }
        return model
    }

    @Test fun applyIsRefusedWithoutAVerifiedBackup() = runBlocking {
        importModel()
        val outcome = engine.apply(ops, "model", pkg, vehicle)
        assertTrue(outcome is ApplyOutcome.Failed)
        assertFalse((outcome as ApplyOutcome.Failed).gameFilesTouched)
        assertEquals("stock-body", File(target, "body.m3g").readText())
    }

    @Test fun backupThenApplyThenRevertRoundTripsTheVehicleFolder() = runBlocking {
        importModel()
        val backup = engine.ensureBackup(ops, pkg, vehicle).getOrThrow()
        assertTrue(backup.verified)
        assertEquals(3, backup.files.size)
        assertTrue(File(backupDir, CarModelEngine.BACKUP_MANIFEST).isFile)

        val outcome = engine.apply(ops, "model", pkg, vehicle)
        assertEquals(ApplyOutcome.Success(3, 1_000L), outcome)
        assertEquals("modded-body-model", File(target, "body.m3g").readText())
        assertFalse("full replacement removes stale stock files", File(target, "car.points").exists())
        assertTrue(File(target, "extra.bin").isFile)
        assertTrue(store.current.activeInstall(pkg, vehicle) != null)

        assertEquals(3, engine.revert(ops, pkg, vehicle).getOrThrow())
        assertEquals("stock-body", File(target, "body.m3g").readText())
        assertEquals("stock-livery", File(target, "livery/skin_01.atc.dds.z").readText())
        assertFalse("files the model added are removed on revert", File(target, "extra.bin").exists())
        assertEquals(null, store.current.activeInstall(pkg, vehicle))
    }

    @Test fun corruptBackupBlocksApplyAndRevert() = runBlocking {
        importModel()
        engine.ensureBackup(ops, pkg, vehicle).getOrThrow()
        File(backupDir, "files/body.m3g").writeText("tampered")

        val checked = engine.verifyBackup(ops, pkg, vehicle).getOrThrow()
        assertEquals(BackupStatus.CORRUPT, checked.status)
        assertEquals(1, checked.problemCount)
        assertTrue(engine.ensureBackup(ops, pkg, vehicle).isFailure)
        assertTrue(engine.apply(ops, "model", pkg, vehicle) is ApplyOutcome.Failed)
        assertTrue(engine.revert(ops, pkg, vehicle).isFailure)
        assertEquals("stock-body", File(target, "body.m3g").readText())
    }

    @Test fun missingBackupIsReportedAndBlocksRevert() = runBlocking {
        engine.ensureBackup(ops, pkg, vehicle).getOrThrow()
        File(backupDir, "files").deleteRecursively()
        assertEquals(BackupStatus.MISSING, engine.verifyBackup(ops, pkg, vehicle).getOrThrow().status)
        assertTrue(engine.revert(ops, pkg, vehicle).isFailure)
    }

    @Test fun failedCopyRollsBackToTheOriginalFiles() = runBlocking {
        importModel()
        engine.ensureBackup(ops, pkg, vehicle).getOrThrow()
        ops.failImportOf = "livery/skin_01.atc.dds.z"
        val outcome = engine.apply(ops, "model", pkg, vehicle) as ApplyOutcome.Failed
        assertTrue(outcome.gameFilesTouched)
        assertTrue(outcome.originalRestored)
        assertEquals("stock-body", File(target, "body.m3g").readText())
        assertFalse(File(target, "extra.bin").exists())
        assertEquals(null, store.current.activeInstall(pkg, vehicle))
    }

    @Test fun existingBackupIsNeverOverwrittenByModdedFiles() = runBlocking {
        importModel()
        engine.ensureBackup(ops, pkg, vehicle).getOrThrow()
        engine.apply(ops, "model", pkg, vehicle)
        val again = engine.ensureBackup(ops, pkg, vehicle).getOrThrow()
        assertTrue(again.verified)
        assertEquals("stock-body", File(backupDir, "files/body.m3g").readText())
    }

    @Test fun backupIsAdoptedFromDiskAfterAppDataIsCleared() = runBlocking {
        importModel()
        engine.ensureBackup(ops, pkg, vehicle).getOrThrow()
        engine.apply(ops, "model", pkg, vehicle)
        store.update { CarModelState() } // app data wiped; game folder still holds modded files
        val adopted = engine.ensureBackup(ops, pkg, vehicle).getOrThrow()
        assertTrue(adopted.verified)
        assertEquals("stock-body", File(backupDir, "files/body.m3g").readText())
    }

    @Test fun applyingASecondModelStartsFromStock() = runBlocking {
        importModel("first")
        importModel("second", extraFile = false)
        engine.ensureBackup(ops, pkg, vehicle).getOrThrow()
        engine.apply(ops, "first", pkg, vehicle)
        assertTrue(engine.apply(ops, "second", pkg, vehicle) is ApplyOutcome.Success)
        assertEquals("modded-body-second", File(target, "body.m3g").readText())
        assertFalse("the first model's extra file must not linger", File(target, "extra.bin").exists())
        assertEquals("second", store.current.activeInstall(pkg, vehicle)?.modelId)
    }

    @Test fun deletingABackupRemovesFilesAndRecord() = runBlocking {
        engine.ensureBackup(ops, pkg, vehicle).getOrThrow()
        engine.deleteBackup(ops, pkg, vehicle).getOrThrow()
        assertFalse(backupDir.exists())
        assertEquals(null, store.current.backup(pkg, vehicle))
    }

    @Test fun unsafeVehicleNamesAreRejected() = runBlocking {
        assertTrue(engine.ensureBackup(ops, pkg, "../escape").isFailure)
        assertTrue(engine.ensureBackup(ops, "com.other.app", vehicle).isFailure)
    }

    @Test fun stateSurvivesAReload() = runBlocking {
        importModel()
        engine.ensureBackup(ops, pkg, vehicle).getOrThrow()
        engine.apply(ops, "model", pkg, vehicle)
        val reloaded = CarModelStore(File(temp, "store")).current
        assertEquals(store.current, reloaded)
    }

    @Test fun fileGroupsMatchTheWizardBuckets() {
        assertEquals(CarModelFileGroup.MESHES, CarModelFileGroups.groupOf("car_lod0.m3g"))
        assertEquals(CarModelFileGroup.POINTS, CarModelFileGroups.groupOf("car.points"))
        assertEquals(CarModelFileGroup.LIVERIES, CarModelFileGroups.groupOf("livery/car_livery_01.atc.dds.z"))
        assertEquals(CarModelFileGroup.BINARIES, CarModelFileGroups.groupOf("car.bin"))
        assertEquals(CarModelFileGroup.OTHER, CarModelFileGroups.groupOf("car.xyz"))
        assertFalse(CarModelFileGroups.isIgnored("readme.txt"))
        assertTrue(CarModelFileGroups.isIgnored(".DS_Store"))
        assertFalse(CarModelFileGroups.isIgnored("car.m3g"))
    }

    private fun Result<*>.code() = (exceptionOrNull() as? CarModelException)?.error

    @Test fun failuresCarryTheirStableCodes() = runBlocking {
        assertEquals("CM-APPLY-003", (engine.apply(ops, "nope", pkg, vehicle) as ApplyOutcome.Failed).code)
        importModel()
        assertEquals("CM-APPLY-001", (engine.apply(ops, "model", pkg, vehicle) as ApplyOutcome.Failed).code)
        assertEquals(CarModelErrorCode.NO_BACKUP, engine.verifyBackup(ops, pkg, vehicle).code())
        assertEquals(CarModelErrorCode.NO_BACKUP, engine.revert(ops, pkg, vehicle).code())
        assertEquals(CarModelErrorCode.NO_BACKUP, engine.deleteBackup(ops, pkg, vehicle).code())
        assertEquals(CarModelErrorCode.UNSAFE_VEHICLE_DIRECTORY, engine.ensureBackup(ops, pkg, "../escape").code())
        assertEquals(CarModelErrorCode.UNSUPPORTED_PACKAGE, engine.ensureBackup(ops, "com.other.app", vehicle).code())
        assertEquals(CarModelErrorCode.TARGET_NOT_FOUND, engine.ensureBackup(ops, pkg, "no_such_vehicle").code())

        engine.ensureBackup(ops, pkg, vehicle).getOrThrow()
        ops.failImportOf = "livery/skin_01.atc.dds.z"
        val failed = engine.apply(ops, "model", pkg, vehicle) as ApplyOutcome.Failed
        assertEquals("CM-APPLY-006", failed.code)
        assertEquals("Unable to copy livery/skin_01.atc.dds.z", failed.reason)
        assertTrue("the Activity log names the code", store.current.activity.first().detail.startsWith("[CM-APPLY-006] "))
        ops.failImportOf = null

        File(backupDir, "files/body.m3g").writeText("tampered")
        assertEquals(CarModelErrorCode.BACKUP_CORRUPT, engine.ensureBackup(ops, pkg, vehicle).code())
        assertEquals("CM-APPLY-002", (engine.apply(ops, "model", pkg, vehicle) as ApplyOutcome.Failed).code)
        assertEquals(CarModelErrorCode.REVERT_BACKUP_CORRUPT, engine.revert(ops, pkg, vehicle).code())
        File(backupDir, "files").deleteRecursively()
        assertEquals(CarModelErrorCode.BACKUP_MISSING, engine.ensureBackup(ops, pkg, vehicle).code())
        assertEquals(CarModelErrorCode.REVERT_BACKUP_MISSING, engine.revert(ops, pkg, vehicle).code())
    }

    @Test fun backupGoneWhileAppliedIsRefusedWithItsCode() = runBlocking {
        importModel()
        engine.ensureBackup(ops, pkg, vehicle).getOrThrow()
        engine.apply(ops, "model", pkg, vehicle)
        engine.deleteBackup(ops, pkg, vehicle).getOrThrow()
        assertEquals(CarModelErrorCode.BACKUP_GONE_WHILE_APPLIED, engine.ensureBackup(ops, pkg, vehicle).code())
        assertEquals("modded files were never snapshotted as originals", false, backupDir.exists())
    }

    private val vehiclesRoot get() = target.parentFile!!

    @Test fun replaceModeStillGatesOnAVerifiedBackup() = runBlocking {
        importModel()
        val outcome = engine.install(ops, InstallMode.REPLACE, "model", pkg, vehicle) as ApplyOutcome.Failed
        assertEquals("CM-APPLY-001", outcome.code)
        assertFalse(outcome.gameFilesTouched)
        assertEquals("stock-body", File(target, "body.m3g").readText())
        engine.ensureBackup(ops, pkg, vehicle).getOrThrow()
        assertTrue(engine.install(ops, InstallMode.REPLACE, "model", pkg, vehicle) is ApplyOutcome.Success)
        assertEquals(InstallMode.REPLACE, store.current.activeInstall(pkg, vehicle)?.mode)
    }

    @Test fun addonInstallsIntoANewFolderWithoutAFullBackupAndRemoveDeletesExactlyItsFiles() = runBlocking {
        importModel()
        val addonDir = File(vehiclesRoot, "bmw_addon")
        val outcome = engine.install(ops, InstallMode.ADDON, "model", pkg, "bmw_addon")
        assertEquals(ApplyOutcome.Success(3, 1_000L), outcome)
        assertEquals("modded-body-model", File(addonDir, "body.m3g").readText())
        assertTrue(File(addonDir, "extra.bin").isFile)
        assertEquals("no whole-folder backup is made for an add-on", null, store.current.backup(pkg, "bmw_addon"))
        assertEquals("the stock vehicle is untouched", "stock-body", File(target, "body.m3g").readText())
        val install = store.current.activeInstall(pkg, "bmw_addon")!!
        assertEquals(InstallMode.ADDON, install.mode)
        assertEquals(3, install.addedFiles.size)
        assertTrue(install.overwritten.isEmpty())

        assertEquals(3, engine.removeAddon(ops, pkg, "bmw_addon").getOrThrow())
        assertFalse("the folder the add-on created is gone", addonDir.exists())
        assertEquals(null, store.current.activeInstall(pkg, "bmw_addon"))
        assertEquals("stock-points", File(target, "car.points").readText())
    }

    @Test fun addonBacksUpOnlyTheFilesItOverwritesAndRestoresThemOnRemove() = runBlocking {
        importModel()
        assertTrue(engine.install(ops, InstallMode.ADDON, "model", pkg, vehicle) is ApplyOutcome.Success)
        val install = store.current.activeInstall(pkg, vehicle)!!
        assertEquals(listOf("extra.bin"), install.addedFiles)
        assertEquals(setOf("body.m3g", "livery/skin_01.atc.dds.z"), install.overwritten.map { it.path }.toSet())
        assertEquals("modded-body-model", File(target, "body.m3g").readText())
        assertEquals("unrelated stock files are left alone", "stock-points", File(target, "car.points").readText())
        assertEquals("stock-body", File(install.backupRoot!!, "files/body.m3g").readText())

        engine.removeAddon(ops, pkg, vehicle).getOrThrow()
        assertEquals("stock-body", File(target, "body.m3g").readText())
        assertEquals("stock-livery", File(target, "livery/skin_01.atc.dds.z").readText())
        assertEquals("stock-points", File(target, "car.points").readText())
        assertFalse(File(target, "extra.bin").exists())
        assertTrue("an existing vehicle folder is never deleted", target.isDirectory)
        assertFalse(File(install.backupRoot!!).exists())
    }

    @Test fun failedAddonCopyRemovesWhatWasAdded() = runBlocking {
        importModel()
        ops.failImportOf = "livery/skin_01.atc.dds.z"
        val outcome = engine.install(ops, InstallMode.ADDON, "model", pkg, "bmw_addon") as ApplyOutcome.Failed
        assertEquals("CM-ADDON-003", outcome.code)
        assertTrue(outcome.gameFilesTouched)
        assertTrue(outcome.originalRestored)
        assertFalse(File(vehiclesRoot, "bmw_addon").exists())
        assertEquals(null, store.current.activeInstall(pkg, "bmw_addon"))
    }

    @Test fun replaceAndAddonDoNotStackOnTheSameFolder() = runBlocking {
        importModel()
        engine.install(ops, InstallMode.ADDON, "model", pkg, vehicle)
        assertEquals("CM-ADDON-008", (engine.install(ops, InstallMode.REPLACE, "model", pkg, vehicle) as ApplyOutcome.Failed).code)
        assertEquals("CM-ADDON-002", (engine.install(ops, InstallMode.ADDON, "model", pkg, vehicle) as ApplyOutcome.Failed).code)
        engine.removeAddon(ops, pkg, vehicle).getOrThrow()
        assertEquals(CarModelErrorCode.ADDON_NOT_INSTALLED, engine.removeAddon(ops, pkg, vehicle).code())
    }

    @Test fun addonInspectionReportsNewAndOverwrittenFiles() = runBlocking {
        val model = importModel()
        val fresh = engine.inspectAddonTarget(ops, model.manifest, pkg, "bmw_addon").getOrThrow()
        assertFalse(fresh.exists)
        assertTrue(fresh.nothingOverwritten)
        val existing = engine.inspectAddonTarget(ops, model.manifest, pkg, vehicle).getOrThrow()
        assertEquals("only exact manifest destinations are inspected", 1, existing.newFiles.size)
        assertTrue("the non-destination file remains new", existing.newFiles.contains("extra.bin"))
    }

    @Test fun addonStateSurvivesAReload() = runBlocking {
        importModel()
        engine.install(ops, InstallMode.ADDON, "model", pkg, vehicle)
        assertEquals(store.current, CarModelStore(File(temp, "store")).current)
    }

    /** Runs the privileged operations against the local disk so the engine is exercised end to end. */
    private class DiskOps : CarModelFileOps {
        var failImportOf: String? = null
        override fun exists(path: String) = File(path).exists()
        override fun isDirectory(path: String) = File(path).isDirectory
        override fun listDirectories(packageRoot: String, directory: String) = File(directory).listFiles().orEmpty().filter { it.isDirectory }.map { it.name }
        override fun listTree(packageRoot: String, directory: String): List<RemoteFile> {
            val root = File(directory)
            return root.walkTopDown().filter { it.isFile }.map { RemoteFile(it.relativeTo(root).path.replace(File.separatorChar, '/'), it.length()) }.toList()
        }
        override fun sha256(path: String) = File(path).takeIf { it.isFile }?.let(CarModelEngine::sha256).orEmpty()
        override fun copy(source: String, destination: String): Boolean {
            if (!File(source).isFile) return false
            File(source).copyTo(File(destination), overwrite = true)
            return true
        }
        override fun importLocal(source: File, destination: String): Boolean {
            val failing = failImportOf
            if (failing != null && destination.endsWith(failing)) return false
            source.copyTo(File(destination), overwrite = true)
            return true
        }
        override fun delete(path: String) = !File(path).exists() || File(path).deleteRecursively()
        override fun writeText(path: String, text: String): Boolean { File(path).apply { parentFile?.mkdirs() }.writeText(text); return true }
        override fun readText(path: String) = File(path).takeIf { it.isFile }?.readText()
        override fun ensureGameStopped(gamePackage: String) = true
    }
}
