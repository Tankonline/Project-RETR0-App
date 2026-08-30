package rikka.shizuku;

import android.graphics.Typeface;

/* JADX INFO: loaded from: classes.dex */
public final class Ap extends X8 {
    public final /* synthetic */ AbstractC0750xn s;
    public final /* synthetic */ Cp t;

    public Ap(Cp cp, AbstractC0750xn abstractC0750xn) {
        this.t = cp;
        this.s = abstractC0750xn;
    }

    @Override // rikka.shizuku.X8
    public final void K(int i) {
        this.t.m = true;
        this.s.o(i);
    }

    @Override // rikka.shizuku.X8
    public final void L(Typeface typeface) {
        Cp cp = this.t;
        cp.n = Typeface.create(typeface, cp.c);
        cp.m = true;
        this.s.p(cp.n, false);
    }
}
