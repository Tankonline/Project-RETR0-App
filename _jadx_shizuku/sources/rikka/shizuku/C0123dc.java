package rikka.shizuku;

/* JADX INFO: renamed from: rikka.shizuku.dc, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0123dc {
    public int a;
    public Z6 d;
    public Z6 e;
    public Z6 f;
    public Z6 g;
    public int h;
    public int i;
    public int j;
    public int k;
    public int q;
    public final /* synthetic */ C0153ec r;
    public C0425n7 b = null;
    public int c = 0;
    public int l = 0;
    public int m = 0;
    public int n = 0;
    public int o = 0;
    public int p = 0;

    public C0123dc(C0153ec c0153ec, int i, Z6 z6, Z6 z7, Z6 z8, Z6 z9, int i2) {
        this.r = c0153ec;
        this.h = 0;
        this.i = 0;
        this.j = 0;
        this.k = 0;
        this.q = 0;
        this.a = i;
        this.d = z6;
        this.e = z7;
        this.f = z8;
        this.g = z9;
        this.h = c0153ec.j0;
        this.i = c0153ec.f0;
        this.j = c0153ec.k0;
        this.k = c0153ec.g0;
        this.q = i2;
    }

    public final void a(C0425n7 c0425n7) {
        int i = this.a;
        C0153ec c0153ec = this.r;
        if (i == 0) {
            int iD = c0153ec.D(c0425n7, this.q);
            if (c0425n7.c0[0] == 3) {
                this.p++;
                iD = 0;
            }
            this.l = iD + (c0425n7.V != 8 ? c0153ec.C0 : 0) + this.l;
            int iC = c0153ec.C(c0425n7, this.q);
            if (this.b == null || this.c < iC) {
                this.b = c0425n7;
                this.c = iC;
                this.m = iC;
            }
        } else {
            int iD2 = c0153ec.D(c0425n7, this.q);
            int iC2 = c0153ec.C(c0425n7, this.q);
            if (c0425n7.c0[1] == 3) {
                this.p++;
                iC2 = 0;
            }
            this.m = iC2 + (c0425n7.V != 8 ? c0153ec.D0 : 0) + this.m;
            if (this.b == null || this.c < iD2) {
                this.b = c0425n7;
                this.c = iD2;
                this.l = iD2;
            }
        }
        this.o++;
    }

    public final void b(int i, boolean z, boolean z2) {
        C0153ec c0153ec;
        int i2;
        C0425n7 c0425n7;
        char c;
        int i3;
        int i4;
        int i5;
        int i6 = this.o;
        int i7 = 0;
        while (true) {
            c0153ec = this.r;
            if (i7 >= i6 || (i5 = this.n + i7) >= c0153ec.O0) {
                break;
            }
            C0425n7 c0425n8 = c0153ec.N0[i5];
            if (c0425n8 != null) {
                c0425n8.t();
            }
            i7++;
        }
        if (i6 == 0 || this.b == null) {
            return;
        }
        boolean z3 = z2 && i == 0;
        int i8 = -1;
        int i9 = -1;
        for (int i10 = 0; i10 < i6; i10++) {
            int i11 = this.n + (z ? (i6 - 1) - i10 : i10);
            if (i11 >= c0153ec.O0) {
                break;
            }
            if (c0153ec.N0[i11].V == 0) {
                if (i8 == -1) {
                    i8 = i10;
                }
                i9 = i10;
            }
        }
        if (this.a != 0) {
            C0425n7 c0425n9 = this.b;
            c0425n9.X = c0153ec.q0;
            int i12 = this.h;
            if (i > 0) {
                i12 += c0153ec.C0;
            }
            Z6 z6 = c0425n9.x;
            Z6 z7 = c0425n9.z;
            if (z) {
                z7.a(this.f, i12);
                if (z2) {
                    z6.a(this.d, this.j);
                }
                if (i > 0) {
                    this.f.b.x.a(z7, 0);
                }
            } else {
                z6.a(this.d, i12);
                if (z2) {
                    z7.a(this.f, this.j);
                }
                if (i > 0) {
                    this.d.b.z.a(z6, 0);
                }
            }
            int i13 = 0;
            C0425n7 c0425n10 = null;
            while (i13 < i6) {
                int i14 = this.n + i13;
                if (i14 >= c0153ec.O0) {
                    return;
                }
                C0425n7 c0425n11 = c0153ec.N0[i14];
                if (i13 == 0) {
                    c0425n11.e(c0425n11.y, this.e, this.i);
                    int i15 = c0153ec.r0;
                    float f = c0153ec.x0;
                    if (this.n == 0) {
                        int i16 = c0153ec.t0;
                        i2 = -1;
                        if (i16 != -1) {
                            f = c0153ec.z0;
                        }
                        i15 = i16;
                        c0425n11.Y = i15;
                        c0425n11.T = f;
                    } else {
                        i2 = -1;
                    }
                    if (z2 && (i16 = c0153ec.v0) != i2) {
                        f = c0153ec.B0;
                        i15 = i16;
                    }
                    c0425n11.Y = i15;
                    c0425n11.T = f;
                }
                if (i13 == i6 - 1) {
                    c0425n11.e(c0425n11.A, this.g, this.k);
                }
                if (c0425n10 != null) {
                    Z6 z8 = c0425n11.y;
                    int i17 = c0153ec.D0;
                    Z6 z9 = c0425n10.A;
                    z8.a(z9, i17);
                    Z6 z10 = c0425n11.y;
                    if (i13 == i8) {
                        int i18 = this.i;
                        if (z10.f()) {
                            z10.f = i18;
                        }
                    }
                    z9.a(z10, 0);
                    if (i13 == i9 + 1) {
                        int i19 = this.k;
                        if (z9.f()) {
                            z9.f = i19;
                        }
                    }
                }
                if (c0425n11 != c0425n9) {
                    if (z) {
                        int i20 = c0153ec.E0;
                        if (i20 == 0) {
                            c0425n11.z.a(z7, 0);
                        } else if (i20 == 1) {
                            c0425n11.x.a(z6, 0);
                        } else if (i20 == 2) {
                            c0425n11.x.a(z6, 0);
                            c0425n11.z.a(z7, 0);
                        }
                    } else {
                        int i21 = c0153ec.E0;
                        if (i21 == 0) {
                            c0425n11.x.a(z6, 0);
                        } else if (i21 == 1) {
                            c0425n11.z.a(z7, 0);
                        } else if (i21 == 2) {
                            if (z3) {
                                c0425n11.x.a(this.d, this.h);
                                c0425n11.z.a(this.f, this.j);
                            } else {
                                c0425n11.x.a(z6, 0);
                                c0425n11.z.a(z7, 0);
                            }
                        }
                    }
                }
                i13++;
                c0425n10 = c0425n11;
            }
            return;
        }
        C0425n7 c0425n12 = this.b;
        c0425n12.Y = c0153ec.r0;
        int i22 = this.i;
        if (i > 0) {
            i22 += c0153ec.D0;
        }
        Z6 z11 = this.e;
        Z6 z12 = c0425n12.y;
        z12.a(z11, i22);
        Z6 z13 = c0425n12.A;
        if (z2) {
            z13.a(this.g, this.k);
        }
        if (i > 0) {
            this.e.b.A.a(z12, 0);
        }
        if (c0153ec.F0 != 3 || c0425n12.w) {
            c0425n7 = c0425n12;
            break;
        }
        int i23 = 0;
        while (true) {
            if (i23 < i6) {
                int i24 = this.n + (z ? (i6 - 1) - i23 : i23);
                if (i24 < c0153ec.O0) {
                    c0425n7 = c0153ec.N0[i24];
                    if (c0425n7.w) {
                        break;
                    } else {
                        i23++;
                    }
                }
            }
            c0425n7 = c0425n12;
            break;
        }
        int i25 = 0;
        C0425n7 c0425n13 = null;
        while (i25 < i6) {
            int i26 = z ? (i6 - 1) - i25 : i25;
            int i27 = this.n + i26;
            if (i27 >= c0153ec.O0) {
                return;
            }
            C0425n7 c0425n14 = c0153ec.N0[i27];
            if (i25 == 0) {
                c0425n14.e(c0425n14.x, this.d, this.h);
            }
            if (i26 == 0) {
                int i28 = c0153ec.q0;
                float f2 = c0153ec.w0;
                if (this.n == 0) {
                    int i29 = c0153ec.s0;
                    i3 = -1;
                    if (i29 != -1) {
                        f2 = c0153ec.y0;
                    }
                    i4 = i29;
                    c0425n14.X = i4;
                    c0425n14.S = f2;
                } else {
                    i3 = -1;
                }
                if (!z2 || (i29 = c0153ec.u0) == i3) {
                    i4 = i28;
                } else {
                    f2 = c0153ec.A0;
                    i4 = i29;
                }
                c0425n14.X = i4;
                c0425n14.S = f2;
            }
            if (i25 == i6 - 1) {
                c0425n14.e(c0425n14.z, this.f, this.j);
            }
            if (c0425n13 != null) {
                Z6 z14 = c0425n14.x;
                int i30 = c0153ec.C0;
                Z6 z15 = c0425n13.z;
                z14.a(z15, i30);
                Z6 z16 = c0425n14.x;
                if (i25 == i8) {
                    int i31 = this.h;
                    if (z16.f()) {
                        z16.f = i31;
                    }
                }
                z15.a(z16, 0);
                if (i25 == i9 + 1) {
                    int i32 = this.j;
                    if (z15.f()) {
                        z15.f = i32;
                    }
                }
            }
            if (c0425n14 != c0425n12) {
                int i33 = c0153ec.F0;
                c = 3;
                if (i33 == 3 && c0425n7.w && c0425n14 != c0425n7 && c0425n14.w) {
                    c0425n14.B.a(c0425n7.B, 0);
                } else if (i33 == 0) {
                    c0425n14.y.a(z12, 0);
                } else if (i33 == 1) {
                    c0425n14.A.a(z13, 0);
                } else if (z3) {
                    c0425n14.y.a(this.e, this.i);
                    c0425n14.A.a(this.g, this.k);
                } else {
                    c0425n14.y.a(z12, 0);
                    c0425n14.A.a(z13, 0);
                }
            } else {
                c = 3;
            }
            i25++;
            c0425n13 = c0425n14;
        }
    }

    public final int c() {
        return this.a == 1 ? this.m - this.r.D0 : this.m;
    }

    public final int d() {
        return this.a == 0 ? this.l - this.r.C0 : this.l;
    }

    public final void e(int i) {
        C0153ec c0153ec;
        int i2;
        int i3 = this.p;
        if (i3 == 0) {
            return;
        }
        int i4 = this.o;
        int i5 = i / i3;
        int i6 = 0;
        while (true) {
            c0153ec = this.r;
            if (i6 >= i4 || (i2 = this.n + i6) >= c0153ec.O0) {
                break;
            }
            C0425n7 c0425n7 = c0153ec.N0[i2];
            if (this.a == 0) {
                if (c0425n7 != null) {
                    int[] iArr = c0425n7.c0;
                    if (iArr[0] == 3 && c0425n7.j == 0) {
                        c0153ec.E(1, i5, iArr[1], c0425n7.i(), c0425n7);
                    }
                }
            } else if (c0425n7 != null) {
                int[] iArr2 = c0425n7.c0;
                if (iArr2[1] == 3 && c0425n7.k == 0) {
                    int i7 = iArr2[0];
                    int i8 = i5;
                    c0153ec.E(i7, c0425n7.l(), 1, i8, c0425n7);
                    i5 = i8;
                }
            }
            i6++;
        }
        this.l = 0;
        this.m = 0;
        this.b = null;
        this.c = 0;
        int i9 = this.o;
        for (int i10 = 0; i10 < i9; i10++) {
            int i11 = this.n + i10;
            if (i11 >= c0153ec.O0) {
                return;
            }
            C0425n7 c0425n8 = c0153ec.N0[i11];
            if (this.a == 0) {
                int iL = c0425n8.l();
                int i12 = c0153ec.C0;
                if (c0425n8.V == 8) {
                    i12 = 0;
                }
                this.l = iL + i12 + this.l;
                int iC = c0153ec.C(c0425n8, this.q);
                if (this.b == null || this.c < iC) {
                    this.b = c0425n8;
                    this.c = iC;
                    this.m = iC;
                }
            } else {
                int iD = c0153ec.D(c0425n8, this.q);
                int iC2 = c0153ec.C(c0425n8, this.q);
                int i13 = c0153ec.D0;
                if (c0425n8.V == 8) {
                    i13 = 0;
                }
                this.m = iC2 + i13 + this.m;
                if (this.b == null || this.c < iD) {
                    this.b = c0425n8;
                    this.c = iD;
                    this.l = iD;
                }
            }
        }
    }

    public final void f(int i, Z6 z6, Z6 z7, Z6 z8, Z6 z9, int i2, int i3, int i4, int i5, int i6) {
        this.a = i;
        this.d = z6;
        this.e = z7;
        this.f = z8;
        this.g = z9;
        this.h = i2;
        this.i = i3;
        this.j = i4;
        this.k = i5;
        this.q = i6;
    }
}
