package rikka.shizuku;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.SeekBar;
import moe.shizuku.privileged.api.R;

/* JADX INFO: loaded from: classes.dex */
public final class V2 extends SeekBar {
    public final W2 a;

    public V2(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, R.attr._0_resource_name_obfuscated_res_0x7f04041b);
        Lp.a(this, getContext());
        W2 w2 = new W2(this);
        this.a = w2;
        w2.H(attributeSet, R.attr._0_resource_name_obfuscated_res_0x7f04041b);
    }

    @Override // android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    public final void drawableStateChanged() {
        super.drawableStateChanged();
        W2 w2 = this.a;
        Drawable drawable = w2.g;
        if (drawable == null || !drawable.isStateful()) {
            return;
        }
        V2 v2 = w2.f;
        if (drawable.setState(v2.getDrawableState())) {
            v2.invalidateDrawable(drawable);
        }
    }

    @Override // android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    public final void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable = this.a.g;
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }
    }

    @Override // android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    public final synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.a.b0(canvas);
    }
}
