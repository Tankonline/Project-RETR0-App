package rikka.shizuku;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX INFO: renamed from: rikka.shizuku.f4, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0176f4 implements Iterator {
    public int a;
    public int b;
    public boolean c;
    public final /* synthetic */ int d;
    public final /* synthetic */ Object e;

    public C0176f4(int i) {
        this.a = i;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.b < this.a;
    }

    @Override // java.util.Iterator
    public final Object next() {
        Object objF;
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        int i = this.b;
        switch (this.d) {
            case 0:
                objF = ((C0298j4) this.e).f(i);
                break;
            case 1:
                objF = ((C0298j4) this.e).i(i);
                break;
            default:
                objF = ((C0391m4) this.e).b[i];
                break;
        }
        this.b++;
        this.c = true;
        return objF;
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
                ((C0298j4) this.e).g(i);
                break;
            case 1:
                ((C0298j4) this.e).g(i);
                break;
            default:
                ((C0391m4) this.e).a(i);
                break;
        }
        this.a--;
        this.c = false;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public C0176f4(C0391m4 c0391m4) {
        this(c0391m4.c);
        this.d = 2;
        this.e = c0391m4;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public C0176f4(C0298j4 c0298j4, int i) {
        this(c0298j4.c);
        this.d = i;
        switch (i) {
            case 1:
                this.e = c0298j4;
                this(c0298j4.c);
                break;
            default:
                this.e = c0298j4;
                break;
        }
    }
}
