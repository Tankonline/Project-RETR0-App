package rikka.shizuku;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Insets;
import android.os.Trace;

/* JADX INFO: loaded from: classes.dex */
public abstract class Q3 {
    public static String a(Context context) {
        return context.getOpPackageName();
    }

    public static boolean b() {
        return Trace.isEnabled();
    }

    public static Insets c(int i, int i2, int i3, int i4) {
        return Insets.of(i, i2, i3, i4);
    }

    public static void d(Resources.Theme theme) {
        theme.rebase();
    }

    public static final void e(Activity activity, C0592sk c0592sk) {
        activity.registerActivityLifecycleCallbacks(c0592sk);
    }
}
