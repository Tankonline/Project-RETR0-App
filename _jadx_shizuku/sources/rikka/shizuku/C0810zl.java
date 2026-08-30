package rikka.shizuku;

import java.lang.reflect.Field;

/* JADX INFO: renamed from: rikka.shizuku.zl, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0810zl extends AbstractC0779yl {
    public final InterfaceC0253hj b;

    public C0810zl(InterfaceC0253hj interfaceC0253hj, Al al) {
        super(al);
        this.b = interfaceC0253hj;
    }

    @Override // rikka.shizuku.AbstractC0779yl
    public final Object d() {
        return this.b.d();
    }

    @Override // rikka.shizuku.AbstractC0779yl
    public final void f(Object obj, Vf vf, C0748xl c0748xl) throws IllegalAccessException {
        Object objB = c0748xl.f.b(vf);
        if (objB == null && c0748xl.g) {
            return;
        }
        Field field = c0748xl.b;
        if (!c0748xl.h) {
            field.set(obj, objB);
            return;
        }
        throw new Rf("Cannot set value of 'static final' " + AbstractC0717wl.d(field, false));
    }

    @Override // rikka.shizuku.AbstractC0779yl
    public final Object e(Object obj) {
        return obj;
    }
}
