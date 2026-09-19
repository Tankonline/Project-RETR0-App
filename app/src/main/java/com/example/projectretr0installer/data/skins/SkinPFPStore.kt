package com.example.projectretr0installer.data.skins

import android.content.Context
import android.util.Log
import java.io.File
import java.security.MessageDigest

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

    /** A validated local avatar and content-derived revision for deterministic UI invalidation. */
    data class LocalAvatar(val file: File, val revision: String)

    /** The persisted PFP descriptor for [installationId], or null if the user has not set one. */
    fun current(context: Context, installationId: String): LocalAvatar? =
        descriptorFor(fileFor(context, installationId))

    fun fileFor(context: Context, installationId: String): File =
        File(directory(context), "$installationId.$EXTENSION")

    private fun directory(context: Context): File =
        File(context.applicationContext.filesDir, DIRECTORY_NAME).apply { mkdirs() }

    /**
     * Moves a freshly processed [SkinImageRole.PFP] output file into the persistent,
     * installation-linked location, replacing any previous PFP. The source file (from the
     * temporary import cache) is consumed -- it no longer exists after this call succeeds.
     */
    fun adopt(context: Context, installationId: String, processed: File): LocalAvatar {
        require(processed.isFile && processed.length() > 0) { "Processed profile picture is missing or empty." }
        val target = fileFor(context, installationId)
        val tmp = File(target.parentFile, "${target.name}.tmp-${System.nanoTime()}")
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
        return descriptorFor(target)
            ?: error("Unable to verify the saved profile picture.")
    }

    private fun descriptorFor(file: File): LocalAvatar? {
        if (!file.isFile || file.length() <= 0 || !file.canRead()) return null
        val revision = file.inputStream().use { input ->
            val digest = MessageDigest.getInstance("SHA-256")
            val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
            while (true) {
                val read = input.read(buffer)
                if (read < 0) break
                digest.update(buffer, 0, read)
            }
            digest.digest().joinToString("") { byte -> "%02x".format(byte) }
        }
        Log.i(LOG_TAG, "PFP_OUTPUT_SAVED bytes=${file.length()} path=${file.name} revision=${revision.take(12)}")
        return LocalAvatar(file, revision)
    }

    /** Removes the persisted PFP, reverting the user to the generated identicon. */
    fun clear(context: Context, installationId: String): Boolean {
        val file = fileFor(context, installationId)
        val removed = !file.exists() || file.delete()
        if (removed) Log.i(LOG_TAG, "PFP_CLEARED")
        return removed
    }
}
