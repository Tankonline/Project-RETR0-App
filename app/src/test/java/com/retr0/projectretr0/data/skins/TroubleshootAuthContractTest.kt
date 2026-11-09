package com.retr0.projectretr0.data.skins

import org.junit.Assert.assertEquals
import org.junit.Test

class TroubleshootAuthContractTest {
    @Test fun troubleshootUsesProductionPathAndBodyBoundCanonicalization() {
        val bodyHash = SkinRequestCanonicalizer.sha256("{\"error\":\"x\",\"device\":\"d\",\"type\":\"troubleshoot\"}".toByteArray())
        assertEquals(
            "POST\n/troubleshoot\n11111111-1111-4111-8111-111111111111\n1700000000000\nabcdefghijklmnop\n$bodyHash",
            SkinRequestCanonicalizer.canonical(
                "post", "/troubleshoot", "11111111-1111-4111-8111-111111111111",
                "1700000000000", "abcdefghijklmnop", bodyHash.uppercase(),
            ),
        )
    }
}
