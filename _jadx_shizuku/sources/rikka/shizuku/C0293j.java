package rikka.shizuku;

import java.io.IOException;

/* JADX INFO: renamed from: rikka.shizuku.j, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0293j extends IOException {
    public final IllegalArgumentException a;

    public C0293j(String str, IllegalArgumentException illegalArgumentException) {
        super(str);
        this.a = illegalArgumentException;
    }

    @Override // java.lang.Throwable
    public final Throwable getCause() {
        return this.a;
    }
}
