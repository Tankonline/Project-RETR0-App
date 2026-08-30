package rikka.shizuku;

import android.app.ActivityManager;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.text.TextUtils;
import java.util.ArrayList;
import rikka.shizuku.server.ShizukuService;

/* JADX INFO: renamed from: rikka.shizuku.e5, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0146e5 {
    public static final C0589sh a = new C0589sh(0, "BinderSender");
    public static ShizukuService b;

    public static void a(int i, int i2) {
        String[] strArr;
        ArrayList arrayListS = AbstractC0599sr.s(i);
        if (arrayListS.isEmpty()) {
            return;
        }
        TextUtils.join(", ", arrayListS);
        int i3 = i / 100000;
        int size = arrayListS.size();
        int i4 = 0;
        while (i4 < size) {
            Object obj = arrayListS.get(i4);
            i4++;
            String str = (String) obj;
            PackageInfo packageInfoR = AbstractC0599sr.r(str, 4096L, i3);
            if (packageInfoR != null && (strArr = packageInfoR.requestedPermissions) != null) {
                if (AbstractC0484p4.d0(strArr, "moe.shizuku.manager.permission.MANAGER")) {
                    if (i2 == -1) {
                        if (X8.n(i, "moe.shizuku.manager.permission.MANAGER") == 0) {
                            ShizukuService.s(b, "moe.shizuku.privileged.api", i3, true);
                            return;
                        }
                    } else if (AbstractC0134dn.a.a().checkPermission("moe.shizuku.manager.permission.MANAGER", i2, i) == 0) {
                        ShizukuService.s(b, "moe.shizuku.privileged.api", i3, true);
                        return;
                    }
                } else if (AbstractC0484p4.d0(packageInfoR.requestedPermissions, "moe.shizuku.manager.permission.API_V23")) {
                    ShizukuService.s(b, str, i3, true);
                    return;
                }
            }
        }
    }

    public static void b(ShizukuService shizukuService) {
        C0589sh c0589sh = a;
        b = shizukuService;
        try {
            AbstractC0134dn.a.a().registerProcessObserver(new IProcessObserverStubC0085c5());
        } catch (Throwable th) {
            c0589sh.a(th, "registerProcessObserver", new Object[0]);
        }
        int i = Build.VERSION.SDK_INT;
        if (i >= 26) {
            int i2 = ActivityManager.UID_OBSERVER_GONE | ActivityManager.UID_OBSERVER_IDLE | ActivityManager.UID_OBSERVER_ACTIVE;
            if (i >= 27) {
                i2 |= ActivityManager.UID_OBSERVER_CACHED;
            }
            try {
                AbstractC0134dn.a.a().registerUidObserver(new IUidObserverStubC0116d5(), i2, ActivityManager.PROCESS_STATE_UNKNOWN, (String) null);
            } catch (Throwable th2) {
                c0589sh.a(th2, "registerUidObserver", new Object[0]);
            }
        }
    }
}
