package rikka.shizuku;

import java.io.IOException;
import java.net.URL;

/* JADX INFO: renamed from: rikka.shizuku.ar, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0045ar extends Lq {
    @Override // rikka.shizuku.Lq
    public final Object b(Vf vf) throws IOException {
        if (vf.v() == 9) {
            vf.r();
            return null;
        }
        String strT = vf.t();
        if (strT.equals("null")) {
            return null;
        }
        return new URL(strT);
    }

    @Override // rikka.shizuku.Lq
    public final void c(Wf wf, Object obj) throws IOException {
        URL url = (URL) obj;
        wf.p(url == null ? null : url.toExternalForm());
    }
}
