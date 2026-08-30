package rikka.shizuku;

import java.sql.Date;
import java.sql.Timestamp;

/* JADX INFO: loaded from: classes.dex */
public abstract class Go {
    public static final boolean a;
    public static final Fo b;
    public static final Fo c;
    public static final C0813zo d;
    public static final Bo e;
    public static final Do f;

    static {
        boolean z;
        try {
            Class.forName("java.sql.Date");
            z = true;
        } catch (ClassNotFoundException unused) {
            z = false;
        }
        a = z;
        if (z) {
            b = new Fo(0, Date.class);
            c = new Fo(1, Timestamp.class);
            d = Ao.b;
            e = Co.b;
            f = Eo.b;
            return;
        }
        b = null;
        c = null;
        d = null;
        e = null;
        f = null;
    }
}
