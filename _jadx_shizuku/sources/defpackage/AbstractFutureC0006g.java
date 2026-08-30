package defpackage;

import java.util.Locale;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.concurrent.locks.LockSupport;
import java.util.logging.Level;
import java.util.logging.Logger;

/* JADX INFO: renamed from: g, reason: case insensitive filesystem */
/* JADX INFO: loaded from: assets/rish_shizuku.dex */
public abstract class AbstractFutureC0006g implements Future {
    public static final boolean d = Boolean.parseBoolean(System.getProperty("guava.concurrent.generate_cancellation_cause", "false"));
    public static final Logger e = Logger.getLogger(AbstractFutureC0006g.class.getName());
    public static final t f;
    public static final Object g;
    public volatile Object a;
    public volatile C0002c b;
    public volatile C0005f c;

    static {
        t c0004e;
        try {
            c0004e = new C0003d(AtomicReferenceFieldUpdater.newUpdater(C0005f.class, Thread.class, "a"), AtomicReferenceFieldUpdater.newUpdater(C0005f.class, C0005f.class, "b"), AtomicReferenceFieldUpdater.newUpdater(AbstractFutureC0006g.class, C0005f.class, "c"), AtomicReferenceFieldUpdater.newUpdater(AbstractFutureC0006g.class, C0002c.class, "b"), AtomicReferenceFieldUpdater.newUpdater(AbstractFutureC0006g.class, Object.class, "a"));
            th = null;
        } catch (Throwable th) {
            th = th;
            c0004e = new C0004e();
        }
        f = c0004e;
        if (th != null) {
            e.log(Level.SEVERE, "SafeAtomicHelper is broken!", th);
        }
        g = new Object();
    }

    public static void b(AbstractFutureC0006g abstractFutureC0006g) {
        C0005f c0005f;
        C0002c c0002c;
        do {
            c0005f = abstractFutureC0006g.c;
        } while (!f.e(abstractFutureC0006g, c0005f, C0005f.c));
        while (c0005f != null) {
            Thread thread = c0005f.a;
            if (thread != null) {
                c0005f.a = null;
                LockSupport.unpark(thread);
            }
            c0005f = c0005f.b;
        }
        do {
            c0002c = abstractFutureC0006g.b;
        } while (!f.c(abstractFutureC0006g, c0002c));
        C0002c c0002c2 = null;
        while (c0002c != null) {
            C0002c c0002c3 = c0002c.a;
            c0002c.a = c0002c2;
            c0002c2 = c0002c;
            c0002c = c0002c3;
        }
        while (c0002c2 != null) {
            c0002c2 = c0002c2.a;
            try {
                throw null;
            } catch (RuntimeException e2) {
                e.log(Level.SEVERE, "RuntimeException while executing runnable null with executor null", (Throwable) e2);
            }
        }
    }

    public static Object c(Object obj) throws ExecutionException {
        if (obj instanceof C0000a) {
            CancellationException cancellationException = ((C0000a) obj).a;
            CancellationException cancellationException2 = new CancellationException("Task was cancelled.");
            cancellationException2.initCause(cancellationException);
            throw cancellationException2;
        }
        if (obj instanceof AbstractC0001b) {
            ((AbstractC0001b) obj).getClass();
            throw new ExecutionException((Throwable) null);
        }
        if (obj == g) {
            return null;
        }
        return obj;
    }

    public static Object d(AbstractFutureC0006g abstractFutureC0006g) {
        Object obj;
        boolean z = false;
        while (true) {
            try {
                obj = abstractFutureC0006g.get();
                break;
            } catch (InterruptedException unused) {
                z = true;
            } catch (Throwable th) {
                if (z) {
                    Thread.currentThread().interrupt();
                }
                throw th;
            }
        }
        if (z) {
            Thread.currentThread().interrupt();
        }
        return obj;
    }

    public final void a(StringBuilder sb) {
        try {
            Object objD = d(this);
            sb.append("SUCCESS, result=[");
            sb.append(objD == this ? "this future" : String.valueOf(objD));
            sb.append("]");
        } catch (CancellationException unused) {
            sb.append("CANCELLED");
        } catch (RuntimeException e2) {
            sb.append("UNKNOWN, cause=[");
            sb.append(e2.getClass());
            sb.append(" thrown from get()]");
        } catch (ExecutionException e3) {
            sb.append("FAILURE, cause=[");
            sb.append(e3.getCause());
            sb.append("]");
        }
    }

    @Override // java.util.concurrent.Future
    public final boolean cancel(boolean z) {
        C0000a c0000a;
        Object obj = this.a;
        if (obj != null) {
            return false;
        }
        if (d) {
            c0000a = new C0000a(z, new CancellationException("Future.cancel() was called."));
        } else {
            c0000a = z ? C0000a.b : C0000a.c;
        }
        if (!f.d(this, obj, c0000a)) {
            return false;
        }
        b(this);
        return true;
    }

