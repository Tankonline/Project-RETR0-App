package rikka.shizuku;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.AnimatedStateListDrawable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.autofill.AutofillManager;
import android.widget.CompoundButton;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import moe.shizuku.privileged.api.R;

/* JADX INFO: loaded from: classes.dex */
public final class Th extends C0482p2 {
    public final LinkedHashSet e;
    public ColorStateList f;
    public boolean g;
    public final boolean h;
    public final boolean i;
    public final CharSequence j;
    public Drawable k;
    public Drawable l;
    public boolean m;
    public ColorStateList n;
    public final ColorStateList o;
    public final PorterDuff.Mode p;
    public int q;
    public int[] r;
    public boolean s;
    public CharSequence t;
    public CompoundButton.OnCheckedChangeListener u;
    public final U1 v;
    public final Rh w;
    public static final int[] x = {R.attr._0_resource_name_obfuscated_res_0x7f040462};
    public static final int[] y = {R.attr._0_resource_name_obfuscated_res_0x7f040461};
    public static final int[][] z = {new int[]{android.R.attr.state_enabled, R.attr._0_resource_name_obfuscated_res_0x7f040461}, new int[]{android.R.attr.state_enabled, android.R.attr.state_checked}, new int[]{android.R.attr.state_enabled, -16842912}, new int[]{-16842910, android.R.attr.state_checked}, new int[]{-16842910, -16842912}};
    public static final int A = Resources.getSystem().getIdentifier("btn_check_material_anim", "drawable", "android");

    public Th(Context context, AttributeSet attributeSet) {
        super(X8.c0(context, attributeSet, R.attr._0_resource_name_obfuscated_res_0x7f0400b3, R.style._0_resource_name_obfuscated_res_0x7f12047d), attributeSet, R.attr._0_resource_name_obfuscated_res_0x7f0400b3);
        new LinkedHashSet();
        this.e = new LinkedHashSet();
        Context context2 = getContext();
        U1 u1 = new U1(context2);
        Resources resources = context2.getResources();
        Resources.Theme theme = context2.getTheme();
        ThreadLocal threadLocal = Xl.a;
        Drawable drawable = resources.getDrawable(R.drawable._0_resource_name_obfuscated_res_0x7f0800f3, theme);
        u1.a = drawable;
        drawable.setCallback(u1.f);
        new T1(u1.a.getConstantState());
        this.v = u1;
        this.w = new Rh(this);
        Context context3 = getContext();
        this.k = this.k;
        ColorStateList colorStateList = this.n;
        this.n = colorStateList == null ? super.getButtonTintList() != null ? super.getButtonTintList() : null : colorStateList;
        C0574s2 c0574s2 = this.a;
        if (c0574s2 != null) {
            c0574s2.c = true;
            c0574s2.a();
        }
        int[] iArr = Lk.s;
        X8.j(context3, attributeSet, R.attr._0_resource_name_obfuscated_res_0x7f0400b3, R.style._0_resource_name_obfuscated_res_0x7f12047d);
        X8.p(context3, attributeSet, iArr, R.attr._0_resource_name_obfuscated_res_0x7f0400b3, R.style._0_resource_name_obfuscated_res_0x7f12047d, new int[0]);
        TypedArray typedArrayObtainStyledAttributes = context3.obtainStyledAttributes(attributeSet, iArr, R.attr._0_resource_name_obfuscated_res_0x7f0400b3, R.style._0_resource_name_obfuscated_res_0x7f12047d);
        K3 k3 = new K3(context3, typedArrayObtainStyledAttributes);
        this.l = k3.i(2);
        if (this.k != null && AbstractC0046as.N(R.attr._0_resource_name_obfuscated_res_0x7f040258, context3, false)) {
            int resourceId = typedArrayObtainStyledAttributes.getResourceId(0, 0);
            int resourceId2 = typedArrayObtainStyledAttributes.getResourceId(1, 0);
            if (resourceId == A && resourceId2 == 0) {
                super.setButtonDrawable((Drawable) null);
                this.k = AbstractC0744xh.v(context3, R.drawable._0_resource_name_obfuscated_res_0x7f0800f2);
                this.m = true;
                if (this.l == null) {
                    this.l = AbstractC0744xh.v(context3, R.drawable._0_resource_name_obfuscated_res_0x7f0800f4);
                }
            }
        }
        this.o = X8.x(context3, k3, 3);
        this.p = AbstractC0750xn.q(typedArrayObtainStyledAttributes.getInt(4, -1), PorterDuff.Mode.SRC_IN);
        this.g = typedArrayObtainStyledAttributes.getBoolean(10, false);
        this.h = typedArrayObtainStyledAttributes.getBoolean(6, true);
        this.i = typedArrayObtainStyledAttributes.getBoolean(9, false);
        this.j = typedArrayObtainStyledAttributes.getText(8);
        if (typedArrayObtainStyledAttributes.hasValue(7)) {
            c(typedArrayObtainStyledAttributes.getInt(7, 0));
        }
        k3.o();
        b();
    }

