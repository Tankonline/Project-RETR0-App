package rikka.shizuku;

import android.view.ViewGroup;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public abstract class Yk {
    public final Zk a = new Zk();
    public boolean b = false;
    public final int c = 1;

    public abstract int a();

    public long b(int i) {
        return -1L;
    }

    public int c(int i) {
        return 0;
    }

    public abstract void d(AbstractC0470ol abstractC0470ol, int i);

    public void e(AbstractC0470ol abstractC0470ol, int i, List list) {
        d(abstractC0470ol, i);
    }

    public abstract AbstractC0470ol f(ViewGroup viewGroup, int i);

    public final void j(boolean z) {
        if (this.a.a()) {
            throw new IllegalStateException("Cannot change whether this adapter has stable IDs while the adapter has registered observers.");
        }
        this.b = z;
    }

    public void g(AbstractC0470ol abstractC0470ol) {
    }

    public void h(AbstractC0470ol abstractC0470ol) {
    }

    public void i(AbstractC0470ol abstractC0470ol) {
    }
}
