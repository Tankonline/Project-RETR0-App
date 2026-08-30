package rikka.shizuku;

import java.io.IOException;
import java.net.InetAddress;

/* JADX INFO: renamed from: rikka.shizuku.cr, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0107cr extends Lq {
    @Override // rikka.shizuku.Lq
    public final Object b(Vf vf) {
        if (vf.v() != 9) {
            return InetAddress.getByName(vf.t());
        }
        vf.r();
        return null;
    }

    @Override // rikka.shizuku.Lq
    public final void c(Wf wf, Object obj) throws IOException {
        InetAddress inetAddress = (InetAddress) obj;
        wf.p(inetAddress == null ? null : inetAddress.getHostAddress());
    }
}
