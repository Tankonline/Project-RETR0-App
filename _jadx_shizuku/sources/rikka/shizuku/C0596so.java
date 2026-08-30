package rikka.shizuku;

import java.util.Arrays;

/* JADX INFO: renamed from: rikka.shizuku.so, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0596so implements InterfaceC0329k4 {
    public int a = 16;
    public final int[] b = new int[16];
    public int[] c = new int[16];
    public int[] d = new int[16];
    public float[] e = new float[16];
    public int[] f = new int[16];
    public int[] g = new int[16];
    public int h = 0;
    public int i = -1;
    public final Kg j;
    public final Xn k;

    public C0596so(Kg kg, Xn xn) {
        this.j = kg;
        this.k = xn;
        clear();
    }

    @Override // rikka.shizuku.InterfaceC0329k4
    public final float a(int i) {
        int i2 = this.h;
        int i3 = this.i;
        for (int i4 = 0; i4 < i2; i4++) {
            if (i4 == i) {
                return this.e[i3];
            }
            i3 = this.g[i3];
            if (i3 == -1) {
                return 0.0f;
            }
        }
        return 0.0f;
    }

    @Override // rikka.shizuku.InterfaceC0329k4
    public final void b(C0565ro c0565ro, float f) {
        if (f > -0.001f && f < 0.001f) {
            j(c0565ro, true);
            return;
        }
        int i = 0;
        if (this.h == 0) {
            m(0, c0565ro, f);
            l(c0565ro, 0);
            this.i = 0;
            return;
        }
        int iN = n(c0565ro);
        if (iN != -1) {
            this.e[iN] = f;
            return;
        }
        int i2 = this.h + 1;
        int i3 = this.a;
        if (i2 >= i3) {
            int i4 = i3 * 2;
            this.d = Arrays.copyOf(this.d, i4);
            this.e = Arrays.copyOf(this.e, i4);
            this.f = Arrays.copyOf(this.f, i4);
            this.g = Arrays.copyOf(this.g, i4);
            this.c = Arrays.copyOf(this.c, i4);
            for (int i5 = this.a; i5 < i4; i5++) {
                this.d[i5] = -1;
                this.c[i5] = -1;
            }
            this.a = i4;
        }
        int i6 = this.h;
        int i7 = this.i;
        int i8 = -1;
        for (int i9 = 0; i9 < i6; i9++) {
            int i10 = this.d[i7];
            int i11 = c0565ro.b;
            if (i10 == i11) {
                this.e[i7] = f;
                return;
            }
            if (i10 < i11) {
                i8 = i7;
            }
            i7 = this.g[i7];
            if (i7 == -1) {
                break;
            }
        }
        while (true) {
            if (i >= this.a) {
                i = -1;
                break;
            } else if (this.d[i] == -1) {
                break;
            } else {
                i++;
            }
        }
        m(i, c0565ro, f);
        if (i8 != -1) {
            this.f[i] = i8;
            int[] iArr = this.g;
            iArr[i] = iArr[i8];
            iArr[i8] = i;
        } else {
            this.f[i] = -1;
            if (this.h > 0) {
                this.g[i] = this.i;
                this.i = i;
            } else {
                this.g[i] = -1;
            }
        }
        int i12 = this.g[i];
        if (i12 != -1) {
            this.f[i12] = i;
        }
        l(c0565ro, i);
    }

    @Override // rikka.shizuku.InterfaceC0329k4
    public final float c(C0360l4 c0360l4, boolean z) {
        float fG = g(c0360l4.a);
        j(c0360l4.a, z);
        C0596so c0596so = (C0596so) c0360l4.d;
        int i = c0596so.h;
        int i2 = 0;
        int i3 = 0;
        while (i2 < i) {
            int i4 = c0596so.d[i3];
            if (i4 != -1) {
                h(((C0565ro[]) this.k.d)[i4], c0596so.e[i3] * fG, z);
                i2++;
            }
            i3++;
        }
        return fG;
    }

    @Override // rikka.shizuku.InterfaceC0329k4
    public final void clear() {
        int i = this.h;
        for (int i2 = 0; i2 < i; i2++) {
            C0565ro c0565roE = e(i2);
            if (c0565roE != null) {
                c0565roE.b(this.j);
            }
        }
        for (int i3 = 0; i3 < this.a; i3++) {
            this.d[i3] = -1;
            this.c[i3] = -1;
        }
        for (int i4 = 0; i4 < 16; i4++) {
            this.b[i4] = -1;
        }
        this.h = 0;
        this.i = -1;
    }

    @Override // rikka.shizuku.InterfaceC0329k4
    public final boolean d(C0565ro c0565ro) {
        return n(c0565ro) != -1;
    }

    @Override // rikka.shizuku.InterfaceC0329k4
    public final C0565ro e(int i) {
        int i2 = this.h;
        if (i2 == 0) {
            return null;
        }
        int i3 = this.i;
        for (int i4 = 0; i4 < i2; i4++) {
            if (i4 == i && i3 != -1) {
                return ((C0565ro[]) this.k.d)[this.d[i3]];
            }
            i3 = this.g[i3];
            if (i3 == -1) {
                break;
            }
        }
        return null;
    }

    @Override // rikka.shizuku.InterfaceC0329k4
    public final void f(float f) {
        int i = this.h;
        int i2 = this.i;
        for (int i3 = 0; i3 < i; i3++) {
            float[] fArr = this.e;
            fArr[i2] = fArr[i2] / f;
            i2 = this.g[i2];
            if (i2 == -1) {
                return;
            }
        }
    }

    @Override // rikka.shizuku.InterfaceC0329k4
    public final float g(C0565ro c0565ro) {
        int iN = n(c0565ro);
        if (iN != -1) {
            return this.e[iN];
        }
        return 0.0f;
    }

    @Override // rikka.shizuku.InterfaceC0329k4
    public final void h(C0565ro c0565ro, float f, boolean z) {
        if (f <= -0.001f || f >= 0.001f) {
            int iN = n(c0565ro);
            if (iN == -1) {
                b(c0565ro, f);
                return;
            }
            float[] fArr = this.e;
            float f2 = fArr[iN] + f;
            fArr[iN] = f2;
            if (f2 <= -0.001f || f2 >= 0.001f) {
                return;
            }
            fArr[iN] = 0.0f;
            j(c0565ro, z);
        }
    }

    @Override // rikka.shizuku.InterfaceC0329k4
    public final void i() {
        int i = this.h;
        int i2 = this.i;
        for (int i3 = 0; i3 < i; i3++) {
            float[] fArr = this.e;
            fArr[i2] = fArr[i2] * (-1.0f);
            i2 = this.g[i2];
            if (i2 == -1) {
                return;
            }
        }
    }

    @Override // rikka.shizuku.InterfaceC0329k4
    public final float j(C0565ro c0565ro, boolean z) {
        int[] iArr;
        int i;
        int iN = n(c0565ro);
        if (iN == -1) {
            return 0.0f;
        }
        int i2 = c0565ro.b;
        int i3 = i2 % 16;
        int[] iArr2 = this.b;
        int i4 = iArr2[i3];
        if (i4 != -1) {
            if (this.d[i4] == i2) {
                int[] iArr3 = this.c;
                iArr2[i3] = iArr3[i4];
                iArr3[i4] = -1;
            } else {
                while (true) {
                    iArr = this.c;
                    i = iArr[i4];
                    if (i == -1 || this.d[i] == i2) {
                        break;
                    }
                    i4 = i;
                }
                if (i != -1 && this.d[i] == i2) {
                    iArr[i4] = iArr[i];
                    iArr[i] = -1;
                }
            }
        }
        float f = this.e[iN];
        if (this.i == iN) {
            this.i = this.g[iN];
        }
        this.d[iN] = -1;
        int[] iArr4 = this.f;
        int i5 = iArr4[iN];
        if (i5 != -1) {
            int[] iArr5 = this.g;
            iArr5[i5] = iArr5[iN];
        }
        int i6 = this.g[iN];
        if (i6 != -1) {
            iArr4[i6] = iArr4[iN];
        }
        this.h--;
        c0565ro.k--;
        if (z) {
            c0565ro.b(this.j);
        }
        return f;
    }

    @Override // rikka.shizuku.InterfaceC0329k4
    public final int k() {
        return this.h;
    }

    public final void l(C0565ro c0565ro, int i) {
        int[] iArr;
        int i2 = c0565ro.b % 16;
        int[] iArr2 = this.b;
        int i3 = iArr2[i2];
        if (i3 == -1) {
            iArr2[i2] = i;
        } else {
            while (true) {
                iArr = this.c;
                int i4 = iArr[i3];
                if (i4 == -1) {
                    break;
                } else {
                    i3 = i4;
                }
            }
            iArr[i3] = i;
        }
        this.c[i] = -1;
    }

    public final void m(int i, C0565ro c0565ro, float f) {
        this.d[i] = c0565ro.b;
        this.e[i] = f;
        this.f[i] = -1;
        this.g[i] = -1;
        c0565ro.a(this.j);
        c0565ro.k++;
        this.h++;
    }

    public final int n(C0565ro c0565ro) {
        if (this.h == 0) {
            return -1;
        }
        int i = c0565ro.b;
        int i2 = this.b[i % 16];
        if (i2 == -1) {
            return -1;
        }
        if (this.d[i2] == i) {
            return i2;
        }
        do {
            i2 = this.c[i2];
            if (i2 == -1) {
                break;
            }
        } while (this.d[i2] != i);
        if (i2 != -1 && this.d[i2] == i) {
            return i2;
        }
        return -1;
    }

    public final String toString() {
        String strC = hashCode() + " { ";
        int i = this.h;
        for (int i2 = 0; i2 < i; i2++) {
            C0565ro c0565roE = e(i2);
            if (c0565roE != null) {
                String str = strC + c0565roE + " = " + a(i2) + " ";
                int iN = n(c0565roE);
                String strC2 = AbstractC0535qo.c(str, "[p: ");
                int i3 = this.f[iN];
                Xn xn = this.k;
                String strC3 = AbstractC0535qo.c(i3 != -1 ? strC2 + ((C0565ro[]) xn.d)[this.d[this.f[iN]]] : AbstractC0535qo.c(strC2, "none"), ", n: ");
                strC = AbstractC0535qo.c(this.g[iN] != -1 ? strC3 + ((C0565ro[]) xn.d)[this.d[this.g[iN]]] : AbstractC0535qo.c(strC3, "none"), "]");
            }
        }
        return AbstractC0535qo.c(strC, " }");
    }
}
