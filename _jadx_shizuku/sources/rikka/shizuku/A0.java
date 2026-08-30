package rikka.shizuku;

import android.app.Application;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import com.google.android.material.behavior.SwipeDismissBehavior;
import java.lang.reflect.Method;
import java.util.WeakHashMap;
import rikka.html.text.style.BulletSpan;

/* JADX INFO: loaded from: classes.dex */
public final class A0 implements Runnable {
    public final /* synthetic */ int a;
    public Object b;
    public final /* synthetic */ Object c;

    public /* synthetic */ A0(Object obj, int i, Object obj2) {
        this.a = i;
        this.c = obj;
        this.b = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        InterfaceC0067bi interfaceC0067bi;
        int i = 0;
        switch (this.a) {
            case 0:
                C0 c0 = (C0) this.c;
                MenuC0129di menuC0129di = c0.c;
                if (menuC0129di != null && (interfaceC0067bi = menuC0129di.e) != null) {
                    interfaceC0067bi.a(menuC0129di);
                }
                View view = (View) c0.h;
                if (view != null && view.getWindowToken() != null) {
                    C0758y0 c0758y0 = (C0758y0) this.b;
                    if (c0758y0.b()) {
                        c0.q = c0758y0;
                    } else if (c0758y0.e != null) {
                        c0758y0.d(0, 0, false, false);
                        c0.q = c0758y0;
                    }
                }
                c0.s = null;
                return;
            case 1:
                ((H0) this.b).a = this.c;
                return;
            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                ((Application) this.b).unregisterActivityLifecycleCallbacks((H0) this.c);
                return;
            case 3:
                try {
                    Method method = I0.d;
                    Object obj = this.c;
                    Object obj2 = this.b;
                    if (method != null) {
                        method.invoke(obj2, obj, Boolean.FALSE, "AppCompat recreation");
                    } else {
                        I0.e.invoke(obj2, obj, Boolean.FALSE);
                    }
                    return;
                } catch (RuntimeException e) {
                    if (e.getClass() == RuntimeException.class && e.getMessage() != null && e.getMessage().startsWith("Unable to stop")) {
                        throw e;
                    }
                    return;
                } catch (Throwable th) {
                    Log.e("ActivityRecreator", "Exception while invoking performStopActivity", th);
                    return;
                }
            case 4:
                X8 x8 = (X8) ((Hm) this.b).a;
                if (x8 != null) {
                    x8.L((Typeface) this.c);
                    return;
                }
                return;
            case BulletSpan.STANDARD_BULLET_RADIUS /* 5 */:
                break;
            case 6:
                ((C0399mc) this.b).a(this.c);
                return;
            default:
                Js js = ((SwipeDismissBehavior) this.c).a;
                if (js == null || !js.f()) {
                    return;
                }
                WeakHashMap weakHashMap = Hs.a;
                ((View) this.b).postOnAnimation(this);
                return;
        }
        while (true) {
            try {
                ((Runnable) this.b).run();
            } catch (Throwable th2) {
                AbstractC0046as.C(C0152eb.a, th2);
            }
            try {
                Runnable runnableH = ((Bg) this.c).h();
                if (runnableH == null) {
                    return;
                }
                this.b = runnableH;
                i++;
                if (i >= 16) {
                    Bg bg = (Bg) this.c;
                    W7 w7 = bg.b;
                    try {
                        if (w7.e(bg)) {
                            Bg bg2 = (Bg) this.c;
                            X8.W(bg2.b, bg2, this);
                            return;
                        }
                    } catch (Throwable th3) {
                        throw new Y9(th3, w7, bg);
                    }
                }
            } catch (Throwable th4) {
                Bg bg3 = (Bg) this.c;
                synchronized (bg3.e) {
                    Bg.f.decrementAndGet(bg3);
                    throw th4;
                }
            }
        }
    }

    public /* synthetic */ A0(Object obj, Object obj2, int i, boolean z) {
        this.a = i;
        this.b = obj;
        this.c = obj2;
    }

    public A0(SwipeDismissBehavior swipeDismissBehavior, View view, boolean z) {
        this.a = 7;
        this.c = swipeDismissBehavior;
        this.b = view;
    }
}
