package defpackage;

import android.os.DeadObjectException;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ServiceManager;
import java.io.FileDescriptor;

/* JADX INFO: loaded from: assets/rish_shizuku.dex */
public final class M implements IBinder, IBinder.DeathRecipient {
    public IBinder a;
    public IInterface b;

    public final IBinder a() {
        IBinder iBinder = this.a;
        if (iBinder != null) {
            return iBinder;
        }
        IBinder service = ServiceManager.getService("package");
        if (service == null) {
            return null;
        }
        try {
            service.linkToDeath(this, 0);
        } catch (Throwable unused) {
        }
        this.a = service;
        return service;
    }

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied() {
        this.a.unlinkToDeath(this, 0);
        this.a = null;
        this.b = null;
    }

    @Override // android.os.IBinder
    public final void dump(FileDescriptor fileDescriptor, String[] strArr) throws RemoteException {
        IBinder iBinderA = a();
        if (iBinderA != null) {
            iBinderA.dump(fileDescriptor, strArr);
        }
    }

    @Override // android.os.IBinder
    public final void dumpAsync(FileDescriptor fileDescriptor, String[] strArr) throws RemoteException {
        IBinder iBinderA = a();
        if (iBinderA != null) {
            iBinderA.dumpAsync(fileDescriptor, strArr);
        }
    }

    @Override // android.os.IBinder
    public final String getInterfaceDescriptor() {
        IBinder iBinderA = a();
        if (iBinderA != null) {
            return iBinderA.getInterfaceDescriptor();
        }
        return null;
    }

    @Override // android.os.IBinder
    public final boolean isBinderAlive() {
        IBinder iBinderA = a();
        if (iBinderA != null) {
            return iBinderA.isBinderAlive();
        }
        return false;
    }

    @Override // android.os.IBinder
    public final void linkToDeath(IBinder.DeathRecipient deathRecipient, int i) throws RemoteException {
        IBinder iBinderA = a();
        if (iBinderA != null) {
            iBinderA.linkToDeath(deathRecipient, i);
        }
    }

    @Override // android.os.IBinder
    public final boolean pingBinder() {
        IBinder iBinderA = a();
        if (iBinderA != null) {
            return iBinderA.pingBinder();
        }
        return false;
    }

    @Override // android.os.IBinder
    public final IInterface queryLocalInterface(String str) {
        IBinder iBinderA = a();
        if (iBinderA != null) {
            return iBinderA.queryLocalInterface(str);
        }
        return null;
    }

    @Override // android.os.IBinder
    public final boolean transact(int i, Parcel parcel, Parcel parcel2, int i2) {
        IBinder iBinderA = a();
        if (iBinderA == null) {
            return false;
        }
        try {
            return iBinderA.transact(i, parcel, parcel2, i2);
        } catch (DeadObjectException unused) {
            this.a = null;
            if (a() != null) {
                return iBinderA.transact(i, parcel, parcel2, i2);
            }
            return false;
        }
    }

    @Override // android.os.IBinder
    public final boolean unlinkToDeath(IBinder.DeathRecipient deathRecipient, int i) {
        IBinder iBinderA = a();
        if (iBinderA != null) {
            return iBinderA.unlinkToDeath(deathRecipient, i);
        }
        return false;
    }
}
