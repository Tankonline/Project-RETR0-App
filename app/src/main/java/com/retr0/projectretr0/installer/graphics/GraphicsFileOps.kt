package com.retr0.projectretr0.installer.graphics

import android.util.Log
import com.retr0.projectretr0.IRetr0UserService

/** Outcome of a privileged operation, with the reason when it failed. */
data class OpResult(val ok: Boolean, val detail: String = "") {
    companion object {
        val OK = OpResult(true)
        fun fail(detail: String) = OpResult(false, detail)
    }
}

/** The privileged file operations the Graphics Hub needs. */
interface GraphicsFileOps {
    fun exists(path: String): Boolean
    fun isDirectory(path: String): Boolean
    /** Names of direct regular files (hidden files included). Empty when unreadable. */
    fun listFileNames(directory: String): List<String>
    /** Names of direct sub-directories. Empty when unreadable. */
    fun listDirectoryNames(directory: String): List<String>
    /** Lower-case SHA-256, or "" when the file can't be read. */
    fun sha256(path: String): String
    /** File size in bytes, or -1 when unknown. Used for logging only. */
    fun size(path: String): Long = -1L
    fun createDirectory(path: String): Boolean
    /** Copies a file into the backup area and checks the copy's hash. */
    fun copyVerified(source: String, destination: String, expectedSha256: String): OpResult
    /** Atomically replaces a file in .depot/eds or .depot/feats (temp file + rename) and checks its hash. */
    fun publishGraphicsFile(source: String, target: String, expectedSha256: String): OpResult
    /** Deletes one regular file in .depot/eds or .depot/feats. Never a directory. */
    fun deleteGraphicsFile(target: String): OpResult
    /** Deletes a path below the graphics backup root. */
    fun deleteBackupPath(path: String): Boolean
    fun writeText(path: String, text: String): Boolean
    fun readText(path: String): String?
    fun writeBytes(path: String, data: ByteArray): Boolean
    fun readBytes(path: String): ByteArray?
    /** Force-stops the game if needed; ok once it is confirmed not running. */
    fun ensureGameStopped(gamePackage: String): OpResult
    /** Graphics API version of the privileged helper: 0 = helper too old, -1 = not applicable. */
    fun serviceApiVersion(): Int = -1
    /** One-line diagnostic dump (uid, folder owners/modes, free space...) for the logs. */
    fun environment(packageRoot: String): String = ""
}

class ShizukuGraphicsFileOps(private val service: IRetr0UserService) : GraphicsFileOps {
    override fun exists(path: String) = service.exists(path)
    override fun isDirectory(path: String) = service.isDirectory(path)

    override fun listFileNames(directory: String): List<String> =
        service.listFiles(directory).orEmpty().filter { !service.isDirectory("$directory/$it") }

    override fun listDirectoryNames(directory: String): List<String> =
        service.listFiles(directory).orEmpty().filter { service.isDirectory("$directory/$it") }

    override fun sha256(path: String): String = service.sha256File(path).orEmpty().lowercase()
    override fun size(path: String): Long = runCatching { service.getFileSize(path) }.getOrDefault(-1L)
    override fun createDirectory(path: String) = service.createDirectory(path)

    override fun copyVerified(source: String, destination: String, expectedSha256: String): OpResult {
        if (!GraphicsPathValidator.isBackupPath(destination)) return OpResult.fail("destination_not_in_backup_root")
        if (!service.copyFile(source, destination)) return OpResult.fail("copyFile_failed (see RETR0_INSTALLER_SVC)")
        val actual = service.sha256File(destination).orEmpty().lowercase()
        if (actual == expectedSha256.lowercase()) return OpResult.OK
        service.delete(destination)
        return OpResult.fail("backup_hash_mismatch expected=${expectedSha256.take(8)} actual=${actual.take(8).ifEmpty { "none" }}")
    }

    override fun publishGraphicsFile(source: String, target: String, expectedSha256: String): OpResult =
        reply { service.publishGraphicsFile(source, target, expectedSha256) }

    override fun deleteGraphicsFile(target: String): OpResult = reply { service.deleteGraphicsFile(target) }

    override fun deleteBackupPath(path: String): Boolean =
        GraphicsPathValidator.isBackupPath(path) && service.delete(path)

    override fun writeText(path: String, text: String) = service.writeFile(path, text.toByteArray(Charsets.UTF_8))
    override fun readText(path: String): String? = service.readFile(path)?.toString(Charsets.UTF_8)
    override fun writeBytes(path: String, data: ByteArray) = service.writeFile(path, data)
    override fun readBytes(path: String): ByteArray? = service.readFile(path)

    override fun ensureGameStopped(gamePackage: String): OpResult {
        if (!service.isPackageRunning(gamePackage)) return OpResult(true, "wasRunning=false")
        service.forceStopPackage(gamePackage)
        for (attempt in 1..10) {
            if (!service.isPackageRunning(gamePackage)) return OpResult(true, "wasRunning=true forceStopped=true checks=$attempt")
            Thread.sleep(300)
        }
        val running = service.isPackageRunning(gamePackage)
        return OpResult(!running, "wasRunning=true forceStopped=${!running} checks=11")
    }

    override fun serviceApiVersion(): Int = runCatching { service.getGraphicsApiVersion() }.getOrDefault(0)

    override fun environment(packageRoot: String): String =
        runCatching { service.graphicsDiagnostics(packageRoot) }.getOrNull()
            ?: "diagnostics=unavailable (old Shizuku helper)"

    /**
     * Helper replies are "OK ..." or "FAIL stage=... reason=...". A null reply means the running
     * Shizuku helper doesn't know the call, i.e. it's an older build that hasn't been restarted.
     */
    private inline fun reply(call: () -> String?): OpResult {
        val text = try {
            call()
        } catch (e: Exception) {
            return OpResult.fail("binder_error ${e.javaClass.simpleName}: ${e.message}")
        }
        return when {
            text == null -> OpResult.fail("no_reply: the Shizuku helper is an older build. Fully close Project RETR0 and reopen it, or restart Shizuku")
            text.startsWith("OK") -> OpResult(true, text.removePrefix("OK").trim())
            else -> OpResult.fail(text.removePrefix("FAIL").trim())
        }
    }
}

/** Logcat sink for the engine: `adb logcat -s RETR0_GRAPHICS`. */
object GraphicsLogcat : GraphicsLog {
    const val TAG = "RETR0_GRAPHICS"
    override fun log(level: GraphicsLog.Level, code: String, detail: String) {
        val line = "code=$code $detail"
        when (level) {
            GraphicsLog.Level.INFO -> Log.i(TAG, line)
            GraphicsLog.Level.WARN -> Log.w(TAG, line)
            GraphicsLog.Level.ERROR -> Log.e(TAG, line)
        }
    }
}
