package rikka.shizuku;

import android.os.IBinder;
import android.util.Log;
import moe.shizuku.starter.ServiceStarter;

/* JADX INFO: renamed from: rikka.shizuku.bn, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class C0072bn implements IBinder.DeathRecipient {
    public final /* synthetic */ int a;

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied() {
        switch (this.a) {
            case 0:
                String str = ServiceStarter.a;
                Log.i("ShizukuServiceStarter", "exiting...");
                System.exit(0);
                break;
            default:
                Nn.j = false;
                Nn.g(null, null);
                break;
        }
    }
}
