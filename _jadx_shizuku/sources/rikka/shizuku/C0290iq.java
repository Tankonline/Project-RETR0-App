package rikka.shizuku;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.appcompat.widget.Toolbar;
import moe.shizuku.privileged.api.R;

/* JADX INFO: renamed from: rikka.shizuku.iq, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0290iq implements InterfaceC0157eg {
    public static final C0290iq a = new C0290iq();

    @Override // rikka.shizuku.InterfaceC0157eg
    public final void a(View view, Context context, AttributeSet attributeSet) {
        if (view instanceof Toolbar) {
            Toolbar toolbar = (Toolbar) view;
            int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen._0_resource_name_obfuscated_res_0x7f070001);
            if (dimensionPixelSize < 0) {
                dimensionPixelSize = Integer.MIN_VALUE;
            }
            if (dimensionPixelSize != toolbar.u) {
                toolbar.u = dimensionPixelSize;
                if (toolbar.n() != null) {
                    toolbar.requestLayout();
                }
            }
        }
    }
}
