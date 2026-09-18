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
        assertEquals(SmartScanResult.NotFound, smartMatch(source, root, name, SmartScanLimits(directories = 2)))
        assertEquals(3, calls.size)
    }

    @Test fun smartMatchUsesValidatorAsGate() = runBlocking {
        val vehicle = SkinBrowserEntry("car", "$root/files/.depot/vehicles/car", true, false, false, 0)
        val invalid = SkinBrowserEntry(name, "$root/files/$name", false, true, false, 1)
        val source = SkinTargetBrowserSource { _, path -> if(path.endsWith("vehicles")) BrowserResult.Success(listOf(vehicle)) else BrowserResult.Success(listOf(invalid)) }
        assertEquals(SmartScanResult.NotFound, smartMatch(source, root, name))
    }

    @Test fun validationMessageIsActionable() {
        val entry = SkinBrowserEntry("other.dds.z", "$root/files/.depot/vehicles/car/livery/other.dds.z", false, true, false, 1)
        val error = SkinTargetValidator.validate(root, name, entry).exceptionOrNull()
        assertEquals(ValidationSeverity.WARNING, validationPresentation(error, name).severity)
        assertTrue(validationPresentation(error, name).message.contains(name))
    }
}
