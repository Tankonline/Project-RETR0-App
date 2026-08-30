package rikka.shizuku;

import android.R;
import android.app.Activity;
import android.app.Dialog;
import android.app.UiModeManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.LocaleList;
import android.text.TextUtils;
import android.util.AndroidRuntimeException;
import android.util.AttributeSet;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.TypedValue;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;
import androidx.appcompat.view.menu.ExpandedMenuView;
import androidx.appcompat.widget.ActionBarContextView;
import androidx.appcompat.widget.ActionBarOverlayLayout;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.ContentFrameLayout;
import androidx.appcompat.widget.Toolbar;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.WeakHashMap;
import rikka.html.text.style.BulletSpan;

/* JADX INFO: loaded from: classes.dex */
public final class I2 extends AbstractC0667v2 implements InterfaceC0067bi, LayoutInflater.Factory2 {
    public static final C0104co e0 = new C0104co(0);
    public static final int[] f0 = {R.attr.windowBackground};
    public static final boolean g0 = !"robolectric".equals(Build.FINGERPRINT);
    public static final boolean h0 = true;
    public ViewGroup A;
    public TextView B;
    public boolean C;
    public boolean D;
    public boolean E;
    public boolean F;
    public boolean G;
    public boolean H;
    public boolean I;
    public boolean J;
    public H2[] K;
    public H2 L;
    public boolean M;
    public boolean N;
    public boolean O;
    public boolean P;
    public Configuration Q;
    public final int R;
    public int S;
    public int T;
    public boolean U;
    public D2 V;
    public D2 W;
    public boolean X;
    public int Y;
    public boolean a0;
    public D3 b0;
    public OnBackInvokedDispatcher c0;
    public OnBackInvokedCallback d0;
    public final Object j;
    public final Context k;
    public Window l;
    public C2 m;
    public final Object n;
    public AbstractC0744xh o;
    public C0381lp p;
    public CharSequence q;
    public Y8 r;
    public C0729x2 s;
    public C0729x2 t;
    public F0 u;
    public ActionBarContextView v;
    public PopupWindow w;
    public RunnableC0698w2 x;
    public boolean z;
    public C0109ct y = null;
    public final RunnableC0698w2 Z = new RunnableC0698w2(this, 0);

    public I2(Context context, Window window, InterfaceC0451o2 interfaceC0451o2, Object obj) {
        AbstractActivityC0051b2 abstractActivityC0051b2 = null;
        this.R = -100;
        this.k = context;
        this.n = interfaceC0451o2;
        this.j = obj;
        if (obj instanceof Dialog) {
            while (context != null) {
                if (!(context instanceof AbstractActivityC0051b2)) {
                    if (!(context instanceof ContextWrapper)) {
                        break;
                    } else {
                        context = ((ContextWrapper) context).getBaseContext();
                    }
                } else {
                    abstractActivityC0051b2 = (AbstractActivityC0051b2) context;
                    break;
                }
            }
            if (abstractActivityC0051b2 != null) {
                this.R = ((I2) abstractActivityC0051b2.k()).R;
            }
        }
        if (this.R == -100) {
            C0104co c0104co = e0;
            Integer num = (Integer) c0104co.get(this.j.getClass().getName());
            if (num != null) {
                this.R = num.intValue();
                c0104co.remove(this.j.getClass().getName());
            }
        }
        if (window != null) {
            q(window);
        }
        L2.d();
    }

    public static C0342kh r(Context context) {
        C0342kh c0342kh;
        C0342kh c0342kh2;
        if (Build.VERSION.SDK_INT >= 33 || (c0342kh = AbstractC0667v2.c) == null) {
            return null;
        }
        C0342kh c0342khB = AbstractC0791z2.b(context.getApplicationContext().getResources().getConfiguration());
        C0373lh c0373lh = c0342kh.a;
        if (c0373lh.a.isEmpty()) {
            c0342kh2 = C0342kh.b;
        } else {
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            int i = 0;
            while (i < c0342khB.a.a.size() + c0373lh.a.size()) {
                Locale locale = i < c0373lh.a.size() ? c0373lh.a.get(i) : c0342khB.a.a.get(i - c0373lh.a.size());
                if (locale != null) {
                    linkedHashSet.add(locale);
                }
                i++;
            }
            c0342kh2 = new C0342kh(new C0373lh(new LocaleList((Locale[]) linkedHashSet.toArray(new Locale[linkedHashSet.size()]))));
        }
        return c0342kh2.a.a.isEmpty() ? c0342khB : c0342kh2;
    }

    public static Configuration v(Context context, int i, C0342kh c0342kh, Configuration configuration, boolean z) {
        int i2;
        if (i == 1) {
            i2 = 16;
        } else if (i != 2) {
            i2 = z ? 0 : context.getApplicationContext().getResources().getConfiguration().uiMode & 48;
        } else {
            i2 = 32;
        }
        Configuration configuration2 = new Configuration();
        configuration2.fontScale = 0.0f;
        if (configuration != null) {
            configuration2.setTo(configuration);
        }
        configuration2.uiMode = i2 | (configuration2.uiMode & (-49));
        if (c0342kh != null) {
            AbstractC0791z2.d(configuration2, c0342kh);
        }
        return configuration2;
    }

    public final F2 A(Context context) {
        if (this.V == null) {
            if (K3.f == null) {
                Context applicationContext = context.getApplicationContext();
                K3.f = new K3(applicationContext, (LocationManager) applicationContext.getSystemService("location"));
            }
            this.V = new D2(this, K3.f);
        }
        return this.V;
    }

    public final H2 B(int i) {
        H2[] h2Arr = this.K;
        if (h2Arr == null || h2Arr.length <= i) {
            H2[] h2Arr2 = new H2[i + 1];
            if (h2Arr != null) {
                System.arraycopy(h2Arr, 0, h2Arr2, 0, h2Arr.length);
            }
            this.K = h2Arr2;
            h2Arr = h2Arr2;
        }
        H2 h2 = h2Arr[i];
        if (h2 != null) {
            return h2;
        }
        H2 h3 = new H2();
        h3.a = i;
        h3.n = false;
        h2Arr[i] = h3;
        return h3;
    }

    public final void C() {
        y();
        if (this.E && this.o == null) {
            Object obj = this.j;
            if (obj instanceof Activity) {
                this.o = new Ct((Activity) obj, this.F);
            } else if (obj instanceof Dialog) {
                this.o = new Ct((Dialog) obj);
            }
            AbstractC0744xh abstractC0744xh = this.o;
            if (abstractC0744xh != null) {
                abstractC0744xh.S(this.a0);
            }
        }
    }

    public final void D(int i) {
        this.Y = (1 << i) | this.Y;
        if (this.X) {
            return;
        }
        View decorView = this.l.getDecorView();
        RunnableC0698w2 runnableC0698w2 = this.Z;
        WeakHashMap weakHashMap = Hs.a;
        decorView.postOnAnimation(runnableC0698w2);
        this.X = true;
    }

    public final int E(Context context, int i) {
        if (i != -100) {
            if (i != -1) {
                if (i != 0) {
                    if (i != 1 && i != 2) {
                        if (i != 3) {
                            throw new IllegalStateException("Unknown value set for night mode. Please use one of the MODE_NIGHT values from AppCompatDelegate.");
                        }
                        if (this.W == null) {
                            this.W = new D2(this, context);
                        }
                        return this.W.e();
                    }
                } else if (((UiModeManager) context.getApplicationContext().getSystemService("uimode")).getNightMode() != 0) {
                    return A(context).e();
                }
            }
            return i;
        }
        return -1;
    }

    public final boolean F() {
        boolean z = this.M;
        this.M = false;
        H2 h2B = B(0);
        if (!h2B.m) {
            F0 f1 = this.u;
            if (f1 != null) {
                f1.b();
                return true;
            }
            C();
            AbstractC0744xh abstractC0744xh = this.o;
            if (abstractC0744xh == null || !abstractC0744xh.k()) {
                return false;
            }
        } else if (!z) {
            u(h2B, true);
            return true;
        }
        return true;
    }

