package rikka.shizuku;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* JADX INFO: loaded from: classes.dex */
public final class E2 extends BroadcastReceiver {
    public final /* synthetic */ F2 a;

    public E2(F2 f2) {
        this.a = f2;
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        this.a.g();
    }
}
