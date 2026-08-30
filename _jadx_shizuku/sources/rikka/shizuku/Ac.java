package rikka.shizuku;

import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import moe.shizuku.privileged.api.R;

/* JADX INFO: loaded from: classes.dex */
public abstract class Ac implements ComponentCallbacks, View.OnCreateContextMenuListener, InterfaceC0743xg, InterfaceC0047at, InterfaceC0155ee, Bm {
    public static final Object V = new Object();
    public boolean A;
    public boolean B;
    public boolean C;
    public boolean E;
    public ViewGroup F;
    public View G;
    public boolean H;
    public C0801zc J;
    public boolean K;
    public LayoutInflater L;
    public boolean M;
    public String N;
    public EnumC0588sg O;
    public C0805zg P;
    public C0093cd Q;
    public final Pi R;
    public R2 S;
    public final ArrayList T;
    public final C0739xc U;
    public Bundle b;
    public SparseArray c;
    public Bundle d;
    public Bundle f;
    public Ac g;
    public int i;
    public boolean k;
    public boolean l;
    public boolean m;
    public boolean n;
    public boolean o;
    public boolean p;
    public boolean q;
    public boolean r;
    public int s;
    public Qc t;
    public Cc u;
    public Ac w;
    public int x;
    public int y;
    public String z;
    public int a = -1;
    public String e = UUID.randomUUID().toString();
    public String h = null;
    public Boolean j = null;
    public Qc v = new Qc();
    public final boolean D = true;
    public boolean I = true;

    public Ac() {
        new RunnableC0607t4(7, this);
        this.O = EnumC0588sg.e;
        this.R = new Pi();
        new AtomicInteger();
        this.T = new ArrayList();
        this.U = new C0739xc(this);
        o();
    }

    public abstract void A(Bundle bundle);

    public void B() {
        this.E = true;
    }

    public void C() {
        this.E = true;
    }

    public void E(Bundle bundle) {
        this.E = true;
    }

    public void F(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.v.Q();
        this.r = true;
        this.Q = new C0093cd(this, d(), new G0(9, this));
        View viewW = w(layoutInflater, viewGroup, bundle);
        this.G = viewW;
        if (viewW == null) {
            if (this.Q.d != null) {
                throw new IllegalStateException("Called getViewLifecycleOwner() but onCreateView() returned null");
            }
            this.Q = null;
            return;
        }
        this.Q.f();
        if (Qc.J(3)) {
            Objects.toString(this.G);
            toString();
        }
        this.G.setTag(R.id._0_resource_name_obfuscated_res_0x7f0901fc, this.Q);
        this.G.setTag(R.id._0_resource_name_obfuscated_res_0x7f0901ff, this.Q);
        this.G.setTag(R.id._0_resource_name_obfuscated_res_0x7f0901fe, this.Q);
        this.R.f(this.Q);
    }

    public final AbstractActivityC0051b2 G() {
        AbstractActivityC0051b2 abstractActivityC0051b2G = g();
        if (abstractActivityC0051b2G != null) {
            return abstractActivityC0051b2G;
        }
        throw new IllegalStateException("Fragment " + this + " not attached to an activity.");
    }

    public final Context H() {
        Context contextI = i();
        if (contextI != null) {
            return contextI;
        }
        throw new IllegalStateException("Fragment " + this + " not attached to a context.");
    }

    public final View I() {
        View view = this.G;
        if (view != null) {
            return view;
        }
        throw new IllegalStateException("Fragment " + this + " did not return a View from onCreateView() or this was called before onCreateView().");
    }

    public final void J(int i, int i2, int i3, int i4) {
        if (this.J == null && i == 0 && i2 == 0 && i3 == 0 && i4 == 0) {
            return;
        }
        f().b = i;
        f().c = i2;
        f().d = i3;
        f().e = i4;
    }

    public final void K(Bundle bundle) {
        Qc qc = this.t;
        if (qc != null) {
            if (qc == null ? false : qc.O()) {
                throw new IllegalStateException("Fragment already added and state has been saved");
            }
        }
        this.f = bundle;
    }

