package defpackage;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import androidx.profileinstaller.ProfileInstallerInitializer;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import rikka.html.text.style.BulletSpan;
import rikka.shizuku.shell.ShizukuShellLoader;

/* JADX INFO: loaded from: assets/rish_shizuku.dex */
public final /* synthetic */ class z implements Runnable {
    public final /* synthetic */ int a;
    public final /* synthetic */ Object b;

    public /* synthetic */ z(int i, Object obj) {
        this.a = i;
        this.b = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = 1;
        Object obj = this.b;
        switch (this.a) {
            case 0:
                (Build.VERSION.SDK_INT >= 28 ? D.a(Looper.getMainLooper()) : new Handler(Looper.getMainLooper())).postDelayed(new z(i, (Context) obj), new Random().nextInt(Math.max(1000, 1)) + 5000);
                break;
            case 1:
                new ThreadPoolExecutor(0, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue()).execute(new z(2, (Context) obj));
                break;
            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                t.A((Context) obj, new v(), t.c, false);
                break;
            default:
                String[] strArr = ShizukuShellLoader.a;
                System.err.println(String.format("Request timeout. The connection between the current app (%1$s) and Shizuku app may be blocked by your system. Please disable all battery optimization features for both current app (%1$s) and Shizuku app.", (String) obj));
                System.err.flush();
                System.exit(1);
                break;
        }
    }

    public /* synthetic */ z(ProfileInstallerInitializer profileInstallerInitializer, Context context) {
        this.a = 0;
        this.b = context;
    }
}
