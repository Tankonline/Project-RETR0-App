package com.example.projectretr0installer.ui.skins

import android.util.Log
import com.example.projectretr0installer.IRetr0UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.util.Locale

private const val SMART_MATCH_TAG = "RETR0_SMART_MATCH"

enum class SmartMatchCategory { EXACT, LIKELY, RELATED }
enum class SmartMatchErrorCode {
    SERVICE_UNAVAILABLE, INVALID_ROOT, MISSING_ROOT, EMPTY_SCAN, TRANSPORT_FAILURE,
    PARSE_FAILURE, ZERO_CANDIDATES, STALE_RESULT, VALIDATION_REJECTED, LIMIT_REACHED,
}
data class SmartMatchDiagnostic(val code: SmartMatchErrorCode, val event: String, val detail: String = "") {
    val message: String get() = "${code.name}: ${event}${detail.takeIf { it.isNotBlank() }?.let { " ($it)" }.orEmpty()}"
}

internal fun diagnosticForValidation(error: Throwable?): SmartMatchDiagnostic? = error?.let {
    SmartMatchDiagnostic(SmartMatchErrorCode.VALIDATION_REJECTED, "FINAL_VALIDATION", it.message.orEmpty())
}
data class SmartMatchCandidate(val entry: SkinBrowserEntry, val category: SmartMatchCategory, val reasons: List<String>, val score: Int)
data class SkinBrowserEntry(val name: String, val path: String, val isDirectory: Boolean, val isRegularFile: Boolean, val isSymbolicLink: Boolean, val size: Long)
data class SelectedSkinTarget(val absolutePath: String, val relativePath: String, val fileName: String, val format: String)

sealed interface BrowserResult {
    data class Success(val entries: List<SkinBrowserEntry>) : BrowserResult
    data class Error(val message: String, val diagnostic: SmartMatchDiagnostic? = null) : BrowserResult
}

fun interface SkinTargetBrowserSource { suspend fun list(packageRoot: String, path: String): BrowserResult }

data class SmartScanLimits(
    val directories: Int = 2_000,
    val entriesPerDirectory: Int = 50_000,
    val candidates: Int = 24,
    val maxDepth: Int = 8,
) {
    init {
        require(directories in 0..2_000)
        require(entriesPerDirectory in 0..50_000)
        require(candidates in 0..24)
        require(maxDepth in 0..8)
    }
}

data class SmartScanCacheKey(val packageRoot: String, val importedFileName: String)

class SmartScanCache(private val ttlMillis: Long = 15_000L, private val clock: () -> Long = System::currentTimeMillis) {
    private data class Entry(val storedAt: Long, val value: SmartScanResult)
    private val entries = mutableMapOf<SmartScanCacheKey, Entry>()

    @Synchronized fun get(key: SmartScanCacheKey): SmartScanResult? = entries[key]?.takeIf { clock() - it.storedAt <= ttlMillis }?.value
    @Synchronized fun put(key: SmartScanCacheKey, value: SmartScanResult) { entries[key] = Entry(clock(), value) }
    @Synchronized fun invalidate(key: SmartScanCacheKey) { entries.remove(key) }
}

sealed interface SmartScanResult {
    data class Found(val candidate: SmartMatchCandidate) : SmartScanResult
    data class Candidates(val entries: List<SmartMatchCandidate>) : SmartScanResult
    data object NotFound : SmartScanResult
    data class Error(val message: String) : SmartScanResult
}

enum class ValidationSeverity { INFO, WARNING, ERROR, SUCCESS }
data class ValidationPresentation(val message: String, val severity: ValidationSeverity)

