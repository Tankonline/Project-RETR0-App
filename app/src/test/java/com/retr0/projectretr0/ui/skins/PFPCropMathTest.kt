package com.retr0.projectretr0.ui.skins

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Coverage for the 1:1 (PFP) generalization of the shared crop math: [SkinImageCropMath]'s
 * ratio-generic center-crop/tolerance functions and [CropGeometry] driven with
 * `targetRatio = CropGeometry.SQUARE_RATIO`. Mirrors [CropGeometryTest] and
 * [SkinImageProcessorTest]'s 16:9 coverage so the two ratios are held to the same bar.
 */
class PFPCropMathTest {
    private fun ratioOf(width: Int, height: Int): Float = width.toFloat() / height

    private fun squareGeo(sw: Int, sh: Int, vw: Float = 400f, vh: Float = 400f) =
        CropGeometry(sw, sh, vw, vh, targetRatio = CropGeometry.SQUARE_RATIO)

    // ---- SkinImageCropMath.centerCrop1By1 --------------------------------------------------

    @Test fun centerCrop1By1_onPortraitSource_trimsHeightKeepsFullWidth() {
        val rect = SkinImageCropMath.centerCrop1By1(1000, 2000)
        assertEquals(CropRect(0, 500, 1000, 1000), rect)
    }

    @Test fun centerCrop1By1_onLandscapeSource_trimsWidthKeepsFullHeight() {
        val rect = SkinImageCropMath.centerCrop1By1(2000, 1000)
        assertEquals(CropRect(500, 0, 1000, 1000), rect)
    }

    @Test fun centerCrop1By1_onSquareSource_isANoOp() {
        val rect = SkinImageCropMath.centerCrop1By1(1024, 1024)
        assertEquals(CropRect(0, 0, 1024, 1024), rect)
    }

    @Test fun centerCrop1By1_isExactlySquareForOddDimensions() {
        val sizes = listOf(999 to 1001, 4032 to 3024, 3024 to 4032, 1 to 1000, 1000 to 1)
        for ((w, h) in sizes) {
            val rect = SkinImageCropMath.centerCrop1By1(w, h)
            assertEquals("source ${w}x$h -> $rect must be a perfect square", rect.width, rect.height)
            assertTrue("rect=$rect out of [0,$w)x[0,$h)", rect.left >= 0 && rect.top >= 0 && rect.left + rect.width <= w && rect.top + rect.height <= h)
        }
    }

    @Test fun centerCrop1By1_isCenteredWithinOnePixel() {
        val sizes = listOf(2000 to 500, 500 to 2000, 4032 to 3024, 3024 to 4032, 1999 to 1000)
        for ((w, h) in sizes) {
            val rect = SkinImageCropMath.centerCrop1By1(w, h)
            val marginBefore = if (rect.width < w) rect.left else rect.top
            val marginAfter = if (rect.width < w) (w - rect.width - rect.left) else (h - rect.height - rect.top)
            assertTrue("auto crop of ${w}x$h is not center-aligned: before=$marginBefore after=$marginAfter", kotlin.math.abs(marginBefore - marginAfter) <= 1)
        }
    }

    @Test fun centerCrop1By1_isAPureFunction() {
        val first = SkinImageCropMath.centerCrop1By1(1080, 2340)
        val second = SkinImageCropMath.centerCrop1By1(1080, 2340)
        assertEquals(first, second)
    }

    // ---- SkinImageCropMath.isMateriallyDifferentFromSquare / tolerance --------------------

    @Test fun isMateriallyDifferentFromSquare_exactSquareIsFalse() {
        assertFalse(SkinImageCropMath.isMateriallyDifferentFromSquare(512, 512))
    }

    @Test fun isMateriallyDifferentFromSquare_withinPointZeroOneToleranceIsFalse() {
        // SQUARE_ASPECT_TOLERANCE is 0.01; a 1000x995 image has ratio 1.00503 < 1.01.
        assertFalse(SkinImageCropMath.isMateriallyDifferentFromSquare(1000, 995))
    }

    @Test fun isMateriallyDifferentFromSquare_beyondToleranceIsTrue() {
        assertTrue(SkinImageCropMath.isMateriallyDifferentFromSquare(1000, 900))
        assertTrue(SkinImageCropMath.isMateriallyDifferentFromSquare(900, 1000))
    }

