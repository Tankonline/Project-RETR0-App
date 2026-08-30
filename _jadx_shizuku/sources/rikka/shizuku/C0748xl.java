package rikka.shizuku;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* JADX INFO: renamed from: rikka.shizuku.xl, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0748xl {
    public final String a;
    public final Field b;
    public final String c;
    public final /* synthetic */ Method d;
    public final /* synthetic */ Lq e;
    public final /* synthetic */ Lq f;
    public final /* synthetic */ boolean g;
    public final /* synthetic */ boolean h;

    public C0748xl(String str, Field field, Method method, Lq lq, Lq lq2, boolean z, boolean z2) {
        this.d = method;
        this.e = lq;
        this.f = lq2;
        this.g = z;
        this.h = z2;
        this.a = str;
        this.b = field;
        this.c = field.getName();
    }

    public final void a(Wf wf, Object obj) throws IllegalAccessException {
        Object objInvoke;
        Field field = this.b;
        Method method = this.d;
        if (method != null) {
            try {
                objInvoke = method.invoke(obj, null);
            } catch (InvocationTargetException e) {
                throw new Rf(AbstractC0535qo.d("Accessor ", AbstractC0717wl.d(method, false), " threw exception"), e.getCause());
            }
        } else {
            objInvoke = field.get(obj);
        }
        if (objInvoke == obj) {
            return;
        }
        wf.g(this.a);
        this.e.c(wf, objInvoke);
    }
}
