package rikka.shizuku;

import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Parcel;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class Kn extends Binder implements Ye {
    @Override // rikka.shizuku.Ye
    public final void g(final int i, Bundle bundle) {
        int i2 = 0;
        final int i3 = bundle.getBoolean("shizuku:request-permission-reply-allowed", false) ? 0 : -1;
        synchronized (Nn.m) {
            try {
                ArrayList arrayList = Nn.o;
                int size = arrayList.size();
                while (i2 < size) {
                    Object obj = arrayList.get(i2);
                    i2++;
                    final Ln ln = (Ln) obj;
                    Handler handler = ln.b;
                    if (handler != null) {
                        final int i4 = 0;
                        handler.post(new Runnable(ln, i, i3, i4) { // from class: rikka.shizuku.Jn
                            public final /* synthetic */ int a;
                            public final /* synthetic */ Ln b;
                            public final /* synthetic */ int c;

                            {
                                this.a = i4;
                                this.c = i3;
                            }

                            @Override // java.lang.Runnable
                            public final void run() {
                                switch (this.a) {
                                    case 0:
                                        ((C0719wn) this.b.a).a(this.c);
                                        break;
                                    default:
                                        ((C0719wn) this.b.a).a(this.c);
                                        break;
                                }
                            }
                        });
                    } else if (Looper.myLooper() == Looper.getMainLooper()) {
                        ((C0719wn) ln.a).a(i3);
                    } else {
                        final int i5 = 1;
                        Nn.p.post(new Runnable(ln, i, i3, i5) { // from class: rikka.shizuku.Jn
                            public final /* synthetic */ int a;
                            public final /* synthetic */ Ln b;
                            public final /* synthetic */ int c;

                            {
                                this.a = i5;
                                this.c = i3;
                            }

                            @Override // java.lang.Runnable
                            public final void run() {
                                switch (this.a) {
                                    case 0:
                                        ((C0719wn) this.b.a).a(this.c);
                                        break;
                                    default:
                                        ((C0719wn) this.b.a).a(this.c);
                                        break;
                                }
                            }
                        });
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // rikka.shizuku.Ye
    public final void l(Bundle bundle) {
        Nn.c = bundle.getInt("shizuku:attach-reply-uid", -1);
        Nn.d = bundle.getInt("shizuku:attach-reply-version", -1);
        Nn.e = bundle.getInt("shizuku:attach-reply-patch-version", -1);
        Nn.f = bundle.getString("shizuku:attach-reply-secontext");
        Nn.g = bundle.getBoolean("shizuku:attach-reply-permission-granted", false);
        Nn.h = bundle.getBoolean("shizuku:attach-reply-should-show-request-permission-rationale", false);
        Nn.l();
    }

    @Override // android.os.Binder
    public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        if (i >= 1 && i <= 16777215) {
            parcel.enforceInterface("moe.shizuku.server.IShizukuApplication");
        }
        if (i == 1598968902) {
            parcel2.writeString("moe.shizuku.server.IShizukuApplication");
            return true;
        }
        if (i == 2) {
            l((Bundle) (parcel.readInt() != 0 ? Bundle.CREATOR.createFromParcel(parcel) : null));
            return true;
        }
        if (i == 3) {
            g(parcel.readInt(), (Bundle) (parcel.readInt() != 0 ? Bundle.CREATOR.createFromParcel(parcel) : null));
            return true;
        }
        if (i != 10001) {
            return super.onTransact(i, parcel, parcel2, i2);
        }
        parcel.readInt();
        parcel.readInt();
        parcel.readString();
        parcel.readInt();
        parcel2.writeNoException();
        return true;
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this;
    }
}
