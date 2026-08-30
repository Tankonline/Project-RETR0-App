package rikka.shizuku;

import android.content.pm.UserInfo;
import android.os.Build;
import android.os.IUserManager;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public abstract class Vn {
    public static final ArrayList a;

    static {
        C0597sp.e = new C0579s7(17);
        a = new ArrayList();
    }

    public static int a(int i, String str) {
        if (!Nn.h()) {
            return -1;
        }
        try {
            int i2 = Build.VERSION.SDK_INT;
            if (i2 >= 31) {
                return AbstractC0134dn.c.a().checkPermission("moe.shizuku.manager.permission.API_V23", str, i);
            }
            return i2 >= 30 ? AbstractC0134dn.d.a().checkPermission("moe.shizuku.manager.permission.API_V23", str, i) : AbstractC0134dn.c.a().checkPermission("moe.shizuku.manager.permission.API_V23", str, i);
        } catch (RemoteException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static ArrayList b() {
        List<UserInfo> users;
        if (!Nn.h()) {
            return new ArrayList(new C0053b4(new Ur[]{new Ur(Tr.a, "Owner")}, true));
        }
        try {
            IUserManager iUserManagerA = AbstractC0134dn.b.a();
            if (Build.VERSION.SDK_INT >= 30) {
                users = iUserManagerA.getUsers(true, true, true);
            } else {
                try {
                    users = iUserManagerA.getUsers(true);
                } catch (NoSuchMethodError unused) {
                    users = iUserManagerA.getUsers(true, true, true);
                }
            }
            ArrayList arrayList = new ArrayList();
            for (UserInfo userInfo : users) {
                arrayList.add(new Ur(userInfo.id, userInfo.name));
            }
            return arrayList;
        } catch (Throwable unused2) {
            return new ArrayList(new C0053b4(new Ur[]{new Ur(Tr.a, "Owner")}, true));
        }
    }

    public static ArrayList c(boolean z) {
        ArrayList arrayList = a;
        synchronized (arrayList) {
            if (z) {
                try {
                    if (arrayList.isEmpty()) {
                        arrayList.clear();
                        arrayList.addAll(b());
                    }
                } catch (Throwable th) {
                    throw th;
                }
            } else {
                arrayList.clear();
                arrayList.addAll(b());
            }
        }
        return arrayList;
    }
}
