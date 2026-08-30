package rikka.shizuku;

import android.app.Dialog;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public final class T9 extends AbstractC0046as {
    public final /* synthetic */ C0770yc i;
    public final /* synthetic */ U9 j;

    public T9(U9 u9, C0770yc c0770yc) {
        this.j = u9;
        this.i = c0770yc;
    }

    @Override // rikka.shizuku.AbstractC0046as
    public final View J(int i) {
        C0770yc c0770yc = this.i;
        if (c0770yc.K()) {
            return c0770yc.J(i);
        }
        Dialog dialog = this.j.h0;
        if (dialog != null) {
            return dialog.findViewById(i);
        }
        return null;
    }

    @Override // rikka.shizuku.AbstractC0046as
    public final boolean K() {
        return this.i.K() || this.j.l0;
    }
}
