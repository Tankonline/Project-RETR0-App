package rikka.shizuku;

/* JADX INFO: loaded from: classes.dex */
public final class K1 extends AbstractC0417n {
    public C0510q a;
    public InterfaceC0202g b;

    public K1(C0510q c0510q, InterfaceC0202g interfaceC0202g) {
        this.a = c0510q;
        this.b = interfaceC0202g;
    }

    public static K1 i(Object obj) {
        if (obj instanceof K1) {
            return (K1) obj;
        }
        if (obj == null) {
            return null;
        }
        AbstractC0726x abstractC0726xT = AbstractC0726x.t(obj);
        K1 k1 = new K1();
        if (abstractC0726xT.size() < 1 || abstractC0726xT.size() > 2) {
            throw new IllegalArgumentException("Bad sequence size: " + abstractC0726xT.size());
        }
        k1.a = C0510q.t(abstractC0726xT.u(0));
        if (abstractC0726xT.size() == 2) {
            k1.b = abstractC0726xT.u(1);
            return k1;
        }
        k1.b = null;
        return k1;
    }

    @Override // rikka.shizuku.InterfaceC0202g
    public final AbstractC0633u d() {
        C0233h c0233h = new C0233h(2);
        c0233h.d(this.a);
        InterfaceC0202g interfaceC0202g = this.b;
        if (interfaceC0202g != null) {
            c0233h.d(interfaceC0202g);
        }
        D8 d8 = new D8(c0233h);
        d8.c = -1;
        return d8;
    }
}
