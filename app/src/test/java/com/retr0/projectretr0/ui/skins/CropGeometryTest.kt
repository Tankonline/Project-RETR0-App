package com.retr0.projectretr0.ui.skins

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class CropGeometryTest {
    // A 1000x825 px viewport approximates the manual crop editor's fillMaxWidth() x 300.dp box.
    private fun geo(sw: Int, sh: Int, vw: Float = 1000f, vh: Float = 825f) =
        CropGeometry(sw, sh, vw, vh)

    private fun ratioOf(width: Int, height: Int): Float = width.toFloat() / height

    @Test fun portrait_baseScale_covers_frame() {
        val g = geo(1080, 2340)
        val cover = maxOf(g.frameW / 1080, g.frameH / 2340)
        assertEquals(cover, g.baseScale, 1e-4f)
        assertTrue(g.drawnW >= g.frameW - 0.5f && g.drawnH >= g.frameH - 0.5f)
    }

    @Test fun square_image_covers_frame() {
        val g = geo(1000, 1000)
        assertTrue(g.drawnW >= g.frameW - 0.5f && g.drawnH >= g.frameH - 0.5f)
    }

    @Test fun wide_image_covers_frame() {
        val g = geo(2400, 1080)
        assertTrue(g.drawnW >= g.frameW - 0.5f && g.drawnH >= g.frameH - 0.5f)
    }

    @Test fun ultrawide_image_covers_frame() {
        val g = geo(3440, 1080)
        assertTrue(g.drawnW >= g.frameW - 0.5f && g.drawnH >= g.frameH - 0.5f)
    }

    @Test fun already_16by9_image_has_zero_pan_room_at_userScale_one() {
        // Source ratio == target ratio -> both axes bind simultaneously, no blank, no slack.
        val g = geo(1920, 1080)
        assertEquals(g.frameW, g.drawnW, 0.5f)
        assertEquals(g.frameH, g.drawnH, 0.5f)
        assertEquals(0f, g.maxPanX, 0.5f)
        assertEquals(0f, g.maxPanY, 0.5f)
    }

    @Test fun already_small_16by9_image_upscales_to_cover_frame() {
        val g = geo(320, 180)
        assertTrue(g.baseScale > 1f)
        assertTrue(g.drawnW >= g.frameW - 0.5f && g.drawnH >= g.frameH - 0.5f)
    }

    @Test fun crop_is_exactly_16_9_within_tolerance() {
        val r = geo(1080, 2340).withTransform(2.65f, 20f, -40f).toSourceCropRect()!!
        assertTrue("expected ~16:9, got ${r.width}x${r.height}", kotlin.math.abs(ratioOf(r.width, r.height) - CropGeometry.TARGET_RATIO) < 0.02f)
    }

    @Test fun crop_stays_in_bounds_when_panned_to_the_far_edges() {
        val g = geo(1080, 2340).withTransform(1f, 99999f, -99999f)
        val r = g.toSourceCropRect()!!
        assertTrue(r.left >= 0 && r.top >= 0 && r.left + r.width <= 1080 && r.top + r.height <= 2340)
    }

    @Test fun crop_stays_in_bounds_when_panned_to_every_edge() {
        val base = geo(1080, 2340).withTransform(3f, 0f, 0f)
        val extremes = listOf(
            base.maxPanX to base.maxPanY,
            -base.maxPanX to base.maxPanY,
            base.maxPanX to -base.maxPanY,
            -base.maxPanX to -base.maxPanY,
        )
        for ((panX, panY) in extremes) {
            val r = base.withTransform(base.userScale, panX, panY).toSourceCropRect()!!
            assertTrue("rect=$r out of [0,1080)x[0,2340)", r.left >= 0 && r.top >= 0 && r.left + r.width <= 1080 && r.top + r.height <= 2340)
        }
    }

    @Test fun user_scale_is_clamped_to_valid_range() {
        val tooSmall = geo(1080, 2340).withTransform(0.1f, 0f, 0f)
        assertEquals(CropGeometry.MIN_USER_SCALE, tooSmall.userScale, 1e-6f)
        val tooLarge = geo(1080, 2340).withTransform(50f, 0f, 0f)
        assertEquals(CropGeometry.MAX_USER_SCALE, tooLarge.userScale, 1e-6f)
    }

    @Test fun reset_is_deterministic() {
        val a = geo(1080, 2340).withTransform(3f, 50f, 50f).reset()
        val b = geo(1080, 2340).reset()
        assertEquals(b.userScale, a.userScale)
        assertEquals(b.panX, a.panX)
        assertEquals(b.panY, a.panY)
    }

    @Test fun exif_rotated_dims_are_used_as_is() {
        // Caller passes the already EXIF-oriented dimensions; the model is agnostic to rotation.
        val g = geo(2340, 1080)
        val r = g.toSourceCropRect()!!
        assertTrue(r.left + r.width <= 2340 && r.top + r.height <= 1080)
    }

    @Test fun invalid_inputs_return_null() {
        assertNull(CropGeometry(0, 100, 100f, 100f).toSourceCropRect())
        assertNull(CropGeometry(100, 100, 0f, 100f).toSourceCropRect())
        assertNull(CropGeometry(100, 0, 100f, 100f).toSourceCropRect())
        assertNull(CropGeometry(100, 100, 100f, 0f).toSourceCropRect())
    }

    /**
     * Regression for the reported over-zoom defect.
     *
     * The report this fix is based on proposed asserting that, for the exact numeric state
     * logged from the buggy build (source=1080x2340, scale~=2.6518), the NEW crop width would be
     * larger than the OLD logged width of 407px. That does not hold for this codebase: the real
     * `ManualCropDialog`'s pre-fix export math (`cropRectFor`) already used this same cover-scale
     * formula (`baseScale = max(frameW/srcW, frameH/srcH)`, `width = frameW / (baseScale*userScale)`)
     * -- confirmed by reading the pre-fix source, not the decompiled APK the report was written
     * against. Feeding the same numeric (userScale, pan) state into that unchanged formula yields
     * the same width regardless of this fix, so ">407" is not something a pure geometry change can
     * satisfy here.
     *
     * The actual, confirmed defect was entirely in the DISPLAY: the old `ManualCropDialog` drew the
     * bitmap with `ContentScale.Fit` *and then* an additional `graphicsLayer` scale, so the true
     * on-screen scale was `fitScale * baseScale * userScale` while the export only ever used
     * `baseScale * userScale` -- so what the user saw on screen (a wide, mostly-zoomed-out framing)
     * did not correspond to what got exported (a tight, ~2.8x zoomed crop) for the same slider state.
     * This test reproduces that gap explicitly and asserts the new model has no such hidden factor.
     */
    @Test fun display_scale_has_no_hidden_content_scale_factor() {
        val g = geo(1080, 2340).withTransform(2.6518f, 6.15f, 49.93f)

        // The model's own on-screen scale is exactly baseScale * userScale -- nothing else.
        assertEquals(g.baseScale * g.userScale, g.totalScale, 1e-4f)
        assertEquals(g.sourceW * g.totalScale, g.drawnW, 1e-2f)

        // Reproduce what the OLD buggy display would have shown for this same state: an extra,
        // un-exported ContentScale.Fit factor stacked on top of the same baseScale*userScale.
        val fitScale = minOf(g.viewportW / g.sourceW, g.viewportH / g.sourceH)
        val buggyDisplayScale = fitScale * g.baseScale * g.userScale
        val buggyApparentWidth = g.frameW / buggyDisplayScale

        val rect = g.toSourceCropRect()!!
        assertTrue(
            "expected the Fit-contaminated display (${buggyApparentWidth}px apparent) to look far " +
                "less zoomed than the actually-exported crop (${rect.width}px) -- the historical bug",
            buggyApparentWidth > rect.width * 2f,
        )
    }

    /**
     * Direct "manual crop preserves the selected composition" check, computed a second, independent
     * way: intersect the drawn image rect with the fixed frame rect (both in viewport px) to get the
     * visible source region, then map that intersection back to source px. This must equal
     * `toSourceCropRect()` -- proving the exported rect really is the region visible under the frame,
     * not merely a formula that happens to look similar.
     */
    @Test fun exported_rect_equals_the_source_region_visible_under_the_frame() {
        for ((sw, sh) in listOf(1080 to 2340, 2400 to 1080, 1000 to 1000, 1920 to 1080, 3440 to 1080)) {
            val g = geo(sw, sh).withTransform(2.2f, 33f, -17f)
            val dst = g.imageDestRect()
            val frame = g.frameRect()
            val visibleLeft = maxOf(dst[0], frame[0])
            val visibleTop = maxOf(dst[1], frame[1])
            val visibleRight = minOf(dst[2], frame[2])
            val visibleBottom = minOf(dst[3], frame[3])
            // At userScale >= 1 the image always fully covers the frame (no blank), so the visible
            // intersection must equal the frame itself.
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
}
