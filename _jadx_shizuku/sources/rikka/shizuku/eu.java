package rikka.shizuku;

import android.view.View;
import android.view.Window;
import android.view.WindowInsetsController;

/* JADX INFO: loaded from: classes.dex */
public class eu extends AbstractC0688vn {
    public final WindowInsetsController b;
    public Window c;

    public eu(WindowInsetsController windowInsetsController, Ge ge) {
        this.b = windowInsetsController;
    }

    @Override // rikka.shizuku.AbstractC0688vn
    public final void w(boolean z) {
        Window window = this.c;
        if (z) {
            if (window != null) {
                View decorView = window.getDecorView();
                decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() | 16);
            }
            this.b.setSystemBarsAppearance(16, 16);
            return;
        }
        if (window != null) {
            View decorView2 = window.getDecorView();
            decorView2.setSystemUiVisibility(decorView2.getSystemUiVisibility() & (-17));
        }
        this.b.setSystemBarsAppearance(0, 16);
    }

    @Override // rikka.shizuku.AbstractC0688vn
    public final void x(boolean z) {
        Window window = this.c;
        if (z) {
            if (window != null) {
                View decorView = window.getDecorView();
                decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() | 8192);
            }
            this.b.setSystemBarsAppearance(8, 8);
            return;
        }
        if (window != null) {
            View decorView2 = window.getDecorView();
            decorView2.setSystemUiVisibility(decorView2.getSystemUiVisibility() & (-8193));
        }
        this.b.setSystemBarsAppearance(0, 8);
    }
}