    @Test fun squareTolerance_isTighterThan16By9Tolerance() {
        // The PFP auto-bypass path is deliberately stricter than the 16:9 skin-image path.
        assertTrue(SkinImageCropMath.SQUARE_ASPECT_TOLERANCE < SkinImageCropMath.ASPECT_TOLERANCE)
    }

    // ---- CropGeometry with targetRatio = SQUARE_RATIO -------------------------------------

    @Test fun squareGeometry_frameIsSquare() {
        val g = squareGeo(1080, 2340, vw = 400f, vh = 300f)
        assertEquals(g.frameW, g.frameH, 0.01f)
    }

    @Test fun squareGeometry_portraitSource_coversFrame() {
        val g = squareGeo(1080, 2340)
        assertTrue(g.drawnW >= g.frameW - 0.5f && g.drawnH >= g.frameH - 0.5f)
    }

    @Test fun squareGeometry_landscapeSource_coversFrame() {
        val g = squareGeo(2340, 1080)
        assertTrue(g.drawnW >= g.frameW - 0.5f && g.drawnH >= g.frameH - 0.5f)
    }

    @Test fun squareGeometry_squareSource_hasZeroPanRoomAtUserScaleOne() {
        val g = squareGeo(1024, 1024)
        assertEquals(g.frameW, g.drawnW, 0.5f)
        assertEquals(g.frameH, g.drawnH, 0.5f)
        assertEquals(0f, g.maxPanX, 0.5f)
        assertEquals(0f, g.maxPanY, 0.5f)
    }

    @Test fun squareGeometry_cropIsExactlySquareWithinTolerance() {
        for ((sw, sh) in listOf(1080 to 2340, 2340 to 1080, 1024 to 1024, 3440 to 1080)) {
            val r = squareGeo(sw, sh).withTransform(2.4f, 15f, -8f).toSourceCropRect()!!
            assertTrue("expected 1:1, got ${r.width}x${r.height}", kotlin.math.abs(ratioOf(r.width, r.height) - CropGeometry.SQUARE_RATIO) < 0.02f)
        }
    }

    @Test fun squareGeometry_cropStaysInBoundsWhenPannedToEveryEdge() {
        for ((sw, sh) in listOf(1080 to 2340, 2340 to 1080, 1024 to 1024)) {
            val base = squareGeo(sw, sh).withTransform(3f, 0f, 0f)
            val extremes = listOf(
                base.maxPanX to base.maxPanY,
                -base.maxPanX to base.maxPanY,
                base.maxPanX to -base.maxPanY,
                -base.maxPanX to -base.maxPanY,
            )
            for ((panX, panY) in extremes) {
                val r = base.withTransform(base.userScale, panX, panY).toSourceCropRect()!!
                assertTrue("rect=$r out of [0,$sw)x[0,$sh)", r.left >= 0 && r.top >= 0 && r.left + r.width <= sw && r.top + r.height <= sh)
            }
        }
    }

    @Test fun squareGeometry_userScaleIsClampedToValidRange() {
        val tooSmall = squareGeo(1080, 2340).withTransform(0.1f, 0f, 0f)
        assertEquals(CropGeometry.MIN_USER_SCALE, tooSmall.userScale, 1e-6f)
        val tooLarge = squareGeo(1080, 2340).withTransform(50f, 0f, 0f)
        assertEquals(CropGeometry.MAX_USER_SCALE, tooLarge.userScale, 1e-6f)
    }

    @Test fun squareGeometry_resetIsDeterministic() {
        val a = squareGeo(1080, 2340).withTransform(3f, 50f, 50f).reset()
        val b = squareGeo(1080, 2340).reset()
        assertEquals(b.userScale, a.userScale)
        assertEquals(b.panX, a.panX)
        assertEquals(b.panY, a.panY)
    }

    @Test fun squareGeometry_invalidInputsReturnNull() {
        assertNull(CropGeometry(0, 100, 100f, 100f, targetRatio = CropGeometry.SQUARE_RATIO).toSourceCropRect())
        assertNull(CropGeometry(100, 100, 0f, 100f, targetRatio = CropGeometry.SQUARE_RATIO).toSourceCropRect())
    }

