package com.retr0.projectretr0.installer

import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.io.SequenceInputStream
import java.net.URI
import java.util.Collections

/** Validates manifest-declared split ZIP parts and opens them as one logical archive. */
object SplitZipSupport {
    private val sha256Pattern = Regex("^[0-9a-fA-F]{64}$")
    private val partNamePattern = Regex("^.+\\.(?:z\\d{2,3}|zip)$", RegexOption.IGNORE_CASE)
    private val chunkSuffixPattern = Regex("\\.\\d{3}$")

    fun validate(file: InstallFile): List<ArchivePart> {
        require(file.type.equals("zip", ignoreCase = true)) { "Split parts require ZIP type" }
        require(file.parts.size >= 2) { "A split ZIP requires at least two parts" }
        require(file.parts.last().name.endsWith(".zip", ignoreCase = true)) { "Final split ZIP part must end in .zip" }

        val names = HashSet<String>()
        file.parts.forEachIndexed { index, part ->
            require(part.name == File(part.name).name && partNamePattern.matches(part.name)) { "Invalid split ZIP part name" }
            require(names.add(part.name.lowercase())) { "Duplicate split ZIP part name" }
            require(part.size > 0) { "Split ZIP part size must be positive" }
            require(sha256Pattern.matches(part.sha256)) { "Split ZIP part requires SHA-256" }
            val uri = URI(part.url)
            require(uri.scheme.equals("https", ignoreCase = true) && !uri.host.isNullOrBlank() && uri.userInfo == null) {
                "Split ZIP part URL must be HTTPS"
            }
            if (index < file.parts.lastIndex) {
                require(Regex("^.+\\.z\\d{2,3}$", RegexOption.IGNORE_CASE).matches(part.name)) {
                    "Non-final split ZIP parts must use .zNN naming"
                }
            }
        }
        require(file.size == file.parts.sumOf { it.size }) { "Split ZIP part sizes do not match archive size" }
        return file.parts
    }

    fun validateChunkedArchive(file: InstallFile) {
        require(file.type.equals("split_zip", ignoreCase = true)) { "Chunked archive must use split_zip type" }
        require(file.chunks.isNotEmpty()) { "Chunked archive requires chunks" }
        require(file.size > 0 && file.uncompressedSize > 0) { "Chunked archive sizes must be positive" }
        val suffixes = file.chunks.mapIndexed { index, chunk ->
            val uri = URI(chunk)
            if (uri.isAbsolute) {
                require(uri.scheme.equals("https", ignoreCase = true) && !uri.host.isNullOrBlank() && uri.userInfo == null) {
                    "Chunk URL must be HTTPS"
                }
            }
            val name = FileDownloader.localChunkFileName(chunk)
            require(chunkSuffixPattern.containsMatchIn(name)) { "Chunk must end in .NNN" }
            require(name.endsWith(".${(index + 1).toString().padStart(3, '0')}")) { "Chunks must be sequential" }
            name.substringAfterLast('.')
        }
        require(suffixes.distinct().size == suffixes.size) { "Duplicate chunk suffix" }
    }

    fun open(files: List<File>): InputStream {
        require(files.size >= 2) { "A split ZIP requires at least two local parts" }
        require(files.all { it.isFile }) { "A split ZIP part is missing" }
        val streams = files.map(::FileInputStream)
        return try {
            SequenceInputStream(Collections.enumeration(streams))
        } catch (error: Throwable) {
            streams.forEach { runCatching { it.close() } }
            throw error
        }
    }
}
