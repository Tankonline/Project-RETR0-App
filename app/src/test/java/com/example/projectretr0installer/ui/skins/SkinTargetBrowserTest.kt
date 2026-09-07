package com.example.projectretr0installer.ui.skins

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SkinTargetBrowserTest {
    private val name = "2014_audi_r18_livery_etc(1).dds.z"
    private fun entry(root: String = SkinTargetValidator.ROW_ROOT, file: String = name, regular: Boolean = true, symlink: Boolean = false) = SkinBrowserEntry(file, "$root/files/.depot/vehicles/audi_r18/livery/$file", false, regular, symlink, 10)

    @Test fun acceptsExactRowTarget() { assertTrue(SkinTargetValidator.validate(SkinTargetValidator.ROW_ROOT, name, entry()).isSuccess) }
    @Test fun acceptsExactNaTarget() { assertTrue(SkinTargetValidator.validate(SkinTargetValidator.NA_ROOT, name, entry(SkinTargetValidator.NA_ROOT)).isSuccess) }
    @Test fun rejectsTraversalAndSiblingPrefix() {
        assertTrue(SkinTargetValidator.validate(SkinTargetValidator.ROW_ROOT, name, entry().copy(path = SkinTargetValidator.ROW_ROOT + "/../com.ea.games.r3_na/files/.depot/vehicles/car/livery/$name")).isFailure)
        assertTrue(SkinTargetValidator.validate(SkinTargetValidator.ROW_ROOT, name, entry().copy(path = SkinTargetValidator.ROW_ROOT + "_evil/files/.depot/vehicles/car/livery/$name")).isFailure)
    }
    @Test fun rejectsWrongLocationFolderSymlinkExtensionAndName() {
        assertTrue(SkinTargetValidator.validate(SkinTargetValidator.ROW_ROOT, name, entry().copy(path = SkinTargetValidator.ROW_ROOT + "/files/$name")).isFailure)
        assertTrue(SkinTargetValidator.validate(SkinTargetValidator.ROW_ROOT, name, entry().copy(isDirectory = true, isRegularFile = false)).isFailure)
        assertTrue(SkinTargetValidator.validate(SkinTargetValidator.ROW_ROOT, name, entry(symlink = true)).isFailure)
        assertTrue(SkinTargetValidator.validate(SkinTargetValidator.ROW_ROOT, name, entry(file = "bad.png")).isFailure)
        assertTrue(SkinTargetValidator.validate(SkinTargetValidator.ROW_ROOT, name, entry(file = "other.dds.z")).isFailure)
    }
    @Test fun atcOnlyMatchesAtcExactly() {
        val atc = "car_livery_atc(1).dds.z"
        assertTrue(SkinTargetValidator.validate(SkinTargetValidator.ROW_ROOT, atc, entry(file = atc)).isSuccess)
        assertTrue(SkinTargetValidator.validate(SkinTargetValidator.ROW_ROOT, atc, entry()).isFailure)
        assertEquals("atc(1)", SkinTargetValidator.textureFormat(atc))
    }
}
