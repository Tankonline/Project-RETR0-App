package com.retr0.projectretr0.data.storage

import android.content.Context
import android.os.StatFs
import coil.annotation.ExperimentalCoilApi
import coil.imageLoader
import com.retr0.projectretr0.IRetr0UserService
import com.retr0.projectretr0.data.carModels.CarModelErrorCode
import com.retr0.projectretr0.data.carModels.CarModelLog
import com.retr0.projectretr0.data.carModels.local.CarModelPaths
import com.retr0.projectretr0.installer.persistence.DownloadDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import kotlinx.coroutines.sync.withLock
import java.io.File
import java.nio.file.Files
import kotlin.coroutines.coroutineContext

enum class StorageCategoryId { BACKUPS, LIBRARY, DOWNLOADS, MUSIC, MEDIA, CACHE }

/** [bytes] is null when the size could not be read (e.g. Shizuku disconnected); the UI shows "—". */
data class StorageCategory(val id: StorageCategoryId, val label: String, val bytes: Long?, val dirs: List<String>)

data class StorageSnapshot(
    val categories: List<StorageCategory>,
    /** Sum of the categories that could be read. */
    val appTotalBytes: Long,
    val deviceFreeBytes: Long,
    val deviceTotalBytes: Long,
    /** RR3 data + obb for the active package; null when unreadable. */
    val gameBytes: Long?,
) {
    fun bytes(id: StorageCategoryId): Long? = categories.firstOrNull { it.id == id }?.bytes
}

/** Every folder Project RETR0 writes to, taken from the code that writes there. */
class StoragePaths(context: Context) {
    private val app = context.applicationContext
    val filesDir: File = app.filesDir
    val cacheDir: File = app.cacheDir
    val externalCacheDir: File? = app.externalCacheDir
    /** Same folder MainActivity measures for the System Status free-space value. */
    val freeSpaceProbe: File get() = externalCacheDir ?: cacheDir

    // Shared storage, written through the Shizuku service (shell-owned, so measured through it too).
    val documentsRoot = "/storage/emulated/0/Documents/ProjectRETR0"
    val carModelBackups = CarModelPaths().backupRoot              // <pkg>/<veh> + addons/<pkg>/<veh>
    val skinBackups = "$documentsRoot/SkinBackups"                  // SkinModsScreen → SkinTransactionCoordinator
    val saveBackups = "$documentsRoot/Backups"                      // Retr0Installer.SAVE_HUB_PATH
    val musicBackups = "$documentsRoot/MusicBackups"                // Retr0Installer.MUSIC_HUB_PATH
    val musicLibrary = "$documentsRoot/MusicLibrary"                // Retr0Installer.MUSIC_LIBRARY_PATH

    // App-private, readable directly.
    val carModelsLibrary = File(filesDir, "car_models/library")      // CarModelStore.libraryDir; <id>/media holds covers/screenshots
    val carModelsStaging = File(filesDir, "car_models/staging")      // CarModelStore.stagingDir
    val skinsLibrary = File(filesDir, "skins")                       // LocalSkinRepository / SkinDownloadManager; */screenshots = media
    val profilePicture = File(filesDir, "pfp")                       // SkinPFPStore

    /** Resumable game-install downloads (Retr0Installer / MainActivity.clearInstallerTempFiles). */
    val installDownloads = File(freeSpaceProbe, "retr0_install")
    /** Single-file download leftovers Retr0Installer writes straight into externalCacheDir. */
    val downloadTemps: List<File> = externalCacheDir?.let { ext ->
        listOf("community_download.tmp", "music_download.tmp", "youtube_download.mp3").map { File(ext, it) }
    }.orEmpty()

    /** Kept when clearing: Cronet's live HTTP cache, and Coil's disk cache (cleared through Coil instead). */
    val protectedCacheChildren = setOf("cronet_cache", "image_cache")

    fun gameDirs(gamePackage: String) = listOf("/storage/emulated/0/Android/data/$gamePackage", "/storage/emulated/0/Android/obb/$gamePackage")
}

/** Measures what Project RETR0 stores on the device and clears the safe, re-creatable parts. */
internal class StorageSnapshotCache<K, V> {
    private val values = mutableMapOf<K, V>()

    fun get(key: K): V? = values[key]
    fun put(key: K, value: V) { values[key] = value }
    fun clear() = values.clear()
}

interface StorageDataSource {
    suspend fun snapshot(service: IRetr0UserService?, gamePackage: String): StorageSnapshot
    fun invalidate()
    suspend fun clearCache(): Result<Long>
    suspend fun clearDownloads(): Result<Long>
}

