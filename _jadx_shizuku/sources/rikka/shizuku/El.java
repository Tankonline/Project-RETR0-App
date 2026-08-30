package rikka.shizuku;

import android.os.IBinder;
import android.util.Log;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class El implements IBinder.DeathRecipient {
    public final /* synthetic */ int a;
    public final /* synthetic */ Object b;

    public /* synthetic */ El(int i, Object obj) {
        this.a = i;
        this.b = obj;
    }

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied() {
        boolean z;
        switch (this.a) {
            case 0:
                Fl fl = (Fl) this.b;
                fl.getClass();
                Process process = fl.a;
                C0589sh c0589sh = Fl.d;
                try {
                    try {
                        process.exitValue();
                        z = false;
                    } catch (IllegalThreadStateException unused) {
                        z = true;
                    }
                    if (z) {
                        process.destroy();
                        c0589sh.b(4, "destroy process because the owner is dead");
                    }
                } catch (Throwable th) {
                    c0589sh.c(th, "failed to destroy process", new Object[0]);
                    return;
                }
                break;
            default:
                Xr xr = (Xr) this.b;
                String str = xr.d;
                Locale locale = Locale.ENGLISH;
                Log.println(2, "UserServiceRecord", "Binder for service record " + str + " is dead");
                xr.b();
                break;
        }
    }
}
