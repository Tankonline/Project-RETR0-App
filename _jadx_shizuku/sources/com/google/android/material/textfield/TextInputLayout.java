package com.google.android.material.textfield;

import android.R;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Parcelable;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStructure;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.google.android.material.internal.CheckableImageButton;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.WeakHashMap;
import rikka.shizuku.Aa;
import rikka.shizuku.AbstractC0046as;
import rikka.shizuku.AbstractC0304ja;
import rikka.shizuku.AbstractC0599sr;
import rikka.shizuku.AbstractC0744xh;
import rikka.shizuku.AbstractC0750xn;
import rikka.shizuku.An;
import rikka.shizuku.Aq;
import rikka.shizuku.Bs;
import rikka.shizuku.C0054b5;
import rikka.shizuku.C0256hm;
import rikka.shizuku.C0299j5;
import rikka.shizuku.C0317jn;
import rikka.shizuku.C0340kf;
import rikka.shizuku.C0371lf;
import rikka.shizuku.C0398mb;
import rikka.shizuku.C0460ob;
import rikka.shizuku.C0488p8;
import rikka.shizuku.C0519q8;
import rikka.shizuku.C0578s6;
import rikka.shizuku.C0579s7;
import rikka.shizuku.C0606t3;
import rikka.shizuku.C0644ua;
import rikka.shizuku.C0732x5;
import rikka.shizuku.Cp;
import rikka.shizuku.Dp;
import rikka.shizuku.E6;
import rikka.shizuku.Eh;
import rikka.shizuku.G;
import rikka.shizuku.G0;
import rikka.shizuku.Gp;
import rikka.shizuku.Hb;
import rikka.shizuku.Hp;
import rikka.shizuku.Hs;
import rikka.shizuku.K3;
import rikka.shizuku.L2;
import rikka.shizuku.Lk;
import rikka.shizuku.N9;
import rikka.shizuku.Po;
import rikka.shizuku.R5;
import rikka.shizuku.RunnableC0607t4;
import rikka.shizuku.S7;
import rikka.shizuku.V1;
import rikka.shizuku.Wh;
import rikka.shizuku.X8;
import rikka.shizuku.Xh;
import rikka.shizuku.Ys;

/* JADX INFO: loaded from: classes.dex */
public class TextInputLayout extends LinearLayout implements ViewTreeObserver.OnGlobalLayoutListener {
    public static final int[][] B0 = {new int[]{R.attr.state_pressed}, new int[0]};
    public final ColorStateList A;
    public boolean A0;
    public final ColorStateList B;
    public final boolean C;
    public CharSequence D;
    public boolean E;
    public Xh F;
    public Xh G;
    public StateListDrawable H;
    public boolean I;
    public Xh J;
    public Xh K;
    public C0317jn L;
    public boolean M;
    public final int N;
    public final int O;
    public int P;
    public int Q;
    public final int R;
    public final int S;
    public int T;
    public int U;
    public final Rect V;
    public final Rect W;
    public final FrameLayout a;
    public final RectF a0;
    public final Po b;
    public ColorDrawable b0;
    public final C0460ob c;
    public int c0;
    public EditText d;
    public final LinkedHashSet d0;
    public CharSequence e;
    public ColorDrawable e0;
    public int f;
    public int f0;
    public int g;
    public Drawable g0;
    public int h;
    public ColorStateList h0;
    public int i;
    public final ColorStateList i0;
    public final C0371lf j;
    public final int j0;
    public final boolean k;
    public final int k0;
    public final int l;
    public final int l0;
    public boolean m;
    public final ColorStateList m0;
    public final C0579s7 n;
    public final int n0;
    public final C0606t3 o;
    public final int o0;
    public final int p;
    public final int p0;
    public final int q;
    public final int q0;
    public final CharSequence r;
    public final int r0;
    public boolean s;
    public int s0;
    public C0606t3 t;
    public boolean t0;
    public final ColorStateList u;
    public final C0578s6 u0;
    public final int v;
    public final boolean v0;
    public final Hb w;
    public final boolean w0;
    public final Hb x;
    public ValueAnimator x0;
    public final ColorStateList y;
    public boolean y0;
    public final ColorStateList z;
    public boolean z0;

