package rikka.shizuku;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes.dex */
public final class Lh extends LinearLayoutManager {
    public final /* synthetic */ int E;
    public final /* synthetic */ Oh F;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Lh(Oh oh, int i, int i2) {
        super(i);
        this.F = oh;
        this.E = i2;
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, rikka.shizuku.AbstractC0162el
    public final void A0(RecyclerView recyclerView, int i) {
        C0411mo c0411mo = new C0411mo(recyclerView.getContext());
        c0411mo.a = i;
        B0(c0411mo);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager
    public final void D0(C0408ml c0408ml, int[] iArr) {
        int i = this.E;
        Oh oh = this.F;
        if (i == 0) {
            iArr[0] = oh.d0.getWidth();
            iArr[1] = oh.d0.getWidth();
        } else {
            iArr[0] = oh.d0.getHeight();
            iArr[1] = oh.d0.getHeight();
        }
    }
}
