package rikka.shizuku;

import java.util.concurrent.CancellationException;

/* JADX INFO: renamed from: rikka.shizuku.bj, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0068bj extends I implements Ef {
    public static final C0068bj b = new C0068bj(Fe.a);

    @Override // rikka.shizuku.Ef
    public final boolean a() {
        return true;
    }

    @Override // rikka.shizuku.Ef
    public final CancellationException c() {
        throw new IllegalStateException("This job is always active");
    }

    @Override // rikka.shizuku.Ef
    public final T5 k(Kf kf) {
        return C0099cj.a;
    }

    @Override // rikka.shizuku.Ef
    public final boolean start() {
        return false;
    }

    public final String toString() {
        return "NonCancellable";
    }

    @Override // rikka.shizuku.Ef
    public final void i(CancellationException cancellationException) {
    }
}
