package com.retr0.projectretr0

import android.os.ParcelFileDescriptor
import android.os.SystemClock
import android.util.Log
import com.retr0.projectretr0.installer.graphics.GraphicsPathValidator
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.RandomAccessFile
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.security.MessageDigest

/**
 * Shizuku UserService implementation.
 */
class Retr0UserService : IRetr0UserService.Stub() {

    init {
        try {
            System.loadLibrary("sonic-extractor")
            Log.i("RETR0_INSTALLER_SVC", "Native library loaded via System.loadLibrary")
        } catch (e: Throwable) {
            Log.e("RETR0_INSTALLER_SVC", "CRITICAL: Native library load failed", e)
        }
    }

    private external fun nativeExtractPrivileged(fd: Int, destPath: String): Int

    override fun nativeExtract(pfd: ParcelFileDescriptor, destPath: String): Int {
        Log.i("RETR0_INSTALLER_SVC", "code=ASSET_INSTALL_I016 stage=service_extract_requested destination=$destPath")
        if (!isPathAllowed(destPath)) {
            Log.e("RETR0_INSTALLER_SVC", "code=ASSET_INSTALL_E008 stage=destination_blocked destination=$destPath")
            return -1
        }
        
        return try {
            pfd.use {
                Log.i("RETR0_INSTALLER_SVC", "code=ASSET_INSTALL_I017 stage=native_call_start fd=${it.fd}")
                val result = nativeExtractPrivileged(it.fd, destPath)
                Log.i("RETR0_INSTALLER_SVC", "code=ASSET_INSTALL_I018 stage=native_call_result result=$result")
                result
            }
        } catch (e: Throwable) {
            Log.e("RETR0_INSTALLER_SVC", "Privileged native extraction failed with exception", e)
            -1
        }
    }

    private companion object {
        private const val TAG = "RETR0_INSTALLER_SVC"
        private const val MUSIC_LIBRARY_TAG = "RETR0_MUSIC_LIBRARY"
        
        private val ALLOWED_ROOTS = listOf(
            "/storage/emulated/0/Android/data/com.ea.games.r3_row",
            "/storage/emulated/0/Android/data/com.ea.games.r3_na",
            "/storage/emulated/0/Documents/ProjectRETR0",
            "/storage/emulated/0/Android/obb/com.ea.games.r3_row",
            "/storage/emulated/0/Android/obb/com.ea.games.r3_na",
            "/storage/emulated/0/Download",
        )
    }

    private fun isPathAllowed(path: String): Boolean {
        val candidate = try {
            File(path).canonicalFile.toPath()
        } catch (_: Exception) {
            return false
        }
        val appRoot = File(System.getProperty("user.dir"), "com.retr0.projectretr0")
            .canonicalFile
            .toPath()
        if ((candidate == appRoot) || candidate.startsWith(appRoot)) return true

        return ALLOWED_ROOTS.any { rootPath ->
            val root = File(rootPath).canonicalFile.toPath()
            (candidate == root) || candidate.startsWith(root)
        }
    }

    override fun ping(): Boolean = true

    override fun exists(path: String): Boolean {
        return isPathAllowed(path) && File(path).exists()
    }

    override fun isDirectory(path: String): Boolean {
        return isPathAllowed(path) && File(path).isDirectory
    }

    override fun createDirectory(path: String): Boolean {
        if (!isPathAllowed(path)) {
            Log.e(TAG, "Blocked unauthorized createDirectory: $path")
            return false
        }
        val file = File(path)
        if (file.exists()) return file.isDirectory
        return file.mkdirs()
    }

    override fun delete(path: String): Boolean {
        if (!isPathAllowed(path)) {
            Log.e(TAG, "Blocked unauthorized delete: $path")
            return false
        }
        val file = File(path)
        if (!file.exists()) return true
        
        return if (file.isDirectory) {
            // Optimization for Android 8.1/Older kernels:
            // rm -rf is often much faster for 10GB+ folders than JVM recursive deletion.
            try {
                val process = Runtime.getRuntime().exec(arrayOf("rm", "-rf", path))
                process.waitFor() == 0
            } catch (_: Exception) {
                file.deleteRecursively()
            }
        } else {
            file.delete()
        }
    }

    override fun copyFile(source: String, destination: String): Boolean {
        Log.i(TAG, "copyFile: $source -> $destination")
        if (!isPathAllowed(source) || !isPathAllowed(destination)) {
            Log.e(TAG, "Blocked unauthorized copy: $source -> $destination")
            return false
        }
        return try {
            val srcFile = File(source)
            if (!srcFile.exists()) {
                Log.e(TAG, "copyFile failed: Source not found: $source")
                return false
            }

            // Ensure parent directory exists
            val dstFile = File(destination)
            dstFile.parentFile?.mkdirs()

            // Native copy for better reliability and performance
            try {
                val process = Runtime.getRuntime().exec(arrayOf("cp", source, destination))
                if (process.waitFor() == 0) return true
            } catch (_: Exception) {}

            // Fallback to JVM copy
            FileInputStream(srcFile).use { input ->
                FileOutputStream(dstFile).use { output ->
                    input.copyTo(output)
                }
            }
            true
        } catch (e: Exception) {
            Log.e(TAG, "copyFile failed: $source -> $destination", e)
            false
        }
    }

