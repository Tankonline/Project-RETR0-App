package rikka.shizuku;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public final class Kq extends Lq {
    public final /* synthetic */ Lq a;

    public Kq(Lq lq) {
        this.a = lq;
    }

    @Override // rikka.shizuku.Lq
    public final Object b(Vf vf) {
        if (vf.v() != 9) {
            return this.a.b(vf);
        }
        vf.r();
        return null;
    }

    @Override // rikka.shizuku.Lq
    public final void c(Wf wf, Object obj) throws IOException {
        if (obj == null) {
            wf.i();
        } else {
            this.a.c(wf, obj);
        }
    }

    public final String toString() {
        return "NullSafeTypeAdapter[" + this.a + "]";
    }
}
