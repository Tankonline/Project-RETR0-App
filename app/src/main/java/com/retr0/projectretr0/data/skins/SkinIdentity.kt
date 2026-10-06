package com.retr0.projectretr0.data.skins

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.nio.charset.StandardCharsets
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.MessageDigest
import java.security.SecureRandom
import java.security.Signature
import java.security.interfaces.ECPublicKey
import java.security.spec.ECGenParameterSpec
import java.util.UUID

internal data class SignedHeaders(
    val installationId: String,
    val timestamp: String,
    val nonce: String,
    val signature: String,
)

internal data class IdentitySnapshot(
    val installationId: String,
    val publicKey: SkinPublicJwk,
)

internal object SkinRequestCanonicalizer {
    fun canonical(method: String, encodedPathAndQuery: String, installationId: String, timestamp: String, nonce: String, bodySha256: String): String =
        "${method.uppercase()}\n$encodedPathAndQuery\n$installationId\n$timestamp\n$nonce\n${bodySha256.lowercase()}"

    fun sha256(bytes: ByteArray): String = MessageDigest.getInstance("SHA-256").digest(bytes).joinToString("") { "%02x".format(it) }
}

internal class SkinInstallationIdentity(context: Context) {
    private val appContext = context.applicationContext
    private val preferences = appContext.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
    val installationId: String by lazy {
        preferences.getString(INSTALLATION_ID, null)?.takeIf(::isUuid)
            ?: UUID.randomUUID().toString().also { preferences.edit().putString(INSTALLATION_ID, it).apply() }
    }

    fun publicJwk(): SkinPublicJwk {
        val publicKey = keyPair().public as ECPublicKey
        return SkinPublicJwk(
            x = base64Url(unsigned32(publicKey.w.affineX.toByteArray())),
            y = base64Url(unsigned32(publicKey.w.affineY.toByteArray())),
        )
    }

    fun sign(method: String, encodedPathAndQuery: String, bodySha256: String): SignedHeaders {
        val timestamp = System.currentTimeMillis().toString()
        val nonce = base64Url(ByteArray(24).also(SecureRandom()::nextBytes))
        val canonical = SkinRequestCanonicalizer.canonical(method, encodedPathAndQuery, installationId, timestamp, nonce, bodySha256)
        val signer = Signature.getInstance("SHA256withECDSA")
        signer.initSign(keyPair().private)
        signer.update(canonical.toByteArray(StandardCharsets.UTF_8))
        return SignedHeaders(installationId, timestamp, nonce, base64Url(signer.sign()))
    }

    fun snapshot(): IdentitySnapshot = IdentitySnapshot(
        installationId = installationId,
        publicKey = publicJwk(),
    )

    fun resetIdentity() {
        val previousInstallationId = installationId
        SkinPFPStore.clear(appContext, previousInstallationId)
        preferences.edit().remove(INSTALLATION_ID).apply()
        val store = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }
        store.deleteEntry(KEY_ALIAS)
    }

    private fun keyPair(): KeyPair {
        val store = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }
        val existing = store.getEntry(KEY_ALIAS, null) as? KeyStore.PrivateKeyEntry
        if (existing != null) return KeyPair(existing.certificate.publicKey, existing.privateKey)
        return KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_EC, "AndroidKeyStore").run {
            initialize(
                KeyGenParameterSpec.Builder(KEY_ALIAS, KeyProperties.PURPOSE_SIGN or KeyProperties.PURPOSE_VERIFY)
                    .setAlgorithmParameterSpec(ECGenParameterSpec("secp256r1"))
                    .setDigests(KeyProperties.DIGEST_SHA256)
                    .build(),
            )
            generateKeyPair()
        }
    }

    private fun unsigned32(source: ByteArray): ByteArray {
        val unsigned = source.dropWhile { it == 0.toByte() }.toByteArray()
        require(unsigned.size <= 32) { "Invalid P-256 coordinate" }
        return ByteArray(32 - unsigned.size) + unsigned
    }

    private fun base64Url(bytes: ByteArray): String = Base64.encodeToString(bytes, Base64.URL_SAFE or Base64.NO_WRAP or Base64.NO_PADDING)
    private fun isUuid(value: String) = runCatching { UUID.fromString(value).toString() == value.lowercase() }.getOrDefault(false)

    private companion object {
        const val KEY_ALIAS = "projectretr0.community.p256.v1"
        const val PREFERENCES = "community_identity_v1"
        const val INSTALLATION_ID = "installation_id"
    }
}
