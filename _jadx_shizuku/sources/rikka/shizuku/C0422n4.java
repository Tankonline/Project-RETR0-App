package rikka.shizuku;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;

/* JADX INFO: renamed from: rikka.shizuku.n4, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0422n4 implements Mq {
    @Override // rikka.shizuku.Mq
    public final Lq a(Rd rd, C0630tr c0630tr) {
        Type type = c0630tr.b;
        boolean z = type instanceof GenericArrayType;
        if (!z && (!(type instanceof Class) || !((Class) type).isArray())) {
            return null;
        }
        Type genericComponentType = z ? ((GenericArrayType) type).getGenericComponentType() : ((Class) type).getComponentType();
        return new C0453o4(rd, rd.c(new C0630tr(genericComponentType)), X8.E(genericComponentType));
    }
}
