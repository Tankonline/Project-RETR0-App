package rikka.shizuku;

import java.io.IOException;
import java.util.Arrays;

/* JADX INFO: renamed from: rikka.shizuku.w8, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0704w8 extends AbstractC0633u implements A {
    public final byte[] a;

    static {
        new C0048b(2, C0704w8.class);
    }

    public C0704w8(byte[] bArr) {
        if (bArr == null) {
            throw new NullPointerException("'contents' cannot be null");
        }
        this.a = bArr;
    }

    @Override // rikka.shizuku.A
    public final String e() {
        return Wo.a(this.a);
    }

    @Override // rikka.shizuku.AbstractC0633u, rikka.shizuku.AbstractC0417n
    public final int hashCode() {
        return AbstractC0046as.D(this.a);
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final boolean i(AbstractC0633u abstractC0633u) {
        if (!(abstractC0633u instanceof C0704w8)) {
            return false;
        }
        return Arrays.equals(this.a, ((C0704w8) abstractC0633u).a);
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final void j(Ys ys, boolean z) throws IOException {
        ys.H(z, 25, this.a);
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
