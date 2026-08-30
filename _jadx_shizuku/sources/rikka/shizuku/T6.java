package rikka.shizuku;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Trace;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes.dex */
public abstract class T6 extends S6 implements InterfaceC0047at, InterfaceC0155ee, Bm {
    public final C7 b = new C7();
    public final K3 c;
    public final C0805zg d;
    public final R2 e;
    public Zs f;
    public C0622tj g;
    public final R6 h;
    public final C0672v7 i;
    public final AtomicInteger j;
    public final N6 k;
    public final CopyOnWriteArrayList l;
    public final CopyOnWriteArrayList m;
    public final CopyOnWriteArrayList n;
    public final CopyOnWriteArrayList o;
    public final CopyOnWriteArrayList p;
    public boolean q;
    public boolean r;

    public T6() {
        AbstractActivityC0051b2 abstractActivityC0051b2 = (AbstractActivityC0051b2) this;
        this.c = new K3(new G0(4, abstractActivityC0051b2));
        C0805zg c0805zg = new C0805zg(this);
        this.d = c0805zg;
        R2 r2 = new R2(new Am(this, new C0419n1(4, this)));
        this.e = r2;
        this.g = null;
        this.h = new R6(abstractActivityC0051b2);
        this.i = new C0672v7(new C0419n1(1, abstractActivityC0051b2));
        this.j = new AtomicInteger();
        this.k = new N6(abstractActivityC0051b2);
        this.l = new CopyOnWriteArrayList();
        this.m = new CopyOnWriteArrayList();
        this.n = new CopyOnWriteArrayList();
        this.o = new CopyOnWriteArrayList();
        this.p = new CopyOnWriteArrayList();
        this.q = false;
        this.r = false;
        c0805zg.a(new O6(abstractActivityC0051b2, 0));
        c0805zg.a(new O6(abstractActivityC0051b2, 1));
        c0805zg.a(new O6(abstractActivityC0051b2, 2));
        r2.P();
        AbstractC0599sr.l(this);
        ((R2) r2.c).S("android:support:activity-result", new L6(0, abstractActivityC0051b2));
        h(new M6(abstractActivityC0051b2, 0));
    }

    @Override // rikka.shizuku.InterfaceC0155ee
    public final Oi a() {
        Oi oi = new Oi();
        if (getApplication() != null) {
            oi.a(X8.q, getApplication());
        }
        oi.a(AbstractC0599sr.o, this);
        oi.a(AbstractC0599sr.p, this);
        if (getIntent() != null && getIntent().getExtras() != null) {
            oi.a(AbstractC0599sr.q, getIntent().getExtras());
        }
        return oi;
    }

    @Override // rikka.shizuku.Bm
    public final R2 b() {
        return (R2) this.e.c;
    }

    @Override // rikka.shizuku.InterfaceC0047at
    public final Zs d() {
        if (getApplication() == null) {
            throw new IllegalStateException("Your activity is not yet attached to the Application instance. You can't request ViewModel before onCreate call.");
        }
        if (this.f == null) {
            Q6 q6 = (Q6) getLastNonConfigurationInstance();
            if (q6 != null) {
                this.f = q6.a;
            }
            if (this.f == null) {
                this.f = new Zs();
            }
        }
        return this.f;
    }

    @Override // rikka.shizuku.InterfaceC0743xg
    public final C0805zg e() {
        return this.d;
    }

    public final void g(InterfaceC0734x7 interfaceC0734x7) {
        this.l.add(interfaceC0734x7);
    }

    public final void h(InterfaceC0653uj interfaceC0653uj) {
        C7 c7 = this.b;
        if (c7.b != null) {
            interfaceC0653uj.a();
        }
        c7.a.add(interfaceC0653uj);
    }

    public final C0622tj i() {
        if (this.g == null) {
            this.g = new C0622tj(new RunnableC0607t4(2, this));
            this.d.a(new Tk(1, this));
        }
        return this.g;
    }

