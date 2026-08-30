package rikka.shizuku;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;

/* JADX INFO: renamed from: rikka.shizuku.cd, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0093cd implements InterfaceC0155ee, Bm, InterfaceC0047at {
    public final Ac a;
    public final Zs b;
    public final G0 c;
    public C0805zg d = null;
    public R2 e = null;

    public C0093cd(Ac ac, Zs zs, G0 g0) {
        this.a = ac;
        this.b = zs;
        this.c = g0;
    }

    @Override // rikka.shizuku.InterfaceC0155ee
    public final Oi a() {
        Application application;
        Ac ac = this.a;
        Context applicationContext = ac.H().getApplicationContext();
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
        Oi oi = new Oi();
        if (application != null) {
            oi.a(X8.q, application);
        }
        oi.a(AbstractC0599sr.o, ac);
        oi.a(AbstractC0599sr.p, this);
        Bundle bundle = ac.f;
        if (bundle != null) {
            oi.a(AbstractC0599sr.q, bundle);
        }
        return oi;
    }

    @Override // rikka.shizuku.Bm
    public final R2 b() {
        f();
        return (R2) this.e.c;
    }

    public final void c(EnumC0557rg enumC0557rg) {
        this.d.d(enumC0557rg);
    }

    @Override // rikka.shizuku.InterfaceC0047at
    public final Zs d() {
        f();
        return this.b;
    }

    @Override // rikka.shizuku.InterfaceC0743xg
    public final C0805zg e() {
        f();
        return this.d;
    }

    public final void f() {
        if (this.d == null) {
            this.d = new C0805zg(this);
            R2 r2 = new R2(new Am(this, new C0419n1(4, this)));
            this.e = r2;
            r2.P();
            this.c.run();
        }
    }
}
