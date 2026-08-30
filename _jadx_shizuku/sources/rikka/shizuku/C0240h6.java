package rikka.shizuku;

import java.lang.reflect.Method;

/* JADX INFO: renamed from: rikka.shizuku.h6, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0240h6 {
    public final int a;
    public final Method b;

    public C0240h6(int i, Method method) {
        this.a = i;
        this.b = method;
        method.setAccessible(true);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C0240h6)) {
            return false;
        }
        C0240h6 c0240h6 = (C0240h6) obj;
        return this.a == c0240h6.a && this.b.getName().equals(c0240h6.b.getName());
    }

    public final int hashCode() {
        return this.b.getName().hashCode() + (this.a * 31);
    }
}
