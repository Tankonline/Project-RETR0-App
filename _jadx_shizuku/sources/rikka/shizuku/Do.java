package rikka.shizuku;

import java.sql.Timestamp;
import java.util.Date;

/* JADX INFO: loaded from: classes.dex */
public class Do implements Mq {
    @Override // rikka.shizuku.Mq
    public final Lq a(Rd rd, C0630tr c0630tr) {
        if (c0630tr.a != Timestamp.class) {
            return null;
        }
        rd.getClass();
        return new Eo(rd.c(new C0630tr(Date.class)));
    }
}
