package com.retr0.projectretr0

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.retr0.projectretr0.data.skins.local.LocalSkinEntity
import com.retr0.projectretr0.data.skins.local.SkinDatabase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SkinDatabaseTest {
    private val database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), SkinDatabase::class.java).allowMainThreadQueries().build()

    @After fun close() = database.close()

    @Test fun importedSkinRoundTrips() = runBlocking {
        val entity = LocalSkinEntity(
            id = "id",
            communityId = null,
            title = "Title",
            creator = "Creator",
            vehicle = "Vehicle",
            carDirectory = "car",
            slotKey = "ext_one",
            textureFormat = "etc(1)",
            sourceFilename = "skin.dds.z",
            binaryPath = "/private/skin.dds.z",
            binarySize = 3L,
            binarySha256 = "a".repeat(64),
            targetPath = "files/.depot/vehicles/car/livery/skin.dds.z",
            gamePackage = "row",
            importedAt = 1L,
        )
        database.skinDao().insertImported(entity, emptyList())
        assertEquals(entity, database.skinDao().skin("id"))
    }
}
