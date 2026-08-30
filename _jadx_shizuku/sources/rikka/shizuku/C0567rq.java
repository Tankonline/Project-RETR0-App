package rikka.shizuku;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;

/* JADX INFO: renamed from: rikka.shizuku.rq, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0567rq extends AnimatorListenerAdapter {
    public final /* synthetic */ int a = 1;
    public final /* synthetic */ Object b;
    public final /* synthetic */ Object c;

    public C0567rq(Jt jt, View view) {
        this.b = jt;
        this.c = view;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationEnd(Animator animator) {
        switch (this.a) {
            case 0:
                ((C0298j4) this.b).remove(animator);
                ((AbstractC0722wq) this.c).n.remove(animator);
                break;
            default:
                ((Jt) this.b).a.d(1.0f);
                Ft.e((View) this.c);
                break;
        }
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationStart(Animator animator) {
        switch (this.a) {
            case 0:
                ((AbstractC0722wq) this.c).n.add(animator);
                break;
            default:
                super.onAnimationStart(animator);
                break;
        }
    }

    public C0567rq(AbstractC0722wq abstractC0722wq, C0298j4 c0298j4) {
        this.c = abstractC0722wq;
        this.b = c0298j4;
    }
}