open class StorageRepository(private val context: Context, val paths: StoragePaths = StoragePaths(context)) : StorageDataSource {
    private val snapshotLock = kotlinx.coroutines.sync.Mutex()
    private val snapshotCache = StorageSnapshotCache<String, StorageSnapshot>()

    override fun invalidate() {
        snapshotCache.clear()
    }

    override suspend fun snapshot(service: IRetr0UserService?, gamePackage: String): StorageSnapshot {
        val key = "${System.identityHashCode(service)}:$gamePackage"
        snapshotCache.get(key)?.let { return it }
        return snapshotLock.withLock {
            snapshotCache.get(key) ?: snapshotUncached(service, gamePackage).also { snapshotCache.put(key, it) }
        }
    }

    private suspend fun snapshotUncached(service: IRetr0UserService?, gamePackage: String): StorageSnapshot = withContext(Dispatchers.IO) {
        val downloadPaths = (listOf(paths.installDownloads) + paths.downloadTemps).map { it.absolutePath }.toSet()

        // Library and media share folders: split them file by file so nothing is counted twice.
        var library = 0L; var media = 0L; var libraryFailed = false
        listOf(paths.carModelsLibrary, paths.skinsLibrary).forEach { root ->
            runCatching {
                walk(root) { file ->
                    val rel = file.relativeTo(root).invariantSeparatorsPath
                    val isMedia = if (root == paths.carModelsLibrary) rel.split('/').getOrNull(1) == "media" else rel.split('/').dropLast(1).contains("screenshots")
                    if (isMedia) media += file.length() else library += file.length()
                }
            }.onFailure { libraryFailed = true; warn("LIBRARY", root.path, it) }
        }
        val pfp = local("MEDIA", paths.profilePicture)

        val cacheRoots = listOfNotNull(paths.carModelsStaging, paths.cacheDir, paths.externalCacheDir).distinct()
        var cache: Long? = 0L
        cacheRoots.forEach { root ->
            val size = local("CACHE", root) { it.absolutePath in downloadPaths }
            cache = if (size == null || cache == null) null else cache!! + size
        }
        val downloads = sumKnown((listOf(paths.installDownloads) + paths.downloadTemps).map { local("DOWNLOADS", it) })

        val backupDirs = listOf(paths.carModelBackups, paths.skinBackups, paths.saveBackups, paths.musicBackups)
        val backups = sumKnown(backupDirs.map { remote("BACKUPS", service, it) })
        val music = remote("MUSIC", service, paths.musicLibrary)
        val game = sumKnown(paths.gameDirs(gamePackage).map { remote("GAME", service, it) })

        val categories = listOf(
            StorageCategory(StorageCategoryId.BACKUPS, "Backups", backups, backupDirs),
            StorageCategory(StorageCategoryId.LIBRARY, "Library", library.takeUnless { libraryFailed }, listOf(paths.carModelsLibrary.path, paths.skinsLibrary.path)),
            StorageCategory(StorageCategoryId.DOWNLOADS, "Downloads", downloads, (listOf(paths.installDownloads) + paths.downloadTemps).map { it.path }),
            StorageCategory(StorageCategoryId.MUSIC, "Music", music, listOf(paths.musicLibrary)),
            StorageCategory(StorageCategoryId.MEDIA, "Media", if (libraryFailed || pfp == null) null else media + pfp, listOf("${paths.carModelsLibrary.path}/*/media", "${paths.skinsLibrary.path}/**/screenshots", paths.profilePicture.path)),
            StorageCategory(StorageCategoryId.CACHE, "Cache", cache, cacheRoots.map { it.path }),
        )
        val (free, total) = deviceSpace()
        StorageSnapshot(categories, categories.sumOf { it.bytes ?: 0L }, free, total, game).also {
            CarModelLog.info("CM-STORAGE-OK", "scan app=${it.appTotalBytes} game=${it.gameBytes} free=$free total=$total unread=${categories.filter { c -> c.bytes == null }.map { c -> c.id }}")
        }
    }

