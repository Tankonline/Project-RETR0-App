package rikka.shizuku;

/* JADX INFO: loaded from: classes.dex */
public final class Pk extends AbstractC0417n {
    public static final K1 e;
    public static final K1 f;
    public static final C0386m g;
    public static final C0386m h;
    public K1 a;
    public K1 b;
    public C0386m c;
    public C0386m d;

    static {
        K1 k1 = new K1(InterfaceC0191fj.a, C0766y8.a);
        e = k1;
        f = new K1(Ej.f, k1);
        g = new C0386m(20L);
        h = new C0386m(1L);
    }

    public static Pk i(Object obj) {
        if (obj instanceof Pk) {
            return (Pk) obj;
        }
        if (obj == null) {
            return null;
        }
        AbstractC0726x abstractC0726xT = AbstractC0726x.t(obj);
        Pk pk = new Pk();
        pk.a = e;
        pk.b = f;
        pk.c = g;
        pk.d = h;
        for (int i = 0; i != abstractC0726xT.size(); i++) {
            H4 h4 = (H4) abstractC0726xT.u(i);
            int i2 = h4.c;
            C0048b c0048b = AbstractC0726x.b;
            if (i2 == 0) {
                pk.a = K1.i((AbstractC0726x) c0048b.d(h4, true));
            } else if (i2 != 1) {
                C0048b c0048b2 = C0386m.c;
                if (i2 == 2) {
                    pk.c = (C0386m) c0048b2.d(h4, true);
                } else {
                    if (i2 != 3) {
                        throw new IllegalArgumentException("unknown tag");
                    }
                    pk.d = (C0386m) c0048b2.d(h4, true);
                }
            } else {
                pk.b = K1.i((AbstractC0726x) c0048b.d(h4, true));
            }
        }
        return pk;
    }

    @Override // rikka.shizuku.InterfaceC0202g
    public final AbstractC0633u d() {
        C0233h c0233h = new C0233h(4);
        K1 k1 = e;
        K1 k2 = this.a;
        if (!k2.equals(k1)) {
            c0233h.d(new H4(true, 0, (InterfaceC0202g) k2, 1));
        }
        K1 k3 = f;
        K1 k4 = this.b;
        if (!k4.equals(k3)) {
            c0233h.d(new H4(true, 1, (InterfaceC0202g) k4, 1));
        }
        C0386m c0386m = g;
        C0386m c0386m2 = this.c;
        if (!c0386m2.n(c0386m)) {
            c0233h.d(new H4(true, 2, (InterfaceC0202g) c0386m2, 1));
        }
        C0386m c0386m3 = h;
        C0386m c0386m4 = this.d;
        if (!c0386m4.n(c0386m3)) {
            c0233h.d(new H4(true, 3, (InterfaceC0202g) c0386m4, 1));
        }
        D8 d8 = new D8(c0233h);
        d8.c = -1;
        return d8;
    }
}
