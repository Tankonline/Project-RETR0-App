package rikka.shizuku;

import java.io.IOException;

/* JADX INFO: renamed from: rikka.shizuku.mr, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0414mr extends Lq {
    @Override // rikka.shizuku.Lq
    public final Object b(Vf vf) throws IOException {
        if (vf.v() == 9) {
            vf.r();
            return null;
        }
        try {
            int iN = vf.n();
            if (iN <= 255 && iN >= -128) {
                return Byte.valueOf((byte) iN);
            }
            throw new Rf("Lossy conversion from " + iN + " to byte; at path " + vf.h(true));
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
            wf.n(number.byteValue());
        }
    }
}
