package rikka.shizuku;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public final class Ng extends AbstractSet {
    public final /* synthetic */ int a;
    public final /* synthetic */ Map b;

    public /* synthetic */ Ng(Map map, int i) {
        this.a = i;
        this.b = map;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public void clear() {
        switch (this.a) {
            case 0:
                ((Pg) this.b).clear();
                break;
            case 1:
                ((Pg) this.b).clear();
                break;
            default:
                super.clear();
                break;
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean contains(Object obj) {
        Og ogA;
        switch (this.a) {
            case 0:
                if (!(obj instanceof Map.Entry)) {
                    return false;
                }
                Pg pg = (Pg) this.b;
                Map.Entry entry = (Map.Entry) obj;
                Object key = entry.getKey();
                Og og = null;
                if (key != null) {
                    try {
                        ogA = pg.a(key, false);
                    } catch (ClassCastException unused) {
                        ogA = null;
                    }
                    break;
                } else {
                    ogA = null;
                }
                if (ogA != null && Objects.equals(ogA.h, entry.getValue())) {
                    og = ogA;
                }
                return og != null;
            case 1:
                return ((Pg) this.b).containsKey(obj);
            default:
                return super.contains(obj);
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator iterator() {
        switch (this.a) {
            case 0:
                return new Mg((Pg) this.b, 0);
            case 1:
                return new Mg((Pg) this.b, 1);
            default:
                return new C0238h4((C0298j4) this.b);
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean remove(Object obj) {
        Og ogA;
        switch (this.a) {
            case 0:
                if (!(obj instanceof Map.Entry)) {
                    return false;
                }
                Map.Entry entry = (Map.Entry) obj;
                Pg pg = (Pg) this.b;
                Object key = entry.getKey();
                Og og = null;
                if (key != null) {
                    try {
                        ogA = pg.a(key, false);
                    } catch (ClassCastException unused) {
                        ogA = null;
                    }
                    break;
                } else {
                    ogA = null;
                }
                if (ogA != null && Objects.equals(ogA.h, entry.getValue())) {
                    og = ogA;
                }
                if (og == null) {
                    return false;
                }
                pg.c(og, true);
                return true;
            case 1:
                Pg pg2 = (Pg) this.b;
                Og ogA2 = null;
                if (obj != null) {
                    try {
                        ogA2 = pg2.a(obj, false);
                        break;
                    } catch (ClassCastException unused2) {
                    }
                }
                if (ogA2 != null) {
                    pg2.c(ogA2, true);
                }
                return ogA2 != null;
            default:
                return super.remove(obj);
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        switch (this.a) {
            case 0:
                return ((Pg) this.b).d;
            case 1:
                return ((Pg) this.b).d;
            default:
                return ((C0298j4) this.b).c;
        }
    }
}
