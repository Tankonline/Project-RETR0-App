package rikka.shizuku;

import android.view.MenuItem;
import java.lang.reflect.Method;

/* JADX INFO: renamed from: rikka.shizuku.jp, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class MenuItemOnMenuItemClickListenerC0319jp implements MenuItem.OnMenuItemClickListener {
    public static final Class[] d = {MenuItem.class};
    public final /* synthetic */ int a = 0;
    public Object b;
    public Object c;

    public /* synthetic */ MenuItemOnMenuItemClickListenerC0319jp() {
    }

    @Override // android.view.MenuItem.OnMenuItemClickListener
    public final boolean onMenuItemClick(MenuItem menuItem) {
        switch (this.a) {
            case 0:
                Method method = (Method) this.c;
                try {
                    Class<?> returnType = method.getReturnType();
                    Class<?> cls = Boolean.TYPE;
                    Object obj = this.b;
                    if (returnType == cls) {
                        return ((Boolean) method.invoke(obj, menuItem)).booleanValue();
                    }
                    method.invoke(obj, menuItem);
                    return true;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            default:
                return ((MenuItem.OnMenuItemClickListener) this.b).onMenuItemClick(((MenuItemC0343ki) this.c).f(menuItem));
        }
    }

    public MenuItemOnMenuItemClickListenerC0319jp(MenuItemC0343ki menuItemC0343ki, MenuItem.OnMenuItemClickListener onMenuItemClickListener) {
        this.c = menuItemC0343ki;
        this.b = onMenuItemClickListener;
    }
}
