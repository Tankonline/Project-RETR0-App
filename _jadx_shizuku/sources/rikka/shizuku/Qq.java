package rikka.shizuku;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class Qq extends Lq {
    @Override // rikka.shizuku.Lq
    public final Object b(Vf vf) {
        if (vf.v() != 9) {
            return Float.valueOf((float) vf.m());
        }
        vf.r();
        return null;
    }

    @Override // rikka.shizuku.Lq
    public final void c(Wf wf, Object obj) throws IOException {
        Number numberValueOf = (Number) obj;
        if (numberValueOf == null) {
            wf.i();
            return;
        }
        if (!(numberValueOf instanceof Float)) {
            numberValueOf = Float.valueOf(numberValueOf.floatValue());
        }
        wf.o(numberValueOf);
    }
}
