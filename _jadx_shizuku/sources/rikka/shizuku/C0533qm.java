package rikka.shizuku;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/* JADX INFO: renamed from: rikka.shizuku.qm, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0533qm implements InterfaceC0310jg, Serializable {
    public static final AtomicReferenceFieldUpdater c = AtomicReferenceFieldUpdater.newUpdater(C0533qm.class, Object.class, "b");
    public volatile InterfaceC0124dd a;
    public volatile Object b;

    @Override // rikka.shizuku.InterfaceC0310jg
    public final Object getValue() {
        Object obj = this.b;
        Ne ne = Ne.a;
        if (obj != ne) {
            return obj;
        }
        InterfaceC0124dd interfaceC0124dd = this.a;
        if (interfaceC0124dd != null) {
            Object objA = interfaceC0124dd.a();
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = c;
            while (!atomicReferenceFieldUpdater.compareAndSet(this, ne, objA)) {
                if (atomicReferenceFieldUpdater.get(this) != ne) {
                }
            }
            this.a = null;
            return objA;
        }
        return this.b;
    }

    public final String toString() {
        return this.b != Ne.a ? String.valueOf(getValue()) : "Lazy value not initialized yet.";
    }
}
