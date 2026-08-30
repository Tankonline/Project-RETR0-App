package rikka.shizuku;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class Ic {
    public boolean a = false;
    public final CopyOnWriteArrayList b = new CopyOnWriteArrayList();
    public C0591sj c;
    public final /* synthetic */ Qc d;

    public Ic(Qc qc) {
        this.d = qc;
    }

    public final void a() {
        boolean zJ = Qc.J(3);
        Qc qc = this.d;
        if (zJ) {
            Objects.toString(qc);
        }
        qc.getClass();
        if (Qc.J(3)) {
            Objects.toString(qc.h);
        }
        L4 l4 = qc.h;
        if (l4 != null) {
            l4.s = false;
            l4.d();
            L4 l5 = qc.h;
            G0 g0 = new G0(10, qc);
            if (l5.q == null) {
                l5.q = new ArrayList();
            }
            l5.q.add(g0);
            qc.h.e(false, true);
            qc.i = true;
            qc.z(true);
            qc.D();
            qc.i = false;
            qc.h = null;
        }
    }
}
