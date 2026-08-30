package defpackage;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/* JADX INFO: renamed from: k, reason: case insensitive filesystem */
/* JADX INFO: loaded from: assets/rish_shizuku.dex */
public final class C0010k implements Set {
    public final /* synthetic */ C0013n a;

    public C0010k(C0013n c0013n) {
        this.a = c0013n;
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean add(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean addAll(Collection collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Set, java.util.Collection
    public final void clear() {
        this.a.clear();
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean contains(Object obj) {
        return this.a.b(obj);
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean containsAll(Collection collection) {
        C0013n c0013n = this.a;
        c0013n.getClass();
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            if (!c0013n.b(it.next())) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Set)) {
            return false;
        }
        Set set = (Set) obj;
        try {
            return size() == set.size() && containsAll(set);
        } catch (ClassCastException | NullPointerException unused) {
            return false;
        }
    }

    @Override // java.util.Set, java.util.Collection
    public final int hashCode() {
        C0013n c0013n = this.a;
        int iHashCode = 0;
        for (int i = c0013n.c - 1; i >= 0; i--) {
            Object objH = c0013n.h(i);
            iHashCode += objH == null ? 0 : objH.hashCode();
        }
        return iHashCode;
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean isEmpty() {
        return this.a.isEmpty();
    }

    @Override // java.util.Set, java.util.Collection, java.lang.Iterable
    public final Iterator iterator() {
        return new C0009j(this.a, 0);
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean remove(Object obj) {
        C0013n c0013n = this.a;
        int iF = c0013n.f(obj);
        if (iF < 0) {
            return false;
        }
        c0013n.j(iF);
        return true;
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean removeAll(Collection collection) {
        C0013n c0013n = this.a;
        int i = c0013n.c;
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            c0013n.i(it.next());
        }
        return i != c0013n.c;
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean retainAll(Collection collection) {
        C0013n c0013n = this.a;
        int i = c0013n.c;
        for (int i2 = i - 1; i2 >= 0; i2--) {
            if (!collection.contains(c0013n.h(i2))) {
                c0013n.j(i2);
            }
        }
        return i != c0013n.c;
    }

    @Override // java.util.Set, java.util.Collection
    public final int size() {
        return this.a.c;
    }

    @Override // java.util.Set, java.util.Collection
    public final Object[] toArray() {
        C0013n c0013n = this.a;
        int i = c0013n.c;
        Object[] objArr = new Object[i];
        for (int i2 = 0; i2 < i; i2++) {
            objArr[i2] = c0013n.h(i2);
        }
        return objArr;
    }

    @Override // java.util.Set, java.util.Collection
    public final Object[] toArray(Object[] objArr) {
        C0013n c0013n = this.a;
        int i = c0013n.c;
        if (objArr.length < i) {
            objArr = (Object[]) Array.newInstance(objArr.getClass().getComponentType(), i);
        }
        for (int i2 = 0; i2 < i; i2++) {
            objArr[i2] = c0013n.h(i2);
        }
        if (objArr.length > i) {
            objArr[i] = null;
        }
        return objArr;
    }
}
