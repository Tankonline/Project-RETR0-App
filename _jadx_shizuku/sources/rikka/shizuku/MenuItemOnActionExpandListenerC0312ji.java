package rikka.shizuku;

import android.view.MenuItem;

/* JADX INFO: renamed from: rikka.shizuku.ji, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class MenuItemOnActionExpandListenerC0312ji implements MenuItem.OnActionExpandListener {
    public final MenuItem.OnActionExpandListener a;
    public final /* synthetic */ MenuItemC0343ki b;

    public MenuItemOnActionExpandListenerC0312ji(MenuItemC0343ki menuItemC0343ki, MenuItem.OnActionExpandListener onActionExpandListener) {
        this.b = menuItemC0343ki;
        this.a = onActionExpandListener;
    }

    @Override // android.view.MenuItem.OnActionExpandListener
    public final boolean onMenuItemActionCollapse(MenuItem menuItem) {
        return this.a.onMenuItemActionCollapse(this.b.f(menuItem));
    }

    @Override // android.view.MenuItem.OnActionExpandListener
    public final boolean onMenuItemActionExpand(MenuItem menuItem) {
        return this.a.onMenuItemActionExpand(this.b.f(menuItem));
    }
}
