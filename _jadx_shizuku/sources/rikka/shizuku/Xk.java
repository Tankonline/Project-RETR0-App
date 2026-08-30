package rikka.shizuku;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;

/* JADX INFO: loaded from: classes.dex */
public final class Xk implements W9 {
    public final /* synthetic */ RecyclerView a;

    public /* synthetic */ Xk(RecyclerView recyclerView) {
        this.a = recyclerView;
    }

    public void a(S0 s0) {
        int i = s0.a;
        RecyclerView recyclerView = this.a;
        if (i == 1) {
            recyclerView.n.Y(s0.b, s0.d);
            return;
        }
        if (i == 2) {
            recyclerView.n.b0(s0.b, s0.d);
        } else if (i == 4) {
            recyclerView.n.c0(s0.b, s0.d);
        } else {
            if (i != 8) {
                return;
            }
            recyclerView.n.a0(s0.b, s0.d);
        }
    }

    @Override // rikka.shizuku.W9
    public boolean b(float f) {
        int i;
        int i2;
        RecyclerView recyclerView = this.a;
        if (recyclerView.n.e()) {
            i2 = (int) f;
            i = 0;
        } else if (recyclerView.n.d()) {
            i = (int) f;
            i2 = 0;
        } else {
            i = 0;
            i2 = 0;
        }
        if (i == 0 && i2 == 0) {
            return false;
        }
        recyclerView.q0();
        return recyclerView.G(i, i2, 0, Integer.MAX_VALUE);
    }

    public AbstractC0470ol c(int i) {
        RecyclerView recyclerView = this.a;
        int iH = recyclerView.f.h();
        AbstractC0470ol abstractC0470ol = null;
        for (int i2 = 0; i2 < iH; i2++) {
            AbstractC0470ol abstractC0470olK = RecyclerView.K(recyclerView.f.g(i2));
            if (abstractC0470olK != null && !abstractC0470olK.h() && abstractC0470olK.c == i) {
                if (!recyclerView.f.c.contains(abstractC0470olK.a)) {
                    abstractC0470ol = abstractC0470olK;
                    break;
                }
                abstractC0470ol = abstractC0470olK;
            }
        }
        if (abstractC0470ol == null) {
            return null;
        }
        if (!recyclerView.f.c.contains(abstractC0470ol.a)) {
            return abstractC0470ol;
        }
        int[] iArr = RecyclerView.z0;
        return null;
    }

    public void d(int i, int i2, Object obj) {
        int i3;
        int i4;
        RecyclerView recyclerView = this.a;
        int iH = recyclerView.f.h();
        int i5 = i2 + i;
        for (int i6 = 0; i6 < iH; i6++) {
            View viewG = recyclerView.f.g(i6);
            AbstractC0470ol abstractC0470olK = RecyclerView.K(viewG);
            if (abstractC0470olK != null && !abstractC0470olK.o() && (i4 = abstractC0470olK.c) >= i && i4 < i5) {
                abstractC0470olK.a(2);
                if (obj == null) {
                    abstractC0470olK.a(1024);
                } else if ((1024 & abstractC0470olK.j) == 0) {
                    if (abstractC0470olK.k == null) {
                        ArrayList arrayList = new ArrayList();
                        abstractC0470olK.k = arrayList;
                        abstractC0470olK.l = Collections.unmodifiableList(arrayList);
                    }
                    abstractC0470olK.k.add(obj);
                }
                ((C0193fl) viewG.getLayoutParams()).c = true;
            }
        }
        C0285il c0285il = recyclerView.c;
        ArrayList arrayList2 = c0285il.c;
        for (int size = arrayList2.size() - 1; size >= 0; size--) {
            AbstractC0470ol abstractC0470ol = (AbstractC0470ol) arrayList2.get(size);
            if (abstractC0470ol != null && (i3 = abstractC0470ol.c) >= i && i3 < i5) {
                abstractC0470ol.a(2);
                c0285il.g(size);
            }
        }
        recyclerView.j0 = true;
    }

    public void e(int i, int i2) {
        RecyclerView recyclerView = this.a;
        int iH = recyclerView.f.h();
        for (int i3 = 0; i3 < iH; i3++) {
            AbstractC0470ol abstractC0470olK = RecyclerView.K(recyclerView.f.g(i3));
            if (abstractC0470olK != null && !abstractC0470olK.o() && abstractC0470olK.c >= i) {
                abstractC0470olK.l(i2, false);
                recyclerView.g0.f = true;
            }
        }
        ArrayList arrayList = recyclerView.c.c;
        int size = arrayList.size();
        for (int i4 = 0; i4 < size; i4++) {
            AbstractC0470ol abstractC0470ol = (AbstractC0470ol) arrayList.get(i4);
            if (abstractC0470ol != null && abstractC0470ol.c >= i) {
                abstractC0470ol.l(i2, false);
            }
        }
        recyclerView.requestLayout();
        recyclerView.i0 = true;
    }

