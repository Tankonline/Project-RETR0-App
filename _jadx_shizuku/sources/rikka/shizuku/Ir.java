package rikka.shizuku;

/* JADX INFO: loaded from: classes.dex */
public final class Ir extends W7 {
    public static final Ir b = new Ir();

    @Override // rikka.shizuku.W7
    public final void d(V7 v7, Runnable runnable) {
        C0550r9.c.b.b(runnable, true);
    }

    @Override // rikka.shizuku.W7
    public final W7 f(int i) {
        X8.m(i);
        return i >= AbstractC0783yp.d ? this : super.f(i);
    }

    @Override // rikka.shizuku.W7
    public final String toString() {
        return "Dispatchers.IO";
    }
}
