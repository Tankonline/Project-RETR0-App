package rikka.shizuku;

import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class W5 {
    public final Xk a;
    public View e;
    public int d = 0;
    public final V5 b = new V5();
    public final ArrayList c = new ArrayList();

    public W5(Xk xk) {
        this.a = xk;
    }

    public final void a(View view, int i, boolean z) {
        RecyclerView recyclerView = this.a.a;
        int childCount = i < 0 ? recyclerView.getChildCount() : f(i);
        this.b.e(childCount, z);
        if (z) {
            i(view);
        }
        recyclerView.addView(view, childCount);
        AbstractC0470ol abstractC0470olK = RecyclerView.K(view);
        Yk yk = recyclerView.m;
        if (yk == null || abstractC0470olK == null) {
            return;
        }
        yk.g(abstractC0470olK);
    }

    public final void b(View view, int i, ViewGroup.LayoutParams layoutParams, boolean z) {
        RecyclerView recyclerView = this.a.a;
        int childCount = i < 0 ? recyclerView.getChildCount() : f(i);
        this.b.e(childCount, z);
        if (z) {
            i(view);
        }
        AbstractC0470ol abstractC0470olK = RecyclerView.K(view);
        if (abstractC0470olK != null) {
            if (!abstractC0470olK.j() && !abstractC0470olK.o()) {
                throw new IllegalArgumentException("Called attach on a child which is not detached: " + abstractC0470olK + recyclerView.z());
            }
            abstractC0470olK.j &= -257;
        }
        recyclerView.attachViewToParent(view, childCount, layoutParams);
    }

    public final void c(int i) {
        int iF = f(i);
        this.b.f(iF);
        RecyclerView recyclerView = this.a.a;
        View childAt = recyclerView.getChildAt(iF);
        if (childAt != null) {
            AbstractC0470ol abstractC0470olK = RecyclerView.K(childAt);
            if (abstractC0470olK != null) {
                if (abstractC0470olK.j() && !abstractC0470olK.o()) {
                    throw new IllegalArgumentException("called detach on an already detached child " + abstractC0470olK + recyclerView.z());
                }
                abstractC0470olK.a(256);
            }
        } else {
            int[] iArr = RecyclerView.z0;
        }
        recyclerView.detachViewFromParent(iF);
    }

    public final View d(int i) {
        return this.a.a.getChildAt(f(i));
    }

    public final int e() {
        return this.a.a.getChildCount() - this.c.size();
    }

    public final int f(int i) {
        if (i < 0) {
            return -1;
        }
        int childCount = this.a.a.getChildCount();
        int i2 = i;
        while (i2 < childCount) {
            V5 v5 = this.b;
            int iB = i - (i2 - v5.b(i2));
            if (iB == 0) {
                while (v5.d(i2)) {
                    i2++;
                }
                return i2;
            }
            i2 += iB;
        }
        return -1;
    }

    public final View g(int i) {
        return this.a.a.getChildAt(i);
    }

    public final int h() {
        return this.a.a.getChildCount();
    }

    public final void i(View view) {
        this.c.add(view);
        Xk xk = this.a;
        AbstractC0470ol abstractC0470olK = RecyclerView.K(view);
        if (abstractC0470olK != null) {
            int i = abstractC0470olK.q;
            View view2 = abstractC0470olK.a;
            if (i != -1) {
                abstractC0470olK.p = i;
            } else {
                abstractC0470olK.p = view2.getImportantForAccessibility();
            }
            RecyclerView recyclerView = xk.a;
            if (!recyclerView.P()) {
                view2.setImportantForAccessibility(4);
            } else {
                abstractC0470olK.q = 4;
                recyclerView.r0.add(abstractC0470olK);
            }
        }
    }

    public final void j(View view) {
        if (this.c.remove(view)) {
            Xk xk = this.a;
            AbstractC0470ol abstractC0470olK = RecyclerView.K(view);
            if (abstractC0470olK != null) {
                int i = abstractC0470olK.p;
                RecyclerView recyclerView = xk.a;
                if (recyclerView.P()) {
                    abstractC0470olK.q = i;
                    recyclerView.r0.add(abstractC0470olK);
                } else {
                    abstractC0470olK.a.setImportantForAccessibility(i);
                }
                abstractC0470olK.p = 0;
            }
        }
    }

    public final String toString() {
        return this.b.toString() + ", hidden list:" + this.c.size();
    }
}
