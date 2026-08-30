package rikka.shizuku;

import android.view.View;
import java.util.ArrayList;
import rikka.html.text.style.BulletSpan;

/* JADX INFO: renamed from: rikka.shizuku.n7, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0425n7 {
    public final Z6 A;
    public final Z6 B;
    public final Z6 C;
    public final Z6 D;
    public final Z6 E;
    public final Z6[] F;
    public final ArrayList G;
    public final boolean[] H;
    public C0425n7 I;
    public int J;
    public int K;
    public float L;
    public int M;
    public int N;
    public int O;
    public int P;
    public int Q;
    public int R;
    public float S;
    public float T;
    public View U;
    public int V;
    public String W;
    public int X;
    public int Y;
    public final float[] Z;
    public boolean a = false;
    public final C0425n7[] a0;
    public J5 b;
    public final C0425n7[] b0;
    public J5 c;
    public final int[] c0;
    public final Ae d;
    public final C0600ss e;
    public final boolean[] f;
    public final int[] g;
    public int h;
    public int i;
    public int j;
    public int k;
    public final int[] l;
    public int m;
    public int n;
    public float o;
    public int p;
    public int q;
    public float r;
    public int s;
    public float t;
    public final int[] u;
    public float v;
    public boolean w;
    public final Z6 x;
    public final Z6 y;
    public final Z6 z;

    public C0425n7() {
        Ae ae = new Ae(this);
        ae.h.e = 4;
        ae.i.e = 5;
        ae.f = 0;
        this.d = ae;
        C0600ss c0600ss = new C0600ss(this);
        M9 m9 = new M9(c0600ss);
        c0600ss.k = m9;
        c0600ss.l = null;
        c0600ss.h.e = 6;
        c0600ss.i.e = 7;
        m9.e = 8;
        c0600ss.f = 1;
        this.e = c0600ss;
        this.f = new boolean[]{true, true};
        this.g = new int[]{0, 0, 0, 0};
        this.h = -1;
        this.i = -1;
        this.j = 0;
        this.k = 0;
        this.l = new int[2];
        this.m = 0;
        this.n = 0;
        this.o = 1.0f;
        this.p = 0;
        this.q = 0;
        this.r = 1.0f;
        this.s = -1;
        this.t = 1.0f;
        this.u = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE};
        this.v = 0.0f;
        this.w = false;
        Z6 z6 = new Z6(this, 2);
        this.x = z6;
        Z6 z7 = new Z6(this, 3);
        this.y = z7;
        Z6 z8 = new Z6(this, 4);
        this.z = z8;
        Z6 z9 = new Z6(this, 5);
        this.A = z9;
        Z6 z10 = new Z6(this, 6);
        this.B = z10;
        Z6 z11 = new Z6(this, 8);
        this.C = z11;
        Z6 z12 = new Z6(this, 9);
        this.D = z12;
        Z6 z13 = new Z6(this, 7);
        this.E = z13;
        this.F = new Z6[]{z6, z8, z7, z9, z10, z13};
        ArrayList arrayList = new ArrayList();
        this.G = arrayList;
        this.H = new boolean[2];
        this.c0 = new int[]{1, 1};
        this.I = null;
        this.J = 0;
        this.K = 0;
        this.L = 0.0f;
        this.M = -1;
        this.N = 0;
        this.O = 0;
        this.P = 0;
        this.S = 0.5f;
        this.T = 0.5f;
        this.V = 0;
        this.W = null;
        this.X = 0;
        this.Y = 0;
        this.Z = new float[]{-1.0f, -1.0f};
        this.a0 = new C0425n7[]{null, null};
        this.b0 = new C0425n7[]{null, null};
        arrayList.add(z6);
        arrayList.add(z7);
        arrayList.add(z8);
        arrayList.add(z9);
        arrayList.add(z11);
        arrayList.add(z12);
        arrayList.add(z13);
        arrayList.add(z10);
    }

    public void A(Lg lg) {
        int i;
        int i2;
        Z6 z6 = this.x;
        lg.getClass();
        int iM = Lg.m(z6);
        int iM2 = Lg.m(this.y);
        int iM3 = Lg.m(this.z);
        int iM4 = Lg.m(this.A);
        Ae ae = this.d;
        M9 m9 = ae.h;
        if (m9.j) {
            M9 m10 = ae.i;
            if (m10.j) {
                iM = m9.g;
                iM3 = m10.g;
            }
        }
        C0600ss c0600ss = this.e;
        M9 m11 = c0600ss.h;
        if (m11.j) {
            M9 m12 = c0600ss.i;
            if (m12.j) {
                iM2 = m11.g;
                iM4 = m12.g;
            }
        }
        int i3 = iM4 - iM2;
        if (iM3 - iM < 0 || i3 < 0 || iM == Integer.MIN_VALUE || iM == Integer.MAX_VALUE || iM2 == Integer.MIN_VALUE || iM2 == Integer.MAX_VALUE || iM3 == Integer.MIN_VALUE || iM3 == Integer.MAX_VALUE || iM4 == Integer.MIN_VALUE || iM4 == Integer.MAX_VALUE) {
            iM = 0;
            iM2 = 0;
            iM3 = 0;
            iM4 = 0;
        }
        int i4 = iM3 - iM;
        int i5 = iM4 - iM2;
        this.N = iM;
        this.O = iM2;
        if (this.V == 8) {
            this.J = 0;
            this.K = 0;
            return;
        }
        int[] iArr = this.c0;
        if (iArr[0] == 1 && i4 < (i2 = this.J)) {
            i4 = i2;
        }
        if (iArr[1] == 1 && i5 < (i = this.K)) {
            i5 = i;
        }
        this.J = i4;
        this.K = i5;
        int i6 = this.R;
        if (i5 < i6) {
            this.K = i6;
        }
        int i7 = this.Q;
        if (i4 < i7) {
            this.J = i7;
        }
    }

    /* JADX WARN: Code duplicated, block: B:187:0x02d0  */
    /* JADX WARN: Code duplicated, block: B:192:0x02db A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:196:0x02e3  */
    /* JADX WARN: Code duplicated, block: B:199:0x02eb  */
    /* JADX WARN: Code duplicated, block: B:201:0x02f0  */
    /* JADX WARN: Code duplicated, block: B:204:0x0310  */
    /* JADX WARN: Code duplicated, block: B:206:0x0316  */
    /* JADX WARN: Code duplicated, block: B:208:0x031c  */
    /* JADX WARN: Code duplicated, block: B:220:0x0372  */
    /* JADX WARN: Code duplicated, block: B:221:0x0379  */
    /* JADX WARN: Code duplicated, block: B:224:0x037f  */
    /* JADX WARN: Code duplicated, block: B:226:0x038a  */
    /* JADX WARN: Code duplicated, block: B:228:0x03eb  */
    /* JADX WARN: Code duplicated, block: B:231:0x0414  */
    /* JADX WARN: Code duplicated, block: B:243:0x0455  */
    /* JADX WARN: Code duplicated, block: B:246:0x0465  */
    /* JADX WARN: Code duplicated, block: B:249:0x0469  */
    /* JADX WARN: Code duplicated, block: B:251:0x046d  */
    /* JADX WARN: Code duplicated, block: B:254:0x0473  */
    /* JADX WARN: Code duplicated, block: B:256:0x0476  */
    /* JADX WARN: Code duplicated, block: B:257:0x0478  */
    /* JADX WARN: Code duplicated, block: B:259:0x047c  */
    /* JADX WARN: Code duplicated, block: B:264:0x0486  */
    /* JADX WARN: Code duplicated, block: B:267:0x048c  */
    /* JADX WARN: Code duplicated, block: B:268:0x0493  */
    /* JADX WARN: Code duplicated, block: B:271:0x0499  */
    /* JADX WARN: Code duplicated, block: B:274:0x04a5  */
    /* JADX WARN: Code duplicated, block: B:276:0x04a9  */
    /* JADX WARN: Code duplicated, block: B:278:0x04b2  */
    /* JADX WARN: Code duplicated, block: B:280:0x04bb  */
    /* JADX WARN: Code duplicated, block: B:282:0x04c7  */
    /* JADX WARN: Code duplicated, block: B:284:0x04cb  */
    /* JADX WARN: Code duplicated, block: B:286:0x0514  */
    /* JADX WARN: Code duplicated, block: B:288:0x051a  */
    /* JADX WARN: Code duplicated, block: B:290:0x0521  */
    /* JADX WARN: Code duplicated, block: B:291:0x054a  */
    /* JADX WARN: Code duplicated, block: B:294:0x0578  */
    /* JADX WARN: Code duplicated, block: B:300:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    public void a(Lg lg) {
        boolean z;
        boolean z2;
        boolean zQ;
        boolean z3;
        C0600ss c0600ss;
        char c;
        int i;
        int i2;
        boolean z4;
        boolean z5;
        boolean z6;
        Z6 z7;
        boolean z8;
        boolean z9;
        boolean z10;
        int i3;
        int[] iArr;
        C0565ro c0565ro;
        C0565ro c0565ro2;
        boolean z11;
        Z6 z12;
        C0565ro c0565ro3;
        int i4;
        boolean z13;
        boolean z14;
        boolean z15;
        boolean z16;
        int i5;
        C0600ss c0600ss2;
        M9 m9;
        C0565ro c0565ro4;
        C0565ro c0565ro5;
        C0565ro c0565ro6;
        int i6;
        int i7;
        int i8;
        int i9;
        C0565ro c0565ro7;
        int i10;
        int i11;
        boolean z17;
        C0425n7 c0425n7;
        C0565ro c0565roJ;
        int i12;
        Object obj;
        boolean z18;
        int i13;
        M9 m10;
        C0425n7 c0425n8;
        C0565ro c0565roJ2;
        C0425n7 c0425n9;
        C0565ro c0565roJ3;
        int i14;
        boolean z19;
        int i15;
        int i16;
        boolean zR;
        Lg lg2 = lg;
        Z6 z20 = this.x;
        C0565ro c0565roJ4 = lg2.j(z20);
        Z6 z21 = this.z;
        C0565ro c0565roJ5 = lg2.j(z21);
        Z6 z22 = this.y;
        C0565ro c0565roJ6 = lg2.j(z22);
        Z6 z23 = this.A;
        C0565ro c0565roJ7 = lg2.j(z23);
        Z6 z24 = this.B;
        C0565ro c0565roJ8 = lg2.j(z24);
        Ae ae = this.d;
        M9 m11 = ae.h;
        boolean z25 = m11.j;
        boolean[] zArr = this.f;
        C0600ss c0600ss3 = this.e;
        if (z25 && ae.i.j && c0600ss3.h.j && c0600ss3.i.j) {
            lg2.d(c0565roJ4, m11.g);
            lg2.d(c0565roJ5, ae.i.g);
            lg2.d(c0565roJ6, c0600ss3.h.g);
            lg2.d(c0565roJ7, c0600ss3.i.g);
            lg2.d(c0565roJ8, c0600ss3.k.g);
            C0425n7 c0425n10 = this.I;
            if (c0425n10 != null) {
                int[] iArr2 = c0425n10.c0;
                boolean z26 = iArr2[0] == 2;
                boolean z27 = iArr2[1] == 2;
                if (z26 && zArr[0] && !q()) {
                    lg2.f(lg2.j(this.I.z), c0565roJ5, 0, 8);
                }
                if (z27 && zArr[1] && !r()) {
                    lg2.f(lg2.j(this.I.A), c0565roJ7, 0, 8);
                    return;
                }
                return;
            }
            return;
        }
        C0425n7 c0425n11 = this.I;
        if (c0425n11 != null) {
            int[] iArr3 = c0425n11.c0;
            z2 = iArr3[0] == 2;
            if (iArr3[1] == 2) {
                i15 = 0;
                z19 = true;
            } else {
                z19 = false;
                i15 = 0;
            }
            if (p(i15)) {
                ((C0456o7) this.I).B(this, i15);
                zQ = true;
                i16 = 1;
            } else {
                zQ = q();
                i16 = 1;
            }
            if (p(i16)) {
                ((C0456o7) this.I).B(this, i16);
                zR = true;
            } else {
                zR = r();
            }
            if (zQ == 0 && z2) {
                z3 = zR;
                if (this.V != 8 && z20.d == null && z21.d == null) {
                    lg2.f(lg2.j(this.I.z), c0565roJ5, 0, 1);
                }
            } else {
                z3 = zR;
            }
            if (!z3 && z19 && this.V != 8 && z22.d == null && z23.d == null && z24 == null) {
                lg2.f(lg2.j(this.I.A), c0565roJ7, 0, 1);
            }
            z = z19;
        } else {
            z20 = z20;
            z = false;
            z2 = false;
            zQ = false;
            z3 = false;
        }
        int i17 = this.J;
        int i18 = this.Q;
        if (i17 >= i18) {
            i18 = i17;
        }
        int i19 = this.K;
        boolean z28 = z;
        int i20 = this.R;
        int i21 = i19 < i20 ? i20 : i19;
        int[] iArr4 = this.c0;
        int i22 = iArr4[0];
        boolean z29 = i22 != 3;
        int i23 = iArr4[1];
        boolean z30 = i23 != 3;
        int i24 = this.M;
        this.s = i24;
        float f = this.L;
        this.t = f;
        int i25 = this.j;
        int i26 = this.k;
        if (f > 0.0f) {
            c0600ss = c0600ss3;
            if (this.V != 8) {
                c = 3;
                int i27 = (i22 == 3 && i25 == 0) ? 3 : i25;
                i2 = (i23 == 3 && i26 == 0) ? 3 : i26;
                if (i22 != 3 || i23 != 3 || i27 != 3 || i2 != 3) {
                    if (i22 == 3 && i27 == 3) {
                        this.s = 0;
                        int i28 = (int) (f * i19);
                        c = 3;
                        i18 = i28;
                        if (i23 != 3) {
                            i = 4;
                        }
                    } else {
                        if (i23 == 3 && i2 == 3) {
                            this.s = 1;
                            if (i24 == -1) {
                                this.t = 1.0f / f;
                            }
                            i21 = (int) (this.t * i17);
                            i = i27;
                            if (i22 != 3) {
                                i2 = 4;
                            }
                        }
                        z4 = true;
                    }
                    int[] iArr5 = this.l;
                    iArr5[0] = i;
                    iArr5[1] = i2;
                    z5 = !z4 && ((i14 = this.s) == 0 || i14 == -1);
                    if (iArr4[0] == 2 || !(this instanceof C0456o7)) {
                        z6 = false;
                    } else {
                        z6 = true;
                    }
                    if (z6) {
                        i18 = 0;
                    }
                    z7 = this.E;
                    z8 = !z7.f();
                    boolean[] zArr2 = this.H;
                    z9 = zArr2[0];
                    z10 = zArr2[1];
                    i3 = this.h;
                    iArr = this.u;
                    if (i3 != 2) {
                        m10 = ae.h;
                        if (m10.j || !ae.i.j) {
                            c0425n8 = this.I;
                            if (c0425n8 != null) {
                                c0565roJ2 = lg2.j(c0425n8.z);
                            } else {
                                c0565roJ2 = null;
                            }
                            c0425n9 = this.I;
                            if (c0425n9 != null) {
                                c0565roJ3 = lg2.j(c0425n9.x);
                            } else {
                                c0565roJ3 = null;
                            }
                            z11 = z2;
                            i4 = i;
                            i5 = i2;
                            C0565ro c0565ro8 = c0565roJ2;
                            z15 = z28;
                            boolean z31 = z8;
                            c0565ro2 = c0565roJ5;
                            z13 = zQ;
                            z14 = z3;
                            c0565ro = c0565roJ4;
                            z12 = z24;
                            c0565ro3 = c0565roJ6;
                            lg2 = lg;
                            c(lg2, true, z11, z15, zArr[0], c0565roJ3, c0565ro8, iArr4[0], z6, this.x, this.z, this.N, i18, this.Q, iArr[0], this.S, z5, z13, z14, z9, i4, i5, this.m, this.n, this.o, z31);
                            c0600ss2 = c0600ss;
                            z16 = z31;
                        } else {
                            lg2.d(c0565roJ4, m10.g);
                            lg2.d(c0565roJ5, ae.i.g);
                            if (this.I != null && z2 && zArr[0] && !q()) {
                                lg2.f(lg2.j(this.I.z), c0565roJ5, 0, 8);
                            }
                            c0565ro = c0565roJ4;
                            c0565ro2 = c0565roJ5;
                            z11 = z2;
                            z12 = z24;
                            c0565ro3 = c0565roJ6;
                            i4 = i;
                            z13 = zQ;
                            z14 = z3;
                            z15 = z28;
                            c0600ss2 = c0600ss;
                            z16 = z8;
                            i5 = i2;
                        }
                    } else {
                        c0565ro = c0565roJ4;
                        c0565ro2 = c0565roJ5;
                        z11 = z2;
                        z12 = z24;
                        c0565ro3 = c0565roJ6;
                        i4 = i;
                        z13 = zQ;
                        z14 = z3;
                        z15 = z28;
                        z16 = z8;
                        i5 = i2;
                        c0600ss2 = c0600ss;
                    }
                    m9 = c0600ss2.h;
                    if (m9.j || !c0600ss2.i.j) {
                        c0565ro4 = c0565ro3;
                        c0565ro5 = r34;
                        c0565ro6 = c0565roJ8;
                        i6 = 1;
                        i7 = 0;
                        i8 = 8;
                        i9 = 1;
                    } else {
                        c0565ro4 = c0565ro3;
                        lg2.d(c0565ro4, m9.g);
                        c0565ro5 = c0565roJ7;
                        lg2.d(c0565ro5, c0600ss2.i.g);
                        c0565ro6 = c0565roJ8;
                        lg2.d(c0565ro6, c0600ss2.k.g);
                        C0425n7 c0425n12 = this.I;
                        if (c0425n12 == null || z14 || !z15) {
                            i6 = 1;
                        } else {
                            i6 = 1;
                            if (zArr[1]) {
                                i7 = 0;
                                i8 = 8;
                                lg2.f(lg2.j(c0425n12.A), c0565ro5, 0, 8);
                            }
                            i9 = i7;
                        }
                        i7 = 0;
                        i8 = 8;
                        i9 = i7;
                    }
                    if (this.i == 2) {
                        i9 = i7;
                    }
                    if (i9 != 0) {
                        if (iArr4[i6] == 2 || !(this instanceof C0456o7)) {
                            i10 = i7;
                        } else {
                            i10 = i6;
                        }
                        if (i10 != 0) {
                            i11 = i7;
                        } else {
                            i11 = i21;
                        }
                        if (z4 || !((i13 = this.s) == i6 || i13 == -1)) {
                            z17 = i7;
                        } else {
                            z17 = i6;
                        }
                        c0425n7 = this.I;
                        if (c0425n7 != null) {
                            c0565roJ = lg2.j(c0425n7.A);
                        } else {
                            c0565roJ = null;
                        }
                        C0425n7 c0425n13 = this.I;
                        C0565ro c0565roJ9 = c0425n13 != null ? lg2.j(c0425n13.y) : null;
                        i12 = this.P;
                        int i29 = i6;
                        if (i12 <= 0 || this.V == i8) {
                            z18 = z16;
                            lg2.e(c0565ro6, c0565ro4, i12, i8);
                            obj = z12.d;
                            if (obj != null) {
                                lg2.e(c0565ro6, lg2.j(obj), i7, i8);
                                if (z15) {
                                    lg2.f(c0565roJ, lg2.j(z23), i7, 5);
                                }
                                z18 = i7;
                            } else if (this.V == i8) {
                                z18 = z16;
                                lg2.e(c0565ro6, c0565ro4, i7, i8);
                                z18 = z16;
                            }
                        }
                        z18 = z16;
                        z18 = z16;
                        c0565ro7 = c0565ro5;
                        lg2 = lg;
                        c(lg2, false, z15, z11, zArr[i29], c0565roJ9, c0565roJ, iArr4[i29], i10, this.y, this.A, this.O, i11, this.R, iArr[i29], this.T, z17, z14, z13, z10, i5, i4, this.p, this.q, this.r, z18);
                    } else {
                        c0565ro7 = c0565ro5;
                    }
                    if (z4) {
                        if (this.s == 1) {
                            float f2 = this.t;
                            C0360l4 c0360l4K = lg2.k();
                            c0360l4K.d.b(c0565ro7, -1.0f);
                            c0360l4K.d.b(c0565ro4, 1.0f);
                            c0360l4K.d.b(c0565ro2, f2);
                            c0360l4K.d.b(c0565ro, -f2);
                            lg2.c(c0360l4K);
                        } else {
                            float f3 = this.t;
                            C0360l4 c0360l4K2 = lg2.k();
                            c0360l4K2.d.b(c0565ro2, -1.0f);
                            c0360l4K2.d.b(c0565ro, 1.0f);
                            c0360l4K2.d.b(c0565ro7, f3);
                            c0360l4K2.d.b(c0565ro4, -f3);
                            lg2.c(c0360l4K2);
                        }
                    }
                    if (r25.f()) {
                        C0425n7 c0425n14 = z7.d.b;
                        float radians = (float) Math.toRadians(this.v + 90.0f);
                        int iC = z7.c();
                        C0565ro c0565roJ10 = lg2.j(g(2));
                        C0565ro c0565roJ11 = lg2.j(g(3));
                        C0565ro c0565roJ12 = lg2.j(g(4));
                        C0565ro c0565roJ13 = lg2.j(g(5));
                        C0565ro c0565roJ14 = lg2.j(c0425n14.g(2));
                        C0565ro c0565roJ15 = lg2.j(c0425n14.g(3));
                        C0565ro c0565roJ16 = lg2.j(c0425n14.g(4));
                        C0565ro c0565roJ17 = lg2.j(c0425n14.g(5));
                        C0360l4 c0360l4K3 = lg2.k();
                        double d = radians;
                        double dSin = Math.sin(d);
                        double d2 = iC;
                        c0360l4K3.d.b(c0565roJ15, 0.5f);
                        c0360l4K3.d.b(c0565roJ17, 0.5f);
                        c0360l4K3.d.b(c0565roJ11, -0.5f);
                        c0360l4K3.d.b(c0565roJ13, -0.5f);
                        c0360l4K3.b = -((float) (dSin * d2));
                        lg2.c(c0360l4K3);
                        C0360l4 c0360l4K4 = lg2.k();
                        float fCos = (float) (Math.cos(d) * d2);
                        c0360l4K4.d.b(c0565roJ14, 0.5f);
                        c0360l4K4.d.b(c0565roJ16, 0.5f);
                        c0360l4K4.d.b(c0565roJ10, -0.5f);
                        c0360l4K4.d.b(c0565roJ12, -0.5f);
                        c0360l4K4.b = -fCos;
                        lg2.c(c0360l4K4);
                    }
                }
                if (i24 == -1) {
                    if (z29 && !z30) {
                        this.s = 0;
                    } else if (!z29 && z30) {
                        this.s = 1;
                        if (i24 == -1) {
                            this.t = 1.0f / f;
                        }
                    }
                }
                if (this.s == 0 && (!z22.f() || !z23.f())) {
                    this.s = 1;
                } else if (this.s == 1 && (!z20.f() || !z21.f())) {
                    this.s = 0;
                }
                if (this.s == -1 && (!z22.f() || !z23.f() || !z20.f() || !z21.f())) {
                    if (z22.f() && z23.f()) {
                        this.s = 0;
                    } else if (z20.f() && z21.f()) {
                        this.t = 1.0f / this.t;
                        this.s = 1;
                    }
                }
                if (this.s == -1) {
                    int i30 = this.m;
                    if (i30 > 0 && this.p == 0) {
                        this.s = 0;
                    } else if (i30 == 0 && this.p > 0) {
                        this.t = 1.0f / this.t;
                        this.s = 1;
                    }
                }
                c = 3;
                i = i27;
                z4 = true;
                int[] iArr6 = this.l;
                iArr6[0] = i;
                iArr6[1] = i2;
                if (z4) {
                }
                if (iArr4[0] == 2) {
                    z6 = false;
                } else {
                    z6 = false;
                }
                if (z6) {
                    i18 = 0;
                }
                z7 = this.E;
                z8 = !z7.f();
                boolean[] zArr3 = this.H;
                z9 = zArr3[0];
                z10 = zArr3[1];
                i3 = this.h;
                iArr = this.u;
                if (i3 != 2) {
                    m10 = ae.h;
                    if (m10.j) {
                        c0425n8 = this.I;
                        if (c0425n8 != null) {
                            c0565roJ2 = lg2.j(c0425n8.z);
                        } else {
                            c0565roJ2 = null;
                        }
                        c0425n9 = this.I;
                        if (c0425n9 != null) {
                            c0565roJ3 = lg2.j(c0425n9.x);
                        } else {
                            c0565roJ3 = null;
                        }
                        z11 = z2;
                        i4 = i;
                        i5 = i2;
                        C0565ro c0565ro9 = c0565roJ2;
                        z15 = z28;
                        boolean z32 = z8;
                        c0565ro2 = c0565roJ5;
                        z13 = zQ;
                        z14 = z3;
                        c0565ro = c0565roJ4;
                        z12 = z24;
                        c0565ro3 = c0565roJ6;
                        lg2 = lg;
                        c(lg2, true, z11, z15, zArr[0], c0565roJ3, c0565ro9, iArr4[0], z6, this.x, this.z, this.N, i18, this.Q, iArr[0], this.S, z5, z13, z14, z9, i4, i5, this.m, this.n, this.o, z32);
                        c0600ss2 = c0600ss;
                        z16 = z32;
                    } else {
                        c0425n8 = this.I;
                        if (c0425n8 != null) {
                            c0565roJ2 = lg2.j(c0425n8.z);
                        } else {
                            c0565roJ2 = null;
                        }
                        c0425n9 = this.I;
                        if (c0425n9 != null) {
                            c0565roJ3 = lg2.j(c0425n9.x);
                        } else {
                            c0565roJ3 = null;
                        }
                        z11 = z2;
                        i4 = i;
                        i5 = i2;
                        C0565ro c0565ro10 = c0565roJ2;
                        z15 = z28;
                        boolean z33 = z8;
                        c0565ro2 = c0565roJ5;
                        z13 = zQ;
                        z14 = z3;
                        c0565ro = c0565roJ4;
                        z12 = z24;
                        c0565ro3 = c0565roJ6;
                        lg2 = lg;
                        c(lg2, true, z11, z15, zArr[0], c0565roJ3, c0565ro10, iArr4[0], z6, this.x, this.z, this.N, i18, this.Q, iArr[0], this.S, z5, z13, z14, z9, i4, i5, this.m, this.n, this.o, z33);
                        c0600ss2 = c0600ss;
                        z16 = z33;
                    }
                } else {
                    c0565ro = c0565roJ4;
                    c0565ro2 = c0565roJ5;
                    z11 = z2;
                    z12 = z24;
                    c0565ro3 = c0565roJ6;
                    i4 = i;
                    z13 = zQ;
                    z14 = z3;
                    z15 = z28;
                    z16 = z8;
                    i5 = i2;
                    c0600ss2 = c0600ss;
                }
                m9 = c0600ss2.h;
                if (m9.j) {
                    c0565ro4 = c0565ro3;
                    c0565ro5 = r34;
                    c0565ro6 = c0565roJ8;
                    i6 = 1;
                    i7 = 0;
                    i8 = 8;
                    i9 = 1;
                } else {
                    c0565ro4 = c0565ro3;
                    c0565ro5 = r34;
                    c0565ro6 = c0565roJ8;
                    i6 = 1;
                    i7 = 0;
                    i8 = 8;
                    i9 = 1;
                }
                if (this.i == 2) {
                    i9 = i7;
                }
                if (i9 != 0) {
                    if (iArr4[i6] == 2) {
                        i10 = i7;
                    } else {
                        i10 = i7;
                    }
                    if (i10 != 0) {
                        i11 = i7;
                    } else {
                        i11 = i21;
                    }
                    if (z4) {
                        z17 = i7;
                    } else {
                        z17 = i7;
                    }
                    c0425n7 = this.I;
                    if (c0425n7 != null) {
                        c0565roJ = lg2.j(c0425n7.A);
                    } else {
                        c0565roJ = null;
                    }
                    C0425n7 c0425n15 = this.I;
                    if (c0425n15 != null) {
                    }
                    i12 = this.P;
                    int i210 = i6;
                    if (i12 <= 0) {
                        z18 = z16;
                        lg2.e(c0565ro6, c0565ro4, i12, i8);
                        obj = z12.d;
                        if (obj != null) {
                            lg2.e(c0565ro6, lg2.j(obj), i7, i8);
                            if (z15) {
                                lg2.f(c0565roJ, lg2.j(z23), i7, 5);
                            }
                            z18 = i7;
                        } else if (this.V == i8) {
                            z18 = z16;
                            lg2.e(c0565ro6, c0565ro4, i7, i8);
                            z18 = z16;
                        }
                    } else {
                        z18 = z16;
                        lg2.e(c0565ro6, c0565ro4, i12, i8);
                        obj = z12.d;
                        if (obj != null) {
                            lg2.e(c0565ro6, lg2.j(obj), i7, i8);
                            if (z15) {
                                lg2.f(c0565roJ, lg2.j(z23), i7, 5);
                            }
                            z18 = i7;
                        } else if (this.V == i8) {
                            z18 = z16;
                            lg2.e(c0565ro6, c0565ro4, i7, i8);
                            z18 = z16;
                        }
                    }
                    z18 = z16;
                    z18 = z16;
                    c0565ro7 = c0565ro5;
                    lg2 = lg;
                    c(lg2, false, z15, z11, zArr[i210], c0565roJ9, c0565roJ, iArr4[i210], i10, this.y, this.A, this.O, i11, this.R, iArr[i210], this.T, z17, z14, z13, z10, i5, i4, this.p, this.q, this.r, z18);
                } else {
                    c0565ro7 = c0565ro5;
                }
                if (z4) {
                    if (this.s == 1) {
                        float f4 = this.t;
                        C0360l4 c0360l4K5 = lg2.k();
                        c0360l4K5.d.b(c0565ro7, -1.0f);
                        c0360l4K5.d.b(c0565ro4, 1.0f);
                        c0360l4K5.d.b(c0565ro2, f4);
                        c0360l4K5.d.b(c0565ro, -f4);
                        lg2.c(c0360l4K5);
                    } else {
                        float f5 = this.t;
                        C0360l4 c0360l4K6 = lg2.k();
                        c0360l4K6.d.b(c0565ro2, -1.0f);
                        c0360l4K6.d.b(c0565ro, 1.0f);
                        c0360l4K6.d.b(c0565ro7, f5);
                        c0360l4K6.d.b(c0565ro4, -f5);
                        lg2.c(c0360l4K6);
                    }
                }
                if (r25.f()) {
                    C0425n7 c0425n16 = z7.d.b;
                    float radians2 = (float) Math.toRadians(this.v + 90.0f);
                    int iC2 = z7.c();
                    C0565ro c0565roJ18 = lg2.j(g(2));
                    C0565ro c0565roJ19 = lg2.j(g(3));
                    C0565ro c0565roJ110 = lg2.j(g(4));
                    C0565ro c0565roJ111 = lg2.j(g(5));
                    C0565ro c0565roJ112 = lg2.j(c0425n16.g(2));
                    C0565ro c0565roJ113 = lg2.j(c0425n16.g(3));
                    C0565ro c0565roJ114 = lg2.j(c0425n16.g(4));
                    C0565ro c0565roJ115 = lg2.j(c0425n16.g(5));
                    C0360l4 c0360l4K7 = lg2.k();
                    double d3 = radians2;
                    double dSin2 = Math.sin(d3);
                    double d4 = iC2;
                    c0360l4K7.d.b(c0565roJ113, 0.5f);
                    c0360l4K7.d.b(c0565roJ115, 0.5f);
                    c0360l4K7.d.b(c0565roJ19, -0.5f);
                    c0360l4K7.d.b(c0565roJ111, -0.5f);
                    c0360l4K7.b = -((float) (dSin2 * d4));
                    lg2.c(c0360l4K7);
                    C0360l4 c0360l4K8 = lg2.k();
                    float fCos2 = (float) (Math.cos(d3) * d4);
                    c0360l4K8.d.b(c0565roJ112, 0.5f);
                    c0360l4K8.d.b(c0565roJ114, 0.5f);
                    c0360l4K8.d.b(c0565roJ18, -0.5f);
                    c0360l4K8.d.b(c0565roJ110, -0.5f);
                    c0360l4K8.b = -fCos2;
                    lg2.c(c0360l4K8);
                }
            }
            z4 = false;
            int[] iArr7 = this.l;
            iArr7[0] = i;
            iArr7[1] = i2;
            if (z4) {
            }
            if (iArr4[0] == 2) {
                z6 = false;
            } else {
                z6 = false;
            }
            if (z6) {
                i18 = 0;
            }
            z7 = this.E;
            z8 = !z7.f();
            boolean[] zArr4 = this.H;
            z9 = zArr4[0];
            z10 = zArr4[1];
            i3 = this.h;
            iArr = this.u;
            if (i3 != 2) {
                m10 = ae.h;
                if (m10.j) {
                    c0425n8 = this.I;
                    if (c0425n8 != null) {
                        c0565roJ2 = lg2.j(c0425n8.z);
                    } else {
                        c0565roJ2 = null;
                    }
                    c0425n9 = this.I;
                    if (c0425n9 != null) {
                        c0565roJ3 = lg2.j(c0425n9.x);
                    } else {
                        c0565roJ3 = null;
                    }
                    z11 = z2;
                    i4 = i;
                    i5 = i2;
                    C0565ro c0565ro11 = c0565roJ2;
                    z15 = z28;
                    boolean z34 = z8;
                    c0565ro2 = c0565roJ5;
                    z13 = zQ;
                    z14 = z3;
                    c0565ro = c0565roJ4;
                    z12 = z24;
                    c0565ro3 = c0565roJ6;
                    lg2 = lg;
                    c(lg2, true, z11, z15, zArr[0], c0565roJ3, c0565ro11, iArr4[0], z6, this.x, this.z, this.N, i18, this.Q, iArr[0], this.S, z5, z13, z14, z9, i4, i5, this.m, this.n, this.o, z34);
                    c0600ss2 = c0600ss;
                    z16 = z34;
                } else {
                    c0425n8 = this.I;
                    if (c0425n8 != null) {
                        c0565roJ2 = lg2.j(c0425n8.z);
                    } else {
                        c0565roJ2 = null;
                    }
                    c0425n9 = this.I;
                    if (c0425n9 != null) {
                        c0565roJ3 = lg2.j(c0425n9.x);
                    } else {
                        c0565roJ3 = null;
                    }
                    z11 = z2;
                    i4 = i;
                    i5 = i2;
                    C0565ro c0565ro12 = c0565roJ2;
                    z15 = z28;
                    boolean z35 = z8;
                    c0565ro2 = c0565roJ5;
                    z13 = zQ;
                    z14 = z3;
                    c0565ro = c0565roJ4;
                    z12 = z24;
                    c0565ro3 = c0565roJ6;
                    lg2 = lg;
                    c(lg2, true, z11, z15, zArr[0], c0565roJ3, c0565ro12, iArr4[0], z6, this.x, this.z, this.N, i18, this.Q, iArr[0], this.S, z5, z13, z14, z9, i4, i5, this.m, this.n, this.o, z35);
                    c0600ss2 = c0600ss;
                    z16 = z35;
                }
            } else {
                c0565ro = c0565roJ4;
                c0565ro2 = c0565roJ5;
                z11 = z2;
                z12 = z24;
                c0565ro3 = c0565roJ6;
                i4 = i;
                z13 = zQ;
                z14 = z3;
                z15 = z28;
                z16 = z8;
                i5 = i2;
                c0600ss2 = c0600ss;
            }
            m9 = c0600ss2.h;
            if (m9.j) {
                c0565ro4 = c0565ro3;
                c0565ro5 = r34;
                c0565ro6 = c0565roJ8;
                i6 = 1;
                i7 = 0;
                i8 = 8;
                i9 = 1;
            } else {
                c0565ro4 = c0565ro3;
                c0565ro5 = r34;
                c0565ro6 = c0565roJ8;
                i6 = 1;
                i7 = 0;
                i8 = 8;
                i9 = 1;
            }
            if (this.i == 2) {
                i9 = i7;
            }
            if (i9 != 0) {
                if (iArr4[i6] == 2) {
                    i10 = i7;
                } else {
                    i10 = i7;
                }
                if (i10 != 0) {
                    i11 = i7;
                } else {
                    i11 = i21;
                }
                if (z4) {
                    z17 = i7;
                } else {
                    z17 = i7;
                }
                c0425n7 = this.I;
                if (c0425n7 != null) {
                    c0565roJ = lg2.j(c0425n7.A);
                } else {
                    c0565roJ = null;
                }
                C0425n7 c0425n17 = this.I;
                if (c0425n17 != null) {
                }
                i12 = this.P;
                int i211 = i6;
                if (i12 <= 0) {
                    z18 = z16;
                    lg2.e(c0565ro6, c0565ro4, i12, i8);
                    obj = z12.d;
                    if (obj != null) {
                        lg2.e(c0565ro6, lg2.j(obj), i7, i8);
                        if (z15) {
                            lg2.f(c0565roJ, lg2.j(z23), i7, 5);
                        }
                        z18 = i7;
                    } else if (this.V == i8) {
                        z18 = z16;
                        lg2.e(c0565ro6, c0565ro4, i7, i8);
                        z18 = z16;
                    }
                } else {
                    z18 = z16;
                    lg2.e(c0565ro6, c0565ro4, i12, i8);
                    obj = z12.d;
                    if (obj != null) {
                        lg2.e(c0565ro6, lg2.j(obj), i7, i8);
                        if (z15) {
                            lg2.f(c0565roJ, lg2.j(z23), i7, 5);
                        }
                        z18 = i7;
                    } else if (this.V == i8) {
                        z18 = z16;
                        lg2.e(c0565ro6, c0565ro4, i7, i8);
                        z18 = z16;
                    }
                }
                z18 = z16;
                z18 = z16;
                c0565ro7 = c0565ro5;
                lg2 = lg;
                c(lg2, false, z15, z11, zArr[i211], c0565roJ9, c0565roJ, iArr4[i211], i10, this.y, this.A, this.O, i11, this.R, iArr[i211], this.T, z17, z14, z13, z10, i5, i4, this.p, this.q, this.r, z18);
            } else {
                c0565ro7 = c0565ro5;
            }
            if (z4) {
                if (this.s == 1) {
                    float f6 = this.t;
                    C0360l4 c0360l4K9 = lg2.k();
                    c0360l4K9.d.b(c0565ro7, -1.0f);
                    c0360l4K9.d.b(c0565ro4, 1.0f);
                    c0360l4K9.d.b(c0565ro2, f6);
                    c0360l4K9.d.b(c0565ro, -f6);
                    lg2.c(c0360l4K9);
                } else {
                    float f7 = this.t;
                    C0360l4 c0360l4K10 = lg2.k();
                    c0360l4K10.d.b(c0565ro2, -1.0f);
                    c0360l4K10.d.b(c0565ro, 1.0f);
                    c0360l4K10.d.b(c0565ro7, f7);
                    c0360l4K10.d.b(c0565ro4, -f7);
                    lg2.c(c0360l4K10);
                }
            }
            if (r25.f()) {
                C0425n7 c0425n18 = z7.d.b;
                float radians3 = (float) Math.toRadians(this.v + 90.0f);
                int iC3 = z7.c();
                C0565ro c0565roJ116 = lg2.j(g(2));
                C0565ro c0565roJ117 = lg2.j(g(3));
                C0565ro c0565roJ118 = lg2.j(g(4));
                C0565ro c0565roJ119 = lg2.j(g(5));
                C0565ro c0565roJ1110 = lg2.j(c0425n18.g(2));
                C0565ro c0565roJ1111 = lg2.j(c0425n18.g(3));
                C0565ro c0565roJ1112 = lg2.j(c0425n18.g(4));
                C0565ro c0565roJ1113 = lg2.j(c0425n18.g(5));
                C0360l4 c0360l4K11 = lg2.k();
                double d5 = radians3;
                double dSin3 = Math.sin(d5);
                double d6 = iC3;
                c0360l4K11.d.b(c0565roJ1111, 0.5f);
                c0360l4K11.d.b(c0565roJ1113, 0.5f);
                c0360l4K11.d.b(c0565roJ117, -0.5f);
                c0360l4K11.d.b(c0565roJ119, -0.5f);
                c0360l4K11.b = -((float) (dSin3 * d6));
                lg2.c(c0360l4K11);
                C0360l4 c0360l4K12 = lg2.k();
                float fCos3 = (float) (Math.cos(d5) * d6);
                c0360l4K12.d.b(c0565roJ1110, 0.5f);
                c0360l4K12.d.b(c0565roJ1112, 0.5f);
                c0360l4K12.d.b(c0565roJ116, -0.5f);
                c0360l4K12.d.b(c0565roJ118, -0.5f);
                c0360l4K12.b = -fCos3;
                lg2.c(c0360l4K12);
            }
        }
        c0600ss = c0600ss3;
        c0565roJ8 = c0565roJ8;
        c = 3;
        i = i25;
        i2 = i26;
        z4 = false;
        int[] iArr8 = this.l;
        iArr8[0] = i;
        iArr8[1] = i2;
        if (z4) {
        }
        if (iArr4[0] == 2) {
            z6 = false;
        } else {
            z6 = false;
        }
        if (z6) {
            i18 = 0;
        }
        z7 = this.E;
        z8 = !z7.f();
        boolean[] zArr5 = this.H;
        z9 = zArr5[0];
        z10 = zArr5[1];
        i3 = this.h;
        iArr = this.u;
        if (i3 != 2) {
            m10 = ae.h;
            if (m10.j) {
                c0425n8 = this.I;
                if (c0425n8 != null) {
                    c0565roJ2 = lg2.j(c0425n8.z);
                } else {
                    c0565roJ2 = null;
                }
                c0425n9 = this.I;
                if (c0425n9 != null) {
                    c0565roJ3 = lg2.j(c0425n9.x);
                } else {
                    c0565roJ3 = null;
                }
                z11 = z2;
                i4 = i;
                i5 = i2;
                C0565ro c0565ro13 = c0565roJ2;
                z15 = z28;
                boolean z36 = z8;
                c0565ro2 = c0565roJ5;
                z13 = zQ;
                z14 = z3;
                c0565ro = c0565roJ4;
                z12 = z24;
                c0565ro3 = c0565roJ6;
                lg2 = lg;
                c(lg2, true, z11, z15, zArr[0], c0565roJ3, c0565ro13, iArr4[0], z6, this.x, this.z, this.N, i18, this.Q, iArr[0], this.S, z5, z13, z14, z9, i4, i5, this.m, this.n, this.o, z36);
                c0600ss2 = c0600ss;
                z16 = z36;
            } else {
                c0425n8 = this.I;
                if (c0425n8 != null) {
                    c0565roJ2 = lg2.j(c0425n8.z);
                } else {
                    c0565roJ2 = null;
                }
                c0425n9 = this.I;
                if (c0425n9 != null) {
                    c0565roJ3 = lg2.j(c0425n9.x);
                } else {
                    c0565roJ3 = null;
                }
                z11 = z2;
                i4 = i;
                i5 = i2;
                C0565ro c0565ro14 = c0565roJ2;
                z15 = z28;
                boolean z37 = z8;
                c0565ro2 = c0565roJ5;
                z13 = zQ;
                z14 = z3;
                c0565ro = c0565roJ4;
                z12 = z24;
                c0565ro3 = c0565roJ6;
                lg2 = lg;
                c(lg2, true, z11, z15, zArr[0], c0565roJ3, c0565ro14, iArr4[0], z6, this.x, this.z, this.N, i18, this.Q, iArr[0], this.S, z5, z13, z14, z9, i4, i5, this.m, this.n, this.o, z37);
                c0600ss2 = c0600ss;
                z16 = z37;
            }
        } else {
            c0565ro = c0565roJ4;
            c0565ro2 = c0565roJ5;
            z11 = z2;
            z12 = z24;
            c0565ro3 = c0565roJ6;
            i4 = i;
            z13 = zQ;
            z14 = z3;
            z15 = z28;
            z16 = z8;
            i5 = i2;
            c0600ss2 = c0600ss;
        }
        m9 = c0600ss2.h;
        if (m9.j) {
            c0565ro4 = c0565ro3;
            c0565ro5 = r34;
            c0565ro6 = c0565roJ8;
            i6 = 1;
            i7 = 0;
            i8 = 8;
            i9 = 1;
        } else {
            c0565ro4 = c0565ro3;
            c0565ro5 = r34;
            c0565ro6 = c0565roJ8;
            i6 = 1;
            i7 = 0;
            i8 = 8;
            i9 = 1;
        }
        if (this.i == 2) {
            i9 = i7;
        }
        if (i9 != 0) {
            if (iArr4[i6] == 2) {
                i10 = i7;
            } else {
                i10 = i7;
            }
            if (i10 != 0) {
                i11 = i7;
            } else {
                i11 = i21;
            }
            if (z4) {
                z17 = i7;
            } else {
                z17 = i7;
            }
            c0425n7 = this.I;
            if (c0425n7 != null) {
                c0565roJ = lg2.j(c0425n7.A);
            } else {
                c0565roJ = null;
            }
            C0425n7 c0425n19 = this.I;
            if (c0425n19 != null) {
            }
            i12 = this.P;
            int i212 = i6;
            if (i12 <= 0) {
                z18 = z16;
                lg2.e(c0565ro6, c0565ro4, i12, i8);
                obj = z12.d;
                if (obj != null) {
                    lg2.e(c0565ro6, lg2.j(obj), i7, i8);
                    if (z15) {
                        lg2.f(c0565roJ, lg2.j(z23), i7, 5);
                    }
                    z18 = i7;
                } else if (this.V == i8) {
                    z18 = z16;
                    lg2.e(c0565ro6, c0565ro4, i7, i8);
                    z18 = z16;
                }
            } else {
                z18 = z16;
                lg2.e(c0565ro6, c0565ro4, i12, i8);
                obj = z12.d;
                if (obj != null) {
                    lg2.e(c0565ro6, lg2.j(obj), i7, i8);
                    if (z15) {
                        lg2.f(c0565roJ, lg2.j(z23), i7, 5);
                    }
                    z18 = i7;
                } else if (this.V == i8) {
                    z18 = z16;
                    lg2.e(c0565ro6, c0565ro4, i7, i8);
                    z18 = z16;
                }
            }
            z18 = z16;
            z18 = z16;
            c0565ro7 = c0565ro5;
            lg2 = lg;
            c(lg2, false, z15, z11, zArr[i212], c0565roJ9, c0565roJ, iArr4[i212], i10, this.y, this.A, this.O, i11, this.R, iArr[i212], this.T, z17, z14, z13, z10, i5, i4, this.p, this.q, this.r, z18);
        } else {
            c0565ro7 = c0565ro5;
        }
        if (z4) {
            if (this.s == 1) {
                float f8 = this.t;
                C0360l4 c0360l4K13 = lg2.k();
                c0360l4K13.d.b(c0565ro7, -1.0f);
                c0360l4K13.d.b(c0565ro4, 1.0f);
                c0360l4K13.d.b(c0565ro2, f8);
                c0360l4K13.d.b(c0565ro, -f8);
                lg2.c(c0360l4K13);
            } else {
                float f9 = this.t;
                C0360l4 c0360l4K14 = lg2.k();
                c0360l4K14.d.b(c0565ro2, -1.0f);
                c0360l4K14.d.b(c0565ro, 1.0f);
                c0360l4K14.d.b(c0565ro7, f9);
                c0360l4K14.d.b(c0565ro4, -f9);
                lg2.c(c0360l4K14);
            }
        }
        if (r25.f()) {
            C0425n7 c0425n110 = z7.d.b;
            float radians4 = (float) Math.toRadians(this.v + 90.0f);
            int iC4 = z7.c();
            C0565ro c0565roJ1114 = lg2.j(g(2));
            C0565ro c0565roJ1115 = lg2.j(g(3));
            C0565ro c0565roJ1116 = lg2.j(g(4));
            C0565ro c0565roJ1117 = lg2.j(g(5));
            C0565ro c0565roJ1118 = lg2.j(c0425n110.g(2));
            C0565ro c0565roJ1119 = lg2.j(c0425n110.g(3));
            C0565ro c0565roJ11110 = lg2.j(c0425n110.g(4));
            C0565ro c0565roJ11111 = lg2.j(c0425n110.g(5));
            C0360l4 c0360l4K15 = lg2.k();
            double d7 = radians4;
            double dSin4 = Math.sin(d7);
            double d8 = iC4;
            c0360l4K15.d.b(c0565roJ1119, 0.5f);
            c0360l4K15.d.b(c0565roJ11111, 0.5f);
            c0360l4K15.d.b(c0565roJ1115, -0.5f);
            c0360l4K15.d.b(c0565roJ1117, -0.5f);
            c0360l4K15.b = -((float) (dSin4 * d8));
            lg2.c(c0360l4K15);
            C0360l4 c0360l4K16 = lg2.k();
            float fCos4 = (float) (Math.cos(d7) * d8);
            c0360l4K16.d.b(c0565roJ1118, 0.5f);
            c0360l4K16.d.b(c0565roJ11110, 0.5f);
            c0360l4K16.d.b(c0565roJ1114, -0.5f);
            c0360l4K16.d.b(c0565roJ1116, -0.5f);
            c0360l4K16.b = -fCos4;
            lg2.c(c0360l4K16);
        }
    }

    public boolean b() {
        return this.V != 8;
    }

    /* JADX WARN: Code duplicated, block: B:170:0x02a9  */
    /* JADX WARN: Code duplicated, block: B:172:0x02af  */
    /* JADX WARN: Code duplicated, block: B:174:0x02b5  */
    /* JADX WARN: Code duplicated, block: B:176:0x02da  */
    /* JADX WARN: Code duplicated, block: B:180:0x02f1 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:181:0x02f3  */
    /* JADX WARN: Code duplicated, block: B:191:0x030b  */
    /* JADX WARN: Code duplicated, block: B:194:0x0323  */
    /* JADX WARN: Code duplicated, block: B:202:0x0339  */
    /* JADX WARN: Code duplicated, block: B:204:0x0341  */
    /* JADX WARN: Code duplicated, block: B:215:0x0356  */
    /* JADX WARN: Code duplicated, block: B:220:0x0360  */
    /* JADX WARN: Code duplicated, block: B:222:0x0364  */
    /* JADX WARN: Code duplicated, block: B:223:0x0366  */
    /* JADX WARN: Code duplicated, block: B:226:0x036e  */
    /* JADX WARN: Code duplicated, block: B:232:0x037c A[PHI: r8
  0x037c: PHI (r8v4 int) = (r8v3 int), (r8v5 int), (r8v5 int), (r8v5 int) binds: [B:225:0x036c, B:227:0x0372, B:228:0x0374, B:230:0x0378] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:235:0x038e A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:236:0x0390  */
    /* JADX WARN: Code duplicated, block: B:237:0x0395  */
    /* JADX WARN: Code duplicated, block: B:239:0x0398  */
    /* JADX WARN: Code duplicated, block: B:248:0x03b0  */
    /* JADX WARN: Code duplicated, block: B:259:0x03cd A[ADDED_TO_REGION, REMOVE] */
    /* JADX WARN: Code duplicated, block: B:261:0x03d8 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:280:0x040f  */
    /* JADX WARN: Code duplicated, block: B:288:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:292:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:93:0x017b A[ADDED_TO_REGION] */
    public final void c(Lg lg, boolean z, boolean z2, boolean z3, boolean z4, C0565ro c0565ro, C0565ro c0565ro2, int i, boolean z5, Z6 z6, Z6 z7, int i2, int i3, int i4, int i5, float f, boolean z8, boolean z9, boolean z10, boolean z11, int i6, int i7, int i8, int i9, float f2, boolean z12) {
        boolean z13;
        int iMin;
        int i10;
        int i11;
        boolean z14;
        C0565ro c0565roJ;
        C0565ro c0565roJ2;
        char c;
        boolean z15;
        Z6 z16;
        C0565ro c0565ro3;
        boolean z17;
        boolean z18;
        boolean z19;
        int i12;
        boolean z20;
        int i13;
        boolean z21;
        boolean z22;
        C0425n7 c0425n7;
        C0565ro c0565ro4;
        C0565ro c0565ro5;
        C0565ro c0565ro6;
        int i14;
        C0425n7 c0425n8;
        C0425n7 c0425n9;
        int iMin2;
        int i15;
        boolean z23;
        int iC;
        int i16;
        int i17;
        int i18;
        int i19;
        int i20;
        int i21;
        boolean z24;
        boolean z25;
        int i22;
        Lg lg2 = lg;
        int i23 = i8;
        int i24 = i9;
        C0565ro c0565roJ3 = lg2.j(z6);
        C0565ro c0565roJ4 = lg2.j(z7);
        C0565ro c0565roJ5 = lg2.j(z6.d);
        C0565ro c0565roJ6 = lg2.j(z7.d);
        boolean zF = z6.f();
        boolean zF2 = z7.f();
        boolean zF3 = this.E.f();
        int i25 = zF2 ? (zF ? 1 : 0) + 1 : zF ? 1 : 0;
        if (zF3) {
            i25++;
        }
        int i26 = i25;
        int i27 = z8 ? 3 : i6;
        int iJ = AbstractC0535qo.j(i);
        boolean z26 = (iJ == 0 || iJ == 1 || iJ != 2 || i27 == 4) ? false : true;
        if (this.V == 8) {
            iMin = 0;
            z13 = false;
        } else {
            z13 = z26;
            iMin = i3;
        }
        if (z12) {
            if (!zF && !zF2 && !zF3) {
                lg2.d(c0565roJ3, i2);
            } else if (zF && !zF2) {
                i10 = 8;
                lg2.e(c0565roJ3, c0565roJ5, z6.c(), 8);
            }
            i10 = 8;
        } else {
            i10 = 8;
        }
        if (z13 != 0) {
            if (i26 == 2 || z8 || !(i27 == 1 || i27 == 0)) {
                if (i23 == -2) {
                    i23 = iMin;
                }
                if (i24 == -2) {
                    i24 = iMin;
                }
                if (iMin > 0 && i27 != 1) {
                    iMin = 0;
                }
                if (i23 > 0) {
                    lg2.f(c0565roJ4, c0565roJ3, i23, 8);
                    iMin = Math.max(iMin, i23);
                }
                if (i24 > 0) {
                    if (!z2 || i27 != 1) {
                        lg2.g(c0565roJ4, c0565roJ3, i24, 8);
                    }
                    iMin = Math.min(iMin, i24);
                }
                if (i27 == 1) {
                    if (z2) {
                        lg2.e(c0565roJ4, c0565roJ3, iMin, 8);
                    } else if (z9) {
                        lg2.e(c0565roJ4, c0565roJ3, iMin, 5);
                        lg2.g(c0565roJ4, c0565roJ3, iMin, 8);
                    } else {
                        lg2.e(c0565roJ4, c0565roJ3, iMin, 5);
                        lg2.g(c0565roJ4, c0565roJ3, iMin, 8);
                    }
                } else if (i27 == 2) {
                    int i28 = z6.c;
                    if (i28 == 3 || i28 == 5) {
                        c0565roJ = lg2.j(this.I.g(3));
                        c0565roJ2 = lg2.j(this.I.g(5));
                    } else {
                        c0565roJ = lg2.j(this.I.g(2));
                        c0565roJ2 = lg2.j(this.I.g(4));
                    }
                    C0360l4 c0360l4K = lg2.k();
                    c0360l4K.d.b(c0565roJ4, -1.0f);
                    c0360l4K.d.b(c0565roJ3, 1.0f);
                    c0360l4K.d.b(c0565roJ2, f2);
                    c0360l4K.d.b(c0565roJ, -f2);
                    lg2.c(c0360l4K);
                    i11 = i23;
                } else {
                    i11 = i23;
                    z14 = z13;
                    z4 = true;
                }
                if (z12 || z9) {
                    c = 2;
                    if (i26 >= c && z2 && z4) {
                        lg2.f(c0565roJ3, c0565ro, 0, 8);
                        Z6 z27 = this.B;
                        boolean z28 = z || z27.d == null;
                        if (z || (z16 = z27.d) == null) {
                            z15 = z28;
                        } else {
                            C0425n7 c0425n10 = z16.b;
                            if (c0425n10.L != 0.0f) {
                                int[] iArr = c0425n10.c0;
                                if (iArr[0] == 3 && iArr[1] == 3) {
                                    z15 = true;
                                } else {
                                    z15 = false;
                                }
                            } else {
                                z15 = false;
                            }
                        }
                        if (z15) {
                            lg2.f(c0565ro2, c0565roJ4, 0, 8);
                            return;
                        }
                        return;
                    }
                    return;
                }
                if ((zF || zF2 || zF3) && (!zF || zF2)) {
                    if (zF || !zF2) {
                        c0565ro3 = c0565roJ6;
                        if (zF && zF2) {
                            C0425n7 c0425n11 = z6.d.b;
                            C0425n7 c0425n12 = z7.d.b;
                            z17 = z4;
                            C0425n7 c0425n13 = this.I;
                            int i29 = 6;
                            if (z14) {
                                if (i27 == 0) {
                                    if (i24 == 0 && i11 == 0) {
                                        i21 = 8;
                                        z24 = false;
                                        z25 = true;
                                        i22 = 8;
                                    } else {
                                        i21 = 5;
                                        z24 = true;
                                        z25 = false;
                                        i22 = 5;
                                    }
                                    if ((c0425n11 instanceof P4) || (c0425n12 instanceof P4)) {
                                        z19 = z24;
                                        i13 = i22;
                                        z18 = false;
                                        i27 = i27;
                                        i29 = 6;
                                        z20 = z25;
                                        i12 = 4;
                                    } else {
                                        i13 = i22;
                                        z18 = false;
                                        z20 = z25;
                                        i12 = i21;
                                        z19 = z24;
                                        i27 = i27;
                                        i29 = 6;
                                    }
                                } else {
                                    if (i27 == 1) {
                                        i27 = i27;
                                        i29 = 6;
                                        z18 = true;
                                        z19 = true;
                                        i12 = 4;
                                        z20 = false;
                                    } else if (i27 == 3) {
                                        i27 = i27;
                                        if (this.s == -1) {
                                            if (z10) {
                                                i29 = z2 ? 5 : 4;
                                            } else {
                                                i29 = 8;
                                            }
                                            z18 = true;
                                            z19 = true;
                                            i12 = 5;
                                            z20 = true;
                                        } else {
                                            if (z8) {
                                                if (i7 == 2 || i7 == 1) {
                                                    i19 = 4;
                                                    i20 = 5;
                                                } else {
                                                    i19 = 5;
                                                    i20 = 8;
                                                }
                                                i12 = i19;
                                                i13 = i20;
                                                z18 = true;
                                                z19 = true;
                                            } else {
                                                if (i24 > 0) {
                                                    z18 = true;
                                                    z19 = true;
                                                    i12 = 5;
                                                } else if (i24 != 0 || i11 != 0) {
                                                    z18 = true;
                                                    z19 = true;
                                                    i12 = 4;
                                                } else if (z10) {
                                                    i13 = (c0425n11 == c0425n13 || c0425n12 == c0425n13) ? 5 : 4;
                                                    z18 = true;
                                                    z19 = true;
                                                    i12 = 4;
                                                } else {
                                                    z18 = true;
                                                    z19 = true;
                                                    i12 = 8;
                                                }
                                                z20 = true;
                                                i13 = 5;
                                            }
                                            z20 = true;
                                        }
                                    } else {
                                        z18 = false;
                                        z19 = false;
                                    }
                                    i13 = 8;
                                }
                                if (z18 || c0565roJ5 != c0565ro3 || c0425n11 == c0425n13) {
                                    z21 = z18;
                                    z22 = true;
                                } else {
                                    z21 = false;
                                    z22 = false;
                                }
                                if (z19) {
                                    if (this.V == 8) {
                                        i29 = 4;
                                    }
                                    c0565ro4 = c0565roJ3;
                                    c0565ro5 = c0565roJ4;
                                    int i30 = i29;
                                    c0565ro6 = c0565roJ5;
                                    c0425n7 = c0425n11;
                                    lg2 = lg;
                                    i14 = 8;
                                    lg2.b(c0565ro4, c0565ro6, z6.c(), f, c0565ro3, c0565ro5, z7.c(), i30);
                                } else {
                                    c0425n7 = c0425n11;
                                    c0565ro4 = c0565roJ3;
                                    c0565ro5 = c0565roJ4;
                                    c0565ro6 = c0565roJ5;
                                    i14 = 8;
                                    lg2 = lg;
                                }
                                if (this.V == i14) {
                                    return;
                                }
                                if (z21) {
                                    if (z2 || c0565ro6 == c0565ro3 || z14) {
                                        c0425n8 = c0425n7;
                                        c0425n9 = c0425n12;
                                    } else {
                                        c0425n8 = c0425n7;
                                        if (c0425n8 instanceof P4) {
                                            c0425n9 = c0425n12;
                                        } else {
                                            c0425n9 = c0425n12;
                                            if (c0425n9 instanceof P4) {
                                            }
                                            lg2.f(c0565ro4, c0565ro6, z6.c(), i18);
                                            lg2.g(c0565ro5, c0565ro3, -z7.c(), i18);
                                            i13 = i18;
                                        }
                                        i18 = 6;
                                        lg2.f(c0565ro4, c0565ro6, z6.c(), i18);
                                        lg2.g(c0565ro5, c0565ro3, -z7.c(), i18);
                                        i13 = i18;
                                    }
                                    i18 = i13;
                                    lg2.f(c0565ro4, c0565ro6, z6.c(), i18);
                                    lg2.g(c0565ro5, c0565ro3, -z7.c(), i18);
                                    i13 = i18;
                                } else {
                                    c0425n8 = c0425n7;
                                    c0425n9 = c0425n12;
                                }
                                if (z2 || !z11 || (c0425n8 instanceof P4) || (c0425n9 instanceof P4)) {
                                    iMin2 = i12;
                                    i15 = i13;
                                    z23 = z22;
                                } else {
                                    iMin2 = 6;
                                    i15 = 6;
                                    z23 = true;
                                }
                                if (z23) {
                                    if (z20 && (!z10 || z3)) {
                                        if (c0425n8 != c0425n13 && c0425n9 != c0425n13) {
                                            i29 = iMin2;
                                        }
                                        if ((c0425n8 instanceof Xd) || (c0425n9 instanceof Xd)) {
                                            i29 = 5;
                                        }
                                        if ((c0425n8 instanceof P4) || (c0425n9 instanceof P4)) {
                                            i29 = 5;
                                        }
                                        if (z10) {
                                            i17 = 5;
                                        } else {
                                            i17 = i29;
                                        }
                                        iMin2 = Math.max(i17, iMin2);
                                    }
                                    if (z2) {
                                        iMin2 = Math.min(i15, iMin2);
                                        if (z8 || z10 || !(c0425n8 == c0425n13 || c0425n9 == c0425n13)) {
                                            i16 = iMin2;
                                        } else {
                                            i16 = 4;
                                        }
                                    } else {
                                        i16 = iMin2;
                                    }
                                    lg2.e(c0565ro4, c0565ro6, z6.c(), i16);
                                    lg2.e(c0565ro5, c0565ro3, -z7.c(), i16);
                                }
                                if (z2) {
                                    if (c0565ro == c0565ro6) {
                                        iC = z6.c();
                                    } else {
                                        iC = 0;
                                    }
                                    if (c0565ro6 != c0565ro) {
                                        lg2.f(c0565ro4, c0565ro, iC, 5);
                                    }
                                }
                                if (z2 && z14 && i4 == 0 && i11 == 0) {
                                    if (z14 || i27 != 3) {
                                        lg2.f(c0565ro5, c0565ro4, 0, 5);
                                    } else {
                                        lg2.f(c0565ro5, c0565ro4, 0, 8);
                                    }
                                }
                            } else {
                                z18 = true;
                                z19 = true;
                            }
                            i12 = 4;
                            z20 = false;
                            i13 = 5;
                            if (z18) {
                                z21 = z18;
                                z22 = true;
                            } else {
                                z21 = z18;
                                z22 = true;
                            }
                            if (z19) {
                                if (this.V == 8) {
                                    i29 = 4;
                                }
                                c0565ro4 = c0565roJ3;
                                c0565ro5 = c0565roJ4;
                                int i31 = i29;
                                c0565ro6 = c0565roJ5;
                                c0425n7 = c0425n11;
                                lg2 = lg;
                                i14 = 8;
                                lg2.b(c0565ro4, c0565ro6, z6.c(), f, c0565ro3, c0565ro5, z7.c(), i31);
                            } else {
                                c0425n7 = c0425n11;
                                c0565ro4 = c0565roJ3;
                                c0565ro5 = c0565roJ4;
                                c0565ro6 = c0565roJ5;
                                i14 = 8;
                                lg2 = lg;
                            }
                            if (this.V == i14) {
                                return;
                            }
                            if (z21) {
                                if (z2) {
                                    c0425n8 = c0425n7;
                                    c0425n9 = c0425n12;
                                    i18 = i13;
                                } else {
                                    c0425n8 = c0425n7;
                                    c0425n9 = c0425n12;
                                    i18 = i13;
                                }
                                lg2.f(c0565ro4, c0565ro6, z6.c(), i18);
                                lg2.g(c0565ro5, c0565ro3, -z7.c(), i18);
                                i13 = i18;
                            } else {
                                c0425n8 = c0425n7;
                                c0425n9 = c0425n12;
                            }
                            if (z2) {
                                iMin2 = i12;
                                i15 = i13;
                                z23 = z22;
                            } else {
                                iMin2 = i12;
                                i15 = i13;
                                z23 = z22;
                            }
                            if (z23) {
                                if (z20) {
                                    if (c0425n8 != c0425n13) {
                                        i29 = iMin2;
                                    }
                                    if (c0425n8 instanceof Xd) {
                                        i29 = 5;
                                    } else {
                                        i29 = 5;
                                    }
                                    if (c0425n8 instanceof P4) {
                                        i29 = 5;
                                    } else {
                                        i29 = 5;
                                    }
                                    if (z10) {
                                        i17 = 5;
                                    } else {
                                        i17 = i29;
                                    }
                                    iMin2 = Math.max(i17, iMin2);
                                }
                                if (z2) {
                                    iMin2 = Math.min(i15, iMin2);
                                    if (z8) {
                                        i16 = iMin2;
                                    } else {
                                        i16 = iMin2;
                                    }
                                } else {
                                    i16 = iMin2;
                                }
                                lg2.e(c0565ro4, c0565ro6, z6.c(), i16);
                                lg2.e(c0565ro5, c0565ro3, -z7.c(), i16);
                            }
                            if (z2) {
                                if (c0565ro == c0565ro6) {
                                    iC = z6.c();
                                } else {
                                    iC = 0;
                                }
                                if (c0565ro6 != c0565ro) {
                                    lg2.f(c0565ro4, c0565ro, iC, 5);
                                }
                            }
                            if (z2) {
                                if (z14) {
                                    lg2.f(c0565ro5, c0565ro4, 0, 5);
                                } else {
                                    lg2.f(c0565ro5, c0565ro4, 0, 5);
                                }
                            }
                        }
                    } else {
                        c0565ro3 = c0565roJ6;
                        lg2.e(c0565roJ4, c0565ro3, -z7.c(), 8);
                        if (z2) {
                            lg2.f(c0565roJ3, c0565ro, 0, 5);
                        }
                    }
                    z7 = z7;
                    c0565ro5 = c0565roJ4;
                    z17 = z4;
                } else {
                    z7 = z7;
                    c0565ro5 = c0565roJ4;
                    z17 = z4;
                    c0565ro3 = c0565roJ6;
                }
                if (z2 && z17) {
                    int iC2 = z7.d != null ? z7.c() : 0;
                    if (c0565ro3 != c0565ro2) {
                        lg2.f(c0565ro2, c0565ro5, iC2, 5);
                        return;
                    }
                    return;
                }
                return;
            }
            int iMax = Math.max(i23, iMin);
            if (i24 > 0) {
                iMax = Math.min(i24, iMax);
            }
            lg2.e(c0565roJ4, c0565roJ3, iMax, 8);
            i11 = i23;
            z14 = false;
            if (z12) {
                c = 2;
            } else {
                c = 2;
            }
            if (i26 >= c) {
            }
        }
        if (z5) {
            lg2.e(c0565roJ4, c0565roJ3, 0, 3);
            if (i4 > 0) {
                lg2.f(c0565roJ4, c0565roJ3, i4, i10);
            }
            if (i5 < Integer.MAX_VALUE) {
                lg2.g(c0565roJ4, c0565roJ3, i5, i10);
            }
        } else {
            lg2.e(c0565roJ4, c0565roJ3, iMin, i10);
        }
        z4 = z4;
        z14 = z13;
        i11 = i23;
        if (z12) {
            c = 2;
        } else {
            c = 2;
        }
        if (i26 >= c) {
        }
    }

    public final void d(int i, C0425n7 c0425n7, int i2, int i3) {
        boolean z;
        if (i == 7) {
            if (i2 != 7) {
                if (i2 == 2 || i2 == 4) {
                    d(2, c0425n7, i2, 0);
                    d(4, c0425n7, i2, 0);
                    g(7).a(c0425n7.g(i2), 0);
                    return;
                } else {
                    if (i2 == 3 || i2 == 5) {
                        d(3, c0425n7, i2, 0);
                        d(5, c0425n7, i2, 0);
                        g(7).a(c0425n7.g(i2), 0);
                        return;
                    }
                    return;
                }
            }
            Z6 z6G = g(2);
            Z6 z6G2 = g(4);
            Z6 z6G3 = g(3);
            Z6 z6G4 = g(5);
            boolean z2 = true;
            if ((z6G == null || !z6G.f()) && (z6G2 == null || !z6G2.f())) {
                d(2, c0425n7, 2, 0);
                d(4, c0425n7, 4, 0);
                z = true;
            } else {
                z = false;
            }
            if ((z6G3 == null || !z6G3.f()) && (z6G4 == null || !z6G4.f())) {
                d(3, c0425n7, 3, 0);
                d(5, c0425n7, 5, 0);
            } else {
                z2 = false;
            }
            if (z && z2) {
                g(7).a(c0425n7.g(7), 0);
                return;
            } else if (z) {
                g(8).a(c0425n7.g(8), 0);
                return;
            } else {
                if (z2) {
                    g(9).a(c0425n7.g(9), 0);
                    return;
                }
                return;
            }
        }
        if (i == 8 && (i2 == 2 || i2 == 4)) {
            Z6 z6G5 = g(2);
            Z6 z6G6 = c0425n7.g(i2);
            Z6 z6G7 = g(4);
            z6G5.a(z6G6, 0);
            z6G7.a(z6G6, 0);
            g(8).a(z6G6, 0);
            return;
        }
        if (i == 9 && (i2 == 3 || i2 == 5)) {
            Z6 z6G8 = c0425n7.g(i2);
            g(3).a(z6G8, 0);
            g(5).a(z6G8, 0);
            g(9).a(z6G8, 0);
            return;
        }
        if (i == 8 && i2 == 8) {
            g(2).a(c0425n7.g(2), 0);
            g(4).a(c0425n7.g(4), 0);
            g(8).a(c0425n7.g(i2), 0);
            return;
        }
        if (i == 9 && i2 == 9) {
            g(3).a(c0425n7.g(3), 0);
            g(5).a(c0425n7.g(5), 0);
            g(9).a(c0425n7.g(i2), 0);
            return;
        }
        Z6 z6G9 = g(i);
        Z6 z6G10 = c0425n7.g(i2);
        if (z6G9.g(z6G10)) {
            if (i == 6) {
                Z6 z6G11 = g(3);
                Z6 z6G12 = g(5);
                if (z6G11 != null) {
                    z6G11.h();
                }
                if (z6G12 != null) {
                    z6G12.h();
                }
                i3 = 0;
            } else if (i == 3 || i == 5) {
                Z6 z6G13 = g(6);
                if (z6G13 != null) {
                    z6G13.h();
                }
                Z6 z6G14 = g(7);
                if (z6G14.d != z6G10) {
                    z6G14.h();
                }
                Z6 z6D = g(i).d();
                Z6 z6G15 = g(9);
                if (z6G15.f()) {
                    z6D.h();
                    z6G15.h();
                }
            } else if (i == 2 || i == 4) {
                Z6 z6G16 = g(7);
                if (z6G16.d != z6G10) {
                    z6G16.h();
                }
                Z6 z6D2 = g(i).d();
                Z6 z6G17 = g(8);
                if (z6G17.f()) {
                    z6D2.h();
                    z6G17.h();
                }
            }
            z6G9.a(z6G10, i3);
        }
    }

    public final void e(Z6 z6, Z6 z7, int i) {
        if (z6.b == this) {
            d(z6.c, z7.b, z7.c, i);
        }
    }

    public final void f(Lg lg) {
        lg.j(this.x);
        lg.j(this.y);
        lg.j(this.z);
        lg.j(this.A);
        if (this.P > 0) {
            lg.j(this.B);
        }
    }

    public Z6 g(int i) {
        switch (AbstractC0535qo.j(i)) {
            case 0:
                return null;
            case 1:
                return this.x;
            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                return this.y;
            case 3:
                return this.z;
            case 4:
                return this.A;
            case BulletSpan.STANDARD_BULLET_RADIUS /* 5 */:
                return this.B;
            case 6:
                return this.E;
            case 7:
                return this.C;
            case 8:
                return this.D;
            default:
                throw new AssertionError(AbstractC0535qo.i(i));
        }
    }

    public final int h(int i) {
        int[] iArr = this.c0;
        if (i == 0) {
            return iArr[0];
        }
        if (i == 1) {
            return iArr[1];
        }
        return 0;
    }

    public final int i() {
        if (this.V == 8) {
            return 0;
        }
        return this.K;
    }

    public final C0425n7 j(int i) {
        Z6 z6;
        Z6 z7;
        if (i != 0) {
            if (i == 1 && (z7 = (z6 = this.A).d) != null && z7.d == z6) {
                return z7.b;
            }
            return null;
        }
        Z6 z8 = this.z;
        Z6 z9 = z8.d;
        if (z9 == null || z9.d != z8) {
            return null;
        }
        return z9.b;
    }

    public final C0425n7 k(int i) {
        Z6 z6;
        Z6 z7;
        if (i != 0) {
            if (i == 1 && (z7 = (z6 = this.y).d) != null && z7.d == z6) {
                return z7.b;
            }
            return null;
        }
        Z6 z8 = this.x;
        Z6 z9 = z8.d;
        if (z9 == null || z9.d != z8) {
            return null;
        }
        return z9.b;
    }

    public final int l() {
        if (this.V == 8) {
            return 0;
        }
        return this.J;
    }

    public final int m() {
        C0425n7 c0425n7 = this.I;
        return (c0425n7 == null || !(c0425n7 instanceof C0456o7)) ? this.N : ((C0456o7) c0425n7).j0 + this.N;
    }

    public final int n() {
        C0425n7 c0425n7 = this.I;
        return (c0425n7 == null || !(c0425n7 instanceof C0456o7)) ? this.O : ((C0456o7) c0425n7).k0 + this.O;
    }

    public final void o(int i, int i2, int i3, int i4, C0425n7 c0425n7) {
        g(i).b(c0425n7.g(i2), i3, i4, true);
    }

    public final boolean p(int i) {
        Z6 z6;
        Z6 z7;
        int i2 = i * 2;
        Z6[] z6Arr = this.F;
        Z6 z8 = z6Arr[i2];
        Z6 z9 = z8.d;
        return (z9 == null || z9.d == z8 || (z7 = (z6 = z6Arr[i2 + 1]).d) == null || z7.d != z6) ? false : true;
    }

    public final boolean q() {
        Z6 z6 = this.x;
        Z6 z7 = z6.d;
        if (z7 != null && z7.d == z6) {
            return true;
        }
        Z6 z8 = this.z;
        Z6 z9 = z8.d;
        return z9 != null && z9.d == z8;
    }

    public final boolean r() {
        Z6 z6 = this.y;
        Z6 z7 = z6.d;
        if (z7 != null && z7.d == z6) {
            return true;
        }
        Z6 z8 = this.A;
        Z6 z9 = z8.d;
        return z9 != null && z9.d == z8;
    }

    public void s() {
        this.x.h();
        this.y.h();
        this.z.h();
        this.A.h();
        this.B.h();
        this.C.h();
        this.D.h();
        this.E.h();
        this.I = null;
        this.v = 0.0f;
        this.J = 0;
        this.K = 0;
        this.L = 0.0f;
        this.M = -1;
        this.N = 0;
        this.O = 0;
        this.P = 0;
        this.Q = 0;
        this.R = 0;
        this.S = 0.5f;
        this.T = 0.5f;
        int[] iArr = this.c0;
        iArr[0] = 1;
        iArr[1] = 1;
        this.U = null;
        this.V = 0;
        this.X = 0;
        this.Y = 0;
        float[] fArr = this.Z;
        fArr[0] = -1.0f;
        fArr[1] = -1.0f;
        this.h = -1;
        this.i = -1;
        int[] iArr2 = this.u;
        iArr2[0] = Integer.MAX_VALUE;
        iArr2[1] = Integer.MAX_VALUE;
        this.j = 0;
        this.k = 0;
        this.o = 1.0f;
        this.r = 1.0f;
        this.n = Integer.MAX_VALUE;
        this.q = Integer.MAX_VALUE;
        this.m = 0;
        this.p = 0;
        this.s = -1;
        this.t = 1.0f;
        boolean[] zArr = this.f;
        zArr[0] = true;
        zArr[1] = true;
        boolean[] zArr2 = this.H;
        zArr2[0] = false;
        zArr2[1] = false;
    }

    public final void t() {
        C0425n7 c0425n7 = this.I;
        if (c0425n7 != null && (c0425n7 instanceof C0456o7)) {
            ((C0456o7) c0425n7).getClass();
        }
        ArrayList arrayList = this.G;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            ((Z6) arrayList.get(i)).h();
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        String str = "";
        sb.append("");
        if (this.W != null) {
            str = "id: " + this.W + " ";
        }
        sb.append(str);
        sb.append("(");
        sb.append(this.N);
        sb.append(", ");
        sb.append(this.O);
        sb.append(") - (");
        sb.append(this.J);
        sb.append(" x ");
        sb.append(this.K);
        sb.append(")");
        return sb.toString();
    }

    public void u(Xn xn) {
        this.x.i();
        this.y.i();
        this.z.i();
        this.A.i();
        this.B.i();
        this.E.i();
        this.C.i();
        this.D.i();
    }

    public final void v(int i) {
        this.K = i;
        int i2 = this.R;
        if (i < i2) {
            this.K = i2;
        }
    }

    public final void w(int i) {
        this.c0[0] = i;
    }

    public final void x(int i) {
        this.c0[1] = i;
    }

    public final void y(int i) {
        this.J = i;
        int i2 = this.Q;
        if (i < i2) {
            this.J = i2;
        }
    }

    public void z(boolean z, boolean z2) {
        int i;
        int i2;
        Ae ae = this.d;
        boolean z3 = z & ae.g;
        C0600ss c0600ss = this.e;
        boolean z4 = z2 & c0600ss.g;
        int i3 = ae.h.g;
        int i4 = c0600ss.h.g;
        int i5 = ae.i.g;
        int i6 = c0600ss.i.g;
        int i7 = i6 - i4;
        if (i5 - i3 < 0 || i7 < 0 || i3 == Integer.MIN_VALUE || i3 == Integer.MAX_VALUE || i4 == Integer.MIN_VALUE || i4 == Integer.MAX_VALUE || i5 == Integer.MIN_VALUE || i5 == Integer.MAX_VALUE || i6 == Integer.MIN_VALUE || i6 == Integer.MAX_VALUE) {
            i5 = 0;
            i6 = 0;
            i3 = 0;
            i4 = 0;
        }
        int i8 = i5 - i3;
        int i9 = i6 - i4;
        if (z3) {
            this.N = i3;
        }
        if (z4) {
            this.O = i4;
        }
        if (this.V == 8) {
            this.J = 0;
            this.K = 0;
            return;
        }
        int[] iArr = this.c0;
        if (z3) {
            if (iArr[0] == 1 && i8 < (i2 = this.J)) {
                i8 = i2;
            }
            this.J = i8;
            int i10 = this.Q;
            if (i8 < i10) {
                this.J = i10;
            }
        }
        if (z4) {
            if (iArr[1] == 1 && i9 < (i = this.K)) {
                i9 = i;
            }
            this.K = i9;
            int i11 = this.R;
            if (i9 < i11) {
                this.K = i11;
            }
        }
    }
}
