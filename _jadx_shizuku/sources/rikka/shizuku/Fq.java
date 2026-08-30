package rikka.shizuku;

import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes.dex */
public final class Fq implements Iterator {
    public final ArrayList a = new ArrayList();
    public Iterator b;

    public Fq(K k) {
        this.b = k;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.b.hasNext();
    }

    @Override // java.util.Iterator
    public final Object next() {
        Object next = this.b.next();
        View view = (View) next;
        ViewGroup viewGroup = view instanceof ViewGroup ? (ViewGroup) view : null;
        K k = viewGroup != null ? new K(1, viewGroup) : null;
        ArrayList arrayList = this.a;
        if (k != null && k.hasNext()) {
            arrayList.add(this.b);
            this.b = k;
            return next;
        }
        while (!this.b.hasNext() && !arrayList.isEmpty()) {
            if (arrayList.isEmpty()) {
                throw new NoSuchElementException("List is empty.");
            }
            this.b = (Iterator) arrayList.get(arrayList.size() - 1);
            if (arrayList.isEmpty()) {
                throw new NoSuchElementException("List is empty.");
            }
            arrayList.remove(arrayList.size() - 1);
        }
        return next;
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}
