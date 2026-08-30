package rikka.shizuku;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: renamed from: rikka.shizuku.j4, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0298j4 extends C0104co implements Map {
    public Ng d;
    public C0207g4 e;
    public C0269i4 f;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0298j4(C0104co c0104co) {
        super(0);
        int i = c0104co.c;
        b(this.c + i);
        if (this.c != 0) {
            for (int i2 = 0; i2 < i; i2++) {
                put(c0104co.f(i2), c0104co.i(i2));
            }
        } else if (i > 0) {
            AbstractC0484p4.e0(0, 0, i, c0104co.a, this.a);
            AbstractC0484p4.f0(c0104co.b, this.b, 0, 0, i << 1);
            this.c = i;
        }
    }

    @Override // java.util.Map
    public final Set entrySet() {
        Ng ng = this.d;
        if (ng != null) {
            return ng;
        }
        Ng ng2 = new Ng(this, 2);
        this.d = ng2;
        return ng2;
    }

    public final boolean j(Collection collection) {
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            if (!super.containsKey(it.next())) {
                return false;
            }
        }
        return true;
    }

    public final boolean k(Collection collection) {
        int i = this.c;
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            super.remove(it.next());
        }
        return i != this.c;
    }

    @Override // java.util.Map
    public final Set keySet() {
        C0207g4 c0207g4 = this.e;
        if (c0207g4 != null) {
            return c0207g4;
        }
        C0207g4 c0207g5 = new C0207g4(this);
        this.e = c0207g5;
        return c0207g5;
    }

    @Override // java.util.Map
    public final void putAll(Map map) {
        b(map.size() + this.c);
        for (Map.Entry entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override // java.util.Map
    public final Collection values() {
        C0269i4 c0269i4 = this.f;
        if (c0269i4 != null) {
            return c0269i4;
        }
        C0269i4 c0269i5 = new C0269i4(this);
        this.f = c0269i5;
        return c0269i5;
    }
}
