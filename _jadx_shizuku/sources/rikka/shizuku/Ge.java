package rikka.shizuku;

import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class Ge {
    public static final Ge a = new Ge();
    public static volatile Ge b;

    public Ge(View view) {
        if (Build.VERSION.SDK_INT >= 30) {
            new C0504po(view);
        } else {
            new Fe(view);
        }
    }

    public Signature[] a(PackageManager packageManager, String str) {
        return packageManager.getPackageInfo(str, 64).signatures;
    }
}
