package moe.shizuku.manager.legacy;

import android.os.Bundle;
import rikka.shizuku.AbstractActivityC0051b2;
import rikka.shizuku.An;

/* JADX INFO: loaded from: classes.dex */
public final class ShellRequestHandlerActivity extends AbstractActivityC0051b2 {
    @Override // rikka.shizuku.AbstractActivityC0051b2, rikka.shizuku.T6, rikka.shizuku.S6, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        An.g(this, getIntent());
        finish();
    }
}
