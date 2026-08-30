package rikka.shizuku;

import android.view.WindowInsets;

/* JADX INFO: loaded from: classes.dex */
public class Rt extends Qt {
    public C0587sf n;

    public Rt(au auVar, WindowInsets windowInsets) {
        super(auVar, windowInsets);
        this.n = null;
    }

    @Override // rikka.shizuku.Wt
    public au b() {
        return au.e(null, this.c.consumeStableInsets());
    }

    @Override // rikka.shizuku.Wt
    public au c() {
        return au.e(null, this.c.consumeSystemWindowInsets());
    }

    @Override // rikka.shizuku.Wt
    public final C0587sf h() {
        if (this.n == null) {
            WindowInsets windowInsets = this.c;
            this.n = C0587sf.b(windowInsets.getStableInsetLeft(), windowInsets.getStableInsetTop(), windowInsets.getStableInsetRight(), windowInsets.getStableInsetBottom());
        }
        return this.n;
    }

    @Override // rikka.shizuku.Wt
    public boolean m() {
        return this.c.isConsumed();
    }

    @Override // rikka.shizuku.Wt
    public void q(C0587sf c0587sf) {
        this.n = c0587sf;
    }
}
