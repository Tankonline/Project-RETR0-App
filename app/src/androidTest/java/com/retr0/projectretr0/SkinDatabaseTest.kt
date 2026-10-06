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
        val entity = LocalSkinEntity("id", null, "Title", "Creator", "Vehicle", "car", "ext_one", "etc(1)", "skin.dds.z", "/private/skin.dds.z", 3, "a".repeat(64), "files/.depot/vehicles/car/livery/skin.dds.z", "row", 1)
        database.skinDao().insertImported(entity, emptyList())
        assertEquals(entity, database.skinDao().skin("id"))
    }
}
