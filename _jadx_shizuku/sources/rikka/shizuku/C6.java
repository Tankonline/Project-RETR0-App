package rikka.shizuku;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public abstract class C6 extends B6 {
    public static void c0(ArrayList arrayList, List list) {
        if (list != null) {
            arrayList.addAll(list);
            return;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next());
        }
    }
}