    override fun copyDirectory(source: String, destination: String): Boolean {
        if (!isPathAllowed(source) || !isPathAllowed(destination)) {
            Log.e(TAG, "Blocked unauthorized copyDirectory: $source -> $destination")
            return false
        }
        val srcDir = File(source)
        if (!srcDir.exists() || !srcDir.isDirectory) return false
        
        // Plain recursive copy: 'cp -a' would also copy owner/mode/timestamps, which can
        // leave files the game can't rewrite under Android/data.
        val nativeOk = try {
            Runtime.getRuntime().exec(arrayOf("cp", "-r", "$source/.", destination)).waitFor() == 0
        } catch (_: Exception) {
            false
        }
        if (nativeOk) return true
        return try {
            srcDir.copyRecursively(File(destination), overwrite = true)
        } catch (e: Exception) {
            Log.e(TAG, "copyDirectory failed: $source -> $destination", e)
            false
        }
    }

    override fun move(source: String, destination: String): Boolean {
        val startedAt = SystemClock.elapsedRealtime()
        val sourceAllowed = isPathAllowed(source)
        val destinationAllowed = isPathAllowed(destination)
        if (!sourceAllowed || !destinationAllowed) {
            Log.e(MUSIC_LIBRARY_TAG, "code=ML_USVC_MOVE_BLOCKED stage=path_validation result=false sourceAllowed=$sourceAllowed targetAllowed=$destinationAllowed sourceExt=${safeExtension(source)} targetExt=${safeExtension(destination)} elapsedMs=${SystemClock.elapsedRealtime() - startedAt}")
            return false
        }
        return try {
            val srcFile = File(source)
            val dstFile = File(destination)
            val result = srcFile.exists() && dstFile.parentFile?.mkdirsOrExists() != false && srcFile.renameTo(dstFile)
            Log.i(MUSIC_LIBRARY_TAG, "code=ML_USVC_MOVE stage=move result=$result sourceExt=${safeExtension(source)} targetExt=${safeExtension(destination)} elapsedMs=${SystemClock.elapsedRealtime() - startedAt}")
            result
        } catch (error: Exception) {
            Log.e(MUSIC_LIBRARY_TAG, "code=ML_USVC_MOVE_EXCEPTION stage=move result=false sourceExt=${safeExtension(source)} targetExt=${safeExtension(destination)} elapsedMs=${SystemClock.elapsedRealtime() - startedAt} exception=${error.javaClass.simpleName} message=${safeServiceMessage(error)}")
            false
        }
    }

    override fun importFile(pfd: ParcelFileDescriptor, destination: String): Boolean {
        val startedAt = SystemClock.elapsedRealtime()
        val allowed = isPathAllowed(destination)
        if (!allowed) {
            Log.e(MUSIC_LIBRARY_TAG, "code=ML_USVC_IMPORT_BLOCKED stage=path_validation result=false targetAllowed=false targetExt=${safeExtension(destination)} elapsedMs=${SystemClock.elapsedRealtime() - startedAt}")
            return false
        }
        return try {
            val dstFile = File(destination)
            dstFile.parentFile?.mkdirs()
            val tempFile = File(dstFile.parentFile, ".${dstFile.name}.retr0-partial-${System.nanoTime()}")

            ParcelFileDescriptor.AutoCloseInputStream(pfd).use { input ->
                FileOutputStream(tempFile).use { output ->
                    input.copyTo(output)
                    output.fd.sync()
                }
            }
            val result = if (!tempFile.renameTo(dstFile)) {
                tempFile.delete()
                false
            } else true
            Log.i(MUSIC_LIBRARY_TAG, "code=ML_USVC_IMPORT stage=import result=$result targetExt=${safeExtension(destination)} elapsedMs=${SystemClock.elapsedRealtime() - startedAt}")
            result
        } catch (error: Exception) {
            Log.e(MUSIC_LIBRARY_TAG, "code=ML_USVC_IMPORT_EXCEPTION stage=import result=false targetExt=${safeExtension(destination)} elapsedMs=${SystemClock.elapsedRealtime() - startedAt} exception=${error.javaClass.simpleName} message=${safeServiceMessage(error)}")
            false
        }
    }

    override fun sha256File(path: String): String {
        if (!isPathAllowed(path)) return ""
        return runCatching { hashFile(File(path)) }.getOrDefault("")
    }

