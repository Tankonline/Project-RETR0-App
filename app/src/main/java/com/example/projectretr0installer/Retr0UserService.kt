package com.example.projectretr0installer

import android.os.ParcelFileDescriptor
import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

/**
 * Shizuku UserService implementation.
 *
 * This class implements [IRetr0UserService] to perform privileged filesystem operations
 * within the Shizuku process context.
 */
class Retr0UserService : IRetr0UserService.Stub() {

    private companion object {
        private const val TAG = "RETR0_INSTALLER_SVC"
        
        private val ALLOWED_ROOTS = listOf(
            "/storage/emulated/0/Android/data/com.ea.games.r3_row",
            "/storage/emulated/0/Android/data/com.ea.games.r3_na",
            "/storage/emulated/0/Documents/ProjectRETR0",
            "/storage/emulated/0/Android/obb/com.ea.games.r3_row",
            "/storage/emulated/0/Android/obb/com.ea.games.r3_na"
        )
    }

    private fun isPathAllowed(path: String): Boolean {
        // Always allow paths within the app's own sandboxed directories
        if (path.contains("/com.example.projectretr0installer/")) return true
        
        // Check against authorized modding directories
        return ALLOWED_ROOTS.any { path.startsWith(it) }
    }

    override fun ping(): Boolean = true

    override fun exists(path: String): Boolean {
        return File(path).exists()
    }

    override fun isDirectory(path: String): Boolean {
        return File(path).isDirectory
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

            ParcelFileDescriptor.AutoCloseInputStream(pfd).use { input ->
                FileOutputStream(dstFile).use { output ->
                    input.copyTo(output)
                }
            }
            true
        } catch (e: Exception) {
            Log.e(TAG, "importFile failed: $destination", e)
            false
        }
    }

    override fun installPackage(apkPath: String): Boolean {
        return try {
            Log.i(TAG, "Installing update: $apkPath")
            // -r: reinstall, keeping data
            // -d: allow version code downgrade (just in case)
            val process = Runtime.getRuntime().exec(arrayOf("pm", "install", "-r", "-d", apkPath))
            process.waitFor() == 0
        } catch (e: Exception) {
            Log.e(TAG, "Silent install failed", e)
            false
        }
    }

    override fun exportFile(source: String, pfd: ParcelFileDescriptor): Boolean {
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
        val file = File(path)
        return if (file.exists() && file.isFile) file.length() else -1L
    }

    override fun getLastModified(path: String): Long {
        val file = File(path)
        return if (file.exists()) file.lastModified() else 0L
    }

    override fun listFiles(path: String): Array<String>? {
        val file = File(path)
        return if (file.exists() && file.isDirectory) {
            file.list()
        } else {
            null
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

    override fun destroy() {
        Log.w(TAG, "Retr0UserService.destroy() called; exiting process")
        android.os.Process.killProcess(android.os.Process.myPid())
    }
}
