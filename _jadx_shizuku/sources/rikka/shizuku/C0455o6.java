package rikka.shizuku;

import android.os.Bundle;

/* JADX INFO: renamed from: rikka.shizuku.o6, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0455o6 {
    public static final C0589sh g = new C0589sh(0, "ClientRecord");
    public final int a;
    public final int b;
    public final Ye c;
    public final String d;
    public final int e;
    public boolean f = false;

    public C0455o6(int i, int i2, Ye ye, String str, int i3) {
        this.a = i;
        this.b = i2;
        this.c = ye;
        this.d = str;
        this.e = i3;
    }

    public final void a(int i, boolean z) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("shizuku:request-permission-reply-allowed", z);
        try {
            this.c.g(i, bundle);
        } catch (Throwable th) {
            g.c(th, "dispatchRequestPermissionResult failed for client (uid=%d, pid=%d, package=%s)", Integer.valueOf(this.a), Integer.valueOf(this.b), this.d);
        }
    }
}
