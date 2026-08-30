package rikka.shizuku;

/* JADX INFO: renamed from: rikka.shizuku.ad, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0031ad {
    public static final Zc a = Zc.a;

    public static Zc a(Ac ac) {
        while (ac != null) {
            if (ac.u != null && ac.k) {
                ac.l();
            }
            ac = ac.w;
        }
        return a;
    }

    public static void b(AbstractC0570rt abstractC0570rt) {
        if (Qc.J(3)) {
            abstractC0570rt.a.getClass();
        }
    }

    public static final void c(Ac ac, String str) {
        b(new Vc(ac, "Attempting to reuse fragment " + ac + " with previous ID " + str));
        a(ac).getClass();
    }
}
