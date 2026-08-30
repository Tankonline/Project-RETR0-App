package rikka.shizuku;

import java.util.ArrayList;

/* JADX INFO: renamed from: rikka.shizuku.ec, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0153ec extends AbstractC0463oe {
    public float A0;
    public float B0;
    public int C0;
    public int D0;
    public int E0;
    public int F0;
    public int G0;
    public int H0;
    public int I0;
    public ArrayList J0;
    public C0425n7[] K0;
    public C0425n7[] L0;
    public int[] M0;
    public C0425n7[] N0;
    public int O0;
    public int f0;
    public int g0;
    public int h0;
    public int i0;
    public int j0;
    public int k0;
    public boolean l0;
    public int m0;
    public int n0;
    public Z4 o0;
    public C0148e7 p0;
    public int q0;
    public int r0;
    public int s0;
    public int t0;
    public int u0;
    public int v0;
    public float w0;
    public float x0;
    public float y0;
    public float z0;

    @Override // rikka.shizuku.AbstractC0463oe
    public final void B() {
        for (int i = 0; i < this.e0; i++) {
            C0425n7 c0425n7 = this.d0[i];
        }
    }

    public final int C(C0425n7 c0425n7, int i) {
        C0425n7 c0425n8;
        if (c0425n7 != null) {
            int[] iArr = c0425n7.c0;
            if (iArr[1] == 3) {
                int i2 = c0425n7.k;
                if (i2 != 0) {
                    if (i2 == 2) {
                        int i3 = (int) (c0425n7.r * i);
                        if (i3 != c0425n7.i()) {
                            E(iArr[0], c0425n7.l(), 1, i3, c0425n7);
                        }
                        return i3;
                    }
                    c0425n8 = c0425n7;
                    if (i2 == 1) {
                        return c0425n8.i();
                    }
                    if (i2 == 3) {
                        return (int) ((c0425n8.l() * c0425n8.L) + 0.5f);
                    }
                }
            } else {
                c0425n8 = c0425n7;
            }
            return c0425n8.i();
        }
        return 0;
    }

    public final int D(C0425n7 c0425n7, int i) {
        C0425n7 c0425n8;
        if (c0425n7 != null) {
            int[] iArr = c0425n7.c0;
            if (iArr[0] == 3) {
                int i2 = c0425n7.j;
                if (i2 != 0) {
                    if (i2 == 2) {
                        int i3 = (int) (c0425n7.o * i);
                        if (i3 != c0425n7.l()) {
                            E(1, i3, iArr[1], c0425n7.i(), c0425n7);
                        }
                        return i3;
                    }
                    c0425n8 = c0425n7;
                    if (i2 == 1) {
                        return c0425n8.l();
                    }
                    if (i2 == 3) {
                        return (int) ((c0425n8.i() * c0425n8.L) + 0.5f);
                    }
                }
            } else {
                c0425n8 = c0425n7;
            }
            return c0425n8.l();
        }
        return 0;
    }

    public final void E(int i, int i2, int i3, int i4, C0425n7 c0425n7) {
        C0148e7 c0148e7;
        C0425n7 c0425n8;
        while (true) {
            c0148e7 = this.p0;
            if (c0148e7 != null || (c0425n8 = this.I) == null) {
                break;
            } else {
                this.p0 = ((C0456o7) c0425n8).g0;
            }
        }
        Z4 z4 = this.o0;
        z4.a = i;
        z4.b = i3;
        z4.c = i2;
        z4.d = i4;
        c0148e7.a(c0425n7, z4);
        c0425n7.y(z4.e);
        c0425n7.v(z4.f);
        c0425n7.w = z4.h;
        int i5 = z4.g;
        c0425n7.P = i5;
        c0425n7.w = i5 > 0;
    }

    @Override // rikka.shizuku.C0425n7
    public final void a(Lg lg) {
        C0425n7 c0425n7;
        super.a(lg);
        C0425n7 c0425n8 = this.I;
        boolean z = c0425n8 != null ? ((C0456o7) c0425n8).h0 : false;
        int i = this.G0;
        ArrayList arrayList = this.J0;
        if (i != 0) {
            if (i == 1) {
                int size = arrayList.size();
                int i2 = 0;
                while (i2 < size) {
                    ((C0123dc) arrayList.get(i2)).b(i2, z, i2 == size + (-1));
                    i2++;
                }
            } else if (i == 2 && this.M0 != null && this.L0 != null && this.K0 != null) {
                for (int i3 = 0; i3 < this.O0; i3++) {
                    this.N0[i3].t();
                }
                int[] iArr = this.M0;
                int i4 = iArr[0];
                int i5 = iArr[1];
                C0425n7 c0425n9 = null;
                for (int i6 = 0; i6 < i4; i6++) {
                    C0425n7 c0425n10 = this.L0[z ? (i4 - i6) - 1 : i6];
                    if (c0425n10 != null && c0425n10.V != 8) {
                        Z6 z6 = c0425n10.x;
                        if (i6 == 0) {
                            c0425n10.e(z6, this.x, this.j0);
                            c0425n10.X = this.q0;
                            c0425n10.S = this.w0;
                        }
                        if (i6 == i4 - 1) {
                            c0425n10.e(c0425n10.z, this.z, this.k0);
                        }
                        if (i6 > 0) {
                            c0425n10.e(z6, c0425n9.z, this.C0);
                            c0425n9.e(c0425n9.z, z6, 0);
                        }
                        c0425n9 = c0425n10;
                    }
                }
                for (int i7 = 0; i7 < i5; i7++) {
                    C0425n7 c0425n11 = this.K0[i7];
                    if (c0425n11 != null && c0425n11.V != 8) {
                        Z6 z7 = c0425n11.y;
                        if (i7 == 0) {
                            c0425n11.e(z7, this.y, this.f0);
                            c0425n11.Y = this.r0;
                            c0425n11.T = this.x0;
                        }
                        if (i7 == i5 - 1) {
                            c0425n11.e(c0425n11.A, this.A, this.g0);
                        }
                        if (i7 > 0) {
                            c0425n11.e(z7, c0425n9.A, this.D0);
                            c0425n9.e(c0425n9.A, z7, 0);
                        }
                        c0425n9 = c0425n11;
                    }
                }
                for (int i8 = 0; i8 < i4; i8++) {
                    for (int i9 = 0; i9 < i5; i9++) {
                        int i10 = (i9 * i4) + i8;
                        if (this.I0 == 1) {
                            i10 = (i8 * i5) + i9;
                        }
                        C0425n7[] c0425n7Arr = this.N0;
                        if (i10 < c0425n7Arr.length && (c0425n7 = c0425n7Arr[i10]) != null && c0425n7.V != 8) {
                            C0425n7 c0425n12 = this.L0[i8];
                            C0425n7 c0425n13 = this.K0[i9];
                            if (c0425n7 != c0425n12) {
                                c0425n7.e(c0425n7.x, c0425n12.x, 0);
                                c0425n7.e(c0425n7.z, c0425n12.z, 0);
                            }
                            if (c0425n7 != c0425n13) {
                                c0425n7.e(c0425n7.y, c0425n13.y, 0);
                                c0425n7.e(c0425n7.A, c0425n13.A, 0);
                            }
                        }
                    }
                }
            }
        } else if (arrayList.size() > 0) {
            ((C0123dc) arrayList.get(0)).b(0, z, true);
        }
        this.l0 = false;
    }
}
