package androidx.coordinatorlayout.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.appbar.AppBarLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.WeakHashMap;
import moe.shizuku.privileged.api.R;
import rikka.shizuku.AbstractC0755xs;
import rikka.shizuku.AbstractC0817zs;
import rikka.shizuku.C0104co;
import rikka.shizuku.Gk;
import rikka.shizuku.Hs;
import rikka.shizuku.J7;
import rikka.shizuku.K7;
import rikka.shizuku.L7;
import rikka.shizuku.M7;
import rikka.shizuku.Ms;
import rikka.shizuku.N7;
import rikka.shizuku.O7;
import rikka.shizuku.P7;
import rikka.shizuku.Q7;
import rikka.shizuku.Wi;
import rikka.shizuku.Xi;
import rikka.shizuku.Xn;
import rikka.shizuku.Yi;
import rikka.shizuku.Yj;
import rikka.shizuku.Ys;
import rikka.shizuku.Zj;
import rikka.shizuku.au;

/* JADX INFO: loaded from: classes.dex */
public class CoordinatorLayout extends ViewGroup implements Wi, Xi {
    public static final String t;
    public static final Class[] u;
    public static final ThreadLocal v;
    public static final Q7 w;
    public static final Zj x;
    public final ArrayList a;
    public final Xn b;
    public final ArrayList c;
    public final ArrayList d;
    public final int[] e;
    public final int[] f;
    public boolean g;
    public boolean h;
    public final int[] i;
    public View j;
    public View k;
    public O7 l;
    public boolean m;
    public au n;
    public boolean o;
    public final Drawable p;
    public ViewGroup.OnHierarchyChangeListener q;
    public Ys r;
    public final Yi s;

    static {
        Package r0 = CoordinatorLayout.class.getPackage();
        t = r0 != null ? r0.getName() : null;
        w = new Q7(0);
        u = new Class[]{Context.class, AttributeSet.class};
        v = new ThreadLocal();
        x = new Zj();
    }

