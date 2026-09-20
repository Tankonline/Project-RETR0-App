package com.example.projectretr0installer.ui.skins

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class SkinTargetSmartMatchTest {
    private val root = SkinTargetValidator.ROW_ROOT
    private val name = FakeSkinMods.AUDI_TARGET

    @Test fun breadcrumbsNeverIncludeParentOfPackageRoot() {
        val crumbs = packageBreadcrumbs(root, "$root/files/.depot/vehicles")
        assertEquals(root, crumbs.first().second)
        assertTrue(crumbs.all { it.second.startsWith(root) })
    }

    @Test fun smartMatchIsSequentialAndHonorsVehicleCap() = runBlocking {
        val calls = mutableListOf<String>()
        val source = SkinTargetBrowserSource { _, path ->
            calls += path
            when(path) {
                "$root/files/.depot/vehicles" -> BrowserResult.Success((1..5).map { SkinBrowserEntry("car$it", "$root/files/.depot/vehicles/car$it", true, false, false, 0) })
                else -> BrowserResult.Success(emptyList())
            }
        }
        assertTrue(smartMatch(source, root, name, SmartScanLimits(directories = 2)) is SmartScanResult.Error)
        assertEquals(3, calls.size)
    }

    @Test fun smartMatchSurfacesSafeRelatedCandidateBeforeFinalValidation() = runBlocking {
        val vehicle = SkinBrowserEntry("car", "$root/files/.depot/vehicles/car", true, false, false, 0)
        val related = SkinBrowserEntry(
            "2014_audi_r18etronquattro_ext_two.etc.dds.z",
            "$root/files/.depot/vehicles/car/livery/2014_audi_r18etronquattro_ext_two.etc.dds.z",
            false,
            true,
            false,
            1,
        )
        val source = SkinTargetBrowserSource { _, path ->
            if (path.endsWith("vehicles")) BrowserResult.Success(listOf(vehicle))
            else BrowserResult.Success(listOf(related))
        }
        val result = smartMatch(source, root, name)
        assertTrue(result is SmartScanResult.Found || result is SmartScanResult.Candidates)
        val candidate = when (result) {
            is SmartScanResult.Found -> result.candidate
            is SmartScanResult.Candidates -> result.entries.single()
            else -> error("Expected a safe related candidate")
        }
        assertEquals(SmartMatchCategory.RELATED, candidate.category)
        assertTrue(SkinTargetValidator.validate(root, name, candidate.entry).isFailure)
    }

    @Test fun unsafeCandidateIsExcludedBeforeRanking() = runBlocking {
        val vehicle = SkinBrowserEntry("car", "$root/files/.depot/vehicles/car", true, false, false, 0)
        val outside = SkinBrowserEntry(name, "/storage/emulated/0/Download/$name", false, true, false, 1)
        val source = SkinTargetBrowserSource { _, path ->
            if (path.endsWith("vehicles")) BrowserResult.Success(listOf(vehicle))
            else BrowserResult.Success(listOf(outside))
        }
        assertTrue(smartMatch(source, root, name) is SmartScanResult.Error)
    }

    @Test fun bulkRecordParserAcceptsActualAndEscapedTabs() {
        val actual = "F\t123\t/storage/emulated/0/Android/data/com.ea.games.r3_row/files/.depot/vehicles/car/livery/a.dds.z"
        val escaped = "F\\t123\\t/storage/emulated/0/Android/data/com.ea.games.r3_row/files/.depot/vehicles/car/livery/a.dds.z"
        assertEquals(3, actual.split('\t', limit = 3).size)
        assertEquals(3, escaped.replace("\\t", "\t").split('\t', limit = 3).size)
    }

    @Test fun filenameProfileExtractsSearchableVehicleDimensions() {
        val profile = SkinTargetValidator.filenameProfile("2014_audi_r18etronquattro_ext_one.etc.dds.z")
        assertEquals("2014", profile.year)
        assertEquals("audi", profile.manufacturer)
        assertEquals("r18etronquattro", profile.vehicle)
        assertEquals("one", profile.slot)
        assertEquals("etc", profile.format)
        assertTrue("audi" in profile.tokens)
        assertTrue("r18etronquattro" in profile.tokens)
    }

    @Test fun rankingPrefersSameVehicleSlotAndFormat() {
        val imported = "2014_audi_r18etronquattro_ext_one.etc.dds.z"
        val entries = listOf(
            SkinBrowserEntry("2014_audi_r18etronquattro_ext_two.etc.dds.z", "/safe/two", false, true, false, 1),
            SkinBrowserEntry("2014_audi_r18etronquattro_ext_one.atc.dds.z", "/safe/atc", false, true, false, 1),
            SkinBrowserEntry("2014_audi_r18etronquattro_ext_one.etc.dds.z", "/safe/exact", false, true, false, 1),
        )
        val ranked = rankSmartCandidates(imported, entries, SmartScanLimits())
        assertEquals("2014_audi_r18etronquattro_ext_one.etc.dds.z", ranked.first().entry.name)
        assertEquals(SmartMatchCategory.EXACT, ranked.first().category)
    }

    @Test fun cacheExpiresAndRetryInvalidates() {
        var now = 0L
        val cache = SmartScanCache(ttlMillis = 10, clock = { now })
        val key = SmartScanCacheKey(root, name.lowercase())
        cache.put(key, SmartScanResult.NotFound)
        assertEquals(SmartScanResult.NotFound, cache.get(key))
        now = 11
        assertNull(cache.get(key))
        cache.put(key, SmartScanResult.NotFound)
        cache.invalidate(key)
        assertNull(cache.get(key))
    }

    @Test fun diagnosticCodesRemainVisibleAndDistinct() {
        assertNotEquals(SmartMatchErrorCode.ZERO_CANDIDATES, SmartMatchErrorCode.SERVICE_UNAVAILABLE)
        assertTrue(SmartMatchDiagnostic(SmartMatchErrorCode.SERVICE_UNAVAILABLE, "AIDL_SERVICE_UNAVAILABLE").message.contains("SERVICE_UNAVAILABLE"))
        assertTrue(SmartMatchDiagnostic(SmartMatchErrorCode.VALIDATION_REJECTED, "FINAL_VALIDATION").message.contains("VALIDATION_REJECTED"))
    }

    @Test fun validationMessageIsActionable() {
        val entry = SkinBrowserEntry("other.dds.z", "$root/files/.depot/vehicles/car/livery/other.dds.z", false, true, false, 1)
        val error = SkinTargetValidator.validate(root, name, entry).exceptionOrNull()
        assertEquals(ValidationSeverity.WARNING, validationPresentation(error, name).severity)
        assertTrue(validationPresentation(error, name).message.contains(name))
    }
}
