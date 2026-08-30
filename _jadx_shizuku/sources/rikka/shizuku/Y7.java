package rikka.shizuku;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.concurrent.locks.LockSupport;

/* JADX INFO: loaded from: classes.dex */
public final class Y7 extends Thread {
    public static final /* synthetic */ AtomicIntegerFieldUpdater i = AtomicIntegerFieldUpdater.newUpdater(Y7.class, "workerCtl$volatile");
    public final iu a;
    public final C0562rl b;
    public int c;
    public long d;
    public long e;
    public int f;
    public boolean g;
    public final /* synthetic */ Z7 h;
    private volatile int indexInArray;
    private volatile Object nextParkedWorker;
    private volatile /* synthetic */ int workerCtl$volatile;

    public Y7(Z7 z7, int i2) {
        this.h = z7;
        setDaemon(true);
        setContextClassLoader(z7.getClass().getClassLoader());
        this.a = new iu();
        this.b = new C0562rl();
        this.c = 4;
        this.nextParkedWorker = Z7.k;
        int iNanoTime = (int) System.nanoTime();
        this.f = iNanoTime == 0 ? 42 : iNanoTime;
        f(i2);
    }

    public final AbstractRunnableC0721wp a(boolean z) {
        AbstractRunnableC0721wp abstractRunnableC0721wpE;
        AbstractRunnableC0721wp abstractRunnableC0721wpE2;
        Z7 z7;
        long j;
        int i2 = this.c;
        AbstractRunnableC0721wp abstractRunnableC0721wp = null;
        iu iuVar = this.a;
        Z7 z8 = this.h;
        if (i2 != 1) {
            AtomicLongFieldUpdater atomicLongFieldUpdater = Z7.i;
            do {
                z7 = this.h;
                j = atomicLongFieldUpdater.get(z7);
                if (((int) ((9223367638808264704L & j) >> 42)) == 0) {
                    iuVar.getClass();
                    loop1: while (true) {
                        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = iu.b;
                        AbstractRunnableC0721wp abstractRunnableC0721wp2 = (AbstractRunnableC0721wp) atomicReferenceFieldUpdater.get(iuVar);
                        if (abstractRunnableC0721wp2 == null || !abstractRunnableC0721wp2.b) {
                            int i3 = iu.d.get(iuVar);
                            int i4 = iu.c.get(iuVar);
                            while (i3 != i4 && iu.e.get(iuVar) != 0) {
                                i4--;
                                AbstractRunnableC0721wp abstractRunnableC0721wpB = iuVar.b(i4, true);
                                if (abstractRunnableC0721wpB != null) {
                                    abstractRunnableC0721wp = abstractRunnableC0721wpB;
                                    break;
                                }
                            }
                            break;
                        }
                        do {
                            if (atomicReferenceFieldUpdater.compareAndSet(iuVar, abstractRunnableC0721wp2, null)) {
                                abstractRunnableC0721wp = abstractRunnableC0721wp2;
                                break loop1;
                            }
                        } while (atomicReferenceFieldUpdater.get(iuVar) == abstractRunnableC0721wp2);
                    }
                    if (abstractRunnableC0721wp != null) {
                        return abstractRunnableC0721wp;
                    }
                    AbstractRunnableC0721wp abstractRunnableC0721wp3 = (AbstractRunnableC0721wp) z8.f.d();
                    return abstractRunnableC0721wp3 == null ? i(1) : abstractRunnableC0721wp3;
                }
            } while (!Z7.i.compareAndSet(z7, j, j - 4398046511104L));
            this.c = 1;
        }
        if (z) {
            boolean z2 = d(z8.a * 2) == 0;
            if (z2 && (abstractRunnableC0721wpE2 = e()) != null) {
                return abstractRunnableC0721wpE2;
            }
            iuVar.getClass();
            AbstractRunnableC0721wp abstractRunnableC0721wpA = (AbstractRunnableC0721wp) iu.b.getAndSet(iuVar, null);
            if (abstractRunnableC0721wpA == null) {
                abstractRunnableC0721wpA = iuVar.a();
            }
            if (abstractRunnableC0721wpA != null) {
                return abstractRunnableC0721wpA;
            }
            if (!z2 && (abstractRunnableC0721wpE = e()) != null) {
                return abstractRunnableC0721wpE;
            }
        } else {
            AbstractRunnableC0721wp abstractRunnableC0721wpE3 = e();
            if (abstractRunnableC0721wpE3 != null) {
                return abstractRunnableC0721wpE3;
            }
        }
        return i(3);
    }

    public final int b() {
        return this.indexInArray;
    }

    public final Object c() {
        return this.nextParkedWorker;
    }

    public final int d(int i2) {
        int i3 = this.f;
        int i4 = i3 ^ (i3 << 13);
        int i5 = i4 ^ (i4 >> 17);
        int i6 = i5 ^ (i5 << 5);
        this.f = i6;
        int i7 = i2 - 1;
        return (i7 & i2) == 0 ? i6 & i7 : (i6 & Integer.MAX_VALUE) % i2;
    }

