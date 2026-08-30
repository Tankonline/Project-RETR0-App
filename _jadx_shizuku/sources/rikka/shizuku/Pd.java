package rikka.shizuku;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;

/* JADX INFO: loaded from: classes.dex */
public final class Pd extends Lq {
    public static final C0160ej c = new C0160ej(0, new Pd(2, Zp.b));
    public final /* synthetic */ int a;
    public final Object b;

    public /* synthetic */ Pd(int i, Object obj) {
        this.a = i;
        this.b = obj;
    }

    @Override // rikka.shizuku.Lq
    public final Object b(Vf vf) {
        switch (this.a) {
            case 0:
                return new AtomicLong(((Number) ((Lq) this.b).b(vf)).longValue());
            case 1:
                ArrayList arrayList = new ArrayList();
                vf.a();
                while (vf.i()) {
                    arrayList.add(Long.valueOf(((Number) ((Lq) this.b).b(vf)).longValue()));
                }
                vf.e();
                int size = arrayList.size();
                AtomicLongArray atomicLongArray = new AtomicLongArray(size);
                for (int i = 0; i < size; i++) {
                    atomicLongArray.set(i, ((Long) arrayList.get(i)).longValue());
                }
                return atomicLongArray;
            default:
                int iV = vf.v();
                int iJ = AbstractC0535qo.j(iV);
                if (iJ == 5 || iJ == 6) {
                    return ((Zp) this.b).a(vf);
                }
                if (iJ == 8) {
                    vf.r();
                    return null;
                }
                throw new Rf("Expecting number, got: " + AbstractC0535qo.k(iV) + "; at path " + vf.h(false));
        }
    }

    @Override // rikka.shizuku.Lq
    public final void c(Wf wf, Object obj) throws IOException {
        switch (this.a) {
            case 0:
                ((Lq) this.b).c(wf, Long.valueOf(((AtomicLong) obj).get()));
                break;
            case 1:
                AtomicLongArray atomicLongArray = (AtomicLongArray) obj;
                wf.b();
                int length = atomicLongArray.length();
                for (int i = 0; i < length; i++) {
                    ((Lq) this.b).c(wf, Long.valueOf(atomicLongArray.get(i)));
                }
                wf.e();
                break;
            default:
                wf.o((Number) obj);
                break;
        }
    }
}
