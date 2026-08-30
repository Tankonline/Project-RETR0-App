package rikka.shizuku;

import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
public final class Nr extends Pr {
    public final /* synthetic */ Method b;

    public Nr(Method method) {
        this.b = method;
    }

    @Override // rikka.shizuku.Pr
    public final Object a(Class cls) {
        String strA = C0672v7.a(cls);
        if (strA != null) {
            throw new AssertionError("UnsafeAllocator is used for non-instantiable type: ".concat(strA));
        }
        return this.b.invoke(null, cls, Object.class);
    }
}
