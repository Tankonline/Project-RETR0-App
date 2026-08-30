package rikka.shizuku;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/* JADX INFO: renamed from: rikka.shizuku.e9, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class RunnableC0150e9 extends AbstractC0676vb implements Runnable {
    private static volatile Thread _thread;
    private static volatile int debugStatus;
    public static final RunnableC0150e9 h;
    public static final long i;

    static {
        Long l;
        RunnableC0150e9 runnableC0150e9 = new RunnableC0150e9();
        h = runnableC0150e9;
        runnableC0150e9.b = 1 + runnableC0150e9.b;
        runnableC0150e9.c = true;
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        try {
            l = Long.getLong("kotlinx.coroutines.DefaultExecutor.keepAlive", 1000L);
        } catch (SecurityException unused) {
            l = 1000L;
        }
        i = timeUnit.toNanos(l.longValue());
    }

    @Override // rikka.shizuku.AbstractC0707wb
    public final Thread m() {
        Thread thread;
        Thread thread2 = _thread;
        if (thread2 != null) {
            return thread2;
        }
        synchronized (this) {
            thread = _thread;
            if (thread == null) {
                thread = new Thread(this, "kotlinx.coroutines.DefaultExecutor");
                _thread = thread;
                thread.setContextClassLoader(RunnableC0150e9.class.getClassLoader());
                thread.setDaemon(true);
                thread.start();
            }
        }
        return thread;
    }

    @Override // rikka.shizuku.AbstractC0676vb, rikka.shizuku.AbstractC0707wb
    public final void o() {
        debugStatus = 4;
        super.o();
    }

    @Override // rikka.shizuku.AbstractC0676vb
    public final void p(Runnable runnable) {
        if (debugStatus == 4) {
            throw new RejectedExecutionException("DefaultExecutor was shut down. This error indicates that Dispatchers.shutdown() was invoked prior to completion of exiting coroutines, leaving coroutines in incomplete state. Please refer to Dispatchers.shutdown documentation for more details");
        }
        super.p(runnable);
    }

    @Override // java.lang.Runnable
    public final void run() {
        Np.a.set(this);
        try {
            synchronized (this) {
                int i2 = debugStatus;
                if (i2 == 2 || i2 == 3) {
                    _thread = null;
                    s();
                    if (q()) {
                        return;
                    }
                    m();
                    return;
                }
                debugStatus = 1;
                notifyAll();
                long j = Long.MAX_VALUE;
                while (true) {
                    Thread.interrupted();
                    long jR = r();
                    if (jR == Long.MAX_VALUE) {
                        long jNanoTime = System.nanoTime();
                        if (j == Long.MAX_VALUE) {
                            j = i + jNanoTime;
                        }
                        long j2 = j - jNanoTime;
                        if (j2 <= 0) {
                            _thread = null;
                            s();
                            if (q()) {
                                return;
                            }
                            m();
                            return;
                        }
                        if (jR > j2) {
                            jR = j2;
                        }
                    } else {
                        j = Long.MAX_VALUE;
                    }
                    if (jR > 0) {
                        int i3 = debugStatus;
                        if (i3 == 2 || i3 == 3) {
                            _thread = null;
                            s();
                            if (q()) {
                                return;
                            }
                            m();
                            return;
                        }
                        LockSupport.parkNanos(this, jR);
                    }
                }
            }
        } catch (Throwable th) {
            _thread = null;
            s();
            if (!q()) {
                m();
            }
            throw th;
        }
    }

    public final synchronized void s() {
        int i2 = debugStatus;
        if (i2 == 2 || i2 == 3) {
            debugStatus = 3;
            AbstractC0676vb.e.set(this, null);
            AbstractC0676vb.f.set(this, null);
            notifyAll();
        }
    }

    @Override // rikka.shizuku.W7
    public final String toString() {
        return "DefaultExecutor";
    }
}
