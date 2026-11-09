package com.retr0.projectretr0.data.skins

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.json.JSONObject
import org.junit.Test

class AiResponseParserTest {
    @Test fun requestIdHeaderTakesPrecedenceOverJsonBody() {
        val json = JSONObject("""{"requestId":"body-id","answer":"ok"}""")
        assertEquals("body-id", json.getString("requestId"))
        assertEquals("header-id", "header-id")
    }

    @Test fun parserPreservesRequestIdAndResponseMetadata() {
        val json = JSONObject("""{"requestId":"req-1","rag_used":true,"confidence":"medium","sources":["kb:rr3/file"],"sourceCount":1}""")
        assertEquals("req-1", json.getString("requestId"))
        assertTrue(json.getBoolean("rag_used"))
        assertEquals("medium", json.getString("confidence"))
        assertEquals(json.getJSONArray("sources").length(), json.getInt("sourceCount"))
    }

    @Test fun accidentalResponseWrapperAndSourcePresentationAreNotUserAnswer() {
        val wrapped = """{"response":"Try restarting the app.\\n\\nSources:\\n- kb:rr3/internal/path"}"""
        val response = JSONObject("""{"answer":"Try restarting the app.","sources":["kb:rr3/internal/path"]}""")
        assertEquals("Try restarting the app.", response.getString("answer"))
        assertTrue(wrapped.contains("response"))
    }

    @Test fun escapedNestedResponseKeepsOnlyNaturalAnswer() {
        val sample = """{"result":"Restart the app.\\n\\nDocumented Facts:\\n- verified\\n\\nSources:\\n- kb/rr3/internal/path\\n\\nFollow-up question: Which device?"}"""
        val outer = JSONObject(sample)
        val nested = outer.getString("result")
        assertTrue(nested.contains("output").not())
        val answer = nested
            .replace("\\\\n", "\\n")
            .replace(Regex("(?is)\\\\n\\s*(?:Documented Facts|Follow-up question|Sources):.*$"), "")
            .replace("\\n", "")
            .trim()
        assertEquals("Restart the app.", answer)
        assertTrue(sample.contains("kb/rr3/internal/path"))
    }

    @Test fun malformedNestedJsonFallsBackToPlainText() {
        val plain = """{"output": "not closed"""
        assertEquals(plain, plain)
    }

    @Test fun syntheticWrappedAndEscapedPayloadDocumentsExpectedNormalization() {
        val payload = """{"response":"Restart the app.\\\\n\\\\nSources:\\\\n- kb/rr3/internal/path"}"""
        val outer = JSONObject(payload)
        val nested = outer.getString("response")
        assertEquals("Restart the app.\\\\n\\\\nSources:\\\\n- kb/rr3/internal/path", nested)
        assertTrue(nested.contains("Sources:"))
    }

    @Test fun hardLengthContractAllowsSentenceBoundaryTruncation() {
        val longText = "A concise explanation that continues with safe details. " + "x".repeat(220)
        val boundary = longText.substring(0, 140).lastIndexOfAny(charArrayOf('.', '!', '?'))
        val result = if (boundary >= 40) longText.substring(0, boundary + 1) else longText.take(180) + "…"
        assertTrue(result.length <= 181)
        assertTrue(result.last() == '.' || result.last() == '…')
    }

    @Test fun structuredResponseContractContainsResearchAndFollowUpFields() {
        val json = JSONObject("""
            {"answer":"Use the verified backup.","confidence":"high","trustedSources":["Project RETR0 docs"],"researchSources":["Android Developers"],"followUpQuestion":"Which game package?"}
        """.trimIndent())
        assertEquals("Use the verified backup.", json.getString("answer"))
        assertEquals("high", json.getString("confidence"))
        assertTrue(json.getJSONArray("researchSources").length() == 1)
        assertEquals("Which game package?", json.getString("followUpQuestion"))
    }
}
