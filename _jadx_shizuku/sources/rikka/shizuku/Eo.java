package rikka.shizuku;

import java.sql.Timestamp;
import java.util.Date;

/* JADX INFO: loaded from: classes.dex */
public final class Eo extends Lq {
    public static final Do b = new Do();
    public final Lq a;

    public Eo(Lq lq) {
        this.a = lq;
    }

    @Override // rikka.shizuku.Lq
    public final Object b(Vf vf) {
        Date date = (Date) this.a.b(vf);
        if (date != null) {
            return new Timestamp(date.getTime());
        }
        return null;
    }

    @Override // rikka.shizuku.Lq
    public final void c(Wf wf, Object obj) {
        this.a.c(wf, (Timestamp) obj);
    }
}
