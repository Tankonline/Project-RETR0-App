package com.retr0.projectretr0.installer.graphics

/**
 * Strict path rules for the Graphics Hub. Shared by the privileged UserService and the engine so
 * both sides agree on exactly which files graphics code may write or delete.
 */
object GraphicsPathValidator {
    const val BACKUP_ROOT = "/storage/emulated/0/Documents/ProjectRETR0/GraphicsBackups"

    /**
     * Graphics API version the privileged helper must report. An older helper that is still running
     * in Shizuku answers 0, and the Hub then asks the user to restart instead of failing mid-write.
     */
    const val SERVICE_API = 2

    /** Same tag as the engine, so helper-side lines show up in the same logcat filter. */
    const val LOG_TAG = "RETR0_GRAPHICS"

    private const val DEPOT = "^/storage/emulated/0/Android/data/com\\.ea\\.games\\.r3_(?:row|na)/files/\\.depot/"
    private val TARGET = Regex(DEPOT + "(?:eds/[A-Za-z0-9,_-]{1,64}\\.plist|feats/[A-Za-z0-9,_-]{1,64}\\.feats)$")
    private val TEMP = Regex(DEPOT + "(?:eds|feats)/\\.[A-Za-z0-9,_-]{1,64}\\.(?:plist|feats)\\.retr0-gfx-[0-9]{1,20}$")

    /** A managed file name inside eds/feats, e.g. `AndroidHigh.plist` or `iPad2,1.feats`. */
    val MANAGED_NAME = Regex("^[A-Za-z0-9,_-]{1,64}\\.(?:plist|feats)$")

    /** A leftover publish temp file name, e.g. `.AndroidHigh.plist.retr0-gfx-123`. */
    val TEMP_NAME = Regex("^\\.[A-Za-z0-9,_-]{1,64}\\.(?:plist|feats)\\.retr0-gfx-[0-9]{1,20}$")

    private fun normalize(path: String) = path.replace('\\', '/')

    /** A real graphics file: `.depot/eds/<name>.plist` or `.depot/feats/<name>.feats`. */
    fun isTarget(path: String): Boolean = TARGET.matches(normalize(path))

    /** A publish temp file inside eds/feats (only ever deleted during cleanup). */
    fun isTempFile(path: String): Boolean = TEMP.matches(normalize(path))

    /** Anything strictly below the graphics backup root, never the root itself. */
    fun isBackupPath(path: String): Boolean {
        val p = normalize(path)
        return p.startsWith("$BACKUP_ROOT/") && p.length > BACKUP_ROOT.length + 1 &&
            p.split('/').none { it == ".." || it == "." }
    }

    fun tempNameFor(targetName: String, nanos: Long): String = ".$targetName.retr0-gfx-$nanos"
}
