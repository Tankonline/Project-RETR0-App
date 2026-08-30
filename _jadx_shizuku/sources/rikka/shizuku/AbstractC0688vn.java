package rikka.shizuku;

import android.content.Context;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.os.IUserManager;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import java.io.File;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import moe.shizuku.manager.ShizukuApplication;

/* JADX INFO: renamed from: rikka.shizuku.vn, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0688vn implements InterfaceC0170et {
    public static ShizukuApplication a;

    public AbstractC0688vn() {
        new ConcurrentHashMap();
    }

    public static final Spanned A(CharSequence charSequence, int i) {
        return AbstractC0599sr.m(i, charSequence.toString());
    }

    public static C0391m4 s() {
        List users;
        C0391m4 c0391m4 = new C0391m4();
        try {
            IUserManager iUserManagerA = AbstractC0134dn.b.a();
            if (Build.VERSION.SDK_INT >= 30) {
                users = iUserManagerA.getUsers(true, true, true);
            } else {
                try {
                    users = iUserManagerA.getUsers(true);
                } catch (NoSuchMethodError unused) {
                    users = iUserManagerA.getUsers(true, true, true);
                }
            }
            Iterator it = users.iterator();
            while (it.hasNext()) {
                c0391m4.add(Integer.valueOf(((UserInfo) it.next()).id));
            }
        } catch (Throwable unused2) {
            c0391m4.add(0);
        }
        return c0391m4;
    }

    public static void y(View view, CharSequence charSequence) {
        if (Build.VERSION.SDK_INT >= 26) {
            AbstractC0382lq.a(view, charSequence);
            return;
        }
        ViewOnLongClickListenerC0444nq viewOnLongClickListenerC0444nq = ViewOnLongClickListenerC0444nq.k;
        if (viewOnLongClickListenerC0444nq != null && viewOnLongClickListenerC0444nq.a == view) {
            ViewOnLongClickListenerC0444nq.b(null);
        }
        if (!TextUtils.isEmpty(charSequence)) {
            new ViewOnLongClickListenerC0444nq(view, charSequence);
            return;
        }
        ViewOnLongClickListenerC0444nq viewOnLongClickListenerC0444nq2 = ViewOnLongClickListenerC0444nq.l;
        if (viewOnLongClickListenerC0444nq2 != null && viewOnLongClickListenerC0444nq2.a == view) {
            viewOnLongClickListenerC0444nq2.a();
        }
        view.setOnLongClickListener(null);
        view.setLongClickable(false);
        view.setOnHoverListener(null);
    }

    public abstract void B(ViewGroup.MarginLayoutParams marginLayoutParams, int i, int i2);

    public abstract int d(ViewGroup.MarginLayoutParams marginLayoutParams);

    public abstract float e(int i);

    public abstract Typeface f(Context context, C0523qc c0523qc, Resources resources, int i);

    public abstract Typeface g(Context context, C0615tc[] c0615tcArr, int i);

    public Typeface h(Context context, List list, int i) {
        throw new IllegalStateException("createFromFontInfoWithFallback must only be called on API 29+");
    }

    public Typeface i(Context context, InputStream inputStream) {
        File fileK = AbstractC0750xn.k(context);
        if (fileK == null) {
            return null;
        }
        try {
            if (AbstractC0750xn.g(fileK, inputStream)) {
                return Typeface.createFromFile(fileK.getPath());
            }
            return null;
        } catch (RuntimeException unused) {
            return null;
        } finally {
            fileK.delete();
        }
    }

    public Typeface j(Context context, Resources resources, int i, String str, int i2) {
        File fileK = AbstractC0750xn.k(context);
        if (fileK == null) {
            return null;
        }
        try {
            if (AbstractC0750xn.f(fileK, resources, i)) {
                return Typeface.createFromFile(fileK.getPath());
            }
            return null;
        } catch (RuntimeException unused) {
            return null;
        } finally {
            fileK.delete();
        }
    }

    public C0615tc k(C0615tc[] c0615tcArr, int i) {
        int i2 = (i & 1) == 0 ? 400 : 700;
        boolean z = (i & 2) != 0;
        C0615tc c0615tc = null;
        int i3 = Integer.MAX_VALUE;
        for (C0615tc c0615tc2 : c0615tcArr) {
            int iAbs = (Math.abs(c0615tc2.c - i2) * 2) + (c0615tc2.d == z ? 0 : 1);
            if (c0615tc == null || i3 > iAbs) {
                c0615tc = c0615tc2;
                i3 = iAbs;
            }
        }
        return c0615tc;
    }

    public abstract int l();

    public abstract int m();

    public abstract int n();

    public abstract int o();

    public abstract int p(View view);

    public abstract int q(CoordinatorLayout coordinatorLayout);

    public abstract int r();

    public abstract boolean t(float f);

    public abstract boolean u(View view);

    public abstract boolean v(float f, float f2);

    public abstract void x(boolean z);

    public abstract boolean z(View view, float f);

    @Override // rikka.shizuku.InterfaceC0170et
    public void b() {
    }

    @Override // rikka.shizuku.InterfaceC0170et
    public void c() {
    }

    public void w(boolean z) {
    }
}
