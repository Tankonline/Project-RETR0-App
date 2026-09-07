package com.example.projectretr0installer.ui.skins

import org.junit.Assert.*
import org.junit.Test

class SkinImageProcessorTest {
    @Test fun limitsDifferByRole() { assertEquals(500L * 1024, SkinImageRole.COVER.maxBytes); assertEquals(1024L * 1024, SkinImageRole.SCREENSHOT.maxBytes) }
    @Test fun firstAttemptRespectsRoleEdge() { assertEquals(1280, SkinImageRetryPolicy.first(4000, 2000, SkinImageRole.COVER).width); assertEquals(1920, SkinImageRetryPolicy.first(4000, 2000, SkinImageRole.SCREENSHOT).width) }
    @Test fun policyTerminatesAtMinimums() { var current = EncodeAttempt(4000, 2000, 90); var count = 0; while (true) { current = SkinImageRetryPolicy.next(current) ?: break; assertTrue(++count < 100) }; assertTrue(current.quality >= SkinImageRetryPolicy.MIN_QUALITY); assertTrue(maxOf(current.width, current.height) <= SkinImageRetryPolicy.MIN_EDGE) }
}
