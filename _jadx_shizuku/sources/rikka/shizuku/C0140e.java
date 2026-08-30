package rikka.shizuku;

import java.io.IOException;

/* JADX INFO: renamed from: rikka.shizuku.e, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0140e extends AbstractC0633u {
    public static final C0140e b;
    public static final C0140e c;
    public final byte a;

    static {
        new C0048b(1, C0140e.class);
        b = new C0140e((byte) 0);
        c = new C0140e((byte) -1);
    }

    public C0140e(byte b2) {
        this.a = b2;
    }

    public static C0140e r(byte[] bArr) {
        if (bArr.length != 1) {
            throw new IllegalArgumentException("BOOLEAN value should have 1 byte in it");
        }
        byte b2 = bArr[0];
        if (b2 != -1) {
            return b2 != 0 ? new C0140e(b2) : b;
        }
        return c;
    }

    @Override // rikka.shizuku.AbstractC0633u, rikka.shizuku.AbstractC0417n
    public final int hashCode() {
        return s() ? 1 : 0;
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final boolean i(AbstractC0633u abstractC0633u) {
        return (abstractC0633u instanceof C0140e) && s() == ((C0140e) abstractC0633u).s();
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final void j(Ys ys, boolean z) throws IOException {
        ys.K(1, z);
        ys.F(1);
        ys.D(this.a);
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final boolean k() {
        return false;
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final int m(boolean z) {
        return Ys.v(1, z);
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final AbstractC0633u p() {
        return s() ? c : b;
    }

    public final boolean s() {
        return this.a != 0;
    }

    public final String toString() {
        return s() ? "TRUE" : "FALSE";
    }
}
