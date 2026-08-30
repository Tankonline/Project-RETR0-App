package rikka.shizuku;

import android.view.View;
import android.view.Window;

/* JADX INFO: loaded from: classes.dex */
public final class O6 implements InterfaceC0681vg {
    public final /* synthetic */ int a;
    public final /* synthetic */ AbstractActivityC0051b2 b;

    public /* synthetic */ O6(AbstractActivityC0051b2 abstractActivityC0051b2, int i) {
        this.a = i;
        this.b = abstractActivityC0051b2;
    }

    @Override // rikka.shizuku.InterfaceC0681vg
    public final void a(InterfaceC0743xg interfaceC0743xg, EnumC0557rg enumC0557rg) {
        switch (this.a) {
            case 0:
                if (enumC0557rg == EnumC0557rg.ON_STOP) {
                    Window window = this.b.getWindow();
                    View viewPeekDecorView = window != null ? window.peekDecorView() : null;
                    if (viewPeekDecorView != null) {
                        viewPeekDecorView.cancelPendingInputEvents();
                    }
                }
                break;
            case 1:
                if (enumC0557rg == EnumC0557rg.ON_DESTROY) {
                    this.b.b.b = null;
                    if (!this.b.isChangingConfigurations()) {
                        this.b.d().a();
                    }
                    R6 r6 = this.b.h;
                    AbstractActivityC0051b2 abstractActivityC0051b2 = r6.c;
                    abstractActivityC0051b2.getWindow().getDecorView().removeCallbacks(r6);
                    abstractActivityC0051b2.getWindow().getDecorView().getViewTreeObserver().removeOnDrawListener(r6);
                }
                break;
            default:
                AbstractActivityC0051b2 abstractActivityC0051b3 = this.b;
                if (abstractActivityC0051b3.f == null) {
                    Q6 q6 = (Q6) abstractActivityC0051b3.getLastNonConfigurationInstance();
                    if (q6 != null) {
                        abstractActivityC0051b3.f = q6.a;
                    }
                    if (abstractActivityC0051b3.f == null) {
                        abstractActivityC0051b3.f = new Zs();
                    }
                }
                abstractActivityC0051b3.d.f(this);
                break;
        }
    }
}
