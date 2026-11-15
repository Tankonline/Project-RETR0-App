package com.retr0.projectretr0.data.carModels.local

import com.retr0.projectretr0.data.carModels.CarModelDownloadPlan
import com.retr0.projectretr0.data.carModels.CarModelDownloadSource
import com.retr0.projectretr0.data.carModels.CarModelErrorCode
import com.retr0.projectretr0.data.carModels.CarModelException
import com.retr0.projectretr0.data.carModels.CarModelHttpException
import com.retr0.projectretr0.data.carModels.CommunityCarModel
import com.retr0.projectretr0.data.carModels.manifest.CarModelFile
import com.retr0.projectretr0.data.carModels.manifest.CarModelManifest
import com.retr0.projectretr0.data.carModels.manifest.InstallationPolicy
import com.retr0.projectretr0.data.carModels.manifest.PackageMetadata
import com.retr0.projectretr0.data.carModels.manifest.TargetVehicle
import com.retr0.projectretr0.ui.carmodels.toDetailsUi
import com.retr0.projectretr0.ui.skins.GamePackage
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.security.MessageDigest

/** Community Hub download → library transaction, including media and resume. */
class CommunityCarModelInstallerTest {
    private lateinit var temp: File
    private lateinit var store: CarModelStore
    private lateinit var downloads: File

    private val remoteId = "8c89ed68-1327-4e1c-a7b9-aedc422b0a22"
    private val target = "2004_bmw_m3_gtr"
    private val hubModel = CommunityCarModel(
        id = remoteId, name = "BMW M3 GTR Addon", creator = "tester", description = "hub model",
        vehicleDirectory = target, supportedPackages = listOf("com.ea.games.r3_row"), gameBuild = "13.x",
        fileCount = 2, totalBytes = 0, likes = 0, downloads = 0, verifiedManifest = true, updatedAt = "",
        coverUrl = "https://hub.test/v1/car-models/$remoteId/cover", screenshotUrls = emptyList(),
    )
    private val payload = mapOf("$target/body.z" to "body-bytes".toByteArray(), "$target/wheels/rim.z" to "rim-bytes!".toByteArray())
    private val webp = "RIFF".toByteArray() + byteArrayOf(20, 0, 0, 0) + "WEBPVP8 cover".toByteArray()
    private val webp2 = "RIFF".toByteArray() + byteArrayOf(20, 0, 0, 0) + "WEBPVP8 shot!".toByteArray()

    @Before fun setUp() {
        temp = Files.createTempDirectory("cm-community").toFile()
        store = CarModelStore(File(temp, "store"))
        downloads = File(temp, "car-model-downloads")
    }

    @After fun tearDown() { temp.deleteRecursively() }

    private fun sha(bytes: ByteArray) = MessageDigest.getInstance("SHA-256").digest(bytes).joinToString("") { "%02x".format(it) }

    private fun manifest(mode: String = "ADDON", files: Map<String, ByteArray> = payload) = CarModelManifest(
        metadata = PackageMetadata("Remote name", creator = "remote creator"),
        packageId = "com.ea.games.r3_row",
        supportedGamePackages = listOf("com.ea.games.r3_row"),
        target = TargetVehicle(target, target),
        installationPolicy = if (mode == "ADDON") InstallationPolicy.ADDON else InstallationPolicy.FULL_DIRECTORY_REPLACEMENT,
        installMode = mode,
        files = files.map { (path, bytes) -> CarModelFile(path, path.substringAfter('/'), "texture", "z", sizeBytes = bytes.size.toLong(), sha256 = sha(bytes)) },
    )

