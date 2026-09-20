package com.example.projectretr0installer.installer

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import androidx.core.graphics.scale
import androidx.exifinterface.media.ExifInterface
import java.io.ByteArrayOutputStream
import java.io.File
import kotlin.math.max
import kotlin.math.roundToInt

internal const val MUSIC_ARTWORK_LIMIT_BYTES = 500 * 1024
internal const val MUSIC_ARTWORK_MIME_TYPE = "image/jpeg"

internal data class MusicArtworkEncodeAttempt(
    val maxEdge: Int,
    val quality: Int,
)

internal object MusicArtworkCompressionPolicy {
    const val INITIAL_MAX_EDGE = 1600
    const val MIN_MAX_EDGE = 320
    const val INITIAL_QUALITY = 90
    const val MIN_QUALITY = 40

    fun first() = MusicArtworkEncodeAttempt(INITIAL_MAX_EDGE, INITIAL_QUALITY)

    fun next(current: MusicArtworkEncodeAttempt): MusicArtworkEncodeAttempt? = when {
        current.quality > MIN_QUALITY -> current.copy(
            quality = (current.quality - 10).coerceAtLeast(MIN_QUALITY),
        )
        current.maxEdge > MIN_MAX_EDGE -> current.copy(
            maxEdge = (current.maxEdge * 0.8f).roundToInt().coerceAtLeast(MIN_MAX_EDGE),
            quality = 80,
        )
        else -> null
    }

    fun dimensions(width: Int, height: Int, maxEdge: Int): Pair<Int, Int> {
        require(width > 0 && height > 0 && (maxEdge > 0))
        val largestEdge = max(width, height)
        if (largestEdge <= maxEdge) return width to height
        val scale = maxEdge.toFloat() / largestEdge
        return (width * scale).roundToInt().coerceAtLeast(1) to
            (height * scale).roundToInt().coerceAtLeast(1)
    }
}

internal data class ProcessedMusicArtwork(
    val file: File,
    val contentType: String = MUSIC_ARTWORK_MIME_TYPE,
)

internal object MusicArtworkProcessor {
    fun process(source: File, cacheDir: File): ProcessedMusicArtwork {
        require(source.isFile && source.length() > 0L) { "Artwork file is empty" }
        val bounds = BitmapFactory.Options().apply { inJustDecodeBounds = true }
        BitmapFactory.decodeFile(source.absolutePath, bounds)
        require(bounds.outWidth > 0 && bounds.outHeight > 0) { "Artwork is not a supported image" }

        var sampleSize = 1
        while (max(bounds.outWidth / sampleSize, bounds.outHeight / sampleSize) > MusicArtworkCompressionPolicy.INITIAL_MAX_EDGE * 2) {
            sampleSize *= 2
        }

        var decoded: Bitmap? = null
        var oriented: Bitmap? = null
        try {
            decoded = BitmapFactory.decodeFile(
                source.absolutePath,
                BitmapFactory.Options().apply { inSampleSize = sampleSize },
            ) ?: error("Android could not decode the artwork")
            val rotation = runCatching { ExifInterface(source).rotationDegrees }.getOrDefault(0)
            oriented = if (rotation == 0) {
                decoded
            } else {
                Bitmap.createBitmap(
                    decoded,
                    0,
                    0,
                    decoded.width,
                    decoded.height,
                    Matrix().apply { postRotate(rotation.toFloat()) },
                    true,
                ).also { decoded.recycle() }
            }

            var attempt = MusicArtworkCompressionPolicy.first()
            while (true) {
                val (width, height) = MusicArtworkCompressionPolicy.dimensions(
                    oriented.width,
                    oriented.height,
                    attempt.maxEdge,
                )
                val scaled = if (oriented.width == width && oriented.height == height) {
                    oriented
                } else {
                    oriented.scale(width, height)
                }
                val bytes = try {
                    ByteArrayOutputStream().use { output ->
                        check(scaled.compress(Bitmap.CompressFormat.JPEG, attempt.quality, output)) {
                            "Android could not encode the artwork as JPEG"
                        }
                        output.toByteArray()
                    }
                } finally {
                    if (scaled !== oriented) scaled.recycle()
                }
                if (bytes.isNotEmpty() && bytes.size <= MUSIC_ARTWORK_LIMIT_BYTES) {
                    val output = File.createTempFile("music_cover_encoded_", ".jpg", cacheDir)
                    try {
                        output.outputStream().use { it.write(bytes) }
                        return ProcessedMusicArtwork(output)
                    } catch (error: Throwable) {
                        output.delete()
                        throw error
                    }
                }
                attempt = MusicArtworkCompressionPolicy.next(attempt)
                    ?: error("Artwork could not be compressed below 500 KiB")
            }
        } finally {
            oriented?.recycle()
            if (decoded !== oriented) decoded?.recycle()
        }
    }
}
