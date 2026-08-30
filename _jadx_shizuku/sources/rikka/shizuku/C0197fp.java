package rikka.shizuku;

import java.util.Enumeration;

/* JADX INFO: renamed from: rikka.shizuku.fp, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0197fp extends AbstractC0417n {
    public K1 a;
    public C0580s8 b;

    public static C0197fp i(Object obj) {
        if (obj instanceof C0197fp) {
            return (C0197fp) obj;
        }
        if (obj == null) {
            return null;
        }
        AbstractC0726x abstractC0726xT = AbstractC0726x.t(obj);
        C0197fp c0197fp = new C0197fp();
        if (abstractC0726xT.size() != 2) {
            throw new IllegalArgumentException("Bad sequence size: " + abstractC0726xT.size());
        }
        Enumeration enumerationV = abstractC0726xT.v();
        c0197fp.a = K1.i(enumerationV.nextElement());
        c0197fp.b = C0580s8.t(enumerationV.nextElement());
        return c0197fp;
    }

    @Override // rikka.shizuku.InterfaceC0202g
    public final AbstractC0633u d() {
        C0233h c0233h = new C0233h(2);
        c0233h.d(this.a);
        c0233h.d(this.b);
        D8 d8 = new D8(c0233h);
        d8.c = -1;
        return d8;
    }
}
