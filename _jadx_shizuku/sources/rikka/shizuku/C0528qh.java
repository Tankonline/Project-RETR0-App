package rikka.shizuku;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/* JADX INFO: renamed from: rikka.shizuku.qh, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0528qh {
    public static final /* synthetic */ AtomicReferenceFieldUpdater e = AtomicReferenceFieldUpdater.newUpdater(C0528qh.class, Object.class, "_next$volatile");
    public static final /* synthetic */ AtomicLongFieldUpdater f = AtomicLongFieldUpdater.newUpdater(C0528qh.class, "_state$volatile");
    public static final C0589sh g = new C0589sh(1, "REMOVE_FROZEN");
    private volatile /* synthetic */ Object _next$volatile;
    private volatile /* synthetic */ long _state$volatile;
    public final int a;
    public final boolean b;
    public final int c;
    public final /* synthetic */ AtomicReferenceArray d;

    public C0528qh(int i, boolean z) {
        this.a = i;
        this.b = z;
        int i2 = i - 1;
        this.c = i2;
        this.d = new AtomicReferenceArray(i);
        if (i2 > 1073741823) {
            throw new IllegalStateException("Check failed.");
        }
        if ((i & i2) != 0) {
            throw new IllegalStateException("Check failed.");
        }
    }

    public final int a(Runnable runnable) {
        while (true) {
            AtomicLongFieldUpdater atomicLongFieldUpdater = f;
            long j = atomicLongFieldUpdater.get(this);
            if ((3458764513820540928L & j) != 0) {
                return (2305843009213693952L & j) != 0 ? 2 : 1;
            }
            int i = (int) (1073741823 & j);
            int i2 = (int) ((1152921503533105152L & j) >> 30);
            int i3 = this.c;
            if (((i2 + 2) & i3) == (i & i3)) {
                return 1;
            }
            AtomicReferenceArray atomicReferenceArray = this.d;
            if (!this.b && atomicReferenceArray.get(i2 & i3) != null) {
                int i4 = this.a;
                if (i4 < 1024 || ((i2 - i) & 1073741823) > (i4 >> 1)) {
                    return 1;
                }
            } else if (atomicLongFieldUpdater.compareAndSet(this, j, ((-1152921503533105153L) & j) | (((long) ((i2 + 1) & 1073741823)) << 30))) {
                atomicReferenceArray.set(i2 & i3, runnable);
                C0528qh c0528qhC = this;
                while ((atomicLongFieldUpdater.get(c0528qhC) & 1152921504606846976L) != 0) {
                    c0528qhC = c0528qhC.c();
                    AtomicReferenceArray atomicReferenceArray2 = c0528qhC.d;
                    int i5 = c0528qhC.c & i2;
                    Object obj = atomicReferenceArray2.get(i5);
                    if ((obj instanceof C0497ph) && ((C0497ph) obj).a == i2) {
                        atomicReferenceArray2.set(i5, runnable);
                    } else {
                        c0528qhC = null;
                    }
                    if (c0528qhC == null) {
                        return 0;
                    }
                }
                return 0;
            }
        }
    }

    public final boolean b() {
        AtomicLongFieldUpdater atomicLongFieldUpdater;
        long j;
        do {
            atomicLongFieldUpdater = f;
            j = atomicLongFieldUpdater.get(this);
            if ((j & 2305843009213693952L) != 0) {
                return true;
            }
            if ((1152921504606846976L & j) != 0) {
                return false;
            }
        } while (!atomicLongFieldUpdater.compareAndSet(this, j, 2305843009213693952L | j));
        return true;
    }

    public final C0528qh c() {
        AtomicLongFieldUpdater atomicLongFieldUpdater;
        long j;
        C0528qh c0528qh;
        while (true) {
            atomicLongFieldUpdater = f;
            j = atomicLongFieldUpdater.get(this);
            if ((j & 1152921504606846976L) != 0) {
                c0528qh = this;
                break;
            }
            long j2 = 1152921504606846976L | j;
            c0528qh = this;
            if (atomicLongFieldUpdater.compareAndSet(c0528qh, j, j2)) {
                j = j2;
                break;
            }
        }
        while (true) {
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = e;
            C0528qh c0528qh2 = (C0528qh) atomicReferenceFieldUpdater.get(this);
            if (c0528qh2 != null) {
                return c0528qh2;
            }
            C0528qh c0528qh3 = new C0528qh(c0528qh.a * 2, c0528qh.b);
            int i = (int) (1073741823 & j);
            int i2 = (int) ((1152921503533105152L & j) >> 30);
            while (true) {
                int i3 = c0528qh.c;
                int i4 = i & i3;
                if (i4 == (i3 & i2)) {
                    break;
                }
                Object c0497ph = c0528qh.d.get(i4);
                if (c0497ph == null) {
                    c0497ph = new C0497ph(i);
                }
                c0528qh3.d.set(c0528qh3.c & i, c0497ph);
                i++;
            }
            atomicLongFieldUpdater.set(c0528qh3, (-1152921504606846977L) & j);
            while (!atomicReferenceFieldUpdater.compareAndSet(this, null, c0528qh3) && atomicReferenceFieldUpdater.get(this) == null) {
            }
        }
    }

    public final Object d() {
        C0528qh c0528qhC = this;
        while (true) {
            AtomicLongFieldUpdater atomicLongFieldUpdater = f;
            long j = atomicLongFieldUpdater.get(c0528qhC);
            if ((j & 1152921504606846976L) != 0) {
                return g;
            }
            int i = (int) (j & 1073741823);
            int i2 = c0528qhC.c;
            int i3 = ((int) ((1152921503533105152L & j) >> 30)) & i2;
            int i4 = i2 & i;
            if (i3 != i4) {
                AtomicReferenceArray atomicReferenceArray = c0528qhC.d;
                Object obj = atomicReferenceArray.get(i4);
                boolean z = c0528qhC.b;
                if (obj == null) {
                    if (z) {
                    }
                } else if (!(obj instanceof C0497ph)) {
                    long j2 = (i + 1) & 1073741823;
                    if (atomicLongFieldUpdater.compareAndSet(c0528qhC, j, (j & (-1073741824)) | j2)) {
                        atomicReferenceArray.set(i4, null);
                        return obj;
                    }
                    c0528qhC = this;
                    if (z) {
                        while (true) {
                            AtomicLongFieldUpdater atomicLongFieldUpdater2 = f;
                            long j3 = atomicLongFieldUpdater2.get(c0528qhC);
                            int i5 = (int) (j3 & 1073741823);
                            if ((j3 & 1152921504606846976L) != 0) {
                                c0528qhC = c0528qhC.c();
                            } else {
                                C0528qh c0528qh = c0528qhC;
                                c0528qhC = c0528qh;
                                if (atomicLongFieldUpdater2.compareAndSet(c0528qh, j3, (j3 & (-1073741824)) | j2)) {
                                    c0528qhC.d.set(c0528qhC.c & i5, null);
                                    c0528qhC = null;
                                } else {
                                    continue;
                                }
                            }
                            if (c0528qhC == null) {
                                return obj;
                            }
                        }
                    }
                }
            }
            return null;
        }
    }
}
