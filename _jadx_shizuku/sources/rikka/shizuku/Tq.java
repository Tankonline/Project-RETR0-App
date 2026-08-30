package rikka.shizuku;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class Tq extends Lq {
    @Override // rikka.shizuku.Lq
    public final Object b(Vf vf) {
        int iV = vf.v();
        if (iV != 9) {
            return iV == 8 ? Boolean.toString(vf.l()) : vf.t();
        }
        vf.r();
        return null;
    }

    @Override // rikka.shizuku.Lq
    public final void c(Wf wf, Object obj) throws IOException {
        wf.p((String) obj);
    }
}