fun validationPresentation(error: Throwable?, importedFileName: String? = null): ValidationPresentation = when {
    error == null -> ValidationPresentation("Exact filename and livery target verified.", ValidationSeverity.SUCCESS)
    error.message?.contains("exactly match") == true || error.message?.contains("must match") == true -> ValidationPresentation("${error.message ?: "Filename mismatch."}${importedFileName?.let { " Expected: $it" }.orEmpty()}", ValidationSeverity.WARNING)
    error.message?.contains("vehicle livery folder") == true -> ValidationPresentation("Choose a candidate inside a vehicle livery folder.", ValidationSeverity.WARNING)
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
    Log.i(SMART_MATCH_TAG, "event=WIZARD_SOURCE_HANDOFF code=START root=${root.substringAfterLast('/')}")
    if (root != SkinTargetValidator.ROW_ROOT && root != SkinTargetValidator.NA_ROOT) {
        return SmartScanResult.Error(SmartMatchDiagnostic(SmartMatchErrorCode.INVALID_ROOT, "INVALID_ROOT").message)
    }
    if (!importedFileName.isSafeSmartMatchName()) {
        return SmartScanResult.Error(SmartMatchDiagnostic(SmartMatchErrorCode.VALIDATION_REJECTED, "SOURCE_NAME_REJECTED").message)
    }
    val vehiclesRoot = "$root/files/.depot/vehicles"
    Log.i(SMART_MATCH_TAG, "event=BROWSER_SCAN_START root=$vehiclesRoot")
    val vehicleDirectories = when (val result = source.list(root, vehiclesRoot)) {
        is BrowserResult.Error -> return SmartScanResult.Error(result.diagnostic?.message ?: SmartMatchDiagnostic(SmartMatchErrorCode.TRANSPORT_FAILURE, "BROWSER_SCAN_FAILURE").message)
        is BrowserResult.Success -> result.entries
    }
    Log.i(SMART_MATCH_TAG, "event=VEHICLE_DIRECTORY_SUMMARY raw=${vehicleDirectories.size} directories=${vehicleDirectories.count { it.isDirectory }}")
    if (vehicleDirectories.isEmpty()) {
        return SmartScanResult.Error(SmartMatchDiagnostic(SmartMatchErrorCode.EMPTY_SCAN, "EMPTY_SCAN").message)
    }
    val entries = mutableListOf<SkinBrowserEntry>()
    vehicleDirectories.asSequence().filter { it.isDirectory && !it.isSymbolicLink }.take(limits.directories).forEach { vehicle ->
        when (val result = source.list(root, vehicle.path)) {
            is BrowserResult.Success -> {
                val files = result.entries.filter { entry ->
                    entry.isRegularFile && !entry.isSymbolicLink && entry.name.endsWith(".dds.z", true)
                }
                val safeFiles = files.filter { entry ->
                    isSafeCandidatePath(root, entry.path) ||
                        isSafeDiscoveryEntry(root, vehicle.path, entry.path)
                }
                Log.i(SMART_MATCH_TAG, "event=VEHICLE_DIRECTORY_RESULT vehicle=${vehicle.name} raw=${result.entries.size} dds=${files.size} safe=${safeFiles.size}")
                entries += safeFiles
            }
            is BrowserResult.Error -> Log.w(SMART_MATCH_TAG, "code=TRANSPORT_FAILURE event=VEHICLE_DIRECTORY_FAILURE vehicle=${vehicle.name}")
        }
    }
    Log.i(SMART_MATCH_TAG, "event=TRAVERSAL_COUNTS directories=${vehicleDirectories.size} entries=${entries.size} limit=${limits.directories}")
    return rankedResult(importedFileName, entries, limits)
}

internal fun rankedResult(importedFileName: String, entries: List<SkinBrowserEntry>, limits: SmartScanLimits): SmartScanResult {
    val ranked = rankSmartCandidates(importedFileName, entries, limits)
    Log.i(SMART_MATCH_TAG, "event=RANKING_COUNT scanned=${entries.size} candidates=${ranked.size}")
    return when (ranked.size) {
        0 -> SmartScanResult.Error(SmartMatchDiagnostic(SmartMatchErrorCode.ZERO_CANDIDATES, "ZERO_CANDIDATES").message)
        1 -> SmartScanResult.Found(ranked.single())
        else -> SmartScanResult.Candidates(ranked)
    }
}

