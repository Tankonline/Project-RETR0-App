package rikka.shizuku;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import moe.shizuku.api.BinderContainer;

/* JADX INFO: loaded from: classes.dex */
public final class Sn implements Mn {
    public final /* synthetic */ IBinder a;
    public final /* synthetic */ String b;
    public final /* synthetic */ C0562rl c;
    public final /* synthetic */ CountDownLatch d;

    public Sn(IBinder iBinder, String str, C0562rl c0562rl, CountDownLatch countDownLatch) {
        this.a = iBinder;
        this.b = str;
        this.c = c0562rl;
        this.d = countDownLatch;
    }

    @Override // rikka.shizuku.Mn
    public final void a() {
        C0562rl c0562rl = this.c;
        String str = this.b;
        try {
            try {
                Nn.k().a(this.a, AbstractC0046as.f(new Hj("shizuku:user-service-arg-token", str)));
                ((Bundle) c0562rl.a).putParcelable("moe.shizuku.privileged.api.intent.extra.BINDER", new BinderContainer(Nn.a));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } catch (Throwable th) {
            Log.e("ShizukuManager", String.format(Locale.ENGLISH, "attachUserService ".concat(str), new Object[0]), th);
            c0562rl.a = null;
        }
        Nn.j(this);
        this.d.countDown();
    }
}
