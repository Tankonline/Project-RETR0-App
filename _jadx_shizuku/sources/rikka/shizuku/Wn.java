package rikka.shizuku;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.util.Log;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public final class Wn implements X1 {
    public final /* synthetic */ String a;
    public final /* synthetic */ Xr b;

    public Wn(String str, Xr xr) {
        this.a = str;
        this.b = xr;
    }

    @Override // rikka.shizuku.X1
    public final void a() {
        String str;
        String str2;
        ApplicationInfo applicationInfo;
        C0176f4 c0176f4 = new C0176f4(AbstractC0688vn.s());
        while (true) {
            boolean zHasNext = c0176f4.hasNext();
            str = this.a;
            if (!zHasNext) {
                str2 = null;
                break;
            }
            PackageInfo packageInfoR = AbstractC0599sr.r(str, 0L, ((Integer) c0176f4.next()).intValue());
            if (packageInfoR != null && (applicationInfo = packageInfoR.applicationInfo) != null && (str2 = applicationInfo.sourceDir) != null) {
                break;
            }
        }
        Xr xr = this.b;
        if (str2 == null) {
            String str3 = xr.d;
            Locale locale = Locale.ENGLISH;
            Log.println(2, "UserServiceManager", "remove record " + str3 + " because package " + str + " has been removed");
            xr.b();
            return;
        }
        String str4 = xr.d;
        Locale locale2 = Locale.ENGLISH;
        Log.println(2, "UserServiceManager", "update apk listener for record " + str4 + " since package " + str + " is upgrading");
        AbstractC0599sr.M(this);
        AbstractC0599sr.L(str2, this);
    }
}
