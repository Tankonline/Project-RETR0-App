package rikka.shizuku;

import java.util.Date;

/* JADX INFO: loaded from: classes.dex */
public class Z8 implements Mq {
    @Override // rikka.shizuku.Mq
    public final Lq a(Rd rd, C0630tr c0630tr) {
        if (c0630tr.a == Date.class) {
            return new C0089c9(AbstractC0058b9.b, 2, 2);
        }
        return null;
    }

    public final String toString() {
        return "DefaultDateTypeAdapter#DEFAULT_STYLE_FACTORY";
    }
}
