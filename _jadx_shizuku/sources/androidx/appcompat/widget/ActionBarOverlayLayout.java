package androidx.appcompat.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.WindowInsets;
import android.widget.OverScroller;
import java.util.WeakHashMap;
import moe.shizuku.privileged.api.R;
import rikka.shizuku.AbstractC0755xs;
import rikka.shizuku.AbstractC0817zs;
import rikka.shizuku.C0;
import rikka.shizuku.C0136dq;
import rikka.shizuku.C0139dt;
import rikka.shizuku.C0351kq;
import rikka.shizuku.C0587sf;
import rikka.shizuku.C0603t0;
import rikka.shizuku.C0665v0;
import rikka.shizuku.Ct;
import rikka.shizuku.Hs;
import rikka.shizuku.InterfaceC0652ui;
import rikka.shizuku.Kt;
import rikka.shizuku.MenuC0129di;
import rikka.shizuku.Mt;
import rikka.shizuku.Nt;
import rikka.shizuku.Ot;
import rikka.shizuku.Pt;
import rikka.shizuku.RunnableC0634u0;
import rikka.shizuku.Wi;
import rikka.shizuku.Wt;
import rikka.shizuku.Xi;
import rikka.shizuku.Y8;
import rikka.shizuku.Yi;
import rikka.shizuku.au;

/* JADX INFO: loaded from: classes.dex */
@SuppressLint({"UnknownNullness"})
public class ActionBarOverlayLayout extends ViewGroup implements Y8, Wi, Xi {
    public static final int[] A = {R.attr._0_resource_name_obfuscated_res_0x7f040003, android.R.attr.windowContentOverlay};
    public int a;
    public int b;
    public ContentFrameLayout c;
    public ActionBarContainer d;
    public C0351kq e;
    public Drawable f;
    public boolean g;
    public boolean h;
    public boolean i;
    public boolean j;
    public int k;
    public int l;
    public final Rect m;
    public final Rect n;
    public final Rect o;
    public au p;
    public au q;
    public au r;
    public au s;
    public Ct t;
    public OverScroller u;
    public ViewPropertyAnimator v;
    public final C0603t0 w;
    public final RunnableC0634u0 x;
    public final RunnableC0634u0 y;
    public final Yi z;

