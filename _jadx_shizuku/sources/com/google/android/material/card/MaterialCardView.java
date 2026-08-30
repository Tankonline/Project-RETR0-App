package com.google.android.material.card;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Checkable;
import java.util.WeakHashMap;
import rikka.shizuku.AbstractC0133dm;
import rikka.shizuku.AbstractC0225gm;
import rikka.shizuku.AbstractC0599sr;
import rikka.shizuku.AbstractC0744xh;
import rikka.shizuku.AbstractC0794z5;
import rikka.shizuku.C0194fm;
import rikka.shizuku.C0317jn;
import rikka.shizuku.Hs;
import rikka.shizuku.InterfaceC0657un;
import rikka.shizuku.Lk;
import rikka.shizuku.Qh;
import rikka.shizuku.R2;
import rikka.shizuku.Wh;
import rikka.shizuku.X8;
import rikka.shizuku.Xh;

/* JADX INFO: loaded from: classes.dex */
public class MaterialCardView extends AbstractC0794z5 implements Checkable, InterfaceC0657un {
    public static final int[] j = {R.attr.state_checkable};
    public static final int[] k = {R.attr.state_checked};
    public final Qh g;
    public final boolean h;
    public boolean i;

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$ArrayArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public MaterialCardView(Context context, AttributeSet attributeSet) {
        super(X8.c0(context, attributeSet, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f040305, moe.shizuku.privileged.api.R.style._0_resource_name_obfuscated_res_0x7f120471), attributeSet);
        this.i = false;
        this.h = true;
        TypedArray typedArrayJ = X8.J(getContext(), attributeSet, Lk.r, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f040305, moe.shizuku.privileged.api.R.style._0_resource_name_obfuscated_res_0x7f120471, new int[0]);
        Qh qh = new Qh(this, attributeSet);
        this.g = qh;
        ColorStateList colorStateList = ((C0194fm) ((Drawable) this.e.b)).h;
        Xh xh = qh.c;
        xh.l(colorStateList);
        Rect rect = this.c;
        int i = rect.left;
        int i2 = rect.top;
        int i3 = rect.right;
        int i4 = rect.bottom;
        Rect rect2 = qh.b;
        rect2.set(i, i2, i3, i4);
        MaterialCardView materialCardView = qh.a;
        float f = 0.0f;
        float fA = ((!materialCardView.b || xh.a.a.f(xh.g())) && !qh.g()) ? 0.0f : qh.a();
        R2 r2 = materialCardView.e;
        if (materialCardView.b && materialCardView.a) {
            f = (float) ((1.0d - Qh.y) * ((double) ((C0194fm) ((Drawable) r2.b)).a));
        }
        int i5 = (int) (fA - f);
        materialCardView.c.set(rect2.left + i5, rect2.top + i5, rect2.right + i5, rect2.bottom + i5);
        AbstractC0794z5 abstractC0794z5 = (AbstractC0794z5) r2.c;
        if (abstractC0794z5.a) {
            C0194fm c0194fm = (C0194fm) ((Drawable) r2.b);
            float f2 = c0194fm.e;
            boolean z = abstractC0794z5.b;
            float f3 = c0194fm.a;
            int iCeil = (int) Math.ceil(AbstractC0225gm.a(f2, f3, z));
            int iCeil2 = (int) Math.ceil(AbstractC0225gm.b(f2, f3, ((AbstractC0794z5) r2.c).b));
            r2.Y(iCeil, iCeil2, iCeil, iCeil2);
        } else {
            r2.Y(0, 0, 0, 0);
        }
        ColorStateList colorStateListW = X8.w(materialCardView.getContext(), typedArrayJ, 11);
        qh.n = colorStateListW;
        if (colorStateListW == null) {
            qh.n = ColorStateList.valueOf(-1);
        }
        qh.h = typedArrayJ.getDimensionPixelSize(12, 0);
        boolean z2 = typedArrayJ.getBoolean(0, false);
        qh.s = z2;
        materialCardView.setLongClickable(z2);
        qh.l = X8.w(materialCardView.getContext(), typedArrayJ, 6);
        Drawable drawableZ = X8.z(materialCardView.getContext(), typedArrayJ, 2);
        if (drawableZ != null) {
            Drawable drawableMutate = drawableZ.mutate();
            qh.j = drawableMutate;
            drawableMutate.setTintList(qh.l);
            qh.e(materialCardView.i, false);
        } else {
            qh.j = Qh.z;
        }
        LayerDrawable layerDrawable = qh.p;
        if (layerDrawable != null) {
            layerDrawable.setDrawableByLayerId(moe.shizuku.privileged.api.R.id._0_resource_name_obfuscated_res_0x7f090117, qh.j);
        }
        qh.f = typedArrayJ.getDimensionPixelSize(5, 0);
        qh.e = typedArrayJ.getDimensionPixelSize(4, 0);
        qh.g = typedArrayJ.getInteger(3, 8388661);
        ColorStateList colorStateListW2 = X8.w(materialCardView.getContext(), typedArrayJ, 7);
        qh.k = colorStateListW2;
        if (colorStateListW2 == null) {
            qh.k = ColorStateList.valueOf(AbstractC0744xh.s(materialCardView, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f0400f5));
        }
        ColorStateList colorStateListW3 = X8.w(materialCardView.getContext(), typedArrayJ, 1);
        colorStateListW3 = colorStateListW3 == null ? ColorStateList.valueOf(0) : colorStateListW3;
        Xh xh2 = qh.d;
        xh2.l(colorStateListW3);
        int[] iArr = AbstractC0133dm.a;
        RippleDrawable rippleDrawable = qh.o;
        if (rippleDrawable != null) {
            rippleDrawable.setColor(qh.k);
        }
        xh.k(((AbstractC0794z5) materialCardView.e.c).getElevation());
        float f4 = qh.h;
        ColorStateList colorStateList2 = qh.n;
        xh2.a.j = f4;
        xh2.invalidateSelf();
        Wh wh = xh2.a;
        if (wh.d != colorStateList2) {
            wh.d = colorStateList2;
            xh2.onStateChange(xh2.getState());
        }
        super.setBackgroundDrawable(qh.d(xh));
        Drawable drawableC = qh.h() ? qh.c() : xh2;
        qh.i = drawableC;
        materialCardView.setForeground(qh.d(drawableC));
        typedArrayJ.recycle();
    }

