package rikka.shizuku;

import android.util.Log;
import rikka.shizuku.server.ShizukuService;

/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class Tn implements X1 {
    @Override // rikka.shizuku.X1
    public final void a() {
        int i = ShizukuService.j;
        if (AbstractC0599sr.n(0, "moe.shizuku.privileged.api") == null) {
            Log.println(5, "Service", "manager app is uninstalled in user 0, exiting...");
            System.exit(50);
        }
    }
}
