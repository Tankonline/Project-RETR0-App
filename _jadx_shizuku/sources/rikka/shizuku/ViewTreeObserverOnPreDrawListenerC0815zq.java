package rikka.shizuku;

import android.animation.Animator;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowId;
import java.util.ArrayList;

/* JADX INFO: renamed from: rikka.shizuku.zq, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class ViewTreeObserverOnPreDrawListenerC0815zq implements ViewTreeObserver.OnPreDrawListener, View.OnAttachStateChangeListener {
    public AbstractC0722wq a;
    public ViewGroup b;

    /* JADX WARN: Code duplicated, block: B:100:0x021c  */
    /* JADX WARN: Code duplicated, block: B:102:0x022a  */
    /* JADX WARN: Code duplicated, block: B:103:0x0236  */
    /* JADX WARN: Code duplicated, block: B:107:0x0248  */
    /* JADX WARN: Code duplicated, block: B:134:0x01f4 A[EDGE_INSN: B:134:0x01f4->B:90:0x01f4 BREAK  A[LOOP:1: B:18:0x0086->B:89:0x01ea], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:14:0x004e  */
    /* JADX WARN: Code duplicated, block: B:164:0x0214 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:16:0x0055 A[LOOP:0: B:15:0x0053->B:16:0x0055, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:20:0x008b  */
    /* JADX WARN: Code duplicated, block: B:22:0x008f  */
    /* JADX WARN: Code duplicated, block: B:24:0x0092  */
    /* JADX WARN: Code duplicated, block: B:26:0x0095  */
    /* JADX WARN: Code duplicated, block: B:28:0x0098  */
    /* JADX WARN: Code duplicated, block: B:29:0x009d  */
    /* JADX WARN: Code duplicated, block: B:31:0x00a8  */
    /* JADX WARN: Code duplicated, block: B:44:0x00f4  */
    /* JADX WARN: Code duplicated, block: B:47:0x0104  */
    /* JADX WARN: Code duplicated, block: B:49:0x0116  */
    /* JADX WARN: Code duplicated, block: B:62:0x015d  */
    /* JADX WARN: Code duplicated, block: B:64:0x0169  */
    /* JADX WARN: Code duplicated, block: B:77:0x01b0  */
    /* JADX WARN: Code duplicated, block: B:79:0x01b9  */
    /* JADX WARN: Code duplicated, block: B:93:0x01fb  */
    /* JADX WARN: Code duplicated, block: B:95:0x0209  */
    @Override // android.view.ViewTreeObserver.OnPreDrawListener
    public final boolean onPreDraw() {
        ArrayList arrayList;
        AbstractC0722wq abstractC0722wq;
        int i;
        Xn xn;
        Xn xn2;
        C0298j4 c0298j4;
        C0298j4 c0298j5;
        int i2;
        int[] iArr;
        boolean z;
        int i3;
        int i4;
        C0298j4 c0298j4P;
        int i5;
        Animator animator;
        C0598sq c0598sq;
        View view;
        Eq eq;
        Eq eq2;
        int i6;
        Xn xn3;
        boolean z2;
        int i7;
        View view2;
        Eq eq3;
        C0298j4 c0298j6;
        int i8;
        int i9;
        View view3;
        SparseArray sparseArray;
        int size;
        int i10;
        View view4;
        View view5;
        C0620th c0620th;
        int iE;
        int i11;
        View view6;
        Xn xn4;
        int size2;
        int i12;
        ViewGroup viewGroup = this.b;
        viewGroup.getViewTreeObserver().removeOnPreDrawListener(this);
        viewGroup.removeOnAttachStateChangeListener(this);
        ArrayList arrayList2 = Aq.c;
        ViewGroup viewGroup2 = this.b;
        boolean z3 = true;
        if (!arrayList2.remove(viewGroup2)) {
            return true;
        }
        C0298j4 c0298j4B = Aq.b();
        ArrayList arrayList3 = (ArrayList) c0298j4B.get(viewGroup2);
        if (arrayList3 != null) {
            arrayList = arrayList3.size() > 0 ? new ArrayList(arrayList3) : null;
            abstractC0722wq = this.a;
            arrayList3.add(abstractC0722wq);
            abstractC0722wq.a(new C0784yq(this, c0298j4B));
            i = 0;
            abstractC0722wq.h(viewGroup2, false);
            if (arrayList != null) {
                size2 = arrayList.size();
                i12 = 0;
                while (i12 < size2) {
                    Object obj = arrayList.get(i12);
                    i12++;
                    ((AbstractC0722wq) obj).A(viewGroup2);
                }
            }
            abstractC0722wq.k = new ArrayList();
            abstractC0722wq.l = new ArrayList();
            xn = abstractC0722wq.g;
            xn2 = abstractC0722wq.h;
            c0298j4 = new C0298j4((C0298j4) xn.a);
            c0298j5 = new C0298j4((C0298j4) xn2.a);
            i2 = 0;
            while (true) {
                iArr = abstractC0722wq.j;
                if (i2 < iArr.length) {
                    break;
                }
                i6 = iArr[i2];
                if (i6 != z3) {
                    xn3 = xn;
                    z2 = z3;
                    for (i7 = c0298j4.c - 1; i7 >= 0; i7--) {
                        view2 = (View) c0298j4.f(i7);
                        if (view2 == null && abstractC0722wq.u(view2) && (eq3 = (Eq) c0298j5.remove(view2)) != null && abstractC0722wq.u(eq3.b)) {
                            abstractC0722wq.k.add((Eq) c0298j4.g(i7));
                            abstractC0722wq.l.add(eq3);
                        }
                    }
                } else if (i6 != 2) {
                    xn3 = xn;
                    z2 = z3;
                    c0298j6 = (C0298j4) xn3.d;
                    i8 = c0298j6.c;
                    for (i9 = 0; i9 < i8; i9++) {
                        view3 = (View) c0298j6.i(i9);
                        if (view3 == null && abstractC0722wq.u(view3)) {
                            View view7 = (View) ((C0298j4) xn2.d).get(c0298j6.f(i9));
                            if (view7 != null && abstractC0722wq.u(view7)) {
                                Eq eq4 = (Eq) c0298j4.get(view3);
                                Eq eq5 = (Eq) c0298j5.get(view7);
                                if (eq4 != null && eq5 != null) {
                                    abstractC0722wq.k.add(eq4);
                                    abstractC0722wq.l.add(eq5);
                                    c0298j4.remove(view3);
                                    c0298j5.remove(view7);
                                }
                            }
                        }
                    }
                } else if (i6 != 3) {
                    xn3 = xn;
                    z2 = z3;
                    sparseArray = (SparseArray) xn3.b;
                    SparseArray sparseArray2 = (SparseArray) xn2.b;
                    size = sparseArray.size();
                    for (i10 = 0; i10 < size; i10++) {
                        view4 = (View) sparseArray.valueAt(i10);
                        if (view4 == null && abstractC0722wq.u(view4) && (view5 = (View) sparseArray2.get(sparseArray.keyAt(i10))) != null && abstractC0722wq.u(view5)) {
                            Eq eq6 = (Eq) c0298j4.get(view4);
                            Eq eq7 = (Eq) c0298j5.get(view5);
                            if (eq6 != null && eq7 != null) {
                                abstractC0722wq.k.add(eq6);
                                abstractC0722wq.l.add(eq7);
                                c0298j4.remove(view4);
                                c0298j5.remove(view5);
                            }
                        }
                    }
                } else if (i6 != 4) {
                    xn3 = xn;
                    z2 = z3;
                } else {
                    c0620th = (C0620th) xn.c;
                    iE = c0620th.e();
                    i11 = i;
                    while (i11 < iE) {
                        view6 = (View) c0620th.f(i11);
                        if (view6 == null && abstractC0722wq.u(view6)) {
                            xn4 = xn;
                            View view8 = (View) ((C0620th) xn2.c).b(c0620th.c(i11));
                            if (view8 != null && abstractC0722wq.u(view8)) {
                                Eq eq8 = (Eq) c0298j4.get(view6);
                                Eq eq9 = (Eq) c0298j5.get(view8);
                                if (eq8 != null && eq9 != null) {
                                    abstractC0722wq.k.add(eq8);
                                    abstractC0722wq.l.add(eq9);
                                    c0298j4.remove(view6);
                                    c0298j5.remove(view8);
                                }
                            }
                            i11++;
                            xn = xn4;
                            z3 = z3;
                        } else {
                            xn4 = xn;
                        }
                        i11++;
                        xn = xn4;
                        z3 = z3;
                    }
                    z2 = z3;
                    xn3 = xn;
                }
                i2++;
                xn = xn3;
                z3 = z2;
                i = 0;
            }
            z = z3;
            for (i3 = 0; i3 < c0298j4.c; i3++) {
                eq2 = (Eq) c0298j4.i(i3);
                if (abstractC0722wq.u(eq2.b)) {
                    abstractC0722wq.k.add(eq2);
                    abstractC0722wq.l.add(null);
                }
            }
            for (i4 = 0; i4 < c0298j5.c; i4++) {
                eq = (Eq) c0298j5.i(i4);
                if (abstractC0722wq.u(eq.b)) {
                    abstractC0722wq.l.add(eq);
                    abstractC0722wq.k.add(null);
                }
            }
            c0298j4P = AbstractC0722wq.p();
            int i13 = c0298j4P.c;
            WindowId windowId = viewGroup2.getWindowId();
            i5 = i13 - 1;
            while (i5 >= 0) {
                animator = (Animator) c0298j4P.f(i5);
                if (animator == null && (c0598sq = (C0598sq) c0298j4P.get(animator)) != null && (view = c0598sq.a) != null && windowId.equals(c0598sq.d)) {
                    boolean z4 = z;
                    Eq eqR = abstractC0722wq.r(view, z4);
                    Eq eqN = abstractC0722wq.n(view, z4);
                    if (eqR == null && eqN == null) {
                        eqN = (Eq) ((C0298j4) abstractC0722wq.h.a).get(view);
                    }
                    if (eqR != null || eqN != null) {
                        Eq eq10 = c0598sq.c;
                        AbstractC0722wq abstractC0722wq2 = c0598sq.e;
                        if (abstractC0722wq2.t(eq10, eqN)) {
                            abstractC0722wq2.o().getClass();
                            if (animator.isRunning() || animator.isStarted()) {
                                animator.cancel();
                            } else {
                                c0298j4P.remove(animator);
                            }
                        }
                    }
                }
                i5--;
                z = true;
            }
            abstractC0722wq.l(viewGroup2, abstractC0722wq.g, abstractC0722wq.h, abstractC0722wq.k, abstractC0722wq.l);
            abstractC0722wq.B();
            return true;
        }
        arrayList3 = new ArrayList();
        c0298j4B.put(viewGroup2, arrayList3);
        abstractC0722wq = this.a;
        arrayList3.add(abstractC0722wq);
        abstractC0722wq.a(new C0784yq(this, c0298j4B));
        i = 0;
        abstractC0722wq.h(viewGroup2, false);
        if (arrayList != null) {
            size2 = arrayList.size();
            i12 = 0;
            while (i12 < size2) {
                Object obj2 = arrayList.get(i12);
                i12++;
                ((AbstractC0722wq) obj2).A(viewGroup2);
            }
        }
        abstractC0722wq.k = new ArrayList();
        abstractC0722wq.l = new ArrayList();
        xn = abstractC0722wq.g;
        xn2 = abstractC0722wq.h;
        c0298j4 = new C0298j4((C0298j4) xn.a);
        c0298j5 = new C0298j4((C0298j4) xn2.a);
        i2 = 0;
        while (true) {
            iArr = abstractC0722wq.j;
            if (i2 < iArr.length) {
                break;
                break;
            }
            i6 = iArr[i2];
            if (i6 != z3) {
                xn3 = xn;
                z2 = z3;
                while (i7 >= 0) {
                    view2 = (View) c0298j4.f(i7);
                    if (view2 == null) {
                    }
                }
            } else if (i6 != 2) {
                xn3 = xn;
                z2 = z3;
                c0298j6 = (C0298j4) xn3.d;
                i8 = c0298j6.c;
                while (i9 < i8) {
                    view3 = (View) c0298j6.i(i9);
                    if (view3 == null) {
                    }
                }
            } else if (i6 != 3) {
                xn3 = xn;
                z2 = z3;
                sparseArray = (SparseArray) xn3.b;
                SparseArray sparseArray3 = (SparseArray) xn2.b;
                size = sparseArray.size();
                while (i10 < size) {
                    view4 = (View) sparseArray.valueAt(i10);
                    if (view4 == null) {
                    }
                }
            } else if (i6 != 4) {
                xn3 = xn;
                z2 = z3;
            } else {
                c0620th = (C0620th) xn.c;
                iE = c0620th.e();
                i11 = i;
                while (i11 < iE) {
                    view6 = (View) c0620th.f(i11);
                    if (view6 == null) {
                        xn4 = xn;
                    } else {
                        xn4 = xn;
                    }
                    i11++;
                    xn = xn4;
                    z3 = z3;
                }
                z2 = z3;
                xn3 = xn;
            }
            i2++;
            xn = xn3;
            z3 = z2;
            i = 0;
        }
        z = z3;
        while (i3 < c0298j4.c) {
            eq2 = (Eq) c0298j4.i(i3);
            if (abstractC0722wq.u(eq2.b)) {
                abstractC0722wq.k.add(eq2);
                abstractC0722wq.l.add(null);
            }
        }
        while (i4 < c0298j5.c) {
            eq = (Eq) c0298j5.i(i4);
            if (abstractC0722wq.u(eq.b)) {
                abstractC0722wq.l.add(eq);
                abstractC0722wq.k.add(null);
            }
        }
        c0298j4P = AbstractC0722wq.p();
        int i14 = c0298j4P.c;
        WindowId windowId2 = viewGroup2.getWindowId();
        i5 = i14 - 1;
        while (i5 >= 0) {
            animator = (Animator) c0298j4P.f(i5);
            if (animator == null) {
            }
            i5--;
            z = true;
        }
        abstractC0722wq.l(viewGroup2, abstractC0722wq.g, abstractC0722wq.h, abstractC0722wq.k, abstractC0722wq.l);
        abstractC0722wq.B();
        return true;
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewDetachedFromWindow(View view) {
        ViewGroup viewGroup = this.b;
        viewGroup.getViewTreeObserver().removeOnPreDrawListener(this);
        viewGroup.removeOnAttachStateChangeListener(this);
        ArrayList arrayList = Aq.c;
        ViewGroup viewGroup2 = this.b;
        arrayList.remove(viewGroup2);
        ArrayList arrayList2 = (ArrayList) Aq.b().get(viewGroup2);
        if (arrayList2 != null && arrayList2.size() > 0) {
            int size = arrayList2.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList2.get(i);
                i++;
                ((AbstractC0722wq) obj).A(viewGroup2);
            }
        }
        this.a.i(true);
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewAttachedToWindow(View view) {
    }
}
