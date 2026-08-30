package rikka.shizuku;

import android.graphics.Typeface;

/* JADX INFO: renamed from: rikka.shizuku.x5, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0732x5 extends AbstractC0750xn {
    public final Typeface d;
    public final Ys e;
    public boolean f;

    public C0732x5(Ys ys, Typeface typeface) {
        this.d = typeface;
        this.e = ys;
    }

    @Override // rikka.shizuku.AbstractC0750xn
    public final void o(int i) {
        if (this.f) {
            return;
        }
        C0578s6 c0578s6 = (C0578s6) this.e.b;
        if (c0578s6.j(this.d)) {
            c0578s6.h(false);
        }
    }

    @Override // rikka.shizuku.AbstractC0750xn
    public final void p(Typeface typeface, boolean z) {
        if (this.f) {
            return;
        }
        C0578s6 c0578s6 = (C0578s6) this.e.b;
        if (c0578s6.j(typeface)) {
            c0578s6.h(false);
        }
    }
}
