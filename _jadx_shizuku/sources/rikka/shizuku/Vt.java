package rikka.shizuku;

import android.view.WindowInsets;

/* JADX INFO: loaded from: classes.dex */
public final class Vt extends Ut {
    public static final au s = au.e(null, WindowInsets.CONSUMED);

    public Vt(au auVar, WindowInsets windowInsets) {
        super(auVar, windowInsets);
    }

    @Override // rikka.shizuku.Ut, rikka.shizuku.Qt, rikka.shizuku.Wt
    public C0587sf f(int i) {
        return C0587sf.c(this.c.getInsets(Zt.a(i)));
    }
}
