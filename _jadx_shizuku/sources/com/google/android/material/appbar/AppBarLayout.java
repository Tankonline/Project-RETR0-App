package com.google.android.material.appbar;

import android.animation.AnimatorInflater;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
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
import android.view.ViewOutlineProvider;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.OverScroller;
import android.widget.ScrollView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.search.SearchBar$ScrollingViewBehavior;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.WeakHashMap;
import moe.shizuku.privileged.api.R;
import rikka.shizuku.AbstractC0046as;
import rikka.shizuku.AbstractC0078bt;
import rikka.shizuku.AbstractC0599sr;
import rikka.shizuku.AbstractC0744xh;
import rikka.shizuku.AbstractC0817zs;
import rikka.shizuku.C0104co;
import rikka.shizuku.C0205g2;
import rikka.shizuku.C0236h2;
import rikka.shizuku.C0267i2;
import rikka.shizuku.C0296j2;
import rikka.shizuku.F;
import rikka.shizuku.Fd;
import rikka.shizuku.Hs;
import rikka.shizuku.J7;
import rikka.shizuku.K7;
import rikka.shizuku.Lk;
import rikka.shizuku.N7;
import rikka.shizuku.R2;
import rikka.shizuku.RunnableC0186fe;
import rikka.shizuku.Ui;
import rikka.shizuku.V1;
import rikka.shizuku.Wt;
import rikka.shizuku.X8;
import rikka.shizuku.Xh;
import rikka.shizuku.Ys;
import rikka.shizuku.au;

/* JADX INFO: loaded from: classes.dex */
public class AppBarLayout extends LinearLayout implements J7 {
    public static final /* synthetic */ int x = 0;
    public int a;
    public int b;
    public int c;
    public int d;
    public boolean e;
    public int f;
    public au g;
    public boolean h;
    public boolean i;
    public final boolean j;
    public final int k;
    public WeakReference l;
    public final boolean m;
    public ValueAnimator n;
    public final ValueAnimator.AnimatorUpdateListener o;
    public final ArrayList p;
    public final long q;
    public final TimeInterpolator r;
    public int[] s;
    public final Drawable t;
    public final Integer u;
    public final float v;
    public Behavior w;

    public class Behavior extends BaseBehavior<AppBarLayout> {
        public Behavior() {
        }

