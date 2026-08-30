package rikka.shizuku;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class Cl implements Mq {
    public final C0672v7 a;
    public final EnumC0061bc b;
    public final C0769yb c;
    public final Nf d;
    public final ArrayList e;

    public Cl(C0672v7 c0672v7, EnumC0061bc enumC0061bc, C0769yb c0769yb, Nf nf, ArrayList arrayList) {
        this.a = c0672v7;
        this.b = enumC0061bc;
        this.c = c0769yb;
        this.d = nf;
        this.e = arrayList;
    }

    public static void b(Class cls, String str, Field field, Field field2) {
        throw new IllegalArgumentException("Class " + cls.getName() + " declares multiple JSON fields named '" + str + "'; conflict is caused by fields " + AbstractC0717wl.c(field) + " and " + AbstractC0717wl.c(field2) + "\nSee " + "https://github.com/google/gson/blob/main/Troubleshooting.md#".concat("duplicate-fields"));
    }

    @Override // rikka.shizuku.Mq
    public final Lq a(Rd rd, C0630tr c0630tr) {
        Class cls = c0630tr.a;
        if (!Object.class.isAssignableFrom(cls)) {
            return null;
        }
        AbstractC0046as abstractC0046as = AbstractC0717wl.a;
        if (!Modifier.isStatic(cls.getModifiers()) && (cls.isAnonymousClass() || cls.isLocalClass())) {
            return new Nd(2);
        }
        AbstractC0599sr.p(this.e);
        return AbstractC0717wl.a.G(cls) ? new Bl(cls, c(rd, c0630tr, cls, true)) : new C0810zl(this.a.b(c0630tr, true), c(rd, c0630tr, cls, false));
    }

    /* JADX WARN: Code duplicated, block: B:37:0x008d  */
    /* JADX WARN: Code duplicated, block: B:40:0x00a5  */
    /* JADX WARN: Code duplicated, block: B:41:0x00ae  */
    /* JADX WARN: Code duplicated, block: B:44:0x00c5  */
    /* JADX WARN: Code duplicated, block: B:45:0x00cd  */
    /* JADX WARN: Code duplicated, block: B:51:0x00fb  */
    /* JADX WARN: Code duplicated, block: B:57:0x0110  */
    /* JADX WARN: Code duplicated, block: B:60:0x011d  */
    /* JADX WARN: Code duplicated, block: B:61:0x012f  */
    /* JADX WARN: Code duplicated, block: B:64:0x013c  */
    /* JADX WARN: Code duplicated, block: B:66:0x0140  */
    /* JADX WARN: Code duplicated, block: B:68:0x0146 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:69:0x0148  */
    /* JADX WARN: Code duplicated, block: B:70:0x014a  */
    /* JADX WARN: Code duplicated, block: B:72:0x0154  */
    /* JADX WARN: Code duplicated, block: B:75:0x0165  */
    /* JADX WARN: Code duplicated, block: B:78:0x016f  */
    /* JADX WARN: Code duplicated, block: B:80:0x017f A[LOOP:2: B:76:0x0169->B:80:0x017f, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:95:0x0181 A[SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r14v0 */
    /* JADX WARN: Type inference failed for: r14v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r14v5 */
    /* JADX WARN: Type inference failed for: r16v3 */
    /* JADX WARN: Type inference failed for: r16v6 */
    /* JADX WARN: Type inference failed for: r22v0 */
    /* JADX WARN: Type inference failed for: r22v1, types: [boolean] */
    /* JADX WARN: Type inference failed for: r22v2 */
    /* JADX WARN: Type inference failed for: r23v0 */
    /* JADX WARN: Type inference failed for: r23v1, types: [boolean] */
    /* JADX WARN: Type inference failed for: r23v2 */
    /* JADX WARN: Type inference failed for: r26v0 */
    /* JADX WARN: Type inference failed for: r26v1 */
    /* JADX WARN: Type inference failed for: r26v2 */
    /* JADX WARN: Type inference failed for: r26v3 */
    /* JADX WARN: Type inference failed for: r26v4 */
    /* JADX WARN: Type inference failed for: r26v5 */
    /* JADX WARN: Type inference failed for: r29v0, types: [rikka.shizuku.Cl] */
    /* JADX WARN: Type inference failed for: r3v2 */
    /* JADX WARN: Type inference failed for: r3v4 */
    /* JADX WARN: Type inference failed for: r3v5, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r3v9 */
    public final Al c(Rd rd, C0630tr c0630tr, Class cls, boolean z) {
        Type type;
        ?? r26;
        ?? r27;
        Method method;
        Xm xm;
        List listAsList;
        String strB;
        ?? SingletonList;
        C0630tr c0630tr2;
        Class cls2;
        ?? r22;
        int modifiers;
        ?? r23;
        Lf lf;
        Rd rd2;
        Lq lqC;
        ?? r16;
        Lq lq;
        C0748xl c0748xl;
        C0748xl c0748xl2;
        C0748xl c0748xl3;
        Lq nq;
        if (cls.isInterface()) {
            return Al.c;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        C0630tr c0630tr3 = c0630tr;
        Class cls3 = cls;
        while (cls3 != Object.class) {
            Field[] declaredFields = cls3.getDeclaredFields();
            if (cls3 != cls && declaredFields.length > 0) {
                AbstractC0599sr.p(this.e);
            }
            int length = declaredFields.length;
            ?? r14 = 0;
            int i = 0;
            while (true) {
                type = c0630tr3.b;
                if (i < length) {
                    Field field = declaredFields[i];
                    boolean zD = d(field, true);
                    boolean zD2 = d(field, r14);
                    if (zD || zD2) {
                        if (z) {
                            if (Modifier.isStatic(field.getModifiers())) {
                                r26 = r14;
                            } else {
                                Method methodR = AbstractC0717wl.a.r(cls3, field);
                                AbstractC0717wl.f(methodR);
                                if (methodR.getAnnotation(Xm.class) != null && field.getAnnotation(Xm.class) == null) {
                                    throw new Rf(AbstractC0535qo.d("@SerializedName on ", AbstractC0717wl.d(methodR, r14), " is not supported"));
                                }
                                r27 = zD2;
                                method = methodR;
                            }
                            if (method == null) {
                                AbstractC0717wl.f(field);
                            }
                            Type typeT = X8.T(type, cls3, field.getGenericType(), new HashMap());
                            xm = (Xm) field.getAnnotation(Xm.class);
                            if (xm == null) {
                                strB = this.b.b(field);
                                listAsList = Collections.EMPTY_LIST;
                            } else {
                                String strValue = xm.value();
                                listAsList = Arrays.asList(xm.alternate());
                                strB = strValue;
                            }
                            if (listAsList.isEmpty()) {
                                SingletonList = Collections.singletonList(strB);
                            } else {
                                ArrayList arrayList = new ArrayList(listAsList.size() + 1);
                                arrayList.add(strB);
                                arrayList.addAll(listAsList);
                                SingletonList = arrayList;
                            }
                            String str = (String) SingletonList.get(r14);
                            c0630tr2 = new C0630tr(typeT);
                            cls2 = c0630tr2.a;
                            if (cls2 == null && cls2.isPrimitive()) {
                                r22 = r16;
                            } else {
                                r22 = r14;
                            }
                            modifiers = field.getModifiers();
                            if (Modifier.isStatic(modifiers) || !Modifier.isFinal(modifiers)) {
                                r23 = r14;
                            } else {
                                r23 = r16;
                            }
                            lf = (Lf) field.getAnnotation(Lf.class);
                            if (lf != null) {
                                rd2 = rd;
                                lqC = this.d.b(this.a, rd2, c0630tr2, lf, false);
                            } else {
                                rd2 = rd;
                                lqC = null;
                            }
                            r16 = lqC == null ? r14 : 1;
                            if (lqC == null) {
                                lqC = rd2.c(c0630tr2);
                            }
                            if (zD) {
                                if (r16 != 0) {
                                    nq = lqC;
                                } else {
                                    nq = new Nq(rd2, lqC, c0630tr2.b);
                                }
                                lq = nq;
                            } else {
                                lq = lqC;
                            }
                            c0748xl = new C0748xl(str, field, method, lq, lqC, r22, r23);
                            if (r27 != 0) {
                                for (String str2 : SingletonList) {
                                    c0748xl3 = (C0748xl) linkedHashMap.put(str2, c0748xl);
                                    if (c0748xl3 == null) {
                                        b(cls, str2, c0748xl3.b, field);
                                        throw null;
                                    }
                                }
                            }
                            if (zD && (c0748xl2 = (C0748xl) linkedHashMap2.put(str, c0748xl)) != null) {
                                b(cls, str, c0748xl2.b, field);
                                throw null;
                            }
                        } else {
                            r26 = zD2;
                        }
                        method = null;
                        r27 = r26;
                        if (method == null) {
                            AbstractC0717wl.f(field);
                        }
                        Type typeT2 = X8.T(type, cls3, field.getGenericType(), new HashMap());
                        xm = (Xm) field.getAnnotation(Xm.class);
                        if (xm == null) {
                            strB = this.b.b(field);
                            listAsList = Collections.EMPTY_LIST;
                        } else {
                            String strValue2 = xm.value();
                            listAsList = Arrays.asList(xm.alternate());
                            strB = strValue2;
                        }
                        if (listAsList.isEmpty()) {
                            SingletonList = Collections.singletonList(strB);
                        } else {
                            ArrayList arrayList2 = new ArrayList(listAsList.size() + 1);
                            arrayList2.add(strB);
                            arrayList2.addAll(listAsList);
                            SingletonList = arrayList2;
                        }
                        String str3 = (String) SingletonList.get(r14);
                        c0630tr2 = new C0630tr(typeT2);
                        cls2 = c0630tr2.a;
                        if (cls2 == null) {
                            r22 = r14;
                        } else {
                            r22 = r14;
                        }
                        modifiers = field.getModifiers();
                        if (Modifier.isStatic(modifiers)) {
                            r23 = r14;
                        } else {
                            r23 = r14;
                        }
                        lf = (Lf) field.getAnnotation(Lf.class);
                        if (lf != null) {
                            rd2 = rd;
                            lqC = this.d.b(this.a, rd2, c0630tr2, lf, false);
                        } else {
                            rd2 = rd;
                            lqC = null;
                        }
                        if (lqC == null) {
                        }
                        if (lqC == null) {
                            lqC = rd2.c(c0630tr2);
                        }
                        if (zD) {
                            if (r16 != 0) {
                                nq = lqC;
                            } else {
                                nq = new Nq(rd2, lqC, c0630tr2.b);
                            }
                            lq = nq;
                        } else {
                            lq = lqC;
                        }
                        c0748xl = new C0748xl(str3, field, method, lq, lqC, r22, r23);
                        if (r27 != 0) {
                            while (r5.hasNext()) {
                                c0748xl3 = (C0748xl) linkedHashMap.put(str2, c0748xl);
                                if (c0748xl3 == null) {
                                    b(cls, str2, c0748xl3.b, field);
                                    throw null;
                                }
                            }
                        }
                        if (zD) {
                            continue;
                        }
                    }
                    i++;
                    r14 = 0;
                }
            }
            c0630tr3 = new C0630tr(X8.T(type, cls3, cls3.getGenericSuperclass(), new HashMap()));
            cls3 = c0630tr3.a;
        }
        return new Al(linkedHashMap, new ArrayList(linkedHashMap2.values()));
    }

    /* JADX WARN: Code duplicated, block: B:11:0x0035 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:12:0x0037  */
    /* JADX WARN: Code duplicated, block: B:14:0x0041  */
    /* JADX WARN: Code duplicated, block: B:17:0x0048  */
    /* JADX WARN: Code duplicated, block: B:21:0x0055  */
    /* JADX WARN: Code duplicated, block: B:22:0x0058  */
    /* JADX WARN: Code duplicated, block: B:25:0x0060  */
    /* JADX WARN: Code duplicated, block: B:28:0x006b  */
    /* JADX WARN: Code duplicated, block: B:4:0x000f  */
    public final boolean d(Field field, boolean z) {
        List list;
        boolean z2;
        Iterator it;
        C0769yb c0769yb = this.c;
        c0769yb.getClass();
        if ((field.getModifiers() & 136) != 0) {
            z2 = true;
        } else if (c0769yb.a != -1.0d) {
            InterfaceC0349ko interfaceC0349ko = (InterfaceC0349ko) field.getAnnotation(InterfaceC0349ko.class);
            Sr sr = (Sr) field.getAnnotation(Sr.class);
            if (interfaceC0349ko != null) {
                if (c0769yb.a >= interfaceC0349ko.value()) {
                    if (sr != null) {
                        if (c0769yb.a < sr.value()) {
                            if (field.isSynthetic() && !c0769yb.c(field.getType(), z)) {
                                if (z) {
                                    list = c0769yb.b;
                                } else {
                                    list = c0769yb.c;
                                }
                                if (!list.isEmpty()) {
                                    it = list.iterator();
                                    if (it.hasNext()) {
                                        it.next().getClass();
                                        throw new ClassCastException();
                                    }
                                }
                                z2 = false;
                            }
                        }
                    } else if (field.isSynthetic()) {
                        if (z) {
                            list = c0769yb.b;
                        } else {
                            list = c0769yb.c;
                        }
                        if (!list.isEmpty()) {
                            it = list.iterator();
                            if (it.hasNext()) {
                                it.next().getClass();
                                throw new ClassCastException();
                            }
                        }
                        z2 = false;
                    }
                }
            } else if (sr != null) {
                if (c0769yb.a < sr.value()) {
                    if (field.isSynthetic()) {
                        if (z) {
                            list = c0769yb.b;
                        } else {
                            list = c0769yb.c;
                        }
                        if (!list.isEmpty()) {
                            it = list.iterator();
                            if (it.hasNext()) {
                                it.next().getClass();
                                throw new ClassCastException();
                            }
                        }
                        z2 = false;
                    }
                }
            } else if (field.isSynthetic()) {
                if (z) {
                    list = c0769yb.b;
                } else {
                    list = c0769yb.c;
                }
                if (!list.isEmpty()) {
                    it = list.iterator();
                    if (it.hasNext()) {
                        it.next().getClass();
                        throw new ClassCastException();
                    }
                }
                z2 = false;
            }
            z2 = true;
        } else if (field.isSynthetic()) {
            if (z) {
                list = c0769yb.b;
            } else {
                list = c0769yb.c;
            }
            if (!list.isEmpty()) {
                it = list.iterator();
                if (it.hasNext()) {
                    it.next().getClass();
                    throw new ClassCastException();
                }
            }
            z2 = false;
        } else {
            z2 = true;
        }
        return !z2;
    }
}
