package rikka.shizuku;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.widget.ActionMenuView;
import java.util.ArrayList;
import moe.shizuku.privileged.api.R;

/* JADX INFO: loaded from: classes.dex */
public final class C0 implements InterfaceC0683vi {
    public final Context a;
    public Context b;
    public MenuC0129di c;
    public final LayoutInflater d;
    public InterfaceC0652ui e;
    public InterfaceC0745xi h;
    public B0 i;
    public boolean j;
    public boolean k;
    public int l;
    public int m;
    public int n;
    public boolean o;
    public C0758y0 q;
    public C0758y0 r;
    public A0 s;
    public C0789z0 t;
    public final int f = R.layout._0_resource_name_obfuscated_res_0x7f0c0003;
    public final int g = R.layout._0_resource_name_obfuscated_res_0x7f0c0002;
    public final SparseBooleanArray p = new SparseBooleanArray();
    public final C0789z0 u = new C0789z0(this);

    public C0(Context context) {
        this.a = context;
        this.d = LayoutInflater.from(context);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final View a(C0221gi c0221gi, View view, ViewGroup viewGroup) {
        InterfaceC0714wi interfaceC0714wi;
        View actionView = c0221gi.getActionView();
        if (actionView == null || c0221gi.e()) {
            if (view instanceof InterfaceC0714wi) {
                interfaceC0714wi = (InterfaceC0714wi) view;
            } else {
                interfaceC0714wi = (InterfaceC0714wi) this.d.inflate(this.g, viewGroup, false);
            }
            interfaceC0714wi.e(c0221gi);
            ActionMenuItemView actionMenuItemView = (ActionMenuItemView) interfaceC0714wi;
            actionMenuItemView.j = (ActionMenuView) this.h;
            if (this.t == null) {
                this.t = new C0789z0(this);
            }
            actionMenuItemView.l = this.t;
            actionView = (View) interfaceC0714wi;
        }
        actionView.setVisibility(c0221gi.C ? 8 : 0);
        ViewGroup.LayoutParams layoutParams = actionView.getLayoutParams();
        ((ActionMenuView) viewGroup).getClass();
        if (!(layoutParams instanceof E0)) {
            actionView.setLayoutParams(ActionMenuView.n(layoutParams));
        }
        return actionView;
    }

    @Override // rikka.shizuku.InterfaceC0683vi
    public final void b(MenuC0129di menuC0129di, boolean z) {
        e();
        C0758y0 c0758y0 = this.r;
        if (c0758y0 != null && c0758y0.b()) {
            c0758y0.i.dismiss();
        }
        InterfaceC0652ui interfaceC0652ui = this.e;
        if (interfaceC0652ui != null) {
            interfaceC0652ui.b(menuC0129di, z);
        }
    }

    @Override // rikka.shizuku.InterfaceC0683vi
    public final void c(InterfaceC0652ui interfaceC0652ui) {
        throw null;
    }

    @Override // rikka.shizuku.InterfaceC0683vi
    public final boolean d(C0221gi c0221gi) {
        return false;
    }

    public final boolean e() {
        Object obj;
        A0 a0 = this.s;
        if (a0 != null && (obj = this.h) != null) {
            ((View) obj).removeCallbacks(a0);
            this.s = null;
            return true;
        }
        C0758y0 c0758y0 = this.q;
        if (c0758y0 == null) {
            return false;
        }
        if (c0758y0.b()) {
            c0758y0.i.dismiss();
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // rikka.shizuku.InterfaceC0683vi
    public final boolean f(SubMenuC0166ep subMenuC0166ep) {
        boolean z;
        if (subMenuC0166ep.hasVisibleItems()) {
            SubMenuC0166ep subMenuC0166ep2 = subMenuC0166ep;
            while (true) {
                MenuC0129di menuC0129di = subMenuC0166ep2.z;
                if (menuC0129di == this.c) {
                    break;
                }
                subMenuC0166ep2 = (SubMenuC0166ep) menuC0129di;
            }
            ViewGroup viewGroup = (ViewGroup) this.h;
            View view = null;
            view = null;
            if (viewGroup != null) {
                int childCount = viewGroup.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View childAt = viewGroup.getChildAt(i);
                    if ((childAt instanceof InterfaceC0714wi) && ((InterfaceC0714wi) childAt).b() == subMenuC0166ep2.A) {
                        view = childAt;
                        break;
                    }
                }
            }
            if (view != null) {
                subMenuC0166ep.A.getClass();
                int size = subMenuC0166ep.f.size();
                int i2 = 0;
                while (true) {
                    if (i2 >= size) {
                        z = false;
                        break;
                    }
                    MenuItem item = subMenuC0166ep.getItem(i2);
                    if (item.isVisible() && item.getIcon() != null) {
                        z = true;
                        break;
                    }
                    i2++;
                }
                C0758y0 c0758y0 = new C0758y0(this, this.b, subMenuC0166ep, view);
                this.r = c0758y0;
                c0758y0.g = z;
                AbstractC0374li abstractC0374li = c0758y0.i;
                if (abstractC0374li != null) {
                    abstractC0374li.o(z);
                }
                C0758y0 c0758y1 = this.r;
                if (!c0758y1.b()) {
                    if (c0758y1.e == null) {
                        throw new IllegalStateException("MenuPopupHelper cannot be used without an anchor");
                    }
                    c0758y1.d(0, 0, false, false);
                }
                InterfaceC0652ui interfaceC0652ui = this.e;
                if (interfaceC0652ui != null) {
                    interfaceC0652ui.c(subMenuC0166ep);
                }
                return true;
            }
        }
        return false;
    }

    @Override // rikka.shizuku.InterfaceC0683vi
    public final boolean g() {
        int size;
        ArrayList arrayListL;
        int i;
        boolean z;
        C0 c0 = this;
        MenuC0129di menuC0129di = c0.c;
        if (menuC0129di != null) {
            arrayListL = menuC0129di.l();
            size = arrayListL.size();
        } else {
            size = 0;
            arrayListL = null;
        }
        int i2 = c0.n;
        int i3 = c0.m;
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        ViewGroup viewGroup = (ViewGroup) c0.h;
        int i4 = 0;
        boolean z2 = false;
        int i5 = 0;
        int i6 = 0;
        while (true) {
            i = 2;
            z = true;
            if (i4 >= size) {
                break;
            }
            C0221gi c0221gi = (C0221gi) arrayListL.get(i4);
            int i7 = c0221gi.y;
            if ((i7 & 2) == 2) {
                i5++;
            } else if ((i7 & 1) == 1) {
                i6++;
            } else {
                z2 = true;
            }
            if (c0.o && c0221gi.C) {
                i2 = 0;
            }
            i4++;
        }
        if (c0.j && (z2 || i6 + i5 > i2)) {
            i2--;
        }
        int i8 = i2 - i5;
        SparseBooleanArray sparseBooleanArray = c0.p;
        sparseBooleanArray.clear();
        int i9 = 0;
        int i10 = 0;
        while (i9 < size) {
            C0221gi c0221gi2 = (C0221gi) arrayListL.get(i9);
            int i11 = c0221gi2.y;
            boolean z3 = (i11 & 2) == i ? z : false;
            int i12 = c0221gi2.b;
            if (z3) {
                View viewA = c0.a(c0221gi2, null, viewGroup);
                viewA.measure(iMakeMeasureSpec, iMakeMeasureSpec);
                int measuredWidth = viewA.getMeasuredWidth();
                i3 -= measuredWidth;
                if (i10 == 0) {
                    i10 = measuredWidth;
                }
                if (i12 != 0) {
                    sparseBooleanArray.put(i12, z);
                }
                c0221gi2.f(z);
            } else {
                if ((i11 & 1) == z) {
                    boolean z4 = sparseBooleanArray.get(i12);
                    boolean z5 = ((i8 > 0 || z4) && i3 > 0) ? z : false;
                    if (z5) {
                        View viewA2 = c0.a(c0221gi2, null, viewGroup);
                        viewA2.measure(iMakeMeasureSpec, iMakeMeasureSpec);
                        int measuredWidth2 = viewA2.getMeasuredWidth();
                        i3 -= measuredWidth2;
                        if (i10 == 0) {
                            i10 = measuredWidth2;
                        }
                        z5 &= i3 + i10 > 0;
                    }
                    if (z5 && i12 != 0) {
                        sparseBooleanArray.put(i12, true);
                    } else if (z4) {
                        sparseBooleanArray.put(i12, false);
                        for (int i13 = 0; i13 < i9; i13++) {
                            C0221gi c0221gi3 = (C0221gi) arrayListL.get(i13);
                            if (c0221gi3.b == i12) {
                                if ((c0221gi3.x & 32) == 32) {
                                    i8++;
                                }
                                c0221gi3.f(false);
                            }
                        }
                    }
                    if (z5) {
                        i8--;
                    }
                    c0221gi2.f(z5);
                } else {
                    c0221gi2.f(false);
                }
                i9++;
                i = 2;
                c0 = this;
                z = true;
            }
            i9++;
            i = 2;
            c0 = this;
            z = true;
        }
        return z;
    }

    @Override // rikka.shizuku.InterfaceC0683vi
    public final boolean h(C0221gi c0221gi) {
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // rikka.shizuku.InterfaceC0683vi
    public final void i() {
        int i;
        ViewGroup viewGroup = (ViewGroup) this.h;
        ArrayList arrayList = null;
        boolean z = false;
        if (viewGroup != null) {
            MenuC0129di menuC0129di = this.c;
            if (menuC0129di != null) {
                menuC0129di.i();
                ArrayList arrayListL = this.c.l();
                int size = arrayListL.size();
                i = 0;
                for (int i2 = 0; i2 < size; i2++) {
                    C0221gi c0221gi = (C0221gi) arrayListL.get(i2);
                    if ((c0221gi.x & 32) == 32) {
                        View childAt = viewGroup.getChildAt(i);
                        C0221gi c0221giB = childAt instanceof InterfaceC0714wi ? ((InterfaceC0714wi) childAt).b() : null;
                        View viewA = a(c0221gi, childAt, viewGroup);
                        if (c0221gi != c0221giB) {
                            viewA.setPressed(false);
                            viewA.jumpDrawablesToCurrentState();
                        }
                        if (viewA != childAt) {
                            ViewGroup viewGroup2 = (ViewGroup) viewA.getParent();
                            if (viewGroup2 != null) {
                                viewGroup2.removeView(viewA);
                            }
                            ((ViewGroup) this.h).addView(viewA, i);
                        }
                        i++;
                    }
                }
            } else {
                i = 0;
            }
            while (i < viewGroup.getChildCount()) {
                if (viewGroup.getChildAt(i) == this.i) {
                    i++;
                } else {
                    viewGroup.removeViewAt(i);
                }
            }
        }
        ((View) this.h).requestLayout();
        MenuC0129di menuC0129di2 = this.c;
        if (menuC0129di2 != null) {
            menuC0129di2.i();
            ArrayList arrayList2 = menuC0129di2.i;
            int size2 = arrayList2.size();
            for (int i3 = 0; i3 < size2; i3++) {
                ActionProviderVisibilityListenerC0252hi actionProviderVisibilityListenerC0252hi = ((C0221gi) arrayList2.get(i3)).A;
            }
        }
        MenuC0129di menuC0129di3 = this.c;
        if (menuC0129di3 != null) {
            menuC0129di3.i();
            arrayList = menuC0129di3.j;
        }
        if (this.j && arrayList != null) {
            int size3 = arrayList.size();
            if (size3 == 1) {
                z = !((C0221gi) arrayList.get(0)).C;
            } else if (size3 > 0) {
                z = true;
            }
        }
        if (z) {
            if (this.i == null) {
                this.i = new B0(this, this.a);
            }
            ViewGroup viewGroup3 = (ViewGroup) this.i.getParent();
            if (viewGroup3 != this.h) {
                if (viewGroup3 != null) {
                    viewGroup3.removeView(this.i);
                }
                ActionMenuView actionMenuView = (ActionMenuView) this.h;
                B0 b0 = this.i;
                actionMenuView.getClass();
                E0 e0M = ActionMenuView.m();
                e0M.a = true;
                actionMenuView.addView(b0, e0M);
            }
        } else {
            B0 b1 = this.i;
            if (b1 != null) {
                Object parent = b1.getParent();
                Object obj = this.h;
                if (parent == obj) {
                    ((ViewGroup) obj).removeView(this.i);
                }
            }
        }
        ((ActionMenuView) this.h).s = this.j;
    }

    public final boolean j() {
        C0758y0 c0758y0 = this.q;
        return c0758y0 != null && c0758y0.b();
    }

    @Override // rikka.shizuku.InterfaceC0683vi
    public final void k(Context context, MenuC0129di menuC0129di) {
        this.b = context;
        LayoutInflater.from(context);
        this.c = menuC0129di;
        Resources resources = context.getResources();
        if (!this.k) {
            this.j = true;
        }
        int i = 2;
        this.l = context.getResources().getDisplayMetrics().widthPixels / 2;
        Configuration configuration = context.getResources().getConfiguration();
        int i2 = configuration.screenWidthDp;
        int i3 = configuration.screenHeightDp;
        if (configuration.smallestScreenWidthDp > 600 || i2 > 600 || ((i2 > 960 && i3 > 720) || (i2 > 720 && i3 > 960))) {
            i = 5;
        } else if (i2 >= 500 || ((i2 > 640 && i3 > 480) || (i2 > 480 && i3 > 640))) {
            i = 4;
        } else if (i2 >= 360) {
            i = 3;
        }
        this.n = i;
        int measuredWidth = this.l;
        if (this.j) {
            if (this.i == null) {
                this.i = new B0(this, this.a);
                int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
                this.i.measure(iMakeMeasureSpec, iMakeMeasureSpec);
            }
            measuredWidth -= this.i.getMeasuredWidth();
        } else {
            this.i = null;
        }
        this.m = measuredWidth;
        float f = resources.getDisplayMetrics().density;
    }

    public final boolean l() {
        MenuC0129di menuC0129di;
        if (!this.j || j() || (menuC0129di = this.c) == null || this.h == null || this.s != null) {
            return false;
        }
        menuC0129di.i();
        if (menuC0129di.j.isEmpty()) {
            return false;
        }
        A0 a0 = new A0(this, 0, new C0758y0(this, this.b, this.c, this.i));
        this.s = a0;
        ((View) this.h).post(a0);
        return true;
    }
}
