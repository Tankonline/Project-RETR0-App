package rikka.shizuku;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public final enum Xp extends Zp {
    public Xp() {
        super("LONG_OR_DOUBLE", 2);
    }

    public static Double b(String str, Vf vf) throws C0775yh {
        try {
            Double dValueOf = Double.valueOf(str);
            if (dValueOf.isInfinite() || dValueOf.isNaN()) {
                if (vf.o != 1) {
                    throw new C0775yh("JSON forbids NaN and infinities: " + dValueOf + "; at path " + vf.h(true));
                }
            }
            return dValueOf;
        } catch (NumberFormatException e) {
            StringBuilder sbE = AbstractC0535qo.e("Cannot parse ", str, "; at path ");
            sbE.append(vf.h(true));
            throw new K6(sbE.toString(), e);
        }
    }

    @Override // rikka.shizuku.Zp
    public final Number a(Vf vf) throws IOException {
        String strT = vf.t();
        if (strT.indexOf(46) >= 0) {
            return b(strT, vf);
        }
        try {
            return Long.valueOf(Long.parseLong(strT));
        } catch (NumberFormatException unused) {
            return b(strT, vf);
        }
    }
}
