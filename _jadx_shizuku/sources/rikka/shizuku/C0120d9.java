package rikka.shizuku;

import android.content.pm.PackageManager;
import android.content.pm.Signature;

/* JADX INFO: renamed from: rikka.shizuku.d9, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0120d9 extends Ge {
    @Override // rikka.shizuku.Ge
    public final Signature[] a(PackageManager packageManager, String str) {
        return packageManager.getPackageInfo(str, 64).signatures;
    }
}
