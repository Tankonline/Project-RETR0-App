package rikka.shizuku;

/* JADX INFO: renamed from: rikka.shizuku.hr, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0261hr implements Mq {
    public final /* synthetic */ int a;
    public final /* synthetic */ Class b;
    public final /* synthetic */ Lq c;

    public /* synthetic */ C0261hr(Class cls, Lq lq, int i) {
        this.a = i;
        this.b = cls;
        this.c = lq;
    }

    @Override // rikka.shizuku.Mq
    public final Lq a(Rd rd, C0630tr c0630tr) {
        switch (this.a) {
            case 0:
                if (c0630tr.a == this.b) {
                    return this.c;
                }
                return null;
            default:
                Class cls = this.b;
                Class<?> cls2 = c0630tr.a;
                if (cls.isAssignableFrom(cls2)) {
                    return new C0609t6(this, cls2);
                }
                return null;
        }
    }

    public final String toString() {
        switch (this.a) {
            case 0:
                return "Factory[type=" + this.b.getName() + ",adapter=" + this.c + "]";
            default:
                return "Factory[typeHierarchy=" + this.b.getName() + ",adapter=" + this.c + "]";
        }
    }
}
