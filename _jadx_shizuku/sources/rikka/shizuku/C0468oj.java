package rikka.shizuku;

import android.window.BackEvent;
import android.window.OnBackAnimationCallback;

/* JADX INFO: renamed from: rikka.shizuku.oj, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0468oj implements OnBackAnimationCallback {
    public final /* synthetic */ InterfaceC0462od a;
    public final /* synthetic */ InterfaceC0462od b;
    public final /* synthetic */ InterfaceC0124dd c;
    public final /* synthetic */ InterfaceC0124dd d;

    public C0468oj(InterfaceC0462od interfaceC0462od, InterfaceC0462od interfaceC0462od2, InterfaceC0124dd interfaceC0124dd, InterfaceC0124dd interfaceC0124dd2) {
        this.a = interfaceC0462od;
        this.b = interfaceC0462od2;
        this.c = interfaceC0124dd;
        this.d = interfaceC0124dd2;
    }

    public final void onBackCancelled() {
        this.d.a();
    }

    public final void onBackInvoked() {
        this.c.a();
    }

    public final void onBackProgressed(BackEvent backEvent) {
        this.b.g(new K4(backEvent));
    }

    public final void onBackStarted(BackEvent backEvent) {
        this.a.g(new K4(backEvent));
    }
}
