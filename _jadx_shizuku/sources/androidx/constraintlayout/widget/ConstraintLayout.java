package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParserException;
import rikka.html.text.style.BulletSpan;
import rikka.shizuku.AbstractC0056b7;
import rikka.shizuku.AbstractC0087c7;
import rikka.shizuku.AbstractC0463oe;
import rikka.shizuku.AbstractC0725wt;
import rikka.shizuku.Ae;
import rikka.shizuku.C0118d7;
import rikka.shizuku.C0148e7;
import rikka.shizuku.C0153ec;
import rikka.shizuku.C0179f7;
import rikka.shizuku.C0210g7;
import rikka.shizuku.C0394m7;
import rikka.shizuku.C0425n7;
import rikka.shizuku.C0456o7;
import rikka.shizuku.C0600ss;
import rikka.shizuku.Fk;
import rikka.shizuku.J5;
import rikka.shizuku.K3;
import rikka.shizuku.L9;
import rikka.shizuku.Lg;
import rikka.shizuku.R2;
import rikka.shizuku.Wd;
import rikka.shizuku.X9;
import rikka.shizuku.Xd;
import rikka.shizuku.Yd;

/* JADX INFO: loaded from: classes.dex */
public class ConstraintLayout extends ViewGroup {
    public final SparseArray a;
    public final ArrayList b;
    public final C0456o7 c;
    public int d;
    public int e;
    public int f;
    public int g;
    public boolean h;
    public int i;
    public C0394m7 j;
    public int k;
    public HashMap l;
    public final SparseArray m;
    public final C0148e7 n;

