package rikka.shizuku;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

/* JADX INFO: renamed from: rikka.shizuku.w9, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class AnimationAnimationListenerC0705w9 implements Animation.AnimationListener {
    public final /* synthetic */ C0751xo a;
    public final /* synthetic */ ViewGroup b;
    public final /* synthetic */ View c;
    public final /* synthetic */ C0736x9 d;

    public AnimationAnimationListenerC0705w9(C0751xo c0751xo, ViewGroup viewGroup, View view, C0736x9 c0736x9) {
        this.a = c0751xo;
        this.b = viewGroup;
        this.c = view;
        this.d = c0736x9;
    }

    @Override // android.view.animation.Animation.AnimationListener
    public final void onAnimationEnd(Animation animation) {
        View view = this.c;
        C0736x9 c0736x9 = this.d;
        ViewGroup viewGroup = this.b;
        viewGroup.post(new RunnableC0674v9(viewGroup, view, c0736x9, 0));
        if (Qc.J(2)) {
            Log.v("FragmentManager", "Animation from operation " + this.a + " has ended.");
        }
    }

    @Override // android.view.animation.Animation.AnimationListener
    public final void onAnimationStart(Animation animation) {
        if (Qc.J(2)) {
            Log.v("FragmentManager", "Animation from operation " + this.a + " has reached onAnimationStart.");
        }
    }

    @Override // android.view.animation.Animation.AnimationListener
    public final void onAnimationRepeat(Animation animation) {
    }
}
