package rikka.shizuku;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import moe.shizuku.privileged.api.R;

/* JADX INFO: loaded from: classes.dex */
public final class W2 extends R2 {
    public final V2 f;
    public Drawable g;
    public ColorStateList h;
    public PorterDuff.Mode i;
    public boolean j;
    public boolean k;

    public W2(V2 v2) {
        super(0, v2);
        this.h = null;
        this.i = null;
        this.j = false;
        this.k = false;
        this.f = v2;
    }

    @Override // rikka.shizuku.R2
    public final void H(AttributeSet attributeSet, int i) {
        super.H(attributeSet, R.attr._0_resource_name_obfuscated_res_0x7f04041b);
        V2 v2 = this.f;
        Context context = v2.getContext();
        int[] iArr = Nk.g;
        K3 k3N = K3.n(context, attributeSet, iArr, R.attr._0_resource_name_obfuscated_res_0x7f04041b);
        Hs.l(v2, v2.getContext(), iArr, attributeSet, (TypedArray) k3N.a, R.attr._0_resource_name_obfuscated_res_0x7f04041b);
        Drawable drawableJ = k3N.j(0);
        if (drawableJ != null) {
            v2.setThumb(drawableJ);
        }
        Drawable drawableI = k3N.i(1);
        Drawable drawable = this.g;
        if (drawable != null) {
            drawable.setCallback(null);
        }
        this.g = drawableI;
        if (drawableI != null) {
            drawableI.setCallback(v2);
            drawableI.setLayoutDirection(v2.getLayoutDirection());
            if (drawableI.isStateful()) {
                drawableI.setState(v2.getDrawableState());
            }
            a0();
        }
        v2.invalidate();
        TypedArray typedArray = (TypedArray) k3N.a;
        if (typedArray.hasValue(3)) {
            this.i = AbstractC0304ja.c(typedArray.getInt(3, -1), this.i);
            this.k = true;
        }
        if (typedArray.hasValue(2)) {
            this.h = k3N.h(2);
            this.j = true;
        }
        k3N.o();
        a0();
    }

    public final void a0() {
        Drawable drawable = this.g;
        if (drawable != null) {
            if (this.j || this.k) {
                Drawable drawableMutate = drawable.mutate();
                this.g = drawableMutate;
                if (this.j) {
                    drawableMutate.setTintList(this.h);
                }
                if (this.k) {
                    this.g.setTintMode(this.i);
                }
                if (this.g.isStateful()) {
                    this.g.setState(this.f.getDrawableState());
                }
            }
        }
    }

    public final void b0(Canvas canvas) {
        if (this.g != null) {
            V2 v2 = this.f;
            int max = v2.getMax();
            if (max > 1) {
                int intrinsicWidth = this.g.getIntrinsicWidth();
                int intrinsicHeight = this.g.getIntrinsicHeight();
                int i = intrinsicWidth >= 0 ? intrinsicWidth / 2 : 1;
                int i2 = intrinsicHeight >= 0 ? intrinsicHeight / 2 : 1;
                this.g.setBounds(-i, -i2, i, i2);
                float width = ((v2.getWidth() - v2.getPaddingLeft()) - v2.getPaddingRight()) / max;
                int iSave = canvas.save();
                canvas.translate(v2.getPaddingLeft(), v2.getHeight() / 2);
                for (int i3 = 0; i3 <= max; i3++) {
                    this.g.draw(canvas);
                    canvas.translate(width, 0.0f);
                }
                canvas.restoreToCount(iSave);
            }
        }
    }
}
