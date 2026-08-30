package rikka.shizuku;

import android.view.View;
import android.view.Window;

/* JADX INFO: loaded from: classes.dex */
public final class cu extends bu {
    @Override // rikka.shizuku.AbstractC0688vn
    public final void w(boolean z) {
        if (!z) {
            C(16);
            return;
        }
        Window window = this.b;
        window.clearFlags(134217728);
        window.addFlags(Integer.MIN_VALUE);
        View decorView = window.getDecorView();
        decorView.setSystemUiVisibility(16 | decorView.getSystemUiVisibility());
    }
}
