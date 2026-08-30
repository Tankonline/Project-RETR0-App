package rikka.shizuku;

import android.view.View;
import android.view.Window;

/* JADX INFO: loaded from: classes.dex */
public class bu extends AbstractC0688vn {
    public final Window b;

    public bu(Window window, Ge ge) {
        this.b = window;
    }

    public final void C(int i) {
        View decorView = this.b.getDecorView();
        decorView.setSystemUiVisibility((~i) & decorView.getSystemUiVisibility());
    }

    @Override // rikka.shizuku.AbstractC0688vn
    public final void x(boolean z) {
        if (!z) {
            C(8192);
            return;
        }
        Window window = this.b;
        window.clearFlags(67108864);
        window.addFlags(Integer.MIN_VALUE);
        View decorView = window.getDecorView();
        decorView.setSystemUiVisibility(8192 | decorView.getSystemUiVisibility());
    }
}
