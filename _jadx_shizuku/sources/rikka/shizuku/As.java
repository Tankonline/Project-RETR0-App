package rikka.shizuku;

import android.view.View;
import android.view.WindowInsets;

/* JADX INFO: loaded from: classes.dex */
public abstract class As {
    public static au a(View view) {
        WindowInsets rootWindowInsets = view.getRootWindowInsets();
        if (rootWindowInsets == null) {
            return null;
        }
        au auVarE = au.e(null, rootWindowInsets);
        Wt wt = auVarE.a;
        wt.p(auVarE);
        wt.d(view.getRootView());
        return auVarE;
    }

    public static void b(View view, int i, int i2) {
        view.setScrollIndicators(i, i2);
    }
}
