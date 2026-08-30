package rikka.shizuku;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class Sq extends Lq {
    @Override // rikka.shizuku.Lq
    public final Object b(Vf vf) throws IOException {
        if (vf.v() == 9) {
            vf.r();
            return null;
        }
        String strT = vf.t();
        if (strT.length() == 1) {
            return Character.valueOf(strT.charAt(0));
        }
        StringBuilder sbE = AbstractC0535qo.e("Expecting character, got: ", strT, "; at ");
        sbE.append(vf.h(true));
        throw new Rf(sbE.toString());
    }

    @Override // rikka.shizuku.Lq
    public final void c(Wf wf, Object obj) throws IOException {
        Character ch = (Character) obj;
        wf.p(ch == null ? null : String.valueOf(ch));
    }
}
