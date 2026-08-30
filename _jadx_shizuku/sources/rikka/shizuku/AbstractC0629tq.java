package rikka.shizuku;

import android.animation.Animator;
import android.animation.AnimatorSet;

/* JADX INFO: renamed from: rikka.shizuku.tq, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0629tq {
    public static long a(Animator animator) {
        return animator.getTotalDuration();
    }

    public static void b(Animator animator, long j) {
        ((AnimatorSet) animator).setCurrentPlayTime(j);
    }
}
