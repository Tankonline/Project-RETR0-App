package rikka.shizuku;

/* JADX INFO: renamed from: rikka.shizuku.qk, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0531qk implements Comparable {
    public C0565ro a;
    public final /* synthetic */ C0561rk b;

    public C0531qk(C0561rk c0561rk) {
        this.b = c0561rk;
    }

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        return this.a.b - ((C0565ro) obj).b;
    }

    public final String toString() {
        String str = "[ ";
        if (this.a != null) {
            for (int i = 0; i < 9; i++) {
                str = str + this.a.h[i] + " ";
            }
        }
        return str + "] " + this.a;
    }
}
