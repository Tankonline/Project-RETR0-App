package rikka.shizuku;

import android.view.View;

/* JADX INFO: renamed from: rikka.shizuku.a3, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0021a3 {
    public static int a(View view) {
        return view.getTextAlignment();
    }

    public static int b(View view) {
        return view.getTextDirection();
    }

    public static void c(View view, int i) {
        view.setTextAlignment(i);
    }

    public static void d(View view, int i) {
        view.setTextDirection(i);
    }
}