internal fun rankSmartCandidates(importedFileName: String, entries: List<SkinBrowserEntry>, limits: SmartScanLimits): List<SmartMatchCandidate> {
    val importedTokens = SkinTargetValidator.searchTokens(importedFileName)
    Log.i(SMART_MATCH_TAG, "event=TOKEN_SUMMARY imported=${importedFileName.take(80)} tokens=${importedTokens.sorted().joinToString(",")}")
    val importedProfile = SkinTargetValidator.filenameProfile(importedFileName)
    return entries.asSequence().distinctBy { it.path }
        .filter { it.isRegularFile && !it.isSymbolicLink && it.name.endsWith(".dds.z", true) }
        .mapNotNull { entry ->
            val candidateProfile = SkinTargetValidator.filenameProfile(entry.name)
            val exact = entry.name.equals(importedFileName, ignoreCase = true)
            val sameVehicle = importedProfile.vehicle != null && importedProfile.vehicle == candidateProfile.vehicle
            val sameSlot = importedProfile.slot != null && importedProfile.slot == candidateProfile.slot
            val sameSide = importedProfile.side != null && importedProfile.side == candidateProfile.side
            val sameFormat = importedProfile.format == candidateProfile.format
            val sameYear = importedProfile.year != null && importedProfile.year == candidateProfile.year
            val sameManufacturer = importedProfile.manufacturer != null && importedProfile.manufacturer == candidateProfile.manufacturer
            val shared = importedProfile.tokens.intersect(candidateProfile.tokens).size
            val category = when {
                exact && sameFormat -> SmartMatchCategory.EXACT
                sameVehicle && sameSlot && sameFormat -> SmartMatchCategory.LIKELY
                sameVehicle || sameManufacturer && sameYear || shared >= 2 || sameFormat -> SmartMatchCategory.RELATED
                else -> null
            } ?: return@mapNotNull null
            val reasons = buildList {
                if (exact) add("exact filename") else {
                    if (sameYear) add("same year")
                    if (sameManufacturer) add("same manufacturer")
                    if (sameVehicle) add("same vehicle")
                    if (sameSlot) add("same slot")
                    if (sameSide) add("same side")
                    if (shared > 0) add("$shared filename token(s) match")
                }
                if (sameFormat) add("format matches") else add("format differs")
            }
            val score = shared * 10 +
                if (exact) 100 else 0 +
                if (sameYear) 20 else 0 +
                if (sameManufacturer) 25 else 0 +
                if (sameVehicle) 45 else 0 +
                if (sameSlot) 25 else 0 +
                if (sameSide) 12 else 0 +
                if (sameFormat) 5 else 0
            SmartMatchCandidate(entry, category, reasons, score)
        }
        .sortedWith(compareByDescending<SmartMatchCandidate> { it.score }.thenBy { it.entry.path })
        .take(limits.candidates)
        .toList()
}

private fun String.isSafeSmartMatchName() = length <= 255 && endsWith(".dds.z", true) && none { it == '/' || it == '\\' || it == '\u0000' } && this != "." && this != ".."

private fun isSafeDiscoveryEntry(root: String, directoryPath: String, entryPath: String): Boolean {
    val normalizedRoot = File(root).toPath().normalize()
    val normalizedDirectory = File(directoryPath).toPath().normalize()
    val normalizedEntry = File(entryPath).toPath().normalize()
    if (!normalizedEntry.startsWith(normalizedRoot) || !normalizedEntry.startsWith(normalizedDirectory)) return false
    val relativeDirectory = normalizedRoot.relativize(normalizedDirectory).toString().replace('\\', '/').split('/').filter(String::isNotBlank)
    val relativeEntry = normalizedRoot.relativize(normalizedEntry).toString().replace('\\', '/').split('/').filter(String::isNotBlank)
    return relativeDirectory.size == 4 && relativeDirectory[0] == "files" && relativeDirectory[1] == ".depot" &&
        relativeDirectory[2] == "vehicles" && relativeEntry.size == 5 &&
        relativeEntry[relativeEntry.lastIndex].endsWith(".dds.z", true)
}

private fun isSafeCandidatePath(root: String, path: String): Boolean {
    val normalizedRoot = File(root).toPath().normalize()
    val normalizedPath = File(path).toPath().normalize()
    if (!normalizedPath.startsWith(normalizedRoot)) return false
    val relative = normalizedRoot.relativize(normalizedPath).toString().replace('\\', '/').split('/').filter(String::isNotBlank)
    return relative.size >= 6 && relative[0] == "files" && relative[1] == ".depot" && relative[2] == "vehicles" &&
        relative[relative.lastIndex - 1].equals("livery", true)
}

class ShizukuSkinTargetBrowserSource(private val service: IRetr0UserService?) : SkinTargetBrowserSource {
    private val cache = SmartScanCache()

