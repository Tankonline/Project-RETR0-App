package rikka.widget.borderview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import moe.shizuku.privileged.api.R;
import rikka.shizuku.C0287in;
import rikka.shizuku.InterfaceC0239h5;

/* JADX INFO: loaded from: classes.dex */
public class BorderRecyclerView extends RecyclerView implements InterfaceC0239h5 {
    public final C0287in G0;

    public BorderRecyclerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, R.attr._0_resource_name_obfuscated_res_0x7f04007e);
        this.G0 = new C0287in(this, context, attributeSet);
    }

    @Override // android.view.View
    public final void onDrawForeground(Canvas canvas) {
        super.onDrawForeground(canvas);
        this.G0.c(canvas);
    }

    @Override // android.view.View
    public final void onScrollChanged(int i, int i2, int i3, int i4) {
        int iComputeVerticalScrollOffset = computeVerticalScrollOffset();
        int iComputeVerticalScrollRange = computeVerticalScrollRange() - computeVerticalScrollExtent();
        boolean z = false;
        boolean z2 = iComputeVerticalScrollOffset == 0;
        boolean z3 = iComputeVerticalScrollOffset == iComputeVerticalScrollRange;
        C0287in c0287in = this.G0;
        int i5 = c0287in.a;
        boolean z4 = i5 == 4 || (i5 == 2 && z2) || (i5 == 3 && !z2);
        int i6 = c0287in.b;
        if (i6 == 4 || ((i6 == 2 && z3) || (i6 == 3 && !z3))) {
            z = true;
        }
        if (!Boolean.valueOf(c0287in.b()).equals(Boolean.valueOf(z4)) || !Boolean.valueOf(c0287in.a()).equals(Boolean.valueOf(z))) {
            C0287in c0287in2 = this.G0;
            c0287in2.e = Boolean.valueOf(z4);
            c0287in2.f = Boolean.valueOf(z);
            ((ViewGroup) c0287in2.d).postInvalidate();
        }
        super.onScrollChanged(i, i2, i3, i4);
    }
}
