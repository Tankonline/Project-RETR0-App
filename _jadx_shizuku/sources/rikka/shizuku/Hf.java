package rikka.shizuku;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/* JADX INFO: loaded from: classes.dex */
public abstract class Hf extends C0435nh implements InterfaceC0151ea, InterfaceC0187ff {
    public Kf d;

    @Override // rikka.shizuku.InterfaceC0187ff
    public final boolean a() {
        return true;
    }

    @Override // rikka.shizuku.InterfaceC0151ea
    public final void b() {
        Kf kf = this.d;
        if (kf == null) {
            kf = null;
        }
        kf.getClass();
        while (true) {
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = Kf.a;
            Object obj = atomicReferenceFieldUpdater.get(kf);
            if (obj instanceof Hf) {
                if (obj != this) {
                    return;
                }
                C0091cb c0091cb = AbstractC0599sr.l;
                while (!atomicReferenceFieldUpdater.compareAndSet(kf, obj, c0091cb)) {
                    if (atomicReferenceFieldUpdater.get(kf) != obj) {
                    }
                }
                return;
            }
            if (!(obj instanceof InterfaceC0187ff) || ((InterfaceC0187ff) obj).d() == null) {
                return;
            }
            while (true) {
                AtomicReferenceFieldUpdater atomicReferenceFieldUpdater2 = C0435nh.a;
                Object obj2 = atomicReferenceFieldUpdater2.get(this);
                if (obj2 instanceof Gl) {
                    C0435nh c0435nh = ((Gl) obj2).a;
                    return;
                }
                if (obj2 == this) {
                    return;
                }
                C0435nh c0435nh2 = (C0435nh) obj2;
                c0435nh2.getClass();
                AtomicReferenceFieldUpdater atomicReferenceFieldUpdater3 = C0435nh.c;
                Gl gl = (Gl) atomicReferenceFieldUpdater3.get(c0435nh2);
                if (gl == null) {
                    gl = new Gl(c0435nh2);
                    atomicReferenceFieldUpdater3.set(c0435nh2, gl);
                }
                do {
                    if (atomicReferenceFieldUpdater2.compareAndSet(this, obj2, gl)) {
                        c0435nh2.f();
                        return;
                    }
                } while (atomicReferenceFieldUpdater2.get(this) == obj2);
            }
        }
    }

    @Override // rikka.shizuku.InterfaceC0187ff
    public final C0037aj d() {
        return null;
    }

    public abstract boolean j();

    public abstract void k(Throwable th);

    @Override // rikka.shizuku.C0435nh
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append('@');
        sb.append(X8.B(this));
        sb.append("[job@");
        Kf kf = this.d;
        if (kf == null) {
            kf = null;
        }
        sb.append(X8.B(kf));
        sb.append(']');
        return sb.toString();
    }
}
