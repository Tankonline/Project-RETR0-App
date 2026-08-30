package rikka.shizuku;

import java.io.IOException;

/* JADX INFO: renamed from: rikka.shizuku.kr, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0352kr extends Lq {
    @Override // rikka.shizuku.Lq
    public final Object b(Vf vf) {
        int iV = vf.v();
        if (iV != 9) {
            return iV == 6 ? Boolean.valueOf(Boolean.parseBoolean(vf.t())) : Boolean.valueOf(vf.l());
        }
        vf.r();
        return null;
    }

    @Override // rikka.shizuku.Lq
    public final void c(Wf wf, Object obj) throws IOException {
        Boolean bool = (Boolean) obj;
        if (bool == null) {
            wf.i();
            return;
        }
        wf.r();
        wf.a();
        wf.a.write(bool.booleanValue() ? "true" : "false");
    }
}
