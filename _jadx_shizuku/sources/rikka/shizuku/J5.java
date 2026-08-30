package rikka.shizuku;

import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class J5 extends AbstractC0725wt {
    public final ArrayList k;
    public int l;

    public J5(C0425n7 c0425n7, int i) {
        C0425n7 c0425n8;
        super(c0425n7);
        this.k = new ArrayList();
        this.f = i;
        C0425n7 c0425n9 = this.b;
        C0425n7 c0425n7K = c0425n9.k(i);
        while (true) {
            C0425n7 c0425n10 = c0425n7K;
            c0425n8 = c0425n9;
            c0425n9 = c0425n10;
            if (c0425n9 == null) {
                break;
            } else {
                c0425n7K = c0425n9.k(this.f);
            }
        }
        this.b = c0425n8;
        int i2 = this.f;
        K9 k9 = i2 == 0 ? c0425n8.d : i2 == 1 ? c0425n8.e : null;
        ArrayList arrayList = this.k;
        arrayList.add(k9);
        C0425n7 c0425n7J = c0425n8.j(this.f);
        while (c0425n7J != null) {
            int i3 = this.f;
            arrayList.add(i3 == 0 ? c0425n7J.d : i3 == 1 ? c0425n7J.e : null);
            c0425n7J = c0425n7J.j(this.f);
        }
        int size = arrayList.size();
        int i4 = 0;
        while (i4 < size) {
            Object obj = arrayList.get(i4);
            i4++;
            AbstractC0725wt abstractC0725wt = (AbstractC0725wt) obj;
            int i5 = this.f;
            if (i5 == 0) {
                abstractC0725wt.b.b = this;
            } else if (i5 == 1) {
                abstractC0725wt.b.c = this;
            }
        }
        if (this.f == 0 && ((C0456o7) this.b.I).h0 && arrayList.size() > 1) {
            this.b = ((AbstractC0725wt) arrayList.get(arrayList.size() - 1)).b;
        }
        this.l = this.f == 0 ? this.b.X : this.b.Y;
    }

    /* JADX WARN: Code duplicated, block: B:121:0x01c1 A[PHI: r1 r26
  0x01c1: PHI (r1v57 int) = (r1v55 int), (r1v60 int) binds: [B:120:0x01bf, B:111:0x019f] A[DONT_GENERATE, DONT_INLINE]
  0x01c1: PHI (r26v1 int) = (r26v0 int), (r26v3 int) binds: [B:120:0x01bf, B:111:0x019f] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:302:0x00ea A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:64:0x00d0  */
    /* JADX WARN: Code duplicated, block: B:66:0x00dc  */
    /* JADX WARN: Code duplicated, block: B:67:0x00df  */
    /* JADX WARN: Code duplicated, block: B:69:0x00e2 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:93:0x0154  */
    @Override // rikka.shizuku.K9
    public final void a(K9 k9) {
        int i;
        int i2;
        boolean z;
        float f;
        int i3;
        int i4;
        int i5;
        int i6;
        float f2;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int iMax;
        int i12;
        int i13;
        float f3;
        M9 m9 = this.h;
        if (m9.j) {
            M9 m10 = this.i;
            if (m10.j) {
                C0425n7 c0425n7 = this.b.I;
                boolean z2 = (c0425n7 == null || !(c0425n7 instanceof C0456o7)) ? false : ((C0456o7) c0425n7).h0;
                int i14 = m10.g - m9.g;
                ArrayList arrayList = this.k;
                int size = arrayList.size();
                int i15 = 0;
                while (true) {
                    i = -1;
                    i2 = 8;
                    if (i15 >= size) {
                        i15 = -1;
                        break;
                    } else if (((AbstractC0725wt) arrayList.get(i15)).b.V != 8) {
                        break;
                    } else {
                        i15++;
                    }
                }
                int i16 = size - 1;
                for (int i17 = i16; i17 >= 0; i17--) {
                    if (((AbstractC0725wt) arrayList.get(i17)).b.V != 8) {
                        i = i17;
                        break;
                    }
                }
                int i18 = 0;
                while (true) {
                    if (i18 >= 2) {
                        z = z2;
                        f = 0.0f;
                        i3 = 0;
                        i4 = 0;
                        i5 = 0;
                        break;
                    }
                    f = 0.0f;
                    int i19 = 0;
                    i5 = 0;
                    int i20 = 0;
                    int i21 = 0;
                    while (i19 < size) {
                        AbstractC0725wt abstractC0725wt = (AbstractC0725wt) arrayList.get(i19);
                        C0425n7 c0425n8 = abstractC0725wt.b;
                        boolean z3 = z2;
                        if (c0425n8.V == i2) {
                            i12 = i18;
                        } else {
                            i21++;
                            if (i19 > 0 && i19 >= i15) {
                                i5 += abstractC0725wt.h.f;
                            }
                            X9 x9 = abstractC0725wt.e;
                            int i22 = x9.g;
                            i12 = i18;
                            boolean z4 = abstractC0725wt.d != 3;
                            if (z4) {
                                int i23 = this.f;
                                if (i23 == 0 && !c0425n8.d.e.j) {
                                    return;
                                }
                                if (i23 == 1 && !c0425n8.e.e.j) {
                                    return;
                                }
                            } else {
                                if (abstractC0725wt.a == 1 && i12 == 0) {
                                    i13 = x9.m;
                                    i20++;
                                } else {
                                    if (x9.j) {
                                        i13 = i22;
                                    }
                                    if (z4) {
                                        i5 += i13;
                                    } else {
                                        i20++;
                                        f3 = c0425n8.Z[this.f];
                                        if (f3 >= 0.0f) {
                                            f += f3;
                                        }
                                    }
                                    if (i19 >= i16 && i19 < i) {
                                        i5 += -abstractC0725wt.i.f;
                                    }
                                }
                                z4 = true;
                                if (z4) {
                                    i20++;
                                    f3 = c0425n8.Z[this.f];
                                    if (f3 >= 0.0f) {
                                        f += f3;
                                    }
                                } else {
                                    i5 += i13;
                                }
                                if (i19 >= i16) {
                                }
                            }
                            i13 = i22;
                            if (z4) {
                                i20++;
                                f3 = c0425n8.Z[this.f];
                                if (f3 >= 0.0f) {
                                    f += f3;
                                }
                            } else {
                                i5 += i13;
                            }
                            if (i19 >= i16) {
                            }
                        }
                        i19++;
                        z2 = z3;
                        i18 = i12;
                        i2 = 8;
                    }
                    z = z2;
                    int i24 = i18;
                    if (i5 < i14 || i20 == 0) {
                        i3 = i20;
                        i4 = i21;
                        break;
                    } else {
                        i18 = i24 + 1;
                        z2 = z;
                        i2 = 8;
                    }
                }
                int i25 = m9.g;
                if (z) {
                    i25 = m10.g;
                }
                float f4 = 0.5f;
                if (i5 > i14) {
                    i25 = z ? i25 + ((int) (((i5 - i14) / 2.0f) + 0.5f)) : i25 - ((int) (((i5 - i14) / 2.0f) + 0.5f));
                }
                if (i3 > 0) {
                    float f5 = i14 - i5;
                    int i26 = (int) ((f5 / i3) + 0.5f);
                    int i27 = 0;
                    int i28 = 0;
                    while (i27 < size) {
                        float f6 = f4;
                        AbstractC0725wt abstractC0725wt2 = (AbstractC0725wt) arrayList.get(i27);
                        int i29 = i25;
                        C0425n7 c0425n9 = abstractC0725wt2.b;
                        int i30 = i3;
                        float f7 = f5;
                        if (c0425n9.V != 8 && abstractC0725wt2.d == 3) {
                            X9 x10 = abstractC0725wt2.e;
                            if (x10.j) {
                                i10 = i26;
                                i11 = i27;
                            } else {
                                if (f > 0.0f) {
                                    i9 = (int) (((c0425n9.Z[this.f] * f7) / f) + f6);
                                    i10 = i26;
                                } else {
                                    i9 = i26;
                                    i10 = i9;
                                }
                                if (this.f == 0) {
                                    int i31 = c0425n9.n;
                                    i11 = i27;
                                    iMax = Math.max(c0425n9.m, abstractC0725wt2.a == 1 ? Math.min(i9, x10.m) : i9);
                                    if (i31 > 0) {
                                        iMax = Math.min(i31, iMax);
                                    }
                                    if (iMax != i9) {
                                        i28++;
                                        i9 = iMax;
                                    }
                                } else {
                                    i11 = i27;
                                    int i32 = c0425n9.q;
                                    iMax = Math.max(c0425n9.p, abstractC0725wt2.a == 1 ? Math.min(i9, x10.m) : i9);
                                    if (i32 > 0) {
                                        iMax = Math.min(i32, iMax);
                                    }
                                    if (iMax != i9) {
                                        i28++;
                                        i9 = iMax;
                                    }
                                }
                                x10.d(i9);
                            }
                        } else {
                            i10 = i26;
                            i11 = i27;
                        }
                        i27 = i11 + 1;
                        i25 = i29;
                        f4 = f6;
                        i3 = i30;
                        f5 = f7;
                        i26 = i10;
                    }
                    i6 = i25;
                    f2 = f4;
                    int i33 = i3;
                    if (i28 > 0) {
                        i3 = i33 - i28;
                        i5 = 0;
                        for (int i34 = 0; i34 < size; i34++) {
                            AbstractC0725wt abstractC0725wt3 = (AbstractC0725wt) arrayList.get(i34);
                            if (abstractC0725wt3.b.V != 8) {
                                if (i34 > 0 && i34 >= i15) {
                                    i5 += abstractC0725wt3.h.f;
                                }
                                i5 += abstractC0725wt3.e.g;
                                if (i34 < i16 && i34 < i) {
                                    i5 += -abstractC0725wt3.i.f;
                                }
                            }
                        }
                    } else {
                        i3 = i33;
                    }
                    i8 = 2;
                    if (this.l == 2 && i28 == 0) {
                        i7 = 0;
                        this.l = 0;
                    } else {
                        i7 = 0;
                    }
                } else {
                    i6 = i25;
                    f2 = 0.5f;
                    i7 = 0;
                    i8 = 2;
                }
                if (i5 > i14) {
                    this.l = i8;
                }
                if (i4 > 0 && i3 == 0 && i15 == i) {
                    this.l = i8;
                }
                int i35 = this.l;
                if (i35 == 1) {
                    int i36 = i4 > 1 ? (i14 - i5) / (i4 - 1) : i4 == 1 ? (i14 - i5) / 2 : i7;
                    if (i3 > 0) {
                        i36 = i7;
                    }
                    int i37 = i6;
                    for (int i38 = i7; i38 < size; i38++) {
                        AbstractC0725wt abstractC0725wt4 = (AbstractC0725wt) arrayList.get(z ? size - (i38 + 1) : i38);
                        int i39 = abstractC0725wt4.b.V;
                        M9 m11 = abstractC0725wt4.i;
                        M9 m12 = abstractC0725wt4.h;
                        if (i39 == 8) {
                            m12.d(i37);
                            m11.d(i37);
                        } else {
                            if (i38 > 0) {
                                i37 = z ? i37 - i36 : i37 + i36;
                            }
                            if (i38 > 0 && i38 >= i15) {
                                i37 = z ? i37 - m12.f : i37 + m12.f;
                            }
                            if (z) {
                                m11.d(i37);
                            } else {
                                m12.d(i37);
                            }
                            X9 x11 = abstractC0725wt4.e;
                            int i40 = x11.g;
                            if (abstractC0725wt4.d == 3 && abstractC0725wt4.a == 1) {
                                i40 = x11.m;
                            }
                            i37 = z ? i37 - i40 : i37 + i40;
                            if (z) {
                                m12.d(i37);
                            } else {
                                m11.d(i37);
                            }
                            abstractC0725wt4.g = true;
                            if (i38 < i16 && i38 < i) {
                                i37 = z ? i37 - (-m11.f) : i37 + (-m11.f);
                            }
                        }
                    }
                    return;
                }
                if (i35 == 0) {
                    int i41 = (i14 - i5) / (i4 + 1);
                    if (i3 > 0) {
                        i41 = i7;
                    }
                    int i42 = i6;
                    for (int i43 = i7; i43 < size; i43++) {
                        AbstractC0725wt abstractC0725wt5 = (AbstractC0725wt) arrayList.get(z ? size - (i43 + 1) : i43);
                        int i44 = abstractC0725wt5.b.V;
                        M9 m13 = abstractC0725wt5.i;
                        M9 m14 = abstractC0725wt5.h;
                        if (i44 == 8) {
                            m14.d(i42);
                            m13.d(i42);
                        } else {
                            int i45 = z ? i42 - i41 : i42 + i41;
                            if (i43 > 0 && i43 >= i15) {
                                i45 = z ? i45 - m14.f : i45 + m14.f;
                            }
                            if (z) {
                                m13.d(i45);
                            } else {
                                m14.d(i45);
                            }
                            X9 x12 = abstractC0725wt5.e;
                            int iMin = x12.g;
                            if (abstractC0725wt5.d == 3 && abstractC0725wt5.a == 1) {
                                iMin = Math.min(iMin, x12.m);
                            }
                            i42 = z ? i45 - iMin : i45 + iMin;
                            if (z) {
                                m14.d(i42);
                            } else {
                                m13.d(i42);
                            }
                            if (i43 < i16 && i43 < i) {
                                i42 = z ? i42 - (-m13.f) : i42 + (-m13.f);
                            }
                        }
                    }
                    return;
                }
                if (i35 == 2) {
                    float f8 = this.f == 0 ? this.b.S : this.b.T;
                    if (z) {
                        f8 = 1.0f - f8;
                    }
                    int i46 = (int) (((i14 - i5) * f8) + f2);
                    if (i46 < 0 || i3 > 0) {
                        i46 = i7;
                    }
                    int i47 = z ? i6 - i46 : i6 + i46;
                    for (int i48 = i7; i48 < size; i48++) {
                        AbstractC0725wt abstractC0725wt6 = (AbstractC0725wt) arrayList.get(z ? size - (i48 + 1) : i48);
                        int i49 = abstractC0725wt6.b.V;
                        M9 m15 = abstractC0725wt6.i;
                        M9 m16 = abstractC0725wt6.h;
                        if (i49 == 8) {
                            m16.d(i47);
                            m15.d(i47);
                        } else {
                            if (i48 > 0 && i48 >= i15) {
                                i47 = z ? i47 - m16.f : i47 + m16.f;
                            }
                            if (z) {
                                m15.d(i47);
                            } else {
                                m16.d(i47);
                            }
                            X9 x13 = abstractC0725wt6.e;
                            int i50 = x13.g;
                            if (abstractC0725wt6.d == 3 && abstractC0725wt6.a == 1) {
                                i50 = x13.m;
                            }
                            i47 = z ? i47 - i50 : i47 + i50;
                            if (z) {
                                m16.d(i47);
                            } else {
                                m15.d(i47);
                            }
                            if (i48 < i16 && i48 < i) {
                                i47 = z ? i47 - (-m15.f) : i47 + (-m15.f);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override // rikka.shizuku.AbstractC0725wt
    public final void d() {
        ArrayList arrayList = this.k;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((AbstractC0725wt) obj).d();
        }
        int size2 = arrayList.size();
        if (size2 < 1) {
            return;
        }
        C0425n7 c0425n7 = ((AbstractC0725wt) arrayList.get(0)).b;
        C0425n7 c0425n8 = ((AbstractC0725wt) arrayList.get(size2 - 1)).b;
        int i2 = this.f;
        M9 m9 = this.i;
        M9 m10 = this.h;
        if (i2 == 0) {
            Z6 z6 = c0425n7.x;
            Z6 z7 = c0425n8.z;
            M9 m9I = AbstractC0725wt.i(z6, 0);
            int iC = z6.c();
            C0425n7 c0425n7M = m();
            if (c0425n7M != null) {
                iC = c0425n7M.x.c();
            }
            if (m9I != null) {
                AbstractC0725wt.b(m10, m9I, iC);
            }
            M9 m9I2 = AbstractC0725wt.i(z7, 0);
            int iC2 = z7.c();
            C0425n7 c0425n7N = n();
            if (c0425n7N != null) {
                iC2 = c0425n7N.z.c();
            }
            if (m9I2 != null) {
                AbstractC0725wt.b(m9, m9I2, -iC2);
            }
        } else {
            Z6 z8 = c0425n7.y;
            Z6 z9 = c0425n8.A;
            M9 m9I3 = AbstractC0725wt.i(z8, 1);
            int iC3 = z8.c();
            C0425n7 c0425n7M2 = m();
            if (c0425n7M2 != null) {
                iC3 = c0425n7M2.y.c();
            }
            if (m9I3 != null) {
                AbstractC0725wt.b(m10, m9I3, iC3);
            }
            M9 m9I4 = AbstractC0725wt.i(z9, 1);
            int iC4 = z9.c();
            C0425n7 c0425n7N2 = n();
            if (c0425n7N2 != null) {
                iC4 = c0425n7N2.A.c();
            }
            if (m9I4 != null) {
                AbstractC0725wt.b(m9, m9I4, -iC4);
            }
        }
        m10.a = this;
        m9.a = this;
    }

    @Override // rikka.shizuku.AbstractC0725wt
    public final void e() {
        int i = 0;
        while (true) {
            ArrayList arrayList = this.k;
            if (i >= arrayList.size()) {
                return;
            }
            ((AbstractC0725wt) arrayList.get(i)).e();
            i++;
        }
    }

    @Override // rikka.shizuku.AbstractC0725wt
    public final void f() {
        this.c = null;
        ArrayList arrayList = this.k;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((AbstractC0725wt) obj).f();
        }
    }

    @Override // rikka.shizuku.AbstractC0725wt
    public final long j() {
        ArrayList arrayList = this.k;
        int size = arrayList.size();
        long j = 0;
        for (int i = 0; i < size; i++) {
            AbstractC0725wt abstractC0725wt = (AbstractC0725wt) arrayList.get(i);
            j = ((long) abstractC0725wt.i.f) + abstractC0725wt.j() + j + ((long) abstractC0725wt.h.f);
        }
        return j;
    }

    @Override // rikka.shizuku.AbstractC0725wt
    public final boolean k() {
        ArrayList arrayList = this.k;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            if (!((AbstractC0725wt) arrayList.get(i)).k()) {
                return false;
            }
        }
        return true;
    }

    public final C0425n7 m() {
        int i = 0;
        while (true) {
            ArrayList arrayList = this.k;
            if (i >= arrayList.size()) {
                return null;
            }
            C0425n7 c0425n7 = ((AbstractC0725wt) arrayList.get(i)).b;
            if (c0425n7.V != 8) {
                return c0425n7;
            }
            i++;
        }
    }

    public final C0425n7 n() {
        ArrayList arrayList = this.k;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            C0425n7 c0425n7 = ((AbstractC0725wt) arrayList.get(size)).b;
            if (c0425n7.V != 8) {
                return c0425n7;
            }
        }
        return null;
    }

    public final String toString() {
        String strConcat = "ChainRun ".concat(this.f == 0 ? "horizontal : " : "vertical : ");
        ArrayList arrayList = this.k;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            strConcat = AbstractC0535qo.c(AbstractC0535qo.c(strConcat, "<") + ((AbstractC0725wt) obj), "> ");
        }
        return strConcat;
    }
}
