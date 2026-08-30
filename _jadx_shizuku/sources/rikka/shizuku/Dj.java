package rikka.shizuku;

import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes.dex */
public final class Dj implements View.OnLayoutChangeListener {
    public boolean a;

    /* JADX WARN: Code duplicated, block: B:22:0x004c  */
    @Override // android.view.View.OnLayoutChangeListener
    public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        Yk yk;
        RecyclerView recyclerView = (RecyclerView) view;
        boolean z = false;
        if (recyclerView.n != null && (yk = recyclerView.m) != null && yk.a() != 0) {
            AbstractC0162el abstractC0162el = recyclerView.n;
            if (abstractC0162el instanceof LinearLayoutManager) {
                int iB = abstractC0162el.B();
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.n;
                View viewP0 = linearLayoutManager.P0(0, linearLayoutManager.v(), true, false);
                int iG = viewP0 == null ? -1 : AbstractC0162el.G(viewP0);
                LinearLayoutManager linearLayoutManager2 = (LinearLayoutManager) recyclerView.n;
                View viewP1 = linearLayoutManager2.P0(linearLayoutManager2.v() - 1, -1, true, false);
                int iG2 = viewP1 != null ? AbstractC0162el.G(viewP1) : -1;
                if (iG != 0 || iG2 != iB - 1) {
                    z = true;
                }
            } else {
                z = true;
            }
        }
        boolean z2 = this.a;
        if (z != z2) {
            this.a = !z2;
            if (z2) {
                view.setOverScrollMode(2);
            } else {
                view.setOverScrollMode(1);
            }
        }
    }
}
