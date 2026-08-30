package rikka.shizuku;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.util.ArrayMap;
import android.util.Log;
import android.util.SparseArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executors;
import rikka.html.text.style.BulletSpan;

/* JADX INFO: loaded from: classes.dex */
public final class Xn implements InterfaceC0310jg {
    public static final C0589sh e = new C0589sh(0, "UserServiceManager");
    public Object a;
    public Object b;
    public Object c;
    public Object d;

    /* JADX WARN: Multi-variable type inference failed */
    public Xn(InterfaceC0124dd interfaceC0124dd, InterfaceC0124dd interfaceC0124dd2, Class cls) {
        this.a = (AbstractC0127dg) interfaceC0124dd;
        this.b = interfaceC0124dd2;
        this.c = cls;
    }

    public static int e(View view, Ja ja) {
        return ((ja.c(view) / 2) + ja.e(view)) - ((ja.l() / 2) + ja.k());
    }

    public static PackageInfo f(String str, int i, int i2) {
        ApplicationInfo applicationInfo;
        PackageInfo packageInfoR = AbstractC0599sr.r(str, 8192L, i2);
        if (packageInfoR == null || (applicationInfo = packageInfoR.applicationInfo) == null) {
            throw new SecurityException("unable to find package " + str);
        }
        if (applicationInfo.uid % 100000 == i) {
            return packageInfoR;
        }
        throw new SecurityException("package " + str + " is not owned by " + i);
    }

    public static View h(AbstractC0162el abstractC0162el, Ja ja) {
        int iV = abstractC0162el.v();
        View view = null;
        if (iV == 0) {
            return null;
        }
        int iL = (ja.l() / 2) + ja.k();
        int i = Integer.MAX_VALUE;
        for (int i2 = 0; i2 < iV; i2++) {
            View viewU = abstractC0162el.u(i2);
            int iAbs = Math.abs(((ja.c(viewU) / 2) + ja.e(viewU)) - iL);
            if (iAbs < i) {
                view = viewU;
                i = iAbs;
            }
        }
        return view;
    }

    public void a(Ac ac) {
        if (((ArrayList) this.a).contains(ac)) {
            throw new IllegalStateException("Fragment already added: " + ac);
        }
        synchronized (((ArrayList) this.a)) {
            ((ArrayList) this.a).add(ac);
        }
        ac.k = true;
    }

    public int[] b(AbstractC0162el abstractC0162el, View view) {
        int[] iArr = new int[2];
        if (abstractC0162el.d()) {
            iArr[0] = e(view, n(abstractC0162el));
        } else {
            iArr[0] = 0;
        }
        if (abstractC0162el.e()) {
            iArr[1] = e(view, o(abstractC0162el));
            return iArr;
        }
        iArr[1] = 0;
        return iArr;
    }

    public Xr c(Xr xr, String str, int i, boolean z, PackageInfo packageInfo) {
        IBinder iBinder;
        C0589sh c0589sh = e;
        if (xr != null) {
            String str2 = xr.d;
            int i2 = xr.c;
            if (i2 != i) {
                Locale locale = Locale.ENGLISH;
                c0589sh.b(2, "Remove service record " + str + " (" + str2 + ") because version code not matched (old=" + i2 + ", new=" + i + ")");
            } else {
                if (xr.h || ((iBinder = xr.e) != null && iBinder.pingBinder())) {
                    Locale locale2 = Locale.ENGLISH;
                    c0589sh.b(4, "Found existing service record " + str + " (" + str2 + ")");
                    if (xr.g != z) {
                        xr.g = z;
                    }
                    return xr;
                }
                Locale locale3 = Locale.ENGLISH;
                c0589sh.b(2, "Service in record " + str + " (" + str2 + ") is dead");
            }
            u(xr);
        }
        Xr xr2 = new Xr(this, i, z);
        String str3 = packageInfo.packageName;
        Map map = (Map) this.c;
        List listSynchronizedList = (List) map.get(str3);
        if (listSynchronizedList == null) {
            listSynchronizedList = Collections.synchronizedList(new ArrayList());
            map.put(str3, listSynchronizedList);
        }
        listSynchronizedList.add(xr2);
        Wn wn = new Wn(packageInfo.packageName, xr2);
        AbstractC0599sr.L(packageInfo.applicationInfo.sourceDir, wn);
        ((ArrayMap) this.d).put(xr2, wn);
        ((Map) this.b).put(str, xr2);
        String str4 = xr2.d;
        String string = Boolean.toString(z);
        String str5 = packageInfo.applicationInfo.sourceDir;
        Locale locale4 = Locale.ENGLISH;
        c0589sh.b(4, "New service record " + str + " (" + str4 + "): version=" + i + ", daemon=" + string + ", apk=" + str5);
        return xr2;
    }

