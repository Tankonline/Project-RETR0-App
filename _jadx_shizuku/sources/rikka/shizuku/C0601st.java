package rikka.shizuku;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewGroup;

/* JADX INFO: renamed from: rikka.shizuku.st, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0601st extends AnimatorListenerAdapter implements InterfaceC0691vq {
    public final View a;
    public final int b;
    public final ViewGroup c;
    public boolean e;
    public boolean f = false;
    public final boolean d = true;

    public C0601st(View view, int i) {
        this.a = view;
        this.b = i;
        this.c = (ViewGroup) view.getParent();
        g(true);
    }

    @Override // rikka.shizuku.InterfaceC0691vq
    public final void b(AbstractC0722wq abstractC0722wq) {
        abstractC0722wq.z(this);
    }

    @Override // rikka.shizuku.InterfaceC0691vq
    public final void c() {
        g(false);
        if (this.f) {
            return;
        }
        AbstractC0263ht.b(this.a, this.b);
    }

    @Override // rikka.shizuku.InterfaceC0691vq
    public final void d() {
        g(true);
        if (this.f) {
            return;
        }
        AbstractC0263ht.b(this.a, 0);
    }

    public final void g(boolean z) {
        ViewGroup viewGroup;
        if (!this.d || this.e == z || (viewGroup = this.c) == null) {
            return;
        }
        this.e = z;
        Hn.n(viewGroup, z);
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationCancel(Animator animator) {
        this.f = true;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationEnd(Animator animator) {
        if (!this.f) {
            AbstractC0263ht.b(this.a, this.b);
            ViewGroup viewGroup = this.c;
            if (viewGroup != null) {
                viewGroup.invalidate();
            }
        }
        g(false);
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationStart(Animator animator) {
    }

    @Override // android.animation.Animator.AnimatorListener
    public final void onAnimationStart(Animator animator, boolean z) {
        if (z) {
            AbstractC0263ht.b(this.a, 0);
            ViewGroup viewGroup = this.c;
            if (viewGroup != null) {
                viewGroup.invalidate();
            }
        }
    }

    @Override // android.animation.Animator.AnimatorListener
    public final void onAnimationEnd(Animator animator, boolean z) {
        if (z) {
            return;
        }
        if (!this.f) {
            AbstractC0263ht.b(this.a, this.b);
            ViewGroup viewGroup = this.c;
            if (viewGroup != null) {
                viewGroup.invalidate();
            }
        }
        g(false);
    }

    @Override // rikka.shizuku.InterfaceC0691vq
    public final void e(AbstractC0722wq abstractC0722wq) {
    }

    @Override // rikka.shizuku.InterfaceC0691vq
    public final void f(AbstractC0722wq abstractC0722wq) {
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationRepeat(Animator animator) {
    }
}
