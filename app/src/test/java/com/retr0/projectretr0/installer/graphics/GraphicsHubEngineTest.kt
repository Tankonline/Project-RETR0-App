package com.retr0.projectretr0.installer.graphics

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.security.MessageDigest
import kotlin.random.Random

class GraphicsHubEngineTest {
    private val pkg = GraphicsPaths.ROW
    private val nonAndroid = listOf("iPad", "AppleTV5,3")
    private lateinit var temp: File
    private lateinit var game: File
    private lateinit var ops: DiskGraphicsOps
    private lateinit var paths: GraphicsPaths
    private lateinit var engine: GraphicsHubEngine
    private var now = 1_000L
    private val sentinels = mutableMapOf<File, String>()
    private val logs = mutableListOf<String>()

    private val resEds get() = File(game, "files/apk/res/eds")
    private val resFeats get() = File(game, "files/apk/res/feats")
    private val depotEds get() = File(game, "files/.depot/eds")
    private val depotFeats get() = File(game, "files/.depot/feats")
    private val backups get() = File(temp, "GraphicsBackups")

    @Before fun setUp() {
        temp = Files.createTempDirectory("gfx-engine").toFile()
        game = File(temp, "game")
        val random = Random(42)
        for (name in GraphicsPaths.ANDROID_TIERS + nonAndroid) {
            File(resEds, "$name.plist").apply { parentFile.mkdirs() }.writeBytes(random.nextBytes(500 + random.nextInt(100)))
            File(resFeats, "$name.feats").apply { parentFile.mkdirs() }.writeBytes(random.nextBytes(800 + random.nextInt(100)))
        }
        File(resFeats, "mac.feats").writeBytes(random.nextBytes(700))
        resEds.copyRecursively(depotEds)
        resFeats.copyRecursively(depotFeats)
        File(game, "files/.depot/Tracks/Brands_Hatch/processed/high").mkdirs()
        File(game, "files/.depot/Tracks/Bathurst/processed/high").mkdirs()
        for (rel in listOf("files/.depot/effects/particles/x.pvr.z", "files/.depot/audio/a.wav", "files/doc/gs.2.dat", "files/.depot/Tracks/Brands_Hatch/processed/hatch.trackdata")) {
            val f = File(game, rel).apply { parentFile.mkdirs(); writeBytes(random.nextBytes(64)) }
            sentinels[f] = sha(f)
        }
        paths = GraphicsPaths(backupRoot = backups.path.n(), packageRoots = mapOf(pkg to game.path.n()))
        ops = DiskGraphicsOps()
        engine = GraphicsHubEngine(ops, paths, clock = { now++ }, log = { level, code, detail -> logs += "${level.name.first()} $code $detail" })
    }

    @After fun tearDown() { temp.deleteRecursively() }

    // ------------------------------------------------------------------ queries

    @Test fun availablePresetsAreCompletePairsOnly() {
        val presets = engine.availablePresets(pkg)
        assertEquals((GraphicsPaths.ANDROID_TIERS + nonAndroid).sorted(), presets)
        assertFalse("mac has no plist", "mac" in presets)
    }

    @Test fun lowAndMidNeedLowTrackAssetsUntilTheyExist() {
        assertEquals(PresetCompatibility.NEEDS_LOW_TRACK_ASSETS, engine.compatibility(pkg, "AndroidLow"))
        assertEquals(PresetCompatibility.NEEDS_LOW_TRACK_ASSETS, engine.compatibility(pkg, "AndroidMid"))
        assertEquals(PresetCompatibility.OK, engine.compatibility(pkg, "AndroidHigh"))
        assertEquals(PresetCompatibility.OK, engine.compatibility(pkg, "SimRigTV"))
        File(game, "files/.depot/Tracks/Brands_Hatch/processed/low").mkdirs()
        assertEquals(PresetCompatibility.OK, engine.compatibility(pkg, "AndroidLow"))
    }

    // ------------------------------------------------------------------ apply

