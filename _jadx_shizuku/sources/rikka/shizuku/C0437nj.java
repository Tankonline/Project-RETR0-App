package rikka.shizuku;

import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;

/* JADX INFO: renamed from: rikka.shizuku.nj, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0437nj {
    public static final C0437nj a = new C0437nj();

    public final OnBackInvokedCallback a(InterfaceC0124dd interfaceC0124dd) {
        return new A2(1, interfaceC0124dd);
    }

    public final void b(Object obj, int i, Object obj2) {
        ((OnBackInvokedDispatcher) obj).registerOnBackInvokedCallback(i, (OnBackInvokedCallback) obj2);
    }

    public final void c(Object obj, Object obj2) {
        ((OnBackInvokedDispatcher) obj).unregisterOnBackInvokedCallback((OnBackInvokedCallback) obj2);
    }
}
