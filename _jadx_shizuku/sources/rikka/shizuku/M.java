package rikka.shizuku;

import java.util.RandomAccess;

/* JADX INFO: loaded from: classes.dex */
public final class M extends N implements RandomAccess {
    public final N a;
    public final int b;
    public final int c;

    public M(N n, int i, int i2) {
        this.a = n;
        this.b = i;
        X8.o(i, i2, n.a());
        this.c = i2 - i;
    }

    @Override // rikka.shizuku.N
    public final int a() {
        return this.c;
    }

    @Override // java.util.List
    public final Object get(int i) {
        int i2 = this.c;
        if (i >= 0 && i < i2) {
            return this.a.get(this.b + i);
        }
        throw new IndexOutOfBoundsException("index: " + i + ", size: " + i2);
    }
}
