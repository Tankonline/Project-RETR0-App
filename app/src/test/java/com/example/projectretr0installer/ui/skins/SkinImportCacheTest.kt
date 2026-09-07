package com.example.projectretr0installer.ui.skins

import org.junit.Assert.*
import org.junit.Test
import java.io.File

class SkinImportCacheTest {
    @Test fun sessionIdsCannotEscapeRoot() { assertFalse(SkinImportCache.sanitizeSessionId("../../evil").contains(".")); assertFalse(SkinImportCache.sanitizeSessionId("a/b").contains("/")) }
    @Test fun generatedNamesAreInternal() { val value = SkinImportCache.sanitizeSessionId("provider/name.jpg"); assertEquals("providernamejpg", value) }
}
