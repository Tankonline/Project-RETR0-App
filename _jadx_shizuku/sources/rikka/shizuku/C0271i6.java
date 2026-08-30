package rikka.shizuku;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: renamed from: rikka.shizuku.i6, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0271i6 {
    public static final C0271i6 c = new C0271i6();
    public final HashMap a = new HashMap();
    public final HashMap b = new HashMap();

    public static void b(HashMap map, C0240h6 c0240h6, EnumC0557rg enumC0557rg, Class cls) {
        EnumC0557rg enumC0557rg2 = (EnumC0557rg) map.get(c0240h6);
        if (enumC0557rg2 == null || enumC0557rg == enumC0557rg2) {
            if (enumC0557rg2 == null) {
                map.put(c0240h6, enumC0557rg);
                return;
            }
            return;
        }
        throw new IllegalArgumentException("Method " + c0240h6.b.getName() + " in " + cls.getName() + " already declared with different @OnLifecycleEvent value: previous value " + enumC0557rg2 + ", new value " + enumC0557rg);
    }

    public final C0209g6 a(Class cls, Method[] methodArr) {
        int i;
        Class superclass = cls.getSuperclass();
        HashMap map = new HashMap();
        HashMap map2 = this.a;
        if (superclass != null) {
            C0209g6 c0209g6A = (C0209g6) map2.get(superclass);
            if (c0209g6A == null) {
                c0209g6A = a(superclass, null);
            }
            map.putAll(c0209g6A.b);
        }
        for (Class<?> cls2 : cls.getInterfaces()) {
            C0209g6 c0209g6A2 = (C0209g6) map2.get(cls2);
            if (c0209g6A2 == null) {
                c0209g6A2 = a(cls2, null);
            }
            for (Map.Entry entry : c0209g6A2.b.entrySet()) {
                b(map, (C0240h6) entry.getKey(), (EnumC0557rg) entry.getValue(), cls);
            }
        }
        if (methodArr == null) {
            try {
                methodArr = cls.getDeclaredMethods();
            } catch (NoClassDefFoundError e) {
                throw new IllegalArgumentException("The observer class has some methods that use newer APIs which are not available in the current OS version. Lifecycles cannot access even other methods so you should make sure that your observer classes only access framework classes that are available in your min API level OR use lifecycle:compiler annotation processor.", e);
            }
        }
        boolean z = false;
        for (Method method : methodArr) {
            InterfaceC0684vj interfaceC0684vj = (InterfaceC0684vj) method.getAnnotation(InterfaceC0684vj.class);
            if (interfaceC0684vj != null) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length <= 0) {
                    i = 0;
                } else {
                    if (!InterfaceC0743xg.class.isAssignableFrom(parameterTypes[0])) {
                        throw new IllegalArgumentException("invalid parameter type. Must be one and instanceof LifecycleOwner");
                    }
                    i = 1;
                }
                EnumC0557rg enumC0557rgValue = interfaceC0684vj.value();
                if (parameterTypes.length > 1) {
                    if (!EnumC0557rg.class.isAssignableFrom(parameterTypes[1])) {
                        throw new IllegalArgumentException("invalid parameter type. second arg must be an event");
                    }
                    if (enumC0557rgValue != EnumC0557rg.ON_ANY) {
                        throw new IllegalArgumentException("Second arg is supported only for ON_ANY value");
                    }
                    i = 2;
                }
                if (parameterTypes.length > 2) {
                    throw new IllegalArgumentException("cannot have more than 2 params");
                }
                b(map, new C0240h6(i, method), enumC0557rgValue, cls);
                z = true;
            }
        }
        C0209g6 c0209g6 = new C0209g6(map);
        map2.put(cls, c0209g6);
        this.b.put(cls, Boolean.valueOf(z));
        return c0209g6;
    }
}
