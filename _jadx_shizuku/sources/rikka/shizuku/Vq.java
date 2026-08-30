package rikka.shizuku;

import java.io.IOException;
import java.math.BigInteger;

/* JADX INFO: loaded from: classes.dex */
public class Vq extends Lq {
    @Override // rikka.shizuku.Lq
    public final Object b(Vf vf) throws IOException {
        if (vf.v() == 9) {
            vf.r();
            return null;
        }
        String strT = vf.t();
        try {
            X8.l(strT);
            return new BigInteger(strT);
        } catch (NumberFormatException e) {
            StringBuilder sbE = AbstractC0535qo.e("Failed parsing '", strT, "' as BigInteger; at path ");
            sbE.append(vf.h(true));
            throw new Rf(sbE.toString(), e);
        }
    }

    @Override // rikka.shizuku.Lq
    public final void c(Wf wf, Object obj) throws IOException {
        wf.o((BigInteger) obj);
    }
}
