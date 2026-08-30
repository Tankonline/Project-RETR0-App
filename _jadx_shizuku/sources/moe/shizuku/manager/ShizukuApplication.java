package moe.shizuku.manager;

import android.app.Application;
import android.os.Build;
import android.util.Log;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import rikka.shizuku.AbstractC0586se;
import rikka.shizuku.AbstractC0667v2;
import rikka.shizuku.AbstractC0688vn;
import rikka.shizuku.AbstractC0744xh;
import rikka.shizuku.AbstractC0750xn;
import rikka.shizuku.C0281ih;
import rikka.shizuku.C0516q5;
import rikka.shizuku.Gn;
import rikka.shizuku.Un;

/* JADX INFO: loaded from: classes.dex */
public final class ShizukuApplication extends Application {
    static {
        C0516q5 c0516q5 = new C0516q5(0);
        c0516q5.a = 8;
        ExecutorService executorService = Gn.j;
        synchronized (AbstractC0744xh.class) {
            if (AbstractC0744xh.b || AbstractC0744xh.q() != null) {
                throw new IllegalStateException("The main shell was already created");
            }
            AbstractC0744xh.c = c0516q5;
        }
        if (Build.VERSION.SDK_INT >= 28) {
            try {
                AbstractC0586se.a(AbstractC0586se.a(null, "getRuntime", new Object[0]), "setHiddenApiExemptions", new String[]{""});
            } catch (ReflectiveOperationException e) {
                Log.w("HiddenApiBypass", "setHiddenApiExemptions", e);
            }
        }
        if (Build.VERSION.SDK_INT >= 30) {
            System.loadLibrary("adb");
        }
    }

    @Override // android.app.Application
    public final void onCreate() {
        super.onCreate();
        AbstractC0688vn.a = this;
        if (AbstractC0750xn.a == null) {
            AbstractC0750xn.a = new Un(createDeviceProtectedStorageContext()).getSharedPreferences("settings", 0);
        }
        Locale locale = C0281ih.b;
        C0281ih.b = AbstractC0750xn.i();
        AbstractC0667v2.m(AbstractC0750xn.j());
    }
}
