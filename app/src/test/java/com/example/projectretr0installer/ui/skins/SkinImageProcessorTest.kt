package com.example.projectretr0installer.ui.skins

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class SkinImageProcessorTest {
    @Test fun limitsDifferByRole() {
        assertEquals(500L * 1024, SkinImageRole.COVER.maxBytes)
        assertEquals(1024L * 1024, SkinImageRole.SCREENSHOT.maxBytes)
    }

    @Test fun coverFirstAttemptIs1280x720() {
        val attempt = SkinImageRetryPolicy.first(4000, 2000, SkinImageRole.COVER)
        assertEquals(1280, attempt.width)
        assertEquals(720, attempt.height)
    }

    @Test fun screenshotFirstAttemptIs1920x1080() {
        val attempt = SkinImageRetryPolicy.first(4000, 2000, SkinImageRole.SCREENSHOT)
        assertEquals(1920, attempt.width)
        assertEquals(1080, attempt.height)
    }

    @Test fun policyTerminatesAtMinimums() {
        var current = EncodeAttempt(4000, 2000, 90)
        var count = 0
        while (true) {
            current = SkinImageRetryPolicy.next(current) ?: break
            assertTrue(++count < 100)
        }
        assertTrue(current.quality >= SkinImageRetryPolicy.MIN_QUALITY)
        assertTrue(maxOf(current.width, current.height) <= SkinImageRetryPolicy.MIN_EDGE)
    }

    /**
     * Regression: `next()` used to shrink width and height independently, each with its own
     * `roundToInt()`. Two independent roundings drift the ratio away from exactly 16:9 after only
     * a couple of retries (1280x720 -> 1024x576 [exact] -> 819x461 [1.7766, NOT 16:9]). Every
     * attempt in the full retry chain -- for both roles -- must be exactly 16:9, checked with
     * integer cross-multiplication so there is no floating-point tolerance to hide behind.
     */
    @Test fun everyRetryAttemptIsExactly16By9ForCover() = assertRetryChainIsExactly16By9(SkinImageRole.COVER)

    @Test fun everyRetryAttemptIsExactly16By9ForScreenshot() = assertRetryChainIsExactly16By9(SkinImageRole.SCREENSHOT)

    private fun assertRetryChainIsExactly16By9(role: SkinImageRole) {
        var current: EncodeAttempt? = SkinImageRetryPolicy.first(4000, 2000, role)
        var steps = 0
        while (current != null) {
            val attempt = current
            assertEquals(
                "attempt $attempt is not exactly 16:9",
                attempt.height.toLong() * 16,
                attempt.width.toLong() * 9,
            )
            current = SkinImageRetryPolicy.next(attempt)
            assertTrue(++steps < 100)
        }
    }

    @Test fun exact16By9DoesNotNeedCropChoice() {
        assertFalse(SkinImageCropMath.isMateriallyDifferentFrom16By9(1920, 1080))
    }

    @Test fun near16By9DoesNotNeedCropChoice() {
        assertFalse(SkinImageCropMath.isMateriallyDifferentFrom16By9(1600, 900))
    }

    @Test fun portraitImageNeedsCropChoice() {
        assertTrue(SkinImageCropMath.isMateriallyDifferentFrom16By9(1080, 1920))
    }

    // Corrected from stale expectations (556,0,888,500) / (0,696,1000,563): the production
    // formula rounds cropWidth/cropHeight to the NEAREST integer (not floor), which is verifiably
    // closer to a true 16:9 ratio, and `top`/`left` are exactly `(sourceEdge - croppedEdge) / 2` --
    // the definition of "centered". These values are what that verified-correct code produces.
    @Test fun centerCropForWideImageKeepsFullHeight() {
        assertEquals(CropRect(555, 0, 889, 500), SkinImageCropMath.centerCrop16By9(2000, 500))
    }

    @Test fun centerCropForTallImageKeepsFullWidth() {
        assertEquals(CropRect(0, 718, 1000, 563), SkinImageCropMath.centerCrop16By9(1000, 2000))
    }

    @Test fun centerCropIsSymmetricallyCenteredAndInBoundsAcrossManySizes() {
        val sizes = listOf(
            2000 to 500, 1000 to 2000, 1000 to 1000, 2400 to 1080, 3440 to 1080,
            1920 to 1080, 320 to 180, 4032 to 3024, 3024 to 4032, 2340 to 1080,
        )
        for ((w, h) in sizes) {
            val rect = SkinImageCropMath.centerCrop16By9(w, h)
            assertTrue("rect=$rect out of [0,$w)x[0,$h) for source ${w}x$h", rect.left >= 0 && rect.top >= 0 && rect.left + rect.width <= w && rect.top + rect.height <= h)
            // Centered: the margin on either side of the crop differs by at most 1px (integer
            // center truncation), i.e. auto crop never favors one edge over the other.
            val marginBefore = if (rect.width < w) rect.left else rect.top
            val marginAfter = if (rect.width < w) (w - rect.width - rect.left) else (h - rect.height - rect.top)
            assertTrue("auto crop of ${w}x$h is not center-aligned: before=$marginBefore after=$marginAfter", kotlin.math.abs(marginBefore - marginAfter) <= 1)
            val ratio = rect.width.toFloat() / rect.height
            assertTrue("rect=$rect ratio=$ratio not close to 16:9", kotlin.math.abs(ratio - SkinImageCropMath.TARGET_ASPECT_RATIO) < 0.01f)
        }
    }

    @Test fun centerCropIsAPureFunctionIndependentOfManualCropState() {
        // Auto crop must never depend on, or be influenced by, any manual-crop editor state
        // (CropGeometry/userScale/pan) -- it is a pure function of the source dimensions alone,
        // called for CropDecision.AUTO_CROP regardless of whether a manual CropRect exists.
        val first = SkinImageCropMath.centerCrop16By9(1080, 2340)
        val second = SkinImageCropMath.centerCrop16By9(1080, 2340)
        assertEquals(first, second)
    }

    @Test fun cropRectangleIsClampedInsideImage() {
        assertEquals(CropRect(500, 300, 500, 500), SkinImageCropMath.clampToImage(CropRect(900, 900, 500, 500), 1000, 800))
    }

    @Test fun unrotatedExifDimensionsAreUnchanged() {
        assertEquals(1080 to 2340, orientedDimensions(1080, 2340, 0))
        assertEquals(1080 to 2340, orientedDimensions(1080, 2340, 180))
    }

    @Test fun rotated90Or270ExifDimensionsAreSwapped() {
        assertEquals(2340 to 1080, orientedDimensions(1080, 2340, 90))
        assertEquals(2340 to 1080, orientedDimensions(1080, 2340, 270))
    }

    @Test fun exifRotationCanFlipCropChoiceNeed() {
        // A 1080x1920 capture rotated 90 degrees is presented as 1920x1080 -- already 16:9 -- so
        // orientation must be resolved BEFORE the crop-choice/aspect check runs.
        val (rawW, rawH) = 1080 to 1920
        val (orientedW, orientedH) = orientedDimensions(rawW, rawH, 90)
        assertEquals(1920, orientedW)
        assertEquals(1080, orientedH)
        assertFalse(SkinImageCropMath.isMateriallyDifferentFrom16By9(orientedW, orientedH))
        assertTrue(SkinImageCropMath.isMateriallyDifferentFrom16By9(rawW, rawH))
    }

    @Test fun exif90And270BothSwapDimensionsIdentically() {
        // Rotation direction does not change which axis the crop math treats as "width" --
        // both a 90 and a 270 degree correction land on the same oriented dimensions.
        val ninety = orientedDimensions(1080, 1920, 90)
        val twoSeventy = orientedDimensions(1080, 1920, 270)
        assertEquals(ninety, twoSeventy)
        assertEquals(1920 to 1080, ninety)
    }

    @Test fun exact16By9SourceIsNotCroppedAndStaysCentered() {
        // Contract #2: "exact 16:9 images: do not change composition unnecessarily."
        val rect = SkinImageCropMath.centerCrop16By9(1920, 1080)
        assertEquals(CropRect(0, 0, 1920, 1080), rect)
    }

    @Test fun toleranceBoundaryIsExclusive() {
        // ASPECT_TOLERANCE is 0.015; 1000 * (16/9 +/- 0.015) ~= 1792.78 is the boundary.
        assertFalse(SkinImageCropMath.isMateriallyDifferentFrom16By9(1792, 1000))
        assertTrue(SkinImageCropMath.isMateriallyDifferentFrom16By9(1793, 1000))
    }

    @Test fun squareImageCenterCropTrimsTopAndBottomKeepingFullWidth() {
        val rect = SkinImageCropMath.centerCrop16By9(1000, 1000)
        assertEquals(CropRect(0, 218, 1000, 563), rect)
        assertTrue(rect.width == 1000) // full width preserved for a square (relatively "tall") source
    }

    @Test fun ultrawideImageCenterCropKeepsFullHeight() {
        val rect = SkinImageCropMath.centerCrop16By9(3440, 1080)
        assertEquals(1080, rect.height) // full height preserved
        assertEquals(CropRect((3440 - rect.width) / 2, 0, rect.width, 1080), rect)
    }

    @Test fun oddDimensionRoundingKeepsMarginsWithinOnePixel() {
        // 1999 is odd, so the (width - cropWidth) remainder is odd and can't split evenly --
        // the two side margins must differ by at most 1px, never favoring one side by more.
        val rect = SkinImageCropMath.centerCrop16By9(1999, 1000)
        assertEquals(CropRect(110, 0, 1778, 1000), rect)
        val marginBefore = rect.left
        val marginAfter = 1999 - rect.width - rect.left
        assertTrue(kotlin.math.abs(marginBefore - marginAfter) <= 1)
    }

    @Test fun outputMimeTypeIsWebp() {
        assertEquals("image/webp", WEBP_MIME_TYPE)
    }

    @Test fun encodeAttemptCarriesNoCropOrPositionState() {
        // Structural guarantee behind "byte-limit retry preserves the exact centered composition":
        // EncodeAttempt (what SkinImageRetryPolicy.next mutates between retries) only carries
        // width/height/quality. It has no left/top/offset field, so a retry can rescale the
        // already-cropped bitmap but can never re-crop or re-center it.
        val fields = EncodeAttempt::class.java.declaredFields
            .filterNot { it.isSynthetic || it.name.startsWith("$") } // Compose adds a synthetic "$stable" field
            .map { it.name }
            .toSet()
        assertEquals(setOf("width", "height", "quality"), fields)
    }

    @Test fun manualCropIsUnaffectedByAutoCropPureFunction() {
        // Contract: manual crop (CropGeometry.toSourceCropRect) and auto crop (centerCrop16By9)
        // are separate code paths. Calling the auto-crop math must not require, read, or mutate
        // any CropGeometry/manual-crop state.
        val manual = CropGeometry(1080, 2340, 1000f, 825f).withTransform(2f, 15f, -10f)
        val autoBefore = SkinImageCropMath.centerCrop16By9(1080, 2340)
        manual.toSourceCropRect()
        val autoAfter = SkinImageCropMath.centerCrop16By9(1080, 2340)
        assertEquals(autoBefore, autoAfter)
    }
}
