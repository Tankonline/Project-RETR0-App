package rikka.shizuku;

import android.app.Activity;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;

/* JADX INFO: loaded from: classes.dex */
public abstract class B2 {
    public static OnBackInvokedDispatcher a(Activity activity) {
        return activity.getOnBackInvokedDispatcher();
    }

    public static OnBackInvokedCallback b(Object obj, I2 i2) {
        A2 a2 = new A2(0, i2);
        Z.d(obj).registerOnBackInvokedCallback(1000000, a2);
        return a2;
    }

    public static void c(Object obj, Object obj2) {
        Z.d(obj).unregisterOnBackInvokedCallback(Z.b(obj2));
    }
}
