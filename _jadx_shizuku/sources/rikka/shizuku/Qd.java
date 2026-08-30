package rikka.shizuku;

/* JADX INFO: loaded from: classes.dex */
public class Qd extends Wm {
    public Lq a = null;

    @Override // rikka.shizuku.Lq
    public final Object b(Vf vf) {
        Lq lq = this.a;
        if (lq != null) {
            return lq.b(vf);
        }
        throw new IllegalStateException("Adapter for type with cyclic dependency has been used before dependency has been resolved");
    }

    @Override // rikka.shizuku.Lq
    public final void c(Wf wf, Object obj) {
        Lq lq = this.a;
        if (lq == null) {
            throw new IllegalStateException("Adapter for type with cyclic dependency has been used before dependency has been resolved");
        }
        lq.c(wf, obj);
    }

    @Override // rikka.shizuku.Wm
    public final Lq d() {
        Lq lq = this.a;
        if (lq != null) {
            return lq;
        }
        throw new IllegalStateException("Adapter for type with cyclic dependency has been used before dependency has been resolved");
    }
}
