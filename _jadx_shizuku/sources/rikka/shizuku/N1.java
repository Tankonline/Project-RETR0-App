package rikka.shizuku;

import android.widget.EdgeEffect;
import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes.dex */
public final class N1 extends Pe {
    public final /* synthetic */ int c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ N1(int i) {
        super(3);
        this.c = i;
    }

    @Override // rikka.shizuku.Pe
    public final EdgeEffect a(RecyclerView recyclerView, int i) {
        switch (this.c) {
            case 0:
                return new M1(i, recyclerView, recyclerView.getContext());
            default:
                return new EdgeEffect(recyclerView.getContext());
        }
    }
}