    override fun createImmutableSkinBackup(targetPath: String, backupPath: String, expectedSha256: String): Boolean {
        if (!isValidSkinTarget(targetPath) || !isPathAllowed(backupPath) || !expectedSha256.isSha256()) return false
        return runCatching {
            val target = File(targetPath)
            val backup = File(backupPath)
            if (!target.isFile || Files.isSymbolicLink(target.toPath()) || hashFile(target) != expectedSha256.lowercase()) return false
            if (backup.exists()) return backup.isFile && !Files.isSymbolicLink(backup.toPath()) && hashFile(backup) == expectedSha256.lowercase()
            backup.parentFile?.mkdirs()
            val temp = File(backup.parentFile, ".${backup.name}.partial-${System.nanoTime()}")
            FileInputStream(target).use { input -> FileOutputStream(temp).use { output -> input.copyTo(output); output.fd.sync() } }
            if (hashFile(temp) != expectedSha256.lowercase()) { temp.delete(); return false }
            Files.move(temp.toPath(), backup.toPath(), StandardCopyOption.ATOMIC_MOVE)
            hashFile(backup) == expectedSha256.lowercase()
        }.getOrElse { Log.e(TAG, "Immutable skin backup failed", it); false }
    }

    override fun atomicImportSkin(pfd: ParcelFileDescriptor, targetPath: String, expectedSha256: String, expectedSize: Long): Boolean {
        if (!isValidSkinTarget(targetPath) || !expectedSha256.isSha256() || expectedSize !in 1..(5L * 1024L * 1024L)) return false
        return runCatching {
            val target = File(targetPath)
            if (!target.isFile || Files.isSymbolicLink(target.toPath())) return false
            val temp = File(target.parentFile, ".${target.name}.retr0-skin-${System.nanoTime()}")
            var total = 0L
            ParcelFileDescriptor.AutoCloseInputStream(pfd).use { input ->
                FileOutputStream(temp).use { output ->
                    val buffer = ByteArray(32 * 1024)
                    while (true) { val count = input.read(buffer); if (count < 0) break; total += count; if (total > expectedSize) { temp.delete(); return false }; output.write(buffer, 0, count) }
                    output.fd.sync()
                }
            }
            if (total != expectedSize || hashFile(temp) != expectedSha256.lowercase()) { temp.delete(); return false }
            Files.move(temp.toPath(), target.toPath(), StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING)
            hashFile(target) == expectedSha256.lowercase()
        }.getOrElse { Log.e(TAG, "Atomic skin import failed", it); false }
    }

    override fun restoreSkinBackup(backupPath: String, targetPath: String, expectedBackupSha256: String): Boolean {
        if (!isPathAllowed(backupPath) || !isValidSkinTarget(targetPath) || !expectedBackupSha256.isSha256()) return false
        return runCatching {
            val backup = File(backupPath)
            val target = File(targetPath)
            if (!backup.isFile || Files.isSymbolicLink(backup.toPath()) || hashFile(backup) != expectedBackupSha256.lowercase()) return false
            val temp = File(target.parentFile, ".${target.name}.retr0-restore-${System.nanoTime()}")
            FileInputStream(backup).use { input -> FileOutputStream(temp).use { output -> input.copyTo(output); output.fd.sync() } }
            if (hashFile(temp) != expectedBackupSha256.lowercase()) { temp.delete(); return false }
            Files.move(temp.toPath(), target.toPath(), StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING)
            hashFile(target) == expectedBackupSha256.lowercase()
        }.getOrElse { Log.e(TAG, "Skin restore failed", it); false }
    }

    // ------------------------------------------------------------------ Graphics Hub
    // Every reply is "OK ..." or "FAIL stage=<stage> reason=<reason> ..." so the app can log the
    // exact root cause. Lines are also logged here under RETR0_GRAPHICS (marked "svc").

    /** Why a graphics path is not acceptable, or null when it is. */
    private fun graphicsPathProblem(path: String, allowTemp: Boolean): String? {
        if (!isPathAllowed(path)) return "path_not_allowed"
        val matches = GraphicsPathValidator.isTarget(path) || (allowTemp && GraphicsPathValidator.isTempFile(path))
        if (!matches) return "not_a_graphics_path"
        val file = File(path)
        val parent = file.parentFile ?: return "no_parent"
        if (Files.isSymbolicLink(parent.toPath())) return "parent_is_symlink"
        if (!parent.exists()) return "parent_missing"
        if (!parent.isDirectory) return "parent_not_directory"
        if (Files.isSymbolicLink(file.toPath())) return "target_is_symlink"
        if (file.isDirectory) return "target_is_directory"
        return null
    }

    /** "uid=.. gid=.. mode=.." for a path, using lstat so symlinks are not followed. */
    private fun statInfo(file: File): String = try {
        val st = android.system.Os.lstat(file.path)
        "uid=${st.st_uid} gid=${st.st_gid} mode=${Integer.toOctalString(st.st_mode and 0xFFF)}"
    } catch (e: Exception) {
        "stat_failed=${e.javaClass.simpleName}"
    }

    private fun gfxLog(error: Boolean, code: String, text: String) {
        val line = "code=$code svc uid=${android.os.Process.myUid()} $text"
        if (error) Log.e(GraphicsPathValidator.LOG_TAG, line) else Log.i(GraphicsPathValidator.LOG_TAG, line)
    }

