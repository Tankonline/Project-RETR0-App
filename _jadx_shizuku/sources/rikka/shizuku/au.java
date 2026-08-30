package rikka.shizuku;

import android.os.Build;
import android.view.View;
import android.view.WindowInsets;
import java.util.Objects;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public final class au {
    public static final au b;
    public final Wt a;

    static {
        int i = Build.VERSION.SDK_INT;
        if (i >= 34) {
            b = Vt.s;
        } else if (i >= 30) {
            b = Ut.r;
        } else {
            b = Wt.b;
        }
    }

    public au(WindowInsets windowInsets) {
        int i = Build.VERSION.SDK_INT;
        if (i >= 34) {
            this.a = new Vt(this, windowInsets);
            return;
        }
        if (i >= 30) {
            this.a = new Ut(this, windowInsets);
            return;
        }
        if (i >= 29) {
            this.a = new Tt(this, windowInsets);
        } else if (i >= 28) {
            this.a = new St(this, windowInsets);
        } else {
            this.a = new Rt(this, windowInsets);
        }
    }

    public static C0587sf c(C0587sf c0587sf, int i, int i2, int i3, int i4) {
        int iMax = Math.max(0, c0587sf.a - i);
        int iMax2 = Math.max(0, c0587sf.b - i2);
        int iMax3 = Math.max(0, c0587sf.c - i3);
        int iMax4 = Math.max(0, c0587sf.d - i4);
        return (iMax == i && iMax2 == i2 && iMax3 == i3 && iMax4 == i4) ? c0587sf : C0587sf.b(iMax, iMax2, iMax3, iMax4);
    }

    public static au e(View view, WindowInsets windowInsets) {
        windowInsets.getClass();
        au auVar = new au(windowInsets);
        if (view != null && view.isAttachedToWindow()) {
            WeakHashMap weakHashMap = Hs.a;
            au auVarA = As.a(view);
            Wt wt = auVar.a;
            wt.p(auVarA);
            wt.d(view.getRootView());
            wt.r(view.getWindowSystemUiVisibility());
        }
        return auVar;
    }

    public final int a() {
        return this.a.j().d;
    }

    public final int b() {
        return this.a.j().b;
    }

    public final WindowInsets d() {
        Wt wt = this.a;
        if (wt instanceof Qt) {
            return ((Qt) wt).c;
        }
        return null;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof au)) {
            return false;
        }
        return Objects.equals(this.a, ((au) obj).a);
    }

    public final int hashCode() {
        Wt wt = this.a;
        if (wt == null) {
            return 0;
        }
        return wt.hashCode();
    }

    public au() {
        this.a = new Wt(this);
    }
}
