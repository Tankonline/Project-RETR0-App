package rikka.shizuku;

/* JADX INFO: loaded from: classes.dex */
public abstract class Lq {
    public final Kq a() {
        return !(this instanceof Kq) ? new Kq(this) : (Kq) this;
    }

    public abstract Object b(Vf vf);

    public abstract void c(Wf wf, Object obj);
}
