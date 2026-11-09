package com.retr0.projectretr0.data.skins

import org.json.JSONObject
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class AiDiagnosticsContractTest {
    @Test fun responseMetadataContractContainsOnlyAllowedFields() {
        val response = JSONObject("""
            {"rag_used":true,"research_used":false,"confidence":"medium","sources":["https://example.test/a"],"follow_up_question":null}
        """.trimIndent())
        assertTrue(response.getBoolean("rag_used"))
        assertFalse(response.getBoolean("research_used"))
        assertTrue(response.has("confidence"))
        assertTrue(response.getJSONArray("sources").length() == 1)
        assertTrue(response.has("follow_up_question"))
        assertFalse(response.has("answer_body"))
        assertFalse(response.has("prompt"))
        assertFalse(response.has("installation_id"))
        assertFalse(response.has("nonce"))
        assertFalse(response.has("signature"))
    }
}
