package com.retr0.projectretr0.data.carModels

import android.util.Log
import com.retr0.projectretr0.BuildConfig

/** One logcat tag for every Car Models operation: `adb logcat -s RETR0-CarModels`. Never log API keys or auth values. */
object CarModelLog {
    const val TAG = "RETR0-CarModels"
    fun fail(code: CarModelErrorCode, detail: String) = Log.e(TAG, "[${code.code}] $detail")
    fun info(code: String, detail: String) = Log.i(TAG, "[$code] $detail")
    fun step(detail: String) { if (BuildConfig.DEBUG) Log.d(TAG, detail) }

    /** `(pkg=r3_row, vehicle=2001_bmw_m3_gtr_street)` */
    fun where(gamePackage: String?, vehicleDirectory: String?): String =
        "(pkg=${gamePackage?.substringAfterLast('.') ?: "?"}, vehicle=${vehicleDirectory ?: "?"})"
}
