package rikka.shizuku;

import android.util.Log;
import android.view.ViewGroup;
import java.util.ArrayList;

/* JADX INFO: renamed from: rikka.shizuku.xo, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0751xo {
    public int a;
    public int b;
    public final Ac c;
    public final ArrayList d;
    public boolean e;
    public boolean f;
    public boolean g;
    public boolean h;
    public boolean i;
    public final ArrayList j;
    public final ArrayList k;
    public final Xc l;

    public C0751xo(int i, int i2, Xc xc) {
        Ac ac = xc.c;
        this.a = i;
        this.b = i2;
        this.c = ac;
        this.d = new ArrayList();
        this.i = true;
        ArrayList arrayList = new ArrayList();
        this.j = arrayList;
        this.k = arrayList;
        this.l = xc;
    }

    public final void a(ViewGroup viewGroup) {
        this.h = false;
        if (this.e) {
            return;
        }
        this.e = true;
        if (this.j.isEmpty()) {
            b();
            return;
        }
        for (AbstractC0720wo abstractC0720wo : AbstractC0671v6.d0(this.k)) {
            if (!abstractC0720wo.b) {
                abstractC0720wo.a(viewGroup);
            }
            abstractC0720wo.b = true;
        }
    }

    public final void b() {
        this.h = false;
        if (!this.f) {
            if (Qc.J(2)) {
                Log.v("FragmentManager", "SpecialEffectsController: " + this + " has called complete.");
            }
            this.f = true;
            ArrayList arrayList = this.d;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                ((Runnable) obj).run();
            }
        }
        this.c.m = false;
        this.l.k();
    }

    public final void c(AbstractC0720wo abstractC0720wo) {
        ArrayList arrayList = this.j;
        if (arrayList.remove(abstractC0720wo) && arrayList.isEmpty()) {
            b();
        }
    }

    public final void d(int i, int i2) {
        int iJ = AbstractC0535qo.j(i2);
        Ac ac = this.c;
        if (iJ == 0) {
            if (this.a != 1) {
                if (Qc.J(2)) {
                    Log.v("FragmentManager", "SpecialEffectsController: For fragment " + ac + " mFinalState = " + AbstractC0535qo.m(this.a) + " -> " + AbstractC0535qo.m(i) + '.');
                }
                this.a = i;
                return;
            }
            return;
        }
        if (iJ == 1) {
            if (this.a == 1) {
                if (Qc.J(2)) {
                    Log.v("FragmentManager", "SpecialEffectsController: For fragment " + ac + " mFinalState = REMOVED -> VISIBLE. mLifecycleImpact = " + AbstractC0535qo.l(this.b) + " to ADDING.");
                }
                this.a = 2;
                this.b = 2;
                this.i = true;
                return;
            }
            return;
        }
        if (iJ != 2) {
            return;
        }
        if (Qc.J(2)) {
            Log.v("FragmentManager", "SpecialEffectsController: For fragment " + ac + " mFinalState = " + AbstractC0535qo.m(this.a) + " -> REMOVED. mLifecycleImpact  = " + AbstractC0535qo.l(this.b) + " to REMOVING.");
        }
        this.a = 1;
        this.b = 3;
        this.i = true;
    }

    public final String toString() {
        StringBuilder sbE = AbstractC0535qo.e("Operation {", Integer.toHexString(System.identityHashCode(this)), "} {finalState = ");
        sbE.append(AbstractC0535qo.m(this.a));
        sbE.append(" lifecycleImpact = ");
        sbE.append(AbstractC0535qo.l(this.b));
        sbE.append(" fragment = ");
        sbE.append(this.c);
        sbE.append('}');
        return sbE.toString();
    }
}
