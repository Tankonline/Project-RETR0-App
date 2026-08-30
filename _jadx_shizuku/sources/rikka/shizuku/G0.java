package rikka.shizuku;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Trace;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.google.android.material.carousel.CarouselLayoutManager;
import com.google.android.material.sidesheet.SideSheetBehavior;
import com.google.android.material.textfield.TextInputLayout;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.nio.MappedByteBuffer;
import java.util.Iterator;
import java.util.Locale;
import moe.shizuku.manager.starter.StarterActivity;
import rikka.html.text.style.BulletSpan;
import rikka.rish.RishHost;
import rikka.rish.RishTerminal;
import rikka.shizuku.server.ShizukuService;

/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class G0 implements Runnable {
    public final /* synthetic */ int a;
    public final /* synthetic */ Object b;

    public /* synthetic */ G0(int i, Object obj) {
        this.a = i;
        this.b = obj;
    }

    /* JADX WARN: Code duplicated, block: B:190:0x0339  */
    /* JADX WARN: Code duplicated, block: B:194:0x034d  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v0 */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v13 */
    /* JADX WARN: Type inference failed for: r4v15 */
    /* JADX WARN: Type inference failed for: r4v16 */
    /* JADX WARN: Type inference failed for: r4v17 */
    /* JADX WARN: Type inference failed for: r4v2 */
    /* JADX WARN: Type inference failed for: r4v3, types: [android.os.Handler] */
    /* JADX WARN: Type inference failed for: r4v4 */
    /* JADX WARN: Type inference failed for: r4v5, types: [android.os.Handler] */
    /* JADX WARN: Type inference failed for: r4v7 */
    /* JADX WARN: Type inference failed for: r6v2 */
    /* JADX WARN: Type inference failed for: r6v25 */
    /* JADX WARN: Type inference failed for: r6v3 */
    /* JADX WARN: Type inference failed for: r6v4, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r6v7 */
    /* JADX WARN: Type inference failed for: r7v0 */
    /* JADX WARN: Type inference failed for: r7v1 */
    /* JADX WARN: Type inference failed for: r7v15 */
    /* JADX WARN: Type inference failed for: r7v2, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r7v5 */
    @Override // java.lang.Runnable
    public final void run() {
        Object obj;
        ?? r7;
        ?? r6;
        ?? r4;
        C0342kh c0342kh;
        Context context;
        String[] strArr;
        Object systemService = null;
        int i = 2;
        boolean z = false;
        ?? r5 = 1;
        r5 = 1;
        switch (this.a) {
            case 0:
                Activity activity = (Activity) this.b;
                if (activity.isFinishing()) {
                    return;
                }
                int i2 = Build.VERSION.SDK_INT;
                if (i2 >= 28) {
                    Class cls = I0.a;
                    activity.recreate();
                    return;
                }
                Class cls2 = I0.a;
                Application application = 27;
                H0 h0 = 26;
                boolean z2 = i2 == 26 || i2 == 27;
                Method method = I0.f;
                if ((!z2 || method != null) && (I0.e != null || I0.d != null)) {
                    try {
                        Object obj2 = I0.c.get(activity);
                        if (obj2 != null && (obj = I0.b.get(activity)) != null) {
                            application = activity.getApplication();
                            h0 = new H0(activity);
                            application.registerActivityLifecycleCallbacks(h0);
                            Handler handler = I0.g;
                            handler.post(new A0(h0, obj2, 1 == true ? 1 : 0, z));
                            if (i2 != 26 && i2 != 27) {
                                r5 = 0;
                            }
                            try {
                                if (r5 != 0) {
                                    Handler handler2 = handler;
                                    try {
                                        Boolean bool = Boolean.FALSE;
                                        method.invoke(obj, obj2, null, null, 0, bool, null, null, bool, bool);
                                        r5 = handler2;
                                    } catch (Throwable th) {
                                        th = th;
                                        r6 = application;
                                        r7 = h0;
                                        r4 = handler2;
                                        r4.post(new A0(r6, r7, i, z));
                                        throw th;
                                    }
                                } else {
                                    r5 = handler;
                                    activity.recreate();
                                }
                                r5.post(new A0(application, h0, i, z));
                                return;
                            } catch (Throwable th2) {
                                th = th2;
                                r4 = r5;
                                r6 = application;
                                r7 = h0;
                            }
                        }
                    } catch (Throwable unused) {
                    }
                }
                activity.recreate();
                return;
            case 1:
                if (Build.VERSION.SDK_INT >= 33) {
                    Context context2 = (Context) this.b;
                    ComponentName componentName = new ComponentName(context2, "androidx.appcompat.app.AppLocalesMetadataHolderService");
                    if (context2.getPackageManager().getComponentEnabledSetting(componentName) != 1) {
                        if (AbstractC0454o5.a()) {
                            C0391m4 c0391m4 = AbstractC0667v2.g;
                            c0391m4.getClass();
                            C0176f4 c0176f4 = new C0176f4(c0391m4);
                            while (c0176f4.hasNext()) {
                                AbstractC0667v2 abstractC0667v2 = (AbstractC0667v2) ((WeakReference) c0176f4.next()).get();
                                if (abstractC0667v2 != null && (context = ((I2) abstractC0667v2).k) != null) {
                                    systemService = context.getSystemService("locale");
                                    if (systemService != null) {
                                        c0342kh = new C0342kh(new C0373lh(AbstractC0636u2.a(systemService)));
                                    } else {
                                        c0342kh = C0342kh.b;
                                    }
                                }
                            }
                            if (systemService != null) {
                                c0342kh = new C0342kh(new C0373lh(AbstractC0636u2.a(systemService)));
                            } else {
                                c0342kh = C0342kh.b;
                            }
                        } else {
                            c0342kh = AbstractC0667v2.c;
                            if (c0342kh == null) {
                                c0342kh = C0342kh.b;
                            }
                        }
                        if (c0342kh.a.a.isEmpty()) {
                            String strR = X8.R(context2);
                            Object systemService2 = context2.getSystemService("locale");
                            if (systemService2 != null) {
                                AbstractC0636u2.b(systemService2, AbstractC0605t2.a(strR));
                            }
                        }
                        context2.getPackageManager().setComponentEnabledSetting(componentName, 1, 1);
                    }
                }
                AbstractC0667v2.f = true;
                return;
            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                ((CarouselLayoutManager) this.b).o0();
                return;
            case 3:
                ((C0393m6) this.b).t(true);
                return;
            case 4:
                ((AbstractActivityC0051b2) this.b).invalidateOptionsMenu();
                return;
            case BulletSpan.STANDARD_BULLET_RADIUS /* 5 */:
                R6 r8 = (R6) this.b;
                Runnable runnable = r8.b;
                if (runnable != null) {
                    runnable.run();
                    r8.b = null;
                    return;
                }
                return;
            case 6:
                U6.a((U6) this.b);
                return;
            case 7:
                C0644ua c0644ua = (C0644ua) this.b;
                boolean zIsPopupShowing = c0644ua.h.isPopupShowing();
                c0644ua.t(zIsPopupShowing);
                c0644ua.m = zIsPopupShowing;
                return;
            case 8:
                C0306jc c0306jc = (C0306jc) this.b;
                synchronized (c0306jc.d) {
                    try {
                        if (c0306jc.h == null) {
                            return;
                        }
                        try {
                            C0615tc c0615tcB = c0306jc.b();
                            int i3 = c0615tcB.e;
                            if (i3 == 2) {
                                synchronized (c0306jc.d) {
                                }
                            }
                            if (i3 != 0) {
                                throw new RuntimeException("fetchFonts result is not OK. (" + i3 + ")");
                            }
                            try {
                                Method method2 = AbstractC0537qq.b;
                                Trace.beginSection("EmojiCompat.FontRequestEmojiCompatConfig.buildTypeface");
                                Ee ee = c0306jc.c;
                                Context context3 = c0306jc.a;
                                ee.getClass();
                                C0615tc[] c0615tcArr = {c0615tcB};
                                AbstractC0688vn abstractC0688vn = AbstractC0661ur.a;
                                AbstractC0750xn.d("TypefaceCompat.createFromFontInfo");
                                try {
                                    Typeface typefaceG = AbstractC0661ur.a.g(context3, c0615tcArr, 0);
                                    Trace.endSection();
                                    MappedByteBuffer mappedByteBufferN = AbstractC0750xn.n(c0306jc.a, c0615tcB.a);
                                    if (mappedByteBufferN == null || typefaceG == null) {
                                        throw new RuntimeException("Unable to open file.");
                                    }
                                    try {
                                        Trace.beginSection("EmojiCompat.MetadataRepo.create");
                                        Xn xn = new Xn(typefaceG, X8.P(mappedByteBufferN));
                                        Trace.endSection();
                                        Trace.endSection();
                                        synchronized (c0306jc.d) {
                                            try {
                                                AbstractC0599sr abstractC0599sr = c0306jc.h;
                                                if (abstractC0599sr != null) {
                                                    abstractC0599sr.C(xn);
                                                }
                                            } catch (Throwable th3) {
                                                throw th3;
                                            }
                                            break;
                                        }
                                        c0306jc.a();
                                        return;
                                    } catch (Throwable th4) {
                                        Method method3 = AbstractC0537qq.b;
                                        Trace.endSection();
                                        throw th4;
                                    }
                                } catch (Throwable th5) {
                                    Trace.endSection();
                                    throw th5;
                                }
                            } catch (Throwable th6) {
                                Method method4 = AbstractC0537qq.b;
                                Trace.endSection();
                                throw th6;
                            }
                            break;
                        } catch (Throwable th7) {
                            synchronized (c0306jc.d) {
                                try {
                                    AbstractC0599sr abstractC0599sr2 = c0306jc.h;
                                    if (abstractC0599sr2 != null) {
                                        abstractC0599sr2.B(th7);
                                    }
                                    c0306jc.a();
                                    return;
                                } catch (Throwable th8) {
                                    throw th8;
                                }
                            }
                        }
                    } catch (Throwable th9) {
                        throw th9;
                    }
                }
            case 9:
                Ac ac = (Ac) this.b;
                C0093cd c0093cd = ac.Q;
                Bundle bundle = ac.d;
                Am am = (Am) c0093cd.e.b;
                if (!am.e) {
                    am.a();
                }
                Bm bm = am.a;
                if (bm.e().c.a(EnumC0588sg.d)) {
                    throw new IllegalStateException(("performRestore cannot be called when owner is " + bm.e().c).toString());
                }
                if (am.g) {
                    throw new IllegalStateException("SavedStateRegistry was already restored.");
                }
                am.f = (bundle == null || !bundle.containsKey("androidx.lifecycle.BundlableSavedStateRegistry.key")) ? null : X8.F("androidx.lifecycle.BundlableSavedStateRegistry.key", bundle);
                am.g = true;
                ac.d = null;
                return;
            case 10:
                Iterator it = ((Qc) this.b).n.iterator();
                if (it.hasNext()) {
                    it.next().getClass();
                    throw new ClassCastException();
                }
                return;
            case 11:
                try {
                    ((C0610t7) this.b).f(AbstractC0744xh.p());
                    return;
                } catch (Zi unused2) {
                    return;
                }
            case 12:
                C0654uk c0654uk = (C0654uk) this.b;
                int i4 = c0654uk.b;
                C0805zg c0805zg = c0654uk.f;
                if (i4 == 0) {
                    c0654uk.c = true;
                    c0805zg.d(EnumC0557rg.ON_PAUSE);
                }
                if (c0654uk.a == 0 && c0654uk.c) {
                    c0805zg.d(EnumC0557rg.ON_STOP);
                    c0654uk.d = true;
                    return;
                }
                return;
            case 13:
                ((Qk) this.b).f();
                return;
            case 14:
                RishHost rishHost = (RishHost) this.b;
                rishHost.j = RishHost.waitFor(rishHost.h);
                return;
            case 15:
                RishTerminal.a((RishTerminal) this.b);
                throw null;
            case 16:
                ((Mn) this.b).a();
                return;
            case 17:
                ((T3) this.b).a();
                return;
            case 18:
                int i5 = ShizukuService.j;
                ShizukuService shizukuService = (ShizukuService) this.b;
                shizukuService.getClass();
                C0176f4 c0176f5 = new C0176f4(AbstractC0688vn.s());
                while (c0176f5.hasNext()) {
                    int iIntValue = ((Integer) c0176f5.next()).intValue();
                    try {
                        for (PackageInfo packageInfo : AbstractC0599sr.q(iIntValue, 4096L)) {
                            if (packageInfo != null && (strArr = packageInfo.requestedPermissions) != null && AbstractC0484p4.d0(strArr, "moe.shizuku.manager.permission.API_V23")) {
                                ShizukuService.s(shizukuService, packageInfo.packageName, iIntValue, true);
                            }
                        }
                    } catch (Throwable th10) {
                        Log.println(6, "Service", "exception when call getInstalledPackages\n" + Log.getStackTraceString(th10));
                    }
                }
                C0176f4 c0176f6 = new C0176f4(AbstractC0688vn.s());
                while (c0176f6.hasNext()) {
                    ShizukuService.s(shizukuService, "moe.shizuku.privileged.api", ((Integer) c0176f6.next()).intValue(), true);
                }
                return;
            case 19:
                C0423n5 c0423n5 = (C0423n5) this.b;
                c0423n5.c = false;
                SideSheetBehavior sideSheetBehavior = (SideSheetBehavior) c0423n5.e;
                Js js = sideSheetBehavior.i;
                if (js != null && js.f()) {
                    c0423n5.a(c0423n5.b);
                    return;
                } else {
                    if (sideSheetBehavior.h == 2) {
                        sideSheetBehavior.s(c0423n5.b);
                        return;
                    }
                    return;
                }
            case 20:
                StarterActivity starterActivity = (StarterActivity) this.b;
                if (starterActivity.isFinishing()) {
                    return;
                }
                starterActivity.finish();
                return;
            case 21:
                ((TextInputLayout) this.b).d.requestLayout();
                return;
            case 22:
                Xr xr = (Xr) this.b;
                if (xr.h) {
                    String str = xr.d;
                    Locale locale = Locale.ENGLISH;
                    Log.println(5, "UserServiceRecord", "Service record " + str + " is not started in 30000 ms");
                    xr.b();
                    return;
                }
                return;
            default:
                View view = (View) this.b;
                ((InputMethodManager) view.getContext().getSystemService(InputMethodManager.class)).showSoftInput(view, 1);
                return;
        }
    }
}
