package rikka.shizuku;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Executors;

/* JADX INFO: loaded from: classes.dex */
public final class H9 extends AbstractC0688vn {
    public final Object b = new Object();
    public volatile Handler c;

    public H9() {
        Executors.newFixedThreadPool(4, new F9());
    }

    public static Handler C(Looper looper) {
        if (Build.VERSION.SDK_INT >= 28) {
            return G9.a(looper);
        }
        try {
            return (Handler) Handler.class.getDeclaredConstructor(Looper.class, Handler.Callback.class, Boolean.TYPE).newInstance(looper, null, Boolean.TRUE);
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException unused) {
            return new Handler(looper);
        } catch (InvocationTargetException unused2) {
            return new Handler(looper);
        }
    }
}
