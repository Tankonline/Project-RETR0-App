package rikka.shizuku;

import android.view.View;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: renamed from: rikka.shizuku.vh, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0682vh {
    public final /* synthetic */ int a;
    public int b;
    public int c;
    public int d;
    public int e;
    public final Object f;
    public final Object g;

    public C0682vh(int i) {
        this.a = 0;
        this.b = i;
        if (i <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        this.f = new Ys(21);
        this.g = new Ne();
    }

    public void a() {
        ArrayList arrayList = (ArrayList) this.f;
        View view = (View) arrayList.get(arrayList.size() - 1);
        Io io = (Io) view.getLayoutParams();
        this.c = ((StaggeredGridLayoutManager) this.g).r.b(view);
        io.getClass();
    }

    public void b() {
        ((ArrayList) this.f).clear();
        this.b = Integer.MIN_VALUE;
        this.c = Integer.MIN_VALUE;
        this.d = 0;
    }

    public int c() {
        boolean z = ((StaggeredGridLayoutManager) this.g).w;
        ArrayList arrayList = (ArrayList) this.f;
        return z ? e(arrayList.size() - 1, -1) : e(0, arrayList.size());
    }

    public int d() {
        boolean z = ((StaggeredGridLayoutManager) this.g).w;
        ArrayList arrayList = (ArrayList) this.f;
        return z ? e(0, arrayList.size()) : e(arrayList.size() - 1, -1);
    }

    public int e(int i, int i2) {
        StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) this.g;
        int iK = staggeredGridLayoutManager.r.k();
        int iG = staggeredGridLayoutManager.r.g();
        int i3 = i2 > i ? 1 : -1;
        while (i != i2) {
            View view = (View) ((ArrayList) this.f).get(i);
            int iE = staggeredGridLayoutManager.r.e(view);
            int iB = staggeredGridLayoutManager.r.b(view);
            boolean z = iE <= iG;
            boolean z2 = iB >= iK;
            if (z && z2 && (iE < iK || iB > iG)) {
                return AbstractC0162el.G(view);
            }
            i += i3;
        }
        return -1;
    }

    public Object f(Object obj) {
        synchronized (((Ne) this.g)) {
            Object obj2 = ((LinkedHashMap) ((Ys) this.f).b).get(obj);
            if (obj2 != null) {
                this.d++;
                return obj2;
            }
            this.e++;
            return null;
        }
    }

    public int g(int i) {
        int i2 = this.c;
        if (i2 != Integer.MIN_VALUE) {
            return i2;
        }
        if (((ArrayList) this.f).size() == 0) {
            return i;
        }
        a();
        return this.c;
    }

    public View h(int i, int i2) {
        StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) this.g;
        ArrayList arrayList = (ArrayList) this.f;
        View view = null;
        if (i2 != -1) {
            int size = arrayList.size() - 1;
            while (size >= 0) {
                View view2 = (View) arrayList.get(size);
                if ((staggeredGridLayoutManager.w && AbstractC0162el.G(view2) >= i) || ((!staggeredGridLayoutManager.w && AbstractC0162el.G(view2) <= i) || !view2.hasFocusable())) {
                    break;
                }
                size--;
                view = view2;
            }
            return view;
        }
        int size2 = arrayList.size();
        int i3 = 0;
        while (i3 < size2) {
            View view3 = (View) arrayList.get(i3);
            if ((staggeredGridLayoutManager.w && AbstractC0162el.G(view3) <= i) || ((!staggeredGridLayoutManager.w && AbstractC0162el.G(view3) >= i) || !view3.hasFocusable())) {
                break;
            }
            i3++;
            view = view3;
        }
        return view;
    }

    public int i(int i) {
        int i2 = this.b;
        if (i2 != Integer.MIN_VALUE) {
            return i2;
        }
        if (((ArrayList) this.f).size() == 0) {
            return i;
        }
        View view = (View) ((ArrayList) this.f).get(0);
        Io io = (Io) view.getLayoutParams();
        this.b = ((StaggeredGridLayoutManager) this.g).r.e(view);
        io.getClass();
        return this.b;
    }

    public Object j(Object obj, Object obj2) {
        Object objPut;
        synchronized (((Ne) this.g)) {
            this.c += k(obj, obj2);
            objPut = ((LinkedHashMap) ((Ys) this.f).b).put(obj, obj2);
            if (objPut != null) {
                this.c -= k(obj, objPut);
            }
        }
        int i = this.b;
        while (true) {
            synchronized (((Ne) this.g)) {
                try {
                    if (this.c < 0 || (((LinkedHashMap) ((Ys) this.f).b).isEmpty() && this.c != 0)) {
                        break;
                    }
                    if (this.c > i && !((LinkedHashMap) ((Ys) this.f).b).isEmpty()) {
                        Set setEntrySet = ((LinkedHashMap) ((Ys) this.f).b).entrySet();
                        Object next = null;
                        if (setEntrySet instanceof List) {
                            List list = (List) setEntrySet;
                            if (!list.isEmpty()) {
                                next = list.get(0);
                            }
                        } else {
                            Iterator it = setEntrySet.iterator();
                            if (it.hasNext()) {
                                next = it.next();
                            }
                        }
                        Map.Entry entry = (Map.Entry) next;
                        if (entry == null) {
                            return objPut;
                        }
                        Object key = entry.getKey();
                        Object value = entry.getValue();
                        ((LinkedHashMap) ((Ys) this.f).b).remove(key);
                        this.c -= k(key, value);
                    }
                    return objPut;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        throw new IllegalStateException("LruCache.sizeOf() is reporting inconsistent results!");
    }

    public int k(Object obj, Object obj2) {
        int iL = l(obj, obj2);
        if (iL >= 0) {
            return iL;
        }
        throw new IllegalStateException(("Negative size: " + obj + '=' + obj2).toString());
    }

    public int l(Object obj, Object obj2) {
        return 1;
    }

    public String toString() {
        String str;
        switch (this.a) {
            case 0:
                synchronized (((Ne) this.g)) {
                    try {
                        int i = this.d;
                        int i2 = this.e + i;
                        str = "LruCache[maxSize=" + this.b + ",hits=" + this.d + ",misses=" + this.e + ",hitRate=" + (i2 != 0 ? (i * 100) / i2 : 0) + "%]";
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                return str;
            default:
                return super.toString();
        }
    }

    public C0682vh(StaggeredGridLayoutManager staggeredGridLayoutManager, int i) {
        this.a = 1;
        this.g = staggeredGridLayoutManager;
        this.f = new ArrayList();
        this.b = Integer.MIN_VALUE;
        this.c = Integer.MIN_VALUE;
        this.d = 0;
        this.e = i;
    }
}
