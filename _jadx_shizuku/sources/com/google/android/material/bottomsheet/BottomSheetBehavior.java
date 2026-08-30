package com.google.android.material.bottomsheet;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.view.AbsSavedState;
import android.view.MotionEvent;
import android.view.RoundedCorner;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.PathInterpolator;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.WeakHashMap;
import moe.shizuku.privileged.api.R;
import rikka.shizuku.AbstractC0046as;
import rikka.shizuku.AbstractC0535qo;
import rikka.shizuku.AbstractC0755xs;
import rikka.shizuku.AbstractC0817zs;
import rikka.shizuku.C0018a0;
import rikka.shizuku.C0232gt;
import rikka.shizuku.C0299j5;
import rikka.shizuku.C0317jn;
import rikka.shizuku.C0330k5;
import rikka.shizuku.C0361l5;
import rikka.shizuku.C0392m5;
import rikka.shizuku.C0423n5;
import rikka.shizuku.C0796z7;
import rikka.shizuku.Et;
import rikka.shizuku.Ft;
import rikka.shizuku.Gt;
import rikka.shizuku.Hs;
import rikka.shizuku.I1;
import rikka.shizuku.Js;
import rikka.shizuku.K7;
import rikka.shizuku.Lk;
import rikka.shizuku.N7;
import rikka.shizuku.Q;
import rikka.shizuku.R2;
import rikka.shizuku.RunnableC0390m3;
import rikka.shizuku.S;
import rikka.shizuku.ViewOnAttachStateChangeListenerC0201ft;
import rikka.shizuku.Wh;
import rikka.shizuku.X8;
import rikka.shizuku.Xh;

/* JADX INFO: loaded from: classes.dex */
public class BottomSheetBehavior<V extends View> extends K7 {
    public final C0423n5 A;
    public final ValueAnimator B;
    public final int C;
    public int D;
    public int E;
    public final float F;
    public int G;
    public final float H;
    public boolean I;
    public boolean J;
    public final boolean K;
    public int L;
    public Js M;
    public boolean N;
    public int O;
    public boolean P;
    public final float Q;
    public int R;
    public int S;
    public int T;
    public WeakReference U;
    public WeakReference V;
    public final ArrayList W;
    public VelocityTracker X;
    public int Y;
    public int Z;
    public final int a;
    public boolean a0;
    public boolean b;
    public HashMap b0;
    public final float c;
    public final SparseIntArray c0;
    public final int d;
    public final C0361l5 d0;
    public int e;
    public boolean f;
    public int g;
    public final int h;
    public final Xh i;
    public final ColorStateList j;
    public final int k;
    public final int l;
    public int m;
    public final boolean n;
    public final boolean o;
    public final boolean p;
    public final boolean q;
    public final boolean r;
    public final boolean s;
    public final boolean t;
    public final boolean u;
    public int v;
    public int w;
    public final boolean x;
    public final C0317jn y;
    public boolean z;

    public BottomSheetBehavior() {
        this.a = 0;
        this.b = true;
        this.k = -1;
        this.l = -1;
        this.A = new C0423n5(this);
        this.F = 0.5f;
        this.H = -1.0f;
        this.K = true;
        this.L = 4;
        this.Q = 0.1f;
        this.W = new ArrayList();
        this.Z = -1;
        this.c0 = new SparseIntArray();
        this.d0 = new C0361l5(this, 0);
    }

    public static View w(View view) {
        if (view.getVisibility() != 0) {
            return null;
        }
        WeakHashMap weakHashMap = Hs.a;
        if (AbstractC0817zs.h(view)) {
            return view;
        }
        if (!(view instanceof ViewGroup)) {
            return null;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View viewW = w(viewGroup.getChildAt(i));
            if (viewW != null) {
                return viewW;
            }
        }
        return null;
    }

    public static int x(int i, int i2, int i3, int i4) {
        int childMeasureSpec = ViewGroup.getChildMeasureSpec(i, i2, i4);
        if (i3 == -1) {
            return childMeasureSpec;
        }
        int mode = View.MeasureSpec.getMode(childMeasureSpec);
        int size = View.MeasureSpec.getSize(childMeasureSpec);
        if (mode == 1073741824) {
            return View.MeasureSpec.makeMeasureSpec(Math.min(size, i3), 1073741824);
        }
        if (size != 0) {
            i3 = Math.min(size, i3);
        }
        return View.MeasureSpec.makeMeasureSpec(i3, Integer.MIN_VALUE);
    }

