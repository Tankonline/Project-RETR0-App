package rikka.shizuku;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;

/* JADX INFO: renamed from: rikka.shizuku.n2, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0420n2 extends Button {
    public final T0 a;
    public final C0544r3 b;
    public R2 c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0420n2(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Sp.a(context);
        Lp.a(this, getContext());
        T0 t0 = new T0(this);
        this.a = t0;
        t0.i(attributeSet, i);
        C0544r3 c0544r3 = new C0544r3(this);
        this.b = c0544r3;
        c0544r3.d(attributeSet, i);
        c0544r3.b();
        if (this.c == null) {
            this.c = new R2(this);
        }
        this.c.H(attributeSet, i);
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

    @Override // android.view.View
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName(Button.class.getName());
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(Button.class.getName());
    }

    @Override // android.widget.TextView, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        C0544r3 c0544r3 = this.b;
        if (c0544r3 == null || AbstractC0323jt.a) {
            return;
        }
        c0544r3.h.a();
    }

    @Override // android.widget.TextView
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
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
        if (this.c == null) {
            this.c = new R2(this);
        }
        this.c.X(z);
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
    public void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        T0 t0 = this.a;
        if (t0 != null) {
            t0.k();
        }
    }

    @Override // android.view.View
    public void setBackgroundResource(int i) {
        super.setBackgroundResource(i);
        T0 t0 = this.a;
        if (t0 != null) {
            t0.l(i);
        }
    }

    @Override // android.widget.TextView
    public final void setCustomSelectionActionModeCallback(ActionMode.Callback callback) {
        super.setCustomSelectionActionModeCallback(An.u(callback, this));
    }

    @Override // android.widget.TextView
    public final void setFilters(InputFilter[] inputFilterArr) {
        if (this.c == null) {
            this.c = new R2(this);
        }
        super.setFilters(this.c.B(inputFilterArr));
    }

    @Override // android.widget.TextView
    public final void setTextAppearance(Context context, int i) {
        super.setTextAppearance(context, i);
        C0544r3 c0544r3 = this.b;
        if (c0544r3 != null) {
            c0544r3.e(context, i);
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
}
