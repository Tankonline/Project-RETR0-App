package rikka.shizuku;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import java.lang.ref.WeakReference;

/* JADX INFO: renamed from: rikka.shizuku.v2, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0667v2 {
    public static final O3 a = new O3(new P3());
    public static int b = -100;
    public static C0342kh c = null;
    public static C0342kh d = null;
    public static Boolean e = null;
    public static boolean f = false;
    public static final C0391m4 g = new C0391m4();
    public static final Object h = new Object();
    public static final Object i = new Object();

    public static boolean e(Context context) {
        if (e == null) {
            try {
                int i2 = M3.a;
                Bundle bundle = context.getPackageManager().getServiceInfo(new ComponentName(context, (Class<?>) M3.class), L3.a() | 128).metaData;
                if (bundle != null) {
                    e = Boolean.valueOf(bundle.getBoolean("autoStoreLocales"));
                }
            } catch (PackageManager.NameNotFoundException unused) {
                e = Boolean.FALSE;
            }
        }
        return e.booleanValue();
    }

    public static void h(I2 i2) {
        synchronized (h) {
            try {
                C0391m4 c0391m4 = g;
                c0391m4.getClass();
                C0176f4 c0176f4 = new C0176f4(c0391m4);
                while (c0176f4.hasNext()) {
                    AbstractC0667v2 abstractC0667v2 = (AbstractC0667v2) ((WeakReference) c0176f4.next()).get();
                    if (abstractC0667v2 == i2 || abstractC0667v2 == null) {
                        c0176f4.remove();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static void m(int i2) {
        if ((i2 == -1 || i2 == 0 || i2 == 1 || i2 == 2 || i2 == 3) && b != i2) {
            b = i2;
            synchronized (h) {
                try {
                    C0391m4 c0391m4 = g;
                    c0391m4.getClass();
                    C0176f4 c0176f4 = new C0176f4(c0391m4);
                    while (c0176f4.hasNext()) {
                        AbstractC0667v2 abstractC0667v2 = (AbstractC0667v2) ((WeakReference) c0176f4.next()).get();
                        if (abstractC0667v2 != null) {
                            ((I2) abstractC0667v2).p(true, true);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public abstract View b(String str, Context context, AttributeSet attributeSet);

    public abstract void c();

    public abstract void d();

    public abstract void f();

    public abstract void g();

    public abstract boolean i(int i2);

    public abstract void j(int i2);

    public abstract void k(View view);

    public abstract void l(View view, ViewGroup.LayoutParams layoutParams);

    public abstract void o(CharSequence charSequence);

    public View onCreateView(String str, Context context, AttributeSet attributeSet) {
        return b(str, context, attributeSet);
    }
}
