package rikka.shizuku;

import android.os.IBinder;
import android.os.Parcel;
import java.util.Locale;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public final class Xr {
    public static final C0589sh j = new C0589sh(0, "UserServiceRecord");
    public G0 a;
    public final int c;
    public IBinder e;
    public boolean g;
    public boolean h;
    public final /* synthetic */ Xn i;
    public final Yr f = new Yr(this);
    public final String d = UUID.randomUUID().toString() + "-" + System.currentTimeMillis();
    public final El b = new El(1, this);

    public Xr(Xn xn, int i, boolean z) {
        this.i = xn;
        this.c = i;
        this.g = z;
    }

    public final void a() {
        C0589sh c0589sh = j;
        String str = this.d;
        Locale locale = Locale.ENGLISH;
        c0589sh.b(2, "Broadcast binder received for service record " + str);
        Yr yr = this.f;
        int iBeginBroadcast = yr.beginBroadcast();
        for (int i = 0; i < iBeginBroadcast; i++) {
            try {
                C0064bf c0064bf = (C0064bf) yr.getBroadcastItem(i);
                IBinder iBinder = this.e;
                c0064bf.getClass();
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("moe.shizuku.server.IShizukuServiceConnection");
                    parcelObtain.writeStrongBinder(iBinder);
                    c0064bf.a.transact(1, parcelObtain, null, 1);
                    parcelObtain.recycle();
                } catch (Throwable th) {
                    parcelObtain.recycle();
                    throw th;
                }
            } catch (Throwable unused) {
                Locale locale2 = Locale.ENGLISH;
                c0589sh.b(5, "Failed to call connected " + str);
            }
        }
        yr.finishBroadcast();
    }

    public final void b() {
        synchronized (this.i) {
            this.i.u(this);
        }
    }

    public final void c() {
        boolean z = this.h;
        String str = this.d;
        C0589sh c0589sh = j;
        if (z) {
            Locale locale = Locale.ENGLISH;
            c0589sh.b(5, "Service record " + str + " is already starting");
            return;
        }
        Locale locale2 = Locale.ENGLISH;
        c0589sh.b(2, "Set starting timeout for service record " + str + ": 30000");
        this.h = true;
        G0 g0 = new G0(22, this);
        this.a = g0;
        X8.r.postDelayed(g0, 30000L);
    }
}
