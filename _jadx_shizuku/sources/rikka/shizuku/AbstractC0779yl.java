package rikka.shizuku;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: renamed from: rikka.shizuku.yl, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0779yl extends Lq {
    public final Al a;

    public AbstractC0779yl(Al al) {
        this.a = al;
    }

    @Override // rikka.shizuku.Lq
    public final Object b(Vf vf) throws IOException {
        if (vf.v() == 9) {
            vf.r();
            return null;
        }
        Object objD = d();
        Map map = this.a.a;
        try {
            vf.b();
            while (vf.i()) {
                C0748xl c0748xl = (C0748xl) map.get(vf.p());
                if (c0748xl == null) {
                    vf.B();
                } else {
                    f(objD, vf, c0748xl);
                }
            }
            vf.f();
            return e(objD);
        } catch (IllegalAccessException e) {
            AbstractC0046as abstractC0046as = AbstractC0717wl.a;
            throw new RuntimeException("Unexpected IllegalAccessException occurred (Gson 2.13.1). Certain ReflectionAccessFilter features require Java >= 9 to work correctly. If you are not using ReflectionAccessFilter, report this to the Gson maintainers.", e);
        } catch (IllegalStateException e2) {
            throw new Rf(e2);
        }
    }

    @Override // rikka.shizuku.Lq
    public final void c(Wf wf, Object obj) throws IOException {
        if (obj == null) {
            wf.i();
            return;
        }
        wf.c();
        try {
            Iterator it = this.a.b.iterator();
            while (it.hasNext()) {
                ((C0748xl) it.next()).a(wf, obj);
            }
            wf.f();
        } catch (IllegalAccessException e) {
            AbstractC0046as abstractC0046as = AbstractC0717wl.a;
            throw new RuntimeException("Unexpected IllegalAccessException occurred (Gson 2.13.1). Certain ReflectionAccessFilter features require Java >= 9 to work correctly. If you are not using ReflectionAccessFilter, report this to the Gson maintainers.", e);
        }
    }

    public abstract Object d();

    public abstract Object e(Object obj);

    public abstract void f(Object obj, Vf vf, C0748xl c0748xl);
}
