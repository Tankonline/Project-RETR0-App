package rikka.shizuku;

import android.view.MenuItem;

/* JADX INFO: renamed from: rikka.shizuku.gq, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0229gq implements InterfaceC0067bi {
    public final /* synthetic */ C0260hq a;

    public /* synthetic */ C0229gq(C0260hq c0260hq) {
        this.a = c0260hq;
    }

    @Override // rikka.shizuku.InterfaceC0067bi
    public void a(MenuC0129di menuC0129di) {
        C0260hq c0260hq = this.a;
        boolean zR = c0260hq.q.a.r();
        C2 c2 = c0260hq.r;
        if (zR) {
            c2.onPanelClosed(108, menuC0129di);
        } else if (c2.onPreparePanel(0, null, menuC0129di)) {
            c2.onMenuOpened(108, menuC0129di);
        }
    }

    @Override // rikka.shizuku.InterfaceC0067bi
    public boolean n(MenuC0129di menuC0129di, MenuItem menuItem) {
        return false;
    }
}