    public final boolean A() {
        WeakReference weakReference = this.U;
        if (weakReference != null && weakReference.get() != null) {
            int[] iArr = new int[2];
            ((View) this.U.get()).getLocationOnScreen(iArr);
            if (iArr[1] == 0) {
                return true;
            }
        }
        return false;
    }

    public final void B(int i) {
        if (i == -1) {
            if (this.f) {
                return;
            } else {
                this.f = true;
            }
        } else {
            if (!this.f && this.e == i) {
                return;
            }
            this.f = false;
            this.e = Math.max(0, i);
        }
        J();
    }

    public final void C(int i) {
        if (i == 1 || i == 2) {
            StringBuilder sb = new StringBuilder("STATE_");
            sb.append(i == 1 ? "DRAGGING" : "SETTLING");
            sb.append(" should not be set externally.");
            throw new IllegalArgumentException(sb.toString());
        }
        if (!this.I && i == 5) {
            Log.w("BottomSheetBehavior", "Cannot set state: " + i);
            return;
        }
        int i2 = (i == 6 && this.b && z(i) <= this.D) ? 3 : i;
        WeakReference weakReference = this.U;
        if (weakReference == null || weakReference.get() == null) {
            D(i);
            return;
        }
        View view = (View) this.U.get();
        RunnableC0390m3 runnableC0390m3 = new RunnableC0390m3(this, view, i2);
        ViewParent parent = view.getParent();
        if (parent != null && parent.isLayoutRequested()) {
            WeakHashMap weakHashMap = Hs.a;
            if (view.isAttachedToWindow()) {
                view.post(runnableC0390m3);
                return;
            }
        }
        runnableC0390m3.run();
    }

    public final void D(int i) {
        if (this.L == i) {
            return;
        }
        this.L = i;
        if (i != 4 && i != 3 && i != 6) {
            boolean z = this.I;
        }
        WeakReference weakReference = this.U;
        if (weakReference == null || ((View) weakReference.get()) == null) {
            return;
        }
        if (i == 3) {
            I(true);
        } else if (i == 6 || i == 5 || i == 4) {
            I(false);
        }
        H(i, true);
        ArrayList arrayList = this.W;
        if (arrayList.size() <= 0) {
            G();
        } else {
            arrayList.get(0).getClass();
            throw new ClassCastException();
        }
    }

