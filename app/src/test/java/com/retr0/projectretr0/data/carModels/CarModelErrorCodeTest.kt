package com.retr0.projectretr0.data.carModels

import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import org.junit.Assert.assertTrue
import org.junit.Test

class CarModelErrorCodeTest {
    private val format = Regex("CM-(APPLY|BACKUP|REVERT|TARGET|IMPORT|HUB|FILEOP|ADDON)-\\d{3}")

    @Test fun codesAreUniqueAndFollowTheScheme() {
        val codes = CarModelErrorCode.entries.map { it.code }
        assertEquals("duplicate error codes", codes.size, codes.toSet().size)
        CarModelErrorCode.entries.forEach { assertTrue("${it.name} has a malformed code ${it.code}", format.matches(it.code)) }
    }

    @Test fun everyCodeHasAUserMessage() {
        CarModelErrorCode.entries.forEach { assertTrue("${it.name} has a blank message", it.userMessage.isNotBlank()) }
    }

    /** Codes are stable: these assignments must never change, only be appended to. */
    @Test fun catalogAssignmentsAreStable() {
        val expected = mapOf(
            CarModelErrorCode.BACKUP_REQUIRED to "CM-APPLY-001", CarModelErrorCode.BACKUP_NOT_VERIFIED to "CM-APPLY-002",
            CarModelErrorCode.MODEL_NOT_FOUND to "CM-APPLY-003", CarModelErrorCode.MODEL_EMPTY to "CM-APPLY-004",
            CarModelErrorCode.LIBRARY_FILE_CHANGED to "CM-APPLY-005", CarModelErrorCode.APPLY_COPY_FAILED to "CM-APPLY-006",
            CarModelErrorCode.APPLY_HASH_MISMATCH to "CM-APPLY-007", CarModelErrorCode.GAME_RUNNING to "CM-APPLY-008",
            CarModelErrorCode.TARGET_NOT_FOUND to "CM-TARGET-001",
            CarModelErrorCode.BACKUP_MISSING to "CM-BACKUP-001", CarModelErrorCode.BACKUP_CORRUPT to "CM-BACKUP-002",
            CarModelErrorCode.BACKUP_GONE_WHILE_APPLIED to "CM-BACKUP-003", CarModelErrorCode.BACKUP_TARGET_EMPTY to "CM-BACKUP-004",
            CarModelErrorCode.NO_BACKUP to "CM-BACKUP-005", CarModelErrorCode.BACKUP_LOCATION_UNRECOGNISED to "CM-BACKUP-006",
            CarModelErrorCode.BACKUP_DELETE_FAILED to "CM-BACKUP-007",
            CarModelErrorCode.RESTORE_COPY_FAILED to "CM-REVERT-001", CarModelErrorCode.RESTORE_HASH_MISMATCH to "CM-REVERT-002",
            CarModelErrorCode.RESTORE_REMOVE_FAILED to "CM-REVERT-003",
            CarModelErrorCode.HUB_UNAVAILABLE to "CM-HUB-001",
        )
        expected.forEach { (entry, code) -> assertEquals(code, entry.code) }
    }

    @Test fun uncodedFailuresKeepTheirMessageAndGetTheFallbackCode() {
        val coded = CarModelException(CarModelErrorCode.GAME_RUNNING)
        assertSame(coded, coded.asCarModelException())
        val wrapped = IllegalStateException("binder died").asCarModelException()
        assertEquals(CarModelErrorCode.UNEXPECTED, wrapped.error)
        assertEquals("binder died", wrapped.message)
        assertEquals(CarModelErrorCode.HUB_UNAVAILABLE, CarModelHubUnavailableException().error)
    }
}
