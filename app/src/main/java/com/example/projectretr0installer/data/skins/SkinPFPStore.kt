package com.example.projectretr0installer.data.skins

import android.content.Context
import android.util.Log
import java.io.File

private const val LOG_TAG = "SkinImageCrop"

/**
 * Persists the local device owner's profile picture, linked to their [SkinInstallationIdentity]
 * purely by filename -- `<installationId>.webp` -- so there is no separate index to fall out of
 * sync with the file on disk. Lives outside the session-scoped [com.example.projectretr0installer.ui.skins.SkinImportCache]
 * because a PFP, unlike a queued skin import, must survive process death and app restarts.
 */
object SkinPFPStore {
    private const val DIRECTORY_NAME = "pfp"
    private const val EXTENSION = "webp"

    /** The persisted PFP file for [installationId], or null if the user has not set one. */
    fun currentFile(context: Context, installationId: String): File? =
        fileFor(context, installationId).takeIf { it.isFile && it.length() > 0 }

    fun fileFor(context: Context, installationId: String): File =
        File(directory(context), "$installationId.$EXTENSION")

    private fun directory(context: Context): File =
        File(context.applicationContext.filesDir, DIRECTORY_NAME).apply { mkdirs() }

    /**
     * Moves a freshly processed [SkinImageRole.PFP] output file into the persistent,
     * installation-linked location, replacing any previous PFP. The source file (from the
     * temporary import cache) is consumed -- it no longer exists after this call succeeds.
     */
    fun adopt(context: Context, installationId: String, processed: File): File {
        val target = fileFor(context, installationId)
        val tmp = File(target.parentFile, "${target.name}.tmp-${System.currentTimeMillis()}")
        processed.copyTo(tmp, overwrite = true)
        processed.delete()
        if (target.exists() && !target.delete()) {
            tmp.delete()
            error("Unable to replace the previous profile picture.")
        }
        if (!tmp.renameTo(target)) {
            tmp.delete()
            error("Unable to save the profile picture.")
        }
        Log.i(LOG_TAG, "PFP_OUTPUT_SAVED bytes=${target.length()} path=${target.name}")
        return target
    }

    /** Removes the persisted PFP, reverting the user to the generated identicon. */
    fun clear(context: Context, installationId: String): Boolean {
        val file = fileFor(context, installationId)
        val removed = !file.exists() || file.delete()
        if (removed) Log.i(LOG_TAG, "PFP_CLEARED")
        return removed
    }
}
