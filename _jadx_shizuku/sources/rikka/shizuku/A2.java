package rikka.shizuku;

import android.window.OnBackInvokedCallback;

/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class A2 implements OnBackInvokedCallback {
    public final /* synthetic */ int a;
    public final /* synthetic */ Object b;

    public /* synthetic */ A2(int i, Object obj) {
        this.a = i;
        this.b = obj;
    }

    public final void onBackInvoked() {
        switch (this.a) {
            case 0:
                ((I2) this.b).F();
                break;
            case 1:
                ((InterfaceC0124dd) this.b).a();
                break;
            default:
                ((Runnable) this.b).run();
                break;
        }
    }
}
