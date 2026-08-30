package rikka.shizuku;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class Pq extends Lq {
    @Override // rikka.shizuku.Lq
    public final Object b(Vf vf) {
        if (vf.v() == 9) {
            vf.r();
            return null;
        }
        try {
            return Long.valueOf(vf.o());
        } catch (NumberFormatException e) {
            throw new Rf(e);
        }
    }

    @Override // rikka.shizuku.Lq
    public final void c(Wf wf, Object obj) throws IOException {
        Number number = (Number) obj;
        if (number == null) {
            wf.i();
        } else {
            wf.n(number.longValue());
        }
    }
}
