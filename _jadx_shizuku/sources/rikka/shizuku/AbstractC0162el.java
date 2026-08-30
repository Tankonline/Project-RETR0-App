package rikka.shizuku;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.WeakHashMap;

/* JADX INFO: renamed from: rikka.shizuku.el, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0162el {
    public W5 a;
    public RecyclerView b;
    public final R2 c;
    public final R2 d;
    public Jg e;
    public boolean f;
    public boolean g;
    public final boolean h;
    public final boolean i;
    public int j;
    public boolean k;
    public int l;
    public int m;
    public int n;
    public int o;

    public AbstractC0162el() {
        C0101cl c0101cl = new C0101cl(this, 0);
        C0101cl c0101cl2 = new C0101cl(this, 1);
        this.c = new R2(c0101cl);
        this.d = new R2(c0101cl2);
        this.f = false;
        this.g = false;
        this.h = true;
        this.i = true;
    }

    public static int A(View view) {
        Rect rect = ((C0193fl) view.getLayoutParams()).b;
        return view.getMeasuredWidth() + rect.left + rect.right;
    }

    public static int G(View view) {
        return ((C0193fl) view.getLayoutParams()).a.b();
    }

    public static C0132dl H(Context context, AttributeSet attributeSet, int i, int i2) {
        C0132dl c0132dl = new C0132dl();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Kk.a, i, i2);
        c0132dl.a = typedArrayObtainStyledAttributes.getInt(0, 1);
        c0132dl.b = typedArrayObtainStyledAttributes.getInt(10, 1);
        c0132dl.c = typedArrayObtainStyledAttributes.getBoolean(9, false);
        c0132dl.d = typedArrayObtainStyledAttributes.getBoolean(11, false);
        typedArrayObtainStyledAttributes.recycle();
        return c0132dl;
    }

    public static boolean M(int i, int i2, int i3) {
        int mode = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i2);
        if (i3 > 0 && i != i3) {
            return false;
        }
        if (mode == Integer.MIN_VALUE) {
            return size >= i;
        }
        if (mode != 0) {
            return mode == 1073741824 && size == i;
        }
        return true;
    }

    public static void N(View view, int i, int i2, int i3, int i4) {
        C0193fl c0193fl = (C0193fl) view.getLayoutParams();
        Rect rect = c0193fl.b;
        view.layout(i + rect.left + ((ViewGroup.MarginLayoutParams) c0193fl).leftMargin, i2 + rect.top + ((ViewGroup.MarginLayoutParams) c0193fl).topMargin, (i3 - rect.right) - ((ViewGroup.MarginLayoutParams) c0193fl).rightMargin, (i4 - rect.bottom) - ((ViewGroup.MarginLayoutParams) c0193fl).bottomMargin);
    }

    public static int g(int i, int i2, int i3) {
        int mode = View.MeasureSpec.getMode(i);
        int size = View.MeasureSpec.getSize(i);
        if (mode != Integer.MIN_VALUE) {
            return mode != 1073741824 ? Math.max(i2, i3) : size;
        }
        return Math.min(size, Math.max(i2, i3));
    }

    /* JADX WARN: Code duplicated, block: B:10:0x001a  */
    /* JADX WARN: Code duplicated, block: B:14:0x0022  */
    /* JADX WARN: Code duplicated, block: B:5:0x0010  */
    public static int w(boolean z, int i, int i2, int i3, int i4) {
        int iMax = Math.max(0, i - i3);
        if (z) {
            if (i4 >= 0) {
                i2 = 1073741824;
            } else if (i4 != -1 || (i2 != Integer.MIN_VALUE && (i2 == 0 || i2 != 1073741824))) {
                i2 = 0;
                i4 = 0;
            } else {
                i4 = iMax;
            }
        } else if (i4 >= 0) {
            i2 = 1073741824;
        } else if (i4 == -1) {
            i4 = iMax;
        } else if (i4 != -2) {
            i2 = 0;
            i4 = 0;
        } else if (i2 == Integer.MIN_VALUE || i2 == 1073741824) {
            i4 = iMax;
            i2 = Integer.MIN_VALUE;
        } else {
            i4 = iMax;
            i2 = 0;
        }
        return View.MeasureSpec.makeMeasureSpec(i4, i2);
    }

    public static int z(View view) {
        Rect rect = ((C0193fl) view.getLayoutParams()).b;
        return view.getMeasuredHeight() + rect.top + rect.bottom;
    }

    public abstract void A0(RecyclerView recyclerView, int i);

    public final int B() {
        RecyclerView recyclerView = this.b;
        Yk yk = recyclerView != null ? recyclerView.m : null;
        if (yk != null) {
            return yk.a();
        }
        return 0;
    }

    public final void B0(Jg jg) {
        Jg jg2 = this.e;
        if (jg2 != null && jg != jg2 && jg2.e) {
            jg2.i();
        }
        this.e = jg;
        RecyclerView recyclerView = this.b;
        RunnableC0439nl runnableC0439nl = recyclerView.d0;
        runnableC0439nl.g.removeCallbacks(runnableC0439nl);
        runnableC0439nl.c.abortAnimation();
        if (jg.h) {
            Log.w("RecyclerView", "An instance of " + jg.getClass().getSimpleName() + " was started more than once. Each instance of" + jg.getClass().getSimpleName() + " is intended to only be used once. You should create a new instance for each use.");
        }
        jg.b = recyclerView;
        jg.c = this;
        int i = jg.a;
        if (i == -1) {
            throw new IllegalArgumentException("Invalid target position");
        }
        recyclerView.g0.a = i;
        jg.e = true;
        jg.d = true;
        jg.f = recyclerView.n.q(i);
        jg.b.d0.b();
        jg.h = true;
    }

    public final int C() {
        RecyclerView recyclerView = this.b;
        if (recyclerView != null) {
            return recyclerView.getPaddingBottom();
        }
        return 0;
    }

    public boolean C0() {
        return false;
    }

    public final int D() {
        RecyclerView recyclerView = this.b;
        if (recyclerView != null) {
            return recyclerView.getPaddingLeft();
        }
        return 0;
    }

    public final int E() {
        RecyclerView recyclerView = this.b;
        if (recyclerView != null) {
            return recyclerView.getPaddingRight();
        }
        return 0;
    }

    public final int F() {
        RecyclerView recyclerView = this.b;
        if (recyclerView != null) {
            return recyclerView.getPaddingTop();
        }
        return 0;
    }

    public int I(C0285il c0285il, C0408ml c0408ml) {
        RecyclerView recyclerView = this.b;
        if (recyclerView == null || recyclerView.m == null || !e()) {
            return 1;
        }
        return this.b.m.a();
    }

    public final void J(View view, Rect rect) {
        Matrix matrix;
        Rect rect2 = ((C0193fl) view.getLayoutParams()).b;
        rect.set(-rect2.left, -rect2.top, view.getWidth() + rect2.right, view.getHeight() + rect2.bottom);
        if (this.b != null && (matrix = view.getMatrix()) != null && !matrix.isIdentity()) {
            RectF rectF = this.b.l;
            rectF.set(rect);
            matrix.mapRect(rectF);
            rect.set((int) Math.floor(rectF.left), (int) Math.floor(rectF.top), (int) Math.ceil(rectF.right), (int) Math.ceil(rectF.bottom));
        }
        rect.offset(view.getLeft(), view.getTop());
    }

    public abstract boolean K();

    public boolean L() {
        return false;
    }

    public void O(int i) {
        RecyclerView recyclerView = this.b;
        if (recyclerView != null) {
            int iE = recyclerView.f.e();
            for (int i2 = 0; i2 < iE; i2++) {
                recyclerView.f.d(i2).offsetLeftAndRight(i);
            }
        }
    }

    public void P(int i) {
        RecyclerView recyclerView = this.b;
        if (recyclerView != null) {
            int iE = recyclerView.f.e();
            for (int i2 = 0; i2 < iE; i2++) {
                recyclerView.f.d(i2).offsetTopAndBottom(i);
            }
        }
    }

    public abstract void S(RecyclerView recyclerView);

    public abstract View T(View view, int i, C0285il c0285il, C0408ml c0408ml);

    public void U(AccessibilityEvent accessibilityEvent) {
        RecyclerView recyclerView = this.b;
        C0285il c0285il = recyclerView.c;
        if (accessibilityEvent == null) {
            return;
        }
        boolean z = true;
        if (!recyclerView.canScrollVertically(1) && !this.b.canScrollVertically(-1) && !this.b.canScrollHorizontally(-1) && !this.b.canScrollHorizontally(1)) {
            z = false;
        }
        accessibilityEvent.setScrollable(z);
        Yk yk = this.b.m;
        if (yk != null) {
            accessibilityEvent.setItemCount(yk.a());
        }
    }

    public void V(C0285il c0285il, C0408ml c0408ml, C0141e0 c0141e0) {
        if (this.b.canScrollVertically(-1) || this.b.canScrollHorizontally(-1)) {
            c0141e0.a(8192);
            c0141e0.l(true);
            c0141e0.h(67108864, true);
        }
        if (this.b.canScrollVertically(1) || this.b.canScrollHorizontally(1)) {
            c0141e0.a(4096);
            c0141e0.l(true);
            c0141e0.h(67108864, true);
        }
        c0141e0.a.setCollectionInfo(AccessibilityNodeInfo.CollectionInfo.obtain(I(c0285il, c0408ml), x(c0285il, c0408ml), false, 0));
    }

    public final void W(View view, C0141e0 c0141e0) {
        AbstractC0470ol abstractC0470olK = RecyclerView.K(view);
        if (abstractC0470olK == null || abstractC0470olK.h() || this.a.c.contains(abstractC0470olK.a)) {
            return;
        }
        RecyclerView recyclerView = this.b;
        X(recyclerView.c, recyclerView.g0, view, c0141e0);
    }

    public void X(C0285il c0285il, C0408ml c0408ml, View view, C0141e0 c0141e0) {
        c0141e0.j(Ys.z(false, e() ? G(view) : 0, 1, d() ? G(view) : 0, 1));
    }

    /* JADX WARN: Code duplicated, block: B:21:0x005c  */
    public final void b(View view, int i, boolean z) {
        int iB;
        AbstractC0470ol abstractC0470olK = RecyclerView.K(view);
        if (z || abstractC0470olK.h()) {
            C0104co c0104co = (C0104co) this.b.g.b;
            Ns nsA = (Ns) c0104co.get(abstractC0470olK);
            if (nsA == null) {
                nsA = Ns.a();
                c0104co.put(abstractC0470olK, nsA);
            }
            nsA.a |= 1;
        } else {
            this.b.g.T(abstractC0470olK);
        }
        C0193fl c0193fl = (C0193fl) view.getLayoutParams();
        if (abstractC0470olK.p() || abstractC0470olK.i()) {
            if (abstractC0470olK.i()) {
                abstractC0470olK.n.l(abstractC0470olK);
            } else {
                abstractC0470olK.j &= -33;
            }
            this.a.b(view, i, view.getLayoutParams(), false);
        } else {
            if (view.getParent() == this.b) {
                W5 w5 = this.a;
                int iIndexOfChild = w5.a.a.indexOfChild(view);
                if (iIndexOfChild == -1) {
                    iB = -1;
                } else {
                    V5 v5 = w5.b;
                    if (v5.d(iIndexOfChild)) {
                        iB = -1;
                    } else {
                        iB = iIndexOfChild - v5.b(iIndexOfChild);
                    }
                }
                if (i == -1) {
                    i = this.a.e();
                }
                if (iB == -1) {
                    throw new IllegalStateException("Added View has RecyclerView as parent but view is not a real child. Unfiltered index:" + this.b.indexOfChild(view) + this.b.z());
                }
                if (iB != i) {
                    AbstractC0162el abstractC0162el = this.b.n;
                    View viewU = abstractC0162el.u(iB);
                    if (viewU == null) {
                        throw new IllegalArgumentException("Cannot move a child from non-existing index:" + iB + abstractC0162el.b.toString());
                    }
                    abstractC0162el.u(iB);
                    abstractC0162el.a.c(iB);
                    C0193fl c0193fl2 = (C0193fl) viewU.getLayoutParams();
                    AbstractC0470ol abstractC0470olK2 = RecyclerView.K(viewU);
                    if (abstractC0470olK2.h()) {
                        C0104co c0104co2 = (C0104co) abstractC0162el.b.g.b;
                        Ns nsA2 = (Ns) c0104co2.get(abstractC0470olK2);
                        if (nsA2 == null) {
                            nsA2 = Ns.a();
                            c0104co2.put(abstractC0470olK2, nsA2);
                        }
                        nsA2.a = 1 | nsA2.a;
                    } else {
                        abstractC0162el.b.g.T(abstractC0470olK2);
                    }
                    abstractC0162el.a.b(viewU, i, c0193fl2, abstractC0470olK2.h());
                }
            } else {
                this.a.a(view, i, false);
                c0193fl.c = true;
                Jg jg = this.e;
                if (jg != null && jg.e) {
                    jg.b.getClass();
                    AbstractC0470ol abstractC0470olK3 = RecyclerView.K(view);
                    if ((abstractC0470olK3 != null ? abstractC0470olK3.b() : -1) == jg.a) {
                        jg.f = view;
                    }
                }
            }
        }
        if (c0193fl.d) {
            abstractC0470olK.a.invalidate();
            c0193fl.d = false;
        }
    }

    public void c(String str) {
        RecyclerView recyclerView = this.b;
        if (recyclerView != null) {
            recyclerView.k(str);
        }
    }

    public abstract boolean d();

    public abstract void d0(C0285il c0285il, C0408ml c0408ml);

    public abstract boolean e();

    public abstract void e0(C0408ml c0408ml);

    public boolean f(C0193fl c0193fl) {
        return c0193fl != null;
    }

    public Parcelable g0() {
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:18:0x0060 A[PHI: r0
  0x0060: PHI (r0v8 int) = (r0v5 int), (r0v14 int) binds: [B:24:0x007c, B:16:0x0052] A[DONT_GENERATE, DONT_INLINE]] */
    public boolean i0(int i, Bundle bundle) {
        int iF;
        int iD;
        float f;
        C0285il c0285il = this.b.c;
        int iHeight = this.o;
        int iWidth = this.n;
        Rect rect = new Rect();
        if (this.b.getMatrix().isIdentity() && this.b.getGlobalVisibleRect(rect)) {
            iHeight = rect.height();
            iWidth = rect.width();
        }
        if (i == 4096) {
            iF = this.b.canScrollVertically(1) ? (iHeight - F()) - C() : 0;
            if (this.b.canScrollHorizontally(1)) {
                iD = (iWidth - D()) - E();
            } else {
                iD = 0;
            }
        } else if (i != 8192) {
            iF = 0;
            iD = 0;
        } else {
            iF = this.b.canScrollVertically(-1) ? -((iHeight - F()) - C()) : 0;
            if (this.b.canScrollHorizontally(-1)) {
                iD = -((iWidth - D()) - E());
            } else {
                iD = 0;
            }
        }
        if (iF != 0 || iD != 0) {
            if (bundle != null) {
                f = bundle.getFloat("androidx.core.view.accessibility.action.ARGUMENT_SCROLL_AMOUNT_FLOAT", 1.0f);
                if (f < 0.0f) {
                    int[] iArr = RecyclerView.z0;
                    return false;
                }
            } else {
                f = 1.0f;
            }
            if (Float.compare(f, Float.POSITIVE_INFINITY) != 0) {
                if (Float.compare(1.0f, f) != 0 && Float.compare(0.0f, f) != 0) {
                    iD = (int) (iD * f);
                    iF = (int) (iF * f);
                }
                this.b.l0(iD, iF, true);
                return true;
            }
            RecyclerView recyclerView = this.b;
            Yk yk = recyclerView.m;
            if (yk != null) {
                if (i == 4096) {
                    recyclerView.m0(yk.a() - 1);
                    return true;
                }
                if (i != 8192) {
                    return true;
                }
                recyclerView.m0(0);
                return true;
            }
        }
        return false;
    }

    public abstract int j(C0408ml c0408ml);

    public final void j0(C0285il c0285il) {
        for (int iV = v() - 1; iV >= 0; iV--) {
            if (!RecyclerView.K(u(iV)).o()) {
                View viewU = u(iV);
                m0(iV);
                c0285il.h(viewU);
            }
        }
    }

    public abstract int k(C0408ml c0408ml);

    public final void k0(C0285il c0285il) {
        ArrayList arrayList;
        int size = c0285il.a.size();
        int i = size - 1;
        while (true) {
            arrayList = c0285il.a;
            if (i < 0) {
                break;
            }
            View view = ((AbstractC0470ol) arrayList.get(i)).a;
            AbstractC0470ol abstractC0470olK = RecyclerView.K(view);
            if (!abstractC0470olK.o()) {
                abstractC0470olK.n(false);
                if (abstractC0470olK.j()) {
                    this.b.removeDetachedView(view, false);
                }
                C0458o9 c0458o9 = this.b.L;
                if (c0458o9 != null) {
                    c0458o9.g(abstractC0470olK);
                }
                abstractC0470olK.n(true);
                AbstractC0470ol abstractC0470olK2 = RecyclerView.K(view);
                abstractC0470olK2.n = null;
                abstractC0470olK2.o = false;
                abstractC0470olK2.j &= -33;
                c0285il.i(abstractC0470olK2);
            }
            i--;
        }
        arrayList.clear();
        ArrayList arrayList2 = c0285il.b;
        if (arrayList2 != null) {
            arrayList2.clear();
        }
        if (size > 0) {
            this.b.invalidate();
        }
    }

    public abstract int l(C0408ml c0408ml);

    public final void l0(View view, C0285il c0285il) {
        W5 w5 = this.a;
        Xk xk = w5.a;
        int i = w5.d;
        if (i == 1) {
            throw new IllegalStateException("Cannot call removeView(At) within removeView(At)");
        }
        if (i == 2) {
            throw new IllegalStateException("Cannot call removeView(At) within removeViewIfHidden");
        }
        try {
            w5.d = 1;
            w5.e = view;
            int iIndexOfChild = xk.a.indexOfChild(view);
            if (iIndexOfChild >= 0) {
                if (w5.b.f(iIndexOfChild)) {
                    w5.j(view);
                }
                xk.i(iIndexOfChild);
            }
            w5.d = 0;
            w5.e = null;
            c0285il.h(view);
        } catch (Throwable th) {
            w5.d = 0;
            w5.e = null;
            throw th;
        }
    }

    public abstract int m(C0408ml c0408ml);

    public final void m0(int i) {
        if (u(i) != null) {
            W5 w5 = this.a;
            Xk xk = w5.a;
            int i2 = w5.d;
            if (i2 == 1) {
                throw new IllegalStateException("Cannot call removeView(At) within removeView(At)");
            }
            if (i2 == 2) {
                throw new IllegalStateException("Cannot call removeView(At) within removeViewIfHidden");
            }
            try {
                int iF = w5.f(i);
                View childAt = xk.a.getChildAt(iF);
                if (childAt != null) {
                    w5.d = 1;
                    w5.e = childAt;
                    if (w5.b.f(iF)) {
                        w5.j(childAt);
                    }
                    xk.i(iF);
                }
            } finally {
                w5.d = 0;
                w5.e = null;
            }
        }
    }

    public abstract int n(C0408ml c0408ml);

    /* JADX WARN: Code duplicated, block: B:28:0x00b0  */
    /* JADX WARN: Code duplicated, block: B:33:0x00b8  */
    /* JADX WARN: Code duplicated, block: B:35:0x00bc  */
    public boolean n0(RecyclerView recyclerView, View view, Rect rect, boolean z, boolean z2) {
        int iD = D();
        int iF = F();
        int iE = this.n - E();
        int iC = this.o - C();
        int left = (view.getLeft() + rect.left) - view.getScrollX();
        int top = (view.getTop() + rect.top) - view.getScrollY();
        int iWidth = rect.width() + left;
        int iHeight = rect.height() + top;
        int i = left - iD;
        int iMin = Math.min(0, i);
        int i2 = top - iF;
        int iMin2 = Math.min(0, i2);
        int i3 = iWidth - iE;
        int iMax = Math.max(0, i3);
        int iMax2 = Math.max(0, iHeight - iC);
        if (this.b.getLayoutDirection() != 1) {
            if (iMin == 0) {
                iMin = Math.min(i, iMax);
            }
            iMax = iMin;
        } else if (iMax == 0) {
            iMax = Math.max(iMin, i3);
        }
        if (iMin2 == 0) {
            iMin2 = Math.min(i2, iMax2);
        }
        int[] iArr = {iMax, iMin2};
        int i4 = iArr[0];
        int i5 = iArr[1];
        if (z2) {
            View focusedChild = recyclerView.getFocusedChild();
            if (focusedChild != null) {
                int iD2 = D();
                int iF2 = F();
                int iE2 = this.n - E();
                int iC2 = this.o - C();
                Rect rect2 = this.b.j;
                y(focusedChild, rect2);
                if (rect2.left - i4 < iE2 && rect2.right - i4 > iD2 && rect2.top - i5 < iC2 && rect2.bottom - i5 > iF2) {
                    if (i4 == 0) {
                    }
                    if (z) {
                        recyclerView.scrollBy(i4, i5);
                        return true;
                    }
                    recyclerView.l0(i4, i5, false);
                    return true;
                }
            }
        } else if (i4 == 0 || i5 != 0) {
            if (z) {
                recyclerView.scrollBy(i4, i5);
                return true;
            }
            recyclerView.l0(i4, i5, false);
            return true;
        }
        return false;
    }

    public abstract int o(C0408ml c0408ml);

    public final void o0() {
        RecyclerView recyclerView = this.b;
        if (recyclerView != null) {
            recyclerView.requestLayout();
        }
    }

    public final void p(C0285il c0285il) {
        for (int iV = v() - 1; iV >= 0; iV--) {
            View viewU = u(iV);
            AbstractC0470ol abstractC0470olK = RecyclerView.K(viewU);
            if (!abstractC0470olK.o()) {
                if (!abstractC0470olK.f() || abstractC0470olK.h() || this.b.m.b) {
                    u(iV);
                    this.a.c(iV);
                    c0285il.j(viewU);
                    this.b.g.T(abstractC0470olK);
                } else {
                    m0(iV);
                    c0285il.i(abstractC0470olK);
                }
            }
        }
    }

    public abstract int p0(int i, C0285il c0285il, C0408ml c0408ml);

    public View q(int i) {
        int iV = v();
        for (int i2 = 0; i2 < iV; i2++) {
            View viewU = u(i2);
            AbstractC0470ol abstractC0470olK = RecyclerView.K(viewU);
            if (abstractC0470olK != null && abstractC0470olK.b() == i && !abstractC0470olK.o() && (this.b.g0.g || !abstractC0470olK.h())) {
                return viewU;
            }
        }
        return null;
    }

    public abstract void q0(int i);

    public abstract C0193fl r();

    public abstract int r0(int i, C0285il c0285il, C0408ml c0408ml);

    public C0193fl s(Context context, AttributeSet attributeSet) {
        return new C0193fl(context, attributeSet);
    }

    public final void s0(RecyclerView recyclerView) {
        t0(View.MeasureSpec.makeMeasureSpec(recyclerView.getWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(recyclerView.getHeight(), 1073741824));
    }

    public C0193fl t(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof C0193fl) {
            return new C0193fl((C0193fl) layoutParams);
        }
        return layoutParams instanceof ViewGroup.MarginLayoutParams ? new C0193fl((ViewGroup.MarginLayoutParams) layoutParams) : new C0193fl(layoutParams);
    }

    public final void t0(int i, int i2) {
        this.n = View.MeasureSpec.getSize(i);
        int mode = View.MeasureSpec.getMode(i);
        this.l = mode;
        if (mode == 0 && !RecyclerView.B0) {
            this.n = 0;
        }
        this.o = View.MeasureSpec.getSize(i2);
        int mode2 = View.MeasureSpec.getMode(i2);
        this.m = mode2;
        if (mode2 != 0 || RecyclerView.B0) {
            return;
        }
        this.o = 0;
    }

    public final View u(int i) {
        W5 w5 = this.a;
        if (w5 != null) {
            return w5.d(i);
        }
        return null;
    }

    public void u0(Rect rect, int i, int i2) {
        int iE = E() + D() + rect.width();
        int iC = C() + F() + rect.height();
        RecyclerView recyclerView = this.b;
        WeakHashMap weakHashMap = Hs.a;
        this.b.setMeasuredDimension(g(i, iE, recyclerView.getMinimumWidth()), g(i2, iC, this.b.getMinimumHeight()));
    }

    public final int v() {
        W5 w5 = this.a;
        if (w5 != null) {
            return w5.e();
        }
        return 0;
    }

    public final void v0(int i, int i2) {
        int iV = v();
        if (iV == 0) {
            this.b.q(i, i2);
            return;
        }
        int i3 = Integer.MIN_VALUE;
        int i4 = Integer.MAX_VALUE;
        int i5 = Integer.MIN_VALUE;
        int i6 = Integer.MAX_VALUE;
        for (int i7 = 0; i7 < iV; i7++) {
            View viewU = u(i7);
            Rect rect = this.b.j;
            y(viewU, rect);
            int i8 = rect.left;
            if (i8 < i6) {
                i6 = i8;
            }
            int i9 = rect.right;
            if (i9 > i3) {
                i3 = i9;
            }
            int i10 = rect.top;
            if (i10 < i4) {
                i4 = i10;
            }
            int i11 = rect.bottom;
            if (i11 > i5) {
                i5 = i11;
            }
        }
        this.b.j.set(i6, i4, i3, i5);
        u0(this.b.j, i, i2);
    }

    public final void w0(RecyclerView recyclerView) {
        if (recyclerView == null) {
            this.b = null;
            this.a = null;
            this.n = 0;
            this.o = 0;
        } else {
            this.b = recyclerView;
            this.a = recyclerView.f;
            this.n = recyclerView.getWidth();
            this.o = recyclerView.getHeight();
        }
        this.l = 1073741824;
        this.m = 1073741824;
    }

    public int x(C0285il c0285il, C0408ml c0408ml) {
        RecyclerView recyclerView = this.b;
        if (recyclerView == null || recyclerView.m == null || !d()) {
            return 1;
        }
        return this.b.m.a();
    }

    public final boolean x0(View view, int i, int i2, C0193fl c0193fl) {
        return (!view.isLayoutRequested() && this.h && M(view.getWidth(), i, ((ViewGroup.MarginLayoutParams) c0193fl).width) && M(view.getHeight(), i2, ((ViewGroup.MarginLayoutParams) c0193fl).height)) ? false : true;
    }

    public void y(View view, Rect rect) {
        int[] iArr = RecyclerView.z0;
        C0193fl c0193fl = (C0193fl) view.getLayoutParams();
        Rect rect2 = c0193fl.b;
        rect.set((view.getLeft() - rect2.left) - ((ViewGroup.MarginLayoutParams) c0193fl).leftMargin, (view.getTop() - rect2.top) - ((ViewGroup.MarginLayoutParams) c0193fl).topMargin, view.getRight() + rect2.right + ((ViewGroup.MarginLayoutParams) c0193fl).rightMargin, view.getBottom() + rect2.bottom + ((ViewGroup.MarginLayoutParams) c0193fl).bottomMargin);
    }

    public boolean y0() {
        return false;
    }

    public final boolean z0(View view, int i, int i2, C0193fl c0193fl) {
        return (this.h && M(view.getMeasuredWidth(), i, ((ViewGroup.MarginLayoutParams) c0193fl).width) && M(view.getMeasuredHeight(), i2, ((ViewGroup.MarginLayoutParams) c0193fl).height)) ? false : true;
    }

    public void Q() {
    }

    public void Z() {
    }

    public void R(RecyclerView recyclerView) {
    }

    public void f0(Parcelable parcelable) {
    }

    public void h0(int i) {
    }

    public void Y(int i, int i2) {
    }

    public void a0(int i, int i2) {
    }

    public void b0(int i, int i2) {
    }

    public void c0(int i, int i2) {
    }

    public void i(int i, Fd fd) {
    }

    public void h(int i, int i2, C0408ml c0408ml, Fd fd) {
    }
}
