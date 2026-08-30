package rikka.shizuku;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Build;
import android.util.Log;
import android.view.InflateException;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/* JADX INFO: renamed from: rikka.shizuku.kp, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0350kp {
    public ActionProviderVisibilityListenerC0252hi A;
    public CharSequence B;
    public CharSequence C;
    public final /* synthetic */ C0381lp F;
    public final Menu a;
    public boolean h;
    public int i;
    public int j;
    public CharSequence k;
    public CharSequence l;
    public int m;
    public char n;
    public int o;
    public char p;
    public int q;
    public int r;
    public boolean s;
    public boolean t;
    public boolean u;
    public int v;
    public int w;
    public String x;
    public String y;
    public String z;
    public ColorStateList D = null;
    public PorterDuff.Mode E = null;
    public int b = 0;
    public int c = 0;
    public int d = 0;
    public int e = 0;
    public boolean f = true;
    public boolean g = true;

    public C0350kp(C0381lp c0381lp, Menu menu) {
        this.F = c0381lp;
        this.a = menu;
    }

    public final Object a(String str, Class[] clsArr, Object[] objArr) {
        try {
            Constructor<?> constructor = Class.forName(str, false, this.F.c.getClassLoader()).getConstructor(clsArr);
            constructor.setAccessible(true);
            return constructor.newInstance(objArr);
        } catch (Exception e) {
            Log.w("SupportMenuInflater", "Cannot instantiate class: " + str, e);
            return null;
        }
    }

    public final void b(MenuItem menuItem) {
        boolean z = false;
        menuItem.setChecked(this.s).setVisible(this.t).setEnabled(this.u).setCheckable(this.r >= 1).setTitleCondensed(this.l).setIcon(this.m);
        int i = this.v;
        if (i >= 0) {
            menuItem.setShowAsAction(i);
        }
        String str = this.z;
        C0381lp c0381lp = this.F;
        if (str != null) {
            if (c0381lp.c.isRestricted()) {
                throw new IllegalStateException("The android:onClick attribute cannot be used within a restricted context");
            }
            if (c0381lp.d == null) {
                c0381lp.d = C0381lp.a(c0381lp.c);
            }
            Object obj = c0381lp.d;
            String str2 = this.z;
            MenuItemOnMenuItemClickListenerC0319jp menuItemOnMenuItemClickListenerC0319jp = new MenuItemOnMenuItemClickListenerC0319jp();
            menuItemOnMenuItemClickListenerC0319jp.b = obj;
            Class<?> cls = obj.getClass();
            try {
                menuItemOnMenuItemClickListenerC0319jp.c = cls.getMethod(str2, MenuItemOnMenuItemClickListenerC0319jp.d);
                menuItem.setOnMenuItemClickListener(menuItemOnMenuItemClickListenerC0319jp);
            } catch (Exception e) {
                StringBuilder sbE = AbstractC0535qo.e("Couldn't resolve menu item onClick handler ", str2, " in class ");
                sbE.append(cls.getName());
                InflateException inflateException = new InflateException(sbE.toString());
                inflateException.initCause(e);
                throw inflateException;
            }
        }
        if (this.r >= 2) {
            if (menuItem instanceof C0221gi) {
                C0221gi c0221gi = (C0221gi) menuItem;
                c0221gi.x = (c0221gi.x & (-5)) | 4;
            } else if (menuItem instanceof MenuItemC0343ki) {
                MenuItemC0343ki menuItemC0343ki = (MenuItemC0343ki) menuItem;
                try {
                    Method method = menuItemC0343ki.d;
                    InterfaceMenuItemC0412mp interfaceMenuItemC0412mp = menuItemC0343ki.c;
                    if (method == null) {
                        menuItemC0343ki.d = interfaceMenuItemC0412mp.getClass().getDeclaredMethod("setExclusiveCheckable", Boolean.TYPE);
                    }
                    menuItemC0343ki.d.invoke(interfaceMenuItemC0412mp, Boolean.TRUE);
                } catch (Exception e2) {
                    Log.w("MenuItemWrapper", "Error while calling setExclusiveCheckable", e2);
                }
            }
        }
        String str3 = this.x;
        if (str3 != null) {
            menuItem.setActionView((View) a(str3, C0381lp.e, c0381lp.a));
            z = true;
        }
        int i2 = this.w;
        if (i2 > 0) {
            if (z) {
                Log.w("SupportMenuInflater", "Ignoring attribute 'itemActionViewLayout'. Action view already specified.");
            } else {
                menuItem.setActionView(i2);
            }
        }
        ActionProviderVisibilityListenerC0252hi actionProviderVisibilityListenerC0252hi = this.A;
        if (actionProviderVisibilityListenerC0252hi != null) {
            if (menuItem instanceof InterfaceMenuItemC0412mp) {
                ((InterfaceMenuItemC0412mp) menuItem).a(actionProviderVisibilityListenerC0252hi);
            } else {
                Log.w("MenuItemCompat", "setActionProvider: item does not implement SupportMenuItem; ignoring");
            }
        }
        CharSequence charSequence = this.B;
        boolean z2 = menuItem instanceof InterfaceMenuItemC0412mp;
        if (z2) {
            ((InterfaceMenuItemC0412mp) menuItem).setContentDescription(charSequence);
        } else if (Build.VERSION.SDK_INT >= 26) {
            AbstractC0190fi.d(menuItem, charSequence);
        }
        CharSequence charSequence2 = this.C;
        if (z2) {
            ((InterfaceMenuItemC0412mp) menuItem).setTooltipText(charSequence2);
        } else if (Build.VERSION.SDK_INT >= 26) {
            AbstractC0190fi.h(menuItem, charSequence2);
        }
        char c = this.n;
        int i3 = this.o;
        if (z2) {
            ((InterfaceMenuItemC0412mp) menuItem).setAlphabeticShortcut(c, i3);
        } else if (Build.VERSION.SDK_INT >= 26) {
            AbstractC0190fi.c(menuItem, c, i3);
        }
        char c2 = this.p;
        int i4 = this.q;
        if (z2) {
            ((InterfaceMenuItemC0412mp) menuItem).setNumericShortcut(c2, i4);
        } else if (Build.VERSION.SDK_INT >= 26) {
            AbstractC0190fi.g(menuItem, c2, i4);
        }
        PorterDuff.Mode mode = this.E;
        if (mode != null) {
            if (z2) {
                ((InterfaceMenuItemC0412mp) menuItem).setIconTintMode(mode);
            } else if (Build.VERSION.SDK_INT >= 26) {
                AbstractC0190fi.f(menuItem, mode);
            }
        }
        ColorStateList colorStateList = this.D;
        if (colorStateList != null) {
            if (z2) {
                ((InterfaceMenuItemC0412mp) menuItem).setIconTintList(colorStateList);
            } else if (Build.VERSION.SDK_INT >= 26) {
                AbstractC0190fi.e(menuItem, colorStateList);
            }
        }
    }
}
