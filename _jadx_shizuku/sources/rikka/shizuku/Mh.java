package rikka.shizuku;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes.dex */
public final class Mh extends AbstractC0070bl {
    public final /* synthetic */ Oh a;

    public Mh(Oh oh) {
        this.a = oh;
        Zr.c(null);
        Zr.c(null);
    }

    @Override // rikka.shizuku.AbstractC0070bl
    public final void b(RecyclerView recyclerView) {
        if ((recyclerView.m instanceof qu) && (recyclerView.n instanceof GridLayoutManager)) {
            this.a.getClass();
            throw null;
        }
    }
}