    public void d(Object obj, ArrayList arrayList, HashSet hashSet) {
        if (arrayList.contains(obj)) {
            return;
        }
        if (hashSet.contains(obj)) {
            throw new RuntimeException("This graph contains cyclic dependencies");
        }
        hashSet.add(obj);
        ArrayList arrayList2 = (ArrayList) ((C0104co) this.b).get(obj);
        if (arrayList2 != null) {
            int size = arrayList2.size();
            for (int i = 0; i < size; i++) {
                d(arrayList2.get(i), arrayList, hashSet);
            }
        }
        hashSet.remove(obj);
        arrayList.add(obj);
    }

    public Ac g(String str) {
        Xc xc = (Xc) ((HashMap) this.b).get(str);
        if (xc != null) {
            return xc.c;
        }
        return null;
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [rikka.shizuku.dd, rikka.shizuku.dg] */
    @Override // rikka.shizuku.InterfaceC0310jg
    public Object getValue() {
        Vs vs = (Vs) this.d;
        if (vs != null) {
            return vs;
        }
        Vs vsR = new Ys((Zs) ((AbstractC0127dg) this.a).a(), new Hm(this)).r((Class) this.c);
        this.d = vsR;
        return vsR;
    }

    public Ac i(String str) {
        for (Xc xc : ((HashMap) this.b).values()) {
            if (xc != null) {
                Ac acI = xc.c;
                if (!str.equals(acI.e)) {
                    acI = acI.v.c.i(str);
                }
                if (acI != null) {
                    return acI;
                }
            }
        }
        return null;
    }

    public C0289ip j(F0 f0) {
        ArrayList arrayList = (ArrayList) this.c;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            C0289ip c0289ip = (C0289ip) arrayList.get(i);
            if (c0289ip != null && c0289ip.b == f0) {
                return c0289ip;
            }
        }
        C0289ip c0289ip2 = new C0289ip((Context) this.b, f0);
        arrayList.add(c0289ip2);
        return c0289ip2;
    }

    public ArrayList k() {
        ArrayList arrayList = new ArrayList();
        for (Xc xc : ((HashMap) this.b).values()) {
            if (xc != null) {
                arrayList.add(xc);
            }
        }
        return arrayList;
    }

    public ArrayList l() {
        ArrayList arrayList = new ArrayList();
        for (Xc xc : ((HashMap) this.b).values()) {
            if (xc != null) {
                arrayList.add(xc.c);
            } else {
                arrayList.add(null);
            }
        }
        return arrayList;
    }

