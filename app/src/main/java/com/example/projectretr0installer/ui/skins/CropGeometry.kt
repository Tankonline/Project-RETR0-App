package com.example.projectretr0installer.ui.skins

import kotlin.math.roundToInt

/**
 * Canonical, pure, testable crop geometry shared by every fixed-aspect-ratio manual crop editor
 * in the app (16:9 skin cover/screenshot crop, 1:1 PFP crop). [targetRatio] parameterizes the
 * frame's width/height ratio so the same coordinate model -- viewport, base "cover" scale, pan
 * clamping, and source-rect export -- is never duplicated per aspect ratio (avoids the
 * "double-scaling" bug class where the on-screen transform and the export transform drift apart).
 *
 * All values are pixels. The source dimensions must describe the same EXIF-oriented
 * bitmap that the crop editor renders and that the image processor later crops.
 */
data class CropGeometry(
    val sourceW: Int,
    val sourceH: Int,
    val viewportW: Float,
    val viewportH: Float,
    val userScale: Float = MIN_USER_SCALE,
    val panX: Float = 0f,
    val panY: Float = 0f,
    val targetRatio: Float = TARGET_RATIO,
) {
    companion object {
        const val TARGET_RATIO = 16f / 9f
        const val SQUARE_RATIO = 1f
        const val MIN_USER_SCALE = 1f
        const val MAX_USER_SCALE = 8f

        fun isValid(sourceW: Int, sourceH: Int, viewportW: Float, viewportH: Float): Boolean =
            sourceW > 0 && sourceH > 0 && viewportW > 0f && viewportH > 0f
    }

    val valid: Boolean get() = isValid(sourceW, sourceH, viewportW, viewportH)

    val frameW: Float get() = if (!valid) 0f else if (viewportW / viewportH >= targetRatio) viewportH * targetRatio else viewportW
    val frameH: Float get() = frameW / targetRatio
    val frameLeft: Float get() = (viewportW - frameW) / 2f
    val frameTop: Float get() = (viewportH - frameH) / 2f
    private val frameCenterX: Float get() = frameLeft + frameW / 2f
    private val frameCenterY: Float get() = frameTop + frameH / 2f

    val baseScale: Float get() = if (!valid) 0f else maxOf(frameW / sourceW, frameH / sourceH)
    val totalScale: Float get() = baseScale * userScale.coerceIn(MIN_USER_SCALE, MAX_USER_SCALE)
    val drawnW: Float get() = sourceW * totalScale
    val drawnH: Float get() = sourceH * totalScale
    val maxPanX: Float get() = ((drawnW - frameW) / 2f).coerceAtLeast(0f)
    val maxPanY: Float get() = ((drawnH - frameH) / 2f).coerceAtLeast(0f)
    val imageLeft: Float get() = frameCenterX - drawnW / 2f + panX
    val imageTop: Float get() = frameCenterY - drawnH / 2f + panY

    fun withTransform(newUserScale: Float, newPanX: Float, newPanY: Float): CropGeometry {
        val scaled = copy(userScale = newUserScale.coerceIn(MIN_USER_SCALE, MAX_USER_SCALE))
        return scaled.copy(
            panX = newPanX.coerceIn(-scaled.maxPanX, scaled.maxPanX),
            panY = newPanY.coerceIn(-scaled.maxPanY, scaled.maxPanY),
        )
    }

    fun reset(): CropGeometry = copy(userScale = MIN_USER_SCALE, panX = 0f, panY = 0f)

    fun imageDestRect(): FloatArray = floatArrayOf(imageLeft, imageTop, imageLeft + drawnW, imageTop + drawnH)

    fun frameRect(): FloatArray = floatArrayOf(frameLeft, frameTop, frameLeft + frameW, frameTop + frameH)

    fun toSourceCropRect(): CropRect? {
        if (!valid || totalScale <= 0f || totalScale.isNaN() || totalScale.isInfinite()) return null
        val left = ((frameLeft - imageLeft) / totalScale).roundToInt()
        val top = ((frameTop - imageTop) / totalScale).roundToInt()
        val width = (frameW / totalScale).roundToInt().coerceAtLeast(1)
        val height = (width / targetRatio).roundToInt().coerceAtLeast(1)
        return runCatching {
            SkinImageCropMath.clampToImage(
                CropRect(left.coerceAtLeast(0), top.coerceAtLeast(0), width, height),
                sourceW,
                sourceH,
            )
        }.getOrNull()
    }
}
