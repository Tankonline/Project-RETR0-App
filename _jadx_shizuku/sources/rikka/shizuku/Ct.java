package rikka.shizuku;

import android.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import androidx.appcompat.widget.ActionBarContainer;
import androidx.appcompat.widget.ActionBarContextView;
import androidx.appcompat.widget.ActionBarOverlayLayout;
import androidx.appcompat.widget.Toolbar;
import java.util.ArrayList;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public final class Ct extends AbstractC0744xh {
    public static final AccelerateInterpolator O = new AccelerateInterpolator();
    public static final DecelerateInterpolator P = new DecelerateInterpolator();
    public R2 A;
    public boolean B;
    public final ArrayList C;
    public int D;
    public boolean E;
    public boolean F;
    public boolean G;
    public boolean H;
    public C0139dt I;
    public boolean J;
    public boolean K;
    public final At L;
    public final At M;
    public final Hm N;
    public Context q;
    public Context r;
    public ActionBarOverlayLayout s;
    public ActionBarContainer t;
    public C0351kq u;
    public ActionBarContextView v;
    public final View w;
    public boolean x;
    public Bt y;
    public Bt z;

    public Ct(Activity activity, boolean z) {
        new ArrayList();
        this.C = new ArrayList();
        this.D = 0;
        this.E = true;
        this.H = true;
        this.L = new At(this, 0);
        this.M = new At(this, 1);
        this.N = new Hm(this);
        View decorView = activity.getWindow().getDecorView();
        e0(decorView);
        if (z) {
            return;
        }
        this.w = decorView.findViewById(R.id.content);
    }

    @Override // rikka.shizuku.AbstractC0744xh
    public final void J() {
        f0(this.q.getResources().getBoolean(moe.shizuku.privileged.api.R.bool._0_resource_name_obfuscated_res_0x7f050000));
    }

    @Override // rikka.shizuku.AbstractC0744xh
    public final boolean L(int i, KeyEvent keyEvent) {
        MenuC0129di menuC0129di;
        Bt bt = this.y;
        if (bt == null || (menuC0129di = bt.d) == null) {
            return false;
        }
        menuC0129di.setQwertyMode(KeyCharacterMap.load(keyEvent.getDeviceId()).getKeyboardType() != 1);
        return menuC0129di.performShortcut(i, keyEvent, 0);
    }

    @Override // rikka.shizuku.AbstractC0744xh
    public final void S(boolean z) {
        if (this.x) {
            return;
        }
        T(z);
    }

    @Override // rikka.shizuku.AbstractC0744xh
    public final void T(boolean z) {
        int i = z ? 4 : 0;
        C0351kq c0351kq = this.u;
        int i2 = c0351kq.b;
        this.x = true;
        c0351kq.a((i & 4) | (i2 & (-5)));
    }

    @Override // rikka.shizuku.AbstractC0744xh
    public final void U() {
        C0351kq c0351kq = this.u;
        Drawable drawableV = AbstractC0744xh.v(c0351kq.a.getContext(), moe.shizuku.privileged.api.R.drawable._0_resource_name_obfuscated_res_0x7f0800a5);
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
    public final void V(boolean z) {
        C0139dt c0139dt;
        this.J = z;
        if (z || (c0139dt = this.I) == null) {
            return;
        }
        c0139dt.a();
    }

    @Override // rikka.shizuku.AbstractC0744xh
    public final void W(CharSequence charSequence) {
        C0351kq c0351kq = this.u;
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

    @Override // rikka.shizuku.AbstractC0744xh
    public final F0 X(R2 r2) {
        Bt bt = this.y;
        if (bt != null) {
            bt.b();
        }
        this.s.l(false);
        this.v.f();
        Bt bt2 = new Bt(this, this.v.getContext(), r2);
        MenuC0129di menuC0129di = bt2.d;
        menuC0129di.w();
        try {
            boolean zT = ((Xn) bt2.e.b).t(bt2, menuC0129di);
            menuC0129di.v();
            if (!zT) {
                return null;
            }
            this.y = bt2;
            bt2.h();
            this.v.d(bt2);
            d0(true);
            return bt2;
        } catch (Throwable th) {
            menuC0129di.v();
            throw th;
        }
    }

    public final void d0(boolean z) {
        C0109ct c0109ctJ;
        C0109ct c0109ctJ2;
        if (z) {
            if (!this.G) {
                this.G = true;
                g0(false);
            }
        } else if (this.G) {
            this.G = false;
            g0(false);
        }
        ActionBarContainer actionBarContainer = this.t;
        WeakHashMap weakHashMap = Hs.a;
        if (!actionBarContainer.isLaidOut()) {
            if (z) {
                this.u.a.setVisibility(4);
                this.v.setVisibility(0);
                return;
            } else {
                this.u.a.setVisibility(0);
                this.v.setVisibility(8);
                return;
            }
        }
        if (z) {
            C0351kq c0351kq = this.u;
            c0109ctJ = Hs.a(c0351kq.a);
            c0109ctJ.a(0.0f);
            c0109ctJ.c(100L);
            c0109ctJ.d(new C0320jq(c0351kq, 4));
            c0109ctJ2 = this.v.j(0, 200L);
        } else {
            C0351kq c0351kq2 = this.u;
            C0109ct c0109ctA = Hs.a(c0351kq2.a);
            c0109ctA.a(1.0f);
            c0109ctA.c(200L);
            c0109ctA.d(new C0320jq(c0351kq2, 0));
            c0109ctJ = this.v.j(8, 100L);
            c0109ctJ2 = c0109ctA;
        }
        C0139dt c0139dt = new C0139dt();
        ArrayList arrayList = c0139dt.a;
        arrayList.add(c0109ctJ);
        View view = (View) c0109ctJ.a.get();
        long duration = view != null ? view.animate().getDuration() : 0L;
        View view2 = (View) c0109ctJ2.a.get();
        if (view2 != null) {
            view2.animate().setStartDelay(duration);
        }
        arrayList.add(c0109ctJ2);
        c0139dt.b();
    }

    public final void e0(View view) {
        ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) view.findViewById(moe.shizuku.privileged.api.R.id._0_resource_name_obfuscated_res_0x7f090083);
        this.s = actionBarOverlayLayout;
        if (actionBarOverlayLayout != null) {
            actionBarOverlayLayout.t = this;
            if (actionBarOverlayLayout.getWindowToken() != null) {
                actionBarOverlayLayout.t.D = actionBarOverlayLayout.b;
                int i = actionBarOverlayLayout.l;
                if (i != 0) {
                    actionBarOverlayLayout.onWindowSystemUiVisibilityChanged(i);
                    WeakHashMap weakHashMap = Hs.a;
                    AbstractC0755xs.c(actionBarOverlayLayout);
                }
            }
        }
        View viewFindViewById = view.findViewById(moe.shizuku.privileged.api.R.id._0_resource_name_obfuscated_res_0x7f090031);
        if (!(viewFindViewById instanceof Toolbar)) {
            throw new IllegalStateException("Can't make a decor toolbar out of ".concat(viewFindViewById != null ? viewFindViewById.getClass().getSimpleName() : "null"));
        }
        Toolbar toolbar = (Toolbar) viewFindViewById;
        if (toolbar.K == null) {
            toolbar.K = new C0351kq(toolbar, true);
        }
        this.u = toolbar.K;
        this.v = (ActionBarContextView) view.findViewById(moe.shizuku.privileged.api.R.id._0_resource_name_obfuscated_res_0x7f090039);
        ActionBarContainer actionBarContainer = (ActionBarContainer) view.findViewById(moe.shizuku.privileged.api.R.id._0_resource_name_obfuscated_res_0x7f090033);
        this.t = actionBarContainer;
        C0351kq c0351kq = this.u;
        if (c0351kq == null || this.v == null || actionBarContainer == null) {
            throw new IllegalStateException(Ct.class.getSimpleName().concat(" can only be used with a compatible window decor layout"));
        }
        Context context = c0351kq.a.getContext();
        this.q = context;
        if ((this.u.b & 4) != 0) {
            this.x = true;
        }
        int i2 = context.getApplicationInfo().targetSdkVersion;
        this.u.getClass();
        f0(context.getResources().getBoolean(moe.shizuku.privileged.api.R.bool._0_resource_name_obfuscated_res_0x7f050000));
        TypedArray typedArrayObtainStyledAttributes = this.q.obtainStyledAttributes(null, Nk.a, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f040005, 0);
        if (typedArrayObtainStyledAttributes.getBoolean(14, false)) {
            ActionBarOverlayLayout actionBarOverlayLayout2 = this.s;
            if (!actionBarOverlayLayout2.h) {
                throw new IllegalStateException("Action bar must be in overlay mode (Window.FEATURE_OVERLAY_ACTION_BAR) to enable hide on content scroll");
            }
            this.K = true;
            actionBarOverlayLayout2.l(true);
        }
        int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(12, 0);
        if (dimensionPixelSize != 0) {
            ActionBarContainer actionBarContainer2 = this.t;
            WeakHashMap weakHashMap2 = Hs.a;
            AbstractC0817zs.k(actionBarContainer2, dimensionPixelSize);
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    public final void f0(boolean z) {
        if (z) {
            this.t.getClass();
            this.u.getClass();
        } else {
            this.u.getClass();
            this.t.getClass();
        }
        this.u.getClass();
        Toolbar toolbar = this.u.a;
        toolbar.getClass();
        toolbar.requestLayout();
        this.s.getClass();
    }

    public final void g0(boolean z) {
        boolean z2 = this.G || !this.F;
        View view = this.w;
        Hm hm = this.N;
        if (!z2) {
            if (this.H) {
                this.H = false;
                C0139dt c0139dt = this.I;
                if (c0139dt != null) {
                    c0139dt.a();
                }
                int i = this.D;
                At at = this.L;
                if (i != 0 || (!this.J && !z)) {
                    at.a();
                    return;
                }
                this.t.setAlpha(1.0f);
                ActionBarContainer actionBarContainer = this.t;
                actionBarContainer.a = true;
                actionBarContainer.setDescendantFocusability(393216);
                C0139dt c0139dt2 = new C0139dt();
                float f = -this.t.getHeight();
                if (z) {
                    int[] iArr = {0, 0};
                    this.t.getLocationInWindow(iArr);
                    f -= iArr[1];
                }
                C0109ct c0109ctA = Hs.a(this.t);
                c0109ctA.e(f);
                View view2 = (View) c0109ctA.a.get();
                if (view2 != null) {
                    view2.animate().setUpdateListener(hm != null ? new C0551ra(hm, view2) : null);
                }
                boolean z3 = c0139dt2.e;
                ArrayList arrayList = c0139dt2.a;
                if (!z3) {
                    arrayList.add(c0109ctA);
                }
                if (this.E && view != null) {
                    C0109ct c0109ctA2 = Hs.a(view);
                    c0109ctA2.e(f);
                    if (!c0139dt2.e) {
                        arrayList.add(c0109ctA2);
                    }
                }
                AccelerateInterpolator accelerateInterpolator = O;
                boolean z4 = c0139dt2.e;
                if (!z4) {
                    c0139dt2.c = accelerateInterpolator;
                }
                if (!z4) {
                    c0139dt2.b = 250L;
                }
                if (!z4) {
                    c0139dt2.d = at;
                }
                this.I = c0139dt2;
                c0139dt2.b();
                return;
            }
            return;
        }
        if (this.H) {
            return;
        }
        this.H = true;
        C0139dt c0139dt3 = this.I;
        if (c0139dt3 != null) {
            c0139dt3.a();
        }
        this.t.setVisibility(0);
        int i2 = this.D;
        At at2 = this.M;
        if (i2 == 0 && (this.J || z)) {
            this.t.setTranslationY(0.0f);
            float f2 = -this.t.getHeight();
            if (z) {
                int[] iArr2 = {0, 0};
                this.t.getLocationInWindow(iArr2);
                f2 -= iArr2[1];
            }
            this.t.setTranslationY(f2);
            C0139dt c0139dt4 = new C0139dt();
            C0109ct c0109ctA3 = Hs.a(this.t);
            c0109ctA3.e(0.0f);
            View view3 = (View) c0109ctA3.a.get();
            if (view3 != null) {
                view3.animate().setUpdateListener(hm != null ? new C0551ra(hm, view3) : null);
            }
            boolean z5 = c0139dt4.e;
            ArrayList arrayList2 = c0139dt4.a;
            if (!z5) {
                arrayList2.add(c0109ctA3);
            }
            if (this.E && view != null) {
                view.setTranslationY(f2);
                C0109ct c0109ctA4 = Hs.a(view);
                c0109ctA4.e(0.0f);
                if (!c0139dt4.e) {
                    arrayList2.add(c0109ctA4);
                }
            }
            DecelerateInterpolator decelerateInterpolator = P;
            boolean z6 = c0139dt4.e;
            if (!z6) {
                c0139dt4.c = decelerateInterpolator;
            }
            if (!z6) {
                c0139dt4.b = 250L;
            }
            if (!z6) {
                c0139dt4.d = at2;
            }
            this.I = c0139dt4;
            c0139dt4.b();
        } else {
            this.t.setAlpha(1.0f);
            this.t.setTranslationY(0.0f);
            if (this.E && view != null) {
                view.setTranslationY(0.0f);
            }
            at2.a();
        }
        ActionBarOverlayLayout actionBarOverlayLayout = this.s;
        if (actionBarOverlayLayout != null) {
            WeakHashMap weakHashMap = Hs.a;
            AbstractC0755xs.c(actionBarOverlayLayout);
        }
    }

    @Override // rikka.shizuku.AbstractC0744xh
    public final boolean k() {
        C0136dq c0136dq;
        C0221gi c0221gi;
        C0351kq c0351kq = this.u;
        if (c0351kq == null || (c0136dq = c0351kq.a.M) == null || (c0221gi = c0136dq.b) == null) {
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
        if (z == this.B) {
            return;
        }
        this.B = z;
        ArrayList arrayList = this.C;
        if (arrayList.size() <= 0) {
            return;
        }
        arrayList.get(0).getClass();
        throw new ClassCastException();
    }

    @Override // rikka.shizuku.AbstractC0744xh
    public final int t() {
        return this.u.b;
    }

    @Override // rikka.shizuku.AbstractC0744xh
    public final Context z() {
        if (this.r == null) {
            TypedValue typedValue = new TypedValue();
            this.q.getTheme().resolveAttribute(moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f04000a, typedValue, true);
            int i = typedValue.resourceId;
            if (i != 0) {
                this.r = new ContextThemeWrapper(this.q, i);
            } else {
                this.r = this.q;
            }
        }
        return this.r;
    }

    public Ct(Dialog dialog) {
        new ArrayList();
        this.C = new ArrayList();
        this.D = 0;
        this.E = true;
        this.H = true;
        this.L = new At(this, 0);
        this.M = new At(this, 1);
        this.N = new Hm(this);
        e0(dialog.getWindow().getDecorView());
    }
}
