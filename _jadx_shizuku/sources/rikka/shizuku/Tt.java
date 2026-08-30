package rikka.shizuku;

import android.view.WindowInsets;

/* JADX INFO: loaded from: classes.dex */
public class Tt extends St {
    public C0587sf o;
    public C0587sf p;
    public C0587sf q;

    public Tt(au auVar, WindowInsets windowInsets) {
        super(auVar, windowInsets);
        this.o = null;
        this.p = null;
        this.q = null;
    }

    @Override // rikka.shizuku.Wt
    public C0587sf g() {
        if (this.p == null) {
            this.p = C0587sf.c(this.c.getMandatorySystemGestureInsets());
        }
        return this.p;
    }

    @Override // rikka.shizuku.Wt
    public C0587sf i() {
        if (this.o == null) {
            this.o = C0587sf.c(this.c.getSystemGestureInsets());
        }
        return this.o;
    }

    @Override // rikka.shizuku.Wt
    public C0587sf k() {
        if (this.q == null) {
            this.q = C0587sf.c(this.c.getTappableElementInsets());
        }
        return this.q;
    }

    @Override // rikka.shizuku.Qt, rikka.shizuku.Wt
    public au l(int i, int i2, int i3, int i4) {
        return au.e(null, this.c.inset(i, i2, i3, i4));
    }

    @Override // rikka.shizuku.Rt, rikka.shizuku.Wt
    public void q(C0587sf c0587sf) {
    }
}
