package rikka.shizuku;

import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public final class Qs extends AbstractC0443np implements InterfaceC0585sd {
    public final /* synthetic */ Us e;
    public final /* synthetic */ String f;
    public final /* synthetic */ int g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Qs(Us us, String str, int i, G7 g7) {
        super(g7);
        this.e = us;
        this.f = str;
        this.g = i;
    }

    @Override // rikka.shizuku.InterfaceC0585sd
    public final Object f(Object obj, Object obj2) throws Throwable {
        Qs qs = (Qs) i((InterfaceC0026a8) obj, (G7) obj2);
        Pe pe = Pe.b;
        qs.j(pe);
        return pe;
    }

    @Override // rikka.shizuku.Q4
    public final G7 i(Object obj, G7 g7) {
        return new Qs(this.e, this.f, this.g, g7);
    }

    @Override // rikka.shizuku.Q4
    public final Object j(Object obj) throws Throwable {
        Object c0071bm;
        Pe pe = Pe.b;
        Us us = this.e;
        AbstractC0046as.V(obj);
        try {
            C0235h1 c0235h1 = new C0235h1(new Ys(28, AbstractC0750xn.a));
            U0 u0 = new U0(this.g, this.f, c0235h1);
            try {
                u0.a();
                u0.c(Qo.b, new Z0(2, us));
                u0.close();
                c0071bm = pe;
            } catch (Throwable th) {
                c0071bm = new C0071bm(th);
            }
            Throwable thA = AbstractC0102cm.a(c0071bm);
            if (thA != null) {
                thA.printStackTrace();
                StringBuilder sb = us.b;
                sb.append('\n');
                sb.append(Log.getStackTraceString(thA));
                us.d(thA);
            }
        } catch (Throwable th2) {
            th2.printStackTrace();
            StringBuilder sb2 = us.b;
            sb2.append('\n');
            sb2.append(Log.getStackTraceString(th2));
            us.d(new C0266i1(th2));
        }
        return pe;
    }
}
