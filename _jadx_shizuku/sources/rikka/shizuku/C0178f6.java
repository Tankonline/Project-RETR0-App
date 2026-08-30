package rikka.shizuku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: renamed from: rikka.shizuku.f6, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0178f6 implements Yf, InterfaceC0117d6 {
    public static final Map b;
    public static final HashMap c;
    public final Class a;

    static {
        List listAsList = Arrays.asList(InterfaceC0124dd.class, InterfaceC0462od.class, InterfaceC0585sd.class, InterfaceC0616td.class, InterfaceC0647ud.class, InterfaceC0678vd.class, InterfaceC0709wd.class, InterfaceC0740xd.class, InterfaceC0771yd.class, InterfaceC0802zd.class, InterfaceC0154ed.class, InterfaceC0185fd.class, InterfaceC0216gd.class, InterfaceC0247hd.class, InterfaceC0278id.class, InterfaceC0307jd.class, InterfaceC0338kd.class, InterfaceC0369ld.class, InterfaceC0400md.class, InterfaceC0431nd.class, InterfaceC0493pd.class, InterfaceC0524qd.class, InterfaceC0554rd.class);
        ArrayList arrayList = new ArrayList(AbstractC0733x6.b0(listAsList));
        int i = 0;
        for (Object obj : listAsList) {
            int i2 = i + 1;
            if (i < 0) {
                throw new ArithmeticException("Index overflow has happened.");
            }
            arrayList.add(new Hj((Class) obj, Integer.valueOf(i)));
            i = i2;
        }
        b = Bh.S(arrayList);
        HashMap map = new HashMap();
        map.put("boolean", "kotlin.Boolean");
        map.put("char", "kotlin.Char");
        map.put("byte", "kotlin.Byte");
        map.put("short", "kotlin.Short");
        map.put("int", "kotlin.Int");
        map.put("float", "kotlin.Float");
        map.put("long", "kotlin.Long");
        map.put("double", "kotlin.Double");
        HashMap map2 = new HashMap();
        map2.put("java.lang.Boolean", "kotlin.Boolean");
        map2.put("java.lang.Character", "kotlin.Char");
        map2.put("java.lang.Byte", "kotlin.Byte");
        map2.put("java.lang.Short", "kotlin.Short");
        map2.put("java.lang.Integer", "kotlin.Int");
        map2.put("java.lang.Float", "kotlin.Float");
        map2.put("java.lang.Long", "kotlin.Long");
        map2.put("java.lang.Double", "kotlin.Double");
        HashMap map3 = new HashMap();
        map3.put("java.lang.Object", "kotlin.Any");
        map3.put("java.lang.String", "kotlin.String");
        map3.put("java.lang.CharSequence", "kotlin.CharSequence");
        map3.put("java.lang.Throwable", "kotlin.Throwable");
        map3.put("java.lang.Cloneable", "kotlin.Cloneable");
        map3.put("java.lang.Number", "kotlin.Number");
        map3.put("java.lang.Comparable", "kotlin.Comparable");
        map3.put("java.lang.Enum", "kotlin.Enum");
        map3.put("java.lang.annotation.Annotation", "kotlin.Annotation");
        map3.put("java.lang.Iterable", "kotlin.collections.Iterable");
        map3.put("java.util.Iterator", "kotlin.collections.Iterator");
        map3.put("java.util.Collection", "kotlin.collections.Collection");
        map3.put("java.util.List", "kotlin.collections.List");
        map3.put("java.util.Set", "kotlin.collections.Set");
        map3.put("java.util.ListIterator", "kotlin.collections.ListIterator");
        map3.put("java.util.Map", "kotlin.collections.Map");
        map3.put("java.util.Map$Entry", "kotlin.collections.Map.Entry");
        map3.put("kotlin.jvm.internal.StringCompanionObject", "kotlin.String.Companion");
        map3.put("kotlin.jvm.internal.EnumCompanionObject", "kotlin.Enum.Companion");
        map3.putAll(map);
        map3.putAll(map2);
        for (String str : map.values()) {
            StringBuilder sb = new StringBuilder("kotlin.jvm.internal.");
            int iLastIndexOf = str.lastIndexOf(46, AbstractC0135dp.r(str));
            sb.append(iLastIndexOf == -1 ? str : str.substring(iLastIndexOf + 1, str.length()));
            sb.append("CompanionObject");
            map3.put(sb.toString(), str.concat(".Companion"));
        }
        for (Map.Entry entry : b.entrySet()) {
            Class cls = (Class) entry.getKey();
            int iIntValue = ((Number) entry.getValue()).intValue();
            map3.put(cls.getName(), "kotlin.Function" + iIntValue);
        }
        c = map3;
        LinkedHashMap linkedHashMap = new LinkedHashMap(Bh.R(map3.size()));
        for (Map.Entry entry2 : map3.entrySet()) {
            Object key = entry2.getKey();
            String strSubstring = (String) entry2.getValue();
            int iLastIndexOf2 = strSubstring.lastIndexOf(46, AbstractC0135dp.r(strSubstring));
            if (iLastIndexOf2 != -1) {
                strSubstring = strSubstring.substring(iLastIndexOf2 + 1, strSubstring.length());
            }
            linkedHashMap.put(key, strSubstring);
        }
    }

    public C0178f6(Class cls) {
        this.a = cls;
    }

    @Override // rikka.shizuku.InterfaceC0117d6
    public final Class a() {
        return this.a;
    }

    public final boolean equals(Object obj) {
        return (obj instanceof C0178f6) && X8.C(this).equals(X8.C((Yf) obj));
    }

    public final int hashCode() {
        return X8.C(this).hashCode();
    }

    public final String toString() {
        return this.a + " (Kotlin reflection is not available)";
    }
}
