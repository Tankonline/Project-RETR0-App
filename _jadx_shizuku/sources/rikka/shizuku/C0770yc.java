package rikka.shizuku;

import android.view.View;

/* JADX INFO: renamed from: rikka.shizuku.yc, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0770yc extends AbstractC0046as {
    public final /* synthetic */ Ac i;

    public C0770yc(Ac ac) {
        this.i = ac;
    }

    @Override // rikka.shizuku.AbstractC0046as
    public final View J(int i) {
        Ac ac = this.i;
        View view = ac.G;
        if (view != null) {
            return view.findViewById(i);
        }
        throw new IllegalStateException("Fragment " + ac + " does not have a view");
    }

    @Override // rikka.shizuku.AbstractC0046as
    public final boolean K() {
        return this.i.G != null;
    }
}
