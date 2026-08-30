package rikka.shizuku;

import java.security.GeneralSecurityException;

/* JADX INFO: renamed from: rikka.shizuku.yj, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0777yj extends Exception {
    public GeneralSecurityException a;

    @Override // java.lang.Throwable
    public final Throwable getCause() {
        return this.a;
    }
}
