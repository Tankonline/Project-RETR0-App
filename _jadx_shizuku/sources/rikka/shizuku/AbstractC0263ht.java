package rikka.shizuku;

import android.graphics.Rect;
import android.os.Build;
import android.view.View;

/* JADX INFO: renamed from: rikka.shizuku.ht, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0263ht {
    public static final C0478ot a;
    public static final K5 b;

    static {
        if (Build.VERSION.SDK_INT >= 29) {
            a = new C0509pt();
        } else {
            a = new C0478ot();
        }
        b = new K5(Float.class, "translationAlpha", 6);
        new K5(Rect.class, "clipBounds", 7);
    }

    public static void a(View view, int i, int i2, int i3, int i4) {
        a.v(view, i, i2, i3, i4);
    }

    public static void b(View view, int i) {
        a.q(view, i);
    }
}
