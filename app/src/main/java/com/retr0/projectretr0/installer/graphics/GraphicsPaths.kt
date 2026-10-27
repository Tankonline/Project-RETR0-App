package com.retr0.projectretr0.installer.graphics

/** Where the Graphics Hub reads from, writes to and keeps its backups. Roots are injectable for tests. */
class GraphicsPaths(
    val backupRoot: String = GraphicsPathValidator.BACKUP_ROOT,
    private val packageRoots: Map<String, String> = mapOf(
        ROW to "/storage/emulated/0/Android/data/$ROW",
        NA to "/storage/emulated/0/Android/data/$NA",
    ),
) {
    fun isSupported(gamePackage: String): Boolean = gamePackage in packageRoots
    fun packageRoot(gamePackage: String): String =
        packageRoots[gamePackage] ?: throw IllegalArgumentException("Unsupported RR3 package: $gamePackage")

    fun resEds(pkg: String) = "${packageRoot(pkg)}/files/apk/res/eds"
    fun resFeats(pkg: String) = "${packageRoot(pkg)}/files/apk/res/feats"
    fun depotEds(pkg: String) = "${packageRoot(pkg)}/files/.depot/eds"
    fun depotFeats(pkg: String) = "${packageRoot(pkg)}/files/.depot/feats"

    /** Read only: used to check which track variants are installed. */
    fun depotTracks(pkg: String) = "${packageRoot(pkg)}/files/.depot/Tracks"

    fun packageBackupRoot(pkg: String): String {
        packageRoot(pkg)
        return "$backupRoot/$pkg"
    }
    fun backupDir(pkg: String, backupId: String) = "${packageBackupRoot(pkg)}/$backupId"

    companion object {
        const val ROW = "com.ea.games.r3_row"
        const val NA = "com.ea.games.r3_na"

        /** The universal override writes the selected pair under every one of these names. */
        val ANDROID_TIERS = listOf("AndroidLow", "AndroidMid", "AndroidMid2", "AndroidHigh", "AndroidXtraHigh", "AndroidUltraWide", "SimRigTV")

        /**
         * Tiers whose factory plist sets `R4_TRACK_EXTENSION = "low/"` (decoded from RR3 14.0.1 files).
         * Installs only ship `Tracks/<track>/processed/high/`, so these presets make RR3 look for track
         * files that do not exist and the menu renders black. EA's servers are gone, so the files can't
         * be downloaded either.
         */
        val LOW_TRACK_PRESETS = setOf("AndroidLow", "AndroidMid")

        val SAFE_PRESET = Regex("^[A-Za-z0-9,_-]{1,64}$")
        val SAFE_BACKUP_ID = Regex("^[0-9]{1,20}-[a-z0-9,_-]{1,80}$")
    }
}
