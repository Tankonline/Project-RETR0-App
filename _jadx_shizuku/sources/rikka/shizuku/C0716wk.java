package rikka.shizuku;

import java.security.PrivilegedAction;
import java.security.Security;

/* JADX INFO: renamed from: rikka.shizuku.wk, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0716wk implements PrivilegedAction {
    public final /* synthetic */ int a;
    public final /* synthetic */ String b;

    public /* synthetic */ C0716wk(int i, String str) {
        this.a = i;
        this.b = str;
    }

    @Override // java.security.PrivilegedAction
    public final Object run() {
        switch (this.a) {
            case 0:
                return Security.getProperty(this.b);
            default:
                return System.getProperty(this.b);
        }
    }
}
