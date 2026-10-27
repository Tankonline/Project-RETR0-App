package com.retr0.projectretr0.installer.graphics

import org.json.JSONObject
import java.util.Locale

fun interface GraphicsLog {
    enum class Level { INFO, WARN, ERROR }
    fun log(level: Level, code: String, detail: String)
}

sealed class GraphicsResult {
    data class Success(val message: String, val backupId: String?) : GraphicsResult()
    /** Refused before anything in RR3 was changed. */
    data class Blocked(val code: String, val message: String) : GraphicsResult()
    /** Something failed mid-way; [rolledBack] says whether the previous state was put back. */
    data class Failed(val code: String, val message: String, val rolledBack: Boolean) : GraphicsResult()
}

enum class PresetCompatibility { OK, NEEDS_LOW_TRACK_ASSETS, UNKNOWN }

data class GraphicsBackupInfo(val id: String, val operation: String, val preset: String?, val createdAt: Long)

/**
 * Read-only scan of the HDR level RR3 will actually use.
 * [state] is the level shared by every Android tier ([HdrState.CUSTOM] with [mixed] = true when the
 * tiers differ). [tiers] gives each tier's state and [fromFactory] lists tiers with no `.depot` copy
 * (RR3 then uses apk/res). The `.feats` files hold no HDR switches; they are only checked to be readable.
 */
data class HdrScan(
    val state: HdrState,
    val mixed: Boolean,
    val tiers: Map<String, HdrState>,
    val fromFactory: List<String>,
    val featsReadable: Int,
    val featsChecked: Int,
    /** Each Android tier's PLIST_IDENTIFIER (null = unreadable). An applied preset keeps its own identifier in every tier. */
    val presets: Map<String, String?> = emptyMap(),
) {
    /** The preset applied to every Android tier, or null when the tiers differ (device default) or can't be read. */
    val activePreset: String? get() = presets.values.toSet().singleOrNull()
}

/**
 * Applies and restores RR3 graphics presets.
 *
 * Writes only to `.depot/eds`, `.depot/feats` and the graphics backup root. Plist/feats files are
 * encrypted game data, so they are only ever copied byte for byte, never parsed or edited.
 * Every change is preceded by a verified snapshot and is rolled back automatically on failure.
 *
 * Logging: every line is `code=GFX_xxxx op=<id> phase=<phase> stage=<stage> key=value...` under the
 * `RETR0_GRAPHICS` tag, so one operation can be followed with `adb logcat -s RETR0_GRAPHICS`.
 */