    @Test fun applyCopiesSelectedPairOverEveryAndroidTierAndKeepsOtherFiles() {
        val before = depotState()
        val result = engine.applyUniversal(pkg, "AndroidHigh")
        assertTrue(result.toString(), result is GraphicsResult.Success)
        val plist = sha(File(resEds, "AndroidHigh.plist"))
        val feats = sha(File(resFeats, "AndroidHigh.feats"))
        for (tier in GraphicsPaths.ANDROID_TIERS) {
            assertEquals(plist, sha(File(depotEds, "$tier.plist")))
            assertEquals(feats, sha(File(depotFeats, "$tier.feats")))
        }
        for (name in nonAndroid) {
            assertEquals(before["eds/$name.plist"], sha(File(depotEds, "$name.plist")))
            assertEquals(before["feats/$name.feats"], sha(File(depotFeats, "$name.feats")))
        }
        assertEquals(before["feats/mac.feats"], sha(File(depotFeats, "mac.feats")))
        assertNoTempFiles()
        assertScope()
    }

    @Test fun applyTakesSnapshotOfPreviousState() {
        val before = depotState()
        val result = engine.applyUniversal(pkg, "AndroidXtraHigh") as GraphicsResult.Success
        val dir = File(backups, "$pkg/${result.backupId}")
        val snap = (dir.resolve("eds").listFiles()!!.map { "eds/${it.name}" to sha(it) } +
            dir.resolve("feats").listFiles()!!.map { "feats/${it.name}" to sha(it) }).toMap()
        assertEquals(before, snap)
        assertTrue(File(dir, "manifest.json").readText().contains("\"operation\": \"apply-androidxtrahigh\""))
    }

    @Test fun lowIsBlockedWithoutOverrideAndNothingIsWritten() {
        val before = depotState()
        val result = engine.applyUniversal(pkg, "AndroidLow")
        assertTrue(result is GraphicsResult.Blocked)
        assertEquals("GFX_E020", (result as GraphicsResult.Blocked).code)
        assertEquals(before, depotState())
        assertTrue(ops.writes.isEmpty())
        assertFalse(backups.exists())
    }

    @Test fun lowAppliesWhenUserOverrides() {
        val result = engine.applyUniversal(pkg, "AndroidLow", allowIncompatible = true)
        assertTrue(result is GraphicsResult.Success)
        assertEquals(sha(File(resEds, "AndroidLow.plist")), sha(File(depotEds, "AndroidHigh.plist")))
    }

    @Test fun applyIsIdempotent() {
        engine.applyUniversal(pkg, "AndroidHigh")
        ops.publishCalls = 0
        val again = engine.applyUniversal(pkg, "AndroidHigh")
        assertTrue(again is GraphicsResult.Success)
        assertEquals(0, ops.publishCalls)
    }

    @Test fun failureMidPublishRollsBackExactly() {
        val before = depotState()
        ops.failPublishOnCall = 5
        val result = engine.applyUniversal(pkg, "AndroidHigh")
        assertTrue(result.toString(), result is GraphicsResult.Failed)
        assertTrue((result as GraphicsResult.Failed).rolledBack)
        assertEquals(before, depotState())
        assertNoTempFiles()
    }

    @Test fun snapshotFailureChangesNothing() {
        val before = depotState()
        ops.failCopyVerified = true
        val result = engine.applyUniversal(pkg, "AndroidHigh")
        assertEquals("GFX_E010", (result as GraphicsResult.Blocked).code)
        assertEquals(before, depotState())
        assertTrue(File(backups, pkg).listFiles().orEmpty().isEmpty())
    }

    @Test fun runningGameBlocksEverything() {
        ops.gameRunning = true
        val before = depotState()
        assertEquals("GFX_E001", (engine.applyUniversal(pkg, "AndroidHigh") as GraphicsResult.Blocked).code)
        assertEquals("GFX_E001", (engine.restoreFactory(pkg) as GraphicsResult.Blocked).code)
        assertEquals(before, depotState())
        assertTrue(ops.writes.isEmpty())
    }

