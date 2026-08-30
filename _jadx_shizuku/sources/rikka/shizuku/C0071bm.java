package rikka.shizuku;

import java.io.Serializable;

/* JADX INFO: renamed from: rikka.shizuku.bm, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0071bm implements Serializable {
    public final Throwable a;

    public C0071bm(Throwable th) {
        this.a = th;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof C0071bm) {
            return AbstractC0046as.c(this.a, ((C0071bm) obj).a);
        }
        return false;
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    public final String toString() {
        return "Failure(" + this.a + ')';
    }
}
