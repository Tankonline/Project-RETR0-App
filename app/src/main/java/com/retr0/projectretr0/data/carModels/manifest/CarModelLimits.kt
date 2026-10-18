package com.retr0.projectretr0.data.carModels.manifest

/** Shared binary limits for car-model package validation. */
object CarModelLimits {
    const val MAX_TOTAL_BYTES = 520L * 1024 * 1024
    const val MAX_FILE_BYTES = 20L * 1024 * 1024
    const val MAX_FILES = 256
    const val MAX_MANIFEST_BYTES = 256L * 1024
    const val MAX_COVER_BYTES = 500L * 1024
    const val MAX_SCREENSHOT_BYTES = 1L * 1024 * 1024
    const val MAX_SCREENSHOTS = 8
}
