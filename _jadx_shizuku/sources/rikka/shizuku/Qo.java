package rikka.shizuku;

import java.io.File;
import moe.shizuku.manager.ShizukuApplication;

/* JADX INFO: loaded from: classes.dex */
public abstract class Qo {
    public static final String a;
    public static final String b;

    static {
        ShizukuApplication shizukuApplication = AbstractC0688vn.a;
        if (shizukuApplication == null) {
            shizukuApplication = null;
        }
        String absolutePath = new File(shizukuApplication.getApplicationInfo().nativeLibraryDir, "libshizuku.so").getAbsolutePath();
        a = "adb shell " + absolutePath;
        ShizukuApplication shizukuApplication2 = AbstractC0688vn.a;
        b = absolutePath + " --apk=" + (shizukuApplication2 != null ? shizukuApplication2 : null).getApplicationInfo().sourceDir;
    }
}
