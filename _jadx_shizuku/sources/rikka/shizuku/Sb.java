package rikka.shizuku;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

/* JADX INFO: loaded from: classes.dex */
public final class Sb extends AnimatorListenerAdapter {
    public boolean a = false;
    public final /* synthetic */ Tb b;

    public Sb(Tb tb) {
        this.b = tb;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationCancel(Animator animator) {
        this.a = true;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationEnd(Animator animator) {
        if (this.a) {
            this.a = false;
            return;
        }
        Tb tb = this.b;
        if (((Float) tb.z.getAnimatedValue()).floatValue() == 0.0f) {
            tb.A = 0;
            tb.g(0);
        } else {
            tb.A = 2;
            tb.s.invalidate();
        }
    }
}
