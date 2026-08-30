package rikka.shizuku;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/* JADX INFO: renamed from: rikka.shizuku.oh, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0466oh {
    public static final /* synthetic */ AtomicReferenceFieldUpdater a = AtomicReferenceFieldUpdater.newUpdater(C0466oh.class, Object.class, "_cur$volatile");
    private volatile /* synthetic */ Object _cur$volatile = new C0528qh(8, false);

    public final boolean a(Runnable runnable) {
        while (true) {
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = a;
            C0528qh c0528qh = (C0528qh) atomicReferenceFieldUpdater.get(this);
            int iA = c0528qh.a(runnable);
            if (iA == 0) {
                return true;
            }
            if (iA == 1) {
                C0528qh c0528qhC = c0528qh.c();
                while (!atomicReferenceFieldUpdater.compareAndSet(this, c0528qh, c0528qhC) && atomicReferenceFieldUpdater.get(this) == c0528qh) {
                }
            } else if (iA == 2) {
                return false;
            }
        }
    }

    public final void b() {
        while (true) {
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = a;
            C0528qh c0528qh = (C0528qh) atomicReferenceFieldUpdater.get(this);
            if (c0528qh.b()) {
                return;
            }
            C0528qh c0528qhC = c0528qh.c();
            while (!atomicReferenceFieldUpdater.compareAndSet(this, c0528qh, c0528qhC) && atomicReferenceFieldUpdater.get(this) == c0528qh) {
            }
        }
    }

    public final int c() {
        C0528qh c0528qh = (C0528qh) a.get(this);
        c0528qh.getClass();
        long j = C0528qh.f.get(c0528qh);
        return (((int) ((j & 1152921503533105152L) >> 30)) - ((int) (1073741823 & j))) & 1073741823;
    }

    public final Object d() {
        while (true) {
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = a;
            C0528qh c0528qh = (C0528qh) atomicReferenceFieldUpdater.get(this);
            Object objD = c0528qh.d();
            if (objD != C0528qh.g) {
                return objD;
            }
            C0528qh c0528qhC = c0528qh.c();
            while (!atomicReferenceFieldUpdater.compareAndSet(this, c0528qh, c0528qhC) && atomicReferenceFieldUpdater.get(this) == c0528qh) {
            }
        }
    }
}
