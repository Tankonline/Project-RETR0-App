package rikka.shizuku;

import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
public final class Lr extends Pr {
    public final /* synthetic */ Method b;
    public final /* synthetic */ Object c;

    public Lr(Method method, Object obj) {
        this.b = method;
        this.c = obj;
    }

    @Override // rikka.shizuku.Pr
    public final Object a(Class cls) {
        String strA = C0672v7.a(cls);
        if (strA != null) {
            throw new AssertionError("UnsafeAllocator is used for non-instantiable type: ".concat(strA));
        }
        return this.b.invoke(this.c, cls);
    }
}