    public final boolean E(View view, float f) {
        if (this.J) {
            return true;
        }
        if (view.getTop() < this.G) {
            return false;
        }
        return Math.abs(((f * this.Q) + ((float) view.getTop())) - ((float) this.G)) / ((float) u()) > 0.5f;
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0030, code lost:
    
        if (r3 != false) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0032, code lost:
    
        D(2);
        H(r4, true);
        r2.A.a(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x003f, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x0012, code lost:
    
        if (r1.o(r3.getLeft(), r0) != false) goto L16;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void F(View view, int i, boolean z) {
        int iZ = z(i);
        Js js = this.M;
        if (js != null) {
            if (!z) {
                int left = view.getLeft();
                js.r = view;
                js.c = -1;
                boolean zH = js.h(left, iZ, 0, 0);
                if (!zH && js.a == 0 && js.r != null) {
                    js.r = null;
                }
            }
        }
        D(i);
    }

    public final void G() {
        View view;
        int iA;
        WeakReference weakReference = this.U;
        if (weakReference == null || (view = (View) weakReference.get()) == null) {
            return;
        }
        Hs.j(view, 524288);
        Hs.h(view, 0);
        Hs.j(view, 262144);
        Hs.h(view, 0);
        Hs.j(view, 1048576);
        Hs.h(view, 0);
        SparseIntArray sparseIntArray = this.c0;
        int i = sparseIntArray.get(0, -1);
        if (i != -1) {
            Hs.j(view, i);
            Hs.h(view, 0);
            sparseIntArray.delete(0);
        }
        if (!this.b && this.L != 6) {
            String string = view.getResources().getString(R.string._0_resource_name_obfuscated_res_0x7f110037);
            I1 i1 = new I1(this, 6);
            ArrayList arrayListF = Hs.f(view);
            int i2 = 0;
            while (true) {
                if (i2 >= arrayListF.size()) {
                    int i3 = -1;
                    for (int i4 = 0; i4 < 32 && i3 == -1; i4++) {
                        int i5 = Hs.d[i4];
                        boolean z = true;
                        for (int i6 = 0; i6 < arrayListF.size(); i6++) {
                            z &= ((C0018a0) arrayListF.get(i6)).a() != i5;
                        }
                        if (z) {
                            i3 = i5;
                        }
                    }
                    iA = i3;
                    break;
                }
                if (TextUtils.equals(string, ((AccessibilityNodeInfo.AccessibilityAction) ((C0018a0) arrayListF.get(i2)).a).getLabel())) {
                    iA = ((C0018a0) arrayListF.get(i2)).a();
                    break;
                }
                i2++;
            }
            if (iA != -1) {
                C0018a0 c0018a0 = new C0018a0(null, iA, string, i1, null);
                View.AccessibilityDelegate accessibilityDelegateD = Hs.d(view);
                S s = accessibilityDelegateD == null ? null : accessibilityDelegateD instanceof Q ? ((Q) accessibilityDelegateD).a : new S(accessibilityDelegateD);
                if (s == null) {
                    s = new S();
                }
                Hs.m(view, s);
                Hs.j(view, c0018a0.a());
                Hs.f(view).add(c0018a0);
                Hs.h(view, 0);
            }
            sparseIntArray.put(0, iA);
        }
        if (this.I && this.L != 5) {
            Hs.k(view, C0018a0.j, new I1(this, 5));
        }
        int i7 = this.L;
        if (i7 == 3) {
            Hs.k(view, C0018a0.i, new I1(this, this.b ? 4 : 6));
            return;
        }
        if (i7 == 4) {
            Hs.k(view, C0018a0.h, new I1(this, this.b ? 3 : 6));
        } else {
            if (i7 != 6) {
                return;
            }
            Hs.k(view, C0018a0.i, new I1(this, 4));
            Hs.k(view, C0018a0.h, new I1(this, 3));
        }
    }

    public final void H(int i, boolean z) {
        Xh xh = this.i;
        ValueAnimator valueAnimator = this.B;
        if (i == 2) {
            return;
        }
        boolean z2 = this.L == 3 && (this.x || A());
        if (this.z == z2 || xh == null) {
            return;
        }
        this.z = z2;
        if (z && valueAnimator != null) {
            if (valueAnimator.isRunning()) {
                valueAnimator.reverse();
                return;
            } else {
                valueAnimator.setFloatValues(xh.a.i, z2 ? t() : 1.0f);
                valueAnimator.start();
                return;
            }
        }
        if (valueAnimator != null && valueAnimator.isRunning()) {
            valueAnimator.cancel();
        }
        float fT = this.z ? t() : 1.0f;
        Wh wh = xh.a;
        if (wh.i != fT) {
            wh.i = fT;
            xh.e = true;
            xh.invalidateSelf();
        }
    }

    public final void I(boolean z) {
        WeakReference weakReference = this.U;
        if (weakReference == null) {
            return;
        }
        ViewParent parent = ((View) weakReference.get()).getParent();
        if (parent instanceof CoordinatorLayout) {
            CoordinatorLayout coordinatorLayout = (CoordinatorLayout) parent;
            int childCount = coordinatorLayout.getChildCount();
            if (z) {
                if (this.b0 != null) {
                    return;
                } else {
                    this.b0 = new HashMap(childCount);
                }
            }
            for (int i = 0; i < childCount; i++) {
                View childAt = coordinatorLayout.getChildAt(i);
                if (childAt != this.U.get() && z) {
                    this.b0.put(childAt, Integer.valueOf(childAt.getImportantForAccessibility()));
                }
            }
            if (z) {
                return;
            }
            this.b0 = null;
        }
    }

    public final void J() {
        View view;
        if (this.U != null) {
            s();
            if (this.L != 4 || (view = (View) this.U.get()) == null) {
                return;
            }
            view.requestLayout();
        }
    }

    @Override // rikka.shizuku.K7
    public final void c(N7 n7) {
        this.U = null;
        this.M = null;
    }

    @Override // rikka.shizuku.K7
    public final void f() {
        this.U = null;
        this.M = null;
    }

    @Override // rikka.shizuku.K7
    public final boolean g(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
        int i;
        Js js;
        if (!view.isShown() || !this.K) {
            this.N = true;
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.Y = -1;
            this.Z = -1;
            VelocityTracker velocityTracker = this.X;
            if (velocityTracker != null) {
                velocityTracker.recycle();
                this.X = null;
            }
        }
        if (this.X == null) {
            this.X = VelocityTracker.obtain();
        }
        this.X.addMovement(motionEvent);
        if (actionMasked == 0) {
            int x = (int) motionEvent.getX();
            this.Z = (int) motionEvent.getY();
            if (this.L != 2) {
                WeakReference weakReference = this.V;
                View view2 = weakReference != null ? (View) weakReference.get() : null;
                if (view2 != null && coordinatorLayout.o(view2, x, this.Z)) {
                    this.Y = motionEvent.getPointerId(motionEvent.getActionIndex());
                    this.a0 = true;
                }
            }
            this.N = this.Y == -1 && !coordinatorLayout.o(view, x, this.Z);
        } else if (actionMasked == 1 || actionMasked == 3) {
            this.a0 = false;
            this.Y = -1;
            if (this.N) {
                this.N = false;
                return false;
            }
        }
        if (this.N || (js = this.M) == null || !js.p(motionEvent)) {
            WeakReference weakReference2 = this.V;
            View view3 = weakReference2 != null ? (View) weakReference2.get() : null;
            if (actionMasked != 2 || view3 == null || this.N || this.L == 1 || coordinatorLayout.o(view3, (int) motionEvent.getX(), (int) motionEvent.getY()) || this.M == null || (i = this.Z) == -1 || Math.abs(i - motionEvent.getY()) <= this.M.b) {
                return false;
            }
        }
        return true;
    }

    @Override // rikka.shizuku.K7
    public final boolean h(CoordinatorLayout coordinatorLayout, View view, int i) {
        int i2 = this.l;
        Xh xh = this.i;
        boolean z = false;
        WeakHashMap weakHashMap = Hs.a;
        if (coordinatorLayout.getFitsSystemWindows() && !view.getFitsSystemWindows()) {
            view.setFitsSystemWindows(true);
        }
        if (this.U == null) {
            this.g = coordinatorLayout.getResources().getDimensionPixelSize(R.dimen._0_resource_name_obfuscated_res_0x7f07007d);
            int i3 = Build.VERSION.SDK_INT;
            boolean z2 = (i3 < 29 || this.n || this.f) ? false : true;
            if (this.o || this.p || this.q || this.s || this.t || this.u || z2) {
                C0330k5 c0330k5 = new C0330k5(this, z2);
                int paddingStart = view.getPaddingStart();
                view.getPaddingTop();
                int paddingEnd = view.getPaddingEnd();
                int paddingBottom = view.getPaddingBottom();
                C0232gt c0232gt = new C0232gt();
                c0232gt.a = paddingStart;
                c0232gt.b = paddingEnd;
                c0232gt.c = paddingBottom;
                AbstractC0817zs.l(view, new R2(c0330k5, c0232gt, 25, z));
                if (view.isAttachedToWindow()) {
                    AbstractC0755xs.c(view);
                } else {
                    view.addOnAttachStateChangeListener(new ViewOnAttachStateChangeListenerC0201ft(0));
                }
            }
            C0796z7 c0796z7 = new C0796z7(view);
            if (i3 >= 30) {
                view.setWindowInsetsAnimationCallback(new Gt(c0796z7));
            } else {
                PathInterpolator pathInterpolator = Ft.e;
                View.OnApplyWindowInsetsListener et = new Et(view, c0796z7);
                view.setTag(R.id._0_resource_name_obfuscated_res_0x7f0901ca, et);
                if (view.getTag(R.id._0_resource_name_obfuscated_res_0x7f0901be) == null && view.getTag(R.id._0_resource_name_obfuscated_res_0x7f0901bf) == null) {
                    view.setOnApplyWindowInsetsListener(et);
                }
            }
            this.U = new WeakReference(view);
            Context context = view.getContext();
            AbstractC0046as.P(context, R.attr._0_resource_name_obfuscated_res_0x7f040350, new PathInterpolator(0.0f, 0.0f, 0.0f, 1.0f));
            AbstractC0046as.O(context, R.attr._0_resource_name_obfuscated_res_0x7f04033f, 300);
            AbstractC0046as.O(context, R.attr._0_resource_name_obfuscated_res_0x7f040344, 150);
            AbstractC0046as.O(context, R.attr._0_resource_name_obfuscated_res_0x7f040343, 100);
            Resources resources = view.getResources();
            resources.getDimension(R.dimen._0_resource_name_obfuscated_res_0x7f0700c1);
            resources.getDimension(R.dimen._0_resource_name_obfuscated_res_0x7f0700c2);
            if (xh != null) {
                view.setBackground(xh);
                float fE = this.H;
                if (fE == -1.0f) {
                    fE = AbstractC0817zs.e(view);
                }
                xh.k(fE);
            } else {
                ColorStateList colorStateList = this.j;
                if (colorStateList != null) {
                    AbstractC0817zs.i(view, colorStateList);
                }
            }
            G();
            if (view.getImportantForAccessibility() == 0) {
                view.setImportantForAccessibility(1);
            }
        }
        if (this.M == null) {
            this.M = new Js(coordinatorLayout.getContext(), coordinatorLayout, this.d0);
        }
        int top = view.getTop();
        coordinatorLayout.q(view, i);
        this.S = coordinatorLayout.getWidth();
        this.T = coordinatorLayout.getHeight();
        int height = view.getHeight();
        this.R = height;
        int iMin = this.T;
        int i4 = iMin - height;
        int i5 = this.w;
        if (i4 < i5) {
            if (this.r) {
                if (i2 != -1) {
                    iMin = Math.min(iMin, i2);
                }
                this.R = iMin;
            } else {
                int iMin2 = iMin - i5;
                if (i2 != -1) {
                    iMin2 = Math.min(iMin2, i2);
                }
                this.R = iMin2;
            }
        }
        this.D = Math.max(0, this.T - this.R);
        this.E = (int) ((1.0f - this.F) * this.T);
        s();
        int i6 = this.L;
        if (i6 == 3) {
            view.offsetTopAndBottom(y());
        } else if (i6 == 6) {
            view.offsetTopAndBottom(this.E);
        } else if (this.I && i6 == 5) {
            view.offsetTopAndBottom(this.T);
        } else if (i6 == 4) {
            view.offsetTopAndBottom(this.G);
        } else if (i6 == 1 || i6 == 2) {
            view.offsetTopAndBottom(top - view.getTop());
        }
        H(this.L, false);
        this.V = new WeakReference(w(view));
        ArrayList arrayList = this.W;
        if (arrayList.size() <= 0) {
            return true;
        }
        arrayList.get(0).getClass();
        throw new ClassCastException();
    }

    @Override // rikka.shizuku.K7
    public final boolean i(CoordinatorLayout coordinatorLayout, View view, int i, int i2, int i3) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        view.measure(x(i, coordinatorLayout.getPaddingRight() + coordinatorLayout.getPaddingLeft() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + i2, this.k, marginLayoutParams.width), x(i3, coordinatorLayout.getPaddingBottom() + coordinatorLayout.getPaddingTop() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin, this.l, marginLayoutParams.height));
        return true;
    }

    @Override // rikka.shizuku.K7
    public final boolean j(View view) {
        WeakReference weakReference = this.V;
        return (weakReference == null || view != weakReference.get() || this.L == 3) ? false : true;
    }

    @Override // rikka.shizuku.K7
    public final void k(CoordinatorLayout coordinatorLayout, View view, View view2, int i, int i2, int[] iArr, int i3) {
        boolean z = this.K;
        if (i3 == 1) {
            return;
        }
        WeakReference weakReference = this.V;
        if (view2 != (weakReference != null ? (View) weakReference.get() : null)) {
            return;
        }
        int top = view.getTop();
        int i4 = top - i2;
        if (i2 > 0) {
            if (i4 < y()) {
                int iY = top - y();
                iArr[1] = iY;
                WeakHashMap weakHashMap = Hs.a;
                view.offsetTopAndBottom(-iY);
                D(3);
            } else {
                if (!z) {
                    return;
                }
                iArr[1] = i2;
                WeakHashMap weakHashMap2 = Hs.a;
                view.offsetTopAndBottom(-i2);
                D(1);
            }
        } else if (i2 < 0 && !view2.canScrollVertically(-1)) {
            int i5 = this.G;
            if (i4 > i5 && !this.I) {
                int i6 = top - i5;
                iArr[1] = i6;
                WeakHashMap weakHashMap3 = Hs.a;
                view.offsetTopAndBottom(-i6);
                D(4);
            } else {
                if (!z) {
                    return;
                }
                iArr[1] = i2;
                WeakHashMap weakHashMap4 = Hs.a;
                view.offsetTopAndBottom(-i2);
                D(1);
            }
        }
        v(view.getTop());
        this.O = i2;
        this.P = true;
    }

    @Override // rikka.shizuku.K7
    public final void n(View view, Parcelable parcelable) {
        C0392m5 c0392m5 = (C0392m5) parcelable;
        int i = this.a;
        if (i != 0) {
            if (i == -1 || (i & 1) == 1) {
                this.e = c0392m5.d;
            }
            if (i == -1 || (i & 2) == 2) {
                this.b = c0392m5.e;
            }
            if (i == -1 || (i & 4) == 4) {
                this.I = c0392m5.f;
            }
            if (i == -1 || (i & 8) == 8) {
                this.J = c0392m5.g;
            }
        }
        int i2 = c0392m5.c;
        if (i2 == 1 || i2 == 2) {
            this.L = 4;
        } else {
            this.L = i2;
        }
    }

    @Override // rikka.shizuku.K7
    public final Parcelable o(View view) {
        AbsSavedState absSavedState = View.BaseSavedState.EMPTY_STATE;
        return new C0392m5(this);
    }

    @Override // rikka.shizuku.K7
    public final boolean p(CoordinatorLayout coordinatorLayout, View view, View view2, int i, int i2) {
        this.O = 0;
        this.P = false;
        return (i & 2) != 0;
    }

    /* JADX WARN: Code duplicated, block: B:30:0x0055  */
    /* JADX WARN: Code duplicated, block: B:32:0x005a  */
    /* JADX WARN: Code duplicated, block: B:34:0x0062  */
    /* JADX WARN: Code duplicated, block: B:37:0x0074  */
    /* JADX WARN: Code duplicated, block: B:39:0x0078  */
    /* JADX WARN: Code duplicated, block: B:42:0x0083  */
    /* JADX WARN: Code duplicated, block: B:45:0x0093  */
    /* JADX WARN: Code duplicated, block: B:47:0x0097  */
    /* JADX WARN: Code duplicated, block: B:48:0x0099  */
    /* JADX WARN: Code duplicated, block: B:50:0x00ae  */
    @Override // rikka.shizuku.K7
    public final void q(CoordinatorLayout coordinatorLayout, View view, View view2, int i) {
        int top;
        int top2;
        int i2;
        float yVelocity;
        int i3 = 3;
        if (view.getTop() == y()) {
            D(3);
            return;
        }
        WeakReference weakReference = this.V;
        if (weakReference != null && view2 == weakReference.get() && this.P) {
            if (this.O > 0) {
                if (!this.b && view.getTop() > this.E) {
                    i3 = 6;
                }
            } else if (this.I) {
                VelocityTracker velocityTracker = this.X;
                if (velocityTracker == null) {
                    yVelocity = 0.0f;
                } else {
                    velocityTracker.computeCurrentVelocity(1000, this.c);
                    yVelocity = this.X.getYVelocity(this.Y);
                }
                if (E(view, yVelocity)) {
                    i3 = 5;
                } else if (this.O == 0) {
                    top2 = view.getTop();
                    if (this.b) {
                        i2 = this.E;
                        if (top2 < i2) {
                            if (top2 >= Math.abs(top2 - this.G)) {
                            }
                        } else if (Math.abs(top2 - i2) < Math.abs(top2 - this.G)) {
                            i3 = 4;
                        }
                        i3 = 6;
                    } else if (Math.abs(top2 - this.D) >= Math.abs(top2 - this.G)) {
                        i3 = 4;
                    }
                } else {
                    if (!this.b) {
                        top = view.getTop();
                        if (Math.abs(top - this.E) < Math.abs(top - this.G)) {
                            i3 = 6;
                        }
                    }
                    i3 = 4;
                }
            } else if (this.O == 0) {
                top2 = view.getTop();
                if (this.b) {
                    i2 = this.E;
                    if (top2 < i2) {
                        if (top2 >= Math.abs(top2 - this.G)) {
                        }
                    } else if (Math.abs(top2 - i2) < Math.abs(top2 - this.G)) {
                        i3 = 4;
                    }
                    i3 = 6;
                } else if (Math.abs(top2 - this.D) >= Math.abs(top2 - this.G)) {
                    i3 = 4;
                }
            } else {
                if (!this.b) {
                    top = view.getTop();
                    if (Math.abs(top - this.E) < Math.abs(top - this.G)) {
                        i3 = 6;
                    }
                }
                i3 = 4;
            }
            F(view, i3, false);
            this.P = false;
        }
    }

    @Override // rikka.shizuku.K7
    public final boolean r(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
        if (!view.isShown()) {
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        int i = this.L;
        if (i == 1 && actionMasked == 0) {
            return true;
        }
        Js js = this.M;
        if (js != null && (this.K || i == 1)) {
            js.j(motionEvent);
        }
        if (actionMasked == 0) {
            this.Y = -1;
            this.Z = -1;
            VelocityTracker velocityTracker = this.X;
            if (velocityTracker != null) {
                velocityTracker.recycle();
                this.X = null;
            }
        }
        if (this.X == null) {
            this.X = VelocityTracker.obtain();
        }
        this.X.addMovement(motionEvent);
        if (this.M != null && ((this.K || this.L == 1) && actionMasked == 2 && !this.N)) {
            float fAbs = Math.abs(this.Z - motionEvent.getY());
            Js js2 = this.M;
            if (fAbs > js2.b) {
                js2.b(view, motionEvent.getPointerId(motionEvent.getActionIndex()));
            }
        }
        return !this.N;
    }

    public final void s() {
        int iU = u();
        if (this.b) {
            this.G = Math.max(this.T - iU, this.D);
        } else {
            this.G = this.T - iU;
        }
    }

    /* JADX WARN: Code duplicated, block: B:21:0x0044  */
    public final float t() {
        WeakReference weakReference;
        WindowInsets rootWindowInsets;
        float f;
        float f2 = 0.0f;
        if (this.i != null && (weakReference = this.U) != null && weakReference.get() != null && Build.VERSION.SDK_INT >= 31) {
            View view = (View) this.U.get();
            if (A() && (rootWindowInsets = view.getRootWindowInsets()) != null) {
                float fH = this.i.h();
                RoundedCorner roundedCorner = rootWindowInsets.getRoundedCorner(0);
                if (roundedCorner != null) {
                    float radius = roundedCorner.getRadius();
                    if (radius <= 0.0f || fH <= 0.0f) {
                        f = 0.0f;
                    } else {
                        f = radius / fH;
                    }
                } else {
                    f = 0.0f;
                }
                Xh xh = this.i;
                float fA = xh.a.a.f.a(xh.g());
                RoundedCorner roundedCorner2 = rootWindowInsets.getRoundedCorner(1);
                if (roundedCorner2 != null) {
                    float radius2 = roundedCorner2.getRadius();
                    if (radius2 > 0.0f && fA > 0.0f) {
                        f2 = radius2 / fA;
                    }
                }
                return Math.max(f, f2);
            }
        }
        return 0.0f;
    }

    public final int u() {
        int i;
        if (this.f) {
            return Math.min(Math.max(this.g, this.T - ((this.S * 9) / 16)), this.R) + this.v;
        }
        return (this.n || this.o || (i = this.m) <= 0) ? this.e + this.v : Math.max(this.e, i + this.h);
    }

    public final void v(int i) {
        if (((View) this.U.get()) != null) {
            ArrayList arrayList = this.W;
            if (arrayList.isEmpty()) {
                return;
            }
            int i2 = this.G;
            if (i <= i2 && i2 != y()) {
                y();
            }
            if (arrayList.size() <= 0) {
                return;
            }
            arrayList.get(0).getClass();
            throw new ClassCastException();
        }
    }

    public final int y() {
        if (this.b) {
            return this.D;
        }
        return Math.max(this.C, this.r ? 0 : this.w);
    }

    public final int z(int i) {
        if (i == 3) {
            return y();
        }
        if (i == 4) {
            return this.G;
        }
        if (i == 5) {
            return this.T;
        }
        if (i == 6) {
            return this.E;
        }
        throw new IllegalArgumentException(AbstractC0535qo.b(i, "Invalid state to get top offset: "));
    }

    public BottomSheetBehavior(Context context, AttributeSet attributeSet) {
        int i;
        int i2 = 0;
        this.a = 0;
        this.b = true;
        this.k = -1;
        this.l = -1;
        this.A = new C0423n5(this);
        this.F = 0.5f;
        this.H = -1.0f;
        this.K = true;
        this.L = 4;
        this.Q = 0.1f;
        this.W = new ArrayList();
        this.Z = -1;
        this.c0 = new SparseIntArray();
        this.d0 = new C0361l5(this, i2);
        this.h = context.getResources().getDimensionPixelSize(R.dimen._0_resource_name_obfuscated_res_0x7f0702de);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Lk.c);
        if (typedArrayObtainStyledAttributes.hasValue(3)) {
            this.j = X8.w(context, typedArrayObtainStyledAttributes, 3);
        }
        if (typedArrayObtainStyledAttributes.hasValue(21)) {
            this.y = C0317jn.c(context, attributeSet, R.attr._0_resource_name_obfuscated_res_0x7f040086, R.style._0_resource_name_obfuscated_res_0x7f120394).a();
        }
        C0317jn c0317jn = this.y;
        if (c0317jn != null) {
            Xh xh = new Xh(c0317jn);
            this.i = xh;
            xh.j(context);
            ColorStateList colorStateList = this.j;
            if (colorStateList != null) {
                this.i.l(colorStateList);
            } else {
                TypedValue typedValue = new TypedValue();
                context.getTheme().resolveAttribute(android.R.attr.colorBackground, typedValue, true);
                this.i.setTint(typedValue.data);
            }
        }
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(t(), 1.0f);
        this.B = valueAnimatorOfFloat;
        valueAnimatorOfFloat.setDuration(500L);
        this.B.addUpdateListener(new C0299j5(i2, this));
        this.H = typedArrayObtainStyledAttributes.getDimension(2, -1.0f);
        if (typedArrayObtainStyledAttributes.hasValue(0)) {
            this.k = typedArrayObtainStyledAttributes.getDimensionPixelSize(0, -1);
        }
        if (typedArrayObtainStyledAttributes.hasValue(1)) {
            this.l = typedArrayObtainStyledAttributes.getDimensionPixelSize(1, -1);
        }
        TypedValue typedValuePeekValue = typedArrayObtainStyledAttributes.peekValue(9);
        if (typedValuePeekValue != null && (i = typedValuePeekValue.data) == -1) {
            B(i);
        } else {
            B(typedArrayObtainStyledAttributes.getDimensionPixelSize(9, -1));
        }
        boolean z = typedArrayObtainStyledAttributes.getBoolean(8, false);
        if (this.I != z) {
            this.I = z;
            if (!z && this.L == 5) {
                C(4);
            }
            G();
        }
        this.n = typedArrayObtainStyledAttributes.getBoolean(13, false);
        boolean z2 = typedArrayObtainStyledAttributes.getBoolean(6, true);
        if (this.b != z2) {
            this.b = z2;
            if (this.U != null) {
                s();
            }
            D((this.b && this.L == 6) ? 3 : this.L);
            H(this.L, true);
            G();
        }
        this.J = typedArrayObtainStyledAttributes.getBoolean(12, false);
        this.K = typedArrayObtainStyledAttributes.getBoolean(4, true);
        this.a = typedArrayObtainStyledAttributes.getInt(10, 0);
        float f = typedArrayObtainStyledAttributes.getFloat(7, 0.5f);
        if (f > 0.0f && f < 1.0f) {
            this.F = f;
            if (this.U != null) {
                this.E = (int) ((1.0f - f) * this.T);
            }
            TypedValue typedValuePeekValue2 = typedArrayObtainStyledAttributes.peekValue(5);
            if (typedValuePeekValue2 != null && typedValuePeekValue2.type == 16) {
                int i3 = typedValuePeekValue2.data;
                if (i3 >= 0) {
                    this.C = i3;
                    H(this.L, true);
                } else {
                    throw new IllegalArgumentException("offset must be greater than or equal to 0");
                }
            } else {
                int dimensionPixelOffset = typedArrayObtainStyledAttributes.getDimensionPixelOffset(5, 0);
                if (dimensionPixelOffset >= 0) {
                    this.C = dimensionPixelOffset;
                    H(this.L, true);
                } else {
                    throw new IllegalArgumentException("offset must be greater than or equal to 0");
                }
            }
            this.d = typedArrayObtainStyledAttributes.getInt(11, 500);
            this.o = typedArrayObtainStyledAttributes.getBoolean(17, false);
            this.p = typedArrayObtainStyledAttributes.getBoolean(18, false);
            this.q = typedArrayObtainStyledAttributes.getBoolean(19, false);
            this.r = typedArrayObtainStyledAttributes.getBoolean(20, true);
            this.s = typedArrayObtainStyledAttributes.getBoolean(14, false);
            this.t = typedArrayObtainStyledAttributes.getBoolean(15, false);
            this.u = typedArrayObtainStyledAttributes.getBoolean(16, false);
            this.x = typedArrayObtainStyledAttributes.getBoolean(23, true);
            typedArrayObtainStyledAttributes.recycle();
            this.c = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
            return;
        }
        throw new IllegalArgumentException("ratio must be a float value between 0 and 1");
    }

    @Override // rikka.shizuku.K7
    public final void l(CoordinatorLayout coordinatorLayout, View view, int i, int i2, int i3, int[] iArr) {
    }
}
