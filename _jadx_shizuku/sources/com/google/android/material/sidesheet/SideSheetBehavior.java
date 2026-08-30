package com.google.android.material.sidesheet;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.AbsSavedState;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.PathInterpolator;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.sidesheet.SideSheetBehavior;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.WeakHashMap;
import moe.shizuku.privileged.api.R;
import rikka.shizuku.AbstractC0046as;
import rikka.shizuku.AbstractC0535qo;
import rikka.shizuku.AbstractC0688vn;
import rikka.shizuku.AbstractC0817zs;
import rikka.shizuku.C0018a0;
import rikka.shizuku.C0042ao;
import rikka.shizuku.C0317jn;
import rikka.shizuku.C0361l5;
import rikka.shizuku.C0403mg;
import rikka.shizuku.C0423n5;
import rikka.shizuku.G;
import rikka.shizuku.Hs;
import rikka.shizuku.InterfaceC0449o0;
import rikka.shizuku.Js;
import rikka.shizuku.K7;
import rikka.shizuku.Lk;
import rikka.shizuku.N7;
import rikka.shizuku.X8;
import rikka.shizuku.Xh;

/* JADX INFO: loaded from: classes.dex */
public class SideSheetBehavior<V extends View> extends K7 {
    public AbstractC0688vn a;
    public final Xh b;
    public final ColorStateList c;
    public final C0317jn d;
    public final C0423n5 e;
    public final float f;
    public final boolean g;
    public int h;
    public Js i;
    public boolean j;
    public final float k;
    public int l;
    public int m;
    public int n;
    public int o;
    public WeakReference p;
    public WeakReference q;
    public final int r;
    public VelocityTracker s;
    public int t;
    public final LinkedHashSet u;
    public final C0361l5 v;

    public SideSheetBehavior() {
        this.e = new C0423n5(this);
        this.g = true;
        this.h = 5;
        this.k = 0.1f;
        this.r = -1;
        this.u = new LinkedHashSet();
        this.v = new C0361l5(this, 1);
    }

    @Override // rikka.shizuku.K7
    public final void c(N7 n7) {
        this.p = null;
        this.i = null;
    }

    @Override // rikka.shizuku.K7
    public final void f() {
        this.p = null;
        this.i = null;
    }

    @Override // rikka.shizuku.K7
    public final boolean g(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
        Js js;
        VelocityTracker velocityTracker;
        if ((!view.isShown() && Hs.e(view) == null) || !this.g) {
            this.j = true;
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0 && (velocityTracker = this.s) != null) {
            velocityTracker.recycle();
            this.s = null;
        }
        if (this.s == null) {
            this.s = VelocityTracker.obtain();
        }
        this.s.addMovement(motionEvent);
        if (actionMasked == 0) {
            this.t = (int) motionEvent.getX();
        } else if ((actionMasked == 1 || actionMasked == 3) && this.j) {
            this.j = false;
            return false;
        }
        return (this.j || (js = this.i) == null || !js.p(motionEvent)) ? false : true;
    }