    public void f(int i, int i2) {
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        RecyclerView recyclerView = this.a;
        int iH = recyclerView.f.h();
        int i10 = -1;
        if (i < i2) {
            i4 = i;
            i3 = i2;
            i5 = -1;
        } else {
            i3 = i;
            i4 = i2;
            i5 = 1;
        }
        for (int i11 = 0; i11 < iH; i11++) {
            AbstractC0470ol abstractC0470olK = RecyclerView.K(recyclerView.f.g(i11));
            if (abstractC0470olK != null && (i9 = abstractC0470olK.c) >= i4 && i9 <= i3) {
                if (i9 == i) {
                    abstractC0470olK.l(i2 - i, false);
                } else {
                    abstractC0470olK.l(i5, false);
                }
                recyclerView.g0.f = true;
            }
        }
        C0285il c0285il = recyclerView.c;
        c0285il.getClass();
        if (i < i2) {
            i7 = i;
            i6 = i2;
        } else {
            i6 = i;
            i7 = i2;
            i10 = 1;
        }
        ArrayList arrayList = c0285il.c;
        int size = arrayList.size();
        for (int i12 = 0; i12 < size; i12++) {
            AbstractC0470ol abstractC0470ol = (AbstractC0470ol) arrayList.get(i12);
            if (abstractC0470ol != null && (i8 = abstractC0470ol.c) >= i7 && i8 <= i6) {
                if (i8 == i) {
                    abstractC0470ol.l(i2 - i, false);
                } else {
                    abstractC0470ol.l(i10, false);
                }
            }
        }
        recyclerView.requestLayout();
        recyclerView.i0 = true;
    }

    /* JADX WARN: Code duplicated, block: B:9:0x001d  */
    public void g(AbstractC0470ol abstractC0470ol, Yi yi, Yi yi2) {
        boolean zB;
        RecyclerView recyclerView = this.a;
        recyclerView.getClass();
        abstractC0470ol.n(false);
        C0458o9 c0458o9 = recyclerView.L;
        if (yi != null) {
            c0458o9.getClass();
            int i = yi.a;
            int i2 = yi2.a;
            if (i == i2 && yi.b == yi2.b) {
                c0458o9.l(abstractC0470ol);
                abstractC0470ol.a.setAlpha(0.0f);
                c0458o9.i.add(abstractC0470ol);
                zB = true;
            } else {
                zB = c0458o9.b(abstractC0470ol, i, yi.b, i2, yi2.b);
            }
        } else {
            c0458o9.l(abstractC0470ol);
            abstractC0470ol.a.setAlpha(0.0f);
            c0458o9.i.add(abstractC0470ol);
            zB = true;
        }
        if (zB) {
            recyclerView.W();
        }
    }

    public void h(AbstractC0470ol abstractC0470ol, Yi yi, Yi yi2) {
        boolean zB;
        RecyclerView recyclerView = this.a;
        recyclerView.c.l(abstractC0470ol);
        recyclerView.h(abstractC0470ol);
        abstractC0470ol.n(false);
        C0458o9 c0458o9 = recyclerView.L;
        c0458o9.getClass();
        int i = yi.a;
        int i2 = yi.b;
        View view = abstractC0470ol.a;
        int left = yi2 == null ? view.getLeft() : yi2.a;
        int top = yi2 == null ? view.getTop() : yi2.b;
        if (abstractC0470ol.h() || (i == left && i2 == top)) {
            c0458o9.l(abstractC0470ol);
            c0458o9.h.add(abstractC0470ol);
            zB = true;
        } else {
            view.layout(left, top, view.getWidth() + left, view.getHeight() + top);
            zB = c0458o9.b(abstractC0470ol, i, i2, left, top);
        }
        if (zB) {
            recyclerView.W();
        }
    }

    public void i(int i) {
        RecyclerView recyclerView = this.a;
        View childAt = recyclerView.getChildAt(i);
        if (childAt != null) {
            AbstractC0470ol abstractC0470olK = RecyclerView.K(childAt);
            Yk yk = recyclerView.m;
            if (yk != null && abstractC0470olK != null) {
                yk.h(abstractC0470olK);
            }
            childAt.clearAnimation();
        }
        recyclerView.removeViewAt(i);
    }

    @Override // rikka.shizuku.W9
    public float l() {
        float f;
        RecyclerView recyclerView = this.a;
        if (recyclerView.n.e()) {
            f = recyclerView.b0;
        } else {
            if (!recyclerView.n.d()) {
                return 0.0f;
            }
            f = recyclerView.a0;
        }
        return -f;
    }

    @Override // rikka.shizuku.W9
    public void m() {
        this.a.q0();
    }
}
