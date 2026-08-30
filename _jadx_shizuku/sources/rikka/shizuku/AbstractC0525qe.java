package rikka.shizuku;

import java.io.ByteArrayOutputStream;

/* JADX INFO: renamed from: rikka.shizuku.qe, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0525qe {
    public static final R2 a = new R2(17);

    public static byte[] a(byte[] bArr, int i) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            R2 r2 = a;
            r2.getClass();
            if (i >= 0) {
                byte[] bArr2 = new byte[72];
                int i2 = 0;
                while (i > 0) {
                    int iMin = Math.min(36, i);
                    int i3 = i2 + iMin;
                    int i4 = 0;
                    while (i2 < i3) {
                        int i5 = i2 + 1;
                        byte b = bArr[i2];
                        int i6 = i4 + 1;
                        byte[] bArr3 = (byte[]) r2.b;
                        bArr2[i4] = bArr3[(b & 255) >>> 4];
                        i4 += 2;
                        bArr2[i6] = bArr3[b & 15];
                        i2 = i5;
                    }
                    byteArrayOutputStream.write(bArr2, 0, i4);
                    i -= iMin;
                    i2 = i3;
                }
            }
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            C0602t c0602t = new C0602t(2, "exception encoding Hex string: " + e.getMessage());
            c0602t.b = e;
            throw c0602t;
        }
    }
}
