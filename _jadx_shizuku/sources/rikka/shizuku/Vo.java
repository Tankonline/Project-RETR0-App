package rikka.shizuku;

import java.security.PrivilegedAction;

/* JADX INFO: loaded from: classes.dex */
public final class Vo implements PrivilegedAction {
    @Override // java.security.PrivilegedAction
    public final Object run() {
        return System.getProperty("line.separator");
    }
}
