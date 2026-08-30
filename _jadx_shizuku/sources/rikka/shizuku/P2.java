package rikka.shizuku;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.method.KeyListener;
import android.text.method.NumberKeyListener;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.MultiAutoCompleteTextView;

/* JADX INFO: loaded from: classes.dex */
public final class P2 extends MultiAutoCompleteTextView {
    public static final int[] d = {R.attr.popupBackground};
    public final T0 a;
    public final C0544r3 b;
    public final R2 c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public P2(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f040043);
        Sp.a(context);
        Lp.a(this, getContext());
        K3 k3N = K3.n(getContext(), attributeSet, d, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f040043);
        if (((TypedArray) k3N.a).hasValue(0)) {
            setDropDownBackgroundDrawable(k3N.i(0));
        }
        k3N.o();
        T0 t0 = new T0(this);
        this.a = t0;
        t0.i(attributeSet, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f040043);
        C0544r3 c0544r3 = new C0544r3(this);
        this.b = c0544r3;
        c0544r3.d(attributeSet, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f040043);
        c0544r3.b();
        R2 r2 = new R2(this, 4);
        this.c = r2;
        r2.H(attributeSet, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f040043);
        KeyListener keyListener = getKeyListener();
        if (keyListener instanceof NumberKeyListener) {
            return;
        }
        boolean zIsFocusable = isFocusable();
        boolean zIsClickable = isClickable();
        boolean zIsLongClickable = isLongClickable();
        int inputType = getInputType();
        KeyListener keyListenerC = r2.C(keyListener);
        if (keyListenerC == keyListener) {
            return;
        }
        super.setKeyListener(keyListenerC);
        setRawInputType(inputType);
        setFocusable(zIsFocusable);
        setClickable(zIsClickable);
        setLongClickable(zIsLongClickable);
    }

    @Override // android.widget.TextView, android.view.View
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

    @Override // android.widget.TextView, android.view.View
    public final InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        InputConnection inputConnectionOnCreateInputConnection = super.onCreateInputConnection(editorInfo);
        AbstractC0046as.I(editorInfo, inputConnectionOnCreateInputConnection, this);
        return this.c.K(inputConnectionOnCreateInputConnection, editorInfo);
    }

    @Override // android.view.View
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

    @Override // android.widget.AutoCompleteTextView
    public final void setDropDownBackgroundResource(int i) {
        setDropDownBackgroundDrawable(AbstractC0744xh.v(getContext(), i));
    }

    @Override // android.widget.TextView
    public final void setKeyListener(KeyListener keyListener) {
        super.setKeyListener(this.c.C(keyListener));
    }

    @Override // android.widget.TextView
    public final void setTextAppearance(Context context, int i) {
        super.setTextAppearance(context, i);
        C0544r3 c0544r3 = this.b;
        if (c0544r3 != null) {
            c0544r3.e(context, i);
        }
    }
}
