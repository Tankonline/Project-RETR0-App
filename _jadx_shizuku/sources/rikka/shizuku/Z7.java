package rikka.shizuku;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.LockSupport;

/* JADX INFO: loaded from: classes.dex */
public final class Z7 implements Executor, Closeable, AutoCloseable {
    public static final /* synthetic */ AtomicLongFieldUpdater h = AtomicLongFieldUpdater.newUpdater(Z7.class, "parkedWorkersStack$volatile");
    public static final /* synthetic */ AtomicLongFieldUpdater i = AtomicLongFieldUpdater.newUpdater(Z7.class, "controlState$volatile");
    public static final /* synthetic */ AtomicIntegerFieldUpdater j = AtomicIntegerFieldUpdater.newUpdater(Z7.class, "_isTerminated$volatile");
    public static final C0589sh k = new C0589sh(1, "NOT_IN_STACK");
    private volatile /* synthetic */ int _isTerminated$volatile;
    public final int a;
    public final int b;
    public final long c;
    private volatile /* synthetic */ long controlState$volatile;
    public final String d;
    public final Kd e;
    public final Kd f;
    public final Ql g;
    private volatile /* synthetic */ long parkedWorkersStack$volatile;

    public Z7(int i2, int i3, long j2, String str) {
        this.a = i2;
        this.b = i3;
        this.c = j2;
        this.d = str;
        if (i2 < 1) {
            throw new IllegalArgumentException(("Core pool size " + i2 + " should be at least 1").toString());
        }
        if (i3 < i2) {
            throw new IllegalArgumentException(("Max pool size " + i3 + " should be greater than or equals to core pool size " + i2).toString());
        }
        if (i3 > 2097150) {
            throw new IllegalArgumentException(("Max pool size " + i3 + " should not exceed maximal supported number of threads 2097150").toString());
        }
        if (j2 <= 0) {
            throw new IllegalArgumentException(("Idle worker keep alive time " + j2 + " must be positive").toString());
        }
        this.e = new Kd();
        this.f = new Kd();
        this.g = new Ql((i2 + 1) * 2);
        this.controlState$volatile = ((long) i2) << 42;
    }