    /* JADX WARN: Code duplicated, block: B:100:0x01d3  */
    /* JADX WARN: Code duplicated, block: B:105:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x0176, code lost:
    
        if (r3.f.getCount() > 0) goto L88;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void G(H2 h2, KeyEvent keyEvent) {
        int i;
        ViewGroup.LayoutParams layoutParams;
        if (h2.m || this.P) {
            return;
        }
        int i2 = h2.a;
        Context context = this.k;
        if (i2 == 0 && (context.getResources().getConfiguration().screenLayout & 15) == 4) {
            return;
        }
        Window.Callback callback = this.l.getCallback();
        if (callback != null && !callback.onMenuOpened(i2, h2.h)) {
            u(h2, true);
            return;
        }
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        if (windowManager != null && I(h2, keyEvent)) {
            G2 g2 = h2.e;
            if (g2 != null && !h2.n) {
                View view = h2.g;
                if (view != null && (layoutParams = view.getLayoutParams()) != null && layoutParams.width == -1) {
                    i = -1;
                }
                h2.l = false;
                WindowManager.LayoutParams layoutParams2 = new WindowManager.LayoutParams(i, -2, 0, 0, 1002, 8519680, -3);
                layoutParams2.gravity = h2.c;
                layoutParams2.windowAnimations = h2.d;
                windowManager.addView(h2.e, layoutParams2);
                h2.m = true;
                if (i2 == 0) {
                    K();
                }
            }
            if (g2 == null) {
                C();
                AbstractC0744xh abstractC0744xh = this.o;
                Context contextZ = abstractC0744xh != null ? abstractC0744xh.z() : null;
                if (contextZ != null) {
                    context = contextZ;
                }
                TypedValue typedValue = new TypedValue();
                Resources.Theme themeNewTheme = context.getResources().newTheme();
                themeNewTheme.setTo(context.getTheme());
                themeNewTheme.resolveAttribute(moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f040002, typedValue, true);
                int i3 = typedValue.resourceId;
                if (i3 != 0) {
                    themeNewTheme.applyStyle(i3, true);
                }
                themeNewTheme.resolveAttribute(moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f0403d0, typedValue, true);
                int i4 = typedValue.resourceId;
                if (i4 != 0) {
                    themeNewTheme.applyStyle(i4, true);
                } else {
                    themeNewTheme.applyStyle(moe.shizuku.privileged.api.R.style._0_resource_name_obfuscated_res_0x7f12025b, true);
                }
                F7 f7 = new F7(context, 0);
                f7.getTheme().setTo(themeNewTheme);
                h2.j = f7;
                TypedArray typedArrayObtainStyledAttributes = f7.obtainStyledAttributes(Nk.j);
                h2.b = typedArrayObtainStyledAttributes.getResourceId(86, 0);
                h2.d = typedArrayObtainStyledAttributes.getResourceId(1, 0);
                typedArrayObtainStyledAttributes.recycle();
                h2.e = new G2(this, h2.j);
                h2.c = 81;
            } else if (h2.n && g2.getChildCount() > 0) {
                h2.e.removeAllViews();
            }
            View view2 = h2.g;
            if (view2 == null) {
                if (h2.h != null) {
                    if (this.t == null) {
                        this.t = new C0729x2(this, 2);
                    }
                    C0729x2 c0729x2 = this.t;
                    if (h2.i == null) {
                        Sg sg = new Sg(h2.j);
                        h2.i = sg;
                        sg.e = c0729x2;
                        MenuC0129di menuC0129di = h2.h;
                        menuC0129di.b(sg, menuC0129di.a);
                    }
                    Sg sg2 = h2.i;
                    G2 g3 = h2.e;
                    if (sg2.d == null) {
                        sg2.d = (ExpandedMenuView) sg2.b.inflate(moe.shizuku.privileged.api.R.layout._0_resource_name_obfuscated_res_0x7f0c000d, (ViewGroup) g3, false);
                        if (sg2.f == null) {
                            sg2.f = new Rg(sg2);
                        }
                        sg2.d.setAdapter((ListAdapter) sg2.f);
                        sg2.d.setOnItemClickListener(sg2);
                    }
                    ExpandedMenuView expandedMenuView = sg2.d;
                    h2.f = expandedMenuView;
                    if (expandedMenuView != null) {
                    }
                }
                h2.n = true;
                return;
            }
            h2.f = view2;
            if (h2.f != null) {
                if (h2.g == null) {
                    Sg sg3 = h2.i;
                    if (sg3.f == null) {
                        sg3.f = new Rg(sg3);
                    }
                }
                ViewGroup.LayoutParams layoutParams3 = h2.f.getLayoutParams();
                if (layoutParams3 == null) {
                    layoutParams3 = new ViewGroup.LayoutParams(-2, -2);
                }
                h2.e.setBackgroundResource(h2.b);
                ViewParent parent = h2.f.getParent();
                if (parent instanceof ViewGroup) {
                    ((ViewGroup) parent).removeView(h2.f);
                }
                h2.e.addView(h2.f, layoutParams3);
                if (!h2.f.hasFocus()) {
                    h2.f.requestFocus();
                }
            }
            h2.n = true;
            return;
            i = -2;
            h2.l = false;
            WindowManager.LayoutParams layoutParams4 = new WindowManager.LayoutParams(i, -2, 0, 0, 1002, 8519680, -3);
            layoutParams4.gravity = h2.c;
            layoutParams4.windowAnimations = h2.d;
            windowManager.addView(h2.e, layoutParams4);
            h2.m = true;
            if (i2 == 0) {
                K();
            }
        }
    }

    public final boolean H(H2 h2, int i, KeyEvent keyEvent) {
        MenuC0129di menuC0129di;
        if (keyEvent.isSystem()) {
            return false;
        }
        if ((h2.k || I(h2, keyEvent)) && (menuC0129di = h2.h) != null) {
            return menuC0129di.performShortcut(i, keyEvent, 1);
        }
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:62:0x00d6  */
    /* JADX WARN: Code duplicated, block: B:67:0x00e0  */
    /* JADX WARN: Code duplicated, block: B:71:0x00fe  */
    /* JADX WARN: Code duplicated, block: B:74:0x0103 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:75:0x0105  */
    /* JADX WARN: Code duplicated, block: B:82:0x011a  */
    public final boolean I(H2 h2, KeyEvent keyEvent) {
        MenuC0129di menuC0129di;
        Y8 y8;
        Y8 y9;
        Resources.Theme themeNewTheme;
        Y8 y10;
        Y8 y11;
        if (!this.P) {
            if (h2.k) {
                return true;
            }
            H2 h3 = this.L;
            if (h3 != null && h3 != h2) {
                u(h3, false);
            }
            Window.Callback callback = this.l.getCallback();
            int i = h2.a;
            if (callback != null) {
                h2.g = callback.onCreatePanelView(i);
            }
            boolean z = i == 0 || i == 108;
            if (z && (y11 = this.r) != null) {
                ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) y11;
                actionBarOverlayLayout.k();
                actionBarOverlayLayout.e.l = true;
            }
            if (h2.g == null && (!z || !(this.o instanceof C0260hq))) {
                MenuC0129di menuC0129di2 = h2.h;
                if (menuC0129di2 == null || h2.o) {
                    if (menuC0129di2 == null) {
                        Context context = this.k;
                        if ((i == 0 || i == 108) && this.r != null) {
                            TypedValue typedValue = new TypedValue();
                            Resources.Theme theme = context.getTheme();
                            theme.resolveAttribute(moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f040009, typedValue, true);
                            if (typedValue.resourceId != 0) {
                                themeNewTheme = context.getResources().newTheme();
                                themeNewTheme.setTo(theme);
                                themeNewTheme.applyStyle(typedValue.resourceId, true);
                                themeNewTheme.resolveAttribute(moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f04000a, typedValue, true);
                            } else {
                                theme.resolveAttribute(moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f04000a, typedValue, true);
                                themeNewTheme = null;
                            }
                            if (typedValue.resourceId != 0) {
                                if (themeNewTheme == null) {
                                    themeNewTheme = context.getResources().newTheme();
                                    themeNewTheme.setTo(theme);
                                }
                                themeNewTheme.applyStyle(typedValue.resourceId, true);
                            }
                            if (themeNewTheme != null) {
                                F7 f7 = new F7(context, 0);
                                f7.getTheme().setTo(themeNewTheme);
                                context = f7;
                            }
                        }
                        MenuC0129di menuC0129di3 = new MenuC0129di(context);
                        menuC0129di3.e = this;
                        MenuC0129di menuC0129di4 = h2.h;
                        if (menuC0129di3 != menuC0129di4) {
                            if (menuC0129di4 != null) {
                                menuC0129di4.r(h2.i);
                            }
                            h2.h = menuC0129di3;
                            Sg sg = h2.i;
                            if (sg != null) {
                                menuC0129di3.b(sg, menuC0129di3.a);
                            }
                        }
                        if (h2.h != null) {
                            if (z && (y9 = this.r) != null) {
                                if (this.s == null) {
                                    this.s = new C0729x2(this, 1);
                                }
                                ((ActionBarOverlayLayout) y9).m(h2.h, this.s);
                            }
                            h2.h.w();
                            if (callback.onCreatePanelMenu(i, h2.h)) {
                                h2.o = false;
                            } else {
                                menuC0129di = h2.h;
                                if (menuC0129di != null) {
                                    if (menuC0129di != null) {
                                        menuC0129di.r(h2.i);
                                    }
                                    h2.h = null;
                                }
                                if (z && (y8 = this.r) != null) {
                                    ((ActionBarOverlayLayout) y8).m(null, this.s);
                                }
                            }
                        }
                    } else {
                        if (z) {
                            if (this.s == null) {
                                this.s = new C0729x2(this, 1);
                            }
                            ((ActionBarOverlayLayout) y9).m(h2.h, this.s);
                        }
                        h2.h.w();
                        if (callback.onCreatePanelMenu(i, h2.h)) {
                            menuC0129di = h2.h;
                            if (menuC0129di != null) {
                                if (menuC0129di != null) {
                                    menuC0129di.r(h2.i);
                                }
                                h2.h = null;
                            }
                            if (z) {
                                ((ActionBarOverlayLayout) y8).m(null, this.s);
                            }
                        } else {
                            h2.o = false;
                        }
                    }
                }
                h2.h.w();
                Bundle bundle = h2.p;
                if (bundle != null) {
                    h2.h.s(bundle);
                    h2.p = null;
                }
                if (!callback.onPreparePanel(0, h2.g, h2.h)) {
                    if (z && (y10 = this.r) != null) {
                        ((ActionBarOverlayLayout) y10).m(null, this.s);
                    }
                    h2.h.v();
                    return false;
                }
                h2.h.setQwertyMode(KeyCharacterMap.load(keyEvent != null ? keyEvent.getDeviceId() : -1).getKeyboardType() != 1);
                h2.h.v();
            }
            h2.k = true;
            h2.l = false;
            this.L = h2;
            return true;
        }
        return false;
    }

    public final void J() {
        if (this.z) {
            throw new AndroidRuntimeException("Window feature must be requested before adding content");
        }
    }

    public final void K() {
        OnBackInvokedCallback onBackInvokedCallback;
        if (Build.VERSION.SDK_INT >= 33) {
            boolean z = false;
            if (this.c0 != null && (B(0).m || this.u != null)) {
                z = true;
            }
            if (z && this.d0 == null) {
                this.d0 = B2.b(this.c0, this);
            } else {
                if (z || (onBackInvokedCallback = this.d0) == null) {
                    return;
                }
                B2.c(this.c0, onBackInvokedCallback);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0044, code lost:
    
        if (r6.j() != false) goto L20;
     */
    @Override // rikka.shizuku.InterfaceC0067bi
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void a(MenuC0129di menuC0129di) {
        ActionMenuView actionMenuView;
        C0 c0;
        Y8 y8 = this.r;
        if (y8 != null) {
            ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) y8;
            actionBarOverlayLayout.k();
            Toolbar toolbar = actionBarOverlayLayout.e.a;
            if (toolbar.getVisibility() == 0 && (actionMenuView = toolbar.a) != null && actionMenuView.s) {
                if (ViewConfiguration.get(this.k).hasPermanentMenuKey()) {
                    ActionBarOverlayLayout actionBarOverlayLayout2 = (ActionBarOverlayLayout) this.r;
                    actionBarOverlayLayout2.k();
                    ActionMenuView actionMenuView2 = actionBarOverlayLayout2.e.a.a;
                    if (actionMenuView2 != null) {
                        C0 c1 = actionMenuView2.t;
                        if (c1 != null) {
                            if (c1.s == null) {
                            }
                        }
                    }
                }
                Window.Callback callback = this.l.getCallback();
                ActionBarOverlayLayout actionBarOverlayLayout3 = (ActionBarOverlayLayout) this.r;
                actionBarOverlayLayout3.k();
                if (actionBarOverlayLayout3.e.a.r()) {
                    ActionBarOverlayLayout actionBarOverlayLayout4 = (ActionBarOverlayLayout) this.r;
                    actionBarOverlayLayout4.k();
                    ActionMenuView actionMenuView3 = actionBarOverlayLayout4.e.a.a;
                    if (actionMenuView3 != null && (c0 = actionMenuView3.t) != null) {
                        c0.e();
                    }
                    if (this.P) {
                        return;
                    }
                    callback.onPanelClosed(108, B(0).h);
                    return;
                }
                if (callback == null || this.P) {
                    return;
                }
                if (this.X && (1 & this.Y) != 0) {
                    View decorView = this.l.getDecorView();
                    RunnableC0698w2 runnableC0698w2 = this.Z;
                    decorView.removeCallbacks(runnableC0698w2);
                    runnableC0698w2.run();
                }
                H2 h2B = B(0);
                MenuC0129di menuC0129di2 = h2B.h;
                if (menuC0129di2 == null || h2B.o || !callback.onPreparePanel(0, h2B.g, menuC0129di2)) {
                    return;
                }
                callback.onMenuOpened(108, h2B.h);
                ActionBarOverlayLayout actionBarOverlayLayout5 = (ActionBarOverlayLayout) this.r;
                actionBarOverlayLayout5.k();
                actionBarOverlayLayout5.e.a.C();
                return;
            }
        }
        H2 h2B2 = B(0);
        h2B2.n = true;
        u(h2B2, false);
        G(h2B2, null);
    }

    @Override // rikka.shizuku.AbstractC0667v2
    public final View b(String str, Context context, AttributeSet attributeSet) {
        View t2;
        String attributeValue = str;
        byte b = 4;
        View view = null;
        if (this.b0 == null) {
            int[] iArr = Nk.j;
            Context context2 = this.k;
            String string = context2.obtainStyledAttributes(iArr).getString(116);
            if (string == null) {
                this.b0 = new D3();
            } else {
                try {
                    this.b0 = (D3) context2.getClassLoader().loadClass(string).getDeclaredConstructor(null).newInstance(null);
                } catch (Throwable th) {
                    Log.i("AppCompatDelegate", "Failed to instantiate custom view inflater " + string + ". Falling back to default.", th);
                    this.b0 = new D3();
                }
            }
        }
        D3 d3 = this.b0;
        int i = AbstractC0415ms.a;
        d3.getClass();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Nk.z, 0, 0);
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(4, 0);
        if (resourceId != 0) {
            Log.i("AppCompatViewInflater", "app:theme is now deprecated. Please move to using android:theme instead.");
        }
        typedArrayObtainStyledAttributes.recycle();
        Context f7 = (resourceId == 0 || ((context instanceof F7) && ((F7) context).a == resourceId)) ? context : new F7(context, resourceId);
        attributeValue.getClass();
        switch (attributeValue.hashCode()) {
            case -1946472170:
                b = !attributeValue.equals("RatingBar") ? (byte) -1 : (byte) 0;
                break;
            case -1455429095:
                b = !attributeValue.equals("CheckedTextView") ? (byte) -1 : (byte) 1;
                break;
            case -1346021293:
                b = !attributeValue.equals("MultiAutoCompleteTextView") ? (byte) -1 : (byte) 2;
                break;
            case -938935918:
                b = !attributeValue.equals("TextView") ? (byte) -1 : (byte) 3;
                break;
            case -937446323:
                if (!attributeValue.equals("ImageButton")) {
                    b = -1;
                }
                break;
            case -658531749:
                b = !attributeValue.equals("SeekBar") ? (byte) -1 : (byte) 5;
                break;
            case -339785223:
                b = !attributeValue.equals("Spinner") ? (byte) -1 : (byte) 6;
                break;
            case 776382189:
                b = !attributeValue.equals("RadioButton") ? (byte) -1 : (byte) 7;
                break;
            case 799298502:
                b = !attributeValue.equals("ToggleButton") ? (byte) -1 : (byte) 8;
                break;
            case 1125864064:
                b = !attributeValue.equals("ImageView") ? (byte) -1 : (byte) 9;
                break;
            case 1413872058:
                b = !attributeValue.equals("AutoCompleteTextView") ? (byte) -1 : (byte) 10;
                break;
            case 1601505219:
                b = !attributeValue.equals("CheckBox") ? (byte) -1 : (byte) 11;
                break;
            case 1666676343:
                b = !attributeValue.equals("EditText") ? (byte) -1 : (byte) 12;
                break;
            case 2001146706:
                b = !attributeValue.equals("Button") ? (byte) -1 : (byte) 13;
                break;
            default:
                b = -1;
                break;
        }
        switch (b) {
            case 0:
                t2 = new T2(f7, attributeSet);
                break;
            case 1:
                t2 = d3.d(f7, attributeSet);
                break;
            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                t2 = new P2(f7, attributeSet);
                break;
            case 3:
                t2 = d3.f(f7, attributeSet);
                break;
            case 4:
                t2 = new N2(f7, attributeSet, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f04024a);
                break;
            case BulletSpan.STANDARD_BULLET_RADIUS /* 5 */:
                t2 = new V2(f7, attributeSet);
                break;
            case 6:
                t2 = new C0297j3(f7, attributeSet);
                break;
            case 7:
                t2 = d3.e(f7, attributeSet);
                break;
            case 8:
                t2 = new B3(f7, attributeSet);
                break;
            case 9:
                t2 = new O2(f7, attributeSet, 0);
                break;
            case 10:
                t2 = d3.a(f7, attributeSet);
                break;
            case 11:
                t2 = d3.c(f7, attributeSet);
                break;
            case 12:
                t2 = new M2(f7, attributeSet);
                break;
            case 13:
                t2 = d3.b(f7, attributeSet);
                break;
            default:
                t2 = null;
                break;
        }
        if (t2 == null && context != f7) {
            Object[] objArr = d3.a;
            if (attributeValue.equals("view")) {
                attributeValue = attributeSet.getAttributeValue(null, "class");
            }
            try {
                objArr[0] = f7;
                objArr[1] = attributeSet;
                if (-1 == attributeValue.indexOf(46)) {
                    int i2 = 0;
                    while (true) {
                        String[] strArr = D3.g;
                        if (i2 < 3) {
                            View viewG = d3.g(f7, attributeValue, strArr[i2]);
                            if (viewG != null) {
                                objArr[0] = null;
                                objArr[1] = null;
                                view = viewG;
                            } else {
                                i2++;
                            }
                        } else {
                            objArr[0] = null;
                            objArr[1] = null;
                        }
                    }
                } else {
                    View viewG2 = d3.g(f7, attributeValue, null);
                    objArr[0] = null;
                    objArr[1] = null;
                    view = viewG2;
                }
            } catch (Exception unused) {
                objArr[0] = null;
                objArr[1] = null;
            } catch (Throwable th2) {
                objArr[0] = null;
                objArr[1] = null;
                throw th2;
            }
            t2 = view;
        }
        if (t2 != null) {
            Context context3 = t2.getContext();
            if (context3 instanceof ContextWrapper) {
                WeakHashMap weakHashMap = Hs.a;
                if (t2.hasOnClickListeners()) {
                    TypedArray typedArrayObtainStyledAttributes2 = context3.obtainStyledAttributes(attributeSet, D3.c);
                    String string2 = typedArrayObtainStyledAttributes2.getString(0);
                    if (string2 != null) {
                        t2.setOnClickListener(new C3(t2, string2));
                    }
                    typedArrayObtainStyledAttributes2.recycle();
                }
            }
            if (Build.VERSION.SDK_INT <= 28) {
                TypedArray typedArrayObtainStyledAttributes3 = f7.obtainStyledAttributes(attributeSet, D3.d);
                if (typedArrayObtainStyledAttributes3.hasValue(0)) {
                    boolean z = typedArrayObtainStyledAttributes3.getBoolean(0, false);
                    WeakHashMap weakHashMap2 = Hs.a;
                    new C0693vs(moe.shizuku.privileged.api.R.id._0_resource_name_obfuscated_res_0x7f0901bc, Boolean.class, 0, 28, 3).d(t2, Boolean.valueOf(z));
                }
                typedArrayObtainStyledAttributes3.recycle();
                TypedArray typedArrayObtainStyledAttributes4 = f7.obtainStyledAttributes(attributeSet, D3.e);
                if (typedArrayObtainStyledAttributes4.hasValue(0)) {
                    Hs.n(t2, typedArrayObtainStyledAttributes4.getString(0));
                }
                typedArrayObtainStyledAttributes4.recycle();
                TypedArray typedArrayObtainStyledAttributes5 = f7.obtainStyledAttributes(attributeSet, D3.f);
                if (typedArrayObtainStyledAttributes5.hasValue(0)) {
                    boolean z2 = typedArrayObtainStyledAttributes5.getBoolean(0, false);
                    WeakHashMap weakHashMap3 = Hs.a;
                    new C0693vs(moe.shizuku.privileged.api.R.id._0_resource_name_obfuscated_res_0x7f0901c4, Boolean.class, 0, 28, 0).d(t2, Boolean.valueOf(z2));
                }
                typedArrayObtainStyledAttributes5.recycle();
            }
        }
        return t2;
    }

    @Override // rikka.shizuku.AbstractC0667v2
    public final void c() {
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(this.k);
        if (layoutInflaterFrom.getFactory() == null) {
            layoutInflaterFrom.setFactory2(this);
        } else {
            if (layoutInflaterFrom.getFactory2() instanceof I2) {
                return;
            }
            Log.i("AppCompatDelegate", "The Activity's LayoutInflater already has a Factory installed so we can not install AppCompat's");
        }
    }

    @Override // rikka.shizuku.AbstractC0667v2
    public final void d() {
        if (this.o != null) {
            C();
            if (this.o.D()) {
                return;
            }
            D(0);
        }
    }

    @Override // rikka.shizuku.AbstractC0667v2
    public final void f() {
        String strY;
        this.N = true;
        p(false, true);
        z();
        Object obj = this.j;
        if (obj instanceof Activity) {
            try {
                Activity activity = (Activity) obj;
                try {
                    strY = AbstractC0744xh.y(activity, activity.getComponentName());
                } catch (PackageManager.NameNotFoundException e) {
                    throw new IllegalArgumentException(e);
                }
            } catch (IllegalArgumentException unused) {
                strY = null;
            }
            if (strY != null) {
                AbstractC0744xh abstractC0744xh = this.o;
                if (abstractC0744xh == null) {
                    this.a0 = true;
                } else {
                    abstractC0744xh.S(true);
                }
            }
            synchronized (AbstractC0667v2.h) {
                AbstractC0667v2.h(this);
                AbstractC0667v2.g.add(new WeakReference(this));
            }
        }
        this.Q = new Configuration(this.k.getResources().getConfiguration());
        this.O = true;
    }

    /* JADX WARN: Code duplicated, block: B:22:0x004d  */
    @Override // rikka.shizuku.AbstractC0667v2
    public final void g() {
        if (this.j instanceof Activity) {
            synchronized (AbstractC0667v2.h) {
                AbstractC0667v2.h(this);
            }
        }
        if (this.X) {
            this.l.getDecorView().removeCallbacks(this.Z);
        }
        this.P = true;
        if (this.R != -100) {
            Object obj = this.j;
            if ((obj instanceof Activity) && ((Activity) obj).isChangingConfigurations()) {
                e0.put(this.j.getClass().getName(), Integer.valueOf(this.R));
            } else {
                e0.remove(this.j.getClass().getName());
            }
        } else {
            e0.remove(this.j.getClass().getName());
        }
        AbstractC0744xh abstractC0744xh = this.o;
        if (abstractC0744xh != null) {
            abstractC0744xh.K();
        }
        D2 d2 = this.V;
        if (d2 != null) {
            d2.c();
        }
        D2 d3 = this.W;
        if (d3 != null) {
            d3.c();
        }
    }

    @Override // rikka.shizuku.AbstractC0667v2
    public final boolean i(int i) {
        if (i == 8) {
            Log.i("AppCompatDelegate", "You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR id when requesting this feature.");
            i = 108;
        } else if (i == 9) {
            Log.i("AppCompatDelegate", "You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR_OVERLAY id when requesting this feature.");
            i = 109;
        }
        if (this.I && i == 108) {
            return false;
        }
        if (this.E && i == 1) {
            this.E = false;
        }
        if (i == 1) {
            J();
            this.I = true;
            return true;
        }
        if (i == 2) {
            J();
            this.C = true;
            return true;
        }
        if (i == 5) {
            J();
            this.D = true;
            return true;
        }
        if (i == 10) {
            J();
            this.G = true;
            return true;
        }
        if (i == 108) {
            J();
            this.E = true;
            return true;
        }
        if (i != 109) {
            return this.l.requestFeature(i);
        }
        J();
        this.F = true;
        return true;
    }

    @Override // rikka.shizuku.AbstractC0667v2
    public final void j(int i) {
        y();
        ViewGroup viewGroup = (ViewGroup) this.A.findViewById(R.id.content);
        viewGroup.removeAllViews();
        LayoutInflater.from(this.k).inflate(i, viewGroup);
        this.m.a(this.l.getCallback());
    }

    @Override // rikka.shizuku.AbstractC0667v2
    public final void k(View view) {
        y();
        ViewGroup viewGroup = (ViewGroup) this.A.findViewById(R.id.content);
        viewGroup.removeAllViews();
        viewGroup.addView(view);
        this.m.a(this.l.getCallback());
    }

    @Override // rikka.shizuku.AbstractC0667v2
    public final void l(View view, ViewGroup.LayoutParams layoutParams) {
        y();
        ViewGroup viewGroup = (ViewGroup) this.A.findViewById(R.id.content);
        viewGroup.removeAllViews();
        viewGroup.addView(view, layoutParams);
        this.m.a(this.l.getCallback());
    }

    /* JADX WARN: Code duplicated, block: B:20:0x002a  */
    @Override // rikka.shizuku.InterfaceC0067bi
    public final boolean n(MenuC0129di menuC0129di, MenuItem menuItem) {
        H2 h2;
        Window.Callback callback = this.l.getCallback();
        if (callback != null && !this.P) {
            MenuC0129di menuC0129diK = menuC0129di.k();
            H2[] h2Arr = this.K;
            int length = h2Arr != null ? h2Arr.length : 0;
            for (int i = 0; i < length; i++) {
                h2 = h2Arr[i];
                if (h2 != null && h2.h == menuC0129diK) {
                    if (h2 != null) {
                        return callback.onMenuItemSelected(h2.a, menuItem);
                    }
                }
            }
            h2 = null;
            if (h2 != null) {
                return callback.onMenuItemSelected(h2.a, menuItem);
            }
        }
        return false;
    }

    @Override // rikka.shizuku.AbstractC0667v2
    public final void o(CharSequence charSequence) {
        this.q = charSequence;
        Y8 y8 = this.r;
        if (y8 == null) {
            AbstractC0744xh abstractC0744xh = this.o;
            if (abstractC0744xh != null) {
                abstractC0744xh.W(charSequence);
                return;
            }
            TextView textView = this.B;
            if (textView != null) {
                textView.setText(charSequence);
                return;
            }
            return;
        }
        ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) y8;
        actionBarOverlayLayout.k();
        C0351kq c0351kq = actionBarOverlayLayout.e;
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

    @Override // android.view.LayoutInflater.Factory2
    public final View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        return b(str, context, attributeSet);
    }

    /* JADX WARN: Code duplicated, block: B:65:0x00d9  */
    /* JADX WARN: Multi-variable type inference failed */
    public final boolean p(boolean z, boolean z2) {
        int i;
        boolean z3;
        Object obj;
        Object obj2;
        int i2 = 0;
        if (this.P) {
            return false;
        }
        int i3 = this.R;
        if (i3 == -100) {
            i3 = AbstractC0667v2.b;
        }
        Context context = this.k;
        int iE = E(context, i3);
        int i4 = Build.VERSION.SDK_INT;
        LongSparseArray longSparseArray = null;
        C0342kh c0342khR = i4 < 33 ? r(context) : null;
        if (!z2 && c0342khR != null) {
            c0342khR = AbstractC0791z2.b(context.getResources().getConfiguration());
        }
        Configuration configurationV = v(context, iE, c0342khR, null, false);
        boolean z4 = this.U;
        Object obj3 = this.j;
        boolean z5 = true;
        if (z4 || !(obj3 instanceof Activity)) {
            this.U = true;
            i = this.T;
        } else {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager == null) {
                i = 0;
            } else {
                try {
                    ActivityInfo activityInfo = packageManager.getActivityInfo(new ComponentName(context, obj3.getClass()), i4 >= 29 ? 269221888 : 786432);
                    if (activityInfo != null) {
                        this.T = activityInfo.configChanges;
                    }
                } catch (PackageManager.NameNotFoundException unused) {
                    this.T = 0;
                }
                this.U = true;
                i = this.T;
            }
        }
        Configuration configuration = this.Q;
        if (configuration == null) {
            configuration = context.getResources().getConfiguration();
        }
        int i5 = configuration.uiMode & 48;
        int i6 = configurationV.uiMode & 48;
        C0342kh c0342khB = AbstractC0791z2.b(configuration);
        C0342kh c0342khB2 = c0342khR == null ? null : AbstractC0791z2.b(configurationV);
        int i7 = i5 != i6 ? 512 : 0;
        if (c0342khB2 != null && !c0342khB.equals(c0342khB2)) {
            i7 |= 8196;
        }
        if (((~i) & i7) != 0 && z && this.N && ((g0 || this.O) && (obj3 instanceof Activity))) {
            Activity activity = (Activity) obj3;
            if (activity.isChild()) {
                z3 = false;
            } else {
                if (Build.VERSION.SDK_INT >= 28) {
                    activity.recreate();
                } else {
                    new Handler(activity.getMainLooper()).post(new G0(i2, activity));
                }
                z3 = true;
            }
        } else {
            z3 = false;
        }
        if (z3 || i7 == 0) {
            z5 = z3;
        } else {
            i2 = (i7 & i) == i7 ? 1 : 0;
            Resources resources = context.getResources();
            Configuration configuration2 = new Configuration(resources.getConfiguration());
            configuration2.uiMode = (resources.getConfiguration().uiMode & (-49)) | i6;
            if (c0342khB2 != null) {
                AbstractC0791z2.d(configuration2, c0342khB2);
            }
            resources.updateConfiguration(configuration2, null);
            int i8 = Build.VERSION.SDK_INT;
            if (i8 < 26 && i8 < 28) {
                if (!AbstractC0599sr.H) {
                    try {
                        Field declaredField = Resources.class.getDeclaredField("mResourcesImpl");
                        AbstractC0599sr.G = declaredField;
                        declaredField.setAccessible(true);
                    } catch (NoSuchFieldException e) {
                        Log.e("ResourcesFlusher", "Could not retrieve Resources#mResourcesImpl field", e);
                    }
                    AbstractC0599sr.H = true;
                }
                Field field = AbstractC0599sr.G;
                if (field != null) {
                    try {
                        obj = field.get(resources);
                    } catch (IllegalAccessException e2) {
                        Log.e("ResourcesFlusher", "Could not retrieve value from Resources#mResourcesImpl", e2);
                        obj = null;
                    }
                    if (obj != null) {
                        if (!AbstractC0599sr.B) {
                            try {
                                Field declaredField2 = obj.getClass().getDeclaredField("mDrawableCache");
                                AbstractC0599sr.A = declaredField2;
                                declaredField2.setAccessible(true);
                            } catch (NoSuchFieldException e3) {
                                Log.e("ResourcesFlusher", "Could not retrieve ResourcesImpl#mDrawableCache field", e3);
                            }
                            AbstractC0599sr.B = true;
                        }
                        Field field2 = AbstractC0599sr.A;
                        if (field2 != null) {
                            try {
                                obj2 = field2.get(obj);
                            } catch (IllegalAccessException e4) {
                                Log.e("ResourcesFlusher", "Could not retrieve value from ResourcesImpl#mDrawableCache", e4);
                                obj2 = null;
                            }
                        } else {
                            obj2 = null;
                        }
                        if (obj2 != null) {
                            if (!AbstractC0599sr.D) {
                                try {
                                    AbstractC0599sr.C = Class.forName("android.content.res.ThemedResourceCache");
                                } catch (ClassNotFoundException e5) {
                                    Log.e("ResourcesFlusher", "Could not find ThemedResourceCache class", e5);
                                }
                                AbstractC0599sr.D = true;
                            }
                            Class cls = AbstractC0599sr.C;
                            if (cls != null) {
                                if (!AbstractC0599sr.F) {
                                    try {
                                        Field declaredField3 = cls.getDeclaredField("mUnthemedEntries");
                                        AbstractC0599sr.E = declaredField3;
                                        declaredField3.setAccessible(true);
                                    } catch (NoSuchFieldException e6) {
                                        Log.e("ResourcesFlusher", "Could not retrieve ThemedResourceCache#mUnthemedEntries field", e6);
                                    }
                                    AbstractC0599sr.F = true;
                                }
                                Field field3 = AbstractC0599sr.E;
                                if (field3 != null) {
                                    try {
                                        longSparseArray = (LongSparseArray) field3.get(obj2);
                                    } catch (IllegalAccessException e7) {
                                        Log.e("ResourcesFlusher", "Could not retrieve value from ThemedResourceCache#mUnthemedEntries", e7);
                                    }
                                    if (longSparseArray != null) {
                                        Yl.a(longSparseArray);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            int i9 = this.S;
            if (i9 != 0) {
                context.setTheme(i9);
                context.getTheme().applyStyle(this.S, true);
            }
            if (i2 != 0 && (obj3 instanceof Activity)) {
                Activity activity2 = (Activity) obj3;
                if (activity2 instanceof InterfaceC0743xg) {
                    if (((InterfaceC0743xg) activity2).e().c.a(EnumC0588sg.c)) {
                        activity2.onConfigurationChanged(configuration2);
                    }
                } else if (this.O && !this.P) {
                    activity2.onConfigurationChanged(configuration2);
                }
            }
        }
        if (z5 && (obj3 instanceof AbstractActivityC0051b2)) {
            if ((i7 & 512) != 0) {
            }
            if ((i7 & 4) != 0) {
            }
        }
        if (z5 && c0342khB2 != null) {
            AbstractC0791z2.c(AbstractC0791z2.b(context.getResources().getConfiguration()));
        }
        if (i3 == 0) {
            A(context).h();
        } else {
            D2 d2 = this.V;
            if (d2 != null) {
                d2.c();
            }
        }
        if (i3 == 3) {
            if (this.W == null) {
                this.W = new D2(this, context);
            }
            this.W.h();
        } else {
            D2 d3 = this.W;
            if (d3 != null) {
                d3.c();
            }
        }
        return z5;
    }

    /* JADX WARN: Code duplicated, block: B:34:0x0074  */
    public final void q(Window window) {
        Drawable drawableC;
        OnBackInvokedDispatcher onBackInvokedDispatcher;
        OnBackInvokedCallback onBackInvokedCallback;
        int resourceId;
        if (this.l != null) {
            throw new IllegalStateException("AppCompat has already installed itself into the Window");
        }
        Window.Callback callback = window.getCallback();
        if (callback instanceof C2) {
            throw new IllegalStateException("AppCompat has already installed itself into the Window");
        }
        C2 c2 = new C2(this, callback);
        this.m = c2;
        window.setCallback(c2);
        int[] iArr = f0;
        Context context = this.k;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes((AttributeSet) null, iArr);
        if (!typedArrayObtainStyledAttributes.hasValue(0) || (resourceId = typedArrayObtainStyledAttributes.getResourceId(0, 0)) == 0) {
            drawableC = null;
        } else {
            L2 l2A = L2.a();
            synchronized (l2A) {
                drawableC = l2A.a.c(resourceId, context, true);
            }
        }
        if (drawableC != null) {
            window.setBackgroundDrawable(drawableC);
        }
        typedArrayObtainStyledAttributes.recycle();
        this.l = window;
        if (Build.VERSION.SDK_INT < 33 || (onBackInvokedDispatcher = this.c0) != null) {
            return;
        }
        if (onBackInvokedDispatcher != null && (onBackInvokedCallback = this.d0) != null) {
            B2.c(onBackInvokedDispatcher, onBackInvokedCallback);
            this.d0 = null;
        }
        Object obj = this.j;
        if (obj instanceof Activity) {
            Activity activity = (Activity) obj;
            if (activity.getWindow() != null) {
                this.c0 = B2.a(activity);
            } else {
                this.c0 = null;
            }
        } else {
            this.c0 = null;
        }
        K();
    }

    public final void s(int i, H2 h2, MenuC0129di menuC0129di) {
        if (menuC0129di == null) {
            if (h2 == null && i >= 0) {
                H2[] h2Arr = this.K;
                if (i < h2Arr.length) {
                    h2 = h2Arr[i];
                }
            }
            if (h2 != null) {
                menuC0129di = h2.h;
            }
        }
        if ((h2 == null || h2.m) && !this.P) {
            C2 c2 = this.m;
            Window.Callback callback = this.l.getCallback();
            c2.getClass();
            try {
                c2.e = true;
                callback.onPanelClosed(i, menuC0129di);
            } finally {
                c2.e = false;
            }
        }
    }

    public final void t(MenuC0129di menuC0129di) {
        C0 c0;
        if (this.J) {
            return;
        }
        this.J = true;
        ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) this.r;
        actionBarOverlayLayout.k();
        ActionMenuView actionMenuView = actionBarOverlayLayout.e.a.a;
        if (actionMenuView != null && (c0 = actionMenuView.t) != null) {
            c0.e();
            C0758y0 c0758y0 = c0.r;
            if (c0758y0 != null && c0758y0.b()) {
                c0758y0.i.dismiss();
            }
        }
        Window.Callback callback = this.l.getCallback();
        if (callback != null && !this.P) {
            callback.onPanelClosed(108, menuC0129di);
        }
        this.J = false;
    }

    public final void u(H2 h2, boolean z) {
        G2 g2;
        Y8 y8;
        if (z && h2.a == 0 && (y8 = this.r) != null) {
            ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) y8;
            actionBarOverlayLayout.k();
            if (actionBarOverlayLayout.e.a.r()) {
                t(h2.h);
                return;
            }
        }
        WindowManager windowManager = (WindowManager) this.k.getSystemService("window");
        if (windowManager != null && h2.m && (g2 = h2.e) != null) {
            windowManager.removeView(g2);
            if (z) {
                s(h2.a, h2, null);
            }
        }
        h2.k = false;
        h2.l = false;
        h2.m = false;
        h2.f = null;
        h2.n = true;
        if (this.L == h2) {
            this.L = null;
        }
        if (h2.a == 0) {
            K();
        }
    }

    /* JADX WARN: Code duplicated, block: B:21:0x003f  */
    /* JADX WARN: Code duplicated, block: B:23:0x004a A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:24:0x004c A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:26:0x0050  */
    /* JADX WARN: Code duplicated, block: B:28:0x0056  */
    /* JADX WARN: Code duplicated, block: B:30:0x005e  */
    /* JADX WARN: Code duplicated, block: B:32:0x0062  */
    /* JADX WARN: Code duplicated, block: B:35:0x006b  */
    /* JADX WARN: Code duplicated, block: B:38:0x006f A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:39:0x0071 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:41:0x0075  */
    /* JADX WARN: Code duplicated, block: B:44:0x007b  */
    /* JADX WARN: Code duplicated, block: B:46:0x0085  */
    /* JADX WARN: Code duplicated, block: B:68:0x00eb  */
    /* JADX WARN: Code duplicated, block: B:70:0x00ef  */
    /* JADX WARN: Code duplicated, block: B:85:0x0113  */
    /* JADX WARN: Code duplicated, block: B:87:0x0121  */
    /* JADX WARN: Code duplicated, block: B:89:0x0125  */
    /* JADX WARN: Code duplicated, block: B:91:0x012d  */
    /* JADX WARN: Code duplicated, block: B:94:0x0134 A[RETURN] */
    public final boolean w(KeyEvent keyEvent) {
        View decorView;
        int keyCode;
        H2 h2B;
        Y8 y8;
        Context context;
        boolean z;
        boolean zC;
        boolean zI;
        AudioManager audioManager;
        Toolbar toolbar;
        ActionMenuView actionMenuView;
        C0 c0;
        H2 h2B2;
        Object obj = this.j;
        if ((!(obj instanceof InterfaceC0065bg) && !(obj instanceof J1)) || (decorView = this.l.getDecorView()) == null || !AbstractC0599sr.j(decorView, keyEvent)) {
            if (keyEvent.getKeyCode() == 82) {
                C2 c2 = this.m;
                Window.Callback callback = this.l.getCallback();
                c2.getClass();
                try {
                    c2.d = true;
                    boolean zDispatchKeyEvent = callback.dispatchKeyEvent(keyEvent);
                    c2.d = false;
                    if (!zDispatchKeyEvent) {
                        keyCode = keyEvent.getKeyCode();
                        if (keyEvent.getAction() == 0) {
                            if (keyCode != 4) {
                                this.M = (keyEvent.getFlags() & 128) != 0;
                                return false;
                            }
                            if (keyCode == 82) {
                                if (keyEvent.getRepeatCount() == 0) {
                                    h2B2 = B(0);
                                    if (!h2B2.m) {
                                        I(h2B2, keyEvent);
                                        return true;
                                    }
                                }
                            }
                            return false;
                        }
                        if (keyCode != 4) {
                            if (keyCode == 82) {
                                if (this.u == null) {
                                    h2B = B(0);
                                    y8 = this.r;
                                    context = this.k;
                                    if (y8 != null) {
                                        ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) y8;
                                        actionBarOverlayLayout.k();
                                        toolbar = actionBarOverlayLayout.e.a;
                                        if (toolbar.getVisibility() == 0 || (actionMenuView = toolbar.a) == null || !actionMenuView.s || ViewConfiguration.get(context).hasPermanentMenuKey()) {
                                            z = h2B.m;
                                            if (!z || h2B.l) {
                                                u(h2B, true);
                                                zC = z;
                                            } else {
                                                if (h2B.k) {
                                                    if (h2B.o) {
                                                        h2B.k = false;
                                                        zI = I(h2B, keyEvent);
                                                    } else {
                                                        zI = true;
                                                    }
                                                    if (zI) {
                                                        G(h2B, keyEvent);
                                                        zC = true;
                                                    }
                                                }
                                                zC = false;
                                            }
                                        } else {
                                            ActionBarOverlayLayout actionBarOverlayLayout2 = (ActionBarOverlayLayout) this.r;
                                            actionBarOverlayLayout2.k();
                                            if (actionBarOverlayLayout2.e.a.r()) {
                                                ActionBarOverlayLayout actionBarOverlayLayout3 = (ActionBarOverlayLayout) this.r;
                                                actionBarOverlayLayout3.k();
                                                ActionMenuView actionMenuView2 = actionBarOverlayLayout3.e.a.a;
                                                if (actionMenuView2 != null && (c0 = actionMenuView2.t) != null && c0.e()) {
                                                    zC = true;
                                                }
                                            } else if (!this.P && I(h2B, keyEvent)) {
                                                ActionBarOverlayLayout actionBarOverlayLayout4 = (ActionBarOverlayLayout) this.r;
                                                actionBarOverlayLayout4.k();
                                                zC = actionBarOverlayLayout4.e.a.C();
                                            }
                                            zC = false;
                                        }
                                    } else {
                                        z = h2B.m;
                                        if (z) {
                                        }
                                        u(h2B, true);
                                        zC = z;
                                    }
                                    if (zC) {
                                        audioManager = (AudioManager) context.getApplicationContext().getSystemService("audio");
                                        if (audioManager != null) {
                                            audioManager.playSoundEffect(0);
                                            return true;
                                        }
                                        Log.w("AppCompatDelegate", "Couldn't get audio manager");
                                        return true;
                                    }
                                }
                            }
                            return false;
                        }
                        if (F()) {
                            return false;
                        }
                    }
                } catch (Throwable th) {
                    c2.d = false;
                    throw th;
                }
            } else {
                keyCode = keyEvent.getKeyCode();
                if (keyEvent.getAction() == 0) {
                    if (keyCode != 4) {
                        this.M = (keyEvent.getFlags() & 128) != 0;
                        return false;
                    }
                    if (keyCode == 82) {
                        if (keyEvent.getRepeatCount() == 0) {
                            h2B2 = B(0);
                            if (!h2B2.m) {
                                I(h2B2, keyEvent);
                                return true;
                            }
                        }
                    }
                    return false;
                }
                if (keyCode != 4) {
                    if (keyCode == 82) {
                        if (this.u == null) {
                            h2B = B(0);
                            y8 = this.r;
                            context = this.k;
                            if (y8 != null) {
                                ActionBarOverlayLayout actionBarOverlayLayout5 = (ActionBarOverlayLayout) y8;
                                actionBarOverlayLayout5.k();
                                toolbar = actionBarOverlayLayout5.e.a;
                                if (toolbar.getVisibility() == 0) {
                                    z = h2B.m;
                                    if (z) {
                                    }
                                    u(h2B, true);
                                    zC = z;
                                } else {
                                    z = h2B.m;
                                    if (z) {
                                    }
                                    u(h2B, true);
                                    zC = z;
                                }
                            } else {
                                z = h2B.m;
                                if (z) {
                                }
                                u(h2B, true);
                                zC = z;
                            }
                            if (zC) {
                                audioManager = (AudioManager) context.getApplicationContext().getSystemService("audio");
                                if (audioManager != null) {
                                    audioManager.playSoundEffect(0);
                                    return true;
                                }
                                Log.w("AppCompatDelegate", "Couldn't get audio manager");
                                return true;
                            }
                        }
                    }
                    return false;
                }
                if (F()) {
                    return false;
                }
            }
        }
        return true;
    }

    public final void x(int i) {
        H2 h2B = B(i);
        if (h2B.h != null) {
            Bundle bundle = new Bundle();
            h2B.h.t(bundle);
            if (bundle.size() > 0) {
                h2B.p = bundle;
            }
            h2B.h.w();
            h2B.h.clear();
        }
        h2B.o = true;
        h2B.n = true;
        if ((i == 108 || i == 0) && this.r != null) {
            H2 h2B2 = B(0);
            h2B2.k = false;
            I(h2B2, null);
        }
    }

    public final void y() {
        ViewGroup viewGroup;
        int i = 0;
        if (this.z) {
            return;
        }
        int[] iArr = Nk.j;
        Context context = this.k;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(iArr);
        if (!typedArrayObtainStyledAttributes.hasValue(117)) {
            typedArrayObtainStyledAttributes.recycle();
            throw new IllegalStateException("You need to use a Theme.AppCompat theme (or descendant) with this activity.");
        }
        if (typedArrayObtainStyledAttributes.getBoolean(126, false)) {
            i(1);
        } else if (typedArrayObtainStyledAttributes.getBoolean(117, false)) {
            i(108);
        }
        if (typedArrayObtainStyledAttributes.getBoolean(118, false)) {
            i(109);
        }
        if (typedArrayObtainStyledAttributes.getBoolean(119, false)) {
            i(10);
        }
        this.H = typedArrayObtainStyledAttributes.getBoolean(0, false);
        typedArrayObtainStyledAttributes.recycle();
        z();
        this.l.getDecorView();
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(context);
        if (this.I) {
            viewGroup = this.G ? (ViewGroup) layoutInflaterFrom.inflate(moe.shizuku.privileged.api.R.layout._0_resource_name_obfuscated_res_0x7f0c0016, (ViewGroup) null) : (ViewGroup) layoutInflaterFrom.inflate(moe.shizuku.privileged.api.R.layout._0_resource_name_obfuscated_res_0x7f0c0015, (ViewGroup) null);
        } else if (this.H) {
            viewGroup = (ViewGroup) layoutInflaterFrom.inflate(moe.shizuku.privileged.api.R.layout._0_resource_name_obfuscated_res_0x7f0c000c, (ViewGroup) null);
            this.F = false;
            this.E = false;
        } else if (this.E) {
            TypedValue typedValue = new TypedValue();
            context.getTheme().resolveAttribute(moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f040009, typedValue, true);
            viewGroup = (ViewGroup) LayoutInflater.from(typedValue.resourceId != 0 ? new F7(context, typedValue.resourceId) : context).inflate(moe.shizuku.privileged.api.R.layout._0_resource_name_obfuscated_res_0x7f0c0017, (ViewGroup) null);
            Y8 y8 = (Y8) viewGroup.findViewById(moe.shizuku.privileged.api.R.id._0_resource_name_obfuscated_res_0x7f090083);
            this.r = y8;
            Window.Callback callback = this.l.getCallback();
            ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) y8;
            actionBarOverlayLayout.k();
            actionBarOverlayLayout.e.k = callback;
            if (this.F) {
                ((ActionBarOverlayLayout) this.r).j(109);
            }
            if (this.C) {
                ((ActionBarOverlayLayout) this.r).j(2);
            }
            if (this.D) {
                ((ActionBarOverlayLayout) this.r).j(5);
            }
        } else {
            viewGroup = null;
        }
        if (viewGroup == null) {
            throw new IllegalArgumentException("AppCompat does not support the current theme features: { windowActionBar: " + this.E + ", windowActionBarOverlay: " + this.F + ", android:windowIsFloating: " + this.H + ", windowActionModeOverlay: " + this.G + ", windowNoTitle: " + this.I + " }");
        }
        if (this.r == null) {
            this.B = (TextView) viewGroup.findViewById(moe.shizuku.privileged.api.R.id._0_resource_name_obfuscated_res_0x7f0901de);
        }
        boolean z = AbstractC0323jt.a;
        try {
            Method method = viewGroup.getClass().getMethod("makeOptionalFitsSystemWindows", null);
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }
            method.invoke(viewGroup, null);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException unused) {
        }
        ContentFrameLayout contentFrameLayout = (ContentFrameLayout) viewGroup.findViewById(moe.shizuku.privileged.api.R.id._0_resource_name_obfuscated_res_0x7f090032);
        ViewGroup viewGroup2 = (ViewGroup) this.l.findViewById(R.id.content);
        if (viewGroup2 != null) {
            while (viewGroup2.getChildCount() > 0) {
                View childAt = viewGroup2.getChildAt(0);
                viewGroup2.removeViewAt(0);
                contentFrameLayout.addView(childAt);
            }
            viewGroup2.setId(-1);
            contentFrameLayout.setId(R.id.content);
            if (viewGroup2 instanceof FrameLayout) {
                ((FrameLayout) viewGroup2).setForeground(null);
            }
        }
        this.l.setContentView(viewGroup);
        contentFrameLayout.h = new C0729x2(this, i);
        this.A = viewGroup;
        Object obj = this.j;
        CharSequence title = obj instanceof Activity ? ((Activity) obj).getTitle() : this.q;
        if (!TextUtils.isEmpty(title)) {
            Y8 y9 = this.r;
            if (y9 != null) {
                ActionBarOverlayLayout actionBarOverlayLayout2 = (ActionBarOverlayLayout) y9;
                actionBarOverlayLayout2.k();
                C0351kq c0351kq = actionBarOverlayLayout2.e;
                if (!c0351kq.g) {
                    c0351kq.h = title;
                    if ((c0351kq.b & 8) != 0) {
                        Toolbar toolbar = c0351kq.a;
                        toolbar.A(title);
                        if (c0351kq.g) {
                            Hs.n(toolbar.getRootView(), title);
                        }
                    }
                }
            } else {
                AbstractC0744xh abstractC0744xh = this.o;
                if (abstractC0744xh != null) {
                    abstractC0744xh.W(title);
                } else {
                    TextView textView = this.B;
                    if (textView != null) {
                        textView.setText(title);
                    }
                }
            }
        }
        ContentFrameLayout contentFrameLayout2 = (ContentFrameLayout) this.A.findViewById(R.id.content);
        View decorView = this.l.getDecorView();
        contentFrameLayout2.g.set(decorView.getPaddingLeft(), decorView.getPaddingTop(), decorView.getPaddingRight(), decorView.getPaddingBottom());
        WeakHashMap weakHashMap = Hs.a;
        if (contentFrameLayout2.isLaidOut()) {
            contentFrameLayout2.requestLayout();
        }
        TypedArray typedArrayObtainStyledAttributes2 = context.obtainStyledAttributes(iArr);
        if (contentFrameLayout2.a == null) {
            contentFrameLayout2.a = new TypedValue();
        }
        typedArrayObtainStyledAttributes2.getValue(124, contentFrameLayout2.a);
        if (contentFrameLayout2.b == null) {
            contentFrameLayout2.b = new TypedValue();
        }
        typedArrayObtainStyledAttributes2.getValue(125, contentFrameLayout2.b);
        if (typedArrayObtainStyledAttributes2.hasValue(122)) {
            if (contentFrameLayout2.c == null) {
                contentFrameLayout2.c = new TypedValue();
            }
            typedArrayObtainStyledAttributes2.getValue(122, contentFrameLayout2.c);
        }
        if (typedArrayObtainStyledAttributes2.hasValue(123)) {
            if (contentFrameLayout2.d == null) {
                contentFrameLayout2.d = new TypedValue();
            }
            typedArrayObtainStyledAttributes2.getValue(123, contentFrameLayout2.d);
        }
        if (typedArrayObtainStyledAttributes2.hasValue(120)) {
            if (contentFrameLayout2.e == null) {
                contentFrameLayout2.e = new TypedValue();
            }
            typedArrayObtainStyledAttributes2.getValue(120, contentFrameLayout2.e);
        }
        if (typedArrayObtainStyledAttributes2.hasValue(121)) {
            if (contentFrameLayout2.f == null) {
                contentFrameLayout2.f = new TypedValue();
            }
            typedArrayObtainStyledAttributes2.getValue(121, contentFrameLayout2.f);
        }
        typedArrayObtainStyledAttributes2.recycle();
        contentFrameLayout2.requestLayout();
        this.z = true;
        H2 h2B = B(0);
        if (this.P || h2B.h != null) {
            return;
        }
        D(108);
    }

    public final void z() {
        if (this.l == null) {
            Object obj = this.j;
            if (obj instanceof Activity) {
                q(((Activity) obj).getWindow());
            }
        }
        if (this.l == null) {
            throw new IllegalStateException("We have not been given a Window");
        }
    }
}
