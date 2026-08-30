package rikka.shizuku;

import android.content.Context;
import android.content.IntentFilter;
import android.view.MenuItem;

/* JADX INFO: loaded from: classes.dex */
public abstract class F2 {
    public Object a;
    public Object b;

    public F2(Context context) {
        this.a = context;
    }

    public void c() {
        E2 e2 = (E2) this.a;
        if (e2 != null) {
            try {
                ((I2) this.b).k.unregisterReceiver(e2);
            } catch (IllegalArgumentException unused) {
            }
            this.a = null;
        }
    }

    public abstract IntentFilter d();

    public abstract int e();

    public MenuItem f(MenuItem menuItem) {
        if (!(menuItem instanceof InterfaceMenuItemC0412mp)) {
            return menuItem;
        }
        InterfaceMenuItemC0412mp interfaceMenuItemC0412mp = (InterfaceMenuItemC0412mp) menuItem;
        if (((C0104co) this.b) == null) {
            this.b = new C0104co(0);
        }
        MenuItem menuItem2 = (MenuItem) ((C0104co) this.b).get(interfaceMenuItemC0412mp);
        if (menuItem2 != null) {
            return menuItem2;
        }
        MenuItemC0343ki menuItemC0343ki = new MenuItemC0343ki((Context) this.a, interfaceMenuItemC0412mp);
        ((C0104co) this.b).put(interfaceMenuItemC0412mp, menuItemC0343ki);
        return menuItemC0343ki;
    }

    public abstract void g();

    public void h() {
        c();
        IntentFilter intentFilterD = d();
        if (intentFilterD.countActions() == 0) {
            return;
        }
        if (((E2) this.a) == null) {
            this.a = new E2(this);
        }
        ((I2) this.b).k.registerReceiver((E2) this.a, intentFilterD);
    }

    public F2(I2 i2) {
        this.b = i2;
    }
}
