package rikka.shizuku;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.CheckedTextView;

/* JADX INFO: renamed from: rikka.shizuku.q2, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0513q2 extends CheckedTextView {
    public final C0543r2 a;
    public final T0 b;
    public final C0544r3 c;
    public R2 d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0513q2(Context context, AttributeSet attributeSet, int i) {
        int resourceId;
        int resourceId2;
        super(context, attributeSet, i);
        Sp.a(context);
        Lp.a(this, getContext());
        C0544r3 c0544r3 = new C0544r3(this);
        this.c = c0544r3;
        c0544r3.d(attributeSet, i);
        c0544r3.b();
        T0 t0 = new T0(this);
        this.b = t0;
        t0.i(attributeSet, i);
        this.a = new C0543r2(this);
        Context context2 = getContext();
        int[] iArr = Nk.l;
        K3 k3N = K3.n(context2, attributeSet, iArr, i);
        TypedArray typedArray = (TypedArray) k3N.a;
        Hs.l(this, getContext(), iArr, attributeSet, (TypedArray) k3N.a, i);
        try {
            if (typedArray.hasValue(1) && (resourceId2 = typedArray.getResourceId(1, 0)) != 0) {
                try {
                    setCheckMarkDrawable(AbstractC0744xh.v(getContext(), resourceId2));
                } catch (Resources.NotFoundException unused) {
                    if (typedArray.hasValue(0)) {
                        setCheckMarkDrawable(AbstractC0744xh.v(getContext(), resourceId));
                    }
                }
            } else if (typedArray.hasValue(0) && (resourceId = typedArray.getResourceId(0, 0)) != 0) {
                setCheckMarkDrawable(AbstractC0744xh.v(getContext(), resourceId));
            }
            if (typedArray.hasValue(2)) {
                setCheckMarkTintList(k3N.h(2));
            }
            if (typedArray.hasValue(3)) {
                setCheckMarkTintMode(AbstractC0304ja.c(typedArray.getInt(3, -1), null));
            }
            k3N.o();
            if (this.d == null) {
                this.d = new R2(this);
            }
            this.d.H(attributeSet, i);
        } catch (Throwable th) {
            k3N.o();
            throw th;
        }
    }

    @Override // android.widget.CheckedTextView, android.widget.TextView, android.view.View
    public void drawableStateChanged() {
        super.drawableStateChanged();
        C0544r3 c0544r3 = this.c;
        if (c0544r3 != null) {
            c0544r3.b();
        }
        T0 t0 = this.b;
        if (t0 != null) {
            t0.a();
        }
        C0543r2 c0543r2 = this.a;
        if (c0543r2 != null) {
            c0543r2.a();
        }
    }

    @Override // android.widget.TextView
    public final ActionMode.Callback getCustomSelectionActionModeCallback() {
        return An.t(super.getCustomSelectionActionModeCallback());
    }

    @Override // android.widget.TextView, android.view.View
    public final InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        InputConnection inputConnectionOnCreateInputConnection = super.onCreateInputConnection(editorInfo);
        AbstractC0046as.I(editorInfo, inputConnectionOnCreateInputConnection, this);
        return inputConnectionOnCreateInputConnection;
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

    @Override // android.widget.CheckedTextView
    public final void setCheckMarkDrawable(Drawable drawable) {
        super.setCheckMarkDrawable(drawable);
        C0543r2 c0543r2 = this.a;
        if (c0543r2 != null) {
            if (c0543r2.d) {
                c0543r2.d = false;
            } else {
                c0543r2.d = true;
                c0543r2.a();
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
    public final void setCustomSelectionActionModeCallback(ActionMode.Callback callback) {
        super.setCustomSelectionActionModeCallback(An.u(callback, this));
    }

    @Override // android.widget.TextView
    public final void setTextAppearance(Context context, int i) {
        super.setTextAppearance(context, i);
        C0544r3 c0544r3 = this.c;
        if (c0544r3 != null) {
            c0544r3.e(context, i);
        }
    }

    @Override // android.widget.CheckedTextView
    public final void setCheckMarkDrawable(int i) {
        setCheckMarkDrawable(AbstractC0744xh.v(getContext(), i));
    }
}
