package rikka.shizuku;

import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public abstract class Nn {
    public static IBinder a = null;
    public static InterfaceC0033af b = null;
    public static int c = -1;
    public static int d = -1;
    public static int e = -1;
    public static String f = null;
    public static boolean g = false;
    public static boolean h = false;
    public static boolean i = false;
    public static boolean j = false;
    public static final Kn k;
    public static final C0072bn l;
    public static final ArrayList m;
    public static final ArrayList n;
    public static final ArrayList o;
    public static final Handler p;

    static {
        Kn kn = new Kn();
        kn.attachInterface(kn, "moe.shizuku.server.IShizukuApplication");
        k = kn;
        l = new C0072bn(1);
        m = new ArrayList();
        n = new ArrayList();
        o = new ArrayList();
        p = new Handler(Looper.getMainLooper());
    }

    public static void a(T3 t3) {
        synchronized (m) {
            n.add(new Ln(t3, null));
        }
    }

    public static void b(Mn mn, boolean z, Handler handler) {
        if (z && j) {
            if (handler != null) {
                handler.post(new G0(16, mn));
            } else if (Looper.myLooper() == Looper.getMainLooper()) {
                mn.a();
            } else {
                p.post(new G0(16, mn));
            }
        }
        ArrayList arrayList = m;
        synchronized (arrayList) {
            arrayList.add(new Ln(mn, handler));
        }
    }

    public static boolean c(IBinder iBinder, String str) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("moe.shizuku.server.IShizukuService");
            Kn kn = k;
            kn.getClass();
            parcelObtain.writeStrongBinder(kn);
            parcelObtain.writeString(str);
            boolean zTransact = iBinder.transact(14, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
            return zTransact;
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    public static boolean d(IBinder iBinder, String str) {
        Bundle bundle = new Bundle();
        bundle.putInt("shizuku:attach-api-version", 13);
        bundle.putString("shizuku:attach-package-name", str);
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("moe.shizuku.server.IShizukuService");
            Kn kn = k;
            kn.getClass();
            parcelObtain.writeStrongBinder(kn);
            parcelObtain.writeInt(1);
            bundle.writeToParcel(parcelObtain, 0);
            boolean zTransact = iBinder.transact(18, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
            return zTransact;
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    public static int e() {
        int i2 = c;
        if (i2 != -1) {
            return i2;
        }
        try {
            int iF = k().f();
            c = iF;
            return iF;
        } catch (RemoteException e2) {
            throw new RuntimeException(e2);
        } catch (SecurityException unused) {
            return -1;
        }
    }

    public static int f() {
        int i2 = d;
        if (i2 != -1) {
            return i2;
        }
        try {
            int iK = k().k();
            d = iK;
            return iK;
        } catch (RemoteException e2) {
            throw new RuntimeException(e2);
        } catch (SecurityException unused) {
            return -1;
        }
    }

    public static void g(IBinder iBinder, String str) {
        InterfaceC0033af interfaceC0033af;
        int i2 = 17;
        IBinder iBinder2 = a;
        if (iBinder2 == iBinder) {
            return;
        }
        int i3 = 0;
        if (iBinder == null) {
            a = null;
            b = null;
            c = -1;
            d = -1;
            f = null;
            synchronized (m) {
                try {
                    ArrayList arrayList = n;
                    int size = arrayList.size();
                    while (i3 < size) {
                        Object obj = arrayList.get(i3);
                        i3++;
                        Ln ln = (Ln) obj;
                        Handler handler = ln.b;
                        if (handler != null) {
                            handler.post(new G0(i2, (T3) ln.a));
                        } else if (Looper.myLooper() == Looper.getMainLooper()) {
                            ((T3) ln.a).a();
                        } else {
                            p.post(new G0(i2, (T3) ln.a));
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return;
        }
        if (iBinder2 != null) {
            iBinder2.unlinkToDeath(l, 0);
        }
        a = iBinder;
        int i4 = AbstractBinderC0041an.e;
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("moe.shizuku.server.IShizukuService");
        if (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof InterfaceC0033af)) {
            Ze ze = new Ze();
            ze.a = iBinder;
            interfaceC0033af = ze;
        } else {
            interfaceC0033af = (InterfaceC0033af) iInterfaceQueryLocalInterface;
        }
        b = interfaceC0033af;
        try {
            a.linkToDeath(l, 0);
        } catch (Throwable unused) {
            Log.i("ShizukuApplication", "attachApplication");
        }
        try {
            if (!d(a, str) && !c(a, str)) {
                i = true;
            }
            Log.i("ShizukuApplication", "attachApplication");
        } catch (Throwable th2) {
            Log.w("ShizukuApplication", Log.getStackTraceString(th2));
        }
        if (i) {
            j = true;
            l();
        }
    }

    public static boolean h() {
        IBinder iBinder = a;
        return iBinder != null && iBinder.pingBinder();
    }

    public static void i(T3 t3) {
        synchronized (m) {
            n.removeIf(new In(0, t3));
        }
    }

    public static void j(Mn mn) {
        ArrayList arrayList = m;
        synchronized (arrayList) {
            arrayList.removeIf(new In(1, mn));
        }
    }

    public static InterfaceC0033af k() {
        InterfaceC0033af interfaceC0033af = b;
        if (interfaceC0033af != null) {
            return interfaceC0033af;
        }
        throw new IllegalStateException("binder haven't been received");
    }

    public static void l() {
        ArrayList arrayList = m;
        synchronized (arrayList) {
            try {
                int size = arrayList.size();
                int i2 = 0;
                while (i2 < size) {
                    Object obj = arrayList.get(i2);
                    i2++;
                    Ln ln = (Ln) obj;
                    Handler handler = ln.b;
                    if (handler != null) {
                        handler.post(new G0(16, (Mn) ln.a));
                    } else if (Looper.myLooper() == Looper.getMainLooper()) {
                        ((Mn) ln.a).a();
                    } else {
                        p.post(new G0(16, (Mn) ln.a));
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        j = true;
    }
}
