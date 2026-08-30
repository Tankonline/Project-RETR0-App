package rikka.shizuku;

import android.content.pm.PackageInfo;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.CancellationException;

/* JADX INFO: loaded from: classes.dex */
public final class X3 extends AbstractC0443np implements InterfaceC0585sd {
    public final /* synthetic */ boolean e;
    public final /* synthetic */ Y3 f;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public X3(boolean z, Y3 y3, G7 g7) {
        super(g7);
        this.e = z;
        this.f = y3;
    }

    @Override // rikka.shizuku.InterfaceC0585sd
    public final Object f(Object obj, Object obj2) throws Throwable {
        X3 x3 = (X3) i((InterfaceC0026a8) obj, (G7) obj2);
        Pe pe = Pe.b;
        x3.j(pe);
        return pe;
    }

    @Override // rikka.shizuku.Q4
    public final G7 i(Object obj, G7 g7) {
        return new X3(this.e, this.f, g7);
    }

    @Override // rikka.shizuku.Q4
    public final Object j(Object obj) throws Throwable {
        Y3 y3 = this.f;
        AbstractC0046as.V(obj);
        try {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayListD = X8.D();
            int size = arrayListD.size();
            int i = 0;
            int i2 = 0;
            while (i2 < size) {
                Object obj2 = arrayListD.get(i2);
                i2++;
                PackageInfo packageInfo = (PackageInfo) obj2;
                arrayList.add(packageInfo);
                String str = packageInfo.packageName;
                int i3 = packageInfo.applicationInfo.uid;
                if (!Nn.i) {
                    try {
                        if ((Nn.k().c(i3, 6) & 2) == 2) {
                            i++;
                        }
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                } else if (Vn.a(i3 / 100000, str) == 0) {
                    i++;
                }
            }
            if (!this.e) {
                y3.b.e(new Rl(1, arrayList, Sl.a));
            }
            y3.d.e(new Rl(1, new Integer(i), Sl.a));
        } catch (CancellationException unused) {
        } catch (Throwable th) {
            y3.b.e(new Rl(2, null, th));
            y3.d.e(new Rl(2, new Integer(0), th));
        }
        return Pe.b;
    }
}
