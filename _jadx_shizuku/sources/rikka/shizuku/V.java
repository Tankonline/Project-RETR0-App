package rikka.shizuku;

import android.app.Notification;
import android.app.NotificationChannel;
import android.content.Context;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.Drawable;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.Executable;
import moe.shizuku.manager.adb.AdbPairingService;

/* JADX INFO: loaded from: classes.dex */
public abstract /* synthetic */ class V {
    public static /* bridge */ /* synthetic */ Class C() {
        return MethodHandle.class;
    }

    public static /* synthetic */ Notification.Builder c(Context context) {
        return new Notification.Builder(context, "adb_pairing");
    }

    public static /* synthetic */ Notification.Builder d(AdbPairingService adbPairingService) {
        return new Notification.Builder(adbPairingService, "adb_pairing");
    }

    public static /* synthetic */ NotificationChannel f(String str) {
        return new NotificationChannel("adb_pairing", str, 4);
    }

    public static /* bridge */ /* synthetic */ AdaptiveIconDrawable i(Object obj) {
        return (AdaptiveIconDrawable) obj;
    }

    public static /* bridge */ /* synthetic */ Class l() {
        return Executable.class;
    }

    public static /* synthetic */ void o() {
    }

    public static /* bridge */ /* synthetic */ boolean z(Drawable drawable) {
        return drawable instanceof AdaptiveIconDrawable;
    }
}