    @Override // rikka.shizuku.K7
    public final boolean h(CoordinatorLayout coordinatorLayout, View view, int i) {
        View view2;
        View view3;
        int i2;
        View viewFindViewById;
        int iP = 0;
        int i3 = 1;
        Xh xh = this.b;
        WeakHashMap weakHashMap = Hs.a;
        if (coordinatorLayout.getFitsSystemWindows() && !view.getFitsSystemWindows()) {
            view.setFitsSystemWindows(true);
        }
        if (this.p == null) {
            this.p = new WeakReference(view);
            Context context = view.getContext();
            AbstractC0046as.P(context, R.attr._0_resource_name_obfuscated_res_0x7f040350, new PathInterpolator(0.0f, 0.0f, 0.0f, 1.0f));
            AbstractC0046as.O(context, R.attr._0_resource_name_obfuscated_res_0x7f04033f, 300);
            AbstractC0046as.O(context, R.attr._0_resource_name_obfuscated_res_0x7f040344, 150);
            AbstractC0046as.O(context, R.attr._0_resource_name_obfuscated_res_0x7f040343, 100);
            Resources resources = view.getResources();
            resources.getDimension(R.dimen._0_resource_name_obfuscated_res_0x7f0700c6);
            resources.getDimension(R.dimen._0_resource_name_obfuscated_res_0x7f0700c5);
            resources.getDimension(R.dimen._0_resource_name_obfuscated_res_0x7f0700c7);
            if (xh != null) {
                view.setBackground(xh);
                float fE = this.f;
                if (fE == -1.0f) {
                    fE = AbstractC0817zs.e(view);
                }
                xh.k(fE);
            } else {
                ColorStateList colorStateList = this.c;
                if (colorStateList != null) {
                    AbstractC0817zs.i(view, colorStateList);
                }
            }
            int i4 = this.h == 5 ? 4 : 0;
            if (view.getVisibility() != i4) {
                view.setVisibility(i4);
            }
            v();
            if (view.getImportantForAccessibility() == 0) {
                view.setImportantForAccessibility(1);
            }
            if (Hs.e(view) == null) {
                Hs.n(view, view.getResources().getString(R.string._0_resource_name_obfuscated_res_0x7f11011f));
            }
        }
        int i5 = Gravity.getAbsoluteGravity(((N7) view.getLayoutParams()).c, i) == 3 ? 1 : 0;
        AbstractC0688vn abstractC0688vn = this.a;
        if (abstractC0688vn == null || abstractC0688vn.r() != i5) {
            C0317jn c0317jn = this.d;
            N7 n7 = null;
            if (i5 == 0) {
                this.a = new C0403mg(this, i3);
                if (c0317jn != null) {
                    WeakReference weakReference = this.p;
                    if (weakReference != null && (view3 = (View) weakReference.get()) != null && (view3.getLayoutParams() instanceof N7)) {
                        n7 = (N7) view3.getLayoutParams();
                    }
                    if (n7 == null || ((ViewGroup.MarginLayoutParams) n7).rightMargin <= 0) {
                        C0317jn c0317jnG = c0317jn.g();
                        c0317jnG.f = new G(0.0f);
                        c0317jnG.g = new G(0.0f);
                        C0317jn c0317jnA = c0317jnG.a();
                        if (xh != null) {
                            xh.a(c0317jnA);
                        }
                    }
                }
            } else {
                if (i5 != 1) {
                    throw new IllegalArgumentException("Invalid sheet edge position value: " + i5 + ". Must be 0 or 1.");
                }
                this.a = new C0403mg(this, iP);
                if (c0317jn != null) {
                    WeakReference weakReference2 = this.p;
                    if (weakReference2 != null && (view2 = (View) weakReference2.get()) != null && (view2.getLayoutParams() instanceof N7)) {
                        n7 = (N7) view2.getLayoutParams();
                    }
                    if (n7 == null || ((ViewGroup.MarginLayoutParams) n7).leftMargin <= 0) {
                        C0317jn c0317jnG2 = c0317jn.g();
                        c0317jnG2.e = new G(0.0f);
                        c0317jnG2.h = new G(0.0f);
                        C0317jn c0317jnA2 = c0317jnG2.a();
                        if (xh != null) {
                            xh.a(c0317jnA2);
                        }
                    }
                }
            }
        }
        if (this.i == null) {
            this.i = new Js(coordinatorLayout.getContext(), coordinatorLayout, this.v);
        }
        int iP2 = this.a.p(view);
        coordinatorLayout.q(view, i);
        this.m = coordinatorLayout.getWidth();
        this.n = this.a.q(coordinatorLayout);
        this.l = view.getWidth();
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        this.o = marginLayoutParams != null ? this.a.d(marginLayoutParams) : 0;
        int i6 = this.h;
        if (i6 == 1 || i6 == 2) {
            iP = iP2 - this.a.p(view);
        } else if (i6 != 3) {
            if (i6 != 5) {
                throw new IllegalStateException("Unexpected value: " + this.h);
            }
            iP = this.a.m();
        }
        view.offsetLeftAndRight(iP);
        if (this.q == null && (i2 = this.r) != -1 && (viewFindViewById = coordinatorLayout.findViewById(i2)) != null) {
            this.q = new WeakReference(viewFindViewById);
        }
        Iterator it = this.u.iterator();
        while (it.hasNext()) {
            if (it.next() != null) {
                throw new ClassCastException();
            }
        }
        return true;
    }

