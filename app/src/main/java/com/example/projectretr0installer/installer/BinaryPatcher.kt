package com.example.projectretr0installer.installer

import com.example.projectretr0installer.IRetr0UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Helper class for applying binary patches to game files using the privileged service.
 */
@Suppress("unused")
class BinaryPatcher(private val service: IRetr0UserService) {

    /**
     * Applies a patch file to a target file.
     * The patch file should be in the RTPC format (RETR0 Patch).
     */
    suspend fun applyPatch(targetPath: String, patchPath: String): Boolean = withContext(Dispatchers.IO) {
        try {
            service.applyPatch(targetPath, patchPath)
        } catch (_: Exception) {
            false
        }
    }
}
