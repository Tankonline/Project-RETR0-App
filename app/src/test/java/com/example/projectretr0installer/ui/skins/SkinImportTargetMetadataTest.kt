package com.example.projectretr0installer.ui.skins

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class SkinImportTargetMetadataTest {
    @Test fun extractsMultiSegmentSlotFromWorkerCompatibleFilename() {
        val metadata = parseTargetMetadata("2014_audi_r18_livery_ext_one.etc(1).dds.z")
        assertEquals("ext_one", metadata.slotKey)
        assertEquals("etc(1)", metadata.textureFormat)
    }

    @Test fun extractsCompactSlotFromWorkerCompatibleFilename() {
        assertEquals("ext1", parseTargetMetadata("car_ext1.atc(2).dds.z").slotKey)
    }

    @Test fun rejectsFilenameWithoutSupportedSlotSuffix() {
        assertThrows(IllegalStateException::class.java) {
            parseTargetMetadata("car_livery.etc(1).dds.z")
        }
    }
}