    override suspend fun list(packageRoot: String, path: String): BrowserResult = withContext(Dispatchers.IO) {
        val privileged = service ?: return@withContext BrowserResult.Error(
            SmartMatchDiagnostic(SmartMatchErrorCode.SERVICE_UNAVAILABLE, "AIDL_SERVICE_UNAVAILABLE").message,
            SmartMatchDiagnostic(SmartMatchErrorCode.SERVICE_UNAVAILABLE, "AIDL_SERVICE_UNAVAILABLE"),
        )
        Log.i(SMART_MATCH_TAG, "event=AIDL_INVOCATION operation=list path=${path.substringAfterLast('/')}")
        runCatching {
            val encodedEntries = privileged.listDirectoryEntries(packageRoot, path)
                ?: return@runCatching BrowserResult.Error(SmartMatchDiagnostic(SmartMatchErrorCode.TRANSPORT_FAILURE, "AIDL_NULL_RESPONSE").message)
            val parsed = encodedEntries.mapNotNull { it.toBrowserEntry(path) }
            Log.i(SMART_MATCH_TAG, "event=RECORD_PARSING raw=${encodedEntries.size} parsed=${parsed.size}")
            BrowserResult.Success(parsed)
        }.getOrElse {
            Log.e(SMART_MATCH_TAG, "code=TRANSPORT_FAILURE event=BROWSER_LIST_FAILURE path=${path.substringAfterLast('/')}", it)
            BrowserResult.Error(SmartMatchDiagnostic(SmartMatchErrorCode.TRANSPORT_FAILURE, "BROWSER_LIST_FAILURE").message)
        }
    }

    suspend fun bulkExactScan(packageRoot: String, importedFileName: String, limits: SmartScanLimits): BrowserResult = withContext(Dispatchers.IO) {
        val key = SmartScanCacheKey(packageRoot, importedFileName.lowercase(Locale.ROOT))
        cache.get(key)?.let {
            Log.i(SMART_MATCH_TAG, "event=BROWSER_SCAN_CACHE result=hit")
            return@withContext it.toBrowserResult()
        }
        Log.i(SMART_MATCH_TAG, "event=BROWSER_SCAN_CACHE result=miss")
        val privileged = service ?: return@withContext BrowserResult.Error(
            SmartMatchDiagnostic(SmartMatchErrorCode.SERVICE_UNAVAILABLE, "AIDL_SERVICE_UNAVAILABLE").message,
            SmartMatchDiagnostic(SmartMatchErrorCode.SERVICE_UNAVAILABLE, "AIDL_SERVICE_UNAVAILABLE"),
        )
        runCatching {
            Log.i(SMART_MATCH_TAG, "event=AIDL_INVOCATION operation=bulk_scan")
            val encoded = privileged.scanSkinTargets(packageRoot, "$packageRoot/files/.depot/vehicles", importedFileName, limits.directories, limits.entriesPerDirectory)
            var malformed = 0
            var invalidType = 0
            var invalidSize = 0
            val entries = encoded.mapNotNull { raw ->
                val normalized = raw
                    .replace("\\t", "\t")
                    .replace("\\\\", "\\")
                val parts = normalized.split('\t', limit = 3)
                if (parts.size != 3) {
                    malformed++
                    null
                } else if (parts[0] != "F") {
                    invalidType++
                    null
                } else {
                    val size = parts[1].toLongOrNull()
                    if (size == null) {
                        invalidSize++
                        null
                    } else {
                        val rawPath = parts[2].replace('\\', '/')
                        SkinBrowserEntry(rawPath.substringAfterLast('/'), rawPath, false, true, false, size)
                    }
                }
            }.filter { entry ->
                // Discovery is broader than final validation so related candidates reach ranking/UI.
                entry.name.endsWith(".dds.z", true) && !entry.isSymbolicLink &&
                    isSafeCandidatePath(packageRoot, entry.path)
            }
            val rejectedByPath = encoded.size - malformed - invalidType - invalidSize - entries.size
            Log.i(SMART_MATCH_TAG, "event=BULK_RECORD_SUMMARY raw=${encoded.size} parsed=${entries.size} malformed=$malformed invalidType=$invalidType invalidSize=$invalidSize rejectedByPath=$rejectedByPath imported=${importedFileName.take(80)}")
            if (entries.isEmpty() && encoded.isNotEmpty()) {
                val sample = encoded.first().replace("\\", "\\\\").take(180)
                Log.w(SMART_MATCH_TAG, "code=PARSE_FAILURE event=BULK_FIRST_RECORD_SAMPLE value=$sample")
            }
            val result = rankedResult(importedFileName, entries, limits)
            cache.put(key, result)
            result.toBrowserResult()
        }.getOrElse {
            Log.e(SMART_MATCH_TAG, "code=TRANSPORT_FAILURE event=BULK_SCAN_FAILURE", it)
            BrowserResult.Error(SmartMatchDiagnostic(SmartMatchErrorCode.TRANSPORT_FAILURE, "BULK_SCAN_FAILURE").message)
        }
    }

