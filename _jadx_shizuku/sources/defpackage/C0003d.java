package defpackage;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/* JADX INFO: renamed from: d, reason: case insensitive filesystem */
/* JADX INFO: loaded from: assets/rish_shizuku.dex */
public final class C0003d extends t {
    public final AtomicReferenceFieldUpdater p;
    public final AtomicReferenceFieldUpdater q;
    public final AtomicReferenceFieldUpdater r;
    public final AtomicReferenceFieldUpdater s;
    public final AtomicReferenceFieldUpdater t;

    public C0003d(AtomicReferenceFieldUpdater atomicReferenceFieldUpdater, AtomicReferenceFieldUpdater atomicReferenceFieldUpdater2, AtomicReferenceFieldUpdater atomicReferenceFieldUpdater3, AtomicReferenceFieldUpdater atomicReferenceFieldUpdater4, AtomicReferenceFieldUpdater atomicReferenceFieldUpdater5) {
        this.p = atomicReferenceFieldUpdater;
        this.q = atomicReferenceFieldUpdater2;
        this.r = atomicReferenceFieldUpdater3;
        this.s = atomicReferenceFieldUpdater4;
        this.t = atomicReferenceFieldUpdater5;
    }

    @Override // defpackage.t
    public final boolean c(AbstractFutureC0006g abstractFutureC0006g, C0002c c0002c) {
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater;
        C0002c c0002c2 = C0002c.b;
        do {
            atomicReferenceFieldUpdater = this.s;
            if (atomicReferenceFieldUpdater.compareAndSet(abstractFutureC0006g, c0002c, c0002c2)) {
                return true;
            }
        } while (atomicReferenceFieldUpdater.get(abstractFutureC0006g) == c0002c);
        return false;
    }

    @Override // defpackage.t
    public final boolean d(AbstractFutureC0006g abstractFutureC0006g, Object obj, Object obj2) {
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater;
        do {
            atomicReferenceFieldUpdater = this.t;
            if (atomicReferenceFieldUpdater.compareAndSet(abstractFutureC0006g, obj, obj2)) {
                return true;
            }
        } while (atomicReferenceFieldUpdater.get(abstractFutureC0006g) == obj);
        return false;
    }

    @Override // defpackage.t
    public final boolean e(AbstractFutureC0006g abstractFutureC0006g, C0005f c0005f, C0005f c0005f2) {
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater;
        do {
            atomicReferenceFieldUpdater = this.r;
            if (atomicReferenceFieldUpdater.compareAndSet(abstractFutureC0006g, c0005f, c0005f2)) {
                return true;
            }
        } while (atomicReferenceFieldUpdater.get(abstractFutureC0006g) == c0005f);
        return false;
    }

    @Override // defpackage.t
    public final void l(C0005f c0005f, C0005f c0005f2) {
        this.q.lazySet(c0005f, c0005f2);
    }

    @Override // defpackage.t
    public final void m(C0005f c0005f, Thread thread) {
        this.p.lazySet(c0005f, thread);
    }
}
