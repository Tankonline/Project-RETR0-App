package rikka.shizuku;

import android.view.ViewGroup;

/* JADX INFO: renamed from: rikka.shizuku.d7, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0118d7 extends ViewGroup.MarginLayoutParams {
    public float A;
    public String B;
    public int C;
    public float D;
    public float E;
    public int F;
    public int G;
    public int H;
    public int I;
    public int J;
    public int K;
    public int L;
    public int M;
    public float N;
    public float O;
    public int P;
    public int Q;
    public int R;
    public boolean S;
    public boolean T;
    public String U;
    public boolean V;
    public boolean W;
    public boolean X;
    public boolean Y;
    public boolean Z;
    public int a;
    public int a0;
    public int b;
    public int b0;
    public float c;
    public int c0;
    public int d;
    public int d0;
    public int e;
    public int e0;
    public int f;
    public int f0;
    public int g;
    public float g0;
    public int h;
    public int h0;
    public int i;
    public int i0;
    public int j;
    public float j0;
    public int k;
    public C0425n7 k0;
    public int l;
    public int m;
    public int n;
    public float o;
    public int p;
    public int q;
    public int r;
    public int s;
    public int t;
    public int u;
    public int v;
    public int w;
    public int x;
    public int y;
    public float z;

    public final void a() {
        this.Y = false;
        this.V = true;
        this.W = true;
        int i = ((ViewGroup.MarginLayoutParams) this).width;
        if (i == -2 && this.S) {
            this.V = false;
            if (this.H == 0) {
                this.H = 1;
            }
        }
        int i2 = ((ViewGroup.MarginLayoutParams) this).height;
        if (i2 == -2 && this.T) {
            this.W = false;
            if (this.I == 0) {
                this.I = 1;
            }
        }
        if (i == 0 || i == -1) {
            this.V = false;
            if (i == 0 && this.H == 1) {
                ((ViewGroup.MarginLayoutParams) this).width = -2;
                this.S = true;
            }
        }
        if (i2 == 0 || i2 == -1) {
            this.W = false;
            if (i2 == 0 && this.I == 1) {
                ((ViewGroup.MarginLayoutParams) this).height = -2;
                this.T = true;
            }
        }
        if (this.c == -1.0f && this.a == -1 && this.b == -1) {
            return;
        }
        this.Y = true;
        this.V = true;
        this.W = true;
        if (!(this.k0 instanceof Xd)) {
            this.k0 = new Xd();
        }
        ((Xd) this.k0).B(this.R);
    }

    /* JADX WARN: Code duplicated, block: B:17:0x0048  */
    /* JADX WARN: Code duplicated, block: B:20:0x004f  */
    /* JADX WARN: Code duplicated, block: B:23:0x0056  */
    /* JADX WARN: Code duplicated, block: B:26:0x005c  */
    /* JADX WARN: Code duplicated, block: B:29:0x0062  */
    /* JADX WARN: Code duplicated, block: B:36:0x0074  */
    /* JADX WARN: Code duplicated, block: B:37:0x007c A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:38:0x007e  */
    /* JADX WARN: Code duplicated, block: B:39:0x0085 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:40:0x0087  */
    @Override // android.view.ViewGroup.MarginLayoutParams, android.view.ViewGroup.LayoutParams
    public final void resolveLayoutDirection(int i) {
        int i2;
        int i3;
        int i4;
        int i5;
        int i6 = ((ViewGroup.MarginLayoutParams) this).leftMargin;
        int i7 = ((ViewGroup.MarginLayoutParams) this).rightMargin;
        super.resolveLayoutDirection(i);
        boolean z = false;
        boolean z2 = 1 == getLayoutDirection();
        this.c0 = -1;
        this.d0 = -1;
        this.a0 = -1;
        this.b0 = -1;
        this.e0 = this.t;
        this.f0 = this.v;
        float f = this.z;
        this.g0 = f;
        int i8 = this.a;
        this.h0 = i8;
        int i9 = this.b;
        this.i0 = i9;
        float f2 = this.c;
        this.j0 = f2;
        if (z2) {
            int i10 = this.p;
            if (i10 != -1) {
                this.c0 = i10;
            } else {
                int i11 = this.q;
                if (i11 != -1) {
                    this.d0 = i11;
                } else {
                    i2 = this.r;
                    if (i2 != -1) {
                        this.b0 = i2;
                        z = true;
                    }
                    i3 = this.s;
                    if (i3 != -1) {
                        this.a0 = i3;
                        z = true;
                    }
                    i4 = this.x;
                    if (i4 != -1) {
                        this.f0 = i4;
                    }
                    i5 = this.y;
                    if (i5 != -1) {
                        this.e0 = i5;
                    }
                    if (z) {
                        this.g0 = 1.0f - f;
                    }
                    if (this.Y && this.R == 1) {
                        if (f2 != -1.0f) {
                            this.j0 = 1.0f - f2;
                            this.h0 = -1;
                            this.i0 = -1;
                        } else if (i8 != -1) {
                            this.i0 = i8;
                            this.h0 = -1;
                            this.j0 = -1.0f;
                        } else if (i9 != -1) {
                            this.h0 = i9;
                            this.i0 = -1;
                            this.j0 = -1.0f;
                        }
                    }
                }
            }
            z = true;
            i2 = this.r;
            if (i2 != -1) {
                this.b0 = i2;
                z = true;
            }
            i3 = this.s;
            if (i3 != -1) {
                this.a0 = i3;
                z = true;
            }
            i4 = this.x;
            if (i4 != -1) {
                this.f0 = i4;
            }
            i5 = this.y;
            if (i5 != -1) {
                this.e0 = i5;
            }
            if (z) {
                this.g0 = 1.0f - f;
            }
            if (this.Y) {
                if (f2 != -1.0f) {
                    this.j0 = 1.0f - f2;
                    this.h0 = -1;
                    this.i0 = -1;
                } else if (i8 != -1) {
                    this.i0 = i8;
                    this.h0 = -1;
                    this.j0 = -1.0f;
                } else if (i9 != -1) {
                    this.h0 = i9;
                    this.i0 = -1;
                    this.j0 = -1.0f;
                }
            }
        } else {
            int i12 = this.p;
            if (i12 != -1) {
                this.b0 = i12;
            }
            int i13 = this.q;
            if (i13 != -1) {
                this.a0 = i13;
            }
            int i14 = this.r;
            if (i14 != -1) {
                this.c0 = i14;
            }
            int i15 = this.s;
            if (i15 != -1) {
                this.d0 = i15;
            }
            int i16 = this.x;
            if (i16 != -1) {
                this.e0 = i16;
            }
            int i17 = this.y;
            if (i17 != -1) {
                this.f0 = i17;
            }
        }
        if (this.r == -1 && this.s == -1 && this.q == -1 && this.p == -1) {
            int i18 = this.f;
            if (i18 != -1) {
                this.c0 = i18;
                if (((ViewGroup.MarginLayoutParams) this).rightMargin <= 0 && i7 > 0) {
                    ((ViewGroup.MarginLayoutParams) this).rightMargin = i7;
                }
            } else {
                int i19 = this.g;
                if (i19 != -1) {
                    this.d0 = i19;
                    if (((ViewGroup.MarginLayoutParams) this).rightMargin <= 0 && i7 > 0) {
                        ((ViewGroup.MarginLayoutParams) this).rightMargin = i7;
                    }
                }
            }
            int i20 = this.d;
            if (i20 != -1) {
                this.a0 = i20;
                if (((ViewGroup.MarginLayoutParams) this).leftMargin > 0 || i6 <= 0) {
                    return;
                }
                ((ViewGroup.MarginLayoutParams) this).leftMargin = i6;
                return;
            }
            int i21 = this.e;
            if (i21 != -1) {
                this.b0 = i21;
                if (((ViewGroup.MarginLayoutParams) this).leftMargin > 0 || i6 <= 0) {
                    return;
                }
                ((ViewGroup.MarginLayoutParams) this).leftMargin = i6;
            }
        }
    }
}
