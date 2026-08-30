package androidx.recyclerview.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import java.util.List;
import rikka.shizuku.AbstractC0162el;
import rikka.shizuku.AbstractC0470ol;
import rikka.shizuku.AbstractC0535qo;
import rikka.shizuku.AbstractC0599sr;
import rikka.shizuku.C0018a0;
import rikka.shizuku.C0132dl;
import rikka.shizuku.C0141e0;
import rikka.shizuku.C0193fl;
import rikka.shizuku.C0285il;
import rikka.shizuku.C0408ml;
import rikka.shizuku.Fd;
import rikka.shizuku.Fg;
import rikka.shizuku.Gg;
import rikka.shizuku.Hg;
import rikka.shizuku.Ig;
import rikka.shizuku.InterfaceC0377ll;
import rikka.shizuku.Ja;
import rikka.shizuku.Jg;
import rikka.shizuku.Yk;

/* JADX INFO: loaded from: classes.dex */
public class LinearLayoutManager extends AbstractC0162el implements InterfaceC0377ll {
    public final Fg A;
    public final Gg B;
    public final int C;
    public final int[] D;
    public int p;
    public Hg q;
    public Ja r;
    public boolean s;
    public final boolean t;
    public boolean u;
    public boolean v;
    public final boolean w;
    public int x;
    public int y;
    public Ig z;

    public LinearLayoutManager(int i) {
        this.p = 1;
        this.t = false;
        this.u = false;
        this.v = false;
        this.w = true;
        this.x = -1;
        this.y = Integer.MIN_VALUE;
        this.z = null;
        this.A = new Fg();
        this.B = new Gg();
        this.C = 2;
        this.D = new int[2];
        c1(i);
        c(null);
        if (this.t) {
            this.t = false;
            o0();
        }
    }

    @Override // rikka.shizuku.AbstractC0162el
    public void A0(RecyclerView recyclerView, int i) {
        Jg jg = new Jg(recyclerView.getContext());
        jg.a = i;
        B0(jg);
    }

    @Override // rikka.shizuku.AbstractC0162el
    public boolean C0() {
        return this.z == null && this.s == this.v;
    }

    public void D0(C0408ml c0408ml, int[] iArr) {
        int i;
        int iL = c0408ml.a != -1 ? this.r.l() : 0;
        if (this.q.f == -1) {
            i = 0;
        } else {
            i = iL;
            iL = 0;
        }
        iArr[0] = iL;
        iArr[1] = i;
    }

    public void E0(C0408ml c0408ml, Hg hg, Fd fd) {
        int i = hg.d;
        if (i < 0 || i >= c0408ml.b()) {
            return;
        }
        fd.a(i, Math.max(0, hg.g));
    }

    public final int F0(C0408ml c0408ml) {
        if (v() == 0) {
            return 0;
        }
        J0();
        Ja ja = this.r;
        boolean z = !this.w;
        return AbstractC0599sr.e(c0408ml, ja, M0(z), L0(z), this, this.w);
    }

    public final int G0(C0408ml c0408ml) {
        if (v() == 0) {
            return 0;
        }
        J0();
        Ja ja = this.r;
        boolean z = !this.w;
        return AbstractC0599sr.f(c0408ml, ja, M0(z), L0(z), this, this.w, this.u);
    }

    public final int H0(C0408ml c0408ml) {
        if (v() == 0) {
            return 0;
        }
        J0();
        Ja ja = this.r;
        boolean z = !this.w;
        return AbstractC0599sr.g(c0408ml, ja, M0(z), L0(z), this, this.w);
    }

    public final int I0(int i) {
        if (i == 1) {
            return (this.p != 1 && V0()) ? 1 : -1;
        }
        if (i == 2) {
            return (this.p != 1 && V0()) ? -1 : 1;
        }
        if (i == 17) {
            return this.p == 0 ? -1 : Integer.MIN_VALUE;
        }
        if (i == 33) {
            return this.p == 1 ? -1 : Integer.MIN_VALUE;
        }
        if (i != 66) {
            return (i == 130 && this.p == 1) ? 1 : Integer.MIN_VALUE;
        }
        return this.p == 0 ? 1 : Integer.MIN_VALUE;
    }

