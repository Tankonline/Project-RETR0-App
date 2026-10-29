package com.retr0.projectretr0.data.carModels.local

import android.os.ParcelFileDescriptor
import com.retr0.projectretr0.IRetr0UserService
import com.retr0.projectretr0.data.carModels.CarModelErrorCode
import com.retr0.projectretr0.data.carModels.CarModelException
import com.retr0.projectretr0.ui.skins.SkinTargetValidator
import java.io.File

data class RemoteFile(val relativePath: String, val sizeBytes: Long, val sha256: String? = null, val contentType: String = "application/octet-stream")

/** Privileged file operations the car-model engine needs. Backed by the same Shizuku service the skin flow uses. */
interface CarModelFileOps {
    fun exists(path: String): Boolean
    fun isDirectory(path: String): Boolean
    /** Names of the direct sub-directories of [directory], which must be inside [packageRoot]. */
    fun listDirectories(packageRoot: String, directory: String): List<String>
    /** Every regular (non-symlink) file under [directory], relative to it. */
    fun listTree(packageRoot: String, directory: String): List<RemoteFile>
    /** Lower-case SHA-256, or an empty string when the file cannot be read. */
    fun sha256(path: String): String
    fun copy(source: String, destination: String): Boolean
    fun importLocal(source: File, destination: String): Boolean
    /** Creates a directory and its parents when supported by the privileged backend. */
    fun createDirectory(path: String): Boolean = File(path).isDirectory || File(path).mkdirs()
    fun delete(path: String): Boolean
    fun writeText(path: String, text: String): Boolean
    fun readText(path: String): String?
    /** Force-stops the game if needed; true once it is no longer running. */
    fun ensureGameStopped(gamePackage: String): Boolean
}

/** Where car models are installed and where their original backups live. */
class CarModelPaths(
    val backupRoot: String = "/storage/emulated/0/Documents/ProjectRETR0/CarModelBackups",
    private val packageRoots: Map<String, String> = mapOf(
        "com.ea.games.r3_row" to SkinTargetValidator.ROW_ROOT,
        "com.ea.games.r3_na" to SkinTargetValidator.NA_ROOT,
    ),
) {
    fun packageRoot(gamePackage: String): String = packageRoots[gamePackage] ?: throw CarModelException(CarModelErrorCode.UNSUPPORTED_PACKAGE, "Unsupported RR3 package: $gamePackage")
    fun depotRoot(gamePackage: String): String = "${packageRoot(gamePackage)}/files/.depot"
    fun vehiclesRoot(gamePackage: String): String = "${depotRoot(gamePackage)}/vehicles"
    fun vehiclesDataRoot(gamePackage: String): String = "${vehiclesRoot(gamePackage)}/data"
    fun depotDataRoot(gamePackage: String): String = "${depotRoot(gamePackage)}/data"
    fun destination(gamePackage: String, vehicleDirectory: String, file: com.retr0.projectretr0.data.carModels.manifest.CarModelFile): String = when (file.destinationRoot) {
        com.retr0.projectretr0.data.carModels.manifest.CarModelDestinationRoot.VEHICLE -> "${vehicleDir(gamePackage, vehicleDirectory)}/${file.destinationPath}"
        com.retr0.projectretr0.data.carModels.manifest.CarModelDestinationRoot.VEHICLES_DATA -> "${vehiclesDataRoot(gamePackage)}/${file.destinationPath}"
        com.retr0.projectretr0.data.carModels.manifest.CarModelDestinationRoot.DEPOT_DATA -> "${depotDataRoot(gamePackage)}/${file.destinationPath}"
        com.retr0.projectretr0.data.carModels.manifest.CarModelDestinationRoot.DEPOT_ROOT -> "${depotRoot(gamePackage)}/${file.destinationPath}"
    }
    fun vehicleDir(gamePackage: String, vehicleDirectory: String): String {
        requireSafe(vehicleDirectory)
        return "${vehiclesRoot(gamePackage)}/$vehicleDirectory"
    }
    fun backupDir(gamePackage: String, vehicleDirectory: String): String {
        packageRoot(gamePackage)
        requireSafe(vehicleDirectory)
        return "$backupRoot/$gamePackage/$vehicleDirectory"
    }
    /** Per-file backups of what an add-on overwrote; kept apart from the REPLACE whole-folder backup. */
    fun addonBackupDir(gamePackage: String, vehicleDirectory: String): String {
        packageRoot(gamePackage)
        requireSafe(vehicleDirectory)
        return "$backupRoot/addons/$gamePackage/$vehicleDirectory"
    }

    private fun requireSafe(vehicleDirectory: String) {
        if (!SAFE_VEHICLE_DIR.matches(vehicleDirectory)) throw CarModelException(CarModelErrorCode.UNSAFE_VEHICLE_DIRECTORY, "Unsafe vehicle directory name: $vehicleDirectory")
    }

    companion object { val SAFE_VEHICLE_DIR = Regex("[A-Za-z0-9_-]{1,120}") }
}

