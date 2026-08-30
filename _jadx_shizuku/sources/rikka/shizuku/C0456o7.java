package rikka.shizuku;

import java.util.ArrayList;
import java.util.Arrays;

/* JADX INFO: renamed from: rikka.shizuku.o7, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0456o7 extends C0425n7 {
    public ArrayList d0 = new ArrayList();
    public final K3 e0 = new K3(this);
    public final L9 f0;
    public C0148e7 g0;
    public boolean h0;
    public final Lg i0;
    public int j0;
    public int k0;
    public int l0;
    public int m0;
    public I5[] n0;
    public I5[] o0;
    public int p0;
    public boolean q0;
    public boolean r0;

    public C0456o7() {
        L9 l9 = new L9();
        l9.b = true;
        l9.c = true;
        l9.e = new ArrayList();
        new ArrayList();
        l9.f = null;
        l9.g = new Z4();
        l9.h = new ArrayList();
        l9.a = this;
        l9.d = this;
        this.f0 = l9;
        this.g0 = null;
        this.h0 = false;
        this.i0 = new Lg();
        this.l0 = 0;
        this.m0 = 0;
        this.n0 = new I5[4];
        this.o0 = new I5[4];
        this.p0 = 263;
        this.q0 = false;
        this.r0 = false;
    }

    public final void B(C0425n7 c0425n7, int i) {
        if (i == 0) {
            int i2 = this.l0 + 1;
            I5[] i5Arr = this.o0;
            if (i2 >= i5Arr.length) {
                this.o0 = (I5[]) Arrays.copyOf(i5Arr, i5Arr.length * 2);
            }
            I5[] i5Arr2 = this.o0;
            int i3 = this.l0;
            i5Arr2[i3] = new I5(c0425n7, 0, this.h0);
            this.l0 = i3 + 1;
            return;
        }
        if (i == 1) {
            int i4 = this.m0 + 1;
            I5[] i5Arr3 = this.n0;
            if (i4 >= i5Arr3.length) {
                this.n0 = (I5[]) Arrays.copyOf(i5Arr3, i5Arr3.length * 2);
            }
            I5[] i5Arr4 = this.n0;
            int i5 = this.m0;
            i5Arr4[i5] = new I5(c0425n7, 1, this.h0);
            this.m0 = i5 + 1;
        }
    }

    public final void C(Lg lg) {
        a(lg);
        int size = this.d0.size();
        boolean z = false;
        for (int i = 0; i < size; i++) {
            C0425n7 c0425n7 = (C0425n7) this.d0.get(i);
            boolean[] zArr = c0425n7.H;
            zArr[0] = false;
            zArr[1] = false;
            if (c0425n7 instanceof P4) {
                z = true;
            }
        }
        if (z) {
            for (int i2 = 0; i2 < size; i2++) {
                C0425n7 c0425n8 = (C0425n7) this.d0.get(i2);
                if (c0425n8 instanceof P4) {
                    P4 p4 = (P4) c0425n8;
                    for (int i3 = 0; i3 < p4.e0; i3++) {
                        C0425n7 c0425n9 = p4.d0[i3];
                        int i4 = p4.f0;
                        if (i4 == 0 || i4 == 1) {
                            c0425n9.H[0] = true;
                        } else if (i4 == 2 || i4 == 3) {
                            c0425n9.H[1] = true;
                        }
                    }
                }
            }
        }
        for (int i5 = 0; i5 < size; i5++) {
            C0425n7 c0425n10 = (C0425n7) this.d0.get(i5);
            c0425n10.getClass();
            if ((c0425n10 instanceof C0153ec) || (c0425n10 instanceof Xd)) {
                c0425n10.a(lg);
            }
        }
        for (int i6 = 0; i6 < size; i6++) {
            C0425n7 c0425n11 = (C0425n7) this.d0.get(i6);
            if (c0425n11 instanceof C0456o7) {
                int[] iArr = c0425n11.c0;
                int i7 = iArr[0];
                int i8 = iArr[1];
                if (i7 == 2) {
                    c0425n11.w(1);
                }
                if (i8 == 2) {
                    c0425n11.x(1);
                }
                c0425n11.a(lg);
                if (i7 == 2) {
                    c0425n11.w(i7);
                }
                if (i8 == 2) {
                    c0425n11.x(i8);
                }
            } else {
                c0425n11.h = -1;
                c0425n11.i = -1;
                int[] iArr2 = this.c0;
                int i9 = iArr2[0];
                int[] iArr3 = c0425n11.c0;
                if (i9 != 2 && iArr3[0] == 4) {
                    Z6 z6 = c0425n11.x;
                    int i10 = z6.e;
                    int iL = l();
                    Z6 z7 = c0425n11.z;
                    int i11 = iL - z7.e;
                    z6.g = lg.j(z6);
                    z7.g = lg.j(z7);
                    lg.d(z6.g, i10);
                    lg.d(z7.g, i11);
                    c0425n11.h = 2;
                    c0425n11.N = i10;
                    int i12 = i11 - i10;
                    c0425n11.J = i12;
                    int i13 = c0425n11.Q;
                    if (i12 < i13) {
                        c0425n11.J = i13;
                    }
                }
                if (iArr2[1] != 2 && iArr3[1] == 4) {
                    Z6 z8 = c0425n11.y;
                    int i14 = z8.e;
                    int i15 = i();
                    Z6 z9 = c0425n11.A;
                    int i16 = i15 - z9.e;
                    z8.g = lg.j(z8);
                    z9.g = lg.j(z9);
                    lg.d(z8.g, i14);
                    lg.d(z9.g, i16);
                    if (c0425n11.P > 0 || c0425n11.V == 8) {
                        Z6 z10 = c0425n11.B;
                        C0565ro c0565roJ = lg.j(z10);
                        z10.g = c0565roJ;
                        lg.d(c0565roJ, c0425n11.P + i14);
                    }
                    c0425n11.i = 2;
                    c0425n11.O = i14;
                    int i17 = i16 - i14;
                    c0425n11.K = i17;
                    int i18 = c0425n11.R;
                    if (i17 < i18) {
                        c0425n11.K = i18;
                    }
                }
                if (!(c0425n11 instanceof C0153ec) && !(c0425n11 instanceof Xd)) {
                    c0425n11.a(lg);
                }
            }
        }
        if (this.l0 > 0) {
            X8.g(this, lg, 0);
        }
        if (this.m0 > 0) {
            X8.g(this, lg, 1);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean D(int i, boolean z) {
        boolean z2;
        int i2;
        int i3;
        boolean z3;
        boolean z4;
        L9 l9 = this.f0;
        C0456o7 c0456o7 = l9.a;
        int iH = c0456o7.h(0);
        int iH2 = c0456o7.h(1);
        int iM = c0456o7.m();
        int iN = c0456o7.n();
        ArrayList arrayList = l9.e;
        C0600ss c0600ss = c0456o7.e;
        Ae ae = c0456o7.d;
        if (z && (iH == 2 || iH2 == 2)) {
            int size = arrayList.size();
            int i4 = 0;
            while (true) {
                if (i4 >= size) {
                    z4 = z;
                    break;
                }
                Object obj = arrayList.get(i4);
                i4++;
                AbstractC0725wt abstractC0725wt = (AbstractC0725wt) obj;
                if (abstractC0725wt.f == i && !abstractC0725wt.k()) {
                    z4 = false;
                    break;
                }
            }
            if (i == 0) {
                if (z4 && iH == 2) {
                    c0456o7.w(1);
                    c0456o7.y(l9.d(c0456o7, 0));
                    ae.e.d(c0456o7.l());
                }
            } else if (z4 && iH2 == 2) {
                c0456o7.x(1);
                c0456o7.v(l9.d(c0456o7, 1));
                c0600ss.e.d(c0456o7.i());
            }
        }
        int[] iArr = c0456o7.c0;
        if (i == 0) {
            i2 = 0;
            int i5 = iArr[0];
            if (i5 == 1 || i5 == 4) {
                int iL = c0456o7.l() + iM;
                ae.i.d(iL);
                ae.e.d(iL - iM);
                i3 = 1;
                z2 = true;
            } else {
                z2 = true;
                i3 = i2;
            }
        } else {
            z2 = true;
            i2 = 0;
            int i6 = iArr[1];
            if (i6 == 1 || i6 == 4) {
                int i7 = c0456o7.i() + iN;
                c0600ss.i.d(i7);
                c0600ss.e.d(i7 - iN);
                i3 = 1;
            } else {
                i3 = i2;
            }
        }
        l9.g();
        int size2 = arrayList.size();
        int i8 = i2;
        while (i8 < size2) {
            Object obj2 = arrayList.get(i8);
            i8++;
            AbstractC0725wt abstractC0725wt2 = (AbstractC0725wt) obj2;
            if (abstractC0725wt2.f == i && (abstractC0725wt2.b != c0456o7 || abstractC0725wt2.g)) {
                abstractC0725wt2.e();
            }
        }
        int size3 = arrayList.size();
        int i9 = i2;
        while (i9 < size3) {
            Object obj3 = arrayList.get(i9);
            i9++;
            AbstractC0725wt abstractC0725wt3 = (AbstractC0725wt) obj3;
            if (abstractC0725wt3.f == i && (i3 != 0 || abstractC0725wt3.b != c0456o7)) {
                if (!abstractC0725wt3.h.j || !abstractC0725wt3.i.j || (!(abstractC0725wt3 instanceof J5) && !abstractC0725wt3.e.j)) {
                    z3 = i2;
                    c0456o7.w(iH);
                    c0456o7.x(iH2);
                    return z3;
                }
            }
        }
        z3 = z2;
        c0456o7.w(iH);
        c0456o7.x(iH2);
        return z3;
    }

    /* JADX WARN: Code duplicated, block: B:107:0x01fb  */
    /* JADX WARN: Code duplicated, block: B:61:0x00fd  */
    /* JADX WARN: Code duplicated, block: B:63:0x010c A[LOOP:3: B:62:0x010a->B:63:0x010c, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:64:0x011a  */
    /* JADX WARN: Code duplicated, block: B:66:0x0121 A[LOOP:5: B:65:0x011f->B:66:0x0121, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:85:0x0193  */
    /* JADX WARN: Code duplicated, block: B:88:0x01a5  */
    /* JADX WARN: Code duplicated, block: B:91:0x01bd  */
    /* JADX WARN: Code duplicated, block: B:93:0x01c7  */
    /* JADX WARN: Code duplicated, block: B:99:0x01e0  */
    /* JADX WARN: Type inference failed for: r2v11 */
    /* JADX WARN: Type inference failed for: r2v12, types: [boolean] */
    /* JADX WARN: Type inference failed for: r2v44 */
    public final void E() {
        boolean z;
        boolean z2;
        boolean[] zArr;
        int i;
        boolean z3;
        int iMax;
        boolean z4;
        boolean z5;
        int iMax2;
        boolean z6;
        boolean z7;
        boolean z8;
        boolean z9;
        boolean z10;
        ?? r2;
        boolean z11;
        boolean z12;
        boolean z13;
        boolean z14;
        int size;
        int i2;
        boolean z15;
        boolean z16 = false;
        this.N = 0;
        this.O = 0;
        int iMax3 = Math.max(0, l());
        int iMax4 = Math.max(0, i());
        this.q0 = false;
        this.r0 = false;
        int i3 = this.p0;
        boolean z17 = true;
        boolean z18 = ((i3 & 64) == 64) || (i3 & 128) == 128;
        Lg lg = this.i0;
        lg.getClass();
        lg.f = false;
        if (this.p0 != 0 && z18) {
            lg.f = true;
        }
        int[] iArr = this.c0;
        int i4 = iArr[1];
        int i5 = iArr[0];
        ArrayList arrayList = this.d0;
        int i6 = 2;
        boolean z19 = i5 == 2 || i4 == 2;
        this.l0 = 0;
        this.m0 = 0;
        int size2 = arrayList.size();
        for (int i7 = 0; i7 < size2; i7++) {
            C0425n7 c0425n7 = (C0425n7) this.d0.get(i7);
            if (c0425n7 instanceof C0456o7) {
                ((C0456o7) c0425n7).E();
            }
        }
        int i8 = 0;
        boolean z20 = false;
        boolean z21 = true;
        while (z21) {
            boolean z22 = z17;
            int i9 = i8 + 1;
            try {
                lg.r();
                this.l0 = z16 ? 1 : 0;
                this.m0 = z16 ? 1 : 0;
                f(lg);
                int i10 = z16 ? 1 : 0;
                while (i10 < size2) {
                    z = z16;
                    try {
                        ((C0425n7) this.d0.get(i10)).f(lg);
                        i10++;
                        z16 = z ? 1 : 0;
                    } catch (Exception e) {
                        e = e;
                        z15 = z21;
                        e.printStackTrace();
                        System.out.println("EXCEPTION : " + e);
                        z2 = z15;
                        zArr = AbstractC0744xh.h;
                        if (z2) {
                            zArr[i6] = z;
                            A(lg);
                            size = this.d0.size();
                            for (i2 = z ? 1 : 0; i2 < size; i2++) {
                                ((C0425n7) this.d0.get(i2)).A(lg);
                            }
                        } else {
                            A(lg);
                            for (i = z ? 1 : 0; i < size2; i++) {
                                ((C0425n7) this.d0.get(i)).A(lg);
                            }
                        }
                        if (z19) {
                            z3 = z ? 1 : 0;
                        } else {
                            z3 = z ? 1 : 0;
                        }
                        z3 = z14;
                        z3 = z14;
                        iMax = Math.max(this.Q, l());
                        z5 = z3;
                        z4 = z20;
                        if (iMax > l()) {
                            y(iMax);
                            iArr[z ? 1 : 0] = z22 ? 1 : 0;
                            boolean z23 = z22 ? 1 : 0;
                            z4 = z23 ? 1 : 0;
                            z5 = z23;
                        }
                        iMax2 = Math.max(this.R, i());
                        z7 = z5;
                        z6 = z4;
                        if (iMax2 > i()) {
                            v(iMax2);
                            iArr[z22 ? 1 : 0] = z22 ? 1 : 0;
                            z13 = z22 ? 1 : 0;
                            z6 = z13 ? 1 : 0;
                        }
                        if (z6) {
                            z7 = z13;
                            i6 = 2;
                            z9 = z7;
                            z8 = z6;
                        } else {
                            if (iArr[z ? 1 : 0] == 2) {
                                z7 = z13;
                                r2 = z22 ? 1 : 0;
                                z12 = z7;
                                z11 = z6;
                            } else {
                                z7 = z13;
                                r2 = z22 ? 1 : 0;
                                z12 = z7;
                                z11 = z6;
                            }
                            i6 = 2;
                            z9 = z12;
                            z9 = z12;
                            z8 = z11;
                            z8 = z11;
                            if (iArr[r2] != 2) {
                            }
                            i8 = i9;
                            z16 = z ? 1 : 0;
                            iArr = iArr;
                            z17 = true;
                            z21 = z10;
                            z20 = z8;
                        }
                        z9 = z12;
                        z8 = z11;
                        z10 = z9;
                        i8 = i9;
                        z16 = z ? 1 : 0;
                        iArr = iArr;
                        z17 = true;
                        z21 = z10;
                        z20 = z8;
                    }
                }
                z = z16;
                C(lg);
                try {
                    C0561rk c0561rk = lg.b;
                    if (lg.f) {
                        int i11 = z ? 1 : 0;
                        while (true) {
                            if (i11 >= lg.i) {
                                for (int i12 = z ? 1 : 0; i12 < lg.i; i12++) {
                                    C0360l4 c0360l4 = lg.e[i12];
                                    c0360l4.a.e = c0360l4.b;
                                }
                                break;
                            }
                            if (!lg.e[i11].e) {
                                lg.o(c0561rk);
                                break;
                            }
                            i11++;
                        }
                    } else {
                        lg.o(c0561rk);
                    }
                    z2 = z22 ? 1 : 0;
                } catch (Exception e2) {
                    e = e2;
                    z15 = z22 ? 1 : 0;
                    e.printStackTrace();
                    System.out.println("EXCEPTION : " + e);
                    z2 = z15;
                }
            } catch (Exception e3) {
                e = e3;
                z = z16 ? 1 : 0;
                z15 = z21;
            }
            zArr = AbstractC0744xh.h;
            if (z2) {
                zArr[i6] = z;
                A(lg);
                size = this.d0.size();
                while (i2 < size) {
                    ((C0425n7) this.d0.get(i2)).A(lg);
                }
            } else {
                A(lg);
                while (i < size2) {
                    ((C0425n7) this.d0.get(i)).A(lg);
                }
            }
            if (z19 || i9 >= 8 || !zArr[i6]) {
                z3 = z ? 1 : 0;
            } else {
                int i13 = z ? 1 : 0;
                int iMax5 = i13;
                int iMax6 = iMax5;
                while (i13 < size2) {
                    C0425n7 c0425n8 = (C0425n7) this.d0.get(i13);
                    iMax5 = Math.max(iMax5, c0425n8.l() + c0425n8.N);
                    iMax6 = Math.max(iMax6, c0425n8.i() + c0425n8.O);
                    i13++;
                }
                int iMax7 = Math.max(this.Q, iMax5);
                int iMax8 = Math.max(this.R, iMax6);
                int i14 = i6;
                if (i5 != i14 || l() >= iMax7) {
                    z3 = z ? 1 : 0;
                    z20 = z20;
                } else {
                    y(iMax7);
                    iArr[z ? 1 : 0] = i14;
                    z14 = z22 ? 1 : 0;
                    z20 = z14 ? 1 : 0;
                }
                if (i4 == i14 && i() < iMax8) {
                    z3 = z14;
                    v(iMax8);
                    iArr[z22 ? 1 : 0] = i14;
                    z3 = z22 ? 1 : 0;
                    z20 = z3 ? 1 : 0;
                }
            }
            z3 = z14;
            z3 = z14;
            iMax = Math.max(this.Q, l());
            z5 = z3;
            z4 = z20;
            if (iMax > l()) {
                y(iMax);
                iArr[z ? 1 : 0] = z22 ? 1 : 0;
                boolean z24 = z22 ? 1 : 0;
                z4 = z24 ? 1 : 0;
                z5 = z24;
            }
            iMax2 = Math.max(this.R, i());
            z7 = z5;
            z6 = z4;
            if (iMax2 > i()) {
                v(iMax2);
                iArr[z22 ? 1 : 0] = z22 ? 1 : 0;
                z13 = z22 ? 1 : 0;
                z6 = z13 ? 1 : 0;
            }
            if (z6) {
                if (iArr[z ? 1 : 0] == 2 || iMax3 <= 0 || l() <= iMax3) {
                    z7 = z13;
                    r2 = z22 ? 1 : 0;
                    z12 = z7;
                    z11 = z6;
                } else {
                    boolean z25 = z22 ? 1 : 0;
                    this.q0 = z25;
                    iArr[z ? 1 : 0] = z25 ? 1 : 0;
                    y(iMax3);
                    boolean z26 = z25 ? 1 : 0;
                    z11 = z26 ? 1 : 0;
                    z12 = z26;
                    r2 = z25;
                }
                i6 = 2;
                z9 = z12;
                z9 = z12;
                z8 = z11;
                z8 = z11;
                if (iArr[r2] != 2 && iMax4 > 0 && i() > iMax4) {
                    z9 = z12;
                    z8 = z11;
                    this.r0 = r2;
                    iArr[r2] = r2;
                    v(iMax4);
                    z10 = true;
                    z8 = true;
                }
                i8 = i9;
                z16 = z ? 1 : 0;
                iArr = iArr;
                z17 = true;
                z21 = z10;
                z20 = z8;
            } else {
                z7 = z13;
                i6 = 2;
                z9 = z7;
                z8 = z6;
            }
            z9 = z12;
            z8 = z11;
            z10 = z9;
            i8 = i9;
            z16 = z ? 1 : 0;
            iArr = iArr;
            z17 = true;
            z21 = z10;
            z20 = z8;
        }
        boolean z27 = z16 ? 1 : 0;
        int[] iArr2 = iArr;
        this.d0 = arrayList;
        if (z20) {
            iArr2[z27 ? 1 : 0] = i5;
            iArr2[1] = i4;
        }
        u(lg.k);
    }

    @Override // rikka.shizuku.C0425n7
    public final void s() {
        this.i0.r();
        this.j0 = 0;
        this.k0 = 0;
        this.d0.clear();
        super.s();
    }

    @Override // rikka.shizuku.C0425n7
    public final void u(Xn xn) {
        super.u(xn);
        int size = this.d0.size();
        for (int i = 0; i < size; i++) {
            ((C0425n7) this.d0.get(i)).u(xn);
        }
    }

    @Override // rikka.shizuku.C0425n7
    public final void z(boolean z, boolean z2) {
        super.z(z, z2);
        int size = this.d0.size();
        for (int i = 0; i < size; i++) {
            ((C0425n7) this.d0.get(i)).z(z, z2);
        }
    }
}
