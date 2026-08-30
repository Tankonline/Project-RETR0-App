package rikka.shizuku;

import java.lang.reflect.Array;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/* JADX INFO: renamed from: rikka.shizuku.c4, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0084c4 extends AbstractList implements List {
    public static final Object[] d = new Object[0];
    public int a;
    public Object[] b = d;
    public int c;

    public final void a(int i, Collection collection) {
        Iterator it = collection.iterator();
        int length = this.b.length;
        while (i < length && it.hasNext()) {
            this.b[i] = it.next();
            i++;
        }
        int i2 = this.a;
        for (int i3 = 0; i3 < i2 && it.hasNext(); i3++) {
            this.b[i3] = it.next();
        }
        this.c = collection.size() + this.c;
    }

    @Override // java.util.AbstractList, java.util.List
    public final void add(int i, Object obj) {
        int i2 = this.c;
        if (i < 0 || i > i2) {
            throw new IndexOutOfBoundsException("index: " + i + ", size: " + i2);
        }
        if (i == i2) {
            addLast(obj);
            return;
        }
        if (i == 0) {
            addFirst(obj);
            return;
        }
        g();
        b(this.c + 1);
        int iF = f(this.a + i);
        int i3 = this.c;
        if (i < ((i3 + 1) >> 1)) {
            int length = iF == 0 ? this.b.length - 1 : iF - 1;
            int i4 = this.a;
            int length2 = i4 == 0 ? this.b.length - 1 : i4 - 1;
            if (length >= i4) {
                Object[] objArr = this.b;
                objArr[length2] = objArr[i4];
                AbstractC0484p4.f0(objArr, objArr, i4, i4 + 1, length + 1);
            } else {
                Object[] objArr2 = this.b;
                AbstractC0484p4.f0(objArr2, objArr2, i4 - 1, i4, objArr2.length);
                Object[] objArr3 = this.b;
                objArr3[objArr3.length - 1] = objArr3[0];
                AbstractC0484p4.f0(objArr3, objArr3, 0, 1, length + 1);
            }
            this.b[length] = obj;
            this.a = length2;
        } else {
            int iF2 = f(this.a + i3);
            if (iF < iF2) {
                Object[] objArr4 = this.b;
                AbstractC0484p4.f0(objArr4, objArr4, iF + 1, iF, iF2);
            } else {
                Object[] objArr5 = this.b;
                AbstractC0484p4.f0(objArr5, objArr5, 1, 0, iF2);
                Object[] objArr6 = this.b;
                objArr6[0] = objArr6[objArr6.length - 1];
                AbstractC0484p4.f0(objArr6, objArr6, iF + 1, iF, objArr6.length - 1);
            }
            this.b[iF] = obj;
        }
        this.c++;
    }

    @Override // java.util.AbstractList, java.util.List
    public final boolean addAll(int i, Collection collection) {
        int i2 = this.c;
        if (i < 0 || i > i2) {
            throw new IndexOutOfBoundsException("index: " + i + ", size: " + i2);
        }
        if (collection.isEmpty()) {
            return false;
        }
        if (i == this.c) {
            return addAll(collection);
        }
        g();
        b(collection.size() + this.c);
        int iF = f(this.a + this.c);
        int iF2 = f(this.a + i);
        int size = collection.size();
        if (i >= ((this.c + 1) >> 1)) {
            int i3 = iF2 + size;
            if (iF2 < iF) {
                int i4 = size + iF;
                Object[] objArr = this.b;
                if (i4 <= objArr.length) {
                    AbstractC0484p4.f0(objArr, objArr, i3, iF2, iF);
                } else if (i3 >= objArr.length) {
                    AbstractC0484p4.f0(objArr, objArr, i3 - objArr.length, iF2, iF);
                } else {
                    int length = iF - (i4 - objArr.length);
                    AbstractC0484p4.f0(objArr, objArr, 0, length, iF);
                    Object[] objArr2 = this.b;
                    AbstractC0484p4.f0(objArr2, objArr2, i3, iF2, length);
                }
            } else {
                Object[] objArr3 = this.b;
                AbstractC0484p4.f0(objArr3, objArr3, size, 0, iF);
                Object[] objArr4 = this.b;
                if (i3 >= objArr4.length) {
                    AbstractC0484p4.f0(objArr4, objArr4, i3 - objArr4.length, iF2, objArr4.length);
                } else {
                    AbstractC0484p4.f0(objArr4, objArr4, 0, objArr4.length - size, objArr4.length);
                    Object[] objArr5 = this.b;
                    AbstractC0484p4.f0(objArr5, objArr5, i3, iF2, objArr5.length - size);
                }
            }
            a(iF2, collection);
            return true;
        }
        int i5 = this.a;
        int length2 = i5 - size;
        if (iF2 < i5) {
            Object[] objArr6 = this.b;
            AbstractC0484p4.f0(objArr6, objArr6, length2, i5, objArr6.length);
            if (size >= iF2) {
                Object[] objArr7 = this.b;
                AbstractC0484p4.f0(objArr7, objArr7, objArr7.length - size, 0, iF2);
            } else {
                Object[] objArr8 = this.b;
                AbstractC0484p4.f0(objArr8, objArr8, objArr8.length - size, 0, size);
                Object[] objArr9 = this.b;
                AbstractC0484p4.f0(objArr9, objArr9, 0, size, iF2);
            }
        } else if (length2 >= 0) {
            Object[] objArr10 = this.b;
            AbstractC0484p4.f0(objArr10, objArr10, length2, i5, iF2);
        } else {
            Object[] objArr11 = this.b;
            length2 += objArr11.length;
            int i6 = iF2 - i5;
            int length3 = objArr11.length - length2;
            if (length3 >= i6) {
                AbstractC0484p4.f0(objArr11, objArr11, length2, i5, iF2);
            } else {
                AbstractC0484p4.f0(objArr11, objArr11, length2, i5, i5 + length3);
                Object[] objArr12 = this.b;
                AbstractC0484p4.f0(objArr12, objArr12, 0, this.a + length3, iF2);
            }
        }
        this.a = length2;
        a(d(iF2 - size), collection);
        return true;
    }

    public final void addFirst(Object obj) {
        g();
        b(this.c + 1);
        int length = this.a;
        if (length == 0) {
            length = this.b.length;
        }
        int i = length - 1;
        this.a = i;
        this.b[i] = obj;
        this.c++;
    }

    public final void addLast(Object obj) {
        g();
        b(this.c + 1);
        this.b[f(this.a + this.c)] = obj;
        this.c++;
    }

    public final void b(int i) {
        if (i < 0) {
            throw new IllegalStateException("Deque is too big.");
        }
        Object[] objArr = this.b;
        if (i <= objArr.length) {
            return;
        }
        if (objArr == d) {
            if (i < 10) {
                i = 10;
            }
            this.b = new Object[i];
            return;
        }
        int length = objArr.length;
        int i2 = length + (length >> 1);
        if (i2 - i < 0) {
            i2 = i;
        }
        if (i2 - 2147483639 > 0) {
            i2 = i > 2147483639 ? Integer.MAX_VALUE : 2147483639;
        }
        Object[] objArr2 = new Object[i2];
        AbstractC0484p4.f0(objArr, objArr2, 0, this.a, objArr.length);
        Object[] objArr3 = this.b;
        int length2 = objArr3.length;
        int i3 = this.a;
        AbstractC0484p4.f0(objArr3, objArr2, length2 - i3, 0, i3);
        this.a = 0;
        this.b = objArr2;
    }

    public final int c(int i) {
        if (i == this.b.length - 1) {
            return 0;
        }
        return i + 1;
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final void clear() {
        if (!isEmpty()) {
            g();
            e(this.a, f(this.a + this.c));
        }
        this.a = 0;
        this.c = 0;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean contains(Object obj) {
        return indexOf(obj) != -1;
    }

    public final int d(int i) {
        return i < 0 ? i + this.b.length : i;
    }

    public final void e(int i, int i2) {
        if (i < i2) {
            Arrays.fill(this.b, i, i2, (Object) null);
            return;
        }
        Object[] objArr = this.b;
        Arrays.fill(objArr, i, objArr.length, (Object) null);
        Arrays.fill(this.b, 0, i2, (Object) null);
    }

    public final int f(int i) {
        Object[] objArr = this.b;
        return i >= objArr.length ? i - objArr.length : i;
    }

    public final void g() {
        ((AbstractList) this).modCount++;
    }

    @Override // java.util.AbstractList, java.util.List
    public final Object get(int i) {
        int i2 = this.c;
        if (i >= 0 && i < i2) {
            return this.b[f(this.a + i)];
        }
        throw new IndexOutOfBoundsException("index: " + i + ", size: " + i2);
    }

    @Override // java.util.AbstractList, java.util.List
    public final int indexOf(Object obj) {
        int i;
        int iF = f(this.a + this.c);
        int length = this.a;
        if (length < iF) {
            while (length < iF) {
                if (AbstractC0046as.c(obj, this.b[length])) {
                    i = this.a;
                } else {
                    length++;
                }
            }
            return -1;
        }
        if (length < iF) {
            return -1;
        }
        int length2 = this.b.length;
        while (length < length2) {
            if (AbstractC0046as.c(obj, this.b[length])) {
                i = this.a;
            } else {
                length++;
            }
        }
        for (int i2 = 0; i2 < iF; i2++) {
            if (AbstractC0046as.c(obj, this.b[i2])) {
                length = i2 + this.b.length;
                i = this.a;
            }
        }
        return -1;
        return length - i;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean isEmpty() {
        return this.c == 0;
    }

    @Override // java.util.AbstractList, java.util.List
    public final int lastIndexOf(Object obj) {
        int length;
        int i;
        int iF = f(this.a + this.c);
        int i2 = this.a;
        if (i2 < iF) {
            length = iF - 1;
            if (i2 <= length) {
                while (!AbstractC0046as.c(obj, this.b[length])) {
                    if (length != i2) {
                        length--;
                    }
                }
                i = this.a;
                return length - i;
            }
            return -1;
        }
        if (i2 > iF) {
            for (int i3 = iF - 1; -1 < i3; i3--) {
                if (AbstractC0046as.c(obj, this.b[i3])) {
                    length = i3 + this.b.length;
                    i = this.a;
                    return length - i;
                }
            }
            length = this.b.length - 1;
            int i4 = this.a;
            if (i4 <= length) {
                while (!AbstractC0046as.c(obj, this.b[length])) {
                    if (length != i4) {
                        length--;
                    }
                }
                i = this.a;
                return length - i;
            }
        }
        return -1;
    }

    @Override // java.util.AbstractList, java.util.List
    public final Object remove(int i) {
        int i2 = this.c;
        if (i < 0 || i >= i2) {
            throw new IndexOutOfBoundsException("index: " + i + ", size: " + i2);
        }
        if (i == size() - 1) {
            return removeLast();
        }
        if (i == 0) {
            return removeFirst();
        }
        g();
        int iF = f(this.a + i);
        Object[] objArr = this.b;
        Object obj = objArr[iF];
        if (i < (this.c >> 1)) {
            int i3 = this.a;
            if (iF >= i3) {
                AbstractC0484p4.f0(objArr, objArr, i3 + 1, i3, iF);
            } else {
                AbstractC0484p4.f0(objArr, objArr, 1, 0, iF);
                Object[] objArr2 = this.b;
                objArr2[0] = objArr2[objArr2.length - 1];
                int i4 = this.a;
                AbstractC0484p4.f0(objArr2, objArr2, i4 + 1, i4, objArr2.length - 1);
            }
            Object[] objArr3 = this.b;
            int i5 = this.a;
            objArr3[i5] = null;
            this.a = c(i5);
        } else {
            int iF2 = f((size() - 1) + this.a);
            if (iF <= iF2) {
                Object[] objArr4 = this.b;
                AbstractC0484p4.f0(objArr4, objArr4, iF, iF + 1, iF2 + 1);
            } else {
                Object[] objArr5 = this.b;
                AbstractC0484p4.f0(objArr5, objArr5, iF, iF + 1, objArr5.length);
                Object[] objArr6 = this.b;
                objArr6[objArr6.length - 1] = objArr6[0];
                AbstractC0484p4.f0(objArr6, objArr6, 0, 1, iF2 + 1);
            }
            this.b[iF2] = null;
        }
        this.c--;
        return obj;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean removeAll(Collection collection) {
        int iF;
        boolean z = false;
        z = false;
        z = false;
        if (!isEmpty() && this.b.length != 0) {
            int iF2 = f(this.a + this.c);
            int i = this.a;
            if (i < iF2) {
                iF = i;
                while (i < iF2) {
                    Object obj = this.b[i];
                    if (collection.contains(obj)) {
                        z = true;
                    } else {
                        this.b[iF] = obj;
                        iF++;
                    }
                    i++;
                }
                Arrays.fill(this.b, iF, iF2, (Object) null);
            } else {
                int length = this.b.length;
                boolean z2 = false;
                int i2 = i;
                while (i < length) {
                    Object[] objArr = this.b;
                    Object obj2 = objArr[i];
                    objArr[i] = null;
                    if (collection.contains(obj2)) {
                        z2 = true;
                    } else {
                        this.b[i2] = obj2;
                        i2++;
                    }
                    i++;
                }
                iF = f(i2);
                for (int i3 = 0; i3 < iF2; i3++) {
                    Object[] objArr2 = this.b;
                    Object obj3 = objArr2[i3];
                    objArr2[i3] = null;
                    if (collection.contains(obj3)) {
                        z2 = true;
                    } else {
                        this.b[iF] = obj3;
                        iF = c(iF);
                    }
                }
                z = z2;
            }
            if (z) {
                g();
                this.c = d(iF - this.a);
            }
        }
        return z;
    }

    public final Object removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("ArrayDeque is empty.");
        }
        g();
        Object[] objArr = this.b;
        int i = this.a;
        Object obj = objArr[i];
        objArr[i] = null;
        this.a = c(i);
        this.c--;
        return obj;
    }

    public final Object removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("ArrayDeque is empty.");
        }
        g();
        int iF = f((size() - 1) + this.a);
        Object[] objArr = this.b;
        Object obj = objArr[iF];
        objArr[iF] = null;
        this.c--;
        return obj;
    }

    @Override // java.util.AbstractList
    public final void removeRange(int i, int i2) {
        X8.o(i, i2, this.c);
        int i3 = i2 - i;
        if (i3 == 0) {
            return;
        }
        if (i3 == this.c) {
            clear();
            return;
        }
        if (i3 == 1) {
            remove(i);
            return;
        }
        g();
        if (i < this.c - i2) {
            int iF = f((i - 1) + this.a);
            int iF2 = f((i2 - 1) + this.a);
            while (i > 0) {
                int i4 = iF + 1;
                int iMin = Math.min(i, Math.min(i4, iF2 + 1));
                Object[] objArr = this.b;
                int i5 = iF2 - iMin;
                int i6 = iF - iMin;
                AbstractC0484p4.f0(objArr, objArr, i5 + 1, i6 + 1, i4);
                iF = d(i6);
                iF2 = d(i5);
                i -= iMin;
            }
            int iF3 = f(this.a + i3);
            e(this.a, iF3);
            this.a = iF3;
        } else {
            int iF4 = f(this.a + i2);
            int iF5 = f(this.a + i);
            int i7 = this.c;
            while (true) {
                i7 -= i2;
                if (i7 <= 0) {
                    break;
                }
                Object[] objArr2 = this.b;
                i2 = Math.min(i7, Math.min(objArr2.length - iF4, objArr2.length - iF5));
                Object[] objArr3 = this.b;
                int i8 = iF4 + i2;
                AbstractC0484p4.f0(objArr3, objArr3, iF5, iF4, i8);
                iF4 = f(i8);
                iF5 = f(iF5 + i2);
            }
            int iF6 = f(this.a + this.c);
            e(d(iF6 - i3), iF6);
        }
        this.c -= i3;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean retainAll(Collection collection) {
        int iF;
        boolean z = false;
        z = false;
        z = false;
        if (!isEmpty() && this.b.length != 0) {
            int iF2 = f(this.a + this.c);
            int i = this.a;
            if (i < iF2) {
                iF = i;
                while (i < iF2) {
                    Object obj = this.b[i];
                    if (collection.contains(obj)) {
                        this.b[iF] = obj;
                        iF++;
                    } else {
                        z = true;
                    }
                    i++;
                }
                Arrays.fill(this.b, iF, iF2, (Object) null);
            } else {
                int length = this.b.length;
                boolean z2 = false;
                int i2 = i;
                while (i < length) {
                    Object[] objArr = this.b;
                    Object obj2 = objArr[i];
                    objArr[i] = null;
                    if (collection.contains(obj2)) {
                        this.b[i2] = obj2;
                        i2++;
                    } else {
                        z2 = true;
                    }
                    i++;
                }
                iF = f(i2);
                for (int i3 = 0; i3 < iF2; i3++) {
                    Object[] objArr2 = this.b;
                    Object obj3 = objArr2[i3];
                    objArr2[i3] = null;
                    if (collection.contains(obj3)) {
                        this.b[iF] = obj3;
                        iF = c(iF);
                    } else {
                        z2 = true;
                    }
                }
                z = z2;
            }
            if (z) {
                g();
                this.c = d(iF - this.a);
            }
        }
        return z;
    }

    @Override // java.util.AbstractList, java.util.List
    public final Object set(int i, Object obj) {
        int i2 = this.c;
        if (i < 0 || i >= i2) {
            throw new IndexOutOfBoundsException("index: " + i + ", size: " + i2);
        }
        int iF = f(this.a + i);
        Object[] objArr = this.b;
        Object obj2 = objArr[iF];
        objArr[iF] = obj;
        return obj2;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.c;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final Object[] toArray() {
        return toArray(new Object[this.c]);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final Object[] toArray(Object[] objArr) {
        int length = objArr.length;
        int i = this.c;
        if (length < i) {
            objArr = (Object[]) Array.newInstance(objArr.getClass().getComponentType(), i);
        }
        int iF = f(this.a + this.c);
        int i2 = this.a;
        if (i2 < iF) {
            AbstractC0484p4.h0(this.b, objArr, i2, iF, 2);
        } else if (!isEmpty()) {
            Object[] objArr2 = this.b;
            AbstractC0484p4.f0(objArr2, objArr, 0, this.a, objArr2.length);
            Object[] objArr3 = this.b;
            AbstractC0484p4.f0(objArr3, objArr, objArr3.length - this.a, 0, iF);
        }
        int i3 = this.c;
        if (i3 < objArr.length) {
            objArr[i3] = null;
        }
        return objArr;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean remove(Object obj) {
        int iIndexOf = indexOf(obj);
        if (iIndexOf == -1) {
            return false;
        }
        remove(iIndexOf);
        return true;
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean add(Object obj) {
        addLast(obj);
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean addAll(Collection collection) {
        if (collection.isEmpty()) {
            return false;
        }
        g();
        b(collection.size() + this.c);
        a(f(this.a + this.c), collection);
        return true;
    }
}
