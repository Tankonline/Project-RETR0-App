package rikka.shizuku;

/* JADX INFO: loaded from: classes.dex */
public final class Db extends AbstractC0417n {
    public static final C0510q d = AbstractC0535qo.f("2.5.29.9");
    public static final C0510q e;
    public static final C0510q f;
    public static final C0510q g;
    public C0510q a;
    public boolean b;
    public r c;

    static {
        new C0510q("2.5.29.14").u();
        new C0510q("2.5.29.15").u();
        new C0510q("2.5.29.16").u();
        e = new C0510q("2.5.29.17").u();
        f = AbstractC0535qo.f("2.5.29.18");
        new C0510q("2.5.29.19").u();
        new C0510q("2.5.29.20").u();
        new C0510q("2.5.29.21").u();
        new C0510q("2.5.29.23").u();
        new C0510q("2.5.29.24").u();
        new C0510q("2.5.29.27").u();
        new C0510q("2.5.29.28").u();
        g = new C0510q("2.5.29.29").u();
        new C0510q("2.5.29.30").u();
        new C0510q("2.5.29.31").u();
        new C0510q("2.5.29.32").u();
        new C0510q("2.5.29.33").u();
        new C0510q("2.5.29.35").u();
        new C0510q("2.5.29.36").u();
        new C0510q("2.5.29.37").u();
        new C0510q("2.5.29.46").u();
        new C0510q("2.5.29.54").u();
        new C0510q("1.3.6.1.5.5.7.1.1").u();
        new C0510q("1.3.6.1.5.5.7.1.11").u();
        new C0510q("1.3.6.1.5.5.7.1.12").u();
        new C0510q("1.3.6.1.5.5.7.1.2").u();
        new C0510q("1.3.6.1.5.5.7.1.3").u();
        new C0510q("1.3.6.1.5.5.7.1.4").u();
        new C0510q("2.5.29.56").u();
        new C0510q("2.5.29.55").u();
        new C0510q("2.5.29.60").u();
    }

    @Override // rikka.shizuku.InterfaceC0202g
    public final AbstractC0633u d() {
        C0233h c0233h = new C0233h(3);
        c0233h.d(this.a);
        if (this.b) {
            c0233h.d(C0140e.c);
        }
        c0233h.d(this.c);
        D8 d8 = new D8(c0233h);
        d8.c = -1;
        return d8;
    }

    @Override // rikka.shizuku.AbstractC0417n
    public final boolean equals(Object obj) {
        if (!(obj instanceof Db)) {
            return false;
        }
        Db db = (Db) obj;
        return db.a.n(this.a) && db.c.n(this.c) && db.b == this.b;
    }

    @Override // rikka.shizuku.AbstractC0417n
    public final int hashCode() {
        C0510q c0510q = this.a;
        r rVar = this.c;
        if (this.b) {
            return c0510q.a.hashCode() ^ AbstractC0046as.D(rVar.a);
        }
        return ~(c0510q.a.hashCode() ^ AbstractC0046as.D(rVar.a));
    }
}
