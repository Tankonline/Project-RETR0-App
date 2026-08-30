package rikka.shizuku;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class Zq extends Lq {
    @Override // rikka.shizuku.Lq
    public final Object b(Vf vf) {
        if (vf.v() != 9) {
            return new StringBuffer(vf.t());
        }
        vf.r();
        return null;
    }

    @Override // rikka.shizuku.Lq
    public final void c(Wf wf, Object obj) throws IOException {
        StringBuffer stringBuffer = (StringBuffer) obj;
        wf.p(stringBuffer == null ? null : stringBuffer.toString());
    }
}
