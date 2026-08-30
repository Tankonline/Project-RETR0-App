package rikka.shizuku;

import android.graphics.drawable.Icon;
import android.os.Handler;
import android.os.Looper;
import android.view.DisplayCutout;
import android.view.ViewConfiguration;
import android.widget.TextView;

/* JADX INFO: loaded from: classes.dex */
public abstract class G9 {
    public static Handler a(Looper looper) {
        return Handler.createAsync(looper);
    }

    public static int b(Object obj) {
        return ((Icon) obj).getResId();
    }

    public static int c(DisplayCutout displayCutout) {
        return displayCutout.getSafeInsetBottom();
    }

    public static int d(DisplayCutout displayCutout) {
        return displayCutout.getSafeInsetLeft();
    }

    public static int e(DisplayCutout displayCutout) {
        return displayCutout.getSafeInsetRight();
    }

    public static int f(DisplayCutout displayCutout) {
        return displayCutout.getSafeInsetTop();
    }

    public static int g(ViewConfiguration viewConfiguration) {
        return viewConfiguration.getScaledHoverSlop();
    }

    public static void h(TextView textView, int i) {
        textView.setFirstBaselineToTopHeight(i);
    }

    public static boolean i(ViewConfiguration viewConfiguration) {
        return viewConfiguration.shouldShowMenuShortcutsWhenKeyboardPresent();
    }
}