    @Test fun invalidPresetNamesAreRejected() {
        for (bad in listOf("../x", "a/b", "", "AndroidHigh.plist", "Android High")) {
            assertTrue(bad, engine.applyUniversal(pkg, bad) is GraphicsResult.Blocked)
        }
        assertTrue(ops.writes.isEmpty())
    }

    @Test fun unsupportedPackageIsRejected() {
        assertEquals("GFX_E002", (engine.applyUniversal("com.example", "AndroidHigh") as GraphicsResult.Blocked).code)
    }

    // ------------------------------------------------------------------ restore

    @Test fun restoreFactoryMatchesResAndRemovesStaleManagedFiles() {
        engine.applyUniversal(pkg, "SimRigTV")
        File(depotEds, "Old.plist").writeText("stale")
        File(depotEds, "notes.txt").writeText("not ours")
        val result = engine.restoreFactory(pkg)
        assertTrue(result.toString(), result is GraphicsResult.Success)
        assertEquals(hashes(resEds), hashes(depotEds) - "notes.txt")
        assertEquals(hashes(resFeats), hashes(depotFeats))
        assertFalse(File(depotEds, "Old.plist").exists())
        assertTrue("unmanaged files are left alone", File(depotEds, "notes.txt").exists())
        assertScope()
    }

    @Test fun restoreBackupReturnsToPreviousStateAndSnapshotsFirst() {
        engine.applyUniversal(pkg, "AndroidHigh")
        val afterHigh = depotState()
        engine.applyUniversal(pkg, "SimRigTV")
        val countBefore = engine.listBackups(pkg).size
        val result = engine.restoreBackup(pkg, "latest")
        assertTrue(result.toString(), result is GraphicsResult.Success)
        assertEquals(afterHigh, depotState())
        assertEquals(countBefore + 1, engine.listBackups(pkg).size)
        assertScope()
    }

    @Test fun tamperedBackupIsRefused() {
        val id = (engine.applyUniversal(pkg, "AndroidHigh") as GraphicsResult.Success).backupId!!
        File(backups, "$pkg/$id/eds/AndroidHigh.plist").writeText("tampered")
        val before = depotState()
        assertEquals("GFX_E040", (engine.restoreBackup(pkg, id) as GraphicsResult.Blocked).code)
        assertEquals(before, depotState())
    }

    @Test fun retentionKeepsNewestTen() {
        repeat(12) { engine.applyUniversal(pkg, if (it % 2 == 0) "AndroidHigh" else "SimRigTV") }
        val list = engine.listBackups(pkg)
        assertEquals(10, list.size)
        assertEquals(list.map { it.createdAt }.sortedDescending(), list.map { it.createdAt })
    }

    @Test fun noBackupMeansBlocked() {
        assertEquals("GFX_E041", (engine.restoreBackup(pkg, "latest") as GraphicsResult.Blocked).code)
    }

    // ------------------------------------------------------------------ diagnostics

    @Test fun outdatedShizukuHelperIsBlockedBeforeAnyWrite() {
        ops.apiVersion = 0
        val before = depotState()
        val result = engine.applyUniversal(pkg, "AndroidHigh")
        assertEquals("GFX_E008", (result as GraphicsResult.Blocked).code)
        assertTrue(result.message.contains("restart Shizuku"))
        assertEquals(before, depotState())
        assertTrue(ops.writes.isEmpty())
    }

    @Test fun writeFailureReasonIsLoggedAndShown() {
        ops.failPublishOnCall = 1
        val result = engine.applyUniversal(pkg, "AndroidLow", allowIncompatible = true) as GraphicsResult.Failed
        assertTrue(result.message, result.message.contains("write feats/Android"))
        assertTrue(result.message, result.message.contains("AccessDeniedException"))
        val line = logs.single { it.startsWith("E GFX_E031") }
        assertTrue(line, line.contains("file=feats/Android"))
        assertTrue(line, line.contains("from=files/apk/res/feats/AndroidLow.feats"))
        assertTrue(line, line.contains("reason=\"stage=rename reason=AccessDeniedException\""))
        assertTrue(logs.any { it.startsWith("I GFX_I051") })
    }

