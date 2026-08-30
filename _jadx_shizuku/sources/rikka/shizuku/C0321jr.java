package rikka.shizuku;

/* JADX INFO: renamed from: rikka.shizuku.jr, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0321jr implements Mq {
    public final /* synthetic */ Class a;
    public final /* synthetic */ Class b;
    public final /* synthetic */ Lq c;

    public C0321jr(Class cls, Class cls2, Lq lq) {
        this.a = cls;
        this.b = cls2;
        this.c = lq;
    }

    @Override // rikka.shizuku.Mq
    public final Lq a(Rd rd, C0630tr c0630tr) {
        Class cls = this.a;
        Class cls2 = c0630tr.a;
        if (cls2 == cls || cls2 == this.b) {
            return this.c;
        }
        return null;
    }

    public final String toString() {
        return "Factory[type=" + this.b.getName() + "+" + this.a.getName() + ",adapter=" + this.c + "]";
    }
}
