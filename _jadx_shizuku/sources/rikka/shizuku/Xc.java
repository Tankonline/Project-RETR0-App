package rikka.shizuku;

import android.content.res.Resources;
import android.os.BadParcelableException;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.fragment.app.FragmentContainerView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;
import java.util.WeakHashMap;
import moe.shizuku.privileged.api.R;
import rikka.html.text.style.BulletSpan;

/* JADX INFO: loaded from: classes.dex */
public final class Xc {
    public final R2 a;
    public final Xn b;
    public final Ac c;
    public boolean d = false;
    public int e = -1;

    public Xc(R2 r2, Xn xn, Ac ac) {
        this.a = r2;
        this.b = xn;
        this.c = ac;
    }

    public final void a() {
        boolean zJ = Qc.J(3);
        Ac ac = this.c;
        if (zJ) {
            Objects.toString(ac);
        }
        Bundle bundle = ac.b;
        if (bundle != null) {
            bundle.getBundle("savedInstanceState");
        }
        ac.v.Q();
        ac.a = 3;
        ac.E = false;
        ac.s();
        if (!ac.E) {
            throw new C0228gp("Fragment " + ac + " did not call through to super.onActivityCreated()");
        }
        if (Qc.J(3)) {
            ac.toString();
        }
        if (ac.G != null) {
            Bundle bundle2 = ac.b;
            Bundle bundle3 = bundle2 != null ? bundle2.getBundle("savedInstanceState") : null;
            SparseArray<Parcelable> sparseArray = ac.c;
            if (sparseArray != null) {
                ac.G.restoreHierarchyState(sparseArray);
                ac.c = null;
            }
            ac.E = false;
            ac.E(bundle3);
            if (!ac.E) {
                throw new C0228gp("Fragment " + ac + " did not call through to super.onViewStateRestored()");
            }
            if (ac.G != null) {
                ac.Q.c(EnumC0557rg.ON_CREATE);
            }
        }
        ac.b = null;
        Qc qc = ac.v;
        qc.H = false;
        qc.I = false;
        qc.O.g = false;
        qc.u(4);
        this.a.j(ac, false);
    }

    public final void b() {
        Ac ac;
        View view;
        View view2;
        int iIndexOfChild = -1;
        Ac ac2 = this.c;
        View view3 = ac2.F;
        while (true) {
            ac = null;
            if (view3 == null) {
                break;
            }
            Object tag = view3.getTag(R.id._0_resource_name_obfuscated_res_0x7f0900bd);
            Ac ac3 = tag instanceof Ac ? (Ac) tag : null;
            if (ac3 != null) {
                ac = ac3;
                break;
            } else {
                Object parent = view3.getParent();
                view3 = parent instanceof View ? (View) parent : null;
            }
        }
        Ac ac4 = ac2.w;
        if (ac != null && !ac.equals(ac4)) {
            int i = ac2.y;
            Zc zc = AbstractC0031ad.a;
            AbstractC0031ad.b(new Vc(ac2, "Attempting to nest fragment " + ac2 + " within the view of parent fragment " + ac + " via container with ID " + i + " without using parent's childFragmentManager"));
            AbstractC0031ad.a(ac2).getClass();
        }
        Xn xn = this.b;
        xn.getClass();
        ViewGroup viewGroup = ac2.F;
        if (viewGroup != null) {
            ArrayList arrayList = (ArrayList) xn.a;
            int iIndexOf = arrayList.indexOf(ac2);
            for (int i2 = iIndexOf - 1; i2 >= 0; i2--) {
                Ac ac5 = (Ac) arrayList.get(i2);
                if (ac5.F == viewGroup && (view2 = ac5.G) != null) {
                    iIndexOfChild = viewGroup.indexOfChild(view2) + 1;
                }
            }
            while (true) {
                iIndexOf++;
                if (iIndexOf >= arrayList.size()) {
                    break;
                }
                Ac ac6 = (Ac) arrayList.get(iIndexOf);
                if (ac6.F == viewGroup && (view = ac6.G) != null) {
                    iIndexOfChild = viewGroup.indexOfChild(view);
                    break;
                }
            }
        }
        ac2.F.addView(ac2.G, iIndexOfChild);
    }

