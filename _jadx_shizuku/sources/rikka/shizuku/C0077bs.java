package rikka.shizuku;

/* JADX INFO: renamed from: rikka.shizuku.bs, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0077bs {
    public H4 a;
    public C0386m b;
    public K1 c;
    public lu d;
    public Qp e;
    public Qp f;
    public lu g;
    public C0197fp h;
    public Eb i;
    public boolean j;

    public final C0659up a() {
        if (this.b == null || this.c == null || this.d == null || this.e == null || this.f == null || ((this.g == null && !this.j) || this.h == null)) {
            throw new IllegalStateException("not all mandatory fields set in V3 TBScertificate generator");
        }
        C0233h c0233h = new C0233h(10);
        c0233h.d(this.a);
        c0233h.d(this.b);
        c0233h.d(this.c);
        c0233h.d(this.d);
        C0233h c0233h2 = new C0233h(2);
        c0233h2.d(this.e);
        c0233h2.d(this.f);
        D8 d8 = new D8(c0233h2);
        d8.c = -1;
        c0233h.d(d8);
        InterfaceC0202g d9 = this.g;
        if (d9 == null) {
            d9 = new D8();
        }
        c0233h.d(d9);
        c0233h.d(this.h);
        Eb eb = this.i;
        if (eb != null) {
            c0233h.d(new H4(true, 3, (InterfaceC0202g) eb, 1));
        }
        D8 d10 = new D8(c0233h);
        d10.c = -1;
        return C0659up.i(d10);
    }
}