    public TextInputLayout(Context context, AttributeSet attributeSet) {
        ColorStateList colorStateListH;
        ColorStateList colorStateListH2;
        ColorStateList colorStateListH3;
        ColorStateList colorStateListH4;
        boolean z;
        ColorStateList colorStateListX;
        super(X8.c0(context, attributeSet, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f0404ff, moe.shizuku.privileged.api.R.style._0_resource_name_obfuscated_res_0x7f12039c), attributeSet, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f0404ff);
        this.f = -1;
        this.g = -1;
        this.h = -1;
        this.i = -1;
        C0371lf c0371lf = new C0371lf(this);
        this.j = c0371lf;
        this.n = new C0579s7(18);
        this.V = new Rect();
        this.W = new Rect();
        this.a0 = new RectF();
        this.d0 = new LinkedHashSet();
        C0578s6 c0578s6 = new C0578s6(this);
        this.u0 = c0578s6;
        this.A0 = false;
        Context context2 = getContext();
        setOrientation(1);
        setWillNotDraw(false);
        setAddStatesFromChildren(true);
        FrameLayout frameLayout = new FrameLayout(context2);
        this.a = frameLayout;
        frameLayout.setAddStatesFromChildren(true);
        LinearInterpolator linearInterpolator = V1.a;
        c0578s6.Q = linearInterpolator;
        c0578s6.h(false);
        c0578s6.P = linearInterpolator;
        c0578s6.h(false);
        if (c0578s6.g != 8388659) {
            c0578s6.g = 8388659;
            c0578s6.h(false);
        }
        int[] iArr = Lk.G;
        X8.j(context2, attributeSet, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f0404ff, moe.shizuku.privileged.api.R.style._0_resource_name_obfuscated_res_0x7f12039c);
        X8.p(context2, attributeSet, iArr, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f0404ff, moe.shizuku.privileged.api.R.style._0_resource_name_obfuscated_res_0x7f12039c, 22, 20, 40, 45, 49);
        TypedArray typedArrayObtainStyledAttributes = context2.obtainStyledAttributes(attributeSet, iArr, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f0404ff, moe.shizuku.privileged.api.R.style._0_resource_name_obfuscated_res_0x7f12039c);
        K3 k3 = new K3(context2, typedArrayObtainStyledAttributes);
        Po po = new Po(this, k3);
        this.b = po;
        this.C = typedArrayObtainStyledAttributes.getBoolean(48, true);
        o(typedArrayObtainStyledAttributes.getText(4));
        this.w0 = typedArrayObtainStyledAttributes.getBoolean(47, true);
        this.v0 = typedArrayObtainStyledAttributes.getBoolean(42, true);
        if (typedArrayObtainStyledAttributes.hasValue(6)) {
            int i = typedArrayObtainStyledAttributes.getInt(6, -1);
            this.f = i;
            EditText editText = this.d;
            if (editText != null && i != -1) {
                editText.setMinEms(i);
            }
        } else if (typedArrayObtainStyledAttributes.hasValue(3)) {
            int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(3, -1);
            this.h = dimensionPixelSize;
            EditText editText2 = this.d;
            if (editText2 != null && dimensionPixelSize != -1) {
                editText2.setMinWidth(dimensionPixelSize);
            }
        }
        if (typedArrayObtainStyledAttributes.hasValue(5)) {
            int i2 = typedArrayObtainStyledAttributes.getInt(5, -1);
            this.g = i2;
            EditText editText3 = this.d;
            if (editText3 != null && i2 != -1) {
                editText3.setMaxEms(i2);
            }
        } else if (typedArrayObtainStyledAttributes.hasValue(2)) {
            int dimensionPixelSize2 = typedArrayObtainStyledAttributes.getDimensionPixelSize(2, -1);
            this.i = dimensionPixelSize2;
            EditText editText4 = this.d;
            if (editText4 != null && dimensionPixelSize2 != -1) {
                editText4.setMaxWidth(dimensionPixelSize2);
            }
        }
        this.L = C0317jn.c(context2, attributeSet, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f0404ff, moe.shizuku.privileged.api.R.style._0_resource_name_obfuscated_res_0x7f12039c).a();
        this.N = context2.getResources().getDimensionPixelOffset(moe.shizuku.privileged.api.R.dimen._0_resource_name_obfuscated_res_0x7f07031a);
        this.P = typedArrayObtainStyledAttributes.getDimensionPixelOffset(9, 0);
        int dimensionPixelSize3 = typedArrayObtainStyledAttributes.getDimensionPixelSize(16, context2.getResources().getDimensionPixelSize(moe.shizuku.privileged.api.R.dimen._0_resource_name_obfuscated_res_0x7f07031b));
        this.R = dimensionPixelSize3;
        this.S = typedArrayObtainStyledAttributes.getDimensionPixelSize(17, context2.getResources().getDimensionPixelSize(moe.shizuku.privileged.api.R.dimen._0_resource_name_obfuscated_res_0x7f07031c));
        this.Q = dimensionPixelSize3;
        float dimension = typedArrayObtainStyledAttributes.getDimension(13, -1.0f);
        float dimension2 = typedArrayObtainStyledAttributes.getDimension(12, -1.0f);
        float dimension3 = typedArrayObtainStyledAttributes.getDimension(10, -1.0f);
        float dimension4 = typedArrayObtainStyledAttributes.getDimension(11, -1.0f);
        C0317jn c0317jnG = this.L.g();
        if (dimension >= 0.0f) {
            c0317jnG.e = new G(dimension);
        }
        if (dimension2 >= 0.0f) {
            c0317jnG.f = new G(dimension2);
        }
        if (dimension3 >= 0.0f) {
            c0317jnG.g = new G(dimension3);
        }
        if (dimension4 >= 0.0f) {
            c0317jnG.h = new G(dimension4);
        }
        this.L = c0317jnG.a();
        ColorStateList colorStateListX2 = X8.x(context2, k3, 7);
        if (colorStateListX2 != null) {
            int defaultColor = colorStateListX2.getDefaultColor();
            this.n0 = defaultColor;
            this.U = defaultColor;
            if (colorStateListX2.isStateful()) {
                this.o0 = colorStateListX2.getColorForState(new int[]{-16842910}, -1);
                this.p0 = colorStateListX2.getColorForState(new int[]{R.attr.state_focused, R.attr.state_enabled}, -1);
                this.q0 = colorStateListX2.getColorForState(new int[]{R.attr.state_hovered, R.attr.state_enabled}, -1);
            } else {
                this.p0 = defaultColor;
                ColorStateList colorStateListO = AbstractC0599sr.o(context2, moe.shizuku.privileged.api.R.color._0_resource_name_obfuscated_res_0x7f06054a);
                this.o0 = colorStateListO.getColorForState(new int[]{-16842910}, -1);
                this.q0 = colorStateListO.getColorForState(new int[]{R.attr.state_hovered}, -1);
            }
        } else {
            this.U = 0;
            this.n0 = 0;
            this.o0 = 0;
            this.p0 = 0;
            this.q0 = 0;
        }
        if (typedArrayObtainStyledAttributes.hasValue(1)) {
            ColorStateList colorStateListH5 = k3.h(1);
            this.i0 = colorStateListH5;
            this.h0 = colorStateListH5;
        }
        ColorStateList colorStateListX3 = X8.x(context2, k3, 14);
        this.l0 = typedArrayObtainStyledAttributes.getColor(14, 0);
        this.j0 = context2.getColor(moe.shizuku.privileged.api.R.color._0_resource_name_obfuscated_res_0x7f060565);
        this.r0 = context2.getColor(moe.shizuku.privileged.api.R.color._0_resource_name_obfuscated_res_0x7f060566);
        this.k0 = context2.getColor(moe.shizuku.privileged.api.R.color._0_resource_name_obfuscated_res_0x7f060569);
        if (colorStateListX3 != null) {
            if (colorStateListX3.isStateful()) {
                this.j0 = colorStateListX3.getDefaultColor();
                this.r0 = colorStateListX3.getColorForState(new int[]{-16842910}, -1);
                this.k0 = colorStateListX3.getColorForState(new int[]{R.attr.state_hovered, R.attr.state_enabled}, -1);
                this.l0 = colorStateListX3.getColorForState(new int[]{R.attr.state_focused, R.attr.state_enabled}, -1);
            } else if (this.l0 != colorStateListX3.getDefaultColor()) {
                this.l0 = colorStateListX3.getDefaultColor();
            }
            C();
        }
        if (typedArrayObtainStyledAttributes.hasValue(15) && this.m0 != (colorStateListX = X8.x(context2, k3, 15))) {
            this.m0 = colorStateListX;
            C();
        }
        if (typedArrayObtainStyledAttributes.getResourceId(49, -1) != -1) {
            int resourceId = typedArrayObtainStyledAttributes.getResourceId(49, 0);
            TextInputLayout textInputLayout = c0578s6.a;
            Cp cp = new Cp(textInputLayout.getContext(), resourceId);
            ColorStateList colorStateList = cp.j;
            if (colorStateList != null) {
                c0578s6.k = colorStateList;
            }
            float f = cp.k;
            if (f != 0.0f) {
                c0578s6.i = f;
            }
            ColorStateList colorStateList2 = cp.a;
            if (colorStateList2 != null) {
                c0578s6.U = colorStateList2;
            }
            c0578s6.S = cp.e;
            c0578s6.T = cp.f;
            c0578s6.R = cp.g;
            c0578s6.V = cp.i;
            C0732x5 c0732x5 = c0578s6.y;
            if (c0732x5 != null) {
                c0732x5.f = true;
            }
            Ys ys = new Ys(11, c0578s6);
            cp.a();
            c0578s6.y = new C0732x5(ys, cp.n);
            cp.c(textInputLayout.getContext(), c0578s6.y);
            c0578s6.h(false);
            this.i0 = c0578s6.k;
            if (this.d != null) {
                z(false, false);
                y();
            }
        }
        this.A = k3.h(24);
        this.B = k3.h(25);
        int resourceId2 = typedArrayObtainStyledAttributes.getResourceId(40, 0);
        CharSequence text = typedArrayObtainStyledAttributes.getText(35);
        int i3 = typedArrayObtainStyledAttributes.getInt(34, 1);
        boolean z2 = typedArrayObtainStyledAttributes.getBoolean(36, false);
        int resourceId3 = typedArrayObtainStyledAttributes.getResourceId(45, 0);
        boolean z3 = typedArrayObtainStyledAttributes.getBoolean(44, false);
        CharSequence text2 = typedArrayObtainStyledAttributes.getText(43);
        int resourceId4 = typedArrayObtainStyledAttributes.getResourceId(57, 0);
        CharSequence text3 = typedArrayObtainStyledAttributes.getText(56);
        boolean z4 = typedArrayObtainStyledAttributes.getBoolean(18, false);
        int i4 = typedArrayObtainStyledAttributes.getInt(19, -1);
        if (this.l != i4) {
            if (i4 > 0) {
                this.l = i4;
            } else {
                this.l = -1;
            }
            if (this.k && this.o != null) {
                EditText editText5 = this.d;
                s(editText5 == null ? null : editText5.getText());
            }
        }
        this.q = typedArrayObtainStyledAttributes.getResourceId(22, 0);
        this.p = typedArrayObtainStyledAttributes.getResourceId(20, 0);
        int i5 = typedArrayObtainStyledAttributes.getInt(8, 0);
        if (i5 != this.O) {
            this.O = i5;
            if (this.d != null) {
                i();
            }
        }
        c0371lf.s = text;
        C0606t3 c0606t3 = c0371lf.r;
        if (c0606t3 != null) {
            c0606t3.setContentDescription(text);
        }
        c0371lf.t = i3;
        C0606t3 c0606t4 = c0371lf.r;
        if (c0606t4 != null) {
            WeakHashMap weakHashMap = Hs.a;
            c0606t4.setAccessibilityLiveRegion(i3);
        }
        c0371lf.z = resourceId3;
        C0606t3 c0606t5 = c0371lf.y;
        if (c0606t5 != null) {
            c0606t5.setTextAppearance(resourceId3);
        }
        c0371lf.u = resourceId2;
        C0606t3 c0606t6 = c0371lf.r;
        if (c0606t6 != null) {
            c0371lf.h.q(c0606t6, resourceId2);
        }
        if (this.t == null) {
            C0606t3 c0606t7 = new C0606t3(getContext(), null);
            this.t = c0606t7;
            c0606t7.setId(moe.shizuku.privileged.api.R.id._0_resource_name_obfuscated_res_0x7f0901da);
            this.t.setImportantForAccessibility(2);
            Hb hbD = d();
            this.w = hbD;
            hbD.b = 67L;
            this.x = d();
            int i6 = this.v;
            this.v = i6;
            C0606t3 c0606t8 = this.t;
            if (c0606t8 != null) {
                c0606t8.setTextAppearance(i6);
            }
        }
        if (TextUtils.isEmpty(text3)) {
            p(false);
        } else {
            if (!this.s) {
                p(true);
            }
            this.r = text3;
        }
        EditText editText6 = this.d;
        A(editText6 == null ? null : editText6.getText());
        this.v = resourceId4;
        C0606t3 c0606t9 = this.t;
        if (c0606t9 != null) {
            c0606t9.setTextAppearance(resourceId4);
        }
        if (typedArrayObtainStyledAttributes.hasValue(41)) {
            ColorStateList colorStateListH6 = k3.h(41);
            c0371lf.v = colorStateListH6;
            C0606t3 c0606t10 = c0371lf.r;
            if (c0606t10 != null && colorStateListH6 != null) {
                c0606t10.setTextColor(colorStateListH6);
            }
        }
        if (typedArrayObtainStyledAttributes.hasValue(46)) {
            ColorStateList colorStateListH7 = k3.h(46);
            c0371lf.A = colorStateListH7;
            C0606t3 c0606t11 = c0371lf.y;
            if (c0606t11 != null && colorStateListH7 != null) {
                c0606t11.setTextColor(colorStateListH7);
            }
        }
        if (typedArrayObtainStyledAttributes.hasValue(50) && this.i0 != (colorStateListH4 = k3.h(50))) {
            if (this.h0 != null || c0578s6.k == colorStateListH4) {
                z = false;
            } else {
                c0578s6.k = colorStateListH4;
                z = false;
                c0578s6.h(false);
            }
            this.i0 = colorStateListH4;
            if (this.d != null) {
                z(z, z);
            }
        }
        if (typedArrayObtainStyledAttributes.hasValue(23) && this.y != (colorStateListH3 = k3.h(23))) {
            this.y = colorStateListH3;
            t();
        }
        if (typedArrayObtainStyledAttributes.hasValue(21) && this.z != (colorStateListH2 = k3.h(21))) {
            this.z = colorStateListH2;
            t();
        }
        if (typedArrayObtainStyledAttributes.hasValue(58) && this.u != (colorStateListH = k3.h(58))) {
            this.u = colorStateListH;
            C0606t3 c0606t12 = this.t;
            if (c0606t12 != null && colorStateListH != null) {
                c0606t12.setTextColor(colorStateListH);
            }
        }
        C0460ob c0460ob = new C0460ob(this, k3);
        this.c = c0460ob;
        boolean z5 = typedArrayObtainStyledAttributes.getBoolean(0, true);
        k3.o();
        setImportantForAccessibility(2);
        int i7 = Build.VERSION.SDK_INT;
        if (i7 >= 26 && i7 >= 26) {
            Bs.b(this, 1);
        }
        frameLayout.addView(po);
        frameLayout.addView(c0460ob);
        addView(frameLayout);
        setEnabled(z5);
        n(z3);
        m(z2);
        if (this.k != z4) {
            if (z4) {
                C0606t3 c0606t13 = new C0606t3(getContext(), null);
                this.o = c0606t13;
                c0606t13.setId(moe.shizuku.privileged.api.R.id._0_resource_name_obfuscated_res_0x7f0901d7);
                this.o.setMaxLines(1);
                c0371lf.a(this.o, 2);
                ((ViewGroup.MarginLayoutParams) this.o.getLayoutParams()).setMarginStart(getResources().getDimensionPixelOffset(moe.shizuku.privileged.api.R.dimen._0_resource_name_obfuscated_res_0x7f07031d));
                t();
                if (this.o != null) {
                    EditText editText7 = this.d;
                    s(editText7 != null ? editText7.getText() : null);
                }
            } else {
                c0371lf.g(this.o, 2);
                this.o = null;
            }
            this.k = z4;
        }
        if (TextUtils.isEmpty(text2)) {
            if (c0371lf.x) {
                n(false);
                return;
            }
            return;
        }
        if (!c0371lf.x) {
            n(true);
        }
        c0371lf.c();
        c0371lf.w = text2;
        c0371lf.y.setText(text2);
        int i8 = c0371lf.n;
        if (i8 != 2) {
            c0371lf.o = 2;
        }
        c0371lf.i(i8, c0371lf.o, c0371lf.h(c0371lf.y, text2));
    }

