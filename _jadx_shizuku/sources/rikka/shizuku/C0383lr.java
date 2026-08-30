package rikka.shizuku;

import java.io.IOException;

/* JADX INFO: renamed from: rikka.shizuku.lr, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0383lr extends Lq {
    @Override // rikka.shizuku.Lq
    public final Object b(Vf vf) {
        if (vf.v() != 9) {
            return Boolean.valueOf(vf.t());
        }
        vf.r();
        return null;
    }

    @Override // rikka.shizuku.Lq
    public final void c(Wf wf, Object obj) throws IOException {
        Boolean bool = (Boolean) obj;
        wf.p(bool == null ? "null" : bool.toString());
    }
}
