package rikka.shizuku;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewPropertyAnimator;

/* JADX INFO: renamed from: rikka.shizuku.k9, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0334k9 extends AnimatorListenerAdapter {
    public final /* synthetic */ AbstractC0470ol a;
    public final /* synthetic */ int b;
    public final /* synthetic */ View c;
    public final /* synthetic */ int d;
    public final /* synthetic */ ViewPropertyAnimator e;
    public final /* synthetic */ C0458o9 f;

    public C0334k9(C0458o9 c0458o9, AbstractC0470ol abstractC0470ol, int i, View view, int i2, ViewPropertyAnimator viewPropertyAnimator) {
        this.f = c0458o9;
        this.a = abstractC0470ol;
        this.b = i;
        this.c = view;
        this.d = i2;
        this.e = viewPropertyAnimator;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationCancel(Animator animator) {
        int i = this.b;
        View view = this.c;
        if (i != 0) {
            view.setTranslationX(0.0f);
        }
        if (this.d != 0) {
            view.setTranslationY(0.0f);
        }
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationEnd(Animator animator) {
        this.e.setListener(null);
        C0458o9 c0458o9 = this.f;
        AbstractC0470ol abstractC0470ol = this.a;
        c0458o9.e(abstractC0470ol);
        c0458o9.p.remove(abstractC0470ol);
        c0458o9.f();
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationStart(Animator animator) {
        this.f.getClass();
    }
}
