package moe.shizuku.manager.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import rikka.shizuku.An;

/* JADX INFO: loaded from: classes.dex */
public final class ShizukuReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        if ("rikka.shizuku.intent.action.REQUEST_BINDER".equals(intent.getAction())) {
            An.g(context, intent);
        }
    }
}
