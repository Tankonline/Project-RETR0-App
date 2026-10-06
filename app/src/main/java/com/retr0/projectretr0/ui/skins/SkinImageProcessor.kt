package com.retr0.projectretr0.ui.skins

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.Rect
import android.net.Uri
import android.util.Log
import androidx.core.graphics.scale
import androidx.exifinterface.media.ExifInterface
import com.retr0.projectretr0.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.security.MessageDigest
import java.util.UUID
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.roundToInt

const val COVER_LIMIT_BYTES = 500L * 1024
const val SCREENSHOT_LIMIT_BYTES = 1024L * 1024
const val PFP_LIMIT_BYTES = 200L * 1024
const val WEBP_MIME_TYPE = "image/webp"

private const val CROP_LOG_TAG = "SkinImageCrop"

enum class SkinImageRole(
    val maxBytes: Long,
    val targetWidth: Int,
    val targetHeight: Int,
) {
    COVER(COVER_LIMIT_BYTES, 1280, 720),
    SCREENSHOT(SCREENSHOT_LIMIT_BYTES, 1920, 1080),
    PFP(PFP_LIMIT_BYTES, 512, 512),

    ;

    val maxEdge: Int get() = maxOf(targetWidth, targetHeight)

    /** Width/height ratio the crop and encode pipeline must preserve for this role. */
    val aspectRatio: Float get() = targetWidth.toFloat() / targetHeight

    /** How far a source image's ratio may drift from [aspectRatio] before a crop choice is required. */
    val cropTolerance: Float get() = if (this == PFP) SkinImageCropMath.SQUARE_ASPECT_TOLERANCE else SkinImageCropMath.ASPECT_TOLERANCE
}

enum class CropDecision { AUTO_CROP, MANUAL_CROP }

data class CropRect(val left: Int, val top: Int, val width: Int, val height: Int) {
    init {
        require(left >= 0 && top >= 0) { "Crop origin must be within the image." }
        require(width > 0 && height > 0) { "Crop size must be positive." }
    }

    fun toAndroidRect() = Rect(left, top, left + width, top + height)
}

/**
 * The pixel dimensions of a bitmap after EXIF rotation is applied. A 90/270 degree rotation
 * swaps width and height; 0/180 do not. Pulled out as a pure function (mirrors what
 * `Bitmap.createBitmap(..., Matrix().postRotate(degrees), true)` produces dimension-wise) so the
 * EXIF-orientation contract is unit-testable without decoding a real bitmap.
 */
internal fun orientedDimensions(rawWidth: Int, rawHeight: Int, rotationDegrees: Int): Pair<Int, Int> =
    if (rotationDegrees == 90 || rotationDegrees == 270) rawHeight to rawWidth else rawWidth to rawHeight

object SkinImageCropMath {
    const val TARGET_ASPECT_RATIO = 16f / 9f
    const val ASPECT_TOLERANCE = 0.015f
    const val SQUARE_ASPECT_RATIO = 1f
    const val SQUARE_ASPECT_TOLERANCE = 0.01f

    /**
     * True when [width]x[height]'s ratio differs from [ratio] by more than [tolerance]. The
     * single generic predicate behind every role-specific "does this need a crop choice" check
     * ([isMateriallyDifferentFrom16By9], [isMateriallyDifferentFromSquare]) so 16:9 and 1:1 (and any
     * future ratio) share one tested implementation instead of parallel near-duplicates.
     */
    fun isMateriallyDifferentFromRatio(width: Int, height: Int, ratio: Float, tolerance: Float): Boolean {
        require(width > 0 && height > 0) { "Image dimensions must be positive." }
        return abs(width.toFloat() / height - ratio) > tolerance
    }

    fun isMateriallyDifferentFrom16By9(width: Int, height: Int): Boolean =
        isMateriallyDifferentFromRatio(width, height, TARGET_ASPECT_RATIO, ASPECT_TOLERANCE)

