package com.google.android.material.button;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Parcelable;
import android.text.Layout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.CompoundButton;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.WeakHashMap;
import rikka.shizuku.AbstractC0133dm;
import rikka.shizuku.AbstractC0599sr;
import rikka.shizuku.AbstractC0744xh;
import rikka.shizuku.AbstractC0750xn;
import rikka.shizuku.C0317jn;
import rikka.shizuku.C0420n2;
import rikka.shizuku.Fh;
import rikka.shizuku.G;
import rikka.shizuku.Gh;
import rikka.shizuku.Hs;
import rikka.shizuku.InterfaceC0657un;
import rikka.shizuku.Lk;
import rikka.shizuku.T0;
import rikka.shizuku.Tp;
import rikka.shizuku.Wh;
import rikka.shizuku.X8;
import rikka.shizuku.Xh;
import rikka.shizuku.Ys;

/* JADX INFO: loaded from: classes.dex */
public class MaterialButton extends C0420n2 implements Checkable, InterfaceC0657un {
    public static final int[] r = {R.attr.state_checkable};
    public static final int[] s = {R.attr.state_checked};
    public final Gh d;
    public final LinkedHashSet e;
    public Ys f;
    public final PorterDuff.Mode g;
    public final ColorStateList h;
    public Drawable i;
    public String j;
    public final int k;
    public int l;
    public int m;
    public final int n;
    public boolean o;
    public boolean p;
    public final int q;

    public MaterialButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    @Override // rikka.shizuku.InterfaceC0657un
    public final void a(C0317jn c0317jn) {
        if (!b()) {
            throw new IllegalStateException("Attempted to set ShapeAppearanceModel on a MaterialButton which has an overwritten background.");
        }
        this.d.c(c0317jn);
    }

    public final boolean b() {
        Gh gh = this.d;
        return (gh == null || gh.n) ? false : true;
    }

    public final void c() {
        int i = this.q;
        boolean z = true;
        if (i != 1 && i != 2) {
            z = false;
        }
        if (z) {
            setCompoundDrawablesRelative(this.i, null, null, null);
            return;
        }
        if (i == 3 || i == 4) {
            setCompoundDrawablesRelative(null, null, this.i, null);
        } else if (i == 16 || i == 32) {
            setCompoundDrawablesRelative(null, this.i, null, null);
        }
    }

    public final void d(ColorStateList colorStateList) {
        if (b()) {
            Gh gh = this.d;
            if (gh.i != colorStateList) {
                gh.i = colorStateList;
                if (gh.b(false) != null) {
                    gh.b(false).setTintList(gh.i);
                    return;
                }
                return;
            }
            return;
        }
        T0 t0 = this.a;
        if (t0 != null) {
            if (((Tp) t0.e) == null) {
                t0.e = new Tp();
            }
            Tp tp = (Tp) t0.e;
            tp.a = colorStateList;
            tp.d = true;
            t0.a();
        }
    }

    public final void e(PorterDuff.Mode mode) {
        if (b()) {
            Gh gh = this.d;
            if (gh.h != mode) {
                gh.h = mode;
                if (gh.b(false) == null || gh.h == null) {
                    return;
                }
                gh.b(false).setTintMode(gh.h);
                return;
            }
            return;
        }
        T0 t0 = this.a;
        if (t0 != null) {
            if (((Tp) t0.e) == null) {
                t0.e = new Tp();
            }
            Tp tp = (Tp) t0.e;
            tp.b = mode;
            tp.c = true;
            t0.a();
        }
    }

