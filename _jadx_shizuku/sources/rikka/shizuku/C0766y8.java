package rikka.shizuku;

import java.io.IOException;

/* JADX INFO: renamed from: rikka.shizuku.y8, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0766y8 extends AbstractC0633u {
    public static final C0766y8 a = new C0766y8();
    public static final byte[] b = new byte[0];

    @Override // rikka.shizuku.AbstractC0633u, rikka.shizuku.AbstractC0417n
    public final int hashCode() {
        return -1;
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final boolean i(AbstractC0633u abstractC0633u) {
        return abstractC0633u instanceof C0766y8;
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final void j(Ys ys, boolean z) throws IOException {
        ys.H(z, 5, b);
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final boolean k() {
        return false;
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final int m(boolean z) {
        return Ys.v(0, z);
    }

    public final String toString() {
        return "NULL";
    }
}
