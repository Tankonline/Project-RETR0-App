package com.retr0.projectretr0.installer

import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.io.path.createTempDirectory

class SplitZipSupportTest {
    private val hash = "a".repeat(64)

    @Test
    fun validatesOrderedPartsAndCombinedSize() {
        val file = splitFile()
        assertEquals(file.parts, SplitZipSupport.validate(file))
    }

    @Test
    fun rejectsUnsafeOrInconsistentParts() {
        assertThrows(IllegalArgumentException::class.java) {
            SplitZipSupport.validate(splitFile().copy(parts = splitFile().parts.mapIndexed { index, part ->
                if (index == 0) part.copy(name = "../payload.z01") else part
            }))
        }
        assertThrows(IllegalArgumentException::class.java) {
            SplitZipSupport.validate(splitFile().copy(size = 99))
        }
        assertThrows(IllegalArgumentException::class.java) {
            SplitZipSupport.validate(splitFile().copy(parts = splitFile().parts.mapIndexed { index, part ->
                if (index == 0) part.copy(url = "http://example.test/payload.z01") else part
            }))
        }
    }

    @Test
    fun validatesSequentialAbsoluteChunksWithRepeatedSuffixesPerArchive() {
        val first = chunkedFile("retr0_part_1")
        val second = chunkedFile("retr0_part_2")
        SplitZipSupport.validateChunkedArchive(first)
        SplitZipSupport.validateChunkedArchive(second)
        assertEquals("retr0_part_1.zip.001", FileDownloader.localChunkFileName(first.chunks.first()))
        assertEquals("retr0_part_2.zip.001", FileDownloader.localChunkFileName(second.chunks.first()))
    }

    @Test
    fun rejectsOutOfOrderChunkSuffixes() {
        assertThrows(IllegalArgumentException::class.java) {
            SplitZipSupport.validateChunkedArchive(
                chunkedFile("retr0_part_1").copy(chunks = listOf(
                    "https://cdn.example/retr0_part_1.zip.002",
                    "https://cdn.example/retr0_part_1.zip.001",
                )),
            )
        }
    }

    @Test
    fun resolvesEncodedAbsoluteUrlFilenameWithoutQuery() {
        assertEquals("payload%20file.zip.001", FileDownloader.localChunkFileName("https://cdn.example/a/payload%20file.zip.001?token=x"))
        assertTrue(FileDownloader.localChunkFileName("folder/payload.zip.018").endsWith(".018"))
    }

    @Test
    fun opensPartsAsOneLogicalStream() {
        val directory = createTempDirectory("split-zip").toFile()
        val first = directory.resolve("payload.z01").apply { writeBytes(byteArrayOf(1, 2)) }
        val final = directory.resolve("payload.zip").apply { writeBytes(byteArrayOf(3, 4)) }
        SplitZipSupport.open(listOf(first, final)).use {
            assertArrayEquals(byteArrayOf(1, 2, 3, 4), it.readBytes())
        }
        directory.deleteRecursively()
    }

    private fun chunkedFile(id: String): InstallFile = InstallFile(
        id = id,
        name = "$id.zip",
        url = "https://cdn.example/$id.zip",
        size = 100,
        uncompressedSize = 200,
        sha256 = hash,
        destination = "files",
        type = "split_zip",
        chunks = (1..18).map { "https://cdn.example/$id.zip.${it.toString().padStart(3, '0')}" },
    )

    private fun splitFile(): InstallFile {
        val parts = listOf(
            ArchivePart("payload.z01", "https://example.test/payload.z01", 2, hash),
            ArchivePart("payload.zip", "https://example.test/payload.zip", 3, hash),
        )
        return InstallFile("archive", "payload.zip", "https://example.test/payload.zip", 5, 10, hash, "files", "zip", parts = parts)
    }
}
