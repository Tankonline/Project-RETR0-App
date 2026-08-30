package rikka.shizuku;

import java.util.concurrent.ExecutorService;

/* JADX INFO: loaded from: classes.dex */
public final class Ss extends AbstractC0443np implements InterfaceC0585sd {
    public final /* synthetic */ Us e;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Ss(Us us, G7 g7) {
        super(g7);
        this.e = us;
    }

    @Override // rikka.shizuku.InterfaceC0585sd
    public final Object f(Object obj, Object obj2) throws Throwable {
        Ss ss = (Ss) i((InterfaceC0026a8) obj, (G7) obj2);
        Pe pe = Pe.b;
        ss.j(pe);
        return pe;
    }

    @Override // rikka.shizuku.Q4
    public final G7 i(Object obj, G7 g7) {
        return new Ss(this.e, g7);
    }

    @Override // rikka.shizuku.Q4
    public final Object j(Object obj) throws Throwable {
        int i = 1;
        AbstractC0046as.V(obj);
        ExecutorService executorService = Gn.j;
        boolean z = AbstractC0744xh.p().a >= 1;
        Pe pe = Pe.b;
        Us us = this.e;
        if (!z) {
            Gn gnQ = AbstractC0744xh.q();
            if (gnQ != null) {
                gnQ.close();
            }
            StringBuilder sb = us.b;
            sb.append('\n');
            sb.append("Can't open root shell, try again...");
            sb.append('\n');
            us.d(null);
            if (AbstractC0744xh.p().a < 1) {
                StringBuilder sb2 = us.b;
                sb2.append('\n');
                sb2.append("Still not :(");
                sb2.append('\n');
                us.d(new C0130dj());
                return pe;
            }
        }
        String[] strArr = {Qo.b};
        Sj sj = new Sj();
        sj.d.add(new H6(strArr));
        sj.e = new Rs(us);
        sj.f = Sj.j;
        C0610t7 c0610t7 = new C0610t7(9, us);
        Cr cr = Dr.b;
        sj.i = new Rj(sj, i);
        sj.g = cr;
        sj.h = c0610t7;
        C0610t7 c0610t8 = new C0610t7(5, sj);
        Gn gnQ2 = AbstractC0744xh.q();
        if (gnQ2 != null) {
            c0610t8.f(gnQ2);
            return pe;
        }
        Gn.j.execute(new G0(11, c0610t8));
        return pe;
    }
}
