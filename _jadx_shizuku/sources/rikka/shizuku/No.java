package rikka.shizuku;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import java.util.WeakHashMap;
import moe.shizuku.privileged.api.R;

/* JADX INFO: loaded from: classes.dex */
public final class No extends AbstractC0374li implements PopupWindow.OnDismissListener, View.OnKeyListener {
    public final Context b;
    public final MenuC0129di c;
    public final C0036ai d;
    public final boolean e;
    public final int f;
    public final int g;
    public final C0621ti h;
    public C0405mi k;
    public View l;
    public View m;
    public InterfaceC0652ui n;
    public ViewTreeObserver o;
    public boolean p;
    public boolean q;
    public int r;
    public boolean t;
    public final Y2 i = new Y2(3, this);
    public final ViewOnAttachStateChangeListenerC0511q0 j = new ViewOnAttachStateChangeListenerC0511q0(5, this);
    public int s = 0;

    public No(int i, Context context, View view, MenuC0129di menuC0129di, boolean z) {
        this.b = context;
        this.c = menuC0129di;
        this.e = z;
        this.d = new C0036ai(menuC0129di, LayoutInflater.from(context), z, R.layout._0_resource_name_obfuscated_res_0x7f0c0013);
        this.g = i;
        Resources resources = context.getResources();
        this.f = Math.max(resources.getDisplayMetrics().widthPixels / 2, resources.getDimensionPixelSize(R.dimen._0_resource_name_obfuscated_res_0x7f070017));
        this.l = view;
        this.h = new C0621ti(context, null, i);
        menuC0129di.b(this, context);
    }

    @Override // rikka.shizuku.Yn
    public final boolean a() {
        return !this.p && this.h.z.isShowing();
    }

    @Override // rikka.shizuku.InterfaceC0683vi
    public final void b(MenuC0129di menuC0129di, boolean z) {
        if (menuC0129di != this.c) {
            return;
        }
        dismiss();
        InterfaceC0652ui interfaceC0652ui = this.n;
        if (interfaceC0652ui != null) {
            interfaceC0652ui.b(menuC0129di, z);
        }
    }

    @Override // rikka.shizuku.InterfaceC0683vi
    public final void c(InterfaceC0652ui interfaceC0652ui) {
        this.n = interfaceC0652ui;
    }

    @Override // rikka.shizuku.Yn
    public final void dismiss() {
        if (a()) {
            this.h.dismiss();
        }
    }

    @Override // rikka.shizuku.Yn
    public final void e() {
        View view;
        if (a()) {
            return;
        }
        if (this.p || (view = this.l) == null) {
            throw new IllegalStateException("StandardMenuPopup cannot be used without an anchor");
        }
        this.m = view;
        C0621ti c0621ti = this.h;
        c0621ti.z.setOnDismissListener(this);
        c0621ti.p = this;
        c0621ti.y = true;
        c0621ti.z.setFocusable(true);
        View view2 = this.m;
        boolean z = this.o == null;
        ViewTreeObserver viewTreeObserver = view2.getViewTreeObserver();
        this.o = viewTreeObserver;
        if (z) {
            viewTreeObserver.addOnGlobalLayoutListener(this.i);
        }
        view2.addOnAttachStateChangeListener(this.j);
        c0621ti.o = view2;
        c0621ti.l = this.s;
        boolean z2 = this.q;
        Context context = this.b;
        C0036ai c0036ai = this.d;
        if (!z2) {
            this.r = AbstractC0374li.m(c0036ai, context, this.f);
            this.q = true;
        }
        c0621ti.p(this.r);
        c0621ti.z.setInputMethodMode(2);
        Rect rect = this.a;
        c0621ti.x = rect != null ? new Rect(rect) : null;
        c0621ti.e();
        C0490pa c0490pa = c0621ti.c;
        c0490pa.setOnKeyListener(this);
        if (this.t) {
            MenuC0129di menuC0129di = this.c;
            if (menuC0129di.m != null) {
                FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(context).inflate(R.layout._0_resource_name_obfuscated_res_0x7f0c0012, (ViewGroup) c0490pa, false);
                TextView textView = (TextView) frameLayout.findViewById(android.R.id.title);
                if (textView != null) {
                    textView.setText(menuC0129di.m);
                }
                frameLayout.setEnabled(false);
                c0490pa.addHeaderView(frameLayout, null, false);
            }
        }
        c0621ti.m(c0036ai);
        c0621ti.e();
    }

