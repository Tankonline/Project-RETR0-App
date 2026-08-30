package rikka.shizuku;

import android.R;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.widget.ToggleButton;

/* JADX INFO: loaded from: classes.dex */
public final class B3 extends ToggleButton {
    public final T0 a;
    public final C0544r3 b;
    public R2 c;

    public B3(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, R.attr.buttonStyleToggle);
        Lp.a(this, getContext());
        T0 t0 = new T0(this);
        this.a = t0;
        t0.i(attributeSet, R.attr.buttonStyleToggle);
        C0544r3 c0544r3 = new C0544r3(this);
        this.b = c0544r3;
        c0544r3.d(attributeSet, R.attr.buttonStyleToggle);
        if (this.c == null) {
            this.c = new R2(this);
        }
        this.c.H(attributeSet, R.attr.buttonStyleToggle);
    }

    @Override // android.widget.ToggleButton, android.widget.CompoundButton, android.widget.TextView, android.view.View
    public final void drawableStateChanged() {
        super.drawableStateChanged();
        T0 t0 = this.a;
        if (t0 != null) {
            t0.a();
        }
        C0544r3 c0544r3 = this.b;
        if (c0544r3 != null) {
            c0544r3.b();
        }
    }

    @Override // android.widget.TextView
    public final void setAllCaps(boolean z) {
        super.setAllCaps(z);
        if (this.c == null) {
            this.c = new R2(this);
        }
        this.c.X(z);
    }

    @Override // android.widget.ToggleButton, android.view.View
    public final void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        T0 t0 = this.a;
        if (t0 != null) {
            t0.k();
        }
    }

    @Override // android.view.View
    public final void setBackgroundResource(int i) {
        super.setBackgroundResource(i);
        T0 t0 = this.a;
        if (t0 != null) {
            t0.l(i);
        }
    }

    @Override // android.widget.TextView
    public final void setCompoundDrawables(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        super.setCompoundDrawables(drawable, drawable2, drawable3, drawable4);
        C0544r3 c0544r3 = this.b;
        if (c0544r3 != null) {
            c0544r3.b();
        }
    }

    @Override // android.widget.TextView
    public final void setCompoundDrawablesRelative(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        super.setCompoundDrawablesRelative(drawable, drawable2, drawable3, drawable4);
        C0544r3 c0544r3 = this.b;
        if (c0544r3 != null) {
            c0544r3.b();
        }
    }

    @Override // android.widget.TextView
    public final void setFilters(InputFilter[] inputFilterArr) {
        if (this.c == null) {
            this.c = new R2(this);
        }
        super.setFilters(this.c.B(inputFilterArr));
    }
}
