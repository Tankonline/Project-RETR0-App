package rikka.shizuku;

import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public final class Tc extends Vs {
    public static final Sc h = new Sc(0);
    public final boolean e;
    public final HashMap b = new HashMap();
    public final HashMap c = new HashMap();
    public final HashMap d = new HashMap();
    public boolean f = false;
    public boolean g = false;

    public Tc(boolean z) {
        this.e = z;
    }

    @Override // rikka.shizuku.Vs
    public final void c() {
        if (Qc.J(3)) {
            toString();
        }
        this.f = true;
    }

    public final void d(String str, boolean z) {
        Qc.J(3);
        e(str, z);
    }

    public final void e(String str, boolean z) {
        HashMap map = this.c;
        Tc tc = (Tc) map.get(str);
        if (tc != null) {
            if (z) {
                ArrayList arrayList = new ArrayList();
                arrayList.addAll(tc.c.keySet());
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    tc.d((String) obj, true);
                }
            }
            tc.c();
            map.remove(str);
        }
        HashMap map2 = this.d;
        Zs zs = (Zs) map2.get(str);
        if (zs != null) {
            zs.a();
            map2.remove(str);
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && Tc.class == obj.getClass()) {
            Tc tc = (Tc) obj;
            if (this.b.equals(tc.b) && this.c.equals(tc.c) && this.d.equals(tc.d)) {
                return true;
            }
        }
        return false;
    }

    public final void f(Ac ac) {
        if (this.g) {
            if (Qc.J(2)) {
                Log.v("FragmentManager", "Ignoring removeRetainedFragment as the state is already saved");
            }
        } else {
            if (this.b.remove(ac.e) == null || !Qc.J(2)) {
                return;
            }
            Log.v("FragmentManager", "Updating retained Fragments: Removed " + ac);
        }
    }

    public final int hashCode() {
        return this.d.hashCode() + ((this.c.hashCode() + (this.b.hashCode() * 31)) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("FragmentManagerViewModel{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append("} Fragments (");
        Iterator it = this.b.values().iterator();
        while (it.hasNext()) {
            sb.append(it.next());
            if (it.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append(") Child Non Config (");
        Iterator it2 = this.c.keySet().iterator();
        while (it2.hasNext()) {
            sb.append((String) it2.next());
            if (it2.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append(") ViewModelStores (");
        Iterator it3 = this.d.keySet().iterator();
        while (it3.hasNext()) {
            sb.append((String) it3.next());
            if (it3.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append(')');
        return sb.toString();
    }
}