    public final void b() {
        ColorStateList colorStateList;
        C0603t0 c0603t0;
        this.k = AbstractC0046as.p(this.k, this.n, getButtonTintMode());
        Drawable drawable = this.l;
        ColorStateList colorStateList2 = this.o;
        this.l = AbstractC0046as.p(drawable, colorStateList2, this.p);
        if (this.m) {
            U1 u1 = this.v;
            if (u1 != null) {
                Drawable drawable2 = u1.a;
                Rh rh = this.w;
                if (drawable2 != null) {
                    AnimatedVectorDrawable animatedVectorDrawable = (AnimatedVectorDrawable) drawable2;
                    if (rh.a == null) {
                        rh.a = new Q1(rh);
                    }
                    animatedVectorDrawable.unregisterAnimationCallback(rh.a);
                }
                ArrayList arrayList = u1.e;
                S1 s1 = u1.b;
                if (arrayList != null && rh != null) {
                    arrayList.remove(rh);
                    if (u1.e.size() == 0 && (c0603t0 = u1.d) != null) {
                        s1.b.removeListener(c0603t0);
                        u1.d = null;
                    }
                }
                Drawable drawable3 = u1.a;
                if (drawable3 != null) {
                    AnimatedVectorDrawable animatedVectorDrawable2 = (AnimatedVectorDrawable) drawable3;
                    if (rh.a == null) {
                        rh.a = new Q1(rh);
                    }
                    animatedVectorDrawable2.registerAnimationCallback(rh.a);
                } else if (rh != null) {
                    if (u1.e == null) {
                        u1.e = new ArrayList();
                    }
                    if (!u1.e.contains(rh)) {
                        u1.e.add(rh);
                        if (u1.d == null) {
                            u1.d = new C0603t0(1, u1);
                        }
                        s1.b.addListener(u1.d);
                    }
                }
            }
            Drawable drawable4 = this.k;
            if ((drawable4 instanceof AnimatedStateListDrawable) && u1 != null) {
                ((AnimatedStateListDrawable) drawable4).addTransition(R.id._0_resource_name_obfuscated_res_0x7f09006b, R.id._0_resource_name_obfuscated_res_0x7f0901f3, u1, false);
                ((AnimatedStateListDrawable) this.k).addTransition(R.id._0_resource_name_obfuscated_res_0x7f0900d3, R.id._0_resource_name_obfuscated_res_0x7f0901f3, u1, false);
            }
        }
        Drawable drawable5 = this.k;
        if (drawable5 != null && (colorStateList = this.n) != null) {
            drawable5.setTintList(colorStateList);
        }
        Drawable drawable6 = this.l;
        if (drawable6 != null && colorStateList2 != null) {
            drawable6.setTintList(colorStateList2);
        }
        super.setButtonDrawable(AbstractC0046as.l(this.k, this.l, -1, -1));
        refreshDrawableState();
    }

    public final void c(int i) {
        AutofillManager autofillManagerB;
        CompoundButton.OnCheckedChangeListener onCheckedChangeListener;
        if (this.q != i) {
            this.q = i;
            super.setChecked(i == 1);
            refreshDrawableState();
            d();
            if (this.s) {
                return;
            }
            this.s = true;
            LinkedHashSet linkedHashSet = this.e;
            if (linkedHashSet != null) {
                Iterator it = linkedHashSet.iterator();
                if (it.hasNext()) {
                    it.next().getClass();
                    throw new ClassCastException();
                }
            }
            if (this.q != 2 && (onCheckedChangeListener = this.u) != null) {
                onCheckedChangeListener.onCheckedChanged(this, isChecked());
            }
            if (Build.VERSION.SDK_INT >= 26 && (autofillManagerB = AbstractC0555re.b(getContext().getSystemService(AbstractC0555re.c()))) != null) {
                autofillManagerB.notifyValueChanged(this);
            }
            this.s = false;
        }
    }

    public final void d() {
        String string;
        if (Build.VERSION.SDK_INT < 30 || this.t != null) {
            return;
        }
        int i = this.q;
        if (i == 1) {
            string = getResources().getString(R.string._0_resource_name_obfuscated_res_0x7f1100bc);
        } else {
            string = i == 0 ? getResources().getString(R.string._0_resource_name_obfuscated_res_0x7f1100be) : getResources().getString(R.string._0_resource_name_obfuscated_res_0x7f1100bd);
        }
        super.setStateDescription(string);
    }

