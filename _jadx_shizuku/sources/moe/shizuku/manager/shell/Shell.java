package moe.shizuku.manager.shell;

import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import java.util.Objects;
import rikka.rish.RishTerminal;
import rikka.shizuku.AbstractC0744xh;
import rikka.shizuku.C0648ue;
import rikka.shizuku.C0719wn;
import rikka.shizuku.Ln;
import rikka.shizuku.N3;
import rikka.shizuku.Nn;

/* JADX INFO: loaded from: classes.dex */
public class Shell {
    public static void main(String[] strArr, String str, IBinder iBinder, Handler handler) {
        Objects.toString(iBinder);
        AbstractC0744xh.m = iBinder;
        AbstractC0744xh.n = "moe.shizuku.server.IShizukuService";
        AbstractC0744xh.o = 30000;
        AbstractC0744xh.I();
        Nn.g(iBinder, str);
        Nn.b(new C0648ue(1, strArr), true, null);
    }

    public final void a(String[] strArr, boolean z) {
        boolean zN;
        if (z) {
            try {
                RishTerminal rishTerminal = new RishTerminal(strArr);
                rishTerminal.d();
                System.exit(rishTerminal.e());
                return;
            } catch (Throwable th) {
                System.err.println(th.getMessage());
                System.err.flush();
                System.exit(1);
                return;
            }
        }
        N3 n3 = new N3(this, 4, strArr);
        if (!Nn.g) {
            try {
                boolean zH = Nn.k().h();
                Nn.g = zH;
                if (!zH) {
                    if (zH) {
                        zN = false;
                    } else if (Nn.h) {
                        zN = true;
                    } else {
                        try {
                            zN = Nn.k().n();
                            Nn.h = zN;
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    if (zN) {
                        System.err.println("Permission denied");
                        System.err.flush();
                        System.exit(1);
                        return;
                    }
                    C0719wn c0719wn = new C0719wn(n3);
                    synchronized (Nn.m) {
                        Nn.o.add(new Ln(c0719wn, null));
                    }
                    try {
                        Nn.k().i(0);
                        return;
                    } catch (RemoteException e2) {
                        throw new RuntimeException(e2);
                    }
                }
            } catch (RemoteException e3) {
                throw new RuntimeException(e3);
            }
        }
        n3.run();
    }
}
