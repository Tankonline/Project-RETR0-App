package com.retr0.projectretr0.installer

import android.util.Log
import java.io.File

/**
 * High-Accuracy Scanner for Real Racing 3 character.2.dat files.
 * Uses Double-precision binary matching for accurate currency detection.
 */
object SaveScanner {
    private const val TAG = "RETR0_SCANNER"

    // The proven modder key for this file version
    private val RR3_KEY = byteArrayOf(0xAD.toByte(), 0x2A.toByte(), 0x74.toByte(), 0x83.toByte(), 0xBC.toByte(), 0xA6.toByte(), 0x3C.toByte(), 0x07.toByte())

    data class Stats(
        val level: Int = 0,
        val gold: Long = 0,
        val rs: Long = 0,
    )

    fun scan(file: File): Stats {
        if (!file.exists()) return Stats()
        
        return try {
            val rawBytes = file.readBytes()
            
            // Decrypt using the proven key
            val data = xor(rawBytes, RR3_KEY)

            // RR3 Logic: Level is an Integer, Currencies are Doubles
            val level = findIntStat(data, "Level") ?: 0
            val gold = findDoubleStat(data, "Gold") ?: 0L
            val rs = findDoubleStat(data, "Currency") ?: 0L

            Log.i(TAG, "Scanner Result -> Lvl: $level, Gold: $gold, RS: $rs")
            Stats(level.toInt().coerceIn(0, 15000), gold, rs)
        } catch (e: Exception) {
            Log.e(TAG, "Scanner failed", e)
            Stats()
        }
    }

    private fun xor(data: ByteArray, key: ByteArray): ByteArray {
        val result = ByteArray(data.size)
        for (i in data.indices) {
            result[i] = (data[i].toInt() xor key[i % key.size].toInt()).toByte()
        }
        return result
    }

    private fun findIntStat(data: ByteArray, keyName: String): Long? {
        val index = indexOf(data, keyName.toByteArray())
        if (index == -1) return null
        
        // Search the next 16 bytes for an integer marker (0x10-0x13)
        for (i in (index + keyName.length) until (index + keyName.length + 16).coerceAtMost(data.size)) {
            val byte = data[i].toInt() and 0xFF
            if ((byte in 0x10..0x13)) {
                val len = 1 shl (byte and 0x0F)
                return parseBigEndian(data, i + 1, len)
            }
        }
        return null
    }

    private fun findDoubleStat(data: ByteArray, keyName: String): Long? {
        val index = indexOf(data, keyName.toByteArray())
        if (index == -1) return null
        
        // Search the next 16 bytes for a Double marker (0x23)
        for (i in (index + keyName.length) until (index + keyName.length + 16).coerceAtMost(data.size)) {
            if ((data[i].toInt() and 0xFF) == 0x23) {
                val bits = parseBigEndian(data, i + 1, 8)
                return java.lang.Double.longBitsToDouble(bits).toLong()
            }
        }
        return null
    }

    private fun parseBigEndian(data: ByteArray, start: Int, len: Int): Long {
        var value = 0L
        for (i in 0 until len) {
            value = (value shl 8) or (data[start + i].toLong() and 0xFF)
        }
        return value
    }

    private fun indexOf(outer: ByteArray, target: ByteArray): Int {
        for (i in 0..outer.size - target.size) {
            if (target.indices.all { outer[i + it] == target[it] }) return i
        }
        return -1
    }
}
