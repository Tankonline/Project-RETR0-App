package com.retr0.projectretr0.data.skins

import com.retr0.projectretr0.data.skins.local.VerifiedFilePromoter
import org.junit.Assert.*
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.File
import java.security.MessageDigest

class VerifiedFilePromoterTest {
    private fun temp(name: String) = File(System.getProperty("java.io.tmpdir"), "retr0-${System.nanoTime()}-$name")
    private fun hash(bytes: ByteArray) = MessageDigest.getInstance("SHA-256").digest(bytes).joinToString("") { "%02x".format(it) }

    @Test fun promotesOnlyAfterLengthAndHashVerification() {
        val bytes = "verified skin".toByteArray()
        val target = temp("skin.dds.z")
        val result = VerifiedFilePromoter.promote(ByteArrayInputStream(bytes), target, bytes.size.toLong(), hash(bytes))
        assertArrayEquals(bytes, result.file.readBytes())
        assertFalse(File(target.parentFile, ".${target.name}.partial").exists())
        target.delete()
    }

    @Test fun hashMismatchCleansPartialAndDestination() {
        val target = temp("bad.dds.z")
        assertThrows(IllegalArgumentException::class.java) {
            VerifiedFilePromoter.promote(ByteArrayInputStream(byteArrayOf(1, 2, 3)), target, 3, "0".repeat(64))
        }
        assertFalse(target.exists())
        assertFalse(File(target.parentFile, ".${target.name}.partial").exists())
    }

    @Test fun oversizedStreamIsRejectedAndCleaned() {
        val target = temp("large.dds.z")
        assertThrows(IllegalArgumentException::class.java) {
            VerifiedFilePromoter.promote(ByteArrayInputStream(ByteArray(17)), target, maxBytes = 16)
        }
        assertFalse(target.exists())
        assertFalse(File(target.parentFile, ".${target.name}.partial").exists())
    }

    @Test fun lengthMismatchIsRejected() {
        val target = temp("short.dds.z")
        assertThrows(IllegalArgumentException::class.java) {
            VerifiedFilePromoter.promote(ByteArrayInputStream(byteArrayOf(1)), target, expectedSize = 2)
        }
        assertFalse(target.exists())
    }
}