    public final void e(C0005f c0005f) {
        c0005f.a = null;
        while (true) {
            C0005f c0005f2 = this.c;
            if (c0005f2 == C0005f.c) {
                return;
            }
            C0005f c0005f3 = null;
            while (c0005f2 != null) {
                C0005f c0005f4 = c0005f2.b;
                if (c0005f2.a != null) {
                    c0005f3 = c0005f2;
                } else if (c0005f3 != null) {
                    c0005f3.b = c0005f4;
                    if (c0005f3.a == null) {
                    }
                } else if (!f.e(this, c0005f2, c0005f4)) {
                }
                c0005f2 = c0005f4;
            }
            return;
        }
    }

    @Override // java.util.concurrent.Future
    public final Object get(long j, TimeUnit timeUnit) throws InterruptedException, TimeoutException {
        long nanos = timeUnit.toNanos(j);
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        Object obj = this.a;
        if (obj != null) {
            return c(obj);
        }
        long jNanoTime = nanos > 0 ? System.nanoTime() + nanos : 0L;
        if (nanos >= 1000) {
            C0005f c0005f = this.c;
            C0005f c0005f2 = C0005f.c;
            if (c0005f != c0005f2) {
                C0005f c0005f3 = new C0005f();
                while (true) {
                    t tVar = f;
                    tVar.l(c0005f3, c0005f);
                    if (tVar.e(this, c0005f, c0005f3)) {
                        do {
                            LockSupport.parkNanos(this, nanos);
                            if (Thread.interrupted()) {
                                e(c0005f3);
                                throw new InterruptedException();
                            }
                            Object obj2 = this.a;
                            if (obj2 != null) {
                                return c(obj2);
                            }
                            nanos = jNanoTime - System.nanoTime();
                        } while (nanos >= 1000);
                        e(c0005f3);
                        break;
                    }
                    c0005f = this.c;
                    if (c0005f == c0005f2) {
                    }
                }
            }
            return c(this.a);
        }
        while (nanos > 0) {
            Object obj3 = this.a;
            if (obj3 != null) {
                return c(obj3);
            }
            if (Thread.interrupted()) {
                throw new InterruptedException();
            }
            nanos = jNanoTime - System.nanoTime();
        }
        String string = toString();
        String string2 = timeUnit.toString();
        Locale locale = Locale.ROOT;
        String lowerCase = string2.toLowerCase(locale);
        String str = "Waited " + j + " " + timeUnit.toString().toLowerCase(locale);
        if (nanos + 1000 < 0) {
            String str2 = str + " (plus ";
            long j2 = -nanos;
            long jConvert = timeUnit.convert(j2, TimeUnit.NANOSECONDS);
            long nanos2 = j2 - timeUnit.toNanos(jConvert);
            boolean z = jConvert == 0 || nanos2 > 1000;
            if (jConvert > 0) {
                String str3 = str2 + jConvert + " " + lowerCase;
                if (z) {
                    str3 = str3 + ",";
                }
                str2 = str3 + " ";
            }
            if (z) {
                str2 = str2 + nanos2 + " nanoseconds ";
            }
            str = str2 + "delay)";
        }
        if (isDone()) {
            throw new TimeoutException(str + " but future completed as timeout expired");
        }
        throw new TimeoutException(str + " for " + string);
    }

    @Override // java.util.concurrent.Future
    public final boolean isCancelled() {
        return this.a instanceof C0000a;
    }

    @Override // java.util.concurrent.Future
    public final boolean isDone() {
        return this.a != null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("[status=");
        if (this.a instanceof C0000a) {
            sb.append("CANCELLED");
        } else if (isDone()) {
            a(sb);
        } else {
            try {
                if (this instanceof ScheduledFuture) {
                    str = "remaining delay=[" + ((ScheduledFuture) this).getDelay(TimeUnit.MILLISECONDS) + " ms]";
                } else {
                    str = null;
                }
            } catch (RuntimeException e2) {
                str = "Exception thrown from implementation: " + e2.getClass();
            }
            if (str != null && !str.isEmpty()) {
                sb.append("PENDING, info=[");
                sb.append(str);
                sb.append("]");
            } else if (isDone()) {
                a(sb);
            } else {
                sb.append("PENDING");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    @Override // java.util.concurrent.Future
    public final Object get() throws InterruptedException {
        Object obj;
        if (!Thread.interrupted()) {
            Object obj2 = this.a;
            if (obj2 != null) {
                return c(obj2);
            }
            C0005f c0005f = this.c;
            C0005f c0005f2 = C0005f.c;
            if (c0005f != c0005f2) {
                C0005f c0005f3 = new C0005f();
                do {
                    t tVar = f;
                    tVar.l(c0005f3, c0005f);
                    if (tVar.e(this, c0005f, c0005f3)) {
                        do {
                            LockSupport.park(this);
                            if (!Thread.interrupted()) {
                                obj = this.a;
                            } else {
                                e(c0005f3);
                                throw new InterruptedException();
                            }
                        } while (obj == null);
                        return c(obj);
                    }
                    c0005f = this.c;
                } while (c0005f != c0005f2);
            }
            return c(this.a);
        }
        throw new InterruptedException();
    }
}
