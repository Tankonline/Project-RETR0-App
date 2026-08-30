package rikka.shizuku;

import android.view.View;
import androidx.appcompat.view.menu.ActionMenuItemView;

/* JADX INFO: renamed from: rikka.shizuku.x0, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0727x0 extends AbstractViewOnTouchListenerC0708wc {
    public final /* synthetic */ int j = 0;
    public final /* synthetic */ View k;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0727x0(ActionMenuItemView actionMenuItemView) {
        super(actionMenuItemView);
        this.k = actionMenuItemView;
    }

    @Override // rikka.shizuku.AbstractViewOnTouchListenerC0708wc
    public final Yn b() {
        C0758y0 c0758y0;
        switch (this.j) {
            case 0:
                C0789z0 c0789z0 = ((ActionMenuItemView) this.k).l;
                if (c0789z0 == null || (c0758y0 = c0789z0.a.r) == null) {
                    return null;
                }
                return c0758y0.a();
            default:
                C0758y0 c0758y1 = ((B0) this.k).d.q;
                if (c0758y1 == null) {
                    return null;
                }
                return c0758y1.a();
        }
    }

    @Override // rikka.shizuku.AbstractViewOnTouchListenerC0708wc
    public final boolean c() {
        Yn ynB;
        switch (this.j) {
            case 0:
                ActionMenuItemView actionMenuItemView = (ActionMenuItemView) this.k;
                InterfaceC0098ci interfaceC0098ci = actionMenuItemView.j;
                return interfaceC0098ci != null && interfaceC0098ci.c(actionMenuItemView.g) && (ynB = b()) != null && ynB.a();
            default:
                ((B0) this.k).d.l();
                return true;
        }
    }

    @Override // rikka.shizuku.AbstractViewOnTouchListenerC0708wc
    public boolean d() {
        switch (this.j) {
            case 1:
                C0 c0 = ((B0) this.k).d;
                if (c0.s != null) {
                    return false;
                }
                c0.e();
                return true;
            default:
                return super.d();
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0727x0(B0 b0, B0 b1) {
        super(b1);
        this.k = b0;
    }
}