    public final void L(AbstractC0284ik abstractC0284ik) {
        if (abstractC0284ik != null) {
            Zc zc = AbstractC0031ad.a;
            AbstractC0031ad.b(new Jd(this, "Attempting to set target fragment " + abstractC0284ik + " with request code 0 for fragment " + this));
            AbstractC0031ad.a(this).getClass();
        }
        Qc qc = this.t;
        Qc qc2 = abstractC0284ik != null ? abstractC0284ik.t : null;
        if (qc != null && qc2 != null && qc != qc2) {
            throw new IllegalArgumentException("Fragment " + abstractC0284ik + " must share the same FragmentManager to be set as a target fragment");
        }
        for (Ac acN = abstractC0284ik; acN != null; acN = acN.n(false)) {
            if (super.equals(this)) {
                throw new IllegalArgumentException("Setting " + abstractC0284ik + " as the target of " + this + " would create a target cycle");
            }
        }
        if (abstractC0284ik == null) {
            this.h = null;
            this.g = null;
        } else if (this.t == null || abstractC0284ik.t == null) {
            this.h = null;
            this.g = abstractC0284ik;
        } else {
            this.h = abstractC0284ik.e;
            this.g = null;
        }
        this.i = 0;
    }

    @Override // rikka.shizuku.InterfaceC0155ee
    public final Oi a() {
        Application application;
        Context applicationContext = H().getApplicationContext();
        while (true) {
            if (!(applicationContext instanceof ContextWrapper)) {
                application = null;
                break;
            }
            if (applicationContext instanceof Application) {
                application = (Application) applicationContext;
                break;
            }
            applicationContext = ((ContextWrapper) applicationContext).getBaseContext();
        }
        if (application == null && Qc.J(3)) {
            Objects.toString(H().getApplicationContext());
        }
        Oi oi = new Oi();
        if (application != null) {
            oi.a(X8.q, application);
        }
        oi.a(AbstractC0599sr.o, this);
        oi.a(AbstractC0599sr.p, this);
        Bundle bundle = this.f;
        if (bundle != null) {
            oi.a(AbstractC0599sr.q, bundle);
        }
        return oi;
    }

    @Override // rikka.shizuku.Bm
    public final R2 b() {
        return (R2) this.S.c;
    }

    public AbstractC0046as c() {
        return new C0770yc(this);
    }

    @Override // rikka.shizuku.InterfaceC0047at
    public final Zs d() {
        if (this.t == null) {
            throw new IllegalStateException("Can't access ViewModels from detached fragment");
        }
        if (k() == 1) {
            throw new IllegalStateException("Calling getViewModelStore() before a Fragment reaches onCreate() when using setMaxLifecycle(INITIALIZED) is not supported");
        }
        HashMap map = this.t.O.d;
        Zs zs = (Zs) map.get(this.e);
        if (zs != null) {
            return zs;
        }
        Zs zs2 = new Zs();
        map.put(this.e, zs2);
        return zs2;
    }

    @Override // rikka.shizuku.InterfaceC0743xg
    public final C0805zg e() {
        return this.P;
    }

    public final C0801zc f() {
        if (this.J == null) {
            C0801zc c0801zc = new C0801zc();
            Object obj = V;
            c0801zc.g = obj;
            c0801zc.h = obj;
            c0801zc.i = obj;
            c0801zc.j = 1.0f;
            c0801zc.k = null;
            this.J = c0801zc;
        }
        return this.J;
    }

    public final AbstractActivityC0051b2 g() {
        Cc cc = this.u;
        if (cc == null) {
            return null;
        }
        return cc.i;
    }

    public final Qc h() {
        if (this.u != null) {
            return this.v;
        }
        throw new IllegalStateException("Fragment " + this + " has not been attached yet.");
    }

    public final Context i() {
        Cc cc = this.u;
        if (cc == null) {
            return null;
        }
        return cc.j;
    }

    public final LayoutInflater j() {
        LayoutInflater layoutInflater = this.L;
        if (layoutInflater != null) {
            return layoutInflater;
        }
        LayoutInflater layoutInflaterZ = z(null);
        this.L = layoutInflaterZ;
        return layoutInflaterZ;
    }

    public final int k() {
        EnumC0588sg enumC0588sg = this.O;
        return (enumC0588sg == EnumC0588sg.b || this.w == null) ? enumC0588sg.ordinal() : Math.min(enumC0588sg.ordinal(), this.w.k());
    }

    public final Qc l() {
        Qc qc = this.t;
        if (qc != null) {
            return qc;
        }
        throw new IllegalStateException("Fragment " + this + " not associated with a fragment manager.");
    }

    public final Resources m() {
        return H().getResources();
    }

    public final Ac n(boolean z) {
        String str;
        if (z) {
            Zc zc = AbstractC0031ad.a;
            AbstractC0031ad.b(new Jd(this, "Attempting to get target fragment from fragment " + this));
            AbstractC0031ad.a(this).getClass();
        }
        Ac ac = this.g;
        if (ac != null) {
            return ac;
        }
        Qc qc = this.t;
        if (qc == null || (str = this.h) == null) {
            return null;
        }
        return qc.c.g(str);
    }

