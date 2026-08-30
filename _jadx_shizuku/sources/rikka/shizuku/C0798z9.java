package rikka.shizuku;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/* JADX INFO: renamed from: rikka.shizuku.z9, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0798z9 extends AnimatorListenerAdapter {
    public final /* synthetic */ ViewGroup a;
    public final /* synthetic */ View b;
    public final /* synthetic */ boolean c;
    public final /* synthetic */ C0751xo d;
    public final /* synthetic */ A9 e;

    public C0798z9(ViewGroup viewGroup, View view, boolean z, C0751xo c0751xo, A9 a9) {
        this.a = viewGroup;
        this.b = view;
        this.c = z;
        this.d = c0751xo;
        this.e = a9;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationEnd(Animator animator) {
        ViewGroup viewGroup = this.a;
        View view = this.b;
        viewGroup.endViewTransition(view);
        boolean z = this.c;
        C0751xo c0751xo = this.d;
        if (z) {
            AbstractC0535qo.a(c0751xo.a, view, viewGroup);
        }
        A9 a9 = this.e;
        ((C0751xo) a9.c.b).c(a9);
        if (Qc.J(2)) {
            Log.v("FragmentManager", "Animator from operation " + c0751xo + " has ended.");
        }
    }
}
