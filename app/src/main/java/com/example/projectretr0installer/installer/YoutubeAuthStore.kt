package com.example.projectretr0installer.installer

import android.content.Context
import android.net.Uri
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.io.File
import java.io.FileOutputStream
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

/**
 * Stores YouTube authentication data locally on this device.
 *
 * Security boundary:
 * - Uses Context.noBackupFilesDir so Android backup/restore does not copy the credential blob.
 * - Encrypts the cookie file with an Android Keystore AES-256-GCM key.
 * - Never exposes the stored cookie bytes to Community Hub, Cloudflare, analytics, or logs.
 * - Decrypts only to a short-lived private-cache file immediately before yt-dlp needs it.
 */
class YoutubeAuthStore(private val context: Context) {

    companion object {
        private const val KEY_ALIAS = "retr0_youtube_auth_v1"
        private const val AUTH_DIR = "youtube_auth"
        private const val ENCRYPTED_COOKIE_FILE = "cookies.enc"
        private const val TEMP_COOKIE_FILE = "cookies.txt"
        private const val IV_LENGTH = 12
        private const val GCM_TAG_BITS = 128
        private const val MAX_COOKIE_BYTES = 2L * 1024L * 1024L
    }

    private val authDir: File
        get() = File(context.noBackupFilesDir, AUTH_DIR)

    private val encryptedCookieFile: File
        get() = File(authDir, ENCRYPTED_COOKIE_FILE)

    fun hasCookies(): Boolean = encryptedCookieFile.isFile && encryptedCookieFile.length() > IV_LENGTH

    fun importCookies(uri: Uri): Result<Unit> = runCatching {
        val bytes = context.contentResolver.openInputStream(uri)?.use { input ->
            input.readBytes()
        } ?: error("Could not open the selected cookie file.")

        require(bytes.isNotEmpty()) { "The cookie file is empty." }
        require(bytes.size <= MAX_COOKIE_BYTES) { "The cookie file is too large." }
        validateCookieFile(bytes)

        authDir.mkdirs()
        val encrypted = encrypt(bytes)
        FileOutputStream(encryptedCookieFile).use { it.write(encrypted) }
    }

    fun clearCookies() {
        encryptedCookieFile.delete()
        if (authDir.isDirectory && authDir.listFiles()?.isEmpty() == true) {
            authDir.delete()
        }
    }

    /**
     * Provides yt-dlp with a temporary plaintext cookie file.
     * The file is private app-cache data and is deleted in finally.
     */
    suspend fun <T> withCookieFile(block: suspend (File) -> T): T? {
        if (!hasCookies()) return null

        val tempFile = File(context.cacheDir, "retr0_youtube_auth/$TEMP_COOKIE_FILE")
        tempFile.parentFile?.mkdirs()

        return try {
            val bytes = decrypt()
            FileOutputStream(tempFile).use { it.write(bytes) }
            block(tempFile)
        } finally {
            tempFile.delete()
            tempFile.parentFile?.let { parent ->
                if (parent.isDirectory && parent.listFiles()?.isEmpty() == true) {
                    parent.delete()
                }
            }
        }
    }

    private fun validateCookieFile(bytes: ByteArray) {
        val text = bytes.toString(Charsets.UTF_8)
        val hasNetscapeHeader = text.lineSequence().any {
            it.trimStart().startsWith("# Netscape HTTP Cookie File") ||
                it.trimStart().startsWith("# HTTP Cookie File")
        }
        val hasCookieRows = text.lineSequence().any { line ->
            val value = line.trim()
            value.isNotEmpty() &&
                !value.startsWith("#") &&
                value.split('\t').size >= 7
        }

        require(hasNetscapeHeader || hasCookieRows) {
            "This does not look like a Netscape-format cookies.txt file."
        }
    }

    private fun getOrCreateKey(): SecretKey {
        val keyStore = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }
        (keyStore.getKey(KEY_ALIAS, null) as? SecretKey)?.let { return it }

        val generator = KeyGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_AES,
            "AndroidKeyStore",
        )
        generator.init(
            KeyGenParameterSpec.Builder(
                KEY_ALIAS,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT,
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .setKeySize(256)
                .build()
        )
        return generator.generateKey()
    }

    private fun encrypt(plain: ByteArray): ByteArray {
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE, getOrCreateKey())
        val iv = cipher.iv
        val ciphertext = cipher.doFinal(plain)
        return iv + ciphertext
    }

    private fun decrypt(): ByteArray {
        val stored = encryptedCookieFile.readBytes()
        require(stored.size > IV_LENGTH) { "Stored YouTube authentication data is invalid." }

        val iv = stored.copyOfRange(0, IV_LENGTH)
        val ciphertext = stored.copyOfRange(IV_LENGTH, stored.size)

        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(
            Cipher.DECRYPT_MODE,
            getOrCreateKey(),
            GCMParameterSpec(GCM_TAG_BITS, iv),
        )
        return cipher.doFinal(ciphertext)
    }
}
