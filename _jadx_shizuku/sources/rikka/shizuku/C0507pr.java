package rikka.shizuku;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: renamed from: rikka.shizuku.pr, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0507pr extends Lq {
    @Override // rikka.shizuku.Lq
    public final Object b(Vf vf) {
        try {
            return new AtomicInteger(vf.n());
        } catch (NumberFormatException e) {
            throw new Rf(e);
        }
    }

    @Override // rikka.shizuku.Lq
    public final void c(Wf wf, Object obj) throws IOException {
        wf.n(((AtomicInteger) obj).get());
    }
}
