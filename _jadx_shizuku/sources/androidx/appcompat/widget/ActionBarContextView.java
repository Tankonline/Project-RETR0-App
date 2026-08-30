package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.WeakHashMap;
import moe.shizuku.privileged.api.R;
import rikka.shizuku.AbstractC0323jt;
import rikka.shizuku.AbstractC0744xh;
import rikka.shizuku.C0;
import rikka.shizuku.C0109ct;
import rikka.shizuku.C0233h;
import rikka.shizuku.C0758y0;
import rikka.shizuku.F0;
import rikka.shizuku.Hs;
import rikka.shizuku.InterfaceC0745xi;
import rikka.shizuku.MenuC0129di;
import rikka.shizuku.Nk;
import rikka.shizuku.ViewOnApplyWindowInsetsListenerC0541r0;
import rikka.shizuku.ViewOnAttachStateChangeListenerC0511q0;
import rikka.shizuku.ViewOnClickListenerC0572s0;

/* JADX INFO: loaded from: classes.dex */
public class ActionBarContextView extends ViewGroup {
    public final C0233h a;
    public final Context b;
    public ActionMenuView c;
    public C0 d;
    public int e;
    public C0109ct f;
    public boolean g;
    public boolean h;
    public CharSequence i;
    public CharSequence j;
    public View k;
    public View l;
    public View m;
    public LinearLayout n;
    public TextView o;
    public TextView p;
    public final int q;
    public final int r;
    public boolean s;
    public final int t;
    public final int u;
    public int v;
    public final int w;
    public int x;
    public int y;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ActionBarContextView(Context context, AttributeSet attributeSet) {
        int resourceId;
        super(context, attributeSet, R.attr._0_resource_name_obfuscated_res_0x7f04001c);
        int i = 0;
        C0233h c0233h = new C0233h();
        c0233h.c = this;
        c0233h.a = false;
        this.a = c0233h;
        TypedValue typedValue = new TypedValue();
        if (!context.getTheme().resolveAttribute(R.attr._0_resource_name_obfuscated_res_0x7f040002, typedValue, true) || typedValue.resourceId == 0) {
            this.b = context;
        } else {
            this.b = new ContextThemeWrapper(context, typedValue.resourceId);
        }
        this.x = Integer.MIN_VALUE;
        this.y = Integer.MIN_VALUE;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Nk.d, R.attr._0_resource_name_obfuscated_res_0x7f04001c, 0);
        Drawable drawable = (!typedArrayObtainStyledAttributes.hasValue(0) || (resourceId = typedArrayObtainStyledAttributes.getResourceId(0, 0)) == 0) ? typedArrayObtainStyledAttributes.getDrawable(0) : AbstractC0744xh.v(context, resourceId);
        WeakHashMap weakHashMap = Hs.a;
        setBackground(drawable);
        this.q = typedArrayObtainStyledAttributes.getResourceId(5, 0);
        this.r = typedArrayObtainStyledAttributes.getResourceId(4, 0);
        this.e = typedArrayObtainStyledAttributes.getLayoutDimension(3, 0);
        this.t = typedArrayObtainStyledAttributes.getResourceId(2, R.layout._0_resource_name_obfuscated_res_0x7f0c0005);
        typedArrayObtainStyledAttributes.recycle();
        this.u = this.e;
        this.w = getPaddingTop();
        if (Build.VERSION.SDK_INT < 30) {
            addOnAttachStateChangeListener(new ViewOnAttachStateChangeListenerC0511q0(i, this));
        }
        setSystemUiVisibility(getSystemUiVisibility() | 1792);
        setOnApplyWindowInsetsListener(new ViewOnApplyWindowInsetsListenerC0541r0(this));
    }

    public static void a(ActionBarContextView actionBarContextView, WindowInsets windowInsets) {
        int systemWindowInsetTop = windowInsets.getSystemWindowInsetTop();
        actionBarContextView.v = systemWindowInsetTop;
        actionBarContextView.e = actionBarContextView.u + systemWindowInsetTop;
        actionBarContextView.setPadding(actionBarContextView.getPaddingLeft(), actionBarContextView.w + actionBarContextView.v, actionBarContextView.getPaddingRight(), actionBarContextView.getPaddingBottom());
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) actionBarContextView.getLayoutParams();
        if (actionBarContextView.x == Integer.MIN_VALUE) {
            actionBarContextView.x = marginLayoutParams.leftMargin;
            actionBarContextView.y = marginLayoutParams.rightMargin;
        }
        marginLayoutParams.leftMargin = windowInsets.getSystemWindowInsetLeft() + actionBarContextView.x;
        marginLayoutParams.rightMargin = windowInsets.getSystemWindowInsetRight() + actionBarContextView.y;
        actionBarContextView.setLayoutParams(marginLayoutParams);
    }

    public static int g(View view, int i, int i2) {
        view.measure(View.MeasureSpec.makeMeasureSpec(i, Integer.MIN_VALUE), i2);
        return Math.max(0, i - view.getMeasuredWidth());
    }

    public static int h(View view, int i, int i2, int i3, boolean z) {
        int measuredWidth = view.getMeasuredWidth();
        int measuredHeight = view.getMeasuredHeight();
        int i4 = ((i3 - measuredHeight) / 2) + i2;
        if (z) {
            view.layout(i - measuredWidth, i4, i, measuredHeight + i4);
        } else {
            view.layout(i, i4, i + measuredWidth, measuredHeight + i4);
        }
        return z ? -measuredWidth : measuredWidth;
    }

    public final void d(F0 f0) {
        View view = this.k;
        int i = 0;
        if (view == null) {
            View viewInflate = LayoutInflater.from(getContext()).inflate(this.t, (ViewGroup) this, false);
            this.k = viewInflate;
            addView(viewInflate);
        } else if (view.getParent() == null) {
            addView(this.k);
        }
        View viewFindViewById = this.k.findViewById(R.id._0_resource_name_obfuscated_res_0x7f090040);
        this.l = viewFindViewById;
        viewFindViewById.setOnClickListener(new ViewOnClickListenerC0572s0(i, f0));
        MenuC0129di menuC0129diD = f0.d();
        C0 c0 = this.d;
        if (c0 != null) {
            c0.e();
            C0758y0 c0758y0 = c0.r;
            if (c0758y0 != null && c0758y0.b()) {
                c0758y0.i.dismiss();
            }
        }
        C0 c1 = new C0(getContext());
        this.d = c1;
        c1.j = true;
        c1.k = true;
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-2, -1);
        menuC0129diD.b(this.d, this.b);
        C0 c2 = this.d;
        InterfaceC0745xi interfaceC0745xi = c2.h;
        if (interfaceC0745xi == null) {
            InterfaceC0745xi interfaceC0745xi2 = (InterfaceC0745xi) c2.d.inflate(c2.f, (ViewGroup) this, false);
            c2.h = interfaceC0745xi2;
            interfaceC0745xi2.a(c2.c);
            c2.i();
        }
        InterfaceC0745xi interfaceC0745xi3 = c2.h;
        if (interfaceC0745xi != interfaceC0745xi3) {
            ActionMenuView actionMenuView = (ActionMenuView) interfaceC0745xi3;
            actionMenuView.t = c2;
            c2.h = actionMenuView;
            actionMenuView.p = c2.c;
        }
        ActionMenuView actionMenuView2 = (ActionMenuView) interfaceC0745xi3;
        this.c = actionMenuView2;
        WeakHashMap weakHashMap = Hs.a;
        actionMenuView2.setBackground(null);
        addView(this.c, layoutParams);
    }

    public final void e() {
        if (this.n == null) {
            LayoutInflater.from(getContext()).inflate(R.layout._0_resource_name_obfuscated_res_0x7f0c0000, this);
            LinearLayout linearLayout = (LinearLayout) getChildAt(getChildCount() - 1);
            this.n = linearLayout;
            this.o = (TextView) linearLayout.findViewById(R.id._0_resource_name_obfuscated_res_0x7f090037);
            this.p = (TextView) this.n.findViewById(R.id._0_resource_name_obfuscated_res_0x7f090036);
            int i = this.q;
            if (i != 0) {
                this.o.setTextAppearance(getContext(), i);
            }
            int i2 = this.r;
            if (i2 != 0) {
                this.p.setTextAppearance(getContext(), i2);
            }
        }
        this.o.setText(this.i);
        this.p.setText(this.j);
        boolean zIsEmpty = TextUtils.isEmpty(this.i);
        boolean zIsEmpty2 = TextUtils.isEmpty(this.j);
        this.p.setVisibility(!zIsEmpty2 ? 0 : 8);
        this.n.setVisibility((zIsEmpty && zIsEmpty2) ? 8 : 0);
        if (this.n.getParent() == null) {
            addView(this.n);
        }
    }

    public final void f() {
        removeAllViews();
        this.m = null;
        this.c = null;
        this.d = null;
        View view = this.l;
        if (view != null) {
            view.setOnClickListener(null);
        }
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new ViewGroup.MarginLayoutParams(-1, -2);
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new ViewGroup.MarginLayoutParams(getContext(), attributeSet);
    }

    public final void i(View view) {
        LinearLayout linearLayout;
        View view2 = this.m;
        if (view2 != null) {
            removeView(view2);
        }
        this.m = view;
        if (view != null && (linearLayout = this.n) != null) {
            removeView(linearLayout);
            this.n = null;
        }
        if (view != null) {
            addView(view);
        }
        requestLayout();
    }

    public final C0109ct j(int i, long j) {
        C0109ct c0109ct = this.f;
        if (c0109ct != null) {
            c0109ct.b();
        }
        C0233h c0233h = this.a;
        if (i != 0) {
            C0109ct c0109ctA = Hs.a(this);
            c0109ctA.a(0.0f);
            c0109ctA.c(j);
            ((ActionBarContextView) c0233h.c).f = c0109ctA;
            c0233h.b = i;
            c0109ctA.d(c0233h);
            return c0109ctA;
        }
        if (getVisibility() != 0) {
            setAlpha(0.0f);
        }
        C0109ct c0109ctA2 = Hs.a(this);
        c0109ctA2.a(1.0f);
        c0109ctA2.c(j);
        ((ActionBarContextView) c0233h.c).f = c0109ctA2;
        c0233h.b = i;
        c0109ctA2.d(c0233h);
        return c0109ctA2;
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        int i;
        super.onConfigurationChanged(configuration);
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(null, Nk.a, R.attr._0_resource_name_obfuscated_res_0x7f040005, 0);
        typedArrayObtainStyledAttributes.getLayoutDimension(13, 0);
        this.e = this.u + this.v;
        typedArrayObtainStyledAttributes.recycle();
        C0 c0 = this.d;
        if (c0 != null) {
            Configuration configuration2 = c0.b.getResources().getConfiguration();
            int i2 = configuration2.screenWidthDp;
            int i3 = configuration2.screenHeightDp;
            if (configuration2.smallestScreenWidthDp > 600 || i2 > 600 || ((i2 > 960 && i3 > 720) || (i2 > 720 && i3 > 960))) {
                i = 5;
            } else if (i2 >= 500 || ((i2 > 640 && i3 > 480) || (i2 > 480 && i3 > 640))) {
                i = 4;
            } else {
                i = i2 >= 360 ? 3 : 2;
            }
            c0.n = i;
            MenuC0129di menuC0129di = c0.c;
            if (menuC0129di != null) {
                menuC0129di.p(true);
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        C0 c0 = this.d;
        if (c0 != null) {
            c0.e();
            C0758y0 c0758y0 = this.d.r;
            if (c0758y0 == null || !c0758y0.b()) {
                return;
            }
            c0758y0.i.dismiss();
        }
    }

    @Override // android.view.View
    public final boolean onHoverEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 9) {
            this.h = false;
        }
        if (!this.h) {
            boolean zOnHoverEvent = super.onHoverEvent(motionEvent);
            if (actionMasked == 9 && !zOnHoverEvent) {
                this.h = true;
            }
        }
        if (actionMasked != 10 && actionMasked != 3) {
            return true;
        }
        this.h = false;
        return true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        boolean zA = AbstractC0323jt.a(this);
        int paddingRight = zA ? (i3 - i) - getPaddingRight() : getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingTop2 = ((i4 - i2) - getPaddingTop()) - getPaddingBottom();
        View view = this.k;
        if (view != null && view.getVisibility() != 8) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.k.getLayoutParams();
            int i5 = zA ? marginLayoutParams.rightMargin : marginLayoutParams.leftMargin;
            int i6 = zA ? marginLayoutParams.leftMargin : marginLayoutParams.rightMargin;
            int i7 = zA ? paddingRight - i5 : paddingRight + i5;
            int iH = h(this.k, i7, paddingTop, paddingTop2, zA) + i7;
            paddingRight = zA ? iH - i6 : iH + i6;
        }
        LinearLayout linearLayout = this.n;
        if (linearLayout != null && this.m == null && linearLayout.getVisibility() != 8) {
            paddingRight += h(this.n, paddingRight, paddingTop, paddingTop2, zA);
        }
        View view2 = this.m;
        if (view2 != null) {
            h(view2, paddingRight, paddingTop, paddingTop2, zA);
        }
        int paddingLeft = zA ? getPaddingLeft() : (i3 - i) - getPaddingRight();
        ActionMenuView actionMenuView = this.c;
        if (actionMenuView != null) {
            h(actionMenuView, paddingLeft, paddingTop, paddingTop2, !zA);
        }
    }

    @Override // android.view.View
    public final void onMeasure(int i, int i2) {
        if (View.MeasureSpec.getMode(i) != 1073741824) {
            throw new IllegalStateException(getClass().getSimpleName().concat(" can only be used with android:layout_width=\"match_parent\" (or fill_parent)"));
        }
        if (View.MeasureSpec.getMode(i2) == 0) {
            throw new IllegalStateException(getClass().getSimpleName().concat(" can only be used with android:layout_height=\"wrap_content\""));
        }
        int size = View.MeasureSpec.getSize(i);
        int size2 = this.e;
        if (size2 <= 0) {
            size2 = View.MeasureSpec.getSize(i2);
        }
        int paddingBottom = getPaddingBottom() + getPaddingTop();
        int paddingLeft = (size - getPaddingLeft()) - getPaddingRight();
        int iMin = size2 - paddingBottom;
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(iMin, Integer.MIN_VALUE);
        View view = this.k;
        if (view != null) {
            int iG = g(view, paddingLeft, iMakeMeasureSpec);
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.k.getLayoutParams();
            paddingLeft = iG - (marginLayoutParams.leftMargin + marginLayoutParams.rightMargin);
        }
        ActionMenuView actionMenuView = this.c;
        if (actionMenuView != null && actionMenuView.getParent() == this) {
            paddingLeft = g(this.c, paddingLeft, iMakeMeasureSpec);
        }
        LinearLayout linearLayout = this.n;
        if (linearLayout != null && this.m == null) {
            if (this.s) {
                this.n.measure(View.MeasureSpec.makeMeasureSpec(0, 0), iMakeMeasureSpec);
                int measuredWidth = this.n.getMeasuredWidth();
                boolean z = measuredWidth <= paddingLeft;
                if (z) {
                    paddingLeft -= measuredWidth;
                }
                this.n.setVisibility(z ? 0 : 8);
            } else {
                paddingLeft = g(linearLayout, paddingLeft, iMakeMeasureSpec);
            }
        }
        View view2 = this.m;
        if (view2 != null) {
            ViewGroup.LayoutParams layoutParams = view2.getLayoutParams();
            int i3 = layoutParams.width;
            int i4 = i3 != -2 ? 1073741824 : Integer.MIN_VALUE;
            if (i3 >= 0) {
                paddingLeft = Math.min(i3, paddingLeft);
            }
            int i5 = layoutParams.height;
            int i6 = i5 == -2 ? Integer.MIN_VALUE : 1073741824;
            if (i5 >= 0) {
                iMin = Math.min(i5, iMin);
            }
            this.m.measure(View.MeasureSpec.makeMeasureSpec(paddingLeft, i4), View.MeasureSpec.makeMeasureSpec(iMin, i6));
        }
        if (this.e > 0) {
            setMeasuredDimension(size, size2);
            return;
        }
        int childCount = getChildCount();
        int i7 = 0;
        for (int i8 = 0; i8 < childCount; i8++) {
            int measuredHeight = getChildAt(i8).getMeasuredHeight() + paddingBottom;
            if (measuredHeight > i7) {
                i7 = measuredHeight;
            }
        }
        setMeasuredDimension(size, i7);
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.g = false;
        }
        if (!this.g) {
            boolean zOnTouchEvent = super.onTouchEvent(motionEvent);
            if (actionMasked == 0 && !zOnTouchEvent) {
                this.g = true;
            }
        }
        if (actionMasked != 1 && actionMasked != 3) {
            return true;
        }
        this.g = false;
        return true;
    }

    @Override // android.view.View
    public final void setVisibility(int i) {
        if (i != getVisibility()) {
            C0109ct c0109ct = this.f;
            if (c0109ct != null) {
                c0109ct.b();
            }
            super.setVisibility(i);
        }
    }

    @Override // android.view.ViewGroup
    public final boolean shouldDelayChildPressedState() {
        return false;
    }
}