    fun isMateriallyDifferentFromSquare(width: Int, height: Int): Boolean =
        isMateriallyDifferentFromRatio(width, height, SQUARE_ASPECT_RATIO, SQUARE_ASPECT_TOLERANCE)

    /**
     * Largest centered crop of [width]x[height] at [ratio], trimming only the longer axis so the
     * shorter axis is always preserved in full. Nearest-integer rounding on the trimmed edge (not
     * floor) keeps the result as close to the true ratio as an integer crop allows; identical
     * formula that [centerCrop16By9] used before this ratio was generalized, so its exact pixel
     * outputs for 16:9 are unchanged.
     */
    fun centerCropToRatio(width: Int, height: Int, ratio: Float): CropRect {
        require(width > 0 && height > 0) { "Image dimensions must be positive." }
        val sourceRatio = width.toFloat() / height
        return if (sourceRatio > ratio) {
            val cropWidth = (height * ratio).roundToInt().coerceAtMost(width)
            CropRect((width - cropWidth) / 2, 0, cropWidth, height)
        } else {
            val cropHeight = (width / ratio).roundToInt().coerceAtMost(height)
            CropRect(0, (height - cropHeight) / 2, width, cropHeight)
        }
    }

    fun centerCrop16By9(width: Int, height: Int): CropRect = centerCropToRatio(width, height, TARGET_ASPECT_RATIO)

    /** Largest centered square crop -- the PFP auto-crop path for portrait, landscape, or square input. */
    fun centerCrop1By1(width: Int, height: Int): CropRect = centerCropToRatio(width, height, SQUARE_ASPECT_RATIO)

    fun clampToImage(rect: CropRect, imageWidth: Int, imageHeight: Int): CropRect {
        require(imageWidth > 0 && imageHeight > 0) { "Image dimensions must be positive." }
        val width = rect.width.coerceAtMost(imageWidth)
        val height = rect.height.coerceAtMost(imageHeight)
        return CropRect(
            left = rect.left.coerceIn(0, imageWidth - width),
            top = rect.top.coerceIn(0, imageHeight - height),
            width = width,
            height = height,
        )
    }
}

data class ProcessedSkinImage(
    val id: String,
    val file: File,
    val width: Int,
    val height: Int,
    val sizeBytes: Long,
    val mimeType: String,
    val role: SkinImageRole,
    val sourceHash: String,
)

data class EncodeAttempt(val width: Int, val height: Int, val quality: Int)

/**
 * Result of decoding a source image through the EXIF-orientation + sample-size pipeline.
 * Carries the raw (pre-sample, pre-rotation) file dimensions and the actual decoded
 * (pre-rotation) dimensions alongside the final oriented [bitmap], so callers can log and
 * cross-check the full raw -> sampled -> oriented chain without re-decoding.
 */
private data class DecodedSource(
    val bitmap: Bitmap,
    val rawWidth: Int,
    val rawHeight: Int,
    val sampledWidth: Int,
    val sampledHeight: Int,
    val rotationDegrees: Int,
    val sampleSize: Int,
)

object SkinImageRetryPolicy {
    const val MIN_EDGE = 320
    const val MIN_QUALITY = 40

    fun first(@Suppress("UNUSED_PARAMETER") width: Int, @Suppress("UNUSED_PARAMETER") height: Int, role: SkinImageRole): EncodeAttempt = EncodeAttempt(
        width = role.targetWidth,
        height = role.targetHeight,
        quality = 90,
    )

    // The role's target ratio reduced to coprime integer units (16:9 -> 16,9; 512x512 -> 1,1).
    // Shrinking the width to a multiple of widthUnits guarantees width * heightUnits / widthUnits
    // is an exact integer, so every retry step lands on an EXACT ratio. Rounding width and height
    // independently (the pre-generalization approach) drifts the ratio after only two shrink steps
    // (1280x720 -> 1024x576 [exact] -> 819x461, ratio 1.7766 not 1.7778) and compounds from there.
    private fun ratioUnits(role: SkinImageRole): Pair<Int, Int> {
        val divisor = gcd(role.targetWidth, role.targetHeight)
        return role.targetWidth / divisor to role.targetHeight / divisor
    }

