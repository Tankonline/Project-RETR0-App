package rikka.shizuku;

import android.transition.Transition;
import android.widget.PopupWindow;

/* JADX INFO: renamed from: rikka.shizuku.pi, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0498pi {
    public static void a(PopupWindow popupWindow, Transition transition) {
        popupWindow.setEnterTransition(transition);
    }

    public static void b(PopupWindow popupWindow, Transition transition) {
        popupWindow.setExitTransition(transition);
    }
}
