package rikka.shizuku;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;
import rikka.html.text.style.BulletSpan;

/* JADX INFO: loaded from: classes.dex */
public final class Fl extends Binder implements IInterface {
    public static final C0589sh d = new C0589sh(0, "RemoteProcessHolder");
    public final Process a;
    public ParcelFileDescriptor b;
    public ParcelFileDescriptor c;

    public Fl(Process process, IBinder iBinder) {
        attachInterface(this, "moe.shizuku.server.IRemoteProcess");
        this.a = process;
        if (iBinder != null) {
            try {
                iBinder.linkToDeath(new El(0, this), 0);
            } catch (Throwable th) {
                d.c(th, "linkToDeath", new Object[0]);
            }
        }
    }

    @Override // android.os.Binder
    public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        Parcel parcel3;
        if (i < 1 || i > 16777215) {
            parcel3 = parcel;
        } else {
            parcel3 = parcel;
            parcel3.enforceInterface("moe.shizuku.server.IRemoteProcess");
        }
        if (i == 1598968902) {
            parcel2.writeString("moe.shizuku.server.IRemoteProcess");
            return true;
        }
        int i3 = 0;
        switch (i) {
            case 1:
                if (this.c == null) {
                    try {
                        OutputStream outputStream = this.a.getOutputStream();
                        ParcelFileDescriptor[] parcelFileDescriptorArrCreatePipe = ParcelFileDescriptor.createPipe();
                        ParcelFileDescriptor parcelFileDescriptor = parcelFileDescriptorArrCreatePipe[0];
                        ParcelFileDescriptor parcelFileDescriptor2 = parcelFileDescriptorArrCreatePipe[1];
                        new Jj(new ParcelFileDescriptor.AutoCloseInputStream(parcelFileDescriptor), outputStream).start();
                        this.c = parcelFileDescriptor2;
                    } catch (IOException e) {
                        throw new IllegalStateException(e);
                    }
                    break;
                }
                ParcelFileDescriptor parcelFileDescriptor3 = this.c;
                parcel2.writeNoException();
                if (parcelFileDescriptor3 == null) {
                    parcel2.writeInt(0);
                    return true;
                }
                parcel2.writeInt(1);
                parcelFileDescriptor3.writeToParcel(parcel2, 1);
                return true;
            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                if (this.b == null) {
                    try {
                        InputStream inputStream = this.a.getInputStream();
                        ParcelFileDescriptor[] parcelFileDescriptorArrCreatePipe2 = ParcelFileDescriptor.createPipe();
                        ParcelFileDescriptor parcelFileDescriptor4 = parcelFileDescriptorArrCreatePipe2[0];
                        new Jj(inputStream, new ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescriptorArrCreatePipe2[1])).start();
                        this.b = parcelFileDescriptor4;
                    } catch (IOException e2) {
                        throw new IllegalStateException(e2);
                    }
                    break;
                }
                ParcelFileDescriptor parcelFileDescriptor5 = this.b;
                parcel2.writeNoException();
                if (parcelFileDescriptor5 == null) {
                    parcel2.writeInt(0);
                    return true;
                }
                parcel2.writeInt(1);
                parcelFileDescriptor5.writeToParcel(parcel2, 1);
                return true;
            case 3:
                try {
                    InputStream errorStream = this.a.getErrorStream();
                    ParcelFileDescriptor[] parcelFileDescriptorArrCreatePipe3 = ParcelFileDescriptor.createPipe();
                    ParcelFileDescriptor parcelFileDescriptor6 = parcelFileDescriptorArrCreatePipe3[0];
                    new Jj(errorStream, new ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescriptorArrCreatePipe3[1])).start();
                    parcel2.writeNoException();
                    if (parcelFileDescriptor6 == null) {
                        parcel2.writeInt(0);
                        return true;
                    }
                    parcel2.writeInt(1);
                    parcelFileDescriptor6.writeToParcel(parcel2, 1);
                    return true;
                } catch (IOException e3) {
                    throw new IllegalStateException(e3);
                }
            case 4:
                try {
                    int iWaitFor = this.a.waitFor();
                    parcel2.writeNoException();
                    parcel2.writeInt(iWaitFor);
                    return true;
                } catch (InterruptedException e4) {
                    throw new IllegalStateException(e4);
                }
            case BulletSpan.STANDARD_BULLET_RADIUS /* 5 */:
                int iExitValue = this.a.exitValue();
                parcel2.writeNoException();
                parcel2.writeInt(iExitValue);
                return true;
            case 6:
                this.a.destroy();
                parcel2.writeNoException();
                return true;
            case 7:
                try {
                    this.a.exitValue();
                    break;
                } catch (IllegalThreadStateException unused) {
                    i3 = 1;
                }
                parcel2.writeNoException();
                parcel2.writeInt(i3);
                return true;
            case 8:
                long j = parcel3.readLong();
                TimeUnit timeUnitValueOf = TimeUnit.valueOf(parcel3.readString());
                long jNanoTime = System.nanoTime();
                long nanos = timeUnitValueOf.toNanos(j);
                do {
                    try {
                        this.a.exitValue();
                        i3 = 1;
                    } catch (IllegalThreadStateException unused2) {
                        if (nanos > 0) {
                            try {
                                Thread.sleep(Math.min(TimeUnit.NANOSECONDS.toMillis(nanos) + 1, 100L));
                            } catch (InterruptedException unused3) {
                                throw new IllegalStateException();
                            }
                        }
                        nanos = timeUnitValueOf.toNanos(j) - (System.nanoTime() - jNanoTime);
                        break;
                    }
                    parcel2.writeNoException();
                    parcel2.writeInt(i3);
                    return true;
                } while (nanos > 0);
                parcel2.writeNoException();
                parcel2.writeInt(i3);
                return true;
            default:
                return super.onTransact(i, parcel, parcel2, i2);
        }
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this;
    }
}
