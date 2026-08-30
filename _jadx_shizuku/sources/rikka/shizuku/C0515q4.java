package rikka.shizuku;

/* JADX INFO: renamed from: rikka.shizuku.q4, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0515q4 extends AbstractC0417n {
    public C0510q a;
    public InterfaceC0202g b;

    public static C0515q4 i(InterfaceC0202g interfaceC0202g) {
        if (interfaceC0202g instanceof C0515q4) {
            return (C0515q4) interfaceC0202g;
        }
        if (interfaceC0202g == null) {
            throw new IllegalArgumentException("null value in getInstance()");
        }
        AbstractC0726x abstractC0726xT = AbstractC0726x.t(interfaceC0202g);
        C0515q4 c0515q4 = new C0515q4();
        c0515q4.a = (C0510q) abstractC0726xT.u(0);
        c0515q4.b = abstractC0726xT.u(1);
        return c0515q4;
    }

    @Override // rikka.shizuku.InterfaceC0202g
    public final AbstractC0633u d() {
        C0233h c0233h = new C0233h(2);
        c0233h.d(this.a);
        c0233h.d(this.b);
        D8 d8 = new D8(c0233h);
        d8.c = -1;
        return d8;
    }
}
