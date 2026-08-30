package rikka.shizuku;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;

/* JADX INFO: loaded from: classes.dex */
public abstract class Dm extends AbstractC0800zb {
    public Z7 b;

    @Override // rikka.shizuku.W7
    public final void d(V7 v7, Runnable runnable) {
        Z7 z7 = this.b;
        AtomicLongFieldUpdater atomicLongFieldUpdater = Z7.h;
        z7.b(runnable, false);
    }
}
