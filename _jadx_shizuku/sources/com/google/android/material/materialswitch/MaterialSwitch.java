package com.google.android.material.materialswitch;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.view.View;
import androidx.appcompat.widget.SwitchCompat;
import moe.shizuku.privileged.api.R;
import rikka.shizuku.AbstractC0046as;
import rikka.shizuku.AbstractC0750xn;
import rikka.shizuku.E6;
import rikka.shizuku.K3;
import rikka.shizuku.Lk;
import rikka.shizuku.X8;

/* JADX INFO: loaded from: classes.dex */
public class MaterialSwitch extends SwitchCompat {
    public static final int[] g0 = {R.attr._0_resource_name_obfuscated_res_0x7f040466};
    public final Drawable T;
    public final Drawable U;
    public final Drawable V;
    public final Drawable W;
    public final ColorStateList a0;
    public final ColorStateList b0;
    public final ColorStateList c0;
    public final ColorStateList d0;
    public int[] e0;
    public int[] f0;

    public MaterialSwitch(Context context, AttributeSet attributeSet) {
        super(X8.c0(context, attributeSet, R.attr._0_resource_name_obfuscated_res_0x7f040315, R.style._0_resource_name_obfuscated_res_0x7f1203e3), attributeSet, R.attr._0_resource_name_obfuscated_res_0x7f040315);
        Context context2 = getContext();
        this.T = this.a;
        ColorStateList colorStateList = this.b;
        this.a0 = colorStateList;
        this.b = null;
        this.d = true;
        a();
        this.V = this.f;
        ColorStateList colorStateList2 = this.g;
        this.c0 = colorStateList2;
        this.g = null;
        this.i = true;
        b();
        int[] iArr = Lk.v;
        X8.j(context2, attributeSet, R.attr._0_resource_name_obfuscated_res_0x7f040315, R.style._0_resource_name_obfuscated_res_0x7f1203e3);
        X8.p(context2, attributeSet, iArr, R.attr._0_resource_name_obfuscated_res_0x7f040315, R.style._0_resource_name_obfuscated_res_0x7f1203e3, new int[0]);
        TypedArray typedArrayObtainStyledAttributes = context2.obtainStyledAttributes(attributeSet, iArr, R.attr._0_resource_name_obfuscated_res_0x7f040315, R.style._0_resource_name_obfuscated_res_0x7f1203e3);
        K3 k3 = new K3(context2, typedArrayObtainStyledAttributes);
        this.U = k3.i(0);
        int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(1, -1);
        ColorStateList colorStateListH = k3.h(2);
        this.b0 = colorStateListH;
        int i = typedArrayObtainStyledAttributes.getInt(3, -1);
        PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
        PorterDuff.Mode modeQ = AbstractC0750xn.q(i, mode);
        this.W = k3.i(4);
        ColorStateList colorStateListH2 = k3.h(5);
        this.d0 = colorStateListH2;
        PorterDuff.Mode modeQ2 = AbstractC0750xn.q(typedArrayObtainStyledAttributes.getInt(6, -1), mode);
        k3.o();
        this.H = false;
        invalidate();
        this.T = AbstractC0046as.p(this.T, colorStateList, this.c);
        this.U = AbstractC0046as.p(this.U, colorStateListH, modeQ);
        j();
        Drawable drawableL = AbstractC0046as.l(this.T, this.U, dimensionPixelSize, dimensionPixelSize);
        Drawable drawable = this.a;
        if (drawable != null) {
            drawable.setCallback(null);
        }
        this.a = drawableL;
        if (drawableL != null) {
            drawableL.setCallback(this);
        }
        requestLayout();
        refreshDrawableState();
        this.V = AbstractC0046as.p(this.V, colorStateList2, this.h);
        this.W = AbstractC0046as.p(this.W, colorStateListH2, modeQ2);
        j();
        Drawable layerDrawable = this.V;
        if (layerDrawable != null && this.W != null) {
            layerDrawable = new LayerDrawable(new Drawable[]{this.V, this.W});
        } else if (layerDrawable == null) {
            layerDrawable = this.W;
        }
        if (layerDrawable != null) {
            this.l = layerDrawable.getIntrinsicWidth();
            requestLayout();
        }
        Drawable drawable2 = this.f;
        if (drawable2 != null) {
            drawable2.setCallback(null);
        }
        this.f = layerDrawable;
        if (layerDrawable != null) {
            layerDrawable.setCallback(this);
        }
        requestLayout();
    }

    public static void i(Drawable drawable, ColorStateList colorStateList, int[] iArr, int[] iArr2, float f) {
        if (drawable == null || colorStateList == null) {
            return;
        }
        drawable.setTint(E6.b(colorStateList.getColorForState(iArr, 0), colorStateList.getColorForState(iArr2, 0), f));
    }

    @Override // android.view.View
    public final void invalidate() {
        j();
        super.invalidate();
    }

    public final void j() {
        ColorStateList colorStateList = this.d0;
        ColorStateList colorStateList2 = this.c0;
        ColorStateList colorStateList3 = this.b0;
        ColorStateList colorStateList4 = this.a0;
        if (colorStateList4 == null && colorStateList3 == null && colorStateList2 == null && colorStateList == null) {
            return;
        }
        float f = this.z;
        if (colorStateList4 != null) {
            i(this.T, colorStateList4, this.e0, this.f0, f);
        }
        if (colorStateList3 != null) {
            i(this.U, colorStateList3, this.e0, this.f0, f);
        }
        if (colorStateList2 != null) {
            i(this.V, colorStateList2, this.e0, this.f0, f);
        }
        if (colorStateList != null) {
            i(this.W, colorStateList, this.e0, this.f0, f);
        }
    }

    @Override // androidx.appcompat.widget.SwitchCompat, android.widget.CompoundButton, android.widget.TextView, android.view.View
    public final int[] onCreateDrawableState(int i) {
        int[] iArrOnCreateDrawableState = super.onCreateDrawableState(i + 1);
        if (this.U != null) {
            View.mergeDrawableStates(iArrOnCreateDrawableState, g0);
        }
        int[] iArr = new int[iArrOnCreateDrawableState.length];
        int i2 = 0;
        for (int i3 : iArrOnCreateDrawableState) {
            if (i3 != 16842912) {
                iArr[i2] = i3;
                i2++;
            }
        }
        this.e0 = iArr;
        this.f0 = AbstractC0046as.t(iArrOnCreateDrawableState);
        return iArrOnCreateDrawableState;
    }
}
