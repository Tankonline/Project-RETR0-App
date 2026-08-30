package defpackage;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;

/* JADX INFO: renamed from: m, reason: case insensitive filesystem */
/* JADX INFO: loaded from: assets/rish_shizuku.dex */
public final class C0012m implements Collection {
    public final /* synthetic */ C0013n a;

    public C0012m(C0013n c0013n) {
        this.a = c0013n;
    }

    @Override // java.util.Collection
    public final boolean add(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Collection
    public final boolean addAll(Collection collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Collection
    public final void clear() {
        this.a.clear();
    }

    @Override // java.util.Collection
    public final boolean contains(Object obj) {
        return this.a.a(obj) >= 0;
    }

    @Override // java.util.Collection
    public final boolean containsAll(Collection collection) {
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            if (!contains(it.next())) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.Collection
    public final boolean isEmpty() {
        return this.a.isEmpty();
    }

    @Override // java.util.Collection, java.lang.Iterable
    public final Iterator iterator() {
        return new C0009j(this.a, 1);
    }

    @Override // java.util.Collection
    public final boolean remove(Object obj) {
        C0013n c0013n = this.a;
        int iA = c0013n.a(obj);
        if (iA < 0) {
            return false;
        }
        c0013n.j(iA);
        return true;
    }

    @Override // java.util.Collection
    public final boolean removeAll(Collection collection) {
        C0013n c0013n = this.a;
        int i = c0013n.c;
        int i2 = 0;
        boolean z = false;
        while (i2 < i) {
            if (collection.contains(c0013n.l(i2))) {
                c0013n.j(i2);
                i2--;
                i--;
                z = true;
            }
            i2++;
        }
        return z;
    }

    @Override // java.util.Collection
    public final boolean retainAll(Collection collection) {
        C0013n c0013n = this.a;
        int i = c0013n.c;
        int i2 = 0;
        boolean z = false;
        while (i2 < i) {
            if (!collection.contains(c0013n.l(i2))) {
                c0013n.j(i2);
                i2--;
                i--;
                z = true;
            }
            i2++;
        }
        return z;
    }

    @Override // java.util.Collection
    public final int size() {
        return this.a.c;
    }

    @Override // java.util.Collection
    public final Object[] toArray() {
        C0013n c0013n = this.a;
        int i = c0013n.c;
        Object[] objArr = new Object[i];
        for (int i2 = 0; i2 < i; i2++) {
            objArr[i2] = c0013n.l(i2);
        }
        return objArr;
    }

    @Override // java.util.Collection
    public final Object[] toArray(Object[] objArr) {
        C0013n c0013n = this.a;
        int i = c0013n.c;
        if (objArr.length < i) {
            objArr = (Object[]) Array.newInstance(objArr.getClass().getComponentType(), i);
        }
        for (int i2 = 0; i2 < i; i2++) {
            objArr[i2] = c0013n.l(i2);
        }
        if (objArr.length > i) {
            objArr[i] = null;
        }
        return objArr;
    }
}
