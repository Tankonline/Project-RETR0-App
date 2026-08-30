package rikka.shizuku;

import java.util.Arrays;

/* JADX INFO: renamed from: rikka.shizuku.p, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0479p {
    public final int a;
    public final byte[] b;

    public C0479p(byte[] bArr) {
        this.a = AbstractC0046as.D(bArr);
        this.b = bArr;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof C0479p)) {
            return false;
        }
        return Arrays.equals(this.b, ((C0479p) obj).b);
    }

    public final int hashCode() {
        return this.a;
    }
}
