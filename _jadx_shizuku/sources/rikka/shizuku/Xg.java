package rikka.shizuku;

import android.os.Handler;
import android.widget.AbsListView;

/* JADX INFO: loaded from: classes.dex */
public final class Xg implements AbsListView.OnScrollListener {
    public final /* synthetic */ Zg a;

    public Xg(Zg zg) {
        this.a = zg;
    }

    @Override // android.widget.AbsListView.OnScrollListener
    public final void onScrollStateChanged(AbsListView absListView, int i) {
        if (i == 1) {
            Zg zg = this.a;
            if (zg.z.getInputMethodMode() == 2 || zg.z.getContentView() == null) {
                return;
            }
            Handler handler = zg.v;
            Vg vg = zg.r;
            handler.removeCallbacks(vg);
            vg.run();
        }
    }

    @Override // android.widget.AbsListView.OnScrollListener
    public final void onScroll(AbsListView absListView, int i, int i2, int i3) {
    }
}
