package rikka.shizuku;

import java.io.IOException;
import java.util.Arrays;

/* JADX INFO: renamed from: rikka.shizuku.i, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0264i extends AbstractC0633u {
    public static final C0264i[] b = new C0264i[12];
    public final byte[] a;

    public C0264i(boolean z, byte[] bArr) {
        if (C0386m.t(bArr)) {
            throw new IllegalArgumentException("malformed enumerated");
        }
        int i = 0;
        if ((bArr[0] & 128) != 0) {
            throw new IllegalArgumentException("enumerated must be non-negative");
        }
        this.a = z ? AbstractC0046as.k(bArr) : bArr;
        int length = bArr.length - 1;
        while (i < length) {
            byte b2 = bArr[i];
            i++;
            if (b2 != (bArr[i] >> 7)) {
                return;
            }
        }
    }

    @Override // rikka.shizuku.AbstractC0633u, rikka.shizuku.AbstractC0417n
    public final int hashCode() {
        return AbstractC0046as.D(this.a);
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final boolean i(AbstractC0633u abstractC0633u) {
        if (!(abstractC0633u instanceof C0264i)) {
            return false;
        }
        return Arrays.equals(this.a, ((C0264i) abstractC0633u).a);
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final void j(Ys ys, boolean z) throws IOException {
        ys.H(z, 10, this.a);
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final boolean k() {
        return false;
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final int m(boolean z) {
        return Ys.v(this.a.length, z);
    }
}