    public final void f(boolean z) {
        Drawable drawable = this.i;
        if (drawable != null) {
            Drawable drawableMutate = drawable.mutate();
            this.i = drawableMutate;
            drawableMutate.setTintList(this.h);
            PorterDuff.Mode mode = this.g;
            if (mode != null) {
                this.i.setTintMode(mode);
            }
            int intrinsicHeight = this.k;
            int intrinsicWidth = intrinsicHeight != 0 ? intrinsicHeight : this.i.getIntrinsicWidth();
            if (intrinsicHeight == 0) {
                intrinsicHeight = this.i.getIntrinsicHeight();
            }
            Drawable drawable2 = this.i;
            int i = this.l;
            int i2 = this.m;
            drawable2.setBounds(i, i2, intrinsicWidth + i, intrinsicHeight + i2);
            this.i.setVisible(true, z);
        }
        if (z) {
            c();
            return;
        }
        Drawable[] compoundDrawablesRelative = getCompoundDrawablesRelative();
        Drawable drawable3 = compoundDrawablesRelative[0];
        Drawable drawable4 = compoundDrawablesRelative[1];
        Drawable drawable5 = compoundDrawablesRelative[2];
        int i3 = this.q;
        if (((i3 == 1 || i3 == 2) && drawable3 != this.i) || (((i3 == 3 || i3 == 4) && drawable5 != this.i) || ((i3 == 16 || i3 == 32) && drawable4 != this.i))) {
            c();
        }
    }

    public final void g(int i, int i2) {
        Layout.Alignment alignment;
        int iMin;
        if (this.i == null || getLayout() == null) {
            return;
        }
        int i3 = this.q;
        boolean z = i3 == 1 || i3 == 2;
        int i4 = this.n;
        int intrinsicWidth = this.k;
        if (!z && i3 != 3 && i3 != 4) {
            if (i3 == 16 || i3 == 32) {
                this.l = 0;
                if (i3 == 16) {
                    this.m = 0;
                    f(false);
                    return;
                }
                if (intrinsicWidth == 0) {
                    intrinsicWidth = this.i.getIntrinsicHeight();
                }
                if (getLineCount() > 1) {
                    iMin = getLayout().getHeight();
                } else {
                    TextPaint paint = getPaint();
                    String string = getText().toString();
                    if (getTransformationMethod() != null) {
                        string = getTransformationMethod().getTransformation(string, this).toString();
                    }
                    Rect rect = new Rect();
                    paint.getTextBounds(string, 0, string.length(), rect);
                    iMin = Math.min(rect.height(), getLayout().getHeight());
                }
                int iMax = Math.max(0, (((((i2 - iMin) - getPaddingTop()) - intrinsicWidth) - i4) - getPaddingBottom()) / 2);
                if (this.m != iMax) {
                    this.m = iMax;
                    f(false);
                    return;
                }
                return;
            }
            return;
        }
        this.m = 0;
        int textAlignment = getTextAlignment();
        if (textAlignment == 1) {
            int gravity = getGravity() & 8388615;
            alignment = gravity != 1 ? (gravity == 5 || gravity == 8388613) ? Layout.Alignment.ALIGN_OPPOSITE : Layout.Alignment.ALIGN_NORMAL : Layout.Alignment.ALIGN_CENTER;
        } else if (textAlignment == 6 || textAlignment == 3) {
            alignment = Layout.Alignment.ALIGN_OPPOSITE;
        } else {
            alignment = textAlignment != 4 ? Layout.Alignment.ALIGN_NORMAL : Layout.Alignment.ALIGN_CENTER;
        }
        if (i3 == 1 || i3 == 3 || ((i3 == 2 && alignment == Layout.Alignment.ALIGN_NORMAL) || (i3 == 4 && alignment == Layout.Alignment.ALIGN_OPPOSITE))) {
            this.l = 0;
            f(false);
            return;
        }
        if (intrinsicWidth == 0) {
            intrinsicWidth = this.i.getIntrinsicWidth();
        }
        int lineCount = getLineCount();
        float fMax = 0.0f;
        for (int i5 = 0; i5 < lineCount; i5++) {
            fMax = Math.max(fMax, getLayout().getLineWidth(i5));
        }
        int iCeil = i - ((int) Math.ceil(fMax));
        WeakHashMap weakHashMap = Hs.a;
        int paddingEnd = (((iCeil - getPaddingEnd()) - intrinsicWidth) - i4) - getPaddingStart();
        if (alignment == Layout.Alignment.ALIGN_CENTER) {
            paddingEnd /= 2;
        }
        if ((getLayoutDirection() == 1) != (i3 == 4)) {
            paddingEnd = -paddingEnd;
        }
        if (this.l != paddingEnd) {
            this.l = paddingEnd;
            f(false);
        }
    }