    public final void J0() {
        if (this.q == null) {
            Hg hg = new Hg();
            hg.a = true;
            hg.h = 0;
            hg.i = 0;
            hg.k = null;
            this.q = hg;
        }
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final boolean K() {
        return true;
    }

    public final int K0(C0285il c0285il, Hg hg, C0408ml c0408ml, boolean z) {
        int i;
        int i2 = hg.c;
        int i3 = hg.g;
        if (i3 != Integer.MIN_VALUE) {
            if (i2 < 0) {
                hg.g = i3 + i2;
            }
            Y0(c0285il, hg);
        }
        int i4 = hg.c + hg.h;
        while (true) {
            if ((!hg.l && i4 <= 0) || (i = hg.d) < 0 || i >= c0408ml.b()) {
                break;
            }
            Gg gg = this.B;
            gg.a = 0;
            gg.b = false;
            gg.c = false;
            gg.d = false;
            W0(c0285il, c0408ml, hg, gg);
            if (!gg.b) {
                int i5 = hg.b;
                int i6 = gg.a;
                hg.b = (hg.f * i6) + i5;
                if (!gg.c || hg.k != null || !c0408ml.g) {
                    hg.c -= i6;
                    i4 -= i6;
                }
                int i7 = hg.g;
                if (i7 != Integer.MIN_VALUE) {
                    int i8 = i7 + i6;
                    hg.g = i8;
                    int i9 = hg.c;
                    if (i9 < 0) {
                        hg.g = i8 + i9;
                    }
                    Y0(c0285il, hg);
                }
                if (z && gg.d) {
                    break;
                }
            } else {
                break;
            }
        }
        return i2 - hg.c;
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final boolean L() {
        return this.t;
    }

    public final View L0(boolean z) {
        return this.u ? P0(0, v(), z, true) : P0(v() - 1, -1, z, true);
    }

    public final View M0(boolean z) {
        return this.u ? P0(v() - 1, -1, z, true) : P0(0, v(), z, true);
    }

    public final int N0() {
        View viewP0 = P0(v() - 1, -1, false, true);
        if (viewP0 == null) {
            return -1;
        }
        return AbstractC0162el.G(viewP0);
    }

    public final View O0(int i, int i2) {
        int i3;
        int i4;
        J0();
        if (i2 <= i && i2 >= i) {
            return u(i);
        }
        if (this.r.e(u(i)) < this.r.k()) {
            i3 = 16644;
            i4 = 16388;
        } else {
            i3 = 4161;
            i4 = 4097;
        }
        return this.p == 0 ? this.c.A(i, i2, i3, i4) : this.d.A(i, i2, i3, i4);
    }

    public final View P0(int i, int i2, boolean z, boolean z2) {
        J0();
        int i3 = z ? 24579 : 320;
        int i4 = z2 ? 320 : 0;
        return this.p == 0 ? this.c.A(i, i2, i3, i4) : this.d.A(i, i2, i3, i4);
    }

    /* JADX WARN: Code duplicated, block: B:33:0x0075  */
    /* JADX WARN: Code duplicated, block: B:35:0x0079  */
    public View Q0(C0285il c0285il, C0408ml c0408ml, boolean z, boolean z2) {
        int i;
        int iV;
        int i2;
        J0();
        int iV2 = v();
        if (z2) {
            iV = v() - 1;
            i = -1;
            i2 = -1;
        } else {
            i = iV2;
            iV = 0;
            i2 = 1;
        }
        int iB = c0408ml.b();
        int iK = this.r.k();
        int iG = this.r.g();
        View view = null;
        View view2 = null;
        View view3 = null;
        while (iV != i) {
            View viewU = u(iV);
            int iG2 = AbstractC0162el.G(viewU);
            int iE = this.r.e(viewU);
            int iB2 = this.r.b(viewU);
            if (iG2 >= 0 && iG2 < iB) {
                if (!((C0193fl) viewU.getLayoutParams()).a.h()) {
                    boolean z3 = iB2 <= iK && iE < iK;
                    boolean z4 = iE >= iG && iB2 > iG;
                    if (!z3 && !z4) {
                        return viewU;
                    }
                    if (z) {
                        if (z4) {
                            view2 = viewU;
                        } else if (view == null) {
                            view = viewU;
                        }
                    } else if (z3) {
                        view2 = viewU;
                    } else if (view == null) {
                        view = viewU;
                    }
                } else if (view3 == null) {
                    view3 = viewU;
                }
            }
            iV += i2;
        }
        if (view != null) {
            return view;
        }
        return view2 != null ? view2 : view3;
    }

    public final int R0(int i, C0285il c0285il, C0408ml c0408ml, boolean z) {
        int iG;
        int iG2 = this.r.g() - i;
        if (iG2 <= 0) {
            return 0;
        }
        int i2 = -b1(-iG2, c0285il, c0408ml);
        int i3 = i + i2;
        if (!z || (iG = this.r.g() - i3) <= 0) {
            return i2;
        }
        this.r.o(iG);
        return iG + i2;
    }

    public final int S0(int i, C0285il c0285il, C0408ml c0408ml, boolean z) {
        int iK;
        int iK2 = i - this.r.k();
        if (iK2 <= 0) {
            return 0;
        }
        int i2 = -b1(iK2, c0285il, c0408ml);
        int i3 = i + i2;
        if (!z || (iK = i3 - this.r.k()) <= 0) {
            return i2;
        }
        this.r.o(-iK);
        return i2 - iK;
    }

    @Override // rikka.shizuku.AbstractC0162el
    public View T(View view, int i, C0285il c0285il, C0408ml c0408ml) {
        int iI0;
        View viewO0;
        a1();
        if (v() != 0 && (iI0 = I0(i)) != Integer.MIN_VALUE) {
            J0();
            e1(iI0, (int) (this.r.l() * 0.33333334f), false, c0408ml);
            Hg hg = this.q;
            hg.g = Integer.MIN_VALUE;
            hg.a = false;
            K0(c0285il, hg, c0408ml, true);
            if (iI0 == -1) {
                viewO0 = this.u ? O0(v() - 1, -1) : O0(0, v());
            } else {
                viewO0 = this.u ? O0(0, v()) : O0(v() - 1, -1);
            }
            View viewU0 = iI0 == -1 ? U0() : T0();
            if (!viewU0.hasFocusable()) {
                return viewO0;
            }
            if (viewO0 != null) {
                return viewU0;
            }
        }
        return null;
    }

    public final View T0() {
        return u(this.u ? 0 : v() - 1);
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final void U(AccessibilityEvent accessibilityEvent) {
        super.U(accessibilityEvent);
        if (v() > 0) {
            View viewP0 = P0(0, v(), false, true);
            accessibilityEvent.setFromIndex(viewP0 == null ? -1 : AbstractC0162el.G(viewP0));
            accessibilityEvent.setToIndex(N0());
        }
    }

    public final View U0() {
        return u(this.u ? v() - 1 : 0);
    }

    @Override // rikka.shizuku.AbstractC0162el
    public void V(C0285il c0285il, C0408ml c0408ml, C0141e0 c0141e0) {
        super.V(c0285il, c0408ml, c0141e0);
        Yk yk = this.b.m;
        if (yk == null || yk.a() <= 0) {
            return;
        }
        c0141e0.b(C0018a0.k);
    }

    public final boolean V0() {
        return this.b.getLayoutDirection() == 1;
    }

    public void W0(C0285il c0285il, C0408ml c0408ml, Hg hg, Gg gg) {
        int iD;
        int i;
        int i2;
        int iD2;
        View viewB = hg.b(c0285il);
        if (viewB == null) {
            gg.b = true;
            return;
        }
        C0193fl c0193fl = (C0193fl) viewB.getLayoutParams();
        if (hg.k == null) {
            if (this.u == (hg.f == -1)) {
                b(viewB, -1, false);
            } else {
                b(viewB, 0, false);
            }
        } else {
            if (this.u == (hg.f == -1)) {
                b(viewB, -1, true);
            } else {
                b(viewB, 0, true);
            }
        }
        C0193fl c0193fl2 = (C0193fl) viewB.getLayoutParams();
        Rect rectL = this.b.L(viewB);
        int i3 = rectL.left + rectL.right;
        int i4 = rectL.top + rectL.bottom;
        int iW = AbstractC0162el.w(d(), this.n, this.l, E() + D() + ((ViewGroup.MarginLayoutParams) c0193fl2).leftMargin + ((ViewGroup.MarginLayoutParams) c0193fl2).rightMargin + i3, ((ViewGroup.MarginLayoutParams) c0193fl2).width);
        int iW2 = AbstractC0162el.w(e(), this.o, this.m, C() + F() + ((ViewGroup.MarginLayoutParams) c0193fl2).topMargin + ((ViewGroup.MarginLayoutParams) c0193fl2).bottomMargin + i4, ((ViewGroup.MarginLayoutParams) c0193fl2).height);
        if (x0(viewB, iW, iW2, c0193fl2)) {
            viewB.measure(iW, iW2);
        }
        gg.a = this.r.c(viewB);
        if (this.p == 1) {
            if (V0()) {
                iD2 = this.n - E();
                iD = iD2 - this.r.d(viewB);
            } else {
                iD = D();
                iD2 = this.r.d(viewB) + iD;
            }
            if (hg.f == -1) {
                i = hg.b;
                i2 = i - gg.a;
            } else {
                i2 = hg.b;
                i = gg.a + i2;
            }
        } else {
            int iF = F();
            int iD3 = this.r.d(viewB) + iF;
            if (hg.f == -1) {
                int i5 = hg.b;
                int i6 = i5 - gg.a;
                iD2 = i5;
                i = iD3;
                iD = i6;
                i2 = iF;
            } else {
                int i7 = hg.b;
                int i8 = gg.a + i7;
                iD = i7;
                i = iD3;
                i2 = iF;
                iD2 = i8;
            }
        }
        AbstractC0162el.N(viewB, iD, i2, iD2, i);
        if (c0193fl.a.h() || c0193fl.a.k()) {
            gg.c = true;
        }
        gg.d = viewB.hasFocusable();
    }

    public final void Y0(C0285il c0285il, Hg hg) {
        if (!hg.a || hg.l) {
            return;
        }
        int i = hg.g;
        int i2 = hg.i;
        if (hg.f == -1) {
            int iV = v();
            if (i < 0) {
                return;
            }
            int iF = (this.r.f() - i) + i2;
            if (this.u) {
                for (int i3 = 0; i3 < iV; i3++) {
                    View viewU = u(i3);
                    if (this.r.e(viewU) < iF || this.r.n(viewU) < iF) {
                        Z0(c0285il, 0, i3);
                        return;
                    }
                }
                return;
            }
            int i4 = iV - 1;
            for (int i5 = i4; i5 >= 0; i5--) {
                View viewU2 = u(i5);
                if (this.r.e(viewU2) < iF || this.r.n(viewU2) < iF) {
                    Z0(c0285il, i4, i5);
                    return;
                }
            }
            return;
        }
        if (i < 0) {
            return;
        }
        int i6 = i - i2;
        int iV2 = v();
        if (!this.u) {
            for (int i7 = 0; i7 < iV2; i7++) {
                View viewU3 = u(i7);
                if (this.r.b(viewU3) > i6 || this.r.m(viewU3) > i6) {
                    Z0(c0285il, 0, i7);
                    return;
                }
            }
            return;
        }
        int i8 = iV2 - 1;
        for (int i9 = i8; i9 >= 0; i9--) {
            View viewU4 = u(i9);
            if (this.r.b(viewU4) > i6 || this.r.m(viewU4) > i6) {
                Z0(c0285il, i8, i9);
                return;
            }
        }
    }

    public final void Z0(C0285il c0285il, int i, int i2) {
        if (i == i2) {
            return;
        }
        if (i2 <= i) {
            while (i > i2) {
                View viewU = u(i);
                m0(i);
                c0285il.h(viewU);
                i--;
            }
            return;
        }
        for (int i3 = i2 - 1; i3 >= i; i3--) {
            View viewU2 = u(i3);
            m0(i3);
            c0285il.h(viewU2);
        }
    }

    @Override // rikka.shizuku.InterfaceC0377ll
    public final PointF a(int i) {
        if (v() == 0) {
            return null;
        }
        int i2 = (i < AbstractC0162el.G(u(0))) != this.u ? -1 : 1;
        return this.p == 0 ? new PointF(i2, 0.0f) : new PointF(0.0f, i2);
    }

    public final void a1() {
        if (this.p == 1 || !V0()) {
            this.u = this.t;
        } else {
            this.u = !this.t;
        }
    }

    public final int b1(int i, C0285il c0285il, C0408ml c0408ml) {
        if (v() != 0 && i != 0) {
            J0();
            this.q.a = true;
            int i2 = i > 0 ? 1 : -1;
            int iAbs = Math.abs(i);
            e1(i2, iAbs, true, c0408ml);
            Hg hg = this.q;
            int iK0 = K0(c0285il, hg, c0408ml, false) + hg.g;
            if (iK0 >= 0) {
                if (iAbs > iK0) {
                    i = i2 * iK0;
                }
                this.r.o(-i);
                this.q.j = i;
                return i;
            }
        }
        return 0;
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final void c(String str) {
        if (this.z == null) {
            super.c(str);
        }
    }

    public final void c1(int i) {
        if (i != 0 && i != 1) {
            throw new IllegalArgumentException(AbstractC0535qo.b(i, "invalid orientation:"));
        }
        c(null);
        if (i != this.p || this.r == null) {
            Ja jaA = Ja.a(this, i);
            this.r = jaA;
            this.A.a = jaA;
            this.p = i;
            o0();
        }
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final boolean d() {
        return this.p == 0;
    }

    /* JADX WARN: Code duplicated, block: B:102:0x01a7  */
    /* JADX WARN: Code duplicated, block: B:104:0x01aa  */
    /* JADX WARN: Code duplicated, block: B:111:0x01d5  */
    /* JADX WARN: Code duplicated, block: B:114:0x01dc  */
    /* JADX WARN: Code duplicated, block: B:118:0x01ef  */
    /* JADX WARN: Code duplicated, block: B:122:0x020f A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:124:0x0213  */
    /* JADX WARN: Code duplicated, block: B:126:0x0216 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:128:0x021a  */
    /* JADX WARN: Code duplicated, block: B:130:0x021d A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:131:0x021f  */
    /* JADX WARN: Code duplicated, block: B:133:0x0223  */
    /* JADX WARN: Code duplicated, block: B:135:0x0227  */
    /* JADX WARN: Code duplicated, block: B:137:0x022e  */
    /* JADX WARN: Code duplicated, block: B:138:0x0234  */
    /* JADX WARN: Code duplicated, block: B:95:0x0192  */
    @Override // rikka.shizuku.AbstractC0162el
    public void d0(C0285il c0285il, C0408ml c0408ml) {
        View focusedChild;
        int iB;
        RecyclerView recyclerView;
        View focusedChild2;
        boolean z;
        boolean z2;
        View viewQ0;
        int iE;
        int iB2;
        int iK;
        int iG;
        boolean z3;
        boolean z4;
        C0193fl c0193fl;
        int i;
        int iE2;
        int i2;
        int i3;
        List list;
        int i4;
        int i5;
        int iR0;
        int i6;
        View viewQ;
        int iE3;
        int iG2;
        int i7;
        int i8 = -1;
        if (!(this.z == null && this.x == -1) && c0408ml.b() == 0) {
            j0(c0285il);
            return;
        }
        Ig ig = this.z;
        if (ig != null && (i7 = ig.a) >= 0) {
            this.x = i7;
        }
        J0();
        this.q.a = false;
        a1();
        RecyclerView recyclerView2 = this.b;
        if (recyclerView2 == null || (focusedChild = recyclerView2.getFocusedChild()) == null || this.a.c.contains(focusedChild)) {
            focusedChild = null;
        }
        Fg fg = this.A;
        if (!fg.e || this.x != -1 || this.z != null) {
            fg.d();
            fg.d = this.u ^ this.v;
            if (c0408ml.g || (i = this.x) == -1) {
                if (v() != 0) {
                    recyclerView = this.b;
                    if (recyclerView != null || (focusedChild2 = recyclerView.getFocusedChild()) == null || this.a.c.contains(focusedChild2)) {
                        focusedChild2 = null;
                    }
                    if (focusedChild2 != null) {
                        c0193fl = (C0193fl) focusedChild2.getLayoutParams();
                        if (!c0193fl.a.h() || c0193fl.a.b() < 0 || c0193fl.a.b() >= c0408ml.b()) {
                            z = this.s;
                            z2 = this.v;
                            if (z == z2 || (viewQ0 = Q0(c0285il, c0408ml, fg.d, z2)) == null) {
                                fg.a();
                                if (this.v) {
                                    iB = c0408ml.b() - 1;
                                } else {
                                    iB = 0;
                                }
                                fg.b = iB;
                            } else {
                                fg.b(viewQ0, AbstractC0162el.G(viewQ0));
                                if (!c0408ml.g && C0()) {
                                    iE = this.r.e(viewQ0);
                                    iB2 = this.r.b(viewQ0);
                                    iK = this.r.k();
                                    iG = this.r.g();
                                    if (iB2 <= iK || iE >= iK) {
                                        z3 = false;
                                    } else {
                                        z3 = true;
                                    }
                                    if (iE >= iG || iB2 <= iG) {
                                        z4 = false;
                                    } else {
                                        z4 = true;
                                    }
                                    if (z3 || z4) {
                                        if (fg.d) {
                                            iK = iG;
                                        }
                                        fg.c = iK;
                                    }
                                }
                            }
                        } else {
                            fg.c(focusedChild2, AbstractC0162el.G(focusedChild2));
                        }
                    } else {
                        z = this.s;
                        z2 = this.v;
                        if (z == z2) {
                            fg.a();
                            if (this.v) {
                                iB = c0408ml.b() - 1;
                            } else {
                                iB = 0;
                            }
                            fg.b = iB;
                        } else {
                            fg.b(viewQ0, AbstractC0162el.G(viewQ0));
                            if (!c0408ml.g) {
                                iE = this.r.e(viewQ0);
                                iB2 = this.r.b(viewQ0);
                                iK = this.r.k();
                                iG = this.r.g();
                                if (iB2 <= iK) {
                                    z3 = false;
                                } else {
                                    z3 = false;
                                }
                                if (iE >= iG) {
                                    z4 = false;
                                } else {
                                    z4 = false;
                                }
                                if (z3) {
                                    if (fg.d) {
                                        iK = iG;
                                    }
                                    fg.c = iK;
                                } else {
                                    if (fg.d) {
                                        iK = iG;
                                    }
                                    fg.c = iK;
                                }
                            }
                        }
                    }
                } else {
                    fg.a();
                    if (this.v) {
                        iB = c0408ml.b() - 1;
                    } else {
                        iB = 0;
                    }
                    fg.b = iB;
                }
            } else if (i < 0 || i >= c0408ml.b()) {
                this.x = -1;
                this.y = Integer.MIN_VALUE;
                if (v() != 0) {
                    recyclerView = this.b;
                    if (recyclerView != null) {
                        focusedChild2 = null;
                    } else {
                        focusedChild2 = null;
                    }
                    if (focusedChild2 != null) {
                        c0193fl = (C0193fl) focusedChild2.getLayoutParams();
                        if (c0193fl.a.h()) {
                            z = this.s;
                            z2 = this.v;
                            if (z == z2) {
                                fg.a();
                                if (this.v) {
                                    iB = c0408ml.b() - 1;
                                } else {
                                    iB = 0;
                                }
                                fg.b = iB;
                            } else {
                                fg.b(viewQ0, AbstractC0162el.G(viewQ0));
                                if (!c0408ml.g) {
                                    iE = this.r.e(viewQ0);
                                    iB2 = this.r.b(viewQ0);
                                    iK = this.r.k();
                                    iG = this.r.g();
                                    if (iB2 <= iK) {
                                        z3 = false;
                                    } else {
                                        z3 = false;
                                    }
                                    if (iE >= iG) {
                                        z4 = false;
                                    } else {
                                        z4 = false;
                                    }
                                    if (z3) {
                                        if (fg.d) {
                                            iK = iG;
                                        }
                                        fg.c = iK;
                                    } else {
                                        if (fg.d) {
                                            iK = iG;
                                        }
                                        fg.c = iK;
                                    }
                                }
                            }
                        } else {
                            z = this.s;
                            z2 = this.v;
                            if (z == z2) {
                                fg.a();
                                if (this.v) {
                                    iB = c0408ml.b() - 1;
                                } else {
                                    iB = 0;
                                }
                                fg.b = iB;
                            } else {
                                fg.b(viewQ0, AbstractC0162el.G(viewQ0));
                                if (!c0408ml.g) {
                                    iE = this.r.e(viewQ0);
                                    iB2 = this.r.b(viewQ0);
                                    iK = this.r.k();
                                    iG = this.r.g();
                                    if (iB2 <= iK) {
                                        z3 = false;
                                    } else {
                                        z3 = false;
                                    }
                                    if (iE >= iG) {
                                        z4 = false;
                                    } else {
                                        z4 = false;
                                    }
                                    if (z3) {
                                        if (fg.d) {
                                            iK = iG;
                                        }
                                        fg.c = iK;
                                    } else {
                                        if (fg.d) {
                                            iK = iG;
                                        }
                                        fg.c = iK;
                                    }
                                }
                            }
                        }
                    } else {
                        z = this.s;
                        z2 = this.v;
                        if (z == z2) {
                            fg.a();
                            if (this.v) {
                                iB = c0408ml.b() - 1;
                            } else {
                                iB = 0;
                            }
                            fg.b = iB;
                        } else {
                            fg.b(viewQ0, AbstractC0162el.G(viewQ0));
                            if (!c0408ml.g) {
                                iE = this.r.e(viewQ0);
                                iB2 = this.r.b(viewQ0);
                                iK = this.r.k();
                                iG = this.r.g();
                                if (iB2 <= iK) {
                                    z3 = false;
                                } else {
                                    z3 = false;
                                }
                                if (iE >= iG) {
                                    z4 = false;
                                } else {
                                    z4 = false;
                                }
                                if (z3) {
                                    if (fg.d) {
                                        iK = iG;
                                    }
                                    fg.c = iK;
                                } else {
                                    if (fg.d) {
                                        iK = iG;
                                    }
                                    fg.c = iK;
                                }
                            }
                        }
                    }
                } else {
                    fg.a();
                    if (this.v) {
                        iB = c0408ml.b() - 1;
                    } else {
                        iB = 0;
                    }
                    fg.b = iB;
                }
            } else {
                int i9 = this.x;
                fg.b = i9;
                Ig ig2 = this.z;
                if (ig2 != null && ig2.a >= 0) {
                    boolean z5 = ig2.c;
                    fg.d = z5;
                    if (z5) {
                        fg.c = this.r.g() - this.z.b;
                    } else {
                        fg.c = this.r.k() + this.z.b;
                    }
                } else if (this.y == Integer.MIN_VALUE) {
                    View viewQ2 = q(i9);
                    if (viewQ2 == null) {
                        if (v() > 0) {
                            fg.d = (this.x < AbstractC0162el.G(u(0))) == this.u;
                        }
                        fg.a();
                    } else if (this.r.c(viewQ2) > this.r.l()) {
                        fg.a();
                    } else if (this.r.e(viewQ2) - this.r.k() < 0) {
                        fg.c = this.r.k();
                        fg.d = false;
                    } else if (this.r.g() - this.r.b(viewQ2) < 0) {
                        fg.c = this.r.g();
                        fg.d = true;
                    } else {
                        if (fg.d) {
                            int iB3 = this.r.b(viewQ2);
                            Ja ja = this.r;
                            iE2 = (Integer.MIN_VALUE == ja.a ? 0 : ja.l() - ja.a) + iB3;
                        } else {
                            iE2 = this.r.e(viewQ2);
                        }
                        fg.c = iE2;
                    }
                } else {
                    boolean z6 = this.u;
                    fg.d = z6;
                    if (z6) {
                        fg.c = this.r.g() - this.y;
                    } else {
                        fg.c = this.r.k() + this.y;
                    }
                }
            }
            fg.e = true;
        } else if (focusedChild != null && (this.r.e(focusedChild) >= this.r.g() || this.r.b(focusedChild) <= this.r.k())) {
            fg.c(focusedChild, AbstractC0162el.G(focusedChild));
        }
        Hg hg = this.q;
        hg.f = hg.j >= 0 ? 1 : -1;
        int[] iArr = this.D;
        iArr[0] = 0;
        iArr[1] = 0;
        D0(c0408ml, iArr);
        int iK2 = this.r.k() + Math.max(0, iArr[0]);
        int iH = this.r.h() + Math.max(0, iArr[1]);
        if (c0408ml.g && (i6 = this.x) != -1 && this.y != Integer.MIN_VALUE && (viewQ = q(i6)) != null) {
            if (this.u) {
                iG2 = this.r.g() - this.r.b(viewQ);
                iE3 = this.y;
            } else {
                iE3 = this.r.e(viewQ) - this.r.k();
                iG2 = this.y;
            }
            int i10 = iG2 - iE3;
            if (i10 > 0) {
                iK2 += i10;
            } else {
                iH -= i10;
            }
        }
        if (!fg.d ? !this.u : this.u) {
            i8 = 1;
        }
        X0(c0285il, c0408ml, fg, i8);
        p(c0285il);
        this.q.l = this.r.i() == 0 && this.r.f() == 0;
        this.q.getClass();
        this.q.i = 0;
        if (fg.d) {
            g1(fg.b, fg.c);
            Hg hg2 = this.q;
            hg2.h = iK2;
            K0(c0285il, hg2, c0408ml, false);
            Hg hg3 = this.q;
            i3 = hg3.b;
            int i11 = hg3.d;
            int i12 = hg3.c;
            if (i12 > 0) {
                iH += i12;
            }
            f1(fg.b, fg.c);
            Hg hg4 = this.q;
            hg4.h = iH;
            hg4.d += hg4.e;
            K0(c0285il, hg4, c0408ml, false);
            Hg hg5 = this.q;
            i2 = hg5.b;
            int i13 = hg5.c;
            if (i13 > 0) {
                g1(i11, i3);
                Hg hg6 = this.q;
                hg6.h = i13;
                K0(c0285il, hg6, c0408ml, false);
                i3 = this.q.b;
            }
        } else {
            f1(fg.b, fg.c);
            Hg hg7 = this.q;
            hg7.h = iH;
            K0(c0285il, hg7, c0408ml, false);
            Hg hg8 = this.q;
            i2 = hg8.b;
            int i14 = hg8.d;
            int i15 = hg8.c;
            if (i15 > 0) {
                iK2 += i15;
            }
            g1(fg.b, fg.c);
            Hg hg9 = this.q;
            hg9.h = iK2;
            hg9.d += hg9.e;
            K0(c0285il, hg9, c0408ml, false);
            Hg hg10 = this.q;
            int i16 = hg10.b;
            int i17 = hg10.c;
            if (i17 > 0) {
                f1(i14, i2);
                Hg hg11 = this.q;
                hg11.h = i17;
                K0(c0285il, hg11, c0408ml, false);
                i2 = this.q.b;
            }
            i3 = i16;
        }
        if (v() > 0) {
            if (this.u ^ this.v) {
                int iR1 = R0(i2, c0285il, c0408ml, true);
                i4 = i3 + iR1;
                i5 = i2 + iR1;
                iR0 = S0(i4, c0285il, c0408ml, false);
            } else {
                int iS0 = S0(i3, c0285il, c0408ml, true);
                i4 = i3 + iS0;
                i5 = i2 + iS0;
                iR0 = R0(i5, c0285il, c0408ml, false);
            }
            i3 = i4 + iR0;
            i2 = i5 + iR0;
        }
        if (c0408ml.k && v() != 0 && !c0408ml.g && C0()) {
            List list2 = c0285il.d;
            int size = list2.size();
            int iG3 = AbstractC0162el.G(u(0));
            int iC = 0;
            int iC2 = 0;
            for (int i18 = 0; i18 < size; i18++) {
                AbstractC0470ol abstractC0470ol = (AbstractC0470ol) list2.get(i18);
                if (!abstractC0470ol.h()) {
                    boolean z7 = abstractC0470ol.b() < iG3;
                    boolean z8 = this.u;
                    View view = abstractC0470ol.a;
                    if (z7 != z8) {
                        iC += this.r.c(view);
                    } else {
                        iC2 += this.r.c(view);
                    }
                }
            }
            this.q.k = list2;
            if (iC > 0) {
                g1(AbstractC0162el.G(U0()), i3);
                Hg hg12 = this.q;
                hg12.h = iC;
                hg12.c = 0;
                hg12.a(null);
                K0(c0285il, this.q, c0408ml, false);
            }
            if (iC2 > 0) {
                f1(AbstractC0162el.G(T0()), i2);
                Hg hg13 = this.q;
                hg13.h = iC2;
                hg13.c = 0;
                list = null;
                hg13.a(null);
                K0(c0285il, this.q, c0408ml, false);
            } else {
                list = null;
            }
            this.q.k = list;
        }
        if (c0408ml.g) {
            fg.d();
        } else {
            Ja ja2 = this.r;
            ja2.a = ja2.l();
        }
        this.s = this.v;
    }

    public void d1(boolean z) {
        c(null);
        if (this.v == z) {
            return;
        }
        this.v = z;
        o0();
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final boolean e() {
        return this.p == 1;
    }

    @Override // rikka.shizuku.AbstractC0162el
    public void e0(C0408ml c0408ml) {
        this.z = null;
        this.x = -1;
        this.y = Integer.MIN_VALUE;
        this.A.d();
    }

    public final void e1(int i, int i2, boolean z, C0408ml c0408ml) {
        int iK;
        this.q.l = this.r.i() == 0 && this.r.f() == 0;
        this.q.f = i;
        int[] iArr = this.D;
        iArr[0] = 0;
        iArr[1] = 0;
        D0(c0408ml, iArr);
        int iMax = Math.max(0, iArr[0]);
        int iMax2 = Math.max(0, iArr[1]);
        boolean z2 = i == 1;
        Hg hg = this.q;
        int i3 = z2 ? iMax2 : iMax;
        hg.h = i3;
        if (!z2) {
            iMax = iMax2;
        }
        hg.i = iMax;
        if (z2) {
            hg.h = this.r.h() + i3;
            View viewT0 = T0();
            Hg hg2 = this.q;
            hg2.e = this.u ? -1 : 1;
            int iG = AbstractC0162el.G(viewT0);
            Hg hg3 = this.q;
            hg2.d = iG + hg3.e;
            hg3.b = this.r.b(viewT0);
            iK = this.r.b(viewT0) - this.r.g();
        } else {
            View viewU0 = U0();
            Hg hg4 = this.q;
            hg4.h = this.r.k() + hg4.h;
            Hg hg5 = this.q;
            hg5.e = this.u ? 1 : -1;
            int iG2 = AbstractC0162el.G(viewU0);
            Hg hg6 = this.q;
            hg5.d = iG2 + hg6.e;
            hg6.b = this.r.e(viewU0);
            iK = (-this.r.e(viewU0)) + this.r.k();
        }
        Hg hg7 = this.q;
        hg7.c = i2;
        if (z) {
            hg7.c = i2 - iK;
        }
        hg7.g = iK;
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final void f0(Parcelable parcelable) {
        if (parcelable instanceof Ig) {
            Ig ig = (Ig) parcelable;
            this.z = ig;
            if (this.x != -1) {
                ig.a = -1;
            }
            o0();
        }
    }

    public final void f1(int i, int i2) {
        this.q.c = this.r.g() - i2;
        Hg hg = this.q;
        hg.e = this.u ? -1 : 1;
        hg.d = i;
        hg.f = 1;
        hg.b = i2;
        hg.g = Integer.MIN_VALUE;
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final Parcelable g0() {
        Ig ig = this.z;
        if (ig != null) {
            Ig ig2 = new Ig();
            ig2.a = ig.a;
            ig2.b = ig.b;
            ig2.c = ig.c;
            return ig2;
        }
        Ig ig3 = new Ig();
        if (v() <= 0) {
            ig3.a = -1;
            return ig3;
        }
        J0();
        boolean z = this.s ^ this.u;
        ig3.c = z;
        if (z) {
            View viewT0 = T0();
            ig3.b = this.r.g() - this.r.b(viewT0);
            ig3.a = AbstractC0162el.G(viewT0);
            return ig3;
        }
        View viewU0 = U0();
        ig3.a = AbstractC0162el.G(viewU0);
        ig3.b = this.r.e(viewU0) - this.r.k();
        return ig3;
    }

    public final void g1(int i, int i2) {
        this.q.c = i2 - this.r.k();
        Hg hg = this.q;
        hg.d = i;
        hg.e = this.u ? 1 : -1;
        hg.f = -1;
        hg.b = i2;
        hg.g = Integer.MIN_VALUE;
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final void h(int i, int i2, C0408ml c0408ml, Fd fd) {
        if (this.p != 0) {
            i = i2;
        }
        if (v() == 0 || i == 0) {
            return;
        }
        J0();
        e1(i > 0 ? 1 : -1, Math.abs(i), true, c0408ml);
        E0(c0408ml, this.q, fd);
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final void i(int i, Fd fd) {
        boolean z;
        int i2;
        Ig ig = this.z;
        if (ig == null || (i2 = ig.a) < 0) {
            a1();
            z = this.u;
            i2 = this.x;
            if (i2 == -1) {
                i2 = z ? i - 1 : 0;
            }
        } else {
            z = ig.c;
        }
        int i3 = z ? -1 : 1;
        for (int i4 = 0; i4 < this.C && i2 >= 0 && i2 < i; i4++) {
            fd.a(i2, 0);
            i2 += i3;
        }
    }

    /* JADX WARN: Code duplicated, block: B:19:0x0048  */
    /* JADX WARN: Code duplicated, block: B:21:0x0050  */
    @Override // rikka.shizuku.AbstractC0162el
    public boolean i0(int i, Bundle bundle) {
        int iMin;
        Ig ig;
        if (super.i0(i, bundle)) {
            return true;
        }
        if (i == 16908343 && bundle != null) {
            if (this.p == 1) {
                int i2 = bundle.getInt("android.view.accessibility.action.ARGUMENT_ROW_INT", -1);
                if (i2 >= 0) {
                    RecyclerView recyclerView = this.b;
                    iMin = Math.min(i2, I(recyclerView.c, recyclerView.g0) - 1);
                    if (iMin >= 0) {
                        this.x = iMin;
                        this.y = 0;
                        ig = this.z;
                        if (ig != null) {
                            ig.a = -1;
                        }
                        o0();
                        return true;
                    }
                }
            } else {
                int i3 = bundle.getInt("android.view.accessibility.action.ARGUMENT_COLUMN_INT", -1);
                if (i3 >= 0) {
                    RecyclerView recyclerView2 = this.b;
                    iMin = Math.min(i3, x(recyclerView2.c, recyclerView2.g0) - 1);
                    if (iMin >= 0) {
                        this.x = iMin;
                        this.y = 0;
                        ig = this.z;
                        if (ig != null) {
                            ig.a = -1;
                        }
                        o0();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final int j(C0408ml c0408ml) {
        return F0(c0408ml);
    }

    @Override // rikka.shizuku.AbstractC0162el
    public int k(C0408ml c0408ml) {
        return G0(c0408ml);
    }

    @Override // rikka.shizuku.AbstractC0162el
    public int l(C0408ml c0408ml) {
        return H0(c0408ml);
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final int m(C0408ml c0408ml) {
        return F0(c0408ml);
    }

    @Override // rikka.shizuku.AbstractC0162el
    public int n(C0408ml c0408ml) {
        return G0(c0408ml);
    }

    @Override // rikka.shizuku.AbstractC0162el
    public int o(C0408ml c0408ml) {
        return H0(c0408ml);
    }

    @Override // rikka.shizuku.AbstractC0162el
    public int p0(int i, C0285il c0285il, C0408ml c0408ml) {
        if (this.p == 1) {
            return 0;
        }
        return b1(i, c0285il, c0408ml);
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final View q(int i) {
        int iV = v();
        if (iV == 0) {
            return null;
        }
        int iG = i - AbstractC0162el.G(u(0));
        if (iG >= 0 && iG < iV) {
            View viewU = u(iG);
            if (AbstractC0162el.G(viewU) == i) {
                return viewU;
            }
        }
        return super.q(i);
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final void q0(int i) {
        this.x = i;
        this.y = Integer.MIN_VALUE;
        Ig ig = this.z;
        if (ig != null) {
            ig.a = -1;
        }
        o0();
    }

    @Override // rikka.shizuku.AbstractC0162el
    public C0193fl r() {
        return new C0193fl(-2, -2);
    }

    @Override // rikka.shizuku.AbstractC0162el
    public int r0(int i, C0285il c0285il, C0408ml c0408ml) {
        if (this.p == 0) {
            return 0;
        }
        return b1(i, c0285il, c0408ml);
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final boolean y0() {
        if (this.m != 1073741824 && this.l != 1073741824) {
            int iV = v();
            for (int i = 0; i < iV; i++) {
                ViewGroup.LayoutParams layoutParams = u(i).getLayoutParams();
                if (layoutParams.width < 0 && layoutParams.height < 0) {
                    return true;
                }
            }
        }
        return false;
    }

    @SuppressLint({"UnknownNullness"})
    public LinearLayoutManager(Context context, AttributeSet attributeSet, int i, int i2) {
        this.p = 1;
        this.t = false;
        this.u = false;
        this.v = false;
        this.w = true;
        this.x = -1;
        this.y = Integer.MIN_VALUE;
        this.z = null;
        this.A = new Fg();
        this.B = new Gg();
        this.C = 2;
        this.D = new int[2];
        C0132dl c0132dlH = AbstractC0162el.H(context, attributeSet, i, i2);
        c1(c0132dlH.a);
        boolean z = c0132dlH.c;
        c(null);
        if (z != this.t) {
            this.t = z;
            o0();
        }
        d1(c0132dlH.d);
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final void S(RecyclerView recyclerView) {
    }

    public void X0(C0285il c0285il, C0408ml c0408ml, Fg fg, int i) {
    }
}
