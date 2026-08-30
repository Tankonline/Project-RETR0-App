package rikka.shizuku;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public final enum Yp extends Zp {
    public Yp() {
        super("BIG_DECIMAL", 3);
    }

    @Override // rikka.shizuku.Zp
    public final Number a(Vf vf) throws IOException {
        String strT = vf.t();
        try {
            return X8.N(strT);
        } catch (NumberFormatException e) {
            StringBuilder sbE = AbstractC0535qo.e("Cannot parse ", strT, "; at path ");
            sbE.append(vf.h(true));
            throw new K6(sbE.toString(), e);
        }
    }
}
