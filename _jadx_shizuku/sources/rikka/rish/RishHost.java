package rikka.rish;

import android.os.ParcelFileDescriptor;
import rikka.shizuku.G0;

/* JADX INFO: loaded from: classes.dex */
public class RishHost {
    public final String[] a;
    public final String[] b;
    public final String c;
    public final byte d;
    public final int e;
    public final int f;
    public final int g;
    public int h;
    public int i;
    public int j = Integer.MAX_VALUE;

    public RishHost(String[] strArr, String[] strArr2, String str, byte b, ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, ParcelFileDescriptor parcelFileDescriptor3) {
        this.a = strArr;
        this.b = strArr2;
        this.c = str;
        this.d = b;
        this.e = parcelFileDescriptor == null ? -1 : parcelFileDescriptor.detachFd();
        this.f = parcelFileDescriptor2 == null ? -1 : parcelFileDescriptor2.detachFd();
        this.g = parcelFileDescriptor3 != null ? parcelFileDescriptor3.detachFd() : -1;
    }

    public static byte[] b(String[] strArr) {
        if (strArr == null) {
            return null;
        }
        int length = strArr.length;
        byte[][] bArr = new byte[length][];
        int length2 = length;
        for (int i = 0; i < length; i++) {
            byte[] bytes = strArr[i].getBytes();
            bArr[i] = bytes;
            length2 += bytes.length;
        }
        byte[] bArr2 = new byte[length2];
        int length3 = 0;
        for (int i2 = 0; i2 < length; i2++) {
            byte[] bArr3 = bArr[i2];
            System.arraycopy(bArr3, 0, bArr2, length3, bArr3.length);
            length3 += bArr3.length + 1;
        }
        return bArr2;
    }

    private static native void setWindowSize(int i, long j);

    private static native int[] start(byte[] bArr, int i, byte[] bArr2, int i2, byte[] bArr3, byte b, int i3, int i4, int i5);

    private static native int waitFor(int i);

    public final void c(long j) {
        setWindowSize(this.i, j);
    }

    public final void d() {
        byte[] bArr;
        String[] strArr = this.a;
        byte[] bArrB = b(strArr);
        String[] strArr2 = this.b;
        byte[] bArrB2 = b(strArr2);
        String str = this.c;
        if (str == null) {
            bArr = null;
        } else {
            byte[] bytes = str.getBytes();
            int length = bytes.length;
            byte[] bArr2 = new byte[length + 1];
            System.arraycopy(bytes, 0, bArr2, 0, bytes.length);
            bArr2[length] = 0;
            bArr = bArr2;
        }
        int[] iArrStart = start(bArrB, strArr.length, bArrB2, strArr2 != null ? strArr2.length : -1, bArr, this.d, this.e, this.f, this.g);
        this.h = iArrStart[0];
        this.i = iArrStart[1];
        new Thread(new G0(14, this)).start();
    }
}
