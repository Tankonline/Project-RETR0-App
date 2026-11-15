package com.retr0.projectretr0.installer

import com.retr0.projectretr0.data.carModels.CarModelErrorCode
import com.retr0.projectretr0.data.carModels.CarModelException
import com.retr0.projectretr0.data.carModels.asCarModelException
import com.retr0.projectretr0.data.carModels.isBinderFailure
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertSame
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.IOException

/** Reconnect / binder-identity rules used by MainActivity, and stable mapping of binder failures. */
class ShizukuConnectionPolicyTest {
    private val ready = ShizukuConnectionPolicy.State(
        shizukuBinderAlive = true, permissionGranted = true, shizukuPreV11 = false,
        serviceBound = false, bindInProgress = false, retryScheduled = false,
    )
    private fun reconnect(state: ShizukuConnectionPolicy.State, now: Long = 100_000, last: Long = 0) =
        ShizukuConnectionPolicy.shouldAutoReconnect(state, now, last, intervalMs = 15_000)

    @Test fun reconnectsWhenShizukuIsUpAuthorizedAndNothingIsBound() = assertTrue(reconnect(ready))

    @Test fun neverBindsWhileShizukuIsUnavailable() = assertFalse(reconnect(ready.copy(shizukuBinderAlive = false)))

    @Test fun neverBindsWithoutPermission() = assertFalse(reconnect(ready.copy(permissionGranted = false)))

    @Test fun unsupportedShizukuVersionIsNotRetried() = assertFalse(reconnect(ready.copy(shizukuPreV11 = true)))

    @Test fun repeatedInitializationDoesNotCreateASecondBinding() {
        assertFalse(reconnect(ready.copy(serviceBound = true)))
        assertFalse(reconnect(ready.copy(bindInProgress = true)))
        assertFalse(reconnect(ready.copy(retryScheduled = true)))
    }

    @Test fun reconnectAttemptsAreRateLimited() {
        assertFalse(reconnect(ready, now = 20_000, last = 10_000))
        assertTrue(reconnect(ready, now = 25_000, last = 10_000))
    }

    @Test fun onlyTheCurrentBinderDeathTearsDownTheConnection() {
        val current = Any()
        val replaced = Any()
        assertTrue(ShizukuConnectionPolicy.isCurrentBinder(current, current))
        assertFalse("a helper replaced on purpose must not disconnect its successor", ShizukuConnectionPolicy.isCurrentBinder(current, replaced))
        assertFalse("nothing bound: nothing to tear down", ShizukuConnectionPolicy.isCurrentBinder(null, replaced))
    }

    @Test fun binderFailuresMapToServiceUnavailable() {
        val dead = android.os.DeadObjectException()
        assertTrue(dead.isBinderFailure())
        assertEquals(CarModelErrorCode.SERVICE_UNAVAILABLE, dead.asCarModelException(CarModelErrorCode.ADDON_COPY_FAILED).error)
        // Wrapped in the cause chain (e.g. rethrown by a helper layer).
        val wrapped = IllegalStateException("copy failed", android.os.RemoteException())
        assertEquals(CarModelErrorCode.SERVICE_UNAVAILABLE, wrapped.asCarModelException(CarModelErrorCode.UNEXPECTED).error)
    }

    @Test fun nonBinderFailuresKeepTheirFallbackAndCodedFailuresKeepTheirCode() {
        assertEquals(CarModelErrorCode.UNEXPECTED, IOException("disk").asCarModelException().error)
        val coded = CarModelException(CarModelErrorCode.UNSAFE_PATH, "bad", android.os.DeadObjectException())
        assertSame(coded, coded.asCarModelException())
    }
}
