package rikka.shizuku;

/* JADX INFO: loaded from: classes.dex */
public class Se implements InterfaceC0180f8 {
    public static final int a(byte[] bArr) {
        if (bArr == null) {
            return 0;
        }
        int length = bArr.length;
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            byte b = bArr[i2];
            i = b >= 0 ? i + b : b + 256 + i;
        }
        return i;
    }

    public boolean b(CharSequence charSequence) {
        return false;
    }
}