    @Override // rikka.shizuku.K7
    public final boolean i(CoordinatorLayout coordinatorLayout, View view, int i, int i2, int i3) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        view.measure(ViewGroup.getChildMeasureSpec(i, coordinatorLayout.getPaddingRight() + coordinatorLayout.getPaddingLeft() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + i2, marginLayoutParams.width), ViewGroup.getChildMeasureSpec(i3, coordinatorLayout.getPaddingBottom() + coordinatorLayout.getPaddingTop() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin, marginLayoutParams.height));
        return true;
    }

    @Override // rikka.shizuku.K7
    public final void n(View view, Parcelable parcelable) {
        int i = ((C0042ao) parcelable).c;
        if (i == 1 || i == 2) {
            i = 5;
        }
        this.h = i;
    }

    @Override // rikka.shizuku.K7
    public final Parcelable o(View view) {
        AbsSavedState absSavedState = View.BaseSavedState.EMPTY_STATE;
        return new C0042ao(this);
    }

    @Override // rikka.shizuku.K7
    public final boolean r(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
        VelocityTracker velocityTracker;
        if (!view.isShown()) {
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (this.h == 1 && actionMasked == 0) {
            return true;
        }
        if (t()) {
            this.i.j(motionEvent);
        }
        if (actionMasked == 0 && (velocityTracker = this.s) != null) {
            velocityTracker.recycle();
            this.s = null;
        }
        if (this.s == null) {
            this.s = VelocityTracker.obtain();
        }
        this.s.addMovement(motionEvent);
        if (t() && actionMasked == 2 && !this.j && t()) {
            float fAbs = Math.abs(this.t - motionEvent.getX());
            Js js = this.i;
            if (fAbs > js.b) {
                js.b(view, motionEvent.getPointerId(motionEvent.getActionIndex()));
            }
        }
        return !this.j;
    }

    public final void s(int i) {
        View view;
        if (this.h == i) {
            return;
        }
        this.h = i;
        WeakReference weakReference = this.p;
        if (weakReference == null || (view = (View) weakReference.get()) == null) {
            return;
        }
        int i2 = this.h == 5 ? 4 : 0;
        if (view.getVisibility() != i2) {
            view.setVisibility(i2);
        }
        Iterator it = this.u.iterator();
        if (it.hasNext()) {
            it.next().getClass();
            throw new ClassCastException();
        }
        v();
    }

    public final boolean t() {
        if (this.i != null) {
            return this.g || this.h == 1;
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x002d, code lost:
    
        if (r1.o(r0, r3.getTop()) != false) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x004b, code lost:
    
        if (r3 != false) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x004d, code lost:
    
        s(2);
        r2.e.a(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0056, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void u(View view, int i, boolean z) {
        int iL;
        if (i == 3) {
            iL = this.a.l();
        } else {
            if (i != 5) {
                throw new IllegalArgumentException(AbstractC0535qo.b(i, "Invalid state to get outer edge offset: "));
            }
            iL = this.a.m();
        }
        Js js = this.i;
        if (js != null) {
            if (!z) {
                int top = view.getTop();
                js.r = view;
                js.c = -1;
                boolean zH = js.h(iL, top, 0, 0);
                if (!zH && js.a == 0 && js.r != null) {
                    js.r = null;
                }
            }
        }
        s(i);
    }

    public final void v() {
        View view;
        WeakReference weakReference = this.p;
        if (weakReference == null || (view = (View) weakReference.get()) == null) {
            return;
        }
        Hs.j(view, 262144);
        Hs.h(view, 0);
        Hs.j(view, 1048576);
        Hs.h(view, 0);
        final int i = 5;
        if (this.h != 5) {
            Hs.k(view, C0018a0.j, new InterfaceC0449o0() { // from class: rikka.shizuku.Zn
                @Override // rikka.shizuku.InterfaceC0449o0
                public final boolean c(View view2) {
                    int i2 = 1;
                    SideSheetBehavior sideSheetBehavior = this.a;
                    sideSheetBehavior.getClass();
                    int i3 = i;
                    if (i3 == 1 || i3 == 2) {
                        StringBuilder sb = new StringBuilder("STATE_");
                        sb.append(i3 == 1 ? "DRAGGING" : "SETTLING");
                        sb.append(" should not be set externally.");
                        throw new IllegalArgumentException(sb.toString());
                    }
                    WeakReference weakReference2 = sideSheetBehavior.p;
                    if (weakReference2 == null || weakReference2.get() == null) {
                        sideSheetBehavior.s(i3);
                        return true;
                    }
                    View view3 = (View) sideSheetBehavior.p.get();
                    Wl wl = new Wl(i3, i2, sideSheetBehavior);
                    ViewParent parent = view3.getParent();
                    if (parent != null && parent.isLayoutRequested()) {
                        WeakHashMap weakHashMap = Hs.a;
                        if (view3.isAttachedToWindow()) {
                            view3.post(wl);
                            return true;
                        }
                    }
                    wl.run();
                    return true;
                }
            });
        }
        final int i2 = 3;
        if (this.h != 3) {
            Hs.k(view, C0018a0.h, new InterfaceC0449o0() { // from class: rikka.shizuku.Zn
                @Override // rikka.shizuku.InterfaceC0449o0
                public final boolean c(View view2) {
                    int i3 = 1;
                    SideSheetBehavior sideSheetBehavior = this.a;
                    sideSheetBehavior.getClass();
                    int i4 = i2;
                    if (i4 == 1 || i4 == 2) {
                        StringBuilder sb = new StringBuilder("STATE_");
                        sb.append(i4 == 1 ? "DRAGGING" : "SETTLING");
                        sb.append(" should not be set externally.");
                        throw new IllegalArgumentException(sb.toString());
                    }
                    WeakReference weakReference2 = sideSheetBehavior.p;
                    if (weakReference2 == null || weakReference2.get() == null) {
                        sideSheetBehavior.s(i4);
                        return true;
                    }
                    View view3 = (View) sideSheetBehavior.p.get();
                    Wl wl = new Wl(i4, i3, sideSheetBehavior);
                    ViewParent parent = view3.getParent();
                    if (parent != null && parent.isLayoutRequested()) {
                        WeakHashMap weakHashMap = Hs.a;
                        if (view3.isAttachedToWindow()) {
                            view3.post(wl);
                            return true;
                        }
                    }
                    wl.run();
                    return true;
                }
            });
        }
    }

    public SideSheetBehavior(Context context, AttributeSet attributeSet) {
        this.e = new C0423n5(this);
        this.g = true;
        this.h = 5;
        this.k = 0.1f;
        this.r = -1;
        this.u = new LinkedHashSet();
        this.v = new C0361l5(this, 1);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Lk.C);
        if (typedArrayObtainStyledAttributes.hasValue(3)) {
            this.c = X8.w(context, typedArrayObtainStyledAttributes, 3);
        }
        if (typedArrayObtainStyledAttributes.hasValue(6)) {
            this.d = C0317jn.c(context, attributeSet, 0, R.style._0_resource_name_obfuscated_res_0x7f120432).a();
        }
        if (typedArrayObtainStyledAttributes.hasValue(5)) {
            int resourceId = typedArrayObtainStyledAttributes.getResourceId(5, -1);
            this.r = resourceId;
            WeakReference weakReference = this.q;
            if (weakReference != null) {
                weakReference.clear();
            }
            this.q = null;
            WeakReference weakReference2 = this.p;
            if (weakReference2 != null) {
                View view = (View) weakReference2.get();
                if (resourceId != -1) {
                    WeakHashMap weakHashMap = Hs.a;
                    if (view.isLaidOut()) {
                        view.requestLayout();
                    }
                }
            }
        }
        C0317jn c0317jn = this.d;
        if (c0317jn != null) {
            Xh xh = new Xh(c0317jn);
            this.b = xh;
            xh.j(context);
            ColorStateList colorStateList = this.c;
            if (colorStateList != null) {
                this.b.l(colorStateList);
            } else {
                TypedValue typedValue = new TypedValue();
                context.getTheme().resolveAttribute(android.R.attr.colorBackground, typedValue, true);
                this.b.setTint(typedValue.data);
            }
        }
        this.f = typedArrayObtainStyledAttributes.getDimension(2, -1.0f);
        this.g = typedArrayObtainStyledAttributes.getBoolean(4, true);
        typedArrayObtainStyledAttributes.recycle();
        ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
    }
}
