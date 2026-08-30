package rikka.shizuku;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import moe.shizuku.privileged.api.R;

/* JADX INFO: loaded from: classes.dex */
public final class Gb extends AnimatorListenerAdapter implements InterfaceC0691vq {
    public final View a;
    public boolean b = false;

    public Gb(View view) {
        this.a = view;
    }

    @Override // rikka.shizuku.InterfaceC0691vq
    public final void c() {
        View view = this.a;
        view.setTag(R.id._0_resource_name_obfuscated_res_0x7f0901ed, Float.valueOf(view.getVisibility() == 0 ? AbstractC0263ht.a.c(view) : 0.0f));
    }

    @Override // rikka.shizuku.InterfaceC0691vq
    public final void d() {
        this.a.setTag(R.id._0_resource_name_obfuscated_res_0x7f0901ed, null);
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationCancel(Animator animator) {
        AbstractC0263ht.a.p(this.a, 1.0f);
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationEnd(Animator animator) {
        onAnimationEnd(animator, false);
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationStart(Animator animator) {
        View view = this.a;
        if (view.hasOverlappingRendering() && view.getLayerType() == 0) {
            this.b = true;
            view.setLayerType(2, null);
        }
    }

    @Override // android.animation.Animator.AnimatorListener
    public final void onAnimationEnd(Animator animator, boolean z) {
        boolean z2 = this.b;
        View view = this.a;
        if (z2) {
            view.setLayerType(0, null);
        }
        if (z) {
            return;
        }
        C0478ot c0478ot = AbstractC0263ht.a;
        c0478ot.p(view, 1.0f);
        c0478ot.getClass();
    }

    @Override // rikka.shizuku.InterfaceC0691vq
    public final void a(AbstractC0722wq abstractC0722wq) {
    }

    @Override // rikka.shizuku.InterfaceC0691vq
    public final void b(AbstractC0722wq abstractC0722wq) {
    }

    @Override // rikka.shizuku.InterfaceC0691vq
    public final void e(AbstractC0722wq abstractC0722wq) {
    }

    @Override // rikka.shizuku.InterfaceC0691vq
    public final void f(AbstractC0722wq abstractC0722wq) {
    }
}
