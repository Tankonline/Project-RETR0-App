package rikka.shizuku;

import java.util.ArrayList;
import java.util.HashSet;

/* JADX INFO: loaded from: classes.dex */
public final class L9 {
    public C0456o7 a;
    public boolean b;
    public boolean c;
    public C0456o7 d;
    public ArrayList e;
    public C0148e7 f;
    public Z4 g;
    public ArrayList h;

    public final void a(M9 m9, int i, ArrayList arrayList, C0316jm c0316jm) {
        AbstractC0725wt abstractC0725wt = m9.d;
        if (abstractC0725wt.c == null) {
            C0456o7 c0456o7 = this.a;
            if (abstractC0725wt == c0456o7.d || abstractC0725wt == c0456o7.e) {
                return;
            }
            if (c0316jm == null) {
                c0316jm = new C0316jm();
                c0316jm.a = null;
                c0316jm.b = new ArrayList();
                c0316jm.a = abstractC0725wt;
                arrayList.add(c0316jm);
            }
            abstractC0725wt.c = c0316jm;
            c0316jm.b.add(abstractC0725wt);
            M9 m10 = abstractC0725wt.h;
            ArrayList arrayList2 = m10.k;
            int size = arrayList2.size();
            int i2 = 0;
            int i3 = 0;
            while (i3 < size) {
                Object obj = arrayList2.get(i3);
                i3++;
                K9 k9 = (K9) obj;
                if (k9 instanceof M9) {
                    a((M9) k9, i, arrayList, c0316jm);
                }
            }
            M9 m11 = abstractC0725wt.i;
            ArrayList arrayList3 = m11.k;
            int size2 = arrayList3.size();
            int i4 = 0;
            while (i4 < size2) {
                Object obj2 = arrayList3.get(i4);
                i4++;
                K9 k10 = (K9) obj2;
                if (k10 instanceof M9) {
                    a((M9) k10, i, arrayList, c0316jm);
                }
            }
            if (i == 1 && (abstractC0725wt instanceof C0600ss)) {
                ArrayList arrayList4 = ((C0600ss) abstractC0725wt).k.k;
                int size3 = arrayList4.size();
                int i5 = 0;
                while (i5 < size3) {
                    Object obj3 = arrayList4.get(i5);
                    i5++;
                    K9 k11 = (K9) obj3;
                    if (k11 instanceof M9) {
                        a((M9) k11, i, arrayList, c0316jm);
                    }
                }
            }
            ArrayList arrayList5 = m10.l;
            int size4 = arrayList5.size();
            int i6 = 0;
            while (i6 < size4) {
                Object obj4 = arrayList5.get(i6);
                i6++;
                a((M9) obj4, i, arrayList, c0316jm);
            }
            ArrayList arrayList6 = m11.l;
            int size5 = arrayList6.size();
            int i7 = 0;
            while (i7 < size5) {
                Object obj5 = arrayList6.get(i7);
                i7++;
                a((M9) obj5, i, arrayList, c0316jm);
            }
            if (i == 1 && (abstractC0725wt instanceof C0600ss)) {
                ArrayList arrayList7 = ((C0600ss) abstractC0725wt).k.l;
                int size6 = arrayList7.size();
                while (i2 < size6) {
                    Object obj6 = arrayList7.get(i2);
                    i2++;
                    a((M9) obj6, i, arrayList, c0316jm);
                }
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:100:0x01a0  */
    /* JADX WARN: Code duplicated, block: B:105:0x01af  */
    /* JADX WARN: Code duplicated, block: B:107:0x01b2  */
    /* JADX WARN: Code duplicated, block: B:110:0x01c5  */
    /* JADX WARN: Code duplicated, block: B:112:0x01ee  */
    /* JADX WARN: Code duplicated, block: B:114:0x01f4  */
    /* JADX WARN: Code duplicated, block: B:115:0x0208  */
    /* JADX WARN: Code duplicated, block: B:117:0x020e  */
    /* JADX WARN: Code duplicated, block: B:119:0x0212  */
    /* JADX WARN: Code duplicated, block: B:125:0x0245  */
    /* JADX WARN: Code duplicated, block: B:127:0x024e  */
    /* JADX WARN: Code duplicated, block: B:130:0x0276  */
    /* JADX WARN: Code duplicated, block: B:132:0x027d A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:134:0x0281 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:146:0x02c6  */
    /* JADX WARN: Code duplicated, block: B:147:0x02e3  */
    /* JADX WARN: Code duplicated, block: B:150:0x02ef  */
    /* JADX WARN: Code duplicated, block: B:153:0x0302  */
    /* JADX WARN: Code duplicated, block: B:58:0x00b2  */
    /* JADX WARN: Code duplicated, block: B:60:0x00b5  */
    /* JADX WARN: Code duplicated, block: B:62:0x00b8  */
    /* JADX WARN: Code duplicated, block: B:64:0x00bb  */
    /* JADX WARN: Code duplicated, block: B:66:0x00c1 A[PHI: r1
  0x00c1: PHI (r1v12 int) = (r1v10 int), (r1v33 int) binds: [B:63:0x00b9, B:57:0x00b0] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:68:0x00ca  */
    /* JADX WARN: Code duplicated, block: B:69:0x00cc  */
    /* JADX WARN: Code duplicated, block: B:73:0x00d4 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:74:0x00d6  */
    /* JADX WARN: Code duplicated, block: B:76:0x00d9  */
    /* JADX WARN: Code duplicated, block: B:77:0x00e5  */
    /* JADX WARN: Code duplicated, block: B:80:0x0117  */
    /* JADX WARN: Code duplicated, block: B:82:0x011e  */
    /* JADX WARN: Code duplicated, block: B:83:0x0130  */
    /* JADX WARN: Code duplicated, block: B:85:0x0133  */
    /* JADX WARN: Code duplicated, block: B:87:0x0137  */
    /* JADX WARN: Code duplicated, block: B:90:0x013b  */
    /* JADX WARN: Code duplicated, block: B:92:0x0169  */
    /* JADX WARN: Code duplicated, block: B:94:0x0171  */
    /* JADX WARN: Code duplicated, block: B:97:0x0197  */
    /* JADX WARN: Code duplicated, block: B:99:0x019e  */
    public final void b(C0456o7 c0456o7) {
        int i;
        int i2;
        int iL;
        int i3;
        int i4;
        int[] iArr;
        Z6[] z6Arr;
        int i5;
        int i6;
        C0600ss c0600ss;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        float f;
        int i15;
        C0600ss c0600ss2;
        ArrayList arrayList = c0456o7.d0;
        int size = arrayList.size();
        char c = 0;
        int i16 = 0;
        while (i16 < size) {
            Object obj = arrayList.get(i16);
            i16++;
            C0425n7 c0425n7 = (C0425n7) obj;
            int[] iArr2 = c0425n7.c0;
            int i17 = iArr2[c];
            int i18 = iArr2[1];
            if (c0425n7.V == 8) {
                c0425n7.a = true;
            } else {
                float f2 = c0425n7.o;
                if (f2 < 1.0f && i17 == 3) {
                    c0425n7.j = 2;
                }
                float f3 = c0425n7.r;
                if (f3 < 1.0f && i18 == 3) {
                    c0425n7.k = 2;
                }
                char c2 = c;
                if (c0425n7.L > 0.0f) {
                    if (i17 == 3 && (i18 == 2 || i18 == 1)) {
                        c0425n7.j = 3;
                    } else if (i18 == 3 && (i17 == 2 || i17 == 1)) {
                        c0425n7.k = 3;
                    } else if (i17 == 3 && i18 == 3) {
                        if (c0425n7.j == 0) {
                            c0425n7.j = 3;
                        }
                        if (c0425n7.k == 0) {
                            c0425n7.k = 3;
                        }
                    }
                }
                Z6 z6 = c0425n7.z;
                Z6 z7 = c0425n7.x;
                if (i17 == 3 && c0425n7.j == 1 && (z7.d == null || z6.d == null)) {
                    i17 = 2;
                }
                Z6 z8 = c0425n7.A;
                Z6 z9 = c0425n7.y;
                int i19 = (i18 == 3 && c0425n7.k == 1 && (z9.d == null || z8.d == null)) ? 2 : i18;
                Ae ae = c0425n7.d;
                ae.d = i17;
                int i20 = c0425n7.j;
                ae.a = i20;
                C0600ss c0600ss3 = c0425n7.e;
                c0600ss3.d = i19;
                int i21 = c0425n7.k;
                c0600ss3.a = i21;
                ArrayList arrayList2 = arrayList;
                if (i17 == 4 || i17 == 1) {
                    if (i19 == 4) {
                        i = 1;
                    } else if (i19 != 1) {
                        i4 = 2;
                        if (i19 == 2) {
                            i = 1;
                        } else {
                            iArr = c0456o7.c0;
                            z6Arr = c0425n7.F;
                            i5 = 3;
                            if (i17 != 3) {
                                i6 = i4;
                                c0600ss = c0600ss3;
                            } else if (i19 == i4 && i19 != 1) {
                                c0600ss = c0600ss3;
                                i6 = 2;
                            } else if (i20 == 3) {
                                if (i19 == 2) {
                                    c0600ss2 = c0600ss3;
                                    f(2, 0, 2, 0, c0425n7);
                                } else {
                                    c0600ss2 = c0600ss3;
                                }
                                int i22 = c0425n7.i();
                                f(1, (int) ((i22 * c0425n7.L) + 0.5f), 1, i22, c0425n7);
                                ae.e.d(c0425n7.l());
                                c0600ss2.e.d(c0425n7.i());
                                c0425n7.a = true;
                            } else {
                                c0600ss = c0600ss3;
                                if (i20 == 1) {
                                    f(2, 0, i19, 0, c0425n7);
                                    ae.e.m = c0425n7.l();
                                } else if (i20 == 2) {
                                    i15 = iArr[c2];
                                    if (i15 != 1 || i15 == 4) {
                                        f(1, (int) ((f2 * c0456o7.l()) + 0.5f), i19, c0425n7.i(), c0425n7);
                                        ae.e.d(c0425n7.l());
                                        c0600ss.e.d(c0425n7.i());
                                        c0425n7.a = true;
                                    } else {
                                        i6 = 2;
                                        i5 = 3;
                                    }
                                } else if (z6Arr[c2].d != null || z6Arr[1].d == null) {
                                    f(2, 0, i19, 0, c0425n7);
                                    ae.e.d(c0425n7.l());
                                    c0600ss.e.d(c0425n7.i());
                                    c0425n7.a = true;
                                } else {
                                    i6 = 2;
                                    i5 = 3;
                                }
                            }
                            if (i19 != i5) {
                                i7 = i6;
                                i8 = 1;
                                i9 = i17;
                            } else if (i17 == i6 && i17 != 1) {
                                i11 = i5;
                                i7 = 2;
                                i8 = 1;
                                i9 = i17;
                                f3 = f3;
                                i10 = 1;
                                if (i9 == i11) {
                                    if (i20 != i10) {
                                        f(i7, 0, i7, 0, c0425n7);
                                        ae.e.m = c0425n7.l();
                                        c0600ss.e.m = c0425n7.i();
                                    } else {
                                        f(i7, 0, i7, 0, c0425n7);
                                        ae.e.m = c0425n7.l();
                                        c0600ss.e.m = c0425n7.i();
                                    }
                                }
                            } else if (i21 == i5) {
                                if (i17 == 2) {
                                    f(2, 0, 2, 0, c0425n7);
                                }
                                int iL2 = c0425n7.l();
                                f = c0425n7.L;
                                if (c0425n7.M == -1) {
                                    f = 1.0f / f;
                                }
                                f(1, iL2, 1, (int) ((iL2 * f) + 0.5f), c0425n7);
                                ae.e.d(c0425n7.l());
                                c0600ss.e.d(c0425n7.i());
                                c0425n7.a = true;
                            } else {
                                i8 = 1;
                                i7 = 2;
                                if (i21 == 1) {
                                    f(i17, 0, 2, 0, c0425n7);
                                    c0600ss.e.m = c0425n7.i();
                                } else {
                                    i9 = i17;
                                    if (i21 == 2) {
                                        i14 = iArr[1];
                                        if (i14 != 1 || i14 == 4) {
                                            f(i9, c0425n7.l(), 1, (int) ((c0456o7.i() * f3) + 0.5f), c0425n7);
                                            ae.e.d(c0425n7.l());
                                            c0600ss.e.d(c0425n7.i());
                                            c0425n7.a = true;
                                        }
                                    } else if (z6Arr[2].d != null || z6Arr[3].d == null) {
                                        f(2, 0, i19, 0, c0425n7);
                                        ae.e.d(c0425n7.l());
                                        c0600ss.e.d(c0425n7.i());
                                        c0425n7.a = true;
                                    } else {
                                        i10 = 1;
                                        i11 = 3;
                                        if (i9 == i11 && i19 == i11) {
                                            if (i20 != i10 || i21 == i10) {
                                                f(i7, 0, i7, 0, c0425n7);
                                                ae.e.m = c0425n7.l();
                                                c0600ss.e.m = c0425n7.i();
                                            } else if (i21 == 2 && i20 == 2 && (((i12 = iArr[c2]) == i8 || i12 == i8) && ((i13 = iArr[i10]) == i8 || i13 == i8))) {
                                                f(i8, (int) ((f2 * c0456o7.l()) + 0.5f), i8, (int) ((c0456o7.i() * f3) + 0.5f), c0425n7);
                                                ae.e.d(c0425n7.l());
                                                c0600ss.e.d(c0425n7.i());
                                                c0425n7.a = true;
                                            }
                                        }
                                    }
                                }
                            }
                            i10 = 1;
                            i11 = 3;
                            if (i9 == i11) {
                                if (i20 != i10) {
                                    f(i7, 0, i7, 0, c0425n7);
                                    ae.e.m = c0425n7.l();
                                    c0600ss.e.m = c0425n7.i();
                                } else {
                                    f(i7, 0, i7, 0, c0425n7);
                                    ae.e.m = c0425n7.l();
                                    c0600ss.e.m = c0425n7.i();
                                }
                            }
                        }
                    } else {
                        i = 1;
                    }
                    i2 = i17;
                    iL = c0425n7.l();
                    if (i2 == 4) {
                        iL = (c0456o7.l() - z7.e) - z6.e;
                        i2 = i;
                    }
                    int i23 = iL;
                    i3 = c0425n7.i();
                    if (i19 == 4) {
                        i3 = (c0456o7.i() - z9.e) - z8.e;
                        i19 = i;
                    }
                    f(i2, i23, i19, i3, c0425n7);
                    ae.e.d(c0425n7.l());
                    c0600ss3.e.d(c0425n7.i());
                    c0425n7.a = true;
                } else {
                    i4 = 2;
                    if (i17 == 2) {
                        if (i19 == 4) {
                            i = 1;
                        } else if (i19 != 1) {
                            i4 = 2;
                            if (i19 == 2) {
                                i = 1;
                            } else {
                                iArr = c0456o7.c0;
                                z6Arr = c0425n7.F;
                                i5 = 3;
                                if (i17 != 3) {
                                    i6 = i4;
                                    c0600ss = c0600ss3;
                                } else if (i19 == i4) {
                                    if (i20 == 3) {
                                        if (i19 == 2) {
                                            c0600ss2 = c0600ss3;
                                            f(2, 0, 2, 0, c0425n7);
                                        } else {
                                            c0600ss2 = c0600ss3;
                                        }
                                        int i24 = c0425n7.i();
                                        f(1, (int) ((i24 * c0425n7.L) + 0.5f), 1, i24, c0425n7);
                                        ae.e.d(c0425n7.l());
                                        c0600ss2.e.d(c0425n7.i());
                                        c0425n7.a = true;
                                    } else {
                                        c0600ss = c0600ss3;
                                        if (i20 == 1) {
                                            f(2, 0, i19, 0, c0425n7);
                                            ae.e.m = c0425n7.l();
                                        } else if (i20 == 2) {
                                            i15 = iArr[c2];
                                            if (i15 != 1) {
                                            }
                                            f(1, (int) ((f2 * c0456o7.l()) + 0.5f), i19, c0425n7.i(), c0425n7);
                                            ae.e.d(c0425n7.l());
                                            c0600ss.e.d(c0425n7.i());
                                            c0425n7.a = true;
                                        } else {
                                            if (z6Arr[c2].d != null) {
                                            }
                                            f(2, 0, i19, 0, c0425n7);
                                            ae.e.d(c0425n7.l());
                                            c0600ss.e.d(c0425n7.i());
                                            c0425n7.a = true;
                                        }
                                    }
                                } else if (i20 == 3) {
                                    if (i19 == 2) {
                                        c0600ss2 = c0600ss3;
                                        f(2, 0, 2, 0, c0425n7);
                                    } else {
                                        c0600ss2 = c0600ss3;
                                    }
                                    int i25 = c0425n7.i();
                                    f(1, (int) ((i25 * c0425n7.L) + 0.5f), 1, i25, c0425n7);
                                    ae.e.d(c0425n7.l());
                                    c0600ss2.e.d(c0425n7.i());
                                    c0425n7.a = true;
                                } else {
                                    c0600ss = c0600ss3;
                                    if (i20 == 1) {
                                        f(2, 0, i19, 0, c0425n7);
                                        ae.e.m = c0425n7.l();
                                    } else if (i20 == 2) {
                                        i15 = iArr[c2];
                                        if (i15 != 1) {
                                        }
                                        f(1, (int) ((f2 * c0456o7.l()) + 0.5f), i19, c0425n7.i(), c0425n7);
                                        ae.e.d(c0425n7.l());
                                        c0600ss.e.d(c0425n7.i());
                                        c0425n7.a = true;
                                    } else {
                                        if (z6Arr[c2].d != null) {
                                        }
                                        f(2, 0, i19, 0, c0425n7);
                                        ae.e.d(c0425n7.l());
                                        c0600ss.e.d(c0425n7.i());
                                        c0425n7.a = true;
                                    }
                                }
                                if (i19 != i5) {
                                    if (i17 == i6) {
                                    }
                                    if (i21 == i5) {
                                        if (i17 == 2) {
                                            f(2, 0, 2, 0, c0425n7);
                                        }
                                        int iL3 = c0425n7.l();
                                        f = c0425n7.L;
                                        if (c0425n7.M == -1) {
                                            f = 1.0f / f;
                                        }
                                        f(1, iL3, 1, (int) ((iL3 * f) + 0.5f), c0425n7);
                                        ae.e.d(c0425n7.l());
                                        c0600ss.e.d(c0425n7.i());
                                        c0425n7.a = true;
                                    } else {
                                        i8 = 1;
                                        i7 = 2;
                                        if (i21 == 1) {
                                            f(i17, 0, 2, 0, c0425n7);
                                            c0600ss.e.m = c0425n7.i();
                                        } else {
                                            i9 = i17;
                                            if (i21 == 2) {
                                                i14 = iArr[1];
                                                if (i14 != 1) {
                                                }
                                                f(i9, c0425n7.l(), 1, (int) ((c0456o7.i() * f3) + 0.5f), c0425n7);
                                                ae.e.d(c0425n7.l());
                                                c0600ss.e.d(c0425n7.i());
                                                c0425n7.a = true;
                                            } else {
                                                if (z6Arr[2].d != null) {
                                                }
                                                f(2, 0, i19, 0, c0425n7);
                                                ae.e.d(c0425n7.l());
                                                c0600ss.e.d(c0425n7.i());
                                                c0425n7.a = true;
                                            }
                                        }
                                    }
                                } else {
                                    i7 = i6;
                                    i8 = 1;
                                    i9 = i17;
                                }
                                i10 = 1;
                                i11 = 3;
                                if (i9 == i11) {
                                    if (i20 != i10) {
                                        f(i7, 0, i7, 0, c0425n7);
                                        ae.e.m = c0425n7.l();
                                        c0600ss.e.m = c0425n7.i();
                                    } else {
                                        f(i7, 0, i7, 0, c0425n7);
                                        ae.e.m = c0425n7.l();
                                        c0600ss.e.m = c0425n7.i();
                                    }
                                }
                            }
                        } else {
                            i = 1;
                        }
                        i2 = i17;
                        iL = c0425n7.l();
                        if (i2 == 4) {
                            iL = (c0456o7.l() - z7.e) - z6.e;
                            i2 = i;
                        }
                        int i26 = iL;
                        i3 = c0425n7.i();
                        if (i19 == 4) {
                            i3 = (c0456o7.i() - z9.e) - z8.e;
                            i19 = i;
                        }
                        f(i2, i26, i19, i3, c0425n7);
                        ae.e.d(c0425n7.l());
                        c0600ss3.e.d(c0425n7.i());
                        c0425n7.a = true;
                    } else {
                        iArr = c0456o7.c0;
                        z6Arr = c0425n7.F;
                        i5 = 3;
                        if (i17 != 3) {
                            i6 = i4;
                            c0600ss = c0600ss3;
                        } else if (i19 == i4) {
                            if (i20 == 3) {
                                if (i19 == 2) {
                                    c0600ss2 = c0600ss3;
                                    f(2, 0, 2, 0, c0425n7);
                                } else {
                                    c0600ss2 = c0600ss3;
                                }
                                int i27 = c0425n7.i();
                                f(1, (int) ((i27 * c0425n7.L) + 0.5f), 1, i27, c0425n7);
                                ae.e.d(c0425n7.l());
                                c0600ss2.e.d(c0425n7.i());
                                c0425n7.a = true;
                            } else {
                                c0600ss = c0600ss3;
                                if (i20 == 1) {
                                    f(2, 0, i19, 0, c0425n7);
                                    ae.e.m = c0425n7.l();
                                } else if (i20 == 2) {
                                    i15 = iArr[c2];
                                    if (i15 != 1) {
                                    }
                                    f(1, (int) ((f2 * c0456o7.l()) + 0.5f), i19, c0425n7.i(), c0425n7);
                                    ae.e.d(c0425n7.l());
                                    c0600ss.e.d(c0425n7.i());
                                    c0425n7.a = true;
                                } else {
                                    if (z6Arr[c2].d != null) {
                                    }
                                    f(2, 0, i19, 0, c0425n7);
                                    ae.e.d(c0425n7.l());
                                    c0600ss.e.d(c0425n7.i());
                                    c0425n7.a = true;
                                }
                            }
                        } else if (i20 == 3) {
                            if (i19 == 2) {
                                c0600ss2 = c0600ss3;
                                f(2, 0, 2, 0, c0425n7);
                            } else {
                                c0600ss2 = c0600ss3;
                            }
                            int i28 = c0425n7.i();
                            f(1, (int) ((i28 * c0425n7.L) + 0.5f), 1, i28, c0425n7);
                            ae.e.d(c0425n7.l());
                            c0600ss2.e.d(c0425n7.i());
                            c0425n7.a = true;
                        } else {
                            c0600ss = c0600ss3;
                            if (i20 == 1) {
                                f(2, 0, i19, 0, c0425n7);
                                ae.e.m = c0425n7.l();
                            } else if (i20 == 2) {
                                i15 = iArr[c2];
                                if (i15 != 1) {
                                }
                                f(1, (int) ((f2 * c0456o7.l()) + 0.5f), i19, c0425n7.i(), c0425n7);
                                ae.e.d(c0425n7.l());
                                c0600ss.e.d(c0425n7.i());
                                c0425n7.a = true;
                            } else {
                                if (z6Arr[c2].d != null) {
                                }
                                f(2, 0, i19, 0, c0425n7);
                                ae.e.d(c0425n7.l());
                                c0600ss.e.d(c0425n7.i());
                                c0425n7.a = true;
                            }
                        }
                        if (i19 != i5) {
                            if (i17 == i6) {
                            }
                            if (i21 == i5) {
                                if (i17 == 2) {
                                    f(2, 0, 2, 0, c0425n7);
                                }
                                int iL4 = c0425n7.l();
                                f = c0425n7.L;
                                if (c0425n7.M == -1) {
                                    f = 1.0f / f;
                                }
                                f(1, iL4, 1, (int) ((iL4 * f) + 0.5f), c0425n7);
                                ae.e.d(c0425n7.l());
                                c0600ss.e.d(c0425n7.i());
                                c0425n7.a = true;
                            } else {
                                i8 = 1;
                                i7 = 2;
                                if (i21 == 1) {
                                    f(i17, 0, 2, 0, c0425n7);
                                    c0600ss.e.m = c0425n7.i();
                                } else {
                                    i9 = i17;
                                    if (i21 == 2) {
                                        i14 = iArr[1];
                                        if (i14 != 1) {
                                        }
                                        f(i9, c0425n7.l(), 1, (int) ((c0456o7.i() * f3) + 0.5f), c0425n7);
                                        ae.e.d(c0425n7.l());
                                        c0600ss.e.d(c0425n7.i());
                                        c0425n7.a = true;
                                    } else {
                                        if (z6Arr[2].d != null) {
                                        }
                                        f(2, 0, i19, 0, c0425n7);
                                        ae.e.d(c0425n7.l());
                                        c0600ss.e.d(c0425n7.i());
                                        c0425n7.a = true;
                                    }
                                }
                            }
                        } else {
                            i7 = i6;
                            i8 = 1;
                            i9 = i17;
                        }
                        i10 = 1;
                        i11 = 3;
                        if (i9 == i11) {
                            if (i20 != i10) {
                                f(i7, 0, i7, 0, c0425n7);
                                ae.e.m = c0425n7.l();
                                c0600ss.e.m = c0425n7.i();
                            } else {
                                f(i7, 0, i7, 0, c0425n7);
                                ae.e.m = c0425n7.l();
                                c0600ss.e.m = c0425n7.i();
                            }
                        }
                    }
                }
                c = c2;
                arrayList = arrayList2;
            }
        }
    }

    public final void c() {
        ArrayList arrayList = this.e;
        arrayList.clear();
        C0456o7 c0456o7 = this.d;
        c0456o7.d.f();
        C0600ss c0600ss = c0456o7.e;
        c0600ss.f();
        arrayList.add(c0456o7.d);
        arrayList.add(c0600ss);
        ArrayList arrayList2 = c0456o7.d0;
        int size = arrayList2.size();
        HashSet hashSet = null;
        int i = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            C0425n7 c0425n7 = (C0425n7) obj;
            if (c0425n7 instanceof Xd) {
                Yd yd = new Yd(c0425n7);
                c0425n7.d.f();
                c0425n7.e.f();
                yd.f = ((Xd) c0425n7).h0;
                arrayList.add(yd);
            } else {
                if (c0425n7.q()) {
                    if (c0425n7.b == null) {
                        c0425n7.b = new J5(c0425n7, 0);
                    }
                    if (hashSet == null) {
                        hashSet = new HashSet();
                    }
                    hashSet.add(c0425n7.b);
                } else {
                    arrayList.add(c0425n7.d);
                }
                if (c0425n7.r()) {
                    if (c0425n7.c == null) {
                        c0425n7.c = new J5(c0425n7, 1);
                    }
                    if (hashSet == null) {
                        hashSet = new HashSet();
                    }
                    hashSet.add(c0425n7.c);
                } else {
                    arrayList.add(c0425n7.e);
                }
                if (c0425n7 instanceof AbstractC0463oe) {
                    arrayList.add(new C0432ne(c0425n7));
                }
            }
        }
        if (hashSet != null) {
            arrayList.addAll(hashSet);
        }
        int size2 = arrayList.size();
        int i2 = 0;
        while (i2 < size2) {
            Object obj2 = arrayList.get(i2);
            i2++;
            ((AbstractC0725wt) obj2).f();
        }
        int size3 = arrayList.size();
        int i3 = 0;
        while (i3 < size3) {
            Object obj3 = arrayList.get(i3);
            i3++;
            AbstractC0725wt abstractC0725wt = (AbstractC0725wt) obj3;
            if (abstractC0725wt.b != c0456o7) {
                abstractC0725wt.d();
            }
        }
        ArrayList arrayList3 = this.h;
        arrayList3.clear();
        C0456o7 c0456o8 = this.a;
        e(c0456o8.d, 0, arrayList3);
        e(c0456o8.e, 1, arrayList3);
        this.b = false;
    }

    public final int d(C0456o7 c0456o7, int i) {
        ArrayList arrayList;
        int i2;
        int i3;
        long j;
        float f;
        long j2;
        ArrayList arrayList2 = this.h;
        int size = arrayList2.size();
        int i4 = 0;
        long jMax = 0;
        while (i4 < size) {
            AbstractC0725wt abstractC0725wt = ((C0316jm) arrayList2.get(i4)).a;
            if (!(abstractC0725wt instanceof J5) ? !(i != 0 ? (abstractC0725wt instanceof C0600ss) : (abstractC0725wt instanceof Ae)) : ((J5) abstractC0725wt).f != i) {
                M9 m9 = (i == 0 ? c0456o7.d : c0456o7.e).h;
                M9 m10 = (i == 0 ? c0456o7.d : c0456o7.e).i;
                boolean zContains = abstractC0725wt.h.l.contains(m9);
                M9 m11 = abstractC0725wt.i;
                boolean zContains2 = m11.l.contains(m10);
                long j3 = abstractC0725wt.j();
                M9 m12 = abstractC0725wt.h;
                if (zContains && zContains2) {
                    long jB = C0316jm.b(m12, 0L);
                    ArrayList arrayList3 = arrayList2;
                    i2 = size;
                    long jA = C0316jm.a(m11, 0L);
                    long j4 = jB - j3;
                    int i5 = m11.f;
                    arrayList = arrayList3;
                    i3 = i4;
                    if (j4 >= (-i5)) {
                        j4 += (long) i5;
                    }
                    long j5 = (-jA) - j3;
                    long j6 = m12.f;
                    long j7 = j5 - j6;
                    if (j7 >= j6) {
                        j7 -= j6;
                    }
                    C0425n7 c0425n7 = abstractC0725wt.b;
                    if (i == 0) {
                        f = c0425n7.S;
                    } else if (i == 1) {
                        f = c0425n7.T;
                    } else {
                        c0425n7.getClass();
                        f = -1.0f;
                    }
                    if (f > 0.0f) {
                        j2 = (long) ((j4 / (1.0f - f)) + (j7 / f));
                    } else {
                        j2 = 0;
                    }
                    float f2 = j2;
                    j = (((long) m12.f) + ((((long) ((f2 * f) + 0.5f)) + j3) + ((long) (((1.0f - f) * f2) + 0.5f)))) - ((long) m11.f);
                } else {
                    arrayList = arrayList2;
                    i2 = size;
                    i3 = i4;
                    if (zContains) {
                        j = Math.max(C0316jm.b(m12, m12.f), ((long) m12.f) + j3);
                    } else if (zContains2) {
                        j = Math.max(-C0316jm.a(m11, m11.f), ((long) (-m11.f)) + j3);
                    } else {
                        j = (abstractC0725wt.j() + ((long) m12.f)) - ((long) m11.f);
                    }
                }
            } else {
                arrayList = arrayList2;
                i2 = size;
                i3 = i4;
                j = 0;
            }
            jMax = Math.max(jMax, j);
            i4 = i3 + 1;
            size = i2;
            arrayList2 = arrayList;
        }
        return (int) jMax;
    }

    public final void e(AbstractC0725wt abstractC0725wt, int i, ArrayList arrayList) {
        M9 m9;
        ArrayList arrayList2 = abstractC0725wt.h.k;
        int size = arrayList2.size();
        int i2 = 0;
        int i3 = 0;
        while (true) {
            m9 = abstractC0725wt.i;
            if (i3 >= size) {
                break;
            }
            Object obj = arrayList2.get(i3);
            i3++;
            K9 k9 = (K9) obj;
            if (k9 instanceof M9) {
                a((M9) k9, i, arrayList, null);
            } else if (k9 instanceof AbstractC0725wt) {
                a(((AbstractC0725wt) k9).h, i, arrayList, null);
            }
        }
        ArrayList arrayList3 = m9.k;
        int size2 = arrayList3.size();
        int i4 = 0;
        while (i4 < size2) {
            Object obj2 = arrayList3.get(i4);
            i4++;
            K9 k10 = (K9) obj2;
            if (k10 instanceof M9) {
                a((M9) k10, i, arrayList, null);
            } else if (k10 instanceof AbstractC0725wt) {
                a(((AbstractC0725wt) k10).i, i, arrayList, null);
            }
        }
        if (i == 1) {
            ArrayList arrayList4 = ((C0600ss) abstractC0725wt).k.k;
            int size3 = arrayList4.size();
            while (i2 < size3) {
                Object obj3 = arrayList4.get(i2);
                i2++;
                K9 k11 = (K9) obj3;
                if (k11 instanceof M9) {
                    a((M9) k11, i, arrayList, null);
                }
            }
        }
    }

    public final void f(int i, int i2, int i3, int i4, C0425n7 c0425n7) {
        Z4 z4 = this.g;
        z4.a = i;
        z4.b = i3;
        z4.c = i2;
        z4.d = i4;
        this.f.a(c0425n7, z4);
        c0425n7.y(z4.e);
        c0425n7.v(z4.f);
        c0425n7.w = z4.h;
        int i5 = z4.g;
        c0425n7.P = i5;
        c0425n7.w = i5 > 0;
    }

    public final void g() {
        Y4 y4;
        L9 l9 = this;
        ArrayList arrayList = l9.a.d0;
        int size = arrayList.size();
        char c = 0;
        int i = 0;
        while (i < size) {
            int i2 = i + 1;
            C0425n7 c0425n7 = (C0425n7) arrayList.get(i);
            if (!c0425n7.a) {
                int[] iArr = c0425n7.c0;
                int i3 = iArr[c];
                int i4 = iArr[1];
                int i5 = c0425n7.j;
                int i6 = c0425n7.k;
                char c2 = (i3 == 2 || (i3 == 3 && i5 == 1)) ? (char) 1 : c;
                char c3 = (i4 == 2 || (i4 == 3 && i6 == 1)) ? (char) 1 : c;
                Ae ae = c0425n7.d;
                X9 x9 = ae.e;
                boolean z = x9.j;
                C0600ss c0600ss = c0425n7.e;
                X9 x10 = c0600ss.e;
                boolean z2 = x10.j;
                char c4 = c2;
                if (z && z2) {
                    l9.f(1, x9.g, 1, x10.g, c0425n7);
                    c0425n7.a = true;
                } else if (z && c3 != 0) {
                    f(1, x9.g, 2, x10.g, c0425n7);
                    if (i4 == 3) {
                        c0600ss.e.m = c0425n7.i();
                    } else {
                        c0600ss.e.d(c0425n7.i());
                        c0425n7.a = true;
                    }
                } else if (z2 && c4 != 0) {
                    f(2, x9.g, 1, x10.g, c0425n7);
                    if (i3 == 3) {
                        ae.e.m = c0425n7.l();
                    } else {
                        ae.e.d(c0425n7.l());
                        c0425n7.a = true;
                    }
                }
                if (c0425n7.a && (y4 = c0600ss.l) != null) {
                    y4.d(c0425n7.P);
                }
                c = 0;
                l9 = this;
            }
            i = i2;
        }
    }
}
