package rikka.shizuku;

import android.view.View;
import androidx.appcompat.widget.ActionBarContainer;
import androidx.appcompat.widget.ActionBarOverlayLayout;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public final class At extends AbstractC0688vn {
    public final /* synthetic */ int b;
    public final /* synthetic */ Ct c;

    public /* synthetic */ At(Ct ct, int i) {
        this.b = i;
        this.c = ct;
    }

    @Override // rikka.shizuku.InterfaceC0170et
    public final void a() {
        View view;
        Ct ct = this.c;
        switch (this.b) {
            case 0:
                if (ct.E && (view = ct.w) != null) {
                    view.setTranslationY(0.0f);
                    ct.t.setTranslationY(0.0f);
                }
                ct.t.setVisibility(8);
                ActionBarContainer actionBarContainer = ct.t;
                actionBarContainer.a = false;
                actionBarContainer.setDescendantFocusability(262144);
                ct.I = null;
                R2 r2 = ct.A;
                if (r2 != null) {
                    r2.L(ct.z);
                    ct.z = null;
                    ct.A = null;
                }
                ActionBarOverlayLayout actionBarOverlayLayout = ct.s;
                if (actionBarOverlayLayout != null) {
                    WeakHashMap weakHashMap = Hs.a;
                    AbstractC0755xs.c(actionBarOverlayLayout);
                }
                break;
            default:
                ct.I = null;
                ct.t.requestLayout();
                break;
        }
    }
}
