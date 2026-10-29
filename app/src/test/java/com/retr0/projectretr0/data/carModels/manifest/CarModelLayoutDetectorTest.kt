package com.retr0.projectretr0.data.carModels.manifest

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CarModelLayoutDetectorTest {
    @Test fun detectsWrapperAndEffectiveDepotContent() {
        val detection = CarModelLayoutDetector.detect(listOf("download/vehicles/custom_car/body.m3g", "download/vehicles/data/events.dat.nct"))
        assertEquals("download", detection.effectivePrefix)
        assertEquals("custom_car", detection.vehicleDirectory)
        assertTrue(detection.warnings.single().contains("Wrapper"))
    }

    @Test(expected = IllegalArgumentException::class)
    fun rejectsMultipleVehicleDirectories() {
        CarModelLayoutDetector.detect(listOf("vehicles/car_a/body.m3g", "vehicles/car_b/body.m3g"))
    }
}
