package com.example.projectretr0installer.ui.skins

import com.example.projectretr0installer.IRetr0UserService
import android.util.Log
import java.io.File
import java.util.Locale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class SkinBrowserEntry(val name: String, val path: String, val isDirectory: Boolean, val isRegularFile: Boolean, val isSymbolicLink: Boolean, val size: Long)
data class SelectedSkinTarget(val absolutePath: String, val relativePath: String, val fileName: String, val format: String)

sealed interface BrowserResult {
    data class Success(val entries: List<SkinBrowserEntry>) : BrowserResult
    data class Error(val message: String) : BrowserResult
}

fun interface SkinTargetBrowserSource { suspend fun list(packageRoot: String, path: String): BrowserResult }

data class SmartScanLimits(
    val directories: Int = 2_000,
    val entriesPerDirectory: Int = 512,
    val candidates: Int = 24,
)
sealed interface SmartScanResult {
    data class Found(val entry: SkinBrowserEntry) : SmartScanResult
    data class Candidates(val entries: List<SkinBrowserEntry>) : SmartScanResult
    data object NotFound : SmartScanResult
    data class Error(val message: String) : SmartScanResult
}

enum class ValidationSeverity { INFO, WARNING, ERROR, SUCCESS }
data class ValidationPresentation(val message: String, val severity: ValidationSeverity)

fun validationPresentation(error: Throwable?, importedFileName: String? = null): ValidationPresentation = when {
    error == null -> ValidationPresentation("Exact filename and livery target verified.", ValidationSeverity.SUCCESS)
    error.message?.contains("exactly match") == true || error.message?.contains("must match") == true ->
        ValidationPresentation(
            "${error.message ?: "Filenames do not match."}${importedFileName?.let { " Expected: $it" }.orEmpty()}",
            ValidationSeverity.WARNING,
        )
    error.message?.contains("directly inside") == true || error.message?.contains("vehicle livery folder") == true ->
        ValidationPresentation(
            "Open a vehicle's livery folder and choose the exact target file${importedFileName?.let { ": $it" }.orEmpty()}.",
            ValidationSeverity.WARNING,
        )
    error.message?.contains("symlink") == true -> ValidationPresentation("Symbolic links are blocked for safety.", ValidationSeverity.ERROR)
    error.message?.contains("escapes") == true || error.message?.contains("Unsupported") == true -> ValidationPresentation("This location is outside the selected RR3 package.", ValidationSeverity.ERROR)
    else -> ValidationPresentation(error.message ?: "Choose a valid RR3 .dds.z target.", ValidationSeverity.ERROR)
}

fun packageBreadcrumbs(root: String, path: String): List<Pair<String, String>> {
    if (!path.startsWith(root)) return listOf(root.substringAfterLast('/') to root)
    val relative = path.removePrefix(root).trim('/')
    val result = mutableListOf(root.substringAfterLast('/') to root)
    var current = root
    relative.split('/').filter(String::isNotBlank).forEach { segment -> current += "/$segment"; result += segment to current }
    return result
}

suspend fun smartMatch(source: SkinTargetBrowserSource, root: String, importedFileName: String, limits: SmartScanLimits = SmartScanLimits()): SmartScanResult {
    val vehiclesRoot = "$root/files/.depot/vehicles"
    val vehicleDirectories = when (val result = source.list(root, vehiclesRoot)) {
        is BrowserResult.Error -> return SmartScanResult.Error(result.message)
        is BrowserResult.Success -> result.entries
    }
    val validMatches = mutableListOf<SkinBrowserEntry>()
    for (vehicle in vehicleDirectories.asSequence().filter { it.isDirectory && !it.isSymbolicLink }.take(limits.directories)) {
        val entries = when (val result = source.list(root, vehicle.path)) {
            is BrowserResult.Error -> continue
            is BrowserResult.Success -> result.entries
        }
        entries.asSequence()
            .filter { it.isRegularFile && !it.isSymbolicLink }
            .filter { SkinTargetValidator.validate(root, importedFileName, it).isSuccess }
            .take(limits.entriesPerDirectory)
            .forEach(validMatches::add)
    }

    val ranked = validMatches
        .distinctBy { it.path }
        .sortedByDescending { SkinTargetValidator.matchScore(SkinTargetValidator.searchTokens(importedFileName), importedFileName, it.name) }
        .take(limits.candidates)
    return when (ranked.size) {
        0 -> SmartScanResult.NotFound
        1 -> SmartScanResult.Found(ranked.single())
        else -> SmartScanResult.Candidates(ranked)
    }
}

class ShizukuSkinTargetBrowserSource(private val service: IRetr0UserService?) : SkinTargetBrowserSource {
    override suspend fun list(packageRoot: String, path: String): BrowserResult = withContext(Dispatchers.IO) {
        val privileged = service ?: return@withContext BrowserResult.Error("Privileged service is disconnected. Connect Shizuku and retry.")
        runCatching {
            Log.i("SKIN_TARGET_BROWSER", "Listing root=$packageRoot path=$path")
            val encodedEntries = privileged.listDirectoryEntries(packageRoot, path)
                ?: return@runCatching BrowserResult.Error("The privileged service returned no directory listing. Reconnect Shizuku and retry.")
            Log.i("SKIN_TARGET_BROWSER", "Received ${encodedEntries.size} entries for $path")
            BrowserResult.Success(encodedEntries.mapNotNull { it.toBrowserEntry(path) })
        }.getOrElse {
            Log.e("SKIN_TARGET_BROWSER", "Unable to list $path", it)
            BrowserResult.Error(it.message ?: "Unable to read this RR3 directory.")
        }
    }