    @Test fun successfulApplyLogsEveryStepWithCorrelationId() {
        engine.applyUniversal(pkg, "AndroidHigh")
        // 6 tiers differ from AndroidHigh in each folder.
        assertEquals(12, logs.count { it.startsWith("I GFX_I021") })
        for (code in listOf("GFX_I001", "GFX_I006", "GFX_I007", "GFX_I011", "GFX_I012", "GFX_I002", "GFX_I020", "GFX_I003", "GFX_I004", "GFX_I009")) {
            assertTrue("missing $code in $logs", logs.any { it.contains(" $code ") })
        }
        val opIds = logs.map { Regex("op=(\\S+)").find(it)?.groupValues?.get(1) }.toSet()
        assertEquals("one operation id for all lines: $opIds", 1, opIds.size)
        assertTrue(logs.all { it.contains("phase=") && it.contains("stage=") })
    }

    @Test fun verifyMismatchNamesTheFile() {
        // A file that changes after it is written must be reported by name.
        var corrupted = false
        val sneaky = object : GraphicsFileOps by ops {
            override fun publishGraphicsFile(source: String, target: String, expectedSha256: String): OpResult {
                val r = ops.publishGraphicsFile(source, target, expectedSha256)
                if (!corrupted && target.endsWith("eds/AndroidMid.plist")) { corrupted = true; File(target).writeText("changed behind our back") }
                return r
            }
        }
        val e = GraphicsHubEngine(sneaky, paths, clock = { now++ }, log = { level, code, detail -> logs += "${level.name.first()} $code $detail" })
        val result = e.applyUniversal(pkg, "AndroidHigh") as GraphicsResult.Failed
        assertTrue(result.message, result.message.contains("hash mismatch eds/AndroidMid.plist"))
        assertTrue(result.rolledBack)
    }

    // ------------------------------------------------------------------ HDR fix (Graphics Hub apply)

    private fun samplePlist(id: String) = listOf(
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>",
        "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">",
        "<plist version=\"1.0\">", "<dict>",
        "\t<key>ENV_MAP_HDR</key>", "\t<false/>",
        "\t<key>GAMMA_CORRECTION</key>", "\t<false/>",
        "\t<key>PLIST_IDENTIFIER</key>", "\t<string>$id</string>",
        "\t<key>TONEMAP</key>", "\t<false/>",
        "\t<key>USE_HDR_AUTOMATIC_EXPOSURE</key>", "\t<false/>",
        "\t<key>USE_HDR_POST_PROCESS</key>", "\t<false/>",
        "</dict>", "</plist>", "",
    ).joinToString("\r\n")

    private fun useEncryptedHigh() = File(resEds, "AndroidHigh.plist").writeBytes(PlistCipher.transform(samplePlist("AndroidHigh").toByteArray())!!)

    private fun decodedTier(tier: String) = PlistCipher.decodePlist(File(depotEds, "$tier.plist").readBytes())!!

    // ------------------------------------------------------------------ Hub A/B/C switches

    /** Every Android tier as a real (encrypted) plist with HDR off, in both apk/res and .depot. */
    private fun useEncryptedTiers() {
        for (tier in GraphicsPaths.ANDROID_TIERS) {
            val bytes = PlistCipher.transform(samplePlist(tier).toByteArray())!!
            File(resEds, "$tier.plist").writeBytes(bytes)
            File(depotEds, "$tier.plist").writeBytes(bytes)
        }
    }

    private fun backupCount() = backups.resolve(pkg).listFiles()?.size ?: 0

    @Test fun scanReportsNoFixOnFactoryFiles() {
        useEncryptedTiers()
        val scan = engine.scanHdr(pkg)!!
        assertEquals(HdrState.NONE, scan.state)
        assertFalse(scan.mixed)
        assertEquals(GraphicsPaths.ANDROID_TIERS.toSet(), scan.tiers.keys)
        assertTrue(scan.fromFactory.isEmpty())
        assertTrue(logs.any { it.contains(" GFX_I017 ") && it.contains("state=NONE") })
    }

