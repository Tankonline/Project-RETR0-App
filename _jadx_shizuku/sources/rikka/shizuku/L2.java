package rikka.shizuku;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;

/* JADX INFO: loaded from: classes.dex */
public final class L2 {
    public static final PorterDuff.Mode b = PorterDuff.Mode.SRC_IN;
    public static L2 c;
    public Tl a;

    public static synchronized L2 a() {
        try {
            if (c == null) {
                d();
            }
        } catch (Throwable th) {
            throw th;
        }
        return c;
    }

    public static synchronized PorterDuffColorFilter c(int i, PorterDuff.Mode mode) {
        return Tl.e(i, mode);
    }

    public static synchronized void d() {
        if (c == null) {
            L2 l2 = new L2();
            c = l2;
            l2.a = Tl.b();
            Tl tl = c.a;
            K2 k2 = new K2();
            synchronized (tl) {
                tl.e = k2;
            }
        }
    }

    public static void e(Drawable drawable, Tp tp, int[] iArr) {
        PorterDuff.Mode mode = Tl.f;
        int[] state = drawable.getState();
        int[] iArr2 = AbstractC0304ja.a;
        if (drawable.mutate() == drawable) {
            if ((drawable instanceof LayerDrawable) && drawable.isStateful()) {
                drawable.setState(new int[0]);
                drawable.setState(state);
            }
            boolean z = tp.d;
            if (!z && !tp.c) {
                drawable.clearColorFilter();
                return;
            }
            PorterDuffColorFilter porterDuffColorFilterE = null;
            ColorStateList colorStateList = z ? tp.a : null;
            PorterDuff.Mode mode2 = tp.c ? tp.b : Tl.f;
            if (colorStateList != null && mode2 != null) {
                porterDuffColorFilterE = Tl.e(colorStateList.getColorForState(iArr, 0), mode2);
            }
            drawable.setColorFilter(porterDuffColorFilterE);
        }
    }

    public final synchronized Drawable b(Context context, int i) {
        return this.a.d(context, i);
    }
}
