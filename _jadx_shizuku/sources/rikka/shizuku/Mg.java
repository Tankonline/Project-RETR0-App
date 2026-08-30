package rikka.shizuku;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes.dex */
public final class Mg implements Iterator {
    public Og a;
    public Og b = null;
    public int c;
    public final /* synthetic */ Pg d;
    public final /* synthetic */ int e;

    public Mg(Pg pg, int i) {
        this.e = i;
        this.d = pg;
        this.a = pg.f.d;
        this.c = pg.e;
    }

    public final Object a() {
        return b();
    }

    public final Og b() {
        Og og = this.a;
        Pg pg = this.d;
        if (og == pg.f) {
            throw new NoSuchElementException();
        }
        if (pg.e != this.c) {
            throw new ConcurrentModificationException();
        }
        this.a = og.d;
        this.b = og;
        return og;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.a != this.d.f;
    }

    @Override // java.util.Iterator
    public Object next() {
        switch (this.e) {
            case 1:
                return b().f;
            default:
                return a();
        }
    }

    @Override // java.util.Iterator
    public final void remove() {
        Og og = this.b;
        if (og == null) {
            throw new IllegalStateException();
        }
        Pg pg = this.d;
        pg.c(og, true);
        this.b = null;
        this.c = pg.e;
    }
}
