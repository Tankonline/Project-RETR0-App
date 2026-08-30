package rikka.shizuku;

import android.app.Activity;

/* JADX INFO: renamed from: rikka.shizuku.sk, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0592sk extends AbstractC0122db {
    final /* synthetic */ C0654uk this$0;

    public C0592sk(C0654uk c0654uk) {
        this.this$0 = c0654uk;
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPostResumed(Activity activity) {
        this.this$0.a();
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPostStarted(Activity activity) {
        C0654uk c0654uk = this.this$0;
        int i = c0654uk.a + 1;
        c0654uk.a = i;
        if (i == 1 && c0654uk.d) {
            c0654uk.f.d(EnumC0557rg.ON_START);
            c0654uk.d = false;
        }
    }
}
