package rikka.shizuku;

import android.view.View;
import android.view.WindowInsets;

/* JADX INFO: renamed from: rikka.shizuku.xs, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0755xs {
    public static WindowInsets a(View view, WindowInsets windowInsets) {
        int i = Ks.a;
        return view.dispatchApplyWindowInsets(windowInsets);
    }

    public static WindowInsets b(View view, WindowInsets windowInsets) {
        return view.onApplyWindowInsets(windowInsets);
    }

    public static void c(View view) {
        view.requestApplyInsets();
    }
}
