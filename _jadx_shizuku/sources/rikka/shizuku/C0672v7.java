package rikka.shizuku;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import rikka.html.text.style.BulletSpan;

/* JADX INFO: renamed from: rikka.shizuku.v7, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0672v7 {
    public final /* synthetic */ int a;
    public boolean b;
    public final ArrayList c;
    public final Object d;

    public C0672v7(C0419n1 c0419n1) {
        this.a = 1;
        this.d = new Object();
        this.c = new ArrayList();
    }

    public static String a(Class cls) {
        int modifiers = cls.getModifiers();
        if (Modifier.isInterface(modifiers)) {
            return "Interfaces can't be instantiated! Register an InstanceCreator or a TypeAdapter for this type. Interface name: ".concat(cls.getName());
        }
        if (!Modifier.isAbstract(modifiers)) {
            return null;
        }
        return "Abstract classes can't be instantiated! Adjust the R8 configuration or register an InstanceCreator or a TypeAdapter for this type. Class name: " + cls.getName() + "\nSee " + "https://github.com/google/gson/blob/main/Troubleshooting.md#".concat("r8-abstract-class");
    }

    /* JADX WARN: Code duplicated, block: B:56:0x00ff  */
    /* JADX WARN: Code duplicated, block: B:58:0x0107  */
    /* JADX WARN: Code duplicated, block: B:59:0x010d  */
    /* JADX WARN: Code duplicated, block: B:61:0x0115  */
    /* JADX WARN: Code duplicated, block: B:62:0x011b  */
    /* JADX WARN: Code duplicated, block: B:64:0x0123  */
    /* JADX WARN: Code duplicated, block: B:65:0x0129  */
    /* JADX WARN: Code duplicated, block: B:67:0x0131  */
    public InterfaceC0253hj b(C0630tr c0630tr, boolean z) {
        InterfaceC0253hj interfaceC0253hj;
        final String str;
        InterfaceC0253hj c0610t7;
        final int i = 3;
        final int i2 = 2;
        final int i3 = 1;
        final int i4 = 0;
        HashMap map = (HashMap) this.d;
        final Type type = c0630tr.b;
        if (map.get(type) != null) {
            throw new ClassCastException();
        }
        Class cls = c0630tr.a;
        if (map.get(cls) != null) {
            throw new ClassCastException();
        }
        C0579s7 c0579s7 = null;
        if (EnumSet.class.isAssignableFrom(cls)) {
            interfaceC0253hj = new InterfaceC0253hj() { // from class: rikka.shizuku.u7
                @Override // rikka.shizuku.InterfaceC0253hj
                public final Object d() {
                    switch (i4) {
                        case 0:
                            Type type2 = type;
                            if (!(type2 instanceof ParameterizedType)) {
                                throw new Rf("Invalid EnumSet type: " + type2.toString());
                            }
                            Type type3 = ((ParameterizedType) type2).getActualTypeArguments()[0];
                            if (type3 instanceof Class) {
                                return EnumSet.noneOf((Class) type3);
                            }
                            throw new Rf("Invalid EnumSet type: " + type2.toString());
                        default:
                            Type type4 = type;
                            if (!(type4 instanceof ParameterizedType)) {
                                throw new Rf("Invalid EnumMap type: " + type4.toString());
                            }
                            Type type5 = ((ParameterizedType) type4).getActualTypeArguments()[0];
                            if (type5 instanceof Class) {
                                return new EnumMap((Class) type5);
                            }
                            throw new Rf("Invalid EnumMap type: " + type4.toString());
                    }
                }
            };
        } else {
            interfaceC0253hj = cls == EnumMap.class ? new InterfaceC0253hj() { // from class: rikka.shizuku.u7
                @Override // rikka.shizuku.InterfaceC0253hj
                public final Object d() {
                    switch (i3) {
                        case 0:
                            Type type2 = type;
                            if (!(type2 instanceof ParameterizedType)) {
                                throw new Rf("Invalid EnumSet type: " + type2.toString());
                            }
                            Type type3 = ((ParameterizedType) type2).getActualTypeArguments()[0];
                            if (type3 instanceof Class) {
                                return EnumSet.noneOf((Class) type3);
                            }
                            throw new Rf("Invalid EnumSet type: " + type2.toString());
                        default:
                            Type type4 = type;
                            if (!(type4 instanceof ParameterizedType)) {
                                throw new Rf("Invalid EnumMap type: " + type4.toString());
                            }
                            Type type5 = ((ParameterizedType) type4).getActualTypeArguments()[0];
                            if (type5 instanceof Class) {
                                return new EnumMap((Class) type5);
                            }
                            throw new Rf("Invalid EnumMap type: " + type4.toString());
                    }
                }
            } : null;
        }
        if (interfaceC0253hj != null) {
            return interfaceC0253hj;
        }
        AbstractC0599sr.p(this.c);
        if (Modifier.isAbstract(cls.getModifiers())) {
            c0610t7 = null;
        } else {
            try {
                Constructor declaredConstructor = cls.getDeclaredConstructor(null);
                AbstractC0046as abstractC0046as = AbstractC0717wl.a;
                try {
                    declaredConstructor.setAccessible(true);
                    str = null;
                } catch (Exception e) {
                    str = "Failed making constructor '" + AbstractC0717wl.b(declaredConstructor) + "' accessible; either increase its visibility or write a custom InstanceCreator or TypeAdapter for its declaring type: " + e.getMessage() + AbstractC0717wl.e(e);
                }
                c0610t7 = str != null ? new InterfaceC0253hj() { // from class: rikka.shizuku.r7
                    @Override // rikka.shizuku.InterfaceC0253hj
                    public final Object d() {
                        switch (i3) {
                            case 0:
                                throw new Rf(str);
                            case 1:
                                throw new Rf(str);
                            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                                throw new Rf(str);
                            default:
                                throw new Rf(str);
                        }
                    }
                } : new C0610t7(i4, declaredConstructor);
            } catch (NoSuchMethodException unused) {
                c0610t7 = null;
            }
        }
        if (c0610t7 != null) {
            return c0610t7;
        }
        if (Collection.class.isAssignableFrom(cls)) {
            if (cls.isAssignableFrom(ArrayList.class)) {
                c0579s7 = new C0579s7(5);
            } else if (cls.isAssignableFrom(LinkedHashSet.class)) {
                c0579s7 = new C0579s7(6);
            } else if (cls.isAssignableFrom(TreeSet.class)) {
                c0579s7 = new C0579s7(7);
            } else if (cls.isAssignableFrom(ArrayDeque.class)) {
                c0579s7 = new C0579s7(8);
            }
        } else if (Map.class.isAssignableFrom(cls)) {
            if (cls.isAssignableFrom(Pg.class)) {
                if (type instanceof ParameterizedType) {
                    Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
                    if (actualTypeArguments.length == 0 || X8.E(actualTypeArguments[0]) != String.class) {
                        if (cls.isAssignableFrom(LinkedHashMap.class)) {
                            c0579s7 = new C0579s7(1);
                        } else if (cls.isAssignableFrom(TreeMap.class)) {
                            c0579s7 = new C0579s7(2);
                        } else if (cls.isAssignableFrom(ConcurrentHashMap.class)) {
                            c0579s7 = new C0579s7(3);
                        } else if (cls.isAssignableFrom(ConcurrentSkipListMap.class)) {
                            c0579s7 = new C0579s7(4);
                        }
                    }
                }
                c0579s7 = new C0579s7(0);
            } else if (cls.isAssignableFrom(LinkedHashMap.class)) {
                c0579s7 = new C0579s7(1);
            } else if (cls.isAssignableFrom(TreeMap.class)) {
                c0579s7 = new C0579s7(2);
            } else if (cls.isAssignableFrom(ConcurrentHashMap.class)) {
                c0579s7 = new C0579s7(3);
            } else if (cls.isAssignableFrom(ConcurrentSkipListMap.class)) {
                c0579s7 = new C0579s7(4);
            }
        }
        if (c0579s7 != null) {
            return c0579s7;
        }
        final String strA = a(cls);
        if (strA != null) {
            return new InterfaceC0253hj() { // from class: rikka.shizuku.r7
                @Override // rikka.shizuku.InterfaceC0253hj
                public final Object d() {
                    switch (i4) {
                        case 0:
                            throw new Rf(strA);
                        case 1:
                            throw new Rf(strA);
                        case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                            throw new Rf(strA);
                        default:
                            throw new Rf(strA);
                    }
                }
            };
        }
        if (!z) {
            final String str2 = "Unable to create instance of " + cls + "; Register an InstanceCreator or a TypeAdapter for this type.";
            return new InterfaceC0253hj() { // from class: rikka.shizuku.r7
                @Override // rikka.shizuku.InterfaceC0253hj
                public final Object d() {
                    switch (i2) {
                        case 0:
                            throw new Rf(str2);
                        case 1:
                            throw new Rf(str2);
                        case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                            throw new Rf(str2);
                        default:
                            throw new Rf(str2);
                    }
                }
            };
        }
        if (this.b) {
            return new C0610t7(i3, cls);
        }
        final String strC = "Unable to create instance of " + cls + "; usage of JDK Unsafe is disabled. Registering an InstanceCreator or a TypeAdapter for this type, adding a no-args constructor, or enabling usage of JDK Unsafe may fix this problem.";
        if (cls.getDeclaredConstructors().length == 0) {
            strC = AbstractC0535qo.c(strC, " Or adjust your R8 configuration to keep the no-args constructor of the class.");
        }
        return new InterfaceC0253hj() { // from class: rikka.shizuku.r7
            @Override // rikka.shizuku.InterfaceC0253hj
            public final Object d() {
                switch (i) {
                    case 0:
                        throw new Rf(strC);
                    case 1:
                        throw new Rf(strC);
                    case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                        throw new Rf(strC);
                    default:
                        throw new Rf(strC);
                }
            }
        };
    }

    public String toString() {
        switch (this.a) {
            case 0:
                return ((HashMap) this.d).toString();
            default:
                return super.toString();
        }
    }

    public C0672v7(HashMap map, boolean z, ArrayList arrayList) {
        this.a = 0;
        this.d = map;
        this.b = z;
        this.c = arrayList;
    }
}
