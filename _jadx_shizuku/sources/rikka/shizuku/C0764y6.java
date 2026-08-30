package rikka.shizuku;

import java.util.Enumeration;
import java.util.Iterator;

/* JADX INFO: renamed from: rikka.shizuku.y6, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0764y6 implements Iterator {
    public final /* synthetic */ Enumeration a;

    public C0764y6(Enumeration enumeration) {
        this.a = enumeration;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.a.hasMoreElements();
    }

    @Override // java.util.Iterator
    public final Object next() {
        return this.a.nextElement();
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}
