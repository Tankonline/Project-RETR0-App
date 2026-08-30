package rikka.shizuku;

import android.animation.AnimatorSet;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/* JADX INFO: loaded from: classes.dex */
public final class A9 extends AbstractC0720wo {
    public final C0767y9 c;
    public AnimatorSet d;

    public A9(C0767y9 c0767y9) {
        this.c = c0767y9;
    }

    @Override // rikka.shizuku.AbstractC0720wo
    public final void a(ViewGroup viewGroup) {
        AnimatorSet animatorSet = this.d;
        C0767y9 c0767y9 = this.c;
        if (animatorSet == null) {
            ((C0751xo) c0767y9.b).c(this);
            return;
        }
        C0751xo c0751xo = (C0751xo) c0767y9.b;
        if (!c0751xo.g) {
            animatorSet.end();
        } else if (Build.VERSION.SDK_INT >= 26) {
            C9.a.a(animatorSet);
        }
        if (Qc.J(2)) {
            StringBuilder sb = new StringBuilder("Animator from operation ");
            sb.append(c0751xo);
            sb.append(" has been canceled");
            sb.append(c0751xo.g ? " with seeking." : ".");
            sb.append(' ');
            Log.v("FragmentManager", sb.toString());
        }
    }

    @Override // rikka.shizuku.AbstractC0720wo
    public final void b(ViewGroup viewGroup) {
        C0751xo c0751xo = (C0751xo) this.c.b;
        AnimatorSet animatorSet = this.d;
        if (animatorSet == null) {
            c0751xo.c(this);
            return;
        }
        animatorSet.start();
        if (Qc.J(2)) {
            Log.v("FragmentManager", "Animator from operation " + c0751xo + " has started.");
        }
    }

    @Override // rikka.shizuku.AbstractC0720wo
    public final void c(K4 k4) {
        C0767y9 c0767y9 = this.c;
        AnimatorSet animatorSet = this.d;
        C0751xo c0751xo = (C0751xo) c0767y9.b;
        if (animatorSet == null) {
            c0751xo.c(this);
            return;
        }
        if (Build.VERSION.SDK_INT < 34 || !c0751xo.c.m) {
            return;
        }
        if (Qc.J(2)) {
            Log.v("FragmentManager", "Adding BackProgressCallbacks for Animators to operation " + c0751xo);
        }
        long jA = B9.a.a(animatorSet);
        long j = (long) (k4.c * jA);
        if (j == 0) {
            j = 1;
        }
        if (j == jA) {
            j = jA - 1;
        }
        if (Qc.J(2)) {
            Log.v("FragmentManager", "Setting currentPlayTime to " + j + " for Animator " + animatorSet + " on operation " + c0751xo);
        }
        C9.a.b(animatorSet, j);
    }

    @Override // rikka.shizuku.AbstractC0720wo
    public final void d(ViewGroup viewGroup) {
        A9 a9;
        C0767y9 c0767y9 = this.c;
        if (c0767y9.e()) {
            return;
        }
        R2 r2F = c0767y9.f(viewGroup.getContext());
        this.d = r2F != null ? (AnimatorSet) r2F.c : null;
        C0751xo c0751xo = (C0751xo) c0767y9.b;
        Ac ac = c0751xo.c;
        boolean z = c0751xo.a == 3;
        View view = ac.G;
        viewGroup.startViewTransition(view);
        AnimatorSet animatorSet = this.d;
        if (animatorSet != null) {
            a9 = this;
            animatorSet.addListener(new C0798z9(viewGroup, view, z, c0751xo, a9));
        } else {
            a9 = this;
        }
        AnimatorSet animatorSet2 = a9.d;
        if (animatorSet2 != null) {
            animatorSet2.setTarget(view);
        }
    }
}
