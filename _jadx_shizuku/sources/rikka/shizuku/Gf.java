package rikka.shizuku;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/* JADX INFO: loaded from: classes.dex */
public class Gf extends Kf {
    public final boolean c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Code duplicated, block: B:27:0x0043  */
    public Gf() {
        super(true);
        boolean z = true;
        A(null);
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = Kf.b;
        T5 t5 = (T5) atomicReferenceFieldUpdater.get(this);
        U5 u5 = t5 instanceof U5 ? (U5) t5 : null;
        if (u5 == null) {
            z = false;
            break;
        }
        Kf kf = u5.d;
        kf = kf == null ? null : kf;
        if (kf == null) {
            z = false;
            break;
        }
        while (!kf.v()) {
            T5 t6 = (T5) atomicReferenceFieldUpdater.get(kf);
            U5 u6 = t6 instanceof U5 ? (U5) t6 : null;
            if (u6 != null) {
                kf = u6.d;
                if (kf == null) {
                    kf = null;
                }
                if (kf == null) {
                }
            }
            z = false;
        }
        this.c = z;
    }

    @Override // rikka.shizuku.Kf
    public final boolean v() {
        return this.c;
    }

    @Override // rikka.shizuku.Kf
    public final boolean w() {
        return true;
    }
}
