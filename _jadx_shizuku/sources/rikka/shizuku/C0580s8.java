package rikka.shizuku;

import java.io.IOException;

/* JADX INFO: renamed from: rikka.shizuku.s8, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0580s8 extends AbstractC0079c {
    public static C0580s8 t(Object obj) {
        if (obj == null || (obj instanceof C0580s8)) {
            return (C0580s8) obj;
        }
        if (obj instanceof AbstractC0079c) {
            return (C0580s8) ((AbstractC0079c) obj).p();
        }
        if (!(obj instanceof byte[])) {
            throw new IllegalArgumentException("illegal object in getInstance: ".concat(obj.getClass().getName()));
        }
        try {
            return (C0580s8) ((AbstractC0079c) AbstractC0633u.o((byte[]) obj)).p();
        } catch (Exception e) {
            throw new IllegalArgumentException("encoding error in getInstance: " + e.toString());
        }
    }

    public static C0580s8 u(H4 h4) {
        if (128 != h4.b) {
            throw new IllegalStateException("this method only valid for CONTEXT_SPECIFIC tags");
        }
        AbstractC0633u abstractC0633uD = h4.d.d();
        return abstractC0633uD instanceof C0580s8 ? t(abstractC0633uD) : new C0580s8(true, r.r(abstractC0633uD).a);
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final void j(Ys ys, boolean z) throws IOException {
        byte[] bArr = this.a;
        int i = bArr[0] & 255;
        int length = bArr.length;
        int i2 = length - 1;
        byte b = bArr[i2];
        byte b2 = (byte) ((255 << i) & b);
        if (b == b2) {
            ys.H(z, 3, bArr);
            return;
        }
        ys.K(3, z);
        ys.F(length);
        ys.E(bArr, 0, i2);
        ys.D(b2);
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final boolean k() {
        return false;
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final int m(boolean z) {
        return Ys.v(this.a.length, z);
    }

    @Override // rikka.shizuku.AbstractC0079c, rikka.shizuku.AbstractC0633u
    public final AbstractC0633u p() {
        return this;
    }

    @Override // rikka.shizuku.AbstractC0079c, rikka.shizuku.AbstractC0633u
    public final AbstractC0633u q() {
        return this;
    }
}
