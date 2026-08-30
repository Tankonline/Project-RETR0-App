package rikka.shizuku;

import java.io.Serializable;

/* JADX INFO: renamed from: rikka.shizuku.u5, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0639u5 implements Xf, Serializable {
    public transient Xf a;
    public final Object b;
    public final Class c;
    public final String d;
    public final String e;
    public final boolean f;

    public AbstractC0639u5(Object obj, Class cls, String str, String str2, boolean z) {
        this.b = obj;
        this.c = cls;
        this.d = str;
        this.e = str2;
        this.f = z;
    }

    public final InterfaceC0117d6 d() {
        Class cls = this.c;
        if (!this.f) {
            return AbstractC0593sl.a(cls);
        }
        AbstractC0593sl.a.getClass();
        return new Fj(cls);
    }
}
