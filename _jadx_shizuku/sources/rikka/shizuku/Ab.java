package rikka.shizuku;

import java.lang.reflect.Method;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/* JADX INFO: loaded from: classes.dex */
public final class Ab extends AbstractC0800zb implements J9 {
    public final ExecutorService b;

    public Ab(ExecutorService executorService) {
        Method method;
        this.b = executorService;
        Method method2 = X6.a;
        try {
            ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = executorService instanceof ScheduledThreadPoolExecutor ? (ScheduledThreadPoolExecutor) executorService : null;
            if (scheduledThreadPoolExecutor != null && (method = X6.a) != null) {
                method.invoke(scheduledThreadPoolExecutor, Boolean.TRUE);
            }
        } catch (Throwable unused) {
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        this.b.shutdown();
    }

    @Override // rikka.shizuku.W7
    public final void d(V7 v7, Runnable runnable) {
        try {
            this.b.execute(runnable);
        } catch (RejectedExecutionException e) {
            CancellationException cancellationException = new CancellationException("The task was rejected");
            cancellationException.initCause(e);
            Ef ef = (Ef) v7.g(Fe.a);
            if (ef != null) {
                ef.i(cancellationException);
            }
            C0550r9 c0550r9 = AbstractC0090ca.a;
            ExecutorC0243h9.b.d(v7, runnable);
        }
    }

    public final boolean equals(Object obj) {
        return (obj instanceof Ab) && ((Ab) obj).b == this.b;
    }

    public final int hashCode() {
        return System.identityHashCode(this.b);
    }

    @Override // rikka.shizuku.W7
    public final String toString() {
        return this.b.toString();
    }
}
