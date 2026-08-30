package rikka.shizuku;

import android.animation.AnimatorSet;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes.dex */
public final class E9 {
    public final ViewGroup a;
    public final ArrayList b = new ArrayList();
    public final ArrayList c = new ArrayList();
    public boolean d;
    public boolean e;
    public boolean f;

    public E9(ViewGroup viewGroup) {
        this.a = viewGroup;
    }

    public static boolean i(ArrayList arrayList) {
        boolean z;
        AbstractC0720wo abstractC0720wo;
        int size = arrayList.size();
        int i = 0;
        loop0: while (true) {
            z = true;
            while (true) {
                if (i >= size) {
                    break loop0;
                }
                Object obj = arrayList.get(i);
                i++;
                C0751xo c0751xo = (C0751xo) obj;
                if (!c0751xo.k.isEmpty()) {
                    ArrayList arrayList2 = c0751xo.k;
                    if (arrayList2 != null && arrayList2.isEmpty()) {
                        break;
                    }
                    int size2 = arrayList2.size();
                    int i2 = 0;
                    do {
                        if (i2 >= size2) {
                            break;
                        }
                        Object obj2 = arrayList2.get(i2);
                        i2++;
                        abstractC0720wo = (AbstractC0720wo) obj2;
                        abstractC0720wo.getClass();
                    } while (abstractC0720wo instanceof A9);
                }
                z = false;
            }
        }
        if (z) {
            ArrayList arrayList3 = new ArrayList();
            int size3 = arrayList.size();
            int i3 = 0;
            while (i3 < size3) {
                Object obj3 = arrayList.get(i3);
                i3++;
                C6.c0(arrayList3, ((C0751xo) obj3).k);
            }
            if (!arrayList3.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public final void a(C0751xo c0751xo) {
        if (c0751xo.i) {
            AbstractC0535qo.a(c0751xo.a, c0751xo.c.I(), this.a);
            c0751xo.i = false;
        }
    }

    public final void b(ArrayList arrayList, boolean z) {
        Object obj;
        Object next;
        if (Qc.J(2)) {
            Log.v("FragmentManager", "Collecting Effects");
        }
        Iterator it = arrayList.iterator();
        while (true) {
            obj = null;
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            C0751xo c0751xo = (C0751xo) next;
            View view = c0751xo.c.G;
            if (view.getAlpha() != 0.0f || view.getVisibility() != 0) {
                int visibility = view.getVisibility();
                if (visibility != 0) {
                    if (visibility != 4 && visibility != 8) {
                        throw new IllegalArgumentException(AbstractC0535qo.b(visibility, "Unknown visibility "));
                    }
                } else if (c0751xo.a != 2) {
                    break;
                }
            }
        }
        C0751xo c0751xo2 = (C0751xo) next;
        ListIterator listIterator = arrayList.listIterator(arrayList.size());
        while (listIterator.hasPrevious()) {
            Object objPrevious = listIterator.previous();
            C0751xo c0751xo3 = (C0751xo) objPrevious;
            View view2 = c0751xo3.c.G;
            if (view2.getAlpha() != 0.0f || view2.getVisibility() != 0) {
                int visibility2 = view2.getVisibility();
                if (visibility2 == 0) {
                    continue;
                } else if (visibility2 != 4 && visibility2 != 8) {
                    throw new IllegalArgumentException(AbstractC0535qo.b(visibility2, "Unknown visibility "));
                }
            }
            if (c0751xo3.a == 2) {
                obj = objPrevious;
                break;
            }
        }
        C0751xo c0751xo4 = (C0751xo) obj;
        if (Qc.J(2)) {
            Log.v("FragmentManager", "Executing operations from " + c0751xo2 + " to " + c0751xo4);
        }
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        if (arrayList.isEmpty()) {
            throw new NoSuchElementException("List is empty.");
        }
        Ac ac = ((C0751xo) arrayList.get(arrayList.size() - 1)).c;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj2 = arrayList.get(i);
            i++;
            C0801zc c0801zc = ((C0751xo) obj2).c.J;
            C0801zc c0801zc2 = ac.J;
            c0801zc.b = c0801zc2.b;
            c0801zc.c = c0801zc2.c;
            c0801zc.d = c0801zc2.d;
            c0801zc.e = c0801zc2.e;
        }
        int size2 = arrayList.size();
        int i2 = 0;
        while (i2 < size2) {
            Object obj3 = arrayList.get(i2);
            i2++;
            C0751xo c0751xo5 = (C0751xo) obj3;
            arrayList2.add(new C0767y9(c0751xo5, z));
            boolean z2 = !z ? c0751xo5 != c0751xo4 : c0751xo5 != c0751xo2;
            D9 d9 = new D9(1, c0751xo5);
            int i3 = c0751xo5.a;
            Ac ac2 = c0751xo5.c;
            if (i3 == 2) {
                if (z) {
                    C0801zc c0801zc3 = ac2.J;
                } else {
                    ac2.getClass();
                }
            } else if (z) {
                C0801zc c0801zc4 = ac2.J;
            } else {
                ac2.getClass();
            }
            if (c0751xo5.a == 2) {
                if (z) {
                    C0801zc c0801zc5 = ac2.J;
                } else {
                    C0801zc c0801zc6 = ac2.J;
                }
            }
            if (z2) {
                if (z) {
                    C0801zc c0801zc7 = ac2.J;
                } else {
                    ac2.getClass();
                }
            }
            arrayList3.add(d9);
            c0751xo5.d.add(new RunnableC0643u9(this, c0751xo5, 0));
        }
        ArrayList arrayList4 = new ArrayList();
        int size3 = arrayList3.size();
        int i4 = 0;
        while (i4 < size3) {
            Object obj4 = arrayList3.get(i4);
            i4++;
            if (!((D9) obj4).e()) {
                arrayList4.add(obj4);
            }
        }
        ArrayList arrayList5 = new ArrayList();
        int size4 = arrayList4.size();
        int i5 = 0;
        while (i5 < size4) {
            Object obj5 = arrayList4.get(i5);
            i5++;
            ((D9) obj5).getClass();
        }
        int size5 = arrayList5.size();
        int i6 = 0;
        while (i6 < size5) {
            Object obj6 = arrayList5.get(i6);
            i6++;
            ((D9) obj6).getClass();
        }
        ArrayList arrayList6 = new ArrayList();
        ArrayList arrayList7 = new ArrayList();
        int size6 = arrayList2.size();
        int i7 = 0;
        while (i7 < size6) {
            Object obj7 = arrayList2.get(i7);
            i7++;
            C6.c0(arrayList7, ((C0751xo) ((C0767y9) obj7).b).k);
        }
        boolean zIsEmpty = arrayList7.isEmpty();
        int size7 = arrayList2.size();
        boolean z3 = false;
        int i8 = 0;
        while (i8 < size7) {
            Object obj8 = arrayList2.get(i8);
            i8++;
            C0767y9 c0767y9 = (C0767y9) obj8;
            Context context = this.a.getContext();
            C0751xo c0751xo6 = (C0751xo) c0767y9.b;
            R2 r2F = c0767y9.f(context);
            if (r2F != null) {
                if (((AnimatorSet) r2F.c) == null) {
                    arrayList6.add(c0767y9);
                } else {
                    Ac ac3 = c0751xo6.c;
                    if (c0751xo6.k.isEmpty()) {
                        if (c0751xo6.a == 3) {
                            c0751xo6.i = false;
                        }
                        c0751xo6.j.add(new A9(c0767y9));
                        z3 = true;
                    } else if (Qc.J(2)) {
                        Log.v("FragmentManager", "Ignoring Animator set on " + ac3 + " as this Fragment was involved in a Transition.");
                    }
                }
            }
        }
        int size8 = arrayList6.size();
        int i9 = 0;
        while (i9 < size8) {
            Object obj9 = arrayList6.get(i9);
            i9++;
            C0767y9 c0767y10 = (C0767y9) obj9;
            C0751xo c0751xo7 = (C0751xo) c0767y10.b;
            Ac ac4 = c0751xo7.c;
            if (zIsEmpty) {
                if (!z3) {
                    c0751xo7.j.add(new C0736x9(c0767y10));
                } else if (Qc.J(2)) {
                    Log.v("FragmentManager", "Ignoring Animation set on " + ac4 + " as Animations cannot run alongside Animators.");
                }
            } else if (Qc.J(2)) {
                Log.v("FragmentManager", "Ignoring Animation set on " + ac4 + " as Animations cannot run alongside Transitions.");
            }
        }
    }

    public final void c(ArrayList arrayList) {
        ArrayList arrayList2 = new ArrayList();
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            C6.c0(arrayList2, ((C0751xo) obj).k);
        }
        List listD0 = AbstractC0671v6.d0(AbstractC0671v6.e0(arrayList2));
        int size2 = listD0.size();
        for (int i2 = 0; i2 < size2; i2++) {
            ((AbstractC0720wo) listD0.get(i2)).b(this.a);
        }
        int size3 = arrayList.size();
        for (int i3 = 0; i3 < size3; i3++) {
            a((C0751xo) arrayList.get(i3));
        }
        List listD1 = AbstractC0671v6.d0(arrayList);
        int size4 = listD1.size();
        for (int i4 = 0; i4 < size4; i4++) {
            C0751xo c0751xo = (C0751xo) listD1.get(i4);
            if (c0751xo.k.isEmpty()) {
                c0751xo.b();
            }
        }
    }

    public final void d(int i, int i2, Xc xc) {
        synchronized (this.b) {
            try {
                C0751xo c0751xoF = f(xc.c);
                if (c0751xoF == null) {
                    Ac ac = xc.c;
                    c0751xoF = (ac.m || ac.l) ? g(ac) : null;
                }
                if (c0751xoF != null) {
                    c0751xoF.d(i, i2);
                    return;
                }
                C0751xo c0751xo = new C0751xo(i, i2, xc);
                this.b.add(c0751xo);
                c0751xo.d.add(new RunnableC0643u9(this, c0751xo, 1));
                c0751xo.d.add(new RunnableC0643u9(this, c0751xo, 2));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void e() {
        boolean z;
        if (this.f) {
            return;
        }
        if (!this.a.isAttachedToWindow()) {
            h();
            this.e = false;
            return;
        }
        synchronized (this.b) {
            try {
                ArrayList arrayList = new ArrayList(this.c);
                this.c.clear();
                int size = arrayList.size();
                int i = 0;
                while (true) {
                    z = true;
                    if (i >= size) {
                        break;
                    }
                    Object obj = arrayList.get(i);
                    i++;
                    C0751xo c0751xo = (C0751xo) obj;
                    if (this.b.isEmpty() || !c0751xo.c.m) {
                        z = false;
                    }
                    c0751xo.g = z;
                }
                int size2 = arrayList.size();
                int i2 = 0;
                while (i2 < size2) {
                    Object obj2 = arrayList.get(i2);
                    i2++;
                    C0751xo c0751xo2 = (C0751xo) obj2;
                    if (this.d) {
                        if (Qc.J(2)) {
                            Log.v("FragmentManager", "SpecialEffectsController: Completing non-seekable operation " + c0751xo2);
                        }
                        c0751xo2.b();
                    } else {
                        if (Qc.J(2)) {
                            Log.v("FragmentManager", "SpecialEffectsController: Cancelling operation " + c0751xo2);
                        }
                        c0751xo2.a(this.a);
                    }
                    this.d = false;
                    if (!c0751xo2.f) {
                        this.c.add(c0751xo2);
                    }
                }
                if (!this.b.isEmpty()) {
                    k();
                    ArrayList arrayList2 = new ArrayList(this.b);
                    if (arrayList2.isEmpty()) {
                        return;
                    }
                    this.b.clear();
                    this.c.addAll(arrayList2);
                    if (Qc.J(2)) {
                        Log.v("FragmentManager", "SpecialEffectsController: Executing pending operations");
                    }
                    b(arrayList2, this.e);
                    boolean zI = i(arrayList2);
                    int size3 = arrayList2.size();
                    int i3 = 0;
                    boolean z2 = true;
                    while (i3 < size3) {
                        Object obj3 = arrayList2.get(i3);
                        i3++;
                        if (!((C0751xo) obj3).c.m) {
                            z2 = false;
                        }
                    }
                    if (!z2 || zI) {
                        z = false;
                    }
                    this.d = z;
                    if (Qc.J(2)) {
                        Log.v("FragmentManager", "SpecialEffectsController: Operation seekable = " + zI + " \ntransition = " + z2);
                    }
                    if (!z2) {
                        j(arrayList2);
                        c(arrayList2);
                    } else if (zI) {
                        j(arrayList2);
                        int size4 = arrayList2.size();
                        for (int i4 = 0; i4 < size4; i4++) {
                            a((C0751xo) arrayList2.get(i4));
                        }
                    }
                    this.e = false;
                    if (Qc.J(2)) {
                        Log.v("FragmentManager", "SpecialEffectsController: Finished executing pending operations");
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final C0751xo f(Ac ac) {
        Object obj;
        ArrayList arrayList = this.b;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            obj = arrayList.get(i);
            i++;
            C0751xo c0751xo = (C0751xo) obj;
            if (AbstractC0046as.c(c0751xo.c, ac) && !c0751xo.e) {
                return (C0751xo) obj;
            }
        }
        obj = null;
        return (C0751xo) obj;
    }

    public final C0751xo g(Ac ac) {
        Object obj;
        ArrayList arrayList = this.c;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            obj = arrayList.get(i);
            i++;
            C0751xo c0751xo = (C0751xo) obj;
            if (AbstractC0046as.c(c0751xo.c, ac) && !c0751xo.e) {
                return (C0751xo) obj;
            }
        }
        obj = null;
        return (C0751xo) obj;
    }

    public final void h() {
        String str;
        String str2;
        if (Qc.J(2)) {
            Log.v("FragmentManager", "SpecialEffectsController: Forcing all operations to complete");
        }
        boolean zIsAttachedToWindow = this.a.isAttachedToWindow();
        synchronized (this.b) {
            try {
                k();
                j(this.b);
                ArrayList arrayList = new ArrayList(this.c);
                int size = arrayList.size();
                int i = 0;
                int i2 = 0;
                while (i2 < size) {
                    Object obj = arrayList.get(i2);
                    i2++;
                    ((C0751xo) obj).g = false;
                }
                int size2 = arrayList.size();
                int i3 = 0;
                while (i3 < size2) {
                    Object obj2 = arrayList.get(i3);
                    i3++;
                    C0751xo c0751xo = (C0751xo) obj2;
                    if (Qc.J(2)) {
                        if (zIsAttachedToWindow) {
                            str2 = "";
                        } else {
                            str2 = "Container " + this.a + " is not attached to window. ";
                        }
                        Log.v("FragmentManager", "SpecialEffectsController: " + str2 + "Cancelling running operation " + c0751xo);
                    }
                    c0751xo.a(this.a);
                }
                ArrayList arrayList2 = new ArrayList(this.b);
                int size3 = arrayList2.size();
                int i4 = 0;
                while (i4 < size3) {
                    Object obj3 = arrayList2.get(i4);
                    i4++;
                    ((C0751xo) obj3).g = false;
                }
                int size4 = arrayList2.size();
                while (i < size4) {
                    Object obj4 = arrayList2.get(i);
                    i++;
                    C0751xo c0751xo2 = (C0751xo) obj4;
                    if (Qc.J(2)) {
                        if (zIsAttachedToWindow) {
                            str = "";
                        } else {
                            str = "Container " + this.a + " is not attached to window. ";
                        }
                        Log.v("FragmentManager", "SpecialEffectsController: " + str + "Cancelling pending operation " + c0751xo2);
                    }
                    c0751xo2.a(this.a);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void j(ArrayList arrayList) {
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            C0751xo c0751xo = (C0751xo) arrayList.get(i);
            if (!c0751xo.h) {
                c0751xo.h = true;
                int i2 = c0751xo.b;
                Xc xc = c0751xo.l;
                if (i2 == 2) {
                    Ac ac = xc.c;
                    View viewFindFocus = ac.G.findFocus();
                    if (viewFindFocus != null) {
                        ac.f().k = viewFindFocus;
                        if (Qc.J(2)) {
                            Log.v("FragmentManager", "requestFocus: Saved focused view " + viewFindFocus + " for Fragment " + ac);
                        }
                    }
                    View viewI = c0751xo.c.I();
                    if (viewI.getParent() == null) {
                        if (Qc.J(2)) {
                            Log.v("FragmentManager", "Adding fragment " + ac + " view " + viewI + " to container in onStart");
                        }
                        xc.b();
                        viewI.setAlpha(0.0f);
                    }
                    if (viewI.getAlpha() == 0.0f && viewI.getVisibility() == 0) {
                        if (Qc.J(2)) {
                            Log.v("FragmentManager", "Making view " + viewI + " INVISIBLE in onStart");
                        }
                        viewI.setVisibility(4);
                    }
                    C0801zc c0801zc = ac.J;
                    viewI.setAlpha(c0801zc == null ? 1.0f : c0801zc.j);
                    if (Qc.J(2)) {
                        StringBuilder sb = new StringBuilder("Setting view alpha to ");
                        C0801zc c0801zc2 = ac.J;
                        sb.append(c0801zc2 != null ? c0801zc2.j : 1.0f);
                        sb.append(" in onStart");
                        Log.v("FragmentManager", sb.toString());
                    }
                } else if (i2 == 3) {
                    Ac ac2 = xc.c;
                    View viewI2 = ac2.I();
                    if (Qc.J(2)) {
                        Log.v("FragmentManager", "Clearing focus " + viewI2.findFocus() + " on view " + viewI2 + " for Fragment " + ac2);
                    }
                    viewI2.clearFocus();
                }
            }
        }
        ArrayList arrayList2 = new ArrayList();
        int size2 = arrayList.size();
        int i3 = 0;
        while (i3 < size2) {
            Object obj = arrayList.get(i3);
            i3++;
            C6.c0(arrayList2, ((C0751xo) obj).k);
        }
        List listD0 = AbstractC0671v6.d0(AbstractC0671v6.e0(arrayList2));
        int size3 = listD0.size();
        for (int i4 = 0; i4 < size3; i4++) {
            AbstractC0720wo abstractC0720wo = (AbstractC0720wo) listD0.get(i4);
            if (!abstractC0720wo.a) {
                abstractC0720wo.d(this.a);
            }
            abstractC0720wo.a = true;
        }
    }

    public final void k() {
        ArrayList arrayList = this.b;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            C0751xo c0751xo = (C0751xo) obj;
            int i2 = 2;
            if (c0751xo.b == 2) {
                int visibility = c0751xo.c.I().getVisibility();
                if (visibility != 0) {
                    i2 = 4;
                    if (visibility != 4) {
                        if (visibility != 8) {
                            throw new IllegalArgumentException(AbstractC0535qo.b(visibility, "Unknown visibility "));
                        }
                        i2 = 3;
                    }
                }
                c0751xo.d(i2, 1);
            }
        }
    }
}
