package rikka.shizuku;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

/* JADX INFO: renamed from: rikka.shizuku.ep, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class SubMenuC0166ep extends MenuC0129di implements SubMenu {
    public final C0221gi A;
    public final MenuC0129di z;

    public SubMenuC0166ep(Context context, MenuC0129di menuC0129di, C0221gi c0221gi) {
        super(context);
        this.z = menuC0129di;
        this.A = c0221gi;
    }

    @Override // rikka.shizuku.MenuC0129di
    public final boolean d(C0221gi c0221gi) {
        return this.z.d(c0221gi);
    }

    @Override // rikka.shizuku.MenuC0129di
    public final boolean e(MenuC0129di menuC0129di, MenuItem menuItem) {
        return super.e(menuC0129di, menuItem) || this.z.e(menuC0129di, menuItem);
    }

    @Override // rikka.shizuku.MenuC0129di
    public final boolean f(C0221gi c0221gi) {
        return this.z.f(c0221gi);
    }

    @Override // android.view.SubMenu
    public final MenuItem getItem() {
        return this.A;
    }

    @Override // rikka.shizuku.MenuC0129di
    public final String j() {
        C0221gi c0221gi = this.A;
        int i = c0221gi != null ? c0221gi.a : 0;
        if (i == 0) {
            return null;
        }
        return AbstractC0535qo.b(i, "android:menu:actionviewstates:");
    }

    @Override // rikka.shizuku.MenuC0129di
    public final MenuC0129di k() {
        return this.z.k();
    }

    @Override // rikka.shizuku.MenuC0129di
    public final boolean m() {
        return this.z.m();
    }

    @Override // rikka.shizuku.MenuC0129di
    public final boolean n() {
        return this.z.n();
    }

    @Override // rikka.shizuku.MenuC0129di
    public final boolean o() {
        return this.z.o();
    }

    @Override // rikka.shizuku.MenuC0129di, android.view.Menu
    public final void setGroupDividerEnabled(boolean z) {
        this.z.setGroupDividerEnabled(z);
    }

    @Override // android.view.SubMenu
    public final SubMenu setHeaderIcon(Drawable drawable) {
        u(0, null, 0, drawable, null);
        return this;
    }

    @Override // android.view.SubMenu
    public final SubMenu setHeaderTitle(CharSequence charSequence) {
        u(0, charSequence, 0, null, null);
        return this;
    }

    @Override // android.view.SubMenu
    public final SubMenu setHeaderView(View view) {
        u(0, null, 0, null, view);
        return this;
    }

    @Override // android.view.SubMenu
    public final SubMenu setIcon(Drawable drawable) {
        this.A.setIcon(drawable);
        return this;
    }

    @Override // rikka.shizuku.MenuC0129di, android.view.Menu
    public final void setQwertyMode(boolean z) {
        this.z.setQwertyMode(z);
    }

    @Override // android.view.SubMenu
    public final SubMenu setHeaderIcon(int i) {
        u(0, null, i, null, null);
        return this;
    }

    @Override // android.view.SubMenu
    public final SubMenu setHeaderTitle(int i) {
        u(i, null, 0, null, null);
        return this;
    }

    @Override // android.view.SubMenu
    public final SubMenu setIcon(int i) {
        this.A.setIcon(i);
        return this;
    }
}
