package rikka.shizuku;

import android.os.Build;
import android.view.animation.Interpolator;

/* JADX INFO: loaded from: classes.dex */
public final class Jt {
    public It a;

    public Jt(int i, Interpolator interpolator, long j) {
        if (Build.VERSION.SDK_INT >= 30) {
            this.a = new Ht(X.k(i, interpolator, j));
        } else {
            this.a = new Ft(i, interpolator, j);
        }
    }
}
