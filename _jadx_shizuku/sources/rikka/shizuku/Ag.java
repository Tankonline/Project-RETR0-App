package rikka.shizuku;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes.dex */
public abstract class Ag {
    public static final HashMap a = new HashMap();
    public static final HashMap b = new HashMap();

    public static void a(Constructor constructor, InterfaceC0712wg interfaceC0712wg) {
        try {
            if (constructor.newInstance(interfaceC0712wg) == null) {
            } else {
                throw new ClassCastException();
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e2) {
            throw new RuntimeException(e2);
        } catch (InvocationTargetException e3) {
            throw new RuntimeException(e3);
        }
    }

    public static final String b(String str) {
        int iIndexOf;
        StringBuilder sb = new StringBuilder();
        int i = 0;
        if (str == null) {
            int length = str.length();
            int length2 = str.length();
            if (length > length2) {
                length = length2;
            }
            int i2 = new C0680vf(0, length, 1).b;
            if (i2 >= 0) {
                iIndexOf = 0;
                while (!".".regionMatches(0, str, iIndexOf, ".".length())) {
                    if (iIndexOf == i2) {
                        iIndexOf = -1;
                        break;
                    }
                    iIndexOf++;
                }
            } else {
                iIndexOf = -1;
                break;
            }
        } else {
            iIndexOf = str.indexOf(".", 0);
        }
        if (iIndexOf >= 0) {
            int length3 = str.length();
            if (length3 < 0) {
                throw new OutOfMemoryError();
            }
            StringBuilder sb2 = new StringBuilder(length3);
            do {
                sb2.append((CharSequence) str, i, iIndexOf);
                sb2.append("_");
                i = iIndexOf + 1;
                if (iIndexOf >= str.length()) {
                    break;
                }
                iIndexOf = str.indexOf(".", i);
            } while (iIndexOf > 0);
            sb2.append((CharSequence) str, i, str.length());
            str = sb2.toString();
        }
        sb.append(str);
        sb.append("_LifecycleAdapter");
        return sb.toString();
    }

    /* JADX WARN: Code duplicated, block: B:61:0x00eb  */
    /* JADX WARN: Code duplicated, block: B:62:0x00ed  */
    /* JADX WARN: Code duplicated, block: B:64:0x00f0  */
    /* JADX WARN: Code duplicated, block: B:70:0x00fe  */
    /* JADX WARN: Code duplicated, block: B:73:0x0103  */
    /* JADX WARN: Code duplicated, block: B:76:0x010a A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:77:0x010c  */
    /* JADX WARN: Code duplicated, block: B:83:0x0128  */
    /* JADX WARN: Code duplicated, block: B:95:0x012d A[EDGE_INSN: B:95:0x012d->B:84:0x012d BREAK  A[LOOP:0: B:59:0x00e8->B:72:0x0101], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:96:0x0126 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:97:0x0101 A[SYNTHETIC] */
    public static int c(Class cls) {
        Constructor<?> declaredConstructor;
        boolean zBooleanValue;
        Class<?>[] interfaces;
        int i;
        boolean z;
        Class<?> cls2;
        boolean z2;
        HashMap map = a;
        Integer num = (Integer) map.get(cls);
        if (num != null) {
            return num.intValue();
        }
        int i2 = 1;
        if (cls.getCanonicalName() != null) {
            ArrayList arrayList = null;
            try {
                Package r3 = cls.getPackage();
                String canonicalName = cls.getCanonicalName();
                String name = r3 != null ? r3.getName() : "";
                if (name.length() != 0) {
                    canonicalName = canonicalName.substring(name.length() + 1);
                }
                String strB = b(canonicalName);
                if (name.length() != 0) {
                    strB = name + '.' + strB;
                }
                declaredConstructor = Class.forName(strB).getDeclaredConstructor(cls);
                if (!declaredConstructor.isAccessible()) {
                    declaredConstructor.setAccessible(true);
                }
            } catch (ClassNotFoundException unused) {
                declaredConstructor = null;
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
            HashMap map2 = b;
            if (declaredConstructor == null) {
                C0271i6 c0271i6 = C0271i6.c;
                HashMap map3 = c0271i6.b;
                Boolean bool = (Boolean) map3.get(cls);
                if (bool != null) {
                    zBooleanValue = bool.booleanValue();
                } else {
                    try {
                        Method[] declaredMethods = cls.getDeclaredMethods();
                        int length = declaredMethods.length;
                        int i3 = 0;
                        while (true) {
                            if (i3 >= length) {
                                map3.put(cls, Boolean.FALSE);
                                zBooleanValue = false;
                                break;
                            }
                            if (((InterfaceC0684vj) declaredMethods[i3].getAnnotation(InterfaceC0684vj.class)) != null) {
                                c0271i6.a(cls, declaredMethods);
                                zBooleanValue = true;
                                break;
                            }
                            i3++;
                        }
                    } catch (NoClassDefFoundError e2) {
                        throw new IllegalArgumentException("The observer class has some methods that use newer APIs which are not available in the current OS version. Lifecycles cannot access even other methods so you should make sure that your observer classes only access framework classes that are available in your min API level OR use lifecycle:compiler annotation processor.", e2);
                    }
                }
                if (!zBooleanValue) {
                    Class superclass = cls.getSuperclass();
                    if (!(superclass != null && InterfaceC0712wg.class.isAssignableFrom(superclass))) {
                        interfaces = cls.getInterfaces();
                        i = 0;
                        while (true) {
                            if (i < interfaces.length) {
                                z = true;
                            } else {
                                z = false;
                            }
                            if (z) {
                                if (arrayList != null) {
                                    break;
                                }
                                map2.put(cls, arrayList);
                            } else {
                                int i4 = i + 1;
                                try {
                                    cls2 = interfaces[i];
                                    if (cls2 == null && InterfaceC0712wg.class.isAssignableFrom(cls2)) {
                                        z2 = true;
                                    } else {
                                        z2 = false;
                                    }
                                    if (z2) {
                                        if (c(cls2) == 1) {
                                            break;
                                        }
                                        if (arrayList == null) {
                                            arrayList = new ArrayList();
                                        }
                                        arrayList.addAll((Collection) map2.get(cls2));
                                    }
                                    i = i4;
                                } catch (ArrayIndexOutOfBoundsException e3) {
                                    throw new NoSuchElementException(e3.getMessage());
                                }
                            }
                        }
                    } else if (c(superclass) != 1) {
                        arrayList = new ArrayList((Collection) map2.get(superclass));
                        interfaces = cls.getInterfaces();
                        i = 0;
                        while (true) {
                            if (i < interfaces.length) {
                                z = true;
                            } else {
                                z = false;
                            }
                            if (z) {
                                if (arrayList != null) {
                                    break;
                                }
                                map2.put(cls, arrayList);
                            } else {
                                int i5 = i + 1;
                                cls2 = interfaces[i];
                                if (cls2 == null) {
                                    z2 = false;
                                } else {
                                    z2 = false;
                                }
                                if (z2) {
                                    if (c(cls2) == 1) {
                                        break;
                                        break;
                                    }
                                    if (arrayList == null) {
                                        arrayList = new ArrayList();
                                    }
                                    arrayList.addAll((Collection) map2.get(cls2));
                                }
                                i = i5;
                            }
                        }
                    }
                }
            } else {
                map2.put(cls, Collections.singletonList(declaredConstructor));
            }
            i2 = 2;
            break;
        }
        map.put(cls, Integer.valueOf(i2));
        return i2;
    }
}
