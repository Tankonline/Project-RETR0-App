package rikka.shizuku;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;

/* JADX INFO: renamed from: rikka.shizuku.tk, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0623tk extends AbstractC0122db {
    final /* synthetic */ C0654uk this$0;

    public C0623tk(C0654uk c0654uk) {
        this.this$0 = c0654uk;
    }

    @Override // rikka.shizuku.AbstractC0122db, android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
        if (Build.VERSION.SDK_INT < 29) {
            int i = Jl.b;
            ((Jl) activity.getFragmentManager().findFragmentByTag("androidx.lifecycle.LifecycleDispatcher.report_fragment_tag")).a = this.this$0.h;
        }
    }

    @Override // rikka.shizuku.AbstractC0122db, android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
        C0654uk c0654uk = this.this$0;
        int i = c0654uk.b - 1;
        c0654uk.b = i;
        if (i == 0) {
            c0654uk.e.postDelayed(c0654uk.g, 700L);
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPreCreated(Activity activity, Bundle bundle) {
        Q3.e(activity, new C0592sk(this.this$0));
    }

    @Override // rikka.shizuku.AbstractC0122db, android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
        C0654uk c0654uk = this.this$0;
        int i = c0654uk.a - 1;
        c0654uk.a = i;
        if (i == 0 && c0654uk.c) {
            c0654uk.f.d(EnumC0557rg.ON_STOP);
            c0654uk.d = true;
        }
    }
}