    /** In-memory Worker: serves a plan and asset bytes by URL, with injectable failures. */
    private inner class FakeHub(
        val manifest: CarModelManifest = manifest(),
        val files: Map<String, ByteArray> = payload,
        val served: MutableMap<String, ByteArray> = files.toMutableMap(),
        var cover: ByteArray? = webp,
        var screenshots: List<ByteArray> = listOf(webp2),
        val mediaInPlan: Boolean = true,
    ) : CarModelDownloadSource {
        val fetches = mutableListOf<String>()
        val failures = mutableMapOf<String, ArrayDeque<Throwable>>()
        var planCalls = 0

        fun failNext(url: String, vararg errors: Throwable) { failures.getOrPut(url) { ArrayDeque() }.addAll(errors) }

        override suspend fun downloadPlan(model: CommunityCarModel): CarModelDownloadPlan {
            planCalls++
            val json = JSONObject().put("manifestUrl", "/manifest").put("manifestSha256", sha(manifest.toJson().toString().toByteArray()))
                .put("files", org.json.JSONArray(files.map { (p, b) -> JSONObject().put("path", p).put("size", b.size).put("sha256", sha(b)).put("url", "/files/$p") }))
            if (mediaInPlan) {
                cover?.let { json.put("cover", JSONObject().put("url", "/cover").put("sha256", sha(it))) }
                json.put("screenshots", org.json.JSONArray(screenshots.mapIndexed { i, b -> JSONObject().put("position", i + 1).put("url", "/screenshots/${i + 1}").put("sha256", sha(b)) }))
            }
            return CarModelDownloadPlan.parse(json, model)
        }

        override suspend fun fetch(url: String, output: File, onBytes: (Long) -> Unit) {
            fetches += url
            failures[url]?.removeFirstOrNull()?.let { throw it }
            val bytes = when {
                url == "/manifest" -> manifest.toJson().toString().toByteArray()
                url.startsWith("/files/") -> served[url.removePrefix("/files/")] ?: throw CarModelHttpException(404, "missing")
                url == "/cover" || url.endsWith("/cover") -> cover ?: throw CarModelHttpException(404, "no cover")
                url.startsWith("/screenshots/") -> screenshots[url.substringAfterLast('/').toInt() - 1]
                else -> throw CarModelHttpException(404, "unknown $url")
            }
            output.parentFile?.mkdirs()
            output.outputStream().use { out -> bytes.toList().chunked(4).forEach { chunk -> out.write(chunk.toByteArray()); onBytes(output.length()) } }
        }
    }

    private fun installer(hub: CarModelDownloadSource = FakeHub(), move: (File, File) -> Boolean = { a, b -> a.renameTo(b) }, space: Long = Long.MAX_VALUE) =
        CommunityCarModelInstaller(store, downloads, hub, move, maxAttempts = 3, retryDelayMs = { 0L }, freeSpace = { space })

    private fun partialDir() = File(downloads, "$remoteId.partial")

    // ---- success path -------------------------------------------------------------------------

    @Test fun successfulDownloadIsPersistedWithRemoteIdMediaAndAppearsInDownloadedMods() = runBlocking {
        val model = installer().downloadAndImport(hubModel).getOrThrow()

        assertEquals(remoteId, model.remoteId)
        assertEquals("ADDON", model.manifest.installMode)
        assertEquals(InstallationPolicy.ADDON, model.manifest.installationPolicy)
        payload.forEach { (path, bytes) -> assertTrue(File(model.filesRoot, path).readBytes().contentEquals(bytes)) }
        // Media contract: library/<id>/media/cover.webp + screenshot_01.webp, absolute paths persisted.
        val home = store.libraryDir(model.id)
        assertEquals(File(home, "media/cover.webp").absolutePath, model.coverPath)
        assertTrue(File(model.coverPath!!).readBytes().contentEquals(webp))
        assertEquals(listOf(File(home, "media/screenshot_01.webp").absolutePath), model.screenshotPaths)
        assertEquals(listOf("cover", "screenshot"), model.media.map { it.role })
        assertEquals(sha(webp), model.media.first().sha256)
        assertEquals(listOf(model.id), store.state.value.downloadedModels().map { it.id })
        assertFalse(partialDir().exists())
        assertEquals(setOf("files", "media"), home.list()!!.toSet())
    }

    @Test fun detailsUiRendersTheLocalCoverOffline() = runBlocking {
        val model = installer().downloadAndImport(hubModel).getOrThrow()
        val ui = model.toDetailsUi(GamePackage.ROW)
        val cover = ui.cover as File
        assertTrue(cover.isFile)
        assertTrue(cover.absolutePath.startsWith(store.libraryDir(model.id).absolutePath))
    }

