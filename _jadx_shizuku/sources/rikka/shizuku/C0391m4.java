package rikka.shizuku;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* JADX INFO: renamed from: rikka.shizuku.m4, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0391m4 implements Collection, Set {
    public int[] a = AbstractC0744xh.d;
    public Object[] b = AbstractC0744xh.e;
    public int c;

    public final void a(int i) {
        int i2 = this.c;
        Object[] objArr = this.b;
        Object obj = objArr[i];
        if (i2 <= 1) {
            clear();
            return;
        }
        int i3 = i2 - 1;
        int[] iArr = this.a;
        if (iArr.length <= 8 || i2 >= iArr.length / 3) {
            if (i < i3) {
                int i4 = i + 1;
                AbstractC0484p4.e0(i, i4, i2, iArr, iArr);
                Object[] objArr2 = this.b;
                AbstractC0484p4.f0(objArr2, objArr2, i, i4, i2);
            }
            this.b[i3] = null;
        } else {
            int i5 = i2 > 8 ? i2 + (i2 >> 1) : 8;
            int[] iArr2 = new int[i5];
            this.a = iArr2;
            this.b = new Object[i5];
            if (i > 0) {
                System.arraycopy(iArr, 0, iArr2, 0, i);
                AbstractC0484p4.h0(objArr, this.b, 0, i, 6);
            }
            if (i < i3) {
                int i6 = i + 1;
                AbstractC0484p4.e0(i, i6, i2, iArr, this.a);
                AbstractC0484p4.f0(objArr, this.b, i, i6, i2);
            }
        }
        if (i2 != this.c) {
            throw new ConcurrentModificationException();
        }
        this.c = i3;
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean add(Object obj) {
        int i;
        int iV;
        int i2 = this.c;
        if (obj == null) {
            iV = AbstractC0599sr.v(this, null, 0);
            i = 0;
        } else {
            int iHashCode = obj.hashCode();
            i = iHashCode;
            iV = AbstractC0599sr.v(this, obj, iHashCode);
        }
        if (iV >= 0) {
            return false;
        }
        int i3 = ~iV;
        int[] iArr = this.a;
        if (i2 >= iArr.length) {
            int i4 = 8;
            if (i2 >= 8) {
                i4 = (i2 >> 1) + i2;
            } else if (i2 < 4) {
                i4 = 4;
            }
            Object[] objArr = this.b;
            int[] iArr2 = new int[i4];
            this.a = iArr2;
            this.b = new Object[i4];
            if (i2 != this.c) {
                throw new ConcurrentModificationException();
            }
            if (iArr2.length != 0) {
                System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
                AbstractC0484p4.h0(objArr, this.b, 0, objArr.length, 6);
            }
        }
        if (i3 < i2) {
            int[] iArr3 = this.a;
            int i5 = i3 + 1;
            AbstractC0484p4.e0(i5, i3, i2, iArr3, iArr3);
            Object[] objArr2 = this.b;
            AbstractC0484p4.f0(objArr2, objArr2, i5, i3, i2);
        }
        int i6 = this.c;
        if (i2 == i6) {
            int[] iArr4 = this.a;
            if (i3 < iArr4.length) {
                iArr4[i3] = i;
                this.b[i3] = obj;
                this.c = i6 + 1;
                return true;
            }
        }
        throw new ConcurrentModificationException();
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean addAll(Collection collection) {
        int size = collection.size() + this.c;
        int i = this.c;
        int[] iArr = this.a;
        boolean zAdd = false;
        if (iArr.length < size) {
            Object[] objArr = this.b;
            int[] iArr2 = new int[size];
            this.a = iArr2;
            this.b = new Object[size];
            if (i > 0) {
                System.arraycopy(iArr, 0, iArr2, 0, i);
                AbstractC0484p4.h0(objArr, this.b, 0, this.c, 6);
            }
        }
        if (this.c != i) {
            throw new ConcurrentModificationException();
        }
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            zAdd |= add(it.next());
        }
        return zAdd;
    }

    @Override // java.util.Collection, java.util.Set
    public final void clear() {
        if (this.c != 0) {
            this.a = AbstractC0744xh.d;
            this.b = AbstractC0744xh.e;
            this.c = 0;
        }
        if (this.c != 0) {
            throw new ConcurrentModificationException();
        }
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        return (obj == null ? AbstractC0599sr.v(this, null, 0) : AbstractC0599sr.v(this, obj, obj.hashCode())) >= 0;
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean containsAll(Collection collection) {
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            if (!contains(it.next())) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Set) || this.c != ((Set) obj).size()) {
            return false;
        }
        try {
            int i = this.c;
            for (int i2 = 0; i2 < i; i2++) {
                if (!((Set) obj).contains(this.b[i2])) {
                    return false;
                }
            }
            return true;
        } catch (ClassCastException | NullPointerException unused) {
            return false;
        }
    }

    @Override // java.util.Collection, java.util.Set
    public final int hashCode() {
        int[] iArr = this.a;
        int i = this.c;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            i2 += iArr[i3];
        }
        return i2;
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean isEmpty() {
        return this.c <= 0;
    }

    @Override // java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator iterator() {
        return new C0176f4(this);
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean remove(Object obj) {
        int iV = obj == null ? AbstractC0599sr.v(this, null, 0) : AbstractC0599sr.v(this, obj, obj.hashCode());
        if (iV < 0) {
            return false;
        }
        a(iV);
        return true;
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean removeAll(Collection collection) {
        Iterator it = collection.iterator();
        boolean zRemove = false;
        while (it.hasNext()) {
            zRemove |= remove(it.next());
        }
        return zRemove;
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean retainAll(Collection collection) {
        boolean zContains;
        int i = this.c - 1;
        boolean z = false;
        while (true) {
            int iIndexOf = -1;
            if (-1 >= i) {
                return z;
            }
            Object obj = this.b[i];
            if (collection != null) {
                zContains = collection.contains(obj);
            } else {
                if (collection instanceof List) {
                    iIndexOf = ((List) collection).indexOf(obj);
                } else {
                    int i2 = 0;
                    for (Object obj2 : collection) {
                        if (i2 < 0) {
                            throw new ArithmeticException("Index overflow has happened.");
                        }
                        if (AbstractC0046as.c(obj, obj2)) {
                            iIndexOf = i2;
                            break;
                        }
                        i2++;
                    }
                }
                zContains = iIndexOf >= 0;
            }
            if (!zContains) {
                a(i);
                z = true;
            }
            i--;
        }
    }

    @Override // java.util.Collection, java.util.Set
    public final int size() {
        return this.c;
    }

    @Override // java.util.Collection, java.util.Set
    public final Object[] toArray() {
        Object[] objArr = this.b;
        int i = this.c;
        int length = objArr.length;
        if (i <= length) {
            return Arrays.copyOfRange(objArr, 0, i);
        }
        throw new IndexOutOfBoundsException("toIndex (" + i + ") is greater than size (" + length + ").");
    }

    public final String toString() {
        if (isEmpty()) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(this.c * 14);
        sb.append('{');
        int i = this.c;
        for (int i2 = 0; i2 < i; i2++) {
            if (i2 > 0) {
                sb.append(", ");
            }
            Object obj = this.b[i2];
            if (obj != this) {
                sb.append(obj);
            } else {
                sb.append("(this Set)");
            }
        }
        sb.append('}');
        return sb.toString();
    }

    @Override // java.util.Collection, java.util.Set
    public final Object[] toArray(Object[] objArr) {
        int i = this.c;
        if (objArr.length < i) {
            objArr = (Object[]) Array.newInstance(objArr.getClass().getComponentType(), i);
        } else if (objArr.length > i) {
            objArr[i] = null;
        }
        AbstractC0484p4.f0(this.b, objArr, 0, 0, this.c);
        return objArr;
    }
}
