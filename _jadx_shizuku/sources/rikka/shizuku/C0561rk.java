package rikka.shizuku;

import java.util.Arrays;

/* JADX INFO: renamed from: rikka.shizuku.rk, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0561rk extends C0360l4 {
    public C0565ro[] f;
    public C0565ro[] g;
    public int h;
    public C0531qk i;

    @Override // rikka.shizuku.C0360l4
    public final C0565ro d(boolean[] zArr) {
        int i = -1;
        for (int i2 = 0; i2 < this.h; i2++) {
            C0565ro[] c0565roArr = this.f;
            C0565ro c0565ro = c0565roArr[i2];
            if (!zArr[c0565ro.b]) {
                C0531qk c0531qk = this.i;
                c0531qk.a = c0565ro;
                int i3 = 8;
                if (i != -1) {
                    C0565ro c0565ro2 = c0565roArr[i];
                    while (i3 >= 0) {
                        float f = c0565ro2.h[i3];
                        float f2 = c0531qk.a.h[i3];
                        if (f2 != f) {
                            if (f2 >= f) {
                                break;
                            }
                            i = i2;
                            break;
                            break;
                        }
                        i3--;
                    }
                } else {
                    while (i3 >= 0) {
                        float f3 = c0531qk.a.h[i3];
                        if (f3 > 0.0f) {
                            break;
                        }
                        if (f3 < 0.0f) {
                            i = i2;
                            break;
                        }
                        i3--;
                    }
                }
            }
        }
        if (i == -1) {
            return null;
        }
        return this.f[i];
    }

    @Override // rikka.shizuku.C0360l4
    public final void h(C0360l4 c0360l4, boolean z) {
        C0565ro c0565ro = c0360l4.a;
        if (c0565ro == null) {
            return;
        }
        InterfaceC0329k4 interfaceC0329k4 = c0360l4.d;
        int iK = interfaceC0329k4.k();
        for (int i = 0; i < iK; i++) {
            C0565ro c0565roE = interfaceC0329k4.e(i);
            float fA = interfaceC0329k4.a(i);
            C0531qk c0531qk = this.i;
            c0531qk.a = c0565roE;
            boolean z2 = c0565roE.a;
            float[] fArr = c0565ro.h;
            if (z2) {
                boolean z3 = true;
                for (int i2 = 0; i2 < 9; i2++) {
                    float[] fArr2 = c0531qk.a.h;
                    float f = (fArr[i2] * fA) + fArr2[i2];
                    fArr2[i2] = f;
                    if (Math.abs(f) < 1.0E-4f) {
                        c0531qk.a.h[i2] = 0.0f;
                    } else {
                        z3 = false;
                    }
                }
                if (z3) {
                    c0531qk.b.j(c0531qk.a);
                }
            } else {
                for (int i3 = 0; i3 < 9; i3++) {
                    float f2 = fArr[i3];
                    if (f2 != 0.0f) {
                        float f3 = f2 * fA;
                        if (Math.abs(f3) < 1.0E-4f) {
                            f3 = 0.0f;
                        }
                        c0531qk.a.h[i3] = f3;
                    } else {
                        c0531qk.a.h[i3] = 0.0f;
                    }
                }
                i(c0565roE);
            }
            this.b = (c0360l4.b * fA) + this.b;
        }
        j(c0565ro);
    }

    public final void i(C0565ro c0565ro) {
        int i;
        int i2 = this.h + 1;
        C0565ro[] c0565roArr = this.f;
        if (i2 > c0565roArr.length) {
            C0565ro[] c0565roArr2 = (C0565ro[]) Arrays.copyOf(c0565roArr, c0565roArr.length * 2);
            this.f = c0565roArr2;
            this.g = (C0565ro[]) Arrays.copyOf(c0565roArr2, c0565roArr2.length * 2);
        }
        C0565ro[] c0565roArr3 = this.f;
        int i3 = this.h;
        c0565roArr3[i3] = c0565ro;
        int i4 = i3 + 1;
        this.h = i4;
        if (i4 > 1 && c0565roArr3[i3].b > c0565ro.b) {
            int i5 = 0;
            while (true) {
                i = this.h;
                if (i5 >= i) {
                    break;
                }
                this.g[i5] = this.f[i5];
                i5++;
            }
            Arrays.sort(this.g, 0, i, new Q7(3));
            for (int i6 = 0; i6 < this.h; i6++) {
                this.f[i6] = this.g[i6];
            }
        }
        c0565ro.a = true;
        c0565ro.a(this);
    }

    public final void j(C0565ro c0565ro) {
        int i = 0;
        while (i < this.h) {
            if (this.f[i] == c0565ro) {
                while (true) {
                    int i2 = this.h;
                    if (i >= i2 - 1) {
                        this.h = i2 - 1;
                        c0565ro.a = false;
                        return;
                    } else {
                        C0565ro[] c0565roArr = this.f;
                        int i3 = i + 1;
                        c0565roArr[i] = c0565roArr[i3];
                        i = i3;
                    }
                }
            } else {
                i++;
            }
        }
    }

    @Override // rikka.shizuku.C0360l4
    public final String toString() {
        String str = " goal -> (" + this.b + ") : ";
        for (int i = 0; i < this.h; i++) {
            C0565ro c0565ro = this.f[i];
            C0531qk c0531qk = this.i;
            c0531qk.a = c0565ro;
            str = str + c0531qk + " ";
        }
        return str;
    }
}
