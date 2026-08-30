package rikka.shizuku;

import java.util.concurrent.TimeUnit;

/* JADX INFO: renamed from: rikka.shizuku.yp, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0783yp {
    public static final String a;
    public static final long b;
    public static final int c;
    public static final int d;
    public static final long e;
    public static final Ge f;

    static {
        String property;
        int i = AbstractC0566rp.a;
        try {
            property = System.getProperty("kotlinx.coroutines.scheduler.default.name");
        } catch (SecurityException unused) {
            property = null;
        }
        if (property == null) {
            property = "DefaultDispatcher";
        }
        a = property;
        b = Hn.o("kotlinx.coroutines.scheduler.resolution.ns", 100000L, 1L, Long.MAX_VALUE);
        int i2 = AbstractC0566rp.a;
        if (i2 < 2) {
            i2 = 2;
        }
        c = Hn.p("kotlinx.coroutines.scheduler.core.pool.size", i2, 8);
        d = Hn.p("kotlinx.coroutines.scheduler.max.pool.size", 2097150, 4);
        e = TimeUnit.SECONDS.toNanos(Hn.o("kotlinx.coroutines.scheduler.keep.alive.sec", 60L, 1L, Long.MAX_VALUE));
        f = Ge.a;
    }
}
