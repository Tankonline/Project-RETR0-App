package rikka.shizuku;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewConfiguration;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* JADX INFO: renamed from: rikka.shizuku.di, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class MenuC0129di implements Menu {
    public static final int[] y = {1, 4, 5, 3, 2, 0};
    public final Context a;
    public final Resources b;
    public boolean c;
    public final boolean d;
    public InterfaceC0067bi e;
    public final ArrayList f;
    public final ArrayList g;
    public boolean h;
    public final ArrayList i;
    public final ArrayList j;
    public boolean k;
    public CharSequence m;
    public Drawable n;
    public View o;
    public C0221gi v;
    public boolean x;
    public int l = 0;
    public boolean p = false;
    public boolean q = false;
    public boolean r = false;
    public boolean s = false;
    public final ArrayList t = new ArrayList();
    public final CopyOnWriteArrayList u = new CopyOnWriteArrayList();
    public boolean w = false;

    public MenuC0129di(Context context) {
        boolean zI;
        boolean z = false;
        this.a = context;
        Resources resources = context.getResources();
        this.b = resources;
        this.f = new ArrayList();
        this.g = new ArrayList();
        this.h = true;
        this.i = new ArrayList();
        this.j = new ArrayList();
        this.k = true;
        if (resources.getConfiguration().keyboard != 1) {
            ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
            Method method = Is.a;
            if (Build.VERSION.SDK_INT >= 28) {
                zI = G9.i(viewConfiguration);
            } else {
                Resources resources2 = context.getResources();
                int identifier = resources2.getIdentifier("config_showMenuShortcutsWhenKeyboardPresent", "bool", "android");
                zI = identifier != 0 && resources2.getBoolean(identifier);
            }
            if (zI) {
                z = true;
            }
        }
        this.d = z;
    }

    public final C0221gi a(int i, int i2, int i3, CharSequence charSequence) {
        int i4;
        int i5 = ((-65536) & i3) >> 16;
        if (i5 < 0 || i5 >= 6) {
            throw new IllegalArgumentException("order does not contain a valid category.");
        }
        int i6 = (y[i5] << 16) | (65535 & i3);
        C0221gi c0221gi = new C0221gi(this, i, i2, i3, i6, charSequence, this.l);
        ArrayList arrayList = this.f;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            if (((C0221gi) arrayList.get(size)).d <= i6) {
                i4 = size + 1;
                arrayList.add(i4, c0221gi);
                p(true);
                return c0221gi;
            }
        }
        i4 = 0;
        arrayList.add(i4, c0221gi);
        p(true);
        return c0221gi;
    }

    @Override // android.view.Menu
    public final MenuItem add(CharSequence charSequence) {
        return a(0, 0, 0, charSequence);
    }

    @Override // android.view.Menu
    public final int addIntentOptions(int i, int i2, int i3, ComponentName componentName, Intent[] intentArr, Intent intent, int i4, MenuItem[] menuItemArr) {
        int i5;
        PackageManager packageManager = this.a.getPackageManager();
        List<ResolveInfo> listQueryIntentActivityOptions = packageManager.queryIntentActivityOptions(componentName, intentArr, intent, 0);
        int size = listQueryIntentActivityOptions != null ? listQueryIntentActivityOptions.size() : 0;
        if ((i4 & 1) == 0) {
            removeGroup(i);
        }
        for (int i6 = 0; i6 < size; i6++) {
            ResolveInfo resolveInfo = listQueryIntentActivityOptions.get(i6);
            int i7 = resolveInfo.specificIndex;
            Intent intent2 = new Intent(i7 < 0 ? intent : intentArr[i7]);
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            intent2.setComponent(new ComponentName(activityInfo.applicationInfo.packageName, activityInfo.name));
            C0221gi c0221giA = a(i, i2, i3, resolveInfo.loadLabel(packageManager));
            c0221giA.setIcon(resolveInfo.loadIcon(packageManager));
            c0221giA.g = intent2;
            if (menuItemArr != null && (i5 = resolveInfo.specificIndex) >= 0) {
                menuItemArr[i5] = c0221giA;
            }
        }
        return size;
    }

    @Override // android.view.Menu
    public final SubMenu addSubMenu(CharSequence charSequence) {
        return addSubMenu(0, 0, 0, charSequence);
    }

    public final void b(InterfaceC0683vi interfaceC0683vi, Context context) {
        this.u.add(new WeakReference(interfaceC0683vi));
        interfaceC0683vi.k(context, this);
        this.k = true;
    }

    public final void c(boolean z) {
        if (this.s) {
            return;
        }
        this.s = true;
        CopyOnWriteArrayList<WeakReference> copyOnWriteArrayList = this.u;
        for (WeakReference weakReference : copyOnWriteArrayList) {
            InterfaceC0683vi interfaceC0683vi = (InterfaceC0683vi) weakReference.get();
            if (interfaceC0683vi == null) {
                copyOnWriteArrayList.remove(weakReference);
            } else {
                interfaceC0683vi.b(this, z);
            }
        }
        this.s = false;
    }

    @Override // android.view.Menu
    public final void clear() {
        C0221gi c0221gi = this.v;
        if (c0221gi != null) {
            d(c0221gi);
        }
        this.f.clear();
        p(true);
    }

    public final void clearHeader() {
        this.n = null;
        this.m = null;
        this.o = null;
        p(false);
    }

    @Override // android.view.Menu
    public final void close() {
        c(true);
    }

    public boolean d(C0221gi c0221gi) {
        CopyOnWriteArrayList<WeakReference> copyOnWriteArrayList = this.u;
        boolean zD = false;
        if (!copyOnWriteArrayList.isEmpty() && this.v == c0221gi) {
            w();
            for (WeakReference weakReference : copyOnWriteArrayList) {
                InterfaceC0683vi interfaceC0683vi = (InterfaceC0683vi) weakReference.get();
                if (interfaceC0683vi != null) {
                    zD = interfaceC0683vi.d(c0221gi);
                    if (zD) {
                        break;
                    }
                } else {
                    copyOnWriteArrayList.remove(weakReference);
                }
            }
            v();
            if (zD) {
                this.v = null;
            }
        }
        return zD;
    }

    public boolean e(MenuC0129di menuC0129di, MenuItem menuItem) {
        InterfaceC0067bi interfaceC0067bi = this.e;
        return interfaceC0067bi != null && interfaceC0067bi.n(menuC0129di, menuItem);
    }

    public boolean f(C0221gi c0221gi) {
        CopyOnWriteArrayList<WeakReference> copyOnWriteArrayList = this.u;
        boolean zH = false;
        if (copyOnWriteArrayList.isEmpty()) {
            return false;
        }
        w();
        for (WeakReference weakReference : copyOnWriteArrayList) {
            InterfaceC0683vi interfaceC0683vi = (InterfaceC0683vi) weakReference.get();
            if (interfaceC0683vi != null) {
                zH = interfaceC0683vi.h(c0221gi);
                if (zH) {
                    break;
                }
            } else {
                copyOnWriteArrayList.remove(weakReference);
            }
        }
        v();
        if (zH) {
            this.v = c0221gi;
        }
        return zH;
    }

    @Override // android.view.Menu
    public final MenuItem findItem(int i) {
        MenuItem menuItemFindItem;
        ArrayList arrayList = this.f;
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            C0221gi c0221gi = (C0221gi) arrayList.get(i2);
            if (c0221gi.a == i) {
                return c0221gi;
            }
            if (c0221gi.hasSubMenu() && (menuItemFindItem = c0221gi.o.findItem(i)) != null) {
                return menuItemFindItem;
            }
        }
        return null;
    }

    public final C0221gi g(int i, KeyEvent keyEvent) {
        ArrayList arrayList = this.t;
        arrayList.clear();
        h(arrayList, i, keyEvent);
        if (arrayList.isEmpty()) {
            return null;
        }
        int metaState = keyEvent.getMetaState();
        KeyCharacterMap.KeyData keyData = new KeyCharacterMap.KeyData();
        keyEvent.getKeyData(keyData);
        int size = arrayList.size();
        if (size == 1) {
            return (C0221gi) arrayList.get(0);
        }
        boolean zN = n();
        for (int i2 = 0; i2 < size; i2++) {
            C0221gi c0221gi = (C0221gi) arrayList.get(i2);
            char c = zN ? c0221gi.j : c0221gi.h;
            char[] cArr = keyData.meta;
            if ((c == cArr[0] && (metaState & 2) == 0) || ((c == cArr[2] && (metaState & 2) != 0) || (zN && c == '\b' && i == 67))) {
                return c0221gi;
            }
        }
        return null;
    }

    @Override // android.view.Menu
    public final MenuItem getItem(int i) {
        return (MenuItem) this.f.get(i);
    }

    public final void h(ArrayList arrayList, int i, KeyEvent keyEvent) {
        boolean zN = n();
        int modifiers = keyEvent.getModifiers();
        KeyCharacterMap.KeyData keyData = new KeyCharacterMap.KeyData();
        if (keyEvent.getKeyData(keyData) || i == 67) {
            ArrayList arrayList2 = this.f;
            int size = arrayList2.size();
            for (int i2 = 0; i2 < size; i2++) {
                C0221gi c0221gi = (C0221gi) arrayList2.get(i2);
                if (c0221gi.hasSubMenu()) {
                    c0221gi.o.h(arrayList, i, keyEvent);
                }
                char c = zN ? c0221gi.j : c0221gi.h;
                if ((modifiers & 69647) == ((zN ? c0221gi.k : c0221gi.i) & 69647) && c != 0) {
                    char[] cArr = keyData.meta;
                    if ((c == cArr[0] || c == cArr[2] || (zN && c == '\b' && i == 67)) && c0221gi.isEnabled()) {
                        arrayList.add(c0221gi);
                    }
                }
            }
        }
    }

    @Override // android.view.Menu
    public final boolean hasVisibleItems() {
        if (this.x) {
            return true;
        }
        ArrayList arrayList = this.f;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            if (((C0221gi) arrayList.get(i)).isVisible()) {
                return true;
            }
        }
        return false;
    }

    public final void i() {
        ArrayList arrayListL = l();
        if (this.k) {
            CopyOnWriteArrayList<WeakReference> copyOnWriteArrayList = this.u;
            boolean zG = false;
            for (WeakReference weakReference : copyOnWriteArrayList) {
                InterfaceC0683vi interfaceC0683vi = (InterfaceC0683vi) weakReference.get();
                if (interfaceC0683vi == null) {
                    copyOnWriteArrayList.remove(weakReference);
                } else {
                    zG |= interfaceC0683vi.g();
                }
            }
            ArrayList arrayList = this.i;
            ArrayList arrayList2 = this.j;
            if (zG) {
                arrayList.clear();
                arrayList2.clear();
                int size = arrayListL.size();
                for (int i = 0; i < size; i++) {
                    C0221gi c0221gi = (C0221gi) arrayListL.get(i);
                    if ((c0221gi.x & 32) == 32) {
                        arrayList.add(c0221gi);
                    } else {
                        arrayList2.add(c0221gi);
                    }
                }
            } else {
                arrayList.clear();
                arrayList2.clear();
                arrayList2.addAll(l());
            }
            this.k = false;
        }
    }

    @Override // android.view.Menu
    public final boolean isShortcutKey(int i, KeyEvent keyEvent) {
        return g(i, keyEvent) != null;
    }

    public String j() {
        return "android:menu:actionviewstates";
    }

    public final ArrayList l() {
        boolean z = this.h;
        ArrayList arrayList = this.g;
        if (!z) {
            return arrayList;
        }
        arrayList.clear();
        ArrayList arrayList2 = this.f;
        int size = arrayList2.size();
        for (int i = 0; i < size; i++) {
            C0221gi c0221gi = (C0221gi) arrayList2.get(i);
            if (c0221gi.isVisible()) {
                arrayList.add(c0221gi);
            }
        }
        this.h = false;
        this.k = true;
        return arrayList;
    }

    public boolean m() {
        return this.w;
    }

    public boolean n() {
        return this.c;
    }

    public boolean o() {
        return this.d;
    }

    public final void p(boolean z) {
        if (this.p) {
            this.q = true;
            if (z) {
                this.r = true;
                return;
            }
            return;
        }
        if (z) {
            this.h = true;
            this.k = true;
        }
        CopyOnWriteArrayList<WeakReference> copyOnWriteArrayList = this.u;
        if (copyOnWriteArrayList.isEmpty()) {
            return;
        }
        w();
        for (WeakReference weakReference : copyOnWriteArrayList) {
            InterfaceC0683vi interfaceC0683vi = (InterfaceC0683vi) weakReference.get();
            if (interfaceC0683vi == null) {
                copyOnWriteArrayList.remove(weakReference);
            } else {
                interfaceC0683vi.i();
            }
        }
        v();
    }

    @Override // android.view.Menu
    public final boolean performIdentifierAction(int i, int i2) {
        return q(findItem(i), null, i2);
    }

    @Override // android.view.Menu
    public final boolean performShortcut(int i, KeyEvent keyEvent, int i2) {
        C0221gi c0221giG = g(i, keyEvent);
        boolean zQ = c0221giG != null ? q(c0221giG, null, i2) : false;
        if ((i2 & 2) != 0) {
            c(true);
        }
        return zQ;
    }

    /* JADX WARN: Code duplicated, block: B:11:0x0018  */
    /* JADX WARN: Code duplicated, block: B:32:0x0051  */
    /* JADX WARN: Code duplicated, block: B:35:0x0058  */
    /* JADX WARN: Code duplicated, block: B:37:0x005f  */
    /* JADX WARN: Code duplicated, block: B:38:0x0064  */
    /* JADX WARN: Code duplicated, block: B:45:0x0075  */
    /* JADX WARN: Code duplicated, block: B:47:0x0079  */
    /* JADX WARN: Code duplicated, block: B:50:0x0082  */
    /* JADX WARN: Code duplicated, block: B:53:0x0094  */
    /* JADX WARN: Code duplicated, block: B:57:0x00a4 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:58:0x00a6  */
    /* JADX WARN: Code duplicated, block: B:62:0x00b4  */
    /* JADX WARN: Code duplicated, block: B:69:0x00d0  */
    /* JADX WARN: Code duplicated, block: B:75:0x00c6 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:76:0x00c8 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:77:0x00c2 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:79:0x00ae A[SYNTHETIC] */
    public final boolean q(MenuItem menuItem, InterfaceC0683vi interfaceC0683vi, int i) {
        ActionProviderVisibilityListenerC0252hi actionProviderVisibilityListenerC0252hi;
        boolean zExpandActionView;
        ActionProviderVisibilityListenerC0252hi actionProviderVisibilityListenerC0252hi2;
        boolean z;
        SubMenuC0166ep subMenuC0166ep;
        CopyOnWriteArrayList<WeakReference> copyOnWriteArrayList;
        InterfaceC0683vi interfaceC0683vi2;
        C0221gi c0221gi = (C0221gi) menuItem;
        boolean zF = false;
        if (c0221gi == null || !c0221gi.isEnabled()) {
            return false;
        }
        MenuItem.OnMenuItemClickListener onMenuItemClickListener = c0221gi.p;
        if (onMenuItemClickListener == null || !onMenuItemClickListener.onMenuItemClick(c0221gi)) {
            MenuC0129di menuC0129di = c0221gi.n;
            if (menuC0129di.e(menuC0129di, c0221gi)) {
                zExpandActionView = true;
            } else {
                Intent intent = c0221gi.g;
                if (intent != null) {
                    try {
                        menuC0129di.a.startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        Log.e("MenuItemImpl", "Can't find activity to handle intent; ignoring", e);
                        actionProviderVisibilityListenerC0252hi = c0221gi.A;
                        if (actionProviderVisibilityListenerC0252hi == null) {
                        }
                        zExpandActionView = false;
                        actionProviderVisibilityListenerC0252hi2 = c0221gi.A;
                        if (actionProviderVisibilityListenerC0252hi2 == null) {
                            z = false;
                        } else {
                            z = false;
                        }
                        if (c0221gi.e()) {
                            zExpandActionView |= c0221gi.expandActionView();
                            if (zExpandActionView) {
                                c(true);
                            }
                        } else if (c0221gi.hasSubMenu()) {
                            if ((i & 4) == 0) {
                                c(false);
                            }
                            if (!c0221gi.hasSubMenu()) {
                                SubMenuC0166ep subMenuC0166ep2 = new SubMenuC0166ep(this.a, this, c0221gi);
                                c0221gi.o = subMenuC0166ep2;
                                subMenuC0166ep2.setHeaderTitle(c0221gi.e);
                            }
                            subMenuC0166ep = c0221gi.o;
                            if (z) {
                                MenuItemC0343ki menuItemC0343ki = actionProviderVisibilityListenerC0252hi2.b;
                                actionProviderVisibilityListenerC0252hi2.a.onPrepareSubMenu(subMenuC0166ep);
                            }
                            copyOnWriteArrayList = this.u;
                            if (!copyOnWriteArrayList.isEmpty()) {
                                if (interfaceC0683vi != null) {
                                }
                                for (WeakReference weakReference : copyOnWriteArrayList) {
                                    interfaceC0683vi2 = (InterfaceC0683vi) weakReference.get();
                                    if (interfaceC0683vi2 == null) {
                                        copyOnWriteArrayList.remove(weakReference);
                                    } else if (!zF) {
                                        zF = interfaceC0683vi2.f(subMenuC0166ep);
                                    }
                                }
                            }
                            zExpandActionView |= zF;
                            if (!zExpandActionView) {
                                c(true);
                            }
                        } else {
                            if ((i & 4) == 0) {
                                c(false);
                            }
                            if (!c0221gi.hasSubMenu()) {
                                SubMenuC0166ep subMenuC0166ep3 = new SubMenuC0166ep(this.a, this, c0221gi);
                                c0221gi.o = subMenuC0166ep3;
                                subMenuC0166ep3.setHeaderTitle(c0221gi.e);
                            }
                            subMenuC0166ep = c0221gi.o;
                            if (z) {
                                MenuItemC0343ki menuItemC0343ki2 = actionProviderVisibilityListenerC0252hi2.b;
                                actionProviderVisibilityListenerC0252hi2.a.onPrepareSubMenu(subMenuC0166ep);
                            }
                            copyOnWriteArrayList = this.u;
                            if (!copyOnWriteArrayList.isEmpty()) {
                                zF = interfaceC0683vi != null ? interfaceC0683vi.f(subMenuC0166ep) : false;
                                while (r8.hasNext()) {
                                    interfaceC0683vi2 = (InterfaceC0683vi) weakReference.get();
                                    if (interfaceC0683vi2 == null) {
                                        copyOnWriteArrayList.remove(weakReference);
                                    } else if (!zF) {
                                        zF = interfaceC0683vi2.f(subMenuC0166ep);
                                    }
                                }
                            }
                            zExpandActionView |= zF;
                            if (!zExpandActionView) {
                                c(true);
                            }
                        }
                        return zExpandActionView;
                    }
                    zExpandActionView = true;
                } else {
                    actionProviderVisibilityListenerC0252hi = c0221gi.A;
                    if (actionProviderVisibilityListenerC0252hi == null && actionProviderVisibilityListenerC0252hi.a.onPerformDefaultAction()) {
                        zExpandActionView = true;
                    } else {
                        zExpandActionView = false;
                    }
                }
            }
        } else {
            zExpandActionView = true;
        }
        actionProviderVisibilityListenerC0252hi2 = c0221gi.A;
        if (actionProviderVisibilityListenerC0252hi2 == null && actionProviderVisibilityListenerC0252hi2.a.hasSubMenu()) {
            z = true;
        } else {
            z = false;
        }
        if (c0221gi.e()) {
            zExpandActionView |= c0221gi.expandActionView();
            if (zExpandActionView) {
                c(true);
            }
        } else if (c0221gi.hasSubMenu() || z) {
            if ((i & 4) == 0) {
                c(false);
            }
            if (!c0221gi.hasSubMenu()) {
                SubMenuC0166ep subMenuC0166ep4 = new SubMenuC0166ep(this.a, this, c0221gi);
                c0221gi.o = subMenuC0166ep4;
                subMenuC0166ep4.setHeaderTitle(c0221gi.e);
            }
            subMenuC0166ep = c0221gi.o;
            if (z) {
                MenuItemC0343ki menuItemC0343ki3 = actionProviderVisibilityListenerC0252hi2.b;
                actionProviderVisibilityListenerC0252hi2.a.onPrepareSubMenu(subMenuC0166ep);
            }
            copyOnWriteArrayList = this.u;
            if (!copyOnWriteArrayList.isEmpty()) {
                if (interfaceC0683vi != null) {
                }
                while (r8.hasNext()) {
                    interfaceC0683vi2 = (InterfaceC0683vi) weakReference.get();
                    if (interfaceC0683vi2 == null) {
                        copyOnWriteArrayList.remove(weakReference);
                    } else if (!zF) {
                        zF = interfaceC0683vi2.f(subMenuC0166ep);
                    }
                }
            }
            zExpandActionView |= zF;
            if (!zExpandActionView) {
                c(true);
            }
        } else if ((i & 1) == 0) {
            c(true);
        }
        return zExpandActionView;
    }

    public final void r(InterfaceC0683vi interfaceC0683vi) {
        CopyOnWriteArrayList<WeakReference> copyOnWriteArrayList = this.u;
        for (WeakReference weakReference : copyOnWriteArrayList) {
            InterfaceC0683vi interfaceC0683vi2 = (InterfaceC0683vi) weakReference.get();
            if (interfaceC0683vi2 == null || interfaceC0683vi2 == interfaceC0683vi) {
                copyOnWriteArrayList.remove(weakReference);
            }
        }
    }

    @Override // android.view.Menu
    public final void removeGroup(int i) {
        ArrayList arrayList = this.f;
        int size = arrayList.size();
        int i2 = 0;
        int i3 = 0;
        while (true) {
            if (i3 >= size) {
                i3 = -1;
                break;
            } else if (((C0221gi) arrayList.get(i3)).b == i) {
                break;
            } else {
                i3++;
            }
        }
        if (i3 >= 0) {
            int size2 = arrayList.size() - i3;
            while (true) {
                int i4 = i2 + 1;
                if (i2 >= size2 || ((C0221gi) arrayList.get(i3)).b != i) {
                    break;
                }
                if (i3 >= 0) {
                    ArrayList arrayList2 = this.f;
                    if (i3 < arrayList2.size()) {
                        arrayList2.remove(i3);
                    }
                }
                i2 = i4;
            }
            p(true);
        }
    }

    @Override // android.view.Menu
    public final void removeItem(int i) {
        ArrayList arrayList = this.f;
        int size = arrayList.size();
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                i2 = -1;
                break;
            } else if (((C0221gi) arrayList.get(i2)).a == i) {
                break;
            } else {
                i2++;
            }
        }
        if (i2 >= 0) {
            ArrayList arrayList2 = this.f;
            if (i2 >= arrayList2.size()) {
                return;
            }
            arrayList2.remove(i2);
            p(true);
        }
    }

    public final void s(Bundle bundle) {
        MenuItem menuItemFindItem;
        if (bundle == null) {
            return;
        }
        SparseArray<Parcelable> sparseParcelableArray = bundle.getSparseParcelableArray(j());
        int size = this.f.size();
        for (int i = 0; i < size; i++) {
            MenuItem item = getItem(i);
            View actionView = item.getActionView();
            if (actionView != null && actionView.getId() != -1) {
                actionView.restoreHierarchyState(sparseParcelableArray);
            }
            if (item.hasSubMenu()) {
                ((SubMenuC0166ep) item.getSubMenu()).s(bundle);
            }
        }
        int i2 = bundle.getInt("android:menu:expandedactionview");
        if (i2 <= 0 || (menuItemFindItem = findItem(i2)) == null) {
            return;
        }
        menuItemFindItem.expandActionView();
    }

    @Override // android.view.Menu
    public final void setGroupCheckable(int i, boolean z, boolean z2) {
        ArrayList arrayList = this.f;
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            C0221gi c0221gi = (C0221gi) arrayList.get(i2);
            if (c0221gi.b == i) {
                c0221gi.x = (c0221gi.x & (-5)) | (z2 ? 4 : 0);
                c0221gi.setCheckable(z);
            }
        }
    }

    @Override // android.view.Menu
    public void setGroupDividerEnabled(boolean z) {
        this.w = z;
    }

    @Override // android.view.Menu
    public final void setGroupEnabled(int i, boolean z) {
        ArrayList arrayList = this.f;
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            C0221gi c0221gi = (C0221gi) arrayList.get(i2);
            if (c0221gi.b == i) {
                c0221gi.setEnabled(z);
            }
        }
    }

    @Override // android.view.Menu
    public final void setGroupVisible(int i, boolean z) {
        ArrayList arrayList = this.f;
        int size = arrayList.size();
        boolean z2 = false;
        for (int i2 = 0; i2 < size; i2++) {
            C0221gi c0221gi = (C0221gi) arrayList.get(i2);
            if (c0221gi.b == i) {
                int i3 = c0221gi.x;
                int i4 = (i3 & (-9)) | (z ? 0 : 8);
                c0221gi.x = i4;
                if (i3 != i4) {
                    z2 = true;
                }
            }
        }
        if (z2) {
            p(true);
        }
    }

    @Override // android.view.Menu
    public void setQwertyMode(boolean z) {
        this.c = z;
        p(false);
    }

    @Override // android.view.Menu
    public final int size() {
        return this.f.size();
    }

    public final void t(Bundle bundle) {
        int size = this.f.size();
        SparseArray<? extends Parcelable> sparseArray = null;
        for (int i = 0; i < size; i++) {
            MenuItem item = getItem(i);
            View actionView = item.getActionView();
            if (actionView != null && actionView.getId() != -1) {
                if (sparseArray == null) {
                    sparseArray = new SparseArray<>();
                }
                actionView.saveHierarchyState(sparseArray);
                if (item.isActionViewExpanded()) {
                    bundle.putInt("android:menu:expandedactionview", item.getItemId());
                }
            }
            if (item.hasSubMenu()) {
                ((SubMenuC0166ep) item.getSubMenu()).t(bundle);
            }
        }
        if (sparseArray != null) {
            bundle.putSparseParcelableArray(j(), sparseArray);
        }
    }

    public final void u(int i, CharSequence charSequence, int i2, Drawable drawable, View view) {
        if (view != null) {
            this.o = view;
            this.m = null;
            this.n = null;
        } else {
            if (i > 0) {
                this.m = this.b.getText(i);
            } else if (charSequence != null) {
                this.m = charSequence;
            }
            if (i2 > 0) {
                this.n = this.a.getDrawable(i2);
            } else if (drawable != null) {
                this.n = drawable;
            }
            this.o = null;
        }
        p(false);
    }

    public final void v() {
        this.p = false;
        if (this.q) {
            this.q = false;
            p(this.r);
        }
    }

    public final void w() {
        if (this.p) {
            return;
        }
        this.p = true;
        this.q = false;
        this.r = false;
    }

    @Override // android.view.Menu
    public final MenuItem add(int i) {
        return a(0, 0, 0, this.b.getString(i));
    }

    @Override // android.view.Menu
    public final SubMenu addSubMenu(int i) {
        return addSubMenu(0, 0, 0, this.b.getString(i));
    }

    @Override // android.view.Menu
    public final MenuItem add(int i, int i2, int i3, CharSequence charSequence) {
        return a(i, i2, i3, charSequence);
    }

    @Override // android.view.Menu
    public final SubMenu addSubMenu(int i, int i2, int i3, CharSequence charSequence) {
        C0221gi c0221giA = a(i, i2, i3, charSequence);
        SubMenuC0166ep subMenuC0166ep = new SubMenuC0166ep(this.a, this, c0221giA);
        c0221giA.o = subMenuC0166ep;
        subMenuC0166ep.setHeaderTitle(c0221giA.e);
        return subMenuC0166ep;
    }

    @Override // android.view.Menu
    public final MenuItem add(int i, int i2, int i3, int i4) {
        return a(i, i2, i3, this.b.getString(i4));
    }

    @Override // android.view.Menu
    public final SubMenu addSubMenu(int i, int i2, int i3, int i4) {
        return addSubMenu(i, i2, i3, this.b.getString(i4));
    }

    public MenuC0129di k() {
        return this;
    }
}
