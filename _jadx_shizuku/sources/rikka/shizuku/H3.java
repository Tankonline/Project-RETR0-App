package rikka.shizuku;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.UserHandle;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;

/* JADX INFO: loaded from: classes.dex */
public final class H3 implements InterfaceC0026a8 {
    public static final Ab c;
    public static final H3 a = new H3();
    public static final LinkedHashMap d = new LinkedHashMap();
    public static final E3 b = new E3((int) ((Runtime.getRuntime().maxMemory() / ((long) 1024)) / ((long) 4)), 0);

    static {
        int iAvailableProcessors;
        try {
            iAvailableProcessors = Runtime.getRuntime().availableProcessors();
        } catch (Exception unused) {
            iAvailableProcessors = 1;
        }
        int i = iAvailableProcessors / 2;
        c = new Ab(Executors.newFixedThreadPool(1 < i ? i : 1));
    }

    public static Bitmap a(String str, int i, int i2) {
        return (Bitmap) b.f(new Gq(str, Integer.valueOf(i), Integer.valueOf(i2)));
    }

    public static Bitmap b(Context context, ApplicationInfo applicationInfo, int i, int i2) {
        Bitmap bitmapA = a(applicationInfo.packageName, i, i2);
        if (bitmapA != null) {
            return bitmapA;
        }
        LinkedHashMap linkedHashMap = d;
        J3 j3 = (J3) linkedHashMap.get(Integer.valueOf(i2));
        if (j3 == null) {
            J3 j4 = new J3(i2, context, Build.VERSION.SDK_INT >= 30 && V.z(context.getApplicationInfo().loadIcon(context.getPackageManager())));
            linkedHashMap.put(Integer.valueOf(i2), j4);
            j3 = j4;
        }
        Context context2 = j3.c;
        Drawable drawableLoadUnbadgedIcon = applicationInfo.loadUnbadgedIcon(context2.getPackageManager());
        UserHandle userHandleForUid = UserHandle.getUserHandleForUid(applicationInfo.uid);
        ConcurrentLinkedQueue concurrentLinkedQueue = j3.d;
        I3 i3 = (I3) concurrentLinkedQueue.poll();
        if (i3 == null) {
            i3 = new I3(context2, j3.a);
        }
        try {
            Bitmap bitmap = (Bitmap) i3.a(drawableLoadUnbadgedIcon, userHandleForUid, j3.b).b;
            concurrentLinkedQueue.offer(i3);
            String str = applicationInfo.packageName;
            if (a(str, i, i2) == null) {
                b.j(new Gq(str, Integer.valueOf(i), Integer.valueOf(i2)), bitmap);
            }
            return bitmap;
        } catch (Throwable th) {
            concurrentLinkedQueue.offer(i3);
            throw th;
        }
    }

    @Override // rikka.shizuku.InterfaceC0026a8
    public final V7 f() {
        C0550r9 c0550r9 = AbstractC0090ca.a;
        return AbstractC0713wh.a;
    }
}
