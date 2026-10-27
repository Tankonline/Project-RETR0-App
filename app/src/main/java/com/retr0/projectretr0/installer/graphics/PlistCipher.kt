package com.retr0.projectretr0.installer.graphics

import java.security.MessageDigest
import java.util.Base64

/**
 * Reads and writes RR3's encrypted graphics files (.plist / .feats).
 *
 * RR3 stores these as text XORed with one fixed keystream (same for every file). The keystream
 * below was recovered from RR3 14.0.1 files: AndroidLow.plist rebuilt from the game's own settings
 * dump, extended with the known plist key order, then extended and cross-checked with all 42 .feats
 * files (fixed structure, 190 keys in one order). It covers the first 10420 bytes: every .plist and
 * every .feats in RR3 14.0.1 (longest: iPhone2,1.feats). Bytes 210 and 212 were corrected using the
 * .feats files (3D_SCENE_SCALE is stored as 1.0; the dump's 0.8 is computed at runtime).
 */
object PlistCipher {
    private const val KEYSTREAM_SHA256 = "b8700d285217c1631d824c9521f3bf392d7f0d9bde79dd4ecb435af4347329c1"

    private val keystream: ByteArray by lazy {
        val bytes = Base64.getDecoder().decode(KEYSTREAM_B64)
        val sha = MessageDigest.getInstance("SHA-256").digest(bytes).joinToString("") { "%02x".format(it) }
        check(sha == KEYSTREAM_SHA256) { "Graphics keystream is corrupted" }
        bytes
    }

    /** Number of bytes this keystream can decode or encode. */
    val capacity: Int get() = keystream.size

    fun fits(size: Int): Boolean = size <= keystream.size

    /** XOR is symmetric: the same call encrypts and decrypts. Returns null if the data is too long. */
    fun transform(data: ByteArray): ByteArray? {
        if (data.size > keystream.size) return null
        return ByteArray(data.size) { i -> (data[i].toInt() xor keystream[i].toInt()).toByte() }
    }

    /** Decodes an encrypted plist and checks it really is an XML plist (guards against other game versions). */
    fun decodePlist(encrypted: ByteArray): String? {
        val plain = transform(encrypted) ?: return null
        val text = String(plain, Charsets.UTF_8)
        return text.takeIf { it.startsWith("<?xml") && it.trimEnd().endsWith("</plist>") }
    }

    private const val KEYSTREAM_B64 =
        "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" +
        "LU1NBw=="
}