    private fun quote(text: String?): String = "\"" + text.orEmpty().replace('"', '\'').replace('\n', ' ') + "\""

    override fun getGraphicsApiVersion(): Int = GraphicsPathValidator.SERVICE_API

    override fun publishGraphicsFile(sourcePath: String, targetPath: String, expectedSha256: String): String {
        val startedAt = SystemClock.elapsedRealtime()
        val target = File(targetPath)
        val name = "${target.parentFile?.name}/${target.name}"
        val temp = File(target.parentFile, GraphicsPathValidator.tempNameFor(target.name, System.nanoTime()))
        fun fail(stage: String, reason: String, extra: String = ""): String {
            val parent = target.parentFile
            val folder = "folder[${parent?.let(::statInfo)} canWrite=${parent?.canWrite()} freeMB=${(parent?.usableSpace ?: 0L) / (1024 * 1024)}]"
            val targetInfo = "targetFile[exists=${target.exists()}${if (target.exists()) " " + statInfo(target) else ""}]"
            val reply = "FAIL stage=$stage reason=$reason${if (extra.isBlank()) "" else " $extra"} target=$name $folder $targetInfo ms=${SystemClock.elapsedRealtime() - startedAt}"
            gfxLog(true, "GFX_S031", reply)
            return reply
        }
        graphicsPathProblem(targetPath, allowTemp = false)?.let { return fail("validate_target", it) }
        if (!isPathAllowed(sourcePath)) return fail("validate_source", "source_not_allowed", "source=${quote(sourcePath)}")
        if (!expectedSha256.isSha256()) return fail("validate_args", "bad_expected_sha")
        val expected = expectedSha256.lowercase()
        val source = File(sourcePath)
        try {
            if (!source.exists()) return fail("validate_source", "source_missing", "source=${source.name}")
            if (Files.isSymbolicLink(source.toPath())) return fail("validate_source", "source_is_symlink", "source=${source.name}")
            if (!source.isFile) return fail("validate_source", "source_not_file", "source=${source.name}")
            val sourceHash = try {
                hashFile(source)
            } catch (e: Exception) {
                return fail("read_source", "${e.javaClass.simpleName}: ${quote(e.message)}", "source=${source.name}")
            }
            if (sourceHash != expected) {
                return fail("validate_source", "source_hash_mismatch", "expected=${expected.take(8)} actual=${sourceHash.take(8)} bytes=${source.length()}")
            }
            try {
                FileInputStream(source).use { input -> FileOutputStream(temp).use { output -> input.copyTo(output); output.fd.sync() } }
            } catch (e: Exception) {
                return fail("write_temp", "${e.javaClass.simpleName}: ${quote(e.message)}", "temp=${temp.name} tempExists=${temp.exists()} tempBytes=${temp.length()}")
            }
            val tempHash = runCatching { hashFile(temp) }.getOrDefault("")
            if (tempHash != expected) {
                return fail("verify_temp", "temp_hash_mismatch", "expected=${expected.take(8)} actual=${tempHash.take(8).ifEmpty { "none" }} sourceBytes=${source.length()} tempBytes=${temp.length()}")
            }
            try {
                Files.move(temp.toPath(), target.toPath(), StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING)
            } catch (e: Exception) {
                return fail("rename", "${e.javaClass.simpleName}: ${quote(e.message)}", "temp=${temp.name} tempExists=${temp.exists()}")
            }
            val finalHash = runCatching { hashFile(target) }.getOrDefault("")
            if (finalHash != expected) {
                return fail("verify_target", "target_hash_mismatch", "expected=${expected.take(8)} actual=${finalHash.take(8).ifEmpty { "none" }}")
            }
            val reply = "OK bytes=${target.length()} ${statInfo(target)} ms=${SystemClock.elapsedRealtime() - startedAt}"
            gfxLog(false, "GFX_S030", "target=$name $reply")
            return reply
        } catch (e: Exception) {
            return fail("unexpected", "${e.javaClass.simpleName}: ${quote(e.message)}")
        } finally {
            if (temp.exists()) temp.delete()
        }
    }

    override fun deleteGraphicsFile(targetPath: String): String {
        val file = File(targetPath)
        val name = "${file.parentFile?.name}/${file.name}"
        fun fail(stage: String, reason: String): String {
            val reply = "FAIL stage=$stage reason=$reason target=$name exists=${file.exists()}${if (file.exists()) " " + statInfo(file) else ""}"
            gfxLog(true, "GFX_S033", reply)
            return reply
        }
        graphicsPathProblem(targetPath, allowTemp = true)?.let { return fail("validate_target", it) }
        if (!file.exists()) return "OK already_absent"
        if (!file.isFile) return fail("validate_target", "not_a_regular_file")
        return try {
            if (file.delete()) {
                gfxLog(false, "GFX_S032", "target=$name deleted=true")
                "OK deleted"
            } else {
                fail("delete", "delete_returned_false folder[${file.parentFile?.let(::statInfo)} canWrite=${file.parentFile?.canWrite()}]")
            }
        } catch (e: Exception) {
            fail("delete", "${e.javaClass.simpleName}: ${quote(e.message)}")
        }
    }

