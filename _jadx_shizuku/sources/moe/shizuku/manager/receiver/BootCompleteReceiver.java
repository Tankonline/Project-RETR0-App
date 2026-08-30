package moe.shizuku.manager.receiver;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import rikka.shizuku.AbstractC0090ca;
import rikka.shizuku.AbstractC0599sr;
import rikka.shizuku.AbstractC0744xh;
import rikka.shizuku.AbstractC0750xn;
import rikka.shizuku.C0208g5;
import rikka.shizuku.C0516q5;
import rikka.shizuku.C0550r9;
import rikka.shizuku.Cr;
import rikka.shizuku.D7;
import rikka.shizuku.ExecutorC0243h9;
import rikka.shizuku.Fe;
import rikka.shizuku.Gf;
import rikka.shizuku.Gn;
import rikka.shizuku.H6;
import rikka.shizuku.InterfaceC0781yn;
import rikka.shizuku.N3;
import rikka.shizuku.Nn;
import rikka.shizuku.Qo;
import rikka.shizuku.Re;
import rikka.shizuku.Rj;
import rikka.shizuku.Sj;
import rikka.shizuku.Tr;
import rikka.shizuku.V7;
import rikka.shizuku.Zi;

/* JADX INFO: loaded from: classes.dex */
public final class BootCompleteReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        int i = 0;
        if (("android.intent.action.LOCKED_BOOT_COMPLETED".equals(intent.getAction()) || "android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) && Tr.a <= 0 && !Nn.h()) {
            if (AbstractC0750xn.a.getInt("mode", -1) != 0) {
                if (Build.VERSION.SDK_INT < 33 || context.checkSelfPermission("android.permission.WRITE_SECURE_SETTINGS") != 0 || AbstractC0750xn.a.getInt("mode", -1) != 1) {
                    Log.w("ShizukuManager", "No support start on boot");
                    return;
                }
                ContentResolver contentResolver = context.getContentResolver();
                Settings.Global.putInt(contentResolver, "adb_wifi_enabled", 1);
                Settings.Global.putInt(contentResolver, "adb_enabled", 1);
                Settings.Global.putLong(contentResolver, "adb_allowed_connection_time", 0L);
                BroadcastReceiver.PendingResult pendingResultGoAsync = goAsync();
                C0550r9 c0550r9 = AbstractC0090ca.a;
                V7 v7P = ExecutorC0243h9.b;
                if (v7P.g(Fe.a) == null) {
                    v7P = AbstractC0744xh.P(v7P, new Gf());
                }
                AbstractC0599sr.z(new D7(v7P), new C0208g5(context, contentResolver, pendingResultGoAsync, null), 3);
                return;
            }
            ExecutorService executorService = Gn.j;
            if (AbstractC0744xh.p().a < 1) {
                Gn gnQ = AbstractC0744xh.q();
                if (gnQ != null) {
                    gnQ.close();
                    return;
                }
                return;
            }
            String[] strArr = {Qo.b};
            Sj sj = new Sj();
            sj.d.add(new H6(strArr));
            sj.i = new Rj(sj, 0);
            sj.h = new Re();
            sj.g = null;
            try {
                try {
                    AbstractC0744xh.p().b(sj);
                } catch (IOException unused) {
                }
            } catch (Zi unused2) {
                ArrayList arrayList = sj.d;
                int size = arrayList.size();
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    ((H6) obj).getClass();
                }
                C0516q5 c0516q5 = new C0516q5(1);
                InterfaceC0781yn interfaceC0781yn = sj.h;
                if (interfaceC0781yn != null) {
                    Cr cr = sj.g;
                    if (cr == null) {
                        interfaceC0781yn.a(c0516q5);
                    } else {
                        cr.execute(new N3(sj, 2, c0516q5));
                    }
                }
            }
        }
    }
}
