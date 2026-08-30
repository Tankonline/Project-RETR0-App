package rikka.shizuku;

import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.helper.widget.Flow;
import androidx.constraintlayout.widget.ConstraintLayout;

/* JADX INFO: renamed from: rikka.shizuku.e7, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0148e7 {
    public final ConstraintLayout a;
    public int b;
    public int c;
    public int d;
    public int e;
    public int f;
    public int g;

    public C0148e7(ConstraintLayout constraintLayout) {
        this.a = constraintLayout;
    }

    /* JADX WARN: Code duplicated, block: B:101:0x0174  */
    /* JADX WARN: Code duplicated, block: B:104:0x0178  */
    /* JADX WARN: Code duplicated, block: B:107:0x0180  */
    /* JADX WARN: Code duplicated, block: B:109:0x0184  */
    /* JADX WARN: Code duplicated, block: B:112:0x018e  */
    /* JADX WARN: Code duplicated, block: B:115:0x019e A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:124:0x01b2  */
    /* JADX WARN: Code duplicated, block: B:126:0x01b6  */
    /* JADX WARN: Code duplicated, block: B:129:0x01c4  */
    /* JADX WARN: Code duplicated, block: B:132:0x01d5  */
    /* JADX WARN: Code duplicated, block: B:133:0x01dc  */
    /* JADX WARN: Code duplicated, block: B:135:0x01e4  */
    /* JADX WARN: Code duplicated, block: B:136:0x01eb  */
    /* JADX WARN: Code duplicated, block: B:139:0x01f5  */
    /* JADX WARN: Code duplicated, block: B:140:0x01fa  */
    /* JADX WARN: Code duplicated, block: B:143:0x01ff  */
    /* JADX WARN: Code duplicated, block: B:146:0x0207  */
    /* JADX WARN: Code duplicated, block: B:147:0x020c  */
    /* JADX WARN: Code duplicated, block: B:150:0x0211  */
    /* JADX WARN: Code duplicated, block: B:153:0x0219 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:155:0x0222 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:156:0x0224 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:159:0x022e A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:160:0x0230  */
    /* JADX WARN: Code duplicated, block: B:162:0x0234  */
    /* JADX WARN: Code duplicated, block: B:164:0x023a  */
    /* JADX WARN: Code duplicated, block: B:167:0x0251  */
    /* JADX WARN: Code duplicated, block: B:168:0x0254  */
    /* JADX WARN: Code duplicated, block: B:171:0x025a  */
    /* JADX WARN: Code duplicated, block: B:175:0x0262  */
    /* JADX WARN: Code duplicated, block: B:178:0x026a  */
    /* JADX WARN: Code duplicated, block: B:180:0x026e  */
    /* JADX WARN: Code duplicated, block: B:49:0x00c7 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:50:0x00c9  */
    /* JADX WARN: Code duplicated, block: B:52:0x00cd  */
    /* JADX WARN: Code duplicated, block: B:54:0x00d0  */
    /* JADX WARN: Code duplicated, block: B:55:0x00d6  */
    /* JADX WARN: Code duplicated, block: B:57:0x00da  */
    /* JADX WARN: Code duplicated, block: B:58:0x00df  */
    /* JADX WARN: Code duplicated, block: B:60:0x00e3  */
    /* JADX WARN: Code duplicated, block: B:62:0x00f8  */
    /* JADX WARN: Code duplicated, block: B:64:0x0105  */
    /* JADX WARN: Code duplicated, block: B:65:0x0107  */
    /* JADX WARN: Code duplicated, block: B:68:0x010f  */
    /* JADX WARN: Code duplicated, block: B:69:0x0111  */
    /* JADX WARN: Code duplicated, block: B:75:0x0122  */
    /* JADX WARN: Code duplicated, block: B:77:0x0126 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:79:0x0129  */
    /* JADX WARN: Code duplicated, block: B:83:0x013c  */
    /* JADX WARN: Code duplicated, block: B:84:0x014a  */
    /* JADX WARN: Code duplicated, block: B:86:0x0158  */
    /* JADX WARN: Code duplicated, block: B:87:0x015a  */
    /* JADX WARN: Code duplicated, block: B:89:0x015e  */
    /* JADX WARN: Code duplicated, block: B:90:0x0160  */
    /* JADX WARN: Code duplicated, block: B:93:0x0165 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:96:0x016b  */
    /* JADX WARN: Code duplicated, block: B:98:0x016e A[ADDED_TO_REGION] */
    public final void a(C0425n7 c0425n7, Z4 z4) {
        int iMakeMeasureSpec;
        boolean z;
        int iJ;
        int i;
        int iMakeMeasureSpec2;
        boolean z2;
        boolean z3;
        boolean z5;
        boolean z6;
        boolean z7;
        boolean z8;
        boolean z9;
        C0118d7 c0118d7;
        int measuredWidth;
        int measuredHeight;
        int baseline;
        int i2;
        int measuredWidth2;
        int i3;
        int i4;
        int measuredHeight2;
        int i5;
        boolean z10;
        boolean z11;
        boolean z12;
        boolean z13;
        int i6;
        int childMeasureSpec;
        if (c0425n7 == null) {
            return;
        }
        if (c0425n7.V == 8) {
            z4.e = 0;
            z4.f = 0;
            z4.g = 0;
            return;
        }
        int i7 = z4.a;
        int i8 = z4.b;
        int i9 = z4.c;
        int i10 = z4.d;
        int i11 = this.b + this.c;
        int i12 = this.d;
        View view = c0425n7.U;
        int iJ2 = AbstractC0535qo.j(i7);
        Z6 z14 = c0425n7.z;
        Z6 z15 = c0425n7.x;
        int[] iArr = c0425n7.g;
        int i13 = 2;
        if (iJ2 != 0) {
            if (iJ2 == 1) {
                i13 = 2;
                childMeasureSpec = ViewGroup.getChildMeasureSpec(this.f, i12, -2);
                iArr[2] = -2;
            } else {
                if (iJ2 != 2) {
                    if (iJ2 != 3) {
                        i13 = 2;
                        z = false;
                        iMakeMeasureSpec = 0;
                    } else {
                        int i14 = this.f;
                        int i15 = z15 != null ? z15.e : 0;
                        if (z14 != null) {
                            i15 += z14.e;
                        }
                        iMakeMeasureSpec = ViewGroup.getChildMeasureSpec(i14, i12 + i15, -1);
                        iArr[i13] = -1;
                    }
                    iJ = AbstractC0535qo.j(i8);
                    if (iJ == 0) {
                        i = 3;
                        int iMakeMeasureSpec3 = View.MeasureSpec.makeMeasureSpec(i10, 1073741824);
                        iArr[3] = i10;
                        iMakeMeasureSpec2 = iMakeMeasureSpec3;
                        z2 = false;
                    } else if (iJ == 1) {
                        iMakeMeasureSpec2 = ViewGroup.getChildMeasureSpec(this.g, i11, -2);
                        i = 3;
                        iArr[3] = -2;
                        z2 = true;
                    } else if (iJ == i13) {
                        iMakeMeasureSpec2 = ViewGroup.getChildMeasureSpec(this.g, i11, -2);
                        if (c0425n7.k == 1) {
                            z12 = true;
                        } else {
                            z12 = false;
                        }
                        iArr[3] = 0;
                        if (z4.j) {
                            if (z12 || iArr[2] == 0 || iArr[1] == c0425n7.i()) {
                                z13 = false;
                            } else {
                                z13 = true;
                            }
                            if (z12 || z13) {
                                iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(c0425n7.i(), 1073741824);
                                z2 = false;
                            } else {
                                z2 = true;
                            }
                        } else {
                            z2 = true;
                        }
                        i = 3;
                    } else if (iJ != 3) {
                        i = 3;
                        iMakeMeasureSpec2 = 0;
                        z2 = false;
                    } else {
                        int i16 = this.g;
                        if (z15 != null) {
                            i6 = c0425n7.y.e;
                        } else {
                            i6 = 0;
                        }
                        if (z14 != null) {
                            i6 += c0425n7.A.e;
                        }
                        iMakeMeasureSpec2 = ViewGroup.getChildMeasureSpec(i16, i11 + i6, -1);
                        iArr[3] = -1;
                        z2 = false;
                        i = 3;
                    }
                    if (i7 == i) {
                        z3 = true;
                    } else {
                        z3 = false;
                    }
                    if (i8 == i) {
                        z5 = true;
                    } else {
                        z5 = false;
                    }
                    if (i8 != 4 || i8 == 1) {
                        z6 = true;
                    } else {
                        z6 = false;
                    }
                    if (i7 != 4 || i7 == 1) {
                        z7 = true;
                    } else {
                        z7 = false;
                    }
                    if (z3 || c0425n7.L <= 0.0f) {
                        z8 = false;
                    } else {
                        z8 = true;
                    }
                    if (z5 || c0425n7.L <= 0.0f) {
                        z9 = false;
                    } else {
                        z9 = true;
                    }
                    c0118d7 = (C0118d7) view.getLayoutParams();
                    if (z4.j && z3 && c0425n7.j == 0 && z5 && c0425n7.k == 0) {
                        measuredWidth2 = 0;
                        measuredHeight2 = 0;
                        baseline = 0;
                    } else {
                        if ((view instanceof Flow) || !(c0425n7 instanceof C0153ec)) {
                            view.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
                        } else {
                            ((Flow) view).i((C0153ec) c0425n7, iMakeMeasureSpec, iMakeMeasureSpec2);
                        }
                        measuredWidth = view.getMeasuredWidth();
                        measuredHeight = view.getMeasuredHeight();
                        baseline = view.getBaseline();
                        if (z) {
                            iArr[0] = measuredWidth;
                            iArr[2] = measuredHeight;
                        } else {
                            iArr[0] = 0;
                            iArr[2] = 0;
                        }
                        if (z2) {
                            iArr[1] = measuredHeight;
                            iArr[3] = measuredWidth;
                        } else {
                            iArr[1] = 0;
                            iArr[3] = 0;
                        }
                        i2 = c0425n7.m;
                        if (i2 > 0) {
                            measuredWidth2 = Math.max(i2, measuredWidth);
                        } else {
                            measuredWidth2 = measuredWidth;
                        }
                        i3 = c0425n7.n;
                        if (i3 > 0) {
                            measuredWidth2 = Math.min(i3, measuredWidth2);
                        }
                        i4 = c0425n7.p;
                        if (i4 > 0) {
                            measuredHeight2 = Math.max(i4, measuredHeight);
                        } else {
                            measuredHeight2 = measuredHeight;
                        }
                        i5 = c0425n7.q;
                        if (i5 > 0) {
                            measuredHeight2 = Math.min(i5, measuredHeight2);
                        }
                        if (!z8 && z6) {
                            measuredWidth2 = (int) ((measuredHeight2 * c0425n7.L) + 0.5f);
                        } else if (z9 && z7) {
                            measuredHeight2 = (int) ((measuredWidth2 / c0425n7.L) + 0.5f);
                        }
                        if (measuredWidth == measuredWidth2 || measuredHeight != measuredHeight2) {
                            if (measuredWidth != measuredWidth2) {
                                iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(measuredWidth2, 1073741824);
                            }
                            if (measuredHeight != measuredHeight2) {
                                iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(measuredHeight2, 1073741824);
                            }
                            view.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
                            measuredWidth2 = view.getMeasuredWidth();
                            measuredHeight2 = view.getMeasuredHeight();
                            baseline = view.getBaseline();
                        }
                    }
                    if (baseline != -1) {
                        z10 = true;
                    } else {
                        z10 = false;
                    }
                    if (measuredWidth2 == z4.c || measuredHeight2 != z4.d) {
                        z11 = true;
                    } else {
                        z11 = false;
                    }
                    z4.i = z11;
                    if (c0118d7.X) {
                        z10 = true;
                    }
                    if (z10 && baseline != -1 && c0425n7.P != baseline) {
                        z4.i = true;
                    }
                    z4.e = measuredWidth2;
                    z4.f = measuredHeight2;
                    z4.h = z10;
                    z4.g = baseline;
                }
                i13 = 2;
                childMeasureSpec = ViewGroup.getChildMeasureSpec(this.f, i12, -2);
                boolean z16 = c0425n7.j == 1;
                iArr[2] = 0;
                if (z4.j) {
                    boolean z17 = (!z16 || iArr[3] == 0 || iArr[0] == c0425n7.l()) ? false : true;
                    if (!z16 || z17) {
                        iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(c0425n7.l(), 1073741824);
                    }
                }
            }
            iMakeMeasureSpec = childMeasureSpec;
            z = true;
            iJ = AbstractC0535qo.j(i8);
            if (iJ == 0) {
                i = 3;
                int iMakeMeasureSpec4 = View.MeasureSpec.makeMeasureSpec(i10, 1073741824);
                iArr[3] = i10;
                iMakeMeasureSpec2 = iMakeMeasureSpec4;
                z2 = false;
            } else if (iJ == 1) {
                iMakeMeasureSpec2 = ViewGroup.getChildMeasureSpec(this.g, i11, -2);
                i = 3;
                iArr[3] = -2;
                z2 = true;
            } else if (iJ == i13) {
                iMakeMeasureSpec2 = ViewGroup.getChildMeasureSpec(this.g, i11, -2);
                if (c0425n7.k == 1) {
                    z12 = true;
                } else {
                    z12 = false;
                }
                iArr[3] = 0;
                if (z4.j) {
                    z2 = true;
                } else {
                    if (z12) {
                        z13 = false;
                    } else {
                        z13 = false;
                    }
                    if (z12) {
                    }
                    iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(c0425n7.i(), 1073741824);
                    z2 = false;
                }
                i = 3;
            } else if (iJ != 3) {
                i = 3;
                iMakeMeasureSpec2 = 0;
                z2 = false;
            } else {
                int i17 = this.g;
                if (z15 != null) {
                    i6 = c0425n7.y.e;
                } else {
                    i6 = 0;
                }
                if (z14 != null) {
                    i6 += c0425n7.A.e;
                }
                iMakeMeasureSpec2 = ViewGroup.getChildMeasureSpec(i17, i11 + i6, -1);
                iArr[3] = -1;
                z2 = false;
                i = 3;
            }
            if (i7 == i) {
                z3 = true;
            } else {
                z3 = false;
            }
            if (i8 == i) {
                z5 = true;
            } else {
                z5 = false;
            }
            if (i8 != 4) {
                z6 = true;
            } else {
                z6 = true;
            }
            if (i7 != 4) {
                z7 = true;
            } else {
                z7 = true;
            }
            if (z3) {
                z8 = false;
            } else {
                z8 = false;
            }
            if (z5) {
                z9 = false;
            } else {
                z9 = false;
            }
            c0118d7 = (C0118d7) view.getLayoutParams();
            if (z4.j) {
                if (view instanceof Flow) {
                    view.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
                } else {
                    view.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
                }
                measuredWidth = view.getMeasuredWidth();
                measuredHeight = view.getMeasuredHeight();
                baseline = view.getBaseline();
                if (z) {
                    iArr[0] = measuredWidth;
                    iArr[2] = measuredHeight;
                } else {
                    iArr[0] = 0;
                    iArr[2] = 0;
                }
                if (z2) {
                    iArr[1] = measuredHeight;
                    iArr[3] = measuredWidth;
                } else {
                    iArr[1] = 0;
                    iArr[3] = 0;
                }
                i2 = c0425n7.m;
                if (i2 > 0) {
                    measuredWidth2 = Math.max(i2, measuredWidth);
                } else {
                    measuredWidth2 = measuredWidth;
                }
                i3 = c0425n7.n;
                if (i3 > 0) {
                    measuredWidth2 = Math.min(i3, measuredWidth2);
                }
                i4 = c0425n7.p;
                if (i4 > 0) {
                    measuredHeight2 = Math.max(i4, measuredHeight);
                } else {
                    measuredHeight2 = measuredHeight;
                }
                i5 = c0425n7.q;
                if (i5 > 0) {
                    measuredHeight2 = Math.min(i5, measuredHeight2);
                }
                if (!z8) {
                    if (z9) {
                        measuredHeight2 = (int) ((measuredWidth2 / c0425n7.L) + 0.5f);
                    }
                } else if (z9) {
                    measuredHeight2 = (int) ((measuredWidth2 / c0425n7.L) + 0.5f);
                }
                if (measuredWidth == measuredWidth2) {
                    if (measuredWidth != measuredWidth2) {
                        iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(measuredWidth2, 1073741824);
                    }
                    if (measuredHeight != measuredHeight2) {
                        iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(measuredHeight2, 1073741824);
                    }
                    view.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
                    measuredWidth2 = view.getMeasuredWidth();
                    measuredHeight2 = view.getMeasuredHeight();
                    baseline = view.getBaseline();
                } else {
                    if (measuredWidth != measuredWidth2) {
                        iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(measuredWidth2, 1073741824);
                    }
                    if (measuredHeight != measuredHeight2) {
                        iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(measuredHeight2, 1073741824);
                    }
                    view.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
                    measuredWidth2 = view.getMeasuredWidth();
                    measuredHeight2 = view.getMeasuredHeight();
                    baseline = view.getBaseline();
                }
            } else {
                if (view instanceof Flow) {
                    view.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
                } else {
                    view.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
                }
                measuredWidth = view.getMeasuredWidth();
                measuredHeight = view.getMeasuredHeight();
                baseline = view.getBaseline();
                if (z) {
                    iArr[0] = measuredWidth;
                    iArr[2] = measuredHeight;
                } else {
                    iArr[0] = 0;
                    iArr[2] = 0;
                }
                if (z2) {
                    iArr[1] = measuredHeight;
                    iArr[3] = measuredWidth;
                } else {
                    iArr[1] = 0;
                    iArr[3] = 0;
                }
                i2 = c0425n7.m;
                if (i2 > 0) {
                    measuredWidth2 = Math.max(i2, measuredWidth);
                } else {
                    measuredWidth2 = measuredWidth;
                }
                i3 = c0425n7.n;
                if (i3 > 0) {
                    measuredWidth2 = Math.min(i3, measuredWidth2);
                }
                i4 = c0425n7.p;
                if (i4 > 0) {
                    measuredHeight2 = Math.max(i4, measuredHeight);
                } else {
                    measuredHeight2 = measuredHeight;
                }
                i5 = c0425n7.q;
                if (i5 > 0) {
                    measuredHeight2 = Math.min(i5, measuredHeight2);
                }
                if (!z8) {
                    if (z9) {
                        measuredHeight2 = (int) ((measuredWidth2 / c0425n7.L) + 0.5f);
                    }
                } else if (z9) {
                    measuredHeight2 = (int) ((measuredWidth2 / c0425n7.L) + 0.5f);
                }
                if (measuredWidth == measuredWidth2) {
                    if (measuredWidth != measuredWidth2) {
                        iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(measuredWidth2, 1073741824);
                    }
                    if (measuredHeight != measuredHeight2) {
                        iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(measuredHeight2, 1073741824);
                    }
                    view.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
                    measuredWidth2 = view.getMeasuredWidth();
                    measuredHeight2 = view.getMeasuredHeight();
                    baseline = view.getBaseline();
                } else {
                    if (measuredWidth != measuredWidth2) {
                        iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(measuredWidth2, 1073741824);
                    }
                    if (measuredHeight != measuredHeight2) {
                        iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(measuredHeight2, 1073741824);
                    }
                    view.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
                    measuredWidth2 = view.getMeasuredWidth();
                    measuredHeight2 = view.getMeasuredHeight();
                    baseline = view.getBaseline();
                }
            }
            if (baseline != -1) {
                z10 = true;
            } else {
                z10 = false;
            }
            if (measuredWidth2 == z4.c) {
                z11 = true;
            } else {
                z11 = true;
            }
            z4.i = z11;
            if (c0118d7.X) {
                z10 = true;
            }
            if (z10) {
                z4.i = true;
            }
            z4.e = measuredWidth2;
            z4.f = measuredHeight2;
            z4.h = z10;
            z4.g = baseline;
        }
        i13 = 2;
        int iMakeMeasureSpec5 = View.MeasureSpec.makeMeasureSpec(i9, 1073741824);
        iArr[2] = i9;
        iMakeMeasureSpec = iMakeMeasureSpec5;
        z = false;
        iJ = AbstractC0535qo.j(i8);
        if (iJ == 0) {
            i = 3;
            int iMakeMeasureSpec6 = View.MeasureSpec.makeMeasureSpec(i10, 1073741824);
            iArr[3] = i10;
            iMakeMeasureSpec2 = iMakeMeasureSpec6;
            z2 = false;
        } else if (iJ == 1) {
            iMakeMeasureSpec2 = ViewGroup.getChildMeasureSpec(this.g, i11, -2);
            i = 3;
            iArr[3] = -2;
            z2 = true;
        } else if (iJ == i13) {
            iMakeMeasureSpec2 = ViewGroup.getChildMeasureSpec(this.g, i11, -2);
            if (c0425n7.k == 1) {
                z12 = true;
            } else {
                z12 = false;
            }
            iArr[3] = 0;
            if (z4.j) {
                z2 = true;
            } else {
                if (z12) {
                    z13 = false;
                } else {
                    z13 = false;
                }
                if (z12) {
                }
                iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(c0425n7.i(), 1073741824);
                z2 = false;
            }
            i = 3;
        } else if (iJ != 3) {
            i = 3;
            iMakeMeasureSpec2 = 0;
            z2 = false;
        } else {
            int i18 = this.g;
            if (z15 != null) {
                i6 = c0425n7.y.e;
            } else {
                i6 = 0;
            }
            if (z14 != null) {
                i6 += c0425n7.A.e;
            }
            iMakeMeasureSpec2 = ViewGroup.getChildMeasureSpec(i18, i11 + i6, -1);
            iArr[3] = -1;
            z2 = false;
            i = 3;
        }
        if (i7 == i) {
            z3 = true;
        } else {
            z3 = false;
        }
        if (i8 == i) {
            z5 = true;
        } else {
            z5 = false;
        }
        if (i8 != 4) {
            z6 = true;
        } else {
            z6 = true;
        }
        if (i7 != 4) {
            z7 = true;
        } else {
            z7 = true;
        }
        if (z3) {
            z8 = false;
        } else {
            z8 = false;
        }
        if (z5) {
            z9 = false;
        } else {
            z9 = false;
        }
        c0118d7 = (C0118d7) view.getLayoutParams();
        if (z4.j) {
            if (view instanceof Flow) {
                view.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
            } else {
                view.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
            }
            measuredWidth = view.getMeasuredWidth();
            measuredHeight = view.getMeasuredHeight();
            baseline = view.getBaseline();
            if (z) {
                iArr[0] = measuredWidth;
                iArr[2] = measuredHeight;
            } else {
                iArr[0] = 0;
                iArr[2] = 0;
            }
            if (z2) {
                iArr[1] = measuredHeight;
                iArr[3] = measuredWidth;
            } else {
                iArr[1] = 0;
                iArr[3] = 0;
            }
            i2 = c0425n7.m;
            if (i2 > 0) {
                measuredWidth2 = Math.max(i2, measuredWidth);
            } else {
                measuredWidth2 = measuredWidth;
            }
            i3 = c0425n7.n;
            if (i3 > 0) {
                measuredWidth2 = Math.min(i3, measuredWidth2);
            }
            i4 = c0425n7.p;
            if (i4 > 0) {
                measuredHeight2 = Math.max(i4, measuredHeight);
            } else {
                measuredHeight2 = measuredHeight;
            }
            i5 = c0425n7.q;
            if (i5 > 0) {
                measuredHeight2 = Math.min(i5, measuredHeight2);
            }
            if (!z8) {
                if (z9) {
                    measuredHeight2 = (int) ((measuredWidth2 / c0425n7.L) + 0.5f);
                }
            } else if (z9) {
                measuredHeight2 = (int) ((measuredWidth2 / c0425n7.L) + 0.5f);
            }
            if (measuredWidth == measuredWidth2) {
                if (measuredWidth != measuredWidth2) {
                    iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(measuredWidth2, 1073741824);
                }
                if (measuredHeight != measuredHeight2) {
                    iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(measuredHeight2, 1073741824);
                }
                view.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
                measuredWidth2 = view.getMeasuredWidth();
                measuredHeight2 = view.getMeasuredHeight();
                baseline = view.getBaseline();
            } else {
                if (measuredWidth != measuredWidth2) {
                    iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(measuredWidth2, 1073741824);
                }
                if (measuredHeight != measuredHeight2) {
                    iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(measuredHeight2, 1073741824);
                }
                view.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
                measuredWidth2 = view.getMeasuredWidth();
                measuredHeight2 = view.getMeasuredHeight();
                baseline = view.getBaseline();
            }
        } else {
            if (view instanceof Flow) {
                view.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
            } else {
                view.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
            }
            measuredWidth = view.getMeasuredWidth();
            measuredHeight = view.getMeasuredHeight();
            baseline = view.getBaseline();
            if (z) {
                iArr[0] = measuredWidth;
                iArr[2] = measuredHeight;
            } else {
                iArr[0] = 0;
                iArr[2] = 0;
            }
            if (z2) {
                iArr[1] = measuredHeight;
                iArr[3] = measuredWidth;
            } else {
                iArr[1] = 0;
                iArr[3] = 0;
            }
            i2 = c0425n7.m;
            if (i2 > 0) {
                measuredWidth2 = Math.max(i2, measuredWidth);
            } else {
                measuredWidth2 = measuredWidth;
            }
            i3 = c0425n7.n;
            if (i3 > 0) {
                measuredWidth2 = Math.min(i3, measuredWidth2);
            }
            i4 = c0425n7.p;
            if (i4 > 0) {
                measuredHeight2 = Math.max(i4, measuredHeight);
            } else {
                measuredHeight2 = measuredHeight;
            }
            i5 = c0425n7.q;
            if (i5 > 0) {
                measuredHeight2 = Math.min(i5, measuredHeight2);
            }
            if (!z8) {
                if (z9) {
                    measuredHeight2 = (int) ((measuredWidth2 / c0425n7.L) + 0.5f);
                }
            } else if (z9) {
                measuredHeight2 = (int) ((measuredWidth2 / c0425n7.L) + 0.5f);
            }
            if (measuredWidth == measuredWidth2) {
                if (measuredWidth != measuredWidth2) {
                    iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(measuredWidth2, 1073741824);
                }
                if (measuredHeight != measuredHeight2) {
                    iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(measuredHeight2, 1073741824);
                }
                view.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
                measuredWidth2 = view.getMeasuredWidth();
                measuredHeight2 = view.getMeasuredHeight();
                baseline = view.getBaseline();
            } else {
                if (measuredWidth != measuredWidth2) {
                    iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(measuredWidth2, 1073741824);
                }
                if (measuredHeight != measuredHeight2) {
                    iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(measuredHeight2, 1073741824);
                }
                view.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
                measuredWidth2 = view.getMeasuredWidth();
                measuredHeight2 = view.getMeasuredHeight();
                baseline = view.getBaseline();
            }
        }
        if (baseline != -1) {
            z10 = true;
        } else {
            z10 = false;
        }
        if (measuredWidth2 == z4.c) {
            z11 = true;
        } else {
            z11 = true;
        }
        z4.i = z11;
        if (c0118d7.X) {
            z10 = true;
        }
        if (z10) {
            z4.i = true;
        }
        z4.e = measuredWidth2;
        z4.f = measuredHeight2;
        z4.h = z10;
        z4.g = baseline;
    }
}