    private tailrec fun gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)

    fun next(current: EncodeAttempt, role: SkinImageRole = SkinImageRole.COVER): EncodeAttempt? = when {
        current.quality > MIN_QUALITY -> current.copy(quality = (current.quality - 10).coerceAtLeast(MIN_QUALITY))
        max(current.width, current.height) > MIN_EDGE -> {
            val (widthUnits, heightUnits) = ratioUnits(role)
            val rawWidth = (current.width * .8f).roundToInt().coerceAtLeast(widthUnits)
            val width = (rawWidth.toFloat() / widthUnits).roundToInt().coerceAtLeast(1) * widthUnits
            current.copy(width = width, height = width / widthUnits * heightUnits, quality = 80)
        }
        else -> null
    }
}

class SkinImageProcessor(private val resolver: ContentResolver, private val cache: SkinImportCache) {
    suspend fun inspect(
        uri: Uri,
        aspectRatio: Float = SkinImageCropMath.TARGET_ASPECT_RATIO,
        tolerance: Float = SkinImageCropMath.ASPECT_TOLERANCE,
    ): Result<ImageInspection> = withContext(Dispatchers.IO) {
        runCatching {
            val bounds = BitmapFactory.Options().apply { inJustDecodeBounds = true }
            resolver.openInputStream(uri)?.use { BitmapFactory.decodeStream(it, null, bounds) }
            require(bounds.outWidth > 0 && bounds.outHeight > 0) { "The selected file is not a supported image." }
            val rotation = orientation(uri)
            val (width, height) = orientedDimensions(bounds.outWidth, bounds.outHeight, rotation)
            ImageInspection(width, height, SkinImageCropMath.isMateriallyDifferentFromRatio(width, height, aspectRatio, tolerance))
        }
    }

    /**
     * Decodes the same EXIF-oriented, sample-reduced bitmap [process] will crop, so the manual
     * crop editor's viewport/geometry always matches the pixels the export will actually use.
     */
    suspend fun decodeForManualCrop(uri: Uri, role: SkinImageRole): Result<Bitmap> = withContext(Dispatchers.IO) {
        runCatching { decodeOriented(uri, role).bitmap }.onFailure { error ->
            if (role == SkinImageRole.PFP) {
                Log.e(CROP_LOG_TAG, "PFP_CROP_E001 source_decode_failure role=$role error=${error.message}")
            }
        }
    }