    /** Deletes car-model staging, app cache folders and Coil's image cache. Returns bytes freed. */
    @OptIn(ExperimentalCoilApi::class)
    override suspend fun clearCache(): Result<Long> = withContext(Dispatchers.IO) {
        val keep = (listOf(paths.installDownloads) + paths.downloadTemps).map { it.absolutePath }.toSet()
        var freed = 0L; var failed = 0
        fun remove(file: File) {
            val size = runCatching { file.walkTopDown().filter { it.isFile }.sumOf { it.length() } }.getOrDefault(0L)
            if (file.deleteRecursively()) freed += size else failed++
        }
        remove(paths.carModelsStaging)
        paths.carModelsStaging.mkdirs()
        listOfNotNull(paths.cacheDir, paths.externalCacheDir).distinct().forEach { root ->
            root.listFiles()?.forEach { child ->
                coroutineContext.ensureActive()
                if (child.name !in paths.protectedCacheChildren && child.absolutePath !in keep) remove(child)
            }
        }
        val loader = context.imageLoader
        val coilBefore = loader.diskCache?.size ?: 0L
        runCatching { loader.diskCache?.clear(); loader.memoryCache?.clear() }.onSuccess { freed += coilBefore }.onFailure { failed++ }
        finish("cache", freed, failed)
    }

    /** Deletes the resumable install downloads (and their chunk records) plus download leftovers. Returns bytes freed. */
    override suspend fun clearDownloads(): Result<Long> = withContext(Dispatchers.IO) {
        var freed = 0L; var failed = 0
        (listOf(paths.installDownloads) + paths.downloadTemps).forEach { file ->
            val size = runCatching { file.walkTopDown().filter { it.isFile }.sumOf { it.length() } }.getOrDefault(0L)
            if (file.deleteRecursively()) freed += size else failed++
        }
        paths.installDownloads.mkdirs()
        // Same as MainActivity.clearInstallerTempFiles: chunk records must not outlive their files.
        runCatching { DownloadDatabase.getDatabase(context.applicationContext).chunkDao().clearAllChunks() }.onFailure { failed++ }
        finish("downloads", freed, failed)
    }

    private fun finish(what: String, freed: Long, failed: Int): Result<Long> =
        if (failed == 0) Result.success(freed).also { CarModelLog.info("CM-STORAGE-OK", "clear $what freed=$freed") }
        else {
            CarModelLog.fail(CarModelErrorCode.STORAGE_CLEAR_FAILED, "clear $what freed=$freed failedItems=$failed")
            Result.failure(IllegalStateException("${CarModelErrorCode.STORAGE_CLEAR_FAILED.userMessage} (${formatFreed(freed)} freed)"))
        }

    private fun formatFreed(bytes: Long) = com.retr0.projectretr0.installer.StorageUtils.formatSize(bytes)

    /** Free/total of the same volume MainActivity uses for the status row, so both agree. */
    private fun deviceSpace(): Pair<Long, Long> = runCatching {
        val stat = StatFs(paths.freeSpaceProbe.absolutePath)
        stat.availableBlocksLong * stat.blockSizeLong to stat.blockCountLong * stat.blockSizeLong
    }.getOrElse { warn("DEVICE", paths.freeSpaceProbe.path, it); -1L to -1L }

    private suspend fun local(category: String, root: File, skip: (File) -> Boolean = { false }): Long? = runCatching {
        var total = 0L
        walk(root, skip) { total += it.length() }
        total
    }.onFailure { warn(category, root.path, it) }.getOrNull()

    private fun remote(category: String, service: IRetr0UserService?, path: String): Long? {
        if (service == null) return null
        return runCatching { service.getDirectoryStats(path)[0] }.onFailure { warn(category, path, it) }.getOrNull()
    }

    /** Recursive walk over regular files that checks for cancellation and never follows symlinked folders. */
    private suspend fun walk(root: File, skip: (File) -> Boolean = { false }, onFile: (File) -> Unit) {
        if (skip(root)) return
        if (root.isFile) { onFile(root); return }
        val stack = ArrayDeque<File>().apply { if (root.isDirectory) add(root) }
        var seen = 0
        while (stack.isNotEmpty()) {
            val dir = stack.removeLast()
            dir.listFiles()?.forEach { child ->
                if (++seen % 256 == 0) coroutineContext.ensureActive()
                when {
                    skip(child) -> Unit
                    child.isFile -> onFile(child)
                    child.isDirectory && !Files.isSymbolicLink(child.toPath()) -> stack.add(child)
                }
            }
        }
    }

    private fun sumKnown(values: List<Long?>): Long? = if (values.any { it == null }) null else values.sumOf { it!! }

    private fun warn(category: String, path: String, error: Throwable) {
        if (error is kotlinx.coroutines.CancellationException) throw error
        CarModelLog.fail(CarModelErrorCode.STORAGE_SCAN_FAILED, "category=$category path=$path cause=${error.javaClass.simpleName}")
    }
}
