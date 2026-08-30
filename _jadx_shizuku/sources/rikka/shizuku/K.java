package rikka.shizuku;

import android.view.View;
import android.view.ViewGroup;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes.dex */
public class K implements Iterator {
    public final /* synthetic */ int a;
    public int b;
    public final /* synthetic */ Object c;

    public /* synthetic */ K(int i, Object obj) {
        this.a = i;
        this.c = obj;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        switch (this.a) {
            case 0:
                return this.b < ((N) this.c).a();
            default:
                return this.b < ((ViewGroup) this.c).getChildCount();
        }
    }

    @Override // java.util.Iterator
    public final Object next() {
        switch (this.a) {
            case 0:
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                int i = this.b;
                this.b = i + 1;
                return ((N) this.c).get(i);
            default:
                int i2 = this.b;
                this.b = i2 + 1;
                View childAt = ((ViewGroup) this.c).getChildAt(i2);
                if (childAt != null) {
                    return childAt;
                }
                throw new IndexOutOfBoundsException();
        }
    }

    @Override // java.util.Iterator
    public final void remove() {
        switch (this.a) {
            case 0:
                throw new UnsupportedOperationException("Operation is not supported for read-only collection");
            default:
                int i = this.b - 1;
                this.b = i;
                ((ViewGroup) this.c).removeViewAt(i);
                return;
        }
    }
}
