package androidx.recyclerview.widget;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import java.util.WeakHashMap;
import rikka.shizuku.AbstractC0162el;
import rikka.shizuku.AbstractC0470ol;
import rikka.shizuku.AbstractC0535qo;
import rikka.shizuku.C0018a0;
import rikka.shizuku.C0141e0;
import rikka.shizuku.C0193fl;
import rikka.shizuku.C0285il;
import rikka.shizuku.C0408ml;
import rikka.shizuku.Fd;
import rikka.shizuku.Fg;
import rikka.shizuku.Gg;
import rikka.shizuku.Hg;
import rikka.shizuku.Hs;
import rikka.shizuku.Ig;
import rikka.shizuku.Md;
import rikka.shizuku.R2;
import rikka.shizuku.Yk;
import rikka.shizuku.Ys;

/* JADX INFO: loaded from: classes.dex */
public class GridLayoutManager extends LinearLayoutManager {
    public static final Set P = Collections.unmodifiableSet(new HashSet(Arrays.asList(17, 66, 33, 130)));
    public boolean E;
    public int F;
    public int[] G;
    public View[] H;
    public final SparseIntArray I;
    public final SparseIntArray J;
    public final R2 K;
    public final Rect L;
    public int M;
    public int N;
    public int O;

    public GridLayoutManager(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.E = false;
        this.F = -1;
        this.I = new SparseIntArray();
        this.J = new SparseIntArray();
        this.K = new R2(16);
        this.L = new Rect();
        this.M = -1;
        this.N = -1;
        this.O = -1;
        s1(AbstractC0162el.H(context, attributeSet, i, i2).b);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, rikka.shizuku.AbstractC0162el
    public final boolean C0() {
        return this.z == null && !this.E;
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager
    public final void E0(C0408ml c0408ml, Hg hg, Fd fd) {
        int i;
        int i2 = this.F;
        for (int i3 = 0; i3 < this.F && (i = hg.d) >= 0 && i < c0408ml.b() && i2 > 0; i3++) {
            fd.a(hg.d, Math.max(0, hg.g));
            this.K.getClass();
            i2--;
            hg.d += hg.e;
        }
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final int I(C0285il c0285il, C0408ml c0408ml) {
        if (this.p == 0) {
            return Math.min(this.F, B());
        }
        if (c0408ml.b() < 1) {
            return 0;
        }
        return o1(c0408ml.b() - 1, c0285il, c0408ml) + 1;
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager
    public final View Q0(C0285il c0285il, C0408ml c0408ml, boolean z, boolean z2) {
        int i;
        int iV;
        int iV2 = v();
        int i2 = 1;
        if (z2) {
            iV = v() - 1;
            i = -1;
            i2 = -1;
        } else {
            i = iV2;
            iV = 0;
        }
        int iB = c0408ml.b();
        J0();
        int iK = this.r.k();
        int iG = this.r.g();
        View view = null;
        View view2 = null;
        while (iV != i) {
            View viewU = u(iV);
            int iG2 = AbstractC0162el.G(viewU);
            if (iG2 >= 0 && iG2 < iB && p1(iG2, c0285il, c0408ml) == 0) {
                if (((C0193fl) viewU.getLayoutParams()).a.h()) {
                    if (view2 == null) {
                        view2 = viewU;
                    }
                } else {
                    if (this.r.e(viewU) < iG && this.r.b(viewU) >= iK) {
                        return viewU;
                    }
                    if (view == null) {
                        view = viewU;
                    }
                }
            }
            iV += i2;
        }
        return view != null ? view : view2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:62:0x00e0, code lost:
    
        if (r13 == (r2 > r15)) goto L57;
     */
    @Override // androidx.recyclerview.widget.LinearLayoutManager, rikka.shizuku.AbstractC0162el
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final View T(View view, int i, C0285il c0285il, C0408ml c0408ml) {
        View viewB;
        int iV;
        int i2;
        int iV2;
        View view2;
        View view3;
        int i3;
        int i4;
        C0285il c0285il2 = c0285il;
        C0408ml c0408ml2 = c0408ml;
        RecyclerView recyclerView = this.b;
        if (recyclerView == null || (viewB = recyclerView.B(view)) == null || this.a.c.contains(viewB)) {
            viewB = null;
        }
        if (viewB != null) {
            Md md = (Md) viewB.getLayoutParams();
            int i5 = md.e;
            int i6 = md.f + i5;
            if (super.T(view, i, c0285il, c0408ml) != null) {
                if ((I0(i) == 1) != this.u) {
                    iV2 = v() - 1;
                    iV = -1;
                    i2 = -1;
                } else {
                    iV = v();
                    i2 = 1;
                    iV2 = 0;
                }
                boolean z = this.p == 1 && V0();
                int iO1 = o1(iV2, c0285il2, c0408ml2);
                View view4 = null;
                int i7 = -1;
                int i8 = -1;
                int iMin = 0;
                int i9 = iV2;
                int iMin2 = 0;
                View view5 = null;
                while (true) {
                    view2 = view5;
                    if (i9 == iV) {
                        break;
                    }
                    int iO2 = o1(i9, c0285il2, c0408ml2);
                    View viewU = u(i9);
                    if (viewU == viewB) {
                        break;
                    }
                    if (!viewU.hasFocusable() || iO2 == iO1) {
                        Md md2 = (Md) viewU.getLayoutParams();
                        int i10 = md2.e;
                        view3 = viewB;
                        int i11 = md2.f + i10;
                        if (viewU.hasFocusable() && i10 == i5 && i11 == i6) {
                            return viewU;
                        }
                        if (!(viewU.hasFocusable() && view4 == null) && (viewU.hasFocusable() || view2 != null)) {
                            i3 = iV;
                            int iMin3 = Math.min(i11, i6) - Math.max(i10, i5);
                            if (viewU.hasFocusable()) {
                                if (iMin3 <= iMin) {
                                    if (iMin3 == iMin) {
                                    }
                                    i4 = iMin;
                                }
                                i4 = iMin;
                            } else if (view4 == null) {
                                i4 = iMin;
                                if (!this.c.G(viewU) || !this.d.G(viewU)) {
                                    if (iMin3 <= iMin2) {
                                        if (iMin3 == iMin2) {
                                            if (z == (i10 > i7)) {
                                            }
                                        }
                                    }
                                }
                            } else {
                                i4 = iMin;
                            }
                            i9 += i2;
                            c0285il2 = c0285il;
                            c0408ml2 = c0408ml;
                            viewB = view3;
                            iV = i3;
                        } else {
                            i4 = iMin;
                            i3 = iV;
                        }
                        if (viewU.hasFocusable()) {
                            int i12 = md2.e;
                            iMin = Math.min(i11, i6) - Math.max(i10, i5);
                            view4 = viewU;
                            i8 = i12;
                            view5 = view2;
                        } else {
                            int i13 = md2.e;
                            view5 = viewU;
                            i7 = i13;
                            iMin = i4;
                            iMin2 = Math.min(i11, i6) - Math.max(i10, i5);
                        }
                        i9 += i2;
                        c0285il2 = c0285il;
                        c0408ml2 = c0408ml;
                        viewB = view3;
                        iV = i3;
                    } else {
                        if (view4 != null) {
                            break;
                        }
                        view3 = viewB;
                        i4 = iMin;
                        i3 = iV;
                    }
                    view5 = view2;
                    iMin = i4;
                    i9 += i2;
                    c0285il2 = c0285il;
                    c0408ml2 = c0408ml;
                    viewB = view3;
                    iV = i3;
                }
                return view4 != null ? view4 : view2;
            }
        }
        return null;
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, rikka.shizuku.AbstractC0162el
    public final void V(C0285il c0285il, C0408ml c0408ml, C0141e0 c0141e0) {
        super.V(c0285il, c0408ml, c0141e0);
        c0141e0.i(GridView.class.getName());
        Yk yk = this.b.m;
        if (yk == null || yk.a() <= 1) {
            return;
        }
        c0141e0.b(C0018a0.n);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v22 */
    /* JADX WARN: Type inference failed for: r8v23, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r8v31 */
    /* JADX WARN: Type inference failed for: r8v32 */
    /* JADX WARN: Type inference failed for: r8v37 */
    @Override // androidx.recyclerview.widget.LinearLayoutManager
    public final void W0(C0285il c0285il, C0408ml c0408ml, Hg hg, Gg gg) {
        int i;
        int i2;
        int i3;
        int iD;
        int iD2;
        int iF;
        int iD3;
        int iW;
        int iW2;
        ?? r8;
        int i4;
        View viewB;
        int iJ = this.r.j();
        boolean z = iJ != 1073741824;
        int i5 = v() > 0 ? this.G[this.F] : 0;
        if (z) {
            t1();
        }
        boolean z2 = hg.e == 1;
        int iP1 = this.F;
        if (!z2) {
            iP1 = p1(hg.d, c0285il, c0408ml) + q1(hg.d, c0285il, c0408ml);
        }
        int i6 = 0;
        while (i6 < this.F && (i4 = hg.d) >= 0 && i4 < c0408ml.b() && iP1 > 0) {
            int i7 = hg.d;
            int iQ1 = q1(i7, c0285il, c0408ml);
            if (iQ1 > this.F) {
                throw new IllegalArgumentException("Item at position " + i7 + " requires " + iQ1 + " spans but GridLayoutManager has only " + this.F + " spans.");
            }
            iP1 -= iQ1;
            if (iP1 < 0 || (viewB = hg.b(c0285il)) == null) {
                break;
            }
            this.H[i6] = viewB;
            i6++;
        }
        if (i6 == 0) {
            gg.b = true;
            return;
        }
        if (z2) {
            i3 = 1;
            i2 = i6;
            i = 0;
        } else {
            i = i6 - 1;
            i2 = -1;
            i3 = -1;
        }
        int i8 = 0;
        while (i != i2) {
            View view = this.H[i];
            Md md = (Md) view.getLayoutParams();
            int iQ2 = q1(AbstractC0162el.G(view), c0285il, c0408ml);
            md.f = iQ2;
            md.e = i8;
            i8 += iQ2;
            i += i3;
        }
        float f = 0.0f;
        int i9 = 0;
        for (int i10 = 0; i10 < i6; i10++) {
            View view2 = this.H[i10];
            if (hg.k != null) {
                r8 = 0;
                r8 = 0;
                if (z2) {
                    b(view2, -1, true);
                } else {
                    b(view2, 0, true);
                }
            } else if (z2) {
                r8 = 0;
                b(view2, -1, false);
            } else {
                r8 = 0;
                b(view2, 0, false);
            }
            RecyclerView recyclerView = this.b;
            Rect rect = this.L;
            if (recyclerView == null) {
                rect.set(r8, r8, r8, r8);
            } else {
                rect.set(recyclerView.L(view2));
            }
            r1(view2, iJ, r8);
            int iC = this.r.c(view2);
            if (iC > i9) {
                i9 = iC;
            }
            float fD = (this.r.d(view2) * 1.0f) / ((Md) view2.getLayoutParams()).f;
            if (fD > f) {
                f = fD;
            }
        }
        if (z) {
            h1(Math.max(Math.round(f * this.F), i5));
            i9 = 0;
            for (int i11 = 0; i11 < i6; i11++) {
                View view3 = this.H[i11];
                r1(view3, 1073741824, true);
                int iC2 = this.r.c(view3);
                if (iC2 > i9) {
                    i9 = iC2;
                }
            }
        }
        for (int i12 = 0; i12 < i6; i12++) {
            View view4 = this.H[i12];
            if (this.r.c(view4) != i9) {
                Md md2 = (Md) view4.getLayoutParams();
                Rect rect2 = md2.b;
                int i13 = rect2.top + rect2.bottom + ((ViewGroup.MarginLayoutParams) md2).topMargin + ((ViewGroup.MarginLayoutParams) md2).bottomMargin;
                int i14 = rect2.left + rect2.right + ((ViewGroup.MarginLayoutParams) md2).leftMargin + ((ViewGroup.MarginLayoutParams) md2).rightMargin;
                int iN1 = n1(md2.e, md2.f);
                if (this.p == 1) {
                    iW2 = AbstractC0162el.w(false, iN1, 1073741824, i14, ((ViewGroup.MarginLayoutParams) md2).width);
                    iW = View.MeasureSpec.makeMeasureSpec(i9 - i13, 1073741824);
                } else {
                    int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i9 - i14, 1073741824);
                    iW = AbstractC0162el.w(false, iN1, 1073741824, i13, ((ViewGroup.MarginLayoutParams) md2).height);
                    iW2 = iMakeMeasureSpec;
                }
                if (z0(view4, iW2, iW, (C0193fl) view4.getLayoutParams())) {
                    view4.measure(iW2, iW);
                }
            }
        }
        gg.a = i9;
        if (this.p != 1) {
            if (hg.f == -1) {
                int i15 = hg.b;
                iD2 = i15 - i9;
                iD = i15;
            } else {
                int i16 = hg.b;
                iD = i16 + i9;
                iD2 = i16;
            }
            iF = 0;
            iD3 = 0;
        } else if (hg.f == -1) {
            iD3 = hg.b;
            iF = iD3 - i9;
            iD2 = 0;
            iD = 0;
        } else {
            int i17 = hg.b;
            iF = i17;
            iD = 0;
            iD3 = i17 + i9;
            iD2 = 0;
        }
        for (int i18 = 0; i18 < i6; i18++) {
            View view5 = this.H[i18];
            Md md3 = (Md) view5.getLayoutParams();
            if (this.p != 1) {
                iF = F() + this.G[md3.e];
                iD3 = this.r.d(view5) + iF;
            } else if (V0()) {
                int iD4 = D() + this.G[this.F - md3.e];
                iD = iD4;
                iD2 = iD4 - this.r.d(view5);
            } else {
                iD2 = D() + this.G[md3.e];
                iD = this.r.d(view5) + iD2;
            }
            AbstractC0162el.N(view5, iD2, iF, iD, iD3);
            if (md3.a.h() || md3.a.k()) {
                gg.c = true;
            }
            gg.d = view5.hasFocusable() | gg.d;
        }
        Arrays.fill(this.H, (Object) null);
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final void X(C0285il c0285il, C0408ml c0408ml, View view, C0141e0 c0141e0) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (!(layoutParams instanceof Md)) {
            W(view, c0141e0);
            return;
        }
        Md md = (Md) layoutParams;
        int iO1 = o1(md.a.b(), c0285il, c0408ml);
        if (this.p == 0) {
            c0141e0.j(Ys.z(false, md.e, md.f, iO1, 1));
        } else {
            c0141e0.j(Ys.z(false, iO1, 1, md.e, md.f));
        }
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager
    public final void X0(C0285il c0285il, C0408ml c0408ml, Fg fg, int i) {
        t1();
        if (c0408ml.b() > 0 && !c0408ml.g) {
            boolean z = i == 1;
            int iP1 = p1(fg.b, c0285il, c0408ml);
            if (z) {
                while (iP1 > 0) {
                    int i2 = fg.b;
                    if (i2 <= 0) {
                        break;
                    }
                    int i3 = i2 - 1;
                    fg.b = i3;
                    iP1 = p1(i3, c0285il, c0408ml);
                }
            } else {
                int iB = c0408ml.b() - 1;
                int i4 = fg.b;
                while (i4 < iB) {
                    int i5 = i4 + 1;
                    int iP2 = p1(i5, c0285il, c0408ml);
                    if (iP2 <= iP1) {
                        break;
                    }
                    i4 = i5;
                    iP1 = iP2;
                }
                fg.b = i4;
            }
        }
        i1();
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final void Y(int i, int i2) {
        R2 r2 = this.K;
        r2.F();
        ((SparseIntArray) r2.c).clear();
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final void Z() {
        R2 r2 = this.K;
        r2.F();
        ((SparseIntArray) r2.c).clear();
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final void a0(int i, int i2) {
        R2 r2 = this.K;
        r2.F();
        ((SparseIntArray) r2.c).clear();
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final void b0(int i, int i2) {
        R2 r2 = this.K;
        r2.F();
        ((SparseIntArray) r2.c).clear();
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final void c0(int i, int i2) {
        R2 r2 = this.K;
        r2.F();
        ((SparseIntArray) r2.c).clear();
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, rikka.shizuku.AbstractC0162el
    public final void d0(C0285il c0285il, C0408ml c0408ml) {
        boolean z = c0408ml.g;
        SparseIntArray sparseIntArray = this.J;
        SparseIntArray sparseIntArray2 = this.I;
        if (z) {
            int iV = v();
            for (int i = 0; i < iV; i++) {
                Md md = (Md) u(i).getLayoutParams();
                int iB = md.a.b();
                sparseIntArray2.put(iB, md.f);
                sparseIntArray.put(iB, md.e);
            }
        }
        super.d0(c0285il, c0408ml);
        sparseIntArray2.clear();
        sparseIntArray.clear();
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager
    public final void d1(boolean z) {
        if (z) {
            throw new UnsupportedOperationException("GridLayoutManager does not support stack from end. Consider using reverse layout");
        }
        super.d1(false);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, rikka.shizuku.AbstractC0162el
    public final void e0(C0408ml c0408ml) {
        View viewQ;
        super.e0(c0408ml);
        this.E = false;
        int i = this.M;
        if (i == -1 || (viewQ = q(i)) == null) {
            return;
        }
        viewQ.sendAccessibilityEvent(67108864);
        this.M = -1;
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final boolean f(C0193fl c0193fl) {
        return c0193fl instanceof Md;
    }

    public final void h1(int i) {
        int i2;
        int[] iArr = this.G;
        int i3 = this.F;
        if (iArr == null || iArr.length != i3 + 1 || iArr[iArr.length - 1] != i) {
            iArr = new int[i3 + 1];
        }
        int i4 = 0;
        iArr[0] = 0;
        int i5 = i / i3;
        int i6 = i % i3;
        int i7 = 0;
        for (int i8 = 1; i8 <= i3; i8++) {
            i4 += i6;
            if (i4 <= 0 || i3 - i4 >= i6) {
                i2 = i5;
            } else {
                i2 = i5 + 1;
                i4 -= i3;
            }
            i7 += i2;
            iArr[i8] = i7;
        }
        this.G = iArr;
    }

    /* JADX WARN: Code duplicated, block: B:122:0x01a4  */
    /* JADX WARN: Code duplicated, block: B:125:0x01aa  */
    /* JADX WARN: Code duplicated, block: B:126:0x01ac A[EDGE_INSN: B:126:0x01ac->B:170:0x027f BREAK  A[LOOP:2: B:130:0x01bc->B:139:0x01e5]] */
    /* JADX WARN: Code duplicated, block: B:127:0x01af A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:132:0x01c2  */
    /* JADX WARN: Code duplicated, block: B:135:0x01d0  */
    /* JADX WARN: Code duplicated, block: B:138:0x01dd A[LOOP:3: B:133:0x01ca->B:138:0x01dd, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:143:0x01f6  */
    /* JADX WARN: Code duplicated, block: B:146:0x0216  */
    /* JADX WARN: Code duplicated, block: B:147:0x0218  */
    /* JADX WARN: Code duplicated, block: B:149:0x021b A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:154:0x022a  */
    /* JADX WARN: Code duplicated, block: B:157:0x0238  */
    /* JADX WARN: Code duplicated, block: B:160:0x0246  */
    /* JADX WARN: Code duplicated, block: B:167:0x0265  */
    /* JADX WARN: Code duplicated, block: B:171:0x0281  */
    /* JADX WARN: Code duplicated, block: B:213:0x01e8 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:214:0x01ac A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:215:0x01e5 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:216:0x0202 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:217:0x01ac A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:218:? A[LOOP:4: B:141:0x01f0->B:218:?, LOOP_END, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:219:0x0257 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:220:0x01ac A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:221:0x024c A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:222:0x0254 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:224:0x0232 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:226:0x01ac A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:227:0x0271 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:228:? A[LOOP:7: B:165:0x025f->B:228:?, LOOP_END, SYNTHETIC] */
    @Override // androidx.recyclerview.widget.LinearLayoutManager, rikka.shizuku.AbstractC0162el
    public final boolean i0(int i, Bundle bundle) {
        View viewU;
        AbstractC0470ol abstractC0470olJ;
        int iIntValue;
        int i2;
        TreeMap treeMap;
        int i3;
        Iterator it;
        Integer num;
        int iIntValue2;
        Iterator it2;
        Integer num2;
        TreeMap treeMap2;
        int i4;
        Iterator it3;
        Integer num3;
        int iIntValue3;
        Iterator it4;
        Integer num4;
        if (i == C0018a0.n.a() && i != -1) {
            int i5 = 0;
            while (true) {
                if (i5 >= v()) {
                    viewU = null;
                    break;
                }
                if (u(i5).isAccessibilityFocused()) {
                    viewU = u(i5);
                    break;
                }
                i5++;
            }
            if (viewU != null && bundle != null) {
                int i6 = bundle.getInt("android.view.accessibility.action.ARGUMENT_DIRECTION_INT", -1);
                if (P.contains(Integer.valueOf(i6)) && (abstractC0470olJ = this.b.J(viewU)) != null) {
                    RecyclerView recyclerView = abstractC0470olJ.r;
                    int iH = recyclerView == null ? -1 : recyclerView.H(abstractC0470olJ);
                    int iK1 = k1(iH);
                    int iJ1 = j1(iH);
                    if (iK1 >= 0 && iJ1 >= 0) {
                        if (!l1(iH).contains(Integer.valueOf(this.N)) || !m1(j1(iH), iH).contains(Integer.valueOf(this.O))) {
                            this.N = iK1;
                            this.O = iJ1;
                        }
                        int i7 = this.N;
                        if (i7 == -1) {
                            i7 = iK1;
                        }
                        int i8 = this.O;
                        if (i8 != -1) {
                            iJ1 = i8;
                        }
                        if (i6 == 17) {
                            iIntValue = iH - 1;
                            while (true) {
                                if (iIntValue >= 0) {
                                    int iK2 = k1(iIntValue);
                                    int iJ2 = j1(iIntValue);
                                    if (iK2 >= 0 && iJ2 >= 0) {
                                        if (this.p != 1) {
                                            if (l1(iIntValue).contains(Integer.valueOf(i7)) && iJ2 < iJ1) {
                                                this.O = iJ2;
                                                break;
                                            }
                                            iIntValue--;
                                        } else {
                                            if ((iK2 == i7 && iJ2 < iJ1) || iK2 < i7) {
                                                this.N = iK2;
                                                this.O = iJ2;
                                                break;
                                            }
                                            iIntValue--;
                                        }
                                    }
                                }
                                iIntValue = -1;
                                break;
                            }
                            if (iIntValue == -1) {
                                if (i6 == 17) {
                                    if (i6 == 66) {
                                        if (iK1 < 0) {
                                            treeMap = new TreeMap();
                                            i3 = 0;
                                            loop5: while (true) {
                                                if (i3 < B()) {
                                                    it2 = l1(i3).iterator();
                                                    while (true) {
                                                        if (it2.hasNext()) {
                                                            num2 = (Integer) it2.next();
                                                            if (num2.intValue() < 0) {
                                                                if (!treeMap.containsKey(num2)) {
                                                                    treeMap.put(num2, Integer.valueOf(i3));
                                                                }
                                                            }
                                                        } else {
                                                            i3++;
                                                        }
                                                    }
                                                } else {
                                                    it = treeMap.keySet().iterator();
                                                    while (true) {
                                                        if (it.hasNext()) {
                                                            num = (Integer) it.next();
                                                            iIntValue2 = num.intValue();
                                                            if (iIntValue2 > iK1) {
                                                                iIntValue = ((Integer) treeMap.get(num)).intValue();
                                                                this.N = iIntValue2;
                                                                this.O = 0;
                                                                break;
                                                            }
                                                        }
                                                    }
                                                }
                                                iIntValue = -1;
                                                break;
                                            }
                                        }
                                        iIntValue = -1;
                                        break;
                                    }
                                } else {
                                    if (iK1 < 0) {
                                        treeMap2 = new TreeMap(Collections.reverseOrder());
                                        i4 = 0;
                                        loop2: while (true) {
                                            if (i4 < B()) {
                                                it4 = l1(i4).iterator();
                                                while (true) {
                                                    if (it4.hasNext()) {
                                                        num4 = (Integer) it4.next();
                                                        if (num4.intValue() < 0) {
                                                            treeMap2.put(num4, Integer.valueOf(i4));
                                                        }
                                                    } else {
                                                        i4++;
                                                    }
                                                }
                                            } else {
                                                it3 = treeMap2.keySet().iterator();
                                                while (true) {
                                                    if (it3.hasNext()) {
                                                        num3 = (Integer) it3.next();
                                                        iIntValue3 = num3.intValue();
                                                        if (iIntValue3 < iK1) {
                                                            iIntValue = ((Integer) treeMap2.get(num3)).intValue();
                                                            this.N = iIntValue3;
                                                            this.O = j1(iIntValue);
                                                            break;
                                                        }
                                                    }
                                                }
                                            }
                                            iIntValue = -1;
                                            break;
                                        }
                                    }
                                    iIntValue = -1;
                                    break;
                                }
                            }
                            if (iIntValue != -1) {
                                q0(iIntValue);
                                this.M = iIntValue;
                                return true;
                            }
                        } else if (i6 == 33) {
                            iIntValue = iH - 1;
                            while (true) {
                                if (iIntValue >= 0) {
                                    int iK3 = k1(iIntValue);
                                    int iJ3 = j1(iIntValue);
                                    if (iK3 >= 0 && iJ3 >= 0) {
                                        if (this.p != 1) {
                                            if (iK3 < i7 && iJ3 == iJ1) {
                                                this.N = ((Integer) Collections.max(l1(iIntValue))).intValue();
                                                break;
                                            }
                                            iIntValue--;
                                        } else {
                                            if (iK3 < i7 && m1(j1(iIntValue), iIntValue).contains(Integer.valueOf(iJ1))) {
                                                this.N = iK3;
                                                break;
                                            }
                                            iIntValue--;
                                        }
                                    }
                                }
                                iIntValue = -1;
                                break;
                            }
                            if (iIntValue == -1) {
                                if (i6 == 17) {
                                    if (i6 == 66) {
                                        if (iK1 < 0) {
                                            treeMap = new TreeMap();
                                            i3 = 0;
                                            loop5: while (true) {
                                                if (i3 < B()) {
                                                    it2 = l1(i3).iterator();
                                                    while (true) {
                                                        if (it2.hasNext()) {
                                                            num2 = (Integer) it2.next();
                                                            if (num2.intValue() < 0) {
                                                                if (!treeMap.containsKey(num2)) {
                                                                    treeMap.put(num2, Integer.valueOf(i3));
                                                                }
                                                            }
                                                        } else {
                                                            i3++;
                                                        }
                                                    }
                                                } else {
                                                    it = treeMap.keySet().iterator();
                                                    while (true) {
                                                        if (it.hasNext()) {
                                                            num = (Integer) it.next();
                                                            iIntValue2 = num.intValue();
                                                            if (iIntValue2 > iK1) {
                                                                iIntValue = ((Integer) treeMap.get(num)).intValue();
                                                                this.N = iIntValue2;
                                                                this.O = 0;
                                                                break;
                                                            }
                                                        }
                                                    }
                                                }
                                                iIntValue = -1;
                                                break;
                                            }
                                        }
                                        iIntValue = -1;
                                        break;
                                    }
                                } else {
                                    if (iK1 < 0) {
                                        treeMap2 = new TreeMap(Collections.reverseOrder());
                                        i4 = 0;
                                        loop2: while (true) {
                                            if (i4 < B()) {
                                                it4 = l1(i4).iterator();
                                                while (true) {
                                                    if (it4.hasNext()) {
                                                        num4 = (Integer) it4.next();
                                                        if (num4.intValue() < 0) {
                                                            treeMap2.put(num4, Integer.valueOf(i4));
                                                        }
                                                    } else {
                                                        i4++;
                                                    }
                                                }
                                            } else {
                                                it3 = treeMap2.keySet().iterator();
                                                while (true) {
                                                    if (it3.hasNext()) {
                                                        num3 = (Integer) it3.next();
                                                        iIntValue3 = num3.intValue();
                                                        if (iIntValue3 < iK1) {
                                                            iIntValue = ((Integer) treeMap2.get(num3)).intValue();
                                                            this.N = iIntValue3;
                                                            this.O = j1(iIntValue);
                                                            break;
                                                        }
                                                    }
                                                }
                                            }
                                            iIntValue = -1;
                                            break;
                                        }
                                    }
                                    iIntValue = -1;
                                    break;
                                }
                            }
                            if (iIntValue != -1) {
                                q0(iIntValue);
                                this.M = iIntValue;
                                return true;
                            }
                        } else if (i6 == 66) {
                            iIntValue = iH + 1;
                            while (true) {
                                if (iIntValue < B()) {
                                    int iK4 = k1(iIntValue);
                                    int iJ4 = j1(iIntValue);
                                    if (iK4 >= 0 && iJ4 >= 0) {
                                        if (this.p != 1) {
                                            if (iJ4 > iJ1 && l1(iIntValue).contains(Integer.valueOf(i7))) {
                                                this.O = iJ4;
                                                break;
                                            }
                                            iIntValue++;
                                        } else {
                                            if ((iK4 == i7 && iJ4 > iJ1) || iK4 > i7) {
                                                this.N = iK4;
                                                this.O = iJ4;
                                                break;
                                            }
                                            iIntValue++;
                                        }
                                    }
                                }
                                iIntValue = -1;
                                break;
                            }
                            if (iIntValue == -1) {
                                if (i6 == 17) {
                                    if (i6 == 66) {
                                        if (iK1 < 0) {
                                            treeMap = new TreeMap();
                                            i3 = 0;
                                            loop5: while (true) {
                                                if (i3 < B()) {
                                                    it2 = l1(i3).iterator();
                                                    while (true) {
                                                        if (it2.hasNext()) {
                                                            num2 = (Integer) it2.next();
                                                            if (num2.intValue() < 0) {
                                                                if (!treeMap.containsKey(num2)) {
                                                                    treeMap.put(num2, Integer.valueOf(i3));
                                                                }
                                                            }
                                                        } else {
                                                            i3++;
                                                        }
                                                    }
                                                } else {
                                                    it = treeMap.keySet().iterator();
                                                    while (true) {
                                                        if (it.hasNext()) {
                                                            num = (Integer) it.next();
                                                            iIntValue2 = num.intValue();
                                                            if (iIntValue2 > iK1) {
                                                                iIntValue = ((Integer) treeMap.get(num)).intValue();
                                                                this.N = iIntValue2;
                                                                this.O = 0;
                                                                break;
                                                            }
                                                        }
                                                    }
                                                }
                                                iIntValue = -1;
                                                break;
                                            }
                                        }
                                        iIntValue = -1;
                                        break;
                                    }
                                } else {
                                    if (iK1 < 0) {
                                        treeMap2 = new TreeMap(Collections.reverseOrder());
                                        i4 = 0;
                                        loop2: while (true) {
                                            if (i4 < B()) {
                                                it4 = l1(i4).iterator();
                                                while (true) {
                                                    if (it4.hasNext()) {
                                                        num4 = (Integer) it4.next();
                                                        if (num4.intValue() < 0) {
                                                            treeMap2.put(num4, Integer.valueOf(i4));
                                                        }
                                                    } else {
                                                        i4++;
                                                    }
                                                }
                                            } else {
                                                it3 = treeMap2.keySet().iterator();
                                                while (true) {
                                                    if (it3.hasNext()) {
                                                        num3 = (Integer) it3.next();
                                                        iIntValue3 = num3.intValue();
                                                        if (iIntValue3 < iK1) {
                                                            iIntValue = ((Integer) treeMap2.get(num3)).intValue();
                                                            this.N = iIntValue3;
                                                            this.O = j1(iIntValue);
                                                            break;
                                                        }
                                                    }
                                                }
                                            }
                                            iIntValue = -1;
                                            break;
                                        }
                                    }
                                    iIntValue = -1;
                                    break;
                                }
                            }
                            if (iIntValue != -1) {
                                q0(iIntValue);
                                this.M = iIntValue;
                                return true;
                            }
                        } else if (i6 == 130) {
                            iIntValue = iH + 1;
                            while (true) {
                                if (iIntValue < B()) {
                                    int iK5 = k1(iIntValue);
                                    int iJ5 = j1(iIntValue);
                                    if (iK5 >= 0 && iJ5 >= 0) {
                                        if (this.p != 1) {
                                            if (iK5 > i7 && iJ5 == iJ1) {
                                                this.N = k1(iIntValue);
                                                break;
                                            }
                                            iIntValue++;
                                        } else {
                                            if (iK5 > i7 && (iJ5 == iJ1 || m1(j1(iIntValue), iIntValue).contains(Integer.valueOf(iJ1)))) {
                                                this.N = iK5;
                                                break;
                                            }
                                            iIntValue++;
                                        }
                                    }
                                }
                                iIntValue = -1;
                                break;
                            }
                            if (iIntValue == -1 && (i2 = this.p) == 0) {
                                if (i6 == 17) {
                                    if (i6 == 66) {
                                        if (iK1 < 0 || i2 == 1) {
                                            iIntValue = -1;
                                            break;
                                        }
                                        treeMap = new TreeMap();
                                        i3 = 0;
                                        loop5: while (true) {
                                            if (i3 < B()) {
                                                it2 = l1(i3).iterator();
                                                while (true) {
                                                    if (it2.hasNext()) {
                                                        num2 = (Integer) it2.next();
                                                        if (num2.intValue() < 0) {
                                                            if (!treeMap.containsKey(num2)) {
                                                                treeMap.put(num2, Integer.valueOf(i3));
                                                            }
                                                        }
                                                    } else {
                                                        i3++;
                                                    }
                                                }
                                            } else {
                                                it = treeMap.keySet().iterator();
                                                while (true) {
                                                    if (it.hasNext()) {
                                                        num = (Integer) it.next();
                                                        iIntValue2 = num.intValue();
                                                        if (iIntValue2 > iK1) {
                                                            iIntValue = ((Integer) treeMap.get(num)).intValue();
                                                            this.N = iIntValue2;
                                                            this.O = 0;
                                                            break;
                                                        }
                                                    }
                                                }
                                            }
                                            iIntValue = -1;
                                            break;
                                        }
                                    }
                                } else {
                                    if (iK1 < 0 || i2 == 1) {
                                        iIntValue = -1;
                                        break;
                                    }
                                    treeMap2 = new TreeMap(Collections.reverseOrder());
                                    i4 = 0;
                                    loop2: while (true) {
                                        if (i4 < B()) {
                                            it4 = l1(i4).iterator();
                                            while (true) {
                                                if (it4.hasNext()) {
                                                    num4 = (Integer) it4.next();
                                                    if (num4.intValue() < 0) {
                                                        treeMap2.put(num4, Integer.valueOf(i4));
                                                    }
                                                } else {
                                                    i4++;
                                                }
                                            }
                                        } else {
                                            it3 = treeMap2.keySet().iterator();
                                            while (true) {
                                                if (it3.hasNext()) {
                                                    num3 = (Integer) it3.next();
                                                    iIntValue3 = num3.intValue();
                                                    if (iIntValue3 < iK1) {
                                                        iIntValue = ((Integer) treeMap2.get(num3)).intValue();
                                                        this.N = iIntValue3;
                                                        this.O = j1(iIntValue);
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                        iIntValue = -1;
                                        break;
                                    }
                                }
                            }
                            if (iIntValue != -1) {
                                q0(iIntValue);
                                this.M = iIntValue;
                                return true;
                            }
                        }
                    }
                }
            }
        } else {
            if (i != 16908343 || bundle == null) {
                return super.i0(i, bundle);
            }
            int i9 = bundle.getInt("android.view.accessibility.action.ARGUMENT_ROW_INT", -1);
            int i10 = bundle.getInt("android.view.accessibility.action.ARGUMENT_COLUMN_INT", -1);
            if (i9 != -1 && i10 != -1) {
                int iA = this.b.m.a();
                int i11 = 0;
                while (true) {
                    if (i11 >= iA) {
                        i11 = -1;
                        break;
                    }
                    RecyclerView recyclerView2 = this.b;
                    int iP1 = p1(i11, recyclerView2.c, recyclerView2.g0);
                    RecyclerView recyclerView3 = this.b;
                    int iO1 = o1(i11, recyclerView3.c, recyclerView3.g0);
                    if (this.p != 1) {
                        if (iP1 == i9 && iO1 == i10) {
                            break;
                        }
                        i11++;
                    } else {
                        if (iP1 == i10 && iO1 == i9) {
                            break;
                        }
                        i11++;
                    }
                }
                if (i11 > -1) {
                    this.x = i11;
                    this.y = 0;
                    Ig ig = this.z;
                    if (ig != null) {
                        ig.a = -1;
                    }
                    o0();
                    return true;
                }
            }
        }
        return false;
    }

    public final void i1() {
        View[] viewArr = this.H;
        if (viewArr == null || viewArr.length != this.F) {
            this.H = new View[this.F];
        }
    }

    public final int j1(int i) {
        if (this.p == 0) {
            RecyclerView recyclerView = this.b;
            return o1(i, recyclerView.c, recyclerView.g0);
        }
        RecyclerView recyclerView2 = this.b;
        return p1(i, recyclerView2.c, recyclerView2.g0);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, rikka.shizuku.AbstractC0162el
    public final int k(C0408ml c0408ml) {
        return G0(c0408ml);
    }

    public final int k1(int i) {
        if (this.p == 1) {
            RecyclerView recyclerView = this.b;
            return o1(i, recyclerView.c, recyclerView.g0);
        }
        RecyclerView recyclerView2 = this.b;
        return p1(i, recyclerView2.c, recyclerView2.g0);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, rikka.shizuku.AbstractC0162el
    public final int l(C0408ml c0408ml) {
        return H0(c0408ml);
    }

    public final HashSet l1(int i) {
        return m1(k1(i), i);
    }

    public final HashSet m1(int i, int i2) {
        HashSet hashSet = new HashSet();
        RecyclerView recyclerView = this.b;
        int iQ1 = q1(i2, recyclerView.c, recyclerView.g0);
        for (int i3 = i; i3 < i + iQ1; i3++) {
            hashSet.add(Integer.valueOf(i3));
        }
        return hashSet;
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, rikka.shizuku.AbstractC0162el
    public final int n(C0408ml c0408ml) {
        return G0(c0408ml);
    }

    public final int n1(int i, int i2) {
        if (this.p != 1 || !V0()) {
            int[] iArr = this.G;
            return iArr[i2 + i] - iArr[i];
        }
        int[] iArr2 = this.G;
        int i3 = this.F;
        return iArr2[i3 - i] - iArr2[(i3 - i) - i2];
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, rikka.shizuku.AbstractC0162el
    public final int o(C0408ml c0408ml) {
        return H0(c0408ml);
    }

    public final int o1(int i, C0285il c0285il, C0408ml c0408ml) {
        boolean z = c0408ml.g;
        R2 r2 = this.K;
        if (!z) {
            int i2 = this.F;
            r2.getClass();
            return R2.D(i, i2);
        }
        int iB = c0285il.b(i);
        if (iB != -1) {
            int i3 = this.F;
            r2.getClass();
            return R2.D(iB, i3);
        }
        Log.w("GridLayoutManager", "Cannot find span size for pre layout position. " + i);
        return 0;
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, rikka.shizuku.AbstractC0162el
    public final int p0(int i, C0285il c0285il, C0408ml c0408ml) {
        t1();
        i1();
        return super.p0(i, c0285il, c0408ml);
    }

    public final int p1(int i, C0285il c0285il, C0408ml c0408ml) {
        boolean z = c0408ml.g;
        R2 r2 = this.K;
        if (!z) {
            int i2 = this.F;
            r2.getClass();
            return i % i2;
        }
        int i3 = this.J.get(i, -1);
        if (i3 != -1) {
            return i3;
        }
        int iB = c0285il.b(i);
        if (iB != -1) {
            int i4 = this.F;
            r2.getClass();
            return iB % i4;
        }
        Log.w("GridLayoutManager", "Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:" + i);
        return 0;
    }

    public final int q1(int i, C0285il c0285il, C0408ml c0408ml) {
        boolean z = c0408ml.g;
        R2 r2 = this.K;
        if (!z) {
            r2.getClass();
            return 1;
        }
        int i2 = this.I.get(i, -1);
        if (i2 != -1) {
            return i2;
        }
        if (c0285il.b(i) != -1) {
            r2.getClass();
            return 1;
        }
        Log.w("GridLayoutManager", "Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:" + i);
        return 1;
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, rikka.shizuku.AbstractC0162el
    public final C0193fl r() {
        return this.p == 0 ? new Md(-2, -1) : new Md(-1, -2);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, rikka.shizuku.AbstractC0162el
    public final int r0(int i, C0285il c0285il, C0408ml c0408ml) {
        t1();
        i1();
        return super.r0(i, c0285il, c0408ml);
    }

    public final void r1(View view, int i, boolean z) {
        int iW;
        int iW2;
        Md md = (Md) view.getLayoutParams();
        Rect rect = md.b;
        int i2 = rect.top + rect.bottom + ((ViewGroup.MarginLayoutParams) md).topMargin + ((ViewGroup.MarginLayoutParams) md).bottomMargin;
        int i3 = rect.left + rect.right + ((ViewGroup.MarginLayoutParams) md).leftMargin + ((ViewGroup.MarginLayoutParams) md).rightMargin;
        int iN1 = n1(md.e, md.f);
        if (this.p == 1) {
            iW2 = AbstractC0162el.w(false, iN1, i, i3, ((ViewGroup.MarginLayoutParams) md).width);
            iW = AbstractC0162el.w(true, this.r.l(), this.m, i2, ((ViewGroup.MarginLayoutParams) md).height);
        } else {
            int iW3 = AbstractC0162el.w(false, iN1, i, i2, ((ViewGroup.MarginLayoutParams) md).height);
            int iW4 = AbstractC0162el.w(true, this.r.l(), this.l, i3, ((ViewGroup.MarginLayoutParams) md).width);
            iW = iW3;
            iW2 = iW4;
        }
        C0193fl c0193fl = (C0193fl) view.getLayoutParams();
        if (z ? z0(view, iW2, iW, c0193fl) : x0(view, iW2, iW, c0193fl)) {
            view.measure(iW2, iW);
        }
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final C0193fl s(Context context, AttributeSet attributeSet) {
        Md md = new Md(context, attributeSet);
        md.e = -1;
        md.f = 0;
        return md;
    }

    public final void s1(int i) {
        if (i == this.F) {
            return;
        }
        this.E = true;
        if (i < 1) {
            throw new IllegalArgumentException(AbstractC0535qo.b(i, "Span count should be at least 1. Provided "));
        }
        this.F = i;
        this.K.F();
        o0();
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final C0193fl t(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            Md md = new Md((ViewGroup.MarginLayoutParams) layoutParams);
            md.e = -1;
            md.f = 0;
            return md;
        }
        Md md2 = new Md(layoutParams);
        md2.e = -1;
        md2.f = 0;
        return md2;
    }

    public final void t1() {
        int iC;
        int iF;
        if (this.p == 1) {
            iC = this.n - E();
            iF = D();
        } else {
            iC = this.o - C();
            iF = F();
        }
        h1(iC - iF);
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final void u0(Rect rect, int i, int i2) {
        int iG;
        int iG2;
        if (this.G == null) {
            super.u0(rect, i, i2);
        }
        int iE = E() + D();
        int iC = C() + F();
        if (this.p == 1) {
            int iHeight = rect.height() + iC;
            RecyclerView recyclerView = this.b;
            WeakHashMap weakHashMap = Hs.a;
            iG2 = AbstractC0162el.g(i2, iHeight, recyclerView.getMinimumHeight());
            int[] iArr = this.G;
            iG = AbstractC0162el.g(i, iArr[iArr.length - 1] + iE, this.b.getMinimumWidth());
        } else {
            int iWidth = rect.width() + iE;
            RecyclerView recyclerView2 = this.b;
            WeakHashMap weakHashMap2 = Hs.a;
            iG = AbstractC0162el.g(i, iWidth, recyclerView2.getMinimumWidth());
            int[] iArr2 = this.G;
            iG2 = AbstractC0162el.g(i2, iArr2[iArr2.length - 1] + iC, this.b.getMinimumHeight());
        }
        this.b.setMeasuredDimension(iG, iG2);
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final int x(C0285il c0285il, C0408ml c0408ml) {
        if (this.p == 1) {
            return Math.min(this.F, B());
        }
        if (c0408ml.b() < 1) {
            return 0;
        }
        return o1(c0408ml.b() - 1, c0285il, c0408ml) + 1;
    }

    public GridLayoutManager(int i) {
        super(1);
        this.E = false;
        this.F = -1;
        this.I = new SparseIntArray();
        this.J = new SparseIntArray();
        this.K = new R2(16);
        this.L = new Rect();
        this.M = -1;
        this.N = -1;
        this.O = -1;
        s1(i);
    }
}
