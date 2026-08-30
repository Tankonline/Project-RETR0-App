package rikka.shizuku;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public final class Q8 extends AbstractC0726x {
    public int c;

    @Override // rikka.shizuku.AbstractC0633u
    public final void j(Ys ys, boolean z) throws IOException {
        ys.K(48, z);
        P8 p8T = ys.t();
        int length = this.a.length;
        int i = 0;
        if (this.c >= 0 || length > 16) {
            ys.F(z());
            while (i < length) {
                p8T.L(this.a[i].d());
                i++;
            }
            return;
        }
        AbstractC0633u[] abstractC0633uArr = new AbstractC0633u[length];
        int iM = 0;
        for (int i2 = 0; i2 < length; i2++) {
            AbstractC0633u abstractC0633uQ = this.a[i2].d().q();
            abstractC0633uArr[i2] = abstractC0633uQ;
            iM += abstractC0633uQ.m(true);
        }
        this.c = iM;
        ys.F(iM);
        while (i < length) {
            p8T.L(abstractC0633uArr[i]);
            i++;
        }
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final int m(boolean z) {
        return Ys.v(z(), z);
    }

    @Override // rikka.shizuku.AbstractC0726x
    public final AbstractC0079c w() {
        return new M8(false, C0793z4.t(r()));
    }

    @Override // rikka.shizuku.AbstractC0726x
    public final r x() {
        return new A8(C4.s(s()));
    }

    @Override // rikka.shizuku.AbstractC0726x
    public final AbstractC0757y y() {
        return new R8(false, this.a);
    }

    public final int z() {
        if (this.c < 0) {
            int length = this.a.length;
            int iM = 0;
            for (int i = 0; i < length; i++) {
                iM += this.a[i].d().q().m(true);
            }
            this.c = iM;
        }
        return this.c;
    }

    @Override // rikka.shizuku.AbstractC0726x, rikka.shizuku.AbstractC0633u
    public final AbstractC0633u q() {
        return this;
    }
}
