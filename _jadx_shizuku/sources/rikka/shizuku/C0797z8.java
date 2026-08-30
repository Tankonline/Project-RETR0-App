package rikka.shizuku;

import java.io.IOException;
import java.util.Arrays;

/* JADX INFO: renamed from: rikka.shizuku.z8, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0797z8 extends AbstractC0633u implements A {
    public final byte[] a;

    public C0797z8(byte[] bArr) {
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
        if (!(abstractC0633u instanceof C0797z8)) {
            return false;
        }
        return Arrays.equals(this.a, ((C0797z8) abstractC0633u).a);
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final void j(Ys ys, boolean z) throws IOException {
        ys.H(z, 18, this.a);
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final boolean k() {
        return false;
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final int m(boolean z) {
        return Ys.v(this.a.length, z);
    }

    public final String toString() {
        return Wo.a(this.a);
    }
}
