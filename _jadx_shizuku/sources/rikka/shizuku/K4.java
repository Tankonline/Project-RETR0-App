package rikka.shizuku;

import android.window.BackEvent;

/* JADX INFO: loaded from: classes.dex */
public final class K4 {
    public final float a;
    public final float b;
    public final float c;
    public final int d;

    public K4(BackEvent backEvent) {
        W1 w1 = W1.a;
        float fD = w1.d(backEvent);
        float fE = w1.e(backEvent);
        float fB = w1.b(backEvent);
        int iC = w1.c(backEvent);
        this.a = fD;
        this.b = fE;
        this.c = fB;
        this.d = iC;
    }

    public final String toString() {
        return "BackEventCompat{touchX=" + this.a + ", touchY=" + this.b + ", progress=" + this.c + ", swipeEdge=" + this.d + '}';
    }
}
