package rikka.shizuku;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

/* JADX INFO: renamed from: rikka.shizuku.t6, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0609t6 extends Lq {
    public final /* synthetic */ int a = 0;
    public final Object b;
    public final Object c;

    public C0609t6(Nq nq, InterfaceC0253hj interfaceC0253hj) {
        this.b = nq;
        this.c = interfaceC0253hj;
    }

    @Override // rikka.shizuku.Lq
    public final Object b(Vf vf) {
        switch (this.a) {
            case 0:
                if (vf.v() == 9) {
                    vf.r();
                    return null;
                }
                Collection collection = (Collection) ((InterfaceC0253hj) this.c).d();
                vf.a();
                while (vf.i()) {
                    collection.add(((Nq) this.b).b.b(vf));
                }
                vf.e();
                return collection;
            default:
                Object objB = ((C0261hr) this.c).c.b(vf);
                if (objB != null) {
                    Class cls = (Class) this.b;
                    if (!cls.isInstance(objB)) {
                        throw new Rf("Expected a " + cls.getName() + " but was " + objB.getClass().getName() + "; at path " + vf.h(true));
                    }
                }
                return objB;
        }
    }

    @Override // rikka.shizuku.Lq
    public final void c(Wf wf, Object obj) throws IOException {
        switch (this.a) {
            case 0:
                Collection collection = (Collection) obj;
                if (collection != null) {
                    wf.b();
                    Iterator it = collection.iterator();
                    while (it.hasNext()) {
                        ((Nq) this.b).c(wf, it.next());
                    }
                    wf.e();
                } else {
                    wf.i();
                }
                break;
            default:
                ((C0261hr) this.c).c.c(wf, obj);
                break;
        }
    }

    public C0609t6(C0261hr c0261hr, Class cls) {
        this.c = c0261hr;
        this.b = cls;
    }
}
