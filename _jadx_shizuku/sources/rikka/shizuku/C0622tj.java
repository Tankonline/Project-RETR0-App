package rikka.shizuku;

import android.os.Build;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.ListIterator;
import java.util.Objects;

/* JADX INFO: renamed from: rikka.shizuku.tj, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0622tj {
    public final Runnable a;
    public final C0084c4 b = new C0084c4();
    public Ic c;
    public final OnBackInvokedCallback d;
    public OnBackInvokedDispatcher e;
    public boolean f;
    public boolean g;

    public C0622tj(Runnable runnable) {
        this.a = runnable;
        int i = Build.VERSION.SDK_INT;
        if (i >= 33) {
            this.d = i >= 34 ? C0499pj.a.a(new C0375lj(this, 0), new C0375lj(this, 1), new C0406mj(this, 0), new C0406mj(this, 1)) : C0437nj.a.a(new C0406mj(this, 2));
        }
    }

    public final void a() {
        Object objPrevious;
        Ic ic = this.c;
        if (ic == null) {
            C0084c4 c0084c4 = this.b;
            c0084c4.getClass();
            ListIterator listIterator = c0084c4.listIterator(c0084c4.c);
            do {
                if (!listIterator.hasPrevious()) {
                    objPrevious = null;
                    break;
                }
                objPrevious = listIterator.previous();
            } while (!((Ic) objPrevious).a);
            ic = (Ic) objPrevious;
        }
        this.c = null;
        if (ic == null) {
            this.a.run();
            return;
        }
        boolean zJ = Qc.J(3);
        Qc qc = ic.d;
        if (zJ) {
            Objects.toString(qc);
        }
        qc.i = true;
        qc.z(true);
        int i = 0;
        qc.i = false;
        L4 l4 = qc.h;
        Ic ic2 = qc.j;
        if (l4 == null) {
            if (ic2.a) {
                Qc.J(3);
                qc.R();
                return;
            } else {
                Qc.J(3);
                qc.g.a();
                return;
            }
        }
        ArrayList arrayList = qc.n;
        if (!arrayList.isEmpty()) {
            LinkedHashSet linkedHashSet = new LinkedHashSet(Qc.E(qc.h));
            int size = arrayList.size();
            int i2 = 0;
            while (i2 < size) {
                Object obj = arrayList.get(i2);
                i2++;
                if (obj != null) {
                    throw new ClassCastException();
                }
                Iterator it = linkedHashSet.iterator();
                if (it.hasNext()) {
                    throw null;
                }
            }
        }
        ArrayList arrayList2 = qc.h.a;
        int size2 = arrayList2.size();
        int i3 = 0;
        while (i3 < size2) {
            Object obj2 = arrayList2.get(i3);
            i3++;
            Ac ac = ((C0062bd) obj2).b;
            if (ac != null) {
                ac.m = false;
            }
        }
        for (E9 e9 : qc.f(new ArrayList(Collections.singletonList(qc.h)), 0, 1)) {
            e9.getClass();
            Qc.J(3);
            ArrayList arrayList3 = e9.c;
            e9.j(arrayList3);
            e9.c(arrayList3);
        }
        ArrayList arrayList4 = qc.h.a;
        int size3 = arrayList4.size();
        while (i < size3) {
            Object obj3 = arrayList4.get(i);
            i++;
            Ac ac2 = ((C0062bd) obj3).b;
            if (ac2 != null && ac2.F == null) {
                qc.g(ac2).k();
            }
        }
        qc.h = null;
        qc.g0();
        if (Qc.J(3)) {
            boolean z = ic2.a;
            qc.toString();
        }
    }

    public final void b(boolean z) {
        OnBackInvokedDispatcher onBackInvokedDispatcher = this.e;
        OnBackInvokedCallback onBackInvokedCallback = this.d;
        if (onBackInvokedDispatcher == null || onBackInvokedCallback == null) {
            return;
        }
        C0437nj c0437nj = C0437nj.a;
        if (z && !this.f) {
            c0437nj.b(onBackInvokedDispatcher, 0, onBackInvokedCallback);
            this.f = true;
        } else {
            if (z || !this.f) {
                return;
            }
            c0437nj.c(onBackInvokedDispatcher, onBackInvokedCallback);
            this.f = false;
        }
    }

    public final void c() {
        boolean z = this.g;
        boolean z2 = false;
        C0084c4 c0084c4 = this.b;
        if (c0084c4 == null || !c0084c4.isEmpty()) {
            Iterator it = c0084c4.iterator();
            while (it.hasNext()) {
                if (((Ic) it.next()).a) {
                    z2 = true;
                    break;
                }
            }
        }
        this.g = z2;
        if (z2 == z || Build.VERSION.SDK_INT < 33) {
            return;
        }
        b(z2);
    }
}