    @Test fun squareGeometry_defaultTargetRatioIsUnaffectedBy16By9Usage() {
        // Constructing without targetRatio must still default to 16:9 -- generalizing CropGeometry
        // must not silently change the existing skin-image crop editor's behavior.
        val g = CropGeometry(1920, 1080, 1000f, 1000f)
        assertEquals(CropGeometry.TARGET_RATIO, g.targetRatio, 1e-6f)
    }

    /**
     * Direct "manual crop preserves the selected composition" check for the square ratio,
     * mirroring [CropGeometryTest.exported_rect_equals_the_source_region_visible_under_the_frame]:
     * intersect the drawn image rect with the fixed square frame (both in viewport px) to get the
     * visible source region, then map that intersection back to source px. Must equal
     * `toSourceCropRect()`.
     */
    @Test fun squareGeometry_exportedRectEqualsTheSourceRegionVisibleUnderTheFrame() {
        for ((sw, sh) in listOf(1080 to 2340, 2340 to 1080, 1024 to 1024, 3440 to 1080)) {
            val g = squareGeo(sw, sh).withTransform(2.2f, 33f, -17f)
            val dst = g.imageDestRect()
            val frame = g.frameRect()
            val visibleLeft = maxOf(dst[0], frame[0])
            val visibleTop = maxOf(dst[1], frame[1])
            val visibleRight = minOf(dst[2], frame[2])
            val visibleBottom = minOf(dst[3], frame[3])
            assertEquals("$sw x $sh", frame[0], visibleLeft, 0.5f)
            assertEquals("$sw x $sh", frame[1], visibleTop, 0.5f)
            assertEquals("$sw x $sh", frame[2], visibleRight, 0.5f)
            assertEquals("$sw x $sh", frame[3], visibleBottom, 0.5f)

            val expectedSrcLeft = (visibleLeft - dst[0]) / g.totalScale
            val expectedSrcTop = (visibleTop - dst[1]) / g.totalScale
            val rect = g.toSourceCropRect()!!
            assertEquals("left drifted for $sw x $sh", expectedSrcLeft, rect.left.toFloat(), 1.5f)
            assertEquals("top drifted for $sw x $sh", expectedSrcTop, rect.top.toFloat(), 1.5f)
        }
    }

    // ---- SkinImageRole.PFP ------------------------------------------------------------------

    @Test fun pfpRole_targetsIs512x512WithA200KiBLimit() {
        assertEquals(512, SkinImageRole.PFP.targetWidth)
        assertEquals(512, SkinImageRole.PFP.targetHeight)
        assertEquals(200L * 1024, SkinImageRole.PFP.maxBytes)
    }

    @Test fun pfpRole_aspectRatioIsOne() {
        assertEquals(1f, SkinImageRole.PFP.aspectRatio, 1e-6f)
    }

    @Test fun pfpRole_usesTheStricterSquareTolerance() {
        assertEquals(SkinImageCropMath.SQUARE_ASPECT_TOLERANCE, SkinImageRole.PFP.cropTolerance, 1e-6f)
        assertEquals(SkinImageCropMath.ASPECT_TOLERANCE, SkinImageRole.COVER.cropTolerance, 1e-6f)
    }

    @Test fun pfpRetryChain_isExactlySquareAtEveryStep() {
        var current: EncodeAttempt? = SkinImageRetryPolicy.first(4000, 4000, SkinImageRole.PFP)
        var steps = 0
        while (current != null) {
            val attempt = current
            assertEquals("attempt $attempt is not exactly square", attempt.width, attempt.height)
            current = SkinImageRetryPolicy.next(attempt, SkinImageRole.PFP)
            assertTrue(++steps < 100)
        }
    }

    @Test fun pfpRetryChain_terminatesAtMinimums() {
        var current = SkinImageRetryPolicy.first(4000, 4000, SkinImageRole.PFP)
        var count = 0
        while (true) {
            current = SkinImageRetryPolicy.next(current, SkinImageRole.PFP) ?: break
            assertTrue(++count < 100)
        }
        assertTrue(current.quality >= SkinImageRetryPolicy.MIN_QUALITY)
        assertTrue(maxOf(current.width, current.height) <= SkinImageRetryPolicy.MIN_EDGE)
    }

    @Test fun pfpFirstAttemptIs512x512() {
        val attempt = SkinImageRetryPolicy.first(4000, 4000, SkinImageRole.PFP)
        assertEquals(512, attempt.width)
        assertEquals(512, attempt.height)
    }
}