        public Behavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppBarLayout(Context context, AttributeSet attributeSet) {
        final Integer numValueOf;
        super(X8.c0(context, attributeSet, R.attr._0_resource_name_obfuscated_res_0x7f04003a, R.style._0_resource_name_obfuscated_res_0x7f120392), attributeSet, R.attr._0_resource_name_obfuscated_res_0x7f04003a);
        int i = 6;
        this.b = -1;
        this.c = -1;
        this.d = -1;
        boolean z = false;
        this.f = 0;
        this.p = new ArrayList();
        Context context2 = getContext();
        super.setOrientation(1);
        int i2 = Build.VERSION.SDK_INT;
        if (getOutlineProvider() == ViewOutlineProvider.BACKGROUND) {
            setOutlineProvider(ViewOutlineProvider.BOUNDS);
        }
        Context context3 = getContext();
        TypedArray typedArrayJ = X8.J(context3, attributeSet, AbstractC0599sr.v, R.attr._0_resource_name_obfuscated_res_0x7f04003a, R.style._0_resource_name_obfuscated_res_0x7f120392, new int[0]);
        try {
            if (typedArrayJ.hasValue(0)) {
                setStateListAnimator(AnimatorInflater.loadStateListAnimator(context3, typedArrayJ.getResourceId(0, 0)));
            }
            typedArrayJ.recycle();
            TypedArray typedArrayJ2 = X8.J(context2, attributeSet, Lk.a, R.attr._0_resource_name_obfuscated_res_0x7f04003a, R.style._0_resource_name_obfuscated_res_0x7f120392, new int[0]);
            Drawable drawable = typedArrayJ2.getDrawable(0);
            WeakHashMap weakHashMap = Hs.a;
            setBackground(drawable);
            final ColorStateList colorStateListW = X8.w(context2, typedArrayJ2, 6);
            this.m = colorStateListW != null;
            final ColorStateList colorStateListU = AbstractC0046as.u(getBackground());
            Integer numValueOf2 = null;
            if (colorStateListU != null) {
                final Xh xh = new Xh();
                xh.l(colorStateListU);
                if (colorStateListW != null) {
                    Context context4 = getContext();
                    TypedValue typedValueM = AbstractC0046as.M(context4, R.attr._0_resource_name_obfuscated_res_0x7f04011f);
                    if (typedValueM != null) {
                        int i3 = typedValueM.resourceId;
                        numValueOf = Integer.valueOf(i3 != 0 ? context4.getColor(i3) : typedValueM.data);
                    } else {
                        numValueOf = null;
                    }
                    this.o = new ValueAnimator.AnimatorUpdateListener() { // from class: rikka.shizuku.e2
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            Integer num;
                            int i4 = AppBarLayout.x;
                            AppBarLayout appBarLayout = this.a;
                            int iH = AbstractC0744xh.H(colorStateListU.getDefaultColor(), colorStateListW.getDefaultColor(), ((Float) valueAnimator.getAnimatedValue()).floatValue());
                            ColorStateList colorStateListValueOf = ColorStateList.valueOf(iH);
                            Xh xh2 = xh;
                            xh2.l(colorStateListValueOf);
                            if (appBarLayout.t != null && (num = appBarLayout.u) != null && num.equals(numValueOf)) {
                                appBarLayout.t.setTint(iH);
                            }
                            ArrayList arrayList = appBarLayout.p;
                            if (arrayList.isEmpty()) {
                                return;
                            }
                            int size = arrayList.size();
                            int i5 = 0;
                            while (i5 < size) {
                                Object obj = arrayList.get(i5);
                                i5++;
                                if (obj != null) {
                                    throw new ClassCastException();
                                }
                                if (xh2.a.c != null) {
                                    throw null;
                                }
                            }
                        }
                    };
                    setBackground(xh);
                } else {
                    xh.j(context2);
                    this.o = new ValueAnimator.AnimatorUpdateListener() { // from class: rikka.shizuku.f2
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            int i4 = AppBarLayout.x;
                            AppBarLayout appBarLayout = this.a;
                            float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                            xh.k(fFloatValue);
                            Drawable drawable2 = appBarLayout.t;
                            if (drawable2 instanceof Xh) {
                                ((Xh) drawable2).k(fFloatValue);
                            }
                            Iterator it = appBarLayout.p.iterator();
                            if (it.hasNext()) {
                                it.next().getClass();
                                throw new ClassCastException();
                            }
                        }
                    };
                    setBackground(xh);
                }
            }
            this.q = AbstractC0046as.O(context2, R.attr._0_resource_name_obfuscated_res_0x7f04033f, getResources().getInteger(R.integer._0_resource_name_obfuscated_res_0x7f0a0002));
            this.r = AbstractC0046as.P(context2, R.attr._0_resource_name_obfuscated_res_0x7f040351, V1.a);
            if (typedArrayJ2.hasValue(4)) {
                h(typedArrayJ2.getBoolean(4, false), false, false);
            }
            if (typedArrayJ2.hasValue(3)) {
                AbstractC0599sr.F(this, typedArrayJ2.getDimensionPixelSize(3, 0));
            }
            if (i2 >= 26) {
                if (typedArrayJ2.hasValue(2)) {
                    setKeyboardNavigationCluster(typedArrayJ2.getBoolean(2, false));
                }
                if (typedArrayJ2.hasValue(1)) {
                    setTouchscreenBlocksFocus(typedArrayJ2.getBoolean(1, false));
                }
            }
            this.v = getResources().getDimension(R.dimen._0_resource_name_obfuscated_res_0x7f07006e);
            this.j = typedArrayJ2.getBoolean(5, false);
            this.k = typedArrayJ2.getResourceId(7, -1);
            Drawable drawable2 = typedArrayJ2.getDrawable(8);
            Drawable drawable3 = this.t;
            if (drawable3 != drawable2) {
                if (drawable3 != null) {
                    drawable3.setCallback(null);
                }
                Drawable drawableMutate = drawable2 != null ? drawable2.mutate() : null;
                this.t = drawableMutate;
                if (drawableMutate instanceof Xh) {
                    numValueOf2 = Integer.valueOf(((Xh) drawableMutate).u);
                } else {
                    ColorStateList colorStateListU2 = AbstractC0046as.u(drawableMutate);
                    if (colorStateListU2 != null) {
                        numValueOf2 = Integer.valueOf(colorStateListU2.getDefaultColor());
                    }
                }
                this.u = numValueOf2;
                Drawable drawable4 = this.t;
                if (drawable4 != null) {
                    if (drawable4.isStateful()) {
                        this.t.setState(getDrawableState());
                    }
                    this.t.setLayoutDirection(getLayoutDirection());
                    this.t.setVisible(getVisibility() == 0, false);
                    this.t.setCallback(this);
                }
                if (this.t != null && e() > 0) {
                    z = true;
                }
                setWillNotDraw(!z);
                postInvalidateOnAnimation();
            }
            typedArrayJ2.recycle();
            AbstractC0817zs.l(this, new Ys(i, this));
        } catch (Throwable th) {
            typedArrayJ.recycle();
            throw th;
        }
    }

    public static C0296j2 b(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof LinearLayout.LayoutParams) {
            C0296j2 c0296j2 = new C0296j2((LinearLayout.LayoutParams) layoutParams);
            c0296j2.a = 1;
            return c0296j2;
        }
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            C0296j2 c0296j3 = new C0296j2((ViewGroup.MarginLayoutParams) layoutParams);
            c0296j3.a = 1;
            return c0296j3;
        }
        C0296j2 c0296j4 = new C0296j2(layoutParams);
        c0296j4.a = 1;
        return c0296j4;
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public final C0296j2 generateLayoutParams(AttributeSet attributeSet) {
        Context context = getContext();
        C0296j2 c0296j2 = new C0296j2(context, attributeSet);
        c0296j2.a = 1;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Lk.b);
        c0296j2.a = typedArrayObtainStyledAttributes.getInt(1, 0);
        c0296j2.b = typedArrayObtainStyledAttributes.getInt(0, 0) != 1 ? null : new R2(2);
        if (typedArrayObtainStyledAttributes.hasValue(2)) {
            c0296j2.c = AnimationUtils.loadInterpolator(context, typedArrayObtainStyledAttributes.getResourceId(2, 0));
        }
        typedArrayObtainStyledAttributes.recycle();
        return c0296j2;
    }

    /* JADX WARN: Code duplicated, block: B:21:0x0050  */
    /* JADX WARN: Code duplicated, block: B:23:0x0058  */
    public final int c() {
        int iMin;
        int minimumHeight;
        int i = this.c;
        if (i != -1) {
            return i;
        }
        int i2 = 0;
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = getChildAt(childCount);
            if (childAt.getVisibility() != 8) {
                C0296j2 c0296j2 = (C0296j2) childAt.getLayoutParams();
                int measuredHeight = childAt.getMeasuredHeight();
                int i3 = c0296j2.a;
                if ((i3 & 5) != 5) {
                    if (i2 > 0) {
                        break;
                    }
                } else {
                    int i4 = ((LinearLayout.LayoutParams) c0296j2).topMargin + ((LinearLayout.LayoutParams) c0296j2).bottomMargin;
                    if ((i3 & 8) != 0) {
                        WeakHashMap weakHashMap = Hs.a;
                        minimumHeight = childAt.getMinimumHeight();
                    } else {
                        if ((i3 & 2) != 0) {
                            WeakHashMap weakHashMap2 = Hs.a;
                            minimumHeight = measuredHeight - childAt.getMinimumHeight();
                        } else {
                            iMin = i4 + measuredHeight;
                        }
                        if (childCount == 0) {
                            WeakHashMap weakHashMap3 = Hs.a;
                            if (childAt.getFitsSystemWindows()) {
                                iMin = Math.min(iMin, measuredHeight - e());
                            }
                        }
                        i2 += iMin;
                    }
                    iMin = minimumHeight + i4;
                    if (childCount == 0) {
                        WeakHashMap weakHashMap4 = Hs.a;
                        if (childAt.getFitsSystemWindows()) {
                            iMin = Math.min(iMin, measuredHeight - e());
                        }
                    }
                    i2 += iMin;
                }
            }
        }
        int iMax = Math.max(0, i2);
        this.c = iMax;
        return iMax;
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public final boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof C0296j2;
    }

    public final int d() {
        int i = this.d;
        if (i != -1) {
            return i;
        }
        int childCount = getChildCount();
        int minimumHeight = 0;
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if (childAt.getVisibility() != 8) {
                C0296j2 c0296j2 = (C0296j2) childAt.getLayoutParams();
                int measuredHeight = ((LinearLayout.LayoutParams) c0296j2).topMargin + ((LinearLayout.LayoutParams) c0296j2).bottomMargin + childAt.getMeasuredHeight();
                int i3 = c0296j2.a;
                if ((i3 & 1) == 0) {
                    break;
                }
                minimumHeight += measuredHeight;
                if ((i3 & 2) != 0) {
                    WeakHashMap weakHashMap = Hs.a;
                    minimumHeight -= childAt.getMinimumHeight();
                    break;
                }
            }
        }
        int iMax = Math.max(0, minimumHeight);
        this.d = iMax;
        return iMax;
    }

    @Override // android.view.View
    public final void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.t == null || e() <= 0) {
            return;
        }
        int iSave = canvas.save();
        canvas.translate(0.0f, -this.a);
        this.t.draw(canvas);
        canvas.restoreToCount(iSave);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void drawableStateChanged() {
        super.drawableStateChanged();
        int[] drawableState = getDrawableState();
        Drawable drawable = this.t;
        if (drawable != null && drawable.isStateful() && drawable.setState(drawableState)) {
            invalidateDrawable(drawable);
        }
    }

    public final int e() {
        au auVar = this.g;
        if (auVar != null) {
            return auVar.b();
        }
        return 0;
    }

    public final int f() {
        int i = this.b;
        if (i != -1) {
            return i;
        }
        int childCount = getChildCount();
        int minimumHeight = 0;
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if (childAt.getVisibility() != 8) {
                C0296j2 c0296j2 = (C0296j2) childAt.getLayoutParams();
                int measuredHeight = childAt.getMeasuredHeight();
                int i3 = c0296j2.a;
                if ((i3 & 1) == 0) {
                    break;
                }
                int iE = measuredHeight + ((LinearLayout.LayoutParams) c0296j2).topMargin + ((LinearLayout.LayoutParams) c0296j2).bottomMargin + minimumHeight;
                if (i2 == 0) {
                    WeakHashMap weakHashMap = Hs.a;
                    if (childAt.getFitsSystemWindows()) {
                        iE -= e();
                    }
                }
                minimumHeight = iE;
                if ((i3 & 2) != 0) {
                    WeakHashMap weakHashMap2 = Hs.a;
                    minimumHeight -= childAt.getMinimumHeight();
                    break;
                }
            }
        }
        int iMax = Math.max(0, minimumHeight);
        this.b = iMax;
        return iMax;
    }

    public final void g() {
        Behavior behavior = this.w;
        C0267i2 c0267i2Z = (behavior == null || this.b == -1 || this.f != 0) ? null : behavior.z(F.b, this);
        this.b = -1;
        this.c = -1;
        this.d = -1;
        if (c0267i2Z != null) {
            Behavior behavior2 = this.w;
            if (behavior2.m != null) {
                return;
            }
            behavior2.m = c0267i2Z;
        }
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public final ViewGroup.LayoutParams generateDefaultLayoutParams() {
        C0296j2 c0296j2 = new C0296j2(-1, -2);
        c0296j2.a = 1;
        return c0296j2;
    }

    public final void h(boolean z, boolean z2, boolean z3) {
        this.f = (z ? 1 : 2) | (z2 ? 4 : 0) | (z3 ? 8 : 0);
        requestLayout();
    }

    public final boolean i(boolean z) {
        if (this.i == z) {
            return false;
        }
        this.i = z;
        refreshDrawableState();
        if (!(getBackground() instanceof Xh)) {
            return true;
        }
        if (this.m) {
            k(z ? 0.0f : 1.0f, z ? 1.0f : 0.0f);
            return true;
        }
        if (!this.j) {
            return true;
        }
        float f = this.v;
        k(z ? 0.0f : f, z ? f : 0.0f);
        return true;
    }

    public final boolean j(View view) {
        int i;
        if (this.l == null && (i = this.k) != -1) {
            View viewFindViewById = view != null ? view.findViewById(i) : null;
            if (viewFindViewById == null && (getParent() instanceof ViewGroup)) {
                viewFindViewById = ((ViewGroup) getParent()).findViewById(i);
            }
            if (viewFindViewById != null) {
                this.l = new WeakReference(viewFindViewById);
            }
        }
        WeakReference weakReference = this.l;
        View view2 = weakReference != null ? (View) weakReference.get() : null;
        if (view2 != null) {
            view = view2;
        }
        if (view != null) {
            return view.canScrollVertically(-1) || view.getScrollY() > 0;
        }
        return false;
    }

    public final void k(float f, float f2) {
        ValueAnimator valueAnimator = this.n;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(f, f2);
        this.n = valueAnimatorOfFloat;
        valueAnimatorOfFloat.setDuration(this.q);
        this.n.setInterpolator(this.r);
        ValueAnimator.AnimatorUpdateListener animatorUpdateListener = this.o;
        if (animatorUpdateListener != null) {
            this.n.addUpdateListener(animatorUpdateListener);
        }
        this.n.start();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        Drawable background = getBackground();
        if (background instanceof Xh) {
            AbstractC0599sr.H(this, (Xh) background);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final int[] onCreateDrawableState(int i) {
        if (this.s == null) {
            this.s = new int[4];
        }
        int[] iArr = this.s;
        int[] iArrOnCreateDrawableState = super.onCreateDrawableState(i + iArr.length);
        boolean z = this.h;
        iArr[0] = z ? R.attr._0_resource_name_obfuscated_res_0x7f040463 : -2130969699;
        iArr[1] = (z && this.i) ? R.attr._0_resource_name_obfuscated_res_0x7f040464 : -2130969700;
        iArr[2] = z ? R.attr._0_resource_name_obfuscated_res_0x7f04045f : -2130969695;
        iArr[3] = (z && this.i) ? R.attr._0_resource_name_obfuscated_res_0x7f04045e : -2130969694;
        return View.mergeDrawableStates(iArrOnCreateDrawableState, iArr);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        WeakReference weakReference = this.l;
        if (weakReference != null) {
            weakReference.clear();
        }
        this.l = null;
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        boolean z2 = true;
        super.onLayout(z, i, i2, i3, i4);
        WeakHashMap weakHashMap = Hs.a;
        if (getFitsSystemWindows() && getChildCount() > 0) {
            View childAt = getChildAt(0);
            if (childAt.getVisibility() != 8 && !childAt.getFitsSystemWindows()) {
                int iE = e();
                for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
                    getChildAt(childCount).offsetTopAndBottom(iE);
                }
            }
        }
        g();
        this.e = false;
        int childCount2 = getChildCount();
        for (int i5 = 0; i5 < childCount2; i5++) {
            if (((C0296j2) getChildAt(i5).getLayoutParams()).c != null) {
                this.e = true;
                break;
            }
        }
        Drawable drawable = this.t;
        if (drawable != null) {
            drawable.setBounds(0, 0, getWidth(), e());
        }
        if (!this.j) {
            int childCount3 = getChildCount();
            int i6 = 0;
            while (true) {
                if (i6 >= childCount3) {
                    z2 = false;
                    break;
                }
                int i7 = ((C0296j2) getChildAt(i6).getLayoutParams()).a;
                if ((i7 & 1) == 1 && (i7 & 10) != 0) {
                    break;
                } else {
                    i6++;
                }
            }
        }
        if (this.h != z2) {
            this.h = z2;
            refreshDrawableState();
        }
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int mode = View.MeasureSpec.getMode(i2);
        if (mode != 1073741824) {
            WeakHashMap weakHashMap = Hs.a;
            if (getFitsSystemWindows() && getChildCount() > 0) {
                View childAt = getChildAt(0);
                if (childAt.getVisibility() != 8 && !childAt.getFitsSystemWindows()) {
                    int measuredHeight = getMeasuredHeight();
                    if (mode == Integer.MIN_VALUE) {
                        measuredHeight = AbstractC0744xh.i(e() + getMeasuredHeight(), 0, View.MeasureSpec.getSize(i2));
                    } else if (mode == 0) {
                        measuredHeight += e();
                    }
                    setMeasuredDimension(getMeasuredWidth(), measuredHeight);
                }
            }
        }
        g();
    }

    @Override // android.view.View
    public final void setElevation(float f) {
        super.setElevation(f);
        Drawable background = getBackground();
        if (background instanceof Xh) {
            ((Xh) background).k(f);
        }
    }

    @Override // android.widget.LinearLayout
    public final void setOrientation(int i) {
        if (i != 1) {
            throw new IllegalArgumentException("AppBarLayout is always vertical and does not support horizontal orientation");
        }
        super.setOrientation(i);
    }

    @Override // android.view.View
    public final void setVisibility(int i) {
        super.setVisibility(i);
        boolean z = i == 0;
        Drawable drawable = this.t;
        if (drawable != null) {
            drawable.setVisible(z, false);
        }
    }

    @Override // android.view.View
    public final boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.t;
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public final /* bridge */ /* synthetic */ ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return b(layoutParams);
    }

    public class BaseBehavior<T extends AppBarLayout> extends AbstractC0078bt {
        public RunnableC0186fe c;
        public OverScroller d;
        public boolean e;
        public int f;
        public int g;
        public int h;
        public VelocityTracker i;
        public int j;
        public int k;
        public ValueAnimator l;
        public C0267i2 m;
        public WeakReference n;

        public BaseBehavior() {
            this.f = -1;
            this.h = -1;
        }

        /* JADX WARN: Code duplicated, block: B:26:0x005c  */
        public static void D(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int i, int i2, boolean z) {
            View childAt;
            boolean zJ;
            int iAbs = Math.abs(i);
            int childCount = appBarLayout.getChildCount();
            int i3 = 0;
            while (true) {
                if (i3 >= childCount) {
                    childAt = null;
                    break;
                }
                childAt = appBarLayout.getChildAt(i3);
                if (iAbs >= childAt.getTop() && iAbs <= childAt.getBottom()) {
                    break;
                } else {
                    i3++;
                }
            }
            if (childAt != null) {
                int i4 = ((C0296j2) childAt.getLayoutParams()).a;
                if ((i4 & 1) != 0) {
                    WeakHashMap weakHashMap = Hs.a;
                    int minimumHeight = childAt.getMinimumHeight();
                    if (i2 <= 0 || (i4 & 12) == 0 ? (i4 & 2) == 0 || (-i) < (childAt.getBottom() - minimumHeight) - appBarLayout.e() : (-i) < (childAt.getBottom() - minimumHeight) - appBarLayout.e()) {
                        zJ = false;
                    } else {
                        zJ = true;
                    }
                } else {
                    zJ = false;
                }
            } else {
                zJ = false;
            }
            if (appBarLayout.j) {
                zJ = appBarLayout.j(w(coordinatorLayout));
            }
            boolean zI = appBarLayout.i(zJ);
            if (!z) {
                if (zI) {
                    List list = (List) ((C0104co) coordinatorLayout.b.b).get(appBarLayout);
                    ArrayList arrayList = coordinatorLayout.d;
                    arrayList.clear();
                    if (list != null) {
                        arrayList.addAll(list);
                    }
                    int size = arrayList.size();
                    for (int i5 = 0; i5 < size; i5++) {
                        K7 k7 = ((N7) ((View) arrayList.get(i5)).getLayoutParams()).a;
                        if (k7 instanceof ScrollingViewBehavior) {
                            if (((ScrollingViewBehavior) k7).f == 0) {
                                return;
                            }
                        }
                    }
                    return;
                }
                return;
            }
            if (appBarLayout.getBackground() != null) {
                appBarLayout.getBackground().jumpToCurrentState();
            }
            if (appBarLayout.getForeground() != null) {
                appBarLayout.getForeground().jumpToCurrentState();
            }
            if (appBarLayout.getStateListAnimator() != null) {
                appBarLayout.getStateListAnimator().jumpToCurrentState();
            }
        }

        public static View u(BaseBehavior baseBehavior, CoordinatorLayout coordinatorLayout) {
            baseBehavior.getClass();
            int childCount = coordinatorLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = coordinatorLayout.getChildAt(i);
                if (((N7) childAt.getLayoutParams()).a instanceof ScrollingViewBehavior) {
                    return childAt;
                }
            }
            return null;
        }

        public static View w(CoordinatorLayout coordinatorLayout) {
            int childCount = coordinatorLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = coordinatorLayout.getChildAt(i);
                if ((childAt instanceof Ui) || (childAt instanceof AbsListView) || (childAt instanceof ScrollView)) {
                    return childAt;
                }
            }
            return null;
        }

        /* JADX WARN: Code duplicated, block: B:100:0x0185 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:40:0x00ae  */
        /* JADX WARN: Code duplicated, block: B:43:0x00b5  */
        /* JADX WARN: Code duplicated, block: B:73:0x0172  */
        /* JADX WARN: Code duplicated, block: B:75:0x0182  */
        /* JADX WARN: Code duplicated, block: B:79:0x0193  */
        /* JADX WARN: Code duplicated, block: B:81:0x019a  */
        public final int A(CoordinatorLayout coordinatorLayout, View view, int i, int i2, int i3) {
            int top;
            boolean z;
            List list;
            int i4;
            View view2;
            K7 k7;
            int i5;
            R2 r2;
            int iE;
            AppBarLayout appBarLayout = (AppBarLayout) view;
            int iX = x();
            int i6 = 0;
            if (i2 == 0 || iX < i2 || iX > i3) {
                this.j = 0;
            } else {
                int i7 = AbstractC0744xh.i(i, i2, i3);
                if (iX != i7) {
                    if (!appBarLayout.e) {
                        top = i7;
                        break;
                    }
                    int iAbs = Math.abs(i7);
                    int childCount = appBarLayout.getChildCount();
                    int i8 = 0;
                    while (true) {
                        if (i8 < childCount) {
                            View childAt = appBarLayout.getChildAt(i8);
                            C0296j2 c0296j2 = (C0296j2) childAt.getLayoutParams();
                            Interpolator interpolator = c0296j2.c;
                            if (iAbs < childAt.getTop() || iAbs > childAt.getBottom()) {
                                i8++;
                            } else if (interpolator != null) {
                                int i9 = c0296j2.a;
                                if ((i9 & 1) != 0) {
                                    iE = childAt.getHeight() + ((LinearLayout.LayoutParams) c0296j2).topMargin + ((LinearLayout.LayoutParams) c0296j2).bottomMargin;
                                    if ((i9 & 2) != 0) {
                                        WeakHashMap weakHashMap = Hs.a;
                                        iE -= childAt.getMinimumHeight();
                                    }
                                } else {
                                    iE = 0;
                                }
                                WeakHashMap weakHashMap2 = Hs.a;
                                if (childAt.getFitsSystemWindows()) {
                                    iE -= appBarLayout.e();
                                }
                                if (iE > 0) {
                                    float f = iE;
                                    top = (childAt.getTop() + Math.round(interpolator.getInterpolation((iAbs - childAt.getTop()) / f) * f)) * Integer.signum(i7);
                                    break;
                                }
                            }
                        }
                        top = i7;
                        break;
                    }
                    Fd fd = this.a;
                    if (fd != null) {
                        if (fd.c != top) {
                            fd.c = top;
                            fd.b();
                            z = true;
                        }
                        int i10 = iX - i7;
                        this.j = i7 - top;
                        if (z) {
                            for (i5 = 0; i5 < appBarLayout.getChildCount(); i5++) {
                                C0296j2 c0296j3 = (C0296j2) appBarLayout.getChildAt(i5).getLayoutParams();
                                r2 = c0296j3.b;
                                if (r2 == null && (c0296j3.a & 1) != 0) {
                                    View childAt2 = appBarLayout.getChildAt(i5);
                                    float fS = s();
                                    Rect rect = (Rect) r2.b;
                                    childAt2.getDrawingRect(rect);
                                    appBarLayout.offsetDescendantRectToMyCoords(childAt2, rect);
                                    rect.offset(0, -appBarLayout.e());
                                    float fAbs = rect.top - Math.abs(fS);
                                    if (fAbs <= 0.0f) {
                                        float fAbs2 = Math.abs(fAbs / rect.height());
                                        float f2 = 1.0f - (fAbs2 >= 0.0f ? fAbs2 > 1.0f ? 1.0f : fAbs2 : 0.0f);
                                        float fHeight = (-fAbs) - ((rect.height() * 0.3f) * (1.0f - (f2 * f2)));
                                        childAt2.setTranslationY(fHeight);
                                        Rect rect2 = (Rect) r2.c;
                                        childAt2.getDrawingRect(rect2);
                                        rect2.offset(0, (int) (-fHeight));
                                        if (fHeight >= rect2.height()) {
                                            childAt2.setVisibility(4);
                                        } else {
                                            childAt2.setVisibility(0);
                                        }
                                        WeakHashMap weakHashMap3 = Hs.a;
                                        childAt2.setClipBounds(rect2);
                                    } else {
                                        WeakHashMap weakHashMap4 = Hs.a;
                                        childAt2.setClipBounds(null);
                                        childAt2.setTranslationY(0.0f);
                                        childAt2.setVisibility(0);
                                    }
                                }
                            }
                        }
                        if (!z && appBarLayout.e && (list = (List) ((C0104co) coordinatorLayout.b.b).get(appBarLayout)) != null && !list.isEmpty()) {
                            for (i4 = 0; i4 < list.size(); i4++) {
                                view2 = (View) list.get(i4);
                                k7 = ((N7) view2.getLayoutParams()).a;
                                if (k7 != null) {
                                    k7.d(coordinatorLayout, view2, appBarLayout);
                                }
                            }
                        }
                        appBarLayout.a = s();
                        if (!appBarLayout.willNotDraw()) {
                            WeakHashMap weakHashMap5 = Hs.a;
                            appBarLayout.postInvalidateOnAnimation();
                        }
                        D(coordinatorLayout, appBarLayout, i7, i7 < iX ? -1 : 1, false);
                        i6 = i10;
                    } else {
                        this.b = top;
                    }
                    z = false;
                    int i11 = iX - i7;
                    this.j = i7 - top;
                    if (z) {
                        while (i5 < appBarLayout.getChildCount()) {
                            C0296j2 c0296j4 = (C0296j2) appBarLayout.getChildAt(i5).getLayoutParams();
                            r2 = c0296j4.b;
                            if (r2 == null) {
                            }
                        }
                    }
                    if (!z) {
                        while (i4 < list.size()) {
                            view2 = (View) list.get(i4);
                            k7 = ((N7) view2.getLayoutParams()).a;
                            if (k7 != null) {
                                k7.d(coordinatorLayout, view2, appBarLayout);
                            }
                        }
                    }
                    appBarLayout.a = s();
                    if (!appBarLayout.willNotDraw()) {
                        WeakHashMap weakHashMap6 = Hs.a;
                        appBarLayout.postInvalidateOnAnimation();
                    }
                    D(coordinatorLayout, appBarLayout, i7, i7 < iX ? -1 : 1, false);
                    i6 = i11;
                }
            }
            if (Hs.d(coordinatorLayout) != null) {
                return i6;
            }
            Hs.m(coordinatorLayout, new C0236h2(coordinatorLayout, this, appBarLayout));
            return i6;
        }

        public final void B(CoordinatorLayout coordinatorLayout, View view, int i) {
            A(coordinatorLayout, view, i, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }

        public final void C(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout) {
            int paddingTop = appBarLayout.getPaddingTop() + appBarLayout.e();
            int iX = x() - paddingTop;
            int childCount = appBarLayout.getChildCount();
            int i = 0;
            while (true) {
                if (i >= childCount) {
                    i = -1;
                    break;
                }
                View childAt = appBarLayout.getChildAt(i);
                int top = childAt.getTop();
                int bottom = childAt.getBottom();
                C0296j2 c0296j2 = (C0296j2) childAt.getLayoutParams();
                if ((c0296j2.a & 32) == 32) {
                    top -= ((LinearLayout.LayoutParams) c0296j2).topMargin;
                    bottom += ((LinearLayout.LayoutParams) c0296j2).bottomMargin;
                }
                int i2 = -iX;
                if (top <= i2 && bottom >= i2) {
                    break;
                } else {
                    i++;
                }
            }
            if (i >= 0) {
                View childAt2 = appBarLayout.getChildAt(i);
                C0296j2 c0296j3 = (C0296j2) childAt2.getLayoutParams();
                int i3 = c0296j3.a;
                if ((i3 & 17) == 17) {
                    int iE = -childAt2.getTop();
                    int minimumHeight = -childAt2.getBottom();
                    if (i == 0) {
                        WeakHashMap weakHashMap = Hs.a;
                        if (appBarLayout.getFitsSystemWindows() && childAt2.getFitsSystemWindows()) {
                            iE -= appBarLayout.e();
                        }
                    }
                    if ((i3 & 2) == 2) {
                        WeakHashMap weakHashMap2 = Hs.a;
                        minimumHeight += childAt2.getMinimumHeight();
                    } else if ((i3 & 5) == 5) {
                        WeakHashMap weakHashMap3 = Hs.a;
                        int minimumHeight2 = childAt2.getMinimumHeight() + minimumHeight;
                        if (iX < minimumHeight2) {
                            iE = minimumHeight2;
                        } else {
                            minimumHeight = minimumHeight2;
                        }
                    }
                    if ((i3 & 32) == 32) {
                        iE += ((LinearLayout.LayoutParams) c0296j3).topMargin;
                        minimumHeight -= ((LinearLayout.LayoutParams) c0296j3).bottomMargin;
                    }
                    if (iX < (minimumHeight + iE) / 2) {
                        iE = minimumHeight;
                    }
                    v(coordinatorLayout, appBarLayout, AbstractC0744xh.i(iE + paddingTop, -appBarLayout.f(), 0));
                }
            }
        }

        /* JADX WARN: Code duplicated, block: B:21:0x0048  */
        /* JADX WARN: Code duplicated, block: B:35:0x007c  */
        /* JADX WARN: Code duplicated, block: B:37:0x0088  */
        /* JADX WARN: Code duplicated, block: B:46:0x00a2  */
        @Override // rikka.shizuku.K7
        public final boolean g(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
            VelocityTracker velocityTracker;
            int y;
            boolean z;
            OverScroller overScroller;
            View view2;
            int iFindPointerIndex;
            if (this.h < 0) {
                this.h = ViewConfiguration.get(coordinatorLayout.getContext()).getScaledTouchSlop();
            }
            if (motionEvent.getActionMasked() == 2 && this.e) {
                int i = this.f;
                if (i != -1 && (iFindPointerIndex = motionEvent.findPointerIndex(i)) != -1) {
                    int y2 = (int) motionEvent.getY(iFindPointerIndex);
                    if (Math.abs(y2 - this.g) > this.h) {
                        this.g = y2;
                        return true;
                    }
                    if (motionEvent.getActionMasked() == 0) {
                        this.f = -1;
                        int x = (int) motionEvent.getX();
                        y = (int) motionEvent.getY();
                        WeakReference weakReference = this.n;
                        if (weakReference == null) {
                        }
                        this.e = z;
                        if (z) {
                            this.g = y;
                            this.f = motionEvent.getPointerId(0);
                            if (this.i == null) {
                                this.i = VelocityTracker.obtain();
                            }
                            overScroller = this.d;
                            if (overScroller != null) {
                                this.d.abortAnimation();
                                return true;
                            }
                        }
                    }
                    velocityTracker = this.i;
                    if (velocityTracker != null) {
                        velocityTracker.addMovement(motionEvent);
                    }
                }
            } else {
                if (motionEvent.getActionMasked() == 0) {
                    this.f = -1;
                    int x2 = (int) motionEvent.getX();
                    y = (int) motionEvent.getY();
                    WeakReference weakReference2 = this.n;
                    z = !(weakReference2 == null && ((view2 = (View) weakReference2.get()) == null || !view2.isShown() || view2.canScrollVertically(-1))) && coordinatorLayout.o(view, x2, y);
                    this.e = z;
                    if (z) {
                        this.g = y;
                        this.f = motionEvent.getPointerId(0);
                        if (this.i == null) {
                            this.i = VelocityTracker.obtain();
                        }
                        overScroller = this.d;
                        if (overScroller != null && !overScroller.isFinished()) {
                            this.d.abortAnimation();
                            return true;
                        }
                    }
                }
                velocityTracker = this.i;
                if (velocityTracker != null) {
                    velocityTracker.addMovement(motionEvent);
                }
            }
            return false;
        }

        @Override // rikka.shizuku.AbstractC0078bt, rikka.shizuku.K7
        public final boolean h(CoordinatorLayout coordinatorLayout, View view, int i) {
            int iRound;
            AppBarLayout appBarLayout = (AppBarLayout) view;
            super.h(coordinatorLayout, appBarLayout, i);
            int i2 = appBarLayout.f;
            C0267i2 c0267i2 = this.m;
            if (c0267i2 == null || (i2 & 8) != 0) {
                if (i2 != 0) {
                    boolean z = (i2 & 4) != 0;
                    if ((i2 & 2) != 0) {
                        int i3 = -appBarLayout.f();
                        if (z) {
                            v(coordinatorLayout, appBarLayout, i3);
                        } else {
                            B(coordinatorLayout, appBarLayout, i3);
                        }
                    } else if ((i2 & 1) != 0) {
                        if (z) {
                            v(coordinatorLayout, appBarLayout, 0);
                        } else {
                            B(coordinatorLayout, appBarLayout, 0);
                        }
                    }
                }
            } else if (c0267i2.c) {
                B(coordinatorLayout, appBarLayout, -appBarLayout.f());
            } else if (c0267i2.d) {
                B(coordinatorLayout, appBarLayout, 0);
            } else {
                View childAt = appBarLayout.getChildAt(c0267i2.e);
                int i4 = -childAt.getBottom();
                if (this.m.g) {
                    WeakHashMap weakHashMap = Hs.a;
                    iRound = appBarLayout.e() + childAt.getMinimumHeight() + i4;
                } else {
                    iRound = Math.round(childAt.getHeight() * this.m.f) + i4;
                }
                B(coordinatorLayout, appBarLayout, iRound);
            }
            appBarLayout.f = 0;
            this.m = null;
            int i5 = AbstractC0744xh.i(s(), -appBarLayout.f(), 0);
            Fd fd = this.a;
            if (fd == null) {
                this.b = i5;
            } else if (fd.c != i5) {
                fd.c = i5;
                fd.b();
            }
            D(coordinatorLayout, appBarLayout, s(), 0, true);
            appBarLayout.a = s();
            if (!appBarLayout.willNotDraw()) {
                WeakHashMap weakHashMap2 = Hs.a;
                appBarLayout.postInvalidateOnAnimation();
            }
            if (Hs.d(coordinatorLayout) != null) {
                return true;
            }
            Hs.m(coordinatorLayout, new C0236h2(coordinatorLayout, this, appBarLayout));
            return true;
        }

        @Override // rikka.shizuku.K7
        public final boolean i(CoordinatorLayout coordinatorLayout, View view, int i, int i2, int i3) {
            AppBarLayout appBarLayout = (AppBarLayout) view;
            if (((ViewGroup.MarginLayoutParams) ((N7) appBarLayout.getLayoutParams())).height != -2) {
                return false;
            }
            coordinatorLayout.r(appBarLayout, i, i2, View.MeasureSpec.makeMeasureSpec(0, 0));
            return true;
        }

        @Override // rikka.shizuku.K7
        public final /* bridge */ /* synthetic */ void k(CoordinatorLayout coordinatorLayout, View view, View view2, int i, int i2, int[] iArr, int i3) {
            y(coordinatorLayout, (AppBarLayout) view, view2, i2, iArr);
        }

        @Override // rikka.shizuku.K7
        public final void l(CoordinatorLayout coordinatorLayout, View view, int i, int i2, int i3, int[] iArr) {
            CoordinatorLayout coordinatorLayout2;
            AppBarLayout appBarLayout = (AppBarLayout) view;
            if (i3 < 0) {
                coordinatorLayout2 = coordinatorLayout;
                iArr[1] = A(coordinatorLayout2, appBarLayout, x() - i3, -appBarLayout.d(), 0);
            } else {
                coordinatorLayout2 = coordinatorLayout;
            }
            if (i3 == 0 && Hs.d(coordinatorLayout2) == null) {
                Hs.m(coordinatorLayout2, new C0236h2(coordinatorLayout2, this, appBarLayout));
            }
        }

        @Override // rikka.shizuku.K7
        public final void n(View view, Parcelable parcelable) {
            if (parcelable instanceof C0267i2) {
                this.m = (C0267i2) parcelable;
            } else {
                this.m = null;
            }
        }

        @Override // rikka.shizuku.K7
        public final Parcelable o(View view) {
            AbsSavedState absSavedState = View.BaseSavedState.EMPTY_STATE;
            C0267i2 c0267i2Z = z(absSavedState, (AppBarLayout) view);
            return c0267i2Z == null ? absSavedState : c0267i2Z;
        }

        @Override // rikka.shizuku.K7
        public final boolean p(CoordinatorLayout coordinatorLayout, View view, View view2, int i, int i2) {
            ValueAnimator valueAnimator;
            AppBarLayout appBarLayout = (AppBarLayout) view;
            boolean z = (i & 2) != 0 && (appBarLayout.j || (appBarLayout.f() != 0 && coordinatorLayout.getHeight() - view2.getHeight() <= appBarLayout.getHeight()));
            if (z && (valueAnimator = this.l) != null) {
                valueAnimator.cancel();
            }
            this.n = null;
            this.k = i2;
            return z;
        }

        @Override // rikka.shizuku.K7
        public final void q(CoordinatorLayout coordinatorLayout, View view, View view2, int i) {
            AppBarLayout appBarLayout = (AppBarLayout) view;
            if (this.k == 0 || i == 1) {
                C(coordinatorLayout, appBarLayout);
                if (appBarLayout.j) {
                    appBarLayout.i(appBarLayout.j(view2));
                }
            }
            this.n = new WeakReference(view2);
        }

        /* JADX WARN: Code duplicated, block: B:39:0x00e9  */
        /* JADX WARN: Code duplicated, block: B:42:0x00f2  */
        /* JADX WARN: Code duplicated, block: B:45:0x00f9 A[ADDED_TO_REGION] */
        @Override // rikka.shizuku.K7
        public final boolean r(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
            boolean z;
            VelocityTracker velocityTracker;
            VelocityTracker velocityTracker2;
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked != 1) {
                if (actionMasked == 2) {
                    int iFindPointerIndex = motionEvent.findPointerIndex(this.f);
                    if (iFindPointerIndex != -1) {
                        int y = (int) motionEvent.getY(iFindPointerIndex);
                        int i = this.g - y;
                        this.g = y;
                        AppBarLayout appBarLayout = (AppBarLayout) view;
                        A(coordinatorLayout, view, x() - i, appBarLayout.e() + (-appBarLayout.d()), 0);
                    }
                }
                if (actionMasked != 3) {
                    if (actionMasked == 6) {
                        int i2 = motionEvent.getActionIndex() == 0 ? 1 : 0;
                        this.f = motionEvent.getPointerId(i2);
                        this.g = (int) (motionEvent.getY(i2) + 0.5f);
                    }
                }
                z = false;
                velocityTracker2 = this.i;
                if (velocityTracker2 != null) {
                    velocityTracker2.addMovement(motionEvent);
                }
                return !this.e || z;
            }
            VelocityTracker velocityTracker3 = this.i;
            if (velocityTracker3 != null) {
                velocityTracker3.addMovement(motionEvent);
                this.i.computeCurrentVelocity(1000);
                float yVelocity = this.i.getYVelocity(this.f);
                AppBarLayout appBarLayout2 = (AppBarLayout) view;
                int i3 = -appBarLayout2.f();
                Runnable runnable = this.c;
                if (runnable != null) {
                    view.removeCallbacks(runnable);
                    this.c = null;
                }
                if (this.d == null) {
                    this.d = new OverScroller(view.getContext());
                }
                this.d.fling(0, s(), 0, Math.round(yVelocity), 0, 0, i3, 0);
                if (this.d.computeScrollOffset()) {
                    RunnableC0186fe runnableC0186fe = new RunnableC0186fe(this, coordinatorLayout, view);
                    this.c = runnableC0186fe;
                    WeakHashMap weakHashMap = Hs.a;
                    view.postOnAnimation(runnableC0186fe);
                } else {
                    C(coordinatorLayout, appBarLayout2);
                    if (appBarLayout2.j) {
                        appBarLayout2.i(appBarLayout2.j(w(coordinatorLayout)));
                    }
                }
                z = true;
            }
            this.e = false;
            this.f = -1;
            velocityTracker = this.i;
            if (velocityTracker != null) {
                velocityTracker.recycle();
                this.i = null;
            }
            velocityTracker2 = this.i;
            if (velocityTracker2 != null) {
                velocityTracker2.addMovement(motionEvent);
            }
            if (this.e) {
            }
            z = false;
            this.e = false;
            this.f = -1;
            velocityTracker = this.i;
            if (velocityTracker != null) {
                velocityTracker.recycle();
                this.i = null;
            }
            velocityTracker2 = this.i;
            if (velocityTracker2 != null) {
                velocityTracker2.addMovement(motionEvent);
            }
            if (this.e) {
            }
        }

        public final void v(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int i) {
            int iAbs = Math.abs(x() - i);
            float fAbs = Math.abs(0.0f);
            int iRound = fAbs > 0.0f ? Math.round((iAbs / fAbs) * 1000.0f) * 3 : (int) (((iAbs / appBarLayout.getHeight()) + 1.0f) * 150.0f);
            int iX = x();
            if (iX == i) {
                ValueAnimator valueAnimator = this.l;
                if (valueAnimator == null || !valueAnimator.isRunning()) {
                    return;
                }
                this.l.cancel();
                return;
            }
            ValueAnimator valueAnimator2 = this.l;
            if (valueAnimator2 == null) {
                ValueAnimator valueAnimator3 = new ValueAnimator();
                this.l = valueAnimator3;
                valueAnimator3.setInterpolator(V1.e);
                this.l.addUpdateListener(new C0205g2(coordinatorLayout, this, appBarLayout));
            } else {
                valueAnimator2.cancel();
            }
            this.l.setDuration(Math.min(iRound, 600));
            this.l.setIntValues(iX, i);
            this.l.start();
        }

        public final int x() {
            return s() + this.j;
        }

        /* JADX WARN: Code duplicated, block: B:9:0x002b  */
        public final void y(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, int i, int[] iArr) {
            AppBarLayout appBarLayout2;
            int i2;
            int iC;
            if (i == 0) {
                appBarLayout2 = appBarLayout;
            } else {
                if (i < 0) {
                    i2 = -appBarLayout.f();
                    iC = appBarLayout.c() + i2;
                } else {
                    i2 = -appBarLayout.f();
                    iC = 0;
                }
                int i3 = i2;
                int i4 = iC;
                if (i3 != i4) {
                    appBarLayout2 = appBarLayout;
                    iArr[1] = A(coordinatorLayout, appBarLayout2, x() - i, i3, i4);
                } else {
                    appBarLayout2 = appBarLayout;
                }
            }
            if (appBarLayout2.j) {
                appBarLayout2.i(appBarLayout2.j(view));
            }
        }

        public final C0267i2 z(Parcelable parcelable, AppBarLayout appBarLayout) {
            int iS = s();
            int childCount = appBarLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = appBarLayout.getChildAt(i);
                int bottom = childAt.getBottom() + iS;
                if (childAt.getTop() + iS <= 0 && bottom >= 0) {
                    if (parcelable == null) {
                        parcelable = F.b;
                    }
                    C0267i2 c0267i2 = new C0267i2(parcelable);
                    boolean z = iS == 0;
                    c0267i2.d = z;
                    c0267i2.c = !z && (-iS) >= appBarLayout.f();
                    c0267i2.e = i;
                    WeakHashMap weakHashMap = Hs.a;
                    c0267i2.g = bottom == appBarLayout.e() + childAt.getMinimumHeight();
                    c0267i2.f = bottom / childAt.getHeight();
                    return c0267i2;
                }
            }
            return null;
        }

        public BaseBehavior(Context context, AttributeSet attributeSet) {
            super(0);
            this.f = -1;
            this.h = -1;
        }
    }

    public class ScrollingViewBehavior extends AbstractC0078bt {
        public final Rect c;
        public final Rect d;
        public int e;
        public final int f;

        public ScrollingViewBehavior() {
            this.c = new Rect();
            this.d = new Rect();
            this.e = 0;
        }

        public static AppBarLayout u(ArrayList arrayList) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                View view = (View) arrayList.get(i);
                if (view instanceof AppBarLayout) {
                    return (AppBarLayout) view;
                }
            }
            return null;
        }

        @Override // rikka.shizuku.K7
        public final boolean b(View view, View view2) {
            return view2 instanceof AppBarLayout;
        }

        @Override // rikka.shizuku.K7
        public boolean d(CoordinatorLayout coordinatorLayout, View view, View view2) {
            K7 k7 = ((N7) view2.getLayoutParams()).a;
            if (k7 instanceof BaseBehavior) {
                int bottom = (((view2.getBottom() - view.getTop()) + ((BaseBehavior) k7).j) + this.e) - v(view2);
                WeakHashMap weakHashMap = Hs.a;
                view.offsetTopAndBottom(bottom);
            }
            if (!(view2 instanceof AppBarLayout)) {
                return false;
            }
            AppBarLayout appBarLayout = (AppBarLayout) view2;
            if (!appBarLayout.j) {
                return false;
            }
            appBarLayout.i(appBarLayout.j(view));
            return false;
        }

        @Override // rikka.shizuku.K7
        public final void e(CoordinatorLayout coordinatorLayout, View view) {
            if (view instanceof AppBarLayout) {
                Hs.m(coordinatorLayout, null);
            }
        }

        @Override // rikka.shizuku.K7
        public final boolean i(CoordinatorLayout coordinatorLayout, View view, int i, int i2, int i3) {
            AppBarLayout appBarLayoutU;
            au auVar;
            int i4 = view.getLayoutParams().height;
            if ((i4 != -1 && i4 != -2) || (appBarLayoutU = u(coordinatorLayout.j(view))) == null) {
                return false;
            }
            int size = View.MeasureSpec.getSize(i3);
            if (size > 0) {
                WeakHashMap weakHashMap = Hs.a;
                if (appBarLayoutU.getFitsSystemWindows() && (auVar = coordinatorLayout.n) != null) {
                    size += auVar.a() + auVar.b();
                }
            } else {
                size = coordinatorLayout.getHeight();
            }
            int iF = appBarLayoutU.f() + size;
            int measuredHeight = appBarLayoutU.getMeasuredHeight();
            if (this instanceof SearchBar$ScrollingViewBehavior) {
                view.setTranslationY(-measuredHeight);
            } else {
                view.setTranslationY(0.0f);
                iF -= measuredHeight;
            }
            coordinatorLayout.r(view, i, i2, View.MeasureSpec.makeMeasureSpec(iF, i4 == -1 ? 1073741824 : Integer.MIN_VALUE));
            return true;
        }

        @Override // rikka.shizuku.K7
        public final boolean m(CoordinatorLayout coordinatorLayout, View view, Rect rect, boolean z) {
            AppBarLayout appBarLayoutU = u(coordinatorLayout.j(view));
            if (appBarLayoutU != null) {
                Rect rect2 = new Rect(rect);
                rect2.offset(view.getLeft(), view.getTop());
                int width = coordinatorLayout.getWidth();
                int height = coordinatorLayout.getHeight();
                Rect rect3 = this.c;
                rect3.set(0, 0, width, height);
                if (!rect3.contains(rect2)) {
                    appBarLayoutU.h(false, !z, true);
                    return true;
                }
            }
            return false;
        }

        @Override // rikka.shizuku.AbstractC0078bt
        public final void t(CoordinatorLayout coordinatorLayout, View view, int i) {
            AppBarLayout appBarLayoutU = u(coordinatorLayout.j(view));
            if (appBarLayoutU == null) {
                coordinatorLayout.q(view, i);
                this.e = 0;
                return;
            }
            N7 n7 = (N7) view.getLayoutParams();
            int paddingLeft = coordinatorLayout.getPaddingLeft() + ((ViewGroup.MarginLayoutParams) n7).leftMargin;
            int bottom = appBarLayoutU.getBottom() + ((ViewGroup.MarginLayoutParams) n7).topMargin;
            int width = (coordinatorLayout.getWidth() - coordinatorLayout.getPaddingRight()) - ((ViewGroup.MarginLayoutParams) n7).rightMargin;
            int bottom2 = ((appBarLayoutU.getBottom() + coordinatorLayout.getHeight()) - coordinatorLayout.getPaddingBottom()) - ((ViewGroup.MarginLayoutParams) n7).bottomMargin;
            Rect rect = this.c;
            rect.set(paddingLeft, bottom, width, bottom2);
            au auVar = coordinatorLayout.n;
            if (auVar != null) {
                WeakHashMap weakHashMap = Hs.a;
                if (coordinatorLayout.getFitsSystemWindows() && !view.getFitsSystemWindows()) {
                    int i2 = rect.left;
                    Wt wt = auVar.a;
                    rect.left = wt.j().a + i2;
                    rect.right -= wt.j().c;
                }
            }
            int i3 = n7.c;
            if (i3 == 0) {
                i3 = 8388659;
            }
            int measuredWidth = view.getMeasuredWidth();
            int measuredHeight = view.getMeasuredHeight();
            Rect rect2 = this.d;
            Gravity.apply(i3, measuredWidth, measuredHeight, rect, rect2, i);
            int iV = v(appBarLayoutU);
            view.layout(rect2.left, rect2.top - iV, rect2.right, rect2.bottom - iV);
            this.e = rect2.top - appBarLayoutU.getBottom();
        }

        public final int v(View view) {
            int i;
            if (this.f == 0) {
                return 0;
            }
            float f = 0.0f;
            if (view instanceof AppBarLayout) {
                AppBarLayout appBarLayout = (AppBarLayout) view;
                int iF = appBarLayout.f();
                int iC = appBarLayout.c();
                K7 k7 = ((N7) appBarLayout.getLayoutParams()).a;
                int iX = k7 instanceof BaseBehavior ? ((BaseBehavior) k7).x() : 0;
                if ((iC == 0 || iF + iX > iC) && (i = iF - iC) != 0) {
                    f = (iX / i) + 1.0f;
                }
            }
            int i2 = this.f;
            return AbstractC0744xh.i((int) (f * i2), 0, i2);
        }

        public ScrollingViewBehavior(Context context, AttributeSet attributeSet) {
            super(0);
            this.c = new Rect();
            this.d = new Rect();
            this.e = 0;
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Lk.A);
            this.f = typedArrayObtainStyledAttributes.getDimensionPixelSize(0, 0);
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public final LinearLayout.LayoutParams generateDefaultLayoutParams() {
        C0296j2 c0296j2 = new C0296j2(-1, -2);
        c0296j2.a = 1;
        return c0296j2;
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public final /* bridge */ /* synthetic */ LinearLayout.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return b(layoutParams);
    }
}
