package com.example.projectretr0installer.data.skins.local

import java.io.File
import java.io.InputStream
import java.security.MessageDigest

object VerifiedFilePromoter {
    const val MAX_BINARY_BYTES = 5L * 1024L * 1024L

    fun promote(
        input: InputStream,
        destination: File,
        expectedSize: Long? = null,
        expectedSha256: String? = null,
        maxBytes: Long = MAX_BINARY_BYTES,
        onProgress: (Long) -> Unit = {},
    ): VerifiedFile {
        require(expectedSize == null || expectedSize in 1..maxBytes) { "Invalid expected file length" }
        destination.parentFile?.mkdirs()
        val partial = File(destination.parentFile, ".${destination.name}.partial")
        partial.delete()
        try {
            val digest = MessageDigest.getInstance("SHA-256")
            var total = 0L
            partial.outputStream().buffered().use { output ->
                input.buffered().use { source ->
                    val buffer = ByteArray(32 * 1024)
                    while (true) {
                        val count = source.read(buffer)
                        if (count < 0) break
                        total += count
                        require(total <= maxBytes) { "Skin file exceeds the 5 MiB limit" }
                        digest.update(buffer, 0, count)
                        output.write(buffer, 0, count)
                        onProgress(total)
                    }
                }
            }
            require(total > 0) { "Skin file is empty" }
            require(expectedSize == null || total == expectedSize) { "Downloaded length does not match metadata" }
            val hash = digest.digest().joinToString("") { "%02x".format(it) }
            require(expectedSha256 == null || hash.equals(expectedSha256, true)) { "Downloaded SHA-256 does not match metadata" }
            check(partial.renameTo(destination)) { "Unable to atomically promote skin file" }
            return VerifiedFile(destination, total, hash)
        } catch (error: Throwable) {
            partial.delete()
            destination.delete()
            throw error
        }
    }
}

data class VerifiedFile(val file: File, val size: Long, val sha256: String)