    @Test fun olderWorkerWithoutMediaFieldsFallsBackToCatalogCoverUrl() = runBlocking {
        val hub = FakeHub(mediaInPlan = false)
        val model = installer(hub).downloadAndImport(hubModel).getOrThrow()
        assertTrue(hub.fetches.contains(hubModel.coverUrl))
        assertTrue(File(model.coverPath!!).isFile)
    }

    @Test fun modelWithoutAnyMediaIsStillValid() = runBlocking {
        val hub = FakeHub(cover = null, screenshots = emptyList())
        val model = installer(hub).downloadAndImport(hubModel.copy(coverUrl = null)).getOrThrow()
        assertNull(model.coverPath)
        assertTrue(model.screenshotPaths.isEmpty())
        assertEquals(1, store.state.value.downloadedModels().size)
    }

    @Test fun brokenCoverDoesNotBlockTheModel() = runBlocking {
        val hub = FakeHub(cover = "not an image at all".toByteArray())
        val model = installer(hub).downloadAndImport(hubModel).getOrThrow()
        assertNull(model.coverPath)
        assertEquals(1, model.screenshotPaths.size)
    }

    @Test fun replaceModeIsPreserved() = runBlocking {
        val model = installer(FakeHub(manifest("REPLACE"))).downloadAndImport(hubModel).getOrThrow()
        assertEquals("REPLACE", model.manifest.installMode)
    }

    // ---- persistence ---------------------------------------------------------------------------

    @Test fun remoteIdAndCoverSurviveStateFileReload() = runBlocking {
        val model = installer().downloadAndImport(hubModel).getOrThrow()
        val restored = CarModelStore(File(temp, "store")).state.value.model(model.id)
        assertNotNull(restored)
        assertEquals(remoteId, restored!!.remoteId)
        assertEquals(model.coverPath, restored.coverPath)
        assertEquals(model.screenshotPaths, restored.screenshotPaths)
        assertEquals("ADDON", restored.manifest.installMode)
    }

    @Test fun jsonRoundTripPreservesTheWholeModel() = runBlocking {
        val model = installer().downloadAndImport(hubModel).getOrThrow()
        val decoded = CarModelState.fromJson(JSONObject(store.state.value.toJson().toString()))
        assertEquals(model, decoded.models.single())
    }

    @Test fun sameRemoteIdReplacesInsteadOfDuplicating() = runBlocking {
        val first = installer().downloadAndImport(hubModel).getOrThrow()
        val updated = payload + ("$target/extra.z" to "extra".toByteArray())
        val second = installer(FakeHub(manifest(files = updated), updated)).downloadAndImport(hubModel).getOrThrow()

        assertEquals(first.id, second.id)
        assertEquals(1, store.state.value.models.size)
        assertEquals(3, store.state.value.models.single().fileCount)
        assertEquals(setOf("files", "media"), store.libraryDir(first.id).list()!!.toSet())
    }

    @Test fun reDownloadWhoseMediaFailsKeepsThePreviousImages() = runBlocking {
        val first = installer().downloadAndImport(hubModel).getOrThrow()
        val second = installer(FakeHub(cover = null, screenshots = emptyList())).downloadAndImport(hubModel.copy(coverUrl = null)).getOrThrow()
        assertEquals(first.coverPath, second.coverPath)
        assertTrue(File(second.coverPath!!).isFile)
    }

    // ---- robustness ----------------------------------------------------------------------------

    @Test fun renameFailureFallsBackToCopy() = runBlocking {
        val model = installer(move = { _, _ -> false }).downloadAndImport(hubModel).getOrThrow()
        payload.forEach { (path, bytes) -> assertTrue(File(model.filesRoot, path).readBytes().contentEquals(bytes)) }
        assertTrue(File(model.coverPath!!).isFile)
        assertFalse(partialDir().exists())
    }

    @Test fun transientNetworkErrorsAreRetried() = runBlocking {
        val hub = FakeHub()
        hub.failNext("/files/$target/body.z", IOException("reset"), CarModelHttpException(503, "busy"))
        installer(hub).downloadAndImport(hubModel).getOrThrow()
        assertEquals(3, hub.fetches.count { it == "/files/$target/body.z" })
    }

