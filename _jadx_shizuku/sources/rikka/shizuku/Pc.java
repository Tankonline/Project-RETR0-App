package rikka.shizuku;

import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;

/* JADX INFO: loaded from: classes.dex */
public final class Pc implements Nc {
    public final /* synthetic */ Qc a;

    public Pc(Qc qc) {
        this.a = qc;
    }

    @Override // rikka.shizuku.Nc
    public final boolean a(ArrayList arrayList, ArrayList arrayList2) {
        boolean zT;
        Qc qc = this.a;
        qc.getClass();
        if (Qc.J(2)) {
            Log.v("FragmentManager", "FragmentManager has the following pending actions inside of prepareBackStackState: " + qc.a);
        }
        int i = 0;
        if (qc.d.isEmpty()) {
            Log.i("FragmentManager", "Ignoring call to start back stack pop because the back stack is empty.");
            zT = false;
        } else {
            ArrayList arrayList3 = qc.d;
            L4 l4 = (L4) arrayList3.get(arrayList3.size() - 1);
            qc.h = l4;
            ArrayList arrayList4 = l4.a;
            int size = arrayList4.size();
            int i2 = 0;
            while (i2 < size) {
                Object obj = arrayList4.get(i2);
                i2++;
                Ac ac = ((C0062bd) obj).b;
                if (ac != null) {
                    ac.m = true;
                }
            }
            zT = qc.T(arrayList, arrayList2, -1, 0);
        }
        if (!qc.n.isEmpty() && arrayList.size() > 0) {
            ((Boolean) arrayList2.get(arrayList.size() - 1)).getClass();
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            int size2 = arrayList.size();
            int i3 = 0;
            while (i3 < size2) {
                Object obj2 = arrayList.get(i3);
                i3++;
                linkedHashSet.addAll(Qc.E((L4) obj2));
            }
            ArrayList arrayList5 = qc.n;
            int size3 = arrayList5.size();
            while (i < size3) {
                Object obj3 = arrayList5.get(i);
                i++;
                if (obj3 != null) {
                    throw new ClassCastException();
                }
                Iterator it = linkedHashSet.iterator();
                if (it.hasNext()) {
                    throw null;
                }
            }
        }
        return zT;
    }
}
