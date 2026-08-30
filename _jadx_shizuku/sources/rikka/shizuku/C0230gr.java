package rikka.shizuku;

import java.io.IOException;
import java.util.Locale;
import java.util.StringTokenizer;

/* JADX INFO: renamed from: rikka.shizuku.gr, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0230gr extends Lq {
    @Override // rikka.shizuku.Lq
    public final Object b(Vf vf) {
        if (vf.v() == 9) {
            vf.r();
            return null;
        }
        StringTokenizer stringTokenizer = new StringTokenizer(vf.t(), "_");
        String strNextToken = stringTokenizer.hasMoreElements() ? stringTokenizer.nextToken() : null;
        String strNextToken2 = stringTokenizer.hasMoreElements() ? stringTokenizer.nextToken() : null;
        String strNextToken3 = stringTokenizer.hasMoreElements() ? stringTokenizer.nextToken() : null;
        if (strNextToken2 == null && strNextToken3 == null) {
            return new Locale(strNextToken);
        }
        return strNextToken3 == null ? new Locale(strNextToken, strNextToken2) : new Locale(strNextToken, strNextToken2, strNextToken3);
    }

    @Override // rikka.shizuku.Lq
    public final void c(Wf wf, Object obj) throws IOException {
        Locale locale = (Locale) obj;
        wf.p(locale == null ? null : locale.toString());
    }
}
