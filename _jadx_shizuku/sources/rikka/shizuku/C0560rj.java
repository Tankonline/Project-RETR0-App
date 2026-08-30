package rikka.shizuku;

/* JADX INFO: renamed from: rikka.shizuku.rj, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0560rj implements InterfaceC0763y5 {
    public final Ic a;
    public final /* synthetic */ C0622tj b;

    public C0560rj(C0622tj c0622tj, Ic ic) {
        this.b = c0622tj;
        this.a = ic;
    }

    @Override // rikka.shizuku.InterfaceC0763y5
    public final void cancel() {
        C0622tj c0622tj = this.b;
        C0084c4 c0084c4 = c0622tj.b;
        Ic ic = this.a;
        c0084c4.remove(ic);
        if (AbstractC0046as.c(c0622tj.c, ic)) {
            ic.a();
            c0622tj.c = null;
        }
        ic.b.remove(this);
        C0591sj c0591sj = ic.c;
        if (c0591sj != null) {
            c0591sj.a();
        }
        ic.c = null;
    }
}
