package rikka.shizuku;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* JADX INFO: renamed from: rikka.shizuku.g5, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0208g5 extends AbstractC0443np implements InterfaceC0585sd {
    public final /* synthetic */ Context e;
    public final /* synthetic */ ContentResolver f;
    public final /* synthetic */ BroadcastReceiver.PendingResult g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0208g5(Context context, ContentResolver contentResolver, BroadcastReceiver.PendingResult pendingResult, G7 g7) {
        super(g7);
        this.e = context;
        this.f = contentResolver;
        this.g = pendingResult;
    }

    @Override // rikka.shizuku.InterfaceC0585sd
    public final Object f(Object obj, Object obj2) throws Throwable {
        C0208g5 c0208g5 = (C0208g5) i((InterfaceC0026a8) obj, (G7) obj2);
        Pe pe = Pe.b;
        c0208g5.j(pe);
        return pe;
    }

    @Override // rikka.shizuku.Q4
    public final G7 i(Object obj, G7 g7) {
        return new C0208g5(this.e, this.f, this.g, g7);
    }

    @Override // rikka.shizuku.Q4
    public final Object j(Object obj) throws Throwable {
        AbstractC0046as.V(obj);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        C0357l1 c0357l1 = new C0357l1(this.e, "_adb-tls-connect._tcp", new V0(2, countDownLatch));
        if (Settings.Global.getInt(this.f, "adb_wifi_enabled", 0) == 1) {
            c0357l1.a();
            countDownLatch.await(3L, TimeUnit.SECONDS);
            c0357l1.b();
        }
        this.g.finish();
        return Pe.b;
    }
}
