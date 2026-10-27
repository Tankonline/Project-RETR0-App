package com.retr0.projectretr0

import com.retr0.projectretr0.MainActivity.AppMode
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class NavStateCodecTest {
    @Test fun roundTripsModeAndBackHistory() {
        val returns = mapOf(AppMode.MUSIC_SLOTS to AppMode.MUSIC, AppMode.MUSIC to AppMode.DASHBOARD, AppMode.STORAGE to AppMode.SKIN_MODS)
        assertEquals(returns, NavStateCodec.decodeReturns(NavStateCodec.encodeReturns(returns)))
        assertEquals(AppMode.GRAPHICS, NavStateCodec.decodeMode(AppMode.GRAPHICS.name))
    }

    @Test fun emptyHistoryIsEmpty() {
        assertEquals("", NavStateCodec.encodeReturns(emptyMap()))
        assertEquals(emptyMap<AppMode, AppMode>(), NavStateCodec.decodeReturns(""))
        assertEquals(emptyMap<AppMode, AppMode>(), NavStateCodec.decodeReturns(null))
    }

    @Test fun unknownOrDamagedValuesAreDropped() {
        assertNull(NavStateCodec.decodeMode(null))
        assertNull(NavStateCodec.decodeMode("REMOVED_SCREEN"))
        assertEquals(
            mapOf(AppMode.SAVES to AppMode.DASHBOARD),
            NavStateCodec.decodeReturns("SAVES=DASHBOARD,GONE=DASHBOARD,MUSIC=,broken,A=B=C"),
        )
    }
}