    public List m() {
        ArrayList arrayList;
        if (((ArrayList) this.a).isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        synchronized (((ArrayList) this.a)) {
            arrayList = new ArrayList((ArrayList) this.a);
        }
        return arrayList;
    }

    public Ja n(AbstractC0162el abstractC0162el) {
        Aj aj = (Aj) this.d;
        if (aj == null || ((AbstractC0162el) aj.b) != abstractC0162el) {
            this.d = new Aj(abstractC0162el, 0);
        }
        return (Aj) this.d;
    }

    public Ja o(AbstractC0162el abstractC0162el) {
        Aj aj = (Aj) this.c;
        if (aj == null || ((AbstractC0162el) aj.b) != abstractC0162el) {
            this.c = new Aj(abstractC0162el, 1);
        }
        return (Aj) this.c;
    }

    public Vs p(C0178f6 c0178f6, String str) {
        Vs vs;
        boolean zIsInstance;
        Vs vsA;
        synchronized (((Ne) this.d)) {
            try {
                vs = (Vs) ((Zs) this.a).a.get(str);
                Map map = C0178f6.b;
                Class clsC = c0178f6.a;
                Integer num = (Integer) map.get(clsC);
                if (num != null) {
                    zIsInstance = AbstractC0599sr.y(num.intValue(), vs);
                } else {
                    if (clsC.isPrimitive()) {
                        clsC = X8.C(AbstractC0593sl.a(clsC));
                    }
                    zIsInstance = clsC.isInstance(vs);
                }
                if (zIsInstance) {
                    Xs xs = (Xs) this.b;
                    if (xs instanceof Cm) {
                    }
                } else {
                    Oi oi = new Oi((AbstractC0211g8) this.c);
                    oi.a(Ys.c, str);
                    Xs xs2 = (Xs) this.b;
                    try {
                        try {
                            vsA = xs2.b(c0178f6, oi);
                        } catch (AbstractMethodError unused) {
                            vsA = xs2.a(c0178f6.a());
                        }
                    } catch (AbstractMethodError unused2) {
                        vsA = xs2.d(c0178f6.a(), oi);
                    }
                    vs = vsA;
                    Vs vs2 = (Vs) ((Zs) this.a).a.put(str, vs);
                    if (vs2 != null) {
                        vs2.a();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return vs;
    }

    public void q(Xc xc) {
        Ac ac = xc.c;
        String str = ac.e;
        HashMap map = (HashMap) this.b;
        if (map.get(str) != null) {
            return;
        }
        map.put(ac.e, xc);
        if (Qc.J(2)) {
            Log.v("FragmentManager", "Added fragment to active set " + ac);
        }
    }

    public void r(Xc xc) {
        Ac ac = xc.c;
        if (ac.C) {
            ((Tc) this.d).f(ac);
        }
        HashMap map = (HashMap) this.b;
        if (map.get(ac.e) == xc && ((Xc) map.put(ac.e, null)) != null && Qc.J(2)) {
            Log.v("FragmentManager", "Removed fragment from active set " + ac);
        }
    }

    public boolean s(F0 f0, MenuItem menuItem) {
        return ((ActionMode.Callback) this.a).onActionItemClicked(j(f0), new MenuItemC0343ki((Context) this.b, (InterfaceMenuItemC0412mp) menuItem));
    }

    public boolean t(F0 f0, MenuC0129di menuC0129di) {
        C0289ip c0289ipJ = j(f0);
        C0104co c0104co = (C0104co) this.d;
        Menu menuC0776yi = (Menu) c0104co.get(menuC0129di);
        if (menuC0776yi == null) {
            menuC0776yi = new MenuC0776yi((Context) this.b, menuC0129di);
            c0104co.put(menuC0129di, menuC0776yi);
        }
        return ((ActionMode.Callback) this.a).onCreateActionMode(c0289ipJ, menuC0776yi);
    }

    public void u(Xr xr) {
        if (((Map) this.b).values().remove(xr)) {
            IBinder iBinder = xr.e;
            if (iBinder != null) {
                iBinder.unlinkToDeath(xr.b, 0);
            }
            IBinder iBinder2 = xr.e;
            if (iBinder2 != null && iBinder2.pingBinder()) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    try {
                        parcelObtain.writeInterfaceToken(xr.e.getInterfaceDescriptor());
                        xr.e.transact(16777115, parcelObtain, parcelObtain2, 1);
                    } catch (Throwable unused) {
                        String str = xr.d;
                        Locale locale = Locale.ENGLISH;
                        Log.println(5, "UserServiceRecord", "Failed to call destroy " + str);
                    }
                    parcelObtain.recycle();
                    parcelObtain2.recycle();
                } catch (Throwable th) {
                    parcelObtain.recycle();
                    parcelObtain2.recycle();
                    throw th;
                }
            }
            xr.f.kill();
            ArrayMap arrayMap = (ArrayMap) this.d;
            X1 x1 = (X1) arrayMap.get(xr);
            if (x1 != null) {
                AbstractC0599sr.M(x1);
                arrayMap.remove(xr);
            }
        }
    }

    public void v(IBinder iBinder, String str) {
        Map.Entry entry;
        Iterator it = ((Map) this.b).entrySet().iterator();
        do {
            if (!it.hasNext()) {
                entry = null;
                break;
            }
            entry = (Map.Entry) it.next();
        } while (!((Xr) entry.getValue()).d.equals(str));
        if (entry == null) {
            throw new IllegalArgumentException("unable to find token " + str);
        }
        C0589sh c0589sh = e;
        Locale locale = Locale.ENGLISH;
        c0589sh.b(2, "Received binder for service record " + str);
        Xr xr = (Xr) entry.getValue();
        String str2 = xr.d;
        Log.println(2, "UserServiceRecord", "Binder received for service record " + str2);
        X8.r.removeCallbacks(xr.a);
        xr.e = iBinder;
        try {
            iBinder.linkToDeath(xr.b, 0);
        } catch (Throwable unused) {
            Locale locale2 = Locale.ENGLISH;
            Log.println(5, "UserServiceRecord", "linkToDeath " + str2);
        }
        xr.a();
    }

    public Bundle w(String str, Bundle bundle) {
        HashMap map = (HashMap) this.c;
        return bundle != null ? (Bundle) map.put(str, bundle) : (Bundle) map.remove(str);
    }

    public void x() {
        AbstractC0162el abstractC0162el;
        View viewH;
        RecyclerView recyclerView = (RecyclerView) this.a;
        if (recyclerView == null || (abstractC0162el = recyclerView.n) == null) {
            return;
        }
        if (abstractC0162el.e()) {
            viewH = h(abstractC0162el, o(abstractC0162el));
        } else {
            viewH = abstractC0162el.d() ? h(abstractC0162el, n(abstractC0162el)) : null;
        }
        if (viewH == null) {
            return;
        }
        int[] iArrB = b(abstractC0162el, viewH);
        int i = iArrB[0];
        if (i == 0 && iArrB[1] == 0) {
            return;
        }
        ((RecyclerView) this.a).l0(i, iArrB[1], false);
    }

    public Xn(Zs zs, Xs xs, AbstractC0211g8 abstractC0211g8) {
        this.a = zs;
        this.b = xs;
        this.c = abstractC0211g8;
        this.d = new Ne();
    }

    public Xn(int i) {
        switch (i) {
            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                this.a = new float[3];
                this.b = new float[360];
                this.c = new int[20];
                this.d = new SparseArray();
                break;
            case 3:
                this.a = new Yj(10);
                this.b = new C0104co(0);
                this.c = new ArrayList();
                this.d = new HashSet();
                break;
            case 4:
                this.a = new ArrayList();
                this.b = new HashMap();
                this.c = new HashMap();
                break;
            case BulletSpan.STANDARD_BULLET_RADIUS /* 5 */:
            case 7:
            default:
                this.a = Executors.newSingleThreadExecutor();
                this.b = Collections.synchronizedMap(new ArrayMap());
                this.c = Collections.synchronizedMap(new ArrayMap());
                this.d = new ArrayMap();
                Collections.synchronizedMap(new ArrayMap());
                break;
            case 6:
                this.b = new C0473oo(this);
                break;
            case 8:
                this.a = new C0298j4(0);
                this.b = new SparseArray();
                this.c = new C0620th();
                this.d = new C0298j4(0);
                break;
        }
    }

    public Xn(Typeface typeface, Bi bi) {
        int i;
        int i2;
        int i3;
        int i4;
        this.d = typeface;
        this.a = bi;
        this.c = new Ci(1024);
        int iA = bi.a(6);
        if (iA != 0) {
            int i5 = iA + bi.a;
            i = ((ByteBuffer) bi.d).getInt(((ByteBuffer) bi.d).getInt(i5) + i5);
        } else {
            i = 0;
        }
        this.b = new char[i * 2];
        int iA2 = bi.a(6);
        if (iA2 != 0) {
            int i6 = iA2 + bi.a;
            i2 = ((ByteBuffer) bi.d).getInt(((ByteBuffer) bi.d).getInt(i6) + i6);
        } else {
            i2 = 0;
        }
        for (int i7 = 0; i7 < i2; i7++) {
            Va va = new Va(this, i7);
            Ai aiB = va.b();
            int iA3 = aiB.a(4);
            Character.toChars(iA3 != 0 ? ((ByteBuffer) aiB.d).getInt(iA3 + aiB.a) : 0, (char[]) this.b, i7 * 2);
            Ai aiB2 = va.b();
            int iA4 = aiB2.a(16);
            if (iA4 != 0) {
                int i8 = iA4 + aiB2.a;
                i3 = ((ByteBuffer) aiB2.d).getInt(((ByteBuffer) aiB2.d).getInt(i8) + i8);
            } else {
                i3 = 0;
            }
            AbstractC0046as.h("invalid metadata codepoint length", i3 > 0);
            Ai aiB3 = va.b();
            int iA5 = aiB3.a(16);
            if (iA5 != 0) {
                int i9 = iA5 + aiB3.a;
                i4 = ((ByteBuffer) aiB3.d).getInt(((ByteBuffer) aiB3.d).getInt(i9) + i9);
            } else {
                i4 = 0;
            }
            ((Ci) this.c).a(va, 0, i4 - 1);
        }
    }
}
