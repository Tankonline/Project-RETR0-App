package com.retr0.projectretr0.installer

import com.retr0.projectretr0.data.skins.SaveApiClient
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CommunityHubErrorsTest {
    private val id = "3f2a9c4e-1b7d-4e8a-9c3f-5d6e7f8a9b0c"

    @Test fun traversalNamesBecomeASinglePathSegment() {
        for (hostile in listOf(
            "../../../Android/data/com.ea.games.r3_row/files/x",
            "..\\..\\evil",
            "a/../../b",
            "/absolute/path",
            "....//....//x",
        )) {
            val name = CommunitySaveFileNames.baseName(hostile, id)
            assertFalse("separator in '$name'", name.contains('/') || name.contains('\\'))
            assertFalse("dot-run in '$name'", name.contains(".."))
            assertFalse("hidden file '$name'", name.startsWith("."))
        }
    }

    @Test fun ordinaryNamesArePreserved() {
        assertEquals("My Save (v2)", CommunitySaveFileNames.baseName("  My Save (v2)  ", id))
        assertEquals("Ünïcødé_セーブ", CommunitySaveFileNames.baseName("Ünïcødé_セーブ", id))
        assertEquals("trailing", CommunitySaveFileNames.baseName("trailing.dat", id))
    }

    @Test fun controlCharactersAndReservedSymbolsAreReplaced() {
        assertEquals("a_b_c_d", CommunitySaveFileNames.baseName("a:b*c\u0000d", id))
    }

    @Test fun unusableNamesFallBackToTheSaveId() {
        for (empty in listOf("", "   ", "..", "///", "...")) {
            assertEquals("community_save_$id", CommunitySaveFileNames.baseName(empty, id))
        }
    }

    @Test fun namesAreBoundedTo80Characters() {
        assertTrue(CommunitySaveFileNames.baseName("x".repeat(500), id).length <= 80)
    }

    @Test fun existingBackupsAreNeverOverwritten() {
        val existing = setOf("Garage.dat", "Garage (2).dat")
        assertEquals("Garage (3).dat", CommunitySaveFileNames.uniqueFileName("Garage") { it in existing })
        assertEquals("Fresh.dat", CommunitySaveFileNames.uniqueFileName("Fresh") { false })
    }

    @Test fun aiRegistrationRetryIsBoundedToOneAttempt() {
        assertTrue(CommunityHubErrors.shouldRegisterAndRetryAi(401, "unknown_installation", allowRetry = true))
        assertFalse(CommunityHubErrors.shouldRegisterAndRetryAi(401, "unknown_installation", allowRetry = false))
        assertFalse(CommunityHubErrors.shouldRegisterAndRetryAi(401, "invalid_signature", allowRetry = true))
        assertFalse(CommunityHubErrors.shouldRegisterAndRetryAi(409, "unknown_installation", allowRetry = true))
    }

    @Test fun downloadHintsFollowTheServerCode() {
        assertTrue(CommunityHubErrors.saveDownloadHint(401, "stale_request").contains("date/time"))
        assertTrue(CommunityHubErrors.saveDownloadHint(401, "invalid_signature").contains("Reset identity"))
        assertTrue(CommunityHubErrors.saveDownloadHint(409, "installation_exists").contains("Reset identity"))
        assertTrue(CommunityHubErrors.saveDownloadHint(404, "save_asset_not_found").contains("deleted"))
        assertTrue(CommunityHubErrors.saveDownloadHint(null, SaveApiClient.DOWNLOAD_NETWORK_ERROR).contains("internet"))
        assertTrue(CommunityHubErrors.saveDownloadHint(200, SaveApiClient.DOWNLOAD_TOO_LARGE).contains("25 MiB"))
        assertTrue(CommunityHubErrors.saveDownloadHint(503, null).contains("Try again later"))
    }

    @Test fun aiFailureMessagesAreSpecific() {
        assertTrue(CommunityHubErrors.aiFailureMessage("HTTP_ERROR", 401, "stale_request").contains("clock"))
        assertTrue(CommunityHubErrors.aiFailureMessage("HTTP_ERROR", 401, "unknown_installation").contains("Reset identity"))
        assertTrue(CommunityHubErrors.aiFailureMessage("HTTP_ERROR", 409, "replayed_request").contains("duplicate"))
        assertTrue(CommunityHubErrors.aiFailureMessage("HTTP_ERROR", 503, "service_unavailable").contains("temporarily unavailable"))
        assertTrue(CommunityHubErrors.aiFailureMessage("TIMEOUT", null, null).contains("too long"))
        assertTrue(CommunityHubErrors.aiFailureMessage("NETWORK", null, null).contains("internet"))
        assertTrue(CommunityHubErrors.aiFailureMessage("INVALID_RESPONSE", 200, null).contains("unreadable"))
    }
}
