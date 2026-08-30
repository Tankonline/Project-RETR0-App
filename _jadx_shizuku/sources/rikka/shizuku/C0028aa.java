package rikka.shizuku;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/* JADX INFO: renamed from: rikka.shizuku.aa, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0028aa extends Em {
    public static final /* synthetic */ AtomicIntegerFieldUpdater e = AtomicIntegerFieldUpdater.newUpdater(C0028aa.class, "_decision$volatile");
    private volatile /* synthetic */ int _decision$volatile;

    @Override // rikka.shizuku.Em, rikka.shizuku.Kf
    public final void m(Object obj) throws Y9 {
        n(obj);
    }

    @Override // rikka.shizuku.Em, rikka.shizuku.Kf
    public final void n(Object obj) throws Y9 {
        AtomicIntegerFieldUpdater atomicIntegerFieldUpdater;
        do {
            atomicIntegerFieldUpdater = e;
            int i = atomicIntegerFieldUpdater.get(this);
            if (i != 0) {
                if (i != 1) {
                    throw new IllegalStateException("Already resumed");
                }
                X8.U(AbstractC0744xh.R(obj), AbstractC0744xh.C(this.d));
                return;
            }
        } while (!atomicIntegerFieldUpdater.compareAndSet(this, 0, 2));
    }
}
