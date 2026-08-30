package rikka.shizuku;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/* JADX INFO: loaded from: classes.dex */
public abstract class H7 extends Q4 {
    public final V7 b;
    public transient G7 c;

    public H7(G7 g7, V7 v7) {
        super(g7);
        this.b = v7;
    }

    @Override // rikka.shizuku.G7
    public final V7 e() {
        return this.b;
    }

    @Override // rikka.shizuku.Q4
    public final void k() {
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater;
        G7 g7 = this.c;
        if (g7 != null && g7 != this) {
            ((W7) ((I7) this.b.g(Aa.a))).getClass();
            Z9 z9 = (Z9) g7;
            do {
                atomicReferenceFieldUpdater = Z9.h;
            } while (atomicReferenceFieldUpdater.get(z9) == X8.h);
            atomicReferenceFieldUpdater.get(z9);
        }
        this.c = I6.a;
    }
}