    public final void c() {
        Xc xc;
        boolean zJ = Qc.J(3);
        Ac ac = this.c;
        if (zJ) {
            Objects.toString(ac);
        }
        Ac ac2 = ac.g;
        Xn xn = this.b;
        if (ac2 != null) {
            xc = (Xc) ((HashMap) xn.b).get(ac2.e);
            if (xc == null) {
                throw new IllegalStateException("Fragment " + ac + " declared target fragment " + ac.g + " that does not belong to this FragmentManager!");
            }
            ac.h = ac.g.e;
            ac.g = null;
        } else {
            String str = ac.h;
            if (str != null) {
                xc = (Xc) ((HashMap) xn.b).get(str);
                if (xc == null) {
                    throw new IllegalStateException("Fragment " + ac + " declared target fragment " + ac.h + " that does not belong to this FragmentManager!");
                }
            } else {
                xc = null;
            }
        }
        if (xc != null) {
            xc.k();
        }
        Qc qc = ac.t;
        ac.u = qc.w;
        ac.w = qc.y;
        R2 r2 = this.a;
        r2.p(ac, false);
        ArrayList arrayList = ac.T;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            Ac ac3 = ((C0739xc) obj).a;
            ac3.S.P();
            AbstractC0599sr.l(ac3);
            Bundle bundle = ac3.b;
            Bundle bundle2 = bundle != null ? bundle.getBundle("registryState") : null;
            Am am = (Am) ac3.S.b;
            if (!am.e) {
                am.a();
            }
            Bm bm = am.a;
            if (bm.e().c.a(EnumC0588sg.d)) {
                throw new IllegalStateException(("performRestore cannot be called when owner is " + bm.e().c).toString());
            }
            if (am.g) {
                throw new IllegalStateException("SavedStateRegistry was already restored.");
            }
            am.f = (bundle2 == null || !bundle2.containsKey("androidx.lifecycle.BundlableSavedStateRegistry.key")) ? null : X8.F("androidx.lifecycle.BundlableSavedStateRegistry.key", bundle2);
            am.g = true;
        }
        arrayList.clear();
        ac.v.b(ac.u, ac.c(), ac);
        ac.a = 0;
        ac.E = false;
        ac.u(ac.u.j);
        if (!ac.E) {
            throw new C0228gp("Fragment " + ac + " did not call through to super.onAttach()");
        }
        Iterator it = ac.t.p.iterator();
        while (it.hasNext()) {
            ((Uc) it.next()).a();
        }
        Qc qc2 = ac.v;
        qc2.H = false;
        qc2.I = false;
        qc2.O.g = false;
        qc2.u(0);
        r2.k(ac, false);
    }

    public final int d() {
        E9 e9;
        Ac ac = this.c;
        if (ac.t == null) {
            return ac.a;
        }
        int iMin = this.e;
        int iOrdinal = ac.O.ordinal();
        int i = 0;
        if (iOrdinal == 1) {
            iMin = Math.min(iMin, 0);
        } else if (iOrdinal == 2) {
            iMin = Math.min(iMin, 1);
        } else if (iOrdinal == 3) {
            iMin = Math.min(iMin, 5);
        } else if (iOrdinal != 4) {
            iMin = Math.min(iMin, -1);
        }
        if (ac.n) {
            if (ac.o) {
                iMin = Math.max(this.e, 2);
                View view = ac.G;
                if (view != null && view.getParent() == null) {
                    iMin = Math.min(iMin, 2);
                }
            } else {
                iMin = this.e < 4 ? Math.min(iMin, ac.a) : Math.min(iMin, 1);
            }
        }
        if (ac.p && ac.F == null) {
            iMin = Math.min(iMin, 4);
        }
        if (!ac.k) {
            iMin = Math.min(iMin, 1);
        }
        ViewGroup viewGroup = ac.F;
        if (viewGroup != null) {
            ac.l().H();
            Object tag = viewGroup.getTag(R.id._0_resource_name_obfuscated_res_0x7f09019c);
            if (tag instanceof E9) {
                e9 = (E9) tag;
            } else {
                e9 = new E9(viewGroup);
                viewGroup.setTag(R.id._0_resource_name_obfuscated_res_0x7f09019c, e9);
            }
            e9.getClass();
            C0751xo c0751xoF = e9.f(ac);
            int i2 = c0751xoF != null ? c0751xoF.b : 0;
            C0751xo c0751xoG = e9.g(ac);
            i = c0751xoG != null ? c0751xoG.b : 0;
            int i3 = i2 == 0 ? -1 : AbstractC0782yo.a[AbstractC0535qo.j(i2)];
            if (i3 != -1 && i3 != 1) {
                i = i2;
            }
        }
        if (i == 2) {
            iMin = Math.min(iMin, 6);
        } else if (i == 3) {
            iMin = Math.max(iMin, 3);
        } else if (ac.l) {
            iMin = ac.r() ? Math.min(iMin, 1) : Math.min(iMin, -1);
        }
        if (ac.H && ac.a < 5) {
            iMin = Math.min(iMin, 4);
        }
        if (ac.m) {
            iMin = Math.max(iMin, 3);
        }
        if (Qc.J(2)) {
            Log.v("FragmentManager", "computeExpectedState() of " + iMin + " for " + ac);
        }
        return iMin;
    }

    public final void e() {
        Bundle bundle;
        boolean zJ = Qc.J(3);
        Ac ac = this.c;
        if (zJ) {
            Objects.toString(ac);
        }
        Bundle bundle2 = ac.b;
        Bundle bundle3 = bundle2 != null ? bundle2.getBundle("savedInstanceState") : null;
        if (ac.M) {
            ac.a = 1;
            Bundle bundle4 = ac.b;
            if (bundle4 == null || (bundle = bundle4.getBundle("childFragmentManager")) == null) {
                return;
            }
            ac.v.W(bundle);
            Qc qc = ac.v;
            qc.H = false;
            qc.I = false;
            qc.O.g = false;
            qc.u(1);
            return;
        }
        R2 r2 = this.a;
        r2.q(ac, false);
        ac.v.Q();
        ac.a = 1;
        ac.E = false;
        ac.P.a(new Tk(3, ac));
        ac.v(bundle3);
        ac.M = true;
        if (ac.E) {
            ac.P.d(EnumC0557rg.ON_CREATE);
            r2.l(ac, false);
        } else {
            throw new C0228gp("Fragment " + ac + " did not call through to super.onCreate()");
        }
    }

    public final void f() {
        String resourceName;
        Ac ac = this.c;
        if (ac.n) {
            return;
        }
        if (Qc.J(3)) {
            Objects.toString(ac);
        }
        Bundle bundle = ac.b;
        Bundle bundle2 = bundle != null ? bundle.getBundle("savedInstanceState") : null;
        LayoutInflater layoutInflaterZ = ac.z(bundle2);
        ac.L = layoutInflaterZ;
        ViewGroup viewGroup = ac.F;
        if (viewGroup == null) {
            int i = ac.y;
            if (i == 0) {
                viewGroup = null;
            } else {
                if (i == -1) {
                    throw new IllegalArgumentException("Cannot create fragment " + ac + " for a container view with no id");
                }
                viewGroup = (ViewGroup) ac.t.x.J(i);
                if (viewGroup == null) {
                    if (!ac.q && !ac.p) {
                        try {
                            resourceName = ac.m().getResourceName(ac.y);
                        } catch (Resources.NotFoundException unused) {
                            resourceName = "unknown";
                        }
                        throw new IllegalArgumentException("No view found for id 0x" + Integer.toHexString(ac.y) + " (" + resourceName + ") for fragment " + ac);
                    }
                } else if (!(viewGroup instanceof FragmentContainerView)) {
                    Zc zc = AbstractC0031ad.a;
                    AbstractC0031ad.b(new Vc(ac, "Attempting to add fragment " + ac + " to container " + viewGroup + " which is not a FragmentContainerView"));
                    AbstractC0031ad.a(ac).getClass();
                }
            }
        }
        ac.F = viewGroup;
        ac.F(layoutInflaterZ, viewGroup, bundle2);
        if (ac.G != null) {
            if (Qc.J(3)) {
                Objects.toString(ac);
            }
            ac.G.setSaveFromParentEnabled(false);
            ac.G.setTag(R.id._0_resource_name_obfuscated_res_0x7f0900bd, ac);
            if (viewGroup != null) {
                b();
            }
            if (ac.A) {
                ac.G.setVisibility(8);
            }
            if (ac.G.isAttachedToWindow()) {
                View view = ac.G;
                WeakHashMap weakHashMap = Hs.a;
                AbstractC0755xs.c(view);
            } else {
                View view2 = ac.G;
                view2.addOnAttachStateChangeListener(new ViewOnAttachStateChangeListenerC0511q0(4, view2));
            }
            Bundle bundle3 = ac.b;
            ac.D(bundle3 != null ? bundle3.getBundle("savedInstanceState") : null);
            ac.v.u(2);
            this.a.v(ac, ac.G, false);
            int visibility = ac.G.getVisibility();
            ac.f().j = ac.G.getAlpha();
            if (ac.F != null && visibility == 0) {
                View viewFindFocus = ac.G.findFocus();
                if (viewFindFocus != null) {
                    ac.f().k = viewFindFocus;
                    if (Qc.J(2)) {
                        Log.v("FragmentManager", "requestFocus: Saved focused view " + viewFindFocus + " for Fragment " + ac);
                    }
                }
                ac.G.setAlpha(0.0f);
            }
        }
        ac.a = 2;
    }

    public final void g() {
        boolean z;
        Ac acG;
        boolean zJ = Qc.J(3);
        Ac ac = this.c;
        if (zJ) {
            Objects.toString(ac);
        }
        int i = 0;
        boolean z2 = ac.l && !ac.r();
        Xn xn = this.b;
        if (z2) {
            xn.w(ac.e, null);
        }
        if (!z2) {
            Tc tc = (Tc) xn.d;
            if (!((tc.b.containsKey(ac.e) && tc.e) ? tc.f : true)) {
                String str = ac.h;
                if (str != null && (acG = xn.g(str)) != null && acG.C) {
                    ac.g = acG;
                }
                ac.a = 0;
                return;
            }
        }
        Cc cc = ac.u;
        if (cc != null) {
            z = ((Tc) xn.d).f;
        } else {
            AbstractActivityC0051b2 abstractActivityC0051b2 = cc.j;
            z = abstractActivityC0051b2 != null ? !abstractActivityC0051b2.isChangingConfigurations() : true;
        }
        if (z2 || z) {
            Tc tc2 = (Tc) xn.d;
            tc2.getClass();
            if (Qc.J(3)) {
                Objects.toString(ac);
            }
            tc2.e(ac.e, false);
        }
        ac.v.l();
        ac.P.d(EnumC0557rg.ON_DESTROY);
        ac.a = 0;
        ac.M = false;
        ac.E = true;
        this.a.m(ac, false);
        ArrayList arrayListK = xn.k();
        int size = arrayListK.size();
        while (i < size) {
            Object obj = arrayListK.get(i);
            i++;
            Xc xc = (Xc) obj;
            if (xc != null) {
                String str2 = ac.e;
                Ac ac2 = xc.c;
                if (str2.equals(ac2.h)) {
                    ac2.g = ac;
                    ac2.h = null;
                }
            }
        }
        String str3 = ac.h;
        if (str3 != null) {
            ac.g = xn.g(str3);
        }
        xn.r(this);
    }

    public final void h() {
        View view;
        boolean zJ = Qc.J(3);
        Ac ac = this.c;
        if (zJ) {
            Objects.toString(ac);
        }
        ViewGroup viewGroup = ac.F;
        if (viewGroup != null && (view = ac.G) != null) {
            viewGroup.removeView(view);
        }
        ac.v.u(1);
        if (ac.G != null) {
            C0093cd c0093cd = ac.Q;
            c0093cd.f();
            if (c0093cd.d.c.a(EnumC0588sg.c)) {
                ac.Q.c(EnumC0557rg.ON_DESTROY);
            }
        }
        ac.a = 1;
        ac.E = false;
        ac.x();
        if (!ac.E) {
            throw new C0228gp("Fragment " + ac + " did not call through to super.onDestroyView()");
        }
        C0689vo c0689vo = ((C0251hh) new Ys(ac.d(), C0251hh.c).r(C0251hh.class)).b;
        if (c0689vo.c > 0) {
            c0689vo.b[0].getClass();
            throw new ClassCastException();
        }
        ac.r = false;
        this.a.w(ac, false);
        ac.F = null;
        ac.G = null;
        ac.Q = null;
        ac.R.f(null);
        ac.o = false;
    }

    public final void i() {
        boolean zJ = Qc.J(3);
        Ac ac = this.c;
        if (zJ) {
            Objects.toString(ac);
        }
        ac.a = -1;
        ac.E = false;
        ac.y();
        ac.L = null;
        if (!ac.E) {
            throw new C0228gp("Fragment " + ac + " did not call through to super.onDetach()");
        }
        Qc qc = ac.v;
        if (!qc.J) {
            qc.l();
            ac.v = new Qc();
        }
        this.a.n(ac, false);
        ac.a = -1;
        ac.u = null;
        ac.w = null;
        ac.t = null;
        if (!ac.l || ac.r()) {
            Tc tc = (Tc) this.b.d;
            if (!((tc.b.containsKey(ac.e) && tc.e) ? tc.f : true)) {
                return;
            }
        }
        if (Qc.J(3)) {
            Objects.toString(ac);
        }
        ac.p();
    }

    public final void j() {
        Ac ac = this.c;
        if (ac.n && ac.o && !ac.r) {
            if (Qc.J(3)) {
                Objects.toString(ac);
            }
            Bundle bundle = ac.b;
            Bundle bundle2 = bundle != null ? bundle.getBundle("savedInstanceState") : null;
            LayoutInflater layoutInflaterZ = ac.z(bundle2);
            ac.L = layoutInflaterZ;
            ac.F(layoutInflaterZ, null, bundle2);
            View view = ac.G;
            if (view != null) {
                view.setSaveFromParentEnabled(false);
                ac.G.setTag(R.id._0_resource_name_obfuscated_res_0x7f0900bd, ac);
                if (ac.A) {
                    ac.G.setVisibility(8);
                }
                Bundle bundle3 = ac.b;
                ac.D(bundle3 != null ? bundle3.getBundle("savedInstanceState") : null);
                ac.v.u(2);
                this.a.v(ac, ac.G, false);
                ac.a = 2;
            }
        }
    }

    public final void k() {
        ViewGroup viewGroup;
        E9 e9;
        ViewGroup viewGroup2;
        E9 e10;
        ViewGroup viewGroup3;
        E9 e11;
        Xn xn = this.b;
        boolean z = this.d;
        Ac ac = this.c;
        if (z) {
            if (Qc.J(2)) {
                Log.v("FragmentManager", "Ignoring re-entrant call to moveToExpectedState() for " + ac);
                return;
            }
            return;
        }
        try {
            this.d = true;
            boolean z2 = false;
            while (true) {
                int iD = d();
                int i = ac.a;
                int i2 = 3;
                if (iD == i) {
                    if (!z2 && i == -1 && ac.l && !ac.r()) {
                        if (Qc.J(3)) {
                            Objects.toString(ac);
                        }
                        Tc tc = (Tc) xn.d;
                        tc.getClass();
                        if (Qc.J(3)) {
                            Objects.toString(ac);
                        }
                        tc.e(ac.e, true);
                        xn.r(this);
                        if (Qc.J(3)) {
                            Objects.toString(ac);
                        }
                        ac.p();
                    }
                    if (ac.K) {
                        if (ac.G != null && (viewGroup = ac.F) != null) {
                            ac.l().H();
                            Object tag = viewGroup.getTag(R.id._0_resource_name_obfuscated_res_0x7f09019c);
                            if (tag instanceof E9) {
                                e9 = (E9) tag;
                            } else {
                                e9 = new E9(viewGroup);
                                viewGroup.setTag(R.id._0_resource_name_obfuscated_res_0x7f09019c, e9);
                            }
                            if (ac.A) {
                                e9.getClass();
                                if (Qc.J(2)) {
                                    Log.v("FragmentManager", "SpecialEffectsController: Enqueuing hide operation for fragment " + ac);
                                }
                                e9.d(3, 1, this);
                            } else {
                                e9.getClass();
                                if (Qc.J(2)) {
                                    Log.v("FragmentManager", "SpecialEffectsController: Enqueuing show operation for fragment " + ac);
                                }
                                e9.d(2, 1, this);
                            }
                        }
                        Qc qc = ac.t;
                        if (qc != null && ac.k && Qc.K(ac)) {
                            qc.G = true;
                        }
                        ac.K = false;
                        ac.v.o();
                    }
                    this.d = false;
                    return;
                }
                if (iD <= i) {
                    switch (i - 1) {
                        case -1:
                            i();
                            break;
                        case 0:
                            g();
                            break;
                        case 1:
                            h();
                            ac.a = 1;
                            break;
                        case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                            ac.o = false;
                            ac.a = 2;
                            break;
                        case 3:
                            if (Qc.J(3)) {
                                Objects.toString(ac);
                            }
                            if (ac.G != null && ac.c == null) {
                                o();
                            }
                            if (ac.G != null && (viewGroup2 = ac.F) != null) {
                                ac.l().H();
                                Object tag2 = viewGroup2.getTag(R.id._0_resource_name_obfuscated_res_0x7f09019c);
                                if (tag2 instanceof E9) {
                                    e10 = (E9) tag2;
                                } else {
                                    e10 = new E9(viewGroup2);
                                    viewGroup2.setTag(R.id._0_resource_name_obfuscated_res_0x7f09019c, e10);
                                }
                                e10.getClass();
                                if (Qc.J(2)) {
                                    Log.v("FragmentManager", "SpecialEffectsController: Enqueuing remove operation for fragment " + ac);
                                }
                                e10.d(1, 3, this);
                            }
                            ac.a = 3;
                            break;
                        case 4:
                            q();
                            break;
                        case BulletSpan.STANDARD_BULLET_RADIUS /* 5 */:
                            ac.a = 5;
                            break;
                        case 6:
                            l();
                            break;
                    }
                } else {
                    switch (i + 1) {
                        case 0:
                            c();
                            break;
                        case 1:
                            e();
                            break;
                        case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                            j();
                            f();
                            break;
                        case 3:
                            a();
                            break;
                        case 4:
                            if (ac.G != null && (viewGroup3 = ac.F) != null) {
                                ac.l().H();
                                Object tag3 = viewGroup3.getTag(R.id._0_resource_name_obfuscated_res_0x7f09019c);
                                if (tag3 instanceof E9) {
                                    e11 = (E9) tag3;
                                } else {
                                    e11 = new E9(viewGroup3);
                                    viewGroup3.setTag(R.id._0_resource_name_obfuscated_res_0x7f09019c, e11);
                                }
                                int visibility = ac.G.getVisibility();
                                if (visibility == 0) {
                                    i2 = 2;
                                } else if (visibility == 4) {
                                    i2 = 4;
                                } else if (visibility != 8) {
                                    throw new IllegalArgumentException("Unknown visibility " + visibility);
                                }
                                e11.getClass();
                                if (Qc.J(2)) {
                                    Log.v("FragmentManager", "SpecialEffectsController: Enqueuing add operation for fragment " + ac);
                                }
                                e11.d(i2, 2, this);
                            }
                            ac.a = 4;
                            break;
                        case BulletSpan.STANDARD_BULLET_RADIUS /* 5 */:
                            p();
                            break;
                        case 6:
                            ac.a = 6;
                            break;
                        case 7:
                            n();
                            break;
                    }
                }
                z2 = true;
            }
        } catch (Throwable th) {
            this.d = false;
            throw th;
        }
    }

    public final void l() {
        boolean zJ = Qc.J(3);
        Ac ac = this.c;
        if (zJ) {
            Objects.toString(ac);
        }
        ac.v.u(5);
        if (ac.G != null) {
            ac.Q.c(EnumC0557rg.ON_PAUSE);
        }
        ac.P.d(EnumC0557rg.ON_PAUSE);
        ac.a = 6;
        ac.E = true;
        this.a.o(ac, false);
    }

    public final void m(ClassLoader classLoader) {
        Ac ac = this.c;
        Bundle bundle = ac.b;
        if (bundle == null) {
            return;
        }
        bundle.setClassLoader(classLoader);
        if (ac.b.getBundle("savedInstanceState") == null) {
            ac.b.putBundle("savedInstanceState", new Bundle());
        }
        try {
            ac.c = ac.b.getSparseParcelableArray("viewState");
            ac.d = ac.b.getBundle("viewRegistryState");
            Wc wc = (Wc) ac.b.getParcelable("state");
            if (wc != null) {
                ac.h = wc.m;
                ac.i = wc.n;
                ac.I = wc.o;
            }
            if (ac.I) {
                return;
            }
            ac.H = true;
        } catch (BadParcelableException e) {
            throw new IllegalStateException("Failed to restore view hierarchy state for fragment " + ac, e);
        }
    }

    /* JADX WARN: Code duplicated, block: B:19:0x0031  */
    /* JADX WARN: Code duplicated, block: B:21:0x0042  */
    /* JADX WARN: Code duplicated, block: B:22:0x0045  */
    public final void n() {
        boolean zRequestFocus;
        String str;
        boolean zJ = Qc.J(3);
        Ac ac = this.c;
        if (zJ) {
            Objects.toString(ac);
        }
        C0801zc c0801zc = ac.J;
        View view = c0801zc == null ? null : c0801zc.k;
        if (view != null) {
            if (view == ac.G) {
                zRequestFocus = view.requestFocus();
                if (Qc.J(2)) {
                    StringBuilder sb = new StringBuilder("requestFocus: Restoring focused view ");
                    sb.append(view);
                    sb.append(" ");
                    if (zRequestFocus) {
                        str = "succeeded";
                    } else {
                        str = "failed";
                    }
                    sb.append(str);
                    sb.append(" on Fragment ");
                    sb.append(ac);
                    sb.append(" resulting in focused view ");
                    sb.append(ac.G.findFocus());
                    Log.v("FragmentManager", sb.toString());
                }
            } else {
                ViewParent parent = view.getParent();
                while (true) {
                    if (parent != null) {
                        if (parent == ac.G) {
                            zRequestFocus = view.requestFocus();
                            if (Qc.J(2)) {
                                StringBuilder sb2 = new StringBuilder("requestFocus: Restoring focused view ");
                                sb2.append(view);
                                sb2.append(" ");
                                if (zRequestFocus) {
                                    str = "succeeded";
                                } else {
                                    str = "failed";
                                }
                                sb2.append(str);
                                sb2.append(" on Fragment ");
                                sb2.append(ac);
                                sb2.append(" resulting in focused view ");
                                sb2.append(ac.G.findFocus());
                                Log.v("FragmentManager", sb2.toString());
                            }
                        } else {
                            parent = parent.getParent();
                        }
                    }
                }
            }
        }
        ac.f().k = null;
        ac.v.Q();
        ac.v.z(true);
        ac.a = 7;
        ac.E = true;
        C0805zg c0805zg = ac.P;
        EnumC0557rg enumC0557rg = EnumC0557rg.ON_RESUME;
        c0805zg.d(enumC0557rg);
        if (ac.G != null) {
            ac.Q.c(enumC0557rg);
        }
        Qc qc = ac.v;
        qc.H = false;
        qc.I = false;
        qc.O.g = false;
        qc.u(7);
        this.a.r(ac, false);
        this.b.w(ac.e, null);
        ac.b = null;
        ac.c = null;
        ac.d = null;
    }

    public final void o() {
        Ac ac = this.c;
        if (ac.G == null) {
            return;
        }
        if (Qc.J(2)) {
            Log.v("FragmentManager", "Saving view state for fragment " + ac + " with view " + ac.G);
        }
        SparseArray<Parcelable> sparseArray = new SparseArray<>();
        ac.G.saveHierarchyState(sparseArray);
        if (sparseArray.size() > 0) {
            ac.c = sparseArray;
        }
        Bundle bundle = new Bundle();
        ac.Q.e.Q(bundle);
        if (bundle.isEmpty()) {
            return;
        }
        ac.d = bundle;
    }

    public final void p() {
        boolean zJ = Qc.J(3);
        Ac ac = this.c;
        if (zJ) {
            Objects.toString(ac);
        }
        ac.v.Q();
        ac.v.z(true);
        ac.a = 5;
        ac.E = false;
        ac.B();
        if (!ac.E) {
            throw new C0228gp("Fragment " + ac + " did not call through to super.onStart()");
        }
        C0805zg c0805zg = ac.P;
        EnumC0557rg enumC0557rg = EnumC0557rg.ON_START;
        c0805zg.d(enumC0557rg);
        if (ac.G != null) {
            ac.Q.c(enumC0557rg);
        }
        Qc qc = ac.v;
        qc.H = false;
        qc.I = false;
        qc.O.g = false;
        qc.u(5);
        this.a.t(ac, false);
    }

    public final void q() {
        boolean zJ = Qc.J(3);
        Ac ac = this.c;
        if (zJ) {
            Objects.toString(ac);
        }
        Qc qc = ac.v;
        qc.I = true;
        qc.O.g = true;
        qc.u(4);
        if (ac.G != null) {
            ac.Q.c(EnumC0557rg.ON_STOP);
        }
        ac.P.d(EnumC0557rg.ON_STOP);
        ac.a = 4;
        ac.E = false;
        ac.C();
        if (ac.E) {
            this.a.u(ac, false);
            return;
        }
        throw new C0228gp("Fragment " + ac + " did not call through to super.onStop()");
    }

    public Xc(R2 r2, Xn xn, ClassLoader classLoader, Kc kc, Bundle bundle) {
        this.a = r2;
        this.b = xn;
        Wc wc = (Wc) bundle.getParcelable("state");
        Ac acA = kc.a(wc.a);
        acA.e = wc.b;
        acA.n = wc.c;
        acA.p = wc.d;
        acA.q = true;
        acA.x = wc.e;
        acA.y = wc.f;
        acA.z = wc.g;
        acA.C = wc.h;
        acA.l = wc.i;
        acA.B = wc.j;
        acA.A = wc.k;
        acA.O = EnumC0588sg.b()[wc.l];
        acA.h = wc.m;
        acA.i = wc.n;
        acA.I = wc.o;
        this.c = acA;
        acA.b = bundle;
        Bundle bundle2 = bundle.getBundle("arguments");
        if (bundle2 != null) {
            bundle2.setClassLoader(classLoader);
        }
        acA.K(bundle2);
        if (Qc.J(2)) {
            Log.v("FragmentManager", "Instantiated fragment " + acA);
        }
    }

    public Xc(R2 r2, Xn xn, Ac ac, Bundle bundle) {
        this.a = r2;
        this.b = xn;
        this.c = ac;
        ac.c = null;
        ac.d = null;
        ac.s = 0;
        ac.o = false;
        ac.k = false;
        Ac ac2 = ac.g;
        ac.h = ac2 != null ? ac2.e : null;
        ac.g = null;
        ac.b = bundle;
        ac.f = bundle.getBundle("arguments");
    }
}
