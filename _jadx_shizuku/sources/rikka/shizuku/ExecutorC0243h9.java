package rikka.shizuku;

import java.util.concurrent.Executor;

/* JADX INFO: renamed from: rikka.shizuku.h9, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class ExecutorC0243h9 extends AbstractC0800zb implements Executor {
    public static final ExecutorC0243h9 b = new ExecutorC0243h9();
    public static final W7 c;

    static {
        Ir ir = Ir.b;
        int i = AbstractC0566rp.a;
        if (64 >= i) {
            i = 64;
        }
        c = ir.f(Hn.p("kotlinx.coroutines.io.parallelism", i, 12));
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        throw new IllegalStateException("Cannot be invoked on Dispatchers.IO");
    }

    @Override // rikka.shizuku.W7
    public final void d(V7 v7, Runnable runnable) {
        c.d(v7, runnable);
    }

    @Override // java.util.concurrent.Executor
    public final void execute(Runnable runnable) {
        d(C0152eb.a, runnable);
    }

    @Override // rikka.shizuku.W7
    public final String toString() {
        return "Dispatchers.IO";
    }
}
