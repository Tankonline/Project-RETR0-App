package rikka.shizuku;

import java.util.Arrays;

/* JADX INFO: renamed from: rikka.shizuku.e4, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0145e4 implements InterfaceC0329k4 {
    public final C0360l4 b;
    public final Xn c;
    public int a = 0;
    public int d = 8;
    public int[] e = new int[8];
    public int[] f = new int[8];
    public float[] g = new float[8];
    public int h = -1;
    public int i = -1;
    public boolean j = false;

    public C0145e4(C0360l4 c0360l4, Xn xn) {
        this.b = c0360l4;
        this.c = xn;
    }

    @Override // rikka.shizuku.InterfaceC0329k4
    public final float a(int i) {
        int i2 = this.h;
        for (int i3 = 0; i2 != -1 && i3 < this.a; i3++) {
            if (i3 == i) {
                return this.g[i2];
            }
            i2 = this.f[i2];
        }
        return 0.0f;
    }

    @Override // rikka.shizuku.InterfaceC0329k4
    public final void b(C0565ro c0565ro, float f) {
        if (f == 0.0f) {
            j(c0565ro, true);
            return;
        }
        int i = this.h;
        C0360l4 c0360l4 = this.b;
        if (i == -1) {
            this.h = 0;
            this.g[0] = f;
            this.e[0] = c0565ro.b;
            this.f[0] = -1;
            c0565ro.k++;
            c0565ro.a(c0360l4);
            this.a++;
            if (this.j) {
                return;
            }
            int i2 = this.i + 1;
            this.i = i2;
            int[] iArr = this.e;
            if (i2 >= iArr.length) {
                this.j = true;
                this.i = iArr.length - 1;
                return;
            }
            return;
        }
        int i3 = -1;
        for (int i4 = 0; i != -1 && i4 < this.a; i4++) {
            int i5 = this.e[i];
            int i6 = c0565ro.b;
            if (i5 == i6) {
                this.g[i] = f;
                return;
            }
            if (i5 < i6) {
                i3 = i;
            }
            i = this.f[i];
        }
        int length = this.i;
        int i7 = length + 1;
        if (this.j) {
            int[] iArr2 = this.e;
            if (iArr2[length] != -1) {
                length = iArr2.length;
            }
        } else {
            length = i7;
        }
        int[] iArr3 = this.e;
        if (length >= iArr3.length && this.a < iArr3.length) {
            int i8 = 0;
            while (true) {
                int[] iArr4 = this.e;
                if (i8 >= iArr4.length) {
                    break;
                }
                if (iArr4[i8] == -1) {
                    length = i8;
                    break;
                }
                i8++;
            }
        }
        int[] iArr5 = this.e;
        if (length >= iArr5.length) {
            length = iArr5.length;
            int i9 = this.d * 2;
            this.d = i9;
            this.j = false;
            this.i = length - 1;
            this.g = Arrays.copyOf(this.g, i9);
            this.e = Arrays.copyOf(this.e, this.d);
            this.f = Arrays.copyOf(this.f, this.d);
        }
        this.e[length] = c0565ro.b;
        this.g[length] = f;
        if (i3 != -1) {
            int[] iArr6 = this.f;
            iArr6[length] = iArr6[i3];
            iArr6[i3] = length;
        } else {
            this.f[length] = this.h;
            this.h = length;
        }
        c0565ro.k++;
        c0565ro.a(c0360l4);
        int i10 = this.a + 1;
        this.a = i10;
        if (!this.j) {
            this.i++;
        }
        int[] iArr7 = this.e;
        if (i10 >= iArr7.length) {
            this.j = true;
        }
        if (this.i >= iArr7.length) {
            this.j = true;
            this.i = iArr7.length - 1;
        }
    }

    @Override // rikka.shizuku.InterfaceC0329k4
    public final float c(C0360l4 c0360l4, boolean z) {
        float fG = g(c0360l4.a);
        j(c0360l4.a, z);
        InterfaceC0329k4 interfaceC0329k4 = c0360l4.d;
        int iK = interfaceC0329k4.k();
        for (int i = 0; i < iK; i++) {
            C0565ro c0565roE = interfaceC0329k4.e(i);
            h(c0565roE, interfaceC0329k4.g(c0565roE) * fG, z);
        }
        return fG;
    }

    @Override // rikka.shizuku.InterfaceC0329k4
    public final void clear() {
        int i = this.h;
        for (int i2 = 0; i != -1 && i2 < this.a; i2++) {
            C0565ro c0565ro = ((C0565ro[]) this.c.d)[this.e[i]];
            if (c0565ro != null) {
                c0565ro.b(this.b);
            }
            i = this.f[i];
        }
        this.h = -1;
        this.i = -1;
        this.j = false;
        this.a = 0;
    }

    @Override // rikka.shizuku.InterfaceC0329k4
    public final boolean d(C0565ro c0565ro) {
        int i = this.h;
        if (i != -1) {
            for (int i2 = 0; i != -1 && i2 < this.a; i2++) {
                if (this.e[i] == c0565ro.b) {
                    return true;
                }
                i = this.f[i];
            }
        }
        return false;
    }

    @Override // rikka.shizuku.InterfaceC0329k4
    public final C0565ro e(int i) {
        int i2 = this.h;
        for (int i3 = 0; i2 != -1 && i3 < this.a; i3++) {
            if (i3 == i) {
                return ((C0565ro[]) this.c.d)[this.e[i2]];
            }
            i2 = this.f[i2];
        }
        return null;
    }

    @Override // rikka.shizuku.InterfaceC0329k4
    public final void f(float f) {
        int i = this.h;
        for (int i2 = 0; i != -1 && i2 < this.a; i2++) {
            float[] fArr = this.g;
            fArr[i] = fArr[i] / f;
            i = this.f[i];
        }
    }

    @Override // rikka.shizuku.InterfaceC0329k4
    public final float g(C0565ro c0565ro) {
        int i = this.h;
        for (int i2 = 0; i != -1 && i2 < this.a; i2++) {
            if (this.e[i] == c0565ro.b) {
                return this.g[i];
            }
            i = this.f[i];
        }
        return 0.0f;
    }

    @Override // rikka.shizuku.InterfaceC0329k4
    public final void h(C0565ro c0565ro, float f, boolean z) {
        if (f <= -0.001f || f >= 0.001f) {
            int i = this.h;
            C0360l4 c0360l4 = this.b;
            if (i == -1) {
                this.h = 0;
                this.g[0] = f;
                this.e[0] = c0565ro.b;
                this.f[0] = -1;
                c0565ro.k++;
                c0565ro.a(c0360l4);
                this.a++;
                if (this.j) {
                    return;
                }
                int i2 = this.i + 1;
                this.i = i2;
                int[] iArr = this.e;
                if (i2 >= iArr.length) {
                    this.j = true;
                    this.i = iArr.length - 1;
                    return;
                }
                return;
            }
            int i3 = -1;
            for (int i4 = 0; i != -1 && i4 < this.a; i4++) {
                int i5 = this.e[i];
                int i6 = c0565ro.b;
                if (i5 == i6) {
                    float[] fArr = this.g;
                    float f2 = fArr[i] + f;
                    if (f2 > -0.001f && f2 < 0.001f) {
                        f2 = 0.0f;
                    }
                    fArr[i] = f2;
                    if (f2 == 0.0f) {
                        if (i == this.h) {
                            this.h = this.f[i];
                        } else {
                            int[] iArr2 = this.f;
                            iArr2[i3] = iArr2[i];
                        }
                        if (z) {
                            c0565ro.b(c0360l4);
                        }
                        if (this.j) {
                            this.i = i;
                        }
                        c0565ro.k--;
                        this.a--;
                        return;
                    }
                    return;
                }
                if (i5 < i6) {
                    i3 = i;
                }
                i = this.f[i];
            }
            int length = this.i;
            int i7 = length + 1;
            if (this.j) {
                int[] iArr3 = this.e;
                if (iArr3[length] != -1) {
                    length = iArr3.length;
                }
            } else {
                length = i7;
            }
            int[] iArr4 = this.e;
            if (length >= iArr4.length && this.a < iArr4.length) {
                int i8 = 0;
                while (true) {
                    int[] iArr5 = this.e;
                    if (i8 >= iArr5.length) {
                        break;
                    }
                    if (iArr5[i8] == -1) {
                        length = i8;
                        break;
                    }
                    i8++;
                }
            }
            int[] iArr6 = this.e;
            if (length >= iArr6.length) {
                length = iArr6.length;
                int i9 = this.d * 2;
                this.d = i9;
                this.j = false;
                this.i = length - 1;
                this.g = Arrays.copyOf(this.g, i9);
                this.e = Arrays.copyOf(this.e, this.d);
                this.f = Arrays.copyOf(this.f, this.d);
            }
            this.e[length] = c0565ro.b;
            this.g[length] = f;
            if (i3 != -1) {
                int[] iArr7 = this.f;
                iArr7[length] = iArr7[i3];
                iArr7[i3] = length;
            } else {
                this.f[length] = this.h;
                this.h = length;
            }
            c0565ro.k++;
            c0565ro.a(c0360l4);
            this.a++;
            if (!this.j) {
                this.i++;
            }
            int i10 = this.i;
            int[] iArr8 = this.e;
            if (i10 >= iArr8.length) {
                this.j = true;
                this.i = iArr8.length - 1;
            }
        }
    }

    @Override // rikka.shizuku.InterfaceC0329k4
    public final void i() {
        int i = this.h;
        for (int i2 = 0; i != -1 && i2 < this.a; i2++) {
            float[] fArr = this.g;
            fArr[i] = fArr[i] * (-1.0f);
            i = this.f[i];
        }
    }

    @Override // rikka.shizuku.InterfaceC0329k4
    public final float j(C0565ro c0565ro, boolean z) {
        int i = this.h;
        if (i == -1) {
            return 0.0f;
        }
        int i2 = 0;
        int i3 = -1;
        while (i != -1 && i2 < this.a) {
            if (this.e[i] == c0565ro.b) {
                if (i == this.h) {
                    this.h = this.f[i];
                } else {
                    int[] iArr = this.f;
                    iArr[i3] = iArr[i];
                }
                if (z) {
                    c0565ro.b(this.b);
                }
                c0565ro.k--;
                this.a--;
                this.e[i] = -1;
                if (this.j) {
                    this.i = i;
                }
                return this.g[i];
            }
            i2++;
            i3 = i;
            i = this.f[i];
        }
        return 0.0f;
    }

    @Override // rikka.shizuku.InterfaceC0329k4
    public final int k() {
        return this.a;
    }

    public final String toString() {
        int i = this.h;
        String str = "";
        for (int i2 = 0; i != -1 && i2 < this.a; i2++) {
            str = (AbstractC0535qo.c(str, " -> ") + this.g[i] + " : ") + ((C0565ro[]) this.c.d)[this.e[i]];
            i = this.f[i];
        }
        return str;
    }
}
