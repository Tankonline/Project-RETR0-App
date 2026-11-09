package com.retr0.projectretr0.auth

import android.content.Context
import android.content.Intent
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

/**
 * Isolated, opt-in Firebase email-link authentication preparation.
 *
 * This class is deliberately not constructed by the active login flow. Set [enabled]
 * explicitly at an integration boundary before using it.
 */
class EmailLinkAuthManager(
    context: Context,
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val settings: EmailLinkSettings = EmailLinkSettings(),
    val enabled: Boolean = false,
) {
    private val appContext = context.applicationContext
    private val pendingEmailStore = PendingEmailStore(appContext)

    fun validateEmail(email: String): Boolean = EMAIL_REGEX.matches(email.trim())

    fun buildActionCodeSettings(): ActionCodeSettings = ActionCodeSettings.newBuilder()
        .setUrl(settings.continueUrl)
        .setHandleCodeInApp(true)
        .setAndroidPackageName(appContext.packageName, true, null)
        .build()

    suspend fun sendLink(email: String) {
        check(enabled) { "Email-link authentication is inactive" }
        val normalized = email.trim()
        require(validateEmail(normalized)) { "A valid email address is required" }
        auth.sendSignInLinkToEmail(normalized, buildActionCodeSettings()).await()
        pendingEmailStore.save(normalized)
    }

    fun isSignInLink(intent: Intent?): Boolean {
        val link = intent?.dataString ?: return false
        return enabled && auth.isSignInWithEmailLink(link)
    }

    suspend fun completeSignIn(intent: Intent, emailOverride: String? = null): FirebaseUser {
        check(enabled) { "Email-link authentication is inactive" }
        val link = intent.dataString.orEmpty()
        require(link.isNotBlank() && auth.isSignInWithEmailLink(link)) { "Malformed or unsupported email link" }
        val email = (emailOverride ?: pendingEmailStore.read()).orEmpty().trim()
        require(validateEmail(email)) { "A valid email is required to complete sign-in" }
        val result = auth.signInWithEmailLink(email, link).await()
        pendingEmailStore.clear()
        return requireNotNull(result.user)
    }

    suspend fun refreshToken(forceRefresh: Boolean = true): String? =
        auth.currentUser?.getIdToken(forceRefresh)?.await()?.token

    suspend fun adminClaimStatus(): AdminClaimStatus {
        val user = auth.currentUser ?: return AdminClaimStatus.SignedOut
        val claims = user.getIdToken(false).await()?.claims.orEmpty()
        return AdminClaimStatus.SignedIn(isAdmin = claims["admin"] == true || claims["role"] == "admin")
    }

    fun signOut() { auth.signOut(); pendingEmailStore.clear() }

    suspend fun linkGoogleCredential(credential: AuthCredential): LinkResult {
        val user = auth.currentUser ?: return LinkResult.SignedOut
        return try {
            user.linkWithCredential(credential).await()
            LinkResult.Linked
        } catch (error: Exception) {
            if (error is FirebaseAuthRecentLoginRequiredException) LinkResult.ReauthenticationRequired
            else LinkResult.Failed(error)
        }
    }

    suspend fun linkGoogleIdToken(idToken: String): LinkResult =
        linkGoogleCredential(GoogleAuthProvider.getCredential(idToken, null))

    data class EmailLinkSettings(
        val continueUrl: String = "https://auth.projectretr0.web.app/emailLink",
    )

    sealed interface AdminClaimStatus {
        data object SignedOut : AdminClaimStatus
        data class SignedIn(val isAdmin: Boolean) : AdminClaimStatus
    }

    sealed interface LinkResult {
        data object SignedOut : LinkResult
        data object Linked : LinkResult
        data object ReauthenticationRequired : LinkResult
        data class Failed(val error: Exception) : LinkResult
    }

    private companion object {
        val EMAIL_REGEX = Regex("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")
    }
}

internal class PendingEmailStore(context: Context) {
    private val preferences = EncryptedSharedPreferences.create(
        context,
        "inactive_email_link_auth",
        MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build(),
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
    )

    fun save(email: String) { preferences.edit().putString(KEY, email).apply() }
    fun read(): String? = preferences.getString(KEY, null)
    fun clear() { preferences.edit().remove(KEY).apply() }

    private companion object { const val KEY = "pending_email" }
}

/** Safe isolated forwarding activity. It does not complete links while the manager is disabled. */
class EmailLinkHandlerActivity : android.app.Activity() {
    override fun onCreate(state: android.os.Bundle?) {
        super.onCreate(state)
        setResult(android.app.Activity.RESULT_CANCELED, intent)
        finish()
    }
}
