package rikka.shizuku;

/* JADX INFO: loaded from: classes.dex */
public final class I6 implements G7 {
    public static final I6 a = new I6();

    @Override // rikka.shizuku.G7
    public final V7 e() {
        throw new IllegalStateException("This continuation is already complete");
    }

    @Override // rikka.shizuku.G7
    public final void h(Object obj) {
        throw new IllegalStateException("This continuation is already complete");
    }

    public final String toString() {
        return "This continuation is already complete";
    }
}
