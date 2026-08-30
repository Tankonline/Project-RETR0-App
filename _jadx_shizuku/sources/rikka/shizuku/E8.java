package rikka.shizuku;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public final class E8 extends AbstractC0757y {
    public int c;

    @Override // rikka.shizuku.AbstractC0633u
    public final void j(Ys ys, boolean z) throws IOException {
        ys.K(49, z);
        B8 b8S = ys.s();
        InterfaceC0202g[] interfaceC0202gArr = this.a;
        int length = interfaceC0202gArr.length;
        int i = 0;
        if (this.c >= 0 || length > 16) {
            ys.F(u());
            while (i < length) {
                interfaceC0202gArr[i].d().p().j(b8S, true);
                i++;
            }
            return;
        }
        AbstractC0633u[] abstractC0633uArr = new AbstractC0633u[length];
        int iM = 0;
        for (int i2 = 0; i2 < length; i2++) {
            AbstractC0633u abstractC0633uP = interfaceC0202gArr[i2].d().p();
            abstractC0633uArr[i2] = abstractC0633uP;
            iM += abstractC0633uP.m(true);
        }
        this.c = iM;
        ys.F(iM);
        while (i < length) {
            abstractC0633uArr[i].j(b8S, true);
            i++;
        }
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final int m(boolean z) {
        return Ys.v(u(), z);
    }

    @Override // rikka.shizuku.AbstractC0757y, rikka.shizuku.AbstractC0633u
    public final AbstractC0633u p() {
        return this.b ? this : super.p();
    }

    public final int u() {
        if (this.c < 0) {
            int iM = 0;
            for (InterfaceC0202g interfaceC0202g : this.a) {
                iM += interfaceC0202g.d().p().m(true);
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
