package rikka.shizuku;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import java.util.concurrent.CountDownLatch;
import moe.shizuku.manager.adb.AdbPairingService;
import moe.shizuku.privileged.api.R;
import rikka.html.text.style.BulletSpan;

/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class V0 implements InterfaceC0313jj {
    public final /* synthetic */ int a;
    public final /* synthetic */ Object b;

    public /* synthetic */ V0(int i, Object obj) {
        this.a = i;
        this.b = obj;
    }

    /* JADX WARN: Type inference failed for: r2v7, types: [java.lang.Object, rikka.shizuku.jg] */
    @Override // rikka.shizuku.InterfaceC0313jj
    public final void b(Object obj) {
        Object obj2 = this.b;
        Integer num = (Integer) obj;
        switch (this.a) {
            case 0:
                num.intValue();
                ((C0050b1) obj2).o0.e(num);
                break;
            case 1:
                int iIntValue = num.intValue();
                int i = AdbPairingService.h;
                Log.i("AdbPairingService", "Pairing service port: " + iIntValue);
                if (iIntValue > 0) {
                    AdbPairingService adbPairingService = (AdbPairingService) obj2;
                    Notification.Builder smallIcon = V.c(adbPairingService).setColor(adbPairingService.getColor(R.color._0_resource_name_obfuscated_res_0x7f06056e)).setContentTitle(adbPairingService.getString(R.string._0_resource_name_obfuscated_res_0x7f1100f6)).setSmallIcon(R.drawable._0_resource_name_obfuscated_res_0x7f0800cc);
                    Notification.Action action = (Notification.Action) adbPairingService.e.getValue();
                    PendingIntent.getForegroundService(adbPairingService, 1, new Intent(adbPairingService, (Class<?>) AdbPairingService.class).setAction("reply").putExtra("paring_code", iIntValue), Build.VERSION.SDK_INT >= 31 ? 167772160 : 134217728);
                    ((NotificationManager) adbPairingService.getSystemService(NotificationManager.class)).notify(1, smallIcon.addAction(action).build());
                    break;
                }
                break;
            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                int iIntValue2 = num.intValue();
                if (iIntValue2 > 0) {
                    try {
                        U0 u0 = new U0(iIntValue2, "127.0.0.1", new C0235h1(new Ys(28, AbstractC0750xn.a)));
                        u0.a();
                        u0.c(Qo.b, null);
                        u0.close();
                        break;
                    } catch (Exception unused) {
                    }
                    ((CountDownLatch) obj2).countDown();
                    break;
                }
                break;
            default:
                num.intValue();
                ((Ts) obj2).d.e(num);
                break;
        }
    }
}
