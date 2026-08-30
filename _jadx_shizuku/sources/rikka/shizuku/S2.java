package rikka.shizuku;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.widget.RadioButton;
import moe.shizuku.privileged.api.R;

/* JADX INFO: loaded from: classes.dex */
public class S2 extends RadioButton {
    public final C0574s2 a;
    public final T0 b;
    public final C0544r3 c;
    public R2 d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public S2(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, R.attr._0_resource_name_obfuscated_res_0x7f040400);
        Sp.a(context);
        Lp.a(this, getContext());
        C0574s2 c0574s2 = new C0574s2(this);
        this.a = c0574s2;
        c0574s2.b(attributeSet, R.attr._0_resource_name_obfuscated_res_0x7f040400);
        T0 t0 = new T0(this);
        this.b = t0;
        t0.i(attributeSet, R.attr._0_resource_name_obfuscated_res_0x7f040400);
        C0544r3 c0544r3 = new C0544r3(this);
        this.c = c0544r3;
        c0544r3.d(attributeSet, R.attr._0_resource_name_obfuscated_res_0x7f040400);
        if (this.d == null) {
            this.d = new R2(this);
        }
        this.d.H(attributeSet, R.attr._0_resource_name_obfuscated_res_0x7f040400);
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    public final void drawableStateChanged() {
        super.drawableStateChanged();
        T0 t0 = this.b;
        if (t0 != null) {
            t0.a();
        }
        C0544r3 c0544r3 = this.c;
        if (c0544r3 != null) {
            c0544r3.b();
        }
    }

    @Override // android.widget.CompoundButton, android.widget.TextView
    public final int getCompoundPaddingLeft() {
        int compoundPaddingLeft = super.getCompoundPaddingLeft();
        C0574s2 c0574s2 = this.a;
        if (c0574s2 != null) {
            c0574s2.getClass();
        }
        return compoundPaddingLeft;
    }

    @Override // android.widget.TextView
    public final void setAllCaps(boolean z) {
        super.setAllCaps(z);
        if (this.d == null) {
            this.d = new R2(this);
        }
        this.d.X(z);
    }

    @Override // android.view.View
    public final void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        T0 t0 = this.b;
        if (t0 != null) {
            t0.k();
        }
    }

    @Override // android.view.View
    public final void setBackgroundResource(int i) {
        super.setBackgroundResource(i);
        T0 t0 = this.b;
        if (t0 != null) {
            t0.l(i);
        }
    }

    @Override // android.widget.CompoundButton
    public final void setButtonDrawable(Drawable drawable) {
        super.setButtonDrawable(drawable);
        C0574s2 c0574s2 = this.a;
        if (c0574s2 != null) {
            if (c0574s2.e) {
                c0574s2.e = false;
            } else {
                c0574s2.e = true;
                c0574s2.a();
            }
        }
    }

    @Override // android.widget.TextView
    public final void setCompoundDrawables(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        super.setCompoundDrawables(drawable, drawable2, drawable3, drawable4);
        C0544r3 c0544r3 = this.c;
        if (c0544r3 != null) {
            c0544r3.b();
        }
    }

    @Override // android.widget.TextView
    public final void setCompoundDrawablesRelative(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        super.setCompoundDrawablesRelative(drawable, drawable2, drawable3, drawable4);
        C0544r3 c0544r3 = this.c;
        if (c0544r3 != null) {
            c0544r3.b();
        }
    }

    @Override // android.widget.TextView
    public final void setFilters(InputFilter[] inputFilterArr) {
        if (this.d == null) {
            this.d = new R2(this);
        }
        super.setFilters(this.d.B(inputFilterArr));
    }

    @Override // android.widget.CompoundButton
    public final void setButtonDrawable(int i) {
        setButtonDrawable(AbstractC0744xh.v(getContext(), i));
    }
}