    @Override // android.view.View
    public final ColorStateList getBackgroundTintList() {
        Tp tp;
        if (b()) {
            return this.d.i;
        }
        T0 t0 = this.a;
        if (t0 == null || (tp = (Tp) t0.e) == null) {
            return null;
        }
        return tp.a;
    }

    @Override // android.view.View
    public final PorterDuff.Mode getBackgroundTintMode() {
        Tp tp;
        if (b()) {
            return this.d.h;
        }
        T0 t0 = this.a;
        if (t0 == null || (tp = (Tp) t0.e) == null) {
            return null;
        }
        return tp.b;
    }

    @Override // android.widget.Checkable
    public final boolean isChecked() {
        return this.o;
    }

    @Override // android.widget.TextView, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (b()) {
            AbstractC0599sr.H(this, this.d.b(false));
        }
    }

    @Override // android.widget.TextView, android.view.View
    public final int[] onCreateDrawableState(int i) {
        int[] iArrOnCreateDrawableState = super.onCreateDrawableState(i + 2);
        Gh gh = this.d;
        if (gh != null && gh.o) {
            View.mergeDrawableStates(iArrOnCreateDrawableState, r);
        }
        if (this.o) {
            View.mergeDrawableStates(iArrOnCreateDrawableState, s);
        }
        return iArrOnCreateDrawableState;
    }

    @Override // rikka.shizuku.C0420n2, android.view.View
    public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        String name;
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        if (TextUtils.isEmpty(this.j)) {
            Gh gh = this.d;
            name = ((gh == null || !gh.o) ? Button.class : CompoundButton.class).getName();
        } else {
            name = this.j;
        }
        accessibilityEvent.setClassName(name);
        accessibilityEvent.setChecked(this.o);
    }

    @Override // rikka.shizuku.C0420n2, android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        String name;
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        boolean zIsEmpty = TextUtils.isEmpty(this.j);
        Gh gh = this.d;
        if (zIsEmpty) {
            name = ((gh == null || !gh.o) ? Button.class : CompoundButton.class).getName();
        } else {
            name = this.j;
        }
        accessibilityNodeInfo.setClassName(name);
        accessibilityNodeInfo.setCheckable(gh != null && gh.o);
        accessibilityNodeInfo.setChecked(this.o);
        accessibilityNodeInfo.setClickable(isClickable());
    }

    @Override // rikka.shizuku.C0420n2, android.widget.TextView, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        g(getMeasuredWidth(), getMeasuredHeight());
    }

    @Override // android.widget.TextView, android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof Fh)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        Fh fh = (Fh) parcelable;
        super.onRestoreInstanceState(fh.a);
        setChecked(fh.c);
    }

    @Override // android.widget.TextView, android.view.View
    public final Parcelable onSaveInstanceState() {
        Fh fh = new Fh(super.onSaveInstanceState());
        fh.c = this.o;
        return fh;
    }

    @Override // rikka.shizuku.C0420n2, android.widget.TextView
    public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        super.onTextChanged(charSequence, i, i2, i3);
        g(getMeasuredWidth(), getMeasuredHeight());
    }

    @Override // android.view.View
    public final boolean performClick() {
        if (this.d.p) {
            toggle();
        }
        return super.performClick();
    }

    @Override // android.view.View
    public final void refreshDrawableState() {
        super.refreshDrawableState();
        if (this.i != null) {
            if (this.i.setState(getDrawableState())) {
                invalidate();
            }
        }
    }

    @Override // android.view.View
    public final void setBackground(Drawable drawable) {
        setBackgroundDrawable(drawable);
    }

    @Override // android.view.View
    public final void setBackgroundColor(int i) {
        if (!b()) {
            super.setBackgroundColor(i);
            return;
        }
        Gh gh = this.d;
        if (gh.b(false) != null) {
            gh.b(false).setTint(i);
        }
    }

    @Override // rikka.shizuku.C0420n2, android.view.View
    public final void setBackgroundDrawable(Drawable drawable) {
        if (!b()) {
            super.setBackgroundDrawable(drawable);
            return;
        }
        if (drawable == getBackground()) {
            getBackground().setState(drawable.getState());
            return;
        }
        Log.w("MaterialButton", "MaterialButton manages its own background to control elevation, shape, color and states. Consider using backgroundTint, shapeAppearance and other attributes where available. A custom background will ignore these attributes and you should consider handling interaction states such as pressed, focused and disabled");
        Gh gh = this.d;
        gh.n = true;
        ColorStateList colorStateList = gh.i;
        MaterialButton materialButton = gh.a;
        materialButton.d(colorStateList);
        materialButton.e(gh.h);
        super.setBackgroundDrawable(drawable);
    }

    @Override // rikka.shizuku.C0420n2, android.view.View
    public final void setBackgroundResource(int i) {
        setBackgroundDrawable(i != 0 ? AbstractC0744xh.v(getContext(), i) : null);
    }

    @Override // android.view.View
    public final void setBackgroundTintList(ColorStateList colorStateList) {
        d(colorStateList);
    }

    @Override // android.view.View
    public final void setBackgroundTintMode(PorterDuff.Mode mode) {
        e(mode);
    }

    @Override // android.widget.Checkable
    public final void setChecked(boolean z) {
        Gh gh = this.d;
        if (gh == null || !gh.o || !isEnabled() || this.o == z) {
            return;
        }
        this.o = z;
        refreshDrawableState();
        if (getParent() instanceof MaterialButtonToggleGroup) {
            MaterialButtonToggleGroup materialButtonToggleGroup = (MaterialButtonToggleGroup) getParent();
            boolean z2 = this.o;
            if (!materialButtonToggleGroup.f) {
                materialButtonToggleGroup.b(getId(), z2);
            }
        }
        if (this.p) {
            return;
        }
        this.p = true;
        Iterator it = this.e.iterator();
        if (it.hasNext()) {
            it.next().getClass();
            throw new ClassCastException();
        }
        this.p = false;
    }

    @Override // android.view.View
    public final void setElevation(float f) {
        super.setElevation(f);
        if (b()) {
            this.d.b(false).k(f);
        }
    }

    @Override // android.view.View
    public final void setPressed(boolean z) {
        Ys ys = this.f;
        if (ys != null) {
            ((MaterialButtonToggleGroup) ys.b).invalidate();
        }
        super.setPressed(z);
    }

    @Override // android.view.View
    public final void setTextAlignment(int i) {
        super.setTextAlignment(i);
        g(getMeasuredWidth(), getMeasuredHeight());
    }

    @Override // android.widget.Checkable
    public final void toggle() {
        setChecked(!this.o);
    }

    public MaterialButton(Context context, AttributeSet attributeSet, int i) {
        boolean z;
        boolean z2;
        super(X8.c0(context, attributeSet, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f0402f1, moe.shizuku.privileged.api.R.style._0_resource_name_obfuscated_res_0x7f120465), attributeSet, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f0402f1);
        this.e = new LinkedHashSet();
        this.o = false;
        this.p = false;
        Context context2 = getContext();
        TypedArray typedArrayJ = X8.J(context2, attributeSet, Lk.n, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f0402f1, moe.shizuku.privileged.api.R.style._0_resource_name_obfuscated_res_0x7f120465, new int[0]);
        int dimensionPixelSize = typedArrayJ.getDimensionPixelSize(12, 0);
        this.n = dimensionPixelSize;
        int i2 = typedArrayJ.getInt(15, -1);
        PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
        this.g = AbstractC0750xn.q(i2, mode);
        this.h = X8.w(getContext(), typedArrayJ, 14);
        this.i = X8.z(getContext(), typedArrayJ, 10);
        this.q = typedArrayJ.getInteger(11, 1);
        this.k = typedArrayJ.getDimensionPixelSize(13, 0);
        Gh gh = new Gh(this, C0317jn.c(context2, attributeSet, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f0402f1, moe.shizuku.privileged.api.R.style._0_resource_name_obfuscated_res_0x7f120465).a());
        this.d = gh;
        gh.c = typedArrayJ.getDimensionPixelOffset(1, 0);
        gh.d = typedArrayJ.getDimensionPixelOffset(2, 0);
        gh.e = typedArrayJ.getDimensionPixelOffset(3, 0);
        gh.f = typedArrayJ.getDimensionPixelOffset(4, 0);
        if (typedArrayJ.hasValue(8)) {
            float dimensionPixelSize2 = typedArrayJ.getDimensionPixelSize(8, -1);
            C0317jn c0317jnG = gh.b.g();
            c0317jnG.e = new G(dimensionPixelSize2);
            c0317jnG.f = new G(dimensionPixelSize2);
            c0317jnG.g = new G(dimensionPixelSize2);
            c0317jnG.h = new G(dimensionPixelSize2);
            gh.c(c0317jnG.a());
        }
        gh.g = typedArrayJ.getDimensionPixelSize(20, 0);
        gh.h = AbstractC0750xn.q(typedArrayJ.getInt(7, -1), mode);
        gh.i = X8.w(getContext(), typedArrayJ, 6);
        gh.j = X8.w(getContext(), typedArrayJ, 19);
        gh.k = X8.w(getContext(), typedArrayJ, 16);
        gh.o = typedArrayJ.getBoolean(5, false);
        gh.r = typedArrayJ.getDimensionPixelSize(9, 0);
        gh.p = typedArrayJ.getBoolean(21, true);
        WeakHashMap weakHashMap = Hs.a;
        int paddingStart = getPaddingStart();
        int paddingTop = getPaddingTop();
        int paddingEnd = getPaddingEnd();
        int paddingBottom = getPaddingBottom();
        if (typedArrayJ.hasValue(0)) {
            gh.n = true;
            d(gh.i);
            e(gh.h);
            z2 = false;
            z = true;
        } else {
            Xh xh = new Xh(gh.b);
            xh.j(getContext());
            xh.setTintList(gh.i);
            PorterDuff.Mode mode2 = gh.h;
            if (mode2 != null) {
                xh.setTintMode(mode2);
            }
            float f = gh.g;
            ColorStateList colorStateList = gh.j;
            xh.a.j = f;
            xh.invalidateSelf();
            Wh wh = xh.a;
            if (wh.d != colorStateList) {
                wh.d = colorStateList;
                xh.onStateChange(xh.getState());
            }
            Xh xh2 = new Xh(gh.b);
            xh2.setTint(0);
            float f2 = gh.g;
            int iS = gh.m ? AbstractC0744xh.s(this, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f04011f) : 0;
            z = true;
            xh2.a.j = f2;
            xh2.invalidateSelf();
            ColorStateList colorStateListValueOf = ColorStateList.valueOf(iS);
            Wh wh2 = xh2.a;
            if (wh2.d != colorStateListValueOf) {
                wh2.d = colorStateListValueOf;
                xh2.onStateChange(xh2.getState());
            }
            Xh xh3 = new Xh(gh.b);
            gh.l = xh3;
            xh3.setTint(-1);
            RippleDrawable rippleDrawable = new RippleDrawable(AbstractC0133dm.a(gh.k), new InsetDrawable((Drawable) new LayerDrawable(new Drawable[]{xh2, xh}), gh.c, gh.e, gh.d, gh.f), gh.l);
            gh.q = rippleDrawable;
            super.setBackgroundDrawable(rippleDrawable);
            z2 = false;
            Xh xhB = gh.b(false);
            if (xhB != null) {
                xhB.k(gh.r);
                xhB.setState(getDrawableState());
            }
        }
        setPaddingRelative(paddingStart + gh.c, paddingTop + gh.e, paddingEnd + gh.d, paddingBottom + gh.f);
        typedArrayJ.recycle();
        setCompoundDrawablePadding(dimensionPixelSize);
        f(this.i != null ? z : z2);
    }
}
