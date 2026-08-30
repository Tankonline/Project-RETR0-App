package rikka.shizuku;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/* JADX INFO: renamed from: rikka.shizuku.br, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0076br extends Lq {
    @Override // rikka.shizuku.Lq
    public final Object b(Vf vf) throws IOException {
        if (vf.v() == 9) {
            vf.r();
            return null;
        }
        try {
            String strT = vf.t();
            if (strT.equals("null")) {
                return null;
            }
            return new URI(strT);
        } catch (URISyntaxException e) {
            throw new Rf(e);
        }
    }

    @Override // rikka.shizuku.Lq
    public final void c(Wf wf, Object obj) throws IOException {
        URI uri = (URI) obj;
        wf.p(uri == null ? null : uri.toASCIIString());
    }
}
