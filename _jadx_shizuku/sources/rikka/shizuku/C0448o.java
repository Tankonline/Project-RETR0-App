package rikka.shizuku;

import java.io.IOException;

/* JADX INFO: renamed from: rikka.shizuku.o, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0448o extends AbstractC0633u {
    public final C0704w8 a;

    public C0448o(C0704w8 c0704w8) {
        this.a = c0704w8;
    }

    @Override // rikka.shizuku.AbstractC0633u, rikka.shizuku.AbstractC0417n
    public final int hashCode() {
        return ~AbstractC0046as.D(this.a.a);
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final boolean i(AbstractC0633u abstractC0633u) {
        if (abstractC0633u instanceof C0448o) {
            return this.a.i(((C0448o) abstractC0633u).a);
        }
        return false;
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final void j(Ys ys, boolean z) throws IOException {
        ys.K(7, z);
        ys.H(false, 25, this.a.a);
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final boolean k() {
        return false;
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final int m(boolean z) {
        return this.a.m(z);
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final AbstractC0633u p() {
        this.a.getClass();
        return this;
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final AbstractC0633u q() {
        this.a.getClass();
        return this;
    }
}
