package rikka.shizuku;

import android.animation.AnimatorSet;

/* JADX INFO: loaded from: classes.dex */
public final class C9 {
    public static final C9 a = new C9();

    public final void a(AnimatorSet animatorSet) {
        animatorSet.reverse();
    }

    public final void b(AnimatorSet animatorSet, long j) {
        animatorSet.setCurrentPlayTime(j);
    }
}
