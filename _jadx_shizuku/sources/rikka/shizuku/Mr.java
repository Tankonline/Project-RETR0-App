package rikka.shizuku;

import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
public final class Mr extends Pr {
    public final /* synthetic */ Method b;
    public final /* synthetic */ int c;

    public Mr(int i, Method method) {
        this.b = method;
        this.c = i;
    }

    @Override // rikka.shizuku.Pr
    public final Object a(Class cls) {
        String strA = C0672v7.a(cls);
        if (strA != null) {
            throw new AssertionError("UnsafeAllocator is used for non-instantiable type: ".concat(strA));
        }
        return this.b.invoke(null, cls, Integer.valueOf(this.c));
    }
}
