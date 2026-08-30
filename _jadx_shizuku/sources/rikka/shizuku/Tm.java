package rikka.shizuku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public abstract class Tm extends Um {
    public static Rm a0(Iterator it) {
        return new Y6(new Vm(it));
    }

    public static List b0(Rm rm) {
        Iterator it = rm.iterator();
        if (!it.hasNext()) {
            return C0214gb.a;
        }
        Object next = it.next();
        if (!it.hasNext()) {
            return Collections.singletonList(next);
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(next);
        while (it.hasNext()) {
            arrayList.add(it.next());
        }
        return arrayList;
    }
}
