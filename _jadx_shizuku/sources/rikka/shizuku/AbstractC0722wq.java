package rikka.shizuku;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowId;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.WeakHashMap;

/* JADX INFO: renamed from: rikka.shizuku.wq, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0722wq implements Cloneable {
    public ArrayList k;
    public ArrayList l;
    public InterfaceC0691vq[] m;
    public long w;
    public long x;
    public static final Animator[] y = new Animator[0];
    public static final int[] z = {2, 1, 3, 4};
    public static final Pe A = new Pe(4);
    public static final ThreadLocal B = new ThreadLocal();
    public final String a = getClass().getName();
    public long b = -1;
    public long c = -1;
    public TimeInterpolator d = null;
    public final ArrayList e = new ArrayList();
    public final ArrayList f = new ArrayList();
    public Xn g = new Xn(8);
    public Xn h = new Xn(8);
    public C0638u4 i = null;
    public final int[] j = z;
    public final ArrayList n = new ArrayList();
    public Animator[] o = y;
    public int p = 0;
    public boolean q = false;
    public boolean r = false;
    public AbstractC0722wq s = null;
    public ArrayList t = null;
    public ArrayList u = new ArrayList();
    public Pe v = A;

    public static void b(Xn xn, View view, Eq eq) {
        ((C0298j4) xn.a).put(view, eq);
        int id = view.getId();
        if (id >= 0) {
            SparseArray sparseArray = (SparseArray) xn.b;
            if (sparseArray.indexOfKey(id) >= 0) {
                sparseArray.put(id, null);
            } else {
                sparseArray.put(id, view);
            }
        }
        WeakHashMap weakHashMap = Hs.a;
        String strF = AbstractC0817zs.f(view);
        if (strF != null) {
            C0298j4 c0298j4 = (C0298j4) xn.d;
            if (c0298j4.containsKey(strF)) {
                c0298j4.put(strF, null);
            } else {
                c0298j4.put(strF, view);
            }
        }
        if (view.getParent() instanceof ListView) {
            ListView listView = (ListView) view.getParent();
            if (listView.getAdapter().hasStableIds()) {
                long itemIdAtPosition = listView.getItemIdAtPosition(listView.getPositionForView(view));
                C0620th c0620th = (C0620th) xn.c;
                if (c0620th.a) {
                    int i = c0620th.d;
                    long[] jArr = c0620th.b;
                    Object[] objArr = c0620th.c;
                    int i2 = 0;
                    for (int i3 = 0; i3 < i; i3++) {
                        Object obj = objArr[i3];
                        if (obj != AbstractC0744xh.g) {
                            if (i3 != i2) {
                                jArr[i2] = jArr[i3];
                                objArr[i2] = obj;
                                objArr[i3] = null;
                            }
                            i2++;
                        }
                    }
                    c0620th.a = false;
                    c0620th.d = i2;
                }
                if (AbstractC0744xh.e(c0620th.b, c0620th.d, itemIdAtPosition) < 0) {
                    view.setHasTransientState(true);
                    c0620th.d(itemIdAtPosition, view);
                    return;
                }
                View view2 = (View) c0620th.b(itemIdAtPosition);
                if (view2 != null) {
                    view2.setHasTransientState(false);
                    c0620th.d(itemIdAtPosition, null);
                }
            }
        }
    }

    public static C0298j4 p() {
        ThreadLocal threadLocal = B;
        C0298j4 c0298j4 = (C0298j4) threadLocal.get();
        if (c0298j4 != null) {
            return c0298j4;
        }
        C0298j4 c0298j5 = new C0298j4(0);
        threadLocal.set(c0298j5);
        return c0298j5;
    }

    public static boolean v(Eq eq, Eq eq2, String str) {
        Object obj = eq.a.get(str);
        Object obj2 = eq2.a.get(str);
        if (obj == null && obj2 == null) {
            return false;
        }
        if (obj == null || obj2 == null) {
            return true;
        }
        return !obj.equals(obj2);
    }

    public void A(View view) {
        if (this.q) {
            if (!this.r) {
                ArrayList arrayList = this.n;
                int size = arrayList.size();
                Animator[] animatorArr = (Animator[]) arrayList.toArray(this.o);
                this.o = y;
                for (int i = size - 1; i >= 0; i--) {
                    Animator animator = animatorArr[i];
                    animatorArr[i] = null;
                    animator.resume();
                }
                this.o = animatorArr;
                w(this, C0579s7.f, false);
            }
            this.q = false;
        }
    }

    public void B() {
        J();
        C0298j4 c0298j4P = p();
        ArrayList arrayList = this.u;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            Animator animator = (Animator) obj;
            if (c0298j4P.containsKey(animator)) {
                J();
                if (animator != null) {
                    animator.addListener(new C0567rq(this, c0298j4P));
                    long j = this.c;
                    if (j >= 0) {
                        animator.setDuration(j);
                    }
                    long j2 = this.b;
                    if (j2 >= 0) {
                        animator.setStartDelay(animator.getStartDelay() + j2);
                    }
                    TimeInterpolator timeInterpolator = this.d;
                    if (timeInterpolator != null) {
                        animator.setInterpolator(timeInterpolator);
                    }
                    animator.addListener(new C0603t0(4, this));
                    animator.start();
                }
            }
        }
        this.u.clear();
        m();
    }

    public void C(long j, long j2) {
        long j3 = this.w;
        boolean z2 = j < j2;
        if ((j2 < 0 && j >= 0) || (j2 > j3 && j <= j3)) {
            this.r = false;
            w(this, C0579s7.b, z2);
        }
        ArrayList arrayList = this.n;
        int size = arrayList.size();
        Animator[] animatorArr = (Animator[]) arrayList.toArray(this.o);
        this.o = y;
        for (int i = 0; i < size; i++) {
            Animator animator = animatorArr[i];
            animatorArr[i] = null;
            AbstractC0629tq.b(animator, Math.min(Math.max(0L, j), AbstractC0629tq.a(animator)));
        }
        this.o = animatorArr;
        if ((j <= j3 || j2 > j3) && (j >= 0 || j2 < 0)) {
            return;
        }
        if (j > j3) {
            this.r = true;
        }
        w(this, C0579s7.c, z2);
    }

    public void D(long j) {
        this.c = j;
    }

    public void F(TimeInterpolator timeInterpolator) {
        this.d = timeInterpolator;
    }

    public void G(Pe pe) {
        if (pe == null) {
            this.v = A;
        } else {
            this.v = pe;
        }
    }

    public void I(long j) {
        this.b = j;
    }

    public final void J() {
        if (this.p == 0) {
            w(this, C0579s7.b, false);
            this.r = false;
        }
        this.p++;
    }

    public String K(String str) {
        StringBuilder sb = new StringBuilder(str);
        sb.append(getClass().getSimpleName());
        sb.append("@");
        sb.append(Integer.toHexString(hashCode()));
        sb.append(": ");
        if (this.c != -1) {
            sb.append("dur(");
            sb.append(this.c);
            sb.append(") ");
        }
        if (this.b != -1) {
            sb.append("dly(");
            sb.append(this.b);
            sb.append(") ");
        }
        if (this.d != null) {
            sb.append("interp(");
            sb.append(this.d);
            sb.append(") ");
        }
        ArrayList arrayList = this.e;
        int size = arrayList.size();
        ArrayList arrayList2 = this.f;
        if (size > 0 || arrayList2.size() > 0) {
            sb.append("tgts(");
            if (arrayList.size() > 0) {
                for (int i = 0; i < arrayList.size(); i++) {
                    if (i > 0) {
                        sb.append(", ");
                    }
                    sb.append(arrayList.get(i));
                }
            }
            if (arrayList2.size() > 0) {
                for (int i2 = 0; i2 < arrayList2.size(); i2++) {
                    if (i2 > 0) {
                        sb.append(", ");
                    }
                    sb.append(arrayList2.get(i2));
                }
            }
            sb.append(")");
        }
        return sb.toString();
    }

    public void a(InterfaceC0691vq interfaceC0691vq) {
        if (this.t == null) {
            this.t = new ArrayList();
        }
        this.t.add(interfaceC0691vq);
    }

    public void c() {
        ArrayList arrayList = this.n;
        int size = arrayList.size();
        Animator[] animatorArr = (Animator[]) arrayList.toArray(this.o);
        this.o = y;
        for (int i = size - 1; i >= 0; i--) {
            Animator animator = animatorArr[i];
            animatorArr[i] = null;
            animator.cancel();
        }
        this.o = animatorArr;
        w(this, C0579s7.d, false);
    }

    public abstract void d(Eq eq);

    public final void e(View view, boolean z2) {
        if (view == null) {
            return;
        }
        view.getId();
        if (view.getParent() instanceof ViewGroup) {
            Eq eq = new Eq(view);
            if (z2) {
                g(eq);
            } else {
                d(eq);
            }
            eq.c.add(this);
            f(eq);
            if (z2) {
                b(this.g, view, eq);
            } else {
                b(this.h, view, eq);
            }
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                e(viewGroup.getChildAt(i), z2);
            }
        }
    }

    public abstract void g(Eq eq);

    public final void h(ViewGroup viewGroup, boolean z2) {
        i(z2);
        ArrayList arrayList = this.e;
        int size = arrayList.size();
        ArrayList arrayList2 = this.f;
        if (size <= 0 && arrayList2.size() <= 0) {
            e(viewGroup, z2);
            return;
        }
        for (int i = 0; i < arrayList.size(); i++) {
            View viewFindViewById = viewGroup.findViewById(((Integer) arrayList.get(i)).intValue());
            if (viewFindViewById != null) {
                Eq eq = new Eq(viewFindViewById);
                if (z2) {
                    g(eq);
                } else {
                    d(eq);
                }
                eq.c.add(this);
                f(eq);
                if (z2) {
                    b(this.g, viewFindViewById, eq);
                } else {
                    b(this.h, viewFindViewById, eq);
                }
            }
        }
        for (int i2 = 0; i2 < arrayList2.size(); i2++) {
            View view = (View) arrayList2.get(i2);
            Eq eq2 = new Eq(view);
            if (z2) {
                g(eq2);
            } else {
                d(eq2);
            }
            eq2.c.add(this);
            f(eq2);
            if (z2) {
                b(this.g, view, eq2);
            } else {
                b(this.h, view, eq2);
            }
        }
    }

    public final void i(boolean z2) {
        if (z2) {
            ((C0298j4) this.g.a).clear();
            ((SparseArray) this.g.b).clear();
            ((C0620th) this.g.c).a();
        } else {
            ((C0298j4) this.h.a).clear();
            ((SparseArray) this.h.b).clear();
            ((C0620th) this.h.c).a();
        }
    }

    @Override // 
    /* JADX INFO: renamed from: j, reason: merged with bridge method [inline-methods] */
    public AbstractC0722wq clone() {
        try {
            AbstractC0722wq abstractC0722wq = (AbstractC0722wq) super.clone();
            abstractC0722wq.u = new ArrayList();
            abstractC0722wq.g = new Xn(8);
            abstractC0722wq.h = new Xn(8);
            abstractC0722wq.k = null;
            abstractC0722wq.l = null;
            abstractC0722wq.s = this;
            abstractC0722wq.t = null;
            return abstractC0722wq;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public Animator k(ViewGroup viewGroup, Eq eq, Eq eq2) {
        return null;
    }

    public void l(ViewGroup viewGroup, Xn xn, Xn xn2, ArrayList arrayList, ArrayList arrayList2) {
        int i;
        int i2;
        View view;
        Eq eq;
        Animator animator;
        Eq eq2;
        C0298j4 c0298j4P = p();
        SparseIntArray sparseIntArray = new SparseIntArray();
        int size = arrayList.size();
        o().getClass();
        int i3 = 0;
        while (i3 < size) {
            Eq eq3 = (Eq) arrayList.get(i3);
            Eq eq4 = (Eq) arrayList2.get(i3);
            if (eq3 != null && !eq3.c.contains(this)) {
                eq3 = null;
            }
            if (eq4 != null && !eq4.c.contains(this)) {
                eq4 = null;
            }
            if ((eq3 != null || eq4 != null) && (eq3 == null || eq4 == null || t(eq3, eq4))) {
                Animator animatorK = k(viewGroup, eq3, eq4);
                if (animatorK != null) {
                    String str = this.a;
                    if (eq4 != null) {
                        String[] strArrQ = q();
                        view = eq4.b;
                        if (strArrQ != null && strArrQ.length > 0) {
                            eq2 = new Eq(view);
                            Eq eq5 = (Eq) ((C0298j4) xn2.a).get(view);
                            i = size;
                            if (eq5 != null) {
                                int i4 = 0;
                                while (i4 < strArrQ.length) {
                                    HashMap map = eq2.a;
                                    int i5 = i3;
                                    String str2 = strArrQ[i4];
                                    map.put(str2, eq5.a.get(str2));
                                    i4++;
                                    i3 = i5;
                                }
                            }
                            i2 = i3;
                            int i6 = c0298j4P.c;
                            int i7 = 0;
                            while (true) {
                                if (i7 >= i6) {
                                    animator = animatorK;
                                    break;
                                }
                                C0598sq c0598sq = (C0598sq) c0298j4P.get((Animator) c0298j4P.f(i7));
                                if (c0598sq.c != null && c0598sq.a == view && c0598sq.b.equals(str) && c0598sq.c.equals(eq2)) {
                                    animator = null;
                                    break;
                                }
                                i7++;
                            }
                        } else {
                            i = size;
                            i2 = i3;
                            animator = animatorK;
                            eq2 = null;
                        }
                        animatorK = animator;
                        eq = eq2;
                    } else {
                        i = size;
                        i2 = i3;
                        view = eq3.b;
                        eq = null;
                    }
                    if (animatorK != null) {
                        WindowId windowId = viewGroup.getWindowId();
                        C0598sq c0598sq2 = new C0598sq();
                        c0598sq2.a = view;
                        c0598sq2.b = str;
                        c0598sq2.c = eq;
                        c0598sq2.d = windowId;
                        c0598sq2.e = this;
                        c0598sq2.f = animatorK;
                        c0298j4P.put(animatorK, c0598sq2);
                        this.u.add(animatorK);
                    }
                }
                i3 = i2 + 1;
                size = i;
            }
            i = size;
            i2 = i3;
            i3 = i2 + 1;
            size = i;
        }
        if (sparseIntArray.size() != 0) {
            for (int i8 = 0; i8 < sparseIntArray.size(); i8++) {
                C0598sq c0598sq3 = (C0598sq) c0298j4P.get((Animator) this.u.get(sparseIntArray.keyAt(i8)));
                c0598sq3.f.setStartDelay(c0598sq3.f.getStartDelay() + (((long) sparseIntArray.valueAt(i8)) - Long.MAX_VALUE));
            }
        }
    }

    public final void m() {
        int i = this.p - 1;
        this.p = i;
        if (i == 0) {
            w(this, C0579s7.c, false);
            for (int i2 = 0; i2 < ((C0620th) this.g.c).e(); i2++) {
                View view = (View) ((C0620th) this.g.c).f(i2);
                if (view != null) {
                    view.setHasTransientState(false);
                }
            }
            for (int i3 = 0; i3 < ((C0620th) this.h.c).e(); i3++) {
                View view2 = (View) ((C0620th) this.h.c).f(i3);
                if (view2 != null) {
                    view2.setHasTransientState(false);
                }
            }
            this.r = true;
        }
    }

    public final Eq n(View view, boolean z2) {
        C0638u4 c0638u4 = this.i;
        if (c0638u4 != null) {
            return c0638u4.n(view, z2);
        }
        ArrayList arrayList = z2 ? this.k : this.l;
        if (arrayList == null) {
            return null;
        }
        int size = arrayList.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                i = -1;
                break;
            }
            Eq eq = (Eq) arrayList.get(i);
            if (eq == null) {
                return null;
            }
            if (eq.b == view) {
                break;
            }
            i++;
        }
        if (i >= 0) {
            return (Eq) (z2 ? this.l : this.k).get(i);
        }
        return null;
    }

    public final AbstractC0722wq o() {
        C0638u4 c0638u4 = this.i;
        return c0638u4 != null ? c0638u4.o() : this;
    }

    public String[] q() {
        return null;
    }

    public final Eq r(View view, boolean z2) {
        C0638u4 c0638u4 = this.i;
        if (c0638u4 != null) {
            return c0638u4.r(view, z2);
        }
        return (Eq) ((C0298j4) (z2 ? this.g : this.h).a).get(view);
    }

    public boolean s() {
        return !this.n.isEmpty();
    }

    public boolean t(Eq eq, Eq eq2) {
        if (eq != null && eq2 != null) {
            String[] strArrQ = q();
            if (strArrQ != null) {
                for (String str : strArrQ) {
                    if (v(eq, eq2, str)) {
                        return true;
                    }
                }
            } else {
                Iterator it = eq.a.keySet().iterator();
                while (it.hasNext()) {
                    if (v(eq, eq2, (String) it.next())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public final String toString() {
        return K("");
    }

    public final boolean u(View view) {
        int id = view.getId();
        ArrayList arrayList = this.e;
        int size = arrayList.size();
        ArrayList arrayList2 = this.f;
        return (size == 0 && arrayList2.size() == 0) || arrayList.contains(Integer.valueOf(id)) || arrayList2.contains(view);
    }

    public final void w(AbstractC0722wq abstractC0722wq, C0579s7 c0579s7, boolean z2) {
        AbstractC0722wq abstractC0722wq2 = this.s;
        if (abstractC0722wq2 != null) {
            abstractC0722wq2.w(abstractC0722wq, c0579s7, z2);
        }
        ArrayList arrayList = this.t;
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        int size = this.t.size();
        InterfaceC0691vq[] interfaceC0691vqArr = this.m;
        if (interfaceC0691vqArr == null) {
            interfaceC0691vqArr = new InterfaceC0691vq[size];
        }
        this.m = null;
        InterfaceC0691vq[] interfaceC0691vqArr2 = (InterfaceC0691vq[]) this.t.toArray(interfaceC0691vqArr);
        for (int i = 0; i < size; i++) {
            InterfaceC0691vq interfaceC0691vq = interfaceC0691vqArr2[i];
            switch (c0579s7.a) {
                case 19:
                    interfaceC0691vq.a(abstractC0722wq);
                    break;
                case 20:
                    interfaceC0691vq.b(abstractC0722wq);
                    break;
                case 21:
                    interfaceC0691vq.e(abstractC0722wq);
                    break;
                case 22:
                    interfaceC0691vq.c();
                    break;
                default:
                    interfaceC0691vq.d();
                    break;
            }
            interfaceC0691vqArr2[i] = null;
        }
        this.m = interfaceC0691vqArr2;
    }

    public void x(ViewGroup viewGroup) {
        if (this.r) {
            return;
        }
        ArrayList arrayList = this.n;
        int size = arrayList.size();
        Animator[] animatorArr = (Animator[]) arrayList.toArray(this.o);
        this.o = y;
        for (int i = size - 1; i >= 0; i--) {
            Animator animator = animatorArr[i];
            animatorArr[i] = null;
            animator.pause();
        }
        this.o = animatorArr;
        w(this, C0579s7.e, false);
        this.q = true;
    }

    public void y() {
        C0298j4 c0298j4P = p();
        this.w = 0L;
        for (int i = 0; i < this.u.size(); i++) {
            Animator animator = (Animator) this.u.get(i);
            C0598sq c0598sq = (C0598sq) c0298j4P.get(animator);
            if (animator != null && c0598sq != null) {
                long j = this.c;
                Animator animator2 = c0598sq.f;
                if (j >= 0) {
                    animator2.setDuration(j);
                }
                long j2 = this.b;
                if (j2 >= 0) {
                    animator2.setStartDelay(animator2.getStartDelay() + j2);
                }
                TimeInterpolator timeInterpolator = this.d;
                if (timeInterpolator != null) {
                    animator2.setInterpolator(timeInterpolator);
                }
                this.n.add(animator);
                this.w = Math.max(this.w, AbstractC0629tq.a(animator));
            }
        }
        this.u.clear();
    }

    public AbstractC0722wq z(InterfaceC0691vq interfaceC0691vq) {
        AbstractC0722wq abstractC0722wq;
        ArrayList arrayList = this.t;
        if (arrayList != null) {
            if (!arrayList.remove(interfaceC0691vq) && (abstractC0722wq = this.s) != null) {
                abstractC0722wq.z(interfaceC0691vq);
            }
            if (this.t.size() == 0) {
                this.t = null;
            }
        }
        return this;
    }

    public void H() {
    }

    public void E(An an) {
    }

    public void f(Eq eq) {
    }
}
