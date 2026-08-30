package rikka.shizuku;

import android.util.Log;
import java.lang.reflect.Method;
import java.util.HashMap;

/* JADX INFO: renamed from: rikka.shizuku.tp, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0628tp {
    public static final HashMap a = new HashMap();
    public static final Method b;

    static {
        new HashMap();
        try {
            b = Class.forName("android.os.ServiceManager").getMethod("getService", String.class);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            Log.w("SystemServiceHelper", Log.getStackTraceString(e));
        }
    }
}
