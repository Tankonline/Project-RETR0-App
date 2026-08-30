package rikka.shizuku;

import android.os.Build;
import android.os.SystemProperties;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/* JADX INFO: renamed from: rikka.shizuku.we, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0710we extends T4 {
    public final ArrayList f;
    public final C0803ze g;
    public final Y3 h;

    public C0710we(C0803ze c0803ze, Y3 y3) {
        super(new ArrayList());
        this.f = new ArrayList();
        this.g = c0803ze;
        this.h = y3;
        m();
        j(true);
    }

    @Override // rikka.shizuku.Yk
    public final long b(int i) {
        return ((Long) this.f.get(i)).longValue();
    }

    @Override // rikka.shizuku.T4
    public final InterfaceC0242h8 k() {
        return new Cif();
    }

    public final void l(C0759y1 c0759y1, Object obj, long j) {
        Cif cif = (Cif) this.e;
        ArrayList arrayList = cif.a;
        int iIndexOf = arrayList.indexOf(c0759y1);
        if (iIndexOf == -1) {
            arrayList.add(c0759y1);
            iIndexOf = arrayList.size() - 1;
        }
        cif.b.add(Integer.valueOf(iIndexOf));
        this.d.add(obj);
        this.f.add(Long.valueOf(j));
    }

    /* JADX WARN: Code duplicated, block: B:67:0x012b  */
    public final void m() {
        C0103cn c0103cn;
        String str;
        Iterable iterableSingletonList;
        Object next;
        Integer num;
        Object obj = this.g.c.e;
        Object obj2 = Pi.k;
        if (obj == obj2) {
            obj = null;
        }
        Rl rl = (Rl) obj;
        if (rl == null || (c0103cn = (C0103cn) rl.b) == null) {
            return;
        }
        Object obj3 = this.h.e.e;
        if (obj3 == obj2) {
            obj3 = null;
        }
        Rl rl2 = (Rl) obj3;
        int iIntValue = (rl2 == null || (num = (Integer) rl2.b) == null) ? 0 : num.intValue();
        boolean zA = c0103cn.a();
        boolean z = Tr.a == 0;
        ((Cif) this.e).b.clear();
        this.d.clear();
        this.f.clear();
        l(Ym.y, c0103cn, 0L);
        boolean z2 = c0103cn.e;
        if (z2) {
            l(ViewOnClickListenerC0806zh.x, new Hj(c0103cn, Integer.valueOf(iIntValue)), 1L);
            l(S3.A, c0103cn, 2L);
        }
        if (zA && !z2) {
            l(C0790z1.x, c0103cn, 7L);
        }
        if (z) {
            String str2 = System.getenv("PATH");
            if (str2 != null) {
                String strValueOf = String.valueOf(new char[]{File.pathSeparatorChar}[0]);
                int iIndexOf = str2.indexOf(strValueOf, 0);
                if (iIndexOf != -1) {
                    ArrayList arrayList = new ArrayList(10);
                    int length = 0;
                    do {
                        arrayList.add(str2.subSequence(length, iIndexOf).toString());
                        length = strValueOf.length() + iIndexOf;
                        iIndexOf = str2.indexOf(strValueOf, length);
                    } while (iIndexOf != -1);
                    arrayList.add(str2.subSequence(length, str2.length()).toString());
                    iterableSingletonList = arrayList;
                } else {
                    iterableSingletonList = Collections.singletonList(str2.toString());
                }
                Iterator it = iterableSingletonList.iterator();
                do {
                    if (!it.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it.next();
                } while (!new File(AbstractC0535qo.c((String) next, "/su")).exists());
                str = (String) next;
            } else {
                str = null;
            }
            boolean z3 = str != null;
            boolean z4 = zA && c0103cn.a == 0;
            if (z3) {
                l(Ym.z, Boolean.valueOf(z4), 3L);
            }
            if (Build.VERSION.SDK_INT >= 30) {
                l(C0790z1.B, null, 4L);
            } else {
                int i = SystemProperties.getInt("service.adb.tcp.port", -1);
                if (i == -1) {
                    i = SystemProperties.getInt("persist.adb.tcp.port", -1);
                }
                if (i > 0) {
                    l(C0790z1.B, null, 4L);
                }
            }
            l(C0790z1.A, null, 5L);
            if (!z3) {
                l(Ym.z, Boolean.valueOf(z4), 3L);
            }
        }
        l(C0790z1.z, null, 6L);
        this.a.b();
    }
}
