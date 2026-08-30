package rikka.shizuku;

import android.view.Window;

/* JADX INFO: renamed from: rikka.shizuku.x2, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0729x2 implements InterfaceC0652ui {
    public final /* synthetic */ int a;
    public final /* synthetic */ I2 b;

    public /* synthetic */ C0729x2(I2 i2, int i) {
        this.a = i;
        this.b = i2;
    }

    @Override // rikka.shizuku.InterfaceC0652ui
    public void b(MenuC0129di menuC0129di, boolean z) {
        H2 h2;
        switch (this.a) {
            case 1:
                this.b.t(menuC0129di);
                break;
            default:
                MenuC0129di menuC0129diK = menuC0129di.k();
                int i = 0;
                boolean z2 = menuC0129diK != menuC0129di;
                if (z2) {
                    menuC0129di = menuC0129diK;
                }
                I2 i2 = this.b;
                H2[] h2Arr = i2.K;
                int length = h2Arr != null ? h2Arr.length : 0;
                while (true) {
                    if (i >= length) {
                        h2 = null;
                    } else {
                        h2 = h2Arr[i];
                        if (h2 == null || h2.h != menuC0129di) {
                            i++;
                        }
                    }
                }
                if (h2 != null) {
                    if (!z2) {
                        i2.u(h2, z);
                    } else {
                        i2.s(h2.a, h2, menuC0129diK);
                        i2.u(h2, true);
                    }
                }
                break;
        }
    }

    @Override // rikka.shizuku.InterfaceC0652ui
    public boolean c(MenuC0129di menuC0129di) {
        Window.Callback callback;
        switch (this.a) {
            case 1:
                Window.Callback callback2 = this.b.l.getCallback();
                if (callback2 != null) {
                    callback2.onMenuOpened(108, menuC0129di);
                }
                break;
            default:
                if (menuC0129di == menuC0129di.k()) {
                    I2 i2 = this.b;
                    if (i2.E && (callback = i2.l.getCallback()) != null && !i2.P) {
                        callback.onMenuOpened(108, menuC0129di);
                        break;
                    }
                }
                break;
        }
        return true;
    }
}
