package rikka.shizuku;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SearchEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.PopupWindow;
import androidx.appcompat.widget.ActionBarContextView;
import androidx.appcompat.widget.ViewStubCompat;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;
import moe.shizuku.privileged.api.R;

/* JADX INFO: loaded from: classes.dex */
public final class C2 implements Window.Callback {
    public final Window.Callback a;
    public C0229gq b;
    public boolean c;
    public boolean d;
    public boolean e;
    public final /* synthetic */ I2 f;

    public C2(I2 i2, Window.Callback callback) {
        this.f = i2;
        if (callback == null) {
            throw new IllegalArgumentException("Window callback may not be null");
        }
        this.a = callback;
    }

    public final void a(Window.Callback callback) {
        try {
            this.c = true;
            callback.onContentChanged();
        } finally {
            this.c = false;
        }
    }

    public final boolean b(int i, Menu menu) {
        return this.a.onMenuOpened(i, menu);
    }

    public final void c(int i, Menu menu) {
        this.a.onPanelClosed(i, menu);
    }

    public final void d(List list, Menu menu, int i) {
        AbstractC0787yt.a(this.a, list, menu, i);
    }

    @Override // android.view.Window.Callback
    public final boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
        return this.a.dispatchGenericMotionEvent(motionEvent);
    }

    @Override // android.view.Window.Callback
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        boolean z = this.d;
        Window.Callback callback = this.a;
        if (z) {
            return callback.dispatchKeyEvent(keyEvent);
        }
        return this.f.w(keyEvent) || callback.dispatchKeyEvent(keyEvent);
    }

    @Override // android.view.Window.Callback
    public final boolean dispatchKeyShortcutEvent(KeyEvent keyEvent) {
        if (!this.a.dispatchKeyShortcutEvent(keyEvent)) {
            int keyCode = keyEvent.getKeyCode();
            I2 i2 = this.f;
            i2.C();
            AbstractC0744xh abstractC0744xh = i2.o;
            if (abstractC0744xh == null || !abstractC0744xh.L(keyCode, keyEvent)) {
                H2 h2 = i2.L;
                if (h2 == null || !i2.H(h2, keyEvent.getKeyCode(), keyEvent)) {
                    if (i2.L == null) {
                        H2 h2B = i2.B(0);
                        i2.I(h2B, keyEvent);
                        boolean zH = i2.H(h2B, keyEvent.getKeyCode(), keyEvent);
                        h2B.k = false;
                        if (zH) {
                        }
                    }
                    return false;
                }
                H2 h3 = i2.L;
                if (h3 != null) {
                    h3.l = true;
                    return true;
                }
            }
        }
        return true;
    }

    @Override // android.view.Window.Callback
    public final boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        return this.a.dispatchPopulateAccessibilityEvent(accessibilityEvent);
    }

    @Override // android.view.Window.Callback
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        return this.a.dispatchTouchEvent(motionEvent);
    }

    @Override // android.view.Window.Callback
    public final boolean dispatchTrackballEvent(MotionEvent motionEvent) {
        return this.a.dispatchTrackballEvent(motionEvent);
    }

    @Override // android.view.Window.Callback
    public final void onActionModeFinished(ActionMode actionMode) {
        this.a.onActionModeFinished(actionMode);
    }

    @Override // android.view.Window.Callback
    public final void onActionModeStarted(ActionMode actionMode) {
        this.a.onActionModeStarted(actionMode);
    }

    @Override // android.view.Window.Callback
    public final void onAttachedToWindow() {
        this.a.onAttachedToWindow();
    }

    @Override // android.view.Window.Callback
    public final void onContentChanged() {
        if (this.c) {
            this.a.onContentChanged();
        }
    }

    @Override // android.view.Window.Callback
    public final boolean onCreatePanelMenu(int i, Menu menu) {
        if (i != 0 || (menu instanceof MenuC0129di)) {
            return this.a.onCreatePanelMenu(i, menu);
        }
        return false;
    }

    @Override // android.view.Window.Callback
    public final View onCreatePanelView(int i) {
        C0229gq c0229gq = this.b;
        if (c0229gq != null) {
            c0229gq.getClass();
            View view = i == 0 ? new View(c0229gq.a.q.a.getContext()) : null;
            if (view != null) {
                return view;
            }
        }
        return this.a.onCreatePanelView(i);
    }

    @Override // android.view.Window.Callback
    public final void onDetachedFromWindow() {
        this.a.onDetachedFromWindow();
    }

    @Override // android.view.Window.Callback
    public final boolean onMenuItemSelected(int i, MenuItem menuItem) {
        return this.a.onMenuItemSelected(i, menuItem);
    }

    @Override // android.view.Window.Callback
    public final boolean onMenuOpened(int i, Menu menu) {
        b(i, menu);
        I2 i2 = this.f;
        if (i == 108) {
            i2.C();
            AbstractC0744xh abstractC0744xh = i2.o;
            if (abstractC0744xh != null) {
                abstractC0744xh.o(true);
            }
        } else {
            i2.getClass();
        }
        return true;
    }

    @Override // android.view.Window.Callback
    public final void onPanelClosed(int i, Menu menu) {
        if (this.e) {
            this.a.onPanelClosed(i, menu);
            return;
        }
        c(i, menu);
        I2 i2 = this.f;
        if (i == 108) {
            i2.C();
            AbstractC0744xh abstractC0744xh = i2.o;
            if (abstractC0744xh != null) {
                abstractC0744xh.o(false);
                return;
            }
            return;
        }
        if (i != 0) {
            i2.getClass();
            return;
        }
        H2 h2B = i2.B(i);
        if (h2B.m) {
            i2.u(h2B, false);
        }
    }

    @Override // android.view.Window.Callback
    public final void onPointerCaptureChanged(boolean z) {
        AbstractC0818zt.a(this.a, z);
    }

    @Override // android.view.Window.Callback
    public final boolean onPreparePanel(int i, View view, Menu menu) {
        MenuC0129di menuC0129di = menu instanceof MenuC0129di ? (MenuC0129di) menu : null;
        if (i == 0 && menuC0129di == null) {
            return false;
        }
        if (menuC0129di != null) {
            menuC0129di.x = true;
        }
        C0229gq c0229gq = this.b;
        if (c0229gq != null && i == 0) {
            C0260hq c0260hq = c0229gq.a;
            if (!c0260hq.t) {
                c0260hq.q.l = true;
                c0260hq.t = true;
            }
        }
        boolean zOnPreparePanel = this.a.onPreparePanel(i, view, menu);
        if (menuC0129di != null) {
            menuC0129di.x = false;
        }
        return zOnPreparePanel;
    }

    @Override // android.view.Window.Callback
    public final void onProvideKeyboardShortcuts(List list, Menu menu, int i) {
        MenuC0129di menuC0129di = this.f.B(0).h;
        if (menuC0129di != null) {
            d(list, menuC0129di, i);
        } else {
            d(list, menu, i);
        }
    }

    @Override // android.view.Window.Callback
    public final boolean onSearchRequested(SearchEvent searchEvent) {
        return AbstractC0756xt.a(this.a, searchEvent);
    }

    @Override // android.view.Window.Callback
    public final void onWindowAttributesChanged(WindowManager.LayoutParams layoutParams) {
        this.a.onWindowAttributesChanged(layoutParams);
    }

    @Override // android.view.Window.Callback
    public final void onWindowFocusChanged(boolean z) {
        this.a.onWindowFocusChanged(z);
    }

    /* JADX WARN: Code duplicated, block: B:52:0x0164  */
    @Override // android.view.Window.Callback
    public final ActionMode onWindowStartingActionMode(ActionMode.Callback callback, int i) {
        boolean z;
        ViewGroup viewGroup;
        int i2 = 1;
        I2 i3 = this.f;
        i3.getClass();
        if (i != 0) {
            return AbstractC0756xt.b(this.a, callback, i);
        }
        Context context = i3.k;
        Xn xn = new Xn();
        xn.b = context;
        xn.a = callback;
        xn.c = new ArrayList();
        xn.d = new C0104co(0);
        F0 f0 = i3.u;
        if (f0 != null) {
            f0.b();
        }
        R2 r2 = new R2(i3, 3, xn);
        i3.C();
        AbstractC0744xh abstractC0744xh = i3.o;
        if (abstractC0744xh != null) {
            i3.u = abstractC0744xh.X(r2);
        }
        if (i3.u == null) {
            C0109ct c0109ct = i3.y;
            if (c0109ct != null) {
                c0109ct.b();
            }
            F0 f1 = i3.u;
            if (f1 != null) {
                f1.b();
            }
            if (i3.v == null) {
                boolean z2 = i3.H;
                Context context2 = i3.k;
                if (z2) {
                    TypedValue typedValue = new TypedValue();
                    Resources.Theme theme = context2.getTheme();
                    theme.resolveAttribute(R.attr._0_resource_name_obfuscated_res_0x7f040009, typedValue, true);
                    if (typedValue.resourceId != 0) {
                        Resources.Theme themeNewTheme = context2.getResources().newTheme();
                        themeNewTheme.setTo(theme);
                        themeNewTheme.applyStyle(typedValue.resourceId, true);
                        F7 f7 = new F7(context2, 0);
                        f7.getTheme().setTo(themeNewTheme);
                        context2 = f7;
                    }
                    i3.v = new ActionBarContextView(context2, null);
                    PopupWindow popupWindow = new PopupWindow(context2, (AttributeSet) null, R.attr._0_resource_name_obfuscated_res_0x7f040018);
                    i3.w = popupWindow;
                    popupWindow.setWindowLayoutType(2);
                    i3.w.setContentView(i3.v);
                    i3.w.setWidth(-1);
                    context2.getTheme().resolveAttribute(R.attr._0_resource_name_obfuscated_res_0x7f040003, typedValue, true);
                    TypedValue.complexToDimensionPixelSize(typedValue.data, context2.getResources().getDisplayMetrics());
                    ActionBarContextView actionBarContextView = i3.v;
                    actionBarContextView.e = actionBarContextView.u + actionBarContextView.v;
                    i3.w.setHeight(-2);
                    i3.x = new RunnableC0698w2(i3, i2);
                } else {
                    ViewStubCompat viewStubCompat = (ViewStubCompat) i3.A.findViewById(R.id._0_resource_name_obfuscated_res_0x7f09003f);
                    if (viewStubCompat != null) {
                        i3.C();
                        AbstractC0744xh abstractC0744xh2 = i3.o;
                        Context contextZ = abstractC0744xh2 != null ? abstractC0744xh2.z() : null;
                        if (contextZ != null) {
                            context2 = contextZ;
                        }
                        viewStubCompat.d = LayoutInflater.from(context2);
                        i3.v = (ActionBarContextView) viewStubCompat.a();
                    }
                }
            }
            if (i3.v != null) {
                C0109ct c0109ct2 = i3.y;
                if (c0109ct2 != null) {
                    c0109ct2.b();
                }
                i3.v.f();
                Context context3 = i3.v.getContext();
                ActionBarContextView actionBarContextView2 = i3.v;
                Lo lo = new Lo();
                lo.c = context3;
                lo.d = actionBarContextView2;
                lo.e = r2;
                MenuC0129di menuC0129di = new MenuC0129di(actionBarContextView2.getContext());
                menuC0129di.l = 1;
                lo.h = menuC0129di;
                menuC0129di.e = lo;
                if (((Xn) r2.b).t(lo, menuC0129di)) {
                    lo.h();
                    i3.v.d(lo);
                    i3.u = lo;
                    if (!i3.z || (viewGroup = i3.A) == null) {
                        z = false;
                    } else {
                        WeakHashMap weakHashMap = Hs.a;
                        if (viewGroup.isLaidOut()) {
                            z = true;
                        } else {
                            z = false;
                        }
                    }
                    if (z) {
                        i3.v.setAlpha(0.0f);
                        C0109ct c0109ctA = Hs.a(i3.v);
                        c0109ctA.a(1.0f);
                        i3.y = c0109ctA;
                        c0109ctA.d(new C0760y2(i2, i3));
                    } else {
                        i3.v.setAlpha(1.0f);
                        i3.v.setVisibility(0);
                        if (i3.v.getParent() instanceof View) {
                            View view = (View) i3.v.getParent();
                            WeakHashMap weakHashMap2 = Hs.a;
                            AbstractC0755xs.c(view);
                        }
                    }
                    if (i3.w != null) {
                        i3.l.getDecorView().post(i3.x);
                    }
                } else {
                    i3.u = null;
                }
            }
            i3.K();
            i3.u = i3.u;
        }
        i3.K();
        F0 f2 = i3.u;
        if (f2 != null) {
            return xn.j(f2);
        }
        return null;
    }

    @Override // android.view.Window.Callback
    public final boolean onSearchRequested() {
        return this.a.onSearchRequested();
    }

    @Override // android.view.Window.Callback
    public final ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
        return null;
    }
}
