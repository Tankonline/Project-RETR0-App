package rikka.shizuku;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.appcompat.widget.Toolbar;
import java.util.ArrayList;
import moe.shizuku.privileged.api.R;

/* JADX INFO: renamed from: rikka.shizuku.dq, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0136dq implements InterfaceC0683vi {
    public MenuC0129di a;
    public C0221gi b;
    public final /* synthetic */ Toolbar c;

    public C0136dq(Toolbar toolbar) {
        this.c = toolbar;
    }

    @Override // rikka.shizuku.InterfaceC0683vi
    public final boolean d(C0221gi c0221gi) {
        Toolbar toolbar = this.c;
        KeyEvent.Callback callback = toolbar.i;
        if (callback instanceof InterfaceC0547r6) {
            ((InterfaceC0547r6) callback).f();
        }
        toolbar.removeView(toolbar.i);
        toolbar.removeView(toolbar.h);
        toolbar.i = null;
        ArrayList arrayList = toolbar.E;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            toolbar.addView((View) arrayList.get(size));
        }
        arrayList.clear();
        this.b = null;
        toolbar.requestLayout();
        c0221gi.C = false;
        c0221gi.n.p(false);
        toolbar.D();
        return true;
    }

    @Override // rikka.shizuku.InterfaceC0683vi
    public final boolean f(SubMenuC0166ep subMenuC0166ep) {
        return false;
    }

    @Override // rikka.shizuku.InterfaceC0683vi
    public final boolean g() {
        return false;
    }

    @Override // rikka.shizuku.InterfaceC0683vi
    public final boolean h(C0221gi c0221gi) {
        Toolbar toolbar = this.c;
        if (toolbar.h == null) {
            N2 n2 = new N2(toolbar.getContext(), null, R.attr._0_resource_name_obfuscated_res_0x7f040531);
            toolbar.h = n2;
            n2.setImageDrawable(toolbar.f);
            toolbar.h.setContentDescription(toolbar.g);
            C0167eq c0167eqF = Toolbar.f();
            c0167eqF.a = (toolbar.n & 112) | 8388611;
            c0167eqF.b = 2;
            toolbar.h.setLayoutParams(c0167eqF);
            toolbar.h.setOnClickListener(new ViewOnClickListenerC0572s0(5, toolbar));
        }
        ViewParent parent = toolbar.h.getParent();
        if (parent != toolbar) {
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(toolbar.h);
            }
            toolbar.addView(toolbar.h);
        }
        View actionView = c0221gi.getActionView();
        toolbar.i = actionView;
        this.b = c0221gi;
        ViewParent parent2 = actionView.getParent();
        if (parent2 != toolbar) {
            if (parent2 instanceof ViewGroup) {
                ((ViewGroup) parent2).removeView(toolbar.i);
            }
            C0167eq c0167eqF2 = Toolbar.f();
            c0167eqF2.a = 8388611 | (toolbar.n & 112);
            c0167eqF2.b = 2;
            toolbar.i.setLayoutParams(c0167eqF2);
            toolbar.addView(toolbar.i);
        }
        for (int childCount = toolbar.getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = toolbar.getChildAt(childCount);
            if (((C0167eq) childAt.getLayoutParams()).b != 2 && childAt != toolbar.a) {
                toolbar.removeViewAt(childCount);
                toolbar.E.add(childAt);
            }
        }
        toolbar.requestLayout();
        c0221gi.C = true;
        c0221gi.n.p(false);
        KeyEvent.Callback callback = toolbar.i;
        if (callback instanceof InterfaceC0547r6) {
            ((InterfaceC0547r6) callback).d();
        }
        toolbar.D();
        return true;
    }

    @Override // rikka.shizuku.InterfaceC0683vi
    public final void i() {
        if (this.b != null) {
            MenuC0129di menuC0129di = this.a;
            if (menuC0129di != null) {
                int size = menuC0129di.f.size();
                for (int i = 0; i < size; i++) {
                    if (this.a.getItem(i) == this.b) {
                        return;
                    }
                }
            }
            d(this.b);
        }
    }

    @Override // rikka.shizuku.InterfaceC0683vi
    public final void k(Context context, MenuC0129di menuC0129di) {
        C0221gi c0221gi;
        MenuC0129di menuC0129di2 = this.a;
        if (menuC0129di2 != null && (c0221gi = this.b) != null) {
            menuC0129di2.d(c0221gi);
        }
        this.a = menuC0129di;
    }

    @Override // rikka.shizuku.InterfaceC0683vi
    public final void b(MenuC0129di menuC0129di, boolean z) {
    }
}