    public final void o() {
        this.P = new C0805zg(this);
        this.S = new R2(new Am(this, new C0419n1(4, this)));
        Bundle bundleF = null;
        ArrayList arrayList = this.T;
        C0739xc c0739xc = this.U;
        if (arrayList.contains(c0739xc)) {
            return;
        }
        if (this.a < 0) {
            arrayList.add(c0739xc);
            return;
        }
        Ac ac = c0739xc.a;
        ac.S.P();
        AbstractC0599sr.l(ac);
        Bundle bundle = ac.b;
        Bundle bundle2 = bundle != null ? bundle.getBundle("registryState") : null;
        Am am = (Am) ac.S.b;
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
        if (bundle2 != null && bundle2.containsKey("androidx.lifecycle.BundlableSavedStateRegistry.key")) {
            bundleF = X8.F("androidx.lifecycle.BundlableSavedStateRegistry.key", bundle2);
        }
        am.f = bundleF;
        am.g = true;
    }

    @Override // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        this.E = true;
    }

    @Override // android.view.View.OnCreateContextMenuListener
    public final void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        G().onCreateContextMenu(contextMenu, view, contextMenuInfo);
    }

    @Override // android.content.ComponentCallbacks
    public final void onLowMemory() {
        this.E = true;
    }

    public final void p() {
        o();
        this.N = this.e;
        this.e = UUID.randomUUID().toString();
        this.k = false;
        this.l = false;
        this.n = false;
        this.o = false;
        this.q = false;
        this.s = 0;
        this.t = null;
        this.v = new Qc();
        this.u = null;
        this.x = 0;
        this.y = 0;
        this.z = null;
        this.A = false;
        this.B = false;
    }

    public final boolean q() {
        if (this.A) {
            return true;
        }
        Qc qc = this.t;
        if (qc != null) {
            Ac ac = this.w;
            qc.getClass();
            if (ac == null ? false : ac.q()) {
                return true;
            }
        }
        return false;
    }

    public final boolean r() {
        return this.s > 0;
    }

    public void s() {
        this.E = true;
    }

    public final void t(int i, int i2, Intent intent) {
        if (Qc.J(2)) {
            Log.v("FragmentManager", "Fragment " + this + " received the following in onActivityResult(): requestCode: " + i + " resultCode: " + i2 + " data: " + intent);
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append(getClass().getSimpleName());
        sb.append("{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append("} (");
        sb.append(this.e);
        if (this.x != 0) {
            sb.append(" id=0x");
            sb.append(Integer.toHexString(this.x));
        }
        if (this.z != null) {
            sb.append(" tag=");
            sb.append(this.z);
        }
        sb.append(")");
        return sb.toString();
    }

    public void u(AbstractActivityC0051b2 abstractActivityC0051b2) {
        this.E = true;
        Cc cc = this.u;
        if ((cc == null ? null : cc.i) != null) {
            this.E = true;
        }
    }

    public void v(Bundle bundle) {
        Bundle bundle2;
        this.E = true;
        Bundle bundle3 = this.b;
        if (bundle3 != null && (bundle2 = bundle3.getBundle("childFragmentManager")) != null) {
            this.v.W(bundle2);
            Qc qc = this.v;
            qc.H = false;
            qc.I = false;
            qc.O.g = false;
            qc.u(1);
        }
        Qc qc2 = this.v;
        if (qc2.v >= 1) {
            return;
        }
        qc2.H = false;
        qc2.I = false;
        qc2.O.g = false;
        qc2.u(1);
    }

    public View w(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return null;
    }

    public void x() {
        this.E = true;
    }

    public void y() {
        this.E = true;
    }

    public LayoutInflater z(Bundle bundle) {
        Cc cc = this.u;
        if (cc == null) {
            throw new IllegalStateException("onGetLayoutInflater() cannot be executed until the Fragment is attached to the FragmentManager.");
        }
        AbstractActivityC0051b2 abstractActivityC0051b2 = cc.m;
        LayoutInflater layoutInflaterCloneInContext = abstractActivityC0051b2.getLayoutInflater().cloneInContext(abstractActivityC0051b2);
        layoutInflaterCloneInContext.setFactory2(this.v.f);
        return layoutInflaterCloneInContext;
    }

    public void D(Bundle bundle) {
    }
}