    @Test fun truncatedTransferIsRetriedThenSucceeds() = runBlocking {
        val hub = FakeHub()
        val good = payload.getValue("$target/body.z")
        hub.served["$target/body.z"] = good.copyOf(3)
        val source = object : CarModelDownloadSource by hub {
            var calls = 0
            override suspend fun fetch(url: String, output: File, onBytes: (Long) -> Unit) {
                if (url == "/files/$target/body.z" && ++calls == 2) hub.served["$target/body.z"] = good
                hub.fetch(url, output, onBytes)
            }
        }
        installer(source).downloadAndImport(hubModel).getOrThrow()
        assertEquals(2, source.calls)
    }

    @Test fun permanentHttpErrorIsNotRetriedAndKeepsPartialForResume() = runBlocking {
        val hub = FakeHub()
        hub.failNext("/files/$target/wheels/rim.z", CarModelHttpException(403, "forbidden"))
        val error = installer(hub).downloadAndImport(hubModel).exceptionOrNull() as CarModelException
        assertEquals(CarModelErrorCode.HUB_DOWNLOAD_FAILED, error.error)
        assertTrue(error.message!!.contains("forbidden"))
        assertEquals(1, hub.fetches.count { it.endsWith("rim.z") })
        assertTrue(partialDir().isDirectory)
    }

    @Test fun interruptedDownloadResumesWithoutRefetchingVerifiedFiles() = runBlocking {
        val hub = FakeHub()
        hub.failNext("/files/$target/wheels/rim.z", IOException("1"), IOException("2"), IOException("3"))
        assertTrue(installer(hub).downloadAndImport(hubModel).isFailure)
        assertTrue(File(partialDir(), "files/$target/body.z").isFile)

        val resumed = FakeHub()
        installer(resumed).downloadAndImport(hubModel).getOrThrow()
        assertFalse(resumed.fetches.contains("/files/$target/body.z"))
        assertTrue(resumed.fetches.contains("/files/$target/wheels/rim.z"))
        assertFalse(partialDir().exists())
    }

    @Test fun cancellationPropagatesAndKeepsVerifiedFilesForResume() = runBlocking {
        val hub = FakeHub()
        hub.failNext("/files/$target/wheels/rim.z", CancellationException("user left"))
        try { installer(hub).downloadAndImport(hubModel); fail("cancellation must propagate") } catch (_: CancellationException) { }
        assertTrue(File(partialDir(), "files/$target/body.z").isFile)
        assertTrue(store.state.value.models.isEmpty())
    }

    @Test fun progressIsMonotonicAndReachesTotalEvenAcrossRetries() = runBlocking {
        val hub = FakeHub()
        hub.failNext("/files/$target/wheels/rim.z", IOException("drop"))
        val seen = mutableListOf<Pair<Long, Long>>()
        installer(hub).downloadAndImport(hubModel) { done, total -> seen += done to total }.getOrThrow()
        val total = payload.values.sumOf { it.size.toLong() }
        assertTrue(seen.zipWithNext().all { (a, b) -> b.first >= a.first })
        assertEquals(total to total, seen.last())
    }

    @Test fun notEnoughStorageFailsBeforeDownloading() = runBlocking {
        val hub = FakeHub()
        val error = installer(hub, space = 1024).downloadAndImport(hubModel).exceptionOrNull() as CarModelException
        assertEquals(CarModelErrorCode.HUB_DOWNLOAD_NO_SPACE, error.error)
        assertTrue(hub.fetches.isEmpty())
    }

    @Test fun missingFileFailsVisiblyAndDeletesTheCorruptPartial() = runBlocking {
        val partial = mapOf("$target/body.z" to payload.getValue("$target/body.z"))
        // Plan lists only body.z but the manifest needs rim.z too: a corrupt package.
        val result = installer(FakeHub(manifest(), partial)).downloadAndImport(hubModel)
        val error = result.exceptionOrNull() as CarModelException
        assertEquals(CarModelErrorCode.IMPORT_VALIDATION_FAILED, error.error)
        assertTrue(error.message!!.contains("wheels/rim.z"))
        assertTrue(store.state.value.models.isEmpty())
        assertFalse(store.state.value.activity.first().success)
        assertFalse(partialDir().exists())
        assertTrue(File(store.root, "library").listFiles().orEmpty().isEmpty())
    }