    private fun SmartScanResult.toBrowserResult(): BrowserResult = when (this) {
        is SmartScanResult.Error -> BrowserResult.Error(message)
        is SmartScanResult.Found -> BrowserResult.Success(listOf(candidate.entry))
        is SmartScanResult.Candidates -> BrowserResult.Success(entries.map { it.entry })
        SmartScanResult.NotFound -> BrowserResult.Error(SmartMatchDiagnostic(SmartMatchErrorCode.ZERO_CANDIDATES, "ZERO_CANDIDATES").message, SmartMatchDiagnostic(SmartMatchErrorCode.ZERO_CANDIDATES, "ZERO_CANDIDATES"))
    }

    private fun String.toBrowserEntry(parent: String): SkinBrowserEntry? {
        val parts = split('\t', limit = 3)
        if (parts.size != 3) return null
        val size = parts[1].toLongOrNull() ?: return null
        val name = parts[2]
        if (name.isBlank() || '/' in name || '\\' in name || name.indexOf('\u0000') >= 0) return null
        return SkinBrowserEntry(name, "${parent.trimEnd('/')}/$name", parts[0] == "D", parts[0] == "F", parts[0] == "L", size)
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
        require(segments.size >= 6 && segments[0] == "files" && segments[1] == ".depot" && segments[2] == "vehicles" && segments[3].isSafeName() && segments[segments.lastIndex - 1].equals("livery", ignoreCase = true)) { "Choose a file inside an RR3 vehicle livery folder." }
        require(entry.name.endsWith(".dds.z")) { "Target must be a .dds.z file." }
        require(targetStem(entry.name).equals(targetStem(importedFileName), ignoreCase = true)) { "Filename must match the selected skin's vehicle and slot." }
        val importedFormat = textureFormat(importedFileName)
        val targetFormat = textureFormat(entry.name)
        require(importedFormat.substringBefore('(') == targetFormat.substringBefore('(')) { "Texture formats do not match." }
        SelectedSkinTarget(normalizedTarget.toString().replace('\\', '/'), relative, entry.name, targetFormat)
    }

    fun textureFormat(name: String): String {
        val stem = name.removeSuffix(".dds.z").lowercase(Locale.ROOT)
        val formatToken = stem.substringAfterLast('.', "")
        val formatMatch = Regex("(?:^|_)(etc|atc)(?:\\((\\d+)\\))?(?:$|\\.)").find(stem)
        return when {
            formatMatch != null -> formatMatch.groupValues[1] + formatMatch.groupValues[2].takeIf { it.isNotBlank() }?.let { "($it)" }.orEmpty()
            formatToken == "etc" || formatToken == "atc" -> formatToken
            else -> "dds.z"
        }
    }

    fun targetStem(name: String): String = name.removeSuffix(".dds.z").substringBeforeLast('.')

    data class FilenameProfile(
        val year: String?,
        val manufacturer: String?,
        val vehicle: String?,
        val slot: String?,
        val side: String?,
        val format: String,
        val tokens: Set<String>,
    )

    fun searchTokens(name: String): Set<String> = targetStem(name).lowercase(Locale.ROOT).split(Regex("[^a-z0-9]+"))
        .filter { it.length >= 2 && it !in setOf("dds", "ext", "int", "one", "two") }.toSet()

    fun filenameProfile(name: String): FilenameProfile {
        val tokens = targetStem(name).lowercase(Locale.ROOT).split('_').filter(String::isNotBlank)
        val year = tokens.firstOrNull { it.matches(Regex("20\\d{2}")) }
        val manufacturer = tokens.getOrNull(1)
        val slotIndex = tokens.indexOfFirst { it in setOf("ext", "int") }
        val slot = tokens.getOrNull(slotIndex.takeIf { it >= 0 }?.plus(1) ?: -1)
        val side = tokens.firstOrNull { it in setOf("left", "right", "front", "rear", "one", "two") }
        val vehicle = tokens.drop(2).takeWhile { it !in setOf("ext", "int") }.joinToString("_").ifBlank { null }
        return FilenameProfile(year, manufacturer, vehicle, slot, side, textureFormat(name), searchTokens(name))
    }

    fun matchScore(importedTokens: Set<String>, importedFileName: String, candidateName: String): Int {
        val candidateTokens = searchTokens(candidateName)
        val shared = importedTokens.count { it in candidateTokens }
        val exact = targetStem(candidateName).equals(targetStem(importedFileName), ignoreCase = true)
        return shared * 10 + if (exact) 100 else 0
    }

    private fun String.isSafeName() = isNotBlank() && this != "." && this != ".." && indexOf('\u0000') < 0 && '/' !in this && '\\' !in this
}