class ShizukuCarModelFileOps(private val service: IRetr0UserService) : CarModelFileOps {
    override fun exists(path: String) = service.exists(path)
    override fun isDirectory(path: String) = service.isDirectory(path)

    override fun listDirectories(packageRoot: String, directory: String): List<String> {
        android.util.Log.i("RETR0-CarModels", "listDirectories root=$packageRoot directory=$directory")
        val result = entries(packageRoot, directory).filter { it.type == 'D' }.map { it.name }
        android.util.Log.i("RETR0-CarModels", "listDirectories result count=${result.size} directory=$directory")
        return result
    }

    override fun listTree(packageRoot: String, directory: String): List<RemoteFile> {
        val files = mutableListOf<RemoteFile>()
        fun walk(path: String, prefix: String, depth: Int) {
            require(depth <= MAX_DEPTH) { "Vehicle folder is nested too deeply" }
            entries(packageRoot, path).forEach { entry ->
                when (entry.type) {
                    'F' -> {
                        val relative = prefix + entry.name
                        files += RemoteFile(relative, entry.size)
                    }
                    'D' -> walk("$path/${entry.name}", "$prefix${entry.name}/", depth + 1)
                }
                require(files.size <= MAX_FILES) { "Vehicle folder has too many files" }
            }
        }
        walk(directory, "", 0)
        return files
    }

    override fun sha256(path: String): String = service.sha256File(path).orEmpty().lowercase()
    override fun copy(source: String, destination: String) = service.copyFile(source, destination)
    override fun importLocal(source: File, destination: String): Boolean =
        // The service closes the descriptor once it has streamed the file to a temp sibling and renamed it.
        service.importFile(ParcelFileDescriptor.open(source, ParcelFileDescriptor.MODE_READ_ONLY), destination)
    override fun createDirectory(path: String) = service.createDirectory(path)
    override fun delete(path: String) = service.delete(path)
    override fun writeText(path: String, text: String) = service.writeFile(path, text.toByteArray(Charsets.UTF_8))
    override fun readText(path: String): String? = service.readFile(path)?.toString(Charsets.UTF_8)

    override fun ensureGameStopped(gamePackage: String): Boolean {
        if (service.isPackageRunning(gamePackage)) service.forceStopPackage(gamePackage)
        return !service.isPackageRunning(gamePackage)
    }

    private data class Entry(val type: Char, val size: Long, val name: String)
    private fun entries(packageRoot: String, path: String): List<Entry> = try {
        android.util.Log.i("RETR0-CarModels", "AIDL listDirectoryEntries root=$packageRoot path=$path")
        service.listDirectoryEntries(packageRoot, path).mapNotNull { raw ->
            val parts = raw.split('\t', limit = 3)
            if (parts.size != 3 || parts[0].length != 1) null else Entry(parts[0][0], parts[1].toLongOrNull() ?: 0L, parts[2])
        }
    } catch (error: Throwable) {
        android.util.Log.e("RETR0-CarModels", "AIDL listDirectoryEntries failed root=$packageRoot path=$path", error)
        throw CarModelException(
            CarModelErrorCode.VEHICLES_UNREADABLE,
            "Unable to enumerate RR3 directory '$path': ${error.message ?: error.javaClass.simpleName}",
            error,
        )
    }

    private companion object { const val MAX_FILES = 2_000; const val MAX_DEPTH = 8 }
}
