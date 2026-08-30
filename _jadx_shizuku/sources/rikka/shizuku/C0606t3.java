package rikka.shizuku;

import android.R;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.textclassifier.TextClassifier;
import android.widget.TextView;

/* JADX INFO: renamed from: rikka.shizuku.t3, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0606t3 extends TextView {
    public final T0 a;
    public final C0544r3 b;
    public final R2 c;
    public R2 d;
    public boolean e;
    public Ys f;

    public C0606t3(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.textViewStyle);
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

    public final Ys g() {
        if (this.f == null) {
            int i = Build.VERSION.SDK_INT;
            if (i >= 28) {
                this.f = new C0575s3(this);
            } else if (i >= 26) {
                this.f = new Ys(8, this);
            }
        }
        return this.f;
    }

    @Override // android.widget.TextView
    public final int getAutoSizeMaxTextSize() {
        if (AbstractC0323jt.a) {
            return super.getAutoSizeMaxTextSize();
        }
        C0544r3 c0544r3 = this.b;
        if (c0544r3 != null) {
            return Math.round(c0544r3.h.e);
        }
        return -1;
    }

    @Override // android.widget.TextView
    public final int getAutoSizeMinTextSize() {
        if (AbstractC0323jt.a) {
            return super.getAutoSizeMinTextSize();
        }
        C0544r3 c0544r3 = this.b;
        if (c0544r3 != null) {
            return Math.round(c0544r3.h.d);
        }
        return -1;
    }

    @Override // android.widget.TextView
    public final int getAutoSizeStepGranularity() {
        if (AbstractC0323jt.a) {
            return super.getAutoSizeStepGranularity();
        }
        C0544r3 c0544r3 = this.b;
        if (c0544r3 != null) {
            return Math.round(c0544r3.h.c);
        }
        return -1;
    }

    @Override // android.widget.TextView
    public final int[] getAutoSizeTextAvailableSizes() {
        if (AbstractC0323jt.a) {
            return super.getAutoSizeTextAvailableSizes();
        }
        C0544r3 c0544r3 = this.b;
        return c0544r3 != null ? c0544r3.h.f : new int[0];
    }

    @Override // android.widget.TextView
    public final int getAutoSizeTextType() {
        if (AbstractC0323jt.a) {
            return super.getAutoSizeTextType() == 1 ? 1 : 0;
        }
        C0544r3 c0544r3 = this.b;
        if (c0544r3 != null) {
            return c0544r3.h.a;
        }
        return 0;
    }

    @Override // android.widget.TextView
    public final ActionMode.Callback getCustomSelectionActionModeCallback() {
        return An.t(super.getCustomSelectionActionModeCallback());
    }

    @Override // android.widget.TextView
    public final int getFirstBaselineToTopHeight() {
        return getPaddingTop() - getPaint().getFontMetricsInt().top;
    }

    @Override // android.widget.TextView
    public final int getLastBaselineToBottomHeight() {
        return getPaddingBottom() + getPaint().getFontMetricsInt().bottom;
    }

    @Override // android.widget.TextView
    public final TextClassifier getTextClassifier() {
        R2 r2;
        if (Build.VERSION.SDK_INT >= 28 || (r2 = this.c) == null) {
            return super.getTextClassifier();
        }
        TextClassifier textClassifier = (TextClassifier) r2.c;
        return textClassifier == null ? AbstractC0328k3.a((TextView) r2.b) : textClassifier;
    }

    @Override // android.widget.TextView, android.view.View
    public final InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        InputConnection inputConnectionOnCreateInputConnection = super.onCreateInputConnection(editorInfo);
        this.b.getClass();
        C0544r3.f(editorInfo, inputConnectionOnCreateInputConnection, this);
        AbstractC0046as.I(editorInfo, inputConnectionOnCreateInputConnection, this);
        return inputConnectionOnCreateInputConnection;
    }

    @Override // android.widget.TextView, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        C0544r3 c0544r3 = this.b;
        if (c0544r3 == null || AbstractC0323jt.a) {
            return;
        }
        c0544r3.h.a();
    }

    @Override // android.widget.TextView, android.view.View
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
    }

    @Override // android.widget.TextView
    public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        super.onTextChanged(charSequence, i, i2, i3);
        C0544r3 c0544r3 = this.b;
        if (c0544r3 == null || AbstractC0323jt.a) {
            return;
        }
        A3 a3 = c0544r3.h;
        if (a3.f()) {
            a3.a();
        }
    }

    @Override // android.widget.TextView
    public final void setAllCaps(boolean z) {
        super.setAllCaps(z);
        if (this.d == null) {
            this.d = new R2(this);
        }
        this.d.X(z);
    }

    @Override // android.widget.TextView
    public final void setAutoSizeTextTypeUniformWithConfiguration(int i, int i2, int i3, int i4) {
        if (AbstractC0323jt.a) {
            super.setAutoSizeTextTypeUniformWithConfiguration(i, i2, i3, i4);
            return;
        }
        C0544r3 c0544r3 = this.b;
        if (c0544r3 != null) {
            c0544r3.g(i, i2, i3, i4);
        }
    }

    @Override // android.widget.TextView
    public final void setAutoSizeTextTypeUniformWithPresetSizes(int[] iArr, int i) {
        if (AbstractC0323jt.a) {
            super.setAutoSizeTextTypeUniformWithPresetSizes(iArr, i);
            return;
        }
        C0544r3 c0544r3 = this.b;
        if (c0544r3 != null) {
            c0544r3.h(iArr, i);
        }
    }

    @Override // android.widget.TextView
    public final void setAutoSizeTextTypeWithDefaults(int i) {
        if (AbstractC0323jt.a) {
            super.setAutoSizeTextTypeWithDefaults(i);
            return;
        }
        C0544r3 c0544r3 = this.b;
        if (c0544r3 != null) {
            c0544r3.i(i);
        }
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

    @Override // android.widget.TextView
    public final void setCompoundDrawablesRelativeWithIntrinsicBounds(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        super.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
        C0544r3 c0544r3 = this.b;
        if (c0544r3 != null) {
            c0544r3.b();
        }
    }

    @Override // android.widget.TextView
    public final void setCompoundDrawablesWithIntrinsicBounds(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        super.setCompoundDrawablesWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
        C0544r3 c0544r3 = this.b;
        if (c0544r3 != null) {
            c0544r3.b();
        }
    }

    @Override // android.widget.TextView
    public final void setCustomSelectionActionModeCallback(ActionMode.Callback callback) {
        super.setCustomSelectionActionModeCallback(An.u(callback, this));
    }

    @Override // android.widget.TextView
    public final void setFilters(InputFilter[] inputFilterArr) {
        if (this.d == null) {
            this.d = new R2(this);
        }
        super.setFilters(this.d.B(inputFilterArr));
    }

    @Override // android.widget.TextView
    public final void setFirstBaselineToTopHeight(int i) {
        if (Build.VERSION.SDK_INT >= 28) {
            g().B(i);
        } else {
            An.m(this, i);
        }
    }

    @Override // android.widget.TextView
    public final void setLastBaselineToBottomHeight(int i) {
        if (Build.VERSION.SDK_INT >= 28) {
            g().C(i);
        } else {
            An.n(this, i);
        }
    }

    @Override // android.widget.TextView
    public final void setLineHeight(int i) {
        An.o(this, i);
    }

    @Override // android.widget.TextView
    public void setTextAppearance(Context context, int i) {
        super.setTextAppearance(context, i);
        C0544r3 c0544r3 = this.b;
        if (c0544r3 != null) {
            c0544r3.e(context, i);
        }
    }

    @Override // android.widget.TextView
    public final void setTextClassifier(TextClassifier textClassifier) {
        R2 r2;
        if (Build.VERSION.SDK_INT >= 28 || (r2 = this.c) == null) {
            super.setTextClassifier(textClassifier);
        } else {
            r2.c = textClassifier;
        }
    }

    @Override // android.widget.TextView
    public final void setTextSize(int i, float f) {
        boolean z = AbstractC0323jt.a;
        if (z) {
            super.setTextSize(i, f);
            return;
        }
        C0544r3 c0544r3 = this.b;
        if (c0544r3 == null || z) {
            return;
        }
        A3 a3 = c0544r3.h;
        if (a3.f()) {
            return;
        }
        a3.g(i, f);
    }

    @Override // android.widget.TextView
    public void setTypeface(Typeface typeface, int i) {
        Typeface typefaceCreate;
        if (this.e) {
            return;
        }
        if (typeface == null || i <= 0) {
            typefaceCreate = null;
        } else {
            Context context = getContext();
            AbstractC0688vn abstractC0688vn = AbstractC0661ur.a;
            if (context == null) {
                throw new IllegalArgumentException("Context cannot be null");
            }
            typefaceCreate = Typeface.create(typeface, i);
        }
        this.e = true;
        if (typefaceCreate != null) {
            typeface = typefaceCreate;
        }
        try {
            super.setTypeface(typeface, i);
        } finally {
            this.e = false;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0606t3(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Sp.a(context);
        this.e = false;
        this.f = null;
        Lp.a(this, getContext());
        T0 t0 = new T0(this);
        this.a = t0;
        t0.i(attributeSet, i);
        C0544r3 c0544r3 = new C0544r3(this);
        this.b = c0544r3;
        c0544r3.d(attributeSet, i);
        c0544r3.b();
        R2 r2 = new R2(6, false);
        r2.b = this;
        this.c = r2;
        if (this.d == null) {
            this.d = new R2(this);
        }
        this.d.H(attributeSet, i);
    }

    @Override // android.widget.TextView
    public final void setCompoundDrawablesRelativeWithIntrinsicBounds(int i, int i2, int i3, int i4) {
        Context context = getContext();
        setCompoundDrawablesRelativeWithIntrinsicBounds(i != 0 ? AbstractC0744xh.v(context, i) : null, i2 != 0 ? AbstractC0744xh.v(context, i2) : null, i3 != 0 ? AbstractC0744xh.v(context, i3) : null, i4 != 0 ? AbstractC0744xh.v(context, i4) : null);
        C0544r3 c0544r3 = this.b;
        if (c0544r3 != null) {
            c0544r3.b();
        }
    }

    @Override // android.widget.TextView
    public final void setCompoundDrawablesWithIntrinsicBounds(int i, int i2, int i3, int i4) {
        Context context = getContext();
        setCompoundDrawablesWithIntrinsicBounds(i != 0 ? AbstractC0744xh.v(context, i) : null, i2 != 0 ? AbstractC0744xh.v(context, i2) : null, i3 != 0 ? AbstractC0744xh.v(context, i3) : null, i4 != 0 ? AbstractC0744xh.v(context, i4) : null);
        C0544r3 c0544r3 = this.b;
        if (c0544r3 != null) {
            c0544r3.b();
        }
    }
}
