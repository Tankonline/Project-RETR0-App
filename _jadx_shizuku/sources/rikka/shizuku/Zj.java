package rikka.shizuku;

/* JADX INFO: loaded from: classes.dex */
public final class Zj extends Yj {
    public final Object d;

    public Zj() {
        super(12);
        this.d = new Object();
    }

    @Override // rikka.shizuku.Yj
    public final Object a() {
        Object objA;
        synchronized (this.d) {
            objA = super.a();
        }
        return objA;
    }

    @Override // rikka.shizuku.Yj
    public final boolean c(Object obj) {
        boolean zC;
        synchronized (this.d) {
            zC = super.c(obj);
        }
        return zC;
    }
}
