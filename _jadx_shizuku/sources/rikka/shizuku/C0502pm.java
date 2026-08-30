package rikka.shizuku;

import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

/* JADX INFO: renamed from: rikka.shizuku.pm, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0502pm implements Iterable {
    public C0409mm a;
    public C0409mm b;
    public final WeakHashMap c = new WeakHashMap();
    public int d = 0;

    public C0409mm a(Object obj) {
        C0409mm c0409mm = this.a;
        while (c0409mm != null && !c0409mm.a.equals(obj)) {
            c0409mm = c0409mm.c;
        }
        return c0409mm;
    }

    public Object b(Object obj) {
        C0409mm c0409mmA = a(obj);
        if (c0409mmA == null) {
            return null;
        }
        this.d--;
        WeakHashMap weakHashMap = this.c;
        if (!weakHashMap.isEmpty()) {
            Iterator it = weakHashMap.keySet().iterator();
            while (it.hasNext()) {
                ((AbstractC0471om) it.next()).a(c0409mmA);
            }
        }
        C0409mm c0409mm = c0409mmA.d;
        if (c0409mm != null) {
            c0409mm.c = c0409mmA.c;
        } else {
            this.a = c0409mmA.c;
        }
        C0409mm c0409mm2 = c0409mmA.c;
        if (c0409mm2 != null) {
            c0409mm2.d = c0409mm;
        } else {
            this.b = c0409mm;
        }
        c0409mmA.c = null;
        c0409mmA.d = null;
        return c0409mmA.b;
    }

    public final boolean equals(Object obj) {
        C0378lm c0378lm;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof C0502pm)) {
            return false;
        }
        C0502pm c0502pm = (C0502pm) obj;
        if (this.d != c0502pm.d) {
            return false;
        }
        Iterator it = iterator();
        Iterator it2 = c0502pm.iterator();
        while (true) {
            c0378lm = (C0378lm) it;
            if (!c0378lm.hasNext()) {
                break;
            }
            C0378lm c0378lm2 = (C0378lm) it2;
            if (!c0378lm2.hasNext()) {
                break;
            }
            Map.Entry entry = (Map.Entry) c0378lm.next();
            Object next = c0378lm2.next();
            if ((entry == null && next != null) || (entry != null && !entry.equals(next))) {
                return false;
            }
        }
        return (c0378lm.hasNext() || ((C0378lm) it2).hasNext()) ? false : true;
    }

    public final int hashCode() {
        Iterator it = iterator();
        int iHashCode = 0;
        while (true) {
            C0378lm c0378lm = (C0378lm) it;
            if (!c0378lm.hasNext()) {
                return iHashCode;
            }
            iHashCode += ((Map.Entry) c0378lm.next()).hashCode();
        }
    }

    @Override // java.lang.Iterable
    public final Iterator iterator() {
        C0378lm c0378lm = new C0378lm(this.a, this.b, 0);
        this.c.put(c0378lm, Boolean.FALSE);
        return c0378lm;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("[");
        Iterator it = iterator();
        while (true) {
            C0378lm c0378lm = (C0378lm) it;
            if (!c0378lm.hasNext()) {
                sb.append("]");
                return sb.toString();
            }
            sb.append(((Map.Entry) c0378lm.next()).toString());
            if (c0378lm.hasNext()) {
                sb.append(", ");
            }
        }
    }
}
