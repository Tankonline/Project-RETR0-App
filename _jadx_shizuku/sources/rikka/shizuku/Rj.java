package rikka.shizuku;

import java.io.IOException;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class Rj implements Runnable {
    public final /* synthetic */ int a;
    public final /* synthetic */ Sj b;

    public /* synthetic */ Rj(Sj sj, int i) {
        this.a = i;
        this.b = sj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.a) {
            case 0:
                Sj sj = this.b;
                try {
                    try {
                        AbstractC0744xh.p().b(sj);
                    } catch (IOException unused) {
                        return;
                    }
                } catch (Zi unused2) {
                    ArrayList arrayList = sj.d;
                    int size = arrayList.size();
                    int i = 0;
                    while (i < size) {
                        Object obj = arrayList.get(i);
                        i++;
                        ((H6) obj).getClass();
                    }
                    C0516q5 c0516q5 = new C0516q5(1);
                    InterfaceC0781yn interfaceC0781yn = sj.h;
                    if (interfaceC0781yn != null) {
                        Cr cr = sj.g;
                        if (cr == null) {
                            interfaceC0781yn.a(c0516q5);
                            return;
                        } else {
                            cr.execute(new N3(sj, 2, c0516q5));
                            return;
                        }
                    }
                    return;
                }
                break;
            default:
                C0610t7 c0610t7 = new C0610t7(5, this.b);
                Gn gnQ = AbstractC0744xh.q();
                if (gnQ == null) {
                    Gn.j.execute(new G0(11, c0610t7));
                } else {
                    c0610t7.f(gnQ);
                }
                break;
        }
    }
}
