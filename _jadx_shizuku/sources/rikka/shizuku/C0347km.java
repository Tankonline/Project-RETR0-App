package rikka.shizuku;

import java.security.SignatureException;

/* JADX INFO: renamed from: rikka.shizuku.km, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0347km extends RuntimeException {
    public SignatureException a;

    @Override // java.lang.Throwable
    public final Throwable getCause() {
        return this.a;
    }
}
