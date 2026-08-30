package rikka.shizuku;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.concurrent.locks.LockSupport;

/* JADX INFO: renamed from: rikka.shizuku.vb, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0676vb extends AbstractC0707wb implements J9 {
    public static final /* synthetic */ AtomicReferenceFieldUpdater e = AtomicReferenceFieldUpdater.newUpdater(AbstractC0676vb.class, Object.class, "_queue$volatile");
    public static final /* synthetic */ AtomicReferenceFieldUpdater f = AtomicReferenceFieldUpdater.newUpdater(AbstractC0676vb.class, Object.class, "_delayed$volatile");
    public static final /* synthetic */ AtomicIntegerFieldUpdater g = AtomicIntegerFieldUpdater.newUpdater(AbstractC0676vb.class, "_isCompleted$volatile");
    private volatile /* synthetic */ Object _delayed$volatile;
    private volatile /* synthetic */ int _isCompleted$volatile;
    private volatile /* synthetic */ Object _queue$volatile;

    @Override // rikka.shizuku.W7
    public final void d(V7 v7, Runnable runnable) {
        p(runnable);
    }

    @Override // rikka.shizuku.AbstractC0707wb
    public void o() {
        Np.a.set(null);
        g.set(this, 1);
        loop0: while (true) {
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = e;
            Object obj = atomicReferenceFieldUpdater.get(this);
            C0589sh c0589sh = AbstractC0744xh.f;
            if (obj == null) {
                do {
                    if (atomicReferenceFieldUpdater.compareAndSet(this, null, c0589sh)) {
                        break loop0;
                    }
                } while (atomicReferenceFieldUpdater.get(this) == null);
            } else if (obj instanceof C0528qh) {
                ((C0528qh) obj).b();
                break;
            } else {
                if (obj == c0589sh) {
                    break;
                }
                C0528qh c0528qh = new C0528qh(8, true);
                c0528qh.a((Runnable) obj);
                do {
                    if (atomicReferenceFieldUpdater.compareAndSet(this, obj, c0528qh)) {
                        break loop0;
                    }
                } while (atomicReferenceFieldUpdater.get(this) == obj);
            }
        }
        while (r() <= 0) {
        }
        System.nanoTime();
    }

    public void p(Runnable runnable) {
        loop0: while (true) {
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = e;
            Object obj = atomicReferenceFieldUpdater.get(this);
            if (g.get(this) != 1) {
                if (obj == null) {
                    do {
                        if (atomicReferenceFieldUpdater.compareAndSet(this, null, runnable)) {
                            break loop0;
                        }
                    } while (atomicReferenceFieldUpdater.get(this) == null);
                } else if (obj instanceof C0528qh) {
                    C0528qh c0528qh = (C0528qh) obj;
                    int iA = c0528qh.a(runnable);
                    if (iA == 0) {
                        break;
                    }
                    if (iA == 1) {
                        C0528qh c0528qhC = c0528qh.c();
                        while (!atomicReferenceFieldUpdater.compareAndSet(this, obj, c0528qhC) && atomicReferenceFieldUpdater.get(this) == obj) {
                        }
                    } else if (iA != 2) {
                    }
                } else if (obj != AbstractC0744xh.f) {
                    C0528qh c0528qh2 = new C0528qh(8, true);
                    c0528qh2.a((Runnable) obj);
                    c0528qh2.a(runnable);
                    do {
                        if (atomicReferenceFieldUpdater.compareAndSet(this, obj, c0528qh2)) {
                            break loop0;
                        }
                    } while (atomicReferenceFieldUpdater.get(this) == obj);
                }
            }
            RunnableC0150e9.h.p(runnable);
            return;
        }
        Thread threadM = m();
        if (Thread.currentThread() != threadM) {
            LockSupport.unpark(threadM);
        }
    }

    public final boolean q() {
        C0084c4 c0084c4 = this.d;
        if (c0084c4 != null ? c0084c4.isEmpty() : true) {
            Object obj = e.get(this);
            if (obj != null) {
                if (obj instanceof C0528qh) {
                    long j = C0528qh.f.get((C0528qh) obj);
                    return ((int) (1073741823 & j)) == ((int) ((j & 1152921503533105152L) >> 30));
                }
                if (obj == AbstractC0744xh.f) {
                }
            }
            return true;
        }
        return false;
    }

    public final long r() {
        Runnable runnable;
        if (!n()) {
            loop0: while (true) {
                AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = e;
                Object obj = atomicReferenceFieldUpdater.get(this);
                runnable = null;
                if (obj == null) {
                    break;
                }
                if (obj instanceof C0528qh) {
                    C0528qh c0528qh = (C0528qh) obj;
                    Object objD = c0528qh.d();
                    if (objD != C0528qh.g) {
                        runnable = (Runnable) objD;
                        break;
                    }
                    C0528qh c0528qhC = c0528qh.c();
                    while (!atomicReferenceFieldUpdater.compareAndSet(this, obj, c0528qhC) && atomicReferenceFieldUpdater.get(this) == obj) {
                    }
                } else {
                    if (obj == AbstractC0744xh.f) {
                        break;
                    }
                    do {
                        if (atomicReferenceFieldUpdater.compareAndSet(this, obj, null)) {
                            runnable = (Runnable) obj;
                            break loop0;
                        }
                    } while (atomicReferenceFieldUpdater.get(this) == obj);
                }
            }
            if (runnable != null) {
                runnable.run();
                return 0L;
            }
            C0084c4 c0084c4 = this.d;
            if (((c0084c4 == null || c0084c4.isEmpty()) ? Long.MAX_VALUE : 0L) != 0) {
                Object obj2 = e.get(this);
                if (obj2 == null) {
                } else if (obj2 instanceof C0528qh) {
                    long j = C0528qh.f.get((C0528qh) obj2);
                    if (((int) (1073741823 & j)) != ((int) ((j & 1152921503533105152L) >> 30))) {
                        return 0L;
                    }
                } else if (obj2 == AbstractC0744xh.f) {
                }
                return Long.MAX_VALUE;
            }
        }
        return 0L;
    }
}
