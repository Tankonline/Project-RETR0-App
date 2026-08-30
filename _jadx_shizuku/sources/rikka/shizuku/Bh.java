package rikka.shizuku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public abstract class Bh extends AbstractC0599sr {
    public static int R(int i) {
        if (i < 0) {
            return i;
        }
        if (i < 3) {
            return i + 1;
        }
        if (i < 1073741824) {
            return (int) ((i / 0.75f) + 1.0f);
        }
        return Integer.MAX_VALUE;
    }

    public static Map S(ArrayList arrayList) {
        C0245hb c0245hb = C0245hb.a;
        int size = arrayList.size();
        if (size == 0) {
            return c0245hb;
        }
        int i = 0;
        if (size == 1) {
            Hj hj = (Hj) arrayList.get(0);
            return Collections.singletonMap(hj.a, hj.b);
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap(R(arrayList.size()));
        int size2 = arrayList.size();
        while (i < size2) {
            Object obj = arrayList.get(i);
            i++;
            Hj hj2 = (Hj) obj;
            linkedHashMap.put(hj2.a, hj2.b);
        }
        return linkedHashMap;
    }
}
