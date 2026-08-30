package rikka.shizuku;

import android.view.ViewGroup;

/* JADX INFO: loaded from: classes.dex */
public abstract class Ls {
    public static int a(ViewGroup viewGroup, int i) {
        return viewGroup.getChildDrawingOrder(i);
    }

    public static void b(ViewGroup viewGroup, boolean z) {
        viewGroup.suppressLayout(z);
    }
}
