package com.retr0.projectretr0.installer.graphics

/**
 * Turns on RR3's HDR rendering switches inside a preset.
 *
 * Finding (Adreno 840, Android 16): every preset with HDR post-processing off shows a black screen.
 * Device tests showed level A alone is not enough; B (HDR post-process + tonemap + gamma) and C fix it.
 * The Graphics Hub lets the user pick exactly one level (A, B or C; B by default) and shows the level
 * currently in the game files (see [HdrPatcher.detect]).
 */
enum class HdrLevel(val label: String, val keys: List<String>) {
    A("HDR post-process only", listOf("USE_HDR_POST_PROCESS")),
    B("+ tonemap & gamma", listOf("USE_HDR_POST_PROCESS", "TONEMAP", "GAMMA_CORRECTION")),
    C("All HDR switches", listOf("USE_HDR_POST_PROCESS", "TONEMAP", "GAMMA_CORRECTION", "ENV_MAP_HDR", "USE_HDR_AUTOMATIC_EXPOSURE")),
}

/** What a plist's five HDR switches add up to. */
enum class HdrState {
    /** All five HDR switches off: no fix (factory state of most presets). */
    NONE,
    A, B, C,
    /** A combination that matches no level. */
    CUSTOM,
    /** The file could not be read or decoded. */
    UNKNOWN;

    val level: HdrLevel? get() = when (this) { A -> HdrLevel.A; B -> HdrLevel.B; C -> HdrLevel.C; else -> null }
}

object HdrPatcher {
    /** Every switch any level may change, in display order. */
    val ALL_KEYS = HdrLevel.C.keys

    /** Reads a boolean key from plist text: true, false, or null when absent. */
    fun readBool(plist: String, key: String): Boolean? {
        val m = Regex("<key>${Regex.escape(key)}</key>\\s*<(true|false)/>").find(plist) ?: return null
        return m.groupValues[1] == "true"
    }

    fun identifier(plist: String): String? =
        Regex("<key>PLIST_IDENTIFIER</key>\\s*<string>([^<]*)</string>").find(plist)?.groupValues?.get(1)

    /**
     * Returns the plist text with the level's switches set to true. Only those values change;
     * everything else (including PLIST_IDENTIFIER) is kept byte for byte. Never turns a switch off.
     */
    fun patch(plist: String, level: HdrLevel): String = setSwitches(plist, level.keys.associateWith { true })

    /**
     * Sets exactly [level]: its switches on and every other HDR switch off, so [detect] then reports
     * [level]. Only the five HDR values can change; everything else is kept byte for byte.
     */
    fun applyExact(plist: String, level: HdrLevel): String =
        setSwitches(plist, ALL_KEYS.associateWith { it in level.keys })

    /** The level the plist's HDR switches currently match. */
    fun detect(plist: String): HdrState {
        val on = ALL_KEYS.map { readBool(plist, it) ?: return HdrState.UNKNOWN }
        val set = ALL_KEYS.filterIndexed { i, _ -> on[i] }.toSet()
        return when (set) {
            emptySet<String>() -> HdrState.NONE
            HdrLevel.A.keys.toSet() -> HdrState.A
            HdrLevel.B.keys.toSet() -> HdrState.B
            HdrLevel.C.keys.toSet() -> HdrState.C
            else -> HdrState.CUSTOM
        }
    }

    private fun setSwitches(plist: String, values: Map<String, Boolean>): String {
        var text = plist
        for ((key, value) in values) {
            val pattern = Regex("(<key>${Regex.escape(key)}</key>\\s*)<(true|false)/>")
            val matches = pattern.findAll(text).count()
            require(matches == 1) { "$key found $matches times" }
            text = pattern.replace(text) { "${it.groupValues[1]}<${if (value) "true" else "false"}/>" }
        }
        return text
    }
}
