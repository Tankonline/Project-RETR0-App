package rikka.shizuku;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public final class D8 extends AbstractC0726x {
    public int c;

    public D8() {
        this.c = -1;
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final void j(Ys ys, boolean z) throws IOException {
        ys.K(48, z);
        B8 b8S = ys.s();
        int length = this.a.length;
        int i = 0;
        if (this.c >= 0 || length > 16) {
            ys.F(z());
            while (i < length) {
                this.a[i].d().p().j(b8S, true);
                i++;
            }
            return;
        }
        AbstractC0633u[] abstractC0633uArr = new AbstractC0633u[length];
        int iM = 0;
        for (int i2 = 0; i2 < length; i2++) {
            AbstractC0633u abstractC0633uP = this.a[i2].d().p();
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
        return Ys.v(z(), z);
    }

    @Override // rikka.shizuku.AbstractC0726x
    public final AbstractC0079c w() {
        return new C0580s8(false, C0793z4.t(r()));
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
                iM += this.a[i].d().p().m(true);
            }
            this.c = iM;
        }
        return this.c;
    }

    public D8(InterfaceC0202g[] interfaceC0202gArr) {
        if (interfaceC0202gArr != null) {
            for (InterfaceC0202g interfaceC0202g : interfaceC0202gArr) {
                if (interfaceC0202g != null) {
                }
            }
            this.a = C0233h.e(interfaceC0202gArr);
            this.c = -1;
            return;
        }
        throw new NullPointerException("'elements' cannot be null, or contain null");
    }

    @Override // rikka.shizuku.AbstractC0726x, rikka.shizuku.AbstractC0633u
    public final AbstractC0633u p() {
        return this;
    }

    @Override // rikka.shizuku.AbstractC0726x, rikka.shizuku.AbstractC0633u
    public final AbstractC0633u q() {
        return this;
    }
}
