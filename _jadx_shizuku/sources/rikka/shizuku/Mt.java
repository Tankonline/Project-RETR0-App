package rikka.shizuku;

import android.view.WindowInsets;

/* JADX INFO: loaded from: classes.dex */
public class Mt extends Pt {
    public final WindowInsets.Builder c;

    public Mt() {
        this.c = Lt.b();
    }

    @Override // rikka.shizuku.Pt
    public au b() {
        a();
        au auVarE = au.e(null, this.c.build());
        auVarE.a.o(this.b);
        return auVarE;
    }

    @Override // rikka.shizuku.Pt
    public void d(C0587sf c0587sf) {
        this.c.setMandatorySystemGestureInsets(c0587sf.d());
    }

    @Override // rikka.shizuku.Pt
    public void e(C0587sf c0587sf) {
        this.c.setStableInsets(c0587sf.d());
    }

    @Override // rikka.shizuku.Pt
    public void f(C0587sf c0587sf) {
        this.c.setSystemGestureInsets(c0587sf.d());
    }

    @Override // rikka.shizuku.Pt
    public void g(C0587sf c0587sf) {
        this.c.setSystemWindowInsets(c0587sf.d());
    }

    @Override // rikka.shizuku.Pt
    public void h(C0587sf c0587sf) {
        this.c.setTappableElementInsets(c0587sf.d());
    }

    public Mt(au auVar) {
        WindowInsets.Builder builderB;
        super(auVar);
        WindowInsets windowInsetsD = auVar.d();
        if (windowInsetsD != null) {
            builderB = Lt.c(windowInsetsD);
        } else {
            builderB = Lt.b();
        }
        this.c = builderB;
    }
}