    override fun graphicsDiagnostics(packageRoot: String): String {
        val allowedRoots = listOf("/storage/emulated/0/Android/data/com.ea.games.r3_row", "/storage/emulated/0/Android/data/com.ea.games.r3_na")
        if (packageRoot !in allowedRoots) return "diagnostics=refused"
        fun dir(rel: String): String {
            val f = File(packageRoot, rel)
            if (!f.exists()) return "$rel[missing]"
            val count = f.list()?.size ?: -1
            return "$rel[${statInfo(f)} canWrite=${f.canWrite()} entries=$count symlink=${Files.isSymbolicLink(f.toPath())}]"
        }
        val selinux = runCatching { File("/proc/self/attr/current").readText().trim().trim('\u0000') }.getOrDefault("?")
        val free = File(packageRoot).usableSpace / (1024 * 1024)
        return "svcUid=${android.os.Process.myUid()} selinux=$selinux sdk=${android.os.Build.VERSION.SDK_INT} freeMB=$free " +
            listOf("files/.depot/eds", "files/.depot/feats", "files/apk/res/eds", "files/apk/res/feats").joinToString(" ") { dir(it) }
    }

    private fun isValidSkinTarget(path: String): Boolean {
        if (!isPathAllowed(path)) return false
        val normalized = path.replace('\\', '/')
        return Regex("^/storage/emulated/0/Android/data/com\\.ea\\.games\\.r3_(?:row|na)/files/\\.depot/vehicles/[a-zA-Z0-9_-]+/livery/[a-zA-Z0-9_() .-]+\\.dds\\.z$").matches(normalized)
    }

    private fun hashFile(file: File): String {
        val digest = MessageDigest.getInstance("SHA-256")
        FileInputStream(file).use { input ->
            val buffer = ByteArray(32 * 1024)
            while (true) { val count = input.read(buffer); if (count < 0) break; digest.update(buffer, 0, count) }
        }
        return digest.digest().joinToString("") { "%02x".format(it) }
    }

    private fun String.isSha256() = matches(Regex("[0-9a-fA-F]{64}"))

    override fun exportFile(source: String, pfd: ParcelFileDescriptor): Boolean {
        if (!isPathAllowed(source)) return false
        return try {
            val srcFile = File(source)
            if (!srcFile.exists()) return false

            FileInputStream(srcFile).use { input ->
                ParcelFileDescriptor.AutoCloseOutputStream(pfd).use { output ->
                    input.copyTo(output)
                }
            }
            true
        } catch (e: Exception) {
            Log.e(TAG, "exportFile failed: $source", e)
            false
        }
    }

    override fun getFileSize(path: String): Long {
        if (!isPathAllowed(path)) return -1L
        val file = File(path)
        return if (file.exists() && file.isFile) file.length() else -1L
    }

    override fun getLastModified(path: String): Long {
        if (!isPathAllowed(path)) return 0L
        val file = File(path)
        return if (file.exists()) file.lastModified() else 0L
    }

    override fun listFiles(path: String): Array<String>? {
        if (!isPathAllowed(path)) return null
        val file = File(path)
        return if (file.exists() && file.isDirectory) file.list() else null
    }

