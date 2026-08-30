package com.google.android.material.internal;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.CheckedTextView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import java.util.WeakHashMap;
import rikka.shizuku.AbstractC0688vn;
import rikka.shizuku.C0221gi;
import rikka.shizuku.Dg;
import rikka.shizuku.Eg;
import rikka.shizuku.Hs;
import rikka.shizuku.InterfaceC0714wi;
import rikka.shizuku.Lk;
import rikka.shizuku.R5;
import rikka.shizuku.X8;

/* JADX INFO: loaded from: classes.dex */
public class NavigationMenuItemView extends Eg implements InterfaceC0714wi {
    public static final int[] C = {R.attr.state_checked};
    public C0221gi A;
    public final R5 B;
    public Drawable p;
    public final Rect q;
    public final Rect r;
    public int s;
    public final boolean t;
    public boolean u;
    public final int v;
    public boolean w;
    public final boolean x;
    public final CheckedTextView y;
    public FrameLayout z;

    public NavigationMenuItemView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
        this.q = new Rect();
        this.r = new Rect();
        this.s = 119;
        this.t = true;
        this.u = false;
        int[] iArr = Lk.k;
        X8.j(context, attributeSet, 0, 0);
        X8.p(context, attributeSet, iArr, 0, 0, new int[0]);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, iArr, 0, 0);
        this.s = typedArrayObtainStyledAttributes.getInt(1, this.s);
        Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(0);
        if (drawable != null) {
            setForeground(drawable);
        }
        this.t = typedArrayObtainStyledAttributes.getBoolean(2, true);
        typedArrayObtainStyledAttributes.recycle();
        this.x = true;
        R5 r5 = new R5(3, this);
        this.B = r5;
        if (this.d != 0) {
            this.d = 0;
            requestLayout();
        }
        LayoutInflater.from(context).inflate(moe.shizuku.privileged.api.R.layout._0_resource_name_obfuscated_res_0x7f0c0036, (ViewGroup) this, true);
        this.v = context.getResources().getDimensionPixelSize(moe.shizuku.privileged.api.R.dimen._0_resource_name_obfuscated_res_0x7f070087);
        CheckedTextView checkedTextView = (CheckedTextView) findViewById(moe.shizuku.privileged.api.R.id._0_resource_name_obfuscated_res_0x7f09008d);
        this.y = checkedTextView;
        checkedTextView.setDuplicateParentStateEnabled(true);
        Hs.m(checkedTextView, r5);
    }

    @Override // rikka.shizuku.InterfaceC0714wi
    public final C0221gi b() {
        return this.A;
    }

    @Override // android.view.View
    public final void draw(Canvas canvas) {
        super.draw(canvas);
        Drawable drawable = this.p;
        if (drawable != null) {
            if (this.u) {
                this.u = false;
                int right = getRight() - getLeft();
                int bottom = getBottom() - getTop();
                boolean z = this.t;
                Rect rect = this.q;
                if (z) {
                    rect.set(0, 0, right, bottom);
                } else {
                    rect.set(getPaddingLeft(), getPaddingTop(), right - getPaddingRight(), bottom - getPaddingBottom());
                }
                int i = this.s;
                int intrinsicWidth = drawable.getIntrinsicWidth();
                int intrinsicHeight = drawable.getIntrinsicHeight();
                Rect rect2 = this.r;
                Gravity.apply(i, intrinsicWidth, intrinsicHeight, rect, rect2);
                drawable.setBounds(rect2);
            }
            drawable.draw(canvas);
        }
    }

    @Override // android.view.View
    public final void drawableHotspotChanged(float f, float f2) {
        super.drawableHotspotChanged(f, f2);
        Drawable drawable = this.p;
        if (drawable != null) {
            drawable.setHotspot(f, f2);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable = this.p;
        if (drawable == null || !drawable.isStateful()) {
            return;
        }
        this.p.setState(getDrawableState());
    }

    @Override // rikka.shizuku.InterfaceC0714wi
    public final void e(C0221gi c0221gi) {
        StateListDrawable stateListDrawable;
        this.A = c0221gi;
        int i = c0221gi.a;
        if (i > 0) {
            setId(i);
        }
        setVisibility(c0221gi.isVisible() ? 0 : 8);
        if (getBackground() == null) {
            TypedValue typedValue = new TypedValue();
            if (getContext().getTheme().resolveAttribute(moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f0400f5, typedValue, true)) {
                stateListDrawable = new StateListDrawable();
                stateListDrawable.addState(C, new ColorDrawable(typedValue.data));
                stateListDrawable.addState(ViewGroup.EMPTY_STATE_SET, new ColorDrawable(0));
            } else {
                stateListDrawable = null;
            }
            WeakHashMap weakHashMap = Hs.a;
            setBackground(stateListDrawable);
        }
        boolean zIsCheckable = c0221gi.isCheckable();
        refreshDrawableState();
        boolean z = this.w;
        CheckedTextView checkedTextView = this.y;
        if (z != zIsCheckable) {
            this.w = zIsCheckable;
            this.B.h(checkedTextView, 2048);
        }
        boolean zIsChecked = c0221gi.isChecked();
        refreshDrawableState();
        checkedTextView.setChecked(zIsChecked);
        checkedTextView.setTypeface(checkedTextView.getTypeface(), (zIsChecked && this.x) ? 1 : 0);
        setEnabled(c0221gi.isEnabled());
        checkedTextView.setText(c0221gi.e);
        Drawable icon = c0221gi.getIcon();
        if (icon != null) {
            int i2 = this.v;
            icon.setBounds(0, 0, i2, i2);
        }
        checkedTextView.setCompoundDrawablesRelative(icon, null, null, null);
        View actionView = c0221gi.getActionView();
        if (actionView != null) {
            if (this.z == null) {
                this.z = (FrameLayout) ((ViewStub) findViewById(moe.shizuku.privileged.api.R.id._0_resource_name_obfuscated_res_0x7f09008c)).inflate();
            }
            if (actionView.getParent() != null) {
                ((ViewGroup) actionView.getParent()).removeView(actionView);
            }
            this.z.removeAllViews();
            this.z.addView(actionView);
        }
        setContentDescription(c0221gi.q);
        AbstractC0688vn.y(this, c0221gi.r);
        C0221gi c0221gi2 = this.A;
        if (c0221gi2.e == null && c0221gi2.getIcon() == null && this.A.getActionView() != null) {
            checkedTextView.setVisibility(8);
            FrameLayout frameLayout = this.z;
            if (frameLayout != null) {
                Dg dg = (Dg) frameLayout.getLayoutParams();
                ((LinearLayout.LayoutParams) dg).width = -1;
                this.z.setLayoutParams(dg);
                return;
            }
            return;
        }
        checkedTextView.setVisibility(0);
        FrameLayout frameLayout2 = this.z;
        if (frameLayout2 != null) {
            Dg dg2 = (Dg) frameLayout2.getLayoutParams();
            ((LinearLayout.LayoutParams) dg2).width = -2;
            this.z.setLayoutParams(dg2);
        }
    }

    @Override // android.view.View
    public final Drawable getForeground() {
        return this.p;
    }

    @Override // android.view.View
    public final int getForegroundGravity() {
        return this.s;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable = this.p;
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final int[] onCreateDrawableState(int i) {
        int[] iArrOnCreateDrawableState = super.onCreateDrawableState(i + 1);
        C0221gi c0221gi = this.A;
        if (c0221gi != null && c0221gi.isCheckable() && this.A.isChecked()) {
            View.mergeDrawableStates(iArrOnCreateDrawableState, C);
        }
        return iArrOnCreateDrawableState;
    }

    @Override // rikka.shizuku.Eg, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.u = z | this.u;
    }

    @Override // android.view.View
    public final void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.u = true;
    }

    @Override // android.view.View
    public final void setForeground(Drawable drawable) {
        Drawable drawable2 = this.p;
        if (drawable2 != drawable) {
            if (drawable2 != null) {
                drawable2.setCallback(null);
                unscheduleDrawable(this.p);
            }
            this.p = drawable;
            this.u = true;
            if (drawable != null) {
                setWillNotDraw(false);
                drawable.setCallback(this);
                if (drawable.isStateful()) {
                    drawable.setState(getDrawableState());
                }
                if (this.s == 119) {
                    drawable.getPadding(new Rect());
                }
            } else {
                setWillNotDraw(true);
            }
            requestLayout();
            invalidate();
        }
    }

    @Override // android.view.View
    public final void setForegroundGravity(int i) {
        if (this.s != i) {
            if ((8388615 & i) == 0) {
                i |= 8388611;
            }
            if ((i & 112) == 0) {
                i |= 48;
            }
            this.s = i;
            if (i == 119 && this.p != null) {
                this.p.getPadding(new Rect());
            }
            requestLayout();
        }
    }

    @Override // android.view.View
    public final boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.p;
    }
}
