package rikka.shizuku;

import android.view.ViewTreeObserver;

/* JADX INFO: loaded from: classes.dex */
public abstract class Z2 {
    public static void a(ViewTreeObserver viewTreeObserver, ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener) {
        viewTreeObserver.removeOnGlobalLayoutListener(onGlobalLayoutListener);
    }
}
