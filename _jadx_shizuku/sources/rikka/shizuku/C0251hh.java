package rikka.shizuku;

/* JADX INFO: renamed from: rikka.shizuku.hh, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0251hh extends Vs {
    public static final Sc c = new Sc(1);
    public final C0689vo b = new C0689vo();

    @Override // rikka.shizuku.Vs
    public final void c() {
        C0689vo c0689vo = this.b;
        int i = c0689vo.c;
        if (i > 0) {
            c0689vo.b[0].getClass();
            throw new ClassCastException();
        }
        Object[] objArr = c0689vo.b;
        for (int i2 = 0; i2 < i; i2++) {
            objArr[i2] = null;
        }
        c0689vo.c = 0;
    }
}
