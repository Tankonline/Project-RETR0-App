package rikka.shizuku;

/* JADX INFO: loaded from: classes.dex */
public abstract class H extends Kf implements G7, InterfaceC0026a8 {
    public final V7 c;

    public H(V7 v7, boolean z) {
        super(z);
        A((Ef) v7.g(Fe.a));
        this.c = v7.b(this);
    }

    @Override // rikka.shizuku.Kf
    public final void F(Object obj) {
        if (obj instanceof J6) {
            J6 j6 = (J6) obj;
            Throwable th = j6.a;
            j6.getClass();
            J6.b.get(j6);
        }
    }

    @Override // rikka.shizuku.G7
    public final V7 e() {
        return this.c;
    }

    @Override // rikka.shizuku.InterfaceC0026a8
    public final V7 f() {
        return this.c;
    }

    @Override // rikka.shizuku.G7
    public final void h(Object obj) {
        Throwable thA = AbstractC0102cm.a(obj);
        if (thA != null) {
            obj = new J6(thA);
        }
        Object objC = C(obj);
        if (objC == AbstractC0599sr.g) {
            return;
        }
        n(objC);
    }

    @Override // rikka.shizuku.Kf
    public final String q() {
        return getClass().getSimpleName().concat(" was cancelled");
    }

    @Override // rikka.shizuku.Kf
    public final void z(K6 k6) {
        AbstractC0046as.C(this.c, k6);
    }
}
