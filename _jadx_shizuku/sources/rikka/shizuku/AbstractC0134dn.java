package rikka.shizuku;

import android.os.Build;

/* JADX INFO: renamed from: rikka.shizuku.dn, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0134dn {
    public static final C0597sp a = new C0597sp("activity", new C0579s7(12));
    public static final C0597sp b = new C0597sp("user", new C0579s7(13));
    public static final C0597sp c = new C0597sp("package", new C0579s7(14));
    public static final C0597sp d;
    public static final C0597sp e;

    static {
        int i = Build.VERSION.SDK_INT;
        if (i >= 30) {
            d = new C0597sp("permissionmgr", new C0579s7(15));
        } else {
            d = null;
        }
        if (i >= 30) {
            e = new C0597sp("deviceidle", new C0579s7(16));
        } else {
            e = null;
        }
    }
}