    @Override // android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        if (this.k.a(i, i2, intent)) {
            return;
        }
        super.onActivityResult(i, i2, intent);
    }

    @Override // android.app.Activity
    public final void onBackPressed() {
        i().a();
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Iterator it = this.l.iterator();
        while (it.hasNext()) {
            ((InterfaceC0734x7) it.next()).a(configuration);
        }
    }

    @Override // rikka.shizuku.S6, android.app.Activity
    public void onCreate(Bundle bundle) {
        Am am = (Am) this.e.b;
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
        Bundle bundleF = null;
        if (bundle != null && bundle.containsKey("androidx.lifecycle.BundlableSavedStateRegistry.key")) {
            bundleF = X8.F("androidx.lifecycle.BundlableSavedStateRegistry.key", bundle);
        }
        am.f = bundleF;
        am.g = true;
        C7 c7 = this.b;
        c7.b = this;
        Iterator it = c7.a.iterator();
        while (it.hasNext()) {
            ((InterfaceC0653uj) it.next()).a();
        }
        super.onCreate(bundle);
        int i = Jl.b;
        AbstractC0744xh.B(this);
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public final boolean onCreatePanelMenu(int i, Menu menu) {
        if (i != 0) {
            return true;
        }
        super.onCreatePanelMenu(i, menu);
        getMenuInflater();
        Iterator it = ((CopyOnWriteArrayList) this.c.c).iterator();
        while (it.hasNext()) {
            ((Jc) it.next()).a.k();
        }
        return true;
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public boolean onMenuItemSelected(int i, MenuItem menuItem) {
        if (super.onMenuItemSelected(i, menuItem)) {
            return true;
        }
        if (i == 0) {
            Iterator it = ((CopyOnWriteArrayList) this.c.c).iterator();
            while (it.hasNext()) {
                if (((Jc) it.next()).a.p()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // android.app.Activity
    public final void onMultiWindowModeChanged(boolean z) {
        if (this.q) {
            return;
        }
        Iterator it = this.o.iterator();
        while (it.hasNext()) {
            ((InterfaceC0734x7) it.next()).a(new Ni(z));
        }
    }

    @Override // android.app.Activity
    public final void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Iterator it = this.n.iterator();
        while (it.hasNext()) {
            ((InterfaceC0734x7) it.next()).a(intent);
        }
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onPanelClosed(int i, Menu menu) {
        Iterator it = ((CopyOnWriteArrayList) this.c.c).iterator();
        while (it.hasNext()) {
            ((Jc) it.next()).a.q();
        }
        super.onPanelClosed(i, menu);
    }

    @Override // android.app.Activity
    public final void onPictureInPictureModeChanged(boolean z) {
        if (this.r) {
            return;
        }
        Iterator it = this.p.iterator();
        while (it.hasNext()) {
            ((InterfaceC0734x7) it.next()).a(new Uj(z));
        }
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public final boolean onPreparePanel(int i, View view, Menu menu) {
        if (i != 0) {
            return true;
        }
        super.onPreparePanel(i, view, menu);
        Iterator it = ((CopyOnWriteArrayList) this.c.c).iterator();
        while (it.hasNext()) {
            ((Jc) it.next()).a.t();
        }
        return true;
    }

    @Override // android.app.Activity
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (this.k.a(i, -1, new Intent().putExtra("androidx.activity.result.contract.extra.PERMISSIONS", strArr).putExtra("androidx.activity.result.contract.extra.PERMISSION_GRANT_RESULTS", iArr))) {
            return;
        }
        super.onRequestPermissionsResult(i, strArr, iArr);
    }

    @Override // android.app.Activity
    public final Object onRetainNonConfigurationInstance() {
        Q6 q6;
        Zs zs = this.f;
        if (zs == null && (q6 = (Q6) getLastNonConfigurationInstance()) != null) {
            zs = q6.a;
        }
        if (zs == null) {
            return null;
        }
        Q6 q7 = new Q6();
        q7.a = zs;
        return q7;
    }

    @Override // rikka.shizuku.S6, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        C0805zg c0805zg = this.d;
        if (c0805zg != null) {
            EnumC0588sg enumC0588sg = EnumC0588sg.c;
            c0805zg.c("setCurrentState");
            c0805zg.e(enumC0588sg);
        }
        super.onSaveInstanceState(bundle);
        this.e.Q(bundle);
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks2
    public final void onTrimMemory(int i) {
        super.onTrimMemory(i);
        Iterator it = this.m.iterator();
        while (it.hasNext()) {
            ((InterfaceC0734x7) it.next()).a(Integer.valueOf(i));
        }
    }

    @Override // android.app.Activity
    public final void reportFullyDrawn() {
        try {
            if (AbstractC0750xn.l()) {
                AbstractC0750xn.d("reportFullyDrawn() for ComponentActivity");
            }
            super.reportFullyDrawn();
            C0672v7 c0672v7 = this.i;
            synchronized (c0672v7.d) {
                try {
                    c0672v7.b = true;
                    ArrayList arrayList = c0672v7.c;
                    int size = arrayList.size();
                    int i = 0;
                    while (i < size) {
                        Object obj = arrayList.get(i);
                        i++;
                        ((InterfaceC0124dd) obj).a();
                    }
                    c0672v7.c.clear();
                } catch (Throwable th) {
                    throw th;
                }
            }
            Trace.endSection();
        } catch (Throwable th2) {
            Trace.endSection();
            throw th2;
        }
    }

    @Override // android.app.Activity
    public final void onMultiWindowModeChanged(boolean z, Configuration configuration) {
        this.q = true;
        try {
            super.onMultiWindowModeChanged(z, configuration);
            this.q = false;
            Iterator it = this.o.iterator();
            while (it.hasNext()) {
                ((InterfaceC0734x7) it.next()).a(new Ni(z));
            }
        } catch (Throwable th) {
            this.q = false;
            throw th;
        }
    }

    @Override // android.app.Activity
    public final void onPictureInPictureModeChanged(boolean z, Configuration configuration) {
        this.r = true;
        try {
            super.onPictureInPictureModeChanged(z, configuration);
            this.r = false;
            Iterator it = this.p.iterator();
            while (it.hasNext()) {
                ((InterfaceC0734x7) it.next()).a(new Uj(z));
            }
        } catch (Throwable th) {
            this.r = false;
            throw th;
        }
    }
}
