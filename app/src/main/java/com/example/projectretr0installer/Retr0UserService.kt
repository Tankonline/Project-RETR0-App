package com.example.projectretr0installer

import android.os.ParcelFileDescriptor
import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.RandomAccessFile
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.file.Files

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
        Log.i("RETR0_INSTALLER_SVC", "nativeExtract requested")
        if (!isPathAllowed(destPath)) {
            Log.e("RETR0_INSTALLER_SVC", "Path blocked: $destPath")
            return -1
        }
        
        return try {
            pfd.use {
                Log.i("RETR0_INSTALLER_SVC", "Calling nativeExtractPrivileged with FD: ${it.fd}")
                val result = nativeExtractPrivileged(it.fd, destPath)
                Log.i("RETR0_INSTALLER_SVC", "nativeExtractPrivileged returned: $result")
                result
            }
        } catch (e: Throwable) {
            Log.e("RETR0_INSTALLER_SVC", "Privileged native extraction failed with exception", e)
            -1
        }
    }

    private companion object {
        private const val TAG = "RETR0_INSTALLER_SVC"
        
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
        val appRoot = File("/data/data/com.example.projectretr0installer").canonicalFile.toPath()
        if (candidate == appRoot || candidate.startsWith(appRoot)) return true

        return ALLOWED_ROOTS.any { rootPath ->
            val root = File(rootPath).canonicalFile.toPath()
            candidate == root || candidate.startsWith(root)
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
        if (!isPathAllowed(destination)) {
            Log.e(TAG, "Blocked unauthorized copy destination: $destination")
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
        if (!isPathAllowed(destination)) {
            Log.e(TAG, "Blocked unauthorized copyDirectory destination: $destination")
            return false
        }
        val srcDir = File(source)
        if (!srcDir.exists() || !srcDir.isDirectory) return false
        
        return try {
            // Optimization for large folders: use native 'cp -a'
            val process = Runtime.getRuntime().exec(arrayOf("cp", "-a", "$source/.", destination))
            process.waitFor() == 0
        } catch (_: Exception) {
            srcDir.copyRecursively(File(destination), overwrite = true)
        }
    }

    override fun move(source: String, destination: String): Boolean {
        if (!isPathAllowed(destination)) {
            Log.e(TAG, "Blocked unauthorized move destination: $destination")
            return false
        }
        return try {
            val srcFile = File(source)
            val dstFile = File(destination)

            if (!srcFile.exists()) return false

            // Ensure parent directory exists
            dstFile.parentFile?.mkdirs()

            srcFile.renameTo(dstFile)
        } catch (e: Exception) {
            Log.e(TAG, "move failed: $source -> $destination", e)
            false
        }
    }

    override fun importFile(pfd: ParcelFileDescriptor, destination: String): Boolean {
        if (!isPathAllowed(destination)) {
            Log.e(TAG, "Blocked unauthorized import destination: $destination")
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
            if (!tempFile.renameTo(dstFile)) {
                tempFile.delete()
                false
            } else true
        } catch (e: Exception) {
            Log.e(TAG, "importFile failed: $destination", e)
            false
        }
    }

    override fun installPackage(apkPath: String): Boolean {
        return try {
            Log.i(TAG, "Installing update: $apkPath")
            
            val apkFile = File(apkPath)
            if (!apkFile.exists()) {
                Log.e(TAG, "Update APK not found at: $apkPath")
                return false
            }
            
            val size = apkFile.length()
            
            // 1. Create an install session
            val createCmd = arrayOf("pm", "install-create", "-r", "-d")
            val createProcess = Runtime.getRuntime().exec(createCmd)
            val createOutput = createProcess.inputStream.bufferedReader().readText()
            createProcess.waitFor()
            
            val sessionId = Regex("\\[(\\d+)]").find(createOutput)?.groupValues?.get(1)
            
            if (sessionId == null) {
                val err = createProcess.errorStream.bufferedReader().readText()
                Log.e(TAG, "Failed to create install session: $createOutput $err")
                return false
            }
            
            Log.i(TAG, "Created session: $sessionId. Writing APK data...")
            
            // 2. Write the APK data to the session
            // We use 'pm install-write' which is the standard way to feed data to the package manager
            val writeProcess = Runtime.getRuntime().exec(
                arrayOf("pm", "install-write", "-S", size.toString(), sessionId, "base.apk", apkPath),
            )
            val writeSuccess = writeProcess.waitFor() == 0
            
            if (!writeSuccess) {
                val err = writeProcess.errorStream.bufferedReader().readText()
                Log.e(TAG, "Failed to write APK to session: $err")
                Runtime.getRuntime().exec(arrayOf("pm", "install-abandon", sessionId))
                return false
            }
            
            // 3. Commit the session
            Log.i(TAG, "Committing session: $sessionId")
            val commitProcess = Runtime.getRuntime().exec(arrayOf("pm", "install-commit", sessionId))
            val success = commitProcess.waitFor() == 0
            
            if (success) {
                Log.i(TAG, "Update installed successfully. Triggering restart...")
                try {
                    Runtime.getRuntime().exec(arrayOf("am", "start", "--user", "0", "-n", "com.example.projectretr0installer/.MainActivity"))
                } catch (e: Exception) {
                    Log.e(TAG, "Failed to trigger restart", e)
                }
            } else {
                val errorMsg = commitProcess.errorStream.bufferedReader().readText()
                Log.e(TAG, "PM Session Commit failed: $errorMsg")
            }
            success
        } catch (e: Exception) {
            Log.e(TAG, "Session-based install failed", e)
            false
        }
    }

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

    override fun listDirectoryEntries(packageRoot: String, path: String): Array<String> {
        Log.i(TAG, "listDirectoryEntries requested: root=$packageRoot path=$path")
        val allowedPackageRoots = setOf(
            "/storage/emulated/0/Android/data/com.ea.games.r3_row",
            "/storage/emulated/0/Android/data/com.ea.games.r3_na",
        )
        if (packageRoot !in allowedPackageRoots || path.indexOf('\u0000') >= 0) throw SecurityException("Unsupported package root")
        val root = File(packageRoot)
        val candidate = File(path)
        val rootCanonical = root.canonicalFile
        val candidateCanonical = candidate.canonicalFile
        val rootPath = rootCanonical.toPath()
        val candidatePath = candidateCanonical.toPath()
        if (candidatePath != rootPath && !candidatePath.startsWith(rootPath)) throw SecurityException("Path escapes package root")
        if (!candidate.exists() || !candidate.isDirectory || Files.isSymbolicLink(candidate.toPath())) throw IllegalArgumentException("Not a readable directory")
        val children = candidate.listFiles()
            ?: throw IllegalStateException("Directory could not be listed: $path")
        Log.i(TAG, "listDirectory raw children=${children.size}: $path")
        return children.mapNotNull { child ->
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
        }.sortedWith(compareBy<String> { !it.startsWith("D\t") }.thenBy(String.CASE_INSENSITIVE_ORDER) { it.substringAfterLast('\t') }).toTypedArray().also {
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
        if (!isPathAllowed(path)) {
            Log.e(TAG, "Blocked unauthorized writeFile: $path")
            return false
        }
        return try {
            val file = File(path)
            // Ensure parent directory exists
            file.parentFile?.mkdirs()
            file.writeBytes(data)
            true
        } catch (e: Exception) {
            Log.e(TAG, "writeFile failed: $path", e)
            false
        }
    }

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
            val process = Runtime.getRuntime().exec(arrayOf("ps", "-A"))
            val output = process.inputStream.bufferedReader().readText()
            output.contains(packageName)
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
