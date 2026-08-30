package rikka.shizuku;

import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public final class Qj {
    public final byte a;
    public final byte[] b;

    public Qj(byte b, byte[] bArr) {
        this.a = b;
        byte[] bArr2 = new byte[8191];
        this.b = bArr2;
        int length = bArr.length;
        System.arraycopy(bArr, 0, bArr2, 0, length <= 8191 ? length : 8191);
    }

    public final String a() {
        return "type=" + ((int) this.a) + ", data=" + Arrays.toString(this.b);
    }

    public final String toString() {
        return AbstractC0535qo.d("PeerInfo(", a(), ")");
    }
}
