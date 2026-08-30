package rikka.shizuku;

import java.util.concurrent.CancellationException;

/* JADX INFO: renamed from: rikka.shizuku.ye, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0772ye extends AbstractC0443np implements InterfaceC0585sd {
    public final /* synthetic */ C0803ze e;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0772ye(C0803ze c0803ze, G7 g7) {
        super(g7);
        this.e = c0803ze;
    }

    @Override // rikka.shizuku.InterfaceC0585sd
    public final Object f(Object obj, Object obj2) throws Throwable {
        C0772ye c0772ye = (C0772ye) i((InterfaceC0026a8) obj, (G7) obj2);
        Pe pe = Pe.b;
        c0772ye.j(pe);
        return pe;
    }

    @Override // rikka.shizuku.Q4
    public final G7 i(Object obj, G7 g7) {
        return new C0772ye(this.e, g7);
    }

    @Override // rikka.shizuku.Q4
    public final Object j(Object obj) throws Throwable {
        C0803ze c0803ze = this.e;
        Pi pi = c0803ze.b;
        AbstractC0046as.V(obj);
        try {
            pi.e(new Rl(1, C0803ze.d(c0803ze), Sl.a));
        } catch (CancellationException unused) {
        } catch (Throwable th) {
            pi.e(new Rl(2, new C0103cn(), th));
        }
        return Pe.b;
    }
}
