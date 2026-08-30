package rikka.shizuku;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

/* JADX INFO: renamed from: rikka.shizuku.x9, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0736x9 extends AbstractC0720wo {
    public final C0767y9 c;

    public C0736x9(C0767y9 c0767y9) {
        this.c = c0767y9;
    }

    @Override // rikka.shizuku.AbstractC0720wo
    public final void a(ViewGroup viewGroup) {
        C0767y9 c0767y9 = this.c;
        C0751xo c0751xo = (C0751xo) c0767y9.b;
        View view = c0751xo.c.G;
        view.clearAnimation();
        viewGroup.endViewTransition(view);
        ((C0751xo) c0767y9.b).c(this);
        if (Qc.J(2)) {
            Log.v("FragmentManager", "Animation from operation " + c0751xo + " has been cancelled.");
        }
    }

    @Override // rikka.shizuku.AbstractC0720wo
    public final void b(ViewGroup viewGroup) {
        C0767y9 c0767y9 = this.c;
        boolean zE = c0767y9.e();
        C0751xo c0751xo = (C0751xo) c0767y9.b;
        if (zE) {
            c0751xo.c(this);
            return;
        }
        Context context = viewGroup.getContext();
        View view = c0751xo.c.G;
        R2 r2F = c0767y9.f(context);
        if (r2F == null) {
            throw new IllegalStateException("Required value was null.");
        }
        Animation animation = (Animation) r2F.b;
        if (animation == null) {
            throw new IllegalStateException("Required value was null.");
        }
        if (c0751xo.a != 1) {
            view.startAnimation(animation);
            c0751xo.c(this);
            return;
        }
        viewGroup.startViewTransition(view);
        Dc dc = new Dc(animation, viewGroup, view);
        dc.setAnimationListener(new AnimationAnimationListenerC0705w9(c0751xo, viewGroup, view, this));
        view.startAnimation(dc);
        if (Qc.J(2)) {
            Log.v("FragmentManager", "Animation from operation " + c0751xo + " has started.");
        }
    }
}
