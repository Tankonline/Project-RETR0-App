package rikka.shizuku;

/* JADX INFO: loaded from: classes.dex */
public final class U5 extends Hf implements T5 {
    public final Kf e;

    public U5(Kf kf) {
        this.e = kf;
    }

    @Override // rikka.shizuku.T5
    public final boolean c(Throwable th) {
        Kf kf = this.d;
        if (kf == null) {
            kf = null;
        }
        return kf.r(th);
    }

    @Override // rikka.shizuku.Hf
    public final boolean j() {
        return true;
    }

    @Override // rikka.shizuku.Hf
    public final void k(Throwable th) {
        Kf kf = this.d;
        if (kf == null) {
            kf = null;
        }
        this.e.o(kf);
    }
}