    public ActionBarOverlayLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.b = 0;
        this.m = new Rect();
        this.n = new Rect();
        this.o = new Rect();
        new Rect();
        new Rect();
        new Rect();
        new Rect();
        au auVar = au.b;
        this.p = auVar;
        this.q = auVar;
        this.r = auVar;
        this.s = auVar;
        this.w = new C0603t0(0, this);
        this.x = new RunnableC0634u0(this, 0);
        this.y = new RunnableC0634u0(this, 1);
        i(context);
        this.z = new Yi();
    }

    public static boolean g(View view, Rect rect, boolean z) {
        boolean z2;
        C0665v0 c0665v0 = (C0665v0) view.getLayoutParams();
        int i = ((ViewGroup.MarginLayoutParams) c0665v0).leftMargin;
        int i2 = rect.left;
        if (i != i2) {
            ((ViewGroup.MarginLayoutParams) c0665v0).leftMargin = i2;
            z2 = true;
        } else {
            z2 = false;
        }
        int i3 = ((ViewGroup.MarginLayoutParams) c0665v0).topMargin;
        int i4 = rect.top;
        if (i3 != i4) {
            ((ViewGroup.MarginLayoutParams) c0665v0).topMargin = i4;
            z2 = true;
        }
        int i5 = ((ViewGroup.MarginLayoutParams) c0665v0).rightMargin;
        int i6 = rect.right;
        if (i5 != i6) {
            ((ViewGroup.MarginLayoutParams) c0665v0).rightMargin = i6;
            z2 = true;
        }
        if (z) {
            int i7 = ((ViewGroup.MarginLayoutParams) c0665v0).bottomMargin;
            int i8 = rect.bottom;
            if (i7 != i8) {
                ((ViewGroup.MarginLayoutParams) c0665v0).bottomMargin = i8;
                return true;
            }
        }
        return z2;
    }

    @Override // rikka.shizuku.Wi
    public final void a(View view, View view2, int i, int i2) {
        if (i2 == 0) {
            onNestedScrollAccepted(view, view2, i);
        }
    }

    @Override // rikka.shizuku.Wi
    public final void b(View view, int i) {
        if (i == 0) {
            onStopNestedScroll(view);
        }
    }

    @Override // rikka.shizuku.Wi
    public final void c(View view, int i, int i2, int[] iArr, int i3) {
    }

    @Override // android.view.ViewGroup
    public final boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof C0665v0;
    }

    @Override // rikka.shizuku.Xi
    public final void d(View view, int i, int i2, int i3, int i4, int i5, int[] iArr) {
        e(view, i, i2, i3, i4, i5);
    }

    @Override // android.view.View
    public final void draw(Canvas canvas) {
        int translationY;
        super.draw(canvas);
        if (this.f == null || this.g) {
            return;
        }
        if (this.d.getVisibility() == 0) {
            translationY = (int) (this.d.getTranslationY() + this.d.getBottom() + 0.5f);
        } else {
            translationY = 0;
        }
        this.f.setBounds(0, translationY, getWidth(), this.f.getIntrinsicHeight() + translationY);
        this.f.draw(canvas);
    }

    @Override // rikka.shizuku.Wi
    public final void e(View view, int i, int i2, int i3, int i4, int i5) {
        if (i5 == 0) {
            onNestedScroll(view, i, i2, i3, i4);
        }
    }

    @Override // rikka.shizuku.Wi
    public final boolean f(View view, View view2, int i, int i2) {
        return i2 == 0 && onStartNestedScroll(view, view2, i);
    }

    @Override // android.view.View
    public final boolean fitSystemWindows(Rect rect) {
        return super.fitSystemWindows(rect);
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new C0665v0(-1, -1);
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new C0665v0(getContext(), attributeSet);
    }

    @Override // android.view.ViewGroup
    public final int getNestedScrollAxes() {
        Yi yi = this.z;
        return yi.b | yi.a;
    }

    public final void h() {
        removeCallbacks(this.x);
        removeCallbacks(this.y);
        ViewPropertyAnimator viewPropertyAnimator = this.v;
        if (viewPropertyAnimator != null) {
            viewPropertyAnimator.cancel();
        }
    }

    public final void i(Context context) {
        TypedArray typedArrayObtainStyledAttributes = getContext().getTheme().obtainStyledAttributes(A);
        this.a = typedArrayObtainStyledAttributes.getDimensionPixelSize(0, 0);
        Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(1);
        this.f = drawable;
        setWillNotDraw(drawable == null);
        typedArrayObtainStyledAttributes.recycle();
        this.g = context.getApplicationInfo().targetSdkVersion < 19;
        this.u = new OverScroller(context);
    }

    public final void j(int i) {
        k();
        if (i == 2) {
            this.e.getClass();
            Log.i("ToolbarWidgetWrapper", "Progress display unsupported");
        } else if (i == 5) {
            this.e.getClass();
            Log.i("ToolbarWidgetWrapper", "Progress display unsupported");
        } else {
            if (i != 109) {
                return;
            }
            this.h = true;
            this.g = getContext().getApplicationInfo().targetSdkVersion < 19;
        }
    }

    public final void k() {
        if (this.c == null) {
            this.c = (ContentFrameLayout) findViewById(R.id._0_resource_name_obfuscated_res_0x7f090032);
            this.d = (ActionBarContainer) findViewById(R.id._0_resource_name_obfuscated_res_0x7f090033);
            View viewFindViewById = findViewById(R.id._0_resource_name_obfuscated_res_0x7f090031);
            if (!(viewFindViewById instanceof Toolbar)) {
                throw new IllegalStateException("Can't make a decor toolbar out of ".concat(viewFindViewById.getClass().getSimpleName()));
            }
            Toolbar toolbar = (Toolbar) viewFindViewById;
            if (toolbar.K == null) {
                toolbar.K = new C0351kq(toolbar, true);
            }
            this.e = toolbar.K;
        }
    }

    public final void l(boolean z) {
        if (z != this.i) {
            this.i = z;
            if (z) {
                return;
            }
            h();
            h();
            this.d.setTranslationY(-Math.max(0, Math.min(0, this.d.getHeight())));
        }
    }

    public final void m(MenuC0129di menuC0129di, InterfaceC0652ui interfaceC0652ui) {
        k();
        C0351kq c0351kq = this.e;
        C0 c0 = c0351kq.m;
        Toolbar toolbar = c0351kq.a;
        if (c0 == null) {
            c0351kq.m = new C0(toolbar.getContext());
        }
        C0 c1 = c0351kq.m;
        c1.e = interfaceC0652ui;
        if (menuC0129di == null && toolbar.a == null) {
            return;
        }
        toolbar.d();
        MenuC0129di menuC0129di2 = toolbar.a.p;
        if (menuC0129di2 == menuC0129di) {
            return;
        }
        if (menuC0129di2 != null) {
            menuC0129di2.r(toolbar.L);
            menuC0129di2.r(toolbar.M);
        }
        if (toolbar.M == null) {
            toolbar.M = new C0136dq(toolbar);
        }
        c1.o = true;
        if (menuC0129di != null) {
            menuC0129di.b(c1, toolbar.j);
            menuC0129di.b(toolbar.M, toolbar.j);
        } else {
            c1.k(toolbar.j, null);
            toolbar.M.k(toolbar.j, null);
            c1.i();
            toolbar.M.i();
        }
        ActionMenuView actionMenuView = toolbar.a;
        int i = toolbar.k;
        if (actionMenuView.r != i) {
            actionMenuView.r = i;
            if (i == 0) {
                actionMenuView.q = actionMenuView.getContext();
            } else {
                actionMenuView.q = new ContextThemeWrapper(actionMenuView.getContext(), i);
            }
        }
        ActionMenuView actionMenuView2 = toolbar.a;
        actionMenuView2.t = c1;
        c1.h = actionMenuView2;
        actionMenuView2.p = c1.c;
        toolbar.L = c1;
        toolbar.D();
    }

    @Override // android.view.View
    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        k();
        au auVarE = au.e(this, windowInsets);
        Wt wt = auVarE.a;
        boolean zG = g(this.d, new Rect(wt.j().a, auVarE.b(), wt.j().c, auVarE.a()), false);
        WeakHashMap weakHashMap = Hs.a;
        Rect rect = this.m;
        AbstractC0817zs.b(this, auVarE, rect);
        au auVarL = wt.l(rect.left, rect.top, rect.right, rect.bottom);
        this.p = auVarL;
        boolean z = true;
        if (!this.q.equals(auVarL)) {
            this.q = this.p;
            zG = true;
        }
        Rect rect2 = this.n;
        if (rect2.equals(rect)) {
            z = zG;
        } else {
            rect2.set(rect);
        }
        if (z) {
            requestLayout();
        }
        return wt.a().a.c().a.b().d();
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        i(getContext());
        WeakHashMap weakHashMap = Hs.a;
        AbstractC0755xs.c(this);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        h();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int childCount = getChildCount();
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            if (childAt.getVisibility() != 8) {
                C0665v0 c0665v0 = (C0665v0) childAt.getLayoutParams();
                int measuredWidth = childAt.getMeasuredWidth();
                int measuredHeight = childAt.getMeasuredHeight();
                int i6 = ((ViewGroup.MarginLayoutParams) c0665v0).leftMargin + paddingLeft;
                int i7 = ((ViewGroup.MarginLayoutParams) c0665v0).topMargin + paddingTop;
                childAt.layout(i6, i7, measuredWidth + i6, measuredHeight + i7);
            }
        }
    }

    @Override // android.view.View
    public final void onMeasure(int i, int i2) {
        int measuredHeight;
        Pt mt;
        k();
        measureChildWithMargins(this.d, i, 0, i2, 0);
        C0665v0 c0665v0 = (C0665v0) this.d.getLayoutParams();
        int iMax = Math.max(0, this.d.getMeasuredWidth() + ((ViewGroup.MarginLayoutParams) c0665v0).leftMargin + ((ViewGroup.MarginLayoutParams) c0665v0).rightMargin);
        int iMax2 = Math.max(0, this.d.getMeasuredHeight() + ((ViewGroup.MarginLayoutParams) c0665v0).topMargin + ((ViewGroup.MarginLayoutParams) c0665v0).bottomMargin);
        int iCombineMeasuredStates = View.combineMeasuredStates(0, this.d.getMeasuredState());
        WeakHashMap weakHashMap = Hs.a;
        boolean z = (getWindowSystemUiVisibility() & 256) != 0;
        if (z) {
            measuredHeight = this.a;
        } else {
            measuredHeight = this.d.getVisibility() != 8 ? this.d.getMeasuredHeight() : 0;
        }
        Rect rect = this.m;
        Rect rect2 = this.o;
        rect2.set(rect);
        au auVar = this.p;
        this.r = auVar;
        if (this.h || z) {
            C0587sf c0587sfB = C0587sf.b(auVar.a.j().a, this.r.b() + measuredHeight, this.r.a.j().c, this.r.a());
            au auVar2 = this.r;
            int i3 = Build.VERSION.SDK_INT;
            if (i3 >= 34) {
                mt = new Ot(auVar2);
            } else if (i3 >= 30) {
                mt = new Nt(auVar2);
            } else {
                mt = i3 >= 29 ? new Mt(auVar2) : new Kt(auVar2);
            }
            mt.g(c0587sfB);
            this.r = mt.b();
        } else {
            rect2.top += measuredHeight;
            rect2.bottom = rect2.bottom;
            this.r = auVar.a.l(0, measuredHeight, 0, 0);
        }
        g(this.c, rect2, true);
        if (!this.s.equals(this.r)) {
            au auVar3 = this.r;
            this.s = auVar3;
            Hs.b(this.c, auVar3);
        }
        measureChildWithMargins(this.c, i, 0, i2, 0);
        C0665v0 c0665v1 = (C0665v0) this.c.getLayoutParams();
        int iMax3 = Math.max(iMax, this.c.getMeasuredWidth() + ((ViewGroup.MarginLayoutParams) c0665v1).leftMargin + ((ViewGroup.MarginLayoutParams) c0665v1).rightMargin);
        int iMax4 = Math.max(iMax2, this.c.getMeasuredHeight() + ((ViewGroup.MarginLayoutParams) c0665v1).topMargin + ((ViewGroup.MarginLayoutParams) c0665v1).bottomMargin);
        int iCombineMeasuredStates2 = View.combineMeasuredStates(iCombineMeasuredStates, this.c.getMeasuredState());
        setMeasuredDimension(View.resolveSizeAndState(Math.max(getPaddingRight() + getPaddingLeft() + iMax3, getSuggestedMinimumWidth()), i, iCombineMeasuredStates2), View.resolveSizeAndState(Math.max(getPaddingBottom() + getPaddingTop() + iMax4, getSuggestedMinimumHeight()), i2, iCombineMeasuredStates2 << 16));
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final boolean onNestedFling(View view, float f, float f2, boolean z) {
        if (!this.i || !z) {
            return false;
        }
        this.u.fling(0, 0, 0, (int) f2, 0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        if (this.u.getFinalY() > this.d.getHeight()) {
            h();
            this.y.run();
        } else {
            h();
            this.x.run();
        }
        this.j = true;
        return true;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final boolean onNestedPreFling(View view, float f, float f2) {
        return false;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void onNestedPreScroll(View view, int i, int i2, int[] iArr) {
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void onNestedScroll(View view, int i, int i2, int i3, int i4) {
        int i5 = this.k + i2;
        this.k = i5;
        h();
        this.d.setTranslationY(-Math.max(0, Math.min(i5, this.d.getHeight())));
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void onNestedScrollAccepted(View view, View view2, int i) {
        C0139dt c0139dt;
        this.z.a = i;
        ActionBarContainer actionBarContainer = this.d;
        this.k = actionBarContainer != null ? -((int) actionBarContainer.getTranslationY()) : 0;
        h();
        Ct ct = this.t;
        if (ct == null || (c0139dt = ct.I) == null) {
            return;
        }
        c0139dt.a();
        ct.I = null;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final boolean onStartNestedScroll(View view, View view2, int i) {
        if ((i & 2) == 0 || this.d.getVisibility() != 0) {
            return false;
        }
        return this.i;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void onStopNestedScroll(View view) {
        if (!this.i || this.j) {
            return;
        }
        if (this.k <= this.d.getHeight()) {
            h();
            postDelayed(this.x, 600L);
        } else {
            h();
            postDelayed(this.y, 600L);
        }
    }

    @Override // android.view.View
    public final void onWindowSystemUiVisibilityChanged(int i) {
        super.onWindowSystemUiVisibilityChanged(i);
        k();
        int i2 = this.l ^ i;
        this.l = i;
        boolean z = (i & 4) == 0;
        boolean z2 = (i & 256) != 0;
        Ct ct = this.t;
        if (ct != null) {
            ct.E = !z2;
            if (z || !z2) {
                if (ct.F) {
                    ct.F = false;
                    ct.g0(true);
                }
            } else if (!ct.F) {
                ct.F = true;
                ct.g0(true);
            }
        }
        if ((i2 & 256) == 0 || this.t == null) {
            return;
        }
        WeakHashMap weakHashMap = Hs.a;
        AbstractC0755xs.c(this);
    }

    @Override // android.view.View
    public final void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
        this.b = i;
        Ct ct = this.t;
        if (ct != null) {
            ct.D = i;
        }
    }

    @Override // android.view.ViewGroup
    public final boolean shouldDelayChildPressedState() {
        return false;
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new C0665v0(layoutParams);
    }
}
