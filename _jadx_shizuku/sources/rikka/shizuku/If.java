package rikka.shizuku;

/* JADX INFO: loaded from: classes.dex */
public final class If extends Hf {
    public final Kf e;
    public final Jf f;
    public final U5 g;
    public final Object h;

    public If(Kf kf, Jf jf, U5 u5, Object obj) {
        this.e = kf;
        this.f = jf;
        this.g = u5;
        this.h = obj;
    }

    @Override // rikka.shizuku.Hf
    public final boolean j() {
        return false;
    }

    @Override // rikka.shizuku.Hf
    public final void k(Throwable th) {
        U5 u5 = this.g;
        Kf kf = this.e;
        kf.getClass();
        U5 u5D = Kf.D(u5);
        Jf jf = this.f;
        Object obj = this.h;
        if (u5D == null || !kf.L(jf, u5D, obj)) {
            jf.a.e(new Qg(2), 2);
            U5 u5D2 = Kf.D(u5);
            if (u5D2 == null || !kf.L(jf, u5D2, obj)) {
                kf.m(kf.u(jf, obj));
            }
        }
    }
}
