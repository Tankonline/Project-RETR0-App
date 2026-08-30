package rikka.shizuku;

import android.app.ActivityManagerNative;
import android.app.IActivityManager;
import android.content.pm.IPackageManager;
import android.os.Build;
import android.os.DeadObjectException;
import android.os.IBinder;
import android.os.IDeviceIdleController;
import android.os.IInterface;
import android.os.IUserManager;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.permission.IPermissionManager;
import java.io.FileDescriptor;

/* JADX INFO: renamed from: rikka.shizuku.sp, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0597sp implements IBinder, IBinder.DeathRecipient {
    public static C0579s7 e;
    public final String a;
    public final C0579s7 b;
    public IBinder c;
    public IInterface d;

    public C0597sp(String str, C0579s7 c0579s7) {
        this.a = str;
        this.b = c0579s7;
    }

    public final IInterface a() {
        IDeviceIdleController iDeviceIdleControllerAsInterface;
        IInterface iInterface = this.d;
        if (iInterface != null) {
            return iInterface;
        }
        IBinder iBinderB = b();
        if (iBinderB == null) {
            return null;
        }
        switch (this.b.a) {
            case 12:
                iDeviceIdleControllerAsInterface = Build.VERSION.SDK_INT < 26 ? ActivityManagerNative.asInterface(iBinderB) : IActivityManager.Stub.asInterface(iBinderB);
                break;
            case 13:
                iDeviceIdleControllerAsInterface = IUserManager.Stub.asInterface(iBinderB);
                break;
            case 14:
                iDeviceIdleControllerAsInterface = IPackageManager.Stub.asInterface(iBinderB);
                break;
            case 15:
                iDeviceIdleControllerAsInterface = IPermissionManager.Stub.asInterface(iBinderB);
                break;
            default:
                iDeviceIdleControllerAsInterface = IDeviceIdleController.Stub.asInterface(iBinderB);
                break;
        }
        IInterface iInterface2 = (IInterface) iDeviceIdleControllerAsInterface;
        this.d = iInterface2;
        return iInterface2;
    }

    public final IBinder b() {
        IBinder iBinder = this.c;
        if (iBinder != null) {
            return iBinder;
        }
        IBinder service = ServiceManager.getService(this.a);
        if (service == null) {
            return null;
        }
        if (e != null) {
            service = new On(service);
        }
        try {
            service.linkToDeath(this, 0);
        } catch (Throwable unused) {
        }
        this.c = service;
        return service;
    }

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied() {
        this.c.unlinkToDeath(this, 0);
        this.c = null;
        this.d = null;
    }

    @Override // android.os.IBinder
    public final void dump(FileDescriptor fileDescriptor, String[] strArr) throws RemoteException {
        IBinder iBinderB = b();
        if (iBinderB != null) {
            iBinderB.dump(fileDescriptor, strArr);
        }
    }

    @Override // android.os.IBinder
    public final void dumpAsync(FileDescriptor fileDescriptor, String[] strArr) throws RemoteException {
        IBinder iBinderB = b();
        if (iBinderB != null) {
            iBinderB.dumpAsync(fileDescriptor, strArr);
        }
    }

    @Override // android.os.IBinder
    public final String getInterfaceDescriptor() {
        IBinder iBinderB = b();
        if (iBinderB != null) {
            return iBinderB.getInterfaceDescriptor();
        }
        return null;
    }

    @Override // android.os.IBinder
    public final boolean isBinderAlive() {
        IBinder iBinderB = b();
        if (iBinderB != null) {
            return iBinderB.isBinderAlive();
        }
        return false;
    }

    @Override // android.os.IBinder
    public final void linkToDeath(IBinder.DeathRecipient deathRecipient, int i) throws RemoteException {
        IBinder iBinderB = b();
        if (iBinderB != null) {
            iBinderB.linkToDeath(deathRecipient, i);
        }
    }

    @Override // android.os.IBinder
    public final boolean pingBinder() {
        IBinder iBinderB = b();
        if (iBinderB != null) {
            return iBinderB.pingBinder();
        }
        return false;
    }

    @Override // android.os.IBinder
    public final IInterface queryLocalInterface(String str) {
        IBinder iBinderB = b();
        if (iBinderB != null) {
            return iBinderB.queryLocalInterface(str);
        }
        return null;
    }

    @Override // android.os.IBinder
    public final boolean transact(int i, Parcel parcel, Parcel parcel2, int i2) {
        IBinder iBinderB = b();
        if (iBinderB == null) {
            return false;
        }
        try {
            return iBinderB.transact(i, parcel, parcel2, i2);
        } catch (DeadObjectException unused) {
            this.c = null;
            if (b() != null) {
                return iBinderB.transact(i, parcel, parcel2, i2);
            }
            return false;
        }
    }

    @Override // android.os.IBinder
    public final boolean unlinkToDeath(IBinder.DeathRecipient deathRecipient, int i) {
        IBinder iBinderB = b();
        if (iBinderB != null) {
            return iBinderB.unlinkToDeath(deathRecipient, i);
        }
        return false;
    }
}
