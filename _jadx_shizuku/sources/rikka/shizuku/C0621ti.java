package rikka.shizuku;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.PopupWindow;
import java.lang.reflect.Method;

/* JADX INFO: renamed from: rikka.shizuku.ti, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0621ti extends Zg {
    public static final Method D;
    public Ys C;

    static {
        try {
            if (Build.VERSION.SDK_INT <= 28) {
                D = PopupWindow.class.getDeclaredMethod("setTouchModal", Boolean.TYPE);
            }
        } catch (NoSuchMethodException unused) {
            Log.i("MenuPopupWindow", "Could not find method setTouchModal() on PopupWindow. Oh well.");
        }
    }

    @Override // rikka.shizuku.Zg
    public final C0490pa o(Context context, boolean z) {
        C0590si c0590si = new C0590si(context, z);
        c0590si.o = this;
        return c0590si;
    }
}
