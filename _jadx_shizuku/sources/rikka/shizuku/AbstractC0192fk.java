package rikka.shizuku;

import android.view.Window;
import android.view.WindowInsets;

/* JADX INFO: renamed from: rikka.shizuku.fk, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0192fk {
    public static void a(Window window) {
        window.getDecorView().getWindowInsetsController().show(WindowInsets.Type.ime());
    }
}
