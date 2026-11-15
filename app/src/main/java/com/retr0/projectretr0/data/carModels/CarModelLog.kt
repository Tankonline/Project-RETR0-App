package com.retr0.projectretr0.data.carModels

import android.util.Log
import com.retr0.projectretr0.BuildConfig

/** One logcat tag for every Car Models operation: `adb logcat -s RETR0-CarModels`. Never log API keys or auth values. */
object CarModelLog {
    const val TAG = "RETR0-CarModels"
    const val OVERWRITE_TAG = "RETR0-CarModelOverwrite"
    const val ENGINE_TAG = "RETR0-CarModelEngine"
    const val DIAGNOSTIC_TAG = "RETR0-CarModelDiag"
    fun fail(code: CarModelErrorCode, detail: String) = Log.e(TAG, "[${code.code}] $detail")
    fun info(code: String, detail: String) = Log.i(TAG, "[$code] $detail")
    fun step(detail: String) { if (BuildConfig.DEBUG) Log.d(TAG, detail) }
    fun overwrite(detail: String) { if (BuildConfig.DEBUG) Log.d(OVERWRITE_TAG, detail) }
    fun engine(detail: String) { if (BuildConfig.DEBUG) Log.d(ENGINE_TAG, detail) }
    fun diagnostic(code: String, detail: String) { if (BuildConfig.DEBUG) Log.d(DIAGNOSTIC_TAG, "[$code] $detail") }

    /** Structured apply diagnostics are always emitted in debug builds for in-app filtering. */
    fun apply(code: String, detail: String) {
        if (BuildConfig.DEBUG) Log.i(TAG, "[CM-APPLY][$code] $detail")
    }

    /** `(pkg=r3_row, vehicle=2001_bmw_m3_gtr_street)` */
    fun where(gamePackage: String?, vehicleDirectory: String?): String =
        "(pkg=${gamePackage?.substringAfterLast('.') ?: "?"}, vehicle=${vehicleDirectory ?: "?"})"
}
