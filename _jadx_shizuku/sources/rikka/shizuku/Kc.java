package rikka.shizuku;

import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: classes.dex */
public final class Kc {
    public static final C0104co b = new C0104co(0);
    public final /* synthetic */ Qc a;

    public Kc(Qc qc) {
        this.a = qc;
    }

    public static Class b(ClassLoader classLoader, String str) throws ClassNotFoundException {
        C0104co c0104co = b;
        C0104co c0104co2 = (C0104co) c0104co.get(classLoader);
        if (c0104co2 == null) {
            c0104co2 = new C0104co(0);
            c0104co.put(classLoader, c0104co2);
        }
        Class cls = (Class) c0104co2.get(str);
        if (cls != null) {
            return cls;
        }
        Class<?> cls2 = Class.forName(str, false, classLoader);
        c0104co2.put(str, cls2);
        return cls2;
    }

    public static Class c(ClassLoader classLoader, String str) {
        try {
            return b(classLoader, str);
        } catch (ClassCastException e) {
            throw new K6(AbstractC0535qo.d("Unable to instantiate fragment ", str, ": make sure class is a valid subclass of Fragment"), e);
        } catch (ClassNotFoundException e2) {
            throw new K6(AbstractC0535qo.d("Unable to instantiate fragment ", str, ": make sure class name exists"), e2);
        }
    }

    public final Ac a(String str) {
        try {
            return (Ac) c(this.a.w.j.getClassLoader(), str).getConstructor(null).newInstance(null);
        } catch (IllegalAccessException e) {
            throw new K6(AbstractC0535qo.d("Unable to instantiate fragment ", str, ": make sure class name exists, is public, and has an empty constructor that is public"), e);
        } catch (InstantiationException e2) {
            throw new K6(AbstractC0535qo.d("Unable to instantiate fragment ", str, ": make sure class name exists, is public, and has an empty constructor that is public"), e2);
        } catch (NoSuchMethodException e3) {
            throw new K6(AbstractC0535qo.d("Unable to instantiate fragment ", str, ": could not find Fragment constructor"), e3);
        } catch (InvocationTargetException e4) {
            throw new K6(AbstractC0535qo.d("Unable to instantiate fragment ", str, ": calling Fragment constructor caused an exception"), e4);
        }
    }
}
