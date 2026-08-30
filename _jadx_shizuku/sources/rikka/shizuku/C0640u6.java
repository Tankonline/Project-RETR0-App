package rikka.shizuku;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/* JADX INFO: renamed from: rikka.shizuku.u6, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0640u6 implements Mq {
    public final /* synthetic */ int a;
    public final C0672v7 b;

    public /* synthetic */ C0640u6(C0672v7 c0672v7, int i) {
        this.a = i;
        this.b = c0672v7;
    }

    @Override // rikka.shizuku.Mq
    public final Lq a(Rd rd, C0630tr c0630tr) {
        Type[] actualTypeArguments;
        C0672v7 c0672v7 = this.b;
        Type type = Object.class;
        switch (this.a) {
            case 0:
                Class cls = c0630tr.a;
                if (!Collection.class.isAssignableFrom(cls)) {
                    return null;
                }
                Type type2 = c0630tr.b;
                if (type2 instanceof WildcardType) {
                    type2 = ((WildcardType) type2).getUpperBounds()[0];
                }
                AbstractC0744xh.g(Collection.class.isAssignableFrom(cls));
                Type typeT = X8.T(type2, cls, X8.A(type2, cls, Collection.class), new HashMap());
                type = typeT instanceof ParameterizedType ? ((ParameterizedType) typeT).getActualTypeArguments()[0] : Object.class;
                return new C0609t6(new Nq(rd, rd.c(new C0630tr(type)), type), c0672v7.b(c0630tr, false));
            default:
                Class cls2 = c0630tr.a;
                if (!Map.class.isAssignableFrom(cls2)) {
                    return null;
                }
                if (Properties.class.isAssignableFrom(cls2)) {
                    actualTypeArguments = new Type[]{String.class, String.class};
                } else {
                    Type type3 = c0630tr.b;
                    if (type3 instanceof WildcardType) {
                        type3 = ((WildcardType) type3).getUpperBounds()[0];
                    }
                    AbstractC0744xh.g(Map.class.isAssignableFrom(cls2));
                    Type typeT2 = X8.T(type3, cls2, X8.A(type3, cls2, Map.class), new HashMap());
                    actualTypeArguments = typeT2 instanceof ParameterizedType ? ((ParameterizedType) typeT2).getActualTypeArguments() : new Type[]{type, type};
                }
                Type type4 = actualTypeArguments[0];
                Type type5 = actualTypeArguments[1];
                return new Ah(this, new Nq(rd, (type4 == Boolean.TYPE || type4 == Boolean.class) ? AbstractC0568rr.c : rd.c(new C0630tr(type4)), type4), new Nq(rd, rd.c(new C0630tr(type5)), type5), c0672v7.b(c0630tr, false));
        }
    }
}
