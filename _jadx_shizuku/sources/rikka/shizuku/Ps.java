package rikka.shizuku;

import moe.shizuku.manager.adb.AdbPairingClient;

/* JADX INFO: loaded from: classes.dex */
public final class Ps extends AbstractC0443np implements InterfaceC0585sd {
    public final /* synthetic */ Ts e;
    public final /* synthetic */ int f;
    public final /* synthetic */ String g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Ps(Ts ts, int i, String str, G7 g7) {
        super(g7);
        this.e = ts;
        this.f = i;
        this.g = str;
    }

    @Override // rikka.shizuku.InterfaceC0585sd
    public final Object f(Object obj, Object obj2) throws Throwable {
        Ps ps = (Ps) i((InterfaceC0026a8) obj, (G7) obj2);
        Pe pe = Pe.b;
        ps.j(pe);
        return pe;
    }

    @Override // rikka.shizuku.Q4
    public final G7 i(Object obj, G7 g7) {
        return new Ps(this.e, this.f, this.g, g7);
    }

    @Override // rikka.shizuku.Q4
    public final Object j(Object obj) throws Throwable {
        Object c0071bm;
        Pe pe = Pe.b;
        Pi pi = this.e.b;
        AbstractC0046as.V(obj);
        try {
            try {
                c0071bm = Boolean.valueOf(new AdbPairingClient(this.f, this.g, new C0235h1(new Ys(28, AbstractC0750xn.a))).b());
            } catch (Throwable th) {
                c0071bm = new C0071bm(th);
            }
            Throwable thA = AbstractC0102cm.a(c0071bm);
            if (thA != null) {
                pi.e(thA);
                thA.printStackTrace();
            }
            if (!(c0071bm instanceof C0071bm) && ((Boolean) c0071bm).booleanValue()) {
                pi.e(null);
            }
            return pe;
        } catch (Throwable th2) {
            th2.printStackTrace();
            pi.e(new C0266i1(th2));
            return pe;
        }
    }
}
