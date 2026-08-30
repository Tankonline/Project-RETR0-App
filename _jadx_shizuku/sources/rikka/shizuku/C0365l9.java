package rikka.shizuku;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewPropertyAnimator;

/* JADX INFO: renamed from: rikka.shizuku.l9, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0365l9 extends AnimatorListenerAdapter {
    public final /* synthetic */ int a;
    public final /* synthetic */ C0396m9 b;
    public final /* synthetic */ ViewPropertyAnimator c;
    public final /* synthetic */ View d;
    public final /* synthetic */ C0458o9 e;

    public /* synthetic */ C0365l9(C0458o9 c0458o9, C0396m9 c0396m9, ViewPropertyAnimator viewPropertyAnimator, View view, int i) {
        this.a = i;
        this.e = c0458o9;
        this.b = c0396m9;
        this.c = viewPropertyAnimator;
        this.d = view;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationEnd(Animator animator) {
        switch (this.a) {
            case 0:
                this.c.setListener(null);
                View view = this.d;
                view.setAlpha(1.0f);
                view.setTranslationX(0.0f);
                view.setTranslationY(0.0f);
                C0396m9 c0396m9 = this.b;
                AbstractC0470ol abstractC0470ol = c0396m9.a;
                C0458o9 c0458o9 = this.e;
                c0458o9.e(abstractC0470ol);
                c0458o9.r.remove(c0396m9.a);
                c0458o9.f();
                break;
            default:
                this.c.setListener(null);
                View view2 = this.d;
                view2.setAlpha(1.0f);
                view2.setTranslationX(0.0f);
                view2.setTranslationY(0.0f);
                C0396m9 c0396m10 = this.b;
                AbstractC0470ol abstractC0470ol2 = c0396m10.b;
                C0458o9 c0458o10 = this.e;
                c0458o10.e(abstractC0470ol2);
                c0458o10.r.remove(c0396m10.b);
                c0458o10.f();
                break;
        }
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationStart(Animator animator) {
        switch (this.a) {
            case 0:
                AbstractC0470ol abstractC0470ol = this.b.a;
                this.e.getClass();
                break;
            default:
                AbstractC0470ol abstractC0470ol2 = this.b.b;
                this.e.getClass();
                break;
        }
    }
}
