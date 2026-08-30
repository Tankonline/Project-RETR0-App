package rikka.shizuku;

import java.util.Map;

/* JADX INFO: renamed from: rikka.shizuku.mm, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0409mm implements Map.Entry {
    public final Object a;
    public final Object b;
    public C0409mm c;
    public C0409mm d;

    public C0409mm(Object obj, Object obj2) {
        this.a = obj;
        this.b = obj2;
    }

    @Override // java.util.Map.Entry
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof C0409mm)) {
            return false;
        }
        C0409mm c0409mm = (C0409mm) obj;
        return this.a.equals(c0409mm.a) && this.b.equals(c0409mm.b);
    }

    @Override // java.util.Map.Entry
    public final Object getKey() {
        return this.a;
    }

    @Override // java.util.Map.Entry
    public final Object getValue() {
        return this.b;
    }

    @Override // java.util.Map.Entry
    public final int hashCode() {
        return this.a.hashCode() ^ this.b.hashCode();
    }

    @Override // java.util.Map.Entry
    public final Object setValue(Object obj) {
        throw new UnsupportedOperationException("An entry modification is not supported");
    }

    public final String toString() {
        return this.a + "=" + this.b;
    }
}
