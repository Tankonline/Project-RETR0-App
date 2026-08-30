package rikka.shizuku;

import android.os.SELinux;

/* JADX INFO: loaded from: classes.dex */
public abstract class Cj {
    public static final int a = android.system.Os.getuid();
    public static final String b;

    static {
        String context;
        android.system.Os.getpid();
        try {
            context = SELinux.getContext();
        } catch (Throwable unused) {
            context = null;
        }
        b = context;
    }
}
