package com.google.android.material.snackbar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import java.util.WeakHashMap;
import moe.shizuku.privileged.api.R;
import rikka.shizuku.AbstractC0744xh;
import rikka.shizuku.AbstractC0750xn;
import rikka.shizuku.AbstractC0755xs;
import rikka.shizuku.AbstractC0817zs;
import rikka.shizuku.C0317jn;
import rikka.shizuku.Hs;
import rikka.shizuku.Lk;
import rikka.shizuku.V4;
import rikka.shizuku.W4;
import rikka.shizuku.X8;
import rikka.shizuku.Xh;

/* JADX INFO: loaded from: classes.dex */
public final class Snackbar$SnackbarLayout extends FrameLayout {
    public static final V4 e = new V4();
    public final C0317jn a;
    public final int b;
    public ColorStateList c;
    public PorterDuff.Mode d;

    public Snackbar$SnackbarLayout(Context context, AttributeSet attributeSet) {
        Drawable drawable;
        super(X8.c0(context, attributeSet, 0, 0), attributeSet);
        Context context2 = getContext();
        TypedArray typedArrayObtainStyledAttributes = context2.obtainStyledAttributes(attributeSet, Lk.D);
        if (typedArrayObtainStyledAttributes.hasValue(6)) {
            float dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(6, 0);
            WeakHashMap weakHashMap = Hs.a;
            AbstractC0817zs.k(this, dimensionPixelSize);
        }
        typedArrayObtainStyledAttributes.getInt(2, 0);
        if (typedArrayObtainStyledAttributes.hasValue(8) || typedArrayObtainStyledAttributes.hasValue(9)) {
            this.a = C0317jn.c(context2, attributeSet, 0, 0).a();
        }
        float f = typedArrayObtainStyledAttributes.getFloat(3, 1.0f);
        setBackgroundTintList(X8.w(context2, typedArrayObtainStyledAttributes, 4));
        setBackgroundTintMode(AbstractC0750xn.q(typedArrayObtainStyledAttributes.getInt(5, -1), PorterDuff.Mode.SRC_IN));
        typedArrayObtainStyledAttributes.getFloat(1, 1.0f);
        this.b = typedArrayObtainStyledAttributes.getDimensionPixelSize(0, -1);
        typedArrayObtainStyledAttributes.getDimensionPixelSize(7, -1);
        typedArrayObtainStyledAttributes.recycle();
        setOnTouchListener(e);
        setFocusable(true);
        if (getBackground() == null) {
            int iH = AbstractC0744xh.H(AbstractC0744xh.s(this, R.attr._0_resource_name_obfuscated_res_0x7f04011f), AbstractC0744xh.s(this, R.attr._0_resource_name_obfuscated_res_0x7f040108), f);
            C0317jn c0317jn = this.a;
            if (c0317jn != null) {
                int i = W4.a;
                Xh xh = new Xh(c0317jn);
                xh.l(ColorStateList.valueOf(iH));
                drawable = xh;
            } else {
                Resources resources = getResources();
                int i2 = W4.a;
                float dimension = resources.getDimension(R.dimen._0_resource_name_obfuscated_res_0x7f07030d);
                GradientDrawable gradientDrawable = new GradientDrawable();
                gradientDrawable.setShape(0);
                gradientDrawable.setCornerRadius(dimension);
                gradientDrawable.setColor(iH);
                drawable = gradientDrawable;
            }
            ColorStateList colorStateList = this.c;
            if (colorStateList != null) {
                drawable.setTintList(colorStateList);
            }
            WeakHashMap weakHashMap2 = Hs.a;
            setBackgroundDrawable(drawable);
        }
    }

    public final void a(int i, int i2) {
        super.onMeasure(i, i2);
        int i3 = this.b;
        if (i3 <= 0 || getMeasuredWidth() <= i3) {
            return;
        }
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(i3, 1073741824), i2);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        WeakHashMap weakHashMap = Hs.a;
        AbstractC0755xs.c(this);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
    }

    @Override // android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        a(i, i2);
        int childCount = getChildCount();
        int measuredWidth = (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            if (childAt.getLayoutParams().width == -1) {
                childAt.measure(View.MeasureSpec.makeMeasureSpec(measuredWidth, 1073741824), View.MeasureSpec.makeMeasureSpec(childAt.getMeasuredHeight(), 1073741824));
            }
        }
    }

    @Override // android.view.View
    public final void setBackground(Drawable drawable) {
        setBackgroundDrawable(drawable);
    }

    @Override // android.view.View
    public final void setBackgroundDrawable(Drawable drawable) {
        if (drawable != null && this.c != null) {
            drawable = drawable.mutate();
            drawable.setTintList(this.c);
            drawable.setTintMode(this.d);
        }
        super.setBackgroundDrawable(drawable);
    }

    @Override // android.view.View
    public final void setBackgroundTintList(ColorStateList colorStateList) {
        this.c = colorStateList;
        if (getBackground() != null) {
            Drawable drawableMutate = getBackground().mutate();
            drawableMutate.setTintList(colorStateList);
            drawableMutate.setTintMode(this.d);
            if (drawableMutate != getBackground()) {
                super.setBackgroundDrawable(drawableMutate);
            }
        }
    }

    @Override // android.view.View
    public final void setBackgroundTintMode(PorterDuff.Mode mode) {
        this.d = mode;
        if (getBackground() != null) {
            Drawable drawableMutate = getBackground().mutate();
            drawableMutate.setTintMode(mode);
            if (drawableMutate != getBackground()) {
                super.setBackgroundDrawable(drawableMutate);
            }
        }
    }

    @Override // android.view.View
    public final void setLayoutParams(ViewGroup.LayoutParams layoutParams) {
        super.setLayoutParams(layoutParams);
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
            new Rect(marginLayoutParams.leftMargin, marginLayoutParams.topMargin, marginLayoutParams.rightMargin, marginLayoutParams.bottomMargin);
        }
    }

    @Override // android.view.View
    public final void setOnClickListener(View.OnClickListener onClickListener) {
        setOnTouchListener(onClickListener != null ? null : e);
        super.setOnClickListener(onClickListener);
    }
}
