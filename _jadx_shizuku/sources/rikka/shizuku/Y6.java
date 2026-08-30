package rikka.shizuku;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
public final class Y6 implements Rm {
    public final AtomicReference a;

    public Y6(Vm vm) {
        this.a = new AtomicReference(vm);
    }

    @Override // rikka.shizuku.Rm
    public final Iterator iterator() {
        Rm rm = (Rm) this.a.getAndSet(null);
        if (rm != null) {
            return rm.iterator();
        }
        throw new IllegalStateException("This sequence can be consumed only once.");
    }
}
