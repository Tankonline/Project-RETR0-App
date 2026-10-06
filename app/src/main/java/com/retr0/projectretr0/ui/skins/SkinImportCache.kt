package com.retr0.projectretr0.ui.skins

import android.content.Context
import java.io.File
import java.util.UUID

class SkinImportCache private constructor(private val sessionDir: File) {
    constructor(context: Context, sessionId: String = UUID.randomUUID().toString()) : this(
        File(context.cacheDir, "skin-import/${sanitizeSessionId(sessionId)}")
    )

    init { sessionDir.mkdirs() }

    fun newImageFile(extension: String = "webp"): File {
        val safeExtension = extension.lowercase().filter(Char::isLetterOrDigit).take(5).ifEmpty { "webp" }
        return File(sessionDir, "image-${UUID.randomUUID()}.$safeExtension")
    }

    fun delete(file: File): Boolean = if (contains(file)) !file.exists() || file.delete() else false

    fun cleanup(): Boolean = !sessionDir.exists() || sessionDir.deleteRecursively()

    fun contains(file: File): Boolean {
        val root = sessionDir.canonicalFile.toPath()
        return file.canonicalFile.toPath().startsWith(root)
    }

    internal fun root(): File = sessionDir

    companion object {
        internal fun sanitizeSessionId(value: String): String =
            value.filter { it.isLetterOrDigit() || it == '-' || it == '_' }.take(64).ifEmpty { UUID.randomUUID().toString() }
    }
}
