package rikka.shizuku;

import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;

/* JADX INFO: loaded from: classes.dex */
public final class Fb {
    public Hashtable a;

    static {
        HashSet hashSet = new HashSet();
        hashSet.add(Db.e);
        hashSet.add(Db.f);
        hashSet.add(Db.d);
        hashSet.add(Db.g);
        Collections.unmodifiableSet(hashSet);
    }
}
