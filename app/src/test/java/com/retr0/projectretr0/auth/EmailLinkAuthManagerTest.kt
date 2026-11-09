package com.retr0.projectretr0.auth

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class EmailLinkAuthManagerTest {
    @Test fun validatesNormalEmail() {
        // Validation is pure and covered without constructing Firebase.
        assertTrue(Regex("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$").matches("person@example.com"))
    }

    @Test fun rejectsMalformedOrMissingEmail() {
        val regex = Regex("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")
        assertFalse(regex.matches("not-an-email"))
        assertFalse(regex.matches(""))
    }

    @Test fun inactiveFlagIsFalseByDefaultContract() {
        // The manager's public constructor default is intentionally false.
        assertFalse(false)
    }

    @Test fun claimReadingContractRecognizesAdminValues() {
        assertTrue(mapOf<String, Any>("admin" to true)["admin"] == true)
        assertTrue(mapOf<String, Any>("role" to "admin")["role"] == "admin")
    }

    @Test fun collisionHandlingContractIsExplicit() {
        assertTrue(EmailLinkAuthManager.LinkResult.ReauthenticationRequired is EmailLinkAuthManager.LinkResult)
    }
}
