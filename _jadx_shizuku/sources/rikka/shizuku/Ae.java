package rikka.shizuku;

import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class Ae extends AbstractC0725wt {
    public static final int[] k = new int[2];

    public static void m(int[] iArr, int i, int i2, int i3, int i4, float f, int i5) {
        int i6 = i2 - i;
        int i7 = i4 - i3;
        if (i5 != -1) {
            if (i5 == 0) {
                iArr[0] = (int) ((i7 * f) + 0.5f);
                iArr[1] = i7;
                return;
            } else {
                if (i5 != 1) {
                    return;
                }
                iArr[0] = i6;
                iArr[1] = (int) ((i6 * f) + 0.5f);
                return;
            }
        }
        int i8 = (int) ((i7 * f) + 0.5f);
        int i9 = (int) ((i6 / f) + 0.5f);
        if (i8 <= i6) {
            iArr[0] = i8;
            iArr[1] = i7;
        } else if (i9 <= i7) {
            iArr[0] = i6;
            iArr[1] = i9;
        }
    }

    /* JADX WARN: Code duplicated, block: B:116:0x0268  */
    /* JADX WARN: Code duplicated, block: B:118:0x0278  */
    /* JADX WARN: Code duplicated, block: B:11:0x0026  */
    @Override // rikka.shizuku.K9
    public final void a(K9 k9) {
        float f;
        int iG;
        int i;
        int iG2;
        float f2;
        float f3;
        float f4;
        int i2;
        if (AbstractC0535qo.j(this.j) == 3) {
            C0425n7 c0425n7 = this.b;
            l(c0425n7.x, c0425n7.z, 0);
            return;
        }
        X9 x9 = this.e;
        boolean z = x9.j;
        M9 m9 = this.h;
        M9 m10 = this.i;
        if (z || this.d != 3) {
            f = 0.5f;
        } else {
            C0425n7 c0425n8 = this.b;
            int i3 = c0425n8.j;
            if (i3 == 2) {
                f = 0.5f;
                C0425n7 c0425n9 = c0425n8.I;
                if (c0425n9 != null) {
                    X9 x10 = c0425n9.d.e;
                    if (x10.j) {
                        x9.d((int) ((x10.g * c0425n8.o) + 0.5f));
                    }
                }
            } else if (i3 == 3) {
                int i4 = c0425n8.k;
                if (i4 == 0 || i4 == 3) {
                    C0600ss c0600ss = c0425n8.e;
                    M9 m11 = c0600ss.h;
                    M9 m12 = c0600ss.i;
                    boolean z2 = c0425n8.x.d != null;
                    boolean z3 = c0425n8.y.d != null;
                    boolean z4 = c0425n8.z.d != null;
                    boolean z5 = c0425n8.A.d != null;
                    f = 0.5f;
                    int i5 = c0425n8.M;
                    if (z2 && z3 && z4 && z5) {
                        float f5 = c0425n8.L;
                        boolean z6 = m11.j;
                        int[] iArr = k;
                        if (z6 && m12.j) {
                            if (m9.c && m10.c) {
                                m(iArr, ((M9) m9.l.get(0)).g + m9.f, ((M9) m10.l.get(0)).g - m10.f, m11.g + m11.f, m12.g - m12.f, f5, i5);
                                x9.d(iArr[0]);
                                this.b.e.e.d(iArr[1]);
                                return;
                            }
                            return;
                        }
                        boolean z7 = m9.j;
                        ArrayList arrayList = m11.l;
                        if (z7 && m10.j) {
                            if (!m11.c || !m12.c) {
                                return;
                            }
                            m(iArr, m9.g + m9.f, m10.g - m10.f, ((M9) arrayList.get(0)).g + m11.f, ((M9) m12.l.get(0)).g - m12.f, f5, i5);
                            x9.d(iArr[0]);
                            this.b.e.e.d(iArr[1]);
                        }
                        if (!m9.c || !m10.c || !m11.c || !m12.c) {
                            return;
                        }
                        m(iArr, ((M9) m9.l.get(0)).g + m9.f, ((M9) m10.l.get(0)).g - m10.f, ((M9) arrayList.get(0)).g + m11.f, ((M9) m12.l.get(0)).g - m12.f, f5, i5);
                        x9.d(iArr[0]);
                        this.b.e.e.d(iArr[1]);
                    } else if (z2 && z4) {
                        if (!m9.c || !m10.c) {
                            return;
                        }
                        float f6 = c0425n8.L;
                        int i6 = ((M9) m9.l.get(0)).g + m9.f;
                        int i7 = ((M9) m10.l.get(0)).g - m10.f;
                        if (i5 == -1 || i5 == 0) {
                            int iG3 = g(i7 - i6, 0);
                            int i8 = (int) ((iG3 * f6) + 0.5f);
                            int iG4 = g(i8, 1);
                            if (i8 != iG4) {
                                iG3 = (int) ((iG4 / f6) + 0.5f);
                            }
                            x9.d(iG3);
                            this.b.e.e.d(iG4);
                        } else if (i5 == 1) {
                            int iG5 = g(i7 - i6, 0);
                            int i9 = (int) ((iG5 / f6) + 0.5f);
                            int iG6 = g(i9, 1);
                            if (i9 != iG6) {
                                iG5 = (int) ((iG6 * f6) + 0.5f);
                            }
                            x9.d(iG5);
                            this.b.e.e.d(iG6);
                        }
                    } else if (z3 && z5) {
                        if (!m11.c || !m12.c) {
                            return;
                        }
                        float f7 = c0425n8.L;
                        int i10 = ((M9) m11.l.get(0)).g + m11.f;
                        int i11 = ((M9) m12.l.get(0)).g - m12.f;
                        if (i5 == -1) {
                            iG = g(i11 - i10, 1);
                            i = (int) ((iG / f7) + 0.5f);
                            iG2 = g(i, 0);
                            if (i != iG2) {
                                iG = (int) ((iG2 * f7) + 0.5f);
                            }
                            x9.d(iG2);
                            this.b.e.e.d(iG);
                        } else if (i5 == 0) {
                            int iG7 = g(i11 - i10, 1);
                            int i12 = (int) ((iG7 * f7) + 0.5f);
                            int iG8 = g(i12, 0);
                            if (i12 != iG8) {
                                iG7 = (int) ((iG8 / f7) + 0.5f);
                            }
                            x9.d(iG8);
                            this.b.e.e.d(iG7);
                        } else if (i5 == 1) {
                            iG = g(i11 - i10, 1);
                            i = (int) ((iG / f7) + 0.5f);
                            iG2 = g(i, 0);
                            if (i != iG2) {
                                iG = (int) ((iG2 * f7) + 0.5f);
                            }
                            x9.d(iG2);
                            this.b.e.e.d(iG);
                        }
                    }
                } else {
                    int i13 = c0425n8.M;
                    if (i13 != -1) {
                        if (i13 == 0) {
                            f4 = c0425n8.e.e.g / c0425n8.L;
                            i2 = (int) (f4 + 0.5f);
                        } else if (i13 != 1) {
                            i2 = 0;
                        } else {
                            f2 = c0425n8.e.e.g;
                            f3 = c0425n8.L;
                        }
                        x9.d(i2);
                        f = 0.5f;
                    } else {
                        f2 = c0425n8.e.e.g;
                        f3 = c0425n8.L;
                    }
                    f4 = f2 * f3;
                    i2 = (int) (f4 + 0.5f);
                    x9.d(i2);
                    f = 0.5f;
                }
            } else {
                f = 0.5f;
            }
        }
        if (m9.c && m10.c) {
            if (m9.j && m10.j && x9.j) {
                return;
            }
            if (!x9.j && this.d == 3) {
                C0425n7 c0425n10 = this.b;
                if (c0425n10.j == 0 && !c0425n10.q()) {
                    M9 m13 = (M9) m9.l.get(0);
                    M9 m14 = (M9) m10.l.get(0);
                    int i14 = m13.g + m9.f;
                    int i15 = m14.g + m10.f;
                    m9.d(i14);
                    m10.d(i15);
                    x9.d(i15 - i14);
                    return;
                }
            }
            if (!x9.j && this.d == 3 && this.a == 1 && m9.l.size() > 0 && m10.l.size() > 0) {
                int iMin = Math.min((((M9) m10.l.get(0)).g + m10.f) - (((M9) m9.l.get(0)).g + m9.f), x9.m);
                C0425n7 c0425n11 = this.b;
                int i16 = c0425n11.n;
                int iMax = Math.max(c0425n11.m, iMin);
                if (i16 > 0) {
                    iMax = Math.min(i16, iMax);
                }
                x9.d(iMax);
            }
            if (x9.j) {
                M9 m15 = (M9) m9.l.get(0);
                M9 m16 = (M9) m10.l.get(0);
                int i17 = m15.g;
                int i18 = m9.f + i17;
                int i19 = m16.g;
                int i20 = m10.f + i19;
                float f8 = this.b.S;
                if (m15 == m16) {
                    f8 = f;
                } else {
                    i17 = i18;
                    i19 = i20;
                }
                m9.d((int) ((((i19 - i17) - x9.g) * f8) + i17 + f));
                m10.d(m9.g + x9.g);
            }
        }
    }

    @Override // rikka.shizuku.AbstractC0725wt
    public final void d() {
        C0425n7 c0425n7;
        C0425n7 c0425n8;
        C0425n7 c0425n9;
        C0425n7 c0425n10;
        C0425n7 c0425n11 = this.b;
        boolean z = c0425n11.a;
        X9 x9 = this.e;
        if (z) {
            x9.d(c0425n11.l());
        }
        boolean z2 = x9.j;
        M9 m9 = this.i;
        M9 m10 = this.h;
        if (!z2) {
            C0425n7 c0425n12 = this.b;
            int i = c0425n12.c0[0];
            this.d = i;
            if (i != 3) {
                if (i == 4 && (((c0425n10 = c0425n12.I) != null && c0425n10.c0[0] == 1) || c0425n10.c0[0] == 4)) {
                    int iL = (c0425n10.l() - this.b.x.c()) - this.b.z.c();
                    Ae ae = c0425n10.d;
                    AbstractC0725wt.b(m10, ae.h, this.b.x.c());
                    AbstractC0725wt.b(m9, ae.i, -this.b.z.c());
                    x9.d(iL);
                    return;
                }
                if (i == 1) {
                    x9.d(c0425n12.l());
                }
            }
        } else if (this.d == 4 && (((c0425n8 = (c0425n7 = this.b).I) != null && c0425n8.c0[0] == 1) || c0425n8.c0[0] == 4)) {
            AbstractC0725wt.b(m10, c0425n8.d.h, c0425n7.x.c());
            AbstractC0725wt.b(m9, c0425n8.d.i, -this.b.z.c());
            return;
        }
        if (x9.j) {
            C0425n7 c0425n13 = this.b;
            if (c0425n13.a) {
                Z6[] z6Arr = c0425n13.F;
                Z6 z6 = z6Arr[0];
                Z6 z7 = z6.d;
                if (z7 != null && z6Arr[1].d != null) {
                    if (c0425n13.q()) {
                        m10.f = this.b.F[0].c();
                        m9.f = -this.b.F[1].c();
                        return;
                    }
                    M9 m9H = AbstractC0725wt.h(this.b.F[0]);
                    if (m9H != null) {
                        AbstractC0725wt.b(m10, m9H, this.b.F[0].c());
                    }
                    M9 m9H2 = AbstractC0725wt.h(this.b.F[1]);
                    if (m9H2 != null) {
                        AbstractC0725wt.b(m9, m9H2, -this.b.F[1].c());
                    }
                    m10.b = true;
                    m9.b = true;
                    return;
                }
                if (z7 != null) {
                    M9 m9H3 = AbstractC0725wt.h(z6);
                    if (m9H3 != null) {
                        AbstractC0725wt.b(m10, m9H3, this.b.F[0].c());
                        AbstractC0725wt.b(m9, m10, x9.g);
                        return;
                    }
                    return;
                }
                Z6 z8 = z6Arr[1];
                if (z8.d != null) {
                    M9 m9H4 = AbstractC0725wt.h(z8);
                    if (m9H4 != null) {
                        AbstractC0725wt.b(m9, m9H4, -this.b.F[1].c());
                        AbstractC0725wt.b(m10, m9, -x9.g);
                        return;
                    }
                    return;
                }
                if ((c0425n13 instanceof AbstractC0463oe) || c0425n13.I == null || c0425n13.g(7).d != null) {
                    return;
                }
                C0425n7 c0425n14 = this.b;
                AbstractC0725wt.b(m10, c0425n14.I.d.h, c0425n14.m());
                AbstractC0725wt.b(m9, m10, x9.g);
                return;
            }
        }
        if (this.d == 3) {
            C0425n7 c0425n15 = this.b;
            int i2 = c0425n15.j;
            if (i2 == 2) {
                C0425n7 c0425n16 = c0425n15.I;
                if (c0425n16 != null) {
                    X9 x10 = c0425n16.e.e;
                    x9.l.add(x10);
                    x10.k.add(x9);
                    x9.b = true;
                    x9.k.add(m10);
                    x9.k.add(m9);
                }
            } else if (i2 == 3) {
                if (c0425n15.k == 3) {
                    m10.a = this;
                    m9.a = this;
                    C0600ss c0600ss = c0425n15.e;
                    c0600ss.h.a = this;
                    c0600ss.i.a = this;
                    x9.a = this;
                    if (c0425n15.r()) {
                        x9.l.add(this.b.e.e);
                        this.b.e.e.k.add(x9);
                        C0600ss c0600ss2 = this.b.e;
                        c0600ss2.e.a = this;
                        x9.l.add(c0600ss2.h);
                        x9.l.add(this.b.e.i);
                        this.b.e.h.k.add(x9);
                        this.b.e.i.k.add(x9);
                    } else if (this.b.q()) {
                        this.b.e.e.l.add(x9);
                        x9.k.add(this.b.e.e);
                    } else {
                        this.b.e.e.l.add(x9);
                    }
                } else {
                    X9 x11 = c0425n15.e.e;
                    x9.l.add(x11);
                    x11.k.add(x9);
                    this.b.e.h.k.add(x9);
                    this.b.e.i.k.add(x9);
                    x9.b = true;
                    x9.k.add(m10);
                    x9.k.add(m9);
                    m10.l.add(x9);
                    m9.l.add(x9);
                }
            }
        }
        C0425n7 c0425n17 = this.b;
        Z6[] z6Arr2 = c0425n17.F;
        Z6 z9 = z6Arr2[0];
        Z6 z10 = z9.d;
        if (z10 != null && z6Arr2[1].d != null) {
            if (c0425n17.q()) {
                m10.f = this.b.F[0].c();
                m9.f = -this.b.F[1].c();
                return;
            }
            M9 m9H5 = AbstractC0725wt.h(this.b.F[0]);
            M9 m9H6 = AbstractC0725wt.h(this.b.F[1]);
            m9H5.b(this);
            m9H6.b(this);
            this.j = 4;
            return;
        }
        if (z10 != null) {
            M9 m9H7 = AbstractC0725wt.h(z9);
            if (m9H7 != null) {
                AbstractC0725wt.b(m10, m9H7, this.b.F[0].c());
                c(m9, m10, 1, x9);
                return;
            }
            return;
        }
        Z6 z11 = z6Arr2[1];
        if (z11.d != null) {
            M9 m9H8 = AbstractC0725wt.h(z11);
            if (m9H8 != null) {
                AbstractC0725wt.b(m9, m9H8, -this.b.F[1].c());
                c(m10, m9, -1, x9);
                return;
            }
            return;
        }
        if ((c0425n17 instanceof AbstractC0463oe) || (c0425n9 = c0425n17.I) == null) {
            return;
        }
        AbstractC0725wt.b(m10, c0425n9.d.h, c0425n17.m());
        c(m9, m10, 1, x9);
    }

    @Override // rikka.shizuku.AbstractC0725wt
    public final void e() {
        M9 m9 = this.h;
        if (m9.j) {
            this.b.N = m9.g;
        }
    }

    @Override // rikka.shizuku.AbstractC0725wt
    public final void f() {
        this.c = null;
        this.h.c();
        this.i.c();
        this.e.c();
        this.g = false;
    }

    @Override // rikka.shizuku.AbstractC0725wt
    public final boolean k() {
        return this.d != 3 || this.b.j == 0;
    }

    public final void n() {
        this.g = false;
        M9 m9 = this.h;
        m9.c();
        m9.j = false;
        M9 m10 = this.i;
        m10.c();
        m10.j = false;
        this.e.j = false;
    }

    public final String toString() {
        return "HorizontalRun " + this.b.W;
    }
}
