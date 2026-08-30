package rikka.shizuku;

import android.content.Context;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.ActionBarContextView;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
public final class Lo extends F0 implements InterfaceC0067bi {
    public Context c;
    public ActionBarContextView d;
    public R2 e;
    public WeakReference f;
    public boolean g;
    public MenuC0129di h;

    @Override // rikka.shizuku.InterfaceC0067bi
    public final void a(MenuC0129di menuC0129di) {
        h();
        C0 c0 = this.d.d;
        if (c0 != null) {
            c0.l();
        }
    }

    @Override // rikka.shizuku.F0
    public final void b() {
        if (this.g) {
            return;
        }
        this.g = true;
        this.e.L(this);
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
        return this.h;
    }

    @Override // rikka.shizuku.F0
    public final MenuInflater e() {
        return new C0381lp(this.d.getContext());
    }

    @Override // rikka.shizuku.F0
    public final CharSequence f() {
        return this.d.j;
    }

    @Override // rikka.shizuku.F0
    public final CharSequence g() {
        return this.d.i;
    }

    @Override // rikka.shizuku.F0
    public final void h() {
        this.e.M(this, this.h);
    }

    @Override // rikka.shizuku.F0
    public final boolean i() {
        return this.d.s;
    }

    @Override // rikka.shizuku.F0
    public final void j(View view) {
        this.d.i(view);
        this.f = view != null ? new WeakReference(view) : null;
    }

    @Override // rikka.shizuku.F0
    public final void k(int i) {
        l(this.c.getString(i));
    }

    @Override // rikka.shizuku.F0
    public final void l(CharSequence charSequence) {
        ActionBarContextView actionBarContextView = this.d;
        actionBarContextView.j = charSequence;
        actionBarContextView.e();
    }

    @Override // rikka.shizuku.F0
    public final void m(int i) {
        o(this.c.getString(i));
    }

    @Override // rikka.shizuku.InterfaceC0067bi
    public final boolean n(MenuC0129di menuC0129di, MenuItem menuItem) {
        return ((Xn) this.e.b).s(this, menuItem);
    }

    @Override // rikka.shizuku.F0
    public final void o(CharSequence charSequence) {
        ActionBarContextView actionBarContextView = this.d;
        actionBarContextView.i = charSequence;
        actionBarContextView.e();
        Hs.n(actionBarContextView, charSequence);
    }

    @Override // rikka.shizuku.F0
    public final void p(boolean z) {
        this.b = z;
        ActionBarContextView actionBarContextView = this.d;
        if (z != actionBarContextView.s) {
            actionBarContextView.requestLayout();
        }
        actionBarContextView.s = z;
    }
}
