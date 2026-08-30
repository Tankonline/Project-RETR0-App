package rikka.shizuku;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public abstract class Wo {
    public static final /* synthetic */ int a = 0;

    static {
        try {
            try {
            } catch (Exception unused) {
                String.format("%n", new Object[0]);
            }
        } catch (Exception unused2) {
        }
    }

    public static String a(byte[] bArr) {
        int length = bArr.length;
        char[] cArr = new char[length];
        for (int i = 0; i != length; i++) {
            cArr[i] = (char) (bArr[i] & 255);
        }
        return new String(cArr);
    }

    public static byte[] b(String str) {
        int length = str.length();
        byte[] bArr = new byte[length];
        for (int i = 0; i != length; i++) {
            bArr[i] = (byte) str.charAt(i);
        }
        return bArr;
    }

    public static String c(String str) {
        char[] charArray = str.toCharArray();
        boolean z = false;
        for (int i = 0; i != charArray.length; i++) {
            char c = charArray[i];
            if ('A' <= c && 'Z' >= c) {
                charArray[i] = (char) (c + ' ');
                z = true;
            }
        }
        return z ? new String(charArray) : str;
    }

    public static void d(char[] cArr, ByteArrayOutputStream byteArrayOutputStream) throws IOException {
        int i;
        int i2;
        int i3;
        int i4 = 0;
        while (i4 < cArr.length) {
            char c = cArr[i4];
            if (c >= 128) {
                if (c < 2048) {
                    i = c;
                    i2 = (c >> 6) | 192;
                } else {
                    if (c < 55296 || c > 57343) {
                        i = c;
                        i = c;
                        byteArrayOutputStream.write((c >> '\f') | 224);
                        i2 = ((c >> 6) & 63) | 128;
                    } else {
                        i4++;
                        if (i4 >= cArr.length) {
                            i = c;
                            throw new IllegalStateException("invalid UTF-16 codepoint");
                        }
                        char c2 = cArr[i4];
                        if (c > 56319) {
                            i = c;
                            throw new IllegalStateException("invalid UTF-16 codepoint");
                        }
                        i = c;
                        int i5 = (((c & 1023) << 10) | (c2 & 1023)) + 65536;
                        byteArrayOutputStream.write((i5 >> 18) | 240);
                        byteArrayOutputStream.write(((i5 >> 12) & 63) | 128);
                        byteArrayOutputStream.write(((i5 >> 6) & 63) | 128);
                        i3 = i5;
                    }
                    i = (i3 & 63) | 128;
                }
                byteArrayOutputStream.write(i2);
                i3 = c;
                i = (i3 & 63) | 128;
            }
            i = c;
            byteArrayOutputStream.write(i);
            i4++;
        }
    }

    public static String e(String str) {
        char[] charArray = str.toCharArray();
        boolean z = false;
        for (int i = 0; i != charArray.length; i++) {
            char c = charArray[i];
            if ('a' <= c && 'z' >= c) {
                charArray[i] = (char) (c - ' ');
                z = true;
            }
        }
        return z ? new String(charArray) : str;
    }
}
