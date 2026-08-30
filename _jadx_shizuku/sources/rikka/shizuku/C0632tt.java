package rikka.shizuku;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewGroup;
import moe.shizuku.privileged.api.R;

/* JADX INFO: renamed from: rikka.shizuku.tt, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0632tt extends AnimatorListenerAdapter implements InterfaceC0691vq {
    public final ViewGroup a;
    public final View b;
    public final View c;
    public boolean d = true;
    public final /* synthetic */ Hb e;

    public C0632tt(Hb hb, ViewGroup viewGroup, View view, View view2) {
        this.e = hb;
        this.a = viewGroup;
        this.b = view;
        this.c = view2;
    }

    @Override // rikka.shizuku.InterfaceC0691vq
    public final void b(AbstractC0722wq abstractC0722wq) {
        abstractC0722wq.z(this);
    }

    @Override // rikka.shizuku.InterfaceC0691vq
    public final void e(AbstractC0722wq abstractC0722wq) {
        if (this.d) {
            g();
        }
    }

    public final void g() {
        this.c.setTag(R.id._0_resource_name_obfuscated_res_0x7f090171, null);
        this.a.getOverlay().remove(this.b);
        this.d = false;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationEnd(Animator animator) {
        g();
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorPauseListener
    public final void onAnimationPause(Animator animator) {
        this.a.getOverlay().remove(this.b);
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorPauseListener
    public final void onAnimationResume(Animator animator) {
        View view = this.b;
        if (view.getParent() == null) {
            this.a.getOverlay().add(view);
        } else {
            this.e.c();
        }
    }

    @Override // android.animation.Animator.AnimatorListener
    public final void onAnimationStart(Animator animator, boolean z) {
        if (z) {
            View view = this.c;
            View view2 = this.b;
            view.setTag(R.id._0_resource_name_obfuscated_res_0x7f090171, view2);
            this.a.getOverlay().add(view2);
            this.d = true;
        }
    }

    @Override // android.animation.Animator.AnimatorListener
    public final void onAnimationEnd(Animator animator, boolean z) {
        if (z) {
            return;
        }
        g();
    }

    @Override // rikka.shizuku.InterfaceC0691vq
    public final void c() {
    }

    @Override // rikka.shizuku.InterfaceC0691vq
    public final void d() {
    }

    @Override // rikka.shizuku.InterfaceC0691vq
    public final void f(AbstractC0722wq abstractC0722wq) {
    }
}