    suspend fun process(
        uri: Uri,
        role: SkinImageRole,
        cropDecision: CropDecision = CropDecision.AUTO_CROP,
        cropRect: CropRect? = null,
    ): Result<ProcessedSkinImage> = withContext(Dispatchers.IO) {
        runCatching {
            val hash = sourceHash(uri)
            val decoded = try {
                decodeOriented(uri, role)
            } catch (error: Throwable) {
                if (role == SkinImageRole.PFP) {
                    Log.e(CROP_LOG_TAG, "PFP_CROP_E001 source_decode_failure role=$role error=${error.message}")
                }
                throw error
            }
            val oriented = decoded.bitmap
            val isAutoCrop = cropDecision == CropDecision.AUTO_CROP

            if (oriented.width <= 0 || oriented.height <= 0) {
                if (isAutoCrop) {
                    Log.e(CROP_LOG_TAG, "AUTO_CROP_E001_INVALID_SOURCE_DIMENSIONS role=$role width=${oriented.width} height=${oriented.height}")
                }
                if (role == SkinImageRole.PFP) {
                    Log.e(CROP_LOG_TAG, "PFP_CROP_E001 source_decode_failure role=$role width=${oriented.width} height=${oriented.height}")
                }
                error("The selected image has invalid dimensions.")
            }

            // Sanity check: the crop rect below is computed from oriented.width/height, so those
            // must equal orientedDimensions() applied to the *actual* decoded (pre-rotation) bitmap
            // size -- not an estimate derived from dividing raw bounds by inSampleSize, since
            // BitmapFactory does not guarantee outWidth/inSampleSize exactly equals the decoded
            // bitmap's width (see Android's "Loading Large Bitmaps Efficiently" guide). A mismatch
            // here would mean the crop math and the pixels actually being cropped have drifted apart.
            val predictedOriented = orientedDimensions(decoded.sampledWidth, decoded.sampledHeight, decoded.rotationDegrees)
            if (isAutoCrop && (predictedOriented.first != oriented.width || predictedOriented.second != oriented.height)) {
                Log.e(
                    CROP_LOG_TAG,
                    "AUTO_CROP_E003_ORIENTATION_MISMATCH role=$role rotation=${decoded.rotationDegrees} " +
                        "predicted=${predictedOriented.first}x${predictedOriented.second} actual=${oriented.width}x${oriented.height}",
                )
            }

            if (isAutoCrop) {
                val rawRatio = decoded.rawWidth.toFloat() / decoded.rawHeight
                val orientedRatio = oriented.width.toFloat() / oriented.height
                val withinTolerance = !SkinImageCropMath.isMateriallyDifferentFromRatio(oriented.width, oriented.height, role.aspectRatio, role.cropTolerance)
                Log.i(
                    CROP_LOG_TAG,
                    "AUTO_CROP_CLASSIFIED role=$role rawW=${decoded.rawWidth} rawH=${decoded.rawHeight} rawRatio=$rawRatio " +
                        "orientedW=${oriented.width} orientedH=${oriented.height} orientedRatio=$orientedRatio " +
                        "rotationDegrees=${decoded.rotationDegrees} withinTolerance=$withinTolerance",
                )
                if (withinTolerance) {
                    Log.i(
                        CROP_LOG_TAG,
                        "AUTO_CROP_BYPASSED_WITHIN_TOLERANCE role=$role orientedW=${oriented.width} orientedH=${oriented.height} orientedRatio=$orientedRatio",
                    )
                }
            }

            val requestedCrop = try {
                when (cropDecision) {
                    CropDecision.AUTO_CROP -> SkinImageCropMath.centerCropToRatio(oriented.width, oriented.height, role.aspectRatio)
                    CropDecision.MANUAL_CROP -> requireNotNull(cropRect) { "Choose a crop area before continuing." }
                }
            } catch (error: IllegalArgumentException) {
                if (isAutoCrop) {
                    Log.e(CROP_LOG_TAG, "AUTO_CROP_E002_INVALID_CENTER_CROP_RECT role=$role orientedW=${oriented.width} orientedH=${oriented.height} error=${error.message}")
                }
                throw error
            }
            val safeCrop = try {
                SkinImageCropMath.clampToImage(requestedCrop, oriented.width, oriented.height)
            } catch (error: IllegalArgumentException) {
                if (isAutoCrop) {
                    Log.e(CROP_LOG_TAG, "AUTO_CROP_E002_INVALID_CENTER_CROP_RECT role=$role rect=$requestedCrop orientedW=${oriented.width} orientedH=${oriented.height} error=${error.message}")
                }
                throw error
            }
            Log.i(CROP_LOG_TAG, "CROP_APPLIED decision=$cropDecision role=$role source=${oriented.width}x${oriented.height} rect=${safeCrop.left},${safeCrop.top},${safeCrop.width}x${safeCrop.height}")
            if (isAutoCrop) {
                Log.i(
                    CROP_LOG_TAG,
                    "AUTO_CROP_APPLIED role=$role source=${oriented.width}x${oriented.height} " +
                        "rect=${safeCrop.left},${safeCrop.top},${safeCrop.width}x${safeCrop.height} ratio=${safeCrop.width.toFloat() / safeCrop.height}",
                )
            }
            if (BuildConfig.DEBUG && cropDecision == CropDecision.MANUAL_CROP) {
                val cropRatio = safeCrop.width.toFloat() / safeCrop.height
                if (abs(cropRatio - role.aspectRatio) > 0.01f) {
                    if (role == SkinImageRole.PFP) {
                        Log.e(CROP_LOG_TAG, "PFP_CROP_E003 invalid_ratio_after_crop cropRatio=$cropRatio expected=${role.aspectRatio}")
                    } else {
                        Log.e(CROP_LOG_TAG, "CROP_EDITOR_E009 cropRatio=$cropRatio expected=${role.aspectRatio}")
                    }
                }
            }
            val cropped = Bitmap.createBitmap(oriented, safeCrop.left, safeCrop.top, safeCrop.width, safeCrop.height)
            if (cropped !== oriented) oriented.recycle()
            encode(cropped, role, hash, isAutoCrop).also { cropped.recycle() }
        }
    }

