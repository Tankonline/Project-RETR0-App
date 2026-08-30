package rikka.shizuku;

/* JADX INFO: loaded from: classes.dex */
public final class S8 extends I4 {
    public final boolean d;

    public S8(int i, int i2, boolean z, C0788z c0788z) {
        super(i, i2, c0788z);
        this.d = z;
    }

    @Override // rikka.shizuku.I4, rikka.shizuku.InterfaceC0126df
    public final AbstractC0633u a() {
        C0788z c0788z = this.c;
        int i = this.b;
        boolean z = this.d;
        int i2 = this.a;
        if (z) {
            return H4.r(i2, i, c0788z.f());
        }
        H4 h4 = new H4(4, i2, i, new A8(((I9) ((Cg) c0788z.b)).b()), 2);
        return i2 != 64 ? h4 : new L8(h4);
    }
}
