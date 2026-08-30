package rikka.shizuku;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import androidx.appcompat.view.menu.ExpandedMenuView;

/* JADX INFO: loaded from: classes.dex */
public final class Sg implements InterfaceC0683vi, AdapterView.OnItemClickListener {
    public Context a;
    public LayoutInflater b;
    public MenuC0129di c;
    public ExpandedMenuView d;
    public InterfaceC0652ui e;
    public Rg f;

    public Sg(ContextWrapper contextWrapper) {
        this.a = contextWrapper;
        this.b = LayoutInflater.from(contextWrapper);
    }

    @Override // rikka.shizuku.InterfaceC0683vi
    public final void b(MenuC0129di menuC0129di, boolean z) {
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

    @Override // rikka.shizuku.InterfaceC0683vi
    public final boolean f(SubMenuC0166ep subMenuC0166ep) {
        if (!subMenuC0166ep.hasVisibleItems()) {
            return false;
        }
        DialogInterfaceOnKeyListenerC0159ei dialogInterfaceOnKeyListenerC0159ei = new DialogInterfaceOnKeyListenerC0159ei();
        dialogInterfaceOnKeyListenerC0159ei.a = subMenuC0166ep;
        Context context = subMenuC0166ep.a;
        I1 i1 = new I1(context);
        E1 e1 = (E1) i1.b;
        Sg sg = new Sg(e1.a);
        dialogInterfaceOnKeyListenerC0159ei.c = sg;
        sg.e = dialogInterfaceOnKeyListenerC0159ei;
        subMenuC0166ep.b(sg, context);
        Sg sg2 = dialogInterfaceOnKeyListenerC0159ei.c;
        if (sg2.f == null) {
            sg2.f = new Rg(sg2);
        }
        e1.q = sg2.f;
        e1.r = dialogInterfaceOnKeyListenerC0159ei;
        View view = subMenuC0166ep.o;
        if (view != null) {
            e1.e = view;
        } else {
            e1.c = subMenuC0166ep.n;
            e1.d = subMenuC0166ep.m;
        }
        e1.o = dialogInterfaceOnKeyListenerC0159ei;
        J1 j1B = i1.b();
        dialogInterfaceOnKeyListenerC0159ei.b = j1B;
        j1B.setOnDismissListener(dialogInterfaceOnKeyListenerC0159ei);
        WindowManager.LayoutParams attributes = dialogInterfaceOnKeyListenerC0159ei.b.getWindow().getAttributes();
        attributes.type = 1003;
        attributes.flags |= 131072;
        dialogInterfaceOnKeyListenerC0159ei.b.show();
        InterfaceC0652ui interfaceC0652ui = this.e;
        if (interfaceC0652ui == null) {
            return true;
        }
        interfaceC0652ui.c(subMenuC0166ep);
        return true;
    }

    @Override // rikka.shizuku.InterfaceC0683vi
    public final boolean g() {
        return false;
    }

    @Override // rikka.shizuku.InterfaceC0683vi
    public final boolean h(C0221gi c0221gi) {
        return false;
    }

    @Override // rikka.shizuku.InterfaceC0683vi
    public final void i() {
        Rg rg = this.f;
        if (rg != null) {
            rg.notifyDataSetChanged();
        }
    }

    @Override // rikka.shizuku.InterfaceC0683vi
    public final void k(Context context, MenuC0129di menuC0129di) {
        if (this.a != null) {
            this.a = context;
            if (this.b == null) {
                this.b = LayoutInflater.from(context);
            }
        }
        this.c = menuC0129di;
        Rg rg = this.f;
        if (rg != null) {
            rg.notifyDataSetChanged();
        }
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
        this.c.q(this.f.getItem(i), this, 0);
    }
}
