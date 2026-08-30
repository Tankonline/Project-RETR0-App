package rikka.shizuku;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class Og implements Map.Entry {
    public Og a;
    public Og b;
    public Og c;
    public Og d;
    public Og e;
    public final Object f;
    public final boolean g;
    public Object h;
    public int i;

    public Og(boolean z) {
        this.f = null;
        this.g = z;
        this.e = this;
        this.d = this;
    }

    @Override // java.util.Map.Entry
    public final boolean equals(Object obj) {
        if (obj instanceof Map.Entry) {
            Map.Entry entry = (Map.Entry) obj;
            Object obj2 = this.f;
            if (obj2 != null ? obj2.equals(entry.getKey()) : entry.getKey() == null) {
                Object obj3 = this.h;
                if (obj3 == null) {
                    if (entry.getValue() == null) {
                        return true;
                    }
                } else if (obj3.equals(entry.getValue())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // java.util.Map.Entry
    public final Object getKey() {
        return this.f;
    }

    @Override // java.util.Map.Entry
    public final Object getValue() {
        return this.h;
    }

    @Override // java.util.Map.Entry
    public final int hashCode() {
        Object obj = this.f;
        int iHashCode = obj == null ? 0 : obj.hashCode();
        Object obj2 = this.h;
        return (obj2 != null ? obj2.hashCode() : 0) ^ iHashCode;
    }

    @Override // java.util.Map.Entry
    public final Object setValue(Object obj) {
        if (obj == null && !this.g) {
            throw new NullPointerException("value == null");
        }
        Object obj2 = this.h;
        this.h = obj;
        return obj2;
    }

    public final String toString() {
        return this.f + "=" + this.h;
    }

    public Og(boolean z, Og og, Object obj, Og og2, Og og3) {
        this.a = og;
        this.f = obj;
        this.g = z;
        this.i = 1;
        this.d = og2;
        this.e = og3;
        og3.d = this;
        og2.e = this;
    }
}
