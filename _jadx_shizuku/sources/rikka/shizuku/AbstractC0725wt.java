package rikka.shizuku;

/* JADX INFO: renamed from: rikka.shizuku.wt, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0725wt implements K9 {
    public int a;
    public C0425n7 b;
    public C0316jm c;
    public int d;
    public final X9 e = new X9(this);
    public int f = 0;
    public boolean g = false;
    public final M9 h = new M9(this);
    public final M9 i = new M9(this);
    public int j = 1;

    public AbstractC0725wt(C0425n7 c0425n7) {
        this.b = c0425n7;
    }

    public static void b(M9 m9, M9 m10, int i) {
        m9.l.add(m10);
        m9.f = i;
        m10.k.add(m9);
    }

    public static M9 h(Z6 z6) {
        Z6 z7 = z6.d;
        if (z7 == null) {
            return null;
        }
        int iJ = AbstractC0535qo.j(z7.c);
        C0425n7 c0425n7 = z7.b;
        if (iJ == 1) {
            return c0425n7.d.h;
        }
        if (iJ == 2) {
            return c0425n7.e.h;
        }
        if (iJ == 3) {
            return c0425n7.d.i;
        }
        if (iJ == 4) {
            return c0425n7.e.i;
        }
        if (iJ != 5) {
            return null;
        }
        return c0425n7.e.k;
    }

    public static M9 i(Z6 z6, int i) {
        Z6 z7 = z6.d;
        if (z7 == null) {
            return null;
        }
        C0425n7 c0425n7 = z7.b;
        AbstractC0725wt abstractC0725wt = i == 0 ? c0425n7.d : c0425n7.e;
        int iJ = AbstractC0535qo.j(z7.c);
        if (iJ == 1 || iJ == 2) {
            return abstractC0725wt.h;
        }
        if (iJ == 3 || iJ == 4) {
            return abstractC0725wt.i;
        }
        return null;
    }

    public final void c(M9 m9, M9 m10, int i, X9 x9) {
        m9.l.add(m10);
        m9.l.add(this.e);
        m9.h = i;
        m9.i = x9;
        m10.k.add(m9);
        x9.k.add(m9);
    }

    public abstract void d();

    public abstract void e();

    public abstract void f();

    public final int g(int i, int i2) {
        if (i2 == 0) {
            C0425n7 c0425n7 = this.b;
            int i3 = c0425n7.n;
            int iMax = Math.max(c0425n7.m, i);
            if (i3 > 0) {
                iMax = Math.min(i3, i);
            }
            if (iMax != i) {
                return iMax;
            }
        } else {
            C0425n7 c0425n8 = this.b;
            int i4 = c0425n8.q;
            int iMax2 = Math.max(c0425n8.p, i);
            if (i4 > 0) {
                iMax2 = Math.min(i4, i);
            }
            if (iMax2 != i) {
                return iMax2;
            }
        }
        return i;
    }

    public long j() {
        X9 x9 = this.e;
        if (x9.j) {
            return x9.g;
        }
        return 0L;
    }

    public abstract boolean k();

    public final void l(Z6 z6, Z6 z7, int i) {
        M9 m9H = h(z6);
        M9 m9H2 = h(z7);
        if (m9H.j && m9H2.j) {
            int iC = z6.c() + m9H.g;
            int iC2 = m9H2.g - z7.c();
            int i2 = iC2 - iC;
            X9 x9 = this.e;
            if (!x9.j && this.d == 3) {
                int i3 = this.a;
                if (i3 == 0) {
                    x9.d(g(i2, i));
                } else if (i3 == 1) {
                    x9.d(Math.min(g(x9.m, i), i2));
                } else if (i3 == 2) {
                    C0425n7 c0425n7 = this.b;
                    C0425n7 c0425n8 = c0425n7.I;
                    if (c0425n8 != null) {
                        X9 x10 = (i == 0 ? c0425n8.d : c0425n8.e).e;
                        if (x10.j) {
                            x9.d(g((int) ((x10.g * (i == 0 ? c0425n7.o : c0425n7.r)) + 0.5f), i));
                        }
                    }
                } else if (i3 == 3) {
                    C0425n7 c0425n9 = this.b;
                    AbstractC0725wt abstractC0725wt = c0425n9.d;
                    int i4 = abstractC0725wt.d;
                    C0600ss c0600ss = c0425n9.e;
                    if (i4 != 3 || abstractC0725wt.a != 3 || c0600ss.d != 3 || c0600ss.a != 3) {
                        if (i == 0) {
                            abstractC0725wt = c0600ss;
                        }
                        X9 x11 = abstractC0725wt.e;
                        if (x11.j) {
                            float f = c0425n9.L;
                            x9.d(i == 1 ? (int) ((x11.g / f) + 0.5f) : (int) ((f * x11.g) + 0.5f));
                        }
                    }
                }
            }
            if (x9.j) {
                int i5 = x9.g;
                M9 m9 = this.i;
                M9 m10 = this.h;
                if (i5 == i2) {
                    m10.d(iC);
                    m9.d(iC2);
                    return;
                }
                C0425n7 c0425n10 = this.b;
                float f2 = i == 0 ? c0425n10.S : c0425n10.T;
                if (m9H == m9H2) {
                    iC = m9H.g;
                    iC2 = m9H2.g;
                    f2 = 0.5f;
                }
                m10.d((int) ((((iC2 - iC) - i5) * f2) + iC + 0.5f));
                m9.d(m10.g + x9.g);
            }
        }
    }
}
