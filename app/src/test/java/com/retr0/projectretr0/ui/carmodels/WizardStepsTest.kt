package com.retr0.projectretr0.ui.carmodels

import com.retr0.projectretr0.data.carModels.local.AddonInspection
import com.retr0.projectretr0.data.carModels.local.InstallMode
import com.retr0.projectretr0.data.carModels.local.InstalledCarModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class WizardStepsTest {
    @Test fun anInstallTypeMustBeChosenBeforeImporting() {
        assertNull(WizardSteps.afterMode(null, applyingExistingModel = false))
        assertNull(WizardSteps.afterMode(null, applyingExistingModel = true))
        assertEquals(WizardSteps.FOLDER, WizardSteps.afterMode(InstallMode.REPLACE, applyingExistingModel = false))
        assertEquals(WizardSteps.FOLDER, WizardSteps.afterMode(InstallMode.ADDON, applyingExistingModel = false))
        assertEquals(WizardSteps.TARGET, WizardSteps.afterMode(InstallMode.ADDON, applyingExistingModel = true))
        assertEquals("install type is the first step", 1, WizardSteps.MODE)
    }

    @Test fun replaceNeedsAVerifiedBackupWhileAddonDoesNot() {
        val free = AddonInspection("/x", false, listOf("a.m3g"), emptyList(), null)
        assertFalse(WizardSteps.canApply(null, backupVerified = true, addon = free))
        assertFalse(WizardSteps.canApply(InstallMode.REPLACE, backupVerified = false, addon = free))
        assertTrue(WizardSteps.canApply(InstallMode.REPLACE, backupVerified = true, addon = null))
        assertTrue(WizardSteps.canApply(InstallMode.ADDON, backupVerified = false, addon = free))
        assertFalse(WizardSteps.canApply(InstallMode.ADDON, backupVerified = false, addon = null))
        val occupied = free.copy(occupiedBy = InstalledCarModel("m", "M", "p", "d", 0, true, 1, 1))
        assertFalse(WizardSteps.canApply(InstallMode.ADDON, backupVerified = true, addon = occupied))
    }

    @Test fun addonFolderSuggestionsAreSafeDirectoryNames() {
        assertEquals("BMW_M3_GTR_v2", WizardSteps.suggestAddonFolder(" BMW M3 GTR (v2) "))
        assertEquals("", WizardSteps.suggestAddonFolder(null))
        assertEquals("a-b_c", WizardSteps.suggestAddonFolder("a-b_c"))
    }
}
