package defpackage;

import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import rikka.shizuku.shell.ShizukuShellLoader;

/* JADX INFO: loaded from: assets/rish_shizuku.dex */
public final class K extends Binder {
    @Override // android.os.Binder
    public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        if (i != 1) {
            return super.onTransact(i, parcel, parcel2, i2);
        }
        final IBinder strongBinder = parcel.readStrongBinder();
        final String string = parcel.readString();
        if (strongBinder != null) {
            ShizukuShellLoader.c.post(new Runnable() { // from class: J
                @Override // java.lang.Runnable
                public final void run() {
                    ShizukuShellLoader.a(strongBinder, string);
                }
            });
            return true;
        }
        System.err.println("Server is not running");
        System.err.flush();
        System.exit(1);
        return true;
    }
}
