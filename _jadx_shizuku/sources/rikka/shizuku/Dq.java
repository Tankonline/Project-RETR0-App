package rikka.shizuku;

import android.os.Build;

/* JADX INFO: loaded from: classes.dex */
public abstract class Dq {
    public static final boolean a;

    static {
        a = Build.VERSION.SDK_INT >= 28;
    }
}
