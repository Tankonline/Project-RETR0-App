package rikka.shizuku;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import com.google.android.material.card.MaterialCardView;
import moe.shizuku.privileged.api.R;

/* JADX INFO: loaded from: classes.dex */
public final class Qh {
    public static final double y = Math.cos(Math.toRadians(45.0d));
    public static final ColorDrawable z;
    public final MaterialCardView a;
    public final Xh c;
    public final Xh d;
    public int e;
    public int f;
    public int g;
    public int h;
    public Drawable i;
    public Drawable j;
    public ColorStateList k;
    public ColorStateList l;
    public C0317jn m;
    public ColorStateList n;
    public RippleDrawable o;
    public LayerDrawable p;
    public Xh q;
    public boolean s;
    public ValueAnimator t;
    public final TimeInterpolator u;
    public final int v;
    public final int w;
    public final Rect b = new Rect();
    public boolean r = false;
    public float x = 0.0f;

    static {
        z = Build.VERSION.SDK_INT <= 28 ? new ColorDrawable() : null;
    }

    public Qh(MaterialCardView materialCardView, AttributeSet attributeSet) {
        this.a = materialCardView;
        Xh xh = new Xh(materialCardView.getContext(), attributeSet, R.attr._0_resource_name_obfuscated_res_0x7f040305, R.style._0_resource_name_obfuscated_res_0x7f120471);
        this.c = xh;
        xh.j(materialCardView.getContext());
        xh.m();
        C0317jn c0317jnG = xh.a.a.g();
        TypedArray typedArrayObtainStyledAttributes = materialCardView.getContext().obtainStyledAttributes(attributeSet, Lk.d, R.attr._0_resource_name_obfuscated_res_0x7f040305, R.style._0_resource_name_obfuscated_res_0x7f12013b);
        if (typedArrayObtainStyledAttributes.hasValue(3)) {
            float dimension = typedArrayObtainStyledAttributes.getDimension(3, 0.0f);
            c0317jnG.e = new G(dimension);
            c0317jnG.f = new G(dimension);
            c0317jnG.g = new G(dimension);
            c0317jnG.h = new G(dimension);
        }
        this.d = new Xh();
        f(c0317jnG.a());
        this.u = AbstractC0046as.P(materialCardView.getContext(), R.attr._0_resource_name_obfuscated_res_0x7f04034d, V1.a);
        this.v = AbstractC0046as.O(materialCardView.getContext(), R.attr._0_resource_name_obfuscated_res_0x7f040343, 300);
        this.w = AbstractC0046as.O(materialCardView.getContext(), R.attr._0_resource_name_obfuscated_res_0x7f040342, 300);
        typedArrayObtainStyledAttributes.recycle();
    }

    public static float b(AbstractC0046as abstractC0046as, float f) {
        if (abstractC0046as instanceof C0256hm) {
            return (float) ((1.0d - y) * ((double) f));
        }
        if (abstractC0046as instanceof C0457o8) {
            return f / 2.0f;
        }
        return 0.0f;
    }

    public final float a() {
        AbstractC0046as abstractC0046as = this.m.a;
        Xh xh = this.c;
        return Math.max(Math.max(b(abstractC0046as, xh.h()), b(this.m.b, xh.a.a.f.a(xh.g()))), Math.max(b(this.m.c, xh.a.a.g.a(xh.g())), b(this.m.d, xh.a.a.h.a(xh.g()))));
    }

    public final LayerDrawable c() {
        if (this.o == null) {
            int[] iArr = AbstractC0133dm.a;
            this.q = new Xh(this.m);
            this.o = new RippleDrawable(this.k, null, this.q);
        }
        if (this.p == null) {
            LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{this.o, this.d, this.j});
            this.p = layerDrawable;
            layerDrawable.setId(2, R.id._0_resource_name_obfuscated_res_0x7f090117);
        }
        return this.p;
    }

    public final Ph d(Drawable drawable) {
        int iCeil;
        int i;
        MaterialCardView materialCardView = this.a;
        if (materialCardView.a) {
            int iCeil2 = (int) Math.ceil((((C0194fm) ((Drawable) materialCardView.e.b)).e * 1.5f) + (g() ? a() : 0.0f));
            iCeil = (int) Math.ceil(((C0194fm) ((Drawable) materialCardView.e.b)).e + (g() ? a() : 0.0f));
            i = iCeil2;
        } else {
            iCeil = 0;
            i = 0;
        }
        return new Ph(drawable, iCeil, i, iCeil, i);
    }

    public final void e(boolean z2, boolean z3) {
        Drawable drawable = this.j;
        if (drawable != null) {
            if (!z3) {
                drawable.setAlpha(z2 ? 255 : 0);
                this.x = z2 ? 1.0f : 0.0f;
                return;
            }
            float f = z2 ? 1.0f : 0.0f;
            float f2 = z2 ? 1.0f - this.x : this.x;
            ValueAnimator valueAnimator = this.t;
            if (valueAnimator != null) {
                valueAnimator.cancel();
                this.t = null;
            }
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.x, f);
            this.t = valueAnimatorOfFloat;
            valueAnimatorOfFloat.addUpdateListener(new C0551ra(1, this));
            this.t.setInterpolator(this.u);
            this.t.setDuration((long) ((z2 ? this.v : this.w) * f2));
            this.t.start();
        }
    }

    public final void f(C0317jn c0317jn) {
        this.m = c0317jn;
        Xh xh = this.c;
        xh.a(c0317jn);
        xh.w = !xh.a.a.f(xh.g());
        Xh xh2 = this.d;
        if (xh2 != null) {
            xh2.a(c0317jn);
        }
        Xh xh3 = this.q;
        if (xh3 != null) {
            xh3.a(c0317jn);
        }
    }

    public final boolean g() {
        MaterialCardView materialCardView = this.a;
        if (!materialCardView.b) {
            return false;
        }
        Xh xh = this.c;
        return xh.a.a.f(xh.g()) && materialCardView.a;
    }

    public final boolean h() {
        View view = this.a;
        if (view.isClickable()) {
            return true;
        }
        while (view.isDuplicateParentStateEnabled() && (view.getParent() instanceof View)) {
            view = (View) view.getParent();
        }
        return view.isClickable();
    }

    public final void i() {
        Drawable drawable = this.i;
        Drawable drawableC = h() ? c() : this.d;
        this.i = drawableC;
        if (drawable != drawableC) {
            MaterialCardView materialCardView = this.a;
            if (materialCardView.getForeground() instanceof InsetDrawable) {
                ((InsetDrawable) materialCardView.getForeground()).setDrawable(drawableC);
            } else {
                materialCardView.setForeground(d(drawableC));
            }
        }
    }
}
