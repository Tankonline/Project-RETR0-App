package rikka.shizuku;

import java.util.ArrayList;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public final class Lg {
    public static int o = 1000;
    public static boolean p = true;
    public final C0561rk b;
    public C0360l4[] e;
    public final Xn k;
    public C0360l4 n;
    public int a = 0;
    public int c = 32;
    public int d = 32;
    public boolean f = false;
    public boolean[] g = new boolean[32];
    public int h = 1;
    public int i = 0;
    public int j = 32;
    public C0565ro[] l = new C0565ro[o];
    public int m = 0;

    public Lg() {
        this.e = null;
        this.e = new C0360l4[32];
        q();
        Xn xn = new Xn();
        xn.a = new Yj();
        xn.b = new Yj();
        xn.c = new Yj();
        xn.d = new C0565ro[32];
        this.k = xn;
        C0561rk c0561rk = new C0561rk(xn);
        c0561rk.f = new C0565ro[128];
        c0561rk.g = new C0565ro[128];
        c0561rk.h = 0;
        c0561rk.i = new C0531qk(c0561rk);
        this.b = c0561rk;
        if (p) {
            this.n = new Kg(xn);
        } else {
            this.n = new C0360l4(xn);
        }
    }

    public static int m(Object obj) {
        C0565ro c0565ro = ((Z6) obj).g;
        if (c0565ro != null) {
            return (int) (c0565ro.e + 0.5f);
        }
        return 0;
    }

    public final C0565ro a(int i) {
        C0565ro c0565ro = (C0565ro) ((Yj) this.k.c).a();
        if (c0565ro == null) {
            c0565ro = new C0565ro(i);
            c0565ro.l = i;
        } else {
            c0565ro.c();
            c0565ro.l = i;
        }
        int i2 = this.m;
        int i3 = o;
        if (i2 >= i3) {
            int i4 = i3 * 2;
            o = i4;
            this.l = (C0565ro[]) Arrays.copyOf(this.l, i4);
        }
        C0565ro[] c0565roArr = this.l;
        int i5 = this.m;
        this.m = i5 + 1;
        c0565roArr[i5] = c0565ro;
        return c0565ro;
    }

    public final void b(C0565ro c0565ro, C0565ro c0565ro2, int i, float f, C0565ro c0565ro3, C0565ro c0565ro4, int i2, int i3) {
        C0360l4 c0360l4K = k();
        if (c0565ro2 == c0565ro3) {
            c0360l4K.d.b(c0565ro, 1.0f);
            c0360l4K.d.b(c0565ro4, 1.0f);
            c0360l4K.d.b(c0565ro2, -2.0f);
        } else if (f == 0.5f) {
            c0360l4K.d.b(c0565ro, 1.0f);
            c0360l4K.d.b(c0565ro2, -1.0f);
            c0360l4K.d.b(c0565ro3, -1.0f);
            c0360l4K.d.b(c0565ro4, 1.0f);
            if (i > 0 || i2 > 0) {
                c0360l4K.b = (-i) + i2;
            }
        } else if (f <= 0.0f) {
            c0360l4K.d.b(c0565ro, -1.0f);
            c0360l4K.d.b(c0565ro2, 1.0f);
            c0360l4K.b = i;
        } else if (f >= 1.0f) {
            c0360l4K.d.b(c0565ro4, -1.0f);
            c0360l4K.d.b(c0565ro3, 1.0f);
            c0360l4K.b = -i2;
        } else {
            float f2 = 1.0f - f;
            c0360l4K.d.b(c0565ro, f2 * 1.0f);
            c0360l4K.d.b(c0565ro2, f2 * (-1.0f));
            c0360l4K.d.b(c0565ro3, (-1.0f) * f);
            c0360l4K.d.b(c0565ro4, 1.0f * f);
            if (i > 0 || i2 > 0) {
                c0360l4K.b = (i2 * f) + ((-i) * f2);
            }
        }
        if (i3 != 8) {
            c0360l4K.a(this, i3);
        }
        c(c0360l4K);
    }

    /* JADX WARN: Code duplicated, block: B:51:0x00be  */
    /* JADX WARN: Code duplicated, block: B:69:0x00e0  */
    public final void c(C0360l4 c0360l4) {
        boolean z;
        boolean z2;
        C0565ro c0565roE;
        ArrayList arrayList;
        if (this.i + 1 >= this.j || this.h + 1 >= this.d) {
            n();
        }
        if (c0360l4.e) {
            z = false;
        } else {
            if (this.e.length != 0) {
                boolean z3 = false;
                while (!z3) {
                    int iK = c0360l4.d.k();
                    int i = 0;
                    while (true) {
                        arrayList = c0360l4.c;
                        if (i >= iK) {
                            break;
                        }
                        C0565ro c0565roE2 = c0360l4.d.e(i);
                        if (c0565roE2.c != -1 || c0565roE2.f) {
                            arrayList.add(c0565roE2);
                        }
                        i++;
                    }
                    if (arrayList.size() > 0) {
                        int size = arrayList.size();
                        int i2 = 0;
                        while (i2 < size) {
                            Object obj = arrayList.get(i2);
                            i2++;
                            C0565ro c0565ro = (C0565ro) obj;
                            if (c0565ro.f) {
                                c0360l4.g(c0565ro, true);
                            } else {
                                c0360l4.h(this.e[c0565ro.c], true);
                            }
                        }
                        arrayList.clear();
                    } else {
                        z3 = true;
                    }
                }
            }
            float f = 0.0f;
            if (c0360l4.a == null && c0360l4.b == 0.0f && c0360l4.d.k() == 0) {
                return;
            }
            float f2 = c0360l4.b;
            if (f2 < 0.0f) {
                c0360l4.b = f2 * (-1.0f);
                c0360l4.d.i();
            }
            int iK2 = c0360l4.d.k();
            float f3 = 0.0f;
            float f4 = 0.0f;
            C0565ro c0565ro2 = null;
            C0565ro c0565ro3 = null;
            int i3 = 0;
            boolean z4 = false;
            boolean z5 = false;
            while (i3 < iK2) {
                float fA = c0360l4.d.a(i3);
                C0565ro c0565roE3 = c0360l4.d.e(i3);
                float f5 = f;
                if (c0565roE3.l == 1) {
                    if (c0565ro2 == null) {
                        if (c0565roE3.k <= 1) {
                            z4 = true;
                        } else {
                            z4 = false;
                        }
                        c0565ro2 = c0565roE3;
                        f3 = fA;
                    } else {
                        if (f3 > fA) {
                            if (c0565roE3.k > 1) {
                                z4 = false;
                            }
                            c0565ro2 = c0565roE3;
                            f3 = fA;
                        } else if (z4 || c0565roE3.k > 1) {
                        }
                        z4 = true;
                        c0565ro2 = c0565roE3;
                        f3 = fA;
                    }
                } else if (c0565ro2 == null && fA < f5) {
                    if (c0565ro3 == null) {
                        if (c0565roE3.k <= 1) {
                            z5 = true;
                        } else {
                            z5 = false;
                        }
                        c0565ro3 = c0565roE3;
                        f4 = fA;
                    } else {
                        if (f4 > fA) {
                            if (c0565roE3.k > 1) {
                                z5 = false;
                            }
                            c0565ro3 = c0565roE3;
                            f4 = fA;
                        } else if (z5 || c0565roE3.k > 1) {
                        }
                        z5 = true;
                        c0565ro3 = c0565roE3;
                        f4 = fA;
                    }
                }
                i3++;
                f = f5;
            }
            float f6 = f;
            if (c0565ro2 == null) {
                c0565ro2 = c0565ro3;
            }
            if (c0565ro2 == null) {
                z2 = true;
            } else {
                c0360l4.f(c0565ro2);
                z2 = false;
            }
            if (c0360l4.d.k() == 0) {
                c0360l4.e = true;
            }
            if (z2) {
                if (this.h + 1 >= this.d) {
                    n();
                }
                C0565ro c0565roA = a(3);
                int i4 = this.a + 1;
                this.a = i4;
                this.h++;
                c0565roA.b = i4;
                ((C0565ro[]) this.k.d)[i4] = c0565roA;
                c0360l4.a = c0565roA;
                h(c0360l4);
                C0360l4 c0360l5 = this.n;
                c0360l5.a = null;
                c0360l5.d.clear();
                for (int i5 = 0; i5 < c0360l4.d.k(); i5++) {
                    c0360l5.d.h(c0360l4.d.e(i5), c0360l4.d.a(i5), true);
                }
                p(this.n);
                if (c0565roA.c == -1) {
                    if (c0360l4.a == c0565roA && (c0565roE = c0360l4.e(null, c0565roA)) != null) {
                        c0360l4.f(c0565roE);
                    }
                    if (!c0360l4.e) {
                        c0360l4.a.d(c0360l4);
                    }
                    this.i--;
                }
                z = true;
            } else {
                z = false;
            }
            C0565ro c0565ro4 = c0360l4.a;
            if (c0565ro4 == null) {
                return;
            }
            if (c0565ro4.l != 1 && c0360l4.b < f6) {
                return;
            }
        }
        if (z) {
            return;
        }
        h(c0360l4);
    }

    public final void d(C0565ro c0565ro, int i) {
        int i2 = c0565ro.c;
        if (i2 == -1) {
            c0565ro.e = i;
            c0565ro.f = true;
            int i3 = c0565ro.j;
            for (int i4 = 0; i4 < i3; i4++) {
                c0565ro.i[i4].g(c0565ro, false);
            }
            c0565ro.j = 0;
            return;
        }
        if (i2 == -1) {
            C0360l4 c0360l4K = k();
            c0360l4K.a = c0565ro;
            float f = i;
            c0565ro.e = f;
            c0360l4K.b = f;
            c0360l4K.e = true;
            c(c0360l4K);
            return;
        }
        C0360l4 c0360l4 = this.e[i2];
        if (c0360l4.e) {
            c0360l4.b = i;
            return;
        }
        if (c0360l4.d.k() == 0) {
            c0360l4.e = true;
            c0360l4.b = i;
            return;
        }
        C0360l4 c0360l4K2 = k();
        if (i < 0) {
            c0360l4K2.b = i * (-1);
            c0360l4K2.d.b(c0565ro, 1.0f);
        } else {
            c0360l4K2.b = i;
            c0360l4K2.d.b(c0565ro, -1.0f);
        }
        c(c0360l4K2);
    }

    public final void e(C0565ro c0565ro, C0565ro c0565ro2, int i, int i2) {
        boolean z = false;
        if (i2 == 8 && c0565ro2.f && c0565ro.c == -1) {
            c0565ro.e = c0565ro2.e + i;
            c0565ro.f = true;
            int i3 = c0565ro.j;
            for (int i4 = 0; i4 < i3; i4++) {
                c0565ro.i[i4].g(c0565ro, false);
            }
            c0565ro.j = 0;
            return;
        }
        C0360l4 c0360l4K = k();
        if (i != 0) {
            if (i < 0) {
                i *= -1;
                z = true;
            }
            c0360l4K.b = i;
        }
        if (z) {
            c0360l4K.d.b(c0565ro, 1.0f);
            c0360l4K.d.b(c0565ro2, -1.0f);
        } else {
            c0360l4K.d.b(c0565ro, -1.0f);
            c0360l4K.d.b(c0565ro2, 1.0f);
        }
        if (i2 != 8) {
            c0360l4K.a(this, i2);
        }
        c(c0360l4K);
    }

    public final void f(C0565ro c0565ro, C0565ro c0565ro2, int i, int i2) {
        C0360l4 c0360l4K = k();
        C0565ro c0565roL = l();
        c0565roL.d = 0;
        c0360l4K.b(c0565ro, c0565ro2, c0565roL, i);
        if (i2 != 8) {
            c0360l4K.d.b(i(i2), (int) (c0360l4K.d.g(c0565roL) * (-1.0f)));
        }
        c(c0360l4K);
    }

    public final void g(C0565ro c0565ro, C0565ro c0565ro2, int i, int i2) {
        C0360l4 c0360l4K = k();
        C0565ro c0565roL = l();
        c0565roL.d = 0;
        c0360l4K.c(c0565ro, c0565ro2, c0565roL, i);
        if (i2 != 8) {
            c0360l4K.d.b(i(i2), (int) (c0360l4K.d.g(c0565roL) * (-1.0f)));
        }
        c(c0360l4K);
    }

    public final void h(C0360l4 c0360l4) {
        boolean z = p;
        Xn xn = this.k;
        if (z) {
            C0360l4 c0360l5 = this.e[this.i];
            if (c0360l5 != null) {
                ((Yj) xn.a).b(c0360l5);
            }
        } else {
            C0360l4 c0360l6 = this.e[this.i];
            if (c0360l6 != null) {
                ((Yj) xn.b).b(c0360l6);
            }
        }
        C0360l4[] c0360l4Arr = this.e;
        int i = this.i;
        c0360l4Arr[i] = c0360l4;
        C0565ro c0565ro = c0360l4.a;
        c0565ro.c = i;
        this.i = i + 1;
        c0565ro.d(c0360l4);
    }

    public final C0565ro i(int i) {
        if (this.h + 1 >= this.d) {
            n();
        }
        C0565ro c0565roA = a(4);
        int i2 = this.a + 1;
        this.a = i2;
        this.h++;
        c0565roA.b = i2;
        c0565roA.d = i;
        ((C0565ro[]) this.k.d)[i2] = c0565roA;
        C0561rk c0561rk = this.b;
        c0561rk.i.a = c0565roA;
        float[] fArr = c0565roA.h;
        Arrays.fill(fArr, 0.0f);
        fArr[c0565roA.d] = 1.0f;
        c0561rk.i(c0565roA);
        return c0565roA;
    }

    public final C0565ro j(Object obj) {
        if (obj == null) {
            return null;
        }
        if (this.h + 1 >= this.d) {
            n();
        }
        if (!(obj instanceof Z6)) {
            return null;
        }
        Z6 z6 = (Z6) obj;
        C0565ro c0565ro = z6.g;
        if (c0565ro == null) {
            z6.i();
            c0565ro = z6.g;
        }
        int i = c0565ro.b;
        Xn xn = this.k;
        if (i != -1 && i <= this.a && ((C0565ro[]) xn.d)[i] != null) {
            return c0565ro;
        }
        if (i != -1) {
            c0565ro.c();
        }
        int i2 = this.a + 1;
        this.a = i2;
        this.h++;
        c0565ro.b = i2;
        c0565ro.l = 1;
        ((C0565ro[]) xn.d)[i2] = c0565ro;
        return c0565ro;
    }

    public final C0360l4 k() {
        boolean z = p;
        Xn xn = this.k;
        if (z) {
            C0360l4 c0360l4 = (C0360l4) ((Yj) xn.a).a();
            if (c0360l4 == null) {
                return new Kg(xn);
            }
            c0360l4.a = null;
            c0360l4.d.clear();
            c0360l4.b = 0.0f;
            c0360l4.e = false;
            return c0360l4;
        }
        C0360l4 c0360l5 = (C0360l4) ((Yj) xn.b).a();
        if (c0360l5 == null) {
            return new C0360l4(xn);
        }
        c0360l5.a = null;
        c0360l5.d.clear();
        c0360l5.b = 0.0f;
        c0360l5.e = false;
        return c0360l5;
    }

    public final C0565ro l() {
        if (this.h + 1 >= this.d) {
            n();
        }
        C0565ro c0565roA = a(3);
        int i = this.a + 1;
        this.a = i;
        this.h++;
        c0565roA.b = i;
        ((C0565ro[]) this.k.d)[i] = c0565roA;
        return c0565roA;
    }

    public final void n() {
        int i = this.c * 2;
        this.c = i;
        this.e = (C0360l4[]) Arrays.copyOf(this.e, i);
        Xn xn = this.k;
        xn.d = (C0565ro[]) Arrays.copyOf((C0565ro[]) xn.d, this.c);
        int i2 = this.c;
        this.g = new boolean[i2];
        this.d = i2;
        this.j = i2;
    }

    public final void o(C0561rk c0561rk) {
        Xn xn;
        for (int i = 0; i < this.i; i++) {
            C0360l4 c0360l4 = this.e[i];
            int i2 = 1;
            if (c0360l4.a.l != 1) {
                float f = 0.0f;
                if (c0360l4.b < 0.0f) {
                    boolean z = false;
                    int i3 = 0;
                    while (!z) {
                        i3 += i2;
                        float f2 = Float.MAX_VALUE;
                        int i4 = -1;
                        int i5 = -1;
                        int i6 = 0;
                        int i7 = 0;
                        while (true) {
                            int i8 = this.i;
                            xn = this.k;
                            if (i6 >= i8) {
                                break;
                            }
                            C0360l4 c0360l5 = this.e[i6];
                            if (c0360l5.a.l != i2 && !c0360l5.e && c0360l5.b < f) {
                                int i9 = i2;
                                while (i9 < this.h) {
                                    C0565ro c0565ro = ((C0565ro[]) xn.d)[i9];
                                    float fG = c0360l5.d.g(c0565ro);
                                    if (fG > f) {
                                        for (int i10 = 0; i10 < 9; i10++) {
                                            float f3 = c0565ro.g[i10] / fG;
                                            if ((f3 < f2 && i10 == i7) || i10 > i7) {
                                                i7 = i10;
                                                f2 = f3;
                                                i4 = i6;
                                                i5 = i9;
                                            }
                                        }
                                    }
                                    i9++;
                                    f = 0.0f;
                                }
                            }
                            i6++;
                            f = 0.0f;
                            i2 = 1;
                        }
                        if (i4 != -1) {
                            C0360l4 c0360l6 = this.e[i4];
                            c0360l6.a.c = -1;
                            c0360l6.f(((C0565ro[]) xn.d)[i5]);
                            C0565ro c0565ro2 = c0360l6.a;
                            c0565ro2.c = i4;
                            c0565ro2.d(c0360l6);
                        } else {
                            z = true;
                        }
                        if (i3 > this.h / 2) {
                            z = true;
                        }
                        f = 0.0f;
                        i2 = 1;
                    }
                    break;
                }
            }
        }
        p(c0561rk);
        for (int i11 = 0; i11 < this.i; i11++) {
            C0360l4 c0360l7 = this.e[i11];
            c0360l7.a.e = c0360l7.b;
        }
    }

    public final void p(C0360l4 c0360l4) {
        for (int i = 0; i < this.h; i++) {
            this.g[i] = false;
        }
        boolean z = false;
        int i2 = 0;
        while (!z) {
            i2++;
            if (i2 >= this.h * 2) {
                return;
            }
            C0565ro c0565ro = c0360l4.a;
            if (c0565ro != null) {
                this.g[c0565ro.b] = true;
            }
            C0565ro c0565roD = c0360l4.d(this.g);
            if (c0565roD != null) {
                boolean[] zArr = this.g;
                int i3 = c0565roD.b;
                if (zArr[i3]) {
                    return;
                } else {
                    zArr[i3] = true;
                }
            }
            if (c0565roD != null) {
                float f = Float.MAX_VALUE;
                int i4 = -1;
                for (int i5 = 0; i5 < this.i; i5++) {
                    C0360l4 c0360l5 = this.e[i5];
                    if (c0360l5.a.l != 1 && !c0360l5.e && c0360l5.d.d(c0565roD)) {
                        float fG = c0360l5.d.g(c0565roD);
                        if (fG < 0.0f) {
                            float f2 = (-c0360l5.b) / fG;
                            if (f2 < f) {
                                i4 = i5;
                                f = f2;
                            }
                        }
                    }
                }
                if (i4 > -1) {
                    C0360l4 c0360l6 = this.e[i4];
                    c0360l6.a.c = -1;
                    c0360l6.f(c0565roD);
                    C0565ro c0565ro2 = c0360l6.a;
                    c0565ro2.c = i4;
                    c0565ro2.d(c0360l6);
                }
            } else {
                z = true;
            }
        }
    }

    public final void q() {
        boolean z = p;
        Xn xn = this.k;
        int i = 0;
        if (z) {
            while (true) {
                C0360l4[] c0360l4Arr = this.e;
                if (i >= c0360l4Arr.length) {
                    return;
                }
                C0360l4 c0360l4 = c0360l4Arr[i];
                if (c0360l4 != null) {
                    ((Yj) xn.a).b(c0360l4);
                }
                this.e[i] = null;
                i++;
            }
        } else {
            while (true) {
                C0360l4[] c0360l4Arr2 = this.e;
                if (i >= c0360l4Arr2.length) {
                    return;
                }
                C0360l4 c0360l5 = c0360l4Arr2[i];
                if (c0360l5 != null) {
                    ((Yj) xn.b).b(c0360l5);
                }
                this.e[i] = null;
                i++;
            }
        }
    }

    public final void r() {
        Xn xn;
        int i = 0;
        while (true) {
            xn = this.k;
            C0565ro[] c0565roArr = (C0565ro[]) xn.d;
            if (i >= c0565roArr.length) {
                break;
            }
            C0565ro c0565ro = c0565roArr[i];
            if (c0565ro != null) {
                c0565ro.c();
            }
            i++;
        }
        Yj yj = (Yj) xn.c;
        C0565ro[] c0565roArr2 = this.l;
        int length = this.m;
        yj.getClass();
        if (length > c0565roArr2.length) {
            length = c0565roArr2.length;
        }
        for (int i2 = 0; i2 < length; i2++) {
            C0565ro c0565ro2 = c0565roArr2[i2];
            int i3 = yj.c;
            Object[] objArr = yj.b;
            if (i3 < objArr.length) {
                objArr[i3] = c0565ro2;
                yj.c = i3 + 1;
            }
        }
        this.m = 0;
        Arrays.fill((C0565ro[]) xn.d, (Object) null);
        this.a = 0;
        C0561rk c0561rk = this.b;
        c0561rk.h = 0;
        c0561rk.b = 0.0f;
        this.h = 1;
        for (int i4 = 0; i4 < this.i; i4++) {
            this.e[i4].getClass();
        }
        q();
        this.i = 0;
        if (p) {
            this.n = new Kg(xn);
        } else {
            this.n = new C0360l4(xn);
        }
    }
}
