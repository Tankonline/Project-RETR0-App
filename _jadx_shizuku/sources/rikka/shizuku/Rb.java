package rikka.shizuku;

import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes.dex */
public final class Rb extends AbstractC0224gl {
    public final /* synthetic */ Tb a;

    public Rb(Tb tb) {
        this.a = tb;
    }

    @Override // rikka.shizuku.AbstractC0224gl
    public final void b(RecyclerView recyclerView, int i, int i2) {
        int iComputeHorizontalScrollOffset = recyclerView.computeHorizontalScrollOffset();
        int iComputeVerticalScrollOffset = recyclerView.computeVerticalScrollOffset();
        Tb tb = this.a;
        int iComputeVerticalScrollRange = tb.s.computeVerticalScrollRange();
        int i3 = tb.r;
        int i4 = iComputeVerticalScrollRange - i3;
        int i5 = tb.a;
        tb.t = i4 > 0 && i3 >= i5;
        int iComputeHorizontalScrollRange = tb.s.computeHorizontalScrollRange();
        int i6 = tb.q;
        boolean z = iComputeHorizontalScrollRange - i6 > 0 && i6 >= i5;
        tb.u = z;
        boolean z2 = tb.t;
        if (!z2 && !z) {
            if (tb.v != 0) {
                tb.g(0);
                return;
            }
            return;
        }
        if (z2) {
            float f = i3;
            tb.l = (int) ((((f / 2.0f) + iComputeVerticalScrollOffset) * f) / iComputeVerticalScrollRange);
            tb.k = Math.min(i3, (i3 * i3) / iComputeVerticalScrollRange);
        }
        if (tb.u) {
            float f2 = iComputeHorizontalScrollOffset;
            float f3 = i6;
            tb.o = (int) ((((f3 / 2.0f) + f2) * f3) / iComputeHorizontalScrollRange);
            tb.n = Math.min(i6, (i6 * i6) / iComputeHorizontalScrollRange);
        }
        int i7 = tb.v;
        if (i7 == 0 || i7 == 1) {
            tb.g(1);
        }
    }
}
