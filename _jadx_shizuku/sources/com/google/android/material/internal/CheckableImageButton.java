package com.google.android.material.internal;

import android.R;
import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;
import rikka.shizuku.Hs;
import rikka.shizuku.N2;
import rikka.shizuku.R5;
import rikka.shizuku.S5;

/* JADX INFO: loaded from: classes.dex */
public class CheckableImageButton extends N2 implements Checkable {
    public static final int[] g = {R.attr.state_checked};
    public boolean d;
    public boolean e;
    public boolean f;

    public CheckableImageButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f04024a);
        this.e = true;
        this.f = true;
        Hs.m(this, new R5(0, this));
    }

    @Override // android.widget.Checkable
    public final boolean isChecked() {
        return this.d;
    }

    @Override // android.widget.ImageView, android.view.View
    public final int[] onCreateDrawableState(int i) {
        return this.d ? View.mergeDrawableStates(super.onCreateDrawableState(i + 1), g) : super.onCreateDrawableState(i);
    }

    @Override // android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof S5)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        S5 s5 = (S5) parcelable;
        super.onRestoreInstanceState(s5.a);
        setChecked(s5.c);
    }

    @Override // android.view.View
    public final Parcelable onSaveInstanceState() {
        S5 s5 = new S5(super.onSaveInstanceState());
        s5.c = this.d;
        return s5;
    }

    @Override // android.widget.Checkable
    public final void setChecked(boolean z) {
        if (!this.e || this.d == z) {
            return;
        }
        this.d = z;
        refreshDrawableState();
        sendAccessibilityEvent(2048);
    }

    @Override // android.view.View
    public final void setPressed(boolean z) {
        if (this.f) {
            super.setPressed(z);
        }
    }

    @Override // android.widget.Checkable
    public final void toggle() {
        setChecked(!this.d);
    }
}
