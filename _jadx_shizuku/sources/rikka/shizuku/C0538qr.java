package rikka.shizuku;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: renamed from: rikka.shizuku.qr, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0538qr extends Lq {
    @Override // rikka.shizuku.Lq
    public final Object b(Vf vf) {
        return new AtomicBoolean(vf.l());
    }

    @Override // rikka.shizuku.Lq
    public final void c(Wf wf, Object obj) throws IOException {
        wf.q(((AtomicBoolean) obj).get());
    }
}
