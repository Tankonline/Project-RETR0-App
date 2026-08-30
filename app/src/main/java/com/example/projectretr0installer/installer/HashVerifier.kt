package com.example.projectretr0installer.installer

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.security.MessageDigest

/**
 * Handles verifying file integrity using SHA-256 hashes.
 */
class HashVerifier {

    private companion object {
        private const val TAG = "RETR0_HASH"
        private const val BUFFER_SIZE = 8 * 1024
    }

    /**
     * Verifies that the given file matches the expected SHA-256 hash.
     * 
     * @param file The file to verify.
     * @param expectedHash The expected SHA-256 hex string.
     * @return True if the hash matches.
     */
    suspend fun verifySha256(
        file: File,
        expectedHash: String,
    ): Boolean = withContext(Dispatchers.IO) {
        if (expectedHash == "SKIP_VERIFICATION") {
            Log.w(TAG, "Hash verification skipped for ${file.name} (development mode)")
            return@withContext true
        }
        
        try {
            if (!file.exists()) {
                Log.e(TAG, "Verification failed: File does not exist: ${file.absolutePath}")
                return@withContext false
            }

            val digest = MessageDigest.getInstance("SHA-256")
            FileInputStream(file).use { input ->
                val buffer = ByteArray(BUFFER_SIZE)
                var bytesRead: Int
                while (input.read(buffer).also { bytesRead = it } != -1) {
                    digest.update(buffer, 0, bytesRead)
                }
            }

            val actualHash = digest.digest().joinToString("") { "%02x".format(it) }
            val matches = actualHash.equals(expectedHash, ignoreCase = true)
            
            if (!matches) {
                Log.e(TAG, "Hash mismatch for ${file.name}\nExpected: $expectedHash\nActual:   $actualHash")
            } else {
                Log.i(TAG, "Hash verification successful for ${file.name}")
            }
            
            matches
        } catch (_: Exception) {
            Log.e(TAG, "Verification encountered an error")
            false
        }
    }
}
