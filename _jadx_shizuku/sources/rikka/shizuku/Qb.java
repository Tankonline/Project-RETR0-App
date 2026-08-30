package rikka.shizuku;

import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public final class Qb extends C0502pm {
    public final HashMap e = new HashMap();

    @Override // rikka.shizuku.C0502pm
    public final C0409mm a(Object obj) {
        return (C0409mm) this.e.get(obj);
    }

    @Override // rikka.shizuku.C0502pm
    public final Object b(Object obj) {
        Object objB = super.b(obj);
        this.e.remove(obj);
        return objB;
    }
}
