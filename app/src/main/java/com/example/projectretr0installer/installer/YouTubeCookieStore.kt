package com.example.projectretr0installer.installer

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.io.File
import java.nio.charset.StandardCharsets
import java.security.KeyStore
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

/** Local-only encrypted cookie storage. The plaintext cookie file is never retained. */
class YouTubeCookieStore(private val context: Context) {
    private companion object {
        const val KEYSTORE = "AndroidKeyStore"
        const val KEY_ALIAS = "retr0_youtube_cookies_v1"
        const val FILE_NAME = "youtube_cookies.enc"
        const val TRANSFORMATION = "AES/GCM/NoPadding"
        const val IV_BYTES = 12
        const val TAG_BITS = 128
    }

    private val encryptedFile: File get() = File(context.noBackupFilesDir, FILE_NAME)

    fun hasCookies(): Boolean = encryptedFile.isFile && encryptedFile.length() > IV_BYTES

    fun saveNetscapeCookies(contents: String) {
        require(NetscapeCookieValidator.isValid(contents)) { "Invalid Netscape cookies" }
        val iv = ByteArray(IV_BYTES).also { SecureRandom().nextBytes(it) }
        val cipher = Cipher.getInstance(TRANSFORMATION).apply {
            init(Cipher.ENCRYPT_MODE, key(), GCMParameterSpec(TAG_BITS, iv))
        }
        encryptedFile.parentFile?.mkdirs()
        encryptedFile.writeBytes(iv + cipher.doFinal(contents.toByteArray(StandardCharsets.UTF_8)))
    }

    fun readNetscapeCookies(): String? {
        if (!hasCookies()) return null
        return runCatching {
            val payload = encryptedFile.readBytes()
            val iv = payload.copyOfRange(0, IV_BYTES)
            val ciphertext = payload.copyOfRange(IV_BYTES, payload.size)
            val cipher = Cipher.getInstance(TRANSFORMATION).apply {
                init(Cipher.DECRYPT_MODE, key(), GCMParameterSpec(TAG_BITS, iv))
            }
            cipher.doFinal(ciphertext).toString(StandardCharsets.UTF_8)
        }.getOrNull()?.takeIf(NetscapeCookieValidator::isValid)
    }

    fun remove() {
        encryptedFile.delete()
    }

    private fun key(): SecretKey {
        val store = KeyStore.getInstance(KEYSTORE).apply { load(null) }
        (store.getKey(KEY_ALIAS, null) as? SecretKey)?.let { return it }
        val generator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, KEYSTORE)
        generator.init(
            KeyGenParameterSpec.Builder(
                KEY_ALIAS,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT,
            ).setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .setUserAuthenticationRequired(false)
                .build(),
        )
        return generator.generateKey()
    }
}

object NetscapeCookieValidator {
    private val datePattern = Regex("^-?\\d{1,12}$")

    fun isValid(contents: String): Boolean {
        val lines = contents.lineSequence().map(String::trimEnd).filter { it.isNotBlank() && !it.startsWith("#") }.toList()
        if (lines.isEmpty()) return false
        return lines.all { line ->
            val fields = line.split('\t')
            fields.size >= 7 && fields[0].isNotBlank() &&
                (fields[1] == "TRUE" || fields[1] == "FALSE") &&
                (fields[2] == "/" || fields[2].isNotBlank()) &&
                datePattern.matches(fields[4]) && fields[5].isNotBlank() && fields[6].isNotBlank()
        }
    }
}