    override fun scanSkinTargets(packageRoot: String, vehiclesRoot: String, importedFileName: String, maxDirectories: Int, maxEntriesPerDirectory: Int): Array<String> {
        val startedAt = SystemClock.elapsedRealtime()
        Log.i(TAG, "code=SMART_MATCH_SERVICE_ROOT_VALIDATION event=START root=$packageRoot")
        val boundedDirectories = maxDirectories.coerceIn(0, 2_000)
        val boundedEntries = maxEntriesPerDirectory.coerceIn(0, 50_000)
        if (packageRoot !in setOf("/storage/emulated/0/Android/data/com.ea.games.r3_row", "/storage/emulated/0/Android/data/com.ea.games.r3_na")) {
            Log.e(TAG, "code=INVALID_ROOT event=SMART_MATCH_SERVICE_ROOT_VALIDATION")
            return emptyArray()
        }
        val rootFile = File(packageRoot)
        if (!rootFile.exists()) { Log.e(TAG, "code=MISSING_ROOT event=SMART_MATCH_SERVICE_ROOT_VALIDATION"); return emptyArray() }
        if (!rootFile.isDirectory || !rootFile.canRead()) { Log.e(TAG, "code=INVALID_ROOT event=SMART_MATCH_SERVICE_ROOT_VALIDATION"); return emptyArray() }
        if (vehiclesRoot != "$packageRoot/files/.depot/vehicles" || importedFileName.isBlank() || !isSafeSkinName(importedFileName)) {
            Log.e(TAG, "code=VALIDATION_REJECTED event=SMART_MATCH_SERVICE_INPUT")
            return emptyArray()
        }
        return runCatching {
            val root = File(packageRoot).canonicalFile.toPath()
            val vehicles = File(vehiclesRoot).canonicalFile
            if (!vehicles.exists()) { Log.e(TAG, "code=MISSING_ROOT event=SMART_MATCH_SCAN_ROOT"); return@runCatching emptyArray() }
            if (!vehicles.isDirectory || !vehicles.canRead() || Files.isSymbolicLink(vehicles.toPath())) { Log.e(TAG, "code=INVALID_ROOT event=SMART_MATCH_SCAN_ROOT"); return@runCatching emptyArray() }
            val result = ArrayList<String>()
            val queue = ArrayDeque<Pair<File, Int>>()
            queue.add(vehicles to 0)
            var directories = 0
            var inspectedFiles = 0
            while (queue.isNotEmpty() && directories < boundedDirectories) {
                val (directory, depth) = queue.removeFirst()
                if (Files.isSymbolicLink(directory.toPath()) || !directory.isDirectory) continue
                directories++
                directory.listFiles().orEmpty().asSequence()
                    .filter { !Files.isSymbolicLink(it.toPath()) }
                    .forEach { child ->
                        if (child.isDirectory && depth < 8) {
                            queue.addLast(child to (depth + 1))
                        } else if (child.isFile && child.name.endsWith(".dds.z", true) && result.size < boundedEntries) {
                            inspectedFiles++
                            val canonical = child.canonicalFile.toPath()
                            if (canonical.startsWith(root) && isSkinLiveryPath(root, canonical.toString())) {
                                result += "F\t${child.length()}\t${canonical.toString()}"
                            }
                        }
                    }
            }
            Log.i(TAG, "code=SMART_MATCH_TRAVERSAL event=COUNTS resultCount=${result.size} directories=$directories files=$inspectedFiles entryLimit=$boundedEntries elapsedMs=${SystemClock.elapsedRealtime() - startedAt}")
            result.toTypedArray()
        }.getOrElse {
            Log.e(TAG, "code=TRANSPORT_FAILURE event=SMART_MATCH_SCAN_EXCEPTION elapsedMs=${SystemClock.elapsedRealtime() - startedAt}", it)
            emptyArray()
        }
    }

    private fun isSafeSkinName(name: String): Boolean = name.length <= 255 && name.endsWith(".dds.z", true) &&
        name.none { it == '/' || it == '\\' || it == '\u0000' } && name != "." && name != ".."

    private fun isSkinLiveryPath(root: Path, path: String): Boolean {
        val relative = runCatching { root.relativize(File(path).canonicalFile.toPath()).toString().replace('\\', '/') }.getOrNull() ?: return false
        val segments = relative.split('/').filter(String::isNotBlank)
        return segments.size >= 6 && segments[0] == "files" && segments[1] == ".depot" &&
            segments[2] == "vehicles" && segments[3].isNotBlank() &&
            segments[segments.lastIndex - 1].equals("livery", ignoreCase = true)
    }

    override fun listDirectoryEntries(packageRoot: String, path: String): Array<String> {
        Log.i(TAG, "listDirectoryEntries requested: root=$packageRoot path=$path")
        val allowedPackageRoots = setOf(
            "/storage/emulated/0/Android/data/com.ea.games.r3_row",
            "/storage/emulated/0/Android/data/com.ea.games.r3_na",
        )
        if (packageRoot !in allowedPackageRoots || path.indexOf('\u0000') >= 0) {
            Log.e(TAG, "listDirectoryEntries rejected root=$packageRoot path=$path reason=unsupported_root_or_nul")
            throw SecurityException("Unsupported package root")
        }
        val root = File(packageRoot)
        val candidate = File(path)
        val rootCanonical = root.canonicalFile
        val candidateCanonical = candidate.canonicalFile
        val rootPath = rootCanonical.toPath()
        val candidatePath = candidateCanonical.toPath()
        if (candidatePath != rootPath && !candidatePath.startsWith(rootPath)) {
            Log.e(TAG, "listDirectoryEntries rejected root=$packageRoot path=$path reason=path_escape")
            throw SecurityException("Path escapes package root")
        }
        if (!candidate.exists()) {
            Log.e(TAG, "listDirectoryEntries missing path=$path")
            throw IllegalArgumentException("Directory does not exist: $path")
        }
        if (!candidate.isDirectory) throw IllegalArgumentException("Path is not a directory: $path")
        if (Files.isSymbolicLink(candidate.toPath())) throw SecurityException("Directory is a symbolic link: $path")
        val children = candidate.listFiles()
            ?: throw IllegalStateException("Directory could not be listed: $path")
        Log.i(TAG, "listDirectory raw children=${children.size}: $path")
        return children.asSequence().mapNotNull { child ->
            if (child.name.indexOf('\u0000') >= 0 || child.name == "." || child.name == "..") return@mapNotNull null
            val symlink = Files.isSymbolicLink(child.toPath())
            val childCanonical = runCatching { child.canonicalFile }.getOrNull() ?: return@mapNotNull null
            val childPath = childCanonical.toPath()
            if (childPath != rootPath && !childPath.startsWith(rootPath)) return@mapNotNull null
            val type = when {
                symlink -> "L"
                child.isDirectory -> "D"
                child.isFile -> "F"
                else -> return@mapNotNull null
            }
            "$type\t${child.length()}\t${child.name}"
        }.sortedWith(compareBy<String> { !it.startsWith("D\t") }.thenBy(String.CASE_INSENSITIVE_ORDER) { it.substringAfterLast('\t') }).toList().toTypedArray().also {
            Log.i(TAG, "listDirectoryEntries returned entries=${it.size}: $path")
        }
    }

