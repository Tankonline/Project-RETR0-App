package rikka.shizuku;

import java.io.IOException;
import java.util.Currency;

/* JADX INFO: renamed from: rikka.shizuku.er, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0168er extends Lq {
    @Override // rikka.shizuku.Lq
    public final Object b(Vf vf) throws IOException {
        String strT = vf.t();
        try {
            return Currency.getInstance(strT);
        } catch (IllegalArgumentException e) {
            StringBuilder sbE = AbstractC0535qo.e("Failed parsing '", strT, "' as Currency; at path ");
            sbE.append(vf.h(true));
            throw new Rf(sbE.toString(), e);
        }
    }

    @Override // rikka.shizuku.Lq
    public final void c(Wf wf, Object obj) throws IOException {
        wf.p(((Currency) obj).getCurrencyCode());
    }
}