    @Override // android.widget.CompoundButton
    public final Drawable getButtonDrawable() {
        return this.k;
    }

    @Override // android.widget.CompoundButton
    public final ColorStateList getButtonTintList() {
        return this.n;
    }

    @Override // android.widget.CompoundButton, android.widget.Checkable
    public final boolean isChecked() {
        return this.q == 1;
    }

    @Override // android.widget.TextView, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.g && this.n == null && this.o == null) {
            this.g = true;
            if (this.f == null) {
                int iS = AbstractC0744xh.s(this, R.attr._0_resource_name_obfuscated_res_0x7f0400f4);
                int iS2 = AbstractC0744xh.s(this, R.attr._0_resource_name_obfuscated_res_0x7f0400f7);
                int iS3 = AbstractC0744xh.s(this, R.attr._0_resource_name_obfuscated_res_0x7f04011f);
                int iS4 = AbstractC0744xh.s(this, R.attr._0_resource_name_obfuscated_res_0x7f040108);
                this.f = new ColorStateList(z, new int[]{AbstractC0744xh.H(iS3, iS2, 1.0f), AbstractC0744xh.H(iS3, iS, 1.0f), AbstractC0744xh.H(iS3, iS4, 0.54f), AbstractC0744xh.H(iS3, iS4, 0.38f), AbstractC0744xh.H(iS3, iS4, 0.38f)});
            }
            setButtonTintList(this.f);
        }
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    public final int[] onCreateDrawableState(int i) {
        int[] iArrOnCreateDrawableState = super.onCreateDrawableState(i + 2);
        if (this.q == 2) {
            View.mergeDrawableStates(iArrOnCreateDrawableState, x);
        }
        if (this.i) {
            View.mergeDrawableStates(iArrOnCreateDrawableState, y);
        }
        this.r = AbstractC0046as.t(iArrOnCreateDrawableState);
        return iArrOnCreateDrawableState;
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    public final void onDraw(Canvas canvas) {
        Drawable buttonDrawable;
        if (!this.h || !TextUtils.isEmpty(getText()) || (buttonDrawable = getButtonDrawable()) == null) {
            super.onDraw(canvas);
            return;
        }
        int width = ((getWidth() - buttonDrawable.getIntrinsicWidth()) / 2) * (AbstractC0750xn.m(this) ? -1 : 1);
        int iSave = canvas.save();
        canvas.translate(width, 0.0f);
        super.onDraw(canvas);
        canvas.restoreToCount(iSave);
        if (getBackground() != null) {
            Rect bounds = buttonDrawable.getBounds();
            getBackground().setHotspotBounds(bounds.left + width, bounds.top, bounds.right + width, bounds.bottom);
        }
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        if (accessibilityNodeInfo != null && this.i) {
            accessibilityNodeInfo.setText(((Object) accessibilityNodeInfo.getText()) + ", " + ((Object) this.j));
        }
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof Sh)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        Sh sh = (Sh) parcelable;
        super.onRestoreInstanceState(sh.getSuperState());
        c(sh.a);
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    public final Parcelable onSaveInstanceState() {
        Sh sh = new Sh(super.onSaveInstanceState());
        sh.a = this.q;
        return sh;
    }

    @Override // rikka.shizuku.C0482p2, android.widget.CompoundButton
    public final void setButtonDrawable(int i) {
        setButtonDrawable(AbstractC0744xh.v(getContext(), i));
    }

    @Override // android.widget.CompoundButton
    public final void setButtonTintList(ColorStateList colorStateList) {
        if (this.n == colorStateList) {
            return;
        }
        this.n = colorStateList;
        b();
    }

    @Override // android.widget.CompoundButton
    public final void setButtonTintMode(PorterDuff.Mode mode) {
        C0574s2 c0574s2 = this.a;
        if (c0574s2 != null) {
            c0574s2.b = mode;
            c0574s2.d = true;
            c0574s2.a();
        }
        b();
    }

    @Override // android.widget.CompoundButton, android.widget.Checkable
    public final void setChecked(boolean z2) {
        c(z2 ? 1 : 0);
    }

    @Override // android.widget.CompoundButton
    public final void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        this.u = onCheckedChangeListener;
    }

    @Override // android.widget.CompoundButton, android.view.View
    public final void setStateDescription(CharSequence charSequence) {
        this.t = charSequence;
        if (charSequence == null) {
            d();
        } else {
            super.setStateDescription(charSequence);
        }
    }

    @Override // android.widget.CompoundButton, android.widget.Checkable
    public final void toggle() {
        c(!isChecked() ? 1 : 0);
    }

    @Override // rikka.shizuku.C0482p2, android.widget.CompoundButton
    public final void setButtonDrawable(Drawable drawable) {
        this.k = drawable;
        this.m = false;
        b();
    }
}
