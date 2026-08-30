package rikka.shizuku;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;

/* JADX INFO: renamed from: rikka.shizuku.i4, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0269i4 implements Collection {
    public final /* synthetic */ C0298j4 a;

    public C0269i4(C0298j4 c0298j4) {
        this.a = c0298j4;
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
        return new C0176f4(this.a, 1);
    }

    @Override // java.util.Collection
    public final boolean remove(Object obj) {
        C0298j4 c0298j4 = this.a;
        int iA = c0298j4.a(obj);
        if (iA < 0) {
            return false;
        }
        c0298j4.g(iA);
        return true;
    }

    @Override // java.util.Collection
    public final boolean removeAll(Collection collection) {
        C0298j4 c0298j4 = this.a;
        int i = c0298j4.c;
        int i2 = 0;
        boolean z = false;
        while (i2 < i) {
            if (collection.contains(c0298j4.i(i2))) {
                c0298j4.g(i2);
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
        C0298j4 c0298j4 = this.a;
        int i = c0298j4.c;
        int i2 = 0;
        boolean z = false;
        while (i2 < i) {
            if (!collection.contains(c0298j4.i(i2))) {
                c0298j4.g(i2);
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
        C0298j4 c0298j4 = this.a;
        int i = c0298j4.c;
        Object[] objArr = new Object[i];
        for (int i2 = 0; i2 < i; i2++) {
            objArr[i2] = c0298j4.i(i2);
        }
        return objArr;
    }

    @Override // java.util.Collection
    public final Object[] toArray(Object[] objArr) {
        C0298j4 c0298j4 = this.a;
        int i = c0298j4.c;
        if (objArr.length < i) {
            objArr = (Object[]) Array.newInstance(objArr.getClass().getComponentType(), i);
        }
        for (int i2 = 0; i2 < i; i2++) {
            objArr[i2] = c0298j4.i(i2);
        }
        if (objArr.length > i) {
            objArr[i] = null;
        }
        return objArr;
    }
}
