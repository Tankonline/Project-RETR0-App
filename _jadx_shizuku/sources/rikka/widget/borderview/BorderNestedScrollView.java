package rikka.widget.borderview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.core.widget.NestedScrollView;
import moe.shizuku.privileged.api.R;
import rikka.shizuku.C0287in;
import rikka.shizuku.InterfaceC0239h5;

/* JADX INFO: loaded from: classes.dex */
public class BorderNestedScrollView extends NestedScrollView implements InterfaceC0239h5 {
    public final C0287in F;

    public BorderNestedScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, R.attr._0_resource_name_obfuscated_res_0x7f04007e);
        this.F = new C0287in(this, context, attributeSet);
    }

    @Override // android.view.View
    public final void onDrawForeground(Canvas canvas) {
        super.onDrawForeground(canvas);
        this.F.c(canvas);
    }

    @Override // androidx.core.widget.NestedScrollView, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        z();
    }

    @Override // androidx.core.widget.NestedScrollView, android.view.View
    public final void onScrollChanged(int i, int i2, int i3, int i4) {
        z();
        super.onScrollChanged(i, i2, i3, i4);
    }

    public final void z() {
        int iMax;
        int scrollY = getScrollY();
        boolean z = false;
        if (getChildCount() > 0) {
            View childAt = getChildAt(0);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
            iMax = Math.max(0, ((childAt.getHeight() + layoutParams.topMargin) + layoutParams.bottomMargin) - ((getHeight() - getPaddingTop()) - getPaddingBottom()));
        } else {
            iMax = 0;
        }
        if (iMax != 0) {
            boolean z2 = scrollY == 0;
            boolean z3 = scrollY == iMax;
            C0287in c0287in = this.F;
            int i = c0287in.a;
            boolean z4 = i == 4 || (i == 2 && z2) || (i == 3 && !z2);
            int i2 = c0287in.b;
            if (i2 == 4 || ((i2 == 2 && z3) || (i2 == 3 && !z3))) {
                z = true;
            }
            if (Boolean.valueOf(c0287in.b()).equals(Boolean.valueOf(z4)) && Boolean.valueOf(c0287in.a()).equals(Boolean.valueOf(z))) {
                return;
            }
            C0287in c0287in2 = this.F;
            c0287in2.e = Boolean.valueOf(z4);
            c0287in2.f = Boolean.valueOf(z);
            ((ViewGroup) c0287in2.d).postInvalidate();
        }
    }
}
