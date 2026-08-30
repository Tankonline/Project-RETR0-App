package rikka.shizuku;

/* JADX INFO: renamed from: rikka.shizuku.a4, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0022a4 extends AbstractC0688vn {
    public static volatile C0022a4 c;
    public final H9 b = new H9();

    public static C0022a4 C() {
        if (c != null) {
            return c;
        }
        synchronized (C0022a4.class) {
            try {
                if (c == null) {
                    c = new C0022a4();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return c;
    }
}
