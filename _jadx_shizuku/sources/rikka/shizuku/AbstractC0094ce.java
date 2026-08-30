package rikka.shizuku;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.Choreographer;

/* JADX INFO: renamed from: rikka.shizuku.ce, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0094ce {
    private static volatile Choreographer choreographer;

    static {
        Object c0071bm;
        try {
            c0071bm = new C0063be(a(Looper.getMainLooper()), false);
        } catch (Throwable th) {
            c0071bm = new C0071bm(th);
        }
        if (c0071bm instanceof C0071bm) {
            c0071bm = null;
        }
    }

    public static final Handler a(Looper looper) {
        if (Build.VERSION.SDK_INT >= 28) {
            return (Handler) Handler.class.getDeclaredMethod("createAsync", Looper.class).invoke(null, looper);
        }
        try {
            return (Handler) Handler.class.getDeclaredConstructor(Looper.class, Handler.Callback.class, Boolean.TYPE).newInstance(looper, null, Boolean.TRUE);
        } catch (NoSuchMethodException unused) {
            return new Handler(looper);
        }
    }
}
