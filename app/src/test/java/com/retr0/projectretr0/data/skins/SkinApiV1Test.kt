package com.retr0.projectretr0.data.skins

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class SkinApiV1Test {
    @Test fun canonicalRequestExactlyMatchesWorkerContract() {
        assertEquals(
            "PUT\n/v1/skins/123/binary?caption=front%20view\n11111111-1111-4111-8111-111111111111\n1700000000000\nabcdefghijklmnop\nabc123",
            SkinRequestCanonicalizer.canonical(
                "put", "/v1/skins/123/binary?caption=front%20view", "11111111-1111-4111-8111-111111111111",
                "1700000000000", "abcdefghijklmnop", "ABC123",
            ),
        )
    }

    @Test fun parsesV1PositionCaptionAndPaging() {
        val response = SkinApiParser.parseList("""{"items":[{"id":"1","title":"T","creatorName":"C","carDirectory":"car","slotKey":"ext","textureFormat":"etc","binarySize":1,"screenshots":[{"position":2,"caption":"rear","url":"https://example.com/2"},{"position":1,"url":"https://example.com/1"}]}],"limit":10,"offset":20}""")
        assertEquals(10, response.limit)
        assertEquals(20, response.offset)
        assertEquals(listOf(1, 2), response.items.single().screenshots.map { it.order })
        assertEquals("rear", response.items.single().screenshots.last().caption)
    }

    @Test fun parsesStructuredDuplicateError() {
        val error = SkinApiParser.parseError("""{"error":{"code":"duplicate_skin","message":"exists","details":{"duplicateId":"duplicate-1"}},"requestId":"request-1"}""")
        assertEquals("duplicate_skin", error?.code)
        assertEquals("duplicate-1", error?.duplicateId)
        assertEquals("request-1", error?.requestId)
    }

    @Test fun invalidErrorBodyIsSafelyIgnored() {
        assertNull(SkinApiParser.parseError("not json"))
    }
}
