package rikka.shizuku;

import java.util.ArrayList;
import rikka.html.text.style.BulletSpan;

/* JADX INFO: loaded from: classes.dex */
public final class Xd extends C0425n7 {
    public float d0 = -1.0f;
    public int e0 = -1;
    public int f0 = -1;
    public Z6 g0 = this.y;
    public int h0 = 0;

    public Xd() {
        this.G.clear();
        this.G.add(this.g0);
        int length = this.F.length;
        for (int i = 0; i < length; i++) {
            this.F[i] = this.g0;
        }
    }

    @Override // rikka.shizuku.C0425n7
    public final void A(Lg lg) {
        if (this.I == null) {
            return;
        }
        Z6 z6 = this.g0;
        lg.getClass();
        int iM = Lg.m(z6);
        if (this.h0 == 1) {
            this.N = iM;
            this.O = 0;
            v(this.I.i());
            y(0);
            return;
        }
        this.N = 0;
        this.O = iM;
        y(this.I.l());
        v(0);
    }

    public final void B(int i) {
        if (this.h0 == i) {
            return;
        }
        this.h0 = i;
        ArrayList arrayList = this.G;
        arrayList.clear();
        if (this.h0 == 1) {
            this.g0 = this.x;
        } else {
            this.g0 = this.y;
        }
        arrayList.add(this.g0);
        Z6[] z6Arr = this.F;
        int length = z6Arr.length;
        for (int i2 = 0; i2 < length; i2++) {
            z6Arr[i2] = this.g0;
        }
    }

    @Override // rikka.shizuku.C0425n7
    public final void a(Lg lg) {
        C0456o7 c0456o7 = (C0456o7) this.I;
        if (c0456o7 == null) {
            return;
        }
        Z6 z6G = c0456o7.g(2);
        Z6 z6G2 = c0456o7.g(4);
        C0425n7 c0425n7 = this.I;
        boolean z = c0425n7 != null && c0425n7.c0[0] == 2;
        if (this.h0 == 0) {
            z6G = c0456o7.g(3);
            z6G2 = c0456o7.g(5);
            C0425n7 c0425n8 = this.I;
            z = c0425n8 != null && c0425n8.c0[1] == 2;
        }
        if (this.e0 != -1) {
            C0565ro c0565roJ = lg.j(this.g0);
            lg.e(c0565roJ, lg.j(z6G), this.e0, 8);
            if (z) {
                lg.f(lg.j(z6G2), c0565roJ, 0, 5);
                return;
            }
            return;
        }
        if (this.f0 != -1) {
            C0565ro c0565roJ2 = lg.j(this.g0);
            C0565ro c0565roJ3 = lg.j(z6G2);
            lg.e(c0565roJ2, c0565roJ3, -this.f0, 8);
            if (z) {
                lg.f(c0565roJ2, lg.j(z6G), 0, 5);
                lg.f(c0565roJ3, c0565roJ2, 0, 5);
                return;
            }
            return;
        }
        if (this.d0 != -1.0f) {
            C0565ro c0565roJ4 = lg.j(this.g0);
            C0565ro c0565roJ5 = lg.j(z6G2);
            float f = this.d0;
            C0360l4 c0360l4K = lg.k();
            c0360l4K.d.b(c0565roJ4, -1.0f);
            c0360l4K.d.b(c0565roJ5, f);
            lg.c(c0360l4K);
        }
    }

    @Override // rikka.shizuku.C0425n7
    public final boolean b() {
        return true;
    }

    @Override // rikka.shizuku.C0425n7
    public final Z6 g(int i) {
        switch (AbstractC0535qo.j(i)) {
            case 0:
            case BulletSpan.STANDARD_BULLET_RADIUS /* 5 */:
            case 6:
            case 7:
            case 8:
                return null;
            case 1:
            case 3:
                if (this.h0 == 1) {
                    return this.g0;
                }
                break;
            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
            case 4:
                if (this.h0 == 0) {
                    return this.g0;
                }
                break;
        }
        throw new AssertionError(AbstractC0535qo.i(i));
    }
}