    suspend fun changeRole(image: ProcessedSkinImage, role: SkinImageRole): Result<ProcessedSkinImage> = withContext(Dispatchers.IO) {
        runCatching {
            require(image.file.isFile) { "The selected image is no longer available. Add it again." }
            val bitmap = BitmapFactory.decodeFile(image.file.absolutePath)
                ?: error("Android could not decode the selected image.")
            encode(bitmap, role, image.sourceHash).also { bitmap.recycle() }
        }
    }

    private fun sourceHash(uri: Uri): String = resolver.openInputStream(uri)?.use { input ->
        val digest = MessageDigest.getInstance("SHA-256")
        val buffer = ByteArray(32 * 1024)
        while (true) {
            val count = input.read(buffer)
            if (count < 0) break
            digest.update(buffer, 0, count)
        }
        digest.digest().joinToString("") { "%02x".format(it) }
    } ?: error("Unable to open the selected image.")

    private fun decodeOriented(uri: Uri, role: SkinImageRole): DecodedSource {
        val bounds = BitmapFactory.Options().apply { inJustDecodeBounds = true }
        resolver.openInputStream(uri)?.use { BitmapFactory.decodeStream(it, null, bounds) }
        require(bounds.outWidth > 0 && bounds.outHeight > 0) { "The selected file is not a supported image." }
        var sample = 1
        while (max(bounds.outWidth / sample, bounds.outHeight / sample) > role.maxEdge * 2) sample *= 2
        val rotation = orientation(uri)
        val sampled = resolver.openInputStream(uri)?.use {
            BitmapFactory.decodeStream(it, null, BitmapFactory.Options().apply { inSampleSize = sample })
        } ?: error("Android could not decode the selected image.")
        // Read the actual decoded size back rather than trusting outWidth/sample: inSampleSize is
        // rounded down to a power of two by the decoder and is not guaranteed to divide evenly.
        val sampledWidth = sampled.width
        val sampledHeight = sampled.height
        val oriented = orient(sampled, rotation)
        if (oriented !== sampled) sampled.recycle()
        return DecodedSource(oriented, bounds.outWidth, bounds.outHeight, sampledWidth, sampledHeight, rotation, sample)
    }

