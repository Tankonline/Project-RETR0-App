package rikka.shizuku;

import java.io.Serializable;

/* JADX INFO: renamed from: rikka.shizuku.dg, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0127dg implements Cd, Serializable {
    public final int a;

    public AbstractC0127dg(int i) {
        this.a = i;
    }

    @Override // rikka.shizuku.Cd
    public final int b() {
        return this.a;
    }

    public final String toString() {
        AbstractC0593sl.a.getClass();
        return C0624tl.a(this);
    }
}
