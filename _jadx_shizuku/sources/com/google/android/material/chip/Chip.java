package com.google.android.material.chip;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.RippleDrawable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.TextView;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.WeakHashMap;
import rikka.shizuku.AbstractC0133dm;
import rikka.shizuku.AbstractC0599sr;
import rikka.shizuku.AbstractC0817zs;
import rikka.shizuku.C0024a6;
import rikka.shizuku.C0086c6;
import rikka.shizuku.C0317jn;
import rikka.shizuku.C0482p2;
import rikka.shizuku.Cp;
import rikka.shizuku.Fp;
import rikka.shizuku.G;
import rikka.shizuku.Gi;
import rikka.shizuku.Hs;
import rikka.shizuku.InterfaceC0055b6;
import rikka.shizuku.InterfaceC0657un;
import rikka.shizuku.Lk;
import rikka.shizuku.Wh;
import rikka.shizuku.X8;
import rikka.shizuku.Y5;
import rikka.shizuku.Z5;
import rikka.shizuku.ju;

/* JADX INFO: loaded from: classes.dex */
public class Chip extends C0482p2 implements InterfaceC0055b6, InterfaceC0657un, Checkable {
    public static final Rect t = new Rect();
    public static final int[] u = {R.attr.state_selected};
    public static final int[] v = {R.attr.state_checkable};
    public final C0086c6 e;
    public InsetDrawable f;
    public RippleDrawable g;
    public CompoundButton.OnCheckedChangeListener h;
    public boolean i;
    public boolean j;
    public boolean k;
    public boolean l;
    public final boolean m;
    public int n;
    public int o;
    public String p;
    public final Rect q;
    public final RectF r;
    public final Y5 s;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r14v15 */
    /* JADX WARN: Type inference failed for: r14v6 */
    /* JADX WARN: Type inference failed for: r14v7, types: [android.graphics.drawable.Drawable] */
    /* JADX WARN: Type inference failed for: r15v18 */
    /* JADX WARN: Type inference failed for: r15v4 */
    /* JADX WARN: Type inference failed for: r15v5, types: [android.graphics.drawable.Drawable] */
    public Chip(Context context, AttributeSet attributeSet) {
        ?? r15;
        ?? r14;
        Object obj;
        int resourceId;
        int resourceId2;
        ColorStateList colorStateListW;
        Drawable drawable;
        int resourceId3;
        super(X8.c0(context, attributeSet, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f0400d1, moe.shizuku.privileged.api.R.style._0_resource_name_obfuscated_res_0x7f120473), attributeSet, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f0400d1);
        int i = 0;
        this.q = new Rect();
        this.r = new RectF();
        this.s = new Y5(i, this);
        Context context2 = getContext();
        if (attributeSet != null) {
            if (attributeSet.getAttributeValue("http://schemas.android.com/apk/res/android", "background") != null) {
                Log.w("Chip", "Do not set the background; Chip manages its own background drawable.");
            }
            if (attributeSet.getAttributeValue("http://schemas.android.com/apk/res/android", "drawableLeft") != null) {
                throw new UnsupportedOperationException("Please set left drawable using R.attr#chipIcon.");
            }
            if (attributeSet.getAttributeValue("http://schemas.android.com/apk/res/android", "drawableStart") != null) {
                throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
            }
            if (attributeSet.getAttributeValue("http://schemas.android.com/apk/res/android", "drawableEnd") != null) {
                throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
            }
            if (attributeSet.getAttributeValue("http://schemas.android.com/apk/res/android", "drawableRight") != null) {
                throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
            }
            if (!attributeSet.getAttributeBooleanValue("http://schemas.android.com/apk/res/android", "singleLine", true) || attributeSet.getAttributeIntValue("http://schemas.android.com/apk/res/android", "lines", 1) != 1 || attributeSet.getAttributeIntValue("http://schemas.android.com/apk/res/android", "minLines", 1) != 1 || attributeSet.getAttributeIntValue("http://schemas.android.com/apk/res/android", "maxLines", 1) != 1) {
                throw new UnsupportedOperationException("Chip does not support multi-line text");
            }
            if (attributeSet.getAttributeIntValue("http://schemas.android.com/apk/res/android", "gravity", 8388627) != 8388627) {
                Log.w("Chip", "Chip text must be vertically center and start aligned");
            }
        }
        C0086c6 c0086c6 = new C0086c6(context2, attributeSet);
        int[] iArr = Lk.f;
        TypedArray typedArrayJ = X8.J(c0086c6.c0, attributeSet, iArr, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f0400d1, moe.shizuku.privileged.api.R.style._0_resource_name_obfuscated_res_0x7f120473, new int[0]);
        c0086c6.C0 = typedArrayJ.hasValue(37);
        Context context3 = c0086c6.c0;
        ColorStateList colorStateListW2 = X8.w(context3, typedArrayJ, 24);
        if (c0086c6.y != colorStateListW2) {
            c0086c6.y = colorStateListW2;
            c0086c6.onStateChange(c0086c6.getState());
        }
        ColorStateList colorStateListW3 = X8.w(context3, typedArrayJ, 11);
        if (c0086c6.z != colorStateListW3) {
            c0086c6.z = colorStateListW3;
            c0086c6.onStateChange(c0086c6.getState());
        }
        float dimension = typedArrayJ.getDimension(19, 0.0f);
        if (c0086c6.A != dimension) {
            c0086c6.A = dimension;
            c0086c6.invalidateSelf();
            c0086c6.x();
        }
        if (typedArrayJ.hasValue(12)) {
            float dimension2 = typedArrayJ.getDimension(12, 0.0f);
            if (c0086c6.B != dimension2) {
                c0086c6.B = dimension2;
                C0317jn c0317jnG = c0086c6.a.a.g();
                c0317jnG.e = new G(dimension2);
                c0317jnG.f = new G(dimension2);
                c0317jnG.g = new G(dimension2);
                c0317jnG.h = new G(dimension2);
                c0086c6.a(c0317jnG.a());
            }
        }
        ColorStateList colorStateListW4 = X8.w(context3, typedArrayJ, 22);
        if (c0086c6.C != colorStateListW4) {
            c0086c6.C = colorStateListW4;
            if (c0086c6.C0) {
                Wh wh = c0086c6.a;
                if (wh.d != colorStateListW4) {
                    wh.d = colorStateListW4;
                    c0086c6.onStateChange(c0086c6.getState());
                }
            }
            c0086c6.onStateChange(c0086c6.getState());
        }
        float dimension3 = typedArrayJ.getDimension(23, 0.0f);
        if (c0086c6.D != dimension3) {
            c0086c6.D = dimension3;
            c0086c6.d0.setStrokeWidth(dimension3);
            if (c0086c6.C0) {
                c0086c6.a.j = dimension3;
                c0086c6.invalidateSelf();
            }
            c0086c6.invalidateSelf();
        }
        ColorStateList colorStateListW5 = X8.w(context3, typedArrayJ, 36);
        if (c0086c6.E != colorStateListW5) {
            c0086c6.E = colorStateListW5;
            c0086c6.x0 = null;
            c0086c6.onStateChange(c0086c6.getState());
        }
        String text = typedArrayJ.getText(5);
        text = text == null ? "" : text;
        if (!TextUtils.equals(c0086c6.F, text)) {
            c0086c6.F = text;
            c0086c6.i0.d = true;
            c0086c6.invalidateSelf();
            c0086c6.x();
        }
        Cp cp = (!typedArrayJ.hasValue(0) || (resourceId3 = typedArrayJ.getResourceId(0, 0)) == 0) ? null : new Cp(context3, resourceId3);
        cp.k = typedArrayJ.getDimension(1, cp.k);
        c0086c6.C(cp);
        int i2 = typedArrayJ.getInt(3, 0);
        if (i2 == 1) {
            c0086c6.z0 = TextUtils.TruncateAt.START;
        } else if (i2 == 2) {
            c0086c6.z0 = TextUtils.TruncateAt.MIDDLE;
        } else if (i2 == 3) {
            c0086c6.z0 = TextUtils.TruncateAt.END;
        }
        c0086c6.A(typedArrayJ.getBoolean(18, false));
        if (attributeSet != null && attributeSet.getAttributeValue("http://schemas.android.com/apk/res-auto", "chipIconEnabled") != null && attributeSet.getAttributeValue("http://schemas.android.com/apk/res-auto", "chipIconVisible") == null) {
            c0086c6.A(typedArrayJ.getBoolean(15, false));
        }
        Drawable drawableZ = X8.z(context3, typedArrayJ, 14);
        Object obj2 = c0086c6.H;
        if (obj2 == null) {
            r15 = 0;
        } else if (obj2 instanceof ju) {
            r15 = obj2;
            r15 = 0;
        }
        if (r15 != drawableZ) {
            float fS = c0086c6.s();
            c0086c6.H = drawableZ != null ? drawableZ.mutate() : null;
            float fS2 = c0086c6.s();
            C0086c6.G(r15);
            if (c0086c6.E()) {
                c0086c6.q(c0086c6.H);
            }
            c0086c6.invalidateSelf();
            if (fS != fS2) {
                c0086c6.x();
            }
        }
        if (typedArrayJ.hasValue(17)) {
            ColorStateList colorStateListW6 = X8.w(context3, typedArrayJ, 17);
            c0086c6.K = true;
            if (c0086c6.I != colorStateListW6) {
                c0086c6.I = colorStateListW6;
                if (c0086c6.E()) {
                    c0086c6.H.setTintList(colorStateListW6);
                }
                c0086c6.onStateChange(c0086c6.getState());
            }
        }
        float dimension4 = typedArrayJ.getDimension(16, -1.0f);
        if (c0086c6.J != dimension4) {
            float fS3 = c0086c6.s();
            c0086c6.J = dimension4;
            float fS4 = c0086c6.s();
            c0086c6.invalidateSelf();
            if (fS3 != fS4) {
                c0086c6.x();
            }
        }
        c0086c6.B(typedArrayJ.getBoolean(31, false));
        if (attributeSet != null && attributeSet.getAttributeValue("http://schemas.android.com/apk/res-auto", "closeIconEnabled") != null && attributeSet.getAttributeValue("http://schemas.android.com/apk/res-auto", "closeIconVisible") == null) {
            c0086c6.B(typedArrayJ.getBoolean(26, false));
        }
        Drawable drawableZ2 = X8.z(context3, typedArrayJ, 25);
        Object obj3 = c0086c6.M;
        if (obj3 == null) {
            r14 = 0;
        } else if (obj3 instanceof ju) {
            r14 = obj3;
            r14 = 0;
        }
        if (r14 != drawableZ2) {
            float fT = c0086c6.t();
            c0086c6.M = drawableZ2 != null ? drawableZ2.mutate() : null;
            int[] iArr2 = AbstractC0133dm.a;
            c0086c6.N = new RippleDrawable(AbstractC0133dm.a(c0086c6.E), c0086c6.M, C0086c6.E0);
            float fT2 = c0086c6.t();
            C0086c6.G(r14);
            if (c0086c6.F()) {
                c0086c6.q(c0086c6.M);
            }
            c0086c6.invalidateSelf();
            if (fT != fT2) {
                c0086c6.x();
            }
        }
        ColorStateList colorStateListW7 = X8.w(context3, typedArrayJ, 30);
        if (c0086c6.O != colorStateListW7) {
            c0086c6.O = colorStateListW7;
            if (c0086c6.F()) {
                c0086c6.M.setTintList(colorStateListW7);
            }
            c0086c6.onStateChange(c0086c6.getState());
        }
        float dimension5 = typedArrayJ.getDimension(28, 0.0f);
        if (c0086c6.P != dimension5) {
            c0086c6.P = dimension5;
            c0086c6.invalidateSelf();
            if (c0086c6.F()) {
                c0086c6.x();
            }
        }
        boolean z = typedArrayJ.getBoolean(6, false);
        if (c0086c6.Q != z) {
            c0086c6.Q = z;
            float fS5 = c0086c6.s();
            if (!z && c0086c6.p0) {
                c0086c6.p0 = false;
            }
            float fS6 = c0086c6.s();
            c0086c6.invalidateSelf();
            if (fS5 != fS6) {
                c0086c6.x();
            }
        }
        c0086c6.z(typedArrayJ.getBoolean(10, false));
        if (attributeSet != null && attributeSet.getAttributeValue("http://schemas.android.com/apk/res-auto", "checkedIconEnabled") != null && attributeSet.getAttributeValue("http://schemas.android.com/apk/res-auto", "checkedIconVisible") == null) {
            c0086c6.z(typedArrayJ.getBoolean(8, false));
        }
        Drawable drawableZ3 = X8.z(context3, typedArrayJ, 7);
        if (c0086c6.S != drawableZ3) {
            float fS7 = c0086c6.s();
            c0086c6.S = drawableZ3;
            float fS8 = c0086c6.s();
            C0086c6.G(c0086c6.S);
            c0086c6.q(c0086c6.S);
            c0086c6.invalidateSelf();
            if (fS7 != fS8) {
                c0086c6.x();
            }
        }
        if (typedArrayJ.hasValue(9) && c0086c6.T != (colorStateListW = X8.w(context3, typedArrayJ, 9))) {
            c0086c6.T = colorStateListW;
            if (c0086c6.R && (drawable = c0086c6.S) != null && c0086c6.Q) {
                drawable.setTintList(colorStateListW);
            }
            c0086c6.onStateChange(c0086c6.getState());
        }
        if (typedArrayJ.hasValue(39) && (resourceId2 = typedArrayJ.getResourceId(39, 0)) != 0) {
            try {
                Animator animatorLoadAnimator = AnimatorInflater.loadAnimator(context3, resourceId2);
                if (animatorLoadAnimator instanceof AnimatorSet) {
                    Gi.a(((AnimatorSet) animatorLoadAnimator).getChildAnimations());
                } else if (animatorLoadAnimator != null) {
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(animatorLoadAnimator);
                    Gi.a(arrayList);
                }
            } catch (Exception e) {
                Log.w("MotionSpec", "Can't load animation resource ID #0x" + Integer.toHexString(resourceId2), e);
            }
        }
        if (typedArrayJ.hasValue(33) && (resourceId = typedArrayJ.getResourceId(33, 0)) != 0) {
            try {
                Animator animatorLoadAnimator2 = AnimatorInflater.loadAnimator(context3, resourceId);
                if (animatorLoadAnimator2 instanceof AnimatorSet) {
                    Gi.a(((AnimatorSet) animatorLoadAnimator2).getChildAnimations());
                } else if (animatorLoadAnimator2 != null) {
                    ArrayList arrayList2 = new ArrayList();
                    arrayList2.add(animatorLoadAnimator2);
                    Gi.a(arrayList2);
                }
            } catch (Exception e2) {
                Log.w("MotionSpec", "Can't load animation resource ID #0x" + Integer.toHexString(resourceId), e2);
            }
        }
        float dimension6 = typedArrayJ.getDimension(21, 0.0f);
        if (c0086c6.U != dimension6) {
            c0086c6.U = dimension6;
            c0086c6.invalidateSelf();
            c0086c6.x();
        }
        float dimension7 = typedArrayJ.getDimension(35, 0.0f);
        if (c0086c6.V != dimension7) {
            float fS9 = c0086c6.s();
            c0086c6.V = dimension7;
            float fS10 = c0086c6.s();
            c0086c6.invalidateSelf();
            if (fS9 != fS10) {
                c0086c6.x();
            }
        }
        float dimension8 = typedArrayJ.getDimension(34, 0.0f);
        if (c0086c6.W != dimension8) {
            float fS11 = c0086c6.s();
            c0086c6.W = dimension8;
            float fS12 = c0086c6.s();
            c0086c6.invalidateSelf();
            if (fS11 != fS12) {
                c0086c6.x();
            }
        }
        float dimension9 = typedArrayJ.getDimension(41, 0.0f);
        if (c0086c6.X != dimension9) {
            c0086c6.X = dimension9;
            c0086c6.invalidateSelf();
            c0086c6.x();
        }
        float dimension10 = typedArrayJ.getDimension(40, 0.0f);
        if (c0086c6.Y != dimension10) {
            c0086c6.Y = dimension10;
            c0086c6.invalidateSelf();
            c0086c6.x();
        }
        float dimension11 = typedArrayJ.getDimension(29, 0.0f);
        if (c0086c6.Z != dimension11) {
            c0086c6.Z = dimension11;
            c0086c6.invalidateSelf();
            if (c0086c6.F()) {
                c0086c6.x();
            }
        }
        float dimension12 = typedArrayJ.getDimension(27, 0.0f);
        if (c0086c6.a0 != dimension12) {
            c0086c6.a0 = dimension12;
            c0086c6.invalidateSelf();
            if (c0086c6.F()) {
                c0086c6.x();
            }
        }
        float dimension13 = typedArrayJ.getDimension(13, 0.0f);
        if (c0086c6.b0 != dimension13) {
            c0086c6.b0 = dimension13;
            c0086c6.invalidateSelf();
            c0086c6.x();
        }
        c0086c6.B0 = typedArrayJ.getDimensionPixelSize(4, Integer.MAX_VALUE);
        typedArrayJ.recycle();
        X8.j(context2, attributeSet, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f0400d1, moe.shizuku.privileged.api.R.style._0_resource_name_obfuscated_res_0x7f120473);
        X8.p(context2, attributeSet, iArr, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f0400d1, moe.shizuku.privileged.api.R.style._0_resource_name_obfuscated_res_0x7f120473, new int[0]);
        TypedArray typedArrayObtainStyledAttributes = context2.obtainStyledAttributes(attributeSet, iArr, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f0400d1, moe.shizuku.privileged.api.R.style._0_resource_name_obfuscated_res_0x7f120473);
        this.m = typedArrayObtainStyledAttributes.getBoolean(32, false);
        this.o = (int) Math.ceil(typedArrayObtainStyledAttributes.getDimension(20, (float) Math.ceil(TypedValue.applyDimension(1, 48, getContext().getResources().getDisplayMetrics()))));
        typedArrayObtainStyledAttributes.recycle();
        C0086c6 c0086c7 = this.e;
        if (c0086c7 != c0086c6) {
            if (c0086c7 != null) {
                c0086c7.y0 = new WeakReference(null);
            }
            this.e = c0086c6;
            c0086c6.A0 = false;
            c0086c6.y0 = new WeakReference(this);
            b(this.o);
        }
        c0086c6.k(AbstractC0817zs.e(this));
        X8.j(context2, attributeSet, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f0400d1, moe.shizuku.privileged.api.R.style._0_resource_name_obfuscated_res_0x7f120473);
        X8.p(context2, attributeSet, iArr, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f0400d1, moe.shizuku.privileged.api.R.style._0_resource_name_obfuscated_res_0x7f120473, new int[0]);
        TypedArray typedArrayObtainStyledAttributes2 = context2.obtainStyledAttributes(attributeSet, iArr, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f0400d1, moe.shizuku.privileged.api.R.style._0_resource_name_obfuscated_res_0x7f120473);
        boolean zHasValue = typedArrayObtainStyledAttributes2.hasValue(37);
        typedArrayObtainStyledAttributes2.recycle();
        new C0024a6(this, this);
        C0086c6 c0086c8 = this.e;
        if (c0086c8 != null && (obj = c0086c8.M) != null && (obj instanceof ju)) {
        }
        Hs.m(this, null);
        if (!zHasValue) {
            setOutlineProvider(new Z5(i, this));
        }
        setChecked(this.i);
        setText(c0086c6.F);
        setEllipsize(c0086c6.z0);
        e();
        if (!this.e.A0) {
            setLines(1);
            setHorizontallyScrolling(true);
        }
        super.setGravity(8388627);
        d();
        if (this.m) {
            setMinHeight(this.o);
        }
        this.n = getLayoutDirection();
        super.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: rikka.shizuku.X5
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                CompoundButton.OnCheckedChangeListener onCheckedChangeListener = this.a.h;
                if (onCheckedChangeListener != null) {
                    onCheckedChangeListener.onCheckedChanged(compoundButton, z2);
                }
            }
        });
    }

    @Override // rikka.shizuku.InterfaceC0657un
    public final void a(C0317jn c0317jn) {
        this.e.a(c0317jn);
    }

    public final void b(int i) {
        this.o = i;
        if (!this.m) {
            InsetDrawable insetDrawable = this.f;
            if (insetDrawable == null) {
                c();
                return;
            }
            if (insetDrawable != null) {
                this.f = null;
                setMinWidth(0);
                C0086c6 c0086c6 = this.e;
                setMinHeight((int) (c0086c6 != null ? c0086c6.A : 0.0f));
                c();
                return;
            }
            return;
        }
        int iMax = Math.max(0, i - ((int) this.e.A));
        int iMax2 = Math.max(0, i - this.e.getIntrinsicWidth());
        if (iMax2 <= 0 && iMax <= 0) {
            InsetDrawable insetDrawable2 = this.f;
            if (insetDrawable2 == null) {
                c();
                return;
            }
            if (insetDrawable2 != null) {
                this.f = null;
                setMinWidth(0);
                C0086c6 c0086c7 = this.e;
                setMinHeight((int) (c0086c7 != null ? c0086c7.A : 0.0f));
                c();
                return;
            }
            return;
        }
        int i2 = iMax2 > 0 ? iMax2 / 2 : 0;
        int i3 = iMax > 0 ? iMax / 2 : 0;
        if (this.f != null) {
            Rect rect = new Rect();
            this.f.getPadding(rect);
            if (rect.top == i3 && rect.bottom == i3 && rect.left == i2 && rect.right == i2) {
                c();
                return;
            }
        }
        if (getMinHeight() != i) {
            setMinHeight(i);
        }
        if (getMinWidth() != i) {
            setMinWidth(i);
        }
        this.f = new InsetDrawable((Drawable) this.e, i2, i3, i2, i3);
        c();
    }

    public final void c() {
        C0086c6 c0086c6 = this.e;
        int[] iArr = AbstractC0133dm.a;
        ColorStateList colorStateListA = AbstractC0133dm.a(c0086c6.E);
        Drawable drawable = this.f;
        if (drawable == null) {
            drawable = c0086c6;
        }
        this.g = new RippleDrawable(colorStateListA, drawable, null);
        c0086c6.getClass();
        RippleDrawable rippleDrawable = this.g;
        WeakHashMap weakHashMap = Hs.a;
        setBackground(rippleDrawable);
        d();
    }

    public final void d() {
        C0086c6 c0086c6 = this.e;
        if (TextUtils.isEmpty(getText()) || c0086c6 == null) {
            return;
        }
        int iT = (int) (c0086c6.t() + c0086c6.b0 + c0086c6.Y);
        int iS = (int) (c0086c6.s() + c0086c6.U + c0086c6.X);
        if (this.f != null) {
            Rect rect = new Rect();
            this.f.getPadding(rect);
            iS += rect.left;
            iT += rect.right;
        }
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        WeakHashMap weakHashMap = Hs.a;
        setPaddingRelative(iS, paddingTop, iT, paddingBottom);
    }

    @Override // android.view.View
    public final boolean dispatchHoverEvent(MotionEvent motionEvent) {
        return super.dispatchHoverEvent(motionEvent);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [boolean, int] */
    @Override // rikka.shizuku.C0482p2, android.widget.CompoundButton, android.widget.TextView, android.view.View
    public final void drawableStateChanged() {
        int i;
        int i2;
        super.drawableStateChanged();
        C0086c6 c0086c6 = this.e;
        boolean zY = false;
        if (c0086c6 != null && C0086c6.w(c0086c6.M)) {
            C0086c6 c0086c7 = this.e;
            ?? IsEnabled = isEnabled();
            if (this.l) {
                i = IsEnabled;
                i = IsEnabled + 1;
            }
            i = IsEnabled;
            int i3 = i;
            if (this.k) {
                i3 = i + 1;
            }
            int i4 = i3;
            if (this.j) {
                i4 = i3 + 1;
            }
            int i5 = i4;
            if (isChecked()) {
                i5 = i4 + 1;
            }
            int[] iArr = new int[i5];
            if (isEnabled()) {
                iArr[0] = 16842910;
                i2 = 1;
            } else {
                i2 = 0;
            }
            if (this.l) {
                iArr[i2] = 16842908;
                i2++;
            }
            if (this.k) {
                iArr[i2] = 16843623;
                i2++;
            }
            if (this.j) {
                iArr[i2] = 16842919;
                i2++;
            }
            if (isChecked()) {
                iArr[i2] = 16842913;
            }
            if (!Arrays.equals(c0086c7.w0, iArr)) {
                c0086c7.w0 = iArr;
                if (c0086c7.F()) {
                    zY = c0086c7.y(c0086c7.getState(), iArr);
                }
            }
        }
        if (zY) {
            invalidate();
        }
    }

    public final void e() {
        TextPaint paint = getPaint();
        C0086c6 c0086c6 = this.e;
        if (c0086c6 != null) {
            paint.drawableState = c0086c6.getState();
        }
        C0086c6 c0086c7 = this.e;
        Cp cp = c0086c7 != null ? c0086c7.i0.f : null;
        if (cp != null) {
            cp.e(getContext(), paint, this.s);
        }
    }

    @Override // android.widget.CheckBox, android.widget.CompoundButton, android.widget.Button, android.widget.TextView, android.view.View
    public final CharSequence getAccessibilityClassName() {
        if (!TextUtils.isEmpty(this.p)) {
            return this.p;
        }
        C0086c6 c0086c6 = this.e;
        if (!(c0086c6 != null && c0086c6.Q)) {
            return isClickable() ? "android.widget.Button" : "android.view.View";
        }
        getParent();
        return "android.widget.Button";
    }

    @Override // android.widget.TextView
    public final TextUtils.TruncateAt getEllipsize() {
        C0086c6 c0086c6 = this.e;
        if (c0086c6 != null) {
            return c0086c6.z0;
        }
        return null;
    }

    @Override // android.widget.TextView, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        AbstractC0599sr.H(this, this.e);
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    public final int[] onCreateDrawableState(int i) {
        int[] iArrOnCreateDrawableState = super.onCreateDrawableState(i + 2);
        if (isChecked()) {
            View.mergeDrawableStates(iArrOnCreateDrawableState, u);
        }
        C0086c6 c0086c6 = this.e;
        if (c0086c6 != null && c0086c6.Q) {
            View.mergeDrawableStates(iArrOnCreateDrawableState, v);
        }
        return iArrOnCreateDrawableState;
    }

    @Override // android.widget.TextView, android.view.View
    public final void onFocusChanged(boolean z, int i, Rect rect) {
        super.onFocusChanged(z, i, rect);
    }

    @Override // android.view.View
    public final boolean onHoverEvent(MotionEvent motionEvent) {
        Object obj;
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 7) {
            RectF rectF = this.r;
            rectF.setEmpty();
            C0086c6 c0086c6 = this.e;
            if (c0086c6 != null && (obj = c0086c6.M) != null && (obj instanceof ju)) {
            }
            boolean zContains = rectF.contains(motionEvent.getX(), motionEvent.getY());
            if (this.k != zContains) {
                this.k = zContains;
                refreshDrawableState();
            }
        } else if (actionMasked == 10 && this.k) {
            this.k = false;
            refreshDrawableState();
        }
        return super.onHoverEvent(motionEvent);
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(getAccessibilityClassName());
        C0086c6 c0086c6 = this.e;
        accessibilityNodeInfo.setCheckable(c0086c6 != null && c0086c6.Q);
        accessibilityNodeInfo.setClickable(isClickable());
        getParent();
    }

    @Override // android.widget.Button, android.widget.TextView, android.view.View
    public final PointerIcon onResolvePointerIcon(MotionEvent motionEvent, int i) {
        Object obj;
        RectF rectF = this.r;
        rectF.setEmpty();
        C0086c6 c0086c6 = this.e;
        if (c0086c6 != null && (obj = c0086c6.M) != null && (obj instanceof ju)) {
        }
        return (rectF.contains(motionEvent.getX(), motionEvent.getY()) && isEnabled()) ? PointerIcon.getSystemIcon(getContext(), 1002) : super.onResolvePointerIcon(motionEvent, i);
    }

    @Override // android.widget.TextView, android.view.View
    public final void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
        if (this.n != i) {
            this.n = i;
            d();
        }
    }

    /* JADX WARN: Code duplicated, block: B:29:0x004d  */
    @Override // android.widget.TextView, android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z;
        Object obj;
        int actionMasked = motionEvent.getActionMasked();
        RectF rectF = this.r;
        rectF.setEmpty();
        C0086c6 c0086c6 = this.e;
        if (c0086c6 != null && (obj = c0086c6.M) != null && (obj instanceof ju)) {
        }
        boolean zContains = rectF.contains(motionEvent.getX(), motionEvent.getY());
        if (actionMasked != 0) {
            if (actionMasked != 1) {
                if (actionMasked == 2) {
                    boolean z2 = this.j;
                    if (z2) {
                        if (!zContains && z2) {
                            this.j = false;
                            refreshDrawableState();
                        }
                        z = true;
                    }
                } else if (actionMasked != 3) {
                }
                z = false;
            } else {
                if (this.j) {
                    playSoundEffect(0);
                    z = true;
                }
                if (this.j) {
                    this.j = false;
                    refreshDrawableState();
                }
            }
            z = false;
            if (this.j) {
                this.j = false;
                refreshDrawableState();
            }
        } else if (zContains) {
            if (!this.j) {
                this.j = true;
                refreshDrawableState();
            }
            z = true;
        } else {
            z = false;
        }
        return z || super.onTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public final void setBackground(Drawable drawable) {
        Drawable drawable2 = this.f;
        if (drawable2 == null) {
            drawable2 = this.e;
        }
        if (drawable == drawable2 || drawable == this.g) {
            super.setBackground(drawable);
        } else {
            Log.w("Chip", "Do not set the background; Chip manages its own background drawable.");
        }
    }

    @Override // android.view.View
    public final void setBackgroundColor(int i) {
        Log.w("Chip", "Do not set the background color; Chip manages its own background drawable.");
    }

    @Override // rikka.shizuku.C0482p2, android.view.View
    public final void setBackgroundDrawable(Drawable drawable) {
        Drawable drawable2 = this.f;
        if (drawable2 == null) {
            drawable2 = this.e;
        }
        if (drawable == drawable2 || drawable == this.g) {
            super.setBackgroundDrawable(drawable);
        } else {
            Log.w("Chip", "Do not set the background drawable; Chip manages its own background drawable.");
        }
    }

    @Override // rikka.shizuku.C0482p2, android.view.View
    public final void setBackgroundResource(int i) {
        Log.w("Chip", "Do not set the background resource; Chip manages its own background drawable.");
    }

    @Override // android.view.View
    public final void setBackgroundTintList(ColorStateList colorStateList) {
        Log.w("Chip", "Do not set the background tint list; Chip manages its own background drawable.");
    }

    @Override // android.view.View
    public final void setBackgroundTintMode(PorterDuff.Mode mode) {
        Log.w("Chip", "Do not set the background tint mode; Chip manages its own background drawable.");
    }

    @Override // android.widget.CompoundButton, android.widget.Checkable
    public final void setChecked(boolean z) {
        C0086c6 c0086c6 = this.e;
        if (c0086c6 == null) {
            this.i = z;
        } else if (c0086c6.Q) {
            super.setChecked(z);
        }
    }

    @Override // rikka.shizuku.C0482p2, android.widget.TextView
    public final void setCompoundDrawables(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        if (drawable != null) {
            throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        }
        if (drawable3 != null) {
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        }
        super.setCompoundDrawables(drawable, drawable2, drawable3, drawable4);
    }

    @Override // rikka.shizuku.C0482p2, android.widget.TextView
    public final void setCompoundDrawablesRelative(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        if (drawable != null) {
            throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        }
        if (drawable3 != null) {
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        }
        super.setCompoundDrawablesRelative(drawable, drawable2, drawable3, drawable4);
    }

    @Override // android.widget.TextView
    public final void setCompoundDrawablesRelativeWithIntrinsicBounds(int i, int i2, int i3, int i4) {
        if (i != 0) {
            throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        }
        if (i3 != 0) {
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        }
        super.setCompoundDrawablesRelativeWithIntrinsicBounds(i, i2, i3, i4);
    }

    @Override // android.widget.TextView
    public final void setCompoundDrawablesWithIntrinsicBounds(int i, int i2, int i3, int i4) {
        if (i != 0) {
            throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        }
        if (i3 != 0) {
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        }
        super.setCompoundDrawablesWithIntrinsicBounds(i, i2, i3, i4);
    }

    @Override // android.view.View
    public final void setElevation(float f) {
        super.setElevation(f);
        C0086c6 c0086c6 = this.e;
        if (c0086c6 != null) {
            c0086c6.k(f);
        }
    }

    @Override // android.widget.TextView
    public final void setEllipsize(TextUtils.TruncateAt truncateAt) {
        if (this.e == null) {
            return;
        }
        if (truncateAt == TextUtils.TruncateAt.MARQUEE) {
            throw new UnsupportedOperationException("Text within a chip are not allowed to scroll.");
        }
        super.setEllipsize(truncateAt);
        C0086c6 c0086c6 = this.e;
        if (c0086c6 != null) {
            c0086c6.z0 = truncateAt;
        }
    }

    @Override // android.widget.TextView
    public final void setGravity(int i) {
        if (i != 8388627) {
            Log.w("Chip", "Chip text must be vertically center and start aligned");
        } else {
            super.setGravity(i);
        }
    }

    @Override // android.view.View
    public final void setLayoutDirection(int i) {
        if (this.e == null) {
            return;
        }
        super.setLayoutDirection(i);
    }

    @Override // android.widget.TextView
    public final void setLines(int i) {
        if (i > 1) {
            throw new UnsupportedOperationException("Chip does not support multi-line text");
        }
        super.setLines(i);
    }

    @Override // android.widget.TextView
    public final void setMaxLines(int i) {
        if (i > 1) {
            throw new UnsupportedOperationException("Chip does not support multi-line text");
        }
        super.setMaxLines(i);
    }

    @Override // android.widget.TextView
    public final void setMaxWidth(int i) {
        super.setMaxWidth(i);
        C0086c6 c0086c6 = this.e;
        if (c0086c6 != null) {
            c0086c6.B0 = i;
        }
    }

    @Override // android.widget.TextView
    public final void setMinLines(int i) {
        if (i > 1) {
            throw new UnsupportedOperationException("Chip does not support multi-line text");
        }
        super.setMinLines(i);
    }

    @Override // android.widget.CompoundButton
    public final void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        this.h = onCheckedChangeListener;
    }

    @Override // android.widget.TextView
    public final void setSingleLine(boolean z) {
        if (!z) {
            throw new UnsupportedOperationException("Chip does not support multi-line text");
        }
        super.setSingleLine(z);
    }

    @Override // android.widget.TextView
    public final void setText(CharSequence charSequence, TextView.BufferType bufferType) {
        C0086c6 c0086c6 = this.e;
        if (c0086c6 == null) {
            return;
        }
        if (charSequence == null) {
            charSequence = "";
        }
        super.setText(c0086c6.A0 ? null : charSequence, bufferType);
        C0086c6 c0086c7 = this.e;
        if (c0086c7 == null || TextUtils.equals(c0086c7.F, charSequence)) {
            return;
        }
        c0086c7.F = charSequence;
        c0086c7.i0.d = true;
        c0086c7.invalidateSelf();
        c0086c7.x();
    }

    @Override // android.widget.TextView
    public final void setTextAppearance(Context context, int i) {
        super.setTextAppearance(context, i);
        C0086c6 c0086c6 = this.e;
        if (c0086c6 != null) {
            c0086c6.C(new Cp(c0086c6.c0, i));
        }
        e();
    }

    @Override // android.widget.TextView
    public final void setTextSize(int i, float f) {
        super.setTextSize(i, f);
        C0086c6 c0086c6 = this.e;
        if (c0086c6 != null) {
            float fApplyDimension = TypedValue.applyDimension(i, f, getResources().getDisplayMetrics());
            Fp fp = c0086c6.i0;
            Cp cp = fp.f;
            if (cp != null) {
                cp.k = fApplyDimension;
                fp.a.setTextSize(fApplyDimension);
                c0086c6.x();
                c0086c6.invalidateSelf();
            }
        }
        e();
    }

    @Override // android.widget.TextView
    public final void setCompoundDrawablesRelativeWithIntrinsicBounds(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        if (drawable != null) {
            throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        }
        if (drawable3 == null) {
            super.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
            return;
        }
        throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
    }

    @Override // android.widget.TextView
    public final void setCompoundDrawablesWithIntrinsicBounds(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        if (drawable != null) {
            throw new UnsupportedOperationException("Please set left drawable using R.attr#chipIcon.");
        }
        if (drawable3 == null) {
            super.setCompoundDrawablesWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
            return;
        }
        throw new UnsupportedOperationException("Please set right drawable using R.attr#closeIcon.");
    }

    @Override // android.widget.TextView
    public final void setTextAppearance(int i) {
        super.setTextAppearance(i);
        C0086c6 c0086c6 = this.e;
        if (c0086c6 != null) {
            c0086c6.C(new Cp(c0086c6.c0, i));
        }
        e();
    }
}
