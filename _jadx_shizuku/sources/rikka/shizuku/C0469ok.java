package rikka.shizuku;

import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: renamed from: rikka.shizuku.ok, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0469ok extends C0532ql {
    public final RecyclerView f;
    public final C0501pl g;
    public final R5 h;

    public C0469ok(RecyclerView recyclerView) {
        super(recyclerView);
        this.g = this.e;
        this.h = new R5(4, this);
        this.f = recyclerView;
    }

    @Override // rikka.shizuku.C0532ql
    public final S j() {
        return this.h;
    }
}
