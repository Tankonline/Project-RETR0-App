package androidx.constraintlayout.helper.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import rikka.shizuku.AbstractC0056b7;
import rikka.shizuku.C0123dc;
import rikka.shizuku.C0148e7;
import rikka.shizuku.C0153ec;
import rikka.shizuku.C0425n7;
import rikka.shizuku.C0456o7;
import rikka.shizuku.Fk;
import rikka.shizuku.Xd;
import rikka.shizuku.Z4;
import rikka.shizuku.Z6;

/* JADX INFO: loaded from: classes.dex */
public class Flow extends AbstractC0056b7 {
    public final boolean g;
    public final boolean h;
    public final C0153ec i;

    public Flow(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.a = new int[32];
        this.f = new HashMap();
        this.c = context;
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, Fk.b);
            int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = typedArrayObtainStyledAttributes.getIndex(i);
                if (index == 19) {
                    String string = typedArrayObtainStyledAttributes.getString(index);
                    this.e = string;
                    f(string);
                }
            }
        }
        int[] iArr = Fk.b;
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes2 = getContext().obtainStyledAttributes(attributeSet, iArr);
            int indexCount2 = typedArrayObtainStyledAttributes2.getIndexCount();
            for (int i2 = 0; i2 < indexCount2; i2++) {
                int index2 = typedArrayObtainStyledAttributes2.getIndex(i2);
                if (index2 == 6) {
                    this.g = true;
                } else if (index2 == 13) {
                    this.h = true;
                }
            }
        }
        C0153ec c0153ec = new C0153ec();
        c0153ec.f0 = 0;
        c0153ec.g0 = 0;
        c0153ec.h0 = 0;
        c0153ec.i0 = 0;
        c0153ec.j0 = 0;
        c0153ec.k0 = 0;
        c0153ec.l0 = false;
        c0153ec.m0 = 0;
        c0153ec.n0 = 0;
        c0153ec.o0 = new Z4();
        c0153ec.p0 = null;
        c0153ec.q0 = -1;
        c0153ec.r0 = -1;
        c0153ec.s0 = -1;
        c0153ec.t0 = -1;
        c0153ec.u0 = -1;
        c0153ec.v0 = -1;
        c0153ec.w0 = 0.5f;
        c0153ec.x0 = 0.5f;
        c0153ec.y0 = 0.5f;
        c0153ec.z0 = 0.5f;
        c0153ec.A0 = 0.5f;
        c0153ec.B0 = 0.5f;
        c0153ec.C0 = 0;
        c0153ec.D0 = 0;
        c0153ec.E0 = 2;
        c0153ec.F0 = 2;
        c0153ec.G0 = 0;
        c0153ec.H0 = -1;
        c0153ec.I0 = 0;
        c0153ec.J0 = new ArrayList();
        c0153ec.K0 = null;
        c0153ec.L0 = null;
        c0153ec.M0 = null;
        c0153ec.O0 = 0;
        this.i = c0153ec;
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes3 = getContext().obtainStyledAttributes(attributeSet, iArr);
            int indexCount3 = typedArrayObtainStyledAttributes3.getIndexCount();
            for (int i3 = 0; i3 < indexCount3; i3++) {
                int index3 = typedArrayObtainStyledAttributes3.getIndex(i3);
                if (index3 == 0) {
                    this.i.I0 = typedArrayObtainStyledAttributes3.getInt(index3, 0);
                } else if (index3 == 1) {
                    C0153ec c0153ec2 = this.i;
                    int dimensionPixelSize = typedArrayObtainStyledAttributes3.getDimensionPixelSize(index3, 0);
                    c0153ec2.f0 = dimensionPixelSize;
                    c0153ec2.g0 = dimensionPixelSize;
                    c0153ec2.h0 = dimensionPixelSize;
                    c0153ec2.i0 = dimensionPixelSize;
                } else if (index3 == 11) {
                    C0153ec c0153ec3 = this.i;
                    int dimensionPixelSize2 = typedArrayObtainStyledAttributes3.getDimensionPixelSize(index3, 0);
                    c0153ec3.h0 = dimensionPixelSize2;
                    c0153ec3.j0 = dimensionPixelSize2;
                    c0153ec3.k0 = dimensionPixelSize2;
                } else if (index3 == 12) {
                    this.i.i0 = typedArrayObtainStyledAttributes3.getDimensionPixelSize(index3, 0);
                } else if (index3 == 2) {
                    this.i.j0 = typedArrayObtainStyledAttributes3.getDimensionPixelSize(index3, 0);
                } else if (index3 == 3) {
                    this.i.f0 = typedArrayObtainStyledAttributes3.getDimensionPixelSize(index3, 0);
                } else if (index3 == 4) {
                    this.i.k0 = typedArrayObtainStyledAttributes3.getDimensionPixelSize(index3, 0);
                } else if (index3 == 5) {
                    this.i.g0 = typedArrayObtainStyledAttributes3.getDimensionPixelSize(index3, 0);
                } else if (index3 == 37) {
                    this.i.G0 = typedArrayObtainStyledAttributes3.getInt(index3, 0);
                } else if (index3 == 27) {
                    this.i.q0 = typedArrayObtainStyledAttributes3.getInt(index3, 0);
                } else if (index3 == 36) {
                    this.i.r0 = typedArrayObtainStyledAttributes3.getInt(index3, 0);
                } else if (index3 == 21) {
                    this.i.s0 = typedArrayObtainStyledAttributes3.getInt(index3, 0);
                } else if (index3 == 29) {
                    this.i.u0 = typedArrayObtainStyledAttributes3.getInt(index3, 0);
                } else if (index3 == 23) {
                    this.i.t0 = typedArrayObtainStyledAttributes3.getInt(index3, 0);
                } else if (index3 == 31) {
                    this.i.v0 = typedArrayObtainStyledAttributes3.getInt(index3, 0);
                } else if (index3 == 25) {
                    this.i.w0 = typedArrayObtainStyledAttributes3.getFloat(index3, 0.5f);
                } else if (index3 == 20) {
                    this.i.y0 = typedArrayObtainStyledAttributes3.getFloat(index3, 0.5f);
                } else if (index3 == 28) {
                    this.i.A0 = typedArrayObtainStyledAttributes3.getFloat(index3, 0.5f);
                } else if (index3 == 22) {
                    this.i.z0 = typedArrayObtainStyledAttributes3.getFloat(index3, 0.5f);
                } else if (index3 == 30) {
                    this.i.B0 = typedArrayObtainStyledAttributes3.getFloat(index3, 0.5f);
                } else if (index3 == 34) {
                    this.i.x0 = typedArrayObtainStyledAttributes3.getFloat(index3, 0.5f);
                } else if (index3 == 24) {
                    this.i.E0 = typedArrayObtainStyledAttributes3.getInt(index3, 2);
                } else if (index3 == 33) {
                    this.i.F0 = typedArrayObtainStyledAttributes3.getInt(index3, 2);
                } else if (index3 == 26) {
                    this.i.C0 = typedArrayObtainStyledAttributes3.getDimensionPixelSize(index3, 0);
                } else if (index3 == 35) {
                    this.i.D0 = typedArrayObtainStyledAttributes3.getDimensionPixelSize(index3, 0);
                } else if (index3 == 32) {
                    this.i.H0 = typedArrayObtainStyledAttributes3.getInt(index3, -1);
                }
            }
        }
        this.d = this.i;
        h();
    }

    @Override // rikka.shizuku.AbstractC0056b7
    public final void e(C0425n7 c0425n7, boolean z) {
        C0153ec c0153ec = this.i;
        int i = c0153ec.h0;
        if (i > 0 || c0153ec.i0 > 0) {
            if (z) {
                c0153ec.j0 = c0153ec.i0;
                c0153ec.k0 = i;
            } else {
                c0153ec.j0 = i;
                c0153ec.k0 = c0153ec.i0;
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:101:0x0150  */
    /* JADX WARN: Code duplicated, block: B:103:0x0154  */
    /* JADX WARN: Code duplicated, block: B:105:0x015b  */
    /* JADX WARN: Code duplicated, block: B:107:0x015f  */
    /* JADX WARN: Code duplicated, block: B:111:0x0167  */
    /* JADX WARN: Code duplicated, block: B:114:0x016f  */
    /* JADX WARN: Code duplicated, block: B:117:0x0178  */
    /* JADX WARN: Code duplicated, block: B:120:0x017f  */
    /* JADX WARN: Code duplicated, block: B:129:0x0198 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:130:0x019a  */
    /* JADX WARN: Code duplicated, block: B:131:0x01aa  */
    /* JADX WARN: Code duplicated, block: B:136:0x01be  */
    /* JADX WARN: Code duplicated, block: B:145:0x01d6  */
    /* JADX WARN: Code duplicated, block: B:148:0x01de  */
    /* JADX WARN: Code duplicated, block: B:150:0x01e2  */
    /* JADX WARN: Code duplicated, block: B:152:0x01eb  */
    /* JADX WARN: Code duplicated, block: B:155:0x01f6  */
    /* JADX WARN: Code duplicated, block: B:163:0x0210  */
    /* JADX WARN: Code duplicated, block: B:168:0x0224  */
    /* JADX WARN: Code duplicated, block: B:173:0x023d  */
    /* JADX WARN: Code duplicated, block: B:175:0x0245 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:176:0x0247  */
    /* JADX WARN: Code duplicated, block: B:181:0x0258  */
    /* JADX WARN: Code duplicated, block: B:183:0x0262 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:184:0x0264  */
    /* JADX WARN: Code duplicated, block: B:189:0x027b  */
    /* JADX WARN: Code duplicated, block: B:192:0x0282  */
    /* JADX WARN: Code duplicated, block: B:193:0x0285  */
    /* JADX WARN: Code duplicated, block: B:198:0x02a4 A[PHI: r17 r20 r21
  0x02a4: PHI (r17v9 int) = (r17v3 int), (r17v17 int) binds: [B:200:0x02b8, B:197:0x0295] A[DONT_GENERATE, DONT_INLINE]
  0x02a4: PHI (r20v8 int) = (r20v1 int), (r20v9 int) binds: [B:200:0x02b8, B:197:0x0295] A[DONT_GENERATE, DONT_INLINE]
  0x02a4: PHI (r21v8 int) = (r21v1 int), (r21v9 int) binds: [B:200:0x02b8, B:197:0x0295] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:199:0x02b0  */
    /* JADX WARN: Code duplicated, block: B:202:0x02bb  */
    /* JADX WARN: Code duplicated, block: B:204:0x02e1  */
    /* JADX WARN: Code duplicated, block: B:206:0x02e7  */
    /* JADX WARN: Code duplicated, block: B:208:0x02f6  */
    /* JADX WARN: Code duplicated, block: B:225:0x0319  */
    /* JADX WARN: Code duplicated, block: B:227:0x0339  */
    /* JADX WARN: Code duplicated, block: B:229:0x0341  */
    /* JADX WARN: Code duplicated, block: B:231:0x0350  */
    /* JADX WARN: Code duplicated, block: B:233:0x0356  */
    /* JADX WARN: Code duplicated, block: B:235:0x0365  */
    /* JADX WARN: Code duplicated, block: B:252:0x0389  */
    /* JADX WARN: Code duplicated, block: B:254:0x03a2  */
    /* JADX WARN: Code duplicated, block: B:256:0x03a6  */
    /* JADX WARN: Code duplicated, block: B:265:0x03d4  */
    /* JADX WARN: Code duplicated, block: B:270:0x03dd  */
    /* JADX WARN: Code duplicated, block: B:272:0x03e5  */
    /* JADX WARN: Code duplicated, block: B:273:0x03ef  */
    /* JADX WARN: Code duplicated, block: B:277:0x0419  */
    /* JADX WARN: Code duplicated, block: B:279:0x0421  */
    /* JADX WARN: Code duplicated, block: B:281:0x0425  */
    /* JADX WARN: Code duplicated, block: B:282:0x0436  */
    /* JADX WARN: Code duplicated, block: B:285:0x045a  */
    /* JADX WARN: Code duplicated, block: B:287:0x0464  */
    /* JADX WARN: Code duplicated, block: B:289:0x046a  */
    /* JADX WARN: Code duplicated, block: B:290:0x047b  */
    /* JADX WARN: Code duplicated, block: B:293:0x049d  */
    /* JADX WARN: Code duplicated, block: B:297:0x04b4  */
    /* JADX WARN: Code duplicated, block: B:300:0x04c6  */
    /* JADX WARN: Code duplicated, block: B:302:0x04cc  */
    /* JADX WARN: Code duplicated, block: B:303:0x04dd  */
    /* JADX WARN: Code duplicated, block: B:306:0x0520 A[LOOP:14: B:305:0x051e->B:306:0x0520, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:311:0x054b A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:312:0x054d  */
    /* JADX WARN: Code duplicated, block: B:313:0x0552 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:314:0x0554  */
    /* JADX WARN: Code duplicated, block: B:315:0x0556  */
    /* JADX WARN: Code duplicated, block: B:318:0x055a A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:319:0x055c  */
    /* JADX WARN: Code duplicated, block: B:320:0x0561 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:321:0x0563  */
    /* JADX WARN: Code duplicated, block: B:322:0x0565  */
    /* JADX WARN: Code duplicated, block: B:325:0x0574  */
    /* JADX WARN: Code duplicated, block: B:326:0x0577  */
    /* JADX WARN: Code duplicated, block: B:337:0x00d4 A[EDGE_INSN: B:337:0x00d4->B:63:0x00d4 BREAK  A[LOOP:1: B:57:0x00c0->B:62:0x00d0], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:339:0x00d0 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:342:0x00f3 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:344:0x014b A[EDGE_INSN: B:344:0x014b->B:99:0x014b BREAK  A[LOOP:3: B:87:0x012f->B:98:0x0148], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:346:0x0148 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:359:0x0250 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:362:0x026d A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:363:0x0175 A[EDGE_INSN: B:363:0x0175->B:116:0x0175 BREAK  A[LOOP:9: B:104:0x0159->B:115:0x0172], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:366:0x0172 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:41:0x009a  */
    /* JADX WARN: Code duplicated, block: B:44:0x00a0  */
    /* JADX WARN: Code duplicated, block: B:46:0x00a4  */
    /* JADX WARN: Code duplicated, block: B:49:0x00aa  */
    /* JADX WARN: Code duplicated, block: B:50:0x00ad  */
    /* JADX WARN: Code duplicated, block: B:52:0x00b1  */
    /* JADX WARN: Code duplicated, block: B:55:0x00b7  */
    /* JADX WARN: Code duplicated, block: B:59:0x00c6  */
    /* JADX WARN: Code duplicated, block: B:61:0x00ce  */
    /* JADX WARN: Code duplicated, block: B:64:0x00d6  */
    /* JADX WARN: Code duplicated, block: B:67:0x00e1  */
    /* JADX WARN: Code duplicated, block: B:69:0x00ef  */
    /* JADX WARN: Code duplicated, block: B:73:0x0101  */
    /* JADX WARN: Code duplicated, block: B:76:0x010e  */
    /* JADX WARN: Code duplicated, block: B:78:0x0111  */
    /* JADX WARN: Code duplicated, block: B:80:0x0114  */
    /* JADX WARN: Code duplicated, block: B:82:0x0122  */
    /* JADX WARN: Code duplicated, block: B:84:0x0126  */
    /* JADX WARN: Code duplicated, block: B:86:0x012a  */
    /* JADX WARN: Code duplicated, block: B:88:0x0131  */
    /* JADX WARN: Code duplicated, block: B:90:0x0135  */
    /* JADX WARN: Code duplicated, block: B:94:0x013d  */
    /* JADX WARN: Code duplicated, block: B:97:0x0145  */
    /* JADX WARN: Multi-variable type inference failed */
    public final void i(C0153ec c0153ec, int i, int i2) {
        int i3;
        int i4;
        int i5;
        int i6;
        int[] iArr;
        int i7;
        int i8;
        C0425n7[] c0425n7Arr;
        int i9;
        int i10;
        char c;
        int i11;
        int i12;
        C0425n7[] c0425n7Arr2;
        int i13;
        int i14;
        ArrayList arrayList;
        int i15;
        int i16;
        int i17;
        int[] iArr2;
        C0425n7[] c0425n7Arr3;
        int i18;
        int i19;
        C0123dc c0123dc;
        int i20;
        char c2;
        int i21;
        int i22;
        boolean z;
        int i23;
        int i24;
        int i25;
        int i26;
        C0123dc c0123dc2;
        C0425n7[] c0425n7Arr4;
        int i27;
        int i28;
        int i29;
        int i30;
        C0425n7 c0425n7;
        int iC;
        boolean z2;
        int i31;
        int size;
        int[] iArr3;
        boolean z3;
        Z6 z6;
        Z6 z7;
        Z6 z8;
        int i32;
        Z6 z9;
        int i33;
        int i34;
        Z6 z10;
        Z6 z11;
        int i35;
        int i36;
        int i37;
        int i38;
        C0123dc c0123dc3;
        int i39;
        int iD;
        int iC2;
        int i40;
        C0123dc c0123dc4;
        int i41;
        int i42;
        C0425n7 c0425n8;
        int iD2;
        int i43;
        int i44;
        boolean z4;
        C0425n7 c0425n9;
        int i45;
        int i46;
        int i47;
        int i48;
        int i49;
        int iCeil;
        int iCeil2;
        int i50;
        int i51;
        C0425n7 c0425n10;
        int iC3;
        boolean z5;
        C0425n7[] c0425n7Arr5;
        C0425n7[] c0425n7Arr6;
        int i52;
        int i53;
        int i54;
        int iD3;
        int i55;
        int iC4;
        int i56;
        C0425n7 c0425n11;
        C0425n7 c0425n12;
        int i57;
        int i58;
        int i59;
        int i60;
        C0425n7 c0425n13;
        int i61;
        C0425n7 c0425n14;
        C0425n7 c0425n15;
        int i62;
        int i63;
        int i64;
        C0425n7 c0425n16;
        int iD4;
        C0425n7[] c0425n7Arr7;
        int i65;
        C0425n7 c0425n17;
        C0425n7[] c0425n7Arr8;
        int mode = View.MeasureSpec.getMode(i);
        int size2 = View.MeasureSpec.getSize(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int size3 = View.MeasureSpec.getSize(i2);
        if (c0153ec == null) {
            setMeasuredDimension(0, 0);
            return;
        }
        if (c0153ec.e0 <= 0) {
            i3 = c0153ec.j0;
            i4 = c0153ec.k0;
            i5 = c0153ec.f0;
            i6 = c0153ec.g0;
            iArr = new int[2];
            i7 = (size2 - i3) - i4;
            i8 = c0153ec.I0;
            if (i8 == 1) {
                i7 = (size3 - i5) - i6;
            }
            if (i8 == 0) {
                if (c0153ec.q0 == -1) {
                    c0153ec.q0 = 0;
                }
                if (c0153ec.r0 == -1) {
                    c0153ec.r0 = 0;
                }
            } else {
                if (c0153ec.q0 == -1) {
                    c0153ec.q0 = 0;
                }
                if (c0153ec.r0 == -1) {
                    c0153ec.r0 = 0;
                }
            }
            c0425n7Arr = c0153ec.d0;
            i9 = 0;
            i10 = 0;
            c = 0;
            while (true) {
                i11 = c0153ec.e0;
                if (i9 < i11) {
                    break;
                }
                if (c0153ec.d0[i9].V == 8) {
                    i10++;
                }
                i9++;
            }
            if (i10 > 0) {
                c0425n7Arr7 = new C0425n7[i11 - i10];
                i65 = 0;
                i12 = 0;
                while (i65 < c0153ec.e0) {
                    c0425n17 = c0153ec.d0[i65];
                    int i66 = i3;
                    c0425n7Arr8 = c0425n7Arr7;
                    if (c0425n17.V != 8) {
                        c0425n7Arr8[i12] = c0425n17;
                        i12++;
                    }
                    i65++;
                    i3 = i66;
                    c0425n7Arr7 = c0425n7Arr8;
                }
                c0425n7Arr2 = c0425n7Arr7;
            } else {
                i12 = i11;
                c0425n7Arr2 = c0425n7Arr;
            }
            i13 = i3;
            c0153ec.N0 = c0425n7Arr2;
            c0153ec.O0 = i12;
            i14 = c0153ec.G0;
            arrayList = c0153ec.J0;
            if (i14 != 0) {
                i15 = i4;
                i16 = i5;
                i17 = i6;
                iArr2 = iArr;
                c0425n7Arr3 = c0425n7Arr2;
                i18 = i13;
                i19 = c0153ec.I0;
                if (i12 == 0) {
                    if (arrayList.size() == 0) {
                        c0123dc = new C0123dc(c0153ec, i19, c0153ec.x, c0153ec.y, c0153ec.z, c0153ec.A, i7);
                        arrayList.add(c0123dc);
                    } else {
                        c0123dc = (C0123dc) arrayList.get(0);
                        c0123dc.c = 0;
                        c0123dc.b = null;
                        c0123dc.l = 0;
                        c0123dc.m = 0;
                        c0123dc.n = 0;
                        c0123dc.o = 0;
                        c0123dc.p = 0;
                        c0123dc.f(i19, c0153ec.x, c0153ec.y, c0153ec.z, c0153ec.A, c0153ec.j0, c0153ec.f0, c0153ec.k0, c0153ec.g0, i7);
                    }
                    for (i20 = 0; i20 < i12; i20++) {
                        c0123dc.a(c0425n7Arr3[i20]);
                    }
                    c = 0;
                    iArr2[0] = c0123dc.d();
                    c2 = 1;
                    iArr2[1] = c0123dc.c();
                }
                i21 = iArr2[c] + i18 + i15;
                i22 = iArr2[c2] + i16 + i17;
                if (mode != 1073741824) {
                    if (mode == Integer.MIN_VALUE) {
                        size2 = Math.min(i21, size2);
                    } else if (mode == 0) {
                        size2 = i21;
                    } else {
                        size2 = 0;
                    }
                }
                if (mode2 != 1073741824) {
                    if (mode2 == Integer.MIN_VALUE) {
                        size3 = Math.min(i22, size3);
                    } else if (mode2 == 0) {
                        size3 = i22;
                    } else {
                        size3 = 0;
                    }
                }
                c0153ec.m0 = size2;
                c0153ec.n0 = size3;
                c0153ec.y(size2);
                c0153ec.v(size3);
                if (c0153ec.e0 > 0) {
                    z = c2;
                } else {
                    z = 0;
                }
                c0153ec.l0 = z;
            } else if (i14 != 1) {
                i23 = i4;
                i24 = i5;
                i25 = i6;
                i26 = c0153ec.I0;
                if (i12 == 0) {
                    iArr2 = iArr;
                    i15 = i23;
                    i18 = i13;
                    i16 = i24;
                    i17 = i25;
                } else {
                    arrayList.clear();
                    c0425n7Arr4 = c0425n7Arr2;
                    i15 = i23;
                    i18 = i13;
                    iArr2 = iArr;
                    i16 = i24;
                    i17 = i25;
                    c0123dc2 = new C0123dc(c0153ec, i26, c0153ec.x, c0153ec.y, c0153ec.z, c0153ec.A, i7);
                    arrayList.add(c0123dc2);
                    if (i26 == 0) {
                        i41 = 0;
                        i30 = 0;
                        i42 = 0;
                        while (i41 < i12) {
                            c0425n8 = c0425n7Arr4[i41];
                            iD2 = c0153ec.D(c0425n8, i7);
                            i43 = i26;
                            if (c0425n8.c0[0] == 3) {
                                i30++;
                            }
                            i44 = i30;
                            z4 = (i42 != i7 || (c0153ec.C0 + i42) + iD2 > i7) && c0123dc2.b != null;
                            if (!z4 && i41 > 0 && (i47 = c0153ec.H0) > 0 && i41 % i47 == 0) {
                                z4 = true;
                            }
                            if (z4) {
                                c0425n9 = c0425n8;
                                i45 = i44;
                                i46 = i41;
                                i26 = i43;
                                c0123dc2 = new C0123dc(c0153ec, i26, c0153ec.x, c0153ec.y, c0153ec.z, c0153ec.A, i7);
                                c0123dc2.n = i46;
                                arrayList.add(c0123dc2);
                            } else {
                                i26 = i43;
                                c0425n9 = c0425n8;
                                i45 = i44;
                                i46 = i41;
                                if (i46 > 0) {
                                    i42 = c0153ec.C0 + iD2 + i42;
                                }
                                c0123dc2.a(c0425n9);
                                i41 = i46 + 1;
                                i30 = i45;
                            }
                            i42 = iD2;
                            c0123dc2.a(c0425n9);
                            i41 = i46 + 1;
                            i30 = i45;
                        }
                    } else {
                        i27 = 0;
                        i28 = 0;
                        i29 = 0;
                        while (i27 < i12) {
                            c0425n7 = c0425n7Arr4[i27];
                            iC = c0153ec.C(c0425n7, i7);
                            if (c0425n7.c0[1] == 3) {
                                i28++;
                            }
                            int i67 = i28;
                            z2 = (i29 != i7 || (c0153ec.D0 + i29) + iC > i7) && c0123dc2.b != null;
                            if (!z2 && i27 > 0 && (i31 = c0153ec.H0) > 0 && i27 % i31 == 0) {
                                z2 = true;
                            }
                            if (z2) {
                                c0123dc2 = new C0123dc(c0153ec, i26, c0153ec.x, c0153ec.y, c0153ec.z, c0153ec.A, i7);
                                c0123dc2.n = i27;
                                arrayList.add(c0123dc2);
                            } else {
                                if (i27 > 0) {
                                    i29 = c0153ec.D0 + iC + i29;
                                }
                                c0123dc2.a(c0425n7);
                                i27++;
                                i28 = i67;
                            }
                            i29 = iC;
                            c0123dc2.a(c0425n7);
                            i27++;
                            i28 = i67;
                        }
                        i30 = i28;
                    }
                    size = arrayList.size();
                    int i68 = c0153ec.j0;
                    int i69 = c0153ec.f0;
                    int i70 = c0153ec.k0;
                    int i71 = c0153ec.g0;
                    iArr3 = c0153ec.c0;
                    if (iArr3[0] != 2 || iArr3[1] == 2) {
                        z3 = true;
                    } else {
                        z3 = false;
                    }
                    if (i30 > 0 && z3) {
                        for (i40 = 0; i40 < size; i40++) {
                            c0123dc4 = (C0123dc) arrayList.get(i40);
                            if (i26 == 0) {
                                c0123dc4.e(i7 - c0123dc4.d());
                            } else {
                                c0123dc4.e(i7 - c0123dc4.c());
                            }
                        }
                    }
                    z6 = c0153ec.A;
                    z7 = c0153ec.z;
                    z8 = z6;
                    i32 = i69;
                    z9 = z7;
                    i33 = i70;
                    i34 = i71;
                    z10 = c0153ec.x;
                    z11 = c0153ec.y;
                    i35 = i68;
                    i36 = 0;
                    i37 = 0;
                    i38 = 0;
                    while (i36 < size) {
                        c0123dc3 = (C0123dc) arrayList.get(i36);
                        if (i26 == 0) {
                            if (i36 < size - 1) {
                                z8 = ((C0123dc) arrayList.get(i36 + 1)).b.y;
                                i34 = 0;
                            } else {
                                z8 = z6;
                                i34 = c0153ec.g0;
                            }
                            Z6 z12 = c0123dc3.b.A;
                            c0123dc3.f(i26, z10, z11, z9, z8, i35, i32, i33, i34, i7);
                            i39 = size;
                            int iMax = Math.max(i37, c0123dc3.d());
                            iC2 = c0123dc3.c() + i38;
                            if (i36 > 0) {
                                iC2 += c0153ec.D0;
                            }
                            i38 = iC2;
                            z11 = z12;
                            i32 = 0;
                            i37 = iMax;
                        } else {
                            i39 = size;
                            if (i36 < i39 - 1) {
                                z9 = ((C0123dc) arrayList.get(i36 + 1)).b.x;
                                i33 = 0;
                            } else {
                                i33 = c0153ec.k0;
                                z9 = z7;
                            }
                            Z6 z13 = c0123dc3.b.z;
                            c0123dc3.f(i26, z10, z11, z9, z8, i35, i32, i33, i34, i7);
                            iD = c0123dc3.d() + i37;
                            int iMax2 = Math.max(i38, c0123dc3.c());
                            if (i36 > 0) {
                                iD += c0153ec.C0;
                            }
                            z10 = z13;
                            i38 = iMax2;
                            i37 = iD;
                            i35 = 0;
                        }
                        i36++;
                        size = i39;
                    }
                    iArr2[0] = i37;
                    iArr2[1] = i38;
                }
            } else if (i14 != 2) {
                i15 = i4;
                i16 = i5;
                i17 = i6;
                iArr2 = iArr;
                i18 = i13;
            } else {
                i48 = c0153ec.I0;
                if (i48 == 0) {
                    iCeil2 = c0153ec.H0;
                    if (iCeil2 <= 0) {
                        i62 = 0;
                        i63 = 0;
                        i64 = 0;
                        while (i62 < i12) {
                            int i72 = i62;
                            if (i62 > 0) {
                                i63 += c0153ec.C0;
                            }
                            c0425n16 = c0425n7Arr2[i72];
                            if (c0425n16 != null) {
                                iD4 = c0153ec.D(c0425n16, i7) + i63;
                                if (iD4 > i7) {
                                    break;
                                }
                                i64++;
                                i63 = iD4;
                            }
                            i62 = i72 + 1;
                        }
                        iCeil2 = i64;
                    }
                    iCeil = 0;
                } else {
                    i49 = c0153ec.H0;
                    if (i49 <= 0) {
                        i50 = 0;
                        i51 = 0;
                        iCeil = 0;
                        while (i50 < i12) {
                            int i73 = i50;
                            if (i50 > 0) {
                                i51 += c0153ec.D0;
                            }
                            c0425n10 = c0425n7Arr2[i73];
                            if (c0425n10 != null) {
                                iC3 = c0153ec.C(c0425n10, i7) + i51;
                                if (iC3 > i7) {
                                    break;
                                }
                                iCeil++;
                                i51 = iC3;
                            }
                            i50 = i73 + 1;
                        }
                    } else {
                        iCeil = i49;
                    }
                    iCeil2 = 0;
                }
                if (c0153ec.M0 == null) {
                    c0153ec.M0 = new int[2];
                }
                z5 = (iCeil != 0 && i48 == 1) || (iCeil2 == 0 && i48 == 0);
                while (!z5) {
                    if (i48 == 0) {
                        iCeil = (int) Math.ceil(i12 / iCeil2);
                        iCeil2 = iCeil2;
                    } else {
                        iCeil2 = (int) Math.ceil(i12 / iCeil);
                    }
                    c0425n7Arr5 = c0153ec.L0;
                    int i74 = i5;
                    if (c0425n7Arr5 != null || c0425n7Arr5.length < iCeil2) {
                        c0153ec.L0 = new C0425n7[iCeil2];
                    } else {
                        Arrays.fill(c0425n7Arr5, (Object) null);
                    }
                    c0425n7Arr6 = c0153ec.K0;
                    if (c0425n7Arr6 != null || c0425n7Arr6.length < iCeil) {
                        c0153ec.K0 = new C0425n7[iCeil];
                    } else {
                        Arrays.fill(c0425n7Arr6, (Object) null);
                    }
                    i52 = 0;
                    while (i52 < iCeil2) {
                        i57 = 0;
                        while (i57 < iCeil) {
                            i58 = (i57 * iCeil2) + i52;
                            i59 = i52;
                            if (i48 == 1) {
                                i58 = (i59 * iCeil) + i57;
                            }
                            int i75 = i48;
                            i60 = i58;
                            if (i60 >= c0425n7Arr2.length && (c0425n13 = c0425n7Arr2[i60]) != null) {
                                int iD5 = c0153ec.D(c0425n13, i7);
                                i61 = i57;
                                c0425n14 = c0153ec.L0[i59];
                                if (c0425n14 != null || c0425n14.l() < iD5) {
                                    c0153ec.L0[i59] = c0425n13;
                                }
                                int iC5 = c0153ec.C(c0425n13, i7);
                                c0425n15 = c0153ec.K0[i61];
                                if (c0425n15 != null || c0425n15.i() < iC5) {
                                    c0153ec.K0[i61] = c0425n13;
                                }
                            } else {
                                i61 = i57;
                            }
                            i57 = i61 + 1;
                            i52 = i59;
                            i48 = i75;
                        }
                        i52++;
                    }
                    i53 = i48;
                    i54 = 0;
                    iD3 = 0;
                    while (i54 < iCeil2) {
                        c0425n12 = c0153ec.L0[i54];
                        int i76 = i54;
                        if (c0425n12 == null) {
                            if (i54 > 0) {
                                iD3 += c0153ec.C0;
                            }
                            iD3 = c0153ec.D(c0425n12, i7) + iD3;
                        }
                        i54 = i76 + 1;
                    }
                    i55 = 0;
                    iC4 = 0;
                    while (i55 < iCeil) {
                        i56 = i55;
                        c0425n11 = c0153ec.K0[i56];
                        int i77 = i6;
                        if (c0425n11 == null) {
                            if (i56 > 0) {
                                iC4 += c0153ec.D0;
                            }
                            iC4 = c0153ec.C(c0425n11, i7) + iC4;
                        }
                        i55 = i56 + 1;
                        i6 = i77;
                    }
                    int i78 = i6;
                    iArr[0] = iD3;
                    iArr[1] = iC4;
                    if (i53 == 0) {
                        if (iD3 > i7 || iCeil2 <= 1) {
                            z5 = true;
                        } else {
                            iCeil2--;
                        }
                    } else if (iC4 > i7 || iCeil <= 1) {
                        z5 = true;
                    } else {
                        iCeil--;
                    }
                    i4 = i4;
                    i5 = i74;
                    i6 = i78;
                    i48 = i53;
                }
                int i79 = iCeil2;
                i23 = i4;
                i24 = i5;
                i25 = i6;
                int[] iArr4 = c0153ec.M0;
                iArr4[0] = i79;
                iArr4[1] = iCeil;
                iArr2 = iArr;
                i15 = i23;
                i18 = i13;
                i16 = i24;
                i17 = i25;
            }
            c2 = 1;
            i21 = iArr2[c] + i18 + i15;
            i22 = iArr2[c2] + i16 + i17;
            if (mode != 1073741824) {
                if (mode == Integer.MIN_VALUE) {
                    size2 = Math.min(i21, size2);
                } else if (mode == 0) {
                    size2 = i21;
                } else {
                    size2 = 0;
                }
            }
            if (mode2 != 1073741824) {
                if (mode2 == Integer.MIN_VALUE) {
                    size3 = Math.min(i22, size3);
                } else if (mode2 == 0) {
                    size3 = i22;
                } else {
                    size3 = 0;
                }
            }
            c0153ec.m0 = size2;
            c0153ec.n0 = size3;
            c0153ec.y(size2);
            c0153ec.v(size3);
            if (c0153ec.e0 > 0) {
                z = c2;
            } else {
                z = 0;
            }
            c0153ec.l0 = z;
        } else {
            C0425n7 c0425n18 = c0153ec.I;
            C0148e7 c0148e7 = c0425n18 != null ? ((C0456o7) c0425n18).g0 : null;
            if (c0148e7 == null) {
                c0153ec.m0 = 0;
                c0153ec.n0 = 0;
                c0153ec.l0 = false;
            } else {
                for (int i80 = 0; i80 < c0153ec.e0; i80++) {
                    C0425n7 c0425n19 = c0153ec.d0[i80];
                    if (c0425n19 != null && !(c0425n19 instanceof Xd)) {
                        int iH = c0425n19.h(0);
                        int iH2 = c0425n19.h(1);
                        if (iH != 3 || c0425n19.j == 1 || iH2 != 3 || c0425n19.k == 1) {
                            if (iH == 3) {
                                iH = 2;
                            }
                            if (iH2 == 3) {
                                iH2 = 2;
                            }
                            Z4 z14 = c0153ec.o0;
                            z14.a = iH;
                            z14.b = iH2;
                            z14.c = c0425n19.l();
                            z14.d = c0425n19.i();
                            c0148e7.a(c0425n19, z14);
                            c0425n19.y(z14.e);
                            c0425n19.v(z14.f);
                            int i81 = z14.g;
                            c0425n19.P = i81;
                            c0425n19.w = i81 > 0;
                        }
                    }
                }
                i3 = c0153ec.j0;
                i4 = c0153ec.k0;
                i5 = c0153ec.f0;
                i6 = c0153ec.g0;
                iArr = new int[2];
                i7 = (size2 - i3) - i4;
                i8 = c0153ec.I0;
                if (i8 == 1) {
                    i7 = (size3 - i5) - i6;
                }
                if (i8 == 0) {
                    if (c0153ec.q0 == -1) {
                        c0153ec.q0 = 0;
                    }
                    if (c0153ec.r0 == -1) {
                        c0153ec.r0 = 0;
                    }
                } else {
                    if (c0153ec.q0 == -1) {
                        c0153ec.q0 = 0;
                    }
                    if (c0153ec.r0 == -1) {
                        c0153ec.r0 = 0;
                    }
                }
                c0425n7Arr = c0153ec.d0;
                i9 = 0;
                i10 = 0;
                c = 0;
                while (true) {
                    i11 = c0153ec.e0;
                    if (i9 < i11) {
                        break;
                        break;
                    } else {
                        if (c0153ec.d0[i9].V == 8) {
                            i10++;
                        }
                        i9++;
                    }
                }
                if (i10 > 0) {
                    c0425n7Arr7 = new C0425n7[i11 - i10];
                    i65 = 0;
                    i12 = 0;
                    while (i65 < c0153ec.e0) {
                        c0425n17 = c0153ec.d0[i65];
                        int i610 = i3;
                        c0425n7Arr8 = c0425n7Arr7;
                        if (c0425n17.V != 8) {
                            c0425n7Arr8[i12] = c0425n17;
                            i12++;
                        }
                        i65++;
                        i3 = i610;
                        c0425n7Arr7 = c0425n7Arr8;
                    }
                    c0425n7Arr2 = c0425n7Arr7;
                } else {
                    i12 = i11;
                    c0425n7Arr2 = c0425n7Arr;
                }
                i13 = i3;
                c0153ec.N0 = c0425n7Arr2;
                c0153ec.O0 = i12;
                i14 = c0153ec.G0;
                arrayList = c0153ec.J0;
                if (i14 != 0) {
                    i15 = i4;
                    i16 = i5;
                    i17 = i6;
                    iArr2 = iArr;
                    c0425n7Arr3 = c0425n7Arr2;
                    i18 = i13;
                    i19 = c0153ec.I0;
                    if (i12 == 0) {
                        if (arrayList.size() == 0) {
                            c0123dc = new C0123dc(c0153ec, i19, c0153ec.x, c0153ec.y, c0153ec.z, c0153ec.A, i7);
                            arrayList.add(c0123dc);
                        } else {
                            c0123dc = (C0123dc) arrayList.get(0);
                            c0123dc.c = 0;
                            c0123dc.b = null;
                            c0123dc.l = 0;
                            c0123dc.m = 0;
                            c0123dc.n = 0;
                            c0123dc.o = 0;
                            c0123dc.p = 0;
                            c0123dc.f(i19, c0153ec.x, c0153ec.y, c0153ec.z, c0153ec.A, c0153ec.j0, c0153ec.f0, c0153ec.k0, c0153ec.g0, i7);
                        }
                        while (i20 < i12) {
                            c0123dc.a(c0425n7Arr3[i20]);
                        }
                        c = 0;
                        iArr2[0] = c0123dc.d();
                        c2 = 1;
                        iArr2[1] = c0123dc.c();
                    }
                    i21 = iArr2[c] + i18 + i15;
                    i22 = iArr2[c2] + i16 + i17;
                    if (mode != 1073741824) {
                        if (mode == Integer.MIN_VALUE) {
                            size2 = Math.min(i21, size2);
                        } else if (mode == 0) {
                            size2 = i21;
                        } else {
                            size2 = 0;
                        }
                    }
                    if (mode2 != 1073741824) {
                        if (mode2 == Integer.MIN_VALUE) {
                            size3 = Math.min(i22, size3);
                        } else if (mode2 == 0) {
                            size3 = i22;
                        } else {
                            size3 = 0;
                        }
                    }
                    c0153ec.m0 = size2;
                    c0153ec.n0 = size3;
                    c0153ec.y(size2);
                    c0153ec.v(size3);
                    if (c0153ec.e0 > 0) {
                        z = c2;
                    } else {
                        z = 0;
                    }
                    c0153ec.l0 = z;
                } else if (i14 != 1) {
                    i23 = i4;
                    i24 = i5;
                    i25 = i6;
                    i26 = c0153ec.I0;
                    if (i12 == 0) {
                        iArr2 = iArr;
                        i15 = i23;
                        i18 = i13;
                        i16 = i24;
                        i17 = i25;
                    } else {
                        arrayList.clear();
                        c0425n7Arr4 = c0425n7Arr2;
                        i15 = i23;
                        i18 = i13;
                        iArr2 = iArr;
                        i16 = i24;
                        i17 = i25;
                        c0123dc2 = new C0123dc(c0153ec, i26, c0153ec.x, c0153ec.y, c0153ec.z, c0153ec.A, i7);
                        arrayList.add(c0123dc2);
                        if (i26 == 0) {
                            i41 = 0;
                            i30 = 0;
                            i42 = 0;
                            while (i41 < i12) {
                                c0425n8 = c0425n7Arr4[i41];
                                iD2 = c0153ec.D(c0425n8, i7);
                                i43 = i26;
                                if (c0425n8.c0[0] == 3) {
                                    i30++;
                                }
                                i44 = i30;
                                if (i42 != i7) {
                                }
                                if (!z4) {
                                    z4 = true;
                                }
                                if (z4) {
                                    c0425n9 = c0425n8;
                                    i45 = i44;
                                    i46 = i41;
                                    i26 = i43;
                                    c0123dc2 = new C0123dc(c0153ec, i26, c0153ec.x, c0153ec.y, c0153ec.z, c0153ec.A, i7);
                                    c0123dc2.n = i46;
                                    arrayList.add(c0123dc2);
                                } else {
                                    i26 = i43;
                                    c0425n9 = c0425n8;
                                    i45 = i44;
                                    i46 = i41;
                                    if (i46 > 0) {
                                        i42 = c0153ec.C0 + iD2 + i42;
                                    }
                                    c0123dc2.a(c0425n9);
                                    i41 = i46 + 1;
                                    i30 = i45;
                                }
                                i42 = iD2;
                                c0123dc2.a(c0425n9);
                                i41 = i46 + 1;
                                i30 = i45;
                            }
                        } else {
                            i27 = 0;
                            i28 = 0;
                            i29 = 0;
                            while (i27 < i12) {
                                c0425n7 = c0425n7Arr4[i27];
                                iC = c0153ec.C(c0425n7, i7);
                                if (c0425n7.c0[1] == 3) {
                                    i28++;
                                }
                                int i611 = i28;
                                if (i29 != i7) {
                                }
                                if (!z2) {
                                    z2 = true;
                                }
                                if (z2) {
                                    c0123dc2 = new C0123dc(c0153ec, i26, c0153ec.x, c0153ec.y, c0153ec.z, c0153ec.A, i7);
                                    c0123dc2.n = i27;
                                    arrayList.add(c0123dc2);
                                } else {
                                    if (i27 > 0) {
                                        i29 = c0153ec.D0 + iC + i29;
                                    }
                                    c0123dc2.a(c0425n7);
                                    i27++;
                                    i28 = i611;
                                }
                                i29 = iC;
                                c0123dc2.a(c0425n7);
                                i27++;
                                i28 = i611;
                            }
                            i30 = i28;
                        }
                        size = arrayList.size();
                        int i612 = c0153ec.j0;
                        int i613 = c0153ec.f0;
                        int i710 = c0153ec.k0;
                        int i711 = c0153ec.g0;
                        iArr3 = c0153ec.c0;
                        if (iArr3[0] != 2) {
                            z3 = true;
                        } else {
                            z3 = true;
                        }
                        if (i30 > 0) {
                            while (i40 < size) {
                                c0123dc4 = (C0123dc) arrayList.get(i40);
                                if (i26 == 0) {
                                    c0123dc4.e(i7 - c0123dc4.d());
                                } else {
                                    c0123dc4.e(i7 - c0123dc4.c());
                                }
                            }
                        }
                        z6 = c0153ec.A;
                        z7 = c0153ec.z;
                        z8 = z6;
                        i32 = i613;
                        z9 = z7;
                        i33 = i710;
                        i34 = i711;
                        z10 = c0153ec.x;
                        z11 = c0153ec.y;
                        i35 = i612;
                        i36 = 0;
                        i37 = 0;
                        i38 = 0;
                        while (i36 < size) {
                            c0123dc3 = (C0123dc) arrayList.get(i36);
                            if (i26 == 0) {
                                if (i36 < size - 1) {
                                    z8 = ((C0123dc) arrayList.get(i36 + 1)).b.y;
                                    i34 = 0;
                                } else {
                                    z8 = z6;
                                    i34 = c0153ec.g0;
                                }
                                Z6 z15 = c0123dc3.b.A;
                                c0123dc3.f(i26, z10, z11, z9, z8, i35, i32, i33, i34, i7);
                                i39 = size;
                                int iMax3 = Math.max(i37, c0123dc3.d());
                                iC2 = c0123dc3.c() + i38;
                                if (i36 > 0) {
                                    iC2 += c0153ec.D0;
                                }
                                i38 = iC2;
                                z11 = z15;
                                i32 = 0;
                                i37 = iMax3;
                            } else {
                                i39 = size;
                                if (i36 < i39 - 1) {
                                    z9 = ((C0123dc) arrayList.get(i36 + 1)).b.x;
                                    i33 = 0;
                                } else {
                                    i33 = c0153ec.k0;
                                    z9 = z7;
                                }
                                Z6 z16 = c0123dc3.b.z;
                                c0123dc3.f(i26, z10, z11, z9, z8, i35, i32, i33, i34, i7);
                                iD = c0123dc3.d() + i37;
                                int iMax4 = Math.max(i38, c0123dc3.c());
                                if (i36 > 0) {
                                    iD += c0153ec.C0;
                                }
                                z10 = z16;
                                i38 = iMax4;
                                i37 = iD;
                                i35 = 0;
                            }
                            i36++;
                            size = i39;
                        }
                        iArr2[0] = i37;
                        iArr2[1] = i38;
                    }
                } else if (i14 != 2) {
                    i15 = i4;
                    i16 = i5;
                    i17 = i6;
                    iArr2 = iArr;
                    i18 = i13;
                } else {
                    i48 = c0153ec.I0;
                    if (i48 == 0) {
                        iCeil2 = c0153ec.H0;
                        if (iCeil2 <= 0) {
                            i62 = 0;
                            i63 = 0;
                            i64 = 0;
                            while (i62 < i12) {
                                int i712 = i62;
                                if (i62 > 0) {
                                    i63 += c0153ec.C0;
                                }
                                c0425n16 = c0425n7Arr2[i712];
                                if (c0425n16 != null) {
                                    iD4 = c0153ec.D(c0425n16, i7) + i63;
                                    if (iD4 > i7) {
                                        break;
                                        break;
                                    } else {
                                        i64++;
                                        i63 = iD4;
                                    }
                                }
                                i62 = i712 + 1;
                            }
                            iCeil2 = i64;
                        }
                        iCeil = 0;
                    } else {
                        i49 = c0153ec.H0;
                        if (i49 <= 0) {
                            i50 = 0;
                            i51 = 0;
                            iCeil = 0;
                            while (i50 < i12) {
                                int i713 = i50;
                                if (i50 > 0) {
                                    i51 += c0153ec.D0;
                                }
                                c0425n10 = c0425n7Arr2[i713];
                                if (c0425n10 != null) {
                                    iC3 = c0153ec.C(c0425n10, i7) + i51;
                                    if (iC3 > i7) {
                                        break;
                                        break;
                                    } else {
                                        iCeil++;
                                        i51 = iC3;
                                    }
                                }
                                i50 = i713 + 1;
                            }
                        } else {
                            iCeil = i49;
                        }
                        iCeil2 = 0;
                    }
                    if (c0153ec.M0 == null) {
                        c0153ec.M0 = new int[2];
                    }
                    if (iCeil != 0) {
                    }
                    while (!z5) {
                        if (i48 == 0) {
                            iCeil = (int) Math.ceil(i12 / iCeil2);
                            iCeil2 = iCeil2;
                        } else {
                            iCeil2 = (int) Math.ceil(i12 / iCeil);
                        }
                        c0425n7Arr5 = c0153ec.L0;
                        int i714 = i5;
                        if (c0425n7Arr5 != null) {
                            c0153ec.L0 = new C0425n7[iCeil2];
                        } else {
                            c0153ec.L0 = new C0425n7[iCeil2];
                        }
                        c0425n7Arr6 = c0153ec.K0;
                        if (c0425n7Arr6 != null) {
                            c0153ec.K0 = new C0425n7[iCeil];
                        } else {
                            c0153ec.K0 = new C0425n7[iCeil];
                        }
                        i52 = 0;
                        while (i52 < iCeil2) {
                            i57 = 0;
                            while (i57 < iCeil) {
                                i58 = (i57 * iCeil2) + i52;
                                i59 = i52;
                                if (i48 == 1) {
                                    i58 = (i59 * iCeil) + i57;
                                }
                                int i715 = i48;
                                i60 = i58;
                                if (i60 >= c0425n7Arr2.length) {
                                    i61 = i57;
                                } else {
                                    int iD6 = c0153ec.D(c0425n13, i7);
                                    i61 = i57;
                                    c0425n14 = c0153ec.L0[i59];
                                    if (c0425n14 != null) {
                                        c0153ec.L0[i59] = c0425n13;
                                    } else {
                                        c0153ec.L0[i59] = c0425n13;
                                    }
                                    int iC6 = c0153ec.C(c0425n13, i7);
                                    c0425n15 = c0153ec.K0[i61];
                                    if (c0425n15 != null) {
                                        c0153ec.K0[i61] = c0425n13;
                                    } else {
                                        c0153ec.K0[i61] = c0425n13;
                                    }
                                }
                                i57 = i61 + 1;
                                i52 = i59;
                                i48 = i715;
                            }
                            i52++;
                        }
                        i53 = i48;
                        i54 = 0;
                        iD3 = 0;
                        while (i54 < iCeil2) {
                            c0425n12 = c0153ec.L0[i54];
                            int i716 = i54;
                            if (c0425n12 == null) {
                                if (i54 > 0) {
                                    iD3 += c0153ec.C0;
                                }
                                iD3 = c0153ec.D(c0425n12, i7) + iD3;
                            }
                            i54 = i716 + 1;
                        }
                        i55 = 0;
                        iC4 = 0;
                        while (i55 < iCeil) {
                            i56 = i55;
                            c0425n11 = c0153ec.K0[i56];
                            int i717 = i6;
                            if (c0425n11 == null) {
                                if (i56 > 0) {
                                    iC4 += c0153ec.D0;
                                }
                                iC4 = c0153ec.C(c0425n11, i7) + iC4;
                            }
                            i55 = i56 + 1;
                            i6 = i717;
                        }
                        int i718 = i6;
                        iArr[0] = iD3;
                        iArr[1] = iC4;
                        if (i53 == 0) {
                            if (iD3 > i7) {
                                z5 = true;
                            } else {
                                z5 = true;
                            }
                        } else if (iC4 > i7) {
                            z5 = true;
                        } else {
                            z5 = true;
                        }
                        i4 = i4;
                        i5 = i714;
                        i6 = i718;
                        i48 = i53;
                    }
                    int i719 = iCeil2;
                    i23 = i4;
                    i24 = i5;
                    i25 = i6;
                    int[] iArr5 = c0153ec.M0;
                    iArr5[0] = i719;
                    iArr5[1] = iCeil;
                    iArr2 = iArr;
                    i15 = i23;
                    i18 = i13;
                    i16 = i24;
                    i17 = i25;
                }
                c2 = 1;
                i21 = iArr2[c] + i18 + i15;
                i22 = iArr2[c2] + i16 + i17;
                if (mode != 1073741824) {
                    if (mode == Integer.MIN_VALUE) {
                        size2 = Math.min(i21, size2);
                    } else if (mode == 0) {
                        size2 = i21;
                    } else {
                        size2 = 0;
                    }
                }
                if (mode2 != 1073741824) {
                    if (mode2 == Integer.MIN_VALUE) {
                        size3 = Math.min(i22, size3);
                    } else if (mode2 == 0) {
                        size3 = i22;
                    } else {
                        size3 = 0;
                    }
                }
                c0153ec.m0 = size2;
                c0153ec.n0 = size3;
                c0153ec.y(size2);
                c0153ec.v(size3);
                if (c0153ec.e0 > 0) {
                    z = c2;
                } else {
                    z = 0;
                }
                c0153ec.l0 = z;
            }
        }
        setMeasuredDimension(c0153ec.m0, c0153ec.n0);
    }

    @Override // rikka.shizuku.AbstractC0056b7, android.view.View
    public final void onAttachedToWindow() {
        ViewParent parent;
        super.onAttachedToWindow();
        if ((this.g || this.h) && (parent = getParent()) != null && (parent instanceof ConstraintLayout)) {
            ConstraintLayout constraintLayout = (ConstraintLayout) parent;
            int visibility = getVisibility();
            float elevation = getElevation();
            for (int i = 0; i < this.b; i++) {
                View view = (View) constraintLayout.a.get(this.a[i]);
                if (view != null) {
                    if (this.g) {
                        view.setVisibility(visibility);
                    }
                    if (this.h && elevation > 0.0f) {
                        view.setTranslationZ(view.getTranslationZ() + elevation);
                    }
                }
            }
        }
    }

    @Override // rikka.shizuku.AbstractC0056b7, android.view.View
    public final void onMeasure(int i, int i2) {
        i(this.i, i, i2);
    }

    @Override // android.view.View
    public final void setElevation(float f) {
        super.setElevation(f);
        c();
    }

    @Override // android.view.View
    public final void setVisibility(int i) {
        super.setVisibility(i);
        c();
    }
}
