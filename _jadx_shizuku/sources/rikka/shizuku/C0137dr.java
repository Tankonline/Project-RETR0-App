package rikka.shizuku;

import java.io.IOException;
import java.util.UUID;

/* JADX INFO: renamed from: rikka.shizuku.dr, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0137dr extends Lq {
    @Override // rikka.shizuku.Lq
    public final Object b(Vf vf) throws IOException {
        if (vf.v() == 9) {
            vf.r();
            return null;
        }
        String strT = vf.t();
        try {
            return UUID.fromString(strT);
        } catch (IllegalArgumentException e) {
            StringBuilder sbE = AbstractC0535qo.e("Failed parsing '", strT, "' as UUID; at path ");
            sbE.append(vf.h(true));
            throw new Rf(sbE.toString(), e);
        }
    }

    @Override // rikka.shizuku.Lq
    public final void c(Wf wf, Object obj) throws IOException {
        UUID uuid = (UUID) obj;
        wf.p(uuid == null ? null : uuid.toString());
    }
}
