package rikka.shizuku;

/* JADX INFO: loaded from: classes.dex */
public class Em extends H implements InterfaceC0088c8 {
    public final G3 d;

    public Em(V7 v7, G3 g3) {
        super(v7, true);
        this.d = g3;
    }

    @Override // rikka.shizuku.Kf
    public final boolean B() {
        return true;
    }

    @Override // rikka.shizuku.InterfaceC0088c8
    public final InterfaceC0088c8 d() {
        G3 g3 = this.d;
        if (g3 != null) {
            return g3;
        }
        return null;
    }

    @Override // rikka.shizuku.Kf
    public void m(Object obj) {
        X8.U(AbstractC0744xh.R(obj), AbstractC0744xh.C(this.d));
    }

    @Override // rikka.shizuku.Kf
    public void n(Object obj) {
        this.d.h(AbstractC0744xh.R(obj));
    }

    public void M() {
    }
}
