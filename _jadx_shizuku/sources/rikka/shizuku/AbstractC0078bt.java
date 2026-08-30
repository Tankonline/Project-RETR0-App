package rikka.shizuku;

import android.view.View;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

/* JADX INFO: renamed from: rikka.shizuku.bt, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0078bt extends K7 {
    public Fd a;
    public int b = 0;

    public AbstractC0078bt() {
    }

    @Override // rikka.shizuku.K7
    public boolean h(CoordinatorLayout coordinatorLayout, View view, int i) {
        t(coordinatorLayout, view, i);
        if (this.a == null) {
            Fd fd = new Fd();
            fd.d = view;
            this.a = fd;
        }
        Fd fd2 = this.a;
        View view2 = (View) fd2.d;
        fd2.a = view2.getTop();
        fd2.b = view2.getLeft();
        this.a.b();
        int i2 = this.b;
        if (i2 == 0) {
            return true;
        }
        Fd fd3 = this.a;
        if (fd3.c != i2) {
            fd3.c = i2;
            fd3.b();
        }
        this.b = 0;
        return true;
    }

    public final int s() {
        Fd fd = this.a;
        if (fd != null) {
            return fd.c;
        }
        return 0;
    }

    public void t(CoordinatorLayout coordinatorLayout, View view, int i) {
        coordinatorLayout.q(view, i);
    }

    public AbstractC0078bt(int i) {
    }
}
