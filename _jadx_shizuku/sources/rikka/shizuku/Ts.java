package rikka.shizuku;

import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
public final class Ts extends Vs {
    public final Pi b;
    public final Pi c;
    public final Pi d;
    public final Pi e;
    public final C0357l1 f;

    public Ts(Context context) {
        Pi pi = new Pi();
        this.b = pi;
        this.c = pi;
        Pi pi2 = new Pi();
        this.d = pi2;
        this.e = pi2;
        C0357l1 c0357l1 = new C0357l1(context, "_adb-tls-pairing._tcp", new V0(3, this));
        this.f = c0357l1;
        c0357l1.a();
    }

    @Override // rikka.shizuku.Vs
    public final void c() {
        this.f.b();
    }
}
