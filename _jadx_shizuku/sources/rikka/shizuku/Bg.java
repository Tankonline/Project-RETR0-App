package rikka.shizuku;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/* JADX INFO: loaded from: classes.dex */
public final class Bg extends W7 implements J9 {
    public static final /* synthetic */ AtomicIntegerFieldUpdater f = AtomicIntegerFieldUpdater.newUpdater(Bg.class, "runningWorkers$volatile");
    public final W7 b;
    public final int c;
    public final C0466oh d;
    public final Object e;
    private volatile /* synthetic */ int runningWorkers$volatile;

    /* JADX WARN: Multi-variable type inference failed */
    public Bg(W7 w7, int i) {
        if ((w7 instanceof J9 ? (J9) w7 : null) == null) {
            int i2 = AbstractC0181f9.a;
        }
        this.b = w7;
        this.c = i;
        this.d = new C0466oh();
        this.e = new Object();
    }

    @Override // rikka.shizuku.W7
    public final void d(V7 v7, Runnable runnable) {
        this.d.a(runnable);
        AtomicIntegerFieldUpdater atomicIntegerFieldUpdater = f;
        if (atomicIntegerFieldUpdater.get(this) < this.c) {
            synchronized (this.e) {
                if (atomicIntegerFieldUpdater.get(this) >= this.c) {
                    return;
                }
                atomicIntegerFieldUpdater.incrementAndGet(this);
                Runnable runnableH = h();
                if (runnableH == null) {
                    return;
                }
                try {
                    X8.W(this.b, this, new A0(this, 5, runnableH));
                } catch (Throwable th) {
                    f.decrementAndGet(this);
                    throw th;
                }
            }
        }
    }

    public final Runnable h() {
        while (true) {
            Runnable runnable = (Runnable) this.d.d();
            if (runnable != null) {
                return runnable;
            }
            synchronized (this.e) {
                AtomicIntegerFieldUpdater atomicIntegerFieldUpdater = f;
                atomicIntegerFieldUpdater.decrementAndGet(this);
                if (this.d.c() == 0) {
                    return null;
                }
                atomicIntegerFieldUpdater.incrementAndGet(this);
            }
        }
    }

    @Override // rikka.shizuku.W7
    public final String toString() {
        return this.b + ".limitedParallelism(" + this.c + ')';
    }
}
