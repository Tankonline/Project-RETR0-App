package rikka.shizuku;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/* JADX INFO: renamed from: rikka.shizuku.v6, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0671v6 extends C6 {
    public static List d0(Iterable iterable) {
        ArrayList arrayList;
        boolean z = iterable instanceof Collection;
        C0214gb c0214gb = C0214gb.a;
        if (z) {
            Collection collection = (Collection) iterable;
            int size = collection.size();
            if (size != 0) {
                if (size != 1) {
                    return new ArrayList(collection);
                }
                return Collections.singletonList(iterable instanceof List ? ((List) iterable).get(0) : collection.iterator().next());
            }
        } else {
            if (z) {
                arrayList = new ArrayList((Collection) iterable);
            } else {
                arrayList = new ArrayList();
                Iterator it = iterable.iterator();
                while (it.hasNext()) {
                    arrayList.add(it.next());
                }
            }
            int size2 = arrayList.size();
            if (size2 != 0) {
                return size2 != 1 ? arrayList : Collections.singletonList(arrayList.get(0));
            }
        }
        return c0214gb;
    }

    public static Set e0(ArrayList arrayList) {
        C0276ib c0276ib = C0276ib.a;
        int size = arrayList.size();
        if (size == 0) {
            return c0276ib;
        }
        int i = 0;
        if (size == 1) {
            return Collections.singleton(arrayList.get(0));
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet(Bh.R(arrayList.size()));
        int size2 = arrayList.size();
        while (i < size2) {
            Object obj = arrayList.get(i);
            i++;
            linkedHashSet.add(obj);
        }
        return linkedHashSet;
    }
}