    private fun String.toBrowserEntry(parent: String): SkinBrowserEntry? {
        val parts = split('\t', limit = 3)
        if (parts.size != 3) return null
        val type = parts[0]
        val size = parts[1].toLongOrNull() ?: return null
        val name = parts[2]
        if (name.isBlank() || '/' in name || '\\' in name || name.indexOf('\u0000') >= 0) return null
        return SkinBrowserEntry(
            name = name,
            path = "${parent.trimEnd('/')}/$name",
            isDirectory = type == "D",
            isRegularFile = type == "F",
            isSymbolicLink = type == "L",
            size = size,
        )
    }
}

object SkinTargetValidator {
    const val ROW_ROOT = "/storage/emulated/0/Android/data/com.ea.games.r3_row"
    const val NA_ROOT = "/storage/emulated/0/Android/data/com.ea.games.r3_na"
    fun root(gamePackage: GamePackage) = if (gamePackage == GamePackage.ROW) ROW_ROOT else NA_ROOT

    fun validate(root: String, importedFileName: String, entry: SkinBrowserEntry): Result<SelectedSkinTarget> = runCatching {
        require(root == ROW_ROOT || root == NA_ROOT) { "Unsupported RR3 package root." }
        require(importedFileName.isSafeName() && importedFileName.endsWith(".dds.z")) { "Imported filename is not a safe .dds.z name." }
        require(entry.name.isSafeName() && entry.path.indexOf('\u0000') < 0) { "Unsafe target name." }
        require(entry.isRegularFile && !entry.isDirectory && !entry.isSymbolicLink) { "Target must be a regular, non-symlink file." }
        val normalizedRoot = File(root).toPath().normalize()
        val normalizedTarget = File(entry.path).toPath().normalize()
        require(normalizedTarget.startsWith(normalizedRoot) && normalizedTarget != normalizedRoot) { "Target escapes the selected package." }
        val relative = normalizedRoot.relativize(normalizedTarget).joinToString("/")
        val segments = relative.split('/')
        require(segments.size >= 6 && segments[0] == "files" && segments[1] == ".depot" && segments[2] == "vehicles" && segments[3].isSafeName() && segments[segments.lastIndex - 1].equals("livery", ignoreCase = true)) {
            "Choose a file inside an RR3 vehicle livery folder."
        }
        require(entry.name.endsWith(".dds.z")) { "Target must be a .dds.z file." }
        require(targetStem(entry.name).equals(targetStem(importedFileName), ignoreCase = true)) {
            "Filename must match the selected skin's vehicle and slot."
        }
        val importedFormat = textureFormat(importedFileName)
        val targetFormat = textureFormat(entry.name)
        require(importedFormat.substringBefore('(') == targetFormat.substringBefore('(')) {
            "Texture formats do not match."
        }
        SelectedSkinTarget(normalizedTarget.toString().replace('\\', '/'), relative, entry.name, targetFormat)
    }

    fun textureFormat(name: String): String {
        val stem = name.removeSuffix(".dds.z").lowercase(Locale.ROOT)
        return when {
            Regex(".*etc\\(\\d+\\)$").matches(stem) -> stem.substringAfterLast('_', stem)
            Regex(".*atc\\(\\d+\\)$").matches(stem) -> stem.substringAfterLast('_', stem)
            else -> "dds.z"
        }
    }

    /**
     * RR3's normal livery filenames use a dot-separated texture suffix, for example
     * `2014_audi_r18etronquattro_ext_one.etc.dds.z`. The portion before that suffix
     * identifies the car and slot and is the correct key for Smart Match.
     */
    fun targetStem(name: String): String {
        val base = name.removeSuffix(".dds.z")
        return base.substringBeforeLast('.', base)
    }

    fun searchTokens(name: String): Set<String> = targetStem(name)
        .lowercase(Locale.ROOT)
        .split(Regex("[^a-z0-9]+"))
        .filter { it.length >= 2 && it !in setOf("dds", "ext", "int", "one", "two") }
        .toSet()

    fun matchScore(importedTokens: Set<String>, importedFileName: String, candidateName: String): Int {
        val candidateStem = targetStem(candidateName).lowercase(Locale.ROOT)
        val candidateTokens = searchTokens(candidateName)
        val shared = importedTokens.count { it in candidateTokens }
        val exact = targetStem(candidateName).equals(targetStem(importedFileName), ignoreCase = true)
        return shared * 10 + (if (exact) 100 else 0) + (if (candidateStem.contains("livery")) 1 else 0)
    }

    private fun String.isSafeName() = isNotBlank() && this != "." && this != ".." && indexOf('\u0000') < 0 && '/' !in this && '\\' !in this
}
