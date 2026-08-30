package rikka.shizuku;

import android.os.RemoteException;
import android.util.Log;
import java.util.Locale;

/* JADX INFO: renamed from: rikka.shizuku.ze, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0803ze extends Vs {
    public final Pi b;
    public final Pi c;

    public C0803ze() {
        Pi pi = new Pi();
        this.b = pi;
        this.c = pi;
    }

    public static final C0103cn d(C0803ze c0803ze) {
        int iE;
        if (!Nn.h()) {
            return new C0103cn();
        }
        int iE2 = Nn.e();
        int iF = Nn.f();
        int i = Nn.e;
        if (i < 0) {
            i = 0;
        }
        String str = null;
        if (iF >= 6) {
            try {
                String strJ = Nn.f;
                if (strJ == null) {
                    try {
                        strJ = Nn.k().j();
                        Nn.f = strJ;
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    } catch (SecurityException unused) {
                    }
                }
                str = strJ;
            } catch (Throwable th) {
                Locale locale = Locale.ENGLISH;
                Log.w("ShizukuManager", "getSELinuxContext", th);
            }
        }
        if (Nn.c == 0) {
            iE = 0;
        } else {
            try {
                iE = Nn.k().e("android.permission.GRANT_RUNTIME_PERMISSIONS");
            } catch (RemoteException e2) {
                throw new RuntimeException(e2);
            }
        }
        boolean z = iE == 0;
        Vn.a(0, "moe.shizuku.privileged.api");
        return new C0103cn(iE2, iF, i, str, z);
    }
}