    public CoordinatorLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, R.attr._0_resource_name_obfuscated_res_0x7f040149);
        this.a = new ArrayList();
        this.b = new Xn(3);
        this.c = new ArrayList();
        this.d = new ArrayList();
        this.e = new int[2];
        this.f = new int[2];
        this.s = new Yi();
        int[] iArr = Gk.a;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, iArr, R.attr._0_resource_name_obfuscated_res_0x7f040149, 0);
        if (Build.VERSION.SDK_INT >= 29) {
            saveAttributeDataForStyleable(context, iArr, attributeSet, typedArrayObtainStyledAttributes, R.attr._0_resource_name_obfuscated_res_0x7f040149, 0);
        }
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(0, 0);
        if (resourceId != 0) {
            Resources resources = context.getResources();
            int[] intArray = resources.getIntArray(resourceId);
            this.i = intArray;
            float f = resources.getDisplayMetrics().density;
            int length = intArray.length;
            for (int i = 0; i < length; i++) {
                int[] iArr2 = this.i;
                iArr2[i] = (int) (iArr2[i] * f);
            }
        }
        this.p = typedArrayObtainStyledAttributes.getDrawable(1);
        typedArrayObtainStyledAttributes.recycle();
        w();
        super.setOnHierarchyChangeListener(new M7(this));
        WeakHashMap weakHashMap = Hs.a;
        if (getImportantForAccessibility() == 0) {
            setImportantForAccessibility(1);
        }
    }

    public static Rect g() {
        Rect rect = (Rect) x.a();
        return rect == null ? new Rect() : rect;
    }

    public static void l(int i, Rect rect, Rect rect2, N7 n7, int i2, int i3) {
        int iWidth;
        int iHeight;
        int i4 = n7.c;
        if (i4 == 0) {
            i4 = 17;
        }
        int absoluteGravity = Gravity.getAbsoluteGravity(i4, i);
        int i5 = n7.d;
        if ((i5 & 7) == 0) {
            i5 |= 8388611;
        }
        if ((i5 & 112) == 0) {
            i5 |= 48;
        }
        int absoluteGravity2 = Gravity.getAbsoluteGravity(i5, i);
        int i6 = absoluteGravity & 7;
        int i7 = absoluteGravity & 112;
        int i8 = absoluteGravity2 & 7;
        int i9 = absoluteGravity2 & 112;
        if (i8 != 1) {
            iWidth = i8 != 5 ? rect.left : rect.right;
        } else {
            iWidth = rect.left + (rect.width() / 2);
        }
        if (i9 != 16) {
            iHeight = i9 != 80 ? rect.top : rect.bottom;
        } else {
            iHeight = rect.top + (rect.height() / 2);
        }
        if (i6 == 1) {
            iWidth -= i2 / 2;
        } else if (i6 != 5) {
            iWidth -= i2;
        }
        if (i7 == 16) {
            iHeight -= i3 / 2;
        } else if (i7 != 80) {
            iHeight -= i3;
        }
        rect2.set(iWidth, iHeight, i2 + iWidth, i3 + iHeight);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static N7 n(View view) {
        N7 n7 = (N7) view.getLayoutParams();
        if (!n7.b) {
            if (view instanceof J7) {
                AppBarLayout.Behavior behavior = new AppBarLayout.Behavior();
                ((AppBarLayout) ((J7) view)).w = behavior;
                K7 k7 = n7.a;
                if (k7 != behavior) {
                    if (k7 != null) {
                        k7.f();
                    }
                    n7.a = behavior;
                    n7.b = true;
                }
                n7.b = true;
                return n7;
            }
            L7 l7 = null;
            for (Class<?> superclass = view.getClass(); superclass != null; superclass = superclass.getSuperclass()) {
                l7 = (L7) superclass.getAnnotation(L7.class);
                if (l7 != null) {
                    break;
                }
            }
            if (l7 != null) {
                try {
                    K7 k8 = (K7) l7.value().getDeclaredConstructor(null).newInstance(null);
                    K7 k9 = n7.a;
                    if (k9 != k8) {
                        if (k9 != null) {
                            k9.f();
                        }
                        n7.a = k8;
                        n7.b = true;
                        if (k8 != null) {
                            k8.c(n7);
                        }
                    }
                } catch (Exception e) {
                    Log.e("CoordinatorLayout", "Default behavior class " + l7.value().getName() + " could not be instantiated. Did you forget a default constructor?", e);
                }
            }
            n7.b = true;
        }
        return n7;
    }

    public static void u(View view, int i) {
        N7 n7 = (N7) view.getLayoutParams();
        int i2 = n7.i;
        if (i2 != i) {
            WeakHashMap weakHashMap = Hs.a;
            view.offsetLeftAndRight(i - i2);
            n7.i = i;
        }
    }

    public static void v(View view, int i) {
        N7 n7 = (N7) view.getLayoutParams();
        int i2 = n7.j;
        if (i2 != i) {
            WeakHashMap weakHashMap = Hs.a;
            view.offsetTopAndBottom(i - i2);
            n7.j = i;
        }
    }

    @Override // rikka.shizuku.Wi
    public final void a(View view, View view2, int i, int i2) {
        Yi yi = this.s;
        if (i2 == 1) {
            yi.b = i;
        } else {
            yi.a = i;
        }
        this.k = view2;
        int childCount = getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            ((N7) getChildAt(i3).getLayoutParams()).getClass();
        }
    }

    @Override // rikka.shizuku.Wi
    public final void b(View view, int i) {
        Yi yi = this.s;
        if (i == 1) {
            yi.b = 0;
        } else {
            yi.a = 0;
        }
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            N7 n7 = (N7) childAt.getLayoutParams();
            if (n7.a(i)) {
                K7 k7 = n7.a;
                if (k7 != null) {
                    k7.q(this, childAt, view, i);
                }
                if (i == 0) {
                    n7.m = false;
                } else if (i == 1) {
                    n7.n = false;
                }
                n7.o = false;
            }
        }
        this.k = null;
    }

    @Override // rikka.shizuku.Wi
    public final void c(View view, int i, int i2, int[] iArr, int i3) {
        K7 k7;
        int childCount = getChildCount();
        boolean z = false;
        int iMax = 0;
        int iMax2 = 0;
        for (int i4 = 0; i4 < childCount; i4++) {
            View childAt = getChildAt(i4);
            if (childAt.getVisibility() != 8) {
                N7 n7 = (N7) childAt.getLayoutParams();
                if (n7.a(i3) && (k7 = n7.a) != null) {
                    int[] iArr2 = this.e;
                    iArr2[0] = 0;
                    iArr2[1] = 0;
                    k7.k(this, childAt, view, i, i2, iArr2, i3);
                    iMax = i > 0 ? Math.max(iMax, iArr2[0]) : Math.min(iMax, iArr2[0]);
                    iMax2 = i2 > 0 ? Math.max(iMax2, iArr2[1]) : Math.min(iMax2, iArr2[1]);
                    z = true;
                }
            }
        }
        iArr[0] = iMax;
        iArr[1] = iMax2;
        if (z) {
            p(1);
        }
    }

    @Override // android.view.ViewGroup
    public final boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof N7) && super.checkLayoutParams(layoutParams);
    }

    @Override // rikka.shizuku.Xi
    public final void d(View view, int i, int i2, int i3, int i4, int i5, int[] iArr) {
        K7 k7;
        int childCount = getChildCount();
        int iMax = 0;
        int iMax2 = 0;
        boolean z = false;
        for (int i6 = 0; i6 < childCount; i6++) {
            View childAt = getChildAt(i6);
            if (childAt.getVisibility() != 8) {
                N7 n7 = (N7) childAt.getLayoutParams();
                if (n7.a(i5) && (k7 = n7.a) != null) {
                    int[] iArr2 = this.e;
                    iArr2[0] = 0;
                    iArr2[1] = 0;
                    k7.l(this, childAt, i2, i3, i4, iArr2);
                    iMax = i3 > 0 ? Math.max(iMax, iArr2[0]) : Math.min(iMax, iArr2[0]);
                    iMax2 = i4 > 0 ? Math.max(iMax2, iArr2[1]) : Math.min(iMax2, iArr2[1]);
                    z = true;
                }
            }
        }
        iArr[0] = iArr[0] + iMax;
        iArr[1] = iArr[1] + iMax2;
        if (z) {
            p(1);
        }
    }

    @Override // android.view.ViewGroup
    public final boolean drawChild(Canvas canvas, View view, long j) {
        K7 k7 = ((N7) view.getLayoutParams()).a;
        if (k7 != null) {
            k7.getClass();
        }
        return super.drawChild(canvas, view, j);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void drawableStateChanged() {
        super.drawableStateChanged();
        int[] drawableState = getDrawableState();
        Drawable drawable = this.p;
        if ((drawable == null || !drawable.isStateful()) ? false : drawable.setState(drawableState)) {
            invalidate();
        }
    }

    @Override // rikka.shizuku.Wi
    public final void e(View view, int i, int i2, int i3, int i4, int i5) {
        d(view, i, i2, i3, i4, 0, this.f);
    }

    @Override // rikka.shizuku.Wi
    public final boolean f(View view, View view2, int i, int i2) {
        View view3;
        int i3;
        int i4;
        int childCount = getChildCount();
        int i5 = 0;
        boolean z = false;
        while (i5 < childCount) {
            View childAt = getChildAt(i5);
            if (childAt.getVisibility() == 8) {
                view3 = view;
                i3 = i;
                i4 = i2;
            } else {
                N7 n7 = (N7) childAt.getLayoutParams();
                K7 k7 = n7.a;
                if (k7 != null) {
                    view3 = view;
                    i3 = i;
                    i4 = i2;
                    boolean zP = k7.p(this, childAt, view3, i3, i4);
                    z |= zP;
                    if (i4 == 0) {
                        n7.m = zP;
                    } else if (i4 == 1) {
                        n7.n = zP;
                    }
                } else {
                    view3 = view;
                    i3 = i;
                    i4 = i2;
                    if (i4 == 0) {
                        n7.m = false;
                    } else if (i4 == 1) {
                        n7.n = false;
                    }
                }
            }
            i5++;
            view = view3;
            i = i3;
            i2 = i4;
        }
        return z;
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new N7();
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new N7(getContext(), attributeSet);
    }

    @Override // android.view.ViewGroup
    public final int getNestedScrollAxes() {
        Yi yi = this.s;
        return yi.b | yi.a;
    }

    @Override // android.view.View
    public final int getSuggestedMinimumHeight() {
        return Math.max(super.getSuggestedMinimumHeight(), getPaddingBottom() + getPaddingTop());
    }

    @Override // android.view.View
    public final int getSuggestedMinimumWidth() {
        return Math.max(super.getSuggestedMinimumWidth(), getPaddingRight() + getPaddingLeft());
    }

    public final void h(N7 n7, Rect rect, int i, int i2) {
        int width = getWidth();
        int height = getHeight();
        int iMax = Math.max(getPaddingLeft() + ((ViewGroup.MarginLayoutParams) n7).leftMargin, Math.min(rect.left, ((width - getPaddingRight()) - i) - ((ViewGroup.MarginLayoutParams) n7).rightMargin));
        int iMax2 = Math.max(getPaddingTop() + ((ViewGroup.MarginLayoutParams) n7).topMargin, Math.min(rect.top, ((height - getPaddingBottom()) - i2) - ((ViewGroup.MarginLayoutParams) n7).bottomMargin));
        rect.set(iMax, iMax2, i + iMax, i2 + iMax2);
    }

    public final void i(View view, Rect rect, boolean z) {
        if (view.isLayoutRequested() || view.getVisibility() == 8) {
            rect.setEmpty();
        } else if (z) {
            k(view, rect);
        } else {
            rect.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        }
    }

    public final ArrayList j(View view) {
        C0104co c0104co = (C0104co) this.b.b;
        int i = c0104co.c;
        ArrayList arrayList = null;
        for (int i2 = 0; i2 < i; i2++) {
            ArrayList arrayList2 = (ArrayList) c0104co.i(i2);
            if (arrayList2 != null && arrayList2.contains(view)) {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.add(c0104co.f(i2));
            }
        }
        ArrayList arrayList3 = this.d;
        arrayList3.clear();
        if (arrayList != null) {
            arrayList3.addAll(arrayList);
        }
        return arrayList3;
    }

    public final void k(View view, Rect rect) {
        ThreadLocal threadLocal = Ms.a;
        rect.set(0, 0, view.getWidth(), view.getHeight());
        ThreadLocal threadLocal2 = Ms.a;
        Matrix matrix = (Matrix) threadLocal2.get();
        if (matrix == null) {
            matrix = new Matrix();
            threadLocal2.set(matrix);
        } else {
            matrix.reset();
        }
        Ms.a(this, view, matrix);
        ThreadLocal threadLocal3 = Ms.b;
        RectF rectF = (RectF) threadLocal3.get();
        if (rectF == null) {
            rectF = new RectF();
            threadLocal3.set(rectF);
        }
        rectF.set(rect);
        matrix.mapRect(rectF);
        rect.set((int) (rectF.left + 0.5f), (int) (rectF.top + 0.5f), (int) (rectF.right + 0.5f), (int) (rectF.bottom + 0.5f));
    }

    public final int m(int i) {
        int[] iArr = this.i;
        if (iArr == null) {
            Log.e("CoordinatorLayout", "No keylines defined for " + this + " - attempted index lookup " + i);
            return 0;
        }
        if (i >= 0 && i < iArr.length) {
            return iArr[i];
        }
        Log.e("CoordinatorLayout", "Keyline index " + i + " out of range for " + this);
        return 0;
    }

    public final boolean o(View view, int i, int i2) {
        Zj zj = x;
        Rect rectG = g();
        k(view, rectG);
        try {
            return rectG.contains(i, i2);
        } finally {
            rectG.setEmpty();
            zj.c(rectG);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        t(false);
        if (this.m) {
            if (this.l == null) {
                this.l = new O7(this);
            }
            getViewTreeObserver().addOnPreDrawListener(this.l);
        }
        if (this.n == null) {
            WeakHashMap weakHashMap = Hs.a;
            if (getFitsSystemWindows()) {
                AbstractC0755xs.c(this);
            }
        }
        this.h = true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        t(false);
        if (this.m && this.l != null) {
            getViewTreeObserver().removeOnPreDrawListener(this.l);
        }
        View view = this.k;
        if (view != null) {
            b(view, 0);
        }
        this.h = false;
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        Drawable drawable;
        super.onDraw(canvas);
        if (!this.o || (drawable = this.p) == null) {
            return;
        }
        au auVar = this.n;
        int iB = auVar != null ? auVar.b() : 0;
        if (iB > 0) {
            drawable.setBounds(0, 0, getWidth(), iB);
            drawable.draw(canvas);
        }
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            t(true);
        }
        boolean zS = s(motionEvent, 0);
        if (actionMasked != 1 && actionMasked != 3) {
            return zS;
        }
        t(true);
        return zS;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        K7 k7;
        WeakHashMap weakHashMap = Hs.a;
        int layoutDirection = getLayoutDirection();
        ArrayList arrayList = this.a;
        int size = arrayList.size();
        for (int i5 = 0; i5 < size; i5++) {
            View view = (View) arrayList.get(i5);
            if (view.getVisibility() != 8 && ((k7 = ((N7) view.getLayoutParams()).a) == null || !k7.h(this, view, layoutDirection))) {
                q(view, layoutDirection);
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:185:0x008a A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:33:0x007d A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:34:0x007f  */
    /* JADX WARN: Code duplicated, block: B:36:0x0085  */
    /* JADX WARN: Code duplicated, block: B:39:0x0092  */
    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Not found exit edge by exit block: B:40:0x0096
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.checkLoopExits(LoopRegionMaker.java:272)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.makeLoopRegion(LoopRegionMaker.java:237)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:80)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:92)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:117)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:111)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:111)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:111)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.makeEndlessLoop(LoopRegionMaker.java:590)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:82)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:92)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:117)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:117)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:111)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.makeEndlessLoop(LoopRegionMaker.java:590)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:82)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:92)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:49)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
        */
    @Override // android.view.View
    public final void onMeasure(int r27, int r28) {
        /*
            Method dump skipped, instruction units count: 894
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.coordinatorlayout.widget.CoordinatorLayout.onMeasure(int, int):void");
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final boolean onNestedFling(View view, float f, float f2, boolean z) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() != 8) {
                N7 n7 = (N7) childAt.getLayoutParams();
                if (n7.a(0)) {
                    K7 k7 = n7.a;
                }
            }
        }
        return false;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final boolean onNestedPreFling(View view, float f, float f2) {
        K7 k7;
        int childCount = getChildCount();
        boolean zJ = false;
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() != 8) {
                N7 n7 = (N7) childAt.getLayoutParams();
                if (n7.a(0) && (k7 = n7.a) != null) {
                    zJ |= k7.j(view);
                }
            }
        }
        return zJ;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void onNestedPreScroll(View view, int i, int i2, int[] iArr) {
        c(view, i, i2, iArr, 0);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void onNestedScroll(View view, int i, int i2, int i3, int i4) {
        e(view, i, i2, i3, i4, 0);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void onNestedScrollAccepted(View view, View view2, int i) {
        a(view, view2, i, 0);
    }

    @Override // android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        Parcelable parcelable2;
        if (!(parcelable instanceof P7)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        P7 p7 = (P7) parcelable;
        super.onRestoreInstanceState(p7.a);
        SparseArray sparseArray = p7.c;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            int id = childAt.getId();
            K7 k7 = n(childAt).a;
            if (id != -1 && k7 != null && (parcelable2 = (Parcelable) sparseArray.get(id)) != null) {
                k7.n(childAt, parcelable2);
            }
        }
    }

    @Override // android.view.View
    public final Parcelable onSaveInstanceState() {
        Parcelable parcelableO;
        P7 p7 = new P7(super.onSaveInstanceState());
        SparseArray sparseArray = new SparseArray();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            int id = childAt.getId();
            K7 k7 = ((N7) childAt.getLayoutParams()).a;
            if (id != -1 && k7 != null && (parcelableO = k7.o(childAt)) != null) {
                sparseArray.append(id, parcelableO);
            }
        }
        p7.c = sparseArray;
        return p7;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final boolean onStartNestedScroll(View view, View view2, int i) {
        return f(view, view2, i, 0);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void onStopNestedScroll(View view) {
        b(view, 0);
    }

    /* JADX WARN: Code duplicated, block: B:14:0x002f  */
    /* JADX WARN: Code duplicated, block: B:15:0x0035 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:16:0x0037  */
    /* JADX WARN: Code duplicated, block: B:18:0x004a  */
    /* JADX WARN: Code duplicated, block: B:7:0x0015 A[PHI: r3
  0x0015: PHI (r3v4 boolean) = (r3v2 boolean), (r3v5 boolean) binds: [B:10:0x0022, B:5:0x0012] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        boolean zS;
        boolean zR;
        MotionEvent motionEventObtain;
        int actionMasked = motionEvent.getActionMasked();
        if (this.j == null) {
            zS = s(motionEvent, 1);
            if (!zS) {
                zR = false;
            }
            motionEventObtain = null;
            if (this.j == null) {
                zR |= super.onTouchEvent(motionEvent);
            } else if (zS) {
                long jUptimeMillis = SystemClock.uptimeMillis();
                motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 3, 0.0f, 0.0f, 0);
                super.onTouchEvent(motionEventObtain);
            }
            if (motionEventObtain != null) {
                motionEventObtain.recycle();
            }
            if (actionMasked == 1 && actionMasked != 3) {
                return zR;
            }
            t(false);
            return zR;
        }
        zS = false;
        K7 k7 = ((N7) this.j.getLayoutParams()).a;
        if (k7 != null) {
            zR = k7.r(this, this.j, motionEvent);
        } else {
            zR = false;
        }
        motionEventObtain = null;
        if (this.j == null) {
            zR |= super.onTouchEvent(motionEvent);
        } else if (zS) {
            long jUptimeMillis2 = SystemClock.uptimeMillis();
            motionEventObtain = MotionEvent.obtain(jUptimeMillis2, jUptimeMillis2, 3, 0.0f, 0.0f, 0);
            super.onTouchEvent(motionEventObtain);
        }
        if (motionEventObtain != null) {
            motionEventObtain.recycle();
        }
        if (actionMasked == 1) {
        }
        t(false);
        return zR;
    }

    /* JADX WARN: Code duplicated, block: B:107:0x0271  */
    /* JADX WARN: Code duplicated, block: B:33:0x00df  */
    public final void p(int i) {
        Rect rect;
        int i2;
        Rect rect2;
        int i3;
        ArrayList arrayList;
        int i4;
        boolean zD;
        boolean z;
        boolean z2;
        int width;
        int i5;
        int i6;
        int i7;
        int height;
        int i8;
        int i9;
        int i10;
        Rect rect3;
        Zj zj;
        K7 k7;
        WeakHashMap weakHashMap = Hs.a;
        int layoutDirection = getLayoutDirection();
        ArrayList arrayList2 = this.a;
        int size = arrayList2.size();
        Rect rectG = g();
        Rect rectG2 = g();
        Rect rectG3 = g();
        int i11 = 0;
        while (true) {
            Zj zj2 = x;
            if (i11 >= size) {
                Rect rect4 = rectG2;
                Rect rect5 = rectG3;
                rectG.setEmpty();
                zj2.c(rectG);
                rect4.setEmpty();
                zj2.c(rect4);
                rect5.setEmpty();
                zj2.c(rect5);
                return;
            }
            View view = (View) arrayList2.get(i11);
            N7 n7 = (N7) view.getLayoutParams();
            if (i != 0 || view.getVisibility() != 8) {
                int i12 = 0;
                while (i12 < i11) {
                    if (n7.l == ((View) arrayList2.get(i12))) {
                        N7 n8 = (N7) view.getLayoutParams();
                        if (n8.k != null) {
                            Rect rectG4 = g();
                            Rect rectG5 = g();
                            Rect rect6 = rectG3;
                            Rect rectG6 = g();
                            k(n8.k, rectG4);
                            i(view, rectG5, false);
                            int measuredWidth = view.getMeasuredWidth();
                            Zj zj3 = zj2;
                            int measuredHeight = view.getMeasuredHeight();
                            zj = zj3;
                            l(layoutDirection, rectG4, rectG6, n8, measuredWidth, measuredHeight);
                            rect3 = rect6;
                            boolean z3 = (rectG6.left == rectG5.left && rectG6.top == rectG5.top) ? false : true;
                            h(n8, rectG6, measuredWidth, measuredHeight);
                            int i13 = rectG6.left - rectG5.left;
                            int i14 = rectG6.top - rectG5.top;
                            if (i13 != 0) {
                                WeakHashMap weakHashMap2 = Hs.a;
                                view.offsetLeftAndRight(i13);
                            }
                            if (i14 != 0) {
                                WeakHashMap weakHashMap3 = Hs.a;
                                view.offsetTopAndBottom(i14);
                            }
                            if (z3 && (k7 = n8.a) != null) {
                                k7.d(this, view, n8.k);
                            }
                            rectG4.setEmpty();
                            zj.c(rectG4);
                            rectG5.setEmpty();
                            zj.c(rectG5);
                            rectG6.setEmpty();
                            zj.c(rectG6);
                        } else {
                            rect3 = rectG3;
                            zj = zj2;
                        }
                    } else {
                        rect3 = rectG3;
                        zj = zj2;
                    }
                    zj2 = zj;
                    i12++;
                    rectG2 = rectG2;
                    i11 = i11;
                    arrayList2 = arrayList2;
                    size = size;
                    rectG3 = rect3;
                }
                ArrayList arrayList3 = arrayList2;
                rect = rectG2;
                Rect rect7 = rectG3;
                i2 = i11;
                Yj yj = zj2;
                int i15 = size;
                i(view, rect, true);
                if (n7.g != 0 && !rect.isEmpty()) {
                    int absoluteGravity = Gravity.getAbsoluteGravity(n7.g, layoutDirection);
                    int i16 = absoluteGravity & 112;
                    if (i16 == 48) {
                        rectG.top = Math.max(rectG.top, rect.bottom);
                    } else if (i16 == 80) {
                        rectG.bottom = Math.max(rectG.bottom, getHeight() - rect.top);
                    }
                    int i17 = absoluteGravity & 7;
                    if (i17 == 3) {
                        rectG.left = Math.max(rectG.left, rect.right);
                    } else if (i17 == 5) {
                        rectG.right = Math.max(rectG.right, getWidth() - rect.left);
                    }
                }
                if (n7.h != 0 && view.getVisibility() == 0) {
                    WeakHashMap weakHashMap4 = Hs.a;
                    if (view.isLaidOut() && view.getWidth() > 0 && view.getHeight() > 0) {
                        N7 n9 = (N7) view.getLayoutParams();
                        K7 k8 = n9.a;
                        Rect rectG7 = g();
                        Rect rectG8 = g();
                        rectG8.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
                        if (k8 == null || !k8.a(view)) {
                            rectG7.set(rectG8);
                        } else if (!rectG8.contains(rectG7)) {
                            throw new IllegalArgumentException("Rect should be within the child's bounds. Rect:" + rectG7.toShortString() + " | Bounds:" + rectG8.toShortString());
                        }
                        rectG8.setEmpty();
                        yj.c(rectG8);
                        if (rectG7.isEmpty()) {
                            rectG7.setEmpty();
                            yj.c(rectG7);
                        } else {
                            int absoluteGravity2 = Gravity.getAbsoluteGravity(n9.h, layoutDirection);
                            if ((absoluteGravity2 & 48) != 48 || (i9 = (rectG7.top - ((ViewGroup.MarginLayoutParams) n9).topMargin) - n9.j) >= (i10 = rectG.top)) {
                                z = false;
                            } else {
                                v(view, i10 - i9);
                                z = true;
                            }
                            if ((absoluteGravity2 & 80) == 80 && (height = ((getHeight() - rectG7.bottom) - ((ViewGroup.MarginLayoutParams) n9).bottomMargin) + n9.j) < (i8 = rectG.bottom)) {
                                v(view, height - i8);
                                z = true;
                            }
                            if (!z) {
                                v(view, 0);
                            }
                            if ((absoluteGravity2 & 3) != 3 || (i6 = (rectG7.left - ((ViewGroup.MarginLayoutParams) n9).leftMargin) - n9.i) >= (i7 = rectG.left)) {
                                z2 = false;
                            } else {
                                u(view, i7 - i6);
                                z2 = true;
                            }
                            if ((absoluteGravity2 & 5) == 5 && (width = ((getWidth() - rectG7.right) - ((ViewGroup.MarginLayoutParams) n9).rightMargin) + n9.i) < (i5 = rectG.right)) {
                                u(view, width - i5);
                                z2 = true;
                            }
                            if (!z2) {
                                u(view, 0);
                            }
                            rectG7.setEmpty();
                            yj.c(rectG7);
                        }
                    }
                }
                if (i != 2) {
                    rect2 = rect7;
                    rect2.set(((N7) view.getLayoutParams()).p);
                    if (rect2.equals(rect)) {
                        arrayList = arrayList3;
                        i3 = i15;
                    } else {
                        ((N7) view.getLayoutParams()).p.set(rect);
                    }
                } else {
                    rect2 = rect7;
                }
                int i18 = i2 + 1;
                i3 = i15;
                while (true) {
                    arrayList = arrayList3;
                    if (i18 >= i3) {
                        break;
                    }
                    View view2 = (View) arrayList.get(i18);
                    N7 n10 = (N7) view2.getLayoutParams();
                    K7 k9 = n10.a;
                    if (k9 == null || !k9.b(view2, view)) {
                        i4 = 1;
                    } else if (i == 0 && n10.o) {
                        n10.o = false;
                        i4 = 1;
                    } else {
                        if (i != 2) {
                            zD = k9.d(this, view2, view);
                        } else {
                            k9.e(this, view);
                            zD = true;
                        }
                        i4 = 1;
                        if (i == 1) {
                            n10.o = zD;
                        }
                    }
                    i18 += i4;
                    arrayList3 = arrayList;
                }
            } else {
                int i19 = size;
                arrayList = arrayList2;
                i3 = i19;
                rect = rectG2;
                rect2 = rectG3;
                i2 = i11;
            }
            i11 = i2 + 1;
            ArrayList arrayList4 = arrayList;
            size = i3;
            arrayList2 = arrayList4;
            rectG3 = rect2;
            rectG2 = rect;
        }
    }

    public final void q(View view, int i) {
        int i2;
        N7 n7 = (N7) view.getLayoutParams();
        View view2 = n7.k;
        if (view2 == null && n7.f != -1) {
            throw new IllegalStateException("An anchor may not be changed after CoordinatorLayout measurement begins before layout is complete.");
        }
        Zj zj = x;
        if (view2 != null) {
            Rect rectG = g();
            Rect rectG2 = g();
            try {
                k(view2, rectG);
                N7 n8 = (N7) view.getLayoutParams();
                int measuredWidth = view.getMeasuredWidth();
                int measuredHeight = view.getMeasuredHeight();
                l(i, rectG, rectG2, n8, measuredWidth, measuredHeight);
                h(n8, rectG2, measuredWidth, measuredHeight);
                view.layout(rectG2.left, rectG2.top, rectG2.right, rectG2.bottom);
                return;
            } finally {
                rectG.setEmpty();
                zj.c(rectG);
                rectG2.setEmpty();
                zj.c(rectG2);
            }
        }
        int i3 = n7.e;
        if (i3 < 0) {
            N7 n9 = (N7) view.getLayoutParams();
            Rect rectG3 = g();
            rectG3.set(getPaddingLeft() + ((ViewGroup.MarginLayoutParams) n9).leftMargin, getPaddingTop() + ((ViewGroup.MarginLayoutParams) n9).topMargin, (getWidth() - getPaddingRight()) - ((ViewGroup.MarginLayoutParams) n9).rightMargin, (getHeight() - getPaddingBottom()) - ((ViewGroup.MarginLayoutParams) n9).bottomMargin);
            if (this.n != null) {
                WeakHashMap weakHashMap = Hs.a;
                if (getFitsSystemWindows() && !view.getFitsSystemWindows()) {
                    rectG3.left = this.n.a.j().a + rectG3.left;
                    rectG3.top = this.n.b() + rectG3.top;
                    rectG3.right -= this.n.a.j().c;
                    rectG3.bottom -= this.n.a();
                }
            }
            Rect rectG4 = g();
            int i4 = n9.c;
            if ((i4 & 7) == 0) {
                i4 |= 8388611;
            }
            if ((i4 & 112) == 0) {
                i4 |= 48;
            }
            Gravity.apply(i4, view.getMeasuredWidth(), view.getMeasuredHeight(), rectG3, rectG4, i);
            view.layout(rectG4.left, rectG4.top, rectG4.right, rectG4.bottom);
            rectG3.setEmpty();
            zj.c(rectG3);
            rectG4.setEmpty();
            zj.c(rectG4);
            return;
        }
        N7 n10 = (N7) view.getLayoutParams();
        int i5 = n10.c;
        if (i5 == 0) {
            i5 = 8388661;
        }
        int absoluteGravity = Gravity.getAbsoluteGravity(i5, i);
        int i6 = absoluteGravity & 7;
        int i7 = absoluteGravity & 112;
        int width = getWidth();
        int height = getHeight();
        int measuredWidth2 = view.getMeasuredWidth();
        int measuredHeight2 = view.getMeasuredHeight();
        if (i == 1) {
            i3 = width - i3;
        }
        int iM = m(i3) - measuredWidth2;
        if (i6 == 1) {
            iM += measuredWidth2 / 2;
        } else if (i6 == 5) {
            iM += measuredWidth2;
        }
        if (i7 != 16) {
            i2 = i7 != 80 ? 0 : measuredHeight2;
        } else {
            i2 = measuredHeight2 / 2;
        }
        int iMax = Math.max(getPaddingLeft() + ((ViewGroup.MarginLayoutParams) n10).leftMargin, Math.min(iM, ((width - getPaddingRight()) - measuredWidth2) - ((ViewGroup.MarginLayoutParams) n10).rightMargin));
        int iMax2 = Math.max(getPaddingTop() + ((ViewGroup.MarginLayoutParams) n10).topMargin, Math.min(i2, ((height - getPaddingBottom()) - measuredHeight2) - ((ViewGroup.MarginLayoutParams) n10).bottomMargin));
        view.layout(iMax, iMax2, measuredWidth2 + iMax, measuredHeight2 + iMax2);
    }

    public final void r(View view, int i, int i2, int i3) {
        measureChildWithMargins(view, i, i2, i3, 0);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final boolean requestChildRectangleOnScreen(View view, Rect rect, boolean z) {
        K7 k7 = ((N7) view.getLayoutParams()).a;
        if (k7 == null || !k7.m(this, view, rect, z)) {
            return super.requestChildRectangleOnScreen(view, rect, z);
        }
        return true;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void requestDisallowInterceptTouchEvent(boolean z) {
        super.requestDisallowInterceptTouchEvent(z);
        if (!z || this.g) {
            return;
        }
        t(false);
        this.g = true;
    }

    public final boolean s(MotionEvent motionEvent, int i) {
        int actionMasked = motionEvent.getActionMasked();
        ArrayList arrayList = this.c;
        arrayList.clear();
        boolean zIsChildrenDrawingOrderEnabled = isChildrenDrawingOrderEnabled();
        int childCount = getChildCount();
        for (int i2 = childCount - 1; i2 >= 0; i2--) {
            arrayList.add(getChildAt(zIsChildrenDrawingOrderEnabled ? getChildDrawingOrder(childCount, i2) : i2));
        }
        Q7 q7 = w;
        if (q7 != null) {
            Collections.sort(arrayList, q7);
        }
        int size = arrayList.size();
        MotionEvent motionEventObtain = null;
        boolean zG = false;
        for (int i3 = 0; i3 < size; i3++) {
            View view = (View) arrayList.get(i3);
            K7 k7 = ((N7) view.getLayoutParams()).a;
            if (zG && actionMasked != 0) {
                if (k7 != null) {
                    if (motionEventObtain == null) {
                        long jUptimeMillis = SystemClock.uptimeMillis();
                        motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 3, 0.0f, 0.0f, 0);
                    }
                    if (i == 0) {
                        k7.g(this, view, motionEventObtain);
                    } else if (i == 1) {
                        k7.r(this, view, motionEventObtain);
                    }
                }
            } else if (!zG && k7 != null) {
                if (i == 0) {
                    zG = k7.g(this, view, motionEvent);
                } else if (i == 1) {
                    zG = k7.r(this, view, motionEvent);
                }
                if (zG) {
                    this.j = view;
                }
            }
        }
        arrayList.clear();
        return zG;
    }

    @Override // android.view.View
    public final void setFitsSystemWindows(boolean z) {
        super.setFitsSystemWindows(z);
        w();
    }

    @Override // android.view.ViewGroup
    public final void setOnHierarchyChangeListener(ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener) {
        this.q = onHierarchyChangeListener;
    }

    @Override // android.view.View
    public final void setVisibility(int i) {
        super.setVisibility(i);
        boolean z = i == 0;
        Drawable drawable = this.p;
        if (drawable == null || drawable.isVisible() == z) {
            return;
        }
        drawable.setVisible(z, false);
    }

    public final void t(boolean z) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            K7 k7 = ((N7) childAt.getLayoutParams()).a;
            if (k7 != null) {
                long jUptimeMillis = SystemClock.uptimeMillis();
                MotionEvent motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 3, 0.0f, 0.0f, 0);
                if (z) {
                    k7.g(this, childAt, motionEventObtain);
                } else {
                    k7.r(this, childAt, motionEventObtain);
                }
                motionEventObtain.recycle();
            }
        }
        for (int i2 = 0; i2 < childCount; i2++) {
            ((N7) getChildAt(i2).getLayoutParams()).getClass();
        }
        this.j = null;
        this.g = false;
    }

    @Override // android.view.View
    public final boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.p;
    }

    public final void w() {
        WeakHashMap weakHashMap = Hs.a;
        if (!getFitsSystemWindows()) {
            AbstractC0817zs.l(this, null);
            return;
        }
        if (this.r == null) {
            this.r = new Ys(14, this);
        }
        AbstractC0817zs.l(this, this.r);
        setSystemUiVisibility(1280);
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof N7) {
            return new N7((N7) layoutParams);
        }
        return layoutParams instanceof ViewGroup.MarginLayoutParams ? new N7((ViewGroup.MarginLayoutParams) layoutParams) : new N7(layoutParams);
    }
}
