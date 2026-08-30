package rikka.shizuku;

/* JADX INFO: loaded from: classes.dex */
public final class P4 extends AbstractC0463oe {
    public int f0;
    public boolean g0;
    public int h0;

    @Override // rikka.shizuku.C0425n7
    public final void a(Lg lg) {
        boolean z;
        int i;
        int i2;
        Z6[] z6Arr = this.F;
        Z6 z6 = this.x;
        z6Arr[0] = z6;
        Z6 z7 = this.y;
        int i3 = 2;
        z6Arr[2] = z7;
        Z6 z8 = this.z;
        z6Arr[1] = z8;
        Z6 z9 = this.A;
        z6Arr[3] = z9;
        for (Z6 z10 : z6Arr) {
            z10.g = lg.j(z10);
        }
        int i4 = this.f0;
        if (i4 < 0 || i4 >= 4) {
            return;
        }
        Z6 z11 = z6Arr[i4];
        int i5 = 0;
        while (true) {
            if (i5 >= this.e0) {
                z = false;
                break;
            }
            C0425n7 c0425n7 = this.d0[i5];
            if ((this.g0 || c0425n7.b()) && ((((i2 = this.f0) == 0 || i2 == 1) && c0425n7.c0[0] == 3 && c0425n7.x.d != null && c0425n7.z.d != null) || ((i2 == 2 || i2 == 3) && c0425n7.c0[1] == 3 && c0425n7.y.d != null && c0425n7.A.d != null))) {
                z = true;
                break;
            }
            i5++;
        }
        boolean z2 = z6.e() || z8.e();
        boolean z3 = z7.e() || z9.e();
        int i6 = (z || !(((i = this.f0) == 0 && z2) || ((i == 2 && z3) || ((i == 1 && z2) || (i == 3 && z3))))) ? 4 : 5;
        int i7 = 0;
        while (i7 < this.e0) {
            C0425n7 c0425n8 = this.d0[i7];
            if (this.g0 || c0425n8.b()) {
                C0565ro c0565roJ = lg.j(c0425n8.F[this.f0]);
                int i8 = this.f0;
                Z6 z12 = c0425n8.F[i8];
                z12.g = c0565roJ;
                Z6 z13 = z12.d;
                int i9 = (z13 == null || z13.b != this) ? 0 : z12.e;
                if (i8 == 0 || i8 == i3) {
                    C0565ro c0565ro = z11.g;
                    int i10 = this.h0 - i9;
                    C0360l4 c0360l4K = lg.k();
                    C0565ro c0565roL = lg.l();
                    c0565roL.d = 0;
                    c0360l4K.c(c0565ro, c0565roJ, c0565roL, i10);
                    lg.c(c0360l4K);
                } else {
                    C0565ro c0565ro2 = z11.g;
                    int i11 = this.h0 + i9;
                    C0360l4 c0360l4K2 = lg.k();
                    C0565ro c0565roL2 = lg.l();
                    c0565roL2.d = 0;
                    c0360l4K2.b(c0565ro2, c0565roJ, c0565roL2, i11);
                    lg.c(c0360l4K2);
                }
                lg.e(z11.g, c0565roJ, this.h0 + i9, i6);
            }
            i7++;
            i3 = 2;
        }
        int i12 = this.f0;
        if (i12 == 0) {
            lg.e(z8.g, z6.g, 0, 8);
            lg.e(z6.g, this.I.z.g, 0, 4);
            lg.e(z6.g, this.I.x.g, 0, 0);
            return;
        }
        if (i12 == 1) {
            lg.e(z6.g, z8.g, 0, 8);
            lg.e(z6.g, this.I.x.g, 0, 4);
            lg.e(z6.g, this.I.z.g, 0, 0);
        } else if (i12 == 2) {
            lg.e(z9.g, z7.g, 0, 8);
            lg.e(z7.g, this.I.A.g, 0, 4);
            lg.e(z7.g, this.I.y.g, 0, 0);
        } else if (i12 == 3) {
            lg.e(z7.g, z9.g, 0, 8);
            lg.e(z7.g, this.I.y.g, 0, 4);
            lg.e(z7.g, this.I.A.g, 0, 0);
        }
    }

    @Override // rikka.shizuku.C0425n7
    public final boolean b() {
        return true;
    }

    @Override // rikka.shizuku.C0425n7
    public final String toString() {
        String strC = "[Barrier] " + this.W + " {";
        for (int i = 0; i < this.e0; i++) {
            C0425n7 c0425n7 = this.d0[i];
            if (i > 0) {
                strC = AbstractC0535qo.c(strC, ", ");
            }
            strC = strC + c0425n7.W;
        }
        return AbstractC0535qo.c(strC, "}");
    }
}
