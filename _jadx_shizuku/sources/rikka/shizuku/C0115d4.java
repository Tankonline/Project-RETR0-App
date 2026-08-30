package rikka.shizuku;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX INFO: renamed from: rikka.shizuku.d4, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0115d4 implements Iterator {
    public final /* synthetic */ int a;
    public final Object[] b;
    public int c;

    public C0115d4(Object[] objArr, int i) {
        this.a = i;
        switch (i) {
            case 1:
                this.c = 0;
                this.b = objArr;
                break;
            default:
                this.b = objArr;
                break;
        }
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        switch (this.a) {
            case 0:
                return this.c < this.b.length;
            default:
                return this.c < this.b.length;
        }
    }

    @Override // java.util.Iterator
    public final Object next() {
        switch (this.a) {
            case 0:
                try {
                    Object[] objArr = this.b;
                    int i = this.c;
                    this.c = i + 1;
                    return objArr[i];
                } catch (ArrayIndexOutOfBoundsException e) {
                    this.c--;
                    throw new NoSuchElementException(e.getMessage());
                }
            default:
                int i2 = this.c;
                Object[] objArr2 = this.b;
                if (i2 != objArr2.length) {
                    this.c = i2 + 1;
                    return objArr2[i2];
                }
                throw new NoSuchElementException("Out of elements: " + this.c);
        }
    }

    @Override // java.util.Iterator
    public final void remove() {
        switch (this.a) {
            case 0:
                throw new UnsupportedOperationException("Operation is not supported for read-only collection");
            default:
                throw new UnsupportedOperationException("Cannot remove element from an Array.");
        }
    }
}
