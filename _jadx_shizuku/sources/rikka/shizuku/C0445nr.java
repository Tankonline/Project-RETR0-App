package rikka.shizuku;

import java.io.IOException;

/* JADX INFO: renamed from: rikka.shizuku.nr, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0445nr extends Lq {
    @Override // rikka.shizuku.Lq
    public final Object b(Vf vf) throws IOException {
        if (vf.v() == 9) {
            vf.r();
            return null;
        }
        try {
            int iN = vf.n();
            if (iN <= 65535 && iN >= -32768) {
                return Short.valueOf((short) iN);
            }
            throw new Rf("Lossy conversion from " + iN + " to short; at path " + vf.h(true));
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
            wf.n(number.shortValue());
        }
    }
}
