package defpackage;

import android.os.Handler;
import android.os.Looper;

/* JADX INFO: loaded from: assets/rish_shizuku.dex */
public abstract class D {
    public static Handler a(Looper looper) {
        return Handler.createAsync(looper);
    }
}
