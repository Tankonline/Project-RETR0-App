package rikka.shizuku;

import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: renamed from: rikka.shizuku.oo, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0473oo extends AbstractC0224gl {
    public boolean a = false;
    public final /* synthetic */ Xn b;

    public C0473oo(Xn xn) {
        this.b = xn;
    }

    @Override // rikka.shizuku.AbstractC0224gl
    public final void a(RecyclerView recyclerView, int i) {
        if (i == 0 && this.a) {
            this.a = false;
            this.b.x();
        }
    }

    @Override // rikka.shizuku.AbstractC0224gl
    public final void b(RecyclerView recyclerView, int i, int i2) {
        if (i == 0 && i2 == 0) {
            return;
        }
        this.a = true;
    }
}