    public ConstraintLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.a = new SparseArray();
        this.b = new ArrayList(4);
        this.c = new C0456o7();
        this.d = 0;
        this.e = 0;
        this.f = Integer.MAX_VALUE;
        this.g = Integer.MAX_VALUE;
        this.h = true;
        this.i = 263;
        this.j = null;
        this.k = -1;
        this.l = new HashMap();
        this.m = new SparseArray();
        this.n = new C0148e7(this);
        c(attributeSet, 0);
    }

    public static C0118d7 a() {
        C0118d7 c0118d7 = new C0118d7(-2, -2);
        c0118d7.a = -1;
        c0118d7.b = -1;
        c0118d7.c = -1.0f;
        c0118d7.d = -1;
        c0118d7.e = -1;
        c0118d7.f = -1;
        c0118d7.g = -1;
        c0118d7.h = -1;
        c0118d7.i = -1;
        c0118d7.j = -1;
        c0118d7.k = -1;
        c0118d7.l = -1;
        c0118d7.m = -1;
        c0118d7.n = 0;
        c0118d7.o = 0.0f;
        c0118d7.p = -1;
        c0118d7.q = -1;
        c0118d7.r = -1;
        c0118d7.s = -1;
        c0118d7.t = -1;
        c0118d7.u = -1;
        c0118d7.v = -1;
        c0118d7.w = -1;
        c0118d7.x = -1;
        c0118d7.y = -1;
        c0118d7.z = 0.5f;
        c0118d7.A = 0.5f;
        c0118d7.B = null;
        c0118d7.C = 1;
        c0118d7.D = -1.0f;
        c0118d7.E = -1.0f;
        c0118d7.F = 0;
        c0118d7.G = 0;
        c0118d7.H = 0;
        c0118d7.I = 0;
        c0118d7.J = 0;
        c0118d7.K = 0;
        c0118d7.L = 0;
        c0118d7.M = 0;
        c0118d7.N = 1.0f;
        c0118d7.O = 1.0f;
        c0118d7.P = -1;
        c0118d7.Q = -1;
        c0118d7.R = -1;
        c0118d7.S = false;
        c0118d7.T = false;
        c0118d7.U = null;
        c0118d7.V = true;
        c0118d7.W = true;
        c0118d7.X = false;
        c0118d7.Y = false;
        c0118d7.Z = false;
        c0118d7.a0 = -1;
        c0118d7.b0 = -1;
        c0118d7.c0 = -1;
        c0118d7.d0 = -1;
        c0118d7.e0 = -1;
        c0118d7.f0 = -1;
        c0118d7.g0 = 0.5f;
        c0118d7.k0 = new C0425n7();
        return c0118d7;
    }

    public final C0425n7 b(View view) {
        if (view == this) {
            return this.c;
        }
        if (view == null) {
            return null;
        }
        return ((C0118d7) view.getLayoutParams()).k0;
    }

    public final void c(AttributeSet attributeSet, int i) {
        C0456o7 c0456o7 = this.c;
        c0456o7.U = this;
        C0148e7 c0148e7 = this.n;
        c0456o7.g0 = c0148e7;
        c0456o7.f0.f = c0148e7;
        this.a.put(getId(), this);
        this.j = null;
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, Fk.b, i, 0);
            int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
            for (int i2 = 0; i2 < indexCount; i2++) {
                int index = typedArrayObtainStyledAttributes.getIndex(i2);
                if (index == 9) {
                    this.d = typedArrayObtainStyledAttributes.getDimensionPixelOffset(index, this.d);
                } else if (index == 10) {
                    this.e = typedArrayObtainStyledAttributes.getDimensionPixelOffset(index, this.e);
                } else if (index == 7) {
                    this.f = typedArrayObtainStyledAttributes.getDimensionPixelOffset(index, this.f);
                } else if (index == 8) {
                    this.g = typedArrayObtainStyledAttributes.getDimensionPixelOffset(index, this.g);
                } else if (index == 89) {
                    this.i = typedArrayObtainStyledAttributes.getInt(index, this.i);
                } else if (index == 38) {
                    int resourceId = typedArrayObtainStyledAttributes.getResourceId(index, 0);
                    if (resourceId != 0) {
                        try {
                            d(resourceId);
                        } catch (Resources.NotFoundException unused) {
                        }
                    }
                } else if (index == 18) {
                    int resourceId2 = typedArrayObtainStyledAttributes.getResourceId(index, 0);
                    try {
                        C0394m7 c0394m7 = new C0394m7();
                        this.j = c0394m7;
                        c0394m7.e(getContext(), resourceId2);
                    } catch (Resources.NotFoundException unused2) {
                        this.j = null;
                    }
                    this.k = resourceId2;
                }
            }
            typedArrayObtainStyledAttributes.recycle();
        }
        int i3 = this.i;
        c0456o7.p0 = i3;
        Lg.p = (i3 & 256) == 256;
    }

    @Override // android.view.ViewGroup
    public final boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof C0118d7;
    }

    /* JADX WARN: Code duplicated, block: B:32:0x0077  */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public final void d(int i) {
        byte b;
        Context context = getContext();
        R2 r2 = new R2(10, false);
        r2.b = new SparseArray();
        r2.c = new SparseArray();
        XmlResourceParser xml = context.getResources().getXml(i);
        try {
            C0179f7 c0179f7 = null;
            for (int eventType = xml.getEventType(); eventType != 1; eventType = xml.next()) {
                if (eventType == 0) {
                    xml.getName();
                } else if (eventType == 2) {
                    String name = xml.getName();
                    switch (name.hashCode()) {
                        case -1349929691:
                            if (name.equals("ConstraintSet")) {
                                b = 4;
                            } else {
                                b = -1;
                            }
                            break;
                        case 80204913:
                            if (name.equals("State")) {
                                b = 2;
                            } else {
                                b = -1;
                            }
                            break;
                        case 1382829617:
                            if (name.equals("StateSet")) {
                                b = 1;
                            } else {
                                b = -1;
                            }
                            break;
                        case 1657696882:
                            if (name.equals("layoutDescription")) {
                                b = 0;
                            } else {
                                b = -1;
                            }
                            break;
                        case 1901439077:
                            if (name.equals("Variant")) {
                                b = 3;
                            } else {
                                b = -1;
                            }
                            break;
                        default:
                            b = -1;
                            break;
                    }
                    if (b != 0 && b != 1) {
                        if (b == 2) {
                            c0179f7 = new C0179f7(context, xml);
                            ((SparseArray) r2.b).put(c0179f7.a, c0179f7);
                        } else if (b == 3) {
                            C0210g7 c0210g7 = new C0210g7(context, xml);
                            if (c0179f7 != null) {
                                ((ArrayList) c0179f7.c).add(c0210g7);
                            }
                        } else if (b != 4) {
                            Log.v("ConstraintLayoutStates", "unknown tag " + name);
                        } else {
                            r2.O(context, xml);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e2) {
            e2.printStackTrace();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchDraw(Canvas canvas) {
        Object tag;
        int size;
        ArrayList arrayList = this.b;
        if (arrayList != null && (size = arrayList.size()) > 0) {
            for (int i = 0; i < size; i++) {
                ((AbstractC0056b7) arrayList.get(i)).getClass();
            }
        }
        super.dispatchDraw(canvas);
        if (isInEditMode()) {
            int childCount = getChildCount();
            float width = getWidth();
            float height = getHeight();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = getChildAt(i2);
                if (childAt.getVisibility() != 8 && (tag = childAt.getTag()) != null && (tag instanceof String)) {
                    String[] strArrSplit = ((String) tag).split(",");
                    if (strArrSplit.length == 4) {
                        int i3 = Integer.parseInt(strArrSplit[0]);
                        int i4 = Integer.parseInt(strArrSplit[1]);
                        int i5 = Integer.parseInt(strArrSplit[2]);
                        int i6 = (int) ((i3 / 1080.0f) * width);
                        int i7 = (int) ((i4 / 1920.0f) * height);
                        int i8 = (int) ((Integer.parseInt(strArrSplit[3]) / 1920.0f) * height);
                        Paint paint = new Paint();
                        paint.setColor(-65536);
                        float f = i6;
                        float f2 = i7;
                        float f3 = i6 + ((int) ((i5 / 1080.0f) * width));
                        canvas.drawLine(f, f2, f3, f2, paint);
                        float f4 = i7 + i8;
                        canvas.drawLine(f3, f2, f3, f4, paint);
                        canvas.drawLine(f3, f4, f, f4, paint);
                        canvas.drawLine(f, f4, f, f2, paint);
                        paint.setColor(-16711936);
                        canvas.drawLine(f, f2, f3, f4, paint);
                        canvas.drawLine(f, f4, f3, f2, paint);
                    }
                }
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:162:0x0311  */
    /* JADX WARN: Code duplicated, block: B:164:0x0329  */
    /* JADX WARN: Code duplicated, block: B:166:0x032c  */
    /* JADX WARN: Code duplicated, block: B:170:0x0347  */
    /* JADX WARN: Code duplicated, block: B:178:0x0363  */
    /* JADX WARN: Code duplicated, block: B:375:0x0394 A[SYNTHETIC] */
    public final void e(C0456o7 c0456o7, int i, int i2, int i3) {
        int iMin;
        int iMax;
        int iMin2;
        int iMax2;
        int i4;
        boolean z;
        int i5;
        int i6;
        C0148e7 c0148e7;
        int i7;
        int i8;
        int i9;
        boolean zD;
        boolean z2;
        boolean z3;
        ArrayList arrayList;
        int i10;
        int i11;
        C0148e7 c0148e8;
        boolean z4;
        C0148e7 c0148e9;
        int i12;
        int i13;
        int i14;
        int i15;
        int[] iArr;
        int i16;
        int i17;
        boolean z5;
        int size;
        int i18;
        int size2;
        int i19;
        boolean z6;
        AbstractC0725wt abstractC0725wt;
        AbstractC0725wt abstractC0725wt2;
        int mode = View.MeasureSpec.getMode(i2);
        int size3 = View.MeasureSpec.getSize(i2);
        int mode2 = View.MeasureSpec.getMode(i3);
        int size4 = View.MeasureSpec.getSize(i3);
        int iMax3 = Math.max(0, getPaddingTop());
        int iMax4 = Math.max(0, getPaddingBottom());
        int i20 = iMax3 + iMax4;
        int iMax5 = Math.max(0, getPaddingRight()) + Math.max(0, getPaddingLeft());
        int iMax6 = Math.max(0, getPaddingEnd()) + Math.max(0, getPaddingStart());
        if (iMax6 > 0) {
            iMax5 = iMax6;
        }
        C0148e7 c0148e10 = this.n;
        c0148e10.b = iMax3;
        c0148e10.c = iMax4;
        c0148e10.d = iMax5;
        c0148e10.e = i20;
        c0148e10.f = i2;
        c0148e10.g = i3;
        int iMax7 = Math.max(0, getPaddingStart());
        int iMax8 = Math.max(0, getPaddingEnd());
        int i21 = 1;
        if (iMax7 <= 0 && iMax8 <= 0) {
            iMax7 = Math.max(0, getPaddingLeft());
        } else if ((getContext().getApplicationInfo().flags & 4194304) != 0 && 1 == getLayoutDirection()) {
            iMax7 = iMax8;
        }
        int i22 = size3 - iMax5;
        int i23 = size4 - i20;
        int i24 = c0148e10.e;
        int i25 = c0148e10.d;
        int childCount = getChildCount();
        if (mode == Integer.MIN_VALUE) {
            if (childCount == 0) {
                iMax = Math.max(0, this.d);
                iMin = iMax;
            } else {
                iMin = i22;
            }
            i21 = 2;
        } else if (mode == 0) {
            if (childCount == 0) {
                iMax = Math.max(0, this.d);
                iMin = iMax;
            } else {
                iMin = 0;
            }
            i21 = 2;
        } else if (mode != 1073741824) {
            iMin = 0;
        } else {
            iMin = Math.min(this.f - i25, i22);
            i21 = 1;
        }
        if (mode2 == Integer.MIN_VALUE) {
            if (childCount == 0) {
                iMax2 = Math.max(0, this.e);
                iMin2 = iMax2;
            } else {
                iMin2 = i23;
            }
            i4 = 2;
        } else if (mode2 != 0) {
            iMin2 = mode2 != 1073741824 ? 0 : Math.min(this.g - i24, i23);
            i4 = 1;
        } else {
            if (childCount == 0) {
                iMax2 = Math.max(0, this.e);
                iMin2 = iMax2;
            } else {
                iMin2 = 0;
            }
            i4 = 2;
        }
        int iL = c0456o7.l();
        L9 l9 = c0456o7.f0;
        int i26 = iMin;
        if (i26 != iL || iMin2 != c0456o7.i()) {
            l9.c = true;
        }
        c0456o7.N = 0;
        c0456o7.O = 0;
        int i27 = this.f - i25;
        int[] iArr2 = c0456o7.u;
        iArr2[0] = i27;
        iArr2[1] = this.g - i24;
        c0456o7.Q = 0;
        c0456o7.R = 0;
        c0456o7.w(i21);
        c0456o7.y(i26);
        c0456o7.x(i4);
        c0456o7.v(iMin2);
        int i28 = this.d - i25;
        if (i28 < 0) {
            c0456o7.Q = 0;
        } else {
            c0456o7.Q = i28;
        }
        int i29 = this.e - i24;
        if (i29 < 0) {
            c0456o7.R = 0;
        } else {
            c0456o7.R = i29;
        }
        c0456o7.j0 = iMax7;
        c0456o7.k0 = iMax3;
        K3 k3 = c0456o7.e0;
        k3.getClass();
        C0148e7 c0148e11 = c0456o7.g0;
        int size5 = c0456o7.d0.size();
        int iL2 = c0456o7.l();
        int i30 = c0456o7.i();
        boolean z7 = (i & 128) == 128;
        boolean z8 = z7 || (i & 64) == 64;
        if (z8) {
            int i31 = 0;
            while (true) {
                if (i31 < size5) {
                    C0425n7 c0425n7 = (C0425n7) c0456o7.d0.get(i31);
                    boolean z9 = z8;
                    int[] iArr3 = c0425n7.c0;
                    i5 = size5;
                    boolean z10 = (iArr3[0] == 3) && (iArr3[1] == 3) && c0425n7.L > 0.0f;
                    if ((c0425n7.q() && z10) || ((c0425n7.r() && z10) || (c0425n7 instanceof C0153ec) || c0425n7.q() || c0425n7.r())) {
                        i6 = 1073741824;
                        z = false;
                    } else {
                        i31++;
                        z8 = z9;
                        size5 = i5;
                    }
                } else {
                    z = z8;
                    i5 = size5;
                    i6 = 1073741824;
                }
            }
        } else {
            z = z8;
            i5 = size5;
            i6 = 1073741824;
        }
        if (z && ((mode == i6 && mode2 == i6) || z7)) {
            int iMin3 = Math.min(iArr2[0], i22);
            int iMin4 = Math.min(iArr2[1], i23);
            if (mode == 1073741824 && c0456o7.l() != iMin3) {
                c0456o7.y(iMin3);
                c0456o7.f0.b = true;
            }
            if (mode2 == 1073741824 && c0456o7.i() != iMin4) {
                c0456o7.v(iMin4);
                c0456o7.f0.b = true;
            }
            if (mode == 1073741824 && mode2 == 1073741824) {
                boolean z11 = l9.b;
                C0456o7 c0456o8 = l9.a;
                if (z11 || l9.c) {
                    ArrayList arrayList2 = c0456o8.d0;
                    int size6 = arrayList2.size();
                    int i32 = 0;
                    while (i32 < size6) {
                        Object obj = arrayList2.get(i32);
                        i32++;
                        C0425n7 c0425n8 = (C0425n7) obj;
                        c0425n8.a = false;
                        c0425n8.d.n();
                        c0425n8.e.m();
                    }
                    i14 = 0;
                    c0456o8.a = false;
                    c0456o8.d.n();
                    c0456o8.e.m();
                    l9.c = false;
                } else {
                    i14 = 0;
                }
                l9.b(l9.d);
                c0456o8.N = i14;
                c0456o8.O = i14;
                int iH = c0456o8.h(i14);
                int iH2 = c0456o8.h(1);
                if (l9.b) {
                    l9.c();
                }
                int iM = c0456o8.m();
                int iN = c0456o8.n();
                Ae ae = c0456o8.d;
                ae.h.d(iM);
                C0600ss c0600ss = c0456o8.e;
                c0600ss.h.d(iN);
                l9.g();
                ArrayList arrayList3 = l9.e;
                X9 x9 = ae.e;
                c0148e7 = c0148e11;
                X9 x10 = c0600ss.e;
                i7 = iL2;
                if (iH == 2 || iH2 == 2) {
                    if (z7) {
                        int size7 = arrayList3.size();
                        i8 = i30;
                        int i33 = 0;
                        while (i33 < size7) {
                            Object obj2 = arrayList3.get(i33);
                            i33++;
                            if (!((AbstractC0725wt) obj2).k()) {
                                z7 = false;
                                break;
                            }
                        }
                    } else {
                        i8 = i30;
                    }
                    if (z7 && iH == 2) {
                        c0456o8.w(1);
                        c0456o8.y(l9.d(c0456o8, 0));
                        x9.d(c0456o8.l());
                    }
                    if (z7 && iH2 == 2) {
                        i15 = 1;
                        c0456o8.x(1);
                        c0456o8.v(l9.d(c0456o8, 1));
                        x10.d(c0456o8.i());
                    }
                    iArr = c0456o8.c0;
                    i16 = iArr[0];
                    if (i16 != i15 || i16 == 4) {
                        int iL3 = c0456o8.l() + iM;
                        ae.i.d(iL3);
                        x9.d(iL3 - iM);
                        l9.g();
                        i17 = iArr[1];
                        if (i17 != 1 || i17 == 4) {
                            int i34 = c0456o8.i() + iN;
                            c0600ss.i.d(i34);
                            x10.d(i34 - iN);
                        }
                        l9.g();
                        z5 = true;
                    } else {
                        z5 = false;
                    }
                    size = arrayList3.size();
                    i18 = 0;
                    while (i18 < size) {
                        Object obj3 = arrayList3.get(i18);
                        i18++;
                        abstractC0725wt2 = (AbstractC0725wt) obj3;
                        if (abstractC0725wt2.b == c0456o8 || abstractC0725wt2.g) {
                            abstractC0725wt2.e();
                        }
                    }
                    size2 = arrayList3.size();
                    i19 = 0;
                    while (true) {
                        if (i19 < size2) {
                            z6 = true;
                            break;
                        }
                        Object obj4 = arrayList3.get(i19);
                        i19++;
                        abstractC0725wt = (AbstractC0725wt) obj4;
                        if (!z5 || abstractC0725wt.b != c0456o8) {
                            if (abstractC0725wt.h.j || ((!abstractC0725wt.i.j && !(abstractC0725wt instanceof Yd)) || (!abstractC0725wt.e.j && !(abstractC0725wt instanceof J5) && !(abstractC0725wt instanceof Yd)))) {
                                z6 = false;
                                break;
                            }
                        }
                    }
                    c0456o8.w(iH);
                    c0456o8.x(iH2);
                    zD = z6;
                    i13 = 1073741824;
                    i9 = 2;
                } else {
                    i8 = i30;
                }
                i15 = 1;
                iArr = c0456o8.c0;
                i16 = iArr[0];
                if (i16 != i15) {
                    int iL4 = c0456o8.l() + iM;
                    ae.i.d(iL4);
                    x9.d(iL4 - iM);
                    l9.g();
                    i17 = iArr[1];
                    if (i17 != 1) {
                        int i35 = c0456o8.i() + iN;
                        c0600ss.i.d(i35);
                        x10.d(i35 - iN);
                    } else {
                        int i36 = c0456o8.i() + iN;
                        c0600ss.i.d(i36);
                        x10.d(i36 - iN);
                    }
                    l9.g();
                    z5 = true;
                } else {
                    int iL5 = c0456o8.l() + iM;
                    ae.i.d(iL5);
                    x9.d(iL5 - iM);
                    l9.g();
                    i17 = iArr[1];
                    if (i17 != 1) {
                        int i37 = c0456o8.i() + iN;
                        c0600ss.i.d(i37);
                        x10.d(i37 - iN);
                    } else {
                        int i38 = c0456o8.i() + iN;
                        c0600ss.i.d(i38);
                        x10.d(i38 - iN);
                    }
                    l9.g();
                    z5 = true;
                }
                size = arrayList3.size();
                i18 = 0;
                while (i18 < size) {
                    Object obj5 = arrayList3.get(i18);
                    i18++;
                    abstractC0725wt2 = (AbstractC0725wt) obj5;
                    if (abstractC0725wt2.b == c0456o8) {
                    }
                    abstractC0725wt2.e();
                }
                size2 = arrayList3.size();
                i19 = 0;
                while (true) {
                    if (i19 < size2) {
                        z6 = true;
                        break;
                    }
                    Object obj6 = arrayList3.get(i19);
                    i19++;
                    abstractC0725wt = (AbstractC0725wt) obj6;
                    if (!z5) {
                    }
                    if (abstractC0725wt.h.j) {
                    }
                    z6 = false;
                    break;
                }
                c0456o8.w(iH);
                c0456o8.x(iH2);
                zD = z6;
                i13 = 1073741824;
                i9 = 2;
            } else {
                c0148e7 = c0148e11;
                i7 = iL2;
                i8 = i30;
                boolean z12 = l9.b;
                C0456o7 c0456o9 = l9.a;
                if (z12) {
                    ArrayList arrayList4 = c0456o9.d0;
                    int size8 = arrayList4.size();
                    int i39 = 0;
                    while (i39 < size8) {
                        Object obj7 = arrayList4.get(i39);
                        i39++;
                        C0425n7 c0425n9 = (C0425n7) obj7;
                        c0425n9.a = false;
                        Ae ae2 = c0425n9.d;
                        ae2.e.j = false;
                        ae2.g = false;
                        ae2.n();
                        C0600ss c0600ss2 = c0425n9.e;
                        c0600ss2.e.j = false;
                        c0600ss2.g = false;
                        c0600ss2.m();
                    }
                    i12 = 0;
                    c0456o9.a = false;
                    Ae ae3 = c0456o9.d;
                    ae3.e.j = false;
                    ae3.g = false;
                    ae3.n();
                    C0600ss c0600ss3 = c0456o9.e;
                    c0600ss3.e.j = false;
                    c0600ss3.g = false;
                    c0600ss3.m();
                    l9.c();
                } else {
                    i12 = 0;
                }
                l9.b(l9.d);
                c0456o9.N = i12;
                c0456o9.O = i12;
                c0456o9.d.h.d(i12);
                c0456o9.e.h.d(i12);
                i13 = 1073741824;
                if (mode == 1073741824) {
                    zD = c0456o7.D(i12, z7);
                    i9 = 1;
                } else {
                    i9 = 0;
                    zD = true;
                }
                if (mode2 == 1073741824) {
                    zD &= c0456o7.D(1, z7);
                    i9++;
                }
            }
            if (zD) {
                c0456o7.z(mode == i13, mode2 == i13);
            }
        } else {
            c0148e7 = c0148e11;
            i7 = iL2;
            i8 = i30;
            i9 = 0;
            zD = false;
        }
        if (zD && i9 == 2) {
            return;
        }
        if (i5 > 0) {
            int size9 = c0456o7.d0.size();
            C0148e7 c0148e12 = c0456o7.g0;
            for (int i40 = 0; i40 < size9; i40++) {
                C0425n7 c0425n10 = (C0425n7) c0456o7.d0.get(i40);
                if (!(c0425n10 instanceof Xd) && (!c0425n10.d.e.j || !c0425n10.e.e.j)) {
                    int iH3 = c0425n10.h(0);
                    int iH4 = c0425n10.h(1);
                    if (iH3 != 3 || c0425n10.j == 1 || iH4 != 3 || c0425n10.k == 1) {
                        k3.m(c0148e12, c0425n10, false);
                    }
                }
            }
            ConstraintLayout constraintLayout = c0148e12.a;
            int childCount2 = constraintLayout.getChildCount();
            for (int i41 = 0; i41 < childCount2; i41++) {
                constraintLayout.getChildAt(i41);
            }
            ArrayList arrayList5 = constraintLayout.b;
            int size10 = arrayList5.size();
            if (size10 > 0) {
                for (int i42 = 0; i42 < size10; i42++) {
                    ((AbstractC0056b7) arrayList5.get(i42)).getClass();
                }
            }
        }
        int i43 = c0456o7.p0;
        ArrayList arrayList6 = (ArrayList) k3.a;
        int size11 = arrayList6.size();
        int i44 = i7;
        int i45 = i8;
        if (i5 > 0) {
            k3.p(c0456o7, i44, i45);
        }
        if (size11 > 0) {
            int[] iArr4 = c0456o7.c0;
            z2 = false;
            boolean z13 = iArr4[0] == 2;
            boolean z14 = iArr4[1] == 2;
            int iL6 = c0456o7.l();
            C0456o7 c0456o10 = (C0456o7) k3.c;
            int iMax9 = Math.max(iL6, c0456o10.Q);
            int iMax10 = Math.max(c0456o7.i(), c0456o10.R);
            int i46 = 0;
            boolean z15 = false;
            while (i46 < size11) {
                C0425n7 c0425n11 = (C0425n7) arrayList6.get(i46);
                if (c0425n11 instanceof C0153ec) {
                    int iL7 = c0425n11.l();
                    int i47 = c0425n11.i();
                    z4 = z14;
                    c0148e9 = c0148e7;
                    boolean zM = z15 | k3.m(c0148e9, c0425n11, true);
                    int iL8 = c0425n11.l();
                    boolean z16 = zM;
                    int i48 = c0425n11.i();
                    if (iL8 != iL7) {
                        c0425n11.y(iL8);
                        if (z13 && c0425n11.m() + c0425n11.J > iMax9) {
                            iMax9 = Math.max(iMax9, c0425n11.g(4).c() + c0425n11.m() + c0425n11.J);
                        }
                        z16 = true;
                    }
                    if (i48 != i47) {
                        c0425n11.v(i48);
                        if (z4 && c0425n11.n() + c0425n11.K > iMax10) {
                            iMax10 = Math.max(iMax10, c0425n11.g(5).c() + c0425n11.n() + c0425n11.K);
                        }
                        z16 = true;
                    }
                    z15 = z16 | ((C0153ec) c0425n11).l0;
                } else {
                    z4 = z14;
                    c0148e9 = c0148e7;
                }
                c0148e7 = c0148e9;
                i46++;
                z14 = z4;
            }
            boolean z17 = z14;
            int i49 = 0;
            while (true) {
                C0148e7 c0148e13 = c0148e7;
                if (i49 >= 2) {
                    break;
                }
                boolean zM2 = z15;
                int i50 = 0;
                while (i50 < size11) {
                    C0425n7 c0425n12 = (C0425n7) arrayList6.get(i50);
                    if ((!(c0425n12 instanceof AbstractC0463oe) || (c0425n12 instanceof C0153ec)) && !(c0425n12 instanceof Xd)) {
                        arrayList = arrayList6;
                        if (c0425n12.V != 8 && ((!c0425n12.d.e.j || !c0425n12.e.e.j) && !(c0425n12 instanceof C0153ec))) {
                            int iL9 = c0425n12.l();
                            int i51 = c0425n12.i();
                            i10 = size11;
                            int i52 = c0425n12.P;
                            i11 = i49;
                            zM2 |= k3.m(c0148e13, c0425n12, true);
                            int iL10 = c0425n12.l();
                            c0148e8 = c0148e13;
                            int i53 = c0425n12.i();
                            if (iL10 != iL9) {
                                c0425n12.y(iL10);
                                if (z13 && c0425n12.m() + c0425n12.J > iMax9) {
                                    iMax9 = Math.max(iMax9, c0425n12.g(4).c() + c0425n12.m() + c0425n12.J);
                                }
                                zM2 = true;
                            }
                            if (i53 != i51) {
                                c0425n12.v(i53);
                                if (z17 && c0425n12.n() + c0425n12.K > iMax10) {
                                    iMax10 = Math.max(iMax10, c0425n12.g(5).c() + c0425n12.n() + c0425n12.K);
                                }
                                zM2 = true;
                            }
                            if (c0425n12.w && i52 != c0425n12.P) {
                                zM2 = true;
                            }
                        }
                        i50++;
                        arrayList6 = arrayList;
                        size11 = i10;
                        i49 = i11;
                        c0148e13 = c0148e8;
                    } else {
                        arrayList = arrayList6;
                    }
                    i10 = size11;
                    i11 = i49;
                    c0148e8 = c0148e13;
                    i50++;
                    arrayList6 = arrayList;
                    size11 = i10;
                    i49 = i11;
                    c0148e13 = c0148e8;
                }
                ArrayList arrayList7 = arrayList6;
                int i54 = size11;
                int i55 = i49;
                c0148e7 = c0148e13;
                if (zM2) {
                    k3.p(c0456o7, i44, i45);
                    z15 = false;
                } else {
                    z15 = zM2;
                }
                i49 = i55 + 1;
                arrayList6 = arrayList7;
                size11 = i54;
            }
            if (z15) {
                k3.p(c0456o7, i44, i45);
                if (c0456o7.l() < iMax9) {
                    c0456o7.y(iMax9);
                    z3 = true;
                } else {
                    z3 = false;
                }
                if (c0456o7.i() < iMax10) {
                    c0456o7.v(iMax10);
                    z3 = true;
                }
                if (z3) {
                    k3.p(c0456o7, i44, i45);
                }
            }
        } else {
            z2 = false;
        }
        c0456o7.p0 = i43;
        Lg.p = (i43 & 256) == 256 ? true : z2;
    }

    @Override // android.view.View
    public final void forceLayout() {
        this.h = true;
        super.forceLayout();
    }

    @Override // android.view.ViewGroup
    public final /* bridge */ /* synthetic */ ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return a();
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        int i;
        Context context = getContext();
        C0118d7 c0118d7 = new C0118d7(context, attributeSet);
        c0118d7.a = -1;
        c0118d7.b = -1;
        c0118d7.c = -1.0f;
        c0118d7.d = -1;
        c0118d7.e = -1;
        c0118d7.f = -1;
        c0118d7.g = -1;
        c0118d7.h = -1;
        c0118d7.i = -1;
        c0118d7.j = -1;
        c0118d7.k = -1;
        c0118d7.l = -1;
        c0118d7.m = -1;
        c0118d7.n = 0;
        c0118d7.o = 0.0f;
        c0118d7.p = -1;
        c0118d7.q = -1;
        c0118d7.r = -1;
        c0118d7.s = -1;
        c0118d7.t = -1;
        c0118d7.u = -1;
        c0118d7.v = -1;
        c0118d7.w = -1;
        c0118d7.x = -1;
        c0118d7.y = -1;
        c0118d7.z = 0.5f;
        c0118d7.A = 0.5f;
        c0118d7.B = null;
        c0118d7.C = 1;
        c0118d7.D = -1.0f;
        c0118d7.E = -1.0f;
        c0118d7.F = 0;
        c0118d7.G = 0;
        c0118d7.H = 0;
        c0118d7.I = 0;
        c0118d7.J = 0;
        c0118d7.K = 0;
        c0118d7.L = 0;
        c0118d7.M = 0;
        c0118d7.N = 1.0f;
        c0118d7.O = 1.0f;
        c0118d7.P = -1;
        c0118d7.Q = -1;
        c0118d7.R = -1;
        c0118d7.S = false;
        c0118d7.T = false;
        c0118d7.U = null;
        c0118d7.V = true;
        c0118d7.W = true;
        c0118d7.X = false;
        c0118d7.Y = false;
        c0118d7.Z = false;
        c0118d7.a0 = -1;
        c0118d7.b0 = -1;
        c0118d7.c0 = -1;
        c0118d7.d0 = -1;
        c0118d7.e0 = -1;
        c0118d7.f0 = -1;
        c0118d7.g0 = 0.5f;
        c0118d7.k0 = new C0425n7();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Fk.b);
        int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
        for (int i2 = 0; i2 < indexCount; i2++) {
            int index = typedArrayObtainStyledAttributes.getIndex(i2);
            int i3 = AbstractC0087c7.a.get(index);
            switch (i3) {
                case 1:
                    c0118d7.R = typedArrayObtainStyledAttributes.getInt(index, c0118d7.R);
                    break;
                case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                    int resourceId = typedArrayObtainStyledAttributes.getResourceId(index, c0118d7.m);
                    c0118d7.m = resourceId;
                    if (resourceId == -1) {
                        c0118d7.m = typedArrayObtainStyledAttributes.getInt(index, -1);
                    }
                    break;
                case 3:
                    c0118d7.n = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, c0118d7.n);
                    break;
                case 4:
                    float f = typedArrayObtainStyledAttributes.getFloat(index, c0118d7.o) % 360.0f;
                    c0118d7.o = f;
                    if (f < 0.0f) {
                        c0118d7.o = (360.0f - f) % 360.0f;
                    }
                    break;
                case BulletSpan.STANDARD_BULLET_RADIUS /* 5 */:
                    c0118d7.a = typedArrayObtainStyledAttributes.getDimensionPixelOffset(index, c0118d7.a);
                    break;
                case 6:
                    c0118d7.b = typedArrayObtainStyledAttributes.getDimensionPixelOffset(index, c0118d7.b);
                    break;
                case 7:
                    c0118d7.c = typedArrayObtainStyledAttributes.getFloat(index, c0118d7.c);
                    break;
                case 8:
                    int resourceId2 = typedArrayObtainStyledAttributes.getResourceId(index, c0118d7.d);
                    c0118d7.d = resourceId2;
                    if (resourceId2 == -1) {
                        c0118d7.d = typedArrayObtainStyledAttributes.getInt(index, -1);
                    }
                    break;
                case 9:
                    int resourceId3 = typedArrayObtainStyledAttributes.getResourceId(index, c0118d7.e);
                    c0118d7.e = resourceId3;
                    if (resourceId3 == -1) {
                        c0118d7.e = typedArrayObtainStyledAttributes.getInt(index, -1);
                    }
                    break;
                case 10:
                    int resourceId4 = typedArrayObtainStyledAttributes.getResourceId(index, c0118d7.f);
                    c0118d7.f = resourceId4;
                    if (resourceId4 == -1) {
                        c0118d7.f = typedArrayObtainStyledAttributes.getInt(index, -1);
                    }
                    break;
                case 11:
                    int resourceId5 = typedArrayObtainStyledAttributes.getResourceId(index, c0118d7.g);
                    c0118d7.g = resourceId5;
                    if (resourceId5 == -1) {
                        c0118d7.g = typedArrayObtainStyledAttributes.getInt(index, -1);
                    }
                    break;
                case 12:
                    int resourceId6 = typedArrayObtainStyledAttributes.getResourceId(index, c0118d7.h);
                    c0118d7.h = resourceId6;
                    if (resourceId6 == -1) {
                        c0118d7.h = typedArrayObtainStyledAttributes.getInt(index, -1);
                    }
                    break;
                case 13:
                    int resourceId7 = typedArrayObtainStyledAttributes.getResourceId(index, c0118d7.i);
                    c0118d7.i = resourceId7;
                    if (resourceId7 == -1) {
                        c0118d7.i = typedArrayObtainStyledAttributes.getInt(index, -1);
                    }
                    break;
                case 14:
                    int resourceId8 = typedArrayObtainStyledAttributes.getResourceId(index, c0118d7.j);
                    c0118d7.j = resourceId8;
                    if (resourceId8 == -1) {
                        c0118d7.j = typedArrayObtainStyledAttributes.getInt(index, -1);
                    }
                    break;
                case 15:
                    int resourceId9 = typedArrayObtainStyledAttributes.getResourceId(index, c0118d7.k);
                    c0118d7.k = resourceId9;
                    if (resourceId9 == -1) {
                        c0118d7.k = typedArrayObtainStyledAttributes.getInt(index, -1);
                    }
                    break;
                case 16:
                    int resourceId10 = typedArrayObtainStyledAttributes.getResourceId(index, c0118d7.l);
                    c0118d7.l = resourceId10;
                    if (resourceId10 == -1) {
                        c0118d7.l = typedArrayObtainStyledAttributes.getInt(index, -1);
                    }
                    break;
                case 17:
                    int resourceId11 = typedArrayObtainStyledAttributes.getResourceId(index, c0118d7.p);
                    c0118d7.p = resourceId11;
                    if (resourceId11 == -1) {
                        c0118d7.p = typedArrayObtainStyledAttributes.getInt(index, -1);
                    }
                    break;
                case 18:
                    int resourceId12 = typedArrayObtainStyledAttributes.getResourceId(index, c0118d7.q);
                    c0118d7.q = resourceId12;
                    if (resourceId12 == -1) {
                        c0118d7.q = typedArrayObtainStyledAttributes.getInt(index, -1);
                    }
                    break;
                case 19:
                    int resourceId13 = typedArrayObtainStyledAttributes.getResourceId(index, c0118d7.r);
                    c0118d7.r = resourceId13;
                    if (resourceId13 == -1) {
                        c0118d7.r = typedArrayObtainStyledAttributes.getInt(index, -1);
                    }
                    break;
                case 20:
                    int resourceId14 = typedArrayObtainStyledAttributes.getResourceId(index, c0118d7.s);
                    c0118d7.s = resourceId14;
                    if (resourceId14 == -1) {
                        c0118d7.s = typedArrayObtainStyledAttributes.getInt(index, -1);
                    }
                    break;
                case 21:
                    c0118d7.t = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, c0118d7.t);
                    break;
                case 22:
                    c0118d7.u = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, c0118d7.u);
                    break;
                case 23:
                    c0118d7.v = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, c0118d7.v);
                    break;
                case 24:
                    c0118d7.w = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, c0118d7.w);
                    break;
                case 25:
                    c0118d7.x = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, c0118d7.x);
                    break;
                case 26:
                    c0118d7.y = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, c0118d7.y);
                    break;
                case 27:
                    c0118d7.S = typedArrayObtainStyledAttributes.getBoolean(index, c0118d7.S);
                    break;
                case 28:
                    c0118d7.T = typedArrayObtainStyledAttributes.getBoolean(index, c0118d7.T);
                    break;
                case 29:
                    c0118d7.z = typedArrayObtainStyledAttributes.getFloat(index, c0118d7.z);
                    break;
                case 30:
                    c0118d7.A = typedArrayObtainStyledAttributes.getFloat(index, c0118d7.A);
                    break;
                case 31:
                    int i4 = typedArrayObtainStyledAttributes.getInt(index, 0);
                    c0118d7.H = i4;
                    if (i4 == 1) {
                        Log.e("ConstraintLayout", "layout_constraintWidth_default=\"wrap\" is deprecated.\nUse layout_width=\"WRAP_CONTENT\" and layout_constrainedWidth=\"true\" instead.");
                    }
                    break;
                case 32:
                    int i5 = typedArrayObtainStyledAttributes.getInt(index, 0);
                    c0118d7.I = i5;
                    if (i5 == 1) {
                        Log.e("ConstraintLayout", "layout_constraintHeight_default=\"wrap\" is deprecated.\nUse layout_height=\"WRAP_CONTENT\" and layout_constrainedHeight=\"true\" instead.");
                    }
                    break;
                case 33:
                    try {
                        c0118d7.J = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, c0118d7.J);
                    } catch (Exception unused) {
                        if (typedArrayObtainStyledAttributes.getInt(index, c0118d7.J) == -2) {
                            c0118d7.J = -2;
                        }
                    }
                    break;
                case 34:
                    try {
                        c0118d7.L = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, c0118d7.L);
                    } catch (Exception unused2) {
                        if (typedArrayObtainStyledAttributes.getInt(index, c0118d7.L) == -2) {
                            c0118d7.L = -2;
                        }
                    }
                    break;
                case 35:
                    c0118d7.N = Math.max(0.0f, typedArrayObtainStyledAttributes.getFloat(index, c0118d7.N));
                    c0118d7.H = 2;
                    break;
                case 36:
                    try {
                        c0118d7.K = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, c0118d7.K);
                    } catch (Exception unused3) {
                        if (typedArrayObtainStyledAttributes.getInt(index, c0118d7.K) == -2) {
                            c0118d7.K = -2;
                        }
                    }
                    break;
                case 37:
                    try {
                        c0118d7.M = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, c0118d7.M);
                    } catch (Exception unused4) {
                        if (typedArrayObtainStyledAttributes.getInt(index, c0118d7.M) == -2) {
                            c0118d7.M = -2;
                        }
                    }
                    break;
                case 38:
                    c0118d7.O = Math.max(0.0f, typedArrayObtainStyledAttributes.getFloat(index, c0118d7.O));
                    c0118d7.I = 2;
                    break;
                default:
                    switch (i3) {
                        case 44:
                            String string = typedArrayObtainStyledAttributes.getString(index);
                            c0118d7.B = string;
                            c0118d7.C = -1;
                            if (string != null) {
                                int length = string.length();
                                int iIndexOf = c0118d7.B.indexOf(44);
                                if (iIndexOf <= 0 || iIndexOf >= length - 1) {
                                    i = 0;
                                } else {
                                    String strSubstring = c0118d7.B.substring(0, iIndexOf);
                                    if (strSubstring.equalsIgnoreCase("W")) {
                                        c0118d7.C = 0;
                                    } else if (strSubstring.equalsIgnoreCase("H")) {
                                        c0118d7.C = 1;
                                    }
                                    i = iIndexOf + 1;
                                }
                                int iIndexOf2 = c0118d7.B.indexOf(58);
                                if (iIndexOf2 < 0 || iIndexOf2 >= length - 1) {
                                    String strSubstring2 = c0118d7.B.substring(i);
                                    if (strSubstring2.length() > 0) {
                                        Float.parseFloat(strSubstring2);
                                    }
                                } else {
                                    String strSubstring3 = c0118d7.B.substring(i, iIndexOf2);
                                    String strSubstring4 = c0118d7.B.substring(iIndexOf2 + 1);
                                    if (strSubstring3.length() > 0 && strSubstring4.length() > 0) {
                                        try {
                                            float f2 = Float.parseFloat(strSubstring3);
                                            float f3 = Float.parseFloat(strSubstring4);
                                            if (f2 > 0.0f && f3 > 0.0f) {
                                                if (c0118d7.C == 1) {
                                                    Math.abs(f3 / f2);
                                                } else {
                                                    Math.abs(f2 / f3);
                                                }
                                            }
                                        } catch (NumberFormatException unused5) {
                                        }
                                    }
                                }
                            }
                            break;
                        case 45:
                            c0118d7.D = typedArrayObtainStyledAttributes.getFloat(index, c0118d7.D);
                            break;
                        case 46:
                            c0118d7.E = typedArrayObtainStyledAttributes.getFloat(index, c0118d7.E);
                            break;
                        case 47:
                            c0118d7.F = typedArrayObtainStyledAttributes.getInt(index, 0);
                            break;
                        case 48:
                            c0118d7.G = typedArrayObtainStyledAttributes.getInt(index, 0);
                            break;
                        case 49:
                            c0118d7.P = typedArrayObtainStyledAttributes.getDimensionPixelOffset(index, c0118d7.P);
                            break;
                        case 50:
                            c0118d7.Q = typedArrayObtainStyledAttributes.getDimensionPixelOffset(index, c0118d7.Q);
                            break;
                        case 51:
                            c0118d7.U = typedArrayObtainStyledAttributes.getString(index);
                            break;
                    }
                    break;
            }
        }
        typedArrayObtainStyledAttributes.recycle();
        c0118d7.a();
        return c0118d7;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int childCount = getChildCount();
        boolean zIsInEditMode = isInEditMode();
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            C0118d7 c0118d7 = (C0118d7) childAt.getLayoutParams();
            C0425n7 c0425n7 = c0118d7.k0;
            if (childAt.getVisibility() != 8 || c0118d7.Y || c0118d7.Z || zIsInEditMode) {
                int iM = c0425n7.m();
                int iN = c0425n7.n();
                childAt.layout(iM, iN, c0425n7.l() + iM, c0425n7.i() + iN);
            }
        }
        ArrayList arrayList = this.b;
        int size = arrayList.size();
        if (size > 0) {
            for (int i6 = 0; i6 < size; i6++) {
                ((AbstractC0056b7) arrayList.get(i6)).getClass();
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:114:0x0201  */
    /* JADX WARN: Code duplicated, block: B:156:0x030a  */
    /* JADX WARN: Code duplicated, block: B:158:0x0314  */
    /* JADX WARN: Code duplicated, block: B:161:0x0322  */
    /* JADX WARN: Code duplicated, block: B:168:0x0340  */
    /* JADX WARN: Code duplicated, block: B:170:0x034a  */
    /* JADX WARN: Code duplicated, block: B:171:0x035a  */
    /* JADX WARN: Code duplicated, block: B:173:0x0362  */
    /* JADX WARN: Code duplicated, block: B:178:0x0387  */
    /* JADX WARN: Code duplicated, block: B:181:0x038f  */
    /* JADX WARN: Code duplicated, block: B:183:0x0399  */
    /* JADX WARN: Code duplicated, block: B:186:0x03ab  */
    /* JADX WARN: Code duplicated, block: B:193:0x03ce  */
    /* JADX WARN: Code duplicated, block: B:201:0x041c  */
    /* JADX WARN: Code duplicated, block: B:204:0x0424  */
    /* JADX WARN: Code duplicated, block: B:278:0x0551  */
    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        boolean z;
        boolean z2;
        int i3;
        int i4;
        boolean z3;
        C0425n7 c0425n7;
        int i5;
        C0425n7 c0425n8;
        int i6;
        int i7;
        C0425n7 c0425n9;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        C0425n7 c0425n10;
        int i13;
        int i14;
        C0425n7 c0425n11;
        C0425n7 c0425n12;
        int i15;
        float f;
        View view;
        C0425n7 c0425n13;
        C0425n7 c0425n14;
        C0425n7 c0425n15;
        int i16;
        C0425n7 c0425n16;
        int i17;
        int i18;
        int i19;
        int i20;
        float fAbs;
        int i21;
        C0425n7 c0425n17;
        byte b;
        boolean z4;
        boolean z5;
        boolean z6;
        C0425n7 c0425n18;
        int i22 = 0;
        boolean z7 = true;
        boolean z8 = (getContext().getApplicationInfo().flags & 4194304) != 0 && 1 == getLayoutDirection();
        C0456o7 c0456o7 = this.c;
        c0456o7.h0 = z8;
        if (this.h) {
            this.h = false;
            int childCount = getChildCount();
            int i23 = 0;
            while (true) {
                if (i23 >= childCount) {
                    z = false;
                    break;
                } else {
                    if (getChildAt(i23).isLayoutRequested()) {
                        z = true;
                        break;
                    }
                    i23++;
                }
            }
            if (z) {
                boolean zIsInEditMode = isInEditMode();
                int childCount2 = getChildCount();
                for (int i24 = 0; i24 < childCount2; i24++) {
                    C0425n7 c0425n7B = b(getChildAt(i24));
                    if (c0425n7B != null) {
                        c0425n7B.s();
                    }
                }
                SparseArray sparseArray = this.a;
                if (zIsInEditMode) {
                    int i25 = 0;
                    while (i25 < childCount2) {
                        View childAt = getChildAt(i25);
                        try {
                            String resourceName = getResources().getResourceName(childAt.getId());
                            Integer numValueOf = Integer.valueOf(childAt.getId());
                            if (resourceName != null) {
                                z6 = z7;
                                try {
                                    if (this.l == null) {
                                        this.l = new HashMap();
                                    }
                                    int iIndexOf = resourceName.indexOf("/");
                                    this.l.put(iIndexOf != -1 ? resourceName.substring(iIndexOf + 1) : resourceName, numValueOf);
                                } catch (Resources.NotFoundException unused) {
                                }
                            } else {
                                z6 = z7;
                            }
                            int iIndexOf2 = resourceName.indexOf(47);
                            if (iIndexOf2 != -1) {
                                resourceName = resourceName.substring(iIndexOf2 + 1);
                            }
                            int id = childAt.getId();
                            if (id != 0) {
                                View viewFindViewById = (View) sparseArray.get(id);
                                if (viewFindViewById == null && (viewFindViewById = findViewById(id)) != null && viewFindViewById != this && viewFindViewById.getParent() == this) {
                                    onViewAdded(viewFindViewById);
                                }
                                c0425n18 = viewFindViewById == this ? c0456o7 : viewFindViewById == null ? null : ((C0118d7) viewFindViewById.getLayoutParams()).k0;
                            }
                            c0425n18.W = resourceName;
                        } catch (Resources.NotFoundException unused2) {
                            z6 = z7;
                        }
                        i25++;
                        z7 = z6;
                    }
                }
                boolean z9 = z7;
                if (this.k != -1) {
                    for (int i26 = 0; i26 < childCount2; i26++) {
                        getChildAt(i26).getId();
                    }
                }
                C0394m7 c0394m7 = this.j;
                if (c0394m7 != null) {
                    c0394m7.a(this);
                }
                c0456o7.d0.clear();
                ArrayList arrayList = this.b;
                int size = arrayList.size();
                if (size > 0) {
                    int i27 = 0;
                    while (i27 < size) {
                        AbstractC0056b7 abstractC0056b7 = (AbstractC0056b7) arrayList.get(i27);
                        if (abstractC0056b7.isInEditMode()) {
                            abstractC0056b7.f(abstractC0056b7.e);
                        }
                        AbstractC0463oe abstractC0463oe = abstractC0056b7.d;
                        if (abstractC0463oe == null) {
                            z4 = z;
                        } else {
                            abstractC0463oe.e0 = i22;
                            Arrays.fill(abstractC0463oe.d0, (Object) null);
                            int i28 = i22;
                            while (i28 < abstractC0056b7.b) {
                                int i29 = abstractC0056b7.a[i28];
                                View view2 = (View) this.a.get(i29);
                                if (view2 == null) {
                                    Integer numValueOf2 = Integer.valueOf(i29);
                                    HashMap map = abstractC0056b7.f;
                                    String str = (String) map.get(numValueOf2);
                                    z5 = z;
                                    int iD = abstractC0056b7.d(this, str);
                                    if (iD != 0) {
                                        abstractC0056b7.a[i28] = iD;
                                        map.put(Integer.valueOf(iD), str);
                                        view2 = (View) this.a.get(iD);
                                    }
                                } else {
                                    z5 = z;
                                }
                                if (view2 != null) {
                                    AbstractC0463oe abstractC0463oe2 = abstractC0056b7.d;
                                    C0425n7 c0425n7B2 = b(view2);
                                    abstractC0463oe2.getClass();
                                    if (c0425n7B2 != abstractC0463oe2 && c0425n7B2 != null) {
                                        int i30 = abstractC0463oe2.e0 + 1;
                                        C0425n7[] c0425n7Arr = abstractC0463oe2.d0;
                                        if (i30 > c0425n7Arr.length) {
                                            abstractC0463oe2.d0 = (C0425n7[]) Arrays.copyOf(c0425n7Arr, c0425n7Arr.length * 2);
                                        }
                                        C0425n7[] c0425n7Arr2 = abstractC0463oe2.d0;
                                        int i31 = abstractC0463oe2.e0;
                                        c0425n7Arr2[i31] = c0425n7B2;
                                        abstractC0463oe2.e0 = i31 + 1;
                                    }
                                }
                                i28++;
                                z = z5;
                            }
                            z4 = z;
                            abstractC0056b7.d.B();
                        }
                        i27++;
                        z = z4;
                        i22 = 0;
                    }
                }
                z2 = z;
                int i32 = 2;
                for (int i33 = 0; i33 < childCount2; i33++) {
                    getChildAt(i33);
                }
                SparseArray sparseArray2 = this.m;
                sparseArray2.clear();
                sparseArray2.put(0, c0456o7);
                sparseArray2.put(getId(), c0456o7);
                for (int i34 = 0; i34 < childCount2; i34++) {
                    View childAt2 = getChildAt(i34);
                    sparseArray2.put(childAt2.getId(), b(childAt2));
                }
                int i35 = 0;
                while (i35 < childCount2) {
                    View childAt3 = getChildAt(i35);
                    C0425n7 c0425n7B3 = b(childAt3);
                    if (c0425n7B3 == null) {
                        i4 = i35;
                        z3 = zIsInEditMode;
                        i18 = i32;
                    } else {
                        C0118d7 c0118d7 = (C0118d7) childAt3.getLayoutParams();
                        c0456o7.d0.add(c0425n7B3);
                        C0425n7 c0425n19 = c0425n7B3.I;
                        if (c0425n19 != null) {
                            ((C0456o7) c0425n19).d0.remove(c0425n7B3);
                            c0425n7B3.I = null;
                        }
                        c0425n7B3.I = c0456o7;
                        c0118d7.a();
                        c0425n7B3.V = childAt3.getVisibility();
                        c0425n7B3.U = childAt3;
                        if (childAt3 instanceof AbstractC0056b7) {
                            ((AbstractC0056b7) childAt3).e(c0425n7B3, c0456o7.h0);
                        }
                        if (c0118d7.Y) {
                            Xd xd = (Xd) c0425n7B3;
                            int i36 = c0118d7.h0;
                            int i37 = c0118d7.i0;
                            float f2 = c0118d7.j0;
                            if (f2 == -1.0f) {
                                b = -1;
                                if (i36 != -1) {
                                    if (i36 > -1) {
                                        xd.d0 = -1.0f;
                                        xd.e0 = i36;
                                        xd.f0 = -1;
                                    }
                                } else if (i37 != -1 && i37 > -1) {
                                    xd.d0 = -1.0f;
                                    xd.e0 = -1;
                                    xd.f0 = i37;
                                    i4 = i35;
                                    z3 = zIsInEditMode;
                                    i18 = i32;
                                }
                                i4 = i35;
                                z3 = zIsInEditMode;
                                i18 = i32;
                            } else if (f2 > -1.0f) {
                                xd.d0 = f2;
                                b = -1;
                                xd.e0 = -1;
                                xd.f0 = -1;
                                i4 = i35;
                                z3 = zIsInEditMode;
                                i18 = i32;
                            } else {
                                i4 = i35;
                                z3 = zIsInEditMode;
                                i18 = i32;
                            }
                        } else {
                            int i38 = c0118d7.a0;
                            int i39 = c0118d7.b0;
                            int i40 = c0118d7.c0;
                            int i41 = c0118d7.d0;
                            int i42 = c0118d7.e0;
                            i4 = i35;
                            int i43 = c0118d7.f0;
                            float f3 = c0118d7.g0;
                            int i44 = c0118d7.m;
                            z3 = zIsInEditMode;
                            if (i44 != -1) {
                                C0425n7 c0425n20 = (C0425n7) sparseArray2.get(i44);
                                if (c0425n20 != null) {
                                    float f4 = c0118d7.o;
                                    c0425n7B3.o(7, 7, c0118d7.n, 0, c0425n20);
                                    c0425n17 = c0425n7B3;
                                    c0425n17.v = f4;
                                } else {
                                    c0425n17 = c0425n7B3;
                                }
                                c0425n12 = c0425n17;
                            } else {
                                if (i38 != -1) {
                                    C0425n7 c0425n21 = (C0425n7) sparseArray2.get(i38);
                                    if (c0425n21 != null) {
                                        c0425n7 = c0425n7B3;
                                        i5 = 2;
                                        c0425n7.o(2, 2, ((ViewGroup.MarginLayoutParams) c0118d7).leftMargin, i42, c0425n21);
                                    } else {
                                        c0425n7 = c0425n7B3;
                                        i5 = 2;
                                    }
                                } else {
                                    c0425n7 = c0425n7B3;
                                    i5 = 2;
                                    if (i39 != -1 && (c0425n8 = (C0425n7) sparseArray2.get(i39)) != null) {
                                        c0425n7.o(2, 4, ((ViewGroup.MarginLayoutParams) c0118d7).leftMargin, i42, c0425n8);
                                        i6 = 2;
                                        i7 = 4;
                                    }
                                    if (i40 != -1) {
                                        c0425n16 = (C0425n7) sparseArray2.get(i40);
                                        if (c0425n16 != null) {
                                            c0425n7.o(i7, i6, ((ViewGroup.MarginLayoutParams) c0118d7).rightMargin, i43, c0425n16);
                                        }
                                    } else if (i41 != -1 && (c0425n9 = (C0425n7) sparseArray2.get(i41)) != null) {
                                        c0425n7.o(i7, i7, ((ViewGroup.MarginLayoutParams) c0118d7).rightMargin, i43, c0425n9);
                                    }
                                    i8 = c0118d7.h;
                                    if (i8 != -1) {
                                        c0425n15 = (C0425n7) sparseArray2.get(i8);
                                        if (c0425n15 != null) {
                                            i16 = 3;
                                            c0425n7.o(3, 3, ((ViewGroup.MarginLayoutParams) c0118d7).topMargin, c0118d7.u, c0425n15);
                                        } else {
                                            i16 = 3;
                                        }
                                        i11 = i16;
                                        i12 = 5;
                                        i10 = -1;
                                    } else {
                                        i9 = c0118d7.i;
                                        i10 = -1;
                                        if (i9 != -1 || (c0425n10 = (C0425n7) sparseArray2.get(i9)) == null) {
                                            i11 = 3;
                                            i12 = 5;
                                        } else {
                                            c0425n7.o(3, 5, ((ViewGroup.MarginLayoutParams) c0118d7).topMargin, c0118d7.u, c0425n10);
                                            i11 = 3;
                                            i12 = 5;
                                        }
                                    }
                                    i13 = c0118d7.j;
                                    if (i13 != i10) {
                                        c0425n14 = (C0425n7) sparseArray2.get(i13);
                                        if (c0425n14 != null) {
                                            c0425n7.o(i12, i11, ((ViewGroup.MarginLayoutParams) c0118d7).bottomMargin, c0118d7.w, c0425n14);
                                        }
                                    } else {
                                        i14 = c0118d7.k;
                                        if (i14 != i10 && (c0425n11 = (C0425n7) sparseArray2.get(i14)) != null) {
                                            c0425n7.o(i12, i12, ((ViewGroup.MarginLayoutParams) c0118d7).bottomMargin, c0118d7.w, c0425n11);
                                        }
                                    }
                                    c0425n12 = c0425n7;
                                    i15 = c0118d7.l;
                                    if (i15 != -1) {
                                        view = (View) sparseArray.get(i15);
                                        c0425n13 = (C0425n7) sparseArray2.get(c0118d7.l);
                                        if (c0425n13 != null && view != null && (view.getLayoutParams() instanceof C0118d7)) {
                                            C0118d7 c0118d8 = (C0118d7) view.getLayoutParams();
                                            boolean z10 = z9;
                                            c0118d7.X = z10;
                                            c0118d8.X = z10;
                                            c0425n12.g(6).b(c0425n13.g(6), 0, -1, z10);
                                            c0425n12.w = z10;
                                            c0118d8.k0.w = z10;
                                            c0425n12.g(3).h();
                                            c0425n12.g(5).h();
                                        }
                                    }
                                    if (f3 >= 0.0f) {
                                        c0425n12.S = f3;
                                    }
                                    f = c0118d7.A;
                                    if (f >= 0.0f) {
                                        c0425n12.T = f;
                                    }
                                }
                                i6 = i5;
                                i7 = 4;
                                if (i40 != -1) {
                                    c0425n16 = (C0425n7) sparseArray2.get(i40);
                                    if (c0425n16 != null) {
                                        c0425n7.o(i7, i6, ((ViewGroup.MarginLayoutParams) c0118d7).rightMargin, i43, c0425n16);
                                    }
                                } else if (i41 != -1) {
                                    c0425n7.o(i7, i7, ((ViewGroup.MarginLayoutParams) c0118d7).rightMargin, i43, c0425n9);
                                }
                                i8 = c0118d7.h;
                                if (i8 != -1) {
                                    c0425n15 = (C0425n7) sparseArray2.get(i8);
                                    if (c0425n15 != null) {
                                        i16 = 3;
                                        c0425n7.o(3, 3, ((ViewGroup.MarginLayoutParams) c0118d7).topMargin, c0118d7.u, c0425n15);
                                    } else {
                                        i16 = 3;
                                    }
                                    i11 = i16;
                                    i12 = 5;
                                    i10 = -1;
                                } else {
                                    i9 = c0118d7.i;
                                    i10 = -1;
                                    if (i9 != -1) {
                                        i11 = 3;
                                        i12 = 5;
                                    } else {
                                        i11 = 3;
                                        i12 = 5;
                                    }
                                }
                                i13 = c0118d7.j;
                                if (i13 != i10) {
                                    c0425n14 = (C0425n7) sparseArray2.get(i13);
                                    if (c0425n14 != null) {
                                        c0425n7.o(i12, i11, ((ViewGroup.MarginLayoutParams) c0118d7).bottomMargin, c0118d7.w, c0425n14);
                                    }
                                } else {
                                    i14 = c0118d7.k;
                                    if (i14 != i10) {
                                        c0425n7.o(i12, i12, ((ViewGroup.MarginLayoutParams) c0118d7).bottomMargin, c0118d7.w, c0425n11);
                                    }
                                }
                                c0425n12 = c0425n7;
                                i15 = c0118d7.l;
                                if (i15 != -1) {
                                    view = (View) sparseArray.get(i15);
                                    c0425n13 = (C0425n7) sparseArray2.get(c0118d7.l);
                                    if (c0425n13 != null) {
                                        C0118d7 c0118d9 = (C0118d7) view.getLayoutParams();
                                        boolean z11 = z9;
                                        c0118d7.X = z11;
                                        c0118d9.X = z11;
                                        c0425n12.g(6).b(c0425n13.g(6), 0, -1, z11);
                                        c0425n12.w = z11;
                                        c0118d9.k0.w = z11;
                                        c0425n12.g(3).h();
                                        c0425n12.g(5).h();
                                    }
                                }
                                if (f3 >= 0.0f) {
                                    c0425n12.S = f3;
                                }
                                f = c0118d7.A;
                                if (f >= 0.0f) {
                                    c0425n12.T = f;
                                }
                            }
                            if (z3 && ((i21 = c0118d7.P) != -1 || c0118d7.Q != -1)) {
                                int i45 = c0118d7.Q;
                                c0425n12.N = i21;
                                c0425n12.O = i45;
                            }
                            if (c0118d7.V) {
                                c0425n12.w(1);
                                c0425n12.y(((ViewGroup.MarginLayoutParams) c0118d7).width);
                                if (((ViewGroup.MarginLayoutParams) c0118d7).width == -2) {
                                    c0425n12.w(i32);
                                }
                            } else if (((ViewGroup.MarginLayoutParams) c0118d7).width == -1) {
                                if (c0118d7.S) {
                                    c0425n12.w(3);
                                } else {
                                    c0425n12.w(4);
                                }
                                c0425n12.g(2).e = ((ViewGroup.MarginLayoutParams) c0118d7).leftMargin;
                                c0425n12.g(4).e = ((ViewGroup.MarginLayoutParams) c0118d7).rightMargin;
                            } else {
                                c0425n12.w(3);
                                c0425n12.y(0);
                            }
                            if (c0118d7.W) {
                                i17 = -1;
                                c0425n12.x(1);
                                c0425n12.v(((ViewGroup.MarginLayoutParams) c0118d7).height);
                                if (((ViewGroup.MarginLayoutParams) c0118d7).height == -2) {
                                    c0425n12.x(2);
                                }
                            } else {
                                i17 = -1;
                                if (((ViewGroup.MarginLayoutParams) c0118d7).height == -1) {
                                    if (c0118d7.T) {
                                        c0425n12.x(3);
                                    } else {
                                        c0425n12.x(4);
                                    }
                                    c0425n12.g(3).e = ((ViewGroup.MarginLayoutParams) c0118d7).topMargin;
                                    c0425n12.g(5).e = ((ViewGroup.MarginLayoutParams) c0118d7).bottomMargin;
                                } else {
                                    c0425n12.x(3);
                                    c0425n12.v(0);
                                }
                            }
                            String str2 = c0118d7.B;
                            if (str2 == null || str2.length() == 0) {
                                c0425n12.L = 0.0f;
                            } else {
                                int length = str2.length();
                                int iIndexOf3 = str2.indexOf(44);
                                if (iIndexOf3 <= 0 || iIndexOf3 >= length - 1) {
                                    i19 = i17;
                                    i20 = 0;
                                } else {
                                    String strSubstring = str2.substring(0, iIndexOf3);
                                    i19 = strSubstring.equalsIgnoreCase("W") ? 0 : strSubstring.equalsIgnoreCase("H") ? 1 : i17;
                                    i20 = iIndexOf3 + 1;
                                }
                                int iIndexOf4 = str2.indexOf(58);
                                if (iIndexOf4 < 0 || iIndexOf4 >= length - 1) {
                                    String strSubstring2 = str2.substring(i20);
                                    if (strSubstring2.length() > 0) {
                                        fAbs = Float.parseFloat(strSubstring2);
                                    } else {
                                        fAbs = 0.0f;
                                    }
                                } else {
                                    String strSubstring3 = str2.substring(i20, iIndexOf4);
                                    String strSubstring4 = str2.substring(iIndexOf4 + 1);
                                    if (strSubstring3.length() <= 0 || strSubstring4.length() <= 0) {
                                        fAbs = 0.0f;
                                    } else {
                                        try {
                                            float f5 = Float.parseFloat(strSubstring3);
                                            float f6 = Float.parseFloat(strSubstring4);
                                            if (f5 <= 0.0f || f6 <= 0.0f) {
                                                fAbs = 0.0f;
                                            } else {
                                                fAbs = i19 == 1 ? Math.abs(f6 / f5) : Math.abs(f5 / f6);
                                            }
                                        } catch (NumberFormatException unused3) {
                                        }
                                    }
                                }
                                if (fAbs > 0.0f) {
                                    c0425n12.L = fAbs;
                                    c0425n12.M = i19;
                                }
                            }
                            float f7 = c0118d7.D;
                            float[] fArr = c0425n12.Z;
                            fArr[0] = f7;
                            fArr[1] = c0118d7.E;
                            c0425n12.X = c0118d7.F;
                            c0425n12.Y = c0118d7.G;
                            int i46 = c0118d7.H;
                            int i47 = c0118d7.J;
                            int i48 = c0118d7.L;
                            float f8 = c0118d7.N;
                            c0425n12.j = i46;
                            c0425n12.m = i47;
                            if (i48 == Integer.MAX_VALUE) {
                                i48 = 0;
                            }
                            c0425n12.n = i48;
                            c0425n12.o = f8;
                            if (f8 > 0.0f && f8 < 1.0f && i46 == 0) {
                                c0425n12.j = 2;
                            }
                            int i49 = c0118d7.I;
                            int i50 = c0118d7.K;
                            int i51 = c0118d7.M;
                            float f9 = c0118d7.O;
                            c0425n12.k = i49;
                            c0425n12.p = i50;
                            if (i51 == Integer.MAX_VALUE) {
                                i51 = 0;
                            }
                            c0425n12.q = i51;
                            c0425n12.r = f9;
                            if (f9 <= 0.0f || f9 >= 1.0f || i49 != 0) {
                                i18 = 2;
                            } else {
                                i18 = 2;
                                c0425n12.k = 2;
                            }
                        }
                    }
                    i32 = i18;
                    i35 = i4 + 1;
                    zIsInEditMode = z3;
                    z9 = true;
                }
            } else {
                z2 = z;
            }
            if (z2) {
                ArrayList arrayList2 = (ArrayList) c0456o7.e0.a;
                arrayList2.clear();
                int size2 = c0456o7.d0.size();
                for (int i52 = 0; i52 < size2; i52++) {
                    C0425n7 c0425n22 = (C0425n7) c0456o7.d0.get(i52);
                    int[] iArr = c0425n22.c0;
                    int i53 = iArr[0];
                    if (i53 == 3 || i53 == 4 || (i3 = iArr[1]) == 3 || i3 == 4) {
                        arrayList2.add(c0425n22);
                    }
                }
                c0456o7.f0.b = true;
            }
        }
        e(c0456o7, this.i, i, i2);
        int iL = c0456o7.l();
        int i54 = c0456o7.i();
        boolean z12 = c0456o7.q0;
        boolean z13 = c0456o7.r0;
        C0148e7 c0148e7 = this.n;
        int i55 = c0148e7.e;
        int iResolveSizeAndState = View.resolveSizeAndState(iL + c0148e7.d, i, 0);
        int iResolveSizeAndState2 = View.resolveSizeAndState(i54 + i55, i2, 0) & 16777215;
        int iMin = Math.min(this.f, iResolveSizeAndState & 16777215);
        int iMin2 = Math.min(this.g, iResolveSizeAndState2);
        if (z12) {
            iMin |= 16777216;
        }
        if (z13) {
            iMin2 |= 16777216;
        }
        setMeasuredDimension(iMin, iMin2);
    }

    @Override // android.view.ViewGroup
    public final void onViewAdded(View view) {
        super.onViewAdded(view);
        C0425n7 c0425n7B = b(view);
        if ((view instanceof Wd) && !(c0425n7B instanceof Xd)) {
            C0118d7 c0118d7 = (C0118d7) view.getLayoutParams();
            Xd xd = new Xd();
            c0118d7.k0 = xd;
            c0118d7.Y = true;
            xd.B(c0118d7.R);
        }
        if (view instanceof AbstractC0056b7) {
            AbstractC0056b7 abstractC0056b7 = (AbstractC0056b7) view;
            abstractC0056b7.h();
            ((C0118d7) view.getLayoutParams()).Z = true;
            ArrayList arrayList = this.b;
            if (!arrayList.contains(abstractC0056b7)) {
                arrayList.add(abstractC0056b7);
            }
        }
        this.a.put(view.getId(), view);
        this.h = true;
    }

    @Override // android.view.ViewGroup
    public void onViewRemoved(View view) {
        super.onViewRemoved(view);
        this.a.remove(view.getId());
        C0425n7 c0425n7B = b(view);
        this.c.d0.remove(c0425n7B);
        c0425n7B.I = null;
        this.b.remove(view);
        this.h = true;
    }

    @Override // android.view.View, android.view.ViewParent
    public final void requestLayout() {
        this.h = true;
        super.requestLayout();
    }

    @Override // android.view.View
    public final void setId(int i) {
        int id = getId();
        SparseArray sparseArray = this.a;
        sparseArray.remove(id);
        super.setId(i);
        sparseArray.put(getId(), this);
    }

    @Override // android.view.ViewGroup
    public final boolean shouldDelayChildPressedState() {
        return false;
    }

    public ConstraintLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.a = new SparseArray();
        this.b = new ArrayList(4);
        this.c = new C0456o7();
        this.d = 0;
        this.e = 0;
        this.f = Integer.MAX_VALUE;
        this.g = Integer.MAX_VALUE;
        this.h = true;
        this.i = 263;
        this.j = null;
        this.k = -1;
        this.l = new HashMap();
        this.m = new SparseArray();
        this.n = new C0148e7(this);
        c(attributeSet, i);
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        C0118d7 c0118d7 = new C0118d7(layoutParams);
        c0118d7.a = -1;
        c0118d7.b = -1;
        c0118d7.c = -1.0f;
        c0118d7.d = -1;
        c0118d7.e = -1;
        c0118d7.f = -1;
        c0118d7.g = -1;
        c0118d7.h = -1;
        c0118d7.i = -1;
        c0118d7.j = -1;
        c0118d7.k = -1;
        c0118d7.l = -1;
        c0118d7.m = -1;
        c0118d7.n = 0;
        c0118d7.o = 0.0f;
        c0118d7.p = -1;
        c0118d7.q = -1;
        c0118d7.r = -1;
        c0118d7.s = -1;
        c0118d7.t = -1;
        c0118d7.u = -1;
        c0118d7.v = -1;
        c0118d7.w = -1;
        c0118d7.x = -1;
        c0118d7.y = -1;
        c0118d7.z = 0.5f;
        c0118d7.A = 0.5f;
        c0118d7.B = null;
        c0118d7.C = 1;
        c0118d7.D = -1.0f;
        c0118d7.E = -1.0f;
        c0118d7.F = 0;
        c0118d7.G = 0;
        c0118d7.H = 0;
        c0118d7.I = 0;
        c0118d7.J = 0;
        c0118d7.K = 0;
        c0118d7.L = 0;
        c0118d7.M = 0;
        c0118d7.N = 1.0f;
        c0118d7.O = 1.0f;
        c0118d7.P = -1;
        c0118d7.Q = -1;
        c0118d7.R = -1;
        c0118d7.S = false;
        c0118d7.T = false;
        c0118d7.U = null;
        c0118d7.V = true;
        c0118d7.W = true;
        c0118d7.X = false;
        c0118d7.Y = false;
        c0118d7.Z = false;
        c0118d7.a0 = -1;
        c0118d7.b0 = -1;
        c0118d7.c0 = -1;
        c0118d7.d0 = -1;
        c0118d7.e0 = -1;
        c0118d7.f0 = -1;
        c0118d7.g0 = 0.5f;
        c0118d7.k0 = new C0425n7();
        return c0118d7;
    }
}
