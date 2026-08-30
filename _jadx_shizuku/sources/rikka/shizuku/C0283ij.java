package rikka.shizuku;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* JADX INFO: renamed from: rikka.shizuku.ij, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0283ij extends Lq {
    public static final C0160ej c = new C0160ej(1, Zp.a);
    public final Rd a;
    public final Zp b;

    public C0283ij(Rd rd, Zp zp) {
        this.a = rd;
        this.b = zp;
    }

    @Override // rikka.shizuku.Lq
    public final Object b(Vf vf) {
        Object arrayList;
        Serializable arrayList2;
        int iV = vf.v();
        int iJ = AbstractC0535qo.j(iV);
        if (iJ == 0) {
            vf.a();
            arrayList = new ArrayList();
        } else if (iJ != 2) {
            arrayList = null;
        } else {
            vf.b();
            arrayList = new Pg(true);
        }
        if (arrayList == null) {
            return d(vf, iV);
        }
        ArrayDeque arrayDeque = new ArrayDeque();
        while (true) {
            if (vf.i()) {
                String strP = arrayList instanceof Map ? vf.p() : null;
                int iV2 = vf.v();
                int iJ2 = AbstractC0535qo.j(iV2);
                if (iJ2 == 0) {
                    vf.a();
                    arrayList2 = new ArrayList();
                } else if (iJ2 != 2) {
                    arrayList2 = null;
                } else {
                    vf.b();
                    arrayList2 = new Pg(true);
                }
                boolean z = arrayList2 != null;
                if (arrayList2 == null) {
                    arrayList2 = d(vf, iV2);
                }
                if (arrayList instanceof List) {
                    ((List) arrayList).add(arrayList2);
                } else {
                    ((Map) arrayList).put(strP, arrayList2);
                }
                if (z) {
                    arrayDeque.addLast(arrayList);
                    arrayList = arrayList2;
                }
            } else {
                if (arrayList instanceof List) {
                    vf.e();
                } else {
                    vf.f();
                }
                if (arrayDeque.isEmpty()) {
                    return arrayList;
                }
                arrayList = arrayDeque.removeLast();
            }
        }
    }

    @Override // rikka.shizuku.Lq
    public final void c(Wf wf, Object obj) throws IOException {
        if (obj == null) {
            wf.i();
            return;
        }
        Class<?> cls = obj.getClass();
        Rd rd = this.a;
        rd.getClass();
        Lq lqC = rd.c(new C0630tr(cls));
        if (!(lqC instanceof C0283ij)) {
            lqC.c(wf, obj);
        } else {
            wf.c();
            wf.f();
        }
    }

    public final Serializable d(Vf vf, int i) {
        int iJ = AbstractC0535qo.j(i);
        if (iJ == 5) {
            return vf.t();
        }
        if (iJ == 6) {
            return this.b.a(vf);
        }
        if (iJ == 7) {
            return Boolean.valueOf(vf.l());
        }
        if (iJ != 8) {
            throw new IllegalStateException("Unexpected token: ".concat(AbstractC0535qo.k(i)));
        }
        vf.r();
        return null;
    }
}
