package rikka.shizuku;

/* JADX INFO: loaded from: classes.dex */
public final class Gr extends Em {
    public final ThreadLocal e;
    private volatile boolean threadLocalIsSet;

    /* JADX WARN: Illegal instructions before constructor call */
    public Gr(V7 v7, G3 g3) {
        Hr hr = Hr.a;
        super(v7.g(hr) == null ? v7.b(hr) : v7, g3);
        this.e = new ThreadLocal();
        if (g3.b.g(Aa.a) instanceof W7) {
            return;
        }
        Object objO = AbstractC0599sr.O(v7, null);
        AbstractC0599sr.E(v7, objO);
        P(v7, objO);
    }

    @Override // rikka.shizuku.Em
    public final void M() {
        O();
    }

    public final boolean N() {
        boolean z = this.threadLocalIsSet && this.e.get() == null;
        this.e.remove();
        return !z;
    }

    public final void O() {
        if (this.threadLocalIsSet) {
            Hj hj = (Hj) this.e.get();
            if (hj != null) {
                AbstractC0599sr.E((V7) hj.a, hj.b);
            }
            this.e.remove();
        }
    }

    public final void P(V7 v7, Object obj) {
        this.threadLocalIsSet = true;
        this.e.set(new Hj(v7, obj));
    }

    @Override // rikka.shizuku.Em, rikka.shizuku.Kf
    public final void n(Object obj) {
        O();
        Object objR = AbstractC0744xh.R(obj);
        G3 g3 = this.d;
        V7 v7 = g3.b;
        Object objO = AbstractC0599sr.O(v7, null);
        Gr grB0 = objO != AbstractC0599sr.r ? X8.b0(g3, v7, objO) : null;
        try {
            g3.h(objR);
        } finally {
            if (grB0 == null || grB0.N()) {
                AbstractC0599sr.E(v7, objO);
            }
        }
    }
}