    public static void k(ViewGroup viewGroup, boolean z) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            childAt.setEnabled(z);
            if (childAt instanceof ViewGroup) {
                k((ViewGroup) childAt, z);
            }
        }
    }

    public final void A(Editable editable) {
        this.n.getClass();
        int length = editable != null ? editable.length() : 0;
        FrameLayout frameLayout = this.a;
        if (length != 0 || this.t0) {
            C0606t3 c0606t3 = this.t;
            if (c0606t3 == null || !this.s) {
                return;
            }
            c0606t3.setText((CharSequence) null);
            Aq.a(frameLayout, this.x);
            this.t.setVisibility(4);
            return;
        }
        if (this.t == null || !this.s || TextUtils.isEmpty(this.r)) {
            return;
        }
        this.t.setText(this.r);
        Aq.a(frameLayout, this.w);
        this.t.setVisibility(0);
        this.t.bringToFront();
        announceForAccessibility(this.r);
    }

    public final void B(boolean z, boolean z2) {
        int defaultColor = this.m0.getDefaultColor();
        int colorForState = this.m0.getColorForState(new int[]{R.attr.state_hovered, R.attr.state_enabled}, defaultColor);
        int colorForState2 = this.m0.getColorForState(new int[]{R.attr.state_activated, R.attr.state_enabled}, defaultColor);
        if (z) {
            this.T = colorForState2;
        } else if (z2) {
            this.T = colorForState;
        } else {
            this.T = defaultColor;
        }
    }

    public final void C() {
        C0606t3 c0606t3;
        EditText editText;
        EditText editText2;
        if (this.F == null || this.O == 0) {
            return;
        }
        boolean z = false;
        boolean z2 = isFocused() || ((editText2 = this.d) != null && editText2.hasFocus());
        if (isHovered() || ((editText = this.d) != null && editText.isHovered())) {
            z = true;
        }
        if (!isEnabled()) {
            this.T = this.r0;
        } else if (r()) {
            if (this.m0 != null) {
                B(z2, z);
            } else {
                C0606t3 c0606t4 = this.j.r;
                this.T = c0606t4 != null ? c0606t4.getCurrentTextColor() : -1;
            }
        } else if (!this.m || (c0606t3 = this.o) == null) {
            if (z2) {
                this.T = this.l0;
            } else if (z) {
                this.T = this.k0;
            } else {
                this.T = this.j0;
            }
        } else if (this.m0 != null) {
            B(z2, z);
        } else {
            this.T = c0606t3.getCurrentTextColor();
        }
        if (Build.VERSION.SDK_INT >= 29) {
            u();
        }
        C0460ob c0460ob = this.c;
        c0460ob.k();
        ColorStateList colorStateList = c0460ob.d;
        CheckableImageButton checkableImageButton = c0460ob.c;
        TextInputLayout textInputLayout = c0460ob.a;
        AbstractC0599sr.D(textInputLayout, checkableImageButton, colorStateList);
        ColorStateList colorStateList2 = c0460ob.j;
        CheckableImageButton checkableImageButton2 = c0460ob.f;
        AbstractC0599sr.D(textInputLayout, checkableImageButton2, colorStateList2);
        if (c0460ob.b() instanceof C0644ua) {
            if (!textInputLayout.r() || checkableImageButton2.getDrawable() == null) {
                AbstractC0599sr.a(textInputLayout, checkableImageButton2, c0460ob.j, c0460ob.k);
            } else {
                Drawable drawableMutate = checkableImageButton2.getDrawable().mutate();
                C0606t3 c0606t5 = textInputLayout.j.r;
                drawableMutate.setTint(c0606t5 != null ? c0606t5.getCurrentTextColor() : -1);
                checkableImageButton2.setImageDrawable(drawableMutate);
            }
        }
        Po po = this.b;
        AbstractC0599sr.D(po.a, po.d, po.e);
        if (this.O == 2) {
            int i = this.Q;
            if (z2 && isEnabled()) {
                this.Q = this.S;
            } else {
                this.Q = this.R;
            }
            if (this.Q != i && e() && !this.t0) {
                if (e()) {
                    ((C0519q8) this.F).q(0.0f, 0.0f, 0.0f, 0.0f);
                }
                j();
            }
        }
        if (this.O == 1) {
            if (!isEnabled()) {
                this.U = this.o0;
            } else if (z && !z2) {
                this.U = this.q0;
            } else if (z2) {
                this.U = this.p0;
            } else {
                this.U = this.n0;
            }
        }
        b();
    }

    public final void a(float f) {
        int i = 2;
        C0578s6 c0578s6 = this.u0;
        if (c0578s6.b == f) {
            return;
        }
        if (this.x0 == null) {
            ValueAnimator valueAnimator = new ValueAnimator();
            this.x0 = valueAnimator;
            valueAnimator.setInterpolator(AbstractC0046as.P(getContext(), moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f04034b, V1.b));
            this.x0.setDuration(AbstractC0046as.O(getContext(), moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f040341, 167));
            this.x0.addUpdateListener(new C0299j5(i, this));
        }
        this.x0.setFloatValues(c0578s6.b, f);
        this.x0.start();
    }

    @Override // android.view.ViewGroup
    public final void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        boolean z;
        if (!(view instanceof EditText)) {
            super.addView(view, i, layoutParams);
            return;
        }
        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(layoutParams);
        layoutParams2.gravity = (layoutParams2.gravity & (-113)) | 16;
        FrameLayout frameLayout = this.a;
        frameLayout.addView(view, layoutParams2);
        frameLayout.setLayoutParams(layoutParams);
        y();
        EditText editText = (EditText) view;
        if (this.d != null) {
            throw new IllegalArgumentException("We already have an EditText, can only have one");
        }
        C0460ob c0460ob = this.c;
        if (c0460ob.h != 3 && !(editText instanceof TextInputEditText)) {
            Log.i("TextInputLayout", "EditText added is not a TextInputEditText. Please switch to using that class instead.");
        }
        this.d = editText;
        int i2 = this.f;
        if (i2 != -1) {
            this.f = i2;
            if (editText != null && i2 != -1) {
                editText.setMinEms(i2);
            }
        } else {
            int i3 = this.h;
            this.h = i3;
            if (editText != null && i3 != -1) {
                editText.setMinWidth(i3);
            }
        }
        int i4 = this.g;
        if (i4 != -1) {
            this.g = i4;
            EditText editText2 = this.d;
            if (editText2 != null && i4 != -1) {
                editText2.setMaxEms(i4);
            }
        } else {
            int i5 = this.i;
            this.i = i5;
            EditText editText3 = this.d;
            if (editText3 != null && i5 != -1) {
                editText3.setMaxWidth(i5);
            }
        }
        this.I = false;
        i();
        R5 r5 = new R5(this);
        EditText editText4 = this.d;
        if (editText4 != null) {
            Hs.m(editText4, r5);
        }
        Typeface typeface = this.d.getTypeface();
        C0578s6 c0578s6 = this.u0;
        boolean zJ = c0578s6.j(typeface);
        if (c0578s6.w != typeface) {
            c0578s6.w = typeface;
            Typeface typefaceH = An.h(c0578s6.a.getContext().getResources().getConfiguration(), typeface);
            c0578s6.v = typefaceH;
            if (typefaceH == null) {
                typefaceH = c0578s6.w;
            }
            c0578s6.u = typefaceH;
            z = true;
        } else {
            z = false;
        }
        if (zJ || z) {
            c0578s6.h(false);
        }
        float textSize = this.d.getTextSize();
        if (c0578s6.h != textSize) {
            c0578s6.h = textSize;
            c0578s6.h(false);
        }
        int i6 = Build.VERSION.SDK_INT;
        float letterSpacing = this.d.getLetterSpacing();
        if (c0578s6.W != letterSpacing) {
            c0578s6.W = letterSpacing;
            c0578s6.h(false);
        }
        int gravity = this.d.getGravity();
        int i7 = (gravity & (-113)) | 48;
        if (c0578s6.g != i7) {
            c0578s6.g = i7;
            c0578s6.h(false);
        }
        if (c0578s6.f != gravity) {
            c0578s6.f = gravity;
            c0578s6.h(false);
        }
        WeakHashMap weakHashMap = Hs.a;
        this.s0 = editText.getMinimumHeight();
        this.d.addTextChangedListener(new Gp(this, editText));
        if (this.h0 == null) {
            this.h0 = this.d.getHintTextColors();
        }
        if (this.C) {
            if (TextUtils.isEmpty(this.D)) {
                CharSequence hint = this.d.getHint();
                this.e = hint;
                o(hint);
                this.d.setHint((CharSequence) null);
            }
            this.E = true;
        }
        if (i6 >= 29) {
            u();
        }
        if (this.o != null) {
            s(this.d.getText());
        }
        w();
        this.j.b();
        this.b.bringToFront();
        c0460ob.bringToFront();
        Iterator it = this.d0.iterator();
        while (it.hasNext()) {
            ((C0398mb) it.next()).a(this);
        }
        c0460ob.l();
        if (!isEnabled()) {
            editText.setEnabled(false);
        }
        z(false, true);
    }

    public final void b() {
        int i;
        int i2;
        Xh xh = this.F;
        if (xh == null) {
            return;
        }
        C0317jn c0317jn = xh.a.a;
        C0317jn c0317jn2 = this.L;
        if (c0317jn != c0317jn2) {
            xh.a(c0317jn2);
        }
        if (this.O == 2 && (i = this.Q) > -1 && (i2 = this.T) != 0) {
            Xh xh2 = this.F;
            xh2.a.j = i;
            xh2.invalidateSelf();
            ColorStateList colorStateListValueOf = ColorStateList.valueOf(i2);
            Wh wh = xh2.a;
            if (wh.d != colorStateListValueOf) {
                wh.d = colorStateListValueOf;
                xh2.onStateChange(xh2.getState());
            }
        }
        int iC = this.U;
        if (this.O == 1) {
            iC = E6.c(this.U, AbstractC0744xh.r(getContext(), moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f04011f, 0));
        }
        this.U = iC;
        this.F.l(ColorStateList.valueOf(iC));
        Xh xh3 = this.J;
        if (xh3 != null && this.K != null) {
            if (this.Q > -1 && this.T != 0) {
                xh3.l(this.d.isFocused() ? ColorStateList.valueOf(this.j0) : ColorStateList.valueOf(this.T));
                this.K.l(ColorStateList.valueOf(this.T));
            }
            invalidate();
        }
        x();
    }

    public final int c() {
        float fD;
        if (!this.C) {
            return 0;
        }
        int i = this.O;
        C0578s6 c0578s6 = this.u0;
        if (i == 0) {
            fD = c0578s6.d();
        } else {
            if (i != 2) {
                return 0;
            }
            fD = c0578s6.d() / 2.0f;
        }
        return (int) fD;
    }

    public final Hb d() {
        Hb hb = new Hb();
        hb.c = AbstractC0046as.O(getContext(), moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f040343, 87);
        hb.d = AbstractC0046as.P(getContext(), moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f04034d, V1.a);
        return hb;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchProvideAutofillStructure(ViewStructure viewStructure, int i) {
        EditText editText = this.d;
        if (editText == null) {
            super.dispatchProvideAutofillStructure(viewStructure, i);
            return;
        }
        if (this.e != null) {
            boolean z = this.E;
            this.E = false;
            CharSequence hint = editText.getHint();
            this.d.setHint(this.e);
            try {
                super.dispatchProvideAutofillStructure(viewStructure, i);
                return;
            } finally {
                this.d.setHint(hint);
                this.E = z;
            }
        }
        viewStructure.setAutofillId(getAutofillId());
        onProvideAutofillStructure(viewStructure, i);
        onProvideAutofillVirtualStructure(viewStructure, i);
        FrameLayout frameLayout = this.a;
        viewStructure.setChildCount(frameLayout.getChildCount());
        for (int i2 = 0; i2 < frameLayout.getChildCount(); i2++) {
            View childAt = frameLayout.getChildAt(i2);
            ViewStructure viewStructureNewChild = viewStructure.newChild(i2);
            childAt.dispatchProvideAutofillStructure(viewStructureNewChild, i);
            if (childAt == this.d) {
                viewStructureNewChild.setHint(this.C ? this.D : null);
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchRestoreInstanceState(SparseArray sparseArray) {
        this.z0 = true;
        super.dispatchRestoreInstanceState(sparseArray);
        this.z0 = false;
    }

    @Override // android.view.View
    public final void draw(Canvas canvas) {
        Xh xh;
        Canvas canvas2 = canvas;
        super.draw(canvas);
        boolean z = this.C;
        C0578s6 c0578s6 = this.u0;
        if (z) {
            c0578s6.getClass();
            int iSave = canvas2.save();
            if (c0578s6.B != null) {
                RectF rectF = c0578s6.e;
                if (rectF.width() > 0.0f && rectF.height() > 0.0f) {
                    TextPaint textPaint = c0578s6.N;
                    textPaint.setTextSize(c0578s6.G);
                    float f = c0578s6.p;
                    float f2 = c0578s6.q;
                    float f3 = c0578s6.F;
                    if (f3 != 1.0f) {
                        canvas2.scale(f3, f3, f, f2);
                    }
                    if (c0578s6.d0 <= 1 || c0578s6.C) {
                        canvas2.translate(f, f2);
                        c0578s6.Y.draw(canvas2);
                    } else {
                        float lineStart = c0578s6.p - c0578s6.Y.getLineStart(0);
                        int alpha = textPaint.getAlpha();
                        canvas2.translate(lineStart, f2);
                        float f4 = alpha;
                        textPaint.setAlpha((int) (c0578s6.b0 * f4));
                        int i = Build.VERSION.SDK_INT;
                        if (i >= 31) {
                            float f5 = c0578s6.H;
                            float f6 = c0578s6.I;
                            float f7 = c0578s6.J;
                            int i2 = c0578s6.K;
                            textPaint.setShadowLayer(f5, f6, f7, E6.e(i2, (textPaint.getAlpha() * Color.alpha(i2)) / 255));
                        }
                        c0578s6.Y.draw(canvas2);
                        textPaint.setAlpha((int) (c0578s6.a0 * f4));
                        if (i >= 31) {
                            float f8 = c0578s6.H;
                            float f9 = c0578s6.I;
                            float f10 = c0578s6.J;
                            int i3 = c0578s6.K;
                            textPaint.setShadowLayer(f8, f9, f10, E6.e(i3, (Color.alpha(i3) * textPaint.getAlpha()) / 255));
                        }
                        int lineBaseline = c0578s6.Y.getLineBaseline(0);
                        CharSequence charSequence = c0578s6.c0;
                        float f11 = lineBaseline;
                        canvas2.drawText(charSequence, 0, charSequence.length(), 0.0f, f11, textPaint);
                        if (i >= 31) {
                            textPaint.setShadowLayer(c0578s6.H, c0578s6.I, c0578s6.J, c0578s6.K);
                        }
                        String strTrim = c0578s6.c0.toString().trim();
                        if (strTrim.endsWith("…")) {
                            strTrim = strTrim.substring(0, strTrim.length() - 1);
                        }
                        String str = strTrim;
                        textPaint.setAlpha(alpha);
                        canvas2 = canvas;
                        canvas2.drawText(str, 0, Math.min(c0578s6.Y.getLineEnd(0), str.length()), 0.0f, f11, (Paint) textPaint);
                    }
                    canvas2.restoreToCount(iSave);
                }
            }
        }
        if (this.K == null || (xh = this.J) == null) {
            return;
        }
        xh.draw(canvas2);
        if (this.d.isFocused()) {
            Rect bounds = this.K.getBounds();
            Rect bounds2 = this.J.getBounds();
            float f12 = c0578s6.b;
            int iCenterX = bounds2.centerX();
            bounds.left = V1.c(iCenterX, bounds2.left, f12);
            bounds.right = V1.c(iCenterX, bounds2.right, f12);
            this.K.draw(canvas2);
        }
    }

    /* JADX WARN: Code duplicated, block: B:16:0x002f  */
    @Override // android.view.ViewGroup, android.view.View
    public final void drawableStateChanged() {
        boolean z;
        ColorStateList colorStateList;
        if (this.y0) {
            return;
        }
        this.y0 = true;
        super.drawableStateChanged();
        int[] drawableState = getDrawableState();
        C0578s6 c0578s6 = this.u0;
        if (c0578s6 != null) {
            c0578s6.L = drawableState;
            ColorStateList colorStateList2 = c0578s6.k;
            if ((colorStateList2 == null || !colorStateList2.isStateful()) && ((colorStateList = c0578s6.j) == null || !colorStateList.isStateful())) {
                z = false;
            } else {
                c0578s6.h(false);
                z = true;
            }
        } else {
            z = false;
        }
        if (this.d != null) {
            WeakHashMap weakHashMap = Hs.a;
            z(isLaidOut() && isEnabled(), false);
        }
        w();
        C();
        if (z) {
            invalidate();
        }
        this.y0 = false;
    }

    public final boolean e() {
        return this.C && !TextUtils.isEmpty(this.D) && (this.F instanceof C0519q8);
    }

    public final Xh f(boolean z) {
        float dimensionPixelOffset = getResources().getDimensionPixelOffset(moe.shizuku.privileged.api.R.dimen._0_resource_name_obfuscated_res_0x7f070300);
        float f = z ? dimensionPixelOffset : 0.0f;
        EditText editText = this.d;
        float dimensionPixelOffset2 = editText instanceof Eh ? ((Eh) editText).h : getResources().getDimensionPixelOffset(moe.shizuku.privileged.api.R.dimen._0_resource_name_obfuscated_res_0x7f070162);
        int dimensionPixelOffset3 = getResources().getDimensionPixelOffset(moe.shizuku.privileged.api.R.dimen._0_resource_name_obfuscated_res_0x7f0702c1);
        C0256hm c0256hm = new C0256hm();
        C0256hm c0256hm2 = new C0256hm();
        C0256hm c0256hm3 = new C0256hm();
        C0256hm c0256hm4 = new C0256hm();
        Aa aa = new Aa();
        Aa aa2 = new Aa();
        Aa aa3 = new Aa();
        Aa aa4 = new Aa();
        G g = new G(f);
        G g2 = new G(f);
        G g3 = new G(dimensionPixelOffset);
        G g4 = new G(dimensionPixelOffset);
        C0317jn c0317jn = new C0317jn();
        c0317jn.a = c0256hm;
        c0317jn.b = c0256hm2;
        c0317jn.c = c0256hm3;
        c0317jn.d = c0256hm4;
        c0317jn.e = g;
        c0317jn.f = g2;
        c0317jn.g = g4;
        c0317jn.h = g3;
        c0317jn.i = aa;
        c0317jn.j = aa2;
        c0317jn.k = aa3;
        c0317jn.l = aa4;
        EditText editText2 = this.d;
        ColorStateList colorStateListValueOf = editText2 instanceof Eh ? ((Eh) editText2).i : null;
        Context context = getContext();
        if (colorStateListValueOf == null) {
            Paint paint = Xh.x;
            TypedValue typedValueQ = AbstractC0046as.Q(context, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f04011f, Xh.class.getSimpleName());
            int i = typedValueQ.resourceId;
            colorStateListValueOf = ColorStateList.valueOf(i != 0 ? context.getColor(i) : typedValueQ.data);
        }
        Xh xh = new Xh();
        xh.j(context);
        xh.l(colorStateListValueOf);
        xh.k(dimensionPixelOffset2);
        xh.a(c0317jn);
        Wh wh = xh.a;
        if (wh.g == null) {
            wh.g = new Rect();
        }
        xh.a.g.set(0, dimensionPixelOffset3, 0, dimensionPixelOffset3);
        xh.invalidateSelf();
        return xh;
    }

    /* JADX WARN: Code duplicated, block: B:11:0x0016  */
    /* JADX WARN: Code duplicated, block: B:12:0x001b  */
    /* JADX WARN: Code duplicated, block: B:8:0x000e A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:9:0x0010  */
    public final int g(int i, boolean z) {
        int compoundPaddingLeft;
        C0460ob c0460ob;
        if (!z) {
            Po po = this.b;
            if (po.c != null) {
                compoundPaddingLeft = po.a();
            } else if (z) {
                c0460ob = this.c;
                if (c0460ob.m != null) {
                    compoundPaddingLeft = c0460ob.c();
                } else {
                    compoundPaddingLeft = this.d.getCompoundPaddingLeft();
                }
            } else {
                compoundPaddingLeft = this.d.getCompoundPaddingLeft();
            }
        } else if (z) {
            c0460ob = this.c;
            if (c0460ob.m != null) {
                compoundPaddingLeft = c0460ob.c();
            } else {
                compoundPaddingLeft = this.d.getCompoundPaddingLeft();
            }
        } else {
            compoundPaddingLeft = this.d.getCompoundPaddingLeft();
        }
        return compoundPaddingLeft + i;
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final int getBaseline() {
        EditText editText = this.d;
        if (editText == null) {
            return super.getBaseline();
        }
        return c() + getPaddingTop() + editText.getBaseline();
    }

    /* JADX WARN: Code duplicated, block: B:11:0x0016  */
    /* JADX WARN: Code duplicated, block: B:12:0x001b  */
    /* JADX WARN: Code duplicated, block: B:8:0x000e A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:9:0x0010  */
    public final int h(int i, boolean z) {
        int compoundPaddingRight;
        Po po;
        if (!z) {
            C0460ob c0460ob = this.c;
            if (c0460ob.m != null) {
                compoundPaddingRight = c0460ob.c();
            } else if (z) {
                po = this.b;
                if (po.c != null) {
                    compoundPaddingRight = po.a();
                } else {
                    compoundPaddingRight = this.d.getCompoundPaddingRight();
                }
            } else {
                compoundPaddingRight = this.d.getCompoundPaddingRight();
            }
        } else if (z) {
            po = this.b;
            if (po.c != null) {
                compoundPaddingRight = po.a();
            } else {
                compoundPaddingRight = this.d.getCompoundPaddingRight();
            }
        } else {
            compoundPaddingRight = this.d.getCompoundPaddingRight();
        }
        return i - compoundPaddingRight;
    }

    public final void i() {
        int i = this.O;
        if (i == 0) {
            this.F = null;
            this.J = null;
            this.K = null;
        } else if (i == 1) {
            this.F = new Xh(this.L);
            this.J = new Xh();
            this.K = new Xh();
        } else {
            if (i != 2) {
                throw new IllegalArgumentException(i + " is illegal; only @BoxBackgroundMode constants are supported.");
            }
            if (!this.C || (this.F instanceof C0519q8)) {
                this.F = new Xh(this.L);
            } else {
                C0317jn c0317jn = this.L;
                int i2 = C0519q8.z;
                if (c0317jn == null) {
                    c0317jn = new C0317jn(1);
                }
                C0488p8 c0488p8 = new C0488p8(c0317jn, new RectF());
                C0519q8 c0519q8 = new C0519q8(c0488p8);
                c0519q8.y = c0488p8;
                this.F = c0519q8;
            }
            this.J = null;
            this.K = null;
        }
        x();
        C();
        if (i == 1) {
            if (getContext().getResources().getConfiguration().fontScale >= 2.0f) {
                this.P = getResources().getDimensionPixelSize(moe.shizuku.privileged.api.R.dimen._0_resource_name_obfuscated_res_0x7f070258);
            } else if (X8.I(getContext())) {
                this.P = getResources().getDimensionPixelSize(moe.shizuku.privileged.api.R.dimen._0_resource_name_obfuscated_res_0x7f070257);
            }
        }
        if (this.d != null && i == 1) {
            if (getContext().getResources().getConfiguration().fontScale >= 2.0f) {
                EditText editText = this.d;
                WeakHashMap weakHashMap = Hs.a;
                editText.setPaddingRelative(editText.getPaddingStart(), getResources().getDimensionPixelSize(moe.shizuku.privileged.api.R.dimen._0_resource_name_obfuscated_res_0x7f070256), this.d.getPaddingEnd(), getResources().getDimensionPixelSize(moe.shizuku.privileged.api.R.dimen._0_resource_name_obfuscated_res_0x7f070255));
            } else if (X8.I(getContext())) {
                EditText editText2 = this.d;
                WeakHashMap weakHashMap2 = Hs.a;
                editText2.setPaddingRelative(editText2.getPaddingStart(), getResources().getDimensionPixelSize(moe.shizuku.privileged.api.R.dimen._0_resource_name_obfuscated_res_0x7f070254), this.d.getPaddingEnd(), getResources().getDimensionPixelSize(moe.shizuku.privileged.api.R.dimen._0_resource_name_obfuscated_res_0x7f070253));
            }
        }
        if (i != 0) {
            y();
        }
        EditText editText3 = this.d;
        if (editText3 instanceof AutoCompleteTextView) {
            AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) editText3;
            if (autoCompleteTextView.getDropDownBackground() == null) {
                if (i == 2) {
                    if (this.G == null) {
                        this.G = f(true);
                    }
                    autoCompleteTextView.setDropDownBackgroundDrawable(this.G);
                } else if (i == 1) {
                    if (this.H == null) {
                        StateListDrawable stateListDrawable = new StateListDrawable();
                        this.H = stateListDrawable;
                        int[] iArr = {R.attr.state_above_anchor};
                        if (this.G == null) {
                            this.G = f(true);
                        }
                        stateListDrawable.addState(iArr, this.G);
                        this.H.addState(new int[0], f(false));
                    }
                    autoCompleteTextView.setDropDownBackgroundDrawable(this.H);
                }
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:44:0x008d  */
    public final void j() {
        float f;
        float f2;
        float f3;
        RectF rectF;
        float f4;
        int i;
        float f5;
        int i2;
        if (e()) {
            int width = this.d.getWidth();
            int gravity = this.d.getGravity();
            C0578s6 c0578s6 = this.u0;
            boolean zB = c0578s6.b(c0578s6.A);
            c0578s6.C = zB;
            Rect rect = c0578s6.d;
            if (gravity != 17 && (gravity & 7) != 1) {
                if ((gravity & 8388613) == 8388613 || (gravity & 5) == 5) {
                    if (zB) {
                        i2 = rect.left;
                        f3 = i2;
                    } else {
                        f = rect.right;
                        f2 = c0578s6.Z;
                    }
                } else if (zB) {
                    f = rect.right;
                    f2 = c0578s6.Z;
                } else {
                    i2 = rect.left;
                    f3 = i2;
                }
                float fMax = Math.max(f3, rect.left);
                rectF = this.a0;
                rectF.left = fMax;
                rectF.top = rect.top;
                if (gravity != 17 || (gravity & 7) == 1) {
                    f4 = (width / 2.0f) + (c0578s6.Z / 2.0f);
                } else if ((gravity & 8388613) == 8388613 || (gravity & 5) == 5) {
                    if (c0578s6.C) {
                        f5 = c0578s6.Z;
                        f4 = f5 + fMax;
                    } else {
                        i = rect.right;
                        f4 = i;
                    }
                } else if (c0578s6.C) {
                    i = rect.right;
                    f4 = i;
                } else {
                    f5 = c0578s6.Z;
                    f4 = f5 + fMax;
                }
                rectF.right = Math.min(f4, rect.right);
                rectF.bottom = c0578s6.d() + rect.top;
                if (rectF.width() > 0.0f || rectF.height() <= 0.0f) {
                }
                float f6 = rectF.left;
                float f7 = this.N;
                rectF.left = f6 - f7;
                rectF.right += f7;
                rectF.offset(-getPaddingLeft(), ((-getPaddingTop()) - (rectF.height() / 2.0f)) + this.Q);
                C0519q8 c0519q8 = (C0519q8) this.F;
                c0519q8.getClass();
                c0519q8.q(rectF.left, rectF.top, rectF.right, rectF.bottom);
                return;
            }
            f = width / 2.0f;
            f2 = c0578s6.Z / 2.0f;
            f3 = f - f2;
            float fMax2 = Math.max(f3, rect.left);
            rectF = this.a0;
            rectF.left = fMax2;
            rectF.top = rect.top;
            if (gravity != 17) {
                f4 = (width / 2.0f) + (c0578s6.Z / 2.0f);
            } else {
                f4 = (width / 2.0f) + (c0578s6.Z / 2.0f);
            }
            rectF.right = Math.min(f4, rect.right);
            rectF.bottom = c0578s6.d() + rect.top;
            if (rectF.width() > 0.0f) {
            }
        }
    }

    public final void l(CharSequence charSequence) {
        C0371lf c0371lf = this.j;
        if (!c0371lf.q) {
            if (TextUtils.isEmpty(charSequence)) {
                return;
            } else {
                m(true);
            }
        }
        if (TextUtils.isEmpty(charSequence)) {
            c0371lf.f();
            return;
        }
        c0371lf.c();
        c0371lf.p = charSequence;
        c0371lf.r.setText(charSequence);
        int i = c0371lf.n;
        if (i != 1) {
            c0371lf.o = 1;
        }
        c0371lf.i(i, c0371lf.o, c0371lf.h(c0371lf.r, charSequence));
    }

    public final void m(boolean z) {
        C0371lf c0371lf = this.j;
        if (c0371lf.q == z) {
            return;
        }
        c0371lf.c();
        TextInputLayout textInputLayout = c0371lf.h;
        if (z) {
            C0606t3 c0606t3 = new C0606t3(c0371lf.g, null);
            c0371lf.r = c0606t3;
            c0606t3.setId(moe.shizuku.privileged.api.R.id._0_resource_name_obfuscated_res_0x7f0901d8);
            c0371lf.r.setTextAlignment(5);
            int i = c0371lf.u;
            c0371lf.u = i;
            C0606t3 c0606t4 = c0371lf.r;
            if (c0606t4 != null) {
                textInputLayout.q(c0606t4, i);
            }
            ColorStateList colorStateList = c0371lf.v;
            c0371lf.v = colorStateList;
            C0606t3 c0606t5 = c0371lf.r;
            if (c0606t5 != null && colorStateList != null) {
                c0606t5.setTextColor(colorStateList);
            }
            CharSequence charSequence = c0371lf.s;
            c0371lf.s = charSequence;
            C0606t3 c0606t6 = c0371lf.r;
            if (c0606t6 != null) {
                c0606t6.setContentDescription(charSequence);
            }
            int i2 = c0371lf.t;
            c0371lf.t = i2;
            C0606t3 c0606t7 = c0371lf.r;
            if (c0606t7 != null) {
                WeakHashMap weakHashMap = Hs.a;
                c0606t7.setAccessibilityLiveRegion(i2);
            }
            c0371lf.r.setVisibility(4);
            c0371lf.a(c0371lf.r, 0);
        } else {
            c0371lf.f();
            c0371lf.g(c0371lf.r, 0);
            c0371lf.r = null;
            textInputLayout.w();
            textInputLayout.C();
        }
        c0371lf.q = z;
    }

    public final void n(boolean z) {
        C0371lf c0371lf = this.j;
        if (c0371lf.x == z) {
            return;
        }
        c0371lf.c();
        if (z) {
            C0606t3 c0606t3 = new C0606t3(c0371lf.g, null);
            c0371lf.y = c0606t3;
            c0606t3.setId(moe.shizuku.privileged.api.R.id._0_resource_name_obfuscated_res_0x7f0901d9);
            c0371lf.y.setTextAlignment(5);
            c0371lf.y.setVisibility(4);
            c0371lf.y.setAccessibilityLiveRegion(1);
            int i = c0371lf.z;
            c0371lf.z = i;
            C0606t3 c0606t4 = c0371lf.y;
            if (c0606t4 != null) {
                c0606t4.setTextAppearance(i);
            }
            ColorStateList colorStateList = c0371lf.A;
            c0371lf.A = colorStateList;
            C0606t3 c0606t5 = c0371lf.y;
            if (c0606t5 != null && colorStateList != null) {
                c0606t5.setTextColor(colorStateList);
            }
            c0371lf.a(c0371lf.y, 1);
            c0371lf.y.setAccessibilityDelegate(new C0340kf(c0371lf));
        } else {
            c0371lf.c();
            int i2 = c0371lf.n;
            if (i2 == 2) {
                c0371lf.o = 0;
            }
            c0371lf.i(i2, c0371lf.o, c0371lf.h(c0371lf.y, ""));
            c0371lf.g(c0371lf.y, 1);
            c0371lf.y = null;
            TextInputLayout textInputLayout = c0371lf.h;
            textInputLayout.w();
            textInputLayout.C();
        }
        c0371lf.x = z;
    }

    public final void o(CharSequence charSequence) {
        if (this.C) {
            if (!TextUtils.equals(charSequence, this.D)) {
                this.D = charSequence;
                C0578s6 c0578s6 = this.u0;
                if (charSequence == null || !TextUtils.equals(c0578s6.A, charSequence)) {
                    c0578s6.A = charSequence;
                    c0578s6.B = null;
                    Bitmap bitmap = c0578s6.E;
                    if (bitmap != null) {
                        bitmap.recycle();
                        c0578s6.E = null;
                    }
                    c0578s6.h(false);
                }
                if (!this.t0) {
                    j();
                }
            }
            sendAccessibilityEvent(2048);
        }
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.u0.g(configuration);
    }

    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
    public final void onGlobalLayout() {
        int iMax;
        C0460ob c0460ob = this.c;
        c0460ob.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        boolean z = false;
        this.A0 = false;
        if (this.d != null && this.d.getMeasuredHeight() < (iMax = Math.max(c0460ob.getMeasuredHeight(), this.b.getMeasuredHeight()))) {
            this.d.setMinimumHeight(iMax);
            z = true;
        }
        boolean zV = v();
        if (z || zV) {
            this.d.post(new G0(21, this));
        }
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5 = this.O;
        super.onLayout(z, i, i2, i3, i4);
        EditText editText = this.d;
        if (editText != null) {
            ThreadLocal threadLocal = N9.a;
            int width = editText.getWidth();
            int height = editText.getHeight();
            Rect rect = this.V;
            rect.set(0, 0, width, height);
            ThreadLocal threadLocal2 = N9.a;
            Matrix matrix = (Matrix) threadLocal2.get();
            if (matrix == null) {
                matrix = new Matrix();
                threadLocal2.set(matrix);
            } else {
                matrix.reset();
            }
            N9.a(this, editText, matrix);
            ThreadLocal threadLocal3 = N9.b;
            RectF rectF = (RectF) threadLocal3.get();
            if (rectF == null) {
                rectF = new RectF();
                threadLocal3.set(rectF);
            }
            rectF.set(rect);
            matrix.mapRect(rectF);
            rect.set((int) (rectF.left + 0.5f), (int) (rectF.top + 0.5f), (int) (rectF.right + 0.5f), (int) (rectF.bottom + 0.5f));
            Xh xh = this.J;
            if (xh != null) {
                int i6 = rect.bottom;
                xh.setBounds(rect.left, i6 - this.R, rect.right, i6);
            }
            Xh xh2 = this.K;
            if (xh2 != null) {
                int i7 = rect.bottom;
                xh2.setBounds(rect.left, i7 - this.S, rect.right, i7);
            }
            if (this.C) {
                float textSize = this.d.getTextSize();
                C0578s6 c0578s6 = this.u0;
                if (c0578s6.h != textSize) {
                    c0578s6.h = textSize;
                    c0578s6.h(false);
                }
                int gravity = this.d.getGravity();
                int i8 = (gravity & (-113)) | 48;
                if (c0578s6.g != i8) {
                    c0578s6.g = i8;
                    c0578s6.h(false);
                }
                if (c0578s6.f != gravity) {
                    c0578s6.f = gravity;
                    c0578s6.h(false);
                }
                if (this.d == null) {
                    throw new IllegalStateException();
                }
                boolean zM = AbstractC0750xn.m(this);
                int i9 = rect.bottom;
                Rect rect2 = this.W;
                rect2.bottom = i9;
                if (i5 == 1) {
                    rect2.left = g(rect.left, zM);
                    rect2.top = rect.top + this.P;
                    rect2.right = h(rect.right, zM);
                } else if (i5 != 2) {
                    rect2.left = g(rect.left, zM);
                    rect2.top = getPaddingTop();
                    rect2.right = h(rect.right, zM);
                } else {
                    rect2.left = this.d.getPaddingLeft() + rect.left;
                    rect2.top = rect.top - c();
                    rect2.right = rect.right - this.d.getPaddingRight();
                }
                int i10 = rect2.left;
                int i11 = rect2.top;
                int i12 = rect2.right;
                int i13 = rect2.bottom;
                Rect rect3 = c0578s6.d;
                if (rect3.left != i10 || rect3.top != i11 || rect3.right != i12 || rect3.bottom != i13) {
                    rect3.set(i10, i11, i12, i13);
                    c0578s6.M = true;
                }
                if (this.d == null) {
                    throw new IllegalStateException();
                }
                TextPaint textPaint = c0578s6.O;
                textPaint.setTextSize(c0578s6.h);
                textPaint.setTypeface(c0578s6.u);
                textPaint.setLetterSpacing(c0578s6.W);
                float f = -textPaint.ascent();
                rect2.left = this.d.getCompoundPaddingLeft() + rect.left;
                rect2.top = (i5 != 1 || this.d.getMinLines() > 1) ? rect.top + this.d.getCompoundPaddingTop() : (int) (rect.centerY() - (f / 2.0f));
                rect2.right = rect.right - this.d.getCompoundPaddingRight();
                int compoundPaddingBottom = (i5 != 1 || this.d.getMinLines() > 1) ? rect.bottom - this.d.getCompoundPaddingBottom() : (int) (rect2.top + f);
                rect2.bottom = compoundPaddingBottom;
                int i14 = rect2.left;
                int i15 = rect2.top;
                int i16 = rect2.right;
                Rect rect4 = c0578s6.c;
                if (rect4.left != i14 || rect4.top != i15 || rect4.right != i16 || rect4.bottom != compoundPaddingBottom) {
                    rect4.set(i14, i15, i16, compoundPaddingBottom);
                    c0578s6.M = true;
                }
                c0578s6.h(false);
                if (!e() || this.t0) {
                    return;
                }
                j();
            }
        }
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        EditText editText;
        super.onMeasure(i, i2);
        boolean z = this.A0;
        C0460ob c0460ob = this.c;
        if (!z) {
            c0460ob.getViewTreeObserver().addOnGlobalLayoutListener(this);
            this.A0 = true;
        }
        if (this.t != null && (editText = this.d) != null) {
            this.t.setGravity(editText.getGravity());
            this.t.setPadding(this.d.getCompoundPaddingLeft(), this.d.getCompoundPaddingTop(), this.d.getCompoundPaddingRight(), this.d.getCompoundPaddingBottom());
        }
        c0460ob.l();
    }

    @Override // android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof Hp)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        Hp hp = (Hp) parcelable;
        super.onRestoreInstanceState(hp.a);
        l(hp.c);
        if (hp.d) {
            post(new RunnableC0607t4(15, this));
        }
        requestLayout();
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
        boolean z = i == 1;
        if (z != this.M) {
            S7 s7 = this.L.e;
            RectF rectF = this.a0;
            float fA = s7.a(rectF);
            float fA2 = this.L.f.a(rectF);
            float fA3 = this.L.h.a(rectF);
            float fA4 = this.L.g.a(rectF);
            C0317jn c0317jn = this.L;
            AbstractC0046as abstractC0046as = c0317jn.a;
            AbstractC0046as abstractC0046as2 = c0317jn.b;
            AbstractC0046as abstractC0046as3 = c0317jn.d;
            AbstractC0046as abstractC0046as4 = c0317jn.c;
            Aa aa = new Aa();
            Aa aa2 = new Aa();
            Aa aa3 = new Aa();
            Aa aa4 = new Aa();
            C0317jn.d(abstractC0046as2);
            C0317jn.d(abstractC0046as);
            C0317jn.d(abstractC0046as4);
            C0317jn.d(abstractC0046as3);
            G g = new G(fA2);
            G g2 = new G(fA);
            G g3 = new G(fA4);
            G g4 = new G(fA3);
            C0317jn c0317jn2 = new C0317jn();
            c0317jn2.a = abstractC0046as2;
            c0317jn2.b = abstractC0046as;
            c0317jn2.c = abstractC0046as3;
            c0317jn2.d = abstractC0046as4;
            c0317jn2.e = g;
            c0317jn2.f = g2;
            c0317jn2.g = g4;
            c0317jn2.h = g3;
            c0317jn2.i = aa;
            c0317jn2.j = aa2;
            c0317jn2.k = aa3;
            c0317jn2.l = aa4;
            this.M = z;
            Xh xh = this.F;
            if (xh == null || xh.a.a == c0317jn2) {
                return;
            }
            this.L = c0317jn2;
            b();
        }
    }

    @Override // android.view.View
    public final Parcelable onSaveInstanceState() {
        Hp hp = new Hp(super.onSaveInstanceState());
        if (r()) {
            C0371lf c0371lf = this.j;
            hp.c = c0371lf.q ? c0371lf.p : null;
        }
        C0460ob c0460ob = this.c;
        hp.d = c0460ob.h != 0 && c0460ob.f.d;
        return hp;
    }

    public final void p(boolean z) {
        if (this.s == z) {
            return;
        }
        if (z) {
            C0606t3 c0606t3 = this.t;
            if (c0606t3 != null) {
                this.a.addView(c0606t3);
                this.t.setVisibility(0);
            }
        } else {
            C0606t3 c0606t4 = this.t;
            if (c0606t4 != null) {
                c0606t4.setVisibility(8);
            }
            this.t = null;
        }
        this.s = z;
    }

    public final void q(C0606t3 c0606t3, int i) {
        try {
            c0606t3.setTextAppearance(i);
            if (c0606t3.getTextColors().getDefaultColor() != -65281) {
                return;
            }
        } catch (Exception unused) {
        }
        c0606t3.setTextAppearance(moe.shizuku.privileged.api.R.style._0_resource_name_obfuscated_res_0x7f1201e3);
        c0606t3.setTextColor(getContext().getColor(moe.shizuku.privileged.api.R.color._0_resource_name_obfuscated_res_0x7f060053));
    }

    public final boolean r() {
        C0371lf c0371lf = this.j;
        return (c0371lf.o != 1 || c0371lf.r == null || TextUtils.isEmpty(c0371lf.p)) ? false : true;
    }

    public final void s(Editable editable) {
        String str;
        int i = this.l;
        C0606t3 c0606t3 = this.o;
        this.n.getClass();
        int length = editable != null ? editable.length() : 0;
        boolean z = this.m;
        String string = null;
        if (i == -1) {
            c0606t3.setText(String.valueOf(length));
            c0606t3.setContentDescription(null);
            this.m = false;
        } else {
            this.m = length > i;
            c0606t3.setContentDescription(getContext().getString(this.m ? moe.shizuku.privileged.api.R.string._0_resource_name_obfuscated_res_0x7f110043 : moe.shizuku.privileged.api.R.string._0_resource_name_obfuscated_res_0x7f110042, Integer.valueOf(length), Integer.valueOf(i)));
            if (z != this.m) {
                t();
            }
            String str2 = C0054b5.b;
            C0054b5 c0054b5 = TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == 1 ? C0054b5.e : C0054b5.d;
            String string2 = getContext().getString(moe.shizuku.privileged.api.R.string._0_resource_name_obfuscated_res_0x7f110044, Integer.valueOf(length), Integer.valueOf(i));
            c0054b5.getClass();
            if (string2 != null) {
                boolean zD = Dp.c.d(string2, string2.length());
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
                String str3 = C0054b5.c;
                String str4 = C0054b5.b;
                boolean zD2 = (zD ? Dp.b : Dp.a).d(string2, string2.length());
                boolean z2 = c0054b5.a;
                if (z2 || !(zD2 || C0054b5.a(string2) == 1)) {
                    str = (!z2 || (zD2 && C0054b5.a(string2) != -1)) ? "" : str3;
                } else {
                    str = str4;
                }
                spannableStringBuilder.append((CharSequence) str);
                if (zD != z2) {
                    spannableStringBuilder.append(zD ? (char) 8235 : (char) 8234);
                    spannableStringBuilder.append((CharSequence) string2);
                    spannableStringBuilder.append((char) 8236);
                } else {
                    spannableStringBuilder.append((CharSequence) string2);
                }
                boolean zD3 = (zD ? Dp.b : Dp.a).d(string2, string2.length());
                if (!z2 && (zD3 || C0054b5.b(string2) == 1)) {
                    str3 = str4;
                } else if (!z2 || (zD3 && C0054b5.b(string2) != -1)) {
                    str3 = "";
                }
                spannableStringBuilder.append((CharSequence) str3);
                string = spannableStringBuilder.toString();
            }
            c0606t3.setText(string);
        }
        if (this.d == null || z == this.m) {
            return;
        }
        z(false, false);
        C();
        w();
    }

    @Override // android.view.View
    public final void setEnabled(boolean z) {
        k(this, z);
        super.setEnabled(z);
    }

    public final void t() {
        ColorStateList colorStateList;
        ColorStateList colorStateList2;
        C0606t3 c0606t3 = this.o;
        if (c0606t3 != null) {
            q(c0606t3, this.m ? this.p : this.q);
            if (!this.m && (colorStateList2 = this.y) != null) {
                this.o.setTextColor(colorStateList2);
            }
            if (!this.m || (colorStateList = this.z) == null) {
                return;
            }
            this.o.setTextColor(colorStateList);
        }
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    public final void u() {
        ColorStateList colorStateList;
        ColorStateList colorStateListValueOf = this.A;
        if (colorStateListValueOf == null) {
            Context context = getContext();
            TypedValue typedValueM = AbstractC0046as.M(context, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f0400f4);
            if (typedValueM != null) {
                int i = typedValueM.resourceId;
                if (i != 0) {
                    colorStateListValueOf = AbstractC0599sr.o(context, i);
                } else {
                    int i2 = typedValueM.data;
                    if (i2 != 0) {
                        colorStateListValueOf = ColorStateList.valueOf(i2);
                    } else {
                        colorStateListValueOf = null;
                    }
                }
            } else {
                colorStateListValueOf = null;
            }
        }
        EditText editText = this.d;
        if (editText == null || editText.getTextCursorDrawable() == null) {
            return;
        }
        Drawable drawableMutate = this.d.getTextCursorDrawable().mutate();
        if ((r() || (this.o != null && this.m)) && (colorStateList = this.B) != null) {
            colorStateListValueOf = colorStateList;
        }
        drawableMutate.setTintList(colorStateListValueOf);
    }

    /* JADX WARN: Code duplicated, block: B:25:0x0076  */
    public final boolean v() {
        boolean z;
        if (this.d == null) {
            return false;
        }
        Po po = this.b;
        CheckableImageButton checkableImageButton = null;
        boolean z2 = true;
        if ((po.d.getDrawable() != null || (po.c != null && po.b.getVisibility() == 0)) && po.getMeasuredWidth() > 0) {
            int measuredWidth = po.getMeasuredWidth() - this.d.getPaddingLeft();
            if (this.b0 == null || this.c0 != measuredWidth) {
                ColorDrawable colorDrawable = new ColorDrawable();
                this.b0 = colorDrawable;
                this.c0 = measuredWidth;
                colorDrawable.setBounds(0, 0, measuredWidth, 1);
            }
            Drawable[] compoundDrawablesRelative = this.d.getCompoundDrawablesRelative();
            Drawable drawable = compoundDrawablesRelative[0];
            ColorDrawable colorDrawable2 = this.b0;
            if (drawable != colorDrawable2) {
                this.d.setCompoundDrawablesRelative(colorDrawable2, compoundDrawablesRelative[1], compoundDrawablesRelative[2], compoundDrawablesRelative[3]);
                z = true;
            } else {
                z = false;
            }
        } else if (this.b0 != null) {
            Drawable[] compoundDrawablesRelative2 = this.d.getCompoundDrawablesRelative();
            this.d.setCompoundDrawablesRelative(null, compoundDrawablesRelative2[1], compoundDrawablesRelative2[2], compoundDrawablesRelative2[3]);
            this.b0 = null;
            z = true;
        } else {
            z = false;
        }
        C0460ob c0460ob = this.c;
        if ((c0460ob.e() || ((c0460ob.h != 0 && c0460ob.d()) || c0460ob.m != null)) && c0460ob.getMeasuredWidth() > 0) {
            int measuredWidth2 = c0460ob.n.getMeasuredWidth() - this.d.getPaddingRight();
            if (c0460ob.e()) {
                checkableImageButton = c0460ob.c;
            } else if (c0460ob.h != 0 && c0460ob.d()) {
                checkableImageButton = c0460ob.f;
            }
            if (checkableImageButton != null) {
                measuredWidth2 = ((ViewGroup.MarginLayoutParams) checkableImageButton.getLayoutParams()).getMarginStart() + checkableImageButton.getMeasuredWidth() + measuredWidth2;
            }
            Drawable[] compoundDrawablesRelative3 = this.d.getCompoundDrawablesRelative();
            ColorDrawable colorDrawable3 = this.e0;
            if (colorDrawable3 != null && this.f0 != measuredWidth2) {
                this.f0 = measuredWidth2;
                colorDrawable3.setBounds(0, 0, measuredWidth2, 1);
                this.d.setCompoundDrawablesRelative(compoundDrawablesRelative3[0], compoundDrawablesRelative3[1], this.e0, compoundDrawablesRelative3[3]);
                return true;
            }
            if (colorDrawable3 == null) {
                ColorDrawable colorDrawable4 = new ColorDrawable();
                this.e0 = colorDrawable4;
                this.f0 = measuredWidth2;
                colorDrawable4.setBounds(0, 0, measuredWidth2, 1);
            }
            Drawable drawable2 = compoundDrawablesRelative3[2];
            ColorDrawable colorDrawable5 = this.e0;
            if (drawable2 != colorDrawable5) {
                this.g0 = drawable2;
                this.d.setCompoundDrawablesRelative(compoundDrawablesRelative3[0], compoundDrawablesRelative3[1], colorDrawable5, compoundDrawablesRelative3[3]);
                return true;
            }
        } else if (this.e0 != null) {
            Drawable[] compoundDrawablesRelative4 = this.d.getCompoundDrawablesRelative();
            if (compoundDrawablesRelative4[2] == this.e0) {
                this.d.setCompoundDrawablesRelative(compoundDrawablesRelative4[0], compoundDrawablesRelative4[1], this.g0, compoundDrawablesRelative4[3]);
            } else {
                z2 = z;
            }
            this.e0 = null;
            return z2;
        }
        return z;
    }

    public final void w() {
        Drawable background;
        C0606t3 c0606t3;
        EditText editText = this.d;
        if (editText == null || this.O != 0 || (background = editText.getBackground()) == null) {
            return;
        }
        int[] iArr = AbstractC0304ja.a;
        Drawable drawableMutate = background.mutate();
        if (r()) {
            C0606t3 c0606t4 = this.j.r;
            drawableMutate.setColorFilter(L2.c(c0606t4 != null ? c0606t4.getCurrentTextColor() : -1, PorterDuff.Mode.SRC_IN));
        } else if (this.m && (c0606t3 = this.o) != null) {
            drawableMutate.setColorFilter(L2.c(c0606t3.getCurrentTextColor(), PorterDuff.Mode.SRC_IN));
        } else {
            drawableMutate.clearColorFilter();
            this.d.refreshDrawableState();
        }
    }

    public final void x() {
        Drawable rippleDrawable;
        int i = this.O;
        EditText editText = this.d;
        if (editText == null || this.F == null) {
            return;
        }
        if ((this.I || editText.getBackground() == null) && i != 0) {
            EditText editText2 = this.d;
            if (!(editText2 instanceof AutoCompleteTextView) || X8.H(editText2)) {
                rippleDrawable = this.F;
            } else {
                int iS = AbstractC0744xh.s(this.d, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f0400f5);
                int[][] iArr = B0;
                if (i == 2) {
                    Context context = getContext();
                    Xh xh = this.F;
                    TypedValue typedValueQ = AbstractC0046as.Q(context, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f04011f, "TextInputLayout");
                    int i2 = typedValueQ.resourceId;
                    int color = i2 != 0 ? context.getColor(i2) : typedValueQ.data;
                    Xh xh2 = new Xh(xh.a.a);
                    int iH = AbstractC0744xh.H(iS, color, 0.1f);
                    xh2.l(new ColorStateList(iArr, new int[]{iH, 0}));
                    xh2.setTint(color);
                    ColorStateList colorStateList = new ColorStateList(iArr, new int[]{iH, color});
                    Xh xh3 = new Xh(xh.a.a);
                    xh3.setTint(-1);
                    rippleDrawable = new LayerDrawable(new Drawable[]{new RippleDrawable(colorStateList, xh2, xh3), xh});
                } else if (i == 1) {
                    Xh xh4 = this.F;
                    int i3 = this.U;
                    rippleDrawable = new RippleDrawable(new ColorStateList(iArr, new int[]{AbstractC0744xh.H(iS, i3, 0.1f), i3}), xh4, xh4);
                } else {
                    rippleDrawable = null;
                }
            }
            EditText editText3 = this.d;
            WeakHashMap weakHashMap = Hs.a;
            editText3.setBackground(rippleDrawable);
            this.I = true;
        }
    }

    public final void y() {
        if (this.O != 1) {
            FrameLayout frameLayout = this.a;
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) frameLayout.getLayoutParams();
            int iC = c();
            if (iC != layoutParams.topMargin) {
                layoutParams.topMargin = iC;
                frameLayout.requestLayout();
            }
        }
    }

    public final void z(boolean z, boolean z2) {
        ColorStateList colorStateList;
        C0606t3 c0606t3;
        boolean zIsEnabled = isEnabled();
        EditText editText = this.d;
        boolean z3 = (editText == null || TextUtils.isEmpty(editText.getText())) ? false : true;
        EditText editText2 = this.d;
        boolean z4 = editText2 != null && editText2.hasFocus();
        ColorStateList colorStateList2 = this.h0;
        C0578s6 c0578s6 = this.u0;
        if (colorStateList2 != null) {
            c0578s6.i(colorStateList2);
        }
        if (!zIsEnabled) {
            ColorStateList colorStateList3 = this.h0;
            c0578s6.i(ColorStateList.valueOf(colorStateList3 != null ? colorStateList3.getColorForState(new int[]{-16842910}, this.r0) : this.r0));
        } else if (r()) {
            C0606t3 c0606t4 = this.j.r;
            c0578s6.i(c0606t4 != null ? c0606t4.getTextColors() : null);
        } else if (this.m && (c0606t3 = this.o) != null) {
            c0578s6.i(c0606t3.getTextColors());
        } else if (z4 && (colorStateList = this.i0) != null && c0578s6.k != colorStateList) {
            c0578s6.k = colorStateList;
            c0578s6.h(false);
        }
        boolean z5 = this.w0;
        C0460ob c0460ob = this.c;
        Po po = this.b;
        if (z3 || !this.v0 || (isEnabled() && z4)) {
            if (z2 || this.t0) {
                ValueAnimator valueAnimator = this.x0;
                if (valueAnimator != null && valueAnimator.isRunning()) {
                    this.x0.cancel();
                }
                if (z && z5) {
                    a(1.0f);
                } else {
                    c0578s6.k(1.0f);
                }
                this.t0 = false;
                if (e()) {
                    j();
                }
                EditText editText3 = this.d;
                A(editText3 != null ? editText3.getText() : null);
                po.h = false;
                po.c();
                c0460ob.o = false;
                c0460ob.m();
                return;
            }
            return;
        }
        if (z2 || !this.t0) {
            ValueAnimator valueAnimator2 = this.x0;
            if (valueAnimator2 != null && valueAnimator2.isRunning()) {
                this.x0.cancel();
            }
            if (z && z5) {
                a(0.0f);
            } else {
                c0578s6.k(0.0f);
            }
            if (e() && !((C0519q8) this.F).y.q.isEmpty() && e()) {
                ((C0519q8) this.F).q(0.0f, 0.0f, 0.0f, 0.0f);
            }
            this.t0 = true;
            C0606t3 c0606t5 = this.t;
            if (c0606t5 != null && this.s) {
                c0606t5.setText((CharSequence) null);
                Aq.a(this.a, this.x);
                this.t.setVisibility(4);
            }
            po.h = true;
            po.c();
            c0460ob.o = true;
            c0460ob.m();
        }
    }
}
