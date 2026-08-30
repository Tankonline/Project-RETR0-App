package rikka.shizuku;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

/* JADX INFO: renamed from: rikka.shizuku.o9, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0458o9 {
    public static TimeInterpolator s;
    public Xk a;
    public ArrayList b;
    public long c;
    public long d;
    public long e;
    public long f;
    public boolean g;
    public ArrayList h;
    public ArrayList i;
    public ArrayList j;
    public ArrayList k;
    public ArrayList l;
    public ArrayList m;
    public ArrayList n;
    public ArrayList o;
    public ArrayList p;
    public ArrayList q;
    public ArrayList r;

    public static void c(AbstractC0470ol abstractC0470ol) {
        RecyclerView recyclerView;
        int i = abstractC0470ol.j;
        if (abstractC0470ol.f() || (i & 4) != 0 || (recyclerView = abstractC0470ol.r) == null) {
            return;
        }
        recyclerView.H(abstractC0470ol);
    }

    public static void d(ArrayList arrayList) {
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            ((AbstractC0470ol) arrayList.get(size)).a.animate().cancel();
        }
    }

    public final boolean a(AbstractC0470ol abstractC0470ol, AbstractC0470ol abstractC0470ol2, Yi yi, Yi yi2) {
        int i;
        int i2;
        int i3 = yi.a;
        int i4 = yi.b;
        if (abstractC0470ol2.o()) {
            int i5 = yi.a;
            i2 = yi.b;
            i = i5;
        } else {
            i = yi2.a;
            i2 = yi2.b;
        }
        if (abstractC0470ol == abstractC0470ol2) {
            return b(abstractC0470ol, i3, i4, i, i2);
        }
        View view = abstractC0470ol.a;
        float translationX = view.getTranslationX();
        float translationY = view.getTranslationY();
        float alpha = view.getAlpha();
        l(abstractC0470ol);
        view.setTranslationX(translationX);
        view.setTranslationY(translationY);
        view.setAlpha(alpha);
        l(abstractC0470ol2);
        float f = -((int) ((i - i3) - translationX));
        View view2 = abstractC0470ol2.a;
        view2.setTranslationX(f);
        view2.setTranslationY(-((int) ((i2 - i4) - translationY)));
        view2.setAlpha(0.0f);
        ArrayList arrayList = this.k;
        C0396m9 c0396m9 = new C0396m9();
        c0396m9.a = abstractC0470ol;
        c0396m9.b = abstractC0470ol2;
        c0396m9.c = i3;
        c0396m9.d = i4;
        c0396m9.e = i;
        c0396m9.f = i2;
        arrayList.add(c0396m9);
        return true;
    }

    public final boolean b(AbstractC0470ol abstractC0470ol, int i, int i2, int i3, int i4) {
        View view = abstractC0470ol.a;
        int translationX = i + ((int) view.getTranslationX());
        int translationY = i2 + ((int) abstractC0470ol.a.getTranslationY());
        l(abstractC0470ol);
        int i5 = i3 - translationX;
        int i6 = i4 - translationY;
        if (i5 == 0 && i6 == 0) {
            e(abstractC0470ol);
            return false;
        }
        if (i5 != 0) {
            view.setTranslationX(-i5);
        }
        if (i6 != 0) {
            view.setTranslationY(-i6);
        }
        ArrayList arrayList = this.j;
        C0427n9 c0427n9 = new C0427n9();
        c0427n9.a = abstractC0470ol;
        c0427n9.b = translationX;
        c0427n9.c = translationY;
        c0427n9.d = i3;
        c0427n9.e = i4;
        arrayList.add(c0427n9);
        return true;
    }

    /* JADX WARN: Code duplicated, block: B:33:0x0068  */
    public final void e(AbstractC0470ol abstractC0470ol) {
        Xk xk = this.a;
        if (xk != null) {
            boolean z = true;
            abstractC0470ol.n(true);
            if (abstractC0470ol.h != null && abstractC0470ol.i == null) {
                abstractC0470ol.h = null;
            }
            abstractC0470ol.i = null;
            if ((abstractC0470ol.j & 16) != 0) {
                return;
            }
            RecyclerView recyclerView = xk.a;
            recyclerView.n0();
            W5 w5 = recyclerView.f;
            V5 v5 = w5.b;
            Xk xk2 = w5.a;
            int i = w5.d;
            View view = abstractC0470ol.a;
            if (i != 1) {
                if (i == 2) {
                    throw new IllegalStateException("Cannot call removeViewIfHidden within removeViewIfHidden");
                }
                try {
                    w5.d = 2;
                    int iIndexOfChild = xk2.a.indexOfChild(view);
                    if (iIndexOfChild == -1) {
                        w5.j(view);
                    } else if (v5.d(iIndexOfChild)) {
                        v5.f(iIndexOfChild);
                        w5.j(view);
                        xk2.i(iIndexOfChild);
                    } else {
                        w5.d = 0;
                    }
                    w5.d = 0;
                    if (z) {
                        AbstractC0470ol abstractC0470olK = RecyclerView.K(view);
                        C0285il c0285il = recyclerView.c;
                        c0285il.l(abstractC0470olK);
                        c0285il.i(abstractC0470olK);
                    }
                    recyclerView.p0(!z);
                    if (z && abstractC0470ol.j()) {
                        recyclerView.removeDetachedView(view, false);
                        return;
                    }
                } catch (Throwable th) {
                    w5.d = 0;
                    throw th;
                }
            }
            if (w5.e != view) {
                throw new IllegalStateException("Cannot call removeViewIfHidden within removeView(At) for a different view");
            }
            z = false;
            if (z) {
                AbstractC0470ol abstractC0470olK2 = RecyclerView.K(view);
                C0285il c0285il2 = recyclerView.c;
                c0285il2.l(abstractC0470olK2);
                c0285il2.i(abstractC0470olK2);
            }
            recyclerView.p0(!z);
            if (z) {
            }
        }
    }

    public final void f() {
        if (k()) {
            return;
        }
        ArrayList arrayList = this.b;
        if (arrayList.size() <= 0) {
            arrayList.clear();
        } else {
            arrayList.get(0).getClass();
            throw new ClassCastException();
        }
    }

    public final void g(AbstractC0470ol abstractC0470ol) {
        View view = abstractC0470ol.a;
        view.animate().cancel();
        ArrayList arrayList = this.j;
        int size = arrayList.size();
        while (true) {
            size--;
            if (size < 0) {
                break;
            }
            if (((C0427n9) arrayList.get(size)).a == abstractC0470ol) {
                view.setTranslationY(0.0f);
                view.setTranslationX(0.0f);
                e(abstractC0470ol);
                arrayList.remove(size);
            }
        }
        i(this.k, abstractC0470ol);
        if (this.h.remove(abstractC0470ol)) {
            view.setAlpha(1.0f);
            e(abstractC0470ol);
        }
        if (this.i.remove(abstractC0470ol)) {
            view.setAlpha(1.0f);
            e(abstractC0470ol);
        }
        ArrayList arrayList2 = this.n;
        for (int size2 = arrayList2.size() - 1; size2 >= 0; size2--) {
            ArrayList arrayList3 = (ArrayList) arrayList2.get(size2);
            i(arrayList3, abstractC0470ol);
            if (arrayList3.isEmpty()) {
                arrayList2.remove(size2);
            }
        }
        ArrayList arrayList4 = this.m;
        for (int size3 = arrayList4.size() - 1; size3 >= 0; size3--) {
            ArrayList arrayList5 = (ArrayList) arrayList4.get(size3);
            for (int size4 = arrayList5.size() - 1; size4 >= 0; size4--) {
                if (((C0427n9) arrayList5.get(size4)).a == abstractC0470ol) {
                    view.setTranslationY(0.0f);
                    view.setTranslationX(0.0f);
                    e(abstractC0470ol);
                    arrayList5.remove(size4);
                    if (!arrayList5.isEmpty()) {
                        break;
                    }
                    arrayList4.remove(size3);
                    break;
                }
            }
        }
        ArrayList arrayList6 = this.l;
        for (int size5 = arrayList6.size() - 1; size5 >= 0; size5--) {
            ArrayList arrayList7 = (ArrayList) arrayList6.get(size5);
            if (arrayList7.remove(abstractC0470ol)) {
                view.setAlpha(1.0f);
                e(abstractC0470ol);
                if (arrayList7.isEmpty()) {
                    arrayList6.remove(size5);
                }
            }
        }
        this.q.remove(abstractC0470ol);
        this.o.remove(abstractC0470ol);
        this.r.remove(abstractC0470ol);
        this.p.remove(abstractC0470ol);
        f();
    }

    public final void h() {
        ArrayList arrayList = this.j;
        int size = arrayList.size();
        while (true) {
            size--;
            if (size < 0) {
                break;
            }
            C0427n9 c0427n9 = (C0427n9) arrayList.get(size);
            View view = c0427n9.a.a;
            view.setTranslationY(0.0f);
            view.setTranslationX(0.0f);
            e(c0427n9.a);
            arrayList.remove(size);
        }
        ArrayList arrayList2 = this.h;
        for (int size2 = arrayList2.size() - 1; size2 >= 0; size2--) {
            e((AbstractC0470ol) arrayList2.get(size2));
            arrayList2.remove(size2);
        }
        ArrayList arrayList3 = this.i;
        int size3 = arrayList3.size();
        while (true) {
            size3--;
            if (size3 < 0) {
                break;
            }
            AbstractC0470ol abstractC0470ol = (AbstractC0470ol) arrayList3.get(size3);
            abstractC0470ol.a.setAlpha(1.0f);
            e(abstractC0470ol);
            arrayList3.remove(size3);
        }
        ArrayList arrayList4 = this.k;
        for (int size4 = arrayList4.size() - 1; size4 >= 0; size4--) {
            C0396m9 c0396m9 = (C0396m9) arrayList4.get(size4);
            AbstractC0470ol abstractC0470ol2 = c0396m9.a;
            if (abstractC0470ol2 != null) {
                j(c0396m9, abstractC0470ol2);
            }
            AbstractC0470ol abstractC0470ol3 = c0396m9.b;
            if (abstractC0470ol3 != null) {
                j(c0396m9, abstractC0470ol3);
            }
        }
        arrayList4.clear();
        if (k()) {
            ArrayList arrayList5 = this.m;
            for (int size5 = arrayList5.size() - 1; size5 >= 0; size5--) {
                ArrayList arrayList6 = (ArrayList) arrayList5.get(size5);
                for (int size6 = arrayList6.size() - 1; size6 >= 0; size6--) {
                    C0427n9 c0427n10 = (C0427n9) arrayList6.get(size6);
                    View view2 = c0427n10.a.a;
                    view2.setTranslationY(0.0f);
                    view2.setTranslationX(0.0f);
                    e(c0427n10.a);
                    arrayList6.remove(size6);
                    if (arrayList6.isEmpty()) {
                        arrayList5.remove(arrayList6);
                    }
                }
            }
            ArrayList arrayList7 = this.l;
            for (int size7 = arrayList7.size() - 1; size7 >= 0; size7--) {
                ArrayList arrayList8 = (ArrayList) arrayList7.get(size7);
                for (int size8 = arrayList8.size() - 1; size8 >= 0; size8--) {
                    AbstractC0470ol abstractC0470ol4 = (AbstractC0470ol) arrayList8.get(size8);
                    abstractC0470ol4.a.setAlpha(1.0f);
                    e(abstractC0470ol4);
                    arrayList8.remove(size8);
                    if (arrayList8.isEmpty()) {
                        arrayList7.remove(arrayList8);
                    }
                }
            }
            ArrayList arrayList9 = this.n;
            for (int size9 = arrayList9.size() - 1; size9 >= 0; size9--) {
                ArrayList arrayList10 = (ArrayList) arrayList9.get(size9);
                for (int size10 = arrayList10.size() - 1; size10 >= 0; size10--) {
                    C0396m9 c0396m10 = (C0396m9) arrayList10.get(size10);
                    AbstractC0470ol abstractC0470ol5 = c0396m10.a;
                    if (abstractC0470ol5 != null) {
                        j(c0396m10, abstractC0470ol5);
                    }
                    AbstractC0470ol abstractC0470ol6 = c0396m10.b;
                    if (abstractC0470ol6 != null) {
                        j(c0396m10, abstractC0470ol6);
                    }
                    if (arrayList10.isEmpty()) {
                        arrayList9.remove(arrayList10);
                    }
                }
            }
            d(this.q);
            d(this.p);
            d(this.o);
            d(this.r);
            ArrayList arrayList11 = this.b;
            if (arrayList11.size() > 0) {
                arrayList11.get(0).getClass();
                throw new ClassCastException();
            }
            arrayList11.clear();
        }
    }

    public final void i(ArrayList arrayList, AbstractC0470ol abstractC0470ol) {
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            C0396m9 c0396m9 = (C0396m9) arrayList.get(size);
            if (j(c0396m9, abstractC0470ol) && c0396m9.a == null && c0396m9.b == null) {
                arrayList.remove(c0396m9);
            }
        }
    }

    public final boolean j(C0396m9 c0396m9, AbstractC0470ol abstractC0470ol) {
        if (c0396m9.b == abstractC0470ol) {
            c0396m9.b = null;
        } else {
            if (c0396m9.a != abstractC0470ol) {
                return false;
            }
            c0396m9.a = null;
        }
        abstractC0470ol.a.setAlpha(1.0f);
        View view = abstractC0470ol.a;
        view.setTranslationX(0.0f);
        view.setTranslationY(0.0f);
        e(abstractC0470ol);
        return true;
    }

    public final boolean k() {
        return (this.i.isEmpty() && this.k.isEmpty() && this.j.isEmpty() && this.h.isEmpty() && this.p.isEmpty() && this.q.isEmpty() && this.o.isEmpty() && this.r.isEmpty() && this.m.isEmpty() && this.l.isEmpty() && this.n.isEmpty()) ? false : true;
    }

    public final void l(AbstractC0470ol abstractC0470ol) {
        if (s == null) {
            s = new ValueAnimator().getInterpolator();
        }
        abstractC0470ol.a.animate().setInterpolator(s);
        g(abstractC0470ol);
    }
}
