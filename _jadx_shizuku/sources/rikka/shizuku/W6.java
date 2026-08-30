package rikka.shizuku;

import android.os.Handler;
import android.os.Looper;

/* JADX INFO: loaded from: classes.dex */
public abstract class W6 {
    public static Handler a(Looper looper) {
        return Handler.createAsync(looper);
    }
}
