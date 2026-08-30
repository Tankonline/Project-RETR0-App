package rikka.shizuku;

import android.view.View;
import android.view.WindowInsets;

/* JADX INFO: loaded from: classes.dex */
public class Ut extends Tt {
    public static final au r = au.e(null, WindowInsets.CONSUMED);

    public Ut(au auVar, WindowInsets windowInsets) {
        super(auVar, windowInsets);
    }

    @Override // rikka.shizuku.Qt, rikka.shizuku.Wt
    public C0587sf f(int i) {
        return C0587sf.c(this.c.getInsets(Xt.a(i)));
    }

    @Override // rikka.shizuku.Qt, rikka.shizuku.Wt
    public final void d(View view) {
    }
}
