package rikka.shizuku;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;
import java.util.ArrayList;
import java.util.WeakHashMap;
import moe.shizuku.privileged.api.R;

/* JADX INFO: renamed from: rikka.shizuku.hq, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0260hq extends AbstractC0744xh {
    public final C0351kq q;
    public final C2 r;
    public final C0229gq s;
    public boolean t;
    public boolean u;
    public boolean v;
    public final ArrayList w = new ArrayList();
    public final RunnableC0607t4 x = new RunnableC0607t4(17, this);

    public C0260hq(Toolbar toolbar, CharSequence charSequence, C2 c2) {
        C0229gq c0229gq = new C0229gq(this);
        C0351kq c0351kq = new C0351kq(toolbar, false);
        this.q = c0351kq;
        c2.getClass();
        this.r = c2;
        c0351kq.k = c2;
        toolbar.I = c0229gq;
        if (!c0351kq.g) {
            c0351kq.h = charSequence;
            if ((c0351kq.b & 8) != 0) {
                Toolbar toolbar2 = c0351kq.a;
                toolbar2.A(charSequence);
                if (c0351kq.g) {
                    Hs.n(toolbar2.getRootView(), charSequence);
                }
            }
        }
        this.s = new C0229gq(this);
    }

    @Override // rikka.shizuku.AbstractC0744xh
    public final boolean D() {
        C0351kq c0351kq = this.q;
        Toolbar toolbar = c0351kq.a;
        RunnableC0607t4 runnableC0607t4 = this.x;
        toolbar.removeCallbacks(runnableC0607t4);
        Toolbar toolbar2 = c0351kq.a;
        WeakHashMap weakHashMap = Hs.a;
        toolbar2.postOnAnimation(runnableC0607t4);
        return true;
    }

    @Override // rikka.shizuku.AbstractC0744xh
    public final void K() {
        this.q.a.removeCallbacks(this.x);
    }

    @Override // rikka.shizuku.AbstractC0744xh
    public final boolean L(int i, KeyEvent keyEvent) {
        MenuC0129di menuC0129diD0 = d0();
        if (menuC0129diD0 == null) {
            return false;
        }
        menuC0129diD0.setQwertyMode(KeyCharacterMap.load(keyEvent.getDeviceId()).getKeyboardType() != 1);
        return menuC0129diD0.performShortcut(i, keyEvent, 0);
    }

    @Override // rikka.shizuku.AbstractC0744xh
    public final boolean M(KeyEvent keyEvent) {
        if (keyEvent.getAction() == 1) {
            O();
        }
        return true;
    }

    @Override // rikka.shizuku.AbstractC0744xh
    public final boolean O() {
        return this.q.a.C();
    }

    @Override // rikka.shizuku.AbstractC0744xh
    public final void T(boolean z) {
        C0351kq c0351kq = this.q;
        c0351kq.a((c0351kq.b & (-5)) | 4);
    }

    @Override // rikka.shizuku.AbstractC0744xh
    public final void U() {
        C0351kq c0351kq = this.q;
        Drawable drawableV = AbstractC0744xh.v(c0351kq.a.getContext(), R.drawable._0_resource_name_obfuscated_res_0x7f0800a5);
        c0351kq.f = drawableV;
        int i = c0351kq.b & 4;
        Toolbar toolbar = c0351kq.a;
        if (i == 0) {
            toolbar.y(null);
            return;
        }
        if (drawableV == null) {
            drawableV = c0351kq.o;
        }
        toolbar.y(drawableV);
    }

    @Override // rikka.shizuku.AbstractC0744xh
    public final void W(CharSequence charSequence) {
        C0351kq c0351kq = this.q;
        if (c0351kq.g) {
            return;
        }
        c0351kq.h = charSequence;
        if ((c0351kq.b & 8) != 0) {
            Toolbar toolbar = c0351kq.a;
            toolbar.A(charSequence);
            if (c0351kq.g) {
                Hs.n(toolbar.getRootView(), charSequence);
            }
        }
    }

    public final MenuC0129di d0() {
        boolean z = this.u;
        C0351kq c0351kq = this.q;
        if (!z) {
            C0330k5 c0330k5 = new C0330k5(this);
            C0229gq c0229gq = new C0229gq(this);
            Toolbar toolbar = c0351kq.a;
            toolbar.N = c0330k5;
            toolbar.O = c0229gq;
            ActionMenuView actionMenuView = toolbar.a;
            if (actionMenuView != null) {
                actionMenuView.u = c0330k5;
                actionMenuView.v = c0229gq;
            }
            this.u = true;
        }
        return c0351kq.a.m();
    }

    @Override // rikka.shizuku.AbstractC0744xh
    public final boolean j() {
        C0 c0;
        ActionMenuView actionMenuView = this.q.a.a;
        return (actionMenuView == null || (c0 = actionMenuView.t) == null || !c0.e()) ? false : true;
    }

    @Override // rikka.shizuku.AbstractC0744xh
    public final boolean k() {
        C0221gi c0221gi;
        C0136dq c0136dq = this.q.a.M;
        if (c0136dq == null || (c0221gi = c0136dq.b) == null) {
            return false;
        }
        if (c0136dq == null) {
            c0221gi = null;
        }
        if (c0221gi == null) {
            return true;
        }
        c0221gi.collapseActionView();
        return true;
    }

    @Override // rikka.shizuku.AbstractC0744xh
    public final void o(boolean z) {
        if (z == this.v) {
            return;
        }
        this.v = z;
        ArrayList arrayList = this.w;
        if (arrayList.size() <= 0) {
            return;
        }
        arrayList.get(0).getClass();
        throw new ClassCastException();
    }

    @Override // rikka.shizuku.AbstractC0744xh
    public final int t() {
        return this.q.b;
    }

    @Override // rikka.shizuku.AbstractC0744xh
    public final Context z() {
        return this.q.a.getContext();
    }

    @Override // rikka.shizuku.AbstractC0744xh
    public final void J() {
    }

    @Override // rikka.shizuku.AbstractC0744xh
    public final void S(boolean z) {
    }

    @Override // rikka.shizuku.AbstractC0744xh
    public final void V(boolean z) {
    }
}
