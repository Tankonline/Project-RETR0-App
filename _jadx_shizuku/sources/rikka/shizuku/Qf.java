package rikka.shizuku;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public class Qf extends Lq {
    public static final Qf a = new Qf();

    private Qf() {
    }

    public static Pf d(Vf vf, int i) {
        int iJ = AbstractC0535qo.j(i);
        if (iJ == 5) {
            return new Uf(vf.t());
        }
        if (iJ == 6) {
            return new Uf(new C0280ig(vf.t()));
        }
        if (iJ == 7) {
            return new Uf(Boolean.valueOf(vf.l()));
        }
        if (iJ != 8) {
            throw new IllegalStateException("Unexpected token: ".concat(AbstractC0535qo.k(i)));
        }
        vf.r();
        return Sf.a;
    }

    public static void e(Wf wf, Pf pf) throws IOException {
        if (pf == null || (pf instanceof Sf)) {
            wf.i();
            return;
        }
        boolean z = pf instanceof Uf;
        if (z) {
            if (!z) {
                throw new IllegalStateException("Not a JSON Primitive: " + pf);
            }
            Uf uf = (Uf) pf;
            Serializable serializable = uf.a;
            if (serializable instanceof Number) {
                wf.o(uf.b());
                return;
            } else if (serializable instanceof Boolean) {
                wf.q(serializable instanceof Boolean ? ((Boolean) serializable).booleanValue() : Boolean.parseBoolean(uf.c()));
                return;
            } else {
                wf.p(uf.c());
                return;
            }
        }
        boolean z2 = pf instanceof Of;
        if (z2) {
            wf.b();
            if (!z2) {
                throw new IllegalStateException("Not a JSON Array: " + pf);
            }
            ArrayList arrayList = ((Of) pf).a;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                e(wf, (Pf) obj);
            }
            wf.e();
            return;
        }
        boolean z3 = pf instanceof Tf;
        if (!z3) {
            throw new IllegalArgumentException("Couldn't write " + pf.getClass());
        }
        wf.c();
        if (!z3) {
            throw new IllegalStateException("Not a JSON Object: " + pf);
        }
        Iterator it = ((Ng) ((Tf) pf).a.entrySet()).iterator();
        while (((Mg) it).hasNext()) {
            Og ogB = ((Mg) it).b();
            wf.g((String) ogB.getKey());
            e(wf, (Pf) ogB.getValue());
        }
        wf.f();
    }

    @Override // rikka.shizuku.Lq
    public final Object b(Vf vf) {
        Pf of;
        Pf of2;
        int iV = vf.v();
        int iJ = AbstractC0535qo.j(iV);
        if (iJ == 0) {
            vf.a();
            of = new Of();
        } else if (iJ != 2) {
            of = null;
        } else {
            vf.b();
            of = new Tf();
        }
        if (of == null) {
            return d(vf, iV);
        }
        ArrayDeque arrayDeque = new ArrayDeque();
        while (true) {
            if (vf.i()) {
                String strP = of instanceof Tf ? vf.p() : null;
                int iV2 = vf.v();
                int iJ2 = AbstractC0535qo.j(iV2);
                if (iJ2 == 0) {
                    vf.a();
                    of2 = new Of();
                } else if (iJ2 != 2) {
                    of2 = null;
                } else {
                    vf.b();
                    of2 = new Tf();
                }
                boolean z = of2 != null;
                if (of2 == null) {
                    of2 = d(vf, iV2);
                }
                if (of instanceof Of) {
                    ((Of) of).a.add(of2);
                } else {
                    ((Tf) of).a.put(strP, of2);
                }
                if (z) {
                    arrayDeque.addLast(of);
                    of = of2;
                }
            } else {
                if (of instanceof Of) {
                    vf.e();
                } else {
                    vf.f();
                }
                if (arrayDeque.isEmpty()) {
                    return of;
                }
                of = (Pf) arrayDeque.removeLast();
            }
        }
    }

    @Override // rikka.shizuku.Lq
    public final /* bridge */ /* synthetic */ void c(Wf wf, Object obj) throws IOException {
        e(wf, (Pf) obj);
    }
}
