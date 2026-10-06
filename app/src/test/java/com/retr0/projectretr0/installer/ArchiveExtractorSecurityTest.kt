package com.retr0.projectretr0.installer

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File
import kotlin.io.path.createTempDirectory

class ArchiveExtractorSecurityTest {
    @Test
    fun canonicalDestinationRejectsTraversal() {
        val root = createTempDirectory("retr0-root").toFile()
        val extractor = ArchiveExtractor()
        val method = ArchiveExtractor::class.java.getDeclaredMethod(
            "isPathWithinDestination",
            String::class.java,
            String::class.java,
        ).apply { isAccessible = true }

        assertTrue(method.invoke(extractor, File(root, "safe/file.bin").path, root.path) as Boolean)
        assertFalse(method.invoke(extractor, File(root, "../escape.bin").path, root.path) as Boolean)
        assertFalse(method.invoke(extractor, File(root, "safe/../../escape.bin").path, root.path) as Boolean)
        root.deleteRecursively()
    }
}