    public final AbstractRunnableC0721wp e() {
        int iD = d(2);
        Z7 z7 = this.h;
        if (iD == 0) {
            AbstractRunnableC0721wp abstractRunnableC0721wp = (AbstractRunnableC0721wp) z7.e.d();
            return abstractRunnableC0721wp != null ? abstractRunnableC0721wp : (AbstractRunnableC0721wp) z7.f.d();
        }
        AbstractRunnableC0721wp abstractRunnableC0721wp2 = (AbstractRunnableC0721wp) z7.f.d();
        return abstractRunnableC0721wp2 != null ? abstractRunnableC0721wp2 : (AbstractRunnableC0721wp) z7.e.d();
    }

    public final void f(int i2) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.h.d);
        sb.append("-worker-");
        sb.append(i2 == 0 ? "TERMINATED" : String.valueOf(i2));
        setName(sb.toString());
        this.indexInArray = i2;
    }

    public final void g(Object obj) {
        this.nextParkedWorker = obj;
    }

    public final boolean h(int i2) {
        int i3 = this.c;
        boolean z = i3 == 1;
        if (z) {
            Z7.i.addAndGet(this.h, 4398046511104L);
        }
        if (i3 != i2) {
            this.c = i2;
        }
        return z;
    }

    public final AbstractRunnableC0721wp i(int i2) {
        int i3;
        long j;
        AbstractRunnableC0721wp abstractRunnableC0721wpB;
        long j2;
        long j3;
        AtomicLongFieldUpdater atomicLongFieldUpdater = Z7.i;
        Z7 z7 = this.h;
        int i4 = (int) (atomicLongFieldUpdater.get(z7) & 2097151);
        AbstractRunnableC0721wp abstractRunnableC0721wp = null;
        if (i4 < 2) {
            return null;
        }
        int iD = d(i4);
        int i5 = 0;
        long jMin = Long.MAX_VALUE;
        while (i5 < i4) {
            int i6 = iD + 1;
            if (i6 > i4) {
                i6 = 1;
            }
            Y7 y7 = (Y7) z7.g.b(i6);
            if (y7 == null || y7 == this) {
                i3 = i6;
            } else {
                iu iuVar = y7.a;
                if (i2 != 3) {
                    iuVar.getClass();
                    int i7 = iu.d.get(iuVar);
                    int i8 = iu.c.get(iuVar);
                    boolean z = i2 == 1;
                    while (true) {
                        if (i7 != i8) {
                            j = 0;
                            if (!z || iu.e.get(iuVar) != 0) {
                                int i9 = i7 + 1;
                                abstractRunnableC0721wpB = iuVar.b(i7, z);
                                if (abstractRunnableC0721wpB != null) {
                                    break;
                                }
                                i7 = i9;
                            }
                        } else {
                            j = 0;
                        }
                        abstractRunnableC0721wpB = abstractRunnableC0721wp;
                        break;
                    }
                } else {
                    abstractRunnableC0721wpB = iuVar.a();
                    j = 0;
                }
                C0562rl c0562rl = this.b;
                if (abstractRunnableC0721wpB == null) {
                    while (true) {
                        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = iu.b;
                        AbstractRunnableC0721wp abstractRunnableC0721wp2 = (AbstractRunnableC0721wp) atomicReferenceFieldUpdater.get(iuVar);
                        if (abstractRunnableC0721wp2 == null) {
                            j2 = -1;
                        } else {
                            j2 = -1;
                            if (((abstractRunnableC0721wp2.b ? 1 : 2) & i2) != 0) {
                                AbstractC0783yp.f.getClass();
                                i3 = i6;
                                long jNanoTime = System.nanoTime() - abstractRunnableC0721wp2.a;
                                long j4 = AbstractC0783yp.b;
                                if (jNanoTime < j4) {
                                    j3 = j4 - jNanoTime;
                                    abstractRunnableC0721wp = null;
                                    break;
                                }
                                do {
                                    abstractRunnableC0721wp = null;
                                    if (atomicReferenceFieldUpdater.compareAndSet(iuVar, abstractRunnableC0721wp2, null)) {
                                        c0562rl.a = abstractRunnableC0721wp2;
                                        j3 = -1;
                                        break;
                                    }
                                } while (atomicReferenceFieldUpdater.get(iuVar) == abstractRunnableC0721wp2);
                                i6 = i3;
                                abstractRunnableC0721wp = null;
                            }
                        }
                        j3 = -2;
                        i3 = i6;
                        break;
                    }
                } else {
                    c0562rl.a = abstractRunnableC0721wpB;
                    i3 = i6;
                    j3 = -1;
                    j2 = -1;
                }
                if (j3 == j2) {
                    AbstractRunnableC0721wp abstractRunnableC0721wp3 = (AbstractRunnableC0721wp) c0562rl.a;
                    c0562rl.a = abstractRunnableC0721wp;
                    return abstractRunnableC0721wp3;
                }
                if (j3 > j) {
                    jMin = Math.min(jMin, j3);
                }
            }
            i5++;
            iD = i3;
            abstractRunnableC0721wp = null;
        }
        if (jMin == Long.MAX_VALUE) {
            jMin = 0;
        }
        this.e = jMin;
        return null;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final void run() {
        AtomicLongFieldUpdater atomicLongFieldUpdater;
        long j;
        int i2;
        loop0: while (true) {
            boolean z = false;
            while (true) {
                Z7 z7 = this.h;
                z7.getClass();
                if (Z7.j.get(z7) != 1 && this.c != 5) {
                    AbstractRunnableC0721wp abstractRunnableC0721wpA = a(this.g);
                    if (abstractRunnableC0721wpA == null) {
                        this.g = false;
                        if (this.e == 0) {
                            Object obj = this.nextParkedWorker;
                            C0589sh c0589sh = Z7.k;
                            long j2 = 2097151;
                            if (obj != c0589sh) {
                                i.set(this, -1);
                                while (this.nextParkedWorker != Z7.k) {
                                    AtomicIntegerFieldUpdater atomicIntegerFieldUpdater = i;
                                    if (atomicIntegerFieldUpdater.get(this) != -1) {
                                        break;
                                    }
                                    Z7 z8 = this.h;
                                    z8.getClass();
                                    AtomicIntegerFieldUpdater atomicIntegerFieldUpdater2 = Z7.j;
                                    if (atomicIntegerFieldUpdater2.get(z8) == 1 || this.c == 5) {
                                        break;
                                    }
                                    h(3);
                                    Thread.interrupted();
                                    if (this.d == 0) {
                                        this.d = System.nanoTime() + this.h.c;
                                    }
                                    LockSupport.parkNanos(this.h.c);
                                    if (System.nanoTime() - this.d >= 0) {
                                        this.d = 0L;
                                        Z7 z9 = this.h;
                                        synchronized (z9.g) {
                                            try {
                                                if (!(atomicIntegerFieldUpdater2.get(z9) == 1)) {
                                                    AtomicLongFieldUpdater atomicLongFieldUpdater2 = Z7.i;
                                                    if (((int) (atomicLongFieldUpdater2.get(z9) & j2)) > z9.a) {
                                                        if (atomicIntegerFieldUpdater.compareAndSet(this, -1, 1)) {
                                                            int i3 = this.indexInArray;
                                                            f(0);
                                                            z9.c(this, i3, 0);
                                                            int andDecrement = (int) (atomicLongFieldUpdater2.getAndDecrement(z9) & j2);
                                                            if (andDecrement != i3) {
                                                                Y7 y7 = (Y7) z9.g.b(andDecrement);
                                                                z9.g.c(i3, y7);
                                                                y7.f(i3);
                                                                z9.c(y7, andDecrement, i3);
                                                            }
                                                            z9.g.c(andDecrement, null);
                                                            this.c = 5;
                                                        }
                                                    }
                                                }
                                            } catch (Throwable th) {
                                                throw th;
                                            }
                                        }
                                    }
                                    j2 = j2;
                                }
                            } else {
                                Z7 z10 = this.h;
                                z10.getClass();
                                if (this.nextParkedWorker == c0589sh) {
                                    do {
                                        atomicLongFieldUpdater = Z7.h;
                                        j = atomicLongFieldUpdater.get(z10);
                                        i2 = this.indexInArray;
                                        this.nextParkedWorker = z10.g.b((int) (j & 2097151));
                                    } while (!atomicLongFieldUpdater.compareAndSet(z10, j, ((j + 2097152) & (-2097152)) | ((long) i2)));
                                }
                            }
                        } else {
                            if (z) {
                                h(3);
                                Thread.interrupted();
                                LockSupport.parkNanos(this.e);
                                this.e = 0L;
                                break;
                            }
                            z = true;
                        }
                    } else {
                        this.e = 0L;
                        this.d = 0L;
                        if (this.c == 3) {
                            this.c = 2;
                        }
                        boolean z2 = abstractRunnableC0721wpA.b;
                        Z7 z11 = this.h;
                        if (!z2) {
                            z11.getClass();
                            try {
                                abstractRunnableC0721wpA.run();
                                break;
                            } catch (Throwable th2) {
                                Thread threadCurrentThread = Thread.currentThread();
                                threadCurrentThread.getUncaughtExceptionHandler().uncaughtException(threadCurrentThread, th2);
                                break;
                            }
                        }
                        if (h(2) && !z11.e() && !z11.d(Z7.i.get(z11))) {
                            z11.e();
                        }
                        z11.getClass();
                        try {
                            abstractRunnableC0721wpA.run();
                        } catch (Throwable th3) {
                            Thread threadCurrentThread2 = Thread.currentThread();
                            threadCurrentThread2.getUncaughtExceptionHandler().uncaughtException(threadCurrentThread2, th3);
                        }
                        Z7.i.addAndGet(z11, -2097152L);
                        if (this.c == 5) {
                            break;
                        }
                        this.c = 4;
                        break;
                    }
                } else {
                    break loop0;
                }
            }
        }
        h(5);
    }
}
