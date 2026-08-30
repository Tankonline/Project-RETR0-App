package rikka.shizuku;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewPropertyAnimator;

/* JADX INFO: renamed from: rikka.shizuku.j9, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0303j9 extends AnimatorListenerAdapter {
    public final /* synthetic */ int a = 1;
    public final /* synthetic */ AbstractC0470ol b;
    public final /* synthetic */ View c;
    public final /* synthetic */ ViewPropertyAnimator d;
    public final /* synthetic */ C0458o9 e;

    public C0303j9(C0458o9 c0458o9, AbstractC0470ol abstractC0470ol, ViewPropertyAnimator viewPropertyAnimator, View view) {
        this.e = c0458o9;
        this.b = abstractC0470ol;
        this.d = viewPropertyAnimator;
        this.c = view;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationCancel(Animator animator) {
        switch (this.a) {
            case 1:
                this.c.setAlpha(1.0f);
                break;
            default:
                super.onAnimationCancel(animator);
                break;
        }
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationEnd(Animator animator) {
        switch (this.a) {
            case 0:
                this.d.setListener(null);
                this.c.setAlpha(1.0f);
                C0458o9 c0458o9 = this.e;
                AbstractC0470ol abstractC0470ol = this.b;
                c0458o9.e(abstractC0470ol);
                c0458o9.q.remove(abstractC0470ol);
                c0458o9.f();
                break;
            default:
                this.d.setListener(null);
                C0458o9 c0458o10 = this.e;
                AbstractC0470ol abstractC0470ol2 = this.b;
                c0458o10.e(abstractC0470ol2);
                c0458o10.o.remove(abstractC0470ol2);
                c0458o10.f();
                break;
        }
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationStart(Animator animator) {
        switch (this.a) {
            case 0:
                this.e.getClass();
                break;
            default:
                this.e.getClass();
                break;
        }
    }

    public C0303j9(C0458o9 c0458o9, AbstractC0470ol abstractC0470ol, View view, ViewPropertyAnimator viewPropertyAnimator) {
        this.e = c0458o9;
        this.b = abstractC0470ol;
        this.c = view;
        this.d = viewPropertyAnimator;
    }
}
