package rikka.shizuku;

import android.content.Context;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.ActionBarContextView;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
public final class Bt extends F0 implements InterfaceC0067bi {
    public final Context c;
    public final MenuC0129di d;
    public R2 e;
    public WeakReference f;
    public final /* synthetic */ Ct g;

    public Bt(Ct ct, Context context, R2 r2) {
        this.g = ct;
        this.c = context;
        this.e = r2;
        MenuC0129di menuC0129di = new MenuC0129di(context);
        menuC0129di.l = 1;
        this.d = menuC0129di;
        menuC0129di.e = this;
    }

    @Override // rikka.shizuku.InterfaceC0067bi
    public final void a(MenuC0129di menuC0129di) {
        if (this.e == null) {
            return;
        }
        h();
        C0 c0 = this.g.v.d;
        if (c0 != null) {
            c0.l();
        }
    }

    @Override // rikka.shizuku.F0
    public final void b() {
        Ct ct = this.g;
        if (ct.y != this) {
            return;
        }
        if (ct.F) {
            ct.z = this;
            ct.A = this.e;
        } else {
            this.e.L(this);
        }
        this.e = null;
        ct.d0(false);
        ActionBarContextView actionBarContextView = ct.v;
        if (actionBarContextView.k == null) {
            actionBarContextView.f();
        }
        ct.s.l(ct.K);
        ct.y = null;
    }

    @Override // rikka.shizuku.F0
    public final View c() {
        WeakReference weakReference = this.f;
        if (weakReference != null) {
            return (View) weakReference.get();
        }
        return null;
    }

    @Override // rikka.shizuku.F0
    public final MenuC0129di d() {
        return this.d;
    }

    @Override // rikka.shizuku.F0
    public final MenuInflater e() {
        return new C0381lp(this.c);
    }

    @Override // rikka.shizuku.F0
    public final CharSequence f() {
        return this.g.v.j;
    }

    @Override // rikka.shizuku.F0
    public final CharSequence g() {
        return this.g.v.i;
    }

    @Override // rikka.shizuku.F0
    public final void h() {
        if (this.g.y != this) {
            return;
        }
        MenuC0129di menuC0129di = this.d;
        menuC0129di.w();
        try {
            this.e.M(this, menuC0129di);
        } finally {
            menuC0129di.v();
        }
    }

    @Override // rikka.shizuku.F0
    public final boolean i() {
        return this.g.v.s;
    }

    @Override // rikka.shizuku.F0
    public final void j(View view) {
        this.g.v.i(view);
        this.f = new WeakReference(view);
    }

    @Override // rikka.shizuku.F0
    public final void k(int i) {
        l(this.g.q.getResources().getString(i));
    }

    @Override // rikka.shizuku.F0
    public final void l(CharSequence charSequence) {
        ActionBarContextView actionBarContextView = this.g.v;
        actionBarContextView.j = charSequence;
        actionBarContextView.e();
    }

    @Override // rikka.shizuku.F0
    public final void m(int i) {
        o(this.g.q.getResources().getString(i));
    }

    @Override // rikka.shizuku.InterfaceC0067bi
    public final boolean n(MenuC0129di menuC0129di, MenuItem menuItem) {
        R2 r2 = this.e;
        if (r2 != null) {
            return ((Xn) r2.b).s(this, menuItem);
        }
        return false;
    }

    @Override // rikka.shizuku.F0
    public final void o(CharSequence charSequence) {
        ActionBarContextView actionBarContextView = this.g.v;
        actionBarContextView.i = charSequence;
        actionBarContextView.e();
        Hs.n(actionBarContextView, charSequence);
    }

    @Override // rikka.shizuku.F0
    public final void p(boolean z) {
        this.b = z;
        ActionBarContextView actionBarContextView = this.g.v;
        if (z != actionBarContextView.s) {
            actionBarContextView.requestLayout();
        }
        actionBarContextView.s = z;
    }
}
