package rikka.shizuku;

/* JADX INFO: renamed from: rikka.shizuku.np, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0443np extends H7 implements Cd {
    public final int d;

    public AbstractC0443np(G7 g7) {
        super(g7, g7 != null ? g7.e() : null);
        this.d = 2;
    }

    @Override // rikka.shizuku.Cd
    public final int b() {
        return this.d;
    }

    @Override // rikka.shizuku.Q4
    public final String toString() {
        if (this.a != null) {
            return super.toString();
        }
        AbstractC0593sl.a.getClass();
        return C0624tl.a(this);
    }
}