    @Test fun scanFindsTheActivePresetOnlyWhenEveryTierShowsIt() {
        useEncryptedTiers()
        assertEquals("factory tiers each keep their own name", null, engine.scanHdr(pkg)!!.activePreset)
        assertEquals("AndroidMid2", engine.scanHdr(pkg)!!.presets["AndroidMid2"])
        assertTrue(engine.applyUniversalWithHdr(pkg, "AndroidXtraHigh", HdrLevel.B) is GraphicsResult.Success)
        val scan = engine.scanHdr(pkg)!!
        assertEquals("AndroidXtraHigh", scan.activePreset)
        assertTrue(logs.any { it.contains(" GFX_I017 ") && it.contains("activePreset=AndroidXtraHigh") })
        // Changing the HDR level keeps the preset.
        assertTrue(engine.applyHdrToCurrent(pkg, HdrLevel.C) is GraphicsResult.Success)
        assertEquals("AndroidXtraHigh", engine.scanHdr(pkg)!!.activePreset)
    }

    @Test fun switchSetsExactlyThatLevelAndLowerLevelsTurnHigherSwitchesOff() {
        useEncryptedTiers()
        val featsBefore = hashes(depotFeats)
        assertTrue(engine.applyHdrToCurrent(pkg, HdrLevel.C) is GraphicsResult.Success)
        assertEquals(HdrState.C, engine.scanHdr(pkg)!!.state)
        assertTrue(engine.applyHdrToCurrent(pkg, HdrLevel.A) is GraphicsResult.Success)
        val scan = engine.scanHdr(pkg)!!
        assertEquals(HdrState.A, scan.state)
        for (tier in GraphicsPaths.ANDROID_TIERS) {
            val t = decodedTier(tier)
            assertEquals(true, HdrPatcher.readBool(t, "USE_HDR_POST_PROCESS"))
            assertEquals("higher switch turned off", false, HdrPatcher.readBool(t, "TONEMAP"))
            assertEquals(false, HdrPatcher.readBool(t, "USE_HDR_AUTOMATIC_EXPOSURE"))
            assertEquals("identifier kept", tier, HdrPatcher.identifier(t))
        }
        assertEquals("feats untouched", featsBefore, hashes(depotFeats))
        assertTrue("undecodable presets left alone", sha(File(resEds, "iPad.plist")) == sha(File(depotEds, "iPad.plist")))
        assertScope()
    }

    @Test fun choosingTheLevelAlreadyAppliedWritesNothing() {
        useEncryptedTiers()
        assertTrue(engine.applyHdrToCurrent(pkg, HdrLevel.B) is GraphicsResult.Success)
        val count = backupCount()
        val before = depotState()
        val again = engine.applyHdrToCurrent(pkg, HdrLevel.B) as GraphicsResult.Success
        assertEquals(null, again.backupId)
        assertEquals(count, backupCount())
        assertEquals(before, depotState())
    }

    @Test fun switchFillsInTiersThatOnlyExistInApkRes() {
        useEncryptedTiers()
        File(depotEds, "AndroidHigh.plist").delete()
        assertEquals(listOf("AndroidHigh"), engine.scanHdr(pkg)!!.fromFactory)
        assertTrue(engine.applyHdrToCurrent(pkg, HdrLevel.B) is GraphicsResult.Success)
        assertEquals(HdrState.B, HdrPatcher.detect(decodedTier("AndroidHigh")))
        assertTrue(engine.scanHdr(pkg)!!.fromFactory.isEmpty())
    }

    @Test fun mixedTiersAreReported() {
        useEncryptedTiers()
        File(depotEds, "SimRigTV.plist").writeBytes(PlistCipher.transform(HdrPatcher.applyExact(samplePlist("SimRigTV"), HdrLevel.C).toByteArray())!!)
        val scan = engine.scanHdr(pkg)!!
        assertTrue(scan.mixed)
        assertEquals(HdrState.CUSTOM, scan.state)
        assertEquals(HdrState.C, scan.tiers["SimRigTV"])
    }

