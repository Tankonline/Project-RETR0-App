package rikka.shizuku;

import java.io.Serializable;

/* JADX INFO: renamed from: rikka.shizuku.rb, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0552rb extends N implements InterfaceC0522qb, Serializable {
    public final Enum[] a;

    public C0552rb(Enum[] enumArr) {
        this.a = enumArr;
    }

    @Override // rikka.shizuku.N
    public final int a() {
        return this.a.length;
    }

    @Override // rikka.shizuku.N, java.util.List, java.util.Collection
    public final boolean contains(Object obj) {
        if (!(obj instanceof Enum)) {
            return false;
        }
        Enum r4 = (Enum) obj;
        int iOrdinal = r4.ordinal();
        Enum[] enumArr = this.a;
        return ((iOrdinal < 0 || iOrdinal >= enumArr.length) ? null : enumArr[iOrdinal]) == r4;
    }

    @Override // java.util.List
    public final Object get(int i) {
        Enum[] enumArr = this.a;
        int length = enumArr.length;
        if (i >= 0 && i < length) {
            return enumArr[i];
        }
        throw new IndexOutOfBoundsException("index: " + i + ", size: " + length);
    }

    @Override // rikka.shizuku.N, java.util.List
    public final int indexOf(Object obj) {
        if (!(obj instanceof Enum)) {
            return -1;
        }
        Enum r4 = (Enum) obj;
        int iOrdinal = r4.ordinal();
        Enum[] enumArr = this.a;
        if (((iOrdinal < 0 || iOrdinal >= enumArr.length) ? null : enumArr[iOrdinal]) == r4) {
            return iOrdinal;
        }
        return -1;
    }

    @Override // rikka.shizuku.N, java.util.List
    public final int lastIndexOf(Object obj) {
        if (obj instanceof Enum) {
            return indexOf((Enum) obj);
        }
        return -1;
    }
}
