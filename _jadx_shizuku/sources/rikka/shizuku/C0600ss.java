package rikka.shizuku;

/* JADX INFO: renamed from: rikka.shizuku.ss, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0600ss extends AbstractC0725wt {
    public M9 k;
    public Y4 l;

    @Override // rikka.shizuku.K9
    public final void a(K9 k9) {
        float f;
        float f2;
        float f3;
        int i;
        if (AbstractC0535qo.j(this.j) == 3) {
            C0425n7 c0425n7 = this.b;
            l(c0425n7.y, c0425n7.A, 1);
            return;
        }
        X9 x9 = this.e;
        if (x9.c && !x9.j && this.d == 3) {
            C0425n7 c0425n8 = this.b;
            int i2 = c0425n8.k;
            if (i2 == 2) {
                C0425n7 c0425n9 = c0425n8.I;
                if (c0425n9 != null) {
                    X9 x10 = c0425n9.e.e;
                    if (x10.j) {
                        x9.d((int) ((x10.g * c0425n8.r) + 0.5f));
                    }
                }
            } else if (i2 == 3) {
                X9 x11 = c0425n8.d.e;
                if (x11.j) {
                    int i3 = c0425n8.M;
                    if (i3 != -1) {
                        if (i3 == 0) {
                            f3 = x11.g * c0425n8.L;
                            i = (int) (f3 + 0.5f);
                        } else if (i3 != 1) {
                            i = 0;
                        } else {
                            f = x11.g;
                            f2 = c0425n8.L;
                        }
                        x9.d(i);
                    } else {
                        f = x11.g;
                        f2 = c0425n8.L;
                    }
                    f3 = f / f2;
                    i = (int) (f3 + 0.5f);
                    x9.d(i);
                }
            }
        }
        M9 m9 = this.h;
        if (m9.c) {
            M9 m10 = this.i;
            if (m10.c) {
                if (m9.j && m10.j && x9.j) {
                    return;
                }
                if (!x9.j && this.d == 3) {
                    C0425n7 c0425n10 = this.b;
                    if (c0425n10.j == 0 && !c0425n10.r()) {
                        M9 m11 = (M9) m9.l.get(0);
                        M9 m12 = (M9) m10.l.get(0);
                        int i4 = m11.g + m9.f;
                        int i5 = m12.g + m10.f;
                        m9.d(i4);
                        m10.d(i5);
                        x9.d(i5 - i4);
                        return;
                    }
                }
                if (!x9.j && this.d == 3 && this.a == 1 && m9.l.size() > 0 && m10.l.size() > 0) {
                    M9 m13 = (M9) m9.l.get(0);
                    int i6 = (((M9) m10.l.get(0)).g + m10.f) - (m13.g + m9.f);
                    int i7 = x9.m;
                    if (i6 < i7) {
                        x9.d(i6);
                    } else {
                        x9.d(i7);
                    }
                }
                if (x9.j && m9.l.size() > 0 && m10.l.size() > 0) {
                    M9 m14 = (M9) m9.l.get(0);
                    M9 m15 = (M9) m10.l.get(0);
                    int i8 = m14.g;
                    int i9 = m9.f + i8;
                    int i10 = m15.g;
                    int i11 = m10.f + i10;
                    float f4 = this.b.T;
                    if (m14 == m15) {
                        f4 = 0.5f;
                    } else {
                        i8 = i9;
                        i10 = i11;
                    }
                    m9.d((int) ((((i10 - i8) - x9.g) * f4) + i8 + 0.5f));
                    m10.d(m9.g + x9.g);
                }
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
            x9.d(c0425n11.i());
        }
        boolean z2 = x9.j;
        M9 m9 = this.i;
        M9 m10 = this.h;
        if (!z2) {
            C0425n7 c0425n12 = this.b;
            this.d = c0425n12.c0[1];
            if (c0425n12.w) {
                this.l = new Y4(this);
            }
            int i = this.d;
            if (i != 3) {
                if (i == 4 && (c0425n10 = this.b.I) != null && c0425n10.c0[1] == 1) {
                    int i2 = (c0425n10.i() - this.b.y.c()) - this.b.A.c();
                    C0600ss c0600ss = c0425n10.e;
                    AbstractC0725wt.b(m10, c0600ss.h, this.b.y.c());
                    AbstractC0725wt.b(m9, c0600ss.i, -this.b.A.c());
                    x9.d(i2);
                    return;
                }
                if (i == 1) {
                    x9.d(this.b.i());
                }
            }
        } else if (this.d == 4 && (c0425n8 = (c0425n7 = this.b).I) != null && c0425n8.c0[1] == 1) {
            C0600ss c0600ss2 = c0425n8.e;
            AbstractC0725wt.b(m10, c0600ss2.h, c0425n7.y.c());
            AbstractC0725wt.b(m9, c0600ss2.i, -this.b.A.c());
            return;
        }
        boolean z3 = x9.j;
        M9 m11 = this.k;
        if (z3) {
            C0425n7 c0425n13 = this.b;
            if (c0425n13.a) {
                Z6[] z6Arr = c0425n13.F;
                Z6 z6 = z6Arr[2];
                Z6 z7 = z6.d;
                if (z7 != null && z6Arr[3].d != null) {
                    if (c0425n13.r()) {
                        m10.f = this.b.F[2].c();
                        m9.f = -this.b.F[3].c();
                    } else {
                        M9 m9H = AbstractC0725wt.h(this.b.F[2]);
                        if (m9H != null) {
                            AbstractC0725wt.b(m10, m9H, this.b.F[2].c());
                        }
                        M9 m9H2 = AbstractC0725wt.h(this.b.F[3]);
                        if (m9H2 != null) {
                            AbstractC0725wt.b(m9, m9H2, -this.b.F[3].c());
                        }
                        m10.b = true;
                        m9.b = true;
                    }
                    C0425n7 c0425n14 = this.b;
                    if (c0425n14.w) {
                        AbstractC0725wt.b(m11, m10, c0425n14.P);
                        return;
                    }
                    return;
                }
                if (z7 != null) {
                    M9 m9H3 = AbstractC0725wt.h(z6);
                    if (m9H3 != null) {
                        AbstractC0725wt.b(m10, m9H3, this.b.F[2].c());
                        AbstractC0725wt.b(m9, m10, x9.g);
                        C0425n7 c0425n15 = this.b;
                        if (c0425n15.w) {
                            AbstractC0725wt.b(m11, m10, c0425n15.P);
                            return;
                        }
                        return;
                    }
                    return;
                }
                Z6 z8 = z6Arr[3];
                if (z8.d != null) {
                    M9 m9H4 = AbstractC0725wt.h(z8);
                    if (m9H4 != null) {
                        AbstractC0725wt.b(m9, m9H4, -this.b.F[3].c());
                        AbstractC0725wt.b(m10, m9, -x9.g);
                    }
                    C0425n7 c0425n16 = this.b;
                    if (c0425n16.w) {
                        AbstractC0725wt.b(m11, m10, c0425n16.P);
                        return;
                    }
                    return;
                }
                Z6 z9 = z6Arr[4];
                if (z9.d != null) {
                    M9 m9H5 = AbstractC0725wt.h(z9);
                    if (m9H5 != null) {
                        AbstractC0725wt.b(m11, m9H5, 0);
                        AbstractC0725wt.b(m10, m11, -this.b.P);
                        AbstractC0725wt.b(m9, m10, x9.g);
                        return;
                    }
                    return;
                }
                if ((c0425n13 instanceof AbstractC0463oe) || c0425n13.I == null || c0425n13.g(7).d != null) {
                    return;
                }
                C0425n7 c0425n17 = this.b;
                AbstractC0725wt.b(m10, c0425n17.I.e.h, c0425n17.n());
                AbstractC0725wt.b(m9, m10, x9.g);
                C0425n7 c0425n18 = this.b;
                if (c0425n18.w) {
                    AbstractC0725wt.b(m11, m10, c0425n18.P);
                    return;
                }
                return;
            }
        }
        if (z3 || this.d != 3) {
            x9.b(this);
        } else {
            C0425n7 c0425n19 = this.b;
            int i3 = c0425n19.k;
            if (i3 == 2) {
                C0425n7 c0425n20 = c0425n19.I;
                if (c0425n20 != null) {
                    X9 x10 = c0425n20.e.e;
                    x9.l.add(x10);
                    x10.k.add(x9);
                    x9.b = true;
                    x9.k.add(m10);
                    x9.k.add(m9);
                }
            } else if (i3 == 3 && !c0425n19.r()) {
                C0425n7 c0425n21 = this.b;
                if (c0425n21.j != 3) {
                    X9 x11 = c0425n21.d.e;
                    x9.l.add(x11);
                    x11.k.add(x9);
                    x9.b = true;
                    x9.k.add(m10);
                    x9.k.add(m9);
                }
            }
        }
        C0425n7 c0425n22 = this.b;
        Z6[] z6Arr2 = c0425n22.F;
        Z6 z10 = z6Arr2[2];
        Z6 z11 = z10.d;
        if (z11 != null && z6Arr2[3].d != null) {
            if (c0425n22.r()) {
                m10.f = this.b.F[2].c();
                m9.f = -this.b.F[3].c();
            } else {
                M9 m9H6 = AbstractC0725wt.h(this.b.F[2]);
                M9 m9H7 = AbstractC0725wt.h(this.b.F[3]);
                m9H6.b(this);
                m9H7.b(this);
                this.j = 4;
            }
            if (this.b.w) {
                c(m11, m10, 1, this.l);
            }
        } else if (z11 != null) {
            M9 m9H8 = AbstractC0725wt.h(z10);
            if (m9H8 != null) {
                AbstractC0725wt.b(m10, m9H8, this.b.F[2].c());
                c(m9, m10, 1, x9);
                if (this.b.w) {
                    c(m11, m10, 1, this.l);
                }
                if (this.d == 3) {
                    C0425n7 c0425n23 = this.b;
                    if (c0425n23.L > 0.0f) {
                        Ae ae = c0425n23.d;
                        if (ae.d == 3) {
                            ae.e.k.add(x9);
                            x9.l.add(this.b.d.e);
                            x9.a = this;
                        }
                    }
                }
            }
        } else {
            Z6 z12 = z6Arr2[3];
            if (z12.d != null) {
                M9 m9H9 = AbstractC0725wt.h(z12);
                if (m9H9 != null) {
                    AbstractC0725wt.b(m9, m9H9, -this.b.F[3].c());
                    c(m10, m9, -1, x9);
                    if (this.b.w) {
                        c(m11, m10, 1, this.l);
                    }
                }
            } else {
                Z6 z13 = z6Arr2[4];
                if (z13.d != null) {
                    M9 m9H10 = AbstractC0725wt.h(z13);
                    if (m9H10 != null) {
                        AbstractC0725wt.b(m11, m9H10, 0);
                        c(m10, m11, -1, this.l);
                        c(m9, m10, 1, x9);
                    }
                } else if (!(c0425n22 instanceof AbstractC0463oe) && (c0425n9 = c0425n22.I) != null) {
                    AbstractC0725wt.b(m10, c0425n9.e.h, c0425n22.n());
                    c(m9, m10, 1, x9);
                    if (this.b.w) {
                        c(m11, m10, 1, this.l);
                    }
                    if (this.d == 3) {
                        C0425n7 c0425n24 = this.b;
                        if (c0425n24.L > 0.0f) {
                            Ae ae2 = c0425n24.d;
                            if (ae2.d == 3) {
                                ae2.e.k.add(x9);
                                x9.l.add(this.b.d.e);
                                x9.a = this;
                            }
                        }
                    }
                }
            }
        }
        if (x9.l.size() == 0) {
            x9.c = true;
        }
    }

    @Override // rikka.shizuku.AbstractC0725wt
    public final void e() {
        M9 m9 = this.h;
        if (m9.j) {
            this.b.O = m9.g;
        }
    }

    @Override // rikka.shizuku.AbstractC0725wt
    public final void f() {
        this.c = null;
        this.h.c();
        this.i.c();
        this.k.c();
        this.e.c();
        this.g = false;
    }

    @Override // rikka.shizuku.AbstractC0725wt
    public final boolean k() {
        return this.d != 3 || this.b.k == 0;
    }

    public final void m() {
        this.g = false;
        M9 m9 = this.h;
        m9.c();
        m9.j = false;
        M9 m10 = this.i;
        m10.c();
        m10.j = false;
        M9 m11 = this.k;
        m11.c();
        m11.j = false;
        this.e.j = false;
    }

    public final String toString() {
        return "VerticalRun " + this.b.W;
    }
}