    @Override // rikka.shizuku.InterfaceC0683vi
    public final boolean f(SubMenuC0166ep subMenuC0166ep) {
        if (subMenuC0166ep.hasVisibleItems()) {
            View view = this.m;
            C0467oi c0467oi = new C0467oi(this.g, this.b, view, subMenuC0166ep, this.e);
            InterfaceC0652ui interfaceC0652ui = this.n;
            c0467oi.h = interfaceC0652ui;
            AbstractC0374li abstractC0374li = c0467oi.i;
            if (abstractC0374li != null) {
                abstractC0374li.c(interfaceC0652ui);
            }
            boolean zU = AbstractC0374li.u(subMenuC0166ep);
            c0467oi.g = zU;
            AbstractC0374li abstractC0374li2 = c0467oi.i;
            if (abstractC0374li2 != null) {
                abstractC0374li2.o(zU);
            }
            c0467oi.j = this.k;
            this.k = null;
            this.c.c(false);
            C0621ti c0621ti = this.h;
            int width = c0621ti.f;
            int iF = c0621ti.f();
            int i = this.s;
            View view2 = this.l;
            WeakHashMap weakHashMap = Hs.a;
            if ((Gravity.getAbsoluteGravity(i, view2.getLayoutDirection()) & 7) == 5) {
                width += this.l.getWidth();
            }
            if (!c0467oi.b()) {
                if (c0467oi.e != null) {
                    c0467oi.d(width, iF, true, true);
                }
            }
            InterfaceC0652ui interfaceC0652ui2 = this.n;
            if (interfaceC0652ui2 != null) {
                interfaceC0652ui2.c(subMenuC0166ep);
            }
            return true;
        }
        return false;
    }

    @Override // rikka.shizuku.InterfaceC0683vi
    public final boolean g() {
        return false;
    }

    @Override // rikka.shizuku.InterfaceC0683vi
    public final void i() {
        this.q = false;
        C0036ai c0036ai = this.d;
        if (c0036ai != null) {
            c0036ai.notifyDataSetChanged();
        }
    }

    @Override // rikka.shizuku.Yn
    public final C0490pa j() {
        return this.h.c;
    }

    @Override // rikka.shizuku.AbstractC0374li
    public final void n(View view) {
        this.l = view;
    }

    @Override // rikka.shizuku.AbstractC0374li
    public final void o(boolean z) {
        this.d.c = z;
    }

    @Override // android.widget.PopupWindow.OnDismissListener
    public final void onDismiss() {
        this.p = true;
        this.c.c(true);
        ViewTreeObserver viewTreeObserver = this.o;
        if (viewTreeObserver != null) {
            if (!viewTreeObserver.isAlive()) {
                this.o = this.m.getViewTreeObserver();
            }
            this.o.removeGlobalOnLayoutListener(this.i);
            this.o = null;
        }
        this.m.removeOnAttachStateChangeListener(this.j);
        C0405mi c0405mi = this.k;
        if (c0405mi != null) {
            c0405mi.onDismiss();
        }
    }

    @Override // android.view.View.OnKeyListener
    public final boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() != 1 || i != 82) {
            return false;
        }
        dismiss();
        return true;
    }

    @Override // rikka.shizuku.AbstractC0374li
    public final void p(int i) {
        this.s = i;
    }

    @Override // rikka.shizuku.AbstractC0374li
    public final void q(int i) {
        this.h.f = i;
    }

    @Override // rikka.shizuku.AbstractC0374li
    public final void r(PopupWindow.OnDismissListener onDismissListener) {
        this.k = (C0405mi) onDismissListener;
    }

    @Override // rikka.shizuku.AbstractC0374li
    public final void s(boolean z) {
        this.t = z;
    }

    @Override // rikka.shizuku.AbstractC0374li
    public final void t(int i) {
        this.h.l(i);
    }

    @Override // rikka.shizuku.AbstractC0374li
    public final void l(MenuC0129di menuC0129di) {
    }
}
