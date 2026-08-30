package rikka.shizuku;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/* JADX INFO: renamed from: rikka.shizuku.up, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0659up extends AbstractC0417n {
    public AbstractC0726x a;
    public C0386m b;
    public C0386m c;
    public K1 d;
    public lu e;
    public Qp f;
    public Qp g;
    public lu h;
    public C0197fp i;
    public C0580s8 j;
    public C0580s8 k;
    public Eb l;

    public static C0659up i(InterfaceC0202g interfaceC0202g) {
        int i;
        boolean z;
        boolean z2;
        lu luVar;
        lu luVar2;
        Eb eb;
        Db db;
        C0140e c0140e;
        int i2 = 3;
        if (interfaceC0202g instanceof C0659up) {
            return (C0659up) interfaceC0202g;
        }
        if (interfaceC0202g == null) {
            return null;
        }
        AbstractC0726x abstractC0726xT = AbstractC0726x.t(interfaceC0202g);
        C0659up c0659up = new C0659up();
        c0659up.a = abstractC0726xT;
        if (abstractC0726xT.u(0) instanceof H4) {
            c0659up.b = (C0386m) C0386m.c.d((H4) abstractC0726xT.u(0), true);
            i = 0;
        } else {
            c0659up.b = new C0386m(0L);
            i = -1;
        }
        if (c0659up.b.s(0)) {
            z = true;
            z2 = false;
        } else if (c0659up.b.s(1)) {
            z2 = true;
            z = false;
        } else {
            if (!c0659up.b.s(2)) {
                throw new IllegalArgumentException("version number not recognised");
            }
            z = false;
            z2 = false;
        }
        c0659up.c = C0386m.r(abstractC0726xT.u(i + 1));
        c0659up.d = K1.i(abstractC0726xT.u(i + 2));
        InterfaceC0202g interfaceC0202gU = abstractC0726xT.u(i + 3);
        C0700w4 c0700w4 = lu.f;
        if (interfaceC0202gU instanceof lu) {
            luVar = (lu) interfaceC0202gU;
        } else {
            luVar = interfaceC0202gU != null ? new lu(AbstractC0726x.t(interfaceC0202gU)) : null;
        }
        c0659up.e = luVar;
        AbstractC0726x abstractC0726x = (AbstractC0726x) abstractC0726xT.u(i + 4);
        c0659up.f = Qp.i(abstractC0726x.u(0));
        c0659up.g = Qp.i(abstractC0726x.u(1));
        InterfaceC0202g interfaceC0202gU2 = abstractC0726xT.u(i + 5);
        if (interfaceC0202gU2 instanceof lu) {
            luVar2 = (lu) interfaceC0202gU2;
        } else {
            luVar2 = interfaceC0202gU2 != null ? new lu(AbstractC0726x.t(interfaceC0202gU2)) : null;
        }
        c0659up.h = luVar2;
        int i3 = i + 6;
        c0659up.i = C0197fp.i(abstractC0726xT.u(i3));
        int size = (abstractC0726xT.size() - i3) - 1;
        if (size != 0 && z) {
            throw new IllegalArgumentException("version 1 certificate contains extra data");
        }
        while (size > 0) {
            H4 h4 = (H4) abstractC0726xT.u(i3 + size);
            int i4 = h4.c;
            if (i4 == 1) {
                c0659up.j = C0580s8.u(h4);
            } else if (i4 == 2) {
                c0659up.k = C0580s8.u(h4);
            } else {
                if (i4 != i2) {
                    throw new IllegalArgumentException("Unknown tag encountered in structure: " + h4.c);
                }
                if (z2) {
                    throw new IllegalArgumentException("version 2 certificate cannot contain extensions");
                }
                AbstractC0726x abstractC0726x2 = (AbstractC0726x) AbstractC0726x.b.d(h4, true);
                if (abstractC0726x2 != null) {
                    AbstractC0726x abstractC0726xT2 = AbstractC0726x.t(abstractC0726x2);
                    eb = new Eb();
                    eb.a = new Hashtable();
                    eb.b = new Vector();
                    Enumeration enumerationV = abstractC0726xT2.v();
                    while (enumerationV.hasMoreElements()) {
                        Object objNextElement = enumerationV.nextElement();
                        C0510q c0510q = Db.d;
                        if (objNextElement instanceof Db) {
                            db = (Db) objNextElement;
                        } else if (objNextElement != null) {
                            AbstractC0726x abstractC0726xT3 = AbstractC0726x.t(objNextElement);
                            Db db2 = new Db();
                            if (abstractC0726xT3.size() == 2) {
                                db2.a = C0510q.t(abstractC0726xT3.u(0));
                                db2.b = false;
                                db2.c = r.r(abstractC0726xT3.u(1));
                            } else {
                                if (abstractC0726xT3.size() != i2) {
                                    throw new IllegalArgumentException("Bad sequence size: " + abstractC0726xT3.size());
                                }
                                db2.a = C0510q.t(abstractC0726xT3.u(0));
                                Object objU = abstractC0726xT3.u(1);
                                if (objU == null || (objU instanceof C0140e)) {
                                    c0140e = (C0140e) objU;
                                } else {
                                    if (!(objU instanceof byte[])) {
                                        throw new IllegalArgumentException("illegal object in getInstance: ".concat(objU.getClass().getName()));
                                    }
                                    try {
                                        AbstractC0633u abstractC0633uO = AbstractC0633u.o((byte[]) objU);
                                        if (!C0140e.class.isInstance(abstractC0633uO)) {
                                            throw new IllegalStateException("unexpected object: ".concat(abstractC0633uO.getClass().getName()));
                                        }
                                        c0140e = (C0140e) abstractC0633uO;
                                    } catch (IOException e) {
                                        throw new IllegalArgumentException("failed to construct boolean from byte[]: " + e.getMessage());
                                    }
                                }
                                db2.b = c0140e.s();
                                db2.c = r.r(abstractC0726xT3.u(2));
                            }
                            db = db2;
                        } else {
                            db = null;
                        }
                        boolean zContainsKey = eb.a.containsKey(db.a);
                        C0510q c0510q2 = db.a;
                        if (zContainsKey) {
                            throw new IllegalArgumentException("repeated extension found: " + c0510q2);
                        }
                        eb.a.put(c0510q2, db);
                        eb.b.addElement(c0510q2);
                        i2 = 3;
                    }
                } else {
                    eb = null;
                }
                c0659up.l = eb;
            }
            size--;
            i2 = 3;
        }
        return c0659up;
    }

    @Override // rikka.shizuku.InterfaceC0202g
    public final AbstractC0633u d() {
        String strA = AbstractC0747xk.a("org.bouncycastle.x509.allow_non-der_tbscert");
        AbstractC0726x abstractC0726x = this.a;
        if (strA == null || AbstractC0747xk.b("org.bouncycastle.x509.allow_non-der_tbscert")) {
            return abstractC0726x;
        }
        C0233h c0233h = new C0233h();
        C0386m c0386m = this.b;
        if (!c0386m.s(0)) {
            c0233h.d(new H4(true, 0, (InterfaceC0202g) c0386m, 1));
        }
        c0233h.d(this.c);
        c0233h.d(this.d);
        c0233h.d(this.e);
        C0233h c0233h2 = new C0233h(2);
        c0233h2.d(this.f);
        c0233h2.d(this.g);
        D8 d8 = new D8(c0233h2);
        d8.c = -1;
        c0233h.d(d8);
        InterfaceC0202g d9 = this.h;
        if (d9 == null) {
            d9 = new D8();
        }
        c0233h.d(d9);
        c0233h.d(this.i);
        C0580s8 c0580s8 = this.j;
        if (c0580s8 != null) {
            c0233h.d(new H4(2, 128, 1, c0580s8, 1));
        }
        C0580s8 c0580s9 = this.k;
        if (c0580s9 != null) {
            c0233h.d(new H4(2, 128, 2, c0580s9, 1));
        }
        Eb eb = this.l;
        if (eb != null) {
            c0233h.d(new H4(true, 3, (InterfaceC0202g) eb, 1));
        }
        D8 d10 = new D8(c0233h);
        d10.c = -1;
        return d10;
    }
}
