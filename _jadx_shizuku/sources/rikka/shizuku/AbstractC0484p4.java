package rikka.shizuku;

/* JADX INFO: renamed from: rikka.shizuku.p4, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0484p4 extends AbstractC0744xh {
    public static boolean d0(Object[] objArr, Object obj) {
        return i0(objArr, obj) >= 0;
    }

    public static void e0(int i, int i2, int i3, int[] iArr, int[] iArr2) {
        System.arraycopy(iArr, i2, iArr2, i, i3 - i2);
    }

    public static void f0(Object[] objArr, Object[] objArr2, int i, int i2, int i3) {
        System.arraycopy(objArr, i2, objArr2, i, i3 - i2);
    }

    public static void g0(byte[] bArr, byte[] bArr2, int i, int i2) {
        if ((i2 & 2) != 0) {
            i = 0;
        }
        System.arraycopy(bArr, 0, bArr2, i, bArr.length);
    }

    public static void h0(Object[] objArr, Object[] objArr2, int i, int i2, int i3) {
        if ((i3 & 4) != 0) {
            i = 0;
        }
        System.arraycopy(objArr, i, objArr2, 0, i2 - i);
    }

    public static int i0(Object[] objArr, Object obj) {
        int i = 0;
        if (obj == null) {
            int length = objArr.length;
            while (i < length) {
                if (objArr[i] == null) {
                    return i;
                }
                i++;
            }
            return -1;
        }
        int length2 = objArr.length;
        while (i < length2) {
            if (obj.equals(objArr[i])) {
                return i;
            }
            i++;
        }
        return -1;
    }
}
