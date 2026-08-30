package rikka.shizuku;

/* JADX INFO: renamed from: rikka.shizuku.q6, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0517q6 implements AutoCloseable, InterfaceC0026a8 {
    public final V7 a;

    public C0517q6(V7 v7) {
        this.a = v7;
    }

    @Override // java.lang.AutoCloseable
    public final void close() {
        Ef ef = (Ef) this.a.g(Fe.a);
        if (ef != null) {
            ef.i(null);
        }
    }

    @Override // rikka.shizuku.InterfaceC0026a8
    public final V7 f() {
        return this.a;
    }
}
