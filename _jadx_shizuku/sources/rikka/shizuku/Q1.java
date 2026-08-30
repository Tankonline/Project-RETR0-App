package rikka.shizuku;

import android.content.res.ColorStateList;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.Drawable;

/* JADX INFO: loaded from: classes.dex */
public final class Q1 extends Animatable2.AnimationCallback {
    public final /* synthetic */ Rh a;

    public Q1(Rh rh) {
        this.a = rh;
    }

    @Override // android.graphics.drawable.Animatable2.AnimationCallback
    public final void onAnimationEnd(Drawable drawable) {
        ColorStateList colorStateList = this.a.b.n;
        if (colorStateList != null) {
            drawable.setTintList(colorStateList);
        }
    }

    @Override // android.graphics.drawable.Animatable2.AnimationCallback
    public final void onAnimationStart(Drawable drawable) {
        Th th = this.a.b;
        ColorStateList colorStateList = th.n;
        if (colorStateList != null) {
            drawable.setTint(colorStateList.getColorForState(th.r, colorStateList.getDefaultColor()));
        }
    }
}