    @Override // rikka.shizuku.InterfaceC0657un
    public final void a(C0317jn c0317jn) {
        RectF rectF = new RectF();
        Qh qh = this.g;
        rectF.set(qh.c.getBounds());
        setClipToOutline(c0317jn.f(rectF));
        qh.f(c0317jn);
    }

    @Override // android.widget.Checkable
    public final boolean isChecked() {
        return this.i;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        Qh qh = this.g;
        qh.i();
        AbstractC0599sr.H(this, qh.c);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final int[] onCreateDrawableState(int i) {
        int[] iArrOnCreateDrawableState = super.onCreateDrawableState(i + 3);
        Qh qh = this.g;
        if (qh != null && qh.s) {
            View.mergeDrawableStates(iArrOnCreateDrawableState, j);
        }
        if (this.i) {
            View.mergeDrawableStates(iArrOnCreateDrawableState, k);
        }
        return iArrOnCreateDrawableState;
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName("androidx.cardview.widget.CardView");
        accessibilityEvent.setChecked(this.i);
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName("androidx.cardview.widget.CardView");
        Qh qh = this.g;
        accessibilityNodeInfo.setCheckable(qh != null && qh.s);
        accessibilityNodeInfo.setClickable(isClickable());
        accessibilityNodeInfo.setChecked(this.i);
    }

    @Override // rikka.shizuku.AbstractC0794z5, android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        int iCeil;
        int iCeil2;
        int i3;
        int i4;
        super.onMeasure(i, i2);
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        Qh qh = this.g;
        if (qh.p != null) {
            MaterialCardView materialCardView = qh.a;
            if (materialCardView.a) {
                iCeil = (int) Math.ceil(((((C0194fm) ((Drawable) materialCardView.e.b)).e * 1.5f) + (qh.g() ? qh.a() : 0.0f)) * 2.0f);
                iCeil2 = (int) Math.ceil((((C0194fm) ((Drawable) materialCardView.e.b)).e + (qh.g() ? qh.a() : 0.0f)) * 2.0f);
            } else {
                iCeil = 0;
                iCeil2 = 0;
            }
            int i5 = qh.g;
            int i6 = (i5 & 8388613) == 8388613 ? ((measuredWidth - qh.e) - qh.f) - iCeil2 : qh.e;
            int i7 = (i5 & 80) == 80 ? qh.e : ((measuredHeight - qh.e) - qh.f) - iCeil;
            int i8 = (i5 & 8388613) == 8388613 ? qh.e : ((measuredWidth - qh.e) - qh.f) - iCeil2;
            int i9 = (i5 & 80) == 80 ? ((measuredHeight - qh.e) - qh.f) - iCeil : qh.e;
            WeakHashMap weakHashMap = Hs.a;
            if (materialCardView.getLayoutDirection() == 1) {
                i4 = i8;
                i3 = i6;
            } else {
                i3 = i8;
                i4 = i6;
            }
            qh.p.setLayerInset(2, i4, i9, i3, i7);
        }
    }

    @Override // android.view.View
    public final void setBackground(Drawable drawable) {
        setBackgroundDrawable(drawable);
    }

    @Override // android.view.View
    public final void setBackgroundDrawable(Drawable drawable) {
        if (this.h) {
            Qh qh = this.g;
            if (!qh.r) {
                Log.i("MaterialCardView", "Setting a custom background is not supported.");
                qh.r = true;
            }
            super.setBackgroundDrawable(drawable);
        }
    }

    @Override // android.widget.Checkable
    public final void setChecked(boolean z) {
        if (this.i != z) {
            toggle();
        }
    }

    @Override // android.view.View
    public final void setClickable(boolean z) {
        super.setClickable(z);
        Qh qh = this.g;
        if (qh != null) {
            qh.i();
        }
    }

    @Override // android.widget.Checkable
    public final void toggle() {
        RippleDrawable rippleDrawable;
        Qh qh = this.g;
        if (qh != null && qh.s && isEnabled()) {
            this.i = !this.i;
            refreshDrawableState();
            if (Build.VERSION.SDK_INT > 26 && (rippleDrawable = qh.o) != null) {
                Rect bounds = rippleDrawable.getBounds();
                int i = bounds.bottom;
                qh.o.setBounds(bounds.left, bounds.top, bounds.right, i - 1);
                qh.o.setBounds(bounds.left, bounds.top, bounds.right, i);
            }
            qh.e(this.i, true);
        }
    }
}
