package rikka.shizuku;

/* JADX INFO: renamed from: rikka.shizuku.r9, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0550r9 extends Dm {
    public static final C0550r9 c;

    static {
        int i = AbstractC0783yp.c;
        int i2 = AbstractC0783yp.d;
        long j = AbstractC0783yp.e;
        String str = AbstractC0783yp.a;
        C0550r9 c0550r9 = new C0550r9();
        c0550r9.b = new Z7(i, i2, j, str);
        c = c0550r9;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        throw new UnsupportedOperationException("Dispatchers.Default cannot be closed");
    }

    @Override // rikka.shizuku.W7
    public final String toString() {
        return "Dispatchers.Default";
    }
}