    @Test fun restorePreviousUsesTheSelectedLevel() {
        useEncryptedTiers()
        assertTrue(engine.applyHdrToCurrent(pkg, HdrLevel.C) is GraphicsResult.Success) // backup = HDR off state
        val result = engine.restoreBackup(pkg, "latest", HdrLevel.B)
        assertTrue(result.toString(), result is GraphicsResult.Success)
        assertEquals(HdrState.B, engine.scanHdr(pkg)!!.state)
        assertScope()
    }

    @Test fun restorePreviousWithoutLevelIsByteForByte() {
        useEncryptedTiers()
        val before = hashes(depotEds)
        assertTrue(engine.applyHdrToCurrent(pkg, HdrLevel.C) is GraphicsResult.Success)
        assertTrue(engine.restoreBackup(pkg, "latest") is GraphicsResult.Success)
        assertEquals(before, hashes(depotEds))
    }

    @Test fun hubApplyIsExactAndTurnsHigherSwitchesOff() {
        File(resEds, "AndroidHigh.plist").writeBytes(PlistCipher.transform(HdrPatcher.applyExact(samplePlist("AndroidHigh"), HdrLevel.C).toByteArray())!!)
        assertTrue(engine.applyUniversalWithHdr(pkg, "AndroidHigh", HdrLevel.A) is GraphicsResult.Success)
        assertEquals(HdrState.A, HdrPatcher.detect(decodedTier("AndroidLow")))
    }

    @Test fun hdrFixLevelBTurnsOnPostProcessTonemapGammaOnEveryTier() {
        useEncryptedHigh()
        val result = engine.applyUniversalWithHdr(pkg, "AndroidHigh", HdrLevel.B)
        assertTrue(result.toString(), result is GraphicsResult.Success)
        for (tier in GraphicsPaths.ANDROID_TIERS) {
            val t = decodedTier(tier)
            assertEquals(true, HdrPatcher.readBool(t, "USE_HDR_POST_PROCESS"))
            assertEquals(true, HdrPatcher.readBool(t, "TONEMAP"))
            assertEquals(true, HdrPatcher.readBool(t, "GAMMA_CORRECTION"))
            assertEquals(false, HdrPatcher.readBool(t, "ENV_MAP_HDR"))
            assertEquals(false, HdrPatcher.readBool(t, "USE_HDR_AUTOMATIC_EXPOSURE"))
            assertEquals("identifier kept", "AndroidHigh", HdrPatcher.identifier(t))
            assertEquals(sha(File(resFeats, "AndroidHigh.feats")), sha(File(depotFeats, "$tier.feats")))
        }
        assertTrue(logs.any { it.contains(" GFX_I013 ") && it.contains("level=B") })
        assertScope()
    }

    @Test fun fullHdrLevelCTurnsOnEverything() {
        useEncryptedHigh()
        assertTrue(engine.applyUniversalWithHdr(pkg, "AndroidHigh", HdrLevel.C) is GraphicsResult.Success)
        val t = decodedTier("AndroidLow")
        HdrLevel.C.keys.forEach { assertEquals(it, true, HdrPatcher.readBool(t, it)) }
    }

    @Test fun undecodablePresetIsRefusedWithoutTouchingTheGame() {
        val before = depotState()
        val result = engine.applyUniversalWithHdr(pkg, "AndroidLow", HdrLevel.B) // random bytes in the fixture
        assertEquals("GFX_E015", (result as GraphicsResult.Blocked).code)
        assertEquals(before, depotState())
        assertFalse(backups.resolve(pkg).exists())
    }

    @Test fun crashFixRestoresFactoryWithHdrFixPerPreset() {
        useEncryptedHigh()
        val result = engine.restoreFactory(pkg, HdrLevel.B)
        assertTrue(result.toString(), result is GraphicsResult.Success)
        val high = decodedTier("AndroidHigh")
        assertEquals(true, HdrPatcher.readBool(high, "TONEMAP"))
        assertEquals("keeps its own identity", "AndroidHigh", HdrPatcher.identifier(high))
        // Presets that can't be decoded (random fixture bytes) come back as exact factory bytes.
        assertEquals(sha(File(resEds, "AndroidLow.plist")), sha(File(depotEds, "AndroidLow.plist")))
        assertEquals(hashes(resFeats), hashes(depotFeats))
        assertTrue(logs.any { it.contains(" GFX_I015 ") && it.contains("patched=1") })
        assertScope()
    }

