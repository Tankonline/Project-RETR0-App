package com.example.projectretr0installer.data.skins

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File
import java.security.MessageDigest

class SkinPFPStoreTest {
    @Test
    fun contentRevision_changesWhenAvatarBytesChange() {
        val first = revisionOf("first-image".toByteArray())
        val second = revisionOf("second-image".toByteArray())

        assertEquals(64, first.length)
        assertNotEquals(first, second)
    }

    @Test
    fun contentRevision_isStableForTheSameBytes() {
        val bytes = "same-image".toByteArray()

        assertEquals(revisionOf(bytes), revisionOf(bytes))
    }

    @Test
    fun noAvatarDescriptorExistsForAnEmptyFile() {
        val empty = File.createTempFile("pfp-empty", ".webp")

        assertTrue(empty.isFile)
        assertEquals(0L, empty.length())
        assertNull(empty.takeIf { it.isFile && it.length() > 0 && it.canRead() })

        empty.delete()
    }

    private fun revisionOf(bytes: ByteArray): String =
        MessageDigest.getInstance("SHA-256").digest(bytes).joinToString("") { "%02x".format(it) }
}