    private fun encode(bitmap: Bitmap, role: SkinImageRole, hash: String, isAutoCrop: Boolean = false): ProcessedSkinImage {
        var attempt = SkinImageRetryPolicy.first(bitmap.width, bitmap.height, role)
        var retryOccurred = false
        while (true) {
            val scaled = if (bitmap.width == attempt.width && bitmap.height == attempt.height) bitmap else bitmap.scale(attempt.width, attempt.height)
            val candidate = cache.newImageFile("webp")
            val compressed = FileOutputStream(candidate).use { scaled.compress(Bitmap.CompressFormat.WEBP, attempt.quality, it) }
            if (scaled !== bitmap) scaled.recycle()
            if (!compressed) {
                candidate.delete()
                if (isAutoCrop) {
                    Log.e(CROP_LOG_TAG, "AUTO_CROP_E006_ENCODING_FAILED role=$role dimensions=${attempt.width}x${attempt.height} quality=${attempt.quality} retryOccurred=$retryOccurred")
                }
                error("Android could not encode the image as WebP.")
            }
            Log.d(CROP_LOG_TAG, "encode_attempt role=$role dimensions=${attempt.width}x${attempt.height} quality=${attempt.quality} bytes=${candidate.length()} retryOccurred=$retryOccurred")
            if (candidate.length() <= role.maxBytes) {
                Log.i(CROP_LOG_TAG, "OUTPUT_ENCODED role=$role dimensions=${attempt.width}x${attempt.height} bytes=${candidate.length()} mime=$WEBP_MIME_TYPE retryOccurred=$retryOccurred")
                val outRatio = attempt.width.toFloat() / attempt.height
                if (isAutoCrop) {
                    val ratioValid = abs(outRatio - role.aspectRatio) <= 0.01f
                    val sizeValid = attempt.width in 1..role.targetWidth && attempt.height in 1..role.targetHeight
                    Log.i(
                        CROP_LOG_TAG,
                        "AUTO_CROP_OUTPUT_ENCODED role=$role dimensions=${attempt.width}x${attempt.height} ratio=$outRatio " +
                            "mime=$WEBP_MIME_TYPE bytes=${candidate.length()} retryOccurred=$retryOccurred",
                    )
                    if (!ratioValid) {
                        if (role == SkinImageRole.PFP) {
                            Log.e(CROP_LOG_TAG, "PFP_CROP_E003 invalid_ratio_after_processing role=$role ratio=$outRatio expected=${role.aspectRatio}")
                        } else {
                            Log.e(CROP_LOG_TAG, "AUTO_CROP_E004_OUTPUT_RATIO_INVALID role=$role ratio=$outRatio expected=${role.aspectRatio}")
                        }
                    }
                    if (!sizeValid) {
                        Log.e(CROP_LOG_TAG, "AUTO_CROP_E005_OUTPUT_SIZE_INVALID role=$role dimensions=${attempt.width}x${attempt.height} maxAllowed=${role.targetWidth}x${role.targetHeight}")
                    }
                    if (ratioValid && sizeValid) {
                        Log.i(CROP_LOG_TAG, "AUTO_CROP_OUTPUT_VALIDATED role=$role ratio=$outRatio dimensions=${attempt.width}x${attempt.height}")
                    }
                } else if (BuildConfig.DEBUG) {
                    if (abs(outRatio - role.aspectRatio) > 0.01f) {
                        if (role == SkinImageRole.PFP) {
                            Log.e(CROP_LOG_TAG, "PFP_CROP_E003 invalid_ratio_after_processing outRatio=$outRatio expected=${role.aspectRatio}")
                        } else {
                            Log.e(CROP_LOG_TAG, "CROP_EDITOR_E008 outRatio=$outRatio expected=${role.aspectRatio}")
                        }
                    } else {
                        Log.i(CROP_LOG_TAG, "OUTPUT_VALIDATED ratio=$outRatio")
                    }
                }
                return ProcessedSkinImage(UUID.randomUUID().toString(), candidate, attempt.width, attempt.height, candidate.length(), WEBP_MIME_TYPE, role, hash)
            }
            candidate.delete()
            retryOccurred = true
            attempt = SkinImageRetryPolicy.next(attempt, role) ?: run {
                if (role == SkinImageRole.PFP) {
                    Log.e(CROP_LOG_TAG, "PFP_CROP_E004 byte_limit_exceeded_after_max_compression role=$role maxBytes=${role.maxBytes} lastAttempt=${attempt.width}x${attempt.height}@${attempt.quality}")
                }
                error("The image could not be compressed below ${role.maxBytes / 1024} KiB.")
            }
        }
    }

    private fun orientation(uri: Uri): Int = runCatching {
        resolver.openInputStream(uri)?.use { ExifInterface(it).rotationDegrees } ?: 0
    }.getOrDefault(0)

    private fun orient(bitmap: Bitmap, degrees: Int): Bitmap = if (degrees == 0) {
        bitmap
    } else {
        Log.d(CROP_LOG_TAG, "exif_orientation rotationDegrees=$degrees")
        Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, Matrix().apply { postRotate(degrees.toFloat()) }, true)
    }
}

data class ImageInspection(val width: Int, val height: Int, val needsCropChoice: Boolean)