    @Test fun restoreOriginalWithFullHdrAppliesLevelC() {
        useEncryptedHigh()
        val result = engine.restoreFactory(pkg, HdrLevel.C)
        assertTrue(result.toString(), result is GraphicsResult.Success)
        val high = decodedTier("AndroidHigh")
        assertEquals(true, HdrPatcher.readBool(high, "TONEMAP"))
        assertEquals(true, HdrPatcher.readBool(high, "ENV_MAP_HDR"))
        assertEquals(true, HdrPatcher.readBool(high, "USE_HDR_AUTOMATIC_EXPOSURE"))
        assertEquals("AndroidHigh", HdrPatcher.identifier(high))
        assertScope()
    }

    @Test fun restoreAfterHdrApplyReturnsFactoryBytes() {
        useEncryptedHigh()
        engine.applyUniversalWithHdr(pkg, "AndroidHigh", HdrLevel.B)
        assertTrue(engine.restoreFactory(pkg) is GraphicsResult.Success)
        assertEquals(hashes(resEds), hashes(depotEds))
    }

    // ------------------------------------------------------------------ validator

    @Test fun pathValidatorAcceptsOnlyGraphicsFiles() {
        val root = "/storage/emulated/0/Android/data/com.ea.games.r3_row/files"
        assertTrue(GraphicsPathValidator.isTarget("$root/.depot/eds/AndroidHigh.plist"))
        assertTrue(GraphicsPathValidator.isTarget("/storage/emulated/0/Android/data/com.ea.games.r3_na/files/.depot/feats/iPad2,1.feats"))
        for (bad in listOf(
            "$root/.depot/effects/x.plist", "$root/.depot/eds/../doc/gs.2.dat", "$root/.depot/eds/sub/x.plist",
            "$root/.depot/eds/x.txt", "$root/.depot/eds/x.feats", "$root/doc/gs.2.dat",
            "/storage/emulated/0/Android/data/com.other/files/.depot/eds/AndroidHigh.plist", "",
        )) assertFalse(bad, GraphicsPathValidator.isTarget(bad))
        assertTrue(GraphicsPathValidator.isTempFile("$root/.depot/eds/.AndroidHigh.plist.retr0-gfx-123"))
        assertFalse(GraphicsPathValidator.isTempFile("$root/.depot/eds/AndroidHigh.plist"))
        assertTrue(GraphicsPathValidator.isBackupPath("${GraphicsPathValidator.BACKUP_ROOT}/com.ea.games.r3_row/1-x"))
        assertFalse(GraphicsPathValidator.isBackupPath(GraphicsPathValidator.BACKUP_ROOT))
        assertFalse(GraphicsPathValidator.isBackupPath("${GraphicsPathValidator.BACKUP_ROOT}/../Backups"))
    }

    // ------------------------------------------------------------------ helpers

    private fun String.n() = replace('\\', '/')
    private fun sha(f: File) = MessageDigest.getInstance("SHA-256").digest(f.readBytes()).joinToString("") { "%02x".format(it) }
    private fun hashes(dir: File) = dir.listFiles().orEmpty().filter { it.isFile }.associate { it.name to sha(it) }
    private fun depotState() = hashes(depotEds).mapKeys { "eds/${it.key}" } + hashes(depotFeats).mapKeys { "feats/${it.key}" }

    private fun assertNoTempFiles() {
        val leftovers = (depotEds.list().orEmpty().toList() + depotFeats.list().orEmpty().toList()).filter { it.contains("retr0-gfx") }
        assertTrue(leftovers.toString(), leftovers.isEmpty())
    }

