package rikka.shizuku;

import java.util.ArrayList;

/* JADX INFO: renamed from: rikka.shizuku.l4, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0360l4 {
    public InterfaceC0329k4 d;
    public C0565ro a = null;
    public float b = 0.0f;
    public ArrayList c = new ArrayList();
    public boolean e = false;

    public C0360l4(Xn xn) {
        this.d = new C0145e4(this, xn);
    }

    public final void a(Lg lg, int i) {
        this.d.b(lg.i(i), 1.0f);
        this.d.b(lg.i(i), -1.0f);
    }

    public final void b(C0565ro c0565ro, C0565ro c0565ro2, C0565ro c0565ro3, int i) {
        boolean z = false;
        if (i != 0) {
            if (i < 0) {
                i *= -1;
                z = true;
            }
            this.b = i;
        }
        if (z) {
            this.d.b(c0565ro, 1.0f);
            this.d.b(c0565ro2, -1.0f);
            this.d.b(c0565ro3, -1.0f);
        } else {
            this.d.b(c0565ro, -1.0f);
            this.d.b(c0565ro2, 1.0f);
            this.d.b(c0565ro3, 1.0f);
        }
    }

    public final void c(C0565ro c0565ro, C0565ro c0565ro2, C0565ro c0565ro3, int i) {
        boolean z = false;
        if (i != 0) {
            if (i < 0) {
                i *= -1;
                z = true;
            }
            this.b = i;
        }
        if (z) {
            this.d.b(c0565ro, 1.0f);
            this.d.b(c0565ro2, -1.0f);
            this.d.b(c0565ro3, 1.0f);
        } else {
            this.d.b(c0565ro, -1.0f);
            this.d.b(c0565ro2, 1.0f);
            this.d.b(c0565ro3, -1.0f);
        }
    }

    public C0565ro d(boolean[] zArr) {
        return e(zArr, null);
    }

    public final C0565ro e(boolean[] zArr, C0565ro c0565ro) {
        int i;
        int iK = this.d.k();
        C0565ro c0565ro2 = null;
        float f = 0.0f;
        for (int i2 = 0; i2 < iK; i2++) {
            float fA = this.d.a(i2);
            if (fA < 0.0f) {
                C0565ro c0565roE = this.d.e(i2);
                if ((zArr == null || !zArr[c0565roE.b]) && c0565roE != c0565ro && (((i = c0565roE.l) == 3 || i == 4) && fA < f)) {
                    f = fA;
                    c0565ro2 = c0565roE;
                }
            }
        }
        return c0565ro2;
    }

    public final void f(C0565ro c0565ro) {
        C0565ro c0565ro2 = this.a;
        if (c0565ro2 != null) {
            this.d.b(c0565ro2, -1.0f);
            this.a = null;
        }
        float fJ = this.d.j(c0565ro, true) * (-1.0f);
        this.a = c0565ro;
        if (fJ == 1.0f) {
            return;
        }
        this.b /= fJ;
        this.d.f(fJ);
    }

    public final void g(C0565ro c0565ro, boolean z) {
        if (c0565ro.f) {
            float fG = this.d.g(c0565ro);
            this.b = (c0565ro.e * fG) + this.b;
            this.d.j(c0565ro, z);
            if (z) {
                c0565ro.b(this);
            }
        }
    }

    public void h(C0360l4 c0360l4, boolean z) {
        float fC = this.d.c(c0360l4, z);
        this.b = (c0360l4.b * fC) + this.b;
        if (z) {
            c0360l4.a.b(this);
        }
    }

    public String toString() {
        boolean z;
        String strC = AbstractC0535qo.c(this.a == null ? "0" : "" + this.a, " = ");
        if (this.b != 0.0f) {
            strC = strC + this.b;
            z = true;
        } else {
            z = false;
        }
        int iK = this.d.k();
        for (int i = 0; i < iK; i++) {
            C0565ro c0565roE = this.d.e(i);
            if (c0565roE != null) {
                float fA = this.d.a(i);
                if (fA != 0.0f) {
                    String string = c0565roE.toString();
                    if (z) {
                        if (fA > 0.0f) {
                            strC = AbstractC0535qo.c(strC, " + ");
                        } else {
                            strC = AbstractC0535qo.c(strC, " - ");
                            fA *= -1.0f;
                        }
                    } else if (fA < 0.0f) {
                        strC = AbstractC0535qo.c(strC, "- ");
                        fA *= -1.0f;
                    }
                    strC = fA == 1.0f ? AbstractC0535qo.c(strC, string) : strC + fA + " " + string;
                    z = true;
                }
            }
        }
        return !z ? AbstractC0535qo.c(strC, "0.0") : strC;
    }
}