    override fun getDirectoryStats(path: String): LongArray {
        val root = File(path)
        if (!root.exists() || !root.isDirectory) return longArrayOf(0L, 0L)

        var totalSize = 0L
        var fileCount = 0L

        root.walkTopDown().forEach { file ->
            if (file.isFile) {
                totalSize += file.length()
                fileCount++
            }
        }
        return longArrayOf(totalSize, fileCount)
    }

    override fun readFile(path: String): ByteArray? {
        if (!isPathAllowed(path)) return null
        return try {
            val file = File(path)
            if (!file.exists() || !file.isFile) return null
            file.readBytes()
        } catch (e: Exception) {
            Log.e(TAG, "readFile failed: $path", e)
            null
        }
    }

    override fun writeFile(path: String, data: ByteArray): Boolean {
        val startedAt = android.os.SystemClock.elapsedRealtime()
        val allowed = isPathAllowed(path)
        if (!allowed) {
            Log.e(MUSIC_LIBRARY_TAG, "code=ML_USVC_WRITE_BLOCKED stage=path_validation result=false targetAllowed=false targetExt=${safeExtension(path)} byteCount=${data.size} elapsedMs=${SystemClock.elapsedRealtime() - startedAt}")
            return false
        }
        return try {
            val file = File(path)
            val parentReady = file.parentFile?.mkdirsOrExists() != false
            if (parentReady) file.writeBytes(data)
            Log.i(MUSIC_LIBRARY_TAG, "code=ML_USVC_WRITE stage=write result=$parentReady targetExt=${safeExtension(path)} byteCount=${data.size} elapsedMs=${SystemClock.elapsedRealtime() - startedAt}")
            parentReady
        } catch (error: Exception) {
            Log.e(MUSIC_LIBRARY_TAG, "code=ML_USVC_WRITE_EXCEPTION stage=write result=false targetExt=${safeExtension(path)} byteCount=${data.size} elapsedMs=${SystemClock.elapsedRealtime() - startedAt} exception=${error.javaClass.simpleName} message=${safeServiceMessage(error)}")
            false
        }
    }

    private fun File.mkdirsOrExists(): Boolean = isDirectory || mkdirs()

    private fun safeExtension(path: String): String {
        val name = path.substringAfterLast('/').substringBeforeLast('.', missingDelimiterValue = "")
        return path.substringAfterLast('.', missingDelimiterValue = "none")
            .takeIf { name.isNotEmpty() && it.length in 1..12 && it.all(Char::isLetterOrDigit) }
            ?.lowercase()
            ?: "none"
    }

    private fun safeServiceMessage(error: Throwable): String = error.message
        ?.takeIf { message ->
            message.length <= 120 && !message.contains('/') && !message.contains('\\') &&
                message.all { character -> character.code in 32..126 }
        }
        ?: "REDACTED_OR_EMPTY"

    override fun testRR3Access(): String {
        val rowPath = "/storage/emulated/0/Android/data/com.ea.games.r3_row"
        val naPath = "/storage/emulated/0/Android/data/com.ea.games.r3_na"
        
        val targetPath = if (File(rowPath).exists()) rowPath else if (File(naPath).exists()) naPath else null

        return try {
            if (targetPath == null) {
                return "RR3 directory not found (checked both ROW and NA versions)."
            }

            val rr3Directory = File(targetPath)
            if (!rr3Directory.isDirectory) {
                return "RR3 path exists but is not a directory."
            }

            val files = rr3Directory.listFiles()

            val fileList = files?.joinToString("\n") {
                "• ${it.name}"
            } ?: "Unable to list files."

            "SUCCESS!\n\n" +
                "RR3 directory found.\n\n" +
                "Path:\n$targetPath\n\n" +
                "Contents:\n$fileList"
        } catch (e: Exception) {
            Log.e(TAG, "testRR3Access failed", e)

            "ERROR\n\n" +
                "${e.javaClass.simpleName}\n\n" +
                e.message
        }
    }