    /** Every write stayed inside eds, feats or the backup root, and nothing else in the game changed. */
    private fun assertScope() {
        val allowed = listOf(depotEds.path.n() + "/", depotFeats.path.n() + "/", backups.path.n() + "/")
        for (w in ops.writes) assertTrue("out of scope: $w", allowed.any { w.n().startsWith(it) })
        for ((f, h) in sentinels) assertEquals(f.path, h, sha(f))
        assertFalse(File(game, "files/.retr0").exists())
    }

    /** Real-disk fake of the Shizuku service that records every write and can inject failures. */
    private inner class DiskGraphicsOps : GraphicsFileOps {
        val writes = mutableListOf<String>()
        var publishCalls = 0
        var failPublishOnCall = -1
        var failPublishReason = "stage=rename reason=AccessDeniedException"
        var failCopyVerified = false
        var gameRunning = false
        var apiVersion = GraphicsPathValidator.SERVICE_API

        override fun exists(path: String) = File(path).exists()
        override fun isDirectory(path: String) = File(path).isDirectory
        override fun listFileNames(directory: String) = File(directory).listFiles().orEmpty().filter { it.isFile }.map { it.name }
        override fun listDirectoryNames(directory: String) = File(directory).listFiles().orEmpty().filter { it.isDirectory }.map { it.name }
        override fun sha256(path: String) = File(path).takeIf { it.isFile }?.let { sha(it) }.orEmpty()
        override fun size(path: String) = File(path).takeIf { it.isFile }?.length() ?: -1L
        override fun createDirectory(path: String): Boolean { writes += path; return File(path).isDirectory || File(path).mkdirs() }

        override fun copyVerified(source: String, destination: String, expectedSha256: String): OpResult {
            writes += destination
            if (failCopyVerified) return OpResult.fail("copyFile_failed")
            File(source).copyTo(File(destination), overwrite = true)
            return if (sha256(destination) == expectedSha256) OpResult.OK else OpResult.fail("backup_hash_mismatch")
        }

        override fun publishGraphicsFile(source: String, target: String, expectedSha256: String): OpResult {
            publishCalls++
            writes += target
            if (publishCalls == failPublishOnCall) {
                // Simulate a crash after the temp file was written.
                File(File(target).parentFile, GraphicsPathValidator.tempNameFor(File(target).name, 7)).writeText("partial")
                return OpResult.fail(failPublishReason)
            }
            if (sha256(source) != expectedSha256) return OpResult.fail("stage=validate_source reason=source_hash_mismatch")
            val t = File(File(target).parentFile, GraphicsPathValidator.tempNameFor(File(target).name, System.nanoTime()))
            File(source).copyTo(t, overwrite = true)
            Files.move(t.toPath(), File(target).toPath(), StandardCopyOption.REPLACE_EXISTING)
            return if (sha256(target) == expectedSha256) OpResult(true, "bytes=${File(target).length()}") else OpResult.fail("stage=verify_target")
        }

        override fun deleteGraphicsFile(target: String): OpResult {
            writes += target
            val f = File(target)
            return if (!f.exists() || (f.isFile && f.delete())) OpResult.OK else OpResult.fail("stage=delete")
        }
        override fun deleteBackupPath(path: String): Boolean { writes += path; return File(path).deleteRecursively() }
        override fun writeText(path: String, text: String): Boolean { writes += path; File(path).apply { parentFile?.mkdirs() }.writeText(text); return true }
        override fun readText(path: String) = File(path).takeIf { it.isFile }?.readText()
        override fun writeBytes(path: String, data: ByteArray): Boolean { writes += path; File(path).apply { parentFile?.mkdirs() }.writeBytes(data); return true }
        override fun readBytes(path: String) = File(path).takeIf { it.isFile }?.readBytes()
        override fun ensureGameStopped(gamePackage: String) = if (gameRunning) OpResult.fail("wasRunning=true forceStopped=false") else OpResult(true, "wasRunning=false")
        override fun serviceApiVersion() = apiVersion
        override fun environment(packageRoot: String) = "svcUid=2000 test=true"
    }
}
