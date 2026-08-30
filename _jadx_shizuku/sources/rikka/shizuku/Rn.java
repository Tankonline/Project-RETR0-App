package rikka.shizuku;

import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Handler;
import android.util.AtomicFile;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public final class Rn extends X8 {
    public static final Rd u = new Sd().a();
    public static final Rd v;
    public static final AtomicFile w;
    public final RunnableC0607t4 s = new RunnableC0607t4(13, this);
    public final Qn t;

    static {
        Sd sd = new Sd();
        if (Double.isNaN(2.0d)) {
            throw new IllegalArgumentException("Invalid version: 2.0");
        }
        C0769yb c0769ybB = sd.a.clone();
        c0769ybB.a = 2.0d;
        sd.a = c0769ybB;
        v = sd.a();
        w = new AtomicFile(new File("/data/user_de/0/com.android.shell/shizuku.json"));
    }

    public Rn() {
        Qn qn;
        boolean z;
        String[] strArr;
        boolean z2;
        AtomicFile atomicFile = w;
        try {
            FileInputStream fileInputStreamOpenRead = atomicFile.openRead();
            try {
                qn = (Qn) u.b(new InputStreamReader(fileInputStreamOpenRead));
                try {
                    fileInputStreamOpenRead.close();
                } catch (IOException e) {
                    Log.println(5, "ConfigManager", "failed to close: " + e);
                }
            } catch (Throwable th) {
                try {
                    StringBuilder sb = new StringBuilder("load config\n");
                    Locale locale = Locale.ENGLISH;
                    sb.append(Log.getStackTraceString(th));
                    Log.println(5, "ConfigManager", sb.toString());
                    try {
                        fileInputStreamOpenRead.close();
                    } catch (IOException e2) {
                        Log.println(5, "ConfigManager", "failed to close: " + e2);
                    }
                    qn = null;
                } catch (Throwable th2) {
                    try {
                        fileInputStreamOpenRead.close();
                        throw th2;
                    } catch (IOException e3) {
                        Log.println(5, "ConfigManager", "failed to close: " + e3);
                        throw th2;
                    }
                }
            }
            if (qn == null) {
                qn = new Qn();
            }
        } catch (FileNotFoundException unused) {
            Log.println(4, "ConfigManager", "no existing config file " + atomicFile.getBaseFile() + "; starting empty");
            qn = new Qn();
        }
        this.t = qn;
        if (qn.b == null) {
            qn.b = new ArrayList();
            z = true;
        } else {
            z = false;
        }
        ArrayList arrayList = new ArrayList(qn.b);
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            Pn pn = (Pn) obj;
            if (pn.c == null) {
                pn.c = new ArrayList();
            }
            ArrayList arrayListS = AbstractC0599sr.s(pn.a);
            if (arrayListS.isEmpty()) {
                int i2 = pn.a;
                Locale locale2 = Locale.ENGLISH;
                Log.println(4, "ConfigManager", "remove config for uid " + i2 + " since it has gone");
                this.t.b.remove(pn);
            } else {
                Iterator<String> it = pn.c.iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (arrayListS.contains(it.next())) {
                            z2 = false;
                            break;
                        }
                    } else {
                        z2 = true;
                        break;
                    }
                }
                int size2 = pn.c.size();
                LinkedHashSet linkedHashSet = new LinkedHashSet(pn.c);
                pn.c.clear();
                pn.c.addAll(linkedHashSet);
                int size3 = pn.c.size();
                if (size3 < size2) {
                    Locale locale3 = Locale.ENGLISH;
                    Log.println(5, "ConfigManager", "entry.packages has duplicate! Shrunk. (" + size2 + " -> " + size3 + ")");
                }
                if (z2) {
                    int i3 = pn.a;
                    Locale locale4 = Locale.ENGLISH;
                    Log.println(4, "ConfigManager", "remove config for uid " + i3 + " since the packages for it changed");
                    this.t.b.remove(pn);
                }
            }
            z = true;
        }
        C0176f4 c0176f4 = new C0176f4(AbstractC0688vn.s());
        while (c0176f4.hasNext()) {
            for (PackageInfo packageInfo : AbstractC0599sr.q(((Integer) c0176f4.next()).intValue(), 4096L)) {
                if (packageInfo != null && packageInfo.applicationInfo != null && (strArr = packageInfo.requestedPermissions) != null && AbstractC0484p4.d0(strArr, "moe.shizuku.manager.permission.API_V23")) {
                    int i4 = packageInfo.applicationInfo.uid;
                    try {
                        boolean z3 = X8.n(i4, "moe.shizuku.manager.permission.API_V23") == 0;
                        ArrayList arrayList2 = new ArrayList();
                        arrayList2.add(packageInfo.packageName);
                        f0(i4, arrayList2, 6, z3 ? 2 : 0);
                        z = true;
                    } catch (Throwable unused2) {
                        Log.println(5, "ConfigManager", "checkPermission");
                    }
                }
            }
        }
        if (z) {
            e0();
        }
    }

    public final Pn d0(int i) {
        Pn next;
        synchronized (this) {
            Iterator<Pn> it = this.t.b.iterator();
            while (it.hasNext()) {
                next = it.next();
                if (i == next.a) {
                }
            }
            next = null;
        }
        return next;
    }

    public final void e0() {
        int i = Build.VERSION.SDK_INT;
        RunnableC0607t4 runnableC0607t4 = this.s;
        if (i < 29) {
            ((Handler) AbstractC0125de.b.getValue()).removeCallbacks(runnableC0607t4);
        } else if (((Handler) AbstractC0125de.b.getValue()).hasCallbacks(runnableC0607t4)) {
            return;
        }
        ((Handler) AbstractC0125de.b.getValue()).postDelayed(runnableC0607t4, 10000L);
    }

    public final void f0(int i, ArrayList arrayList, int i2, int i3) {
        Pn pn;
        Qn qn = this.t;
        Iterator<Pn> it = qn.b.iterator();
        do {
            if (!it.hasNext()) {
                pn = null;
                break;
            }
            pn = it.next();
        } while (i != pn.a);
        if (pn == null) {
            pn = new Pn(i, i2 & i3);
            qn.b.add(pn);
        } else {
            int i4 = pn.b;
            int i5 = (i2 & i3) | ((~i2) & i4);
            if (i5 == i4) {
                return;
            } else {
                pn.b = i5;
            }
        }
        if (arrayList != null) {
            int size = arrayList.size();
            int i6 = 0;
            while (i6 < size) {
                Object obj = arrayList.get(i6);
                i6++;
                String str = (String) obj;
                if (!pn.c.contains(str)) {
                    pn.c.add(str);
                }
            }
        }
        e0();
    }
}
