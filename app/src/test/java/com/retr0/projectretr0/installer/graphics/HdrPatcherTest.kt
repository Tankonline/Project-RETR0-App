package com.retr0.projectretr0.installer.graphics

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class HdrPatcherTest {
    private val sample = listOf(
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>",
        "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">",
        "<plist version=\"1.0\">",
        "<dict>",
        "\t<key>ENV_MAP_HDR</key>",
        "\t<false/>",
        "\t<key>GAMMA_CORRECTION</key>",
        "\t<false/>",
        "\t<key>PLIST_IDENTIFIER</key>",
        "\t<string>AndroidHigh</string>",
        "\t<key>TONEMAP</key>",
        "\t<false/>",
        "\t<key>USE_HDR_AUTOMATIC_EXPOSURE</key>",
        "\t<false/>",
        "\t<key>USE_HDR_POST_PROCESS</key>",
        "\t<false/>",
        "\t<key>USE_PBR_WATER</key>",
        "\t<false/>",
        "</dict>",
        "</plist>",
        "",
    ).joinToString("\r\n")

    @Test fun keystreamDecodesRealRr3Bytes() {
        // First 64 bytes of the factory RR3 14.0.1 AndroidLow.plist.
        val hex = "e6ea60c54026f388a8a65a62e99576bfdbe5de278565ae74d6f4750b77ff2033836ea84d062355f83f7b15d3d3a16f0a3383423b999a5b58714690b27659b9ce"
        val bytes = hex.chunked(2).map { it.toInt(16).toByte() }.toByteArray()
        val plain = String(PlistCipher.transform(bytes)!!, Charsets.UTF_8)
        assertTrue(plain, plain.startsWith("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<!DOCTYPE plist"))
    }

    @Test fun keystreamCorrectedBytesDecodeSceneScale() {
        // AndroidLow.plist bytes 180..229: keystream bytes 210 and 212 were fixed using the .feats files.
        val bytes = "dfb4c7ee5b34bc494c9c87eba8fdc613f5fd6d8ec3fbed184d89d81947bfb3aaa2e0e29ba9fe36d2402dacf52d857cc6974f".chunked(2).map { it.toInt(16).toByte() }.toByteArray()
        val ks = PlistCipher.transform(ByteArray(230))!!.copyOfRange(180, 230)
        val plain = String(ByteArray(50) { i -> (bytes[i].toInt() xor ks[i].toInt()).toByte() }, Charsets.UTF_8)
        assertTrue(plain, plain.contains("3D_SCENE_SCALE</key>\r\n\t<real>1.0</real>"))
    }

    @Test fun keystreamDecodesEndOfLongestFeats() {
        // Last 40 bytes of the factory iPhone2,1.feats (10420 bytes).
        val tail = "e6674662f1989286ea1be7d0131e2aa8a3c3740cf854394eaa22738736f4083def2d25db483e730d".chunked(2).map { it.toInt(16).toByte() }.toByteArray()
        val ks = PlistCipher.transform(ByteArray(10420))!!.copyOfRange(10380, 10420)
        val plain = String(ByteArray(40) { i -> (tail[i].toInt() xor ks[i].toInt()).toByte() }, Charsets.UTF_8)
        assertTrue(plain, plain.endsWith("</Value>\n  </Device>\n</ShaderFeatures>\n"))
    }

    @Test fun transformRoundTripsAndRespectsCapacity() {
        val enc = PlistCipher.transform(sample.toByteArray())!!
        assertEquals(sample, PlistCipher.decodePlist(enc))
        assertNull(PlistCipher.transform(ByteArray(PlistCipher.capacity + 1)))
        assertTrue("must cover the longest preset (iPhone.plist, 6990 bytes)", PlistCipher.capacity >= 6990)
        assertTrue("must cover the longest .feats (iPhone2,1.feats, 10420 bytes)", PlistCipher.capacity >= 10420)
    }

    @Test fun decodeRejectsNonPlist() {
        assertNull(PlistCipher.decodePlist(PlistCipher.transform("hello world".toByteArray())!!))
    }

    @Test fun readsSwitchesAndIdentifier() {
        assertEquals(false, HdrPatcher.readBool(sample, "USE_HDR_POST_PROCESS"))
        assertNull(HdrPatcher.readBool(sample, "NOT_A_KEY"))
        assertEquals("AndroidHigh", HdrPatcher.identifier(sample))
    }

    @Test fun detectRecognisesEachLevel() {
        assertEquals(HdrState.NONE, HdrPatcher.detect(sample))
        for (level in HdrLevel.entries) assertEquals(level.name, HdrPatcher.detect(HdrPatcher.applyExact(sample, level)).name)
        val custom = sample.replace("<key>TONEMAP</key>\r\n\t<false/>", "<key>TONEMAP</key>\r\n\t<true/>")
        assertEquals(HdrState.CUSTOM, HdrPatcher.detect(custom))
        assertEquals(HdrState.UNKNOWN, HdrPatcher.detect("<plist><dict></dict></plist>"))
    }

    @Test fun applyExactTurnsHigherSwitchesOffAndTouchesNothingElse() {
        val c = HdrPatcher.applyExact(sample, HdrLevel.C)
        val a = HdrPatcher.applyExact(c, HdrLevel.A)
        assertEquals(HdrState.A, HdrPatcher.detect(a))
        assertEquals(HdrPatcher.applyExact(sample, HdrLevel.A), a)
        // Back to factory = only the HDR switches differed.
        val restored = HdrLevel.A.keys.fold(a) { t, k -> t.replace("<key>$k</key>\r\n\t<true/>", "<key>$k</key>\r\n\t<false/>") }
        assertEquals(sample, restored)
        // patch() never turns a switch off.
        assertEquals(HdrState.C, HdrPatcher.detect(HdrPatcher.patch(c, HdrLevel.A)))
    }

    @Test fun levelAOnlyFlipsHdrPostProcess() {
        val out = HdrPatcher.patch(sample, HdrLevel.A)
        assertEquals(true, HdrPatcher.readBool(out, "USE_HDR_POST_PROCESS"))
        assertEquals(false, HdrPatcher.readBool(out, "TONEMAP"))
        assertEquals(false, HdrPatcher.readBool(out, "USE_PBR_WATER"))
        assertEquals("AndroidHigh", HdrPatcher.identifier(out))
        assertEquals(sample.length - 1, out.length)
    }

    @Test fun levelCFlipsEveryHdrSwitchAndNothingElse() {
        val out = HdrPatcher.patch(sample, HdrLevel.C)
        HdrLevel.C.keys.forEach { assertEquals(it, true, HdrPatcher.readBool(out, it)) }
        assertEquals(false, HdrPatcher.readBool(out, "USE_PBR_WATER"))
        assertEquals(sample.length - HdrLevel.C.keys.size, out.length)
    }

    @Test fun patchFailsWhenKeyMissing() {
        val broken = sample.replace("USE_HDR_POST_PROCESS", "SOMETHING_ELSE")
        assertFalse(runCatching { HdrPatcher.patch(broken, HdrLevel.A) }.isSuccess)
    }
}
