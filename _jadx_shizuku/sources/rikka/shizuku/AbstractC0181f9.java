package rikka.shizuku;

/* JADX INFO: renamed from: rikka.shizuku.f9, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0181f9 {
    public static final /* synthetic */ int a = 0;

    static {
        String property;
        int i = AbstractC0566rp.a;
        try {
            property = System.getProperty("kotlinx.coroutines.main.delay");
        } catch (SecurityException unused) {
            property = null;
        }
        if (!(property != null ? Boolean.parseBoolean(property) : false)) {
            RunnableC0150e9 runnableC0150e9 = RunnableC0150e9.h;
            return;
        }
        C0550r9 c0550r9 = AbstractC0090ca.a;
        C0063be c0063be = AbstractC0713wh.a;
        C0063be c0063be2 = c0063be.d;
        if (c0063be == null) {
            RunnableC0150e9 runnableC0150e10 = RunnableC0150e9.h;
        }
    }
}