    public final int a() {
        synchronized (this.g) {
            try {
                if (j.get(this) == 1) {
                    return -1;
                }
                AtomicLongFieldUpdater atomicLongFieldUpdater = i;
                long j2 = atomicLongFieldUpdater.get(this);
                int i2 = (int) (j2 & 2097151);
                int i3 = i2 - ((int) ((j2 & 4398044413952L) >> 21));
                if (i3 < 0) {
                    i3 = 0;
                }
                if (i3 >= this.a) {
                    return 0;
                }
                if (i2 >= this.b) {
                    return 0;
                }
                int i4 = ((int) (atomicLongFieldUpdater.get(this) & 2097151)) + 1;
                if (i4 <= 0 || this.g.b(i4) != null) {
                    throw new IllegalArgumentException("Failed requirement.");
                }
                Y7 y7 = new Y7(this, i4);
                this.g.c(i4, y7);
                if (i4 != ((int) (2097151 & atomicLongFieldUpdater.incrementAndGet(this)))) {
                    throw new IllegalArgumentException("Failed requirement.");
                }
                int i5 = i3 + 1;
                y7.start();
                return i5;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void b(Runnable runnable, boolean z) {
        AbstractRunnableC0721wp c0752xp;
        int i2;
        AtomicReferenceArray atomicReferenceArray;
        AbstractC0783yp.f.getClass();
        long jNanoTime = System.nanoTime();
        if (runnable instanceof AbstractRunnableC0721wp) {
            c0752xp = (AbstractRunnableC0721wp) runnable;
            c0752xp.a = jNanoTime;
            c0752xp.b = z;
        } else {
            c0752xp = new C0752xp(runnable, jNanoTime, z);
        }
        boolean z2 = c0752xp.b;
        AtomicLongFieldUpdater atomicLongFieldUpdater = i;
        long jAddAndGet = z2 ? atomicLongFieldUpdater.addAndGet(this, 2097152L) : 0L;
        Thread threadCurrentThread = Thread.currentThread();
        Y7 y7 = threadCurrentThread instanceof Y7 ? (Y7) threadCurrentThread : null;
        if (y7 == null || !AbstractC0046as.c(y7.h, this)) {
            y7 = null;
        }
        if (y7 != null && (i2 = y7.c) != 5 && (c0752xp.b || i2 != 2)) {
            y7.g = true;
            iu iuVar = y7.a;
            iuVar.getClass();
            c0752xp = (AbstractRunnableC0721wp) iu.b.getAndSet(iuVar, c0752xp);
            if (c0752xp == null) {
                c0752xp = null;
            } else {
                iuVar.getClass();
                AtomicIntegerFieldUpdater atomicIntegerFieldUpdater = iu.c;
                if (atomicIntegerFieldUpdater.get(iuVar) - iu.d.get(iuVar) != 127) {
                    if (c0752xp.b) {
                        iu.e.incrementAndGet(iuVar);
                    }
                    int i3 = atomicIntegerFieldUpdater.get(iuVar) & 127;
                    while (true) {
                        atomicReferenceArray = iuVar.a;
                        if (atomicReferenceArray.get(i3) == null) {
                            break;
                        } else {
                            Thread.yield();
                        }
                    }
                    atomicReferenceArray.lazySet(i3, c0752xp);
                    atomicIntegerFieldUpdater.incrementAndGet(iuVar);
                    c0752xp = null;
                }
            }
        }
        if (c0752xp != null) {
            if (!(c0752xp.b ? this.f.a(c0752xp) : this.e.a(c0752xp))) {
                throw new RejectedExecutionException(this.d + " was terminated");
            }
        }
        if (z2) {
            if (e() || d(jAddAndGet)) {
                return;
            }
            e();
            return;
        }
        if (e() || d(atomicLongFieldUpdater.get(this))) {
            return;
        }
        e();
    }

    public final void c(Y7 y7, int i2, int i3) {
        while (true) {
            long j2 = h.get(this);
            int i4 = (int) (2097151 & j2);
            long j3 = (2097152 + j2) & (-2097152);
            if (i4 == i2) {
                if (i3 == 0) {
                    Object objC = y7.c();
                    while (true) {
                        if (objC == k) {
                            i4 = -1;
                            break;
                        }
                        if (objC == null) {
                            i4 = 0;
                            break;
                        }
                        Y7 y8 = (Y7) objC;
                        int iB = y8.b();
                        if (iB != 0) {
                            i4 = iB;
                            break;
                        }
                        objC = y8.c();
                    }
                } else {
                    i4 = i3;
                }
            }
            if (i4 >= 0) {
                if (h.compareAndSet(this, j2, ((long) i4) | j3)) {
                    return;
                }
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:39:0x0087  */
    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() throws InterruptedException {
        int i2;
        AbstractRunnableC0721wp abstractRunnableC0721wpA;
        if (j.compareAndSet(this, 0, 1)) {
            Thread threadCurrentThread = Thread.currentThread();
            Y7 y7 = threadCurrentThread instanceof Y7 ? (Y7) threadCurrentThread : null;
            if (y7 == null || !AbstractC0046as.c(y7.h, this)) {
                y7 = null;
            }
            synchronized (this.g) {
                i2 = (int) (i.get(this) & 2097151);
            }
            if (1 <= i2) {
                int i3 = 1;
                while (true) {
                    Y7 y8 = (Y7) this.g.b(i3);
                    if (y8 != y7) {
                        while (y8.getState() != Thread.State.TERMINATED) {
                            LockSupport.unpark(y8);
                            y8.join(10000L);
                        }
                        iu iuVar = y8.a;
                        Kd kd = this.f;
                        iuVar.getClass();
                        AbstractRunnableC0721wp abstractRunnableC0721wp = (AbstractRunnableC0721wp) iu.b.getAndSet(iuVar, null);
                        if (abstractRunnableC0721wp != null) {
                            kd.a(abstractRunnableC0721wp);
                        }
                        while (true) {
                            AbstractRunnableC0721wp abstractRunnableC0721wpA2 = iuVar.a();
                            if (abstractRunnableC0721wpA2 == null) {
                                break;
                            } else {
                                kd.a(abstractRunnableC0721wpA2);
                            }
                        }
                    }
                    if (i3 == i2) {
                        break;
                    } else {
                        i3++;
                    }
                }
            }
            this.f.b();
            this.e.b();
            while (true) {
                if (y7 != null) {
                    abstractRunnableC0721wpA = y7.a(true);
                    if (abstractRunnableC0721wpA == null) {
                        abstractRunnableC0721wpA = (AbstractRunnableC0721wp) this.e.d();
                        if (abstractRunnableC0721wpA == null) {
                            break;
                            break;
                        }
                    }
                } else {
                    abstractRunnableC0721wpA = (AbstractRunnableC0721wp) this.e.d();
                    if (abstractRunnableC0721wpA == null && (abstractRunnableC0721wpA = (AbstractRunnableC0721wp) this.f.d()) == null) {
                        break;
                    }
                }
                try {
                    abstractRunnableC0721wpA.run();
                } catch (Throwable th) {
                    Thread threadCurrentThread2 = Thread.currentThread();
                    threadCurrentThread2.getUncaughtExceptionHandler().uncaughtException(threadCurrentThread2, th);
                }
            }
            if (y7 != null) {
                y7.h(5);
            }
            h.set(this, 0L);
            i.set(this, 0L);
        }
    }

    public final boolean d(long j2) {
        int i2 = ((int) (2097151 & j2)) - ((int) ((j2 & 4398044413952L) >> 21));
        if (i2 < 0) {
            i2 = 0;
        }
        int i3 = this.a;
        if (i2 < i3) {
            int iA = a();
            if (iA == 1 && i3 > 1) {
                a();
            }
            if (iA > 0) {
                return true;
            }
        }
        return false;
    }

    public final boolean e() {
        C0589sh c0589sh;
        int iB;
        while (true) {
            AtomicLongFieldUpdater atomicLongFieldUpdater = h;
            long j2 = atomicLongFieldUpdater.get(this);
            Y7 y7 = (Y7) this.g.b((int) (2097151 & j2));
            if (y7 == null) {
                y7 = null;
            } else {
                long j3 = (2097152 + j2) & (-2097152);
                Object objC = y7.c();
                while (true) {
                    c0589sh = k;
                    if (objC == c0589sh) {
                        iB = -1;
                        break;
                    }
                    if (objC == null) {
                        iB = 0;
                        break;
                    }
                    Y7 y8 = (Y7) objC;
                    iB = y8.b();
                    if (iB != 0) {
                        break;
                    }
                    objC = y8.c();
                }
                if (iB >= 0 && atomicLongFieldUpdater.compareAndSet(this, j2, j3 | ((long) iB))) {
                    y7.g(c0589sh);
                }
            }
            if (y7 == null) {
                return false;
            }
            if (Y7.i.compareAndSet(y7, -1, 0)) {
                LockSupport.unpark(y7);
                return true;
            }
        }
    }

    @Override // java.util.concurrent.Executor
    public final void execute(Runnable runnable) {
        b(runnable, false);
    }

    public final String toString() {
        ArrayList arrayList = new ArrayList();
        Ql ql = this.g;
        int iA = ql.a();
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        for (int i7 = 1; i7 < iA; i7++) {
            Y7 y7 = (Y7) ql.b(i7);
            if (y7 != null) {
                iu iuVar = y7.a;
                iuVar.getClass();
                int i8 = iu.b.get(iuVar) != null ? (iu.c.get(iuVar) - iu.d.get(iuVar)) + 1 : iu.c.get(iuVar) - iu.d.get(iuVar);
                int iJ = AbstractC0535qo.j(y7.c);
                if (iJ == 0) {
                    i2++;
                    StringBuilder sb = new StringBuilder();
                    sb.append(i8);
                    sb.append('c');
                    arrayList.add(sb.toString());
                } else if (iJ == 1) {
                    i3++;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(i8);
                    sb2.append('b');
                    arrayList.add(sb2.toString());
                } else if (iJ == 2) {
                    i4++;
                } else if (iJ == 3) {
                    i5++;
                    if (i8 > 0) {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(i8);
                        sb3.append('d');
                        arrayList.add(sb3.toString());
                    }
                } else {
                    if (iJ != 4) {
                        throw new K6();
                    }
                    i6++;
                }
            }
        }
        long j2 = i.get(this);
        StringBuilder sb4 = new StringBuilder();
        sb4.append(this.d);
        sb4.append('@');
        sb4.append(X8.B(this));
        sb4.append("[Pool Size {core = ");
        int i9 = this.a;
        sb4.append(i9);
        sb4.append(", max = ");
        sb4.append(this.b);
        sb4.append("}, Worker States {CPU = ");
        sb4.append(i2);
        sb4.append(", blocking = ");
        sb4.append(i3);
        sb4.append(", parked = ");
        sb4.append(i4);
        sb4.append(", dormant = ");
        sb4.append(i5);
        sb4.append(", terminated = ");
        sb4.append(i6);
        sb4.append("}, running workers queues = ");
        sb4.append(arrayList);
        sb4.append(", global CPU queue size = ");
        sb4.append(this.e.c());
        sb4.append(", global blocking queue size = ");
        sb4.append(this.f.c());
        sb4.append(", Control State {created workers= ");
        sb4.append((int) (2097151 & j2));
        sb4.append(", blocking tasks = ");
        sb4.append((int) ((4398044413952L & j2) >> 21));
        sb4.append(", CPUs acquired = ");
        sb4.append(i9 - ((int) ((j2 & 9223367638808264704L) >> 42)));
        sb4.append("}]");
        return sb4.toString();
    }
}
