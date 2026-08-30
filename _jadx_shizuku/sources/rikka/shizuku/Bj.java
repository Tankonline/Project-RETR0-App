package rikka.shizuku;

import android.os.SELinux;

/* JADX INFO: loaded from: classes.dex */
public abstract class Bj {
    public static final int a = android.system.Os.getuid();
    public static final int b = android.system.Os.getpid();

    static {
        try {
            SELinux.getContext();
        } catch (Throwable unused) {
        }
    }
}
