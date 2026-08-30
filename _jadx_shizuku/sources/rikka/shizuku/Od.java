package rikka.shizuku;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class Od extends Lq {
    @Override // rikka.shizuku.Lq
    public final Object b(Vf vf) {
        if (vf.v() != 9) {
            return Long.valueOf(vf.o());
        }
        vf.r();
        return null;
    }

    @Override // rikka.shizuku.Lq
    public final void c(Wf wf, Object obj) throws IOException {
        Number number = (Number) obj;
        if (number == null) {
            wf.i();
        } else {
            wf.p(number.toString());
        }
    }
}
