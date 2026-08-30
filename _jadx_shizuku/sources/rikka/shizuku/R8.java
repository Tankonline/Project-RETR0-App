package rikka.shizuku;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public final class R8 extends AbstractC0757y {
    public int c;

    public R8(boolean z, InterfaceC0202g[] interfaceC0202gArr) {
        super(z, interfaceC0202gArr);
        this.c = -1;
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final void j(Ys ys, boolean z) throws IOException {
        ys.K(49, z);
        P8 p8T = ys.t();
        InterfaceC0202g[] interfaceC0202gArr = this.a;
        int length = interfaceC0202gArr.length;
        int i = 0;
        if (this.c >= 0 || length > 16) {
            ys.F(u());
            while (i < length) {
                p8T.L(interfaceC0202gArr[i].d());
                i++;
            }
            return;
        }
        AbstractC0633u[] abstractC0633uArr = new AbstractC0633u[length];
        int iM = 0;
        for (int i2 = 0; i2 < length; i2++) {
            AbstractC0633u abstractC0633uQ = interfaceC0202gArr[i2].d().q();
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
        return Ys.v(u(), z);
    }

    public final int u() {
        if (this.c < 0) {
            int iM = 0;
            for (InterfaceC0202g interfaceC0202g : this.a) {
                iM += interfaceC0202g.d().q().m(true);
            }
            this.c = iM;
        }
        return this.c;
    }

    @Override // rikka.shizuku.AbstractC0757y, rikka.shizuku.AbstractC0633u
    public final AbstractC0633u q() {
        return this;
    }
}
