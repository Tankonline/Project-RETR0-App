package rikka.shizuku;

import java.io.IOException;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class Ah extends Lq {
    public final Nq a;
    public final Nq b;
    public final InterfaceC0253hj c;
    public final /* synthetic */ C0640u6 d;

    public Ah(C0640u6 c0640u6, Nq nq, Nq nq2, InterfaceC0253hj interfaceC0253hj) {
        this.d = c0640u6;
        this.a = nq;
        this.b = nq2;
        this.c = interfaceC0253hj;
    }

    @Override // rikka.shizuku.Lq
    public final Object b(Vf vf) throws IOException {
        int iV = vf.v();
        if (iV == 9) {
            vf.r();
            return null;
        }
        Map map = (Map) this.c.d();
        if (iV == 1) {
            vf.a();
            while (vf.i()) {
                vf.a();
                Object objB = this.a.b.b(vf);
                if (map.put(objB, this.b.b.b(vf)) != null) {
                    throw new Rf("duplicate key: " + objB);
                }
                vf.e();
            }
            vf.e();
            return map;
        }
        vf.b();
        while (vf.i()) {
            Ge.b.getClass();
            int iD = vf.g;
            if (iD == 0) {
                iD = vf.d();
            }
            if (iD == 13) {
                vf.g = 9;
            } else if (iD == 12) {
                vf.g = 8;
            } else {
                if (iD != 14) {
                    throw vf.D("a name");
                }
                vf.g = 10;
            }
            Object objB2 = this.a.b.b(vf);
            if (map.put(objB2, this.b.b.b(vf)) != null) {
                throw new Rf("duplicate key: " + objB2);
            }
        }
        vf.f();
        return map;
    }

    @Override // rikka.shizuku.Lq
    public final void c(Wf wf, Object obj) throws IOException {
        Map map = (Map) obj;
        if (map == null) {
            wf.i();
            return;
        }
        this.d.getClass();
        Nq nq = this.b;
        wf.c();
        for (Map.Entry entry : map.entrySet()) {
            wf.g(String.valueOf(entry.getKey()));
            nq.c(wf, entry.getValue());
        }
        wf.f();
    }
}
