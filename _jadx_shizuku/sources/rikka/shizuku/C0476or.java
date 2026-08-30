package rikka.shizuku;

import java.io.IOException;

/* JADX INFO: renamed from: rikka.shizuku.or, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0476or extends Lq {
    @Override // rikka.shizuku.Lq
    public final Object b(Vf vf) {
        if (vf.v() == 9) {
            vf.r();
            return null;
        }
        try {
            return Integer.valueOf(vf.n());
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
            wf.n(number.intValue());
        }
    }
}