class GraphicsHubEngine(
    private val ops: GraphicsFileOps,
    private val paths: GraphicsPaths = GraphicsPaths(),
    private val clock: () -> Long = System::currentTimeMillis,
    private val log: GraphicsLog = GraphicsLog { _, _, _ -> },
    private val maxBackups: Int = 10,
) {
    private data class Wanted(val source: String, val sha: String)
    private class Abort(val result: GraphicsResult) : Exception()

    /** One managed folder: eds holds `.plist`, feats holds `.feats`. */
    private enum class Kind(val ext: String) { EDS(".plist"), FEATS(".feats") }

    private fun depotDir(pkg: String, kind: Kind) = if (kind == Kind.EDS) paths.depotEds(pkg) else paths.depotFeats(pkg)
    private fun resDir(pkg: String, kind: Kind) = if (kind == Kind.EDS) paths.resEds(pkg) else paths.resFeats(pkg)
    private fun folderName(kind: Kind) = if (kind == Kind.EDS) "eds" else "feats"

    /** Correlation id and phase included in every log line of the running operation. */
    private var opId = "-"
    private var phase = "idle"
    private var currentPkg: String? = null

    // ---------------------------------------------------------------- queries

    fun availablePresets(pkg: String): List<String> {
        if (!paths.isSupported(pkg)) return emptyList()
        val eds = presetNames(paths.resEds(pkg), Kind.EDS)
        val feats = presetNames(paths.resFeats(pkg), Kind.FEATS)
        return eds.intersect(feats).sorted()
    }

    private fun presetNames(dir: String, kind: Kind): Set<String> =
        ops.listFileNames(dir).filter { it.endsWith(kind.ext) && GraphicsPathValidator.MANAGED_NAME.matches(it) }
            .map { it.removeSuffix(kind.ext) }.toSet()

    fun compatibility(pkg: String, preset: String): PresetCompatibility {
        if (preset !in GraphicsPaths.LOW_TRACK_PRESETS) return PresetCompatibility.OK
        if (!paths.isSupported(pkg)) return PresetCompatibility.UNKNOWN
        val tracks = paths.depotTracks(pkg)
        if (!ops.isDirectory(tracks)) return PresetCompatibility.UNKNOWN
        val names = ops.listDirectoryNames(tracks)
        if (names.isEmpty()) return PresetCompatibility.UNKNOWN
        val hasLow = names.any { ops.isDirectory("$tracks/$it/processed/low") }
        return if (hasLow) PresetCompatibility.OK else PresetCompatibility.NEEDS_LOW_TRACK_ASSETS
    }

    fun listBackups(pkg: String): List<GraphicsBackupInfo> {
        if (!paths.isSupported(pkg)) return emptyList()
        val root = paths.packageBackupRoot(pkg)
        return ops.listDirectoryNames(root)
            .filter { GraphicsPaths.SAFE_BACKUP_ID.matches(it) }
            .mapNotNull { id ->
                val json = ops.readText("$root/$id/manifest.json") ?: return@mapNotNull null
                runCatching {
                    val m = JSONObject(json)
                    GraphicsBackupInfo(id, m.optString("operation"), m.optString("preset").ifBlank { null }, m.optLong("createdAt"))
                }.getOrNull()
            }
            .sortedWith(compareByDescending<GraphicsBackupInfo> { it.createdAt }.thenByDescending { it.id })
    }

    // ---------------------------------------------------------------- operations

    fun applyUniversal(pkg: String, preset: String, allowIncompatible: Boolean = false, progress: (String) -> Unit = {}): GraphicsResult = guarded(pkg, "apply") {
        if (!GraphicsPaths.SAFE_PRESET.matches(preset)) throw Abort(GraphicsResult.Blocked("GFX_E003", "Invalid preset name."))
        info("GFX_I001", "start", "pkg=${short(pkg)} preset=$preset allowIncompatible=$allowIncompatible")

        val compat = compatibility(pkg, preset)
        info("GFX_I011", "compatibility", "preset=$preset result=$compat lowTrackPresets=${GraphicsPaths.LOW_TRACK_PRESETS.joinToString(",")}")
        when (compat) {
            PresetCompatibility.NEEDS_LOW_TRACK_ASSETS -> if (!allowIncompatible) throw Abort(
                GraphicsResult.Blocked(
                    "GFX_E020",
                    "$preset needs low-detail track files that this install doesn't have. Applying it would show a black screen. " +
                        "Choose Mid 2, High, Xtra High, Ultra Wide or SimRigTV.",
                ),
            ) else warn("GFX_W021", "compat_override", "preset=$preset reason=user_confirmed")
            PresetCompatibility.UNKNOWN -> warn("GFX_W022", "compat_unknown", "preset=$preset reason=tracks_folder_unreadable")
            PresetCompatibility.OK -> Unit
        }

        publishUniversal(pkg, preset, "${paths.resEds(pkg)}/$preset.plist", "${paths.resFeats(pkg)}/$preset.feats", progress)
    }

    /**
     * Universal override from arbitrary source files (e.g. a patched plist staged in the backup
     * area). Same backup / atomic publish / verify / rollback as [applyUniversal]; no compatibility gate.
     */
    fun applyUniversalFrom(pkg: String, label: String, sourcePlist: String, sourceFeats: String, progress: (String) -> Unit = {}): GraphicsResult =
        guarded(pkg, "apply_custom") {
            info("GFX_I001", "start", "pkg=${short(pkg)} label=$label plist=${rel(pkg, sourcePlist)} feats=${rel(pkg, sourceFeats)}")
            publishUniversal(pkg, label, sourcePlist, sourceFeats, progress)
        }

    /**
     * The Graphics Hub's apply: the selected factory preset with HDR [level], staged in the backup area
     * and then published to every Android tier. The preset gets exactly that level (higher-level
     * switches turned off).
     * Only the HDR values change; everything else (including PLIST_IDENTIFIER) stays byte for byte.
     */
    fun applyUniversalWithHdr(pkg: String, preset: String, level: HdrLevel, progress: (String) -> Unit = {}): GraphicsResult =
        guarded(pkg, "apply_hdr") {
            if (!GraphicsPaths.SAFE_PRESET.matches(preset)) throw Abort(GraphicsResult.Blocked("GFX_E003", "Invalid preset name."))
            info("GFX_I001", "start", "pkg=${short(pkg)} preset=$preset hdr=${level.name}")
            val source = "${paths.resEds(pkg)}/$preset.plist"
            val feats = "${paths.resFeats(pkg)}/$preset.feats"
            progress("Building $preset with the HDR fix...")
            val bytes = ops.readBytes(source)
                ?: throw Abort(GraphicsResult.Blocked("GFX_E004", "Could not read $preset.plist from apk/res."))
            if (!PlistCipher.fits(bytes.size)) {
                throw Abort(GraphicsResult.Blocked("GFX_E014", "$preset.plist is larger than the HDR decoder supports (${bytes.size} > ${PlistCipher.capacity} bytes)."))
            }
            val text = PlistCipher.decodePlist(bytes)
                ?: throw Abort(GraphicsResult.Blocked("GFX_E015", "$preset.plist could not be decoded (different RR3 version?)."))
            val patched = try {
                HdrPatcher.applyExact(text, level)
            } catch (e: IllegalArgumentException) {
                throw Abort(GraphicsResult.Blocked("GFX_E016", "Could not apply the HDR fix to $preset: ${e.message}"))
            }
            val encoded = PlistCipher.transform(patched.toByteArray(Charsets.UTF_8))
            if (encoded == null || PlistCipher.decodePlist(encoded) != patched) {
                throw Abort(GraphicsResult.Blocked("GFX_E016", "HDR fix for $preset failed its round-trip check."))
            }
            val stageDir = "${paths.backupRoot}/hdr"
            val stage = "$stageDir/${preset}_hdr_${level.name}.plist"
            if (!ops.createDirectory(stageDir) || !ops.writeBytes(stage, encoded) || ops.readBytes(stage)?.contentEquals(encoded) != true) {
                throw Abort(GraphicsResult.Blocked("GFX_E017", "Could not stage the HDR preset in Documents/ProjectRETR0."))
            }
            val before = HdrPatcher.ALL_KEYS.joinToString(",") { "${it.removePrefix("USE_")}=${HdrPatcher.readBool(text, it)}" }
            info("GFX_I013", "hdr_patch", "preset=$preset level=${level.name} exact=true set=${level.keys.joinToString(",")} before[$before] after=${HdrPatcher.detect(patched)} bytes=${bytes.size}->${encoded.size}")
            publishUniversal(pkg, "$preset+HDR-${level.name}", stage, feats, progress)
        }

    private fun publishUniversal(pkg: String, preset: String, sourcePlist: String, sourceFeats: String, progress: (String) -> Unit): GraphicsResult {
        val plistExists = ops.exists(sourcePlist)
        val featsExists = ops.exists(sourceFeats)
        if (!plistExists || !featsExists) {
            throw Abort(GraphicsResult.Blocked("GFX_E004", "Complete preset pair not found: $preset (plist=${yes(plistExists)}, feats=${yes(featsExists)})."))
        }
        val plistSha = ops.sha256(sourcePlist)
        val featsSha = ops.sha256(sourceFeats)
        info("GFX_I012", "source", "plist=${rel(pkg, sourcePlist)} bytes=${ops.size(sourcePlist)} sha=${sha8(plistSha)} feats=${rel(pkg, sourceFeats)} bytes=${ops.size(sourceFeats)} sha=${sha8(featsSha)}")
        if (plistSha.isBlank() || featsSha.isBlank()) {
            throw Abort(GraphicsResult.Blocked("GFX_E005", "Could not read the preset files (plistHash=${sha8(plistSha)}, featsHash=${sha8(featsSha)})."))
        }

        prepare(pkg)
        progress("Backing up current graphics...")
        val safeLabel = preset.lowercase(Locale.ROOT).replace(Regex("[^a-z0-9,_-]"), "-")
        val backupId = snapshot(pkg, "apply-$safeLabel", preset)

        val plan = mapOf(
            Kind.EDS to currentPlan(pkg, Kind.EDS) + GraphicsPaths.ANDROID_TIERS.associate { "$it.plist" to Wanted(sourcePlist, plistSha) },
            Kind.FEATS to currentPlan(pkg, Kind.FEATS) + GraphicsPaths.ANDROID_TIERS.associate { "$it.feats" to Wanted(sourceFeats, featsSha) },
        )
        commit(pkg, plan, backupId, progress)
        prune(pkg, backupId)
        return GraphicsResult.Success("$preset graphics applied to all Android tiers. Start RR3 to use it.", backupId)
    }

    fun restoreFactory(pkg: String, hdrLevel: HdrLevel? = null, progress: (String) -> Unit = {}): GraphicsResult = guarded(pkg, "restore") {
        info("GFX_I001", "start", "pkg=${short(pkg)} operation=restore_factory")
        val plan = Kind.entries.associateWith { kind ->
            val dir = resDir(pkg, kind)
            if (!ops.isDirectory(dir)) throw Abort(GraphicsResult.Blocked("GFX_E006", "Original graphics source requires both apk/res/eds and apk/res/feats (missing ${rel(pkg, dir)})."))
            val names = ops.listFileNames(dir).filter { it.endsWith(kind.ext) && GraphicsPathValidator.MANAGED_NAME.matches(it) }
            if (names.isEmpty()) throw Abort(GraphicsResult.Blocked("GFX_E006", "apk/res/${folderName(kind)} contains no graphics files."))
            names.associateWith { name ->
                val sha = ops.sha256("$dir/$name")
                if (sha.isBlank()) throw Abort(GraphicsResult.Blocked("GFX_E005", "Could not read apk/res/${folderName(kind)}/$name."))
                Wanted("$dir/$name", sha)
            }
        }
        info("GFX_I012", "source", "eds=${plan[Kind.EDS]?.size} feats=${plan[Kind.FEATS]?.size} from=files/apk/res hdr=${hdrLevel?.name ?: "none"}")
        prepare(pkg)
        val finalPlan = if (hdrLevel == null) plan else plan + (Kind.EDS to hdrPatched(plan.getValue(Kind.EDS), hdrLevel, "factory", progress))
        progress("Backing up current graphics...")
        val backupId = snapshot(pkg, if (hdrLevel == null) "restore-factory" else "restore-factory-hdr-${hdrLevel.name.lowercase(Locale.ROOT)}", null)
        commit(pkg, finalPlan, backupId, progress)
        prune(pkg, backupId)
        GraphicsResult.Success(if (hdrLevel == null) "Original graphics restored." else "Original graphics restored with the HDR fix.", backupId)
    }

    /**
     * Each plist in [eds] set to exactly HDR [level] (each keeps its own identity and other values).
     * Files that can't be decoded are kept unchanged and logged. [source] names the staging folder.
     */
    private fun hdrPatched(eds: Map<String, Wanted>, level: HdrLevel, source: String, progress: (String) -> Unit): Map<String, Wanted> {
        progress("Applying HDR level ${level.name}...")
        val stageDir = "${paths.backupRoot}/hdr/$source-${level.name}"
        if (!ops.createDirectory(stageDir)) throw Abort(GraphicsResult.Blocked("GFX_E017", "Could not create the HDR staging folder in Documents/ProjectRETR0."))
        var patched = 0
        val skipped = mutableListOf<String>()
        val out = eds.mapValues { (name, want) ->
            val bytes = ops.readBytes(want.source)
            val text = bytes?.let { PlistCipher.decodePlist(it) }
            val result = text?.let { runCatching { HdrPatcher.applyExact(it, level) }.getOrNull() }
            val encoded = result?.let { PlistCipher.transform(it.toByteArray(Charsets.UTF_8)) }
            if (encoded == null) {
                skipped += name
                return@mapValues want
            }
            val stage = "$stageDir/$name"
            if (!ops.writeBytes(stage, encoded) || ops.readBytes(stage)?.contentEquals(encoded) != true) {
                throw Abort(GraphicsResult.Blocked("GFX_E017", "Could not stage the HDR version of $name."))
            }
            patched++
            Wanted(stage, ops.sha256(stage))
        }
        info("GFX_I015", "hdr_$source", "level=${level.name} exact=true patched=$patched unchanged=${skipped.size}${if (skipped.isEmpty()) "" else " unchangedList=${skipped.joinToString(",")}"}")
        if (skipped.isNotEmpty()) warn("GFX_W016", "hdr_${source}_skipped", "files=${skipped.joinToString(",")} reason=not_decodable")
        return out
    }

    /**
     * Sets the current graphics to exactly HDR [level] (the Hub's A/B/C switches). Every managed
     * `.depot` plist is patched in place; Android tiers without a `.depot` copy get their factory plist,
     * patched. Feats are untouched. Nothing is written (and no backup made) when the files already match.
     */
    fun applyHdrToCurrent(pkg: String, level: HdrLevel, progress: (String) -> Unit = {}): GraphicsResult = guarded(pkg, "set_hdr") {
        info("GFX_I001", "start", "pkg=${short(pkg)} operation=set_hdr level=${level.name}")
        val eds = currentPlan(pkg, Kind.EDS).toMutableMap()
        val added = GraphicsPaths.ANDROID_TIERS.map { "$it.plist" }.filter { it !in eds }.mapNotNull { name ->
            val res = "${paths.resEds(pkg)}/$name"
            if (!ops.exists(res)) return@mapNotNull null
            eds[name] = Wanted(res, ops.sha256(res))
            name
        }
        if (eds.isEmpty()) throw Abort(GraphicsResult.Blocked("GFX_E006", "No graphics files found to update."))
        val patchedEds = hdrPatched(eds, level, "current", progress)
        val current = currentHashes(pkg, Kind.EDS, quiet = true)
        val changes = patchedEds.filter { (name, want) -> current[name] != want.sha }
        info("GFX_I016", "hdr_current_plan", "level=${level.name} files=${patchedEds.size} change=${changes.size} addedFromFactory=${added.joinToString(",").ifEmpty { "none" }}")
        if (changes.isEmpty()) return@guarded GraphicsResult.Success("HDR level ${level.name} is already applied.", null)
        prepare(pkg)
        progress("Backing up current graphics...")
        val backupId = snapshot(pkg, "set-hdr-${level.name.lowercase(Locale.ROOT)}", null)
        commit(pkg, mapOf(Kind.EDS to patchedEds, Kind.FEATS to currentPlan(pkg, Kind.FEATS)), backupId, progress)
        prune(pkg, backupId)
        GraphicsResult.Success("HDR level ${level.name} applied. Start RR3 to use it.", backupId)
    }

    /** Read-only: the HDR level in the plists RR3 will load for each Android tier (`.depot` first, else apk/res). */
    fun scanHdr(pkg: String): HdrScan? {
        if (!paths.isSupported(pkg)) return null
        val tiers = linkedMapOf<String, HdrState>()
        val presets = linkedMapOf<String, String?>()
        val fromFactory = mutableListOf<String>()
        var featsReadable = 0
        for (tier in GraphicsPaths.ANDROID_TIERS) {
            val depot = "${paths.depotEds(pkg)}/$tier.plist"
            val path = if (ops.exists(depot)) depot else "${paths.resEds(pkg)}/$tier.plist".also { fromFactory += tier }
            val text = ops.readBytes(path)?.let { PlistCipher.decodePlist(it) }
            tiers[tier] = text?.let { HdrPatcher.detect(it) } ?: HdrState.UNKNOWN
            presets[tier] = text?.let { HdrPatcher.identifier(it) }
            val featsDepot = "${paths.depotFeats(pkg)}/$tier.feats"
            val feats = ops.readBytes(if (ops.exists(featsDepot)) featsDepot else "${paths.resFeats(pkg)}/$tier.feats")
                ?.let { PlistCipher.transform(it) }?.toString(Charsets.UTF_8)
            if (feats != null && feats.startsWith("<ShaderFeatures>") && feats.trimEnd().endsWith("</ShaderFeatures>")) featsReadable++
        }
        val distinct = tiers.values.toSet()
        val scan = HdrScan(
            state = distinct.singleOrNull() ?: HdrState.CUSTOM,
            mixed = distinct.size > 1,
            tiers = tiers,
            fromFactory = fromFactory,
            featsReadable = featsReadable,
            featsChecked = GraphicsPaths.ANDROID_TIERS.size,
            presets = presets,
        )
        log.log(
            GraphicsLog.Level.INFO, "GFX_I017",
            "op=scan phase=read stage=hdr_scan pkg=${short(pkg)} state=${scan.state} mixed=${scan.mixed} " +
                "tiers[${tiers.entries.joinToString(",") { "${it.key}=${it.value}" }}] fromFactory=${fromFactory.joinToString(",").ifEmpty { "none" }} " +
                "feats=${featsReadable}/${scan.featsChecked} activePreset=${scan.activePreset ?: "none"}",
        )
        return scan
    }

    /**
     * Restores a snapshot. [backupId] may be "latest". With [hdrLevel], every restored plist is set to
     * exactly that level (the Hub's selection); null restores the backup byte for byte.
     */
    fun restoreBackup(pkg: String, backupId: String, hdrLevel: HdrLevel? = null, progress: (String) -> Unit = {}): GraphicsResult = guarded(pkg, "restore_backup") {
        val id = if (backupId == "latest") {
            listBackups(pkg).firstOrNull()?.id ?: throw Abort(GraphicsResult.Blocked("GFX_E041", "No graphics backup found."))
        } else backupId
        if (!GraphicsPaths.SAFE_BACKUP_ID.matches(id)) throw Abort(GraphicsResult.Blocked("GFX_E042", "Invalid backup id."))
        info("GFX_I001", "start", "pkg=${short(pkg)} operation=restore_backup backup=$id requested=$backupId hdr=${hdrLevel?.name ?: "none"}")
        val snapshotPlan = loadSnapshotPlan(pkg, id) ?: throw Abort(GraphicsResult.Blocked("GFX_E040", "Backup $id is missing or damaged; nothing was changed."))
        val plan = if (hdrLevel == null) snapshotPlan
        else snapshotPlan + (Kind.EDS to hdrPatched(snapshotPlan.getValue(Kind.EDS), hdrLevel, "backup", progress))
        prepare(pkg)
        progress("Backing up current graphics...")
        val newBackup = snapshot(pkg, "restore-backup", null)
        commit(pkg, plan, newBackup, progress)
        prune(pkg, newBackup)
        GraphicsResult.Success(if (hdrLevel == null) "Previous graphics restored." else "Previous graphics restored with HDR level ${hdrLevel.name}.", newBackup)
    }

    // ---------------------------------------------------------------- internals

    private inline fun guarded(pkg: String, operation: String, block: () -> GraphicsResult): GraphicsResult {
        opId = "$operation#${(clock() % 100_000).toString().padStart(5, '0')}"
        phase = "preflight"
        currentPkg = pkg
        val started = System.nanoTime()
        if (!paths.isSupported(pkg)) {
            logError("GFX_E002", "blocked", "pkg=$pkg reason=unsupported_package")
            return GraphicsResult.Blocked("GFX_E002", "Unsupported RR3 package.")
        }
        val result = try {
            block()
        } catch (abort: Abort) {
            abort.result
        } catch (e: Exception) {
            logError("GFX_E098", "exception", "type=${e.javaClass.name} message=${q(e.message)} at=${e.stackTrace.firstOrNull()}")
            GraphicsResult.Failed("GFX_E098", "Graphics error: ${e.javaClass.simpleName}: ${e.message}", rolledBack = false)
        } finally {
            cleanupTemps(pkg)
        }
        val ms = (System.nanoTime() - started) / 1_000_000
        phase = "done"
        when (result) {
            is GraphicsResult.Success -> info("GFX_I009", "done", "result=success backup=${result.backupId} ms=$ms")
            is GraphicsResult.Blocked -> logError(result.code, "done", "result=blocked changed=false ms=$ms message=${q(result.message)}")
            is GraphicsResult.Failed -> logError(result.code, "done", "result=failed rolledBack=${result.rolledBack} ms=$ms message=${q(result.message)}")
        }
        phase = "idle"
        return result
    }

    /** Checks the Shizuku helper, stops RR3 and makes sure both graphics folders exist (the only directories this code creates). */
    private fun prepare(pkg: String) {
        val api = ops.serviceApiVersion()
        info("GFX_I006", "environment", "serviceApi=$api required=${GraphicsPathValidator.SERVICE_API} ${ops.environment(paths.packageRoot(pkg))}")
        if (api in 0 until GraphicsPathValidator.SERVICE_API) {
            throw Abort(
                GraphicsResult.Blocked(
                    "GFX_E008",
                    "The Shizuku helper is running old code (version $api, need ${GraphicsPathValidator.SERVICE_API}). " +
                        "If you installed from Android Studio, turn on Run > Edit Configurations > app > \"Always install with package manager\" and run again. " +
                        "Otherwise install the APK normally, then restart Shizuku.",
                ),
            )
        }
        val stop = ops.ensureGameStopped(pkg)
        info("GFX_I007", "game_stop", "pkg=${short(pkg)} stopped=${stop.ok} ${stop.detail}")
        if (!stop.ok) throw Abort(GraphicsResult.Blocked("GFX_E001", "RR3 is still running; graphics were not changed (${stop.detail})."))
        for (kind in Kind.entries) {
            val dir = depotDir(pkg, kind)
            if (!ops.isDirectory(dir)) {
                val created = ops.createDirectory(dir)
                warn("GFX_W008", "create_folder", "folder=${rel(pkg, dir)} created=$created reason=missing")
                if (!created) throw Abort(GraphicsResult.Blocked("GFX_E007", "Could not open .depot/${folderName(kind)}."))
            }
        }
    }

    /** Managed files currently in a depot folder, mapped to "keep as is". */
    private fun currentPlan(pkg: String, kind: Kind): Map<String, Wanted> {
        val dir = depotDir(pkg, kind)
        return currentHashes(pkg, kind).mapValues { (name, sha) -> Wanted("$dir/$name", sha) }
    }

    private fun currentHashes(pkg: String, kind: Kind, quiet: Boolean = false): Map<String, String> {
        val dir = depotDir(pkg, kind)
        val result = linkedMapOf<String, String>()
        for (name in ops.listFileNames(dir)) {
            if (GraphicsPathValidator.TEMP_NAME.matches(name)) {
                if (!quiet) warn("GFX_W031", "leftover_temp", "folder=${folderName(kind)} name=$name")
                continue
            }
            if (!name.endsWith(kind.ext) || !GraphicsPathValidator.MANAGED_NAME.matches(name)) {
                if (!quiet) warn("GFX_W030", "unmanaged_file", "folder=${folderName(kind)} name=$name")
                continue
            }
            result[name] = ops.sha256("$dir/$name")
        }
        return result
    }

    /** Copies every managed eds/feats file to a new verified backup. Returns its id. */
    private fun snapshot(pkg: String, operation: String, preset: String?): String {
        phase = "snapshot"
        val started = System.nanoTime()
        var id = "${clock()}-$operation"
        var n = 1
        while (ops.exists(paths.backupDir(pkg, id))) id = "${clock()}-$operation-${n++}"
        val dir = paths.backupDir(pkg, id)
        val manifest = JSONObject()
            .put("version", 1).put("package", pkg).put("id", id).put("operation", operation)
            .put("preset", preset ?: "").put("createdAt", clock())
        val counts = mutableListOf<String>()
        try {
            for (kind in Kind.entries) {
                val target = "$dir/${folderName(kind)}"
                if (!ops.createDirectory(target)) throw IllegalStateException("mkdir ${rel(pkg, target)} failed")
                val files = JSONObject()
                var bytes = 0L
                val current = currentHashes(pkg, kind)
                for ((name, sha) in current) {
                    val from = "${depotDir(pkg, kind)}/$name"
                    if (sha.isBlank()) throw IllegalStateException("cannot hash ${folderName(kind)}/$name")
                    val copy = ops.copyVerified(from, "$target/$name", sha)
                    if (!copy.ok) throw IllegalStateException("copy ${folderName(kind)}/$name: ${copy.detail}")
                    bytes += ops.size(from).coerceAtLeast(0)
                    files.put(name, sha)
                }
                manifest.put(folderName(kind), files)
                counts += "${folderName(kind)}=${current.size}files/${bytes}B"
            }
            if (!ops.writeText("$dir/manifest.json", manifest.toString(2))) throw IllegalStateException("manifest write failed")
        } catch (e: Exception) {
            val cleaned = ops.deleteBackupPath(dir)
            logError("GFX_E010", "snapshot_failed", "backup=$id reason=${q(e.message)} partialRemoved=$cleaned")
            throw Abort(GraphicsResult.Blocked("GFX_E010", "Could not back up current graphics (${e.message}); nothing was changed."))
        }
        info("GFX_I002", "snapshot_ok", "backup=$id ${counts.joinToString(" ")} path=${relBackup(dir)} ms=${(System.nanoTime() - started) / 1_000_000}")
        return id
    }

    /** Reads a snapshot and checks every file against its manifest. Null when missing or damaged (reason is logged). */
    private fun loadSnapshotPlan(pkg: String, id: String): Map<Kind, Map<String, Wanted>>? {
        val dir = paths.backupDir(pkg, id)
        fun bad(reason: String): Map<Kind, Map<String, Wanted>>? {
            logError("GFX_E040", "snapshot_invalid", "backup=$id reason=$reason")
            return null
        }
        val json = ops.readText("$dir/manifest.json") ?: return bad("manifest_missing")
        val manifest = runCatching { JSONObject(json) }.getOrNull() ?: return bad("manifest_unreadable")
        val plan = mutableMapOf<Kind, Map<String, Wanted>>()
        for (kind in Kind.entries) {
            val files = manifest.optJSONObject(folderName(kind)) ?: return bad("manifest_has_no_${folderName(kind)}")
            val entries = linkedMapOf<String, Wanted>()
            for (name in files.keys()) {
                if (!name.endsWith(kind.ext) || !GraphicsPathValidator.MANAGED_NAME.matches(name)) return bad("bad_name ${folderName(kind)}/$name")
                val sha = files.optString(name)
                val path = "$dir/${folderName(kind)}/$name"
                val actual = ops.sha256(path)
                if (sha.isBlank() || actual != sha) return bad("hash_mismatch file=${folderName(kind)}/$name expected=${sha8(sha)} actual=${sha8(actual)}")
                entries[name] = Wanted(path, sha)
            }
            plan[kind] = entries
        }
        return plan
    }

    /** Publishes [plan], verifies it, and rolls back to [backupId] on any failure. */
    private fun commit(pkg: String, plan: Map<Kind, Map<String, Wanted>>, backupId: String, progress: (String) -> Unit) {
        phase = "publish"
        val failure = publish(pkg, plan, progress) ?: run { phase = "verify"; verify(pkg, plan) }
        if (failure == null) {
            info("GFX_I004", "verify_ok", "backup=$backupId eds=${plan[Kind.EDS]?.size} feats=${plan[Kind.FEATS]?.size}")
            return
        }
        logError("GFX_E030", "publish_failed", "backup=$backupId detail=${q(failure)}")
        progress("Something went wrong. Putting the previous graphics back...")
        phase = "rollback"
        info("GFX_I050", "rollback_start", "backup=$backupId")
        val rollbackPlan = loadSnapshotPlan(pkg, backupId)
        val rollbackFailure = when {
            rollbackPlan == null -> "snapshot unreadable"
            else -> publish(pkg, rollbackPlan) {} ?: verify(pkg, rollbackPlan)
        }
        if (rollbackFailure == null) info("GFX_I051", "rollback_ok", "backup=$backupId")
        else logError("GFX_E051", "rollback_failed", "backup=$backupId detail=${q(rollbackFailure)}")
        throw Abort(
            if (rollbackFailure == null) {
                GraphicsResult.Failed("GFX_E030", "Graphics could not be applied ($failure). Your previous graphics were put back.", rolledBack = true)
            } else {
                GraphicsResult.Failed("GFX_E099", "Graphics changes could not be undone automatically ($rollbackFailure). Use Restore original graphics. Backup: $backupId", rolledBack = false)
            },
        )
    }

    /** Makes each depot folder match the plan: changed files are replaced, unplanned managed files deleted. Returns a failure description or null. */
    private fun publish(pkg: String, plan: Map<Kind, Map<String, Wanted>>, progress: (String) -> Unit): String? {
        val total = plan.values.sumOf { it.size }
        var index = 0
        var written = 0
        for (kind in listOf(Kind.FEATS, Kind.EDS)) {
            val dir = depotDir(pkg, kind)
            val folder = folderName(kind)
            val wanted = plan[kind].orEmpty()
            val current = currentHashes(pkg, kind)
            val toWrite = wanted.filter { current[it.key] != it.value.sha }
            val stale = current.keys - wanted.keys
            info("GFX_I020", "plan", "folder=$folder planned=${wanted.size} write=${toWrite.size} delete=${stale.size} unchanged=${wanted.size - toWrite.size} writeList=${toWrite.keys.joinToString(",")}${if (stale.isEmpty()) "" else " deleteList=${stale.joinToString(",")}"}")
            for ((name, want) in wanted) {
                index++
                if (current[name] == want.sha) continue
                progress("Writing $name ($index/$total)")
                val target = "$dir/$name"
                val started = System.nanoTime()
                val r = ops.publishGraphicsFile(want.source, target, want.sha)
                val ms = (System.nanoTime() - started) / 1_000_000
                val where = "file=$folder/$name from=${rel(pkg, want.source)} sha=${sha8(want.sha)} was=${current[name]?.let(::sha8) ?: "absent"} ms=$ms"
                if (!r.ok) {
                    logError("GFX_E031", "write_failed", "$where reason=${q(r.detail)}")
                    return "write $folder/$name: ${r.detail}"
                }
                info("GFX_I021", "write_ok", "$where ${r.detail}".trim())
                written++
            }
            for (name in stale) {
                progress("Removing $name")
                val r = ops.deleteGraphicsFile("$dir/$name")
                if (!r.ok) {
                    logError("GFX_E032", "delete_failed", "file=$folder/$name reason=${q(r.detail)}")
                    return "delete $folder/$name: ${r.detail}"
                }
                info("GFX_I022", "delete_ok", "file=$folder/$name")
                written++
            }
        }
        info("GFX_I003", "publish_ok", "changed=$written")
        return null
    }

    private fun verify(pkg: String, plan: Map<Kind, Map<String, Wanted>>): String? {
        for (kind in Kind.entries) {
            val folder = folderName(kind)
            val current = currentHashes(pkg, kind, quiet = true)
            val wanted = plan[kind].orEmpty()
            if (current.keys != wanted.keys) {
                val missing = wanted.keys - current.keys
                val extra = current.keys - wanted.keys
                val detail = "file list mismatch in $folder missing=${missing.joinToString(",").ifEmpty { "none" }} extra=${extra.joinToString(",").ifEmpty { "none" }}"
                logError("GFX_E033", "verify_failed", detail)
                return detail
            }
            for ((name, want) in wanted) {
                val actual = current[name]
                if (actual != want.sha) {
                    val detail = "hash mismatch $folder/$name expected=${sha8(want.sha)} actual=${sha8(actual.orEmpty())}"
                    logError("GFX_E033", "verify_failed", detail)
                    return detail
                }
            }
        }
        return null
    }

    private fun cleanupTemps(pkg: String) {
        if (!paths.isSupported(pkg)) return
        runCatching {
            for (kind in Kind.entries) {
                val dir = depotDir(pkg, kind)
                ops.listFileNames(dir).filter { GraphicsPathValidator.TEMP_NAME.matches(it) }.forEach {
                    val r = ops.deleteGraphicsFile("$dir/$it")
                    if (r.ok) warn("GFX_W032", "temp_removed", "file=${folderName(kind)}/$it")
                    else logError("GFX_E034", "temp_remove_failed", "file=${folderName(kind)}/$it reason=${q(r.detail)}")
                }
            }
        }
    }

    /** Keeps the newest [maxBackups] snapshots; never deletes [keep]. */
    private fun prune(pkg: String, keep: String) {
        runCatching {
            val old = listBackups(pkg).filter { it.id != keep }.drop((maxBackups - 1).coerceAtLeast(0))
            old.forEach { ops.deleteBackupPath(paths.backupDir(pkg, it.id)) }
            if (old.isNotEmpty()) info("GFX_I060", "prune", "removed=${old.size} kept=$maxBackups")
        }
    }

    // ---------------------------------------------------------------- log helpers

    private fun short(pkg: String) = pkg.substringAfterLast('.')
    private fun sha8(sha: String) = sha.take(8).ifEmpty { "none" }
    private fun yes(b: Boolean) = if (b) "found" else "missing"
    private fun q(text: String?) = "\"${text.orEmpty().replace('"', '\'').replace('\n', ' ')}\""

    /** Path relative to the RR3 package root, e.g. `files/apk/res/feats/AndroidLow.feats`. */
    private fun rel(pkg: String, path: String): String {
        val root = paths.packageRoot(pkg).trimEnd('/') + "/"
        if (path.startsWith(root)) return path.removePrefix(root)
        return relBackup(path)
    }
    private fun relBackup(path: String): String {
        val root = paths.backupRoot.trimEnd('/') + "/"
        return if (path.startsWith(root)) "backups/" + path.removePrefix(root) else path
    }

    private fun line(stage: String, details: String) = "op=$opId phase=$phase stage=$stage $details".trim()
    private fun info(code: String, stage: String, details: String = "") = log.log(GraphicsLog.Level.INFO, code, line(stage, details))
    private fun warn(code: String, stage: String, details: String = "") = log.log(GraphicsLog.Level.WARN, code, line(stage, details))
    private fun logError(code: String, stage: String, details: String = "") = log.log(GraphicsLog.Level.ERROR, code, line(stage, details))
}
