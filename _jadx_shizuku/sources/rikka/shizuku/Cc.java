package rikka.shizuku;

import android.os.Handler;
import android.view.View;
import android.view.Window;

/* JADX INFO: loaded from: classes.dex */
public final class Cc extends AbstractC0046as implements InterfaceC0047at, InterfaceC0743xg, Bm, Uc {
    public final AbstractActivityC0051b2 i;
    public final AbstractActivityC0051b2 j;
    public final Handler k;
    public final Qc l;
    public final /* synthetic */ AbstractActivityC0051b2 m;

    public Cc(AbstractActivityC0051b2 abstractActivityC0051b2) {
        this.m = abstractActivityC0051b2;
        Handler handler = new Handler();
        this.i = abstractActivityC0051b2;
        this.j = abstractActivityC0051b2;
        this.k = handler;
        this.l = new Qc();
    }

    @Override // rikka.shizuku.AbstractC0046as
    public final View J(int i) {
        return this.m.findViewById(i);
    }

    @Override // rikka.shizuku.AbstractC0046as
    public final boolean K() {
        Window window = this.m.getWindow();
        return (window == null || window.peekDecorView() == null) ? false : true;
    }

    @Override // rikka.shizuku.Bm
    public final R2 b() {
        return (R2) this.m.e.c;
    }

    @Override // rikka.shizuku.InterfaceC0047at
    public final Zs d() {
        return this.m.d();
    }

    @Override // rikka.shizuku.InterfaceC0743xg
    public final C0805zg e() {
        return this.m.t;
    }

    @Override // rikka.shizuku.Uc
    public final void a() {
    }
}
