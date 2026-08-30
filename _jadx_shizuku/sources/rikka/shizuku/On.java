package rikka.shizuku;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.io.FileDescriptor;

/* JADX INFO: loaded from: classes.dex */
public final class On implements IBinder {
    public final IBinder a;

    public On(IBinder iBinder) {
        this.a = iBinder;
    }

    @Override // android.os.IBinder
    public final void dump(FileDescriptor fileDescriptor, String[] strArr) throws RemoteException {
        this.a.dump(fileDescriptor, strArr);
    }

    @Override // android.os.IBinder
    public final void dumpAsync(FileDescriptor fileDescriptor, String[] strArr) throws RemoteException {
        this.a.dumpAsync(fileDescriptor, strArr);
    }

    @Override // android.os.IBinder
    public final String getInterfaceDescriptor() {
        return this.a.getInterfaceDescriptor();
    }

    @Override // android.os.IBinder
    public final boolean isBinderAlive() {
        return this.a.isBinderAlive();
    }

    @Override // android.os.IBinder
    public final void linkToDeath(IBinder.DeathRecipient deathRecipient, int i) throws RemoteException {
        this.a.linkToDeath(deathRecipient, i);
    }

    @Override // android.os.IBinder
    public final boolean pingBinder() {
        return this.a.pingBinder();
    }

    @Override // android.os.IBinder
    public final IInterface queryLocalInterface(String str) {
        return null;
    }

    @Override // android.os.IBinder
    public final boolean transact(int i, Parcel parcel, Parcel parcel2, int i2) {
        boolean z = !Nn.i && Nn.f() >= 13;
        Parcel parcelObtain = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("moe.shizuku.server.IShizukuService");
            parcelObtain.writeStrongBinder(this.a);
            parcelObtain.writeInt(i);
            if (z) {
                parcelObtain.writeInt(i2);
            }
            parcelObtain.appendFrom(parcel, 0, parcel.dataSize());
            if (z) {
                try {
                    Nn.k().asBinder().transact(1, parcelObtain, parcel2, 0);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    Nn.k().asBinder().transact(1, parcelObtain, parcel2, i2);
                } catch (RemoteException e2) {
                    throw new RuntimeException(e2);
                }
            }
            parcelObtain.recycle();
            return true;
        } catch (Throwable th) {
            parcelObtain.recycle();
            throw th;
        }
    }

    @Override // android.os.IBinder
    public final boolean unlinkToDeath(IBinder.DeathRecipient deathRecipient, int i) {
        return this.a.unlinkToDeath(deathRecipient, i);
    }
}
