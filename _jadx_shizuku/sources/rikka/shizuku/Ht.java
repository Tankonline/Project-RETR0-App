package rikka.shizuku;

import android.view.WindowInsetsAnimation;

/* JADX INFO: loaded from: classes.dex */
public final class Ht extends It {
    public final WindowInsetsAnimation e;

    public Ht(WindowInsetsAnimation windowInsetsAnimation) {
        super(0, null, 0L);
        this.e = windowInsetsAnimation;
    }

    @Override // rikka.shizuku.It
    public final long a() {
        return this.e.getDurationMillis();
    }

    @Override // rikka.shizuku.It
    public final float b() {
        return this.e.getInterpolatedFraction();
    }

    @Override // rikka.shizuku.It
    public final int c() {
        return this.e.getTypeMask();
    }

    @Override // rikka.shizuku.It
    public final void d(float f) {
        this.e.setFraction(f);
    }
}
