package rikka.shizuku;

import android.os.IInterface;
import android.os.RemoteCallbackList;
import android.util.Log;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public final class Yr extends RemoteCallbackList {
    public final /* synthetic */ Xr a;

    public Yr(Xr xr) {
        this.a = xr;
    }

    @Override // android.os.RemoteCallbackList
    public final void onCallbackDied(IInterface iInterface) {
        Xr xr = this.a;
        if (xr.g || getRegisteredCallbackCount() != 0) {
            return;
        }
        Locale locale = Locale.ENGLISH;
        Log.println(2, "UserServiceRecord", "Remove service record " + xr.d + " since it does not run as a daemon and all connections are gone");
        xr.b();
    }
}