    @Test fun persistentChecksumMismatchIsRejected() = runBlocking {
        val hub = FakeHub()
        hub.served["$target/body.z"] = "BODY-bytes".toByteArray()
        val error = installer(hub).downloadAndImport(hubModel).exceptionOrNull() as CarModelException
        assertEquals(CarModelErrorCode.IMPORT_VALIDATION_FAILED, error.error)
        assertEquals(3, hub.fetches.count { it == "/files/$target/body.z" })
        assertTrue(store.state.value.models.isEmpty())
    }

    @Test fun failedReplacementKeepsThePreviousCopy() = runBlocking {
        val first = installer().downloadAndImport(hubModel).getOrThrow()
        val hub = FakeHub()
        hub.served.remove("$target/wheels/rim.z")
        assertTrue(installer(hub).downloadAndImport(hubModel).isFailure)
        assertEquals(first, store.state.value.models.single())
        payload.keys.forEach { path -> assertTrue(File(first.filesRoot, path).isFile) }
        assertTrue(File(first.coverPath!!).isFile)
    }

    @Test fun legacyDownloadFoldersAreCleanedUp() = runBlocking {
        File(downloads, remoteId).apply { mkdirs(); File(this, "junk").writeText("x") }
        File(downloads, "$remoteId.123.tmp").mkdirs()
        installer().downloadAndImport(hubModel).getOrThrow()
        assertTrue(downloads.listFiles().orEmpty().isEmpty())
    }

    @Test fun successIsReportedOnlyAfterPersistence() = runBlocking {
        var seenOnSuccess: List<LocalCarModel>? = null
        val result = installer().downloadAndImport(hubModel)
        result.onSuccess { seenOnSuccess = store.state.value.downloadedModels() }
        assertEquals(result.getOrThrow().id, seenOnSuccess!!.single().id)

        var navigatedOnFailure = false
        val hub = FakeHub().apply { failNext("/manifest", CarModelHttpException(404, "gone")) }
        installer(hub).downloadAndImport(hubModel).onSuccess { navigatedOnFailure = true }
        assertFalse(navigatedOnFailure)
    }

    // ---- media backfill -----------------------------------------------------------------------

    @Test fun refreshMediaBackfillsAModelDownloadedWithoutImages() = runBlocking {
        val bare = installer(FakeHub(cover = null, screenshots = emptyList())).downloadAndImport(hubModel.copy(coverUrl = null)).getOrThrow()
        assertNull(bare.coverPath)
        val updated = installer().refreshMedia(bare.id) { hubModel }.getOrThrow()
        assertEquals(File(store.libraryDir(bare.id), "media/cover.webp").absolutePath, updated.coverPath)
        assertEquals(updated, store.state.value.model(bare.id))
        payload.keys.forEach { assertTrue(File(updated.filesRoot, it).isFile) }
    }

    @Test fun refreshMediaRunsOncePerModelPerProcess() = runBlocking {
        val bare = installer(FakeHub(cover = null, screenshots = emptyList())).downloadAndImport(hubModel.copy(coverUrl = null)).getOrThrow()
        val hub = FakeHub(cover = null, screenshots = emptyList())
        val backfill = installer(hub)
        backfill.refreshMedia(bare.id) { hubModel.copy(coverUrl = null) }
        backfill.refreshMedia(bare.id) { hubModel.copy(coverUrl = null) }
        assertEquals(1, hub.planCalls)
    }

    @Test fun downloadedModsFilterShowsOnlyRemoteModels() {
        val base = LocalCarModel("local", "Local", "", "", "", "", emptyList(), manifest(), "/x", null, emptyList(), emptyList(), 0)
        val state = CarModelState(models = listOf(base, base.copy(id = "remote", remoteId = remoteId)))
        assertEquals(listOf("remote"), state.downloadedModels().map { it.id })
    }
}
