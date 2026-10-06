package com.retr0.projectretr0.ui.skins

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.security.MessageDigest

private const val MAX_SKIN_SOURCE_BYTES = 5L * 1024L * 1024L

data class SelectedSkinSource(
    val uri: Uri,
    val fileName: String,
    val size: Long,
    val sha256: String,
    val format: String,
)

suspend fun inspectSkinSource(contentResolver: ContentResolver, uri: Uri): Result<SelectedSkinSource> =
    withContext(Dispatchers.IO) {
        runCatching {
            val metadata = contentResolver.query(
                uri,
                arrayOf(OpenableColumns.DISPLAY_NAME, OpenableColumns.SIZE),
                null,
                null,
                null,
            )?.use(::readMetadata) ?: throw IllegalArgumentException("The selected file could not be read.")
            val fileName = metadata.first
            require(fileName.endsWith(".dds.z", ignoreCase = true)) { "Choose a .dds.z car skin file." }
            require('/' !in fileName && '\\' !in fileName && '\u0000' !in fileName) { "The selected filename is unsafe." }
            val input = contentResolver.openInputStream(uri)
                ?: throw IllegalArgumentException("The selected file could not be opened.")
            val digest = MessageDigest.getInstance("SHA-256")
            var actualSize = 0L
            input.buffered().use { stream ->
                val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
                while (true) {
                    val count = stream.read(buffer)
                    if (count < 0) break
                    actualSize += count
                    require(actualSize <= MAX_SKIN_SOURCE_BYTES) { "Skin files must be 5 MiB or smaller." }
                    digest.update(buffer, 0, count)
                }
            }
            require(actualSize > 0) { "The selected skin file is empty." }
            val providerSize = metadata.second
            require(providerSize == null || providerSize < 0 || providerSize == actualSize) { "The file changed while it was being read. Select it again." }
            SelectedSkinSource(
                uri = uri,
                fileName = fileName,
                size = actualSize,
                sha256 = digest.digest().joinToString("") { "%02x".format(it) },
                format = SkinTargetValidator.textureFormat(fileName),
            )
        }
    }

private fun readMetadata(cursor: Cursor): Pair<String, Long?> {
    require(cursor.moveToFirst()) { "The selected file has no metadata." }
    val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
    val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
    val name = if (nameIndex >= 0) cursor.getString(nameIndex)?.trim().orEmpty() else ""
    require(name.isNotEmpty()) { "The selected file has no filename." }
    val size = if (sizeIndex >= 0 && !cursor.isNull(sizeIndex)) cursor.getLong(sizeIndex) else null
    return name to size
}
