package defpackage;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX INFO: renamed from: j, reason: case insensitive filesystem */
/* JADX INFO: loaded from: assets/rish_shizuku.dex */
public final class C0009j implements Iterator {
    public int a;
    public int b;
    public boolean c;
    public final /* synthetic */ int d;
    public final /* synthetic */ C0013n e;

    public C0009j(int i) {
        this.a = i;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.b < this.a;
    }

    @Override // java.util.Iterator
    public final Object next() {
        Object objH;
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        int i = this.b;
        switch (this.d) {
            case 0:
                objH = this.e.h(i);
                break;
            default:
                objH = this.e.l(i);
                break;
        }
        this.b++;
        this.c = true;
        return objH;
    }

    @Override // java.util.Iterator
    public final void remove() {
        if (!this.c) {
            throw new IllegalStateException("Call next() before removing an element.");
        }
        int i = this.b - 1;
        this.b = i;
        switch (this.d) {
            case 0:
                this.e.j(i);
                break;
            default:
                this.e.j(i);
                break;
        }
        this.a--;
        this.c = false;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public C0009j(C0013n c0013n, int i) {
        this(c0013n.c);
        this.d = i;
        switch (i) {
            case 1:
                this.e = c0013n;
                this(c0013n.c);
                break;
            default:
                this.e = c0013n;
                break;
        }
    }
}