    override fun testRR3Write(): String {
        val rowPath = "/storage/emulated/0/Android/data/com.ea.games.r3_row"
        val naPath = "/storage/emulated/0/Android/data/com.ea.games.r3_na"
        
        val targetPath = if (File(rowPath).exists()) rowPath else if (File(naPath).exists()) naPath else null

        return try {
            if (targetPath == null) {
                return "RR3 directory not found."
            }

            val testFile = File(targetPath, "retr0_test.txt")
            testFile.writeText("Project RETR0 installer write test")

            if (testFile.exists()) {
                "WRITE SUCCESS!\n\n" +
                    "Created:\n${testFile.absolutePath}\n\n" +
                    "Size: ${testFile.length()} bytes"
            } else {
                "WRITE FAILED!\n\n" +
                    "File was not created."
            }
        } catch (e: Exception) {
            Log.e(TAG, "testRR3Write failed", e)

            "WRITE ERROR\n\n" +
                "${e.javaClass.simpleName}\n\n" +
                e.message
            }
    }

    override fun forceStopPackage(packageName: String) {
        try {
            Log.i(TAG, "Force stopping package: $packageName")
            // Use full path to am and --user 0 for maximum reliability
            val process = Runtime.getRuntime().exec(arrayOf("/system/bin/am", "force-stop", "--user", "0", packageName))
            process.waitFor()
            
            // Fallback for some older devices or custom ROMs
            Runtime.getRuntime().exec(arrayOf("am", "force-stop", packageName)).waitFor()
        } catch (e: Exception) {
            Log.e(TAG, "Failed to force stop $packageName", e)
        }
    }

    override fun isPackageRunning(packageName: String): Boolean {
        return try {
            val pidof = runCatching {
                val process = Runtime.getRuntime().exec(arrayOf("pidof", packageName))
                val output = process.inputStream.bufferedReader().readText().trim()
                process.waitFor() == 0 && output.isNotEmpty()
            }.getOrNull()
            if (pidof == true) return true
            // Fallback / confirmation: match the process-name column exactly (main or ":sub" processes).
            val process = Runtime.getRuntime().exec(arrayOf("ps", "-A"))
            val output = process.inputStream.bufferedReader().readText()
            process.waitFor()
            output.lineSequence().any { line ->
                val name = line.trim().substringAfterLast(' ')
                name == packageName || name.startsWith("$packageName:")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to check if $packageName is running", e)
            false
        }
    }

    override fun dumpLogcat(destinationPath: String): Boolean {
        if (!isPathAllowed(destinationPath)) {
            Log.e(TAG, "Blocked unauthorized log dump destination")
            return false
        }
        return try {
            FileOutputStream(File(destinationPath)).use { output ->
                val process = Runtime.getRuntime().exec(arrayOf("logcat", "-d"))
                process.inputStream.use { input -> input.copyTo(output) }
                process.waitFor() == 0
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to dump logcat", e)
            false
        }
    }

    override fun applyPatch(targetPath: String, patchPath: String): Boolean {
        if (!isPathAllowed(targetPath)) return false

        return try {
            val targetFile = File(targetPath)
            val patchFile = File(patchPath)

            if (!targetFile.exists() || !patchFile.exists()) return false

            val tempFile = File(targetFile.parent, "${targetFile.name}.patching")

            // Basic Copy-and-Patch Implementation
            // Format: [4 bytes header "RTPC"][4 bytes chunk count][8 bytes offset][4 bytes data length][data]...

            FileInputStream(patchFile).use { patchIn ->
                val header = ByteArray(4)
                if ((patchIn.read(header) != 4) || (String(header) != "RTPC")) {
                    Log.e(TAG, "Invalid patch header")
                    return false
                }

                // Simple approach: Copy original to temp, then apply patches using RandomAccessFile
                targetFile.copyTo(tempFile, overwrite = true)

                RandomAccessFile(tempFile, "rw").use { raf ->
                    val buffer = ByteArray(8)
                    if (patchIn.read(buffer, 0, 4) != 4) return false
                    val count = ByteBuffer.wrap(buffer, 0, 4).order(ByteOrder.LITTLE_ENDIAN).int

                    repeat(count) {
                        if (patchIn.read(buffer, 0, 8) != 8) return@repeat
                        val offset = ByteBuffer.wrap(buffer, 0, 8).order(ByteOrder.LITTLE_ENDIAN).long

                        if (patchIn.read(buffer, 0, 4) != 4) return@repeat
                        val len = ByteBuffer.wrap(buffer, 0, 4).order(ByteOrder.LITTLE_ENDIAN).int

                        val data = ByteArray(len)
                        var read = 0
                        while (read < len) {
                            val r = patchIn.read(data, read, len - read)
                            if (r == -1) break
                            read += r
                        }

                        raf.seek(offset)
                        raf.write(data)
                    }
                }
            }

            if (targetFile.delete()) {
                tempFile.renameTo(targetFile)
            } else {
                false
            }
        } catch (e: Exception) {
            Log.e(TAG, "Patch application failed", e)
            false
        }
    }

    override fun destroy() {
        Log.w(TAG, "Retr0UserService.destroy() called; exiting process")
        android.os.Process.killProcess(android.os.Process.myPid())
    }
}
